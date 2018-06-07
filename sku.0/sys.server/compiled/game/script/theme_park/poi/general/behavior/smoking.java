package script.theme_park.poi.general.behavior;

import script.location;
import script.obj_id;

public class smoking extends script.base_script
{
    public smoking()
    {
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        location here = getLocation(self);
        playClientEffectLoc(self, "clienteffect/lair_hvy_damage_fire.cef", here, 0);
        return SCRIPT_CONTINUE;
    }
}
