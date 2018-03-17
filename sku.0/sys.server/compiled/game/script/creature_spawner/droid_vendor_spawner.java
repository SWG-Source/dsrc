package script.creature_spawner;

import script.dictionary;
import script.library.create;
import script.obj_id;

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
        obj_id spawned = create.object("farmer", getLocation(self));
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
