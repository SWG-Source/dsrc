package script.item.loot_kits.simple_kit_scripts;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.utils;

public class datadisk_repair_kit extends script.base_script
{
    public datadisk_repair_kit()
    {
    }
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        messageTo(self, "setUp", null, 2, false);
        return SCRIPT_CONTINUE;
    }
    public int setUp(obj_id self, dictionary params) throws InterruptedException
    {
        String[] needs = new String[5];
        needs[0] = "object/tangible/loot/misc/datadisk_corrupt.iff";
        needs[1] = "object/tangible/loot/simple_kit/recovery_software.iff";
        needs[2] = "object/tangible/loot/simple_kit/magnetic_burner.iff";
        needs[3] = "object/tangible/loot/simple_kit/magnetic_reader.iff";
        needs[4] = "object/tangible/loot/simple_kit/wiring.iff";
        setObjVar(self, "needs", needs);
        setObjVar(self, "overview", needs);
        setObjVar(self, "reward", "object/tangible/loot/simple_kit/datadisk.iff");
        return SCRIPT_CONTINUE;
    }
}
