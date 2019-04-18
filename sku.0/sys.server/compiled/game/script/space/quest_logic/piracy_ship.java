package script.space.quest_logic;

import script.dictionary;
import script.obj_id;

public class piracy_ship extends script.base_script
{
    public piracy_ship()
    {
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        messageTo(self, "statusCheck", null, 5.0f, false);
        return SCRIPT_CONTINUE;
    }
    public int OnSpaceUnitMoveToComplete(obj_id self) throws InterruptedException
    {
        obj_id beacon = getObjIdObjVar(self, "beacon");
        if (!isIdValid(beacon) || !exists(beacon))
        {
            return SCRIPT_CONTINUE;
        }
        messageTo(beacon, "startMovement", null, 1.0f, false);
        return SCRIPT_CONTINUE;
    }
    public int statusCheck(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id beacon = getObjIdObjVar(self, "beacon");
        if (!isIdValid(beacon) || !exists(beacon))
        {
            messageTo(self, "hyperLeave", null, 20.0f, false);
        }
        obj_id targetShip = getObjIdObjVar(beacon, "targetShip");
        if (!isIdValid(targetShip) || !exists(targetShip))
        {
            messageTo(self, "hyperLeave", null, 20.0f, false);
        }
        messageTo(self, "statusCheck", null, 30.0f, false);
        return SCRIPT_CONTINUE;
    }
    public int hyperLeave(obj_id self, dictionary params) throws InterruptedException
    {
        destroyObjectHyperspace(self);
        return SCRIPT_CONTINUE;
    }
}
