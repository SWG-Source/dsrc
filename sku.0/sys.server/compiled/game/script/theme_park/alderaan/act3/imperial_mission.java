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

public class imperial_mission extends script.base_script
{
    public imperial_mission()
    {
    }
    public static final String IMPERIAL_STF = "theme_park/alderaan/act3/imperial_missions";
    public static final String IMPERIAL_SHARED_STF = "theme_park/alderaan/act3/shared_imperial_missions";
    public int OnAttach(obj_id self) throws InterruptedException
    {
        if (hasScript(self, "theme_park.alderaan.act3.rebel_mission"))
        {
            detachScript(self, "theme_park.alderaan.act3.rebel_mission");
        }
        startImperialMission(self);
        return SCRIPT_CONTINUE;
    }
    public int OnDetach(obj_id self) throws InterruptedException
    {
        if (hasObjVar(self, "coa3.imperial"))
        {
            int missionNum = getIntObjVar(self, "coa3.imperial.missionNum");
            abortImperialMission(self, missionNum, false);
        }
        return SCRIPT_CONTINUE;
    }
    public int OnLogin(obj_id self) throws InterruptedException
    {
        if (hasObjVar(self, "coa3.imperial.redirect"))
        {
            String planet = getStringObjVar(self, "coa3.imperial.redirect.planet");
            String scene = getCurrentSceneName();
            if (planet.equals(scene))
            {
                setObjVar(self, "coa3.imperial.success", 1);
                removeMissionWaypoint(self);
                int missionNum = getIntObjVar(self, "coa3.imperial.missionNum");
                int minDistance = getIntObjVar(self, "coa3.imperial.redirect.minDistance");
                int maxDistance = getIntObjVar(self, "coa3.imperial.redirect.maxDistance");
                if (maxDistance > 0)
                {
                    location missionLoc = getDeliveryLoc(self, minDistance, maxDistance);
                    string_id waypointName = new string_id(IMPERIAL_SHARED_STF, "waypoint_name_" + missionNum);
                    string_id waypointDesc = new string_id(IMPERIAL_SHARED_STF, "waypoint_desc_" + missionNum);
                    createMissionWaypoint(self, missionLoc, 20, getString(waypointName), "missionDest");
                    setObjVar(self, "coa3.imperial.missionLoc", missionLoc);
                    setObjVar(self, "coa3.imperial.waypointDesc", waypointDesc);
                    addLocationTarget("missionSpawner", missionLoc, 100);
                }
                else 
                {
                    obj_id wp = getObjIdObjVar(self, "coa3.imperial.waypoint");
                    setWaypointActive(wp, true);
                }
                removeObjVar(self, "coa3.imperial.redirect");
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
            LOG("CoA3_Imperial", "Imperial Delivery Location:  Attepmt #" + (x + 1));
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
        int missionNum = getIntObjVar(self, "coa3.imperial.missionNum");
        if (name.equals("missionSpawner"))
        {
            location missionLoc = getLocationObjVar(self, "coa3.imperial.missionLoc");
            String missionNpc = getStringObjVar(self, "coa3.imperial.missionNpc");
            if (missionNpc != null && !missionNpc.equals(""))
            {
                obj_id npc = create.object(missionNpc, missionLoc);
                setObjVar(self, "coa3.imperial.missionNpcId", npc);
                setObjVar(npc, "coa3.imperial.playerId", self);
            }
            else 
            {
                removeObjVar(self, "coa3.imperial.missionNpcId");
            }
        }
        if (name.equals("missionDest"))
        {
            setObjVar(self, "coa3.imperial.success", 1);
            if (hasObjVar(self, "coa3.imperial.missionNpcId"))
            {
                obj_id npc = getObjIdObjVar(self, "coa3.imperial.missionNpcId");
                if (!isIdValid(npc) || !exists(npc))
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
            setObjVar(self, "coa3.imperial.success", 1);
            removeMissionWaypoint(self);
            removeLocationTarget(name);
        }
        return SCRIPT_CONTINUE;
    }
    public int handleRestartMission(obj_id self, dictionary params) throws InterruptedException
    {
        setObjVar(self, "coa3.imperial.success", 1);
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
        if (hasObjVar(self, "coa3.imperial.waypoint"))
        {
            removeMissionWaypoint(self);
        }
        int missionNum = getIntObjVar(self, "coa3.imperial.missionNum");
        location returnLoc = getLocationObjVar(self, "coa3.imperial.returnLoc");
        string_id waypointName = new string_id(IMPERIAL_SHARED_STF, "waypoint_return_name_" + missionNum);
        string_id waypointDesc = new string_id(IMPERIAL_SHARED_STF, "waypoint_return_desc_" + missionNum);
        obj_id waypoint = createMissionWaypoint(self, returnLoc, 20, getString(waypointName), "returnDest");
        if (isIdValid(waypoint))
        {
            setObjVar(self, "coa3.imperial.waypointDesc", waypointDesc);
            String planet = returnLoc.area;
            if (!planet.equals(getCurrentSceneName()))
            {
                string_id planetMsg = new string_id("mission/mission_generic", "target_located_" + planet);
                sendSystemMessage(self, planetMsg);
                setObjVar(self, "coa3.imperial.redirect.planet", planet);
                removeObjVar(self, "coa3.imperial.redirect.minDistance");
                removeObjVar(self, "coa3.imperial.redirect.maxDistance");
            }
        }
        if (hasObjVar(self, "coa3.imperial.finalMission"))
        {
            removeObjVar(self, "coa3.imperial");
        }
        return SCRIPT_CONTINUE;
    }
    public void startImperialMission(obj_id player) throws InterruptedException
    {
        int missionNum = getIntObjVar(player, "coa3.imperial.missionNum");
        location missionLoc = getLocationObjVar(player, "coa3.imperial.missionLoc");
        string_id waypointName = new string_id(IMPERIAL_SHARED_STF, "waypoint_name_" + missionNum);
        string_id waypointDesc = new string_id(IMPERIAL_SHARED_STF, "waypoint_desc_" + missionNum);
        obj_id waypoint = createMissionWaypoint(player, missionLoc, 20, getString(waypointName), "missionDest");
        if (isIdValid(waypoint))
        {
            setObjVar(player, "coa3.imperial.waypointDesc", waypointDesc);
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
            removeObjVar(player, "coa3.imperial");
            detachScript(player, "theme_park.alderaan.act3.imperial_mission");
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
            setObjVar(player, "coa3.convTracker", 301);
            break;
            case 4:
            setObjVar(player, "coa3.convTracker", 303);
            break;
            case 5:
            setObjVar(player, "coa3.convTracker", 401);
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
            setObjVar(player, "coa3.imperial.waypoint", waypoint);
            setObjVar(player, "coa3.imperial.volume", volumeName);
        }
        return waypoint;
    }
    public void removeMissionWaypoint(obj_id player) throws InterruptedException
    {
        obj_id waypoint = getObjIdObjVar(player, "coa3.imperial.waypoint");
        if (hasObjVar(player, "coa3.imperial.volume"))
        {
            String name = getStringObjVar(player, "coa3.imperial.volume");
            removeLocationTarget(name);
            if (name.equals("missionDest"))
            {
                removeLocationTarget("missionSpawner");
            }
            removeObjVar(player, "coa3.imperial.volume");
        }
        setWaypointVisible(waypoint, false);
        setWaypointActive(waypoint, false);
        destroyWaypointInDatapad(waypoint, player);
        removeObjVar(player, "coa3.imperial.waypoint");
    }
    public int OnWaypointGetAttributes(obj_id self, obj_id waypoint, obj_id player, String[] names, String[] attribs) throws InterruptedException
    {
        obj_id wp = getObjIdObjVar(player, "coa3.imperial.waypoint");
        if (waypoint != wp)
        {
            return SCRIPT_CONTINUE;
        }
        int idx = utils.getValidAttributeIndex(names);
        if (idx == -1)
        {
            return SCRIPT_CONTINUE;
        }
        string_id waypointDesc = getStringIdObjVar(player, "coa3.imperial.waypointDesc");
        names[idx] = "mission_details";
        attribs[idx] = "@" + waypointDesc.toString();
        return SCRIPT_CONTINUE;
    }
    public int OnWaypointDestroyed(obj_id self, obj_id waypoint) throws InterruptedException
    {
        obj_id wp = getObjIdObjVar(self, "coa3.imperial.waypoint");
        if (waypoint != wp)
        {
            return SCRIPT_CONTINUE;
        }
        if (hasObjVar(self, "coa3.imperial.success"))
        {
            removeObjVar(self, "coa3.imperial.success");
        }
        else 
        {
            int missionNum = getIntObjVar(self, "coa3.imperial.missionNum");
            revertMissionProgress(self, missionNum);
            debugSpeakMsg(self, "Reverting Mission Progress - " + missionNum);
        }
        removeObjVar(self, "coa3.imperial.waypoint");
        removeObjVar(self, "coa3.imperial.volume");
        return SCRIPT_CONTINUE;
    }
    public int handleFloraCheck(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id termUser = params.getObjId("termUser");
        obj_id warehouse = params.getObjId("warehouse");
        obj_id player = params.getObjId("player");
        if (exists(warehouse))
        {
            sendSystemMessage(termUser, new string_id(IMPERIAL_SHARED_STF, "self_destruct_aborted"));
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
