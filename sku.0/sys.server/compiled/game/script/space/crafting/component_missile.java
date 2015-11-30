package script.space.crafting;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.space_crafting;
import script.library.space_utils;
import script.library.utils;

public class component_missile extends script.base_script
{
    public component_missile()
    {
    }
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        return SCRIPT_CONTINUE;
    }
    public int OnGetAttributes(obj_id self, obj_id player, String[] names, String[] attribs) throws InterruptedException
    {
        int idx = utils.getValidAttributeIndex(names);
        if (idx == -1)
        {
            return SCRIPT_CONTINUE;
        }
        if (hasObjVar(self, "fltMinDamage"))
        {
            float value = getFloatObjVar(self, "fltMinDamage");
            names[idx] = "damage_min";
            attribs[idx] = Float.toString(value);
            idx++;
        }
        if (hasObjVar(self, "fltMaxDamage"))
        {
            float value = getFloatObjVar(self, "fltMaxDamage");
            names[idx] = "damage_max";
            attribs[idx] = Float.toString(value);
            idx++;
        }
        if (hasObjVar(self, "fltRefireRate"))
        {
            float value = getFloatObjVar(self, "fltRefireRate");
            names[idx] = "refire_rate";
            attribs[idx] = Float.toString(value);
            idx++;
        }
        if (hasObjVar(self, "fltShieldEffectiveness"))
        {
            float value = getFloatObjVar(self, "fltShieldEffectiveness");
            names[idx] = "effective_shields";
            attribs[idx] = Float.toString(value);
            idx++;
        }
        if (hasObjVar(self, "fltArmorEffectiveness"))
        {
            float value = getFloatObjVar(self, "fltArmorEffectiveness");
            names[idx] = "effective_armor";
            attribs[idx] = Float.toString(value);
            idx++;
        }
        if (getCount(self) > 0)
        {
            int ammo = getCount(self);
            names[idx] = "ammo";
            attribs[idx] = Integer.toString(ammo);
            idx++;
        }
        return SCRIPT_CONTINUE;
    }
}
