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

public class mde_combat_droid extends script.base_script
{
    public mde_combat_droid()
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
        obj_id[] players = trial.getValidTargetsInDungeon(self);
        if (players == null || players.length == 0)
        {
            return SCRIPT_CONTINUE;
        }
        obj_id prefered = obj_id.NULL_ID;
        for (int i = 0; i < players.length; i++)
        {
            if (!isDead(players[i]))
            {
                if (prefered == obj_id.NULL_ID)
                {
                    prefered = players[i];
                }
                else 
                {
                    if (getDistance(self, players[i]) < getDistance(self, prefered))
                    {
                        prefered = players[i];
                    }
                }
            }
        }
        if (prefered == obj_id.NULL_ID)
        {
            return SCRIPT_CONTINUE;
        }
        startCombat(self, prefered);
        return SCRIPT_CONTINUE;
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        if (!utils.hasScriptVar(self, trial.WORKING_MDE_REVIVED))
        {
            messageTo(self, "path", null, 2, false);
        }
        else 
        {
            utils.findLocInFrontOfTarget(self, 1);
        }
        return SCRIPT_CONTINUE;
    }
    public int path(obj_id self, dictionary params) throws InterruptedException
    {
        location exit = utils.getLocationScriptVar(self, trial.WORKING_CLONER_EXIT);
        ai_lib.aiPathTo(self, exit);
        return SCRIPT_CONTINUE;
    }
    public void doLogging(String section, String message) throws InterruptedException
    {
        if (LOGGING || trial.WORKING_LOGGING)
        {
            LOG("logging/mde_combat_droid/" + section, message);
        }
    }
}
