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

public class nightsister_guardian extends script.base_script
{
    public nightsister_guardian()
    {
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        setName(self, "Nightsister Guardian");
        ai_lib.setDefaultCalmBehavior(self, ai_lib.BEHAVIOR_SENTINEL);
        return SCRIPT_CONTINUE;
    }
}
