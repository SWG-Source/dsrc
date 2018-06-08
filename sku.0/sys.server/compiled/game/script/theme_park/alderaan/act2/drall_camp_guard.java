package script.theme_park.alderaan.act2;

import script.obj_id;

public class drall_camp_guard extends script.base_script
{
    public drall_camp_guard()
    {
    }
    public int OnIncapacitated(obj_id self, obj_id attacker) throws InterruptedException
    {
        obj_id camp = getObjIdObjVar(self, "coa2.imperial.drall_camp");
        if (isIdValid(camp))
        {
            messageTo(camp, "guardKilled", null, 0, false);
        }
        return SCRIPT_CONTINUE;
    }
}
