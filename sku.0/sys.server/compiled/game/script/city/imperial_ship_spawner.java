package script.city;

import script.location;
import script.obj_id;

public class imperial_ship_spawner extends script.base_script
{
    public imperial_ship_spawner()
    {
    }
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        location here = getLocation(self);
        obj_id npc = createObject("object/static/structure/general/distant_ship_controller_imperial.iff", here);
        return SCRIPT_CONTINUE;
    }
}
