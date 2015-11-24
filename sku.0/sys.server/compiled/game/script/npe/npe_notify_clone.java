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

public class npe_notify_clone extends script.base_script
{
    public npe_notify_clone()
    {
    }
    public int OnReceivedItem(obj_id self, obj_id srcContainer, obj_id transferer, obj_id item) throws InterruptedException
    {
        if (isPlayer(item))
        {
            if (!utils.hasScriptVar(item, "npe.pop_clone"))
            {
                string_id popClone = new string_id("npe", "pop_clone");
                obj_id building = getTopMostContainer(item);
                obj_id droid = utils.getObjIdScriptVar(building, "objDroidInvis");
                npe.commTutorialPlayer(droid, item, 8, popClone, "sound/c3po_24.snd", "object/mobile/c_3po.iff");
                utils.setScriptVar(item, "npe.pop_clone", 1);
            }
        }
        return SCRIPT_CONTINUE;
    }
}
