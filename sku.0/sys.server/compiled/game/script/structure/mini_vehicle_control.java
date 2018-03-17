package script.structure;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.player_structure;
import script.library.utils;

public class mini_vehicle_control extends script.base_script
{
    public mini_vehicle_control()
    {
    }
    public static final String VEHICLE_ID = "mini_vehicle.vehicle";
    public int OnAttach(obj_id self) throws InterruptedException
    {
        messageTo(self, "checkVehicleId", null, 3, false);
        return SCRIPT_CONTINUE;
    }
    public int OnReceivedItem(obj_id self, obj_id srcContainer, obj_id transferer, obj_id item) throws InterruptedException
    {
        if (isPlayer(item))
        {
            messageTo(self, "handlerMiniVehicleReceived", null, 6, false);
        }
        return SCRIPT_CONTINUE;
    }
    public int OnLostItem(obj_id self, obj_id srcContainer, obj_id transferer, obj_id item) throws InterruptedException
    {
        if (isPlayer(item))
        {
            messageTo(self, "handlerMiniVehicleLost", null, 6, false);
        }
        return SCRIPT_CONTINUE;
    }
    public int checkVehicleId(obj_id self, dictionary params) throws InterruptedException
    {
        if (!isValidId(getObjIdObjVar(self, VEHICLE_ID)))
        {
            detachScript(self, "structure.mini_vehicle_control");
        }
        return SCRIPT_CONTINUE;
    }
    public int handlerMiniVehicleReceived(obj_id self, dictionary params) throws InterruptedException
    {
        if (!isValidId(self) && !exists(self))
        {
            return SCRIPT_CONTINUE;
        }
        if (!hasObjVar(self, VEHICLE_ID))
        {
            detachScript(self, "structure.mini_vehicle_control");
        }
        obj_id vehicle = getObjIdObjVar(self, VEHICLE_ID);
        if (!isValidId(vehicle) && !exists(vehicle))
        {
            return SCRIPT_CONTINUE;
        }
        if (utils.hasScriptVar(vehicle, "vehicle.moving") || utils.hasScriptVar(vehicle, "vehicle.settingPatrol"))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id[] players = player_structure.getPlayersInBuilding(self);
        if (players == null || players.length == 0)
        {
            return SCRIPT_CONTINUE;
        }
        if (players.length >= 1)
        {
            return SCRIPT_CONTINUE;
        }
        broadcastMessage("handleActivateVehicle", new dictionary());
        return SCRIPT_CONTINUE;
    }
    public int handlerMiniVehicleLost(obj_id self, dictionary params) throws InterruptedException
    {
        if (!isValidId(self) && !exists(self))
        {
            return SCRIPT_CONTINUE;
        }
        if (!hasObjVar(self, VEHICLE_ID))
        {
            detachScript(self, "structure.mini_vehicle_control");
        }
        obj_id vehicle = getObjIdObjVar(self, VEHICLE_ID);
        if (!isValidId(vehicle) && !exists(vehicle))
        {
            return SCRIPT_CONTINUE;
        }
        if (utils.hasScriptVar(vehicle, "vehicle.settingPatrol"))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id[] players = player_structure.getPlayersInBuilding(self);
        if (players == null || players.length == 0)
        {
            broadcastMessage("handleDeactivateVehicle", new dictionary());
        }
        return SCRIPT_CONTINUE;
    }
}
