package script.space.crafting;

import script.library.space_transition;
import script.library.utils;
import script.location;
import script.obj_id;

import java.util.Vector;

public class alarm extends script.base_script
{
    public alarm()
    {
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        LOG("space", "ONATTACH GOING OFF ON INTERIOR COMPONETNES");
        setInvulnerable(self, true);
        obj_id objShip = space_transition.getContainingShip(self);
        LOG("space", "I AM INSIDE " + objShip);
        Vector objAlarms = new Vector();
        objAlarms.setSize(0);
        if (utils.hasScriptVar(objShip, "objAlarms"))
        {
            objAlarms = utils.getResizeableObjIdArrayScriptVar(objShip, "objAlarms");
        }
        objAlarms = utils.addElement(objAlarms, self);
        utils.setScriptVar(objShip, "objAlarms", objAlarms);
        location locTest = getLocation(self);
        if (hasObjVar(objShip, "intAlarmsOn"))
        {
            setInvulnerableHitpoints(self, 1);
        }
        return SCRIPT_CONTINUE;
    }
}
