package script.npc.static_npc;

import script.dictionary;
import script.library.ai_lib;
import script.obj_id;

public class npc_dead extends script.base_script
{
    public npc_dead()
    {
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        messageTo(self, "makeNpcDead", null, 10.0f, false);
        return SCRIPT_CONTINUE;
    }
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        messageTo(self, "makeNpcDead", null, 10.0f, false);
        return SCRIPT_CONTINUE;
    }
    public int makeNpcDead(obj_id self, dictionary params) throws InterruptedException
    {
        ai_lib.setMood(self, "npc_dead_01");
        setInvulnerable(self, true);
        return SCRIPT_CONTINUE;
    }
}
