package script.theme_park.heroic.axkva_min;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.instance;

public class axkva_door_enter extends script.base_script
{
    public axkva_door_enter()
    {
    }
    public int OnObjectMenuRequest(obj_id self, obj_id player, menu_info item) throws InterruptedException
    {
        item.addRootMenu(menu_info_types.ITEM_USE, new string_id("building_name", "heroic_axkva_enter"));
        return SCRIPT_CONTINUE;
    }
    public int OnObjectMenuSelect(obj_id self, obj_id player, int item) throws InterruptedException
    {
        if (item == menu_info_types.ITEM_USE)
        {
            if (getDistance(player, self) > 6.0f)
            {
                CustomerServiceLog("heroic_axkva_min", "Player successfully used the door but server placed them " + getDistance(player, self) + "m away");
            }
            instance.requestInstanceMovement(player, "heroic_axkva_min");
            return SCRIPT_CONTINUE;
        }
        return SCRIPT_CONTINUE;
    }
}
