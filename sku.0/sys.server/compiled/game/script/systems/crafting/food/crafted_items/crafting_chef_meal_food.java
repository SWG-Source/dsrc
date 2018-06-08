package script.systems.crafting.food.crafted_items;

import script.library.consumable;
import script.obj_id;

public class crafting_chef_meal_food extends script.systems.crafting.food.crafted_items.crafting_chef_food
{
    public crafting_chef_meal_food()
    {
    }
    public void fillStomach(obj_id prototype, int filling) throws InterruptedException
    {
        int[] stomach = 
        {
            filling,
            0,
            0
        };
        setObjVar(prototype, consumable.VAR_CONSUMABLE_STOMACH_VALUES, stomach);
    }
}
