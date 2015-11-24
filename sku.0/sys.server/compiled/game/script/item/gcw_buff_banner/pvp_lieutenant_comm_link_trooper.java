package script.item.gcw_buff_banner;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.trial;

public class pvp_lieutenant_comm_link_trooper extends script.base_script
{
    public pvp_lieutenant_comm_link_trooper()
    {
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        messageTo(self, "despawn", null, 30, false);
        return SCRIPT_CONTINUE;
    }
    public int despawn(obj_id self, dictionary params) throws InterruptedException
    {
        trial.cleanupObject(self);
        return SCRIPT_CONTINUE;
    }
}
