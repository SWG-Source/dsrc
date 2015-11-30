package script.quest.township;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.groundquests;
import script.library.group;
import script.library.instance;
import script.library.stealth;
import script.library.sui;

public class ig88_scanner extends script.base_script
{
    public ig88_scanner()
    {
    }
    public static final int COUNTDOWN_TIME = 5;
    public static final String QUEST_NAME = "found_ig88_encounter";
    public static final String QUEST_TASK_NAME = "ig88_enter_compound";
    public static final String QUEST_SIGNAL = "ig88_compound_enter_signal";
    public static final String INSTANCE_NAME = "heroic_ig88";
    public static final string_id SCANNER_TEXT = new string_id("spam", "ig88_use_scanner");
    public int OnObjectMenuRequest(obj_id self, obj_id player, menu_info menuInfo) throws InterruptedException
    {
        if (isDead(player) || isIncapacitated(player))
        {
            return SCRIPT_CONTINUE;
        }
        int menu = menu = menuInfo.addRootMenu(menu_info_types.ITEM_USE, SCANNER_TEXT);
        menu_info_data menuInfoData = menuInfo.getMenuItemById(menu);
        if (menuInfoData != null)
        {
            menuInfoData.setServerNotify(true);
        }
        return SCRIPT_CONTINUE;
    }
    public int OnObjectMenuSelect(obj_id self, obj_id player, int item) throws InterruptedException
    {
        if (isDead(player) || isIncapacitated(player))
        {
            return SCRIPT_CONTINUE;
        }
        if (item == menu_info_types.ITEM_USE && (groundquests.isQuestActiveOrComplete(player, QUEST_NAME) || instance.isFlaggedForInstance(player, "heroic_ig88")))
        {
            string_id menuStringId = new string_id("spam", "ig88_use_scanner");
            String handler = "handleCountdownTimer";
            int startTime = 0;
            int range = 3;
            int flags = 0;
            flags |= sui.CD_EVENT_INCAPACITATE;
            flags |= sui.CD_EVENT_DAMAGED;
            stealth.testInvisNonCombatAction(player, self);
            int countdownSui = sui.smartCountdownTimerSUI(self, player, "quest_countdown_timer", menuStringId, startTime, COUNTDOWN_TIME, handler, range, flags);
        }
        else 
        {
            sendSystemMessage(player, new string_id("nexus", "retina_not_recognized"));
        }
        return SCRIPT_CONTINUE;
    }
    public int handleCountdownTimer(obj_id self, dictionary params) throws InterruptedException
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
        groundquests.sendSignal(player, QUEST_SIGNAL);
        instance.requestInstanceMovement(player, INSTANCE_NAME);
        return SCRIPT_CONTINUE;
    }
}
