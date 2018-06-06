package script.item.camp;

import script.obj_id;

public class camp_multi extends script.item.camp.camp_base
{
    public camp_multi()
    {
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        setObjVar(self, "campPower", 2);
        setObjVar(self, "skillReq", 20);
        return SCRIPT_CONTINUE;
    }
}
