package script.item.component;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

public class reverse_engineer_component extends script.base_script
{
    public reverse_engineer_component()
    {
    }
    public int OnGetAttributes(obj_id self, obj_id player, String[] names, String[] attribs) throws InterruptedException
    {
        if (names == null || attribs == null || names.length != attribs.length)
        {
            return SCRIPT_CONTINUE;
        }
        int i = getFirstFreeIndex(names);
        if (i != -1 && i < names.length)
        {
            if (hasObjVar(self, "reverse_engineering.reverse_engineering_power"))
            {
                int powerModPower = getIntObjVar(self, "reverse_engineering.reverse_engineering_power");
                names[i] = "@crafting:power_bit_power";
                attribs[i] = "" + powerModPower;
                ++i;
            }
            if (hasObjVar(self, "reverse_engineering.reverse_engineering_modifier"))
            {
                String modBitType = getStringObjVar(self, "reverse_engineering.reverse_engineering_modifier");
                names[i] = "@crafting:mod_bit_type";
                attribs[i] = "@stat_n:" + modBitType;
                ++i;
            }
            if (hasObjVar(self, "reverse_engineering.reverse_engineering_ratio"))
            {
                int modBitRatio = getIntObjVar(self, "reverse_engineering.reverse_engineering_ratio");
                names[i] = "@crafting:mod_bit_ratio";
                attribs[i] = "" + modBitRatio;
                ++i;
            }
        }
        return SCRIPT_CONTINUE;
    }
}
