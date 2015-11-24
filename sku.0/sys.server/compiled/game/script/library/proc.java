package script.library;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.utils;
import script.library.combat;
import script.library.static_item;
import script.library.colors;
import script.library.buff;
import script.library.vehicle;

public class proc extends script.base_script
{
    public proc()
    {
    }
    public static final String CYBERNETICS_TABLE = "datatables/cybernetic/cybernetic.iff";
    public static final String PROC_TABLE = "datatables/proc/proc.iff";
    public static final String REAC_BASE = "reactive_proc.";
    public static void executeProcEffects(obj_id attacker, obj_id defender) throws InterruptedException
    {
        executeProcEffects(attacker, defender, null);
    }
    public static void executeProcEffects(obj_id attacker, obj_id defender, combat_data actionData) throws InterruptedException
    {
        weapon_data weaponData = getWeaponData(getCurrentWeapon(attacker));
        String params = "";
        prose_package pp = new prose_package();
        if (actionData != null)
        {
            if (actionData.params != null && !actionData.params.equals(""))
            {
                params = actionData.params;
            }
        }
        if (!isMob(attacker))
        {
            return;
        }
        if (vehicle.isRidingVehicle(attacker))
        {
            return;
        }
        if (utils.hasScriptVar(attacker, "currentProcList"))
        {
            if (actionData.attackType == combat.CONE || actionData.attackType == combat.AREA || actionData.attackType == combat.TARGET_AREA)
            {
                return;
            }
            Vector procEffects = utils.getResizeableStringArrayScriptVar(attacker, "currentProcList");
            if (procEffects != null && procEffects.size() > 0)
            {
                for (int i = 0; i < procEffects.size(); ++i)
                {
                    dictionary parms = dataTableGetRow(PROC_TABLE, ((String)procEffects.get(i)));
                    if (parms != null)
                    {
                        int procChance = parms.getInt("procChance");
                        int expertiseProcChance = (int)getSkillStatisticModifier(attacker, ((String)procEffects.get(i)));
                        if (expertiseProcChance > 0)
                        {
                            procChance = expertiseProcChance;
                            if (((String)procEffects.get(i)).startsWith("kill_meter_"))
                            {
                                procChance *= getKillMeter(attacker);
                            }
                        }
                        String noSelfProcBuffs = parms.getString("noSelfProcBuffs");
                        String noTargetProcBuffs = parms.getString("noTargetProcBuffs");
                        if (isGod(attacker) || isGod(defender))
                        {
                            LOG("expertise", "Proc: " + parms.getString("procString") + " noSelfProcBuffs: " + noSelfProcBuffs + " noTargetProcBuffs: " + noTargetProcBuffs);
                        }
                        if (buff.hasAnyBuffInList(attacker, noSelfProcBuffs) || buff.hasAnyBuffInList(defender, noTargetProcBuffs))
                        {
                            continue;
                        }
                        String requiredBuffs = parms.getString("requiredSelfBuffs");
                        if (requiredBuffs.length() > 0 && !buff.hasAnyBuffInList(attacker, requiredBuffs))
                        {
                            continue;
                        }
                        if (procChance != 100)
                        {
                            procChance = Math.round(weaponData.attackSpeed * (float)procChance);
                        }
                        if (rand(0, 99) < procChance)
                        {
                            String procCommand = parms.getString("procString");
                            if (procCommand != null)
                            {
                                if (combat.canUseWeaponWithAbility(attacker, weaponData, procCommand, false))
                                {
                                    queueCommand(attacker, getStringCrc(procCommand.toLowerCase()), defender, params, COMMAND_PRIORITY_DEFAULT);
                                    pp = prose.setStringId(pp, new string_id("proc/proc", procCommand));
                                    showFlyTextPrivateProseWithFlags(attacker, attacker, pp, 1.5f, colors.LEMONCHIFFON, FLY_TEXT_FLAG_IS_CRITICAL_HIT);
                                }
                            }
                        }
                    }
                }
            }
        }
        if (!isMob(defender))
        {
            return;
        }
        if (vehicle.isRidingVehicle(defender))
        {
            return;
        }
        if (utils.hasScriptVar(defender, "currentReacList"))
        {
            Vector reacEffects = utils.getResizeableStringArrayScriptVar(defender, "currentReacList");
            if (reacEffects != null && reacEffects.size() > 0)
            {
                for (int i = 0; i < reacEffects.size(); ++i)
                {
                    dictionary reacParms = dataTableGetRow(PROC_TABLE, ((String)reacEffects.get(i)));
                    if (reacParms != null)
                    {
                        int reacChance = reacParms.getInt("procChance");
                        int expertiseReacChance = (int)getSkillStatisticModifier(defender, ((String)reacEffects.get(i)));
                        if (expertiseReacChance > 0)
                        {
                            reacChance = expertiseReacChance;
                            if (((String)reacEffects.get(i)).startsWith("kill_meter_"))
                            {
                                reacChance *= getKillMeter(defender);
                            }
                        }
                        String noSelfProcBuffs = reacParms.getString("noSelfProcBuffs");
                        String noTargetProcBuffs = reacParms.getString("noTargetProcBuffs");
                        if (buff.hasAnyBuffInList(defender, noSelfProcBuffs) || buff.hasAnyBuffInList(attacker, noTargetProcBuffs))
                        {
                            return;
                        }
                        if (reacChance != 100)
                        {
                            reacChance = Math.round(weaponData.attackSpeed * (float)reacChance);
                        }
                        if (rand(0, 99) < reacChance)
                        {
                            String reacCommand = reacParms.getString("procString");
                            if (reacCommand != null)
                            {
                                combat_data abilityData = combat_engine.getCombatData(reacCommand);
                                String cooldownGroup = abilityData.cooldownGroup;
                                float cooldownTime = abilityData.cooldownTime;
                                boolean canProc = false;
                                int timeLapse = -1;
                                if (utils.hasScriptVar(defender, REAC_BASE + cooldownGroup))
                                {
                                    int lastProc = utils.getIntScriptVar(defender, REAC_BASE + cooldownGroup);
                                    timeLapse = getGameTime() - lastProc;
                                    if (timeLapse >= cooldownTime)
                                    {
                                        canProc = true;
                                    }
                                }
                                else 
                                {
                                    canProc = true;
                                }
                                if (canProc)
                                {
                                    if (queueCommand(defender, getStringCrc(reacCommand.toLowerCase()), attacker, "", COMMAND_PRIORITY_DEFAULT))
                                    {
                                        LOG("procCommand", "Reac -- " + reacCommand + " activated");
                                        utils.setScriptVar(defender, REAC_BASE + cooldownGroup, getGameTime());
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
    public static void buildCurrentProcList(obj_id player) throws InterruptedException
    {
        Vector currentProcList = new Vector();
        currentProcList.setSize(0);
        obj_id weapon = getCurrentWeapon(player);
        if (static_item.isStaticItem(weapon))
        {
            dictionary itemData = static_item.getStaticItemWeaponDictionary(weapon);
            String procEffect = itemData.getString("proc_effect");
            if (procEffect != null && !procEffect.equals(""))
            {
                String[] weaponProcEffects = split(procEffect, ',');
                for (int i = 0; i < weaponProcEffects.length; ++i)
                {
                    currentProcList.addElement(weaponProcEffects[i]);
                }
            }
        }
        else 
        {
            if (hasObjVar(weapon, "procEffect"))
            {
                String[] weaponProcEffects = getStringArrayObjVar(weapon, "procEffect");
                for (int i = 0; i < weaponProcEffects.length; ++i)
                {
                    currentProcList.addElement(weaponProcEffects[i]);
                }
            }
        }
        if (utils.hasScriptVar(player, "procBuffEffects"))
        {
            String[] buffProcEffects = utils.getStringArrayScriptVar(player, "procBuffEffects");
            for (int i = 0; i < buffProcEffects.length; ++i)
            {
                currentProcList.addElement(buffProcEffects[i]);
            }
        }
        if (utils.hasScriptVar(player, "cyberneticItems"))
        {
            String[] installedCybernetics = utils.getStringArrayScriptVar(player, "cyberneticItems");
            String procString = null;
            for (int i = 0; i < installedCybernetics.length; ++i)
            {
                procString = dataTableGetString(CYBERNETICS_TABLE, installedCybernetics[i], "procEffectString");
                if (procString != null && !procString.equals(""))
                {
                    currentProcList.addElement(procString);
                }
                procString = null;
            }
        }
        if (utils.hasScriptVar(player, "expertiseProcReacList"))
        {
            Vector expertiseList = utils.getResizeableStringArrayScriptVar(player, "expertiseProcReacList");
            for (int i = 0; i < expertiseList.size(); ++i)
            {
                if (((String)expertiseList.get(i)).endsWith("_proc"))
                {
                    currentProcList.addElement(((String)expertiseList.get(i)));
                }
            }
        }
        if (currentProcList.size() > 0)
        {
            Vector tempProcList = new Vector();
            tempProcList.setSize(0);
            for (int i = 0; i < currentProcList.size(); ++i)
            {
                if (!tempProcList.contains(((String)currentProcList.get(i))))
                {
                    tempProcList.addElement(((String)currentProcList.get(i)));
                }
            }
            utils.setScriptVar(player, "currentProcList", tempProcList);
        }
        else if (utils.hasScriptVar(player, "currentProcList"))
        {
            utils.removeScriptVar(player, "currentProcList");
        }
    }
    public static void buildCurrentReacList(obj_id player) throws InterruptedException
    {
        Vector currentReacList = new Vector();
        currentReacList.setSize(0);
        final String strWear[] = 
        {
            "chest2",
            "chest1",
            "hat",
            "bicep_r",
            "bicep_l",
            "bracer_upper_r",
            "bracer_upper_l",
            "pants2",
            "pants1",
            "bandolier",
            "wrist_r",
            "wrist_l",
            "gloves",
            "ring_r",
            "ring_l",
            "utility_belt",
            "shoes"
        };
        for (int intI = 0; intI < strWear.length; intI++)
        {
            obj_id wearable = getObjectInSlot(player, strWear[intI]);
            if (!isIdNull(wearable))
            {
                if (static_item.isStaticItem(wearable))
                {
                    dictionary wearableData = null;
                    String wearableName = static_item.getStaticItemName(wearable);
                    if (static_item.getStaticObjectType(wearableName) == 2)
                    {
                        wearableData = static_item.getStaticArmorDictionary(wearable);
                    }
                    else 
                    {
                        wearableData = static_item.getStaticItemDictionary(wearable);
                    }
                    if (wearableData != null)
                    {
                        String reacEffect = wearableData.getString("reactive_effect");
                        if (reacEffect != null && !reacEffect.equals(""))
                        {
                            String[] wearableReacEffects = split(reacEffect, ',');
                            for (int i = 0; i < wearableReacEffects.length; ++i)
                            {
                                currentReacList.addElement(wearableReacEffects[i]);
                            }
                        }
                    }
                }
            }
        }
        if (utils.hasScriptVar(player, "reacBuffEffects"))
        {
            String[] buffReacEffects = utils.getStringArrayScriptVar(player, "reacBuffEffects");
            for (int i = 0; i < buffReacEffects.length; ++i)
            {
                currentReacList.addElement(buffReacEffects[i]);
            }
        }
        if (utils.hasScriptVar(player, "cyberneticItems"))
        {
            String[] installedCybernetics = utils.getStringArrayScriptVar(player, "cyberneticItems");
            String reacString = null;
            for (int i = 0; i < installedCybernetics.length; ++i)
            {
                reacString = dataTableGetString(CYBERNETICS_TABLE, installedCybernetics[i], "reacEffectString");
                if (reacString != null && !reacString.equals(""))
                {
                    currentReacList.addElement(reacString);
                }
                reacString = null;
            }
        }
        if (utils.hasScriptVar(player, "expertiseProcReacList"))
        {
            Vector expertiseList = utils.getResizeableStringArrayScriptVar(player, "expertiseProcReacList");
            for (int i = 0; i < expertiseList.size(); ++i)
            {
                if (((String)expertiseList.get(i)).endsWith("_reac"))
                {
                    currentReacList.addElement(((String)expertiseList.get(i)));
                }
            }
        }
        if (currentReacList.size() > 0)
        {
            utils.setScriptVar(player, "currentReacList", currentReacList);
        }
        else if (utils.hasScriptVar(player, "currentReacList"))
        {
            utils.removeScriptVar(player, "currentReacList");
        }
    }
}
