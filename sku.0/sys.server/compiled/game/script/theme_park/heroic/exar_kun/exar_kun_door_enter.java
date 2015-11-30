package script.theme_park.heroic.exar_kun;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.instance;

public class exar_kun_door_enter extends script.base_script
{
    public exar_kun_door_enter()
    {
    }
    public int OnObjectMenuRequest(obj_id self, obj_id player, menu_info item) throws InterruptedException
    {
        item.addRootMenu(menu_info_types.ITEM_USE, new string_id("building_name", "heroic_exar_enter"));
        return SCRIPT_CONTINUE;
    }
    public int OnObjectMenuSelect(obj_id self, obj_id player, int item) throws InterruptedException
    {
        if (item == menu_info_types.ITEM_USE)
        {
            if (getDistance(player, self) > 6.0f)
            {
                CustomerServiceLog("instance-heroic_exar_kun", "Player successfully used the door but server placed them " + getDistance(player, self) + "m away");
            }
            if (instance.isFlaggedForInstance(player, "heroic_exar_kun"))
            {
                instance.requestInstanceMovement(player, "heroic_exar_kun");
            }
            else 
            {
                String chance = "" + rand(1, 5);
                if (rand(0, 999) == 999)
                {
                    chance = "rare";
                }
                sendSystemMessage(player, new string_id("building_name", "heroic_exar_unflagged_entrance_" + chance));
            }
            return SCRIPT_CONTINUE;
        }
        return SCRIPT_CONTINUE;
    }
}
