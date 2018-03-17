package script.item.comestible;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.ai_lib;
import script.library.callable;
import script.library.consumable;
import script.library.factions;
import script.library.healing;
import script.library.pet_lib;
import script.library.prose;
import script.library.sui;
import script.library.utils;

public class pet_med extends script.base_script
{
    public pet_med()
    {
    }
    public static final String SCRIPT_PET_MED = "item.comestible.pet_med";
    public static final string_id SID_TARGET_NOT_CREATURE = new string_id("error_message", "target_not_creature");
    public static final string_id SID_TARGETTING_ERROR = new string_id("error_message", "targetting_error");
    public static final string_id SID_NO_VALID_MEDICINE = new string_id("pet/pet_menu", "no_valid_medicine");
    public static final string_id SID_CANNOT_DO_THAT_NOW = new string_id("pet/pet_menu", "cannot_do_that_now");
    public static final string_id SID_DO_NOT_HEAL = new string_id("pet/pet_menu", "do_not_heal");
    public static final string_id SID_NOTHING_TO_HEAL = new string_id("pet/pet_menu", "nothing_to_heal");
    public int OnObjectMenuRequest(obj_id self, obj_id player, menu_info mi) throws InterruptedException
    {
        LOG("LOG_CHANNEL", "OnObjectMenuRequest");
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
            detachScript(self, SCRIPT_PET_MED);
        }
        return SCRIPT_CONTINUE;
    }
    public int OnObjectMenuSelect(obj_id self, obj_id player, int item) throws InterruptedException
    {
        LOG("LOG_CHANNEL", "item ->" + item);
        if (item == menu_info_types.ITEM_USE)
        {
            obj_id target = getLookAtTarget(player);
            if (!isIdValid(target))
            {
                sendSystemMessage(player, SID_TARGET_NOT_CREATURE);
                return SCRIPT_CONTINUE;
            }
            else 
            {
                if (hasObjVar(self, "consumable.strength"))
                {
                    performPetHealVitality(player, target, self);
                }
                else 
                {
                    performPetHealDamage(player, target, self);
                }
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
        if ((am == null) || (am.length == 0))
        {
            names[idx] = "strength";
            attribs[idx] = Integer.toString(getIntObjVar(self, "consumable.strength"));
            idx++;
            if (idx >= names.length)
            {
                return SCRIPT_CONTINUE;
            }
        }
        else 
        {
            for (int i = 0; i < am.length; i++)
            {
                int attrib = am[i].getAttribute();
                int val = am[i].getValue();
                float atk = am[i].getAttack();
                float dcy = am[i].getDecay();
                names[idx] = "examine_heal_damage_" + consumable.STAT_NAME[attrib];
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
    public int healPetDamage(obj_id self, obj_id target, String params, float defaultTime) throws InterruptedException
    {
        if (!isIdValid(target))
        {
            sendSystemMessage(self, SID_TARGET_NOT_CREATURE);
            return SCRIPT_CONTINUE;
        }
        else 
        {
            obj_id pet_med = healing.findPetDamageMed(self);
            if (!isIdValid(pet_med))
            {
                sendSystemMessage(self, SID_NO_VALID_MEDICINE);
                return SCRIPT_CONTINUE;
            }
            performPetHealDamage(self, target, pet_med);
        }
        return SCRIPT_CONTINUE;
    }
    public boolean performPetHealDamage(obj_id player, obj_id target, obj_id pet_med) throws InterruptedException
    {
        int useTime = utils.getIntScriptVar(player, "pet_med.useTime");
        int curTime = getGameTime();
        if (curTime < useTime)
        {
            prose_package ppNoMedsNow = prose.getPackage(SID_CANNOT_DO_THAT_NOW, (useTime - curTime));
            sendSystemMessageProse(player, ppNoMedsNow);
            return false;
        }
        if (!pet_lib.isCreaturePet(target))
        {
            sendSystemMessage(player, SID_TARGET_NOT_CREATURE);
            return false;
        }
        if (!factions.pvpDoAllowedHelpCheck(player, target))
        {
            sendSystemMessage(player, SID_DO_NOT_HEAL);
            return false;
        }
        if (!healing.isDamaged(target))
        {
            prose_package ppNothingToHeal = prose.getPackage(SID_NOTHING_TO_HEAL, target);
            sendSystemMessageProse(player, ppNothingToHeal);
            return false;
        }
        if (healing.performMedicalHealDamage(player, target, pet_med, true))
        {
            doAnimationAction(player, "heal_other");
            healing.playHealDamageEffect(getLocation(target));
            useTime = curTime + 15;
            utils.setScriptVar(player, "pet_med.useTime", useTime);
            return true;
        }
        return false;
    }
    public int healPetVitality(obj_id self, obj_id target, String params, float defaultTime) throws InterruptedException
    {
        if (!isIdValid(target))
        {
            sendSystemMessage(self, SID_TARGET_NOT_CREATURE);
            return SCRIPT_CONTINUE;
        }
        else 
        {
            obj_id pet_med = healing.findPetVitalityMed(self);
            if (!isIdValid(pet_med))
            {
                sendSystemMessage(self, SID_NO_VALID_MEDICINE);
                return SCRIPT_CONTINUE;
            }
            performPetHealVitality(self, target, pet_med);
        }
        return SCRIPT_CONTINUE;
    }
    public boolean performPetHealVitality(obj_id player, obj_id target, obj_id pet_med) throws InterruptedException
    {
        if (!pet_lib.isCreaturePet(target))
        {
            sendSystemMessage(player, SID_TARGET_NOT_CREATURE);
            return false;
        }
        if (!factions.pvpDoAllowedHelpCheck(player, target))
        {
            sendSystemMessage(player, SID_DO_NOT_HEAL);
            return false;
        }
        obj_id petControlDevice = callable.getCallableCD(target);
        int vitality = getIntObjVar(petControlDevice, "pet.vitality");
        if (vitality < 1)
        {
            prose_package ppNothingToHeal = prose.getPackage(SID_NOTHING_TO_HEAL, target);
            sendSystemMessageProse(player, ppNothingToHeal);
            return false;
        }
        if (pet_lib.performVitalityHeal(player, target, petControlDevice, pet_med))
        {
            doAnimationAction(player, "heal_other");
            healing.playHealDamageEffect(getLocation(target));
            consumable.decrementCharges(pet_med, player);
            return true;
        }
        return false;
    }
}
