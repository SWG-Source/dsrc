package script.library;

import script.*;
import script.combat_engine.combat_data;
import script.combat_engine.weapon_data;

import java.util.Vector;

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
        weapon_data weaponData = getWeaponData(getCurrentWeapon(attacker));
        if (utils.hasScriptVar(attacker, "currentProcList"))
        {
            if (actionData != null && (actionData.attackType == combat.CONE || actionData.attackType == combat.AREA || actionData.attackType == combat.TARGET_AREA))
            {
                return;
            }
            Vector procEffects = utils.getResizeableStringArrayScriptVar(attacker, "currentProcList");
            if (procEffects != null && procEffects.size() > 0)
            {
                dictionary parms;
                String noSelfProcBuffs;
                String noTargetProcBuffs;
                String requiredBuffs;
                String procCommand;

                for (Object procEffect : procEffects) {
                    parms = dataTableGetRow(PROC_TABLE, ((String) procEffect));
                    if (parms != null) {
                        int procChance = parms.getInt("procChance");
                        int expertiseProcChance = getSkillStatisticModifier(attacker, ((String) procEffect));
                        if (expertiseProcChance > 0) {
                            procChance = expertiseProcChance;
                            if (((String) procEffect).startsWith("kill_meter_")) {
                                procChance *= getKillMeter(attacker);
                            }
                        }
                        noSelfProcBuffs = parms.getString("noSelfProcBuffs");
                        noTargetProcBuffs = parms.getString("noTargetProcBuffs");
                        if (isGod(attacker) || isGod(defender)) {
                            LOG("expertise", "Proc: " + parms.getString("procString") + " noSelfProcBuffs: " + noSelfProcBuffs + " noTargetProcBuffs: " + noTargetProcBuffs);
                        }
                        if (buff.hasAnyBuffInList(attacker, noSelfProcBuffs) || buff.hasAnyBuffInList(defender, noTargetProcBuffs)) {
                            continue;
                        }
                        requiredBuffs = parms.getString("requiredSelfBuffs");
                        if (requiredBuffs.length() > 0 && !buff.hasAnyBuffInList(attacker, requiredBuffs)) {
                            continue;
                        }
                        if (procChance != 100) {
                            procChance = Math.round(weaponData.attackSpeed * (float) procChance);
                        }
                        if (rand(0, 99) < procChance) {
                            procCommand = parms.getString("procString");
                            if (procCommand != null) {
                                if (combat.canUseWeaponWithAbility(attacker, weaponData, procCommand, false)) {
                                    queueCommand(attacker, getStringCrc(procCommand.toLowerCase()), defender, params, COMMAND_PRIORITY_DEFAULT);
                                    showFlyTextPrivateProseWithFlags(
                                            attacker,
                                            attacker,
                                            prose.setStringId(pp, new string_id("proc/proc", procCommand)),
                                            1.5f,
                                            colors.LEMONCHIFFON,
                                            FLY_TEXT_FLAG_IS_CRITICAL_HIT
                                    );
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
                dictionary reacParms;
                String reacCommand;
                String cooldownGroup;

                for (Object reacEffect : reacEffects) {
                    reacParms = dataTableGetRow(PROC_TABLE, ((String) reacEffect));
                    if (reacParms != null) {
                        int reacChance = reacParms.getInt("procChance");
                        int expertiseReacChance = getSkillStatisticModifier(defender, ((String) reacEffect));
                        if (expertiseReacChance > 0) {
                            reacChance = expertiseReacChance;
                            if (((String) reacEffect).startsWith("kill_meter_")) {
                                reacChance *= getKillMeter(defender);
                            }
                        }
                        if (buff.hasAnyBuffInList(defender, reacParms.getString("noSelfProcBuffs")) || buff.hasAnyBuffInList(attacker, reacParms.getString("noTargetProcBuffs"))) {
                            return;
                        }
                        if (reacChance != 100) {
                            reacChance = Math.round(weaponData.attackSpeed * (float) reacChance);
                        }
                        if (rand(0, 99) < reacChance) {
                            reacCommand = reacParms.getString("procString");
                            if (reacCommand != null) {
                                combat_data abilityData = combat_engine.getCombatData(reacCommand);
                                cooldownGroup = abilityData.cooldownGroup;
                                boolean canProc = false;
                                int timeLapse = -1;
                                if (utils.hasScriptVar(defender, REAC_BASE + cooldownGroup)) {
                                    int lastProc = utils.getIntScriptVar(defender, REAC_BASE + cooldownGroup);
                                    timeLapse = getGameTime() - lastProc;
                                    if (timeLapse >= abilityData.cooldownTime) {
                                        canProc = true;
                                    }
                                } else {
                                    canProc = true;
                                }
                                if (canProc) {
                                    if (queueCommand(defender, getStringCrc(reacCommand.toLowerCase()), attacker, "", COMMAND_PRIORITY_DEFAULT)) {
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
                for (String weaponProcEffect : weaponProcEffects) {
                    currentProcList.addElement(weaponProcEffect);
                }
            }
        }
        else 
        {
            if (hasObjVar(weapon, "procEffect"))
            {
                String[] weaponProcEffects = getStringArrayObjVar(weapon, "procEffect");
                for (String weaponProcEffect : weaponProcEffects) {
                    currentProcList.addElement(weaponProcEffect);
                }
            }
        }
        if (utils.hasScriptVar(player, "procBuffEffects"))
        {
            String[] buffProcEffects = utils.getStringArrayScriptVar(player, "procBuffEffects");
            for (String buffProcEffect : buffProcEffects) {
                currentProcList.addElement(buffProcEffect);
            }
        }
        if (utils.hasScriptVar(player, "cyberneticItems"))
        {
            String[] installedCybernetics = utils.getStringArrayScriptVar(player, "cyberneticItems");
            String procString = null;
            for (String installedCybernetic : installedCybernetics) {
                procString = dataTableGetString(CYBERNETICS_TABLE, installedCybernetic, "procEffectString");
                if (procString != null && !procString.equals("")) {
                    currentProcList.addElement(procString);
                }
            }
        }
        if (utils.hasScriptVar(player, "expertiseProcReacList"))
        {
            Vector expertiseList = utils.getResizeableStringArrayScriptVar(player, "expertiseProcReacList");
            for (Object anExpertiseList : expertiseList) {
                if (((String) anExpertiseList).endsWith("_proc")) {
                    currentProcList.addElement(anExpertiseList);
                }
            }
        }
        if (currentProcList.size() > 0)
        {
            Vector tempProcList = new Vector();
            tempProcList.setSize(0);
            for (Object aCurrentProcList : currentProcList) {
                if (!tempProcList.contains(aCurrentProcList)) {
                    tempProcList.addElement(aCurrentProcList);
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
        obj_id wearable;
        dictionary wearableData;
        String reacEffect;

        for (String aStrWear : strWear) {
            wearable = getObjectInSlot(player, aStrWear);
            if (!isIdNull(wearable)) {
                if (static_item.isStaticItem(wearable)) {
                    if (static_item.getStaticObjectType(static_item.getStaticItemName(wearable)) == 2) {
                        wearableData = static_item.getStaticArmorDictionary(wearable);
                    } else {
                        wearableData = static_item.getStaticItemDictionary(wearable);
                    }
                    if (wearableData != null) {
                        reacEffect = wearableData.getString("reactive_effect");
                        if (reacEffect != null && !reacEffect.equals("")) {
                            for (String wearableReacEffect : split(reacEffect, ',')) {
                                currentReacList.addElement(wearableReacEffect);
                            }
                        }
                    }
                }
            }
        }
        if (utils.hasScriptVar(player, "reacBuffEffects"))
        {
            String[] buffReacEffects = utils.getStringArrayScriptVar(player, "reacBuffEffects");
            for (String buffReacEffect : buffReacEffects) {
                currentReacList.addElement(buffReacEffect);
            }
        }
        if (utils.hasScriptVar(player, "cyberneticItems"))
        {
            String[] installedCybernetics = utils.getStringArrayScriptVar(player, "cyberneticItems");
            String reacString;
            for (String installedCybernetic : installedCybernetics) {
                reacString = dataTableGetString(CYBERNETICS_TABLE, installedCybernetic, "reacEffectString");
                if (reacString != null && !reacString.equals("")) {
                    currentReacList.addElement(reacString);
                }
                reacString = null;
            }
        }
        if (utils.hasScriptVar(player, "expertiseProcReacList"))
        {
            Vector expertiseList = utils.getResizeableStringArrayScriptVar(player, "expertiseProcReacList");
            for (Object anExpertiseList : expertiseList) {
                if (((String) anExpertiseList).endsWith("_reac")) {
                    currentReacList.addElement(anExpertiseList);
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
