package script.theme_park.tatooine.jabbaspawner;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

public class bomarrmonk2 extends script.base_script
{
    public bomarrmonk2()
    {
    }
    public int OnDestroy(obj_id self) throws InterruptedException
    {
        obj_id palace = getObjIdObjVar(self, "palace");
        messageTo(palace, "bomarrMonk2Died", null, 20, true);
        return SCRIPT_CONTINUE;
    }
}
