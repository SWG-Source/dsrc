package script.theme_park.heroic.star_destroyer;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.ai_lib;
import script.library.trial;
import script.library.utils;

public class south_squad_leader extends script.base_script
{
    public south_squad_leader()
    {
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        messageTo(self, "handleClanup", null, 30.0f, false);
        return SCRIPT_CONTINUE;
    }
    public int handleClanup(obj_id self, dictionary params) throws InterruptedException
    {
        if (ai_lib.isInCombat(self))
        {
            messageTo(self, "handleClanup", null, 10.0f, false);
            return SCRIPT_CONTINUE;
        }
        trial.cleanupObject(self);
        return SCRIPT_CONTINUE;
    }
}
