package script.theme_park.heroic.axkva_min;

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

public class axkva_storm extends script.base_script
{
    public axkva_storm()
    {
    }
    public static final float STORM_PULSE = 1.0f;
    public int OnAttach(obj_id self) throws InterruptedException
    {
        setInvulnerable(self, true);
        messageTo(self, "beginPath", null, 1.0f, false);
        messageTo(self, "forceStormPulse", null, 2.0f, false);
        return SCRIPT_CONTINUE;
    }
    public int beginPath(obj_id self, dictionary params) throws InterruptedException
    {
        location[] path = getLocationArrayObjVar(self, "path");
        patrolRandom(self, path);
        return SCRIPT_CONTINUE;
    }
    public int forceStormPulse(obj_id self, dictionary params) throws InterruptedException
    {
        location leadLoc = utils.findLocInFrontOfTarget(self, 1.0f);
        obj_id[] targets = trial.getValidTargetsInRadius(self, 5.0f);
        obj_id master = getMaster(self);
        if (!isIdValid(master) || ai_lib.isDead(master) || !ai_lib.isInCombat(master))
        {
            trial.cleanupObject(self);
            return SCRIPT_CONTINUE;
        }
        if (targets != null && targets.length > 0)
        {
            location loc = getLocation(self);
            String locationData = "" + loc.x + " " + loc.y + " " + loc.z + " " + loc.cell + " " + loc.x + " " + loc.y + " " + loc.z;
            queueCommand(getMaster(self), (2071988811), targets[0], locationData, COMMAND_PRIORITY_DEFAULT);
        }
        messageTo(self, "forceStormPulse", null, STORM_PULSE, false);
        return SCRIPT_CONTINUE;
    }
    public int OnMovePathComplete(obj_id self) throws InterruptedException
    {
        obj_id master = getMaster(self);
        if (!isIdValid(master) || ai_lib.isDead(master))
        {
            trial.cleanupObject(self);
            return SCRIPT_CONTINUE;
        }
        return SCRIPT_CONTINUE;
    }
}
