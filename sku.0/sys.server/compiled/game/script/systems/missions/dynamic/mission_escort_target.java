package script.systems.missions.dynamic;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.ai_lib;
import script.library.chat;

public class mission_escort_target extends script.systems.missions.base.mission_dynamic_base
{
    public mission_escort_target()
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
        debugServerConsoleMsg(self, "mission_escort_target_script succesfully attached");
        attachScript(self, "npc.converse.npc_converse_menu");
        ai_lib.setDefaultCalmBehavior(self, ai_lib.BEHAVIOR_SENTINEL);
        return SCRIPT_CONTINUE;
    }
    public int OnStartNpcConversation(obj_id self, obj_id speaker) throws InterruptedException
    {
        obj_id objEscorter = getObjIdObjVar(self, "objEscorter");
        if (speaker != objEscorter)
        {
            doIncorrectPlayerBlurb(self, speaker);
            return SCRIPT_CONTINUE;
        }
        if (!hasObjVar(self, "intPickedUp"))
        {
            string_id greeting = new string_id("mission/mission_generic", "hello");
            string_id response[] = new string_id[1];
            response[0] = new string_id("mission/mission_generic", "escort_pickup_request");
            npcStartConversation(speaker, self, "escort_convo", greeting, response);
        }
        else 
        {
            string_id strSpam = new string_id("mission/mission_generic", "escort_follow_again");
            chat.chat(self, speaker, strSpam);
            ai_lib.aiFollow(self, speaker);
        }
        return SCRIPT_CONTINUE;
    }
    public int OnNpcConversationResponse(obj_id self, String convo, obj_id player, string_id response) throws InterruptedException
    {
        if ((response.getAsciiId()).equals("escort_pickup_request"))
        {
            obj_id objMission = getObjIdObjVar(self, "objMission");
            obj_id objMissionData = getMissionData(objMission);
            string_id strResponse = getNPCEscortPickupId(objMissionData);
            npcSpeak(self, strResponse);
            npcEndConversation(player);
            obj_id objEscorter = getObjIdObjVar(self, "objEscorter");
            if (objEscorter == player)
            {
                ai_lib.aiFollow(self, player);
                setObjVar(self, "intPickedUp", 1);
                location locEscortFinish = getMissionEndLocation(objMissionData);
                addLocationTarget("strEscortDestination", locEscortFinish, 50);
                dictionary dctParams = new dictionary();
                messageTo(objMission, "escort_Pickup", dctParams, 0, true);
            }
            else 
            {
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int OnDeath(obj_id self, obj_id killer, obj_id corpseId) throws InterruptedException
    {
        obj_id objMission = getObjIdObjVar(self, "objMission");
        sendEscortFail(objMission);
        return SCRIPT_CONTINUE;
    }
    public int OnArrivedAtLocation(obj_id self, String strName) throws InterruptedException
    {
        if (strName.equals("strEscortDestination"))
        {
            obj_id objMission = getObjIdObjVar(self, "objMission");
            obj_id objMissionData = getMissionData(objMission);
            string_id strResponse = getNPCEscortDropoffId(objMissionData);
            debugSpeakMsg(self, "IM HERE!. You're A WINNER!. Mission complete");
            sendEscortSuccess(objMission);
        }
        return SCRIPT_CONTINUE;
    }
    public int OnRemovingFromWorld(obj_id self) throws InterruptedException
    {
        return SCRIPT_CONTINUE;
    }
}
