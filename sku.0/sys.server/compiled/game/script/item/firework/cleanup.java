package script.item.firework;

import script.dictionary;
import script.library.firework;
import script.obj_id;

public class cleanup extends script.base_script
{
    public cleanup()
    {
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        messageTo(self, "handleCleanup", null, 60.0f, false);
        return SCRIPT_CONTINUE;
    }
    public int OnDetach(obj_id self) throws InterruptedException
    {
        destroyObject(self);
        return SCRIPT_CONTINUE;
    }
    public int handleCleanup(obj_id self, dictionary params) throws InterruptedException
    {
        detachScript(self, firework.SCRIPT_FIREWORK_CLEANUP);
        return SCRIPT_CONTINUE;
    }
}
