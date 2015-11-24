package script.theme_park.dungeon.myyydril;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

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
