package script.theme_park.wod;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.static_item;
import script.library.utils;

public class wod_spiderconsort_dna extends script.base_script
{
    public wod_spiderconsort_dna()
    {
    }
    public static final String DNA_LOOT_ITEM = "item_spiderclan_consort_dna";
    public static final int DNA_LOOT_CHANCE = 15;
    public int aiCorpsePrepared(obj_id self, dictionary params) throws InterruptedException
    {
        int chanceDna = rand(1, 100);
        if (chanceDna > DNA_LOOT_CHANCE)
        {
            return SCRIPT_CONTINUE;
        }
        obj_id inv = utils.getInventoryContainer(self);
        obj_id dna = static_item.createNewItemFunction(DNA_LOOT_ITEM, inv);
        return SCRIPT_CONTINUE;
    }
}
