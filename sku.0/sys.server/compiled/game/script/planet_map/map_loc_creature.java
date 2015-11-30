package script.planet_map;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.utils;
import script.library.planetary_map;

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
