package script.theme_park.dungeon.mustafar_trials.working_droid_factory;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.trial;
import script.library.utils;
import script.library.create;

public class rapid_assembly_unit extends script.base_script
{
    public rapid_assembly_unit()
    {
    }
    public static final String DROID_COMBAT = "som_working_blastromech";
    public static final String DROID_ASSASSIN = "som_working_assassin_droid";
    public static final String DROID_BOMB = "som_working_detonation_droid";
    public static final String DROID_REPAIR = "som_working_repair_droid";
    public static final int STAGE_COMBAT = 0;
    public static final int STAGE_ASSASSIN = 1;
    public static final int STAGE_REPAIR = 2;
    public static final int STAGE_COMBAT_2 = 3;
    public static final int STAGE_BOMB = 4;
    public static final int STAGE_ASSASSIN_2 = 5;
    public static final int STAGE_BOMB_2 = 6;
    public static final String IS_ACTIVE = "terminal.isActive";
    public static final int ACTIVE_RESPAWN = 36;
    public int OnAttach(obj_id self) throws InterruptedException
    {
        trial.setHp(self, trial.HP_WORKING_ASSEMBLY);
        setInvulnerable(self, true);
        return SCRIPT_CONTINUE;
    }
    public int OnObjectDisabled(obj_id self, obj_id killer) throws InterruptedException
    {
        deactivateTerminal(self);
        return SCRIPT_CONTINUE;
    }
    public int triggerEvent(obj_id self, dictionary params) throws InterruptedException
    {
        if (!trial.verifySession(trial.getTop(self), params, "mde_control"))
        {
            return SCRIPT_CONTINUE;
        }
        trial.bumpSession(self);
        messageTo(self, "doEventSpawn", trial.getSessionDict(self), 0, false);
        return SCRIPT_CONTINUE;
    }
    public int doEventSpawn(obj_id self, dictionary params) throws InterruptedException
    {
        if (!trial.verifySession(self, params))
        {
            return SCRIPT_CONTINUE;
        }
        if (!trial.isMdeEngaged(self))
        {
            return SCRIPT_CONTINUE;
        }
        setInvulnerable(self, false);
        int stage = 0;
        if (utils.hasScriptVar(self, trial.WORKING_ASSEMBLY_STAGE))
        {
            stage = utils.getIntScriptVar(self, trial.WORKING_ASSEMBLY_STAGE);
        }
        location spawnLoc = utils.findLocInFrontOfTarget(self, 3);
        location exitLoc = utils.getLocationScriptVar(self, trial.WORKING_CLONER_EXIT);
        obj_id creature = obj_id.NULL_ID;
        obj_id creature1 = obj_id.NULL_ID;
        obj_id creature2 = obj_id.NULL_ID;
        obj_id creature3 = obj_id.NULL_ID;
        switch (stage)
        {
            case STAGE_COMBAT:
            creature = create.object(DROID_COMBAT, spawnLoc);
            attachScript(creature, "theme_park.dungeon.mustafar_trials.working_droid_factory.mde_combat_droid");
            utils.setScriptVar(creature, trial.WORKING_MDE_MOB, true);
            utils.setScriptVar(creature, trial.WORKING_CLONER_EXIT, exitLoc);
            creature1 = create.object(DROID_COMBAT, spawnLoc);
            attachScript(creature1, "theme_park.dungeon.mustafar_trials.working_droid_factory.mde_combat_droid");
            utils.setScriptVar(creature1, trial.WORKING_MDE_MOB, true);
            utils.setScriptVar(creature1, trial.WORKING_CLONER_EXIT, exitLoc);
            messageTo(self, "doEventSpawn", trial.getSessionDict(self), ACTIVE_RESPAWN, false);
            return SCRIPT_CONTINUE;
            case STAGE_ASSASSIN:
            creature = create.object(DROID_ASSASSIN, spawnLoc);
            attachScript(creature, "theme_park.dungeon.mustafar_trials.working_droid_factory.mde_assassin_droid");
            utils.setScriptVar(creature, trial.WORKING_MDE_MOB, true);
            utils.setScriptVar(creature, trial.WORKING_CLONER_EXIT, exitLoc);
            creature1 = create.object(DROID_ASSASSIN, spawnLoc);
            attachScript(creature1, "theme_park.dungeon.mustafar_trials.working_droid_factory.mde_assassin_droid");
            utils.setScriptVar(creature1, trial.WORKING_MDE_MOB, true);
            utils.setScriptVar(creature1, trial.WORKING_CLONER_EXIT, exitLoc);
            messageTo(self, "doEventSpawn", trial.getSessionDict(self), ACTIVE_RESPAWN, false);
            return SCRIPT_CONTINUE;
            case STAGE_REPAIR:
            creature1 = create.object(DROID_REPAIR, spawnLoc);
            creature2 = create.object(DROID_REPAIR, spawnLoc);
            attachScript(creature1, "theme_park.dungeon.mustafar_trials.working_droid_factory.mde_repair_droid");
            attachScript(creature2, "theme_park.dungeon.mustafar_trials.working_droid_factory.mde_repair_droid");
            utils.setScriptVar(creature1, trial.WORKING_MDE_MOB, true);
            utils.setScriptVar(creature2, trial.WORKING_MDE_MOB, true);
            return SCRIPT_CONTINUE;
            case STAGE_COMBAT_2:
            creature = create.object(DROID_COMBAT, spawnLoc);
            attachScript(creature, "theme_park.dungeon.mustafar_trials.working_droid_factory.mde_combat_droid");
            utils.setScriptVar(creature, trial.WORKING_MDE_MOB, true);
            utils.setScriptVar(creature, trial.WORKING_CLONER_EXIT, exitLoc);
            creature1 = create.object(DROID_COMBAT, spawnLoc);
            attachScript(creature1, "theme_park.dungeon.mustafar_trials.working_droid_factory.mde_combat_droid");
            utils.setScriptVar(creature1, trial.WORKING_MDE_MOB, true);
            utils.setScriptVar(creature1, trial.WORKING_CLONER_EXIT, exitLoc);
            messageTo(self, "doEventSpawn", trial.getSessionDict(self), ACTIVE_RESPAWN, false);
            return SCRIPT_CONTINUE;
            case STAGE_BOMB:
            creature1 = create.object(DROID_BOMB, spawnLoc);
            creature2 = create.object(DROID_BOMB, spawnLoc);
            creature3 = create.object(DROID_BOMB, spawnLoc);
            attachScript(creature1, "theme_park.dungeon.mustafar_trials.working_droid_factory.mde_bomb_droid");
            attachScript(creature2, "theme_park.dungeon.mustafar_trials.working_droid_factory.mde_bomb_droid");
            attachScript(creature3, "theme_park.dungeon.mustafar_trials.working_droid_factory.mde_bomb_droid");
            messageTo(self, "doEventSpawn", trial.getSessionDict(self), ACTIVE_RESPAWN, false);
            utils.setScriptVar(creature1, trial.WORKING_MDE_MOB, true);
            utils.setScriptVar(creature2, trial.WORKING_MDE_MOB, true);
            utils.setScriptVar(creature3, trial.WORKING_MDE_MOB, true);
            utils.setScriptVar(creature1, trial.WORKING_CLONER_EXIT, exitLoc);
            utils.setScriptVar(creature2, trial.WORKING_CLONER_EXIT, exitLoc);
            utils.setScriptVar(creature3, trial.WORKING_CLONER_EXIT, exitLoc);
            return SCRIPT_CONTINUE;
            case STAGE_ASSASSIN_2:
            creature = create.object(DROID_ASSASSIN, spawnLoc);
            attachScript(creature, "theme_park.dungeon.mustafar_trials.working_droid_factory.mde_assassin_droid");
            utils.setScriptVar(creature, trial.WORKING_MDE_MOB, true);
            utils.setScriptVar(creature, trial.WORKING_CLONER_EXIT, exitLoc);
            creature1 = create.object(DROID_ASSASSIN, spawnLoc);
            attachScript(creature1, "theme_park.dungeon.mustafar_trials.working_droid_factory.mde_assassin_droid");
            utils.setScriptVar(creature1, trial.WORKING_MDE_MOB, true);
            utils.setScriptVar(creature1, trial.WORKING_CLONER_EXIT, exitLoc);
            return SCRIPT_CONTINUE;
            case STAGE_BOMB_2:
            creature1 = create.object(DROID_BOMB, spawnLoc);
            creature2 = create.object(DROID_BOMB, spawnLoc);
            creature3 = create.object(DROID_BOMB, spawnLoc);
            attachScript(creature1, "theme_park.dungeon.mustafar_trials.working_droid_factory.mde_bomb_droid");
            attachScript(creature2, "theme_park.dungeon.mustafar_trials.working_droid_factory.mde_bomb_droid");
            attachScript(creature3, "theme_park.dungeon.mustafar_trials.working_droid_factory.mde_bomb_droid");
            messageTo(self, "doEventSpawn", trial.getSessionDict(self), ACTIVE_RESPAWN, false);
            utils.setScriptVar(creature1, trial.WORKING_MDE_MOB, true);
            utils.setScriptVar(creature2, trial.WORKING_MDE_MOB, true);
            utils.setScriptVar(creature3, trial.WORKING_MDE_MOB, true);
            utils.setScriptVar(creature1, trial.WORKING_CLONER_EXIT, exitLoc);
            utils.setScriptVar(creature2, trial.WORKING_CLONER_EXIT, exitLoc);
            utils.setScriptVar(creature3, trial.WORKING_CLONER_EXIT, exitLoc);
            return SCRIPT_CONTINUE;
        }
        return SCRIPT_CONTINUE;
    }
    public void deactivateTerminal(obj_id self) throws InterruptedException
    {
        restoreTerminal(self);
        setInvulnerable(self, true);
        trial.bumpSession(self);
    }
    public void restoreTerminal(obj_id self) throws InterruptedException
    {
        if (isDisabled(self))
        {
            clearCondition(self, CONDITION_DISABLED);
        }
        int max = getMaxHitpoints(self);
        setHitpoints(self, max);
    }
    public int fix(obj_id self, dictionary params) throws InterruptedException
    {
        restoreTerminal(self);
        return SCRIPT_CONTINUE;
    }
}
