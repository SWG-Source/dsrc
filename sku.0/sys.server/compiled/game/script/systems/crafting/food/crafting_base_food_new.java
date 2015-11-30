package script.systems.crafting.food;

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
import script.library.player_stomach;
import script.library.bio_engineer;
import script.library.debug;

public class crafting_base_food_new extends script.systems.crafting.crafting_base
{
    public crafting_base_food_new()
    {
    }
    public static final String VERSION = "v1.00.00";
    public static final String FOOD_DATA = "datatables/food/food_data.iff";
    public void calcAndSetPrototypeProperties(obj_id prototype, draft_schematic.attribute[] itemAttributes, dictionary craftingValuesDictionary) throws InterruptedException
    {
        for (int i = 0; i < itemAttributes.length; ++i)
        {
            if (itemAttributes[i] == null)
            {
                continue;
            }
            if (((itemAttributes[i].name).getAsciiId()).equals("filling"))
            {
                itemAttributes[i].currentValue = (itemAttributes[i].minValue + itemAttributes[i].maxValue) - itemAttributes[i].currentValue;
            }
        }
        super.calcAndSetPrototypeProperties(prototype, itemAttributes, craftingValuesDictionary);
    }
    public void calcAndSetPrototypeProperties(obj_id prototype, draft_schematic.attribute[] itemAttributes) throws InterruptedException
    {
        String template = getTemplateName(prototype);
        String[] foods = dataTableGetStringColumn(FOOD_DATA, "TEMPLATE");
        int food_index = 0;
        for (int i = 0; i < foods.length; i++)
        {
            if (template.equals("object/tangible/" + foods[i]))
            {
                food_index = i;
                break;
            }
        }
        int quantity = 0;
        float quantity_bonus = 1.f;
        int filling = 0;
        float effectiveness = 1;
        float duration = 1;
        int race_restriction = -1;
        int stomach = 0;
        for (int i = 0; i < itemAttributes.length; ++i)
        {
            if (itemAttributes[i] == null)
            {
                continue;
            }
            if (!calcAndSetPrototypeProperty(prototype, itemAttributes[i]))
            {
                if (((itemAttributes[i].name).getAsciiId()).equals("quantity"))
                {
                    int quantity_base = (int)itemAttributes[i].currentValue;
                    float quantity_mod = quantity_base / 100.f;
                    int quantity_food = dataTableGetInt(FOOD_DATA, food_index, "QUANTITY");
                    quantity = (int)(quantity_mod * quantity_food);
                }
                if (((itemAttributes[i].name).getAsciiId()).equals("quantity_bonus"))
                {
                    int quantity_bonus_base = (int)itemAttributes[i].currentValue;
                    switch (quantity_bonus_base)
                    {
                        case 1:
                        quantity_bonus = 1.f;
                        break;
                        case 2:
                        quantity_bonus = 1.5f;
                        break;
                        case 3:
                        quantity_bonus = 2.25f;
                        break;
                        case 4:
                        quantity_bonus = 3.f;
                        break;
                    }
                }
                if (((itemAttributes[i].name).getAsciiId()).equals("filling"))
                {
                    int filling_base = (int)itemAttributes[i].currentValue;
                    float filling_mod = filling_base / 100.f;
                    int filling_food = dataTableGetInt(FOOD_DATA, food_index, "FILLING");
                    filling = (int)(filling_mod * filling_food);
                }
                if (((itemAttributes[i].name).getAsciiId()).equals("nutrition"))
                {
                    int effectiveness_base = (int)itemAttributes[i].currentValue;
                    effectiveness = effectiveness_base / 100.f;
                }
                if (((itemAttributes[i].name).getAsciiId()).equals("flavor"))
                {
                    int duration_base = (int)itemAttributes[i].currentValue;
                    duration = duration_base / 100.f;
                }
                if (((itemAttributes[i].name).getAsciiId()).equals("race_restriction"))
                {
                    race_restriction = (int)itemAttributes[i].currentValue;
                }
                if (((itemAttributes[i].name).getAsciiId()).equals("stomach"))
                {
                    stomach = (int)itemAttributes[i].currentValue;
                }
            }
        }
        quantity = (int)(quantity * quantity_bonus);
        obj_id self = getSelf();
        if (hasObjVar(self, "crafting_components.additive.add_faction"))
        {
            setObjVar(prototype, "faction", getIntObjVar(self, "crafting_components.additive.add_faction"));
        }
        if (hasObjVar(self, "crafting_components.additive.add_quantity"))
        {
            float quantity_additive_mod = getIntObjVar(self, "crafting_components.additive.add_quantity") / 100.f;
            int new_quantity = (int)(quantity + quantity * quantity_additive_mod);
            quantity = new_quantity;
        }
        if (hasObjVar(self, "crafting_components.additive.add_filling"))
        {
            int filling_additive_mod = getIntObjVar(self, "crafting_components.additive.add_filling");
            filling = (int)(filling - filling * (filling_additive_mod / 100.f));
            if (filling <= 0)
            {
                filling = 1;
            }
        }
        if (hasObjVar(self, "crafting_components.additive.add_nutrition"))
        {
            int nutrition_additive_mod = getIntObjVar(self, "crafting_components.additive.add_nutrition");
            effectiveness = effectiveness + effectiveness * (nutrition_additive_mod / 100.f);
        }
        if (hasObjVar(self, "crafting_components.additive.add_flavor"))
        {
            int flavor_additive_mod = getIntObjVar(self, "crafting_components.additive.add_flavor");
            duration = duration + duration * (flavor_additive_mod / 100.f);
        }
        setCount(prototype, quantity);
        if (filling > 100)
        {
            filling = 100;
        }
        fillStomach(prototype, filling, stomach);
        if (race_restriction != -1)
        {
            setObjVar(prototype, "race_restriction", race_restriction);
        }
        String buff_name = dataTableGetString(FOOD_DATA, food_index, "BUFF_NAME");
        int pet_only = dataTableGetInt(FOOD_DATA, food_index, "PET_ONLY");
        if (buff_name == null || buff_name.equals(""))
        {
            setCount(prototype, 0);
            return;
        }
        else 
        {
            setObjVar(prototype, "buff_name", buff_name);
        }
        if (pet_only == 1)
        {
            setObjVar(prototype, "pet_only", 1);
        }
        setObjVar(prototype, "effectiveness", effectiveness);
        setObjVar(prototype, "duration", duration);
    }
    public void fillStomach(obj_id prototype, int filling, int idx) throws InterruptedException
    {
        int[] stomach = 
        {
            0,
            0,
            0
        };
        stomach[idx] = filling;
        setObjVar(prototype, "filling", stomach);
    }
}
