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

public class turret_ladder extends script.base_script
{
    public turret_ladder()
    {
    }
    public static final string_id SID_PILOT = new string_id("space/space_interaction", "pilot_ship");
    public static final string_id SID_TURRET_ENTER = new string_id("space/space_interaction", "turret_enter");
    public int OnObjectMenuRequest(obj_id self, obj_id player, menu_info mi) throws InterruptedException
    {
        obj_id objShip = space_transition.getContainingShip(player);
        if (!hasObjVar(self, "turret_slot"))
        {
            return SCRIPT_CONTINUE;
        }
        if (space_utils.playerCanControlShipSlot(objShip, player, true))
        {
            mi.addRootMenu(menu_info_types.ITEM_USE, SID_TURRET_ENTER);
        }
        return SCRIPT_CONTINUE;
    }
    public int OnObjectMenuSelect(obj_id self, obj_id player, int item) throws InterruptedException
    {
        if (item == menu_info_types.ITEM_USE)
        {
            int slot = getIntObjVar(self, "turret_slot");
            if (slot < 0 || slot > 7)
            {
                return SCRIPT_CONTINUE;
            }
            String slotName = "ship_gunner" + slot + "_pob";
            int intSlot = ship_chassis_slot_type.SCST_weapon_0;
            switch (slot)
            {
                case 0:
                intSlot = ship_chassis_slot_type.SCST_weapon_0;
                break;
                case 1:
                intSlot = ship_chassis_slot_type.SCST_weapon_1;
                break;
                case 2:
                intSlot = ship_chassis_slot_type.SCST_weapon_2;
                break;
                case 3:
                intSlot = ship_chassis_slot_type.SCST_weapon_3;
                break;
                case 4:
                intSlot = ship_chassis_slot_type.SCST_weapon_4;
                break;
                case 5:
                intSlot = ship_chassis_slot_type.SCST_weapon_5;
                break;
                case 6:
                intSlot = ship_chassis_slot_type.SCST_weapon_6;
                break;
                case 7:
                intSlot = ship_chassis_slot_type.SCST_weapon_7;
                break;
                default:
                intSlot = ship_chassis_slot_type.SCST_weapon_0;
                break;
            }
            obj_id objShip = space_transition.getContainingShip(player);
            if (space_utils.playerCanControlShipSlot(objShip, player, false))
            {
                if (hasTurretWeapon(objShip, intSlot, player))
                {
                    if (!equip(player, self, slotName))
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
