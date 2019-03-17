package script.test;

import script.*;
import script.combat_engine.combat_data;
import script.library.*;

import java.util.Vector;

public class thicks_test extends script.base_script
{
    public thicks_test()
    {
    }
    public static String[] OPTIONS = 
    {
        "listScripts",
        "clearScript",
        "clearAllScripts",
        "listObjVars",
        "clearObjVar",
        "listAllObjVar",
        "doClientEffect EFFECT",
        "spawn SPAWN"
    };
    public static final int listScripts = 0;
    public static final int clearScript = 1;
    public static final int clearAllScripts = 2;
    public static final int listObjVars = 3;
    public static final int clearObjVar = 4;
    public static final int clearAllObjVars = 5;
    public static final int doClientEffect = 6;
    public static final int spawnCreature = 7;
    public int OnAttach(obj_id self) throws InterruptedException
    {
        if (!isGod(self) || getGodLevel(self) < 50 || !isPlayer(self)) {
            detachScript(self, "test.thicks_test");
        }
        return SCRIPT_CONTINUE;
    }
    public int OnMoveMoving(obj_id self) throws InterruptedException
    {
        setState(self, STATE_STUNNED, false);
        setState(self, STATE_FROZEN, false);
        return SCRIPT_CONTINUE;
    }
    public int OnLogin(obj_id self) throws InterruptedException
    {
        setState(self, STATE_STUNNED, false);
        setState(self, STATE_FROZEN, false);
        return SCRIPT_CONTINUE;
    }
    public int OnSpeaking(obj_id self, String text) throws InterruptedException
    {
        if (text.equals("set"))
        {
            overrideDefaultAttack(self, "hoth_speeder_bolt");
        }
        if (text.equals("ping"))
        {
            obj_id record = utils.getObjIdScriptVar(self, "messageTo.recordObject");
            if (!isIdValid(record))
            {
                record = instance.getAreaInstanceController(self);
            }
            dictionary dict = new dictionary();
            dict.put("sender", self);
            messageTo(record, "ping", dict, 0.0f, false);
        }
        if (text.equals("clear"))
        {
            setState(self, STATE_COVER, false);
        }
        if (!isGod(self))
        {
            return SCRIPT_CONTINUE;
        }
        String[] parse = split(text, ' ');
        if (parse[0].equals("showGodWindow"))
        {
            showGodWindow(self);
        }
        if (parse[0].equals("help"))
        {
            showHelp();
        }
        if (parse[0].equals("listScripts"))
        {
            listScripts(self);
        }
        if (parse[0].equals("clearScript"))
        {
            clearScript(self);
        }
        if (parse[0].equals("clearAllScripts"))
        {
            clearAllScripts(self);
        }
        if (parse[0].equals("doClientEffect"))
        {
            runDoClientEffect(self, parse);
        }
        if (parse[0].equals("spawn"))
        {
            runSpawnCreature(self, parse);
        }
        if (parse[0].equals("find"))
        {
            runFind(self, parse);
        }
        if (parse[0].equals("warpToFind"))
        {
            runWarpToFind(self, parse);
        }
        if (parse[0].equals("enterFind"))
        {
            runEnterFind(self, parse);
        }
        if (parse[0].equals("buildingWarp"))
        {
            runInteriorWarp(self, parse);
        }
        if (parse[0].equals("showTime"))
        {
            runShowTime(self, parse);
        }
        if (parse[0].equals("isScentMasked"))
        {
            runIsScentMasked(self, parse);
        }
        if (parse[0].equals("validateConceal"))
        {
            runValidateConceal(self, parse);
        }
        if (parse[0].equals("getBuildingScripts"))
        {
            runGetBuildingScripts(self, parse);
        }
        if (parse[0].equals("getBuildingObjVars"))
        {
            runGetBuildingObjVars(self, parse);
        }
        if (parse[0].equals("getLoc"))
        {
            runTargetLocation(self, parse);
        }
        if (parse[0].equals("getTop"))
        {
            runGetTopMostContainer(self, parse);
        }
        if (parse[0].equals("buildoutLoc"))
        {
            runBuildoutLoc(self, parse);
        }
        if (parse[0].equals("messageTo"))
        {
            sendMessageTo(self, parse);
        }
        if (parse[0].equals("recreate"))
        {
            runDestroyAndRecreate(self, parse);
        }
        if (parse[0].equals("buildingMessage"))
        {
            runMessageToBuilding(self, parse);
        }
        if (parse[0].equals("deathTouch"))
        {
            runDeathTouch(self, parse);
        }
        if (parse[0].equals("buffMe"))
        {
            runBuffMe(self, parse);
        }
        if (parse[0].equals("buffYou"))
        {
            runBuffYou(self, parse);
        }
        if (parse[0].equals("setRecordObject"))
        {
            runSetRecordObject(self, parse);
        }
        if (parse[0].equals("recordTo"))
        {
            runRecordTo(self, parse);
        }
        if (parse[0].equals("getCellId"))
        {
            runGetCellId(self, parse);
        }
        if (parse[0].equals("clearLockout"))
        {
            runClearAllLockoutTimers(self, parse);
        }
        if (parse[0].equals("applyBuff"))
        {
            runApplyBuff(self, parse);
        }
        if (parse[0].equals("doAnim"))
        {
            runDoAnim(self, parse);
        }
        if (parse[0].equals("recordLoc"))
        {
            runDoRecordBuildoutLoc(self, parse);
        }
        if (parse[0].equals("playMusic"))
        {
            runPlayMusic(self, parse);
        }
        if (parse[0].equals("doPrt"))
        {
            runDoPrt(self, parse);
        }
        if (parse[0].equals("spawnAk"))
        {
            runSpawnAkPrime(self, parse);
        }
        if (parse[0].equals("badge"))
        {
            runBadgeOperation(self, parse);
        }
        if (parse[0].equals("formation"))
        {
            runSetFollowInFormation(self, parse);
        }
        if (parse[0].equals("deleteObject"))
        {
            runDeleteObject(self, parse);
        }
        if (parse[0].equals("dumpArea"))
        {
            runRoriConversion(self, parse);
        }
        if (parse[0].equals("getModLoc"))
        {
            getModLoc(self);
        }
        if (parse[0].equals("calculateWarp"))
        {
            calculateWarp(self, parse);
        }
        if (parse[0].equals("getRecordDistance"))
        {
            calculateDistance(self, parse);
        }
        if (parse[0].equals("deleteRegion"))
        {
            deleteRegion(self, parse);
        }
        if (parse[0].equals("broadcastMessage"))
        {
            broadcastMessageTo(self, parse);
        }
        if (parse[0].equals("getSpaceLoc"))
        {
            get3dSpaceLoc(self, parse);
        }
        if (parse[0].equals("query"))
        {
            runQuery(self, parse);
        }
        if (parse[0].equals("getBaseCount"))
        {
            getBaseCount(self, parse);
        }
        if (parse[0].equals("getGameTime"))
        {
            calculateGameTime(self, parse);
        }
        if (parse[0].equals("testBatch"))
        {
            runTestBatch(self, parse);
        }
        if (parse[0].equals("makeSpaceOvert"))
        {
            runMakeSpaceOvert(self, parse);
        }
        if (parse[0].equals("getPlanetObject"))
        {
            runGetPlanetObject(self, parse);
        }
        if (parse[0].equals("respec"))
        {
            runRespec(self, parse);
        }
        if (parse[0].equals("getSkillTemplate"))
        {
            runGetSkillTemplate(self, parse);
        }
        if (parse[0].equals("queueCommand"))
        {
            runQueueCommand(self, parse);
        }
        if (parse[0].equals("setCooldown"))
        {
            runSetCooldown(self, parse);
        }
        if (parse[0].equals("getWeapon"))
        {
            runGetWeapon(self, parse);
        }
        if (parse[0].equals("validateInstance"))
        {
            runVerifySession(self, parse);
        }
        if (parse[0].equals("getHateList"))
        {
            runGetHateList(self, parse);
        }
        if (parse[0].equals("getRespect"))
        {
            runGetRespect(self, parse);
        }
        if (parse[0].equals("makeDecoy"))
        {
            runMakeDecoy(self, parse);
        }
        if (parse[0].equals("comm_link"))
        {
            runCommLink(self, parse);
        }
        if (parse[0].equals("prepCave"))
        {
            runPrepCave(self, parse);
        }
        if (parse[0].equals("swarmTarget"))
        {
            runSwarmTarget(self, parse);
        }
        if (parse[0].equals("setMaster"))
        {
            runSetMaster(self, parse);
        }
        if (parse[0].equals("convert"))
        {
            runConvert(self, parse);
        }
        if (parse[0].equals("dumpToTable"))
        {
            dumpToTable(self, parse);
        }
        if (parse[0].equals("recreateTable"))
        {
            recreateTable(self, parse);
        }
        if (parse[0].equals("getCommandList"))
        {
            runGetCommmandList(self, parse);
        }
        if (parse[0].equals("cell"))
        {
            if (parse.length == 1)
            {
                String[] params = new String[2];
                params[0] = "getCellList";
                params[1] = "" + getTopMostContainer(self);
                runGetCellList(self, params);
            }
            if (parse.length == 2)
            {
                String[] params = new String[3];
                params[0] = "enterNamedCell";
                params[1] = "" + getTopMostContainer(self);
                params[2] = parse[1];
                runEnterCell(self, params);
            }
        }
        if (parse[0].equals("getCellList"))
        {
            runGetCellList(self, parse);
        }
        if (parse[0].equals("enterNamedCell"))
        {
            runEnterCell(self, parse);
        }
        if (parse[0].equals("doIt"))
        {
            runIt(self, parse);
        }
        if (parse[0].equals("getBell"))
        {
            runGetBell(self, parse);
        }
        if (parse[0].equals("setHover"))
        {
            runSetHover(self, parse);
        }
        if (parse[0].equals("testSlope"))
        {
            runTestSlope(self, parse);
        }
        if (parse[0].equals("changeY"))
        {
            runAlterHeight(self, parse);
        }
        if (parse[0].equals("testBool"))
        {
            runTestBool(self, parse);
        }
        if (parse[0].equals("addAttrib"))
        {
            runAddAttrib(self, parse);
        }
        if (parse[0].equals("lit3"))
        {
            runLit3(self, parse);
        }
        if (parse[0].equals("shield"))
        {
            runShieldMe(self, parse);
        }
        if (parse[0].equals("clearStun"))
        {
            clearStunEffects(self, parse);
        }
        if (parse[0].equals("shazam"))
        {
            makeMeAGod(self, parse);
        }
        if (parse[0].equals("setSlopeMod"))
        {
            runSetSlope(self, parse);
        }
        if (parse[0].equals("setKillMeter"))
        {
            runSetKillMeter(self, parse);
        }
        if (parse[0].equals("setMind"))
        {
            runSetMind(self, parse);
        }
        if (parse[0].equals("getMind"))
        {
            runGetMind(self, parse);
        }
        if (parse[0].equals("snowspeeder"))
        {
            runSetupSnowspeeder(self, parse);
        }
        if (parse[0].equals("recordSet"))
        {
            runSetRecordObjectFromFind(self, parse);
        }
        if (parse[0].equals("shoot"))
        {
            runTestShoot(self, parse);
        }
        if (parse[0].equals("setLoc"))
        {
            runSetLoc(self, parse);
        }
        if (parse[0].equals("dda_set"))
        {
            runSetDda(self, parse);
        }
        if (parse[0].equals("dda_get"))
        {
            runGetDda(self, parse);
        }
        if ((toLower(parse[0])).equals("staticitem"))
        {
            runStaticItemParse(self, parse);
        }
        if ((toLower(parse[0])).equals("staticitemfind"))
        {
            runStaticItemFind(self, parse);
        }
        if ((toLower(parse[0])).equals("staticitemcreate"))
        {
            runStaticItemCreate(self, parse);
        }
        if ((toLower(parse[0])).equals("staticitemlist"))
        {
            runStaticItemShowList(self);
        }
        if ((toLower(parse[0])).equals("staticitemclear"))
        {
            runStaticItemClearList(self);
        }
        if (parse[0].equals("init_vehicle"))
        {
            vehicle.initializeVehicle(getMountId(self));
        }
        if (parse[0].equals("playSound"))
        {
            runPlaySound(self, parse);
        }
        if (parse[0].equals("triggerId"))
        {
            runFireTrigger(self, parse);
        }
        if (parse[0].equals("setCellLabel"))
        {
            runSetCellLabel(self, parse);
        }
        if (parse[0].equals("setId"))
        {
            runSetIdOnInstance(self, parse);
        }
        if (parse[0].equals("clearWp"))
        {
            runClearWaypoints(self, parse);
        }
        if (parse[0].equals("hideFrom"))
        {
            runHideFromClient(self, parse);
        }
        return SCRIPT_CONTINUE;
    }
    public void showHelp() throws InterruptedException
    {
        for (String option : OPTIONS) {
            sendSystemMessageTestingOnly(getSelf(), option);
        }
    }
    public void listScripts(obj_id self) throws InterruptedException
    {
        obj_id target = getLookAtTarget(self);
        String[] scriptList = getScriptList(target);
        for (String s : scriptList) {
            debugSpeakMsg(self, s);
        }
    }
    public void clearScript(obj_id self) throws InterruptedException
    {
        obj_id target = getLookAtTarget(self);
        String[] scriptList = getScriptList(target);
    }
    public void clearAllScripts(obj_id self) throws InterruptedException
    {
        obj_id target = getLookAtTarget(self);
        detachAllScripts(target);
        sendSystemMessageTestingOnly(self, "ALL SCRIPTS REMOVED");
    }
    public int handleClearScript(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id target = params.getObjId("target");
        String script = params.getString("script");
        detachScript(target, script);
        sendSystemMessageTestingOnly(self, "Removing Script(" + script + ") From(" + getName(target) + "/" + target + ")");
        return SCRIPT_CONTINUE;
    }
    public void runDoClientEffect(obj_id self, String[] parse) throws InterruptedException
    {
        if (parse[1].equals("self"))
        {
            String effect = "clienteffect/" + parse[1] + ".cef";
            playClientEffectObj(self, effect, self, "", null, "tag");
            messageTo(self, "clearEffect", null, 10, false);
        }
        else 
        {
            location playLoc = utils.findLocInFrontOfTarget(self, 10.0f);
            String effect = "clienteffect/" + parse[1] + ".cef";
            playClientEffectLoc(getIntendedTarget(self), effect, playLoc, 0.4f);
        }
    }
    public int clearEffect(obj_id self, dictionary params) throws InterruptedException
    {
        stopClientEffectObjByLabel(self, "tag");
        return SCRIPT_CONTINUE;
    }
    public void runSpawnCreature(obj_id self, String[] parse) throws InterruptedException
    {
        location spawnLoc = utils.findLocInFrontOfTarget(self, 30.0f);
        if (parse[1].equals("single"))
        {
            if (parse.length == 4)
            {
                create.object(parse[2], spawnLoc, utils.stringToInt(parse[3]));
            }
            else 
            {
                create.object(parse[2], spawnLoc);
            }
        }
        if (parse[1].equals("group"))
        {
            int intRand = rand(2, 4);
            for (int i = 0; i < intRand; i++)
            {
                if (parse.length == 4)
                {
                    create.object(parse[2], spawnLoc, utils.stringToInt(parse[3]));
                }
                else 
                {
                    create.object(parse[2], spawnLoc);
                }
            }
        }
    }
    public void showGodWindow(obj_id user) throws InterruptedException
    {
        int pid = sui.listbox(user, user, "Select GM Option", OPTIONS, "handleSelectGMCommand");
    }
    public int handleSelectGMCommand(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id player = sui.getPlayerId(params);
        int idx = sui.getListboxSelectedRow(params);
        if (idx == listScripts)
        {
            doScriptListSUI(player);
        }
        if (idx == clearScript)
        {
        }
        if (idx == clearAllScripts)
        {
            clearAllScripts(player);
        }
        if (idx == listObjVars)
        {
        }
        if (idx == clearObjVar)
        {
        }
        if (idx == clearAllObjVars)
        {
        }
        if (idx == doClientEffect)
        {
        }
        if (idx == spawnCreature)
        {
        }
        return SCRIPT_CONTINUE;
    }
    public void doScriptListSUI(obj_id player) throws InterruptedException
    {
        obj_id target = getLookAtTarget(player);
        String[] scriptList = getScriptList(target);
        int pid = sui.listbox(player, player, "SCRIPTS ON TARGET", scriptList, "handleRecievedScriptList");
    }
    public void runFind(obj_id self, String[] parse) throws InterruptedException
    {
        float distance = 32000.0f;
        obj_id closest = null;
        if (parse[1].equals("object"))
        {
            obj_id[] objects = getObjectsInRange(self, 32000);
            for (obj_id object : objects) {
                if ((getTemplateName(object)).equals(parse[2]) && object != self) {
                    float range = getDistance(self, object);
                    if (range < distance) {
                        distance = range;
                        closest = object;
                    }
                }
            }
            location foundLoc = getLocation(closest);
            utils.setScriptVar(self, "objectLoc", foundLoc);
            utils.setScriptVar(self, "objectId", closest);
            sendSystemMessageTestingOnly(self, "Object(" + getTemplateName(closest) + "/" + closest + ") found at: " + foundLoc);
            return;
        }
        if (parse[1].equals("obj_id"))
        {
            obj_id[] objects = getObjectsInRange(self, 32000);
            obj_id passedObject = utils.stringToObjId(parse[2]);
            debugSpeakMsg(self, "Passed obj_id was: " + passedObject);
            for (obj_id object : objects) {
                if (object == passedObject) {
                    float range = getDistance(self, object);
                    if (range < distance) {
                        distance = range;
                        closest = object;
                    }
                }
            }
            location foundLoc = getLocation(closest);
            utils.setScriptVar(self, "objectLoc", foundLoc);
            utils.setScriptVar(self, "objectId", closest);
            sendSystemMessageTestingOnly(self, "Object(" + getTemplateName(closest) + "/" + closest + ") found at: " + foundLoc);
            return;
        }
        if (parse[1].equals("indexOf"))
        {
            obj_id[] objects = getObjectsInRange(self, 32000);
            for (obj_id object : objects) {
                if ((getTemplateName(object)).contains(parse[2]) && object != self) {
                    float range = getDistance(self, object);
                    if (range < distance) {
                        distance = range;
                        closest = object;
                    }
                }
            }
            location foundLoc = getLocation(closest);
            utils.setScriptVar(self, "objectLoc", foundLoc);
            utils.setScriptVar(self, "objectId", closest);
            sendSystemMessageTestingOnly(self, "Object(" + getTemplateName(closest) + "/" + closest + ") found at: " + foundLoc);
            return;
        }
        if (parse[1].equals("indexOfAll"))
        {
            obj_id[] objects = getObjectsInRange(self, 32000);
            location foundLoc;
            for (obj_id object : objects) {
                if ((getTemplateName(object)).contains(parse[2]) && object != self) {
                    float range = getDistance(self, object);
                    foundLoc = getLocation(object);
                    sendSystemMessageTestingOnly(self, "Object(" + getTemplateName(object) + "/" + object + ") found at: " + foundLoc);
                    if (range < distance) {
                        distance = range;
                        closest = object;
                    }
                }
            }
            foundLoc = getLocation(closest);
            utils.setScriptVar(self, "objectLoc", foundLoc);
            utils.setScriptVar(self, "objectId", closest);
            sendSystemMessageTestingOnly(self, "Closest Object(" + getTemplateName(closest) + "/" + closest + ") found at: " + foundLoc);
            return;
        }
        if (parse[1].equals("spawnId"))
        {
            obj_id instance_id = instance.getAreaInstanceController(self);
            if (!isIdValid(instance_id))
            {
                sendSystemMessage(self, "Invalid instance_id.", "");
            }
            if (parse.length < 3)
            {
                sendSystemMessage(self, "No spawn id specified.", "");
                return;
            }
            sendSystemMessage(self, "spawnId search = " + parse[2], "");
            location foundLoc;
            obj_id[] spawnIdObjects = trial.getObjectsInInstanceBySpawnId(instance_id, parse[2]);
            if (spawnIdObjects != null && spawnIdObjects.length > 0)
            {
                for (obj_id spawnIdObject : spawnIdObjects) {
                    float range = getDistance(self, spawnIdObject);
                    foundLoc = getLocation(spawnIdObject);
                    sendSystemMessage(self, "Object( " + getTemplateName(spawnIdObject) + " / " + spawnIdObject + " ) found at: " + foundLoc, "");
                    if (range < distance) {
                        distance = range;
                        closest = spawnIdObject;
                    }
                }
                foundLoc = getLocation(closest);
                utils.setScriptVar(self, "objectLoc", foundLoc);
                utils.setScriptVar(self, "objectId", closest);
                sendSystemMessageTestingOnly(self, "Closest Object( " + getTemplateName(closest) + " / " + closest + " ) found at: " + foundLoc);
                return;
            }
        }
        sendSystemMessageTestingOnly(self, "No objects of that type found");
        return;
    }
    public void runWarpToFind(obj_id self, String[] parse) throws InterruptedException
    {
        if (!utils.hasScriptVar(self, "objectLoc"))
        {
            sendSystemMessageTestingOnly(self, "No found loc on self");
            return;
        }
        obj_id toEnter = trial.getTop(utils.getObjIdScriptVar(self, "objectId"));
        obj_id[] cells = getCellIds(toEnter);
        boolean isPob = false;
        if (cells != null && cells.length > 0)
        {
            isPob = true;
        }
        location objLoc = utils.getLocationScriptVar(self, "objectLoc");
        if (!isPob)
        {
            location warpTo = utils.getLocationScriptVar(self, "objectLoc");
            warpPlayer(self, warpTo.area, warpTo.x, warpTo.y, warpTo.z, null, warpTo.x, warpTo.y, warpTo.z);
        }
        else 
        {
            obj_id cellId = objLoc.cell;
            String cellName = getCellName(cellId);
            location cellLoc = getGoodLocation(toEnter, cellName);
            warpPlayer(self, cellLoc.area, cellLoc.x, cellLoc.y, cellLoc.z, toEnter, cellName, cellLoc.x, cellLoc.y, cellLoc.z, "nullCallback", false);
        }
    }
    public void runEnterFind(obj_id self, String[] parse) throws InterruptedException
    {
        if (!utils.hasScriptVar(self, "objectId"))
        {
            sendSystemMessageTestingOnly(self, "No object id on self");
            return;
        }
        obj_id toEnter = utils.getObjIdScriptVar(self, "objectId");
        obj_id[] cells = getCellIds(toEnter);
        if (cells == null || cells.length == 0)
        {
            sendSystemMessageTestingOnly(self, "There are no cells on this object");
            return;
        }
        String cellName = getCellName(cells[0]);
        location cellLoc = getGoodLocation(toEnter, cellName);
        warpPlayer(self, cellLoc.area, cellLoc.x, cellLoc.y, cellLoc.z, toEnter, cellName, cellLoc.x, cellLoc.y, cellLoc.z, "nullCallback", false);
    }
    public void runInteriorWarp(obj_id self, String[] parse) throws InterruptedException
    {
        if (getTopMostContainer(self) == null)
        {
            return;
        }
        location cellLoc = getGoodLocation(getTopMostContainer(self), parse[1]);
        if (cellLoc == null)
        {
            sendSystemMessageTestingOnly(self, "No floor in target cell: " + parse[1]);
            return;
        }
        warpPlayer(self, cellLoc.area, cellLoc.x, cellLoc.y, cellLoc.z, getTopMostContainer(self), parse[1], cellLoc.x, cellLoc.y, cellLoc.z, "nullCallback", false);
    }
    public void runShowTime(obj_id self, String[] parse) throws InterruptedException
    {
        String time = utils.formatTime(getGameTime());
        sendSystemMessageTestingOnly(self, time);
    }
    public void runIsScentMasked(obj_id self, String[] parse) throws InterruptedException
    {
        if (getState(self, STATE_MASK_SCENT) == 1)
        {
            sendSystemMessageTestingOnly(self, "You are scent masked");
        }
        else 
        {
            sendSystemMessageTestingOnly(self, "You are not scent masked");
        }
    }
    public void runValidateConceal(obj_id self, String[] parse) throws InterruptedException
    {
        if (utils.hasScriptVar(self, "scentmask.count"))
        {
            sendSystemMessageTestingOnly(self, "Count: " + utils.getIntScriptVar(self, "scentmask.count"));
        }
        if (utils.hasScriptVar(self, "scentmask.camokit"))
        {
            sendSystemMessageTestingOnly(self, "Planet: " + utils.getStringScriptVar(self, "scentmask.camokit"));
        }
        if (utils.hasScriptVar(self, "scentmask.camoquality"))
        {
            sendSystemMessageTestingOnly(self, "Quality: " + utils.getIntScriptVar(self, "scentmask.camoquality"));
        }
        if (utils.hasScriptVar(self, "scentmask.camoapply"))
        {
            sendSystemMessageTestingOnly(self, "Player: " + getName(utils.getObjIdScriptVar(self, "scentmask.camoapply")));
        }
    }
    public void runGetBuildingScripts(obj_id self, String[] parse) throws InterruptedException
    {
        obj_id top = getTopMostContainer(self);
        if (isIdValid(top))
        {
            String[] scriptList = getScriptList(top);
            int pid = sui.listbox(self, self, "SCRIPTS ON BUILDING", scriptList, "handleRecievedScriptList");
        }
    }
    public void runGetBuildingObjVars(obj_id player, String[] parse) throws InterruptedException
    {
        obj_id target = getTopMostContainer(player);
        String[] scriptList = getScriptList(target);
        int pid = sui.listbox(player, player, "SCRIPTS ON TARGET", scriptList, "handleRecievedScriptList");
    }
    public void runTargetLocation(obj_id self, String[] parse) throws InterruptedException
    {
        obj_id target = getIntendedTarget(self);
        if (!isIdValid(target))
        {
            sendSystemMessageTestingOnly(self, "You must target something first");
            return;
        }
        location tarLoc = getLocation(target);
        float yaw = getYaw(target);
        if (!isIdValid(tarLoc.cell))
        {
            sendSystemMessageTestingOnly(self, "Location(" + tarLoc.x + ", " + tarLoc.y + ", " + tarLoc.z + ") yaw(" + yaw + ") cell(" + tarLoc.cell + ") on scene (" + tarLoc.area + ")");
        }
        else 
        {
            String cell = utils.getCellName(getTopMostContainer(self), tarLoc.cell);
            sendSystemMessageTestingOnly(self, "Location(" + tarLoc.x + ", " + tarLoc.y + ", " + tarLoc.z + ") yaw(" + yaw + ") cell(" + cell + ") on scene (" + tarLoc.area + ")");
        }
    }
    public void runBuildoutLoc(obj_id self, String[] parse) throws InterruptedException
    {
        if (!hasObjVar(self, "record_table"))
        {
            sendSystemMessageTestingOnly(self, "You do not have the record_table objvar defined");
            return;
        }
        obj_id target = getTarget(self);
        if (!isIdValid(target))
        {
            if (parse.length < 2)
            {
                sendSystemMessageTestingOnly(self, "You must target something first");
                return;
            }
            target = utils.stringToObjId(parse[1]);
            if (!isIdValid(target))
            {
                sendSystemMessageTestingOnly(self, "Invalid Target Specified");
                return;
            }
        }
        String table = getStringObjVar(self, "record_table");
        location tarLoc = getLocation(target);
        String name = getTemplateName(target);
        float yaw = getYaw(target);
        String cell = utils.getCellName(getTopMostContainer(self), tarLoc.cell);
        sendSystemMessageTestingOnly(self, "Location(" + tarLoc.x + ", " + tarLoc.y + ", " + tarLoc.z + ") yaw(" + yaw + ") cell(" + cell + ")");
        dictionary dctRow = new dictionary();
        dctRow.put("spawns", name);
        dctRow.put("loc_x", tarLoc.x);
        dctRow.put("loc_y", tarLoc.y);
        dctRow.put("loc_z", tarLoc.z);
        dctRow.put("room", cell);
        dctRow.put("yaw", yaw);
        datatable.serverDataTableAddRow(table, dctRow);
    }
    public void sendMessageTo(obj_id self, String[] parse) throws InterruptedException
    {
        obj_id target = getTarget(self);
        if (!isIdValid(target))
        {
            target = self;
        }
        String message = parse[1];
        if (parse.length > 2)
        {
            dictionary dict = new dictionary();
            for (int i = 2; i < parse.length; i += 2)
            {
                int k = i + 1;
                dict.put(parse[i], parse[k]);
            }
            messageTo(target, message, dict, 0, false);
        }
        else 
        {
            messageTo(target, message, null, 0, false);
        }
    }
    public void broadcastMessageTo(obj_id self, String[] parse) throws InterruptedException
    {
        obj_id[] objects = getObjectsInRange(self, 200.0f);
        if (objects == null || objects.length == 0)
        {
            return;
        }
        for (obj_id object : objects) {
            if (!isIdValid(object)) {
                continue;
            }
            obj_id target = object;
            String message = parse[1];
            if (parse.length > 2) {
                dictionary dict = new dictionary();
                for (int i = 2; i < parse.length; i += 2) {
                    int k = i + 1;
                    dict.put(parse[i], parse[k]);
                }
                messageTo(target, message, dict, 0, false);
            } else {
                messageTo(target, message, null, 0, false);
            }
        }
    }
    public void runDestroyAndRecreate(obj_id self, String[] parse) throws InterruptedException
    {
        obj_id target = obj_id.NULL_ID;
        if (parse.length > 1)
        {
            if (parse[1].equals("recordObject"))
            {
                target = utils.getObjIdScriptVar(self, "messageTo.recordObject");
            }
        }
        else 
        {
            target = getTopMostContainer(self);
        }
        if (!isIdValid(target) || target == self)
        {
            return;
        }
        String template = getTemplateName(target);
        location tarLoc = getLocation(target);
        if (template.startsWith("object"))
        {
            if (target == getTopMostContainer(self))
            {
                obj_id[] toDestroy = trial.getAllObjectsInDungeon(target);
                trial.cleanupNpc(toDestroy);
                location recordLoc = getLocation(target);
                location selfLoc = getLocation(self);
                String cell = getCellName(selfLoc.cell);
                trial.cleanupObject(target);
                obj_id newObject = createObject(template, tarLoc);
                warpPlayer(self, recordLoc.area, recordLoc.x, recordLoc.y, recordLoc.z, newObject, cell, selfLoc.x, selfLoc.y, selfLoc.z, "");
            }
        }
    }
    public void runMessageToBuilding(obj_id self, String[] parse) throws InterruptedException
    {
        obj_id top = getTopMostContainer(self);
        String message = parse[1];
        if (parse.length > 2)
        {
            dictionary dict = trial.getSessionDict(top);
            for (int i = 2; i < parse.length; i += 2)
            {
                int k = i + 1;
                dict.put(parse[i], parse[k]);
            }
            messageTo(top, message, dict, 0, false);
        }
        else 
        {
            messageTo(top, message, null, 0, false);
        }
    }
    public void runDeathTouch(obj_id self, String[] parse) throws InterruptedException
    {
        obj_id target = getTarget(self);
        if (!isIdValid(target))
        {
            return;
        }
        trial.cleanupObject(target);
    }
    public void runBuffMe(obj_id self, String[] parse) throws InterruptedException
    {
        buff.applyBuff(self, parse[1]);
    }
    public void runBuffYou(obj_id self, String[] parse) throws InterruptedException
    {
        obj_id target = getIntendedTarget(self);
        buff.applyBuff(target, parse[1]);
    }
    public void runSetRecordObject(obj_id self, String[] parse) throws InterruptedException
    {
        obj_id target = getTarget(self);
        if (isIdValid(target))
        {
            utils.setScriptVar(self, "messageTo.recordObject", target);
            sendSystemMessageTestingOnly(self, "Record object set to: " + target);
        }
        else 
        {
            obj_id top = trial.getTop(self);
            utils.setScriptVar(self, "messageTo.recordObject", top);
            sendSystemMessageTestingOnly(self, "Record object set to: " + top);
        }
    }
    public void runSetRecordObjectFromFind(obj_id self, String[] parse) throws InterruptedException
    {
        obj_id findObject = utils.getObjIdScriptVar(self, "objectId");
        if (isIdValid(findObject))
        {
            utils.setScriptVar(self, "messageTo.recordObject", findObject);
            sendSystemMessageTestingOnly(self, "Record object set to: " + findObject);
        }
    }
    public void runRecordTo(obj_id self, String[] parse) throws InterruptedException
    {
        obj_id target = utils.getObjIdScriptVar(self, "messageTo.recordObject");
        if (!isIdValid(target))
        {
            sendSystemMessageTestingOnly(self, "You do not have a record object");
            return;
        }
        String message = parse[1];
        if (parse.length > 2)
        {
            dictionary dict = trial.getSessionDict(target);
            for (int i = 2; i < parse.length; i += 2)
            {
                int k = i + 1;
                dict.put(parse[i], parse[k]);
            }
            messageTo(target, message, dict, 0, false);
        }
        else 
        {
            messageTo(target, message, null, 0, false);
        }
    }
    public void runGetCellId(obj_id self, String[] parse) throws InterruptedException
    {
        obj_id container = getContainedBy(self);
        sendSystemMessageTestingOnly(self, "I am contained by (" + container + ")");
    }
    public void runClearAllLockoutTimers(obj_id self, String[] parse) throws InterruptedException
    {
        space_dungeon.clearAllDungeonLockoutTimers(self);
    }
    public void runApplyBuff(obj_id self, String[] parse) throws InterruptedException
    {
        obj_id target = getTarget(self);
        if (!isIdValid(target))
        {
            target = self;
        }
        buff.applyBuff(target, parse[1]);
    }
    public void runDoAnim(obj_id self, String[] parse) throws InterruptedException
    {
        obj_id target = getLookAtTarget(self);
        if (!isIdValid(target))
        {
            target = self;
        }
        doAnimationAction(target, parse[1]);
    }
    public void runDoRecordBuildoutLoc(obj_id self, String[] parse) throws InterruptedException
    {
        if (!hasObjVar(self, "record_table"))
        {
            sendSystemMessageTestingOnly(self, "You do not have the record_table objvar defined");
            return;
        }
        obj_id target = getTarget(self);
        if (!isIdValid(target))
        {
            sendSystemMessageTestingOnly(self, "You must target something first");
            return;
        }
        String table = getStringObjVar(self, "record_table");
        location objLoc = getLocation(target);
        float x = objLoc.x;
        float y = objLoc.y;
        float z = objLoc.z;
        String name = getTemplateName(target);
        float yaw = getYaw(target);
        sendSystemMessageTestingOnly(self, "Location(" + x + ", " + y + ", " + z + ") yaw(" + yaw + ")");
        dictionary dctRow = new dictionary();
        dctRow.put("object", name);
        dctRow.put("phase", 0);
        dctRow.put("x_offset", x);
        dctRow.put("y_offset", y);
        dctRow.put("z_offset", z);
        dctRow.put("yaw", yaw);
        datatable.serverDataTableAddRow(table, dctRow);
    }
    public void runPlayMusic(obj_id self, String[] parse) throws InterruptedException
    {
        String sound = "sound/" + parse[1] + ".snd";
        playMusic(self, sound);
        sendSystemMessageTestingOnly(self, "Tried to play sound: " + sound);
    }
    public void runDoPrt(obj_id self, String[] parse) throws InterruptedException
    {
        String effect = "appearance/" + parse[1] + ".prt";
        location playLoc = utils.findLocInFrontOfTarget(self, 10.0f);
        playClientEffectLoc(getIntendedTarget(self), effect, playLoc, 0.4f);
        sendSystemMessageTestingOnly(self, "Attempting to do prt " + effect);
    }
    public void runSpawnAkPrime(obj_id self, String[] parse) throws InterruptedException
    {
        obj_id ak = create.object("som_volcano_two_ak_prime", getLocation(self));
        attachScript(ak, "theme_park.dungeon.mustafar_trials.volcano_battlefield.event_two_boss");
        setInvulnerable(ak, false);
    }
    public void runGetTopMostContainer(obj_id self, String[] parse) throws InterruptedException
    {
        obj_id top = getTopMostContainer(self);
        sendSystemMessageTestingOnly(self, "Top most container is(" + top + ")");
    }
    public void runBadgeOperation(obj_id self, String[] parse) throws InterruptedException
    {
        if (parse.length < 3)
        {
            return;
        }
        if (parse[1].equals("grant"))
        {
            badge.grantBadge(self, parse[2]);
        }
        if (parse[1].equals("revoke"))
        {
            badge.revokeBadge(self, parse[2], true);
        }
    }
    public void runSetFollowInFormation(obj_id self, String[] parse) throws InterruptedException
    {
        if (parse.length < 2)
        {
            return;
        }
        obj_id target = getTarget(self);
        if (!isIdValid(target))
        {
            return;
        }
        stop(target);
        ai_lib.setDefaultCalmBehavior(target, ai_lib.BEHAVIOR_SENTINEL);
        int position = 0;
        if (parse.length == 3)
        {
            position = utils.stringToInt(parse[2]);
        }
        if (parse[1].equals("column"))
        {
            ai_lib.followInFormation(target, self, ai_lib.FORMATION_COLUMN, position);
        }
        if (parse[1].equals("wedge"))
        {
            ai_lib.followInFormation(target, self, ai_lib.FORMATION_WEDGE, position);
        }
        if (parse[1].equals("line"))
        {
            ai_lib.followInFormation(target, self, ai_lib.FORMATION_LINE, position);
        }
        if (parse[1].equals("box"))
        {
            ai_lib.followInFormation(target, self, ai_lib.FORMATION_BOX, position);
        }
        return;
    }
    public void runDeleteObject(obj_id self, String[] parse) throws InterruptedException
    {
        int length = parse.length;
        switch (length)
        {
            case 0:
            return;
            case 1:
            runDeathTouch(self, parse);
            return;
            case 2:
            String deleteString = parse[1];
            obj_id[] objects = getObjectsInRange(self, 200);
            if (objects == null || objects.length == 0)
            {
                return;
            }
                for (obj_id object1 : objects) {
                    if ((getTemplateName(object1)).equals(deleteString)) {
                        trial.cleanupObject(object1);
                    }
                }
            return;
            case 3:
            if (!parse[1].equals("indexOf"))
            {
                return;
            }
            objects = getObjectsInRange(self, 200);
            if (objects == null || objects.length == 0)
            {
                return;
            }
                for (obj_id object : objects) {
                    if (!isIdValid(object)) {
                        continue;
                    }
                    if ((getTemplateName(object)).contains(parse[2])) {
                        trial.cleanupObject(object);
                    }
                }
            return;
        }
    }
    public void runRoriConversion(obj_id self, String[] parse) throws InterruptedException
    {
        if (!hasObjVar(self, "record_table"))
        {
            sendSystemMessageTestingOnly(self, "You do not have the record_table objvar defined");
            return;
        }
        if (!utils.hasScriptVar(self, "messageTo.recordObject"))
        {
            sendSystemMessageTestingOnly(self, "You do not have a recordTo defined");
            return;
        }
        String table = getStringObjVar(self, "record_table");
        String[] strHeaderTypes = 
        {
            "s",
            "s",
            "f",
            "f",
            "f",
            "f",
            "s"
        };
        String[] strHeaders = 
        {
            "object",
            "phase",
            "x_offset",
            "y_offset",
            "z_offset",
            "yaw",
            "cell"
        };
        boolean boolTest = datatable.createDataTable(table, strHeaders, strHeaderTypes);
        if (!boolTest)
        {
            sendSystemMessageTestingOnly(self, "No dataTable made");
            return;
        }
        location recordLoc = getLocation(utils.getObjIdScriptVar(self, "messageTo.recordObject"));
        obj_id[] objects = getAllObjectsWithObjVar(recordLoc, 300.0f, "test");
        if (objects == null || objects.length == 0)
        {
            return;
        }
        for (obj_id object : objects) {
            if ((getTemplateName(object)).indexOf("invis") < 1 && !isPlayer(object)) {
                obj_id top = trial.getTop(object);
                if (top == object) {
                    location objLoc = getLocation(object);
                    float x = objLoc.x - recordLoc.x;
                    float y = objLoc.y - recordLoc.y;
                    float z = objLoc.z - recordLoc.z;
                    String name = getTemplateName(object);
                    float yaw = getYaw(object);
                    sendSystemMessageTestingOnly(self, "Object(" + name + ") Location(" + x + ", " + y + ", " + z + ") yaw(" + yaw + ")");
                    dictionary dctRow = new dictionary();
                    dctRow.put("object", name);
                    dctRow.put("phase", "0");
                    dctRow.put("x_offset", x);
                    dctRow.put("y_offset", y);
                    dctRow.put("z_offset", z);
                    dctRow.put("yaw", yaw);
                    datatable.serverDataTableAddRow(table, dctRow);
                }
            }
        }
    }
    public void getModLoc(obj_id self) throws InterruptedException
    {
        if (!utils.hasScriptVar(self, "messageTo.recordObject"))
        {
            sendSystemMessageTestingOnly(self, "You do not have a record object");
            return;
        }
        obj_id target = getIntendedTarget(self);
        if (!isIdValid(target))
        {
            target = self;
        }
        location tLoc = getLocation(target);
        float yaw = getYaw(target);
        location rLoc = getLocation(utils.getObjIdScriptVar(self, "messageTo.recordObject"));
        location modLoc = new location(tLoc.x - rLoc.x, tLoc.y - rLoc.y, tLoc.z - rLoc.z, rLoc.area);
        sendSystemMessageTestingOnly(self, "Transform " + modLoc.x + " " + modLoc.y + " " + modLoc.z + ", Yaw: " + yaw);
        LOG("whereLog", "Where: x " + modLoc.x + "     ");
        LOG("whereLog", "Where: y " + modLoc.y + "     ");
        LOG("whereLog", "Where: z " + modLoc.z + "     ");
        LOG("whereLog", "Yaw====: " + yaw + "     ");
    }
    public void calculateWarp(obj_id self, String[] parse) throws InterruptedException
    {
        if (parse == null || parse.length < 3)
        {
            return;
        }
        float dist = 4096.0f;
        float x = utils.stringToFloat(parse[1]);
        float z = utils.stringToFloat(parse[2]);
        x += dist;
        z += dist;
        warpPlayer(self, "rori", x, 0, z, null, x, 0, z);
    }
    public void calculateDistance(obj_id self, String[] parse) throws InterruptedException
    {
        float distance = getDistance(getLocation(self), getLocation(utils.getObjIdScriptVar(self, "messageTo.recordObject")));
        sendSystemMessageTestingOnly(self, "Distance to recordObject: " + distance);
    }
    public void deleteRegion(obj_id self, String[] parse) throws InterruptedException
    {
        region r = getRegion(getLocation(self).area, parse[1]);
        deleteRegion(r);
        sendSystemMessageTestingOnly(self, "Deleted Region: " + r);
    }
    public void get3dSpaceLoc(obj_id self, String[] parse) throws InterruptedException
    {
        obj_id target = getTarget(self);
        transform vctTest = getTransform_o2p(target);
        vector vctJ = vctTest.getLocalFrameJ_p();
        vector vctK = vctTest.getLocalFrameK_p();
        vector vctP = vctTest.getPosition_p();
        sendSystemMessageTestingOnly(self, "J = " + vctJ.x + ", " + vctJ.y + ", " + vctJ.z + ", K = " + vctK.x + ", " + vctK.y + ", " + vctK.z + ", Pos = " + vctP.x + ", " + vctP.y + ", " + vctP.z);
    }
    public void runQuery(obj_id self, String[] parse) throws InterruptedException
    {
        String subject = parse[1];
        String modifier = parse[2];
        if (subject.equals("creature"))
        {
            String creatureTable = "datatables/mob/creatures.iff";
            if (modifier.equals("name"))
            {
                int row = dataTableSearchColumnForString(parse[3], "creatureName", creatureTable);
                if (row > -1)
                {
                    dictionary creatureDict = dataTableGetRow(creatureTable, row);
                    String[] data = convertCreatureQueryToStringArray(creatureDict);
                    populateListbox(self, data, "none");
                }
                else 
                {
                    String[] nameList = dataTableGetStringColumn(creatureTable, "creatureName");
                    Vector indexList = new Vector();
                    indexList.setSize(0);
                    for (String s : nameList) {
                        if (s.contains(parse[3])) {
                            utils.addElement(indexList, s);
                        }
                    }
                    if (indexList == null || indexList.size() == 0)
                    {
                        sendSystemMessageTestingOnly(self, "No matches could be found");
                        return;
                    }
                    if (indexList != null)
                    {
                        nameList = new String[indexList.size()];
                        indexList.toArray(nameList);

                    }
                    populateListbox(self, nameList, "creatureLookup");
                }
            }
        }
    }
    public void populateListbox(obj_id self, String[] data, String handler) throws InterruptedException
    {
        int pid = createSUIPage(sui.SUI_LISTBOX, self, self, handler);
        if (pid > -1)
        {
            setSUIProperty(pid, sui.LISTBOX_TITLE, sui.PROP_TEXT, "Query Result");
            setSUIProperty(pid, sui.LISTBOX_PROMPT, sui.PROP_TEXT, "Query Results");
            sui.listboxButtonSetup(pid, 0);
            if (data != null && data.length > 0)
            {
                clearSUIDataSource(pid, sui.LISTBOX_DATASOURCE);
                for (int i = 0; i < data.length; i++)
                {
                    sui.addSUIDataItem(pid, sui.LISTBOX_DATASOURCE, "" + i);
                    sui.setSUIProperty(pid, sui.LISTBOX_DATASOURCE + "." + i, sui.PROP_TEXT, data[i]);
                }
            }
            utils.setScriptVar(self, "query.data", data);
            sui.subscribeToSUIProperty(pid, sui.LISTBOX_LIST, sui.PROP_SELECTEDROW);
            sui.subscribeToSUIProperty(pid, sui.LISTBOX_TITLE, sui.PROP_TEXT);
            sui.showSUIPage(pid);
        }
    }
    public String[] convertCreatureQueryToStringArray(dictionary creatureDict) throws InterruptedException
    {
        String[] data = new String[18];
        data[0] = "Name: " + creatureDict.getString("creatureName");
        data[1] = "Level: " + creatureDict.getInt("BaseLevel");
        data[2] = "Difficulty Class: " + creatureDict.getInt("difficultyClass");
        data[3] = "Location: " + creatureDict.getString("where");
        data[4] = "Social Group: " + creatureDict.getString("socialGroup");
        data[5] = "PvP Faction: " + creatureDict.getString("pvpFaction");
        data[6] = "Template: " + creatureDict.getString("template");
        data[7] = "Loot Table: " + creatureDict.getString("lootTable");
        data[8] = "Loot List: " + creatureDict.getString("lootList");
        data[9] = "Niche: " + creatureDict.getInt("niche");
        data[10] = "Scripts: " + creatureDict.getString("scripts");
        data[11] = "Primary Weapon: " + creatureDict.getString("primary_weapon");
        data[12] = "Primary Specials: " + creatureDict.getString("primary_weapon_specials");
        data[13] = "Secondary Weapon: " + creatureDict.getString("secondary_weapon");
        data[14] = "Secondary Specials: " + creatureDict.getString("secondary_weapon_specials");
        data[15] = "Aggressive: " + creatureDict.getFloat("aggressive");
        data[16] = "Assist: " + creatureDict.getFloat("assist");
        data[17] = "Death Blow: " + creatureDict.getInt("death_blow");
        return data;
    }
    public int creatureLookup(obj_id self, dictionary params) throws InterruptedException
    {
        int btn = sui.getIntButtonPressed(params);
        int idx = sui.getListboxSelectedRow(params);
        if (btn == sui.BP_CANCEL)
        {
            return SCRIPT_CONTINUE;
        }
        if (idx == -1)
        {
            return SCRIPT_CONTINUE;
        }
        String[] passedData = utils.getStringArrayScriptVar(self, "query.data");
        String creatureTable = "datatables/mob/creatures.iff";
        int row = dataTableSearchColumnForString(passedData[idx], "creatureName", creatureTable);
        if (row > -1)
        {
            dictionary creatureDict = dataTableGetRow(creatureTable, row);
            String[] data = convertCreatureQueryToStringArray(creatureDict);
            populateListbox(self, data, "none");
        }
        return SCRIPT_CONTINUE;
    }
    public void getBaseCount(obj_id self, String[] parse) throws InterruptedException
    {
        obj_id planetId = getPlanetByName(getLocation(self).area);
        obj_id planetRegister = utils.getObjIdScriptVar(planetId, gcw.GCW_BASE_MANAGER);
        int rebel = gcw.getRebelBaseCount(planetId);
        int imperial = gcw.getImperialBaseCount(planetId);
        sendSystemMessageTestingOnly(self, "There are " + rebel + " Rebel Bases and " + imperial + " Imperial Bases in scene " + getLocation(self).area + " via manager " + planetRegister);
    }
    public void calculateGameTime(obj_id self, String[] parse) throws InterruptedException
    {
        if (parse.length == 1)
        {
            sendSystemMessageTestingOnly(self, "" + getGameTime());
        }
        else 
        {
            int passedTime = utils.stringToInt(parse[1]);
            String verboseTime = utils.formatTimeVerbose(getGameTime() - passedTime);
            sendSystemMessageTestingOnly(self, verboseTime);
        }
    }
    public void runTestBatch(obj_id self, String[] parse) throws InterruptedException
    {
        if (parse.length < 2)
        {
            return;
        }
        int[] toTheMax = new int[45];
        for (int i = 0; i < 45; i++)
        {
            toTheMax[i] = i;
        }
        if (utils.hasIntBatchObjVar(self, parse[1]))
        {
            utils.removeBatchObjVar(self, parse[1]);
        }
        else 
        {
            utils.setBatchObjVar(self, parse[1], toTheMax);
        }
    }
    public void runMakeSpaceOvert(obj_id self, String[] parse) throws InterruptedException
    {
        String faction = factions.getFaction(self);
        if (faction == null)
        {
            return;
        }
        if (faction.equals("Rebel"))
        {
            space_transition.setPlayerOvert(self, (370444368));
        }
        else 
        {
            space_transition.setPlayerOvert(self, (-615855020));
        }
    }
    public void runGetPlanetObject(obj_id self, String[] parse) throws InterruptedException
    {
        sendSystemMessageTestingOnly(self, "" + getPlanetByName(getLocation(self).area));
    }
    public void runRespec(obj_id self, String[] parse) throws InterruptedException
    {
        if (parse.length < 3)
        {
            sendSystemMessageTestingOnly(self, "Not enough parameters");
            return;
        }
        String newTemplate = parse[1];
        float pctDone = utils.stringToFloat(parse[2]);
        skill.revokeAllProfessionSkills(self);
        setSkillTemplate(self, newTemplate);
        respec.setPercentageCompletion(self, newTemplate, pctDone, false);
        skill.recalcPlayerPools(self, true);
    }
    public void runGetSkillTemplate(obj_id self, String[] parse) throws InterruptedException
    {
        sendSystemMessageTestingOnly(self, getSkillTemplate(self));
    }
    public void runQueueCommand(obj_id self, String[] parse) throws InterruptedException
    {
        obj_id target = getIntendedTarget(self);
        queueCommand(self, getStringCrc(parse[1].toLowerCase()), target, "", COMMAND_PRIORITY_DEFAULT);
    }
    public void runSetCooldown(obj_id self, String[] parse) throws InterruptedException
    {
        sendSystemMessageTestingOnly(self, "Gathering Data for : " + parse[1]);
        combat_data cd = combat_engine.getCombatData(parse[1]);
        String cooldownGroup = cd.cooldownGroup;
        sendSystemMessageTestingOnly(self, "Cooldown Group and Base: " + cd.cooldownGroup + ", " + cd.cooldownTime);
        int groupCrc = getStringCrc(cooldownGroup);
        float baseCooldownTime = 1.0f;
        if (parse.length > 2)
        {
            sendSystemMessageTestingOnly(self, "Overriding base time with " + utils.stringToFloat(parse[2]));
            baseCooldownTime = utils.stringToFloat(parse[2]);
        }
        else 
        {
            baseCooldownTime = cd.cooldownTime;
        }
        sendSystemMessageTestingOnly(self, "Attempting to Set cooldown time on " + getName(self) + " to group " + cd.cooldownGroup + " at time " + baseCooldownTime);
        sendCooldownGroupTimingOnly(self, groupCrc, baseCooldownTime);
    }
    public void runGetWeapon(obj_id self, String[] parse) throws InterruptedException
    {
        obj_id weapon = getCurrentWeapon(self);
        sendSystemMessageTestingOnly(self, "Current weapon is: " + weapon);
    }
    public void runVerifySession(obj_id self, String[] parse) throws InterruptedException
    {
        space_dungeon.verifyPlayerSession(self);
    }
    public void runGetHateList(obj_id self, String[] parse) throws InterruptedException
    {
        obj_id subject = getTarget(self);
        obj_id primary_target = getHateTarget(subject);
        float hate_value = getHate(subject, primary_target);
        obj_id[] hated = getHateList(subject);
        sendSystemMessageTestingOnly(self, "Primary Target: " + getName(primary_target) + " - " + hate_value);
        if (hated.length < 2)
        {
            return;
        }
        sendSystemMessageTestingOnly(self, "Full List");
        String[] detailed_list = new String[hated.length];
        for (int i = 0; i < hated.length; i++)
        {
            detailed_list[i] = "" + getName(hated[i]) + " - " + getHate(subject, hated[i]);
            sendSystemMessageTestingOnly(self, detailed_list[i]);
        }
    }
    public void runGetRespect(obj_id self, String[] parse) throws InterruptedException
    {
        obj_id subject = getTarget(self);
        float respectRadius = aiGetRespectRadius(subject, self);
        sendSystemMessageTestingOnly(self, "Respect Radius for " + getName(subject) + " to " + getName(self) + " is " + respectRadius);
    }
    public void runMakeDecoy(obj_id self, String[] parse) throws InterruptedException
    {
        obj_id decoy = stealth.createDecoy(self);
        if (!isIdValid(decoy))
        {
            return;
        }
        obj_id[] haters = getWhoIsTargetingMe(self);
        if (haters == null || haters.length == 0)
        {
            int diversionLevel = getEnhancedSkillStatisticModifierUncapped(self, "expertise_improved_decoy");
            if (diversionLevel < 2)
            {
                return;
            }
            buff.applyBuff(self, "invis_sp_diversion_stealth");
            return;
        }
        for (obj_id hater : haters) {
            obj_id[] hateList = getHateList(hater);
            if (hateList == null || hateList.length == 0) {
                continue;
            }
            for (obj_id obj_id : hateList) {
                if (obj_id == self) {
                    setHate(hater, decoy, getHate(hater, self) * 2);
                }
            }
        }
        int diversionLevel = getEnhancedSkillStatisticModifierUncapped(self, "expertise_improved_decoy");
        if (diversionLevel < 2)
        {
            return;
        }
        buff.applyBuff(self, "invis_sp_diversion_stealth");
    }
    public void runCommLink(obj_id self, String[] parse) throws InterruptedException
    {
        if (parse.length < 3)
        {
            return;
        }
        faction_perk.spawnTroopers(self, parse[1], utils.stringToInt(parse[2]));
    }
    public void runPrepCave(obj_id self, String[] parse) throws InterruptedException
    {
        obj_id cave = trial.getTop(self);
        if (!isIdValid(cave) || cave == self)
        {
            return;
        }
        detachAllScripts(cave);
        removeAllObjVars(cave);
        attachScript(cave, "theme_park.heroic.axkva_min.axkva_controller");
    }
    public void runSwarmTarget(obj_id self, String[] parse) throws InterruptedException
    {
        obj_id target = getIntendedTarget(self);
        obj_id[] creatures = getCreaturesInRange(getLocation(self), 64.0f);
        if (creatures == null || creatures.length == 0)
        {
            return;
        }
        for (obj_id creature : creatures) {
            if (creature != target) {
                addHate(creature, target, 1);
            }
        }
    }
    public void runSetMaster(obj_id self, String[] parse) throws InterruptedException
    {
        obj_id target = getIntendedTarget(self);
        setMaster(self, null);
        setMaster(target, self);
    }
    public void runConvert(obj_id self, String[] parse) throws InterruptedException
    {
        int numRows = dataTableGetNumRows("datatables/spawning/heroic/heroic_axkva_min.iff");
        for (int i = 0; i < numRows; i++)
        {
            String triggerId = dataTableGetString("datatables/spawning/heroic/heroic_axkva_min.iff", i, "triggerId");
            debugSpeakMsg(self, "Converted triggerId(" + triggerId + ") = " + utils.stringToInt(triggerId));
        }
    }
    public void dumpToTable(obj_id self, String[] parse) throws InterruptedException
    {
        if (!hasObjVar(self, "record_table"))
        {
            sendSystemMessageTestingOnly(self, "You do not have the record_table objvar defined");
            return;
        }
        if (!utils.hasScriptVar(self, "messageTo.recordObject"))
        {
            sendSystemMessageTestingOnly(self, "You do not have a recordTo defined");
            return;
        }
        String table = getStringObjVar(self, "record_table");
        String[] strHeaderTypes = 
        {
            "s",
            "f",
            "f",
            "f",
            "f"
        };
        String[] strHeaders = 
        {
            "object",
            "x_offset",
            "y_offset",
            "z_offset",
            "yaw"
        };
        boolean boolTest = datatable.createDataTable(table, strHeaders, strHeaderTypes);
        if (!boolTest)
        {
            sendSystemMessageTestingOnly(self, "No dataTable made");
            return;
        }
        location recordLoc = getLocation(utils.getObjIdScriptVar(self, "messageTo.recordObject"));
        obj_id[] objects = getObjectsInRange(recordLoc, 1000.0f);
        if (objects == null || objects.length == 0)
        {
            return;
        }
        for (obj_id object : objects) {
            if ((getTemplateName(object)).contains("furniture")) {
                obj_id top = trial.getTop(object);
                if (top == object) {
                    location objLoc = getLocation(object);
                    float x = objLoc.x - recordLoc.x;
                    float y = objLoc.y - recordLoc.y;
                    float z = objLoc.z - recordLoc.z;
                    String name = getTemplateName(object);
                    float yaw = getYaw(object);
                    sendSystemMessageTestingOnly(self, "Object(" + name + ") Location(" + x + ", " + y + ", " + z + ") yaw(" + yaw + ")");
                    dictionary dctRow = new dictionary();
                    dctRow.put("object", name);
                    dctRow.put("x_offset", x);
                    dctRow.put("y_offset", y);
                    dctRow.put("z_offset", z);
                    dctRow.put("yaw", yaw);
                    datatable.serverDataTableAddRow(table, dctRow);
                }
            }
        }
    }
    public void recreateTable(obj_id self, String[] parse) throws InterruptedException
    {
        if (!hasObjVar(self, "record_table"))
        {
            sendSystemMessageTestingOnly(self, "You do not have the record_table objvar defined");
            return;
        }
        if (!utils.hasScriptVar(self, "messageTo.recordObject"))
        {
            sendSystemMessageTestingOnly(self, "You do not have a recordTo defined");
            return;
        }
        String table = getStringObjVar(self, "record_table");
        obj_id recordObject = utils.getObjIdScriptVar(self, "messageTo.recordObject");
        int rows = dataTableGetNumRows(table);
        for (int i = 0; i < rows; i++)
        {
            dictionary dict = dataTableGetRow(table, i);
            String object = dict.getString("object");
            float ox = dict.getFloat("x_offset");
            float oy = dict.getFloat("y_offset");
            float oz = dict.getFloat("z_offset");
            float yaw = dict.getFloat("yaw");
            location spawnLoc = getLocation(recordObject);
            spawnLoc.x += ox;
            spawnLoc.y += oy;
            spawnLoc.z += oz;
            obj_id newObject = createObject(object, spawnLoc);
            if (isIdValid(newObject))
            {
                setYaw(newObject, yaw);
                persistObject(newObject);
            }
        }
    }
    public void runGetCommmandList(obj_id self, String[] parse) throws InterruptedException
    {
        String[] commandList = getCommandListingForPlayer(self);
        if (commandList == null || commandList.length == 0)
        {
            debugSpeakMsg(self, "Fail");
            return;
        }
        for (String s : commandList) {
            debugSpeakMsg(self, "Command: " + s);
        }
    }
    public void runGetCellList(obj_id self, String[] parse) throws InterruptedException
    {
        obj_id pob = utils.stringToObjId(parse[1]);
        String[] list = getCellNames(pob);
        for (String s : list) {
            debugSpeakMsg(self, "" + s);
        }
    }
    public void runEnterCell(obj_id self, String[] parse) throws InterruptedException
    {
        obj_id pob = utils.stringToObjId(parse[1]);
        location cellLoc = getGoodLocation(pob, parse[2]);
        warpPlayer(self, cellLoc.area, cellLoc.x, cellLoc.y, cellLoc.z, pob, parse[2], cellLoc.x, cellLoc.y, cellLoc.z, "nullCallback", false);
    }
    public void runIt(obj_id self, String[] parse) throws InterruptedException
    {
        attacker_results atkRslt = new attacker_results();
        defender_results[] dfndRslt = new defender_results[0];
        atkRslt.id = self;
        atkRslt.endPosture = POSTURE_UPRIGHT;
        String anim = parse[1];
        dfndRslt[0].id = self;
        doCombatResults(anim, atkRslt, dfndRslt);
    }
    public void runGetBell(obj_id self, String[] parse) throws InterruptedException
    {
        if (utils.hasScriptVar(self, "bellpid"))
        {
            int bellpid = utils.getIntScriptVar(self, "bellpid");
            sui.forceCloseSUIPage(bellpid);
        }
        float min = utils.stringToFloat(parse[1]);
        float max = utils.stringToFloat(parse[2]);
        int level = utils.stringToInt(parse[3]);
        int pid = createSUIPage(sui.SUI_LISTBOX, self, self, "noHandler");
        setSUIProperty(pid, "", "Size", "650,375");
        setSUIProperty(pid, sui.LISTBOX_TITLE, sui.PROP_TEXT, "Distributed Random");
        sui.listboxButtonSetup(pid, sui.OK_ONLY);
        setSUIProperty(pid, sui.LISTBOX_BTN_OK, sui.PROP_TEXT, "@cancel");
        int listSize = 1000;
        float[] bigList = new float[listSize];
        float average = 0.0f;
        float high = 0.0f;
        float low = 10.0f;
        int in4 = 0;
        int in5 = 0;
        int in6 = 0;
        int in7 = 0;
        int in8 = 0;
        int in9 = 0;
        int in10 = 0;
        for (int i = 0; i < listSize; i++)
        {
            bigList[i] = distributedRand(min, max, level);
            addSUIDataItem(pid, sui.LISTBOX_DATASOURCE, "" + i);
            setSUIProperty(pid, sui.LISTBOX_DATASOURCE + "." + i, sui.PROP_TEXT, "" + bigList[i]);
            average += bigList[i];
            if (bigList[i] < low)
            {
                low = bigList[i];
            }
            if (bigList[i] > high)
            {
                high = bigList[i];
            }
            float num = bigList[i];
            if (num >= 4.0f && num < 5.0f)
            {
                in4++;
            }
            if (num >= 5.0f && num < 6.0f)
            {
                in5++;
            }
            if (num >= 6.0f && num < 7.0f)
            {
                in6++;
            }
            if (num >= 7.0f && num < 8.0f)
            {
                in7++;
            }
            if (num >= 8.0f && num < 9.0f)
            {
                in8++;
            }
            if (num >= 9.0f && num < 10.0f)
            {
                in9++;
            }
            if (num >= 10.0f)
            {
                in10++;
            }
        }
        average = average / listSize;
        setSUIProperty(pid, sui.LISTBOX_PROMPT, sui.PROP_TEXT, "Average = " + average + ", High = " + high + ", Low = " + low + " \n 4 = " + in4 + " \n 5 = " + in5 + " \n 6 = " + in6 + " \n 7 = " + in7 + " \n 8 = " + in8 + " \n 9 = " + in9 + " \n 10 = " + in10);
        subscribeToSUIEvent(pid, sui_event_type.SET_onGenericSelection, sui.LISTBOX_LIST, "noHandler");
        subscribeToSUIPropertyForEvent(pid, sui_event_type.SET_onGenericSelection, sui.LISTBOX_LIST, sui.LISTBOX_LIST, sui.PROP_SELECTEDROW);
        subscribeToSUIProperty(pid, sui.LISTBOX_LIST, sui.PROP_SELECTEDROW);
        subscribeToSUIProperty(pid, sui.LISTBOX_TITLE, sui.PROP_TEXT);
        showSUIPage(pid);
        flushSUIPage(pid);
        utils.setScriptVar(self, "bellpid", pid);
    }
    public float distributedRand(float min, float max, int level) throws InterruptedException
    {
        final int levelMin = 0;
        final int levelMax = 10;
        boolean inverted = false;
        float _min = min;
        float _max = max;
        if (min > max)
        {
            inverted = true;
            min = _max;
            max = _min;
        }
        float rank = (float)(level - levelMin) / (levelMax - levelMin);
        float mid = min + ((max - min) * rank);
        if (mid < min)
        {
            max += (mid - min);
            mid = min;
        }
        if (mid > max)
        {
            min += (mid - max);
            mid = max;
        }
        float minRand = rand(min, mid);
        float maxRand = rand(mid, max);
        float randNum = rand(minRand, maxRand);
        if (inverted)
        {
            randNum = _min + (_max - randNum);
        }
        return randNum;
    }
    public void runSetHover(obj_id self, String[] parse) throws InterruptedException
    {
        float height = utils.stringToFloat(parse[1]);
        vehicle.setHoverHeight(getIntendedTarget(self), height);
    }
    public void runTestSlope(obj_id self, String[] parse) throws InterruptedException
    {
        obj_id target = getIntendedTarget(self);
        addSlowDownEffect(self, target, 10.0f, 180.0f, 85.0f, 10.0f);
    }
    public void runAlterHeight(obj_id self, String[] parse) throws InterruptedException
    {
        obj_id target = getIntendedTarget(self);
        float change = utils.stringToFloat(parse[1]);
        location loc = getLocation(target);
        loc.y += change;
        setLocation(target, loc);
    }
    public void runTestBool(obj_id self, String[] parse) throws InterruptedException
    {
        boolean overall = false;
        for (int i = 0; i < 6; i++)
        {
            boolean current = false;
            if (i == 4)
            {
                current = true;
            }
            overall |= current;
            debugSpeakMsg(self, "overall = " + overall + ", current = " + current);
        }
        debugSpeakMsg(self, "overall = " + overall);
    }
    public void runAddAttrib(obj_id self, String[] parse) throws InterruptedException
    {
        String attrib = parse[1];
        int value = utils.stringToInt(parse[2]);
        int time = utils.stringToInt(parse[3]);
        addSkillModModifier(self, attrib + "_0", attrib, value, time, false, false);
    }
    public void runLit3(obj_id self, String[] parse) throws InterruptedException
    {
        obj_id recordObject = utils.getObjIdScriptVar(self, "messageTo.recordObject");
        obj_id target = getIntendedTarget(self);
        chat.chat(self, "Can See: " + canSee(recordObject, target));
    }
    public void runShieldMe(obj_id self, String[] parse) throws InterruptedException
    {
        for (int i = 0; i < 9; i++)
        {
            debugSpeakMsg(self, "attrib" + i + " = " + getAttrib(self, i));
        }
    }
    public void clearStunEffects(obj_id self, String[] parse) throws InterruptedException
    {
        setState(self, STATE_STUNNED, false);
        setState(self, STATE_FROZEN, false);
    }
    public void makeMeAGod(obj_id self, String[] parse) throws InterruptedException
    {
        buff.applyBuff(self, "kun_eight_sacrifice", 2400);
        buff.applyBuff(self, "crystal_buff", 2400);
        buff.applyBuff(self, "kun_minder_heal_buff", 2400);
    }
    public void runSetSlope(obj_id self, String[] parse) throws InterruptedException
    {
        obj_id target = getIntendedTarget(self);
        addSlowDownEffect(self, target, 50.0f, 0.01f, 75.0f, 10.0f);
    }
    public void runSetKillMeter(obj_id self, String[] parse) throws InterruptedException
    {
        int value = utils.stringToInt(parse[1]);
        combat.setKillMeter(self, value);
    }
    public void runSetMind(obj_id self, String[] parse) throws InterruptedException
    {
        setMind(self, utils.stringToInt(parse[1]));
    }
    public void runGetMind(obj_id self, String[] parse) throws InterruptedException
    {
        int current = getMind(self);
        int max = getMaxMind(self);
        debugSpeakMsg(self, "Mind = " + current + " of " + max);
    }
    public void runSetupSnowspeeder(obj_id self, String[] parse) throws InterruptedException
    {
        obj_id target = getMountId(self);
        setBeastmasterPet(self, target);
        String[] abilities = 
        {
            "hoth_speeder_shoot",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            ""
        };
        setBeastmasterPetCommands(self, abilities);
    }
    public void runTestShoot(obj_id self, String[] parse) throws InterruptedException
    {
        obj_id target = getIntendedTarget(self);
        float travelDistance = getDistance(target, self);
        float speed = travelDistance / 2.0f;
        location selfLoc = getLocation(self);
        selfLoc.y += 10.0f;
        location tarLoc = getLocation(target);
        createClientProjectile(self, "object/weapon/ranged/pistol/shared_pistol_green_bolt.iff", selfLoc, getLocation(target), speed, 2.0f, false, 32, 178, 170, 255);
    }
    public void runSetLoc(obj_id self, String[] parse) throws InterruptedException
    {
        float locx = utils.stringToFloat(parse[1]);
        float locy = utils.stringToFloat(parse[2]);
        float locz = utils.stringToFloat(parse[3]);
        location tarLoc = getLocation(getIntendedTarget(self));
        location newLoc = new location(locx, locy, locz, tarLoc.area, tarLoc.cell);
        setLocation(getIntendedTarget(self), newLoc);
    }
    public static final dictionary dict = new dictionary();
    public void runSetDda(obj_id self, String[] parse) throws InterruptedException
    {
        int fromRow = utils.stringToInt(parse[1]);
        String[] testString = 
        {
            "one",
            "two",
            "three",
            "four",
            "five",
            "six",
            "seven",
            "eight"
        };
        int[] testInt = 
        {
            1,
            2,
            3,
            4,
            5,
            6,
            7,
            8
        };
        String[] passString = new String[3];
        int[] passInt = new int[3];
        int q = 0;
        for (int i = fromRow; i < fromRow + 3; i++)
        {
            passString[q] = testString[i];
            passInt[q] = testInt[i];
            q++;
        }
        dict.put("testString", passString);
        dict.put("testInt", passInt);
    }
    public void runGetDda(obj_id self, String[] parse) throws InterruptedException
    {
        String[] testString = dict.getStringArray("testString");
        int[] testInt = dict.getIntArray("testInt");
        for (int i = 0; i < testString.length; i++)
        {
            debugSpeakMsg(self, "" + testString[i]);
            debugSpeakMsg(self, "" + testInt[i]);
        }
    }
    public void runStaticItemParse(obj_id self, String[] parse) throws InterruptedException
    {
        int parseLength = parse.length;
        if (parseLength >= 2)
        {
            switch ((toLower(parse[1]))) {
                case "find": {
                    if (parseLength < 3) {
                        sendSystemMessage(self, "Syntax: staticItem find [keyword] [optional sub-keyword]", "");
                        return;
                    }
                    String[] data = new String[parse.length - 1];
                    int dataIndex = 0;
                    for (int i = 0; i < parse.length; i++) {
                        if (i != 1) {
                            data[dataIndex] = parse[i];
                            ++dataIndex;
                        }
                    }
                    runStaticItemFind(self, data);
                    break;
                }
                case "create": {
                    if (parseLength < 3) {
                        sendSystemMessage(self, "Syntax: staticItem create [list index or static item name]", "");
                        return;
                    }
                    String[] data = new String[parse.length - 1];
                    int dataIndex = 0;
                    for (int i = 0; i < parse.length; i++) {
                        if (i != 1) {
                            data[dataIndex] = parse[i];
                            ++dataIndex;
                        }
                    }
                    runStaticItemCreate(self, data);
                    break;
                }
                case "list":
                    runStaticItemShowList(self);
                    break;
                case "clear":
                    runStaticItemClearList(self);
                    break;
            }
        }
        else 
        {
            String helpMessage = "staticItem Help: \n" + "--------------- \n" + "staticItem find [keyword] [optional sub-keyword] \n" + "staticItem create [list index or static item name] \n" + "staticItem list \n" + "staticItem clear";
            sendSystemMessage(self, helpMessage, "");
        }
        return;
    }
    public void runStaticItemFind(obj_id self, String[] parse) throws InterruptedException
    {
        String staticItemWanted = "";
        String subString = "";
        int parseLength = parse.length;
        if (parseLength < 2)
        {
            sendSystemMessage(self, "Syntax: staticItem find [keyword] [optional sub-keyword]", "");
            return;
        }
        staticItemWanted = parse[1];
        if (parseLength >= 3)
        {
            subString = parse[2];
        }
        Vector matchingStaticItems = new Vector();
        matchingStaticItems.setSize(0);
        String output = "";
        int index = 0;
        String[] allStaticItems = dataTableGetStringColumn(static_item.MASTER_ITEM_TABLE, "name");
        for (String allStaticItem : allStaticItems) {
            boolean matchFound = false;
            String staticItemName = allStaticItem;
            int stringCheck = staticItemName.indexOf(staticItemWanted);
            if (stringCheck > -1) {
                if (subString != null && subString.length() > 0) {
                    int subStringCheck = staticItemName.indexOf(subString);
                    if (subStringCheck > -1) {
                        matchFound = true;
                    }
                } else {
                    matchFound = true;
                }
                if (matchFound) {
                    utils.addElement(matchingStaticItems, staticItemName);
                    output += index + " - " + staticItemName + " \n";
                    ++index;
                }
            }
        }
        if (matchingStaticItems.size() < 1)
        {
            output = "No matching static items found.";
        }
        else 
        {
            utils.setScriptVar(self, "idiot_matchingStaticItems", matchingStaticItems);
        }
        sendSystemMessage(self, output, "\n");
        return;
    }
    public void runStaticItemCreate(obj_id self, String[] parse) throws InterruptedException
    {
        if (parse.length < 2)
        {
            sendSystemMessage(self, "Syntax: staticItem create [list index or static item name]", "");
            return;
        }
        String staticItemString = parse[1];
        int staticItemIndex = utils.stringToInt(staticItemString);
        if (staticItemIndex > -1 && utils.hasScriptVar(self, "idiot_matchingStaticItems"))
        {
            Vector matchingStaticItems = utils.getResizeableStringArrayScriptVar(self, "idiot_matchingStaticItems");
            if (matchingStaticItems != null && matchingStaticItems.size() > 0)
            {
                if (staticItemIndex >= 0 && staticItemIndex < matchingStaticItems.size())
                {
                    String staticItem = ((String)matchingStaticItems.get(staticItemIndex));
                    obj_id inv = getObjectInSlot(self, "inventory");
                    obj_id item = static_item.createNewItemFunction(staticItem, inv);
                    if (isIdValid(item))
                    {
                        sendSystemMessage(self, "Static Item " + staticItem + " created in your inventory.", "");
                    }
                    else 
                    {
                        sendSystemMessage(self, "Error creating static item with supplied NAME (" + staticItem + ").", "");
                    }
                }
                else 
                {
                    sendSystemMessage(self, "Invalid static item index.", "");
                }
            }
            else 
            {
                sendSystemMessage(self, "Empty or Null array returned from Scriptvar.", "");
            }
        }
        else 
        {
            if (static_item.createNewItemFunction(staticItemString, self) == null)
            {
                sendSystemMessage(self, "Error creating static item with supplied NAME (" + staticItemString + ").", "");
                return;
            }
            else 
            {
                sendSystemMessage(self, "Static Item " + staticItemString + " created in your inventory.", "");
                return;
            }
        }
        return;
    }
    public void runStaticItemShowList(obj_id self) throws InterruptedException
    {
        if (utils.hasScriptVar(self, "idiot_matchingStaticItems"))
        {
            Vector matchingStaticItems = utils.getResizeableStringArrayScriptVar(self, "idiot_matchingStaticItems");
            String output = "\n";
            if (matchingStaticItems != null && matchingStaticItems.size() > 0)
            {
                for (int i = 0; i < matchingStaticItems.size(); i++)
                {
                    output = output + i + " - " + ((String)matchingStaticItems.get(i)) + " \n";
                }
                sendSystemMessage(self, output, "");
            }
            else 
            {
                sendSystemMessage(self, "Empty or Null array returned from Static Items List Scriptvar.", "");
            }
        }
        else 
        {
            sendSystemMessage(self, "Static Items List not found.", "");
        }
        return;
    }
    public void runStaticItemClearList(obj_id self) throws InterruptedException
    {
        if (utils.hasScriptVar(self, "idiot_matchingStaticItems"))
        {
            utils.removeScriptVar(self, "idiot_matchingStaticItems");
            sendSystemMessage(self, "Static Items List removed.", "");
        }
        else 
        {
            sendSystemMessage(self, "Static Items List not found.", "");
        }
        return;
    }
    public void runPlaySound(obj_id self, String[] parse) throws InterruptedException
    {
        debugSpeakMsg(self, "attempting to play: " + parse[1]);
        play2dNonLoopingSound(self, "sound/" + parse[1] + ".snd");
    }
    public int instance_ping_response(obj_id self, dictionary params) throws InterruptedException
    {
        String message = params.getString("message");
        sendConsoleMessage(self, message);
        return SCRIPT_CONTINUE;
    }
    public void runFireTrigger(obj_id self, String[] parse) throws InterruptedException
    {
        trial.sendSequenceTrigger(parse[1]);
    }
    public void runSetCellLabel(obj_id self, String[] parse) throws InterruptedException
    {
        obj_id top = trial.getTop(self);
        obj_id cellId = getCellId(top, parse[1]);
        setCellLabel(cellId, parse[2]);
    }
    public void runClearWaypoints(obj_id self, String[] parse) throws InterruptedException
    {
        obj_id[] waypoints = getWaypointsInDatapad(self);
        if (waypoints != null && waypoints.length > 0)
        {
            for (obj_id waypoint : waypoints) {
                destroyWaypointInDatapad(waypoint, self);
            }
        }
    }
    public void runSetIdOnInstance(obj_id self, String[] parse) throws InterruptedException
    {
        obj_id instance_id = instance.getAreaInstanceController(self);
        instance.addToPlayerIdList(instance_id, self);
    }
    public void runHideFromClient(obj_id self, String[] parse) throws InterruptedException
    {
        obj_id target = getIntendedTarget(self);
        if (parse[1].equals("true"))
        {
            hideFromClient(self, true);
        }
        if (parse[1].equals("false"))
        {
            hideFromClient(self, false);
        }
    }
}
