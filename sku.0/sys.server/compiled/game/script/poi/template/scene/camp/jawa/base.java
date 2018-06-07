package script.poi.template.scene.camp.jawa;

import script.dictionary;
import script.obj_id;

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
