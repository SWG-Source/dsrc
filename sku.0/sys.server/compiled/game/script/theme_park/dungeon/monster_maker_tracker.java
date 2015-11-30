package script.theme_park.dungeon;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

public class monster_maker_tracker extends script.base_script
{
    public monster_maker_tracker()
    {
    }
    public int OnDestroy(obj_id self) throws InterruptedException
    {
        if (hasObjVar(self, "parent"))
        {
            messageTo(getObjIdObjVar(self, "parent"), "removeMonsterBlock", null, 120, false);
        }
        return SCRIPT_CONTINUE;
    }
}
