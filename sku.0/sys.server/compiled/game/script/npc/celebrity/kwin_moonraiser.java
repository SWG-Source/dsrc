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

public class kwin_moonraiser extends script.base_script
{
    public kwin_moonraiser()
    {
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        setName(self, "Kwin Moonraiser");
        ai_lib.setDefaultCalmBehavior(self, ai_lib.BEHAVIOR_SENTINEL);
        attachScript(self, "npc.epic_quest.quest_convo");
        setObjVar(self, "quest_table", "kwin_moonraiser");
        return SCRIPT_CONTINUE;
    }
}
