package script.planet_map;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.planetary_map;

public class map_loc extends script.planet_map.map_loc_base
{
    public map_loc()
    {
    }
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        String template = getTemplateName(self);
        if ((template != null) || (!template.equals("")))
        {
            if (template.indexOf("object/building/player/city/garden_") != -1)
            {
                detachScript(self, "planet_map.map_loc_both");
                return SCRIPT_CONTINUE;
            }
        }
        if (!planetary_map.addMapLocation(self))
        {
            location here = getLocation(self);
            debugServerConsoleMsg(self, "WARNING: unable to add self as planetary map location!");
            debugServerConsoleMsg(self, "template = " + template + " here = " + here.toString());
            LOG("planetaryMapLocation", "trigger OnInitialize()");
            LOG("planetaryMapLocation", "WARNING: unable to add self (" + self + ") as planetary map location!");
            LOG("planetaryMapLocation", "template = " + template);
            LOG("planetaryMapLocation", "here = " + here.toString());
        }
        return SCRIPT_CONTINUE;
    }
}
