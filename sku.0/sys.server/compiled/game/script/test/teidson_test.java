package script.test;

import script.dictionary;
import script.library.*;
import script.location;
import script.obj_id;
import script.region;

public class teidson_test extends script.base_script
{
    public teidson_test()
    {
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        if (!isGod(self) || getGodLevel(self) < 50 || !isPlayer(self)) {
            detachScript(self, "test.teidson_test");
        }
        return SCRIPT_CONTINUE;
    }
    public int OnSpeaking(obj_id self, String strText) throws InterruptedException
    {
        String[] strCommands = split(strText, ' ');
        if (strCommands[0].equalsIgnoreCase("cityschedule"))
        {
            int bSeconds = gcw.gcwGetNextInvasionTime("bestine");
            int bHours = bSeconds / 3600;
            int bMinutes = (bSeconds % 3600) / 60;
            int bSecondsModded = bSeconds % 60;
            debugSpeakMsg(self, "invasion bestine: " + bSeconds + "s (" + bHours + "h " + bMinutes + "m " + bSecondsModded + "s)");
            bSeconds = gcw.gcwGetNextInvasionTime("dearic");
            bHours = bSeconds / 3600;
            bMinutes = (bSeconds % 3600) / 60;
            bSecondsModded = bSeconds % 60;
            debugSpeakMsg(self, "invasion dearic: " + bSeconds + "s (" + bHours + "h " + bMinutes + "m " + bSecondsModded + "s)");
            bSeconds = gcw.gcwGetNextInvasionTime("keren");
            bHours = bSeconds / 3600;
            bMinutes = (bSeconds % 3600) / 60;
            bSecondsModded = bSeconds % 60;
            debugSpeakMsg(self, "invasion keren: " + bSeconds + "s (" + bHours + "h " + bMinutes + "m " + bSecondsModded + "s)");
            return SCRIPT_CONTINUE;
        }
        if (strCommands[0].equalsIgnoreCase("time"))
        {
            int gameTime = getGameTime();
            int[] convertedGameTime = player_structure.convertSecondsTime(gameTime);
            sendSystemMessageTestingOnly(self, "getGameTime() returns " + gameTime + " (" + (convertedGameTime[0] / 365) + "y:" + (convertedGameTime[0] % 365) + "d:" + convertedGameTime[1] + "h:" + convertedGameTime[2] + "m:" + convertedGameTime[3] + "s)");
            int calendarTime = getCalendarTime();
            int[] convertedCalendarTime = player_structure.convertSecondsTime(calendarTime);
            sendSystemMessageTestingOnly(self, "getCalendarTime() returns " + calendarTime + " (" + (convertedCalendarTime[0] / 365) + "y:" + (convertedCalendarTime[0] % 365) + "d:" + convertedCalendarTime[1] + "h:" + convertedCalendarTime[2] + "m:" + convertedCalendarTime[3] + "s)");
            sendSystemMessageTestingOnly(self, "getCalendarTimeStringGMT(" + calendarTime + ") returns " + getCalendarTimeStringGMT(calendarTime));
            sendSystemMessageTestingOnly(self, "getCalendarTimeStringLocal(" + calendarTime + ") returns " + getCalendarTimeStringLocal(calendarTime));
        }
        if (strCommands[0].equalsIgnoreCase("gcwnear"))
        {
            obj_id near = gcw.getInvasionSequencerNearby(self);
            sendSystemMessageTestingOnly(self, "GCW Sequencer: " + near);
        }
        if (strCommands[0].equalsIgnoreCase("yaw"))
        {
            obj_id target = getTarget(self);
            float yaw = getYaw(target);
            sendSystemMessageTestingOnly(self, "Target's yaw: " + yaw);
        }
        if (strCommands[0].equalsIgnoreCase("rotate"))
        {
            obj_id target = getTarget(self);
            float yaw = getYaw(target);
            setYaw(target, yaw + 5.0f);
        }
        if (strCommands[0].equalsIgnoreCase("increment"))
        {
            obj_id target = getTarget(self);
            incrementCount(target, 100);
        }
        if (strCommands[0].equalsIgnoreCase("ismob"))
        {
            obj_id target = getTarget(self);
            sendSystemMessageTestingOnly(self, "isMob: " + isMob(target));
        }
        if (strCommands[0].equalsIgnoreCase("gcwpoints"))
        {
            obj_id target = getTarget(self);
            gcw._grantGcwPoints(target, self, 1000000, false, gcw.GCW_POINT_TYPE_GROUND_PVE, "");
        }
        if (strCommands[0].equalsIgnoreCase("seqloc"))
        {
            if (!hasObjVar(self, "record_table"))
            {
                sendSystemMessageTestingOnly(self, "You do not have the record_table objvar defined");
                return SCRIPT_CONTINUE;
            }
            obj_id target = getTarget(self);
            if (!isIdValid(target))
            {
                sendSystemMessageTestingOnly(self, "You must target something first");
                return SCRIPT_CONTINUE;
            }
            String table = getStringObjVar(self, "record_table");
            location objLoc = getLocation(target);
            float x = objLoc.x;
            float y = objLoc.y;
            float z = objLoc.z;
            String name = getTemplateName(target);
            float yaw = getYaw(target);
            sendSystemMessageTestingOnly(self, "Location(" + x + ", " + y + ", " + z + ") yaw(" + yaw + ")");
            dictionary dctRow = new dictionary();
            dctRow.put("object", name);
            dctRow.put("spawn_id", "");
            dctRow.put("triggerId", "");
            dctRow.put("room", "");
            dctRow.put("loc_x", x);
            dctRow.put("loc_y", y);
            dctRow.put("loc_z", z);
            dctRow.put("yaw", yaw);
            dctRow.put("script", "");
            dctRow.put("spawn_objvar", "");
            dctRow.put("trigger_event", "");
            dctRow.put("isInvulnerable", 0);
            dctRow.put("comment", "");
            dctRow.put("paths", "");
            dctRow.put("respawn", -1);
            dctRow.put("mission_critical", 0);
            datatable.serverDataTableAddRow(table, dctRow);
            return SCRIPT_CONTINUE;
        }
        if (strCommands[0].equalsIgnoreCase("gcwc"))
        {
            location loc = getLocation(self);
            createObject("object/tangible/destructible/gcw_kit_tower.iff", loc);
            loc.x += 6.0f;
            createObject("object/tangible/destructible/gcw_kit_barricade.iff", loc);
            loc.x += 6.0f;
            createObject("object/tangible/destructible/gcw_kit_turret.iff", loc);
            loc.x += 6.0f;
            createObject("object/tangible/destructible/gcw_kit_patrol.iff", loc);
            return SCRIPT_CONTINUE;
        }
        if (strCommands[0].equalsIgnoreCase("rebelgcw"))
        {
            sendSystemMessageTestingOnly(self, "Rebel percentage: " + gcw.getRebelPercentileByRegion(self));
            return SCRIPT_CONTINUE;
        }
        if (strCommands[0].equalsIgnoreCase("rnd"))
        {
            obj_id resource = pickRandomNonDepeletedResource("gas_reactive");
            if (isIdValid(resource))
            {
                location loc = getLocation(self);
                oneTimeHarvest(resource, 1000000, loc);
                sendSystemMessageTestingOnly(self, "gas_reactive resource: " + resource);
                return SCRIPT_CONTINUE;
            }
            resource = pickRandomNonDepeletedResource("steel");
            if (isIdValid(resource))
            {
                location loc = getLocation(self);
                oneTimeHarvest(resource, 1000000, loc);
                sendSystemMessageTestingOnly(self, "steel resource: " + resource);
                return SCRIPT_CONTINUE;
            }
        }
        if (strCommands[0].equalsIgnoreCase("ron"))
        {
            location whereIam = getLocation(self);
            location lowerLeft = getLocation(self);
            location topRight = getLocation(self);
            float x = whereIam.x;
            float z = whereIam.z;
            lowerLeft.x = whereIam.x - 300;
            lowerLeft.z = whereIam.x + 300;
            topRight.x = whereIam.x + 300;
            topRight.z = whereIam.z - 300;
            createRectRegion(lowerLeft, topRight, "pvp_hanse", regions.PVP_REGION_TYPE_NORMAL, regions.BUILD_FALSE, regions.MUNI_FALSE, regions.GEO_BEACH, 0, 0, regions.SPAWN_FALSE, regions.MISSION_NONE, false, true);
        }
        if (strCommands[0].equalsIgnoreCase("roff"))
        {
            region pvpHanse = getRegion("yavin4", "pvp_hanse");
            deleteRegion(pvpHanse);
        }
        if (strCommands[0].equalsIgnoreCase("pvpcheck"))
        {
            obj_id target = getIntendedTarget(self);
            if (!isIdValid(target))
            {
                return SCRIPT_CONTINUE;
            }
            for (int i = 0, j = 10; i < j; i++)
            {
                int amount = gcw.distributeIndividualContribution(self, target, 0, gcw.GCW_POINT_TYPE_GROUND_PVP);
                sendSystemMessageTestingOnly(self, "GCW: " + amount);
            }
        }
        if (strCommands[0].equalsIgnoreCase("lreg"))
        {
            region[] regions = getRegionsAtPoint(getLocation(self));
            if (regions == null || regions.length < 1)
            {
                sendSystemMessageTestingOnly(self, "No regions here.");
                return SCRIPT_CONTINUE;
            }
            for (region region : regions) {
                sendSystemMessageTestingOnly(self, "Region: " + region.getName());
            }
        }
        if (strCommands[0].equalsIgnoreCase("top"))
        {
            obj_id topMost = getTopMostContainer(self);
            sendSystemMessageTestingOnly(self, "Topmost: " + topMost);
        }
        if (strCommands[0].equalsIgnoreCase("resources"))
        {
            craftinglib.makeBestResource(self, "steel", 1000000);
            craftinglib.makeBestResource(self, "iron", 1000000);
            craftinglib.makeBestResource(self, "copper", 1000000);
            craftinglib.makeBestResource(self, "fuel_petrochem_solid", 1000000);
            craftinglib.makeBestResource(self, "radioactive", 1000000);
            craftinglib.makeBestResource(self, "aluminum", 1000000);
            craftinglib.makeBestResource(self, "ore_extrusive", 1000000);
            craftinglib.makeBestResource(self, "petrochem_inert", 1000000);
            craftinglib.makeBestResource(self, "fiberplast", 1000000);
            craftinglib.makeBestResource(self, "gas_inert", 1000000);
            craftinglib.makeBestResource(self, "gas_reactive", 1000000);
            sendSystemMessageTestingOnly(self, "Completed.");
            return SCRIPT_CONTINUE;
        }
        if (strCommands[0].equalsIgnoreCase("tcg"))
        {
            String[] promotions = scheduled_drop.validatePromotionsVersusCluster(scheduled_drop.getScheduledPromotions("item"));
            if (promotions == null || promotions.length <= 0)
            {
                sendSystemMessageTestingOnly(self, "All promotions empty.");
                return SCRIPT_CONTINUE;
            }
            dictionary[] promotionalItems = scheduled_drop.getStaticItemsForAllPromotions(promotions);
            if (promotionalItems == null || promotionalItems.length <= 0)
            {
                sendSystemMessageTestingOnly(self, "Items in promotions empty.");
                return SCRIPT_CONTINUE;
            }
            int index = scheduled_drop.getRandomStaticItem(promotionalItems);
            if (index < 0)
            {
                sendSystemMessageTestingOnly(self, "No promotion item chosen.");
                return SCRIPT_CONTINUE;
            }
            sendSystemMessageTestingOnly(self, "promotions.length: " + promotions.length + "promotionalItems.length: " + promotionalItems.length + " random item: " + promotionalItems[index].getString("promotionItem"));
            String[] promotionList = new String[promotionalItems.length];
            for (int i = 0, j = promotionalItems.length; i < j; i++)
            {
                promotionList[i] = "[" + i + "] promo: " + promotionalItems[i].getString("promotionName") + " item: " + promotionalItems[i].getString("promotionItem") + " weight: " + promotionalItems[i].getInt("promotionWeight");
            }
            int pid = sui.listbox(self, self, "List of items", sui.OK_CANCEL, "Promotion List", promotionList, "noHandler", true, false);
        }
        if (strCommands[0].equalsIgnoreCase("planet"))
        {
            location where = getLocation(self);
            String serverName = toLower(getConfigSetting("CentralServer", "clusterName"));
            sendSystemMessageTestingOnly(self, "Planet: " + getPlanetByName(where.area) + " serverName: " + serverName);
            return SCRIPT_CONTINUE;
        }
        if (strCommands[0].equalsIgnoreCase("erasetcg"))
        {
            scheduled_drop.setClusterPromotions(null);
            return SCRIPT_CONTINUE;
        }
        if (strCommands[0].equalsIgnoreCase("reducetcg"))
        {
            dictionary params = new dictionary();
            params.put("promotion", "tcg_swg_promo_1");
            messageTo(getPlanetByName("tatooine"), "reducePromotion", params, 1.0f, true);
        }
        if (strCommands[0].equalsIgnoreCase("slamtcg"))
        {
            int boosters = 0;
            int starters = 0;
            int cards = 0;
            for (int i = 0; i < 50000; i++)
            {
                if (scheduled_drop.canDropCard(scheduled_drop.SYSTEM_CRAFTER))
                {
                    obj_id temp = scheduled_drop.dropCard(scheduled_drop.SYSTEM_CRAFTER, self);
                }
            }
        }
        if (strCommands[0].equalsIgnoreCase("cleani"))
        {
            obj_id inv = utils.getInventoryContainer(self);
            obj_id[] contents = getContents(inv);
            for (obj_id content : contents) {
                if (!isIdValid(content)) {
                    continue;
                }
                String temp = "" + content;
                int k = Integer.parseInt(temp);
                if (k > 11000000) {
                    destroyObject(content);
                }
            }
        }
        if (strCommands[0].equalsIgnoreCase("setup"))
        {
            obj_id building = getTopMostContainer(self);
            setObjVar(building, "tester", self);
            messageTo(building, "setupArea", null, 0, false);
        }
        if (strCommands[0].equalsIgnoreCase("start"))
        {
            obj_id building = getTopMostContainer(self);
            setObjVar(building, "tester", self);
            messageTo(building, "startEncounter", null, 0, false);
        }
        if (strCommands[0].equalsIgnoreCase("end"))
        {
            obj_id building = getTopMostContainer(self);
            messageTo(building, "endEncounter", null, 0, false);
        }
        if (strCommands[0].equalsIgnoreCase("cleanup"))
        {
            obj_id building = getTopMostContainer(self);
            messageTo(building, "cleanUpArea", null, 0, false);
        }
        if (strCommands[0].equals("cleanUpYo"))
        {
            sendSystemMessageTestingOnly(self, "Cleaning up encounters....");
            obj_id[] objectsInRange = getObjectsInRange(getLocation(self), 200.0f);
            for (obj_id obj_id : objectsInRange) {
                if (hasObjVar(obj_id, "grievous_encounter.active")) {
                    obj_id powerCell = obj_id;
                    destroyObject(powerCell);
                }
            }
        }
        if (strCommands[0].equals("checkPermissions"))
        {
            obj_id dungeon = getTopMostContainer(self);
            String cellName = strCommands[1];
            obj_id cell = getCellId(dungeon, cellName);
            sendSystemMessageTestingOnly(self, "Cell " + cell + " is public:  " + permissionsIsPublic(cell));
        }
        if (strCommands[0].equals("exp"))
        {
            location here = getLocation(self);
            obj_id room = here.cell;
            obj_id top = getTopMostContainer(self);
            String roomName = getCellName(room);
            String table = "datatables/pvp/pvp_battlefield_building_bunker.tab";
            dictionary dctRow = new dictionary();
            String num = "";
            if (strCommands.length > 1)
            {
                num = strCommands[1];
            }
            dctRow.put("name", "explosion" + num);
            dctRow.put("loc_x", here.x);
            dctRow.put("loc_y", here.y);
            dctRow.put("loc_z", here.z);
            dctRow.put("room", roomName);
            dctRow.put("delay", 1.0f);
            datatable.serverDataTableAddRow(table, dctRow);
            sendSystemMessageTestingOnly(self, "Explosion added: " + here);
            return SCRIPT_CONTINUE;
        }
        if (strCommands[0].equals("schematicTest"))
        {
            grantSchematic(self, "object/draft_schematic/weapon/knife_naktra_crystal_false.iff");
        }
        if (strCommands[0].equals("prtTest"))
        {
            playClientEffectObj(self, "appearance/pt_grievious_powerup.prt", self, "");
        }
        if (strCommands[0].equals("damageYou"))
        {
            obj_id target = getLookAtTarget(self);
            setHitpoints(target, 1);
        }
        if (strCommands[0].equals("scene"))
        {
            String scene = getCurrentSceneName();
            debugSpeakMsg(self, "Scene is " + scene);
        }
        if (strCommands[0].equals("id"))
        {
            obj_id objPlayerShip = getPilotedShip(self);
            debugSpeakMsg(self, "My ID is " + self + " and my inventory is " + utils.getInventoryContainer(self));
            debugSpeakMsg(self, "Furthermore, my ship ID is " + objPlayerShip);
        }
        if (strCommands[0].equals("cargoHoldYes"))
        {
            obj_id objShip = getPilotedShip(self);
        }
        if (strCommands[0].equals("clearCargo"))
        {
            obj_id objPlayerShip = getPilotedShip(self);
            obj_id objs[] = getShipCargoHoldContentsResourceTypes(objPlayerShip);
            int amounts[] = getShipCargoHoldContentsAmounts(objPlayerShip);
            for (obj_id obj : objs) {
                setShipCargoHoldContent(objPlayerShip, obj, 0);
            }
        }
        if (strCommands[0].equals("cargoCheck"))
        {
            obj_id objPlayerShip = getPilotedShip(self);
            int contents = getShipCargoHoldContentsCurrent(objPlayerShip);
            int max = getShipCargoHoldContentsMaximum(objPlayerShip);
            sendSystemMessageTestingOnly(self, "CARGO: " + contents + " / " + max);
        }
        if (strCommands[0].equals("stopRunning"))
        {
            setLocomotion(self, 0);
        }
        if (strCommands[0].equalsIgnoreCase("yt2400"))
        {
            debugSpeakMsg(self, " getting on board!");
            obj_id objShip = getLookAtTarget(self);
            if (!isIdValid(objShip))
            {
                sendSystemMessageTestingOnly(self, "No look at target");
                return SCRIPT_CONTINUE;
            }
            obj_id[] objCells = getContents(objShip);
            if ((objCells == null) || (objCells.length == 0))
            {
                sendSystemMessageTestingOnly(self, "ship  doesn't have an interior");
                return SCRIPT_CONTINUE;
            }
            obj_id objCell = objCells[0];
            location locTest = new location();
            locTest.cell = objCell;
            setLocation(self, locTest);
        }
        if (strCommands[0].equalsIgnoreCase("leave"))
        {
            location locTest = new location();
            locTest.area = "tatooine";
            setLocation(self, locTest);
        }
        if (strCommands[0].equals("nukeAll"))
        {
            obj_id[] objTestObjects = getObjectsInRange(self, 1000);
            if (objTestObjects != null)
            {
                for (obj_id objTestObject : objTestObjects) {
                    destroyObject(objTestObject);
                }
            }
            return SCRIPT_CONTINUE;
        }
        if (strCommands[0].equals("loco"))
        {
            int loco = getLocomotion(self);
            sendSystemMessageTestingOnly(self, "Locomotion state = " + loco);
        }
        if (strCommands[0].equals("cellx"))
        {
            obj_id objShip = getTopMostContainer(self);
            obj_id[] objCells = getContents(objShip);
            obj_id objCell = objCells[rand(0, objCells.length)];
            if (strCommands.length == 2)
            {
                for (obj_id objCell1 : objCells) {
                    String cellName = getCellName(objCell1);
                    if (cellName.equals(strCommands[1])) {
                        objCell = objCell1;
                    }
                }
            }
            location locTest = new location();
            locTest.cell = objCell;
            setLocation(self, locTest);
        }
        if (strCommands[0].equals("bridgeMe"))
        {
            obj_id objShip = getLookAtTarget(self);
            if (!isIdValid(objShip))
            {
                sendSystemMessageTestingOnly(self, "No look at target");
                return SCRIPT_CONTINUE;
            }
            obj_id[] objCells = getContents(objShip);
            obj_id objCell = objCells[13];
            location locTest = new location();
            locTest.cell = objCell;
            setLocation(self, locTest);
        }
        if (strCommands[0].equals("makeTutorial"))
        {
            location locTest = getLocation(self);
            locTest.z = locTest.z + 250.0f;
            obj_id[] objTestObjects = getAllObjectsWithTemplate(getLocation(self), 320000, "object/building/general/npe_hangar_1.iff");
            obj_id objStation = null;
            if ((objTestObjects == null) || (objTestObjects.length == 0))
            {
                objStation = createObject("object/building/general/npe_hangar_1.iff", getLocation(self));
                persistObject(objStation);
            }
            else 
            {
                objStation = objTestObjects[0];
            }
            obj_id[] objCells = getContents(objStation);
            if ((objCells == null) || (objCells.length == 0))
            {
                sendSystemMessageTestingOnly(self, "ship  doesn't have an interior");
                return SCRIPT_CONTINUE;
            }
            obj_id objCell = objCells[0];
            location locDestination = getGoodLocation(objStation, "hanger");
            locDestination.x = -0.5f;
            locDestination.y = -3;
            locDestination.z = -72.2f;
            setLocation(self, locDestination);
            sendSystemMessageTestingOnly(self, "station is " + objStation);
        }
        if (strCommands[0].equals("loc"))
        {
            location loc = getLocation(self);
            sendSystemMessageTestingOnly(self, "X = " + loc.x + " Y = " + loc.y + "Z = " + loc.z + " AREA = " + loc.area + " CELL = " + loc.cell);
        }
        if (strCommands[0].equals("teleport"))
        {
            obj_id top = getTopMostContainer(self);
            String whereTo = strCommands[1];
            location randomLoc = getGoodLocation(top, whereTo);
            obj_id cell = getCellId(top, whereTo);
            utils.warpPlayer(self, randomLoc);
            return SCRIPT_CONTINUE;
        }
        if (strCommands[0].equals("beam"))
        {
            obj_id objTarget = getTarget(self);
            if (isIdValid(objTarget))
            {
                playClientEffectObj(self, "appearance/pt_ig88_electrify.prt", objTarget, "");
                playClientEffectObj(objTarget, "appearance/pt_ig88_electrify_target.prt", self, "");
            }
            return SCRIPT_CONTINUE;
        }
        if (strCommands[0].equals("rocket"))
        {
            obj_id objTarget = getTarget(self);
            if (isIdValid(objTarget))
            {
                queueCommand(self, (1862364946), objTarget, "", COMMAND_PRIORITY_DEFAULT);
            }
            return SCRIPT_CONTINUE;
        }
        if (strCommands[0].equals("ig88"))
        {
            location loc = new location(4260, 180, -3943);
            utils.warpPlayer(self, loc);
            return SCRIPT_CONTINUE;
        }
        if (strCommands[0].equals("shipinfo"))
        {
            obj_id[] scds = callable.getDatapadCallablesByType(self, callable.CALLABLE_TYPE_SHIP);
            for (obj_id scd : scds) {
                if (isIdValid(scd)) {
                    obj_id[] controlDeviceContents = getContents(scd);
                    LOG("shipcontents", "Ship: " + controlDeviceContents[0]);
                    if (isIdValid(controlDeviceContents[0])) {
                        int[] intRawSlots = getShipChassisSlots(controlDeviceContents[0]);
                        LOG("shipcontents", "Ship Control Device: " + getName(scd) + " first content: " + getName(controlDeviceContents[0]) + " slots: " + intRawSlots.length + " chassisType: " + getShipChassisType(controlDeviceContents[0]));
                        for (int intRawSlot : intRawSlots) {
                            LOG("shipcontents", "" + getName(controlDeviceContents[0]) + " Slot: " + space_crafting.getShipComponentStringType(intRawSlot));
                        }
                    }
                }
            }
        }
        if (strCommands[0].equals("shipplasma"))
        {
            obj_id objShip = getPilotedShip(self);
            if (!isIdValid(objShip))
            {
                objShip = getTopMostContainer(self);
                if (!isIdValid(objShip))
                {
                    return SCRIPT_CONTINUE;
                }
            }
            if (utils.hasScriptVar(objShip, "objPlasmaConduits"))
            {
                obj_id[] objPlasmaConduits = utils.getObjIdArrayScriptVar(objShip, "objPlasmaConduits");
                sendSystemMessageTestingOnly(self, "objPlasmaCondiuts size: " + objPlasmaConduits.length);
                for (int i = 0; i < objPlasmaConduits.length; i++)
                {
                    sendSystemMessageTestingOnly(self, "objPlasmaConduits[" + i + "]: " + objPlasmaConduits[i]);
                }
            }
        }
        if (strCommands[0].equals("shiphp"))
        {
            obj_id objShip = getPilotedShip(self);
            if (!isIdValid(objShip))
            {
                objShip = getTopMostContainer(self);
                if (!isIdValid(objShip))
                {
                    return SCRIPT_CONTINUE;
                }
            }
            if (strCommands[1] == null || strCommands[1].length() <= 0)
            {
                return SCRIPT_CONTINUE;
            }
            playClientEffectObj(self, "appearance/pt_cmndo_flashbang.prt", objShip, strCommands[1]);
            return SCRIPT_CONTINUE;
        }
        if (strCommands[0].equals("engineReadout"))
        {
            String strDataTable = "datatables/ship/components/engine_balance_readout.tab";
            String[] strHeaderTypes = 
            {
                "f"
            };
            String[] strHeaders = 
            {
                "speed"
            };
            boolean boolTest = datatable.createDataTable(strDataTable, strHeaders, strHeaderTypes);
            if (!boolTest)
            {
                sendSystemMessageTestingOnly(self, "No datatable made");
                return SCRIPT_CONTINUE;
            }
            dictionary dctRow = new dictionary(1);
            for (int i = 0; i < 1000; i++)
            {
                float speed = space_crafting.getBellValue(58.8f, 0.1f);
                dctRow.put("speed", speed);
                datatable.serverDataTableAddRow(strDataTable, dctRow);
            }
        }
        if (strCommands[0].equals("topMostContainer"))
        {
            debugSpeakMsg(self, "topmost is" + getTopMostContainer(self));
        }
        if (strCommands[0].equals("fillInventory"))
        {
            debugSpeakMsg(self, "AHH!");
            for (int i = 0; i < 60; i++)
            {
                obj_id playerInv = utils.getInventoryContainer(self);
                obj_id newComponent = create.object("object/tangible/ship/components/weapon/wpn_incom_light_blaster.iff", playerInv, false, false);
            }
        }
        if (strCommands[0].equals("noXp"))
        {
            xp.grant(self, "shipwright", +23356);
        }
        if (strCommands[0].equals("makeRebelPilot"))
        {
            skill.grantSkill(self, "pilot_rebel_navy_novice");
            skill.grantSkill(self, "pilot_rebel_navy_starships_01");
            skill.grantSkill(self, "pilot_rebel_navy_starships_02");
            skill.grantSkill(self, "pilot_rebel_navy_starships_03");
            skill.grantSkill(self, "pilot_rebel_navy_starships_04");
            skill.grantSkill(self, "pilot_rebel_navy_weapons_01");
            skill.grantSkill(self, "pilot_rebel_navy_weapons_02");
            skill.grantSkill(self, "pilot_rebel_navy_weapons_03");
            skill.grantSkill(self, "pilot_rebel_navy_weapons_04");
            skill.grantSkill(self, "pilot_rebel_navy_procedures_01");
            skill.grantSkill(self, "pilot_rebel_navy_procedures_02");
            skill.grantSkill(self, "pilot_rebel_navy_procedures_03");
            skill.grantSkill(self, "pilot_rebel_navy_procedures_04");
            skill.grantSkill(self, "pilot_rebel_navy_droid_01");
            skill.grantSkill(self, "pilot_rebel_navy_droid_02");
            skill.grantSkill(self, "pilot_rebel_navy_droid_03");
            skill.grantSkill(self, "pilot_rebel_navy_droid_04");
            skill.grantSkill(self, "pilot_rebel_navy_master");
        }
        if (strCommands[0].equals("giveMeMass"))
        {
            obj_id pcd[] = space_transition.findShipControlDevicesForPlayer(self);
            obj_id ship = space_transition.getShipFromShipControlDevice(pcd[0]);
            setChassisComponentMassMaximum(ship, 10000000.0f);
            return SCRIPT_CONTINUE;
        }
        if (strCommands[0].equals("colorShip"))
        {
            obj_id pcd[] = space_transition.findShipControlDevicesForPlayer(self);
            obj_id ship = space_transition.getShipFromShipControlDevice(pcd[0]);
            if (isIdValid(ship))
            {
                String index = "/shared_owner/index_color_1";
                sui.colorize(self, self, ship, index, "handleColorize");
                String index2 = "/shared_owner/index_color_2";
                sui.colorize(self, self, ship, index2, "handleColorize");
            }
            return SCRIPT_CONTINUE;
        }
        if (strCommands[0].equals("stimpack"))
        {
            obj_id playerInv = utils.getInventoryContainer(self);
            obj_id newComponent = create.object("object/tangible/medicine/instant_stimpack/stimpack_a.iff", playerInv, false, false);
            setObjVar(newComponent, "healing.power", 250);
            setCount(newComponent, 5);
        }
        if (strCommands[0].equals("hurtMyself"))
        {
            int maxHealth = getMaxHealth(self);
            int newHealth = maxHealth / 2;
            setHealth(self, newHealth);
        }
        if (strCommands[0].equals("pattern0"))
        {
            debugSpeakMsg(self, "Pattern 0");
            obj_id[] pcd = space_transition.findShipControlDevicesForPlayer(self);
            obj_id ship = space_transition.getShipFromShipControlDevice(pcd[0]);
            String index = "/shared_owner/index_texture_1";
            hue.setRangedIntCustomVar(ship, index, 0);
        }
        if (strCommands[0].equals("pattern1"))
        {
            obj_id[] pcd = space_transition.findShipControlDevicesForPlayer(self);
            obj_id ship = space_transition.getShipFromShipControlDevice(pcd[0]);
            String index = "/shared_owner/index_texture_1";
            hue.setRangedIntCustomVar(ship, index, 1);
        }
        if (strCommands[0].equals("pattern2"))
        {
            obj_id[] pcd = space_transition.findShipControlDevicesForPlayer(self);
            obj_id ship = space_transition.getShipFromShipControlDevice(pcd[0]);
            String index = "/shared_owner/index_texture_1";
            hue.setRangedIntCustomVar(ship, index, 2);
        }
        if (strCommands[0].equals("pattern3"))
        {
            obj_id[] pcd = space_transition.findShipControlDevicesForPlayer(self);
            obj_id ship = space_transition.getShipFromShipControlDevice(pcd[0]);
            String index = "/shared_owner/index_texture_1";
            hue.setRangedIntCustomVar(ship, index, 3);
        }
        if (strCommands[0].equals("pattern4"))
        {
            obj_id[] pcd = space_transition.findShipControlDevicesForPlayer(self);
            obj_id ship = space_transition.getShipFromShipControlDevice(pcd[0]);
            String index = "/shared_owner/index_texture_1";
            hue.setRangedIntCustomVar(ship, index, 4);
        }
        if (strCommands[0].equals("pattern5"))
        {
            obj_id[] pcd = space_transition.findShipControlDevicesForPlayer(self);
            obj_id ship = space_transition.getShipFromShipControlDevice(pcd[0]);
            String index = "/shared_owner/index_texture_1";
            hue.setRangedIntCustomVar(ship, index, 5);
        }
        if (strCommands[0].equals("pattern6"))
        {
            obj_id[] pcd = space_transition.findShipControlDevicesForPlayer(self);
            obj_id ship = space_transition.getShipFromShipControlDevice(pcd[0]);
            String index = "/shared_owner/index_texture_1";
            hue.setRangedIntCustomVar(ship, index, 6);
        }
        if (strCommands[0].equals("moveShip"))
        {
            debugSpeakMsg(self, "Understood command");
            obj_id[] pcd = space_transition.findShipControlDevicesForPlayer(self);
            obj_id ship = space_transition.getShipFromShipControlDevice(pcd[0]);
            location x = getLocation(self);
            setLocation(ship, x);
            debugSpeakMsg(self, "Ship ID = " + ship);
        }
        if (strCommands[0].equals("unstickMe"))
        {
            debugSpeakMsg(self, "Unsticking self.");
            location x = getLocation(self);
            x.x -= 1;
            x.y -= 1;
            x.z -= 1;
            setLocation(self, x);
        }
        if (strCommands[0].equals("getShipId"))
        {
            obj_id[] pcd = space_transition.findShipControlDevicesForPlayer(self);
            obj_id ship = space_transition.getShipFromShipControlDevice(pcd[0]);
            String chassis = getShipChassisType(ship);
            debugSpeakMsg(self, "Ship ID = " + ship);
            debugSpeakMsg(self, "Chassis = " + chassis);
        }
        if (strCommands[0].equals("wwallaceValidateLoot"))
        {
            String strDataTable = "datatables/ship/components/loot_validation.tab";
            String[] strHeaderTypes = 
            {
                "s",
                "s",
                "s"
            };
            String[] strHeaders = 
            {
                "objectTemplate",
                "didItCreate",
                "validStats"
            };
            boolean boolTest = datatable.createDataTable(strDataTable, strHeaders, strHeaderTypes);
            if (!boolTest)
            {
                sendSystemMessageTestingOnly(self, "No datatable made");
                return SCRIPT_CONTINUE;
            }
            String[] strTables = new String[1];
            strTables = new String[8];
            strTables[0] = "armor";
            strTables[1] = "booster";
            strTables[2] = "capacitor";
            strTables[3] = "droid_interface";
            strTables[4] = "engine";
            strTables[5] = "reactor";
            strTables[6] = "shield";
            strTables[7] = "weapon";
            for (int intM = 0; intM < strTables.length; intM++)
            {
                debugSpeakMsg(self, "Now doing " + strTables[intM]);
                String strFileName = "datatables/ship/components/" + strTables[intM] + ".iff";
                String[] strTemplates = dataTableGetStringColumn(strFileName, "strType");
                for (String strTemplate : strTemplates) {
                    dictionary dctRow = new dictionary(2);
                    String created = "";
                    obj_id objTest = null;
                    try {
                        objTest = createObject(strTemplate, getLocation(self));
                        created = "yes";
                    } catch (Throwable err) {
                        LOG("space_error", "OBJECT " + strTemplates[intM] + " in " + strFileName + " does not create!!!");
                        created = "no";
                    }
                    destroyObject(objTest);
                    dctRow.put("objectTemplate", strTemplate);
                    dctRow.put("didItCreate", created);
                    datatable.serverDataTableAddRow(strDataTable, dctRow);
                }
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int handleColorize(obj_id self, dictionary params) throws InterruptedException
    {
        int bp = sui.getIntButtonPressed(params);
        if (bp == sui.BP_CANCEL)
        {
            return SCRIPT_CONTINUE;
        }
        obj_id player = sui.getPlayerId(params);
        if (!isIdValid(player))
        {
            return SCRIPT_CONTINUE;
        }
        String template = utils.getTemplateFilenameNoPath(self);
        String index = "/shared_owner/index_color_1";
        if (index != null && !index.equals("") && !index.equals("none"))
        {
            int idx = sui.getColorPickerIndex(params);
            if (idx > -1)
            {
                obj_id ship = getPilotedShip(player);
                if (isIdValid(ship))
                {
                    hue.setColor(ship, index, idx);
                }
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int delayTeleport(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id newbieHall = getObjIdObjVar(self, "newbieHallId");
        location randomLoc = getGoodLocation(newbieHall, "r13");
        utils.warpPlayer(self, randomLoc);
        return SCRIPT_CONTINUE;
    }
}
