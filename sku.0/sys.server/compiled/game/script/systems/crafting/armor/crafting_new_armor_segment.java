package script.systems.crafting.armor;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.armor;
import script.library.utils;
import script.library.craftinglib;

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
        for (int i = 0; i < itemAttributes.length; ++i)
        {
            if (itemAttributes[i] == null)
            {
                continue;
            }
            if (!calcAndSetPrototypeProperty(prototype, itemAttributes[i]))
            {
                if (itemAttributes[i].currentValue < itemAttributes[i].minValue)
                {
                    itemAttributes[i].currentValue = itemAttributes[i].minValue;
                }
                else if (itemAttributes[i].currentValue > itemAttributes[i].maxValue)
                {
                    itemAttributes[i].currentValue = itemAttributes[i].maxValue;
                }
                setObjVar(prototype, craftinglib.COMPONENT_ATTRIBUTE_OBJVAR_NAME + "." + (itemAttributes[i].name).getAsciiId(), itemAttributes[i].currentValue);
                if ((itemAttributes[i].name).equals(armor.OBJVAR_CONDITION))
                {
                    float hp = armor.getAbsoluteArmorAttribute(itemAttributes[i].currentValue, segmentRow, armor.DATATABLE_MIN_CONDITION_COL);
                    if (hp != Float.MIN_VALUE)
                    {
                        setMaxHitpoints(prototype, (int)hp);
                    }
                }
            }
        }
        armor.saveScaledDataToPrototype(getSelf(), prototype, itemAttributes, craftinglib.COMPONENT_ATTRIBUTE_OBJVAR_NAME);
    }
}
