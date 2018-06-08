package script.theme_park.nightsister;

import script.obj_id;

public class attack_script_rancor_cave extends script.base_script
{
    public attack_script_rancor_cave()
    {
    }
    public int OnAboutToReceiveItem(obj_id self, obj_id destinationCell, obj_id transferrer, obj_id item) throws InterruptedException
    {
        if (isPlayer(item))
        {
            obj_id rancor = getObjIdObjVar(self, "rancor");
            obj_id slave = getObjIdObjVar(self, "slave");
            startCombat(rancor, slave);
            return SCRIPT_CONTINUE;
        }
        return SCRIPT_CONTINUE;
    }
}
