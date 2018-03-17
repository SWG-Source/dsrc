package script.npc;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.stealth;

public class prof_spy_21_steal extends script.base_script
{
    public prof_spy_21_steal()
    {
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        stealth.addStealableTemplate(self, "object/tangible/quest/prof_spy_21_incom_plans.iff");
        return SCRIPT_CONTINUE;
    }
}
