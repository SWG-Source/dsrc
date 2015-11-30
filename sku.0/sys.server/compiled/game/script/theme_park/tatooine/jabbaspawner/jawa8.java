package script.theme_park.tatooine.jabbaspawner;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

public class jawa8 extends script.base_script
{
    public jawa8()
    {
    }
    public int OnDestroy(obj_id self) throws InterruptedException
    {
        obj_id palace = getObjIdObjVar(self, "palace");
        messageTo(palace, "jawa8Died", null, 20, true);
        return SCRIPT_CONTINUE;
    }
    public int doFacing(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id faceTarget = getObjIdObjVar(self, "facer");
        faceTo(self, faceTarget);
        return SCRIPT_CONTINUE;
    }
}
