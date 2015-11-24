package script.npc.celebrity;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.ai_lib;

public class leia extends script.base_script
{
    public leia()
    {
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        ai_lib.setDefaultCalmBehavior(self, ai_lib.BEHAVIOR_SENTINEL);
        debugSpeakMsg(self, "Aren't you a little short for a stormtrooper?");
        messageTo(self, "handleAttachConvoScript", null, 1, false);
        return SCRIPT_CONTINUE;
    }
    public int handleAttachConvoScript(obj_id self, dictionary params) throws InterruptedException
    {
        if ((getCreatureName(self)).equals("leia_organa"))
        {
            attachScript(self, "conversation.rtp_leia_main");
        }
        return SCRIPT_CONTINUE;
    }
}
