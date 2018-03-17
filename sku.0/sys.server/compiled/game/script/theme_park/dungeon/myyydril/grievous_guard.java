package script.theme_park.dungeon.myyydril;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

public class grievous_guard extends script.base_script
{
    public grievous_guard()
    {
    }
    public int OnDeath(obj_id self, obj_id killer, obj_id corpseId) throws InterruptedException
    {
        obj_id contentManager = getObjIdObjVar(self, "grievous_encounter.contentManager");
        messageTo(contentManager, "handleNpcDeath", null, 1, false);
        return SCRIPT_CONTINUE;
    }
}
