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

public class crafting_wound_chemical extends script.systems.crafting.crafting_base
{
    public crafting_wound_chemical()
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
                else 
                {
                    setObjVar(prototype, craftinglib.COMPONENT_ATTRIBUTE_OBJVAR_NAME + "." + (itemAttributes[i].name).getAsciiId(), itemAttributes[i].currentValue);
                }
            }
        }
        String template_name = getTemplateName(prototype);
        int wound_type = -1;
        if (template_name.equals("object/tangible/medicine/medpack_wound_health.iff"))
        {
            wound_type = 0;
        }
        if (template_name.equals("object/tangible/medicine/medpack_wound_constitution.iff"))
        {
            wound_type = 1;
        }
        if (template_name.equals("object/tangible/medicine/medpack_wound_action.iff"))
        {
            wound_type = 2;
        }
        if (template_name.equals("object/tangible/medicine/medpack_wound_stamina.iff"))
        {
            wound_type = 3;
        }
        int power = rand(25, 150);
        int charges = rand(1, 3);
        if (wound_type == -1)
        {
            wound_type = rand(0, NUM_ATTRIBUTES - NUM_ATTRIBUTES_PER_GROUP - 1);
        }
        attrib_mod[] am = new attrib_mod[1];
        am[0] = utils.createHealWoundAttribMod(wound_type, tempPower);
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
