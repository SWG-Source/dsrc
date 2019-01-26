package script.systems.crafting.droid;

import script.dictionary;
import script.draft_schematic;
import script.library.craftinglib;
import script.obj_id;

public class crafting_base_droid_component extends script.systems.crafting.crafting_base
{
    public crafting_base_droid_component()
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
                if (((itemAttribute.name).getAsciiId()).equals("mechanism_quality")) {
                    setObjVar(prototype, craftinglib.COMPONENT_ATTRIBUTE_OBJVAR_NAME + ".mechanism_quality", itemAttribute.currentValue);
                } else if (((itemAttribute.name).getAsciiId()).equals("armor_toughness")) {
                    setObjVar(prototype, craftinglib.COMPONENT_ATTRIBUTE_OBJVAR_NAME + ".armor_toughness", (int) (itemAttribute.currentValue));
                } else if (((itemAttribute.name).getAsciiId()).equals("armor_module")) {
                    setObjVar(prototype, craftinglib.COMPONENT_ATTRIBUTE_OBJVAR_NAME + ".armor_module", itemAttribute.currentValue);
                } else if ((((itemAttribute.name).getAsciiId()).equals("charges")) || (((itemAttribute.name).getAsciiId()).equals("droid_count"))) {
                    setCount(prototype, (int) itemAttribute.currentValue);
                } else if (((itemAttribute.name).getAsciiId()).equals("useModifier")) {
                    setObjVar(prototype, craftinglib.COMPONENT_ATTRIBUTE_OBJVAR_NAME + ".useModifier", itemAttribute.currentValue);
                } else if (((itemAttribute.name).getAsciiId()).equals("cmbt_module")) {
                    setObjVar(prototype, craftinglib.COMPONENT_ATTRIBUTE_OBJVAR_NAME + ".cmbt_module", itemAttribute.currentValue);
                } else if (((itemAttribute.name).getAsciiId()).equals("struct_module")) {
                    setObjVar(prototype, craftinglib.COMPONENT_ATTRIBUTE_OBJVAR_NAME + ".struct_module", (int) itemAttribute.currentValue);
                } else if (((itemAttribute.name).getAsciiId()).equals("fire_potency") && itemAttribute.currentValue > 0) {
                    setObjVar(prototype, craftinglib.COMPONENT_ATTRIBUTE_OBJVAR_NAME + ".fire_potency", itemAttribute.currentValue);
                } else if (((itemAttribute.name).getAsciiId()).equals("arc_projector") && itemAttribute.currentValue > 0) {
                    setObjVar(prototype, craftinglib.COMPONENT_ATTRIBUTE_OBJVAR_NAME + ".arc_projector", itemAttribute.currentValue);
                } else if (((itemAttribute.name).getAsciiId()).equals("shield_heatsink") && itemAttribute.currentValue > 0) {
                    setObjVar(prototype, craftinglib.COMPONENT_ATTRIBUTE_OBJVAR_NAME + ".shield_heatsink", itemAttribute.currentValue);
                } else if (((itemAttribute.name).getAsciiId()).equals("pain_inducer") && itemAttribute.currentValue > 0) {
                    setObjVar(prototype, craftinglib.COMPONENT_ATTRIBUTE_OBJVAR_NAME + ".pain_inducer", itemAttribute.currentValue);
                } else if (((itemAttribute.name).getAsciiId()).equals("quickset_metal") && itemAttribute.currentValue > 0) {
                    setObjVar(prototype, craftinglib.COMPONENT_ATTRIBUTE_OBJVAR_NAME + ".quickset_metal", itemAttribute.currentValue);
                } else if (((itemAttribute.name).getAsciiId()).equals("dump_capacitors") && itemAttribute.currentValue > 0) {
                    setObjVar(prototype, craftinglib.COMPONENT_ATTRIBUTE_OBJVAR_NAME + ".dump_capacitors", itemAttribute.currentValue);
                } else if (((itemAttribute.name).getAsciiId()).equals("flame_jet_level") && itemAttribute.currentValue > 0) {
                } else if (((itemAttribute.name).getAsciiId()).equals("regenerative_plating_level") && itemAttribute.currentValue > 0) {
                } else if (((itemAttribute.name).getAsciiId()).equals("battery_dump_level") && itemAttribute.currentValue > 0) {
                } else if (((itemAttribute.name).getAsciiId()).equals("electrical_shock_level") && itemAttribute.currentValue > 0) {
                } else if (((itemAttribute.name).getAsciiId()).equals("droideka_shield_level") && itemAttribute.currentValue > 0) {
                } else if (((itemAttribute.name).getAsciiId()).equals("torturous_needle_level") && itemAttribute.currentValue > 0) {
                } else if (itemAttribute.currentValue > 0) {
                    setObjVar(prototype, craftinglib.COMPONENT_ATTRIBUTE_OBJVAR_NAME + "." + (itemAttribute.name).getAsciiId(), itemAttribute.currentValue);
                }
            }
        }
    }
}
