package script.systems.crafting.camp;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.utils;
import script.library.craftinglib;

public class crafting_camp_base extends script.systems.crafting.crafting_base
{
    public crafting_camp_base()
    {
    }
    public static final String VERSION = "v1.00.00";
    public void calcAndSetPrototypeProperties(obj_id prototype, draft_schematic.attribute[] itemAttributes, dictionary craftingValuesDictionary) throws InterruptedException
    {
        super.calcAndSetPrototypeProperties(prototype, itemAttributes, craftingValuesDictionary);
    }
    public void calcAndSetPrototypeProperties(obj_id prototype, draft_schematic.attribute[] itemAttributes) throws InterruptedException
    {
        debugServerConsoleMsg(null, "Beginning assembly-phase prototype property setting");
        for (int i = 0; i < itemAttributes.length; ++i)
        {
            if (itemAttributes[i] == null)
            {
                continue;
            }
            if (!calcAndSetPrototypeProperty(prototype, itemAttributes[i]))
            {
                if (itemAttributes[i].currentValue == 0.0f)
                {
                    continue;
                }
                if (((itemAttributes[i].name).getAsciiId()).equals("lifetime"))
                {
                    setObjVar(prototype, "modules.lifetime", itemAttributes[i].currentValue);
                }
                else if (((itemAttributes[i].name).getAsciiId()).equals("shuttle_beacon"))
                {
                    setObjVar(prototype, "modules.shuttle_beacon", itemAttributes[i].currentValue);
                }
                else if (((itemAttributes[i].name).getAsciiId()).equals("cloning_tube"))
                {
                    setObjVar(prototype, "modules.cloning_tube", itemAttributes[i].currentValue);
                }
                else if (((itemAttributes[i].name).getAsciiId()).equals("entertainer"))
                {
                    setObjVar(prototype, "modules.entertainer", itemAttributes[i].currentValue);
                }
                else if (((itemAttributes[i].name).getAsciiId()).equals("junk_dealer"))
                {
                    setObjVar(prototype, "modules.junk_dealer", itemAttributes[i].currentValue);
                }
                else if (((itemAttributes[i].name).getAsciiId()).equals("clothing_station"))
                {
                    setObjVar(prototype, "modules.clothing_station", itemAttributes[i].currentValue);
                }
                else if (((itemAttributes[i].name).getAsciiId()).equals("weapon_station"))
                {
                    setObjVar(prototype, "modules.weapon_station", itemAttributes[i].currentValue);
                }
                else if (((itemAttributes[i].name).getAsciiId()).equals("structure_station"))
                {
                    setObjVar(prototype, "modules.structure_station", itemAttributes[i].currentValue);
                }
                else if (((itemAttributes[i].name).getAsciiId()).equals("ship_station"))
                {
                    setObjVar(prototype, "modules.ship_station", itemAttributes[i].currentValue);
                }
                else if (((itemAttributes[i].name).getAsciiId()).equals("food_station"))
                {
                    setObjVar(prototype, "modules.food_station", itemAttributes[i].currentValue);
                }
                else if (((itemAttributes[i].name).getAsciiId()).equals("xp_bonus"))
                {
                    setObjVar(prototype, "modules.xp_bonus", itemAttributes[i].currentValue);
                }
                else if (((itemAttributes[i].name).getAsciiId()).equals("rebel"))
                {
                    setObjVar(prototype, "modules.rebel", itemAttributes[i].currentValue);
                }
                else if (((itemAttributes[i].name).getAsciiId()).equals("imperial"))
                {
                    setObjVar(prototype, "modules.imperial", itemAttributes[i].currentValue);
                }
                else if (((itemAttributes[i].name).getAsciiId()).equals("extra_life"))
                {
                    setObjVar(prototype, "modules.extra_life", itemAttributes[i].currentValue);
                }
                else if (((itemAttributes[i].name).getAsciiId()).equals("holonet"))
                {
                    setObjVar(prototype, "modules.holonet", itemAttributes[i].currentValue);
                }
                else if (((itemAttributes[i].name).getAsciiId()).equals("vehicle_repair"))
                {
                    setObjVar(prototype, "modules.vehicle_repair", itemAttributes[i].currentValue);
                }
                else 
                {
                    setObjVar(prototype, craftinglib.COMPONENT_ATTRIBUTE_OBJVAR_NAME + "." + (itemAttributes[i].name).getAsciiId(), itemAttributes[i].currentValue);
                }
            }
        }
    }
}
