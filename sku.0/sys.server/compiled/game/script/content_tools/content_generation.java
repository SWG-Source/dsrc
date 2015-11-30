package script.content_tools;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.space_create;
import script.library.npe;
import script.library.sequencer;
import script.library.create;
import script.library.locations;
import script.library.space_transition;
import script.library.ship_ai;
import script.library.space_battlefield;
import script.library.space_crafting;
import script.library.player_structure;
import script.library.space_content;
import script.library.objvar_mangle;
import script.library.space_quest;
import script.library.sui;
import script.library.datatable;
import script.library.utils;
import script.library.hue;
import script.library.space_utils;

public class content_generation extends script.base_script
{
    public content_generation()
    {
    }
    public static final float AUTOSAVE_DELAY = 300;
    public int OnAttach(obj_id self) throws InterruptedException
    {
        sendSystemMessageTestingOnly(self, "Welcome to the content maker script. Please consult Development before dorking around with this script.  NOTE - to test pilot you need to attach the space content_generation script, not this one");
        return SCRIPT_CONTINUE;
    }
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        dictionary dctParams = new dictionary();
        dctParams.put("strSpam", "If you are doing worldbuilding, it is recommended that you turn on the autosave feature. It will automatically dump your zone every 5 minutes into a datatables/space_zones/backups folder with the name formate <area>_timestamp. If you wish to turn this on, say 'autoSave on'. To turn it off, say 'autoSave off'. If you want any additional features, talk to Dan.");
        messageTo(self, "delayedMessage", dctParams, 60, false);
        return SCRIPT_CONTINUE;
    }
    public int delayedMessage(obj_id self, dictionary params) throws InterruptedException
    {
        String strSpam = params.getString("strSpam");
        sendSystemMessageTestingOnly(self, strSpam);
        return SCRIPT_CONTINUE;
    }
    public int autoSave(obj_id self, dictionary params) throws InterruptedException
    {
        if (hasObjVar(self, "intAutoSaveOff"))
        {
            messageTo(self, "autoSave", null, AUTOSAVE_DELAY, false);
            return SCRIPT_CONTINUE;
        }
        location locTest = getLocation(self);
        String strArea = locTest.area;
        String strDataTable = "";
        sendSystemMessageTestingOnly(self, "AutoSaving zone to " + strDataTable);
        messageTo(self, "autoSave", null, AUTOSAVE_DELAY, false);
        return SCRIPT_CONTINUE;
    }
    public int OnPreloadComplete(obj_id self) throws InterruptedException
    {
        sendSystemMessageTestingOnly(self, "2");
        return SCRIPT_CONTINUE;
    }
    public int OnSpeaking(obj_id self, String strText) throws InterruptedException
    {
        String[] strCommands = split(strText, ' ');
        if (strCommands[0].equals("preload"))
        {
            requestPreloadCompleteTrigger(self);
            sendSystemMessageTestingOnly(self, "1");
        }
        if (strCommands[0].equals("preloadOther"))
        {
            requestPreloadCompleteTrigger(getLookAtTarget(self));
            sendSystemMessageTestingOnly(self, "1");
        }
        if (strCommands[0].equals("createAt"))
        {
            if (strCommands.length > 0)
            {
                obj_id objTest = space_transition.getContainingShip(self);
                obj_id objShip = null;
                if (isIdValid(objTest))
                {
                    objShip = space_create.createShip(strCommands[1], getTransform_o2p(objTest));
                }
                else 
                {
                    objShip = space_create.createShip(strCommands[1], getTransform_o2p(self));
                }
                if (objShip == null)
                {
                    sendSystemMessageTestingOnly(self, "You passed in a bad shipType. Type is " + strCommands[1]);
                    return SCRIPT_CONTINUE;
                }
                else 
                {
                    sendSystemMessageTestingOnly(self, "Made ship of type " + strCommands[1] + " object id is: " + objShip);
                    debugConsoleMsg(self, "Made ship of type " + strCommands[1] + " object id is: " + objShip);
                }
            }
            else 
            {
                sendSystemMessageTestingOnly(self, "You need to pass in a ship type for me to spawn.");
                return SCRIPT_CONTINUE;
            }
        }
        if (strCommands[0].equals("createNear"))
        {
            if (strCommands.length > 0)
            {
                transform whereAt = getTransform_o2p(space_transition.getContainingShip(self));
                transform nearby = whereAt.move_l((vector.unitY.multiply(100.0f)).add(vector.unitX.multiply(100.0f)));
                obj_id objShip = space_create.createShip(strCommands[1], nearby);
                if (objShip == null)
                {
                    sendSystemMessageTestingOnly(self, "You passed in a bad shipType. Type is " + strCommands[1]);
                    return SCRIPT_CONTINUE;
                }
                else 
                {
                    sendSystemMessageTestingOnly(self, "Made ship of type " + strCommands[1] + " object id is: " + objShip);
                    debugConsoleMsg(self, "Made ship of type " + strCommands[1] + " object id is: " + objShip);
                }
            }
            else 
            {
                sendSystemMessageTestingOnly(self, "You need to pass in a ship type for me to spawn.");
                return SCRIPT_CONTINUE;
            }
        }
        if (strCommands[0].equals("cleanUpBuildout"))
        {
            obj_id[] objContents = getShipContents(getTopMostContainer(self));
            for (int intI = 0; intI < objContents.length; intI++)
            {
                destroyObject(objContents[intI]);
            }
            sendSystemMessageTestingOnly(self, "Cleaned Up");
        }
        if (strCommands[0].equals("recreateBuildout"))
        {
        }
        if (strCommands[0].equals("setFlightModel"))
        {
            if (strCommands.length < 2)
            {
                sendSystemMessageTestingOnly(self, "Syntax is setFlightModel <shipType>");
                return SCRIPT_CONTINUE;
            }
            dictionary dctShipInfo = dataTableGetRow("datatables/ship/ship_debug.iff", strCommands[1]);
            if (dctShipInfo == null)
            {
                sendSystemMessageTestingOnly(self, strCommands[1] + " is not a valid ship type to use for this command");
                return SCRIPT_CONTINUE;
            }
            obj_id objShip = getPilotedShip(self);
            if (!isIdValid(objShip))
            {
                sendSystemMessageTestingOnly(self, "You must be in a ship to use this command");
                return SCRIPT_CONTINUE;
            }
            setShipEngineAccelerationRate(objShip, dctShipInfo.getFloat("engine_accel"));
            setShipEngineDecelerationRate(objShip, dctShipInfo.getFloat("engine_decel"));
            setShipEnginePitchAccelerationRateDegrees(objShip, dctShipInfo.getFloat("engine_pitch_accel"));
            setShipEngineYawAccelerationRateDegrees(objShip, dctShipInfo.getFloat("engine_yaw_accel"));
            setShipEngineRollAccelerationRateDegrees(objShip, dctShipInfo.getFloat("engine_roll_accel"));
            setShipEnginePitchRateMaximumDegrees(objShip, dctShipInfo.getFloat("engine_pitch"));
            setShipEngineYawRateMaximumDegrees(objShip, dctShipInfo.getFloat("engine_yaw"));
            setShipEngineRollRateMaximumDegrees(objShip, dctShipInfo.getFloat("engine_roll"));
            setShipEngineSpeedMaximum(objShip, dctShipInfo.getFloat("engine_speed"));
            setShipEngineSpeedRotationFactorMaximum(objShip, dctShipInfo.getFloat("speed_rotation_factor"));
            setShipSlideDampener(objShip, dctShipInfo.getFloat("slideDamp"));
            setShipBoosterEnergyCurrent(objShip, dctShipInfo.getFloat("booster_energy"));
            setShipBoosterEnergyMaximum(objShip, dctShipInfo.getFloat("booster_energy"));
            setShipBoosterEnergyRechargeRate(objShip, dctShipInfo.getFloat("booster_recharge"));
            setShipBoosterEnergyConsumptionRate(objShip, dctShipInfo.getFloat("booster_consumption"));
            setShipBoosterAcceleration(objShip, dctShipInfo.getFloat("booster_accel"));
            setShipBoosterSpeedMaximum(objShip, dctShipInfo.getFloat("booster_speed"));
            sendSystemMessageTestingOnly(self, "Reset flight model to " + strCommands[1]);
        }
        if (strCommands[0].equals("setAIFlightModel"))
        {
            if (strCommands.length < 2)
            {
                sendSystemMessageTestingOnly(self, "Syntax is setAIFlightModel <shipType>");
                return SCRIPT_CONTINUE;
            }
            dictionary dctShipInfo = dataTableGetRow("datatables/ship/ship_debug.iff", strCommands[1]);
            if (dctShipInfo == null)
            {
                sendSystemMessageTestingOnly(self, strCommands[1] + " is not a valid ship type to use for this command");
                return SCRIPT_CONTINUE;
            }
            obj_id[] objTestObjects = getAllObjectsWithScript(getLocation(self), 320000, "space.combat.combat_ship");
            for (int intI = 0; intI < objTestObjects.length; intI++)
            {
                if (!space_utils.isPlayerControlledShip(objTestObjects[intI]))
                {
                    obj_id objShip = objTestObjects[intI];
                    ship_ai.unitSetPilotType(objShip, dctShipInfo.getString("strPilotType"));
                    setShipEngineAccelerationRate(objShip, dctShipInfo.getFloat("engine_accel"));
                    setShipEngineDecelerationRate(objShip, dctShipInfo.getFloat("engine_decel"));
                    setShipEnginePitchAccelerationRateDegrees(objShip, dctShipInfo.getFloat("engine_pitch_accel"));
                    setShipEngineYawAccelerationRateDegrees(objShip, dctShipInfo.getFloat("engine_yaw_accel"));
                    setShipEngineRollAccelerationRateDegrees(objShip, dctShipInfo.getFloat("engine_roll_accel"));
                    setShipEnginePitchRateMaximumDegrees(objShip, dctShipInfo.getFloat("engine_pitch"));
                    setShipEngineYawRateMaximumDegrees(objShip, dctShipInfo.getFloat("engine_yaw"));
                    setShipEngineRollRateMaximumDegrees(objShip, dctShipInfo.getFloat("engine_roll"));
                    setShipEngineSpeedMaximum(objShip, dctShipInfo.getFloat("engine_speed"));
                    setShipEngineSpeedRotationFactorMaximum(objShip, dctShipInfo.getFloat("speed_rotation_factor_max"));
                    setShipEngineSpeedRotationFactorMinimum(objShip, dctShipInfo.getFloat("speed_rotation_factor_min"));
                    setShipEngineSpeedRotationFactorOptimal(objShip, dctShipInfo.getFloat("speed_rotation_factor_optimal"));
                    setShipSlideDampener(objShip, dctShipInfo.getFloat("slideDamp"));
                    if (isShipSlotInstalled(objShip, space_crafting.BOOSTER))
                    {
                        setShipBoosterEnergyCurrent(objShip, dctShipInfo.getFloat("booster_energy"));
                        setShipBoosterEnergyMaximum(objShip, dctShipInfo.getFloat("booster_energy"));
                        setShipBoosterEnergyRechargeRate(objShip, dctShipInfo.getFloat("booster_recharge"));
                        setShipBoosterEnergyConsumptionRate(objShip, dctShipInfo.getFloat("booster_consumption"));
                        setShipBoosterAcceleration(objShip, dctShipInfo.getFloat("booster_accel"));
                        setShipBoosterSpeedMaximum(objShip, dctShipInfo.getFloat("booster_speed"));
                    }
                    ship_ai.unitSetLeashDistance(objShip, dctShipInfo.getFloat("fltLeashDistance"));
                }
            }
            sendSystemMessageTestingOnly(self, "Reset flight models of All AI  to " + strCommands[1]);
        }
        if (strCommands[0].equals("setupSharedStation"))
        {
            obj_id[] objTestObjects = getAllObjectsWithTemplate(getLocation(self), 320000, "object/building/general/npe_space_station.iff");
            obj_id objStation = null;
            if ((objTestObjects == null) || (objTestObjects.length == 0))
            {
                objStation = createObject("object/building/general/npe_space_station.iff", getLocation(self));
                persistObject(objStation);
            }
            else 
            {
                objStation = objTestObjects[0];
                persistObject(objStation);
            }
            obj_id[] objCells = getContents(objStation);
            if ((objCells == null) || (objCells.length == 0))
            {
                sendSystemMessageTestingOnly(self, "ship  doesn't have an interior");
                return SCRIPT_CONTINUE;
            }
            obj_id objCell = objCells[0];
            location locTest = new location();
            locTest.cell = objCell;
            setLocation(self, locTest);
            sendSystemMessageTestingOnly(self, "done");
        }
        if (strCommands[0].equals("setupSpaceDungeon"))
        {
            obj_id[] objTestObjects = getAllObjectsWithTemplate(getLocation(self), 320000, "object/building/general/npe_space_dungeon.iff");
            obj_id objStation = null;
            if ((objTestObjects == null) || (objTestObjects.length == 0))
            {
                objStation = createObject("object/building/general/npe_space_dungeon.iff", getLocation(self));
                persistObject(objStation);
            }
            else 
            {
                objStation = objTestObjects[0];
                persistObject(objStation);
            }
            obj_id[] objCells = getContents(objStation);
            if ((objCells == null) || (objCells.length == 0))
            {
                sendSystemMessageTestingOnly(self, "ship  doesn't have an interior");
                return SCRIPT_CONTINUE;
            }
            obj_id objCell = objCells[0];
            location locTest = new location();
            locTest.cell = objCell;
            setLocation(self, locTest);
            sendSystemMessageTestingOnly(self, "done");
        }
        if (strCommands[0].equals("hackTest"))
        {
            String strFileName = "object/building/general/bunker_mad_bio.iff";
            obj_id[] objTestObjects = getAllObjectsWithTemplate(getLocation(self), 320000, strFileName);
            obj_id objStation = null;
            if ((objTestObjects == null) || (objTestObjects.length == 0))
            {
                objStation = createObject(strFileName, getLocation(self));
                persistObject(objStation);
            }
            else 
            {
                objStation = objTestObjects[0];
                persistObject(objStation);
            }
            obj_id[] objCells = getContents(objStation);
            if ((objCells == null) || (objCells.length == 0))
            {
                sendSystemMessageTestingOnly(self, "ship  doesn't have an interior");
                return SCRIPT_CONTINUE;
            }
            obj_id objCell = objCells[0];
            location locTest = new location();
            locTest.cell = objCell;
            setLocation(self, locTest);
            sendSystemMessageTestingOnly(self, "done");
        }
        if (strCommands[0].equals("setChassisVariant"))
        {
            if (strCommands.length < 2)
            {
                sendSystemMessageTestingOnly(self, "Syntax is setChassisVariant <Type>");
                return SCRIPT_CONTINUE;
            }
            String strFoo = toLower(strCommands[1]);
            if (space_crafting.setupChassisDifferentiation(space_transition.getContainingShip(self), strFoo))
            {
                sendSystemMessageTestingOnly(self, "Set Chassis to " + strCommands[1]);
            }
            else 
            {
                sendSystemMessageTestingOnly(self, "Did not Set Chassis. Bad Type:" + strFoo);
            }
        }
        if (strCommands[0].equals("autoSave"))
        {
            if (strCommands.length > 1)
            {
                if (strCommands[1].equals("on"))
                {
                    if (hasObjVar(self, "intAutoSaveOff"))
                    {
                        removeObjVar(self, "intAutoSaveOff");
                    }
                    sendSystemMessageTestingOnly(self, "Auto Save is turned on");
                    return SCRIPT_CONTINUE;
                }
                else if (strCommands[1].equals("off"))
                {
                    if (!hasObjVar(self, "intAutoSaveOff"))
                    {
                        setObjVar(self, "intAutoSaveOff", 1);
                    }
                    sendSystemMessageTestingOnly(self, "Auto Save is turned off");
                    return SCRIPT_CONTINUE;
                }
                else 
                {
                    sendSystemMessageTestingOnly(self, "Incorrect Syntax. Autosave commands are 'autoSave on' and 'autoSave off'");
                    return SCRIPT_CONTINUE;
                }
            }
            else 
            {
                sendSystemMessageTestingOnly(self, "Incorrect Syntax. Autosave commands are 'autoSave on' and 'autoSave off'");
                return SCRIPT_CONTINUE;
            }
        }
        if (strCommands[0].equals("fixMe"))
        {
            space_crafting.repairDamage(self, getPilotedShip(self), 100.0f);
            sendSystemMessageTestingOnly(self, "Fixed ship");
        }
        if (strCommands[0].equals("getIn"))
        {
            obj_id objShip = getLookAtTarget(self);
            if (!isIdValid(objShip))
            {
                sendSystemMessageTestingOnly(self, "No look at target");
                return SCRIPT_CONTINUE;
            }
            obj_id[] objCells = getContents(objShip);
            if ((objCells == null) || (objCells.length == 0))
            {
                sendSystemMessageTestingOnly(self, "ship  doesn't have an interior");
                return SCRIPT_CONTINUE;
            }
            obj_id objCell = objCells[0];
            location locTest = new location();
            locTest.cell = objCell;
            setLocation(self, locTest);
        }
        if (strCommands[0].equals("dumpBuildingBuildout"))
        {
            location locTest = getLocation(self);
            obj_id objCell = locTest.cell;
            if (!isIdValid(objCell))
            {
                sendSystemMessageTestingOnly(self, "DONT USE THIS IF YOU'RE NOT IN A BUILDING!");
                return SCRIPT_CONTINUE;
            }
            obj_id objBuilding = getContainedBy(objCell);
            if (!isIdValid(objBuilding))
            {
                sendSystemMessageTestingOnly(self, "You are not in a building. Don't use this when not in a building");
                return SCRIPT_CONTINUE;
            }
            String strBuildoutName = "";
            if (strCommands.length > 1)
            {
                strBuildoutName = strCommands[1];
            }
            else 
            {
                if (hasObjVar(objBuilding, "strBuildout"))
                {
                    strBuildoutName = getStringObjVar(objBuilding, "strBuildout");
                }
                else 
                {
                    sendSystemMessageTestingOnly(self, "Syntax is dumpBuildout <type> or set the strBuildout objvar");
                    return SCRIPT_CONTINUE;
                }
            }
            sendSystemMessageTestingOnly(self, "for objbuilding " + objBuilding + " objvatr is " + strBuildoutName);
            String strArea = locTest.area;
            String strDataTable = "";
            strDataTable = "datatables/interior_buildouts/" + strBuildoutName + ".tab";
            LOG("space", "table name is " + strDataTable);
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
                "s",
                "i",
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
                "strCellName",
                "intNoCreate",
                "strLocationList"
            };
            boolean boolTest = datatable.createDataTable(strDataTable, strHeaders, strHeaderTypes);
            if (!boolTest)
            {
                sendSystemMessageTestingOnly(self, "No dattable made");
                return SCRIPT_CONTINUE;
            }
            obj_id[] objObjects = getBuildingContents(objBuilding);
            sendSystemMessageTestingOnly(self, "dumping contents of " + objBuilding);
            for (int intI = 0; intI < objObjects.length; intI++)
            {
                if (isDumpable(objObjects[intI], true))
                {
                    dictionary dctRow = new dictionary();
                    int intNoCreate = 0;
                    locTest = getLocation(objObjects[intI]);
                    String strTemplate = getTemplateName(objObjects[intI]);
                    float fltX = locTest.x;
                    float fltY = locTest.y;
                    float fltZ = locTest.z;
                    String strCellName = space_utils.getCellName(objBuilding, locTest.cell);
                    if (hasObjVar(objObjects[intI], "intNoCreate"))
                    {
                        intNoCreate = 1;
                    }
                    String strLocationList = "";
                    if (hasObjVar(objObjects[intI], "strLocationList"))
                    {
                        strLocationList = getStringObjVar(objObjects[intI], "strLocationList");
                    }
                    dctRow.put("strLocationList", strLocationList);
                    dctRow.put("intNoCreate", intNoCreate);
                    transform vctTest = getTransform_o2p(objObjects[intI]);
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
                    dctRow.put("strCellName", strCellName);
                    dctRow.put("strTemplate", strTemplate);
                    String strObjVars = getPackedObjvars(objObjects[intI]);
                    dctRow.put("strObjVars", strObjVars);
                    String strScripts = utils.getPackedScripts(objObjects[intI]);
                    dctRow.put("strScripts", strScripts);
                    datatable.serverDataTableAddRow(strDataTable, dctRow);
                }
            }
            return SCRIPT_CONTINUE;
        }
        if (strCommands[0].equals("sad"))
        {
            obj_id objGun = createObject("object/weapon/ranged/pistol/pistol_cdef.iff", getLookAtTarget(self), "hold_r");
            sendSystemMessageTestingOnly(self, "asd");
        }
        if (strCommands[0].equals("makeTestGuy"))
        {
            obj_id objNPC = create.object("nym_guard", getLocation(self));
            String[] strScripts = utils.getUsableScriptList(objNPC);
            for (int intI = 0; intI < strScripts.length; intI++)
            {
                detachScript(objNPC, strScripts[intI]);
            }
            setObjVar(objNPC, "strSequenceIdentifier", strCommands[1]);
            attachScript(objNPC, "content_tools.sequencer_object");
            Object[] newParams = new Object[1];
            newParams[0] = objNPC;
            space_utils.callTrigger("OnPreloadComplete", newParams);
        }
        if (strCommands[0].equals("setupSequencerNPC"))
        {
            obj_id objTarget = getLookAtTarget(self);
            if (strCommands.length < 3)
            {
                sendSystemMessageTestingOnly(self, "Syntax is setupSequencerNPC <name> <table>");
            }
            String strName = strCommands[1];
            String strTable = strCommands[2];
            attachScript(objTarget, "content_tools.sequencer_master_object");
            attachScript(objTarget, "content_tools.sequencer_object");
            setObjVar(objTarget, "strSequenceTable", strTable);
            sendSystemMessageTestingOnly(self, "Set Table to " + strTable);
            setObjVar(objTarget, "strSequenceIdentifier", strName);
            sendSystemMessageTestingOnly(self, "Set Name to " + strName);
            Object[] newParams = new Object[1];
            newParams[0] = objTarget;
            space_utils.callTrigger("OnPreloadComplete", newParams);
        }
        if (strCommands[0].equals("setSequencerTable"))
        {
            obj_id objTarget = getLookAtTarget(self);
            setObjVar(objTarget, "strSequenceTable", strCommands[1]);
            sendSystemMessageTestingOnly(self, "set " + objTarget + " to table " + strCommands[1]);
        }
        if (strCommands[0].equals("setSequencerName"))
        {
            obj_id objTarget = getLookAtTarget(self);
            attachScript(objTarget, "content_tools.sequencer_object");
            String strName = strCommands[1];
            sendSystemMessageTestingOnly(self, "Set Name to " + strName);
            setObjVar(objTarget, "strSequenceTable", strName);
            Object[] newParams = new Object[1];
            newParams[0] = objTarget;
            space_utils.callTrigger("OnPreloadComplete", newParams);
        }
        if (strCommands[0].equals("testSequenceLookAt"))
        {
            obj_id objTarget = getLookAtTarget(self);
            utils.removeScriptVar(objTarget, "intSecondIndex");
            utils.removeScriptVar(objTarget, "strSecondaryTable");
            utils.removeScriptVar(objTarget, "intMainIndex");
            messageTo(objTarget, "doEvents", null, 0, false);
            sendSystemMessageTestingOnly(self, "messaged " + objTarget + " with doEvents");
        }
        if (strCommands[0].equals("testSequence"))
        {
            obj_id objTarget = sequencer.getMasterSequenceObject(self);
            utils.removeScriptVar(objTarget, "intSecondIndex");
            utils.removeScriptVar(objTarget, "strSecondaryTable");
            utils.removeScriptVar(objTarget, "intMainIndex");
            messageTo(objTarget, "doEvents", null, 0, false);
            sendSystemMessageTestingOnly(self, "Asdq");
        }
        if (strCommands[0].equals("checkSequencer"))
        {
            String strSequencerName = strCommands[1];
            obj_id objSequencer = sequencer.getSequenceObject(strSequencerName);
            sendSystemMessageTestingOnly(self, strSequencerName + " is " + objSequencer);
        }
        if (strCommands[0].equals("continueSequence"))
        {
            obj_id objTarget = getLookAtTarget(self);
            messageTo(objTarget, "continueMainTable", null, 0, false);
            sendSystemMessageTestingOnly(self, "Asdq2");
        }
        if (strCommands[0].equals("interruptSequence"))
        {
            obj_id objTarget = getTopMostContainer(self);
            messageTo(objTarget, "interruptSequence", null, 0, false);
            sendSystemMessageTestingOnly(self, "Asdq2");
        }
        if (strCommands[0].equals("makeSequencerObject"))
        {
            if (strCommands.length < 2)
            {
                sendSystemMessageTestingOnly(self, "Syntax is makeSequencerObject <sequencerName>");
                return SCRIPT_CONTINUE;
            }
            String strTest = strCommands[1];
            obj_id objSequencer = createObject("object/tangible/npe/npe_node.iff", getLocation(self));
            setObjVar(objSequencer, "strSequenceIdentifier", strCommands[1]);
            attachScript(objSequencer, "content_tools.sequencer_object");
            sendSystemMessageTestingOnly(self, "Made Sequencer object " + objSequencer + " and name " + strCommands[1]);
            Object[] newParams = new Object[1];
            newParams[0] = objSequencer;
            space_utils.callTrigger("OnPreloadComplete", newParams);
            return SCRIPT_CONTINUE;
        }
        if (strCommands[0].equals("doBuildout"))
        {
            Object[] newParams = new Object[1];
            newParams[0] = getTopMostContainer(self);
            space_utils.callTrigger("OnInitialize", newParams);
            sendSystemMessageTestingOnly(self, "trigger sent to " + newParams[0] + " and Buildout done");
        }
        if (strCommands[0].equals("zoneBootstrap"))
        {
            obj_id[] objTestObjects = getObjectsInRange(getLocation(self), 320000);
            sendSystemMessageTestingOnly(self, "Notifying " + objTestObjects.length);
            for (int intI = 0; intI < objTestObjects.length; intI++)
            {
                Object[] newParams = new Object[1];
                newParams[0] = objTestObjects[intI];
                space_utils.callTrigger("OnPreloadComplete", newParams);
            }
            sendSystemMessageTestingOnly(self, "Reset zone");
        }
        if (strCommands[0].equals("dungeon"))
        {
            warpPlayer(self, "dungeon1", 0, 0, 0, null, null, 0, 0, 0);
            location post = new location();
            warpPlayer(self, "dungeon1", post.x, post.y, post.z, obj_id.NULL_ID, 0.0f, 0.0f, 0.0f, "msgDungeonTravelComplete");
            sendSystemMessageTestingOnly(self, "Going to dungeon");
        }
        if (strCommands[0].equals("makeDungeonObjects"))
        {
            location locTest = new location();
            sendSystemMessageTestingOnly(self, "Making formal objects");
            String strObject = "object/building/general/npe_hangar_1.iff";
            obj_id[] objTestObjects = getAllObjectsWithTemplate(getLocation(self), 320000, strObject);
            location selfLocation = getLocation(self);
            if ((objTestObjects == null) || (objTestObjects.length < 1))
            {
                location locFoo = new location();
                locFoo.x = 1000;
                locFoo.y = 1000;
                locFoo.z = 1000;
                obj_id objObject = createObject(strObject, locFoo);
                persistObject(objObject);
                sendSystemMessageTestingOnly(self, "Made " + objObject + " " + strObject);
            }
            else 
            {
                sendSystemMessageTestingOnly(self, "Found " + objTestObjects[0] + " " + strObject);
            }
            strObject = "object/building/general/npe_space_dungeon.iff";
            objTestObjects = getAllObjectsWithTemplate(getLocation(self), 320000, strObject);
            if ((objTestObjects == null) || (objTestObjects.length < 1))
            {
                location locFoo = new location();
                locFoo.x = 2000;
                locFoo.y = 2000;
                locFoo.z = 2000;
                obj_id objObject = createObject(strObject, locFoo);
                persistObject(objObject);
                sendSystemMessageTestingOnly(self, "Made " + objObject + " " + strObject);
            }
            else 
            {
                sendSystemMessageTestingOnly(self, "Found " + objTestObjects[0] + " " + strObject);
            }
            strObject = "object/building/general/npe_hangar_2.iff";
            objTestObjects = getAllObjectsWithTemplate(getLocation(self), 320000, strObject);
            if ((objTestObjects == null) || (objTestObjects.length < 1))
            {
                location locFoo = new location();
                locFoo.x = 3000;
                locFoo.y = 3000;
                locFoo.z = 3000;
                obj_id objObject = createObject(strObject, locFoo);
                persistObject(objObject);
                sendSystemMessageTestingOnly(self, "Made " + objObject + " " + strObject);
            }
            else 
            {
                sendSystemMessageTestingOnly(self, "Found " + objTestObjects[0] + " " + strObject);
            }
            strObject = "object/building/general/npe_space_station.iff";
            objTestObjects = getAllObjectsWithTemplate(getLocation(self), 320000, strObject);
            if ((objTestObjects == null) || (objTestObjects.length < 1))
            {
                location locFoo = new location();
                locFoo.x = 4000;
                locFoo.y = 4000;
                locFoo.z = 4000;
                obj_id objObject = createObject(strObject, locFoo);
                persistObject(objObject);
                sendSystemMessageTestingOnly(self, "Made " + objObject + " " + strObject);
            }
            else 
            {
                sendSystemMessageTestingOnly(self, "Found " + objTestObjects[0] + " " + strObject);
            }
            sendSystemMessageTestingOnly(self, "Go to falcon ");
            return SCRIPT_CONTINUE;
        }
        if (strCommands[0].equals("makeFalcon"))
        {
            String strObject = "object/ship/dungeon/dungeon_yt1300.iff";
            location selfLocation = getLocation(space_transition.getContainingShip(self));
            obj_id[] objTestObjects = getAllObjectsWithTemplate(getLocation(self), 320000, strObject);
            if ((objTestObjects == null) || (objTestObjects.length < 1))
            {
                location locFoo = new location();
                locFoo.x = 4000;
                locFoo.y = 4000;
                locFoo.z = 4000;
                obj_id objObject = createObject(strObject, locFoo);
                sendSystemMessageTestingOnly(self, "Made " + objObject);
                persistObject(objObject);
            }
            else 
            {
                sendSystemMessageTestingOnly(self, "Found " + objTestObjects[0]);
            }
            return SCRIPT_CONTINUE;
        }
        if (strCommands[0].equals("gotoOrdZone"))
        {
            if (isGod(self))
            {
                return SCRIPT_CONTINUE;
            }
            sendSystemMessageTestingOnly(self, "Going to ord space...");
            npe.movePlayerFromSharedStationToOrdMantellSpace(self, new location(0, 0, 0));
        }
        if (strCommands[0].equals("gotoSpaceDungeon"))
        {
            if (isGod(self))
            {
                return SCRIPT_CONTINUE;
            }
            sendSystemMessageTestingOnly(self, "Going to space station...");
            npe.movePlayerFromOrdMantellSpaceToOrdMantellDungeon(self);
        }
        if (strCommands[0].equals("leaveSpaceDungeon"))
        {
            if (isGod(self))
            {
                return SCRIPT_CONTINUE;
            }
            sendSystemMessageTestingOnly(self, "Going to ord space...");
            npe.movePlayerFromOrdMantellDungeonToOrdMantellSpace(self, new location(0, 0, 0));
        }
        if (strCommands[0].equals("gotoSharedStationFromOrdZone"))
        {
            if (isGod(self))
            {
                return SCRIPT_CONTINUE;
            }
            sendSystemMessageTestingOnly(self, "Going to space station...");
            npe.movePlayerFromOrdMantellSpaceToSharedStation(self);
        }
        if (strCommands[0].equals("gotoFalcon"))
        {
            if (isGod(self))
            {
                return SCRIPT_CONTINUE;
            }
            sendSystemMessageTestingOnly(self, "Going to turret...");
            npe.movePlayerFromHangarToFalcon(self);
        }
        if (strCommands[0].equals("startHangar"))
        {
            obj_id objBuilding = getTopMostContainer(self);
            setObjVar(objBuilding, "tester", self);
            messageTo(objBuilding, "setupArea", null, 0, false);
            sendSystemMessageTestingOnly(self, "setting up " + objBuilding);
            messageTo(objBuilding, "doEvents", null, 4, false);
        }
        if (strCommands[0].equals("finishNpe"))
        {
            if (isGod(self))
            {
                return SCRIPT_CONTINUE;
            }
            sendSystemMessageTestingOnly(self, "Going to post staging area...");
            npe.movePlayerFromSharedStationToFinishLocation(self);
        }
        if (strCommands[0].equals("doNpe"))
        {
            space_utils.destroyShipControlDevices(self, true);
            space_utils.createShipControlDevice(self, "basic_hutt_light", true);
            grantCommand(self, "cert_starships_lighthuttfighter");
            sendSystemMessageTestingOnly(self, "Setting up for npe...");
            if (isGod(self))
            {
                sendSystemMessageTestingOnly(self, "TURN OFF GOD MODE! NEW PLAYERS DON't GET THAT");
                return SCRIPT_CONTINUE;
            }
        }
        if (strCommands[0].equals("findDungeon"))
        {
            obj_id[] objTestObjects = getAllObjectsWithTemplate(getLocation(self), 320000, "object/building/general/npe_space_dungeon.iff");
            sendSystemMessageTestingOnly(self, "objTestObjects.length is " + objTestObjects.length);
        }
        if (strCommands[0].equalsIgnoreCase("endHangar"))
        {
            obj_id building = getTopMostContainer(self);
            messageTo(building, "endEncounter", null, 0, false);
        }
        if (strCommands[0].equalsIgnoreCase("cleanupHangar"))
        {
            obj_id building = getTopMostContainer(self);
            messageTo(building, "cleanUpArea", null, 0, false);
        }
        return SCRIPT_CONTINUE;
    }
    public int createInteriorComponent(obj_id self, dictionary params) throws InterruptedException
    {
        int intButton = sui.getIntButtonPressed(params);
        if (intButton == sui.BP_CANCEL)
        {
            sendSystemMessageTestingOnly(self, "Cancelling interior component creation");
            return SCRIPT_CONTINUE;
        }
        String strTypeToCreate = sui.getInputBoxText(params);
        String strData = "";
        String[] strTypes = dataTableGetStringColumnNoDefaults("datatables/space_crafting/interior_component_lookup.iff", "strManagerName");
        boolean boolValid = false;
        for (int intI = 0; intI < strTypes.length; intI++)
        {
            if (strTypes[intI].equals(strTypeToCreate))
            {
                boolValid = true;
            }
            strData = strData + strTypes[intI] + "\n";
        }
        if (!boolValid)
        {
            sui.inputbox(self, self, "BAD TYPE NAME! \n What type of interior component would you like? Valid types are :\n" + strData, sui.OK_CANCEL, "Test", sui.INPUT_NORMAL, null, "createInteriorComponent", null);
            return SCRIPT_CONTINUE;
        }
        dictionary dctInfo = dataTableGetRow("datatables/space_crafting/interior_component_lookup.iff", strTypeToCreate);
        if (dctInfo == null)
        {
            sendSystemMessageTestingOnly(self, "CATASTROPHIC FUCKUP! go find dan and ask how this could POSSIBLY EVERY HAPPEN1?#@!@?!?@!@");
            return SCRIPT_CONTINUE;
        }
        String strTemplate = dctInfo.getString("strTemplateName");
        obj_id objTest = createObject(strTemplate, getLocation(self));
        if (!isIdValid(objTest))
        {
            sendSystemMessageTestingOnly(self, "You have made a bad object of type " + strTemplate + ". This is using entry " + strTypeToCreate + " in datatables/space_crafting/interior_component_lookup.tab. Please go fix this!@!@!");
            return SCRIPT_CONTINUE;
        }
        setObjVar(objTest, "strManagerName", strTypeToCreate);
        attachScript(objTest, "space.crafting.interior_component");
        sendSystemMessageTestingOnly(self, "Interior Manager Created, move into position, object id is " + objTest);
        return SCRIPT_CONTINUE;
    }
    public int specifyCellToLock(obj_id self, dictionary params) throws InterruptedException
    {
        int intButton = sui.getIntButtonPressed(params);
        if (intButton == sui.BP_CANCEL)
        {
            sendSystemMessageTestingOnly(self, "Cancelling terminal creation");
            return SCRIPT_CONTINUE;
        }
        String strCellToLock = sui.getInputBoxText(params);
        String strData = "";
        obj_id objShip = getTopMostContainer(self);
        if (!isIdValid(objShip))
        {
            sendSystemMessageTestingOnly(self, "This command only works in a ship");
            return SCRIPT_CONTINUE;
        }
        String[] strCells = getCellNames(objShip);
        boolean boolValid = false;
        for (int intI = 0; intI < strCells.length; intI++)
        {
            if (strCells[intI].equals(strCellToLock))
            {
                boolValid = true;
            }
            strData = strData + strCells[intI] + "\n";
        }
        if (!boolValid)
        {
            sui.inputbox(self, self, "BAD CELL NAME: " + strCellToLock + "\n What cell should this panel lock? Valid Cell names are :\n" + strData, sui.OK_CANCEL, "Test", sui.INPUT_NORMAL, null, "specifyCellToLock", null);
        }
        obj_id objTest = createObject("object/tangible/terminal/terminal_ship_interior_security_1.iff", getLocation(self));
        setObjVar(objTest, "strCellToLock", strCellToLock);
        attachScript(objTest, "space.combat.combat_ship_security_terminal");
        sendSystemMessageTestingOnly(self, "Terminal Created, move into position, object id is " + objTest);
        return SCRIPT_CONTINUE;
    }
    public int specifyLootLookup(obj_id self, dictionary params) throws InterruptedException
    {
        int intButton = sui.getIntButtonPressed(params);
        if (intButton == sui.BP_CANCEL)
        {
            sendSystemMessageTestingOnly(self, "Cancelling Loot COntainer creation");
            clearSpawnerScriptVar(self, true);
            return SCRIPT_CONTINUE;
        }
        String strChestType = sui.getInputBoxText(params);
        dictionary dctParams = dataTableGetRow("datatables/space_content/loot_chests.iff", strChestType);
        if (strChestType == null)
        {
            sui.inputbox(self, self, "BAD CHEST TYPE: " + strChestType + "\n What chest type would you like to use?", sui.OK_CANCEL, "Test", sui.INPUT_NORMAL, null, "specifyLootLookup", null);
            return SCRIPT_CONTINUE;
        }
        String strLootLookup = dctParams.getString("strLootLookup");
        int intRolls = dctParams.getInt("intRolls");
        float fltItemChance = dctParams.getFloat("fltItemChance");
        int intMinCredits = dctParams.getInt("intMinCredits");
        int intMaxCredits = dctParams.getInt("intMaxCredits");
        String strChestTemplate = dctParams.getString("strChestTemplate");
        final String LOOKUP_TABLE = "datatables/space_loot/loot_lookup.iff";
        String[] strColumns = dataTableGetStringColumnNoDefaults(LOOKUP_TABLE, strLootLookup);
        obj_id objContainer = null;
        if ((strColumns == null) || (strColumns.length == 0))
        {
            sendSystemMessageTestingOnly(self, "BAD BAD BAD LOOT LOOKUP TYPE FOR YOUR CHEST! FOR ENTRY " + strChestType + " You used " + strLootLookup + " GO FIX IT!@!@!");
            return SCRIPT_CONTINUE;
        }
        try
        {
            objContainer = createObject(strChestTemplate, getLocation(self));
        }
        catch(Throwable err)
        {
            sendSystemMessageTestingOnly(self, "BAD OBJECT at entry " + strChestType + " Object is " + strChestTemplate);
            return SCRIPT_CONTINUE;
        }
        if (!isIdValid(objContainer))
        {
            sendSystemMessageTestingOnly(self, "BAD OBJECT at entry " + strChestType + " Object is " + strChestTemplate);
            return SCRIPT_CONTINUE;
        }
        setObjVar(objContainer, "loot.intCredits", rand(intMinCredits, intMaxCredits));
        int intNumItems = 0;
        fltItemChance = fltItemChance * 100;
        LOG("space", "Item chance is " + fltItemChance);
        for (int intI = 0; intI < intRolls; intI++)
        {
            int intRoll = rand(1, 100);
            LOG("space", "roll is " + intRoll);
            if (intRoll < fltItemChance)
            {
                LOG("space", "Increasing items");
                intNumItems = intNumItems + 1;
            }
        }
        setObjVar(objContainer, "loot.intNumItems", intNumItems);
        setObjVar(objContainer, "loot.strLootTable", strLootLookup);
        String strScript = dctParams.getString("strScript");
        attachScript(objContainer, "space.content_tools.loot_container");
        attachScript(objContainer, strScript);
        sendSystemMessageTestingOnly(self, "Loot Container created, object id is " + objContainer);
        return SCRIPT_CONTINUE;
    }
    public int makeSpaceStation(obj_id self, dictionary params) throws InterruptedException
    {
        int intButton = sui.getIntButtonPressed(params);
        if (intButton == sui.BP_CANCEL)
        {
            sendSystemMessageTestingOnly(self, "Cancelling station creation");
            return SCRIPT_CONTINUE;
        }
        String strStationType = sui.getInputBoxText(params);
        String[] strStations = utils.getStringArrayScriptVar(self, "strStations");
        utils.removeScriptVar(self, "strStations");
        boolean boolTest = false;
        for (int intI = 0; intI < strStations.length; intI++)
        {
            String strTest = strStations[intI];
            if (strTest.equals(strStationType))
            {
                boolTest = true;
            }
        }
        if (!boolTest)
        {
            String strStationString = "";
            utils.setScriptVar(self, "strStations", strStations);
            for (int intI = 0; intI < strStations.length; intI++)
            {
                strStationString = strStationString + strStations[intI] + "\n";
            }
            sui.inputbox(self, self, "INVALID STATION TYPE! What type of spaceStation is this? valid names are " + strStationString, sui.OK_CANCEL, "Test", sui.INPUT_NORMAL, null, "makeSpaceStation", null);
            return SCRIPT_CONTINUE;
        }
        dictionary dctStationInfo = dataTableGetRow("datatables/space_content/spacestations.iff", strStationType);
        if (dctStationInfo == null)
        {
            sendSystemMessageTestingOnly(self, "Critical station failure. Go ask dan. Station type was " + strStationType);
            utils.removeScriptVar(self, "strStations");
            return SCRIPT_CONTINUE;
        }
        String strName = dctStationInfo.getString("strName");
        String strType = dctStationInfo.getString("strType");
        String strRawString = dctStationInfo.getString("strAdditionalScripts");
        String[] strAdditionalScripts = split(strRawString, ' ');
        String strConversationScript = dctStationInfo.getString("strConversationScript");
        float fltCostPerDamagePoint = dctStationInfo.getFloat("fltCostPerDamagePoint");
        obj_id objStation = space_create.createShip(strType, getTransform_o2p(getPilotedShip(self)));
        if (!isIdValid(objStation))
        {
            sendSystemMessageTestingOnly(self, "in datatable datatables/space_content/spacestations.tab entry " + strStationType + " the template is bad. Template name is " + strType);
            return SCRIPT_CONTINUE;
        }
        setObjVar(objStation, "strName", strName);
        setObjVar(objStation, "fltCostPerDamagePoint", fltCostPerDamagePoint);
        if (!strConversationScript.equals(""))
        {
            if (!(attachScript(objStation, strConversationScript) == SCRIPT_CONTINUE))
            {
                sendSystemMessageTestingOnly(self, "in datatable datatables/space_content/spacestations.tab entry " + strStationType + " the convo script is bad. script name is " + strConversationScript);
                destroyObject(objStation);
                return SCRIPT_CONTINUE;
            }
        }
        if (strAdditionalScripts != null)
        {
            for (int intI = 0; intI < strAdditionalScripts.length; intI++)
            {
                if (!strAdditionalScripts[intI].equals(""))
                {
                    attachScript(objStation, strAdditionalScripts[intI]);
                }
            }
        }
        setObjVar(objStation, "intDockable", 1);
        int intInvincible = dctStationInfo.getInt("intInvincible");
        if (intInvincible > 0)
        {
            setObjVar(self, "intInvincible", intInvincible);
        }
        if (hasObjVar(objStation, "intNoDump"))
        {
            removeObjVar(objStation, "intNoDump");
        }
        sendSystemMessageTestingOnly(self, "Station created! id is " + objStation);
        return SCRIPT_CONTINUE;
    }
    public int makeEntrySpaceStation(obj_id self, dictionary params) throws InterruptedException
    {
        int intButton = sui.getIntButtonPressed(params);
        if (intButton == sui.BP_CANCEL)
        {
            sendSystemMessageTestingOnly(self, "Cancelling station creation");
            return SCRIPT_CONTINUE;
        }
        String strStationType = sui.getInputBoxText(params);
        String[] strStations = utils.getStringArrayScriptVar(self, "strStations");
        utils.removeScriptVar(self, "strStations");
        boolean boolTest = false;
        for (int intI = 0; intI < strStations.length; intI++)
        {
            String strTest = strStations[intI];
            if (strTest.equals(strStationType))
            {
                boolTest = true;
            }
        }
        if (!boolTest)
        {
            String strStationString = "";
            utils.setScriptVar(self, "strStations", strStations);
            for (int intI = 0; intI < strStations.length; intI++)
            {
                strStationString = strStationString + strStations[intI] + "\n";
            }
            sui.inputbox(self, self, "INVALID STATION TYPE! What type of entry station is this? valid names are " + strStationString, sui.OK_CANCEL, "Test", sui.INPUT_NORMAL, null, "makeEntrySpaceStation", null);
            return SCRIPT_CONTINUE;
        }
        dictionary dctStationInfo = dataTableGetRow("datatables/space_content/battlefields/entry_stations.iff", strStationType);
        if (dctStationInfo == null)
        {
            sendSystemMessageTestingOnly(self, "Critical station failure. Go ask dan. Station type was " + strStationType);
            utils.removeScriptVar(self, "strStations");
            return SCRIPT_CONTINUE;
        }
        String strName = dctStationInfo.getString("strName");
        String strShipType = dctStationInfo.getString("strShipType");
        String strConversationScript = dctStationInfo.getString("strConversationScript");
        obj_id objStation = space_create.createShip(strShipType, getTransform_o2p(space_transition.getContainingShip(self)));
        if (!isIdValid(objStation))
        {
            sendSystemMessageTestingOnly(self, "in datatable datatables/space_content/spacestations.tab entry " + strStationType + " the ship type is bad. ship type  name is " + strShipType);
            return SCRIPT_CONTINUE;
        }
        setObjVar(objStation, "strName", strName);
        removeObjVar(objStation, "intNoDump");
        setObjVar(objStation, "intInvincible", 1);
        if (!(attachScript(objStation, strConversationScript) == SCRIPT_CONTINUE))
        {
            sendSystemMessageTestingOnly(self, "in datatable datatables/space_content/spacestations.tab entry " + strStationType + " the convo script is bad. script name is " + strConversationScript);
            destroyObject(objStation);
            return SCRIPT_CONTINUE;
        }
        sendSystemMessageTestingOnly(self, "Station created! id is " + objStation);
        return SCRIPT_CONTINUE;
    }
    public boolean isValidSpawnerType(String strText) throws InterruptedException
    {
        if (strText.equals("generic"))
        {
            return true;
        }
        if (strText.equals("wave"))
        {
            return true;
        }
        if (strText.equals("asteroid"))
        {
            return true;
        }
        return false;
    }
    public int specifySpawnerType(obj_id self, dictionary params) throws InterruptedException
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
            sendSystemMessageTestingOnly(self, "That's not a valid spawner type, exiting");
            sui.inputbox(self, self, "INVALID SPAWN TYPE, PLEASE RE_ENTER. \nWhat type of spawner? Valid types are : generic", sui.OK_CANCEL, "Test", sui.INPUT_NORMAL, null, "specifySpawnerType", null);
            return SCRIPT_CONTINUE;
        }
        else 
        {
            if (strType.equals("asteroid"))
            {
                utils.setScriptVar(self, "strSpawnerType", strType);
                sui.inputbox(self, self, "What is this spawner's name?", sui.OK_CANCEL, "Test", sui.INPUT_NORMAL, null, "specifyAsteroidSpawnerName", null);
                return SCRIPT_CONTINUE;
            }
            utils.setScriptVar(self, "strSpawnerType", strType);
            sui.inputbox(self, self, "What is this spawner's name?", sui.OK_CANCEL, "Test", sui.INPUT_NORMAL, null, "specifySpawnerName", null);
        }
        return SCRIPT_CONTINUE;
    }
    public int specifySpawnerName(obj_id self, dictionary params) throws InterruptedException
    {
        int intButton = sui.getIntButtonPressed(params);
        if (intButton == sui.BP_CANCEL)
        {
            sendSystemMessageTestingOnly(self, "Cancelling spawner creation");
            clearSpawnerScriptVar(self, true);
            return SCRIPT_CONTINUE;
        }
        String strName = sui.getInputBoxText(params);
        if (strName.equals(""))
        {
            sui.inputbox(self, self, "INVALID NAME: Please enter a valid name \nWhat is this spawner's name?", sui.OK_CANCEL, "Test", sui.INPUT_NORMAL, null, "specifySpawnerName", null);
            return SCRIPT_CONTINUE;
        }
        else 
        {
        }
        utils.setScriptVar(self, "strSpawnerName", strName);
        sui.inputbox(self, self, "SCALE: Meters \nWhat is this spawner's minimum spawn distance?", sui.OK_CANCEL, "Test", sui.INPUT_NORMAL, null, "specifySpawnerMinSpawnDistance", null);
        return SCRIPT_CONTINUE;
    }
    public int specifyAsteroidSpawnerName(obj_id self, dictionary params) throws InterruptedException
    {
        int intButton = sui.getIntButtonPressed(params);
        if (intButton == sui.BP_CANCEL)
        {
            sendSystemMessageTestingOnly(self, "Cancelling spawner creation");
            clearSpawnerScriptVar(self, true);
            return SCRIPT_CONTINUE;
        }
        String strName = sui.getInputBoxText(params);
        if (strName.equals(""))
        {
            sui.inputbox(self, self, "INVALID NAME: Please enter a valid name \nWhat is this spawner's name?", sui.OK_CANCEL, "Test", sui.INPUT_NORMAL, null, "specifyAsteroidSpawnerName", null);
            return SCRIPT_CONTINUE;
        }
        else 
        {
        }
        utils.setScriptVar(self, "strSpawnerName", strName);
        sui.inputbox(self, self, "What type of asteroid should this spawn?  Valid types are: iron \n carbonaceous \n silicaceous \n ice \n obsidian \n diamond \n crystal \n petrochem \n acid \n cyanomethanic \n sulfuric \n methane \n organometallic .", sui.OK_CANCEL, "Test", sui.INPUT_NORMAL, null, "specifyAsteroidType", null);
        return SCRIPT_CONTINUE;
    }
    public int specifyAsteroidType(obj_id self, dictionary params) throws InterruptedException
    {
        int intButton = sui.getIntButtonPressed(params);
        if (intButton == sui.BP_CANCEL)
        {
            sendSystemMessageTestingOnly(self, "Cancelling spawner creation");
            clearSpawnerScriptVar(self, true);
            return SCRIPT_CONTINUE;
        }
        String strAsteroidType = sui.getInputBoxText(params);
        if (!isValidAsteroidType(strAsteroidType))
        {
            sui.inputbox(self, self, "INVALID TYPE: Please enter a valid type\nWhat is this spawner's type?", sui.OK_CANCEL, "Test", sui.INPUT_NORMAL, null, "specifyAsteroidType", null);
        }
        utils.setScriptVar(self, "strAsteroidType", strAsteroidType);
        sui.inputbox(self, self, "Minimum asteroid resource pool?  Integers only please.", sui.OK_CANCEL, "Test", sui.INPUT_NORMAL, null, "specifyMinAsteroidPool", null);
        return SCRIPT_CONTINUE;
    }
    public int specifyMinAsteroidPool(obj_id self, dictionary params) throws InterruptedException
    {
        int intButton = sui.getIntButtonPressed(params);
        if (intButton == sui.BP_CANCEL)
        {
            sendSystemMessageTestingOnly(self, "Cancelling spawner creation");
            clearSpawnerScriptVar(self, true);
            return SCRIPT_CONTINUE;
        }
        String strMinPool = sui.getInputBoxText(params);
        int intMinResourcePool = utils.stringToInt(strMinPool);
        utils.setScriptVar(self, "intMinResourcePool", intMinResourcePool);
        sui.inputbox(self, self, "Maximum asteroid resource pool?  Integers only please.", sui.OK_CANCEL, "Test", sui.INPUT_NORMAL, null, "specifyMaxAsteroidPool", null);
        return SCRIPT_CONTINUE;
    }
    public int specifyMaxAsteroidPool(obj_id self, dictionary params) throws InterruptedException
    {
        int intButton = sui.getIntButtonPressed(params);
        if (intButton == sui.BP_CANCEL)
        {
            sendSystemMessageTestingOnly(self, "Cancelling spawner creation");
            clearSpawnerScriptVar(self, true);
            return SCRIPT_CONTINUE;
        }
        String strMaxPool = sui.getInputBoxText(params);
        int intMaxResourcePool = utils.stringToInt(strMaxPool);
        utils.setScriptVar(self, "intMaxResourcePool", intMaxResourcePool);
        sui.inputbox(self, self, "Mining danger level? (How bad are the baddies that spawn.  Valid entries are int (0-5).", sui.OK_CANCEL, "Test", sui.INPUT_NORMAL, null, "specifyMiningDangerLevel", null);
        return SCRIPT_CONTINUE;
    }
    public int specifyMiningDangerLevel(obj_id self, dictionary params) throws InterruptedException
    {
        int intButton = sui.getIntButtonPressed(params);
        if (intButton == sui.BP_CANCEL)
        {
            sendSystemMessageTestingOnly(self, "Cancelling spawner creation");
            clearSpawnerScriptVar(self, true);
            return SCRIPT_CONTINUE;
        }
        String strDangerLevel = sui.getInputBoxText(params);
        int intDangerLevel = utils.stringToInt(strDangerLevel);
        utils.setScriptVar(self, "intDangerLevel", intDangerLevel);
        sui.inputbox(self, self, "Frequency of spawn (percent).  Int 0-100.  This is the percentage of dynamic asteroids spawned that will generate asteroids.  Thus, 10 means that 1 in 10 dynamic chunks will bring baddies..", sui.OK_CANCEL, "Test", sui.INPUT_NORMAL, null, "specifyMiningDangerFrequency", null);
        return SCRIPT_CONTINUE;
    }
    public int specifyMiningDangerFrequency(obj_id self, dictionary params) throws InterruptedException
    {
        int intButton = sui.getIntButtonPressed(params);
        if (intButton == sui.BP_CANCEL)
        {
            sendSystemMessageTestingOnly(self, "Cancelling spawner creation");
            clearSpawnerScriptVar(self, true);
            return SCRIPT_CONTINUE;
        }
        String strDangerPct = sui.getInputBoxText(params);
        int intDangerPct = utils.stringToInt(strDangerPct);
        utils.setScriptVar(self, "intDangerPct", intDangerPct);
        writeAsteroidSpawner(self);
        return SCRIPT_CONTINUE;
    }
    public int specifySpawnerMinSpawnDistance(obj_id self, dictionary params) throws InterruptedException
    {
        int intButton = sui.getIntButtonPressed(params);
        if (intButton == sui.BP_CANCEL)
        {
            sendSystemMessageTestingOnly(self, "Cancelling spawner creation");
            clearSpawnerScriptVar(self, true);
            return SCRIPT_CONTINUE;
        }
        String strMinDistance = sui.getInputBoxText(params);
        float fltMinSpawnDistance = utils.stringToFloat(strMinDistance);
        if (fltMinSpawnDistance == Float.NEGATIVE_INFINITY)
        {
            sui.inputbox(self, self, "INVALID NUMBER, PLEASE USE A NUMBER\n SCALE: Meters \nWhat is this spawner's minimum spawn distance?", sui.OK_CANCEL, "Test", sui.INPUT_NORMAL, null, "specifySpawnerMinSpawnDistance", null);
        }
        utils.setScriptVar(self, "fltMinSpawnDistance", fltMinSpawnDistance);
        sui.inputbox(self, self, "Min Spawn Distance : " + fltMinSpawnDistance + "\nSCALE: Meters \nWhat is this spawner's maximum spawn distance?", sui.OK_CANCEL, "Test", sui.INPUT_NORMAL, null, "specifySpawnerMaxSpawnDistance", null);
        return SCRIPT_CONTINUE;
    }
    public int specifySpawnerMaxSpawnDistance(obj_id self, dictionary params) throws InterruptedException
    {
        int intButton = sui.getIntButtonPressed(params);
        if (intButton == sui.BP_CANCEL)
        {
            sendSystemMessageTestingOnly(self, "Cancelling spawner creation");
            clearSpawnerScriptVar(self, true);
            return SCRIPT_CONTINUE;
        }
        float fltMinSpawnDistance = utils.getFloatScriptVar(self, "fltMinSpawnDistance");
        String strMaxDistance = sui.getInputBoxText(params);
        float fltMaxSpawnDistance = utils.stringToFloat(strMaxDistance);
        if (fltMaxSpawnDistance == Float.NEGATIVE_INFINITY)
        {
            sui.inputbox(self, self, "INVALID NUMBER, PLEASE USE A NUMBER\n SCALE: Meters \nWhat is this spawner's maximum spawn distance?", sui.OK_CANCEL, "Test", sui.INPUT_NORMAL, null, "specifySpawnerMaxSpawnDistance", null);
            return SCRIPT_CONTINUE;
        }
        if (fltMaxSpawnDistance <= fltMinSpawnDistance)
        {
            sui.inputbox(self, self, "MAX SPAWN DISTANCE NEEDS TO BE GREATER THAN MIN SPAWN DISTANCE, MIN DISTANCE IS:" + fltMinSpawnDistance + "\n SCALE: Meters \n What is this spawner's maximum spawn distance?", sui.OK_CANCEL, "Test", sui.INPUT_NORMAL, null, "specifySpawnerMaxSpawnDistance", null);
            return SCRIPT_CONTINUE;
        }
        String strSpawnerType = utils.getStringScriptVar(self, "strSpawnerType");
        utils.setScriptVar(self, "fltMaxSpawnDistance", fltMaxSpawnDistance);
        if (strSpawnerType.equals("wave"))
        {
            sui.inputbox(self, self, "SCALE: Seconds \nWhat is the minimum delay between waves?", sui.OK_CANCEL, "Test", sui.INPUT_NORMAL, null, "specifySpawnerMinWaveSpawnTime", null);
        }
        else 
        {
            sui.inputbox(self, self, "SCALE: Seconds \nWhat is this spawner's minimum spawn recycle time?", sui.OK_CANCEL, "Test", sui.INPUT_NORMAL, null, "specifySpawnerMinSpawnTime", null);
        }
        return SCRIPT_CONTINUE;
    }
    public int specifySpawnerMinWaveSpawnTime(obj_id self, dictionary params) throws InterruptedException
    {
        int intButton = sui.getIntButtonPressed(params);
        if (intButton == sui.BP_CANCEL)
        {
            sendSystemMessageTestingOnly(self, "Cancelling spawner creation");
            clearSpawnerScriptVar(self, true);
            return SCRIPT_CONTINUE;
        }
        String strMinWaveSpawnTime = sui.getInputBoxText(params);
        float fltMinWaveSpawnTime = utils.stringToFloat(strMinWaveSpawnTime);
        if (fltMinWaveSpawnTime == Float.NEGATIVE_INFINITY)
        {
            sui.inputbox(self, self, "INVALID NUMBER, PLEASE USE A NUMBER\n SCALE: Seconds \nWhat is the minimum delay between waves?", sui.OK_CANCEL, "Test", sui.INPUT_NORMAL, null, "specifySpawnerMinWaveSpawnTime", null);
            return SCRIPT_CONTINUE;
        }
        utils.setScriptVar(self, "fltMinWaveSpawnTime", fltMinWaveSpawnTime);
        sui.inputbox(self, self, "Min Wave Spawn Time :" + fltMinWaveSpawnTime + "\n SCALE: Seconds \nWhat is this maximum delay between waves?", sui.OK_CANCEL, "Test", sui.INPUT_NORMAL, null, "specifySpawnerMaxWaveSpawnTime", null);
        return SCRIPT_CONTINUE;
    }
    public int specifySpawnerMaxWaveSpawnTime(obj_id self, dictionary params) throws InterruptedException
    {
        int intButton = sui.getIntButtonPressed(params);
        if (intButton == sui.BP_CANCEL)
        {
            sendSystemMessageTestingOnly(self, "Cancelling spawner creation");
            clearSpawnerScriptVar(self, true);
            return SCRIPT_CONTINUE;
        }
        float fltMinWaveSpawnTime = utils.getFloatScriptVar(self, "fltMinWaveSpawnTime");
        String strMaxWaveSpawnTime = sui.getInputBoxText(params);
        float fltMaxWaveSpawnTime = utils.stringToFloat(strMaxWaveSpawnTime);
        if (fltMaxWaveSpawnTime == Float.NEGATIVE_INFINITY)
        {
            sui.inputbox(self, self, "INVALID NUMBER, PLEASE USE A NUMBER\n SCALE: Seconds\nWhat is this maximum delay between waves?", sui.OK_CANCEL, "Test", sui.INPUT_NORMAL, null, "specifySpawnerMaxSpawnTime", null);
            return SCRIPT_CONTINUE;
        }
        if (fltMaxWaveSpawnTime <= fltMinWaveSpawnTime)
        {
            sui.inputbox(self, self, "MAXSPAWN TIME MUST BE GREATER THAN MIN SPAWN TIME, MIN SPAWN TIME IS:" + fltMinWaveSpawnTime + "\n SCALE: Seconds \nWhat is this maximum delay between waves?", sui.OK_CANCEL, "Test", sui.INPUT_NORMAL, null, "specifySpawnerMaxSpawnTime", null);
            return SCRIPT_CONTINUE;
        }
        sui.inputbox(self, self, "SCALE: seconds\nWhat is the minimum time between spawner resets? (All spawns destroyed, and starting at wave 1)", sui.OK_CANCEL, "Test", sui.INPUT_NORMAL, null, "specifySpawnerMinResetTime", null);
        utils.setScriptVar(self, "fltMaxWaveSpawnTime", fltMaxWaveSpawnTime);
        return SCRIPT_CONTINUE;
    }
    public int specifySpawnerMinResetTime(obj_id self, dictionary params) throws InterruptedException
    {
        int intButton = sui.getIntButtonPressed(params);
        if (intButton == sui.BP_CANCEL)
        {
            sendSystemMessageTestingOnly(self, "Cancelling spawner creation");
            clearSpawnerScriptVar(self, true);
            return SCRIPT_CONTINUE;
        }
        String strMinResetTime = sui.getInputBoxText(params);
        float fltMinResetTime = utils.stringToFloat(strMinResetTime);
        if (fltMinResetTime == Float.NEGATIVE_INFINITY)
        {
            sui.inputbox(self, self, "INVALID NUMBER, PLEASE USE A NUMBER\n SCALE: Seconds \nWhat is the minimum time between spawner resets? (All spawns destroyed, and starting at wave 1)", sui.OK_CANCEL, "Test", sui.INPUT_NORMAL, null, "specifySpawnerMinResetTime", null);
            return SCRIPT_CONTINUE;
        }
        utils.setScriptVar(self, "fltMinResetTime", fltMinResetTime);
        sui.inputbox(self, self, "SCALE: seconds\nWhat is the maximum time between spawner resets? (All spawns destroyed, and starting at wave 1)", sui.OK_CANCEL, "Test", sui.INPUT_NORMAL, null, "specifySpawnerMaxResetTime", null);
        return SCRIPT_CONTINUE;
    }
    public int specifySpawnerMaxResetTime(obj_id self, dictionary params) throws InterruptedException
    {
        int intButton = sui.getIntButtonPressed(params);
        if (intButton == sui.BP_CANCEL)
        {
            sendSystemMessageTestingOnly(self, "Cancelling spawner creation");
            clearSpawnerScriptVar(self, true);
            return SCRIPT_CONTINUE;
        }
        float fltMinResetTime = utils.getFloatScriptVar(self, "fltMinResetTime");
        String strMaxResetTime = sui.getInputBoxText(params);
        float fltMaxResetTime = utils.stringToFloat(strMaxResetTime);
        if (fltMaxResetTime == Float.NEGATIVE_INFINITY)
        {
            sui.inputbox(self, self, "INVALID NUMBER, PLEASE USE A NUMBER\n SCALE: Seconds \nWhat is the maximum time between spawner resets?", sui.OK_CANCEL, "Test", sui.INPUT_NORMAL, null, "specifySpawnerMaxResetTime", null);
            return SCRIPT_CONTINUE;
        }
        if (fltMaxResetTime <= fltMinResetTime)
        {
            sui.inputbox(self, self, "MAXSPAWN TIME MUST BE GREATER THAN MIN RESET TIME, MIN RESET TIME IS:" + fltMinResetTime + "\n SCALE: Seconds\nWhat is the maximum time between spawner resets?", sui.OK_CANCEL, "Test", sui.INPUT_NORMAL, null, "specifySpawnerMaxSpawnTime", null);
            return SCRIPT_CONTINUE;
        }
        utils.setScriptVar(self, "fltMaxResetTime", fltMaxResetTime);
        sui.inputbox(self, self, "What wave would you like to add? Currently the list contains nothing", sui.OK_CANCEL, "Test", sui.INPUT_NORMAL, null, "specifyWaves", null);
        return SCRIPT_CONTINUE;
    }
    public int specifySpawnerMinSpawnTime(obj_id self, dictionary params) throws InterruptedException
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
            sui.inputbox(self, self, "INVALID NUMBER, PLEASE USE A NUMBER\n SCALE: Seconds \nWhat is this spawner's minimum spawn time?", sui.OK_CANCEL, "Test", sui.INPUT_NORMAL, null, "specifySpawnerMinSpawnTime", null);
            return SCRIPT_CONTINUE;
        }
        utils.setScriptVar(self, "fltMinSpawnTime", fltMinSpawnTime);
        sui.inputbox(self, self, "Min Spawn Time :" + fltMinSpawnTime + "\n SCALE: Seconds \nWhat is this spawner's maximum spawn recycle time?", sui.OK_CANCEL, "Test", sui.INPUT_NORMAL, null, "specifySpawnerMaxSpawnTime", null);
        return SCRIPT_CONTINUE;
    }
    public int specifySpawnerMaxSpawnTime(obj_id self, dictionary params) throws InterruptedException
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
            sui.inputbox(self, self, "INVALID NUMBER, PLEASE USE A NUMBER\n SCALE: Seconds \nWhat is this spawner's maximum spawn time?", sui.OK_CANCEL, "Test", sui.INPUT_NORMAL, null, "specifySpawnerMaxSpawnTime", null);
            return SCRIPT_CONTINUE;
        }
        if (fltMaxSpawnTime <= fltMinSpawnTime)
        {
            sui.inputbox(self, self, "MAXSPAWN TIME MUST BE GREATER THAN MIN SPAWN TIME, MIN SPAWN TIME IS:" + fltMinSpawnTime + "\n SCALE: Seconds \nWhat is this spawner's maximum spawn time?", sui.OK_CANCEL, "Test", sui.INPUT_NORMAL, null, "specifySpawnerMaxSpawnTime", null);
            return SCRIPT_CONTINUE;
        }
        sui.inputbox(self, self, "SCALE: Mobs \nWhat is this spawner's maximum population?", sui.OK_CANCEL, "Test", sui.INPUT_NORMAL, null, "specifySpawnerSpawnCount", null);
        utils.setScriptVar(self, "fltMaxSpawnTime", fltMaxSpawnTime);
        return SCRIPT_CONTINUE;
    }
    public int specifySpawnerSpawnCount(obj_id self, dictionary params) throws InterruptedException
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
            sui.inputbox(self, self, "INVALID NUMBER, PLEASE USE A NUMBER\n SCALE: Mobs \nWhat is this spawner's maximum population ?", sui.OK_CANCEL, "Test", sui.INPUT_NORMAL, null, "specifySpawnerSpawnCount", null);
            return SCRIPT_CONTINUE;
        }
        utils.setScriptVar(self, "intSpawnCount", intSpawnCount);
        sui.inputbox(self, self, "What mobs would you like to spawn? Currently the list is Empty", sui.OK_CANCEL, "Test", sui.INPUT_NORMAL, null, "specifySpawns", null);
        return SCRIPT_CONTINUE;
    }
    public int specifyWaves(obj_id self, dictionary params) throws InterruptedException
    {
        int intButton = sui.getIntButtonPressed(params);
        if (intButton == sui.BP_CANCEL)
        {
            sendSystemMessageTestingOnly(self, "Cancelling spawner creation");
            clearSpawnerScriptVar(self, true);
            return SCRIPT_CONTINUE;
        }
        String strWave = sui.getInputBoxText(params);
        if (strWave.equals("done"))
        {
            deltadictionary dctScriptVars = self.getScriptVars();
            sui.inputbox(self, self, "What are the spawned mob's default behavior? Allowed values are patrol, patrolNoRecycle and loiter", sui.OK_CANCEL, "Test", sui.INPUT_NORMAL, null, "specifyDefaultBehavior", null);
            return SCRIPT_CONTINUE;
        }
        Vector strWaves = new Vector();
        strWaves.setSize(0);
        if (utils.hasScriptVar(self, "strWaves"))
        {
            strWaves = utils.getResizeableStringArrayScriptVar(self, "strWaves");
        }
        String strData = "";
        for (int intI = 0; intI < strWaves.size(); intI++)
        {
            strData = strData + ((String)strWaves.get(intI)) + "\n";
        }
        if (isValidWave(strWave))
        {
            strWaves = utils.addElement(strWaves, strWave);
            strData = strData + strWave + "\n";
            utils.setScriptVar(self, "strWaves", strWaves);
            sui.inputbox(self, self, "wave of type " + strWave + " Added. Please add another wave or type done to finish\n. Current wave list is \n" + strData, sui.OK_CANCEL, "Test", sui.INPUT_NORMAL, null, "specifyWaves", null);
            return SCRIPT_CONTINUE;
        }
        else 
        {
            sui.inputbox(self, self, strWave + " IS NOT A VALID WAVE. Please try again\n What wave would you like to add? Currently the list contains \n" + strData, sui.OK_CANCEL, "Test", sui.INPUT_NORMAL, null, "specifyWaves", null);
            return SCRIPT_CONTINUE;
        }
    }
    public int specifySpawns(obj_id self, dictionary params) throws InterruptedException
    {
        int intButton = sui.getIntButtonPressed(params);
        if (intButton == sui.BP_CANCEL)
        {
            sendSystemMessageTestingOnly(self, "Cancelling spawner creation");
            clearSpawnerScriptVar(self, true);
            return SCRIPT_CONTINUE;
        }
        String strSpawnToAdd = sui.getInputBoxText(params);
        if (strSpawnToAdd.equals("done"))
        {
            deltadictionary dctScriptVars = self.getScriptVars();
            sui.inputbox(self, self, "What are these spawned mob's default behavior? Allowed values are patrol, patrolNoRecycle, patrolFixedCircle, patrolRandomPath and loiter", sui.OK_CANCEL, "Test", sui.INPUT_NORMAL, null, "specifyDefaultBehavior", null);
            return SCRIPT_CONTINUE;
        }
        Vector strSpawns = new Vector();
        strSpawns.setSize(0);
        if (utils.hasScriptVar(self, "strSpawns"))
        {
            strSpawns = utils.getResizeableStringArrayScriptVar(self, "strSpawns");
        }
        String strData = "";
        for (int intI = 0; intI < strSpawns.size(); intI++)
        {
            strData = strData + ((String)strSpawns.get(intI)) + "\n";
        }
        if (isValidMobString(strSpawnToAdd))
        {
            strSpawns = utils.addElement(strSpawns, strSpawnToAdd);
            strData = strData + strSpawnToAdd + "\n";
            utils.setScriptVar(self, "strSpawns", strSpawns);
            sui.inputbox(self, self, "Spawn of type " + strSpawnToAdd + " Added. Please add another spawn or type done to finish\n. Current spawn list is \n" + strData, sui.OK_CANCEL, "Test", sui.INPUT_NORMAL, null, "specifySpawns", null);
            return SCRIPT_CONTINUE;
        }
        else 
        {
            sui.inputbox(self, self, strSpawnToAdd + " IS NOT A VALID SPAWN. Please try again\n What mobs would you like to spawn? Currently the list contains \n" + strData, sui.OK_CANCEL, "Test", sui.INPUT_NORMAL, null, "specifySpawns", null);
            return SCRIPT_CONTINUE;
        }
    }
    public int specifyDefaultBehavior(obj_id self, dictionary params) throws InterruptedException
    {
        int intButton = sui.getIntButtonPressed(params);
        if (intButton == sui.BP_CANCEL)
        {
            sendSystemMessageTestingOnly(self, "Cancelling spawner creation");
            clearSpawnerScriptVar(self, true);
            return SCRIPT_CONTINUE;
        }
        String strDefaultBehavior = sui.getInputBoxText(params);
        if (!isValidBehavior(strDefaultBehavior))
        {
            sui.inputbox(self, self, strDefaultBehavior + " IS AN INVALID BEHAVIOR \n What are these spawned mob's default behavior? Allowed values are patrol, patrolNoRecycle and loiter", sui.OK_CANCEL, "Test", sui.INPUT_NORMAL, null, "specifyDefaultBehavior", null);
            return SCRIPT_CONTINUE;
        }
        utils.setScriptVar(self, "strDefaultBehavior", strDefaultBehavior);
        if (strDefaultBehavior.equals("loiter"))
        {
            sui.inputbox(self, self, "SCALE: Meters \nWhat is the minimum loiter distance? ", sui.OK_CANCEL, "Test", sui.INPUT_NORMAL, null, "specifyMinLoiterDistance", null);
            return SCRIPT_CONTINUE;
        }
        if ((strDefaultBehavior.equals("patrol")) || (strDefaultBehavior.equals("patrolNoRecycle")))
        {
            sui.inputbox(self, self, "What global name do you want to use for your patrol points\n Use existing to latch onto existing patrol points otherwise it must be unique, and should not contain numbers. ", sui.OK_CANCEL, "Test", sui.INPUT_NORMAL, null, "setPatrolPointName", null);
            return SCRIPT_CONTINUE;
        }
        if (strDefaultBehavior.equals("patrolFixedCircle"))
        {
            sui.inputbox(self, self, "SCALE: Meters \nWhat is the Minimum circle size? ", sui.OK_CANCEL, "Test", sui.INPUT_NORMAL, null, "specifyMinCircleDistance", null);
        }
        if (strDefaultBehavior.equals("patrolRandomPath"))
        {
            sui.inputbox(self, self, "SCALE: Meters \nWhat is the Minimum circle size? ", sui.OK_CANCEL, "Test", sui.INPUT_NORMAL, null, "specifyMinCircleDistance", null);
        }
        return SCRIPT_CONTINUE;
    }
    public int specifyMinCircleDistance(obj_id self, dictionary params) throws InterruptedException
    {
        int intButton = sui.getIntButtonPressed(params);
        if (intButton == sui.BP_CANCEL)
        {
            sendSystemMessageTestingOnly(self, "Cancelling spawner creation");
            clearSpawnerScriptVar(self, true);
            return SCRIPT_CONTINUE;
        }
        String strMinCircleDistance = sui.getInputBoxText(params);
        float fltMinCircleDistance = utils.stringToFloat(strMinCircleDistance);
        if (fltMinCircleDistance == Float.NEGATIVE_INFINITY)
        {
            sui.inputbox(self, self, "INVALID NUMBER, PLEASE USE A NUMBER\nSCALE: Meters \nWhat is the Minimum circle size? ", sui.OK_CANCEL, "Test", sui.INPUT_NORMAL, null, "specifyMinCircleDistance", null);
            return SCRIPT_CONTINUE;
        }
        sui.inputbox(self, self, "Minimum Circle Distance: " + fltMinCircleDistance + "\n SCALE: Meters \nWhat is the maximum circle distance? ", sui.OK_CANCEL, "Test", sui.INPUT_NORMAL, null, "specifyMaxCircleDistance", null);
        utils.setScriptVar(self, "fltMinCircleDistance", fltMinCircleDistance);
        return SCRIPT_CONTINUE;
    }
    public int specifyMaxCircleDistance(obj_id self, dictionary params) throws InterruptedException
    {
        int intButton = sui.getIntButtonPressed(params);
        if (intButton == sui.BP_CANCEL)
        {
            sendSystemMessageTestingOnly(self, "Cancelling spawner creation");
            clearSpawnerScriptVar(self, true);
            return SCRIPT_CONTINUE;
        }
        float fltMinCircleDistance = utils.getFloatScriptVar(self, "fltMinCircleDistance");
        String strMaxCircleDistance = sui.getInputBoxText(params);
        float fltMaxCircleDistance = utils.stringToFloat(strMaxCircleDistance);
        if (fltMaxCircleDistance == Float.NEGATIVE_INFINITY)
        {
            sui.inputbox(self, self, "INVALID NUMBER, PLEASE USE A NUMBER\nSCALE: Meters \nWhat is the maximum circle distance? ", sui.OK_CANCEL, "Test", sui.INPUT_NORMAL, null, "specifyMaxCircleDistance", null);
            return SCRIPT_CONTINUE;
        }
        if (fltMinCircleDistance > fltMaxCircleDistance)
        {
            sui.inputbox(self, self, "MAX LOITER DISTANCE MUST BE GREATHER THAN MIN\n MIN LOITER DISTANCE: " + fltMinCircleDistance + "\nSCALE: Meters \nWhat is the maximum loiter distance? ", sui.OK_CANCEL, "Test", sui.INPUT_NORMAL, null, "specifyMaxCircleDistance", null);
            return SCRIPT_CONTINUE;
        }
        utils.setScriptVar(self, "fltMaxCircleDistance", fltMaxCircleDistance);
        obj_id objManager = space_battlefield.getManagerObject();
        if (hasObjVar(objManager, "intSpawnersDeactivated"))
        {
            sui.inputbox(self, self, "What Phase do you want these spawners to be activated in? Valid options are ALL, IMPERIAL and REBEL.\nIMPERIAL means that the empire won the battlezone and REBEL treasure boats spawn here.", sui.OK_CANCEL, "Test", sui.INPUT_NORMAL, null, "setActivationPhase", null);
            return SCRIPT_CONTINUE;
        }
        else 
        {
            writeSpawner(self);
        }
        return SCRIPT_CONTINUE;
    }
    public int specifyMinLoiterDistance(obj_id self, dictionary params) throws InterruptedException
    {
        int intButton = sui.getIntButtonPressed(params);
        if (intButton == sui.BP_CANCEL)
        {
            sendSystemMessageTestingOnly(self, "Cancelling spawner creation");
            clearSpawnerScriptVar(self, true);
            return SCRIPT_CONTINUE;
        }
        String strMinLoiterDistance = sui.getInputBoxText(params);
        float fltMinLoiterDistance = utils.stringToFloat(strMinLoiterDistance);
        if (fltMinLoiterDistance == Float.NEGATIVE_INFINITY)
        {
            sui.inputbox(self, self, "INVALID NUMBER, PLEASE USE A NUMBER\nSCALE: Meters \nWhat is the minimum loiter distance? ", sui.OK_CANCEL, "Test", sui.INPUT_NORMAL, null, "specifyMinLoiterDistance", null);
            return SCRIPT_CONTINUE;
        }
        sendSystemMessageTestingOnly(self, "Calling max thing");
        sui.inputbox(self, self, "Minimum Loiter Distance: " + fltMinLoiterDistance + "\n SCALE: Meters \nWhat is the maximum loiter distance? ", sui.OK_CANCEL, "Test", sui.INPUT_NORMAL, null, "specifyMaxLoiterDistance", null);
        utils.setScriptVar(self, "fltMinLoiterDistance", fltMinLoiterDistance);
        return SCRIPT_CONTINUE;
    }
    public int specifyMaxLoiterDistance(obj_id self, dictionary params) throws InterruptedException
    {
        sendSystemMessageTestingOnly(self, "MAX CALLEd");
        int intButton = sui.getIntButtonPressed(params);
        if (intButton == sui.BP_CANCEL)
        {
            sendSystemMessageTestingOnly(self, "Cancelling spawner creation");
            clearSpawnerScriptVar(self, true);
            return SCRIPT_CONTINUE;
        }
        float fltMinLoiterDistance = utils.getFloatScriptVar(self, "fltMinLoiterDistance");
        String strMaxLoiterDistance = sui.getInputBoxText(params);
        float fltMaxLoiterDistance = utils.stringToFloat(strMaxLoiterDistance);
        if (fltMaxLoiterDistance == Float.NEGATIVE_INFINITY)
        {
            sui.inputbox(self, self, "INVALID NUMBER, PLEASE USE A NUMBER\nSCALE: Meters \nWhat is the maximum loiter distance? ", sui.OK_CANCEL, "Test", sui.INPUT_NORMAL, null, "specifyMaxLoiterDistance", null);
            return SCRIPT_CONTINUE;
        }
        if (fltMinLoiterDistance > fltMaxLoiterDistance)
        {
            sui.inputbox(self, self, "MAX LOITER DISTANCE MUST BE GREATHER THAN MIN\n MIN LOITER DISTANCE: " + fltMinLoiterDistance + "\nSCALE: Meters \nWhat is the maximum loiter distance? ", sui.OK_CANCEL, "Test", sui.INPUT_NORMAL, null, "specifyMinLoiterDistance", null);
            return SCRIPT_CONTINUE;
        }
        utils.setScriptVar(self, "fltMaxLoiterDistance", fltMaxLoiterDistance);
        obj_id objManager = space_battlefield.getManagerObject();
        if (hasObjVar(objManager, "intSpawnersDeactivated"))
        {
            sui.inputbox(self, self, "What Phase do you want these spawners to be activated in? Valid options are ALL, IMPERIAL and REBEL.\nIMPERIAL means that the empire won the battlezone and REBEL treasure boats spawn here.", sui.OK_CANCEL, "Test", sui.INPUT_NORMAL, null, "setActivationPhase", null);
            return SCRIPT_CONTINUE;
        }
        else 
        {
            writeSpawner(self);
        }
        return SCRIPT_CONTINUE;
    }
    public boolean isValidBehavior(String strBehavior) throws InterruptedException
    {
        if (strBehavior.equals("loiter"))
        {
            return true;
        }
        if (strBehavior.equals("patrol"))
        {
            return true;
        }
        if (strBehavior.equals("patrolNoRecycle"))
        {
            return true;
        }
        if (strBehavior.equals("patrolRandomPath"))
        {
            return true;
        }
        if (strBehavior.equals("patrolFixedCircle"))
        {
            return true;
        }
        return false;
    }
    public boolean isValidAsteroidType(String strAsteroidType) throws InterruptedException
    {
        if (strAsteroidType.equals("iron"))
        {
            return true;
        }
        else if (strAsteroidType.equals("silicaceous"))
        {
            return true;
        }
        else if (strAsteroidType.equals("carbonaceous"))
        {
            return true;
        }
        else if (strAsteroidType.equals("ice"))
        {
            return true;
        }
        else if (strAsteroidType.equals("obsidian"))
        {
            return true;
        }
        else if (strAsteroidType.equals("diamond"))
        {
            return true;
        }
        else if (strAsteroidType.equals("crystal"))
        {
            return true;
        }
        else if (strAsteroidType.equals("petrochem"))
        {
            return true;
        }
        else if (strAsteroidType.equals("acid"))
        {
            return true;
        }
        else if (strAsteroidType.equals("cyanomethanic"))
        {
            return true;
        }
        else if (strAsteroidType.equals("sulfuric"))
        {
            return true;
        }
        else if (strAsteroidType.equals("methane"))
        {
            return true;
        }
        else if (strAsteroidType.equals("organometallic"))
        {
            return true;
        }
        return false;
    }
    public boolean isValidMobString(String strShipType) throws InterruptedException
    {
        String strDataTable = "";
        int intIndex = strShipType.indexOf("squad_");
        String[] strColumn = new String[0];
        if (intIndex > -1)
        {
            LOG("space", "Found squad");
            strDataTable = "datatables/space_content/spawners/squads.iff";
            strColumn = dataTableGetStringColumn(strDataTable, "strSquadName");
        }
        else 
        {
            strDataTable = "datatables/space_mobile/space_mobile.iff";
            strColumn = dataTableGetStringColumn(strDataTable, "strIndex");
        }
        intIndex = utils.getElementPositionInArray(strColumn, strShipType);
        if (intIndex > -1)
        {
            return true;
        }
        else 
        {
            return false;
        }
    }
    public int setPatrolPointName(obj_id self, dictionary params) throws InterruptedException
    {
        int intButton = sui.getIntButtonPressed(params);
        if (intButton == sui.BP_CANCEL)
        {
            sendSystemMessageTestingOnly(self, "Cancelling spawner creation");
            clearSpawnerScriptVar(self, true);
            return SCRIPT_CONTINUE;
        }
        String strPatrolPointName = sui.getInputBoxText(params);
        if (strPatrolPointName.equals("existing"))
        {
            dictionary dctReturnInfo = getPatrolPointNamesInZone(self);
            if (dctReturnInfo != null)
            {
                String[] strNames = dctReturnInfo.getStringArray("strNames");
                String strData = "";
                for (int intI = 0; intI < strNames.length; intI++)
                {
                    LOG("space", "strData is " + strData);
                    strData = strData + strNames[intI] + "\n";
                }
                sui.inputbox(self, self, "What patrol point batches do you want to use? Valid points are: \n " + strData, sui.OK_CANCEL, "Test", sui.INPUT_NORMAL, null, "useExistingPatrolPoint", null);
            }
            else 
            {
                sui.inputbox(self, self, "NO PATROL POINTS IN ZONE, SO DON'T USE EXISTING!@!@\n What global name do you want to use for your patrol points\n Use existing to latch onto existing patrol points otherwise it must be unique, and should not contain numbers. ", sui.OK_CANCEL, "Test", sui.INPUT_NORMAL, null, "setPatrolPointName", null);
            }
            return SCRIPT_CONTINUE;
        }
        utils.setScriptVar(self, "strPatrolPointName", strPatrolPointName);
        utils.setScriptVar(self, "trTestLocation", getTransform_o2p(getPilotedShip(self)));
        sui.inputbox(self, self, "Patrol behavior requires you to create at least 2 patrol points. Go to where you wish to place the patrol point, face the proper direction, and enter 'add' into this box. When you are finished adding points, write 'done'", sui.OK_CANCEL, "Test", sui.INPUT_NORMAL, null, "makePatrolPoint", null);
        return SCRIPT_CONTINUE;
    }
    public int useExistingPatrolPoint(obj_id self, dictionary params) throws InterruptedException
    {
        int intButton = sui.getIntButtonPressed(params);
        if (intButton == sui.BP_CANCEL)
        {
            sendSystemMessageTestingOnly(self, "Cancelling spawner creation");
            clearSpawnerScriptVar(self, true);
            return SCRIPT_CONTINUE;
        }
        String strExistingPatrolPoint = sui.getInputBoxText(params);
        dictionary dctReturnInfo = getPatrolPointNamesInZone(self);
        String[] strNames = dctReturnInfo.getStringArray("strNames");
        int[] intCount = dctReturnInfo.getIntArray("intCount");
        int intIndex = utils.getElementPositionInArray(strNames, strExistingPatrolPoint);
        if (intIndex < 0)
        {
            String strData = "";
            for (int intI = 0; intI < strNames.length; intI++)
            {
                strData = strData + strNames[intI] + "\n";
            }
            sui.inputbox(self, self, "INVALID PATROL POINT NAME\n What patrol point batches do you want to use? Valid points are: \n " + strData, sui.OK_CANCEL, "Test", sui.INPUT_NORMAL, null, "useExistingPatrolPoint", null);
        }
        else 
        {
            obj_id[] objPatrolPoints = getPatrolPointsFromName(strNames[intIndex], intCount[intIndex]);
            transform[] trPatrolPoints = new transform[intCount[intIndex]];
            String[] strPatrolPoints = new String[intCount[intIndex]];
            for (int intI = 0; intI < intCount[intIndex]; intI++)
            {
                trPatrolPoints[intI] = getTransform_o2p(objPatrolPoints[intI]);
                strPatrolPoints[intI] = strNames[intIndex] + "_" + intI;
            }
            utils.setScriptVar(self, "trPatrolPoints", trPatrolPoints);
            utils.setScriptVar(self, "strPatrolPoints", strPatrolPoints);
            utils.setScriptVar(self, "objPatrolPoints", objPatrolPoints);
            sui.inputbox(self, self, "Patrol points placed. To finalize this spawner, go to the location for this object and enter 'done'. Use 'doneOrigin' to use the the start location", sui.OK_CANCEL, "Test", sui.INPUT_NORMAL, null, "createSpawner", null);
        }
        return SCRIPT_CONTINUE;
    }
    public obj_id[] getPatrolPointsFromName(String strName, int intCount) throws InterruptedException
    {
        Vector objPoints = new Vector();
        objPoints.setSize(0);
        obj_id[] objObjects = getAllObjectsWithTemplate(getLocation(getSelf()), 320000, "object/tangible/space/content_infrastructure/basic_patrol_point.iff");
        LOG("space", "intCount is " + intCount);
        for (int intI = 0; intI < intCount; intI++)
        {
            for (int intJ = 0; intJ < objObjects.length; intJ++)
            {
                String strRealName = getStringObjVar(objObjects[intJ], "strName");
                LOG("space", "comparing " + strRealName + " to " + strName + "_" + intI);
                if (strRealName.equals(strName + "_" + intCount))
                {
                    objPoints = utils.addElement(objPoints, objObjects[intJ]);
                    intJ = objObjects.length + 1;
                }
            }
        }
        obj_id[] _objPoints = new obj_id[0];
        if (objPoints != null)
        {
            _objPoints = new obj_id[objPoints.size()];
            objPoints.toArray(_objPoints);
        }
        return _objPoints;
    }
    public String getRawPatrolPointName(String strName) throws InterruptedException
    {
        String[] strArray = split(strName, '_');
        if (strArray.length == 0)
        {
            return null;
        }
        String strRawName = "";
        for (int intI = 0; intI < strArray.length - 1; intI++)
        {
            strRawName = strRawName + strArray[intI];
            if (intI != strArray.length - 2)
            {
                strRawName = strRawName + "_";
            }
            LOG("space", "at " + intI + " name is " + strRawName + " passed name is " + strName);
        }
        return strRawName;
    }
    public dictionary getPatrolPointNamesInZone(obj_id self) throws InterruptedException
    {
        obj_id[] objObjects = getAllObjectsWithTemplate(getLocation(self), 32000, "object/tangible/space/content_infrastructure/basic_patrol_point.iff");
        Vector strNames = new Vector();
        strNames.setSize(0);
        Vector intCount = new Vector();
        intCount.setSize(0);
        for (int intI = 0; intI < objObjects.length; intI++)
        {
            String strPatrolPointName = getStringObjVar(objObjects[intI], "strName");
            if (strPatrolPointName != null)
            {
                String strRawName = getRawPatrolPointName(strPatrolPointName);
                int intIndex = utils.getElementPositionInArray(strNames, strRawName);
                if (intIndex < 0)
                {
                    strNames = utils.addElement(strNames, strRawName);
                    intCount = utils.addElement(intCount, 1);
                }
                else 
                {
                    intCount.set(intIndex, new Integer(((Integer)intCount.get(intIndex)).intValue() + 1));
                }
                LOG("space", "strNames length is " + strNames.size());
            }
        }
        if (strNames.size() > 0)
        {
            dictionary dctReturnInfo = new dictionary();
            dctReturnInfo.put("strNames", strNames);
            dctReturnInfo.put("intCount", intCount);
            return dctReturnInfo;
        }
        return null;
    }
    public int makePatrolPoint(obj_id self, dictionary params) throws InterruptedException
    {
        int intButton = sui.getIntButtonPressed(params);
        if (intButton == sui.BP_CANCEL)
        {
            sendSystemMessageTestingOnly(self, "Cancelling spawner creation");
            clearSpawnerScriptVar(self, true);
            return SCRIPT_CONTINUE;
        }
        String strString = sui.getInputBoxText(params);
        if (strString.equals("done"))
        {
            sendSystemMessageTestingOnly(self, "DoNE");
            if (utils.hasScriptVar(self, "objPatrolPoints"))
            {
                sendSystemMessageTestingOnly(self, "HasScriptVar");
                obj_id[] objPatrolPoints = utils.getObjIdArrayScriptVar(self, "objPatrolPoints");
                if (objPatrolPoints.length < 2)
                {
                    sui.inputbox(self, self, "YOU NEED AT LEAST 2 PATROL POINTS. \n Patrol behavior requires you to create at least 2 patrol points. Go to where you wish to place the patrol point, face the proper direction, and enter 'add' into this box. When you are finished adding points, write 'done'", sui.OK_CANCEL, "Test", sui.INPUT_NORMAL, null, "makePatrolPoint", null);
                    return SCRIPT_CONTINUE;
                }
                sui.inputbox(self, self, "Patrol points placed. To finalize this spawner, go to the location for this object and enter 'done'. Use 'doneOrigin' to use the the start location", sui.OK_CANCEL, "Test", sui.INPUT_NORMAL, null, "createSpawner", null);
                return SCRIPT_CONTINUE;
            }
            else 
            {
                sui.inputbox(self, self, "YOU NEED AT LEAST 2 PATROL POINTS. \n Patrol behavior requires you to create at least 2 patrol points. Go to where you wish to place the patrol point, face the proper direction, and enter 'add' into this box. When you are finished adding points, write 'done'", sui.OK_CANCEL, "Test", sui.INPUT_NORMAL, null, "makePatrolPoint", null);
                return SCRIPT_CONTINUE;
            }
        }
        if (strString.equals("add"))
        {
            obj_id objPatrolPoint = createObject("object/tangible/space/content_infrastructure/basic_patrol_point.iff", getTransform_o2p(getPilotedShip(self)), null);
            Vector objPatrolPoints = new Vector();
            objPatrolPoints.setSize(0);
            if (utils.hasScriptVar(self, "objPatrolPoints"))
            {
                objPatrolPoints = utils.getResizeableObjIdArrayScriptVar(self, "objPatrolPoints");
            }
            objPatrolPoints = utils.addElement(objPatrolPoints, objPatrolPoint);
            utils.setScriptVar(self, "objPatrolPoints", objPatrolPoints);
            sui.inputbox(self, self, "Point Added. Go to where you wish to place the next patrol point, face the proper direction, and enter 'add' into this box. When you are finished adding points, write 'done'", sui.OK_CANCEL, "Test", sui.INPUT_NORMAL, null, "makePatrolPoint", null);
            return SCRIPT_CONTINUE;
        }
        sui.inputbox(self, self, strString + " IS AN INVALID STRING\n VALID STRINGS ARE : add, done\n Patrol behavior requires you to create at least 2 patrol points. Go to where you wish to place the patrol point, face the proper direction, and enter 'add' into this box. When you are finished adding points, write 'done'", sui.OK_CANCEL, "Test", sui.INPUT_NORMAL, null, "makePatrolPoint", null);
        return SCRIPT_CONTINUE;
    }
    public int createSpawner(obj_id self, dictionary params) throws InterruptedException
    {
        int intButton = sui.getIntButtonPressed(params);
        if (intButton == sui.BP_CANCEL)
        {
            sendSystemMessageTestingOnly(self, "Cancelling spawner creation");
            clearSpawnerScriptVar(self, true);
            return SCRIPT_CONTINUE;
        }
        String strString = sui.getInputBoxText(params);
        if ((strString.equals("done")) || (strString.equals("doneOrigin")))
        {
            if (strString.equals("done"))
            {
                utils.removeScriptVar(self, "trTestLocation");
            }
            obj_id objManager = space_battlefield.getManagerObject();
            if (hasObjVar(objManager, "intSpawnersDeactivated"))
            {
                sui.inputbox(self, self, "What Phase do you want these spawners to be activated in? Valid options are ALL, IMPERIAL and REBEL.\nIMPERIAL means that the empire won the battlezone and REBEL treasure boats spawn here.", sui.OK_CANCEL, "Test", sui.INPUT_NORMAL, null, "setActivationPhase", null);
                return SCRIPT_CONTINUE;
            }
            else 
            {
                writeSpawner(self);
            }
        }
        else 
        {
            sui.inputbox(self, self, "INVALID STRING THE ONLY VALID ENTRY IS 'done'. To finalize this spawner, go to the location for this object and enter 'done' or 'doneOrigin'.");
            return SCRIPT_CONTINUE;
        }
        return SCRIPT_CONTINUE;
    }
    public int setActivationPhase(obj_id self, dictionary params) throws InterruptedException
    {
        int intButton = sui.getIntButtonPressed(params);
        if (intButton == sui.BP_CANCEL)
        {
            sendSystemMessageTestingOnly(self, "Cancelling spawner creation");
            clearSpawnerScriptVar(self, true);
            return SCRIPT_CONTINUE;
        }
        String strString = sui.getInputBoxText(params);
        int intPhase = -1;
        if (strString.equals("ALL"))
        {
            intPhase = -1;
        }
        else if (strString.equals("IMPERIAL"))
        {
            intPhase = space_battlefield.STATE_IMPERIAL;
        }
        else if (strString.equals("REBEL"))
        {
            intPhase = space_battlefield.STATE_REBEL;
        }
        else 
        {
            sui.inputbox(self, self, "INVALID STRING " + strString + "What Phase do you want these spawners to be activated in? Valid options are ALL, IMPERIAL and REBEL.\nIMPERIAL means that the empire won the battlezone and REBEL treasure boats spawn here.", sui.OK_CANCEL, "Test", sui.INPUT_NORMAL, null, "setActivationPhase", null);
            return SCRIPT_CONTINUE;
        }
        utils.setScriptVar(self, "intActivationPhase", intPhase);
        sendSystemMessageTestingOnly(self, "DoNE");
        writeSpawner(self);
        return SCRIPT_CONTINUE;
    }
    public void clearSpawnerScriptVar(obj_id self, boolean boolDestroySpawners) throws InterruptedException
    {
        utils.removeScriptVar(self, "strPatrolPoints");
        utils.removeScriptVar(self, "objPatrolPoints");
        utils.removeScriptVar(self, "trPatrolPoints");
        utils.removeScriptVar(self, "strSpawnerType");
        utils.removeScriptVar(self, "strSpawnerName");
        utils.removeScriptVar(self, "fltMinSpawnDistance");
        utils.removeScriptVar(self, "fltMaxSpawnDistance");
        utils.removeScriptVar(self, "fltMinSapwnTime");
        utils.removeScriptVar(self, "fltMaxSpawnTime");
        utils.removeScriptVar(self, "strSpawns");
        utils.removeScriptVar(self, "intSpawnCount");
        utils.removeScriptVar(self, "strDefaultBehavior");
        utils.removeScriptVar(self, "fltMinLoiterDistance");
        utils.removeScriptVar(self, "fltMaxLoiterDistance");
        utils.removeScriptVar(self, "strPatrolPointName");
        utils.removeScriptVar(self, "fltMaxWaveSpawnTime");
        utils.removeScriptVar(self, "fltMinWaveSpawnTime");
        utils.removeScriptVar(self, "fltMinResetTime");
        utils.removeScriptVar(self, "fltMaxResetTime");
        utils.removeScriptVar(self, "strWaves");
        utils.removeScriptVar(self, "strLootLookup");
        utils.removeScriptVar(self, "intMinCredits");
        utils.removeScriptVar(self, "intMaxCredits");
        utils.removeScriptVar(self, "fltItemChange");
        utils.removeScriptVar(self, "intRolls");
        utils.removeScriptVar(self, "fltMinCircleDistance");
        utils.removeScriptVar(self, "fltMaxCircleDistance");
        utils.removeScriptVar(self, "trTestLocation");
        utils.removeScriptVar(self, "intActivationPhase");
        utils.removeScriptVar(self, "strAsteroidSpawnerName");
        utils.removeScriptVar(self, "strAsteroidType");
        if (boolDestroySpawners)
        {
            obj_id[] objPatrolPoints = utils.getObjIdArrayScriptVar(self, "objPatrolPoints");
            if (objPatrolPoints != null)
            {
                for (int intI = 0; intI < objPatrolPoints.length; intI++)
                {
                    destroyObject(objPatrolPoints[intI]);
                }
            }
        }
        return;
    }
    public void writeAsteroidSpawner(obj_id self) throws InterruptedException
    {
        sendSystemMessageTestingOnly(self, "Writing asteroid spawner");
        transform trTest = getTransform_o2p(getPilotedShip(self));
        if (utils.hasScriptVar(self, "trTestLocation"))
        {
            trTest = utils.getTransformScriptVar(self, "trTestLocation");
            utils.removeScriptVar(self, "trTestLocation");
        }
        obj_id objSpawner = createObject("object/tangible/space/content_infrastructure/basic_spawner.iff", trTest, null);
        String strSpawnerType = utils.getStringScriptVar(self, "strSpawnerType");
        setObjVar(objSpawner, "strSpawnerType", strSpawnerType);
        String strSpawnerName = utils.getStringScriptVar(self, "strSpawnerName");
        setObjVar(objSpawner, "strSpawnerName", strSpawnerName);
        String strAsteroidType = utils.getStringScriptVar(self, "strAsteroidType");
        setObjVar(objSpawner, "strAsteroidType", strAsteroidType);
        String strAsteroidSpawnerName = utils.getStringScriptVar(self, "strAsteroidSpawnerName");
        setObjVar(objSpawner, "strAsteroidSpawnerName", strAsteroidSpawnerName);
        int intMinResourcePool = utils.getIntScriptVar(self, "intMinResourcePool");
        setObjVar(objSpawner, "intMinResourcePool", intMinResourcePool);
        int intMaxResourcePool = utils.getIntScriptVar(self, "intMaxResourcePool");
        setObjVar(objSpawner, "intMaxResourcePool", intMaxResourcePool);
        int intDangerLevel = utils.getIntScriptVar(self, "intDangerLevel");
        setObjVar(objSpawner, "intDangerLevel", intDangerLevel);
        int intDangerPct = utils.getIntScriptVar(self, "intDangerPct");
        setObjVar(objSpawner, "intDangerPct", intDangerPct);
    }
    public void writeSpawner(obj_id self) throws InterruptedException
    {
        sendSystemMessageTestingOnly(self, "Writing spawner");
        transform trTest = getTransform_o2p(getPilotedShip(self));
        if (utils.hasScriptVar(self, "trTestLocation"))
        {
            trTest = utils.getTransformScriptVar(self, "trTestLocation");
            utils.removeScriptVar(self, "trTestLocation");
        }
        obj_id objSpawner = createObject("object/tangible/space/content_infrastructure/basic_spawner.iff", trTest, null);
        String strSpawnerType = utils.getStringScriptVar(self, "strSpawnerType");
        setObjVar(objSpawner, "strSpawnerType", strSpawnerType);
        String strSpawnerName = utils.getStringScriptVar(self, "strSpawnerName");
        setObjVar(objSpawner, "strSpawnerName", strSpawnerName);
        if (strSpawnerType.equals("generic"))
        {
            String[] strSpawns = utils.getStringArrayScriptVar(self, "strSpawns");
            float fltMinSpawnDistance = utils.getFloatScriptVar(self, "fltMinSpawnDistance");
            float fltMaxSpawnDistance = utils.getFloatScriptVar(self, "fltMaxSpawnDistance");
            float fltMinSpawnTime = utils.getFloatScriptVar(self, "fltMinSpawnTime");
            float fltMaxSpawnTime = utils.getFloatScriptVar(self, "fltMaxSpawnTime");
            int intSpawnCount = utils.getIntScriptVar(self, "intSpawnCount");
            setObjVar(objSpawner, "fltMinSpawnDistance", fltMinSpawnDistance);
            setObjVar(objSpawner, "fltMaxSpawnDistance", fltMaxSpawnDistance);
            setObjVar(objSpawner, "fltMinSpawnTime", fltMinSpawnTime);
            setObjVar(objSpawner, "fltMaxSpawnTime", fltMaxSpawnTime);
            objvar_mangle.setMangledStringArrayObjVar(objSpawner, "strSpawns", strSpawns);
            setObjVar(objSpawner, "intSpawnCount", intSpawnCount);
        }
        else if (strSpawnerType.equals("wave"))
        {
            String[] strWaves = utils.getStringArrayScriptVar(self, "strWaves");
            float fltMinSpawnDistance = utils.getFloatScriptVar(self, "fltMinSpawnDistance");
            float fltMaxSpawnDistance = utils.getFloatScriptVar(self, "fltMaxSpawnDistance");
            float fltMinSpawnTime = utils.getFloatScriptVar(self, "fltMinwaveSpawnTime");
            float fltMaxSpawnTime = utils.getFloatScriptVar(self, "fltMaxWaveSpawnTime");
            float fltMinResetTime = utils.getFloatScriptVar(self, "fltMinResetTime");
            float fltMaxResetTime = utils.getFloatScriptVar(self, "fltMaxResetTime");
            int intSpawnCount = utils.getIntScriptVar(self, "intSpawnCount");
            setObjVar(objSpawner, "fltMinSpawnDistance", fltMinSpawnDistance);
            setObjVar(objSpawner, "fltMaxSpawnDistance", fltMaxSpawnDistance);
            setObjVar(objSpawner, "fltMinWaveSpawnTime", fltMinSpawnTime);
            setObjVar(objSpawner, "fltMaxWaveSpawnTime", fltMaxSpawnTime);
            objvar_mangle.setMangledStringArrayObjVar(objSpawner, "strWaves", strWaves);
            setObjVar(objSpawner, "fltMinResetTime", fltMinResetTime);
            setObjVar(objSpawner, "fltMaxResetTime", fltMaxResetTime);
        }
        if (utils.hasScriptVar(self, "intActivationPhase"))
        {
            setObjVar(objSpawner, "intActivationPhase", utils.getIntScriptVar(self, "intActivationPhase"));
        }
        String strDefaultBehavior = utils.getStringScriptVar(self, "strDefaultBehavior");
        setObjVar(objSpawner, "strDefaultBehavior", strDefaultBehavior);
        if (strDefaultBehavior.equals("loiter"))
        {
            float fltMinLoiterDistance = utils.getFloatScriptVar(self, "fltMinLoiterDistance");
            float fltMaxLoiterDistance = utils.getFloatScriptVar(self, "fltMaxLoiterDistance");
            setObjVar(objSpawner, "fltMinLoiterDistance", fltMinLoiterDistance);
            setObjVar(objSpawner, "fltMaxLoiterDistance", fltMaxLoiterDistance);
        }
        else if ((strDefaultBehavior.equals("patrol")) || (strDefaultBehavior.equals("patrolNoRecycle")))
        {
            if (utils.hasScriptVar(self, "strPatrolPoints"))
            {
                obj_id[] objPatrolPoints = utils.getObjIdArrayScriptVar(self, "objPatrolPoints");
                transform[] trPatrolPoints = utils.getTransformArrayScriptVar(self, "trPatrolPoints");
                String[] strPatrolPoints = utils.getStringArrayScriptVar(self, "strPatrolPoints");
                utils.setScriptVar(objSpawner, "trPatrolPoints", trPatrolPoints);
                utils.setScriptVar(objSpawner, "objPatrolPoints", objPatrolPoints);
                objvar_mangle.setMangledStringArrayObjVar(objSpawner, "strPatrolPoints", strPatrolPoints);
            }
            else 
            {
                LOG("space", "Patrol behavior");
                String strPatrolPointName = utils.getStringScriptVar(self, "strPatrolPointName");
                Vector strPatrolPoints = new Vector();
                strPatrolPoints.setSize(0);
                Vector trPatrolPoints = new Vector();
                trPatrolPoints.setSize(0);
                obj_id[] objPatrolPoints = utils.getObjIdArrayScriptVar(self, "objPatrolPoints");
                LOG("space", "patrol points lnegth is " + objPatrolPoints.length);
                for (int intI = 0; intI < objPatrolPoints.length; intI++)
                {
                    String strTest = strPatrolPointName + "_" + (intI + 1);
                    strPatrolPoints = utils.addElement(strPatrolPoints, strTest);
                    setObjVar(objPatrolPoints[intI], "strName", strTest);
                    trPatrolPoints = utils.addElement(trPatrolPoints, getTransform_o2p(objPatrolPoints[intI]));
                }
                if (strPatrolPoints.size() > 0)
                {
                    String[] strFixedPoints = new String[strPatrolPoints.size()];
                    for (int intI = 0; intI < strPatrolPoints.size(); intI++)
                    {
                        String strFoo = ((String)strPatrolPoints.get(intI));
                        strFixedPoints[intI] = strFoo;
                    }
                    objvar_mangle.setMangledStringArrayObjVar(objSpawner, "strPatrolPoints", strFixedPoints);
                }
                LOG("space", "setting patrol points array script var");
                utils.setScriptVar(objSpawner, "trPatrolPoints", trPatrolPoints);
                LOG("space", "set patro point array script var");
                utils.setScriptVar(objSpawner, "objPatrolPoints", objPatrolPoints);
            }
        }
        if ((strDefaultBehavior.equals("patrolFixedCircle")) || (strDefaultBehavior.equals("patrolRandomPath")))
        {
            float fltMinCircleDistance = utils.getFloatScriptVar(self, "fltMinCircleDistance");
            float fltMaxCircleDistance = utils.getFloatScriptVar(self, "fltMaxCircleDistance");
            setObjVar(objSpawner, "fltMinCircleDistance", fltMinCircleDistance);
            setObjVar(objSpawner, "fltMaxCircleDistance", fltMaxCircleDistance);
        }
        clearSpawnerScriptVar(self, false);
    }
    public boolean isValidWave(String strWave) throws InterruptedException
    {
        String strFileName = "datatables/space_content/spawners/waves.iff";
        String[] strColumn = dataTableGetStringColumnNoDefaults(strFileName, strWave);
        if (strColumn == null)
        {
            LOG("space", "Bad column name of " + strColumn);
            return false;
        }
        for (int intI = 0; intI < strColumn.length; intI++)
        {
            if (!isValidMobString(strColumn[intI]))
            {
                sendSystemMessageTestingOnly(getSelf(), "BAD DATA IN WAVE FILE, COLUMN " + strWave + " entry is " + strColumn[intI]);
                return false;
            }
        }
        return true;
    }
    public int handleColorize(obj_id self, dictionary params) throws InterruptedException
    {
        int bp = sui.getIntButtonPressed(params);
        if (bp == sui.BP_CANCEL)
        {
            return SCRIPT_CONTINUE;
        }
        obj_id player = sui.getPlayerId(params);
        if (!isIdValid(player))
        {
            return SCRIPT_CONTINUE;
        }
        String template = utils.getTemplateFilenameNoPath(self);
        String index = "/shared_owner/index_color_1";
        if (index != null && !index.equals("") && !index.equals("none"))
        {
            int idx = sui.getColorPickerIndex(params);
            if (idx > -1)
            {
                obj_id ship = getPilotedShip(player);
                if (isIdValid(ship))
                {
                    hue.setColor(ship, index, idx);
                }
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int specifyPlayerPatrolName(obj_id self, dictionary params) throws InterruptedException
    {
        int intButton = sui.getIntButtonPressed(params);
        if (intButton == sui.BP_CANCEL)
        {
            sendSystemMessageTestingOnly(self, "Cancelling player patrol point creation.");
            return SCRIPT_CONTINUE;
        }
        String name = sui.getInputBoxText(params);
        if (name.equals(""))
        {
            sui.inputbox(self, self, "BAD NAME :\nWhat is the name of this player patrol point?", sui.OK_CANCEL, "Test", sui.INPUT_NORMAL, null, "specifyPlayerPatrolName", null);
            return SCRIPT_CONTINUE;
        }
        obj_id pp = createObject("object/tangible/space/content_infrastructure/quest_nav_point.iff", getTransform_o2p(getPilotedShip(self)), null);
        setObjVar(pp, "nav_name", name);
        sendSystemMessageTestingOnly(self, "Created player patrol point: " + name);
        return SCRIPT_CONTINUE;
    }
    public int specifyEscortPointName(obj_id self, dictionary params) throws InterruptedException
    {
        int intButton = sui.getIntButtonPressed(params);
        if (intButton == sui.BP_CANCEL)
        {
            sendSystemMessageTestingOnly(self, "Cancelling escort point creation.");
            return SCRIPT_CONTINUE;
        }
        String name = sui.getInputBoxText(params);
        if (name.equals(""))
        {
            sui.inputbox(self, self, "BAD NAME :\nWhat is the name of this escort point?", sui.OK_CANCEL, "Test", sui.INPUT_NORMAL, null, "specifyEscortPointName", null);
            return SCRIPT_CONTINUE;
        }
        obj_id pp = createObject("object/tangible/space/content_infrastructure/quest_escort_point.iff", getTransform_o2p(getPilotedShip(self)), null);
        setObjVar(pp, "nav_name", name);
        sendSystemMessageTestingOnly(self, "Created escort point: " + name);
        return SCRIPT_CONTINUE;
    }
    public int handleDebugOutput(obj_id self, dictionary params) throws InterruptedException
    {
        String strSpam = params.getString("strSpam");
        sendSystemMessageTestingOnly(self, "strSpam");
        return SCRIPT_CONTINUE;
    }
    public boolean isDumpable(obj_id objObject, boolean boolDumpCells) throws InterruptedException
    {
        if (isPlayer(objObject))
        {
            return false;
        }
        if (player_structure.isBuilding(objObject) || player_structure.isInstallation(objObject))
        {
            return true;
        }
        if (space_utils.isPlayerControlledShip(objObject))
        {
            return false;
        }
        String strName = getTemplateName(objObject);
        int intIndex = strName.indexOf("object/cell");
        if (intIndex > -1)
        {
            return false;
        }
        if (hasObjVar(objObject, "intNoDump"))
        {
            return false;
        }
        location locTest = getLocation(objObject);
        if (!boolDumpCells)
        {
            if (isIdValid(locTest.cell))
            {
                return false;
            }
        }
        if (hasScript(objObject, "systems.spawning.spawned_tracker"))
        {
            return false;
        }
        return true;
    }
    public boolean isGroundDumpable(obj_id objObject, boolean boolDumpCells) throws InterruptedException
    {
        if (isPlayer(objObject))
        {
            return false;
        }
        if (isMob(objObject))
        {
            return false;
        }
        if (player_structure.isBuilding(objObject) || player_structure.isInstallation(objObject))
        {
            return false;
        }
        if (space_utils.isPlayerControlledShip(objObject))
        {
            return false;
        }
        String strName = getTemplateName(objObject);
        int intIndex = strName.indexOf("object/cell");
        LOG("space", "name is " + strName);
        if (intIndex > -1)
        {
            return false;
        }
        if (hasObjVar(objObject, "intNoDump"))
        {
            return false;
        }
        location locTest = getLocation(objObject);
        if (!boolDumpCells)
        {
            if (isIdValid(locTest.cell))
            {
                return false;
            }
        }
        return true;
    }
    public obj_id[] getBuildingContents(obj_id objObject) throws InterruptedException
    {
        Vector objContents = new Vector();
        objContents.setSize(0);
        obj_id[] objCells = getContents(objObject);
        for (int intI = 0; intI < objCells.length; intI++)
        {
            obj_id[] objTestContents = getContents(objCells[intI]);
            if ((objTestContents != null) && (objTestContents.length > 0))
            {
                for (int intJ = 0; intJ < objTestContents.length; intJ++)
                {
                    objContents = utils.addElement(objContents, objTestContents[intJ]);
                }
            }
        }
        obj_id[] _objContents = new obj_id[0];
        if (objContents != null)
        {
            _objContents = new obj_id[objContents.size()];
            objContents.toArray(_objContents);
        }
        return _objContents;
    }
    public void checkComponentValidity(obj_id objItem, String strTemplate, String strColumn, obj_id self) throws InterruptedException
    {
        if (isIdValid(objItem))
        {
            int intIndex = strTemplate.indexOf("object/tangible/ship/components");
            if (intIndex > -1)
            {
                String strComponentType = space_crafting.getShipComponentStringType(objItem);
                if ((strComponentType != null) && (!strComponentType.equals("")))
                {
                    dictionary dctParams = dataTableGetRow("datatables/ship/components/" + strComponentType + ".iff", getTemplateName(objItem));
                    if (dctParams == null)
                    {
                        sendSystemMessageTestingOnly(self, "Loot Type: " + strColumn);
                        sendSystemMessageTestingOnly(self, "Item Template: " + strTemplate);
                        sendSystemMessageTestingOnly(self, "NO ENTRY IN LOOT TABLE FOR TYPE " + strComponentType);
                        debugConsoleMsg(self, "Loot Type: " + strColumn);
                        debugConsoleMsg(self, "Item Template: " + strTemplate);
                        debugConsoleMsg(self, "NO ENTRY IN LOOT TABLE FOR TYPE " + strComponentType);
                    }
                }
                else 
                {
                    sendSystemMessageTestingOnly(self, "Loot Type: " + strColumn);
                    sendSystemMessageTestingOnly(self, "Item Template: " + strTemplate);
                    sendSystemMessageTestingOnly(self, "NO COMPONENT TYPE SETUP!@!@");
                    debugConsoleMsg(self, "Loot Type: " + strColumn);
                    debugConsoleMsg(self, "Item Template: " + strTemplate);
                    debugConsoleMsg(self, "NO COMPONENT TYPE SETUP!@!@");
                }
            }
            else 
            {
                destroyObject(objItem);
                return;
            }
        }
        else 
        {
            sendSystemMessageTestingOnly(self, "Loot Type: " + strColumn);
            sendSystemMessageTestingOnly(self, "Item Template: " + strTemplate);
            sendSystemMessageTestingOnly(self, "BAD OBJECT TEMPLATE");
            debugConsoleMsg(self, "Loot Type: " + strColumn);
            debugConsoleMsg(self, "Item Template: " + strTemplate);
            debugConsoleMsg(self, "BAD OBJECT TEMPLATE");
        }
        destroyObject(objItem);
    }
    public obj_id[] getShipContents(obj_id objObject) throws InterruptedException
    {
        Vector objContents = new Vector();
        objContents.setSize(0);
        obj_id[] objCells = getContents(objObject);
        for (int intI = 0; intI < objCells.length; intI++)
        {
            obj_id[] objTestContents = getContents(objCells[intI]);
            if ((objTestContents != null) && (objTestContents.length > 0))
            {
                for (int intJ = 0; intJ < objTestContents.length; intJ++)
                {
                    objContents = utils.addElement(objContents, objTestContents[intJ]);
                }
            }
        }
        obj_id[] _objContents = new obj_id[0];
        if (objContents != null)
        {
            _objContents = new obj_id[objContents.size()];
            objContents.toArray(_objContents);
        }
        return _objContents;
    }
    public int makeDungeonObjects(obj_id self, dictionary params) throws InterruptedException
    {
        return SCRIPT_CONTINUE;
    }
}
