package script.systems.missions.base;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.utils;
import script.library.skill;
import script.library.money;
import script.library.group;
import script.library.factions;
import script.library.prose;
import script.library.hq;
import script.library.jedi;
import script.library.city;
import script.library.missions;
import script.library.space_utils;
import script.library.session;

public class mission_player extends script.systems.missions.base.mission_player_base
{
    public mission_player()
    {
    }
    public static final String[] OPS_APPEARANCES = 
    {
        "object/mobile/dressed_rebel_trooper_human_female_01.iff",
        "object/mobile/dressed_rebel_trooper_twk_female_01.iff",
        "object/mobile/dressed_rebel_trooper_bith_m_01.iff",
        "object/mobile/dressed_rebel_trooper_sullustan_male_01.iff"
    };
    public static final string_id IMPROVED_JOB_MARKET_MESSAGE = new string_id("city/city", "improved_job_market_message");
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        final String[] strSchematics = 
        {
            "object/draft_schematic/item/quest_item/attunement_grid.iff",
            "object/draft_schematic/item/quest_item/current_alternator.iff",
            "object/draft_schematic/item/quest_item/feedback_controller.iff",
            "object/draft_schematic/item/quest_item/output_governor.iff",
            "object/draft_schematic/item/quest_item/power_regulator.iff"
        };
        for (int intI = 0; intI < strSchematics.length; intI++)
        {
            boolean boolHasMission = false;
            if (hasSchematic(self, strSchematics[intI]))
            {
                obj_id[] objMissions = getMissionObjects(self);
                if ((objMissions != null) && (objMissions.length > 0))
                {
                    for (int intM = 0; intM < objMissions.length; intM++)
                    {
                        if (hasObjVar(objMissions[intM], "strSchematic"))
                        {
                            String strSchematic = getStringObjVar(objMissions[intM], "strSchematic");
                            String strTest = strSchematics[intI];
                            if (strSchematic.equals(strTest))
                            {
                                intM = objMissions.length;
                                boolHasMission = true;
                            }
                        }
                    }
                }
                if (!boolHasMission)
                {
                    revokeSchematic(self, strSchematics[intI]);
                }
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int OnPlayerRequestMissionBoard(obj_id self, obj_id objPlayer, obj_id objMissionTerminal, obj_id[] objMissionData) throws InterruptedException
    {
        if (objMissionData.length < 10)
        {
            return SCRIPT_CONTINUE;
        }
        location locTest = getMissionLocation(self);
        if (!areMissionsAllowed(locTest))
        {
            sendSystemMessageTestingOnly(self, "missions only work on naboo and tatooine, you are on " + locTest.area);
        }
        int city_id = getCityAtLocation(getLocation(objMissionTerminal), 0);
        if (cityExists(city_id) && city.isCityBanned(objPlayer, city_id))
        {
            sendSystemMessage(objPlayer, new string_id("city/city", "city_banned"));
            return SCRIPT_CONTINUE;
        }
        if (hasObjVar(objMissionTerminal, "intBounty"))
        {
            if (!hasSkill(self, "class_bountyhunter_phase1_novice"))
            {
                string_id strSpam = new string_id("mission/mission_generic", "not_bounty_hunter_terminal");
                sendSystemMessage(self, strSpam);
                return SCRIPT_OVERRIDE;
            }
        }
        float fltTerminalRewardModifier = 1.0f;
        if (utils.hasScriptVar(objPlayer, "slicing.terminal"))
        {
            obj_id objSlicedTerminal = utils.getObjIdScriptVar(objPlayer, "slicing.terminal");
            if (objSlicedTerminal == objMissionTerminal)
            {
                fltTerminalRewardModifier = utils.getFloatScriptVar(objPlayer, "slicing.terminal_bonus");
                if (fltTerminalRewardModifier < 1)
                {
                    fltTerminalRewardModifier = 1.0f;
                }
            }
        }
        city_id = city.checkCity(objPlayer, false);
        if (city_id > 0 && city.cityHasSpec(city_id, city.SF_SPEC_MISSIONS))
        {
            String message = getString(IMPROVED_JOB_MARKET_MESSAGE);
            sendConsoleMessage(objPlayer, message);
            fltTerminalRewardModifier += 0.15f;
        }
        debugServerConsoleMsg(self, "locTest is " + locTest);
        String strPlanet = locTest.area;
        obj_id objMasterSpawner;
        String strFaction = "";
        String strDestroyFaction = "";
        String strPlayerFaction = factions.getFaction(self);
        if (hasObjVar(objMissionTerminal, "faction"))
        {
            strFaction = getStringObjVar(objMissionTerminal, "faction");
            strDestroyFaction = strFaction;
            if (factions.isCovert(self))
            {
                if (strFaction.equals(strPlayerFaction))
                {
                    strDestroyFaction = strFaction + "_military";
                }
                else 
                {
                    strDestroyFaction = strFaction + "_non_military";
                }
            }
            else 
            {
                strDestroyFaction = strFaction + "_non_military";
            }
        }
        obj_id objHq = null;
        obj_id topMost = getTopMostContainer(objMissionTerminal);
        if (isIdValid(topMost) && hasObjVar(topMost, hq.VAR_HQ_BASE))
        {
            objHq = topMost;
        }
        obj_id objMission;
        int intLevel = 0;
        intLevel = skill.getGroupLevel(self);
        if (hasObjVar(objMissionTerminal, "intBounty"))
        {
            debugServerConsoleMsg(self, "making bounty mission");
            String opposingFaction = "";
            if (strPlayerFaction != null)
            {
                if (strPlayerFaction.equals("Imperial"))
                {
                    opposingFaction = "Rebel";
                }
                if (strPlayerFaction.equals("Rebel"))
                {
                    opposingFaction = "Imperial";
                }
            }
            int intBountyDifficulty = getBountyDifficulty(objPlayer);
            int intPlayerDifficulty = getLevel(objPlayer);
            int pvpBountyCount = 0;
            for (int intI = 0; intI < objMissionData.length; intI++)
            {
                cleanMissionObject(objMissionData[intI]);
                obj_id objTest = null;
                LOG("missions", "Making regular missions");
                if (pvpBountyCount < 1)
                {
                    objTest = createJediBountyMission(objMissionData[intI], objMissionTerminal, strFaction, intPlayerDifficulty, objPlayer, missions.BOUNTY_FLAG_SMUGGLER);
                    if (objTest == null)
                    {
                        objTest = createJediBountyMission(objMissionData[intI], objMissionTerminal, strFaction, intPlayerDifficulty, objPlayer, missions.BOUNTY_FLAG_NONE);
                        if (objTest == null)
                        {
                            objTest = createDynamicBountyMission(objMissionData[intI], objMissionTerminal, intBountyDifficulty, intPlayerDifficulty, locTest.area, strFaction);
                        }
                    }
                }
                if (pvpBountyCount >= 1 && pvpBountyCount < 3)
                {
                    objTest = createJediBountyMission(objMissionData[intI], objMissionTerminal, opposingFaction, intPlayerDifficulty, objPlayer, missions.BOUNTY_FLAG_NONE);
                    if (objTest == null)
                    {
                        objTest = createDynamicBountyMission(objMissionData[intI], objMissionTerminal, intBountyDifficulty, intPlayerDifficulty, locTest.area, strFaction);
                    }
                }
                if (pvpBountyCount >= 3 && pvpBountyCount < 5)
                {
                    objTest = createJediBountyMission(objMissionData[intI], objMissionTerminal, strFaction, intPlayerDifficulty, objPlayer, missions.BOUNTY_FLAG_NONE);
                    if (objTest == null)
                    {
                        objTest = createDynamicBountyMission(objMissionData[intI], objMissionTerminal, intBountyDifficulty, intPlayerDifficulty, locTest.area, strFaction);
                    }
                }
                if (pvpBountyCount >= 5)
                {
                    objTest = createDynamicBountyMission(objMissionData[intI], objMissionTerminal, intBountyDifficulty, intPlayerDifficulty, locTest.area, strFaction);
                }
                if (objTest == null)
                {
                    setMissionStatus(objMissionData[intI], 0);
                }
                else 
                {
                    setMissionStatus(objMissionData[intI], 1);
                    if (isIdValid(objHq))
                    {
                        setObjVar(objTest, "hq", objHq);
                    }
                }
                pvpBountyCount++;
            }
        }
        else if (hasObjVar(objMissionTerminal, "intScout"))
        {
            for (int intI = 0; intI < objMissionData.length; intI = intI + 2)
            {
                cleanMissionObject(objMissionData[intI]);
                cleanMissionObject(objMissionData[intI + 1]);
                obj_id objTest = createHuntingMission(objMissionData[intI], objMissionTerminal, intLevel, "");
                if (objTest == null)
                {
                    setMissionStatus(objMissionData[intI], 0);
                }
                else 
                {
                    setMissionStatus(objMissionData[intI], 1);
                    if (isIdValid(objHq))
                    {
                        setObjVar(objTest, "hq", objHq);
                    }
                }
                objTest = createReconMission(objMissionData[intI + 1], objMissionTerminal, getMissionLocation(self), intLevel, strPlanet, "");
                if (objTest == null)
                {
                    setMissionStatus(objMissionData[intI + 1], 0);
                }
                else 
                {
                    setMissionStatus(objMissionData[intI + 1], 1);
                    if (isIdValid(objHq))
                    {
                        setObjVar(objTest, "hq", objHq);
                    }
                }
            }
        }
        else if (hasObjVar(objMissionTerminal, "intEntertainer"))
        {
            for (int intI = 0; intI < objMissionData.length; intI = intI + 2)
            {
                cleanMissionObject(objMissionData[intI]);
                cleanMissionObject(objMissionData[intI + 1]);
                obj_id objTest = createDancerMission(objMissionData[intI], objMissionTerminal, 0, strFaction);
                if (objTest == null)
                {
                    setMissionStatus(objMissionData[intI], 0);
                }
                else 
                {
                    setMissionStatus(objMissionData[intI], 1);
                    if (isIdValid(objHq))
                    {
                        setObjVar(objTest, "hq", objHq);
                    }
                }
                objTest = createMusicianMission(objMissionData[intI + 1], objMissionTerminal, 0, strFaction);
                if (objTest == null)
                {
                    setMissionStatus(objMissionData[intI + 1], 0);
                }
                else 
                {
                    setMissionStatus(objMissionData[intI + 1], 1);
                    if (isIdValid(objHq))
                    {
                        setObjVar(objTest, "hq", objHq);
                    }
                }
            }
        }
        else if (hasObjVar(objMissionTerminal, "intArtisan"))
        {
            for (int intI = 0; intI < objMissionData.length; intI = intI + 2)
            {
                cleanMissionObject(objMissionData[intI]);
                cleanMissionObject(objMissionData[intI + 1]);
                obj_id objTest = createSurveyMission(objMissionData[intI], objMissionTerminal, intLevel, strFaction);
                if (objTest == null)
                {
                    setMissionStatus(objMissionData[intI], 0);
                }
                else 
                {
                    setMissionStatus(objMissionData[intI], 1);
                    if (isIdValid(objHq))
                    {
                        setObjVar(objTest, "hq", objHq);
                    }
                }
                objTest = createCraftingMission(objMissionData[intI + 1], objMissionTerminal, getMissionLocation(self), intLevel, strFaction);
                if (objTest == null)
                {
                    setMissionStatus(objMissionData[intI + 1], 0);
                }
                else 
                {
                    setMissionStatus(objMissionData[intI + 1], 1);
                    if (isIdValid(objHq))
                    {
                        setObjVar(objTest, "hq", objHq);
                    }
                }
            }
        }
        else 
        {
            for (int intI = 0; intI < objMissionData.length; intI = intI + 2)
            {
                cleanMissionObject(objMissionData[intI]);
                cleanMissionObject(objMissionData[intI + 1]);
                obj_id objTest = createDeliverMissionFromLocation(objMissionData[intI], self, locTest, intLevel, locTest.area, strFaction, fltTerminalRewardModifier);
                if (objTest == null)
                {
                    setMissionStatus(objMissionData[intI], 0);
                }
                else 
                {
                    setMissionStatus(objMissionData[intI], 1);
                    if (isIdValid(objHq))
                    {
                        setObjVar(objTest, "hq", objHq);
                    }
                }
                int destructionMissionLevel = intLevel + rand(-2, 2);
                if (destructionMissionLevel > 90)
                {
                    destructionMissionLevel = 90;
                }
                objTest = createDestructionMissionDataFromLocation(objMissionData[intI + 1], self, locTest, destructionMissionLevel, locTest.area, strDestroyFaction, fltTerminalRewardModifier);
                if (!isIdValid(objTest))
                {
                    setMissionStatus(objMissionData[intI + 1], 0);
                }
                else 
                {
                    setMissionStatus(objMissionData[intI + 1], 1);
                    if (isIdValid(objHq))
                    {
                        setObjVar(objTest, "hq", objHq);
                    }
                }
            }
        }
        if (utils.hasScriptVar(objPlayer, "slicing.terminal"))
        {
            obj_id objSlicedTerminal = utils.getObjIdScriptVar(objPlayer, "slicing.terminal");
            if (objSlicedTerminal == objMissionTerminal)
            {
                utils.removeScriptVar(objPlayer, "slicing.terminal");
                utils.removeScriptVar(objPlayer, "slicing.terminal_bonus");
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int OnMissionAssigned(obj_id self, dictionary params) throws InterruptedException
    {
        playMusic(self, "sound/music_mission_accepted.snd");
        obj_id objMission = params.getObjId("missionObject");
        LOG("mission", "starting mission " + objMission.toString());
        startMission(objMission);
        return SCRIPT_CONTINUE;
    }
    public int OnAssignMission(obj_id self, dictionary params) throws InterruptedException
    {
        LOG("LOG_CHANNEL", "Mission Assigned -- " + params);
        if (params == null)
        {
            debugServerConsoleMsg(self, "Params is most definitely Null");
        }
        else 
        {
            debugServerConsoleMsg(self, "Params is " + params.toString());
        }
        obj_id objMessageSource = params.getObjId("messageSource");
        if (objMessageSource == null)
        {
            debugServerConsoleMsg(self, "messageSource is null");
        }
        obj_id objMissionData = params.getObjId("missionData");
        String strMissionType = getMissionType(objMissionData);
        if (strMissionType == null || strMissionType.equals(""))
        {
            return SCRIPT_CONTINUE;
        }
        debugServerConsoleMsg(self, "OnAssignMission mission type = " + strMissionType);
        session.logActivity(self, session.ACTIVITY_MISSION_TERMINAL);
        if (strMissionType.equals("bounty"))
        {
            obj_id objBountyMission = getBountyMission(self);
            if (objBountyMission != null)
            {
                string_id strSpam = new string_id("mission/mission_generic", "too_many_missions");
                sendSystemMessage(self, strSpam);
                return SCRIPT_CONTINUE;
            }
            messageTo(self, "informantComm", null, 5.0f, false);
        }
        if (strMissionType.equals("assassin"))
        {
            obj_id objAssassinMission = getAssassinMission(self);
            if (objAssassinMission != null)
            {
                string_id strSpam = new string_id("mission/mission_generic", "too_many_missions");
                sendSystemMessage(self, strSpam);
                return SCRIPT_CONTINUE;
            }
        }
        obj_id[] objMissionArray = getMissionObjects(self);
        if (objMissionArray != null)
        {
            if (objMissionArray.length >= MAX_MISSIONS)
            {
                string_id strSpam = new string_id("mission/mission_generic", "too_many_missions");
                sendSystemMessage(self, strSpam);
                return SCRIPT_CONTINUE;
            }
        }
        if (objMessageSource != null)
        {
            debugServerConsoleMsg(self, "objMissionData is " + objMissionData.toString());
            if (objMissionData != null)
            {
                int intBond = 0;
                if (intBond > 0)
                {
                    debugServerConsoleMsg(self, "Mission Has Bond");
                    int intCurrentBalance = getBankBalance(self);
                    if (intCurrentBalance <= intBond)
                    {
                        debugServerConsoleMsg(self, "You need more money in your bank to take this mission");
                        return SCRIPT_CONTINUE;
                    }
                    obj_id objMission = objMissionData;
                    dictionary dctParams = new dictionary();
                    dctParams.put("missionObject", objMission);
                    dctParams.put("objMessageSource", objMessageSource);
                    transferBankCreditsTo(self, objMissionData, intBond, "bondTransferSuccess", "bondTransferFail", dctParams);
                    debugServerConsoleMsg(self, "Mission Object Id is " + objMission.toString());
                }
                else 
                {
                    obj_id objMission = objMissionData;
                    debugServerConsoleMsg(objMission, "Mission Object Succesfully Created without a BOND");
                    if (objMission == null)
                    {
                        debugServerConsoleMsg(self, "SEE? ITS NULL!!!");
                        return SCRIPT_CONTINUE;
                    }
                    debugServerConsoleMsg(objMission, "Mission Object is " + objMission.toString());
                    if (strMissionType.equals("bounty"))
                    {
                        if (hasObjVar(objMessageSource, "objTarget"))
                        {
                            obj_id target = getObjIdObjVar(objMessageSource, "objTarget");
                            LOG("LOG_CHANNEL", "target ->" + target + " self ->" + self);
                            if (!isIdValid(target))
                            {
                                LOG("LOG_CHANNEL", "mission_player.bondTransferSuccess: Invalid target obj_id");
                                return SCRIPT_CONTINUE;
                            }
                            utils.setScriptVar(self, "bounty_hunter.jedi_mission", objMessageSource);
                            requestJediBounty(target, self, "msgJediMissionStartConfirmed", "msgJediMissionStartFailed");
                            debugServerConsoleMsg(self, "Waiting for requestJediBountyResult for hunter " + self);
                            return SCRIPT_CONTINUE;
                        }
                    }
                    dictionary dctParams = new dictionary();
                    dctParams.put("missionObject", objMission);
                    startMission(objMission);
                    debugServerConsoleMsg(self, "Mission Object Id is " + objMission.toString());
                }
            }
        }
        debugServerConsoleMsg(self, "done with onAssignMission Trigger");
        return SCRIPT_CONTINUE;
    }
    public int bondTransferSuccess(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id objMessageSource = params.getObjId("objMessageSource");
        debugServerConsoleMsg(self, "Bond Succesfully Transferred to Mission Data Object");
        startMission(objMessageSource);
        return SCRIPT_CONTINUE;
    }
    public int bondTransferFailure(obj_id self, dictionary params) throws InterruptedException
    {
        debugServerConsoleMsg(self, "You need more bond money to take this mission");
        return SCRIPT_CONTINUE;
    }
    public int msgJediMissionStartConfirmed(obj_id self, dictionary params) throws InterruptedException
    {
        debugServerConsoleMsg(self, "requestJediBountyResult for hunter " + self + " success");
        if (utils.hasScriptVar(self, "bounty_hunter.jedi_mission"))
        {
            obj_id jedi_mission = utils.getObjIdScriptVar(self, "bounty_hunter.jedi_mission");
            utils.removeScriptVar(self, "bounty_hunter.jedi_mission");
            if (isIdValid(jedi_mission))
            {
                obj_id bounty_mission = getBountyMission(self);
                if (isIdValid(bounty_mission))
                {
                    string_id message = new string_id("mission/mission_generic", "too_many_missions");
                    sendSystemMessage(self, message);
                }
                else 
                {
                    startMission(jedi_mission);
                }
                return SCRIPT_CONTINUE;
            }
        }
        string_id message = new string_id("mission/mission_generic", "jedi_mission_start_failed");
        sendSystemMessage(self, message);
        return SCRIPT_CONTINUE;
    }
    public int msgJediMissionStartFailed(obj_id self, dictionary params) throws InterruptedException
    {
        debugServerConsoleMsg(self, "requestJediBountyResult for hunter " + self + " fail");
        if (utils.hasScriptVar(self, "bounty_hunter.jedi_mission"))
        {
            utils.removeScriptVar(self, "bounty_hunter.jedi_mission");
        }
        string_id message = new string_id("mission/mission_generic", "jedi_mission_start_failed");
        sendSystemMessage(self, message);
        return SCRIPT_CONTINUE;
    }
    public int setupSpawn(obj_id self, dictionary params) throws InterruptedException
    {
        debugServerConsoleMsg(self, "setupSpawn message received on the player");
        location locSpawnLocation = params.getLocation("locSpawnLocation");
        obj_id objMission = params.getObjId("objMission");
        debugServerConsoleMsg(self, "Player receives spawn setup message for " + locSpawnLocation.toString());
        debugServerConsoleMsg(self, "distnace for trigger is " + MISSION_SPAWN_TRIGGER_RANGE);
        debugServerConsoleMsg(self, "objMission is " + objMission.toString());
        addLocationTarget(objMission.toString(), locSpawnLocation, MISSION_SPAWN_TRIGGER_RANGE);
        return SCRIPT_CONTINUE;
    }
    public int OnArrivedAtLocation(obj_id self, String strName) throws InterruptedException
    {
        debugServerConsoleMsg(self, "ARRIVED AT LOCATION " + strName);
        debugServerConsoleMsg(self, "Above Conversion to long");
        Long lngId;
        try
        {
            lngId = new Long(strName);
        }
        catch(NumberFormatException err)
        {
            debugServerConsoleMsg(self, "Long Conversion Failed, Continuing to next onArrivedAtLocation");
            return SCRIPT_CONTINUE;
        }
        debugServerConsoleMsg(self, "Above conversion to obj_id");
        obj_id objMission = obj_id.getObjId(lngId.longValue());
        debugServerConsoleMsg(self, "Above Mission Array thing");
        removeLocationTarget(strName);
        obj_id[] objMissionArray = getMissionObjects(self);
        if (objMissionArray == null)
        {
            debugServerConsoleMsg(self, "NULL OBJIDARRAY! BAD");
        }
        debugServerConsoleMsg(self, "got Mission Array");
        if (utils.isObjIdInArray(objMissionArray, objMission))
        {
            debugServerConsoleMsg(self, "sending message to " + objMission);
            missionArrival(objMission);
        }
        return SCRIPT_CONTINUE;
    }
    public int cleanupLocationTarget(obj_id self, dictionary params) throws InterruptedException
    {
        debugServerConsoleMsg(self, "cleaning up location targets on player, message received");
        String strLocationTarget = (String)params.get("strLocationTarget");
        debugServerConsoleMsg(self, "strLocationTarget is " + strLocationTarget);
        removeLocationTarget(strLocationTarget);
        debugServerConsoleMsg(self, "locationTargets was removed");
        return SCRIPT_CONTINUE;
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        return SCRIPT_CONTINUE;
    }
    public int setupArrivedAtLocation(obj_id self, dictionary params) throws InterruptedException
    {
        float fltDistance = params.getFloat("fltDistance");
        obj_id objMission = params.getObjId("objMission");
        location locTargetLocation = params.getLocation("locTargetLocation");
        addLocationTarget(objMission.toString(), locTargetLocation, fltDistance);
        return SCRIPT_CONTINUE;
    }
    public int bountyIncomplete(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id target = params.getObjId("target");
        if (isIdValid(target))
        {
            obj_id bountyMission = getBountyMission(self);
            if (isIdValid(bountyMission))
            {
                obj_id missionTarget = getObjIdObjVar(bountyMission, "objTarget");
                if (missionTarget == target)
                {
                    messageTo(bountyMission, "bountyIncomplete", params, 0, true);
                }
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int bountyFailure(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id target = params.getObjId("target");
        if (isIdValid(target))
        {
            obj_id bountyMission = getBountyMission(self);
            if (isIdValid(bountyMission))
            {
                obj_id missionTarget = getObjIdObjVar(bountyMission, "objTarget");
                if (missionTarget == target)
                {
                    messageTo(bountyMission, "bountyFailure", params, 0, true);
                }
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int bountySuccess(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id target = params.getObjId("target");
        if (isIdValid(target))
        {
            obj_id bountyMission = getBountyMission(self);
            if (isIdValid(bountyMission))
            {
                obj_id missionTarget = getObjIdObjVar(bountyMission, "objTarget");
                if (missionTarget == target)
                {
                    messageTo(bountyMission, "bountySuccess", params, 0, true);
                }
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int destruction_Arrival(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id objMission = params.getObjId("objMission");
        if (!createDestructionTarget(objMission, self))
        {
            sendDestructionIncomplete(objMission);
        }
        return SCRIPT_CONTINUE;
    }
    public int assassin_Arrival(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id objMission = params.getObjId("objMission");
        if (!createAssassinTarget(objMission))
        {
            sendAssassinIncomplete(objMission);
        }
        return SCRIPT_CONTINUE;
    }
    public int recon_Arrival(obj_id self, dictionary params) throws InterruptedException
    {
        debugServerConsoleMsg(self, "got recon arrival message");
        obj_id objMission = params.getObjId("objMission");
        if (!createReconTarget(objMission, self))
        {
            sendReconIncomplete(objMission);
        }
        return SCRIPT_CONTINUE;
    }
    public int deliver_Arrival(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id objMission = params.getObjId("objMission");
        int intState = getIntObjVar(objMission, "intState");
        debugServerConsoleMsg(self, "Arrived Location state is " + intState);
        if (intState == missions.STATE_DYNAMIC_DROPOFF)
        {
            if (!createDeliverDropoffNPC(objMission))
            {
                sendDeliverIncomplete(objMission);
                return SCRIPT_CONTINUE;
            }
        }
        else if (intState == missions.STATE_DYNAMIC_PICKUP)
        {
            if (!createDeliverPickupNPC(objMission))
            {
                sendDeliverIncomplete(objMission);
                return SCRIPT_CONTINUE;
            }
        }
        else 
        {
            debugServerConsoleMsg(self, "Something so horribly fucked happened here. I got a state thats not defined anywhere in a t mission");
        }
        return SCRIPT_CONTINUE;
    }
    public int crafting_Arrival(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id objMission = params.getObjId("objMission");
        int intState = getIntObjVar(objMission, "intState");
        debugServerConsoleMsg(self, "Arrived Location state is " + intState);
        if (intState == missions.STATE_DYNAMIC_DROPOFF)
        {
            if (!createCraftingDropoffNPC(objMission))
            {
                sendCraftingIncomplete(objMission);
                return SCRIPT_CONTINUE;
            }
        }
        else if (intState == missions.STATE_DYNAMIC_PICKUP)
        {
            if (!createCraftingPickupNPC(objMission))
            {
                sendCraftingIncomplete(objMission);
                return SCRIPT_CONTINUE;
            }
        }
        else 
        {
            debugServerConsoleMsg(self, "Something so horribly fucked happened here. I got a state thats not defined anywhere in a t mission");
        }
        return SCRIPT_CONTINUE;
    }
    public int fetch_Arrival(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id objMission = params.getObjId("objMission");
        obj_id objMissionData = getMissionData(objMission);
        int intState = getIntObjVar(objMission, "intState");
        debugServerConsoleMsg(self, "Arrived Location state is " + intState);
        if (intState == missions.STATE_DYNAMIC_START)
        {
            if (!createFetchDropoffNPC(objMission))
            {
                fetchIncomplete(objMission);
                return SCRIPT_CONTINUE;
            }
        }
        if (intState == missions.STATE_DYNAMIC_PICKUP)
        {
            if (!createFetchPickupNPC(objMission))
            {
                fetchIncomplete(objMission);
                return SCRIPT_CONTINUE;
            }
        }
        else if (intState == missions.STATE_DYNAMIC_DROPOFF)
        {
            if (!createFetchDropoffNPC(objMission))
            {
                fetchIncomplete(objMission);
                return SCRIPT_CONTINUE;
            }
        }
        else 
        {
            debugServerConsoleMsg(self, "Something so horribly fucked happened here. I got a state thats not defined anywhere in a fetch mission");
        }
        return SCRIPT_CONTINUE;
    }
    public int escort_Arrival(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id objMission = params.getObjId("objMission");
        if (!createEscortTarget(objMission))
        {
            escortIncomplete(objMission);
        }
        return SCRIPT_CONTINUE;
    }
    public int bounty_Arrival(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id objMission = params.getObjId("objMission");
        obj_id target = createBountyTarget(objMission);
        if (!isIdValid(target))
        {
            if (hasObjVar(objMission, "targetInWater"))
            {
                int numberOfFails = getIntObjVar(objMission, "targetInWater");
                ++numberOfFails;
                setObjVar(objMission, "targetInWater", numberOfFails);
                if (numberOfFails > 5)
                {
                    setObjVar(self, "intState", missions.STATE_MISSION_COMPLETE);
                    messageTo(objMission, "bountyIncomplete", null, 0, true);
                    return SCRIPT_CONTINUE;
                }
            }
            utils.removeScriptVar(objMission, "intTracking");
            String planet = getCurrentSceneName();
            location locSpawnLocation = getGoodBountyDestination(planet);
            setObjVar(objMission, "locSpawnLocation", locSpawnLocation);
            utils.removeScriptVar(self, "intTracking");
            messageTo(objMission, "targetInWater", null, 1, false);
            sendSystemMessage(self, SID_MISSION_TARGET_IN_WATER);
            if (!hasObjVar(objMission, "targetInWater"))
            {
                setObjVar(objMission, "targetInWater", 1);
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int setup_Spawn_Overload(obj_id self, dictionary params) throws InterruptedException
    {
        String strOverloadTemplate = params.getString("strOverloadTemplate");
        String[] strSpawnOverloadArray = getStringArrayObjVar(self, "strSpawnOverloadArray");
        String strTest = getStringObjVar(self, "strSpawnOverloadArray");
        debugServerConsoleMsg(self, "strTest is " + strTest);
        if (strSpawnOverloadArray == null)
        {
            if (hasObjVar(self, "strSpawnOverloadArray"))
            {
                debugServerConsoleMsg(self, "Not getting the array even though its tehre!");
            }
            debugServerConsoleMsg(self, "SpawnOverloadArray was null, not appending");
            String[] strNewArray = new String[1];
            strNewArray[0] = strOverloadTemplate;
            setObjVar(self, "strSpawnOverloadArray", strNewArray);
            return SCRIPT_CONTINUE;
        }
        String[] strNewSpawnOverloadArray = new String[strSpawnOverloadArray.length + 1];
        int intI = 0;
        while (intI < strSpawnOverloadArray.length)
        {
            strNewSpawnOverloadArray[intI] = strSpawnOverloadArray[intI];
            intI = intI + 1;
        }
        strNewSpawnOverloadArray[strNewSpawnOverloadArray.length - 1] = strOverloadTemplate;
        setObjVar(self, "strSpawnOverloadArray", strNewSpawnOverloadArray);
        return SCRIPT_CONTINUE;
    }
    public int informantComm(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id player = self;
        if (!hasSkill(player, "class_bountyhunter_phase1_novice"))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id objMission = getBountyMission(self);
        if (objMission == null)
        {
            return SCRIPT_CONTINUE;
        }
        obj_id objMissionHolder = getMissionHolder(objMission);
        obj_id objMissionData = getMissionData(objMission);
        int intInformantLevel = getIntObjVar(objMissionData, "intInformantLevel");
        dictionary dctParams = new dictionary();
        int intState = getIntObjVar(objMission, "intState");
        if (intState != STATE_BOUNTY_INFORMANT)
        {
            return SCRIPT_CONTINUE;
        }
        if (!hasObjVar(objMissionData, "intPlayerMission"))
        {
            int intIndex = rand(1, 5);
            int randAppearance = rand(0, 3);
            if (intInformantLevel == INFORMANT_EASY)
            {
                string_id strResponse = new string_id("mission/mission_bounty_informant", "target_easy_" + intIndex);
                prose_package pp = prose.getPackage(strResponse);
                commPlayers(self, OPS_APPEARANCES[randAppearance], null, 15, player, pp);
                updateMissionWaypoint(objMission, getLocationObjVar(objMission, "locSpawnLocation"));
                strResponse = new string_id("mission/mission_bounty_informant", "target_location_received");
                sendSystemMessage(player, strResponse);
                setObjVar(objMission, "intState", STATE_BOUNTY_PROBE);
            }
            else if (intInformantLevel == INFORMANT_MEDIUM)
            {
                string_id strResponse = new string_id("mission/mission_bounty_informant", "target_medium_" + intIndex);
                prose_package pp = prose.getPackage(strResponse);
                commPlayers(self, OPS_APPEARANCES[randAppearance], null, 15, player, pp);
                location locSpawnLocation = utils.getLocationObjVar(objMissionData, "locSpawnLocation");
                String strPlanet = locSpawnLocation.area;
                String strPlanetResponse = "target_hard_" + strPlanet + "_1";
                strResponse = new string_id("mission/mission_bounty_informant", strPlanetResponse);
                pp = prose.getPackage(strResponse);
                commPlayers(self, OPS_APPEARANCES[randAppearance], null, 15, player, pp);
                strResponse = new string_id("mission/mission_bounty_informant", "target_biological_signature");
                sendSystemMessage(player, strResponse);
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
                prose_package pp = prose.getPackage(strResponse);
                commPlayers(self, OPS_APPEARANCES[randAppearance], null, 15, player, pp);
                strResponse = new string_id("mission/mission_bounty_informant", "target_biological_signature");
                sendSystemMessage(player, strResponse);
                setObjVar(objMission, "intState", STATE_BOUNTY_PROBE);
                if (hasObjVar(objMission, "intMissionDynamic"))
                {
                    setObjVar(objMission, "movingTarget", 1);
                    dctParams.put("moveTargetSequence", utils.getIntScriptVar(self, "moveTargetSequence"));
                    messageTo(objMission, "moveTarget", dctParams, rand(30, 60), false);
                }
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int addListener(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id objListener = params.getObjId("objListener");
        if (objListener == null)
        {
            LOG("DESIGNER_FATAL", "Null listener was passed to " + self);
            return SCRIPT_CONTINUE;
        }
        String strObjVar = params.getString("strObjVar");
        if (strObjVar == null)
        {
            LOG("DESIGNER_FATAL", "Null objvar name was passed to " + self);
        }
        Vector objListeners = new Vector();
        objListeners.setSize(0);
        if (hasObjVar(self, strObjVar))
        {
            objListeners = getResizeableObjIdArrayObjVar(self, strObjVar);
        }
        for (int intI = 0; intI < objListeners.size(); intI++)
        {
            if (((obj_id)objListeners.get(intI)) == objListener)
            {
                LOG("DESIGNER_FATAL", "objListener of " + objListener + " is alread listening to " + self);
                return SCRIPT_CONTINUE;
            }
        }
        objListeners = utils.addElement(objListeners, objListener);
        if ((objListeners != null) && (objListeners.size() > 0))
        {
            setObjVar(self, strObjVar, objListeners);
        }
        return SCRIPT_CONTINUE;
    }
    public int removeListener(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id objListener = params.getObjId("objListener");
        if (objListener == null)
        {
            LOG("DESIGNER_FATAL", "Null listener was passed to " + self);
            return SCRIPT_CONTINUE;
        }
        String strObjVar = params.getString("strObjVar");
        if (strObjVar == null)
        {
            LOG("DESIGNER_FATAL", "Null objvar name was passed to " + self);
        }
        Vector objListeners = new Vector();
        objListeners.setSize(0);
        if (hasObjVar(self, strObjVar))
        {
            objListeners = getResizeableObjIdArrayObjVar(self, strObjVar);
        }
        else 
        {
            return SCRIPT_CONTINUE;
        }
        for (int intI = 0; intI < objListeners.size(); intI++)
        {
            if (((obj_id)objListeners.get(intI)) == objListener)
            {
                objListeners = utils.removeElement(objListeners, objListener);
                setObjVar(self, strObjVar, objListeners);
                return SCRIPT_CONTINUE;
            }
        }
        LOG("DESIGNER_FATAL", "Someone called removeListener for an object that doesn't exist on the listener objvar");
        LOG("DESIGNER_FATAL", "target is " + self + " and listener is " + objListener);
        return SCRIPT_CONTINUE;
    }
    public int findAssassinTarget(obj_id self, obj_id target, String params, float defaultTime) throws InterruptedException
    {
        obj_id objTarget = target;
        if (!isIdValid(target))
        {
            return SCRIPT_CONTINUE;
        }
        if (!exists(objTarget))
        {
            string_id strSpam = new string_id("mission/mission_generic", "no_assassin_target");
            sendSystemMessage(self, strSpam);
            return SCRIPT_CONTINUE;
        }
        location locTarget = getLocation(objTarget);
        location locCurrentLocation = getLocation(self);
        float fltDistance = getDistance(locTarget, locCurrentLocation);
        if (fltDistance < 32)
        {
            pvpSetPermanentPersonalEnemyFlag(objTarget, self);
        }
        string_id strDirection = utils.getCardinalDirectionForPoints(locCurrentLocation, locTarget);
        int intDistance = (int)fltDistance;
        prose_package ppTargetLoc = prose.getPackage(new string_id("mission/mission_generic", "assassin_target_location"), strDirection, intDistance);
        sendSystemMessageProse(self, ppTargetLoc);
        return SCRIPT_CONTINUE;
    }
}
