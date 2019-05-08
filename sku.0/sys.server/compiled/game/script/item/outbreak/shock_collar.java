package script.item.outbreak;

import script.dictionary;
import script.library.static_item;
import script.library.utils;
import script.obj_id;

public class shock_collar extends script.base_script
{
    public shock_collar()
    {
    }
    public static final String SHOCK_COLLAR_LOOT_ITEM = "item_tcg_loot_reward_series6_shock_collar";
    public static final int SHOCK_COLLAR_LOOT_CHANCE = 5;


    public int aiCorpsePrepared(obj_id self, dictionary params) throws InterruptedException
    {
        int chanceDna = rand(1, 100);
        if (chanceDna > SHOCK_COLLAR_LOOT_CHANCE)
        {
            return SCRIPT_CONTINUE;
        }
        obj_id inv = utils.getInventoryContainer(self);
        obj_id dna = static_item.createNewItemFunction(SHOCK_COLLAR_LOOT_ITEM, inv);
        return SCRIPT_CONTINUE;
    }
}
