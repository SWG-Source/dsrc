package script.beta;

import script.dictionary;
import script.library.callable;
import script.obj_id;

public class free_mount extends script.base_script
{
    public free_mount()
    {
    }
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        int expirationTime = getIntObjVar(self, "mount_vendor.create");
        if (getGameTime() > expirationTime)
        {
            messageTo(self, "expireAndCleanup", null, 0, false);
        }
        return SCRIPT_CONTINUE;
    }
    public int expireAndCleanup(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id currentPet = callable.getCDCallable(self);
        if (isIdValid(currentPet) && exists(currentPet))
        {
            obj_id riderId = getRiderId(currentPet);
            if (isIdValid(riderId))
            {
                dismountCreature(riderId);
            }
        }
        setObjVar(self, "pet.isDead", true);
        destroyObject(self);
        return SCRIPT_CONTINUE;
    }
}
