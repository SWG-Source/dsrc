package script.systems.missions.dynamic;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.utils;

public class mission_destruction extends script.systems.missions.base.mission_dynamic_base
{
    public mission_destruction()
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
    public int onArrivedAtLocation(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id objPlayer = getMissionHolder(self);
        dictionary dctParams = new dictionary();
        dctParams.put("objMission", self);
        messageTo(objPlayer, "destruction_Arrival", dctParams, 0, true);
        return SCRIPT_CONTINUE;
    }
    public int abortMission(obj_id self, dictionary params) throws InterruptedException
    {
        LOG("mission", "ABORT MISSION RECEIVED!");
        sendDestructionIncomplete(self);
        return SCRIPT_CONTINUE;
    }
    public int OnStartMission(obj_id self, dictionary params) throws InterruptedException
    {
        LOG("mission", "Received start mission message");
        String strContext;
        obj_id objMissionData = getMissionData(self);
        LOG("mission", "objMissionData is " + objMissionData);
        obj_id objPlayer = getMissionHolder(self);
        LOG("mission", "Right above AttachScript");
        LOG("mission", "Right above setupDestructionMissionObject");
        setupDestructionMissionObject(self);
        LOG("mission", "Right above setupSpawn");
        setupSpawn(self, getMissionStartLocation(objMissionData));
        location locSpawnLocation = getMissionStartLocation(objMissionData);
        if (locSpawnLocation == null)
        {
            LOG("mission", "locSpawnLocaiton is NULL");
            return SCRIPT_CONTINUE;
        }
        updateMissionWaypoint(self, locSpawnLocation);
        dictionary dctParams = new dictionary();
        return SCRIPT_CONTINUE;
    }
    public int destructionIncomplete(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id objPlayer = getMissionHolder(self);
        string_id strMessage = new string_id(GENERIC_MISSION_MESSAGE_STRING_FILE, "incomplete");
        sendSystemMessage(objPlayer, strMessage);
        endMission(self);
        return SCRIPT_CONTINUE;
    }
    public int destructionFailure(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id objPlayer = getMissionHolder(self);
        string_id strMessage = new string_id(GENERIC_MISSION_MESSAGE_STRING_FILE, "failed");
        sendSystemMessage(objPlayer, strMessage);
        String strTitleString = MISSION_FAILURE_PERSISTENT_MESSAGE;
        obj_id objMissionData = getMissionData(self);
        int intStringId = getIntObjVar(objMissionData, "intStringId");
        String strMessageString = "m" + intStringId + "d";
        endMission(self);
        return SCRIPT_CONTINUE;
    }
    public int destructionSuccess(obj_id self, dictionary params) throws InterruptedException
    {
        if (!hasObjVar(self, "intFinished"))
        {
            obj_id objMissionData = getMissionData(self);
            if (hasObjVar(objMissionData, "locCreatorLocation"))
            {
                setObjVar(self, "intNPC", 1);
                setObjVar(self, "intMissionComplete", 1);
                location locCreatorLocation = getLocationObjVar(objMissionData, "locCreatorLocation");
                removeObjVar(objMissionData, "locCreatorLocation");
                updateMissionWaypoint(self, locCreatorLocation);
                string_id strSpam = new string_id("mission/mission_generic", "destroy_return");
                sendSystemMessage(getMissionHolder(self), strSpam);
                return SCRIPT_CONTINUE;
            }
            setObjVar(self, "intFinished", 1);
            if (!hasObjVar(self, "intNPC"))
            {
            }
            deliverReward(self);
            obj_id objPlayer = getMissionHolder(self);
            playMusic(objPlayer, "sound/music_mission_complete.snd");
        }
        endMission(self);
        return SCRIPT_CONTINUE;
    }
    public int OnEndMission(obj_id self, dictionary params) throws InterruptedException
    {
        cleanupLocationTargets(self);
        cleanupObjects(self);
        return SCRIPT_CONTINUE;
    }
}
