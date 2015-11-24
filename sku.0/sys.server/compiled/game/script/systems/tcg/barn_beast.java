package script.systems.tcg;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.ai_lib;
import script.library.beast_lib;
import script.library.callable;
import script.library.pet_lib;
import script.library.prose;
import script.library.target_dummy;
import script.library.tcg;
import script.library.utils;

public class barn_beast extends script.base_script
{
    public barn_beast()
    {
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        ai_lib.setDefaultCalmBehavior(self, ai_lib.BEHAVIOR_LOITER);
        return SCRIPT_CONTINUE;
    }
    public int OnObjectMenuRequest(obj_id self, obj_id player, menu_info mi) throws InterruptedException
    {
        obj_id barn = utils.getObjIdScriptVar(self, "barnId");
        if (isIdValid(barn))
        {
            obj_id owner = getOwner(barn);
            if (isIdValid(owner) && owner == player)
            {
                int menuPlacement = mi.addRootMenu(menu_info_types.SERVER_MENU1, new string_id(pet_lib.MENU_FILE, "menu_store"));
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int OnObjectMenuSelect(obj_id self, obj_id player, int item) throws InterruptedException
    {
        if (item == menu_info_types.SERVER_MENU1)
        {
            obj_id barn = utils.getObjIdScriptVar(self, "barnId");
            if (isIdValid(barn))
            {
                obj_id owner = getOwner(barn);
                if (isIdValid(owner) && owner == player)
                {
                    String storageSlot = utils.getStringScriptVar(self, "barnSlot");
                    utils.removeScriptVar(barn, storageSlot);
                    String beastName = beast_lib.getBeastLocalizedName(self);
                    if (beastName == null || beastName.length() < 1)
                    {
                        beastName = "Unknown";
                    }
                    if (hasObjVar(barn, "barnStorage." + storageSlot + ".beast.beastName"))
                    {
                        beastName = getStringObjVar(barn, "barnStorage." + storageSlot + ".beast.beastName");
                    }
                    else if (hasObjVar(barn, "barnStorage." + storageSlot + ".tempName"))
                    {
                        beastName = getStringObjVar(barn, "barnStorage." + storageSlot + ".tempName");
                    }
                    string_id message = new string_id("tcg", "barn_beast_display_stored");
                    if (tcg.isBarnLiteDevice(barn))
                    {
                        message = new string_id("tcg", "barn_lite_beast_display_stored");
                    }
                    prose_package pp = prose.getPackage(message, player, player);
                    prose.setTO(pp, beastName);
                    sendSystemMessageProse(player, pp);
                    removeObjVar(barn, "barnStorage." + storageSlot + "." + tcg.BEAST_ROAMING);
                    destroyObject(self);
                }
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int OnPack(obj_id self, dictionary params) throws InterruptedException
    {
        if (!isIdValid(self))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id barn = utils.getObjIdScriptVar(self, "barnId");
        if (isIdValid(barn))
        {
            String storageSlot = utils.getStringScriptVar(self, "barnSlot");
            if (utils.hasScriptVar(barn, storageSlot))
            {
                utils.removeScriptVar(barn, storageSlot);
            }
            removeObjVar(barn, "barnStorage." + storageSlot + "." + tcg.BEAST_ROAMING);
        }
        destroyObject(self);
        return SCRIPT_CONTINUE;
    }
    public int OnDestroy(obj_id self) throws InterruptedException
    {
        target_dummy.removeTargetDummyFromPermissions(self);
        return SCRIPT_CONTINUE;
    }
    public int OnGetAttributes(obj_id self, obj_id player, String[] names, String[] attribs) throws InterruptedException
    {
        if (!isMob(self))
        {
            return SCRIPT_CONTINUE;
        }
        int idx = utils.getValidAttributeIndex(names);
        obj_id barn = utils.getObjIdScriptVar(self, "barnId");
        if (!isIdValid(barn))
        {
            return SCRIPT_CONTINUE;
        }
        String storageSlot = utils.getStringScriptVar(self, "barnSlot");
        if (hasObjVar(barn, "barnStorage." + storageSlot + "." + beast_lib.OBJVAR_BEAST_ENGINEER))
        {
            String creatorName = getStringObjVar(barn, "barnStorage." + storageSlot + "." + beast_lib.OBJVAR_BEAST_ENGINEER);
            names[idx] = "bm_creator";
            attribs[idx] = creatorName;
            idx++;
            if (idx >= names.length)
            {
                return SCRIPT_CONTINUE;
            }
        }
        names[idx] = "health";
        attribs[idx] = " " + (int)getMaxAttrib(self, HEALTH);
        idx++;
        int level = getLevel(self);
        if (level < 90)
        {
            dictionary beastStatsNextLevel = utils.dataTableGetRow(beast_lib.BEASTS_STATS, level - 1);
            int experienceLastLevel = 0;
            int experienceNextLevel = 0;
            if (level > 1)
            {
                dictionary beastStatsLastLevel = utils.dataTableGetRow(beast_lib.BEASTS_STATS, level - 2);
                if (beastStatsLastLevel == null || beastStatsLastLevel.size() <= 0)
                {
                    return SCRIPT_CONTINUE;
                }
                experienceLastLevel = beastStatsLastLevel.getInt("XP");
            }
            if (beastStatsNextLevel == null || beastStatsNextLevel.size() <= 0)
            {
                return SCRIPT_CONTINUE;
            }
            experienceNextLevel = beastStatsNextLevel.getInt("XP");
            int beastExperience = beast_lib.getBeastExperience(self);
            int experienceProgress = beastExperience - experienceLastLevel;
            int experienceNeeded = experienceNextLevel - experienceLastLevel;
            int experiencePercentage = 0;
            if (experienceProgress > 0)
            {
                experiencePercentage = (int)(((float)experienceProgress / (float)experienceNeeded) * 100);
            }
            names[idx] = "level_progress";
            attribs[idx] = "" + experiencePercentage + "%";
            idx++;
        }
        if (beast_lib.getBeastCanLevel(self))
        {
            names[idx] = "exp_on";
            attribs[idx] = "true";
            idx++;
        }
        else 
        {
            names[idx] = "exp_off";
            attribs[idx] = "true";
            idx++;
        }
        names[idx] = beast_lib.BEAST_MOOD_TITLE;
        int currentHappiness = 0;
        if (hasObjVar(barn, "barnStorage." + storageSlot + "." + beast_lib.OBJVAR_BEAST_HAPPINESS))
        {
            currentHappiness = getIntObjVar(barn, "barnStorage." + storageSlot + "." + beast_lib.OBJVAR_BEAST_HAPPINESS);
        }
        String currentHappinessString = utils.packStringId(beast_lib.convertHappinessString(currentHappiness));
        if (currentHappinessString != null)
        {
            attribs[idx] = "" + currentHappinessString;
            idx++;
            if (idx >= names.length)
            {
                return SCRIPT_CONTINUE;
            }
        }
        names[idx] = beast_lib.BEAST_LOYALTY_TITLE;
        int currentLoyalty = 0;
        if (hasObjVar(barn, "barnStorage." + storageSlot + "." + beast_lib.PET_LOYALTY_LEVEL_OBJVAR))
        {
            currentLoyalty = getIntObjVar(barn, "barnStorage." + storageSlot + "." + beast_lib.PET_LOYALTY_LEVEL_OBJVAR);
        }
        String currentLoyaltyString = utils.packStringId(beast_lib.convertLoyaltyString(currentLoyalty));
        if (currentLoyaltyString != null)
        {
            attribs[idx] = "" + currentLoyaltyString;
            idx++;
            if (idx >= names.length)
            {
                return SCRIPT_CONTINUE;
            }
        }
        names[idx] = "armorhpmax";
        attribs[idx] = "" + (utils.getIntScriptVar(self, "beast.display.armor") + getEnhancedSkillStatisticModifier(self, "private_armor_bonus"));
        idx++;
        obj_id beastWeapon = getCurrentWeapon(self);
        if (isIdValid(beastWeapon))
        {
            int minDamage = getWeaponMinDamage(beastWeapon);
            int maxDamage = getWeaponMaxDamage(beastWeapon);
            int expertiseDamageBonus = getEnhancedSkillStatisticModifierUncapped(self, "expertise_damage_all");
            minDamage = (int)(minDamage * (1.0f + ((float)expertiseDamageBonus / 100.0f)));
            maxDamage = (int)(maxDamage * (1.0f + ((float)expertiseDamageBonus / 100.0f)));
            float weaponSpeed = getWeaponAttackSpeed(beastWeapon);
            names[idx] = "damage";
            attribs[idx] = "" + minDamage + " - " + maxDamage;
            idx++;
            names[idx] = "attackspeed";
            attribs[idx] = "" + weaponSpeed;
            idx++;
            float beastDPS = utils.roundFloatByDecimal((minDamage + maxDamage) / weaponSpeed / 2);
            names[idx] = "basedps";
            attribs[idx] = "" + beastDPS;
            idx++;
        }
        else 
        {
            obj_id defaultWeapon = getDefaultWeapon(self);
            if (isIdValid(defaultWeapon))
            {
                int minDamage = getWeaponMinDamage(defaultWeapon);
                int maxDamage = getWeaponMaxDamage(defaultWeapon);
                float weaponSpeed = getWeaponAttackSpeed(defaultWeapon);
                int expertiseDamageBonus = getEnhancedSkillStatisticModifierUncapped(self, "expertise_damage_all");
                minDamage = (int)(minDamage * (1.0f + ((float)expertiseDamageBonus / 100.0f)));
                maxDamage = (int)(maxDamage * (1.0f + ((float)expertiseDamageBonus / 100.0f)));
                names[idx] = "damage";
                attribs[idx] = "" + minDamage + " - " + maxDamage;
                idx++;
                names[idx] = "attackspeed";
                attribs[idx] = "" + weaponSpeed;
                idx++;
                float beastDPS = utils.roundFloatByDecimal((minDamage + maxDamage) / weaponSpeed / 2);
                names[idx] = "basedps";
                attribs[idx] = "" + beastDPS;
                idx++;
            }
        }
        if (beast_lib.DISPLAY_NAMES.length == beast_lib.ARRAY_BEAST_INCUBATION_STATS.length && beast_lib.DISPLAY_NAMES.length == beast_lib.DISPLAY_CONVERSION_RATES.length)
        {
            for (int i = 0; i < beast_lib.DISPLAY_NAMES.length; ++i)
            {
                String name = beast_lib.DISPLAY_NAMES[i];
                if (name.indexOf("_skill") < 0)
                {
                    if (!name.equals("block_value_bonus"))
                    {
                        names[idx] = beast_lib.DISPLAY_NAMES[i];
                        attribs[idx] = "" + utils.roundFloatByDecimal((utils.getFloatScriptVar(self, beast_lib.ARRAY_BEAST_INCUBATION_STATS[i]) * beast_lib.DISPLAY_CONVERSION_RATES[i])) + "%";
                        idx++;
                    }
                    else 
                    {
                        names[idx] = beast_lib.DISPLAY_NAMES[i];
                        attribs[idx] = "" + utils.roundFloatByDecimal(utils.getFloatScriptVar(self, beast_lib.ARRAY_BEAST_INCUBATION_STATS[i]));
                        idx++;
                    }
                    continue;
                }
                else 
                {
                    names[idx] = beast_lib.DISPLAY_NAMES[i];
                    attribs[idx] = "" + utils.roundFloatByDecimal(utils.getFloatScriptVar(self, beast_lib.ARRAY_BEAST_INCUBATION_STATS[i]));
                    idx++;
                    continue;
                }
            }
        }
        float glanceReduct = beast_lib.getBeastModGlancingReduction(self);
        if (glanceReduct > 0)
        {
            names[idx] = "bm_glance_reduct";
            attribs[idx] = "" + glanceReduct + "%";
            idx++;
            if (idx >= names.length)
            {
                return SCRIPT_CONTINUE;
            }
        }
        float damageReduct = beast_lib.getBeastModDamageReduction(self);
        if (damageReduct > 0)
        {
            names[idx] = "bm_damage_reduct";
            attribs[idx] = "" + damageReduct + "%";
            idx++;
            if (idx >= names.length)
            {
                return SCRIPT_CONTINUE;
            }
        }
        float punishReduct = beast_lib.getBeastModPunishingReduction(self);
        if (punishReduct > 0)
        {
            names[idx] = "bm_punish_reduct";
            attribs[idx] = "" + punishReduct + "%";
            idx++;
            if (idx >= names.length)
            {
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_CONTINUE;
    }
}
