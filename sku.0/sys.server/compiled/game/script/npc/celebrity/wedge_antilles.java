package script.npc.celebrity;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.ai_lib;

public class wedge_antilles extends script.base_script
{
    public wedge_antilles()
    {
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        ai_lib.setDefaultCalmBehavior(self, ai_lib.BEHAVIOR_SENTINEL);
        debugSpeakMsg(self, "2 METERS?!?");
        attachScript(self, "conversation.rtp_wedge_main");
        return SCRIPT_CONTINUE;
    }
}
