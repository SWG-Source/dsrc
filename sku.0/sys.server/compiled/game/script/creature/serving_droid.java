package script.creature;

import script.*;
import script.library.*;

public class serving_droid extends script.base_script
{
    public serving_droid()
    {
    }
    public static final int MAX_WAYPOINTS = 20;
    public static final String OBJVAR_DROID_PATROL_POINTS = "droid.patrolPoints";
    public static final String OBJVAR_DROID_PATROL_LOOP = "droid.patrol_loop";
    public static final String OBJVAR_DROID_PATROL_ONCE = "droid.patrol_once";
    public static final String DROID_MOVING = "droid.moving";
    public static final String DROID_SETTING_PATROL = "droid.settingPatrol";
    public static final String SCRIPTVAR_DROID_HELP_MENU = "droid.helpmenu";
    public static final String TERMINAL_DROID_ID = "droid.id";
    public static final String MENU_FILE = "pet/pet_menu";
    public static final string_id SYS_PATROL_ADDED = new string_id(MENU_FILE, "patrol_added");
    public static final string_id SYS_PATROL_REMOVED = new string_id(MENU_FILE, "patrol_removed");
    public static final string_id SETPATROLPOINT = new string_id(MENU_FILE, "menu_set_patrol_point");
    public static final string_id CLEARPOINTS = new string_id(MENU_FILE, "menu_clear_patrol_points");
    public static final string_id CLEAR_LAST = new string_id(MENU_FILE, "patrol_clear_last");
    public static final string_id PROGRAM = new string_id(MENU_FILE, "droid_options");
    public static final string_id HELP = new string_id(MENU_FILE, "serving_droid_menu_help");
    public static final string_id PATROL_OPTIONS = new string_id(MENU_FILE, "patrol_setting");
    public static final string_id PATROL = new string_id(MENU_FILE, "menu_patrol");
    public static final string_id ONCE = new string_id(MENU_FILE, "patrol_once");
    public static final string_id LOOP = new string_id(MENU_FILE, "patrol_loop");
    public static final string_id PCOLOR = new string_id("sui", "set_primary_color");
    public static final string_id SCOLOR = new string_id("sui", "set_secondary_color");
    public static final string_id ASTROMECH_NOT_OWNER = new string_id(MENU_FILE, "serving_droid_not_owner");
    public static final string_id ASTROMECH_NO_WAYPOINTS = new string_id(MENU_FILE, "serving_droid_no_waypoints");
    public static final string_id ASTROMECH_MAX_WAYPOINTS = new string_id(MENU_FILE, "serving_droid_max_waypoints");
    public static final string_id ASTROMECH_NOT_IN_HOUSE = new string_id(MENU_FILE, "serving_droid_not_in_house");
    public static final string_id ASTROMECH_WAYPOINTS_NOT_CLEAR = new string_id(MENU_FILE, "serving_droid_waypoints_not_cleared");
    public static final String INFO_UI_TITLE = localize(new string_id("sui", "serving_droid_sui_title"));
    public static final String INFO_UI_TEXT = localize(new string_id("sui", "serving_droid_sui_text"));
    public static final String TEST_PATROL = localize(new string_id("sui", "serving_droid_sui_test_button"));
    public static final String CLOSE = localize(new string_id("sui", "close"));
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
            removeObjVar(house, "serving_droid.droid");
            detachScript(house, "structure.serving_droid_control");
        }
        removeObjVar(terminal, TERMINAL_DROID_ID);
        return SCRIPT_CONTINUE;
    }
    public int OnMovePathComplete(obj_id self) throws InterruptedException
    {
        if (!isValidId(self))
        {
            return SCRIPT_CONTINUE;
        }
        utils.removeScriptVar(self, DROID_MOVING);
        return SCRIPT_CONTINUE;
    }
    public int OnMoveMoving(obj_id self) throws InterruptedException
    {
        if (!isValidId(self))
        {
            return SCRIPT_CONTINUE;
        }
        utils.setScriptVar(self, DROID_MOVING, true);
        return SCRIPT_CONTINUE;
    }
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        if (!isValidId(self))
        {
            return SCRIPT_CONTINUE;
        }
        messageTo(self, "initializeDroidVariables", null, 1, false);
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
            messageTo(terminal, "destroyDroid", null, 1, false);
            return SCRIPT_CONTINUE;
        }
        mi.addRootMenu(menu_info_types.ITEM_ACTIVATE, PATROL);
        mi.addRootMenu(menu_info_types.SERVER_MENU1, HELP);
        int mnuColor = mi.addRootMenu(menu_info_types.SERVER_MENU2, PROGRAM);
        mi.addSubMenu(mnuColor, menu_info_types.SERVER_MENU3, PCOLOR);
        mi.addSubMenu(mnuColor, menu_info_types.SERVER_MENU4, SCOLOR);
        int mnuProgram = mi.addRootMenu(menu_info_types.SERVER_MENU5, PATROL_OPTIONS);
        mi.addSubMenu(mnuProgram, menu_info_types.SERVER_MENU6, SETPATROLPOINT);
        mi.addSubMenu(mnuProgram, menu_info_types.SERVER_MENU7, CLEARPOINTS);
        mi.addSubMenu(mnuProgram, menu_info_types.SERVER_MENU10, CLEAR_LAST);
        mi.addSubMenu(mnuProgram, menu_info_types.SERVER_MENU8, ONCE);
        mi.addSubMenu(mnuProgram, menu_info_types.SERVER_MENU9, LOOP);
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
            messageTo(terminal, "destroyDroid", null, 1, false);
            return SCRIPT_CONTINUE;
        }
        if (player != getObjIdObjVar(terminal, "biolink.id"))
        {
            sendSystemMessage(player, ASTROMECH_NOT_OWNER);
            return SCRIPT_CONTINUE;
        }
        sendDirtyObjectMenuNotification(self);
        if (item == menu_info_types.SERVER_MENU1)
        {
            createHelpDialog(self, player);
        }
        else if (item == menu_info_types.SERVER_MENU3)
        {
            sui.colorize(self, player, self, hue.INDEX_1, "handlePrimaryColorize");
        }
        else if (item == menu_info_types.SERVER_MENU4)
        {
            sui.colorize(self, player, self, hue.INDEX_2, "handleSecondaryColorize");
        }
        else if (item == menu_info_types.SERVER_MENU6)
        {
            doSetPatrolPoint(self, player);
        }
        else if (item == menu_info_types.SERVER_MENU7)
        {
            doClearPatrolPoints(self, player);
        }
        else if (item == menu_info_types.SERVER_MENU8)
        {
            setPatrolOnce(self, player, terminal);
        }
        else if (item == menu_info_types.SERVER_MENU9)
        {
            setPatrolLoop(self, player, terminal);
        }
        else if (item == menu_info_types.SERVER_MENU10)
        {
            removeLastPatrolPoint(self, player);
        }
        else if (item == menu_info_types.ITEM_ACTIVATE)
        {
            if (utils.hasScriptVar(self, DROID_MOVING) && !utils.hasScriptVar(self, DROID_SETTING_PATROL))
            {
                stop(self);
                utils.removeScriptVar(self, DROID_MOVING);
            }
            else 
            {
                doPatrol(self, player);
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int handlePrimaryColorize(obj_id self, dictionary params) throws InterruptedException
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
        int idx = sui.getColorPickerIndex(params);
        int bp = sui.getIntButtonPressed(params);
        if (bp == sui.BP_CANCEL)
        {
            return SCRIPT_CONTINUE;
        }
        if (idx > -1)
        {
            colorPrimary(self, terminal, idx);
        }
        return SCRIPT_CONTINUE;
    }
    public int handleSecondaryColorize(obj_id self, dictionary params) throws InterruptedException
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
        int idx = sui.getColorPickerIndex(params);
        int bp = sui.getIntButtonPressed(params);
        if (bp == sui.BP_CANCEL)
        {
            return SCRIPT_CONTINUE;
        }
        if (idx > -1)
        {
            colorSecondary(self, terminal, idx);
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
        location[] patrolLoc = getLocationArrayObjVar(terminal, OBJVAR_DROID_PATROL_POINTS);
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
        location[] patrolLoc = getLocationArrayObjVar(terminal, OBJVAR_DROID_PATROL_POINTS);
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
    public int handleActivateDroid(obj_id self, dictionary params) throws InterruptedException
    {
        if (!isValidId(self))
        {
            return SCRIPT_CONTINUE;
        }
        doPatrol(self);
        return SCRIPT_CONTINUE;
    }
    public int handleDeactivateDroid(obj_id self, dictionary params) throws InterruptedException
    {
        if (!isValidId(self))
        {
            return SCRIPT_CONTINUE;
        }
        stop(self);
        if (utils.hasScriptVar(self, DROID_MOVING))
        {
            utils.removeScriptVar(self, DROID_MOVING);
        }
        return SCRIPT_CONTINUE;
    }
    public int reColorDroid(obj_id self, dictionary params) throws InterruptedException
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
        if (!hasObjVar(terminal, "color.primary") && !hasObjVar(terminal, "color.secondary"))
        {
            setObjVar(terminal, "color.primary", 0);
            setObjVar(terminal, "color.secondary", 22);
        }
        int primary = getIntObjVar(terminal, "color.primary");
        int secondary = getIntObjVar(terminal, "color.secondary");
        if (primary < 0 || secondary < 0)
        {
            return SCRIPT_CONTINUE;
        }
        hue.setColor(self, "/private/index_color_1", primary);
        hue.setColor(self, "/private/index_color_2", secondary);
        return SCRIPT_CONTINUE;
    }
    public int initializeDroidVariables(obj_id self, dictionary params) throws InterruptedException
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
            messageTo(terminal, "destroyDroid", null, 1, false);
            return SCRIPT_CONTINUE;
        }
        setObjVar(self, "house", house);
        if (!hasObjVar(terminal, OBJVAR_DROID_PATROL_POINTS))
        {
            messageTo(self, "getUserOidSetFirstPoint", null, 1, false);
        }
        if (!hasScript(house, "structure.serving_droid_control"))
        {
            attachScript(house, "structure.serving_droid_control");
        }
        if (!hasObjVar(house, "serving_droid.droid"))
        {
            setObjVar(house, "serving_droid.droid", self);
        }
        if (hasObjVar(terminal, "color.primary"))
        {
            messageTo(self, "reColorDroid", null, 0, false);
        }
        listenToMessage(house, "handleActivateDroid");
        listenToMessage(house, "handleDeactivateDroid");
        return SCRIPT_CONTINUE;
    }
    public int handleDialogInput(obj_id self, dictionary params) throws InterruptedException
    {
        if (!isValidId(self))
        {
            if (utils.hasScriptVar(self, SCRIPTVAR_DROID_HELP_MENU))
            {
                utils.removeScriptVar(self, SCRIPTVAR_DROID_HELP_MENU);
            }
            return SCRIPT_CONTINUE;
        }
        obj_id player = sui.getPlayerId(params);
        if (!isValidId(player))
        {
            if (utils.hasScriptVar(self, SCRIPTVAR_DROID_HELP_MENU))
            {
                utils.removeScriptVar(self, SCRIPTVAR_DROID_HELP_MENU);
            }
            return SCRIPT_CONTINUE;
        }
        switch (sui.getIntButtonPressed(params))
        {
            case sui.BP_OK:
                doPatrol(self, player);
                break;
            case sui.BP_CANCEL:
                utils.removeScriptVar(self, SCRIPTVAR_DROID_HELP_MENU);
                break;
        }
        return SCRIPT_CONTINUE;
    }
    public boolean colorPrimary(obj_id droid, obj_id terminal, int idx) throws InterruptedException
    {
        setObjVar(terminal, "color.primary", idx);
        hue.setColor(droid, "/private/index_color_1", idx);
        return true;
    }
    public boolean colorSecondary(obj_id droid, obj_id terminal, int idx) throws InterruptedException
    {
        setObjVar(terminal, "color.secondary", idx);
        hue.setColor(droid, "/private/index_color_2", idx);
        return true;
    }
    public boolean doPatrol(obj_id droid) throws InterruptedException
    {
        if (!isValidId(droid))
        {
            return false;
        }
        obj_id terminal = getObjIdObjVar(droid, "terminal");
        if (!isValidId(terminal))
        {
            return false;
        }
        location[] patrolLoc = getLocationArrayObjVar(terminal, OBJVAR_DROID_PATROL_POINTS);
        if (patrolLoc == null)
        {
            return false;
        }
        if (hasObjVar(terminal, OBJVAR_DROID_PATROL_LOOP))
        {
            ai_lib.setPatrolPath(droid, patrolLoc);
            return true;
        }
        else 
        {
            patrolOnce(droid, patrolLoc);
            messageTo(droid, "fromLastPointGoBackToStart", null, 120, false);
            return true;
        }
    }
    public boolean doPatrol(obj_id droid, obj_id player) throws InterruptedException
    {
        if (!isValidId(droid) || !isValidId(player))
        {
            return false;
        }
        if (utils.hasScriptVar(droid, SCRIPTVAR_DROID_HELP_MENU))
        {
            utils.removeScriptVar(droid, SCRIPTVAR_DROID_HELP_MENU);
        }
        if (utils.hasScriptVar(droid, DROID_SETTING_PATROL))
        {
            utils.removeScriptVar(droid, DROID_SETTING_PATROL);
        }
        obj_id terminal = getObjIdObjVar(droid, "terminal");
        if (!isValidId(terminal))
        {
            return false;
        }
        if (!hasObjVar(terminal, OBJVAR_DROID_PATROL_POINTS))
        {
            sendSystemMessage(player, ASTROMECH_NO_WAYPOINTS);
            return false;
        }
        location[] patrolLoc = getLocationArrayObjVar(terminal, OBJVAR_DROID_PATROL_POINTS);
        if (patrolLoc == null)
        {
            return false;
        }
        if (hasObjVar(terminal, OBJVAR_DROID_PATROL_LOOP))
        {
            ai_lib.setPatrolPath(droid, patrolLoc);
            return true;
        }
        else 
        {
            patrolOnce(droid, patrolLoc);
            messageTo(droid, "fromLastPointGoBackToStart", null, 120, false);
            return true;
        }
    }
    public void doSetPatrolPoint(obj_id droid, obj_id player) throws InterruptedException
    {
        if (!isValidId(droid) || !isValidId(player))
        {
            return;
        }
        if (utils.hasScriptVar(droid, SCRIPTVAR_DROID_HELP_MENU))
        {
            utils.removeScriptVar(droid, SCRIPTVAR_DROID_HELP_MENU);
        }
        obj_id terminal = getObjIdObjVar(droid, "terminal");
        if (!isValidId(terminal))
        {
            return;
        }
        if (!utils.hasScriptVar(droid, DROID_SETTING_PATROL))
        {
            utils.setScriptVar(droid, DROID_SETTING_PATROL, true);
        }
        location[] patrolLoc = getLocationArrayObjVar(terminal, OBJVAR_DROID_PATROL_POINTS);
        if (patrolLoc == null)
        {
            patrolLoc = new location[1];
        }
        else 
        {
            sendSystemMessage(player, ASTROMECH_MAX_WAYPOINTS);
            return;
        }
        location playerLoc = getLocation(player);
        if (!isValidId(playerLoc.cell))
        {
            sendSystemMessage(player, ASTROMECH_NOT_IN_HOUSE);
            return;
        }
        patrolLoc[patrolLoc.length - 1] = playerLoc;
        setObjVar(terminal, OBJVAR_DROID_PATROL_POINTS, patrolLoc);
        sendSystemMessage(player, SYS_PATROL_ADDED);
        sendConsoleMessage(player, "Patrol points left: " + (MAX_WAYPOINTS - patrolLoc.length));
        pathTo(droid, playerLoc);
    }
    public void doClearPatrolPoints(obj_id droid, obj_id player) throws InterruptedException
    {
        if (!isValidId(droid) || !isValidId(player))
        {
            return;
        }
        obj_id terminal = getObjIdObjVar(droid, "terminal");
        if (!isValidId(terminal))
        {
            return;
        }
        if (hasObjVar(terminal, OBJVAR_DROID_PATROL_POINTS))
        {
            removeObjVar(terminal, OBJVAR_DROID_PATROL_POINTS);
            sendSystemMessage(player, SYS_PATROL_REMOVED);
            ai_lib.clearPatrolPath(droid);
            stop(droid);
            messageTo(droid, "resumeDefaultCalmBehavior", null, 1, false);
        }
        else 
        {
            sendSystemMessage(player, ASTROMECH_WAYPOINTS_NOT_CLEAR);
        }
    }
    public void removeLastPatrolPoint(obj_id droid, obj_id player) throws InterruptedException
    {
        if (!isValidId(droid) || !isValidId(player))
        {
            return;
        }
        obj_id terminal = getObjIdObjVar(droid, "terminal");
        if (!isValidId(terminal))
        {
            return;
        }
        if (hasObjVar(terminal, OBJVAR_DROID_PATROL_POINTS))
        {
            location[] patrolLoc = getLocationArrayObjVar(terminal, OBJVAR_DROID_PATROL_POINTS);
            if (patrolLoc == null)
            {
                sendSystemMessage(player, ASTROMECH_WAYPOINTS_NOT_CLEAR);
            }
            else if (patrolLoc.length > 0)
            {
                setObjVar(terminal, OBJVAR_DROID_PATROL_POINTS, patrolLoc);
                sendSystemMessage(player, SYS_PATROL_ADDED);
                sendConsoleMessage(player, "Patrol points left: " + (MAX_WAYPOINTS - patrolLoc.length));
            }
            location playerLoc = getLocation(player);
            if (isValidId(playerLoc.cell))
            {
                pathTo(droid, playerLoc);
            }
        }
    }
    public boolean setPatrolLoop(obj_id droid, obj_id player, obj_id terminal) throws InterruptedException
    {
        if (!isValidId(droid) || !isValidId(player))
        {
            return false;
        }
        if (!isValidId(terminal) || !isValidId(terminal))
        {
            return false;
        }
        if (!hasObjVar(terminal, OBJVAR_DROID_PATROL_LOOP))
        {
            setObjVar(terminal, OBJVAR_DROID_PATROL_LOOP, true);
            if (hasObjVar(terminal, OBJVAR_DROID_PATROL_ONCE))
            {
                removeObjVar(terminal, OBJVAR_DROID_PATROL_ONCE);
            }
            sendConsoleMessage(player, "Patrol loop setting applied.");
            if (utils.hasScriptVar(droid, DROID_MOVING))
            {
                stop(droid);
                doPatrol(droid, player);
            }
            return true;
        }
        return false;
    }
    public boolean setPatrolOnce(obj_id droid, obj_id player, obj_id terminal) throws InterruptedException
    {
        if (!hasObjVar(terminal, OBJVAR_DROID_PATROL_ONCE))
        {
            setObjVar(terminal, OBJVAR_DROID_PATROL_ONCE, true);
            if (hasObjVar(terminal, OBJVAR_DROID_PATROL_LOOP))
            {
                removeObjVar(terminal, OBJVAR_DROID_PATROL_LOOP);
            }
            sendConsoleMessage(player, "Patrol once only setting applied.");
            if (utils.hasScriptVar(droid, DROID_MOVING))
            {
                stop(droid);
                doPatrol(droid, player);
            }
            return true;
        }
        return false;
    }
    public int createHelpDialog(obj_id droid, obj_id player) throws InterruptedException
    {
        if (utils.hasScriptVar(droid, SCRIPTVAR_DROID_HELP_MENU))
        {
            return -1;
        }
        if (!isIdValid(player))
        {
            return -1;
        }
        if (!isIdValid(droid))
        {
            return -1;
        }
        int pid = sui.createSUIPage(sui.SUI_MSGBOX, droid, player, "handleDialogInput");
        sui.setSUIProperty(pid, sui.MSGBOX_PROMPT, sui.PROP_TEXT, INFO_UI_TEXT);
        sui.setSUIProperty(pid, sui.MSGBOX_TITLE, sui.PROP_TEXT, INFO_UI_TITLE);
        sui.msgboxButtonSetup(pid, sui.OK_CANCEL);
        sui.setSUIProperty(pid, sui.MSGBOX_BTN_CANCEL, sui.PROP_TEXT, CLOSE);
        sui.setSUIProperty(pid, sui.MSGBOX_BTN_OK, sui.PROP_TEXT, TEST_PATROL);
        sui.showSUIPage(pid);
        utils.setScriptVar(droid, SCRIPTVAR_DROID_HELP_MENU, true);
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
        messageTo(terminal, "destroyDroid", null, 1, false);
        return SCRIPT_CONTINUE;
    }
}
