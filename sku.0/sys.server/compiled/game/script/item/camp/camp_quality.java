package script.item.camp;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

public class camp_quality extends script.item.camp.camp_base
{
    public camp_quality()
    {
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        setObjVar(self, "campPower", 4);
        setObjVar(self, "skillReq", 40);
        return SCRIPT_CONTINUE;
    }
}
