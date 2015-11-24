package script.working.wwallace;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.space_utils;
import script.library.space_combat;
import script.library.space_transition;
import script.library.space_crafting;
import script.library.create;
import script.library.utils;
import script.library.sui;
import script.library.space_create;

public class space_mining_test extends script.base_script
{
    public space_mining_test()
    {
    }
    public static final String SPACE_MINING_STF = "space_mining";
    public int OnSpeaking(obj_id self, String strText) throws InterruptedException
    {
        String[] strCommands = split(strText, ' ');
        obj_id objInventory = utils.getInventoryContainer(self);
        if (strCommands[0].equalsIgnoreCase("letsGoMining"))
        {
            obj_id weapon1 = createObjectOverloaded("object/tangible/ship/components/weapon/wpn_mining_laser_mk2.iff", objInventory);
            obj_id weapon2 = createObjectOverloaded("object/tangible/ship/components/weapon/wpn_tractor_pulse_gun.iff", objInventory);
            obj_id newship = space_utils.createShipControlDevice(self, "xwing", true);
            if (isIdValid(newship))
            {
                obj_id ship = space_transition.getShipFromShipControlDevice(newship);
                obj_id comp1 = shipUninstallComponent(self, ship, 12, objInventory);
                destroyObject(comp1);
                shipInstallComponent(self, ship, 12, weapon1);
                obj_id comp2 = shipUninstallComponent(self, ship, 13, objInventory);
                destroyObject(comp2);
                shipInstallComponent(self, ship, 13, weapon2);
            }
            return SCRIPT_CONTINUE;
        }
        if (strCommands[0].equalsIgnoreCase("asteroidScan"))
        {
            location selfLocation = getLocation(self);
            obj_id[] objectsInRange = getObjectsInRange(selfLocation, 10000.0f);
            Vector entries = new Vector();
            entries.setSize(0);
            Vector locations = new Vector();
            locations.setSize(0);
            for (int i = 0; i < objectsInRange.length; i++)
            {
                if (hasObjVar(objectsInRange[i], "mining_asteroid.numShipsSpawned"))
                {
                    sendSystemMessageTestingOnly(self, "Found asteroid within 10000 meters!");
                    String strAsteroidType = getStringObjVar(objectsInRange[i], "strAsteroidType");
                    location asteroid = getLocation(objectsInRange[i]);
                    locations = utils.addElement(locations, asteroid);
                    entries = utils.addElement(entries, "Found asteroid of type: " + strAsteroidType + " at location " + asteroid);
                }
            }
            String prompt = "Asteroid Scan Results!  Pick an asteroid and get a waypoint, yo.";
            String title = "Asteroid Scan Results";
            utils.setScriptVar(self, "mining_scan.locations", locations);
            int pid = sui.listbox(self, self, prompt, sui.OK_ONLY, title, entries, "handleGiveWaypoint", false, false);
            if (pid > -1)
            {
                showSUIPage(pid);
            }
            return SCRIPT_CONTINUE;
        }
        return SCRIPT_CONTINUE;
    }
    public int handleGiveWaypoint(obj_id self, dictionary params) throws InterruptedException
    {
        int intButton = sui.getIntButtonPressed(params);
        if (intButton == sui.BP_CANCEL)
        {
            return SCRIPT_CONTINUE;
        }
        int row = sui.getListboxSelectedRow(params);
        location[] locations = utils.getLocationArrayScriptVar(self, "mining_scan.locations");
        location waypointLoc = locations[row];
        obj_id waypoint = createWaypointInDatapad(self, waypointLoc);
        setName(waypoint, "Asteroids are here");
        return SCRIPT_CONTINUE;
    }
    public void launch(obj_id player, obj_id ship, obj_id[] membersApprovedByShipOwner, location warpLocation, location groundLoc) throws InterruptedException
    {
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
                obj_id group = getGroupObject(player);
                if (isIdValid(group))
                {
                    obj_id[] groupMembers = getGroupMemberIds(group);
                    for (int i = 0; i < groupMembers.length; ++i)
                    {
                        if (groupMembers[i] != player && exists(groupMembers[i]) && getLocation(groupMembers[i]).cell == playerLoc.cell && groupMemberApproved(membersApprovedByShipOwner, groupMembers[i]))
                        {
                            startIndex = getNextStartIndex(shipStartLocations, startIndex);
                            if (startIndex <= shipStartLocations.size())
                            {
                                groupMembersToWarp = utils.addElement(groupMembersToWarp, groupMembers[i]);
                                groupMemberStartIndex = utils.addElement(groupMemberStartIndex, startIndex);
                            }
                            else 
                            {
                                string_id strSpam = new string_id("space/space_interaction", "no_space_expansion");
                                sendSystemMessage(groupMembers[i], strSpam);
                            }
                        }
                    }
                }
            }
        }
        for (int i = 0; i < groupMembersToWarp.size(); ++i)
        {
            space_transition.setLaunchInfo(((obj_id)groupMembersToWarp.get(i)), ship, ((Integer)groupMemberStartIndex.get(i)).intValue(), groundLoc);
            warpPlayer(((obj_id)groupMembersToWarp.get(i)), warpLocation.area, warpLocation.x, warpLocation.y, warpLocation.z, null, warpLocation.x, warpLocation.y, warpLocation.z);
        }
    }
    public int getNextStartIndex(Vector shipStartLocations, int lastStartIndex) throws InterruptedException
    {
        int startIndex = lastStartIndex + 1;
        if (startIndex > shipStartLocations.size())
        {
            for (startIndex = 1; startIndex <= shipStartLocations.size(); ++startIndex)
            {
                if (((location)shipStartLocations.get(startIndex - 1)).cell != null)
                {
                    break;
                }
            }
        }
        return startIndex;
    }
    public boolean groupMemberApproved(obj_id[] membersApprovedByShipOwner, obj_id memberToTest) throws InterruptedException
    {
        for (int i = 0; i < membersApprovedByShipOwner.length; ++i)
        {
            if (membersApprovedByShipOwner[i] == memberToTest)
            {
                return true;
            }
        }
        return false;
    }
}
