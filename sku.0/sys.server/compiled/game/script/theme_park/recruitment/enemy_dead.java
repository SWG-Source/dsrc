package script.theme_park.recruitment;

import script.obj_id;

public class enemy_dead extends script.base_script
{
    public enemy_dead()
    {
    }
    public int OnIncapacitated(obj_id self, obj_id killer) throws InterruptedException
    {
        obj_id player = getObjIdObjVar(self, "killer");
        messageTo(player, "backToHutt", null, 0, true);
        return SCRIPT_CONTINUE;
    }
}
