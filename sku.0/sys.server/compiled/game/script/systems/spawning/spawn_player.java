package script.systems.spawning;

import script.*;
import script.library.*;

import java.util.Vector;

public class spawn_player extends script.systems.spawning.spawn_base
{
    public spawn_player()
    {
    }
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        removeObjVar(self, "spawning.verboseMode");
        return SCRIPT_CONTINUE;
    }
    public int showSpawnRegion(obj_id self, obj_id target, String params, float defaultTime) throws InterruptedException
    {
        region[] rgnRegionList = getRegionsWithSpawnableAtPoint(getLocation(self), regions.SPAWN_TRUE);
        if (rgnRegionList == null)
        {
            rgnRegionList = getRegionsWithSpawnableAtPoint(getLocation(self), regions.SPAWN_DEFAULT);
        }
        if (rgnRegionList == null)
        {
            sendSystemMessageTestingOnly(self, "Catastrophic failure, no spawn region at this point");
            return SCRIPT_CONTINUE;
        }
        region rgnOverloadRegion = null;
        region[] rgnOverloadRegions = getRegionsWithGeographicalAtPoint(getLocation(self), regions.GEO_OVERLOAD);
        if ((rgnOverloadRegions != null) && (rgnOverloadRegions.length > 0))
        {
            rgnOverloadRegion = rgnOverloadRegions[rand(0, rgnOverloadRegions.length - 1)];
        }
        region rgnSpawnRegion = locations.getSmallestRegion(rgnRegionList);
        if (rgnOverloadRegion != null)
        {
            sendSystemMessageTestingOnly(self, "Multiple Spawn Regions found : ");
            sendSystemMessageTestingOnly(self, "Overload region is " + rgnOverloadRegion.getName());
        }
        else 
        {
            sendSystemMessageTestingOnly(self, "Spawn Region:");
        }
        sendSystemMessageTestingOnly(self, "Region: " + rgnSpawnRegion.getName());
        return SCRIPT_CONTINUE;
    }
    public int secretSpawnSpam(obj_id self, obj_id target, String params, float defaultTime) throws InterruptedException
    {
        if (hasObjVar(self, "spawning.verboseMode"))
        {
            removeObjVar(self, "spawning.verboseMode");
            sendSystemMessageTestingOnly(self, "Verbose spawn checks disabled");
        }
        else 
        {
            setObjVar(self, "spawning.verboseMode", 1);
            sendSystemMessageTestingOnly(self, "Verbose spawning checks enabled");
        }
        return SCRIPT_CONTINUE;
    }
    public int OnAddedToWorld(obj_id self) throws InterruptedException
    {
        messageTo(self, "checkStationCells", null, 2, false);
        return SCRIPT_CONTINUE;
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        return SCRIPT_CONTINUE;
    }
    public int createSpawningElement(obj_id self, obj_id target, String params, float defaultTime) throws InterruptedException
    {
        if (params.equals(""))
        {
            sendSystemMessageTestingOnly(self, "You must provide a datatable and an index for this to work properly");
            return SCRIPT_CONTINUE;
        }
        int intIndex = params.indexOf(" ");
        String strTable = params.substring(0, intIndex);
        strTable = "datatables/spawning/spawn_lists/" + strTable + ".iff";
        debugSpeakMsg(self, "strTable is " + strTable);
        String strNumber = params.substring(intIndex + 1, params.length());
        debugSpeakMsg(self, "strNumber is " + strNumber);
        if (!dataTableOpen(strTable))
        {
            sendSystemMessageTestingOnly(self, strTable + " is not a valid dataTable");
            return SCRIPT_CONTINUE;
        }
        Integer intTestIndex;
        try
        {
            intTestIndex = new Integer(strNumber);
        }
        catch(NumberFormatException err)
        {
            sendSystemMessageTestingOnly(self, strNumber + " is not a valid index");
            return SCRIPT_CONTINUE;
        }
        intIndex = intTestIndex;
        dictionary dctRow = dataTableGetRow(strTable, intIndex);
        if (dctRow == null)
        {
            sendSystemMessageTestingOnly(self, "Not a valid combination of data");
            return SCRIPT_CONTINUE;
        }
        int intMinDifficulty = dctRow.getInt("intMinDifficulty");
        int intMaxDifficulty = dctRow.getInt("intMaxDifficulty");
        int intPlayerDifficulty = rand(intMinDifficulty, intMaxDifficulty);
        String strTemplate = dctRow.getString("strTemplate");
        String strLairType = dctRow.getString("strLairType");
        String strBuildingType = dctRow.getString("strBuildingType");
        obj_id objCreatedTemplate = createObject(strTemplate, getLocation(self));
        if (!(objCreatedTemplate == null))
        {
            if (group.isGrouped(self))
            {
                obj_id objGroup = getGroupObject(self);
                if (objGroup != null)
                {
                    int intGroupSize = getGroupSize(objGroup);
                    setObjVar(objCreatedTemplate, "spawning.intGroupSize", intGroupSize);
                }
            }
            setObjVar(objCreatedTemplate, "spawning.intDifficultyLevel", intPlayerDifficulty);
            String strDifficulty = create.getLairDifficulty(intMinDifficulty, intMaxDifficulty, intPlayerDifficulty);
            setObjVar(objCreatedTemplate, "spawning.lairDifficulty", strDifficulty);
            setObjVar(objCreatedTemplate, "spawning.lairType", strLairType);
            setObjVar(objCreatedTemplate, "spawning.buildingTrackingType", strBuildingType);
            if (!strBuildingType.equals(""))
            {
                setObjVar(objCreatedTemplate, "spawning.buildingType", strBuildingType);
            }
        }
        else 
        {
            debugSpeakMsg(self, "I made a null object");
            debugSpeakMsg(self, "row was " + dctRow.toString());
        }
        return SCRIPT_CONTINUE;
    }
    public int createSpawningElementWithDifficulty(obj_id self, obj_id target, String params, float defaultTime) throws InterruptedException
    {
        if (params.equals(""))
        {
            sendSystemMessageTestingOnly(self, "You must provide a datatable, difficulty and an index for this to work properly");
            return SCRIPT_CONTINUE;
        }
        int intIndex = params.indexOf(" ");
        String strTable = params.substring(0, intIndex);
        strTable = "datatables/spawning/spawn_lists/" + strTable + ".iff";
        String strRemainingString = params.substring(intIndex + 1, params.length());
        int intNextIndex = strRemainingString.indexOf(" ");
        String strNumber = strRemainingString.substring(0, intNextIndex);
        String strDifficultyInt = strRemainingString.substring(intNextIndex + 1, strRemainingString.length());
        if (!dataTableOpen(strTable))
        {
            sendSystemMessageTestingOnly(self, strTable + " is not a valid dataTable");
            return SCRIPT_CONTINUE;
        }
        Integer intTestIndex;
        try
        {
            intTestIndex = new Integer(strNumber);
        }
        catch(NumberFormatException err)
        {
            sendSystemMessageTestingOnly(self, strNumber + " is not a valid index");
            return SCRIPT_CONTINUE;
        }
        intIndex = intTestIndex;
        try
        {
            intTestIndex = new Integer(strDifficultyInt);
        }
        catch(NumberFormatException err)
        {
            sendSystemMessageTestingOnly(self, strDifficultyInt + " is not a valid index");
            return SCRIPT_CONTINUE;
        }
        int intLevel = intTestIndex;
        dictionary dctRow = dataTableGetRow(strTable, intIndex);
        if (dctRow == null)
        {
            sendSystemMessageTestingOnly(self, "Not a valid combination of data");
            return SCRIPT_CONTINUE;
        }
        int intMinDifficulty = dctRow.getInt("intMinDifficulty");
        int intMaxDifficulty = dctRow.getInt("intMaxDifficulty");
        String strTemplate = dctRow.getString("strTemplate");
        String strLairType = dctRow.getString("strLairType");
        String strBuildingType = dctRow.getString("strBuildingType");
        obj_id objCreatedTemplate = createObject(strTemplate, getLocation(self));
        if (!(objCreatedTemplate == null))
        {
            if (group.isGrouped(self))
            {
                obj_id objGroup = getGroupObject(self);
                if (objGroup != null)
                {
                    int intGroupSize = getGroupSize(objGroup);
                    setObjVar(objCreatedTemplate, "spawning.intGroupSize", intGroupSize);
                }
            }
            setObjVar(objCreatedTemplate, "spawning.intDifficultyLevel", intLevel);
            String strDifficulty = create.getLairDifficulty(intMinDifficulty, intMaxDifficulty, intLevel);
            setObjVar(objCreatedTemplate, "spawning.lairDifficulty", strDifficulty);
            setObjVar(objCreatedTemplate, "spawning.lairType", strLairType);
            setObjVar(objCreatedTemplate, "spawning.buildingTrackingType", strBuildingType);
            if (!strBuildingType.equals(""))
            {
                setObjVar(objCreatedTemplate, "spawning.buildingType", strBuildingType);
            }
        }
        else 
        {
            debugSpeakMsg(self, "I made a null object");
            debugSpeakMsg(self, "row was " + dctRow.toString());
        }
        return SCRIPT_CONTINUE;
    }
    public int startSpawner(obj_id self, obj_id target, String params, float defaultTime) throws InterruptedException
    {
        obj_id objMasterSpawner = getPlanetByName(getLocation(self).area);
        if (objMasterSpawner == null)
        {
            return SCRIPT_CONTINUE;
        }
        if (!hasScript(objMasterSpawner, "systems.spawning.spawn_master"))
        {
            attachScript(objMasterSpawner, "systems.spawning.spawn_master");
        }
        if (!hasObjVar(objMasterSpawner, "boolSpawnerIsOn"))
        {
            sendSystemMessageTestingOnly(self, "The masterspawner is not setup correctly.  Resetting it to off.");
            setObjVar(objMasterSpawner, "boolSpawnerIsOn", false);
            return SCRIPT_OVERRIDE;
        }
        else 
        {
            if (!getBooleanObjVar(objMasterSpawner, "boolSpawnerIsOn"))
            {
                setObjVar(objMasterSpawner, "boolSpawnerIsOn", true);
                sendSystemMessageTestingOnly(self, "The master spawner is now on!");
                return SCRIPT_OVERRIDE;
            }
            else 
            {
                sendSystemMessageTestingOnly(self, "The master spawner is already on!");
                return SCRIPT_OVERRIDE;
            }
        }
    }
    public int OnLogin(obj_id self) throws InterruptedException
    {
        messageTo(self, "checkStationCells", null, 2, false);
        removeObjVar(self, "spawning.locSpawnLocation");
        return SCRIPT_CONTINUE;
    }
    public int spawn_Trigger(obj_id self, dictionary params) throws InterruptedException
    {
        if (!isIdValid(self))
        {
            return SCRIPT_CONTINUE;
        }
        location locTest = getLocation(self);
        if (utils.checkConfigFlag("ScriptFlags", "newSpawnSystem_" + locTest.area))
        {
            spawning.activateSpawnerHack(self);
            return SCRIPT_CONTINUE;
        }
        location locCurrentLocation = getLocation(self);
        if (locCurrentLocation == null)
        {
            return SCRIPT_CONTINUE;
        }
        if (canSpawn(self, locCurrentLocation, false, null))
        {
            region rgnSpawnRegion = getSpawnRegion(self);
            if (rgnSpawnRegion == null)
            {
                return SCRIPT_CONTINUE;
            }
            String strRegionName = rgnSpawnRegion.getName();
            String strPlanet = rgnSpawnRegion.getPlanetName();
            String strTestPlanet = locCurrentLocation.area;
            if (!strPlanet.equals(strTestPlanet))
            {
                LOG("DESIGNER_FATAL", "For region " + strRegionName + " planet is " + strPlanet);
                LOG("DESIGNER_FATAL", "But planet name for player is " + strTestPlanet);
            }
            int intLevel = locations.normalizeDifficultyForRegion(skill.getGroupLevel(self), locCurrentLocation);
            dictionary dctSpawnParams = new dictionary();
            dctSpawnParams.put("intDifficulty", intLevel);
            dctSpawnParams.put("objPlayer", self);
            if (strRegionName != null)
            {
                dctSpawnParams.put("strRegionName", strRegionName);
            }
            dctSpawnParams.put("strPlanet", strPlanet);
            boolean boolTheatersAllowed = false;
            int intNumRules = getNumRunTimeRules();
            if (intNumRules > 0)
            {
                if (intNumRules < MAXIMUM_SPAWNING_RUN_TIME_RULES)
                {
                    boolTheatersAllowed = true;
                }
            }
            dctSpawnParams.put("boolTheatersAllowed", boolTheatersAllowed);
            doSpawnEvent(dctSpawnParams);
        }
        return SCRIPT_CONTINUE;
    }
    public int OnSpeaking(obj_id self, String strText) throws InterruptedException
    {
        if (!isGod(self))
        {
            return SCRIPT_CONTINUE;
        }
        String[] strCommands = split(strText, ' ');
        if (strCommands[0].equals("makeGroundSpawner"))
        {
            int pid = sui.inputbox(self, self, "Should this spawner be named?", sui.OK_CANCEL, "Test", sui.INPUT_NORMAL, null, "specifyGroundSpawnerName", null);
            if (utils.hasScriptVar(self, "previousName"))
            {
                String defaultText = utils.getStringScriptVar(self, "previousName");
                if (defaultText != null && defaultText.length() > 0)
                {
                    setSUIProperty(pid, sui.INPUTBOX_INPUT, sui.PROP_TEXT, defaultText);
                }
            }
            sui.showSUIPage(pid);
            return SCRIPT_CONTINUE;
        }
        if (strCommands[0].equals("dumpBuildout"))
        {
            String strDataTable;
            if (strCommands.length > 1)
            {
                strDataTable = "datatables/spawning/ground_spawning/buildouts/" + strCommands[1] + ".tab";
            }
            else 
            {
                sendSystemMessageTestingOnly(self, "You must provide a filename. Syntax: dumpBuildout <fileName>");
                return SCRIPT_CONTINUE;
            }
            location locTest = getLocation(self);
            obj_id objCell = locTest.cell;
            boolean boolInBuilding = isIdValid(objCell);
            obj_id objBuilding = null;
            if (boolInBuilding)
            {
                objBuilding = getContainedBy(objCell);
            }
            String[] strHeaderTypes = 
            {
                "s",
                "f",
                "f",
                "f",
                "f",
                "f",
                "f",
                "f",
                "f",
                "f",
                "p",
                "s",
                "s"
            };
            String[] strHeaders = 
            {
                "strTemplate",
                "fltJX",
                "fltJY",
                "fltJZ",
                "fltKX",
                "fltKY",
                "fltKZ",
                "fltPX",
                "fltPY",
                "fltPZ",
                "strObjVars",
                "strScripts",
                "strCellName"
            };
            if (!datatable.createDataTable(strDataTable, strHeaders, strHeaderTypes))
            {
                sendSystemMessageTestingOnly(self, "No datatable made");
                return SCRIPT_CONTINUE;
            }
            obj_id[] objObjects;
            if (boolInBuilding)
            {
                objObjects = spawning.getAllContents(objBuilding);
            }
            else 
            {
                objObjects = getAllObjectsWithObjVar(getLocation(self), 15000, "intSpawnLocation");
            }
            sendSystemMessageTestingOnly(self, "dumping contents of " + objBuilding);
            dictionary dctRow;
            for (obj_id objObject : objObjects) {
                if (hasObjVar(objObject, "intSpawnLocation")) {
                    dctRow = new dictionary();
                    locTest = getLocation(objObject);
                    dctRow.put("strCellName",(boolInBuilding ? space_utils.getCellName(objBuilding, locTest.cell) : ""));
                    transform vctTest = getTransform_o2p(objObject);
                    vector vctJ = vctTest.getLocalFrameJ_p();
                    vector vctK = vctTest.getLocalFrameK_p();
                    vector vctP = vctTest.getPosition_p();
                    dctRow.put("fltJX", vctJ.x);
                    dctRow.put("fltJY", vctJ.y);
                    dctRow.put("fltJZ", vctJ.z);
                    dctRow.put("fltKX", vctK.x);
                    dctRow.put("fltKY", vctK.y);
                    dctRow.put("fltKZ", vctK.z);
                    dctRow.put("fltPX", vctP.x);
                    dctRow.put("fltPY", vctP.y);
                    dctRow.put("fltPZ", vctP.z);
                    dctRow.put("strTemplate", getTemplateName(objObject));
                    String strObjVars = getPackedObjvars(objObject);
                    dctRow.put("strObjVars", strObjVars);
                    String strScripts = utils.getPackedScripts(objObject);
                    dctRow.put("strScripts", strScripts);
                    datatable.serverDataTableAddRow(strDataTable, dctRow);
                }
            }
            return SCRIPT_CONTINUE;
        }
        if (strCommands[0].equals("addSpawnLocation"))
        {
            obj_id objSpawner = createObject("object/tangible/space/content_infrastructure/generic_egg_small.iff", getTransform_o2p(self), getLocation(self).cell);
            setObjVar(objSpawner, "intSpawnLocation", 1);
            setName(objSpawner, "SPAWN LOCATION!");
            sendSystemMessageTestingOnly(self, "Made spawn location");
        }
        if (strCommands[0].equals("createMineField"))
        {
            handleMineFieldCreation();
        }
        if (strCommands[0].equals("spawnTrigger"))
        {
            messageTo(self, "spawn_Trigger", null, 0, false);
        }
        return SCRIPT_CONTINUE;
    }
    public int specifyGroundSpawnerName(obj_id self, dictionary params) throws InterruptedException
    {
        int intButton = sui.getIntButtonPressed(params);
        if (intButton == sui.BP_CANCEL)
        {
            sendSystemMessageTestingOnly(self, "Cancelling spawner creation");
            clearSpawnerScriptVar(self, false);
            return SCRIPT_CONTINUE;
        }
        String strName = sui.getInputBoxText(params);
        if (strName.equals(""))
        {
            sui.inputbox(self, self, "INVALID Name! Should this spawner be named?", sui.OK_CANCEL, "Test", sui.INPUT_NORMAL, null, "specifyGroundSpawnerName", null);
            return SCRIPT_CONTINUE;
        }
        utils.setScriptVar(self, "strName", strName);
        utils.setScriptVar(self, "previousName", strName);
        if (canDuplicateLastSpawner(self))
        {
            String strSpawnerType = utils.getStringScriptVar(self, "strType");
            float fltSpawnerRadius = utils.getFloatScriptVar(self, "fltRadius");
            int intSpawnerCount = utils.getIntScriptVar(self, "intSpawnCount");
            String strSpawnerSpawns = utils.getStringScriptVar(self, "strSpawns");
            float fltSpawnerMinTime = utils.getFloatScriptVar(self, "fltMinSpawnTime");
            float fltSpawnerMaxTime = utils.getFloatScriptVar(self, "fltMaxSpawnTime");
            int intSpawnerBehavior = utils.getIntScriptVar(self, "intDefaultBehavior");
            String[] behaviors = 
            {
                "wander",
                "sentinel",
                "loiter",
                "stop"
            };
            String strSpawnerBehavior = behaviors[intSpawnerBehavior];
            String title = "Test";
            String prompt = "Current Spawn Data \n \\#pcontrast1 Type: \\#. " + strSpawnerType + " \n \\#pcontrast1 Spawn: \\#. " + strSpawnerSpawns + " \n \\#pcontrast1 Default Behavior: \\#. " + strSpawnerBehavior + " \n \\#pcontrast1 Radius: \\#. " + fltSpawnerRadius + " \n \\#pcontrast1 Count: \\#. " + intSpawnerCount + " \n \\#pcontrast1 Respawn (Min - Max): \\#. " + fltSpawnerMinTime + " - " + fltSpawnerMaxTime;
            int pid = sui.createSUIPage(sui.SUI_MSGBOX, self, self, "duplicateLastSpawner");
            setSUIProperty(pid, sui.MSGBOX_TITLE, sui.PROP_TEXT, title);
            setSUIProperty(pid, sui.MSGBOX_PROMPT, sui.PROP_TEXT, prompt);
            sui.msgboxButtonSetup(pid, sui.YES_NO_CANCEL);
            String revertButton = "Change to area";
            if (strSpawnerType.equals("area"))
            {
                revertButton = "Change to patrol";
            }
            setSUIProperty(pid, sui.MSGBOX_BTN_REVERT, sui.PROP_TEXT, revertButton);
            setSUIProperty(pid, sui.MSGBOX_BTN_CANCEL, sui.PROP_TEXT, "Enter new data");
            setSUIProperty(pid, sui.MSGBOX_BTN_OK, sui.PROP_TEXT, "Use this data");
            setSUIProperty(pid, sui.MSGBOX_BTN_REVERT, "OnPress", "RevertWasPressed=1\r\nparent.btnOk.press=t");
            subscribeToSUIProperty(pid, sui.MSGBOX_BTN_REVERT, "RevertWasPressed");
            sui.showSUIPage(pid);
            return SCRIPT_CONTINUE;
        }
        clearSpawnerScriptVar(self, false);
        utils.setScriptVar(self, "strName", strName);
        sui.inputbox(self, self, "What type of spawner? Valid types are : area, patrol", sui.OK_CANCEL, "Test", sui.INPUT_NORMAL, null, "specifyGroundSpawnerType", null);
        return SCRIPT_CONTINUE;
    }
    public int duplicateLastSpawner(obj_id self, dictionary params) throws InterruptedException
    {
        int intButton = sui.getIntButtonPressed(params);
        String revert = params.getString(sui.MSGBOX_BTN_REVERT + ".RevertWasPressed");
        if (intButton == sui.BP_CANCEL)
        {
            String strName = utils.getStringScriptVar(self, "strName");
            clearSpawnerScriptVar(self, false);
            utils.setScriptVar(self, "strName", strName);
            sui.inputbox(self, self, "What type of spawner? Valid types are : area, patrol", sui.OK_CANCEL, "Test", sui.INPUT_NORMAL, null, "specifyGroundSpawnerType", null);
        }
        if (intButton == sui.BP_OK)
        {
            String strSpawnerType = utils.getStringScriptVar(self, "strType");
            if (revert != null && !revert.equals(""))
            {
                if (strSpawnerType.equals("patrol"))
                {
                    strSpawnerType = "area";
                }
                else 
                {
                    strSpawnerType = "patrol";
                }
                utils.setScriptVar(self, "strType", strSpawnerType);
            }
            if (strSpawnerType.equals("patrol"))
            {
                sui.inputbox(self, self, "What is the Patrol Path Type: Cycle or Oscillate", sui.OK_CANCEL, "Test", sui.INPUT_NORMAL, null, "handlePatrolPathType", null);
            }
            else 
            {
                int intSpawnerBehavior = utils.getIntScriptVar(self, "intDefaultBehavior");
                if (intSpawnerBehavior == 3)
                {
                    intSpawnerBehavior = 1;
                    utils.setScriptVar(self, "intDefaultBehavior", intSpawnerBehavior);
                }
                writeSpawner(self);
            }
            return SCRIPT_CONTINUE;
        }
        return SCRIPT_CONTINUE;
    }
    public int specifyGroundSpawnerType(obj_id self, dictionary params) throws InterruptedException
    {
        int intButton = sui.getIntButtonPressed(params);
        if (intButton == sui.BP_CANCEL)
        {
            sendSystemMessageTestingOnly(self, "Cancelling spawner creation");
            clearSpawnerScriptVar(self, true);
            return SCRIPT_CONTINUE;
        }
        String strType = sui.getInputBoxText(params);
        if (!isValidSpawnerType(strType))
        {
            sui.inputbox(self, self, "INVALID SPAWN TYPE " + strType + ". What type of spawner? Valid types are : area, patrol, location ", sui.OK_CANCEL, "Test", sui.INPUT_NORMAL, null, "specifyGroundSpawnerType", null);
            return SCRIPT_CONTINUE;
        }
        else 
        {
            utils.setScriptVar(self, "strType", strType);
            sui.inputbox(self, self, "What type of spawns should this spawner use?", sui.OK_CANCEL, "Test", sui.INPUT_NORMAL, null, "specifyGroundSpawnerSpawns", null);
        }
        return SCRIPT_CONTINUE;
    }
    public int specifyGroundSpawnerSpawns(obj_id self, dictionary params) throws InterruptedException
    {
        int intButton = sui.getIntButtonPressed(params);
        if (intButton == sui.BP_CANCEL)
        {
            sendSystemMessageTestingOnly(self, "Cancelling spawner creation");
            clearSpawnerScriptVar(self, true);
            return SCRIPT_CONTINUE;
        }
        String strSpawns = sui.getInputBoxText(params);
        if (strSpawns.equals(""))
        {
            sui.inputbox(self, self, "INVALID SPAWNS! What type of spawns should this spawner use?", sui.OK_CANCEL, "Test", sui.INPUT_NORMAL, null, "specifyGroundSpawnerSpawns", null);
            return SCRIPT_CONTINUE;
        }
        else 
        {
            String strFileName = "datatables/spawning/ground_spawning/types/" + strSpawns + ".iff";
            String creatureTable = "datatables/mob/creatures.iff";
            dictionary creatureDict = null;
            int stringCheck = strSpawns.indexOf("/");
            if (stringCheck < 0)
            {
                creatureDict = dataTableGetRow(creatureTable, strSpawns);
            }
            if (!dataTableOpen(strFileName) && creatureDict == null)
            {
                sui.inputbox(self, self, "Unable to open type " + strFileName + " or find " + strSpawns + " in creatures.tab! What type of spawns should this spawner use?", sui.OK_CANCEL, "Test", sui.INPUT_NORMAL, null, "specifyGroundSpawnerSpawns", null);
                return SCRIPT_CONTINUE;
            }
        }
        utils.setScriptVar(self, "strSpawns", strSpawns);
        if ((utils.getStringScriptVar(self, "strType")).equals("patrol"))
        {
            sui.inputbox(self, self, "SCALE: Meters \nWhat is this spawner's spawn radius?", sui.OK_CANCEL, "Test", sui.INPUT_NORMAL, null, "specifyGroundSpawnerRadius", null);
            utils.setScriptVar(self, "intDefaultBehavior", isValidBehavior("stop"));
            utils.setScriptVar(self, "intGoodLocationSpawner", 0);
        }
        else 
        {
            sui.inputbox(self, self, "What default behavior do you wish your creatures to have? Valid Behaviors are : wander, loiter, stop, sentinel", sui.OK_CANCEL, "Test", sui.INPUT_NORMAL, null, "specifyGroundSpawnerDefaultBehavior", null);
        }
        return SCRIPT_CONTINUE;
    }
    public int specifyGroundSpawnerDefaultBehavior(obj_id self, dictionary params) throws InterruptedException
    {
        int intButton = sui.getIntButtonPressed(params);
        if (intButton == sui.BP_CANCEL)
        {
            sendSystemMessageTestingOnly(self, "Cancelling spawner creation");
            clearSpawnerScriptVar(self, true);
            return SCRIPT_CONTINUE;
        }
        String strBehavior = sui.getInputBoxText(params);
        int intBehavior = isValidBehavior(strBehavior);
        if ((strBehavior.equals("")) || (intBehavior < 0))
        {
            sui.inputbox(self, self, "INVALID BEHAVIOR : What default behavior do you wish your creatures to have? Valid Behaviors are : wander, loiter, stop, sentinel", sui.OK_CANCEL, "Test", sui.INPUT_NORMAL, null, "specifyGroundSpawnerDefaultBehavior", null);
            return SCRIPT_CONTINUE;
        }
        utils.setScriptVar(self, "intDefaultBehavior", intBehavior);
        sui.inputbox(self, self, "Do you want to use getgoodlocation for this spawner? Valid answers are : yes, no", sui.OK_CANCEL, "Test", sui.INPUT_NORMAL, null, "specifyGroundSpawnerGoodLocationType", null);
        return SCRIPT_CONTINUE;
    }
    public int isValidBehavior(String strBehavior) throws InterruptedException
    {
        final String[] BEHAVIORS = 
        {
            "wander",
            "sentinel",
            "loiter",
            "stop"
        };
        int intIndex = utils.getElementPositionInArray(BEHAVIORS, strBehavior);
        sendSystemMessageTestingOnly(getSelf(), "For behavior " + strBehavior + " intIndex is " + intIndex);
        return intIndex;
    }
    public int specifyGroundSpawnerGoodLocationType(obj_id self, dictionary params) throws InterruptedException
    {
        int intButton = sui.getIntButtonPressed(params);
        if (intButton == sui.BP_CANCEL)
        {
            sendSystemMessageTestingOnly(self, "Cancelling spawner creation");
            clearSpawnerScriptVar(self, true);
            return SCRIPT_CONTINUE;
        }
        String strGoodLocationType = sui.getInputBoxText(params);
        if (!strGoodLocationType.equals("yes") && (!strGoodLocationType.equals("no")))
        {
            sui.inputbox(self, self, "INVALID ANSWER : Do you want to use getgoodlocation for this spawner? Valid answers are : yes, no", sui.OK_CANCEL, "Test", sui.INPUT_NORMAL, null, "specifyGroundSpawnerGoodLocationType", null);
            return SCRIPT_CONTINUE;
        }
        if (strGoodLocationType.equals("yes"))
        {
            utils.setScriptVar(self, "intGoodLocationSpawner", 1);
        }
        else 
        {
            utils.setScriptVar(self, "intGoodLocationSpawner", 0);
        }
        String strType = utils.getStringScriptVar(self, "strType");
        if (strType.equals("location"))
        {
            sui.inputbox(self, self, "What spawner location file do you want to use for this spawner?", sui.OK_CANCEL, "Test", sui.INPUT_NORMAL, null, "specifyGroundSpawnerLocations", null);
        }
        else 
        {
            sui.inputbox(self, self, "SCALE: Meters \nWhat is this spawner's spawn radius?", sui.OK_CANCEL, "Test", sui.INPUT_NORMAL, null, "specifyGroundSpawnerRadius", null);
        }
        return SCRIPT_CONTINUE;
    }
    public int specifyGroundSpawnerLocations(obj_id self, dictionary params) throws InterruptedException
    {
        int intButton = sui.getIntButtonPressed(params);
        if (intButton == sui.BP_CANCEL)
        {
            sendSystemMessageTestingOnly(self, "Cancelling spawner creation");
            clearSpawnerScriptVar(self, true);
            return SCRIPT_CONTINUE;
        }
        String strSpawnLocations = sui.getInputBoxText(params);
        if (strSpawnLocations.equals(""))
        {
            sui.inputbox(self, self, "INVALID LOCATIONS FILE. What spawner location file do you want to use for this spawner?", sui.OK_CANCEL, "Test", sui.INPUT_NORMAL, null, "specifyGroundSpawnerLocations", null);
            return SCRIPT_CONTINUE;
        }
        else 
        {
            String strFileName = "datatables/spawning/ground_spawning/buildouts/" + strSpawnLocations + ".iff";
            if (!dataTableOpen(strFileName))
            {
                sui.inputbox(self, self, "UNABLE TO OPEN LOCATION FINE " + strFileName + ". What spawner location file do you want to use for this spawner?", sui.OK_CANCEL, "Test", sui.INPUT_NORMAL, null, "specifyGroundSpawnerLocations", null);
                return SCRIPT_CONTINUE;
            }
        }
        utils.setScriptVar(self, "strSpawnLocations", strSpawnLocations);
        sui.inputbox(self, self, "What is the spawn count for this spawner?", sui.OK_CANCEL, "Test", sui.INPUT_NORMAL, null, "specifyGroundSpawnerSpawnCount", null);
        return SCRIPT_CONTINUE;
    }
    public int specifyGroundSpawnerRadius(obj_id self, dictionary params) throws InterruptedException
    {
        int intButton = sui.getIntButtonPressed(params);
        if (intButton == sui.BP_CANCEL)
        {
            sendSystemMessageTestingOnly(self, "Cancelling spawner creation");
            clearSpawnerScriptVar(self, true);
            return SCRIPT_CONTINUE;
        }
        String strRadius = sui.getInputBoxText(params);
        float fltRadius = utils.stringToFloat(strRadius);
        if (fltRadius == Float.NEGATIVE_INFINITY)
        {
            sui.inputbox(self, self, "INVALID NUMBER, PLEASE USE A NUMBER\n SCALE: Meters \nWhat is this spawner's spawn radius?", sui.OK_CANCEL, "Test", sui.INPUT_NORMAL, null, "specifyGroundSpawnerMinSpawnDistance", null);
            return SCRIPT_CONTINUE;
        }
        utils.setScriptVar(self, "fltRadius", fltRadius);
        sui.inputbox(self, self, "What is the spawn count for this spawner?", sui.OK_CANCEL, "Test", sui.INPUT_NORMAL, null, "specifyGroundSpawnerSpawnCount", null);
        return SCRIPT_CONTINUE;
    }
    public int specifyGroundSpawnerSpawnCount(obj_id self, dictionary params) throws InterruptedException
    {
        int intButton = sui.getIntButtonPressed(params);
        if (intButton == sui.BP_CANCEL)
        {
            sendSystemMessageTestingOnly(self, "Cancelling spawner creation");
            clearSpawnerScriptVar(self, true);
            return SCRIPT_CONTINUE;
        }
        String strSpawnCount = sui.getInputBoxText(params);
        int intSpawnCount = utils.stringToInt(strSpawnCount);
        if (intSpawnCount == -1)
        {
            sui.inputbox(self, self, "INVALID NUMBER, PLEASE USE A NUMBER\n SCALE: Mobs \nWhat is this spawner's maximum population ?", sui.OK_CANCEL, "Test", sui.INPUT_NORMAL, null, "specifyGroundSpawnerSpawnCount", null);
            return SCRIPT_CONTINUE;
        }
        utils.setScriptVar(self, "intSpawnCount", intSpawnCount);
        sui.inputbox(self, self, "SCALE: Seconds \nWhat is this spawner's minimum spawn time?", sui.OK_CANCEL, "Test", sui.INPUT_NORMAL, null, "specifyGroundSpawnerMinSpawnTime", null);
        return SCRIPT_CONTINUE;
    }
    public int specifyGroundSpawnerMinSpawnTime(obj_id self, dictionary params) throws InterruptedException
    {
        int intButton = sui.getIntButtonPressed(params);
        if (intButton == sui.BP_CANCEL)
        {
            sendSystemMessageTestingOnly(self, "Cancelling spawner creation");
            clearSpawnerScriptVar(self, true);
            return SCRIPT_CONTINUE;
        }
        String strMinSpawnTime = sui.getInputBoxText(params);
        float fltMinSpawnTime = utils.stringToFloat(strMinSpawnTime);
        if (fltMinSpawnTime == Float.NEGATIVE_INFINITY)
        {
            sui.inputbox(self, self, "INVALID NUMBER, PLEASE USE A NUMBER\n SCALE: Seconds \nWhat is this spawner's minimum spawn time?", sui.OK_CANCEL, "Test", sui.INPUT_NORMAL, null, "specifyGroundSpawnerMinSpawnTime", null);
            return SCRIPT_CONTINUE;
        }
        utils.setScriptVar(self, "fltMinSpawnTime", fltMinSpawnTime);
        sui.inputbox(self, self, "Min Spawn Time :" + fltMinSpawnTime + "\n SCALE: Seconds \nWhat is this spawner's maximum spawn recycle time?", sui.OK_CANCEL, "Test", sui.INPUT_NORMAL, null, "specifyGroundSpawnerMaxSpawnTime", null);
        return SCRIPT_CONTINUE;
    }
    public int specifyGroundSpawnerMaxSpawnTime(obj_id self, dictionary params) throws InterruptedException
    {
        int intButton = sui.getIntButtonPressed(params);
        if (intButton == sui.BP_CANCEL)
        {
            sendSystemMessageTestingOnly(self, "Cancelling spawner creation");
            clearSpawnerScriptVar(self, true);
            return SCRIPT_CONTINUE;
        }
        float fltMinSpawnTime = utils.getFloatScriptVar(self, "fltMinSpawnTime");
        String strMaxSpawnTime = sui.getInputBoxText(params);
        float fltMaxSpawnTime = utils.stringToFloat(strMaxSpawnTime);
        if (fltMaxSpawnTime == Float.NEGATIVE_INFINITY)
        {
            sui.inputbox(self, self, "INVALID NUMBER, PLEASE USE A NUMBER\n SCALE: Seconds \nWhat is this spawner's maximum spawn time?", sui.OK_CANCEL, "Test", sui.INPUT_NORMAL, null, "specifyGroundSpawnerMaxSpawnTime", null);
            return SCRIPT_CONTINUE;
        }
        if (fltMaxSpawnTime <= fltMinSpawnTime)
        {
            sui.inputbox(self, self, "MAXSPAWN TIME MUST BE GREATER THAN MIN SPAWN TIME, MIN SPAWN TIME IS:" + fltMinSpawnTime + "\n SCALE: Seconds \nWhat is this spawner's maximum spawn time?", sui.OK_CANCEL, "Test", sui.INPUT_NORMAL, null, "specifyGroundSpawnerMaxSpawnTime", null);
            return SCRIPT_CONTINUE;
        }
        utils.setScriptVar(self, "fltMaxSpawnTime", fltMaxSpawnTime);
        if ((utils.getStringScriptVar(self, "strType")).equals("patrol"))
        {
            sui.inputbox(self, self, "What is the Patrol Path Type: Cycle or Oscillate", sui.OK_CANCEL, "Test", sui.INPUT_NORMAL, null, "handlePatrolPathType", null);
        }
        else 
        {
            writeSpawner(self);
        }
        return SCRIPT_CONTINUE;
    }
    public int handlePatrolPathType(obj_id self, dictionary params) throws InterruptedException
    {
        int intButton = sui.getIntButtonPressed(params);
        if (intButton == sui.BP_CANCEL)
        {
            sendSystemMessageTestingOnly(self, "Cancelling spawner creation");
            clearSpawnerScriptVar(self, true);
            return SCRIPT_CONTINUE;
        }
        String patrolType = sui.getInputBoxText(params);
        if (!(patrolType.equals("cycle") || patrolType.equals("oscillate")))
        {
            sui.inputbox(self, self, "INVALID ENTRY: What is the Patrol Path Type: cycle or oscillate", sui.OK_CANCEL, "Test", sui.INPUT_NORMAL, null, "handlePatrolPathType", null);
            return SCRIPT_CONTINUE;
        }
        utils.setScriptVar(self, "strPatrolPathType", patrolType);
        Vector patrolPoints = new Vector();
        patrolPoints.setSize(0);
        patrolPoints = utils.addElement(patrolPoints, createObject("object/tangible/ground_spawning/patrol_spawner.iff", getLocation(self)));
        utils.setScriptVar(self, "objPatrolPoints", patrolPoints);
        String title = "Place Patrol Waypoint";
        String prompt = "Choose WAYPOINT to set a new waypoint or END to write path to the spawner";
        int pid = sui.msgbox(self, self, prompt, sui.YES_NO, title, "handlePlaceNewPatrolPoint");
        setSUIProperty(pid, sui.LISTBOX_BTN_CANCEL, sui.PROP_TEXT, "END POINT");
        setSUIProperty(pid, sui.LISTBOX_BTN_OK, sui.PROP_TEXT, "WAYPOINT");
        showSUIPage(pid);
        return SCRIPT_CONTINUE;
    }
    public int handlePlaceNewPatrolPoint(obj_id self, dictionary params) throws InterruptedException
    {
        int intButton = sui.getIntButtonPressed(params);
        Vector waypointArray = utils.getResizeableObjIdArrayScriptVar(self, "objPatrolPoints");
        obj_id patrolPoint;
        for (int q = 0; q < waypointArray.size(); q++)
        {
            doLogging("handlePlaceNewPatrolPoint", "waypointArray.length = " + q);
        }
        if (intButton == sui.BP_CANCEL)
        {
            if (!utils.hasScriptVar(self, "objPatrolPoints"))
            {
                sendSystemMessageTestingOnly(self, "Attempted to create patrol route with no waypoints");
                sendSystemMessageTestingOnly(self, "Cancelling spawner creation.");
                clearSpawnerScriptVar(self, true);
                return SCRIPT_CONTINUE;
            }
            patrolPoint = createObject("object/tangible/ground_spawning/patrol_waypoint.iff", getLocation(self));
            waypointArray = utils.addElement(waypointArray, patrolPoint);
            utils.setScriptVar(self, "objPatrolPoints", waypointArray);
            if (waypointArray == null || waypointArray.size() < 2)
            {
                sendSystemMessageTestingOnly(self, "Location Array was NULL or only contained the base waypoint");
                clearSpawnerScriptVar(self, true);
                return SCRIPT_CONTINUE;
            }
            writeSpawner(self);
        }
        if (intButton == sui.BP_OK)
        {
            doLogging("handlePlaceNewPatrolPoint", "WAYPOINT button pressed");
            patrolPoint = createObject("object/tangible/ground_spawning/patrol_waypoint.iff", getLocation(self));
            waypointArray = utils.addElement(waypointArray, patrolPoint);
            utils.setScriptVar(self, "objPatrolPoints", waypointArray);
            String title = "Place Patrol Waypoint";
            String prompt = "Choose WAYPOINT to set a new waypoint or END to write path to the spawner";
            int pid = sui.msgbox(self, self, prompt, sui.YES_NO, title, "handlePlaceNewPatrolPoint");
            setSUIProperty(pid, sui.LISTBOX_BTN_CANCEL, sui.PROP_TEXT, "END POINT");
            setSUIProperty(pid, sui.LISTBOX_BTN_OK, sui.PROP_TEXT, "WAYPOINT");
            showSUIPage(pid);
        }
        return SCRIPT_CONTINUE;
    }
    public boolean canDuplicateLastSpawner(obj_id self) throws InterruptedException
    {
        if (!utils.hasScriptVar(self, "strType"))
        {
            return false;
        }
        String strSpawnerType = utils.getStringScriptVar(self, "strType");
        if (strSpawnerType.equals("location"))
        {
            return false;
        }
        if (!utils.hasScriptVar(self, "fltRadius"))
        {
            return false;
        }
        if (!utils.hasScriptVar(self, "strName"))
        {
            return false;
        }
        if (!utils.hasScriptVar(self, "intSpawnCount"))
        {
            return false;
        }
        if (!utils.hasScriptVar(self, "strSpawns"))
        {
            return false;
        }
        if (!utils.hasScriptVar(self, "fltMaxSpawnTime"))
        {
            return false;
        }
        if (!utils.hasScriptVar(self, "fltMinSpawnTime"))
        {
            return false;
        }
        if (!utils.hasScriptVar(self, "intDefaultBehavior"))
        {
            return false;
        }
        if (!utils.hasScriptVar(self, "intGoodLocationSpawner"))
        {
            return false;
        }
        return true;
    }
    public void clearSpawnerScriptVar(obj_id self, boolean boolDestroySpawners) throws InterruptedException
    {
        utils.removeScriptVar(self, "strType");
        utils.removeScriptVar(self, "fltRadius");
        utils.removeScriptVar(self, "strName");
        utils.removeScriptVar(self, "intSpawnCount");
        utils.removeScriptVar(self, "strSpawns");
        utils.removeScriptVar(self, "fltMaxSpawnTime");
        utils.removeScriptVar(self, "fltMinSpawnTime");
        utils.removeScriptVar(self, "strSpawnLocations");
        utils.removeScriptVar(self, "intDefaultBehavior");
        utils.removeScriptVar(self, "intGoodLocationSpawner");
        utils.removeScriptVar(self, "strPatrolPathType");
        if (boolDestroySpawners)
        {
            Vector objPatrolPoints = utils.getResizeableObjIdArrayScriptVar(self, "objPatrolPoints");
            if (objPatrolPoints != null)
            {
                for (Object objPatrolPoint : objPatrolPoints) {
                    destroyObject(((obj_id) objPatrolPoint));
                }
            }
        }
        utils.removeScriptVar(self, "objPatrolPoints");
    }
    public void writeSpawner(obj_id self) throws InterruptedException
    {
        sendSystemMessageTestingOnly(self, "Writing spawner");
        String strSpawnerType = utils.getStringScriptVar(self, "strType");
        Vector patrolPoints = new Vector();
        patrolPoints.setSize(0);
        obj_id objSpawner;
        if (strSpawnerType.equals("area") || strSpawnerType.equals("location"))
        {
            transform trTest = getTransform_o2p(self);
            location locTest = getLocation(self);
            objSpawner = createObject("object/tangible/ground_spawning/area_spawner.iff", trTest, locTest.cell);
            persistObject(objSpawner);
        }
        else 
        {
            patrolPoints = utils.getResizeableObjIdArrayScriptVar(self, "objPatrolPoints");
            String strPatrolPathType = utils.getStringScriptVar(self, "strPatrolPathType");
            objSpawner = ((obj_id)patrolPoints.get(0));
            persistObject(objSpawner);
            setObjVar(objSpawner, "patrolPathType", strPatrolPathType);
        }
        setObjVar(objSpawner, "strSpawnerType", strSpawnerType);
        setObjVar(objSpawner, "intSpawnSystem", 1);
        String strSpawnerName = utils.getStringScriptVar(self, "strName");
        setObjVar(objSpawner, "strName", strSpawnerName);
        setName(objSpawner, strSpawnerName);
        int intSpawnCount = utils.getIntScriptVar(self, "intSpawnCount");
        setObjVar(objSpawner, "intSpawnCount", intSpawnCount);
        float fltMaxSpawnTime = utils.getFloatScriptVar(self, "fltMaxSpawnTime");
        setObjVar(objSpawner, "fltMaxSpawnTime", fltMaxSpawnTime);
        float fltMinSpawnTime = utils.getFloatScriptVar(self, "fltMinSpawnTime");
        setObjVar(objSpawner, "fltMinSpawnTime", fltMinSpawnTime);
        String strSpawns = utils.getStringScriptVar(self, "strSpawns");
        setObjVar(objSpawner, "strSpawns", strSpawns);
        if (strSpawnerType.equals("patrol"))
        {
            String[] patrolPointNames = new String[patrolPoints.size() - 1];
            patrolPointNames[0] = strSpawnerName;
            int k = 0;
            for (int i = 1; i < patrolPoints.size(); i++)
            {
                setName(((obj_id)patrolPoints.get(i)), strSpawnerName + "_" + objSpawner + "_" + i);
                patrolPointNames[k] = strSpawnerName + "_" + objSpawner + "_" + i;
                setObjVar(((obj_id)patrolPoints.get(i)), "pointName", strSpawnerName + "_" + objSpawner + "_" + i);
                persistObject(((obj_id)patrolPoints.get(i)));
                attachScript(((obj_id)patrolPoints.get(i)), "systems.spawning.patrol_point_setup");
                k++;
            }
            setObjVar(objSpawner, "strPatrolPointNames", patrolPointNames);
            float fltRadius = utils.getFloatScriptVar(self, "fltRadius");
            setObjVar(objSpawner, "fltRadius", fltRadius);
            attachScript(objSpawner, "systems.spawning.spawner_patrol");
        }
        if (strSpawnerType.equals("area"))
        {
            float fltRadius = utils.getFloatScriptVar(self, "fltRadius");
            setObjVar(objSpawner, "fltRadius", fltRadius);
            attachScript(objSpawner, "systems.spawning.spawner_area");
        }
        else if (strSpawnerType.equals("location"))
        {
            String strSpawnLocations = utils.getStringScriptVar(self, "strSpawnLocations");
            setObjVar(objSpawner, "strSpawnLocations", strSpawnLocations);
            attachScript(objSpawner, "systems.spawning.spawner_location");
        }
        int intDefaultBehavior = utils.getIntScriptVar(self, "intDefaultBehavior");
        setObjVar(objSpawner, "intDefaultBehavior", intDefaultBehavior);
        int intGoodLocationSpawner = utils.getIntScriptVar(self, "intGoodLocationSpawner");
        setObjVar(objSpawner, "intGoodLocationSpawner", intGoodLocationSpawner);
    }
    public boolean isValidSpawnerType(String strText) throws InterruptedException
    {
        return strText.equals("area") || strText.equals("patrol");
    }
    public void handleMineFieldCreation() throws InterruptedException
    {
        String datatable = "datatables/combat/npc_landmines.iff";
        String[] validMineTypes = dataTableGetStringColumn(datatable, "mineType");
        float[] detonateRange = dataTableGetFloatColumn(datatable, "detonateRange");
        float[] blastRadius = dataTableGetFloatColumn(datatable, "blastRadius");
        int[] minDamage = dataTableGetIntColumn(datatable, "minDamage");
        int[] maxDamage = dataTableGetIntColumn(datatable, "maxDamage");
        String[] damageType = dataTableGetStringColumn(datatable, "damageType");
        obj_id user = getSelf();
        String[] packedMineStrings = new String[validMineTypes.length];
        for (int i = 0; i < validMineTypes.length; i++)
        {
            packedMineStrings[i] = validMineTypes[i] + " DR[" + detonateRange[i] + "] BR[" + blastRadius[i] + "] MinD[" + minDamage[i] + "] MaxD[" + maxDamage[i] + "] DT [" + damageType[i] + "]";
        }
        if (validMineTypes.length > 0)
        {
            int pid = sui.listbox(user, user, "Select Mine Type", packedMineStrings, "handleSelectMineType");
            if (pid > -1)
            {
                showSUIPage(pid);
            }
        }
    }
    public int handleSelectMineType(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id player = sui.getPlayerId(params);
        int idx = sui.getListboxSelectedRow(params);
        if (idx < 0)
        {
            return SCRIPT_CONTINUE;
        }
        utils.setScriptVar(player, "mineCreation.mineType", idx);
        sui.inputbox(player, player, "What is the Radius of this field", sui.OK_CANCEL, "Test", sui.INPUT_NORMAL, null, "handleSetFieldRadius", null);
        return SCRIPT_CONTINUE;
    }
    public int handleSetFieldRadius(obj_id self, dictionary params) throws InterruptedException
    {
        int button = sui.getIntButtonPressed(params);
        if (button == sui.BP_CANCEL)
        {
            sendSystemMessageTestingOnly(self, "Cancelling mine field creation");
            clearMineScriptVar(self);
            return SCRIPT_CONTINUE;
        }
        String stringFieldRadius = sui.getInputBoxText(params);
        float floatFieldRadius = utils.stringToFloat(stringFieldRadius);
        utils.setScriptVar(self, "mineCreation.fieldRadius", floatFieldRadius);
        sui.inputbox(self, self, "What is the Mine Count of this field", sui.OK_CANCEL, "Test", sui.INPUT_NORMAL, null, "handleSetMineCount", null);
        return SCRIPT_CONTINUE;
    }
    public int handleSetMineCount(obj_id self, dictionary params) throws InterruptedException
    {
        int button = sui.getIntButtonPressed(params);
        if (button == sui.BP_CANCEL)
        {
            sendSystemMessageTestingOnly(self, "Cancelling mine field creation");
            clearMineScriptVar(self);
            return SCRIPT_CONTINUE;
        }
        String stringMineCount = sui.getInputBoxText(params);
        int intMineCount = utils.stringToInt(stringMineCount);
        utils.setScriptVar(self, "mineCreation.mineCount", intMineCount);
        sui.inputbox(self, self, "What is the respawn time of this field", sui.OK_CANCEL, "Test", sui.INPUT_NORMAL, null, "handleSetMineRespawn", null);
        return SCRIPT_CONTINUE;
    }
    public int handleSetMineRespawn(obj_id self, dictionary params) throws InterruptedException
    {
        int button = sui.getIntButtonPressed(params);
        if (button == sui.BP_CANCEL)
        {
            sendSystemMessageTestingOnly(self, "Cancelling mine field creation");
            clearMineScriptVar(self);
            return SCRIPT_CONTINUE;
        }
        String stringMineRespawn = sui.getInputBoxText(params);
        int intMineRespawn = utils.stringToInt(stringMineRespawn);
        utils.setScriptVar(self, "mineCreation.mineRespawn", intMineRespawn);
        doLogging("handleMineRespawn", "Call generateMineField");
        generateMineField();
        return SCRIPT_CONTINUE;
    }
    public void generateMineField() throws InterruptedException
    {
        obj_id player = getSelf();
        transform tranx = getTransform_o2p(player);
        location fieldLoc = getLocation(player);
        obj_id mineFieldController = createObject("object/tangible/ground_spawning/minefield_spawner.iff", tranx, fieldLoc.cell);
        doLogging("generateMineField", "mineFieldController isIdValid? " + isIdValid(mineFieldController));
        persistObject(mineFieldController);
        int intMineType = utils.getIntScriptVar(player, "mineCreation.mineType");
        String stringMineType = dataTableGetString("datatables/combat/npc_landmines.iff", intMineType, "mineType");
        setObjVar(mineFieldController, "mineField.mineType", stringMineType);
        float fieldRadius = utils.getFloatScriptVar(player, "mineCreation.fieldRadius");
        setObjVar(mineFieldController, "mineField.fieldRadius", fieldRadius);
        int mineCount = utils.getIntScriptVar(player, "mineCreation.mineCount");
        setObjVar(mineFieldController, "mineField.mineCount", mineCount);
        int mineRespawn = utils.getIntScriptVar(player, "mineCreation.mineRespawn");
        setObjVar(mineFieldController, "mineField.mineRespawn", mineRespawn);
        attachScript(mineFieldController, "systems.combat.combat_mine_spawner");
        clearMineScriptVar(player);
    }
    public void clearMineScriptVar(obj_id player) throws InterruptedException
    {
        utils.removeScriptVar(player, "mineCreation");
    }
    public void doLogging(String section, String message) throws InterruptedException
    {
    }
    public int checkStationCells(obj_id self, dictionary params) throws InterruptedException
    {
        if (isInWorldCell(self))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id cell = getContainedBy(self);
        if (!isIdValid(cell) || !exists(cell))
        {
            return SCRIPT_CONTINUE;
        }
        if (utils.hasScriptVar(cell, craftinglib.SCRIPTVAR_CELL_STATIONS))
        {
            obj_id[] stations = utils.getObjIdArrayScriptVar(cell, craftinglib.SCRIPTVAR_CELL_STATIONS);
            if (stations == null || stations.length <= 0)
            {
                return SCRIPT_CONTINUE;
            }
            String buffName;
            for (obj_id station : stations) {
                if (!isIdValid(station)) {
                    continue;
                }
                int craftingType = getIntObjVar(station, craftinglib.OBJVAR_CRAFTING_TYPE);
                buffName = craftinglib.getCraftingStationBuffName(craftingType);
                if (buffName == null || buffName.equals("")) {
                    continue;
                }
                buff.applyBuff(self, station, buffName);
            }
        }
        return SCRIPT_CONTINUE;
    }
}
