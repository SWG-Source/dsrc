package script.systems.veteran_reward;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.utils;

public class harvester_examine extends script.base_script
{
    public harvester_examine()
    {
    }
    public int OnGetAttributes(obj_id self, obj_id playerId, String[] names, String[] attribs) throws InterruptedException
    {
        int idx = getFirstFreeIndex(names);
        if (idx == -1)
        {
            return SCRIPT_CONTINUE;
        }
        names[idx] = "@veteran_new:harvester_examine_title";
        attribs[idx] = "@veteran_new:harvester_examine_text";
        return SCRIPT_CONTINUE;
    }
}
