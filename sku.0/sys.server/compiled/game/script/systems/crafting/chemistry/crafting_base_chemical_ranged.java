package script.systems.crafting.chemistry;

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

public class crafting_base_chemical_ranged extends script.systems.crafting.crafting_base
{
    public crafting_base_chemical_ranged()
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
                    tempPower = (int)(itemAttributes[i].currentValue);
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
                else 
                {
                    setObjVar(prototype, craftinglib.COMPONENT_ATTRIBUTE_OBJVAR_NAME + "." + (itemAttributes[i].name).getAsciiId(), itemAttributes[i].currentValue);
                }
            }
        }
        attrib_mod[] am = createHealDamageMedicineMod(tempPower);
        setObjVar(prototype, consumable.VAR_CONSUMABLE_MODS, am);
        int[] stomach = 
        {
            0,
            0,
            0
        };
        setObjVar(prototype, consumable.VAR_CONSUMABLE_STOMACH_VALUES, stomach);
        String[] skill_mod = 
        {
            "combat_healing_ability"
        };
        setObjVar(prototype, consumable.VAR_SKILL_MOD_REQUIRED, skill_mod);
        setObjVar(prototype, consumable.VAR_SKILL_MOD_MIN, skill_value);
    }
    public attrib_mod[] createHealDamageMedicineMod(int power) throws InterruptedException
    {
        attrib_mod[] am = new attrib_mod[2];
        for (int i = 0; i < 2; i++)
        {
            am[i] = utils.createHealDamageAttribMod(i * 2, power);
        }
        return am;
    }
}
