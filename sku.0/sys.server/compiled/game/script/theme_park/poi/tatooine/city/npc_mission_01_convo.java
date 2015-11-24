package script.theme_park.poi.tatooine.city;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.ai_lib;
import script.library.chat;
import script.library.factions;
import script.library.skill;

public class npc_mission_01_convo extends script.systems.missions.base.mission_dynamic_base
{
    public npc_mission_01_convo()
    {
    }
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        setObjVar(self, "mission.intPersistent", 1);
        ai_lib.setDefaultCalmBehavior(self, ai_lib.BEHAVIOR_SENTINEL);
        messageTo(self, "cleanUpScripts", null, 3, true);
        return SCRIPT_CONTINUE;
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        messageTo(self, "cleanUpScripts", null, 3, true);
        String[] strMissionTypes = new String[1];
        strMissionTypes[0] = NPC_PERSISTENT_MISSION_OBJVAR_NAMES[rand(0, NPC_PERSISTENT_MISSION_OBJVAR_NAMES.length - 1)];
        setObjVar(self, "mission.strMissionTypes", strMissionTypes);
        int intMinDifficulty = rand(1, 10);
        int intMaxDifficulty = rand(20, 30);
        setObjVar(self, "mission.intMinDifficulty", intMinDifficulty);
        setObjVar(self, "mission.intMaxDifficulty", intMaxDifficulty);
        stop(self);
        attachScript(self, "npc.converse.npc_converse_menu");
        attachScript(self, "systems.missions.base.mission_npc");
        ai_lib.setDefaultCalmBehavior(self, ai_lib.BEHAVIOR_SENTINEL);
        return SCRIPT_CONTINUE;
    }
    public int OnStartNpcConversation(obj_id self, obj_id speaker) throws InterruptedException
    {
        if (ai_lib.isInCombat(self) || ai_lib.isInCombat(speaker))
        {
            return SCRIPT_OVERRIDE;
        }
        obj_id objMission = getAssignedNPCMission(self, speaker);
        if (objMission != null)
        {
            if (hasObjVar(objMission, "intMissionComplete"))
            {
                string_id strResponse = sendNPCMissionSuccess(objMission, self);
                chat.chat(self, strResponse);
                return SCRIPT_CONTINUE;
            }
            else 
            {
                string_id greeting = new string_id("npc_mission/npc_mission_01", "npc_msn_01_greeting_onmission");
                chat.chat(self, greeting);
                return SCRIPT_CONTINUE;
            }
        }
        else 
        {
            obj_id[] objMissionArray = getMissionObjects(speaker);
            if (objMissionArray != null)
            {
                if (objMissionArray.length >= MAX_MISSIONS)
                {
                    string_id greeting = new string_id("npc_mission/npc_mission_01", "npc_msn_01_greeting_onothermission");
                    chat.chat(self, greeting);
                    return SCRIPT_CONTINUE;
                }
            }
        }
        string_id greeting = new string_id("npc_mission/npc_mission_01", "npc_msn_01_greeting_01");
        string_id response[] = new string_id[1];
        response[0] = new string_id("npc_mission/npc_mission_01", "npc_msn_01_presp_greeting_01");
        npcStartConversation(speaker, self, "recruiting/imperial_recruit", greeting, response);
        return SCRIPT_CONTINUE;
    }
    public int OnNpcConversationResponse(obj_id self, String convo, obj_id player, string_id response) throws InterruptedException
    {
        if ((response.getAsciiId()).equals("npc_msn_01_presp_greeting_01"))
        {
            string_id message = new string_id("npc_mission/npc_mission_01", "npc_msn_01_work_01");
            npcSpeak(player, message);
            npcRemoveConversationResponse(player, new string_id("npc_mission/npc_mission_01", "npc_msn_01_presp_greeting_01"));
            npcAddConversationResponse(player, new string_id("npc_mission/npc_mission_01", "npc_msn_01_presp_getmission_03"));
            npcAddConversationResponse(player, new string_id("npc_mission/npc_mission_01", "npc_msn_01_presp_getmission_04"));
            return SCRIPT_CONTINUE;
        }
        if ((response.getAsciiId()).equals("npc_msn_01_presp_greeting_02"))
        {
            string_id message = new string_id("npc_mission/npc_mission_01", "npc_msn_01_busy_01");
            npcSpeak(player, message);
            npcRemoveConversationResponse(player, new string_id("npc_mission/npc_mission_01", "npc_msn_01_presp_greeting_01"));
            npcRemoveConversationResponse(player, new string_id("npc_mission/npc_mission_01", "npc_msn_01_presp_greeting_02"));
            npcAddConversationResponse(player, new string_id("npc_mission/npc_mission_01", "npc_msn_01_presp_getmission_05"));
            return SCRIPT_CONTINUE;
        }
        if ((response.getAsciiId()).equals("npc_msn_01_presp_getmission_03") || (response.getAsciiId()).equals("npc_msn_01_presp_getmission_04") || (response.getAsciiId()).equals("npc_msn_01_presp_getmission_05"))
        {
            string_id message = new string_id("npc_mission/npc_mission_01", "npc_msn_01_work_02");
            npcSpeak(player, message);
            npcRemoveConversationResponse(player, new string_id("npc_mission/npc_mission_01", "npc_msn_01_presp_getmission_03"));
            npcRemoveConversationResponse(player, new string_id("npc_mission/npc_mission_01", "npc_msn_01_presp_getmission_04"));
            npcRemoveConversationResponse(player, new string_id("npc_mission/npc_mission_01", "npc_msn_01_presp_getmission_05"));
            npcAddConversationResponse(player, new string_id("npc_mission/npc_mission_01", "npc_msn_01_presp_getmission_01"));
            npcAddConversationResponse(player, new string_id("npc_mission/npc_mission_01", "npc_msn_01_presp_getmission_02"));
            return SCRIPT_CONTINUE;
        }
        if ((response.getAsciiId()).equals("npc_msn_01_presp_getmission_01") || (response.getAsciiId()).equals("npc_msn_01_presp_getmission_02"))
        {
            obj_id objMission = getAssignedNPCMission(self, player);
            if (objMission != null)
            {
                string_id strResponse = new string_id("npc_mission/npc_mission_01", "npc_msn_01_greeting_onmission");
                npcSpeak(player, strResponse);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            int intPlayerDifficulty = getLevel(player);
            if (intPlayerDifficulty <= getIntObjVar(self, "intMinDifficulty"))
            {
                string_id strResponse = new string_id("mission/mission_generic", "npc_job_request_wrong_difficulty");
                npcSpeak(player, strResponse);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            obj_id objMissionData = getMissionObjectFromNPC(self, intPlayerDifficulty);
            if (objMissionData != null)
            {
                if (validateNPCMissionForPlayer(self, objMissionData, player))
                {
                    deltadictionary dctMissionInformation = player.getScriptVars();
                    dctMissionInformation.put("mission.objNPCMission", objMissionData);
                    LOG("missions", "putting " + objMissionData + " into dictionary for player");
                    string_id message = getNPCMissionDescriptionId(objMissionData);
                    npcSpeak(player, message);
                    string_id[] responses = new string_id[2];
                    responses[0] = new string_id("mission/mission_generic", "npc_job_accept_yes");
                    responses[1] = new string_id("mission/mission_generic", "npc_job_accept_no");
                    npcSetConversationResponses(player, responses);
                    return SCRIPT_CONTINUE;
                }
            }
            string_id strSpam = new string_id("mission/mission_generic", "npc_job_none_available");
            npcSpeak(player, strSpam);
            npcEndConversation(player);
            return SCRIPT_CONTINUE;
        }
        if ((response.getAsciiId()).equals("npc_job_accept_yes"))
        {
            deltadictionary dctMissionInformation = player.getScriptVars();
            obj_id objMissionData = dctMissionInformation.getObjId("mission.objNPCMission");
            if (objMissionData == null)
            {
                return SCRIPT_CONTINUE;
            }
            string_id strResponse = new string_id("mission/mission_generic", "npc_job_accept_response_yes");
            LOG("missions", "objMissionData is " + objMissionData + " in npc crap");
            assignNPCMissionToPlayer(self, objMissionData, player);
            npcSpeak(player, strResponse);
            npcEndConversation(player);
        }
        if ((response.getAsciiId()).equals("npc_job_accept_no"))
        {
            string_id strResponse = new string_id("npc_mission/npc_mission_01", "npc_msn_01_decline");
            npcSpeak(player, strResponse);
            npcEndConversation(player);
        }
        return SCRIPT_CONTINUE;
    }
    public int cleanUpScripts(obj_id self, dictionary params) throws InterruptedException
    {
        if (!hasScript(self, "theme_park.poi.tatooine.city.npc_mission_convo"))
        {
            attachScript(self, "theme_park.poi.tatooine.city.npc_mission_convo");
        }
        return SCRIPT_CONTINUE;
    }
}
