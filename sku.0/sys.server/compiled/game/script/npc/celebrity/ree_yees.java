package script.npc.celebrity;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

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
