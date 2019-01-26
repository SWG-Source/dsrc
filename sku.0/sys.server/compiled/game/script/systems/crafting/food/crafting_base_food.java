package script.systems.crafting.food;

import script.*;
import script.library.bio_engineer;
import script.library.consumable;
import script.library.craftinglib;
import script.library.utils;

import java.util.Vector;

public class crafting_base_food extends script.systems.crafting.crafting_base
{
    public crafting_base_food()
    {
    }
    public static final String VERSION = "v1.00.00";
    public void calcAndSetPrototypeProperties(obj_id prototype, draft_schematic.attribute[] itemAttributes, dictionary craftingValuesDictionary) throws InterruptedException
    {
        for (draft_schematic.attribute itemAttribute : itemAttributes) {
            if (itemAttribute == null) {
                continue;
            }
            if (((itemAttribute.name).getAsciiId()).equals("filling")) {
                itemAttribute.currentValue = (itemAttribute.minValue + itemAttribute.maxValue) - itemAttribute.currentValue;
            }
        }
        super.calcAndSetPrototypeProperties(prototype, itemAttributes, craftingValuesDictionary);
    }
    public void calcAndSetPrototypeProperties(obj_id prototype, draft_schematic.attribute[] itemAttributes) throws InterruptedException
    {
        int filling = 0;
        int health_e = 0, health_dur = 0;
        int con_e = 0, con_dur = 0;
        int action_e = 0, action_dur = 0;
        int stam_e = 0, stam_dur = 0;
        obj_id self = getSelf();
        String root = craftinglib.COMPONENT_ATTRIBUTE_OBJVAR_NAME + "." + craftinglib.TISSUE_SKILL_MODS + ".";
        int[] effect_type = getIntArrayObjVar(self, root + craftinglib.TISSUE_SKILL_INDEX);
        int[] effect_mod = getIntArrayObjVar(self, root + craftinglib.TISSUE_SKILL_VALUE);
        obj_var_list attributeList = getObjVarList(self, "crafting_attributes");
        if (attributeList != null)
        {
            for (int i = 0; i < attributeList.getNumItems(); i++)
            {
                obj_var objVar = attributeList.getObjVar(i);
                String name = objVar.getName();
                int value = (int)objVar.getFloatData();
                switch (name) {
                    case "crafting:filling":
                        filling = value;
                        break;
                    case "crafting:health_e":
                        health_e = value;
                        break;
                    case "crafting:health_dur":
                        health_dur = value;
                        break;
                    case "crafting:con_e":
                        con_e = value;
                        break;
                    case "crafting:con_dur":
                        con_dur = value;
                        break;
                    case "crafting:action_e":
                        action_e = value;
                        break;
                    case "crafting:action_dur":
                        action_dur = value;
                        break;
                    case "crafting:stam_e":
                        stam_e = value;
                        break;
                    case "crafting:stam_dur":
                        stam_dur = value;
                        break;
                    case "crafting:quantity":
                        setCount(prototype, value);
                        break;
                }
            }
        }
        if (effect_type != null && effect_mod != null && effect_mod.length == effect_type.length)
        {
            for (int i = 0; i < effect_type.length; i++)
            {
                switch (effect_type[i])
                {
                    case bio_engineer.BIO_COMP_EFFECT_DURATION:
                    health_dur += (int)((health_dur * effect_mod[i]) / 100);
                    con_dur += (int)((con_dur * effect_mod[i]) / 100);
                    action_dur += (int)((action_dur * effect_mod[i]) / 100);
                    stam_dur += (int)((stam_dur * effect_mod[i]) / 100);
                    break;
                    case bio_engineer.BIO_COMP_EFFECT_FLAVOR:
                    health_e += (int)((health_e * effect_mod[i]) / 100);
                    con_e += (int)((con_e * effect_mod[i]) / 100);
                    action_e += (int)((action_e * effect_mod[i]) / 100);
                    stam_e += (int)((stam_e * effect_mod[i]) / 100);
                    break;
                    case bio_engineer.BIO_COMP_EFFECT_HEALTH_MOD:
                    health_e += effect_mod[i];
                    break;
                    case bio_engineer.BIO_COMP_EFFECT_CON_MOD:
                    con_e += effect_mod[i];
                    break;
                    case bio_engineer.BIO_COMP_EFFECT_ACTION_MOD:
                    action_e += effect_mod[i];
                    break;
                    case bio_engineer.BIO_COMP_EFFECT_STAM_MOD:
                    stam_e += effect_mod[i];
                    break;
                }
            }
        }
        Vector am = new Vector();
        am.setSize(0);
        if ((health_e != 0) && (health_dur != 0))
        {
            attrib_mod new_am = new attrib_mod(HEALTH, health_e, health_dur, 0.0f, 0.0f);
            am = utils.addElement(am, new_am);
        }
        if ((con_e != 0) && (con_dur != 0))
        {
            attrib_mod new_am = new attrib_mod(CONSTITUTION, con_e, con_dur, 0.0f, 0.0f);
            am = utils.addElement(am, new_am);
        }
        if ((action_e != 0) && (action_dur != 0))
        {
            attrib_mod new_am = new attrib_mod(ACTION, action_e, action_dur, 0.0f, 0.0f);
            am = utils.addElement(am, new_am);
        }
        if ((stam_e != 0) && (stam_dur != 0))
        {
            attrib_mod new_am = new attrib_mod(STAMINA, stam_e, stam_dur, 0.0f, 0.0f);
            am = utils.addElement(am, new_am);
        }
        if ((am != null) && (am.size() > 0))
        {
            setObjVar(prototype, consumable.VAR_CONSUMABLE_MODS, am);
        }
        fillStomach(prototype, filling);
    }
    public void fillStomach(obj_id prototype, int filling) throws InterruptedException
    {
    }
}
