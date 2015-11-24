package script.space.ship;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.space_transition;

public class turret_y8_upper extends script.base_script
{
    public turret_y8_upper()
    {
    }
    public static final string_id SID_PILOT = new string_id("space/space_interaction", "pilot_ship");
    public static final string_id SID_TURRET_UPPER = new string_id("space/space_interaction", "turret_upper");
    public int OnObjectMenuRequest(obj_id self, obj_id player, menu_info mi) throws InterruptedException
    {
        mi.addRootMenu(menu_info_types.SERVER_MENU1, SID_TURRET_UPPER);
        return SCRIPT_CONTINUE;
    }
    public int OnObjectMenuSelect(obj_id self, obj_id player, int item) throws InterruptedException
    {
        if (item == menu_info_types.SERVER_MENU1)
        {
            if (hasTurretWeapon(space_transition.getContainingShip(player), ship_chassis_slot_type.SCST_weapon_0, player))
            {
                if (!equip(player, self, "ship_gunner0_pob"))
                {
                    string_id strSpam = new string_id("space/space_interaction", "turret_occupied");
                    sendSystemMessage(player, strSpam);
                }
            }
        }
        return SCRIPT_CONTINUE;
    }
    public boolean hasTurretWeapon(obj_id objShip, int intSlot, obj_id objPlayer) throws InterruptedException
    {
        if (!isShipSlotInstalled(objShip, intSlot))
        {
            string_id strSpam = new string_id("space/space_interaction", "no_turret");
            sendSystemMessage(objPlayer, strSpam);
            return false;
        }
        if (isShipComponentDisabled(objShip, intSlot))
        {
            string_id strSpam = new string_id("space/space_interaction", "turret_disabled");
            sendSystemMessage(objPlayer, strSpam);
            return false;
        }
        return true;
    }
}
