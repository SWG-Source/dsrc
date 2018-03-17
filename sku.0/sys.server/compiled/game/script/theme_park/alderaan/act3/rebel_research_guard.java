package script.theme_park.alderaan.act3;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.ai_lib;
import script.library.create;
import script.library.utils;
import script.library.chat;

public class rebel_research_guard extends script.base_script
{
    public rebel_research_guard()
    {
    }
    public int OnIncapacitated(obj_id self, obj_id attacker) throws InterruptedException
    {
        obj_id facility = getObjIdObjVar(self, "coa3.rebel.facility");
        if (isIdValid(facility))
        {
            messageTo(facility, "guardKilled", null, 0, false);
        }
        return SCRIPT_CONTINUE;
    }
}
