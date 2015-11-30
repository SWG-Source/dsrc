package script.item.loot_kits.simple_kit_scripts;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.utils;

public class dud_firework extends script.base_script
{
    public dud_firework()
    {
    }
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        messageTo(self, "setUp", null, 2, false);
        return SCRIPT_CONTINUE;
    }
    public int setUp(obj_id self, dictionary params) throws InterruptedException
    {
        String[] needs = new String[3];
        needs[0] = "object/tangible/loot/misc/firework_dud_s1.iff";
        needs[1] = "object/tangible/loot/simple_kit/powder_pack.iff";
        needs[2] = "object/tangible/loot/simple_kit/firework_casing.iff";
        setObjVar(self, "needs", needs);
        setObjVar(self, "overview", needs);
        setObjVar(self, "reward", "object/tangible/firework/firework_one.iff");
        return SCRIPT_CONTINUE;
    }
}
