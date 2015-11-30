package script.systems.missions.dynamic;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.utils;

public class mission_recon extends script.systems.missions.base.mission_dynamic_base
{
    public mission_recon()
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
        debugServerConsoleMsg(self, "gotArrivedMessage");
        if (!hasObjVar(self, "intTargetCreated"))
        {
            dictionary dctParams = new dictionary();
            dctParams.put("objMission", self);
            messageTo(objPlayer, "recon_Arrival", dctParams, 0, true);
        }
        else 
        {
            if (hasObjVar(self, "intTerminal"))
            {
                sendReconSuccess(self);
                return SCRIPT_CONTINUE;
            }
            setObjVar(self, "intMissionComplete", 1);
            string_id strSpam = new string_id("mission/mission_generic", "recon_return");
            sendSystemMessage(objPlayer, strSpam);
            obj_id objMissionData = getMissionData(self);
            location locCreatorLocation = getLocationObjVar(objMissionData, "locCreatorLocation");
            updateMissionWaypoint(self, locCreatorLocation);
        }
        return SCRIPT_CONTINUE;
    }
    public int abortMission(obj_id self, dictionary params) throws InterruptedException
    {
        sendReconIncomplete(self);
        return SCRIPT_CONTINUE;
    }
    public int OnStartMission(obj_id self, dictionary params) throws InterruptedException
    {
        String strContext;
        obj_id objMissionData = getMissionData(self);
        debugServerConsoleMsg(self, "objMissionData is " + objMissionData);
        obj_id objPlayer = getMissionHolder(self);
        int intStringId = getIntObjVar(objMissionData, "intStringId");
        setupSpawn(self, getMissionStartLocation(objMissionData));
        location locSpawnLocation = getMissionStartLocation(objMissionData);
        if (locSpawnLocation == null)
        {
            debugServerConsoleMsg(self, "locSpawnLocaiton is NULL");
            return SCRIPT_CONTINUE;
        }
        updateMissionWaypoint(self, locSpawnLocation);
        activateMissionWaypoint(self);
        dictionary dctParams = new dictionary();
        return SCRIPT_CONTINUE;
    }
    public int reconIncomplete(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id objPlayer = getMissionHolder(self);
        String strTitleString = MISSION_INCOMPLETE_PERSISTENT_MESSAGE;
        obj_id objMissionData = getMissionData(self);
        int intStringId = getIntObjVar(objMissionData, "intStringId");
        String strMessageString = "m" + intStringId + "d";
        string_id strMessage = new string_id(GENERIC_MISSION_MESSAGE_STRING_FILE, "incomplete");
        sendSystemMessage(objPlayer, strMessage);
        endMission(self);
        return SCRIPT_CONTINUE;
    }
    public int reconFailure(obj_id self, dictionary params) throws InterruptedException
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
    public int reconSuccess(obj_id self, dictionary params) throws InterruptedException
    {
        if (!hasObjVar(self, "intFinished"))
        {
            setObjVar(self, "intFinished", 1);
            obj_id objMissionData = getMissionData(self);
            obj_id objPlayer = getMissionHolder(self);
            deliverReward(self);
            playMusic(objPlayer, "sound/music_mission_complete.snd");
            endMission(self);
        }
        return SCRIPT_CONTINUE;
    }
    public int OnEndMission(obj_id self, dictionary params) throws InterruptedException
    {
        debugServerConsoleMsg(self, "Ending mission");
        cleanupObjects(self);
        cleanupLocationTargets(self);
        debugServerConsoleMsg(self, "cleaned up locationt argets, getting objwaypoint objvars");
        return SCRIPT_CONTINUE;
    }
}
