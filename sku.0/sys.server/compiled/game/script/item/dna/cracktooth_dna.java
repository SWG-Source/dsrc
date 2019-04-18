package script.item.dna;

import script.dictionary;
import script.library.static_item;
import script.library.utils;
import script.obj_id;

public class cracktooth_dna extends script.base_script
{
    public cracktooth_dna()
    {
    }
    public static final String CRACKTOOTH_NARGLATCH_DNA_LOOT_ITEM = "item_cs_dna_mutated_narglatch";
    public static final int CRACKTOOTH_NARGLATCH_DNA_LOOT_CHANCE = 15;


    public int aiCorpsePrepared(obj_id self, dictionary params) throws InterruptedException
    {
        int chanceDna = rand(1, 100);
        if (chanceDna > CRACKTOOTH_NARGLATCH_DNA_LOOT_CHANCE)
        {
            return SCRIPT_CONTINUE;
        }
        obj_id inv = utils.getInventoryContainer(self);
        obj_id dna = static_item.createNewItemFunction(CRACKTOOTH_NARGLATCH_DNA_LOOT_ITEM, inv);
        return SCRIPT_CONTINUE;
    }
}
