package script.theme_park.dungeon.trando_slave_camp;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

public class warden_tosk extends script.base_script
{
    public warden_tosk()
    {
    }
    public int OnIncapacitated(obj_id self, obj_id killer) throws InterruptedException
    {
        obj_id bunker = getTopMostContainer(self);
        if (hasObjVar(self, "parent"))
        {
            bunker = getObjIdObjVar(self, "parent");
        }
        setObjVar(bunker, "toskKilled", 1);
        return SCRIPT_CONTINUE;
    }
}
