package script.theme_park.wod;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.buff;
import script.library.collection;
import script.library.colors;
import script.library.create;
import script.library.factions;
import script.library.groundquests;
import script.library.holiday;
import script.library.money;
import script.library.pclib;
import script.library.pet_lib;
import script.library.prose;
import script.library.static_item;
import script.library.stealth;
import script.library.sui;
import script.library.trial;
import script.library.utils;

public class herb_quest_object extends script.base_script
{
    public herb_quest_object()
    {
    }
    public static final String QUEST_NAME = "wod_prologue_herb_gathering";
    public static final String QUEST_NAME_TWO = "wod_themepark_ns_herb_gathering";
    public static final String QUEST_NAME_THREE = "wod_themepark_herb_gathering";
    public static final String THEMEPARK = "theme_park_wod/messages";
    public static final string_id SID_NOT_WHILE_MOUNTED = new string_id(THEMEPARK, "gather_herb_not_while_mounted");
    public static final string_id SID_ZIP_BAR = new string_id(THEMEPARK, "herb_zip_bar");
    public static final string_id SID_ZIP_BAR_1 = new string_id(THEMEPARK, "herb_zip_bar_rinor");
    public static final string_id SID_ZIP_BAR_2 = new string_id(THEMEPARK, "herb_zip_bar_red");
    public static final string_id SID_ZIP_BAR_3 = new string_id(THEMEPARK, "herb_zip_bar_grey");
    public static final string_id SID_ZIP_BAR_4 = new string_id(THEMEPARK, "herb_zip_bar_ongmuel");
    public static final string_id SID_ZIP_BAR_5 = new string_id(THEMEPARK, "herb_zip_bar_lesset");
    public static final string_id SID_CHA_CHING = new string_id(THEMEPARK, "extra_credit_herbs");
    public static final string_id SID_MNU_USE = new string_id(THEMEPARK, "gather_herb");
    public static final string_id SID_NOT_SURE_HOW_DESTROY = new string_id(THEMEPARK, "herb_not_sure_what_to_do");
    public static final string_id SID_DONT_NEED_HERB = new string_id(THEMEPARK, "dont_need_herb");
    public static final string_id SID_ALRDY_COMPLETED_QUEST = new string_id(THEMEPARK, "herb_already_completed_quest");
    public static final int COUNTDOWN_TIMER = 3;
    public static final int CASH_AMOUNT = 25;
    public int OnObjectMenuRequest(obj_id self, obj_id player, menu_info mi) throws InterruptedException
    {
        if (!isValidId(self) || !exists(self))
        {
            return SCRIPT_CONTINUE;
        }
        if (isDead(player) || isIncapacitated(player))
        {
            return SCRIPT_CONTINUE;
        }
        if (!hasObjVar(self, "herb_type"))
        {
            return SCRIPT_CONTINUE;
        }
        int mnu2 = mi.addRootMenu(menu_info_types.ITEM_USE, SID_MNU_USE);
        return SCRIPT_CONTINUE;
    }
    public int OnObjectMenuSelect(obj_id self, obj_id player, int item) throws InterruptedException
    {
        if (!isValidId(self) || !exists(self))
        {
            return SCRIPT_CONTINUE;
        }
        if (item != menu_info_types.ITEM_USE)
        {
            return SCRIPT_CONTINUE;
        }
        if (!hasObjVar(self, "herb_type"))
        {
            return SCRIPT_CONTINUE;
        }
        if (isDead(player) || isIncapacitated(player))
        {
            return SCRIPT_CONTINUE;
        }
        if (pet_lib.isMounted(player))
        {
            sendSystemMessage(player, SID_NOT_WHILE_MOUNTED);
            return SCRIPT_CONTINUE;
        }
        if (!groundquests.isTaskActive(player, QUEST_NAME, "gatherHerbs") && !groundquests.isTaskActive(player, QUEST_NAME_TWO, "gatherHerbs") && !groundquests.isTaskActive(player, QUEST_NAME_THREE, "gatherHerbs"))
        {
            sendSystemMessage(player, SID_NOT_SURE_HOW_DESTROY);
            CustomerServiceLog("wod_themepark", "herb_quest_object.OnObjectMenuSelect() Player: " + player + " did not have the quest wod_*_herb_gathering needed for this collection. canister: (" + self);
            return SCRIPT_CONTINUE;
        }
        int herbType = getIntObjVar(self, "herb_type");
        if (herbType < 0)
        {
            CustomerServiceLog("wod_themepark", "herb_quest_object.OnObjectMenuSelect() herb: " + self + " did not have a valid herb type var");
            return SCRIPT_CONTINUE;
        }
        string_id zipBar = SID_ZIP_BAR;
        switch (herbType)
        {
            case 1:
            zipBar = SID_ZIP_BAR_1;
            break;
            case 2:
            zipBar = SID_ZIP_BAR_2;
            break;
            case 3:
            zipBar = SID_ZIP_BAR_3;
            break;
            case 4:
            zipBar = SID_ZIP_BAR_4;
            break;
            case 5:
            zipBar = SID_ZIP_BAR_5;
            break;
            default:
            break;
        }
        int startTime = 0;
        int range = 3;
        int flags = 0;
        flags |= sui.CD_EVENT_INCAPACITATE;
        flags |= sui.CD_EVENT_DAMAGED;
        stealth.testInvisNonCombatAction(player, self);
        int countdownSui = sui.smartCountdownTimerSUI(self, player, "quest_countdown_timer", zipBar, startTime, COUNTDOWN_TIMER, "handleObjectSwapTimer", range, flags);
        CustomerServiceLog("wod_themepark", "herb_quest_object.OnObjectMenuSelect() Player: " + player + " is destroying WoD Herb: " + self);
        return SCRIPT_CONTINUE;
    }
    public int handleObjectSwapTimer(obj_id self, dictionary params) throws InterruptedException
    {
        if (!isValidId(self) || !exists(self))
        {
            return SCRIPT_CONTINUE;
        }
        if (params == null || params.equals(""))
        {
            return SCRIPT_CONTINUE;
        }
        int pid = params.getInt("id");
        obj_id player = params.getObjId("player");
        if (!isIdValid(player) || !exists(player))
        {
            return SCRIPT_CONTINUE;
        }
        int bp = sui.getIntButtonPressed(params);
        if (bp == sui.BP_CANCEL)
        {
            detachScript(player, sui.COUNTDOWNTIMER_PLAYER_SCRIPT);
            CustomerServiceLog("wod_themepark", "herb_quest_object.handleObjectSwapTimer() cancelled");
            return SCRIPT_CONTINUE;
        }
        else if (bp == sui.BP_REVERT)
        {
            CustomerServiceLog("wod_themepark", "herb_quest_object.handleObjectSwapTimer() Player: " + player + " failed to destroy the canister because the player moved or was disrupted. Deposit: " + self);
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
            CustomerServiceLog("wod_themepark", "herb_quest_object.handleObjectSwapTimer() no countdowntimer");
            return SCRIPT_CONTINUE;
        }
        int test_pid = getIntObjVar(player, sui.COUNTDOWNTIMER_SUI_VAR);
        if (pid != test_pid)
        {
            CustomerServiceLog("wod_themepark", "herb_quest_object.handleObjectSwapTimer() pid != test_pid");
            return SCRIPT_CONTINUE;
        }
        forceCloseSUIPage(pid);
        detachScript(player, sui.COUNTDOWNTIMER_PLAYER_SCRIPT);
        if (!groundquests.isTaskActive(player, QUEST_NAME, "gatherHerbs") && !groundquests.isTaskActive(player, QUEST_NAME_TWO, "gatherHerbs") && !groundquests.isTaskActive(player, QUEST_NAME_THREE, "gatherHerbs"))
        {
            CustomerServiceLog("wod_themepark", "herb_quest_object.handleObjectSwapTimer() Player: " + player + " did not have the quest wod_*_herb_gathering needed for this collection. canister: (" + self);
            return SCRIPT_CONTINUE;
        }
        int herbType = getIntObjVar(self, "herb_type");
        if (herbType < 0)
        {
            CustomerServiceLog("wod_themepark", "herb_quest_object.OnObjectMenuSelect() herb: " + self + " did not have a valid herb type var");
            return SCRIPT_CONTINUE;
        }
        String questTask = getStringObjVar(self, "taskName");
        String questSignal = getStringObjVar(self, "signalName");
        if (questTask == null || questTask.length() <= 0)
        {
            CustomerServiceLog("wod_themepark", "herb_quest_object.OnObjectMenuSelect() Player: " + player + " could not attain a valid quest task off the herb object: " + self);
            return SCRIPT_CONTINUE;
        }
        if (questSignal == null || questSignal.length() <= 0)
        {
            CustomerServiceLog("wod_themepark", "herb_quest_object.OnObjectMenuSelect() Player: " + player + " could not attain a valid quest signal off the herb object: " + self);
            return SCRIPT_CONTINUE;
        }
        if (groundquests.isTaskActive(player, QUEST_NAME, questTask) || groundquests.isTaskActive(player, QUEST_NAME_TWO, questTask) || groundquests.isTaskActive(player, QUEST_NAME_THREE, questTask))
        {
            groundquests.sendSignal(player, questSignal);
        }
        else 
        {
            sendSystemMessage(player, SID_DONT_NEED_HERB);
            if (!utils.hasScriptVar(player, "commPlayerherbs"))
            {
                String npc = "object/mobile/dressed_gray_witch.iff";
                if (groundquests.isQuestActive(player, QUEST_NAME_TWO))
                {
                    npc = "object/mobile/dressed_dathomir_nightsister_herbalist.iff";
                }
                if (groundquests.isQuestActive(player, QUEST_NAME_THREE))
                {
                    npc = "object/mobile/dressed_dathomir_sing_mt_clan_herbalist.iff";
                }
                String sound = "sound/sys_comm_generic.snd";
                prose_package pp = new prose_package();
                prose.setStringId(pp, new string_id("theme_park_wod/messages", "extra_credit_herbs"));
                commPlayers(player, npc, sound, 10f, player, pp);
                utils.setScriptVar(player, "commPlayerherbs", true);
            }
            money.bankTo(money.ACCT_NEW_PLAYER_QUESTS, player, CASH_AMOUNT);
            play2dNonLoopingSound(player, groundquests.MUSIC_QUEST_RECEIVED_CREDITS);
        }
        messageTo(self, "destroySelf", null, 0, false);
        return SCRIPT_CONTINUE;
    }
    public int destroySelf(obj_id self, dictionary params) throws InterruptedException
    {
        if (!hasObjVar(self, "mySpawner"))
        {
            CustomerServiceLog("wod_themepark", "herb_quest_object.destroySelf() The herb: " + self + " did not have a parent objvar. The parent cannot be told to destroy self.");
        }
        obj_id parent = getObjIdObjVar(self, "mySpawner");
        if (!isValidId(parent))
        {
            CustomerServiceLog("wod_themepark", "herb_quest_object.destroySelf() The herb: " + self + " did not have a valid parent objvar. The parent may already be destroyed.");
        }
        messageTo(parent, "destroySelf", null, 0, false);
        destroyObject(self);
        return SCRIPT_CONTINUE;
    }
}
