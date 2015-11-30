package script.space.characters;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.ai_lib;
import script.library.utils;

public class greeter_moenia_dancer extends script.base_script
{
    public greeter_moenia_dancer()
    {
    }
    public int stopDancing(obj_id self, dictionary params) throws InterruptedException
    {
        utils.removeScriptVar(self, "isDancing");
        ai_lib.setMood(self, "calm");
        return SCRIPT_CONTINUE;
    }
}
