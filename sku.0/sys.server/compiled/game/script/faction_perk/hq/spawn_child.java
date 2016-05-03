package script.faction_perk.hq;

import script.dictionary;
import script.library.hq;
import script.obj_id;

public class spawn_child extends script.base_script
{
    public spawn_child()
    {
    }
    public int OnIncapacitated(obj_id self, obj_id killer) throws InterruptedException
    {
        if (hasObjVar(self, hq.VAR_SPAWN_LEADER))
        {
            obj_id parent = getObjIdObjVar(self, hq.VAR_SPAWN_PARENT);
            if (!isIdValid(parent))
            {
                return SCRIPT_CONTINUE;
            }
            dictionary d = new dictionary();
            d.put("oid", self);
            messageTo(parent, "handleLeaderIncapacitated", d, 1f, false);
        }
        return SCRIPT_CONTINUE;
    }
    public int OnDestroy(obj_id self) throws InterruptedException
    {
        obj_id parent = getObjIdObjVar(self, hq.VAR_SPAWN_PARENT);
        if (!isIdValid(parent))
        {
            return SCRIPT_CONTINUE;
        }
        dictionary d = new dictionary();
        d.put("oid", self);
        messageTo(parent, "handleChildDestroyed", d, 1f, false);
        return SCRIPT_CONTINUE;
    }
}
