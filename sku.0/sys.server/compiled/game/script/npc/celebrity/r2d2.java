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
import script.library.factions;
import script.library.skill;

public class r2d2 extends script.base_script
{
    public r2d2()
    {
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        setName(self, "R2 D2");
        ai_lib.setDefaultCalmBehavior(self, ai_lib.BEHAVIOR_SENTINEL);
        return SCRIPT_CONTINUE;
    }
}
