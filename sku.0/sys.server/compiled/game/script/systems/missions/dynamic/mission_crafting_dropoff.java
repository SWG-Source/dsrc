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

public class mission_crafting_dropoff extends script.systems.missions.base.mission_dynamic_base
{
    public mission_crafting_dropoff()
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
    public int OnGiveItem(obj_id self, obj_id objItem, obj_id objPlayer) throws InterruptedException
    {
        return SCRIPT_CONTINUE;
    }
    public int OnStartNpcConversation(obj_id self, obj_id speaker) throws InterruptedException
    {
        obj_id objMission = getObjIdObjVar(self, "objMission");
        obj_id objMissionHolder = getMissionHolder(objMission);
        int intState = getIntObjVar(objMission, "intState");
        obj_id objPlayer = getObjIdObjVar(self, "objPlayer");
        if (intState == missions.STATE_DYNAMIC_DROPOFF)
        {
            if (speaker != objMissionHolder)
            {
                doIncorrectPlayerBlurb(self, speaker);
                return SCRIPT_CONTINUE;
            }
            obj_id objMissionData = getMissionData(objMission);
            String strItemToMake = getStringObjVar(objMissionData, "strItemToMake");
            if (hasCraftingMissionItem(speaker, strItemToMake))
            {
                revokeMissionSchematic(objPlayer, objMission);
                int intStringId = getIntObjVar(objMissionData, "intStringId");
                String strIdFileName = getStringObjVar(objMissionData, "strIdFileName");
                string_id strConvo = new string_id(strIdFileName, "m" + intStringId + "r");
                setObjVar(objMissionData, "strIdFileName", strIdFileName);
                String strSpeakString = getString(strConvo);
                chat.chat(self, speaker, strConvo);
                dictionary dctParams = new dictionary();
                sendCraftingSuccess(getObjIdObjVar(self, "objMission"));
                messageTo(self, "destroySelf", null, 120, true);
                return SCRIPT_OVERRIDE;
            }
            else 
            {
                string_id strSpam = new string_id("mission/mission_generic", "wrong_item");
                chat.chat(self, speaker, strSpam);
                return SCRIPT_CONTINUE;
            }
        }
        else 
        {
            string_id message = new string_id("mission/mission_generic", "crafting_already_dropped_off");
            chat.chat(self, speaker, message);
            doAnimationAction(self, anims.PLAYER_WAVE_ON_DISMISSING);
        }
        return SCRIPT_CONTINUE;
    }
    public int destroySelf(obj_id self, dictionary params) throws InterruptedException
    {
        destroyObject(self);
        return SCRIPT_CONTINUE;
    }
}
