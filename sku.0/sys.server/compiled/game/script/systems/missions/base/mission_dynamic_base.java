package script.systems.missions.base;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.regions;
import script.library.utils;
import script.library.battlefield;
import script.library.create;
import script.library.ai_lib;
import script.library.locations;
import script.library.money;
import script.library.create;
import script.library.chat;
import script.library.ai_lib;
import script.library.anims;
import script.library.jedi;
import script.library.factions;
import script.library.missions;
import script.library.group;

public class mission_dynamic_base extends script.systems.missions.base.mission_base
{
    public mission_dynamic_base()
    {
    }
    public static final float MIN_DESTROY_DISTANCE = 350f;
    public static final int FACTION_DELIVER_REWARD_VALUE = 150;
    public static final int FACTION_REWARD_VALUE = 100;
    public static final int INFORMANT_EASY = 1;
    public static final int INFORMANT_MEDIUM = 2;
    public static final int INFORMANT_HARD = 3;
    public static final int MAX_SPAWN_ATTEMPTS = 3;
    public static final int MIN_DISTANCE_FOR_RETRY = 400;
    public static final int MAX_DISTANCE_FOR_RETRY = 600;
    public static final int DESTRUCTION_REWARD_MODIFIER = 100;
    public static final int BOUNTY_REWARD_MODIFIER = 150;
    public static final int MISSION_DIFFICULTY_EASY = 1;
    public static final int MISSION_DIFFICULTY_MEDIUM = 2;
    public static final int MISSION_DIFFICULTY_HARD = 3;
    public static final int MISSION_BOUNTY_DIFFICULTY_JEDI = 4;
    public static final int MISSION_DIFFICULTY_BOSS = 5;
    public static final int STRING_ID_QUANTITY = 5;
    public static final String[] PLAYER_SPECIES = 
    {
        "wookiee",
        "human",
        "bothan",
        "trandoshan",
        "zabrak",
        "rodian",
        "moncal",
        "twilek"
    };
    public static final float DELIVER_DIFFICULTY_MODIFIER = .5f;
    public static final float FETCH_DIFFICULTY_MODIFIER = .5f;
    public static final float ESCORT_DIFFICULTY_MODIFIER = .5f;
    public static final float MULTI_PLANET_MODIFIER = 4f;
    public static final float RANGE_MODIFIER = 25f;
    public static final float MIN_DESTRUCTION_DISTANCE = 0f;
    public static final float MAX_DESTRUCTION_DISTANCE = 4000f;
    public static final int LOCATION_CHECKS = 10;
    public static final float DEFAULT_OBJECTIVE_SIZE = 30f;
    public static final float PLAYER_INTEREST_RANGE = 200f;
    public static final float ITEM_CHECK_RANGE = 25f;
    public static final float MIN_REWARD_VARIANCE = -10;
    public static final float MAX_REWARD_VARIANCE = 10;
    public static final String SUCCESS_MESSAGE_TYPE = "s";
    public static final String FAILURE_MESSAGE_TYPE = "f";
    public static final String DESCRIPTION_MESSAGE_TYPE = "d";
    public static final String INCOMPLETE_MESSAGE_TYPE = "t";
    public static final int MISSION_EXPIRE_TIME = 24 * 60 * 60 * 2;
    public static final int MISSION_DELIVER_DIFFICULTY_EASY = 10;
    public static final int MISSION_DELIVER_DIFFICULTY_MEDIUM = 18;
    public static final int MISSION_DELIVER_DIFFICULTY_HARD = 25;
    public static final int MIN_EASY_DELIVER_DISTANCE = 100;
    public static final int MAX_EASY_DELIVER_DISTANCE = 900;
    public static final int MISSION_DESTROY_DIFFICULTY_EASY = 12;
    public static final int MISSION_DESTROY_DIFFICULTY_MEDIUM = 24;
    public static final int MISSION_DESTROY_DIFFICULTY_HARD = 30;
    public static final String[] CITY_PLANETS = 
    {
        "tatooine",
        "naboo",
        "talus",
        "corellia",
        "yavin4",
        "dantooine",
        "dathomir",
        "lok",
        "rori",
        "endor"
    };
    public static final String[] PLANETS = 
    {
        "tatooine",
        "naboo",
        "talus",
        "corellia",
        "yavin4",
        "dantooine",
        "dathomir",
        "lok",
        "rori",
        "endor"
    };
    public static final int DELIVERY_INTEREST_MIN_DELAY = 15;
    public static final int DELIVERY_INTEREST_MAX_DELAY = 30;
    public static final float DELIVER_REWARD_RATIO = 7;
    public static final float RECON_REWARD_RATIO = 6;
    public static final float CRAFTING_REWARD_RATIO = 4;
    public static final String[] NPC_NONPERSISTENT_MISSION_OBJVAR_NAMES = 
    {
        "mission.objDestroyMission",
        "mission.objDeliverMission",
        "mission.objSurveyMission",
        "mission.objDancerMission",
        "mission.objMusicianMission",
        "mission.objCraftingMission",
        "mission.objSurveyMission",
        "mission.objDancerMission",
        "mission.objMusicianMission",
        "mission.objCraftingMission",
        "mission.objHuntingMission"
    };
    public static final String[] NPC_PERSISTENT_MISSION_OBJVAR_NAMES = 
    {
        "mission.objDestroyMission",
        "mission.objDeliverMission",
        "mission.objReconMission"
    };
    public static final float MIN_RECON_DISTANCE = 0f;
    public static final float MAX_RECON_DISTANCE = 5000f;
    public static final float RECON_EASY_DISTANCE = 1000;
    public static final float RECON_MEDIUM_DISTANCE = 2000;
    public static final float RECON_HARD_DISTANCE = 5000;
    public static final int RECON_OFF_PLANET_THRESHOLD = 100000;
    public static final String[] RECON_PLANETS = 
    {
        "tatooine",
        "naboo"
    };
    public static final int[] RECON_DIFFICULTY_MODIFIER = 
    {
        1,
        1
    };
    public static final float RECON_OFF_PLANET_BASE_MODIFIER = 2;
    public static final int ESCORT_DIFFICULTY_EASY = 5;
    public static final int ESCORT_DIFFICULTY_MEDIUM = 8;
    public static final int ESCORT_DIFFICULTY_HARD = 20;
    public static final String[] NPC_TYPES = 
    {
        "artisan",
        "bodyguard",
        "businessman",
        "bothan_diplomat",
        "entertainer",
        "explorer",
        "farmer",
        "gambler",
        "info_broker",
        "miner",
        "medic",
        "noble",
        "pilot",
        "scientist"
    };
    public static final String OBJVAR_SURVEY_MISSION_EFFECIENCY = "intEffeciency";
    public static final String OBJVAR_SURVEY_MISSION_RESOURCE = "strResource";
    public static final String SID_SURVEY_RESOURCE_NAMES_TABLE = "mission/survey/survey_resource_names";
    public static final string_id SID_MISSION_TARGET_UPDATED = new string_id("mission/mission_generic", "mission_target_updated");
    public static final string_id SID_MISSION_TARGET_UPDATED_EXACT = new string_id("mission/mission_generic", "mission_target_updated_exact");
    public static final string_id SID_MISSION_TATOOINE = new string_id("spam", "mission_wrong_planet_tatooine");
    public static final string_id SID_MISSION_TATOOINE_NABOO_CORELIA = new string_id("spam", "mission_wrong_planet_tatooine_naboo_corelia");
    public static final string_id SID_MISSION_NABOO_CORELIA = new string_id("spam", "mission_wrong_planet_naboo_corelia");
    public static final string_id SID_MISSION_RORI_TALUS = new string_id("spam", "mission_wrong_planet_rori_talus");
    public static final string_id SID_MISSION_RORI_TALUS_DANTOOINE_LOK = new string_id("spam", "mission_wrong_planet_rori_talus_dantooine_lok");
    public static final string_id SID_MISSION_DANTOOINE_LOK = new string_id("spam", "mission_wrong_planet_dantooine_lok");
    public static final string_id SID_MISSION_YAVIN_ENDOR = new string_id("spam", "mission_wrong_planet_yavin_endor");
    public static final string_id SID_MISSION_YAVIN_ENDOR_DATHOMIR = new string_id("spam", "mission_wrong_planet_yavin_endor_dathomir");
    public static final string_id SID_MISSION_TARGET_IN_WATER = new string_id("spam", "target_in_water");
    public static final String[] CITIES_WITH_STARPORTS = 
    {
        "bestine",
        "mos_eisley",
        "mos_entha",
        "mos_espa",
        "theed",
        "keren",
        "moenia",
        "kaadara",
        "coronet",
        "tyrena",
        "kor_vella",
        "doaba_guerfel",
        "restuss",
        "narmle",
        "lok_nyms_stronghold",
        "yavin4_labor_outpost",
        "yavin4_mining_outpost",
        "endor_neutral_outpost",
        "dantooine_mining_outpost",
        "dantooine_pirate_outpost",
        "dathomir_trade_outpost",
        "dathomir_survey_outpost"
    };
    public static final float PREFERRED_DISTANCE = 1000.0f;
    public obj_id createMissionDataInContainer(obj_id objCreator, float fltEffeciencyRequirement, int intExpireTime, obj_id objCreatorToken, String strMissionType, String strScript) throws InterruptedException
    {
        obj_id objContainer;
        if (isPlayer(objCreator))
        {
            objContainer = utils.getMissionBag(objCreator);
        }
        else if (isMob(objCreator))
        {
            objContainer = utils.getMissionBag(objCreator);
        }
        else 
        {
            objContainer = objCreator;
        }
        if (!isIdValid(objContainer))
        {
            return null;
        }
        obj_id objMissionData = createObject("object/mission/base_mission_data.iff", objContainer, "");
        if (!isIdValid(objMissionData))
        {
            obj_id[] objContents = utils.getContents(objContainer);
            for (int intI = 0; intI < objContents.length; intI++)
            {
                destroyObject(objContents[intI]);
            }
            return null;
        }
        setMissionType(objMissionData, strMissionType);
        setMissionRootScriptName(objMissionData, strScript);
        return objMissionData;
    }
    public obj_id getMissionData(obj_id objMission) throws InterruptedException
    {
        return objMission;
    }
    public obj_id createDestructionMissionDataFromLocation(obj_id objMissionData, obj_id objCreator, location locMissionStart, int intDifficulty, String strPlanet, String strFaction, float fltRewardModifier) throws InterruptedException
    {
        LOG("missions", "Making destroy mission");
        setMissionRootScriptName(objMissionData, "systems.missions.dynamic.mission_destruction");
        setMissionType(objMissionData, "destroy");
        int intI = 0;
        String strSpawnType = "";
        region rgnSpawnRegion = null;
        location locMissionDestination = new location();
        float fltRange;
        location locGoodLocation = new location();
        region[] rgnCities = getRegionsWithMunicipalAtPoint(locMissionStart, regions.MUNI_TRUE);
        if (rgnCities == null)
        {
            rgnCities = getRegionsWithGeographicalAtPoint(locMissionStart, regions.GEO_CITY);
        }
        for (int i = 0; i < LOCATION_CHECKS; i++)
        {
            if (intDifficulty < 7)
            {
                fltRange = intDifficulty * RANGE_MODIFIER;
            }
            else 
            {
                fltRange = rand(500, 1500);
            }
            if (rgnCities != null)
            {
                locGoodLocation = locations.getGoodLocationOutsideOfRegion(rgnCities[0], DEFAULT_OBJECTIVE_SIZE, DEFAULT_OBJECTIVE_SIZE, fltRange, false, true);
            }
            else 
            {
                location locNewLocation = utils.getRandomLocationInRing(locMissionStart, fltRange, fltRange + 200);
                locGoodLocation = locations.getGoodLocationAroundLocation(locNewLocation, 30, 30, 150, 150, false, true);
            }
            if (locGoodLocation != null)
            {
                float fltDistance = getDistance(locMissionStart, locGoodLocation);
                if (fltDistance < MIN_DESTROY_DISTANCE)
                {
                    fltRange = MIN_DESTROY_DISTANCE + rand(20, 100);
                    location locNewLocation = utils.getRandomLocationInRing(locMissionStart, fltRange, fltRange + 200);
                    locGoodLocation = locations.getGoodLocationAroundLocation(locNewLocation, 30, 30, 150, 150, false, true);
                }
            }
            if (locGoodLocation != null)
            {
                locMissionDestination = (location)locGoodLocation.clone();
                rgnCities = getRegionsWithMunicipalAtPoint(locMissionDestination, regions.MUNI_TRUE);
                region[] rgnCities2 = getRegionsWithGeographicalAtPoint(locMissionDestination, regions.GEO_CITY);
                if (rgnCities == null && rgnCities2 == null)
                {
                    region rgnBattlefield = battlefield.getBattlefield(locMissionDestination);
                    if (rgnBattlefield == null)
                    {
                        region[] rgnMissionListing = getRegionsWithMissionAtPoint(locMissionDestination, regions.MISSION_OTHER);
                        if (rgnMissionListing != null && rgnMissionListing.length != 0)
                        {
                            rgnSpawnRegion = locations.getSmallestRegion(rgnMissionListing);
                            strSpawnType = regions.translateGeoToString(rgnSpawnRegion.getGeographicalType());
                        }
                        else 
                        {
                            strSpawnType = "default";
                        }
                        break;
                    }
                }
            }
        }
        if (locGoodLocation == null)
        {
            LOG("missions", "Returning null due to good location failure");
            return null;
        }
        if (strSpawnType == null || strSpawnType.equals(""))
        {
            LOG("missions", "Returning null due to SPAWN TYPE FAILURE");
            return null;
        }
        if (locations.getMinMissionDifficultyAtLocation(locMissionStart) > intDifficulty)
        {
            return null;
        }
        dictionary dctSpawnList = new dictionary();
        LOG("mission_spam", "Making destruction Mission Spawn List");
        dctSpawnList = getDestructionMissionSpawnList(objCreator, intDifficulty, strPlanet, strSpawnType, strFaction);
        if (dctSpawnList == null)
        {
            return null;
        }
        String strGoal = dctSpawnList.getString("strTemplate");
        if (strGoal == null || strGoal.equals(""))
        {
            return null;
        }
        int intMinReward = dctSpawnList.getInt("intMinReward");
        int intMaxReward = dctSpawnList.getInt("intMaxReward");
        String strLairType = dctSpawnList.getString("strLairType");
        String strBuildingType = dctSpawnList.getString("strBuildingType");
        String strObjective = dctSpawnList.getString("strObjective");
        String strObjectiveTarget = dctSpawnList.getString("strObjectiveTarget");
        String strTargetType = dctSpawnList.getString("strTargetType");
        float fltKillPercentage = dctSpawnList.getFloat("fltKillPercentage");
        int intMinDifficulty = dctSpawnList.getInt("intMinDifficulty");
        int intMaxDifficulty = dctSpawnList.getInt("intMaxDifficulty");
        int intSize = dctSpawnList.getInt("intSize");
        int intGCWPoints = dctSpawnList.getInt("intGCWPoints");
        int intTest = utils.getTheaterSize(strLairType);
        if (intTest > 0)
        {
            intSize = intTest;
        }
        String strTemplateToSpawn = dctSpawnList.getString("strTemplateToSpawn");
        int intNumToSpawn = dctSpawnList.getInt("intNumToSpawn");
        String strDetailTemplate = dctSpawnList.getString("strDetailTemplate");
        String strIdFileName = dctSpawnList.getString("strIdFileName");
        int intStringId = dctSpawnList.getInt("intStringId");
        setObjVar(objMissionData, "fltKillPercentage", fltKillPercentage);
        setObjVar(objMissionData, "strLairType", strLairType);
        setObjVar(objMissionData, "strBuildingType", strBuildingType);
        setObjVar(objMissionData, "strObjective", strObjective);
        setObjVar(objMissionData, "strObjectiveTarget", strObjectiveTarget);
        setObjVar(objMissionData, "strTargetType", strTargetType);
        setObjVar(objMissionData, "intPlayerDifficulty", intDifficulty);
        setObjVar(objMissionData, "intMinDifficulty", intMinDifficulty);
        setObjVar(objMissionData, "intMaxDifficulty", intMaxDifficulty);
        String strLairDifficulty = create.getLairDifficulty(intMinDifficulty, intMaxDifficulty, intDifficulty);
        setObjVar(objMissionData, "strLairDifficulty", strLairDifficulty);
        setObjVar(objMissionData, "intSize", intSize);
        setObjVar(objMissionData, "strTemplateToSpawn", strTemplateToSpawn);
        setObjVar(objMissionData, "intNumToSpawn", intNumToSpawn);
        obj_id groupObject = getGroupObject(objCreator);
        if (isIdValid(groupObject))
        {
            setObjVar(objMissionData, "originalGroupSize", getPCGroupSize(groupObject));
        }
        else 
        {
            setObjVar(objMissionData, "originalGroupSize", 1);
        }
        location locTest = new location();
        if (strDetailTemplate.equals(""))
        {
            string_id strTokenName = new string_id("lair_n", strLairType);
            setMissionTargetName(objMissionData, utils.packStringId(strTokenName));
            setMissionTargetAppearance(objMissionData, strGoal);
        }
        else 
        {
            string_id strTokenName = new string_id("lair_n", strLairType);
            setObjVar(objMissionData, "strGoal", strGoal);
            setMissionTargetAppearance(objMissionData, strDetailTemplate);
            setMissionTargetName(objMissionData, utils.packStringId(strTokenName));
        }
        float fltDistance = utils.getDistance(locMissionStart, locMissionDestination);
        int intReward = getDestroyReward(intMinReward, intMaxReward, fltDistance);
        intReward = (int)(intReward * fltRewardModifier);
        if (!strFaction.equals("") && strFaction != null)
        {
            String strActualFaction = utils.getFactionSubString(strFaction);
            if (strActualFaction != null)
            {
                int intFactionReward = getFactionReward(intReward);
                setObjVar(objMissionData, "intFactionReward", intFactionReward);
                setObjVar(objMissionData, "strFaction", strActualFaction);
                setObjVar(objMissionData, "intGCWPoints", intGCWPoints);
            }
        }
        setMissionReward(objMissionData, intReward);
        dictionary dctParams = new dictionary();
        location locSpawnLocation = (location)locMissionDestination.clone();
        setMissionStartLocation(objMissionData, locSpawnLocation);
        setMissionDifficulty(objMissionData, intDifficulty);
        int intMissionDifficulty = 0;
        if (intDifficulty < MISSION_DESTROY_DIFFICULTY_EASY)
        {
            intMissionDifficulty = MISSION_DIFFICULTY_EASY;
        }
        else if (intDifficulty < MISSION_DESTROY_DIFFICULTY_MEDIUM)
        {
            intMissionDifficulty = MISSION_DIFFICULTY_MEDIUM;
        }
        else 
        {
            intMissionDifficulty = MISSION_DIFFICULTY_HARD;
        }
        if (!strIdFileName.equals(""))
        {
            setObjVar(objMissionData, "intStringId", intStringId);
            setObjVar(objMissionData, "strIdFileName", strIdFileName);
        }
        else 
        {
            setupContextualizationStrings(objMissionData, intMissionDifficulty, objCreator, strFaction);
        }
        if (ai_lib.aiGetNiche(objCreator) == NICHE_NPC)
        {
            location locCreatorLocation = getMissionLocation(objCreator);
            setObjVar(objMissionData, "locCreatorLocation", locCreatorLocation);
        }
        setObjVar(objMissionData, "location.locMissionStart", locMissionStart);
        return objMissionData;
    }
    public obj_id createDeliverMissionFromLocation(obj_id objMissionData, obj_id objCreator, location locMissionStart, int intDifficulty, String strPlanet, String strFaction, float fltRewardModifier) throws InterruptedException
    {
        setMissionRootScriptName(objMissionData, "systems.missions.dynamic.mission_deliver");
        setMissionType(objMissionData, "deliver");
        location locPickupLocation = new location();
        location locDropoffLocation = new location();
        int intVariance;
        String strPickupNPC = NPC_TYPES[rand(0, NPC_TYPES.length - 1)];
        String strDropoffNPC = NPC_TYPES[rand(0, NPC_TYPES.length - 1)];
        String strDeliveryItem = "object/tangible/mission/mission_datadisk.iff";
        location locStartLocation = new location();
        location locEndLocation = new location();
        setObjVar(objMissionData, "strPickupNPC", strPickupNPC);
        setObjVar(objMissionData, "strDropoffNPC", strDropoffNPC);
        region[] rgnCities = getRegionsWithMunicipalAtPoint(locMissionStart, regions.MUNI_TRUE);
        if (rgnCities == null)
        {
            rgnCities = getRegionsAtPoint(locStartLocation);
            if (rgnCities == null)
            {
                return null;
            }
        }
        region rgnStartRegion = rgnCities[0];
        boolean boolIsCity = false;
        if (locations.isCityRegion(rgnStartRegion))
        {
            locStartLocation = locations.getGoodCityRegionLocation(rgnStartRegion, strPlanet);
            boolIsCity = true;
        }
        else 
        {
            region closestCity = locations.getClosestCityRegion(rgnStartRegion);
            locStartLocation = locations.getGoodCityRegionLocation(closestCity, strPlanet);
            boolIsCity = true;
        }
        if (locStartLocation == null)
        {
            return null;
        }
        int intMissionDifficulty = MISSION_DIFFICULTY_EASY;
        if (boolIsCity)
        {
            int intRoll = rand(1, 100);
            if (intRoll < 50)
            {
                locEndLocation = locations.getDifferentGoodCityRegionLocation(locStartLocation);
            }
            else 
            {
                if (intRoll < 75)
                {
                    intMissionDifficulty = MISSION_DIFFICULTY_HARD;
                }
                else{
                    intMissionDifficulty = MISSION_DIFFICULTY_MEDIUM;
                }
                region rgnEndRegion = locations.getDeliverCityRegion(rgnStartRegion);
                try {
                    locEndLocation = locations.getGoodCityRegionLocation(rgnEndRegion, rgnEndRegion.getPlanetName());
                }
                catch(Exception e){
                    LOG("mission", "Missions (DELIVER): Unable to resolve end location for mission.  End Region Planet (" + rgnEndRegion.getPlanetName()
                            + "), End Region City (" + rgnEndRegion.getName() + "), Start Location (" + locStartLocation.toString() + ") on Planet " + locStartLocation.area
                            + "), Start Region City (" + rgnStartRegion.getName() + "), Pickup NPC (" + strPickupNPC + "), Dropoff NPC (" + strDropoffNPC
                            + "), Terminal Location (" + getLocation(getSelf()) + "), Terminal ID (" + getSelf() + ")");
                    return null;
                }
            }
        }
        else 
        {
            region rgnEndRegion = locations.getClosestCityRegion(rgnStartRegion);
            try {
                locEndLocation = locations.getGoodCityRegionLocation(rgnEndRegion, strPlanet);
            }
            catch(Exception e){
                LOG("mission", "Missions (DELIVER): Unable to resolve end location for mission.  End Region Planet (" + rgnEndRegion.getPlanetName()
                        + "), End Region City (" + rgnEndRegion.getName() + "), Start Location (" + locStartLocation.toString() + ") on Planet " + locStartLocation.area
                        + "), Start Region City (" + rgnStartRegion.getName() + "), Pickup NPC (" + strPickupNPC + "), Dropoff NPC (" + strDropoffNPC
                        + "), Terminal Location (" + getLocation(getSelf()) + "), Terminal ID (" + getSelf() + ")");
                return null;
            }
        }
        setObjVar(objMissionData, "intMissionDifficulty", intMissionDifficulty);
        if (locEndLocation == null)
        {
            return null;
        }
        setMissionDifficulty(objMissionData, getIntObjVar(objMissionData, "intMissionDifficulty"));
        setMissionTargetAppearance(objMissionData, strDeliveryItem);
        if (ai_lib.aiGetNiche(objCreator) == NICHE_NPC)
        {
            setMissionStartLocation(objMissionData, locEndLocation);
            setMissionEndLocation(objMissionData, getMissionLocation(objCreator));
            setObjVar(objMissionData, "objEndNPC", objCreator);
        }
        else 
        {
            setMissionStartLocation(objMissionData, locStartLocation);
            setMissionEndLocation(objMissionData, locEndLocation);
        }
        int intReward = getDeliverReward(locStartLocation, locEndLocation, DELIVER_REWARD_RATIO, 1);
        intReward = (int)(intReward * fltRewardModifier);
        if (intReward < 5)
        {
            intReward = 5;
        }
        if (!strFaction.equals(""))
        {
            int intFactionReward = getFactionReward(intReward);
            setObjVar(objMissionData, "intFactionReward", intFactionReward);
            setObjVar(objMissionData, "strFaction", strFaction);
        }
        obj_id groupObject = getGroupObject(objCreator);
        if (isIdValid(groupObject))
        {
            setObjVar(objMissionData, "originalGroupSize", getPCGroupSize(groupObject));
        }
        setMissionReward(objMissionData, intReward);
        dictionary dctParams = new dictionary();
        setupContextualizationStrings(objMissionData, intMissionDifficulty, objCreator, strFaction);
        return objMissionData;
    }
    public location getGoodBountyDestination(String strPlanet) throws InterruptedException
    {
        location locMissionDestination = null;
        region[] rgnMissionRegions = getRegionsWithMission(strPlanet, regions.MISSION_OTHER);
        if (rgnMissionRegions == null)
        {
            return null;
        }
        region rgnMissionRegion = rgnMissionRegions[rand(0, rgnMissionRegions.length - 1)];
        if (rgnMissionRegion.getMunicipalType() == regions.MUNI_TRUE)
        {
            locMissionDestination = locations.getGoodCityLocation(rgnMissionRegion, strPlanet);
            if (locMissionDestination == null)
            {
                return null;
            }
        }
        else 
        {
            location[] locExtents = getRegionExtent(rgnMissionRegion);
            location locLowerLeft = locExtents[0];
            location locUpperRight = locExtents[1];
            float minimum = -8192f + 512f;
            float maximum = 8192f - 512f;
            if (locLowerLeft.x < minimum)
            {
                locLowerLeft.x = minimum;
            }
            if (locLowerLeft.z < minimum)
            {
                locLowerLeft.z = minimum;
            }
            if (locUpperRight.x < maximum)
            {
                locUpperRight.x = maximum;
            }
            if (locUpperRight.z < maximum)
            {
                locUpperRight.z = maximum;
            }
            locMissionDestination = locLowerLeft;
            locMissionDestination.x = rand(locLowerLeft.x, locUpperRight.x);
            locMissionDestination.z = rand(locLowerLeft.z, locUpperRight.z);
        }
        return locMissionDestination;
    }
    public obj_id createDynamicBountyMission(obj_id objMissionData, obj_id objCreator, int intBountyDifficulty, int intDifficulty, String strStartPlanet, String strFaction) throws InterruptedException
    {
        setMissionRootScriptName(objMissionData, "systems.missions.dynamic.mission_bounty");
        setMissionType(objMissionData, "bounty");
        float fltMovementTime = 0;
        location locMissionDestination = new location();
        region rgnMissionRegion;
        String strPlanet = new String();
        String strTargetName;
        int intMissionDifficulty = MISSION_DIFFICULTY_EASY;
        String strColumnName = new String();
        if (intDifficulty < 42 || intBountyDifficulty < 2)
        {
            strColumnName = "strEasy";
        }
        else if (intDifficulty < 82 || intBountyDifficulty < 3)
        {
            strColumnName = "strMedium";
            intMissionDifficulty = MISSION_DIFFICULTY_MEDIUM;
        }
        else 
        {
            strColumnName = "strHard";
            intMissionDifficulty = MISSION_DIFFICULTY_HARD;
            if ((intDifficulty == 90) && (21 > rand(1, 100)))
            {
                strColumnName = "strBoss";
                intMissionDifficulty = MISSION_DIFFICULTY_BOSS;
            }
        }
        if (!strFaction.equals(""))
        {
            if (strFaction.equals("imperial"))
            {
                strColumnName = strColumnName + strFaction;
            }
        }
        float fltDifficultyModifier = 1;
        if (intBountyDifficulty == BOUNTY_DIFFICULTY_BASIC)
        {
            int intRoll = rand(1, 100);
            strPlanet = strStartPlanet;
        }
        else if (intBountyDifficulty == BOUNTY_DIFFICULTY_ADVANCED)
        {
            fltDifficultyModifier = 1.5f;
            strPlanet = PLANETS[rand(0, PLANETS.length - 1)];
        }
        else if (intBountyDifficulty == BOUNTY_DIFFICULTY_EXPERT)
        {
            fltDifficultyModifier = 2.0f;
            strPlanet = PLANETS[rand(0, PLANETS.length - 1)];
        }
        else 
        {
            return null;
        }
        locMissionDestination = getGoodBountyDestination(strPlanet);
        if (locMissionDestination == null)
        {
            return null;
        }
        setObjVar(objMissionData, "locSpawnLocation", locMissionDestination);
        setObjVar(objMissionData, "intInformantLevel", intBountyDifficulty);
        String strSpecies = "";
        String strSex = "";
        String[] strBountyTypes = dataTableGetStringColumnNoDefaults("datatables/missions/bounty/bounty.iff", strColumnName);
        if (strBountyTypes == null)
        {
            return null;
        }
        String strTarget = strBountyTypes[rand(0, strBountyTypes.length - 1)];
        setObjVar(objMissionData, "strTarget", strTarget);
        int intRoll = rand(1, 2);
        if (intRoll == 1)
        {
            strSpecies = PLAYER_SPECIES[rand(0, PLAYER_SPECIES.length - 1)];
            intRoll = rand(1, 2);
            if (intRoll == 1)
            {
                strSex = "male";
            }
            else 
            {
                strSex = "female";
            }
        }
        else 
        {
            strSpecies = "alien";
            strSex = "other";
        }
        setObjVar(objMissionData, "intBountyDifficulty", intBountyDifficulty);
        strTargetName = getRandomSpeciesName(strSpecies);
        if (intMissionDifficulty == MISSION_DIFFICULTY_BOSS)
        {
            strTargetName = getRandomSpeciesName(strSpecies) + " (Extremely Dangerous)";
        }
        setObjVar(objMissionData, "strTargetName", strTargetName);
        setObjVar(objMissionData, "strSpecies", strSpecies);
        float fltVariance = rand(MIN_REWARD_VARIANCE, MAX_REWARD_VARIANCE);
        fltVariance = fltVariance / 100;
        intDifficulty = intDifficulty + (int)(intDifficulty * fltVariance);
        location locTest = new location();
        String strGoal = "object/tangible/mission/mission_bounty_target.iff";
        setMissionTargetAppearance(objMissionData, strGoal);
        setMissionTargetName(objMissionData, strTargetName);
        int intReward = 0;
        if (intBountyDifficulty > 1)
        {
            intReward = (intBountyDifficulty * 40) + intDifficulty;
        }
        else 
        {
            intReward = 20 + intDifficulty;
        }
        intReward = intReward * 150 + rand(-100, 300);
        obj_id groupObject = getGroupObject(objCreator);
        if (isIdValid(groupObject))
        {
            setObjVar(objMissionData, "originalGroupSize", getPCGroupSize(groupObject));
        }
        setMissionReward(objMissionData, intReward);
        dictionary dctParams = new dictionary();
        location locSpawnLocation = (location)locMissionDestination.clone();
        setObjVar(objMissionData, "locSpawnLocation", locSpawnLocation);
        if (intDifficulty > 90)
        {
            intDifficulty = 90;
        }
        int intCombatDifficulty = intDifficulty + rand(1, 2);
        if (intCombatDifficulty > 90)
        {
            intCombatDifficulty = 90;
        }
        setObjVar(objMissionData, "intCombatDifficulty", intCombatDifficulty);
        setMissionDifficulty(objMissionData, intDifficulty);
        setupContextualizationStrings(objMissionData, intMissionDifficulty, objCreator, strFaction);
        return objMissionData;
    }
    public obj_id createJediBountyMission(obj_id objMissionData, obj_id objCreator, String strFaction, int hunterLevel, obj_id bountyHunterId) throws InterruptedException
    {
        return createJediBountyMission(objMissionData, objCreator, strFaction, hunterLevel, bountyHunterId, missions.BOUNTY_FLAG_NONE);
    }
    public obj_id createJediBountyMission(obj_id objMissionData, obj_id objCreator, String strFaction, int hunterLevel, obj_id bountyHunterId, int flag) throws InterruptedException
    {
        setMissionRootScriptName(objMissionData, "systems.missions.dynamic.mission_bounty");
        setMissionType(objMissionData, "bounty");
        int bhMin = 20;
        int bhMax = 90;
        if (hunterLevel < 22)
        {
            return null;
        }
        else 
        {
            bhMin = (int)(hunterLevel * 0.75);
            bhMax = (int)(hunterLevel * 1.5);
        }
        dictionary dctJediInfo = requestJedi(IGNORE_JEDI_STAT, 15000, bhMin, bhMax, IGNORE_JEDI_STAT, -3);
        if (dctJediInfo == null)
        {
            LIVE_LOG("bh_jedi_mission", "mission_dynamic_base.createJediBountyMission: No PvP target found (null)");
            LOG("PvP_Bounty", "No missions.");
            return null;
        }
        obj_id objTarget = null;
        String strTargetName = null;
        int jediBountyValue = 0;
        int intFaction = 0;
        strFaction = toLower(strFaction);
        if (strFaction != null)
        {
            intFaction = getStringCrc(strFaction);
        }
        obj_id[] objJedis = dctJediInfo.getObjIdArray("id");
        boolean[] boolOnline = dctJediInfo.getBooleanArray("online");
        int[] jediFaction = dctJediInfo.getIntArray("faction");
        String[] jediPlanet = dctJediInfo.getStringArray("scene");
        location bountyHunterLocation = getLocation(bountyHunterId);
        location[] jediLocation = dctJediInfo.getLocationArray("location");
        int[] smugglerFlags = dctJediInfo.getIntArray("smuggler");
        if (objJedis == null || objJedis.length == 0)
        {
            LIVE_LOG("bh_jedi_mission", "mission_dynamic_base.createJediBountyMission: No PvP bounty found (no data)");
            return null;
        }
        LIVE_LOG("bh_jedi_mission", "mission_dynamic_base.createJediBountyMission: found " + objJedis.length + " qualifying PvP Bounties");
        int intRoll = -1;
        Vector jediList = new Vector();
        Vector jediIdx = new Vector();
        for (int i = 0; i < objJedis.length; i++)
        {
            jediIdx = utils.addElement(jediIdx, i);
        }
        jediList = utils.concatArrays(jediList, objJedis);
        obj_id[] SamePlanetObjId = new obj_id[jediList.size()];
        int SamePlanetCounter = 0;
        int[] SamePlanetInt = new int[jediList.size()];
        while (jediList.size() > 0)
        {
            intRoll = rand(0, jediList.size() - 1);
            if (!isIdValid(((obj_id)jediList.get(intRoll))) || !boolOnline[(((Integer)(jediIdx.get(intRoll)))).intValue()])
            {
                jediList = utils.removeElementAt(jediList, intRoll);
                jediIdx = utils.removeElementAt(jediIdx, intRoll);
            }
            else if ((((obj_id)jediList.get(intRoll)) == bountyHunterId) || ((intFaction != 0) && (jediFaction[(((Integer)(jediIdx.get(intRoll)))).intValue()] != intFaction)))
            {
                jediList = utils.removeElementAt(jediList, intRoll);
                jediIdx = utils.removeElementAt(jediIdx, intRoll);
            }
            else if (flag == missions.BOUNTY_FLAG_SMUGGLER && ((smugglerFlags == null) || (smugglerFlags.length < ((((Integer)(jediIdx.get(intRoll)))).intValue() + 1)) || (smugglerFlags[(((Integer)(jediIdx.get(intRoll)))).intValue()] != 1)))
            {
                jediList = utils.removeElementAt(jediList, intRoll);
                jediIdx = utils.removeElementAt(jediIdx, intRoll);
            }
            else if (jediPlanet[(((Integer)(jediIdx.get(intRoll)))).intValue()].equals(bountyHunterLocation.area))
            {
                SamePlanetObjId[SamePlanetCounter] = ((obj_id)jediList.get(intRoll));
                SamePlanetInt[SamePlanetCounter] = ((Integer)jediIdx.get(intRoll)).intValue();
                ++SamePlanetCounter;
                jediList = utils.removeElementAt(jediList, intRoll);
                jediIdx = utils.removeElementAt(jediIdx, intRoll);
            }
            else 
            {
                intRoll = ((Integer)jediIdx.get(intRoll)).intValue();
                String[] strNames = dctJediInfo.getStringArray("name");
                strTargetName = strNames[intRoll];
                int[] bountyValue = dctJediInfo.getIntArray("bountyValue");
                jediBountyValue = bountyValue[intRoll];
                objTarget = objJedis[intRoll];
                if (!isIdValid(objTarget))
                {
                    LIVE_LOG("bh_jedi_mission", "WARNING mission_dynamic_base.createJediBountyMission: target " + intRoll + " was invalid");
                }
                break;
            }
        }
        if (jediList.size() <= 0 && SamePlanetCounter > 0)
        {
            int randomChoice = rand(0, SamePlanetObjId.length - 1);
            obj_id farthestBounty = null;
            int farthestBountyIndex = -1;
            for (int i = 0; i <= 100; ++i)
            {
                if (!isIdNull(SamePlanetObjId[randomChoice]))
                {
                    float distance = getDistance(bountyHunterLocation, jediLocation[SamePlanetInt[randomChoice]]);
                    if (distance > PREFERRED_DISTANCE)
                    {
                        farthestBounty = SamePlanetObjId[randomChoice];
                        farthestBountyIndex = SamePlanetInt[randomChoice];
                        break;
                    }
                }
                randomChoice = rand(0, SamePlanetObjId.length - 1);
                if (i >= 100)
                {
                    farthestBounty = SamePlanetObjId[randomChoice];
                    farthestBountyIndex = SamePlanetInt[randomChoice];
                }
            }
            intRoll = farthestBountyIndex;
            String[] strNames = dctJediInfo.getStringArray("name");
            strTargetName = strNames[intRoll];
            int[] bountyValue = dctJediInfo.getIntArray("bountyValue");
            jediBountyValue = bountyValue[intRoll];
            objTarget = objJedis[intRoll];
            if (!isIdValid(objTarget))
            {
                LIVE_LOG("bh_jedi_mission", "WARNING mission_dynamic_base.createJediBountyMission: target " + intRoll + " was invalid");
            }
        }
        if (strTargetName == null)
        {
            LIVE_LOG("bh_jedi_mission", "mission_dynamic_base.createJediBountyMission: No PvP Bounty found (bad data)");
            return null;
        }
        LIVE_LOG("bh_jedi_mission", "mission_dynamic_base.createJediBountyMission: creating mission on Jedi " + strTargetName);
        setObjVar(objMissionData, "objTarget", objTarget);
        setObjVar(objMissionData, "intInformantLevel", 3);
        setObjVar(objMissionData, "intBountyDifficulty", 3);
        setObjVar(objMissionData, "strTargetName", strTargetName);
        String strGoal = "object/tangible/mission/mission_bounty_jedi_target.iff";
        setMissionTargetAppearance(objMissionData, strGoal);
        if (flag == missions.BOUNTY_FLAG_SMUGGLER)
        {
            if (jediFaction[intRoll] == (370444368))
            {
                setMissionTargetName(objMissionData, utils.packStringId(new string_id("mission/mission_bounty_jedi", "rebel_smuggler")));
            }
            else 
            {
                if (jediFaction[intRoll] == (-615855020))
                {
                    setMissionTargetName(objMissionData, utils.packStringId(new string_id("mission/mission_bounty_jedi", "imperial_smuggler")));
                }
                else 
                {
                    setMissionTargetName(objMissionData, utils.packStringId(new string_id("mission/mission_bounty_jedi", "neutral_smuggler")));
                }
            }
        }
        else 
        {
            if (jediFaction[intRoll] == (370444368))
            {
                setMissionTargetName(objMissionData, utils.packStringId(new string_id("mission/mission_bounty_jedi", "rebel_jedi")));
            }
            else 
            {
                if (jediFaction[intRoll] == (-615855020))
                {
                    setMissionTargetName(objMissionData, utils.packStringId(new string_id("mission/mission_bounty_jedi", "imperial_jedi")));
                }
                else 
                {
                    setMissionTargetName(objMissionData, utils.packStringId(new string_id("mission/mission_bounty_jedi", "neutral_jedi")));
                }
            }
        }
        setMissionReward(objMissionData, jediBountyValue);
        setMissionDifficulty(objMissionData, 100);
        String factionName = factions.getFactionNameByHashCode(jediFaction[intRoll]);
        if (factionName != null)
        {
            factionName = toLower(factionName);
            if (factionName.equals("imperial"))
            {
                factionName = "rebel";
            }
            else if (factionName.equals("rebel"))
            {
                factionName = "imperial";
            }
        }
        setupContextualizationStrings(objMissionData, MISSION_BOUNTY_DIFFICULTY_JEDI, objCreator, factionName, flag);
        return objMissionData;
    }
    public obj_id createBountyTarget(obj_id objMission) throws InterruptedException
    {
        obj_id objMissionData = getMissionData(objMission);
        String strSpecies = getStringObjVar(objMissionData, "strSpecies");
        obj_id objPlayer = getMissionHolder(objMission);
        location locSpawnLocation = getLocationObjVar(objMission, "locSpawnLocation");
        location locGoodLocation = new location();
        if (!(locations.isInCity(locSpawnLocation)))
        {
            locGoodLocation = locations.getGoodLocationAroundLocationAvoidCollidables(locSpawnLocation, 1, 1, 64, 64, false, true, 10.0f);
        }
        else 
        {
            region rgnCityRegion = locations.getCityRegion(locSpawnLocation);
            locGoodLocation = locations.getGoodCityLocation(rgnCityRegion, locSpawnLocation.area);
        }
        if (locGoodLocation == null)
        {
            return null;
        }
        int npcLevel = getIntObjVar(objMissionData, "intCombatDifficulty");
        String strNPCType = getStringObjVar(objMissionData, "strTarget");
        obj_id objTargetNPC;
        locSpawnLocation.y = getHeightAtLocation(locSpawnLocation.x, locSpawnLocation.z);
        objTargetNPC = create.object(strNPCType, locSpawnLocation, npcLevel);
        stopFloating(objTargetNPC);
        if (!isIdValid(objTargetNPC))
        {
            objTargetNPC = create.object(NPC_TYPES[rand(0, NPC_TYPES.length - 1)], locSpawnLocation, npcLevel);
            stopFloating(objTargetNPC);
            if (!isIdValid(objTargetNPC))
            {
                return null;
            }
        }
        String strName = getStringObjVar(objMissionData, "strTargetName");
        setName(objTargetNPC, strName);
        setMissionStartLocation(objMissionData, locGoodLocation);
        pvpSetAlignedFaction(objTargetNPC, (84709322));
        pvpSetPermanentPersonalEnemyFlag(objTargetNPC, objPlayer);
        setObjVar(objMissionData, "intTargetCreated", 1);
        setObjVar(objTargetNPC, "objHunter", objPlayer);
        setObjVar(objTargetNPC, "objMission", objMission);
        persistAndRegisterObject(objTargetNPC, objMission);
        int intBool = attachScript(objTargetNPC, "systems.missions.dynamic.mission_bounty_target");
        dictionary dctParams = new dictionary();
        messageTo(objTargetNPC, "handle_Spawn_Setup_Complete", dctParams, 0, true);
        setObjVar(objMission, "intTargetSpawned", 1);
        return objTargetNPC;
    }
    public boolean transferDeliveryItem(obj_id objItem, obj_id objTarget) throws InterruptedException
    {
        obj_id objInventory = getObjectInSlot(objTarget, "inventory");
        boolean boolTransfer = putIn(objItem, objInventory);
        if (boolTransfer == true)
        {
            return true;
        }
        return false;
    }
    public void missionArrival(obj_id objMission) throws InterruptedException
    {
        dictionary dctParams = new dictionary();
        messageTo(objMission, "onArrivedAtLocation", dctParams, 0, true);
        return;
    }
    public void cleanupLocationTargets(obj_id objMission) throws InterruptedException
    {
        dictionary dctParams = new dictionary();
        dctParams.put("strLocationTarget", objMission.toString());
        messageTo(getMissionHolder(objMission), "cleanupLocationTarget", dctParams, 0, true);
    }
    public void setupSpawn(obj_id objMission, location locSpawnLocation) throws InterruptedException
    {
        obj_id objPlayer = getMissionHolder(objMission);
        dictionary dctParams = new dictionary();
        dctParams.put("objMission", objMission);
        dctParams.put("locSpawnLocation", locSpawnLocation);
        messageTo(objPlayer, "setupSpawn", dctParams, 0, true);
    }
    public void setupUnloadedSpawn(obj_id objMission, obj_id objMissionHolder, location locSpawnLocation) throws InterruptedException
    {
        dictionary dctParams = new dictionary();
        dctParams.put("objMission", objMission);
        dctParams.put("locSpawnLocation", locSpawnLocation);
        messageTo(objMissionHolder, "setupSpawn", dctParams, 0, true);
    }
    public location getGoodMissionLocation(obj_id objMission, obj_id objPlayer) throws InterruptedException
    {
        int intSize = getIntObjVar(objMission, "intSize");
        if (intSize < 1)
        {
            intSize = 64;
        }
        location locSpawnLocation = getMissionStartLocation(objMission);
        location locGoodLocation = locations.getGoodLocationAroundLocation(locSpawnLocation, intSize, intSize, 300, 300, false, true);
        if (locGoodLocation == null)
        {
            locGoodLocation = locations.getGoodLocationAroundLocation(getLocation(objPlayer), intSize, intSize, 400, 400, false, true);
            if (locGoodLocation == null)
            {
                return null;
            }
        }
        if (locations.isInMissionCity(locGoodLocation))
        {
            locGoodLocation = locations.getGoodLocationAroundLocation(getLocation(objPlayer), intSize, intSize, 400, 400, false, true);
            if (locGoodLocation == null)
            {
                return null;
            }
            if (locations.isInMissionCity(locGoodLocation))
            {
                return null;
            }
        }
        region rgnBattlefield = battlefield.getBattlefield(locGoodLocation);
        if (rgnBattlefield != null)
        {
            return null;
        }
        if (locations.isInMissionCity(locGoodLocation))
        {
            return null;
        }
        float fltHeight = getHeightAtLocation(locGoodLocation.x, locGoodLocation.z);
        locGoodLocation.y = fltHeight;
        return locGoodLocation;
    }
    public boolean setupDestructionMissionObject(obj_id objMission) throws InterruptedException
    {
        return true;
    }
    public void sendMissionStartMovementSpam(obj_id objMission) throws InterruptedException
    {
        obj_id objPlayer = getMissionHolder(objMission);
        sendSystemMessage(objPlayer, SID_MISSION_TARGET_UPDATED);
    }
    public void moveMissionStartLocation(obj_id objMission) throws InterruptedException
    {
        location locSpawnLocation = getMissionStartLocation(objMission);
        location locStartLocation = getLocationObjVar(objMission, "location.locMissionStart");
        int intDistance = rand(MIN_DISTANCE_FOR_RETRY, MAX_DISTANCE_FOR_RETRY);
        location locNewLocation = (location)locSpawnLocation.clone();
        locNewLocation.x = locSpawnLocation.x - locStartLocation.x;
        locNewLocation.z = locSpawnLocation.z - locStartLocation.z;
        float fltDistance = utils.getDistance(locStartLocation, locSpawnLocation);
        locNewLocation.x = locNewLocation.x / fltDistance;
        locNewLocation.z = locNewLocation.z / fltDistance;
        location locFinalLocation = (location)locSpawnLocation.clone();
        locFinalLocation.x = locSpawnLocation.x + (float)(locNewLocation.x * intDistance);
        locFinalLocation.z = locSpawnLocation.z + (float)(locNewLocation.z * intDistance);
        setMissionStartLocation(objMission, locFinalLocation);
        updateMissionWaypoint(objMission, locFinalLocation);
        sendMissionStartMovementSpam(objMission);
        setupSpawn(objMission, locFinalLocation);
        return;
    }
    public boolean createDestructionTarget(obj_id objMission, obj_id objPlayer) throws InterruptedException
    {
        int intAttempts = 0;
        if (hasObjVar(objMission, "intAttempts"))
        {
            intAttempts = getIntObjVar(objMission, "intAttempts");
        }
        else 
        {
            setObjVar(objMission, "intAttempts", intAttempts);
        }
        String strTemplate;
        obj_id objMissionData = getMissionData(objMission);
        if (hasObjVar(objMissionData, "strGoal"))
        {
            strTemplate = getStringObjVar(objMissionData, "strGoal");
        }
        else 
        {
            return false;
        }
        location locGoodLocation = getGoodMissionLocation(objMission, objPlayer);
        if (locGoodLocation == null)
        {
            if (intAttempts >= MAX_SPAWN_ATTEMPTS)
            {
                return false;
            }
            else 
            {
                intAttempts = intAttempts + 1;
                setObjVar(objMission, "intAttempts", intAttempts);
                moveMissionStartLocation(objMission);
                return true;
            }
        }
        obj_id objDestructionTarget = createObject(strTemplate, locGoodLocation);
        if (!isIdValid(objDestructionTarget))
        {
            return false;
        }
        persistAndRegisterObject(objDestructionTarget, objMission);
        float fltKillPercentage = getFloatObjVar(objMissionData, "fltKillPercentage");
        String strLairType = getStringObjVar(objMissionData, "strLairType");
        String strBuildingType = getStringObjVar(objMissionData, "strBuildingType");
        String strObjective = getStringObjVar(objMissionData, "strObjective");
        String strObjectiveTarget = getStringObjVar(objMissionData, "strObjectiveTarget");
        String strLairDifficulty = getStringObjVar(objMissionData, "strLairDifficulty");
        int intPlayerDifficulty = getIntObjVar(objMissionData, "intPlayerDifficulty");
        int groupSize = getIntObjVar(objMissionData, "originalGroupSize");
        if (!strLairType.equals(""))
        {
            setObjVar(objDestructionTarget, "spawning.lairType", strLairType);
            setObjVar(objDestructionTarget, "spawning.lairDifficulty", strLairDifficulty);
            setObjVar(objDestructionTarget, "spawning.intDifficultyLevel", intPlayerDifficulty);
            setObjVar(objDestructionTarget, "spawning.groupSize", groupSize);
            if (!strBuildingType.equals(""))
            {
                setObjVar(objDestructionTarget, "spawning.buildingType", strBuildingType);
            }
            if (!strObjectiveTarget.equals(""))
            {
                setObjVar(objDestructionTarget, "spawning.target", strObjectiveTarget);
            }
            else 
            {
                setObjVar(objDestructionTarget, "spawning.killPercent", fltKillPercentage);
            }
        }
        else 
        {
            int intNumToSpawn = getIntObjVar(objMissionData, "intNumToSpawn");
            String strTemplateToSpawn = getStringObjVar(objMissionData, "strTemplateToSpawn");
            if (intNumToSpawn > 0)
            {
                setObjVar(objDestructionTarget, "numToSpawn", intNumToSpawn);
            }
            if (strTemplateToSpawn != null && !strTemplateToSpawn.equals(""))
            {
                setObjVar(objDestructionTarget, "creatureTemplate", strTemplateToSpawn);
            }
            int intBool = attachScript(objDestructionTarget, "systems.missions.dynamic.mission_destruction_target");
        }
        updateMissionWaypoint(objMission, locGoodLocation);
        sendSystemMessage(objPlayer, SID_MISSION_TARGET_UPDATED_EXACT);
        setMissionStartLocation(objMissionData, locGoodLocation);
        setObjVar(objDestructionTarget, "objDestroyer", objPlayer);
        setObjVar(objDestructionTarget, "objMission", objMission);
        setObjVar(objDestructionTarget, "locSpawnLocation", locGoodLocation);
        setObjVar(objDestructionTarget, "objMissionHolder", objPlayer);
        dictionary dctParams = new dictionary();
        messageTo(objDestructionTarget, "handle_Spawn_Setup_Complete", dctParams, 0, true);
        return true;
    }
    public void sendDestructionFail(obj_id objMission) throws InterruptedException
    {
        dictionary dctParams = new dictionary();
        messageTo(objMission, "destructionFail", dctParams, 0, true);
    }
    public void sendDestructionSuccess(obj_id objMission) throws InterruptedException
    {
        dictionary dctParams = new dictionary();
        messageTo(objMission, "destructionSuccess", dctParams, 0, false);
    }
    public void sendDestructionIncomplete(obj_id objMission) throws InterruptedException
    {
        dictionary dctParams = new dictionary();
        messageTo(objMission, "destructionIncomplete", dctParams, 0, true);
    }
    public void sendEscortFail(obj_id objMission) throws InterruptedException
    {
        dictionary dctParams = new dictionary();
        messageTo(objMission, "escortFail", dctParams, 0, true);
    }
    public void sendEscortSuccess(obj_id objMission) throws InterruptedException
    {
        dictionary dctParams = new dictionary();
        messageTo(objMission, "escortSuccess", dctParams, 0, true);
    }
    public void sendEscortIncomplete(obj_id objMission) throws InterruptedException
    {
        dictionary dctParams = new dictionary();
        messageTo(objMission, "escortIncomplete", dctParams, 0, true);
    }
    public boolean createEscortTarget(obj_id objMission) throws InterruptedException
    {
        obj_id objMissionData = getMissionData(objMission);
        location locSpawnLocation = getMissionStartLocation(objMissionData);
        location locEscortFinish = getMissionEndLocation(objMissionData);
        region[] rgnCities = getRegionsWithMunicipalAtPoint(locSpawnLocation, regions.MUNI_TRUE);
        if (rgnCities != null)
        {
            locSpawnLocation = locations.getGoodCityLocation(rgnCities[0], locSpawnLocation.area);
        }
        else 
        {
            locSpawnLocation = locations.getGoodLocationAroundLocation(locSpawnLocation, 1, 1, 128, 128, false, true);
            rgnCities = getRegionsWithMunicipalAtPoint(locSpawnLocation, regions.MUNI_TRUE);
            if (rgnCities != null)
            {
                locSpawnLocation = locations.getGoodCityLocation(rgnCities[0], locSpawnLocation.area);
            }
        }
        if (locSpawnLocation == null)
        {
            return false;
        }
        String strNPCType = getStringObjVar(objMissionData, "strNPCType");
        strNPCType = "townsperson";
        obj_id objEscortTarget = create.object(NPC_TYPES[rand(0, NPC_TYPES.length - 1)], locSpawnLocation);
        obj_id objPlayer = getMissionHolder(objMission);
        if (!isIdValid(objEscortTarget))
        {
            return false;
        }
        else 
        {
            updateMissionWaypoint(objMission, locSpawnLocation);
            setObjVar(objMission, "objEscortTarget", objEscortTarget);
            setObjVar(objEscortTarget, "objMission", objMission);
            setObjVar(objEscortTarget, "objEscorter", objPlayer);
            setObjVar(objEscortTarget, "locEscortFinish", locEscortFinish);
            attachScript(objEscortTarget, "systems.missions.dynamic.mission_escort_target");
            persistAndRegisterObject(objEscortTarget, objMission);
        }
        return true;
    }
    public void escortFail(obj_id objMission) throws InterruptedException
    {
        dictionary dctParams = new dictionary();
        messageTo(objMission, "escortFailure", dctParams, 0, true);
    }
    public void escortSuccess(obj_id objMission) throws InterruptedException
    {
        dictionary dctParams = new dictionary();
        messageTo(objMission, "escortSuccess", dctParams, 0, true);
    }
    public void escortIncomplete(obj_id objMission) throws InterruptedException
    {
        dictionary dctParams = new dictionary();
        messageTo(objMission, "escortIncomplete", dctParams, 0, true);
    }
    public boolean setupFetchMissionObject(obj_id objMission) throws InterruptedException
    {
        obj_id objMissionData = getMissionData(objMission);
        location locPickupLocation = getMissionStartLocation(objMissionData);
        location locDropoffLocation = getMissionEndLocation(objMissionData);
        return true;
    }
    public boolean createFetchPickupNPC(obj_id objMission) throws InterruptedException
    {
        return true;
    }
    public boolean createFetchDropoffNPC(obj_id objMission) throws InterruptedException
    {
        obj_id objMissionData = getMissionData(objMission);
        location locDropoffLocation = getMissionEndLocation(objMissionData);
        String strDropoffNPC = getStringObjVar(objMissionData, "strDropoffNPC");
        obj_id objDropoffNPC = create.object(NPC_TYPES[rand(0, NPC_TYPES.length - 1)], locDropoffLocation);
        if (!isIdValid(objDropoffNPC))
        {
            return false;
        }
        else 
        {
            attachScript(objDropoffNPC, "systems.missions.dynamic.mission_fetch_dropoff");
            setObjVar(objDropoffNPC, "objMission", objMission);
            setObjVar(objDropoffNPC, "locSpawnLocation", locDropoffLocation);
        }
        return true;
    }
    public void fetchFail(obj_id objMission) throws InterruptedException
    {
        dictionary dctParams = new dictionary();
        setObjVar(objMission, "intState", missions.STATE_MISSION_COMPLETE);
        messageTo(objMission, "fetchFailure", dctParams, 0, true);
    }
    public void fetchSuccess(obj_id objMission) throws InterruptedException
    {
        dictionary dctParams = new dictionary();
        setObjVar(objMission, "intState", missions.STATE_MISSION_COMPLETE);
        messageTo(objMission, "fetchSuccess", dctParams, 0, true);
    }
    public void fetchIncomplete(obj_id objMission) throws InterruptedException
    {
        dictionary dctParams = new dictionary();
        setObjVar(objMission, "intState", missions.STATE_MISSION_COMPLETE);
        messageTo(objMission, "fetchIncomplete", dctParams, 0, true);
    }
    public boolean verifyDeliveryItem(obj_id objPlayer, obj_id objDeliveryItem) throws InterruptedException
    {
        obj_id objInventory = getObjectInSlot(objPlayer, "inventory");
        obj_id[] objContents = getContents(objInventory);
        if (objContents == null)
        {
            return false;
        }
        else 
        {
            int intI = 0;
            while (intI < objContents.length)
            {
                if (objContents[intI] == objDeliveryItem)
                {
                    return true;
                }
                intI = intI + 1;
            }
        }
        return false;
    }
    public void sendDeliverFail(obj_id objMission) throws InterruptedException
    {
        dictionary dctParams = new dictionary();
        setObjVar(objMission, "intState", missions.STATE_MISSION_COMPLETE);
        messageTo(objMission, "deliverFailure", dctParams, 0, true);
    }
    public void sendDeliverSuccess(obj_id objMission) throws InterruptedException
    {
        dictionary dctParams = new dictionary();
        setObjVar(objMission, "intState", missions.STATE_MISSION_COMPLETE);
        messageTo(objMission, "deliverSuccess", dctParams, 0, true);
    }
    public void sendDeliverIncomplete(obj_id objMission) throws InterruptedException
    {
        dictionary dctParams = new dictionary();
        setObjVar(objMission, "intState", missions.STATE_MISSION_COMPLETE);
        messageTo(objMission, "deliverIncomplete", dctParams, 0, true);
    }
    public boolean createDeliverPickupNPC(obj_id objMission) throws InterruptedException
    {
        obj_id objPlayer = getMissionHolder(objMission);
        obj_id objMissionData = getMissionData(objMission);
        String strPickupNPC = getStringObjVar(objMissionData, "strPickupNPC");
        location locSpawnLocation = getMissionStartLocation(objMissionData);
        obj_id objPickupNPC = create.object(strPickupNPC, locSpawnLocation);
        if (!isIdValid(objPickupNPC))
        {
            return false;
        }
        else 
        {
            updateMissionWaypoint(objMission, locSpawnLocation);
            attachScript(objPickupNPC, "systems.missions.dynamic.mission_deliver_pickup");
            setObjVar(objPickupNPC, "objMission", objMission);
            setObjVar(objPickupNPC, "locSpawnLocation", locSpawnLocation);
            setObjVar(objPickupNPC, "objPlayer", objPlayer);
            persistAndRegisterObject(objPickupNPC, objMission);
            setWantSawAttackTriggers(objPickupNPC, false);
        }
        return true;
    }
    public boolean createDeliverDropoffNPC(obj_id objMission) throws InterruptedException
    {
        obj_id objMissionData = getMissionData(objMission);
        String strDropoffNPC = getStringObjVar(objMissionData, "strDropoffNPC");
        location locSpawnLocation = getMissionEndLocation(objMissionData);
        obj_id objDropoffNPC = create.object(strDropoffNPC, locSpawnLocation);
        obj_id objPlayer = getMissionHolder(objMission);
        if (!isIdValid(objDropoffNPC))
        {
            return false;
        }
        else 
        {
            persistAndRegisterObject(objDropoffNPC, objMission);
            updateMissionWaypoint(objMission, locSpawnLocation);
            attachScript(objDropoffNPC, "systems.missions.dynamic.mission_deliver_dropoff");
            setObjVar(objDropoffNPC, "objMission", objMission);
            setObjVar(objDropoffNPC, "locSpawnLocation", locSpawnLocation);
            setObjVar(objDropoffNPC, "objPlayer", objPlayer);
            setWantSawAttackTriggers(objDropoffNPC, false);
        }
        return true;
    }
    public dictionary getDestructionMissionSpawnList(obj_id objCreator, int intDifficulty, String strPlanet, String strSpawnType, String strFaction) throws InterruptedException
    {
        strFaction = toLower(strFaction);
        String strFileName;
        if (strFaction.equals(""))
        {
            strFileName = "datatables/missions/" + strPlanet + "/" + strPlanet + "_destroy_" + strSpawnType + ".iff";
        }
        else 
        {
            strFileName = "datatables/missions/faction/" + strFaction + "_destroy" + ".iff";
        }
        int intOverrideMissionIndex = -1;
        if (hasObjVar(objCreator, "missionTerminalOverrideDestroyMissionFileName") && hasObjVar(objCreator, "missionTerminalOverrideDestroyMissionIndex"))
        {
            String strOverrideFileName = getStringObjVar(objCreator, "missionTerminalOverrideDestroyMissionFileName");
            intOverrideMissionIndex = getIntObjVar(objCreator, "missionTerminalOverrideDestroyMissionIndex");
            sendSystemMessageTestingOnly(objCreator, "using mission #" + intOverrideMissionIndex + " from " + strOverrideFileName + " instead of " + strFileName);
            strFileName = strOverrideFileName;
            intOverrideMissionIndex -= 3;
        }
        int[] intMinDifficulty = dataTableGetIntColumn(strFileName, "intMinDifficulty");
        int[] intMaxDifficulty = dataTableGetIntColumn(strFileName, "intMaxDifficulty");
        int maxLength = intMinDifficulty.length;
        Vector okRows = new Vector();
        okRows.setSize(0);
        if (intOverrideMissionIndex >= 0)
        {
            okRows = utils.addElement(okRows, intOverrideMissionIndex);
        }
        else 
        {
            for (int j = 0; j < maxLength; j++)
            {
                if ((intMinDifficulty[j] <= intDifficulty) && (intMaxDifficulty[j] >= intDifficulty))
                {
                    okRows = utils.addElement(okRows, j);
                }
            }
        }
        dictionary row = null;
        if (okRows.size() > 0)
        {
            int randRow = rand(0, (okRows.size() - 1));
            randRow = ((Integer)okRows.get(randRow)).intValue();
            row = dataTableGetRow(strFileName, randRow);
            String strRewardFileName = "datatables/missions/destroy/destroy_rewards.iff";
            int minReward = dataTableGetInt(strRewardFileName, intDifficulty, "intMinReward");
            int maxReward = dataTableGetInt(strRewardFileName, intDifficulty, "intMaxReward");
            int intGCWPoints = 0;
            if (!strFaction.equals("") && strFaction.length() > 0)
            {
                intGCWPoints = dataTableGetInt(strRewardFileName, intDifficulty, "intGCWPoints");
            }
            row.put("intMinReward", minReward);
            row.put("intMaxReward", maxReward);
            row.put("intGCWPoints", intGCWPoints);
        }
        return row;
    }
    public String getOverloadTemplate(int intDifficulty, String strFaction, int intFaction) throws InterruptedException
    {
        String strFileName = "datatables/spawning/spawn_lists/overloads/spawn_overloads.iff";
        int intI = 1;
        dictionary dctSpawnInformation = new dictionary();
        int[] intDifficulties = dataTableGetIntColumn(strFileName, "intMinDifficulty");
        String[] strTemplates = new String[intDifficulties.length];
        int intArrayIndex = 0;
        while (intI < intDifficulties.length)
        {
            dctSpawnInformation = dataTableGetRow(strFileName, intI);
            int intMinTemplateDifficulty = dctSpawnInformation.getInt("intMinDifficulty");
            int intMaxTemplateDifficulty = dctSpawnInformation.getInt("intMaxDifficulty");
            String strTemplateFaction = dctSpawnInformation.getString("strFaction");
            int intTemplateFaction = dctSpawnInformation.getInt("intFaction");
            int intMinTemplateFaction = intTemplateFaction - 250;
            int intMaxTemplateFaction = intTemplateFaction + 250;
            if (intMinTemplateFaction < 0)
            {
                intMinTemplateFaction = 0;
            }
            if (intMaxTemplateFaction > 1000)
            {
                intMaxTemplateFaction = 1000;
            }
            if ((intMinTemplateDifficulty < intDifficulty) && (intMaxTemplateDifficulty > intDifficulty))
            {
                if (!strTemplateFaction.equals("neutral"))
                {
                    if (!strTemplateFaction.equals(strFaction))
                    {
                        if ((intMinTemplateFaction < intFaction) && (intMaxTemplateFaction > intFaction))
                        {
                            strTemplates[intArrayIndex] = dctSpawnInformation.getString("strTemplate");
                            intArrayIndex = intArrayIndex + 1;
                        }
                    }
                }
                else 
                {
                    strTemplates[intArrayIndex] = dctSpawnInformation.getString("strTemplate");
                    intArrayIndex = intArrayIndex + 1;
                }
            }
            intI = intI + 1;
        }
        if (intArrayIndex > 0)
        {
            int intId = rand(0, intArrayIndex);
            return strTemplates[intId];
        }
        return null;
    }
    public void setupContextualizationStrings(obj_id objMissionData, int intMissionDifficulty, obj_id objCreator, String strMissionFaction) throws InterruptedException
    {
        setupContextualizationStrings(objMissionData, intMissionDifficulty, objCreator, strMissionFaction, missions.BOUNTY_FLAG_NONE);
    }
    public void setupContextualizationStrings(obj_id objMissionData, int intMissionDifficulty, obj_id objCreator, String strMissionFaction, int flag) throws InterruptedException
    {
        if (strMissionFaction == null)
        {
            strMissionFaction = "";
        }
        strMissionFaction = toLower(strMissionFaction);
        String strTemplate;
        String strMissionType = getMissionType(objMissionData);
        if (strMissionType == null)
        {
            return;
        }
        boolean boolNPC = false;
        String strIdFileName = "mission_";
        if (ai_lib.aiGetNiche(objCreator) == NICHE_NPC)
        {
            boolNPC = true;
        }
        else 
        {
        }
        boolean boolRandomName = false;
        if (hasObjVar(objCreator, "intScout"))
        {
            boolNPC = true;
            boolRandomName = true;
        }
        else if (hasObjVar(objCreator, "intArtisan"))
        {
            boolNPC = true;
            boolRandomName = true;
        }
        else if (hasObjVar(objCreator, "intEntertainer"))
        {
            boolNPC = true;
            boolRandomName = true;
        }
        if (boolNPC)
        {
            strIdFileName = strIdFileName + "npc_";
        }
        if (!strMissionFaction.equals(""))
        {
            strIdFileName = strIdFileName + strMissionType + "_" + strMissionFaction + "_";
        }
        else 
        {
            strIdFileName = strIdFileName + strMissionType + "_neutral_";
        }
        if (intMissionDifficulty == MISSION_DIFFICULTY_EASY)
        {
            strIdFileName = strIdFileName + "easy";
        }
        else if (intMissionDifficulty == MISSION_DIFFICULTY_MEDIUM)
        {
            strIdFileName = strIdFileName + "medium";
        }
        else if (intMissionDifficulty == MISSION_DIFFICULTY_HARD)
        {
            strIdFileName = strIdFileName + "hard";
        }
        else if (intMissionDifficulty == MISSION_DIFFICULTY_BOSS)
        {
            strIdFileName = strIdFileName + "hard";
        }
        else if (intMissionDifficulty == MISSION_BOUNTY_DIFFICULTY_JEDI)
        {
            strIdFileName = "mission_bounty_jedi";
        }
        else 
        {
            strIdFileName = strIdFileName + "easy";
            return;
        }
        if (hasObjVar(objMissionData, "strGoal"))
        {
            strTemplate = getStringObjVar(objMissionData, "strGoal");
        }
        if (strMissionType.equals("destroy"))
        {
            if (strMissionFaction.equals(""))
            {
                String strTargetType = getStringObjVar(objMissionData, "strTargetType");
                strIdFileName = strIdFileName + "_" + strTargetType;
                location locMissionStartLocation = getMissionStartLocation(objMissionData);
                String strPlanet = locMissionStartLocation.area;
                if (strPlanet.equals("tatooine"))
                {
                    if (!boolNPC)
                    {
                        strIdFileName = strIdFileName + "_tatooine";
                    }
                }
                else if (strPlanet.equals("naboo"))
                {
                    if (!boolNPC)
                    {
                        strIdFileName = strIdFileName + "_naboo";
                    }
                }
                else if (strPlanet.equals("corellia"))
                {
                    if (!boolNPC)
                    {
                        strIdFileName = strIdFileName + "_corellia";
                    }
                }
            }
        }
        strIdFileName = "mission/" + strIdFileName;
        int intStringQuantity = 1;
        string_id strEntryId = new string_id(strIdFileName, "number_of_entries");
        String strNumberOfEntries = getString(strEntryId);
        if (strNumberOfEntries != null)
        {
            if (strNumberOfEntries == null)
            {
                intStringQuantity = 0;
            }
            else if (!strNumberOfEntries.equals(""))
            {
                Integer intTest = new Integer(0);
                boolean boolError = false;
                try
                {
                    intTest = new Integer(strNumberOfEntries);
                }
                catch(NumberFormatException err)
                {
                    boolError = true;
                }
                if (!boolError)
                {
                    intStringQuantity = intTest.intValue();
                    if (intStringQuantity < 0)
                    {
                        intStringQuantity = 1;
                    }
                }
            }
        }
        int intId = rand(1, intStringQuantity);
        if (intMissionDifficulty == MISSION_BOUNTY_DIFFICULTY_JEDI)
        {
            if (strMissionFaction.equals("imperial"))
            {
                intId = 2;
            }
            else if (strMissionFaction.equals("rebel"))
            {
                intId = 1;
            }
            else 
            {
                intId = 3;
            }
        }
        setObjVar(objMissionData, "intStringId", intId);
        setObjVar(objMissionData, "strIdFileName", strIdFileName);
        location locTest = new location();
        int intStringId = intId;
        String flagChar = "";
        if (flag == missions.BOUNTY_FLAG_SMUGGLER)
        {
            flagChar = "s";
        }
        string_id strTitleId = new string_id(strIdFileName, "m" + intStringId + "t" + flagChar);
        String strDescription;
        if (boolNPC)
        {
            /*
            // Cekis:  mission_npc_hunting_neutral_medium
            // this is breaking non-player city mission terminals where the description is not
            // showing properly.  This commenting out MAY break Player city mission terminals
            // but it is not known - need to revisit later.
            if (isObjectPersisted(objCreator))
            {
                strDescription = "m" + intStringId + "o";
            }
            else 
            {
                strDescription = "m" + intStringId + "d" + flagChar;
            }
            */
            // so, instead, just set the default description to be the one we KNOW is missing from non-player city terminals.
            strDescription = "m" + intStringId + "o" + flagChar;

            String strTest = getMissionType(objMissionData);
            if (strTest.equals("survey"))
            {
                strDescription = "m" + intStringId + "o";
            }
            else if (strTest.equals("crafting"))
            {
                strDescription = "m" + intStringId + "d" + flagChar;
            }
            else if (strTest.equals("dancer"))
            {
                strDescription = "m" + intStringId + "o";
            }
            else if (strTest.equals("musician"))
            {
                strDescription = "m" + intStringId + "o";
            }
        }
        else 
        {
            strDescription = "m" + intStringId + "d" + flagChar;
        }
        string_id strDescriptionId = new string_id(strIdFileName, strDescription);
        String strName = "";
        if (ai_lib.aiGetNiche(objCreator) != NICHE_NPC)
        {
            if (intMissionDifficulty == MISSION_BOUNTY_DIFFICULTY_JEDI)
            {
                if (strMissionFaction.equals("imperial"))
                {
                    strName = "The Galactic Empire";
                }
                else if (strMissionFaction.equals("rebel"))
                {
                    strName = "The Alliance";
                }
                else 
                {
                    strName = "Corporate Sector Authority";
                }
            }
            else if (!boolRandomName)
            {
                string_id strCreator = new string_id(strIdFileName, "m" + intStringId + "o");
                String strText = getString(strCreator);
                if (strText != null)
                {
                    if (strText.equals(""))
                    {
                        strName = getRandomMissionCreatorName();
                        setObjVar(objMissionData, "strCreatorName", strName);
                    }
                    else 
                    {
                        strName = getString(strCreator);
                    }
                }
                else 
                {
                    strName = getString(strCreator);
                }
            }
            else 
            {
                strName = getRandomMissionCreatorName();
            }
            setMissionCreator(objMissionData, strName);
        }
        else 
        {
            strName = getName(objCreator);
            setMissionCreator(objMissionData, strName);
        }
        setMissionTitle(objMissionData, strTitleId);
        setMissionDescription(objMissionData, strDescriptionId);
        return;
    }
    public void sendMissionPersistentMessage(obj_id objMission, String strType) throws InterruptedException
    {
        return;
    }
    public void createDeliverInterestNPC(obj_id objMission) throws InterruptedException
    {
        obj_id objMissionData = getMissionData(objMission);
        obj_id objPlayer = getMissionHolder(objMission);
        location locGoodLocation = new location();
        int intMissionDifficulty = getIntObjVar(objMissionData, "intMissionDifficulty");
        if (hasObjVar(objMission, "intInterestActive"))
        {
            return;
        }
        locGoodLocation = locations.getGoodLocationAroundLocation(getMissionLocation(objPlayer), 1, 1, 200, 200, false, true);
        if (locGoodLocation == null)
        {
            return;
        }
        if (intMissionDifficulty == MISSION_DIFFICULTY_MEDIUM)
        {
            obj_id objNPC = create.object(NPC_TYPES[rand(0, NPC_TYPES.length - 1)], locGoodLocation);
            if (!isIdValid(objNPC))
            {
                return;
            }
            setObjVar(objNPC, "objPlayer", objPlayer);
            setObjVar(objNPC, "objMission", objMission);
            setObjVar(objMission, "intInterestActive", 1);
            setObjVar(objNPC, "objMissionData", objMissionData);
            persistAndRegisterObject(objNPC, objMission);
            int intInterest = getIntObjVar(objMission, "intInterest");
            intInterest = intInterest - 1;
            if (intInterest < 1)
            {
                removeObjVar(objMission, "intInterest");
            }
            else 
            {
                setObjVar(objMission, "intInterest", intInterest);
            }
            int intRoll = rand(1, 10);
            if (intRoll < 1)
            {
                attachScript(objNPC, "systems.missions.dynamic.mission_deliver_interest_npc_demand");
            }
            else if (intRoll < 10)
            {
                attachScript(objNPC, "systems.missions.dynamic.mission_deliver_interest_npc_escort");
            }
            else if (intRoll < 10)
            {
                attachScript(objNPC, "systems.missions.dynamic.mission_deliver_interest_npc_single_attacker");
            }
        }
        return;
    }
    public void cleanUpInterestNPC(obj_id objNPC) throws InterruptedException
    {
        obj_id objMission = getObjIdObjVar(objNPC, "objMission");
        destroyObject(objNPC);
        return;
    }
    public void removeSpawnedTemplateElement(obj_id objMissionData, obj_id objTemplate) throws InterruptedException
    {
        int intI = 0;
        if (hasObjVar(objMissionData, "objSpawnedTemplates"))
        {
            Vector objSpawnedTemplates = getResizeableObjIdArrayObjVar(objMissionData, "objSpawnedTemplates");
            while (intI < objSpawnedTemplates.size())
            {
                if (((obj_id)objSpawnedTemplates.get(intI)) == objTemplate)
                {
                    objSpawnedTemplates = utils.removeElementAt(objSpawnedTemplates, intI);
                    return;
                }
                intI = intI + 1;
            }
        }
    }
    public String getRandomMissionCreatorName() throws InterruptedException
    {
        String strTemplate = "object/creature/player/human_male.iff";
        int intRoll = rand(1, 8);
        switch (intRoll)
        {
            case 1:
            strTemplate = "object/creature/player/bothan_male.iff";
            break;
            case 2:
            strTemplate = "object/creature/player/moncal_male.iff";
            break;
            case 3:
            strTemplate = "object/creature/player/trandoshan_male.iff";
            break;
            case 4:
            strTemplate = "object/creature/player/twilek_male.iff";
            break;
            case 5:
            strTemplate = "object/creature/player/rodian_male.iff";
            break;
            case 6:
            strTemplate = "object/creature/player/zabrak_male.iff";
            break;
            case 7:
            strTemplate = "object/creature/player/wookiee_male.iff";
            break;
            case 8:
            strTemplate = "object/creature/player/human_male.iff";
            break;
        }
        String strName = generateRandomName(strTemplate);
        return strName;
    }
    public String getRandomSpeciesName(String strSpecies) throws InterruptedException
    {
        String strTemplate = "";
        if (strSpecies.equals("alien"))
        {
            int intRoll = rand(1, 8);
            switch (intRoll)
            {
                case 1:
                strTemplate = "object/creature/player/bothan_male.iff";
                break;
                case 2:
                strTemplate = "object/creature/player/moncal_male.iff";
                break;
                case 3:
                strTemplate = "object/creature/player/trandoshan_male.iff";
                break;
                case 4:
                strTemplate = "object/creature/player/twilek_male.iff";
                break;
                case 5:
                strTemplate = "object/creature/player/rodian_male.iff";
                break;
                case 6:
                strTemplate = "object/creature/player/zabrak_male.iff";
                break;
                case 7:
                strTemplate = "object/creature/player/wookiee_male.iff";
                break;
                case 8:
                strTemplate = "object/creature/player/human_male.iff";
                break;
            }
        }
        else 
        {
            if (strSpecies.equals("bothan"))
            {
                strTemplate = "object/creature/player/bothan_male.iff";
            }
            else if (strSpecies.equals("moncal"))
            {
                strTemplate = "object/creature/player/moncal_male.iff";
            }
            else if (strSpecies.equals("wookiee"))
            {
                strTemplate = "object/creature/player/wookiee_male.iff";
            }
            else if (strSpecies.equals("trandoshan"))
            {
                strTemplate = "object/creature/player/trandoshan_male.iff";
            }
            else if (strSpecies.equals("twilek"))
            {
                strTemplate = "object/creature/player/twilek_male.iff";
            }
            else if (strSpecies.equals("human"))
            {
                strTemplate = "object/creature/player/human_male.iff";
            }
            else if (strSpecies.equals("zabrak"))
            {
                strTemplate = "object/creature/player/zabrak_male.iff";
            }
            else if (strSpecies.equals("rodian"))
            {
                strTemplate = "object/creature/player/rodian_male.iff";
            }
            else 
            {
                strTemplate = "object/creature/player/human_male.iff";
            }
        }
        String strName = generateRandomName(strTemplate);
        return strName;
    }
    public void preLoadDestructionDataTables(obj_id objMissionBoard, String strPlanet) throws InterruptedException
    {
        return;
    }
    public void registerBountyInformant(obj_id objInformant) throws InterruptedException
    {
        location locCurrentLocation = getMissionLocation(objInformant);
        obj_id objPlanetObject = getPlanetByName(locCurrentLocation.area);
        int intInformantLevel = getIntObjVar(objInformant, "intInformantLevel");
        String strInformantLevel = "informant_level_" + intInformantLevel;
        String strPlanetName = locCurrentLocation.area;
        deltadictionary dctMissionInformation = objPlanetObject.getScriptVars();
        String strArrayName = strPlanetName + "_" + strInformantLevel;
        Vector objInformants = dctMissionInformation.getResizeableObjIdArray(strArrayName);
        objInformants = utils.addElement(objInformants, objInformant);
        dctMissionInformation.put(strArrayName, objInformants);
        return;
    }
    public void removeBountyInformant(obj_id objInformant) throws InterruptedException
    {
        location locCurrentLocation = getMissionLocation(objInformant);
        obj_id objPlanetObject = getPlanetByName(locCurrentLocation.area);
        int intInformantLevel = getIntObjVar(objInformant, "intInformantLevel");
        String strInformantLevel = "informant_level_" + intInformantLevel;
        deltadictionary dctMissionInformation = objPlanetObject.getScriptVars();
        String strPlanetName = locCurrentLocation.area;
        String strArrayName = strPlanetName + "_" + strInformantLevel;
        Vector objInformants = dctMissionInformation.getResizeableObjIdArray(strArrayName);
        Vector strInformantNames = dctMissionInformation.getResizeableStringArray(strArrayName + "names");
        if (objInformants != null)
        {
            int intIndex = utils.getElementPositionInArray(objInformants, objInformant);
            if (intIndex != -1)
            {
                objInformants = utils.removeElementAt(objInformants, intIndex);
                strInformantNames = utils.removeElementAt(strInformantNames, intIndex);
            }
            dctMissionInformation.put(strArrayName, objInformants);
        }
        return;
    }
    public void setupInvisibleWaypoint(obj_id objMission) throws InterruptedException
    {
        location locSpawnLocation = getLocation(objMission);
        obj_id objTargetWaypoint = createWaypointInMissionObject(objMission, locSpawnLocation);
        if (isIdValid(objTargetWaypoint))
        {
            setObjVar(objMission, "objTargetWaypoint", objTargetWaypoint);
            setWaypointColor(objTargetWaypoint, "invisible");
            string_id strWaypointName = getMissionTitle(objMission);
            String strString = utils.packStringId(strWaypointName);
            setWaypointName(objTargetWaypoint, strString);
            setName(objMission, strWaypointName);
        }
    }
    public boolean updateMissionWaypoint(obj_id objMission, location locSpawnLocation) throws InterruptedException
    {
        if (!isIdValid(objMission))
        {
            return false;
        }
        obj_id objMissionData = getMissionData(objMission);
        if (locSpawnLocation == null)
        {
            return false;
        }
        obj_id objTargetWaypoint;
        if (!hasObjVar(objMission, "objTargetWaypoint"))
        {
            objTargetWaypoint = createWaypointInMissionObject(objMission, locSpawnLocation);
            if (isIdValid(objTargetWaypoint))
            {
                setObjVar(objMission, "objTargetWaypoint", objTargetWaypoint);
                string_id strWaypointName = getMissionTitle(objMissionData);
                String strString = utils.packStringId(strWaypointName);
                setWaypointName(objTargetWaypoint, strString);
                setName(objMission, strWaypointName);
            }
        }
        else 
        {
            objTargetWaypoint = getObjIdObjVar(objMission, "objTargetWaypoint");
        }
        if (!isIdValid(objTargetWaypoint))
        {
            return false;
        }
        setWaypointColor(objTargetWaypoint, "orange");
        setWaypointLocation(objTargetWaypoint, locSpawnLocation);
        setWaypointActive(objTargetWaypoint, true);
        setWaypointVisible(objTargetWaypoint, true);
        obj_id player = getMissionHolder(objMission);
        if (isIdValid(player))
        {
            dictionary msgData = new dictionary();
            msgData.put("missionId", objMission);
            messageTo(player, "missionWaypointUpdated", msgData, 0, false);
        }
        return true;
    }
    public void activateMissionWaypoint(obj_id objMission) throws InterruptedException
    {
        if (hasObjVar(objMission, "objTargetWaypoint"))
        {
            obj_id objTargetWaypoint = getObjIdObjVar(objMission, "objTargetWaypoint");
            setWaypointActive(objTargetWaypoint, true);
        }
        return;
    }
    public obj_id generateNPCMissions(obj_id objNPC) throws InterruptedException
    {
        obj_id objMissionData = createMissionObjectInCreatureMissionBag(objNPC);
        String[] strMissionTypes = getStringArrayObjVar(objNPC, "mission.strMissionTypes");
        if (strMissionTypes == null)
        {
            return null;
        }
        int intDifficulty;
        int intMinDifficulty = getIntObjVar(objNPC, "mission.intMinDifficulty");
        int intMaxDifficulty = getIntObjVar(objNPC, "mission.intMaxDifficulty");
        if (intMinDifficulty == 0)
        {
            intMinDifficulty = 1;
        }
        if (intMaxDifficulty == 0)
        {
            intMaxDifficulty = intMinDifficulty + 10;
            if (intMaxDifficulty > 20)
            {
                intMaxDifficulty = 20;
            }
        }
        intDifficulty = rand(intMinDifficulty, intMaxDifficulty);
        String strNPCType = getStringObjVar(objNPC, "strNPCType");
        location locCurrentLocation = getLocation(objNPC);
        obj_id foo = getTopMostContainer(objNPC);
        if (!isIdValid(foo))
        {
        }
        else 
        {
            locCurrentLocation = getLocation(foo);
        }
        String strPlanet = locCurrentLocation.area;
        String strFaction = "";
        if (hasObjVar(objNPC, factions.FACTION))
        {
            strFaction = factions.getFaction(objNPC);
            if (!strFaction.equals("Imperial") && !strFaction.equals("Rebel"))
            {
                strFaction = "";
            }
        }
        obj_id objCreator = null;
        if (hasObjVar(objNPC, "mission.intPersistent"))
        {
            objCreator = objNPC;
        }
        else 
        {
            objCreator = utils.getMissionBag(objNPC);
        }
        int intI = 0;
        while (intI < strMissionTypes.length)
        {
            if (!hasObjVar(objNPC, strMissionTypes[intI]))
            {
                String strMissionType = strMissionTypes[intI];
                if (strMissionType.equals("mission.objReconMission"))
                {
                    objMissionData = createReconMission(objMissionData, objNPC, locCurrentLocation, intDifficulty, strPlanet, "");
                }
                else if (strMissionType.equals("mission.objHuntingMission"))
                {
                    objMissionData = createHuntingMission(objMissionData, objNPC, intDifficulty, "");
                }
                else if (strMissionType.equals("mission.objDestroyMission"))
                {
                    boolean boolDeclaredOnly = false;
                    if (!strFaction.equals(""))
                    {
                        if (hasObjVar(objNPC, "mission.intDeclaredOnly"))
                        {
                            boolDeclaredOnly = true;
                            strFaction = strFaction + "_military";
                        }
                        else 
                        {
                            strFaction = strFaction + "_non_military";
                        }
                    }
                    objMissionData = createDestructionMissionDataFromLocation(objMissionData, objCreator, locCurrentLocation, intDifficulty, strPlanet, strFaction, 1.0f);
                    if (isIdValid(objMissionData))
                    {
                        if (boolDeclaredOnly)
                        {
                            setObjVar(objMissionData, "boolDeclaredOnly", true);
                        }
                        if (!hasObjVar(objNPC, "mission.intPersistent"))
                        {
                            reContextualizationTerminalToNPCMission(objMissionData, objNPC);
                        }
                        if (hasObjVar(objNPC, "mission.strNPCName"))
                        {
                            objMissionData = dynamicDestroyToThemePark(objMissionData, objNPC);
                        }
                    }
                }
                else if (strMissionType.equals("mission.objDeliverMission"))
                {
                    objMissionData = createDeliverMissionFromLocation(objMissionData, objCreator, locCurrentLocation, intDifficulty, strPlanet, strFaction, 1.0f);
                    if (isIdValid(objMissionData))
                    {
                        if (!hasObjVar(objNPC, "mission.intPersistent"))
                        {
                            reContextualizationTerminalToNPCMission(objMissionData, objNPC);
                        }
                        if (hasObjVar(objNPC, "mission.strNPCName"))
                        {
                            objMissionData = dynamicDeliverToThemePark(objMissionData, objNPC);
                        }
                    }
                }
                else if (strMissionType.equals("mission.objEscortMission"))
                {
                    objMissionData = createEscortToCreatorMission(objMissionData, objNPC, locCurrentLocation, intDifficulty, strPlanet, strFaction);
                    if (isIdValid(objMissionData))
                    {
                        if (hasObjVar(objNPC, "mission.strNPCName"))
                        {
                            objMissionData = dynamicEscortToCreatorToThemePark(objMissionData, objNPC);
                        }
                    }
                }
                else if (strMissionType.equals("mission.objDancerMission"))
                {
                    objMissionData = createDancerMission(objMissionData, objNPC, 0, strFaction);
                }
                else if (strMissionType.equals("mission.objMusicianMission"))
                {
                    objMissionData = createMusicianMission(objMissionData, objNPC, 0, strFaction);
                }
                else if (strMissionType.equals("mission.objSurveyMission"))
                {
                    objMissionData = createSurveyMission(objMissionData, objNPC, intDifficulty, strFaction);
                }
                else if (strMissionType.equals("mission.objCraftingMission"))
                {
                    objMissionData = createCraftingMission(objMissionData, objNPC, locCurrentLocation, intDifficulty, strFaction);
                }
                else 
                {
                    return null;
                }
                if (!isIdValid(objMissionData))
                {
                    return null;
                }
                else 
                {
                    setObjVar(objMissionData, "objCreator", objNPC);
                    return objMissionData;
                }
            }
            intI = intI + 1;
        }
        return null;
    }
    public void cleanUpMissions(obj_id objNPC) throws InterruptedException
    {
        int intI = 0;
        String[] strMissionTypes = getStringArrayObjVar(objNPC, "mission.strMissionTypes");
        while (intI < strMissionTypes.length)
        {
            if (hasObjVar(objNPC, strMissionTypes[intI]))
            {
                obj_id objMissionData = getObjIdObjVar(objNPC, strMissionTypes[intI]);
                removeObjVar(objNPC, strMissionTypes[intI]);
            }
            intI = intI + 1;
        }
        return;
    }
    public boolean hasMissionFromNPC(obj_id objPlayer, obj_id objNPC) throws InterruptedException
    {
        obj_id[] objMissionArray = getMissionObjects(objPlayer);
        int intI = 0;
        if (objMissionArray == null)
        {
            return false;
        }
        while (intI < objMissionArray.length)
        {
            obj_id objMissionData = getMissionData(objMissionArray[intI]);
            obj_id objMissionCreator = getObjIdObjVar(objMissionData, "objCreator");
            if (objMissionCreator == objNPC)
            {
                return true;
            }
            intI = intI + 1;
        }
        return false;
    }
    public obj_id getMissionObjectFromNPC(obj_id objNPC, int intPlayerDifficulty) throws InterruptedException
    {
        obj_id objMissionData = generateNPCMissions(objNPC);
        if (!isIdValid(objMissionData))
        {
            return null;
        }
        return objMissionData;
    }
    public void removeMissionObjectFromNPC(obj_id objNPC, obj_id objMissionData) throws InterruptedException
    {
        int intI = 0;
        String[] strMissionTypes = getStringArrayObjVar(objNPC, "mission.strMissionTypes");
        while (intI < strMissionTypes.length)
        {
            if (hasObjVar(objNPC, strMissionTypes[intI]))
            {
                if (objMissionData == getObjIdObjVar(objNPC, strMissionTypes[intI]))
                {
                    removeObjVar(objNPC, strMissionTypes[intI]);
                    return;
                }
            }
            intI = intI + 1;
        }
    }
    public void assignNPCMissionToPlayer(obj_id objNPC, obj_id objMissionData, obj_id objPlayer) throws InterruptedException
    {
        assignMission(objMissionData, objPlayer);
        removeMissionObjectFromNPC(objNPC, objMissionData);
        return;
    }
    public obj_id getAssignedNPCMission(obj_id objNPC, obj_id objPlayer) throws InterruptedException
    {
        obj_id[] objMissionArray = getMissionObjects(objPlayer);
        int intI = 0;
        if (objMissionArray == null)
        {
            return null;
        }
        while (intI < objMissionArray.length)
        {
            obj_id objMissionData = getMissionData(objMissionArray[intI]);
            obj_id objMissionCreator = getObjIdObjVar(objMissionData, "objCreator");
            if (objMissionCreator == objNPC)
            {
                return objMissionArray[intI];
            }
            intI = intI + 1;
        }
        return null;
    }
    public string_id sendNPCMissionSuccess(obj_id objMission, obj_id objNPC) throws InterruptedException
    {
        obj_id objMissionData = getMissionData(objMission);
        String strMissionType = getMissionType(objMissionData);
        if (strMissionType.equals("destroy"))
        {
            sendDestructionSuccess(objMission);
        }
        if (strMissionType.equals("deliver"))
        {
            sendDeliverSuccess(objMission);
        }
        if (strMissionType.equals("recon"))
        {
            sendReconSuccess(objMission);
        }
        string_id strResponse = getNPCMissionSuccessId(objMissionData);
        return strResponse;
    }
    public string_id getNPCMissionSuccessId(obj_id objMissionData) throws InterruptedException
    {
        int intStringId = getIntObjVar(objMissionData, "intStringId");
        String strIdFileName = getStringObjVar(objMissionData, "strIdFileName");
        string_id strMissionDescription = new string_id(strIdFileName, "m" + intStringId + "c");
        return strMissionDescription;
    }
    public string_id getNPCEscortPickupId(obj_id objMissionData) throws InterruptedException
    {
        int intStringId = getIntObjVar(objMissionData, "intStringId");
        String strIdFileName = getStringObjVar(objMissionData, "strIdFileName");
        string_id strPickupId = new string_id(strIdFileName, "m" + intStringId + "p");
        return strPickupId;
    }
    public string_id getNPCEscortDropoffId(obj_id objMissionData) throws InterruptedException
    {
        int intStringId = getIntObjVar(objMissionData, "intStringId");
        String strIdFileName = getStringObjVar(objMissionData, "strIdFileName");
        string_id strPickupId = new string_id(strIdFileName, "m" + intStringId + "e");
        return strPickupId;
    }
    public string_id getNPCMissionDescriptionId(obj_id objMissionData) throws InterruptedException
    {
        int intStringId = getIntObjVar(objMissionData, "intStringId");
        String strIdFileName = getStringObjVar(objMissionData, "strIdFileName");
        int intIndex = strIdFileName.indexOf("persistent");
        string_id strMissionDescription;
        String strMissionType = getMissionType(objMissionData);
        if (!strMissionType.equals("crafting"))
        {
            if (intIndex < 0)
            {
                strMissionDescription = new string_id(strIdFileName, "m" + intStringId + "o");
            }
            else 
            {
                strMissionDescription = new string_id(strIdFileName, "m" + intStringId + "d");
            }
        }
        else 
        {
            strMissionDescription = new string_id(strIdFileName, "m" + intStringId + "d");
        }
        return strMissionDescription;
    }
    public obj_id createReconMission(obj_id objMissionData, obj_id objCreator, location locMissionStart, int intDifficulty, String strPlanet, String strFaction) throws InterruptedException
    {
        setMissionRootScriptName(objMissionData, "systems.missions.dynamic.mission_recon");
        setMissionType(objMissionData, "recon");
        if (hasObjVar(objCreator, "intScout"))
        {
            setObjVar(objMissionData, "intTerminal", 1);
        }
        int intI = 0;
        String strSpawnType = "";
        region rgnSpawnRegion;
        location locMissionDestination = new location();
        float fltCityRegionExtent = 0;
        float fltRange;
        location locGoodLocation = null;
        float fltRewardModifier = 1;
        region[] rgnCities = new region[0];
        if (intDifficulty > RECON_OFF_PLANET_THRESHOLD)
        {
            int intRoll = rand(0, RECON_PLANETS.length - 1);
            String strEndPlanet = RECON_PLANETS[intRoll];
            String strStartPlanet = locMissionStart.area;
            if (!strEndPlanet.equals(strStartPlanet))
            {
                fltRewardModifier = RECON_OFF_PLANET_BASE_MODIFIER;
                fltRewardModifier = fltRewardModifier + RECON_DIFFICULTY_MODIFIER[intRoll];
            }
            boolean boolInCity = false;
            rgnCities = getRegionsWithMunicipal(strEndPlanet, regions.MUNI_TRUE);
            if (rgnCities == null)
            {
                rgnCities = getRegionsWithMission(strEndPlanet, regions.MISSION_OTHER);
                if (rgnCities == null)
                {
                    return null;
                }
                fltCityRegionExtent = 0;
            }
            else 
            {
                boolInCity = true;
                fltCityRegionExtent = locations.getRegionExtents(rgnCities[0]);
            }
        }
        else 
        {
            rgnCities = getRegionsWithMunicipalAtPoint(locMissionStart, regions.MUNI_TRUE);
            if (rgnCities != null)
            {
                fltCityRegionExtent = locations.getRegionExtents(rgnCities[0]);
            }
            else 
            {
                fltCityRegionExtent = 0;
            }
        }
        while (intI < LOCATION_CHECKS)
        {
            fltRange = fltCityRegionExtent + rand(MIN_RECON_DISTANCE, MAX_RECON_DISTANCE);
            if (fltRange < MIN_RECON_DISTANCE)
            {
                fltRange = MIN_RECON_DISTANCE;
            }
            location locNewLocation = utils.getRandomLocationInRing(locMissionStart, fltRange, fltRange + 200);
            locGoodLocation = locations.getGoodLocationAroundLocation(locNewLocation, 30, 30, 200, 200, false, true);
            if (locGoodLocation != null)
            {
                locMissionDestination = locGoodLocation;
                rgnCities = getRegionsWithMunicipalAtPoint(locMissionDestination, regions.MUNI_TRUE);
                if ((rgnCities != null))
                {
                    continue;
                }
                else 
                {
                    region rgnBattlefield = battlefield.getBattlefield(locGoodLocation);
                    if (rgnBattlefield != null)
                    {
                        continue;
                    }
                }
                break;
            }
            else 
            {
            }
            intI = intI + 1;
        }
        if (intI >= LOCATION_CHECKS)
        {
            return null;
        }
        if (locGoodLocation == null)
        {
            return null;
        }
        location locSpawnLocation = (location)locMissionDestination.clone();
        location locCreator = getMissionLocation(objCreator);
        float fltDistance = 0;
        fltDistance = utils.getDistance(locCreator, locSpawnLocation);
        setObjVar(objMissionData, "locCreatorLocation", locCreator);
        if (locSpawnLocation.area != locCreator.area)
        {
            if (fltDistance < 3000)
            {
                fltDistance = rand(3000, 4000);
            }
        }
        int intMissionDifficulty = 1;
        if (fltDistance < RECON_EASY_DISTANCE)
        {
            intMissionDifficulty = MISSION_DIFFICULTY_EASY;
        }
        else if (fltDistance < RECON_MEDIUM_DISTANCE)
        {
            intMissionDifficulty = MISSION_DIFFICULTY_MEDIUM;
        }
        else 
        {
            intMissionDifficulty = MISSION_DIFFICULTY_HARD;
        }
        location locTest = new location();
        String strTest = "object/tangible/mission/mission_recon_target.iff";
        setMissionTargetAppearance(objMissionData, strTest);
        setupContextualizationStrings(objMissionData, intMissionDifficulty, objCreator, strFaction);
        dictionary dctReconList = getReconSpawnList(objMissionData, locGoodLocation, intDifficulty);
        if (dctReconList == null)
        {
            return null;
        }
        int[] intSizes = dctReconList.getIntArray("intSizes");
        String[] strTemplates = dctReconList.getStringArray("strTemplates");
        String[] strLairTypes = dctReconList.getStringArray("strLairTypes");
        String[] strBuildingTypes = dctReconList.getStringArray("strBuildingTypes");
        int[] intMinDifficulties = dctReconList.getIntArray("intMinDifficulties");
        int[] intMaxDifficulties = dctReconList.getIntArray("intMaxDifficulties");
        int intRandomNumber = rand(0, strTemplates.length - 1);
        String strGoal = strTemplates[intRandomNumber];
        String strBuildingType = strBuildingTypes[intRandomNumber];
        String strLairType = strLairTypes[intRandomNumber];
        int intMinDifficulty = intMinDifficulties[intRandomNumber];
        int intMaxDifficulty = intMaxDifficulties[intRandomNumber];
        setObjVar(objMissionData, "strGoal", strGoal);
        setObjVar(objMissionData, "strLairType", strLairType);
        setObjVar(objMissionData, "strBuildingType", strBuildingType);
        setObjVar(objMissionData, "intMinDifficulty", intMinDifficulty);
        setObjVar(objMissionData, "intMaxDifficulty", intMaxDifficulty);
        setObjVar(objMissionData, "intPlayerDifficulty", intDifficulty);
        int intSize = intSizes[intRandomNumber];
        setObjVar(objMissionData, "intSize", intSize);
        obj_id groupObject = getGroupObject(objCreator);
        if (isIdValid(groupObject))
        {
            setObjVar(objMissionData, "originalGroupSize", getPCGroupSize(groupObject));
        }
        else 
        {
            setObjVar(objMissionData, "originalGroupSize", 1);
        }
        int intReward = getDeliverReward(locMissionStart, locSpawnLocation, RECON_REWARD_RATIO, 4);
        intReward = (int)(intReward * fltRewardModifier);
        intReward = intReward + rand(-100, 100);
        setMissionReward(objMissionData, intReward);
        dictionary dctParams = new dictionary();
        setMissionStartLocation(objMissionData, locSpawnLocation);
        setMissionDifficulty(objMissionData, intDifficulty);
        return objMissionData;
    }
    public dictionary getReconSpawnList(obj_id objMissionData, location locMissionLocation, int intPlayerDifficulty) throws InterruptedException
    {
        if (locMissionLocation == null)
        {
            return null;
        }
        dictionary dctReturn = new dictionary();
        int intStringId = getIntObjVar(objMissionData, "intStringId");
        String strIdFileName = getStringObjVar(objMissionData, "strIdFileName");
        string_id strReconFile = new string_id(strIdFileName, "m" + intStringId + "d");
        String strFileName = getString(strReconFile);
        if (strFileName != null)
        {
            strFileName = toLower(strFileName);
            strFileName = "datatables/missions/recon/recon_" + strFileName + ".iff";
            if (!dataTableOpen(strFileName))
            {
                strFileName = "datatables/missions/recon/recon_npc.iff";
            }
        }
        else 
        {
            strFileName = "datatables/missions/recon/recon_npc.iff";
        }
        int[] intSizes = dataTableGetIntColumn(strFileName, "intSize");
        String[] strTemplates = dataTableGetStringColumn(strFileName, "strTemplate");
        String[] strLairTypes = dataTableGetStringColumn(strFileName, "strLairType");
        String[] strBuildingTypes = dataTableGetStringColumn(strFileName, "strBuildingType");
        int[] intMinDifficulties = dataTableGetIntColumn(strFileName, "intMinDifficulty");
        int[] intMaxDifficulties = dataTableGetIntColumn(strFileName, "intMaxDifficulty");
        if (strTemplates == null)
        {
            return null;
        }
        dctReturn.put("intSizes", intSizes);
        dctReturn.put("strTemplates", strTemplates);
        dctReturn.put("strLairTypes", strLairTypes);
        dctReturn.put("strBuildingTypes", strBuildingTypes);
        dctReturn.put("intMinDifficulties", intMinDifficulties);
        dctReturn.put("intMaxDifficulties", intMaxDifficulties);
        return dctReturn;
    }
    public void sendReconFail(obj_id objMission) throws InterruptedException
    {
        dictionary dctParams = new dictionary();
        messageTo(objMission, "reconFail", dctParams, 0, true);
    }
    public void sendReconSuccess(obj_id objMission) throws InterruptedException
    {
        dictionary dctParams = new dictionary();
        messageTo(objMission, "reconSuccess", dctParams, 0, true);
    }
    public void sendReconIncomplete(obj_id objMission) throws InterruptedException
    {
        dictionary dctParams = new dictionary();
        messageTo(objMission, "reconIncomplete", dctParams, 0, true);
    }
    public void setupArrivedAtLocation(obj_id objTarget, obj_id objMission, location locTargetLocation, float fltDistance) throws InterruptedException
    {
        dictionary dctParams = new dictionary();
        dctParams.put("locTargetLocation", locTargetLocation);
        dctParams.put("objMission", objMission);
        dctParams.put("fltDistance", fltDistance);
        messageTo(objTarget, "setupArrivedAtLocation", dctParams, 0, true);
        return;
    }
    public boolean createReconTarget(obj_id objMission, obj_id objPlayer) throws InterruptedException
    {
        obj_id objMissionData = getMissionData(objMission);
        String strTemplate = getStringObjVar(objMissionData, "strGoal");
        String strLairType = getStringObjVar(objMissionData, "strLairType");
        String strBuildingType = getStringObjVar(objMissionData, "strBuildingType");
        int intMinDifficulty = getIntObjVar(objMissionData, "intMinDifficulty");
        int intMaxDifficulty = getIntObjVar(objMissionData, "intMaxDifficulty");
        int intPlayerDifficulty = getIntObjVar(objMissionData, "intPlayerDifficulty");
        int intSize = getIntObjVar(objMissionData, "intSize");
        location locGoodLocation = getGoodMissionLocation(objMission, objPlayer);
        if (locGoodLocation == null)
        {
            return false;
        }
        if (strTemplate == null || strTemplate.equals(""))
        {
            return false;
        }
        obj_id objReconTarget = createObject(strTemplate, locGoodLocation);
        if (!isIdValid(objReconTarget))
        {
            return false;
        }
        if (!strLairType.equals(""))
        {
            setObjVar(objReconTarget, "spawning.lairType", strLairType);
            String strLairDifficulty = create.getLairDifficulty(intMinDifficulty, intMaxDifficulty, intPlayerDifficulty);
            setObjVar(objReconTarget, "spawning.lairDifficulty", strLairDifficulty);
            setObjVar(objReconTarget, "spawning.intDifficultyLevel", intPlayerDifficulty);
            if (!strBuildingType.equals(""))
            {
                setObjVar(objReconTarget, "spawning.buildingType", strBuildingType);
            }
        }
        sendSystemMessage(objPlayer, SID_MISSION_TARGET_UPDATED_EXACT);
        updateMissionWaypoint(objMission, locGoodLocation);
        setObjVar(objReconTarget, "objMission", objMission);
        persistAndRegisterObject(objReconTarget, objMission);
        dictionary dctParams = new dictionary();
        messageTo(objReconTarget, "handle_Spawn_Setup_Complete", dctParams, 0, true);
        setObjVar(objMission, "intTargetCreated", 1);
        setupArrivedAtLocation(objPlayer, objMission, locGoodLocation, 32);
        return true;
    }
    public obj_id createEscortToCreatorMission(obj_id objMissionData, obj_id objCreator, location locMissionStart, int intDifficulty, String strPlanet, String strFaction) throws InterruptedException
    {
        setMissionRootScriptName(objMissionData, "systems.missions.dynamic.mission_escort");
        setMissionType(objMissionData, "escorttocreator");
        setObjVar(objMissionData, "intReturnToCreator", 1);
        location locPickupLocation = new location();
        location locDropoffLocation = new location();
        int intVariance;
        String strPickupNPC = NPC_TYPES[rand(0, NPC_TYPES.length - 1)];
        location locStartLocation = new location();
        location locEndLocation = new location();
        setObjVar(objMissionData, "strPickupNPC", strPickupNPC);
        region[] rgnCities = getRegionsWithMunicipalAtPoint(locMissionStart, regions.MUNI_TRUE);
        if (rgnCities == null)
        {
            rgnCities = getRegionsAtPoint(locStartLocation);
            if (rgnCities == null)
            {
                return null;
            }
        }
        location locCreatorLocation = getMissionLocation(objCreator);
        locEndLocation = (location)locCreatorLocation.clone();
        if (intDifficulty < ESCORT_DIFFICULTY_EASY)
        {
            region rgnEndRegion = rgnCities[0];
            if (locations.isCityRegion(rgnEndRegion))
            {
                locStartLocation = locations.getGoodLocationOutsideOfRegion(rgnEndRegion, 1, 1, 200, false, true);
            }
            else 
            {
                locStartLocation = utils.getRandomLocationInRing(locCreatorLocation, 128, 200);
                locStartLocation = locations.getGoodLocationAroundLocation(locStartLocation, 1, 1, 150, 150, false, true);
                if (locStartLocation == null)
                {
                    return null;
                }
                rgnCities = getRegionsWithMunicipalAtPoint(locEndLocation, regions.MUNI_TRUE);
                if (rgnCities != null)
                {
                    locStartLocation = locations.getGoodCityLocation(rgnCities[0], strPlanet);
                }
            }
        }
        else if (intDifficulty < ESCORT_DIFFICULTY_MEDIUM)
        {
            region rgnCity = locations.getClosestCityRegion(rgnCities[0]);
            locStartLocation = locations.getGoodCityLocation(rgnCity, strPlanet);
            if (locStartLocation == null)
            {
                return null;
            }
        }
        else 
        {
            region rgnCity = locations.getCityRegion(rgnCities[0]);
            locStartLocation = locations.getGoodCityLocation(rgnCity, strPlanet);
            if (locStartLocation == null)
            {
                return null;
            }
        }
        setObjVar(objMissionData, "intMissionDifficulty", intDifficulty);
        setMissionDifficulty(objMissionData, intDifficulty);
        setMissionStartLocation(objMissionData, locStartLocation);
        setMissionEndLocation(objMissionData, locEndLocation);
        setMissionType(objMissionData, "escorttocreator");
        obj_id groupObject = getGroupObject(objCreator);
        if (isIdValid(groupObject))
        {
            setObjVar(objMissionData, "originalGroupSize", getPCGroupSize(groupObject));
        }
        int intReward = getDeliverReward(locStartLocation, locEndLocation, DELIVER_REWARD_RATIO, 2);
        if (intReward < 25)
        {
            intReward = 25;
        }
        setMissionReward(objMissionData, intReward);
        dictionary dctParams = new dictionary();
        setupContextualizationStrings(objMissionData, intDifficulty, objCreator, strFaction);
        return objMissionData;
    }
    public obj_id createEscortTargetMission(obj_id objMissionData, obj_id objCreator, location locMissionStart, int intDifficulty, String strPlanet, String strFaction) throws InterruptedException
    {
        setMissionType(objMissionData, "escort");
        setObjVar(objMissionData, "intTargetPickedUp", 1);
        location locDropoffLocation = new location();
        int intVariance;
        String strPickupNPC = NPC_TYPES[rand(0, NPC_TYPES.length - 1)];
        location locStartLocation = new location();
        location locEndLocation = new location();
        region[] rgnCities = getRegionsWithMunicipalAtPoint(locMissionStart, regions.MUNI_TRUE);
        if (rgnCities == null)
        {
            rgnCities = getRegionsAtPoint(locStartLocation);
            if (rgnCities == null)
            {
                return null;
            }
        }
        location locCreatorLocation = getMissionLocation(objCreator);
        locEndLocation = (location)locCreatorLocation.clone();
        if (intDifficulty < ESCORT_DIFFICULTY_EASY)
        {
            region rgnEndRegion = rgnCities[0];
            if (locations.isCityRegion(rgnEndRegion))
            {
                locStartLocation = locations.getGoodLocationOutsideOfRegion(rgnEndRegion, 1, 1, 200, false, true);
            }
            else 
            {
                locStartLocation = utils.getRandomLocationInRing(locCreatorLocation, 128, 200);
                locStartLocation = locations.getGoodLocationAroundLocation(locStartLocation, 1, 1, 150, 150, false, true);
                if (locStartLocation == null)
                {
                    return null;
                }
                rgnCities = getRegionsWithMunicipalAtPoint(locEndLocation, regions.MUNI_TRUE);
                if (rgnCities != null)
                {
                    locStartLocation = locations.getGoodCityLocation(rgnCities[0], strPlanet);
                }
            }
        }
        else if (intDifficulty < ESCORT_DIFFICULTY_MEDIUM)
        {
            region rgnCity = locations.getClosestCityRegion(rgnCities[0]);
            locStartLocation = locations.getGoodCityLocation(rgnCity, strPlanet);
            if (locStartLocation == null)
            {
                return null;
            }
        }
        else 
        {
            region rgnCity = locations.getCityRegion(rgnCities[0]);
            locStartLocation = locations.getGoodCityLocation(rgnCity, strPlanet);
            if (locStartLocation == null)
            {
                return null;
            }
        }
        setObjVar(objMissionData, "intMissionDifficulty", intDifficulty);
        setMissionEndLocation(objMissionData, locStartLocation);
        setMissionType(objMissionData, "escort");
        obj_id groupObject = getGroupObject(objCreator);
        if (isIdValid(groupObject))
        {
            setObjVar(objMissionData, "originalGroupSize", getPCGroupSize(groupObject));
        }
        int intReward = getDeliverReward(locStartLocation, locEndLocation, DELIVER_REWARD_RATIO, 2);
        if (intReward < 25)
        {
            intReward = 25;
        }
        setMissionReward(objMissionData, intReward);
        dictionary dctParams = new dictionary();
        setupContextualizationStrings(objMissionData, intDifficulty, objCreator, strFaction);
        return objMissionData;
    }
    public int getDeliverReward(location locStartLocation, location locEndLocation, float fltDistanceModifier, float fltRewardModifier) throws InterruptedException
    {
        float fltReward = 0;
        float fltDistance = 0;
        String strStartPlanet = locStartLocation.area;
        String strEndPlanet = locEndLocation.area;
        if (strStartPlanet.equals(strEndPlanet))
        {
            fltDistance = utils.getDistance(locStartLocation, locEndLocation);
            fltReward = fltDistance / fltDistanceModifier;
        }
        else 
        {
            fltDistance = 1;
            fltReward = 1000;
        }
        fltReward = fltReward * fltRewardModifier;
        fltReward = fltReward / 2;
        if (fltReward > 1500)
        {
            fltReward = 650 + rand(-200, 150);
        }
        if (fltReward < 25)
        {
            fltReward = fltReward + rand(25, 50);
        }
        int intReward = 20 + (int)(fltReward);
        intReward = intReward + rand(-10, 10);
        return intReward;
    }
    public int getDestroyReward(int intMinReward, int intMaxReward, float fltDistance) throws InterruptedException
    {
        final int minRangeReward = 500;
        final int maxRangeReward = 1500;
        if (fltDistance < minRangeReward)
        {
            fltDistance = minRangeReward;
        }
        if (fltDistance > maxRangeReward)
        {
            fltDistance = maxRangeReward;
        }
        float fltRewardPercentage = (fltDistance - minRangeReward) / (float)(maxRangeReward - minRangeReward);
        int intRewardDifference = intMaxReward - intMinReward;
        int intReward = intMinReward + (int)(intRewardDifference * fltRewardPercentage);
        return intReward;
    }
    public void persistAndRegisterObject(obj_id objObject, obj_id objMission) throws InterruptedException
    {
        persistObject(objObject);
        dictionary dctParams = new dictionary();
        dctParams.put("objObject", objObject);
        messageTo(objMission, "objectCreated", dctParams, 0, true);
        return;
    }
    public void cleanupObjects(obj_id objMission) throws InterruptedException
    {
        obj_id[] objObjects = getObjIdArrayObjVar(objMission, "objObjects");
        int intI = 0;
        if (objObjects != null)
        {
            while (intI < objObjects.length)
            {
                messageTo(objObjects[intI], "destroySelf", null, rand(30, 120), true);
                intI = intI + 1;
            }
        }
        return;
    }
    public location getGoodTheaterLocation(obj_id objMissionData, location locSpawnLocation) throws InterruptedException
    {
        int intSize = getIntObjVar(objMissionData, "intSize");
        location locGoodLocation = locations.getGoodLocationAroundLocation(locSpawnLocation, intSize, intSize, 200, 200, false, true);
        if (locGoodLocation == null)
        {
            return null;
        }
        obj_id[] objTheaters = getAllObjectsWithObjVar(locGoodLocation, 80, "theater.stamp");
        if (objTheaters != null)
        {
            if (objTheaters.length > 0)
            {
                locSpawnLocation.x = locSpawnLocation.x + rand(-200, 200);
                locSpawnLocation.z = locSpawnLocation.z + rand(-200, 200);
                locGoodLocation = locations.getGoodLocationAroundLocation(locSpawnLocation, intSize, intSize, 200, 200, false, true);
                objTheaters = getAllObjectsWithObjVar(locGoodLocation, 80, "theater.stamp");
                if (locGoodLocation != null)
                {
                    if (objTheaters != null)
                    {
                        if (objTheaters.length > 0)
                        {
                            return null;
                        }
                    }
                    else 
                    {
                        return locGoodLocation;
                    }
                }
                else 
                {
                    return null;
                }
            }
        }
        return locGoodLocation;
    }
    public obj_id createSurveyMission(obj_id objMissionData, obj_id objCreator, int intDifficulty, String strFaction) throws InterruptedException
    {
        final int MIN_SURVEY_EFFECIENCY = 50;
        final int MAX_SURVEY_EFFECIENCY = 70;
        final int MIN_SURVEY_REWARD = 150;
        final int SURVEY_BONUS_PER_POINT = 15;
        setMissionRootScriptName(objMissionData, "systems.missions.dynamic.mission_survey");
        setMissionType(objMissionData, "survey");
        int intEffeciency = rand(MIN_SURVEY_EFFECIENCY, MAX_SURVEY_EFFECIENCY);
        int intDifference = intEffeciency - MIN_SURVEY_EFFECIENCY;
        intDifference = intDifference * SURVEY_BONUS_PER_POINT;
        int intReward = MIN_SURVEY_REWARD + intDifference;
        intReward = intReward * 2;
        obj_id groupObject = getGroupObject(objCreator);
        if (isIdValid(groupObject))
        {
            setObjVar(objMissionData, "originalGroupSize", getPCGroupSize(groupObject));
        }
        String[] strResources = new String[0];
        String[] strDetailTemplates = new String[0];
        String strFileName = "datatables/missions/survey/survey.iff";
        int intRewardMultiplier = 0;
        if (intDifficulty < 10)
        {
            intRewardMultiplier = 1;
            strResources = dataTableGetStringColumnNoDefaults(strFileName, "strResourceEasy");
            strDetailTemplates = dataTableGetStringColumnNoDefaults(strFileName, "strDetailTemplateEasy");
        }
        else 
        {
            intRewardMultiplier = 2;
            strResources = dataTableGetStringColumnNoDefaults(strFileName, "strResourceHard");
            strDetailTemplates = dataTableGetStringColumnNoDefaults(strFileName, "strDetailTemplateHard");
        }
        intReward = intReward * intRewardMultiplier;
        int intRandomNumber = rand(0, strResources.length - 1);
        String strDetailTemplate = strDetailTemplates[intRandomNumber];
        String strResource = strResources[intRandomNumber];
        location locTest = new location();
        string_id strTokenName = new string_id(SID_SURVEY_RESOURCE_NAMES_TABLE, strResource);
        setMissionTargetAppearance(objMissionData, strDetailTemplate);
        setMissionTargetName(objMissionData, utils.packStringId(strTokenName));
        setMissionDifficulty(objMissionData, intEffeciency);
        setObjVar(objMissionData, OBJVAR_SURVEY_MISSION_EFFECIENCY, intEffeciency);
        setObjVar(objMissionData, OBJVAR_SURVEY_MISSION_RESOURCE, strResource);
        if (!strFaction.equals(""))
        {
            int intFactionReward = getFactionReward(intReward);
            setObjVar(objMissionData, "intFactionReward", intFactionReward);
            setObjVar(objMissionData, "strFaction", strFaction);
        }
        setMissionReward(objMissionData, intReward);
        dictionary dctParams = new dictionary();
        setupContextualizationStrings(objMissionData, 1, objCreator, strFaction);
        setObjVar(objMissionData, "locStartLocation", getLocation(objCreator));
        return objMissionData;
    }
    public void sendSurveyFail(obj_id objMission) throws InterruptedException
    {
        dictionary dctParams = new dictionary();
        setObjVar(objMission, "intState", missions.STATE_MISSION_COMPLETE);
        messageTo(objMission, "surveyFailure", dctParams, 0, true);
    }
    public void sendSurveySuccess(obj_id objMission) throws InterruptedException
    {
        dictionary dctParams = new dictionary();
        setObjVar(objMission, "intState", missions.STATE_MISSION_COMPLETE);
        messageTo(objMission, "surveySuccess", dctParams, 0, true);
    }
    public void sendSurveyIncomplete(obj_id objMission) throws InterruptedException
    {
        dictionary dctParams = new dictionary();
        setObjVar(objMission, "intState", missions.STATE_MISSION_COMPLETE);
        messageTo(objMission, "surveyIncomplete", dctParams, 0, true);
    }
    public obj_id createDancerMission(obj_id objMissionData, obj_id objCreator, int intDifficulty, String strFaction) throws InterruptedException
    {
        setMissionRootScriptName(objMissionData, "systems.missions.dynamic.mission_entertainer");
        setMissionType(objMissionData, "dancer");
        String strDetailTemplate = "object/tangible/instrument/organ_max_rebo.iff";
        location locTest = new location();
        string_id strTokenName = new string_id("mission/mission_generic", "dancer");
        setMissionTargetAppearance(objMissionData, strDetailTemplate);
        setMissionTargetName(objMissionData, utils.packStringId(strTokenName));
        String strPlanet = locTest.area;
        if (!setupEntertainerMissionTarget(objMissionData, strPlanet))
        {
            return null;
        }
        int intTime = 600;
        int intReward = (intTime * 2) + rand(-100, 100);
        obj_id groupObject = getGroupObject(objCreator);
        if (isIdValid(groupObject))
        {
            setObjVar(objMissionData, "originalGroupSize", getPCGroupSize(groupObject));
        }
        setObjVar(objMissionData, "intTime", intTime);
        if (!strFaction.equals(""))
        {
            int intFactionReward = getFactionReward(intReward);
            setObjVar(objMissionData, "intFactionReward", intFactionReward);
            setObjVar(objMissionData, "strFaction", strFaction);
        }
        setMissionReward(objMissionData, intReward);
        dictionary dctParams = new dictionary();
        setupContextualizationStrings(objMissionData, 1, objCreator, strFaction);
        return objMissionData;
    }
    public obj_id createMusicianMission(obj_id objMissionData, obj_id objCreator, int intDifficulty, String strFaction) throws InterruptedException
    {
        setMissionRootScriptName(objMissionData, "systems.missions.dynamic.mission_entertainer");
        setMissionType(objMissionData, "musician");
        String strDetailTemplate = "object/tangible/instrument/organ_max_rebo.iff";
        location locTest = new location();
        string_id strTokenName = new string_id("mission/mission_generic", "dancer");
        setMissionTargetAppearance(objMissionData, strDetailTemplate);
        setMissionTargetName(objMissionData, utils.packStringId(strTokenName));
        obj_id groupObject = getGroupObject(objCreator);
        if (isIdValid(groupObject))
        {
            setObjVar(objMissionData, "originalGroupSize", getPCGroupSize(groupObject));
        }
        int intTime = 600;
        int intReward = (intTime * 2) + rand(-100, 100);
        setObjVar(objMissionData, "intTime", intTime);
        locTest = getMissionLocation(objCreator);
        String strPlanet = locTest.area;
        if (!setupEntertainerMissionTarget(objMissionData, strPlanet))
        {
            return null;
        }
        if (!strFaction.equals(""))
        {
            int intFactionReward = getFactionReward(intReward);
            setObjVar(objMissionData, "intFactionReward", intFactionReward);
            setObjVar(objMissionData, "strFaction", strFaction);
        }
        setMissionReward(objMissionData, intReward);
        dictionary dctParams = new dictionary();
        setupContextualizationStrings(objMissionData, 1, objCreator, strFaction);
        return objMissionData;
    }
    public boolean setupEntertainerMissionTarget(obj_id objMissionData, String strPlanet) throws InterruptedException
    {
        setMissionRootScriptName(objMissionData, "systems.missions.dynamic.mission_entertainer");
        final String[] ENTERTAINER_TARGET_CATEGORY = 
        {
            "cantina",
            "hotel",
            "tavern",
            "barracks",
            "theater"
        };
        Vector strCategories = new Vector();
        strCategories.setSize(0);
        for (int intI = 0; intI < ENTERTAINER_TARGET_CATEGORY.length - 1; intI++)
        {
            map_location[] locTargets = getPlanetaryMapLocations(ENTERTAINER_TARGET_CATEGORY[intI], "");
            if (locTargets != null && locTargets.length > 0)
            {
                strCategories = utils.addElement(strCategories, ENTERTAINER_TARGET_CATEGORY[intI]);
            }
        }
        if (strCategories.size() == 0)
        {
            return true;
        }
        String strCategory = ((String)strCategories.get(rand(0, strCategories.size() - 1)));
        map_location[] locTargets = getPlanetaryMapLocations(strCategory, "");
        if (locTargets == null)
        {
            return false;
        }
        if (locTargets.length == 0)
        {
            return false;
        }
        location locDestination = new location();
        locDestination.area = strPlanet;
        int intRoll = rand(0, locTargets.length - 1);
        locDestination.x = locTargets[intRoll].getX();
        locDestination.z = locTargets[intRoll].getY();
        obj_id objTarget = locTargets[intRoll].getLocationId();
        setObjVar(objMissionData, "objBuilding", objTarget);
        setMissionStartLocation(objMissionData, locDestination);
        return true;
    }
    public void sendEntertainerFail(obj_id objMission) throws InterruptedException
    {
        dictionary dctParams = new dictionary();
        setObjVar(objMission, "intState", missions.STATE_MISSION_COMPLETE);
        messageTo(objMission, "entertainerFailure", dctParams, 0, true);
    }
    public void sendEntertainerSuccess(obj_id objMission) throws InterruptedException
    {
        dictionary dctParams = new dictionary();
        setObjVar(objMission, "intState", missions.STATE_MISSION_COMPLETE);
        messageTo(objMission, "entertainerSuccess", dctParams, 0, true);
    }
    public void sendEntertainerIncomplete(obj_id objMission) throws InterruptedException
    {
        dictionary dctParams = new dictionary();
        setObjVar(objMission, "intState", missions.STATE_MISSION_COMPLETE);
        messageTo(objMission, "entertainerIncomplete", dctParams, 0, true);
    }
    public obj_id createCraftingMission(obj_id objMissionData, obj_id objCreator, location locMissionStart, int intDifficulty, String strFaction) throws InterruptedException
    {
        setMissionRootScriptName(objMissionData, "systems.missions.dynamic.mission_crafting");
        setMissionType(objMissionData, "crafting");
        String strPlanet = locMissionStart.area;
        location locPickupLocation = new location();
        location locDropoffLocation = new location();
        int intVariance;
        String strFileName = "datatables/missions/crafting/crafting.iff";
        String[] strSchematics = dataTableGetStringColumn(strFileName, "strSchematic");
        int intRoll = rand(0, strSchematics.length - 1);
        dictionary dctCraftingStats = dataTableGetRow(strFileName, intRoll);
        int intI = 0;
        String strSchematic = dctCraftingStats.getString("strSchematic");
        String strDetailTemplate = dctCraftingStats.getString("strDetailTemplate");
        Vector strComponents = new Vector();
        strComponents.setSize(0);
        while (intI < 4)
        {
            String strLookupName = "strComponent" + (intI + 1);
            String strComponent = dctCraftingStats.getString(strLookupName);
            if (!strComponent.equals(""))
            {
                strComponents = utils.addElement(strComponents, strComponent);
            }
            intI = intI + 1;
        }
        String strAscii = dctCraftingStats.getString("strName");
        string_id strItemName = new string_id("item_n", strAscii);
        setMissionTargetName(objMissionData, utils.packStringId(strItemName));
        setObjVar(objMissionData, "strSchematic", strSchematic);
        setObjVar(objMissionData, "strComponents", strComponents);
        setObjVar(objMissionData, "strItemToMake", strDetailTemplate);
        String strPickupNPC = NPC_TYPES[rand(0, NPC_TYPES.length - 1)];
        String strDropoffNPC = NPC_TYPES[rand(0, NPC_TYPES.length - 1)];
        location locStartLocation = new location();
        location locEndLocation = new location();
        setObjVar(objMissionData, "strPickupNPC", strPickupNPC);
        setObjVar(objMissionData, "strDropoffNPC", strDropoffNPC);
        region[] rgnCities = getRegionsWithMunicipalAtPoint(locMissionStart, regions.MUNI_TRUE);
        if (rgnCities == null)
        {
            rgnCities = getRegionsAtPoint(locStartLocation);
            if (rgnCities == null)
            {
                return null;
            }
        }
        region rgnStartRegion = rgnCities[0];
        boolean boolIsCity = false;
        if (locations.isCityRegion(rgnStartRegion))
        {
            locStartLocation = locations.getGoodCityRegionLocation(rgnStartRegion, strPlanet);
            boolIsCity = true;
        }
        else 
        {
            region closestCity = locations.getClosestCityRegion(rgnStartRegion);
            locStartLocation = locations.getGoodCityRegionLocation(closestCity, strPlanet);
            boolIsCity = true;
        }
        if (locStartLocation == null)
        {
            return null;
        }
        int intMissionDifficulty;
        if (intDifficulty < MISSION_DELIVER_DIFFICULTY_EASY)
        {
            if (boolIsCity)
            {
                intRoll = rand(1, 100);
                if (intRoll < 40)
                {
                    locEndLocation = locations.getDifferentGoodCityLocation(locStartLocation);
                }
                else 
                {
                    region rgnEndRegion = locations.getClosestCityRegion(rgnStartRegion);
                    String strName = rgnEndRegion.getName();
                    locEndLocation = locations.getGoodCityLocation(rgnEndRegion, strPlanet);
                }
            }
            else 
            {
                region rgnEndRegion = locations.getClosestCityRegion(rgnStartRegion);
                String strName = rgnEndRegion.getName();
                locEndLocation = locations.getGoodCityRegionLocation(rgnEndRegion, strPlanet);
            }
            intMissionDifficulty = MISSION_DIFFICULTY_EASY;
            setObjVar(objMissionData, "intMissionDifficulty", intMissionDifficulty);
        }
        else 
        {
            region rgnEndRegion = locations.getCityRegion(rgnStartRegion);
            if (rgnEndRegion == null)
            {
                return null;
            }
            locEndLocation = locations.getGoodCityRegionLocation(rgnEndRegion, strPlanet);
            intMissionDifficulty = MISSION_DIFFICULTY_EASY;
            setObjVar(objMissionData, "intMissionDifficulty", intMissionDifficulty);
        }
        if (locEndLocation == null)
        {
            return null;
        }
        setMissionDifficulty(objMissionData, getIntObjVar(objMissionData, "intMissionDifficulty"));
        setMissionTargetAppearance(objMissionData, strDetailTemplate);
        setMissionStartLocation(objMissionData, locStartLocation);
        setMissionEndLocation(objMissionData, locEndLocation);
        int intReward = getDeliverReward(locStartLocation, locEndLocation, CRAFTING_REWARD_RATIO, 3);
        if (intReward < 5)
        {
            intReward = 5;
        }
        if (!strFaction.equals(""))
        {
            int intFactionReward = getFactionReward(intReward);
            setObjVar(objMissionData, "intFactionReward", intFactionReward);
            setObjVar(objMissionData, "strFaction", strFaction);
        }
        setMissionReward(objMissionData, intReward);
        dictionary dctParams = new dictionary();
        setupContextualizationStrings(objMissionData, intMissionDifficulty, objCreator, strFaction);
        return objMissionData;
    }
    public void sendCraftingFail(obj_id objMission) throws InterruptedException
    {
        dictionary dctParams = new dictionary();
        setObjVar(objMission, "intState", missions.STATE_MISSION_COMPLETE);
        messageTo(objMission, "craftingFailure", dctParams, 0, true);
    }
    public void sendCraftingSuccess(obj_id objMission) throws InterruptedException
    {
        dictionary dctParams = new dictionary();
        setObjVar(objMission, "intState", missions.STATE_MISSION_COMPLETE);
        messageTo(objMission, "craftingSuccess", dctParams, 0, true);
    }
    public void sendCraftingIncomplete(obj_id objMission) throws InterruptedException
    {
        dictionary dctParams = new dictionary();
        setObjVar(objMission, "intState", missions.STATE_MISSION_COMPLETE);
        messageTo(objMission, "craftingIncomplete", dctParams, 0, true);
    }
    public boolean createCraftingPickupNPC(obj_id objMission) throws InterruptedException
    {
        obj_id objPlayer = getMissionHolder(objMission);
        obj_id objMissionData = getMissionData(objMission);
        String strPickupNPC = getStringObjVar(objMissionData, "strPickupNPC");
        location locSpawnLocation = getMissionStartLocation(objMissionData);
        obj_id objPickupNPC = create.object(strPickupNPC, locSpawnLocation);
        if (!isIdValid(objPickupNPC))
        {
            return false;
        }
        else 
        {
            attachScript(objPickupNPC, "systems.missions.dynamic.mission_crafting_pickup");
            setObjVar(objPickupNPC, "objMission", objMission);
            setObjVar(objPickupNPC, "locSpawnLocation", locSpawnLocation);
            setObjVar(objPickupNPC, "objPlayer", objPlayer);
            persistAndRegisterObject(objPickupNPC, objMission);
            setWantSawAttackTriggers(objPickupNPC, false);
        }
        return true;
    }
    public boolean createCraftingDropoffNPC(obj_id objMission) throws InterruptedException
    {
        obj_id objMissionData = getMissionData(objMission);
        String strDropoffNPC = getStringObjVar(objMissionData, "strDropoffNPC");
        location locSpawnLocation = getMissionEndLocation(objMissionData);
        obj_id objDropoffNPC = create.object(strDropoffNPC, locSpawnLocation);
        obj_id objPlayer = getMissionHolder(objMission);
        if (!isIdValid(objDropoffNPC))
        {
            return false;
        }
        else 
        {
            attachScript(objDropoffNPC, "systems.missions.dynamic.mission_crafting_dropoff");
            setObjVar(objDropoffNPC, "objMission", objMission);
            setObjVar(objDropoffNPC, "locSpawnLocation", locSpawnLocation);
            setObjVar(objDropoffNPC, "objPlayer", objPlayer);
            persistAndRegisterObject(objDropoffNPC, objMission);
            setWantSawAttackTriggers(objDropoffNPC, false);
        }
        return true;
    }
    public void grantMissionSchematic(obj_id objPlayer, obj_id objMission) throws InterruptedException
    {
        obj_id objMissionData = getMissionData(objMission);
        String strSchematic = getStringObjVar(objMissionData, "strSchematic");
        grantSchematic(objPlayer, strSchematic);
        int intNumGranted = getIntObjVar(objPlayer, "mission." + strSchematic);
        intNumGranted = intNumGranted + 1;
        setObjVar(objPlayer, "mission." + strSchematic, intNumGranted);
        return;
    }
    public void revokeMissionSchematic(obj_id objPlayer, obj_id objMission) throws InterruptedException
    {
        if (!hasObjVar(objMission, "strSchematic"))
        {
            return;
        }
        obj_id objMissionData = getMissionData(objMission);
        String strSchematic = getStringObjVar(objMissionData, "strSchematic");
        int intNumGranted = getIntObjVar(objPlayer, "mission." + strSchematic);
        intNumGranted = intNumGranted - 1;
        if (intNumGranted < 1)
        {
            removeObjVar(objPlayer, "mission." + strSchematic);
            revokeSchematic(objPlayer, strSchematic);
            removeObjVar(objMission, "strSchematic");
        }
        else 
        {
            setObjVar(objPlayer, "mission." + strSchematic, intNumGranted);
            return;
        }
        return;
    }
    public void reContextualizationTerminalToNPCMission(obj_id objMissionData, obj_id objCreator) throws InterruptedException
    {
        String strIdFileName = getStringObjVar(objMissionData, "strIdFileName");
        if (strIdFileName == null)
        {
            if (isIdValid(objMissionData))
            {
                String strMissionType = getMissionType(objMissionData);
            }
            else 
            {
            }
            return;
        }
        int intIndex = strIdFileName.indexOf("tatooine");
        if (intIndex > 0)
        {
            String strTest = strIdFileName.substring(0, intIndex - 1);
            strIdFileName = strTest;
        }
        intIndex = strIdFileName.indexOf("corellia");
        if (intIndex > 0)
        {
            String strTest = strIdFileName.substring(0, intIndex - 1);
            strIdFileName = strTest;
        }
        intIndex = strIdFileName.indexOf("naboo");
        if (intIndex > 0)
        {
            String strTest = strIdFileName.substring(0, intIndex - 1);
            strIdFileName = strTest;
        }
        strIdFileName = strIdFileName + "_non_persistent_from_npc";
        int intStringQuantity = 1;
        string_id strEntryId = new string_id(strIdFileName, "number_of_entries");
        String strNumberOfEntries = getString(strEntryId);
        if (strNumberOfEntries != null)
        {
            if (strNumberOfEntries == null)
            {
                intStringQuantity = 0;
            }
            else if (!strNumberOfEntries.equals(""))
            {
                Integer intTest = new Integer(0);
                boolean boolError = false;
                try
                {
                    intTest = new Integer(strNumberOfEntries);
                }
                catch(NumberFormatException err)
                {
                    boolError = true;
                }
                if (!boolError)
                {
                    intStringQuantity = intTest.intValue();
                    if (intStringQuantity < 0)
                    {
                        intStringQuantity = 1;
                    }
                }
            }
        }
        int intId = rand(1, intStringQuantity);
        setObjVar(objMissionData, "intStringId", intId);
        setObjVar(objMissionData, "strIdFileName", strIdFileName);
        int intStringId = intId;
        string_id strTitleId = new string_id(strIdFileName, "m" + intStringId + "t");
        String strDescription;
        strDescription = "m" + intStringId + "d";
        string_id strDescriptionId = new string_id(strIdFileName, strDescription);
        location locTest = getMissionLocation(objCreator);
        setMissionCreator(objMissionData, getName(objCreator));
        setMissionTitle(objMissionData, strTitleId);
        setMissionDescription(objMissionData, strDescriptionId);
        return;
    }
    public obj_id dynamicDestroyToThemePark(obj_id objMissionData, obj_id objCreator) throws InterruptedException
    {
        int intDifficulty = getIntObjVar(objMissionData, "intDifficulty");
        String strNPCType = getStringObjVar(objCreator, "mission.strNPCName");
        String strFileName = "mission/theme_park/mission_destroy_" + strNPCType;
        string_id strId = new string_id(strFileName, "number_of_entries");
        String strNumEntries = getString(strId);
        int intNumEntries = utils.stringToInt(strNumEntries);
        int intRoll = rand(1, intNumEntries);
        String strTag = "m" + intRoll + "reward";
        strId = new string_id(strFileName, strTag);
        String strString = getString(strId);
        int intReward = utils.stringToInt(strString);
        strTag = "m" + intRoll + "lairtype";
        strId = new string_id(strFileName, strTag);
        String strLairType = getString(strId);
        strTag = "m" + intRoll + "buildingtype";
        strId = new string_id(strFileName, strTag);
        String strBuildingType = getString(strId);
        strTag = "m" + intRoll + "objective";
        strId = new string_id(strFileName, strTag);
        String strObjective = getString(strId);
        strTag = "m" + intRoll + "objectivetarget";
        strId = new string_id(strFileName, strTag);
        String strObjectiveTarget = getString(strId);
        strTag = "m" + intRoll + "killpercentage";
        strId = new string_id(strFileName, strTag);
        strString = getString(strId);
        float fltKillPercentage = utils.stringToFloat(strString);
        strTag = "m" + intRoll + "mindifficulty";
        strId = new string_id(strFileName, strTag);
        strString = getString(strId);
        int intMinDifficulty = utils.stringToInt(strString);
        strTag = "m" + intRoll + "maxdifficulty";
        strId = new string_id(strFileName, strTag);
        strString = getString(strId);
        int intMaxDifficulty = utils.stringToInt(strString);
        strTag = "m" + intRoll + "size";
        strId = new string_id(strFileName, strTag);
        strString = getString(strId);
        int intSize = utils.stringToInt(strString);
        strTag = "m" + intRoll + "detailtemplate";
        strId = new string_id(strFileName, strTag);
        String strDetailTemplate = getString(strId);
        setObjVar(objMissionData, "fltKillPercentage", fltKillPercentage);
        setObjVar(objMissionData, "strLairType", strLairType);
        setObjVar(objMissionData, "strBuildingType", strBuildingType);
        setObjVar(objMissionData, "strObjective", strObjective);
        setObjVar(objMissionData, "strObjectiveTarget", strObjectiveTarget);
        setObjVar(objMissionData, "intPlayerDifficulty", intDifficulty);
        setObjVar(objMissionData, "intMinDifficulty", intMinDifficulty);
        setObjVar(objMissionData, "intMaxDifficulty", intMaxDifficulty);
        String strLairDifficulty = create.getLairDifficulty(intMinDifficulty, intMaxDifficulty, intDifficulty);
        setObjVar(objMissionData, "strLairDifficulty", strLairDifficulty);
        setObjVar(objMissionData, "intSize", intSize);
        setObjVar(objMissionData, "strTemplateToSpawn", "");
        setObjVar(objMissionData, "intNumToSpawn", 0);
        setMissionTargetAppearance(objMissionData, strDetailTemplate);
        if (!strLairType.equals(""))
        {
            string_id strTokenName = new string_id("lair_n", strLairType);
            setMissionTargetName(objMissionData, utils.packStringId(strTokenName));
        }
        obj_id groupObject = getGroupObject(objCreator);
        if (isIdValid(groupObject))
        {
            setObjVar(objMissionData, "originalGroupSize", getPCGroupSize(groupObject));
        }
        else 
        {
            setObjVar(objMissionData, "originalGroupSize", 1);
        }
        intReward = intReward + rand(-10, 10);
        setMissionReward(objMissionData, intReward);
        dictionary dctParams = new dictionary();
        convertDynamicContextualizationToThemePark(objMissionData, strFileName, intRoll);
        return objMissionData;
    }
    public obj_id dynamicDeliverToThemePark(obj_id objMissionData, obj_id objCreator) throws InterruptedException
    {
        String strNPCType = getStringObjVar(objCreator, "mission.strNPCName");
        String strFileName = "mission/theme_park/mission_deliver_" + strNPCType;
        string_id strId = new string_id(strFileName, "number_of_entries");
        String strNumEntries = getString(strId);
        int intNumEntries = utils.stringToInt(strNumEntries);
        int intRoll = rand(1, intNumEntries);
        String strTag = "m" + intRoll + "pickupNPC";
        strId = new string_id(strFileName, strTag);
        String strPickupNPC = getString(strId);
        strTag = "m" + intRoll + "dropoffNPC";
        strId = new string_id(strFileName, strTag);
        String strDropoffNPC = getString(strId);
        strTag = "m" + intRoll + "deliveryItem";
        strId = new string_id(strFileName, strTag);
        String strDeliveryItem = getString(strId);
        setObjVar(objMissionData, "strPickupNPC", strPickupNPC);
        setObjVar(objMissionData, "strDropoffNPC", strDropoffNPC);
        setMissionTargetAppearance(objMissionData, strDeliveryItem);
        convertDynamicContextualizationToThemePark(objMissionData, strFileName, intRoll);
        return objMissionData;
    }
    public void convertDynamicContextualizationToThemePark(obj_id objMissionData, String strFileName, int intId) throws InterruptedException
    {
        setObjVar(objMissionData, "intStringId", intId);
        setObjVar(objMissionData, "strIdFileName", strFileName);
        int intStringId = intId;
        string_id strTitleId = new string_id(strFileName, "m" + intStringId + "t");
        String strDescription;
        strDescription = "m" + intStringId + "d";
        string_id strDescriptionId = new string_id(strFileName, strDescription);
        setMissionTitle(objMissionData, strTitleId);
        setMissionDescription(objMissionData, strDescriptionId);
        return;
    }
    public obj_id dynamicEscortToCreatorToThemePark(obj_id objMissionData, obj_id objCreator) throws InterruptedException
    {
        String strNPCType = getStringObjVar(objCreator, "mission.strNPCName");
        String strFileName = "mission/theme_park/mission_escorttocreator" + strNPCType;
        string_id strId = new string_id(strFileName, "number_of_entries");
        String strNumEntries = getString(strId);
        int intNumEntries = utils.stringToInt(strNumEntries);
        int intRoll = rand(1, intNumEntries);
        String strTag = "m" + intRoll + "pickupNPC";
        strId = new string_id(strFileName, strTag);
        String strPickupNPC = getString(strId);
        setObjVar(objMissionData, "strPickupNPC", strPickupNPC);
        convertDynamicContextualizationToThemePark(objMissionData, strFileName, intRoll);
        return objMissionData;
    }
    public location getMissionLocation(obj_id objTarget) throws InterruptedException
    {
        obj_id objTopMostContainer = getTopMostContainer(objTarget);
        if (!isIdValid(objTopMostContainer))
        {
            location locTest = getLocation(objTarget);
            return locTest;
        }
        else 
        {
            location locTest = getLocation(objTopMostContainer);
            return locTest;
        }
    }
    public boolean areMissionsAllowed(location locTest) throws InterruptedException
    {
        if (locTest == null || locTest.area == null)
        {
            return false;
        }
        final String[] MISSIONS_NOT_ALLOWED = 
        {
            "tutorial"
        };
        int intI = 0;
        String strTest = locTest.area;
        for (intI = 0; intI < MISSIONS_NOT_ALLOWED.length; intI++)
        {
            if (strTest.equals(MISSIONS_NOT_ALLOWED[intI]))
            {
                return false;
            }
        }
        return true;
    }
    public void doIncorrectPlayerBlurb(obj_id objNPC, obj_id player) throws InterruptedException
    {
        int lastBlurb = utils.getIntScriptVar(objNPC, "mission.blurbTime");
        int gameTime = getGameTime();
        if (gameTime - lastBlurb < 2)
        {
            return;
        }
        utils.setScriptVar(objNPC, "mission.blurbTime", gameTime);
        final int NUMBER_OF_RESPONSES = 4;
        final String[] ANIMATIONS = 
        {
            anims.PLAYER_POINT_TO_SELF,
            anims.PLAYER_REFUSE_OFFER_FORMAL,
            anims.PLAYER_RUB_CHIN_THOUGHTFUL,
            anims.PLAYER_SHAKE_HEAD_DISGUST,
            anims.PLAYER_SHUSH
        };
        String strId = "deliver_incorrect_player_";
        int intId = rand(0, NUMBER_OF_RESPONSES);
        doAnimationAction(objNPC, ANIMATIONS[intId]);
        strId = strId + intId;
        string_id strSpam = new string_id("mission/mission_generic", strId);
        chat.chat(objNPC, player, strSpam);
        return;
    }
    public boolean validateNPCMissionForPlayer(obj_id objNPC, obj_id objMissionData, obj_id objPlayer) throws InterruptedException
    {
        if (!isIdValid(objMissionData))
        {
            return false;
        }
        if (hasObjVar(objMissionData, "boolDeclaredOnly"))
        {
            if (!factions.isCovert(objPlayer))
            {
                return false;
            }
            String strPlayerFaction = factions.getFaction(objPlayer);
            String strMissionFaction = getStringObjVar(objMissionData, "strFaction");
            if (!strPlayerFaction.equals(strMissionFaction))
            {
                return false;
            }
        }
        String strMissionType = getMissionType(objMissionData);
        if (strMissionType.equals("musician"))
        {
            if (!utils.isProfession(objPlayer, utils.ENTERTAINER))
            {
                return false;
            }
        }
        if (strMissionType.equals("dancer"))
        {
            if (!utils.isProfession(objPlayer, utils.ENTERTAINER))
            {
                return false;
            }
        }
        return true;
    }
    public boolean hasStarPort(location locTestLocation) throws InterruptedException
    {
        String strName = locations.getCityName(locTestLocation);
        if (strName == null)
        {
            return false;
        }
        for (int intI = 0; intI < CITIES_WITH_STARPORTS.length; intI++)
        {
            if (strName.equals(CITIES_WITH_STARPORTS[intI]))
            {
                return true;
            }
        }
        return false;
    }
    public int getFactionReward(int intReward) throws InterruptedException
    {
        int intBaseReward = 5 + intReward / FACTION_REWARD_VALUE;
        return intBaseReward;
    }
    public obj_id getBountyMission(obj_id objPlayer) throws InterruptedException
    {
        if (isIdValid(objPlayer))
        {
            obj_id[] objMissionArray = getMissionObjects(objPlayer);
            if (objMissionArray != null)
            {
                for (int intI = 0; intI < objMissionArray.length; intI++)
                {
                    String strType = getMissionType(objMissionArray[intI]);
                    if (strType.equals("bounty"))
                    {
                        return objMissionArray[intI];
                    }
                }
            }
        }
        return null;
    }
    public boolean hasCraftingMissionItem(obj_id objPlayer, String strTemplate) throws InterruptedException
    {
        obj_id objInventory = utils.getInventoryContainer(objPlayer);
        if (!isIdValid(objInventory))
        {
            return false;
        }
        obj_id[] objContents = getContents(objInventory);
        if (objContents == null)
        {
            return false;
        }
        for (int intI = 0; intI < objContents.length; intI++)
        {
            String strItemTemplate = getTemplateName(objContents[intI]);
            if (strItemTemplate.equals(strTemplate))
            {
                destroyObject(objContents[intI]);
                return true;
            }
        }
        return false;
    }
    public void sendHuntingFail(obj_id objMission) throws InterruptedException
    {
        dictionary dctParams = new dictionary();
        setObjVar(objMission, "intState", missions.STATE_MISSION_COMPLETE);
        messageTo(objMission, "huntingFailure", dctParams, 0, true);
    }
    public void sendHuntingSuccess(obj_id objMission) throws InterruptedException
    {
        dictionary dctParams = new dictionary();
        setObjVar(objMission, "intState", missions.STATE_MISSION_COMPLETE);
        messageTo(objMission, "huntingSuccess", dctParams, 0, true);
    }
    public void sendHuntingIncomplete(obj_id objMission) throws InterruptedException
    {
        dictionary dctParams = new dictionary();
        setObjVar(objMission, "intState", missions.STATE_MISSION_COMPLETE);
        messageTo(objMission, "huntingIncomplete", dctParams, 0, true);
    }
    public obj_id createHuntingMission(obj_id objMissionData, obj_id objCreator, int intDifficulty, String strFaction) throws InterruptedException
    {
        setMissionRootScriptName(objMissionData, "systems.missions.dynamic.mission_hunting");
        setMissionType(objMissionData, "hunting");
        int intMissionDifficulty = rand(1, 3);
        final int[] HUNTING_QUANTITIES = 
        {
            15,
            30,
            45
        };
        final int[] REWARD_MULTIPLIER = 
        {
            4,
            6,
            8
        };
        location locStartLocation = getLocation(objCreator);
        String strFileName = "datatables/missions/hunting/hunting_";
        strFileName = strFileName + locStartLocation.area + ".iff";
        String[] strCreatureTypes = dataTableGetStringColumn(strFileName, "strCreatureType");
        String[] strDetailTemplates = dataTableGetStringColumn(strFileName, "strDetailTemplate");
        int[] intMinDifficulties = dataTableGetIntColumn(strFileName, "intMinDifficulty");
        int[] intMaxDifficulties = dataTableGetIntColumn(strFileName, "intMaxDifficulty");
        int[] intRewardsPerKill = dataTableGetIntColumn(strFileName, "intRewardPerKill");
        int intLength = strCreatureTypes.length;
        int intArrayIndex = 0;
        String[] strCreatureType = new String[intLength];
        String[] strDetailTemplate = new String[intLength];
        int[] intRewardPerKill = new int[intLength];
        obj_id groupObject = getGroupObject(objCreator);
        int maximumDifficultyInColumn = 0;
        if (isIdValid(groupObject))
        {
            setObjVar(objMissionData, "originalGroupSize", getPCGroupSize(groupObject));
        }
        for (int intI = 0; intI < strCreatureTypes.length; intI++)
        {
            LOG("missions", "intDifficulty is " + intDifficulty);
            LOG("missions", "intMinDifficulties[intI] is " + intMinDifficulties[intI]);
            LOG("missions", "intMaxDifficulties[intI] is " + intMaxDifficulties[intI]);
            strCreatureType[intArrayIndex] = strCreatureTypes[intI];
            LOG("missions", "adding " + strCreatureTypes[intI]);
            strDetailTemplate[intArrayIndex] = strDetailTemplates[intI];
            intRewardPerKill[intArrayIndex] = intRewardsPerKill[intI];
            intArrayIndex = intArrayIndex + 1;
        }
        if (strCreatureType == null || strCreatureType.length == 0)
        {
            return null;
        }
        int intRoll = rand(0, intArrayIndex - 1);
        String strAppearance = "object/mobile/" + strDetailTemplate[intRoll];
        LOG("missions", "strAppearance is " + strAppearance);
        setMissionTargetAppearance(objMissionData, strAppearance);
        string_id strTokenName = new string_id("mob/creature_names", strCreatureType[intRoll]);
        LOG("missions", "setting strCreatureType to " + strCreatureType[intRoll]);
        setMissionTargetName(objMissionData, utils.packStringId(strTokenName));
        setObjVar(objMissionData, "strCreatureToKill", strCreatureType[intRoll]);
        int intQuantityToKill = HUNTING_QUANTITIES[intMissionDifficulty - 1];
        setObjVar(objMissionData, "intQuantityToKill", intQuantityToKill);
        int intMultiplier = REWARD_MULTIPLIER[intMissionDifficulty - 1];
        int intReward = rand(300, 500) + intMultiplier * (intQuantityToKill * intRewardPerKill[intRoll]);
        setMissionReward(objMissionData, intReward);
        LOG("missions", "Hunting difficulty is " + intMissionDifficulty);
        setupContextualizationStrings(objMissionData, intMissionDifficulty, objCreator, strFaction);
        setMissionDifficulty(objMissionData, intDifficulty);
        return objMissionData;
    }
    public void sendAssassinFail(obj_id objMission) throws InterruptedException
    {
        dictionary dctParams = new dictionary();
        setObjVar(objMission, "intState", missions.STATE_MISSION_COMPLETE);
        messageTo(objMission, "assassinFailure", dctParams, 0, true);
    }
    public void sendAssassinSuccess(obj_id objMission) throws InterruptedException
    {
        dictionary dctParams = new dictionary();
        setObjVar(objMission, "intState", missions.STATE_MISSION_COMPLETE);
        messageTo(objMission, "assassinSuccess", dctParams, 0, true);
    }
    public void sendAssassinIncomplete(obj_id objMission) throws InterruptedException
    {
        dictionary dctParams = new dictionary();
        setObjVar(objMission, "intState", missions.STATE_MISSION_COMPLETE);
        messageTo(objMission, "assassinIncomplete", dctParams, 0, true);
    }
    public obj_id createAssassinMission(obj_id objMissionData, obj_id objCreator, int intDifficulty, String strFaction) throws InterruptedException
    {
        setMissionRootScriptName(objMissionData, "systems.missions.dynamic.mission_assassin");
        setMissionType(objMissionData, "assassin");
        location[] locMissionLocations = getAssassinMissionLocation();
        location locStartLocation = locMissionLocations[0];
        location locSpawnLocation = locMissionLocations[1];
        setMissionStartLocation(objMissionData, locStartLocation);
        setObjVar(objMissionData, "locSpawnLocation", locSpawnLocation);
        String strGoal = "object/tangible/mission/mission_bounty_target.iff";
        setMissionTargetAppearance(objMissionData, strGoal);
        string_id strName = new string_id("mission/mission_generic", "unknown");
        setMissionTargetName(objMissionData, utils.packStringId(strName));
        obj_id groupObject = getGroupObject(objCreator);
        if (isIdValid(groupObject))
        {
            setObjVar(objMissionData, "originalGroupSize", getPCGroupSize(groupObject));
        }
        int intReward = 2000;
        setMissionReward(objMissionData, intReward);
        setupContextualizationStrings(objMissionData, 1, objCreator, strFaction);
        setMissionDifficulty(objMissionData, intDifficulty);
        return objMissionData;
    }
    public location[] getAssassinMissionLocation() throws InterruptedException
    {
        location[] locMissionLocations = new location[2];
        String strPlanet = CITY_PLANETS[rand(0, CITY_PLANETS.length - 1)];
        region[] rgnCities = getRegionsWithMunicipal(strPlanet, regions.MUNI_TRUE);
        region rgnStartRegion = rgnCities[rand(0, rgnCities.length - 1)];
        location locStartLocation = locations.getRegionCenter(rgnStartRegion);
        location locSpawnLocation = locations.getGoodCityLocation(rgnStartRegion, strPlanet);
        locMissionLocations[0] = (location)locStartLocation.clone();
        locMissionLocations[1] = (location)locSpawnLocation.clone();
        return locMissionLocations;
    }
    public boolean createAssassinTarget(obj_id objMission) throws InterruptedException
    {
        String strNPC = NPC_TYPES[rand(0, NPC_TYPES.length - 1)];
        location locSpawnLocation = getLocationObjVar(objMission, "locSpawnLocation");
        obj_id objNPC = create.object(strNPC, locSpawnLocation);
        if (!isIdValid(objNPC))
        {
            return false;
        }
        setObjVar(objNPC, "objMission", objMission);
        setObjVar(objNPC, "objKiller", getMissionHolder(objMission));
        attachScript(objNPC, "systems.missions.dynamic.mission_assassin_target");
        persistAndRegisterObject(objNPC, objMission);
        setObjVar(objMission, "objNPC", objNPC);
        return true;
    }
    public obj_id getAssassinMission(obj_id objPlayer) throws InterruptedException
    {
        if (isIdValid(objPlayer))
        {
            obj_id[] objMissionArray = getMissionObjects(objPlayer);
            if (objMissionArray != null)
            {
                for (int intI = 0; intI < objMissionArray.length; intI++)
                {
                    String strType = getMissionType(objMissionArray[intI]);
                    if (strType.equals("assassin"))
                    {
                        return objMissionArray[intI];
                    }
                }
            }
        }
        return null;
    }
    public boolean isPlanetHuntingViable(obj_id player, String area) throws InterruptedException
    {
        int playerLevel = getLevel(player);
        if (area.equals("tatooine") && playerLevel > 30)
        {
            return false;
        }
        if ((area.equals("naboo") || area.equals("corelia")) && (playerLevel < 20 || playerLevel > 40))
        {
            return false;
        }
        if ((area.equals("rori") || area.equals("talus")) && (playerLevel < 40 || playerLevel > 60))
        {
            return false;
        }
        if ((area.equals("dantooine") || area.equals("lok")) && (playerLevel < 50 || playerLevel > 70))
        {
            return false;
        }
        if ((area.equals("yavin4") || area.equals("endor")) && (playerLevel < 70))
        {
            return false;
        }
        if (area.equals("dathomir") && playerLevel < 80)
        {
            return false;
        }
        return true;
    }
    public void msgWrongHuntingPlanet(obj_id player) throws InterruptedException
    {
        int playerLevel = getLevel(player);
        int msgIndex = playerLevel / 10;
        string_id[] missionPlanets = 
        {
            SID_MISSION_TATOOINE,
            SID_MISSION_TATOOINE,
            SID_MISSION_TATOOINE_NABOO_CORELIA,
            SID_MISSION_NABOO_CORELIA,
            SID_MISSION_RORI_TALUS,
            SID_MISSION_RORI_TALUS_DANTOOINE_LOK,
            SID_MISSION_DANTOOINE_LOK,
            SID_MISSION_YAVIN_ENDOR,
            SID_MISSION_YAVIN_ENDOR_DATHOMIR,
            SID_MISSION_YAVIN_ENDOR_DATHOMIR
        };
        if (msgIndex > missionPlanets.length)
        {
            return;
        }
        sendSystemMessage(player, missionPlanets[msgIndex]);
    }
}
