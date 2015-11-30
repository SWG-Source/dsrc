package script.terminal;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.hue;
import script.library.pet_lib;
import script.library.player_structure;
import script.library.prose;
import script.library.space_transition;
import script.library.sui;
import script.library.utils;
import script.library.vehicle;

public class terminal_structure_vehicle_garage extends script.base_script
{
    public terminal_structure_vehicle_garage()
    {
    }
    public static final String PID_NAME = "garage";
    public static final String SCRIPT_VAR_VCD_LIST = "garage.vcds";
    public static final String SCRIPT_VAR_RESTORING_VEHICLE = "garage.restoring";
    public static final String SCRIPT_VAR_DECORATING_VEHICLE = "garage.decorating";
    public static final String TERMINAL_OBJVAR_DISPLAY_LIST = "garage.decoratedVehicles";
    public static final String VEHICLE_OBJVAR_DISPLAYED_VEHICLE = "garage.decoratedVehicle";
    public static final String CUSTOMER_SERVICE_LOG = "garage";
    public static final int MESSAGE_DELAY = 5;
    public static final int MAX_ITERATIONS = 5;
    public static final int MAX_STORED_VEHICLES = 10;
    public static final string_id SID_WHILE_DEAD = new string_id("player_structure", "while_dead");
    public static final string_id SID_HANGAR_COULD_NOT_INTIALIZE = new string_id("player_structure", "hangar_could_not_intialize");
    public static final string_id SID_INTIALIZING_HANGAR = new string_id("player_structure", "intializing_hangar");
    public static final string_id SID_TOO_FAR = new string_id("player_structure", "not_in_same_cell");
    public static final string_id SID_TERMINAL_MANAGEMENT = new string_id("player_structure", "garage_management");
    public static final string_id SID_HANGAR_STORE_VEHICLES = new string_id("player_structure", "garage_store_vehicles");
    public static final string_id SID_HANGAR_RESTORE_VEHICLES = new string_id("player_structure", "garage_restore_vehicles");
    public static final string_id SID_NO_VEHICLES = new string_id("player_structure", "no_vehicles");
    public static final string_id SID_VEHICLE_LIST_STORE_PROMPT = new string_id("player_structure", "garage_vehicle_list_store_prompt");
    public static final string_id SID_VEHICLE_LIST_RESTORE_PROMPT = new string_id("player_structure", "garage_vehicle_list_restore_prompt");
    public static final string_id SID_LIST_TITLE = new string_id("player_structure", "garage_list_title");
    public static final string_id SID_VEHICLE_MOVE_STORE_PROMPT = new string_id("player_structure", "garage_move_store_prompt");
    public static final string_id SID_VEHICLE_MOVE_RESTORE_PROMPT = new string_id("player_structure", "garage_move_restore_prompt");
    public static final string_id SID_VEHICLE_MOVE_TITLE = new string_id("player_structure", "garage_vehicle_move_title");
    public static final string_id SID_VEHICLE_WAS_STORED = new string_id("player_structure", "garage_vehicle_was_stored");
    public static final string_id SID_VEHICLE_WAS_RESTORED = new string_id("player_structure", "garage_vehicle_was_restored");
    public static final string_id SID_TRANSFER_FAILED_GENERIC = new string_id("player_structure", "garage_transfer_failed_generic");
    public static final string_id SID_MAX_VEHICLES_STORED = new string_id("player_structure", "garage_max_vehicles_stored");
    public static final string_id SID_PLACE_VEHICLE_DECORATION = new string_id("player_structure", "garage_place_display_vehicle_title");
    public static final string_id SID_PLACE_VEHICLE_DECORATION_PROMPT = new string_id("player_structure", "garage_place_display_vehicle_prompt");
    public static final string_id SID_PLACE_VEHICLE_DECORATION_SUCCESS = new string_id("player_structure", "garage_place_display_vehicle_success");
    public static final string_id SID_REMOVE_VEHICLE_DECORATION_SUCCESS = new string_id("player_structure", "garage_remove_display_vehicle_success");
    public static final String GARAGE_DISPLAY_VEHICLES_TABLE = "datatables/vehicle/vehicle_template.iff";
    public int OnObjectMenuRequest(obj_id self, obj_id player, menu_info mi) throws InterruptedException
    {
        obj_id structure = self;
        if (isDead(player) || isIncapacitated(player))
        {
            sendSystemMessage(player, SID_WHILE_DEAD);
            return SCRIPT_CONTINUE;
        }
        if (player_structure.isStructureCondemned(self) && player_structure.isOwner(structure, player))
        {
            player_structure.doCondemnedSui(self, player);
            return SCRIPT_OVERRIDE;
        }
        if (player_structure.isOwner(structure, player))
        {
            boolean canStoreVehicles = false;
            boolean canRecallVehicles = false;
            obj_id[] datapadVehicleControlDevices = vehicle.findVehicleControlDevicesForPlayer(player);
            if (datapadVehicleControlDevices != null && datapadVehicleControlDevices.length > 0)
            {
                canStoreVehicles = true;
            }
            obj_id playerHangar = utils.getPlayerHangar(player);
            if (isIdValid(playerHangar) && hasObjVar(player, player_structure.OBJVAR_HANGAR_CREATED))
            {
                obj_id[] hangarVehicleControlDevices = vehicle.findVehicleControlDevicesInHangarSlot(player);
                if (hangarVehicleControlDevices != null && hangarVehicleControlDevices.length > 0)
                {
                    canRecallVehicles = true;
                }
            }
            if (canStoreVehicles || canRecallVehicles)
            {
                int management_root = mi.addRootMenu(menu_info_types.SERVER_TERMINAL_MANAGEMENT, SID_TERMINAL_MANAGEMENT);
                if (canStoreVehicles)
                {
                    mi.addSubMenu(management_root, menu_info_types.SERVER_MENU1, SID_HANGAR_STORE_VEHICLES);
                }
                if (canRecallVehicles)
                {
                    mi.addSubMenu(management_root, menu_info_types.SERVER_MENU2, SID_HANGAR_RESTORE_VEHICLES);
                    mi.addSubMenu(management_root, menu_info_types.SERVER_MENU3, SID_PLACE_VEHICLE_DECORATION);
                }
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int OnObjectMenuSelect(obj_id self, obj_id player, int item) throws InterruptedException
    {
        sendDirtyObjectMenuNotification(self);
        obj_id structure = self;
        obj_id playerHangar = utils.getPlayerHangar(player);
        if (!isIdValid(playerHangar) && !hasObjVar(player, player_structure.OBJVAR_HANGAR_CREATED))
        {
            playerHangar = createObject("object/tangible/datapad/character_hangar_datapad.iff", player, "hangar");
            if (!isIdValid(playerHangar))
            {
                CustomerServiceLog(CUSTOMER_SERVICE_LOG, "Player " + getPlayerName(player) + "(" + player + ") tried to create a hangar in their 'hangar' slot, and failed. Usually this means there is already a hangar in that slot, or for some reason the player is missing that slot.");
                sendSystemMessage(player, SID_HANGAR_COULD_NOT_INTIALIZE);
                return SCRIPT_CONTINUE;
            }
            setObjVar(player, player_structure.OBJVAR_HANGAR_CREATED, playerHangar);
            persistObject(playerHangar);
            sendSystemMessage(player, SID_INTIALIZING_HANGAR);
            dictionary dict = new dictionary();
            dict.put("player", player);
            dict.put("iteration", 1);
            dict.put("storing", true);
            messageTo(self, "handleHangarInitialize", dict, MESSAGE_DELAY, false);
            return SCRIPT_CONTINUE;
        }
        if (!isIdValid(playerHangar) && hasObjVar(player, player_structure.OBJVAR_HANGAR_CREATED))
        {
            obj_id storeHangerId = getObjIdObjVar(player, player_structure.OBJVAR_HANGAR_CREATED);
            CustomerServiceLog(CUSTOMER_SERVICE_LOG, "Player " + getPlayerName(player) + "(" + player + ") returned an invalid id for their hangar slot, but had the objar stating they have a hangar. Hangar objvar Id is " + storeHangerId);
            sendSystemMessage(player, SID_TRANSFER_FAILED_GENERIC);
            return SCRIPT_CONTINUE;
        }
        if (sui.hasPid(player, PID_NAME))
        {
            int pid = sui.getPid(player, PID_NAME);
            forceCloseSUIPage(pid);
        }
        if (item == menu_info_types.SERVER_MENU1 && player_structure.isOwner(structure, player))
        {
            callSuiOfVehiclesToStore(player, self, playerHangar);
        }
        else if (item == menu_info_types.SERVER_MENU2 && player_structure.isOwner(structure, player))
        {
            callSuiOfStoredVehicles(player, self, playerHangar);
        }
        else if (item == menu_info_types.SERVER_MENU3 && player_structure.isOwner(structure, player))
        {
            placeDisplayVehicle(player, self, playerHangar);
        }
        return SCRIPT_CONTINUE;
    }
    public int OnGetAttributes(obj_id self, obj_id player, String[] names, String[] attribs) throws InterruptedException
    {
        int idx = utils.getValidAttributeIndex(names);
        if (idx == -1)
        {
            return SCRIPT_CONTINUE;
        }
        if (!exists(self))
        {
            return SCRIPT_CONTINUE;
        }
        if (!player_structure.isOwner(self, player))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id playerHangar = utils.getPlayerHangar(player);
        if (!isIdValid(playerHangar))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id[] storedVehicles = vehicle.findVehicleControlDevicesInHangarSlot(player);
        if (storedVehicles == null || storedVehicles.length <= 0)
        {
            names[idx] = "vehicle_in_garage";
            attribs[idx] = "none";
            idx++;
        }
        else 
        {
            String[] storedVehicleNames = new String[storedVehicles.length];
            for (int i = 0; i < storedVehicles.length; ++i)
            {
                obj_id storedVehicle = storedVehicles[i];
                storedVehicleNames[i] = getEncodedName(storedVehicle);
            }
            if (storedVehicleNames == null || storedVehicleNames.length <= 0)
            {
                names[idx] = "vehicle_in_garage";
                attribs[idx] = "none";
                idx++;
            }
            else 
            {
                names[idx] = "vehicles_in_garage_amount";
                attribs[idx] = "" + storedVehicles.length;
                idx++;
                for (int j = 0; j < storedVehicleNames.length; ++j)
                {
                    names[idx] = "vehicle_in_garage";
                    attribs[idx] = storedVehicleNames[j];
                    idx++;
                }
            }
        }
        return SCRIPT_CONTINUE;
    }
    public void placeDisplayVehicle(obj_id player, obj_id garageControls, obj_id playerHangarSlot) throws InterruptedException
    {
        if (!isIdValid(player) || !exists(player))
        {
            return;
        }
        if (!isIdValid(garageControls) || !exists(garageControls))
        {
            return;
        }
        if (!isIdValid(playerHangarSlot) || !exists(playerHangarSlot))
        {
            return;
        }
        obj_id[] vehiclesInHangarSlot = vehicle.findVehicleControlDevicesInHangarSlot(player);
        if (vehiclesInHangarSlot == null || vehiclesInHangarSlot.length <= 0)
        {
            sendSystemMessage(player, SID_NO_VEHICLES);
            return;
        }
        utils.setScriptVar(garageControls, SCRIPT_VAR_VCD_LIST, vehiclesInHangarSlot);
        String[] localizedList = new String[vehiclesInHangarSlot.length];
        for (int i = 0; i < localizedList.length; ++i)
        {
            localizedList[i] = getEncodedName(vehiclesInHangarSlot[i]);
            if (hasObjVar(vehiclesInHangarSlot[i], VEHICLE_OBJVAR_DISPLAYED_VEHICLE))
            {
                localizedList[i] += " (Placed)";
            }
        }
        utils.setScriptVar(garageControls, SCRIPT_VAR_DECORATING_VEHICLE, true);
        int pid = sui.listbox(garageControls, player, "@" + SID_PLACE_VEHICLE_DECORATION_PROMPT, sui.OK_CANCEL, "@" + SID_LIST_TITLE, localizedList, "onDisplayVehicleResponse", true, false);
        sui.setPid(player, pid, PID_NAME);
    }
    public boolean callSuiOfVehiclesToStore(obj_id player, obj_id terminal, obj_id hangar) throws InterruptedException
    {
        if (!isIdValid(player) || !exists(player))
        {
            return false;
        }
        if (!isIdValid(terminal) || !exists(terminal))
        {
            return false;
        }
        if (!isIdValid(hangar) || !exists(hangar))
        {
            return false;
        }
        obj_id[] vehiclesInHangarSlot = vehicle.findVehicleControlDevicesInHangarSlot(player);
        if (vehiclesInHangarSlot != null && vehiclesInHangarSlot.length >= MAX_STORED_VEHICLES)
        {
            sendSystemMessage(player, SID_MAX_VEHICLES_STORED);
            return false;
        }
        obj_id[] vehicleControlDevices = vehicle.findVehicleControlDevicesForPlayer(player);
        if (vehicleControlDevices == null || vehicleControlDevices.length <= 0)
        {
            sendSystemMessage(player, SID_NO_VEHICLES);
            return false;
        }
        utils.setScriptVar(terminal, SCRIPT_VAR_VCD_LIST, vehicleControlDevices);
        String[] localizedList = new String[vehicleControlDevices.length];
        for (int i = 0; i < localizedList.length; ++i)
        {
            localizedList[i] = getEncodedName(vehicleControlDevices[i]);
        }
        int pid = sui.listbox(terminal, player, "@" + SID_VEHICLE_LIST_STORE_PROMPT, sui.OK_CANCEL, "@" + SID_LIST_TITLE, localizedList, "onVehicleListResponse", true, false);
        sui.setPid(player, pid, PID_NAME);
        return true;
    }
    public boolean callSuiOfStoredVehicles(obj_id player, obj_id terminal, obj_id hangar) throws InterruptedException
    {
        if (!isIdValid(player) || !exists(player))
        {
            return false;
        }
        if (!isIdValid(terminal) || !exists(terminal))
        {
            return false;
        }
        if (!isIdValid(hangar) || !exists(hangar))
        {
            return false;
        }
        obj_id[] vehiclesInHangarSlot = vehicle.findVehicleControlDevicesInHangarSlot(player);
        if (vehiclesInHangarSlot == null || vehiclesInHangarSlot.length <= 0)
        {
            sendSystemMessage(player, SID_NO_VEHICLES);
            return false;
        }
        utils.setScriptVar(terminal, SCRIPT_VAR_VCD_LIST, vehiclesInHangarSlot);
        String[] localizedList = new String[vehiclesInHangarSlot.length];
        for (int i = 0; i < localizedList.length; ++i)
        {
            localizedList[i] = getEncodedName(vehiclesInHangarSlot[i]);
        }
        utils.setScriptVar(terminal, SCRIPT_VAR_RESTORING_VEHICLE, true);
        int pid = sui.listbox(terminal, player, "@" + SID_VEHICLE_LIST_RESTORE_PROMPT, sui.OK_CANCEL, "@" + SID_LIST_TITLE, localizedList, "onVehicleListResponse", true, false);
        sui.setPid(player, pid, PID_NAME);
        return true;
    }
    public int handleHangarInitialize(obj_id self, dictionary params) throws InterruptedException
    {
        if (params == null || params.isEmpty())
        {
            return SCRIPT_CONTINUE;
        }
        obj_id player = params.getObjId("player");
        if (!isIdValid(player) || !exists(player))
        {
            return SCRIPT_CONTINUE;
        }
        int iteration = params.getInt("iteration");
        obj_id playerHangar = utils.getPlayerHangar(player);
        if (!isIdValid(playerHangar))
        {
            if (iteration > MAX_ITERATIONS)
            {
                sendSystemMessage(player, SID_HANGAR_COULD_NOT_INTIALIZE);
                obj_id storeHangarId = getObjIdObjVar(player, player_structure.OBJVAR_HANGAR_CREATED);
                CustomerServiceLog(CUSTOMER_SERVICE_LOG, "Player " + getPlayerName(player) + "(" + player + ")'s Hangar could not be found after 5 5 second messageTos. Hangar Id should be " + storeHangarId + ", we know this from the objvar set on the player when the hangar is first created.");
                return SCRIPT_CONTINUE;
            }
            params.put("iteration", ++iteration);
            sendSystemMessage(player, SID_INTIALIZING_HANGAR);
            messageTo(self, "handleHangarInitialize", params, MESSAGE_DELAY, false);
            return SCRIPT_CONTINUE;
        }
        boolean storing = params.getBoolean("storing");
        if (storing)
        {
            callSuiOfVehiclesToStore(player, self, playerHangar);
        }
        else 
        {
            callSuiOfStoredVehicles(player, self, playerHangar);
        }
        return SCRIPT_CONTINUE;
    }
    public int onDisplayVehicleResponse(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id player = params.getObjId("player");
        obj_id playerHangar = utils.getPlayerHangar(player);
        if (!isIdValid(player) || !exists(player))
        {
            return SCRIPT_CONTINUE;
        }
        if (!isIdValid(self) || !exists(self))
        {
            return SCRIPT_CONTINUE;
        }
        if (!isIdValid(playerHangar) || !exists(playerHangar))
        {
            return SCRIPT_CONTINUE;
        }
        if (!utils.hasScriptVar(self, SCRIPT_VAR_VCD_LIST))
        {
            utils.removeScriptVar(self, SCRIPT_VAR_DECORATING_VEHICLE);
            return SCRIPT_CONTINUE;
        }
        int bp = sui.getIntButtonPressed(params);
        int idx = sui.getListboxSelectedRow(params);
        if (bp == sui.BP_CANCEL || idx == -1)
        {
            sui.removePid(player, PID_NAME);
            utils.removeScriptVar(self, SCRIPT_VAR_VCD_LIST);
            utils.removeScriptVar(self, SCRIPT_VAR_DECORATING_VEHICLE);
            return SCRIPT_CONTINUE;
        }
        int pageId = params.getInt("pageId");
        if (!sui.hasPid(player, PID_NAME))
        {
            utils.removeScriptVar(self, SCRIPT_VAR_VCD_LIST);
            utils.removeScriptVar(self, SCRIPT_VAR_DECORATING_VEHICLE);
            return SCRIPT_CONTINUE;
        }
        int pid = sui.getPid(player, PID_NAME);
        if (pageId != pid)
        {
            forceCloseSUIPage(pageId);
            forceCloseSUIPage(pid);
            sui.removePid(player, PID_NAME);
            utils.removeScriptVar(self, SCRIPT_VAR_VCD_LIST);
            utils.removeScriptVar(self, SCRIPT_VAR_DECORATING_VEHICLE);
            return SCRIPT_CONTINUE;
        }
        if (!player_structure.isOwner(self, player))
        {
            sui.removePid(player, PID_NAME);
            utils.removeScriptVar(self, SCRIPT_VAR_VCD_LIST);
            utils.removeScriptVar(self, SCRIPT_VAR_DECORATING_VEHICLE);
            return SCRIPT_CONTINUE;
        }
        obj_id[] vehicleList = utils.getObjIdArrayScriptVar(self, SCRIPT_VAR_VCD_LIST);
        if (vehicleList == null || vehicleList.length <= 0)
        {
            utils.removeScriptVar(self, SCRIPT_VAR_DECORATING_VEHICLE);
            return SCRIPT_CONTINUE;
        }
        utils.removeScriptVar(self, SCRIPT_VAR_VCD_LIST);
        obj_id vehicleSelected = vehicleList[idx];
        if (!isIdValid(vehicleSelected) || !exists(vehicleSelected))
        {
            utils.removeScriptVar(self, SCRIPT_VAR_DECORATING_VEHICLE);
            return SCRIPT_CONTINUE;
        }
        if (hasObjVar(vehicleSelected, VEHICLE_OBJVAR_DISPLAYED_VEHICLE))
        {
            obj_id displayVehicle = getObjIdObjVar(vehicleSelected, VEHICLE_OBJVAR_DISPLAYED_VEHICLE);
            if (!isIdValid(displayVehicle) || !exists(displayVehicle))
            {
                removeObjVar(vehicleSelected, VEHICLE_OBJVAR_DISPLAYED_VEHICLE);
                return SCRIPT_CONTINUE;
            }
            destroyObject(displayVehicle);
            removeObjVar(vehicleSelected, VEHICLE_OBJVAR_DISPLAYED_VEHICLE);
            sendSystemMessage(player, SID_REMOVE_VEHICLE_DECORATION_SUCCESS);
        }
        else 
        {
            String vehicleReference = vehicle.getVehicleReference(vehicleSelected);
            dictionary vehicleTemplateData = dataTableGetRow(GARAGE_DISPLAY_VEHICLES_TABLE, vehicleReference);
            if (vehicleTemplateData == null)
            {
                return SCRIPT_CONTINUE;
            }
            String template = vehicleTemplateData.getString("GARAGE_DISPLAY_TEMPLATE");
            if (template == null || template.length() <= 0)
            {
                return SCRIPT_CONTINUE;
            }
            location here = getLocation(self);
            here.x = -2.0f;
            here.y = 0.5f;
            here.z = 0.0f;
            obj_id house = getTopMostContainer(player);
            if (!isIdValid(house) || !exists(house))
            {
                return SCRIPT_CONTINUE;
            }
            if (!player_structure.isOwner(house, player))
            {
                return SCRIPT_CONTINUE;
            }
            String houseTemplate = getTemplateName(house);
            if (!houseTemplate.equals("object/building/player/player_house_tcg_vehicle_garage.iff"))
            {
                return SCRIPT_CONTINUE;
            }
            obj_id toyVehicle = createObject(template, here);
            if (isIdValid(toyVehicle))
            {
                persistObject(toyVehicle);
                setYaw(toyVehicle, -90);
                String houseName = getName(house);
                houseName = localize(new string_id(houseName.substring(0, houseName.indexOf(":")), houseName.substring(houseName.indexOf(":") + 1, houseName.length())));
                setObjVar(vehicleSelected, VEHICLE_OBJVAR_DISPLAYED_VEHICLE, toyVehicle);
                setObjVar(toyVehicle, "validHouse", "object/building/player/player_house_tcg_vehicle_garage.iff");
                setObjVar(toyVehicle, "validHouseName", houseName);
                attachScript(toyVehicle, "item.special.nomove_furniture_house_only");
                vehicle.restoreCustomization(toyVehicle, vehicleSelected);
                sendSystemMessage(player, SID_PLACE_VEHICLE_DECORATION_SUCCESS);
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int onVehicleListResponse(obj_id self, dictionary params) throws InterruptedException
    {
        if (params == null || params.isEmpty())
        {
            utils.removeScriptVar(self, SCRIPT_VAR_RESTORING_VEHICLE);
            return SCRIPT_CONTINUE;
        }
        obj_id player = sui.getPlayerId(params);
        if (!isIdValid(player))
        {
            utils.removeScriptVar(self, SCRIPT_VAR_RESTORING_VEHICLE);
            return SCRIPT_CONTINUE;
        }
        location garageTerminalLoc = getLocation(self);
        location playerLoc = getLocation(player);
        if (garageTerminalLoc.cell != playerLoc.cell)
        {
            sendSystemMessage(player, SID_TOO_FAR);
            return SCRIPT_CONTINUE;
        }
        if (!utils.hasScriptVar(self, SCRIPT_VAR_VCD_LIST))
        {
            utils.removeScriptVar(self, SCRIPT_VAR_RESTORING_VEHICLE);
            return SCRIPT_CONTINUE;
        }
        int bp = sui.getIntButtonPressed(params);
        int idx = sui.getListboxSelectedRow(params);
        if (bp == sui.BP_CANCEL || idx == -1)
        {
            sui.removePid(player, PID_NAME);
            utils.removeScriptVar(self, SCRIPT_VAR_VCD_LIST);
            utils.removeScriptVar(self, SCRIPT_VAR_RESTORING_VEHICLE);
            return SCRIPT_CONTINUE;
        }
        int pageId = params.getInt("pageId");
        if (!sui.hasPid(player, PID_NAME))
        {
            utils.removeScriptVar(self, SCRIPT_VAR_VCD_LIST);
            utils.removeScriptVar(self, SCRIPT_VAR_RESTORING_VEHICLE);
            return SCRIPT_CONTINUE;
        }
        int pid = sui.getPid(player, PID_NAME);
        if (pageId != pid)
        {
            forceCloseSUIPage(pageId);
            forceCloseSUIPage(pid);
            sui.removePid(player, PID_NAME);
            utils.removeScriptVar(self, SCRIPT_VAR_VCD_LIST);
            utils.removeScriptVar(self, SCRIPT_VAR_RESTORING_VEHICLE);
            return SCRIPT_CONTINUE;
        }
        if (!player_structure.isOwner(self, player))
        {
            sui.removePid(player, PID_NAME);
            utils.removeScriptVar(self, SCRIPT_VAR_VCD_LIST);
            utils.removeScriptVar(self, SCRIPT_VAR_RESTORING_VEHICLE);
            CustomerServiceLog(CUSTOMER_SERVICE_LOG, "Player " + getPlayerName(player) + "(" + player + ") did NOT transfer a vehicle because they are no longer the owner of the structure.");
            return SCRIPT_CONTINUE;
        }
        obj_id[] vehicleList = utils.getObjIdArrayScriptVar(self, SCRIPT_VAR_VCD_LIST);
        if (vehicleList == null || vehicleList.length <= 0)
        {
            utils.removeScriptVar(self, SCRIPT_VAR_RESTORING_VEHICLE);
            return SCRIPT_CONTINUE;
        }
        utils.removeScriptVar(self, SCRIPT_VAR_VCD_LIST);
        obj_id vehicleSelected = vehicleList[idx];
        if (!isIdValid(vehicleSelected) || !exists(vehicleSelected))
        {
            utils.removeScriptVar(self, SCRIPT_VAR_RESTORING_VEHICLE);
            return SCRIPT_CONTINUE;
        }
        utils.setScriptVar(self, SCRIPT_VAR_VCD_LIST, vehicleSelected);
        CustomerServiceLog(CUSTOMER_SERVICE_LOG, "Player " + getPlayerName(player) + "(" + player + ") chose VCD " + getEncodedName(vehicleSelected) + "(" + vehicleSelected + ") as the vehicle to move.");
        String prompt = "@" + SID_VEHICLE_MOVE_RESTORE_PROMPT;
        if (!utils.hasScriptVar(self, SCRIPT_VAR_RESTORING_VEHICLE))
        {
            prompt = "@" + SID_VEHICLE_MOVE_STORE_PROMPT;
        }
        int newPid = sui.msgbox(self, player, prompt, sui.YES_NO, "@" + SID_VEHICLE_MOVE_TITLE, "handlerConfirmVehicleMove");
        sui.setPid(player, newPid, PID_NAME);
        return SCRIPT_CONTINUE;
    }
    public int handlerConfirmVehicleMove(obj_id self, dictionary params) throws InterruptedException
    {
        if (params == null || params.isEmpty())
        {
            utils.removeScriptVar(self, SCRIPT_VAR_RESTORING_VEHICLE);
            return SCRIPT_CONTINUE;
        }
        obj_id player = sui.getPlayerId(params);
        if (!isIdValid(player))
        {
            utils.removeScriptVar(self, SCRIPT_VAR_RESTORING_VEHICLE);
            return SCRIPT_CONTINUE;
        }
        location garageTerminalLoc = getLocation(self);
        location playerLoc = getLocation(player);
        if (garageTerminalLoc.cell != playerLoc.cell)
        {
            sendSystemMessage(player, SID_TOO_FAR);
            return SCRIPT_CONTINUE;
        }
        if (!utils.hasScriptVar(self, SCRIPT_VAR_VCD_LIST))
        {
            utils.removeScriptVar(self, SCRIPT_VAR_RESTORING_VEHICLE);
            return SCRIPT_CONTINUE;
        }
        int bp = sui.getIntButtonPressed(params);
        if (bp == sui.BP_CANCEL)
        {
            sui.removePid(player, PID_NAME);
            utils.removeScriptVar(self, SCRIPT_VAR_VCD_LIST);
            utils.removeScriptVar(self, SCRIPT_VAR_RESTORING_VEHICLE);
            return SCRIPT_CONTINUE;
        }
        int pageId = params.getInt("pageId");
        if (!sui.hasPid(player, PID_NAME))
        {
            utils.removeScriptVar(self, SCRIPT_VAR_VCD_LIST);
            utils.removeScriptVar(self, SCRIPT_VAR_RESTORING_VEHICLE);
            return SCRIPT_CONTINUE;
        }
        int pid = sui.getPid(player, PID_NAME);
        if (pageId != pid)
        {
            forceCloseSUIPage(pageId);
            forceCloseSUIPage(pid);
            sui.removePid(player, PID_NAME);
            utils.removeScriptVar(self, SCRIPT_VAR_VCD_LIST);
            utils.removeScriptVar(self, SCRIPT_VAR_RESTORING_VEHICLE);
            return SCRIPT_CONTINUE;
        }
        if (!player_structure.isOwner(self, player))
        {
            sui.removePid(player, PID_NAME);
            utils.removeScriptVar(self, SCRIPT_VAR_VCD_LIST);
            utils.removeScriptVar(self, SCRIPT_VAR_RESTORING_VEHICLE);
            CustomerServiceLog(CUSTOMER_SERVICE_LOG, "Player " + getPlayerName(player) + "(" + player + ") did not transfer a vehicle because they are no longer the owner of the structure.");
            return SCRIPT_CONTINUE;
        }
        obj_id vehicleSelected = utils.getObjIdScriptVar(self, SCRIPT_VAR_VCD_LIST);
        if (!isIdValid(vehicleSelected) || !exists(vehicleSelected))
        {
            utils.removeScriptVar(self, SCRIPT_VAR_RESTORING_VEHICLE);
            return SCRIPT_CONTINUE;
        }
        utils.removeScriptVar(self, SCRIPT_VAR_VCD_LIST);
        obj_id playerHangar = utils.getPlayerHangar(player);
        obj_id datapad = utils.getPlayerDatapad(player);
        if (!isIdValid(playerHangar))
        {
            utils.removeScriptVar(self, SCRIPT_VAR_RESTORING_VEHICLE);
            return SCRIPT_CONTINUE;
        }
        if (!isIdValid(datapad))
        {
            utils.removeScriptVar(self, SCRIPT_VAR_RESTORING_VEHICLE);
            return SCRIPT_CONTINUE;
        }
        if (!utils.hasScriptVar(self, SCRIPT_VAR_RESTORING_VEHICLE))
        {
            vehicle.storeVehicle(vehicleSelected, player);
            if (putIn(vehicleSelected, playerHangar, player))
            {
                CustomerServiceLog(CUSTOMER_SERVICE_LOG, "VCD " + getEncodedName(vehicleSelected) + " (" + vehicleSelected + ") was transferred to Player " + getPlayerName(player) + "(" + player + ")'s hangar slot (" + playerHangar + "), using the vehicle garage terminal.");
                prose_package pp = new prose_package();
                prose.setStringId(pp, SID_VEHICLE_WAS_STORED);
                prose.setTT(pp, getEncodedName(vehicleSelected));
                sendSystemMessageProse(player, pp);
            }
            else 
            {
                sendSystemMessage(player, SID_TRANSFER_FAILED_GENERIC);
                CustomerServiceLog(CUSTOMER_SERVICE_LOG, "VCD " + getEncodedName(vehicleSelected) + " (" + vehicleSelected + ") was NOT transferred to Player " + getPlayerName(player) + "(" + player + ")'s hangar slot (" + playerHangar + "), the putIn command failed.");
            }
        }
        else 
        {
            if (putIn(vehicleSelected, datapad, player))
            {
                obj_id displayVehicle = getObjIdObjVar(vehicleSelected, VEHICLE_OBJVAR_DISPLAYED_VEHICLE);
                if (!isIdValid(displayVehicle) || !exists(displayVehicle))
                {
                    removeObjVar(vehicleSelected, VEHICLE_OBJVAR_DISPLAYED_VEHICLE);
                }
                else 
                {
                    destroyObject(displayVehicle);
                    removeObjVar(vehicleSelected, VEHICLE_OBJVAR_DISPLAYED_VEHICLE);
                }
                CustomerServiceLog(CUSTOMER_SERVICE_LOG, "VCD " + getEncodedName(vehicleSelected) + " (" + vehicleSelected + ") was transferred back to Player " + getPlayerName(player) + "(" + player + ")'s datapad (" + datapad + "), using the vehicle garage terminal..");
                prose_package pp = new prose_package();
                prose.setStringId(pp, SID_VEHICLE_WAS_RESTORED);
                prose.setTT(pp, getEncodedName(vehicleSelected));
                sendSystemMessageProse(player, pp);
            }
            else 
            {
                sendSystemMessage(player, SID_TRANSFER_FAILED_GENERIC);
                CustomerServiceLog(CUSTOMER_SERVICE_LOG, "VCD " + getEncodedName(vehicleSelected) + " (" + vehicleSelected + ") was NOT transferred to Player " + getPlayerName(player) + "(" + player + ")'s datapad (" + datapad + "), the putIn command failed.");
            }
            utils.removeScriptVar(self, SCRIPT_VAR_RESTORING_VEHICLE);
        }
        return SCRIPT_CONTINUE;
    }
}
