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

public class crafting_new_armor_layer extends script.systems.crafting.crafting_base
{
    public crafting_new_armor_layer()
    {
    }
    public static final String VERSION = "v0.00.00";
    public void calcAndSetPrototypeProperties(obj_id prototype, draft_schematic.attribute[] itemAttributes, dictionary craftingValuesDictionary) throws InterruptedException
    {
        for (int i = 0; i < itemAttributes.length; ++i)
        {
            if (itemAttributes[i] == null)
            {
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
                float newValue = armor.rescaleArmorData((itemAttributes[i].name).getAsciiId(), layerRow, referenceRow, itemAttributes[i].currentValue);
                if (newValue == Float.MIN_VALUE)
                {
                    newValue = itemAttributes[i].currentValue;
                }
                if ((itemAttributes[i].name).equals("special_protection"))
                {
                    setObjVar(prototype, craftinglib.COMPONENT_ATTRIBUTE_OBJVAR_NAME + "." + armor.OBJVAR_LAYER_PREFIX + layer, newValue);
                }
                else 
                {
                    setObjVar(prototype, craftinglib.COMPONENT_ATTRIBUTE_OBJVAR_NAME + "." + (itemAttributes[i].name).getAsciiId(), newValue);
                }
            }
        }
    }
}
