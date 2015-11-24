package script.theme_park.dungeon.mustafar_trials.volcano_battlefield;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.ai_lib;
import script.library.utils;
import script.library.create;
import script.library.trial;

public class event_one extends script.base_script
{
    public event_one()
    {
    }
    public static final String boss = "som_volcano_one_taskmaster";
    public static final String guard = "som_volcano_one_sustainer";
    public static final boolean doLogging = false;
    public int OnAttach(obj_id self) throws InterruptedException
    {
        spawnBoss(self);
        messageTo(self, "spawnGuards", null, 2, false);
        setTriggerVolume(self);
        return SCRIPT_CONTINUE;
    }
    public void setTriggerVolume(obj_id self) throws InterruptedException
    {
        if (!hasTriggerVolume(self, "activateVolume"))
        {
            createTriggerVolume("activateVolume", 45, true);
        }
    }
    public int OnTriggerVolumeEntered(obj_id self, String volumeName, obj_id breacher) throws InterruptedException
    {
        if (isPlayer(breacher))
        {
            if (volumeName.equals("activateVolume"))
            {
                activateEncounter(self);
            }
        }
        return SCRIPT_CONTINUE;
    }
    public void activateEncounter(obj_id self) throws InterruptedException
    {
        obj_id eventBoss = utils.getObjIdScriptVar(self, "eventBoss");
        setInvulnerable(eventBoss, false);
        obj_id[] guardList = utils.getObjIdArrayScriptVar(self, "currentGuardList");
        utils.messageTo(guardList, "activateShield", null, 0, false);
    }
    public void spawnBoss(obj_id self) throws InterruptedException
    {
        location here = getLocation(self);
        obj_id eventBoss = create.object(boss, here);
        if (!isIdValid(eventBoss))
        {
            doLogging("spawnGuards", "Failed to generate boss");
            return;
        }
        setYaw(eventBoss, 0);
        attachScript(eventBoss, "theme_park.dungeon.mustafar_trials.volcano_battlefield.event_one_boss");
        ai_lib.setDefaultCalmBehavior(eventBoss, ai_lib.BEHAVIOR_SENTINEL);
        trial.setParent(self, eventBoss, false);
        utils.setScriptVar(self, "eventBoss", eventBoss);
    }
    public int beginGuardCycle(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id[] guardList = utils.getObjIdArrayScriptVar(self, "currentGuardList");
        utils.messageTo(guardList, "healBoss", null, 0, false);
        messageTo(self, "doGuardAttack", null, 1, false);
        return SCRIPT_CONTINUE;
    }
    public int spawnGuards(obj_id self, dictionary params) throws InterruptedException
    {
        location here = getLocation(self);
        obj_id event_boss = utils.getObjIdScriptVar(self, "eventBoss");
        String[] offSet = 
        {
            "3:2",
            "6:4",
            "9:7",
            "12:10",
            "-3:2",
            "-6:4",
            "-9:7",
            "-12:10"
        };
        obj_id[] guards = new obj_id[8];
        for (int i = 0; i < offSet.length; i++)
        {
            String[] parse = split(offSet[i], ':');
            float locX = here.x + utils.stringToFloat(parse[0]);
            float locZ = here.z + utils.stringToFloat(parse[1]);
            location spawnLoc = new location(locX, here.y, locZ, here.area, here.cell);
            guards[i] = create.object(guard, spawnLoc);
            if (isIdValid(guards[i]))
            {
                ai_lib.setDefaultCalmBehavior(guards[i], ai_lib.BEHAVIOR_SENTINEL);
                attachScript(guards[i], "theme_park.dungeon.mustafar_trials.volcano_battlefield.event_one_guard");
                setYaw(guards[i], 0);
                setObjVar(guards[i], "boss", event_boss);
                setObjVar(guards[i], "parent", self);
                setInvulnerable(guards[i], true);
            }
        }
        utils.setScriptVar(self, "currentGuardList", guards);
        utils.setScriptVar(self, "currentAttacker", guards[0]);
        return SCRIPT_CONTINUE;
    }
    public int doGuardAttack(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id currentAttacker = getCurrentAttacker(self);
        obj_id newAttacker = chooseNewAttacker(self);
        if (!isIdValid(newAttacker))
        {
            doLogging("doGuardAttack", "Unable to get new attacker, guards must be dead");
            return SCRIPT_CONTINUE;
        }
        if (isIdValid(currentAttacker))
        {
            messageTo(currentAttacker, "stopAttack", null, 0, false);
        }
        messageTo(newAttacker, "beginAttack", null, 0, false);
        messageTo(self, "doGuardAttack", null, 10, false);
        return SCRIPT_CONTINUE;
    }
    public obj_id getCurrentAttacker(obj_id self) throws InterruptedException
    {
        if (!utils.hasScriptVar(self, "currentAttacker"))
        {
            return obj_id.NULL_ID;
        }
        return utils.getObjIdScriptVar(self, "currentAttacker");
    }
    public obj_id chooseNewAttacker(obj_id self) throws InterruptedException
    {
        obj_id[] guards = utils.getObjIdArrayScriptVar(self, "currentGuardList");
        obj_id attacker = obj_id.NULL_ID;
        if (utils.hasScriptVar(self, "currentAttacker"))
        {
            attacker = utils.getObjIdScriptVar(self, "currentAttacker");
        }
        if (guards == null || guards.length == 0)
        {
            doLogging("chooseNewAttacker", "The guards scriptVar is empty or null");
            return obj_id.NULL_ID;
        }
        Vector activeGuards = new Vector();
        activeGuards.setSize(0);
        for (int i = 0; i < guards.length; i++)
        {
            if (exists(guards[i]))
            {
                utils.addElement(activeGuards, guards[i]);
            }
        }
        if (activeGuards == null || activeGuards.size() == 0)
        {
            doLogging("chooseNewAttacker", "There are no active guards remaining");
            return obj_id.NULL_ID;
        }
        obj_id newAttacker = obj_id.NULL_ID;
        if (activeGuards.size() == 1)
        {
            newAttacker = ((obj_id)activeGuards.get(0));
        }
        else 
        {
            Vector nonAttackerGuards = new Vector();
            nonAttackerGuards.setSize(0);
            if (isIdValid(attacker))
            {
                for (int k = 0; k < activeGuards.size(); k++)
                {
                    if (((obj_id)activeGuards.get(k)) != attacker)
                    {
                        utils.addElement(nonAttackerGuards, ((obj_id)activeGuards.get(k)));
                    }
                }
            }
            else 
            {
                nonAttackerGuards = activeGuards;
            }
            if (nonAttackerGuards == null || nonAttackerGuards.size() == 0)
            {
                doLogging("chooseNewAttacker", "There are more than one guard left but nonAttackerGuards is empty");
                return obj_id.NULL_ID;
            }
            if (nonAttackerGuards.size() == 1)
            {
                newAttacker = ((obj_id)nonAttackerGuards.get(0));
            }
            else 
            {
                newAttacker = ((obj_id)nonAttackerGuards.get(rand(0, nonAttackerGuards.size() - 1)));
            }
        }
        obj_id[] newGuardList = new obj_id[0];
        if (activeGuards != null)
        {
            newGuardList = new obj_id[activeGuards.size()];
            activeGuards.toArray(newGuardList);
        }
        utils.setScriptVar(self, "currentGuardList", newGuardList);
        utils.setScriptVar(self, "currentAttacker", newAttacker);
        return newAttacker;
    }
    public int eventMobDied(obj_id self, dictionary params) throws InterruptedException
    {
        String type = params.getString("type");
        if (type.equals("boss"))
        {
            winEvent(self);
        }
        return SCRIPT_CONTINUE;
    }
    public void winEvent(obj_id self) throws InterruptedException
    {
        obj_id top = getObjIdObjVar(self, "parent");
        if (!isIdValid(top))
        {
            doLogging("winEvent", "Parent ID is not valid");
            return;
        }
        messageTo(top, "eventDefeated", null, 0, false);
    }
    public void doLogging(String section, String message) throws InterruptedException
    {
        if (doLogging)
        {
            LOG("travis/event_one/" + section, message);
        }
    }
}
