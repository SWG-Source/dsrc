package script.systems.missions.dynamic;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.utils;

public class mission_survey extends script.systems.missions.base.mission_dynamic_base
{
    public mission_survey()
    {
    }
    public int OnGetAttributes(obj_id self, obj_id player, String[] names, String[] attribs) throws InterruptedException
    {
        if (player == null || names == null || attribs == null || names.length != attribs.length)
        {
            return SCRIPT_CONTINUE;
        }
        int i = getFirstFreeIndex(names);
        String resource = getStringObjVar(self, OBJVAR_SURVEY_MISSION_RESOURCE);
        names[i] = "@" + new string_id("ui_mission", "attribs_resource");
        attribs[i] = "@" + new string_id(SID_SURVEY_RESOURCE_NAMES_TABLE, resource);
        ++i;
        names[i] = "@" + new string_id("ui_mission", "attribs_efficiency");
        attribs[i] = "" + getIntObjVar(self, OBJVAR_SURVEY_MISSION_EFFECIENCY);
        ++i;
        return SCRIPT_CONTINUE;
    }
    public int abortMission(obj_id self, dictionary params) throws InterruptedException
    {
        sendSurveyIncomplete(self);
        return SCRIPT_CONTINUE;
    }
    public int OnStartMission(obj_id self, dictionary params) throws InterruptedException
    {
        setupInvisibleWaypoint(self);
        obj_id objPlayer = getMissionHolder(self);
        dictionary dctParams = new dictionary();
        dctParams.put("eventName", "SurveyMissionGranted");
        messageTo(objPlayer, "handleHolocronEvent", dctParams, 0, false);
        string_id strSpam = new string_id("mission/mission_generic", "survey_start");
        sendSystemMessage(objPlayer, strSpam);
        return SCRIPT_CONTINUE;
    }
    public int surveySuccess(obj_id self, dictionary params) throws InterruptedException
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
    public int surveyFail(obj_id self, dictionary params) throws InterruptedException
    {
        endMission(self);
        return SCRIPT_CONTINUE;
    }
    public int surveyIncomplete(obj_id self, dictionary params) throws InterruptedException
    {
        endMission(self);
        return SCRIPT_CONTINUE;
    }
    public int OnEndMission(obj_id self, dictionary params) throws InterruptedException
    {
        return SCRIPT_CONTINUE;
    }
    public int surveyEvent(obj_id self, dictionary params) throws InterruptedException
    {
        return SCRIPT_CONTINUE;
    }
}
