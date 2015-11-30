package script.item.slicing;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.prose;
import script.library.slicing;
import script.library.utils;
import script.library.jedi;
import script.library.weapons;
import script.library.xp;
import script.library.powerup;

public class slicing_weapon extends script.base_script
{
    public slicing_weapon()
    {
    }
    public static final string_id SID_SLICE = new string_id("slicing/slicing", "slice");
    public static final string_id SID_REPAIR = new string_id("slicing/slicing", "repair");
    public static final string_id SID_NO_KIT = new string_id("slicing/slicing", "no_weapon_kit");
    public static final string_id SID_DAM_MOD = new string_id("slicing/slicing", "dam_mod");
    public static final string_id SID_SPD_MOD = new string_id("slicing/slicing", "spd_mod");
    public static final string_id SID_CRIT_MOD = new string_id("slicing/slicing", "crit_mod");
    public static final string_id SID_FAIL_WEAPON = new string_id("slicing/slicing", "fail_weapon");
    public static final string_id SID_WEAPON_AT_MAX = new string_id("slicing/slicing", "weapon_at_max");
    public static final string_id SID_NOT_IN_INV = new string_id("slicing/slicing", "not_in_inv");
    public static final string_id SID_FAILED_NO_EQUIP = new string_id("slicing/slicing", "failed_no_equip");
    public static final string_id SID_SLICE_APPLIED = new string_id("slicing/slicing", "slice_applied");
    public static final String SLICE_TABLE = "datatables/smuggler/slice_weapon.iff";
    public int OnAttach(obj_id self) throws InterruptedException
    {
        detachScript(self, "item.slicing.slicing_weapon");
        return SCRIPT_CONTINUE;
    }
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        if (jedi.isLightsaber(self))
        {
            if (!hasScript(self, "systems.jedi.saber_base"))
            {
                attachScript(self, "systems.jedi.saber_base");
            }
        }
        detachScript(self, "item.slicing.slicing_weapon");
        return SCRIPT_CONTINUE;
    }
    public int finishSlicing(obj_id self, dictionary params) throws InterruptedException
    {
        if (params == null)
        {
            return SCRIPT_CONTINUE;
        }
        obj_id player = params.getObjId("player");
        obj_id pInv = utils.getInventoryContainer(player);
        if (!isIdValid(pInv))
        {
            return SCRIPT_CONTINUE;
        }
        if (!contains(pInv, self))
        {
            sendSystemMessage(player, SID_NOT_IN_INV);
            return SCRIPT_CONTINUE;
        }
        if (powerup.hasPowerUpInstalled(self))
        {
            return SCRIPT_CONTINUE;
        }
        int success = params.getInt("success");
        int row = params.getInt("row");
        if (success == 1)
        {
            dictionary effect = dataTableGetRow(SLICE_TABLE, row);
            String slice_name = effect.getString("SLICE");
            String stat = effect.getString("STAT");
            int slice_cost = effect.getInt("COST");
            int slice_amount = effect.getInt("AMOUNT");
            boolean asuccess = false;
            if (stat.equals("fire_rate"))
            {
                asuccess = applyFireRateChange(self, player, slice_amount);
            }
            else if (stat.equals("damage"))
            {
                asuccess = applyDamageChange(self, player, slice_amount);
            }
            else if (stat.equals("crit_chance"))
            {
                asuccess = applyCritChance(self, player, slice_amount);
            }
            if (asuccess)
            {
                int weapon_cust = getIntObjVar(self, "slice.customized");
                weapon_cust += slice_cost;
                setObjVar(self, "slice.customized", weapon_cust);
                setObjVar(self, "slice.rank." + stat, effect.getInt("RANK"));
                prose_package pp = prose.getPackage(SID_SLICE_APPLIED, new string_id("slicing/slicing_weapon", slice_name));
                sendSystemMessageProse(player, pp);
                setObjVar(self, "slicing.new_hacked", 1);
                sendDirtyAttributesNotification(self);
            }
        }
        else 
        {
        }
        return SCRIPT_CONTINUE;
    }
    public boolean applyFireRateChange(obj_id self, obj_id player, int slice_amount) throws InterruptedException
    {
        String template = getTemplateName(self);
        float pctBonus = slice_amount / 100.0f;
        float spdLow = (float)weapons.getSpeedLow(template, weapons.VIA_TEMPLATE);
        spdLow /= 100.0f;
        float spd = getWeaponAttackSpeed(self);
        if (hasObjVar(self, "slice.old_fire_rate"))
        {
            spd = getFloatObjVar(self, "slice.old_fire_rate");
        }
        if (spd < (spdLow * 0.7f))
        {
            sendSystemMessage(player, SID_WEAPON_AT_MAX);
            return false;
        }
        setObjVar(self, "slice.old_fire_rate", spd);
        spd -= spd * pctBonus;
        if (spd < (spdLow * 0.7f))
        {
            spd = (spdLow * 0.7f);
        }
        setWeaponAttackSpeed(self, spd);
        int mod = (int)(pctBonus * 100);
        prose_package pp = prose.getPackage(SID_SPD_MOD, mod);
        sendSystemMessageProse(player, pp);
        setObjVar(self, "slice.fire_rate", slice_amount);
        return true;
    }
    public boolean applyDamageChange(obj_id self, obj_id player, int slice_amount) throws InterruptedException
    {
        String template = getTemplateName(self);
        float minDamLow = (float)weapons.getMinDamageLow(template, weapons.VIA_TEMPLATE);
        float minDamHigh = (float)weapons.getMinDamageHigh(template, weapons.VIA_TEMPLATE);
        float maxDamLow = (float)weapons.getMaxDamageLow(template, weapons.VIA_TEMPLATE);
        float maxDamHigh = (float)weapons.getMaxDamageHigh(template, weapons.VIA_TEMPLATE);
        float minDamRange = minDamHigh - minDamLow;
        float maxDamRange = maxDamHigh - maxDamLow;
        float minDam = (float)getWeaponMinDamage(self);
        float maxDam = (float)getWeaponMaxDamage(self);
        if ((minDam > (minDamHigh * 1.3f)) || (maxDam > (maxDamHigh * 1.3f)))
        {
            sendSystemMessage(player, SID_WEAPON_AT_MAX);
            return false;
        }
        if (hasObjVar(self, "slice.old_min_dam"))
        {
            minDam = getFloatObjVar(self, "slice.old_min_dam");
        }
        if (hasObjVar(self, "slice.old_max_dam"))
        {
            maxDam = getFloatObjVar(self, "slice.old_max_dam");
        }
        setObjVar(self, "slice.old_min_dam", minDam);
        setObjVar(self, "slice.old_max_dam", maxDam);
        float pctBonus = slice_amount / 100.0f;
        minDam += minDam * pctBonus;
        maxDam += maxDam * pctBonus;
        if (minDam > (minDamHigh * 1.3f))
        {
            minDam = (minDamHigh * 1.3f);
        }
        if (maxDam > (maxDamHigh * 1.3f))
        {
            maxDam = (maxDamHigh * 1.3f);
        }
        setWeaponMinDamage(self, (int)minDam);
        setWeaponMaxDamage(self, (int)maxDam);
        int mod = (int)(pctBonus * 100);
        prose_package pp = prose.getPackage(SID_DAM_MOD, mod);
        sendSystemMessageProse(player, pp);
        setObjVar(self, "slice.damage", slice_amount);
        return true;
    }
    public boolean applyCritChance(obj_id self, obj_id player, int slice_amount) throws InterruptedException
    {
        prose_package pp = prose.getPackage(SID_CRIT_MOD, slice_amount);
        sendSystemMessageProse(player, pp);
        setObjVar(self, "slice.crit_chance", slice_amount);
        return true;
    }
}
