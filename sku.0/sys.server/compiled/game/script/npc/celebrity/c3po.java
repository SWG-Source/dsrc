package script.npc.celebrity;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.ai_lib;

public class c3po extends script.base_script
{
    public c3po()
    {
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        setName(self, "C-3PO");
        ai_lib.setDefaultCalmBehavior(self, ai_lib.BEHAVIOR_SENTINEL);
        attachScript(self, "conversation.rtp_c3po_main");
        return SCRIPT_CONTINUE;
    }
}
