package script.npc.converse;

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
import script.library.utils;
import script.library.money;

public class npc_convo_assassin_mission extends script.systems.missions.base.mission_dynamic_base
{
    public npc_convo_assassin_mission()
    {
    }
    public static final String BASE_CONVO = "missions/assassin_mission_npc_convo";
    public static final String ASSASSIN_MISSION_DEVICE = "object/tangible/mission/assassin_missions_device.iff";
    public static final int DEVICE_LOC_FEE = 119;
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        removeTriggerVolume("alertTriggerVolume");
        return SCRIPT_CONTINUE;
    }
    public int OnObjectMenuRequest(obj_id self, obj_id player, menu_info item) throws InterruptedException
    {
        return SCRIPT_CONTINUE;
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        removeTriggerVolume("alertTriggerVolume");
        attachScript(self, "npc.converse.npc_converse_menu");
        stop(self);
        attachScript(self, "systems.missions.base.mission_npc");
        return SCRIPT_CONTINUE;
    }
    public int OnStartNpcConversation(obj_id self, obj_id speaker) throws InterruptedException
    {
        if (ai_lib.isInCombat(self) || ai_lib.isInCombat(speaker))
        {
            return SCRIPT_OVERRIDE;
        }
        obj_id[] objMissionArray = getMissionObjects(speaker);
        if (objMissionArray != null)
        {
            if (objMissionArray.length >= MAX_MISSIONS)
            {
                string_id greeting = new string_id("mission/mission_generic", "npc_on_other_mission");
                chat.chat(self, speaker, greeting);
                return SCRIPT_CONTINUE;
            }
        }
        int greetingChoice = rand(1, 3);
        if (hasObjVar(self, "greetingChoice"))
        {
            greetingChoice = getIntObjVar(self, "greetingChoice");
        }
        else 
        {
            setObjVar(self, "greetingChoice", greetingChoice);
        }
        String type = getMissionGiverType(self);
        String playerFaction = factions.getFaction(speaker);
        if (!type.equals("neutral"))
        {
            if (!playerFaction.equals(type))
            {
                string_id notInFaction = new string_id(BASE_CONVO, type + "_npc_cannot_offer");
                chat.chat(self, speaker, notInFaction);
                return SCRIPT_OVERRIDE;
            }
        }
        else if (playerFaction.equals("Imperial") || playerFaction.equals("Rebel"))
        {
            string_id notNeutral = new string_id(BASE_CONVO, type + "_npc_cannot_offer");
            chat.chat(self, speaker, notNeutral);
            return SCRIPT_OVERRIDE;
        }
        string_id greeting = new string_id(BASE_CONVO, type + "_npc_greet_" + greetingChoice);
        string_id responseChoices[] = new string_id[2];
        responseChoices[0] = new string_id(BASE_CONVO, "player_job_request");
        responseChoices[1] = new string_id(BASE_CONVO, type + "_player__1_" + greetingChoice);
        npcStartConversation(speaker, self, "assassin_mission", greeting, responseChoices);
        return SCRIPT_CONTINUE;
    }
    public int OnNpcConversationResponse(obj_id self, String convo, obj_id player, string_id response) throws InterruptedException
    {
        String type = getMissionGiverType(self);
        int greetingChoice = getIntObjVar(self, "greetingChoice");
        string_id responseChoices[] = new string_id[2];
        if ((response.getAsciiId()).equals("player_job_request"))
        {
            if (utils.playerHasItemByTemplate(player, ASSASSIN_MISSION_DEVICE))
            {
                string_id message = new string_id(BASE_CONVO, type + "_npc_job_offer_" + greetingChoice);
                npcSpeak(player, message);
                responseChoices[0] = new string_id(BASE_CONVO, "player_job_accept");
                responseChoices[1] = new string_id(BASE_CONVO, "player_job_refuse");
                npcSetConversationResponses(player, responseChoices);
                return SCRIPT_CONTINUE;
            }
            else 
            {
                string_id message = new string_id(BASE_CONVO, type + "_npc_no_device_response");
                npcSpeak(player, message);
                responseChoices[0] = new string_id(BASE_CONVO, "player_device_query");
                responseChoices[1] = new string_id(BASE_CONVO, "player_farewell");
                npcSetConversationResponses(player, responseChoices);
                return SCRIPT_CONTINUE;
            }
        }
        if ((response.getAsciiId()).equals(type + "_player_line_1_" + greetingChoice))
        {
            string_id message = new string_id(BASE_CONVO, type + "_npc_response_1_" + greetingChoice);
            npcSpeak(player, message);
            responseChoices[0] = new string_id(BASE_CONVO, type + "_player_line_2_" + greetingChoice);
            responseChoices[1] = new string_id(BASE_CONVO, "player_job_request" + greetingChoice);
            npcSetConversationResponses(player, responseChoices);
            return SCRIPT_CONTINUE;
        }
        if ((response.getAsciiId()).equals("player_device_query"))
        {
            string_id message = new string_id(BASE_CONVO, type + "_npc_device_description");
            npcSpeak(player, message);
            responseChoices[0] = new string_id(BASE_CONVO, "player_device_interest");
            responseChoices[1] = new string_id(BASE_CONVO, "player_farewell");
            npcSetConversationResponses(player, responseChoices);
            return SCRIPT_CONTINUE;
        }
        if ((response.getAsciiId()).equals("player_device_description"))
        {
            string_id message = new string_id(BASE_CONVO, type + "_npc_device_location_fee");
            npcSpeak(player, message);
            responseChoices[0] = new string_id(BASE_CONVO, "player_device_pay_fee");
            responseChoices[1] = new string_id(BASE_CONVO, "player_farewell");
            npcSetConversationResponses(player, responseChoices);
            return SCRIPT_CONTINUE;
        }
        if ((response.getAsciiId()).equals("player_device_location_fee"))
        {
            if (money.hasFunds(player, money.MT_TOTAL, DEVICE_LOC_FEE))
            {
                money.bankTo(player, self, DEVICE_LOC_FEE);
                string_id message = new string_id(BASE_CONVO, type + "_npc_device_location_fee");
                npcSpeak(player, message);
                return SCRIPT_CONTINUE;
            }
            else 
            {
                string_id message = new string_id(BASE_CONVO, type + "_npc_end_convo_no_credits");
                npcSpeak(player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
        }
        if ((response.getAsciiId()).equals("npc_job_accept"))
        {
            obj_id objMission = getAssignedNPCMission(self, player);
            if (objMission != null)
            {
                string_id strResponse = new string_id("mission/mission_generic", "npc_job_request_already_have");
                npcSpeak(player, strResponse);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            string_id strResponse = new string_id(BASE_CONVO, type + "_npc_give_job_response");
            npcEndConversation(player);
            chat.chat(self, player, strResponse);
        }
        if ((response.getAsciiId()).equals("npc_job_refuse"))
        {
            string_id strResponse = new string_id(BASE_CONVO, type + "_npc_turned_down_response");
            npcEndConversation(player);
            chat.chat(self, player, strResponse);
        }
        if ((response.getAsciiId()).equals(type + "_player_line_2_" + greetingChoice))
        {
            string_id message = new string_id(BASE_CONVO, type + "_npc_end_convo_get_lost_" + greetingChoice);
            npcSpeak(player, message);
            npcEndConversation(player);
            return SCRIPT_CONTINUE;
        }
        string_id message = new string_id(BASE_CONVO, type + "_npc_farewell_" + rand(1, 3));
        npcSpeak(player, message);
        npcEndConversation(player);
        return SCRIPT_CONTINUE;
    }
    public String getMissionGiverType(obj_id self) throws InterruptedException
    {
        String type = "neutral";
        final String npc_type = getCreatureName(self);
        if (npc_type != null)
        {
            if (npc_type.equals("assassin_mission_recruiter_imperial"))
            {
                type = "Imperial";
            }
            else if (npc_type.equals("assassin_mission_recruiter_rebel"))
            {
                type = "Rebel";
            }
        }
        return type;
    }
}
