package script.working.wwallace;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.create;
import script.library.combat;
import script.library.sui;
import script.library.utils;
import script.library.money;
import script.library.pet_lib;
import script.library.ai_lib;
import script.library.skill;
import script.library.hue;
import script.library.space_transition;
import script.library.datatable;
import script.library.xp;
import script.library.space_crafting;
import script.library.space_create;

public class wwallace_test extends script.base_script
{
    public wwallace_test()
    {
    }
    public int OnSpeaking(obj_id self, String strText) throws InterruptedException
    {
        String[] strCommands = split(strText, ' ');
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
            for (int i = 0; i < objectsInRange.length; i++)
            {
                if (hasObjVar(objectsInRange[i], "grievous_encounter.active"))
                {
                    obj_id powerCell = objectsInRange[i];
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
        if (strCommands[0].equals("spawner"))
        {
            location here = getLocation(self);
            float yaw = getYaw(self);
            obj_id room = here.cell;
            obj_id top = getTopMostContainer(self);
            String roomName = getCellName(room);
            String spawnLoc = strCommands[1] + " @ " + here + " yaw " + yaw;
            String table = "datatables/spawning/dungeon/republic_facility.tab";
            dictionary dctRow = new dictionary();
            dctRow.put("spawns", strCommands[1]);
            dctRow.put("loc_x", here.x);
            dctRow.put("loc_y", here.y);
            dctRow.put("loc_z", here.z);
            dctRow.put("room", roomName);
            dctRow.put("yaw", yaw);
            datatable.serverDataTableAddRow(table, dctRow);
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
            setHitpoints(target, 0);
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
            for (int i = 0; i < objs.length; i++)
            {
                setShipCargoHoldContent(objPlayerShip, objs[i], 0);
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
                for (int intI = 0; intI < objTestObjects.length; intI++)
                {
                    destroyObject(objTestObjects[intI]);
                }
            }
            return SCRIPT_CONTINUE;
        }
        if (strCommands[0].equals("loco"))
        {
            int loco = getLocomotion(self);
            sendSystemMessageTestingOnly(self, "Locomotion state = " + loco);
        }
        if (strCommands[0].equals("cell"))
        {
            obj_id objShip = getTopMostContainer(self);
            obj_id[] objCells = getContents(objShip);
            obj_id objCell = objCells[0];
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
            locTest.z = locTest.z + 250f;
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
            locDestination.x = -.5f;
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
        if (strCommands[0].equals("makeCreditChip"))
        {
            obj_id objContainer = utils.getInventoryContainer(self);
            space_create.makeCreditChip(objContainer, 100);
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
            x.x += -1;
            x.y += -1;
            x.z += -1;
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
                for (int intI = 0; intI < strTemplates.length; intI++)
                {
                    dictionary dctRow = new dictionary(2);
                    String created = "";
                    obj_id objTest = null;
                    try
                    {
                        objTest = createObject(strTemplates[intI], getLocation(self));
                        created = "yes";
                    }
                    catch(Throwable err)
                    {
                        LOG("space_error", "OBJECT " + strTemplates[intM] + " in " + strFileName + " does not create!!!");
                        created = "no";
                    }
                    destroyObject(objTest);
                    dctRow.put("objectTemplate", strTemplates[intI]);
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
