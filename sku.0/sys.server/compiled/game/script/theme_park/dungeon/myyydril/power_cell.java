package script.theme_park.dungeon.myyydril;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

public class power_cell extends script.base_script
{
    public power_cell()
    {
    }
    public int OnDestroy(obj_id self) throws InterruptedException
    {
        obj_id contentManager = getObjIdObjVar(self, "grievous_encounter.contentManager");
        float time = getFloatObjVar(contentManager, "grievous_encounter.powerSpawn");
        messageTo(contentManager, "handlePowerCellDestroyed", null, time, true);
        return SCRIPT_CONTINUE;
    }
}
