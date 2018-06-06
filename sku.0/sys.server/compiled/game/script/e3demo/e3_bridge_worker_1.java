package script.e3demo;

import script.dictionary;
import script.obj_id;

public class e3_bridge_worker_1 extends script.base_script
{
    public e3_bridge_worker_1()
    {
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        messageTo(self, "doLoopingEmote", null, 5, false);
        setAnimationMood(self, "npc_imperial");
        return SCRIPT_CONTINUE;
    }
    public int doLoopingEmote(obj_id self, dictionary params) throws InterruptedException
    {
        float fltDelay = rand(4, 9);
        String strEmote = "manipulate_medium";
        doAnimationAction(self, strEmote);
        messageTo(self, "doLoopingEmote", null, fltDelay, false);
        return SCRIPT_CONTINUE;
    }
}
