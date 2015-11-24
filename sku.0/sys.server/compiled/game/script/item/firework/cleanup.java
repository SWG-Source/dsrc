package script.item.firework;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.firework;

public class cleanup extends script.base_script
{
    public cleanup()
    {
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        messageTo(self, "handleCleanup", null, 60f, false);
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
