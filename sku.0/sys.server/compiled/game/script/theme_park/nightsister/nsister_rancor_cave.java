package script.theme_park.nightsister;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.ai_lib;
import script.library.combat;
import script.library.utils;
import script.library.create;

public class nsister_rancor_cave extends script.base_script
{
    public nsister_rancor_cave()
    {
    }
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        if (!hasObjVar(self, "spawn_table"))
        {
            setObjVar(self, "spawn_table", "datatables/spawning/theme_park/dathomir_nsister_rancor_cave.iff");
        }
        if (!hasScript(self, "theme_park.dungeon.generic_spawner"))
        {
            attachScript(self, "theme_park.dungeon.generic_spawner");
        }
        return SCRIPT_CONTINUE;
    }
}
