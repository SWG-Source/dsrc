package script.item.camp;

import script.obj_id;

public class camp_elite extends script.item.camp.camp_base
{
    public camp_elite()
    {
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        setObjVar(self, "campPower", 5);
        setObjVar(self, "skillReq", 75);
        return SCRIPT_CONTINUE;
    }
}
