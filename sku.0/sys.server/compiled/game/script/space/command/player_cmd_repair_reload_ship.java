package script.space.command;

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
import script.library.space_pilot_command;
import script.library.space_transition;

public class player_cmd_repair_reload_ship extends script.base_script
{
    public player_cmd_repair_reload_ship()
    {
    }
    public static final int DROID_VOCALIZE_REACT_CHANCE = 2;
    public static final int SHIP_DAMAGED_SKILLMOD_PENALTY_TIME = 10;
    public static final string_id SID_REPAIRSHIP_STOP_MOVING = new string_id("space/space_pilot_command", "repairship_stop_moving");
    public static final string_id SID_NO_RESPONSE_RECEIVED_ASSIST = new string_id("space/space_pilot_command", "no_response_received_assist");
    public int OnSpaceUnitDocked(obj_id self, obj_id dockTarget) throws InterruptedException
    {
        debugServerConsoleMsg(null, "PLAYER_CMD_REPAIR_RELOAD_SHIP.OnSpaceUnitDocked  ---- Just Entered");
        obj_id objPilot = getPilotId(dockTarget);
        if (!isIdValid(objPilot))
        {
            return SCRIPT_CONTINUE;
        }
        playClientEffectObj(objPilot, "clienteffect/space_command/shp_dock_harddock.cef", dockTarget, "");
        if (getShipHasWings(dockTarget))
        {
            clearCondition(dockTarget, CONDITION_WINGS_OPENED);
        }
        dictionary outparams = new dictionary();
        outparams.put("pilot", objPilot);
        outparams.put("ship", dockTarget);
        messageTo(self, "repairSFX", outparams, 2.0f, false);
        obj_id closestStation = getObjIdObjVar(self, "closestStation");
        String command_type = getStringObjVar(self, "command_type");
        if (command_type.equals("repair"))
        {
            space_crafting.doStationToShipRepairs(objPilot, closestStation, space_pilot_command.IN_SPACE_REPAIR_STD_REPAIR_PERCENTAGE, space_pilot_command.IN_SPACE_REPAIR_DECAY);
        }
        else if (command_type.equals("reload"))
        {
            space_pilot_command.doStationToShipReload(objPilot, closestStation);
        }
        else if (command_type.equals("repairAndReload"))
        {
            space_crafting.doStationToShipRepairs(objPilot, closestStation, space_pilot_command.IN_SPACE_REPAIR_STD_REPAIR_PERCENTAGE, space_pilot_command.IN_SPACE_REPAIR_DECAY);
            space_pilot_command.doStationToShipReload(objPilot, closestStation);
        }
        space_utils.setComponentDisabled(dockTarget, ship_chassis_slot_type.SCST_reactor, true);
        space_utils.setComponentDisabled(dockTarget, ship_chassis_slot_type.SCST_engine, true);
        space_pilot_command.repairCompleteDepart(self);
        return SCRIPT_CONTINUE;
    }
    public int OnSpaceUnitUnDocked(obj_id self, obj_id dockTarget, boolean dockSuccessful) throws InterruptedException
    {
        obj_id objPilot = getPilotId(dockTarget);
        if (!isIdValid(objPilot))
        {
            return SCRIPT_CONTINUE;
        }
        playClientEffectObj(objPilot, "clienteffect/space_command/shp_dock_release.cef", dockTarget, "");
        space_utils.setComponentDisabled(dockTarget, ship_chassis_slot_type.SCST_reactor, false);
        space_combat.recalculateEfficiency(ship_chassis_slot_type.SCST_reactor, dockTarget);
        space_utils.setComponentDisabled(dockTarget, ship_chassis_slot_type.SCST_engine, false);
        space_combat.recalculateEfficiency(ship_chassis_slot_type.SCST_engine, dockTarget);
        playClientEffectObj(objPilot, "clienteffect/space_command/sys_comm_rebel.cef", dockTarget, "");
        return SCRIPT_CONTINUE;
    }
    public int OnSpaceUnitStartUnDock(obj_id self, obj_id dockTarget) throws InterruptedException
    {
        obj_id objPilot = getPilotId(dockTarget);
        if (!isIdValid(objPilot))
        {
            return SCRIPT_CONTINUE;
        }
        playClientEffectObj(objPilot, "clienteffect/space_command/shp_dock_release.cef", dockTarget, "");
        space_utils.setComponentDisabled(dockTarget, ship_chassis_slot_type.SCST_reactor, false);
        space_combat.recalculateEfficiency(ship_chassis_slot_type.SCST_reactor, dockTarget);
        space_utils.setComponentDisabled(dockTarget, ship_chassis_slot_type.SCST_engine, false);
        space_combat.recalculateEfficiency(ship_chassis_slot_type.SCST_engine, dockTarget);
        playClientEffectObj(objPilot, "clienteffect/space_command/sys_comm_rebel.cef", dockTarget, "");
        return SCRIPT_CONTINUE;
    }
    public int OnShipWasHit(obj_id self, obj_id objAttacker, int intWeaponIndex, boolean isMissile, int missileType, int intTargetedComponent, boolean fromPlayerAutoTurret, float hitLocationX_o, float hitLocationY_o, float hitLocationZ_o) throws InterruptedException
    {
        if (!utils.hasLocalVar(self, "cmd.repair"))
        {
            debugServerConsoleMsg(null, "PLAYER_CMD_REPAIR_RELOAD_SHIP.OnShipWasHit  ---- No command local var on repair/reload ship.");
            debugServerConsoleMsg(null, "PLAYER_CMD_REPAIR_RELOAD_SHIP.OnShipWasHit  ---- Probable screwed-up instance. Bugging out!");
        }
        if (utils.hasLocalVar(self, "cmd.repair.buggingOut"))
        {
            debugServerConsoleMsg(null, "PLAYER_CMD_REPAIR_RELOAD_SHIP.OnShipWasHit  ---- I'm hit again!. Already bugging out!");
            return SCRIPT_CONTINUE;
        }
        if (hasObjVar(self, "cmd.repair.commander"))
        {
            obj_id commander = getObjIdObjVar(self, "cmd.repair.commander");
            if (isIdValid(commander))
            {
                string_id strSpam = new string_id("space/space_pilot_command", "spacerepair_attack");
                sendSystemMessage(commander, strSpam);
            }
        }
        space_pilot_command.repairEvac(self);
        return SCRIPT_CONTINUE;
    }
    public int OnSpaceUnitMoveToComplete(obj_id self) throws InterruptedException
    {
        obj_id commander = getObjIdObjVar(self, "cmd.repair.commander");
        obj_id ship = space_transition.getContainingShip(commander);
        if (!isIdValid(ship))
        {
            debugServerConsoleMsg(null, "PLAYER_CMD_REPAIR_RELOAD_SHIP.OnSpaceUnitMoveToComplete - ship obj_id isn't valid. BALLS! YEEargh!!!");
            return SCRIPT_CONTINUE;
        }
        if (!isIdValid(commander))
        {
            debugServerConsoleMsg(null, "PLAYER_CMD_REPAIR_RELOAD_SHIP.OnSpaceUnitMoveToComplete - commander obj_id isn't valid. BALLS! YEEargh!!!");
            return SCRIPT_CONTINUE;
        }
        if (utils.hasLocalVar(self, "cmd.repair.buggingOut"))
        {
            utils.removeLocalVar(commander, "cmd.repair.otw");
            debugServerConsoleMsg(null, "PLAYER_CMD_REPAIR_RELOAD_SHIP.OnShipWasHit  ---- Bugging out! Arrived at destination! Destroying myself w/ hyperspace effect!");
            space_combat.destroyObjectHyperspace(self);
            return SCRIPT_CONTINUE;
        }
        debugServerConsoleMsg(null, "PLAYER_CMD_REPAIR_RELOAD_SHIP.OnSpaceUnitMoveToComplete - repair/resupply ship has reached rendezvous and will now supply estimate");
        if (!utils.hasLocalVar(self, "cmd.repair.command_type") || !hasObjVar(self, "cmd.repair.commander") || !utils.hasLocalVar(self, "cmd.repair.station"))
        {
            debugServerConsoleMsg(null, "PLAYER_CMD_REPAIR_RELOAD_SHIP.OnSpaceUnitMoveToComplete - repair/resupply ship has reached rendezvous but doesn't know what action to do. Evac!");
            space_pilot_command.repairEvac(self);
        }
        obj_id closestStation = utils.getObjIdLocalVar(self, "cmd.repair.station");
        String command_type = utils.getStringLocalVar(self, "cmd.repair.command_type");
        float currentSpeed = getShipCurrentSpeed(ship);
        if (currentSpeed > 1.0f)
        {
            sendSystemMessage(self, SID_REPAIRSHIP_STOP_MOVING);
            return SCRIPT_CONTINUE;
        }
        if (command_type.equals("repair"))
        {
            space_pilot_command.inSpaceRepairComponentsEstimate(ship, commander, closestStation);
        }
        else if (command_type.equals("reload"))
        {
            space_pilot_command.inSpaceReloadLaunchersEstimate(ship, commander, closestStation);
        }
        else if (command_type.equals("repairAndReload"))
        {
            space_pilot_command.inSpaceRepairReloadComponentsEstimate(ship, commander, closestStation);
        }
        return SCRIPT_CONTINUE;
    }
    public int repairReplyTimeout(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id commander = getObjIdObjVar(self, "cmd.repair.commander");
        if (!isIdValid(commander))
        {
            debugServerConsoleMsg(null, "PLAYER_CMD_REPAIR_RELOAD_SHIP.repairReplyTimeout - commander obj_id isn't valid. BALLS! YEEargh!!!");
            return SCRIPT_CONTINUE;
        }
        sendSystemMessage(self, SID_NO_RESPONSE_RECEIVED_ASSIST);
        space_pilot_command.repairCompleteDepart(self);
        return SCRIPT_CONTINUE;
    }
    public int dockTargetMovementCheck(obj_id self, dictionary params) throws InterruptedException
    {
        if (utils.hasLocalVar(self, "cmd.repair.buggingOut"))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id pilot = params.getObjId("pilot");
        obj_id ship = params.getObjId("ship");
        if (!isIdValid(pilot) || !isIdValid(ship))
        {
            debugServerConsoleMsg(null, "PLAYER_CMD_REPAIR_RELOAD_SHIP.dockTargetMovementCheck - pilot or ship obj_id aren't valid. BALLS! YEEargh!!!");
            return SCRIPT_CONTINUE;
        }
        float currentSpeed = getShipCurrentSpeed(ship);
        if (currentSpeed > 1.0f)
        {
            string_id strSpam = new string_id("space/space_interaction", "cant_be_moving");
            sendSystemMessage(pilot, strSpam);
            space_pilot_command.repairEvac(self);
        }
        else 
        {
            dictionary outparams = new dictionary();
            outparams.put("pilot", pilot);
            outparams.put("ship", ship);
            messageTo(self, "dockTargetMovementCheck", outparams, 2.0f, false);
        }
        return SCRIPT_CONTINUE;
    }
    public int repairSFX(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id pilot = params.getObjId("pilot");
        obj_id ship = params.getObjId("ship");
        if (!isIdValid(pilot) || !isIdValid(ship))
        {
            debugServerConsoleMsg(null, "PLAYER_CMD_REPAIR_RELOAD_SHIP.repairSFX - pilot or ship obj_id aren't valid. BALLS! YEEargh!!!");
            return SCRIPT_CONTINUE;
        }
        playClientEffectObj(pilot, "clienteffect/space_command/shp_dock_repair_group.cef", ship, "");
        if (hasObjVar(self, "reload"))
        {
            playClientEffectObj(pilot, "clienteffect/space_command/shp_dock_release.cef", ship, "");
        }
        return SCRIPT_CONTINUE;
    }
}
