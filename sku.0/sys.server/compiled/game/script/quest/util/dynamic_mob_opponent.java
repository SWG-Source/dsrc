package script.quest.util;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.ai_lib;
import script.library.anims;
import script.library.chat;
import script.library.create;
import script.library.groundquests;
import script.library.group;
import script.library.prose;
import script.library.trial;
import script.library.utils;

public class dynamic_mob_opponent extends script.base_script
{
    public dynamic_mob_opponent()
    {
    }
    public static final String CLIENT_EFFECT = "appearance/pt_smoke_puff.prt";
    public static final String FAIL_SIGNAL = "fail_signal";
    public static final String CREATURE_TABLE = "datatables/mob/creatures.iff";
    public static final String STAT_BALANCE_TABLE = "datatables/mob/stat_balance.iff";
    public int OnAttach(obj_id self) throws InterruptedException
    {
        CustomerServiceLog("quest", "dynamic_mob_opponent.OnAttach() Attempting to spawn and modify wave event dynamic enemy.");
        playClientEffectObj(self, CLIENT_EFFECT, self, "");
        messageTo(self, "setDynamicDataOnMob", null, 1, false);
        return SCRIPT_CONTINUE;
    }
    public int OnIncapacitateTarget(obj_id self, obj_id victim) throws InterruptedException
    {
        CustomerServiceLog("quest", "dynamic_mob_opponent.OnIncapacitateTarget() Dynamic Enemy Incapacitated a target.");
        obj_id parent = getObjIdObjVar(self, trial.PARENT);
        if (!isIdValid(parent) || !exists(parent))
        {
            destroyObject(self);
            CustomerServiceLog("quest", "dynamic_mob_opponent.setDynamicDataOnMob() FAILED to find parent object.");
            return SCRIPT_CONTINUE;
        }
        obj_id playerEnemy = utils.getObjIdScriptVar(self, "waveEventPlayer");
        if (!isIdValid(playerEnemy) || !exists(playerEnemy))
        {
            CustomerServiceLog("quest", "dynamic_mob_opponent.OnIncapacitateTarget() FAILED to find playerEnemy script variable.");
            return SCRIPT_CONTINUE;
        }
        if (victim != playerEnemy)
        {
            CustomerServiceLog("quest", "dynamic_mob_opponent.OnIncapacitateTarget() Dynamic Enemy just killed a player ( " + victim + " )that was not the player that spawned the dynamic mob.");
            return SCRIPT_CONTINUE;
        }
        CustomerServiceLog("quest", "dynamic_mob_opponent.OnIncapacitateTarget() Dynamic Enemy has killed the spawning player: " + playerEnemy);
        String signal = getStringObjVar(self, FAIL_SIGNAL);
        if (signal == null || signal.length() <= 0)
        {
            CustomerServiceLog("quest", "dynamic_mob_opponent.OnIncapacitateTarget() FAILED to find signal objvar. This is likely a designer error.");
        }
        CustomerServiceLog("quest", "dynamic_mob_opponent.OnIncapacitateTarget() Dynamic Enemy sent quest signal to player. Signal was: " + signal);
        groundquests.sendSignal(playerEnemy, signal);
        messageTo(parent, "cleanupEvent", null, 2, false);
        return SCRIPT_CONTINUE;
    }
    public int OnDeath(obj_id self, obj_id killer, obj_id corpseId) throws InterruptedException
    {
        return SCRIPT_CONTINUE;
    }
    public int OnDestroy(obj_id self) throws InterruptedException
    {
        return SCRIPT_CONTINUE;
    }
    public int OnExitedCombat(obj_id self) throws InterruptedException
    {
        if (ai_lib.isDead(self))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id parent = getObjIdObjVar(self, trial.PARENT);
        if (!isIdValid(parent) || !exists(parent))
        {
            destroyObject(self);
            CustomerServiceLog("quest", "dynamic_mob_opponent.OnExitedCombat() FAILED to find parent object.");
            return SCRIPT_CONTINUE;
        }
        String signal = getStringObjVar(self, FAIL_SIGNAL);
        if (signal == null || signal.length() <= 0)
        {
            CustomerServiceLog("quest", "dynamic_mob_opponent.OnExitedCombat() FAILED to find signal objvar. This is likely a designer error.");
        }
        obj_id playerEnemy = utils.getObjIdScriptVar(self, "waveEventPlayer");
        if (!isIdValid(playerEnemy) || !exists(playerEnemy))
        {
            CustomerServiceLog("quest", "dynamic_mob_opponent.OnIncapacitateTarget() FAILED to find playerEnemy script variable.");
            return SCRIPT_CONTINUE;
        }
        CustomerServiceLog("quest", "dynamic_mob_opponent.OnExitedCombat() Dynamic Enemy sent quest signal to player. Signal was: " + signal);
        groundquests.sendSignal(playerEnemy, signal);
        messageTo(parent, "cleanupEvent", null, 2, false);
        return SCRIPT_CONTINUE;
    }
    public int setDynamicDataOnMob(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id parent = getObjIdObjVar(self, trial.PARENT);
        if (!isIdValid(parent) || !exists(parent))
        {
            destroyObject(self);
            CustomerServiceLog("quest", "dynamic_mob_opponent.setDynamicDataOnMob() FAILED to find parent object.");
            return SCRIPT_CONTINUE;
        }
        CustomerServiceLog("quest", "dynamic_mob_opponent.setDynamicDataOnMob() Dynamic Mob has parent: " + parent);
        obj_id playerEnemy = utils.getObjIdScriptVar(self, "waveEventPlayer");
        if (!isIdValid(playerEnemy) || !exists(playerEnemy))
        {
            messageTo(parent, "cleanupEvent", null, 2, false);
            CustomerServiceLog("quest", "dynamic_mob_opponent.OnAttach() FAILED to find player enemy.");
            return SCRIPT_CONTINUE;
        }
        int mobLevel = getLevel(playerEnemy);
        if (mobLevel <= 0)
        {
            CustomerServiceLog("quest", "dynamic_mob_opponent.setDynamicDataOnMob() Player level was less than or equal to 0. Making Dynamic Mob level 5");
            mobLevel = 5;
        }
        CustomerServiceLog("quest", "dynamic_mob_opponent.setDynamicDataOnMob() Dynamic Mob level is now: " + mobLevel);
        setObjVar(self, create.INITIALIZE_CREATURE_DO_NOT_SCALE_OBJVAR, 1);
        String creatureName = getStringObjVar(self, "creature_type");
        if (creatureName != null && creatureName.length() > 0)
        {
            dictionary creatureDict = utils.dataTableGetRow(CREATURE_TABLE, creatureName);
            if (creatureDict != null)
            {
                create.initializeCreature(self, creatureName, creatureDict, mobLevel);
            }
        }
        setInvulnerable(self, false);
        clearCondition(self, CONDITION_CONVERSABLE);
        startCombat(self, playerEnemy);
        return SCRIPT_CONTINUE;
    }
}
