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

public class event_five extends script.base_script
{
    public event_five()
    {
    }
    public static final String BOSS = "som_volcano_five_boss_septipod";
    public static final boolean LOGGING = false;
    public int OnAttach(obj_id self) throws InterruptedException
    {
        spawnEvent(self);
        return SCRIPT_CONTINUE;
    }
    public int activateEvent(obj_id self, dictionary params) throws InterruptedException
    {
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
        if (isPlayer(breacher) && !isIncapacitated(breacher))
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
        messageTo(eventBoss, "activate", null, 0, false);
    }
    public void spawnEvent(obj_id self) throws InterruptedException
    {
        location here = getLocation(self);
        obj_id eventBoss = create.object(BOSS, here);
        if (!isIdValid(eventBoss))
        {
            doLogging("spawnGuards", "Failed to generate boss");
            return;
        }
        setYaw(eventBoss, 195);
        attachScript(eventBoss, "theme_park.dungeon.mustafar_trials.volcano_battlefield.event_five_boss");
        ai_lib.setDefaultCalmBehavior(eventBoss, ai_lib.BEHAVIOR_SENTINEL);
        trial.setParent(self, eventBoss, false);
        utils.setScriptVar(self, "eventBoss", eventBoss);
        here.y = here.y - 1;
        String[] offSet = 
        {
            "-19:20",
            "24:23",
            "-4:-32",
            "-6:-22",
            "-12:-6",
            "-12:6",
            "13:15",
            "17:0",
            "18:9"
        };
        for (int i = 0; i < offSet.length; i++)
        {
            String[] parse = split(offSet[i], ':');
            float locX = here.x + utils.stringToFloat(parse[0]);
            float locZ = here.z + utils.stringToFloat(parse[1]);
            location spawnLoc = new location(locX, here.y, locZ, here.area, here.cell);
            obj_id item = createObject(trial.WP_OBJECT, spawnLoc);
            if (!isIdValid(item))
            {
                doLogging("spawnActors", "Tried to create invalid item(" + trial.WP_OBJECT + ")");
                return;
            }
            trial.markAsTempObject(item, true);
            trial.setParent(self, item, false);
            if (i < 3)
            {
                setObjVar(item, "trioAddSpawn", true);
            }
            else 
            {
                setObjVar(item, "midguardSpawn", true);
            }
        }
    }
    public int eventMobDied(obj_id self, dictionary params) throws InterruptedException
    {
        String type = params.getString("type");
        if (type.equals("boss"))
        {
            winEncounter(self);
        }
        if (type.equals("midPointGuard"))
        {
            int guard = 0;
            if (utils.hasScriptVar(self, "midGuard"))
            {
                guard = utils.getIntScriptVar(self, "midGuard");
            }
            guard += 1;
            utils.setScriptVar(self, "midGuard", guard);
            if (guard == 6)
            {
                reactivateBoss(self);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_CONTINUE;
    }
    public void reactivateBoss(obj_id self) throws InterruptedException
    {
        if (!utils.hasScriptVar(self, "eventBoss"))
        {
            doLogging("reactivateBoss", "Do not have boss script var");
            return;
        }
        obj_id eventBoss = utils.getObjIdScriptVar(self, "eventBoss");
        if (!isIdValid(eventBoss) || !exists(eventBoss))
        {
            doLogging("reactivateBoss", "Boss was invalid or does not exist");
        }
        messageTo(eventBoss, "reactivate", null, 3, false);
    }
    public void winEncounter(obj_id self) throws InterruptedException
    {
        obj_id top = trial.getParent(self);
        if (!isIdValid(top))
        {
            doLogging("winEvent", "Parent ID is not valid");
            return;
        }
        messageTo(top, "eventDefeated", null, 0, false);
    }
    public void doLogging(String section, String message) throws InterruptedException
    {
        if (LOGGING || trial.VOLCANO_LOGGING)
        {
            LOG("logging/event_five/" + section, message);
        }
    }
}
