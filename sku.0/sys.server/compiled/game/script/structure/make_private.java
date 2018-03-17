package script.structure;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

public class make_private extends script.base_script
{
    public make_private()
    {
    }
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        permissionsMakePrivate(self);
        return SCRIPT_CONTINUE;
    }
}
