package script.space.command;

import script.dictionary;
import script.library.space_utils;
import script.obj_id;

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
