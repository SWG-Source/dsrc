package script.theme_park.dungeon.mustafar_trials.working_droid_factory;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.ai_lib;
import script.library.utils;
import script.library.trial;

public class hk_final_trigger extends script.base_script
{
    public hk_final_trigger()
    {
    }
    public static final String VOLUME_NAME = "hk_trigger";
    public static final float VOLUME_RANGE = 4.0f;
    public int OnAttach(obj_id self) throws InterruptedException
    {
        messageTo(self, "establishTriggerVolumes", null, 1, false);
        return SCRIPT_CONTINUE;
    }
    public int OnTriggerVolumeEntered(obj_id self, String volumeName, obj_id breacher) throws InterruptedException
    {
        if (!isPlayer(breacher))
        {
            return SCRIPT_CONTINUE;
        }
        String type = getStringObjVar(self, "hk_sequence");
        if (volumeName.equals(VOLUME_NAME) && type.equals("player_spawn_trigger"))
        {
            location selfLoc = getLocation(self);
            location breachLoc = getLocation(breacher);
            float heightDifference = Math.abs(breachLoc.y - selfLoc.y);
            if (heightDifference < 2 && !trial.isHkSpawned(trial.getTop(self)))
            {
                messageTo(trial.getTop(self), "spawnHK", trial.getSessionDict(trial.getTop(self), "spawn_hk"), 0, false);
                return SCRIPT_CONTINUE;
            }
        }
        if (volumeName.equals(VOLUME_NAME) && type.equals("player_blast_trigger"))
        {
            location selfLoc = getLocation(self);
            location breachLoc = getLocation(breacher);
            float heightDifference = Math.abs(breachLoc.y - selfLoc.y);
            if (heightDifference < 2 && !trial.isHkDetonated(trial.getTop(self)))
            {
                messageTo(trial.getTop(self), "detonateHK", trial.getSessionDict(trial.getTop(self), "spawn_hk"), 1, false);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int establishTriggerVolumes(obj_id self, dictionary params) throws InterruptedException
    {
        createTriggerVolume(VOLUME_NAME, VOLUME_RANGE, true);
        return SCRIPT_CONTINUE;
    }
}
