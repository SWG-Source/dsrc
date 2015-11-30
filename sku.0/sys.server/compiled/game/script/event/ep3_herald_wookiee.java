package script.event;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.locations;
import script.library.ai_lib;
import script.library.utils;

public class ep3_herald_wookiee extends script.base_script
{
    public ep3_herald_wookiee()
    {
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        location here = getLocation(self);
        location someLoc = null;
        int x = 0;
        while (x < 10)
        {
            location loc = utils.getRandomLocationInRing(here, 100, 500);
            someLoc = locations.getGoodLocationAroundLocation(loc, 5f, 5f, 50f, 50f);
            if (someLoc != null)
            {
                break;
            }
            x++;
        }
        setObjVar(self, "event.ep3_trando_herald.wookiee_loc", someLoc);
        messageTo(self, "startRunningAway", null, 3, false);
        return SCRIPT_CONTINUE;
    }
    public int startRunningAway(obj_id self, dictionary params) throws InterruptedException
    {
        location loc = getLocationObjVar(self, "event.ep3_trando_herald.wookiee_loc");
        ai_lib.aiPathTo(self, loc);
        setMovementRun(self);
        messageTo(self, "disappearIntoTheCrowd", null, 20, false);
        return SCRIPT_CONTINUE;
    }
    public int disappearIntoTheCrowd(obj_id self, dictionary params) throws InterruptedException
    {
        destroyObject(self);
        return SCRIPT_CONTINUE;
    }
}
