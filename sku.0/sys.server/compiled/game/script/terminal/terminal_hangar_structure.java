package script.terminal;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.hue;
import script.library.instance;
import script.library.player_structure;
import script.library.prose;
import script.library.space_transition;
import script.library.sui;
import script.library.utils;

public class terminal_hangar_structure extends script.base_script
{
    public terminal_hangar_structure()
    {
    }
    public static final String PID_NAME = "hangar";
    public static final String SCRIPT_VAR_SCD_LIST = "hangar.scds";
    public static final String SCRIPT_VAR_RESTORING_SHIP = "hangar.restoring";
    public static final String SCRIPT_VAR_DECORATING_SHIP = "hangar.decorating";
    public static final String TERMINAL_OBJVAR_DECORATED_LIST = "hangar.decoratedShips";
    public static final String SHIP_OBJVAR_DECORATED_SHIP = "hangar.decoratedShip";
    public static final String CUSTOMER_SERVICE_LOG = "hangar";
    public static final int MESSAGE_DELAY = 5;
    public static final int MAX_ITERATIONS = 5;
    public static final int MAX_STORED_SHIPS = 10;
    public static final int MAX_STORED_POBS = 1;
    public static final string_id SID_WHILE_DEAD = new string_id("player_structure", "while_dead");
    public static final string_id SID_TERMINAL_MANAGEMENT = new string_id("player_structure", "hangar_management");
    public static final string_id SID_HANGAR_STORE_SHIPS = new string_id("player_structure", "hangar_store_ships");
    public static final string_id SID_HANGAR_RESTORE_SHIPS = new string_id("player_structure", "hangar_restore_ships");
    public static final string_id SID_INTIALIZING_HANGAR = new string_id("player_structure", "intializing_hangar");
    public static final string_id SID_NO_SHIPS = new string_id("player_structure", "no_ships");
    public static final string_id SID_SHIP_LIST_STORE_PROMPT = new string_id("player_structure", "ship_list_store_prompt");
    public static final string_id SID_SHIP_LIST_RESTORE_PROMPT = new string_id("player_structure", "ship_list_restore_prompt");
    public static final string_id SID_LIST_TITLE = new string_id("player_structure", "list_title");
    public static final string_id SID_HANGAR_COULD_NOT_INTIALIZE = new string_id("player_structure", "hangar_could_not_intialize");
    public static final string_id SID_SHIP_MOVE_STORE_PROMPT = new string_id("player_structure", "move_store_prompt");
    public static final string_id SID_SHIP_MOVE_RESTORE_PROMPT = new string_id("player_structure", "move_restore_prompt");
    public static final string_id SID_SHIP_MOVE_TITLE = new string_id("player_structure", "ship_move_title");
    public static final string_id SID_SHIP_WAS_STORED = new string_id("player_structure", "ship_was_stored");
    public static final string_id SID_SHIP_WAS_RESTORED = new string_id("player_structure", "ship_was_restored");
    public static final string_id SID_TRANSFER_FAILED_GENERIC = new string_id("player_structure", "transfer_failed_generic");
    public static final string_id SID_MAX_SHIPS_STORED = new string_id("player_structure", "max_ships_stored");
    public static final string_id SID_PLACE_SHIP_DECORATION = new string_id("player_structure", "hangar_place_ship_decoration");
    public static final string_id SID_PLACE_SHIP_DECORATION_PROMPT = new string_id("player_structure", "hangar_place_ship_decoration_prompt");
    public static final string_id SID_PLACE_SHIP_DECORATION_SUCCESS = new string_id("player_structure", "hangar_place_ship_decoration_success");
    public static final string_id SID_REMOVE_SHIP_DECORATION_SUCCESS = new string_id("player_structure", "hangar_remove_ship_decoration_success");
    public static final String HANGAR_MINI_SHIPS_TABLE = "datatables/tcg/hangar_mini_ships.iff";
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
            boolean canStoreShips = false;
            boolean canRecallShips = false;
            obj_id[] datapadShipControlDevices = space_transition.findShipControlDevicesForPlayer(player);
            if (datapadShipControlDevices != null && datapadShipControlDevices.length > 0)
            {
                canStoreShips = true;
            }
            obj_id playerHangar = utils.getPlayerHangar(player);
            if (isIdValid(playerHangar) && hasObjVar(player, player_structure.OBJVAR_HANGAR_CREATED))
            {
                obj_id[] shipControlDevices = space_transition.findShipControlDevicesInHangarSlot(player);
                if (shipControlDevices != null && shipControlDevices.length > 0)
                {
                    for (int i = 0; i < shipControlDevices.length; ++i)
                    {
                        obj_id storedShip = shipControlDevices[i];
                        if (isIdValid(storedShip))
                        {
                            if (!hasObjVar(storedShip, player_structure.OBJVAR_STORED_HANGAR_LITE))
                            {
                                canRecallShips = true;
                                break;
                            }
                        }
                    }
                }
            }
            if (canStoreShips || canRecallShips)
            {
                int management_root = mi.addRootMenu(menu_info_types.SERVER_TERMINAL_MANAGEMENT, SID_TERMINAL_MANAGEMENT);
                if (canStoreShips)
                {
                    mi.addSubMenu(management_root, menu_info_types.SERVER_MENU1, SID_HANGAR_STORE_SHIPS);
                }
                if (canRecallShips)
                {
                    mi.addSubMenu(management_root, menu_info_types.SERVER_MENU2, SID_HANGAR_RESTORE_SHIPS);
                    mi.addSubMenu(management_root, menu_info_types.SERVER_MENU3, SID_PLACE_SHIP_DECORATION);
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
            callSuiOfShipsToStore(player, self, playerHangar);
        }
        else if (item == menu_info_types.SERVER_MENU2 && player_structure.isOwner(structure, player))
        {
            callSuiOfStoredShips(player, self, playerHangar);
        }
        else if (item == menu_info_types.SERVER_MENU3 && player_structure.isOwner(structure, player))
        {
            placeShipDecoration(player, self, playerHangar);
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
        Vector storedShipNames = new Vector();
        storedShipNames.setSize(0);
        obj_id[] shipsStored = space_transition.findShipControlDevicesInHangarSlot(player);
        if (shipsStored == null || shipsStored.length <= 0)
        {
            names[idx] = "ship_in_hangar";
            attribs[idx] = "none";
            idx++;
        }
        else 
        {
            for (int i = 0; i < shipsStored.length; ++i)
            {
                obj_id storedShip = shipsStored[i];
                if (isIdValid(storedShip))
                {
                    if (!hasObjVar(storedShip, player_structure.OBJVAR_STORED_HANGAR_LITE))
                    {
                        utils.addElement(storedShipNames, getEncodedName(storedShip));
                    }
                }
            }
            if (storedShipNames == null || storedShipNames.size() <= 0)
            {
                names[idx] = "ship_in_hangar";
                attribs[idx] = "none";
                idx++;
            }
            else 
            {
                for (int j = 0; j < storedShipNames.size(); ++j)
                {
                    names[idx] = "ship_in_hangar";
                    attribs[idx] = ((String)storedShipNames.get(j));
                    idx++;
                }
            }
        }
        return SCRIPT_CONTINUE;
    }
    public void placeShipDecoration(obj_id player, obj_id hangarControls, obj_id playerHangar) throws InterruptedException
    {
        if (!isIdValid(player) || !exists(player) || !isIdValid(hangarControls) || !exists(hangarControls) || !isIdValid(playerHangar) || !exists(playerHangar))
        {
            return;
        }
        obj_id[] shipControlDevices = getContents(playerHangar);
        Vector hangarStoredShips = new Vector();
        hangarStoredShips.setSize(0);
        if (shipControlDevices == null || shipControlDevices.length <= 0)
        {
            sendSystemMessage(player, SID_NO_SHIPS);
            return;
        }
        for (int i = 0; i < shipControlDevices.length; ++i)
        {
            obj_id storedShip = shipControlDevices[i];
            if (isIdValid(storedShip))
            {
                if (!hasObjVar(storedShip, player_structure.OBJVAR_STORED_HANGAR_LITE))
                {
                    utils.addElement(hangarStoredShips, storedShip);
                }
            }
        }
        if (hangarStoredShips == null || hangarStoredShips.size() <= 0)
        {
            sendSystemMessage(player, SID_NO_SHIPS);
            return;
        }
        utils.setScriptVar(hangarControls, SCRIPT_VAR_SCD_LIST, hangarStoredShips);
        String[] localizedList = new String[hangarStoredShips.size()];
        for (int i = 0; i < localizedList.length; ++i)
        {
            localizedList[i] = getEncodedName(((obj_id)hangarStoredShips.get(i)));
            if (hasObjVar(((obj_id)hangarStoredShips.get(i)), SHIP_OBJVAR_DECORATED_SHIP))
            {
                localizedList[i] += " (Placed)";
            }
        }
        utils.setScriptVar(hangarControls, SCRIPT_VAR_DECORATING_SHIP, true);
        int pid = sui.listbox(hangarControls, player, "@" + SID_PLACE_SHIP_DECORATION_PROMPT, sui.OK_CANCEL, "@" + SID_LIST_TITLE, localizedList, "onShipDecorationResponse", true, false);
        sui.setPid(player, pid, PID_NAME);
    }
    public boolean callSuiOfShipsToStore(obj_id player, obj_id terminal, obj_id hangar) throws InterruptedException
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
        obj_id[] shipsInHangar = getContents(hangar);
        Vector hangarStoredShips = new Vector();
        hangarStoredShips.setSize(0);
        if (shipsInHangar != null && shipsInHangar.length > 0)
        {
            for (int i = 0; i < shipsInHangar.length; ++i)
            {
                obj_id storedShip = shipsInHangar[i];
                if (isIdValid(storedShip))
                {
                    if (!hasObjVar(storedShip, player_structure.OBJVAR_STORED_HANGAR_LITE))
                    {
                        utils.addElement(hangarStoredShips, storedShip);
                    }
                }
            }
            if (hangarStoredShips != null && hangarStoredShips.size() >= MAX_STORED_SHIPS)
            {
                sendSystemMessage(player, SID_MAX_SHIPS_STORED);
                return false;
            }
        }
        obj_id[] shipControlDevices = space_transition.findShipControlDevicesForPlayer(player);
        if (shipControlDevices == null || shipControlDevices.length <= 0)
        {
            sendSystemMessage(player, SID_NO_SHIPS);
            return false;
        }
        utils.setScriptVar(terminal, SCRIPT_VAR_SCD_LIST, shipControlDevices);
        String[] localizedList = new String[shipControlDevices.length];
        for (int i = 0; i < localizedList.length; ++i)
        {
            localizedList[i] = getEncodedName(shipControlDevices[i]);
        }
        int pid = sui.listbox(terminal, player, "@" + SID_SHIP_LIST_STORE_PROMPT, sui.OK_CANCEL, "@" + SID_LIST_TITLE, localizedList, "onShipListResponse", true, false);
        sui.setPid(player, pid, PID_NAME);
        return true;
    }
    public boolean callSuiOfStoredShips(obj_id player, obj_id terminal, obj_id hangar) throws InterruptedException
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
        obj_id[] shipControlDevices = getContents(hangar);
        Vector hangarStoredShips = new Vector();
        hangarStoredShips.setSize(0);
        if (shipControlDevices == null || shipControlDevices.length <= 0)
        {
            sendSystemMessage(player, SID_NO_SHIPS);
            return false;
        }
        for (int i = 0; i < shipControlDevices.length; ++i)
        {
            obj_id storedShip = shipControlDevices[i];
            if (isIdValid(storedShip))
            {
                if (!hasObjVar(storedShip, player_structure.OBJVAR_STORED_HANGAR_LITE))
                {
                    utils.addElement(hangarStoredShips, storedShip);
                }
            }
        }
        if (hangarStoredShips == null || hangarStoredShips.size() <= 0)
        {
            sendSystemMessage(player, SID_NO_SHIPS);
            return false;
        }
        utils.setScriptVar(terminal, SCRIPT_VAR_SCD_LIST, hangarStoredShips);
        String[] localizedList = new String[hangarStoredShips.size()];
        for (int i = 0; i < localizedList.length; ++i)
        {
            localizedList[i] = getEncodedName(((obj_id)hangarStoredShips.get(i)));
        }
        utils.setScriptVar(terminal, SCRIPT_VAR_RESTORING_SHIP, true);
        int pid = sui.listbox(terminal, player, "@" + SID_SHIP_LIST_RESTORE_PROMPT, sui.OK_CANCEL, "@" + SID_LIST_TITLE, localizedList, "onShipListResponse", true, false);
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
            callSuiOfShipsToStore(player, self, playerHangar);
        }
        else 
        {
            callSuiOfStoredShips(player, self, playerHangar);
        }
        return SCRIPT_CONTINUE;
    }
    public int onShipDecorationResponse(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id player = params.getObjId("player");
        obj_id playerHangar = utils.getPlayerHangar(player);
        if (!isIdValid(player) || !exists(player) || !isIdValid(self) || !exists(self) || !isIdValid(playerHangar) || !exists(playerHangar))
        {
            return SCRIPT_CONTINUE;
        }
        if (!utils.hasScriptVar(self, SCRIPT_VAR_SCD_LIST))
        {
            utils.removeScriptVar(self, SCRIPT_VAR_DECORATING_SHIP);
            return SCRIPT_CONTINUE;
        }
        int bp = sui.getIntButtonPressed(params);
        int idx = sui.getListboxSelectedRow(params);
        if (bp == sui.BP_CANCEL || idx == -1)
        {
            sui.removePid(player, PID_NAME);
            utils.removeScriptVar(self, SCRIPT_VAR_SCD_LIST);
            utils.removeScriptVar(self, SCRIPT_VAR_DECORATING_SHIP);
            return SCRIPT_CONTINUE;
        }
        int pageId = params.getInt("pageId");
        if (!sui.hasPid(player, PID_NAME))
        {
            utils.removeScriptVar(self, SCRIPT_VAR_SCD_LIST);
            utils.removeScriptVar(self, SCRIPT_VAR_DECORATING_SHIP);
            return SCRIPT_CONTINUE;
        }
        int pid = sui.getPid(player, PID_NAME);
        if (pageId != pid)
        {
            forceCloseSUIPage(pageId);
            forceCloseSUIPage(pid);
            sui.removePid(player, PID_NAME);
            utils.removeScriptVar(self, SCRIPT_VAR_SCD_LIST);
            utils.removeScriptVar(self, SCRIPT_VAR_DECORATING_SHIP);
            return SCRIPT_CONTINUE;
        }
        if (!player_structure.isOwner(self, player))
        {
            sui.removePid(player, PID_NAME);
            utils.removeScriptVar(self, SCRIPT_VAR_SCD_LIST);
            utils.removeScriptVar(self, SCRIPT_VAR_DECORATING_SHIP);
            return SCRIPT_CONTINUE;
        }
        obj_id[] shipList = utils.getObjIdArrayScriptVar(self, SCRIPT_VAR_SCD_LIST);
        if (shipList == null || shipList.length <= 0)
        {
            utils.removeScriptVar(self, SCRIPT_VAR_DECORATING_SHIP);
            return SCRIPT_CONTINUE;
        }
        utils.removeScriptVar(self, SCRIPT_VAR_SCD_LIST);
        obj_id shipSelected = shipList[idx];
        if (!isIdValid(shipSelected) || !exists(shipSelected))
        {
            utils.removeScriptVar(self, SCRIPT_VAR_DECORATING_SHIP);
            return SCRIPT_CONTINUE;
        }
        if (hasObjVar(shipSelected, SHIP_OBJVAR_DECORATED_SHIP))
        {
            obj_id shipDecoration = getObjIdObjVar(shipSelected, SHIP_OBJVAR_DECORATED_SHIP);
            if (!isIdValid(shipDecoration) || !exists(shipDecoration))
            {
                removeObjVar(shipSelected, SHIP_OBJVAR_DECORATED_SHIP);
                return SCRIPT_CONTINUE;
            }
            destroyObject(shipDecoration);
            removeObjVar(shipSelected, SHIP_OBJVAR_DECORATED_SHIP);
            sendSystemMessage(player, SID_REMOVE_SHIP_DECORATION_SUCCESS);
        }
        else 
        {
            obj_id objShip = space_transition.getShipFromShipControlDevice(shipSelected);
            if (!isIdValid(objShip))
            {
                return SCRIPT_CONTINUE;
            }
            String strChassisType = getShipChassisType(objShip);
            dictionary hangarMiniShip = new dictionary();
            hangarMiniShip = dataTableGetRow(HANGAR_MINI_SHIPS_TABLE, strChassisType);
            if (hangarMiniShip == null)
            {
                return SCRIPT_CONTINUE;
            }
            String template = hangarMiniShip.getString("template");
            if (template == null || template.length() <= 0)
            {
                return SCRIPT_CONTINUE;
            }
            location loc = getLocation(player);
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
            if (!houseTemplate.equals("object/building/player/player_house_hangar.iff"))
            {
                return SCRIPT_CONTINUE;
            }
            obj_id toyShip = createObject(template, loc);
            if (isIdValid(toyShip))
            {
                persistObject(toyShip);
                String houseName = getName(house);
                houseName = localize(new string_id(houseName.substring(0, houseName.indexOf(":")), houseName.substring(houseName.indexOf(":") + 1, houseName.length())));
                setObjVar(shipSelected, SHIP_OBJVAR_DECORATED_SHIP, toyShip);
                setObjVar(toyShip, "validHouse", "object/building/player/player_house_hangar.iff");
                setObjVar(toyShip, "validHouseName", houseName);
                attachScript(toyShip, "item.special.nomove_furniture_house_only");
                String index = "/shared_owner/index_texture_1";
                if (strChassisType.equals("player_yt2400"))
                {
                    index = "/private/index_texture_1";
                }
                int intColor0 = hue.getVarColorIndex(objShip, "/shared_owner/index_color_1");
                int intColor1 = hue.getVarColorIndex(objShip, "/shared_owner/index_color_2");
                int intTextureIndex = getRangedIntCustomVarValue(objShip, index);
                if (intColor0 > -1)
                {
                    hue.setColor(toyShip, "/shared_owner/index_color_1", intColor0);
                }
                if (intColor1 > -1)
                {
                    hue.setColor(toyShip, "/shared_owner/index_color_2", intColor1);
                }
                if (intTextureIndex > -1)
                {
                    hue.setRangedIntCustomVar(toyShip, index, intTextureIndex);
                }
                sendSystemMessage(player, SID_PLACE_SHIP_DECORATION_SUCCESS);
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int onShipListResponse(obj_id self, dictionary params) throws InterruptedException
    {
        if (params == null || params.isEmpty())
        {
            utils.removeScriptVar(self, SCRIPT_VAR_RESTORING_SHIP);
            return SCRIPT_CONTINUE;
        }
        obj_id player = sui.getPlayerId(params);
        if (!isIdValid(player))
        {
            utils.removeScriptVar(self, SCRIPT_VAR_RESTORING_SHIP);
            return SCRIPT_CONTINUE;
        }
        if (!utils.hasScriptVar(self, SCRIPT_VAR_SCD_LIST))
        {
            utils.removeScriptVar(self, SCRIPT_VAR_RESTORING_SHIP);
            return SCRIPT_CONTINUE;
        }
        int bp = sui.getIntButtonPressed(params);
        int idx = sui.getListboxSelectedRow(params);
        if (bp == sui.BP_CANCEL || idx == -1)
        {
            sui.removePid(player, PID_NAME);
            utils.removeScriptVar(self, SCRIPT_VAR_SCD_LIST);
            utils.removeScriptVar(self, SCRIPT_VAR_RESTORING_SHIP);
            return SCRIPT_CONTINUE;
        }
        int pageId = params.getInt("pageId");
        if (!sui.hasPid(player, PID_NAME))
        {
            utils.removeScriptVar(self, SCRIPT_VAR_SCD_LIST);
            utils.removeScriptVar(self, SCRIPT_VAR_RESTORING_SHIP);
            return SCRIPT_CONTINUE;
        }
        int pid = sui.getPid(player, PID_NAME);
        if (pageId != pid)
        {
            forceCloseSUIPage(pageId);
            forceCloseSUIPage(pid);
            sui.removePid(player, PID_NAME);
            utils.removeScriptVar(self, SCRIPT_VAR_SCD_LIST);
            utils.removeScriptVar(self, SCRIPT_VAR_RESTORING_SHIP);
            return SCRIPT_CONTINUE;
        }
        if (!player_structure.isOwner(self, player))
        {
            sui.removePid(player, PID_NAME);
            utils.removeScriptVar(self, SCRIPT_VAR_SCD_LIST);
            utils.removeScriptVar(self, SCRIPT_VAR_RESTORING_SHIP);
            CustomerServiceLog(CUSTOMER_SERVICE_LOG, "Player " + getPlayerName(player) + "(" + player + ") did NOT transfer a ship because they are no longer the owner of the structure.");
            return SCRIPT_CONTINUE;
        }
        obj_id[] shipList = utils.getObjIdArrayScriptVar(self, SCRIPT_VAR_SCD_LIST);
        if (shipList == null || shipList.length <= 0)
        {
            utils.removeScriptVar(self, SCRIPT_VAR_RESTORING_SHIP);
            return SCRIPT_CONTINUE;
        }
        utils.removeScriptVar(self, SCRIPT_VAR_SCD_LIST);
        obj_id shipSelected = shipList[idx];
        if (!isIdValid(shipSelected) || !exists(shipSelected))
        {
            utils.removeScriptVar(self, SCRIPT_VAR_RESTORING_SHIP);
            return SCRIPT_CONTINUE;
        }
        utils.setScriptVar(self, SCRIPT_VAR_SCD_LIST, shipSelected);
        CustomerServiceLog(CUSTOMER_SERVICE_LOG, "Player " + getPlayerName(player) + "(" + player + ") chose SCD " + getEncodedName(shipSelected) + "(" + shipSelected + ") as the ship to move.");
        String prompt = "@" + SID_SHIP_MOVE_RESTORE_PROMPT;
        if (!utils.hasScriptVar(self, SCRIPT_VAR_RESTORING_SHIP))
        {
            prompt = "@" + SID_SHIP_MOVE_STORE_PROMPT;
        }
        int newPid = sui.msgbox(self, player, prompt, sui.YES_NO, "@" + SID_SHIP_MOVE_TITLE, "handlerConfirmShipMove");
        sui.setPid(player, newPid, PID_NAME);
        return SCRIPT_CONTINUE;
    }
    public int handlerConfirmShipMove(obj_id self, dictionary params) throws InterruptedException
    {
        if (params == null || params.isEmpty())
        {
            utils.removeScriptVar(self, SCRIPT_VAR_RESTORING_SHIP);
            return SCRIPT_CONTINUE;
        }
        obj_id player = sui.getPlayerId(params);
        if (!isIdValid(player))
        {
            utils.removeScriptVar(self, SCRIPT_VAR_RESTORING_SHIP);
            return SCRIPT_CONTINUE;
        }
        if (!utils.hasScriptVar(self, SCRIPT_VAR_SCD_LIST))
        {
            utils.removeScriptVar(self, SCRIPT_VAR_RESTORING_SHIP);
            return SCRIPT_CONTINUE;
        }
        int bp = sui.getIntButtonPressed(params);
        if (bp == sui.BP_CANCEL)
        {
            sui.removePid(player, PID_NAME);
            utils.removeScriptVar(self, SCRIPT_VAR_SCD_LIST);
            utils.removeScriptVar(self, SCRIPT_VAR_RESTORING_SHIP);
            return SCRIPT_CONTINUE;
        }
        int pageId = params.getInt("pageId");
        if (!sui.hasPid(player, PID_NAME))
        {
            utils.removeScriptVar(self, SCRIPT_VAR_SCD_LIST);
            utils.removeScriptVar(self, SCRIPT_VAR_RESTORING_SHIP);
            return SCRIPT_CONTINUE;
        }
        int pid = sui.getPid(player, PID_NAME);
        if (pageId != pid)
        {
            forceCloseSUIPage(pageId);
            forceCloseSUIPage(pid);
            sui.removePid(player, PID_NAME);
            utils.removeScriptVar(self, SCRIPT_VAR_SCD_LIST);
            utils.removeScriptVar(self, SCRIPT_VAR_RESTORING_SHIP);
            return SCRIPT_CONTINUE;
        }
        if (!player_structure.isOwner(self, player))
        {
            sui.removePid(player, PID_NAME);
            utils.removeScriptVar(self, SCRIPT_VAR_SCD_LIST);
            utils.removeScriptVar(self, SCRIPT_VAR_RESTORING_SHIP);
            CustomerServiceLog(CUSTOMER_SERVICE_LOG, "Player " + getPlayerName(player) + "(" + player + ") did not transfer a ship because they are no longer the owner of the structure.");
            return SCRIPT_CONTINUE;
        }
        obj_id shipSelected = utils.getObjIdScriptVar(self, SCRIPT_VAR_SCD_LIST);
        if (!isIdValid(shipSelected) || !exists(shipSelected))
        {
            utils.removeScriptVar(self, SCRIPT_VAR_RESTORING_SHIP);
            return SCRIPT_CONTINUE;
        }
        utils.removeScriptVar(self, SCRIPT_VAR_SCD_LIST);
        obj_id playerHangar = utils.getPlayerHangar(player);
        obj_id datapad = utils.getPlayerDatapad(player);
        if (!isIdValid(playerHangar))
        {
            utils.removeScriptVar(self, SCRIPT_VAR_RESTORING_SHIP);
            return SCRIPT_CONTINUE;
        }
        if (!isIdValid(datapad))
        {
            utils.removeScriptVar(self, SCRIPT_VAR_RESTORING_SHIP);
            return SCRIPT_CONTINUE;
        }
        if (!utils.hasScriptVar(self, SCRIPT_VAR_RESTORING_SHIP))
        {
            if (putIn(shipSelected, playerHangar, player))
            {
                CustomerServiceLog(CUSTOMER_SERVICE_LOG, "SCD " + getEncodedName(shipSelected) + " (" + shipSelected + ") was transferred to Player " + getPlayerName(player) + "(" + player + ")'s hangar (" + playerHangar + "), using the hangar terminal.");
                prose_package pp = new prose_package();
                prose.setStringId(pp, SID_SHIP_WAS_STORED);
                prose.setTT(pp, getEncodedName(shipSelected));
                sendSystemMessageProse(player, pp);
            }
            else 
            {
                sendSystemMessage(player, SID_TRANSFER_FAILED_GENERIC);
                CustomerServiceLog(CUSTOMER_SERVICE_LOG, "SCD " + getEncodedName(shipSelected) + " (" + shipSelected + ") was NOT transferred to Player " + getPlayerName(player) + "(" + player + ")'s hangar (" + playerHangar + "), the putIn command failed.");
            }
        }
        else 
        {
            if (putIn(shipSelected, datapad, player))
            {
                obj_id shipDecoration = getObjIdObjVar(shipSelected, SHIP_OBJVAR_DECORATED_SHIP);
                if (!isIdValid(shipDecoration) || !exists(shipDecoration))
                {
                    removeObjVar(shipSelected, SHIP_OBJVAR_DECORATED_SHIP);
                }
                else 
                {
                    destroyObject(shipDecoration);
                    removeObjVar(shipSelected, SHIP_OBJVAR_DECORATED_SHIP);
                }
                CustomerServiceLog(CUSTOMER_SERVICE_LOG, "SCD " + getEncodedName(shipSelected) + " (" + shipSelected + ") was transferred back to Player " + getPlayerName(player) + "(" + player + ")'s datapad (" + datapad + "), using the hangar terminal..");
                prose_package pp = new prose_package();
                prose.setStringId(pp, SID_SHIP_WAS_RESTORED);
                prose.setTT(pp, getEncodedName(shipSelected));
                sendSystemMessageProse(player, pp);
            }
            else 
            {
                sendSystemMessage(player, SID_TRANSFER_FAILED_GENERIC);
                CustomerServiceLog(CUSTOMER_SERVICE_LOG, "SCD " + getEncodedName(shipSelected) + " (" + shipSelected + ") was NOT transferred to Player " + getPlayerName(player) + "(" + player + ")'s datapad (" + datapad + "), the putIn command failed.");
            }
            utils.removeScriptVar(self, SCRIPT_VAR_RESTORING_SHIP);
        }
        return SCRIPT_CONTINUE;
    }
}
