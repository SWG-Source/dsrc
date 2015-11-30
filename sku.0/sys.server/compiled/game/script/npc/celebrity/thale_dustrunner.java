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
import script.library.poi;

public class thale_dustrunner extends script.base_script
{
    public thale_dustrunner()
    {
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        setName(self, "Thale Dustrunner (Corsec Captain)");
        ai_lib.setDefaultCalmBehavior(self, ai_lib.BEHAVIOR_SENTINEL);
        attachScript(self, "npc.static_quest.quest_convo");
        setObjVar(self, "quest_table", "corsec_captain");
        setInvulnerable(self, true);
        return SCRIPT_CONTINUE;
    }
}
