package script.npe;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.utils;
import script.library.space_utils;
import script.library.npe;

public class npe_notify_elevator extends script.base_script
{
    public npe_notify_elevator()
    {
    }
    public int OnReceivedItem(obj_id self, obj_id srcContainer, obj_id transferer, obj_id item) throws InterruptedException
    {
        if (isPlayer(item))
        {
            if (!utils.hasScriptVar(item, "npe.pop_elevator"))
            {
                obj_id building = getTopMostContainer(item);
                obj_id droid = utils.getObjIdScriptVar(building, "objDroidInvis");
                string_id popElev = new string_id("npe", "pop_elevator");
                npe.commTutorialPlayer(droid, item, 5, popElev, "sound/c3po_80b.snd", "object/mobile/c_3po.iff");
                utils.setScriptVar(item, "npe.pop_elevator", 1);
            }
        }
        return SCRIPT_CONTINUE;
    }
}
