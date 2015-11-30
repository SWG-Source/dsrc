package script.space.quest_logic;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.space_create;
import script.library.space_quest;
import script.library.space_utils;
import script.library.space_crafting;
import script.library.space_transition;
import script.library.utils;
import script.library.ship_ai;
import script.library.prose;

public class piracy_ship extends script.base_script
{
    public piracy_ship()
    {
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        messageTo(self, "statusCheck", null, 5.f, false);
        return SCRIPT_CONTINUE;
    }
    public int OnSpaceUnitMoveToComplete(obj_id self) throws InterruptedException
    {
        obj_id beacon = getObjIdObjVar(self, "beacon");
        if (!isIdValid(beacon) || !exists(beacon))
        {
            return SCRIPT_CONTINUE;
        }
        messageTo(beacon, "startMovement", null, 1.f, false);
        return SCRIPT_CONTINUE;
    }
    public int statusCheck(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id beacon = getObjIdObjVar(self, "beacon");
        if (!isIdValid(beacon) || !exists(beacon))
        {
            messageTo(self, "hyperLeave", null, 20.f, false);
        }
        obj_id targetShip = getObjIdObjVar(beacon, "targetShip");
        if (!isIdValid(targetShip) || !exists(targetShip))
        {
            messageTo(self, "hyperLeave", null, 20.f, false);
        }
        messageTo(self, "statusCheck", null, 30.f, false);
        return SCRIPT_CONTINUE;
    }
    public int hyperLeave(obj_id self, dictionary params) throws InterruptedException
    {
        destroyObjectHyperspace(self);
        return SCRIPT_CONTINUE;
    }
}
