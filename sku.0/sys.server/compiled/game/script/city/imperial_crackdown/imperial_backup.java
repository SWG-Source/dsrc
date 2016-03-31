package script.city.imperial_crackdown;

import script.dictionary;
import script.library.ai_lib;
import script.location;
import script.obj_id;

public class imperial_backup extends script.base_script
{
    public imperial_backup()
    {
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        messageTo(self, "goingToFight", null, 1, false);
        int x = rand(300, 600);
        messageTo(self, "cleanUp", null, x, false);
        return SCRIPT_CONTINUE;
    }
    public int OnArrivedAtLocation(obj_id self, String name) throws InterruptedException
    {
        if (name.equals("fight"))
        {
            startCombat(self, getObjIdObjVar(self, "whoToFight"));
        }
        if (name.equals("done"))
        {
            messageTo(self, "leaveCantina", null, 7, false);
        }
        return SCRIPT_CONTINUE;
    }
    public int goingToFight(obj_id self, dictionary params) throws InterruptedException
    {
        location goFight = getLocationObjVar(self, "whereToFight");
        goFight.x = goFight.x + (rand(-5, 5));
        ai_lib.aiPathTo(self, goFight);
        addLocationTarget("fight", goFight, 1);
        return SCRIPT_CONTINUE;
    }
    public int cleanUp(obj_id self, dictionary params) throws InterruptedException
    {
        location impLoc = new location(47.02f, .1f, -2.93f, getLocation(self).area, getCellId(getTopMostContainer(self), "foyer1"));
        ai_lib.aiPathTo(self, impLoc);
        addLocationTarget("done", impLoc, 1);
        messageTo(self, "handleBadLeaving", null, 60, false);
        return SCRIPT_CONTINUE;
    }
    public int leaveCantina(obj_id self, dictionary params) throws InterruptedException
    {
        destroyObject(self);
        return SCRIPT_CONTINUE;
    }
    public int handleBadLeaving(obj_id self, dictionary params) throws InterruptedException
    {
        messageTo(self, "leaveCantina", null, 7, false);
        return SCRIPT_CONTINUE;
    }
}
