package script.item.container.got_specific;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

public class medbag extends script.item.container.got_specific.base
{
    public medbag()
    {
    }
    public static final int MY_TYPE = GOT_misc_pharmaceutical;
    public int OnAttach(obj_id self) throws InterruptedException
    {
        setObjVar(self, VAR_GOT, MY_TYPE);
        return SCRIPT_CONTINUE;
    }
    public int OnDetach(obj_id self) throws InterruptedException
    {
        removeObjVar(self, VAR_GOT);
        return SCRIPT_CONTINUE;
    }
}
