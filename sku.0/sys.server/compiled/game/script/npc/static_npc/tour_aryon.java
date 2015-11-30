package script.npc.static_npc;

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

public class tour_aryon extends script.base_script
{
    public tour_aryon()
    {
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        messageTo(self, "npcScript_setHerName", null, 5, false);
        ai_lib.setDefaultCalmBehavior(self, ai_lib.BEHAVIOR_SENTINEL);
        return SCRIPT_CONTINUE;
    }
    public int npcScript_setHerName(obj_id self, dictionary params) throws InterruptedException
    {
        setName(self, "");
        setName(self, "Tour Aryon (governor)");
        return SCRIPT_CONTINUE;
    }
}
