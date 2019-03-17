package script.theme_park.dungeon.mustafar_trials.valley_battleground;

import script.dictionary;
import script.library.*;
import script.location;
import script.obj_id;

public class valley_event_manager extends script.base_script
{
    public valley_event_manager()
    {
    }
    public static final String STAGE = "currentStage";
    public static final boolean LOGGING = false;
    public int beginSpawn(obj_id self, dictionary params) throws InterruptedException
    {
        clearEventArea(self);
        dictionary dict = trial.getSessionDict(self, "da_control");
        dict.put("stage", 1);
        messageTo(self, "spawnNextStage", dict, 0, false);
        return SCRIPT_CONTINUE;
    }
    public int cleanupSpawn(obj_id self, dictionary params) throws InterruptedException
    {
        messageTo(self, "dungeonCleanup", null, 0.0f, false);
        return SCRIPT_CONTINUE;
    }
    public int dungeonCleanup(obj_id self, dictionary params) throws InterruptedException
    {
        clearEventArea(self);
        return SCRIPT_CONTINUE;
    }
    public void clearEventArea(obj_id self) throws InterruptedException
    {
        trial.setIsDroidArmyDefeated(self, false);
        trial.setIsCommanderKilled(self, false);
        trial.setIsGeneratorDestroyed(self, false);
        utils.setScriptVar(self, STAGE, 0);
        trial.bumpSession(self, "da_control");
        obj_id[] objects = getObjectsInRange(self, 400);
        if (objects == null || objects.length == 0)
        {
            return;
        }
        for (obj_id object : objects) {
            if (isIdValid(object) && object != self) {
                if (!isPlayer(object)) {
                    if (!isMob(object)) {
                        if (trial.isTempObject(object)) {
                            trial.cleanupNpc(object);
                        }
                    } else {
                        trial.cleanupNpc(object);
                    }
                }
            }
        }
    }
    public int spawnNextStage(obj_id self, dictionary params) throws InterruptedException
    {
        int nextStage = params.getInt("stage");
        if (!trial.verifySession(self, params, "da_control"))
        {
            return SCRIPT_CONTINUE;
        }
        spawnActors(self, nextStage);
        int[] stages = dataTableGetIntColumn(trial.VALLEY_DATA, "stage");
        if (stages == null || stages.length == 0)
        {
            doLogging("spawnNextStage", "Failed to get the stage column");
            return SCRIPT_CONTINUE;
        }
        boolean moreStages = false;
        for (int stage : stages) {
            if (stage > nextStage) {
                nextStage = stage;
                moreStages = true;
                break;
            }
        }
        if (moreStages)
        {
            dictionary dict = trial.getSessionDict(self, "da_control");
            dict.put("stage", nextStage);
            messageTo(self, "spawnNextStage", dict, trial.BATTLEFIELD_WAVE_DELAY, false);
            return SCRIPT_CONTINUE;
        }
        else 
        {
            return SCRIPT_CONTINUE;
        }
    }
    public void spawnActors(obj_id dungeon, int stage) throws InterruptedException
    {
        int rows = dataTableGetNumRows(trial.VALLEY_DATA);
        if (rows == 0)
        {
            return;
        }
        if (stage == 2)
        {
            instance.playMusicInInstance(dungeon, trial.MUS_BATTLEFIELD_DROID_ARMY_INTRO);
        }
        if (stage == 10)
        {
            messageTo(dungeon, "validateDungeon", trial.getSessionDict(dungeon, "da_control"), 60, false);
            instance.playMusicInInstance(dungeon, trial.MUS_BATTLEFIELD_DROID_ARMY_INTRO);
            instance.sendInstanceSystemMessage(dungeon, trial.BATTLEFIELD_CMNDR_INTRO);
        }
        for (int i = 0; i < rows; i++)
        {
            dictionary dict = dataTableGetRow(trial.VALLEY_DATA, i);
            if (dict.getInt("stage") == stage)
            {
                utils.setScriptVar(dungeon, STAGE, stage);
                location here = getLocation(dungeon);
                float locx = dict.getFloat("locx");
                float locy = dict.getFloat("locy");
                float locz = dict.getFloat("locz");
                float locX = here.x + locx;
                float locY = here.y + locy;
                float locZ = here.z + locz;
                float yaw = dict.getFloat("yaw");
                String script = dict.getString("script");
                String spawnScriptVar = dict.getString("scriptVar");
                location spawnLoc = new location(locX, locY, locZ, here.area, here.cell);
                String object = dict.getString("object");
                if (object.startsWith("object/"))
                {
                    obj_id item = createObject(object, spawnLoc);
                    if (!isIdValid(item))
                    {
                        doLogging("spawnActors", "Tried to create invalid item(" + object + ")");
                        return;
                    }
                    setYaw(item, yaw);
                    trial.markAsTempObject(item, true);
                    trial.setParent(dungeon, item, false);
                    if (!script.equals("none"))
                    {
                        attachSpawnScripts(item, script);
                    }
                    if (!spawnScriptVar.equals("none"))
                    {
                        setSpawnScriptVar(item, spawnScriptVar);
                    }
                }
                else 
                {
                    obj_id creature = create.object(object, spawnLoc);
                    if (!isIdValid(creature))
                    {
                        doLogging("spawnActors", "Tried to create invalid creature(" + object + ")");
                        return;
                    }
                    trial.setParent(dungeon, creature, false);
                    if (!spawnScriptVar.equals("none"))
                    {
                        setSpawnScriptVar(creature, spawnScriptVar);
                    }
                    if (!script.equals("none"))
                    {
                        attachSpawnScripts(creature, script);
                    }
                }
            }
        }
    }
    public void attachSpawnScripts(obj_id subject, String spawnScripts) throws InterruptedException
    {
        String[] scripts = split(spawnScripts, ':');
        for (String script : scripts) {
            attachScript(subject, script);
        }
    }
    public void setSpawnScriptVar(obj_id subject, String spawnScriptVar) throws InterruptedException
    {
        if (spawnScriptVar == null || spawnScriptVar.equals(""))
        {
            return;
        }
        String[] pairs = split(spawnScriptVar, ',');
        for (String pair : pairs) {
            String[] scriptVarToSet = split(pair, '=');
            String scriptVarValue = scriptVarToSet[1];
            String[] scriptVarNameAndType = split(scriptVarToSet[0], ':');
            String scriptVarType = scriptVarNameAndType[0];
            String scriptVarName = scriptVarNameAndType[1];
            switch (scriptVarType) {
                case "string":
                    utils.setScriptVar(subject, scriptVarName, scriptVarValue);
                    break;
                case "int":
                    utils.setScriptVar(subject, scriptVarName, utils.stringToInt(scriptVarValue));
                    break;
                case "float":
                    utils.setScriptVar(subject, scriptVarName, utils.stringToFloat(scriptVarValue));
                    break;
                case "boolean":
                case "bool":
                    utils.setScriptVar(subject, scriptVarName, scriptVarValue);
                    break;
            }
        }
    }
    public void debuffDroidArmy(obj_id self) throws InterruptedException
    {
        obj_id[] objects = getObjectsInRange(self, 400);
        if (objects == null || objects.length == 0)
        {
            return;
        }
        for (obj_id object : objects) {
            if (object != self) {
                if (utils.hasScriptVar(object, trial.BATTLEFIELD_DROID_ARMY)) {
                    if (buff.hasBuff(object, "high_morale")) {
                        buff.removeBuff(object, "high_morale");
                    }
                    buff.applyBuff(object, "low_morale");
                } else if (isPlayer(object) || utils.hasScriptVar(object, trial.BATTLEFIELD_MINER)) {
                    if (buff.hasBuff(object, "low_morale")) {
                        buff.removeBuff(object, "low_morale");
                    }
                    buff.applyBuff(object, "high_morale");
                }
            }
        }
    }
    public void debufMiners(obj_id self) throws InterruptedException
    {
        obj_id[] objects = getObjectsInRange(self, 400);
        if (objects == null || objects.length == 0)
        {
            return;
        }
        for (obj_id object : objects) {
            if (object != self) {
                if (utils.hasScriptVar(object, trial.BATTLEFIELD_DROID_ARMY)) {
                    if (!buff.hasBuff(object, "low_morale")) {
                        buff.applyBuff(object, "high_morale");
                    }
                } else if (isPlayer(object)) {
                    if (!buff.hasBuff(object, "high_morale")) {
                        buff.applyBuff(object, "low_morale");
                    }
                } else if (utils.hasScriptVar(object, trial.BATTLEFIELD_MINER)) {
                    if (buff.hasBuff(object, "high_morale")) {
                        buff.removeBuff(object, "high_morale");
                    }
                }
            }
        }
    }
    public int generatorDestroyed(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id[] players = instance.getPlayersInInstanceArea(self);
        if (players != null && players.length > 0)
        {
            utils.sendSystemMessage(players, trial.BATTLEFIELD_GENERATOR_DEAD);
        }
        messageTo(self, "handleDebufMiners", null, 10, false);
        spawnActors(self, -1);
        return SCRIPT_CONTINUE;
    }
    public int handleDebufMiners(obj_id self, dictionary params) throws InterruptedException
    {
        debufMiners(self);
        return SCRIPT_CONTINUE;
    }
    public void redirectArmy(obj_id self) throws InterruptedException
    {
        obj_id[] objects = getAllObjectsWithObjVar(getLocation(self), 400.0f, trial.WP_NAME);
        if (objects == null || objects.length == 0)
        {
            return;
        }
        location finalPoint = null;
        location playerExit = null;
        for (obj_id object : objects) {
            if ((getStringObjVar(object, trial.WP_NAME)).equals("end_point")) {
                finalPoint = getLocation(object);
            }
            if ((getStringObjVar(object, trial.WP_NAME)).equals("player_exit")) {
                playerExit = getLocation(object);
            }
        }
        obj_id[] army = trial.getObjectsInRangeWithScriptVar(self, trial.BATTLEFIELD_DROID_ARMY, 400.0f);
        if (army == null || army.length == 0)
        {
            return;
        }
        for (obj_id obj_id : army) {
            stop(obj_id);
            location[] newPath = new location[3];
            newPath[0] = getLocation(obj_id);
            newPath[1] = playerExit;
            newPath[2] = finalPoint;
            ai_lib.setPatrolOncePath(obj_id, newPath);
        }
    }
    public int commanderDied(obj_id self, dictionary params) throws InterruptedException
    {
        debuffDroidArmy(self);
        obj_id[] players = instance.getPlayersInInstanceArea(self);
        trial.setIsCommanderKilled(self, true);
        utils.sendSystemMessage(players, trial.BATTLEFIELD_COMMANDER_DIED);
        return SCRIPT_CONTINUE;
    }
    public int winTrial(obj_id self, dictionary params) throws InterruptedException
    {
        trial.setIsDroidArmyDefeated(self, true);
        trial.setDungeonCleanOutTimer(self);
        trial.sendCompletionSignal(self, trial.ARMY_WIN_SIGNAL);
        obj_id[] players = instance.getPlayersInInstanceArea(self);
        if (players == null || players.length == 0)
        {
            return SCRIPT_CONTINUE;
        }
        utils.sendSystemMessage(players, trial.BATTLEFIELD_WIN_MESSAGE);
        instance.playMusicInInstance(self, trial.MUS_MUST_QUEST_WIN);
        badge.grantBadge(players, "bdg_must_victory_army");
        for (obj_id player : players) {
            buff.applyBuff(player, "high_morale", 3600);
        }
        return SCRIPT_CONTINUE;
    }
    public int loseTrial(obj_id self, dictionary params) throws InterruptedException
    {
        instance.closeInstance(self);
        return SCRIPT_CONTINUE;
    }
    public int validateDungeon(obj_id self, dictionary params) throws InterruptedException
    {
        if (!trial.verifySession(self, params, "da_control"))
        {
            doLogging("validateDungeon", "Failed to verify session");
            return SCRIPT_CONTINUE;
        }
        if (trial.isCommanderKilled(self))
        {
            obj_id[] army = trial.getObjectsInRangeWithScriptVar(self, trial.BATTLEFIELD_DROID_ARMY, 400.0f);
            boolean livingDroid = false;
            if (army == null || army.length == 0)
            {
                messageTo(self, "winTrial", null, 0, false);
                return SCRIPT_CONTINUE;
            }
            int test = 0;
            for (obj_id obj_id : army) {
                if (!isDead(obj_id)) {
                    test += 1;
                    livingDroid = true;
                } else {
                    destroyObject(obj_id);
                }
            }
            if (!livingDroid)
            {
                messageTo(self, "winTrial", null, 0, false);
                return SCRIPT_CONTINUE;
            }
        }
        messageTo(self, "validateDungeon", trial.getSessionDict(self, "da_control"), 60, false);
        return SCRIPT_CONTINUE;
    }
    public void doLogging(String section, String message) throws InterruptedException
    {
        if (LOGGING || trial.VALLEY_LOGGING)
        {
            LOG("doLogging/valley_event_manager/" + section, message);
        }
    }
}
