package script.working.cmayer;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

public class inittest extends script.base_script
{
    public inittest()
    {
    }
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        debugServerConsoleMsg(self, ("I am initialized"));
        return SCRIPT_CONTINUE;
    }
}
