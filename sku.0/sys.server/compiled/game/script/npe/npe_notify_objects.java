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
