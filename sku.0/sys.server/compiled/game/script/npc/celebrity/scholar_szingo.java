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

public class scholar_szingo extends script.base_script
{
    public scholar_szingo()
    {
    }
    public static final String CONVO = "celebrity/scholar_szingo";
    public int OnAttach(obj_id self) throws InterruptedException
    {
        ai_lib.setDefaultCalmBehavior(self, ai_lib.BEHAVIOR_SENTINEL);
        detachScript(self, "npc.converse.npc_converse_menu");
        setInvulnerable(self, true);
        return SCRIPT_CONTINUE;
    }
}
