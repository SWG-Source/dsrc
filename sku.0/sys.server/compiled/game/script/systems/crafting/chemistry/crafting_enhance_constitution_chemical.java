package script.systems.crafting.chemistry;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.craftinglib;
import script.library.utils;
import script.library.healing;
import script.library.consumable;
import script.library.player_stomach;

public class crafting_enhance_constitution_chemical extends script.systems.crafting.crafting_base
{
    public crafting_enhance_constitution_chemical()
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
        int tempDuration = 0;
        int tempCharges = 0;
        int tempSkill_req = 0;
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
                    tempCharges = (int)(itemAttributes[i].currentValue);
                }
                else if (((itemAttributes[i].name).getAsciiId()).equals("skillModMin"))
                {
                    tempSkill_req = (int)((itemAttributes[i].maxValue + itemAttributes[i].minValue) - itemAttributes[i].currentValue);
                }
                else if (((itemAttributes[i].name).getAsciiId()).equals("duration"))
                {
                    tempDuration = (int)(itemAttributes[i].currentValue);
                }
                else 
                {
                    setObjVar(prototype, craftinglib.COMPONENT_ATTRIBUTE_OBJVAR_NAME + "." + (itemAttributes[i].name).getAsciiId(), itemAttributes[i].currentValue);
                }
            }
        }
        int buff_type = 2;
        if (buff_type == -1)
        {
            buff_type = rand(0, 5);
        }
        attrib_mod[] am = new attrib_mod[1];
        am[0] = new attrib_mod(buff_type, tempPower, tempDuration, healing.VAR_BUFF_MOD_ATTACK, healing.VAR_BUFF_MOD_DECAY);
        setObjVar(prototype, consumable.VAR_CONSUMABLE_MODS, am);
        setObjVar(prototype, consumable.VAR_CONSUMABLE_MEDICINE, true);
        setCount(prototype, tempCharges);
        int[] stomach = 
        {
            0,
            0,
            0
        };
        setObjVar(prototype, consumable.VAR_CONSUMABLE_STOMACH_VALUES, stomach);
        String[] skill_mod = 
        {
            "healing_ability"
        };
        setObjVar(prototype, consumable.VAR_SKILL_MOD_REQUIRED, skill_mod);
        int[] skill_mod_value = 
        {
            tempSkill_req
        };
        setObjVar(prototype, consumable.VAR_SKILL_MOD_MIN, skill_mod_value);
    }
}
