package script.working.rdelashmit;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.utils;
import script.library.space_utils;
import script.library.space_transition;

public class new_ship_launch extends script.base_script
{
    public new_ship_launch()
    {
    }
    public int OnSpeaking(obj_id self, String text) throws InterruptedException
    {
        if (text.equalsIgnoreCase("beginLaunch"))
        {
            obj_id scd = space_utils.hasUsableShip(self);
            if (isIdValid(scd))
            {
                obj_id ship = space_transition.getShipFromShipControlDevice(scd);
                if (isIdValid(ship))
                {
                    beginLaunch(self, ship, new location(100.f, 0.f, 100.f, "space_tatooine"));
                }
            }
        }
        else if (text.equalsIgnoreCase("confirmLaunch"))
        {
            confirmLaunch(self);
        }
        else if (text.equalsIgnoreCase("finishLaunch"))
        {
            finishLaunch(self);
        }
        return SCRIPT_CONTINUE;
    }
    public void beginLaunch(obj_id player, obj_id ship, location destination) throws InterruptedException
    {
        Vector potentialPassengers = null;
        Vector shipStartLocations = space_transition.getShipStartLocations(ship);
        if (shipStartLocations != null && shipStartLocations.size() > 0)
        {
            obj_id playerBuilding = getTopMostContainer(player);
            obj_id group = getGroupObject(player);
            if (isIdValid(group))
            {
                obj_id[] groupMembers = getGroupMemberIds(group);
                for (int i = 0; i < groupMembers.length; ++i)
                {
                    obj_id groupMember = groupMembers[i];
                    if (groupMember != player && exists(groupMember) && getTopMostContainer(groupMember) == playerBuilding)
                    {
                        potentialPassengers = utils.addElement(potentialPassengers, groupMember);
                    }
                }
            }
        }
        Vector launchees = null;
        launchees = utils.addElement(launchees, player);
        utils.setScriptVar(player, "launch.launchees", launchees);
        utils.setScriptVar(player, "launch.ship", ship);
        utils.setScriptVar(player, "launch.destination", destination);
        if (potentialPassengers != null)
        {
            for (int i = 0; i < potentialPassengers.size(); ++i)
            {
                dictionary params = new dictionary();
                params.put("launcher", player);
                messageTo(((obj_id)potentialPassengers.get(i)), "inviteForPobShipLaunch", params, 0, false);
            }
        }
        else 
        {
            finishLaunch(player);
        }
    }
    public int inviteForPobShipLaunch(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id launcher = params.getObjId("launcher");
        prose_package pp = new prose_package();
        pp.actor.set(getName(launcher));
        pp.stringId = new string_id("space/space_interaction", "invite_for_launch");
        sendSystemMessageProse(self, pp);
        utils.setScriptVar(self, "launch.launcher", launcher);
        return SCRIPT_CONTINUE;
    }
    public void confirmLaunch(obj_id player) throws InterruptedException
    {
        obj_id launcher = utils.getObjIdScriptVar(player, "launch.launcher");
        if (isIdValid(launcher))
        {
            dictionary params = new dictionary();
            params.put("launchee", player);
            messageTo(launcher, "confirmForPobShipLaunch", params, 0, false);
        }
        utils.removeScriptVar(player, "launch.launcher");
    }
    public int confirmForPobShipLaunch(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id launchee = params.getObjId("launchee");
        Vector launchees = utils.getResizeableObjIdArrayScriptVar(self, "launch.launchees");
        if (launchees != null)
        {
            launchees = utils.addElement(launchees, launchee);
            utils.setScriptVar(self, "launch.launchees", launchees);
            prose_package pp = new prose_package();
            pp.actor.set(getName(launchee));
            pp.stringId = new string_id("space/space_interaction", "confirm_for_launch");
            sendSystemMessageProse(self, pp);
        }
        return SCRIPT_CONTINUE;
    }
    public void finishLaunch(obj_id player) throws InterruptedException
    {
        Vector launchees = utils.getResizeableObjIdArrayScriptVar(player, "launch.launchees");
        obj_id ship = utils.getObjIdScriptVar(player, "launch.ship");
        location destination = utils.getLocationScriptVar(player, "launch.destination");
        location groundLoc = getLocation(getTopMostContainer(player));
        Vector groupMembersToWarp = utils.addElement(null, player);
        Vector groupMemberStartIndex = utils.addElement(null, 0);
        Vector shipStartLocations = space_transition.getShipStartLocations(ship);
        if (shipStartLocations != null && shipStartLocations.size() > 0)
        {
            int startIndex = 0;
            obj_id playerBuilding = getTopMostContainer(player);
            obj_id group = getGroupObject(player);
            if (isIdValid(group))
            {
                obj_id[] groupMembers = getGroupMemberIds(group);
                for (int i = 1; i < launchees.size(); ++i)
                {
                    obj_id launchee = ((obj_id)launchees.get(i));
                    boolean ok = false;
                    for (int j = 0; j < groupMembers.length; ++j)
                    {
                        if (groupMembers[j] == launchee && exists(launchee) && getTopMostContainer(launchee) == playerBuilding)
                        {
                            ok = true;
                        }
                    }
                    if (ok)
                    {
                        startIndex = getNextStartIndex(shipStartLocations, startIndex);
                        if (startIndex <= shipStartLocations.size())
                        {
                            groupMembersToWarp = utils.addElement(groupMembersToWarp, ((obj_id)launchees.get(i)));
                            groupMemberStartIndex = utils.addElement(groupMemberStartIndex, startIndex);
                        }
                    }
                }
            }
        }
        launch(groupMembersToWarp, groupMemberStartIndex, ship, destination, groundLoc);
    }
    public void launch(Vector players, Vector playerStartIndex, obj_id ship, location destination, location groundLoc) throws InterruptedException
    {
        for (int i = 0; i < players.size(); ++i)
        {
            launch(((obj_id)players.get(i)), ((Integer)playerStartIndex.get(i)).intValue(), ship, destination, groundLoc);
        }
    }
    public void launch(obj_id player, int playerStartIndex, obj_id ship, location destination, location groundLoc) throws InterruptedException
    {
        utils.removeScriptVar(player, "launch.ship");
        utils.removeScriptVar(player, "launch.destination");
        utils.removeScriptVar(player, "launch.launcher");
        utils.removeScriptVar(player, "launch.launchees");
        space_transition.setLaunchInfo(player, ship, playerStartIndex, groundLoc);
        warpPlayer(player, destination.area, destination.x, destination.y, destination.z, null, destination.x, destination.y, destination.z);
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
}
