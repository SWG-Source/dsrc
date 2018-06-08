package script.space.ship;

import script.obj_id;

public class ship_interior_autocleanup extends script.base_script
{
    public ship_interior_autocleanup()
    {
    }
    public int OnRemovingFromWorld(obj_id self) throws InterruptedException
    {
        destroyObject(self);
        return SCRIPT_CONTINUE;
    }
}
