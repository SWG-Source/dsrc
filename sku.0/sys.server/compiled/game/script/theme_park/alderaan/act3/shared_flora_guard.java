package script.theme_park.alderaan.act3;

import script.obj_id;

public class shared_flora_guard extends script.base_script
{
    public shared_flora_guard()
    {
    }
    public int OnIncapacitated(obj_id self, obj_id attacker) throws InterruptedException
    {
        obj_id warehouse = getObjIdObjVar(self, "coa3.shared.warehouse");
        if (isIdValid(warehouse))
        {
            messageTo(warehouse, "guardKilled", null, 0, false);
        }
        return SCRIPT_CONTINUE;
    }
}
