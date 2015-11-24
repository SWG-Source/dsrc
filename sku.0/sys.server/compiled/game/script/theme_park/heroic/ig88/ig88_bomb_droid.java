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
import script.library.stealth;
import script.library.trial;
import script.library.utils;

public class ig88_bomb_droid extends script.base_script
{
    public ig88_bomb_droid()
    {
    }
    public int setBombDroidMaxHealth(obj_id self, dictionary params) throws InterruptedException
    {
        if (!isIdValid(self))
        {
            return SCRIPT_CONTINUE;
        }
        if (!hasObjVar(self, "bombDroidMaxHealthSet"))
        {
            setMaxAttrib(self, HEALTH, 8000);
            setAttrib(self, HEALTH, 8000);
            setObjVar(self, "bombDroidMaxHealthSet", 1);
        }
        return SCRIPT_CONTINUE;
    }
    public int findTarget(obj_id self, dictionary params) throws InterruptedException
    {
        if (!isIdValid(self) || ai_lib.isDead(self))
        {
            return SCRIPT_CONTINUE;
        }
        if (getMovementPercent(self) >= 1.0f)
        {
            setMovementPercent(self, 0.5f + rand(0f, 0.1f));
        }
        if (isInvulnerable(self))
        {
            setInvulnerable(self, false);
        }
        obj_id dungeon = getTopMostContainer(self);
        obj_id target = utils.getObjIdScriptVar(self, "target");
        if (!isIdValid(dungeon))
        {
            return SCRIPT_CONTINUE;
        }
        setObjVar(self, "ai.combat.oneShotAction", "ig88_bomb_explode");
        dictionary sessionDict = new dictionary();
        if (!isIdValid(target) || isDead(target) || stealth.hasInvisibleBuff(target))
        {
            obj_id[] targets = trial.getNonStealthedTargetsInCell(dungeon, "r1");
            if (targets == null || targets.length <= 0)
            {
                messageTo(dungeon, "ig88_failed", sessionDict, 0, false);
                return SCRIPT_CONTINUE;
            }
            obj_id closest = trial.getClosest(self, targets);
            obj_id secondClosest = trial.getSecondClosest(self, targets);
            if (isIdValid(closest) && isIdValid(secondClosest))
            {
                target = rand(0, 1) == 1 ? closest : secondClosest;
            }
            else 
            {
                target = closest;
            }
        }
        if (!isIdValid(target))
        {
            messageTo(self, "findTarget", sessionDict, 1, false);
            return SCRIPT_CONTINUE;
        }
        if (utils.getDistance2D(getLocation(target), getLocation(self)) < 5)
        {
            if (!utils.hasScriptVar(self, "exploded"))
            {
                queueCommand(self, (2040083514), target, "", COMMAND_PRIORITY_DEFAULT);
            }
            else 
            {
                dictionary messageParams = new dictionary();
                messageParams.put("npc", self);
                messageTo(dungeon, "killNPC", messageParams, 0, false);
                return SCRIPT_CONTINUE;
            }
            messageTo(self, "findTarget", sessionDict, 1, false);
            return SCRIPT_CONTINUE;
        }
        setHate(self, target, 5000);
        utils.setScriptVar(self, "target", target);
        messageTo(self, "findTarget", sessionDict, 1, false);
        return SCRIPT_CONTINUE;
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        if (!isIdValid(self))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id dungeon = getTopMostContainer(self);
        if (!isIdValid(dungeon))
        {
            return SCRIPT_CONTINUE;
        }
        dictionary sessionDict = new dictionary();
        messageTo(self, "colorize", sessionDict, 0.25f, true);
        return SCRIPT_CONTINUE;
    }
    public int colorize(obj_id self, dictionary params) throws InterruptedException
    {
        if (!isIdValid(self))
        {
            return SCRIPT_CONTINUE;
        }
        hue.setColor(self, hue.INDEX_1, 9);
        hue.setColor(self, hue.INDEX_2, 15);
        hue.setTexture(self, 1, 7);
        return SCRIPT_CONTINUE;
    }
    public int OnExitedCombat(obj_id self) throws InterruptedException
    {
        if (!isIdValid(self))
        {
            return SCRIPT_CONTINUE;
        }
        dictionary sessionDict = new dictionary();
        messageTo(self, "findTarget", sessionDict, 0.25f, true);
        return SCRIPT_CONTINUE;
    }
}
