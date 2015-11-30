package script.theme_park.tatooine.sarlacc;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

public class sarlacc_death extends script.base_script
{
    public sarlacc_death()
    {
    }
    public int OnDestroy(obj_id self) throws InterruptedException
    {
        obj_id spawner = getObjIdObjVar(self, "spawner");
        messageTo(spawner, "sarlaccDied", null, 5, true);
        return SCRIPT_CONTINUE;
    }
}
