package script.space.terminal;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.ai_lib;
import script.library.callable;
import script.library.features;
import script.library.locations;
import script.library.performance;
import script.library.pet_lib;
import script.library.player_structure;
import script.library.regions;
import script.library.session;
import script.library.space_crafting;
import script.library.space_flags;
import script.library.space_transition;
import script.library.space_utils;
import script.library.stealth;
import script.library.sui;
import script.library.travel;
import script.library.utils;
import script.library.vehicle;
import script.library.buff;
import script.library.event_perk;

public class terminal_space extends script.terminal.base.base_terminal
{
    public terminal_space()
    {
    }
    public static final float TERMINAL_USE_DISTANCE = 8.0f;
    public static final string_id SID_LAUNCH_SHIP = new string_id("space/space_terminal", "launch_ship");
    public static final string_id SID_MUSTAFAR = new string_id("space/space_terminal", "mustafar_exception");
    public static final string_id SID_NOT_IN_COMBAT = new string_id("travel", "not_in_combat");
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        requestPreloadCompleteTrigger(self);
        return SCRIPT_CONTINUE;
    }
    public int OnPreloadComplete(obj_id self) throws InterruptedException
    {
        String strFileName = "datatables/space_zones/launch_locations.iff";
        String strName = "mos_eisley";
        location locTest = getLocation(self);
        region[] rgnCities = getRegionsWithGeographicalAtPoint(locTest, regions.GEO_CITY);
        dictionary dctTeleportInfo = null;
        if (rgnCities == null || rgnCities.length == 0)
        {
            setName(self, "BUSTED TERMINAL! PUT ME IN A CITY!@!@!@!@");
        }
        else 
        {
            for (int i = 0; i < rgnCities.length; i++)
            {
                region rgnTest = rgnCities[i];
                strName = rgnTest.getName();
                if (strName.startsWith("@"))
                {
                    string_id strTest = utils.unpackString(strName);
                    strName = strTest.getAsciiId();
                    dctTeleportInfo = dataTableGetRow(strFileName, strName);
                    if (dctTeleportInfo != null)
                    {
                        break;
                    }
                }
            }
        }
        if (dctTeleportInfo == null)
        {
            setName(self, "NO ENTRY FOR " + strName + " in teleport datable. Busted terminal");
            return SCRIPT_CONTINUE;
        }
        utils.setScriptVar(self, "space.loc.space", new location(dctTeleportInfo.getFloat("spaceX"), dctTeleportInfo.getFloat("spaceY"), dctTeleportInfo.getFloat("spaceZ"), dctTeleportInfo.getString("spaceScene")));
        utils.setScriptVar(self, "space.locationName", "w" + dctTeleportInfo.getInt("spaceLocationIndex"));
        utils.setScriptVar(self, "space.loc.ground", new location(dctTeleportInfo.getFloat("groundX"), dctTeleportInfo.getFloat("groundY"), dctTeleportInfo.getFloat("groundZ"), dctTeleportInfo.getString("groundScene")));
        utils.setScriptVar(self, "ground.locationName", dctTeleportInfo.getString("pointName"));
        return SCRIPT_CONTINUE;
    }
    public int OnAboutToLaunchIntoSpace(obj_id self, obj_id player, obj_id shipControlDevice, obj_id[] membersApprovedByShipOwner, String destinationGroundPlanet, String destinationGroundTravelPoint) throws InterruptedException
    {
        LOG("space", "triggered OnAboutToLaunchIntoSpace");
        if (!doSpacePrecheck(player))
        {
            return SCRIPT_CONTINUE;
        }
        if (!features.isSpaceEdition(player))
        {
            LOG("space", "NO EXPANSION");
            string_id strSpam = new string_id("space/space_interaction", "no_space_expansion");
            sendSystemMessage(player, strSpam);
            if (!isGod(player))
            {
                LOG("space", "NO EXPANSION - RETURNING");
                return SCRIPT_CONTINUE;
            }
        }
        if (ai_lib.isInCombat(player))
        {
            sendSystemMessage(player, SID_NOT_IN_COMBAT);
            return SCRIPT_CONTINUE;
        }
        boolean isStarportToStarportLaunch = destinationGroundPlanet != null && !destinationGroundPlanet.equals("");
        if (travel.isTravelBlocked(player, !isStarportToStarportLaunch))
        {
            return SCRIPT_CONTINUE;
        }
        if (getState(self, STATE_GLOWING_JEDI) != 0)
        {
            setState(self, STATE_GLOWING_JEDI, false);
        }
        location warpLocation = utils.getLocationScriptVar(self, "space.loc.space");
        if (warpLocation == null)
        {
            LOG("space", "OnAboutToLaunchIntoSpace warpLocation space.loc.space was null on terminal");
        }
        else 
        {
            LOG("space", "getting scd and stuff");
            LOG("space", "scd is " + shipControlDevice);
            if (isIdValid(shipControlDevice))
            {
                obj_id ship = space_transition.getShipFromShipControlDevice(shipControlDevice);
                blog("component_fix", "TERMINAL - WORKING");
                boolean success = space_crafting.checkForCollectionReactor(shipControlDevice, ship);
                if (success)
                {
                    CustomerServiceLog("ShipComponents", "Collection reactor found and handled for: (" + self + ") " + getName(self));
                    return SCRIPT_CONTINUE;
                }
                space_crafting.setCollectionReactorChecked(shipControlDevice);
                if (!space_utils.isShipUsable(ship, player))
                {
                    return SCRIPT_CONTINUE;
                }
                float currentShipMass = getChassisComponentMassCurrent(ship);
                float maxAllowableShipMass = getChassisComponentMassMaximum(ship);
                if ((currentShipMass > maxAllowableShipMass) && !isGod(player))
                {
                    string_id strSpam = new string_id("space/space_interaction", "too_heavy");
                    sendSystemMessage(player, strSpam);
                    return SCRIPT_CONTINUE;
                }
                if (space_utils.isShipWithInterior(ship))
                {
                    obj_id[] objControlDevices = space_transition.findShipControlDevicesForPlayer(player, true);
                    int intCount = 0;
                    for (int intI = 0; intI < objControlDevices.length; intI++)
                    {
                        obj_id objTestShip = space_transition.getShipFromShipControlDevice(objControlDevices[intI]);
                        if (space_utils.isShipWithInterior(objTestShip))
                        {
                            if (objTestShip != ship)
                            {
                                int intItemCount = player_structure.getStructureNumItems(objTestShip);
                                if (intItemCount > 0)
                                {
                                    intCount = intCount + 1;
                                    if (intCount >= 2)
                                    {
                                        string_id strSpam = new string_id("space/space_interaction", "too_many_pobs");
                                        sendSystemMessage(player, strSpam);
                                        return SCRIPT_CONTINUE;
                                    }
                                }
                            }
                        }
                    }
                }
                if (isIdValid(ship))
                {
                    utils.setLocalVar(player, "objControlDevice", shipControlDevice);
                    callable.storeCallables(player);
                    vehicle.storeAllVehicles(player);
                    if (utils.hasScriptVar(player, "currentHolo"))
                    {
                        performance.holographicCleanup(player);
                    }
                    if (isStarportToStarportLaunch)
                    {
                        doStarportToStarportLaunch(player, ship, membersApprovedByShipOwner, destinationGroundPlanet, destinationGroundTravelPoint);
                    }
                    else 
                    {
                        LOG("space", "location is " + warpLocation);
                        location locDestination = space_utils.getRandomLocationInSphere(warpLocation, 150, 300);
                        LOG("space", "launching to " + locDestination);
                        location locFinalDestination = getFinalHyperspaceLocation(player, locDestination);
                        if (locFinalDestination == null)
                        {
                            string_id tooFull = new string_id("shared_hyperspace", "zone_too_full_use_travel");
                            sendSystemMessage(player, tooFull);
                            return SCRIPT_CONTINUE;
                        }
                        utils.setScriptVar(player, "strLaunchPointName", utils.getStringScriptVar(self, "space.locationName"));
                        location groundLoc = utils.getLocationScriptVar(self, "space.loc.ground");
                        session.logActivity(player, session.ACTIVITY_SPACE_LAUNCH);
                        if (hasScript(player, performance.DANCE_HEARTBEAT_SCRIPT))
                        {
                            performance.stopDance(player);
                        }
                        if (hasScript(player, performance.MUSIC_HEARTBEAT_SCRIPT))
                        {
                            performance.stopMusic(player);
                        }
                        if (membersApprovedByShipOwner != null && membersApprovedByShipOwner.length > 0)
                        {
                            for (int i = 0; i < membersApprovedByShipOwner.length; ++i)
                            {
                                if (hasScript(membersApprovedByShipOwner[i], performance.DANCE_HEARTBEAT_SCRIPT))
                                {
                                    performance.stopDance(membersApprovedByShipOwner[i]);
                                }
                                if (hasScript(membersApprovedByShipOwner[i], performance.MUSIC_HEARTBEAT_SCRIPT))
                                {
                                    performance.stopMusic(membersApprovedByShipOwner[i]);
                                }
                            }
                        }
                        launch(player, ship, membersApprovedByShipOwner, locFinalDestination, groundLoc);
                    }
                    return SCRIPT_CONTINUE;
                }
            }
        }
        return SCRIPT_CONTINUE;
    }
    public void doStarportToStarportLaunch(obj_id player, obj_id ship, obj_id[] membersApprovedByShipOwner, String planet, String pointName) throws InterruptedException
    {
        if (!getPlanetTravelPointInterplanetary(planet, pointName))
        {
            return;
        }
        if (planet.equals("mustafar"))
        {
            sendSystemMessage(player, SID_MUSTAFAR);
            return;
        }
        if (planet.equals("kashyyyk_main"))
        {
            if (!features.hasEpisode3Expansion(player))
            {
                sendSystemMessage(player, travel.SID_KASHYYYK_UNAUTHORIZED);
                return;
            }
        }
        if (space_utils.isBasicShip(ship))
        {
            location locTest = getLocation(player);
            if (!planet.equals(locTest.area))
            {
                string_id strSpam = new string_id("space/space_interaction", "no_travel_basic");
                sendSystemMessage(player, strSpam);
                return;
            }
        }
        if (callable.hasAnyCallable(player))
        {
            callable.storeCallables(player);
        }
        LOG("space", "doStarportToStarportLaunch");
        Vector groupMembersToWarp = utils.addElement(null, player);
        Vector groupMemberStartIndex = utils.addElement(null, 0);
        Vector shipStartLocations = space_transition.getShipStartLocations(ship);
        boolean callableInGroup = false;
        if (shipStartLocations != null && shipStartLocations.size() > 0)
        {
            int startIndex = 0;
            location playerLoc = getLocation(player);
            if (isIdValid(playerLoc.cell))
            {
                for (int i = 0; i < membersApprovedByShipOwner.length; ++i)
                {
                    if (membersApprovedByShipOwner[i] != player && exists(membersApprovedByShipOwner[i]) && getLocation(membersApprovedByShipOwner[i]).cell == playerLoc.cell)
                    {
                        startIndex = space_transition.getNextStartIndex(shipStartLocations, startIndex);
                        if (startIndex <= shipStartLocations.size())
                        {
                            groupMembersToWarp = utils.addElement(groupMembersToWarp, membersApprovedByShipOwner[i]);
                            groupMemberStartIndex = utils.addElement(groupMemberStartIndex, startIndex);
                        }
                        if (callable.hasAnyCallable(membersApprovedByShipOwner[i]))
                        {
                            callable.storeCallables(membersApprovedByShipOwner[i]);
                        }
                    }
                }
            }
        }
        for (int i = 0; i < groupMembersToWarp.size(); ++i)
        {
            travel.movePlayerToDestination(((obj_id)groupMembersToWarp.get(i)), planet, pointName);
        }
        return;
    }
    public void launch(obj_id player, obj_id ship, obj_id[] membersApprovedByShipOwner, location warpLocation, location groundLoc) throws InterruptedException
    {
        if (callable.hasAnyCallable(player))
        {
            callable.storeCallables(player);
        }
        stealth.checkForAndMakeVisible(player);
        int shapechange = buff.getBuffOnTargetFromGroup(player, "shapechange");
        if (shapechange != 0)
        {
            buff.removeBuff(player, shapechange);
            sendSystemMessage(player, event_perk.SHAPECHANGE_SPACE);
        }
        space_transition.clearOvertStatus(ship);
        Vector groupMembersToWarp = utils.addElement(null, player);
        Vector groupMemberStartIndex = utils.addElement(null, 0);
        utils.setScriptVar(player, "strLaunchPointName", "launching");
        Vector shipStartLocations = space_transition.getShipStartLocations(ship);
        if (shipStartLocations != null && shipStartLocations.size() > 0)
        {
            int startIndex = 0;
            location playerLoc = getLocation(player);
            if (isIdValid(playerLoc.cell))
            {
                for (int i = 0; i < membersApprovedByShipOwner.length; ++i)
                {
                    if (membersApprovedByShipOwner[i] != player && exists(membersApprovedByShipOwner[i]) && getLocation(membersApprovedByShipOwner[i]).cell == playerLoc.cell)
                    {
                        if (features.isSpaceEdition(membersApprovedByShipOwner[i]))
                        {
                            startIndex = space_transition.getNextStartIndex(shipStartLocations, startIndex);
                            if (startIndex <= shipStartLocations.size())
                            {
                                groupMembersToWarp = utils.addElement(groupMembersToWarp, membersApprovedByShipOwner[i]);
                                groupMemberStartIndex = utils.addElement(groupMemberStartIndex, startIndex);
                            }
                        }
                        else 
                        {
                            string_id strSpam = new string_id("space/space_interaction", "no_space_expansion");
                            sendSystemMessage(membersApprovedByShipOwner[i], strSpam);
                        }
                    }
                }
            }
        }
        for (int i = 0; i < groupMembersToWarp.size(); ++i)
        {
            if (callable.hasAnyCallable(((obj_id)groupMembersToWarp.get(i))))
            {
                callable.storeCallables(((obj_id)groupMembersToWarp.get(i)));
            }
            stealth.checkForAndMakeVisible(((obj_id)groupMembersToWarp.get(i)));
            shapechange = buff.getBuffOnTargetFromGroup(((obj_id)groupMembersToWarp.get(i)), "shapechange");
            if (shapechange != 0)
            {
                buff.removeBuff(((obj_id)groupMembersToWarp.get(i)), shapechange);
                sendSystemMessage(((obj_id)groupMembersToWarp.get(i)), event_perk.SHAPECHANGE_SPACE);
            }
            space_transition.setLaunchInfo(((obj_id)groupMembersToWarp.get(i)), ship, ((Integer)groupMemberStartIndex.get(i)).intValue(), groundLoc);
            warpPlayer(((obj_id)groupMembersToWarp.get(i)), warpLocation.area, warpLocation.x, warpLocation.y, warpLocation.z, null, warpLocation.x, warpLocation.y, warpLocation.z);
        }
    }
    public boolean doSpacePrecheck(obj_id objPlayer) throws InterruptedException
    {
        if (isIncapacitated(objPlayer))
        {
            string_id strSpam = new string_id("space/space_interaction", "no_use_terminal_incapacitated");
            sendSystemMessage(objPlayer, strSpam);
            return false;
        }
        if (isDead(objPlayer))
        {
            string_id strSpam = new string_id("space/space_interaction", "no_use_terminal_dead");
            sendSystemMessage(objPlayer, strSpam);
            return false;
        }
        int intState = getState(objPlayer, STATE_COMBAT);
        if (intState > 0)
        {
            string_id strSpam = new string_id("space/space_interaction", "no_use_terminal_combat");
            sendSystemMessage(objPlayer, strSpam);
            return false;
        }
        return true;
    }
    public void doHyperspaceSelector(obj_id self, obj_id objPlayer) throws InterruptedException
    {
        String strFileName = "datatables/space/hyperspace/hyperspace_locations.iff";
        String[] strPointNames = dataTableGetStringColumnNoDefaults(strFileName, "HYPERSPACE_POINT_NAME");
        utils.setScriptVar(self, "strPointNames", strPointNames);
        String[] strLocalizedNames = new String[strPointNames.length];
        for (int intI = 0; intI < strPointNames.length; intI++)
        {
            strLocalizedNames[intI] = utils.packStringId(new string_id("hyperspace_points_n", strPointNames[intI]));
        }
        String strPrompt = "Choose a Hyperspace point";
        sui.listbox(self, objPlayer, strPrompt, strLocalizedNames, "chooseHyperspaceSpot");
    }
    public int chooseHyperspaceSpot(obj_id self, dictionary params) throws InterruptedException
    {
        if (params == null)
        {
            LOG("space", "params");
            return SCRIPT_CONTINUE;
        }
        obj_id player = sui.getPlayerId(params);
        if (!isIdValid(player))
        {
            LOG("space", "invalid ");
            return SCRIPT_CONTINUE;
        }
        int btn = sui.getIntButtonPressed(params);
        if (btn == sui.BP_CANCEL)
        {
            LOG("space", "cancel");
            return SCRIPT_CONTINUE;
        }
        int intIndex = sui.getListboxSelectedRow(params);
        if (intIndex == -1)
        {
            LOG("space", "Row");
            return SCRIPT_CONTINUE;
        }
        String[] strPointNames = utils.getStringArrayScriptVar(self, "strPointNames");
        if (intIndex > strPointNames.length - 1)
        {
            LOG("space", "array length");
            return SCRIPT_CONTINUE;
        }
        String strFileName = "datatables/space/hyperspace/hyperspace_locations.iff";
        dictionary dctPointInfo = dataTableGetRow(strFileName, strPointNames[intIndex]);
        if (dctPointInfo == null)
        {
            LOG("space", "no point");
            return SCRIPT_CONTINUE;
        }
        location warpLocation = new location();
        warpLocation.x = (float)dctPointInfo.getInt("X");
        warpLocation.y = (float)dctPointInfo.getInt("Y");
        warpLocation.z = (float)dctPointInfo.getInt("Z");
        warpLocation.area = dctPointInfo.getString("SCENE");
        obj_id shipControlDevice = utils.getObjIdLocalVar(player, "objControlDevice");
        LOG("space", "shipControlDevice is " + shipControlDevice);
        obj_id ship = space_transition.getShipFromShipControlDevice(shipControlDevice);
        if (!space_utils.isShipUsable(ship, player))
        {
            LOG("space", "bnadship");
            return SCRIPT_CONTINUE;
        }
        if (space_utils.isShipWithInterior(ship))
        {
        }
        if (isIdValid(ship))
        {
            obj_id[] membersApprovedByShipOwner = new obj_id[0];
            location locDestination = space_utils.getRandomLocationInSphere(warpLocation, 150, 300);
            location groundLoc = utils.getLocationScriptVar(self, "space.loc.ground");
            launch(player, ship, membersApprovedByShipOwner, locDestination, groundLoc);
            return SCRIPT_CONTINUE;
        }
        else 
        {
        }
        sendDirtyObjectMenuNotification(self);
        return SCRIPT_CONTINUE;
    }
    public location getFinalHyperspaceLocation(obj_id objPlayer, location locTest) throws InterruptedException
    {
        return locTest;
    }
    public location checkZonePopulation(location[] locTest) throws InterruptedException
    {
        for (int intI = 0; intI < locTest.length; intI++)
        {
            if (!isAreaTooFullForTravel(locTest[intI].area, 0, 0))
            {
                return locTest[intI];
            }
        }
        return null;
    }
    public boolean blog(String category, String msg) throws InterruptedException
    {
        return true;
    }
}
