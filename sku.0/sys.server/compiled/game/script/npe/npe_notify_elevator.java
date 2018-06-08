package script.npe;

import script.library.npe;
import script.library.utils;
import script.obj_id;
import script.string_id;

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
