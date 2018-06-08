package script.theme_park.spawn_egg;

import script.obj_id;

public class npc_death extends script.base_script
{
    public npc_death()
    {
    }
    public int OnDestroy(obj_id self) throws InterruptedException
    {
        obj_id myBox = getObjIdObjVar(self, "theme_park.spawn_egg");
        messageTo(myBox, "NPCDestroyed", null, 0, true);
        return SCRIPT_CONTINUE;
    }
}
