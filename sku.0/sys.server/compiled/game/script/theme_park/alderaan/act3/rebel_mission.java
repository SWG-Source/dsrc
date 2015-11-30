package script.theme_park.alderaan.act3;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.utils;
import script.library.create;
import script.library.locations;

public class rebel_mission extends script.base_script
{
    public rebel_mission()
    {
    }
    public static final String REBEL_STF = "theme_park/alderaan/act3/rebel_missions";
    public static final String REBEL_SHARED_STF = "theme_park/alderaan/act3/shared_rebel_missions";
    public int OnAttach(obj_id self) throws InterruptedException
    {
        if (hasScript(self, "theme_park.alderaan.act3.imperial_mission"))
        {
            detachScript(self, "theme_park.alderaan.act3.imperial_mission");
        }
        startRebelMission(self);
        return SCRIPT_CONTINUE;
    }
    public int OnDetach(obj_id self) throws InterruptedException
    {
        if (hasObjVar(self, "coa3.rebel"))
        {
            int missionNum = getIntObjVar(self, "coa3.rebel.missionNum");
            abortRebelMission(self, missionNum, false);
        }
        return SCRIPT_CONTINUE;
    }
    public int OnLogin(obj_id self) throws InterruptedException
    {
        if (hasObjVar(self, "coa3.rebel.redirect"))
        {
            String planet = getStringObjVar(self, "coa3.rebel.redirect.planet");
            String scene = getCurrentSceneName();
            if (planet.equals(scene))
            {
                setObjVar(self, "coa3.rebel.success", 1);
                removeMissionWaypoint(self);
                int missionNum = getIntObjVar(self, "coa3.rebel.missionNum");
                int minDistance = getIntObjVar(self, "coa3.rebel.redirect.minDistance");
                int maxDistance = getIntObjVar(self, "coa3.rebel.redirect.maxDistance");
                if (maxDistance > 0)
                {
                    location missionLoc = getDeliveryLoc(self, minDistance, maxDistance);
                    string_id waypointName = new string_id(REBEL_SHARED_STF, "waypoint_name_" + missionNum);
                    string_id waypointDesc = new string_id(REBEL_SHARED_STF, "waypoint_desc_" + missionNum);
                    createMissionWaypoint(self, missionLoc, 20, getString(waypointName), "missionDest");
                    setObjVar(self, "coa3.rebel.missionLoc", missionLoc);
                    setObjVar(self, "coa3.rebel.waypointDesc", waypointDesc);
                    addLocationTarget("missionSpawner", missionLoc, 100);
                }
                else 
                {
                    obj_id wp = getObjIdObjVar(self, "coa3.rebel.waypoint");
                    setWaypointActive(wp, true);
                }
                removeObjVar(self, "coa3.rebel.redirect");
            }
        }
        return SCRIPT_CONTINUE;
    }
    public location getDeliveryLoc(obj_id player, int minDistance, int maxDistance) throws InterruptedException
    {
        location center = getLocation(player);
        location deliveryLoc = null;
        int x = 0;
        while (x < 10)
        {
            LOG("CoA3_Rebel", "Rebel Delivery Location:  Attepmt #" + (x + 1));
            location loc = utils.getRandomLocationInRing(center, minDistance, maxDistance);
            deliveryLoc = locations.getGoodLocationAroundLocation(loc, 30f, 30f, 200f, 200f);
            if (deliveryLoc != null)
            {
                break;
            }
            x += 1;
        }
        deliveryLoc.y = getHeightAtLocation(deliveryLoc.x, deliveryLoc.z);
        return deliveryLoc;
    }
    public int OnArrivedAtLocation(obj_id self, String name) throws InterruptedException
    {
        int missionNum = getIntObjVar(self, "coa3.rebel.missionNum");
        if (name.equals("missionSpawner"))
        {
            location missionLoc = getLocationObjVar(self, "coa3.rebel.missionLoc");
            String missionNpc = getStringObjVar(self, "coa3.rebel.missionNpc");
            if (missionNpc != null && !missionNpc.equals(""))
            {
                obj_id npc = create.object(missionNpc, missionLoc);
                setObjVar(self, "coa3.rebel.missionNpcId", npc);
                setObjVar(npc, "coa3.rebel.playerId", self);
            }
            else 
            {
                removeObjVar(self, "coa3.rebel.missionNpcId");
            }
        }
        if (name.equals("missionDest"))
        {
            setObjVar(self, "coa3.rebel.success", 1);
            if (hasObjVar(self, "coa3.rebel.missionNpcId"))
            {
                obj_id npc = getObjIdObjVar(self, "coa3.rebel.missionNpcId");
                if (!isIdValid(npc) || !exists(npc))
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
            setObjVar(self, "coa3.rebel.success", 1);
            removeMissionWaypoint(self);
            removeLocationTarget(name);
        }
        return SCRIPT_CONTINUE;
    }
    public int handleRestartMission(obj_id self, dictionary params) throws InterruptedException
    {
        setObjVar(self, "coa3.rebel.success", 1);
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
        if (hasObjVar(self, "coa3.rebel.waypoint"))
        {
            removeMissionWaypoint(self);
        }
        int missionNum = getIntObjVar(self, "coa3.rebel.missionNum");
        location returnLoc = getLocationObjVar(self, "coa3.rebel.returnLoc");
        string_id waypointName = new string_id(REBEL_SHARED_STF, "waypoint_return_name_" + missionNum);
        string_id waypointDesc = new string_id(REBEL_SHARED_STF, "waypoint_return_desc_" + missionNum);
        obj_id waypoint = createMissionWaypoint(self, returnLoc, 20, getString(waypointName), "returnDest");
        if (isIdValid(waypoint))
        {
            setObjVar(self, "coa3.rebel.waypointDesc", waypointDesc);
            String planet = returnLoc.area;
            if (!planet.equals(getCurrentSceneName()))
            {
                string_id planetMsg = new string_id("mission/mission_generic", "target_located_" + planet);
                sendSystemMessage(self, planetMsg);
                setObjVar(self, "coa3.rebel.redirect.planet", planet);
                removeObjVar(self, "coa3.rebel.redirect.minDistance");
                removeObjVar(self, "coa3.rebel.redirect.maxDistance");
            }
        }
        if (hasObjVar(self, "coa3.rebel.finalMission"))
        {
            removeObjVar(self, "coa3.rebel");
        }
        return SCRIPT_CONTINUE;
    }
    public void startRebelMission(obj_id player) throws InterruptedException
    {
        int missionNum = getIntObjVar(player, "coa3.rebel.missionNum");
        location missionLoc = getLocationObjVar(player, "coa3.rebel.missionLoc");
        string_id waypointName = new string_id(REBEL_SHARED_STF, "waypoint_name_" + missionNum);
        string_id waypointDesc = new string_id(REBEL_SHARED_STF, "waypoint_desc_" + missionNum);
        obj_id waypoint = createMissionWaypoint(player, missionLoc, 20, getString(waypointName), "missionDest");
        if (isIdValid(waypoint))
        {
            setObjVar(player, "coa3.rebel.waypointDesc", waypointDesc);
            addLocationTarget("missionSpawner", missionLoc, 100);
            String planet = missionLoc.area;
            if (!planet.equals(getCurrentSceneName()))
            {
                string_id planetMsg = new string_id("mission/mission_generic", "target_located_" + planet);
                sendSystemMessage(player, planetMsg);
            }
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
            removeObjVar(player, "coa3.rebel");
            detachScript(player, "theme_park.alderaan.act3.rebel_mission");
        }
    }
    public void revertMissionProgress(obj_id player, int mission) throws InterruptedException
    {
        switch (mission)
        {
            case 1:
            setObjVar(player, "coa3.convTracker", 200);
            break;
            case 2:
            setObjVar(player, "coa3.convTracker", 205);
            break;
            case 3:
            setObjVar(player, "coa3.convTracker", -301);
            break;
            case 4:
            setObjVar(player, "coa3.convTracker", -303);
            break;
            case 5:
            setObjVar(player, "coa3.convTracker", -401);
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
            setObjVar(player, "coa3.rebel.waypoint", waypoint);
            setObjVar(player, "coa3.rebel.volume", volumeName);
        }
        return waypoint;
    }
    public void removeMissionWaypoint(obj_id player) throws InterruptedException
    {
        obj_id waypoint = getObjIdObjVar(player, "coa3.rebel.waypoint");
        if (hasObjVar(player, "coa3.rebel.volume"))
        {
            String name = getStringObjVar(player, "coa3.rebel.volume");
            removeLocationTarget(name);
            if (name.equals("missionDest"))
            {
                removeLocationTarget("missionSpawner");
            }
            removeObjVar(player, "coa3.rebel.volume");
        }
        setWaypointVisible(waypoint, false);
        setWaypointActive(waypoint, false);
        destroyWaypointInDatapad(waypoint, player);
        removeObjVar(player, "coa3.rebel.waypoint");
    }
    public int OnWaypointGetAttributes(obj_id self, obj_id waypoint, obj_id player, String[] names, String[] attribs) throws InterruptedException
    {
        obj_id wp = getObjIdObjVar(player, "coa3.rebel.waypoint");
        if (waypoint != wp)
        {
            return SCRIPT_CONTINUE;
        }
        int idx = utils.getValidAttributeIndex(names);
        if (idx == -1)
        {
            return SCRIPT_CONTINUE;
        }
        string_id waypointDesc = getStringIdObjVar(player, "coa3.rebel.waypointDesc");
        names[idx] = "mission_details";
        attribs[idx] = "@" + waypointDesc.toString();
        return SCRIPT_CONTINUE;
    }
    public int OnWaypointDestroyed(obj_id self, obj_id waypoint) throws InterruptedException
    {
        obj_id wp = getObjIdObjVar(self, "coa3.rebel.waypoint");
        if (waypoint != wp)
        {
            return SCRIPT_CONTINUE;
        }
        if (hasObjVar(self, "coa3.rebel.success"))
        {
            removeObjVar(self, "coa3.rebel.success");
        }
        else 
        {
            int missionNum = getIntObjVar(self, "coa3.rebel.missionNum");
            revertMissionProgress(self, missionNum);
            debugSpeakMsg(self, "Reverting Mission Progress - " + missionNum);
        }
        removeObjVar(self, "coa3.rebel.waypoint");
        removeObjVar(self, "coa3.rebel.volume");
        return SCRIPT_CONTINUE;
    }
    public int handleFloraCheck(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id termUser = params.getObjId("termUser");
        obj_id warehouse = params.getObjId("warehouse");
        obj_id player = params.getObjId("player");
        if (exists(warehouse))
        {
            sendSystemMessage(termUser, "System detects lifeforms inside building, shutting down self destruct", null);
        }
        else 
        {
            if (isIdValid(player))
            {
                int progress = getIntObjVar(player, "coa3.convTracker");
                if (progress == 302)
                {
                    setObjVar(player, "coa3.convTracker", 303);
                }
                else if (progress == -302)
                {
                    setObjVar(player, "coa3.convTracker", -303);
                }
                if (hasObjVar(player, "coa3.extraFlora"))
                {
                    removeObjVar(player, "coa3.extraFlora");
                }
                messageTo(player, "createReturnMission", null, 1, false);
            }
            destroyObject(self);
        }
        return SCRIPT_CONTINUE;
    }
}
