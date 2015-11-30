package script.npc.junk_dealer;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.ai_lib;

public class junk_reggi_nyms extends script.base_script
{
    public junk_reggi_nyms()
    {
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        ai_lib.setDefaultCalmBehavior(self, ai_lib.BEHAVIOR_SENTINEL);
        detachScript(self, "conversation.junk_reggi_nym");
        attachScript(self, "npc.converse.junk_dealer");
        return SCRIPT_CONTINUE;
    }
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        ai_lib.setDefaultCalmBehavior(self, ai_lib.BEHAVIOR_SENTINEL);
        detachScript(self, "conversation.junk_reggi_nym");
        attachScript(self, "npc.converse.junk_dealer");
        return SCRIPT_CONTINUE;
    }
}
