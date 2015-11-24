package script.systems.skills.stealth;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.utils;
import script.library.stealth;
import script.library.prose;

public class chaffflare extends script.base_script
{
    public chaffflare()
    {
    }
    public static final java.text.NumberFormat floatFormat = new java.text.DecimalFormat("###.##");
    public int OnAttach(obj_id self) throws InterruptedException
    {
        setCount(self, 5);
        return SCRIPT_CONTINUE;
    }
    public int OnGetAttributes(obj_id self, obj_id player, String[] names, String[] attribs) throws InterruptedException
    {
        int free = getFirstFreeIndex(names);
        if (free == -1)
        {
            return SCRIPT_CONTINUE;
        }
        names[free] = "effectiveness";
        attribs[free++] = "" + floatFormat.format((float)getIntObjVar(self, stealth.DETECT_EFFECTIVENESS) / 10) + "%";
        float flareDistance = stealth.CHAFF_FLARE_DISTANCE;
        names[free] = "trap_radius";
        attribs[free++] = "" + flareDistance;
        utils.addClassRequirementAttributes(player, self, names, attribs, free, "");
        return SCRIPT_CONTINUE;
    }
    public int OnObjectMenuRequest(obj_id self, obj_id player, menu_info mi) throws InterruptedException
    {
        if (!utils.isNestedWithin(self, player))
        {
            return SCRIPT_OVERRIDE;
        }
        if (!utils.testItemClassRequirements(player, self, true, ""))
        {
            return SCRIPT_OVERRIDE;
        }
        if (!utils.testItemLevelRequirements(player, self, true, ""))
        {
            return SCRIPT_OVERRIDE;
        }
        menu_info_data mid2 = mi.getMenuItemByType(menu_info_types.ITEM_USE);
        if (mid2 != null)
        {
            mid2.setServerNotify(true);
        }
        return SCRIPT_CONTINUE;
    }
    public int OnObjectMenuSelect(obj_id self, obj_id player, int item) throws InterruptedException
    {
        if (item == menu_info_types.ITEM_USE)
        {
            if (!utils.isNestedWithin(self, player))
            {
                return SCRIPT_OVERRIDE;
            }
            if (!utils.testItemClassRequirements(player, self, false, ""))
            {
                return SCRIPT_OVERRIDE;
            }
            if (!utils.testItemLevelRequirements(player, self, false, ""))
            {
                return SCRIPT_OVERRIDE;
            }
            stealth.doChaffFlareEffect(player, self);
            int count = getCount(self);
            count--;
            if (count < 1)
            {
                destroyObject(self);
            }
            else 
            {
                setCount(self, count);
            }
        }
        return SCRIPT_CONTINUE;
    }
}
