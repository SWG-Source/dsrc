package script.item.dna;

import script.dictionary;
import script.library.static_item;
import script.library.utils;
import script.obj_id;

public class vorpal_durni_dna extends script.base_script
{
    public vorpal_durni_dna()
    {
    }
    public static final String VORPAL_DURNI_DNA_LOOT_ITEM = "item_vorpal_durni_dna";
    public static final int VORPAL_DURNI_DNA_LOOT_CHANCE = 2;


    public int aiCorpsePrepared(obj_id self, dictionary params) throws InterruptedException
    {
        int chanceDna = rand(1, 100);
        if (chanceDna > VORPAL_DURNI_DNA_LOOT_CHANCE)
        {
            return SCRIPT_CONTINUE;
        }
        obj_id inv = utils.getInventoryContainer(self);
        obj_id dna = static_item.createNewItemFunction(VORPAL_DURNI_DNA_LOOT_ITEM, inv);
        return SCRIPT_CONTINUE;
    }
}
