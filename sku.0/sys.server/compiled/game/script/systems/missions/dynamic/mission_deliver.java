package script.systems.missions.dynamic;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.utils;
import script.library.missions;

public class mission_deliver extends script.systems.missions.base.mission_dynamic_base
{
    public mission_deliver()
    {
    }
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        if (!hasScript(self, "systems.missions.base.mission_cleanup_tracker"))
        {
            attachScript(self, "systems.missions.base.mission_cleanup_tracker");
        }
        return SCRIPT_CONTINUE;
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        return SCRIPT_CONTINUE;
    }
    public int abortMission(obj_id self, dictionary params) throws InterruptedException
    {
        sendDeliverIncomplete(self);
        return SCRIPT_CONTINUE;
    }
    public int OnStartMission(obj_id self, dictionary params) throws InterruptedException
    {
        debugServerConsoleMsg(self, "Setting up mission data in mission object for DELIVER mission");
        setupFetchMissionObject(self);
        obj_id objMissionData = getMissionData(self);
        location locStartLocation = getMissionStartLocation(objMissionData);
        setObjVar(self, "intState", missions.STATE_DYNAMIC_PICKUP);
        updateMissionWaypoint(self, locStartLocation);
        int intStringId = getIntObjVar(objMissionData, "intStringId");
        String strMessageString = "m" + intStringId + "d";
        String strTitleString = "m" + intStringId + "t";
        LOG("mission", "strMessagebeing sent is " + strMessageString);
        LOG("mission", "strTitle sent is " + strTitleString);
        activateMissionWaypoint(self);
        return SCRIPT_CONTINUE;
    }
    public int deliverSuccess(obj_id self, dictionary params) throws InterruptedException
    {
        if (!hasObjVar(self, "intCompleted"))
        {
            setObjVar(self, "intCompleted", 1);
            obj_id objPlayer = getMissionHolder(self);
            playMusic(self, "sound/music_mission_complete.snd");
            deliverReward(self);
            endMission(self);
        }
        return SCRIPT_CONTINUE;
    }
    public int deliverFail(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id objPlayer = getMissionHolder(self);
        string_id strMessage = new string_id(GENERIC_MISSION_MESSAGE_STRING_FILE, "failed");
        sendSystemMessage(objPlayer, strMessage);
        endMission(self);
        return SCRIPT_CONTINUE;
    }
    public int deliverIncomplete(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id objPlayer = getMissionHolder(self);
        string_id strMessage = new string_id(GENERIC_MISSION_MESSAGE_STRING_FILE, "incomplete");
        sendSystemMessage(objPlayer, strMessage);
        playMusic(objPlayer, "sound/music_mission_complete.snd");
        endMission(self);
        return SCRIPT_CONTINUE;
    }
    public int OnEndMission(obj_id self, dictionary params) throws InterruptedException
    {
        cleanupObjects(self);
        cleanupLocationTargets(self);
        obj_id objMissionData = getMissionData(self);
        cleanupObjects(self);
        obj_id objWaypoint = getObjIdObjVar(self, "objWaypoint");
        setWaypointActive(objWaypoint, false);
        setWaypointVisible(objWaypoint, false);
        destroyObject(objWaypoint);
        return SCRIPT_CONTINUE;
    }
    public int onArrivedAtLocation(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id objPlayer = getMissionHolder(self);
        dictionary dctParams = new dictionary();
        dctParams.put("objMission", self);
        return SCRIPT_CONTINUE;
    }
    public int pickup_event(obj_id self, dictionary params) throws InterruptedException
    {
        return SCRIPT_CONTINUE;
    }
}
