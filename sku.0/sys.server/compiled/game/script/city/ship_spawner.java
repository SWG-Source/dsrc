package script.city;

import script.location;
import script.obj_id;

public class ship_spawner extends script.base_script
{
    public ship_spawner()
    {
    }
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        location here = getLocation(self);
        obj_id npc = createObject("object/static/structure/general/distant_ship_controller2.iff", here);
        return SCRIPT_CONTINUE;
    }
}
