package script.e3demo;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.anims;
import script.library.factions;

public class e3_hangar_worker_1 extends script.base_script
{
    public e3_hangar_worker_1()
    {
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        messageTo(self, "doLoopingEmote", null, 5, false);
        setAnimationMood(self, "npc_imperial");
        factions.setFaction(self, "Imperial");
        return SCRIPT_CONTINUE;
    }
    public int doLoopingEmote(obj_id self, dictionary params) throws InterruptedException
    {
        float fltDelay = rand(2, 5);
        String strEmote = "manipulate_medium";
        doAnimationAction(self, strEmote);
        messageTo(self, "doLoopingEmote", null, fltDelay, false);
        return SCRIPT_CONTINUE;
    }
}
