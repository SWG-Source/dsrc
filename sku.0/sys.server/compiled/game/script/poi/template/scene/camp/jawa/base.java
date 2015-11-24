package script.poi.template.scene.camp.jawa;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

public class base extends script.poi.template.scene.base.base_theater
{
    public base()
    {
    }
    public static final String TBL = "datatables/poi/camp/jawa/setup.iff";
    public int handleTheaterSetup(obj_id self, dictionary params) throws InterruptedException
    {
        return SCRIPT_CONTINUE;
    }
}
