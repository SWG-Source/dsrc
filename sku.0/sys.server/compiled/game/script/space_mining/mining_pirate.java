package script.space_mining;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

public class mining_pirate extends script.base_script
{
    public mining_pirate()
    {
    }
    public int OnDestroy(obj_id self) throws InterruptedException
    {
        obj_id asteroid = getObjIdObjVar(self, "space_mining.parentRoid");
        messageTo(asteroid, "handlePirateKilled", null, 0, false);
        return SCRIPT_CONTINUE;
    }
}
