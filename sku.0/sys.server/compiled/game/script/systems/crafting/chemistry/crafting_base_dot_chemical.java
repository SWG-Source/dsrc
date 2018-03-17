package script.systems.crafting.chemistry;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.craftinglib;
import script.library.consumable;
import script.library.player_stomach;

public class crafting_base_dot_chemical extends script.systems.crafting.crafting_base
{
    public crafting_base_dot_chemical()
    {
    }
    public static final String VERSION = "v1.00.00";
    public void calcAndSetPrototypeProperties(obj_id prototype, draft_schematic.attribute[] itemAttributes, dictionary craftingValuesDictionary) throws InterruptedException
    {
        super.calcAndSetPrototypeProperties(prototype, itemAttributes, craftingValuesDictionary);
    }
    public void calcAndSetPrototypeProperties(obj_id prototype, draft_schematic.attribute[] itemAttributes) throws InterruptedException
    {
        int tempPower = 0;
        int[] skill_value = 
        {
            0
        };
        debugServerConsoleMsg(null, "Beginning assembly-phase prototype property setting");
        for (int i = 0; i < itemAttributes.length; ++i)
        {
            if (itemAttributes[i] == null)
            {
                continue;
            }
            if (!calcAndSetPrototypeProperty(prototype, itemAttributes[i]))
            {
                if (((itemAttributes[i].name).getAsciiId()).equals("power"))
                {
                    setObjVar(prototype, "healing.dot_power", (int)itemAttributes[i].currentValue);
                }
                else if (((itemAttributes[i].name).getAsciiId()).equals("charges"))
                {
                    setCount(prototype, (int)itemAttributes[i].currentValue);
                }
                else if (((itemAttributes[i].name).getAsciiId()).equals("skillModMin"))
                {
                    skill_value[0] = (int)((itemAttributes[i].maxValue + itemAttributes[i].minValue) - itemAttributes[i].currentValue);
                }
                else if (((itemAttributes[i].name).getAsciiId()).equals("range"))
                {
                    int range = (int)itemAttributes[i].currentValue;
                    if (range > 48)
                    {
                        range = 48;
                    }
                    setObjVar(prototype, "healing.range", range);
                }
                else if (((itemAttributes[i].name).getAsciiId()).equals("area"))
                {
                    setObjVar(prototype, "healing.area", (int)itemAttributes[i].currentValue);
                }
                else if (((itemAttributes[i].name).getAsciiId()).equals("potency"))
                {
                    setObjVar(prototype, "healing.dot_potency", (int)itemAttributes[i].currentValue);
                }
                else if (((itemAttributes[i].name).getAsciiId()).equals("duration"))
                {
                    setObjVar(prototype, "healing.dot_duration", (int)itemAttributes[i].currentValue);
                }
                else 
                {
                    setObjVar(prototype, craftinglib.COMPONENT_ATTRIBUTE_OBJVAR_NAME + "." + (itemAttributes[i].name).getAsciiId(), itemAttributes[i].currentValue);
                }
            }
        }
        String[] skill_mod = 
        {
            "combat_healing_ability"
        };
        setObjVar(prototype, consumable.VAR_SKILL_MOD_REQUIRED, skill_mod);
        setObjVar(prototype, consumable.VAR_SKILL_MOD_MIN, skill_value);
    }
}
