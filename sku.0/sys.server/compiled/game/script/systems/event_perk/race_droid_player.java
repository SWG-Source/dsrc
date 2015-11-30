package script.systems.event_perk;

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
import script.library.prose;

public class race_droid_player extends script.base_script
{
    public race_droid_player()
    {
    }
    public static final String RACING_STF = "theme_park/racing/racing";
    public int OnAttach(obj_id self) throws InterruptedException
    {
        setObjVar(self, "event_perk.racing.suppressMusic", 0);
        startRace(self);
        return SCRIPT_CONTINUE;
    }
    public int OnLogin(obj_id self) throws InterruptedException
    {
        messageTo(self, "handleCleanUp", null, 1, false);
        return SCRIPT_CONTINUE;
    }
    public int OnEnteredCombat(obj_id self) throws InterruptedException
    {
        setObjVar(self, "event_perk.racing.suppressMusic", 1);
        playMusic(self, "sound/music_silence.snd");
        return SCRIPT_CONTINUE;
    }
    public int OnExitedCombat(obj_id self) throws InterruptedException
    {
        setObjVar(self, "event_perk.racing.suppressMusic", 0);
        return SCRIPT_CONTINUE;
    }
    public int OnArrivedAtLocation(obj_id self, String name) throws InterruptedException
    {
        float startTime = getFloatObjVar(self, "event_perk.racing.startTime");
        float deltaTime = getGameTime();
        float lapTime = (deltaTime - startTime);
        float currentTime = getFloatObjVar(self, "event_perk.racing.currentTime");
        string_id waypointName = new string_id("event_perk", "racing_waypoint_name");
        string_id waypointDesc = new string_id(RACING_STF, "waypoint_desc_1");
        string_id checkPointMessage = new string_id("event_perk", "racing_checkpoint_message");
        string_id finishMessage = new string_id("event_perk", "racing_finish_message");
        string_id waypointFinalLapFly = new string_id(RACING_STF, "final_lap_fly");
        int maxWaypoints = getIntObjVar(self, "event_perk.racing.maxWaypoints");
        int currentCheckpoint = getIntObjVar(self, "event_perk.racing.currentCheckpoint");
        if (name.equals("returndest"))
        {
            dictionary params = new dictionary();
            params.put("player", self);
            obj_id droid = getObjIdObjVar(self, "event_perk.racing.droid");
            setObjVar(self, "event_perk.racing.currentTime", lapTime);
            setObjVar(self, "event_perk.racing.success", 1);
            removeMissionWaypoint(self);
            removeLocationTarget(name);
            sendSystemMessage(self, finishMessage);
            string_id totalRaceTime = new string_id("event_perk", "racing_total_time");
            prose_package pp = prose.getPackage(totalRaceTime, 0, lapTime);
            sendSystemMessageProse(self, pp);
            messageTo(droid, "messageRegisterBest", params, 1, false);
            messageTo(self, "handleBestTime", null, 1, false);
            messageTo(self, "handleCleanUp", null, 7, false);
            removeObjVar(self, "event_perk.racing.returnLoc");
            removeObjVar(self, "event_perk.racing.isRacing");
            playMusic(self, "sound/music_int_complete_neutral.snd");
            return SCRIPT_CONTINUE;
        }
        if (name.equals("checkpoint" + currentCheckpoint) && (maxWaypoints - 1) == currentCheckpoint)
        {
            setObjVar(self, "racing.agrilat.currentTime", lapTime);
            removeMissionWaypoint(self);
            removeLocationTarget(name);
            location nextLocation = getLocationObjVar(self, "event_perk.racing.returnLoc");
            obj_id waypoint = createMissionWaypoint(self, nextLocation, 14, getString(waypointName), "returndest");
            sendSystemMessage(self, checkPointMessage);
            string_id lapTimeSID = new string_id("event_perk", "racing_lap_time");
            prose_package pp = prose.getPackage(lapTimeSID, 0, lapTime);
            sendSystemMessageProse(self, pp);
            showFlyText(self, waypointFinalLapFly, 1.5f, colors.RED);
            messageTo(self, "playRaceMusic", null, 0, false);
            return SCRIPT_CONTINUE;
        }
        if (name.equals("checkpoint" + currentCheckpoint))
        {
            setObjVar(self, "event_perk.racing.currentTime", lapTime);
            removeMissionWaypoint(self);
            removeLocationTarget(name);
            location nextLocation = getLocationObjVar(self, "event_perk.racing.checkpoint" + (currentCheckpoint + 1));
            obj_id waypoint = createMissionWaypoint(self, nextLocation, 14, getString(waypointName), "checkpoint" + (currentCheckpoint + 1));
            currentCheckpoint++;
            setObjVar(self, "event_perk.racing.currentCheckpoint", currentCheckpoint);
            sendSystemMessage(self, checkPointMessage);
            string_id lapTimeSID = new string_id("event_perk", "racing_lap_time");
            prose_package pp = prose.getPackage(lapTimeSID, 0, lapTime);
            sendSystemMessageProse(self, pp);
            messageTo(self, "playRaceMusic", null, 0, false);
        }
        return SCRIPT_CONTINUE;
    }
    public int playRaceMusic(obj_id self, dictionary params) throws InterruptedException
    {
        int suppressMusic = getIntObjVar(self, "event_perk.racing.suppressMusic");
        if (suppressMusic == 0)
        {
            playMusic(self, "sound/music_combat_bfield_lp.snd");
        }
        return SCRIPT_CONTINUE;
    }
    public int handleCleanUp(obj_id self, dictionary params) throws InterruptedException
    {
        removeMissionWaypoint(self);
        removeObjVar(self, "event_perk.racing");
        detachScript(self, "systems.event_perk.race_droid_player");
        return SCRIPT_CONTINUE;
    }
    public int handleRestartMission(obj_id self, dictionary params) throws InterruptedException
    {
        setObjVar(self, "event_perk.racing.success", 1);
        removeMissionWaypoint(self);
        startRace(self);
        return SCRIPT_CONTINUE;
    }
    public int createReturnMission(obj_id self, dictionary params) throws InterruptedException
    {
        if (hasObjVar(self, "event_perk.racing.waypoint"))
        {
            removeMissionWaypoint(self);
        }
        int missionNum = getIntObjVar(self, "event_perk.racing.missionNum");
        location returnLoc = getLocationObjVar(self, "event_perk.racing.returnLoc");
        string_id waypointName = new string_id(RACING_STF, "waypoint_name_returndest");
        string_id waypointDesc = new string_id(RACING_STF, "waypoint_return_desc_1");
        obj_id waypoint = createMissionWaypoint(self, returnLoc, 14, getString(waypointName), "returndest");
        if (hasObjVar(self, "event_perk.racing.finalMission"))
        {
            removeObjVar(self, "event_perk.racing");
        }
        return SCRIPT_CONTINUE;
    }
    public void startRace(obj_id player) throws InterruptedException
    {
        int missionNum = getIntObjVar(player, "event_perk.racing.missionNum");
        location checkPointOne = getLocationObjVar(player, "event_perk.racing.checkpoint0");
        string_id waypointName = new string_id("event_perk", "racing_waypoint_name");
        string_id waypointDesc = new string_id(RACING_STF, "waypoint_desc_1");
        string_id goFly = new string_id(RACING_STF, "go_fly");
        obj_id waypoint = createMissionWaypoint(player, checkPointOne, 14, getString(waypointName), "checkpoint0");
        showFlyText(player, goFly, 1.5f, colors.BLUE);
        playMusic(player, "sound/music_combat_bfield_lp.snd");
        if (isIdValid(waypoint))
        {
            setObjVar(player, "event_perk.racing.waypointDesc", waypointDesc);
            addLocationTarget("checkPointOne", checkPointOne, 14);
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
            setObjVar(player, "event_perk.racing.waypoint", waypoint);
            setObjVar(player, "event_perk.racing.volume", volumeName);
        }
        return waypoint;
    }
    public void removeMissionWaypoint(obj_id player) throws InterruptedException
    {
        obj_id waypoint = getObjIdObjVar(player, "event_perk.racing.waypoint");
        setWaypointVisible(waypoint, false);
        setWaypointActive(waypoint, false);
        destroyWaypointInDatapad(waypoint, player);
        removeObjVar(player, "event_perk.racing.waypoint");
        if (hasObjVar(player, "event_perk.racing.volume"))
        {
            String name = getStringObjVar(player, "event_perk.racing.volume");
            removeLocationTarget(name);
            if (name.equals("missionDest"))
            {
                removeLocationTarget("missionSpawner");
            }
            removeObjVar(player, "event_perk.racing.volume");
        }
        return;
    }
    public int OnWaypointGetAttributes(obj_id self, obj_id waypoint, obj_id player, String[] names, String[] attribs) throws InterruptedException
    {
        obj_id wp = getObjIdObjVar(player, "event_perk.racing.waypoint");
        if (waypoint != wp)
        {
            return SCRIPT_CONTINUE;
        }
        int idx = utils.getValidAttributeIndex(names);
        if (idx == -1)
        {
            return SCRIPT_CONTINUE;
        }
        string_id waypointDesc = getStringIdObjVar(player, "event_perk.racing.waypointDesc");
        names[idx] = "mission_details";
        attribs[idx] = "@" + waypointDesc.toString();
        return SCRIPT_CONTINUE;
    }
}
