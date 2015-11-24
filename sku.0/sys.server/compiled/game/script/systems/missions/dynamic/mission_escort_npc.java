package script.systems.missions.dynamic;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.utils;
import script.library.skill;
import script.library.locations;
import script.library.regions;
import script.library.ai_lib;
import script.library.chat;

public class mission_escort_npc extends script.systems.missions.base.mission_dynamic_base
{
    public mission_escort_npc()
    {
    }
    public int OnRemovingFromWorld(obj_id self) throws InterruptedException
    {
        removeObjVar(self, "intOnMissionQueue");
        return SCRIPT_CONTINUE;
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        attachScript(self, "npc.converse.npc_converse_menu");
        ai_lib.setDefaultCalmBehavior(self, ai_lib.BEHAVIOR_SENTINEL);
        return SCRIPT_CONTINUE;
    }
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        if (!hasScript(self, "systems.missions.base.mission_cleanup_tracker"))
        {
            attachScript(self, "systems.missions.base.mission_cleanup_tracker");
        }
        return SCRIPT_CONTINUE;
    }
    public int OnStartNpcConversation(obj_id self, obj_id speaker) throws InterruptedException
    {
        string_id greeting = new string_id("mission/mission_generic", "npc_job_hello");
        string_id response[] = new string_id[2];
        response[0] = new string_id("mission/mission_generic", "npc_job_request");
        npcStartConversation(speaker, self, null, greeting, response);
        return SCRIPT_CONTINUE;
    }
    public obj_id getMyMission(obj_id self) throws InterruptedException
    {
        if (hasObjVar(self, "intPickedUp"))
        {
            return null;
        }
        if (!hasObjVar(self, "mission.objEscortMission"))
        {
            obj_id objMissionData = createMissionObjectInCreatureMissionBag(self);
            String strFaction = "";
            if (hasObjVar(self, "strFaction"))
            {
                strFaction = getStringObjVar(self, "strFaction");
            }
            int intDifficulty = getIntObjVar(self, "intDifficulty");
            location locCurrentLocation = getLocation(self);
            if (intDifficulty == 0)
            {
                intDifficulty = rand(1, 20);
            }
            objMissionData = createEscortTargetMission(objMissionData, self, locCurrentLocation, intDifficulty, locCurrentLocation.area, strFaction);
            if (objMissionData != null)
            {
                setObjVar(self, "mission.objEscortMission", objMissionData);
                setObjVar(objMissionData, "objOwner", self);
                return objMissionData;
            }
            else 
            {
                return null;
            }
        }
        else 
        {
            obj_id objMissionData = getObjIdObjVar(self, "mission.objEscortMission");
            return objMissionData;
        }
    }
    public int destroySelf(obj_id self, dictionary params) throws InterruptedException
    {
        destroyObject(self);
        return SCRIPT_CONTINUE;
    }
    public int OnNpcConversationResponse(obj_id self, String convo, obj_id player, string_id response) throws InterruptedException
    {
        if ((response.getAsciiId()).equals("npc_job_accept_yes"))
        {
            deltadictionary dctMissionInformation = player.getScriptVars();
            obj_id objMissionData = dctMissionInformation.getObjId("mission.objNPCMission");
            if (objMissionData == null)
            {
                return SCRIPT_CONTINUE;
            }
            setObjVar(objMissionData, "intPickedUp", 1);
            assignNPCMissionToPlayer(self, objMissionData, player);
            string_id strResponse = new string_id("mission/mission_generic", "npc_job_accept_response_yes");
            npcEndConversation(player);
            chat.chat(self, player, strResponse);
        }
        if ((response.getAsciiId()).equals("npc_job_request"))
        {
            if (hasMissionFromNPC(player, self))
            {
                string_id strResponse = new string_id("mission/mission_generic", "npc_job_request_already_have");
                chat.chat(self, player, strResponse);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            int intPlayerDifficulty = getLevel(player);
            obj_id objMissionData = getMyMission(self);
            if (objMissionData != null)
            {
                deltadictionary dctMissionInformation = player.getScriptVars();
                dctMissionInformation.put("mission.objNPCMission", objMissionData);
                string_id message = getNPCMissionDescriptionId(objMissionData);
                npcSpeak(player, message);
                string_id[] responses = new string_id[2];
                responses[0] = new string_id("mission/mission_generic", "npc_job_accept_yes");
                responses[1] = new string_id("mission/mission_generic", "npc_job_accept_no");
                npcSetConversationResponses(player, responses);
                return SCRIPT_CONTINUE;
            }
            else 
            {
                string_id strResponse = new string_id("mission/misison_generic", "npc_job_request_no_job");
                chat.chat(self, player, strResponse);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int escort_Accepted(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id objMission = params.getObjId("objMission");
        obj_id objMissionData = getMissionData(objMission);
        obj_id objPlayer = getMissionHolder(objMission);
        ai_lib.aiFollow(self, objPlayer);
        setObjVar(self, "intPickedUp", 1);
        location locEscortFinish = getMissionEndLocation(objMissionData);
        addLocationTarget("strEscortDestination", locEscortFinish, 30);
        return SCRIPT_CONTINUE;
    }
    public int OnArrivedAtLocation(obj_id self, String strName) throws InterruptedException
    {
        if (strName.equals("strEscortDestination"))
        {
            debugSpeakMsg(self, "IM HERE!. You're A WINNER!. Mission complete");
            sendEscortSuccess(getObjIdObjVar(self, "objMission"));
        }
        return SCRIPT_CONTINUE;
    }
}
