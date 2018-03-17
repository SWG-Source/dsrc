package script.npc.celebrity;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.ai_lib;
import script.library.hue;
import script.library.colors;
import script.library.chat;
import script.library.skill;

public class darth_vader extends script.base_script
{
    public darth_vader()
    {
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        ai_lib.setDefaultCalmBehavior(self, ai_lib.BEHAVIOR_SENTINEL);
        ai_lib.setDefaultCalmMood(self, "npc_imperial");
        attachScript(self, "conversation.itp_vader_main");
        return SCRIPT_CONTINUE;
    }
}
