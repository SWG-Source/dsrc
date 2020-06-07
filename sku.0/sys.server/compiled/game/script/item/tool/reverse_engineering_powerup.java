package script.item.tool;

import script.library.reverse_engineering;
import script.library.utils;
import script.menu_info;
import script.menu_info_types;
import script.obj_id;
import script.string_id;

public class reverse_engineering_powerup extends script.base_script
{
    public reverse_engineering_powerup()
    {
    }
    public int OnObjectMenuRequest(obj_id self, obj_id player, menu_info mi) throws InterruptedException
    {
        if (!utils.isNestedWithin(self, player))
        {
            return SCRIPT_CONTINUE;
        }
        string_id strSpam = new string_id("spam", "powerup_use");
        mi.addRootMenuOrServerNotify(menu_info_types.ITEM_USE, strSpam);
        return SCRIPT_CONTINUE;
    }
    public int OnObjectMenuSelect(obj_id self, obj_id player, int item) throws InterruptedException
    {
        if (!utils.isNestedWithin(self, player))
        {
            return SCRIPT_CONTINUE;
        }
        if (item == menu_info_types.SERVER_MENU9)
        {
            reverse_engineering.applyPowerupItem(player, self, reverse_engineering.getPowerupType(self));
        }
        if (item == menu_info_types.ITEM_USE)
        {
            reverse_engineering.applyPowerupItem(player, self, reverse_engineering.getPowerupType(self));
        }
        return SCRIPT_CONTINUE;
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
            if (hasObjVar(self, reverse_engineering.ENGINEERING_POWER) && hasObjVar(self, reverse_engineering.ENGINEERING_MODIFIER) && hasObjVar(self, reverse_engineering.ENGINEERING_RATIO))
            {
                int power = getIntObjVar(self, reverse_engineering.ENGINEERING_POWER);
                String mod = getStringObjVar(self, reverse_engineering.ENGINEERING_MODIFIER);
                int ratio = getIntObjVar(self, reverse_engineering.ENGINEERING_RATIO);
                names[i] = "@stat_n:" + mod;
                attribs[i] = Integer.toString(power / ratio);
            }
        }
        return SCRIPT_CONTINUE;
    }
}
