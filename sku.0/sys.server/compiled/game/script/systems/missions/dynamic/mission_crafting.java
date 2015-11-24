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

public class mission_crafting extends script.systems.missions.base.mission_dynamic_base
{
    public mission_crafting()
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
        sendCraftingIncomplete(self);
        return SCRIPT_CONTINUE;
    }
    public int OnStartMission(obj_id self, dictionary params) throws InterruptedException
    {
        debugServerConsoleMsg(self, "Setting up mission data in mission object for DELIVER mission");
        obj_id objMissionData = getMissionData(self);
        location locStartLocation = getMissionStartLocation(objMissionData);
        setObjVar(self, "intState", missions.STATE_DYNAMIC_PICKUP);
        updateMissionWaypoint(self, locStartLocation);
        int intStringId = getIntObjVar(objMissionData, "intStringId");
        String strMessageString = "m" + intStringId + "d";
        String strTitleString = "m" + intStringId + "t";
        activateMissionWaypoint(self);
        string_id strSpam = new string_id("mission/mission_generic", "crafting_mission_start");
        sendSystemMessage(getMissionHolder(self), strSpam);
        return SCRIPT_CONTINUE;
    }
    public int craftingSuccess(obj_id self, dictionary params) throws InterruptedException
    {
        if (!hasObjVar(self, "intCompleted"))
        {
            setObjVar(self, "intCompleted", 1);
            obj_id objPlayer = getMissionHolder(self);
            playMusic(objPlayer, "sound/music_mission_complete.snd");
            deliverReward(self);
            endMission(self);
        }
        return SCRIPT_CONTINUE;
    }
    public int craftingFail(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id objPlayer = getMissionHolder(self);
        string_id strMessage = new string_id(GENERIC_MISSION_MESSAGE_STRING_FILE, "failed");
        sendSystemMessage(objPlayer, strMessage);
        endMission(self);
        return SCRIPT_CONTINUE;
    }
    public int craftingIncomplete(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id objPlayer = getMissionHolder(self);
        string_id strMessage = new string_id(GENERIC_MISSION_MESSAGE_STRING_FILE, "incomplete");
        sendSystemMessage(objPlayer, strMessage);
        endMission(self);
        return SCRIPT_CONTINUE;
    }
    public int OnEndMission(obj_id self, dictionary params) throws InterruptedException
    {
        cleanupObjects(self);
        cleanupLocationTargets(self);
        revokeMissionSchematic(getMissionHolder(self), self);
        obj_id objMissionData = getMissionData(self);
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
        int intDelay = rand(DELIVERY_INTEREST_MIN_DELAY, DELIVERY_INTEREST_MAX_DELAY);
        dictionary dctParams = new dictionary();
        messageTo(self, "crafting_Interest_Tracker", dctParams, intDelay, true);
        return SCRIPT_CONTINUE;
    }
}
