package script.theme_park.dungeon.mustafar_trials.working_droid_factory;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.ai_lib;
import script.library.buff;
import script.library.combat;
import script.library.prose;
import script.library.trial;
import script.library.utils;

public class aurek_besh extends script.base_script
{
    public aurek_besh()
    {
    }
    public static final boolean LOGGING = true;
    public int OnDestroy(obj_id self) throws InterruptedException
    {
        return SCRIPT_CONTINUE;
    }
    public int OnIncapacitated(obj_id self, obj_id killer) throws InterruptedException
    {
        String name = utils.getStringScriptVar(self, "name");
        if (name.equals("aurek"))
        {
            trial.setAurekKilled(self, true);
        }
        if (name.equals("besh"))
        {
            trial.setBeshKilled(self, true);
        }
        obj_id top = trial.getTop(self);
        messageTo(top, "validateAurekBeshKill", trial.getSessionDict(top, "validate_ab_kill"), 0, false);
        messageTo(self, "destroySelf", null, 5, false);
        return SCRIPT_CONTINUE;
    }
    public int destroySelf(obj_id self, dictionary params) throws InterruptedException
    {
        destroyObject(self);
        return SCRIPT_CONTINUE;
    }
    public int OnEnteredCombat(obj_id self) throws InterruptedException
    {
        startEventActions(self);
        return SCRIPT_CONTINUE;
    }
    public void startEventActions(obj_id self) throws InterruptedException
    {
        doLogging("startEventActions", "Have entered combat and am beginning duality");
        trial.bumpSession(trial.getTop(self), "duality");
        String name = utils.getStringScriptVar(self, "name");
        if (name != null && !name.equals("") && name.equals("aurek"))
        {
            messageTo(trial.getTop(self), "startDuality", null, 1.0f, false);
        }
        messageTo(self, "performHealthCheck", trial.getSessionDict(self, "imbalance"), 10.0f, false);
    }
    public int OnCreatureDamaged(obj_id self, obj_id attacker, obj_id weapon, int[] damage) throws InterruptedException
    {
        float max = (float)getMaxHealth(self);
        float current = (float)getHealth(self);
        float ratio = current / max;
        String name = utils.getStringScriptVar(self, "name");
        if (name.equals("aurek"))
        {
            utils.setScriptVar(trial.getTop(self), trial.AUREK_HEALTH, ratio);
        }
        else 
        {
            utils.setScriptVar(trial.getTop(self), trial.BESH_HEALTH, ratio);
        }
        return SCRIPT_CONTINUE;
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        obj_id homeCell = getContainedBy(self);
        trial.setHp(self, trial.HP_WORKING_AUREK_BESH);
        utils.setScriptVar(self, "home", homeCell);
        String name = utils.getStringScriptVar(self, "name");
        if (name.equals("aurek"))
        {
            utils.setScriptVar(trial.getTop(self), trial.AUREK_HEALTH, 1.0f);
        }
        else 
        {
            utils.setScriptVar(trial.getTop(self), trial.BESH_HEALTH, 1.0f);
        }
        return SCRIPT_CONTINUE;
    }
    public int OnAboutToBeTransferred(obj_id self, obj_id destContainer, obj_id transferer) throws InterruptedException
    {
        obj_id homeCell = utils.getObjIdScriptVar(self, "home");
        if (homeCell != destContainer)
        {
            resetSelf(self);
            messageTo(self, "removeInvulnerable", null, 10, false);
            return SCRIPT_CONTINUE;
        }
        return SCRIPT_CONTINUE;
    }
    public int removeInvulnerable(obj_id self, dictionary params) throws InterruptedException
    {
        setInvulnerable(self, false);
        return SCRIPT_CONTINUE;
    }
    public void resetSelf(obj_id self) throws InterruptedException
    {
        int max = getMaxHealth(self);
        int current = getHealth(self);
        int toHeal = max - current;
        addToHealth(self, toHeal);
        setInvulnerable(self, true);
        ai_lib.clearCombatData();
        buff.removeAllBuffs(self);
        trial.bumpSession(self, "imbalance");
        String name = utils.getStringScriptVar(self, "name");
        if (name.equals("aurek"))
        {
            utils.setScriptVar(trial.getTop(self), trial.AUREK_HEALTH, 1.0f);
        }
        else 
        {
            utils.setScriptVar(trial.getTop(self), trial.BESH_HEALTH, 1.0f);
        }
    }
    public int aurek_diminish(obj_id self, dictionary params) throws InterruptedException
    {
        if (!utils.hasScriptVar(self, "name") || !(utils.getStringScriptVar(self, "name")).equals("aurek"))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id[] players = trial.getValidTargetsInCell(trial.getTop(self), "smallroom21");
        if (players == null || players.length == 0)
        {
            doLogging("aurek_diminish", "Players were null or length 0");
            return SCRIPT_CONTINUE;
        }
        Vector beshPlayers = new Vector();
        beshPlayers.setSize(0);
        for (int i = 0; i < players.length; i++)
        {
            if (buff.hasBuff(players[i], "beshDuality"))
            {
                utils.addElement(beshPlayers, players[i]);
            }
        }
        if (beshPlayers == null || beshPlayers.size() == 0)
        {
            doLogging("aurek_diminish", "There were no besh players, detonating now");
            utils.messageTo(players, "dualityDetonation", null, 0.0f, false);
        }
        else 
        {
            int damage = beshPlayers.size() * 500;
            prose_package pp = new prose_package();
            pp.stringId = new string_id("cbt_spam", "duality_detonate_twin");
            pp.target.set(self);
            pp.digitInteger = damage;
            for (int k = 0; k < beshPlayers.size(); k++)
            {
                combat.sendCombatSpamMessageProse(((obj_id)beshPlayers.get(k)), self, pp, true, true, false, COMBAT_RESULT_HIT);
                damage(self, DAMAGE_ELEMENTAL_HEAT, HIT_LOCATION_BODY, damage);
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int besh_diminish(obj_id self, dictionary params) throws InterruptedException
    {
        if (!utils.hasScriptVar(self, "name") || !(utils.getStringScriptVar(self, "name")).equals("besh"))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id[] players = trial.getValidTargetsInCell(trial.getTop(self), "smallroom24");
        if (players == null || players.length == 0)
        {
            doLogging("besh_diminish", "Players were null or length 0");
            return SCRIPT_CONTINUE;
        }
        Vector aurekPlayers = new Vector();
        aurekPlayers.setSize(0);
        for (int i = 0; i < players.length; i++)
        {
            if (buff.hasBuff(players[i], "aurekDuality"))
            {
                utils.addElement(aurekPlayers, players[i]);
            }
        }
        if (aurekPlayers == null || aurekPlayers.size() == 0)
        {
            doLogging("besh_diminish", "There were no aurek players, detonating now");
            utils.messageTo(players, "dualityDetonation", null, 0.0f, false);
        }
        else 
        {
            int damage = aurekPlayers.size() * 8000;
            prose_package pp = new prose_package();
            pp.stringId = new string_id("cbt_spam", "duality_detonate_twin");
            pp.target.set(self);
            pp.digitInteger = damage;
            for (int k = 0; k < aurekPlayers.size(); k++)
            {
                combat.sendCombatSpamMessageProse(((obj_id)aurekPlayers.get(k)), self, pp, true, true, false, COMBAT_RESULT_HIT);
                damage(self, DAMAGE_ELEMENTAL_HEAT, DAMAGE_ELEMENTAL_ELECTRICAL, damage);
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int performHealthCheck(obj_id self, dictionary params) throws InterruptedException
    {
        if (!trial.verifySession(self, params, "imbalance"))
        {
            doLogging("performHealthCheck", "Failed to verify session");
            return SCRIPT_CONTINUE;
        }
        float aurek_health = utils.getFloatScriptVar(trial.getTop(self), trial.AUREK_HEALTH);
        float besh_health = utils.getFloatScriptVar(trial.getTop(self), trial.BESH_HEALTH);
        doLogging("performHealthCheck", "Aurek/Besh Health Levels: " + aurek_health + "/" + besh_health);
        float difference = Math.abs(aurek_health - besh_health);
        if (difference > 0.05f)
        {
            if (!buff.hasBuff(self, "aurek_besh_imbalance"))
            {
                buff.applyBuff(self, "aurek_besh_imbalance");
                utils.setScriptVar(self, "private_damage_bonus", 4.0f * difference);
                obj_id[] players = trial.getPlayersInCell(trial.getTop(self), getContainedBy(self));
                if (players != null && players.length > 0)
                {
                    utils.sendSystemMessage(players, trial.WORKING_IMBALANCE_APPLY);
                }
            }
        }
        else 
        {
            if (buff.hasBuff(self, "aurek_besh_imbalance"))
            {
                buff.removeBuff(self, "aurek_besh_imbalance");
                utils.removeScriptVar(self, "private_damage_bonus");
                obj_id[] players = trial.getPlayersInCell(trial.getTop(self), getContainedBy(self));
                if (players != null && players.length > 0)
                {
                    utils.sendSystemMessage(players, trial.WORKING_IMBALANCE_FADE);
                }
            }
        }
        messageTo(self, "performHealthCheck", trial.getSessionDict(self, "imbalance"), 10.0f, false);
        return SCRIPT_CONTINUE;
    }
    public void doLogging(String section, String message) throws InterruptedException
    {
        if (LOGGING || trial.WORKING_LOGGING)
        {
            LOG("doLogging/aurek_besh/" + section, message);
        }
    }
}
