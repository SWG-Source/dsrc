package script.theme_park.dungeon.death_watch_bunker;

import script.obj_id;

public class locked_dungeon extends script.base_script
{
    public locked_dungeon()
    {
    }
    public static final String MSGS = "dungeon/geonosian_madbio";
    public int OnAttach(obj_id self) throws InterruptedException
    {
        return SCRIPT_CONTINUE;
    }
}
