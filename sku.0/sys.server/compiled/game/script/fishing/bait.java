package script.fishing;

import script.obj_id;

public class bait extends script.base_script
{
    public bait()
    {
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        setCount(self, rand(2, 5));
        return SCRIPT_CONTINUE;
    }
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        detachScript(self, "fishing.bait");
        return SCRIPT_CONTINUE;
    }
}
