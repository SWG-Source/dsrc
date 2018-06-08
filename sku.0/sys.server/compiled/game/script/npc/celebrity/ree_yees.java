package script.npc.celebrity;

import script.obj_id;

public class ree_yees extends script.base_script
{
    public ree_yees()
    {
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        attachScript(self, "conversation.ree_yees");
        return SCRIPT_CONTINUE;
    }
}
