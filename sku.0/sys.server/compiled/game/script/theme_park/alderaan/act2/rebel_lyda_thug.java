package script.theme_park.alderaan.act2;

import script.obj_id;

public class rebel_lyda_thug extends script.base_script
{
    public rebel_lyda_thug()
    {
    }
    public int OnIncapacitated(obj_id self, obj_id attacker) throws InterruptedException
    {
        obj_id lyda = getObjIdObjVar(self, "coa2.rebel.lyda");
        if (isIdValid(lyda))
        {
            messageTo(lyda, "thugKilled", null, 0, false);
        }
        return SCRIPT_CONTINUE;
    }
}
