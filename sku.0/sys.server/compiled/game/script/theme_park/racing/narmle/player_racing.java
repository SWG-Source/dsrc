package script.theme_park.racing.narmle;

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
import script.library.colors;
import script.library.sui;

public class player_racing extends script.base_script
{
    public player_racing()
    {
    }
    public static final String RACING_STF = "theme_park/racing/racing";
    public int OnAttach(obj_id self) throws InterruptedException
    {
        startRace(self);
        setObjVar(self, "racing.narmle.suppressMusic", 0);
        return SCRIPT_CONTINUE;
    }
    public int OnLogin(obj_id self) throws InterruptedException
    {
        messageTo(self, "handleCleanUp", null, 1, false);
        return SCRIPT_CONTINUE;
    }
    public int OnEnteredCombat(obj_id self) throws InterruptedException
    {
        setObjVar(self, "racing.narmle.suppressMusic", 1);
        playMusic(self, "sound/music_combat_bfield_def.snd");
        return SCRIPT_CONTINUE;
    }
    public int OnExitedCombat(obj_id self) throws InterruptedException
    {
        setObjVar(self, "racing.narmle.suppressMusic", 0);
        messageTo(self, "playRaceMusic", null, 3, false);
        return SCRIPT_CONTINUE;
    }
    public int OnArrivedAtLocation(obj_id self, String name) throws InterruptedException
    {
        float startTime = getFloatObjVar(self, "racing.narmle.startTime");
        float deltaTime = getGameTime();
        float lapTime = (deltaTime - startTime);
        float currentTime = getFloatObjVar(self, "racing.narmle.currentTime");
        string_id waypointName = new string_id(RACING_STF, "narmle_waypoint_name_" + name);
        string_id waypointDesc = new string_id(RACING_STF, "waypoint_desc_1");
        string_id checkPointMessage = new string_id(RACING_STF, "narmle_laptime_" + name);
        string_id finishMessage = new string_id(RACING_STF, "finish_message");
        string_id waypointHalfFly = new string_id(RACING_STF, "waypoint_half_fly");
        string_id waypointThreeToGo = new string_id(RACING_STF, "waypoint_3_to_go_fly");
        string_id waypointFinalLapFly = new string_id(RACING_STF, "final_lap_fly");
        if (name.equals("checkpoint1"))
        {
            setObjVar(self, "racing.narmle.currentTime", lapTime);
            removeMissionWaypoint(self);
            removeLocationTarget(name);
            location nextLocation = getLocationObjVar(self, "racing.narmle.checkpoint2");
            obj_id waypoint = createMissionWaypoint(self, nextLocation, 5, getString(waypointName), "checkpoint2");
            sendSystemMessage(self, checkPointMessage);
            sendSystemMessage(self, "Time: " + lapTime, null);
        }
        if (name.equals("checkpoint2"))
        {
            setObjVar(self, "racing.narmle.currentTime", lapTime);
            removeMissionWaypoint(self);
            removeLocationTarget(name);
            location nextLocation = getLocationObjVar(self, "racing.narmle.checkpoint3");
            obj_id waypoint = createMissionWaypoint(self, nextLocation, 5, getString(waypointName), "checkpoint3");
            sendSystemMessage(self, checkPointMessage);
            sendSystemMessage(self, "Time: " + lapTime, null);
        }
        if (name.equals("checkpoint3"))
        {
            setObjVar(self, "racing.narmle.currentTime", lapTime);
            removeMissionWaypoint(self);
            removeLocationTarget(name);
            location nextLocation = getLocationObjVar(self, "racing.narmle.checkpoint4");
            obj_id waypoint = createMissionWaypoint(self, nextLocation, 5, getString(waypointName), "checkpoint4");
            sendSystemMessage(self, checkPointMessage);
            sendSystemMessage(self, "Time: " + lapTime, null);
        }
        if (name.equals("checkpoint4"))
        {
            setObjVar(self, "racing.narmle.currentTime", lapTime);
            removeMissionWaypoint(self);
            removeLocationTarget(name);
            location nextLocation = getLocationObjVar(self, "racing.narmle.checkpoint5");
            obj_id waypoint = createMissionWaypoint(self, nextLocation, 5, getString(waypointName), "checkpoint5");
            sendSystemMessage(self, checkPointMessage);
            sendSystemMessage(self, "Time: " + lapTime, null);
        }
        if (name.equals("checkpoint5"))
        {
            setObjVar(self, "racing.narmle.currentTime", lapTime);
            removeMissionWaypoint(self);
            removeLocationTarget(name);
            location nextLocation = getLocationObjVar(self, "racing.narmle.checkpoint6");
            obj_id waypoint = createMissionWaypoint(self, nextLocation, 5, getString(waypointName), "checkpoint6");
            sendSystemMessage(self, checkPointMessage);
            sendSystemMessage(self, "Time: " + lapTime, null);
            messageTo(self, "playRaceMusic", null, 0, false);
        }
        if (name.equals("checkpoint6"))
        {
            setObjVar(self, "racing.narmle.currentTime", lapTime);
            removeMissionWaypoint(self);
            removeLocationTarget(name);
            location nextLocation = getLocationObjVar(self, "racing.narmle.checkpoint7");
            obj_id waypoint = createMissionWaypoint(self, nextLocation, 5, getString(waypointName), "checkpoint7");
            sendSystemMessage(self, checkPointMessage);
            sendSystemMessage(self, "Time: " + lapTime, null);
        }
        if (name.equals("checkpoint7"))
        {
            setObjVar(self, "racing.narmle.currentTime", lapTime);
            removeMissionWaypoint(self);
            removeLocationTarget(name);
            location nextLocation = getLocationObjVar(self, "racing.narmle.checkpoint8");
            obj_id waypoint = createMissionWaypoint(self, nextLocation, 5, getString(waypointName), "checkpoint8");
            sendSystemMessage(self, checkPointMessage);
            sendSystemMessage(self, "Time: " + lapTime, null);
        }
        if (name.equals("checkpoint8"))
        {
            setObjVar(self, "racing.narmle.currentTime", lapTime);
            removeMissionWaypoint(self);
            removeLocationTarget(name);
            location nextLocation = getLocationObjVar(self, "racing.narmle.checkpoint9");
            obj_id waypoint = createMissionWaypoint(self, nextLocation, 5, getString(waypointName), "checkpoint9");
            sendSystemMessage(self, checkPointMessage);
            sendSystemMessage(self, "Time: " + lapTime, null);
            showFlyText(self, waypointHalfFly, 1.5f, colors.RED);
        }
        if (name.equals("checkpoint9"))
        {
            setObjVar(self, "racing.narmle.currentTime", lapTime);
            removeMissionWaypoint(self);
            removeLocationTarget(name);
            location nextLocation = getLocationObjVar(self, "racing.narmle.checkpoint10");
            obj_id waypoint = createMissionWaypoint(self, nextLocation, 5, getString(waypointName), "checkpoint10");
            sendSystemMessage(self, checkPointMessage);
            sendSystemMessage(self, "Time: " + lapTime, null);
            messageTo(self, "playRaceMusic", null, 0, false);
        }
        if (name.equals("checkpoint10"))
        {
            setObjVar(self, "racing.narmle.currentTime", lapTime);
            removeMissionWaypoint(self);
            removeLocationTarget(name);
            location nextLocation = getLocationObjVar(self, "racing.narmle.checkpoint11");
            obj_id waypoint = createMissionWaypoint(self, nextLocation, 5, getString(waypointName), "checkpoint11");
            sendSystemMessage(self, checkPointMessage);
            sendSystemMessage(self, "Time: " + lapTime, null);
        }
        if (name.equals("checkpoint11"))
        {
            setObjVar(self, "racing.narmle.currentTime", lapTime);
            removeMissionWaypoint(self);
            removeLocationTarget(name);
            location nextLocation = getLocationObjVar(self, "racing.narmle.checkpoint12");
            obj_id waypoint = createMissionWaypoint(self, nextLocation, 5, getString(waypointName), "checkpoint12");
            sendSystemMessage(self, checkPointMessage);
            sendSystemMessage(self, "Time: " + lapTime, null);
        }
        if (name.equals("checkpoint12"))
        {
            setObjVar(self, "racing.narmle.currentTime", lapTime);
            removeMissionWaypoint(self);
            removeLocationTarget(name);
            location nextLocation = getLocationObjVar(self, "racing.narmle.checkpoint13");
            obj_id waypoint = createMissionWaypoint(self, nextLocation, 5, getString(waypointName), "checkpoint13");
            sendSystemMessage(self, checkPointMessage);
            sendSystemMessage(self, "Time: " + lapTime, null);
            messageTo(self, "playRaceMusic", null, 0, false);
        }
        if (name.equals("checkpoint13"))
        {
            setObjVar(self, "racing.narmle.currentTime", lapTime);
            removeMissionWaypoint(self);
            removeLocationTarget(name);
            location nextLocation = getLocationObjVar(self, "racing.narmle.checkpoint14");
            obj_id waypoint = createMissionWaypoint(self, nextLocation, 5, getString(waypointName), "checkpoint14");
            sendSystemMessage(self, checkPointMessage);
            sendSystemMessage(self, "Time: " + lapTime, null);
            showFlyText(self, waypointThreeToGo, 1.5f, colors.RED);
        }
        if (name.equals("checkpoint14"))
        {
            setObjVar(self, "racing.narmle.currentTime", lapTime);
            removeMissionWaypoint(self);
            removeLocationTarget(name);
            location nextLocation = getLocationObjVar(self, "racing.narmle.checkpoint15");
            obj_id waypoint = createMissionWaypoint(self, nextLocation, 5, getString(waypointName), "checkpoint15");
            sendSystemMessage(self, checkPointMessage);
            sendSystemMessage(self, "Time: " + lapTime, null);
            messageTo(self, "playRaceMusic", null, 0, false);
        }
        if (name.equals("checkpoint15"))
        {
            setObjVar(self, "racing.narmle.currentTime", lapTime);
            removeMissionWaypoint(self);
            removeLocationTarget(name);
            location nextLocation = getLocationObjVar(self, "racing.narmle.returnLoc");
            obj_id waypoint = createMissionWaypoint(self, nextLocation, 5, getString(waypointName), "returndest");
            sendSystemMessage(self, checkPointMessage);
            sendSystemMessage(self, "Time: " + lapTime, null);
            showFlyText(self, waypointFinalLapFly, 1.5f, colors.RED);
        }
        if (name.equals("returndest"))
        {
            setObjVar(self, "racing.narmle.currentTime", lapTime);
            setObjVar(self, "racing.narmle.success", 1);
            removeMissionWaypoint(self);
            removeLocationTarget(name);
            sendSystemMessage(self, finishMessage);
            sendSystemMessage(self, "Total Race Time: " + lapTime, null);
            messageTo(self, "handleBestTime", null, 1, false);
            messageTo(self, "handleCleanUp", null, 1, false);
            removeObjVar(self, "racing.narmle.returnLoc");
            removeObjVar(self, "racing.narmle.isRacing");
            playMusic(self, "sound/music_int_complete_neutral.snd");
        }
        return SCRIPT_CONTINUE;
    }
    public int playRaceMusic(obj_id self, dictionary params) throws InterruptedException
    {
        int suppressMusic = getIntObjVar(self, "racing.narmle.suppressMusic");
        if (suppressMusic == 0)
        {
            playMusic(self, "sound/music_combat_bfield_lp.snd");
        }
        return SCRIPT_CONTINUE;
    }
    public int handleCleanUp(obj_id self, dictionary params) throws InterruptedException
    {
        removeMissionWaypoint(self);
        removeObjVar(self, "racing.narmle");
        detachScript(self, "theme_park.racing.narmle.player_racing");
        return SCRIPT_CONTINUE;
    }
    public int handleBestTime(obj_id self, dictionary params) throws InterruptedException
    {
        string_id newRecord = new string_id(RACING_STF, "new_record");
        float currentTime = getFloatObjVar(self, "racing.narmle.currentTime");
        float bestTime = getFloatObjVar(self, "racing.bestTime.narmle");
        setObjVar(self, "racing.lastTime.narmle", currentTime);
        if (currentTime < bestTime)
        {
            setObjVar(self, "racing.bestTime.narmle", currentTime);
            showFlyText(self, newRecord, 1.5f, colors.GREENYELLOW);
        }
        return SCRIPT_CONTINUE;
    }
    public int handleRestartMission(obj_id self, dictionary params) throws InterruptedException
    {
        setObjVar(self, "racing.narmle.success", 1);
        removeMissionWaypoint(self);
        startRace(self);
        return SCRIPT_CONTINUE;
    }
    public int createReturnMission(obj_id self, dictionary params) throws InterruptedException
    {
        if (hasObjVar(self, "racing.narmle.waypoint"))
        {
            removeMissionWaypoint(self);
        }
        int missionNum = getIntObjVar(self, "racing.narmle.missionNum");
        location returnLoc = getLocationObjVar(self, "racing.narmle.returnLoc");
        string_id waypointName = new string_id(RACING_STF, "waypoint_name_returndest");
        string_id waypointDesc = new string_id(RACING_STF, "waypoint_return_desc_1");
        obj_id waypoint = createMissionWaypoint(self, returnLoc, 20, getString(waypointName), "returndest");
        if (hasObjVar(self, "racing.narmle.finalMission"))
        {
            removeObjVar(self, "race");
        }
        return SCRIPT_CONTINUE;
    }
    public void startRace(obj_id player) throws InterruptedException
    {
        int missionNum = getIntObjVar(player, "racing.narmle.missionNum");
        location checkPointOne = getLocationObjVar(player, "racing.narmle.checkpoint1");
        string_id waypointName = new string_id(RACING_STF, "narmle_waypoint_name_checkpoint0");
        string_id waypointDesc = new string_id(RACING_STF, "waypoint_desc_1");
        string_id goFly = new string_id(RACING_STF, "go_fly");
        obj_id waypoint = createMissionWaypoint(player, checkPointOne, 20, getString(waypointName), "checkpoint1");
        showFlyText(player, goFly, 1.5f, colors.BLUE);
        playMusic(player, "sound/music_combat_bfield_lp.snd");
        if (isIdValid(waypoint))
        {
            setObjVar(player, "racing.narmle.waypointDesc", waypointDesc);
            addLocationTarget("checkPointOne", checkPointOne, 100);
        }
        else 
        {
            string_id error = new string_id(RACING_STF, "waypoint_failure");
            sendSystemMessage(player, error);
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
            setObjVar(player, "racing.narmle.waypoint", waypoint);
            setObjVar(player, "racing.narmle.volume", volumeName);
        }
        return waypoint;
    }
    public void removeMissionWaypoint(obj_id player) throws InterruptedException
    {
        obj_id waypoint = getObjIdObjVar(player, "racing.narmle.waypoint");
        if (hasObjVar(player, "racing.narmle.volume"))
        {
            String name = getStringObjVar(player, "racing.narmle.volume");
            removeLocationTarget(name);
            if (name.equals("missionDest"))
            {
                removeLocationTarget("missionSpawner");
            }
            removeObjVar(player, "racing.narmle.volume");
        }
        setWaypointVisible(waypoint, false);
        setWaypointActive(waypoint, false);
        destroyWaypointInDatapad(waypoint, player);
        removeObjVar(player, "racing.narmle.waypoint");
    }
    public int OnWaypointGetAttributes(obj_id self, obj_id waypoint, obj_id player, String[] names, String[] attribs) throws InterruptedException
    {
        obj_id wp = getObjIdObjVar(player, "racing.narmle.waypoint");
        if (waypoint != wp)
        {
            return SCRIPT_CONTINUE;
        }
        int idx = utils.getValidAttributeIndex(names);
        if (idx == -1)
        {
            return SCRIPT_CONTINUE;
        }
        string_id waypointDesc = getStringIdObjVar(player, "racing.narmle.waypointDesc");
        names[idx] = "mission_details";
        attribs[idx] = "@" + waypointDesc.toString();
        return SCRIPT_CONTINUE;
    }
}
