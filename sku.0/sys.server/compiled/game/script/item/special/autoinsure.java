package script.item.special;

import script.dictionary;
import script.obj_id;

public class autoinsure extends script.base_script
{
    public autoinsure()
    {
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        messageTo(self, "handleAutoInsure", null, 1.0f, false);
        return SCRIPT_CONTINUE;
    }
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        messageTo(self, "handleAutoInsure", null, 2.0f, false);
        return SCRIPT_CONTINUE;
    }
    public int handleAutoInsure(obj_id self, dictionary params) throws InterruptedException
    {
        setAutoInsured(self);
        return SCRIPT_CONTINUE;
    }
}
