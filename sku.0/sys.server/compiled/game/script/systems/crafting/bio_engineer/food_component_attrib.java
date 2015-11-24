package script.systems.crafting.bio_engineer;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.craftinglib;
import script.library.bio_engineer;
import script.library.utils;

public class food_component_attrib extends script.base_script
{
    public food_component_attrib()
    {
    }
    public int OnGetAttributes(obj_id self, obj_id player, String[] names, String[] attribs) throws InterruptedException
    {
        int i = 0;
        if (hasObjVar(self, craftinglib.COMPONENT_ATTRIBUTE_OBJVAR_NAME + ".add_faction"))
        {
            int faction = getIntObjVar(self, craftinglib.COMPONENT_ATTRIBUTE_OBJVAR_NAME + ".add_faction");
            names[i] = "faction_restriction";
            if (faction == 1)
            {
                attribs[i] = "Rebel";
            }
            else if (faction == 2)
            {
                attribs[i] = "Imperial";
            }
            else 
            {
                attribs[i] = "None";
            }
            i++;
        }
        if (hasObjVar(self, craftinglib.COMPONENT_ATTRIBUTE_OBJVAR_NAME + ".add_filling"))
        {
            names[i] = "filling_bonus";
            attribs[i] = Integer.toString(getIntObjVar(self, craftinglib.COMPONENT_ATTRIBUTE_OBJVAR_NAME + ".add_filling"));
        }
        if (hasObjVar(self, craftinglib.COMPONENT_ATTRIBUTE_OBJVAR_NAME + ".add_flavor"))
        {
            names[i] = "flavor_bonus";
            attribs[i] = Integer.toString(getIntObjVar(self, craftinglib.COMPONENT_ATTRIBUTE_OBJVAR_NAME + ".add_flavor"));
        }
        if (hasObjVar(self, craftinglib.COMPONENT_ATTRIBUTE_OBJVAR_NAME + ".add_nutrition"))
        {
            names[i] = "nutrition_bonus";
            attribs[i] = Integer.toString(getIntObjVar(self, craftinglib.COMPONENT_ATTRIBUTE_OBJVAR_NAME + ".add_nutrition"));
        }
        if (hasObjVar(self, craftinglib.COMPONENT_ATTRIBUTE_OBJVAR_NAME + ".add_quantity"))
        {
            names[i] = "quantity_bonus";
            attribs[i] = Integer.toString(getIntObjVar(self, craftinglib.COMPONENT_ATTRIBUTE_OBJVAR_NAME + ".add_quantity"));
        }
        return SCRIPT_CONTINUE;
    }
}
