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

public class event_four extends script.base_script
{
    public event_four()
    {
    }
    public static final String BOSS = "som_volcano_four_cym_prototype";
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
        attachScript(eventBoss, "theme_park.dungeon.mustafar_trials.volcano_battlefield.event_four_boss");
        ai_lib.setDefaultCalmBehavior(eventBoss, ai_lib.BEHAVIOR_SENTINEL);
        trial.setParent(self, eventBoss, false);
        utils.setScriptVar(self, "eventBoss", eventBoss);
        String[] offSet = 
        {
            "16:-24",
            "-29:-16",
            "-1:11",
            "29:-6"
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
            setObjVar(item, "event_5_spawn_point", true);
        }
    }
    public int eventMobDied(obj_id self, dictionary params) throws InterruptedException
    {
        String type = params.getString("type");
        if (type.equals("boss"))
        {
            winEncounter(self);
        }
        return SCRIPT_CONTINUE;
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
            LOG("logging/event_four/" + section, message);
        }
    }
}
