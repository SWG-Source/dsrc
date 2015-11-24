package script.theme_park.nym;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.create;

public class frog_test extends script.base_script
{
    public frog_test()
    {
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        obj_id room = getCellId(self, "sleepingquarters");
        location laser = new location(55.67f, -7, -3.3f, "lok", room);
        obj_id laserBlaster = create.staticObject("object/static/structure/general/prp_engine.iff", laser);
        return SCRIPT_CONTINUE;
    }
}
