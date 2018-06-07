package script.working.cmayer;

import script.obj_id;

public class top extends script.base_script
{
    public top()
    {
    }
    public int OnSpeaking(obj_id self, String text) throws InterruptedException
    {
        if (text.equals("test"))
        {
            debugSpeakMsg(self, "TopScript");
        }
        return SCRIPT_CONTINUE;
    }
}
