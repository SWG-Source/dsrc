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
import script.library.create;

public class mde_repair_droid extends script.base_script
{
    public mde_repair_droid()
    {
    }
    public static final String ASSASSIN_TEMPLATE = "ig_assassin_droid.iff";
    public static final String ASSASSIN_RESPAWN = "som_working_assassin_droid";
    public static final String COMBAT_TEMPLATE = "blastromech.iff";
    public static final String COMBAT_RESPAWN = "som_working_blastromech";
    public static final String FIXER_ONE = "som_working_super_repair_droid";
    public static final boolean LOGGING = false;
    public int OnDestroy(obj_id self) throws InterruptedException
    {
        return SCRIPT_CONTINUE;
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        setInvulnerable(self, true);
        trial.bumpSession(self);
        messageTo(self, "path", trial.getSessionDict(self), 1, false);
        return SCRIPT_CONTINUE;
    }
    public int path(obj_id self, dictionary params) throws InterruptedException
    {
        if (!trial.verifySession(self, params))
        {
            return SCRIPT_CONTINUE;
        }
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
    public int OnMovePathComplete(obj_id self) throws InterruptedException
    {
        if (utils.hasScriptVar(self, "swarming"))
        {
            obj_id mde = utils.getObjIdScriptVar(self, "swarming");
            faceTo(self, mde);
            aiSetHomeLocation(self, getLocation(self));
            return SCRIPT_CONTINUE;
        }
        if (utils.hasScriptVar(self, "fixerNum"))
        {
            return SCRIPT_CONTINUE;
        }
        if (!utils.hasScriptVar(self, "rezzing"))
        {
            doAnimationAction(self, "search");
            messageTo(self, "findCorpse", trial.getSessionDict(self), 3, false);
        }
        else 
        {
            obj_id corpse = utils.getObjIdScriptVar(self, "rezzing");
            faceTo(self, corpse);
            messageTo(self, "performRez", trial.getSessionDict(self), 1, false);
        }
        return SCRIPT_CONTINUE;
    }
    public int findCorpse(obj_id self, dictionary params) throws InterruptedException
    {
        if (!trial.verifySession(self, params))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id[] objects = trial.getAllObjectsInDungeon(self);
        Vector corpses = new Vector();
        corpses.setSize(0);
        for (int i = 0; i < objects.length; i++)
        {
            String template = getTemplateName(objects[i]);
            if (template.indexOf(ASSASSIN_TEMPLATE) > -1 || template.indexOf(COMBAT_TEMPLATE) > -1)
            {
                if (!utils.hasScriptVar(objects[i], "taken") && !utils.hasScriptVar(objects[i], trial.WORKING_MDE_REVIVED))
                {
                    if (isDead(objects[i]))
                    {
                        utils.addElement(corpses, objects[i]);
                    }
                }
            }
        }
        if (corpses == null || corpses.size() == 0)
        {
            messageTo(self, "path", trial.getSessionDict(self), 0, false);
        }
        obj_id random = ((obj_id)corpses.get(rand(0, corpses.size() - 1)));
        utils.setScriptVar(random, "taken", true);
        utils.setScriptVar(self, "rezzing", random);
        location corpseLoc = getLocation(random);
        corpseLoc.x -= 1;
        corpseLoc.z -= 1;
        ai_lib.aiPathTo(self, corpseLoc);
        return SCRIPT_CONTINUE;
    }
    public int performRez(obj_id self, dictionary params) throws InterruptedException
    {
        if (!trial.verifySession(self, params))
        {
            return SCRIPT_CONTINUE;
        }
        doAnimationAction(self, "snap_finger1");
        obj_id corpse = utils.getObjIdScriptVar(self, "rezzing");
        location rezLoc = getLocation(corpse);
        playClientEffectLoc(corpse, trial.PRT_WORKING_REPAIR_REZ, rezLoc, 0.4f);
        String template = getTemplateName(corpse);
        if (template.indexOf(ASSASSIN_TEMPLATE) > -1)
        {
            obj_id newAssassin = create.object(ASSASSIN_RESPAWN, rezLoc);
            utils.setScriptVar(newAssassin, trial.WORKING_MDE_REVIVED, true);
            attachScript(newAssassin, "theme_park.dungeon.mustafar_trials.working_droid_factory.mde_assassin_droid");
            utils.setScriptVar(newAssassin, trial.WORKING_MDE_MOB, true);
        }
        if (template.indexOf(COMBAT_TEMPLATE) > -1)
        {
            obj_id newCombat = create.object(COMBAT_RESPAWN, rezLoc);
            utils.setScriptVar(newCombat, trial.WORKING_MDE_REVIVED, true);
            attachScript(newCombat, "theme_park.dungeon.mustafar_trials.working_droid_factory.mde_combat_droid");
            utils.setScriptVar(newCombat, trial.WORKING_MDE_MOB, true);
        }
        destroyObject(corpse);
        utils.removeScriptVar(self, "rezzing");
        messageTo(self, "path", trial.getSessionDict(self), 2, false);
        return SCRIPT_CONTINUE;
    }
    public int formFixerOne(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id mde = params.getObjId("mde");
        utils.setScriptVar(self, "swarming", mde);
        location mdeLoc = getLocation(mde);
        String[] offSet = 
        {
            "-1:-1:",
            "1:1",
            "-1:1",
            "1:-1"
        };
        String myOffset = offSet[getNum(self)];
        String[] parse = split(myOffset, ':');
        mdeLoc.x += utils.stringToFloat(parse[0]);
        mdeLoc.z += utils.stringToFloat(parse[1]);
        ai_lib.aiPathTo(self, mdeLoc);
        messageTo(self, "continueForm", null, 13, false);
        return SCRIPT_CONTINUE;
    }
    public int getNum(obj_id self) throws InterruptedException
    {
        return utils.getIntScriptVar(self, "fixerNum");
    }
    public int continueForm(obj_id self, dictionary params) throws InterruptedException
    {
        int num = getNum(self);
        if (num == 0)
        {
            doAnimationAction(self, "search");
        }
        if (num == 3)
        {
            doAnimationAction(self, "nervous");
        }
        messageTo(self, "anim1", null, 3, false);
        return SCRIPT_CONTINUE;
    }
    public int anim1(obj_id self, dictionary params) throws InterruptedException
    {
        int num = getNum(self);
        if (num == 1)
        {
            doAnimationAction(self, "nervous");
        }
        if (num == 2)
        {
            doAnimationAction(self, "wave2");
        }
        messageTo(self, "anim2", null, 3, false);
        return SCRIPT_CONTINUE;
    }
    public int anim2(obj_id self, dictionary params) throws InterruptedException
    {
        int num = getNum(self);
        if (num == 2)
        {
            doAnimationAction(self, "conversation_1");
        }
        messageTo(self, "anim3", null, 3, false);
        return SCRIPT_CONTINUE;
    }
    public int anim3(obj_id self, dictionary params) throws InterruptedException
    {
        int num = getNum(self);
        if (num == 0 || num == 1 || num == 3)
        {
            doAnimationAction(self, "yes");
        }
        else 
        {
            doAnimationAction(self, "conversation_1");
        }
        messageTo(self, "anim4", null, 3, false);
        return SCRIPT_CONTINUE;
    }
    public int anim4(obj_id self, dictionary params) throws InterruptedException
    {
        int num = getNum(self);
        if (num == 0)
        {
            doAnimationAction(self, "celebrate");
        }
        if (num == 1)
        {
            doAnimationAction(self, "celebrate");
        }
        if (num == 3)
        {
            doAnimationAction(self, "celebrate");
        }
        if (num == 2)
        {
            doAnimationAction(self, "bow");
        }
        messageTo(self, "formOfFixer", null, 3, false);
        return SCRIPT_CONTINUE;
    }
    public int formOfFixer(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id mde = utils.getObjIdScriptVar(self, "swarming");
        location mdeLoc = getLocation(mde);
        ai_lib.aiPathTo(self, mdeLoc);
        playClientEffectLoc(self, trial.PRT_WORKING_REPAIR_REZ, getLocation(self), 0.4f);
        playClientEffectLoc(self, trial.PRT_WORKING_REPAIR_REZ, mdeLoc, 0.4f);
        messageTo(self, "completeTranx", null, 2, false);
        return SCRIPT_CONTINUE;
    }
    public int completeTranx(obj_id self, dictionary params) throws InterruptedException
    {
        playClientEffectLoc(self, trial.PRT_WORKING_REPAIR_COMPILE, getLocation(self), 0.4f);
        if (getNum(self) == 0)
        {
            obj_id fixer = create.object(FIXER_ONE, getLocation(self));
            attachScript(fixer, "theme_park.dungeon.mustafar_trials.working_droid_factory.mde_fixer_one");
            obj_id mde = utils.getObjIdScriptVar(self, "swarming");
            destroyObject(mde);
        }
        trial.cleanupNpc(self);
        return SCRIPT_CONTINUE;
    }
    public void doLogging(String section, String message) throws InterruptedException
    {
        if (LOGGING || trial.WORKING_LOGGING)
        {
            LOG("logging/repair_droid/" + section, message);
        }
    }
}
