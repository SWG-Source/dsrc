package script.theme_park.poi.general.behavior;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

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
