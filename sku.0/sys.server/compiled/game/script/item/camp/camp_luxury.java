package script.item.camp;

import script.obj_id;

public class camp_luxury extends script.item.camp.camp_base
{
    public camp_luxury()
    {
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        setObjVar(self, "campPower", 6);
        setObjVar(self, "skillReq", 80);
        return SCRIPT_CONTINUE;
    }
}
