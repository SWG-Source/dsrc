package script.item.camp;

import script.obj_id;

public class camp_improved extends script.item.camp.camp_base
{
    public camp_improved()
    {
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        setObjVar(self, "campPower", 3);
        setObjVar(self, "skillReq", 30);
        return SCRIPT_CONTINUE;
    }
}
