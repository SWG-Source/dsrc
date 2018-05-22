package script.working.tfiala;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

public class dismount_test extends script.base_script
{
    public dismount_test()
    {
    }
    public int OnHearSpeech(obj_id self, obj_id objSpeaker, String strText) throws InterruptedException
    {
        if (strText.equals("dismounttest"))
        {
            LOG("mounts", "dismount_test: begin");
            LOG("mounts", "self = " + self);
            obj_id mountId = getMountId(self);
            LOG("mounts", "getMountId() returned " + mountId);
            dismountCreature(self);
            LOG("mounts", "dismount_test: end");
        }
        return SCRIPT_CONTINUE;
    }
}
