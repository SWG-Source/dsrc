package script.systems.crafting.camp;

import script.dictionary;
import script.draft_schematic;
import script.library.craftinglib;
import script.obj_id;

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
        for (draft_schematic.attribute itemAttribute : itemAttributes) {
            if (itemAttribute == null) {
                continue;
            }
            if (!calcAndSetPrototypeProperty(prototype, itemAttribute)) {
                if (itemAttribute.currentValue == 0.0f) {
                    continue;
                }
                switch (((itemAttribute.name).getAsciiId())) {
                    case "lifetime":
                        setObjVar(prototype, "modules.lifetime", itemAttribute.currentValue);
                        break;
                    case "shuttle_beacon":
                        setObjVar(prototype, "modules.shuttle_beacon", itemAttribute.currentValue);
                        break;
                    case "cloning_tube":
                        setObjVar(prototype, "modules.cloning_tube", itemAttribute.currentValue);
                        break;
                    case "entertainer":
                        setObjVar(prototype, "modules.entertainer", itemAttribute.currentValue);
                        break;
                    case "junk_dealer":
                        setObjVar(prototype, "modules.junk_dealer", itemAttribute.currentValue);
                        break;
                    case "clothing_station":
                        setObjVar(prototype, "modules.clothing_station", itemAttribute.currentValue);
                        break;
                    case "weapon_station":
                        setObjVar(prototype, "modules.weapon_station", itemAttribute.currentValue);
                        break;
                    case "structure_station":
                        setObjVar(prototype, "modules.structure_station", itemAttribute.currentValue);
                        break;
                    case "ship_station":
                        setObjVar(prototype, "modules.ship_station", itemAttribute.currentValue);
                        break;
                    case "food_station":
                        setObjVar(prototype, "modules.food_station", itemAttribute.currentValue);
                        break;
                    case "xp_bonus":
                        setObjVar(prototype, "modules.xp_bonus", itemAttribute.currentValue);
                        break;
                    case "rebel":
                        setObjVar(prototype, "modules.rebel", itemAttribute.currentValue);
                        break;
                    case "imperial":
                        setObjVar(prototype, "modules.imperial", itemAttribute.currentValue);
                        break;
                    case "extra_life":
                        setObjVar(prototype, "modules.extra_life", itemAttribute.currentValue);
                        break;
                    case "holonet":
                        setObjVar(prototype, "modules.holonet", itemAttribute.currentValue);
                        break;
                    case "vehicle_repair":
                        setObjVar(prototype, "modules.vehicle_repair", itemAttribute.currentValue);
                        break;
                    default:
                        setObjVar(prototype, craftinglib.COMPONENT_ATTRIBUTE_OBJVAR_NAME + "." + (itemAttribute.name).getAsciiId(), itemAttribute.currentValue);
                        break;
                }
            }
        }
    }
}
