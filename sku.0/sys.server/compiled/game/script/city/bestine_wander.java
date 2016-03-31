package script.city;

import script.dictionary;
import script.obj_id;

public class bestine_wander extends script.base_script
{
    public bestine_wander()
    {
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        messageTo(self, "pathRandom", null, 2, false);
        return SCRIPT_CONTINUE;
    }
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        messageTo(self, "pathRandom", null, rand(10, 20), false);
        return SCRIPT_CONTINUE;
    }
    public int pathRandom(obj_id self, dictionary params) throws InterruptedException
    {
        String waypoint = pickDestination();
        if (hasScript(self, "ai.ai"))
        {
            setObjVar(self, "ai.persistentPathingWaypoint", waypoint);
            messageTo(self, "resumeDefaultCalmBehavior", null, 0, false);
        }
        else 
        {
            pathTo(self, waypoint);
        }
        return SCRIPT_CONTINUE;
    }
    public String pickDestination() throws InterruptedException
    {
        String waypoint = "exit";
        switch (rand(1, 12))
        {
            case 1:
                waypoint = "square";
                break;
            case 2:
                waypoint = "cafe1";
                break;
            case 3:
                waypoint = "cafe2";
                break;
            case 4:
                waypoint = "cafe3";
                break;
            case 5:
                waypoint = "cafe4";
                break;
            case 6:
                waypoint = "hotel";
                break;
            case 7:
                waypoint = "capitol";
                break;
            case 8:
                waypoint = "mission1";
                break;
            case 9:
                waypoint = "mission2";
                break;
            case 10:
                waypoint = "installation";
                break;
            case 11:
                waypoint = "exit";
                break;
            case 12:
                waypoint = "gate1";
                break;
        }
        return waypoint;
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
