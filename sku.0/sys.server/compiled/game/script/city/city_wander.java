package script.city;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.ai_lib;

public class city_wander extends script.base_script
{
    public city_wander()
    {
    }
    public static final String[] patrolPoints = 
    {
        "city1",
        "city2",
        "city3",
        "city4",
        "city5",
        "city6"
    };
    public int OnAttach(obj_id self) throws InterruptedException
    {
        ai_lib.setDefaultCalmBehavior(self, ai_lib.BEHAVIOR_SENTINEL);
        messageTo(self, "pathRandom", null, 1, false);
        return SCRIPT_CONTINUE;
    }
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        messageTo(self, "pathRandom", null, 1, false);
        return SCRIPT_CONTINUE;
    }
    public int pathRandom(obj_id self, dictionary params) throws InterruptedException
    {
        ai_lib.setPatrolRandomNamedPath(self, patrolPoints);
        return SCRIPT_CONTINUE;
    }
    public int OnLoiterMoving(obj_id self) throws InterruptedException
    {
        stop(self);
        messageTo(self, "pathRandom", null, 2, false);
        return SCRIPT_OVERRIDE;
    }
    public int OnMovePathComplete(obj_id self) throws InterruptedException
    {
        messageTo(self, "pathRandom", null, rand(30, 60), false);
        return SCRIPT_CONTINUE;
    }
    public int OnMovePathNotFound(obj_id self) throws InterruptedException
    {
        if (hasObjVar(self, "checkingFeet"))
        {
            int checked = getIntObjVar(self, "checkingFeet");
            if (checked > 10)
            {
                return SCRIPT_CONTINUE;
            }
        }
        messageTo(self, "pathRandom", null, rand(30, 60), false);
        checkForFeetGluedToFloor(self);
        return SCRIPT_CONTINUE;
    }
    public void checkForFeetGluedToFloor(obj_id self) throws InterruptedException
    {
        if (!hasObjVar(self, "checkingFeet"))
        {
            setObjVar(self, "checkingFeet", 1);
            location here = getLocation(self);
            setObjVar(self, "mightBeStuck", here);
            messageTo(self, "amIStuck", null, 180, false);
            return;
        }
        else 
        {
            int checked = getIntObjVar(self, "checkingFeet");
            checked = checked + 1;
            setObjVar(self, "checkingFeet", checked);
            if (checked > 10)
            {
                return;
            }
            location here = getLocation(self);
            setObjVar(self, "mightBeStuck", here);
            messageTo(self, "amIStuck", null, 180, false);
            return;
        }
    }
    public int amIStuck(obj_id self, dictionary params) throws InterruptedException
    {
        location now = getLocation(self);
        location here = getLocationObjVar(self, "mightBeStuck");
        if (now == null || now.equals(here))
        {
            messageTo(self, "killIt", null, 3, false);
        }
        else 
        {
            messageTo(self, "cleanUpChecks", null, 10, false);
        }
        return SCRIPT_CONTINUE;
    }
    public int killIt(obj_id self, dictionary params) throws InterruptedException
    {
        LOG("NPC_Cleanup", "NPC: " + self + " at " + getLocation(self) + " has cleaned itself up because it couldn't find anyplace to go, according to the City_Wander script.");
        destroyObject(self);
        return SCRIPT_CONTINUE;
    }
    public int cleanUpChecks(obj_id self, dictionary params) throws InterruptedException
    {
        int checks = getIntObjVar(self, "checkingFeet");
        if (checks > 0)
        {
            checks = checks - 1;
            setObjVar(self, "checkingFeet", checks);
        }
        return SCRIPT_CONTINUE;
    }
}
