package script.theme_park.dathomir.aurilia;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

public class have_a_seat extends script.base_script
{
    public have_a_seat()
    {
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        setAnimationMood(self, "npc_sitting_chair");
        return SCRIPT_CONTINUE;
    }
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        setAnimationMood(self, "npc_sitting_chair");
        return SCRIPT_CONTINUE;
    }
}
