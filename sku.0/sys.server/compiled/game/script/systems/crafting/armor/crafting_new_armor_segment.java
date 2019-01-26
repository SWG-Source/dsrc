package script.systems.crafting.armor;

import script.dictionary;
import script.draft_schematic;
import script.library.armor;
import script.library.craftinglib;
import script.obj_id;
import script.obj_var_list;

public class crafting_new_armor_segment extends script.systems.crafting.crafting_base
{
    public crafting_new_armor_segment()
    {
    }
    public static final String VERSION = "v0.00.00";
    public void calcAndSetPrototypeProperties(obj_id prototype, draft_schematic.attribute[] itemAttributes, dictionary craftingValuesDictionary) throws InterruptedException
    {
        int segmentRow = dataTableSearchColumnForInt(getStringCrc(armor.DATATABLE_SEGMENT_ROW), armor.DATATABLE_TYPE_COL, armor.DATATABLE_ARMOR);
        int layerRow = dataTableSearchColumnForInt(getStringCrc(armor.DATATABLE_LAYER_REFERENCE_ROW), armor.DATATABLE_TYPE_COL, armor.DATATABLE_ARMOR);
        if (segmentRow < 0 || layerRow < 0)
        {
            CustomerServiceLog("crafting", "WARNING: armor datatable " + armor.DATATABLE_ARMOR + " has missing layer or segment row (" + layerRow + "," + segmentRow + ")");
            return;
        }
        obj_var_list componentData = getObjVarList(getSelf(), craftinglib.COMPONENT_ATTRIBUTE_INTERNAL_OBJVAR_NAME);
        if (componentData != null)
        {
            armor.rescaleComponentData(getSelf(), componentData, layerRow, segmentRow);
        }
        armor.updateItemAttributes(getSelf(), itemAttributes, segmentRow);
        super.calcAndSetPrototypeProperties(prototype, itemAttributes, craftingValuesDictionary);
    }
    public void calcAndSetPrototypeProperties(obj_id prototype, draft_schematic.attribute[] itemAttributes) throws InterruptedException
    {
        debugServerConsoleMsg(null, "Beginning assembly-phase prototype property setting");
        int segmentRow = dataTableSearchColumnForInt(getStringCrc(armor.DATATABLE_SEGMENT_ROW), armor.DATATABLE_TYPE_COL, armor.DATATABLE_ARMOR);
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
                setObjVar(prototype, craftinglib.COMPONENT_ATTRIBUTE_OBJVAR_NAME + "." + (itemAttribute.name).getAsciiId(), itemAttribute.currentValue);
                if ((itemAttribute.name).equals(armor.OBJVAR_CONDITION)) {
                    float hp = armor.getAbsoluteArmorAttribute(itemAttribute.currentValue, segmentRow, armor.DATATABLE_MIN_CONDITION_COL);
                    if (hp != Float.MIN_VALUE) {
                        setMaxHitpoints(prototype, (int) hp);
                    }
                }
            }
        }
        armor.saveScaledDataToPrototype(getSelf(), prototype, itemAttributes, craftinglib.COMPONENT_ATTRIBUTE_OBJVAR_NAME);
    }
}
