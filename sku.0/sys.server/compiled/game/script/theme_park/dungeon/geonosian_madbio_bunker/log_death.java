package script.theme_park.dungeon.geonosian_madbio_bunker;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

public class log_death extends script.base_script
{
    public log_death()
    {
    }
    public int OnIncapacitated(obj_id self, obj_id killer) throws InterruptedException
    {
        String name = getName(self);
        if (name.equals("Acklay"))
        {
            CustomerServiceLog("DUNGEON_GeonosianBunker", "*Geonosian Creature Killed: " + killer + ": " + getName(killer) + " has killed the Acklay.");
        }
        else 
        {
            CustomerServiceLog("DUNGEON_GeonosianBunker", "*Geonosian Creature Killed: " + killer + ": " + getName(killer) + " has killed the Fire Breathing Spider.");
        }
        return SCRIPT_CONTINUE;
    }
}
