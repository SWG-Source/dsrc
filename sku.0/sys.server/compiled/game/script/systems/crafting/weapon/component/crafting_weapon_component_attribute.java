package script.systems.crafting.weapon.component;

import script.dictionary;
import script.library.craftinglib;
import script.library.utils;
import script.library.weapons;
import script.obj_id;
import script.string_id;

public class crafting_weapon_component_attribute extends script.base_script
{
    public crafting_weapon_component_attribute()
    {
    }
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        String tangible_name = getTemplateName(self);
        if ((tangible_name != null) && tangible_name.equals("object/tangible/component/weapon/corvette_rifle_barrel.iff"))
        {
            if (!hasObjVar(self, "crafting.source_schematic"))
            {
                setCraftedId(self, self);
                setCrafter(self, self);
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int OnGetAttributes(obj_id self, obj_id player, String[] names, String[] attribs) throws InterruptedException
    {
        if(!isIdValid(self) || !exists(self) || self == null || self == obj_id.NULL_ID){
            return SCRIPT_CONTINUE;
        }
        if (isIdValid(self) && exists(self) && hasObjVar(self, "forceComponent") && !isJedi(player) && !isGod(player)){
            return SCRIPT_CONTINUE;
        }
        int idx = utils.getValidAttributeIndex(names);
        if (idx == -1)
        {
            return SCRIPT_CONTINUE;
        }
        if (!hasObjVar(self, weapons.OBJVAR_NEW_WP_COMPONENT))
        {
            if (hasObjVar(self, craftinglib.COMPONENT_ATTRIBUTE_OBJVAR_NAME + "." + "minDamage"))
            {
                names[idx] = "wpn_comp_damage_min";
                int attrib = (int)getFloatObjVar(self, craftinglib.COMPONENT_ATTRIBUTE_OBJVAR_NAME + "." + "minDamage");
                attribs[idx] = Integer.toString(attrib);
                idx++;
                if (idx >= names.length)
                {
                    return SCRIPT_CONTINUE;
                }
            }
            if (hasObjVar(self, craftinglib.COMPONENT_ATTRIBUTE_OBJVAR_NAME + "." + "maxDamage"))
            {
                names[idx] = "wpn_comp_damage_max";
                int attrib = (int)getFloatObjVar(self, craftinglib.COMPONENT_ATTRIBUTE_OBJVAR_NAME + "." + "maxDamage");
                attribs[idx] = Integer.toString(attrib);
                idx++;
                if (idx >= names.length)
                {
                    return SCRIPT_CONTINUE;
                }
            }
            if (hasObjVar(self, craftinglib.COMPONENT_ATTRIBUTE_OBJVAR_NAME + "." + "attackSpeed"))
            {
                names[idx] = "wpn_attack_speed";
                float attrib = getFloatObjVar(self, craftinglib.COMPONENT_ATTRIBUTE_OBJVAR_NAME + "." + "attackSpeed") / 10.0f;
                String attrib_str = Float.toString(attrib);
                if (attrib_str.length() > 6)
                {
                    attrib_str = attrib_str.substring(0, 5);
                }
                attribs[idx] = attrib_str;
                idx++;
                if (idx >= names.length)
                {
                    return SCRIPT_CONTINUE;
                }
            }
            if (hasObjVar(self, craftinglib.COMPONENT_ATTRIBUTE_OBJVAR_NAME + "." + "woundChance"))
            {
                names[idx] = "wpn_wound_chance";
                int attrib = (int)getFloatObjVar(self, craftinglib.COMPONENT_ATTRIBUTE_OBJVAR_NAME + "." + "woundChance");
                attribs[idx] = Integer.toString(attrib);
                idx++;
                if (idx >= names.length)
                {
                    return SCRIPT_CONTINUE;
                }
            }
            if (hasObjVar(self, craftinglib.COMPONENT_ATTRIBUTE_OBJVAR_NAME + "." + "accuracy"))
            {
                names[idx] = "wpn_accuracy";
                int attrib = (int)getFloatObjVar(self, craftinglib.COMPONENT_ATTRIBUTE_OBJVAR_NAME + "." + "accuracy");
                attribs[idx] = Integer.toString(attrib);
                idx++;
                if (idx >= names.length)
                {
                    return SCRIPT_CONTINUE;
                }
            }
            if (hasObjVar(self, craftinglib.COMPONENT_ATTRIBUTE_OBJVAR_NAME + "." + "attackCost"))
            {
                names[idx] = "wpn_attack_cost";
                int attrib = (int)getFloatObjVar(self, craftinglib.COMPONENT_ATTRIBUTE_OBJVAR_NAME + "." + "attackCost");
                attribs[idx] = Integer.toString(attrib);
                idx++;
                if (idx >= names.length)
                {
                    return SCRIPT_CONTINUE;
                }
            }
            if (hasObjVar(self, craftinglib.COMPONENT_ATTRIBUTE_OBJVAR_NAME + "." + "hitPoints"))
            {
                names[idx] = "hitpoints";
                int attrib = (int)getFloatObjVar(self, craftinglib.COMPONENT_ATTRIBUTE_OBJVAR_NAME + "." + "hitPoints");
                attribs[idx] = Integer.toString(attrib);
                idx++;
                if (idx >= names.length)
                {
                    return SCRIPT_CONTINUE;
                }
            }
            if (hasObjVar(self, craftinglib.COMPONENT_ATTRIBUTE_OBJVAR_NAME + "." + "forceCost"))
            {
                names[idx] = "forcecost";
                int attrib = (int)getFloatObjVar(self, craftinglib.COMPONENT_ATTRIBUTE_OBJVAR_NAME + "." + "forceCost");
                attribs[idx] = Integer.toString(attrib);
                idx++;
                if (idx >= names.length)
                {
                    return SCRIPT_CONTINUE;
                }
            }
            if (hasObjVar(self, craftinglib.COMPONENT_ATTRIBUTE_OBJVAR_NAME + "." + "minRange"))
            {
                names[idx] = "minRange";
                int attrib = (int)getFloatObjVar(self, craftinglib.COMPONENT_ATTRIBUTE_OBJVAR_NAME + "." + "minRange");
                attribs[idx] = Integer.toString(attrib);
                idx++;
                if (idx >= names.length)
                {
                    return SCRIPT_CONTINUE;
                }
            }
            if (hasObjVar(self, craftinglib.COMPONENT_ATTRIBUTE_OBJVAR_NAME + "." + "maxRange"))
            {
                names[idx] = "maxRange";
                int attrib = (int)getFloatObjVar(self, craftinglib.COMPONENT_ATTRIBUTE_OBJVAR_NAME + "." + "maxRange");
                attribs[idx] = Integer.toString(attrib);
                idx++;
                if (idx >= names.length)
                {
                    return SCRIPT_CONTINUE;
                }
            }
            if (hasObjVar(self, craftinglib.COMPONENT_ATTRIBUTE_OBJVAR_NAME + "." + "color_new"))
            {
                names[idx] = "color";
                int attrib = getIntObjVar(self, craftinglib.COMPONENT_ATTRIBUTE_OBJVAR_NAME + "." + "color_new");
                String strAsciiId = "saber_color_" + attrib;
                string_id strSpam = new string_id("jedi_spam", strAsciiId);
                String strPackedId = utils.packStringId(strSpam);
                attribs[idx] = strPackedId;
                idx++;
                if (idx >= names.length)
                {
                    return SCRIPT_CONTINUE;
                }
            }
            if (hasObjVar(self, craftinglib.COMPONENT_ATTRIBUTE_OBJVAR_NAME + "." + "combat_critical_ranged"))
            {
                int rangedCritBonus = getIntObjVar(self, craftinglib.COMPONENT_ATTRIBUTE_OBJVAR_NAME + "." + "combat_critical_ranged");
                attribs[idx] = Integer.toString(rangedCritBonus);
                names[idx] = "cat_skill_mod_bonus.@stat_n:combat_critical_ranged";
                idx++;
                if (idx >= names.length)
                {
                    return SCRIPT_CONTINUE;
                }
            }
            if (hasObjVar(self, craftinglib.COMPONENT_ATTRIBUTE_OBJVAR_NAME + "." + "combat_critical_melee"))
            {
                int meleeCritBonus = getIntObjVar(self, craftinglib.COMPONENT_ATTRIBUTE_OBJVAR_NAME + "." + "combat_critical_melee");
                attribs[idx] = Integer.toString(meleeCritBonus);
                names[idx] = "cat_skill_mod_bonus.@stat_n:combat_critical_melee";
                idx++;
                if (idx >= names.length)
                {
                    return SCRIPT_CONTINUE;
                }
            }
        }
        else 
        {
            int coreLevel = 0;
            dictionary weaponCoreDat = new dictionary();
            if (hasObjVar(self, craftinglib.COMPONENT_ATTRIBUTE_OBJVAR_NAME + "." + "coreLevel"))
            {
                names[idx] = "healing_combat_level_required";
                coreLevel = (int)getFloatObjVar(self, craftinglib.COMPONENT_ATTRIBUTE_OBJVAR_NAME + "." + "coreLevel");
                attribs[idx] = Integer.toString(coreLevel);
                idx++;
                if (idx >= names.length)
                {
                    return SCRIPT_CONTINUE;
                }
                weaponCoreDat = weapons.getWeaponCoreData(coreLevel);
            }
            float coreQualityMin = 0.0f;
            float coreQualityMax = 0.0f;
            if (hasObjVar(self, craftinglib.COMPONENT_ATTRIBUTE_OBJVAR_NAME + "." + "coreQualityLow"))
            {
                coreQualityMin = getFloatObjVar(self, craftinglib.COMPONENT_ATTRIBUTE_OBJVAR_NAME + "." + "coreQualityLow");
                coreQualityMax = getFloatObjVar(self, craftinglib.COMPONENT_ATTRIBUTE_OBJVAR_NAME + "." + "coreQualityHigh");
                names[idx] = "cat_wp_dmge_mods.wpn_comp_core_quality_max";
                attribs[idx] = Float.toString(utils.roundFloatByDecimal(100 * coreQualityMax)) + "%";
                idx++;
                if (idx >= names.length)
                {
                    return SCRIPT_CONTINUE;
                }
                names[idx] = "cat_wp_dmge_mods.wpn_comp_core_quality_min";
                attribs[idx] = Float.toString(utils.roundFloatByDecimal(100 * coreQualityMin)) + "%";
                idx++;
                if (idx >= names.length)
                {
                    return SCRIPT_CONTINUE;
                }
            }
            if (hasObjVar(self, weapons.OBJVAR_MODIFIER_GAS_QUALITY_MIN) && getFloatObjVar(self, weapons.OBJVAR_MODIFIER_GAS_QUALITY_MIN) > 0.0f)
            {
                float gasQualityMin = getFloatObjVar(self, weapons.OBJVAR_MODIFIER_GAS_QUALITY_MIN);
                float gasQualityMax = getFloatObjVar(self, weapons.OBJVAR_MODIFIER_GAS_QUALITY_MAX);
                names[idx] = "cat_wp_dmge_mods.gas_quality_max";
                float attrib = utils.roundFloatByDecimal(100 * gasQualityMax);
                attribs[idx] = Float.toString(attrib) + "%";
                idx++;
                if (idx >= names.length)
                {
                    return SCRIPT_CONTINUE;
                }
                names[idx] = "cat_wp_dmge_mods.gas_quality_min";
                attrib = utils.roundFloatByDecimal(100 * gasQualityMin);
                attribs[idx] = Float.toString(attrib) + "%";
                idx++;
                if (idx >= names.length)
                {
                    return SCRIPT_CONTINUE;
                }
            }
            if (hasObjVar(self, weapons.OBJVAR_MODIFIER_MELEE_QUALITY_MIN) && getFloatObjVar(self, weapons.OBJVAR_MODIFIER_MELEE_QUALITY_MIN) > 0.0f)
            {
                float meleeQualityMin = getFloatObjVar(self, weapons.OBJVAR_MODIFIER_MELEE_QUALITY_MIN);
                float meleeQualityMax = getFloatObjVar(self, weapons.OBJVAR_MODIFIER_MELEE_QUALITY_MAX);
                names[idx] = "cat_wp_dmge_mods.melee_quality_max";
                float attrib = utils.roundFloatByDecimal(100 * meleeQualityMax);
                attribs[idx] = Float.toString(attrib) + "%";
                idx++;
                if (idx >= names.length)
                {
                    return SCRIPT_CONTINUE;
                }
                names[idx] = "cat_wp_dmge_mods.melee_quality_min";
                attrib = utils.roundFloatByDecimal(100 * meleeQualityMin);
                attribs[idx] = Float.toString(attrib) + "%";
                idx++;
                if (idx >= names.length)
                {
                    return SCRIPT_CONTINUE;
                }
            }
            if (weapons.isRangedCore(self))
            {
                float pistolMinDamageBonus = coreQualityMin;
                pistolMinDamageBonus += weapons.getWeaponComponentBonusesMinDamage(self);
                int tableMin = weaponCoreDat.getInt("pistol_min_damage");
                int pistolMinDamage = (int)(tableMin * pistolMinDamageBonus);
                float pistolMaxDamageBonus = coreQualityMax;
                pistolMaxDamageBonus += weapons.getWeaponComponentBonusesMaxDamage(self);
                int tableMax = weaponCoreDat.getInt("pistol_max_damage");
                int pistolMaxDamage = (int)(tableMax * pistolMaxDamageBonus);
                names[idx] = "cat_wp_dmge_range.wpn_pistol_damage_range";
                attribs[idx] = "" + pistolMinDamage + "-" + pistolMaxDamage;
                idx++;
                if (idx >= names.length)
                {
                    return SCRIPT_CONTINUE;
                }
                float carbineMinDamageBonus = coreQualityMin;
                carbineMinDamageBonus += weapons.getWeaponComponentBonusesMinDamage(self);
                tableMin = weaponCoreDat.getInt("carbine_min_damage");
                int carbineMinDamage = (int)(tableMin * carbineMinDamageBonus);
                float carbineMaxDamageBonus = coreQualityMax;
                carbineMaxDamageBonus += weapons.getWeaponComponentBonusesMaxDamage(self);
                tableMax = weaponCoreDat.getInt("carbine_max_damage");
                int carbineMaxDamage = (int)(tableMax * carbineMaxDamageBonus);
                names[idx] = "cat_wp_dmge_range.wpn_carbine_damage_range";
                attribs[idx] = "" + carbineMinDamage + "-" + carbineMaxDamage;
                idx++;
                if (idx >= names.length)
                {
                    return SCRIPT_CONTINUE;
                }
                float rifleMinDamageBonus = coreQualityMin;
                rifleMinDamageBonus += weapons.getWeaponComponentBonusesMinDamage(self);
                tableMin = weaponCoreDat.getInt("rifle_min_damage");
                int rifleMinDamage = (int)(tableMin * rifleMinDamageBonus);
                float rifleMaxDamageBonus = coreQualityMax;
                rifleMaxDamageBonus += weapons.getWeaponComponentBonusesMaxDamage(self);
                tableMax = weaponCoreDat.getInt("rifle_max_damage");
                int rifleMaxDamage = (int)(tableMax * rifleMaxDamageBonus);
                names[idx] = "cat_wp_dmge_range.wpn_rifle_damage_range";
                attribs[idx] = "" + rifleMinDamage + "-" + rifleMaxDamage;
                idx++;
                if (idx >= names.length)
                {
                    return SCRIPT_CONTINUE;
                }
            }
            if (weapons.isHeavyCore(self))
            {
                float directionalMinDamageBonus = coreQualityMin;
                directionalMinDamageBonus += weapons.getWeaponComponentBonusesMinDamage(self);
                int tableMin = weaponCoreDat.getInt("directional_min_damage");
                int directionalMinDamage = (int)(tableMin * directionalMinDamageBonus);
                float directionalMaxDamageBonus = coreQualityMax;
                directionalMaxDamageBonus += weapons.getWeaponComponentBonusesMaxDamage(self);
                int tableMax = weaponCoreDat.getInt("directional_max_damage");
                int directionalMaxDamage = (int)(tableMax * directionalMaxDamageBonus);
                names[idx] = "cat_wp_dmge_range.wpn_directional_damage_range";
                attribs[idx] = "" + directionalMinDamage + "-" + directionalMaxDamage;
                idx++;
                if (idx >= names.length)
                {
                    return SCRIPT_CONTINUE;
                }
                float heavyMinDamageBonus = coreQualityMin;
                heavyMinDamageBonus += weapons.getWeaponComponentBonusesMinDamage(self);
                tableMin = weaponCoreDat.getInt("heavy_min_damage");
                int heavyMinDamage = (int)(tableMin * heavyMinDamageBonus);
                float heavyMaxDamageBonus = coreQualityMax;
                heavyMaxDamageBonus += weapons.getWeaponComponentBonusesMaxDamage(self);
                tableMax = weaponCoreDat.getInt("heavy_max_damage");
                int heavyMaxDamage = (int)(tableMax * heavyMaxDamageBonus);
                names[idx] = "cat_wp_dmge_range.wpn_heavy_damage_range";
                attribs[idx] = "" + heavyMinDamage + "-" + heavyMaxDamage;
                idx++;
                if (idx >= names.length)
                {
                    return SCRIPT_CONTINUE;
                }
            }
            if (weapons.isMeleeCore(self))
            {
                float meleeMinDamageBonus = coreQualityMin;
                meleeMinDamageBonus += weapons.getWeaponComponentBonusesMinDamage(self);
                int tableMin = weaponCoreDat.getInt("melee_min_damage");
                int meleeMinDamage = (int)(tableMin * meleeMinDamageBonus);
                float meleeMaxDamageBonus = coreQualityMax;
                meleeMaxDamageBonus += weapons.getWeaponComponentBonusesMaxDamage(self);
                int tableMax = weaponCoreDat.getInt("melee_max_damage");
                int meleeMaxDamage = (int)(tableMax * meleeMaxDamageBonus);
                names[idx] = "cat_wp_dmge_range.wpn_melee_damage_range";
                attribs[idx] = "" + meleeMinDamage + "-" + meleeMaxDamage;
                idx++;
                if (idx >= names.length)
                {
                    return SCRIPT_CONTINUE;
                }
            }
            if (hasObjVar(self, craftinglib.COMPONENT_ATTRIBUTE_OBJVAR_NAME + "." + "elementalType"))
            {
                float eleType = getFloatObjVar(self, craftinglib.COMPONENT_ATTRIBUTE_OBJVAR_NAME + "." + "elementalType");
                if (eleType >= 0.0f)
                {
                    names[idx] = "cat_wp_ele_dmge_range.wpn_elemental_type";
                    attribs[idx] = "@obj_attr_n:" + weapons.getDamageTypeString((int)eleType);
                    idx++;
                    if (idx >= names.length)
                    {
                        return SCRIPT_CONTINUE;
                    }
                }
            }
            if (hasObjVar(self, craftinglib.COMPONENT_ATTRIBUTE_OBJVAR_NAME + "." + "elementalValue"))
            {
                float eleValue = getFloatObjVar(self, craftinglib.COMPONENT_ATTRIBUTE_OBJVAR_NAME + "." + "elementalValue");
                if (eleValue >= 0.0f)
                {
                    float eletValue = utils.roundFloatByDecimal(100 * eleValue);
                    names[idx] = "cat_wp_ele_dmge_range.wpn_elemental_value";
                    attribs[idx] = Float.toString(eletValue) + "%";
                    idx++;
                    if (idx >= names.length)
                    {
                        return SCRIPT_CONTINUE;
                    }
                }
            }
            if (hasObjVar(self, weapons.OBJVAR_ELEMENTAL_TYPE))
            {
                float eleType = getFloatObjVar(self, weapons.OBJVAR_ELEMENTAL_TYPE);
                if (eleType >= 0.0f)
                {
                    names[idx] = "cat_wp_ele_dmge_range.wpn_elemental_type";
                    attribs[idx] = "@obj_attr_n:" + weapons.getDamageTypeString((int)eleType);
                    idx++;
                    if (idx >= names.length)
                    {
                        return SCRIPT_CONTINUE;
                    }
                }
            }
            if (hasObjVar(self, weapons.OBJVAR_ELEMENTAL_VALUE))
            {
                if (weaponCoreDat != null && !weaponCoreDat.isEmpty())
                {
                    float eleValue = getFloatObjVar(self, weapons.OBJVAR_ELEMENTAL_VALUE);
                    if (eleValue >= 0.0f)
                    {
                        float eletValue = utils.roundFloatByDecimal(100 * eleValue);
                        names[idx] = "cat_wp_ele_dmge_range.wpn_elemental_value";
                        attribs[idx] = Float.toString(eletValue) + "%";
                        idx++;
                        if (idx >= names.length)
                        {
                            return SCRIPT_CONTINUE;
                        }
                    }
                    if (weapons.isRangedCore(self))
                    {
                        int tableValue = weaponCoreDat.getInt("elemental_pistol_average_damage");
                        int pistolEleValue = (int)(tableValue * eleValue);
                        names[idx] = "cat_wp_ele_dmge_range.wpn_pistol_elemental_damage";
                        attribs[idx] = Integer.toString(pistolEleValue);
                        idx++;
                        if (idx >= names.length)
                        {
                            return SCRIPT_CONTINUE;
                        }
                        tableValue = weaponCoreDat.getInt("elemental_carbine_average_damage");
                        int carbineEleValue = (int)(tableValue * eleValue);
                        names[idx] = "cat_wp_ele_dmge_range.wpn_carbine_elemental_damage";
                        attribs[idx] = Integer.toString(carbineEleValue);
                        idx++;
                        if (idx >= names.length)
                        {
                            return SCRIPT_CONTINUE;
                        }
                        tableValue = weaponCoreDat.getInt("elemental_rifle_average_damage");
                        int rifleEleValue = (int)(tableValue * eleValue);
                        names[idx] = "cat_wp_ele_dmge_range.wpn_rifle_elemental_damage";
                        attribs[idx] = Integer.toString(rifleEleValue);
                        idx++;
                        if (idx >= names.length)
                        {
                            return SCRIPT_CONTINUE;
                        }
                    }
                    if (weapons.isMeleeCore(self))
                    {
                        int tableValue = weaponCoreDat.getInt("elemental_melee_average_damage");
                        int meleeEleValue = (int)(tableValue * eleValue);
                        names[idx] = "cat_wp_ele_dmge_range.wpn_melee_elemental_damage";
                        attribs[idx] = Integer.toString(meleeEleValue);
                        idx++;
                        if (idx >= names.length)
                        {
                            return SCRIPT_CONTINUE;
                        }
                    }
                    if (weapons.isHeavyCore(self))
                    {
                        int tableValue = weaponCoreDat.getInt("elemental_directional_average_damage");
                        int directEleValue = (int)(tableValue * eleValue);
                        names[idx] = "cat_wp_ele_dmge_range.wpn_directional_elemental_damage";
                        attribs[idx] = Integer.toString(directEleValue);
                        idx++;
                        if (idx >= names.length)
                        {
                            return SCRIPT_CONTINUE;
                        }
                        tableValue = weaponCoreDat.getInt("elemental_heavy_average_damage");
                        int heavyEleValue = (int)(tableValue * eleValue);
                        names[idx] = "cat_wp_ele_dmge_range.wpn_heavy_elemental_damage";
                        attribs[idx] = Integer.toString(heavyEleValue);
                        idx++;
                        if (idx >= names.length)
                        {
                            return SCRIPT_CONTINUE;
                        }
                    }
                }
            }
            if (hasObjVar(self, weapons.OBJVAR_MODIFIER_COMPONENT_BONUS_MIN))
            {
                float compBonusMin = getFloatObjVar(self, weapons.OBJVAR_MODIFIER_COMPONENT_BONUS_MIN);
                float compBonusMax = getFloatObjVar(self, weapons.OBJVAR_MODIFIER_COMPONENT_BONUS_MAX);
                compBonusMax = utils.roundFloatByDecimal(100 * compBonusMax);
                attribs[idx] = Float.toString(compBonusMax) + "%";
                names[idx] = "cat_wp_dmge_mods.wpn_component_bonus_max";
                idx++;
                if (idx >= names.length)
                {
                    return SCRIPT_CONTINUE;
                }
                compBonusMin = utils.roundFloatByDecimal(100 * compBonusMin);
                attribs[idx] = Float.toString(compBonusMin) + "%";
                names[idx] = "cat_wp_dmge_mods.wpn_component_bonus_min";
                idx++;
                if (idx >= names.length)
                {
                    return SCRIPT_CONTINUE;
                }
            }
            if (hasObjVar(self, craftinglib.OBJVAR_RE_VALUE) && !weapons.isRangedCore(self) && !weapons.isMeleeCore(self) && !weapons.isHeavyCore(self))
            {
                names[idx] = "@crafting:power_bit_power";
                attribs[idx] = "" + getFloatObjVar(self, craftinglib.OBJVAR_RE_VALUE);
                idx++;
                if (idx >= names.length)
                {
                    return SCRIPT_CONTINUE;
                }
                names[idx] = "@crafting:mod_bit_type";
                attribs[idx] = "@stat_n:" + getStringObjVar(self, craftinglib.OBJVAR_RE_STAT_MODIFIED);
                idx++;
                if (idx >= names.length)
                {
                    return SCRIPT_CONTINUE;
                }
            }
        }
        return SCRIPT_CONTINUE;
    }
}
