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
    private static final int SHOCK_COLLAR_LOOT_CHANCE = 5;

    public int aiCorpsePrepared(obj_id self, dictionary params) throws InterruptedException
    {
        // Roll to see if we should drop the shock collar or not.
        if (rand(1, 100) > SHOCK_COLLAR_LOOT_CHANCE)
        {
            return SCRIPT_CONTINUE;
        }
        // Create the shock collar and put it in the dead creature's inventory so it can be looted.
        static_item.createNewItemFunction("item_tcg_loot_reward_series6_shock_collar", utils.getInventoryContainer(self));
        return SCRIPT_CONTINUE;
    }
}
