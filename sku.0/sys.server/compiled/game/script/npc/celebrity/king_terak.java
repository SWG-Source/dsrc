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

public class king_terak extends script.base_script
{
    public king_terak()
    {
    }
    public static final String CONVO = "celebrity/king_terak";
    public int OnAttach(obj_id self) throws InterruptedException
    {
        ai_lib.setDefaultCalmBehavior(self, ai_lib.BEHAVIOR_SENTINEL);
        setObjVar(self, "quest_table", "king_terak");
        attachScript(self, "npc.static_quest.quest_convo");
        return SCRIPT_CONTINUE;
    }
}
