package script.poi.dungeon;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

public class dungeon_mob extends script.base_script
{
    public dungeon_mob()
    {
    }
    public int OnDeath(obj_id self, obj_id killer, obj_id corpseId) throws InterruptedException
    {
        obj_id objParent = getObjIdObjVar(self, "objParent");
        messageTo(objParent, "elementDestroyed", null, 0, true);
        return SCRIPT_CONTINUE;
    }
}
