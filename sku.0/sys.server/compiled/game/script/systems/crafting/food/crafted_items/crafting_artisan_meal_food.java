package script.systems.crafting.food.crafted_items;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.utils;
import script.library.craftinglib;
import script.library.consumable;

public class crafting_artisan_meal_food extends script.systems.crafting.food.crafted_items.crafting_artisan_food
{
    public crafting_artisan_meal_food()
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
