package script.item.loot_kits.simple_kit_scripts;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.utils;

public class empty_datapad extends script.base_script
{
    public empty_datapad()
    {
    }
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        messageTo(self, "setUp", null, 2, false);
        return SCRIPT_CONTINUE;
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        messageTo(self, "setUp", null, 2, false);
        return SCRIPT_CONTINUE;
    }
    public int setUp(obj_id self, dictionary params) throws InterruptedException
    {
        if (hasObjVar(self, "needs"))
        {
            return SCRIPT_CONTINUE;
        }
        String[] needs = new String[1];
        needs[0] = "object/tangible/loot/simple_kit/datadisk.iff";
        setObjVar(self, "needs", needs);
        setObjVar(self, "reward", "object/tangible/loot/tool/usable_datapad.iff");
        return SCRIPT_CONTINUE;
    }
}
