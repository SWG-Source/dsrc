package script.theme_park.wod;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.create;
import script.library.utils;
import script.library.ai_lib;
import script.library.money;
import script.library.loot;
import script.library.jedi;
import script.library.static_item;

public class wod_spider_queen_death extends script.base_script
{
    public wod_spider_queen_death()
    {
    }
    public static final String NS_BACKPACK_SCHEMATIC = "item_wod_bossloot_ns_backpack";
    public static final String SMC_BACKPACK_SCHEMATIC = "item_wod_bossloot_smc_backpack";
    public int aiCorpsePrepared(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id inv = utils.getInventoryContainer(self);
        int lootChance = rand(1, 100);
        if (lootChance <= 15)
        {
            static_item.createNewItemFunction(NS_BACKPACK_SCHEMATIC, inv);
        }
        if (lootChance > 15 && lootChance <= 30)
        {
            static_item.createNewItemFunction(SMC_BACKPACK_SCHEMATIC, inv);
        }
        return SCRIPT_CONTINUE;
    }
}
