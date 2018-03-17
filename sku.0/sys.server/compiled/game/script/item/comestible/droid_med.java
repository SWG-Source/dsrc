package script.item.comestible;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.sui;
import script.library.factions;
import script.library.utils;
import script.library.ai_lib;
import script.library.pet_lib;
import script.library.healing;
import script.library.consumable;
import script.library.prose;
import script.library.locations;
import script.library.camping;

public class droid_med extends script.base_script
{
    public droid_med()
    {
    }
    public static final String SCRIPT_DROID_MED = "item.comestible.droid_med";
    public static final string_id SID_NO_TARGET = new string_id("error_message", "droid_repair_no_target");
    public static final string_id SID_TARGET_NOT_DROID = new string_id("error_message", "droid_repair_target_not_droid");
    public static final string_id SID_NO_WOUND_KIT = new string_id("error_message", "droid_repair_no_wound_kit");
    public static final string_id SID_NO_DAMAGE_KIT = new string_id("error_message", "droid_repair_no_damage_kit");
    public static final string_id SID_MUST_WAIT = new string_id("error_message", "droid_repair_must_wait");
    public static final string_id SID_UNWISE_REPAIR = new string_id("error_message", "droid_repair_opposite_faction");
    public static final string_id SID_NO_WOUNDS = new string_id("error_message", "droid_repair_no_wounds");
    public static final string_id SID_NO_DAMAGE = new string_id("error_message", "droid_repair_no_damage");
    public static final string_id SID_NOT_DE = new string_id("error_message", "droid_repair_not_droid_engineer");
    public static final string_id SID_YOU_IN_COMBAT = new string_id("error_message", "droid_repair_you_in_combat");
    public static final string_id SID_DROID_IN_COMBAT = new string_id("error_message", "droid_repair_droid_in_combat");
    public static final string_id SID_BAD_LOCATION = new string_id("error_message", "droid_repair_not_valid_location");
    public int OnObjectMenuRequest(obj_id self, obj_id player, menu_info mi) throws InterruptedException
    {
        if (hasObjVar(self, consumable.VAR_CONSUMABLE_BASE))
        {
            menu_info_data mid = mi.getMenuItemByType(menu_info_types.EXAMINE);
            if (mid != null)
            {
                mid.setServerNotify(true);
            }
            menu_info_data mid2 = mi.getMenuItemByType(menu_info_types.ITEM_USE);
            if (mid2 != null)
            {
                mid2.setServerNotify(true);
            }
        }
        else 
        {
            detachScript(self, SCRIPT_DROID_MED);
        }
        return SCRIPT_CONTINUE;
    }
    public int OnObjectMenuSelect(obj_id self, obj_id player, int item) throws InterruptedException
    {
        if (item == menu_info_types.ITEM_USE)
        {
            obj_id target = getLookAtTarget(player);
            if (target == null)
            {
                sendSystemMessage(player, SID_NO_TARGET);
                return SCRIPT_CONTINUE;
            }
            else 
            {
                performDroidHealing(player, target, self);
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int OnGetAttributes(obj_id self, obj_id player, String[] names, String[] attribs) throws InterruptedException
    {
        attrib_mod[] am = getAttribModArrayObjVar(self, consumable.VAR_CONSUMABLE_MODS);
        int idx = utils.getValidAttributeIndex(names);
        if (idx == -1)
        {
            return SCRIPT_CONTINUE;
        }
        int count = getCount(self);
        if (count > 0)
        {
            names[idx] = "quantity";
            attribs[idx] = Integer.toString(count);
            idx++;
            if (idx >= names.length)
            {
                return SCRIPT_CONTINUE;
            }
        }
        if (hasObjVar(self, "consumable.energy"))
        {
            names[idx] = "examine_repair_energy";
            attribs[idx] = Integer.toString(getIntObjVar(self, "consumable.energy"));
            idx++;
            if (idx >= names.length)
            {
                return SCRIPT_CONTINUE;
            }
        }
        else 
        {
            if (am != null && am.length > 0)
            {
                int val = am[0].getValue();
                names[idx] = "examine_repair_power";
                attribs[idx] = Integer.toString(val);
                idx++;
                if (idx >= names.length)
                {
                    return SCRIPT_CONTINUE;
                }
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int healDroidDamage(obj_id self, obj_id target, String params, float defaultTime) throws InterruptedException
    {
        if (target == null)
        {
            sendSystemMessage(self, SID_NO_TARGET);
            return SCRIPT_CONTINUE;
        }
        else 
        {
            obj_id droid_med = healing.findDroidDamageMed(self);
            if (droid_med == null)
            {
                sendSystemMessage(self, SID_NO_DAMAGE_KIT);
                return SCRIPT_CONTINUE;
            }
            performDroidHealing(self, target, droid_med);
        }
        return SCRIPT_CONTINUE;
    }
    public int healDroidWound(obj_id self, obj_id target, String params, float defaultTime) throws InterruptedException
    {
        if (target == null)
        {
            sendSystemMessage(self, SID_NO_TARGET);
            return SCRIPT_CONTINUE;
        }
        else 
        {
            obj_id droid_med = healing.findDroidWoundMed(self);
            if (droid_med == null)
            {
                sendSystemMessage(self, SID_NO_WOUND_KIT);
                return SCRIPT_CONTINUE;
            }
            performDroidHealing(self, target, droid_med);
        }
        return SCRIPT_CONTINUE;
    }
    public boolean performDroidHealing(obj_id player, obj_id target, obj_id droid_med) throws InterruptedException
    {
        int useTime = utils.getIntScriptVar(player, "pet_med.useTime");
        int curTime = getGameTime();
        if (curTime < useTime)
        {
            int deltaTime = (useTime - curTime);
            prose_package pp = prose.getPackage(SID_MUST_WAIT, deltaTime);
            sendSystemMessageProse(player, pp);
            return false;
        }
        if (!pet_lib.isDroidPet(target))
        {
            sendSystemMessage(player, SID_TARGET_NOT_DROID);
            return false;
        }
        if (!factions.pvpDoAllowedHelpCheck(player, target))
        {
            sendSystemMessage(player, SID_UNWISE_REPAIR);
            return false;
        }
        if (hasObjVar(droid_med, "consumable.energy"))
        {
            if (!healing.isDroidDamaged(target))
            {
                prose_package pp = prose.getPackage(SID_NO_DAMAGE, getEncodedName(target));
                sendSystemMessageProse(player, pp);
                return false;
            }
        }
        else 
        {
            if (!isDroidEngineer(player))
            {
                sendSystemMessage(player, SID_NOT_DE);
                return false;
            }
            if (ai_lib.isInCombat(player))
            {
                sendSystemMessage(player, SID_YOU_IN_COMBAT);
                return false;
            }
            if (ai_lib.isInCombat(target))
            {
                sendSystemMessage(player, SID_DROID_IN_COMBAT);
                return false;
            }
            location playerLoc = getLocation(player);
            obj_id playerCamp = camping.getCurrentCamp(player);
            if (!locations.isInMissionCity(playerLoc) && !isIdValid(playerCamp) && !pet_lib.isNearResidence(player))
            {
                sendSystemMessage(player, SID_BAD_LOCATION);
                return false;
            }
            if (!healing.isDroidWounded(target))
            {
                prose_package pp = prose.getPackage(SID_NO_WOUNDS, getEncodedName(target));
                sendSystemMessageProse(player, pp);
                return false;
            }
        }
        if (healing.performDroidRepair(player, target, droid_med, true))
        {
            doAnimationAction(player, "heal_other");
            healing.playHealDamageEffect(getLocation(target));
            useTime = curTime + 15;
            utils.setScriptVar(player, "pet_med.useTime", useTime);
            return true;
        }
        return false;
    }
    public boolean isDroidEngineer(obj_id player) throws InterruptedException
    {
        if (hasSkill(player, "class_engineering_phase1_novice"))
        {
            return true;
        }
        return false;
    }
}
