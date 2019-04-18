package script.systems.crafting.armor;

import script.dictionary;
import script.draft_schematic;
import script.library.armor;
import script.library.craftinglib;
import script.obj_id;
import script.obj_var_list;

public class crafting_new_armor_core extends script.systems.crafting.crafting_base
{
    public crafting_new_armor_core()
    {
    }
    public static final String VERSION = "v0.00.00";
    public void calcAndSetPrototypeProperties(obj_id prototype, draft_schematic.attribute[] itemAttributes, dictionary craftingValuesDictionary) throws InterruptedException
    {
        if (!hasObjVar(getSelf(), armor.OBJVAR_ARMOR_LEVEL))
        {
            CustomerServiceLog("crafting", "WARNING: armor core draft schematic " + getDraftSchematic(getSelf()) + " has no " + armor.OBJVAR_ARMOR_LEVEL + " objvar set");
            return;
        }
        if (!hasObjVar(getSelf(), armor.OBJVAR_ARMOR_CATEGORY))
        {
            CustomerServiceLog("crafting", "WARNING: armor core draft schematic " + getDraftSchematic(getSelf()) + " has no " + armor.OBJVAR_ARMOR_CATEGORY + " objvar set");
            return;
        }
        int armorLevel = getIntObjVar(getSelf(), armor.OBJVAR_ARMOR_LEVEL);
        int armorCategory = getIntObjVar(getSelf(), armor.OBJVAR_ARMOR_CATEGORY);
        int coreRow = dataTableSearchColumnForInt(getStringCrc(armor.DATATABLE_CORE_ROW + armorLevel), armor.DATATABLE_TYPE_COL, armor.DATATABLE_ARMOR);
        int segmentRow = dataTableSearchColumnForInt(getStringCrc(armor.DATATABLE_SEGMENT_ROW), armor.DATATABLE_TYPE_COL, armor.DATATABLE_ARMOR);
        if (coreRow < 0 || segmentRow < 0)
        {
            CustomerServiceLog("crafting", "WARNING: armor datatable " + armor.DATATABLE_ARMOR + " has missing segment or core row (" + segmentRow + "," + coreRow + ")");
            return;
        }
        obj_var_list componentData = getObjVarList(getSelf(), craftinglib.COMPONENT_ATTRIBUTE_INTERNAL_OBJVAR_NAME);
        if (componentData != null)
        {
            armor.rescaleComponentData(getSelf(), componentData, segmentRow, coreRow);
        }
        armor.updateItemAttributes(getSelf(), itemAttributes, coreRow);
        super.calcAndSetPrototypeProperties(prototype, itemAttributes, craftingValuesDictionary);
    }
    public void calcAndSetPrototypeProperties(obj_id prototype, draft_schematic.attribute[] itemAttributes) throws InterruptedException
    {
        debugServerConsoleMsg(null, "Beginning assembly-phase prototype property setting");
        if (!hasObjVar(getSelf(), armor.OBJVAR_ARMOR_LEVEL))
        {
            CustomerServiceLog("crafting", "WARNING: armor core draft schematic " + getDraftSchematic(getSelf()) + " has no " + armor.OBJVAR_ARMOR_LEVEL + " objvar set");
            return;
        }
        if (!hasObjVar(getSelf(), armor.OBJVAR_ARMOR_CATEGORY))
        {
            CustomerServiceLog("crafting", "WARNING: armor core draft schematic " + getDraftSchematic(getSelf()) + " has no " + armor.OBJVAR_ARMOR_CATEGORY + " objvar set");
            return;
        }
        int armorLevel = getIntObjVar(getSelf(), armor.OBJVAR_ARMOR_LEVEL);
        int armorCategory = getIntObjVar(getSelf(), armor.OBJVAR_ARMOR_CATEGORY);
        int coreRow = dataTableSearchColumnForInt(getStringCrc(armor.DATATABLE_CORE_ROW + armorLevel), armor.DATATABLE_TYPE_COL, armor.DATATABLE_ARMOR);
        setObjVar(prototype, craftinglib.COMPONENT_ATTRIBUTE_OBJVAR_NAME + "." + armor.OBJVAR_ARMOR_LEVEL, armorLevel);
        setObjVar(prototype, craftinglib.COMPONENT_ATTRIBUTE_OBJVAR_NAME + "." + armor.OBJVAR_ARMOR_CATEGORY, armorCategory);
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
                    float hp = armor.getAbsoluteArmorAttribute(itemAttribute.currentValue, coreRow, armor.DATATABLE_MIN_CONDITION_COL);
                    if (hp != Float.MIN_VALUE) {
                        setMaxHitpoints(prototype, (int) hp);
                    }
                }
            }
        }
        if (armorCategory == AC_reconnaissance)
        {
            armor.setArmorSpecialProtectionPercent(prototype, armor.DATATABLE_RECON_LAYER, 1.0f);
        }
        else if (armorCategory == AC_assault)
        {
            armor.setArmorSpecialProtectionPercent(prototype, armor.DATATABLE_ASSAULT_LAYER, 1.0f);
        }
        armor.saveScaledDataToPrototype(getSelf(), prototype, itemAttributes, craftinglib.COMPONENT_ATTRIBUTE_OBJVAR_NAME);
    }
}
