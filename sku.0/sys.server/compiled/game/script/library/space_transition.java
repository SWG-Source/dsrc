package script.library;

import script.*;

import java.util.Vector;

public class space_transition extends script.base_script
{
    public space_transition()
    {
    }
    public static final boolean debugSpaceTransition = true;
    public static final String DATATABLE_SHIP_START_LOCATIONS = "datatables/ship/ship_start_locations.iff";
    public static final String COLUMN_TEMPLATE = "template";
    public static final String COLUMN_SLOT = "slot";
    public static final String COLUMN_CELL = "cell";
    public static final String COLUMN_X = "x";
    public static final String COLUMN_Y = "y";
    public static final String COLUMN_Z = "z";
    public static final int NEWBIE_CONVO_DELAY = 60;
    public static final int SHIP_LIMIT_EXPANSION = 6;
    public static final int SHIP_LIMIT_MAX = 3;
    public static final float STATION_COMM_MAX_DISTANCE = 750.0f;
    public static final String POB_SHIP_PILOT_SLOT_NAME = "ship_pilot_pob";
    public static final String SHIP_PILOT_SLOT_NAME = "ship_pilot";
    public static final string_id SID_PVP_NOW_OVERT = new string_id("space/space_interaction", "pvp_now_overt");
    public static final string_id SID_PVP_NOW_NEUTRAL = new string_id("space/space_interaction", "pvp_now_neutral");
    public static void handlePotentialSceneChange(obj_id player) throws InterruptedException
    {
        if (utils.hasLocalVar(player, "loggingOut"))
        {
            return;
        }
        obj_id containingShip = getContainingShip(player);
        LIVE_LOG("TeleportFixup", "containingShip is " + containingShip);
        if (isSpaceScene())
        {
            if (isIdValid(containingShip))
            {
                adjustShipTeleportFixupInSpaceScene(containingShip);
                if (getOwner(containingShip) == player)
                {
                    updateShipFaction(containingShip, player);
                }
                doAIImmunityCheck(containingShip);
                return;
            }
            obj_id launchedShip = getObjIdObjVar(player, "space.launch.ship");
            int launchedShipStartIndex = getIntObjVar(player, "space.launch.startIndex") - 1;
            if ((!isGod(player) || (!utils.checkConfigFlag("ScriptFlags", "e3Demo")) && shouldSendToGroundOnLogout()))
            {
                removeObjVar(player, "space.launch.ship");
                removeObjVar(player, "space.launch.startIndex");
            }
            if (isIdValid(launchedShip) && launchedShip.isLoaded())
            {
                LOG("space", "checking " + launchedShip);
                obj_id shipContainer = getContainedBy(launchedShip);
                LOG("space", "2 shipContainer is " + shipContainer);
                if (!isIdValid(shipContainer))
                {
                    LOG("space", "shipContainer is " + shipContainer);
                    Vector startLocations = getShipStartLocations(launchedShip);
                    if (startLocations != null && startLocations.size() > 0)
                    {
                        if (launchedShipStartIndex < 0)
                        {
                            launchedShipStartIndex = 0;
                        }
                        location loc = ((location)startLocations.get(launchedShipStartIndex % startLocations.size()));
                        if (loc.cell == null)
                        {
                            boolean success = equip(player, launchedShip, loc.area);
                            if (success)
                            {
                                if (debugSpaceTransition)
                                {
                                    LOG("space_transition", "Player [" + player + "] in space, going to an already unpacked ship [" + launchedShip + "] in station [" + loc.area + "] succeeded.");
                                }
                                return;
                            }
                            else if (debugSpaceTransition)
                            {
                                LOG("space_transition", "Player [" + player + "] in space, going to an already unpacked ship [" + launchedShip + "] in station [" + loc.area + "] failed.");
                            }
                        }
                        else
                        {
                            if (debugSpaceTransition)
                            {
                                LOG("space_transition", "Player [" + player + "] in space, going to an already unpacked ship [" + launchedShip + "] at location [" + loc + "].");
                            }
                            setLocation(player, loc);
                            return;
                        }
                    }
                    else if (hasObjVar(player, space_dungeon.VAR_TICKET_DUNGEON) && space_dungeon.isSpaceDungeon(launchedShip) && space_dungeon.getDungeonIdForPlayer(player) == launchedShip)
                    {
                        String dungeon = getStringObjVar(player, space_dungeon.VAR_TICKET_DUNGEON);
                        String cellName = space_dungeon_data.getDungeonStartCellName(dungeon);
                        location start = space_dungeon_data.getDungeonStartLocation(dungeon);
                        obj_id cell = getCellId(launchedShip, cellName);
                        start.cell = cell;
                        LOG("space_transition", "Sending player " + player + " to dungeon " + dungeon + " " + launchedShip);
                        setLocation(player, start);
                        return;
                    }
                    else if (debugSpaceTransition)
                    {
                        LOG("space_transition", "Player [" + player + "] in space, going to an already unpacked ship [" + launchedShip + "] but could not find a start location.");
                    }
                }
                else
                {
                    if (getOwner(launchedShip) == player && getContainedBy(getContainedBy(launchedShip)) == utils.getDatapad(player))
                    {
                        if (debugSpaceTransition)
                        {
                            LOG("space_transition", "Player [" + player + "] in space, going to his own packed ship [" + launchedShip + "].");
                        }
                        if (unpackShipForPlayer(player, launchedShip))
                        {
                            return;
                        }
                    }
                }
            }
            if (debugSpaceTransition)
            {
                if (isIdValid(launchedShip))
                {
                    LOG("space_transition", "Player " + player + " could not enter ship in space, sending to launch point. Ship(" + launchedShip + ") isValid(" + isIdValid(launchedShip) + ") isLoaded(" + launchedShip.isLoaded() + ")");
                }
                else
                {
                    LOG("space_transition", "Player " + player + " could not enter ship in space, sending to launch point. Ship(null)");
                }
                String scripts = "";
                String[] scriptArray = getScriptList(player);
                for (String s : scriptArray) {
                    scripts += (s + "|");
                }
                LOG("space_transition", "Detailed player info: objvars: " + getPackedObjvars(player) + " scripts: " + scripts);
            }
            teleportPlayerToLaunchLoc(player);
        }
        else
        {
            removeObjVar(player, "space.launch");
            if (isIdValid(containingShip))
            {
                if (debugSpaceTransition)
                {
                    LIVE_LOG("TeleportFixup", "Player " + player + " in a ship but not in space, packing the ship.");
                }
                packShip(containingShip);
            }
        }
    }
    public static void handleLogout(obj_id player) throws InterruptedException
    {
        obj_id containingShip = getContainingShip(player);
        if (isIdValid(containingShip))
        {
            if (debugSpaceTransition)
            {
                LOG("space_transition", "player " + player + " logging out in ship " + containingShip);
            }
            obj_id owner = getOwner(containingShip);
            if (owner == player)
            {
                utils.setLocalVar(player, "loggingOut", true);
                packShip(containingShip);
                utils.removeLocalVar(player, "loggingOut");
            }
            if (!isGod(player))
            {
                if (player == getPilotId(containingShip))
                {
                    unpilotShip(player);
                }
                if (player != owner || shouldSendToGroundOnLogout())
                {
                    teleportPlayerToLaunchLoc(player);
                }
            }
        }
    }
    public static boolean shouldSendToGroundOnLogout() throws InterruptedException
    {
        String s = getConfigSetting("ScriptFlags", "sendToGroundOnLogout");
        if (s != null && (s.equals("false") || s.equals("0") || s.equals("off")))
        {
            return false;
        }
        return true;
    }
    public static void setLaunchInfo(obj_id player, obj_id ship, int startLocationIndex, location groundLoc) throws InterruptedException
    {
        setObjVar(player, "space.launch.worldLoc", groundLoc);
        setObjVar(player, "space.launch.ship", ship);
        setObjVar(player, "space.launch.startIndex", startLocationIndex);
    }
    public static obj_id getContainingShip(obj_id obj) throws InterruptedException
    {
        obj_id containedBy = getContainedBy(obj);
        while (isIdValid(containedBy))
        {
            if (isGameObjectTypeOf(getGameObjectType(containedBy), GOT_ship))
            {
                return containedBy;
            }
            containedBy = getContainedBy(containedBy);
        }
        return null;
    }
    public static obj_id[] findShipControlDevicesForPlayer(obj_id player) throws InterruptedException
    {
        return findShipControlDevicesForPlayer(player, false);
    }
    public static obj_id[] findShipControlDevicesForPlayer(obj_id player, boolean checkHangar) throws InterruptedException
    {
        obj_id datapad = utils.getDatapad(player);
        obj_id playerHangar = obj_id.NULL_ID;
        if (checkHangar)
        {
            playerHangar = utils.getPlayerHangar(player);
        }
        if (isIdValid(datapad))
        {
            obj_id[] datapadContents = getContents(datapad);
            if (datapadContents != null)
            {
                int count = 0;
                for (obj_id datapadContent1 : datapadContents) {
                    if (isIdValid(datapadContent1) && getGameObjectType(datapadContent1) == GOT_data_ship_control_device) {
                        ++count;
                    }
                }
                if (checkHangar && isIdValid(playerHangar))
                {
                    obj_id[] hangarContents = getContents(playerHangar);
                    if (hangarContents != null && hangarContents.length > 0)
                    {
                        for (obj_id hangarContent : hangarContents) {
                            if (isIdValid(hangarContent) && getGameObjectType(hangarContent) == GOT_data_ship_control_device) {
                                ++count;
                            }
                        }
                    }
                }
                if (count > 0)
                {
                    obj_id[] shipControlDevices = new obj_id[count];
                    count = 0;
                    for (obj_id datapadContent : datapadContents) {
                        if (isIdValid(datapadContent) && getGameObjectType(datapadContent) == GOT_data_ship_control_device) {
                            shipControlDevices[count++] = datapadContent;
                        }
                    }
                    if (checkHangar && isIdValid(playerHangar))
                    {
                        obj_id[] hangarContents = getContents(playerHangar);
                        if (hangarContents != null && hangarContents.length > 0)
                        {
                            for (obj_id hangarContent : hangarContents) {
                                if (isIdValid(hangarContent) && getGameObjectType(hangarContent) == GOT_data_ship_control_device) {
                                    shipControlDevices[count++] = hangarContent;
                                }
                            }
                        }
                    }
                    return shipControlDevices;
                }
            }
        }
        return null;
    }
    public static obj_id[] findShipControlDevicesInHangarSlot(obj_id player) throws InterruptedException
    {
        obj_id playerHangarSlot = utils.getPlayerHangar(player);
        if (isIdValid(playerHangarSlot))
        {
            obj_id[] hangarContents = getContents(playerHangarSlot);
            if (hangarContents != null && hangarContents.length > 0)
            {
                int count = 0;
                for (obj_id hangarContent1 : hangarContents) {
                    if (isIdValid(hangarContent1) && getGameObjectType(hangarContent1) == GOT_data_ship_control_device) {
                        ++count;
                    }
                }
                if (count > 0)
                {
                    obj_id[] shipHangarSlotControlDevices = new obj_id[count];
                    count = 0;
                    for (obj_id hangarContent : hangarContents) {
                        if (isIdValid(hangarContent) && getGameObjectType(hangarContent) == GOT_data_ship_control_device) {
                            shipHangarSlotControlDevices[count++] = hangarContent;
                        }
                    }
                    return shipHangarSlotControlDevices;
                }
            }
        }
        return null;
    }
    public static obj_id findEmptyShipControlDeviceForShip(obj_id player, obj_id ship) throws InterruptedException
    {
        if (hasObjVar(ship, "shipControlDevice"))
        {
            return getObjIdObjVar(ship, "shipControlDevice");
        }
        else
        {
            return findEmptyShipControlDeviceForPlayer(player);
        }
    }
    public static obj_id findEmptyShipControlDeviceForPlayer(obj_id player) throws InterruptedException
    {
        obj_id[] shipControlDevices = findShipControlDevicesForPlayer(player);
        if (shipControlDevices != null)
        {
            for (obj_id shipControlDevice : shipControlDevices) {
                obj_id[] contents = getContents(shipControlDevice);
                if (contents == null || contents.length == 0) {
                    return shipControlDevice;
                }
            }
        }
        return null;
    }
    public static obj_id getShipFromShipControlDevice(obj_id shipControlDevice) throws InterruptedException
    {
        obj_id[] contents = getContents(shipControlDevice);
        if (contents != null && contents.length > 0)
        {
            return contents[0];
        }
        return null;
    }
    public static int countShipControlDevicesForPlayer(obj_id player) throws InterruptedException
    {
        int count = -1;
        obj_id datapad = utils.getDatapad(player);
        if (isIdValid(datapad))
        {
            obj_id[] datapadContents = getContents(datapad);
            if (datapadContents != null)
            {
                count = 0;
                for (obj_id datapadContent : datapadContents) {
                    if (isIdValid(datapadContent) && getGameObjectType(datapadContent) == GOT_data_ship_control_device) {
                        ++count;
                    }
                }
            }
        }
        return count;
    }
    public static boolean isPlayerBelowShipLimit(obj_id player, obj_id destinationContainer) throws InterruptedException
    {
        if ((getTemplateName(destinationContainer)).equals("object/tangible/datapad/character_hangar_datapad.iff"))
        {
            return true;
        }
        else
        {
            return isPlayerBelowShipLimit(player);
        }
    }
    public static boolean isPlayerBelowShipLimit(obj_id player) throws InterruptedException
    {
        int count = countShipControlDevicesForPlayer(player);
        int limit = SHIP_LIMIT_MAX;
        if (features.hasEpisode3Expansion(player))
        {
            limit = SHIP_LIMIT_EXPANSION;
        }
        if (count < 0)
        {
            return false;
        }
        if (count >= limit)
        {
            return false;
        }
        return true;
    }
    public static obj_id findPilotSlotObjectForShip(obj_id pilot, obj_id ship) throws InterruptedException
    {
        if (canPutInSlot(pilot, ship, SHIP_PILOT_SLOT_NAME) == CEC_SUCCESS)
        {
            return ship;
        }
        obj_id[] cells = getContents(ship);
        if (cells != null)
        {
            for (obj_id cell : cells) {
                obj_id[] contents = getContents(cell);
                if (contents != null) {
                    for (obj_id content : contents) {
                        if (canPutInSlot(pilot, content, POB_SHIP_PILOT_SLOT_NAME) == CEC_SUCCESS) {
                            return content;
                        }
                    }
                }
            }
        }
        return null;
    }
    public static Vector getContainedPlayers(obj_id obj) throws InterruptedException
    {
        return getContainedPlayers(obj, null);
    }
    public static Vector getContainedPlayers(obj_id obj, Vector players) throws InterruptedException
    {
        if (isPlayer(obj))
        {
            return utils.addElement(players, obj);
        }
        obj_id[] contents = getContents(obj);
        if (contents != null)
        {
            for (obj_id content : contents) {
                players = getContainedPlayers(content, players);
            }
        }
        return players;
    }
    public static void packShip(obj_id ship) throws InterruptedException
    {
        if (getTopMostContainer(ship) != ship)
        {
            return;
        }
        if (space_dungeon.isSpaceDungeon(ship))
        {
            return;
        }
        boolean inSpace = isSpaceScene();
        boolean teleportFixup = hasObjVar(ship, "teleportFixup");
        obj_id pilot = getPilotId(ship);
        obj_id owner = getOwner(ship);
        location shipLoc = getLocation(ship);
        if (debugSpaceTransition)
        {
            LIVE_LOG("TeleportFixup", "packShip, ship=" + ship + ", template=" + getTemplateName(ship) + ", pilot=" + pilot + ", teleportFixup=" + teleportFixup + ", inSpace=" + inSpace + ", owner=" + owner);
        }
        Vector players = getContainedPlayers(ship, null);
        if (players != null)
        {
            for (Object player1 : players) {
                obj_id player = ((obj_id) player1);
                setState(player, STATE_SITTING_ON_CHAIR, false);
                int posture = getPosture(player);
                if (posture == POSTURE_SITTING || posture == POSTURE_PRONE) {
                    setPostureClientImmediate(player, POSTURE_UPRIGHT);
                }
                revokeDroidCommands(player);
                if (debugSpaceTransition) {
                    LOG("space_transition", "packShip dealing with contained player " + player);
                }
                if (player == pilot) {
                    unpilotShip(pilot);
                }
                if (inSpace && player != owner) {
                    teleportPlayerToLaunchLoc(player);
                } else {
                    if (teleportFixup) {
                        copyObjVar(ship, player, "teleportFixup");
                        LIVE_LOG("TeleportFixup", "Copying teleportFixup objVar from " + ship + " to " + player);
                    }
                    setLocation(player, shipLoc);
                }
            }
        }
        if (teleportFixup)
        {
            removeObjVar(ship, "teleportFixup");
            LIVE_LOG("TeleportFixup", "Removing teleportFixup objVar from " + ship);
        }
        if (isIdValid(owner))
        {
            space_combat.clearHyperspace(ship);
            obj_id shipControlDevice = findEmptyShipControlDeviceForShip(owner, ship);
            if (debugSpaceTransition)
            {
                LOG("space_transition", "packShip ship=" + ship + " scd=" + shipControlDevice);
            }
            obj_id droidControlDevice = getDroidControlDeviceForShip(ship);
            LOG("space", "droidControlDevice is " + droidControlDevice);
            if (isIdValid(droidControlDevice))
            {
                obj_id droid = callable.getCDCallable(droidControlDevice);
                LOG("space", "droid is " + droid);
                if (isIdValid(droid))
                {
                    space_combat.removeFlightDroidFromShip(droidControlDevice, droid);
                }
            }
            space_pilot_command.allPurposeShipComponentReset(ship);
            if (isIdValid(shipControlDevice) && putIn(ship, shipControlDevice))
            {
                if (isGameObjectTypeOf(ship, GOT_ship_fighter) && space_utils.isShipWithInterior(ship))
                {
                    obj_id[] shipContents = getAllObjectsInPob(ship);
                    if (shipContents != null && shipContents.length > 0)
                    {
                        for (obj_id shipContent : shipContents) {
                            if (isIdValid(shipContent)) {
                                messageTo(shipContent, "OnPack", null, 1.0f, false);
                            }
                        }
                    }
                }
                return;
            }
        }
        if (debugSpaceTransition)
        {
            LOG("space_transition", "packShip failed to clean up ship properly, destroying");
        }
        destroyObject(ship);
    }
    public static obj_id[] getAllObjectsInPob(obj_id pob) throws InterruptedException
    {
        obj_id[] cells = getCellIds(pob);
        if (cells == null || cells.length == 0)
        {
            return null;
        }
        Vector objects = new Vector();
        objects.setSize(0);
        for (obj_id cell : cells) {
            obj_id[] contents = getContents(cell);
            if (contents != null && contents.length > 0) {
                for (obj_id content : contents) {
                    utils.addElement(objects, content);
                }
            }
        }
        if (objects == null || objects.size() == 0)
        {
            return null;
        }
        obj_id[] returnList = new obj_id[0];
        if (objects != null)
        {
            returnList = new obj_id[objects.size()];
            objects.toArray(returnList);
        }
        return returnList;
    }
    public static void setShipName(obj_id ship, obj_id player, obj_id shipControlDevice) throws InterruptedException
    {
        String strName = getAssignedName(shipControlDevice);
        LOG("space", "strName is " + strName);
        if (strName == null || strName.length() == 0)
        {
            setName(ship, getName(player));
        }
        else
        {
            setName(ship, getName(player) + " (" + strName + ")");
        }
    }
    public static boolean unpackShipForPlayer(obj_id player, obj_id ship) throws InterruptedException
    {
        obj_id shipControlDevice = getContainedBy(ship);
        if (debugSpaceTransition)
        {
            LOG("space_transition", "unpackShipForPlayer: scd=" + shipControlDevice + " ship=" + ship + " player=" + player);
        }
        if (isIdValid(shipControlDevice) && isIdValid(ship))
        {
            setShipName(ship, player, shipControlDevice);
            setLocation(ship, getLocation(player));
            setObjVar(shipControlDevice, "ship", ship);
            setObjVar(ship, "shipControlDevice", shipControlDevice);
            obj_id pilotSlotObject = findPilotSlotObjectForShip(player, ship);
            if (debugSpaceTransition)
            {
                LOG("space_transition", "unpackShipForPlayer: ship=" + ship + " pilotSlotObject=" + pilotSlotObject + " player=" + player);
            }
            LOG("space", "Trying to pilot the ship for slot " + pilotSlotObject);
            if (isIdValid(pilotSlotObject) && pilotShip(player, pilotSlotObject))
            {
                LOG("space", "I think i piloted");
                updateShipFaction(ship, player);
                doAIImmunityCheck(ship);
                obj_id droidControlDevice = getDroidControlDeviceForShip(ship);
                if (isIdValid(droidControlDevice))
                {
                    obj_id objDroid = callable.getCDCallable(droidControlDevice);
                    if (isIdValid(objDroid))
                    {
                        space_combat.removeFlightDroidFromShip(droidControlDevice, objDroid);
                    }
                }
                if (isIdValid(droidControlDevice))
                {
                    if (debugSpaceTransition)
                    {
                        LOG("space_transition", "unpacking droid from control device " + droidControlDevice + ", ship=" + ship + ", pilotSlotObject=" + pilotSlotObject + ", pilot=" + player);
                    }
                    space_combat.createFlightDroidFromData(droidControlDevice, pilotSlotObject);
                    obj_id objDroid = callable.getCDCallable(droidControlDevice);
                    setAnimationMood(objDroid, "ship");
                    if (!space_utils.isShipWithInterior(ship))
                    {
                        grantDroidCommands(player);
                    }
                    utils.setLocalVar(ship, "droidPcdId", droidControlDevice);
                }
                if (hasScript(ship, "conversation.ship_trainer_01") || hasScript(ship, "conversation.npe_new_jtl_tutorial"))
                {
                    space_utils.openCommChannelAfterLoad(ship, ship);
                }
                if (utils.hasScriptVar(player, "strLaunchPointName"))
                {
                    obj_id objStation = space_combat.getClosestSpaceStation(ship);
                    String strLaunchName = getStringObjVar(objStation, "strName");
                    location locTest = getLocation(objStation);
                    space_transition.updateLaunchWaypoint(player, locTest, strLaunchName);
                    utils.setScriptVar(player, "intNewbieZoneLaunch", 1);
                }
                if (isSpaceBattlefieldZone())
                {
                    if (hasObjVar(ship, "battlefield.locEntryLocation"))
                    {
                        location locEntryLocation = getLocationObjVar(ship, "battlefield.locEntryLocation");
                        warpPlayer(ship, locEntryLocation.area, locEntryLocation.x, locEntryLocation.y, locEntryLocation.z, locEntryLocation.cell, locEntryLocation.x, locEntryLocation.y, locEntryLocation.z);
                    }
                }
                obj_id[] shipContents = trial.getAllObjectsInDungeon(ship);
                if (shipContents != null && shipContents.length > 0)
                {
                    for (obj_id shipContent : shipContents) {
                        if (isIdValid(shipContent)) {
                            messageTo(shipContent, "OnShipUnpack", null, 1.0f, false);
                        }
                    }
                }
                LOG("space", "AOK");
                return true;
            }
            else
            {
                LOG("space", "NO piloting");
            }
            packShip(ship);
        }
        return false;
    }
    public static boolean inNovaOrionBattle(obj_id player) throws InterruptedException
    {
        location here = getLocation(player);
        if (here.area.equals("space_nova_orion"))
        {
            if (hasCompletedCollectionSlot(player, "orion_rank_01_04") || hasCompletedCollectionSlot(player, "nova_rank_01_04"))
            {
                if (!hasCompletedCollectionSlot(player, "orion_rank_01_05") && !hasCompletedCollectionSlot(player, "nova_rank_01_05"))
                {
                    return true;
                }
            }
        }
        return false;
    }
    public static int getPreOverridePlayerSpaceFaction(obj_id player) throws InterruptedException
    {
        obj_id ship = getContainingShip(player);
        if (!isIdValid(ship))
        {
            return 0;
        }
        if (hasObjVar(ship, "spaceFaction.FactionOverride"))
        {
            return getIntObjVar(ship, "spaceFaction.FactionOverride");
        }
        else if (hasSkill(player, "pilot_imperial_navy_novice"))
        {
            return (-615855020);
        }
        else if (hasSkill(player, "pilot_rebel_navy_novice"))
        {
            return (370444368);
        }
        else
        {
            String strFaction = space_flags.getSpaceTrack(player);
            if (strFaction != null)
            {
                if (strFaction.equals(space_flags.PRIVATEER_CORELLIA))
                {
                    return (-1702304293);
                }
                if (strFaction.equals(space_flags.PRIVATEER_TATOOINE))
                {
                    if (space_flags.isInTierTwo(player))
                    {
                        return (1153980303);
                    }
                    else if (space_flags.isInTierThree(player) && (!space_quest.hasReceivedReward(player, "escort", "tatooine_privateer_tier2_4a")))
                    {
                        return (1153980303);
                    }
                    else
                    {
                        return (1808105482);
                    }
                }
                if (strFaction.equals(space_flags.PRIVATEER_NABOO))
                {
                    return (-1360873682);
                }
            }
        }
        return (676821884);
    }
    public static int getPlayerSpaceFaction(obj_id player) throws InterruptedException
    {
        obj_id ship = getContainingShip(player);
        if (!isIdValid(ship))
        {
            return 0;
        }
        if (hasObjVar(player, township.OBJVAR_NOVA_ORION_FACTION))
        {
            if (inNovaOrionBattle(player))
            {
                String novaOrionFaction = getStringObjVar(player, township.OBJVAR_NOVA_ORION_FACTION);
                if (novaOrionFaction != null)
                {
                    if (novaOrionFaction.equals("nova") || novaOrionFaction.equals("orion"))
                    {
                        int[] alliedFactions = new int[3];
                        if (novaOrionFaction.equals("nova"))
                        {
                            alliedFactions[0] = (1089617796);
                            alliedFactions[1] = (-160237431);
                            alliedFactions[2] = getPreOverridePlayerSpaceFaction(player);
                            shipSetSpaceFactionAllies(ship, alliedFactions);
                            return (1089617796);
                        }
                        else
                        {
                            alliedFactions[0] = (2043986206);
                            alliedFactions[1] = (-160237431);
                            alliedFactions[2] = getPreOverridePlayerSpaceFaction(player);
                            shipSetSpaceFactionAllies(ship, alliedFactions);
                            return (2043986206);
                        }
                    }
                }
            }
        }
        if (hasObjVar(ship, "spaceFaction.FactionOverride"))
        {
            int[] alliedFactions = new int[1];
            alliedFactions[0] = (-160237431);
            shipSetSpaceFactionAllies(ship, alliedFactions);
            return getIntObjVar(ship, "spaceFaction.FactionOverride");
        }
        else if (hasSkill(player, "pilot_imperial_navy_novice"))
        {
            int[] alliedFactions = new int[2];
            alliedFactions[0] = (-1360873682);
            alliedFactions[1] = (-160237431);
            shipSetSpaceFactionAllies(ship, alliedFactions);
            return (-615855020);
        }
        else if (hasSkill(player, "pilot_rebel_navy_novice"))
        {
            int[] alliedFactions = new int[2];
            alliedFactions[0] = (-1153515706);
            alliedFactions[1] = (-160237431);
            shipSetSpaceFactionAllies(ship, alliedFactions);
            return (370444368);
        }
        else
        {
            String strFaction = space_flags.getSpaceTrack(player);
            if (strFaction != null)
            {
                if (strFaction.equals(space_flags.PRIVATEER_CORELLIA))
                {
                    int[] alliedFactions = new int[1];
                    alliedFactions[0] = (-160237431);
                    shipSetSpaceFactionAllies(ship, alliedFactions);
                    return (-1702304293);
                }
                if (strFaction.equals(space_flags.PRIVATEER_TATOOINE))
                {
                    int[] alliedFactions = new int[1];
                    alliedFactions[0] = (-160237431);
                    shipSetSpaceFactionAllies(ship, alliedFactions);
                    if (space_flags.isInTierTwo(player))
                    {
                        return (1153980303);
                    }
                    else if (space_flags.isInTierThree(player) && (!space_quest.hasReceivedReward(player, "escort", "tatooine_privateer_tier2_4a")))
                    {
                        return (1153980303);
                    }
                    else
                    {
                        return (1808105482);
                    }
                }
                if (strFaction.equals(space_flags.PRIVATEER_NABOO))
                {
                    int[] alliedFactions = new int[2];
                    alliedFactions[0] = (-615855020);
                    alliedFactions[1] = (-160237431);
                    shipSetSpaceFactionAllies(ship, alliedFactions);
                    return (-1360873682);
                }
            }
        }
        int[] alliedFactions = new int[1];
        alliedFactions[0] = (-160237431);
        shipSetSpaceFactionAllies(ship, alliedFactions);
        return (676821884);
    }
    public static void grantDroidCommands(obj_id objPlayer) throws InterruptedException
    {
        String[] strDroidCommands = space_combat.getProgrammedDroidCommands(objPlayer);
        if (strDroidCommands != null && strDroidCommands.length > 0)
        {
            for (String strDroidCommand : strDroidCommands) {
                grantCommand(objPlayer, "droid+" + strDroidCommand);
            }
        }
    }
    public static void revokeDroidCommands(obj_id objPlayer) throws InterruptedException
    {
        String[] strDroidCommands = space_combat.getProgrammedDroidCommands(objPlayer);
        if (strDroidCommands != null && strDroidCommands.length > 0)
        {
            for (String strDroidCommand : strDroidCommands) {
                revokeCommand(objPlayer, "droid+" + strDroidCommand);
            }
        }
    }
    public static void teleportPlayerToLaunchLoc(obj_id player) throws InterruptedException
    {
        teleportPlayerToLaunchLoc(player, false);
    }
    public static void teleportPlayerToLaunchLoc(obj_id player, boolean hyperspace) throws InterruptedException
    {
        location worldLaunchLoc = getLocationObjVar(player, "space.launch.worldLoc");
        if (hasObjVar(player, "npe.phase_number"))
        {
            if (npe.teleportPlayerToLaunchLoc(player, hyperspace))
            {
                return;
            }
        }
        if (worldLaunchLoc == null)
        {
            sendSystemMessageTestingOnly(player, "You do not have a launch location. This usually means that you didn't use the launch terminal. Please do so.");
            worldLaunchLoc = new location(5.0f, 195.0f, 5.0f, "tatooine");
        }
        float theta = rand() * (2.0f * (float)Math.PI);
        float radius = 2.0f + rand() * 3.0f;
        worldLaunchLoc.x += radius * StrictMath.cos(theta);
        worldLaunchLoc.z += radius * StrictMath.sin(theta);
        if (hyperspace)
        {
            hyperspacePlayerToLocation(player, worldLaunchLoc.area, worldLaunchLoc.x, worldLaunchLoc.y, worldLaunchLoc.z, null, worldLaunchLoc.x, worldLaunchLoc.y, worldLaunchLoc.z, null, false);
        }
        else
        {
            warpPlayer(player, worldLaunchLoc.area, worldLaunchLoc.x, worldLaunchLoc.y, worldLaunchLoc.z, null, worldLaunchLoc.x, worldLaunchLoc.y, worldLaunchLoc.z, null, false);
        }
    }
    public static location getShipBoardingDestination(obj_id objShip) throws InterruptedException
    {
        transform[] trEntrances = utils.getTransformArrayScriptVar(objShip, "locEntrance");
        if (trEntrances == null)
        {
            obj_id[] objCells = getContents(objShip);
            if (objCells == null || objCells.length == 0)
            {
                return null;
            }
            obj_id objCell = objCells[0];
            location locTest = new location();
            locTest.cell = objCell;
            LOG("space", "HORRIBLY BAD SHIP OF OBJECT ID " + objShip);
            return locTest;
        }
        obj_id[] objCells = utils.getObjIdArrayScriptVar(objShip, "locEntranceCells");
        int intTest = rand(0, trEntrances.length - 1);
        transform tr = trEntrances[intTest];
        location locDestination = space_utils.getLocationFromTransform(tr);
        locDestination.cell = objCells[intTest];
        return locDestination;
    }
    public static boolean isShipBoardable(obj_id objPlayer, obj_id objShip) throws InterruptedException
    {
        obj_id objPlayerShip = getPilotedShip(objPlayer);
        if (!isIdValid(objPlayerShip))
        {
            string_id strSpam = new string_id("space/space_advanced", "no_board_without_ship");
            sendSystemMessage(objPlayer, strSpam);
            return false;
        }
        if (!isIdValid(objShip))
        {
            string_id strSpam = new string_id("space/space_advanced", "no_ship_targeted");
            sendSystemMessage(objPlayer, strSpam);
            return false;
        }
        obj_id[] objCells = getContents(objShip);
        if (objCells == null || objCells.length == 0)
        {
            string_id strSpam = new string_id("space/space_advanced", "not_boardable");
            sendSystemMessage(objPlayer, strSpam);
            return false;
        }
        return true;
    }
    public static Vector getShipStartLocations(obj_id ship) throws InterruptedException
    {
        Vector startLocations = null;
        String shipTemplateName = getTemplateName(ship);
        String[] templateNames = dataTableGetStringColumn(DATATABLE_SHIP_START_LOCATIONS, COLUMN_TEMPLATE);
        if (templateNames != null)
        {
            int rowIndex = 0;
            for (; rowIndex < templateNames.length; ++rowIndex)
            {
                if (templateNames[rowIndex].equals(shipTemplateName))
                {
                    break;
                }
            }
            if (rowIndex < templateNames.length)
            {
                do
                {
                    String slotName = dataTableGetString(DATATABLE_SHIP_START_LOCATIONS, rowIndex, COLUMN_SLOT);
                    if (slotName != null && slotName.length() > 0)
                    {
                        int slot = utils.stringToInt(slotName.substring(11));
                        if (slot > 0 && isShipSlotInstalled(ship, slot + ship_chassis_slot_type.SCST_weapon_0))
                        {
                            startLocations = utils.addElement(startLocations, new location(0.0f, 0.0f, 0.0f, slotName, null));
                        }
                        else
                        {
                            LOG("space", "Launching into space.  Turret not installed.  slotName: " + slotName + " slot: " + slot);
                        }
                    }
                    else
                    {
                        String cellName = dataTableGetString(DATATABLE_SHIP_START_LOCATIONS, rowIndex, COLUMN_CELL);
                        obj_id cell = getCellId(ship, cellName);
                        if (isIdValid(cell))
                        {
                            float x = dataTableGetFloat(DATATABLE_SHIP_START_LOCATIONS, rowIndex, COLUMN_X);
                            float y = dataTableGetFloat(DATATABLE_SHIP_START_LOCATIONS, rowIndex, COLUMN_Y);
                            float z = dataTableGetFloat(DATATABLE_SHIP_START_LOCATIONS, rowIndex, COLUMN_Z);
                            startLocations = utils.addElement(startLocations, new location(x, y, z, getCurrentSceneName(), cell));
                        }
                    }
                    ++rowIndex;
                } while (rowIndex < templateNames.length && templateNames[rowIndex].length() == 0);
            }
        }
        return startLocations;
    }
    public static void updateLaunchWaypoint(obj_id objPlayer, location locTest, String strName) throws InterruptedException
    {
        obj_id objLaunchWaypoint = null;
        if (!hasObjVar(objPlayer, "space.objLaunchWaypoint"))
        {
            LOG("space", "HASOBJVAR");
            objLaunchWaypoint = createWaypointInDatapad(objPlayer, locTest);
        }
        else
        {
            objLaunchWaypoint = getObjIdObjVar(objPlayer, "space.objLaunchWaypoint");
            LOG("space", "objLaunchWaypoint of " + objLaunchWaypoint + " does not exist");
            if (!space_utils.hasWaypointInDatapad(objPlayer, objLaunchWaypoint))
            {
                objLaunchWaypoint = createWaypointInDatapad(objPlayer, locTest);
            }
            else
            {
                setWaypointLocation(objLaunchWaypoint, locTest);
            }
        }
        if (isIdValid(objLaunchWaypoint))
        {
            setObjVar(objPlayer, "space.objLaunchWaypoint", objLaunchWaypoint);
            setWaypointActive(objLaunchWaypoint, true);
            string_id strSpam = new string_id("launch_names", strName);
            setWaypointName(objLaunchWaypoint, utils.packStringId(strSpam));
        }
    }
    public static void updateShipFaction(obj_id ship, obj_id player) throws InterruptedException
    {
        int spaceFaction = 0;
        if (isSpaceBattlefieldZone() && hasObjVar(ship, "intBattlefieldTeam"))
        {
            spaceFaction = getIntObjVar(ship, "intBattlefieldTeam");
            shipSetSpaceFaction(ship, spaceFaction);
        }
        else
        {
            spaceFaction = getPlayerSpaceFaction(player);
            shipSetSpaceFaction(ship, spaceFaction);
            updatePVPStatus(ship);
        }
    }
    public static void setPlayerOvert(long player) throws InterruptedException
    {
        final obj_id playerObjId = obj_id.getObjId(player);
        if (isIdValid(playerObjId) && exists(playerObjId) && playerObjId.isAuthoritative())
        {
            setPlayerOvert(playerObjId);
        }
    }
    public static void setPlayerOvert(obj_id player) throws InterruptedException
    {
        obj_id objShip = getContainingShip(player);
        if (isIdValid(objShip))
        {
            setObjVar(objShip, "spaceFaction.overt", 1);
            messageTo(objShip, "checkSpacePVPStatus", null, 30, false);
        }
        return;
    }
    public static void setPlayerOvert(obj_id player, int overrideFaction) throws InterruptedException
    {
        obj_id ship = getContainingShip(player);
        setObjVar(ship, "spaceFaction.FactionOverride", overrideFaction);
        shipSetSpaceFaction(ship, overrideFaction);
        setPlayerOvert(player);
    }
    public static void clearOvertStatus(long objShip) throws InterruptedException
    {
        final obj_id objShipObjId = obj_id.getObjId(objShip);
        if (isIdValid(objShipObjId) && exists(objShipObjId) && objShipObjId.isAuthoritative())
        {
            clearOvertStatus(objShipObjId);
        }
    }
    public static void clearOvertStatus(obj_id objShip) throws InterruptedException
    {
        if (isIdValid(objShip))
        {
            if (hasObjVar(objShip, "intBattlefieldTeam"))
            {
                removeObjVar(objShip, "intBattlefieldTeam");
            }
            removeObjVar(objShip, "spaceFaction.FactionOverride");
            removeObjVar(objShip, "spaceFaction.overt");
            space_utils.notifyObject(objShip, "checkSpacePVPStatus", null);
        }
    }
    public static void updatePVPStatus(obj_id objShip) throws InterruptedException
    {
        if (hasObjVar(objShip, "spaceFaction.FactionOverride"))
        {
            shipSetSpaceFaction(objShip, getIntObjVar(objShip, "spaceFaction.FactionOverride"));
        }
        if (hasObjVar(objShip, "spaceFaction.overt"))
        {
            obj_id objPlayer = getPilotId(objShip);
            int intType = pvpGetType(objShip);
            if (intType != PVPTYPE_DECLARED)
            {
                if (isIdValid(objPlayer))
                {
                    space_utils.sendSystemMessage(objPlayer, SID_PVP_NOW_OVERT);
                }
            }
            pvpMakeDeclared(objShip);
        }
        else
        {
            int intType = pvpGetType(objShip);
            if (intType == PVPTYPE_DECLARED)
            {
                obj_id objPlayer = getPilotId(objShip);
                if (isIdValid(objPlayer))
                {
                    space_utils.sendSystemMessage(objPlayer, SID_PVP_NOW_NEUTRAL);
                }
            }
            pvpSetAlignedFaction(objShip, 0);
            pvpMakeNeutral(objShip);
        }
    }
    public static void doAIImmunityCheck(obj_id objShip) throws InterruptedException
    {
        String strChassisType = getShipChassisType(objShip);
        ship_ai.unitRemoveFromAllAttackTargetLists(objShip);
        if (strChassisType.equals("player_sorosuub_space_yacht"))
        {
            LOG("space", "Setting sorosuub to be immune");
            ship_ai.unitSetAutoAggroImmune(objShip, true);
        }
        else
        {
            ship_ai.unitSetAutoAggroImmuneTime(objShip, 60);
        }
/*        if (hasObjVar(getOwner(objShip), "gm"))
        {
            ship_ai.unitRemoveFromAllAttackTargetLists(objShip);
            ship_ai.unitSetAutoAggroImmune(objShip, true);
            ship_ai.unitSetDamageAggroImmune(objShip, true);
            sendSystemMessageTestingOnly(getOwner(objShip), "aiIgnore[ON]: AI will no longer aggro you or fight back.");
        }*/
    }
    public static void adjustShipTeleportFixupInSpaceScene(obj_id ship) throws InterruptedException
    {
        if (hasObjVar(ship, "teleportFixup"))
        {
            String fixupCallback = getStringObjVar(ship, "teleportFixup.callback");
            LIVE_LOG("TeleportFixup", "Removing teleport fixup from " + ship);
            removeObjVar(ship, "teleportFixup");
            if (fixupCallback != null && fixupCallback.length() > 0)
            {
                setObjVar(ship, "teleportFixup.callback", fixupCallback);
            }
        }
    }
    public static int getNextStartIndex(Vector shipStartLocations, int lastStartIndex) throws InterruptedException
    {
        int startIndex = lastStartIndex + 1;
        if (startIndex > shipStartLocations.size())
        {
            for (startIndex = 1; startIndex <= shipStartLocations.size(); ++startIndex)
            {
                if (((location)shipStartLocations.get(startIndex - 1)).cell != null)
                {
                    break;
                }
            }
        }
        return startIndex;
    }
    public static void launch(obj_id player, obj_id ship, obj_id[] passengers, location warpLocation, location groundLoc) throws InterruptedException
    {
        Vector passengersToWarp = utils.addElement(null, player);
        Vector passengerStartIndexes = utils.addElement(null, 0);
        Vector shipStartLocations = space_transition.getShipStartLocations(ship);
        if (null != passengers)
        {
            if (shipStartLocations != null && shipStartLocations.size() > 0)
            {
                int startIndex = 0;
                location playerLoc = getLocation(player);
                for (obj_id passenger : passengers) {
                    if (passenger != player) {
                        if (features.isSpaceEdition(passenger)) {
                            startIndex = space_transition.getNextStartIndex(shipStartLocations, startIndex);
                            if (startIndex <= shipStartLocations.size()) {
                                passengersToWarp = utils.addElement(passengersToWarp, passenger);
                                passengerStartIndexes = utils.addElement(passengerStartIndexes, startIndex);
                            }
                        } else {
                            string_id strSpam = new string_id("space/space_interaction", "no_space_expansion");
                            sendSystemMessage(passenger, strSpam);
                        }
                    }
                }
            }
        }
        for (int i = 0; i < passengersToWarp.size(); ++i)
        {
            obj_id passenger = ((obj_id)passengersToWarp.get(i));
            int passengerStartIndex = (Integer) passengerStartIndexes.get(i);
            space_transition.setLaunchInfo(passenger, ship, passengerStartIndex, groundLoc);
            warpPlayer(passenger, warpLocation.area, warpLocation.x, warpLocation.y, warpLocation.z, null, warpLocation.x, warpLocation.y, warpLocation.z);
        }
    }
    public static void exitCapitalShip(obj_id objPlayer, transform trTest) throws InterruptedException
    {
        obj_id objContainingShip = space_transition.getContainingShip(objPlayer);
        location locTest = getLocationObjVar(objContainingShip, "locLaunchLocation");
        obj_id[] objControlDevices = space_transition.findShipControlDevicesForPlayer(objPlayer);
        LOG("space", "control device is " + objControlDevices[0]);
        obj_id ship = space_transition.getShipFromShipControlDevice(objControlDevices[0]);
        setObjVar(objPlayer, "space.launch.worldLoc", locTest);
        setObjVar(objPlayer, "space.launch.ship", ship);
        setObjVar(objPlayer, "space.launch.startIndex", 0);
        setLocation(ship, locTest);
        LOG("space", "Putting ship at " + locTest);
        dictionary dctParams = new dictionary();
        dctParams.put("objShip", ship);
        dctParams.put("locTest", locTest);
        dctParams.put("trTest", trTest);
        messageTo(objPlayer, "doDelayedPilot", dctParams, 1.0f, false);
    }
    public static void enterCapitalShip(obj_id objPlayer, transform trTest, obj_id objCell) throws InterruptedException
    {
        space_transition.packShip(space_transition.getContainingShip(objPlayer));
        location locTest = new location();
        locTest.cell = objCell;
        setLocation(objPlayer, locTest);
        LOG("space", "sending player to " + locTest);
        setTransform_o2p(objPlayer, trTest);
    }
}
