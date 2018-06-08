package script.theme_park.dungeon.myyydril;

import script.obj_id;

public class cantina_setup extends script.base_script
{
    public cantina_setup()
    {
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        setObjVar(self, "healing.canhealshock", 1);
        return SCRIPT_CONTINUE;
    }
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        setObjVar(self, "healing.canhealshock", 1);
        return SCRIPT_CONTINUE;
    }
}
