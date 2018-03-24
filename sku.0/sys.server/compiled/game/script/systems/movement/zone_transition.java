package script.systems.movement;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.callable;
import script.library.space_dungeon;
import script.library.sui;
import script.library.utils;
import script.library.pclib;
import script.library.colors_hex;
import script.library.pet_lib;
import script.library.vehicle;
import script.library.transition;

public class zone_transition extends script.base_script
{
    public zone_transition()
    {
    }
    public static final float distanceMax = 6.0f;
    public static final String dataTable = "datatables/travel/zone_transition.iff";
    public static final String STF = "travel/zone_transition";
    public static final int LIGHT = 20;
    public static final int MEDIUM = 40;
    public static final int HEAVY = 60;
    public static final int FULL = 80;
    public static final string_id LIGHT_STRING = new string_id(STF, "population_light");
    public static final string_id MEDIUM_STRING = new string_id(STF, "population_medium");
    public static final string_id HEAVY_STRING = new string_id(STF, "population_heavy");
    public static final string_id FULL_STRING = new string_id(STF, "population_full");
    public static final string_id zone_unavailable = new string_id(STF, "zone_unavailable");
    public static final string_id bad_zone_data = new string_id(STF, "bad_zone_data");
    public static final string_id invalid_travel = new string_id(STF, "invalid_travel");
    public static final boolean doLogging = false;
    public int OnAttach(obj_id self) throws InterruptedException
    {
        if (hasScript(self, "item.microphone_and_speaker.speaker"))
        {
            detachScript(self, "item.microphone_and_speaker.speaker");
        }
        return SCRIPT_CONTINUE;
    }
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        if (hasScript(self, "item.microphone_and_speaker.speaker"))
        {
            detachScript(self, "item.microphone_and_speaker.speaker");
        }
        return SCRIPT_CONTINUE;
    }
    public int OnObjectMenuRequest(obj_id self, obj_id player, menu_info item) throws InterruptedException
    {
        if (!isMob(self))
        {
            item.addRootMenu(menu_info_types.ITEM_USE, transition.getZoneTransitionString(self));
        }
        return SCRIPT_CONTINUE;
    }
    public int OnObjectMenuSelect(obj_id self, obj_id player, int item) throws InterruptedException
    {
        if (item == menu_info_types.ITEM_USE)
        {
            utils.dismountRiderJetpackCheck(player);
            transition.zonePlayer(self, player);
            utils.dismountRiderJetpackCheck(player);
        }
        return SCRIPT_CONTINUE;
    }
    public int OnClusterWideDataResponse(obj_id self, String manage_name, String name, int request_id, String[] element_name_list, dictionary[] data, int lock_key) throws InterruptedException
    {
        String transit = getStringObjVar(self, "zoneLine");
        Vector building_ids = new Vector();
        building_ids.setSize(0);
        Vector zone_population = new Vector();
        zone_population.setSize(0);
        String destination = dataTableGetString(dataTable, transit, "destination");
        String[] parse = split(destination, ':');
        for (int i = 0; i < data.length; i++)
        {
            dictionary dungeon = data[i];
            building_ids = utils.addElement(building_ids, dungeon.getObjId("building_id"));
            zone_population = utils.addElement(zone_population, dungeon.getInt("population"));
        }
        if (parse.length < 1 || data.length < 1)
        {
            obj_id player = utils.getObjIdScriptVar(self, "playerId");
            sendSystemMessage(player, zone_unavailable);
            releaseClusterWideDataLock(manage_name, lock_key);
            return SCRIPT_CONTINUE;
        }
        doLogging("OnCWDResponse", "transit, destination, parse[0], parse[1], parse[2]");
        doLogging("OnCWDResponse", transit + ", " + destination + ", " + parse[0] + ", " + parse[1] + ", " + parse[2]);
        doLogging("OnCWDResponse", "building_ids[0] = " + ((obj_id)building_ids.get(0)));
        releaseClusterWideDataLock(manage_name, lock_key);
        handleListBoxZoneSelect(self, parse, building_ids, zone_population);
        return SCRIPT_CONTINUE;
    }
    public void handleListBoxZoneSelect(obj_id self, String[] parsedList, Vector building_ids, Vector zone_population) throws InterruptedException
    {
        obj_id player = utils.getObjIdScriptVar(self, "playerId");
        String[] zoneList = new String[building_ids.size()];
        int k = 1;
        for (int i = 0; i < building_ids.size(); i++)
        {
            zoneList[i] = "@" + transition.getZoneTransitionString(getSelf()) + " " + k + " - " + getColorCodedPopIndex(((obj_id)building_ids.get(i)), ((Integer)zone_population.get(i)).intValue());
            k++;
        }
        if (building_ids != null && building_ids.size() > 0)
        {
            for (int q = 0; q < building_ids.size(); q++)
            {
                doLogging("handleListBoxZoneSelect", "building_ids[" + q + "] = " + ((obj_id)building_ids.get(q)));
            }
        }
        utils.setScriptVar(player, "zoneTransition", parsedList);
        utils.setScriptVar(player, "building_ids", building_ids);
        if (zoneList.length == 1)
        {
            dictionary webster = new dictionary();
            webster.put("player", player);
            webster.put(sui.LISTBOX_LIST + "." + sui.PROP_SELECTEDROW, "0");
            messageTo(self, "handleSelectDestinationZone", webster, 1, false);
            utils.setScriptVar(player, "suiListPage", -1);
        }
        else 
        {
            int pid = sui.listbox(self, player, "Select Zone Instance", zoneList, "handleSelectDestinationZone");
            showSUIPage(pid);
            utils.setScriptVar(player, "suiListPage", pid);
        }
    }
    public String getColorCodedPopIndex(obj_id controller, int population) throws InterruptedException
    {
        if (population < HEAVY)
        {
            if (population < MEDIUM)
            {
                if (population < LIGHT)
                {
                    return "\\" + colors_hex.LIMEGREEN + "Light" + "\\#";
                }
                return "\\" + colors_hex.WHITE + "Medium" + "\\#";
            }
            return "\\" + colors_hex.YELLOW + "Heavy" + "\\#";
        }
        return "\\" + colors_hex.RED + "Full" + "\\#";
    }
    public int handleSelectDestinationZone(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id player = sui.getPlayerId(params);
        int idx = sui.getListboxSelectedRow(params);
        int sui_id = utils.getIntScriptVar(player, "suiListPage");
        doLogging("handleSelectDestinationZone", "Player(" + player + ") selected row(" + idx + ")");
        if (idx == -1)
        {
            doLogging("handleSelectDestinationZone", "Zone select was empty(-1)");
            return SCRIPT_CONTINUE;
        }
        String[] parsed_list = utils.getStringArrayScriptVar(player, "zoneTransition");
        Vector building_ids = utils.getResizeableObjIdArrayScriptVar(player, "building_ids");
        String world = parsed_list[2];
        String cell = parsed_list[3];
        float locX = utils.stringToFloat(parsed_list[4]);
        float locY = utils.stringToFloat(parsed_list[5]);
        float locZ = utils.stringToFloat(parsed_list[6]);
        transition.cleanupTempAccessFlag(player);
        space_dungeon.setInstanceControllerIdOnPlayer(player, ((obj_id)building_ids.get(idx)));
        doLogging("handleSelectDestinationZone", "Warping player to object(" + ((obj_id)building_ids.get(idx)) + ")");
        doLogging("handleSelectDestinationZone", "Warp data from table: World/Cell/LocX/LocY/LocZ");
        doLogging("handleSelectDestinationZone", world + "/" + cell + "/" + locX + "/" + locY + "/" + locZ);
        if (cell.equals("nocell"))
        {
            dictionary dict = new dictionary();
            dict.put("return_address", self);
            dict.put("player", player);
            doLogging("handleSelectDestinationZone", "Params put self and player: " + self + ", " + player);
            messageTo(((obj_id)building_ids.get(idx)), "getTargetCoordinates", dict, 1, false);
        }
        else 
        {
            callable.storeCallables(player);
            utils.dismountRiderJetpackCheck(player);
            warpPlayer(player, world, locX, locY, locZ, ((obj_id)building_ids.get(idx)), cell, locX, locY, locZ);
        }
        return SCRIPT_CONTINUE;
    }
    public int recievedTargetCoordinates(obj_id self, dictionary params) throws InterruptedException
    {
        location parent = params.getLocation("location");
        obj_id player = params.getObjId("player");
        String[] parsed_list = utils.getStringArrayScriptVar(player, "zoneTransition");
        try {
            String world = parsed_list[2];
            float locX = utils.stringToFloat(parsed_list[4]);
            float locY = utils.stringToFloat(parsed_list[5]);
            float locZ = utils.stringToFloat(parsed_list[6]);
            locX += parent.x;
            locY += parent.y;
            locZ += parent.z;
            callable.storeCallables(player);
            utils.dismountRiderJetpackCheck(player);
            warpPlayer(player, world, locX, locY, locZ, null, locX, locY, locZ);
        }
        catch(Exception e){
            String scene = "";
            if(parent != null) scene = parent.area;
            // if zone transition cannot be found or is invalid.
            LOG("zone_transition", "ERROR: Unable to get zoneTransition script var (" + (parsed_list != null ? parsed_list.toString() : "") + ") off player (" + player + ":" + getName(player) + ") located at (" + scene + ":" + parent + ").");
            Thread.dumpStack();
        }
        finally{
            return SCRIPT_CONTINUE;
        }
    }
    public void doLogging(String section, String message) throws InterruptedException
    {
        if (doLogging)
        {
            LOG("debug/zone_transition/" + section, message);
        }
    }
    public int handleZoneTransitionRequest(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id player = params.getObjId("player");
        boolean returnToKachirhoGate = params.getBoolean("zoneIn");
        if (returnToKachirhoGate)
        {
            String levelWarp = "empty";
            levelWarp = params.getString("destination");
            LOG("debug", "levelWarp was: " + levelWarp);
            if (levelWarp.equals("empty") || levelWarp == null)
            {
                String startingLoc = getStringObjVar(self, "startingLocation");
                String destination = dataTableGetString(dataTable, startingLoc, "destination");
                String[] parse = split(destination, ':');
                transition.doZoneToWorld(player, parse);
            }
            else 
            {
                String destination = dataTableGetString(dataTable, levelWarp, "destination");
                String[] parse = split(destination, ':');
                LOG("debug", "parsing location to warp offset to pass");
                LOG("debug", parse[0] + ", " + parse[1] + ", " + parse[2] + ", " + parse[3]);
                transition.doZoneToWorld(player, parse);
            }
        }
        else 
        {
            transition.zonePlayer(self, player);
        }
        return SCRIPT_CONTINUE;
    }
}
