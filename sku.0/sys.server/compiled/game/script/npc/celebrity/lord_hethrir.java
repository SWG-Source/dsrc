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

public class lord_hethrir extends script.base_script
{
    public lord_hethrir()
    {
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        setName(self, "Lord Hethrir");
        ai_lib.setDefaultCalmBehavior(self, ai_lib.BEHAVIOR_SENTINEL);
        attachScript(self, "conversation.itp_hethrir_main");
        return SCRIPT_CONTINUE;
    }
}
