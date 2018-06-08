package script.space.content_tools;

import script.obj_id;

public class shipwright_trainer extends script.base_script
{
    public shipwright_trainer()
    {
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        setObjVar(self, "trainer", "trainer_shipwright");
        return SCRIPT_CONTINUE;
    }
}
