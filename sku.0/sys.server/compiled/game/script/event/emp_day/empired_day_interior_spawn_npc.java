package script.event.emp_day;

import script.dictionary;
import script.obj_id;

public class empired_day_interior_spawn_npc extends script.base_script
{
    public empired_day_interior_spawn_npc()
    {
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        messageTo(self, "removeConversationScripts", null, 10, false);
        return SCRIPT_CONTINUE;
    }
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        messageTo(self, "removeConversationScripts", null, 10, false);
        return SCRIPT_CONTINUE;
    }
    public int removeConversationScripts(obj_id self, dictionary params) throws InterruptedException
    {
        detachScript(self, "npc.faction_recruiter.recruiter_setup");
        return SCRIPT_CONTINUE;
    }
}
