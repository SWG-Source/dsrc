package script.event.invasion;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.ai_lib;
import script.library.create;
import script.library.utils;
import script.ai.ai_combat;

public class invader extends script.base_script
{
    public invader()
    {
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        messageTo(self, "startAttack", null, 1, false);
        messageTo(self, "cleanUp", null, 7200, false);
        return SCRIPT_CONTINUE;
    }
    public int OnHearSpeech(obj_id self, obj_id objSpeaker, String strText) throws InterruptedException
    {
        obj_id coordinator = getObjIdObjVar(self, "event.invasion.coordinator");
        obj_id target = getObjIdObjVar(self, "event.invasion.target");
        if (objSpeaker == coordinator && strText.equals("abortEventNow"))
        {
            messageTo(target, "cleanUp", null, 1, false);
            messageTo(self, "cleanUp", null, 1, false);
        }
        return SCRIPT_CONTINUE;
    }
    public int startAttack(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id target = getObjIdObjVar(self, "event.invasion.target");
        obj_id coordinator = getObjIdObjVar(self, "event.invasion.coordinator");
        float destinationOffset = getFloatObjVar(target, "event.invasion.spawning.destinationOffset");
        location locationDestination = getLocation(target);
        location adjustedLoc = utils.getRandomLocationInRing(locationDestination, destinationOffset, destinationOffset + 10);
        ai_lib.aiPathTo(self, adjustedLoc);
        setMovementRun(self);
        return SCRIPT_CONTINUE;
    }
    public int cleanUp(obj_id self, dictionary params) throws InterruptedException
    {
        destroyObject(self);
        return SCRIPT_CONTINUE;
    }
}
