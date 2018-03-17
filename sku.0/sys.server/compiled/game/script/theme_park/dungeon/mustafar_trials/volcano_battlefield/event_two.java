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

public class event_two extends script.base_script
{
    public event_two()
    {
    }
    public static final String boss = "som_volcano_two_ak_prime";
    public static final String guard = "som_volcano_two_hk77";
    public static final boolean LOGGING = false;
    public int OnAttach(obj_id self) throws InterruptedException
    {
        spawnBoss(self);
        messageTo(self, "spawnGuards", null, 2, false);
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
        setInvulnerable(eventBoss, false);
        obj_id[] guardList = utils.getObjIdArrayScriptVar(self, "guardList");
        utils.messageTo(guardList, "activate", null, 0, false);
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
        attachScript(eventBoss, "theme_park.dungeon.mustafar_trials.volcano_battlefield.event_two_boss");
        ai_lib.setDefaultCalmBehavior(eventBoss, ai_lib.BEHAVIOR_SENTINEL);
        trial.setParent(self, eventBoss, false);
        utils.setScriptVar(self, "eventBoss", eventBoss);
    }
    public int spawnGuards(obj_id self, dictionary params) throws InterruptedException
    {
        location here = getLocation(self);
        String[] offSet = 
        {
            "-6:-6",
            "6:6",
            "-6:6",
            "6:-6"
        };
        obj_id[] guards = new obj_id[4];
        obj_id eventBoss = utils.getObjIdScriptVar(self, "eventBoss");
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
                attachScript(guards[i], "theme_park.dungeon.mustafar_trials.volcano_battlefield.event_two_guard");
                setYaw(guards[i], 0);
                trial.setParent(self, guards[i], false);
                setObjVar(guards[i], "boss", eventBoss);
                setInvulnerable(guards[i], true);
            }
        }
        utils.setScriptVar(self, "guardList", guards);
        ai_lib.establishAgroLink(eventBoss, guards);
        return SCRIPT_CONTINUE;
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
            LOG("travis/event_two/" + section, message);
        }
    }
}
