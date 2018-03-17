package script.quest.hero_of_tatooine;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.groundquests;
import script.library.stealth;
import script.library.sui;
import script.library.utils;

public class search_lair_squill extends script.base_script
{
    public search_lair_squill()
    {
    }
    public static final string_id SEARCH_ITEM = new string_id("quest/hero_of_tatooine/system_messages", "search");
    public static final string_id ALREADY_SEARCHED_MSG = new string_id("quest/hero_of_tatooine/system_messages", "already_search");
    public static final string_id DEFAULT_RECEIVE_MSG = new string_id("quest/hero_of_tatooine/system_messages", "receive");
    public static final string_id INVENTORY_FULL_MSG = new string_id("quest/hero_of_tatooine/system_messages", "inventory");
    public static final string_id COUNTDOWN_TIMER_SEARCH = new string_id("quest/hero_of_tatooine/system_messages", "countdown_timer_search");
    public static final String VAR_ITEM_REQUEST_BASE = "item_request";
    public static final String VAR_ITEM_REQUEST_CATEGORY = VAR_ITEM_REQUEST_BASE + ".category";
    public int OnObjectMenuRequest(obj_id self, obj_id player, menu_info mi) throws InterruptedException
    {
        if (canSearch(self, player))
        {
            int menuOption = mi.addRootMenu(menu_info_types.ITEM_USE, SEARCH_ITEM);
        }
        return SCRIPT_CONTINUE;
    }
    public int OnObjectMenuSelect(obj_id self, obj_id player, int item) throws InterruptedException
    {
        if (item == menu_info_types.ITEM_USE)
        {
            if (canSearch(self, player))
            {
                if (utils.playerHasItemByTemplateInBankOrInventory(player, "object/tangible/loot/quest/hero_of_tatooine/squill_skull.iff"))
                {
                    sendSystemMessage(player, ALREADY_SEARCHED_MSG);
                    return SCRIPT_CONTINUE;
                }
                String handler = "handleHeroCountdownTimer";
                int startTime = 0;
                int range = 3;
                int flags = 0;
                flags |= sui.CD_EVENT_INCAPACITATE;
                flags |= sui.CD_EVENT_DAMAGED;
                stealth.testInvisNonCombatAction(player, self);
                int countdownSui = sui.smartCountdownTimerSUI(self, player, "quest_countdown_timer", COUNTDOWN_TIMER_SEARCH, startTime, 6, handler, range, flags);
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int handleHeroCountdownTimer(obj_id self, dictionary params) throws InterruptedException
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
        if (canSearch(self, player))
        {
            if (utils.playerHasItemByTemplateInBankOrInventory(player, "object/tangible/loot/quest/hero_of_tatooine/squill_skull.iff"))
            {
                sendSystemMessage(player, ALREADY_SEARCHED_MSG);
                return SCRIPT_CONTINUE;
            }
            String template = "object/tangible/loot/quest/hero_of_tatooine/squill_skull.iff";
            if ((template != null) && (!template.equals("")))
            {
                obj_id objectReceived = createObjectInInventoryAllowOverload(template, player);
                if (isIdValid(objectReceived))
                {
                    sendSystemMessage(player, DEFAULT_RECEIVE_MSG);
                    if (groundquests.playerNeedsToRetrieveThisItem(player, self))
                    {
                        dictionary webster = new dictionary();
                        webster.put("source", self);
                        messageTo(player, "questRetrieveItemObjectFound", webster, 0, false);
                    }
                }
            }
        }
        return SCRIPT_CONTINUE;
    }
    public boolean canSearch(obj_id self, obj_id player) throws InterruptedException
    {
        if (hasObjVar(player, "quest.hero_of_tatooine.task") || groundquests.isTaskActive(player, "quest_hero_of_tatooine_main", "hero_of_tatooine_main_skull"))
        {
            return true;
        }
        return false;
    }
}
