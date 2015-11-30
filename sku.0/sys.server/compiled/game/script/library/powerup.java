package script.library;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.utils;
import java.util.Enumeration;
import java.lang.Integer;
import script.library.combat;
import script.library.jedi;

public class powerup extends script.base_script
{
    public powerup()
    {
    }
    public static final int DEFAULT_USE_COUNT = 500;
    public static final String VAR_POWERUP_BASE = "powerup";
    public static final String SCRIPT_WEAPON = VAR_POWERUP_BASE + ".weapon";
    public static final String VAR_POWERUP_USES_LEFT = VAR_POWERUP_BASE + ".usesLeft";
    public static final String VAR_POWERUP_IS_ACTIVE = VAR_POWERUP_BASE + ".isActive";
    public static final String VAR_POWERUP_EFFECT = VAR_POWERUP_BASE + ".effect";
    public static final String VAR_POWERUP_EFFICIENCY = VAR_POWERUP_BASE + ".efficiency";
    public static final String VAR_POWERUP_DAMAGE = VAR_POWERUP_BASE + ".damage";
    public static final String VAR_POWERUP_SPEED = VAR_POWERUP_BASE + ".speed";
    public static final String VAR_POWERUP_ACCURACY = VAR_POWERUP_BASE + ".accuracy";
    public static final String VAR_POWERUP_ACTION = VAR_POWERUP_BASE + ".actionCost";
    public static final String VAR_POWERUP_WOUND = VAR_POWERUP_BASE + ".wound";
    public static final String VAR_POWERUP_DECAY_CHANCE = VAR_POWERUP_BASE + ".decayChance";
    public static final String VAR_POWERUP_DECAY_AMOUNT = VAR_POWERUP_BASE + ".decayAmount";
    public static final String VAR_POWERUP_ELEMENTAL_DAMAGE = VAR_POWERUP_BASE + ".elementalDamage";
    public static final String VAR_POWERUP_TYPE = "powerupType";
    public static final int CHANCE_TO_DECAY = 75;
    public static final int TYPE_MELEE = 1;
    public static final int TYPE_RANGED = 2;
    public static final int TYPE_GRENADE = 3;
    public static final String STF = "powerup";
    public static final string_id PROSE_POWERUP_APPLY = new string_id(STF, "prose_pup_apply");
    public static final string_id PROSE_POWERUP_EXPIRE = new string_id(STF, "prose_pup_expire");
    public static final string_id PROSE_ALREADY_POWERED = new string_id(STF, "prose_already_powered");
    public static final string_id PROSE_APPLY_RESTRICTED = new string_id(STF, "prose_apply_restricted");
    public static void getWeaponAttributes(obj_id player, obj_id weapon, String[] names, String[] attribs, boolean forWeapon) throws InterruptedException
    {
        int idx = utils.getValidAttributeIndex(names);
        if (idx == -1)
        {
            return;
        }
        java.text.DecimalFormat percentFormat = new java.text.DecimalFormat("+ %##.##;- %##.##");
        java.text.DecimalFormat intFormat = new java.text.DecimalFormat("+ ##.##;- ##.##");
        java.text.DecimalFormat formatter = forWeapon ? intFormat : percentFormat;
        names[idx] = "cat_pup.pup_uses";
        attribs[idx] = Integer.toString(getIntObjVar(weapon, powerup.VAR_POWERUP_USES_LEFT));
        idx++;
        obj_var_list ovl = getObjVarList(weapon, powerup.VAR_POWERUP_BASE);
        if (ovl != null)
        {
            int numItems = ovl.getNumItems();
            for (int i = 0; i < numItems; i++)
            {
                obj_var ov = ovl.getObjVar(i);
                String name = ov.getName();
                String varpath = powerup.VAR_POWERUP_BASE + "." + name;
                float mod = getFloatObjVar(weapon, varpath);
                String log;
                if (name.equals("damage"))
                {
                    names[idx] = "cat_pup.pup_damage";
                    attribs[idx] = formatter.format(mod);
                }
                else if (name.equals("speed"))
                {
                    names[idx] = "cat_pup.pup_speed";
                    attribs[idx] = formatter.format(mod);
                }
                else if (name.equals("accuracy"))
                {
                    names[idx] = "cat_pup.pup_accuracy";
                    attribs[idx] = intFormat.format(mod);
                }
                else if (name.equals("wound"))
                {
                    names[idx] = "cat_pup.pup_wound";
                    attribs[idx] = formatter.format(mod);
                }
                else if (name.equals("elementalDamage"))
                {
                    names[idx] = "cat_pup.pup_elemental";
                    attribs[idx] = formatter.format(mod);
                }
                else if (name.equals("actionCost"))
                {
                    names[idx] = "cat_pup.pup_action";
                    attribs[idx] = formatter.format(mod);
                }
                else 
                {
                    idx--;
                }
                idx++;
            }
        }
    }
    public static boolean hasPowerUpInstalled(obj_id weapon) throws InterruptedException
    {
        return hasObjVar(weapon, VAR_POWERUP_BASE);
    }
    public static boolean powerUpDecay(obj_id weapon) throws InterruptedException
    {
        if (!hasPowerUpInstalled(weapon))
        {
            return false;
        }
        return (rand(1, 100) < CHANCE_TO_DECAY);
    }
    public static boolean applyPowerup(obj_id player, obj_id powerup, obj_id target) throws InterruptedException
    {
        if (!isIdValid(player) || !isIdValid(powerup) || !isIdValid(target))
        {
            return false;
        }
        if (!hasObjVar(powerup, VAR_POWERUP_BASE))
        {
            return false;
        }
        if (hasPowerUpInstalled(target))
        {
            prose_package ppAlreadyPowered = prose.getPackage(PROSE_ALREADY_POWERED, target);
            sendSystemMessageProse(player, ppAlreadyPowered);
            return false;
        }
        int iGot = getGameObjectType(powerup);
        if (!isGameObjectTypeOf(iGot, GOT_powerup_weapon))
        {
            LOG("powerup", "GOT Failure");
            sendSystemMessage(player, new string_id("powerup_d", "apply_failed_type_unknown"));
            return false;
        }
        if (jedi.isLightsaber(target))
        {
            sendSystemMessage(player, new string_id("powerup_d", "apply_fail_saber"));
            return false;
        }
        if (static_item.isStaticItem(target))
        {
            sendSystemMessage(player, new string_id("powerup_d", "apply_fail_static"));
            return false;
        }
        if (!hasObjVar(powerup, VAR_POWERUP_TYPE))
        {
            sendSystemMessage(player, new string_id("powerup_d", "apply_failed_type_unknown"));
            LOG("powerup", VAR_POWERUP_TYPE + " objvar failure");
            return false;
        }
        int powerupType = getIntObjVar(powerup, VAR_POWERUP_TYPE);
        LOG("powerup", "Type=" + powerupType);
        switch (powerupType)
        {
            case TYPE_MELEE:
            if (!combat.isMeleeWeapon(target))
            {
                sendSystemMessage(player, new string_id("powerup_d", "apply_fail_not_melee"));
                LOG("powerup", "Melee weapon failure");
                return false;
            }
            break;
            case TYPE_RANGED:
            if (!combat.isRangedWeapon(target))
            {
                sendSystemMessage(player, new string_id("powerup_d", "apply_fail_not_ranged"));
                LOG("powerup", "Ranged weapon failure");
                return false;
            }
            break;
            case TYPE_GRENADE:
            int weaponType = getWeaponType(target);
            if (weaponType != WEAPON_TYPE_THROWN)
            {
                sendSystemMessage(player, new string_id("powerup_d", "apply_fail_not_grenade"));
                LOG("powerup", "Grenade weapon failure");
                return false;
            }
            break;
            default:
            sendSystemMessage(player, new string_id("powerup_d", "apply_failed_type_unknown"));
            LOG("powerup", "Default faulure");
            return false;
        }
        String template = getTemplateName(powerup);
        if (template.equals("object/tangible/powerup/weapon/ranged_power.iff") || template.equals("object/tangible/powerup/weapon/melee_element_dispursal_tuner.iff"))
        {
            int elementalDamage = getWeaponElementalValue(target);
            if (elementalDamage < 1)
            {
                sendSystemMessage(player, new string_id("powerup_d", "apply_fail_no_elemental"));
                LOG("powerup", "Elemental failure");
                return false;
            }
        }
        float speedMod = 0;
        float accuracyMod = 0;
        float woundMod = 0;
        float actionMod = 0;
        float damageMod = 0;
        float elementalMod = 0;
        obj_var_list ovl = getObjVarList(powerup, VAR_POWERUP_BASE);
        if (ovl != null)
        {
            int numItems = ovl.getNumItems();
            for (int i = 0; i < numItems; i++)
            {
                obj_var ov = ovl.getObjVar(i);
                String name = ov.getName();
                String varpath = VAR_POWERUP_BASE + "." + name;
                if (name.equals("speed"))
                {
                    speedMod = ov.getFloatData();
                }
                else if (name.equals("damage"))
                {
                    damageMod = ov.getFloatData();
                }
                else if (name.equals("accuracy"))
                {
                    accuracyMod = ov.getFloatData();
                }
                else if (name.equals("wound"))
                {
                    woundMod = ov.getFloatData();
                }
                else if (name.equals("elementalDamage"))
                {
                    elementalMod = ov.getFloatData();
                }
                else if (name.equals("actionCost"))
                {
                    actionMod = ov.getFloatData();
                }
            }
        }
        LOG("powerup", "----------- Applying powerup ----------");
        if (speedMod != 0)
        {
            float oldSpeed = getWeaponAttackSpeed(target);
            float newSpeed = oldSpeed + (oldSpeed * speedMod);
            LOG("powerup", "oldSpeed=" + oldSpeed + ", newSpeed=" + newSpeed + ", diff=" + (oldSpeed - newSpeed));
            setWeaponAttackSpeed(target, newSpeed);
            setObjVar(target, VAR_POWERUP_SPEED, oldSpeed - newSpeed);
        }
        if (accuracyMod != 0)
        {
            float oldAccuracy = getWeaponAccuracy(target);
            float newAccuracy = oldAccuracy + accuracyMod;
            LOG("powerup", "oldAccuracy=" + oldAccuracy + ", newAccuracy=" + newAccuracy + ", diff=" + (newAccuracy - ((int)oldAccuracy)));
            setWeaponAccuracy(target, (int)newAccuracy);
            setObjVar(target, VAR_POWERUP_ACCURACY, newAccuracy - ((int)oldAccuracy));
        }
        if (woundMod != 0)
        {
            float oldWounds = getWeaponWoundChance(target);
            float newWounds = oldWounds + (oldWounds * woundMod);
            LOG("powerup", "oldWounds=" + oldWounds + ", newWounds=" + newWounds + ", diff=" + (newWounds - oldWounds));
            setWeaponWoundChance(target, newWounds);
            setObjVar(target, VAR_POWERUP_WOUND, newWounds - oldWounds);
        }
        if (actionMod != 0)
        {
            float oldAction = getWeaponAttackCost(target);
            float newAction = (float)Math.floor(oldAction + (oldAction * actionMod));
            LOG("powerup", "oldAction=" + oldAction + ", newAction=" + newAction + ", diff=" + (newAction - oldAction));
            setWeaponAttackCost(target, (int)newAction);
            setObjVar(target, VAR_POWERUP_ACTION, newAction - oldAction);
        }
        if (damageMod != 0)
        {
            int oldMinDamage = getWeaponMinDamage(target);
            int oldMaxDamage = getWeaponMaxDamage(target);
            int newMinDamage = (int)Math.floor(oldMinDamage + (oldMinDamage * damageMod));
            if (newMinDamage < 1)
            {
                newMinDamage = 1;
            }
            float delta = (int)(newMinDamage - oldMinDamage);
            int newMaxDamage = oldMaxDamage + (int)delta;
            LOG("powerup", "oldMinDamage=" + oldMinDamage + ", newMinDamage=" + newMinDamage + ", delta=" + delta);
            LOG("powerup", "oldMaxDamage=" + oldMaxDamage + ", newMaxDamage=" + newMaxDamage + ", delta=" + delta);
            setWeaponMinDamage(target, newMinDamage);
            setWeaponMaxDamage(target, newMaxDamage);
            setObjVar(target, VAR_POWERUP_DAMAGE, delta);
            weapons.setWeaponData(target);
        }
        if (elementalMod != 0)
        {
            float oldEDamage = getWeaponElementalValue(target);
            float newEDamage = (int)Math.floor(oldEDamage + (oldEDamage * elementalMod));
            float delta = (int)(newEDamage - oldEDamage);
            LOG("powerup", "oldEDamage=" + oldEDamage + ", newEDamage=" + newEDamage + ", diff=" + delta);
            setWeaponElementalValue(target, (int)newEDamage);
            setObjVar(target, VAR_POWERUP_ELEMENTAL_DAMAGE, newEDamage - ((int)oldEDamage));
        }
        LOG("powerup", "----------- Done Applying powerup ----------");
        destroyObject(powerup);
        attachScript(target, SCRIPT_WEAPON);
        prose_package ppApply = prose.getPackage(PROSE_POWERUP_APPLY, powerup, target);
        sendSystemMessageProse(player, ppApply);
        return true;
    }
    public static boolean cleanupWeaponPowerup(obj_id weapon) throws InterruptedException
    {
        LOG("powerup", "Cleaning Up " + weapon);
        obj_var_list ovl = getObjVarList(weapon, VAR_POWERUP_BASE);
        if (ovl != null)
        {
            int numItems = ovl.getNumItems();
            for (int i = 0; i < numItems; i++)
            {
                obj_var ov = ovl.getObjVar(i);
                String name = ov.getName();
                String varpath = VAR_POWERUP_BASE + "." + name;
                if (name.equals("speed"))
                {
                    float curSpeed = getWeaponAttackSpeed(weapon);
                    float speedDiff = getFloatObjVar(weapon, varpath);
                    setWeaponAttackSpeed(weapon, curSpeed + speedDiff);
                }
                else if (name.equals("damage"))
                {
                    int curMinDmg = getWeaponMinDamage(weapon);
                    float dmgDiff = getFloatObjVar(weapon, varpath);
                    setWeaponMinDamage(weapon, curMinDmg - (int)dmgDiff);
                    int curMaxDmg = getWeaponMaxDamage(weapon);
                    setWeaponMaxDamage(weapon, curMaxDmg - (int)dmgDiff);
                }
                else if (name.equals("accuracy"))
                {
                    int curAcc = getWeaponAccuracy(weapon);
                    float accDiff = getFloatObjVar(weapon, varpath);
                    setWeaponAccuracy(weapon, curAcc - (int)accDiff);
                }
                else if (name.equals("wound"))
                {
                    float curWound = getWeaponWoundChance(weapon);
                    float woundDiff = getFloatObjVar(weapon, varpath);
                    LOG("powerup", "Reverting wound: curWound=" + curWound + ", woundDiff=" + woundDiff + ", newWound=" + (curWound - woundDiff));
                    setWeaponWoundChance(weapon, curWound - woundDiff);
                }
                else if (name.equals("elementalDamage"))
                {
                    int curEDmg = getWeaponElementalValue(weapon);
                    float eDmgDiff = getFloatObjVar(weapon, varpath);
                    setWeaponElementalValue(weapon, curEDmg - (int)eDmgDiff);
                }
                else if (name.equals("actionCost"))
                {
                    float curAction = getWeaponAttackCost(weapon);
                    float actionDiff = getFloatObjVar(weapon, varpath);
                    setWeaponAttackCost(weapon, (int)curAction - (int)actionDiff);
                }
            }
        }
        weapons.setWeaponData(weapon);
        removeObjVar(weapon, powerup.VAR_POWERUP_BASE);
        clearCondition(weapon, CONDITION_MAGIC_ITEM);
        detachScript(weapon, SCRIPT_WEAPON);
        return true;
    }
    public static void decrementUseCounter(obj_id target) throws InterruptedException
    {
        if (!isIdValid(target))
        {
            return;
        }
        int cnt = getIntObjVar(target, VAR_POWERUP_USES_LEFT);
        cnt--;
        if (cnt < 1)
        {
            cleanupWeaponPowerup(target);
            obj_id owner = getOwner(target);
            if (!isIdValid(owner))
            {
                return;
            }
            prose_package ppExpire = prose.getPackage(PROSE_POWERUP_EXPIRE, target);
            sendSystemMessageProse(owner, ppExpire);
            return;
        }
        setObjVar(target, VAR_POWERUP_USES_LEFT, cnt);
    }
}
