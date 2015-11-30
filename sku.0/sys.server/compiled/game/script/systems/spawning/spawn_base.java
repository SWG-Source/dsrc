package script.systems.spawning;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.skill;
import script.library.city;
import script.library.regions;
import script.library.locations;
import script.library.battlefield;
import script.library.utils;
import script.library.create;
import script.library.group;
import script.library.gcw;
import script.library.ai_lib;
import script.library.vehicle;

public class spawn_base extends script.base_script
{
    public spawn_base()
    {
    }
    public static final int SPAWN_HEARBEAT_SPAWN_EVENT = 5;
    public static final int SPAWN_PLAYER_DELAY_MIN = 30;
    public static final int SPAWN_PLAYER_DELAY_MAX = 60;
    public static final int SPAWN_DISTANCE_MIN = 12;
    public static final int SPAWN_DISTANCE_MAX = 32;
    public static final int SPAWN_CHECK_DISTANCE = 64;
    public static final int SPAWN_CHECK_LIMIT = 15;
    public static final int SPAWN_TEMPLATE_CHECK_DISTANCE = 128;
    public static final int SPAWN_CHECK_TEMPLATE_LIMIT = 14;
    public static final int SPAWN_THEATER_CHECK_DISTANCE = 200;
    public static final int SPAWN_CHECK_THEATER_LIMIT = 1;
    public static final int EXTERIOR_SPAWN_CHANCE = 50;
    public static final int EXTERIOR_MAX_NPC = 10;
    public static final int INTERIOR_MAX_NPC = 10;
    public static final int EXTERIOR_MIN_NPC = 4;
    public static final int INTERIOR_MIN_NPC = 4;
    public static final int PLAYER_TO_NPC_RATIO = 1;
    public static final boolean boolFastSpawnEnabled = false;
    public static final int MAXIMUM_SPAWNING_RUN_TIME_RULES = 200;
    public static final float CREATURES_TO_PLAYERS_RATIO = 20;
    public static final String[] INVALID_SPAWNING_AREAS = 
    {
        "tutorial"
    };
    public int[] getValidSpawn(dictionary dctPlayerStats) throws InterruptedException
    {
        if (dctPlayerStats == null)
        {
            return null;
        }
        boolean boolTheatersAllowed = dctPlayerStats.getBoolean("boolTheatersAllowed");
        String strRegionName = dctPlayerStats.getString("strRegionName");
        String strPlanet = dctPlayerStats.getString("strPlanet");
        region rgnSpawnRegion = getRegion(strPlanet, strRegionName);
        obj_id objPlayer = dctPlayerStats.getObjId("objPlayer");
        if (rgnSpawnRegion == null)
        {
            return null;
        }
        String strRegion = regions.translateGeoToString(rgnSpawnRegion.getGeographicalType());
        if (strRegion == null || strRegion.equals(""))
        {
            return null;
        }
        String strFileName = "";
        if (strRegion.equals("fictional"))
        {
            if ((strRegionName == null || strRegionName.equals("")) || (strPlanet == null || strPlanet.equals("")))
            {
                return null;
            }
            strFileName = getFictionalRegionFileName(strPlanet, strRegionName);
        }
        else if (strRegion.equals("overload"))
        {
            if (strRegionName == null || strRegionName.equals(""))
            {
                return null;
            }
            strFileName = getOverLoadRegionFileName(strRegionName);
        }
        else 
        {
            if (strPlanet == null || strPlanet.equals(""))
            {
                return null;
            }
            strFileName = "datatables/spawning/spawn_lists/" + strPlanet + "/" + strPlanet + "_" + strRegion + ".iff";
        }
        if (strFileName == null)
        {
            return null;
        }
        String[] strTemplates = dataTableGetStringColumn(strFileName, "strTemplate");
        int[] intMinDifficulties = dataTableGetIntColumn(strFileName, "intMinDifficulty");
        int[] intMaxDifficulties = dataTableGetIntColumn(strFileName, "intMaxDifficulty");
        int[] intGcwFactions = null;
        int[] intGcwThresholds = null;
        int[] intGcwScoreTypes = null;
        String[] strGcwSpecificRegions = null;
        if (dataTableHasColumn(strFileName, "intGCWFaction"))
        {
            intGcwFactions = dataTableGetIntColumn(strFileName, "intGCWFaction");
            intGcwThresholds = dataTableGetIntColumn(strFileName, "intGCWThreshold");
            intGcwScoreTypes = dataTableGetIntColumn(strFileName, "intGCWScoreType");
            strGcwSpecificRegions = dataTableGetStringColumn(strFileName, "strGCWSpecificRegion");
        }
        boolean checkGCWStats = true;
        if (strTemplates == null || strTemplates.length == 0 || intMinDifficulties == null || intMaxDifficulties == null)
        {
            LOG("spawning", "Invalid spawn template, or min/max difficulty values are invalid. Filename = " + strFileName);
            return null;
        }
        location currentLoc = getLocation(objPlayer);
        if (intGcwFactions == null || intGcwThresholds == null || intGcwScoreTypes == null)
        {
            checkGCWStats = false;
        }
        Vector validTemplateIndices = new Vector();
        validTemplateIndices.setSize(0);
        if (validTemplateIndices == null)
        {
            return null;
        }
        for (int i = 0; i < strTemplates.length; ++i)
        {
            int intMinDifficulty = intMinDifficulties[i];
            int intMaxDifficulty = intMaxDifficulties[i];
            if (checkGCWStats)
            {
                int intGcwFaction = intGcwFactions[i];
                int intGcwThreshold = intGcwThresholds[i];
                int intGcwScoreType = intGcwScoreTypes[i];
                String strSpecificRegion = strGcwSpecificRegions == null || strGcwSpecificRegions.length == 0 ? "" : strGcwSpecificRegions[i];
                if (!checkGalacticCivilWarStandings(intGcwFaction, intGcwThreshold, intGcwScoreType, strSpecificRegion, currentLoc))
                {
                    continue;
                }
            }
            if (checkDifficulty(intMinDifficulty, intMaxDifficulty, dctPlayerStats))
            {
                if (!boolTheatersAllowed && strTemplates[i].indexOf("theater") > 0)
                {
                    continue;
                }
                validTemplateIndices = utils.addElement(validTemplateIndices, i);
            }
        }
        int[] _validTemplateIndices = new int[0];
        if (validTemplateIndices != null)
        {
            _validTemplateIndices = new int[validTemplateIndices.size()];
            for (int _i = 0; _i < validTemplateIndices.size(); ++_i)
            {
                _validTemplateIndices[_i] = ((Integer)validTemplateIndices.get(_i)).intValue();
            }
        }
        return _validTemplateIndices;
    }
    public boolean checkDifficulty(int intMinTemplateDifficulty, int intMaxTemplateDifficulty, dictionary dctPlayerStats) throws InterruptedException
    {
        return true;
    }
    public int getPlayerSpawnDiffibculty(obj_id objPlayer) throws InterruptedException
    {
        obj_id objGroup = getGroupObject(objPlayer);
        if (!isIdValid(objGroup))
        {
            int intPlayerLevel = getLevel(objPlayer);
            return intPlayerLevel;
        }
        else 
        {
            int intGroupLevel = skill.getGroupLevel(objPlayer);
            return intGroupLevel;
        }
    }
    public obj_id createTemplate(location locSpawnLocation, dictionary params, obj_id objPlayer) throws InterruptedException
    {
        String strRegionName = params.getString("strRegionName");
        String strPlanet = params.getString("strPlanet");
        region rgnSpawnRegion = getRegion(strPlanet, strRegionName);
        if (rgnSpawnRegion == null)
        {
            return null;
        }
        int intIndex = params.getInt("intIndex");
        int intPlayerDifficulty = params.getInt("intPlayerDifficulty");
        int intNumToSpawn = params.getInt("intNumToSpawn");
        String strTemplateToSpawn = params.getString("strTemplateToSpawn");
        String strTemplate = params.getString("strTemplate");
        int intMinDifficulty = params.getInt("intMinDifficulty");
        int intMaxDifficulty = params.getInt("intMaxDifficulty");
        String strLairType = params.getString("strLairType");
        String strBuildingType = params.getString("strBuildingType");
        obj_id objCreatedTemplate = createObject(strTemplate, locSpawnLocation);
        if (isIdValid(objCreatedTemplate))
        {
            if (group.isGrouped(objPlayer))
            {
                obj_id objGroup = getGroupObject(objPlayer);
                if (isIdValid(objGroup))
                {
                    int intGroupSize = getGroupSize(objGroup);
                    setObjVar(objCreatedTemplate, "spawning.intGroupSize", intGroupSize);
                }
            }
            attachScript(objCreatedTemplate, "systems.spawning.spawn_template");
            if (!strLairType.equals(""))
            {
                setObjVar(objCreatedTemplate, "spawning.intDifficultyLevel", intPlayerDifficulty);
                String strDifficulty = create.getLairDifficulty(intMinDifficulty, intMaxDifficulty, intPlayerDifficulty);
                setObjVar(objCreatedTemplate, "spawning.lairDifficulty", strDifficulty);
                setObjVar(objCreatedTemplate, "spawning.lairType", strLairType);
                setObjVar(objCreatedTemplate, "spawning.buildingTrackingType", strBuildingType);
                if (!strBuildingType.equals(""))
                {
                    setObjVar(objCreatedTemplate, "spawning.buildingType", strBuildingType);
                }
                obj_id groupObject = getGroupObject(objPlayer);
                if (isIdValid(groupObject))
                {
                    setObjVar(objCreatedTemplate, "spawning.groupSize", getPCGroupSize(groupObject));
                }
                else 
                {
                    setObjVar(objCreatedTemplate, "spawning.groupSize", 1);
                }
            }
            else 
            {
                if (strTemplate.indexOf("lair") > 0)
                {
                    setObjVar(objCreatedTemplate, "numToSpawn", intNumToSpawn);
                    if (!strTemplateToSpawn.equals("") && strTemplateToSpawn != null)
                    {
                        setObjVar(objCreatedTemplate, "creatureTemplate", strTemplateToSpawn);
                    }
                }
                if (strTemplate.indexOf("herd") > 0)
                {
                    setObjVar(objCreatedTemplate, "numToSpawn", intNumToSpawn);
                    if ((!strTemplateToSpawn.equals("")) && (strTemplateToSpawn != null))
                    {
                        setObjVar(objCreatedTemplate, "creatureTemplate", strTemplateToSpawn);
                    }
                }
            }
            dictionary dctParams = new dictionary();
            messageTo(objCreatedTemplate, "handle_Spawn_Setup_Complete", dctParams, 0, false);
            dctParams.put("strTemplate", strTemplate);
            dctParams.put("strRegionName", strRegionName);
            dctParams.put("strPlanet", strPlanet);
            dctParams.put("strLairType", strLairType);
            dctParams.put("strBuildingType", strBuildingType);
        }
        else 
        {
            return null;
        }
        return objCreatedTemplate;
    }
    public dictionary chooseWeightedSpawnTemplate(int[] intDataIndex, String strRegionName, String strPlanetName, region rgnRegion) throws InterruptedException
    {
        String strFileName;
        if (strRegionName.equals("fictional"))
        {
            String strRegionFullName = rgnRegion.getName();
            strFileName = getFictionalRegionFileName(strPlanetName, strRegionFullName);
        }
        else if (strRegionName.equals("overload"))
        {
            strFileName = getOverLoadRegionFileName(strRegionName);
        }
        else 
        {
            strFileName = "datatables/spawning/spawn_lists/" + strPlanetName + "/" + strPlanetName + "_" + strRegionName + ".iff";
        }
        dictionary dctRegion = new dictionary();
        String[] strTemplates = dataTableGetStringColumn(strFileName, "strTemplate");
        int[] intSpawnLimits = dataTableGetIntColumn(strFileName, "intSpawnLimit");
        int[] intMinDifficulties = dataTableGetIntColumn(strFileName, "intMinDifficulty");
        int[] intMaxDifficulties = dataTableGetIntColumn(strFileName, "intMaxDifficulty");
        int[] intWeightings = dataTableGetIntColumn(strFileName, "intWeighting");
        int[] intSizes = dataTableGetIntColumn(strFileName, "intSize");
        int[] intNumToSpawns = dataTableGetIntColumn(strFileName, "intNumToSpawn");
        String[] strTemplatesToSpawn = dataTableGetStringColumn(strFileName, "strTemplateToSpawn");
        String[] strLairTypes = dataTableGetStringColumn(strFileName, "strLairType");
        String[] strBuildingTypes = dataTableGetStringColumn(strFileName, "strBuildingType");
        int selectedIndex = 0;
        if (intDataIndex.length != 1)
        {
            int weightingGrandTotal = 0;
            for (int i = 0; i < intDataIndex.length; ++i)
            {
                weightingGrandTotal += intWeightings[intDataIndex[i]];
            }
            int randomValue = rand(0, weightingGrandTotal - 1);
            int previousChance = 0;
            for (int i = 0; i < intDataIndex.length; ++i)
            {
                int currentChance = previousChance + intWeightings[intDataIndex[i]];
                if (randomValue >= previousChance && randomValue < currentChance)
                {
                    selectedIndex = i;
                    break;
                }
                previousChance = currentChance;
            }
        }
        int intIndex = intDataIndex[selectedIndex];
        dictionary dctSpawnInformation = new dictionary();
        dctSpawnInformation.put("intIndex", intIndex);
        dctSpawnInformation.put("strTemplate", strTemplates[intIndex]);
        dctSpawnInformation.put("intMinDifficulty", intMinDifficulties[intIndex]);
        dctSpawnInformation.put("intMaxDifficulty", intMaxDifficulties[intIndex]);
        dctSpawnInformation.put("intSize", intSizes[intIndex]);
        dctSpawnInformation.put("intNumToSpawn", intNumToSpawns[intIndex]);
        dctSpawnInformation.put("strLairType", strLairTypes[intIndex]);
        dctSpawnInformation.put("strBuildingType", strBuildingTypes[intIndex]);
        dctSpawnInformation.put("strTemplateToSpawn", strTemplatesToSpawn[intIndex]);
        return dctSpawnInformation;
    }
    public void sendSpawnSpam(obj_id objPlayer, boolean boolLogFailures, boolean boolVerboseMode, String strSpam) throws InterruptedException
    {
        if (boolLogFailures)
        {
        }
        if (boolVerboseMode && isIdValid(objPlayer))
        {
            deltadictionary dctScriptVars = objPlayer.getScriptVars();
            int intLastSpamTime = dctScriptVars.getInt("intLastSpamTime");
            int intCurrentTime = getGameTime();
            int intDifference = intCurrentTime - intLastSpamTime;
            if (intDifference > 5)
            {
                dctScriptVars.put("intLastSpamTime", intCurrentTime);
                sendSystemMessageTestingOnly(objPlayer, strSpam);
            }
        }
        return;
    }
    public boolean getVerboseMode(obj_id objPlayer) throws InterruptedException
    {
        if (hasObjVar(objPlayer, "spawning.verboseMode"))
        {
            return true;
        }
        return false;
    }
    public boolean checkTemplatesInRange(obj_id objPlayer, location locHome, boolean boolCheckForTheaters, String strObjectType) throws InterruptedException
    {
        boolean boolLogFailures = checkSpawnLogFailures();
        boolean boolVerboseMode = getVerboseMode(objPlayer);
        final int SPAWN_TEMPLATE_CHECK_DISTANCE = 128;
        final int SPAWN_CHECK_TEMPLATE_LIMIT = 140;
        final int SPAWN_THEATER_CHECK_DISTANCE = 200;
        final int SPAWN_CHECK_THEATER_LIMIT = 1;
        return true;
    }
    public boolean canSpawn(obj_id objPlayer, location locSpawnLocation, boolean boolCheckForTheaters, String strObjectType) throws InterruptedException
    {
        boolean boolLogFailures = checkSpawnLogFailures();
        boolean boolVerboseMode = getVerboseMode(objPlayer);
        if (!isSpawningAllowed(locSpawnLocation))
        {
            return false;
        }
        final int intServerSpawnLimit = getServerSpawnLimit();
        final int intNumCreatures = utils.getNumCreaturesForSpawnLimit();
        final int intNumPlayers = getNumPlayers();
        if (intNumPlayers > 0)
        {
            if (intServerSpawnLimit > 0)
            {
                if (intNumCreatures > intServerSpawnLimit)
                {
                    return false;
                }
            }
            else 
            {
                if (intNumCreatures > 5000)
                {
                    return false;
                }
            }
        }
        location locCurrentLocation = getLocation(objPlayer);
        if (locCurrentLocation.area == "tutorial")
        {
            return false;
        }
        if (city.isInCity(locCurrentLocation))
        {
            return false;
        }
        region[] rgnCities = getRegionsWithMunicipalAtPoint(locCurrentLocation, regions.MUNI_TRUE);
        if (rgnCities != null)
        {
            return false;
        }
        region rgnBattlefield = battlefield.getBattlefield(locCurrentLocation);
        if (rgnBattlefield != null)
        {
            return false;
        }
        rgnCities = getRegionsWithGeographicalAtPoint(locCurrentLocation, regions.GEO_CITY);
        if (rgnCities != null)
        {
            return false;
        }
        obj_id objMasterSpawner = getPlanetByName(locCurrentLocation.area);
        if (hasObjVar(objMasterSpawner, "boolSpawnerIsOn"))
        {
            boolean boolSpawnerIsOn;
            boolSpawnerIsOn = getBooleanObjVar(objMasterSpawner, "boolSpawnerIsOn");
            if (boolSpawnerIsOn == false)
            {
                return boolSpawnerIsOn;
            }
        }
        if (locCurrentLocation.cell != obj_id.NULL_ID)
        {
            return false;
        }
        if (hasObjVar(objPlayer, "spawning.locSpawnLocation"))
        {
            int intSpawnedTemplates = 0;
            if (hasObjVar(objPlayer, "spawning.intSpawnedTemplates"))
            {
                intSpawnedTemplates = getIntObjVar(objPlayer, "spawning.intSpawnedTemplates");
            }
            if (intSpawnedTemplates >= SPAWN_CHECK_LIMIT)
            {
                location locLastSpawnLocation = getLocationObjVar(objPlayer, "spawning.locSpawnLocation");
                float fltDistance = utils.getDistance(locLastSpawnLocation, locCurrentLocation);
                if (fltDistance < SPAWN_CHECK_DISTANCE)
                {
                    return false;
                }
            }
        }
        if (locSpawnLocation != null)
        {
            if (city.isInCity(locSpawnLocation))
            {
                return false;
            }
            rgnCities = getRegionsWithMunicipalAtPoint(locSpawnLocation, regions.MUNI_TRUE);
            if (rgnCities != null)
            {
                return false;
            }
            rgnCities = getRegionsWithGeographicalAtPoint(locSpawnLocation, regions.GEO_CITY);
            if (rgnCities != null)
            {
                return false;
            }
            rgnBattlefield = battlefield.getBattlefield(locSpawnLocation);
            if (rgnBattlefield != null)
            {
                return false;
            }
        }
        return true;
    }
    public region getSpawnRegion(obj_id objPlayer) throws InterruptedException
    {
        location locTest = getLocation(objPlayer);
        String strPlanet = locTest.area;
        region[] rgnRegionList = getRegionsWithSpawnableAtPoint(locTest, regions.SPAWN_TRUE);
        if (rgnRegionList == null)
        {
            rgnRegionList = getRegionsWithSpawnableAtPoint(locTest, regions.SPAWN_DEFAULT);
        }
        if (rgnRegionList == null)
        {
            return null;
        }
        region rgnSpawnRegion = locations.getSmallestRegion(rgnRegionList);
        String strTestPlanet = rgnSpawnRegion.getPlanetName();
        if (!strTestPlanet.equals(strPlanet))
        {
        }
        if (rgnSpawnRegion == null)
        {
            return null;
        }
        if (rand(1, 100) > 50)
        {
            region rgnOverloadRegion = null;
            region[] rgnOverloadRegions = getRegionsWithGeographicalAtPoint(locTest, regions.GEO_OVERLOAD);
            if ((rgnOverloadRegions != null) && (rgnOverloadRegions.length > 0))
            {
                rgnOverloadRegion = rgnOverloadRegions[rand(0, rgnOverloadRegions.length - 1)];
            }
            if (rgnOverloadRegion != null)
            {
                return rgnOverloadRegion;
            }
        }
        return rgnSpawnRegion;
    }
    public void preLoadSpawnDataTables(obj_id objMasterSpawner) throws InterruptedException
    {
        return;
    }
    public boolean checkSpawnLogFailures() throws InterruptedException
    {
        String strConfigSetting = getConfigSetting("GameServer", "fastSpawn");
        if (strConfigSetting == null)
        {
            return false;
        }
        if (strConfigSetting.equals("true"))
        {
            return true;
        }
        return false;
    }
    public boolean isSpawningAllowed(location locTest) throws InterruptedException
    {
        if (locTest == null || locTest.area == null)
        {
            return false;
        }
        for (int i = 0; i < INVALID_SPAWNING_AREAS.length; ++i)
        {
            if (locTest.area == INVALID_SPAWNING_AREAS[i])
            {
                return false;
            }
        }
        return true;
    }
    public String getFictionalRegionFileName(String strPlanet, String strFullName) throws InterruptedException
    {
        string_id strId = utils.unpackString(strFullName);
        String strRegionName = strId.getAsciiId();
        String strTableName = "datatables/spawning/spawn_lists/" + strPlanet + "/" + strRegionName + ".iff";
        return strTableName;
    }
    public String getOverLoadRegionFileName(String strRegionName) throws InterruptedException
    {
        int intIndex = strRegionName.indexOf("-");
        if (intIndex < 0)
        {
            return "";
        }
        String strFileName = strRegionName.substring(0, intIndex);
        String strTableName = "datatables/spawning/spawn_lists/spawn_overloads/" + strFileName + ".iff";
        return strTableName;
    }
    public void doSpawnEvent(dictionary params) throws InterruptedException
    {
        if (params == null)
        {
            return;
        }
        obj_id objPlayer = params.getObjId("objPlayer");
        if (!isIdValid(objPlayer))
        {
            return;
        }
        int intPlayerDifficulty = params.getInt("intDifficulty");
        String strRegionName = params.getString("strRegionName");
        String strPlanet = params.getString("strPlanet");
        if (strRegionName == null || strRegionName.length() < 1)
        {
            return;
        }
        if (strPlanet == null || strPlanet.length() < 1)
        {
            return;
        }
        region rgnSpawnRegion = getRegion(strPlanet, strRegionName);
        if (rgnSpawnRegion == null)
        {
            LOG("spawning", objPlayer + " GETREGION RETURNED NULL FOR " + strRegionName + " and planet " + strPlanet);
            return;
        }
        int[] validSpawnIndices = getValidSpawn(params);
        if (validSpawnIndices == null || validSpawnIndices.length == 0)
        {
            LOG("DESIGNER_FATAL", "for dictionary " + params.toString() + " objvarids is null");
            LOG("spawn", "for dictionary " + params.toString() + " objvarids is null");
            return;
        }
        String strRegionType = regions.translateGeoToString(rgnSpawnRegion.getGeographicalType());
        if (strRegionType == null || strRegionType.length() < 1)
        {
            return;
        }
        dictionary dctSpawnInformation = new dictionary();
        dctSpawnInformation = chooseWeightedSpawnTemplate(validSpawnIndices, strRegionType, strPlanet, rgnSpawnRegion);
        if (dctSpawnInformation == null)
        {
            return;
        }
        dctSpawnInformation.put("intPlayerDifficulty", intPlayerDifficulty);
        dctSpawnInformation.put("strRegionName", strRegionName);
        dctSpawnInformation.put("strPlanet", strPlanet);
        doSpawnTemplate(objPlayer, dctSpawnInformation);
    }
    public void doSpawnTemplate(obj_id self, dictionary params) throws InterruptedException
    {
        String strTemplate = params.getString("strTemplate");
        int intIndex = params.getInt("intIndex");
        int intMinDifficulty = params.getInt("intMinDifficulty");
        int intMaxDifficulty = params.getInt("intMaxDifficulty");
        int intSize = params.getInt("intSize");
        int intNumToSpawn = params.getInt("intNumToSpawn");
        String strLairType = params.getString("strLairType");
        String strBuildingType = params.getString("strBuildingType");
        String strTemplateToSpawn = params.getString("strBuildingType");
        String strRegionName = params.getString("strRegionName");
        String strPlanet = params.getString("strPlanet");
        int intPlayerDifficulty = params.getInt("intPlayerDifficulty");
        dictionary dctParams = new dictionary();
        location locSpawnLocation = getLocation(self);
        if (canSpawn(self, locSpawnLocation, true, strLairType))
        {
            dctParams.put("intMinDifficulty", intMinDifficulty);
            dctParams.put("intMaxDifficulty", intMaxDifficulty);
            dctParams.put("strLairType", strLairType);
            dctParams.put("strBuildingType", strBuildingType);
            dctParams.put("strRegionName", strRegionName);
            dctParams.put("strPlanet", strPlanet);
            dctParams.put("intIndex", intIndex);
            dctParams.put("intPlayerDifficulty", intPlayerDifficulty);
            dctParams.put("intNumToSpawn", intNumToSpawn);
            dctParams.put("strTemplateToSpawn", strTemplateToSpawn);
            dctParams.put("strTemplate", strTemplate);
            float fltSize = getLocationSize((float)intSize);
            utils.setScriptVar(self, "dctSpawnInfo", dctParams);
            requestLocation(self, "spawnLocation", locSpawnLocation, 100.0f, fltSize, true, false);
        }
        return;
    }
    public int OnLocationReceived(obj_id self, String strId, obj_id objObject, location locSpawnLocation, float fltRadius) throws InterruptedException
    {
        if (strId.equals("spawnLocation"))
        {
            if (!isIdValid(objObject))
            {
                return SCRIPT_CONTINUE;
            }
            dictionary dctSpawnInfo = utils.getDictionaryScriptVar(self, "dctSpawnInfo");
            if (dctSpawnInfo == null)
            {
                return SCRIPT_CONTINUE;
            }
            obj_id objCreatedTemplate = createTemplate(locSpawnLocation, dctSpawnInfo, self);
            setObjVar(objCreatedTemplate, "objLocationReservation", objObject);
        }
        return SCRIPT_CONTINUE;
    }
    public float getLocationSize(float fltLocation) throws InterruptedException
    {
        if (fltLocation < 32f)
        {
            return 32f;
        }
        else if (fltLocation >= 32 && fltLocation <= 48)
        {
            return 48f;
        }
        else if (fltLocation >= 48 && fltLocation <= 64)
        {
            return 64f;
        }
        else if (fltLocation >= 64 && fltLocation <= 80)
        {
            return 80f;
        }
        else 
        {
            return 96f;
        }
    }
    public boolean isGalaticCivilWarWinner(int faction, int threshold, int imperialScore) throws InterruptedException
    {
        if (faction == 1 && threshold < imperialScore)
        {
            return true;
        }
        else if (faction == 2 && threshold < (100 - imperialScore))
        {
            return true;
        }
        return false;
    }
    public boolean checkGalacticCivilWarStandings(int gcwFaction, int gcwThreshold, int gcwScoreType, String gcwSpecificRegion, location loc) throws InterruptedException
    {
        if (gcwFaction > 0 && gcwThreshold > 0)
        {
            if (gcwScoreType == 0)
            {
                int ImperialScore = getGcwGroupImperialScorePercentile(toLower(getCurrentSceneName()));
                return isGalaticCivilWarWinner(gcwFaction, gcwThreshold, ImperialScore);
            }
            else if (gcwScoreType == 1)
            {
                int ImperialScore = 0;
                region[] allRegions = getRegionsAtPoint(loc);
                if (allRegions != null && allRegions.length > 0)
                {
                    for (int iter = 0; iter < allRegions.length; ++iter)
                    {
                        region gcwRegion = allRegions[iter];
                        if ((gcwRegion.getName()).startsWith("gcw"))
                        {
                            ImperialScore = getGcwImperialScorePercentile(toLower(gcwRegion.getName()));
                            break;
                        }
                    }
                }
                else 
                {
                    return false;
                }
                return isGalaticCivilWarWinner(gcwFaction, gcwThreshold, ImperialScore);
            }
            else if (gcwScoreType == 2)
            {
                if (gcwSpecificRegion == null || gcwSpecificRegion.equals(""))
                {
                    LOG("GCWSpawnError", "Invalid Specific region specified");
                    return false;
                }
                int ImperialScore = getGcwImperialScorePercentile(toLower(gcwSpecificRegion));
                return isGalaticCivilWarWinner(gcwFaction, gcwThreshold, ImperialScore);
            }
            else 
            {
                return false;
            }
        }
        return true;
    }
}
