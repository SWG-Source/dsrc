package script.theme_park.dungeon.corvette;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.ai_lib;

public class prisoner extends script.base_script
{
    public prisoner()
    {
    }
    public int escapeNow(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id top = getTopMostContainer(self);
        obj_id elevator = getCellId(top, "elevator57");
        location here = getLocation(self);
        String planet = here.area;
        location escape = new location(-18.09f, 0, 117.72f, planet, elevator);
        ai_lib.aiPathTo(self, escape);
        addLocationTarget("done", escape, 1);
        messageTo(self, "handleBadLeaving", null, 60, false);
        return SCRIPT_CONTINUE;
    }
    public int handleBadLeaving(obj_id self, dictionary params) throws InterruptedException
    {
        messageTo(self, "leave", null, 7, false);
        return SCRIPT_CONTINUE;
    }
    public int leave(obj_id self, dictionary params) throws InterruptedException
    {
        if (ai_lib.isInCombat(self))
        {
            messageTo(self, "leave", null, 60, false);
            return SCRIPT_CONTINUE;
        }
        destroyObject(self);
        return SCRIPT_CONTINUE;
    }
    public int OnArrivedAtLocation(obj_id self, String name) throws InterruptedException
    {
        if (name.equals("done"))
        {
            messageTo(self, "leave", null, 2, false);
        }
        return SCRIPT_CONTINUE;
    }
}
