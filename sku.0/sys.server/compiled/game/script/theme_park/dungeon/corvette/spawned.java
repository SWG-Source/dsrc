package script.theme_park.dungeon.corvette;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

public class spawned extends script.base_script
{
    public spawned()
    {
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        messageTo(self, "cleanUp", null, 300, false);
        return SCRIPT_CONTINUE;
    }
    public int cleanUp(obj_id self, dictionary params) throws InterruptedException
    {
        detachScript(self, "theme_park.dungeon.corvette.spawned");
        return SCRIPT_CONTINUE;
    }
}
