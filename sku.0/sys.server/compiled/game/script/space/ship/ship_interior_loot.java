package script.space.ship;

import script.dictionary;
import script.library.space_transition;
import script.obj_id;

public class ship_interior_loot extends script.base_script
{
    public ship_interior_loot()
    {
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        messageTo(self, "setupShip", null, 1, false);
        LOG("space", self + " ATTACH !!ATTACH !!ATTACH !!ATTACH !!");
        return SCRIPT_CONTINUE;
    }
    public int setupShip(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id ship = space_transition.getContainingShip(self);
        LOG("space", "Attaching ");
        if (isIdValid(ship))
        {
            if (!isObjectPersisted(self))
            {
                if (hasObjVar(ship, "objLootBox"))
                {
                    LOG("space", ship + " hasObjVar");
                    destroyObject(self);
                }
                else 
                {
                    LOG("space", ship + " no objvar");
                    setObjVar(ship, "objLootBox", self);
                    persistObject(self);
                }
            }
            else 
            {
                LOG("space", ship + " hasObjVar - resetting");
                setObjVar(ship, "objLootBox", self);
            }
        }
        else 
        {
            LOG("space", "No ship found for " + self);
        }
        return SCRIPT_CONTINUE;
    }
}
