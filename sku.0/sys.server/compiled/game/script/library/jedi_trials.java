package script.library;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.chat;
import script.library.create;
import script.library.force_rank;
import script.library.fs_quests;
import script.library.group;
import script.library.jedi;
import script.library.prose;
import script.library.sui;
import script.library.utils;
import script.library.xp;

public class jedi_trials extends script.base_script
{
    public jedi_trials()
    {
    }
    public static final String PADAWAN_TRIALS_DATATABLE = "datatables/jedi_trials/padawan_trials.iff";
    public static final String KNIGHT_TRIALS_DATATABLE = "datatables/jedi_trials/knight_trials.iff";
    public static final String FORCE_SHRINES_DATATABLE = "datatables/jedi_trials/force_shrine_loc.iff";
    public static final String PADAWAN_TRIALS_SCRIPT = "theme_park.jedi_trials.padawan_trials";
    public static final String KNIGHT_TRIALS_SCRIPT = "theme_park.jedi_trials.knight_trials";
    public static final String PADAWAN_TRIALS_ELIGIBLE_OBJVAR = "padawan_trials.padawanEligible";
    public static final String JEDI_TRIALS_BASE_OBJVAR = "jedi_trials";
    public static final String PADAWAN_TRIALS_BASE_OBJVAR = JEDI_TRIALS_BASE_OBJVAR + ".padawan_trials";
    public static final String PADAWAN_QUESTLIST_OBJVAR = PADAWAN_TRIALS_BASE_OBJVAR + ".questList";
    public static final String PADAWAN_TRIALS_TEMP_OBJVAR = PADAWAN_TRIALS_BASE_OBJVAR + ".temp";
    public static final String PADAWAN_TARGET_PLANET_OBJVAR = PADAWAN_TRIALS_BASE_OBJVAR + ".temp.targetPlanet";
    public static final String PADAWAN_TARGET_CITY_ROW_OBJVAR = PADAWAN_TRIALS_BASE_OBJVAR + ".temp.targetCityRow";
    public static final String PADAWAN_TARGET_LOC_OBJVAR = PADAWAN_TRIALS_BASE_OBJVAR + ".temp.targetLoc";
    public static final String PADAWAN_SECOND_LOC_OBJVAR = PADAWAN_TRIALS_BASE_OBJVAR + ".temp.secondLoc";
    public static final String PADAWAN_THIRD_LOC_OBJVAR = PADAWAN_TRIALS_BASE_OBJVAR + ".temp.thirdLoc";
    public static final String SECOND_LOC_OBJECT_OBJID_OBJVAR = PADAWAN_TRIALS_BASE_OBJVAR + ".temp.secondLocObject";
    public static final String THIRD_LOC_OBJECT_OBJID_OBJVAR = PADAWAN_TRIALS_BASE_OBJVAR + ".temp.thirdLocObject";
    public static final String PADAWAN_TRIAL_PLAYER_OBJVAR = PADAWAN_TRIALS_BASE_OBJVAR + ".temp.trialPlayer";
    public static final String PADAWAN_TRIAL_NPC_OBJVAR = PADAWAN_TRIALS_BASE_OBJVAR + ".temp.trialNpc";
    public static final String PADAWAN_WAYPOINT_OBJVAR = PADAWAN_TRIALS_BASE_OBJVAR + ".temp.waypoint";
    public static final String PADAWAN_SABER_CRAFTED_OBJVAR = PADAWAN_TRIALS_BASE_OBJVAR + ".temp.lightsaberCrafted";
    public static final String PADAWAN_SHRINE_WAYPOINT_OBJVAR = PADAWAN_TRIALS_BASE_OBJVAR + ".shrineWaypoint";
    public static final String KNIGHT_TRIALS_BASE_OBJVAR = JEDI_TRIALS_BASE_OBJVAR + ".knight_trials";
    public static final String KNIGHT_QUESTLIST_OBJVAR = KNIGHT_TRIALS_BASE_OBJVAR + ".questList";
    public static final String KNIGHT_COUNCIL_CHOICE_OBJVAR = KNIGHT_TRIALS_BASE_OBJVAR + ".councilChoice";
    public static final String JEDI_TRIALS_TRIALNUMBER_OBJVAR = JEDI_TRIALS_BASE_OBJVAR + ".trialNumber";
    public static final String JEDI_TRIALS_INELIGIBLE_WARNING_OBJVAR = JEDI_TRIALS_BASE_OBJVAR + ".ineligibleWarning";
    public static final String JEDI_TRIALS_TRIALS_FAILED_OBJVAR = JEDI_TRIALS_BASE_OBJVAR + ".trialsFailed";
    public static final String JEDI_TRIALS_CUR_TRIAL_TOTAL_OBJVAR = JEDI_TRIALS_BASE_OBJVAR + ".currentTrialTotal";
    public static final String JEDI_TRIALS_SHRINELOC_OBJVAR = JEDI_TRIALS_BASE_OBJVAR + ".forceShrineLoc";
    public static final string_id SID_PADAWAN_TRIALS_TITLE = new string_id("jedi_trials", "padawan_trials_title");
    public static final string_id SID_PADAWAN_INTRO_MSG = new string_id("jedi_trials", "padawan_trials_intro_msg");
    public static final string_id SID_PADAWAN_START_TRIALS_Q = new string_id("jedi_trials", "padawan_trials_start_query");
    public static final string_id SID_PADAWAN_NEXT_TRIAL = new string_id("jedi_trials", "padawan_trials_next_trial");
    public static final string_id SID_PADAWAN_TELL_ABOUT_RESTART = new string_id("jedi_trials", "padawan_trials_tell_about_restart");
    public static final string_id SID_PADAWAN_TRIAL_FAILED_FIRST = new string_id("jedi_trials", "padawan_trials_trial_failed_first");
    public static final string_id SID_PADAWAN_TRIAL_FAILED_SECOND = new string_id("jedi_trials", "padawan_trials_trial_failed_second");
    public static final string_id SID_PADAWAN_TRIAL_FAILED_FINAL = new string_id("jedi_trials", "padawan_trials_trial_failed_final");
    public static final string_id SID_PADAWAN_TRIALS_COMPLETED = new string_id("jedi_trials", "padawan_trials_completed");
    public static final string_id SID_PADAWAN_RETURN_TO_NPC = new string_id("jedi_trials", "padawan_trials_return_to_npc");
    public static final string_id SID_PADAWAN_RECEIVED_THE_RING = new string_id("jedi_trials", "padawan_trials_received_the_ring");
    public static final string_id SID_PADAWAN_TRIALS_PROGRESS = new string_id("jedi_trials", "padawan_trials_progress");
    public static final string_id SID_PADAWAN_TRIALS_NO_LONGER_ELIGIBLE = new string_id("jedi_trials", "padawan_trials_no_longer_eligible");
    public static final string_id SID_PADAWAN_TRIALS_STARTED_NOT_ELIGIBLE = new string_id("jedi_trials", "padawan_trials_started_not_eligible");
    public static final string_id SID_PADAWAN_TRIALS_BEING_REMOVED = new string_id("jedi_trials", "padawan_trials_being_removed");
    public static final string_id SID_PADAWAN_TRIALS_BEING_REMOVED_REVOKED = new string_id("jedi_trials", "padawan_trials_being_removed_revoked");
    public static final string_id SID_PADAWAN_TRIALS_ABORTED = new string_id("jedi_trials", "padawan_trials_aborted");
    public static final string_id SID_PADAWAN_TRIAL_RESTARTED = new string_id("jedi_trials", "padawan_trials_trial_restarted");
    public static final string_id SID_PADAWAN_ABORTED_CONFIRMATION = new string_id("jedi_trials", "padawan_trials_abort_confirmation");
    public static final string_id SID_PADAWAN_RESTART_CONFIRMATION = new string_id("jedi_trials", "padawan_trials_restart_confirmation");
    public static final string_id SID_KNIGHT_EMPTY = new string_id("jedi_trials", "empty");
    public static final string_id SID_FROM_UNKOWN = new string_id("jedi_trials", "from_unknown");
    public static final string_id SID_KNIGHT_INTRO_MSG = new string_id("jedi_trials", "knight_trials_intro_msg");
    public static final string_id SID_KNIGHT_INTRO_MSG_END = new string_id("jedi_trials", "knight_trials_intro_msg_end");
    public static final string_id SID_KNIGHT_START_TRIALS_Q = new string_id("jedi_trials", "knight_trials_start_query");
    public static final string_id SID_KNIGHT_TRIALS_TITLE = new string_id("jedi_trials", "knight_trials_title");
    public static final string_id SID_KNIGHT_CURRENT_TRIAL_COMPLETE = new string_id("jedi_trials", "knight_trials_current_trial_complete");
    public static final string_id SID_KNIGHT_NEXT_TRIAL_TITLE = new string_id("jedi_trials", "knight_trials_next_trial_title");
    public static final string_id SID_KNIGHT_NEXT_TRIAL_MSG = new string_id("jedi_trials", "knight_trials_next_trial_msg");
    public static final string_id SID_KNIGHT_NEXT_TRIAL_MSG_END = new string_id("jedi_trials", "knight_trials_next_trial_msg_end");
    public static final string_id SID_KNIGHT_TRIALS_PROGRESS = new string_id("jedi_trials", "knight_trials_progress");
    public static final string_id SID_KNIGHT_TRIALS_NO_LONGER_ELIGIBLE = new string_id("jedi_trials", "knight_trials_no_longer_eligible");
    public static final string_id SID_KNIGHT_TRIALS_BEING_REMOVED = new string_id("jedi_trials", "knight_trials_being_removed");
    public static final string_id SID_KNIGHT_TRIALS_COMPLETED_LIGHT = new string_id("jedi_trials", "knight_trials_completed_light");
    public static final string_id SID_KNIGHT_TRIALS_COMPLETED_DARK = new string_id("jedi_trials", "knight_trials_completed_dark");
    public static final string_id SID_FORCE_SHRINE_WRONG = new string_id("jedi_trials", "knight_shrine_wrong");
    public static final string_id SID_FORCE_SHRINE_REMIND = new string_id("jedi_trials", "knight_shrine_reminder");
    public static final string_id SID_FORCE_SHRINE_TITLE = new string_id("jedi_trials", "force_shrine_title");
    public static final string_id SID_COUNCIL_CHOICE_MSG = new string_id("jedi_trials", "council_choice_msg");
    public static final string_id SID_LIGHT_SIDE_CHOSEN = new string_id("jedi_trials", "council_chosen_light");
    public static final string_id SID_DARK_SIDE_CHOSEN = new string_id("jedi_trials", "council_chosen_dark");
    public static final string_id SID_DELAYED_COUNCIL_CHOICE = new string_id("jedi_trials", "council_choice_delayed");
    public static final string_id SID_FACTION_WRONG_CHOICE_LIGHT = new string_id("jedi_trials", "faction_wrong_choice_light");
    public static final string_id SID_FACTION_WRONG_CHOICE_DARK = new string_id("jedi_trials", "faction_wrong_choice_dark");
    public static final string_id SID_FACTION_WRONG_LIGHT = new string_id("jedi_trials", "faction_wrong_light");
    public static final string_id SID_FACTION_WRONG_DARK = new string_id("jedi_trials", "faction_wrong_dark");
    public static final string_id SID_OK_BUTTON = new string_id("jedi_trials", "button_okay");
    public static final string_id SID_CLOSE_BUTTON = new string_id("jedi_trials", "button_close");
    public static final string_id SID_NEXT_PAGE_BUTTON = new string_id("jedi_trials", "button_next_page");
    public static final string_id SID_CANCEL_BUTTON = new string_id("jedi_trials", "button_cancel");
    public static final string_id SID_YES_BUTTON = new string_id("jedi_trials", "button_yes");
    public static final string_id SID_NO_BUTTON = new string_id("jedi_trials", "button_no");
    public static final string_id SID_LIGHTSIDE_BUTTON = new string_id("jedi_trials", "button_lightside");
    public static final string_id SID_DARKSIDE_BUTTON = new string_id("jedi_trials", "button_darkside");
    public static final string_id SID_RESTART_BUTTON = new string_id("jedi_trials", "button_restart");
    public static final string_id SID_ABORT_BUTTON = new string_id("jedi_trials", "button_abort_padawan");
    public static final int PADAWAN_TRIALS_TREES_REQ = 6;
    public static final int KNIGHT_TRIALS_PTS_SPENT_REQ = 206;
    public static final int KNIGHT_TRIALS_TREES_REQ = 2;
    public static final String SUI_STARTING_SIZE = "500,250";
    public static final String PADAWAN_INITIATE_SKBOX = "force_title_jedi_rank_01";
    public static final String JEDI_PADAWAN_SKBOX = "force_title_jedi_rank_02";
    public static final String JEDI_KNIGHT_SKBOX = "force_title_jedi_rank_03";
    public static final String PADAWAN_ROBE_STRING = "item_jedi_robe_padawan_04_01";
    public static final String PADAWAN_TRIALS_LOG = "PADAWAN_TRIALS_LOG";
    public static final String KNIGHT_TRIALS_LOG = "KNIGHT_TRIALS_LOG";
    public static boolean isEligibleForJediPadawanTrials(obj_id player) throws InterruptedException
    {
        if (isIdValid(player))
        {
            if (hasObjVar(player, "overridePTEligibility"))
            {
                if (isGod(player))
                {
                    return true;
                }
            }
            if (!hasSkill(player, JEDI_PADAWAN_SKBOX))
            {
                int numFSBranchesComplete = fs_quests.getBranchesLearned(player);
                if (numFSBranchesComplete >= 6 && hasObjVar(player, PADAWAN_TRIALS_ELIGIBLE_OBJVAR))
                {
                    return true;
                }
            }
        }
        return false;
    }
    public static void initializePadawanTrials(obj_id player) throws InterruptedException
    {
        setObjVar(player, PADAWAN_TRIALS_ELIGIBLE_OBJVAR, true);
        if (!hasScript(player, PADAWAN_TRIALS_SCRIPT))
        {
            attachScript(player, PADAWAN_TRIALS_SCRIPT);
        }
        else 
        {
            messageTo(player, "handleDoPadawanTrialsSetup", null, 2, false);
        }
        return;
    }
    public static void doPadawanTrialsSetup(obj_id player) throws InterruptedException
    {
        if (isIdValid(player))
        {
            if (hasObjVar(player, JEDI_TRIALS_BASE_OBJVAR))
            {
                removeObjVar(player, JEDI_TRIALS_BASE_OBJVAR);
            }
            int[] questList = getQuestOrderFromDatatable(PADAWAN_TRIALS_DATATABLE);
            if (questList != null && questList.length > 0)
            {
                setClosestForceShrineWaypoint(player);
                setObjVar(player, PADAWAN_QUESTLIST_OBJVAR, questList);
                setObjVar(player, JEDI_TRIALS_TRIALNUMBER_OBJVAR, 0);
                String str_message = utils.packStringId(SID_PADAWAN_INTRO_MSG);
                oneButtonMsgBox(player, player, "noHandler", SID_PADAWAN_TRIALS_TITLE, str_message, SID_CLOSE_BUTTON);
                String custLogMsg = "Padawan trials (library.jedi_trials): Player %TU has begun the Jedi Padawan trials.";
                CustomerServiceLog(PADAWAN_TRIALS_LOG, custLogMsg, player);
                String questListSting = getJediTrialsQuestListAsString(player, questList);
                String custQuestListLogMsg = "Padawan trials quest list: Padawan trials quest list for Player %TU is: " + questListSting + ".";
                CustomerServiceLog(PADAWAN_TRIALS_LOG, custQuestListLogMsg, player);
            }
            else 
            {
                String custLogMsg = "Padawan trials (library.jedi_trials): FATAL - failed to generate questList - initialization aborted.";
                CustomerServiceLog(PADAWAN_TRIALS_LOG, custLogMsg, player);
            }
        }
        return;
    }
    public static boolean isEligibleForJediKnightTrials(obj_id player) throws InterruptedException
    {
        String frsConfig = getConfigSetting("GameServer", "enableFRS");
        if (frsConfig == null || frsConfig.length() < 1)
        {
            return false;
        }
        String darkRankSkill = force_rank.getForceRankSkill(0, force_rank.DARK_COUNCIL);
        String lightRankSkill = force_rank.getForceRankSkill(0, force_rank.LIGHT_COUNCIL);
        if (darkRankSkill != null && lightRankSkill != null)
        {
            if (!hasSkill(player, darkRankSkill) && !hasSkill(player, lightRankSkill))
            {
                return (isEligibleForJediKnightTrialsPointsRemaining(player) >= 0);
            }
        }
        return false;
    }
    public static int isEligibleForJediKnightTrialsPointsRemaining(obj_id player) throws InterruptedException
    {
        if (isIdValid(player))
        {
            String[] skillList = getSkillListingForPlayer(player);
            int jediSkillPtsSpent = 0;
            int jediDisciplineSkillTrees = 0;
            for (int j = 0; j < skillList.length; j++)
            {
                String skillName = skillList[j];
                if (skillName.startsWith("force_discipline"))
                {
                    int cost = 0;
                    jediSkillPtsSpent = jediSkillPtsSpent + cost;
                    if (skillName.endsWith("_04"))
                    {
                        jediDisciplineSkillTrees = jediDisciplineSkillTrees + 1;
                    }
                }
            }
            if (jediSkillPtsSpent >= KNIGHT_TRIALS_PTS_SPENT_REQ && jediDisciplineSkillTrees >= KNIGHT_TRIALS_TREES_REQ)
            {
                return (jediSkillPtsSpent - KNIGHT_TRIALS_PTS_SPENT_REQ);
            }
        }
        return -1;
    }
    public static void initializeKnightTrials(obj_id player) throws InterruptedException
    {
        int[] questList = getQuestOrderFromDatatable(KNIGHT_TRIALS_DATATABLE);
        if (questList != null && questList.length > 0)
        {
            location forceShrineLoc = getRandomForceShrineLocation(player);
            setObjVar(player, KNIGHT_QUESTLIST_OBJVAR, questList);
            setObjVar(player, JEDI_TRIALS_TRIALNUMBER_OBJVAR, 0);
            setObjVar(player, JEDI_TRIALS_SHRINELOC_OBJVAR, forceShrineLoc);
            String str_msgFirstPart = utils.packStringId(SID_KNIGHT_INTRO_MSG);
            String str_msgSecondPart = utils.packStringId(new string_id("jedi_trials", forceShrineLoc.area));
            String str_msgThirdPart = utils.packStringId(SID_KNIGHT_INTRO_MSG_END);
            String str_message = str_msgFirstPart + " " + str_msgSecondPart + " " + str_msgThirdPart;
            oneButtonMsgBox(player, player, "noHandler", SID_KNIGHT_TRIALS_TITLE, str_message, SID_CLOSE_BUTTON);
            String custLogMsg = "Knight trials initialization (library.jedi_trials): Player %TU has begun the Jedi Knight trials.";
            CustomerServiceLog(KNIGHT_TRIALS_LOG, custLogMsg, player);
        }
        else 
        {
            String custLogMsg = "Knight trials initialization (library.jedi_trials): FATAL - failed to generate questList - initialization aborted.";
            CustomerServiceLog(KNIGHT_TRIALS_LOG, custLogMsg, player);
        }
        return;
    }
    public static void doJediTrialsCleanup(obj_id player) throws InterruptedException
    {
        removeObjVar(player, JEDI_TRIALS_BASE_OBJVAR);
        if (hasObjVar(player, "handlePlayerCombatKill"))
        {
            removeObjVar(player, "handlePlayerCombatKill");
        }
        if (hasScript(player, PADAWAN_TRIALS_SCRIPT))
        {
            detachScript(player, PADAWAN_TRIALS_SCRIPT);
        }
        else if (hasScript(player, KNIGHT_TRIALS_SCRIPT))
        {
            detachScript(player, KNIGHT_TRIALS_SCRIPT);
        }
        return;
    }
    public static int getJediTrialRow(obj_id player) throws InterruptedException
    {
        int[] questList = getIntArrayObjVar(player, PADAWAN_QUESTLIST_OBJVAR);
        if (questList == null || questList.length < 1)
        {
            return -1;
        }
        int trialNum = getIntObjVar(player, JEDI_TRIALS_TRIALNUMBER_OBJVAR);
        int datatableRow = questList[trialNum];
        return datatableRow;
    }
    public static String getJediTrialName(obj_id player) throws InterruptedException
    {
        int trialRow = getJediTrialRow(player);
        if (trialRow < 0)
        {
            return null;
        }
        String trialName = dataTableGetString(PADAWAN_TRIALS_DATATABLE, trialRow, "trialName");
        return trialName;
    }
    public static location getRandomForceShrineLocation(obj_id player) throws InterruptedException
    {
        String shrinePlanet = chooseRandomPlanet(player);
        location shrineLoc = chooseForceShrineOnPlanet(shrinePlanet);
        return shrineLoc;
    }
    public static String chooseRandomPlanet(obj_id player) throws InterruptedException
    {
        String planet = "";
        String[] allPlanets = 
        {
            "rori",
            "talus",
            "tatooine",
            "naboo",
            "corellia",
            "yavin4",
            "dathomir",
            "dantooine",
            "endor",
            "lok"
        };
        location playerLoc = getLocation(player);
        if (playerLoc != null)
        {
            String playerPlanet = playerLoc.area;
            while (planet.equals(""))
            {
                int foo = rand(0, (allPlanets.length - 1));
                String randomPlanet = allPlanets[foo];
                if (!randomPlanet.equals(playerPlanet))
                {
                    planet = randomPlanet;
                }
            }
        }
        return planet;
    }
    public static String chooseRandomCivilizedPlanet(obj_id player) throws InterruptedException
    {
        String planet = "";
        String[] allPlanets = 
        {
            "rori",
            "talus",
            "tatooine",
            "naboo",
            "corellia"
        };
        location playerLoc = getLocation(player);
        if (playerLoc != null)
        {
            String playerPlanet = playerLoc.area;
            while (planet.equals(""))
            {
                int foo = rand(0, (allPlanets.length - 1));
                String randomPlanet = allPlanets[foo];
                if (!randomPlanet.equals(playerPlanet))
                {
                    planet = randomPlanet;
                }
            }
        }
        return planet;
    }
    public static String chooseRandomAdventurePlanet(obj_id player) throws InterruptedException
    {
        String planet = "";
        String[] allPlanets = 
        {
            "yavin4",
            "dathomir",
            "dantooine",
            "endor",
            "lok"
        };
        location playerLoc = getLocation(player);
        if (playerLoc != null)
        {
            String playerPlanet = playerLoc.area;
            while (planet.equals(""))
            {
                int foo = rand(0, (allPlanets.length - 1));
                String randomPlanet = allPlanets[foo];
                if (!randomPlanet.equals(playerPlanet))
                {
                    planet = randomPlanet;
                }
            }
        }
        return planet;
    }
    public static void setClosestForceShrineWaypoint(obj_id player) throws InterruptedException
    {
        if (hasObjVar(player, PADAWAN_SHRINE_WAYPOINT_OBJVAR))
        {
            obj_id oldWaypoint = getObjIdObjVar(player, PADAWAN_SHRINE_WAYPOINT_OBJVAR);
            removeObjVar(player, PADAWAN_SHRINE_WAYPOINT_OBJVAR);
            if (isIdValid(oldWaypoint))
            {
                destroyWaypointInDatapad(oldWaypoint, player);
            }
        }
        location closestShrineLoc = getclosetForceShrineLocation(player);
        location here = getLocation(player);
        if (utils.getDistance2D(closestShrineLoc, here) > 20)
        {
            obj_id waypoint = createWaypointInDatapad(player, closestShrineLoc);
            string_id sid_planet = new string_id("jedi_trials", here.area);
            String str_planet = getString(sid_planet);
            String waypointName = str_planet + " " + " Force Shrine";
            setWaypointName(waypoint, waypointName);
            setWaypointActive(waypoint, true);
            setObjVar(player, PADAWAN_SHRINE_WAYPOINT_OBJVAR, waypoint);
        }
        return;
    }
    public static location getclosetForceShrineLocation(obj_id player) throws InterruptedException
    {
        location here = getLocation(player);
        String planet = here.area;
        location shrineLoc = null;
        int index = getPlanetForceShrineDatatableIndex(planet);
        if (index != -1)
        {
            int numShrines = dataTableGetInt(FORCE_SHRINES_DATATABLE, index, "numShrines");
            float closestDistance = 99999;
            for (int i = 1; i <= numShrines; i++)
            {
                int shrinedex = index + i;
                float locX = dataTableGetFloat(FORCE_SHRINES_DATATABLE, shrinedex, "locX");
                float locY = dataTableGetFloat(FORCE_SHRINES_DATATABLE, shrinedex, "locY");
                float locZ = dataTableGetFloat(FORCE_SHRINES_DATATABLE, shrinedex, "locZ");
                location tempShrineLoc = new location(locX, locY, locZ, planet, null);
                float shrineDistance = utils.getDistance2D(tempShrineLoc, here);
                if (shrineDistance < closestDistance)
                {
                    closestDistance = shrineDistance;
                    shrineLoc = tempShrineLoc;
                }
            }
        }
        return shrineLoc;
    }
    public static location chooseForceShrineOnPlanet(String planet) throws InterruptedException
    {
        location shrineLoc = new location(3087.47f, 124.22f, 4887.14f, "dathomir", null);
        int index = getPlanetForceShrineDatatableIndex(planet);
        if (index != -1)
        {
            int numShrines = dataTableGetInt(FORCE_SHRINES_DATATABLE, index, "numShrines");
            int shrinedex = index + rand(1, numShrines);
            float locX = dataTableGetFloat(FORCE_SHRINES_DATATABLE, shrinedex, "locX");
            float locY = dataTableGetFloat(FORCE_SHRINES_DATATABLE, shrinedex, "locY");
            float locZ = dataTableGetFloat(FORCE_SHRINES_DATATABLE, shrinedex, "locZ");
            shrineLoc = new location(locX, locY, locZ, planet, null);
        }
        return shrineLoc;
    }
    public static int getPlanetForceShrineDatatableIndex(String planet) throws InterruptedException
    {
        int numItems = dataTableGetNumRows(FORCE_SHRINES_DATATABLE);
        int index = -1;
        for (int i = 0; i < numItems; i++)
        {
            String areaName = dataTableGetString(FORCE_SHRINES_DATATABLE, i, "planet");
            if (areaName.equals(planet))
            {
                index = i;
                break;
            }
        }
        return index;
    }
    public static boolean isValidShrineLocObjVar(obj_id player) throws InterruptedException
    {
        location currentShrineLoc = getLocationObjVar(player, JEDI_TRIALS_SHRINELOC_OBJVAR);
        if (currentShrineLoc == null)
        {
            return false;
        }
        String shrinePlanet = currentShrineLoc.area;
        float curX = currentShrineLoc.x;
        float curY = currentShrineLoc.y;
        float curZ = currentShrineLoc.z;
        int planetIndex = getPlanetForceShrineDatatableIndex(shrinePlanet);
        int numShrines = dataTableGetInt(FORCE_SHRINES_DATATABLE, planetIndex, "numShrines");
        for (int i = 1; i <= numShrines; i++)
        {
            float locX = dataTableGetFloat(FORCE_SHRINES_DATATABLE, (planetIndex + i), "locX");
            float locY = dataTableGetFloat(FORCE_SHRINES_DATATABLE, (planetIndex + i), "locY");
            float locZ = dataTableGetFloat(FORCE_SHRINES_DATATABLE, (planetIndex + i), "locZ");
            if (curX == locX && curY == locY && curZ == locZ)
            {
                return true;
            }
        }
        return false;
    }
    public static int[] getQuestOrderFromDatatable(String datatable) throws InterruptedException
    {
        int numTrials = dataTableGetNumRows(datatable);
        int[] questList = new int[numTrials];
        for (int q = 0; q < questList.length; q++)
        {
            questList[q] = -1;
        }
        Vector unusedRows = new Vector();
        unusedRows.setSize(0);
        for (int datatableRow = 0; datatableRow < numTrials; datatableRow++)
        {
            int questNum = dataTableGetInt(datatable, datatableRow, "trialNum");
            if (questNum != -1)
            {
                questList[questNum] = datatableRow;
            }
            else 
            {
                utils.addElement(unusedRows, datatableRow);
            }
        }
        for (int questNum = 0; questNum < numTrials; questNum++)
        {
            int foo = questList[questNum];
            if (foo == -1)
            {
                int index = rand(0, (unusedRows.size() - 1));
                int datatableRow = ((Integer)unusedRows.get(index)).intValue();
                questList[questNum] = datatableRow;
                utils.removeElementAt(unusedRows, index);
            }
        }
        return questList;
    }
    public static String getJediTrialsQuestListAsString(obj_id player, int[] questList) throws InterruptedException
    {
        String questListString = "{ ";
        for (int i = 0; i < questList.length; i++)
        {
            int questNum = questList[i];
            if (i > 0)
            {
                questListString = questListString + ", ";
            }
            questListString = questListString + questNum;
        }
        questListString = questListString + " }";
        return questListString;
    }
    public static String getJediTrialsDatatableString(obj_id player, String column) throws InterruptedException
    {
        String jediTrialString = "";
        String datatable = "";
        String trialObjVar = "";
        if (hasObjVar(player, PADAWAN_QUESTLIST_OBJVAR))
        {
            datatable = PADAWAN_TRIALS_DATATABLE;
            trialObjVar = PADAWAN_QUESTLIST_OBJVAR;
        }
        else if (hasObjVar(player, KNIGHT_QUESTLIST_OBJVAR))
        {
            datatable = KNIGHT_TRIALS_DATATABLE;
            trialObjVar = KNIGHT_QUESTLIST_OBJVAR;
        }
        if (!datatable.equals("") && !trialObjVar.equals(""))
        {
            int[] questList = getIntArrayObjVar(player, trialObjVar);
            if (questList != null && questList.length > 0)
            {
                int trialNum = getIntObjVar(player, JEDI_TRIALS_TRIALNUMBER_OBJVAR);
                int datatableRow = questList[trialNum];
                jediTrialString = dataTableGetString(datatable, datatableRow, column);
            }
        }
        return jediTrialString;
    }
    public static int getJediTrialsDatatableInt(obj_id player, String column) throws InterruptedException
    {
        int jediTrialInt = Integer.MIN_VALUE;
        String datatable = "";
        String trialObjVar = "";
        if (hasObjVar(player, PADAWAN_QUESTLIST_OBJVAR))
        {
            datatable = PADAWAN_TRIALS_DATATABLE;
            trialObjVar = PADAWAN_QUESTLIST_OBJVAR;
        }
        else if (hasObjVar(player, KNIGHT_QUESTLIST_OBJVAR))
        {
            datatable = KNIGHT_TRIALS_DATATABLE;
            trialObjVar = KNIGHT_QUESTLIST_OBJVAR;
        }
        if (!datatable.equals("") && !trialObjVar.equals(""))
        {
            int[] questList = getIntArrayObjVar(player, trialObjVar);
            if (questList != null && questList.length > 0)
            {
                int trialNum = getIntObjVar(player, JEDI_TRIALS_TRIALNUMBER_OBJVAR);
                int datatableRow = questList[trialNum];
                jediTrialInt = dataTableGetInt(datatable, datatableRow, column);
            }
        }
        return jediTrialInt;
    }
    public static void giveGenericForceShrineMessage(obj_id player) throws InterruptedException
    {
        int chance = rand(1, 15);
        String str_reference = "force_shrine_wisdom_" + chance;
        string_id sid_msg = new string_id("jedi_trials", str_reference);
        sendSystemMessage(player, sid_msg);
        return;
    }
    public static boolean checkForGroupCombatQuestKill(obj_id player, dictionary params, String datatableColumn) throws InterruptedException
    {
        obj_id creature = params.getObjId("target");
        if (isIdValid(creature))
        {
            obj_id topGroup = getObjIdObjVar(creature, xp.VAR_TOP_GROUP);
            obj_id[] killers = null;
            if (isIdValid(topGroup))
            {
                if (group.isGroupObject(topGroup))
                {
                    killers = getGroupMemberIds(topGroup);
                }
                else 
                {
                    killers = new obj_id[1];
                    killers[0] = topGroup;
                }
                if ((killers == null) || (killers.length == 0))
                {
                    return false;
                }
                if (utils.getElementPositionInArray(killers, player) > -1)
                {
                    String creatureType = ai_lib.getCreatureName(creature);
                    if (creatureType == null || creatureType.equals(""))
                    {
                        return false;
                    }
                    String[] validTargets = getValidQuestTargets(player, datatableColumn);
                    if (validTargets != null)
                    {
                        for (int i = 0; i < validTargets.length; i++)
                        {
                            String targetName = validTargets[i];
                            int stringCheck = creatureType.indexOf(targetName);
                            if (stringCheck > -1)
                            {
                                return true;
                            }
                        }
                    }
                }
            }
        }
        return false;
    }
    public static boolean checkForIndividualCombatQuestKill(obj_id player, dictionary params, String datatableColumn) throws InterruptedException
    {
        obj_id creature = params.getObjId("target");
        if (isIdValid(creature))
        {
            obj_id primaryKiller = getObjIdObjVar(creature, xp.VAR_TOP_GROUP);
            obj_id landedDeathBlow = getObjIdObjVar(creature, xp.VAR_LANDED_DEATHBLOW);
            obj_id[] topDamagers = getObjIdArrayObjVar(creature, xp.VAR_TOP_DAMAGERS);
            if (isIdValid(primaryKiller) && isIdValid(landedDeathBlow) && topDamagers != null)
            {
                if (primaryKiller == player || landedDeathBlow == player || utils.isObjIdInArray(topDamagers, player))
                {
                    String creatureType = ai_lib.getCreatureName(creature);
                    if (creatureType == null || creatureType.equals(""))
                    {
                        return false;
                    }
                    String[] validTargets = getValidQuestTargets(player, datatableColumn);
                    if (validTargets != null)
                    {
                        for (int i = 0; i < validTargets.length; i++)
                        {
                            String targetName = validTargets[i];
                            int stringCheck = creatureType.indexOf(targetName);
                            if (stringCheck > -1)
                            {
                                return true;
                            }
                        }
                    }
                }
            }
        }
        return false;
    }
    public static String[] getValidQuestTargets(obj_id player, String datatableColumn) throws InterruptedException
    {
        String targets = getJediTrialsDatatableString(player, datatableColumn);
        if (targets == null || targets.equals("") || targets.equals("null"))
        {
            return null;
        }
        java.util.StringTokenizer foo = new java.util.StringTokenizer(targets, ";");
        int numTokens = foo.countTokens();
        String[] validTargets = new String[numTokens];
        int j = 0;
        while (foo.hasMoreTokens())
        {
            String temp = foo.nextToken();
            validTargets[j] = temp;
            j++;
        }
        return validTargets;
    }
    public static int oneButtonMsgBox(obj_id controller, obj_id player, String handler, string_id title, string_id textMsg, string_id okButton) throws InterruptedException
    {
        String newTextMsg = utils.packStringId(textMsg);
        return oneButtonMsgBox(controller, player, handler, title, newTextMsg, okButton);
    }
    public static int oneButtonMsgBox(obj_id controller, obj_id player, String handler, string_id title, String textMsg, string_id okButton) throws InterruptedException
    {
        if (utils.hasScriptVar(player, "jedi_trials.openSui"))
        {
            int oldSui = utils.getIntScriptVar(player, "jedi_trials.openSui");
            utils.removeScriptVar(player, "jedi_trials.openSui");
            if (oldSui > -1)
            {
                forceCloseSUIPage(oldSui);
            }
        }
        String TITLE_TXT = utils.packStringId(title);
        String OK_BUTTON = utils.packStringId(okButton);
        int pid = sui.createSUIPage(sui.SUI_MSGBOX, controller, player, handler);
        setSUIProperty(pid, "", "Location", "500,75");
        setSUIProperty(pid, "", "Size", SUI_STARTING_SIZE);
        setSUIProperty(pid, sui.MSGBOX_TITLE, sui.PROP_TEXT, TITLE_TXT);
        setSUIProperty(pid, sui.MSGBOX_PROMPT, sui.PROP_TEXT, textMsg);
        sui.msgboxButtonSetup(pid, sui.OK_ONLY);
        setSUIProperty(pid, sui.MSGBOX_BTN_OK, sui.PROP_TEXT, OK_BUTTON);
        utils.setScriptVar(player, "jedi_trials.openSui", pid);
        sui.showSUIPage(pid);
        return pid;
    }
    public static int twoButtonMsgBox(obj_id controller, obj_id player, String handler, string_id title, String textMsg, string_id okButton, string_id cancelButton) throws InterruptedException
    {
        if (utils.hasScriptVar(player, "jedi_trials.openSui"))
        {
            int oldSui = utils.getIntScriptVar(player, "jedi_trials.openSui");
            utils.removeScriptVar(player, "jedi_trials.openSui");
            if (oldSui > -1)
            {
                forceCloseSUIPage(oldSui);
            }
        }
        String TITLE_TXT = utils.packStringId(title);
        String OK_BUTTON = utils.packStringId(okButton);
        String CANCEL_BUTTON = utils.packStringId(cancelButton);
        int pid = sui.createSUIPage(sui.SUI_MSGBOX, controller, player, handler);
        setSUIProperty(pid, "", "Location", "500,75");
        setSUIProperty(pid, "", "Size", SUI_STARTING_SIZE);
        setSUIProperty(pid, sui.MSGBOX_TITLE, sui.PROP_TEXT, TITLE_TXT);
        setSUIProperty(pid, sui.MSGBOX_PROMPT, sui.PROP_TEXT, textMsg);
        sui.msgboxButtonSetup(pid, sui.YES_NO);
        setSUIProperty(pid, sui.MSGBOX_BTN_OK, sui.PROP_TEXT, OK_BUTTON);
        setSUIProperty(pid, sui.MSGBOX_BTN_CANCEL, sui.PROP_TEXT, CANCEL_BUTTON);
        utils.setScriptVar(player, "jedi_trials.openSui", pid);
        sui.showSUIPage(pid);
        return pid;
    }
    public static int threeButtonMsgBox(obj_id controller, obj_id player, String handler, string_id title, string_id textMsg, string_id okButton, string_id cancelButton, string_id revertButton) throws InterruptedException
    {
        String TEXT_MSG = utils.packStringId(textMsg);
        return threeButtonMsgBox(controller, player, handler, title, TEXT_MSG, okButton, cancelButton, revertButton);
    }
    public static int threeButtonMsgBox(obj_id controller, obj_id player, String handler, string_id title, String textMsg, string_id okButton, string_id cancelButton, string_id revertButton) throws InterruptedException
    {
        if (utils.hasScriptVar(player, "jedi_trials.openSui"))
        {
            int oldSui = utils.getIntScriptVar(player, "jedi_trials.openSui");
            utils.removeScriptVar(player, "jedi_trials.openSui");
            if (oldSui > -1)
            {
                forceCloseSUIPage(oldSui);
            }
        }
        String TITLE_TXT = utils.packStringId(title);
        String OK_BUTTON = utils.packStringId(okButton);
        String CANCEL_BUTTON = utils.packStringId(cancelButton);
        String REVERT_BUTTON = utils.packStringId(revertButton);
        int pid = sui.createSUIPage(sui.SUI_MSGBOX, controller, player, handler);
        setSUIProperty(pid, "", "Location", "500,75");
        setSUIProperty(pid, "", "Size", SUI_STARTING_SIZE);
        setSUIProperty(pid, sui.MSGBOX_TITLE, sui.PROP_TEXT, TITLE_TXT);
        setSUIProperty(pid, sui.MSGBOX_PROMPT, sui.PROP_TEXT, textMsg);
        sui.msgboxButtonSetup(pid, sui.YES_NO_CANCEL);
        setSUIProperty(pid, sui.MSGBOX_BTN_REVERT, sui.PROP_TEXT, REVERT_BUTTON);
        setSUIProperty(pid, sui.MSGBOX_BTN_OK, sui.PROP_TEXT, OK_BUTTON);
        setSUIProperty(pid, sui.MSGBOX_BTN_CANCEL, sui.PROP_TEXT, CANCEL_BUTTON);
        setSUIProperty(pid, sui.MSGBOX_BTN_REVERT, "OnPress", "RevertWasPressed=1\r\nparent.btnOk.press=t");
        subscribeToSUIProperty(pid, sui.MSGBOX_BTN_REVERT, "RevertWasPressed");
        utils.setScriptVar(player, "jedi_trials.openSui", pid);
        sui.showSUIPage(pid);
        return pid;
    }
}
