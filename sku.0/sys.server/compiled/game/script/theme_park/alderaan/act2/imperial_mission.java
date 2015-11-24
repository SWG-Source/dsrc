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

public class imperial_mission extends script.base_script
{
    public imperial_mission()
    {
    }
    public static final String IMPERIAL_STF = "theme_park/alderaan/act2/imperial_missions";
    public static final String IMPERIAL_SHARED_STF = "theme_park/alderaan/act2/shared_imperial_missions";
    public int OnAttach(obj_id self) throws InterruptedException
    {
        if (hasScript(self, "theme_park.alderaan.act2.rebel_mission"))
        {
            detachScript(self, "theme_park.alderaan.act2.rebel_mission");
        }
        startImperialMission(self);
        return SCRIPT_CONTINUE;
    }
    public int OnDetach(obj_id self) throws InterruptedException
    {
        if (hasObjVar(self, "coa2.imperial"))
        {
            int missionNum = getIntObjVar(self, "coa2.imperial.missionNum");
            abortImperialMission(self, missionNum, false);
        }
        return SCRIPT_CONTINUE;
    }
    public int OnArrivedAtLocation(obj_id self, String name) throws InterruptedException
    {
        int missionNum = getIntObjVar(self, "coa2.imperial.missionNum");
        if (name.equals("missionSpawner"))
        {
            location missionLoc = getLocationObjVar(self, "coa2.imperial.missionLoc");
            String missionNpc = getStringObjVar(self, "coa2.imperial.missionNpc");
            if (missionNpc != null && !missionNpc.equals(""))
            {
                obj_id npc = create.object(missionNpc, missionLoc);
                setObjVar(self, "coa2.imperial.missionNpcId", npc);
                setObjVar(npc, "coa2.imperial.playerId", self);
            }
            else 
            {
                removeObjVar(self, "coa2.imperial.missionNpcId");
            }
        }
        if (name.equals("missionDest"))
        {
            setObjVar(self, "coa2.imperial.success", 1);
            if (hasObjVar(self, "coa2.imperial.missionNpcId"))
            {
                obj_id npc = getObjIdObjVar(self, "coa2.imperial.missionNpcId");
                if (!isIdValid(npc))
                {
                    string_id error = new string_id(IMPERIAL_SHARED_STF, "m" + missionNum + "_init_failure");
                    sendSystemMessage(self, error);
                    abortImperialMission(self, missionNum, true);
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
            setObjVar(self, "coa2.imperial.success", 1);
            removeMissionWaypoint(self);
            removeLocationTarget(name);
        }
        return SCRIPT_CONTINUE;
    }
    public int handleRestartMission(obj_id self, dictionary params) throws InterruptedException
    {
        setObjVar(self, "coa2.imperial.success", 1);
        removeMissionWaypoint(self);
        startImperialMission(self);
        return SCRIPT_CONTINUE;
    }
    public int handleAbortMission(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id player = params.getObjId("player");
        int missionNum = params.getInt("value");
        abortImperialMission(player, missionNum, false);
        return SCRIPT_CONTINUE;
    }
    public int createReturnMission(obj_id self, dictionary params) throws InterruptedException
    {
        if (hasObjVar(self, "coa2.imperial.waypoint"))
        {
            removeMissionWaypoint(self);
        }
        int missionNum = getIntObjVar(self, "coa2.imperial.missionNum");
        location returnLoc = getLocationObjVar(self, "coa2.imperial.returnLoc");
        string_id waypointName = new string_id(IMPERIAL_SHARED_STF, "waypoint_return_name_" + missionNum);
        string_id waypointDesc = new string_id(IMPERIAL_SHARED_STF, "waypoint_return_desc_" + missionNum);
        obj_id waypoint = createMissionWaypoint(self, returnLoc, 20, getString(waypointName), "returnDest");
        if (isIdValid(waypoint))
        {
            setObjVar(self, "coa2.imperial.waypointDesc", waypointDesc);
        }
        return SCRIPT_CONTINUE;
    }
    public void startImperialMission(obj_id player) throws InterruptedException
    {
        int missionNum = getIntObjVar(player, "coa2.imperial.missionNum");
        location missionLoc = getLocationObjVar(player, "coa2.imperial.missionLoc");
        string_id waypointName = new string_id(IMPERIAL_SHARED_STF, "waypoint_name_" + missionNum);
        string_id waypointDesc = new string_id(IMPERIAL_SHARED_STF, "waypoint_desc_" + missionNum);
        obj_id waypoint = createMissionWaypoint(player, missionLoc, 20, getString(waypointName), "missionDest");
        if (isIdValid(waypoint))
        {
            setObjVar(player, "coa2.imperial.waypointDesc", waypointDesc);
            addLocationTarget("missionSpawner", missionLoc, 300);
        }
        else 
        {
            string_id error = new string_id(IMPERIAL_SHARED_STF, "waypoint_failure");
            sendSystemMessage(player, error);
        }
    }
    public void abortImperialMission(obj_id player, int mission, boolean returnHome) throws InterruptedException
    {
        removeMissionWaypoint(player);
        if (returnHome)
        {
            messageTo(player, "createReturnMission", null, 1, false);
        }
        else 
        {
            revertMissionProgress(player, mission);
            removeObjVar(player, "coa2.imperial");
            detachScript(player, "theme_park.alderaan.act2.imperial_mission");
        }
    }
    public void revertMissionProgress(obj_id player, int mission) throws InterruptedException
    {
        switch (mission)
        {
            case 1:
            setObjVar(player, "coa2.progress", 0);
            break;
            case 2:
            setObjVar(player, "coa2.progress", -3);
            break;
            case 3:
            setObjVar(player, "coa2.progress", -3);
            break;
            case 4:
            setObjVar(player, "coa2.progress", -6);
            break;
            case 5:
            setObjVar(player, "coa2.progress", -8);
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
            setObjVar(player, "coa2.imperial.waypoint", waypoint);
            setObjVar(player, "coa2.imperial.volume", volumeName);
        }
        return waypoint;
    }
    public void removeMissionWaypoint(obj_id player) throws InterruptedException
    {
        obj_id waypoint = getObjIdObjVar(player, "coa2.imperial.waypoint");
        if (hasObjVar(player, "coa2.imperial.volume"))
        {
            String name = getStringObjVar(player, "coa2.imperial.volume");
            removeLocationTarget(name);
            if (name.equals("missionDest"))
            {
                removeLocationTarget("missionSpawner");
            }
            removeObjVar(player, "coa2.imperial.volume");
        }
        setWaypointVisible(waypoint, false);
        setWaypointActive(waypoint, false);
        destroyWaypointInDatapad(waypoint, player);
        removeObjVar(player, "coa2.imperial.waypoint");
    }
    public int OnWaypointGetAttributes(obj_id self, obj_id waypoint, obj_id player, String[] names, String[] attribs) throws InterruptedException
    {
        obj_id wp = getObjIdObjVar(player, "coa2.imperial.waypoint");
        if (waypoint != wp)
        {
            return SCRIPT_CONTINUE;
        }
        int idx = utils.getValidAttributeIndex(names);
        if (idx == -1)
        {
            return SCRIPT_CONTINUE;
        }
        string_id waypointDesc = getStringIdObjVar(player, "coa2.imperial.waypointDesc");
        names[idx] = "mission_details";
        attribs[idx] = "@" + waypointDesc.toString();
        return SCRIPT_CONTINUE;
    }
    public int OnWaypointDestroyed(obj_id self, obj_id waypoint) throws InterruptedException
    {
        obj_id wp = getObjIdObjVar(self, "coa2.imperial.waypoint");
        if (waypoint != wp)
        {
            return SCRIPT_CONTINUE;
        }
        if (hasObjVar(self, "coa2.imperial.success"))
        {
            removeObjVar(self, "coa2.imperial.success");
        }
        else 
        {
            int missionNum = getIntObjVar(self, "coa2.imperial.missionNum");
            revertMissionProgress(self, missionNum);
            debugSpeakMsg(self, "Reverting Mission Progress - " + missionNum);
        }
        removeObjVar(self, "coa2.imperial.waypoint");
        removeObjVar(self, "coa2.imperial.volume");
        return SCRIPT_CONTINUE;
    }
}
