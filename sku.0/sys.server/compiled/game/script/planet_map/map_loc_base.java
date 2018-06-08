package script.planet_map;

import script.library.planetary_map;
import script.obj_id;

public class map_loc_base extends script.base_script
{
    public map_loc_base()
    {
    }
    public int OnDestroy(obj_id self) throws InterruptedException
    {
        planetary_map.removeMapLocation(self);
        return SCRIPT_CONTINUE;
    }
    public int OnObjectDisabled(obj_id self, obj_id killer) throws InterruptedException
    {
        planetary_map.removeMapLocation(self);
        return SCRIPT_CONTINUE;
    }
}
