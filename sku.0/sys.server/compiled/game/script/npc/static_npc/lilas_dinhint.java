package script.npc.static_npc;

import script.library.ai_lib;
import script.obj_id;

public class lilas_dinhint extends script.base_script
{
    public lilas_dinhint()
    {
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        ai_lib.setDefaultCalmBehavior(self, ai_lib.BEHAVIOR_SENTINEL);
        setName(self, "Lilas Dinhint (museum curator)");
        return SCRIPT_CONTINUE;
    }
}
