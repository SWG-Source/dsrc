package script.terminal;

import script.*;
import script.library.travel;
import script.library.*;

public class terminal_travel_instant extends script.base_script
{
    public terminal_travel_instant()
    {
    }
    public static final string_id SID_TIMEOUT = new string_id("travel", "pickup_timeout");
    public static final string_id SID_LEFT_ME = new string_id("travel", "left_pickup_zone");
    public static final string_id SID_NOT_YOUR_SHIP = new string_id("travel", "not_your_ship");
    public static final String TRIGGER_VOLUME_PICKUP_SHIP = "travel_instant_pickup_interest_range";
    public static final float PICKUP_INTEREST_RADIUS = 64.0f;
    public static final boolean CONST_FLAG_DO_LOGGING = false;
    public static final String ITV_PICKUP_BUFF = "call_for_pickup";
    public static final String PID_VAR = "home_itv_pid";
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        debugLogging("//***// OnInitialize: ", "////>>>> ENTERED. ");
        messageTo(self, "timeOutSelfExpire", null, 59, false);
        return SCRIPT_CONTINUE;
    }
    public int OnObjectMenuRequest(obj_id self, obj_id player, menu_info mi) throws InterruptedException
    {
        obj_id owner = (playerCheck(self, "OnObjectMenuRequest - "));
        if (owner != player)
        {
            sendSystemMessage(player, SID_NOT_YOUR_SHIP);
            return SCRIPT_CONTINUE;
        }
        mi.addRootMenu(menu_info_types.ITEM_USE, new string_id("", ""));
        if(hasObjVar(self, "itv_slave_1")) {
            mi.addRootMenu(menu_info_types.ITEM_USE_OTHER, new string_id("tcg", "travel_locations"));
        }
        return SCRIPT_CONTINUE;
    }
    public int OnObjectMenuSelect(obj_id self, obj_id player, int item) throws InterruptedException
    {
        if (item == menu_info_types.ITEM_USE || item == menu_info_types.ITEM_USE_OTHER) {
            obj_id owner = (playerCheck(self, "OnObjectMenuSelect - "));
            if (owner != player) {
                return SCRIPT_CONTINUE;
            }
            if (getState(player, STATE_RIDING_MOUNT) == 1) {
                pet_lib.doDismountNow(player, true);
            }
            if (item == menu_info_types.ITEM_USE) {
                if (hasObjVar(self, "tcg_itv_home") || hasObjVar(self, "itv_slave_1")) {
                    if (city.isAMayor(player)) {
                        int city_id = getCitizenOfCityId(player);
                        if (cityExists(city_id)) {
                            obj_id city_hall = cityGetCityHall(city_id);
                            if (isIdValid(city_hall)) {
                                dictionary dict = new dictionary();
                                dict.put("requestingObject", self);
                                dict.put("homeOwner", player);
                                messageTo(city_hall, "retrieveHouseCoords", dict, 0.0f, false);
                            } else {
                                sendSystemMessage(player, new string_id("tcg", "no_residence_home_itv"));
                            }
                        }
                        return SCRIPT_CONTINUE;
                    } else if (hasObjVar(player, "residenceHouseId")) {
                        obj_id home = getObjIdObjVar(player, "residenceHouseId");
                        if (isIdValid(home)) {
                            dictionary dict = new dictionary();
                            dict.put("requestingObject", self);
                            dict.put("homeOwner", player);
                            messageTo(home, "retrieveHouseCoords", dict, 0, false);
                        } else {
                            sendSystemMessage(player, new string_id("tcg", "no_residence_home_itv"));
                        }
                    } else {
                        sendSystemMessage(player, new string_id("tcg", "no_residence_home_itv"));
                    }
                    return SCRIPT_CONTINUE;
                } else if (hasObjVar(self, "tcg_itv_location") || hasObjVar(self, "itv_snowspeeder")) {
                    LocationItvOptions(self, player);
                    return SCRIPT_CONTINUE;
                }
            }
            // This section is for "regular" ITV's - Privateer, Royal Ship, etc.
            String planet = getCurrentSceneName();
            String travel_point = "Starfighter";
            int cityId = getCityAtLocation(getLocation(player), 1000);
            debugLogging("//***// OnObjectMenuSelect: ", "////>>>> cityId at player's location is: " + cityId);
            if (cityId != 0) {
                travel_point = cityGetName(cityId);
                debugLogging("//***// OnObjectMenuSelect: ", "////>>>> city name at player's location is: " + travel_point);
            }
            LOG("LOG_CHANNEL", "player ->" + player + " planet ->" + planet + " travel_point ->" + travel_point);
            String config = getConfigSetting("GameServer", "disableTravelSystem");
            if (config != null) {
                if (config.equals("on")) {
                    return SCRIPT_CONTINUE;
                }
            }
            utils.setScriptVar(player, travel.SCRIPT_VAR_TERMINAL, self);
            utils.setScriptVar(player, "instantTravel", true);
            boolean success = enterClientTicketPurchaseMode(player, planet, travel_point, true);
            if (success) {
                utils.setScriptVar(self, "transport", 1);
            }
            return SCRIPT_CONTINUE;
        }
        return SCRIPT_CONTINUE;
    }
    public int OnTriggerVolumeExited(obj_id self, String volumeName, obj_id breacher) throws InterruptedException
    {
        obj_id player = (playerCheck(self, "OnTriggerVolumeEntered - "));
        debugLogging("//***// OnTriggerVolumeExited: ", "////>>>> player is leaving the instant travel terminal behind (the ship). Destroying self");
        sendSystemMessage(player, SID_LEFT_ME);
        messageTo(self, "cleanupShip", null, 0, false);
        return SCRIPT_CONTINUE;
    }
    public void debugLogging(String section, String message) throws InterruptedException
    {
        if (CONST_FLAG_DO_LOGGING)
        {
            LOG("debug/terminal_travel_instant/" + section, message);
        }
    }
    public void pickupObjectTriggerVolumeInitializer(obj_id self) throws InterruptedException
    {
        obj_id player = (playerCheck(self, "pickupObjectTriggerVolumeInitializer"));
        if (!hasTriggerVolume(self, TRIGGER_VOLUME_PICKUP_SHIP))
        {
            debugLogging("//***// pickupObjectTriggerVolumeInitializer: ", "////>>>> created new trigger volume TRIGGER_VOLUME_PICKUP_SHIP");
            createTriggerVolume(TRIGGER_VOLUME_PICKUP_SHIP, PICKUP_INTEREST_RADIUS, false);
            addTriggerVolumeEventSource(TRIGGER_VOLUME_PICKUP_SHIP, player);
        }
    }
    public obj_id playerCheck(obj_id self, String section) throws InterruptedException
    {
        if (utils.hasScriptVar(self, "playerOwner"))
        {
            obj_id player = utils.getObjIdScriptVar(self, "playerOwner");
            if (isIdValid(player))
            {
                return player;
            }
            else 
            {
                debugLogging(section, "////>>>> the player owner scriptvar isn't valid, so destroying self");
            }
        }
        else 
        {
            debugLogging(section, "////>>>> we don't have a player owner scriptvar, so no way to know what to track. Destroying self");
        }
        messageTo(self, "cleanupShip", null, 0, false);
        return null;
    }
    public void LocationItvOptions(obj_id ship, obj_id player) throws InterruptedException
    {
        if (!isIdValid(ship) || !isIdValid(player))
        {
            return;
        }
        if (sui.hasPid(player, PID_VAR))
        {
            int pid = sui.getPid(player, PID_VAR);
            forceCloseSUIPage(pid);
        }
        int availableLocations;
        String itvName;
        String prompt;
        String title;
        if(hasObjVar(ship, "tcg_itv_location")){
            itvName = "travel_tcg";
            prompt = "@tcg:location_itv_d";
            title = "@tcg:location_itv_t";
            availableLocations = 3;
        }
        else if(hasObjVar(ship, "itv_snowspeeder")){
            itvName = "travel_snowspeeder";
            prompt = "@spam:location_snowspeeder_manage_d";
            title = "@spam:snowspeeder_itv_t";
            availableLocations = 2;
        }
        else{
            return;
        }
        String[] main_options = new String[availableLocations + 1];
        main_options[0] = "Manage Locations";
        for(int i = 1; i <= availableLocations; i++){
            if(hasObjVar(player, itvName + ".itv.name." + i)){
                main_options[i] = getStringObjVar(player, itvName + ".itv.name." + i);
            }
            else{
                main_options[i] = "Travel Location " + i;
            }
        }

        int pid = sui.listbox(
                ship,
                player,
                prompt,
                sui.OK_CANCEL,
                title,
                main_options,
                "handleLocationItvOptions",
                true,
                true
        );
        sui.setPid(player, pid, PID_VAR);
    }
    public void sendPlayerToLocation(obj_id player, int idx) throws InterruptedException
    {
        if (!isIdValid(player))
        {
            return;
        }
        obj_id self = getSelf();
        if(hasObjVar(self, "tcg_itv_location")) {
            if (hasObjVar(player, ("travel_tcg.itv.location." + idx)) && hasObjVar(player, ("travel_tcg.itv.scene." + idx))) {
                location travelLoc = getLocationObjVar(player, ("travel_tcg.itv.location." + idx));
                String destPlanet = getStringObjVar(player, ("travel_tcg.itv.scene." + idx));
                warpPlayer(player, destPlanet, travelLoc.x, travelLoc.y, travelLoc.z, null, 0, 0, 0, "", false);
                messageTo(self, "cleanupShip", null, 0.0f, false);
            } else {
                sendSystemMessage(player, new string_id("tcg", "corrupt_itv_location_data"));
            }
        }
        else if(hasObjVar(self, "itv_snowspeeder")){
            if (hasObjVar(player, ("travel_snowspeeder.itv.location." + idx)) && hasObjVar(player, ("travel_snowspeeder.itv.scene." + idx))) {
                location travelLoc = getLocationObjVar(player, ("travel_snowspeeder.itv.location." + idx));
                String destPlanet = getStringObjVar(player, ("travel_snowspeeder.itv.scene." + idx));
                warpPlayer(player, destPlanet, travelLoc.x, travelLoc.y, travelLoc.z, null, 0, 0, 0, "", false);
                messageTo(self, "cleanupShip", null, 0.0f, false);
            } else {
                sendSystemMessage(player, new string_id("tcg", "corrupt_itv_location_data"));
            }
        }
    }
    public boolean canMarkAtLocation(obj_id player) throws InterruptedException
    {
        obj_id playerCurrentMount = getMountId(player);
        if (isIdValid(playerCurrentMount)
                || isIdValid(structure.getContainingBuilding(player))
                || isSpaceScene()
                || space_dungeon.verifyPlayerSession(player))
        {
            return false;
        }
        location here = getLocation(player);
        if (locations.isInCity(here))
        {
            return false;
        }
        region geoCities[] = getRegionsWithGeographicalAtPoint(here, regions.GEO_CITY);
        if (geoCities != null && geoCities.length > 0)
        {
            return false;
        }
        region[] regionList = getRegionsWithPvPAtPoint(here, regions.PVP_REGION_TYPE_ADVANCED);
        if (regionList != null && regionList.length > 0)
        {
            return false;
        }
        String planet = getCurrentSceneName();
        if (planet.startsWith("kashyyyk") || planet.equals("adventure1") || planet.equals("adventure2"))
        {
            return false;
        }
        return !combat.isInCombat(player);
    }
    public int timeOutSelfExpire(obj_id self, dictionary params) throws InterruptedException
    {
        if (!utils.hasScriptVar(self, "playerOwner"))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id player = utils.getObjIdScriptVar(self, "playerOwner");
        if (isIdValid(player))
        {
            sendSystemMessage(player, SID_TIMEOUT);
        }
        return SCRIPT_CONTINUE;
    }
    public int initializeInstaTravelShip(obj_id self, dictionary params) throws InterruptedException
    {
        debugLogging("//***// OnInitialize: ", "////>>>> ENTERED. ");
        pickupObjectTriggerVolumeInitializer(self);
        if (utils.hasScriptVar(self, "playerOwner"))
        {
            obj_id player = utils.getObjIdScriptVar(self, "playerOwner");
            if (!isIdValid(player))
            {
                debugLogging("//// OnInitialize: ", "////>>>> we don't have a valid scriptvar pointing to our owning player, so destroy self.");
                messageTo(self, "cleanupShip", null, 0, false);
            }
        }
        else 
        {
            debugLogging("//// OnInitialize: ", "////>>>> apparently we don't have a playerOwner scriptvar, so destroy self.");
            messageTo(self, "cleanupShip", null, 0, false);
        }
        return SCRIPT_CONTINUE;
    }
    public int cleanupShip(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id player = utils.getObjIdScriptVar(self, "playerOwner");
        if (isIdValid(player))
        {
            buff.removeBuff(player, ITV_PICKUP_BUFF);
        }
        if (sui.hasPid(player, PID_VAR))
        {
            int pid = sui.getPid(player, PID_VAR);
            forceCloseSUIPage(pid);
        }
        destroyObject(self);
        return SCRIPT_CONTINUE;
    }
    public int ownerResidenceLocationResponse(obj_id self, dictionary params) throws InterruptedException
    {
        if (params == null || params.isEmpty())
        {
            messageTo(self, "cleanupShip", null, 0, false);
            return SCRIPT_CONTINUE;
        }
        obj_id player = params.getObjId("homeOwner");
        location homeLocation = params.getLocation("residenceLocation");
        String homePlanet = params.getString("homePlanet");
        if (sui.hasPid(player, PID_VAR))
        {
            int pid = sui.getPid(player, PID_VAR);
            forceCloseSUIPage(pid);
        }
        if (!isIdValid(player) || homeLocation == null || homePlanet == null || homePlanet.equals(""))
        {
            sendSystemMessage(player, new string_id("tcg", "invalid_home_itv_location"));
            messageTo(self, "cleanupShip", null, 0, false);
        }
        else 
        {
            utils.setScriptVar(self, "homeLoc", homeLocation);
            utils.setScriptVar(self, "destPlanet", homePlanet);
            int pid = sui.msgbox(self, player, "@tcg:home_itv_d", sui.OK_CANCEL, "@tcg:home_itv_t", sui.MSG_QUESTION, "sendPlayerToHomeLocation");
            sui.setPid(player, pid, PID_VAR);
        }
        return SCRIPT_CONTINUE;
    }
    public int sendPlayerToHomeLocation(obj_id self, dictionary params) throws InterruptedException
    {
        if (params == null || params.isEmpty())
        {
            messageTo(self, "cleanupShip", null, 0, false);
            return SCRIPT_CONTINUE;
        }
        obj_id player = sui.getPlayerId(params);
        if (sui.hasPid(player, PID_VAR))
        {
            int pid = sui.getPid(player, PID_VAR);
            forceCloseSUIPage(pid);
        }
        int btn = sui.getIntButtonPressed(params);
        if (btn == sui.BP_CANCEL)
        {
            utils.removeScriptVar(self, "homeLoc");
            utils.removeScriptVar(self, "destPlanet");
            messageTo(self, "cleanupShip", null, 0, false);
            return SCRIPT_CONTINUE;
        }
        else 
        {
            location destLoc = utils.getLocationScriptVar(self, "homeLoc");
            String destPlanet = utils.getStringScriptVar(self, "destPlanet");
            warpPlayer(player, destPlanet, destLoc.x, destLoc.y, destLoc.z, null, 0, 0, 0, "", false);
            messageTo(self, "cleanupShip", null, 0, false);
        }
        return SCRIPT_CONTINUE;
    }
    public int handleLocationItvOptions(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id player = sui.getPlayerId(params);
        if (sui.hasPid(player, PID_VAR))
        {
            int pid = sui.getPid(player, PID_VAR);
            forceCloseSUIPage(pid);
        }
        if (!isIdValid(player))
        {
            messageTo(self, "cleanupShip", null, 0, false);
            if (sui.hasPid(player, PID_VAR))
            {
                int pid = sui.getPid(player, PID_VAR);
                forceCloseSUIPage(pid);
            }
            return SCRIPT_CONTINUE;
        }
        int idx = sui.getListboxSelectedRow(params);
        int bp = sui.getIntButtonPressed(params);
        if (bp == sui.BP_CANCEL)
        {
            messageTo(self, "cleanupShip", null, 0, false);
            if (sui.hasPid(player, PID_VAR))
            {
                int pid = sui.getPid(player, PID_VAR);
                forceCloseSUIPage(pid);
            }
            return SCRIPT_CONTINUE;
        }
        if (idx < 0)
        {
            messageTo(self, "cleanupShip", null, 0, false);
            if (sui.hasPid(player, PID_VAR))
            {
                int pid = sui.getPid(player, PID_VAR);
                forceCloseSUIPage(pid);
            }
            return SCRIPT_CONTINUE;
        }
        switch (idx)
        {
            case 0:
                String name = "";
                int size = 0;
                if(hasObjVar(self, "tcg_itv_location")) {
                    name = "travel_tcg";
                    size = 3;
                }
                else if(hasObjVar(self, "itv_snowspeeder")){
                    name = "travel_snowspeeder";
                    size = 2;
                }
                else {
                    break;
                }
                String[] locationITVManageOptions = new String[size];
                for(int i = 0; i < size; i++) {
                    locationITVManageOptions[i] = "Set Location " + (i + 1);
                    if (hasObjVar(player, name + ".itv.name." + (i + 1))) {
                        locationITVManageOptions[i] = "Overwrite: " + getStringObjVar(player, name + ".itv.name." + (i + 1));
                    }
                }
                int pid = sui.listbox(
                        self,
                        player,
                        "@tcg:location_itv_manage_d",
                        sui.OK_CANCEL,
                        "@tcg:location_itv_manage_t",
                        locationITVManageOptions,
                        "handleManageLocationItvOptions",
                        true,
                        true
                );
                sui.setPid(player, pid, PID_VAR);
                break;
            case 1:
            case 2:
            case 3:
                sendPlayerToLocation(player, idx);
                break;
            default:
                messageTo(self, "cleanupShip", null, 0, false);
                break;
        }
        return SCRIPT_CONTINUE;
    }
    public int handleManageLocationItvOptions(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id player = sui.getPlayerId(params);
        if (!isIdValid(player))
        {
            messageTo(self, "cleanupShip", null, 0, false);
            if (sui.hasPid(player, PID_VAR))
            {
                int pid = sui.getPid(player, PID_VAR);
                forceCloseSUIPage(pid);
            }
            return SCRIPT_CONTINUE;
        }
        int idx = sui.getListboxSelectedRow(params);
        int bp = sui.getIntButtonPressed(params);
        if (bp == sui.BP_CANCEL)
        {
            messageTo(self, "cleanupShip", null, 0, false);
            if (sui.hasPid(player, PID_VAR))
            {
                int pid = sui.getPid(player, PID_VAR);
                forceCloseSUIPage(pid);
            }
            return SCRIPT_CONTINUE;
        }
        if (idx < 0)
        {
            messageTo(self, "cleanupShip", null, 0, false);
            if (sui.hasPid(player, PID_VAR))
            {
                int pid = sui.getPid(player, PID_VAR);
                forceCloseSUIPage(pid);
            }
            return SCRIPT_CONTINUE;
        }
        switch (idx)
        {
            case 0:
            if (canMarkAtLocation(player))
            {
                int pid = sui.filteredInputbox(self, player, "@tcg:prompt_itv_location_set", "@tcg:title_itv_location_set", "handleSetItvLocation", "");
                sui.setPid(player, pid, PID_VAR);
                utils.setScriptVar(player, "travel.itv.number", 1);
            }
            else 
            {
                sendSystemMessage(player, new string_id("tcg", "invalid_location_for_location_itv"));
            }
            break;
            case 1:
            if (canMarkAtLocation(player))
            {
                int pid = sui.filteredInputbox(self, player, "@tcg:prompt_itv_location_set", "@tcg:title_itv_location_set", "handleSetItvLocation", "");
                sui.setPid(player, pid, PID_VAR);
                utils.setScriptVar(player, "travel.itv.number", 2);
            }
            else 
            {
                sendSystemMessage(player, new string_id("tcg", "invalid_location_for_location_itv"));
            }
            break;
            case 2:
            if (canMarkAtLocation(player))
            {
                int pid = sui.filteredInputbox(self, player, "@tcg:prompt_itv_location_set", "@tcg:title_itv_location_set", "handleSetItvLocation", "");
                sui.setPid(player, pid, PID_VAR);
                utils.setScriptVar(player, "travel.itv.number", 3);
            }
            else 
            {
                sendSystemMessage(player, new string_id("tcg", "invalid_location_for_location_itv"));
            }
            break;
            default:
            messageTo(self, "cleanupShip", null, 0, false);
            if (sui.hasPid(player, PID_VAR))
            {
                int pid = sui.getPid(player, PID_VAR);
                forceCloseSUIPage(pid);
            }
            break;
        }
        return SCRIPT_CONTINUE;
    }
    public int handleSetItvLocation(obj_id self, dictionary params) throws InterruptedException
    {
        if (params == null || params.isEmpty())
        {
            messageTo(self, "cleanupShip", null, 0, false);
            return SCRIPT_CONTINUE;
        }
        obj_id player = sui.getPlayerId(params);
        if (!isIdValid(player))
        {
            utils.removeScriptVar(player, "travel.itv.number");
            return SCRIPT_CONTINUE;
        }
        int bp = sui.getIntButtonPressed(params);
        if (bp == sui.BP_CANCEL)
        {
            utils.removeScriptVar(player, "travel.itv.number");
            messageTo(self, "cleanupShip", null, 0, false);
            return SCRIPT_CONTINUE;
        }
        String markName = sui.getInputBoxText(params);
        if (markName == null || markName.length() == 0)
        {
            sendSystemMessage(self, new string_id("tcg", "not_valid_location_name"));
            utils.removeScriptVar(player, "travel.itv.number");
            messageTo(self, "cleanupShip", null, 0, false);
            return SCRIPT_CONTINUE;
        }
        if (!utils.hasScriptVar(player, "travel.itv.number"))
        {
            utils.removeScriptVar(player, "travel.itv.number");
            messageTo(self, "cleanupShip", null, 0, false);
            return SCRIPT_CONTINUE;
        }
        int n = utils.getIntScriptVar(player, "travel.itv.number");
        utils.removeScriptVar(player, "travel.itv.number");
        if (canMarkAtLocation(player))
        {
            location markLocation = getLocation(player);
            String markScene = getCurrentSceneName();
            if (canMarkAtLocation(player) && markLocation != null && markScene != null)
            {
                String name = "";
                if(hasObjVar(self, "tcg_itv_location")){
                    name = "travel_tcg";
                }
                else if(hasObjVar(self, "itv_snowspeeder")) {
                    name = "travel_snowspeeder";
                }
                setObjVar(player, name + ".itv.location." + n, markLocation);
                setObjVar(player, name + ".itv.scene." + n, markScene);
                setObjVar(player, name + ".itv.name." + n, markName);
                sendSystemMessage(self, new string_id("tcg", "valid_location_set"));
            }
        }
        else 
        {
            sendSystemMessage(player, new string_id("tcg", "invalid_location_for_location_itv"));
        }
        return SCRIPT_CONTINUE;
    }
}
