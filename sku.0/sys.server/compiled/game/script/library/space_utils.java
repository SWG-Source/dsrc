package script.library;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.script_entry;
import script.library.space_content;
import script.library.prose;
import script.library.space_combat;
import script.library.space_transition;
import java.lang.Math;
import java.util.Arrays;
import java.util.Vector;

public class space_utils extends script.base_script
{
    public space_utils()
    {
    }
    public static final int POB_LOOT_BOX_CAPACITY = 75;
    public static final string_id SID_NO_SHIP_CERTIFICATION = new string_id("space/space_interaction", "no_ship_certification");
    public static final string_id SID_NO_WORKING_SHIP = new string_id("space/space_interaction", "no_working_ship");
    public static final string_id SID_NO_SHIP = new string_id("space/space_interaction", "no_ship");
    public static final string_id SID_NO_CAN_CONTROL_SHIP_SLOT = new string_id("space/space_interaction", "no_can_control_ship_slot");
    public static final String[] SHIP_GUNNER_SLOT_NAMES = 
    {
        "ship_gunner0",
        "ship_gunner1",
        "ship_gunner2",
        "ship_gunner3",
        "ship_gunner4",
        "ship_gunner5",
        "ship_gunner6",
        "ship_gunner7"
    };
    public static final String[] POB_SHIP_GUNNER_SLOT_NAMES = 
    {
        "ship_gunner0_pob",
        "ship_gunner1_pob",
        "ship_gunner2_pob",
        "ship_gunner3_pob",
        "ship_gunner4_pob",
        "ship_gunner5_pob",
        "ship_gunner6_pob",
        "ship_gunner7_pob"
    };
    public static void notifyObject(obj_id objTarget, String strNotificationName, dictionary dctParams) throws InterruptedException
    {
        if (!isIdValid(objTarget))
        {
            debugServerConsoleMsg(null, "Null object id passed into notifyObject, exceptioning");
            Thread.dumpStack();
            throw new InterruptedException();
        }
        if (!objTarget.isLoaded() || !objTarget.isAuthoritative())
        {
            return;
        }
        if (strNotificationName == null || strNotificationName.equals(""))
        {
            debugServerConsoleMsg(null, "null or empty notification name passed into notifyObject, exceptioning");
            Thread.dumpStack();
            throw new InterruptedException();
        }
        try
        {
            int intReturn = script_entry.callMessageHandlers(strNotificationName, objTarget, dctParams);
            return;
        }
        catch(Throwable err)
        {
            debugServerConsoleMsg(null, "Unable to call into callMessageHandlers ");
            Thread.dumpStack();
            throw new InterruptedException();
        }
    }
    public static void callTrigger(String strTrigger, Object[] params) throws InterruptedException
    {
        try
        {
            script_entry.runScripts(strTrigger, params);
            return;
        }
        catch(Throwable err)
        {
            debugServerConsoleMsg(null, "Unable to call into callMessageHandlers ");
            Thread.dumpStack();
            throw new InterruptedException();
        }
    }
    public static boolean isPlayerControlledShip(obj_id objShip) throws InterruptedException
    {
        if (objShip == null)
        {
            return false;
        }
        String strTemplate = getTemplateName(objShip);
        if (strTemplate == null)
        {
            return false;
        }
        int intIndex = strTemplate.indexOf("object/ship/player/");
        if (intIndex > -1)
        {
            return true;
        }
        return false;
    }
    public static void sendDebugSpam(obj_id objTarget, String strSpam) throws InterruptedException
    {
        if ((hasScript(objTarget, "space.content_tools.content_generation")) || (isGod(objTarget)))
        {
            sendSystemMessageTestingOnly(objTarget, strSpam);
        }
        return;
    }
    public static boolean hasShip(obj_id player) throws InterruptedException
    {
        obj_id[] shipControlDevices = space_transition.findShipControlDevicesForPlayer(player);
        if (shipControlDevices != null && shipControlDevices.length > 0)
        {
            return true;
        }
        return false;
    }
    public static boolean isShipUsable(obj_id ship, obj_id player) throws InterruptedException
    {
        string_id failReason = null;
        if (isShipFlyable(ship))
        {
            if (hasCertificationsForItem(player, ship))
            {
                return true;
            }
            if (isGod(player))
            {
                sendSystemMessageTestingOnly(player, "** Passing certification test due to god mode. **");
                return true;
            }
            failReason = SID_NO_SHIP_CERTIFICATION;
        }
        else if (failReason == null)
        {
            failReason = SID_NO_WORKING_SHIP;
        }
        if (failReason == null)
        {
            sendSystemMessage(player, SID_NO_SHIP);
        }
        else 
        {
            sendSystemMessage(player, failReason);
        }
        return false;
    }
    public static obj_id hasUsableShip(obj_id player) throws InterruptedException
    {
        string_id failReason = null;
        obj_id[] shipControlDevices = space_transition.findShipControlDevicesForPlayer(player);
        if (shipControlDevices != null && shipControlDevices.length > 0)
        {
            for (int i = 0; i < shipControlDevices.length; ++i)
            {
                obj_id ship = space_transition.getShipFromShipControlDevice(shipControlDevices[i]);
                if (isIdValid(ship))
                {
                    if (isShipFlyable(ship))
                    {
                        if (hasCertificationsForItem(player, ship))
                        {
                            return shipControlDevices[i];
                        }
                        if (isGod(player))
                        {
                            sendSystemMessageTestingOnly(player, "** Passing certification test due to god mode. **");
                            return shipControlDevices[i];
                        }
                        failReason = SID_NO_SHIP_CERTIFICATION;
                    }
                    else if (failReason == null)
                    {
                        failReason = SID_NO_WORKING_SHIP;
                    }
                }
            }
        }
        if (failReason == null)
        {
            sendSystemMessage(player, SID_NO_SHIP);
        }
        else 
        {
            sendSystemMessage(player, failReason);
        }
        return null;
    }
    public static boolean hasShipControlDeviceCertification(obj_id player, obj_id shipControlDevice) throws InterruptedException
    {
        if (isIdValid(shipControlDevice))
        {
            obj_id ship = space_transition.getShipFromShipControlDevice(shipControlDevice);
            if (hasCertificationsForItem(player, ship))
            {
                return true;
            }
        }
        return false;
    }
    public static boolean isShipFlyable(obj_id objShip) throws InterruptedException
    {
        int[] REQUIRED_COMPONENTS = 
        {
            ship_component_type.SCT_reactor,
            ship_component_type.SCT_engine
        };
        if (!isIdValid(objShip))
        {
            return false;
        }
        for (int intI = 0; intI < REQUIRED_COMPONENTS.length; intI++)
        {
            if (isShipSlotInstalled(objShip, REQUIRED_COMPONENTS[intI]))
            {
                if (isShipComponentDisabled(objShip, REQUIRED_COMPONENTS[intI]))
                {
                    return false;
                }
            }
            else 
            {
                return false;
            }
        }
        return true;
    }
    public static boolean isShipPaintable(obj_id objShip) throws InterruptedException
    {
        String chassis = getShipChassisType(objShip);
        if (chassis.equals("player_basic_tiefighter") || chassis.equals("player_decimator") || chassis.equals("player_tieadvanced") || chassis.equals("player_tieaggressor") || chassis.equals("player_tiebomber") || chassis.equals("player_tiefighter") || chassis.equals("player_tie_in") || chassis.equals("player_tieinterceptor") || chassis.equals("player_tie_light_duty") || chassis.equals("player_tieoppressor") || chassis.equals("player_gunship_imperial") || chassis.equals("player_gunship_rebel") || chassis.equals("player_gunship_neutral") || chassis.equals("player_vwing") || chassis.equals("player_naboo_n1"))
        {
            return false;
        }
        return true;
    }
    public static boolean isShipTextureable(obj_id objShip) throws InterruptedException
    {
        String chassis = getShipChassisType(objShip);
        if (chassis.equals("player_basic_tiefighter") || chassis.equals("player_decimator") || chassis.equals("player_tieadvanced") || chassis.equals("player_tieaggressor") || chassis.equals("player_tiebomber") || chassis.equals("player_tiefighter") || chassis.equals("player_tie_in") || chassis.equals("player_tieinterceptor") || chassis.equals("player_tie_light_duty") || chassis.equals("player_tieoppressor") || chassis.equals("player_gunship_imperial") || chassis.equals("player_gunship_rebel") || chassis.equals("player_gunship_neutral") || chassis.equals("player_vwing") || chassis.equals("player_naboo_n1"))
        {
            return false;
        }
        return true;
    }
    public static void sendDelayedSystemMessage(obj_id objPlayer, string_id strSpam, float fltDelay) throws InterruptedException
    {
        dictionary dctParams = new dictionary();
        dctParams.put("strSpam", strSpam);
        messageTo(objPlayer, "doDelayedSystemMessage", dctParams, fltDelay, false);
        return;
    }
    public static transform getRandomPositionInSphere(transform trStartPosition, float fltMinRadius, float fltMaxRadius, boolean boolRandomizeOrientation) throws InterruptedException
    {
        float fltDistance = rand(fltMinRadius, fltMaxRadius);
        vector vctOffset = (vector.randomUnit()).multiply(fltDistance);
        transform transform_w = trStartPosition;
        transform_w = transform_w.move_p(vctOffset);
        if (boolRandomizeOrientation)
        {
            float randomYaw = rand(0f, (float)Math.PI * 2);
            float randomPitch = rand(0f, (float)Math.PI * 2);
            float randomRoll = rand(0f, (float)Math.PI * 2);
            transform_w = transform_w.yaw_l(randomYaw);
            transform_w = transform_w.pitch_l(randomPitch);
            transform_w = transform_w.roll_l(randomRoll);
        }
        return transform_w;
    }
    public static location getLocationFromTransform(transform trTest) throws InterruptedException
    {
        vector vctPoint = trTest.getPosition_p();
        location locTest = new location();
        locTest.x = vctPoint.x;
        locTest.y = vctPoint.y;
        locTest.z = vctPoint.z;
        return locTest;
    }
    public static transform faceTransformToVector(transform trStart, vector vctPoint) throws InterruptedException
    {
        vector vctNewPoint = trStart.rotateTranslate_p2l(vctPoint);
        transform trNewTransform = (trStart.yaw_l(vctNewPoint.theta())).pitch_l(vctNewPoint.phi());
        return trNewTransform;
    }
    public static vector getVector(obj_id objTest) throws InterruptedException
    {
        return (getTransform_o2p(objTest)).getPosition_p();
    }
    public static String getCellName(obj_id objBuilding, obj_id objCell) throws InterruptedException
    {
        String[] strCells = getCellNames(objBuilding);
        for (int intI = 0; intI < strCells.length; intI++)
        {
            obj_id objTest = getCellId(objBuilding, strCells[intI]);
            if (objTest == objCell)
            {
                return strCells[intI];
            }
        }
        return null;
    }
    public static Vector getPlayersInShip(obj_id objObject) throws InterruptedException
    {
        return space_transition.getContainedPlayers(objObject, null);
    }
    public static void sendSystemMessageShip(obj_id objShip, string_id strSpam, boolean boolSendToPilot, boolean boolSendToPassengers, boolean boolSendToGunners, boolean boolSendToOperations) throws InterruptedException
    {
        if (boolSendToPilot)
        {
            sendSystemMessageToPilot(objShip, strSpam);
        }
        if (boolSendToPassengers)
        {
            sendSystemMessageToPassengers(objShip, strSpam);
        }
        if (boolSendToGunners)
        {
            sendSystemMessageToGunners(objShip, strSpam);
        }
        if (boolSendToOperations)
        {
            sendSystemMessageToOperations(objShip, strSpam);
        }
        return;
    }
    public static void tauntShip(obj_id objShip, obj_id objSender, prose_package pp, boolean boolSendToPilot, boolean boolSendToPassengers, boolean boolSendToGunners, boolean boolSendToOperations) throws InterruptedException
    {
        if (boolSendToPilot)
        {
            tauntPilot(objShip, objSender, pp);
        }
        if (boolSendToPassengers)
        {
            tauntPassengers(objShip, objSender, pp);
        }
        if (boolSendToGunners)
        {
            tauntGunners(objShip, objSender, pp);
        }
        if (boolSendToOperations)
        {
            tauntOperations(objShip, objSender, pp);
        }
        return;
    }
    public static void tauntShip(obj_id objShip, obj_id objSender, string_id strSpam, boolean boolSendToPilot, boolean boolSendToPassengers, boolean boolSendToGunners, boolean boolSendToOperations) throws InterruptedException
    {
        if (boolSendToPilot)
        {
            tauntPilot(objShip, objSender, strSpam);
        }
        if (boolSendToPassengers)
        {
            tauntPassengers(objShip, objSender, strSpam);
        }
        if (boolSendToGunners)
        {
            tauntGunners(objShip, objSender, strSpam);
        }
        if (boolSendToOperations)
        {
            tauntOperations(objShip, objSender, strSpam);
        }
        return;
    }
    public static void tauntPlayer(obj_id objPlayer, obj_id objSender, string_id strSpam) throws InterruptedException
    {
        prose_package proseTest = prose.getPackage(strSpam);
        String strAppearance = "";
        if (hasObjVar(objSender, "convo.appearance"))
        {
            strAppearance = getStringObjVar(objSender, "convo.appearance");
        }
        if (!strAppearance.equals(""))
        {
            commPlayers(objSender, strAppearance, null, 6.0f, objPlayer, proseTest);
        }
        else 
        {
            commPlayers(objSender, null, null, 6.0f, objPlayer, proseTest);
        }
        space_combat.playCombatTauntSound(objPlayer);
    }
    public static void tauntPlayerWithSound(obj_id objPlayer, obj_id objSender, string_id strSpam, String soundEffect) throws InterruptedException
    {
        prose_package proseTest = prose.getPackage(strSpam);
        String strAppearance = "";
        if (hasObjVar(objSender, "convo.appearance"))
        {
            strAppearance = getStringObjVar(objSender, "convo.appearance");
        }
        if (!strAppearance.equals(""))
        {
            commPlayers(objSender, strAppearance, null, 6.0f, objPlayer, proseTest);
        }
        else 
        {
            commPlayers(objSender, null, null, 6.0f, objPlayer, proseTest);
        }
        playMusic(objPlayer, soundEffect);
    }
    public static void tauntPlayer(obj_id objPlayer, obj_id objSender, prose_package pp) throws InterruptedException
    {
        String strAppearance = "";
        if (hasObjVar(objSender, "convo.appearance"))
        {
            strAppearance = getStringObjVar(objSender, "convo.appearance");
        }
        if (!strAppearance.equals(""))
        {
            commPlayers(objSender, strAppearance, null, 6.0f, objPlayer, pp);
        }
        else 
        {
            commPlayers(objSender, null, null, 6.0f, objPlayer, pp);
        }
        space_combat.playCombatTauntSound(objPlayer);
    }
    public static void tauntPilot(obj_id objShip, obj_id objSender, string_id strSpam) throws InterruptedException
    {
        if (!isIdValid(objShip))
        {
            return;
        }
        obj_id objPilot = getPilotId(objShip);
        if (isIdValid(objPilot))
        {
            prose_package proseTest = prose.getPackage(strSpam);
            String strAppearance = "";
            if (hasObjVar(objSender, "convo.appearance"))
            {
                strAppearance = getStringObjVar(objSender, "convo.appearance");
            }
            LOG("space", "appearance is " + strAppearance);
            if (!strAppearance.equals(""))
            {
                commPlayers(objSender, strAppearance, null, 6.0f, objPilot, proseTest);
            }
            else 
            {
                commPlayers(objSender, null, null, 6.0f, objPilot, proseTest);
            }
            LOG("space", "playing sound for " + objPilot);
            space_combat.playCombatTauntSound(objPilot);
        }
    }
    public static void tauntPilot(obj_id objShip, obj_id objSender, prose_package pp) throws InterruptedException
    {
        obj_id objPilot = getPilotId(objShip);
        if (isIdValid(objPilot))
        {
            String strAppearance = "";
            if (hasObjVar(objSender, "convo.appearance"))
            {
                strAppearance = getStringObjVar(objSender, "convo.appearance");
            }
            if (!strAppearance.equals(""))
            {
                commPlayers(objSender, strAppearance, null, 6.0f, objPilot, pp);
            }
            else 
            {
                commPlayers(objSender, null, null, 6.0f, objPilot, pp);
            }
            LOG("space", "playing sound for " + objPilot);
            space_combat.playCombatTauntSound(objPilot);
        }
    }
    public static void tauntGunners(obj_id objShip, obj_id objSender, prose_package pp) throws InterruptedException
    {
        Vector objGunners = getGunnersInShip(objShip);
        if ((objGunners == null) || (objGunners.size() < 1))
        {
            return;
        }
        tauntArray(objSender, objGunners, pp);
        return;
    }
    public static void tauntGunners(obj_id objShip, obj_id objSender, string_id strSpam) throws InterruptedException
    {
        Vector objGunners = getGunnersInShip(objShip);
        if ((objGunners == null) || (objGunners.size() < 1))
        {
            return;
        }
        tauntArray(objSender, objGunners, strSpam);
    }
    public static void tauntOperations(obj_id objShip, obj_id objSender, prose_package pp) throws InterruptedException
    {
        obj_id objOfficer = getOperationsOfficer(objShip);
        if (isIdValid(objOfficer))
        {
            String strAppearance = "";
            if (hasObjVar(objSender, "convo.appearance"))
            {
                strAppearance = getStringObjVar(objSender, "convo.appearance");
            }
            if (!strAppearance.equals(""))
            {
                commPlayers(objSender, strAppearance, null, 6.0f, objOfficer, pp);
            }
            else 
            {
                commPlayers(objSender, null, null, 6.0f, objOfficer, pp);
            }
            space_combat.playCombatTauntSound(objOfficer);
        }
    }
    public static void tauntOperations(obj_id objShip, obj_id objSender, string_id strSpam) throws InterruptedException
    {
        obj_id objOfficer = getOperationsOfficer(objShip);
        if (isIdValid(objOfficer))
        {
            prose_package proseTest = prose.getPackage(strSpam);
            String strAppearance = "";
            if (hasObjVar(objSender, "convo.appearance"))
            {
                strAppearance = getStringObjVar(objSender, "convo.appearance");
            }
            if (!strAppearance.equals(""))
            {
                commPlayers(objSender, strAppearance, null, 6.0f, objOfficer, proseTest);
            }
            else 
            {
                commPlayers(objSender, null, null, 6.0f, objOfficer, proseTest);
            }
            space_combat.playCombatTauntSound(objOfficer);
        }
    }
    public static void tauntPassengers(obj_id objShip, obj_id objSender, prose_package pp) throws InterruptedException
    {
        Vector objPlayers = getPassengers(objShip);
        tauntArray(objSender, objPlayers, pp);
    }
    public static void tauntPassengers(obj_id objShip, obj_id objSender, string_id strSpam) throws InterruptedException
    {
        Vector objPlayers = getPassengers(objShip);
        tauntArray(objSender, objPlayers, strSpam);
    }
    public static void sendSystemMessageShip(obj_id objShip, prose_package pp, boolean boolSendToPilot, boolean boolSendToPassengers, boolean boolSendToGunners, boolean boolSendToOperations) throws InterruptedException
    {
        if (boolSendToPilot)
        {
            sendSystemMessageToPilot(objShip, pp);
        }
        if (boolSendToPassengers)
        {
            sendSystemMessageToPassengers(objShip, pp);
        }
        if (boolSendToGunners)
        {
            sendSystemMessageToGunners(objShip, pp);
        }
        if (boolSendToOperations)
        {
            sendSystemMessageToOperations(objShip, pp);
        }
        return;
    }
    public static void sendSystemMessageToPilot(obj_id objShip, prose_package pp) throws InterruptedException
    {
        obj_id objPilot = getPilotId(objShip);
        if (isIdValid(objPilot))
        {
            sendSystemMessageProse(objPilot, pp);
        }
        return;
    }
    public static void sendSystemMessageToPilot(obj_id objShip, string_id strSpam) throws InterruptedException
    {
        obj_id objPilot = getPilotId(objShip);
        if (isIdValid(objPilot))
        {
            sendSystemMessage(objPilot, strSpam);
        }
        return;
    }
    public static void sendSystemMessageToGunners(obj_id objShip, string_id strSpam) throws InterruptedException
    {
        Vector objGunners = getGunnersInShip(objShip);
        sendSystemMessageToArray(objGunners, strSpam);
        return;
    }
    public static void sendSystemMessageToGunners(obj_id objShip, prose_package pp) throws InterruptedException
    {
        Vector objGunners = getGunnersInShip(objShip);
        sendSystemMessageToArray(objGunners, pp);
        return;
    }
    public static void sendSystemMessageToArray(Vector objArray, string_id strSpam) throws InterruptedException
    {
        if ((objArray != null) && (objArray.size() > 0))
        {
            for (int intI = 0; intI < objArray.size(); intI++)
            {
                sendSystemMessage(((obj_id)objArray.get(intI)), strSpam);
            }
        }
    }
    public static void sendSystemMessageToArray(Vector objArray, prose_package pp) throws InterruptedException
    {
        if ((objArray != null) && (objArray.size() > 0))
        {
            for (int intI = 0; intI < objArray.size(); intI++)
            {
                sendSystemMessageProse(((obj_id)objArray.get(intI)), pp);
            }
        }
    }
    public static void tauntArray(obj_id objSender, Vector objArray, prose_package pp) throws InterruptedException
    {
        if ((objArray != null) && (objArray.size() > 0))
        {
            String strAppearance = "";
            if (hasObjVar(objSender, "convo.appearance"))
            {
                strAppearance = getStringObjVar(objSender, "convo.appearance");
            }
            if (!strAppearance.equals(""))
            {
                obj_id[] temp = new obj_id[objArray.size()];
                objArray.toArray(temp);
                dogfightTauntPlayers(objSender, temp, pp, strAppearance);
            }
            else 
            {
                obj_id[] temp = new obj_id[objArray.size()];
                objArray.toArray(temp);
                dogfightTauntPlayers(objSender, temp, pp);
            }
        }
    }
    public static void tauntArray(obj_id objSender, Vector objArray, string_id strSpam) throws InterruptedException
    {
        if ((objArray != null) && (objArray.size() > 0))
        {
            String strAppearance = "";
            if (hasObjVar(objSender, "convo.appearance"))
            {
                strAppearance = getStringObjVar(objSender, "convo.appearance");
            }
            prose_package pp = prose.getPackage(strSpam);
            if (!strAppearance.equals(""))
            {
                obj_id[] temp = new obj_id[objArray.size()];
                objArray.toArray(temp);
                dogfightTauntPlayers(objSender, temp, pp, strAppearance);
            }
            else 
            {
                obj_id[] temp = new obj_id[objArray.size()];
                objArray.toArray(temp);
                dogfightTauntPlayers(objSender, temp, pp);
            }
        }
    }
    public static void sendSystemMessageToPassengers(obj_id objShip, string_id strSpam) throws InterruptedException
    {
        Vector objPlayers = getPassengers(objShip);
        sendSystemMessageToArray(objPlayers, strSpam);
    }
    public static void sendSystemMessageToPassengers(obj_id objShip, prose_package pp) throws InterruptedException
    {
        Vector objPlayers = getPassengers(objShip);
        sendSystemMessageToArray(objPlayers, pp);
    }
    public static void sendSystemMessageToOperations(obj_id objShip, string_id strSpam) throws InterruptedException
    {
        obj_id objOfficer = getOperationsOfficer(objShip);
        if (isIdValid(objOfficer))
        {
            sendSystemMessage(objOfficer, strSpam);
        }
        return;
    }
    public static void sendSystemMessageToOperations(obj_id objShip, prose_package pp) throws InterruptedException
    {
        obj_id objOfficer = getOperationsOfficer(objShip);
        if (isIdValid(objOfficer))
        {
            sendSystemMessageProse(objOfficer, pp);
        }
        return;
    }
    public static Vector getGunnersInShip(obj_id objShip) throws InterruptedException
    {
        Vector gunners = new Vector();
        gunners.setSize(0);
        obj_id[] shipContents = getContents(objShip);
        if (shipContents != null && shipContents.length > 0)
        {
            if (!(getTemplateName(shipContents[0])).startsWith("object/cell/"))
            {
                for (int i = 0; i < POB_SHIP_GUNNER_SLOT_NAMES.length; ++i)
                {
                    obj_id gunner = getObjectInSlot(objShip, SHIP_GUNNER_SLOT_NAMES[i]);
                    if (isIdValid(gunner))
                    {
                        gunners = utils.addElement(gunners, gunner);
                    }
                }
                return gunners;
            }
            for (int i = 0; i < shipContents.length; ++i)
            {
                obj_id[] cellContents = getContents(shipContents[i]);
                if (cellContents != null)
                {
                    for (int j = 0; j < cellContents.length; ++j)
                    {
                        obj_id cellContent = cellContents[j];
                        if (isIdValid(cellContent))
                        {
                            for (int k = 0; k < POB_SHIP_GUNNER_SLOT_NAMES.length; ++k)
                            {
                                obj_id gunner = getObjectInSlot(cellContent, POB_SHIP_GUNNER_SLOT_NAMES[k]);
                                if (isIdValid(gunner))
                                {
                                    gunners = utils.addElement(gunners, gunner);
                                }
                            }
                        }
                    }
                }
            }
        }
        return gunners;
    }
    public static transform randomizeTransformOrientation(transform trTest) throws InterruptedException
    {
        transform tr = ((trTest.yaw_l(rand(-(float)Math.PI, (float)Math.PI))).pitch_l(rand(-(float)Math.PI, (float)Math.PI))).roll_l(rand(-(float)Math.PI, (float)Math.PI));
        return tr;
    }
    public static boolean isShipWithInterior(obj_id objShip) throws InterruptedException
    {
        String[] strCells = getCellNames(objShip);
        if ((strCells == null) || (strCells.length == 0))
        {
            return false;
        }
        else 
        {
            return true;
        }
    }
    public static obj_id[] getAllPlayersInShip(obj_id ship) throws InterruptedException
    {
        Vector crew = getGunnersInShip(ship);
        obj_id pilot = getPilotId(ship);
        if (isIdValid(pilot))
        {
            if (utils.getElementPositionInArray(crew, pilot) == -1)
            {
                utils.addElement(crew, pilot);
            }
        }
        obj_id opps = getOperationsOfficer(ship);
        if (isIdValid(opps))
        {
            if (utils.getElementPositionInArray(crew, opps) == -1)
            {
                utils.addElement(crew, opps);
            }
        }
        Vector passengers = getPassengers(ship);
        if (passengers != null && passengers.size() > 0)
        {
            for (int i = 0; i < passengers.size(); i++)
            {
                if (utils.getElementPositionInArray(crew, ((obj_id)passengers.get(i))) == -1)
                {
                    utils.addElement(crew, ((obj_id)passengers.get(i)));
                }
            }
        }
        obj_id[] _crew = new obj_id[0];
        if (crew != null)
        {
            _crew = new obj_id[crew.size()];
            crew.toArray(_crew);
        }
        return _crew;
    }
    public static Vector getPassengers(obj_id objShip) throws InterruptedException
    {
        obj_id[] objContents = getContents(objShip);
        if ((objContents != null) && (objContents.length > 0))
        {
            Vector objPassengers = new Vector();
            objPassengers.setSize(0);
            for (int intI = 0; intI < objContents.length; intI++)
            {
                obj_id[] objInteriorContents = getContents(objContents[intI]);
                if ((objInteriorContents != null) && (objInteriorContents.length > 0))
                {
                    for (int intJ = 0; intJ < objInteriorContents.length; intJ++)
                    {
                        if (isPlayer(objInteriorContents[intJ]))
                        {
                            objPassengers = utils.addElement(objPassengers, objInteriorContents[intJ]);
                        }
                    }
                }
            }
            if (objPassengers.size() > 0)
            {
                return objPassengers;
            }
            else 
            {
                return null;
            }
        }
        else 
        {
            return null;
        }
    }
    public static float getRandomValueWithDeadZone(float fltStart, float fltMin, float fltMax) throws InterruptedException
    {
        int intRoll = rand(1, 2);
        if (intRoll == 1)
        {
            float fltValue = rand(fltMin, fltMax);
            float fltReturn = fltStart - fltValue;
            return fltReturn;
        }
        else 
        {
            float fltValue = rand(fltMin, fltMax);
            float fltReturn = fltStart + fltValue;
            return fltReturn;
        }
    }
    public static location getRandomLocationInSphere(location locStart, float fltMin, float fltMax) throws InterruptedException
    {
        locStart.x = getRandomValueWithDeadZone(locStart.x, fltMin, fltMax);
        locStart.y = getRandomValueWithDeadZone(locStart.y, fltMin, fltMax);
        locStart.z = getRandomValueWithDeadZone(locStart.z, fltMin, fltMax);
        return locStart;
    }
    public static Vector getShipOfficers(obj_id objShip) throws InterruptedException
    {
        if (space_utils.isShipWithInterior(objShip))
        {
            Vector objOfficers = getGunnersInShip(objShip);
            obj_id objOfficer = getPilotId(objShip);
            if (isIdValid(objOfficer))
            {
                objOfficers = utils.addElement(objOfficers, objOfficer);
            }
            objOfficer = getOperationsOfficer(objShip);
            if (isIdValid(objOfficer))
            {
                objOfficers = utils.addElement(objOfficers, objOfficer);
            }
            return objOfficers;
        }
        else 
        {
            Vector objTest = new Vector();
            objTest.setSize(0);
            objTest = utils.addElement(objTest, getPilotId(objShip));
            return objTest;
        }
    }
    public static obj_id getCommandExecutor(obj_id objShip) throws InterruptedException
    {
        if (isShipWithInterior(objShip))
        {
            return getOperationsOfficer(objShip);
        }
        else 
        {
            return getPilotId(objShip);
        }
    }
    public static obj_id getOperationsOfficer(obj_id objShip) throws InterruptedException
    {
        obj_id objChair = utils.getObjIdScriptVar(objShip, "objOperationsChair");
        if (isIdValid(objChair))
        {
            return getObjectInSlot(objChair, "ship_operations_pob");
        }
        else 
        {
            return getObjectInSlot(objShip, "ship_operations");
        }
    }
    public static void destroyShipControlDevices(obj_id player, boolean verbose) throws InterruptedException
    {
        obj_id[] shipControlDevices = space_transition.findShipControlDevicesForPlayer(player);
        if (shipControlDevices != null)
        {
            for (int i = 0; i < shipControlDevices.length; ++i)
            {
                if (isIdValid(shipControlDevices[i]))
                {
                    if (verbose)
                    {
                        obj_id ship = space_transition.getShipFromShipControlDevice(shipControlDevices[i]);
                        sendSystemMessageTestingOnly(player, "Destroyed ship, scd=" + shipControlDevices[i] + ", ship=" + ship);
                    }
                    destroyObject(shipControlDevices[i]);
                }
            }
        }
    }
    public static obj_id createShipControlDevice(obj_id player, String shipName, boolean verbose) throws InterruptedException
    {
        obj_id shipControlDevice = null;
        obj_id ship = null;
        String scdTemplate = "object/intangible/ship/" + shipName + "_pcd.iff";
        String shipTemplate = "object/ship/player/player_" + shipName + ".iff";
        obj_id datapad = utils.getDatapad(player);
        if (isIdValid(datapad))
        {
            shipControlDevice = createObjectOverloaded(scdTemplate, datapad);
            if (!isIdValid(shipControlDevice))
            {
                shipControlDevice = createObjectOverloaded("object/intangible/ship/xwing_pcd.iff", datapad);
                scdTemplate = "DEFAULT - object/intangible/ship/xwing_pcd.iff";
            }
            if (isIdValid(shipControlDevice))
            {
                ship = createObject(shipTemplate, shipControlDevice, "");
                if (isIdValid(ship))
                {
                    setOwner(ship, player);
                    if (isSpaceScene())
                    {
                        setObjVar(player, "space.launch.ship", ship);
                        space_transition.handlePotentialSceneChange(player);
                    }
                }
            }
        }
        if (verbose)
        {
            if (isIdValid(ship))
            {
                sendSystemMessageTestingOnly(player, "Created ship (" + shipTemplate + ") with scd (" + scdTemplate + ")");
            }
            else if (isIdValid(shipControlDevice))
            {
                destroyObject(shipControlDevice);
                shipControlDevice = null;
                sendSystemMessageTestingOnly(player, "Failed to create ship (" + shipTemplate + ")");
            }
        }
        return shipControlDevice;
    }
    public static String getParkingLocation(obj_id objControlDevice) throws InterruptedException
    {
        if (hasObjVar(objControlDevice, "strParkingLocation"))
        {
            return getStringObjVar(objControlDevice, "strParkingLocation");
        }
        return "";
    }
    public static String getProperParkingLocation(obj_id objControlDevice) throws InterruptedException
    {
        String strPort = getParkingLocation(objControlDevice);
        if (strPort != null)
        {
            String strPlanet = space_content.getPlanetForLaunchLocation(strPort);
            if (strPlanet != null)
            {
                if (strPlanet.length() < 2)
                {
                    strPlanet = strPlanet.toUpperCase();
                }
                else 
                {
                    String strFirstLetter = strPlanet.substring(0, 1);
                    strFirstLetter = strFirstLetter.toUpperCase();
                    String strFinalName = strPlanet.substring(1);
                    strPlanet = strFirstLetter + strFinalName;
                }
                strPort = strPort + ", " + strPlanet;
                return strPort;
            }
        }
        return "";
    }
    public static void setLandingLocation(obj_id objControlDevice, String strLocation) throws InterruptedException
    {
        setObjVar(objControlDevice, "strParkingLocation", strLocation);
        return;
    }
    public static obj_id createObjectTrackItemCount(String strItem, obj_id objContainer) throws InterruptedException
    {
        if (!isIdValid(objContainer))
        {
            return null;
        }
        if (strItem.equals(""))
        {
            return null;
        }
        obj_id objTopMostContainer = getTopMostContainer(objContainer);
        if (hasObjVar(objTopMostContainer, "player_structure"))
        {
            int intCount = player_structure.getStructureNumItems(objContainer);
            if (intCount >= POB_LOOT_BOX_CAPACITY)
            {
                return null;
            }
            if (!static_item.isStaticItem(strItem))
            {
                return createObject(strItem, objContainer, "");
            }
            else 
            {
                return static_item.createNewItemFunction(strItem, objContainer);
            }
        }
        if (!static_item.isStaticItem(strItem))
        {
            return createObject(strItem, objContainer, "");
        }
        if (getVolumeFree(objContainer) > 0)
        {
            return static_item.createNewItemFunction(strItem, objContainer);
        }
        return null;
    }
    public static boolean hasWaypointInDatapad(obj_id player, obj_id waypoint) throws InterruptedException
    {
        obj_id[] waypoints = getWaypointsInDatapad(player);
        if ((waypoints != null) && (waypoints.length > 0))
        {
            for (int intI = 0; intI < waypoints.length; intI++)
            {
                if (waypoints[intI] == waypoint)
                {
                    return true;
                }
            }
            return false;
        }
        else 
        {
            return false;
        }
    }
    public static boolean isInStation(obj_id objPlayer) throws InterruptedException
    {
        int intState = 0;
        intState += getState(objPlayer, STATE_SHIP_GUNNER);
        intState += getState(objPlayer, STATE_SHIP_OPERATIONS);
        intState += getState(objPlayer, STATE_PILOTING_POB_SHIP);
        if (intState > 0)
        {
            return true;
        }
        return false;
    }
    public static boolean isShip(obj_id objShip) throws InterruptedException
    {
        if (!isIdValid(objShip))
        {
            return false;
        }
        int intGOT = getGameObjectType(objShip);
        if (isGameObjectTypeOf(intGOT, GOT_ship))
        {
            return true;
        }
        return false;
    }
    public static void openCommChannelAfterLoad(obj_id objShip, obj_id objTarget) throws InterruptedException
    {
        obj_id objPilot = getPilotId(objShip);
        if (isIdValid(objPilot))
        {
            utils.setScriptVar(objPilot, "objCommTarget", objTarget);
            newbieTutorialRequest(objPilot, "clientReady");
        }
        return;
    }
    public static boolean isBasicShip(obj_id objShip) throws InterruptedException
    {
        String strChassisName = getShipChassisType(objShip);
        int intIndex = strChassisName.indexOf("_basic");
        if (intIndex > -1)
        {
            return true;
        }
        return false;
    }
    public static boolean isPrototypeShip(obj_id objShip) throws InterruptedException
    {
        String strChassisName = getShipChassisType(objShip);
        int intIndex = strChassisName.indexOf("_prototype");
        if (intIndex > -1)
        {
            return true;
        }
        return false;
    }
    public static obj_id[] getSpaceGroupMemberIds(obj_id group) throws InterruptedException
    {
        return utils.getLocalGroupMemberIds(group);
    }
    public static boolean playerCanControlShipSlot(obj_id ship, obj_id player, boolean sendSystemMessage) throws InterruptedException
    {
        boolean result = true;
        obj_id owner = getOwner(ship);
        if (owner != player)
        {
            result = group.inSameGroup(owner, player);
        }
        if ((sendSystemMessage) && (!result))
        {
            prose_package pp = prose.getPackage(SID_NO_CAN_CONTROL_SHIP_SLOT);
            sendSystemMessageProse(player, pp);
        }
        return result;
    }
    public static boolean isPobType(String strType) throws InterruptedException
    {
        String[] TYPES = 
        {
            "ykl37r",
            "yt1300",
            "yt2400",
            "y8_mining_ship",
            "decimator",
            "sorosuub_space_yacht",
            "gunship_rebel",
            "gunship_imperial",
            "gunship_neutral"
        };
        for (int intI = 0; intI < TYPES.length; intI++)
        {
            int intIndex = strType.indexOf(TYPES[intI]);
            if (intIndex > -1)
            {
                return true;
            }
        }
        return false;
    }
    public static boolean isPobType(obj_id ship) throws InterruptedException
    {
        if (!isIdValid(ship))
        {
            return false;
        }
        return isPobType(getTemplateName(ship));
    }
    public static boolean isInPobShip(obj_id player) throws InterruptedException
    {
        return isPobType(getContainedBy(player));
    }
    public static boolean isNestedWithinPobShip(obj_id item) throws InterruptedException
    {
        if (!isIdValid(item))
        {
            return false;
        }
        obj_id containedBy = getContainedBy(item);
        if (!isIdValid(containedBy))
        {
            return false;
        }
        return isInPobShip(containedBy);
    }
    public static void setNpcToPlayerSpeed(obj_id player, obj_id npc, float speedPercent) throws InterruptedException
    {
        matchEngineSpeed(player, npc, speedPercent, false);
    }
    public static void matchEngineSpeed(obj_id player, obj_id npc, float speedPercent, boolean isMission) throws InterruptedException
    {
        obj_id playerShip = space_transition.getContainingShip(player);
        if (isMission)
        {
            speedPercent *= 2;
        }
        float modifiedSpeed = (getShipEngineSpeedMaximum(playerShip) * speedPercent);
        if (isMission && modifiedSpeed > 100.0f)
        {
            modifiedSpeed = 100.0f;
        }
        setShipEngineSpeedMaximum(npc, modifiedSpeed);
    }
    public static boolean isPlayerShipAttackable(obj_id player) throws InterruptedException
    {
        obj_id containing_ship = space_transition.getContainingShip(player);
        if (isIdValid(containing_ship))
        {
            String strChassisType = getShipChassisType(containing_ship);
            if (strChassisType != null && strChassisType.equals("player_sorosuub_space_yacht"))
            {
                return false;
            }
        }
        return true;
    }
    public static boolean isHyperspacing(obj_id player) throws InterruptedException
    {
        obj_id objShip = space_transition.getContainingShip(player);
        return isIdValid(objShip) ? utils.hasScriptVar(objShip, "intHyperspacing") : false;
    }
    public static boolean isHyperspaceBlocked(obj_id player) throws InterruptedException
    {
        if (!utils.hasScriptVar(player, "hyperspaceBlock"))
        {
            return false;
        }
        int hyperspaceBlock = utils.getIntScriptVar(player, "hyperspaceBlock");
        int playerPlayedTime = getPlayerPlayedTime(player);
        int timeRemaining = hyperspaceBlock - playerPlayedTime;
        if (timeRemaining > 0)
        {
            prose_package pp = prose.getPackage(new string_id("space/space_interaction", "hyperspace_not_ready"));
            prose.setDI(pp, timeRemaining);
            sendSystemMessageProse(player, pp);
            return true;
        }
        return false;
    }
    public static void setHyperspaceBlock(obj_id player, int durationInSeconds, boolean abortActiveHyperspace) throws InterruptedException
    {
        if (abortActiveHyperspace && isHyperspacing(player))
        {
            queueCommand(player, (-229072874), null, "", COMMAND_PRIORITY_DEFAULT);
        }
        if (durationInSeconds > 0)
        {
            utils.setScriptVar(player, "hyperspaceBlock", getPlayerPlayedTime(player) + durationInSeconds);
        }
    }
    public static void clearHyperspaceBlock(obj_id player) throws InterruptedException
    {
        if (utils.hasScriptVar(player, "hyperspaceBlock"))
        {
            utils.removeScriptVar(player, "hyperspaceBlock");
        }
    }
    public static void setComponentDisabled(obj_id ship, int slotId, boolean disabled) throws InterruptedException
    {
        setShipComponentDisabled(ship, slotId, disabled);
        if (disabled && slotId == ship_chassis_slot_type.SCST_droid_interface)
        {
            space_combat.normalizeAllComponents(ship);
        }
    }
    public static obj_id getPilotForRealsies(obj_id ship) throws InterruptedException
    {
        obj_id pilot = getPilotId(ship);
        if (isIdValid(pilot))
        {
            return pilot;
        }
        obj_id[] players = space_utils.getAllPlayersInShip(ship);
        if (players == null || players.length == 0)
        {
            return null;
        }
        for (int i = 0; i < players.length; i++)
        {
            if (getOwner(ship) == players[i])
            {
                return players[i];
            }
        }
        return null;
    }
}
