package script.space.combat;

import script.*;
import script.library.*;

import java.util.Iterator;
import java.util.Vector;

public class combat_ship_player extends script.base_script
{
    public combat_ship_player()
    {
    }
    public static final int CMD_REACTOR_PUMP_DELAY = 120;
    public static final int CMD_IFF_SCRAMBLE_DELAY = 180;
    public static final int CMD_REPAIR_SHIP_DELAY = 240;
    public static final int CMD_EMERGENCY_WEAPONS_DELAY = 240;
    public static final int CMD_EMERGENCY_SHIELDS_DELAY = 240;
    public static final int CMD_EMERGENCY_THRUST_DELAY = 240;
    public static final int CMD_VAMPIRIC_REPAIR_DELAY = 240;
    public static final int CMD_VAMPIRIC_REPAIR_OTHER_DELAY = 240;
    public static final int CMD_LIGHT_STRIKE_DELAY = 480;
    public static final int CMD_PIRATE_LURE_DELAY = 480;
    public static final int CMD_ENERGY_PULSE_ONE_DELAY = 180;
    public static final int CMD_ENERGY_PULSE_TWO_DELAY = 240;
    public static final int CMD_ENERGY_PULSE_THREE_DELAY = 360;
    public static final int CMD_NEBULA_BLAST_DELAY = 480;
    public static final int CMD_JUMP_START_ONE_DELAY = 30;
    public static final int CMD_JUMP_START_TWO_DELAY = 60;
    public static final int CMD_JUMP_START_THREE_DELAY = 90;
    public static final int CMD_EMERGENCY_POWER_DAMAGE = 200;
    public static final float HYPERSPACE_ANIMATION_TIME = 5;
    public static final int HYPERSPACE_DELAY = 40;
    public static final int NAVIGATOR_HYPERSPACE_DELAY = 20;
    public static final String POB_SHIP_PILOT_SLOT_NAME = "ship_pilot_pob";
    public static final String POB_SHIP_OPERATIONS_SLOT_NAME = "ship_operations_pob";
    public static final string_id SID_SHIP_DOESNT_HAVE_WINGS = new string_id("space/space_interaction", "ship_doesnt_have_wings");
    public static final string_id SID_NOT_PILOTING_A_SHIP = new string_id("space/space_interaction", "not_piloting_a_ship");
    public int OnAttach(obj_id self) throws InterruptedException
    {
        if (!hasObjVar(self, "jtlNewbie"))
        {
            messageTo(self, "handleScriptAttachment", null, 60, false);
        }
        return SCRIPT_CONTINUE;
    }
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        if (!hasObjVar(self, "jtlNewbie"))
        {
            messageTo(self, "handleScriptAttachment", null, 60, false);
        }
        return SCRIPT_CONTINUE;
    }
    public int handleScriptAttachment(obj_id self, dictionary params) throws InterruptedException
    {
        newPlayerCheck(self);
        return SCRIPT_CONTINUE;
    }
    public void newPlayerCheck(obj_id self) throws InterruptedException
    {
        if (!hasObjVar(self, "jtlNewbie") && features.isSpaceEdition(self))
        {
            if (!hasScript(self, "theme_park.newbie_tutorial.jtl_newbie"))
            {
                attachScript(self, "theme_park.newbie_tutorial.jtl_newbie");
            }
        }
    }
    public void createSwoop(obj_id self) throws InterruptedException
    {
        obj_id datapad = utils.getPlayerDatapad(self);
        String controlTemplate = "object/intangible/vehicle/speederbike_swoop_pcd.iff";
        obj_id petControlDevice = createObject(controlTemplate, datapad, "");
        setObjVar(petControlDevice, "pet.crafted", true);
        setObjVar(petControlDevice, "vehicle_attribs.object_ref", "swoop");
        attachScript(petControlDevice, "systems.vehicle_system.vehicle_control_device");
        setObjVar(petControlDevice, "ai.pet.trainedMount", 1);
        setName(petControlDevice, "(swoop)");
    }
    public int cmdPilotShip(obj_id self, obj_id target, String params, float defaultTime) throws InterruptedException
    {
        obj_id ship = space_transition.getContainingShip(self);
        if (isIdValid(ship))
        {
            obj_id pilotSlotObject = space_transition.findPilotSlotObjectForShip(self, ship);
            if (isIdValid(pilotSlotObject) && (isGod(self) || getContainedBy(pilotSlotObject) == getContainedBy(self)))
            {
                if (!hasCertificationsForItem(self, ship))
                {
                    if ((isGod(self)) || (utils.checkConfigFlag("ScriptFlags", "e3Demo")))
                    {
                        if (isGod(self))
                        {
                            sendSystemMessageTestingOnly(self, "** Passing certification test due to god mode. **");
                        }
                    }
                    else 
                    {
                        sendSystemMessage(self, new string_id("space/space_interaction", "no_ship_certification"));
                        return SCRIPT_CONTINUE;
                    }
                }
                boolean result = pilotShip(self, pilotSlotObject);
                if ((isGod(self)) || (utils.checkConfigFlag("ScriptFlags", "e3Demo")))
                {
                    if ((result) && (isGod(self)))
                    {
                        sendSystemMessageTestingOnly(self, "Now piloting ship [" + ship + "], pilot slot object [" + pilotSlotObject + "]");
                    }
                    else 
                    {
                        sendSystemMessageTestingOnly(self, "pilotShip failed for ship [" + ship + "], pilot slot object [" + pilotSlotObject + "]");
                    }
                }
            }
        }
        else if ((isGod(self)) || (utils.checkConfigFlag("ScriptFlags", "e3Demo")))
        {
            obj_id[] shipControlDevices = space_transition.findShipControlDevicesForPlayer(self);
            if (shipControlDevices != null && shipControlDevices.length > 0)
            {
                ship = space_transition.getShipFromShipControlDevice(shipControlDevices[0]);
                if (isIdValid(ship))
                {
                    setObjVar(self, "space.launch.ship", ship);
                    space_transition.handlePotentialSceneChange(self);
                }
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int cmdLeaveStation(obj_id self, obj_id target, String params, float defaultTime) throws InterruptedException
    {
        obj_id container = getContainedBy(self);
        if (isIdValid(container))
        {
            if (!isGod(self) && !isIdValid(getContainedBy(container)))
            {
                return SCRIPT_CONTINUE;
            }
            if (utils.getBooleanScriptVar(self, "npe.falconEventStarted") && !utils.getBooleanScriptVar(self, "npe.finishedTurret"))
            {
                return SCRIPT_CONTINUE;
            }
            if (utils.getBooleanScriptVar(self, "npe.falconEventStarted") && utils.getBooleanScriptVar(self, "npe.finishedTurret") && !utils.hasScriptVar(self, "npe.hasExitedTurret"))
            {
                utils.setScriptVar(self, "npe.hasExitedTurret", true);
                obj_id building = getTopMostContainer(self);
                messageTo(building, "continueMainTable", null, 0, false);
            }
            if (getObjectInSlot(container, POB_SHIP_PILOT_SLOT_NAME) == self)
            {
                unpilotShip(self);
            }
            else if (getObjectInSlot(container, POB_SHIP_OPERATIONS_SLOT_NAME) == self)
            {
                space_transition.revokeDroidCommands(self);
                vector pos = ((getTransform_o2p(container)).move_l(new vector(0.f, 0.f, -1.f))).getPosition_p();
                location dest = new location(pos.x, pos.y, pos.z, getCurrentSceneName(), getLocation(container).cell);
                setLocation(self, dest);
            }
            else 
            {
                boolean inGunnerSlot = false;
                for (int i = 0; i < space_utils.POB_SHIP_GUNNER_SLOT_NAMES.length; ++i)
                {
                    if (getObjectInSlot(container, space_utils.POB_SHIP_GUNNER_SLOT_NAMES[i]) == self)
                    {
                        inGunnerSlot = true;
                        break;
                    }
                }
                if (inGunnerSlot)
                {
                    vector pos = ((getTransform_o2p(container)).move_l(vector.unitZ)).getPosition_p();
                    location dest = new location(pos.x, pos.y, pos.z, getCurrentSceneName(), getLocation(container).cell);
                    setLocation(self, dest);
                }
                else if (isGod(self))
                {
                    obj_id ship = space_transition.getContainingShip(self);
                    if (isIdValid(ship))
                    {
                        if (getOwner(ship) == self)
                        {
                            space_transition.handleLogout(self);
                        }
                        else 
                        {
                            setLocation(self, getLocation(ship));
                        }
                    }
                }
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int cmdOpenWings(obj_id self, obj_id target, String params, float defaultTime) throws InterruptedException
    {
        obj_id shipId = space_transition.getContainingShip(self);
        if (isIdValid(shipId))
        {
            if (getShipHasWings(shipId))
            {
                setCondition(shipId, CONDITION_WINGS_OPENED);
            }
            else 
            {
                sendSystemMessage(self, SID_SHIP_DOESNT_HAVE_WINGS);
            }
        }
        else 
        {
            sendSystemMessage(self, SID_NOT_PILOTING_A_SHIP);
        }
        return SCRIPT_CONTINUE;
    }
    public int cmdCloseWings(obj_id self, obj_id target, String params, float defaultTime) throws InterruptedException
    {
        obj_id shipId = space_transition.getContainingShip(self);
        if (isIdValid(shipId))
        {
            if (getShipHasWings(shipId))
            {
                clearCondition(shipId, CONDITION_WINGS_OPENED);
            }
            else 
            {
                sendSystemMessage(self, SID_SHIP_DOESNT_HAVE_WINGS);
            }
        }
        else 
        {
            sendSystemMessage(self, SID_NOT_PILOTING_A_SHIP);
        }
        return SCRIPT_CONTINUE;
    }
    public int droid(obj_id self, obj_id target, String params, float defaultTime) throws InterruptedException
    {
        if (params == null)
        {
            return SCRIPT_CONTINUE;
        }
        if (params.equals(""))
        {
            return SCRIPT_CONTINUE;
        }
        space_combat.performDroidCommands(self, params);
        return SCRIPT_CONTINUE;
    }
    public boolean canCreateShip(obj_id self) throws InterruptedException
    {
        if (isGod(self))
        {
            obj_id containingShip = space_transition.getContainingShip(self);
            if (!isIdValid(containingShip) || getOwner(containingShip) != self)
            {
                return true;
            }
        }
        return false;
    }
    public int board(obj_id self, obj_id target, String params, float defaultTime) throws InterruptedException
    {
        return SCRIPT_CONTINUE;
    }
    public int evacuate(obj_id self, obj_id target, String params, float defaultTime) throws InterruptedException
    {
        return SCRIPT_CONTINUE;
    }
    public int OnSpeaking(obj_id self, String strText) throws InterruptedException
    {
        if (!isGod(self))
        {
            return SCRIPT_CONTINUE;
        }
        String[] strCommands = split(strText, ' ');
        String strCommand = strCommands[0];
        String command = new String(strText);
        if (command.equalsIgnoreCase("nextCommand"))
        {
            obj_id objShip = space_transition.getContainingShip(self);
            if (utils.hasScriptVar(self, "cmd.commandTimeStamp"))
            {
                utils.removeScriptVar(self, "cmd.commandTimeStamp");
                sendSystemMessageTestingOnly(self, "Yoinking the cmd.commandTimeStamp SctiptVar off of the pilot. ");
            }
            if (utils.hasLocalVar(self, "cmd.iffScramble"))
            {
                utils.removeLocalVar(self, "cmd.iffScramble");
                sendSystemMessageTestingOnly(self, "Yoinking the cmd.iffScramble LocalVar off of the pilot. ");
            }
            obj_id ship = space_transition.getContainingShip(self);
            if (isIdValid(ship))
            {
                if (utils.hasLocalVar(ship, "cmd.pirate.goRaid"))
                {
                    utils.removeLocalVar(ship, "cmd.pirate.goRaid");
                    sendSystemMessageTestingOnly(self, "Yoinking the cmd.pirate.goRaid LocalVar off of the pilot. ");
                }
            }
        }
        if (strText.indexOf("create pcd ") > -1)
        {
            if (canCreateShip(self))
            {
                String ship = strText.substring(11);
                space_utils.createShipControlDevice(self, ship, true);
            }
        }
        if (command.equalsIgnoreCase("create pcd"))
        {
            if (canCreateShip(self))
            {
                space_utils.destroyShipControlDevices(self, true);
                space_utils.createShipControlDevice(self, "xwing", true);
            }
        }
        if (command.equalsIgnoreCase("destroy pcd"))
        {
            if (canCreateShip(self))
            {
                space_utils.destroyShipControlDevices(self, true);
            }
        }
        if (strText.startsWith("loadClientSetup"))
        {
            if (load_test.loadClientSupportEnabled())
            {
                load_test.handleLoadClientSetup(self, strText);
            }
        }
        if (command.equalsIgnoreCase("whoAmI"))
        {
            sendSystemMessageTestingOnly(self, "You are " + self);
        }
        if (command.equalsIgnoreCase("authenticateShip"))
        {
            sendSystemMessageTestingOnly(self, "Entered AUTHENTICATESHIP portion of onSpeaking");
            obj_id[] shipControlDevices = space_transition.findShipControlDevicesForPlayer(self);
            if (shipControlDevices != null && shipControlDevices.length > 0)
            {
                for (int i = 0; i < shipControlDevices.length; ++i)
                {
                    obj_id ship = space_transition.getShipFromShipControlDevice(shipControlDevices[i]);
                    if (isIdValid(ship))
                    {
                        if (!hasCertificationsForItem(self, ship))
                        {
                            sendSystemMessageTestingOnly(self, "Warning: You do NOT have the appropriate certification for ship " + ship + "! BAH.");
                        }
                        else 
                        {
                            sendSystemMessageTestingOnly(self, "Warning: You DO have the appropriate certification for ship " + ship + ". YEAH!");
                        }
                    }
                    else 
                    {
                        sendSystemMessageTestingOnly(self, "Error: Ship control device " + shipControlDevices[i] + " has no ship, so cannot check certifications.");
                    }
                }
            }
            else 
            {
                sendSystemMessageTestingOnly(self, "You do not have any ships, so cannot check certifications.");
            }
        }
        if (command.equalsIgnoreCase("repairReset"))
        {
            if (utils.hasLocalVar(self, "cmd.repair.otw"))
            {
                utils.removeLocalVar(self, "cmd.repair.otw");
                utils.removeLocalVar(self, "cmd.repair.shipId");
                sendSystemMessageTestingOnly(self, "Yoinking the cmd.repair AND cmd.repair.shipId LocalVar off of the pilot. ");
                if (utils.hasScriptVar(self, "cmd.commandTimeStamp"))
                {
                    utils.removeScriptVar(self, "cmd.commandTimeStamp");
                    sendSystemMessageTestingOnly(self, "Yoinking the cmd.commandTimeStamp SctiptVar off of the pilot. ");
                }
            }
        }
        if (command.equalsIgnoreCase("resetTimer"))
        {
            obj_id objShip = space_transition.getContainingShip(self);
            utils.removeLocalVar(objShip, "intDroidTimestamp");
            sendSystemMessageTestingOnly(self, "Droid timer reset.");
            return SCRIPT_CONTINUE;
        }
        if (command.equalsIgnoreCase("disableDroid"))
        {
            obj_id objShip = space_transition.getContainingShip(self);
            space_utils.setComponentDisabled(objShip, ship_chassis_slot_type.SCST_droid_interface, true);
            sendSystemMessageTestingOnly(self, "Droid inteface disabled: " + isShipComponentDisabled(objShip, ship_chassis_slot_type.SCST_droid_interface));
            return SCRIPT_CONTINUE;
        }
        if (command.equalsIgnoreCase("enableDroid"))
        {
            obj_id objShip = space_transition.getContainingShip(self);
            space_utils.setComponentDisabled(objShip, ship_chassis_slot_type.SCST_droid_interface, false);
            sendSystemMessageTestingOnly(self, "Droid inteface enabled.");
            return SCRIPT_CONTINUE;
        }
        if (command.equalsIgnoreCase("scramMe"))
        {
            obj_id objShip = space_transition.getContainingShip(self);
            space_combat.scramReactor(self, objShip);
        }
        if (command.equalsIgnoreCase("checkTargetShield"))
        {
            obj_id target = getLookAtTarget(self);
            float recipientFrontShieldCurrent = getShipShieldHitpointsFrontCurrent(target);
            float recipientBackShieldCurrent = getShipShieldHitpointsBackCurrent(target);
            sendSystemMessageTestingOnly(self, "CURRENT front shield value on target was: " + recipientFrontShieldCurrent);
            sendSystemMessageTestingOnly(self, "CURRENT rear shield value on target was: " + recipientBackShieldCurrent);
        }
        if (command.equalsIgnoreCase("drainTargetShield"))
        {
            obj_id target = getLookAtTarget(self);
            float recipientFrontShieldCurrent = getShipShieldHitpointsFrontCurrent(target);
            float recipientBackShieldCurrent = getShipShieldHitpointsBackCurrent(target);
            setShipShieldHitpointsFrontCurrent(target, 5.0f);
            setShipShieldHitpointsBackCurrent(target, 5.0f);
            float recipientFrontShieldCurrentNew = getShipShieldHitpointsFrontCurrent(target);
            float recipientBackShieldCurrentNew = getShipShieldHitpointsBackCurrent(target);
            float capacitorNew = getShipCapacitorEnergyCurrent(target);
            sendSystemMessageTestingOnly(self, "Starting front shield value on target was: " + recipientFrontShieldCurrent + " and ending value was: " + recipientFrontShieldCurrentNew);
            sendSystemMessageTestingOnly(self, "Starting rear shield value on target was: " + recipientBackShieldCurrent + " and ending value was: " + recipientBackShieldCurrentNew);
        }
        if (command.equalsIgnoreCase("spawnsquad"))
        {
            obj_id ship = space_transition.getContainingShip(self);
            transform loc = space_combat.playerCommandSpawnerLocGetter(ship, true);
            String strikePackageType = "squad_test";
            Vector baddysquad = space_create.createSquad(null, strikePackageType, loc, 20.0f, null);
        }
        if (command.equalsIgnoreCase("bomberEvac"))
        {
            int bomberSquadId = 0;
            if (utils.hasLocalVar(self, "cmd.bmb.bmbrSquadId"))
            {
                bomberSquadId = utils.getIntLocalVar(self, "cmd.bmb.bmbrSquadId");
            }
            else 
            {
                sendSystemMessageTestingOnly(self, "NO squad ID LOCALVAR ON YOUR PERSON ");
                return SCRIPT_CONTINUE;
            }
            space_combat.strikePackageEvac(self, bomberSquadId, -1);
        }
        if (command.equalsIgnoreCase("damageEngine"))
        {
            obj_id objShip = space_transition.getContainingShip(self);
            float fltCurrentHitPoints = getShipComponentHitpointsCurrent(objShip, ship_chassis_slot_type.SCST_engine);
            sendSystemMessageTestingOnly(self, "changing hp from " + fltCurrentHitPoints + " to " + fltCurrentHitPoints / 2);
            fltCurrentHitPoints = fltCurrentHitPoints / 2;
            setShipComponentHitpointsCurrent(objShip, ship_chassis_slot_type.SCST_engine, fltCurrentHitPoints);
            space_combat.recalculateEfficiency(ship_chassis_slot_type.SCST_engine, objShip);
        }
        if (command.equalsIgnoreCase("sparkOn"))
        {
            obj_id ship = space_transition.getContainingShip(self);
            if (!isIdValid(ship))
            {
                return SCRIPT_CONTINUE;
            }
            playClientEffectObj(self, "clienteffect/space_scram_spark.cef", ship, "astromech");
            playClientEffectObj(self, "clienteffect/space_scram_spark.cef", ship, "wing1");
            playClientEffectObj(self, "clienteffect/space_scram_spark.cef", ship, "torpedo1");
            playClientEffectObj(self, "clienteffect/space_scram_spark.cef", ship, "torpedo2");
            sendSystemMessageTestingOnly(self, "spark ON!!!");
        }
        if (command.equalsIgnoreCase("toggleInvincibility") || command.equalsIgnoreCase("invulnerable"))
        {
            if (!isGod(self))
            {
                return SCRIPT_CONTINUE;
            }
            obj_id objShip = space_transition.getContainingShip(self);
            if (isIdValid(objShip))
            {
                if (hasObjVar(objShip, "intInvincible"))
                {
                    removeObjVar(objShip, "intInvincible");
                    sendSystemMessageTestingOnly(self, "You're ship is no longer invincible");
                }
                else 
                {
                    setObjVar(objShip, "intInvincible", 1);
                    sendSystemMessageTestingOnly(self, "You're ship is now invincible");
                }
            }
        }
        if (command.equalsIgnoreCase("missileTest"))
        {
            debugSpeakMsg(self, "got launch missile command");
            if (!isIdValid(getLookAtTarget(self)))
            {
                return SCRIPT_CONTINUE;
            }
            obj_id ship = space_transition.getContainingShip(self);
            if (!isIdValid(ship))
            {
                ship = self;
            }
            if (launchMissile(self, ship, getLookAtTarget(self), 0, -1))
            {
                debugSpeakMsg(self, "launchmissile returned true.");
            }
            else 
            {
                debugSpeakMsg(self, "launchMissile returned false.");
            }
            return SCRIPT_CONTINUE;
        }
        if (command.equalsIgnoreCase("combatDebug"))
        {
            if (hasObjVar(self, "intCombatDebug"))
            {
                removeObjVar(self, "intCombatDebug");
                sendSystemMessageTestingOnly(self, "Debug Spam Disabled!");
            }
            else 
            {
                setObjVar(self, "intCombatDebug", 1);
                sendSystemMessageTestingOnly(self, "Debug Spam Enabled!");
            }
            return SCRIPT_CONTINUE;
        }
        return SCRIPT_CONTINUE;
    }
    public int abortHyperspace(obj_id self, obj_id target, String params, float defaultTime) throws InterruptedException
    {
        obj_id objShip = space_transition.getContainingShip(self);
        if (!utils.hasScriptVar(objShip, "intHyperspacing"))
        {
            string_id strSpam = new string_id("space/space_interaction", "not_hyperspacing");
            sendSystemMessage(self, strSpam);
            return SCRIPT_CONTINUE;
        }
        utils.setScriptVar(objShip, "intHyperspaceDelayStamp", getGameTime());
        utils.removeScriptVar(objShip, "intHyperspacing");
        string_id strSpam = new string_id("space/space_interaction", "hyperspace_aborted");
        space_utils.sendSystemMessageShip(objShip, strSpam, true, false, true, true);
        strSpam = new string_id("space/space_interaction", "hyperdrive_recharged");
        space_utils.sendDelayedSystemMessage(self, strSpam, HYPERSPACE_DELAY);
        hyperspaceRestoreShipOnClientFromAbortedHyperspace(self);
        return SCRIPT_CONTINUE;
    }
    public int hyperspaceToHyperspacePoint(obj_id self, obj_id target, String params, float defaultTime) throws InterruptedException
    {
        if (space_utils.isHyperspaceBlocked(self))
        {
            return SCRIPT_CONTINUE;
        }
        if (hasObjVar(self, "npe"))
        {
            return SCRIPT_CONTINUE;
        }
        int intDelay = HYPERSPACE_DELAY;
        if (hasCommand(self, "space_navigator"))
        {
            intDelay = NAVIGATOR_HYPERSPACE_DELAY;
        }
        if (isGod(self))
        {
            intDelay = 0;
        }
        obj_id objShip = space_transition.getContainingShip(self);
        if (!space_utils.isShipFlyable(objShip))
        {
            string_id strSpam = new string_id("space/space_interaction", "no_hyperspace_ship_damaged");
            sendSystemMessage(self, strSpam);
            return SCRIPT_CONTINUE;
        }
        String strChassisName = getShipChassisType(objShip);
        int intIndex = strChassisName.indexOf("_basic");
        if (intIndex > -1)
        {
            string_id strSpam = new string_id("space/space_interaction", "no_hyperdrive");
            sendSystemMessage(self, strSpam);
            return SCRIPT_CONTINUE;
        }
        String[] strParams = split(params, ' ');
        LOG("space", "strParams length is " + strParams.length);
        if ((params == null) || (params.equals("")))
        {
            return SCRIPT_CONTINUE;
        }
        if (strParams.length < 1)
        {
            return SCRIPT_CONTINUE;
        }
        LOG("space", "params is " + params);
        String hyperspacePoint = strParams[0];
        String sceneName = getSceneForHyperspacePoint(hyperspacePoint);
        if(sceneName == null || sceneName.equals("")){
            LOG("space","WARNING: Unable to get hyperspace scene name from hyperspacePoint (" + hyperspacePoint + ").  Self (" + self + ":" + getName(self) + "), Target(" + target + ":" + getName(target) + ").");
        }
        if (!isGod(self))
        {
            if (sceneName != null && sceneName.equals("space_kashyyyk"))
            {
                if (!features.hasEpisode3Expansion(self))
                {
                    string_id strSpam = new string_id("space/space_interaction", "no_ep3");
                    sendSystemMessage(self, strSpam);
                    return SCRIPT_CONTINUE;
                }
                Vector passengers = space_transition.getContainedPlayers(objShip);
                for (Iterator it = passengers.iterator(); it.hasNext(); )
                {
                    obj_id passenger = (obj_id)it.next();
                    if (!features.hasEpisode3Expansion(passenger))
                    {
                        string_id strSpam = new string_id("space/space_interaction", "no_ep3_passengers");
                        sendSystemMessage(self, strSpam);
                        return SCRIPT_CONTINUE;
                    }
                }
            }
        }
        else 
        {
            string_id strSpam = new string_id("space/space_interaction", "godmode_bypassing_ep3_check");
            sendSystemMessage(self, strSpam);
        }
        if (utils.hasScriptVar(objShip, "intHyperspacing"))
        {
            string_id strSpam = new string_id("space/space_interaction", "hyperspace_in_progress");
            sendSystemMessage(self, strSpam);
            return SCRIPT_CONTINUE;
        }
        if (utils.hasScriptVar(objShip, "intHyperspaceDelayStamp"))
        {
            int intTime = getGameTime();
            int intStamp = utils.getIntScriptVar(objShip, "intHyperspaceDelayStamp");
            int intDifference = intTime - intStamp;
            if (intDifference < HYPERSPACE_DELAY)
            {
                string_id strSpam = new string_id("space/space_interaction", "hyperspace_not_ready");
                prose_package pp = new prose_package();
                prose.setDI(pp, HYPERSPACE_DELAY - intDifference);
                prose.setStringId(pp, strSpam);
                sendSystemMessageProse(self, pp);
                return SCRIPT_CONTINUE;
            }
        }
        if (isHyperspacePointOverpopulated(hyperspacePoint))
        {
            if (isGod(self))
            {
                sendSystemMessageTestingOnly(self, "Allowing hyperspace to overpopulated zone because you are in god mode.");
            }
            else 
            {
                string_id tooFull = new string_id("shared_hyperspace", "zone_too_full");
                sendSystemMessage(self, tooFull);
                return SCRIPT_CONTINUE;
            }
        }
        utils.setScriptVar(objShip, "intHyperspacing", 1);
        dictionary dctParams = new dictionary();
        LOG("space", "doing hyperspace thing");
        string_id strSpam = new string_id("space/space_interaction", "hyperspace_route_begin");
        sendSystemMessage(self, strSpam);
        strSpam = new string_id("space/space_interaction", "hyperspace_route_calculation_1");
        dctParams.put("strSpam", strSpam);
        float fltDelay = (float)intDelay;
        messageTo(self, "doHyperspaceMessaging", dctParams, fltDelay * .25f, false);
        strSpam = new string_id("space/space_interaction", "hyperspace_route_calculation_2");
        dctParams.put("strSpam", strSpam);
        messageTo(self, "doHyperspaceMessaging", dctParams, fltDelay * .50f, false);
        strSpam = new string_id("space/space_interaction", "hyperspace_route_calculation_3");
        dctParams.put("strSpam", strSpam);
        messageTo(self, "doHyperspaceMessaging", dctParams, fltDelay * .75f, false);
        dctParams.put("strPoint", strParams[0]);
        dctParams.put("intCloseWings", 1);
        dctParams.put("intSound", 1);
        strSpam = new string_id("space/space_interaction", "hyperspace_route_calculation_4");
        dctParams.put("strSpam", strSpam);
        messageTo(self, "doHyperspaceMessaging", dctParams, fltDelay, false);
        dctParams.put("intCloseWings", 0);
        strSpam = new string_id("space/space_interaction", "hyperspace_route_calculation_5");
        dctParams.put("strSpam", strSpam);
        messageTo(self, "doHyperspaceMessaging", dctParams, fltDelay + 1, false);
        strSpam = new string_id("space/space_interaction", "hyperspace_route_calculation_6");
        dctParams.put("strSpam", strSpam);
        messageTo(self, "doHyperspaceMessaging", dctParams, fltDelay + 2, false);
        strSpam = new string_id("space/space_interaction", "hyperspace_route_calculation_7");
        dctParams.put("strSpam", strSpam);
        messageTo(self, "doHyperspaceMessaging", dctParams, fltDelay + 3, false);
        strSpam = new string_id("space/space_interaction", "hyperspace_route_calculation_8");
        dctParams.put("strSpam", strSpam);
        messageTo(self, "doHyperspaceMessaging", dctParams, fltDelay + 4, false);
        LOG("space", "doing hyperspace thing with " + intDelay);
        messageTo(self, "hyperspacePlayer", dctParams, intDelay + 5, false);
        return SCRIPT_CONTINUE;
    }
    public int doHyperspaceMessaging(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id objShip = space_transition.getContainingShip(self);
        if (!isIdValid(objShip))
        {
            return SCRIPT_CONTINUE;
        }
        if (!utils.hasScriptVar(objShip, "intHyperspacing"))
        {
            return SCRIPT_CONTINUE;
        }
        int intCloseWings = params.getInt("intCloseWings");
        if (intCloseWings > 0)
        {
            String strPoint = params.getString("strPoint");
            hyperspacePrepareShipOnClient(self, strPoint);
            clearCondition(objShip, CONDITION_WINGS_OPENED);
        }
        string_id strSpam = params.getStringId("strSpam");
        space_utils.sendSystemMessageShip(objShip, strSpam, true, false, true, true);
        int intSound = params.getInt("intSound");
        if (intSound > 0)
        {
            Vector objGunners = space_utils.getGunnersInShip(objShip);
            if (objGunners != null)
            {
                for (int intI = 0; intI < objGunners.size(); intI++)
                {
                    playMusic(((obj_id)objGunners.get(intI)), "sound/ship_hyperspace_countdown.snd");
                }
            }
            obj_id objOfficer = space_utils.getOperationsOfficer(objShip);
            if (isIdValid(objOfficer))
            {
                playMusic(objOfficer, "sound/ship_hyperspace_countdown.snd");
            }
            obj_id objPilot = getPilotId(objShip);
            if (isIdValid(objPilot))
            {
                playMusic(objPilot, "sound/ship_hyperspace_countdown.snd");
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int hyperspacePlayer(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id objShip = space_transition.getContainingShip(self);
        if (!isIdValid(objShip))
        {
            return SCRIPT_CONTINUE;
        }
        if (!utils.hasScriptVar(objShip, "intHyperspacing"))
        {
            return SCRIPT_CONTINUE;
        }
        utils.removeScriptVar(objShip, "intHyperspacing");
        if (!space_utils.isShipFlyable(objShip))
        {
            string_id strSpam = new string_id("space/space_interaction", "no_hyperspace_ship_damaged");
            sendSystemMessage(self, strSpam);
            return SCRIPT_CONTINUE;
        }
        String strChassisName = getShipChassisType(objShip);
        int intIndex = strChassisName.indexOf("_basic");
        if (intIndex > -1)
        {
            string_id strSpam = new string_id("space/space_interaction", "no_hyperdrive");
            sendSystemMessage(self, strSpam);
            return SCRIPT_CONTINUE;
        }
        String strPoint = params.getString("strPoint");
        ship_ai.unitRemoveFromAllAttackTargetLists(objShip);
        ship_ai.unitSetAutoAggroImmune(objShip, true);
        space_combat.doBattlefieldRepairCheck(objShip);
        hyperspacePlayerToHyperspacePoint(self, strPoint, null, true, isGod(self));
        return SCRIPT_CONTINUE;
    }
    public int OnHyperspaceToHomeLocation(obj_id self) throws InterruptedException
    {
        obj_id objShip = space_transition.getContainingShip(self);
        if (isIdValid(objShip))
        {
            space_combat.clearHyperspace(objShip);
            space_transition.clearOvertStatus(objShip);
        }
        space_transition.teleportPlayerToLaunchLoc(self, true);
        return SCRIPT_CONTINUE;
    }
    public int OnMadeAuthoritative(obj_id self) throws InterruptedException
    {
        space_transition.handlePotentialSceneChange(self);
        return SCRIPT_CONTINUE;
    }
    public int OnLogout(obj_id self) throws InterruptedException
    {
        space_transition.handleLogout(self);
        space_combat.strikeBomberCleanup(self);
        return SCRIPT_CONTINUE;
    }
    public int openContainer(obj_id self, obj_id target, String params, float defaultTime) throws InterruptedException
    {
        if (hasObjVar(target, "objLooters"))
        {
            obj_id[] objLooters = getObjIdArrayObjVar(target, "objLooters");
            int intIndex = utils.getElementPositionInArray(objLooters, self);
            if (intIndex < 0)
            {
                string_id strSpam = new string_id("space/space_loot", "no_permission");
                sendSystemMessage(self, strSpam);
                return SCRIPT_OVERRIDE;
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int destroyTarget(obj_id self, obj_id target, String params, float defaultTime) throws InterruptedException
    {
        obj_id objLookAtTarget = getLookAtTarget(self);
        if (!isIdValid(objLookAtTarget))
        {
            sendSystemMessageTestingOnly(self, "You must target a ship to destroy it");
            return SCRIPT_CONTINUE;
        }
        if (objLookAtTarget == self)
        {
            if (!params.equals("yes"))
            {
                sendSystemMessageTestingOnly(self, "DO NOT DESTROY YOURSELF, params is " + params);
                return SCRIPT_CONTINUE;
            }
        }
        dictionary dctParams = new dictionary();
        obj_id objShip = space_transition.getContainingShip(self);
        if (objLookAtTarget == objShip)
        {
            if (!params.equals("yes"))
            {
                sendSystemMessageTestingOnly(self, "DO NOT DESTROY YOURSELF, params is " + params);
                return SCRIPT_CONTINUE;
            }
        }
        if (params.equals("yes"))
        {
            sendSystemMessageTestingOnly(self, "killing " + objShip + " on command from " + self);
            dctParams.put("objShip", objShip);
            messageTo(objShip, "megaDamage", dctParams, 0, false);
            return SCRIPT_CONTINUE;
        }
        if (!isIdValid(objShip))
        {
            sendSystemMessageTestingOnly(self, "This is a ship only command. Get a ship");
            return SCRIPT_CONTINUE;
        }
        sendSystemMessageTestingOnly(self, "killing " + objLookAtTarget + " on command from " + self);
        dctParams.put("objShip", objShip);
        messageTo(objLookAtTarget, "megaDamage", dctParams, 0, false);
        return SCRIPT_CONTINUE;
    }
    public int disableTarget(obj_id self, obj_id target, String params, float defaultTime) throws InterruptedException
    {
        obj_id objLookAtTarget = getLookAtTarget(self);
        if (!isIdValid(objLookAtTarget))
        {
            sendSystemMessageTestingOnly(self, "You must target a ship to disable it");
            return SCRIPT_CONTINUE;
        }
        if (objLookAtTarget == self)
        {
            sendSystemMessageTestingOnly(self, "DO NOT DISABLE YOURSELF");
            return SCRIPT_CONTINUE;
        }
        dictionary dctParams = new dictionary();
        obj_id objShip = space_transition.getContainingShip(self);
        if (objLookAtTarget == objShip)
        {
            sendSystemMessageTestingOnly(self, "DO NOT DISABLE YOURSELF");
        }
        if (!isIdValid(objShip))
        {
            sendSystemMessageTestingOnly(self, "This is a ship only command. Get a ship");
            return SCRIPT_CONTINUE;
        }
        sendSystemMessageTestingOnly(self, "Disabling " + objLookAtTarget + " on command from " + self);
        dctParams.put("objShip", objShip);
        messageTo(objLookAtTarget, "disableSelf", dctParams, 0, false);
        return SCRIPT_CONTINUE;
    }
    public int cmdLaunchMissile(obj_id self, obj_id target, String params, float defaultTime) throws InterruptedException
    {
        debugSpeakMsg(self, "got launch missile command");
        if (!isIdValid(target))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id ship = space_transition.getContainingShip(self);
        if (!isIdValid(ship))
        {
            ship = self;
        }
        if (launchMissile(self, ship, getLookAtTarget(self), 0, -1))
        {
            debugSpeakMsg(self, "launchmissile returned true.");
        }
        else 
        {
            debugSpeakMsg(self, "launchMissile returned false.");
        }
        return SCRIPT_CONTINUE;
    }
    public int doDelayedSystemMessage(obj_id self, dictionary params) throws InterruptedException
    {
        string_id strSpam = params.getStringId("strSpam");
        sendSystemMessage(self, strSpam);
        return SCRIPT_CONTINUE;
    }
    public int cmdReactorPumpPulse(obj_id self, obj_id target, String params, float defaultTime) throws InterruptedException
    {
        debugServerConsoleMsg(null, "got Reactor Pulse Command");
        if (utils.hasScriptVar(self, "cmd.reactorPumpPulse"))
        {
            string_id strSpam = new string_id("space/space_interaction", "reactor_pumped_already");
            sendSystemMessage(self, strSpam);
            return SCRIPT_CONTINUE;
        }
        if (!space_combat.commandTimePassed(self))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id ship = space_transition.getContainingShip(self);
        if (!isIdValid(ship))
        {
            debugServerConsoleMsg(null, "ship obj_id isn't valid. BALLS!");
            return SCRIPT_CONTINUE;
        }
        if (space_combat.doReactorPumpPulse(self, ship))
        {
            debugServerConsoleMsg(null, "reactor pulse returned true.");
            utils.setScriptVar(self, "cmd.reactorPumpPulse", 1);
            space_combat.initializeCommandTimer(self, CMD_REACTOR_PUMP_DELAY);
        }
        else 
        {
            debugServerConsoleMsg(null, "reactor pulse returned false.");
        }
        return SCRIPT_CONTINUE;
    }
    public int cmdIffScramble(obj_id self, obj_id target, String params, float defaultTime) throws InterruptedException
    {
        debugServerConsoleMsg(null, "got IFF Scramble Command");
        if (utils.hasLocalVar(self, "cmd.iffScramble"))
        {
            string_id strSpam = new string_id("space/space_interaction", "iff_spoofed_already");
            sendSystemMessage(self, strSpam);
            return SCRIPT_CONTINUE;
        }
        if (!space_combat.commandTimePassed(self))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id ship = space_transition.getContainingShip(self);
        if (!isIdValid(ship))
        {
            debugServerConsoleMsg(null, "ship obj_id isn't valid. BALLS! YEEargh!!!");
            return SCRIPT_CONTINUE;
        }
        if (space_combat.doIffScramble(self, ship))
        {
            playClientEffectObj(self, "clienteffect/space_command/sys_manipulation.cef", ship, "");
            debugServerConsoleMsg(null, "Iff Scramble returned true.");
            utils.setLocalVar(self, "cmd.iffScramble", 1);
            space_combat.initializeCommandTimer(self, CMD_IFF_SCRAMBLE_DELAY);
            String cefPlayBackHardpoint = space_combat.targetHardpointForCefPlayback(ship);
            playClientEffectObj(self, "clienteffect/space_command/shp_shocked_radio_01.cef", ship, cefPlayBackHardpoint);
        }
        else 
        {
            debugServerConsoleMsg(null, "Iff Scramble returned false.");
        }
        return SCRIPT_CONTINUE;
    }
    public int unIffScramble(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id pilot = params.getObjId("pilot");
        obj_id ship = params.getObjId("ship");
        utils.removeLocalVar(pilot, "cmd.iffScramble");
        return SCRIPT_CONTINUE;
    }
    public int muteDroid(obj_id self, dictionary params) throws InterruptedException
    {
        debugServerConsoleMsg(null, "Just entered MUTEDROID message handler.");
        obj_id ship = space_transition.getContainingShip(self);
        if (!isIdValid(ship))
        {
            debugServerConsoleMsg(null, "ship obj_id isn't valid. BALLS! YEEargh!!!");
            return SCRIPT_CONTINUE;
        }
        if (utils.hasLocalVar(ship, "droidcmd.muteDroid"))
        {
            string_id strSpam = new string_id("space/space_interaction", "unmute_droid");
            sendSystemMessage(self, strSpam);
            utils.removeLocalVar(ship, "droidcmd.muteDroid");
        }
        else 
        {
            string_id strSpam = new string_id("space/space_interaction", "mute_droid");
            sendSystemMessage(self, strSpam);
            utils.setLocalVar(ship, "droidcmd.muteDroid", 1);
        }
        return SCRIPT_CONTINUE;
    }
    public int zoneToKessel(obj_id self, dictionary params) throws InterruptedException
    {
        if (utils.hasScriptVar(self, "space.zoneDestination"))
        {
            sendSystemMessage(self, "Hyperspace calculations already in progress.", null);
            return SCRIPT_CONTINUE;
        }
        obj_id ship = space_transition.getContainingShip(self);
        if (!isIdValid(ship))
        {
            return SCRIPT_CONTINUE;
        }
        String template = getTemplateName(ship);
        if (template != null && template.endsWith("_yacht.iff"))
        {
            return SCRIPT_CONTINUE;
        }
        String zone = getCurrentSceneName();
        if (zone.equals("space_heavy1"))
        {
            sendSystemMessage(self, "You cannot use this command in Deep Space.", null);
            return SCRIPT_CONTINUE;
        }
        if (zone.equals("space_light1"))
        {
            sendSystemMessage(self, "You cannot use this command in Kessel.", null);
            return SCRIPT_CONTINUE;
        }
        utils.setScriptVar(self, "space.zoneDestination", "space_light1");
        utils.setScriptVar(self, "space.zoneFaction", "kessel");
        zoneToCommand(self);
        return SCRIPT_CONTINUE;
    }
    public int zoneToImperialDeepSpace(obj_id self, dictionary params) throws InterruptedException
    {
        if (space_flags.isRebelPilot(self) || factions.isRebel(self))
        {
            sendSystemMessage(self, "This hypersapce route is unavailable to Rebel Pilots", null);
            return SCRIPT_CONTINUE;
        }
        if (utils.hasScriptVar(self, "space.zoneDestination"))
        {
            sendSystemMessage(self, "Hyperspace calculations already in progress.", null);
            return SCRIPT_CONTINUE;
        }
        obj_id ship = space_transition.getContainingShip(self);
        if (!isIdValid(ship))
        {
            return SCRIPT_CONTINUE;
        }
        String template = getTemplateName(ship);
        if (template != null && template.endsWith("_yacht.iff"))
        {
            return SCRIPT_CONTINUE;
        }
        String zone = getCurrentSceneName();
        if (zone.equals("space_heavy1"))
        {
            sendSystemMessage(self, "You cannot use this command in Deep Space.", null);
            return SCRIPT_CONTINUE;
        }
        if (zone.equals("space_light1"))
        {
            sendSystemMessage(self, "You cannot use this command in Kessel.", null);
            return SCRIPT_CONTINUE;
        }
        utils.setScriptVar(self, "space.zoneDestination", "space_heavy1");
        utils.setScriptVar(self, "space.zoneFaction", "imperial");
        zoneToCommand(self);
        return SCRIPT_CONTINUE;
    }
    public int zoneToRebelDeepSpace(obj_id self, dictionary params) throws InterruptedException
    {
        if (space_flags.isImperialPilot(self) || factions.isImperial(self))
        {
            sendSystemMessage(self, "This hyperspace route is unavailable to Imperial Pilots", null);
            return SCRIPT_CONTINUE;
        }
        if (utils.hasScriptVar(self, "space.zoneDestination"))
        {
            sendSystemMessage(self, "Hyperspace calculations already in progress.", null);
            return SCRIPT_CONTINUE;
        }
        obj_id ship = space_transition.getContainingShip(self);
        if (!isIdValid(ship))
        {
            return SCRIPT_CONTINUE;
        }
        String template = getTemplateName(ship);
        if (template != null && template.endsWith("_yacht.iff"))
        {
            return SCRIPT_CONTINUE;
        }
        String zone = getCurrentSceneName();
        if (zone.equals("space_heavy1"))
        {
            sendSystemMessage(self, "You cannot use this command in Deep Space.", null);
            return SCRIPT_CONTINUE;
        }
        if (zone.equals("space_light1"))
        {
            sendSystemMessage(self, "You cannot use this command in Kessel.", null);
            return SCRIPT_CONTINUE;
        }
        utils.setScriptVar(self, "space.zoneDestination", "space_heavy1");
        utils.setScriptVar(self, "space.zoneFaction", "rebel");
        zoneToCommand(self);
        return SCRIPT_CONTINUE;
    }
    public int zoneTimer(obj_id self, dictionary params) throws InterruptedException
    {
        if (!utils.hasScriptVar(self, "space.zoneDestination"))
        {
            return SCRIPT_CONTINUE;
        }
        zoneToCommand(self);
        return SCRIPT_CONTINUE;
    }
    public int spacePvpRebel(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id ship = space_transition.getContainingShip(self);
        if (hasObjVar(ship, "spaceFaction.FactionOverride") && hasObjVar(ship, "spaceFaction.overt"))
        {
            int faction = getIntObjVar(ship, "spaceFaction.FactionOverride");
            if (faction == (370444368))
            {
                sendSystemMessage(self, "Your ship is already registered as Special Forces with the Rebellion.", null);
            }
            if (faction == (-615855020))
            {
                sendSystemMessage(self, "Your ship is already registered as Special Forces with the Empire.", null);
            }
            return SCRIPT_CONTINUE;
        }
        if (hasObjVar(ship, "spaceFaction.FactionOverride") && !hasObjVar(ship, "spaceFaction.overt"))
        {
            int faction = getIntObjVar(ship, "spaceFaction.FactionOverride");
            if (faction == (-615855020))
            {
                sendSystemMessage(self, "Your ship is already registered with the Empire.", null);
                return SCRIPT_CONTINUE;
            }
        }
        if (hasObjVar(ship, "spaceFaction.overt"))
        {
            sendSystemMessage(self, "Your are already registered as a Special Forces pilot.", null);
            return SCRIPT_CONTINUE;
        }
        if (!utils.hasScriptVar(ship, "space.goOvert"))
        {
            int st = getGameTime();
            int counter = 1;
            utils.setScriptVar(ship, "space.goOvertCounter", counter);
            utils.setScriptVar(ship, "space.goOvertStartTime", st);
            utils.setScriptVar(ship, "space.goOvert", "rebel");
            pvpPrepareToBeDeclared(ship);
            sendSystemMessage(self, "Registering the ship's allegiance as Special Forces with the Rebel Navy.", null);
            sendSystemMessage(self, "The process requires that your ship be at a standstill.", null);
            messageTo(self, "spacePvpRebel", null, 5, false);
            return SCRIPT_CONTINUE;
        }
        if (!utils.hasScriptVar(ship, "space.goOvertCounter"))
        {
            utils.removeScriptVar(ship, "space.goOvertStartTimer");
            utils.removeScriptVar(ship, "space.goOvert");
            pvpPrepareToBeNeutral(ship);
            return SCRIPT_CONTINUE;
        }
        int counter = utils.getIntScriptVar(ship, "space.goOvertCounter");
        if (counter < 6)
        {
            float speed = space_pilot_command.getShipCurrentSpeed(ship);
            if (speed > 10.0f)
            {
                sendSystemMessage(self, "Aborting registration. The registration process requires you remain in place.", null);
                utils.removeScriptVar(ship, "space.goOvertStartTimer");
                utils.removeScriptVar(ship, "space.goOvert");
                pvpPrepareToBeNeutral(ship);
                return SCRIPT_CONTINUE;
            }
            counter = counter + 1;
            utils.setScriptVar(ship, "space.goOvertCounter", counter);
            messageTo(self, "spacePvpRebel", null, 5, false);
            return SCRIPT_CONTINUE;
        }
        int startTime = utils.getIntScriptVar(ship, "space.goOvertStartTime");
        String faction = utils.getStringScriptVar(ship, "space.goOvert");
        if (faction.equals("imperial"))
        {
            utils.removeScriptVar(ship, "space.goOvertStartTimer");
            utils.removeScriptVar(ship, "space.goOvert");
            pvpPrepareToBeNeutral(ship);
            sendSystemMessage(self, "Your ship is already being registered as Special Forces with the Empire.", null);
            return SCRIPT_CONTINUE;
        }
        sendSystemMessage(self, "Your allegiance with the Rebel Navy has been registered. You can now accelerate freely. Your Special Forces status will start in 15 seconds.", null);
        setObjVar(ship, "spaceFaction.FactionOverride", (370444368));
        setObjVar(ship, "spaceFaction.overt", 1);
        messageTo(ship, "checkSpacePVPStatus", null, 15, false);
        utils.removeScriptVar(ship, "space.goOvertStartTimer");
        utils.removeScriptVar(ship, "space.goOvert");
        CustomerServiceLog("space_commands", "Player " + self + " in ship " + ship + " has been flagged as Rebel Special Forces");
        return SCRIPT_CONTINUE;
    }
    public int spacePvpImperial(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id ship = space_transition.getContainingShip(self);
        if (hasObjVar(ship, "spaceFaction.FactionOverride") && hasObjVar(ship, "spaceFaction.overt"))
        {
            int faction = getIntObjVar(ship, "spaceFaction.FactionOverride");
            if (faction == (370444368))
            {
                sendSystemMessage(self, "Your ship is already registered as Special Forces with the Rebellion.", null);
            }
            if (faction == (-615855020))
            {
                sendSystemMessage(self, "Your ship is already registered as Special Forces with the Empire.", null);
            }
            return SCRIPT_CONTINUE;
        }
        if (hasObjVar(ship, "spaceFaction.FactionOverride") && !hasObjVar(ship, "spaceFaction.overt"))
        {
            int faction = getIntObjVar(ship, "spaceFaction.FactionOverride");
            if (faction == (370444368))
            {
                sendSystemMessage(self, "Your ship is already registered with the Rebellion.", null);
                return SCRIPT_CONTINUE;
            }
        }
        if (hasObjVar(ship, "spaceFaction.overt"))
        {
            sendSystemMessage(self, "Your are already registered as a Special Forces pilot.", null);
            return SCRIPT_CONTINUE;
        }
        if (!utils.hasScriptVar(ship, "space.goOvert"))
        {
            int st = getGameTime();
            int counter = 1;
            utils.setScriptVar(ship, "space.goOvertCounter", counter);
            utils.setScriptVar(ship, "space.goOvertStartTime", st);
            utils.setScriptVar(ship, "space.goOvert", "imperial");
            pvpPrepareToBeDeclared(ship);
            sendSystemMessage(self, "Registering the ship's allegiance as Special Forces with the Imperial Navy.", null);
            sendSystemMessage(self, "The process requires that your ship be at a standstill.", null);
            messageTo(self, "spacePvpImperial", null, 5, false);
            return SCRIPT_CONTINUE;
        }
        if (!utils.hasScriptVar(ship, "space.goOvertCounter"))
        {
            utils.removeScriptVar(ship, "space.goOvertStartTimer");
            utils.removeScriptVar(ship, "space.goOvert");
            pvpPrepareToBeNeutral(ship);
            return SCRIPT_CONTINUE;
        }
        int counter = utils.getIntScriptVar(ship, "space.goOvertCounter");
        if (counter < 6)
        {
            float speed = space_pilot_command.getShipCurrentSpeed(ship);
            if (speed > 10.0f)
            {
                sendSystemMessage(self, "Aborting registration. The registration process requires you remain in place.", null);
                utils.removeScriptVar(ship, "space.goOvertStartTimer");
                utils.removeScriptVar(ship, "space.goOvert");
                pvpPrepareToBeNeutral(ship);
                return SCRIPT_CONTINUE;
            }
            counter = counter + 1;
            utils.setScriptVar(ship, "space.goOvertCounter", counter);
            messageTo(self, "spacePvpImperial", null, 5, false);
            return SCRIPT_CONTINUE;
        }
        int startTime = utils.getIntScriptVar(ship, "space.goOvertStartTime");
        String faction = utils.getStringScriptVar(ship, "space.goOvert");
        if (faction.equals("rebel"))
        {
            utils.removeScriptVar(ship, "space.goOvertStartTimer");
            utils.removeScriptVar(ship, "space.goOvert");
            pvpPrepareToBeNeutral(ship);
            sendSystemMessage(self, "Your ship is already being registered as Special Forces with the Rebellion.", null);
            return SCRIPT_CONTINUE;
        }
        sendSystemMessage(self, "Your allegiance with the Imperial Navy has been registered. You can now accelerate freely. Your Special Forces status will start in 15 seconds.", null);
        setObjVar(ship, "spaceFaction.FactionOverride", (-615855020));
        setObjVar(ship, "spaceFaction.overt", 1);
        messageTo(ship, "checkSpacePVPStatus", null, 15, false);
        utils.removeScriptVar(ship, "space.goOvertTimer");
        utils.removeScriptVar(ship, "space.goOvert");
        CustomerServiceLog("space_commands", "Player " + self + " in ship " + ship + " has been flagged as Imperial Special Forces");
        return SCRIPT_CONTINUE;
    }
    public int spaceRebelHelper(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id ship = space_transition.getContainingShip(self);
        if (hasObjVar(ship, "spaceFaction.FactionOverride") && hasObjVar(ship, "spaceFaction.overt"))
        {
            int faction = getIntObjVar(ship, "spaceFaction.FactionOverride");
            if (faction == (370444368))
            {
                sendSystemMessage(self, "Your ship is already registered as Special Forces with the Rebellion.", null);
            }
            if (faction == (-615855020))
            {
                sendSystemMessage(self, "Your ship is already registered as Special Forces with the Empire.", null);
            }
            return SCRIPT_CONTINUE;
        }
        if (hasObjVar(ship, "spaceFaction.FactionOverride") && !hasObjVar(ship, "spaceFaction.overt"))
        {
            int faction = getIntObjVar(ship, "spaceFaction.FactionOverride");
            if (faction == (370444368))
            {
                sendSystemMessage(self, "Your ship is already registered as Combatant with the Rebellion.", null);
            }
            if (faction == (-615855020))
            {
                sendSystemMessage(self, "Your ship is already registered as Combatant with the Empire.", null);
            }
            return SCRIPT_CONTINUE;
        }
        if (!utils.hasScriptVar(ship, "space.FactionOverride"))
        {
            int st = getGameTime();
            utils.setScriptVar(ship, "space.FactionOverrideStartTimer", st);
            utils.setScriptVar(ship, "space.FactionOverride", "rebel");
            sendSystemMessage(self, "Registering the ship's allegiance as Combatant with the Rebel Navy.", null);
            sendSystemMessage(self, "It will take 30 seconds to complete the registration.", null);
            messageTo(self, "spaceRebelHelper", null, 30, false);
            return SCRIPT_CONTINUE;
        }
        int startTime = utils.getIntScriptVar(ship, "space.FactionOverrideStartTimer");
        if (startTime + 30 > getGameTime())
        {
            sendSystemMessage(self, "We are already processing your request to declare your ship as Combatant with the Rebellion.", null);
            return SCRIPT_CONTINUE;
        }
        sendSystemMessage(self, "Your ship's status as Combatant with the Rebel Navy has been registered.", null);
        setObjVar(ship, "spaceFaction.FactionOverride", (370444368));
        space_utils.notifyObject(ship, "checkSpacePVPStatus", null);
        utils.removeScriptVar(ship, "space.FactionOverrideStartTimer");
        utils.removeScriptVar(ship, "space.FactionOverride");
        CustomerServiceLog("space_commands", "Player " + self + " in ship " + ship + " has been flagged as Rebel Factional Helper");
        return SCRIPT_CONTINUE;
    }
    public int spaceImperialHelper(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id ship = space_transition.getContainingShip(self);
        if (hasObjVar(ship, "spaceFaction.FactionOverride") && hasObjVar(ship, "spaceFaction.overt"))
        {
            int faction = getIntObjVar(ship, "spaceFaction.FactionOverride");
            if (faction == (370444368))
            {
                sendSystemMessage(self, "Your ship is already registered as Special Forces with the Rebellion.", null);
            }
            if (faction == (-615855020))
            {
                sendSystemMessage(self, "Your ship is already registered as Special Forces with the Empire.", null);
            }
            return SCRIPT_CONTINUE;
        }
        if (hasObjVar(ship, "spaceFaction.FactionOverride") && !hasObjVar(ship, "spaceFaction.overt"))
        {
            int faction = getIntObjVar(ship, "spaceFaction.FactionOverride");
            if (faction == (370444368))
            {
                sendSystemMessage(self, "Your ship is already registered as Combatant with the Rebellion.", null);
            }
            if (faction == (-615855020))
            {
                sendSystemMessage(self, "Your ship is already registered as Combatant with the Empire.", null);
            }
            return SCRIPT_CONTINUE;
        }
        if (!utils.hasScriptVar(ship, "space.FactionOverride"))
        {
            int st = getGameTime();
            utils.setScriptVar(ship, "space.FactionOverrideStartTimer", st);
            utils.setScriptVar(ship, "space.FactionOverride", "rebel");
            sendSystemMessage(self, "Registering the ship's allegiance as Combatant with the Imperial Navy.", null);
            sendSystemMessage(self, "It will take 30 seconds to complete the registration.", null);
            messageTo(self, "spaceImperialHelper", null, 30, false);
            return SCRIPT_CONTINUE;
        }
        int startTime = utils.getIntScriptVar(ship, "space.FactionOverrideStartTimer");
        if (startTime + 30 > getGameTime())
        {
            sendSystemMessage(self, "We are already processing your request to declare your ship as Combatant with the Empire.", null);
            return SCRIPT_CONTINUE;
        }
        sendSystemMessage(self, "Your ship's status as Combatant with the Imperial Navy has been registered.", null);
        setObjVar(ship, "spaceFaction.FactionOverride", (-615855020));
        space_utils.notifyObject(ship, "checkSpacePVPStatus", null);
        utils.removeScriptVar(ship, "space.FactionOverrideStartTimer");
        utils.removeScriptVar(ship, "space.FactionOverride");
        CustomerServiceLog("space_commands", "Player " + self + " in ship " + ship + " has been flagged as Imperial Factional Helper");
        return SCRIPT_CONTINUE;
    }
    public void zoneToCommand(obj_id self) throws InterruptedException
    {
        if (!isIdValid(self) || !exists(self))
        {
            return;
        }
        if (!utils.hasScriptVar(self, "space.zoneDestination") || !utils.hasScriptVar(self, "space.zoneFaction"))
        {
            return;
        }
        String zone = utils.getStringScriptVar(self, "space.zoneDestination");
        String faction = utils.getStringScriptVar(self, "space.zoneFaction");
        obj_id ship = space_transition.getContainingShip(self);
        if (hasObjVar(ship, "spaceFaction.FactionOverride") && zone.equals("space_heavy1"))
        {
            int factionHash = getIntObjVar(ship, "spaceFaction.FactionOverride");
            if (factionHash == (370444368) && !faction.equals("rebel"))
            {
                sendSystemMessage(self, "You cannot enter Deep Space as an Imperial, your ship is already registered with the Rebellion", null);
                utils.removeScriptVar(ship, "space.zoneToBattlefieldLoc");
                utils.removeScriptVar(ship, "space.countZoneToBattlefield");
                utils.removeScriptVar(self, "space.zoneDestination");
                utils.removeScriptVar(self, "space.zoneFaction");
                return;
            }
            if (factionHash == (-615855020) && !faction.equals("imperial"))
            {
                sendSystemMessage(self, "You cannot enter Deep Space as a Rebel, your ship is already registered with the Empire", null);
                utils.removeScriptVar(ship, "space.zoneToBattlefieldLoc");
                utils.removeScriptVar(ship, "space.countZoneToBattlefield");
                utils.removeScriptVar(self, "space.zoneDestination");
                utils.removeScriptVar(self, "space.zoneFaction");
                return;
            }
        }
        location currentLoc = getLocation(ship);
        String dest = "Deep Space";
        if (zone.equals("space_light1"))
        {
            dest = "Kessel";
        }
        if (!utils.hasScriptVar(ship, "space.zoneToBattlefieldLoc"))
        {
            utils.setScriptVar(ship, "space.zoneToBattlefieldLoc", currentLoc);
            sendSystemMessage(self, "Calculating safe hyperspace path to " + dest + ". Please remain in the area as any movement could disrupt the calculations.", null);
            CustomerServiceLog("space_commands", "Player " + self + " in ship " + ship + " starting hyperspace jump to " + dest);
        }
        else if (utils.hasScriptVar(ship, "space.zoneToBattlefieldLoc"))
        {
            location startLoc = utils.getLocationScriptVar(ship, "space.zoneToBattlefieldLoc");
            float fltDistance = getDistance(ship, startLoc);
            if (fltDistance > 500)
            {
                sendSystemMessage(self, "Hyperspace calculations interupted. By moving too far from the starting point the calculations has been rendered invalid.", null);
                utils.removeScriptVar(ship, "space.zoneToBattlefieldLoc");
                utils.removeScriptVar(ship, "space.countZoneToBattlefield");
                utils.removeScriptVar(self, "space.zoneDestination");
                utils.removeScriptVar(self, "space.zoneFaction");
                return;
            }
        }
        int percentToZone = utils.getIntScriptVar(ship, "space.countZoneToBattlefield");
        sendSystemMessage(self, "Hyperspace calculations " + percentToZone + "% complete.", null);
        if (percentToZone >= 100)
        {
            utils.removeScriptVar(ship, "space.zoneToBattlefieldLoc");
            utils.removeScriptVar(ship, "space.countZoneToBattlefield");
            utils.removeScriptVar(self, "space.zoneDestination");
            utils.removeScriptVar(self, "space.zoneFaction");
            if (faction.equals("imperial") && !space_flags.isRebelPilot(self))
            {
                setObjVar(ship, "spaceFaction.FactionOverride", (-615855020));
                space_battlefield.doBattleFieldCommandTransition(ship, self, zone, "imperial_entry");
                return;
            }
            if (faction.equals("rebel") && !space_flags.isImperialPilot(self))
            {
                setObjVar(ship, "spaceFaction.FactionOverride", (370444368));
                space_battlefield.doBattleFieldCommandTransition(ship, self, zone, "rebel_entry");
                return;
            }
            String factionEntry = "neutral_entry";
            if (space_flags.isRebelPilot(self))
            {
                factionEntry = "rebel_entry";
            }
            if (space_flags.isImperialPilot(self))
            {
                factionEntry = "imperial_entry";
            }
            space_battlefield.doBattleFieldCommandTransition(ship, self, zone, factionEntry);
            return;
        }
        percentToZone = percentToZone + 25;
        utils.setScriptVar(ship, "space.countZoneToBattlefield", percentToZone);
        messageTo(self, "zoneTimer", null, 5, false);
        return;
    }
    public int cmdEmergencyWeapons(obj_id self, obj_id target, String params, float defaultTime) throws InterruptedException
    {
        debugServerConsoleMsg(null, "got Emergency Weapons Command");
        if (!space_combat.commandTimePassed(self))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id ship = space_transition.getContainingShip(self);
        if (!isIdValid(ship))
        {
            debugServerConsoleMsg(null, "ship obj_id isn't valid. BALLS! YEEargh!!!");
            return SCRIPT_CONTINUE;
        }
        if (utils.hasLocalVar(ship, "cmd.emergWeapon"))
        {
            string_id strSpam = new string_id("space/space_interaction", "already_emerg_wpn");
            sendSystemMessage(self, strSpam);
            return SCRIPT_CONTINUE;
        }
        if (!space_combat.readyForEmergencyPower(ship))
        {
            string_id strSpam = new string_id("space/space_interaction", "systems_not_ready");
            sendSystemMessage(self, strSpam);
            return SCRIPT_CONTINUE;
        }
        playClientEffectObj(self, "clienteffect/space_command/sys_manipulation.cef", ship, "");
        if (!isShipSlotInstalled(ship, ship_chassis_slot_type.SCST_reactor))
        {
            debugServerConsoleMsg(null, "+++ SPACE COMMAND . cmdEmergencyWeapons +++ No reactor onboard. What the!?.");
            return SCRIPT_CONTINUE;
        }
        space_combat.flightDroidVocalize(ship, 1);
        if (space_combat.doesReactorScram(self, ship, "level2command"))
        {
            space_combat.scramReactor(self, ship);
            return SCRIPT_CONTINUE;
        }
        utils.setLocalVar(ship, "cmd.emergWeapon", 1);
        space_combat.initializeCommandTimer(self, CMD_EMERGENCY_WEAPONS_DELAY);
        string_id strSpam = new string_id("space/space_interaction", "emergency_weapons");
        sendSystemMessage(self, strSpam);
        for (int chassisSlot = ship_chassis_slot_type.SCST_shield_0; chassisSlot < ship_chassis_slot_type.SCST_shield_1; chassisSlot++)
        {
            if (isShipSlotInstalled(ship, chassisSlot))
            {
                space_utils.setComponentDisabled(ship, chassisSlot, true);
            }
        }
        float fltGeneralEfficiency = 5.0f;
        float fltEnergyEfficiency = 5.0f;
        final int[] WEAPON_SLOTS = 
        {
            ship_chassis_slot_type.SCST_weapon_0,
            ship_chassis_slot_type.SCST_weapon_1,
            ship_chassis_slot_type.SCST_weapon_2,
            ship_chassis_slot_type.SCST_weapon_3,
            ship_chassis_slot_type.SCST_weapon_4,
            ship_chassis_slot_type.SCST_weapon_5,
            ship_chassis_slot_type.SCST_weapon_6,
            ship_chassis_slot_type.SCST_weapon_7
        };
        for (int intI = 0; intI < WEAPON_SLOTS.length; intI++)
        {
            if (isShipSlotInstalled(ship, WEAPON_SLOTS[intI]))
            {
                if ((fltGeneralEfficiency != 0) && (fltEnergyEfficiency != 0))
                {
                    if (space_combat.isEfficiencyModified(WEAPON_SLOTS[intI], ship))
                    {
                        space_combat.setEfficiencyModifier(WEAPON_SLOTS[intI], ship, 1.0f, 1.0f);
                    }
                    space_combat.setEfficiencyModifier(WEAPON_SLOTS[intI], ship, fltGeneralEfficiency, fltEnergyEfficiency);
                }
            }
        }
        String cefPlayBackHardpoint = space_combat.targetHardpointForCefPlayback(ship);
        playClientEffectObj(self, "clienteffect/space_command/emergency_power_on.cef", ship, cefPlayBackHardpoint);
        float emergencyPowerTime = (rand(10.0f, 90.0f));
        dictionary outparams = new dictionary();
        outparams.put("pilot", self);
        outparams.put("ship", ship);
        outparams.put("emergencyPowerTime", emergencyPowerTime);
        if (!messageTo(ship, "emergencyPowerTimeout", outparams, emergencyPowerTime, false))
        {
            debugServerConsoleMsg(null, "+++ SPACE COMMAND . cmdEmergencyWeapons +++ FAILED to send messageTo. ObjID of ship was: " + ship + " objID of pilot was: " + self + " time delay was: " + emergencyPowerTime);
        }
        return SCRIPT_CONTINUE;
    }
    public int cmdEmergencyShields(obj_id self, obj_id target, String params, float defaultTime) throws InterruptedException
    {
        debugServerConsoleMsg(null, "got Emergency Shields Command");
        if (!space_combat.commandTimePassed(self))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id ship = space_transition.getContainingShip(self);
        if (!isIdValid(ship))
        {
            debugServerConsoleMsg(null, "ship obj_id isn't valid. BALLS! YEEargh!!!");
            return SCRIPT_CONTINUE;
        }
        if (utils.hasLocalVar(ship, "cmd.emergShields"))
        {
            string_id strSpam = new string_id("space/space_interaction", "already_emerg_shlds");
            sendSystemMessage(self, strSpam);
            return SCRIPT_CONTINUE;
        }
        if (!space_combat.readyForEmergencyPower(ship))
        {
            string_id strSpam = new string_id("space/space_interaction", "systems_not_ready");
            sendSystemMessage(self, strSpam);
            return SCRIPT_CONTINUE;
        }
        playClientEffectObj(self, "clienteffect/space_command/sys_manipulation.cef", ship, "");
        if (!isShipSlotInstalled(ship, ship_chassis_slot_type.SCST_reactor))
        {
            debugServerConsoleMsg(null, "+++ SPACE COMMAND . cmdEmergencyShields +++ No reactor onboard. What the!?.");
            return SCRIPT_CONTINUE;
        }
        space_combat.flightDroidVocalize(ship, 1);
        if (space_combat.doesReactorScram(self, ship, "level2command"))
        {
            space_combat.scramReactor(self, ship);
            return SCRIPT_CONTINUE;
        }
        utils.setLocalVar(ship, "cmd.emergShields", 1);
        space_combat.initializeCommandTimer(self, CMD_EMERGENCY_SHIELDS_DELAY);
        string_id strSpam = new string_id("space/space_interaction", "emergency_shields");
        sendSystemMessage(self, strSpam);
        space_combat.disableWeaponry(ship, true);
        float fltGeneralEfficiency = 10.0f;
        float fltEnergyEfficiency = 10.0f;
        for (int chassisSlot = ship_chassis_slot_type.SCST_shield_0; chassisSlot < ship_chassis_slot_type.SCST_shield_1; chassisSlot++)
        {
            if (isShipSlotInstalled(ship, chassisSlot))
            {
                if ((fltGeneralEfficiency != 0) && (fltEnergyEfficiency != 0))
                {
                    if (space_combat.isEfficiencyModified(chassisSlot, ship))
                    {
                        space_combat.setEfficiencyModifier(chassisSlot, ship, 1.0f, 1.0f);
                    }
                    space_combat.setEfficiencyModifier(chassisSlot, ship, fltGeneralEfficiency, fltEnergyEfficiency);
                }
            }
        }
        String cefPlayBackHardpoint = space_combat.targetHardpointForCefPlayback(ship);
        playClientEffectObj(self, "clienteffect/space_command/emergency_power_on.cef", ship, cefPlayBackHardpoint);
        float emergencyPowerTime = (rand(30.0f, 90.0f));
        dictionary outparams = new dictionary();
        outparams.put("pilot", self);
        outparams.put("ship", ship);
        outparams.put("emergencyPowerTime", emergencyPowerTime);
        if (!messageTo(ship, "emergencyPowerTimeout", outparams, emergencyPowerTime, false))
        {
            debugServerConsoleMsg(null, "+++ SPACE COMMAND . cmdEmergencyShields +++ FAILED to send messageTo. ObjID of ship was: " + ship + " objID of pilot was: " + self + " time delay was: " + emergencyPowerTime);
        }
        return SCRIPT_CONTINUE;
    }
    public int cmdEmergencyThrust(obj_id self, obj_id target, String params, float defaultTime) throws InterruptedException
    {
        debugServerConsoleMsg(null, "got Emergency Thrust Command");
        if (!space_combat.commandTimePassed(self))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id ship = space_transition.getContainingShip(self);
        if (!isIdValid(ship))
        {
            debugServerConsoleMsg(null, "ship obj_id isn't valid. BALLS! YEEargh!!!");
            return SCRIPT_CONTINUE;
        }
        if (utils.hasLocalVar(ship, "cmd.emergThrust"))
        {
            string_id strSpam = new string_id("space/space_interaction", "already_emerg_engines");
            sendSystemMessage(self, strSpam);
            return SCRIPT_CONTINUE;
        }
        if (!space_combat.readyForEmergencyPower(ship))
        {
            string_id strSpam = new string_id("space/space_interaction", "systems_not_ready");
            sendSystemMessage(self, strSpam);
            return SCRIPT_CONTINUE;
        }
        playClientEffectObj(self, "clienteffect/space_command/sys_manipulation.cef", ship, "");
        if (!isShipSlotInstalled(ship, ship_chassis_slot_type.SCST_reactor))
        {
            debugServerConsoleMsg(null, "+++ SPACE COMMAND . cmdEmergencyThrust +++ No reactor onboard. What the!?.");
            return SCRIPT_CONTINUE;
        }
        space_combat.flightDroidVocalize(ship, 1);
        if (space_combat.doesReactorScram(self, ship, "level2command"))
        {
            space_combat.scramReactor(self, ship);
            return SCRIPT_CONTINUE;
        }
        utils.setLocalVar(ship, "cmd.emergThrust", 1);
        space_combat.initializeCommandTimer(self, CMD_EMERGENCY_THRUST_DELAY);
        string_id strSpam = new string_id("space/space_interaction", "emergency_thrust");
        sendSystemMessage(self, strSpam);
        space_combat.disableWeaponry(ship, true);
        for (int chassisSlot = ship_chassis_slot_type.SCST_shield_0; chassisSlot < ship_chassis_slot_type.SCST_shield_1; chassisSlot++)
        {
            if (isShipSlotInstalled(ship, chassisSlot))
            {
                space_utils.setComponentDisabled(ship, chassisSlot, true);
            }
        }
        float fltGeneralEfficiency = 1.3f;
        float fltEnergyEfficiency = 1.3f;
        int chassisSlot = ship_chassis_slot_type.SCST_engine;
        if (isShipSlotInstalled(ship, chassisSlot))
        {
            if ((fltGeneralEfficiency != 0) && (fltEnergyEfficiency != 0))
            {
                if (space_combat.isEfficiencyModified(chassisSlot, ship))
                {
                    space_combat.setEfficiencyModifier(chassisSlot, ship, 1.0f, 1.0f);
                }
                space_combat.setEfficiencyModifier(chassisSlot, ship, fltGeneralEfficiency, fltEnergyEfficiency);
            }
        }
        String cefPlayBackHardpoint = space_combat.targetHardpointForCefPlayback(ship);
        playClientEffectObj(self, "clienteffect/space_command/emergency_power_on.cef", ship, cefPlayBackHardpoint);
        float emergencyPowerTime = (rand(10.0f, 60.0f));
        dictionary outparams = new dictionary();
        outparams.put("pilot", self);
        outparams.put("ship", ship);
        outparams.put("emergencyPowerTime", emergencyPowerTime);
        if (!messageTo(ship, "emergencyPowerTimeout", outparams, emergencyPowerTime, false))
        {
            debugServerConsoleMsg(null, "+++ SPACE COMMAND . cmdEmergencyThrust +++ FAILED to send messageTo. ObjID of ship was: " + ship + " objID of pilot was: " + self + " time delay was: " + emergencyPowerTime);
        }
        return SCRIPT_CONTINUE;
    }
    public int cmdVampiricRepair(obj_id self, obj_id target, String params, float defaultTime) throws InterruptedException
    {
        debugServerConsoleMsg(null, "got Vampiric Repair Command");
        if (!space_combat.commandTimePassed(self))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id ship = space_transition.getContainingShip(self);
        if (!isIdValid(ship))
        {
            debugServerConsoleMsg(null, "ship obj_id isn't valid. BALLS! YEEargh!!!");
            return SCRIPT_CONTINUE;
        }
        playClientEffectObj(self, "clienteffect/space_command/sys_manipulation.cef", ship, "");
        space_combat.flightDroidVocalize(ship, 1);
        space_combat.initializeCommandTimer(self, CMD_VAMPIRIC_REPAIR_DELAY);
        string_id strSpam = new string_id("space/space_interaction", "vampiric_repair");
        string_id strSpam2 = new string_id("space/space_interaction", "vampiric_repair2");
        sendSystemMessage(self, strSpam);
        sendSystemMessage(self, strSpam2);
        int successLevel = space_combat.doPilotCommandSkillCheck(self, "level2command");
        if (successLevel < 1 || successLevel > 6)
        {
            debugServerConsoleMsg(null, "Holy Cow! Some kinda of whack-tacular invalid result (" + successLevel + ") came back from the skill check. Defaulting to average success! (3)");
            successLevel = 4;
        }
        else if (successLevel == 5)
        {
            sendSystemMessageTestingOnly(self, "Failure in system canibalization sub-routines! Aborting Process!");
        }
        else 
        {
            space_combat.vampiricTypeShipsSystemsRepair(self, ship, successLevel);
        }
        String cefPlayBackHardpoint = space_combat.targetHardpointForCefPlayback(ship);
        playClientEffectObj(self, "clienteffect/space_command/emergency_power_on.cef", ship, cefPlayBackHardpoint);
        float emergencyPowerTime = (rand(5.0f, (float)(CMD_VAMPIRIC_REPAIR_DELAY)));
        return SCRIPT_CONTINUE;
    }
    public int cmdVampiricRepairOther(obj_id self, obj_id target, String params, float defaultTime) throws InterruptedException
    {
        debugServerConsoleMsg(null, "got Vampiric Repair Other Command");
        if (!space_combat.commandTimePassed(self))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id ship = space_transition.getContainingShip(self);
        if (!isIdValid(ship))
        {
            debugServerConsoleMsg(null, "ship obj_id isn't valid. BALLS! YEEargh!!!");
            return SCRIPT_CONTINUE;
        }
        obj_id targetShip = getLookAtTarget(ship);
        if (!space_pilot_command.readyForVampiricRepairOther(ship, self, targetShip))
        {
            string_id strSpam = new string_id("space/space_pilot_command", "cannot_repair_other");
            sendSystemMessage(self, strSpam);
            return SCRIPT_CONTINUE;
        }
        playClientEffectObj(self, "clienteffect/space_command/sys_manipulation.cef", ship, "");
        space_combat.flightDroidVocalize(ship, 1);
        space_combat.initializeCommandTimer(self, CMD_VAMPIRIC_REPAIR_OTHER_DELAY);
        string_id strSpam = new string_id("space/space_interaction", "vampiric_repair");
        string_id strSpam2 = new string_id("space/space_interaction", "vampiric_repair2");
        sendSystemMessage(self, strSpam);
        sendSystemMessage(self, strSpam2);
        int successLevel = space_combat.doPilotCommandSkillCheck(self, "level2command");
        if (successLevel < 1 || successLevel > 6)
        {
            successLevel = 4;
        }
        else if (successLevel == 5)
        {
            sendSystemMessageTestingOnly(self, "Failure in system canibalization sub-routines! Aborting Process!");
        }
        else 
        {
            space_pilot_command.vampiricOtherTypeShipsSystemsRepair(self, ship, targetShip, successLevel);
        }
        String cefPlayBackHardpoint = space_combat.targetHardpointForCefPlayback(ship);
        playClientEffectObj(self, "clienteffect/space_command/emergency_power_on.cef", ship, "");
        float emergencyPowerTime = (rand(5.0f, (float)(CMD_VAMPIRIC_REPAIR_DELAY)));
        return SCRIPT_CONTINUE;
    }
    public int cmdLightBomberStrike(obj_id self, obj_id target, String params, float defaultTime) throws InterruptedException
    {
        debugServerConsoleMsg(null, "got light Bomber Strike Command");
        if (!space_combat.commandTimePassed(self))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id ship = space_transition.getContainingShip(self);
        if (!isIdValid(ship))
        {
            debugServerConsoleMsg(null, "ship obj_id isn't valid. BALLS! YEEargh!!!");
            return SCRIPT_CONTINUE;
        }
        if (!space_combat.readyForBomberStrike(ship, self))
        {
            string_id strSpam = new string_id("space/space_interaction", "bomber_systems_not_ready");
            sendSystemMessage(self, strSpam);
            return SCRIPT_CONTINUE;
        }
        playClientEffectObj(self, "clienteffect/space_command/sys_manipulation.cef", ship, "");
        if (!space_combat.executeLightBomberStrike(ship, self, target, CMD_LIGHT_STRIKE_DELAY))
        {
            debugServerConsoleMsg(null, "combat_ship_player.cmdLightBomberStrike ***** This script has created a FATAL ERROR!");
        }
        return SCRIPT_CONTINUE;
    }
    public int cmdMediumBomberStrike(obj_id self, obj_id target, String params, float defaultTime) throws InterruptedException
    {
        debugServerConsoleMsg(null, "got medium Bomber Strike Command");
        if (!space_combat.commandTimePassed(self))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id ship = space_transition.getContainingShip(self);
        if (!isIdValid(ship))
        {
            debugServerConsoleMsg(null, "ship obj_id isn't valid. BALLS! YEEargh!!!");
            return SCRIPT_CONTINUE;
        }
        if (!space_combat.readyForBomberStrike(ship, self))
        {
            string_id strSpam = new string_id("space/space_interaction", "bomber_systems_not_ready");
            sendSystemMessage(self, strSpam);
            return SCRIPT_CONTINUE;
        }
        playClientEffectObj(self, "clienteffect/space_command/sys_manipulation.cef", ship, "");
        if (!space_combat.executeMediumBomberStrike(ship, self, target, CMD_LIGHT_STRIKE_DELAY))
        {
            debugServerConsoleMsg(null, "combat_ship_player.cmdMediumBomberStrike ***** This script has created a FATAL ERROR!");
        }
        return SCRIPT_CONTINUE;
    }
    public int cmdHeavyBomberStrike(obj_id self, obj_id target, String params, float defaultTime) throws InterruptedException
    {
        debugServerConsoleMsg(null, "got heavy Bomber Strike Command");
        if (!space_combat.commandTimePassed(self))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id ship = space_transition.getContainingShip(self);
        if (!isIdValid(ship))
        {
            debugServerConsoleMsg(null, "ship obj_id isn't valid. BALLS! YEEargh!!!");
            return SCRIPT_CONTINUE;
        }
        if (!space_combat.readyForBomberStrike(ship, self))
        {
            string_id strSpam = new string_id("space/space_interaction", "bomber_systems_not_ready");
            sendSystemMessage(self, strSpam);
            return SCRIPT_CONTINUE;
        }
        playClientEffectObj(self, "clienteffect/space_command/sys_manipulation.cef", ship, "");
        if (!space_combat.executeHeavyBomberStrike(ship, self, target, CMD_LIGHT_STRIKE_DELAY))
        {
            debugServerConsoleMsg(null, "combat_ship_player.cmdHeavyBomberStrike ***** This script has created a FATAL ERROR!");
        }
        return SCRIPT_CONTINUE;
    }
    public int bomberStrikeUnitDestroyed(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id deadBomberId = params.getObjId("deadBomberId");
        if (!utils.hasLocalVar(self, "cmd.bmb.initialStrikePackSize"))
        {
            return SCRIPT_CONTINUE;
        }
        int tauntEmitterSquadId = -1;
        int initialStrikePackageSize = utils.getIntLocalVar(self, "cmd.bmb.initialStrikePackSize");
        int currentStrikePackageSize = (utils.getIntLocalVar(self, "cmd.bmb.currentStrikePackSize") - 1);
        if (currentStrikePackageSize < 1)
        {
            return SCRIPT_CONTINUE;
        }
        else 
        {
            int bomberSquadId = ship_ai.unitGetSquadId(deadBomberId);
            tauntEmitterSquadId = bomberSquadId;
            int numBombers = (ship_ai.squadGetSize(bomberSquadId) - 1);
            utils.setLocalVar(self, "cmd.bmb.currentStrikePackSize", numBombers);
            if (numBombers < (int)(initialStrikePackageSize / 2))
            {
                obj_id[] bomberSquaddyList = ship_ai.squadGetUnitList(tauntEmitterSquadId);
                string_id sid = new string_id("space/space_interaction", "bombers_evacuating");
                prose_package pp = new prose_package();
                pp.stringId = sid;
                dogfightTauntPlayer(bomberSquaddyList[0], self, pp);
                space_combat.strikePackageEvac(self, bomberSquadId, -1);
                return SCRIPT_CONTINUE;
            }
        }
        if (utils.hasLocalVar(self, "cmd.bmb.spamControl"))
        {
            if (getGameTime() > utils.getIntLocalVar(self, "cmd.bmb.spamControl"))
            {
                string_id strSpam = new string_id("space/space_interaction", "strike_bomber_destroyed");
                sendSystemMessage(self, strSpam);
                return SCRIPT_CONTINUE;
            }
        }
        obj_id[] bomberSquaddyList = ship_ai.squadGetUnitList(tauntEmitterSquadId);
        utils.setLocalVar(self, "cmd.bmb.spamControl", (getGameTime() + 10));
        string_id sid = new string_id("space/space_interaction", "bomber_down");
        prose_package pp = new prose_package();
        pp.stringId = sid;
        if(isIdValid(bomberSquaddyList[0]) && isIdValid(self)) dogfightTauntPlayer(bomberSquaddyList[0], self, pp);
        return SCRIPT_CONTINUE;
    }
    public int bomberStrikeEscortUnitDestroyed(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id deadFighterId = params.getObjId("deadFighterId");
        int currentTotalEscortFighters = -1;
        if (utils.hasLocalVar(self, "cmd.bmb.bmbrSquadId"))
        {
            int bomberSquadId = utils.getIntLocalVar(self, "cmd.bmb.bmbrSquadId");
            obj_id[] bomberSquaddyList = ship_ai.squadGetUnitList(bomberSquadId);
            for (int i = 0; i < bomberSquaddyList.length; i++)
            {
                int escortSquadId = utils.getIntLocalVar(bomberSquaddyList[i], "escortSquadId");
                currentTotalEscortFighters += ship_ai.squadGetSize(escortSquadId);
                obj_id[] escortSquaddyList = ship_ai.squadGetUnitList(escortSquadId);
                for (int j = 0; j < escortSquaddyList.length; j++)
                {
                    if (escortSquaddyList[j] == deadFighterId)
                    {
                        int oldEscortUnitCount = utils.getIntLocalVar(bomberSquaddyList[i], "crrntEscrtSqdSz");
                        utils.setLocalVar(bomberSquaddyList[i], "crrntEscrtSqdSz", --oldEscortUnitCount);
                    }
                }
            }
        }
        int initialEscortPackageSize = utils.getIntLocalVar(self, "cmd.bmb.initEscrtPckSize");
        if (currentTotalEscortFighters < (int)(initialEscortPackageSize / 2))
        {
            int bomberSquadId = utils.getIntLocalVar(self, "cmd.bmb.bmbrSquadId");
            space_combat.strikePackageEvac(self, bomberSquadId, -1);
        }
        if (utils.hasLocalVar(self, "cmd.bmb.spamControl"))
        {
            if (getGameTime() > utils.getIntLocalVar(self, "cmd.bmb.spamControl"))
            {
                return SCRIPT_CONTINUE;
            }
        }
        utils.setLocalVar(self, "cmd.bmb.spamControl", (getGameTime() + 10));
        string_id strSpam = new string_id("space/space_interaction", "escort_ship_destroyed");
        sendSystemMessage(self, strSpam);
        return SCRIPT_CONTINUE;
    }
    public int bomberStrikeTargetDestroyed(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id deadBomberTargetId = params.getObjId("deadTargetId");
        if (!utils.hasLocalVar(self, "cmd.bmb.targetSquadId") || !utils.hasLocalVar(self, "cmd.bmb.targetSquadCrrntSz"))
        {
            return SCRIPT_CONTINUE;
        }
        int targetSquadId = utils.getIntLocalVar(self, "cmd.bmb.targetSquadId");
        obj_id[] targetSquaddyList = ship_ai.squadGetUnitList(targetSquadId);
        int targetSquadSize = utils.getIntLocalVar(self, "cmd.bmb.targetSquadCrrntSz") - 1;
        if (!(targetSquadSize > 0))
        {
            if (utils.hasLocalVar(self, "cmd.bmb.bmbrSquadId"))
            {
                int currentStrikePackageSize = (utils.getIntLocalVar(self, "cmd.bmb.currentStrikePackSize"));
                if (currentStrikePackageSize > 0)
                {
                    int bomberSquadId = utils.getIntLocalVar(self, "cmd.bmb.bmbrSquadId");
                    obj_id[] bomberSquaddyList = ship_ai.squadGetUnitList(bomberSquadId);
                    string_id sid = new string_id("space/space_interaction", "bombers_mission_complete");
                    prose_package pp = new prose_package();
                    pp.stringId = sid;
                    dogfightTauntPlayer(bomberSquaddyList[0], self, pp);
                }
                int bomberSquadId = utils.getIntLocalVar(self, "cmd.bmb.bmbrSquadId");
                space_combat.strikePackageEvac(self, bomberSquadId, -1);
            }
            else 
            {
                utils.setLocalVar(self, "cmd.bmb.targetSquadCrrntSz", targetSquadSize);
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int cmdPirateLure(obj_id self, obj_id target, String params, float defaultTime) throws InterruptedException
    {
        debugServerConsoleMsg(null, "got Pirate Lure Command");
        if (!space_combat.commandTimePassed(self))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id ship = space_transition.getContainingShip(self);
        if (!isIdValid(ship))
        {
            debugServerConsoleMsg(null, "ship obj_id isn't valid. BALLS! YEEargh!!!");
            return SCRIPT_CONTINUE;
        }
        obj_id targetShip = getLookAtTarget(ship);
        if (!hasScript(targetShip, "space.combat.combat_ship"))
        {
            return SCRIPT_CONTINUE;
        }
        if (!space_combat.readyForPirateRaid(ship, self))
        {
            string_id strSpam = new string_id("space/space_interaction", "pirate_raid_systems_not_ready");
            sendSystemMessage(self, strSpam);
            return SCRIPT_CONTINUE;
        }
        playClientEffectObj(self, "clienteffect/space_command/sys_manipulation.cef", ship, "");
        if (!space_combat.executePirateRaidOne(ship, self, target, CMD_PIRATE_LURE_DELAY))
        {
            string_id strSpam = new string_id("space/space_interaction", "pirate_raid_systems_not_ready");
            sendSystemMessage(self, strSpam);
            debugServerConsoleMsg(null, "combat_ship_player.cmdPirateLure ****  This script has created a FATAL ERROR!");
        }
        return SCRIPT_CONTINUE;
    }
    public int cmdPirateLureAdvanced(obj_id self, obj_id target, String params, float defaultTime) throws InterruptedException
    {
        debugServerConsoleMsg(null, "got Pirate Lure Command");
        if (!space_combat.commandTimePassed(self))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id ship = space_transition.getContainingShip(self);
        if (!isIdValid(ship))
        {
            debugServerConsoleMsg(null, "ship obj_id isn't valid. BALLS! YEEargh!!!");
            return SCRIPT_CONTINUE;
        }
        obj_id targetShip = getLookAtTarget(ship);
        if (!hasScript(targetShip, "space.combat.combat_ship"))
        {
            return SCRIPT_CONTINUE;
        }
        if (!space_combat.readyForPirateRaid(ship, self))
        {
            string_id strSpam = new string_id("space/space_interaction", "pirate_raid_systems_not_ready");
            sendSystemMessage(self, strSpam);
            return SCRIPT_CONTINUE;
        }
        playClientEffectObj(self, "clienteffect/space_command/sys_manipulation.cef", ship, "");
        if (!space_combat.executePirateRaidTwo(ship, self, target, CMD_PIRATE_LURE_DELAY))
        {
            string_id strSpam = new string_id("space/space_interaction", "pirate_raid_systems_not_ready");
            sendSystemMessage(self, strSpam);
            debugServerConsoleMsg(null, "combat_ship_player.cmdPirateLureAdvanced  ****  This script has created a FATAL ERROR!");
        }
        return SCRIPT_CONTINUE;
    }
    public void pirateCommandCleanup(obj_id self) throws InterruptedException
    {
        if (utils.hasLocalVar(self, "cmd.pirate.squadId"))
        {
            int pirateSquadId = utils.getIntLocalVar(self, "cmd.pirate.squadId");
            space_combat.piratePackageEvac(self, pirateSquadId);
        }
        return;
    }
    public int pirateUnitDestroyed(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id deadPirateId = params.getObjId("deadPirateId");
        if (!utils.hasLocalVar(self, "cmd.pirate.initialSize"))
        {
            return SCRIPT_CONTINUE;
        }
        int initialPiratePackageSize = utils.getIntLocalVar(self, "cmd.pirate.initialSize");
        int pirateSquadId = ship_ai.unitGetSquadId(deadPirateId);
        int numPirates = (ship_ai.squadGetSize(pirateSquadId) - 1);
        if (numPirates < (int)(initialPiratePackageSize / 2))
        {
            space_combat.piratePackageEvac(self, pirateSquadId);
            return SCRIPT_CONTINUE;
        }
        return SCRIPT_CONTINUE;
    }
    public int cmdEnergyPulseOne(obj_id self, obj_id target, String params, float defaultTime) throws InterruptedException
    {
        debugServerConsoleMsg(null, "got Energy Pulse One Command");
        if (!space_combat.commandTimePassed(self))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id ship = space_transition.getContainingShip(self);
        if (!isIdValid(ship))
        {
            string_id strSpam = new string_id("space/space_interaction", "epulse_not_ready");
            sendSystemMessage(self, strSpam);
            debugServerConsoleMsg(null, "ship obj_id isn't valid. BALLS! YEEargh!!!");
            return SCRIPT_CONTINUE;
        }
        if (!space_pilot_command.readyForEnergyPulse(ship, self))
        {
            string_id strSpam = new string_id("space/space_interaction", "epulse_not_ready");
            sendSystemMessage(self, strSpam);
            return SCRIPT_CONTINUE;
        }
        playClientEffectObj(self, "clienteffect/space_command/sys_manipulation.cef", ship, "");
        String cmdlevel = "e_pulse_one";
        if (!space_pilot_command.executeEnergyPulse(self, ship, cmdlevel))
        {
            string_id strSpam = new string_id("space/space_interaction", "epulse_not_ready");
            sendSystemMessage(self, strSpam);
            debugServerConsoleMsg(null, "combat_ship_player.cmdEnergyPulseOne  ****  This script has created a FATAL ERROR!");
        }
        space_combat.initializeCommandTimer(self, CMD_ENERGY_PULSE_ONE_DELAY);
        return SCRIPT_CONTINUE;
    }
    public int cmdEnergyPulseTwo(obj_id self, obj_id target, String params, float defaultTime) throws InterruptedException
    {
        debugServerConsoleMsg(null, "got Energy Pulse Two Command");
        if (!space_combat.commandTimePassed(self))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id ship = space_transition.getContainingShip(self);
        if (!isIdValid(ship))
        {
            string_id strSpam = new string_id("space/space_interaction", "epulse_not_ready");
            sendSystemMessage(self, strSpam);
            debugServerConsoleMsg(null, "ship obj_id isn't valid. BALLS! YEEargh!!!");
            return SCRIPT_CONTINUE;
        }
        if (!space_pilot_command.readyForEnergyPulse(ship, self))
        {
            string_id strSpam = new string_id("space/space_interaction", "epulse_not_ready");
            sendSystemMessage(self, strSpam);
            return SCRIPT_CONTINUE;
        }
        playClientEffectObj(self, "clienteffect/space_command/sys_manipulation.cef", ship, "");
        String cmdlevel = "e_pulse_two";
        if (!space_pilot_command.executeEnergyPulse(self, ship, cmdlevel))
        {
            string_id strSpam = new string_id("space/space_interaction", "epulse_not_ready");
            sendSystemMessage(self, strSpam);
            debugServerConsoleMsg(null, "combat_ship_player.cmdEnergyPulseTwo  ****  This script has created a FATAL ERROR!");
        }
        space_combat.initializeCommandTimer(self, CMD_ENERGY_PULSE_TWO_DELAY);
        return SCRIPT_CONTINUE;
    }
    public int cmdEnergyPulseThree(obj_id self, obj_id target, String params, float defaultTime) throws InterruptedException
    {
        debugServerConsoleMsg(null, "got Energy Pulse Three Command");
        if (!space_combat.commandTimePassed(self))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id ship = space_transition.getContainingShip(self);
        if (!isIdValid(ship))
        {
            string_id strSpam = new string_id("space/space_interaction", "epulse_not_ready");
            sendSystemMessage(self, strSpam);
            debugServerConsoleMsg(null, "ship obj_id isn't valid. BALLS! YEEargh!!!");
            return SCRIPT_CONTINUE;
        }
        if (!space_pilot_command.readyForEnergyPulse(ship, self))
        {
            string_id strSpam = new string_id("space/space_interaction", "epulse_not_ready");
            sendSystemMessage(self, strSpam);
            return SCRIPT_CONTINUE;
        }
        playClientEffectObj(self, "clienteffect/space_command/sys_manipulation.cef", ship, "");
        String cmdlevel = "e_pulse_three";
        if (!space_pilot_command.executeEnergyPulse(self, ship, cmdlevel))
        {
            string_id strSpam = new string_id("space/space_interaction", "epulse_not_ready");
            sendSystemMessage(self, strSpam);
            debugServerConsoleMsg(null, "combat_ship_player.cmdEnergyPulseThree  ****  This script has created a FATAL ERROR!");
        }
        space_combat.initializeCommandTimer(self, CMD_ENERGY_PULSE_THREE_DELAY);
        return SCRIPT_CONTINUE;
    }
    public int cmdNebulaBlast(obj_id self, obj_id target, String params, float defaultTime) throws InterruptedException
    {
        debugServerConsoleMsg(null, "got nebula blast Command");
        if (!space_combat.commandTimePassed(self))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id ship = getPilotedShip(self);
        if (!isIdValid(ship))
        {
            debugServerConsoleMsg(null, "ship obj_id isn't valid. BALLS! YEEargh!!!");
            return SCRIPT_CONTINUE;
        }
        if (!space_pilot_command.readyForNebulaBlast(ship, self))
        {
            return SCRIPT_CONTINUE;
        }
        playClientEffectObj(self, "clienteffect/space_command/sys_manipulation.cef", ship, "");
        float currentSpeed = getShipCurrentSpeed(ship);
        if (currentSpeed < 20.0f)
        {
            playClientEffectObj(self, "clienteffect/space_command/aftershock_small.cef", ship, "");
        }
        else 
        {
            playClientEffectObj(self, "clienteffect/space_command/aftershock_medium.cef", ship, "");
        }
        playClientEffectObj(self, "clienteffect/space_command/cbt_nebulae_fire.cef", ship, "");
        space_pilot_command.doNebulaBlast(self, ship);
        space_combat.initializeCommandTimer(self, CMD_NEBULA_BLAST_DELAY);
        return SCRIPT_CONTINUE;
    }
    public int cmdJstartOne(obj_id self, obj_id target, String params, float defaultTime) throws InterruptedException
    {
        debugServerConsoleMsg(null, "got Jstart One Command");
        if (!space_combat.commandTimePassed(self))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id ship = space_transition.getContainingShip(self);
        if (!isIdValid(ship))
        {
            debugServerConsoleMsg(null, "ship obj_id isn't valid. BALLS! YEEargh!!!");
            return SCRIPT_CONTINUE;
        }
        if (!isIdValid(target))
        {
            string_id strSpam = new string_id("space/space_interaction", "jstart_no_target");
            space_utils.sendSystemMessageShip(ship, strSpam, true, false, false, true);
            return SCRIPT_CONTINUE;
        }
        String cmdlevel = "jstart_one";
        if (!space_pilot_command.readyForJumpStart(ship, self, target, cmdlevel))
        {
            string_id strSpam = new string_id("space/space_interaction", "Jstart_not_ready");
            space_utils.sendSystemMessageShip(ship, strSpam, true, false, false, true);
            return SCRIPT_CONTINUE;
        }
        playClientEffectObj(self, "clienteffect/space_command/sys_manipulation.cef", ship, "");
        if (!space_pilot_command.executeJstart(self, ship, target, cmdlevel))
        {
            sendSystemMessageTestingOnly(self, "This script has created a FATAL ERROR!");
        }
        space_combat.initializeCommandTimer(self, CMD_JUMP_START_ONE_DELAY);
        return SCRIPT_CONTINUE;
    }
    public int cmdJstartTwo(obj_id self, obj_id target, String params, float defaultTime) throws InterruptedException
    {
        debugServerConsoleMsg(null, "got Jstart Two Command");
        if (!space_combat.commandTimePassed(self))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id ship = space_transition.getContainingShip(self);
        if (!isIdValid(ship))
        {
            debugServerConsoleMsg(null, "ship obj_id isn't valid. BALLS! YEEargh!!!");
            return SCRIPT_CONTINUE;
        }
        if (!isIdValid(target))
        {
            string_id strSpam = new string_id("space/space_interaction", "jstart_no_target");
            space_utils.sendSystemMessageShip(ship, strSpam, true, false, false, true);
            return SCRIPT_CONTINUE;
        }
        String cmdlevel = "jstart_two";
        if (!space_pilot_command.readyForJumpStart(ship, self, target, cmdlevel))
        {
            string_id strSpam = new string_id("space/space_interaction", "jstart_not_ready");
            space_utils.sendSystemMessageShip(ship, strSpam, true, false, false, true);
            return SCRIPT_CONTINUE;
        }
        playClientEffectObj(self, "clienteffect/space_command/sys_manipulation.cef", ship, "");
        if (!space_pilot_command.executeJstart(self, ship, target, cmdlevel))
        {
            sendSystemMessageTestingOnly(self, "This script has created a FATAL ERROR!");
        }
        space_combat.initializeCommandTimer(self, CMD_JUMP_START_TWO_DELAY);
        return SCRIPT_CONTINUE;
    }
    public int cmdJstartThree(obj_id self, obj_id target, String params, float defaultTime) throws InterruptedException
    {
        debugServerConsoleMsg(null, "got Jstart Three Command");
        if (!space_combat.commandTimePassed(self))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id ship = space_transition.getContainingShip(self);
        if (!isIdValid(ship))
        {
            debugServerConsoleMsg(null, "ship obj_id isn't valid. BALLS! YEEargh!!!");
            return SCRIPT_CONTINUE;
        }
        if (!isIdValid(target))
        {
            string_id strSpam = new string_id("space/space_interaction", "jstart_no_target");
            space_utils.sendSystemMessageShip(ship, strSpam, true, false, false, true);
            return SCRIPT_CONTINUE;
        }
        String cmdlevel = "jstart_three";
        if (!space_pilot_command.readyForJumpStart(ship, self, target, cmdlevel))
        {
            string_id strSpam = new string_id("space/space_interaction", "jstart_not_ready");
            space_utils.sendSystemMessageShip(ship, strSpam, true, false, false, true);
            return SCRIPT_CONTINUE;
        }
        playClientEffectObj(self, "clienteffect/space_command/sys_manipulation.cef", ship, "");
        if (!space_pilot_command.executeJstart(self, ship, target, cmdlevel))
        {
            sendSystemMessageTestingOnly(self, "This script has created a FATAL ERROR!");
        }
        space_combat.initializeCommandTimer(self, CMD_JUMP_START_THREE_DELAY);
        return SCRIPT_CONTINUE;
    }
    public int cmdInSpaceRepair(obj_id self, obj_id target, String params, float defaultTime) throws InterruptedException
    {
        debugServerConsoleMsg(null, "got Repair Command");
        String command_type = "repair";
        obj_id ship = space_transition.getContainingShip(self);
        if (!isIdValid(ship))
        {
            debugServerConsoleMsg(null, "COMBAT_SHIP_PLAYER.cmdInSpaceRepair - ship obj_id isn't valid. BALLS! YEEargh!!!");
            return SCRIPT_CONTINUE;
        }
        if (utils.hasLocalVar(self, "cmd.repair.otw"))
        {
            debugServerConsoleMsg(null, "COMBAT_SHIP_PLAYER.cmdInSpaceRepair - hasLocalVar(self, cmd.repair.otw! Calling into SPACE_PILOT_COMMAND.confirmRepairs!");
            space_pilot_command.confirmRepairs(self, ship, command_type);
            return SCRIPT_CONTINUE;
        }
        if (space_combat.commandTimePassed(self))
        {
            playClientEffectObj(self, "clienteffect/space_command/sys_manipulation.cef", ship, "");
            if (space_pilot_command.prepInSpaceRepair(self, ship, command_type))
            {
                space_combat.initializeCommandTimer(self, CMD_REPAIR_SHIP_DELAY);
            }
            else 
            {
                debugServerConsoleMsg(null, "COMBAT_SHIP_PLAYER.cmdInSpaceRepair - prepInSpaceRepair failed!");
            }
        }
        else 
        {
            debugServerConsoleMsg(null, "COMBAT_SHIP_PLAYER.cmdInSpaceRepair - commandTimePassed failed");
        }
        return SCRIPT_CONTINUE;
    }
    public int escapePod(obj_id self, obj_id target, String params, float defaultTime) throws InterruptedException
    {
        if (!(params.equals("yes")))
        {
            string_id strSpam = new string_id("space/space_interaction", "say_yes");
            sendSystemMessage(self, strSpam);
            return SCRIPT_CONTINUE;
        }
        else 
        {
            obj_id objShip = space_transition.getContainingShip(self);
            if (space_utils.isShipWithInterior(objShip))
            {
                string_id strSpam = new string_id("space/space_interaction", "use_escape_hatch");
                sendSystemMessage(self, strSpam);
                return SCRIPT_CONTINUE;
            }
            obj_id objOwner = getOwner(objShip);
            if (objOwner != self)
            {
                string_id strSpam = new string_id("space/space_interaction", "ejecting");
                sendSystemMessage(self, strSpam);
                space_transition.teleportPlayerToLaunchLoc(self);
                return SCRIPT_CONTINUE;
            }
            if (!utils.hasLocalVar(objShip, "intEjecting"))
            {
                utils.setLocalVar(objShip, "intEjecting", 1);
                dictionary dctParams = new dictionary();
                dctParams.put("objShip", objShip);
                space_combat.clearHyperspace(objShip);
                messageTo(objShip, "megaDamage", dctParams, 2, false);
            }
            else 
            {
                string_id strSpam = new string_id("space/space_interaction", "already_ejecting");
                sendSystemMessage(self, strSpam);
            }
            return SCRIPT_CONTINUE;
        }
    }
    public int requestSpaceTrainer(obj_id self, obj_id target, String params, float defaultTime) throws InterruptedException
    {
        queueCommand(self, (80588750), space_transition.getContainingShip(self), "0   ", COMMAND_PRIORITY_FRONT);
        return SCRIPT_CONTINUE;
    }
    public int cmdSpaceFaction(obj_id self, obj_id target, String params, float defaultTime) throws InterruptedException
    {
        obj_id ship = space_transition.getContainingShip(self);
        if (!isIdValid(ship))
        {
            return SCRIPT_CONTINUE;
        }
        String pilotBranch = space_flags.getSpaceTrack(self);
        if (pilotBranch.equals(space_flags.PRIVATEER_TATOOINE))
        {
            sendSystemMessage(self, "You are a member of the Smuggler's Alliance.", null);
        }
        if (pilotBranch.equals(space_flags.PRIVATEER_NABOO))
        {
            sendSystemMessage(self, "You are a member of the RSF Squadron.", null);
        }
        if (pilotBranch.equals(space_flags.PRIVATEER_CORELLIA))
        {
            sendSystemMessage(self, "You are a member of the Corsec Squadron.", null);
        }
        if (pilotBranch.equals(space_flags.IMPERIAL_TATOOINE))
        {
            sendSystemMessage(self, "You are a member of the Imperial Storm Squadron.", null);
        }
        if (pilotBranch.equals(space_flags.IMPERIAL_NABOO))
        {
            sendSystemMessage(self, "You are a member of the Black Epsilon Squadron.", null);
        }
        if (pilotBranch.equals(space_flags.IMPERIAL_CORELLIA))
        {
            sendSystemMessage(self, "You are a member of the Imperial Inquisition.", null);
        }
        if (pilotBranch.equals(space_flags.REBEL_TATOOINE))
        {
            sendSystemMessage(self, "You are a member of the Crimson Phoenix Squadron.", null);
        }
        if (pilotBranch.equals(space_flags.REBEL_NABOO))
        {
            sendSystemMessage(self, "You are a member of the Vortex Squadron.", null);
        }
        if (pilotBranch.equals(space_flags.REBEL_CORELLIA))
        {
            sendSystemMessage(self, "You are a member of the Arkon's Havok Squad.", null);
        }
        String zone = getCurrentSceneName();
        if (hasObjVar(ship, "spaceFaction.FactionOverride") && (hasObjVar(ship, "spaceFaction.overt") || zone.equals("space_heavy1")))
        {
            int faction = getIntObjVar(ship, "spaceFaction.FactionOverride");
            if (faction == (370444368))
            {
                sendSystemMessage(self, "Your ship is registered as Special Forces with the Rebellion.", null);
            }
            if (faction == (-615855020))
            {
                sendSystemMessage(self, "Your ship is registered as Special Forces with the Empire.", null);
            }
        }
        else if (hasObjVar(ship, "spaceFaction.FactionOverride") && !hasObjVar(ship, "spaceFaction.overt"))
        {
            int faction = getIntObjVar(ship, "spaceFaction.FactionOverride");
            if (faction == (370444368))
            {
                sendSystemMessage(self, "Your ship is registered as Combatant with the Rebellion.", null);
            }
            if (faction == (-615855020))
            {
                sendSystemMessage(self, "Your ship is registered as Combatant with the Empire.", null);
            }
        }
        else 
        {
            if (space_flags.isRebelPilot(self))
            {
                sendSystemMessage(self, "Your ship is registered as Combatant with the Rebellion.", null);
            }
            if (space_flags.isImperialPilot(self))
            {
                sendSystemMessage(self, "Your ship is registered as Combatant with the Empire.", null);
            }
            if (space_flags.isNeutralPilot(self))
            {
                sendSystemMessage(self, "Your ship is registered as a privateer.", null);
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int cmdSpacepvprebel(obj_id self, obj_id target, String params, float defaultTime) throws InterruptedException
    {
        obj_id ship = space_transition.getContainingShip(self);
        if (!isIdValid(ship))
        {
            return SCRIPT_CONTINUE;
        }
        String template = getTemplateName(ship);
        if (template != null && template.endsWith("_yacht.iff"))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id pilot = getPilotId(ship);
        if (self != pilot)
        {
            sendSystemMessage(self, "You must be the ship's pilot to use this command.", null);
            return SCRIPT_CONTINUE;
        }
        String zone = getCurrentSceneName();
        if (zone.equals("space_heavy1"))
        {
            sendSystemMessage(self, "You cannot use this command in Deep Space.", null);
            return SCRIPT_CONTINUE;
        }
        if (space_flags.isImperialPilot(self) || factions.isImperial(self))
        {
            sendSystemMessage(self, "Factional restrictions prevent you from using this command.", null);
            return SCRIPT_CONTINUE;
        }
        if (hasObjVar(ship, "spaceFaction.overt"))
        {
            sendSystemMessage(self, "You are already declared as a Special Forces pilot.", null);
            return SCRIPT_CONTINUE;
        }
        if (utils.hasScriptVar(ship, "space.goOvert"))
        {
            sendSystemMessage(self, "You are already in the process of being registered as Special Forces.", null);
            return SCRIPT_CONTINUE;
        }
        CustomerServiceLog("space_commands", "Player " + self + " in ship " + ship + " is going Rebel Special Forces");
        messageTo(self, "spacePvpRebel", null, 0, false);
        return SCRIPT_CONTINUE;
    }
    public int cmdSpacepvpimperial(obj_id self, obj_id target, String params, float defaultTime) throws InterruptedException
    {
        obj_id ship = space_transition.getContainingShip(self);
        if (!isIdValid(ship))
        {
            return SCRIPT_CONTINUE;
        }
        String template = getTemplateName(ship);
        if (template != null && template.endsWith("_yacht.iff"))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id pilot = getPilotId(ship);
        if (self != pilot)
        {
            sendSystemMessage(self, "You must be the ship's pilot to use this command.", null);
            return SCRIPT_CONTINUE;
        }
        String zone = getCurrentSceneName();
        if (zone.equals("space_heavy1"))
        {
            sendSystemMessage(self, "You cannot use this command in Deep Space.", null);
            return SCRIPT_CONTINUE;
        }
        if (space_flags.isRebelPilot(self) || factions.isRebel(self))
        {
            sendSystemMessage(self, "Factional restrictions prevent you from using this command.", null);
            return SCRIPT_CONTINUE;
        }
        if (hasObjVar(ship, "spaceFaction.overt"))
        {
            sendSystemMessage(self, "You are already declared as a Special Forces pilot.", null);
            return SCRIPT_CONTINUE;
        }
        if (utils.hasScriptVar(ship, "space.goOvert"))
        {
            sendSystemMessage(self, "You are already in the process of being registered as Special Forces.", null);
            return SCRIPT_CONTINUE;
        }
        CustomerServiceLog("space_commands", "Player " + self + " in ship " + ship + " is going Imperial Special Forces");
        messageTo(self, "spacePvpImperial", null, 0, false);
        return SCRIPT_CONTINUE;
    }
    public int cmdSpaceRebelHelper(obj_id self, obj_id target, String params, float defaultTime) throws InterruptedException
    {
        obj_id ship = space_transition.getContainingShip(self);
        if (!isIdValid(ship))
        {
            return SCRIPT_CONTINUE;
        }
        String template = getTemplateName(ship);
        if (template != null && template.endsWith("_yacht.iff"))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id pilot = getPilotId(ship);
        if (self != pilot)
        {
            sendSystemMessage(self, "You must be the ship's pilot to use this command.", null);
            return SCRIPT_CONTINUE;
        }
        String zone = getCurrentSceneName();
        if (zone.equals("space_heavy1"))
        {
            sendSystemMessage(self, "You cannot use this command in Deep Space.", null);
            return SCRIPT_CONTINUE;
        }
        if (!space_flags.isNeutralPilot(self) || factions.isImperial(self))
        {
            sendSystemMessage(self, "Factional restrictions prevent you from using this command.", null);
            return SCRIPT_CONTINUE;
        }
        if (hasObjVar(ship, "spaceFaction.FactionOverride"))
        {
            sendSystemMessage(self, "You have already declared your allegiance.", null);
            return SCRIPT_CONTINUE;
        }
        CustomerServiceLog("space_commands", "Player " + self + " in ship " + ship + " is going Rebel Factional Helper");
        messageTo(self, "spaceRebelHelper", null, 0, false);
        return SCRIPT_CONTINUE;
    }
    public int cmdSpaceImperialHelper(obj_id self, obj_id target, String params, float defaultTime) throws InterruptedException
    {
        obj_id ship = space_transition.getContainingShip(self);
        if (!isIdValid(ship))
        {
            return SCRIPT_CONTINUE;
        }
        String template = getTemplateName(ship);
        if (template != null && template.endsWith("_yacht.iff"))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id pilot = getPilotId(ship);
        if (self != pilot)
        {
            sendSystemMessage(self, "You must be the ship's pilot to use this command.", null);
            return SCRIPT_CONTINUE;
        }
        String zone = getCurrentSceneName();
        if (zone.equals("space_heavy1"))
        {
            sendSystemMessage(self, "You cannot use this command in Deep Space.", null);
            return SCRIPT_CONTINUE;
        }
        if (!space_flags.isNeutralPilot(self) || factions.isRebel(self))
        {
            sendSystemMessage(self, "Factional restrictions prevent you from using this command.", null);
            return SCRIPT_CONTINUE;
        }
        if (hasObjVar(ship, "spaceFaction.FactionOverride"))
        {
            sendSystemMessage(self, "You have already declared your allegiance.", null);
            return SCRIPT_CONTINUE;
        }
        CustomerServiceLog("space_commands", "Player " + self + " in ship " + ship + " is going Imperial Factional Helper");
        messageTo(self, "spaceImperialHelper", null, 0, false);
        return SCRIPT_CONTINUE;
    }
    public int ionizeArea(obj_id self, dictionary params) throws InterruptedException
    {
        int radius = 100;
        obj_id[] objects = getAllObjectsWithScript(getLocation(self), radius, "space.combat.combat_ship_player");
        if (objects == null && objects.length <= 0)
        {
            return SCRIPT_CONTINUE;
        }
        dictionary dict = new dictionary();
        dict.put("ionizeObjects", objects);
        messageTo(self, "ionizeTarget", dict, 3, false);
        return SCRIPT_CONTINUE;
    }
    public int ionizeTarget(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id[] objects = params.getObjIdArray("ionizeObjects");
        if (objects == null && objects.length <= 0)
        {
            return SCRIPT_CONTINUE;
        }
        for (int i = 0; i < objects.length; i++)
        {
            if (!isIdValid(objects[i]) || !exists(objects[i]))
            {
                continue;
            }
            if (space_utils.isPlayerControlledShip(objects[i]) && pvpCanAttack(objects[i], self) && getDistance(self, objects[i]) < 100)
            {
                space_pilot_command.doSubSystemStun(objects[i], ship_chassis_slot_type.SCST_engine, 10);
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int spaceTaunt(obj_id self, obj_id target, String params, float defaultTime) throws InterruptedException
    {
        final int TAUNT_DELAY = 10;
        if (params == null)
        {
            return SCRIPT_CONTINUE;
        }
        if (params.equals(""))
        {
            string_id strSpam = new string_id("space/space_interaction", "blank_taunt");
            sendSystemMessage(self, strSpam);
            return SCRIPT_CONTINUE;
        }
        int intTauntTime = utils.getIntLocalVar(self, "intTauntTime");
        int intDifference = getGameTime() - intTauntTime;
        if (intDifference < TAUNT_DELAY)
        {
            string_id strSpam = new string_id("space/space_interaction", "wait_for_taunt");
            sendSystemMessage(self, strSpam);
            return SCRIPT_CONTINUE;
        }
        if (params.length() > 64)
        {
            string_id strSpam = new string_id("space/space_interaction", "taunt_too_long");
            sendSystemMessage(self, strSpam);
            return SCRIPT_CONTINUE;
        }
        return SCRIPT_CONTINUE;
    }
    public int cmdInSpaceReload(obj_id self, obj_id target, String params, float defaultTime) throws InterruptedException
    {
        debugServerConsoleMsg(null, "got Reload Command");
        String command_type = "reload";
        obj_id ship = space_transition.getContainingShip(self);
        if (!isIdValid(ship))
        {
            debugServerConsoleMsg(null, "COMBAT_SHIP_PLAYER.cmdInSpaceReload - ship obj_id isn't valid. BALLS! YEEargh!!!");
            return SCRIPT_CONTINUE;
        }
        if (utils.hasLocalVar(self, "cmd.repair.otw"))
        {
            debugServerConsoleMsg(null, "COMBAT_SHIP_PLAYER.cmdInSpaceReload - hasLocalVar(self, cmd.repair.otw! Calling into SPACE_PILOT_COMMAND.confirmRepairs!");
            space_pilot_command.confirmRepairs(self, ship, command_type);
            return SCRIPT_CONTINUE;
        }
        if (space_combat.commandTimePassed(self))
        {
            playClientEffectObj(self, "clienteffect/space_command/sys_manipulation.cef", ship, "");
            string_id strSpam = new string_id("space/space_pilot_command", "inspacereload_notice");
            sendSystemMessage(self, strSpam);
            if (space_pilot_command.prepInSpaceRepair(self, ship, command_type))
            {
                space_combat.initializeCommandTimer(self, CMD_REPAIR_SHIP_DELAY);
            }
            else 
            {
                debugServerConsoleMsg(null, "COMBAT_SHIP_PLAYER.cmdInSpaceReload - prepInSpaceRepair failed!");
            }
        }
        else 
        {
            debugServerConsoleMsg(null, "COMBAT_SHIP_PLAYER.cmdInSpaceReload - commandTimePassed failed");
        }
        return SCRIPT_CONTINUE;
    }
    public int cmdInSpaceRepairAndReload(obj_id self, obj_id target, String params, float defaultTime) throws InterruptedException
    {
        debugServerConsoleMsg(null, "got repairAndReload Command");
        String command_type = "repairAndReload";
        obj_id ship = space_transition.getContainingShip(self);
        if (!isIdValid(ship))
        {
            debugServerConsoleMsg(null, "COMBAT_SHIP_PLAYER.cmdInSpaceRepairAndReload - ship obj_id isn't valid. BALLS! YEEargh!!!");
            return SCRIPT_CONTINUE;
        }
        if (utils.hasLocalVar(self, "cmd.repair.otw"))
        {
            debugServerConsoleMsg(null, "COMBAT_SHIP_PLAYER.cmdInSpaceRepairAndReload - hasLocalVar(self, cmd.repair.otw! Calling into SPACE_PILOT_COMMAND.confirmRepairs!");
            space_pilot_command.confirmRepairs(self, ship, command_type);
            return SCRIPT_CONTINUE;
        }
        if (space_combat.commandTimePassed(self))
        {
            playClientEffectObj(self, "clienteffect/space_command/sys_manipulation.cef", ship, "");
            string_id strSpam = new string_id("space/space_pilot_command", "inspacereload_notice");
            sendSystemMessage(self, strSpam);
            if (space_pilot_command.prepInSpaceRepair(self, ship, command_type))
            {
                space_combat.initializeCommandTimer(self, CMD_REPAIR_SHIP_DELAY);
            }
            else 
            {
                debugServerConsoleMsg(null, "COMBAT_SHIP_PLAYER.cmdInSpaceRepairAndReload - prepInSpaceRepair failed!");
            }
        }
        else 
        {
            debugServerConsoleMsg(null, "COMBAT_SHIP_PLAYER.cmdInSpaceRepairAndReload - commandTimePassed failed");
        }
        return SCRIPT_CONTINUE;
    }
    public int repairShipComponentInSlot(obj_id self, obj_id target, String params, float defaultTime) throws InterruptedException
    {
        String[] paramsArray = split(params, ' ');
        if (paramsArray.length < 2)
        {
            return SCRIPT_CONTINUE;
        }
        obj_id shipId = utils.stringToObjId(paramsArray[0]);
        int intSlot = utils.stringToInt(paramsArray[1]);
        if (intSlot == -1)
        {
            LOG("space", "BAD SLOT");
            return SCRIPT_CONTINUE;
        }
        LOG("space", "intSlot is " + intSlot);
        Vector objRepairKits = space_crafting.getRepairKitsForItem(self, intSlot);
        if ((objRepairKits == null) || (objRepairKits.size() < 1))
        {
            string_id strSpam = new string_id("space/space_interaction", "no_kits");
            sendSystemMessage(self, strSpam);
            return SCRIPT_CONTINUE;
        }
        space_crafting.repairComponentOnShip(intSlot, objRepairKits, self, shipId);
        return SCRIPT_CONTINUE;
    }
    public int OnNewbieTutorialResponse(obj_id self, String strAction) throws InterruptedException
    {
        if (!isSpaceScene())
        {
            return SCRIPT_CONTINUE;
        }
        if (strAction.equals("clientReady"))
        {
            obj_id objCommTarget = utils.getObjIdScriptVar(self, "objCommTarget");
            if (isIdValid(objCommTarget))
            {
                utils.removeScriptVar(self, "objCommTarget");
                queueCommand(self, (80588750), objCommTarget, "0   ", COMMAND_PRIORITY_FRONT);
            }
            obj_id objShip = space_transition.getContainingShip(self);
            if (space_utils.isShip(objShip))
            {
                space_transition.doAIImmunityCheck(objShip);
            }
            if (utils.hasScriptVar(self, "intNewbieZoneLaunch"))
            {
                utils.removeScriptVar(self, "intNewbieZoneLaunch");
                location locTest = getLocation(self);
                int intIndex = locTest.area.indexOf("_2");
                if (intIndex > -1)
                {
                    string_id strSpam = new string_id("space/space_interaction", "launch_" + locTest.area);
                    sendSystemMessage(self, strSpam);
                }
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int dumpZoneInformation(obj_id self, obj_id target, String params, float defaultTime) throws InterruptedException
    {
        String strFileName = "";
        if (!params.equals(""))
        {
            strFileName = params + ".txt";
        }
        obj_id objShip = space_transition.getContainingShip(self);
        if (!isIdValid(objShip))
        {
            objShip = self;
        }
        location locTest = getLocation(objShip);
        obj_id[] objObjects = getAllObjectsWithScript(locTest, 320000, "space.combat.combat_ship");
        String strOutputToFile = "";
        for (int intI = 0; intI < objObjects.length; intI++)
        {
            if (!space_utils.isPlayerControlledShip(objObjects[intI]))
            {
                String strTest = "";
                strTest += "OBJECT_ID: " + objObjects[intI] + ", ";
                strTest += "TYPE: " + getStringObjVar(objObjects[intI], "ship.shipName");
                strTest += ", LOCATION " + getLocation(objObjects[intI]);
                if (!strFileName.equals(""))
                {
                    strOutputToFile += strTest + "\r\n";
                }
                debugConsoleMsg(self, strTest);
                sendSystemMessageTestingOnly(self, strTest);
            }
        }
        objObjects = getAllObjectsWithScript(locTest, 320000, "space.combat.combat_ship_capital");
        for (int intI = 0; intI < objObjects.length; intI++)
        {
            if (!space_utils.isPlayerControlledShip(objObjects[intI]))
            {
                String strTest = "";
                strTest += "OBJECT_ID: " + objObjects[intI] + ", ";
                strTest += "TYPE: " + getStringObjVar(objObjects[intI], "ship.shipName");
                strTest += ", LOCATION " + getLocation(objObjects[intI]);
                if (!strFileName.equals(""))
                {
                    strOutputToFile += strTest + "\r\n";
                }
                debugConsoleMsg(self, strTest);
                sendSystemMessageTestingOnly(self, strTest);
            }
        }
        debugConsoleMsg(self, strOutputToFile);
        if (!strFileName.equals(""))
        {
            saveTextOnClient(self, strFileName, strOutputToFile);
            sendSystemMessageTestingOnly(self, "Saved information to " + strFileName);
        }
        sendSystemMessageTestingOnly(self, "Dumped");
        return SCRIPT_CONTINUE;
    }
    public int OnImmediateLogout(obj_id self) throws InterruptedException
    {
        obj_id ship = space_transition.getContainingShip(self);
        if (isIdValid(ship))
        {
            if (space_utils.isShipWithInterior(ship) && space_utils.isInStation(self))
            {
                queueCommand(self, (-1465754503), self, "", COMMAND_PRIORITY_FRONT);
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int OnSpaceEjectPlayerFromShip(obj_id self) throws InterruptedException
    {
        obj_id ship = space_transition.getContainingShip(self);
        if (isIdValid(ship) && getOwner(ship) == self)
        {
            space_transition.handleLogout(self);
        }
        space_transition.teleportPlayerToLaunchLoc(self);
        return SCRIPT_CONTINUE;
    }
    public int toggleCombatTaunts(obj_id self, obj_id target, String params, float defaultTime) throws InterruptedException
    {
        if (hasObjVar(self, "jtl.combatTauntsDisabled"))
        {
            removeObjVar(self, "jtl.combatTauntsDisabled");
            string_id strSpam = new string_id("space/space_interaction", "taunt_on");
            sendSystemMessage(self, strSpam);
        }
        else 
        {
            setObjVar(self, "jtl.combatTauntsDisabled", 1);
            string_id strSpam = new string_id("space/space_interaction", "taunt_off");
            sendSystemMessage(self, strSpam);
        }
        return SCRIPT_CONTINUE;
    }
    public int OnCommitDroidProgramCommands(obj_id self, obj_id objControlDevice, String[] strCommands, obj_id[] objChipsToAdd, obj_id[] objChipsToRemove) throws InterruptedException
    {
        obj_id objDatapad = utils.getDatapad(objControlDevice);
        if(getVolumeFree(objDatapad) <= 0 || !isIdValid(objDatapad) || !exists(objDatapad) || objDatapad == null || objDatapad.equals(obj_id.NULL_ID)){
            debugSpeakMsg(self, "The control device datapad was full or not found - not creating droid command.");
            return SCRIPT_CONTINUE;
        }
        if (objChipsToRemove != null)
        {
            for (int intI = 0; intI < objChipsToRemove.length; intI++)
            {
                space_combat.destroyObject(objChipsToRemove[intI]);
            }
        }
        if (strCommands != null)
        {
            for (int intI = 0; intI < strCommands.length; intI++)
            {
                space_combat.addModuleToDatapad(strCommands[intI], objDatapad);
            }
        }
        if (objChipsToAdd != null)
        {
            for (int intI = 0; intI < objChipsToAdd.length; intI++)
            {
                space_combat.addModuleToDatapad(objChipsToAdd[intI], objDatapad);
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int commandTimerTimeout(obj_id self, dictionary params) throws InterruptedException
    {
        string_id strSpam = new string_id("space/space_interaction", "pilot_command_timer_compelete");
        sendSystemMessage(self, strSpam);
        return SCRIPT_CONTINUE;
    }
    public int doDelayedPilot(obj_id self, dictionary params) throws InterruptedException
    {
        space_transition.handlePotentialSceneChange(self);
        obj_id objShip = params.getObjId("objShip");
        obj_id objSlot = space_transition.findPilotSlotObjectForShip(self, objShip);
        boolean boolTest = space_transition.pilotShip(self, objSlot);
        transform trTest = params.getTransform("trTest");
        location locTest = params.getLocation("locTest");
        setLocation(objShip, locTest);
        LOG("space", "set player to " + locTest);
        setTransform_o2p(objShip, trTest);
        LOG("space", "transform is " + trTest);
        return SCRIPT_CONTINUE;
    }
}
