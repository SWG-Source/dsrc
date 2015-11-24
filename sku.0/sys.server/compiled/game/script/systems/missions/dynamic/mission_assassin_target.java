package script.systems.missions.dynamic;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.ai_lib;

public class mission_assassin_target extends script.systems.missions.base.mission_dynamic_base
{
    public mission_assassin_target()
    {
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        ai_lib.setDefaultCalmBehavior(self, ai_lib.BEHAVIOR_SENTINEL);
        messageTo(self, "pathRandom", null, rand(1, 5), false);
        return SCRIPT_CONTINUE;
    }
    public int OnAddedToWorld(obj_id self) throws InterruptedException
    {
        return SCRIPT_CONTINUE;
    }
    public int OnIncapacitated(obj_id self, obj_id objKiller) throws InterruptedException
    {
        setObjVar(self, "intKilled", 1);
        obj_id objDestroyer = getObjIdObjVar(self, "objDestroyer");
        sendAssassinSuccess(getObjIdObjVar(self, "objMission"));
        setObjVar(self, "intKilled", 1);
        return SCRIPT_CONTINUE;
    }
    public int destroySelf(obj_id self, dictionary params) throws InterruptedException
    {
        destroyObject(self);
        return SCRIPT_CONTINUE;
    }
    public int pathRandom(obj_id self, dictionary params) throws InterruptedException
    {
        String waypoint = pickDestination();
        pathTo(self, waypoint);
        return SCRIPT_OVERRIDE;
    }
    public int OnLoiterMoving(obj_id self) throws InterruptedException
    {
        stop(self);
        messageTo(self, "pathRandom", null, 2, false);
        return SCRIPT_CONTINUE;
    }
    public String pickDestination() throws InterruptedException
    {
        String waypoint = "exit";
        int whichWay = rand(1, 6);
        switch (whichWay)
        {
            case 1:
            waypoint = "city1";
            break;
            case 2:
            waypoint = "city2";
            break;
            case 3:
            waypoint = "city3";
            break;
            case 4:
            waypoint = "city4";
            break;
            case 5:
            waypoint = "city5";
            break;
            case 6:
            waypoint = "city6";
            break;
        }
        return waypoint;
    }
    public int resumeDefaultCalmBehavior(obj_id self, dictionary params) throws InterruptedException
    {
        messageTo(self, "pathRandom", null, rand(30, 60), false);
        return SCRIPT_OVERRIDE;
    }
    public int OnMovePathComplete(obj_id self) throws InterruptedException
    {
        messageTo(self, "pathRandom", null, rand(30, 60), false);
        return SCRIPT_CONTINUE;
    }
    public int OnMovePathNotFound(obj_id self) throws InterruptedException
    {
        messageTo(self, "pathRandom", null, rand(30, 60), false);
        return SCRIPT_CONTINUE;
    }
}
