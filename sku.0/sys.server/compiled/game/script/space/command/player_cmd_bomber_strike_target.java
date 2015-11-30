package script.space.command;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.space_utils;
import script.library.ship_ai;
import script.library.space_combat;
import script.library.utils;

public class player_cmd_bomber_strike_target extends script.base_script
{
    public player_cmd_bomber_strike_target()
    {
    }
    public int OnDestroy(obj_id self) throws InterruptedException
    {
        debugServerConsoleMsg(null, "ONdESTROY - tie bomber");
        dictionary outparams = new dictionary();
        outparams.put("deadTargetId", self);
        if (hasObjVar(self, "targetedByPlayerObjId"))
        {
            space_utils.notifyObject(getObjIdObjVar(self, "targetedByPlayerObjId"), "bomberStrikeTargetDestroyed", outparams);
        }
        return SCRIPT_CONTINUE;
    }
}
