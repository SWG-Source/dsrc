package script.space.ship;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.space_transition;
import script.library.space_utils;

public class turret_falcon extends script.base_script
{
    public turret_falcon()
    {
    }
    public static final string_id SID_PILOT = new string_id("space/space_interaction", "pilot_ship");
    public static final string_id SID_TURRET_UPPER = new string_id("space/space_interaction", "turret_upper");
    public static final string_id SID_TURRET_LOWER = new string_id("space/space_interaction", "turret_lower");
    public int OnObjectMenuRequest(obj_id self, obj_id player, menu_info mi) throws InterruptedException
    {
        obj_id objShip = space_transition.getContainingShip(player);
        if (space_utils.playerCanControlShipSlot(objShip, player, true))
        {
            mi.addRootMenu(menu_info_types.SERVER_MENU1, SID_TURRET_UPPER);
            mi.addRootMenu(menu_info_types.SERVER_MENU2, SID_TURRET_LOWER);
        }
        return SCRIPT_CONTINUE;
    }
    public int OnObjectMenuSelect(obj_id self, obj_id player, int item) throws InterruptedException
    {
        if (item == menu_info_types.SERVER_MENU1)
        {
            obj_id objShip = space_transition.getContainingShip(player);
            if (space_utils.playerCanControlShipSlot(objShip, player, false))
            {
                if (hasTurretWeapon(objShip, ship_chassis_slot_type.SCST_weapon_0, player))
                {
                    if (!equip(player, self, "ship_gunner0_pob"))
                    {
                        string_id strSpam = new string_id("space/space_interaction", "turret_occupied");
                        sendSystemMessage(player, strSpam);
                    }
                }
            }
        }
        else if (item == menu_info_types.SERVER_MENU2)
        {
            obj_id objShip = space_transition.getContainingShip(player);
            if (space_utils.playerCanControlShipSlot(objShip, player, false))
            {
                if (hasTurretWeapon(objShip, ship_chassis_slot_type.SCST_weapon_1, player))
                {
                    if (!equip(player, self, "ship_gunner1_pob"))
                    {
                        string_id strSpam = new string_id("space/space_interaction", "turret_occupied");
                        sendSystemMessage(player, strSpam);
                    }
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
