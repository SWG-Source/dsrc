package script.theme_park.dungeon.mustafar_trials.working_droid_factory;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.ai_lib;
import script.library.trial;
import script.library.utils;
import script.library.combat;
import script.library.prose;

public class mde_bomb_droid extends script.base_script
{
    public mde_bomb_droid()
    {
    }
    public static final int DROID_DAMAGE = 500;
    public static final boolean LOGGING = false;
    public int OnDestroy(obj_id self) throws InterruptedException
    {
        return SCRIPT_CONTINUE;
    }
    public int OnIncapacitated(obj_id self, obj_id killer) throws InterruptedException
    {
        messageTo(self, "destroySelf", null, 5, false);
        return SCRIPT_CONTINUE;
    }
    public int destroySelf(obj_id self, dictionary params) throws InterruptedException
    {
        destroyObject(self);
        return SCRIPT_CONTINUE;
    }
    public int path(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id[] points = trial.getObjectsInDungeonWithObjVar(self, "de_random");
        if (points == null || points.length == 0)
        {
            return SCRIPT_CONTINUE;
        }
        location[] myPath = new location[4];
        for (int i = 0; i < myPath.length; i++)
        {
            myPath[i] = getLocation(points[rand(0, points.length - 1)]);
        }
        patrolOnce(self, myPath);
        setMovementRun(self);
        return SCRIPT_CONTINUE;
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        setInvulnerable(self, true);
        messageTo(self, "path", null, rand(1, 23), false);
        return SCRIPT_CONTINUE;
    }
    public int OnMovePathComplete(obj_id self) throws InterruptedException
    {
        setInvulnerable(self, false);
        obj_id[] players = trial.getValidTargetsInDungeon(self);
        if (players == null || players.length == 0)
        {
            return SCRIPT_CONTINUE;
        }
        Vector valid = new Vector();
        valid.setSize(0);
        for (int i = 0; i < players.length; i++)
        {
            if (!isDead(players[i]))
            {
                utils.addElement(valid, players[i]);
            }
        }
        if (valid == null || valid.size() == 0)
        {
            messageTo(self, "path", null, 3, false);
            return SCRIPT_CONTINUE;
        }
        ai_lib.aiFollow(self, ((obj_id)valid.get(rand(0, valid.size() - 1))), 1, 2);
        dictionary dict = new dictionary();
        dict.put("count", 3);
        messageTo(self, "countdown", dict, 0, false);
        return SCRIPT_CONTINUE;
    }
    public int countdown(obj_id self, dictionary params) throws InterruptedException
    {
        if (ai_lib.isAiDead(self))
        {
            showFlyText(self, new string_id("pet/droid_modules", "detonation_disabled"), 1.5f, 255, 65, 65);
            return SCRIPT_CONTINUE;
        }
        int count = params.getInt("count");
        if (count > 0)
        {
            String countdown_name = "countdown_" + count;
            showFlyText(self, new string_id("pet/droid_modules", countdown_name), 1.5f, 255, 65, 65);
            count -= 1;
            params.put("count", count);
            messageTo(self, "countdown", params, 2.5f, false);
        }
        else 
        {
            detonateDroid(self);
        }
        return SCRIPT_CONTINUE;
    }
    public void detonateDroid(obj_id self) throws InterruptedException
    {
        obj_id[] targets = trial.getValidTargetsInRadius(self, 7);
        if (targets != null && targets.length > 0)
        {
            for (int i = 0; i < targets.length; i++)
            {
                int damage = DROID_DAMAGE;
                prose_package pp = new prose_package();
                pp.stringId = new string_id("cbt_spam", "detonation_droid");
                pp.actor.set(self);
                pp.target.set(targets[i]);
                pp.digitInteger = damage;
                combat.sendCombatSpamMessageProse(targets[i], self, pp, true, true, false, COMBAT_RESULT_HIT);
                damage(targets[i], DAMAGE_ELEMENTAL_HEAT, HIT_LOCATION_BODY, damage);
                location playLoc = getLocation(self);
                playClientEffectLoc(self, "clienteffect/combat_grenade_fragmentation.cef", playLoc, 0.4f);
            }
        }
        trial.cleanupNpc(self);
    }
    public void doLogging(String section, String message) throws InterruptedException
    {
        if (LOGGING || trial.WORKING_LOGGING)
        {
            LOG("logging/mde_bomb_droid/" + section, message);
        }
    }
}
