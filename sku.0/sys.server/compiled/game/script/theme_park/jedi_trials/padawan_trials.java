package script.theme_park.jedi_trials;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.ai_lib;
import script.library.chat;
import script.library.create;
import script.library.fs_quests;
import script.library.jedi;
import script.library.jedi_trials;
import script.library.locations;
import script.library.prose;
import script.library.sui;
import script.library.utils;
import script.library.xp;
import script.library.static_item;

public class padawan_trials extends script.base_script
{
    public padawan_trials()
    {
    }
    public static final String CITIES_DATATABLE = "datatables/jedi_trials/cities.iff";
    public static final String TRIAL_NPC_SCRIPT = "theme_park.jedi_trials.padawan_trial_npc";
    public static final String TRIAL_SCRIPT_PREFIX = "conversation.padawan_";
    public static final String LOC_NAME_BEGIN_NPC = "padawan_trials_begin_npc";
    public static final String LOC_NAME_SECOND_LOC = "padawan_trials_second_loc";
    public static final String LOC_NAME_THIRD_LOC = "padawan_trials_third_loc";
    public int OnAttach(obj_id self) throws InterruptedException
    {
        messageTo(self, "handleOnAttach", null, 2, false);
        return SCRIPT_CONTINUE;
    }
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        messageTo(self, "handleScriptInitialized", null, 2, false);
        return SCRIPT_CONTINUE;
    }
    public int handleOnAttach(obj_id self, dictionary params) throws InterruptedException
    {
        if (!jedi_trials.isEligibleForJediPadawanTrials(self))
        {
            if (!hasObjVar(self, jedi_trials.JEDI_TRIALS_INELIGIBLE_WARNING_OBJVAR))
            {
                setObjVar(self, jedi_trials.JEDI_TRIALS_INELIGIBLE_WARNING_OBJVAR, true);
                String str_message = utils.packStringId(jedi_trials.SID_PADAWAN_TRIALS_STARTED_NOT_ELIGIBLE);
                jedi_trials.oneButtonMsgBox(self, self, "noHandler", jedi_trials.SID_PADAWAN_TRIALS_TITLE, str_message, jedi_trials.SID_CLOSE_BUTTON);
                jedi_trials.doJediTrialsCleanup(self);
                return SCRIPT_CONTINUE;
            }
        }
        else 
        {
            messageTo(self, "handleScriptInitialized", null, 1, false);
        }
        return SCRIPT_CONTINUE;
    }
    public int handleScriptInitialized(obj_id self, dictionary params) throws InterruptedException
    {
        if (!hasObjVar(self, jedi_trials.PADAWAN_QUESTLIST_OBJVAR))
        {
            messageTo(self, "handleDoPadawanTrialsSetup", null, 1, false);
        }
        return SCRIPT_CONTINUE;
    }
    public int handleDoPadawanTrialsSetup(obj_id self, dictionary params) throws InterruptedException
    {
        jedi_trials.doPadawanTrialsSetup(self);
        return SCRIPT_CONTINUE;
    }
    public int handleForceShrineTrialMessage(obj_id self, dictionary params) throws InterruptedException
    {
        if (!hasObjVar(self, jedi_trials.PADAWAN_QUESTLIST_OBJVAR) || !hasObjVar(self, jedi_trials.JEDI_TRIALS_TRIALNUMBER_OBJVAR))
        {
            messageTo(self, "jediTrialsCleanup", null, 1, false);
            return SCRIPT_CONTINUE;
        }
        int[] questList = getIntArrayObjVar(self, jedi_trials.PADAWAN_QUESTLIST_OBJVAR);
        if (questList == null || questList.length < 1)
        {
            return SCRIPT_CONTINUE;
        }
        int trialNum = getIntObjVar(self, jedi_trials.JEDI_TRIALS_TRIALNUMBER_OBJVAR);
        if (trialNum >= questList.length)
        {
            messageTo(self, "handleJediPadawanTrialsComplete", null, 1, false);
            return SCRIPT_CONTINUE;
        }
        int datatableRow = questList[trialNum];
        int numDatatableRows = dataTableGetNumRows(jedi_trials.PADAWAN_TRIALS_DATATABLE);
        if (datatableRow >= numDatatableRows)
        {
            trialNum = trialNum + 1;
            setObjVar(self, jedi_trials.JEDI_TRIALS_TRIALNUMBER_OBJVAR, trialNum);
            dictionary webster = new dictionary();
            webster.put("nextTrial", true);
            messageTo(self, "handleForceShrineTrialMessage", webster, 1, false);
            return SCRIPT_CONTINUE;
        }
        String trialType = dataTableGetString(jedi_trials.PADAWAN_TRIALS_DATATABLE, datatableRow, "trialType");
        String trialName = dataTableGetString(jedi_trials.PADAWAN_TRIALS_DATATABLE, datatableRow, "trialName");
        if (trialType == null || trialType.equals(""))
        {
            return SCRIPT_CONTINUE;
        }
        if (trialName == null || trialName.equals(""))
        {
            return SCRIPT_CONTINUE;
        }
        if (trialName.equals("craft_lightsaber"))
        {
            if (!hasSkill(self, jedi_trials.PADAWAN_INITIATE_SKBOX))
            {
                grantSkill(self, jedi_trials.PADAWAN_INITIATE_SKBOX);
                String custLogMsg = "Padawan trials Jedi Initiate granted: Player %TU has been granted the Jedi Initiate skill box.";
                CustomerServiceLog(jedi_trials.PADAWAN_TRIALS_LOG, custLogMsg, self);
            }
            if (!isJediState(self, JEDI_STATE_JEDI))
            {
                setJediState(self, JEDI_STATE_JEDI);
                String custLogMsg2 = "Padawan trials Jedi State Flag set: Player %TU has been flagged as a Jedi.";
                CustomerServiceLog(jedi_trials.PADAWAN_TRIALS_LOG, custLogMsg2, self);
            }
            jedi.recalculateForcePower(self);
            if (!hasObjVar(self, jedi_trials.PADAWAN_SABER_CRAFTED_OBJVAR))
            {
                string_id sid_craftingmsg = new string_id("jedi_trials", trialName + "_01");
                String str_craftingmsg = utils.packStringId(sid_craftingmsg);
                jedi_trials.oneButtonMsgBox(self, self, "noHandler", jedi_trials.SID_PADAWAN_TRIALS_TITLE, str_craftingmsg, jedi_trials.SID_CLOSE_BUTTON);
            }
            else 
            {
                string_id sid_craftingmsg = new string_id("jedi_trials", trialName + "_02");
                String str_craftingmsg = utils.packStringId(sid_craftingmsg);
                jedi_trials.oneButtonMsgBox(self, self, "noHandler", jedi_trials.SID_PADAWAN_TRIALS_TITLE, str_craftingmsg, jedi_trials.SID_CLOSE_BUTTON);
            }
        }
        else if (hasObjVar(self, "jedi_trials.padawan_trials.temp.acceptedTask"))
        {
            string_id sid_msg = new string_id("jedi_trials", trialName + "_03");
            String str_msg = utils.packStringId(sid_msg);
            if (trialType.equals("huntAndDestroy"))
            {
                int numKilled = 0;
                if (hasObjVar(self, jedi_trials.JEDI_TRIALS_CUR_TRIAL_TOTAL_OBJVAR))
                {
                    numKilled = getIntObjVar(self, jedi_trials.JEDI_TRIALS_CUR_TRIAL_TOTAL_OBJVAR);
                }
                str_msg = str_msg + " " + numKilled;
            }
            jedi_trials.threeButtonMsgBox(self, self, "handleRestartOrAbortPadawanTrialsChoice", jedi_trials.SID_PADAWAN_TRIALS_TITLE, str_msg, jedi_trials.SID_RESTART_BUTTON, jedi_trials.SID_CLOSE_BUTTON, jedi_trials.SID_ABORT_BUTTON);
        }
        else 
        {
            if (!hasObjVar(self, jedi_trials.PADAWAN_TARGET_PLANET_OBJVAR))
            {
                String planet = jedi_trials.chooseRandomCivilizedPlanet(self);
                if (trialName.equals("architect"))
                {
                    planet = "naboo";
                }
                int[] cityIndexes = getDatatableRowsForStringsInColumn(CITIES_DATATABLE, "planet", planet);
                if (cityIndexes != null || cityIndexes.length > 0)
                {
                    int cityRow = cityIndexes[rand(0, cityIndexes.length - 1)];
                    String cityName = dataTableGetString(CITIES_DATATABLE, cityRow, "cityName");
                    if (trialName.equals("pannaqa"))
                    {
                        planet = "dathomir";
                        cityName = "Aurilia";
                        cityRow = -1;
                        if (hasObjVar(self, "jedi_trials.padawan_trials.pannaqa.failed"))
                        {
                            removeObjVar(self, "jedi_trials.padawan_trials.pannaqa.failed");
                        }
                    }
                    setObjVar(self, jedi_trials.PADAWAN_TARGET_PLANET_OBJVAR, planet);
                    setObjVar(self, jedi_trials.PADAWAN_TARGET_CITY_ROW_OBJVAR, cityRow);
                    string_id sid_part01 = new string_id("jedi_trials", trialName + "_01");
                    string_id sid_part02 = new string_id("jedi_trials", trialName + "_02");
                    String str_part01 = utils.packStringId(sid_part01);
                    String str_part02 = utils.packStringId(sid_part02);
                    string_id sid_planet = new string_id("jedi_trials", planet);
                    String str_planet = utils.packStringId(sid_planet);
                    String str_message = str_part01 + " " + str_planet + " " + str_part02 + " " + cityName + ".";
                    location here = getLocation(self);
                    String herePlanet = here.area;
                    if (herePlanet.equals(planet))
                    {
                        messageTo(self, "handleSetBeginLoc", null, 1, false);
                    }
                    jedi_trials.oneButtonMsgBox(self, self, "handleTellAboutRestart", jedi_trials.SID_PADAWAN_TRIALS_TITLE, str_message, jedi_trials.SID_CLOSE_BUTTON);
                    String custLogMsg02 = "Padawan trials trial began: Player %TU is beginning their # " + trialNum + " trial which for them is " + trialName + ".";
                    CustomerServiceLog(jedi_trials.PADAWAN_TRIALS_LOG, custLogMsg02, self);
                }
            }
            else 
            {
                String targetPlanet = getStringObjVar(self, jedi_trials.PADAWAN_TARGET_PLANET_OBJVAR);
                String targetCityName;
                if (trialName.equals("pannaqa"))
                {
                    targetCityName = "Aurilia";
                }
                else 
                {
                    int targetCityRow = getIntObjVar(self, jedi_trials.PADAWAN_TARGET_CITY_ROW_OBJVAR);
                    targetCityName = dataTableGetString(CITIES_DATATABLE, targetCityRow, "cityName");
                }
                string_id sid_part01 = new string_id("jedi_trials", trialName + "_01");
                string_id sid_part02 = new string_id("jedi_trials", trialName + "_02");
                String str_part01 = utils.packStringId(sid_part01);
                String str_part02 = utils.packStringId(sid_part02);
                string_id sid_targetPlanet = new string_id("jedi_trials", targetPlanet);
                String str_targetPlanet = utils.packStringId(sid_targetPlanet);
                String str_message = str_part01 + " " + str_targetPlanet + " " + str_part02 + " " + targetCityName + ".";
                jedi_trials.threeButtonMsgBox(self, self, "handleRestartOrAbortPadawanTrialsChoice", jedi_trials.SID_PADAWAN_TRIALS_TITLE, str_message, jedi_trials.SID_RESTART_BUTTON, jedi_trials.SID_CLOSE_BUTTON, jedi_trials.SID_ABORT_BUTTON);
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int handleQuestCraftingAction(obj_id self, dictionary params) throws InterruptedException
    {
        int[] questList = getIntArrayObjVar(self, jedi_trials.PADAWAN_QUESTLIST_OBJVAR);
        if (questList == null || questList.length < 1)
        {
            return SCRIPT_CONTINUE;
        }
        int trialNum = getIntObjVar(self, jedi_trials.JEDI_TRIALS_TRIALNUMBER_OBJVAR);
        int datatableRow = questList[trialNum];
        String trialName = dataTableGetString(jedi_trials.PADAWAN_TRIALS_DATATABLE, datatableRow, "trialName");
        if (trialName.equals("craft_lightsaber"))
        {
            if (params.containsKey("prototypeTemplate") && !hasObjVar(self, jedi_trials.PADAWAN_SABER_CRAFTED_OBJVAR))
            {
                String prototypeTemplate = params.getString("prototypeTemplate");
                int saberCheck = prototypeTemplate.indexOf("lightsaber");
                int componentCheck = prototypeTemplate.indexOf("component");
                if (saberCheck > -1 && componentCheck < 0)
                {
                    setObjVar(self, jedi_trials.PADAWAN_SABER_CRAFTED_OBJVAR, true);
                    string_id sid_craftingmsg = new string_id("jedi_trials", trialName + "_02");
                    String str_craftingmsg = utils.packStringId(sid_craftingmsg);
                    jedi_trials.oneButtonMsgBox(self, self, "noHandler", jedi_trials.SID_PADAWAN_TRIALS_TITLE, str_craftingmsg, jedi_trials.SID_CLOSE_BUTTON);
                }
            }
            else if (params.containsKey("crystal_tuned") && hasObjVar(self, jedi_trials.PADAWAN_SABER_CRAFTED_OBJVAR))
            {
                messageTo(self, "handleTrialComplete", null, 1, false);
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int OnLogin(obj_id self) throws InterruptedException
    {
        if (hasObjVar(self, jedi_trials.PADAWAN_TARGET_PLANET_OBJVAR) && !hasObjVar(self, jedi_trials.PADAWAN_TARGET_LOC_OBJVAR))
        {
            String targetPlanet = getStringObjVar(self, jedi_trials.PADAWAN_TARGET_PLANET_OBJVAR);
            location here = getLocation(self);
            String currentPlanet = here.area;
            if (currentPlanet.equals(targetPlanet))
            {
                messageTo(self, "handleSetBeginLoc", null, 1, false);
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int handleSetBeginLoc(obj_id self, dictionary params) throws InterruptedException
    {
        int[] questList = getIntArrayObjVar(self, jedi_trials.PADAWAN_QUESTLIST_OBJVAR);
        if (questList == null || questList.length < 1)
        {
            return SCRIPT_CONTINUE;
        }
        int trialNum = getIntObjVar(self, jedi_trials.JEDI_TRIALS_TRIALNUMBER_OBJVAR);
        int datatableRow = questList[trialNum];
        String trialName = dataTableGetString(jedi_trials.PADAWAN_TRIALS_DATATABLE, datatableRow, "trialName");
        if (hasObjVar(self, jedi_trials.PADAWAN_TARGET_PLANET_OBJVAR) && !hasObjVar(self, jedi_trials.PADAWAN_TARGET_LOC_OBJVAR))
        {
            String targetPlanet = getStringObjVar(self, jedi_trials.PADAWAN_TARGET_PLANET_OBJVAR);
            location targetLoc = null;
            if (trialName.equals("pannaqa"))
            {
                targetLoc = new location(5291.3f, 78.5f, -4037.8f, targetPlanet, null);
            }
            else 
            {
                int cityRow = getIntObjVar(self, jedi_trials.PADAWAN_TARGET_CITY_ROW_OBJVAR);
                float cityX = dataTableGetFloat(CITIES_DATATABLE, cityRow, "locX");
                float cityY = dataTableGetFloat(CITIES_DATATABLE, cityRow, "locY");
                float cityZ = dataTableGetFloat(CITIES_DATATABLE, cityRow, "locZ");
                location cityLoc = new location(cityX, cityY, cityZ, targetPlanet, null);
                region cityRegion = locations.getCityRegion(cityLoc);
                targetLoc = locations.getGoodCityLocation(cityRegion, targetPlanet);
            }
            if (targetLoc != null)
            {
                if (!trialName.equals("pannaqa"))
                {
                    addLocationTarget(LOC_NAME_BEGIN_NPC, targetLoc, 64.0f);
                }
                setObjVar(self, jedi_trials.PADAWAN_TARGET_LOC_OBJVAR, targetLoc);
                obj_id waypoint = createWaypointInDatapad(self, targetLoc);
                setWaypointName(waypoint, "Speak to this person");
                setWaypointActive(waypoint, true);
                setObjVar(self, jedi_trials.PADAWAN_WAYPOINT_OBJVAR, waypoint);
                if (!trialName.equals("old_musician"))
                {
                    dictionary webster = new dictionary();
                    webster.put("targetLoc", targetLoc);
                    webster.put("locationName", LOC_NAME_SECOND_LOC);
                    webster.put("locationObjVar", jedi_trials.PADAWAN_SECOND_LOC_OBJVAR);
                    webster.put("locationAttempts", 1);
                    messageTo(self, "tryToRequestLocation", webster, 1, false);
                }
            }
            else 
            {
                messageTo(self, "handleSetBeginLoc", null, 2, false);
            }
        }
        else if (hasObjVar(self, jedi_trials.PADAWAN_TARGET_LOC_OBJVAR))
        {
            location targetLoc = getLocationObjVar(self, jedi_trials.PADAWAN_TARGET_LOC_OBJVAR);
            addLocationTarget(LOC_NAME_BEGIN_NPC, targetLoc, 64.0f);
            setObjVar(self, jedi_trials.PADAWAN_TARGET_LOC_OBJVAR, targetLoc);
            obj_id oldWaypoint = getObjIdObjVar(self, jedi_trials.PADAWAN_WAYPOINT_OBJVAR);
            if (isIdValid(oldWaypoint))
            {
                destroyWaypointInDatapad(oldWaypoint, self);
            }
            obj_id waypoint = createWaypointInDatapad(self, targetLoc);
            setWaypointName(waypoint, "Padawan Trial waypoint");
            setWaypointActive(waypoint, true);
            setObjVar(self, jedi_trials.PADAWAN_WAYPOINT_OBJVAR, waypoint);
        }
        return SCRIPT_CONTINUE;
    }
    public int tryToRequestLocation(obj_id self, dictionary params) throws InterruptedException
    {
        location targetLoc = params.getLocation("targetLoc");
        String locationName = params.getString("locationName");
        String locationObjVar = params.getString("locationObjVar");
        int locationAttempts = params.getInt("locationAttempts");
        location randomLoc = locations.getRandomGoodLocation(targetLoc, 900.0f, 1800.0f, 32.0f);
        if (randomLoc != null)
        {
            setObjVar(self, locationObjVar, randomLoc);
            requestLocation(self, locationName, randomLoc, 500.0f, 32.0f, true, false);
            String custLogMsg = "theme_park.jedi_trials.padawan_trials: requestLocation( " + self + ", " + locationName + ", " + randomLoc + ", 500.0f, 32.0f, true, false )";
            CustomerServiceLog("Request_Location", custLogMsg, self);
        }
        else 
        {
            if (locationAttempts <= 10)
            {
                dictionary webster = new dictionary();
                webster.put("targetLoc", targetLoc);
                webster.put("locationName", locationName);
                webster.put("locationObjVar", locationObjVar);
                webster.put("locationAttempts", (locationAttempts + 1));
                messageTo(self, "tryToRequestLocation", webster, 1, false);
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int OnLocationReceived(obj_id self, String locationId, obj_id locationObject, location locationLocation, float locationRadius) throws InterruptedException
    {
        if (locationId == null)
        {
            return SCRIPT_CONTINUE;
        }
        if (locationId.equals(LOC_NAME_SECOND_LOC) || locationId.equals(LOC_NAME_THIRD_LOC))
        {
            String custLogMsg = "theme_park.jedi_trials.padawan_trials: OnLocationReceived( " + locationId + ", " + locationObject + ", " + locationLocation + ", " + locationRadius + " )";
            CustomerServiceLog("Request_Location", custLogMsg, self);
            if (!isIdValid(locationObject) || locationLocation == null)
            {
                return SCRIPT_CONTINUE;
            }
            int[] questList = getIntArrayObjVar(self, jedi_trials.PADAWAN_QUESTLIST_OBJVAR);
            if (questList == null || questList.length < 1)
            {
                return SCRIPT_CONTINUE;
            }
            int trialNum = getIntObjVar(self, jedi_trials.JEDI_TRIALS_TRIALNUMBER_OBJVAR);
            int datatableRow = questList[trialNum];
            String trialName = dataTableGetString(jedi_trials.PADAWAN_TRIALS_DATATABLE, datatableRow, "trialName");
            if (locationId.equals(LOC_NAME_SECOND_LOC))
            {
                setObjVar(self, jedi_trials.PADAWAN_SECOND_LOC_OBJVAR, locationLocation);
                utils.setScriptVar(self, jedi_trials.SECOND_LOC_OBJECT_OBJID_OBJVAR, locationObject);
                if (trialName.equals("pannaqa"))
                {
                    dictionary webster = new dictionary();
                    webster.put("targetLoc", locationLocation);
                    webster.put("locationName", LOC_NAME_THIRD_LOC);
                    webster.put("locationObjVar", jedi_trials.PADAWAN_THIRD_LOC_OBJVAR);
                    webster.put("locationAttempts", 1);
                    messageTo(self, "tryToRequestLocation", webster, 1, false);
                }
            }
            else if (locationId.equals(LOC_NAME_THIRD_LOC))
            {
                setObjVar(self, jedi_trials.PADAWAN_THIRD_LOC_OBJVAR, locationLocation);
                utils.setScriptVar(self, jedi_trials.THIRD_LOC_OBJECT_OBJID_OBJVAR, locationObject);
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int handleSetSecondLoc(obj_id self, dictionary params) throws InterruptedException
    {
        int[] questList = getIntArrayObjVar(self, jedi_trials.PADAWAN_QUESTLIST_OBJVAR);
        if (questList == null || questList.length < 1)
        {
            return SCRIPT_CONTINUE;
        }
        int trialNum = getIntObjVar(self, jedi_trials.JEDI_TRIALS_TRIALNUMBER_OBJVAR);
        int datatableRow = questList[trialNum];
        String trialName = dataTableGetString(jedi_trials.PADAWAN_TRIALS_DATATABLE, datatableRow, "trialName");
        if (hasObjVar(self, jedi_trials.PADAWAN_TARGET_PLANET_OBJVAR))
        {
            location here = getLocation(self);
            location secondLoc = null;
            if (trialName.equals("old_musician"))
            {
                secondLoc = new location(3468.0f, 5.0f, -4852.0f, "tatooine", null);
            }
            else if (hasObjVar(self, jedi_trials.PADAWAN_SECOND_LOC_OBJVAR))
            {
                secondLoc = getLocationObjVar(self, jedi_trials.PADAWAN_SECOND_LOC_OBJVAR);
            }
            else 
            {
                secondLoc = locations.getRandomGoodLocation(here, 900.0f, 1800.0f, 32.0f);
            }
            if (secondLoc != null)
            {
                if (!trialName.equals("old_musician"))
                {
                    addLocationTarget(LOC_NAME_SECOND_LOC, secondLoc, 100.0f);
                }
                setObjVar(self, jedi_trials.PADAWAN_SECOND_LOC_OBJVAR, secondLoc);
                obj_id oldWaypoint = getObjIdObjVar(self, jedi_trials.PADAWAN_WAYPOINT_OBJVAR);
                if (isIdValid(oldWaypoint))
                {
                    destroyWaypointInDatapad(oldWaypoint, self);
                }
                obj_id waypoint = createWaypointInDatapad(self, secondLoc);
                setWaypointName(waypoint, "Padawan Trial waypoint");
                setWaypointActive(waypoint, true);
                setObjVar(self, jedi_trials.PADAWAN_WAYPOINT_OBJVAR, waypoint);
            }
            else 
            {
                messageTo(self, "handleSetSecondLoc", null, 2, false);
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int handleSetThirdLoc(obj_id self, dictionary params) throws InterruptedException
    {
        int[] questList = getIntArrayObjVar(self, jedi_trials.PADAWAN_QUESTLIST_OBJVAR);
        if (questList == null || questList.length < 1)
        {
            return SCRIPT_CONTINUE;
        }
        int trialNum = getIntObjVar(self, jedi_trials.JEDI_TRIALS_TRIALNUMBER_OBJVAR);
        int datatableRow = questList[trialNum];
        String trialName = dataTableGetString(jedi_trials.PADAWAN_TRIALS_DATATABLE, datatableRow, "trialName");
        if (hasObjVar(self, jedi_trials.PADAWAN_TARGET_PLANET_OBJVAR))
        {
            location here = getLocation(self);
            location thirdLoc = null;
            if (trialName.equals("old_musician"))
            {
                thirdLoc = new location(469.0f, 12.0f, 5021.0f, "lok", null);
            }
            else if (hasObjVar(self, jedi_trials.PADAWAN_THIRD_LOC_OBJVAR))
            {
                thirdLoc = getLocationObjVar(self, jedi_trials.PADAWAN_THIRD_LOC_OBJVAR);
            }
            else 
            {
                thirdLoc = locations.getRandomGoodLocation(here, 900.0f, 1800.0f, 32.0f);
            }
            if (thirdLoc != null)
            {
                if (!trialName.equals("old_musician"))
                {
                    addLocationTarget(LOC_NAME_THIRD_LOC, thirdLoc, 64.0f);
                }
                setObjVar(self, jedi_trials.PADAWAN_THIRD_LOC_OBJVAR, thirdLoc);
                obj_id oldWaypoint = getObjIdObjVar(self, jedi_trials.PADAWAN_WAYPOINT_OBJVAR);
                if (isIdValid(oldWaypoint))
                {
                    destroyWaypointInDatapad(oldWaypoint, self);
                }
                obj_id waypoint = createWaypointInDatapad(self, thirdLoc);
                setWaypointName(waypoint, "Padawan Trial waypoint");
                setWaypointActive(waypoint, true);
                setObjVar(self, jedi_trials.PADAWAN_WAYPOINT_OBJVAR, waypoint);
            }
            else 
            {
                messageTo(self, "handleSetThirdLoc", null, 2, false);
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int OnArrivedAtLocation(obj_id self, String locationName) throws InterruptedException
    {
        int[] questList = getIntArrayObjVar(self, jedi_trials.PADAWAN_QUESTLIST_OBJVAR);
        if (questList == null || questList.length < 1)
        {
            return SCRIPT_CONTINUE;
        }
        int trialNum = getIntObjVar(self, jedi_trials.JEDI_TRIALS_TRIALNUMBER_OBJVAR);
        int datatableRow = questList[trialNum];
        String trialName = dataTableGetString(jedi_trials.PADAWAN_TRIALS_DATATABLE, datatableRow, "trialName");
        if (locationName.equals(LOC_NAME_BEGIN_NPC))
        {
            if (!trialName.equals("pannaqa"))
            {
                location beginLoc = getLocationObjVar(self, jedi_trials.PADAWAN_TARGET_LOC_OBJVAR);
                String beginSpawnType = dataTableGetString(jedi_trials.PADAWAN_TRIALS_DATATABLE, datatableRow, "trialNpc");
                if (beginSpawnType == null || beginSpawnType.equals("null") || beginSpawnType.equals(""))
                {
                    beginSpawnType = "commoner";
                }
                obj_id beginTrialNpc = create.staticObject(beginSpawnType, beginLoc);
                if (isIdValid(beginTrialNpc))
                {
                    setInvulnerable(beginTrialNpc, true);
                    setCreatureStatic(beginTrialNpc, true);
                    String beginTrialNpcName = dataTableGetString(jedi_trials.PADAWAN_TRIALS_DATATABLE, datatableRow, "trialNpcName");
                    if (beginTrialNpcName != null && !beginTrialNpcName.equals("null") && !beginTrialNpcName.equals(""))
                    {
                        setName(beginTrialNpc, beginTrialNpcName);
                    }
                    String beginScriptName = TRIAL_SCRIPT_PREFIX + trialName + "_01";
                    setObjVar(beginTrialNpc, jedi_trials.PADAWAN_TRIAL_PLAYER_OBJVAR, self);
                    setObjVar(self, jedi_trials.PADAWAN_TRIAL_NPC_OBJVAR, beginTrialNpc);
                    attachScript(beginTrialNpc, beginScriptName);
                    attachScript(beginTrialNpc, TRIAL_NPC_SCRIPT);
                }
            }
        }
        else if (locationName.equals(LOC_NAME_SECOND_LOC))
        {
            location secondTargetLoc = getLocationObjVar(self, jedi_trials.PADAWAN_SECOND_LOC_OBJVAR);
            String secondSpawnType = dataTableGetString(jedi_trials.PADAWAN_TRIALS_DATATABLE, datatableRow, "trialTarget");
            if (secondSpawnType.endsWith(".iff"))
            {
                obj_id skeleton = createObject(secondSpawnType, secondTargetLoc);
                setObjVar(skeleton, jedi_trials.PADAWAN_TRIAL_PLAYER_OBJVAR, self);
                setObjVar(self, jedi_trials.PADAWAN_TRIAL_NPC_OBJVAR, skeleton);
                attachScript(skeleton, "theme_park.jedi_trials.padawan_trials_search");
                setName(skeleton, "The remains of Josef Thelcar");
            }
            else 
            {
                int isVulnerable = dataTableGetInt(jedi_trials.PADAWAN_TRIALS_DATATABLE, datatableRow, "trialTargetVulnerable");
                obj_id secondTrialNpc = null;
                if (isVulnerable > 0)
                {
                    secondTrialNpc = create.object(secondSpawnType, secondTargetLoc);
                }
                else 
                {
                    secondTrialNpc = create.staticObject(secondSpawnType, secondTargetLoc);
                }
                if (isIdValid(secondTrialNpc))
                {
                    if (isVulnerable > 0)
                    {
                        ai_lib.setDefaultCalmBehavior(secondTrialNpc, ai_lib.BEHAVIOR_SENTINEL);
                        pvpSetAlignedFaction(secondTrialNpc, (221551254));
                        pvpMakeDeclared(secondTrialNpc);
                        setObjVar(secondTrialNpc, "ai.faction.nonAggro", true);
                    }
                    else 
                    {
                        setInvulnerable(secondTrialNpc, true);
                        setCreatureStatic(secondTrialNpc, true);
                    }
                    String secondScriptName = TRIAL_SCRIPT_PREFIX + trialName + "_02";
                    setObjVar(secondTrialNpc, jedi_trials.PADAWAN_TRIAL_PLAYER_OBJVAR, self);
                    setObjVar(self, jedi_trials.PADAWAN_TRIAL_NPC_OBJVAR, secondTrialNpc);
                    attachScript(secondTrialNpc, secondScriptName);
                    attachScript(secondTrialNpc, TRIAL_NPC_SCRIPT);
                    if (trialName.equals("spice_mom"))
                    {
                        setName(secondTrialNpc, "Evif Sulp");
                    }
                    if (utils.hasScriptVar(self, jedi_trials.SECOND_LOC_OBJECT_OBJID_OBJVAR))
                    {
                        obj_id secondLocObject = utils.getObjIdScriptVar(self, jedi_trials.SECOND_LOC_OBJECT_OBJID_OBJVAR);
                        if (isIdValid(secondLocObject))
                        {
                            locations.destroyLocationObject(secondLocObject);
                        }
                    }
                }
            }
        }
        else if (locationName.equals(LOC_NAME_THIRD_LOC))
        {
            location thirdTargetLoc = getLocationObjVar(self, jedi_trials.PADAWAN_THIRD_LOC_OBJVAR);
            String thirdSpawnType = dataTableGetString(jedi_trials.PADAWAN_TRIALS_DATATABLE, datatableRow, "trialTarget");
            obj_id thirdTrialNpc = create.staticObject(thirdSpawnType, thirdTargetLoc);
            if (isIdValid(thirdTrialNpc))
            {
                setInvulnerable(thirdTrialNpc, true);
                setCreatureStatic(thirdTrialNpc, true);
                String thirdScriptName = TRIAL_SCRIPT_PREFIX + trialName + "_03";
                setObjVar(thirdTrialNpc, jedi_trials.PADAWAN_TRIAL_PLAYER_OBJVAR, self);
                setObjVar(self, jedi_trials.PADAWAN_TRIAL_NPC_OBJVAR, thirdTrialNpc);
                attachScript(thirdTrialNpc, thirdScriptName);
                attachScript(thirdTrialNpc, TRIAL_NPC_SCRIPT);
                if (trialName.equals("pannaqa"))
                {
                    setName(thirdTrialNpc, "Shendo");
                }
                if (utils.hasScriptVar(self, jedi_trials.THIRD_LOC_OBJECT_OBJID_OBJVAR))
                {
                    obj_id thirdLocObject = utils.getObjIdScriptVar(self, jedi_trials.THIRD_LOC_OBJECT_OBJID_OBJVAR);
                    if (isIdValid(thirdLocObject))
                    {
                        locations.destroyLocationObject(thirdLocObject);
                    }
                }
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int handlerTrialNpcKilled(obj_id self, dictionary params) throws InterruptedException
    {
        int[] questList = getIntArrayObjVar(self, jedi_trials.PADAWAN_QUESTLIST_OBJVAR);
        if (questList == null || questList.length < 1)
        {
            return SCRIPT_CONTINUE;
        }
        int trialNum = getIntObjVar(self, jedi_trials.JEDI_TRIALS_TRIALNUMBER_OBJVAR);
        int datatableRow = questList[trialNum];
        String trialName = dataTableGetString(jedi_trials.PADAWAN_TRIALS_DATATABLE, datatableRow, "trialName");
        if (trialName.equals("the_ring"))
        {
            sendSystemMessage(self, jedi_trials.SID_PADAWAN_RECEIVED_THE_RING);
        }
        sendSystemMessage(self, jedi_trials.SID_PADAWAN_RETURN_TO_NPC);
        obj_id attacker = params.getObjId("attacker");
        obj_id trialNpc = params.getObjId("trialNpc");
        setObjVar(self, "jedi_trials.padawan_trials.temp.killedTarget", attacker);
        messageTo(self, "handleSetBeginLoc", null, 1, false);
        return SCRIPT_CONTINUE;
    }
    public int handlePlayerCombatKill(obj_id self, dictionary params) throws InterruptedException
    {
        if (!hasObjVar(self, jedi_trials.PADAWAN_QUESTLIST_OBJVAR) || !hasObjVar(self, jedi_trials.JEDI_TRIALS_TRIALNUMBER_OBJVAR))
        {
            messageTo(self, "jediTrialsCleanup", null, 1, false);
            return SCRIPT_CONTINUE;
        }
        int[] questList = getIntArrayObjVar(self, jedi_trials.PADAWAN_QUESTLIST_OBJVAR);
        if (questList == null || questList.length < 1)
        {
            return SCRIPT_CONTINUE;
        }
        int trialNum = getIntObjVar(self, jedi_trials.JEDI_TRIALS_TRIALNUMBER_OBJVAR);
        if (trialNum >= questList.length)
        {
            messageTo(self, "handleJediPadawanTrialsComplete", null, 1, false);
            return SCRIPT_CONTINUE;
        }
        int datatableRow = questList[trialNum];
        String trialType = dataTableGetString(jedi_trials.PADAWAN_TRIALS_DATATABLE, datatableRow, "trialType");
        if (trialType == null || trialType.equals(""))
        {
            return SCRIPT_CONTINUE;
        }
        String datatableColumn = "trialTarget";
        if (jedi_trials.checkForGroupCombatQuestKill(self, params, datatableColumn))
        {
            int currentKills = 1;
            if (hasObjVar(self, jedi_trials.JEDI_TRIALS_CUR_TRIAL_TOTAL_OBJVAR))
            {
                currentKills = getIntObjVar(self, jedi_trials.JEDI_TRIALS_CUR_TRIAL_TOTAL_OBJVAR) + 1;
            }
            sendSystemMessage(self, jedi_trials.SID_PADAWAN_TRIALS_PROGRESS);
            int targetKills = jedi_trials.getJediTrialsDatatableInt(self, "trialTargetAmt");
            if (currentKills >= targetKills)
            {
                sendSystemMessage(self, jedi_trials.SID_PADAWAN_RETURN_TO_NPC);
                setObjVar(self, jedi_trials.JEDI_TRIALS_CUR_TRIAL_TOTAL_OBJVAR, currentKills);
                removeObjVar(self, "handlePlayerCombatKill");
                messageTo(self, "handleSetBeginLoc", null, 1, false);
            }
            else 
            {
                setObjVar(self, jedi_trials.JEDI_TRIALS_CUR_TRIAL_TOTAL_OBJVAR, currentKills);
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int handleTrialComplete(obj_id self, dictionary params) throws InterruptedException
    {
        if (hasObjVar(self, jedi_trials.JEDI_TRIALS_CUR_TRIAL_TOTAL_OBJVAR))
        {
            removeObjVar(self, jedi_trials.JEDI_TRIALS_CUR_TRIAL_TOTAL_OBJVAR);
        }
        if (hasObjVar(self, "handlePlayerCombatKill"))
        {
            removeObjVar(self, "handlePlayerCombatKill");
        }
        if (hasObjVar(self, jedi_trials.PADAWAN_WAYPOINT_OBJVAR))
        {
            obj_id oldWaypoint = getObjIdObjVar(self, jedi_trials.PADAWAN_WAYPOINT_OBJVAR);
            if (isIdValid(oldWaypoint))
            {
                destroyWaypointInDatapad(oldWaypoint, self);
            }
        }
        removeObjVar(self, jedi_trials.PADAWAN_TRIALS_TEMP_OBJVAR);
        if (!jedi_trials.isEligibleForJediPadawanTrials(self))
        {
            String str_message = utils.packStringId(jedi_trials.SID_PADAWAN_TRIALS_BEING_REMOVED);
            if (hasSkill(self, jedi_trials.PADAWAN_INITIATE_SKBOX))
            {
                utils.setScriptVar(self, "jedi_trials.PadawanTrialsRevoke", true);
                str_message = utils.packStringId(jedi_trials.SID_PADAWAN_TRIALS_BEING_REMOVED_REVOKED);
                revokeSkill(self, jedi_trials.PADAWAN_INITIATE_SKBOX);
                setJediState(self, JEDI_STATE_FORCE_SENSITIVE);
                String custLogMsg = "Padawan trials Expulsion: Player %TU has removed from the Jedi Padawan trials for no longer" + " meeting the expected requirements.";
                CustomerServiceLog(jedi_trials.PADAWAN_TRIALS_LOG, custLogMsg, self);
            }
            jedi_trials.oneButtonMsgBox(self, self, "noHandler", jedi_trials.SID_PADAWAN_TRIALS_TITLE, str_message, jedi_trials.SID_CLOSE_BUTTON);
            messageTo(self, "jediTrialsFailedCleanup", null, 1, false);
            return SCRIPT_CONTINUE;
        }
        if (hasObjVar(self, jedi_trials.JEDI_TRIALS_INELIGIBLE_WARNING_OBJVAR))
        {
            removeObjVar(self, jedi_trials.JEDI_TRIALS_INELIGIBLE_WARNING_OBJVAR);
        }
        int[] questList = getIntArrayObjVar(self, jedi_trials.PADAWAN_QUESTLIST_OBJVAR);
        int trialNum = getIntObjVar(self, jedi_trials.JEDI_TRIALS_TRIALNUMBER_OBJVAR);
        int datatableRow = questList[trialNum];
        String trialName = dataTableGetString(jedi_trials.PADAWAN_TRIALS_DATATABLE, datatableRow, "trialName");
        String custLogMsg = "Padawan trials trial completion: Player %TU has completed their # " + trialNum + " trial which for them was " + trialName + ".";
        CustomerServiceLog(jedi_trials.PADAWAN_TRIALS_LOG, custLogMsg, self);
        setObjVar(self, jedi_trials.JEDI_TRIALS_TRIALNUMBER_OBJVAR, trialNum + 1);
        if (trialNum >= questList.length)
        {
            messageTo(self, "handleJediPadawanTrialsComplete", null, 1, false);
        }
        else 
        {
            jedi_trials.setClosestForceShrineWaypoint(self);
            String str_message = utils.packStringId(jedi_trials.SID_PADAWAN_NEXT_TRIAL);
            jedi_trials.oneButtonMsgBox(self, self, "noHandler", jedi_trials.SID_PADAWAN_TRIALS_TITLE, str_message, jedi_trials.SID_CLOSE_BUTTON);
        }
        return SCRIPT_CONTINUE;
    }
    public int handleJediPadawanTrialsComplete(obj_id self, dictionary params) throws InterruptedException
    {
        if (hasSkill(self, jedi_trials.JEDI_PADAWAN_SKBOX))
        {
            String custLogMsg = "Padawan trials Unexpected: handleJediPadawanTrialsComplete() called multiple times for Player %TU.";
            CustomerServiceLog(jedi_trials.PADAWAN_TRIALS_LOG, custLogMsg, self);
            return SCRIPT_CONTINUE;
        }
        if (!jedi_trials.isEligibleForJediPadawanTrials(self))
        {
            String str_message = utils.packStringId(jedi_trials.SID_PADAWAN_TRIALS_BEING_REMOVED);
            if (hasSkill(self, jedi_trials.PADAWAN_INITIATE_SKBOX))
            {
                utils.setScriptVar(self, "jedi_trials.PadawanTrialsRevoke", true);
                str_message = utils.packStringId(jedi_trials.SID_PADAWAN_TRIALS_BEING_REMOVED_REVOKED);
                revokeSkill(self, jedi_trials.PADAWAN_INITIATE_SKBOX);
                setJediState(self, JEDI_STATE_FORCE_SENSITIVE);
                String custLogMsg = "Padawan trials Expulsion: Player %TU has removed from the Jedi Padawan trials for no longer" + " meeting the expected requirements.";
                CustomerServiceLog(jedi_trials.PADAWAN_TRIALS_LOG, custLogMsg, self);
            }
            jedi_trials.oneButtonMsgBox(self, self, "noHandler", jedi_trials.SID_PADAWAN_TRIALS_TITLE, str_message, jedi_trials.SID_CLOSE_BUTTON);
            messageTo(self, "jediTrialsFailedCleanup", null, 1, false);
            return SCRIPT_CONTINUE;
        }
        location here = getLocation(self);
        String music = "sound/music_become_jedi.snd";
        String clientFx = "clienteffect/trap_electric_01.cef";
        string_id trialsCompleteMsg = jedi_trials.SID_PADAWAN_TRIALS_COMPLETED;
        playMusic(self, music);
        playClientEffectLoc(self, clientFx, here, 0.1f);
        if (!hasSkill(self, jedi_trials.PADAWAN_INITIATE_SKBOX))
        {
            grantSkill(self, jedi_trials.PADAWAN_INITIATE_SKBOX);
            String custLogMsg = "Padawan trials Jedi Initiate granted: Player %TU has been granted the Jedi Initiate skill box.";
            CustomerServiceLog(jedi_trials.PADAWAN_TRIALS_LOG, custLogMsg, self);
        }
        if (!isJediState(self, JEDI_STATE_JEDI))
        {
            setJediState(self, JEDI_STATE_JEDI);
            String custLogMsg2 = "Padawan trials Jedi State Flag set: Player %TU has been flagged as a Jedi.";
            CustomerServiceLog(jedi_trials.PADAWAN_TRIALS_LOG, custLogMsg2, self);
        }
        if (!hasSkill(self, jedi_trials.JEDI_PADAWAN_SKBOX))
        {
            grantSkill(self, jedi_trials.JEDI_PADAWAN_SKBOX);
        }
        jedi.setupJediTrainer(self);
        jedi.recalculateForcePower(self);
        obj_id createdObject = static_item.createNewItemFunction(jedi_trials.PADAWAN_ROBE_STRING, self);
        if (!hasJediSlot(self))
        {
            addJediSlot(self);
        }
        String custLogMsg = "Padawan trials completion: Player %TU has completed the Jedi Padawan Trials.";
        CustomerServiceLog(jedi_trials.PADAWAN_TRIALS_LOG, custLogMsg, self);
        String str_message = utils.packStringId(trialsCompleteMsg);
        jedi_trials.oneButtonMsgBox(self, self, "noHandler", jedi_trials.SID_PADAWAN_TRIALS_TITLE, str_message, jedi_trials.SID_CLOSE_BUTTON);
        messageTo(self, "jediTrialsCleanup", null, 1, false);
        return SCRIPT_CONTINUE;
    }
    public int handleQuestFailure(obj_id self, dictionary params) throws InterruptedException
    {
        int numTimesFailed = 1;
        if (hasObjVar(self, jedi_trials.JEDI_TRIALS_TRIALS_FAILED_OBJVAR))
        {
            numTimesFailed = numTimesFailed + getIntObjVar(self, jedi_trials.JEDI_TRIALS_TRIALS_FAILED_OBJVAR);
        }
        String str_message;
        String handleResult = "noHandler";
        if (numTimesFailed >= 3)
        {
            str_message = utils.packStringId(jedi_trials.SID_PADAWAN_TRIAL_FAILED_FINAL);
            handleResult = "jediTrialsFailedCleanup";
            String custLogMsg = "Padawan trials failure: Player %TU has failed their third trial and will now be expelled.";
            CustomerServiceLog(jedi_trials.PADAWAN_TRIALS_LOG, custLogMsg, self);
        }
        else 
        {
            if (numTimesFailed == 2)
            {
                str_message = utils.packStringId(jedi_trials.SID_PADAWAN_TRIAL_FAILED_SECOND);
                handleResult = "jediTrialsRestartCurrentTrial";
                String custLogMsg = "Padawan trials failure: Player %TU has failed their second trial.";
                CustomerServiceLog(jedi_trials.PADAWAN_TRIALS_LOG, custLogMsg, self);
            }
            else 
            {
                str_message = utils.packStringId(jedi_trials.SID_PADAWAN_TRIAL_FAILED_FIRST);
                handleResult = "jediTrialsRestartCurrentTrial";
                String custLogMsg = "Padawan trials failure: Player %TU has failed their first trial.";
                CustomerServiceLog(jedi_trials.PADAWAN_TRIALS_LOG, custLogMsg, self);
            }
            setObjVar(self, jedi_trials.JEDI_TRIALS_TRIALS_FAILED_OBJVAR, numTimesFailed);
        }
        jedi_trials.oneButtonMsgBox(self, self, "noHandler", jedi_trials.SID_PADAWAN_TRIALS_TITLE, str_message, jedi_trials.SID_CLOSE_BUTTON);
        messageTo(self, handleResult, null, 1, false);
        return SCRIPT_CONTINUE;
    }
    public int jediTrialsRestartCurrentTrial(obj_id self, dictionary params) throws InterruptedException
    {
        if (hasObjVar(self, jedi_trials.PADAWAN_WAYPOINT_OBJVAR))
        {
            obj_id oldWaypoint = getObjIdObjVar(self, jedi_trials.PADAWAN_WAYPOINT_OBJVAR);
            removeObjVar(self, jedi_trials.PADAWAN_WAYPOINT_OBJVAR);
            if (isIdValid(oldWaypoint))
            {
                destroyWaypointInDatapad(oldWaypoint, self);
            }
        }
        if (hasObjVar(self, "handlePlayerCombatKill"))
        {
            removeObjVar(self, "handlePlayerCombatKill");
        }
        if (hasObjVar(self, jedi_trials.JEDI_TRIALS_CUR_TRIAL_TOTAL_OBJVAR))
        {
            removeObjVar(self, jedi_trials.JEDI_TRIALS_CUR_TRIAL_TOTAL_OBJVAR);
        }
        removeObjVar(self, jedi_trials.PADAWAN_TRIALS_TEMP_OBJVAR);
        return SCRIPT_CONTINUE;
    }
    public int jediTrialsFailedCleanup(obj_id self, dictionary params) throws InterruptedException
    {
        if (hasSkill(self, jedi_trials.PADAWAN_INITIATE_SKBOX))
        {
            utils.setScriptVar(self, "jedi_trials.PadawanTrialsRevoke", true);
            revokeSkill(self, jedi_trials.PADAWAN_INITIATE_SKBOX);
            setJediState(self, JEDI_STATE_FORCE_SENSITIVE);
        }
        messageTo(self, "jediTrialsCleanup", null, 1, false);
        return SCRIPT_CONTINUE;
    }
    public int jediTrialsCleanup(obj_id self, dictionary params) throws InterruptedException
    {
        if (hasObjVar(self, jedi_trials.PADAWAN_WAYPOINT_OBJVAR))
        {
            obj_id oldWaypoint = getObjIdObjVar(self, jedi_trials.PADAWAN_WAYPOINT_OBJVAR);
            if (isIdValid(oldWaypoint))
            {
                destroyWaypointInDatapad(oldWaypoint, self);
            }
        }
        if (hasObjVar(self, "handlePlayerCombatKill"))
        {
            removeObjVar(self, "handlePlayerCombatKill");
        }
        jedi_trials.doJediTrialsCleanup(self);
        return SCRIPT_CONTINUE;
    }
    public int handleTellAboutRestart(obj_id self, dictionary params) throws InterruptedException
    {
        sendSystemMessage(self, jedi_trials.SID_PADAWAN_TELL_ABOUT_RESTART);
        return SCRIPT_CONTINUE;
    }
    public int handleRestartOrAbortPadawanTrialsChoice(obj_id self, dictionary params) throws InterruptedException
    {
        if ((params == null) || (params.isEmpty()))
        {
            return SCRIPT_CONTINUE;
        }
        String revert = params.getString(sui.MSGBOX_BTN_REVERT + ".RevertWasPressed");
        int bp = sui.getIntButtonPressed(params);
        String str_message;
        switch (bp)
        {
            case sui.BP_OK:
            if (revert != null && !revert.equals(""))
            {
                str_message = utils.packStringId(jedi_trials.SID_PADAWAN_ABORTED_CONFIRMATION);
                jedi_trials.twoButtonMsgBox(self, self, "handleAbortPadawanTrials", jedi_trials.SID_PADAWAN_TRIALS_TITLE, str_message, jedi_trials.SID_ABORT_BUTTON, jedi_trials.SID_CLOSE_BUTTON);
            }
            else 
            {
                str_message = utils.packStringId(jedi_trials.SID_PADAWAN_RESTART_CONFIRMATION);
                jedi_trials.twoButtonMsgBox(self, self, "handleRestartCurrentPadawanTrial", jedi_trials.SID_PADAWAN_TRIALS_TITLE, str_message, jedi_trials.SID_RESTART_BUTTON, jedi_trials.SID_CLOSE_BUTTON);
            }
            break;
            case sui.BP_CANCEL:
            break;
        }
        return SCRIPT_CONTINUE;
    }
    public int handleRestartCurrentPadawanTrial(obj_id self, dictionary params) throws InterruptedException
    {
        if ((params == null) || (params.isEmpty()))
        {
            return SCRIPT_CONTINUE;
        }
        String revert = params.getString(sui.MSGBOX_BTN_REVERT + ".RevertWasPressed");
        int bp = sui.getIntButtonPressed(params);
        switch (bp)
        {
            case sui.BP_OK:
            String custLogMsg = "Padawan trials failure: Player %TU has opted to restart their current trial.";
            CustomerServiceLog(jedi_trials.PADAWAN_TRIALS_LOG, custLogMsg, self);
            sendSystemMessage(self, jedi_trials.SID_PADAWAN_TRIAL_RESTARTED);
            messageTo(self, "jediTrialsRestartCurrentTrial", null, 1, false);
            break;
            case sui.BP_CANCEL:
            break;
        }
        return SCRIPT_CONTINUE;
    }
    public int handleAbortPadawanTrials(obj_id self, dictionary params) throws InterruptedException
    {
        if ((params == null) || (params.isEmpty()))
        {
            return SCRIPT_CONTINUE;
        }
        String revert = params.getString(sui.MSGBOX_BTN_REVERT + ".RevertWasPressed");
        int bp = sui.getIntButtonPressed(params);
        switch (bp)
        {
            case sui.BP_OK:
            String custLogMsg = "Padawan trials failure: Player %TU has opted to quit the Jedi PAdawan trials.";
            CustomerServiceLog(jedi_trials.PADAWAN_TRIALS_LOG, custLogMsg, self);
            sendSystemMessage(self, jedi_trials.SID_PADAWAN_TRIALS_ABORTED);
            messageTo(self, "jediTrialsFailedCleanup", null, 1, false);
            break;
            case sui.BP_CANCEL:
            break;
        }
        return SCRIPT_CONTINUE;
    }
    public int OnSkillRevoked(obj_id self, String skillName) throws InterruptedException
    {
        if (skillName.startsWith("force_sensitive"))
        {
            if (!jedi_trials.isEligibleForJediPadawanTrials(self))
            {
                if (!hasObjVar(self, jedi_trials.JEDI_TRIALS_INELIGIBLE_WARNING_OBJVAR))
                {
                    setObjVar(self, jedi_trials.JEDI_TRIALS_INELIGIBLE_WARNING_OBJVAR, true);
                    String str_message = utils.packStringId(jedi_trials.SID_PADAWAN_TRIALS_NO_LONGER_ELIGIBLE);
                    jedi_trials.oneButtonMsgBox(self, self, "noHandler", jedi_trials.SID_PADAWAN_TRIALS_TITLE, str_message, jedi_trials.SID_CLOSE_BUTTON);
                    return SCRIPT_CONTINUE;
                }
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int[] getDatatableRowsForStringsInColumn(String datatable, String column, String targetString) throws InterruptedException
    {
        Vector rows = new Vector();
        rows.setSize(0);
        String[] entries = dataTableGetStringColumn(datatable, column);
        if (entries == null || entries.length == 0)
        {
            int[] _rows = new int[0];
            if (rows != null)
            {
                _rows = new int[rows.size()];
                for (int _i = 0; _i < rows.size(); ++_i)
                {
                    _rows[_i] = ((Integer)rows.get(_i)).intValue();
                }
            }
            return _rows;
        }
        for (int i = 0; i < entries.length; i++)
        {
            String entryString = entries[i];
            if (entryString.equals(targetString))
            {
                utils.addElement(rows, i);
            }
        }
        int[] _rows = new int[0];
        if (rows != null)
        {
            _rows = new int[rows.size()];
            for (int _i = 0; _i < rows.size(); ++_i)
            {
                _rows[_i] = ((Integer)rows.get(_i)).intValue();
            }
        }
        return _rows;
    }
}
