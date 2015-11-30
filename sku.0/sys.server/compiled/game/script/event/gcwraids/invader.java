package script.event.gcwraids;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.ai_lib;
import script.library.utils;
import script.ai.ai_combat;
import script.library.healing;

public class invader extends script.base_script
{
    public invader()
    {
    }
    public static final String DATATABLE = "datatables/event/invasion/ewok_bonus_loot.iff";
    public int OnAttach(obj_id self) throws InterruptedException
    {
        messageTo(self, "startAttack", null, 1, false);
        messageTo(self, "goDie", null, 3600, false);
        return SCRIPT_CONTINUE;
    }
    public int startAttack(obj_id self, dictionary params) throws InterruptedException
    {
        location target = getLocationObjVar(self, "auto_invasion.target");
        float destinationOffset = getFloatObjVar(self, "auto_invasion.dest_offset");
        location adjustedLoc = utils.getRandomLocationInRing(target, 0, destinationOffset);
        ai_lib.aiPathTo(self, adjustedLoc);
        setMovementRun(self);
        return SCRIPT_CONTINUE;
    }
    public int goDie(obj_id self, dictionary params) throws InterruptedException
    {
        if (!ai_lib.isAiDead(self))
        {
            destroyObject(self);
        }
        return SCRIPT_CONTINUE;
    }
    public int OnIncapacitated(obj_id self, obj_id killer) throws InterruptedException
    {
        int myNumber = getIntObjVar(self, "auto_invasion.my_number");
        dictionary params = new dictionary();
        params.put("myNumber", myNumber);
        params.put("deadGuy", self);
        obj_id mom = getObjIdObjVar(self, "auto_invasion.mom");
        removeObjVar(mom, "auto_invasion.spawn" + myNumber);
        messageTo(mom, "invaderDied", params, 1, false);
        return SCRIPT_CONTINUE;
    }
}
