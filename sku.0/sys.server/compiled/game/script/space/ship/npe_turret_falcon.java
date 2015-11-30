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
import script.library.utils;

public class npe_turret_falcon extends script.base_script
{
    public npe_turret_falcon()
    {
    }
    public static final string_id SID_PILOT = new string_id("space/space_interaction", "pilot_ship");
    public static final string_id SID_TURRET_UPPER = new string_id("space/space_interaction", "turret_upper");
    public static final string_id SID_TURRET_LOWER = new string_id("space/space_interaction", "turret_lower");
    public int OnObjectMenuRequest(obj_id self, obj_id player, menu_info mi) throws InterruptedException
    {
        obj_id objShip = space_transition.getContainingShip(player);
        if (utils.hasScriptVar(player, "npe.readyforturret"))
        {
            mi.addRootMenu(menu_info_types.ITEM_USE, SID_TURRET_UPPER);
        }
        sendDirtyObjectMenuNotification(self);
        return SCRIPT_CONTINUE;
    }
    public int OnObjectMenuSelect(obj_id self, obj_id player, int item) throws InterruptedException
    {
        if (item == menu_info_types.ITEM_USE)
        {
            obj_id objShip = space_transition.getContainingShip(player);
            if (!utils.hasScriptVar(player, "npe.readyforturret"))
            {
                string_id message = new string_id("npe_millennium_falcon", "ladder_disabled");
                sendSystemMessage(player, message);
                return SCRIPT_CONTINUE;
            }
            if (hasTurretWeapon(objShip, ship_chassis_slot_type.SCST_weapon_0, player))
            {
                if (!equip(player, self, "ship_gunner0_pob"))
                {
                    string_id strSpam = new string_id("space/space_interaction", "turret_occupied");
                    sendSystemMessage(player, strSpam);
                }
                else 
                {
                    if (!utils.hasScriptVar(player, "firstTurret"))
                    {
                        obj_id building = getTopMostContainer(self);
                        messageTo(building, "continueMainTable", null, 1, false);
                        messageTo(building, "spawnShips", null, 0, false);
                        messageTo(building, "phaseTwo", null, 2, false);
                        utils.setScriptVar(player, "firstTurret", "yes");
                        utils.setScriptVar(player, "npe.finishedTurret", false);
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
