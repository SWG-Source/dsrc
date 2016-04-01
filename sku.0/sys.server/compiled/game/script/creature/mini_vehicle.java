package script.creature;

import script.*;
import script.library.ai_lib;
import script.library.player_structure;
import script.library.sui;
import script.library.utils;

public class mini_vehicle extends script.base_script
{
    public mini_vehicle()
    {
    }
    public static final int MAX_WAYPOINTS = 20;
    public static final String OBJVAR_VEHICLE_PATROL_POINTS = "vehicle.patrolPoints";
    public static final String OBJVAR_VEHICLE_PATROL_LOOP = "vehicle.patrol_loop";
    public static final String OBJVAR_VEHICLE_PATROL_ONCE = "vehicle.patrol_once";
    public static final String VEHICLE_MOVING = "vehicle.moving";
    public static final String VEHICLE_SETTING_PATROL = "vehicle.settingPatrol";
    public static final String SCRIPTVAR_VEHICLE_HELP_MENU = "vehicle.helpmenu";
    public static final String TERMINAL_VEHICLE_ID = "vehicle.id";
    public static final String MENU_FILE = "pet/pet_menu";
    public static final string_id SYS_PATROL_ADDED = new string_id(MENU_FILE, "patrol_added");
    public static final string_id SYS_PATROL_REMOVED = new string_id(MENU_FILE, "patrol_removed");
    public static final string_id SETPATROLPOINT = new string_id(MENU_FILE, "menu_set_patrol_point");
    public static final string_id CLEARPOINTS = new string_id(MENU_FILE, "menu_clear_patrol_points");
    public static final string_id CLEAR_LAST = new string_id(MENU_FILE, "patrol_clear_last");
    public static final string_id PROGRAM = new string_id(MENU_FILE, "vehicle_options");
    public static final string_id HELP = new string_id(MENU_FILE, "mini_vehicle_menu_help");
    public static final string_id PATROL_OPTIONS = new string_id(MENU_FILE, "patrol_setting");
    public static final string_id PATROL = new string_id(MENU_FILE, "menu_patrol");
    public static final string_id ONCE = new string_id(MENU_FILE, "patrol_once");
    public static final string_id LOOP = new string_id(MENU_FILE, "patrol_loop");
    public static final string_id MINI_VEHICLE_NOT_OWNER = new string_id(MENU_FILE, "mini_vehicle_not_owner");
    public static final string_id MINI_VEHICLE_NO_WAYPOINTS = new string_id(MENU_FILE, "mini_vehicle_no_waypoints");
    public static final string_id MINI_VEHICLE_MAX_WAYPOINTS = new string_id(MENU_FILE, "mini_vehicle_max_waypoints");
    public static final string_id MINI_VEHICLE_NOT_IN_HOUSE = new string_id(MENU_FILE, "mini_vehicle_not_in_house");
    public static final string_id MINI_VEHICLE_WAYPOINTS_NOT_CLEAR = new string_id(MENU_FILE, "mini_vehicle_waypoints_not_cleared");
    public int OnDestroy(obj_id self) throws InterruptedException
    {
        if (!isValidId(self))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id terminal = getObjIdObjVar(self, "terminal");
        if (!isValidId(terminal))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id house = getObjIdObjVar(self, "house");
        if (isValidId(house) && exists(house))
        {
            removeObjVar(house, "mini_vehicle.vehicle");
            detachScript(house, "structure.mini_vehicle_control");
        }
        removeObjVar(terminal, TERMINAL_VEHICLE_ID);
        return SCRIPT_CONTINUE;
    }
    public int OnMovePathComplete(obj_id self) throws InterruptedException
    {
        if (!isValidId(self))
        {
            return SCRIPT_CONTINUE;
        }
        utils.removeScriptVar(self, VEHICLE_MOVING);
        return SCRIPT_CONTINUE;
    }
    public int OnMoveMoving(obj_id self) throws InterruptedException
    {
        if (!isValidId(self))
        {
            return SCRIPT_CONTINUE;
        }
        utils.setScriptVar(self, VEHICLE_MOVING, true);
        return SCRIPT_CONTINUE;
    }
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        if (!isValidId(self))
        {
            return SCRIPT_CONTINUE;
        }
        messageTo(self, "initializeVehicleVariables", null, 1, false);
        return SCRIPT_CONTINUE;
    }
    public int OnObjectMenuRequest(obj_id self, obj_id player, menu_info mi) throws InterruptedException
    {
        if (!isIdValid(self))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id terminal = getObjIdObjVar(self, "terminal");
        if (!isValidId(terminal) || !exists(terminal))
        {
            destroyObject(self);
        }
        if (utils.isNestedWithinAPlayer(self))
        {
            messageTo(terminal, "destroyVehicle", null, 1, false);
            return SCRIPT_CONTINUE;
        }
        mi.addRootMenu(menu_info_types.ITEM_ACTIVATE, PATROL);
        mi.addRootMenu(menu_info_types.SERVER_MENU1, HELP);
        int mnuProgram = mi.addRootMenu(menu_info_types.SERVER_MENU2, PATROL_OPTIONS);
        mi.addSubMenu(mnuProgram, menu_info_types.SERVER_MENU3, SETPATROLPOINT);
        mi.addSubMenu(mnuProgram, menu_info_types.SERVER_MENU4, CLEARPOINTS);
        mi.addSubMenu(mnuProgram, menu_info_types.SERVER_MENU5, CLEAR_LAST);
        mi.addSubMenu(mnuProgram, menu_info_types.SERVER_MENU6, ONCE);
        mi.addSubMenu(mnuProgram, menu_info_types.SERVER_MENU7, LOOP);
        return SCRIPT_CONTINUE;
    }
    public int OnObjectMenuSelect(obj_id self, obj_id player, int item) throws InterruptedException
    {
        if (!isValidId(self))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id terminal = getObjIdObjVar(self, "terminal");
        if (!isValidId(terminal))
        {
            return SCRIPT_CONTINUE;
        }
        if (utils.isNestedWithinAPlayer(self))
        {
            messageTo(terminal, "destroyVehicle", null, 1, false);
            return SCRIPT_CONTINUE;
        }
        if (player != getObjIdObjVar(terminal, "biolink.id"))
        {
            sendSystemMessage(player, MINI_VEHICLE_NOT_OWNER);
            return SCRIPT_CONTINUE;
        }
        sendDirtyObjectMenuNotification(self);
        if (item == menu_info_types.SERVER_MENU1)
        {
            createHelpDialog(self, player);
        }
        else if (item == menu_info_types.SERVER_MENU3)
        {
            doSetPatrolPoint(self, player);
        }
        else if (item == menu_info_types.SERVER_MENU4)
        {
            doClearPatrolPoints(self, player);
        }
        else if (item == menu_info_types.SERVER_MENU5)
        {
            removeLastPatrolPoint(self, player);
        }
        else if (item == menu_info_types.SERVER_MENU6)
        {
            setPatrolOnce(self, player, terminal);
        }
        else if (item == menu_info_types.SERVER_MENU7)
        {
            setPatrolLoop(self, player, terminal);
        }
        else if (item == menu_info_types.ITEM_ACTIVATE)
        {
            if (utils.hasScriptVar(self, VEHICLE_MOVING) && !utils.hasScriptVar(self, VEHICLE_SETTING_PATROL))
            {
                stop(self);
                utils.removeScriptVar(self, VEHICLE_MOVING);
            }
            else 
            {
                doPatrol(self, player);
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int getUserOidSetFirstPoint(obj_id self, dictionary params) throws InterruptedException
    {
        if (!isValidId(self))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id player = getObjIdObjVar(self, "user");
        if (!isValidId(player))
        {
            return SCRIPT_CONTINUE;
        }
        doSetPatrolPoint(self, player);
        return SCRIPT_CONTINUE;
    }
    public int pathDirectlyToStart(obj_id self, dictionary params) throws InterruptedException
    {
        if (!isValidId(self))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id terminal = getObjIdObjVar(self, "terminal");
        if (!isValidId(terminal))
        {
            return SCRIPT_CONTINUE;
        }
        location[] patrolLoc = getLocationArrayObjVar(terminal, OBJVAR_VEHICLE_PATROL_POINTS);
        if (patrolLoc != null)
        {
            stop(self);
            pathTo(self, patrolLoc[0]);
        }
        return SCRIPT_CONTINUE;
    }
    public int fromLastPointGoBackToStart(obj_id self, dictionary params) throws InterruptedException
    {
        if (!isValidId(self))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id terminal = getObjIdObjVar(self, "terminal");
        if (!isValidId(terminal))
        {
            return SCRIPT_CONTINUE;
        }
        location[] patrolLoc = getLocationArrayObjVar(terminal, OBJVAR_VEHICLE_PATROL_POINTS);
        if (patrolLoc != null)
        {
            int patrolLength = patrolLoc.length;
            location[] temp = new location[patrolLength];
            for (int i = 0; i < patrolLength; ++i)
            {
                temp[i] = patrolLoc[(patrolLength - 1) - i];
            }
            patrolOnce(self, temp);
        }
        return SCRIPT_CONTINUE;
    }
    public int handleActivateVehicle(obj_id self, dictionary params) throws InterruptedException
    {
        if (!isValidId(self))
        {
            return SCRIPT_CONTINUE;
        }
        doPatrol(self);
        return SCRIPT_CONTINUE;
    }
    public int handleDeactivateVehicle(obj_id self, dictionary params) throws InterruptedException
    {
        if (!isValidId(self))
        {
            return SCRIPT_CONTINUE;
        }
        stop(self);
        if (utils.hasScriptVar(self, VEHICLE_MOVING))
        {
            utils.removeScriptVar(self, VEHICLE_MOVING);
        }
        return SCRIPT_CONTINUE;
    }
    public int initializeVehicleVariables(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id house = getTopMostContainer(self);
        if (!isIdValid(self))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id terminal = getObjIdObjVar(self, "terminal");
        if (!isValidId(terminal))
        {
            return SCRIPT_CONTINUE;
        }
        if (!isIdValid(house) || !player_structure.isBuilding(house) || utils.isNestedWithinAPlayer(self))
        {
            messageTo(terminal, "destroyVehicle", null, 1, false);
            return SCRIPT_CONTINUE;
        }
        setObjVar(self, "house", house);
        if (!hasObjVar(terminal, OBJVAR_VEHICLE_PATROL_POINTS))
        {
            messageTo(self, "getUserOidSetFirstPoint", null, 1, false);
        }
        if (!hasScript(house, "structure.mini_vehicle_control"))
        {
            attachScript(house, "structure.mini_vehicle_control");
        }
        if (!hasObjVar(house, "mini_vehicle.vehicle"))
        {
            setObjVar(house, "mini_vehicle.vehicle", self);
        }
        listenToMessage(house, "handleActivateVehicle");
        listenToMessage(house, "handleDeactivateVehicle");
        return SCRIPT_CONTINUE;
    }
    public int handleDialogInput(obj_id self, dictionary params) throws InterruptedException
    {
        if (!isValidId(self))
        {
            utils.removeScriptVar(self, SCRIPTVAR_VEHICLE_HELP_MENU);
            return SCRIPT_CONTINUE;
        }
        obj_id player = sui.getPlayerId(params);
        if (!isValidId(player))
        {
            utils.removeScriptVar(self, SCRIPTVAR_VEHICLE_HELP_MENU);
            return SCRIPT_CONTINUE;
        }
        switch (sui.getIntButtonPressed(params))
        {
            case sui.BP_OK:
                doPatrol(self, player);
                break;
            case sui.BP_CANCEL:
                utils.removeScriptVar(self, SCRIPTVAR_VEHICLE_HELP_MENU);
                break;
        }
        return SCRIPT_CONTINUE;
    }
    public boolean doPatrol(obj_id vehicle) throws InterruptedException
    {
        if (!isValidId(vehicle))
        {
            return false;
        }
        obj_id terminal = getObjIdObjVar(vehicle, "terminal");
        if (!isValidId(terminal))
        {
            return false;
        }
        location[] patrolLoc = getLocationArrayObjVar(terminal, OBJVAR_VEHICLE_PATROL_POINTS);
        if (patrolLoc == null)
        {
            return false;
        }
        if (hasObjVar(terminal, OBJVAR_VEHICLE_PATROL_LOOP))
        {
            ai_lib.setPatrolPath(vehicle, patrolLoc);
            return true;
        }
        else 
        {
            patrolOnce(vehicle, patrolLoc);
            messageTo(vehicle, "fromLastPointGoBackToStart", null, 120, false);
            return true;
        }
    }
    public boolean doPatrol(obj_id vehicle, obj_id player) throws InterruptedException
    {
        if (!isValidId(vehicle) || !isValidId(player))
        {
            return false;
        }
        utils.removeScriptVar(vehicle, SCRIPTVAR_VEHICLE_HELP_MENU);
        utils.removeScriptVar(vehicle, VEHICLE_SETTING_PATROL);
        obj_id terminal = getObjIdObjVar(vehicle, "terminal");
        if (!isValidId(terminal))
        {
            return false;
        }
        if (!hasObjVar(terminal, OBJVAR_VEHICLE_PATROL_POINTS))
        {
            sendSystemMessage(player, MINI_VEHICLE_NO_WAYPOINTS);
            return false;
        }
        location[] patrolLoc = getLocationArrayObjVar(terminal, OBJVAR_VEHICLE_PATROL_POINTS);
        if (patrolLoc == null)
        {
            return false;
        }
        if (hasObjVar(terminal, OBJVAR_VEHICLE_PATROL_LOOP))
        {
            ai_lib.setPatrolPath(vehicle, patrolLoc);
            return true;
        }
        else 
        {
            patrolOnce(vehicle, patrolLoc);
            messageTo(vehicle, "fromLastPointGoBackToStart", null, 120, false);
            return true;
        }
    }
    public void doSetPatrolPoint(obj_id vehicle, obj_id player) throws InterruptedException
    {
        if (!isValidId(vehicle) || !isValidId(player))
        {
            return;
        }
        utils.removeScriptVar(vehicle, SCRIPTVAR_VEHICLE_HELP_MENU);
        obj_id terminal = getObjIdObjVar(vehicle, "terminal");
        if (!isValidId(terminal))
        {
            return;
        }
        utils.setScriptVar(vehicle, VEHICLE_SETTING_PATROL, true);
        location[] patrolLoc = getLocationArrayObjVar(terminal, OBJVAR_VEHICLE_PATROL_POINTS);
        if (patrolLoc == null)
        {
            patrolLoc = new location[1];
        }
        else if (patrolLoc.length < MAX_WAYPOINTS)
        {
            location[] temp = new location[patrolLoc.length + 1];
            System.arraycopy(patrolLoc, 0, temp, 0, patrolLoc.length);
            patrolLoc = temp;
        }
        else 
        {
            sendSystemMessage(player, MINI_VEHICLE_MAX_WAYPOINTS);
            return;
        }
        location playerLoc = getLocation(player);
        if (!isValidId(playerLoc.cell))
        {
            sendSystemMessage(player, MINI_VEHICLE_NOT_IN_HOUSE);
            return;
        }
        patrolLoc[patrolLoc.length - 1] = playerLoc;
        setObjVar(terminal, OBJVAR_VEHICLE_PATROL_POINTS, patrolLoc);
        sendSystemMessage(player, SYS_PATROL_ADDED);
        sendConsoleMessage(player, "Patrol points left: " + (MAX_WAYPOINTS - patrolLoc.length));
        pathTo(vehicle, playerLoc);
    }
    public void doClearPatrolPoints(obj_id vehicle, obj_id player) throws InterruptedException
    {
        if (!isValidId(vehicle) || !isValidId(player))
        {
            return;
        }
        obj_id terminal = getObjIdObjVar(vehicle, "terminal");
        if (!isValidId(terminal))
        {
            return;
        }
        if (hasObjVar(terminal, OBJVAR_VEHICLE_PATROL_POINTS))
        {
            removeObjVar(terminal, OBJVAR_VEHICLE_PATROL_POINTS);
            sendSystemMessage(player, SYS_PATROL_REMOVED);
            ai_lib.clearPatrolPath(vehicle);
            stop(vehicle);
            messageTo(vehicle, "resumeDefaultCalmBehavior", null, 1, false);
        }
        else 
        {
            sendSystemMessage(player, MINI_VEHICLE_WAYPOINTS_NOT_CLEAR);
        }
    }
    public void removeLastPatrolPoint(obj_id vehicle, obj_id player) throws InterruptedException
    {
        if (!isValidId(vehicle) || !isValidId(player))
        {
            return;
        }
        obj_id terminal = getObjIdObjVar(vehicle, "terminal");
        if (!isValidId(terminal))
        {
            return;
        }
        if (hasObjVar(terminal, OBJVAR_VEHICLE_PATROL_POINTS))
        {
            location[] patrolLoc = getLocationArrayObjVar(terminal, OBJVAR_VEHICLE_PATROL_POINTS);
            if (patrolLoc == null)
            {
                sendSystemMessage(player, MINI_VEHICLE_WAYPOINTS_NOT_CLEAR);
            }
            else if (patrolLoc.length > 0)
            {
                location[] temp = new location[patrolLoc.length - 1];
                System.arraycopy(patrolLoc, 0, temp, 0, patrolLoc.length - 1);
                patrolLoc = temp;
                setObjVar(terminal, OBJVAR_VEHICLE_PATROL_POINTS, patrolLoc);
                sendSystemMessage(player, SYS_PATROL_ADDED);
                sendConsoleMessage(player, "Patrol points left: " + (MAX_WAYPOINTS - patrolLoc.length));
            }
            location playerLoc = getLocation(player);
            if (isValidId(playerLoc.cell))
            {
                pathTo(vehicle, playerLoc);
            }
        }
    }
    public boolean setPatrolLoop(obj_id vehicle, obj_id player, obj_id terminal) throws InterruptedException
    {
        if (!isValidId(vehicle) || !isValidId(player))
        {
            return false;
        }
        if (!isValidId(terminal) || !isValidId(terminal))
        {
            return false;
        }
        if (!hasObjVar(terminal, OBJVAR_VEHICLE_PATROL_LOOP))
        {
            setObjVar(terminal, OBJVAR_VEHICLE_PATROL_LOOP, true);
            if (hasObjVar(terminal, OBJVAR_VEHICLE_PATROL_ONCE))
            {
                removeObjVar(terminal, OBJVAR_VEHICLE_PATROL_ONCE);
            }
            sendConsoleMessage(player, "Patrol loop setting applied.");
            if (utils.hasScriptVar(vehicle, VEHICLE_MOVING))
            {
                stop(vehicle);
                doPatrol(vehicle, player);
            }
            return true;
        }
        return false;
    }
    public boolean setPatrolOnce(obj_id vehicle, obj_id player, obj_id terminal) throws InterruptedException
    {
        if (!hasObjVar(terminal, OBJVAR_VEHICLE_PATROL_ONCE))
        {
            setObjVar(terminal, OBJVAR_VEHICLE_PATROL_ONCE, true);
            if (hasObjVar(terminal, OBJVAR_VEHICLE_PATROL_LOOP))
            {
                removeObjVar(terminal, OBJVAR_VEHICLE_PATROL_LOOP);
            }
            sendConsoleMessage(player, "Patrol once only setting applied.");
            if (utils.hasScriptVar(vehicle, VEHICLE_MOVING))
            {
                stop(vehicle);
                doPatrol(vehicle, player);
            }
            return true;
        }
        return false;
    }
    public int createHelpDialog(obj_id vehicle, obj_id player) throws InterruptedException
    {
        if (utils.hasScriptVar(vehicle, SCRIPTVAR_VEHICLE_HELP_MENU))
        {
            return -1;
        }
        if (!isIdValid(player))
        {
            return -1;
        }
        if (!isIdValid(vehicle))
        {
            return -1;
        }
        int pid = sui.msgbox(vehicle, player, utils.packStringId(new string_id("sui", "mini_vehicle_sui_text")), sui.OK_CANCEL, utils.packStringId(new string_id("sui", "mini_vehicle_sui_title")), "handleDialogInput");
        sui.setSUIProperty(pid, sui.MSGBOX_BTN_CANCEL, sui.PROP_TEXT, utils.packStringId(new string_id("sui", "close")));
        sui.setSUIProperty(pid, sui.MSGBOX_BTN_OK, sui.PROP_TEXT, utils.packStringId(new string_id("sui", "mini_vehicle_sui_test_button")));
        flushSUIPage(pid);
        utils.setScriptVar(vehicle, SCRIPTVAR_VEHICLE_HELP_MENU, true);
        return pid;
    }
    public int OnPack(obj_id self, dictionary params) throws InterruptedException
    {
        if (!isValidId(self))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id terminal = getObjIdObjVar(self, "terminal");
        if (!isValidId(terminal))
        {
            return SCRIPT_CONTINUE;
        }
        messageTo(terminal, "destroyVehicle", null, 1, false);
        return SCRIPT_CONTINUE;
    }
}
