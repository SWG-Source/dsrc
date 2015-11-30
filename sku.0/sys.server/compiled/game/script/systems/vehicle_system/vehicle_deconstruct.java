package script.systems.vehicle_system;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.callable;
import script.library.sui;
import script.library.utils;
import script.library.vehicle;

public class vehicle_deconstruct extends script.base_script
{
    public vehicle_deconstruct()
    {
    }
    public static final String PID_NAME = "vehicleDeconstruct";
    public int OnObjectMenuRequest(obj_id self, obj_id player, menu_info mi) throws InterruptedException
    {
        obj_id dPad = utils.getPlayerDatapad(player);
        obj_id container = getContainedBy(self);
        if (!isIdValid(dPad) || !exists(dPad))
        {
            return SCRIPT_CONTINUE;
        }
        if (!isIdValid(container) || !exists(container))
        {
            return SCRIPT_CONTINUE;
        }
        if (dPad != container)
        {
            return SCRIPT_CONTINUE;
        }
        obj_id vehicleCalled = callable.getCDCallable(self);
        if (isIdValid(vehicleCalled))
        {
            return SCRIPT_CONTINUE;
        }
        mi.addRootMenu(menu_info_types.SERVER_MENU5, vehicle.SID_VEHICLE_TO_SCHEM);
        return SCRIPT_CONTINUE;
    }
    public int OnObjectMenuSelect(obj_id self, obj_id player, int item) throws InterruptedException
    {
        if (item == menu_info_types.SERVER_MENU5)
        {
            obj_id dPad = utils.getPlayerDatapad(player);
            obj_id container = getContainedBy(self);
            if (!isIdValid(dPad) || !exists(dPad))
            {
                return SCRIPT_CONTINUE;
            }
            if (!isIdValid(container) || !exists(container))
            {
                return SCRIPT_CONTINUE;
            }
            if (dPad != container)
            {
                return SCRIPT_CONTINUE;
            }
            obj_id vehicleCalled = callable.getCDCallable(self);
            if (isIdValid(vehicleCalled))
            {
                return SCRIPT_CONTINUE;
            }
            int pid = sui.inputbox(self, player, "@" + vehicle.SID_CONVERT_PROMPT, sui.OK_CANCEL, "@" + vehicle.SID_CONVERT_TITLE, sui.INPUT_NORMAL, null, "handleConvertSchemSui");
            sui.setPid(player, pid, PID_NAME);
        }
        return SCRIPT_CONTINUE;
    }
    public int handleConvertSchemSui(obj_id self, dictionary params) throws InterruptedException
    {
        if (params == null || params.isEmpty())
        {
            return SCRIPT_CONTINUE;
        }
        obj_id player = sui.getPlayerId(params);
        if (!isIdValid(player) || !exists(player))
        {
            return SCRIPT_CONTINUE;
        }
        int bp = sui.getIntButtonPressed(params);
        if (bp == sui.BP_CANCEL)
        {
            sui.removePid(player, PID_NAME);
            return SCRIPT_CONTINUE;
        }
        int pageId = params.getInt("pageId");
        if (!sui.hasPid(player, PID_NAME))
        {
            forceCloseSUIPage(pageId);
            CustomerServiceLog("vehicle_conversion", "Player " + getFirstName(player) + "(" + player + ") has somehow gotten two conversion windows while attempting to convert their old vehiclePCD(" + self + ") to a new schematic. Bailing out now");
            return SCRIPT_CONTINUE;
        }
        int pid = sui.getPid(player, PID_NAME);
        if (pageId != pid)
        {
            forceCloseSUIPage(pageId);
            forceCloseSUIPage(pid);
            sui.removePid(player, PID_NAME);
            CustomerServiceLog("vehicle_conversion", "Player " + getFirstName(player) + "(" + player + ") has somehow gotten two conversion windows while attempting to convert their old vehiclePCD(" + self + ") to a new schematic. Bailing out now");
            return SCRIPT_CONTINUE;
        }
        obj_id vehicleCalled = callable.getCDCallable(self);
        if (isIdValid(vehicleCalled))
        {
            forceCloseSUIPage(pid);
            sui.removePid(player, PID_NAME);
            sendSystemMessage(player, vehicle.SID_CONVERT_VEHICLE_OUT);
            CustomerServiceLog("vehicle_conversion", "Player " + getFirstName(player) + "(" + player + ") has tried to deconstruct their old vehiclePCD(" + self + ") while still having a vehicle(" + vehicleCalled + ") called. Bailing out now");
            return SCRIPT_CONTINUE;
        }
        obj_id dPad = utils.getPlayerDatapad(player);
        obj_id container = getContainedBy(self);
        if (!isIdValid(dPad) || !exists(dPad))
        {
            forceCloseSUIPage(pid);
            sui.removePid(player, PID_NAME);
            return SCRIPT_CONTINUE;
        }
        if (!isIdValid(container) || !exists(container))
        {
            forceCloseSUIPage(pid);
            sui.removePid(player, PID_NAME);
            return SCRIPT_CONTINUE;
        }
        if (dPad != container)
        {
            forceCloseSUIPage(pid);
            sui.removePid(player, PID_NAME);
            sendSystemMessage(player, vehicle.SID_CONVERT_VCD_MOVED);
            CustomerServiceLog("vehicle_conversion", "Player " + getFirstName(player) + "(" + player + ") traded their old vehicle PCD(" + self + ") to object(" + container + ") while attempting to convert it into a new schematic. They are prolly trying to exploit, bailing out now");
            return SCRIPT_CONTINUE;
        }
        String response = sui.getInputBoxText(params);
        if (!(response.toLowerCase()).equals("deconstruct"))
        {
            forceCloseSUIPage(pid);
            sui.removePid(player, PID_NAME);
            sendSystemMessage(player, vehicle.SID_CONVERT_INVALID_RESPONSE);
            CustomerServiceLog("vehicle_conversion", "Player " + getFirstName(player) + "(" + player + ") has entered '" + response + "' instead of 'deconstruct' so the vehicle will not be deconstructed.");
            return SCRIPT_CONTINUE;
        }
        if (vehicle.turnVehicleIntoSchem(player, self))
        {
            CustomerServiceLog("vehicle_conversion", "Player " + getFirstName(player) + "(" + player + ") has decided to convert their old vehicle(" + self + ") to a new cored schematic.");
            sui.removePid(player, PID_NAME);
            sendSystemMessage(player, vehicle.SID_CONVERT_CONVERT_SUCCESS);
            destroyObject(self);
            return SCRIPT_CONTINUE;
        }
        else 
        {
            sendSystemMessage(player, vehicle.SID_CONVERT_CONVERT_FAIL);
            CustomerServiceLog("vehicle_conversion", "Player " + getFirstName(player) + "(" + player + ") attempted to convert their old vehicle(" + self + ") to a new schematic, and it failed.");
            sui.removePid(player, PID_NAME);
            return SCRIPT_CONTINUE;
        }
    }
}
