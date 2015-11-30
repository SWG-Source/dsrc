package script.npe;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.utils;
import script.library.sui;
import script.library.npe;

public class npe_instance_transfer_terminal extends script.base_script
{
    public npe_instance_transfer_terminal()
    {
    }
    public static final String NPE_TRANSFER_INITIAL_PROMPT = "@npe:npe_instance_transfer_initial_prompt";
    public static final String NPE_TRANSFER_PLAYER_PROMPT = "@npe:npe_instance_transfer_select_player_prompt";
    public static final String NPE_TRANSFER_FULL_PROMPT = "@npe:npe_instance_transfer_instance_full";
    public static final String NPE_TRANSFER_IN_CORRECT_INSTANCE = "@npe:npe_instance_transfer_in_instance";
    public static final String SCRIPT_VAR_PATH = "npe_instance_transfer_terminal";
    public static final String NPE_INSTANCE_NAME_OBJ_VAR = "npe_instance_name";
    public int OnObjectMenuRequest(obj_id self, obj_id player, menu_info mi) throws InterruptedException
    {
        menu_info_data data = mi.getMenuItemByType(menu_info_types.ITEM_USE);
        if (data != null)
        {
            data.setServerNotify(true);
        }
        else 
        {
            mi.addRootMenu(menu_info_types.ITEM_USE, new string_id("", ""));
        }
        return SCRIPT_CONTINUE;
    }
    public int OnObjectMenuSelect(obj_id self, obj_id player, int item) throws InterruptedException
    {
        if (item == menu_info_types.ITEM_USE)
        {
            startTerminal(player, self);
        }
        return SCRIPT_CONTINUE;
    }
    public int OnClusterWideDataResponse(obj_id self, String manage_name, String name, int request_id, String[] element_name_list, dictionary[] data, int lock_key) throws InterruptedException
    {
        LOG("npe", "npe_shared_instance_transfer_terminal.OnClusterWideDataResponse");
        boolean error = false;
        String strError = "";
        if (!manage_name.equals(npe.DUNGEON_PUBLIC_MANAGER_NAME))
        {
            LOG("npe", "npe_shared_instance_transfer_terminal.OnClusterWideDataResponse -- ignorning manage_name " + manage_name + " because it is not public_instances.");
            error = true;
        }
        if (request_id < 1)
        {
            LOG("npe", "npe_shared_instance_transfer_terminal.OnClusterWideDataResponse -- invalid request_id value of " + request_id);
            error = true;
        }
        if (data == null)
        {
            LOG("npe", "npe_shared_instance_transfer_terminal.OnClusterWideDataResponse -- data is null.");
            error = true;
        }
        if (!utils.hasScriptVar(self, SCRIPT_VAR_PATH + ".player_request_id." + request_id))
        {
            LOG("npe", "npe_shared_instance_transfer_terminal.OnClusterWideDataResponse -- had no request id stored for " + request_id);
            error = true;
        }
        if (error)
        {
            return SCRIPT_CONTINUE;
        }
        obj_id player = utils.getObjIdScriptVar(self, SCRIPT_VAR_PATH + ".player_request_id." + request_id);
        utils.removeScriptVar(self, SCRIPT_VAR_PATH + ".player_request_id." + request_id);
        dictionary players = new dictionary();
        dictionary populations = new dictionary();
        dictionary locations = new dictionary();
        dictionary indices = new dictionary();
        for (int i = 0; i < data.length; ++i)
        {
            if (data[i] != null)
            {
                obj_id building_id = data[i].getObjId("building_id");
                int population_num = data[i].getInt("population_num");
                location world_location = new location(data[i].getFloat("world_location.x"), data[i].getFloat("world_location.y"), data[i].getFloat("world_location.z"));
                String population_string = data[i].getString("population_info");
                Object population_object = utils.unpackObject(population_string.getBytes());
                if (population_object != null && population_object instanceof Vector)
                {
                    Vector population_info = (Vector)population_object;
                    populations.put(building_id.toString(), population_num);
                    locations.put(building_id.toString(), world_location);
                    indices.put(building_id.toString(), i);
                    for (int j = 0; j < population_info.size(); ++j)
                    {
                        String player_name = getPlayerFullName((obj_id)population_info.get(j));
                        players.put(player_name, building_id);
                    }
                }
                else 
                {
                    LOG("npe", "npe_shared_instance_transfer_terminal: got a bad population object from dictionary in clusterwidedata response!");
                }
            }
        }
        utils.setScriptVar(player, SCRIPT_VAR_PATH + ".players", players);
        utils.setScriptVar(player, SCRIPT_VAR_PATH + ".populations", populations);
        utils.setScriptVar(player, SCRIPT_VAR_PATH + ".locations", locations);
        utils.setScriptVar(player, SCRIPT_VAR_PATH + ".indices", indices);
        inputPlayer(player);
        return SCRIPT_CONTINUE;
    }
    public void startTerminal(obj_id player, obj_id self) throws InterruptedException
    {
        cleanScriptVars(player);
        String instance_name = getStringObjVar(self, NPE_INSTANCE_NAME_OBJ_VAR);
        if (instance_name == null)
        {
            LOG("npe", "Can't find an instance name for npe_shared_instance_transfer_terminal - stopping now");
            return;
        }
        int request_id = getClusterWideData(npe.DUNGEON_PUBLIC_MANAGER_NAME, instance_name + "*", false, self);
        utils.setScriptVar(self, SCRIPT_VAR_PATH + ".player_request_id." + request_id, player);
    }
    public void inputPlayer(obj_id player) throws InterruptedException
    {
        obj_id self = getSelf();
        closeOldWindowPid(player);
        dictionary params = new dictionary();
        int pid = sui.inputbox(self, player, NPE_TRANSFER_INITIAL_PROMPT, sui.OK_CANCEL, "", sui.INPUT_NORMAL, null, "inputPlayerNameOk", params);
        setWindowPid(player, pid);
    }
    public int inputPlayerNameOk(obj_id self, dictionary params) throws InterruptedException
    {
        if ((params == null) || (params.isEmpty()))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id player = sui.getPlayerId(params);
        String searchString = sui.getInputBoxText(params);
        int btn = sui.getIntButtonPressed(params);
        if (btn != sui.BP_OK)
        {
            cleanScriptVars(player);
            return SCRIPT_CONTINUE;
        }
        Vector players = new Vector();
        dictionary players_dic = utils.getDictionaryScriptVar(player, SCRIPT_VAR_PATH + ".players");
        if (players_dic == null)
        {
            return SCRIPT_CONTINUE;
        }
        java.util.Enumeration keys = players_dic.keys();
        while (keys.hasMoreElements())
        {
            String key = (String)keys.nextElement();
            if ((key.toLowerCase()).indexOf(searchString.toLowerCase()) != -1)
            {
                players.add(key);
            }
        }
        java.util.Collections.sort(players);
        utils.setScriptVar(player, SCRIPT_VAR_PATH + ".players_subset", (String[])players.toArray(new String[0]));
        selectDestinationPlayer(player);
        return SCRIPT_CONTINUE;
    }
    public void selectDestinationPlayer(obj_id player) throws InterruptedException
    {
        obj_id self = getSelf();
        closeOldWindowPid(player);
        String[] players = utils.getStringArrayScriptVar(player, SCRIPT_VAR_PATH + ".players_subset");
        int pid = sui.listbox(self, player, NPE_TRANSFER_PLAYER_PROMPT, sui.OK_CANCEL, "", players, "selectDestinationPlayerOk", true, false);
        setWindowPid(player, pid);
    }
    public int selectDestinationPlayerOk(obj_id self, dictionary params) throws InterruptedException
    {
        if ((params == null) || (params.isEmpty()))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id player = sui.getPlayerId(params);
        int btn = sui.getIntButtonPressed(params);
        int idx = sui.getListboxSelectedRow(params);
        dictionary players_dic = utils.getDictionaryScriptVar(player, SCRIPT_VAR_PATH + ".players");
        dictionary populations_dic = utils.getDictionaryScriptVar(player, SCRIPT_VAR_PATH + ".populations");
        dictionary locations_dic = utils.getDictionaryScriptVar(player, SCRIPT_VAR_PATH + ".locations");
        dictionary indices_dic = utils.getDictionaryScriptVar(player, SCRIPT_VAR_PATH + ".indices");
        String[] players_subset = utils.getStringArrayScriptVar(player, SCRIPT_VAR_PATH + ".players_subset");
        if (players_dic == null || populations_dic == null || players_subset == null)
        {
            return SCRIPT_CONTINUE;
        }
        if (btn != sui.BP_OK || idx == -1 || idx >= players_subset.length)
        {
            inputPlayer(player);
            return SCRIPT_CONTINUE;
        }
        String selected_player = players_subset[idx];
        obj_id selected_instance = players_dic.getObjId(selected_player);
        obj_id self_instance = players_dic.getObjId(getPlayerFullName(player));
        cleanScriptVars(player);
        if (!isIdValid(selected_instance))
        {
            LOG("npe", "Object id for instance from npe_shared_instance_transfer_terminal is invalid!");
            return SCRIPT_CONTINUE;
        }
        if (populations_dic.getInt(selected_instance.toString()) > npe.getMaxInstancePopulation())
        {
            LOG("npe", "Could not send player to " + selected_instance + " because it is over the hard maximum");
            displayMessage(player, NPE_TRANSFER_FULL_PROMPT);
            return SCRIPT_CONTINUE;
        }
        if (selected_instance == self_instance)
        {
            LOG("npe", "Could not send player to " + selected_instance + " because the destination player is in the same instance as they are");
            displayMessage(player, NPE_TRANSFER_IN_CORRECT_INSTANCE);
            return SCRIPT_CONTINUE;
        }
        String instance_name = getStringObjVar(self, NPE_INSTANCE_NAME_OBJ_VAR);
        if (instance_name == null)
        {
            LOG("npe", "Could not send player to " + selected_instance + " because I don't have an instance name - failing");
            return SCRIPT_CONTINUE;
        }
        location world_location = locations_dic.getLocation(selected_instance.toString());
        int index = indices_dic.getInt(selected_instance.toString());
        closeOldWindowPid(player);
        npe.movePlayerFromInstanceToInstance(player, selected_instance, world_location, instance_name, index);
        return SCRIPT_CONTINUE;
    }
    public void displayMessage(obj_id player, String message) throws InterruptedException
    {
        obj_id self = getSelf();
        closeOldWindowPid(player);
        int pid = sui.msgbox(self, player, message, sui.OK_ONLY, "", sui.MSG_NORMAL, "displayMessageOk");
        setWindowPid(player, pid);
    }
    public int displayMessageOk(obj_id self, dictionary params) throws InterruptedException
    {
        return SCRIPT_CONTINUE;
    }
    public void cleanScriptVars(obj_id player) throws InterruptedException
    {
        utils.removeScriptVarTree(player, SCRIPT_VAR_PATH);
    }
    public void closeOldWindowPid(obj_id player) throws InterruptedException
    {
        if (utils.hasScriptVar(player, SCRIPT_VAR_PATH + ".pid"))
        {
            int oldpid = utils.getIntScriptVar(player, SCRIPT_VAR_PATH + ".pid");
            forceCloseSUIPage(oldpid);
            utils.removeScriptVar(player, SCRIPT_VAR_PATH + ".pid");
        }
    }
    public void setWindowPid(obj_id player, int pid) throws InterruptedException
    {
        if (pid > -1)
        {
            utils.setScriptVar(player, SCRIPT_VAR_PATH + ".pid", pid);
        }
    }
}
