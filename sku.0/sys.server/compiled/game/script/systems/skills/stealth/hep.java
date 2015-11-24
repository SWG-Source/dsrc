package script.systems.skills.stealth;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.prose;
import script.library.utils;
import script.library.stealth;

public class hep extends script.base_script
{
    public hep()
    {
    }
    public static final java.text.NumberFormat floatFormat = new java.text.DecimalFormat("###.##");
    public int OnGetAttributes(obj_id self, obj_id player, String[] names, String[] attribs) throws InterruptedException
    {
        int free = getFirstFreeIndex(names);
        if (free == -1)
        {
            return SCRIPT_CONTINUE;
        }
        names[free] = "power";
        attribs[free++] = "" + getCount(self);
        names[free] = "effectiveness";
        attribs[free++] = "" + floatFormat.format(getIntObjVar(self, stealth.HEP_EFFECTIVENESS) / 10) + "%";
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
            return SCRIPT_CONTINUE;
        }
        if (!utils.testItemLevelRequirements(player, self, true, ""))
        {
            return SCRIPT_CONTINUE;
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
            if (!utils.testItemClassRequirements(player, self, true, ""))
            {
                return SCRIPT_CONTINUE;
            }
            if (!utils.testItemLevelRequirements(player, self, true, ""))
            {
                return SCRIPT_CONTINUE;
            }
            queueCommand(player, getStringCrc(toLower("urbanStealth")), self, "", COMMAND_PRIORITY_NORMAL);
        }
        return SCRIPT_CONTINUE;
    }
}
