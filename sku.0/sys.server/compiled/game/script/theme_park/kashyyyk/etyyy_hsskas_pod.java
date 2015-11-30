package script.theme_park.kashyyyk;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

public class etyyy_hsskas_pod extends script.base_script
{
    public etyyy_hsskas_pod()
    {
    }
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        if (!hasObjVar(self, "spawnedAt"))
        {
            setObjVar(self, "spawnedAt", getGameTime());
        }
        else 
        {
            int spawned = getIntObjVar(self, "spawnedAt");
            int lifespan = getGameTime() - spawned;
            if (lifespan > 600)
            {
                destroyObject(self);
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        if (!hasObjVar(self, "spawnedAt"))
        {
            setObjVar(self, "spawnedAt", getGameTime());
        }
        else 
        {
            int spawned = getIntObjVar(self, "spawnedAt");
            int lifespan = getGameTime() - spawned;
            if (lifespan > 600)
            {
                destroyObject(self);
            }
        }
        return SCRIPT_CONTINUE;
    }
}
