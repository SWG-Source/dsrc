package script.systems.crafting.droid;

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
import script.library.healing;

public class crafting_base_droid_repair extends script.systems.crafting.crafting_base
{
    public crafting_base_droid_repair()
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
                else if (((itemAttributes[i].name).getAsciiId()).equals("energy"))
                {
                    setObjVar(prototype, "consumable.energy", (int)itemAttributes[i].currentValue);
                }
                else 
                {
                    setObjVar(prototype, craftinglib.COMPONENT_ATTRIBUTE_OBJVAR_NAME + "." + (itemAttributes[i].name).getAsciiId(), itemAttributes[i].currentValue);
                }
            }
        }
        attrib_mod[] am = createDroidMedicineMod(prototype, tempPower);
        setObjVar(prototype, consumable.VAR_CONSUMABLE_MODS, am);
        int[] stomach = 
        {
            0,
            0,
            0
        };
        setObjVar(prototype, consumable.VAR_CONSUMABLE_STOMACH_VALUES, stomach);
    }
    public attrib_mod[] createDroidMedicineMod(obj_id prototype, int power) throws InterruptedException
    {
        attrib_mod[] am = new attrib_mod[1];
        int value = power;
        float attack = healing.AM_HEAL_WOUND;
        float decay = 0.0f;
        if (hasObjVar(prototype, "consumable.energy"))
        {
            value = getIntObjVar(prototype, "consumable.energy");
            attack = 0.0f;
            decay = MOD_POOL;
        }
        am[0] = utils.createAttribMod(0, value, 0.0f, attack, decay);
        return am;
    }
}
