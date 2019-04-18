package script.item.dna;

import script.dictionary;
import script.library.static_item;
import script.library.utils;
import script.obj_id;

public class spider_consort_dna extends script.base_script
{
    public spider_consort_dna()
    {
    }
    public static final String SPIDER_CONSORT_DNA_LOOT_ITEM = "item_spiderclan_consort_dna";
    public static final int SPIDER_CONSORT_DNA_LOOT_CHANCE = 15;


    public int aiCorpsePrepared(obj_id self, dictionary params) throws InterruptedException
    {
        int chanceDna = rand(1, 100);
        if (chanceDna > SPIDER_CONSORT_DNA_LOOT_CHANCE)
        {
            return SCRIPT_CONTINUE;
        }
        obj_id inv = utils.getInventoryContainer(self);
        obj_id dna = static_item.createNewItemFunction(SPIDER_CONSORT_DNA_LOOT_ITEM, inv);
        return SCRIPT_CONTINUE;
    }
}
