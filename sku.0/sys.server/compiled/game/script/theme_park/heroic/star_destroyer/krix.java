package script.theme_park.heroic.star_destroyer;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.chat;
import script.library.prose;
import script.library.trial;
import script.library.utils;

public class krix extends script.base_script
{
    public krix()
    {
    }
    public static final float[] HEALTH_TRIGGER = 
    {
        0.5f,
        0.10f,
        0.15f,
        0.20f,
        0.25f,
        0.30f,
        0.35f,
        0.40f,
        0.45f,
        0.50f,
        0.55f,
        0.60f,
        0.65f,
        0.70f,
        0.75f,
        0.80f,
        0.85f,
        0.90f,
        0.95f
    };
    public int OnAttach(obj_id self) throws InterruptedException
    {
        trial.setHp(self, 1243250);
        return SCRIPT_CONTINUE;
    }
    public int startCombat(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id[] players = trial.getValidTargetsInCell(trial.getTop(self), "secondaryhangar");
        obj_id[] allSpawn = trial.getObjectsInDungeonWithObjVar(trial.getTop(self), "spawn_id");
        obj_id[] guards = getGuardsFromList(allSpawn);
        if (players == null || players.length == 0)
        {
            dictionary dict = trial.getSessionDict(trial.getTop(self));
            dict.put("triggerType", "triggerId");
            dict.put("triggerName", "reset_krix");
            messageTo(trial.getTop(self), "triggerFired", dict, 0.0f, false);
            return SCRIPT_CONTINUE;
        }
        obj_id mainTarget = trial.getClosestValidTarget(self, players);
        for (int i = 0; i < players.length; i++)
        {
            startCombat(self, players[i]);
            if (guards != null && guards.length > 0)
            {
                for (int q = 0; q < guards.length; q++)
                {
                    startCombat(guards[q], players[i]);
                    setHate(guards[q], players[i], rand(10, 1000));
                    startCombat(self, players[i]);
                }
            }
        }
        setHate(self, mainTarget, 10000);
        startAttackCycle(self);
        return SCRIPT_CONTINUE;
    }
    public obj_id[] getGuardsFromList(obj_id[] allSpawn) throws InterruptedException
    {
        if (allSpawn == null || allSpawn.length == 0)
        {
            return null;
        }
        Vector guards = new Vector();
        guards.setSize(0);
        for (int i = 0; i < allSpawn.length; i++)
        {
            if ((getStringObjVar(allSpawn[i], "spawn_id")).equals("sd_gren"))
            {
                if (!isDead(allSpawn[i]))
                {
                    guards.add(allSpawn[i]);
                }
            }
        }
        if (guards == null || guards.size() == 0)
        {
            return null;
        }
        if (guards != null)
        {
            allSpawn = new obj_id[guards.size()];
            guards.toArray(allSpawn);

        }
        return allSpawn;
    }
    public void startAttackCycle(obj_id self) throws InterruptedException
    {
        dictionary dict = trial.getSessionDict(self, "special");
        dict.put("next", 0);
        messageTo(self, "executeBurn", dict, 5.0f, false);
        messageTo(self, "executeFocus", dict, 5.0f, false);
    }
    public int executeBurn(obj_id self, dictionary params) throws InterruptedException
    {
        if (!trial.verifySession(self, params, "special") || isDead(self))
        {
            return SCRIPT_CONTINUE;
        }
        for (int i = 0; i < 30; i++)
        {
            String targetLoc = getBurnLocation(self);
            queueCommand(self, (1537892877), getHateTarget(self), targetLoc, COMMAND_PRIORITY_DEFAULT);
        }
        messageTo(self, "executeBurn", trial.getSessionDict(self), 10.0f, false);
        return SCRIPT_CONTINUE;
    }
    public int executeFocus(obj_id self, dictionary params) throws InterruptedException
    {
        if (!trial.verifySession(self, params, "special") || isDead(self))
        {
            return SCRIPT_CONTINUE;
        }
        validateCombat(self);
        obj_id[] players = trial.getValidTargetsInCell(trial.getTop(self), "secondaryhangar");
        obj_id[] allSpawn = trial.getObjectsInDungeonWithObjVar(trial.getTop(self), "spawn_id");
        obj_id[] guards = getGuardsFromList(allSpawn);
        if (players == null || players.length == 0)
        {
            return SCRIPT_CONTINUE;
        }
        
        if (guards == null || guards.length == 0)
        {
            trial.bumpSession(self, "special");
            phaseTwo(self);
            return SCRIPT_CONTINUE;
        }
        obj_id focus = players[rand(0, players.length - 1)];
        for (int q = 0; q < players.length; q++)
        {
            if (isDead(players[q]) || isIncapacitated(players[q]))
            {
                continue;
            }
            for (int f = 0; f < guards.length; f++)
            {
                if (isDead(guards[f]))
                {
                    continue;
                }
                messageTo(guards[f], "resetHate", null, 5.0f, false);
                if (players[q] == focus)
                {
                    utils.setScriptVar(guards[f], "focus", focus);
                    setHate(guards[f], players[q], 10000.0f);
                }
                else 
                {
                    setHate(guards[f], players[q], 1.0f);
                }
            }
        }
        chat.chat(self, "No, you fools! Kill " + getPlayerName(focus) + "!");
        prose_package pp = new prose_package();
        pp = prose.setStringId(pp, new string_id("spam", "krix_directed_target_spam"));
        pp = prose.setTO(pp, focus);
        utils.sendSystemMessageProse(players, pp);
        messageTo(self, "executeFocus", trial.getSessionDict(self, "special"), 12.0f, false);
        return SCRIPT_CONTINUE;
    }
    public void phaseTwo(obj_id self) throws InterruptedException
    {
        chat.chat(self, "Those useless Imperials, I will do this myself.");
        obj_id[] hateList = getHateList(self);
        utils.sendSystemMessage(hateList, new string_id("spam", "krix_do_myself"));
        messageTo(self, "megaBurn", trial.getSessionDict(self, "special"), 3.0f, false);
    }
    public String getBurnLocation(obj_id self) throws InterruptedException
    {
        float x = rand(-40.0f, 40.0f);
        float y = 173.834f;
        float z = rand(-1.0f, 75.0f);
        return "" + x + " " + y + " " + z + " " + getLocation(self).cell + " " + x + " " + y + " " + z;
    }
    public int megaBurn(obj_id self, dictionary params) throws InterruptedException
    {
        if (!trial.verifySession(self, params, "special") || isDead(self))
        {
            return SCRIPT_CONTINUE;
        }
        validateCombat(self);
        obj_id[] hateList = getHateList(self);
        Vector validTargets = new Vector();
        validTargets.setSize(0);
        for (int i = 0; i < hateList.length; i++)
        {
            if (!isIdValid(hateList[i]) || !exists(hateList[i]) || isDead(hateList[i]) || !canSee(self, hateList[i]))
            {
                continue;
            }
            validTargets.add(hateList[i]);
        }
        obj_id target = ((obj_id)validTargets.get(rand(0, validTargets.size() - 1)));
        location tarLoc = getLocation(target);
        String targetLoc = tarLoc.x + " " + tarLoc.y + " " + tarLoc.z + " " + tarLoc.cell + " " + tarLoc.x + " " + tarLoc.y + " " + tarLoc.z;
        queueCommand(self, (-225032667), getHateTarget(self), targetLoc, COMMAND_PRIORITY_DEFAULT);
        queueCommand(self, (-1364631418), getHateTarget(self), targetLoc, COMMAND_PRIORITY_DEFAULT);
        messageTo(self, "megaBurn", trial.getSessionDict(self, "special"), 6.0f, false);
        return SCRIPT_CONTINUE;
    }
    public int OnCreatureDamaged(obj_id self, obj_id attacker, obj_id weapon, int[] damage) throws InterruptedException
    {
        float max = (float)getMaxHealth(self);
        float current = (float)getHealth(self);
        float ratio = current / max;
        int healthTrigger = getHealthTrigger(self, ratio);
        if (healthTrigger == -1)
        {
            return SCRIPT_CONTINUE;
        }
        dictionary dict = trial.getSessionDict(trial.getTop(self));
        dict.put("triggerType", "triggerId");
        dict.put("triggerName", "hasten_grenadier");
        messageTo(trial.getTop(self), "triggerFired", dict, 0.0f, false);
        return SCRIPT_CONTINUE;
    }
    public int getHealthTrigger(obj_id self, float ratio) throws InterruptedException
    {
        for (int i = 0; i < HEALTH_TRIGGER.length; i++)
        {
            if (utils.hasScriptVar(self, "health_trigger_list." + i))
            {
                continue;
            }
            if (ratio <= HEALTH_TRIGGER[i])
            {
                utils.setScriptVar(self, "health_trigger_list." + i, 1);
                return i;
            }
        }
        return -1;
    }
    public void validateCombat(obj_id self) throws InterruptedException
    {
        obj_id[] players = trial.getValidTargetsInCell(trial.getTop(self), "secondaryhangar");
        if (players == null || players.length == 0)
        {
            dictionary dict = trial.getSessionDict(trial.getTop(self));
            dict.put("triggerType", "triggerId");
            dict.put("triggerName", "reset_krix");
            messageTo(trial.getTop(self), "triggerFired", dict, 0.0f, false);
            return;
        }
        for (int i = 0; i < players.length; i++)
        {
            startCombat(self, players[i]);
        }
    }
}
