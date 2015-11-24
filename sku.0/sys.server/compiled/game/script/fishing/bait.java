package script.fishing;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

public class bait extends script.base_script
{
    public bait()
    {
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        setCount(self, rand(2, 5));
        return SCRIPT_CONTINUE;
    }
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        detachScript(self, "fishing.bait");
        return SCRIPT_CONTINUE;
    }
}
