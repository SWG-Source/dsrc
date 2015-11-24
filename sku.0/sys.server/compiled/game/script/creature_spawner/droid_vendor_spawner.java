package script.creature_spawner;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.locations;
import script.library.create;
import script.library.ai_lib;

public class droid_vendor_spawner extends script.base_script
{
    public droid_vendor_spawner()
    {
    }
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        if (!hasObjVar(self, "started_spawning"))
        {
            spawnVendor(self);
            setObjVar(self, "started_spawning", 1);
        }
        return SCRIPT_CONTINUE;
    }
    public void spawnVendor(obj_id self) throws InterruptedException
    {
        String creatureToSpawn = "farmer";
        location goodLoc = getLocation(self);
        obj_id spawned = create.object(creatureToSpawn, goodLoc);
        create.addDestroyMessage(spawned, "creatureDied", 10.0f, self);
        attachScript(spawned, "beta.droid_vendor");
        setObjVar(self, "myVendor", spawned);
    }
    public int OnDestroy(obj_id self) throws InterruptedException
    {
        obj_id vendor = getObjIdObjVar(self, "myVendor");
        if (isIdValid(vendor))
        {
            destroyObject(vendor);
        }
        return SCRIPT_CONTINUE;
    }
    public int creatureDied(obj_id self, dictionary params) throws InterruptedException
    {
        spawnVendor(self);
        return SCRIPT_CONTINUE;
    }
}
