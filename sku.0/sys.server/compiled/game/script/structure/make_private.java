package script.structure;

import script.obj_id;

public class make_private extends script.base_script
{
    public make_private()
    {
    }
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        permissionsMakePrivate(self);
        return SCRIPT_CONTINUE;
    }
}
