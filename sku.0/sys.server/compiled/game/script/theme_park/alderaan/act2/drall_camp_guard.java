package script.theme_park.alderaan.act2;

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

public class drall_camp_guard extends script.base_script
{
    public drall_camp_guard()
    {
    }
    public int OnIncapacitated(obj_id self, obj_id attacker) throws InterruptedException
    {
        obj_id camp = getObjIdObjVar(self, "coa2.imperial.drall_camp");
        if (isIdValid(camp))
        {
            messageTo(camp, "guardKilled", null, 0, false);
        }
        return SCRIPT_CONTINUE;
    }
}
