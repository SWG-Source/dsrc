package script.library;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.utils;
import script.library.space_utils;
import script.library.space_crafting;
import script.library.ship_ai;
import script.library.xp;
import script.library.space_combat;
import java.lang.Math;

public class space_pilot_command extends script.base_script
{
    public space_pilot_command()
    {
    }
    public static final int DROID_VOCALIZE_REACT_CHANCE = 2;
    public static final int SHIP_DAMAGED_SKILLMOD_PENALTY_TIME = 10;
    public static final String DROID_WELDING_EFFECT_DATATABLE = "datatables/space_command/droid_welding_effects_table.iff";
    public static final String ALL_REPAIRS = "ALL_REPAIRS";
    public static final String HEAVY_REPAIRS = "HEAVY_REPAIRS";
    public static final String MEDIUM_REPAIRS = "MEDIUM_REPAIRS";
    public static final String LIGHT_REPAIRS = "LIGHT_REPAIRS";
    public static final float MAX_EPULSE_RANGE = 300.0f;
    public static final float EPULSE_DAMAGE_MULTIPLIER = 3.0f;
    public static final float MAX_NPULSE_RANGE = 500.0f;
    public static final float NOMINAL_NPULSE_YIELD = 1000.0f;
    public static final String REPAIR_EQUIPMENT_DATATABLE = "datatables/space_command/repair_ship_assignments.iff";
    public static final float IN_SPACE_REPAIR_STD_REPAIR_PERCENTAGE = 0.25f;
    public static final float IN_SPACE_REPAIR_DECAY = 0.30f;
    public static final float IN_SPACE_RELOAD_DECAY = 0.80f;
    public static final int ISR_CHAFF_TO_MISSILE_MULTIPLIER = 5;
    public static final int ISR_COST_MULTIPLIER = 50;
    public static final String DROID_PROGRAM_SIZE_DATATABLE = "datatables/space_command/droid_program_size.iff";
    public static final string_id SID_COST_FOR_REPAIRS = new string_id("space/space_pilot_command", "cost_for_repairs");
    public static final string_id SID_REPAIR_COST = new string_id("space/space_pilot_command", "repair_cost");
    public static final string_id SID_RELOAD_AND_REPAIR_COST = new string_id("space/space_pilot_command", "reload_and_repair_cost");
    public static final string_id SID_MUNITIONS_COST = new string_id("space/space_pilot_command", "munitions_cost");
    public static final string_id SID_SPACEREPAIR_NO_STATION = new string_id("space/space_pilot_command", "spacerepair_no_station");
    public static int targetTierDetect(obj_id target) throws InterruptedException
    {
        debugServerConsoleMsg(null, "SPACE_PILOT_COMMAND.targetTierDetect  ***   ENTERED FUNCTION!");
        int targetSquadId = ship_ai.unitGetSquadId(target);
        obj_id[] targetSquaddyList = ship_ai.squadGetUnitList(targetSquadId);
        int highestTierValue = 1;
        for (int i = 0; i < targetSquaddyList.length; i++)
        {
            int targetTier = 0;
            String targetName = utils.getTemplateFilenameNoPath(targetSquaddyList[i]);
            String[] targetNameDecode = split(targetName, '_');
            int lastIndex = (targetNameDecode.length) - 1;
            String tier = targetNameDecode[lastIndex];
            if (tier.equals("tier2.iff"))
            {
                targetTier = 2;
            }
            else if (tier.equals("tier3.iff"))
            {
                targetTier = 3;
            }
            else if (tier.equals("tier4.iff"))
            {
                targetTier = 4;
            }
            else if (tier.equals("tier5.iff"))
            {
                targetTier = 5;
            }
            else 
            {
                targetTier = 1;
            }
            if (targetTier > highestTierValue)
            {
                highestTierValue = targetTier;
            }
        }
        return highestTierValue;
    }
    public static boolean readyForEnergyPulse(obj_id ship, obj_id commander) throws InterruptedException
    {
        debugServerConsoleMsg(null, "SPACE_COMBAT.readyForEnergyPulse");
        if (utils.hasLocalVar(ship, "cmd.epulseone.execute"))
        {
            string_id strSpam = new string_id("space/space_interaction", "epulse_already");
            sendSystemMessage(commander, strSpam);
            return false;
        }
        if (isShipComponentDisabled(ship, ship_chassis_slot_type.SCST_reactor) || isShipComponentDisabled(ship, ship_chassis_slot_type.SCST_shield_0))
        {
            string_id strSpam = new string_id("space/space_interaction", "epulse_systems_disabled");
            sendSystemMessage(commander, strSpam);
            return false;
        }
        float capacitorCurrent = getShipCapacitorEnergyCurrent(ship);
        float capacitorMax = getShipCapacitorEnergyMaximum(ship);
        int percentCharged = (int)((capacitorCurrent / capacitorMax) * 100.0f);
        if (percentCharged < 50)
        {
            string_id strSpam = new string_id("space/space_interaction", "epulse_not_charged");
            sendSystemMessage(commander, strSpam);
            return false;
        }
        obj_id target = getLookAtTarget(ship);
        if (isIdValid(target) && !pvpCanAttack(ship, target) && (getDistance(ship, target) < MAX_EPULSE_RANGE))
        {
            string_id strSpam = new string_id("space/space_interaction", "epulse_not_enemy");
            sendSystemMessage(commander, strSpam);
            return false;
        }
        string_id strSpam = new string_id("space/space_interaction", "epulse_firing");
        sendSystemMessage(commander, strSpam);
        return true;
    }
    public static boolean executeEnergyPulse(obj_id commander, obj_id ship, String cmdLevel) throws InterruptedException
    {
        space_combat.flightDroidVocalize(ship, 1);
        final int CHANCE_OF_EXTRADMG_LVL3 = 100;
        final int CHANCE_OF_EXTRADMG_LVL2 = 50;
        final int CHANCE_OF_EXTRADMG_LVL1 = 10;
        int percentCharged = 50;
        int totalCurrentEnergy = 0;
        float capacitorCurrent = getShipCapacitorEnergyCurrent(ship);
        float capacitorMax = getShipCapacitorEnergyMaximum(ship);
        float frontShieldCurrent = getShipShieldHitpointsFrontCurrent(ship);
        float frontShieldMax = getShipShieldHitpointsFrontMaximum(ship);
        float backShieldCurrent = getShipShieldHitpointsBackCurrent(ship);
        float backShieldMax = getShipShieldHitpointsBackMaximum(ship);
        float totalShieldStrengthCurrent = frontShieldCurrent + backShieldCurrent;
        float totalShieldStrengthMax = frontShieldMax + backShieldMax;
        if (cmdLevel.equals("e_pulse_three"))
        {
            float totalEnergyMax = capacitorMax + frontShieldMax + backShieldMax;
            totalCurrentEnergy = (int)(capacitorCurrent + frontShieldCurrent + backShieldCurrent);
            percentCharged = (int)((totalCurrentEnergy / totalEnergyMax) * 100.0f);
        }
        else 
        {
            float totalEnergyMax = capacitorMax;
            totalCurrentEnergy = (int)(capacitorCurrent);
            percentCharged = (int)((totalCurrentEnergy / totalEnergyMax) * 100.0f);
        }
        int successLevel = 5;
        setShipCapacitorEnergyCurrent(ship, 5.0f);
        if (cmdLevel.equals("e_pulse_one"))
        {
            successLevel = space_combat.doPilotCommandSkillCheck(commander, "level2command");
        }
        else if (cmdLevel.equals("e_pulse_two"))
        {
            successLevel = space_combat.doPilotCommandSkillCheck(commander, "level4command");
        }
        else if (cmdLevel.equals("e_pulse_three"))
        {
            setShipShieldHitpointsFrontCurrent(ship, 5.0f);
            setShipShieldHitpointsBackCurrent(ship, 5.0f);
            successLevel = space_combat.doPilotCommandSkillCheck(commander, "level6command");
        }
        if (successLevel > 4)
        {
            string_id strSpam = new string_id("space/space_interaction", "energy_pulse_failure");
            sendSystemMessage(commander, strSpam);
            playClientEffectObj(ship, "clienteffect/space_command/emergency_power_on.cef", ship, "");
            if (successLevel == 6)
            {
                playClientEffectObj(ship, "clienteffect/space_command/emergency_power_on.cef", ship, "");
                strSpam = new string_id("space/space_interaction", "energy_pulse_big_failure");
                sendSystemMessage(commander, strSpam);
                int damageMultiplier = 1;
                float damageAmount = 10.0f;
                if (cmdLevel.equals("e_pulse_two"))
                {
                    damageMultiplier = 2;
                    if (percentCharged > 75)
                    {
                        damageAmount = 20.0f;
                    }
                    else 
                    {
                        damageAmount = 10.0f;
                    }
                }
                else if (cmdLevel.equals("e_pulse_three"))
                {
                    damageMultiplier = 3;
                    if (percentCharged > 75)
                    {
                        damageAmount = 30.0f;
                    }
                    else 
                    {
                        damageAmount = 20.0f;
                    }
                }
                int numComponentsDamaged = (int)(rand(1, 3) * damageMultiplier);
                doRandomSubSystemDamage(ship, numComponentsDamaged, damageAmount);
            }
            return true;
        }
        debugServerConsoleMsg(null, "SPACE_COMBAT.executeEnergyPulse SUCCESSLEVEL WAS " + successLevel);
        playClientEffectObj(ship, "clienteffect/space_command/shp_shocked_flash_01.cef", ship, "");
        obj_id[] possibleTargetShips = getAllObjectsWithScript(getLocation(ship), MAX_EPULSE_RANGE, "space.combat.combat_ship");
        Vector attackable_targets = new Vector();
        attackable_targets.setSize(0);
        for (obj_id possibleTarget : possibleTargetShips)
        {
            if (isIdValid(possibleTarget) && !space_utils.isPlayerControlledShip(possibleTarget))
            {
                if (pvpCanAttack(ship, possibleTarget))
                {
                    attackable_targets = utils.addElement(attackable_targets, possibleTarget);
                }
            }
        }
        float componentDamageAmount = 10.0f;
        int numDamagedShipComponents = 0;
        int numStunnedShipComponents = 0;
        for (int j = 0; j < attackable_targets.size(); j++)
        {
            obj_id attackableTarget = (obj_id) attackable_targets.get(j);
            if(!isValidId(attackableTarget) || ship_ai.isDead(attackableTarget)) continue;
            if (attackableTarget.equals(ship))
            {
                if (cmdLevel.equals("e_pulse_three"))
                {
                    if (percentCharged > 75)
                    {
                        numDamagedShipComponents = (int)(2 * (rand(0, (4 - successLevel))));
                        numStunnedShipComponents = (int)((rand(0, (4 - successLevel))) / 2);
                    }
                    else 
                    {
                        numDamagedShipComponents = (int)(2 * (rand(0, (4 - successLevel))));
                    }
                }
                else if (cmdLevel.equals("e_pulse_two"))
                {
                    if (rand(0, 100) > CHANCE_OF_EXTRADMG_LVL2)
                    {
                        if (percentCharged > 75)
                        {
                            numDamagedShipComponents = (int)(2 * (rand(0, (4 - successLevel))));
                            numStunnedShipComponents = (int)((rand(0, (4 - successLevel))) / 2);
                        }
                        else 
                        {
                            numDamagedShipComponents = (int)(2 * (rand(0, (4 - successLevel))));
                        }
                    }
                    else if (rand(0, 100) > CHANCE_OF_EXTRADMG_LVL1)
                    {
                        if (percentCharged > 75)
                        {
                            numDamagedShipComponents = (int)(rand(0, (4 - successLevel)));
                        }
                    }
                }
                if (numStunnedShipComponents == 0)
                {
                    if (space_utils.isPlayerControlledShip(attackableTarget))
                    {
                        playClientEffectObj(ship, "clienteffect/space_command/cbt_impact_emp_lght.cef", attackableTarget, "");
                    }
                    else 
                    {
                        playClientEffectObj(ship, "clienteffect/space_command/cbt_impact_emp_lght_noshake.cef", attackableTarget, "");
                    }
                }
                float damageAmount = totalCurrentEnergy;
                float distanceToBlast = Math.abs(getDistance(ship, attackableTarget));
                if (distanceToBlast < space_pilot_command.MAX_EPULSE_RANGE)
                {
                    debugServerConsoleMsg(null, "SPACE_COMBAT.readyForEnergyPulse *************** UNMODIFIED normal damage amount is: " + damageAmount);
                    debugServerConsoleMsg(null, "SPACE_COMBAT.readyForEnergyPulse *************** DISTANCE TO TARGET is: " + distanceToBlast);
                    damageAmount = totalCurrentEnergy * (((MAX_EPULSE_RANGE - distanceToBlast) / MAX_EPULSE_RANGE) * EPULSE_DAMAGE_MULTIPLIER);
                    debugServerConsoleMsg(null, "SPACE_COMBAT.readyForEnergyPulse *************** num of stunned components was: " + numStunnedShipComponents);
                    debugServerConsoleMsg(null, "SPACE_COMBAT.readyForEnergyPulse *************** num of damaged components was: " + numDamagedShipComponents);
                    debugServerConsoleMsg(null, "SPACE_COMBAT.readyForEnergyPulse *************** RANGE-MODIFIED damage amount is: " + damageAmount);
                    doRandomSubSystemDamage(attackableTarget, numDamagedShipComponents, componentDamageAmount);
                    if (numStunnedShipComponents > 0)
                    {
                        doRandomSubSystemStun(attackableTarget, numStunnedShipComponents);
                    }
                    doNormalDamage(ship, attackableTarget, damageAmount);
                }
                dictionary outparams = new dictionary();
                outparams.put("attacker", commander);
                if (space_utils.isPlayerControlledShip(ship) && isValidId(attackableTarget))
                {
                    space_utils.notifyObject(attackableTarget, "ePulseVictimized", outparams);
                }
            }
        }
        return true;
    }
    public static int randomComponentSelector(obj_id targetShip) throws InterruptedException
    {
        Vector slots = space_crafting.getAllInstalledComponents(targetShip);
        int componentToDamage = ((Integer)slots.get(rand(0, slots.size() - 1))).intValue();
        return componentToDamage;
    }
    public static void doRandomSubSystemDamage(obj_id shipDamaged, int numComponentsDamaged, float damageAmount) throws InterruptedException
    {
        float percentToDamageTo = 0.0f;
        if (damageAmount > 1.0f)
        {
            percentToDamageTo = damageAmount;
        }
        else 
        {
            percentToDamageTo = 10.0f;
        }
        final String COMPONENT_DAMAGED_CEF = "";
        obj_id pilot = getShipPilot(shipDamaged);
        for (int x = 0; x < numComponentsDamaged; x++)
        {
            space_combat.emergencyCmdDamageToShipsSystems(pilot, shipDamaged, percentToDamageTo, COMPONENT_DAMAGED_CEF, randomComponentSelector(shipDamaged));
        }
        return;
    }
    public static void doRandomSubSystemStun(obj_id shipDamaged, int numComponentsStunned) throws InterruptedException
    {
        Vector stunnedComponents = new Vector();
        stunnedComponents.setSize(0);
        for (int i = 0; i < numComponentsStunned; i++)
        {
            int stunnedModule = randomComponentSelector(shipDamaged);
            if (!isShipComponentDisabled(shipDamaged, stunnedModule))
            {
                space_utils.setComponentDisabled(shipDamaged, stunnedModule, true);
                stunnedComponents = utils.addElement(stunnedComponents, stunnedModule);
            }
        }
        if (stunnedComponents.size() < 1)
        {
            return;
        }
        dictionary outparams = new dictionary();
        outparams.put("stunned_components", stunnedComponents);
        outparams.put("stun_loops", 50);
        space_utils.notifyObject(shipDamaged, "componentsStunned", outparams);
        return;
    }
    public static void doSubSystemStun(obj_id targetShip, int component, int duration) throws InterruptedException
    {
        if (!isIdValid(targetShip) || !exists(targetShip))
        {
            return;
        }
        if (duration <= 0)
        {
            return;
        }
        Vector stunnedComponents = new Vector();
        stunnedComponents.setSize(0);
        if (!isShipComponentDisabled(targetShip, component))
        {
            space_utils.setComponentDisabled(targetShip, component, true);
            stunnedComponents = utils.addElement(stunnedComponents, component);
        }
        if (stunnedComponents.size() < 1)
        {
            return;
        }
        dictionary outparams = new dictionary();
        outparams.put("stunned_components", stunnedComponents);
        outparams.put("stun_loops", duration);
        space_utils.notifyObject(targetShip, "componentsStunned", outparams);
        return;
    }
    public static void doNormalDamage(obj_id objAttacker, obj_id shipDamaged, float damageAmount) throws InterruptedException
    {
        obj_id self = shipDamaged;
        int intTargetedComponent = space_combat.SHIP;
        int intWeaponSlot = 50;
        float fltDamage = damageAmount;
        if (hasObjVar(self, "intInvincible"))
        {
            return;
        }
        if (space_combat.hasDeathFlags(self))
        {
            return;
        }
        if (hasObjVar(self, "intNoPlayerDamage"))
        {
            if (space_utils.isPlayerControlledShip(objAttacker))
            {
                return;
            }
        }
        if (hasScript(self, "space.combat.combat_ship_capital"))
        {
            return;
        }
        obj_id objPilot = getPilotId(objAttacker);
        transform attackerTransform_w = getTransform_o2w(objAttacker);
        transform defenderTransform_w = getTransform_o2w(self);
        vector hitDirection_o = defenderTransform_w.rotateTranslate_p2l(attackerTransform_w.getPosition_p());
        int intSide = 0;
        if (hitDirection_o.z < 0.f)
        {
            intSide = 1;
        }
        if (utils.checkConfigFlag("ScriptFlags", "e3Demo"))
        {
            if (space_utils.isPlayerControlledShip(self))
            {
                int intSlot = rand(1, 4);
                int intIntensity = rand(1, 100);
                space_combat.doInteriorDamageNotification(self, intSlot, 100, intIntensity);
                return;
            }
        }
        if (fltDamage > 0.f)
        {
            if (self != objAttacker)
            {
                notifyShipDamage(self, objAttacker, fltDamage);
                ship_ai.unitAddDamageTaken(self, objAttacker, fltDamage);
            }
        }
        if (space_utils.isPlayerControlledShip(self))
        {
            if (!utils.hasLocalVar(self, "cmd.wasDamagedSkillMod"))
            {
                int time = getGameTime();
                utils.setLocalVar(self, "cmd.wasDamagedSkillMod", SHIP_DAMAGED_SKILLMOD_PENALTY_TIME + time);
            }
        }
        float fltRemainingDamage = space_combat.doShieldDamage(objAttacker, self, intWeaponSlot, fltDamage, intSide);
        if (fltRemainingDamage > 0)
        {
            fltRemainingDamage = space_combat.doArmorDamage(objAttacker, self, intWeaponSlot, fltRemainingDamage, intSide);
            if (fltRemainingDamage > 0)
            {
                fltRemainingDamage = space_combat.doComponentDamage(objAttacker, self, intWeaponSlot, intTargetedComponent, fltRemainingDamage, intSide);
                if (fltRemainingDamage > 0)
                {
                    if (rand(1, 10) < DROID_VOCALIZE_REACT_CHANCE)
                    {
                        if (space_utils.isPlayerControlledShip(self))
                        {
                            space_combat.flightDroidVocalize(self, 1);
                        }
                    }
                    fltRemainingDamage = space_combat.doChassisDamage(objAttacker, self, intWeaponSlot, fltRemainingDamage);
                    if (fltRemainingDamage > 0)
                    {
                        setShipCurrentChassisHitPoints(self, 0.0f);
                        obj_id objDefenderPilot = getPilotId(self);
                        if (!space_utils.isPlayerControlledShip(self))
                        {
                            if (space_utils.isPlayerControlledShip(objAttacker))
                            {
                                space_combat.grantRewardsAndCreditForKills(self);
                                space_combat.targetDestroyed(self);
                                return;
                            }
                            else 
                            {
                                space_combat.targetDestroyed(self);
                                return;
                            }
                        }
                        else 
                        {
                            space_combat.setDeathFlags(self);
                            dictionary dctParams = new dictionary();
                            dctParams.put("objAttacker", objAttacker);
                            dctParams.put("objShip", self);
                            space_utils.notifyObject(objDefenderPilot, "playerShipDestroyed", dctParams);
                            float fltIntensity = rand(0, 1.0f);
                            handleShipDestruction(self, fltIntensity);
                            utils.setScriptVar(self, "intPVPKill", 1);
                            messageTo(self, "killSpacePlayer", null, 10.0f, true);
                        }
                    }
                }
                if (rand(1, 10) < DROID_VOCALIZE_REACT_CHANCE)
                {
                    if (space_utils.isPlayerControlledShip(self))
                    {
                        space_combat.flightDroidVocalize(self, 2);
                    }
                }
            }
        }
        return;
    }
    public static boolean readyForVampiricRepairOther(obj_id ship, obj_id commander, obj_id target) throws InterruptedException
    {
        debugServerConsoleMsg(null, "entered readyForVampiricRepairOther");
        if (!isIdValid(ship))
        {
            return false;
        }
        if (!isIdValid(commander))
        {
            return false;
        }
        if (isShipComponentDisabled(ship, ship_chassis_slot_type.SCST_reactor))
        {
            string_id strSpam = new string_id("space/space_interaction", "systems_disabled_repair_abort");
            sendSystemMessage(commander, strSpam);
            return false;
        }
        if (!isIdValid(target))
        {
            string_id strSpam = new string_id("space/space_interaction", "vrepairo_repair_target_invalid");
            sendSystemMessage(commander, strSpam);
            return false;
        }
        if (!space_utils.isPlayerControlledShip(target))
        {
            string_id strSpam = new string_id("space/space_interaction", "vrepairo_cant_repair_npc");
            string_id strSpam2 = new string_id("space/space_interaction", "vrepairo_target_pc");
            sendSystemMessage(commander, strSpam);
            sendSystemMessage(commander, strSpam2);
            return false;
        }
        float yourSpeed = getShipCurrentSpeed(ship);
        float targetSpeed = getShipCurrentSpeed(target);
        float rangeToTarget = getDistance(ship, target);
        if (yourSpeed > 5.0f || targetSpeed > 5.0f)
        {
            string_id strSpam = new string_id("space/space_interaction", "repairo_out_of_range");
            sendSystemMessage(commander, strSpam);
            string_id strSpam2 = new string_id("space/space_pilot_command", "cannot_repair_other_must_shutdown");
            sendSystemMessage(commander, strSpam2);
            return false;
        }
        if (!pvpCanAttack(ship, target))
        {
            string_id strSpam = new string_id("space/space_interaction", "repairo_beginning");
            sendSystemMessage(commander, strSpam);
            return true;
        }
        string_id strSpam = new string_id("space/space_interaction", "repairo_cant_heal_enemy");
        sendSystemMessage(commander, strSpam);
        return false;
    }
    public static float sumShipComponentHitpointsCurrent(obj_id shipId) throws InterruptedException
    {
        return sumShipComponentHitpointsCurrent(shipId, 100.0f);
    }
    public static float sumShipComponentHitpointsCurrent(obj_id shipId, float percentage) throws InterruptedException
    {
        float hitpointsSum = 0.0f;
        int[] installedSlots = space_crafting.getShipInstalledSlots(shipId);
        for (int i = 0; i < installedSlots.length; i++)
        {
            int chassisSlot = installedSlots[i];
            hitpointsSum += getShipComponentHitpointsCurrent(shipId, chassisSlot);
        }
        return hitpointsSum * (percentage / 100.0f);
    }
    public static float sumShipComponentHitpointsMaximum(obj_id shipId) throws InterruptedException
    {
        float hitpointsSum = 0.0f;
        int[] installedSlots = space_crafting.getShipInstalledSlots(shipId);
        for (int i = 0; i < installedSlots.length; i++)
        {
            int chassisSlot = installedSlots[i];
            hitpointsSum += getShipComponentHitpointsMaximum(shipId, chassisSlot);
        }
        return hitpointsSum;
    }
    public static float calcShipPercentDamaged(obj_id shipId) throws InterruptedException
    {
        float totalHitpointsMaximum = sumShipComponentHitpointsMaximum(shipId);
        float totalHitpointsCurrent = sumShipComponentHitpointsCurrent(shipId);
        if (totalHitpointsMaximum <= 0.0f)
        {
            return 0.0f;
        }
        return (float)Math.rint(((totalHitpointsMaximum - totalHitpointsCurrent) / totalHitpointsMaximum) * 100.0f);
    }
    public static float calcShipComponentPercentDamaged(obj_id shipId, int chassisSlot) throws InterruptedException
    {
        float currentHitPoints = getShipComponentHitpointsCurrent(shipId, chassisSlot);
        float maxHitPoints = getShipComponentHitpointsMaximum(shipId, chassisSlot);
        if (maxHitPoints <= 0.0f)
        {
            return 0.0f;
        }
        return (float)Math.rint(((maxHitPoints - currentHitPoints) / maxHitPoints) * 100.0f);
    }
    public static int findMostDamagedShipComponent(obj_id shipId) throws InterruptedException
    {
        float highestDamagePercentage = 0.0f;
        int mostDamagedShipComponent = -1;
        int[] installedSlots = space_crafting.getShipInstalledSlots(shipId);
        for (int i = 0; i < installedSlots.length; i++)
        {
            int chassisSlot = installedSlots[i];
            float damagePercentage = calcShipComponentPercentDamaged(shipId, chassisSlot);
            if (damagePercentage >= highestDamagePercentage)
            {
                highestDamagePercentage = damagePercentage;
                mostDamagedShipComponent = chassisSlot;
            }
        }
        return mostDamagedShipComponent;
    }
    public static float damageAllShipComponents(obj_id shipId, float percentToDamage) throws InterruptedException
    {
        float totalDamage = 0;
        int[] installedSlots = space_crafting.getShipInstalledSlots(shipId);
        for (int i = 0; i < installedSlots.length; i++)
        {
            int chassisSlot = installedSlots[i];
            float maxHP = getShipComponentHitpointsMaximum(shipId, chassisSlot);
            float damage = maxHP * (percentToDamage / 100.0f);
            totalDamage += damage;
            maxHP -= damage;
            if (maxHP < 1)
            {
                maxHP = 1;
                CustomerServiceLog("space_repair", "damageAllShipComponents resulted in <1 HP on component " + chassisSlot + ". Adjusted to 1. Ship=" + shipId + " Owner=%TU", getOwner(shipId));
            }
            setShipComponentHitpointsCurrent(shipId, chassisSlot, maxHP);
        }
        allPurposeShipComponentReset(shipId);
        return totalDamage;
    }
    public static void vampiricOtherTypeShipsSystemsRepair(obj_id donorPilot, obj_id donorShip, obj_id recipientShip, int successLevel) throws InterruptedException
    {
        obj_id recipientPilot = getPilotId(recipientShip);
        int componentToRepair = findMostDamagedShipComponent(recipientShip);
        float componentToRepairHP = getShipComponentHitpointsCurrent(recipientShip, componentToRepair);
        float componentToRepairMaxHP = getShipComponentHitpointsMaximum(recipientShip, componentToRepair);
        float componentToRepairPercentDamaged = calcShipComponentPercentDamaged(recipientShip, componentToRepair);
        float donorPercentDamaged = calcShipPercentDamaged(donorShip);
        float percentDamagedDelta = componentToRepairPercentDamaged - donorPercentDamaged;
        if (percentDamagedDelta < 5.0f)
        {
            string_id strSpam = new string_id("space/space_interaction", "vampiric_other_repair_useless_abort");
            sendSystemMessage(donorPilot, strSpam);
            return;
        }
        float maxAmountToRepair = sumShipComponentHitpointsCurrent(donorShip, 90.0f);
        float amountToRepair = componentToRepairMaxHP * (percentDamagedDelta / 100.0f);
        if (amountToRepair > maxAmountToRepair)
        {
            amountToRepair = maxAmountToRepair;
        }
        componentToRepairHP += amountToRepair;
        if (componentToRepairHP > componentToRepairMaxHP)
        {
            componentToRepairHP = componentToRepairMaxHP;
        }
        setShipComponentHitpointsCurrent(recipientShip, componentToRepair, componentToRepairHP);
        allPurposeShipComponentReset(recipientShip);
        string_id strSpam = new string_id("space/space_interaction", "vampiric_repair_other_underway");
        sendSystemMessage(donorPilot, strSpam);
        string_id strSpam2 = new string_id("space/space_pilot_command", "vampiric_repair_other_underway_recipient");
        sendSystemMessage(recipientPilot, strSpam2);
        int damageLoops = 1 + ((int)componentToRepairPercentDamaged / 20);
        dictionary params = new dictionary();
        params.put("damage_loops", damageLoops);
        params.put("pilot", donorPilot);
        messageTo(donorShip, "vRepairDamageCEFLoop", params, 3.0f, false);
        params.put("pilot", recipientPilot);
        messageTo(recipientShip, "vRepairDamageCEFLoop", params, 3.0f, false);
        float repairCost = amountToRepair;
        switch (successLevel)
        {
            case 1:
            strSpam = new string_id("space/space_interaction", "vampiric_great_success");
            sendSystemMessage(donorPilot, strSpam);
            repairCost -= repairCost * 0.25f;
            break;
            case 2:
            strSpam = new string_id("space/space_interaction", "vampiric_good_success");
            sendSystemMessage(donorPilot, strSpam);
            repairCost -= repairCost * 0.15f;
            break;
            case 3:
            strSpam = new string_id("space/space_interaction", "vampiric_success");
            sendSystemMessage(donorPilot, strSpam);
            break;
            case 4:
            strSpam = new string_id("space/space_interaction", "vampiric_slight_fail");
            sendSystemMessage(donorPilot, strSpam);
            repairCost += repairCost * 0.05f;
            break;
            case 5:
            LOG("space", "vampiricOtherTypeShipsSystemsRepair(): successLevel of " + successLevel + " is UNEXPECTED");
            break;
            case 6:
            strSpam = new string_id("space/space_interaction", "vampiric_big_fail");
            sendSystemMessage(donorPilot, strSpam);
            repairCost += repairCost * 0.20f;
            break;
            default:
            LOG("space", "vampiricOtherTypeShipsSystemsRepair(): successLevel of " + successLevel + " is UNKNOWN");
            break;
        }
        float percentToDamage = (repairCost / sumShipComponentHitpointsMaximum(donorShip)) * 100.0f;
        damageAllShipComponents(donorShip, percentToDamage);
        return;
    }
    public static String randomWeldingCEFPicker() throws InterruptedException
    {
        String[] possibleEffects = dataTableGetStringColumnNoDefaults(DROID_WELDING_EFFECT_DATATABLE, ALL_REPAIRS);
        String clientEffect = possibleEffects[(int)(rand(0, possibleEffects.length - 1))];
        return clientEffect;
    }
    public static boolean readyForNebulaBlast(obj_id ship, obj_id commander) throws InterruptedException
    {
        debugServerConsoleMsg(null, "entered SPACE_COMBAT.readyForNebulaBlast");
        if (utils.hasLocalVar(ship, "cmd.nblast.execute"))
        {
            string_id strSpam = new string_id("space/space_interaction", "nblast_already");
            sendSystemMessage(commander, strSpam);
            debugServerConsoleMsg(null, "SPACE_COMBAT.readyForNebulaBlast we think we're already doing one");
            return false;
        }
        if (isShipComponentDisabled(ship, ship_chassis_slot_type.SCST_reactor) || isShipComponentDisabled(ship, ship_chassis_slot_type.SCST_shield_0))
        {
            string_id strSpam = new string_id("space/space_interaction", "nblast_systems_disabled");
            sendSystemMessage(commander, strSpam);
            debugServerConsoleMsg(null, "SPACE_COMBAT.readyForNebulaBlast we think that systems are enabled");
            return false;
        }
        if (!shipIsInNebula(ship, null))
        {
            string_id strSpam = new string_id("space/space_interaction", "nblast_no_nebula");
            sendSystemMessage(commander, strSpam);
            return false;
        }
        float capacitorCurrent = getShipCapacitorEnergyCurrent(ship);
        float capacitorMax = getShipCapacitorEnergyMaximum(ship);
        int percentCharged = (int)((capacitorCurrent / capacitorMax) * 100.0f);
        if (percentCharged < 50)
        {
            string_id strSpam = new string_id("space/space_interaction", "nblast_not_ready");
            sendSystemMessage(commander, strSpam);
            debugServerConsoleMsg(null, "SPACE_COMBAT.readyForNebulaBlast we think that there isn't enough energy in the capactor");
            return false;
        }
        obj_id target = getLookAtTarget(ship);
        if (pvpCanAttack(ship, target) || !isIdValid(target) || target == ship)
        {
            string_id strSpam = new string_id("space/space_interaction", "nblast_calling");
            sendSystemMessage(commander, strSpam);
            return true;
        }
        string_id strSpam = new string_id("space/space_interaction", "nblast_unknown_failure");
        sendSystemMessage(commander, strSpam);
        debugServerConsoleMsg(null, "SPACE_COMBAT.readyForNebulaBlast we think that we failed for unknown reasons");
        return false;
    }
    public static void doNebulaBlast(obj_id commander, obj_id firingShip) throws InterruptedException
    {
        space_combat.flightDroidVocalize(firingShip, 1);
        float nominalYieldMod = 0.0f;
        float selfDamageRatio = 0.0f;
        float nominalYield = NOMINAL_NPULSE_YIELD;
        int successLevel = space_combat.doPilotCommandSkillCheck(commander, "level2command");
        switch (successLevel)
        {
            case 6:
            string_id strSpam = new string_id("space/space_interaction", "nblast_skill_abort_fail");
            sendSystemMessage(commander, strSpam);
            nominalYield = NOMINAL_NPULSE_YIELD * 1.0f;
            selfDamageRatio = 0.70f;
            break;
            case 5:
            strSpam = new string_id("space/space_interaction", "nblast_skill_abort");
            sendSystemMessage(commander, strSpam);
            break;
            case 4:
            strSpam = new string_id("space/space_interaction", "nblast_execute");
            sendSystemMessage(commander, strSpam);
            nominalYield = NOMINAL_NPULSE_YIELD * 1.0f;
            selfDamageRatio = 0.50f;
            break;
            case 3:
            strSpam = new string_id("space/space_interaction", "nblast_execute");
            sendSystemMessage(commander, strSpam);
            nominalYield = NOMINAL_NPULSE_YIELD * 1.0f;
            selfDamageRatio = 0.30f;
            break;
            case 2:
            strSpam = new string_id("space/space_interaction", "nblast_execute");
            sendSystemMessage(commander, strSpam);
            nominalYield = NOMINAL_NPULSE_YIELD * 1.0f;
            selfDamageRatio = 0.20f;
            break;
            case 1:
            strSpam = new string_id("space/space_interaction", "nblast_execute");
            sendSystemMessage(commander, strSpam);
            nominalYield = NOMINAL_NPULSE_YIELD * 1.0f;
            selfDamageRatio = 0.05f;
            break;
        }
        float targetDamageYield = (nominalYield - (nominalYield * selfDamageRatio));
        debugServerConsoleMsg(null, "base targetDamageYield is: " + targetDamageYield);
        doNormalDamage(firingShip, firingShip, (nominalYield * selfDamageRatio));
        debugServerConsoleMsg(null, "damage yield to self is: " + (nominalYield * selfDamageRatio));
        obj_id[] targetShips = getAllObjectsWithScript(getLocation(firingShip), 150.0f, "space.combat.combat_ship");
        for (int i = 0; i < targetShips.length; i++)
        {
            if ((!isIdValid(targetShips[i])) && (targetShips[i] != firingShip))
            {
                float distanceToBlast = Math.abs(getDistance(firingShip, targetShips[i]));
                if (distanceToBlast < space_pilot_command.MAX_NPULSE_RANGE)
                {
                    space_pilot_command.nblastDamageTarget(firingShip, targetShips[i], targetDamageYield, distanceToBlast);
                }
            }
        }
    }
    public static void nblastDamageTarget(obj_id firingShip, obj_id victim, float blastNominalYield, float distanceToBlast) throws InterruptedException
    {
        if (!isIdValid(victim) || !exists(victim))
        {
            return;
        }
        float damageAmount = blastNominalYield * ((100.0f - (distanceToBlast / MAX_NPULSE_RANGE) * 100.0f) / 100.0f);
        debugServerConsoleMsg(null, "**************************************************** NBLAST damage done to target:" + victim + " was: " + damageAmount);
        doNormalDamage(firingShip, victim, damageAmount);
        playClientEffectObj(victim, "clienteffect/space_command/shp_shocked_01_noshake.cef", victim, "");
        if (exists(victim) && (space_utils.isPlayerControlledShip(victim)))
        {
            obj_id victimPilot = getShipPilot(victim);
            if (distanceToBlast < (MAX_NPULSE_RANGE / 2))
            {
                playClientEffectObj(victimPilot, "clienteffect/space_command/aftershock_medium.cef", victim, "");
            }
            else 
            {
                playClientEffectObj(victimPilot, "clienteffect/space_command/aftershock_small.cef", victim, "");
            }
        }
        return;
    }
    public static boolean readyForJumpStart(obj_id ship, obj_id commander, obj_id target, String commandLevel) throws InterruptedException
    {
        debugServerConsoleMsg(null, "entered readyForJumpStart");
        if (!isIdValid(ship) || !isIdValid(commander))
        {
            return false;
        }
        if (!space_utils.isShip(target))
        {
            string_id strSpam = new string_id("space/space_interaction", "target_not_ship");
            space_utils.sendSystemMessageShip(ship, strSpam, true, false, false, true);
            return false;
        }
        if (!isShipSlotInstalled(target, ship_chassis_slot_type.SCST_shield_0))
        {
            string_id strSpam = new string_id("space/space_interaction", "jstart_target_no_shield");
            space_utils.sendSystemMessageShip(ship, strSpam, true, false, false, true);
            return false;
        }
        if (isShipComponentDisabled(ship, ship_chassis_slot_type.SCST_reactor) || isShipComponentDisabled(ship, ship_chassis_slot_type.SCST_capacitor))
        {
            string_id strSpam = new string_id("space/space_interaction", "jstart_systems_disabled");
            space_utils.sendSystemMessageShip(ship, strSpam, true, false, false, true);
            return false;
        }
        else if (commandLevel.equals("jstart2") || commandLevel.equals("jstart3"))
        {
            if (!isShipSlotInstalled(ship, ship_chassis_slot_type.SCST_shield_0))
            {
                string_id strSpam = new string_id("space/space_interaction", "jstart_no_shield");
                space_utils.sendSystemMessageShip(ship, strSpam, true, false, false, true);
                return false;
            }
            else if (isShipComponentDisabled(ship, ship_chassis_slot_type.SCST_shield_0))
            {
                string_id strSpam = new string_id("space/space_interaction", "jstart_systems_disabled");
                space_utils.sendSystemMessageShip(ship, strSpam, true, false, false, true);
                return false;
            }
        }
        float capacitorCurrent = getShipCapacitorEnergyCurrent(ship);
        float capacitorMax = getShipCapacitorEnergyMaximum(ship);
        int percentCharged = (int)((capacitorCurrent / capacitorMax) * 100.0f);
        if (percentCharged < 50)
        {
            string_id strSpam = new string_id("space/space_interaction", "jstart_not_charged");
            space_utils.sendSystemMessageShip(ship, strSpam, true, false, false, true);
            return false;
        }
        if (!isIdValid(target) || !exists(target) || !space_utils.isPlayerControlledShip(target))
        {
            string_id strSpam = new string_id("space/space_interaction", "jstart_invalid_target");
            space_utils.sendSystemMessageShip(ship, strSpam, true, false, false, true);
            return false;
        }
        float yourSpeed = getShipCurrentSpeed(ship);
        float targetSpeed = getShipCurrentSpeed(target);
        float rangeToTarget = getDistance(ship, target);
        if (rangeToTarget > 20.0f)
        {
            string_id strSpam = new string_id("space/space_interaction", "jstart_out_of_range");
            sendSystemMessage(commander, strSpam);
            space_utils.sendSystemMessageShip(ship, strSpam, true, false, false, true);
            return false;
        }
        if (!pvpCanAttack(ship, target))
        {
            string_id strSpam = new string_id("space/space_interaction", "jstart_beginning");
            space_utils.sendSystemMessageShip(ship, strSpam, true, false, false, true);
            return true;
        }
        string_id strSpam = new string_id("space/space_interaction", "jstart_cant_heal_enemy");
        space_utils.sendSystemMessageShip(ship, strSpam, true, false, false, true);
        return false;
    }
    public static boolean executeJstart(obj_id commander, obj_id ship, obj_id target, String cmdLevel) throws InterruptedException
    {
        if (!isIdValid(ship) || !isIdValid(commander) || !isIdValid(target))
        {
            debugServerConsoleMsg(null, "space_pilot_command.executeJstart ***** dumping out with FALSE reading, because obj_id's passed in are invalid");
            return false;
        }
        space_combat.flightDroidVocalize(ship, 1);
        final int CHANCE_OF_SHORT_CIRCUIT_LVL3 = 100;
        final int CHANCE_OF_SHORT_CIRCUIT_LVL2 = 50;
        final int CHANCE_OF_SHORT_CIRCUIT_LVL1 = 10;
        final float CMD_LEVEL_TWO_MIN_SHIELD = 0.50f;
        final float CMD_LEVEL_THREE_MIN_SHIELD = 0.10f;
        int donorPercentCharged = 50;
        int donorTotalCurrentEnergy = 0;
        float donorCapacitorCurrent = (getShipCapacitorEnergyCurrent(ship)) / 8;
        float donorCapacitorMax = getShipCapacitorEnergyMaximum(ship);
        float recipientCapacitorCurrent = getShipCapacitorEnergyCurrent(ship);
        float recipientCapacitorMax = getShipCapacitorEnergyMaximum(ship);
        float donorFrontShieldCurrent = getShipShieldHitpointsFrontCurrent(ship);
        float donorFrontShieldMax = getShipShieldHitpointsFrontMaximum(ship);
        float donorBackShieldCurrent = getShipShieldHitpointsBackCurrent(ship);
        float donorBackShieldMax = getShipShieldHitpointsBackMaximum(ship);
        float donorTotalShieldStrengthCurrent = donorFrontShieldCurrent + donorBackShieldCurrent;
        float donorTotalShieldStrengthMax = donorFrontShieldMax + donorBackShieldMax;
        float recipientFrontShieldCurrent = getShipShieldHitpointsFrontCurrent(target);
        float recipientFrontShieldMax = getShipShieldHitpointsFrontMaximum(target);
        float recipientBackShieldCurrent = getShipShieldHitpointsBackCurrent(target);
        float recipientBackShieldMax = getShipShieldHitpointsBackMaximum(target);
        float recipientTotalShieldStrengthCurrent = recipientFrontShieldCurrent + recipientBackShieldCurrent;
        float recipientTotalShieldStrengthMax = recipientFrontShieldMax + recipientBackShieldMax;
        float recipientTotalEnergyNeeds = recipientTotalShieldStrengthMax - recipientTotalShieldStrengthCurrent;
        float donorTotalAvailableEnergy = donorCapacitorCurrent;
        float donorAvailableFrontShieldEnergy = 0.0f;
        float donorAvailableBackShieldEnergy = 0.0f;
        if (cmdLevel.equals("jstart_three"))
        {
            donorAvailableFrontShieldEnergy = donorFrontShieldCurrent - (donorFrontShieldMax * CMD_LEVEL_THREE_MIN_SHIELD);
            if (donorAvailableFrontShieldEnergy < 0.0f)
            {
                donorAvailableFrontShieldEnergy = 0;
            }
            donorAvailableBackShieldEnergy = donorBackShieldCurrent - (donorBackShieldMax * CMD_LEVEL_THREE_MIN_SHIELD);
            if (donorAvailableBackShieldEnergy < 0.0f)
            {
                donorAvailableBackShieldEnergy = 0;
            }
            donorTotalAvailableEnergy += donorAvailableFrontShieldEnergy + donorAvailableBackShieldEnergy;
        }
        else if (cmdLevel.equals("jstart_two"))
        {
            donorAvailableFrontShieldEnergy = donorFrontShieldCurrent - (donorFrontShieldMax * CMD_LEVEL_TWO_MIN_SHIELD);
            if (donorAvailableFrontShieldEnergy < 0.0f)
            {
                donorAvailableFrontShieldEnergy = 0;
            }
            donorAvailableBackShieldEnergy = donorBackShieldCurrent - (donorBackShieldMax * CMD_LEVEL_TWO_MIN_SHIELD);
            if (donorAvailableBackShieldEnergy < 0.0f)
            {
                donorAvailableBackShieldEnergy = 0;
            }
            donorTotalAvailableEnergy += donorAvailableFrontShieldEnergy + donorAvailableBackShieldEnergy;
        }
        float donorRecipientenergyDelta = donorTotalAvailableEnergy - recipientTotalEnergyNeeds;
        if (recipientTotalEnergyNeeds > donorTotalAvailableEnergy)
        {
            recipientTotalEnergyNeeds = donorTotalAvailableEnergy;
        }
        float donorEnergyUsed = recipientTotalEnergyNeeds;
        float runoffEnergy = 0.0f;
        if (cmdLevel.equals("jstart_one"))
        {
            if (donorCapacitorCurrent - donorEnergyUsed < 5.0f)
            {
                setShipCapacitorEnergyCurrent(ship, 5.0f);
            }
            else 
            {
                setShipCapacitorEnergyCurrent(ship, (donorCapacitorCurrent - donorEnergyUsed) * 8);
            }
        }
        else if (cmdLevel.equals("jstart_two"))
        {
            if (donorEnergyUsed > donorCapacitorCurrent)
            {
                donorEnergyUsed = (donorEnergyUsed - donorCapacitorCurrent) / 2.0f;
                setShipCapacitorEnergyCurrent(ship, 5.0f);
                if (donorEnergyUsed > donorAvailableFrontShieldEnergy)
                {
                    donorEnergyUsed = (donorEnergyUsed - donorAvailableFrontShieldEnergy);
                    setShipShieldHitpointsFrontCurrent(ship, (donorFrontShieldMax * CMD_LEVEL_TWO_MIN_SHIELD));
                    setShipShieldHitpointsBackCurrent(ship, (donorBackShieldCurrent - donorEnergyUsed));
                }
                else if (donorEnergyUsed > donorAvailableBackShieldEnergy)
                {
                    donorEnergyUsed = (donorEnergyUsed - donorAvailableBackShieldEnergy);
                    setShipShieldHitpointsBackCurrent(ship, (donorBackShieldMax * CMD_LEVEL_TWO_MIN_SHIELD));
                    setShipShieldHitpointsFrontCurrent(ship, (donorFrontShieldCurrent - donorEnergyUsed));
                }
                else 
                {
                    setShipShieldHitpointsFrontCurrent(ship, (donorFrontShieldCurrent - donorEnergyUsed));
                    setShipShieldHitpointsBackCurrent(ship, (donorBackShieldCurrent - donorEnergyUsed));
                }
            }
        }
        else if (cmdLevel.equals("jstart_three"))
        {
            if (donorEnergyUsed > donorCapacitorCurrent)
            {
                debugServerConsoleMsg(null, "space_pilot_commands.executeJstart ---------    donorEnergyUsed > donorCapacitorCurrent ");
                debugServerConsoleMsg(null, "space_pilot_commands.executeJstart ---------    donorCapacitorCurrent = " + donorCapacitorCurrent);
                donorEnergyUsed = (donorEnergyUsed - donorCapacitorCurrent) / 2.0f;
                debugServerConsoleMsg(null, "space_pilot_commands.executeJstart ---------    donorEnergyUsed #11 = " + donorEnergyUsed);
                setShipCapacitorEnergyCurrent(ship, 5.0f);
                if (donorEnergyUsed > donorAvailableFrontShieldEnergy)
                {
                    debugServerConsoleMsg(null, "space_pilot_commands.executeJstart ---------    donorEnergyUsed > donorAvailableFrontShieldEnergy ");
                    donorEnergyUsed = (donorEnergyUsed - donorAvailableFrontShieldEnergy);
                    debugServerConsoleMsg(null, "space_pilot_commands.executeJstart ---------    donorEnergyUsed #12 = " + donorEnergyUsed);
                    setShipShieldHitpointsFrontCurrent(ship, (donorFrontShieldMax * CMD_LEVEL_THREE_MIN_SHIELD));
                    debugServerConsoleMsg(null, "space_pilot_commands.executeJstart ---------    setting donor front shield to = " + (donorFrontShieldMax * CMD_LEVEL_THREE_MIN_SHIELD));
                    setShipShieldHitpointsBackCurrent(ship, (donorBackShieldCurrent - donorEnergyUsed));
                    debugServerConsoleMsg(null, "space_pilot_commands.executeJstart ---------    setting donor back shield to = " + (donorBackShieldCurrent - donorEnergyUsed));
                }
                else if (donorEnergyUsed > donorAvailableBackShieldEnergy)
                {
                    debugServerConsoleMsg(null, "space_pilot_commands.executeJstart ---------    donorEnergyUsed > donorAvailableBackShieldEnergy ");
                    donorEnergyUsed = (donorEnergyUsed - donorAvailableBackShieldEnergy);
                    debugServerConsoleMsg(null, "space_pilot_commands.executeJstart ---------    donorEnergyUsed #13 = " + donorEnergyUsed);
                    setShipShieldHitpointsBackCurrent(ship, (donorBackShieldMax * CMD_LEVEL_THREE_MIN_SHIELD));
                    debugServerConsoleMsg(null, "space_pilot_commands.executeJstart ---------    setting donor back shield to = " + (donorBackShieldMax * CMD_LEVEL_THREE_MIN_SHIELD));
                    setShipShieldHitpointsFrontCurrent(ship, (donorFrontShieldCurrent - donorEnergyUsed));
                    debugServerConsoleMsg(null, "space_pilot_commands.executeJstart ---------    setting donor front shield to = " + (donorFrontShieldCurrent - donorEnergyUsed));
                }
                else 
                {
                    setShipShieldHitpointsFrontCurrent(ship, (donorFrontShieldCurrent - donorEnergyUsed));
                    setShipShieldHitpointsBackCurrent(ship, (donorBackShieldCurrent - donorEnergyUsed));
                }
            }
            else 
            {
                setShipCapacitorEnergyCurrent(ship, ((donorCapacitorCurrent) - donorEnergyUsed) * 8);
            }
        }
        else 
        {
            debugServerConsoleMsg(null, "crazy wack-ness! Dumping out of jumpstart activation due to unknown command level");
            return false;
        }
        int successLevel = 5;
        if (cmdLevel.equals("jstart_one"))
        {
            successLevel = space_combat.doPilotCommandSkillCheck(commander, "level2command");
        }
        else if (cmdLevel.equals("jstart_two"))
        {
            successLevel = space_combat.doPilotCommandSkillCheck(commander, "level4command");
        }
        else if (cmdLevel.equals("jstart_three"))
        {
            successLevel = space_combat.doPilotCommandSkillCheck(commander, "level6command");
        }
        debugServerConsoleMsg(null, "SPACE_COMBAT.executeJstart SUCCESSLEVEL WAS " + successLevel);
        if (successLevel > 4)
        {
            if (successLevel == 5)
            {
                string_id strSpam = new string_id("space/space_interaction", "jstart_failure");
                space_utils.sendSystemMessageShip(ship, strSpam, true, false, false, true);
                playClientEffectObj(ship, "clienteffect/space_command/emergency_power_on.cef", ship, "");
            }
            else if (successLevel == 6)
            {
                playClientEffectObj(ship, "clienteffect/space_command/emergency_power_on.cef", ship, "");
                string_id strSpam = new string_id("space/space_interaction", "jstart_big_failure");
                string_id strSpam2 = new string_id("space/space_interaction", "jstart_big_failure2");
                space_utils.sendSystemMessageShip(ship, strSpam, true, false, false, true);
                space_utils.sendSystemMessageShip(ship, strSpam2, true, false, false, true);
                final String COMPONENT_DAMAGED_CEF = "";
                float percentToDamageTo = 15.0f;
                if (!cmdLevel.equals("jstart_one"))
                {
                    space_combat.emergencyCmdDamageToShipsSystems(commander, ship, percentToDamageTo, COMPONENT_DAMAGED_CEF, ship_chassis_slot_type.SCST_shield_0);
                    if (cmdLevel.equals("jstart_two"))
                    {
                    }
                    else if (cmdLevel.equals("jstart_three"))
                    {
                    }
                }
                space_combat.emergencyCmdDamageToShipsSystems(commander, ship, percentToDamageTo, COMPONENT_DAMAGED_CEF, ship_chassis_slot_type.SCST_capacitor);
            }
            return true;
        }
        debugServerConsoleMsg(null, "space_pilot_commands.executeJstart ---------    runoffEnergy = " + runoffEnergy);
        float totalShieldRegenPoints = recipientTotalEnergyNeeds - runoffEnergy;
        debugServerConsoleMsg(null, "space_pilot_commands.executeJstart ---------    totalShieldRegenPoints #1 = " + totalShieldRegenPoints);
        float recipientFrontShieldDelta = recipientFrontShieldMax - recipientFrontShieldCurrent;
        float recipientBackShieldDelta = recipientBackShieldMax - recipientBackShieldCurrent;
        debugServerConsoleMsg(null, "space_pilot_commands.executeJstart ---------    Current recipientFrontShieldDelta = " + recipientFrontShieldDelta);
        debugServerConsoleMsg(null, "space_pilot_commands.executeJstart ---------    Current recipientBackShieldDelta = " + recipientBackShieldDelta);
        if (totalShieldRegenPoints / 2.0f > recipientFrontShieldDelta)
        {
            totalShieldRegenPoints = (totalShieldRegenPoints / 2.0f) + (totalShieldRegenPoints / 2.0f - recipientFrontShieldDelta);
            setShipShieldHitpointsFrontCurrent(target, recipientFrontShieldMax);
            if (totalShieldRegenPoints > recipientFrontShieldDelta)
            {
                totalShieldRegenPoints = recipientFrontShieldDelta;
            }
            setShipShieldHitpointsBackCurrent(target, (donorBackShieldCurrent + totalShieldRegenPoints));
        }
        else if (totalShieldRegenPoints / 2.0f > recipientBackShieldDelta)
        {
            totalShieldRegenPoints = (totalShieldRegenPoints / 2.0f) + (totalShieldRegenPoints / 2.0f - recipientBackShieldDelta);
            setShipShieldHitpointsBackCurrent(target, recipientBackShieldMax);
            if (totalShieldRegenPoints > recipientFrontShieldDelta)
            {
                totalShieldRegenPoints = recipientFrontShieldDelta;
            }
            setShipShieldHitpointsFrontCurrent(target, (donorFrontShieldCurrent + totalShieldRegenPoints));
        }
        else 
        {
            setShipShieldHitpointsFrontCurrent(target, (recipientFrontShieldCurrent + (totalShieldRegenPoints / 2.0f)));
            setShipShieldHitpointsBackCurrent(target, (recipientBackShieldCurrent + (totalShieldRegenPoints / 2.0f)));
        }
        playClientEffectObj(ship, "clienteffect/space_command/shp_shocked_flash_01.cef", ship, "");
        playClientEffectObj(target, "clienteffect/space_command/shp_shocked_flash_01.cef", target, "");
        string_id strSpam = new string_id("space/space_interaction", "jstart_recipient_alert");
        String commanderName = getEncodedName(commander);
        prose_package proseTest = prose.getPackage(strSpam, commanderName);
        space_utils.sendSystemMessageShip(target, proseTest, true, false, false, true);
        return true;
    }
    public static boolean prepInSpaceRepair(obj_id commander, obj_id ship, String command_type) throws InterruptedException
    {
        debugServerConsoleMsg(null, "SPACE_PILOT_COMMAND.prepInSpaceRepair  ---- Just Entered");
        obj_id closestStation = space_combat.getClosestSpaceStation(ship);
        if (!isIdValid(closestStation))
        {
            space_utils.sendSystemMessage(commander, SID_SPACEREPAIR_NO_STATION);
            return false;
        }
        if (space_utils.isShipWithInterior(ship))
        {
            string_id strSpam = new string_id("space/space_pilot_command", "spacerepair_no_pob");
            space_utils.sendSystemMessageShip(ship, strSpam, true, false, false, true);
            return false;
        }
        string_id strSpam = new string_id("space/space_interaction", "calling_repair_ship");
        sendSystemMessage(commander, strSpam);
        if (!space_pilot_command.shipissafe(ship, commander))
        {
            strSpam = new string_id("space/space_pilot_command", "inspacerepair_ship_not_safe");
            sendSystemMessage(commander, strSpam);
            strSpam = new string_id("space/space_pilot_command", "inspacerepair_ship_not_safe2");
            sendSystemMessage(commander, strSpam);
            debugServerConsoleMsg(null, "we arent clear, so a repair ship cant come in!!!");
            return false;
        }
        else 
        {
            float currentSpeed = getShipCurrentSpeed(ship);
            if (currentSpeed > 1.0f)
            {
                strSpam = new string_id("space/space_interaction", "cant_be_moving");
                sendSystemMessage(commander, strSpam);
                return false;
            }
            playClientEffectObj(commander, "clienteffect/space_command/sys_comm_rebel.cef", ship, "");
            if (space_pilot_command.spawnrepairship(commander, ship, command_type, closestStation))
            {
                debugServerConsoleMsg(null, "spawnrepairship returned true.");
                utils.setScriptVar(commander, "cmd.inSpaceRepair", 1);
                space_combat.flightDroidVocalize(ship, 1);
                strSpam = new string_id("space/space_interaction", "calling_in_repair_ship");
                sendSystemMessage(commander, strSpam);
                strSpam = new string_id("space/space_pilot_command", "inspacerepair_service_fee");
                sendSystemMessage(commander, strSpam);
            }
            else 
            {
                debugServerConsoleMsg(null, "spawnrepairship returned false.");
            }
        }
        return true;
    }
    public static void confirmRepairs(obj_id commander, obj_id ship, String command_type) throws InterruptedException
    {
        debugServerConsoleMsg(null, "SPACE_PILOT_COMMAND.confirmRepair  ---- Just Entered");
        obj_id repairShip = utils.getObjIdLocalVar(commander, "cmd.repair.shipId");
        obj_id closestStation = space_combat.getClosestSpaceStation(ship);
        if (!isIdValid(closestStation))
        {
            string_id strSpam = new string_id("space/space_pilot_command", "spacerepair_no_station");
            space_utils.sendSystemMessageShip(ship, strSpam, true, false, false, true);
            return;
        }
        if (utils.hasLocalVar(repairShip, "cmd.repair.buggingOut"))
        {
            debugServerConsoleMsg(null, "repair ship leaving. Do NOT tell it to do the repair/reload. Allow it to go");
            return;
        }
        if (!utils.hasLocalVar(commander, "cmd.repair.shipId"))
        {
            debugServerConsoleMsg(null, "repair ship already otw, but not spawned yet");
            return;
        }
        setObjVar(repairShip, "closestStation", closestStation);
        setObjVar(repairShip, "command_type", command_type);
        dictionary outparams = new dictionary();
        outparams.put("pilot", commander);
        outparams.put("ship", ship);
        if (command_type.equals("repair"))
        {
            space_pilot_command.doRepair(commander, ship, repairShip, closestStation);
        }
        else if (command_type.equals("reload"))
        {
            space_pilot_command.doReload(commander, ship, repairShip, closestStation);
        }
        else if (command_type.equals("repairAndReload"))
        {
            space_pilot_command.doRepairAndReload(commander, ship, repairShip, closestStation);
        }
        return;
    }
    public static boolean shipissafe(obj_id ship, obj_id pilot) throws InterruptedException
    {
        debugServerConsoleMsg(null, "SPACE_PILOT_COMMAND.shipissafe  ---- Just Entered");
        obj_id[] myAttackers = ship_ai.unitGetWhoIsTargetingMe(ship);
        if (myAttackers.length != 0)
        {
            debugServerConsoleMsg(null, "SPACE_COMBAT.shipissafe: ----------------  WHOOPS! Looks like there are things targeting me!");
            for (int i = 0; i < myAttackers.length; i++)
            {
                debugServerConsoleMsg(null, "SPACE_COMBAT.shipissafe: ----------------  obj_id: " + myAttackers[i] + " is targeting me! It is called: " + getName(myAttackers[i]));
            }
            return false;
        }
        return true;
    }
    public static obj_id getClosestStation(obj_id commander, obj_id ship) throws InterruptedException
    {
        debugServerConsoleMsg(null, "SPACE_PILOT_COMMAND.getClosestStation  ---- Just Entered");
        obj_id closestStation = space_combat.getClosestSpaceStation(ship);
        if (!isIdValid(closestStation))
        {
            string_id strSpam = new string_id("space/space_pilot_command", "spacerepair_no_station");
            space_utils.sendSystemMessageShip(ship, strSpam, true, false, false, true);
            return null;
        }
        return closestStation;
    }
    public static boolean spawnrepairship(obj_id pilot, obj_id ship, String command_type, obj_id closestStation) throws InterruptedException
    {
        debugServerConsoleMsg(null, "SPACE_PILOT_COMMAND.spawnrepairship  ---- Just Entered");
        obj_id player = pilot;
        string_id strSpam = new string_id("space/space_interaction", "repair_arrive_hyperspace");
        sendSystemMessage(pilot, strSpam);
        transform gloc = getTransform_o2w(ship);
        float dist = rand(600.f, 700.f);
        vector n = ((gloc.getLocalFrameK_p()).normalize()).multiply(dist);
        gloc = gloc.move_p(n);
        gloc = gloc.yaw_l(3.14f);
        vector vi = ((gloc.getLocalFrameI_p()).normalize()).multiply(rand(-150.f, 150.f));
        vector vj = ((gloc.getLocalFrameJ_p()).normalize()).multiply(rand(-150.f, 150.f));
        vector vd = vi.add(vj);
        gloc = gloc.move_p(vd);
        String[] possibleRepairShips = dataTableGetStringColumnNoDefaults(REPAIR_EQUIPMENT_DATATABLE, command_type);
        String repairShipType = possibleRepairShips[(int)(rand(0, possibleRepairShips.length - 1))];
        obj_id newship = space_create.createShipHyperspace(repairShipType, gloc, null);
        if (!isIdValid(newship))
        {
            return false;
        }
        utils.setObjVar(newship, "cmd.repair.commander", pilot);
        utils.setLocalVar(newship, "cmd.repair.station", closestStation);
        utils.setLocalVar(newship, "cmd.repair.command_type", command_type);
        utils.setLocalVar(pilot, "cmd.repair.otw", true);
        utils.setLocalVar(pilot, "cmd.repair.shipId", newship);
        attachScript(newship, "space.command.player_cmd_repair_reload_ship");
        playClientEffectObj(pilot, "clienteffect/space_command/shp_dock_repair_loop.cef", newship, "");
        int repairSquadId = ship_ai.unitGetSquadId(newship);
        transform newLoc = space_utils.getRandomPositionInSphere(getTransform_o2p(ship), 50f, 90f, false);
        if (space_utils.isShipWithInterior(ship))
        {
            debugServerConsoleMsg(null, "SPACE_PILOT_COMMAND.spawnrepairship ***** picking a location to move the freighter to that is a bit further out");
            newLoc = space_utils.getRandomPositionInSphere(getTransform_o2p(ship), 150f, 180f, false);
        }
        ship_ai.squadMoveTo(repairSquadId, newLoc);
        return true;
    }
    public static boolean inSpaceRepairComponentsEstimate(obj_id ship, obj_id commander, obj_id closestStation) throws InterruptedException
    {
        debugServerConsoleMsg(null, "SPACE_PILOT_COMMAND.inSpaceRepairComponentsEstimate  ---- Just Entered");
        obj_id repairShip = utils.getObjIdLocalVar(commander, "cmd.repair.shipId");
        int cost = space_crafting.getStationRepairCost(commander, closestStation, IN_SPACE_REPAIR_STD_REPAIR_PERCENTAGE);
        int bankBalance = getBankBalance(commander);
        playClientEffectObj(commander, "clienteffect/space_command/sys_comm_rebel.cef", ship, "");
        if ((cost > 0) && (cost <= bankBalance))
        {
            prose_package ppCostForRepairs = prose.getPackage(SID_COST_FOR_REPAIRS);
            prose.setDI(ppCostForRepairs, cost);
            space_utils.sendSystemMessageProse(commander, ppCostForRepairs);
            dictionary outparams = new dictionary();
            outparams.put("commander", commander);
            outparams.put("ship", ship);
            messageTo(repairShip, "repairReplyTimeout", outparams, 30.0f, false);
        }
        else 
        {
            prose_package ppRepairCost = prose.getPackage(SID_REPAIR_COST);
            prose.setDI(ppRepairCost, cost);
            space_utils.sendSystemMessageProse(commander, ppRepairCost);
            if (cost <= 0)
            {
                string_id strSpam = new string_id("space/space_pilot_command", "spacerepair_unnecessary");
                space_utils.sendSystemMessageShip(ship, strSpam, true, false, false, true);
            }
            else if (cost > bankBalance)
            {
                string_id strSpam = new string_id("space/space_pilot_command", "spacerepair_not_enough_repair");
                space_utils.sendSystemMessageShip(ship, strSpam, true, false, false, true);
            }
            if (isIdValid(repairShip))
            {
                repairCompleteDepart(repairShip);
            }
            else 
            {
                debugServerConsoleMsg(null, "SPACE_PILOT_COMMAND.inSpaceRepairComponentsEstimate  ---- obj_id localVar of RepairShip appears invalid. No departure function called");
            }
        }
        return true;
    }
    public static boolean inSpaceReloadLaunchersEstimate(obj_id ship, obj_id commander, obj_id closestStation) throws InterruptedException
    {
        debugServerConsoleMsg(null, "SPACE_PILOT_COMMAND.inSpaceReloadLaunchersEstimate  ---- Just Entered");
        obj_id repairShip = utils.getObjIdLocalVar(commander, "cmd.repair.shipId");
        int cost = getStationReloadCost(ship, commander, closestStation);
        int bankBalance = getBankBalance(commander);
        debugServerConsoleMsg(null, "SPACE_PILOT_COMMAND.inSpaceReloadLaunchersEstimate  ---- cost for reload estimated at: " + cost);
        debugServerConsoleMsg(null, "SPACE_PILOT_COMMAND.inSpaceReloadLaunchersEstimate  ---- player bank balance at: " + bankBalance);
        if ((cost > 0) && (cost <= bankBalance))
        {
            debugServerConsoleMsg(null, "SPACE_PILOT_COMMAND.inSpaceRepairReloadComponentsEstimate  ---- SECTION 1");
            string_id strSpam = new string_id("space/space_pilot_command", "spacereload_confirm_instructions");
            prose_package ppReloadCost = prose.getPackage(strSpam);
            prose.setDI(ppReloadCost, cost);
            space_utils.sendSystemMessageProse(ship, ppReloadCost);
            dictionary outparams = new dictionary();
            outparams.put("commander", commander);
            outparams.put("ship", ship);
            messageTo(repairShip, "repairReplyTimeout", outparams, 30.0f, false);
        }
        else 
        {
            debugServerConsoleMsg(null, "SPACE_PILOT_COMMAND.inSpaceRepairReloadComponentsEstimate  ---- SECTION 2");
            prose_package ppRepairCost = prose.getPackage(SID_REPAIR_COST);
            prose.setDI(ppRepairCost, cost);
            space_utils.sendSystemMessageProse(commander, ppRepairCost);
            if (cost <= 0)
            {
                debugServerConsoleMsg(null, "SPACE_PILOT_COMMAND.inSpaceRepairReloadComponentsEstimate  ---- SECTION 3");
                string_id strSpam = new string_id("space/space_pilot_command", "spacerepair_no_reload_needed");
                space_utils.sendSystemMessageShip(ship, strSpam, true, false, false, true);
                dictionary outparams = new dictionary();
                outparams.put("commander", commander);
                outparams.put("ship", ship);
                messageTo(repairShip, "repairReplyTimeout", outparams, 30.0f, false);
            }
            else if (cost > bankBalance)
            {
                debugServerConsoleMsg(null, "SPACE_PILOT_COMMAND.inSpaceRepairReloadComponentsEstimate  ---- SECTION 4");
                string_id strSpam = new string_id("space/space_pilot_command", "spacerepair_not_enough_money_rearm");
                space_utils.sendSystemMessageShip(ship, strSpam, true, false, false, true);
            }
            debugServerConsoleMsg(null, "SPACE_PILOT_COMMAND.inSpaceRepairReloadComponentsEstimate  ---- SECTION 5");
            if (isIdValid(repairShip))
            {
                debugServerConsoleMsg(null, "SPACE_PILOT_COMMAND.inSpaceRepairReloadComponentsEstimate  ---- SECTION 6");
                repairCompleteDepart(repairShip);
            }
            else 
            {
                debugServerConsoleMsg(null, "SPACE_PILOT_COMMAND.inSpaceRepairReloadComponentsEstimate  ---- SECTION 7");
                debugServerConsoleMsg(null, "SPACE_PILOT_COMMAND.inSpaceReloadComponentsEstimate  ---- obj_id localVar of RepairShip appears invalid. No departure function called");
            }
        }
        debugServerConsoleMsg(null, "SPACE_PILOT_COMMAND.inSpaceRepairReloadComponentsEstimate  ---- SECTION 8");
        return true;
    }
    public static boolean inSpaceRepairReloadComponentsEstimate(obj_id ship, obj_id commander, obj_id closestStation) throws InterruptedException
    {
        debugServerConsoleMsg(null, "SPACE_PILOT_COMMAND.inSpaceRepairReloadComponentsEstimate  ---- Just Entered");
        obj_id repairShip = utils.getObjIdLocalVar(commander, "cmd.repair.shipId");
        int costRepair = space_crafting.getStationRepairCost(commander, closestStation, IN_SPACE_REPAIR_STD_REPAIR_PERCENTAGE);
        int costReload = getStationReloadCost(ship, commander, closestStation);
        int costTotal = costRepair + costReload;
        int bankBalance = getBankBalance(commander);
        if ((costTotal > 0) && (costTotal <= bankBalance))
        {
            debugServerConsoleMsg(null, "SPACE_PILOT_COMMAND.inSpaceRepairReloadComponentsEstimate  ---- SECTION 1");
            prose_package ppReloadRepairCost = prose.getPackage(SID_RELOAD_AND_REPAIR_COST);
            prose.setDI(ppReloadRepairCost, costTotal);
            space_utils.sendSystemMessageProse(commander, ppReloadRepairCost);
            dictionary outparams = new dictionary();
            outparams.put("commander", commander);
            outparams.put("ship", ship);
            messageTo(repairShip, "repairReplyTimeout", outparams, 30.0f, false);
        }
        else 
        {
            debugServerConsoleMsg(null, "SPACE_PILOT_COMMAND.inSpaceRepairReloadComponentsEstimate  ---- SECTION 2");
            prose_package ppRepairCost = prose.getPackage(SID_REPAIR_COST);
            prose.setDI(ppRepairCost, costTotal);
            space_utils.sendSystemMessageProse(commander, ppRepairCost);
            if (!(costTotal > 0))
            {
                debugServerConsoleMsg(null, "SPACE_PILOT_COMMAND.inSpaceRepairReloadComponentsEstimate  ---- SECTION 3");
                string_id strSpam = new string_id("space/space_pilot_command", "spacerepair_unnecessary");
                space_utils.sendSystemMessageShip(ship, strSpam, true, false, false, true);
            }
            else if (costTotal > bankBalance)
            {
                debugServerConsoleMsg(null, "SPACE_PILOT_COMMAND.inSpaceRepairReloadComponentsEstimate  ---- SECTION 4");
                string_id strSpam = new string_id("space/space_pilot_command", "spacerepair_not_enough_repair_rearm");
                space_utils.sendSystemMessageShip(ship, strSpam, true, false, false, true);
            }
            debugServerConsoleMsg(null, "SPACE_PILOT_COMMAND.inSpaceRepairReloadComponentsEstimate  ---- SECTION 5");
            if (isIdValid(repairShip))
            {
                debugServerConsoleMsg(null, "SPACE_PILOT_COMMAND.inSpaceRepairReloadComponentsEstimate  ---- SECTION 6");
                repairCompleteDepart(repairShip);
            }
            else 
            {
                debugServerConsoleMsg(null, "SPACE_PILOT_COMMAND.inSpaceRepairReloadComponentsEstimate  ---- SECTION 7");
                debugServerConsoleMsg(null, "SPACE_PILOT_COMMAND.inSpaceRepairAndReloadComponentsEstimate  ---- obj_id localVar of RepairShip appears invalid. No departure function called");
            }
        }
        debugServerConsoleMsg(null, "SPACE_PILOT_COMMAND.inSpaceRepairReloadComponentsEstimate  ---- SECTION 8");
        return true;
    }
    public static int getStationReloadCost(obj_id ship, obj_id commander, obj_id closestStation) throws InterruptedException
    {
        debugServerConsoleMsg(null, "SPACE_PILOT_COMMAND.getStationReloadCost  ---- Just Entered");
        int missileReloadCount = 0;
        int countermeasureReloadCount = 0;
        int[] slots = space_crafting.getShipInstalledSlots(ship);
        for (int i = 0; i < slots.length; i++)
        {
            if (space_crafting.isMissileSlot(ship, slots[i]))
            {
                int currentShipWeaponAmmoMaximum = getShipWeaponAmmoMaximum(ship, slots[i]);
                int currentShipWeaponAmmoCurrent = getShipWeaponAmmoCurrent(ship, slots[i]);
                if (currentShipWeaponAmmoCurrent <= (int)(currentShipWeaponAmmoMaximum / 2))
                {
                    missileReloadCount += (currentShipWeaponAmmoMaximum - currentShipWeaponAmmoCurrent);
                }
            }
            else if (space_crafting.isCounterMeasureSlot(ship, slots[i]))
            {
                int currentShipWeaponAmmoMaximum = getShipWeaponAmmoMaximum(ship, slots[i]);
                int currentShipWeaponAmmoCurrent = getShipWeaponAmmoCurrent(ship, slots[i]);
                if (currentShipWeaponAmmoCurrent <= (int)(currentShipWeaponAmmoMaximum / 2))
                {
                    countermeasureReloadCount += (currentShipWeaponAmmoMaximum - currentShipWeaponAmmoCurrent);
                }
            }
        }
        int cost = countermeasureReloadCount + (ISR_CHAFF_TO_MISSILE_MULTIPLIER * missileReloadCount);
        float costPerPoint = ISR_COST_MULTIPLIER * (getFloatObjVar(closestStation, "fltCostPerDamagePoint"));
        int totalCost = (int)(costPerPoint * cost);
        utils.setLocalVar(ship, "cmd.repair.cost", totalCost);
        return totalCost;
    }
    public static void doRepair(obj_id commander, obj_id ship, obj_id repairShip, obj_id closestStation) throws InterruptedException
    {
        debugServerConsoleMsg(null, "SPACE_PILOT_COMMAND.doRepair  ---- Just Entered");
        ship_ai.unitDock(repairShip, ship, 30.0f);
        dictionary outparams = new dictionary();
        outparams.put("pilot", commander);
        outparams.put("ship", ship);
        messageTo(repairShip, "dockTargetMovementCheck", outparams, 2.0f, false);
        return;
    }
    public static void doReload(obj_id commander, obj_id ship, obj_id repairShip, obj_id closestStation) throws InterruptedException
    {
        debugServerConsoleMsg(null, "SPACE_PILOT_COMMAND.doReload  ---- Just Entered");
        setObjVar(repairShip, "reload", 1);
        ship_ai.unitDock(repairShip, ship, 30.0f);
        dictionary outparams = new dictionary();
        outparams.put("pilot", commander);
        outparams.put("ship", ship);
        messageTo(repairShip, "dockTargetMovementCheck", outparams, 2.0f, false);
        return;
    }
    public static void doRepairAndReload(obj_id commander, obj_id ship, obj_id repairShip, obj_id closestStation) throws InterruptedException
    {
        debugServerConsoleMsg(null, "SPACE_PILOT_COMMAND.doReload  ---- Just Entered");
        ship_ai.unitDock(repairShip, ship, 30.0f);
        dictionary outparams = new dictionary();
        outparams.put("pilot", commander);
        outparams.put("ship", ship);
        messageTo(repairShip, "dockTargetMovementCheck", outparams, 2.0f, false);
        return;
    }
    public static void doStationToShipReload(obj_id commander, obj_id closestStation) throws InterruptedException
    {
        debugServerConsoleMsg(null, "SPACE_PILOT_COMMAND.doStationToShipReload  ---- Just Entered");
        obj_id ship = space_transition.getContainingShip(commander);
        if (!isIdValid(ship))
        {
            return;
        }
        int totalCost = utils.getIntLocalVar(ship, "cmd.repair.cost");
        int bankBalance = getBankBalance(commander);
        if (bankBalance > totalCost)
        {
            dictionary dctParams = new dictionary();
            int test = bankBalance - totalCost;
            if (test < 0)
            {
                return;
            }
            else 
            {
                transferBankCreditsToNamedAccount(commander, "SPACE_STATION_REPAIR", totalCost, "repairSuccess", "repairFail", dctParams);
            }
            utils.moneyOutMetric(commander, "REPAIR_SHIP_RELOAD", totalCost);
            int[] slots = space_crafting.getShipInstalledSlots(ship);
            for (int i = 0; i < slots.length; i++)
            {
                if (space_crafting.isMissileSlot(ship, slots[i]))
                {
                    weaponLauncherSpaceReload(ship, commander, slots[i]);
                }
                else if (space_crafting.isCounterMeasureSlot(ship, slots[i]))
                {
                    countermeasureLauncherSpaceReload(ship, commander, slots[i]);
                }
            }
        }
        else 
        {
            prose_package ppMunitionsCost = prose.getPackage(SID_MUNITIONS_COST);
            prose.setDI(ppMunitionsCost, totalCost);
            prose.setTO(ppMunitionsCost, "" + bankBalance);
            space_utils.sendSystemMessageProse(commander, ppMunitionsCost);
            string_id strSpam = new string_id("space/space_pilot_command", "spacerepair_cannot_pay");
            space_utils.sendSystemMessageShip(ship, strSpam, true, false, false, true);
        }
        return;
    }
    public static boolean weaponLauncherSpaceReload(obj_id ship, obj_id commander, int currentSlot) throws InterruptedException
    {
        debugServerConsoleMsg(null, "SPACE_PILOT_COMMAND.weaponLauncherSpaceReload  ---- Just Entered");
        debugServerConsoleMsg(null, "SPACE_PILOT_COMMAND.weaponLauncherSpaceReload  ---- ship is: " + ship + " commander is: " + commander + " and currentSlot is :" + currentSlot);
        float currentShipWeaponDamageMinimum = getShipWeaponDamageMinimum(ship, currentSlot);
        float currentShipWeaponDamageMaximum = getShipWeaponDamageMaximum(ship, currentSlot);
        float currentShipWeaponRefireRate = getShipWeaponRefireRate(ship, currentSlot);
        float currentShipWeaponEffectivenessShields = getShipWeaponEffectivenessShields(ship, currentSlot);
        if (currentShipWeaponEffectivenessShields > 1.0f)
        {
            currentShipWeaponEffectivenessShields = 1.0f;
        }
        float currentShipWeaponEffectivenessArmor = getShipWeaponEffectivenessArmor(ship, currentSlot);
        if (currentShipWeaponEffectivenessArmor > 1.0f)
        {
            currentShipWeaponEffectivenessArmor = 1.0f;
        }
        int currentShipWeaponAmmoMaximum = getShipWeaponAmmoMaximum(ship, currentSlot);
        debugServerConsoleMsg(null, "SPACE_PILOT_COMMAND.weaponLauncherSpaceReload  ---- getShipWeaponAmmoMaximum is: " + currentShipWeaponAmmoMaximum);
        int currentShipWeaponAmmoCurrent = getShipWeaponAmmoCurrent(ship, currentSlot);
        debugServerConsoleMsg(null, "SPACE_PILOT_COMMAND.weaponLauncherSpaceReload  ---- getShipWeaponAmmoCurrent is: " + currentShipWeaponAmmoCurrent);
        setShipWeaponDamageMaximum(ship, currentSlot, currentShipWeaponDamageMaximum * IN_SPACE_RELOAD_DECAY);
        setShipWeaponDamageMinimum(ship, currentSlot, currentShipWeaponDamageMinimum * IN_SPACE_RELOAD_DECAY);
        setShipWeaponRefireRate(ship, currentSlot, currentShipWeaponRefireRate * IN_SPACE_RELOAD_DECAY);
        setShipWeaponEffectivenessArmor(ship, currentSlot, currentShipWeaponEffectivenessArmor * IN_SPACE_RELOAD_DECAY);
        setShipWeaponEffectivenessShields(ship, currentSlot, currentShipWeaponEffectivenessShields * IN_SPACE_RELOAD_DECAY);
        setShipWeaponAmmoMaximum(ship, currentSlot, currentShipWeaponAmmoMaximum);
        setShipWeaponAmmoCurrent(ship, currentSlot, (int)(currentShipWeaponAmmoMaximum * IN_SPACE_RELOAD_DECAY));
        return true;
    }
    public static boolean countermeasureLauncherSpaceReload(obj_id ship, obj_id commander, int currentSlot) throws InterruptedException
    {
        debugServerConsoleMsg(null, "SPACE_PILOT_COMMAND.countermeasureLauncherSpaceReload  ---- Just Entered");
        float currentShipWeaponDamageMinimum = getShipWeaponDamageMinimum(ship, currentSlot);
        float currentShipWeaponDamageMaximum = getShipWeaponDamageMaximum(ship, currentSlot);
        float currentShipWeaponRefireRate = getShipWeaponRefireRate(ship, currentSlot);
        float currentShipWeaponEnergyPerShot = getShipWeaponEnergyPerShot(ship, currentSlot);
        int currentShipWeaponAmmoMaximum = getShipWeaponAmmoMaximum(ship, currentSlot);
        int currentShipWeaponAmmoCurrent = getShipWeaponAmmoCurrent(ship, currentSlot);
        setShipWeaponDamageMaximum(ship, currentSlot, currentShipWeaponDamageMaximum * IN_SPACE_RELOAD_DECAY);
        setShipWeaponDamageMinimum(ship, currentSlot, currentShipWeaponDamageMinimum * IN_SPACE_RELOAD_DECAY);
        setShipWeaponRefireRate(ship, currentSlot, currentShipWeaponRefireRate * IN_SPACE_RELOAD_DECAY);
        setShipWeaponEnergyPerShot(ship, currentSlot, currentShipWeaponEnergyPerShot * IN_SPACE_RELOAD_DECAY);
        setShipWeaponAmmoMaximum(ship, currentSlot, currentShipWeaponAmmoMaximum);
        setShipWeaponAmmoCurrent(ship, currentSlot, (int)(currentShipWeaponAmmoMaximum * IN_SPACE_RELOAD_DECAY));
        return true;
    }
    public static void repairEvac(obj_id repairship) throws InterruptedException
    {
        debugServerConsoleMsg(null, "SPACE_PILOT_COMMAND.repairEvac - Entered repairEvac function. Ship will set buggingOut localVar, call unitUnDock, and move out.");
        utils.setLocalVar(repairship, "cmd.repair.buggingOut", 1);
        ship_ai.unitUnDock(repairship);
        int repairSquadId = ship_ai.unitGetSquadId(repairship);
        transform loc = space_combat.playerCommandSpawnerLocGetter(repairship, false);
        ship_ai.squadSetAttackOrders(repairSquadId, ship_ai.ATTACK_ORDERS_HOLD_FIRE);
        ship_ai.squadMoveTo(repairSquadId, loc);
    }
    public static void repairCompleteDepart(obj_id repairship) throws InterruptedException
    {
        debugServerConsoleMsg(null, "SPACE_PILOT_COMMAND.repairCompleteDepart - Entered repairCompleteDepart function. Ship will set buggingOut localVar, call unitUnDock, and move out.");
        utils.setLocalVar(repairship, "cmd.repair.buggingOut", 1);
        int repairSquadId = ship_ai.unitGetSquadId(repairship);
        transform loc = space_combat.playerCommandSpawnerLocGetter(repairship, false);
        ship_ai.squadSetAttackOrders(repairSquadId, ship_ai.ATTACK_ORDERS_HOLD_FIRE);
        ship_ai.squadMoveTo(repairSquadId, loc);
    }
    public static void allPurposeShipComponentReset(obj_id ship) throws InterruptedException
    {
        if (isIdValid(ship))
        {
            int[] slots = space_crafting.getShipInstalledSlots(ship);
            for (int i = 0; i < slots.length; i++)
            {
                debugServerConsoleMsg(null, "SPACE_PILOT_COMMAND.allPurposeShipComponentReset  ***  working with slots[i], which is currently: " + slots[i]);
                if (isShipSlotInstalled(ship, slots[i]))
                {
                    space_utils.setComponentDisabled(ship, slots[i], false);
                    space_combat.setEfficiencyModifier(slots[i], ship, 1.0f, 1.0f);
                    space_combat.recalculateEfficiency(slots[i], ship);
                    if (slots[i] == ship_chassis_slot_type.SCST_shield_0)
                    {
                        space_combat.simpleShieldRatioRebalance(ship);
                    }
                }
                else 
                {
                    debugServerConsoleMsg(null, "SPACE_PILOT_COMMAND.allPurposeShipComponentReset  ***  WTH! we just tried to work with an uninstalled slot! It was: " + slots[i]);
                }
            }
        }
        return;
    }
    public static int getDroidProgramSize(String programName, obj_id programObject) throws InterruptedException
    {
        int droidProgramSize = 0;
        if (isIdValid(programObject) && (hasObjVar(programObject, "programSize")))
        {
            droidProgramSize = getIntObjVar(programObject, "programSize");
        }
        else 
        {
            int programSizeLookup = dataTableGetInt(DROID_PROGRAM_SIZE_DATATABLE, programName, 1);
            if (programSizeLookup != -1)
            {
                droidProgramSize = programSizeLookup;
            }
            else 
            {
                debugServerConsoleMsg(null, "SPACE_PILOT_COMMAND.getDroidProgramSize  ***   WARNING - couldn't look up the program in the size datatable, returning a value of 0");
            }
        }
        return droidProgramSize;
    }
    public static int getCurrentlyUsedProgramMemory(obj_id[] programs) throws InterruptedException
    {
        int currentlyUsedProgramMemory = 0;
        for (int i = 0; i < programs.length; i++)
        {
            String programName = "";
            if (hasObjVar(programs[i], "strDroidCommand"))
            {
                programName = getStringObjVar(programs[i], "strDroidCommand");
            }
            if (hasObjVar(programs[i], "programSize"))
            {
                currentlyUsedProgramMemory += getIntObjVar(programs[i], "programSize");
            }
            else 
            {
                if (hasObjVar(programs[i], "strDroidCommand"))
                {
                    programName = getStringObjVar(programs[i], "strDroidCommand");
                    int droidProgramSize = getDroidProgramSize(programName, programs[i]);
                    currentlyUsedProgramMemory += droidProgramSize;
                }
            }
        }
        return currentlyUsedProgramMemory;
    }
    public static boolean hasDuplicateProgram(obj_id[] programs, String newProgramName) throws InterruptedException
    {
        int currentlyUsedProgramMemory = 0;
        for (int i = 0; i < programs.length; i++)
        {
            String programName = "";
            if (hasObjVar(programs[i], "strDroidCommand"))
            {
                programName = getStringObjVar(programs[i], "strDroidCommand");
            }
            if (newProgramName.equals(programName))
            {
                return true;
            }
        }
        return false;
    }
    public static void updateControlDevice(obj_id programObject, obj_id dataPad) throws InterruptedException
    {
        obj_id controlDevice = getContainedBy(dataPad);
        int programSize = 0;
        int usedMemory = 0;
        if (hasObjVar(controlDevice, "usedMemory"))
        {
            usedMemory = getIntObjVar(controlDevice, "usedMemory");
        }
        if (hasObjVar(programObject, "programSize"))
        {
            programSize = getIntObjVar(programObject, "programSize");
        }
        else 
        {
            debugServerConsoleMsg(null, "SPACE_PILOT_COMMAND.updateControlDevice  *** no programSize objvar found on object: " + programObject + " Trying lookup method");
            if (hasObjVar(programObject, "strDroidCommand"))
            {
                String programName = getStringObjVar(programObject, "strDroidCommand");
                programSize = getDroidProgramSize(programName, programObject);
            }
        }
        return;
    }
    public static boolean isStation(obj_id station) throws InterruptedException
    {
        if (!isIdValid(station))
        {
            return false;
        }
        String template = getTemplateName(station);
        if (template == null)
        {
            return false;
        }
        int intIndex = template.indexOf("object/ship/spacestation");
        if (intIndex > -1)
        {
            return true;
        }
        return false;
    }
}
