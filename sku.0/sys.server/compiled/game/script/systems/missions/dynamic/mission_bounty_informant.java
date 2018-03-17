package script.systems.missions.dynamic;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.ai_lib;
import script.library.anims;
import script.library.factions;
import script.library.chat;
import script.library.utils;

public class mission_bounty_informant extends script.systems.missions.base.mission_dynamic_base
{
    public mission_bounty_informant()
    {
    }
    public int OnAddedToWorld(obj_id self) throws InterruptedException
    {
        return SCRIPT_CONTINUE;
    }
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        messageTo(self, "removeInformant", null, 3.0f, false);
        return SCRIPT_CONTINUE;
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        setWantSawAttackTriggers(self, false);
        persistObject(self);
        attachScript(self, "npc.converse.npc_converse_menu");
        factions.setFaction(self, "Unattackable");
        ai_lib.setDefaultCalmBehavior(self, ai_lib.BEHAVIOR_SENTINEL);
        return SCRIPT_CONTINUE;
    }
    public int OnStartNpcConversation(obj_id self, obj_id speaker) throws InterruptedException
    {
        if (!hasSkill(speaker, "class_bountyhunter_phase1_novice"))
        {
            doAnimationAction(self, anims.PLAYER_RUB_CHIN_THOUGHTFUL);
            string_id strResponse = new string_id("mission/mission_generic", "informant_not_bounty_hunter");
            chat.chat(self, speaker, strResponse);
            return SCRIPT_CONTINUE;
        }
        obj_id objMission = getBountyMission(speaker);
        if (objMission == null)
        {
            doAnimationAction(self, anims.PLAYER_RUB_CHIN_THOUGHTFUL);
            string_id strResponse = new string_id("mission/mission_generic", "informant_no_bounty_mission");
            chat.chat(self, speaker, strResponse);
            return SCRIPT_CONTINUE;
        }
        obj_id objMissionHolder = getMissionHolder(objMission);
        obj_id objMissionData = getMissionData(objMission);
        int intInformantLevel = getIntObjVar(objMissionData, "intInformantLevel");
        dictionary dctParams = new dictionary();
        int intState = getIntObjVar(objMission, "intState");
        if (intState != STATE_BOUNTY_INFORMANT)
        {
            doIncorrectPlayerBlurb(self, speaker);
            return SCRIPT_CONTINUE;
        }
        if (!hasObjVar(objMissionData, "intPlayerMission"))
        {
            int intIndex = rand(1, 5);
            int intMyDifficulty = getIntObjVar(self, "intInformantLevel");
            if (intInformantLevel != intMyDifficulty)
            {
                if (intMyDifficulty > intInformantLevel)
                {
                    string_id strResponse = new string_id("mission/mission_bounty_informant", "informant_find_easier");
                    chat.chat(self, speaker, strResponse);
                    return SCRIPT_CONTINUE;
                }
                else if (intMyDifficulty < intInformantLevel)
                {
                    string_id strResponse = new string_id("mission/mission_bounty_informant", "informant_find_harder");
                    chat.chat(self, speaker, strResponse);
                    return SCRIPT_CONTINUE;
                }
                return SCRIPT_CONTINUE;
            }
            if (intInformantLevel == INFORMANT_EASY)
            {
                string_id strResponse = new string_id("mission/mission_bounty_informant", "target_easy_" + intIndex);
                chat.chat(self, speaker, strResponse);
                updateMissionWaypoint(objMission, getLocationObjVar(objMission, "locSpawnLocation"));
                strResponse = new string_id("mission/mission_bounty_informant", "target_location_received");
                sendSystemMessage(speaker, strResponse);
                setObjVar(objMission, "intState", STATE_BOUNTY_PROBE);
            }
            else if (intInformantLevel == INFORMANT_MEDIUM)
            {
                string_id strResponse = new string_id("mission/mission_bounty_informant", "target_medium_" + intIndex);
                chat.chat(self, speaker, strResponse);
                location locSpawnLocation = getMissionStartLocation(objMissionData);
                String strPlanet = locSpawnLocation.area;
                String strPlanetResponse = "target_hard_" + strPlanet + "_" + intIndex;
                strResponse = new string_id("mission/mission_bounty_informant", strPlanetResponse);
                strResponse = new string_id("mission/mission_bounty_informant", "target_biological_signature");
                sendSystemMessage(speaker, strResponse);
                setObjVar(objMission, "intState", STATE_BOUNTY_PROBE);
                if (hasObjVar(objMission, "intMissionDynamic"))
                {
                    setObjVar(objMission, "movingTarget", 1);
                    dctParams.put("moveTargetSequence", utils.getIntScriptVar(self, "moveTargetSequence"));
                    messageTo(objMission, "moveTarget", dctParams, rand(30, 60), false);
                }
            }
            else if (intInformantLevel == INFORMANT_HARD)
            {
                string_id strResponse = new string_id("mission/mission_bounty_informant", "target_hard_" + intIndex);
                chat.chat(self, speaker, strResponse);
                strResponse = new string_id("mission/mission_bounty_informant", "target_biological_signature");
                sendSystemMessage(speaker, strResponse);
                setObjVar(objMission, "intState", STATE_BOUNTY_PROBE);
                if (hasObjVar(objMission, "intMissionDynamic"))
                {
                    setObjVar(objMission, "movingTarget", 1);
                    dctParams.put("moveTargetSequence", utils.getIntScriptVar(self, "moveTargetSequence"));
                    messageTo(objMission, "moveTarget", dctParams, rand(30, 60), false);
                }
            }
            else 
            {
            }
        }
        else 
        {
        }
        return SCRIPT_CONTINUE;
    }
    public int removeInformant(obj_id self, dictionary params) throws InterruptedException
    {
        if (isValidId(self) && !isPlayer(self))
        {
            destroyObject(self);
        }
        return SCRIPT_CONTINUE;
    }
}
