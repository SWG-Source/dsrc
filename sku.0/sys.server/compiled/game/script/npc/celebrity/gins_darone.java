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

public class gins_darone extends script.base_script
{
    public gins_darone()
    {
    }
    public static final String CONVO = "celebrity/general_otto";
    public int OnAttach(obj_id self) throws InterruptedException
    {
        setName(self, "Sgt. Gins Darone");
        ai_lib.setDefaultCalmBehavior(self, ai_lib.BEHAVIOR_SENTINEL);
        return SCRIPT_CONTINUE;
    }
}
