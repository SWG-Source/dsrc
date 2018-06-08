package script.city;

import script.location;
import script.obj_id;

public class rebel_ship_spawner extends script.base_script
{
    public rebel_ship_spawner()
    {
    }
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        location here = getLocation(self);
        obj_id npc = createObject("object/static/structure/general/distant_ship_controller_rebel.iff", here);
        return SCRIPT_CONTINUE;
    }
}
