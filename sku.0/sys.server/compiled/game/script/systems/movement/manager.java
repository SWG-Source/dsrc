package script.systems.movement;

import script.obj_id;

public class manager extends script.base_script
{
    public manager()
    {
    }
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        return SCRIPT_CONTINUE;
    }
    public int OnAttribModDone(obj_id self, String buffName, boolean isDead) throws InterruptedException
    {
        return SCRIPT_CONTINUE;
    }
    public int OnTransferred(obj_id self, obj_id src, obj_id dest, obj_id transferer) throws InterruptedException
    {
        return SCRIPT_CONTINUE;
    }
}
