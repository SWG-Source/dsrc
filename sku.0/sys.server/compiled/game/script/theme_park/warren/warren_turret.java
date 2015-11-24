package script.theme_park.warren;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.advanced_turret;
import script.library.ai_lib;
import script.library.attrib;
import script.library.battlefield;
import script.library.combat;
import script.library.factions;
import script.library.list;
import script.library.pclib;
import script.library.player_structure;
import script.library.turret;
import script.library.utils;
import script.library.xp;

public class warren_turret extends script.base_script
{
    public warren_turret()
    {
    }
    public int OnTriggerVolumeEntered(obj_id self, String volumeName, obj_id who) throws InterruptedException
    {
        if (!isIdValid(who))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id bldg = getTopMostContainer(self);
        if (utils.hasScriptVar(bldg, "warren.deactivated"))
        {
            return SCRIPT_CONTINUE;
        }
        return SCRIPT_CONTINUE;
    }
    public int OnTriggerVolumeExited(obj_id self, String volumeName, obj_id who) throws InterruptedException
    {
        obj_id bldg = getTopMostContainer(self);
        if (utils.hasScriptVar(bldg, "warren.deactivated"))
        {
            return SCRIPT_CONTINUE;
        }
        return SCRIPT_CONTINUE;
    }
}
