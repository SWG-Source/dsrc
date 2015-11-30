package script.theme_park.heroic.ig88;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.ai_lib;
import script.library.chat;
import script.library.hue;
import script.library.trial;
import script.library.utils;

public class ig88_super_battle_droid extends script.base_script
{
    public ig88_super_battle_droid()
    {
    }
    public obj_id getRocketTarget(obj_id self) throws InterruptedException
    {
        obj_id dungeon = getTopMostContainer(self);
        obj_id target = null;
        if (!isIdValid(self) || !isIdValid(dungeon))
        {
            return null;
        }
        obj_id[] targets = trial.getNonStealthedTargetsInCell(dungeon, "r1");
        if (targets == null || targets.length <= 0)
        {
            dictionary sessionDict = new dictionary();
            messageTo(dungeon, "ig88_failed", sessionDict, 0, false);
            return null;
        }
        target = targets[rand(0, targets.length - 1)];
        if (!isIdValid(target))
        {
            return null;
        }
        return target;
    }
    public void findNextTarget(obj_id self) throws InterruptedException
    {
        if (!isIdValid(self) || ai_lib.isDead(self) || utils.hasScriptVar(self, "flameThrowerUnequipped"))
        {
            return;
        }
        obj_id dungeon = getTopMostContainer(self);
        obj_id target = null;
        if (!isIdValid(dungeon))
        {
            return;
        }
        location pathToPoint = null;
        String whichSuper = getStringObjVar(self, "spawn_id");
        boolean firstSuperDroid = false;
        if (whichSuper != null && whichSuper.length() > 0)
        {
            if (whichSuper.equals("super_battle_droid1"))
            {
                pathToPoint = new location(5, 0, 10, getLocation(self).area, getLocation(self).cell);
                firstSuperDroid = true;
            }
            else 
            {
                pathToPoint = new location(-5, 0, 10, getLocation(self).area, getLocation(self).cell);
            }
        }
        if (pathToPoint == null)
        {
            return;
        }
        if (utils.getDistance2D(pathToPoint, getLocation(self)) > 2)
        {
            setHomeLocation(self, pathToPoint);
            pathTo(self, pathToPoint);
            return;
        }
        setMovementPercent(self, 0.0f);
        setObjVar(self, "forceNoMovement", 1);
        if (isInvulnerable(self))
        {
            setInvulnerable(self, false);
            setMaxAttrib(self, HEALTH, getMaxAttrib(self, HEALTH) * 2);
            setAttrib(self, HEALTH, getMaxAttrib(self, HEALTH) * 2);
            dictionary sessionDict = new dictionary();
            messageTo(self, "findRocketTarget", sessionDict, rand(1, 3), false);
        }
        obj_id[] targets = trial.getObjectsInDungeonWithObjVar(dungeon, "spawn_id");
        if (targets == null || targets.length <= 0)
        {
            return;
        }
        for (int i = 0; i < targets.length; i++)
        {
            String checkSpawn = getStringObjVar(targets[i], "spawn_id");
            if (firstSuperDroid && checkSpawn.equals("mouse_droid1"))
            {
                target = targets[i];
                break;
            }
            if (!firstSuperDroid && checkSpawn.equals("mouse_droid2"))
            {
                target = targets[i];
                break;
            }
        }
        if (!isIdValid(target))
        {
            return;
        }
        if ((float)getAttrib(self, ACTION) / (float)getMaxAttrib(self, ACTION) < 0.1f)
        {
            destroyMouseDroids(dungeon);
            return;
        }
        setHate(self, target, 1000000);
    }
    public void destroyMouseDroids(obj_id dungeon) throws InterruptedException
    {
        obj_id[] targets = trial.getObjectsInDungeonWithObjVar(dungeon, "spawn_id");
        if (targets == null || targets.length <= 0)
        {
            return;
        }
        for (int i = 0; i < targets.length; i++)
        {
            String checkSpawn = getStringObjVar(targets[i], "spawn_id");
            if (checkSpawn.equals("mouse_droid1") || checkSpawn.equals("mouse_droid2"))
            {
                destroyObject(targets[i]);
            }
        }
    }
    public int findTarget(obj_id self, dictionary params) throws InterruptedException
    {
        if (!isIdValid(self))
        {
            return SCRIPT_CONTINUE;
        }
        dictionary sessionDict = new dictionary();
        messageTo(self, "findTarget", sessionDict, 1, false);
        findNextTarget(self);
        return SCRIPT_CONTINUE;
    }
    public int findRocketTarget(obj_id self, dictionary params) throws InterruptedException
    {
        if (!isIdValid(self))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id target = getRocketTarget(self);
        if (isIdValid(target))
        {
            setObjVar(self, "ai.combat.oneShotAction", "ig88_rocket_launch");
        }
        dictionary sessionDict = new dictionary();
        messageTo(self, "findRocketTarget", sessionDict, 1 + rand(1, 2), false);
        return SCRIPT_CONTINUE;
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        dictionary sessionDict = new dictionary();
        messageTo(self, "colorize", sessionDict, 0.25f, false);
        return SCRIPT_CONTINUE;
    }
    public int colorize(obj_id self, dictionary params) throws InterruptedException
    {
        if (!isIdValid(self))
        {
            return SCRIPT_CONTINUE;
        }
        hue.setColor(self, hue.INDEX_1, 9);
        hue.setColor(self, hue.INDEX_2, 14);
        hue.setTexture(self, 1, 9);
        return SCRIPT_CONTINUE;
    }
}
