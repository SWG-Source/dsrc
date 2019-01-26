package script.item.armor;

import script.dictionary;
import script.library.armor;
import script.library.prose;
import script.library.static_item;
import script.library.utils;
import script.obj_id;
import script.prose_package;
import script.string_id;

public class new_armor extends script.base_script
{
    public new_armor()
    {
    }
    public static final java.text.NumberFormat floatFormat = new java.text.DecimalFormat("###.#");
    public static final java.text.NumberFormat percentFormat = new java.text.DecimalFormat("###.#%");
    public static final String[] SID_SPECIAL_TOOLTIP_PROTECTIONS = 
    {
        "armor_eff_kinetic",
        "armor_eff_energy",
        "armor_eff_elemental_heat",
        "armor_eff_elemental_cold",
        "armor_eff_elemental_acid",
        "armor_eff_elemental_electrical"
    };
    public static final String[] SPECIAL_PROTECTIONS = 
    {
        "kinetic",
        "energy",
        "heat",
        "cold",
        "acid",
        "electricity"
    };
    public static final String APPEARANCE_TBL = "datatables/appearance/appearance_table.iff";
    public String getSpeciesName(String speciesName) throws InterruptedException
    {
        String species = "";
        java.util.StringTokenizer st = new java.util.StringTokenizer(speciesName);
        if (st.hasMoreTokens())
        {
            String gender = st.nextToken();
            if (gender.length() > 0 && st.hasMoreTokens())
            {
                species = st.nextToken();
            }
        }
        return species;
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        int ourType = getGameObjectType(self);

        if (!isGameObjectTypeOf(ourType, GOT_armor) && !isGameObjectTypeOf(ourType, GOT_component_armor) 
		&& !isGameObjectTypeOf(ourType, GOT_component_new_armor) && !isGameObjectTypeOf(ourType, GOT_cybernetic) 
		&& !isGameObjectTypeOf(ourType, GOT_cybernetic_arm) && !isGameObjectTypeOf(ourType, GOT_cybernetic_legs) 
		&& !isGameObjectTypeOf(ourType, GOT_cybernetic_torso))
        {
            debugServerConsoleMsg(self, "!!!!Removing armor script!!!!!");
            debugServerConsoleMsg(self, "--- This is probably bad... removing because game object type of item () doesn't match one of the exceptions: ");

            detachScript(self, "item.armor.new_armor");
        }
        return SCRIPT_CONTINUE;
    }
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        getProtections(self);
        
        {
            java.util.HashSet speciesRequirementsSet = new java.util.HashSet();
            String armorSharedObjectTemplateName = getSharedObjectTemplateName(self);
            if (armorSharedObjectTemplateName != null)
            {
                int startingIndex = armorSharedObjectTemplateName.lastIndexOf('/') + 1;
                int endingIndex = armorSharedObjectTemplateName.lastIndexOf('.');
                armorSharedObjectTemplateName = armorSharedObjectTemplateName.substring(startingIndex, endingIndex);
                dictionary appearanceRestrictions = dataTableGetRow(APPEARANCE_TBL, armorSharedObjectTemplateName);
                if (appearanceRestrictions != null)
                {
                    java.util.Enumeration speciesNames = appearanceRestrictions.keys();
                    while (speciesNames.hasMoreElements())
                    {
                        String species = (String)(speciesNames.nextElement());
                        if (!species.equals("Object Template Name"))
                        {
                            Object speciesAppearance = appearanceRestrictions.get(species);
                            if (speciesAppearance != null && (speciesAppearance instanceof String))
                            {
                                String speciesAppearanceNameString = ((String)speciesAppearance);
                                if (!speciesAppearanceNameString.equals(":block"))
                                {
                                    speciesRequirementsSet.add(getSpeciesName(species));
                                }
                            }
                        }
                    }
                }
            }
            String speciesRequirements = "";
            for (Object o : speciesRequirementsSet) {
                speciesRequirements += o;
                speciesRequirements += " ";
            }
            utils.setScriptVar(self, armor.SCRIPTVAR_SPECIES_RESTRICTIONS, speciesRequirements);
        }
        if (!isGameObjectTypeOf(self, GOT_armor_foot) && !isGameObjectTypeOf(self, GOT_armor_hand) && !hasObjVar(self, "armor.general_protection"))
        {
            messageTo(self, "checkArmorData", null, 1, false);
        }
        if (isGameObjectTypeOf(self, GOT_armor_misc) && !hasObjVar(self, "armor.armorStatCleaned"))
        {
            armor.removeAllArmorData(self);
            setObjVar(self, "armor.armorStatCleaned", 1);
        }
        return SCRIPT_CONTINUE;
    }
    public int OnAboutToBeTransferred(obj_id self, obj_id destContainer, obj_id transferer) throws InterruptedException
    {
        if (isPlayer(destContainer) || isAPlayerAppearanceInventoryContainer(destContainer))
        {
            obj_id player = destContainer;
            if (isAPlayerAppearanceInventoryContainer(destContainer))
            {
                player = getContainedBy(destContainer);
            }
            if (!armor.isArmorCertified(player, self) || (utils.isMando(self) && !utils.hasSpecialSkills(player)))
            {
                prose_package pp = new prose_package();
                pp = prose.setStringId(pp, new string_id("spam", "armor_no_cert"));
                pp = prose.setTT(pp, self);
                sendSystemMessageProse(player, pp);
                return SCRIPT_OVERRIDE;
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int OnTransferred(obj_id self, obj_id sourceContainer, obj_id destContainer, obj_id transferer) throws InterruptedException
    {
        obj_id player;
        if (isPlayer(destContainer))
        {
            player = destContainer;
        }
        else if (isPlayer(sourceContainer))
        {
            player = sourceContainer;
        }
        else 
        {
            return SCRIPT_CONTINUE;
        }
        armor.recalculateArmorForPlayer(player);
        return SCRIPT_CONTINUE;
    }
    public int OnGetAttributes(obj_id self, obj_id player, String[] names, String[] attribs) throws InterruptedException
    {
        if (player == null || names == null || attribs == null || names.length != attribs.length)
        {
            return SCRIPT_CONTINUE;
        }
        int free = getFirstFreeIndex(names);
        if (free == -1)
        {
            return SCRIPT_CONTINUE;
        }
        int armorType = getGameObjectType(self);
        if (armor.isFinalArmor(armorType))
        {
            armorType = GOT_armor;
        }
        int armorLevel = -1;
        int armorCategory = -1;
        int armorRow = armor.getArmorDatatableRow(self);
        if (armorRow < 0)
        {
            CustomerServiceLog("armor", "WARNING: could not find armor datatable row for armor " + self);
            return SCRIPT_CONTINUE;
        }
        if (armorType == GOT_armor || armorType == GOT_armor_core || armorType == GOT_armor_psg)
        {
            armorLevel = armor.getArmorLevel(self);
            armorCategory = armor.getArmorCategory(self);
            if (armorLevel < 0 || armorCategory < 0)
            {
                return SCRIPT_CONTINUE;
            }
        }
        dictionary protections = armor.getArmorSpecialProtections(self);
        float general_protection = armor.getArmorGeneralProtection(self);
        float[] penalties = null;
        if (general_protection > 0 && armorLevel >= 0 && armorCategory >= 0)
        {
            penalties = armor.getScaledArmorPenalties(self, general_protection, armorRow, armorLevel, armorCategory);
        }
        if (armorCategory >= 0)
        {
            Object catName = armor.ARMOR_CATEGORY_MAP.get(armorCategory);
            if (catName != null && free < names.length)
            {
                names[free] = armor.SID_ARMOR_CATEGORY;
                attribs[free++] = (String)catName;
                names[free] = "tooltip.armor_category";
                attribs[free++] = (String)catName;
            }
        }
        if (hasObjVar(self, "attribute.bonus.0"))
        {
            names[free] = "health_bonus";
            attribs[free++] = "+" + getIntObjVar(self, "attribute.bonus.0");
            
        }
        String[][] entries = 
        {
            
            {
                "kinetic",
                "energy"
            },
            
            {
                "heat",
                "cold",
                "acid",
                "electricity"
            }
        };
        int realArmorType = getGameObjectType(self);
        if (realArmorType == GOT_armor_hand || realArmorType == GOT_armor_foot)
        {
            return SCRIPT_CONTINUE;
        }
        for (String[] entry : entries) {
            for (String s : entry) {
                if (free < names.length) {
                    float value = general_protection + 0.5f;
                    if (protections != null) {
                        value += protections.getFloat(s);
                    }
                    names[free] = (String) (armor.SPECIAL_PROTECTION_MAP.get(s));
                    attribs[free++] = Integer.toString((int) value);
                }
            }
        }
        
        {
            for (int i = 0; i < SPECIAL_PROTECTIONS.length; ++i)
            {
                names[free] = "tooltip." + SID_SPECIAL_TOOLTIP_PROTECTIONS[i];
                float value = general_protection + 0.5f;
                if (protections != null)
                {
                    value += protections.getFloat(SPECIAL_PROTECTIONS[i]);
                }
                attribs[free++] = Integer.toString((int)value);
            }
        }
        if (armorType == GOT_armor_psg)
        {
            float efficiency = utils.getFloatScriptVar(self, armor.OBJVAR_ARMOR_BASE + "." + armor.OBJVAR_PSG_CURRENT_EFFICIENCY);
            float recharge = getFloatObjVar(self, armor.OBJVAR_ARMOR_BASE + "." + armor.OBJVAR_PSG_RECHARGE_RATE);
            if (free < names.length)
            {
                names[free] = "@obj_attr_n:" + armor.OBJVAR_PSG_CURRENT_EFFICIENCY;
                attribs[free++] = percentFormat.format(efficiency);
            }
            if (free < names.length)
            {
                names[free] = "@obj_attr_n:" + armor.OBJVAR_PSG_RECHARGE_RATE;
                attribs[free++] = floatFormat.format(recharge);
            }
        }
        String speciesRequirements = utils.getStringScriptVar(self, armor.SCRIPTVAR_SPECIES_RESTRICTIONS);
        if (speciesRequirements != null && speciesRequirements.length() > 0)
        {
            names[free] = "species_restrictions.species_name";
            attribs[free++] = speciesRequirements;
            names[free] = "tooltip.species_restrictions";
            attribs[free++] = speciesRequirements;
        }
        if (static_item.isStaticItem(self))
        {
            dictionary itemData = new dictionary();
            itemData = static_item.getMasterItemDictionary(self);
            int tier = itemData.getInt("tier");
            names[free] = "tier";
            attribs[free++] = "" + tier;
            names[free] = "tooltip.tier";
            attribs[free++] = "" + tier;
        }
        return SCRIPT_CONTINUE;
    }
    public int delayedEquip(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id player = params.getObjId("player");
        equip(self, player);
        return SCRIPT_CONTINUE;
    }
    public int checkArmorData(obj_id self, dictionary params) throws InterruptedException
    {
        String template = getTemplateName(self);
        if(template == null){
            LOG("DESIGNER_FATAL","Unable to check armor data for item (" + self + ") because it doesn't have a template!");
            return SCRIPT_CONTINUE;
        }
        String name = null;
        if (template.endsWith("armor_ubese_helmet_quest.iff"))
        {
            name = "object/tangible/wearables/armor/ubese/armor_ubese_helmet_quest.iff";
        }
        else if (template.endsWith("armor_stormtrooper_helmet_quest.iff"))
        {
            name = "object/tangible/wearables/armor/stormtrooper/armor_stormtrooper_helmet_quest.iff";
        }
        if (name != null)
        {
            utils.replaceSnowflakeItem(self, name);
            return SCRIPT_CONTINUE;
        }
        if (armor.getArmorGeneralProtection(self) < 0)
        {
            if (!isGameObjectTypeOf(self, GOT_armor_foot) && !isGameObjectTypeOf(self, GOT_armor_hand))
            {
                final String[] ARMOR_SET = 
                {
                    "object/tangible/wearables/armor/clone_trooper/armor_clone_trooper_imperial_s01_leggings.iff",
                    "object/tangible/wearables/armor/clone_trooper/armor_clone_trooper_imperial_s01_helmet.iff",
                    "object/tangible/wearables/armor/clone_trooper/armor_clone_trooper_imperial_s01_gloves.iff",
                    "object/tangible/wearables/armor/clone_trooper/armor_clone_trooper_imperial_s01_chest_plate.iff",
                    "object/tangible/wearables/armor/clone_trooper/armor_clone_trooper_imperial_s01_bracer_r.iff",
                    "object/tangible/wearables/armor/clone_trooper/armor_clone_trooper_imperial_s01_bracer_l.iff",
                    "object/tangible/wearables/armor/clone_trooper/armor_clone_trooper_imperial_s01_boots.iff",
                    "object/tangible/wearables/armor/clone_trooper/armor_clone_trooper_imperial_s01_bicep_r.iff",
                    "object/tangible/wearables/armor/clone_trooper/armor_clone_trooper_imperial_s01_bicep_l.iff",
                    "object/tangible/wearables/armor/clone_trooper/armor_clone_trooper_rebel_s01_leggings.iff",
                    "object/tangible/wearables/armor/clone_trooper/armor_clone_trooper_rebel_s01_helmet.iff",
                    "object/tangible/wearables/armor/clone_trooper/armor_clone_trooper_rebel_s01_gloves.iff",
                    "object/tangible/wearables/armor/clone_trooper/armor_clone_trooper_rebel_s01_chest_plate.iff",
                    "object/tangible/wearables/armor/clone_trooper/armor_clone_trooper_rebel_s01_bracer_r.iff",
                    "object/tangible/wearables/armor/clone_trooper/armor_clone_trooper_rebel_s01_bracer_l.iff",
                    "object/tangible/wearables/armor/clone_trooper/armor_clone_trooper_rebel_s01_boots.iff",
                    "object/tangible/wearables/armor/clone_trooper/armor_clone_trooper_rebel_s01_bicep_r.iff",
                    "object/tangible/wearables/armor/clone_trooper/armor_clone_trooper_rebel_s01_bicep_l.iff",
                    "object/tangible/wearables/armor/clone_trooper/armor_clone_trooper_neutral_s01_leggings.iff",
                    "object/tangible/wearables/armor/clone_trooper/armor_clone_trooper_neutral_s01_helmet.iff",
                    "object/tangible/wearables/armor/clone_trooper/armor_clone_trooper_neutral_s01_gloves.iff",
                    "object/tangible/wearables/armor/clone_trooper/armor_clone_trooper_neutral_s01_chest_plate.iff",
                    "object/tangible/wearables/armor/clone_trooper/armor_clone_trooper_neutral_s01_bracer_r.iff",
                    "object/tangible/wearables/armor/clone_trooper/armor_clone_trooper_neutral_s01_bracer_l.iff",
                    "object/tangible/wearables/armor/clone_trooper/armor_clone_trooper_neutral_s01_boots.iff",
                    "object/tangible/wearables/armor/clone_trooper/armor_clone_trooper_neutral_s01_bicep_r.iff",
                    "object/tangible/wearables/armor/clone_trooper/armor_clone_trooper_neutral_s01_bicep_l.iff"
                };
                final String objectTemplateName = getTemplateName(self);
                for (String s : ARMOR_SET) {
                    if (objectTemplateName.equals(s)) {
                        armor.setArmorDataPercent(self, 2, 1, 0.94f, 0.95f);
                        break;
                    }
                }
            }
        }
        return SCRIPT_CONTINUE;
    }
    public void getProtections(obj_id self) throws InterruptedException
    {
        int armorType = getGameObjectType(self);
        if (armor.isFinalArmor(armorType))
        {
            armorType = GOT_armor;
        }
        int armorLevel = -1;
        int armorCategory = -1;
        int armorRow = armor.getArmorDatatableRow(self);
        if (armorRow < 0)
        {
            CustomerServiceLog("armor", "WARNING: could not find armor datatable row for armor " + self);
            return;
        }
        if (armorType == GOT_armor || armorType == GOT_armor_core || armorType == GOT_armor_psg)
        {
            armorLevel = armor.getArmorLevel(self);
            armorCategory = armor.getArmorCategory(self);
            if (armorLevel < 0 || armorCategory < 0)
            {
                return;
            }
        }
        dictionary protections = armor.getArmorSpecialProtections(self);
        float general_protection = armor.getArmorGeneralProtection(self);
        return;
    }
}
