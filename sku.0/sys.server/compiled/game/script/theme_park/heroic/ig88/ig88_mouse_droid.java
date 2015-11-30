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
import script.library.trial;
import script.library.utils;

public class ig88_mouse_droid extends script.base_script
{
    public ig88_mouse_droid()
    {
    }
    public void findNextTarget(obj_id self) throws InterruptedException
    {
        if (ai_lib.isDead(self))
        {
            return;
        }
        obj_id dungeon = getTopMostContainer(self);
        if (!isIdValid(dungeon))
        {
            return;
        }
        location[] waypoints = new location[4];
        waypoints[0] = new location(20, 0, 30, getLocation(self).area, getLocation(self).cell);
        waypoints[1] = new location(20, 0, -10, getLocation(self).area, getLocation(self).cell);
        waypoints[2] = new location(-20, 0, -10, getLocation(self).area, getLocation(self).cell);
        waypoints[3] = new location(-20, 0, 30, getLocation(self).area, getLocation(self).cell);
        int waypoint;
        int lastWaypoint = -1;
        if (getMovementPercent(self) >= 1.0f)
        {
            setMovementPercent(self, 0.5f);
        }
        if (!utils.hasScriptVar(self, "currentWaypoint"))
        {
            waypoint = 0;
        }
        else 
        {
            waypoint = lastWaypoint = utils.getIntScriptVar(self, "currentWaypoint");
        }
        if (utils.getDistance2D(waypoints[waypoint], getLocation(self)) < 2 && hasObjVar(self, "spawn_id"))
        {
            String whichMouse = getStringObjVar(self, "spawn_id");
            if (whichMouse.equals("mouse_droid1"))
            {
                waypoint = (waypoint + 1 > 3 ? 0 : waypoint + 1);
            }
            else 
            {
                waypoint = (waypoint - 1 < 0 ? 3 : waypoint - 1);
            }
        }
        utils.setScriptVar(self, "currentWaypoint", waypoint);
        
        {
            setHomeLocation(self, waypoints[waypoint]);
            pathTo(self, waypoints[waypoint]);
        }
    }
    public int findTarget(obj_id self, dictionary params) throws InterruptedException
    {
        if (!isIdValid(self))
        {
            return SCRIPT_CONTINUE;
        }
        if (hasScript(self, "ai.creature_combat"))
        {
            detachScript(self, "ai.ai");
            detachScript(self, "ai.creature_combat");
            detachScript(self, "ai.ai_combat_movement");
        }
        if (getLocomotion(self) != LOCOMOTION_RUNNING)
        {
            
        }
        
        {
            setMovementRun(self);
        }
        findNextTarget(self);
        dictionary sessionDict = new dictionary();
        messageTo(self, "findTarget", sessionDict, 1, false);
        return SCRIPT_CONTINUE;
    }
}
