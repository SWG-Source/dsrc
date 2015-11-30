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
import script.library.factions;
import script.library.anims;
import script.library.missions;

public class mission_deliver_pickup extends script.systems.missions.base.mission_dynamic_base
{
    public mission_deliver_pickup()
    {
    }
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        destroyObject(self);
        return SCRIPT_CONTINUE;
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        attachScript(self, "npc.converse.npc_converse_menu");
        debugServerConsoleMsg(self, "deliver dropoff script attached");
        factions.setFaction(self, "Unattackable");
        ai_lib.setDefaultCalmBehavior(self, ai_lib.BEHAVIOR_SENTINEL);
        return SCRIPT_CONTINUE;
    }
    public int OnRemovingFromWorld(obj_id self) throws InterruptedException
    {
        if (!hasObjVar(self, "intKilled"))
        {
            obj_id objMission = getObjIdObjVar(self, "objMission");
            int intState = getIntObjVar(objMission, "intState");
            if (intState == missions.STATE_DYNAMIC_PICKUP)
            {
                setupSpawn(getObjIdObjVar(self, "objMission"), getLocation(self));
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int OnStartNpcConversation(obj_id self, obj_id speaker) throws InterruptedException
    {
        obj_id objMission = getObjIdObjVar(self, "objMission");
        obj_id objMissionHolder = getObjIdObjVar(self, "objPlayer");
        int intState = getIntObjVar(objMission, "intState");
        if (speaker != objMissionHolder)
        {
            doIncorrectPlayerBlurb(self, speaker);
            return SCRIPT_CONTINUE;
        }
        if (intState == missions.STATE_DYNAMIC_PICKUP)
        {
            obj_id objMissionData = getMissionData(objMission);
            int intStringId = getIntObjVar(objMissionData, "intStringId");
            String strIdFileName = getStringObjVar(objMissionData, "strIdFileName");
            string_id strConvo = new string_id(strIdFileName, "m" + intStringId + "p");
            setObjVar(objMissionData, "strIdFileName", strIdFileName);
            chat.chat(self, speaker, strConvo);
            obj_id objDeliveryItem = getObjIdObjVar(self, "objDeliveryItem");
            string_id strTitleId = new string_id("mission/mission_generic", "deliver_received_data");
            string_id strMissionTitleId = new string_id(strIdFileName, "m" + intStringId + "t");
            String strMissionTitle = getString(strMissionTitleId);
            String strSystemMessage = getString(strTitleId);
            strSystemMessage = strSystemMessage + " " + strMissionTitle;
            sendSystemMessageTestingOnly(speaker, strSystemMessage);
            dictionary dctParams = new dictionary();
            messageTo(objMission, "pickup_event", dctParams, 0, true);
            setObjVar(objMission, "intState", missions.STATE_DYNAMIC_DROPOFF);
            location locDropoffLocation = getMissionEndLocation(objMissionData);
            setObjVar(objMission, "objDeliveryItem", objDeliveryItem);
            debugServerConsoleMsg(self, "objMissionData for npc pickup is " + objMissionData);
            if (hasObjVar(objMissionData, "objEndNPC"))
            {
                setObjVar(objMission, "intMissionComplete", 1);
            }
            else 
            {
                setupSpawn(objMission, locDropoffLocation);
            }
            updateMissionWaypoint(objMission, locDropoffLocation);
            messageTo(self, "destroySelf", null, 120, true);
        }
        else 
        {
            string_id message = new string_id("mission/mission_generic", "deliver_already_picked_up");
            chat.chat(self, speaker, message);
            doAnimationAction(self, anims.PLAYER_POINT_AWAY);
            return SCRIPT_CONTINUE;
        }
        return SCRIPT_CONTINUE;
    }
    public int destroySelf(obj_id self, dictionary params) throws InterruptedException
    {
        destroyObject(self);
        return SCRIPT_CONTINUE;
    }
}
