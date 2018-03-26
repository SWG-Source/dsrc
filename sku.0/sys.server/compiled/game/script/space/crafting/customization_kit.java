package script.space.crafting;

import script.*;
import script.library.*;

import java.util.Vector;

public class customization_kit extends script.base_script
{
    public customization_kit()
    {
    }
    public static final string_id MNU_COLOR = new string_id("sui", "set_color");
    public static final string_id MNU_COLOR_ONE = new string_id("sui", "color_ship_one");
    public static final string_id MNU_COLOR_TWO = new string_id("sui", "color_ship_two");
    public static final String SCRIPTVAR_SHIP_PID = "ship_customization.pid";
    public static final String SCRIPTVAR_REHUE_ID = "ship_customization.id";
    public static final String STF = "color_kit";
    public static final String BTN_COLOR = "@" + STF + ":btn_color";
    public static final String PICK_A_SHIP_TITLE = "@" + STF + ":pick_a_ship_title";
    public static final String PICK_A_SHIP = "@" + STF + ":pick_a_ship";
    public int OnAttach(obj_id self) throws InterruptedException
    {
        setObjVar(self, "ship_customization.usedOne", 0);
        setObjVar(self, "ship_customization.usedTwo", 0);
        return SCRIPT_CONTINUE;
    }
    public int OnObjectMenuRequest(obj_id self, obj_id player, menu_info mi) throws InterruptedException
    {
        if (!utils.isNestedWithin(self, player))
        {
            return SCRIPT_CONTINUE;
        }
        int mnuColor = mi.addRootMenu(menu_info_types.SERVER_MENU1, MNU_COLOR);
        if (mnuColor > -1 && ((getContainedBy(self) != getOwner(self)) || isGod(player)))
        {
            if (getIntObjVar(self, "ship_customization.usedOne") == 0)
            {
                int mnuColorFrame = mi.addSubMenu(mnuColor, menu_info_types.SERVER_MENU2, MNU_COLOR_ONE);
            }
            if (getIntObjVar(self, "ship_customization.usedTwo") == 0)
            {
                int mnuColorLens = mi.addSubMenu(mnuColor, menu_info_types.SERVER_MENU3, MNU_COLOR_TWO);
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int OnObjectMenuSelect(obj_id self, obj_id player, int item) throws InterruptedException
    {
        if (!utils.isNestedWithin(self, player))
        {
            return SCRIPT_CONTINUE;
        }

        if (item == menu_info_types.SERVER_MENU2)
        {
            if (isSpaceScene())
            {
                sendSystemMessage(player, new string_id(STF, "inspace"));
                return SCRIPT_CONTINUE;
            }
            if (utils.getBooleanScriptVar(self, "paint_kit.inuse"))
            {
                return SCRIPT_CONTINUE;
            }
            utils.setScriptVar(self, "paint_kit.inuse", true);
            obj_id[] shipControlDevices = space_transition.findShipControlDevicesForPlayer(player);
            if (shipControlDevices == null || shipControlDevices.length <= 0)
            {
                utils.removeScriptVar(self, "paint_kit.inuse");
                return SCRIPT_CONTINUE;
            }
            Vector validControlDevices = new Vector();
            validControlDevices.setSize(0);
            for (obj_id shipControlDevice : shipControlDevices) {
                obj_id objShip = space_transition.getShipFromShipControlDevice(shipControlDevice);
                if (space_utils.isShipPaintable(objShip)) {
                    validControlDevices = utils.addElement(validControlDevices, shipControlDevice);
                }
            }
            String entries[] = new String[validControlDevices.size()];
            for (int i = 0; i < validControlDevices.size(); i++)
            {
                entries[i] = getAssignedName(((obj_id)validControlDevices.get(i)));
                if (entries[i] == null || entries[i].equals(""))
                {
                    entries[i] = "@" + getName(((obj_id)validControlDevices.get(i)));
                }
            }
            if (validControlDevices.size() > 0)
            {
                int pid = sui.listbox(self, player, PICK_A_SHIP, sui.OK_CANCEL, PICK_A_SHIP_TITLE, entries, "handleKitColorizeOne", false, false);
                if (pid > -1)
                {
                    utils.setScriptVar(player, "color.scds", validControlDevices);
                    setSUIProperty(pid, sui.LISTBOX_BTN_OK, sui.PROP_TEXT, BTN_COLOR);
                    showSUIPage(pid);
                }
                else 
                {
                    utils.removeScriptVar(self, "paint_kit.inuse");
                    return SCRIPT_CONTINUE;
                }
            }
            else 
            {
                string_id message = new string_id(STF, "ships_not_paintable");
                sendSystemMessage(player, message);
                utils.removeScriptVar(self, "paint_kit.inuse");
            }
        }
        if (item == menu_info_types.SERVER_MENU3)
        {
            if (isSpaceScene())
            {
                sendSystemMessage(player, new string_id(STF, "inspace"));
                return SCRIPT_CONTINUE;
            }
            if (utils.getBooleanScriptVar(self, "paint_kit.inuse"))
            {
                return SCRIPT_CONTINUE;
            }
            utils.setScriptVar(self, "paint_kit.inuse", true);
            obj_id[] shipControlDevices = space_transition.findShipControlDevicesForPlayer(player);
            if (shipControlDevices == null || shipControlDevices.length <= 0)
            {
                utils.removeScriptVar(self, "paint_kit.inuse");
                return SCRIPT_CONTINUE;
            }
            Vector validControlDevices = new Vector();
            validControlDevices.setSize(0);
            for (obj_id shipControlDevice : shipControlDevices) {
                obj_id objShip = space_transition.getShipFromShipControlDevice(shipControlDevice);
                if (space_utils.isShipPaintable(objShip)) {
                    validControlDevices = utils.addElement(validControlDevices, shipControlDevice);
                }
            }
            String entries[] = new String[validControlDevices.size()];
            for (int i = 0; i < validControlDevices.size(); i++)
            {
                entries[i] = getAssignedName(((obj_id)validControlDevices.get(i)));
                if (entries[i] == null || entries[i].equals(""))
                {
                    entries[i] = "@" + getName(((obj_id)validControlDevices.get(i)));
                }
            }
            if (validControlDevices.size() > 0)
            {
                int pid = sui.listbox(self, player, PICK_A_SHIP, sui.OK_CANCEL, PICK_A_SHIP_TITLE, entries, "handleKitColorizeTwo", false, false);
                if (pid > -1)
                {
                    utils.setScriptVar(player, "color.scds", validControlDevices);
                    setSUIProperty(pid, sui.LISTBOX_BTN_OK, sui.PROP_TEXT, BTN_COLOR);
                    showSUIPage(pid);
                }
                else 
                {
                    utils.removeScriptVar(self, "paint_kit.inuse");
                    return SCRIPT_CONTINUE;
                }
            }
            else 
            {
                string_id message = new string_id(STF, "ships_not_paintable");
                sendSystemMessage(player, message);
                utils.removeScriptVar(self, "paint_kit.inuse");
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int handleKitColorizeOne(obj_id self, dictionary params) throws InterruptedException
    {
        int bp = sui.getIntButtonPressed(params);
        int idx = sui.getListboxSelectedRow(params);
        if (bp == sui.BP_CANCEL)
        {
            utils.removeScriptVar(self, "paint_kit.inuse");
            return SCRIPT_CONTINUE;
        }
        else 
        {
            obj_id player = sui.getPlayerId(params);
            if (idx == -1)
            {
                utils.removeScriptVar(self, "paint_kit.inuse");
                return SCRIPT_CONTINUE;
            }
            if (!isIdValid(player))
            {
                utils.removeScriptVar(self, "paint_kit.inuse");
                return SCRIPT_CONTINUE;
            }

            String index = "/shared_owner/index_color_1";
            obj_id[] shipControlDevices = utils.getObjIdArrayScriptVar(player, "color.scds");
            utils.removeScriptVar(player, "color.scds");
            if (shipControlDevices != null && shipControlDevices.length > 0)
            {
                obj_id objShip = space_transition.getShipFromShipControlDevice(shipControlDevices[idx]);
                if (isIdValid(objShip))
                {
                    if (!space_utils.isShipPaintable(objShip))
                    {
                        string_id message = new string_id(STF, "imperial");
                        sendSystemMessage(player, message);
                        utils.removeScriptVar(self, "paint_kit.inuse");
                        return SCRIPT_CONTINUE;
                    }
                    else
                    {
                        utils.setScriptVar(player, "color.shipId", objShip);
                        sui.colorize(self, player, objShip, index, "handleFirstColorize");
                    }
                }
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int handleKitColorizeTwo(obj_id self, dictionary params) throws InterruptedException
    {
        int bp = sui.getIntButtonPressed(params);
        int idx = sui.getListboxSelectedRow(params);
        if (bp == sui.BP_CANCEL)
        {
            utils.removeScriptVar(self, "paint_kit.inuse");
            return SCRIPT_CONTINUE;
        }
        else 
        {
            obj_id player = sui.getPlayerId(params);
            if (idx == -1)
            {
                utils.removeScriptVar(self, "paint_kit.inuse");
                return SCRIPT_CONTINUE;
            }
            if (!isIdValid(player))
            {
                utils.removeScriptVar(self, "paint_kit.inuse");
                return SCRIPT_CONTINUE;
            }
            String index2 = "/shared_owner/index_color_2";
            obj_id[] shipControlDevices = utils.getObjIdArrayScriptVar(player, "color.scds");
            utils.removeScriptVar(player, "color.scds");
            if (shipControlDevices != null && shipControlDevices.length > 0)
            {
                obj_id objShip = space_transition.getShipFromShipControlDevice(shipControlDevices[idx]);
                if (isIdValid(objShip))
                {
                    String chassis = getShipChassisType(objShip);
                    if (
                            chassis.equals("player_basic_tiefighter") ||
                            chassis.equals("player_decimator") ||
                            chassis.equals("player_tieadvanced") ||
                            chassis.equals("player_tieaggressor") ||
                            chassis.equals("player_tiebomber") ||
                            chassis.equals("player_tiefighter") ||
                            chassis.equals("player_tie_in") ||
                            chassis.equals("player_tieinterceptor") ||
                            chassis.equals("player_tie_light_duty") ||
                            chassis.equals("player_tieoppressor") ||
                            chassis.equals("player_tiedefender")
                        )
                    {
                        string_id message = new string_id(STF, "imperial");
                        sendSystemMessage(player, message);
                        utils.removeScriptVar(self, "paint_kit.inuse");
                        return SCRIPT_CONTINUE;
                    }
                    else
                    {
                        utils.setScriptVar(player, "color.shipId", objShip);
                        sui.colorize(self, player, objShip, index2, "handleSecondColorize");
                    }
                }
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int handleFirstColorize(obj_id self, dictionary params) throws InterruptedException
    {
        int idx = sui.getColorPickerIndex(params);
        int bp = sui.getIntButtonPressed(params);
        obj_id player = sui.getPlayerId(params);
        if (bp == sui.BP_CANCEL)
        {
            utils.removeScriptVar(self, "paint_kit.inuse");
            return SCRIPT_OVERRIDE;
        }
        obj_id objShip = utils.getObjIdScriptVar(player, "color.shipId");
        String index = "/shared_owner/index_color_1";
        if (idx > -1)
        {
            if (isIdValid(objShip))
            {
                hue.setColor(objShip, index, idx);
                utils.removeScriptVar(self, "paint_kit.inuse");
                setObjVar(self, "ship_customization.usedOne", 1);
            }
        }
        else 
        {
            utils.removeScriptVar(self, "paint_kit.inuse");
        }
        if (getIntObjVar(self, "ship_customization.usedOne") == 1 && getIntObjVar(self, "ship_customization.usedTwo") == 1)
        {
            destroyObject(self);
        }
        return SCRIPT_CONTINUE;
    }
    public int handleSecondColorize(obj_id self, dictionary params) throws InterruptedException
    {
        int idx = sui.getColorPickerIndex(params);
        int bp = sui.getIntButtonPressed(params);
        obj_id player = sui.getPlayerId(params);
        if (bp == sui.BP_CANCEL)
        {
            utils.removeScriptVar(self, "paint_kit.inuse");
            return SCRIPT_CONTINUE;
        }
        obj_id objShip = utils.getObjIdScriptVar(player, "color.shipId");
        utils.removeScriptVar(player, "color.shipId");
        String index = "/shared_owner/index_color_2";
        if (idx > -1)
        {
            if (isIdValid(objShip))
            {
                hue.setColor(objShip, index, idx);
                utils.removeScriptVar(self, "paint_kit.inuse");
                setObjVar(self, "ship_customization.usedTwo", 1);
            }
        }
        else 
        {
            utils.removeScriptVar(self, "paint_kit.inuse");
        }
        if (getIntObjVar(self, "ship_customization.usedOne") == 1 && getIntObjVar(self, "ship_customization.usedTwo") == 1)
        {
            destroyObject(self);
        }
        return SCRIPT_CONTINUE;
    }
}
