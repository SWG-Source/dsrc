package script.event.emp_day;

import script.dictionary;
import script.obj_id;

public class factional_guard_self_destruct extends script.base_script
{
    public factional_guard_self_destruct()
    {
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        messageTo(self, "selfDestruct", null, 300, false);
        return SCRIPT_CONTINUE;
    }
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        messageTo(self, "selfDestruct", null, 300, false);
        return SCRIPT_CONTINUE;
    }
    public int selfDestruct(obj_id self, dictionary params) throws InterruptedException
    {
        destroyObject(self);
        return SCRIPT_CONTINUE;
    }
}
