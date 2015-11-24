package script.systems.missions.dynamic;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.chat;

public class mission_escort extends script.systems.missions.base.mission_dynamic_base
{
    public mission_escort()
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
    public int OnStartMission(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id objMissionData = getMissionData(self);
        location locSpawnLocation = getMissionStartLocation(objMissionData);
        if (!hasObjVar(objMissionData, "intTargetPickedUp"))
        {
            setupSpawn(self, locSpawnLocation);
            updateMissionWaypoint(self, locSpawnLocation);
        }
        else 
        {
            obj_id objMissionCreator = getObjIdObjVar(self, "objOwner");
            dictionary dctParams = new dictionary();
            dctParams.put("objMission", self);
            messageTo(objMissionCreator, "escort_Accepted", dctParams, 0, true);
        }
        return SCRIPT_CONTINUE;
    }
    public int abortMission(obj_id self, dictionary params) throws InterruptedException
    {
        sendEscortIncomplete(self);
        return SCRIPT_CONTINUE;
    }
    public int escort_Pickup(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id objMissionData = getMissionData(self);
        location locDeliverLocation = getMissionEndLocation(objMissionData);
        updateMissionWaypoint(self, locDeliverLocation);
        return SCRIPT_CONTINUE;
    }
    public int escortIncomplete(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id objPlayer = getMissionHolder(self);
        string_id strMessage = new string_id(GENERIC_MISSION_MESSAGE_STRING_FILE, "incomplete");
        sendSystemMessage(objPlayer, strMessage);
        endMission(self);
        return SCRIPT_CONTINUE;
    }
    public int escortFailure(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id objPlayer = getMissionHolder(self);
        string_id strMessage = new string_id(GENERIC_MISSION_MESSAGE_STRING_FILE, "failed");
        sendSystemMessage(objPlayer, strMessage);
        endMission(self);
        return SCRIPT_CONTINUE;
    }
    public int escortSuccess(obj_id self, dictionary params) throws InterruptedException
    {
        if (!hasObjVar(self, "intCompleted"))
        {
            setObjVar(self, "intCompleted", 1);
            obj_id objPlayer = getMissionHolder(self);
            obj_id objEscortTarget = getObjIdObjVar(self, "objEscortTarget");
            if (!hasObjVar(self, "intMissionComplete"))
            {
                obj_id objMissionData = getMissionData(self);
                setObjVar(self, "intMissionComplete", 1);
                obj_id objEscorter = getObjIdObjVar(self, "objEscorter");
                string_id strResponse = getNPCEscortDropoffId(objMissionData);
                chat.chat(objEscortTarget, strResponse);
                return SCRIPT_CONTINUE;
            }
            playMusic(objPlayer, "sound/music_mission_complete.snd");
            messageTo(objEscortTarget, "destroySelf", null, 600, true);
            deliverReward(self);
            endMission(self);
        }
        return SCRIPT_CONTINUE;
    }
    public int OnEndMission(obj_id self, dictionary params) throws InterruptedException
    {
        return SCRIPT_CONTINUE;
    }
    public int onArrivedAtLocation(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id objPlayer = getMissionHolder(self);
        dictionary dctParams = new dictionary();
        dctParams.put("objMission", self);
        messageTo(objPlayer, "escort_Arrival", dctParams, 0, true);
        return SCRIPT_CONTINUE;
    }
}
