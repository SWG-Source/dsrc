package script.theme_park.alderaan.act2;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.utils;
import script.library.create;

public class rebel_mission extends script.base_script
{
    public rebel_mission()
    {
    }
    public static final String REBEL_STF = "theme_park/alderaan/act2/rebel_missions";
    public static final String REBEL_SHARED_STF = "theme_park/alderaan/act2/shared_rebel_missions";
    public int OnAttach(obj_id self) throws InterruptedException
    {
        if (hasScript(self, "theme_park.alderaan.act2.imperial_mission"))
        {
            detachScript(self, "theme_park.alderaan.act2.imperial_mission");
        }
        startRebelMission(self);
        return SCRIPT_CONTINUE;
    }
    public int OnDetach(obj_id self) throws InterruptedException
    {
        if (hasObjVar(self, "coa2.rebel"))
        {
            int missionNum = getIntObjVar(self, "coa2.rebel.missionNum");
            abortRebelMission(self, missionNum, false);
        }
        return SCRIPT_CONTINUE;
    }
    public int OnArrivedAtLocation(obj_id self, String name) throws InterruptedException
    {
        int missionNum = getIntObjVar(self, "coa2.rebel.missionNum");
        if (name.equals("missionSpawner"))
        {
            location missionLoc = getLocationObjVar(self, "coa2.rebel.missionLoc");
            String missionNpc = getStringObjVar(self, "coa2.rebel.missionNpc");
            if (missionNpc != null && !missionNpc.equals(""))
            {
                obj_id npc = create.object(missionNpc, missionLoc);
                setObjVar(self, "coa2.rebel.missionNpcId", npc);
                setObjVar(npc, "coa2.rebel.playerId", self);
            }
            else 
            {
                removeObjVar(self, "coa2.rebel.missionNpcId");
            }
        }
        if (name.equals("missionDest"))
        {
            setObjVar(self, "coa2.rebel.success", 1);
            if (hasObjVar(self, "coa2.rebel.missionNpcId"))
            {
                obj_id npc = getObjIdObjVar(self, "coa2.rebel.missionNpcId");
                if (!isIdValid(npc))
                {
                    string_id error = new string_id(REBEL_SHARED_STF, "m" + missionNum + "_init_failure");
                    sendSystemMessage(self, error);
                    abortRebelMission(self, missionNum, true);
                }
                else 
                {
                    removeMissionWaypoint(self);
                }
            }
            else 
            {
                removeMissionWaypoint(self);
            }
        }
        if (name.equals("returnDest"))
        {
            setObjVar(self, "coa2.rebel.success", 1);
            removeMissionWaypoint(self);
            removeLocationTarget(name);
        }
        return SCRIPT_CONTINUE;
    }
    public int handleRestartMission(obj_id self, dictionary params) throws InterruptedException
    {
        setObjVar(self, "coa2.rebel.success", 1);
        removeMissionWaypoint(self);
        startRebelMission(self);
        return SCRIPT_CONTINUE;
    }
    public int handleAbortMission(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id player = params.getObjId("player");
        int missionNum = params.getInt("value");
        abortRebelMission(player, missionNum, false);
        return SCRIPT_CONTINUE;
    }
    public int createReturnMission(obj_id self, dictionary params) throws InterruptedException
    {
        if (hasObjVar(self, "coa2.rebel.waypoint"))
        {
            removeMissionWaypoint(self);
        }
        int missionNum = getIntObjVar(self, "coa2.rebel.missionNum");
        location returnLoc = getLocationObjVar(self, "coa2.rebel.returnLoc");
        string_id waypointName = new string_id(REBEL_SHARED_STF, "waypoint_return_name_" + missionNum);
        string_id waypointDesc = new string_id(REBEL_SHARED_STF, "waypoint_return_desc_" + missionNum);
        obj_id waypoint = createMissionWaypoint(self, returnLoc, 20, getString(waypointName), "returnDest");
        if (isIdValid(waypoint))
        {
            setObjVar(self, "coa2.rebel.waypointDesc", waypointDesc);
        }
        return SCRIPT_CONTINUE;
    }
    public void startRebelMission(obj_id player) throws InterruptedException
    {
        int missionNum = getIntObjVar(player, "coa2.rebel.missionNum");
        location missionLoc = getLocationObjVar(player, "coa2.rebel.missionLoc");
        string_id waypointName = new string_id(REBEL_SHARED_STF, "waypoint_name_" + missionNum);
        string_id waypointDesc = new string_id(REBEL_SHARED_STF, "waypoint_desc_" + missionNum);
        obj_id waypoint = createMissionWaypoint(player, missionLoc, 20, getString(waypointName), "missionDest");
        if (isIdValid(waypoint))
        {
            setObjVar(player, "coa2.rebel.waypointDesc", waypointDesc);
            addLocationTarget("missionSpawner", missionLoc, 300);
        }
        else 
        {
            string_id error = new string_id(REBEL_SHARED_STF, "waypoint_failure");
            sendSystemMessage(player, error);
        }
    }
    public void abortRebelMission(obj_id player, int mission, boolean returnHome) throws InterruptedException
    {
        removeMissionWaypoint(player);
        if (returnHome)
        {
            messageTo(player, "createReturnMission", null, 1, false);
        }
        else 
        {
            revertMissionProgress(player, mission);
            removeObjVar(player, "coa2.rebel");
            detachScript(player, "theme_park.alderaan.act2.rebel_mission");
        }
    }
    public void revertMissionProgress(obj_id player, int mission) throws InterruptedException
    {
        switch (mission)
        {
            case 1:
            setObjVar(player, "coa2.progress", 1);
            break;
            case 2:
            setObjVar(player, "coa2.progress", 1);
            break;
            case 3:
            setObjVar(player, "coa2.progress", 6);
            break;
            case 4:
            setObjVar(player, "coa2.progress", 9);
            break;
            case 5:
            setObjVar(player, "coa2.progress", 12);
            obj_id device = utils.getItemPlayerHasByTemplate(player, "object/tangible/theme_park/alderaan/act2/interface_override_device.iff");
            if (isIdValid(device))
            {
                destroyObject(device);
            }
            obj_id disk = utils.getItemPlayerHasByTemplate(player, "object/tangible/encoded_disk/imperial_slicer_disk.iff");
            if (isIdValid(disk))
            {
                destroyObject(disk);
            }
            break;
        }
    }
    public obj_id createMissionWaypoint(obj_id player, location loc, float radius, String waypointName, String volumeName) throws InterruptedException
    {
        addLocationTarget(volumeName, loc, radius);
        obj_id waypoint = createWaypointInDatapad(player, loc);
        if (isIdValid(waypoint))
        {
            setWaypointActive(waypoint, true);
            setWaypointName(waypoint, waypointName);
            setObjVar(player, "coa2.rebel.waypoint", waypoint);
            setObjVar(player, "coa2.rebel.volume", volumeName);
        }
        return waypoint;
    }
    public void removeMissionWaypoint(obj_id player) throws InterruptedException
    {
        obj_id waypoint = getObjIdObjVar(player, "coa2.rebel.waypoint");
        if (hasObjVar(player, "coa2.rebel.volume"))
        {
            String name = getStringObjVar(player, "coa2.rebel.volume");
            removeLocationTarget(name);
            if (name.equals("missionDest"))
            {
                removeLocationTarget("missionSpawner");
            }
            removeObjVar(player, "coa2.rebel.volume");
        }
        setWaypointVisible(waypoint, false);
        setWaypointActive(waypoint, false);
        destroyWaypointInDatapad(waypoint, player);
        removeObjVar(player, "coa2.rebel.waypoint");
    }
    public int OnWaypointGetAttributes(obj_id self, obj_id waypoint, obj_id player, String[] names, String[] attribs) throws InterruptedException
    {
        obj_id wp = getObjIdObjVar(player, "coa2.rebel.waypoint");
        if (waypoint != wp)
        {
            return SCRIPT_CONTINUE;
        }
        int idx = utils.getValidAttributeIndex(names);
        if (idx == -1)
        {
            return SCRIPT_CONTINUE;
        }
        string_id waypointDesc = getStringIdObjVar(player, "coa2.rebel.waypointDesc");
        names[idx] = "mission_details";
        attribs[idx] = "@" + waypointDesc.toString();
        return SCRIPT_CONTINUE;
    }
    public int OnWaypointDestroyed(obj_id self, obj_id waypoint) throws InterruptedException
    {
        obj_id wp = getObjIdObjVar(self, "coa2.rebel.waypoint");
        if (waypoint != wp)
        {
            return SCRIPT_CONTINUE;
        }
        if (hasObjVar(self, "coa2.rebel.success"))
        {
            removeObjVar(self, "coa2.rebel.success");
        }
        else 
        {
            int missionNum = getIntObjVar(self, "coa2.rebel.missionNum");
            revertMissionProgress(self, missionNum);
            debugSpeakMsg(self, "Reverting Mission Progress - " + missionNum);
        }
        removeObjVar(self, "coa2.rebel.waypoint");
        removeObjVar(self, "coa2.rebel.volume");
        return SCRIPT_CONTINUE;
    }
}
