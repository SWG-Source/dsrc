package script.systems.missions.dynamic;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.utils;

public class mission_hunting extends script.systems.missions.base.mission_dynamic_base
{
    public mission_hunting()
    {
    }
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        return SCRIPT_CONTINUE;
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        return SCRIPT_CONTINUE;
    }
    public int abortMission(obj_id self, dictionary params) throws InterruptedException
    {
        sendHuntingIncomplete(self);
        return SCRIPT_CONTINUE;
    }
    public int OnStartMission(obj_id self, dictionary params) throws InterruptedException
    {
        setupInvisibleWaypoint(self);
        return SCRIPT_CONTINUE;
    }
    public int huntingIncomplete(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id objPlayer = getMissionHolder(self);
        string_id strMessage = new string_id(GENERIC_MISSION_MESSAGE_STRING_FILE, "incomplete");
        sendSystemMessage(objPlayer, strMessage);
        endMission(self);
        return SCRIPT_CONTINUE;
    }
    public int huntingFailure(obj_id self, dictionary params) throws InterruptedException
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
    public int huntingSuccess(obj_id self, dictionary params) throws InterruptedException
    {
        if (!hasObjVar(self, "intMissionCompleted"))
        {
            setObjVar(self, "intMissionCompleted", 1);
            deliverReward(self);
            obj_id objPlayer = getMissionHolder(self);
            playMusic(objPlayer, "sound/music_mission_complete.snd");
        }
        endMission(self);
        return SCRIPT_CONTINUE;
    }
    public int OnEndMission(obj_id self, dictionary params) throws InterruptedException
    {
        cleanupObjects(self);
        return SCRIPT_CONTINUE;
    }
}
