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

public class mission_deliver_dropoff extends script.systems.missions.base.mission_dynamic_base
{
    public mission_deliver_dropoff()
    {
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        attachScript(self, "npc.converse.npc_converse_menu");
        debugServerConsoleMsg(self, "deliver dropoff script attached");
        factions.setFaction(self, "Unattackable");
        ai_lib.setDefaultCalmBehavior(self, ai_lib.BEHAVIOR_SENTINEL);
        return SCRIPT_CONTINUE;
    }
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        destroyObject(self);
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
        if (intState == missions.STATE_DYNAMIC_DROPOFF)
        {
            obj_id objMissionData = getMissionData(objMission);
            int intStringId = getIntObjVar(objMissionData, "intStringId");
            String strIdFileName = getStringObjVar(objMissionData, "strIdFileName");
            string_id strConvo = new string_id(strIdFileName, "m" + intStringId + "r");
            setObjVar(objMissionData, "strIdFileName", strIdFileName);
            String strSpeakString = getString(strConvo);
            chat.chat(self, speaker, strConvo);
            obj_id objDeliveryItem = getObjIdObjVar(self, "objDeliveryItem");
            dictionary dctParams = new dictionary();
            messageTo(objMission, "pickup_event", dctParams, 0, true);
            sendDeliverSuccess(getObjIdObjVar(self, "objMission"));
            messageTo(self, "destroySelf", null, 120, true);
        }
        else 
        {
            string_id message = new string_id("mission/mission_generic", "deliver_already_dropped_off");
            chat.chat(self, speaker, message);
            doAnimationAction(self, anims.PLAYER_WAVE_ON_DISMISSING);
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
