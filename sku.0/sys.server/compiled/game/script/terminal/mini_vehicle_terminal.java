package script.terminal;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.player_structure;
import script.library.utils;

public class mini_vehicle_terminal extends script.base_script
{
    public mini_vehicle_terminal()
    {
    }
    public static final String MENU_FILE = "pet/pet_menu";
    public static final string_id VEHICLE_NAME = new string_id(MENU_FILE, "mini_vehicle_custom_name");
    public static final string_id VEHICLE_BIOLINK_NEEDED = new string_id(MENU_FILE, "mini_vehicle_biolink_needed");
    public static final string_id VEHICLE_NOT_INVENTORY = new string_id(MENU_FILE, "mini_vehicle_not_inventory");
    public static final string_id VEHICLE_NOT_OWNER = new string_id(MENU_FILE, "mini_vehicle_not_owner");
    public static final string_id VEHICLE_NOT_IN_HOUSE = new string_id(MENU_FILE, "mini_vehicle_not_in_house");
    public static final string_id SUMMON = new string_id("sui", "summon_vehicle");
    public static final string_id DESTROY = new string_id("sui", "destroy_vehicle");
    public static final String OBJVAR_VEHICLE_PATROL_POINTS = "vehicle.patrolPoints";
    public static final String OBJVAR_VEHICLE_PATROL_LOOP = "vehicle.patrol_loop";
    public static final String OBJVAR_VEHICLE_PATROL_ONCE = "vehicle.patrol_once";
    public static final String VEHICLE_ID = "vehicle.id";
    public static final String VEHICLE_NAMED = "vehicle.named";
    public static final String PLAYER = "vehicle.user";
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        obj_id terminal = self;
        obj_id vehicle = getObjIdObjVar(terminal, VEHICLE_ID);
        if (!isValidId(vehicle))
        {
            removeObjVar(terminal, VEHICLE_ID);
        }
        else if (!exists(vehicle))
        {
            boolean created = recreateVehicle(terminal);
            if (!created)
            {
                removeObjVar(terminal, VEHICLE_ID);
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int OnDestroy(obj_id self) throws InterruptedException
    {
        obj_id terminal = self;
        obj_id vehicle = getObjIdObjVar(terminal, VEHICLE_ID);
        if (!isValidId(vehicle))
        {
            return SCRIPT_CONTINUE;
        }
        destroyObject(vehicle);
        return SCRIPT_CONTINUE;
    }
    public int OnAboutToBeTransferred(obj_id self, obj_id destContainer, obj_id transferer) throws InterruptedException
    {
        obj_id terminal = self;
        if (!isValidId(terminal))
        {
            return SCRIPT_CONTINUE;
        }
        if (hasObjVar(terminal, VEHICLE_ID) && exists(terminal))
        {
            messageTo(terminal, "destroyVehicle", null, 1, false);
        }
        if (hasObjVar(terminal, OBJVAR_VEHICLE_PATROL_POINTS) && exists(terminal))
        {
            removeObjVar(terminal, OBJVAR_VEHICLE_PATROL_POINTS);
        }
        if (hasObjVar(terminal, OBJVAR_VEHICLE_PATROL_LOOP) && exists(terminal))
        {
            removeObjVar(terminal, OBJVAR_VEHICLE_PATROL_LOOP);
        }
        if (hasObjVar(terminal, OBJVAR_VEHICLE_PATROL_ONCE) && exists(terminal))
        {
            removeObjVar(terminal, OBJVAR_VEHICLE_PATROL_ONCE);
        }
        return SCRIPT_CONTINUE;
    }
    public int OnObjectMenuRequest(obj_id self, obj_id player, menu_info mi) throws InterruptedException
    {
        obj_id terminal = self;
        if (!exists(terminal))
        {
            return SCRIPT_CONTINUE;
        }
        if (utils.isInVendor(terminal) || utils.isInBazaar(terminal))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id ownerContainer = getTopMostContainer(terminal);
        if (!isValidId(ownerContainer) || !exists(ownerContainer))
        {
            return SCRIPT_CONTINUE;
        }
        if (!player_structure.isBuilding(ownerContainer) && !isPlayer(ownerContainer))
        {
            return SCRIPT_CONTINUE;
        }
        if (hasObjVar(terminal, VEHICLE_ID))
        {
            mi.addRootMenu(menu_info_types.ITEM_USE, DESTROY);
        }
        else 
        {
            mi.addRootMenu(menu_info_types.ITEM_USE, SUMMON);
        }
        return SCRIPT_CONTINUE;
    }
    public int OnObjectMenuSelect(obj_id self, obj_id player, int item) throws InterruptedException
    {
        obj_id terminal = self;
        obj_id biolink = getBioLink(terminal);
        if (!isValidId(terminal))
        {
            return SCRIPT_CONTINUE;
        }
        if (!isValidId(player))
        {
            return SCRIPT_CONTINUE;
        }
        sendDirtyObjectMenuNotification(terminal);
        if (item != menu_info_types.ITEM_USE)
        {
            return SCRIPT_CONTINUE;
        }
        if (!isValidId(biolink) || biolink == utils.OBJ_ID_BIO_LINK_PENDING)
        {
            sendSystemMessage(player, VEHICLE_BIOLINK_NEEDED);
            return SCRIPT_CONTINUE;
        }
        if (utils.isNestedWithinAPlayer(terminal))
        {
            sendSystemMessage(player, VEHICLE_NOT_INVENTORY);
            return SCRIPT_CONTINUE;
        }
        if (player != getObjIdObjVar(terminal, "biolink.id"))
        {
            sendSystemMessage(player, VEHICLE_NOT_OWNER);
            return SCRIPT_CONTINUE;
        }
        if (!utils.isInHouseCellSpace(terminal))
        {
            sendSystemMessage(player, VEHICLE_NOT_IN_HOUSE);
            return SCRIPT_CONTINUE;
        }
        if (!hasObjVar(terminal, VEHICLE_ID))
        {
            utils.setScriptVar(terminal, "terminal.summon", true);
            boolean vehicleCreated = createVehicle(terminal, player);
            if (!vehicleCreated)
            {
                removeObjVar(terminal, VEHICLE_ID);
            }
        }
        else 
        {
            messageTo(terminal, "destroyVehicle", null, 1, false);
        }
        return SCRIPT_CONTINUE;
    }
    public int destroyVehicle(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id terminal = self;
        if (!isValidId(terminal))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id player = getObjIdObjVar(terminal, "biolink.id");
        if (!isValidId(player) || !exists(player))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id vehicle = getObjIdObjVar(terminal, VEHICLE_ID);
        if (isValidId(vehicle) && exists(vehicle))
        {
            obj_id house = getObjIdObjVar(vehicle, "house");
            if (!isValidId(house) || !exists(house))
            {
                return SCRIPT_CONTINUE;
            }
            removeObjVar(house, "mini_vehicle.vehicle");
            if (hasScript(house, "structure.mini_vehicle_control"))
            {
                detachScript(house, "structure.mini_vehicle_control");
            }
            destroyObject(vehicle);
        }
        removeObjVar(terminal, VEHICLE_ID);
        sendConsoleMessage(player, "vehicle retired.");
        return SCRIPT_CONTINUE;
    }
    public int OnPack(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id vehicle = getObjIdObjVar(self, VEHICLE_ID);
        if (!isValidId(vehicle))
        {
            return SCRIPT_CONTINUE;
        }
        destroyObject(vehicle);
        return SCRIPT_CONTINUE;
    }
    public boolean setVehicleVars(obj_id terminal, obj_id vehicle) throws InterruptedException
    {
        if (!isValidId(terminal) || !exists(terminal))
        {
            return false;
        }
        if (!isValidId(vehicle) || !exists(vehicle))
        {
            return false;
        }
        obj_id user = getObjIdObjVar(terminal, "biolink.id");
        setInvulnerable(vehicle, true);
        setObjVar(terminal, VEHICLE_ID, vehicle);
        setObjVar(vehicle, "terminal", terminal);
        setObjVar(vehicle, PLAYER, user);
        return true;
    }
    public boolean createVehicle(obj_id terminal, obj_id player) throws InterruptedException
    {
        if (!isValidId(terminal) || !exists(terminal))
        {
            return false;
        }
        obj_id anotherVehicle = getObjIdObjVar(terminal, VEHICLE_ID);
        if (isValidId(anotherVehicle) && exists(anotherVehicle))
        {
            return false;
        }
        obj_id house = getTopMostContainer(terminal);
        if (!isIdValid(house) && !exists(house))
        {
            return false;
        }
        if (!player_structure.isBuilding(house))
        {
            return false;
        }
        if (!hasScript(house, "structure.mini_vehicle_control"))
        {
            attachScript(house, "structure.mini_vehicle_control");
            listenToMessage(house, "handleActivateVehicle");
        }
        location startLocation = new location();
        location[] patrolLoc = getLocationArrayObjVar(terminal, OBJVAR_VEHICLE_PATROL_POINTS);
        if (patrolLoc == null)
        {
            location playerLoc = getLocation(player);
            if (!isValidId(playerLoc.cell))
            {
                startLocation = getLocation(terminal);
            }
            else 
            {
                startLocation = getLocation(player);
            }
        }
        else 
        {
            boolean validList = validLocationList(terminal, patrolLoc);
            if (!validList)
            {
                return false;
            }
            startLocation = patrolLoc[0];
        }
        String template = getStringObjVar(terminal, "template");
        obj_id vehicle = createObject(template, startLocation);
        if (patrolLoc != null)
        {
            messageTo(vehicle, "handleActivateVehicle", null, 1, false);
        }
        if (!isIdValid(vehicle) && !exists(vehicle))
        {
            return false;
        }
        setVehicleVars(terminal, vehicle);
        if (utils.hasScriptVar(terminal, "terminal.summon"))
        {
            sendConsoleMessage(player, "vehicle summoned.");
            utils.removeScriptVar(terminal, "terminal.summon");
        }
        return true;
    }
    public boolean recreateVehicle(obj_id terminal) throws InterruptedException
    {
        obj_id anotherVehicle = getObjIdObjVar(terminal, VEHICLE_ID);
        if (isValidId(anotherVehicle) && exists(anotherVehicle))
        {
            return false;
        }
        obj_id house = getTopMostContainer(terminal);
        if (!isIdValid(house) && !exists(house))
        {
            return false;
        }
        if (!player_structure.isBuilding(house))
        {
            return false;
        }
        if (!hasScript(house, "structure.mini_vehicle_control"))
        {
            attachScript(house, "structure.mini_vehicle_control");
            listenToMessage(house, "handleActivateVehicle");
        }
        location startLocation = new location();
        location[] patrolLoc = getLocationArrayObjVar(terminal, OBJVAR_VEHICLE_PATROL_POINTS);
        if (patrolLoc == null)
        {
            return false;
        }
        boolean validList = validLocationList(terminal, patrolLoc);
        if (!validList)
        {
            return false;
        }
        startLocation = patrolLoc[0];
        String template = getStringObjVar(terminal, "template");
        obj_id vehicle = createObject(template, startLocation);
        messageTo(vehicle, "handleActivateVehicle", null, 1, false);
        if (!isIdValid(vehicle) && !exists(vehicle))
        {
            return false;
        }
        setVehicleVars(terminal, vehicle);
        if (utils.hasScriptVar(terminal, "terminal.summon"))
        {
            utils.removeScriptVar(terminal, "terminal.summon");
        }
        return true;
    }
    public boolean validLocationList(obj_id terminal, location[] patrolLocations) throws InterruptedException
    {
        if (patrolLocations == null)
        {
            return false;
        }
        for (int i = 0; i < patrolLocations.length; ++i)
        {
            if (!isValidId(patrolLocations[i].cell))
            {
                removeObjVar(terminal, OBJVAR_VEHICLE_PATROL_POINTS);
                return false;
            }
        }
        return true;
    }
}
