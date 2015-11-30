package script.conversation;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.ai_lib;
import script.library.chat;
import script.library.factions;
import script.library.groundquests;
import script.library.utils;

public class emp_day_reb_colonel extends script.base_script
{
    public emp_day_reb_colonel()
    {
    }
    public static String c_stringFile = "conversation/emp_day_reb_colonel";
    public boolean emp_day_reb_colonel_condition__defaultCondition(obj_id player, obj_id npc) throws InterruptedException
    {
        return true;
    }
    public boolean emp_day_reb_colonel_condition_isRebel(obj_id player, obj_id npc) throws InterruptedException
    {
        int factionHashCode = factions.pvpGetAlignedFaction(player);
        String whichFaction = factions.getFactionNameByHashCode(factionHashCode);
        if (whichFaction == null)
        {
            return false;
        }
        if (whichFaction.equals("Rebel"))
        {
            return true;
        }
        return false;
    }
    public boolean emp_day_reb_colonel_condition_notReadyForYou(obj_id player, obj_id npc) throws InterruptedException
    {
        int questId = questGetQuestId("quest/emp_day_rebel");
        int task1 = groundquests.getTaskId(questId, "converseContact");
        int task2 = groundquests.getTaskId(questId, "killImperials");
        int task3 = groundquests.getTaskId(questId, "diskGuy1");
        int task4 = groundquests.getTaskId(questId, "disksToContact");
        int task5 = groundquests.getTaskId(questId, "toRebelSpy");
        return (questIsTaskActive(questId, task1, player) || questIsTaskActive(questId, task2, player) || questIsTaskActive(questId, task3, player) || questIsTaskActive(questId, task4, player) || questIsTaskActive(questId, task5, player) || !questIsQuestActive(questId, player));
    }
    public boolean emp_day_reb_colonel_condition_questCompleted(obj_id player, obj_id npc) throws InterruptedException
    {
        int questId = questGetQuestId("quest/emp_day_rebel");
        return questIsQuestComplete(questId, player);
    }
    public boolean emp_day_reb_colonel_condition_hasTask6(obj_id player, obj_id npc) throws InterruptedException
    {
        int questId = questGetQuestId("quest/emp_day_rebel");
        int task6 = groundquests.getTaskId(questId, "toRebelColonel");
        int task7 = groundquests.getTaskId(questId, "toSolo");
        int factionHashCode = factions.pvpGetAlignedFaction(player);
        String whichFaction = factions.getFactionNameByHashCode(factionHashCode);
        return (questIsTaskActive(questId, task6, player) || (questIsTaskActive(questId, task7, player) && whichFaction == null));
    }
    public boolean emp_day_reb_colonel_condition_isNeutral(obj_id player, obj_id npc) throws InterruptedException
    {
        int factionHashCode = factions.pvpGetAlignedFaction(player);
        String whichFaction = factions.getFactionNameByHashCode(factionHashCode);
        if (whichFaction == null)
        {
            return true;
        }
        return false;
    }
    public boolean emp_day_reb_colonel_condition_hasTask8(obj_id player, obj_id npc) throws InterruptedException
    {
        int questId = questGetQuestId("quest/emp_day_rebel");
        int task8 = groundquests.getTaskId(questId, "empDayComplete");
        return questIsTaskActive(questId, task8, player);
    }
    public boolean emp_day_reb_colonel_condition_isImperial(obj_id player, obj_id npc) throws InterruptedException
    {
        int factionHashCode = factions.pvpGetAlignedFaction(player);
        String whichFaction = factions.getFactionNameByHashCode(factionHashCode);
        if (whichFaction == null)
        {
            return false;
        }
        if (whichFaction.equals("Imperial"))
        {
            return true;
        }
        return false;
    }
    public boolean emp_day_reb_colonel_condition_hasTask7(obj_id player, obj_id npc) throws InterruptedException
    {
        int questId = questGetQuestId("quest/emp_day_rebel");
        int task7 = groundquests.getTaskId(questId, "toSolo");
        int factionHashCode = factions.pvpGetAlignedFaction(player);
        String whichFaction = factions.getFactionNameByHashCode(factionHashCode);
        return (questIsTaskActive(questId, task7, player) && whichFaction.equals("Rebel"));
    }
    public boolean emp_day_reb_colonel_condition_convertOfficer(obj_id player, obj_id npc) throws InterruptedException
    {
        return (getIntObjVar(player, "event.emp_day.converted_officer") == 1);
    }
    public void emp_day_reb_colonel_action_rewardAndEndNow(obj_id player, obj_id npc) throws InterruptedException
    {
        obj_id playerInventory = utils.getInventoryContainer(player);
        obj_id createdObject = createObject("object/tangible/furniture/all/frn_all_banner_rebel.iff", playerInventory, "");
        if (isIdValid(createdObject))
        {
            groundquests.sendSignal(player, "to_solo");
            groundquests.sendSignal(player, "emp_day_complete");
            removeObjVar(player, "empire_day.waypoint");
            removeObjVar(player, "event.emp_day.han_access");
            setObjVar(player, "event.emp_day.rewarded", 1);
            removeObjVar(player, "event.emp_day.converted_officer");
        }
        else 
        {
            sendSystemMessage(player, new string_id("quest/lifeday/lifeday", "full_inv"));
        }
    }
    public void emp_day_reb_colonel_action_giveSoloAccess(obj_id player, obj_id npc) throws InterruptedException
    {
        setObjVar(player, "event.emp_day.han_access", 0);
        groundquests.sendSignal(player, "to_rebel_colonel");
    }
    public void emp_day_reb_colonel_action_signal6(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.sendSignal(player, "to_rebel_colonel");
    }
    public void emp_day_reb_colonel_action_rewardAndTwoBanners(obj_id player, obj_id npc) throws InterruptedException
    {
        obj_id playerInventory = utils.getInventoryContainer(player);
        obj_id createdObject = createObject("object/tangible/furniture/all/frn_all_banner_rebel.iff", playerInventory, "");
        obj_id createdObjectTwo = createObject("object/tangible/furniture/all/frn_all_banner_rebel.iff", playerInventory, "");
        if (isIdValid(createdObject))
        {
            if (isIdValid(createdObjectTwo))
            {
                groundquests.sendSignal(player, "to_solo");
                groundquests.sendSignal(player, "emp_day_complete");
                removeObjVar(player, "empire_day.waypoint");
                removeObjVar(player, "event.emp_day.han_access");
                setObjVar(player, "event.emp_day.rewarded", 1);
                removeObjVar(player, "event.emp_day.converted_officer");
            }
            else 
            {
                destroyObject(createdObject);
                sendSystemMessage(player, new string_id("quest/lifeday/lifeday", "full_inv"));
            }
        }
        else 
        {
            sendSystemMessage(player, new string_id("quest/lifeday/lifeday", "full_inv"));
        }
    }
    public int emp_day_reb_colonel_handleBranch3(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_303"))
        {
            if (emp_day_reb_colonel_condition__defaultCondition(player, npc))
            {
                emp_day_reb_colonel_action_rewardAndTwoBanners(player, npc);
                string_id message = new string_id(c_stringFile, "s_307");
                utils.removeScriptVar(player, "conversation.emp_day_reb_colonel.branchId");
                chat.chat(npc, player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_310"))
        {
            if (emp_day_reb_colonel_condition__defaultCondition(player, npc))
            {
                emp_day_reb_colonel_action_rewardAndEndNow(player, npc);
                string_id message = new string_id(c_stringFile, "s_312");
                utils.removeScriptVar(player, "conversation.emp_day_reb_colonel.branchId");
                chat.chat(npc, player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int emp_day_reb_colonel_handleBranch7(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_318"))
        {
            if (emp_day_reb_colonel_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_320");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (emp_day_reb_colonel_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_322");
                    }
                    utils.setScriptVar(player, "conversation.emp_day_reb_colonel.branchId", 8);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.emp_day_reb_colonel.branchId");
                    chat.chat(npc, player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int emp_day_reb_colonel_handleBranch8(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_322"))
        {
            if (emp_day_reb_colonel_condition_isRebel(player, npc))
            {
                emp_day_reb_colonel_action_giveSoloAccess(player, npc);
                string_id message = new string_id(c_stringFile, "s_324");
                utils.removeScriptVar(player, "conversation.emp_day_reb_colonel.branchId");
                chat.chat(npc, player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            if (emp_day_reb_colonel_condition_isNeutral(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_326");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (emp_day_reb_colonel_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (emp_day_reb_colonel_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (emp_day_reb_colonel_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse2 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_328");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_332");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_344");
                    }
                    utils.setScriptVar(player, "conversation.emp_day_reb_colonel.branchId", 10);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.emp_day_reb_colonel.branchId");
                    chat.chat(npc, player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int emp_day_reb_colonel_handleBranch10(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_328"))
        {
            if (emp_day_reb_colonel_condition__defaultCondition(player, npc))
            {
                emp_day_reb_colonel_action_signal6(player, npc);
                string_id message = new string_id(c_stringFile, "s_330");
                utils.removeScriptVar(player, "conversation.emp_day_reb_colonel.branchId");
                chat.chat(npc, player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_332"))
        {
            if (emp_day_reb_colonel_condition__defaultCondition(player, npc))
            {
                emp_day_reb_colonel_action_signal6(player, npc);
                string_id message = new string_id(c_stringFile, "s_334");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (emp_day_reb_colonel_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (emp_day_reb_colonel_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_336");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_340");
                    }
                    utils.setScriptVar(player, "conversation.emp_day_reb_colonel.branchId", 12);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.emp_day_reb_colonel.branchId");
                    chat.chat(npc, player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_344"))
        {
            if (emp_day_reb_colonel_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_346");
                utils.removeScriptVar(player, "conversation.emp_day_reb_colonel.branchId");
                chat.chat(npc, player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int emp_day_reb_colonel_handleBranch12(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_336"))
        {
            if (emp_day_reb_colonel_condition__defaultCondition(player, npc))
            {
                emp_day_reb_colonel_action_rewardAndEndNow(player, npc);
                string_id message = new string_id(c_stringFile, "s_338");
                utils.removeScriptVar(player, "conversation.emp_day_reb_colonel.branchId");
                chat.chat(npc, player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_340"))
        {
            if (emp_day_reb_colonel_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_342");
                utils.removeScriptVar(player, "conversation.emp_day_reb_colonel.branchId");
                chat.chat(npc, player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        if ((!isMob(self)) || (isPlayer(self)))
        {
            detachScript(self, "conversation.emp_day_reb_colonel");
        }
        setCondition(self, CONDITION_CONVERSABLE);
        return SCRIPT_CONTINUE;
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        setCondition(self, CONDITION_CONVERSABLE);
        return SCRIPT_CONTINUE;
    }
    public int OnObjectMenuRequest(obj_id self, obj_id player, menu_info menuInfo) throws InterruptedException
    {
        int menu = menuInfo.addRootMenu(menu_info_types.CONVERSE_START, null);
        menu_info_data menuInfoData = menuInfo.getMenuItemById(menu);
        menuInfoData.setServerNotify(false);
        setCondition(self, CONDITION_CONVERSABLE);
        return SCRIPT_CONTINUE;
    }
    public int OnIncapacitated(obj_id self, obj_id killer) throws InterruptedException
    {
        clearCondition(self, CONDITION_CONVERSABLE);
        detachScript(self, "conversation.emp_day_reb_colonel");
        return SCRIPT_CONTINUE;
    }
    public boolean npcStartConversation(obj_id player, obj_id npc, String convoName, string_id greetingId, prose_package greetingProse, string_id[] responses) throws InterruptedException
    {
        Object[] objects = new Object[responses.length];
        System.arraycopy(responses, 0, objects, 0, responses.length);
        return npcStartConversation(player, npc, convoName, greetingId, greetingProse, objects);
    }
    public int OnStartNpcConversation(obj_id self, obj_id player) throws InterruptedException
    {
        obj_id npc = self;
        if (ai_lib.isInCombat(npc) || ai_lib.isInCombat(player))
        {
            return SCRIPT_OVERRIDE;
        }
        if (emp_day_reb_colonel_condition_isImperial(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_293");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        if (emp_day_reb_colonel_condition_questCompleted(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_295");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        if (emp_day_reb_colonel_condition_hasTask8(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_299");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (emp_day_reb_colonel_condition_convertOfficer(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            boolean hasResponse1 = false;
            if (!emp_day_reb_colonel_condition_convertOfficer(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse1 = true;
            }
            if (hasResponse)
            {
                int responseIndex = 0;
                string_id responses[] = new string_id[numberOfResponses];
                if (hasResponse0)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_303");
                }
                if (hasResponse1)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_310");
                }
                utils.setScriptVar(player, "conversation.emp_day_reb_colonel.branchId", 3);
                npcStartConversation(player, npc, "emp_day_reb_colonel", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (emp_day_reb_colonel_condition_notReadyForYou(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_314");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        if (emp_day_reb_colonel_condition_hasTask6(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_316");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (emp_day_reb_colonel_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            if (hasResponse)
            {
                int responseIndex = 0;
                string_id responses[] = new string_id[numberOfResponses];
                if (hasResponse0)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_318");
                }
                utils.setScriptVar(player, "conversation.emp_day_reb_colonel.branchId", 7);
                npcStartConversation(player, npc, "emp_day_reb_colonel", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (emp_day_reb_colonel_condition_hasTask7(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_348");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        if (emp_day_reb_colonel_condition__defaultCondition(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_350");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        chat.chat(npc, "Error:  All conditions for OnStartNpcConversation were false.");
        return SCRIPT_CONTINUE;
    }
    public int OnNpcConversationResponse(obj_id self, String conversationId, obj_id player, string_id response) throws InterruptedException
    {
        if (!conversationId.equals("emp_day_reb_colonel"))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id npc = self;
        int branchId = utils.getIntScriptVar(player, "conversation.emp_day_reb_colonel.branchId");
        if (branchId == 3 && emp_day_reb_colonel_handleBranch3(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 7 && emp_day_reb_colonel_handleBranch7(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 8 && emp_day_reb_colonel_handleBranch8(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 10 && emp_day_reb_colonel_handleBranch10(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 12 && emp_day_reb_colonel_handleBranch12(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        chat.chat(npc, "Error:  Fell through all branches and responses for OnNpcConversationResponse.");
        utils.removeScriptVar(player, "conversation.emp_day_reb_colonel.branchId");
        return SCRIPT_CONTINUE;
    }
}
