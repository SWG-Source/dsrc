package script.theme_park.dungeon.mustafar_trials.valley_battleground;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.utils;
import script.library.create;
import script.library.ai_lib;
import script.library.armor;
import script.library.combat;
import script.library.trial;
import script.library.pet_lib;
import script.library.factions;

public class mining_squad_leader extends script.base_script
{
    public mining_squad_leader()
    {
    }
    public static final String MINE_SOLDIER = "som_battlefield_miner";
    public static final boolean LOGGING = false;
    public int OnAttach(obj_id self) throws InterruptedException
    {
        ai_lib.setDefaultCalmBehavior(self, ai_lib.BEHAVIOR_SENTINEL);
        trial.setInterest(self);
        messageTo(self, "autoDeploy", null, 5, false);
        setHibernationDelay(self, 7200);
        return SCRIPT_CONTINUE;
    }
    public int autoDeploy(obj_id self, dictionary params) throws InterruptedException
    {
        if (utils.hasScriptVar(self, "autoDeploy"))
        {
            messageTo(self, "deployForces", null, 0, false);
        }
        return SCRIPT_CONTINUE;
    }
    public int OnEnteredCombat(obj_id self) throws InterruptedException
    {
        utils.setScriptVar(self, "engaged", true);
        if (hasScript(self, "conversation.som_battlefield_miner_leader"))
        {
            clearCondition(self, CONDITION_CONVERSABLE);
            detachScript(self, "conversation.som_battlefield_miner_leader");
        }
        return SCRIPT_CONTINUE;
    }
    public int OnCreatureDamaged(obj_id self, obj_id attacker, obj_id weapon, int[] damage) throws InterruptedException
    {
        if (isPlayer(attacker) || pet_lib.isPet(attacker))
        {
            setHate(self, attacker, -5000f);
            int total = 0;
            for (int x = 0; x < damage.length; x++)
            {
                total += damage[x];
            }
            addToHealth(self, total);
        }
        return SCRIPT_CONTINUE;
    }
    public int OnAttackerCombatAction(obj_id self, obj_id weapon, obj_id defender) throws InterruptedException
    {
        if (isPlayer(defender))
        {
            setHate(self, defender, -5000f);
        }
        return SCRIPT_CONTINUE;
    }
    public int OnHateTargetAdded(obj_id self, obj_id target) throws InterruptedException
    {
        if (isPlayer(target) || pet_lib.isPet(target))
        {
            setHate(self, target, -5000f);
        }
        return SCRIPT_CONTINUE;
    }
    public int troopEngaged(obj_id self, dictionary params) throws InterruptedException
    {
        utils.setScriptVar(self, "engaged", true);
        return SCRIPT_CONTINUE;
    }
    public int deployForces(obj_id self, dictionary params) throws InterruptedException
    {
        setInvulnerable(self, false);
        obj_id master = self;
        factions.setIgnorePlayer(self);
        obj_id[] miners = new obj_id[4];
        location baseLoc = getLocation(self);
        setYaw(self, -70);
        for (int i = 0; i < miners.length; i++)
        {
            location spawnLoc = new location(baseLoc.x, baseLoc.y, baseLoc.z, baseLoc.area, baseLoc.cell);
            miners[i] = create.object(MINE_SOLDIER, spawnLoc);
            if (isIdValid(miners[i]))
            {
                attachScript(miners[i], "theme_park.dungeon.mustafar_trials.valley_battleground.mining_squad_member");
                setYaw(miners[i], -70);
                utils.setScriptVar(miners[i], trial.PARENT, self);
                ai_lib.followInFormation(miners[i], self, ai_lib.FORMATION_BOX, i + 1);
                factions.setIgnorePlayer(miners[i]);
            }
        }
        utils.setScriptVar(self, "forces", miners);
        utils.setScriptVar(self, "deployed", true);
        return SCRIPT_CONTINUE;
    }
    public int unDeployForces(obj_id self, dictionary params) throws InterruptedException
    {
        if (utils.hasScriptVar(self, "engaged"))
        {
            doLogging("unDeployForces", "I have already been engaged, ignoring command");
            return SCRIPT_CONTINUE;
        }
        if (utils.hasScriptVar(self, "deployed"))
        {
            utils.removeScriptVar(self, "deployed");
        }
        if (!utils.hasScriptVar(self, "forces"))
        {
            doLogging("unDeployForces", "I do not have a forces script");
            return SCRIPT_CONTINUE;
        }
        obj_id[] forces = utils.getObjIdArrayScriptVar(self, "forces");
        if (forces == null || forces.length == 0)
        {
            doLogging("unDeployForces", "forces list was null or length was zero");
            return SCRIPT_CONTINUE;
        }
        setInvulnerable(self, true);
        for (int i = 0; i < forces.length; i++)
        {
            if (isIdValid(forces[i]))
            {
                kill(forces[i]);
                destroyObject(forces[i]);
            }
            else 
            {
                doLogging("unDeployForces", "Id for troop(" + forces[i] + ") was invalid, ignoring");
            }
        }
        return SCRIPT_CONTINUE;
    }
    public void doLogging(String section, String message) throws InterruptedException
    {
        if (LOGGING || trial.VALLEY_LOGGING)
        {
            LOG("logging/mining_squad_leader/" + section, message);
        }
    }
}
