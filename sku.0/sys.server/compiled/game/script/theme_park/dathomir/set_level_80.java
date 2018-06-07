package script.theme_park.dathomir;

import script.obj_id;

public class set_level_80 extends script.base_script
{
    public set_level_80()
    {
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        setLevel(self, 80);
        return SCRIPT_CONTINUE;
    }
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        setLevel(self, 80);
        return SCRIPT_CONTINUE;
    }
}
