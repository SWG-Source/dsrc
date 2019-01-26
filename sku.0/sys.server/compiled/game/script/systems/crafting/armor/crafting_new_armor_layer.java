package script.systems.crafting.armor;

import script.dictionary;
import script.draft_schematic;
import script.library.armor;
import script.library.craftinglib;
import script.obj_id;

public class crafting_new_armor_layer extends script.systems.crafting.crafting_base
{
    public crafting_new_armor_layer()
    {
    }
    public static final String VERSION = "v0.00.00";
    public void calcAndSetPrototypeProperties(obj_id prototype, draft_schematic.attribute[] itemAttributes, dictionary craftingValuesDictionary) throws InterruptedException
    {
        for (draft_schematic.attribute itemAttribute : itemAttributes) {
            if (itemAttribute == null) {
                continue;
            }
        }
        super.calcAndSetPrototypeProperties(prototype, itemAttributes, craftingValuesDictionary);
    }
    public void calcAndSetPrototypeProperties(obj_id prototype, draft_schematic.attribute[] itemAttributes) throws InterruptedException
    {
        obj_id self = getSelf();
        if (!hasObjVar(self, "layer"))
        {
            CustomerServiceLog("crafting", "WARNING: armor layer draft schematic " + getDraftSchematic(self) + " has no layer objvar set");
            return;
        }
        int layer = getIntObjVar(self, "layer");
        int layerRow = dataTableSearchColumnForInt(getStringCrc(armor.DATATABLE_LAYER_ROW + layer), armor.DATATABLE_TYPE_COL, armor.DATATABLE_ARMOR);
        int referenceRow = dataTableSearchColumnForInt(getStringCrc(armor.DATATABLE_LAYER_REFERENCE_ROW), armor.DATATABLE_TYPE_COL, armor.DATATABLE_ARMOR);
        if (layerRow < 0 || referenceRow < 0)
        {
            CustomerServiceLog("crafting", "WARNING: armor datatable " + armor.DATATABLE_ARMOR + " has missing layer or reference row (" + layerRow + "," + referenceRow + ")");
            return;
        }
        debugServerConsoleMsg(null, "Beginning assembly-phase prototype property setting");
        for (draft_schematic.attribute itemAttribute : itemAttributes) {
            if (itemAttribute == null) {
                continue;
            }
            if (!calcAndSetPrototypeProperty(prototype, itemAttribute)) {
                if (itemAttribute.currentValue < itemAttribute.minValue) {
                    itemAttribute.currentValue = itemAttribute.minValue;
                } else if (itemAttribute.currentValue > itemAttribute.maxValue) {
                    itemAttribute.currentValue = itemAttribute.maxValue;
                }
                float newValue = armor.rescaleArmorData((itemAttribute.name).getAsciiId(), layerRow, referenceRow, itemAttribute.currentValue);
                if (newValue == Float.MIN_VALUE) {
                    newValue = itemAttribute.currentValue;
                }
                if ((itemAttribute.name).equals("special_protection")) {
                    setObjVar(prototype, craftinglib.COMPONENT_ATTRIBUTE_OBJVAR_NAME + "." + armor.OBJVAR_LAYER_PREFIX + layer, newValue);
                } else {
                    setObjVar(prototype, craftinglib.COMPONENT_ATTRIBUTE_OBJVAR_NAME + "." + (itemAttribute.name).getAsciiId(), newValue);
                }
            }
        }
    }
}
