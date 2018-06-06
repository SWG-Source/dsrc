package script.npe;

import script.library.npe;
import script.library.utils;
import script.obj_id;
import script.string_id;

public class npe_notify_objects extends script.base_script
{
    public npe_notify_objects()
    {
    }
    public int OnReceivedItem(obj_id self, obj_id srcContainer, obj_id transferer, obj_id item) throws InterruptedException
    {
        if (isPlayer(item))
        {
            if (!utils.hasScriptVar(item, "npe.pop_objects"))
            {
                obj_id building = getTopMostContainer(item);
                obj_id droid = utils.getObjIdScriptVar(building, "objDroidInvis");
                string_id popObjects = new string_id("npe", "pop_objects");
                npe.commTutorialPlayer(droid, item, 5, popObjects, "sound/vo_c3po_9b.snd", "object/mobile/c_3po.iff");
                utils.setScriptVar(item, "npe.pop_objects", 1);
            }
        }
        return SCRIPT_CONTINUE;
    }
}
