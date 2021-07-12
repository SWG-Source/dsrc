package script.theme_park.wod;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.groundquests;

public class rubina_basket_ns extends script.base_script
{
    public rubina_basket_ns()
    {
    }
    public static final string_id SID_MNU_USE = new string_id("pet/pet_menu", "menu_store");
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
        mi.addRootMenu(menu_info_types.ITEM_USE, SID_MNU_USE);
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
        if (isDead(player) || isIncapacitated(player))
        {
            return SCRIPT_CONTINUE;
        }
        if (!(groundquests.isTaskActive(player, "quest/wod_prologue_kill_spider_clan", "useNightsisterBasketSpider") || groundquests.isTaskActive(player, "quest/wod_prologue_kill_rancor", "nightsisterBasketRancor") || groundquests.isTaskActive(player, "quest/wod_prologue_herb_gathering", "storeHerbsNightsister")))
        {
            return SCRIPT_CONTINUE;
        }
        if (groundquests.isTaskActive(player, "quest/wod_prologue_kill_spider_clan", "useNightsisterBasketSpider"))
        {
            groundquests.sendSignal(player, "useNightsisterBasketSpider");
        }
        if (groundquests.isTaskActive(player, "quest/wod_prologue_kill_rancor", "nightsisterBasketRancor"))
        {
            groundquests.sendSignal(player, "useNightsisterBasketRancor");
        }
        if (groundquests.isTaskActive(player, "quest/wod_prologue_herb_gathering", "storeHerbsNightsister"))
        {
            groundquests.sendSignal(player, "storeHerbsNightsister");
        }
        return SCRIPT_CONTINUE;
    }
}
