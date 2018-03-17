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

public class huff_guard extends script.base_script
{
    public huff_guard()
    {
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        setInvulnerable(self, true);
        setName(self, "Huff Darklighter's Guard");
        ai_lib.setDefaultCalmBehavior(self, ai_lib.BEHAVIOR_SENTINEL);
        attachScript(self, "npc.static_quest.quest_convo");
        setObjVar(self, "quest_table", "huff_guard");
        return SCRIPT_CONTINUE;
    }
}
