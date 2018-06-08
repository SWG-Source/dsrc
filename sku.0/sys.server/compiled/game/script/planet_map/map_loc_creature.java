package script.planet_map;

import script.library.planetary_map;
import script.obj_id;

public class map_loc_creature extends script.planet_map.map_loc_base
{
    public map_loc_creature()
    {
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        planetary_map.addCreatureLocation(self);
        return SCRIPT_CONTINUE;
    }
}
