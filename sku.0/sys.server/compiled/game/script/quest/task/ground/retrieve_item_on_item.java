package script.quest.task.ground;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.groundquests;
import script.library.sui;
import script.library.stealth;
import script.library.utils;

public class retrieve_item_on_item extends script.base_script
{
    public retrieve_item_on_item()
    {
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        if (isMob(self))
        {
            setCondition(self, CONDITION_CONVERSABLE);
        }
        return SCRIPT_CONTINUE;
    }
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        if (isMob(self))
        {
            setCondition(self, CONDITION_CONVERSABLE);
        }
        return SCRIPT_CONTINUE;
    }
    public int OnObjectMenuRequest(obj_id self, obj_id player, menu_info menuInfo) throws InterruptedException
    {
        if (isDead(player) || isIncapacitated(player))
        {
            return SCRIPT_CONTINUE;
        }
        if (isIncapacitated(self))
        {
            if (!hasScript(self, "npc.static_npc.npc_dead"))
            {
                return SCRIPT_CONTINUE;
            }
        }
        int menu = 0;
        if (isMob(self))
        {
            menu = menuInfo.addRootMenu(menu_info_types.CONVERSE_START, null);
        }
        else 
        {
            menu = menuInfo.addRootMenu(menu_info_types.ITEM_USE, new string_id("ui_radial", "item_use"));
        }
        if (hasScript(player, "quest.task.ground.retrieve_item"))
        {
            if (groundquests.playerNeedsToRetrieveThisItem(player, self))
            {
                String menuText = groundquests.getRetrieveMenuText(player, self);
                string_id menuStringId = utils.unpackString(menuText);
                if (isMob(self))
                {
                    menu = menuInfo.addRootMenu(menu_info_types.CONVERSE_START, menuStringId);
                }
                else 
                {
                    menu = menuInfo.addRootMenu(menu_info_types.ITEM_USE, menuStringId);
                }
            }
        }
        menu_info_data menuInfoData = menuInfo.getMenuItemById(menu);
        if (menuInfoData != null)
        {
            menuInfoData.setServerNotify(true);
        }
        return SCRIPT_CONTINUE;
    }
    public int OnObjectMenuSelect(obj_id self, obj_id player, int item) throws InterruptedException
    {
        location here = getLocation(player);
        location term = getLocation(self);
        float dist = getDistance(here, term);
        if (item == menu_info_types.ITEM_USE || item == menu_info_types.CONVERSE_START)
        {
            if (isDead(player) || isIncapacitated(player) || dist > 5.0)
            {
                return SCRIPT_CONTINUE;
            }
            if (hasScript(player, "quest.task.ground.retrieve_item"))
            {
                if (groundquests.playerNeedsToRetrieveThisItem(player, self))
                {
                    if (groundquests.gcwActiveDutyCheck(self, player))
                    {
                        int countdownTimer = groundquests.getQuestCountdownTime(player, self);
                        if (countdownTimer > 0)
                        {
                            String menuText = groundquests.getRetrieveMenuText(player, self);
                            string_id menuStringId = utils.unpackString(menuText);
                            String handler = "handleQuestCountdownTimer";
                            int startTime = 0;
                            int range = 3;
                            int flags = 0;
                            flags |= sui.CD_EVENT_INCAPACITATE;
                            flags |= sui.CD_EVENT_DAMAGED;
                            stealth.testInvisNonCombatAction(player, self);
                            int countdownSui = sui.smartCountdownTimerSUI(self, player, "quest_countdown_timer", menuStringId, startTime, countdownTimer, handler, range, flags);
                        }
                        else 
                        {
                            sendRetrieveObjectFoundMessage(self, player);
                        }
                    }
                    else 
                    {
                        sendSystemMessage(player, new string_id("quest/groundquests", "retrieve_item_not_active_duty"));
                    }
                    return SCRIPT_CONTINUE;
                }
                else 
                {
                    String itemTemplateName = getTemplateName(self);
                    dictionary tasks = groundquests.getActiveTasksForTaskType(player, "retrieve_item");
                    if ((tasks != null) && !tasks.isEmpty())
                    {
                        java.util.Enumeration keys = tasks.keys();
                        while (keys.hasMoreElements())
                        {
                            String questCrcString = (String)keys.nextElement();
                            int questCrc = utils.stringToInt(questCrcString);
                            int[] tasksForCurrentQuest = tasks.getIntArray(questCrcString);
                            for (int i = 0; i < tasksForCurrentQuest.length; ++i)
                            {
                                int taskId = tasksForCurrentQuest[i];
                                String baseObjVar = groundquests.getBaseObjVar(player, "retrieve_item", questGetQuestName(questCrc), taskId);
                                String retrieveTemplateName = groundquests.getTaskStringDataEntry(questCrc, taskId, "SERVER_TEMPLATE");
                                if (itemTemplateName.equals(retrieveTemplateName))
                                {
                                    sendSystemMessage(player, new string_id("quest/groundquests", "retrieve_item_already_used"));
                                    return SCRIPT_CONTINUE;
                                }
                            }
                        }
                    }
                }
            }
        }
        string_id msg = new string_id("quest/groundquests", "retrieve_item_no_interest");
        if (isMob(self))
        {
            msg = new string_id("quest/groundquests", "retrieve_item_no_interest_mob");
            if (isIncapacitated(self) || hasScript(self, "npc.static_npc.npc_dead"))
            {
                msg = new string_id("quest/groundquests", "retrieve_item_no_interest_mob_dead");
            }
        }
        sendSystemMessage(player, msg);
        return SCRIPT_CONTINUE;
    }
    public int handleQuestCountdownTimer(obj_id self, dictionary params) throws InterruptedException
    {
        int pid = params.getInt("id");
        obj_id player = params.getObjId("player");
        if (!isIdValid(player))
        {
            return SCRIPT_CONTINUE;
        }
        int bp = sui.getIntButtonPressed(params);
        if (bp == sui.BP_CANCEL)
        {
            detachScript(player, sui.COUNTDOWNTIMER_PLAYER_SCRIPT);
            return SCRIPT_CONTINUE;
        }
        else if (bp == sui.BP_REVERT)
        {
            int event = params.getInt("event");
            if (event == sui.CD_EVENT_LOCOMOTION)
            {
                sendSystemMessage(player, new string_id("quest/groundquests", "countdown_interrupted_locomotion"));
            }
            else if (event == sui.CD_EVENT_INCAPACITATE)
            {
                sendSystemMessage(player, new string_id("quest/groundquests", "countdown_interrupted_incapacitated"));
            }
            else if (event == sui.CD_EVENT_DAMAGED)
            {
                sendSystemMessage(player, new string_id("quest/groundquests", "countdown_interrupted_damaged"));
            }
            return SCRIPT_CONTINUE;
        }
        if (!hasObjVar(player, sui.COUNTDOWNTIMER_SUI_VAR))
        {
            return SCRIPT_CONTINUE;
        }
        int test_pid = getIntObjVar(player, sui.COUNTDOWNTIMER_SUI_VAR);
        if (pid != test_pid)
        {
            return SCRIPT_CONTINUE;
        }
        forceCloseSUIPage(pid);
        detachScript(player, sui.COUNTDOWNTIMER_PLAYER_SCRIPT);
        sendRetrieveObjectFoundMessage(self, player);
        return SCRIPT_CONTINUE;
    }
    public void sendRetrieveObjectFoundMessage(obj_id self, obj_id player) throws InterruptedException
    {
        dictionary webster = new dictionary();
        webster.put("source", self);
        webster.put("player", player);
        messageTo(player, "questRetrieveItemObjectFound", webster, 0, false);
        if (hasObjVar(self, "questRetrieveSignal"))
        {
            String questRetrieveSignal = getStringObjVar(self, "questRetrieveSignal");
            if (questRetrieveSignal != null && questRetrieveSignal.length() > 0)
            {
                groundquests.sendSignal(player, questRetrieveSignal);
            }
        }
        if (hasObjVar(self, "questFlavorObject"))
        {
            messageTo(self, "handleQuestFlavorObject", webster, 0, false);
        }
        return;
    }
}
