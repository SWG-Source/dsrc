package script.space.ship;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.space_transition;

public class turret_y8_lower extends script.base_script
{
    public turret_y8_lower()
    {
    }
    public static final string_id SID_PILOT = new string_id("space/space_interaction", "pilot_ship");
    public static final string_id SID_TURRET_LOWER = new string_id("space/space_interaction", "turret_lower");
    public static final string_id SID_TURRET_LOWER_2 = new string_id("space/space_interaction", "turret_lower_2");
    public static final string_id SID_TURRET_LOWER_3 = new string_id("space/space_interaction", "turret_lower_3");
    public int OnObjectMenuRequest(obj_id self, obj_id player, menu_info mi) throws InterruptedException
    {
        mi.addRootMenu(menu_info_types.SERVER_MENU1, SID_TURRET_LOWER);
        mi.addRootMenu(menu_info_types.SERVER_MENU2, SID_TURRET_LOWER_2);
        mi.addRootMenu(menu_info_types.SERVER_MENU3, SID_TURRET_LOWER_3);
        return SCRIPT_CONTINUE;
    }
    public int OnObjectMenuSelect(obj_id self, obj_id player, int item) throws InterruptedException
    {
        if (item == menu_info_types.SERVER_MENU1)
        {
            if (hasTurretWeapon(space_transition.getContainingShip(player), ship_chassis_slot_type.SCST_weapon_1, player))
            {
                if (!equip(player, self, "ship_gunner1_pob"))
                {
                    string_id strSpam = new string_id("space/space_interaction", "turret_occupied");
                    sendSystemMessage(player, strSpam);
                }
            }
        }
        if (item == menu_info_types.SERVER_MENU2)
        {
            if (hasTurretWeapon(space_transition.getContainingShip(player), ship_chassis_slot_type.SCST_weapon_3, player))
            {
                if (!equip(player, self, "ship_gunner3_pob"))
                {
                    string_id strSpam = new string_id("space/space_interaction", "turret_occupied");
                    sendSystemMessage(player, strSpam);
                }
            }
        }
        if (item == menu_info_types.SERVER_MENU3)
        {
            if (hasTurretWeapon(space_transition.getContainingShip(player), ship_chassis_slot_type.SCST_weapon_4, player))
            {
                if (!equip(player, self, "ship_gunner4_pob"))
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
