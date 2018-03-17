package script.space.crafting;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.utils;
import script.library.hue;
import script.library.sui;
import script.library.space_utils;
import script.library.space_transition;

public class texture_kit extends script.base_script
{
    public texture_kit()
    {
    }
    public static final String STF = "texture_kit";
    public static final string_id MNU_TEXTURE = new string_id("sui", "set_texture");
    public static final String BTN_TEXTURE = "@" + STF + ":btn_texture";
    public static final String PICK_A_SHIP_TITLE = "@" + STF + ":pick_a_ship_title";
    public static final String PICK_A_SHIP = "@" + STF + ":pick_a_ship";
    public int OnAttach(obj_id self) throws InterruptedException
    {
        return SCRIPT_CONTINUE;
    }
    public int OnObjectMenuRequest(obj_id self, obj_id player, menu_info mi) throws InterruptedException
    {
        if (!utils.isNestedWithin(self, player))
        {
            return SCRIPT_CONTINUE;
        }
        int mnuTexture = mi.addRootMenu(menu_info_types.SERVER_MENU1, MNU_TEXTURE);
        if (mnuTexture > -1 && ((getContainedBy(self) != getOwner(self)) || isGod(player)))
        {
            String template = utils.getTemplateFilenameNoPath(self);
        }
        return SCRIPT_CONTINUE;
    }
    public int OnObjectMenuSelect(obj_id self, obj_id player, int item) throws InterruptedException
    {
        if (!utils.isNestedWithin(self, player))
        {
            return SCRIPT_CONTINUE;
        }
        String template = utils.getTemplateFilenameNoPath(self);
        if (item == menu_info_types.SERVER_MENU1)
        {
            if (isSpaceScene() == true)
            {
                sendSystemMessage(player, new string_id(STF, "inspace"));
                return SCRIPT_CONTINUE;
            }
            if (utils.getBooleanScriptVar(self, "paint_kit.inuse") == true)
            {
                return SCRIPT_CONTINUE;
            }
            utils.setScriptVar(self, "paint_kit.inuse", true);
            obj_id[] shipControlDevices = space_transition.findShipControlDevicesForPlayer(player);
            if (shipControlDevices == null || shipControlDevices.length <= 0)
            {
                return SCRIPT_CONTINUE;
            }
            Vector validControlDevices = new Vector();
            validControlDevices.setSize(0);
            for (int i = 0; i < shipControlDevices.length; i++)
            {
                obj_id objShip = space_transition.getShipFromShipControlDevice(shipControlDevices[i]);
                if (space_utils.isShipTextureable(objShip))
                {
                    validControlDevices = utils.addElement(validControlDevices, shipControlDevices[i]);
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
            if (validControlDevices != null && validControlDevices.size() > 0)
            {
                int pid = sui.listbox(self, player, PICK_A_SHIP, sui.OK_CANCEL, PICK_A_SHIP_TITLE, entries, "handleTextureShip", false, false);
                if (pid > -1)
                {
                    utils.setScriptVar(player, "texture.scds", validControlDevices);
                    setSUIProperty(pid, sui.LISTBOX_BTN_OK, sui.PROP_TEXT, BTN_TEXTURE);
                    showSUIPage(pid);
                }
                else 
                {
                    utils.removeScriptVar(self, "paint_kit.inuse");
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
    public int handleTextureShip(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id player = sui.getPlayerId(params);
        int texture = getIntObjVar(self, "texture_style");
        int idx = sui.getListboxSelectedRow(params);
        int bp = sui.getIntButtonPressed(params);
        if (idx < 0 || idx > 5)
        {
            utils.removeScriptVar(self, "paint_kit.inuse");
            return SCRIPT_CONTINUE;
        }
        if (bp == sui.BP_CANCEL)
        {
            utils.removeScriptVar(self, "paint_kit.inuse");
            return SCRIPT_CONTINUE;
        }
        obj_id[] devices = utils.getObjIdArrayScriptVar(player, "texture.scds");
        utils.removeScriptVar(player, "texture.scds");
        obj_id shipId = space_transition.getShipFromShipControlDevice(devices[idx]);
        if (!isIdValid(shipId))
        {
            return SCRIPT_CONTINUE;
        }
        String chassis = getShipChassisType(shipId);
        if (!space_utils.isShipTextureable(shipId))
        {
            string_id message = new string_id(STF, "imperial");
            sendSystemMessage(player, message);
            utils.removeScriptVar(self, "paint_kit.inuse");
            return SCRIPT_CONTINUE;
        }
        if (hasObjVar(self, "texture_ship"))
        {
            String ship = getStringObjVar(self, "texture_ship");
            if (ship.equals(chassis) || (ship.equals("player_xwing") && chassis.equals("player_advanced_xwing")))
            {
                if (utils.getContainingPlayer(shipId) == player && isIdValid(shipId))
                {
                    String index = "/shared_owner/index_texture_1";
                    hue.setRangedIntCustomVar(shipId, index, texture);
                    string_id message = new string_id(STF, "changed_paint_job");
                    sendSystemMessage(player, message);
                    destroyObject(self);
                    utils.removeScriptVar(self, "paint_kit.inuse");
                }
                return SCRIPT_CONTINUE;
            }
            else 
            {
                string_id message = new string_id(STF, "cantpaint");
                sendSystemMessage(player, message);
                utils.removeScriptVar(self, "paint_kit.inuse");
                return SCRIPT_CONTINUE;
            }
        }
        if (utils.getContainingPlayer(shipId) == player && isIdValid(shipId))
        {
            String index = "/shared_owner/index_texture_1";
            if (chassis.equals("player_yt2400"))
            {
                index = "/private/index_texture_1";
            }
            hue.setRangedIntCustomVar(shipId, index, texture);
            string_id message = new string_id(STF, "changed_paint_job");
            sendSystemMessage(player, message);
            destroyObject(self);
        }
        return SCRIPT_CONTINUE;
    }
}
