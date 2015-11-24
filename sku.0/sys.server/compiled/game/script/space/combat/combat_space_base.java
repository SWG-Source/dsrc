package script.space.combat;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.utils;
import script.library.space_utils;
import script.library.space_crafting;
import script.library.space_combat;

public class combat_space_base extends script.base_script
{
    public combat_space_base()
    {
    }
    public int finishDocking(obj_id self, dictionary params) throws InterruptedException
    {
        if (!utils.hasScriptVar(self, "docking"))
        {
            return SCRIPT_CONTINUE;
        }
        utils.removeScriptVar(self, "docking");
        return SCRIPT_CONTINUE;
    }
}
