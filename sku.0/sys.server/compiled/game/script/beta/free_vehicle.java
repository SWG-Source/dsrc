package script.beta;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.callable;
import script.library.utils;

public class free_vehicle extends script.base_script
{
    public free_vehicle()
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
    public int OnDestroy(obj_id self) throws InterruptedException
    {
        obj_id currentPet = callable.getCDCallable(self);
        messageTo(currentPet, "handlePackRequest", null, 1, false);
        if (hasObjVar(self, "hasOwner"))
        {
            obj_id player = getObjIdObjVar(self, "hasOwner");
            if (hasObjVar(player, "hasTestVehicle"))
            {
                removeObjVar(player, "hasTestVehicle");
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int expireAndCleanup(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id currentPet = callable.getCDCallable(self);
        if (isIdValid(currentPet))
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
