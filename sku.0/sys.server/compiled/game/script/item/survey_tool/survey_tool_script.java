package script.item.survey_tool;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.ai_lib;
import script.library.utils;
import script.library.resource;
import script.library.prose;
import script.library.structure;
import script.library.sui;
import script.library.xp;

public class survey_tool_script extends script.base_script
{
    public survey_tool_script()
    {
    }
    public static final int MIN_SURVEY_POINTS = 3;
    public static final int MAX_SURVEY_POINTS = 9;
    public static final int MIN_SURVEY_RANGE = 32;
    public static final int MAX_SURVEY_RANGE = 384;
    public static final int SURVEY_TOOL_DELAY = 25;
    public static final int MIN_SURVEY_TOOL_DELAY = 10;
    public static final int SURVEY_MIND_COST = 0;
    public static final int SAMPLE_MIND_COST = 0;
    public static final int SAMPLE_ACTION_COST = 105;
    public static final int MIN_SURVEY_MISSION_DISTANCE = 1024;
    public static final string_id SID_TOOL_OPTIONS = new string_id("sui", "tool_options");
    public static final string_id SID_TOOL_RANGE = new string_id("sui", "survey_range");
    public static final string_id SID_TOOL_RESOLUTION = new string_id("sui", "survey_resolution");
    public static final string_id SID_NO_SURVEY_INSTANCE = new string_id("error_message", "no_survey_instance");
    public static final string_id SID_SURVEY_IN_STRUCTURE = new string_id("error_message", "survey_in_structure");
    public static final string_id SID_SURVEY_ON_TERRAIN_ONLY = new string_id("error_message", "survey_on_terrain_only");
    public static final string_id SID_SURVEY_ON_MOUNT = new string_id("error_message", "survey_on_mount");
    public static final string_id SID_SURVEY_SWIMMING = new string_id("error_message", "survey_swimming");
    public static final string_id SID_SURVEY_SITTING = new string_id("error_message", "survey_sitting");
    public static final string_id SID_SURVEY_CANT = new string_id("error_message", "survey_cant");
    public static final string_id SID_SURVEY_MIND = new string_id("error_message", "survey_mind");
    public static final string_id SID_SAMPLE_MIND = new string_id("error_message", "sample_mind");
    public static final string_id SID_START_SAMPLING = new string_id("survey", "start_sampling");
    public static final string_id SID_START_SURVEY = new string_id("survey", "start_survey");
    public static final string_id SID_SURVEY_SAMPLE = new string_id("survey", "survey_sample");
    public static final string_id SID_SAMPLE_SELECT_TYPE = new string_id("survey", "sample_select_type");
    public static final string_id SID_SAMPLE_SURVEY = new string_id("survey", "sample_survey");
    public static final string_id SID_SAMPLE_RECHARGE = new string_id("survey", "tool_recharge_time");
    public static final string_id SID_ALREADY_SAMPLING = new string_id("survey", "already_sampling");
    public static final string_id SID_SAMPLE_CANCEL = new string_id("survey", "sample_cancel");
    public static final string_id SID_SAMPLE_GONE = new string_id("survey", "sample_gone");
    public static final string_id SID_NO_SURVEY_IN_SPACE = new string_id("space/space_interaction", "no_survey_in_space");
    public static final string_id SID_NO_SURVEY_WATER = new string_id("survey", "no_survey_in_water");
    public static final String STF = "survey";
    public static dictionary dctParams = new dictionary();
    public int OnAttach(obj_id self) throws InterruptedException
    {
        setObjVar(self, resource.VAR_SETTINGS_INDEX, 0);
        setObjVar(self, resource.VAR_SURVEY_RANGE_VALUE, 32);
        setObjVar(self, resource.VAR_SURVEY_RESOLUTION_VALUE, 3);
        return SCRIPT_CONTINUE;
    }
    public int OnDestroy(obj_id self) throws InterruptedException
    {
        if (utils.hasScriptVar(self, "surveying.player"))
        {
            obj_id player = utils.getObjIdScriptVar(self, "surveying.player");
            utils.removeScriptVar(player, "surveying.outstandingHarvestMessage");
            resource.cleanupTool(player, self);
            sendSystemMessage(player, SID_SAMPLE_GONE);
        }
        return SCRIPT_CONTINUE;
    }
    public int OnAboutToBeTransferred(obj_id self, obj_id destContainer, obj_id transferer) throws InterruptedException
    {
        if (utils.hasScriptVar(self, "surveying.takingSamples"))
        {
            return SCRIPT_OVERRIDE;
        }
        if (utils.hasScriptVar(self, "surveying.surveying"))
        {
            return SCRIPT_OVERRIDE;
        }
        return SCRIPT_CONTINUE;
    }
    public int OnObjectMenuRequest(obj_id self, obj_id player, menu_info mi) throws InterruptedException
    {
        String resource_class = getStringObjVar(self, resource.VAR_SURVEY_CLASS);
        if (resource_class.equals(""))
        {
            sendSystemMessageTestingOnly(player, "ERROR: survey tool broken -> no assigned resource class");
            return SCRIPT_CONTINUE;
        }
        String baseMod = resource.getSkillModForClass(resource_class);
        if (baseMod.equals(""))
        {
            sendSystemMessageTestingOnly(player, "ERROR: survey tool broken -> unknown resource class");
            return SCRIPT_CONTINUE;
        }
        menu_info_data mid = mi.getMenuItemByType(menu_info_types.EXAMINE);
        if (mid != null)
        {
            mid.setServerNotify(true);
        }
        if (!utils.isNestedWithin(self, player))
        {
            return SCRIPT_CONTINUE;
        }
        mid = mi.getMenuItemByType(menu_info_types.ITEM_USE);
        if (mid != null)
        {
            mid.setServerNotify(true);
        }
        String modToUse = "surveying";
        int modVal = getSkillStatMod(player, modToUse) / 20;
        debugServerConsoleMsg(player, modToUse + " = " + modVal);
        if (modVal > 0)
        {
            int mnuOptions = -1;
            mid = mi.getMenuItemByType(menu_info_types.SERVER_ITEM_OPTIONS);
            if (mid == null)
            {
                mnuOptions = mi.addRootMenu(menu_info_types.SERVER_ITEM_OPTIONS, SID_TOOL_OPTIONS);
            }
            else 
            {
                mnuOptions = mid.getId();
            }
            if (mnuOptions > 0)
            {
                int subRange = mi.addSubMenu(mnuOptions, menu_info_types.SERVER_SURVEY_TOOL_RANGE, SID_TOOL_RANGE);
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int OnObjectMenuSelect(obj_id self, obj_id player, int item) throws InterruptedException
    {
        if (isSpaceScene())
        {
            sendSystemMessage(player, SID_NO_SURVEY_IN_SPACE);
            return SCRIPT_CONTINUE;
        }
        String resource_class = getStringObjVar(self, resource.VAR_SURVEY_CLASS);
        if (resource_class == null || resource_class.equals(""))
        {
            sendSystemMessageTestingOnly(player, "ERROR: survey tool broken -> no assigned resource class");
            return SCRIPT_CONTINUE;
        }
        if (item == menu_info_types.EXAMINE)
        {
            resource.showToolProperties(player, self);
            return SCRIPT_CONTINUE;
        }
        if (!utils.isNestedWithin(self, player))
        {
            return SCRIPT_CONTINUE;
        }
        String baseMod = resource.getSkillModForClass(resource_class);
        if (baseMod == null || baseMod.equals(""))
        {
            sendSystemMessageTestingOnly(player, "ERROR: survey tool broken -> unknown resource class");
            return SCRIPT_CONTINUE;
        }
        String modToUse = "surveying";
        int modVal = getSkillStatMod(player, modToUse) / 20;
        if (item == menu_info_types.ITEM_USE)
        {
            location here = getLocation(player);
            if (isWaterInRect(here.x - 1, here.z - 1, here.x + 1, here.z + 1))
            {
                sendSystemMessage(player, SID_NO_SURVEY_WATER);
                return SCRIPT_OVERRIDE;
            }
            if (here.area.equals("dungeon1") || here.area.equals("adventure1"))
            {
                sendSystemMessage(player, SID_NO_SURVEY_INSTANCE);
                return SCRIPT_OVERRIDE;
            }
            obj_id rangeSetter = getObjIdObjVar(self, resource.VAR_SETTINGS_PLAYER);
            if (isIdValid(rangeSetter) && player == rangeSetter)
            {
                int idx = getIntObjVar(self, resource.VAR_SETTINGS_INDEX);
                if (idx <= modVal)
                {
                    obj_id containPlayer = getTopMostContainer(player);
                    if (containPlayer != player)
                    {
                        obj_id playerCurrentMount = getMountId(player);
                        if (!isIdValid(playerCurrentMount))
                        {
                            sendSystemMessage(player, SID_SURVEY_IN_STRUCTURE);
                            return SCRIPT_OVERRIDE;
                        }
                        if (containPlayer != playerCurrentMount)
                        {
                            sendSystemMessage(player, SID_SURVEY_IN_STRUCTURE);
                            return SCRIPT_OVERRIDE;
                        }
                    }
                    requestResourceListForSurvey(player, self, resource_class);
                    return SCRIPT_CONTINUE;
                }
            }
            setObjVar(self, resource.VAR_SETTINGS_INDEX, 0);
            setObjVar(self, resource.VAR_SURVEY_RANGE_VALUE, 32);
            setObjVar(self, resource.VAR_SURVEY_RESOLUTION_VALUE, 3);
            resource.requestSetToolRatio(player, self);
        }
        else if (item == menu_info_types.SERVER_SURVEY_TOOL_RANGE || item == menu_info_types.SERVER_ITEM_OPTIONS)
        {
            resource.requestSetToolRatio(player, self);
        }
        return SCRIPT_CONTINUE;
    }
    public int OnRequestSurvey(obj_id self, obj_id player, String resource_type) throws InterruptedException
    {
        obj_id containPlayer = getTopMostContainer(player);
        if (containPlayer != player)
        {
            obj_id playerCurrentMount = getMountId(player);
            if (!isIdValid(playerCurrentMount))
            {
                sendSystemMessage(player, SID_SURVEY_IN_STRUCTURE);
                return SCRIPT_OVERRIDE;
            }
            if (containPlayer != playerCurrentMount)
            {
                sendSystemMessage(player, SID_SURVEY_IN_STRUCTURE);
                return SCRIPT_OVERRIDE;
            }
        }
        if (1 == getState(player, STATE_SWIMMING))
        {
            sendSystemMessage(player, SID_SURVEY_SWIMMING);
            return SCRIPT_CONTINUE;
        }
        if (POSTURE_SITTING == getPosture(player))
        {
            sendSystemMessage(player, SID_SURVEY_SITTING);
            return SCRIPT_CONTINUE;
        }
        if (POSTURE_SKILL_ANIMATING == getPosture(player))
        {
            return SCRIPT_CONTINUE;
        }
        if (ai_lib.isInCombat(player))
        {
            sendSystemMessage(player, SID_SURVEY_CANT);
            return SCRIPT_CONTINUE;
        }
        obj_id whatAmIStandingOn = getStandingOn(player);
        if (isIdValid(whatAmIStandingOn))
        {
            sendSystemMessage(player, SID_SURVEY_ON_TERRAIN_ONLY);
            return SCRIPT_OVERRIDE;
        }
        boolean surveying = utils.hasScriptVar(player, "surveying.surveying") || utils.hasScriptVar(self, "surveying.surveying");
        if (surveying)
        {
            return SCRIPT_CONTINUE;
        }
        boolean loopRunning = utils.hasScriptVar(player, "surveying.takingSamples") || utils.hasScriptVar(self, "surveying.takingSamples");
        if (loopRunning)
        {
            sendSystemMessage(player, SID_SURVEY_SAMPLE);
            return SCRIPT_CONTINUE;
        }
        if (resource_type.endsWith(","))
        {
            resource_type = resource_type.substring(0, resource_type.length() - 1);
        }
        if (!utils.isNestedWithin(self, player))
        {
            return SCRIPT_CONTINUE;
        }
        String resource_class = getStringObjVar(self, resource.VAR_SURVEY_CLASS);
        int surveyRange = getIntObjVar(self, resource.VAR_SURVEY_RANGE_VALUE);
        int points = getIntObjVar(self, resource.VAR_SURVEY_RESOLUTION_VALUE);
        if (surveyRange > MAX_SURVEY_RANGE)
        {
            surveyRange = MAX_SURVEY_RANGE;
        }
        else if (surveyRange < MIN_SURVEY_RANGE)
        {
            surveyRange = MIN_SURVEY_RANGE;
        }
        if (points > MAX_SURVEY_POINTS)
        {
            points = MAX_SURVEY_POINTS;
        }
        else if (points < MIN_SURVEY_POINTS)
        {
            points = MIN_SURVEY_POINTS;
        }
        utils.setScriptVar(player, "surveying.surveying", 1);
        utils.setScriptVar(self, "surveying.surveying", 1);
        utils.setScriptVar(self, "surveying.player", player);
        String resType = getStringObjVar(self, "survey.resource_class");
        if (resType.equals("gas"))
        {
            playClientEffectLoc(player, "clienteffect/survey_tool_gas.cef", getLocation(player), 0f);
        }
        else if (resType.equals("chemical"))
        {
            playClientEffectLoc(player, "clienteffect/survey_tool_moisture.cef", getLocation(player), 0f);
        }
        else if (resType.equals("flora_resources"))
        {
            playClientEffectLoc(player, "clienteffect/survey_tool_lumber.cef", getLocation(player), 0f);
        }
        else if (resType.equals("mineral"))
        {
            playClientEffectLoc(player, "clienteffect/survey_tool_mineral.cef", getLocation(player), 0f);
        }
        else if (resType.equals("water"))
        {
            playClientEffectLoc(player, "clienteffect/survey_tool_liquid.cef", getLocation(player), 0f);
        }
        else if (resType.equals("energy_renewable_unlimited_wind"))
        {
            playClientEffectLoc(player, "clienteffect/survey_tool_gas.cef", getLocation(player), 0f);
        }
        else if (resType.equals("energy_renewable_unlimited_solar"))
        {
            playClientEffectLoc(player, "clienteffect/survey_tool_moisture.cef", getLocation(player), 0f);
        }
        else if (resType.equals("energy_renewable_site_limited_geothermal"))
        {
            playClientEffectLoc(player, "clienteffect/survey_tool_liquid.cef", getLocation(player), 0f);
        }
        prose_package pp = prose.getPackage(SID_START_SURVEY, resource_type);
        sendSystemMessageProse(player, pp);
        dictionary outparams = new dictionary();
        outparams.put("player", player);
        outparams.put("resource_class", resource_class);
        outparams.put("resource_type", resource_type);
        outparams.put("surveyRange", surveyRange);
        outparams.put("points", points);
        messageTo(self, "finalizeSurvey", outparams, 4.f, false);
        xp.grantCraftingXpChance(self, player, 30);
        return SCRIPT_CONTINUE;
    }
    public int finalizeSurvey(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id player = params.getObjId("player");
        String resource_class = params.getString("resource_class");
        String resource_type = params.getString("resource_type");
        int surveyRange = params.getInt("surveyRange");
        int points = params.getInt("points");
        if (requestSurvey(player, resource_class, resource_type, surveyRange, points))
        {
            if (hasObjVar(player, "newbie_handoff.mission.survey"))
            {
                checkNewbieHandoffSurveyMission(player, resource_type);
            }
            if (hasScript(player, "theme_park.new_player.new_player"))
            {
                dictionary webster = new dictionary();
                webster.put("resource_class", resource_class);
                messageTo(player, "handleNewPlayerArtisanAction", webster, 1, false);
            }
            obj_id resource = getResourceTypeByName(resource_type);
            if (resource != null)
            {
                float efficiency = getResourceEfficiency(resource, getLocation(player));
                dictionary surveyCompleteMessageParameters = new dictionary();
                surveyCompleteMessageParameters.put("resource_type", resource_type);
                surveyCompleteMessageParameters.put("resource_class", resource_class);
                surveyCompleteMessageParameters.put("efficiency", efficiency);
                surveyCompleteMessageParameters.put("resource_type_object", resource);
                messageTo(player, "surveyComplete", surveyCompleteMessageParameters, 0.0f, false);
            }
            obj_id[] objMissionArray = getMissionObjects(player);
            if (objMissionArray != null)
            {
                for (int intI = 0; intI < objMissionArray.length; intI++)
                {
                    obj_id objMissionData = objMissionArray[intI];
                    String strMissionType = getMissionType(objMissionData);
                    location locSurveyLocation = getLocation(player);
                    if (strMissionType.equals("survey"))
                    {
                        obj_id objResource = getResourceTypeByName(resource_type);
                        String strResource = getStringObjVar(objMissionData, "strResource");
                        if (objResource != null)
                        {
                            if (isResourceDerivedFrom(objResource, strResource) && isResourceDerivedFrom(objResource, resource_class))
                            {
                                int intEffeciency = getIntObjVar(objMissionData, "intEffeciency");
                                float fltEffeciency = getResourceEfficiency(objResource, locSurveyLocation);
                                fltEffeciency *= 100.0f;
                                if (fltEffeciency >= (float)intEffeciency)
                                {
                                    float testDistance = 10000.0f;
                                    location locStartLocation = getLocationObjVar(objMissionData, "locStartLocation");
                                    if (locStartLocation == null)
                                    {
                                        CustomerServiceLog("survey_mission", "getLocationObjVar for mission data " + objMissionData + " returned null");
                                    }
                                    else 
                                    {
                                        String strStartPlanet = locStartLocation.area;
                                        String strCurrentPlanet = locSurveyLocation.area;
                                        if (strStartPlanet.equals(strCurrentPlanet))
                                        {
                                            float realDistance = getDistance(locStartLocation, locSurveyLocation);
                                            if (realDistance < 0)
                                            {
                                                CustomerServiceLog("survey_mission", "Could not get a valid distance between " + locStartLocation + " and " + locSurveyLocation);
                                            }
                                            else 
                                            {
                                                testDistance = realDistance;
                                            }
                                        }
                                    }
                                    if (testDistance > MIN_SURVEY_MISSION_DISTANCE)
                                    {
                                        messageTo(objMissionArray[intI], "surveySuccess", null, 0, false);
                                    }
                                    else 
                                    {
                                        prose_package pp = new prose_package();
                                        pp.stringId = new string_id("mission/mission_generic", "survey_too_close");
                                        pp.digitInteger = MIN_SURVEY_MISSION_DISTANCE;
                                        pp.digitFloat = testDistance;
                                        sendSystemMessageProse(player, pp);
                                    }
                                }
                            }
                            else 
                            {
                            }
                        }
                    }
                }
            }
            else 
            {
                LOG("survey_mission", "NULL MISSION ARRAY");
            }
        }
        else 
        {
            sendSystemMessage(player, resource.SID_SURVEY_ERROR);
        }
        resource.cleanupTool(player, self);
        return SCRIPT_CONTINUE;
    }
    public void checkNewbieHandoffSurveyMission(obj_id player, String resource_type) throws InterruptedException
    {
        location surveyLoc = getLocation(player);
        LOG("newbie_survey_mission", "Survey Location is " + surveyLoc);
        obj_id surveyResource = getResourceTypeByName(resource_type);
        obj_id missionResource = getObjIdObjVar(player, "newbie_handoff.mission.survey.resource");
        LOG("newbie_survey_mission", "Survey Resource is " + surveyResource);
        LOG("newbie_survey_mission", "Mission Resource is " + missionResource);
        if (surveyResource == missionResource)
        {
            float goalEffeciency = getFloatObjVar(player, "newbie_handoff.mission.survey.efficiency");
            float curEffeciency = getResourceEfficiency(surveyResource, surveyLoc);
            LOG("newbiesurvey_mission", "Current Efficiency is " + curEffeciency + " and my desired effeciency is " + goalEffeciency);
            if (curEffeciency >= goalEffeciency)
            {
                LOG("newbie_survey_mission", "SUCCESSFUL MISSION!");
                messageTo(player, "missionSurveyComplete", null, 0, true);
            }
            else 
            {
                LOG("newbie_survey_mission", "Not effecient enough ");
            }
        }
        else 
        {
            LOG("newbie_survey_mission", "Not surveying for correct resource");
        }
    }
    public int OnRequestCoreSample(obj_id self, obj_id player, String resource_type) throws InterruptedException
    {
        if (hasScript(player, "quest.force_sensitive.fs_survey_player"))
        {
            if (hasObjVar(player, "quest.survey.specialResource"))
            {
                int winner = rand(1, 3);
                if (winner == 3)
                {
                    String type = getStringObjVar(player, "quest.oldresource.type");
                    if (type != null && !type.equals(""))
                    {
                        String special = pickSpecialResourceToGive(player, resource_type);
                        obj_id backpack = utils.getInventoryContainer(player);
                        obj_id madeObject = createObject(special, backpack, null);
                        float efficiency = getFloatObjVar(player, "quest.survey.efficency");
                        setSpecialResourceObjVars(special, madeObject, efficiency);
                        removeObjVar(player, "quest.survey.specialResource");
                    }
                }
                else 
                {
                    removeObjVar(player, "quest.survey.specialResource");
                    sendSystemMessage(player, "", null);
                }
            }
        }
        if (hasObjVar(player, "surveying.sampleCrateGenFailed"))
        {
            removeObjVar(player, "surveying.sampleCrateGenFailed");
            return SCRIPT_CONTINUE;
        }
        if (hasObjVar(self, "energy_survey_tool"))
        {
            sendSystemMessage(player, getString(new string_id(STF, "must_have_harvester")), null);
            return SCRIPT_CONTINUE;
        }
        boolean surveying = utils.hasScriptVar(player, "surveying.surveying") || utils.hasScriptVar(self, "surveying.surveying");
        if (surveying)
        {
            sendSystemMessage(player, SID_SAMPLE_SURVEY);
            return SCRIPT_CONTINUE;
        }
        if (utils.hasScriptVar(player, "surveying.takingSamples") || utils.hasScriptVar(self, "surveying.takingSamples"))
        {
            sendSystemMessage(player, SID_ALREADY_SAMPLING);
            return SCRIPT_CONTINUE;
        }
        if (utils.hasScriptVar(player, "surveying.outstandingHarvestMessage"))
        {
            int curTime = getGameTime();
            int diffTime = utils.getIntScriptVar(player, "surveying.outstandingHarvestMessage") - curTime;
            if (diffTime >= 0)
            {
                prose_package pp = prose.getPackage(SID_SAMPLE_RECHARGE, diffTime);
                sendSystemMessageProse(player, pp);
                return SCRIPT_CONTINUE;
            }
            if (diffTime < 0 || diffTime >= 45)
            {
                utils.removeScriptVar(player, "surveying.outstandingHarvestMessage");
            }
        }
        obj_id playerCurrentMount = getMountId(player);
        if (!isIdValid(playerCurrentMount))
        {
            if (getTopMostContainer(player) != player)
            {
                sendSystemMessage(player, SID_SURVEY_IN_STRUCTURE);
                return SCRIPT_OVERRIDE;
            }
        }
        else if (playerCurrentMount != getTopMostContainer(player))
        {
            sendSystemMessage(player, SID_SURVEY_IN_STRUCTURE);
            return SCRIPT_OVERRIDE;
        }
        if (1 == getState(player, STATE_SWIMMING))
        {
            sendSystemMessage(player, SID_SURVEY_SWIMMING);
            return SCRIPT_CONTINUE;
        }
        if (POSTURE_SITTING == getPosture(player))
        {
            sendSystemMessage(player, SID_SURVEY_SITTING);
            return SCRIPT_CONTINUE;
        }
        if (POSTURE_SKILL_ANIMATING == getPosture(player))
        {
            return SCRIPT_CONTINUE;
        }
        if (ai_lib.isInCombat(player))
        {
            sendSystemMessage(player, SID_SURVEY_CANT);
            return SCRIPT_CONTINUE;
        }
        if (resource_type.endsWith(","))
        {
            resource_type = resource_type.substring(0, resource_type.length() - 1);
        }
        if (!utils.isNestedWithin(self, player))
        {
            return SCRIPT_CONTINUE;
        }
        String resource_class = getStringObjVar(self, resource.VAR_SURVEY_CLASS);
        obj_id typeId = getResourceTypeByName(resource_type);
        if ((typeId == null) || (typeId == obj_id.NULL_ID))
        {
            sendSystemMessage(player, resource.SID_SURVEY_ERROR);
            return SCRIPT_CONTINUE;
        }
        if (!isResourceDerivedFrom(typeId, resource_class))
        {
            sendSystemMessage(player, SID_SAMPLE_SELECT_TYPE);
            return SCRIPT_CONTINUE;
        }
        if (isResourceDerivedFrom(typeId, "radioactive"))
        {
            boolean isConfirmed = false;
            if (utils.hasScriptVar(player, "surveying.radioactive.id"))
            {
                obj_id rType = utils.getObjIdScriptVar(player, "surveying.radioactive.id");
                if (isIdValid(rType) && rType == typeId && !utils.hasScriptVar(player, "surveying.radioactive.pid"))
                {
                    isConfirmed = true;
                }
            }
            if (!isConfirmed)
            {
                if (utils.hasScriptVar(player, "surveying.radioactive.pid"))
                {
                    int oldpid = utils.getIntScriptVar(player, "surveying.radioactive.pid");
                    if (oldpid > -1)
                    {
                        sui.closeSUI(player, oldpid);
                    }
                    utils.removeScriptVar(player, "surveying.radioactive.pid");
                }
                String ctitle = getString(new string_id(STF, "radioactive_sample_t"));
                String cprompt = getString(new string_id(STF, "radioactive_sample_d"));
                int pid = sui.msgbox(self, player, cprompt, sui.YES_NO, ctitle, "handleRadioactiveConfirm");
                if (pid > -1)
                {
                    utils.setScriptVar(player, "surveying.radioactive.pid", pid);
                    utils.setScriptVar(player, "surveying.radioactive.id", typeId);
                    utils.setScriptVar(player, "surveying.radioactive.name", resource_type);
                }
                return SCRIPT_CONTINUE;
            }
        }
        else 
        {
            if (utils.hasScriptVar(player, "surveying.radioactive.pid"))
            {
                int oldpid = utils.getIntScriptVar(player, "surveying.radioactive.pid");
                if (oldpid > -1)
                {
                    sui.closeSUI(player, oldpid);
                }
                utils.removeScriptVar(player, "surveying.radioactive.pid");
            }
            if (utils.hasScriptVar(player, "surveying.radioactive.id"))
            {
                utils.removeScriptVarTree(player, "surveying.radioactive");
            }
        }
        utils.setScriptVar(self, "surveying.player", player);
        utils.setScriptVar(player, "surveying.tool", self);
        utils.setScriptVar(player, "surveying.takingSamples", 1);
        utils.setScriptVar(self, "surveying.takingSamples", 1);
        utils.setScriptVar(player, "surveying.resource", resource_type);
        queueCommand(player, (28609318), self, "No params.", COMMAND_PRIORITY_FRONT);
        utils.setScriptVar(player, "surveying.outstandingHarvestMessage", getGameTime() + getSurveyToolDelay(player) + 5);
        dictionary outparams = new dictionary();
        outparams.put("player", player);
        messageTo(self, "sampleLoop", outparams, 1.f, false);
        messageTo(self, "samplingEffect", outparams, 1.f, false);
        prose_package pp = prose.getPackage(SID_START_SAMPLING, resource_type);
        sendSystemMessageProse(player, pp);
        xp.grantCraftingXpChance(self, player, 30);
        return SCRIPT_CONTINUE;
    }
    public int sampleLoop(obj_id self, dictionary params) throws InterruptedException
    {
        if (params == null)
        {
            return SCRIPT_CONTINUE;
        }
        obj_id player = params.getObjId("player");
        if (player == null)
        {
            return SCRIPT_CONTINUE;
        }
        boolean loopRunning = utils.hasScriptVar(player, "surveying.takingSamples") || utils.hasScriptVar(self, "surveying.takingSamples");
        if (!loopRunning)
        {
            return SCRIPT_CONTINUE;
        }
        boolean surveying = utils.hasScriptVar(player, "surveying.surveying") || utils.hasScriptVar(self, "surveying.surveying");
        if (surveying)
        {
            resource.cleanupTool(player, self);
            sendSystemMessage(player, SID_SAMPLE_SURVEY);
            return SCRIPT_CONTINUE;
        }
        if (!utils.isNestedWithin(self, player))
        {
            resource.cleanupTool(player, self);
            sendSystemMessage(player, SID_SAMPLE_GONE);
            return SCRIPT_CONTINUE;
        }
        utils.setScriptVar(player, "surveying.takingSamples", 1);
        utils.setScriptVar(self, "surveying.takingSamples", 1);
        location playerCurrent = getLocation(player);
        location sampleLocation = getLocationObjVar(player, "surveying.sampleLocation");
        String resource_type = utils.getStringScriptVar(player, "surveying.resource");
        float dist = 0.f;
        if (sampleLocation != null)
        {
            dist = utils.getDistance(playerCurrent, sampleLocation);
        }
        if ((sampleLocation != null) && (dist > 1.f))
        {
            sendSystemMessage(player, SID_SAMPLE_CANCEL);
            resource.cleanupTool(player, self);
            return SCRIPT_CONTINUE;
        }
        int action = getAttrib(player, ACTION);
        int actioncost = SAMPLE_ACTION_COST * (getLevel(player) / 2);
        if (!drainAttributes(player, actioncost, 0))
        {
            sendSystemMessage(player, SID_SAMPLE_MIND);
            resource.cleanupTool(player, self);
            queueCommand(player, (-1465754503), player, "No params.", COMMAND_PRIORITY_FRONT);
            return SCRIPT_OVERRIDE;
        }
        setObjVar(player, "surveying.sampleLocation", playerCurrent);
        int sampleRes = resource.getSample(player, self, resource_type);
        if (sampleRes == resource.SAMPLE_STOP_LOOP)
        {
            resource.cleanupTool(player, self);
            if (hasObjVar(player, "surveying.sampleCrateGenFailed"))
            {
                removeObjVar(player, "surveying.sampleCrateGenFailed");
            }
            queueCommand(player, (-1465754503), player, "No params.", COMMAND_PRIORITY_FRONT);
        }
        else if (sampleRes == resource.SAMPLE_PAUSE_LOOP_EVENT)
        {
        }
        else 
        {
            messageTo(self, "samplingEffect", params, getSurveyToolDelay(player), false);
            messageTo(self, "resourceHarvest", params, getSurveyToolDelay(player) + 2, false);
            if (!utils.hasScriptVar(player, "surveying.outstandingHarvestMessage"))
            {
                utils.setScriptVar(player, "surveying.outstandingHarvestMessage", getGameTime() + getSurveyToolDelay(player) + 4);
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int handleRadioactiveConfirm(obj_id self, dictionary params) throws InterruptedException
    {
        if (params == null || params.isEmpty())
        {
            return SCRIPT_CONTINUE;
        }
        obj_id player = sui.getPlayerId(params);
        if (!isIdValid(player))
        {
            return SCRIPT_CONTINUE;
        }
        int bp = sui.getIntButtonPressed(params);
        if (bp == sui.BP_CANCEL)
        {
            utils.removeScriptVarTree(player, "surveying.radioactive");
            return SCRIPT_CONTINUE;
        }
        utils.removeScriptVar(player, "surveying.radioactive.pid");
        String resource_type = utils.getStringScriptVar(player, "surveying.radioactive.name");
        if (resource_type != null && !resource_type.equals(""))
        {
            sendSystemMessageTestingOnly(player, getString(new string_id(STF, "radioactive_sample_known")) + resource_type + ".");
        }
        else 
        {
            sendSystemMessageTestingOnly(player, getString(new string_id(STF, "radioactive_sample_unknown")));
        }
        return SCRIPT_CONTINUE;
    }
    public int samplingEffect(obj_id self, dictionary params) throws InterruptedException
    {
        if (params == null)
        {
            return SCRIPT_CONTINUE;
        }
        obj_id player = params.getObjId("player");
        if (!isIdValid(player))
        {
            return SCRIPT_CONTINUE;
        }
        if (!utils.hasScriptVar(player, "surveying.takingSamples"))
        {
            return SCRIPT_CONTINUE;
        }
        if (!utils.hasScriptVar(self, "surveying.takingSamples"))
        {
            return SCRIPT_CONTINUE;
        }
        String resType = getStringObjVar(self, "survey.resource_class");
        if (resType.equals("gas"))
        {
            playClientEffectLoc(player, "clienteffect/survey_sample_gas.cef", getLocation(player), 0f);
        }
        else if (resType.equals("chemical"))
        {
            playClientEffectLoc(player, "clienteffect/survey_sample_moisture.cef", getLocation(player), 0f);
        }
        else if (resType.equals("flora_resources"))
        {
            playClientEffectLoc(player, "clienteffect/survey_sample_lumber.cef", getLocation(player), 0f);
        }
        else if (resType.equals("mineral"))
        {
            playClientEffectLoc(player, "clienteffect/survey_sample_mineral.cef", getLocation(player), 0f);
        }
        else if (resType.equals("water"))
        {
            playClientEffectLoc(player, "clienteffect/survey_sample_liquid.cef", getLocation(player), 0f);
        }
        else if (resType.equals("energy_renewable_site_limited_geothermal"))
        {
            playClientEffectLoc(player, "clienteffect/survey_sample_liquid.cef", getLocation(player), 0f);
        }
        return SCRIPT_CONTINUE;
    }
    public int resourceHarvest(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id player = params.getObjId("player");
        if (player.isAuthoritative() == false)
        {
            debugServerConsoleMsg(player, "player has gone offline or something. BAD! Not authoritative");
            return SCRIPT_CONTINUE;
        }
        utils.removeScriptVar(player, "surveying.outstandingHarvestMessage");
        if (!utils.hasScriptVar(player, "surveying.takingSamples"))
        {
            return SCRIPT_CONTINUE;
        }
        if (!utils.hasScriptVar(self, "surveying.takingSamples"))
        {
            return SCRIPT_CONTINUE;
        }
        messageTo(self, "sampleLoop", params, 1.f, false);
        return SCRIPT_CONTINUE;
    }
    public int handleSetRange(obj_id self, dictionary params) throws InterruptedException
    {
        if (params == null)
        {
            return SCRIPT_CONTINUE;
        }
        obj_id player = sui.getPlayerId(params);
        if (!isIdValid(player))
        {
            return SCRIPT_CONTINUE;
        }
        int idx = sui.getListboxSelectedRow(params);
        if (idx < 0)
        {
            idx = 0;
        }
        obj_id user = getObjIdObjVar(self, resource.VAR_SETTINGS_PLAYER);
        if (!isIdValid(user))
        {
            return SCRIPT_CONTINUE;
        }
        if (player != user)
        {
            return SCRIPT_CONTINUE;
        }
        int[] ranges = getIntArrayObjVar(self, resource.VAR_SETTINGS_RANGES);
        int[] resolutions = getIntArrayObjVar(self, resource.VAR_SETTINGS_RESOLUTIONS);
        if ((ranges == null) || (ranges.length == 0) || (resolutions == null) || (resolutions.length == 0))
        {
            int max_range = getIntObjVar(self, resource.VAR_SURVEY_RANGE_MAX);
            if ((max_range < resource.SURVEY_RANGE_MIN) || (max_range > resource.SURVEY_RANGE_MAX))
            {
                setObjVar(self, resource.VAR_SURVEY_RANGE_VALUE, resource.SURVEY_RANGE_MAX);
            }
            else 
            {
                setObjVar(self, resource.VAR_SURVEY_RANGE_VALUE, max_range);
            }
            int min_res = getIntObjVar(self, resource.VAR_SURVEY_RESOLUTION_MIN);
            if ((min_res < resource.SURVEY_RESOLUTION_MIN) || (min_res > resource.SURVEY_RESOLUTION_MAX))
            {
                setObjVar(self, resource.VAR_SURVEY_RESOLUTION_VALUE, resource.SURVEY_RESOLUTION_MIN);
            }
            else 
            {
                setObjVar(self, resource.VAR_SURVEY_RESOLUTION_VALUE, min_res);
            }
        }
        else 
        {
            if (idx > ranges.length - 1)
            {
                return SCRIPT_CONTINUE;
            }
            setObjVar(self, resource.VAR_SETTINGS_INDEX, idx);
            setObjVar(self, resource.VAR_SURVEY_RANGE_VALUE, ranges[idx]);
            setObjVar(self, resource.VAR_SURVEY_RESOLUTION_VALUE, resolutions[idx]);
            removeObjVar(self, resource.VAR_SETTINGS_RANGES);
            removeObjVar(self, resource.VAR_SETTINGS_RESOLUTIONS);
        }
        return SCRIPT_CONTINUE;
    }
    public int continueSampleLoop(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id player = params.getObjId("player");
        messageTo(self, "samplingEffect", params, getSurveyToolDelay(player), false);
        messageTo(self, "resourceHarvest", params, getSurveyToolDelay(player) + 2, false);
        utils.setScriptVar(player, "surveying.outstandingHarvestMessage", getGameTime() + getSurveyToolDelay(player) + 4);
        return SCRIPT_CONTINUE;
    }
    public int stopSampleEvent(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id player = params.getObjId("player");
        resource.cleanupTool(player, self);
        queueCommand(player, (-1465754503), player, "No params.", COMMAND_PRIORITY_FRONT);
        return SCRIPT_CONTINUE;
    }
    public String pickSpecialResourceToGive(obj_id player, String resource) throws InterruptedException
    {
        String special = "";
        obj_id resourceType = getResourceTypeByName(resource);
        boolean energy = isResourceDerivedFrom(resourceType, "energy");
        boolean gas = isResourceDerivedFrom(resourceType, "gas");
        if (gas == true || energy == true)
        {
            int energySwitch = rand(1, 2);
            if (energySwitch == 1)
            {
                special = "object/tangible/loot/quest/ardanium_ii.iff";
            }
            else 
            {
                special = "object/tangible/loot/quest/wind_crystal.iff";
            }
        }
        else 
        {
            int mineralSwitch = rand(1, 3);
            if (mineralSwitch == 1)
            {
                special = "object/tangible/loot/quest/ostrine.iff";
            }
            else if (mineralSwitch == 2)
            {
                special = "object/tangible/loot/quest/endrine.iff";
            }
            else 
            {
                special = "object/tangible/loot/quest/rudic.iff";
            }
        }
        return special;
    }
    public void setSpecialResourceObjVars(String special, obj_id resource, float efficiency) throws InterruptedException
    {
        if (!isIdValid(resource) || special == null || special.equals(""))
        {
            return;
        }
        int average = (int)(efficiency * 10.0f);
        if (special.equals("object/tangible/loot/quest/ardanium_ii.iff"))
        {
            setObjVar(resource, "crafting_components.res_potential_energy", getResourceValue(average));
            setObjVar(resource, "crafting_components.res_quality", getResourceValue(average));
        }
        else if (special.equals("object/tangible/loot/quest/wind_crystal.iff"))
        {
            setObjVar(resource, "crafting_components.res_potential_energy", getResourceValue(average));
        }
        else if (special.equals("object/tangible/loot/quest/ostrine.iff"))
        {
            setObjVar(resource, "crafting_components.res_malleability", getResourceValue(average));
            setObjVar(resource, "crafting_components.res_quality", getResourceValue(average));
        }
        else if (special.equals("object/tangible/loot/quest/endrine.iff"))
        {
            setObjVar(resource, "crafting_components.res_malleability", getResourceValue(average));
            setObjVar(resource, "crafting_components.res_quality", getResourceValue(average));
            setObjVar(resource, "crafting_components.res_toughness", getResourceValue(average));
        }
        else if (special.equals("object/tangible/loot/quest/rudic.iff"))
        {
            setObjVar(resource, "crafting_components.res_conductivity", getResourceValue(average));
            setObjVar(resource, "crafting_components.res_decay_resist", getResourceValue(average));
            setObjVar(resource, "crafting_components.res_quality", getResourceValue(average));
            setObjVar(resource, "crafting_components.res_shock_resistance", getResourceValue(average));
        }
    }
    public int getResourceValue(int average) throws InterruptedException
    {
        float value = gaussRand(average, 50.0f);
        if (value > Math.min(average + 150, 1000))
        {
            value = Math.min(average + 150, 1000);
        }
        else if (value < Math.max(average - 150, 150))
        {
            value = Math.max(average - 150, 150);
        }
        return (int)(value);
    }
    public int getSurveyToolDelay(obj_id player) throws InterruptedException
    {
        if (!isIdValid(player))
        {
            return SURVEY_TOOL_DELAY;
        }
        int delay = SURVEY_TOOL_DELAY - (int)getSkillStatisticModifier(player, "expertise_resource_sampling_time_decrease");
        if (delay <= MIN_SURVEY_TOOL_DELAY)
        {
            delay = MIN_SURVEY_TOOL_DELAY;
        }
        return delay;
    }
}
