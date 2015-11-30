package script.theme_park.heroic.axkva_min;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.trial;
import script.library.utils;

public class force_storm extends script.base_script
{
    public force_storm()
    {
    }
    public static final float TARGET_SWITCH = 6.0f;
    public static final float STORM_PULSE = 1.0f;
    public static final float LIFESPAN = 18.0f;
    public int OnAttach(obj_id self) throws InterruptedException
    {
        setInvulnerable(self, true);
        messageTo(self, "acquireTarget", null, 1.0f, false);
        messageTo(self, "forceStormPulse", null, 2.0f, false);
        messageTo(self, "endLifeCycle", null, LIFESPAN, false);
        return SCRIPT_CONTINUE;
    }
    public int acquireTarget(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id[] hateList = utils.getObjIdArrayScriptVar(self, trial.KIMARU_HATE_LIST);
        obj_id target = hateList[rand(0, hateList.length - 1)];
        follow(self, target, 0.0f, 0.1f);
        messageTo(self, "acquireTarget", null, TARGET_SWITCH, false);
        return SCRIPT_CONTINUE;
    }
    public int forceStormPulse(obj_id self, dictionary params) throws InterruptedException
    {
        location leadLoc = utils.findLocInFrontOfTarget(self, 1.0f);
        obj_id[] targets = trial.getValidTargetsInRadius(self, 5.0f);
        if (targets != null && targets.length > 0)
        {
            location loc = getLocation(self);
            String locationData = "" + loc.x + " " + loc.y + " " + loc.z + " " + loc.cell + " " + loc.x + " " + loc.y + " " + loc.z;
            queueCommand(getMaster(self), (-399812533), targets[0], locationData, COMMAND_PRIORITY_DEFAULT);
        }
        messageTo(self, "forceStormPulse", null, STORM_PULSE, false);
        return SCRIPT_CONTINUE;
    }
    public int endLifeCycle(obj_id self, dictionary params) throws InterruptedException
    {
        trial.cleanupObject(self);
        return SCRIPT_CONTINUE;
    }
}
