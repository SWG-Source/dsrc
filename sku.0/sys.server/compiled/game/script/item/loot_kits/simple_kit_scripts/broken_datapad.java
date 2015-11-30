package script.item.loot_kits.simple_kit_scripts;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.utils;

public class broken_datapad extends script.base_script
{
    public broken_datapad()
    {
    }
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        messageTo(self, "setUp", null, 2, false);
        return SCRIPT_CONTINUE;
    }
    public int setUp(obj_id self, dictionary params) throws InterruptedException
    {
        String[] needs = new String[4];
        needs[1] = "object/tangible/loot/tool/datapad_broken.iff";
        needs[2] = "object/tangible/loot/simple_kit/datapad_connectors.iff";
        needs[3] = "object/tangible/loot/simple_kit/datapad_backlight.iff";
        needs[0] = "object/tangible/loot/simple_kit/datapad_battery.iff";
        setObjVar(self, "needs", needs);
        setObjVar(self, "overview", needs);
        setObjVar(self, "reward", "object/tangible/loot/simple_kit/empty_datapad.iff");
        return SCRIPT_CONTINUE;
    }
}
