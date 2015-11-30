package script.theme_park.nightsister;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.ai_lib;
import script.ai.ai_combat;
import script.library.utils;
import script.library.attrib;

public class attack_script_rancor_cave extends script.base_script
{
    public attack_script_rancor_cave()
    {
    }
    public int OnAboutToReceiveItem(obj_id self, obj_id destinationCell, obj_id transferrer, obj_id item) throws InterruptedException
    {
        if (isPlayer(item))
        {
            obj_id rancor = getObjIdObjVar(self, "rancor");
            obj_id slave = getObjIdObjVar(self, "slave");
            startCombat(rancor, slave);
            return SCRIPT_CONTINUE;
        }
        return SCRIPT_CONTINUE;
    }
}
