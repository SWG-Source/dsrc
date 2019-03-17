package script.theme_park.dungeon.mustafar_trials.working_droid_factory;

import script.dictionary;
import script.library.*;
import script.location;
import script.obj_id;

public class working_controller extends script.base_script
{
    public working_controller()
    {
    }
    public static final String[] LOCKED_ROOMS = 
    {
        "mainroom27",
        "hall2",
        "hall7",
        "hall13",
        "centralroom28"
    };
    public static final String BESH_ROOM = "smallroom24";
    public static final String AUREK_ROOM = "smallroom21";
    public static final boolean LOGGING = true;
    public int OnAttach(obj_id self) throws InterruptedException
    {
        return SCRIPT_CONTINUE;
    }
    public int beginSpawn(obj_id self, dictionary params) throws InterruptedException
    {
        setEventLocks(self);
        setEventStates(self);
        messageTo(self, "spawnGuardians", null, 4, false);
        messageTo(self, "spawnDevistator", null, 4, false);
        messageTo(self, "spawnDroidEngineer", null, 4, false);
        messageTo(self, "spawnDoomBringer", null, 4, false);
        return SCRIPT_CONTINUE;
    }
    public int dungeonCleanup(obj_id self, dictionary params) throws InterruptedException
    {
        return SCRIPT_CONTINUE;
    }
    public int remoteCommand(obj_id self, dictionary params) throws InterruptedException
    {
        String command = "null";
        command = params.getString("command");
        if (command.equals("setLock"))
        {
            setEventLocks(self);
            return SCRIPT_CONTINUE;
        }
        if (command.equals("removeLock"))
        {
            removeEventLocks(self);
            return SCRIPT_CONTINUE;
        }
        if (command.equals("lockCell"))
        {
            lockCell(self, params);
            return SCRIPT_CONTINUE;
        }
        if (command.equals("setDefaultStates"))
        {
            setEventLocks(self);
            return SCRIPT_CONTINUE;
        }
        if (command.equals("removeEventStates"))
        {
            removeEventStates(self);
            return SCRIPT_CONTINUE;
        }
        return SCRIPT_CONTINUE;
    }
    public int spawnGuardians(obj_id self, dictionary params) throws InterruptedException
    {
        String guard1 = "som_working_hk_58_aurek";
        String guard2 = "som_working_hk_58_besh";
        obj_id[] bossWp = trial.getObjectsInDungeonWithObjVar(self, "boss_wp");
        if (bossWp == null || bossWp.length == 0)
        {
            doLogging("spawnGuardians", "No bossWp could be found");
            return SCRIPT_CONTINUE;
        }
        location loc1 = null;
        location loc2 = null;
        for (obj_id obj_id : bossWp) {
            if ((getStringObjVar(obj_id, "boss_wp")).equals("aurek")) {
                loc1 = getLocation(obj_id);
            }
            if ((getStringObjVar(obj_id, "boss_wp")).equals("besh")) {
                loc2 = getLocation(obj_id);
            }
        }
        if (loc1 == null || loc2 == null)
        {
            doLogging("spawnGuardians", "Failed to find location for guardian(" + loc1 + "/" + loc2 + ")");
            return SCRIPT_CONTINUE;
        }
        obj_id aurek = create.object(guard1, loc1);
        setYaw(aurek, 270);
        utils.setScriptVar(aurek, "name", "aurek");
        attachScript(aurek, "theme_park.dungeon.mustafar_trials.working_droid_factory.aurek_besh");
        obj_id besh = create.object(guard2, loc2);
        setYaw(besh, 90);
        utils.setScriptVar(besh, "name", "besh");
        attachScript(besh, "theme_park.dungeon.mustafar_trials.working_droid_factory.aurek_besh");
        return SCRIPT_CONTINUE;
    }
    public int spawnDevistator(obj_id self, dictionary params) throws InterruptedException
    {
        String devistatorString = "som_working_devistator";
        obj_id[] bossWp = trial.getObjectsInDungeonWithObjVar(self, "boss_wp");
        if (bossWp == null || bossWp.length == 0)
        {
            doLogging("spawnDevistator", "No bossWp could be found");
            return SCRIPT_CONTINUE;
        }
        location devistatorLoc = null;
        location reactiveRepairLoc = null;
        location inhibitorSupplyLoc = null;
        for (obj_id obj_id : bossWp) {
            if ((getStringObjVar(obj_id, "boss_wp")).equals("devistator")) {
                devistatorLoc = getLocation(obj_id);
            }
            if ((getStringObjVar(obj_id, "boss_wp")).equals("reactive_repair_unit")) {
                reactiveRepairLoc = getLocation(obj_id);
            }
            if ((getStringObjVar(obj_id, "boss_wp")).equals("inhibitor_supply")) {
                inhibitorSupplyLoc = getLocation(obj_id);
            }
        }
        if (devistatorLoc == null || reactiveRepairLoc == null || inhibitorSupplyLoc == null)
        {
            doLogging("spawnDevistator", "Failed to find location for devistaor(" + devistatorLoc + "/" + reactiveRepairLoc + "/" + inhibitorSupplyLoc + ")");
            return SCRIPT_CONTINUE;
        }
        obj_id devistator = create.object(devistatorString, devistatorLoc);
        setYaw(devistator, 180);
        attachScript(devistator, "theme_park.dungeon.mustafar_trials.working_droid_factory.devistator_boss");
        obj_id rru = create.object(trial.OBJECT_REACTIVE_REPAIR, reactiveRepairLoc);
        setYaw(rru, 180);
        attachScript(rru, "theme_park.dungeon.mustafar_trials.working_droid_factory.devistator_repair");
        trial.markAsTempObject(rru, true);
        obj_id inhibitorSupply = create.object(trial.OBJECT_INHIBITOR, inhibitorSupplyLoc);
        attachScript(inhibitorSupply, "theme_park.dungeon.mustafar_trials.working_droid_factory.devistator_inhibitor");
        trial.markAsTempObject(inhibitorSupply, false);
        return SCRIPT_CONTINUE;
    }
    public int spawnDroidEngineer(obj_id self, dictionary params) throws InterruptedException
    {
        String droidEngineerString = "som_working_master_droid_engineer";
        obj_id[] bossWp = trial.getObjectsInDungeonWithObjVar(self, "boss_wp");
        if (bossWp == null || bossWp.length == 0)
        {
            doLogging("spawnDroidEngieer", "No bossWp could be found");
            return SCRIPT_CONTINUE;
        }
        location mdeLoc = null;
        location clonerOneLoc = null;
        location clonerTwoLoc = null;
        location clonerOneExit = null;
        location clonerTwoExit = null;
        for (obj_id obj_id : bossWp) {
            if ((getStringObjVar(obj_id, "boss_wp")).equals("droid_engineer")) {
                mdeLoc = getLocation(obj_id);
            }
            if ((getStringObjVar(obj_id, "boss_wp")).equals("cloner1")) {
                clonerOneLoc = getLocation(obj_id);
            }
            if ((getStringObjVar(obj_id, "boss_wp")).equals("cloner2")) {
                clonerTwoLoc = getLocation(obj_id);
            }
            if ((getStringObjVar(obj_id, "boss_wp")).equals("cloner_exit_1")) {
                clonerOneExit = getLocation(obj_id);
            }
            if ((getStringObjVar(obj_id, "boss_wp")).equals("cloner_exit_2")) {
                clonerTwoExit = getLocation(obj_id);
            }
        }
        if (mdeLoc == null || clonerOneLoc == null || clonerTwoLoc == null || clonerOneExit == null || clonerTwoExit == null)
        {
            doLogging("spawnDroidEngineer", "Failed to find location for devistaor(" + mdeLoc + "/" + clonerOneLoc + "/" + clonerTwoLoc + "/" + clonerOneExit + "/" + clonerTwoExit + ")");
            return SCRIPT_CONTINUE;
        }
        obj_id mde = create.object(droidEngineerString, mdeLoc);
        attachScript(mde, "theme_park.dungeon.mustafar_trials.working_droid_factory.master_droid_engineer");
        setYaw(mde, 121);
        obj_id clonerOne = create.object(trial.OBJECT_DE_CLONER, clonerOneLoc);
        attachScript(clonerOne, "theme_park.dungeon.mustafar_trials.working_droid_factory.rapid_assembly_unit");
        utils.setScriptVar(clonerOne, trial.WORKING_CLONER_EXIT, clonerOneExit);
        trial.markAsTempObject(clonerOne, false);
        obj_id clonerTwo = create.object(trial.OBJECT_DE_CLONER, clonerTwoLoc);
        attachScript(clonerTwo, "theme_park.dungeon.mustafar_trials.working_droid_factory.rapid_assembly_unit");
        utils.setScriptVar(clonerTwo, trial.WORKING_CLONER_EXIT, clonerTwoExit);
        trial.markAsTempObject(clonerTwo, false);
        return SCRIPT_CONTINUE;
    }
    public int spawnDoomBringer(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id[] bossWp = trial.getObjectsInDungeonWithObjVar(self, "boss_wp");
        if (bossWp == null || bossWp.length == 0)
        {
            doLogging("spawnDoomBringer", "No bossWp could be found");
            return SCRIPT_CONTINUE;
        }
        location doomBringerLoc = null;
        location destructionPileLoc = null;
        for (obj_id obj_id : bossWp) {
            if ((getStringObjVar(obj_id, "boss_wp")).equals("doom_bringer")) {
                doomBringerLoc = getLocation(obj_id);
            }
            if ((getStringObjVar(obj_id, "boss_wp")).equals("destruction_pile")) {
                destructionPileLoc = getLocation(obj_id);
            }
        }
        if (doomBringerLoc == null || destructionPileLoc == null)
        {
            doLogging("spawnDoomBringer", "Failed to find location for doom bringer(" + doomBringerLoc + "/" + destructionPileLoc + ")");
            return SCRIPT_CONTINUE;
        }
        obj_id doomBringer = create.object("som_working_doom_bringer", doomBringerLoc);
        attachScript(doomBringer, "theme_park.dungeon.mustafar_trials.working_droid_factory.doom_bringer");
        obj_id destructionPile = create.object(trial.OBJECT_DECTRUCT_PILE, destructionPileLoc);
        attachScript(destructionPile, "theme_park.dungeon.mustafar_trials.working_droid_factory.doom_target");
        trial.markAsTempObject(destructionPile, false);
        messageTo(self, "spawnDoomGuards", null, 0, false);
        return SCRIPT_CONTINUE;
    }
    public int spawnDoomGuards(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id[] bossWp = trial.getObjectsInDungeonWithObjVar(self, "boss_wp");
        if (bossWp == null || bossWp.length == 0)
        {
            return SCRIPT_CONTINUE;
        }
        int k = 0;
        for (obj_id obj_id : bossWp) {
            if ((getStringObjVar(obj_id, "boss_wp")).equals("watcher" + k)) {
                obj_id watcher = create.object("som_working_hand_of_doom", getLocation(obj_id));
                attachScript(watcher, "theme_park.dungeon.mustafar_trials.working_droid_factory.doom_hand");
                k++;
            }
        }
        return SCRIPT_CONTINUE;
    }
    public void setEventLocks(obj_id dungeon) throws InterruptedException
    {
        for (String lockedRoom : LOCKED_ROOMS) {
            permissionsMakePrivate(getCellId(dungeon, lockedRoom));
        }
    }
    public void removeEventLocks(obj_id dungeon) throws InterruptedException
    {
        for (String lockedRoom : LOCKED_ROOMS) {
            permissionsMakePublic(getCellId(dungeon, lockedRoom));
        }
    }
    public void setEventStates(obj_id dungeon) throws InterruptedException
    {
        trial.setAurekKilled(dungeon, false);
        trial.clearAurekBeshKillTime(dungeon);
        trial.setBeshKilled(dungeon, false);
        trial.setDevistatorKilled(dungeon, false);
        trial.setMdeKilled(dungeon, false);
        trial.setMdeEngaged(dungeon, false);
        trial.setPowerCoreDeactivated(dungeon, false);
        trial.setDoomBringerKilled(dungeon, false);
        trial.setRruDeactivated(dungeon, false);
        trial.setDevistatorEngaged(dungeon, false);
        trial.setHkSpawned(dungeon, false);
        trial.setHkDetonated(dungeon, false);
    }
    public int validateAurekBeshKill(obj_id self, dictionary params) throws InterruptedException
    {
        if (!trial.verifySession(self, params, "validate_ab_kill"))
        {
            return SCRIPT_CONTINUE;
        }
        boolean aurekKilled = trial.isAurekKilled(self);
        boolean beshKilled = trial.isBeshKilled(self);
        if (!aurekKilled && !beshKilled)
        {
            doLogging("validateAurekBeshKill", "Neither aurek nor besh are dead, this is wrong");
            return SCRIPT_CONTINUE;
        }
        int killTime = trial.getAurekBeshKillTime(self);
        if (killTime == 0)
        {
            trial.setAurekBeshKillTime(self);
            messageTo(self, "validateAurekBeshKill", trial.getSessionDict(self, "validate_ab_kill"), trial.WORKING_AUREKBESH_RESPAWN_DELAY, false);
            return SCRIPT_CONTINUE;
        }
        else 
        {
            int difference = getGameTime() - killTime;
            if (difference >= trial.WORKING_AUREKBESH_RESPAWN_DELAY)
            {
                if (aurekKilled)
                {
                    trial.bumpSession(self, "validate_ab_kill");
                    respawnAurek(self);
                    utils.setScriptVar(self, trial.WORKING_AUREKBESH_KILL_TIME, 0);
                    return SCRIPT_CONTINUE;
                }
                else 
                {
                    trial.bumpSession(self, "validate_ab_kill");
                    respawnBesh(self);
                    utils.setScriptVar(self, trial.WORKING_AUREKBESH_KILL_TIME, 0);
                    return SCRIPT_CONTINUE;
                }
            }
            else 
            {
                if (aurekKilled && beshKilled)
                {
                    guardiansDefeated(self);
                }
                else 
                {
                    doLogging("validateAurekBeshKill", "This message came in less than 30 seconds, but both guardians aren't dead... wtf");
                    messageTo(self, "validateAurekBeshKill", trial.getSessionDict(self, "validate_ab_kill"), 5, false);
                }
            }
        }
        return SCRIPT_CONTINUE;
    }
    public void respawnAurek(obj_id dungeon) throws InterruptedException
    {
        trial.setAurekKilled(dungeon, false);
        obj_id[] bossWp = trial.getObjectsInDungeonWithObjVar(dungeon, "boss_wp");
        if (bossWp == null || bossWp.length == 0)
        {
            doLogging("respawnBesh", "No bossWp could be found");
            return;
        }
        location loc1 = null;
        for (obj_id obj_id : bossWp) {
            if ((getStringObjVar(obj_id, "boss_wp")).equals("aurek")) {
                loc1 = getLocation(obj_id);
            }
        }
        if (loc1 == null)
        {
            doLogging("respawnAurek", "Failed to find location for guardian(" + loc1 + ")");
            return;
        }
        obj_id aurek = create.object("som_working_hk_58_aurek", loc1);
        setYaw(aurek, 270);
        utils.setScriptVar(aurek, "name", "aurek");
        attachScript(aurek, "theme_park.dungeon.mustafar_trials.working_droid_factory.aurek_besh");
        utils.sendSystemMessagePob(dungeon, trial.TWINS_RESPAWN);
    }
    public void respawnBesh(obj_id dungeon) throws InterruptedException
    {
        trial.setBeshKilled(dungeon, false);
        obj_id[] bossWp = trial.getObjectsInDungeonWithObjVar(dungeon, "boss_wp");
        if (bossWp == null || bossWp.length == 0)
        {
            doLogging("respawnBesh", "No bossWp could be found");
            return;
        }
        location loc1 = null;
        for (obj_id obj_id : bossWp) {
            if ((getStringObjVar(obj_id, "boss_wp")).equals("besh")) {
                loc1 = getLocation(obj_id);
            }
        }
        if (loc1 == null)
        {
            doLogging("respawnBesh", "Failed to find location for guardian(" + loc1 + ")");
            return;
        }
        obj_id aurek = create.object("som_working_hk_58_besh", loc1);
        setYaw(aurek, 90);
        utils.setScriptVar(aurek, "name", "besh");
        attachScript(aurek, "theme_park.dungeon.mustafar_trials.working_droid_factory.aurek_besh");
        utils.sendSystemMessagePob(dungeon, trial.TWINS_RESPAWN);
    }
    public void guardiansDefeated(obj_id dungeon) throws InterruptedException
    {
        utils.sendSystemMessagePob(dungeon, trial.TWINS_DEFEATED);
        trial.bumpSession(dungeon, "validate_ab_kill");
        trial.setAurekBeshEngaged(dungeon, false);
    }
    public int devistatorKilled(obj_id self, dictionary params) throws InterruptedException
    {
        trial.setDevistatorKilled(self, true);
        trial.makeCellPublic(self, trial.WORKING_TWO_THREE_STAIR);
        utils.sendSystemMessagePob(self, trial.WORKING_DEVISTATOR_DEFEATED);
        obj_id[] players = instance.getPlayersInInstanceArea(self);
        return SCRIPT_CONTINUE;
    }
    public int mdeKilled(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id[] fixers = trial.getObjectsInDungeonWithScript(self, "theme_park.dungeon.mustafar_trials.working_droid_factory.mde_repair_droid");
        for (int i = 0; i < fixers.length; i++)
        {
            trial.bumpSession(fixers[i]);
            stop(fixers[i]);
            utils.setScriptVar(fixers[i], "fixerNum", i);
            utils.messageTo(fixers, "formFixerOne", params, 3, false);
        }
        return SCRIPT_CONTINUE;
    }
    public int fixerOneKilled(obj_id self, dictionary params) throws InterruptedException
    {
        trial.setMdeKilled(self, true);
        trial.makeCellPublic(self, trial.WORKING_DE_HALL);
        obj_id[] players = instance.getPlayersInInstanceArea(self);
        return SCRIPT_CONTINUE;
    }
    public void removeEventStates(obj_id dungeon) throws InterruptedException
    {
    }
    public int doomBringerKilled(obj_id self, dictionary params) throws InterruptedException
    {
        trial.bumpSession(self, "db_control");
        trial.setDoomBringerKilled(self, true);
        trial.makeCellPublic(self, trial.WORKING_MASTER_CONTROL);
        utils.sendSystemMessagePob(self, trial.WORKING_DOOM_DEFEATED);
        trial.setDungeonCleanOutTimer(self);
        trial.sendCompletionSignal(self, trial.WORKING_WIN_SIGNAL);
        obj_id[] players = instance.getPlayersInInstanceArea(self);
        badge.grantBadge(players, "bdg_must_victory_odf");
        return SCRIPT_CONTINUE;
    }
    public int doomTargetDestroyed(obj_id self, dictionary params) throws InterruptedException
    {
        utils.sendSystemMessagePob(self, trial.WORKING_DOOM_FAILURE);
        obj_id[] players = trial.getPlayersInDungeon(trial.getTop(self));
        if (players == null || players.length == 0)
        {
            return SCRIPT_CONTINUE;
        }
        obj_id[] objects = trial.getObjectsInDungeonWithObjVar(self, "boss_wp");
        for (obj_id object : objects) {
            if ((getStringObjVar(object, "boss_wp")).equals("doom_bringer")) {
                playClientEffectLoc(players[0], trial.PRT_WORKING_HK_BOOM_1, getLocation(object), 1.0f);
            }
            if ((getStringObjVar(object, "boss_wp")).equals("destruction_pile")) {
                playClientEffectLoc(players[0], trial.PRT_WORKING_HK_BOOM_1, getLocation(object), 1.0f);
            }
        }
        messageTo(self, "handlePlayerForceRevive", null, 3, false);
        return SCRIPT_CONTINUE;
    }
    public int handlePlayerForceRevive(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id[] players = trial.getPlayersInDungeon(trial.getTop(self));
        if (players == null || players.length == 0)
        {
            return SCRIPT_CONTINUE;
        }
        instance.closeInstance(self);
        return SCRIPT_CONTINUE;
    }
    public void lockCell(obj_id dungeon, dictionary params) throws InterruptedException
    {
        String cell = params.getString("cell");
        trial.makeCellPrivate(dungeon, cell);
    }
    public int spawnHK(obj_id self, dictionary params) throws InterruptedException
    {
        if (!trial.verifySession(self, params, "spawn_hk"))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id[] wp = trial.getObjectsInDungeonWithObjVar(self, "hk_sequence");
        if (wp == null || wp.length == 0)
        {
            doLogging("spawnHK", "There are no hk_sequence patrol objects");
            return SCRIPT_CONTINUE;
        }
        location startLoc = null;
        location endLoc = null;
        for (obj_id obj_id : wp) {
            if ((getStringObjVar(obj_id, "hk_sequence")).equals("hk_spawn")) {
                startLoc = getLocation(obj_id);
            }
            if ((getStringObjVar(obj_id, "hk_sequence")).equals("hk_moveto")) {
                endLoc = getLocation(obj_id);
            }
        }
        if (startLoc == null || endLoc == null)
        {
            doLogging("spawnHK", "Could not find startloc or end loc: " + startLoc + "/" + endLoc);
            return SCRIPT_CONTINUE;
        }
        obj_id HK = create.object("som_volcano_final_hk47", startLoc);
        if (!isIdValid(HK))
        {
            doLogging("spawnHK", "HK Creation failed");
            return SCRIPT_CONTINUE;
        }
        trial.bumpSession(self, "spawn_hk");
        messageTo(trial.getTop(self), "detonateHK", trial.getSessionDict(trial.getTop(self), "spawn_hk"), 20, false);
        trial.setHkSpawned(trial.getTop(self), true);
        setInvulnerable(HK, true);
        ai_lib.aiPathTo(HK, endLoc);
        attachScript(HK, "theme_park.dungeon.mustafar_trials.working_droid_factory.hk_movement");
        messageTo(HK, "setRun", null, 1, false);
        aiSetHomeLocation(HK, endLoc);
        return SCRIPT_CONTINUE;
    }
    public int detonateHK(obj_id self, dictionary params) throws InterruptedException
    {
        if (!trial.verifySession(self, params, "spawn_hk"))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id[] wp = trial.getObjectsInDungeonWithObjVar(self, "hk_sequence");
        if (wp == null || wp.length == 0)
        {
            doLogging("detonateHk", "There are no hk_sequence patrol objects");
            return SCRIPT_CONTINUE;
        }
        location[] fireLoc = new location[3];
        location blastLoc = null;
        for (obj_id obj_id1 : wp) {
            if ((getStringObjVar(obj_id1, "hk_sequence")).equals("fire1")) {
                fireLoc[0] = getLocation(obj_id1);
            }
            if ((getStringObjVar(obj_id1, "hk_sequence")).equals("fire2")) {
                fireLoc[1] = getLocation(obj_id1);
            }
            if ((getStringObjVar(obj_id1, "hk_sequence")).equals("fire3")) {
                fireLoc[2] = getLocation(obj_id1);
            }
            if ((getStringObjVar(obj_id1, "hk_sequence")).equals("player_blast_trigger")) {
                blastLoc = getLocation(obj_id1);
            }
        }
        if (blastLoc == null || fireLoc[0] == null || fireLoc[1] == null || fireLoc[2] == null)
        {
            doLogging("detonateHK", "One of the blast WP was null");
            return SCRIPT_CONTINUE;
        }
        obj_id[] players = trial.getPlayersInDungeon(trial.getTop(self));
        obj_id[] playersInBlast = getPlayerCreaturesInRange(blastLoc, 10);
        if (players == null || players.length == 0)
        {
            doLogging("detonateHk", "There are no players in the dungeon");
            return SCRIPT_CONTINUE;
        }
        playClientEffectLoc(players[0], trial.PRT_WORKING_HK_BOOM_1, fireLoc[1], 1.0f);
        if (playersInBlast != null && playersInBlast.length > 0)
        {
            for (obj_id obj_id : playersInBlast) {
                buff.applyBuff(obj_id, "stop", 5);
                warpPlayer(obj_id, blastLoc.area, 48, -24, -1, trial.getTop(self), trial.WORKING_MAIN_HALL, 48, -24, -1, "nullCallBack", false);
            }
        }
        trial.bumpSession(self, "spawn_hk");
        trial.setHkDetonated(self, true);
        utils.setScriptVar(self, "wps", fireLoc);
        utils.setScriptVar(self, "viewer", players[0]);
        messageTo(self, "doExplosionTwo", null, 1, false);
        messageTo(self, "doExplosionThree", null, 3, false);
        return SCRIPT_CONTINUE;
    }
    public int doExplosionTwo(obj_id self, dictionary params) throws InterruptedException
    {
        location[] wp = utils.getLocationArrayScriptVar(self, "wps");
        obj_id viewer = utils.getObjIdScriptVar(self, "viewer");
        playClientEffectLoc(viewer, trial.PRT_WORKING_HK_BOOM_2, wp[0], 1.0f);
        return SCRIPT_CONTINUE;
    }
    public int doExplosionThree(obj_id self, dictionary params) throws InterruptedException
    {
        location[] wp = utils.getLocationArrayScriptVar(self, "wps");
        obj_id viewer = utils.getObjIdScriptVar(self, "viewer");
        playClientEffectLoc(viewer, trial.PRT_WORKING_HK_BOOM_3, wp[2], 1.0f);
        messageTo(self, "doHkTaunt", null, 2, false);
        return SCRIPT_CONTINUE;
    }
    public int doHkTaunt(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id[] hk = trial.getObjectsInDungeonWithScript(trial.getTop(self), "theme_park.dungeon.mustafar_trials.working_droid_factory.hk_movement");
        obj_id[] doomBringer = trial.getObjectsInDungeonWithScript(trial.getTop(self), "theme_park.dungeon.mustafar_trials.working_droid_factory.doom_bringer");
        if (hk == null || hk.length == 0 || doomBringer == null || doomBringer.length == 0)
        {
            doLogging("doHkTaunt", "HK or Doombringer was null");
            return SCRIPT_CONTINUE;
        }
        messageTo(hk[0], "doTaunt", null, 0, false);
        messageTo(doomBringer[0], "beginDestruction", null, 12, false);
        return SCRIPT_CONTINUE;
    }
    public int startDuality(obj_id self, dictionary params) throws InterruptedException
    {
        messageTo(self, "performDuality", trial.getSessionDict(self, "duality"), 12.0f, false);
        return SCRIPT_CONTINUE;
    }
    public int performDuality(obj_id self, dictionary params) throws InterruptedException
    {
        if (!trial.verifySession(self, params, "duality"))
        {
            doLogging("performDuality", "Failed to verify session");
            return SCRIPT_CONTINUE;
        }
        obj_id[] twins = trial.getObjectsInDungeonWithScriptVar(self, "name");
        obj_id aurek = null;
        obj_id besh = null;
        for (obj_id twin : twins) {
            if ((utils.getStringScriptVar(twin, "name")).equals("aurek")) {
                aurek = twin;
            }
            if ((utils.getStringScriptVar(twin, "name")).equals("besh")) {
                besh = twin;
            }
        }
        if (!isIdValid(aurek) || !isIdValid(besh) || ai_lib.isDead(aurek) || ai_lib.isDead(besh))
        {
            doLogging("performDuality", "Invalid ObjId for aurek or best or one is dead");
            trial.bumpSession(self, "duality");
            return SCRIPT_CONTINUE;
        }
        if (!ai_lib.isInCombat(aurek) && !ai_lib.isInCombat(besh))
        {
            doLogging("performDuality", "Ending duality for lack of combat");
            trial.bumpSession(self, "duality");
            return SCRIPT_CONTINUE;
        }
        obj_id[] aurekPlayers = trial.getValidTargetsInCell(self, AUREK_ROOM);
        obj_id[] beshPlayers = trial.getValidTargetsInCell(self, BESH_ROOM);
        obj_id[] centralPlayers = trial.getValidTargetsInCell(self, "mainroom27");
        if (aurekPlayers == null || aurekPlayers.length == 0 || beshPlayers == null || beshPlayers.length == 0)
        {
            utils.messageTo(centralPlayers, "dualityDetonation", null, 0.0f, false);
            messageTo(self, "performDuality", trial.getSessionDict(self, "duality"), 12.0f, false);
            return SCRIPT_CONTINUE;
        }
        if (centralPlayers != null && centralPlayers.length > 0)
        {
            utils.messageTo(centralPlayers, "dualityDetonation", null, 0.0f, false);
        }
        buff.applyBuff(aurekPlayers, "aurekDuality");
        buff.applyBuff(aurek, "aurekDuality", 21.0f);
        buff.applyBuff(beshPlayers, "beshDuality");
        buff.applyBuff(besh, "beshDuality", 21.0f);
        messageTo(self, "performDuality", trial.getSessionDict(self, "duality"), 45.0f, false);
        return SCRIPT_CONTINUE;
    }
    public void doLogging(String section, String message) throws InterruptedException
    {
        if (LOGGING)
        {
            LOG("doLogging/working_controller/" + section, message);
        }
    }
}
