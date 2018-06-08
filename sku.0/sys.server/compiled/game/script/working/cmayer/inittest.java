package script.working.cmayer;

import script.obj_id;

public class inittest extends script.base_script
{
    public inittest()
    {
    }
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        debugServerConsoleMsg(self, ("I am initialized"));
        return SCRIPT_CONTINUE;
    }
}
