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

public class e3_deadguy extends script.base_script
{
    public e3_deadguy()
    {
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        kill(self);
        factions.setFaction(self, "Rebel");
        return SCRIPT_CONTINUE;
    }
}
