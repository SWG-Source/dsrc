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

public class rebel_lyda_thug extends script.base_script
{
    public rebel_lyda_thug()
    {
    }
    public int OnIncapacitated(obj_id self, obj_id attacker) throws InterruptedException
    {
        obj_id lyda = getObjIdObjVar(self, "coa2.rebel.lyda");
        if (isIdValid(lyda))
        {
            messageTo(lyda, "thugKilled", null, 0, false);
        }
        return SCRIPT_CONTINUE;
    }
}
