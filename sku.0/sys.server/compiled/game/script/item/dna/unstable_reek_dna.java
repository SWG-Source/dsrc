package script.item.dna;

import script.dictionary;
import script.library.static_item;
import script.library.utils;
import script.obj_id;

public class unstable_reek_dna extends script.base_script
{
    public unstable_reek_dna()
    {
    }
    public static final String UNSTABLE_REEK_DNA_LOOT_ITEM = "item_cs_dna_reek";
    public static final int UNSTABLE_REEK_DNA_LOOT_CHANCE = 15;


    public int aiCorpsePrepared(obj_id self, dictionary params) throws InterruptedException
    {
        int chanceDna = rand(1, 100);
        if (chanceDna > UNSTABLE_REEK_DNA_LOOT_CHANCE)
        {
            return SCRIPT_CONTINUE;
        }
        obj_id inv = utils.getInventoryContainer(self);
        obj_id dna = static_item.createNewItemFunction(UNSTABLE_REEK_DNA_LOOT_ITEM, inv);
        return SCRIPT_CONTINUE;
    }
}
