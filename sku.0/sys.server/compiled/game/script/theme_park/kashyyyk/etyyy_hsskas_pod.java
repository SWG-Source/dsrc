package script.theme_park.kashyyyk;

import script.obj_id;

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
