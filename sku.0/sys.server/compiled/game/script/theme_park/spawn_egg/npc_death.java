package script.theme_park.spawn_egg;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

public class npc_death extends script.base_script
{
    public npc_death()
    {
    }
    public int OnDestroy(obj_id self) throws InterruptedException
    {
        obj_id myBox = getObjIdObjVar(self, "theme_park.spawn_egg");
        messageTo(myBox, "NPCDestroyed", null, 0, true);
        return SCRIPT_CONTINUE;
    }
}
