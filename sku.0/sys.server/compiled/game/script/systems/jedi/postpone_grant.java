package script.systems.jedi;

import script.obj_id;

public class postpone_grant extends script.base_script
{
    public postpone_grant()
    {
    }
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        if (!hasObjVar(self, "jedi.postponeGrant"))
        {
            setObjVar(self, "jedi.postponeGrant", 1);
        }
        detachScript(self, "systems.jedi.postpone_grant");
        return SCRIPT_CONTINUE;
    }
}
