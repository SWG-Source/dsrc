package script.systems.crafting.food.crafted_items;

import script.library.consumable;
import script.obj_id;

public class crafting_artisan_drink_food extends script.systems.crafting.food.crafted_items.crafting_artisan_food
{
    public crafting_artisan_drink_food()
    {
    }
    public void fillStomach(obj_id prototype, int filling) throws InterruptedException
    {
        int[] stomach = 
        {
            0,
            filling,
            0
        };
        setObjVar(prototype, consumable.VAR_CONSUMABLE_STOMACH_VALUES, stomach);
    }
}
