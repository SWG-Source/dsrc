package script.planet_map;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.planetary_map;

public class map_loc_attach extends script.planet_map.map_loc_base
{
    public map_loc_attach()
    {
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        if (!planetary_map.addMapLocation(self))
        {
            String template = getTemplateName(self);
            location here = getLocation(self);
            debugServerConsoleMsg(self, "WARNING: unable to add self as planetary map location!");
            debugServerConsoleMsg(self, "template = " + template + " here = " + here.toString());
            LOG("planetaryMapLocation", "trigger OnAttach()");
            LOG("planetaryMapLocation", "WARNING: unable to add self (" + self + ") as planetary map location!");
            LOG("planetaryMapLocation", "template = " + template);
            LOG("planetaryMapLocation", "here = " + here.toString());
        }
        return SCRIPT_CONTINUE;
    }
    public int OnDetach(obj_id self) throws InterruptedException
    {
        planetary_map.removeMapLocation(self);
        return SCRIPT_CONTINUE;
    }
}
