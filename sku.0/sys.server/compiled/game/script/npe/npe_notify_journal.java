package script.npe;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.utils;
import script.library.npe;

public class npe_notify_journal extends script.base_script
{
    public npe_notify_journal()
    {
    }
    public int OnReceivedItem(obj_id self, obj_id srcContainer, obj_id transferer, obj_id item) throws InterruptedException
    {
        if (isPlayer(item))
        {
            attachScript(item, "npe.trigger_journal");
        }
        return SCRIPT_CONTINUE;
    }
}
