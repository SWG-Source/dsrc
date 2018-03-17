package script.theme_park.dungeon.mustafar_trials.sher_kar;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.utils;
import script.library.create;
import script.library.trial;
import script.library.badge;
import script.library.buff;
import script.library.ai_lib;
import script.library.instance;

public class monster_manager extends script.base_script
{
    public monster_manager()
    {
    }
    public static final boolean LOGGING = false;
    public int beginSpawn(obj_id self, dictionary params) throws InterruptedException
    {
        clearEventArea(self);
        messageTo(self, "prepareEventArea", null, 5, false);
        return SCRIPT_CONTINUE;
    }
    public int dungeonCleanup(obj_id self, dictionary params) throws InterruptedException
    {
        clearEventArea(self);
        return SCRIPT_CONTINUE;
    }
    public int cleanupSpawn(obj_id self, dictionary params) throws InterruptedException
    {
        clearEventArea(self);
        return SCRIPT_CONTINUE;
    }
    public void clearEventArea(obj_id dungeon) throws InterruptedException
    {
        obj_id[] contents = trial.getAllObjectsInDungeon(dungeon);
        if (contents == null || contents.length == 0)
        {
            doLogging("clearEventArea", "Dungeon was empty, return");
            return;
        }
        for (int i = 0; i < contents.length; i++)
        {
            if (isPlayer(contents[i]))
            {
            }
            else 
            {
                if (isMob(contents[i]))
                {
                    trial.cleanupNpc(contents[i]);
                }
                else if (trial.isTempObject(contents[i]))
                {
                    trial.cleanupNpc(contents[i]);
                }
            }
        }
    }
    public int prepareEventArea(obj_id self, dictionary params) throws InterruptedException
    {
        spawnSherKar(self);
        spawnGuards(self);
        return SCRIPT_CONTINUE;
    }
    public void spawnSherKar(obj_id self) throws InterruptedException
    {
        obj_id[] wp = trial.getObjectsInDungeonWithObjVar(self, trial.MONSTER_WP);
        if (wp == null || wp.length == 0)
        {
            return;
        }
        for (int i = 0; i < wp.length; i++)
        {
            if ((getStringObjVar(wp[i], trial.MONSTER_WP)).equals("sher_kar"))
            {
                obj_id sherKar = create.object("som_sherkar", getLocation(wp[i]));
                setYaw(sherKar, -10);
                utils.setScriptVar(self, trial.MONSTER_SHER_KAR, sherKar);
            }
        }
    }
    public void spawnGuards(obj_id self) throws InterruptedException
    {
        obj_id[] wp = trial.getObjectsInDungeonWithObjVar(self, trial.MONSTER_WP);
        if (wp == null || wp.length == 0)
        {
            return;
        }
        int type = 0;
        obj_id[] guards = new obj_id[4];
        for (int i = 0; i < wp.length; i++)
        {
            if ((getStringObjVar(wp[i], trial.MONSTER_WP)).startsWith("guard"))
            {
                if (type == 0 || type == 1)
                {
                    guards[type] = create.object("som_sherkar_praetorian", getLocation(wp[i]));
                    faceTo(guards[type], utils.getObjIdScriptVar(self, trial.MONSTER_SHER_KAR));
                    type++;
                }
                else if (type == 2)
                {
                    guards[type] = create.object("som_sherkar_karling", getLocation(wp[i]));
                    faceTo(guards[type], utils.getObjIdScriptVar(self, trial.MONSTER_SHER_KAR));
                    type++;
                }
                else if (type == 3)
                {
                    guards[type] = create.object("som_sherkar_symbiot", getLocation(wp[i]));
                    faceTo(guards[type], utils.getObjIdScriptVar(self, trial.MONSTER_SHER_KAR));
                    type++;
                }
            }
        }
        ai_lib.establishAgroLink(guards[0], guards);
    }
    public int doMidEvent(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id[] players = trial.getValidTargetsInDungeon(self);
        if (players == null || players.length == 0)
        {
            return SCRIPT_CONTINUE;
        }
        return SCRIPT_CONTINUE;
    }
    public int doEndEvent(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id[] players = trial.getValidTargetsInDungeon(self);
        if (players == null || players.length == 0)
        {
            return SCRIPT_CONTINUE;
        }
        return SCRIPT_CONTINUE;
    }
    public int sherKarDied(obj_id self, dictionary params) throws InterruptedException
    {
        utils.sendSystemMessagePob(self, trial.MONSTER_SK_DEFEATED);
        obj_id[] players = trial.getPlayersInDungeon(self);
        badge.grantBadge(players, "bdg_must_kill_sher_kar");
        instance.setClock(self, 305);
        return SCRIPT_CONTINUE;
    }
    public void doLogging(String section, String message) throws InterruptedException
    {
        if (LOGGING || trial.MONSTER_LOGGING)
        {
            LOG("doLogging/monster_manager/" + section, message);
        }
    }
}
