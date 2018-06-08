package script.theme_park.dungeon.geonosian_madbio_bunker;

import script.obj_id;

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
