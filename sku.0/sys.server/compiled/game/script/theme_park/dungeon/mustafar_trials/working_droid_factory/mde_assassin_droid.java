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

public class mde_assassin_droid extends script.base_script
{
    public mde_assassin_droid()
    {
    }
    public static final boolean LOGGING = true;
    public int OnDestroy(obj_id self) throws InterruptedException
    {
        return SCRIPT_CONTINUE;
    }
    public int OnIncapacitated(obj_id self, obj_id killer) throws InterruptedException
    {
        if (!utils.hasScriptVar(self, trial.WORKING_MDE_REVIVED))
        {
            detachAllScripts(self);
            removeAllObjVars(self);
        }
        else 
        {
            utils.setScriptVar(self, "taken", true);
            messageTo(self, "destroySelf", null, 5, false);
        }
        return SCRIPT_CONTINUE;
    }
    public int destroySelf(obj_id self, dictionary params) throws InterruptedException
    {
        destroyObject(self);
        return SCRIPT_CONTINUE;
    }
    public int OnMovePathComplete(obj_id self) throws InterruptedException
    {
        setInvulnerable(self, false);
        clearAssassinCloak(self);
        obj_id[] players = trial.getValidTargetsInDungeon(self);
        if (players == null || players.length == 0)
        {
            return SCRIPT_CONTINUE;
        }
        obj_id prefered = trial.getClosest(self, players);
        if (prefered == obj_id.NULL_ID)
        {
            return SCRIPT_CONTINUE;
        }
        startCombat(self, prefered);
        return SCRIPT_CONTINUE;
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        messageTo(self, "path", null, 2, false);
        assassinCloak(self);
        return SCRIPT_CONTINUE;
    }
    public int path(obj_id self, dictionary params) throws InterruptedException
    {
        location exit = utils.getLocationScriptVar(self, trial.WORKING_CLONER_EXIT);
        obj_id[] points = trial.getObjectsInDungeonWithObjVar(self, "de_random");
        if (points == null || points.length == 0)
        {
            return SCRIPT_CONTINUE;
        }
        location random = getLocation(points[rand(0, points.length - 1)]);
        if (exit == null)
        {
            exit = getLocation(points[rand(0, points.length - 1)]);
        }
        location[] spawnPath = new location[2];
        spawnPath[0] = exit;
        spawnPath[1] = random;
        patrolOnce(self, spawnPath);
        return SCRIPT_CONTINUE;
    }
    public void assassinCloak(obj_id self) throws InterruptedException
    {
        setCreatureCover(self, 125);
        setCreatureCoverVisibility(self, false);
        playClientEffectObj(self, "clienteffect/combat_special_attacker_cover.cef", self, "");
    }
    public void clearAssassinCloak(obj_id self) throws InterruptedException
    {
        setCreatureCover(self, 0);
        setCreatureCoverVisibility(self, true);
        playClientEffectObj(self, "clienteffect/combat_special_attacker_cover.cef", self, "");
    }
    public void doLogging(String section, String message) throws InterruptedException
    {
        if (LOGGING || trial.WORKING_LOGGING)
        {
            LOG("logging/mde_combat_droid/" + section, message);
        }
    }
}
