package script.library;

import script.dictionary;
import script.location;
import script.obj_id;
import script.string_id;

import java.util.Vector;

public class structure extends script.base_script
{
    public structure()
    {
    }
    public static final String TEMPLATE_DEED = "object/intangible/deed/deed.iff";
    public static final String TEMPLATE_CELL = "object/cell/cell.iff";
    public static final String SLOT_GENERAL_INVENTORY = "";
    public static final String CELLNAME_FOYER = "foyer";
    public static final String CELLNAME_SPAWN = "spawn";
    public static final String VAR_DEED_SCRIPTS = "script_names";
    public static final String VAR_PLAYER_HOME = "home";
    public static final String SCRIPT_DEED = "structure.deed.deed";
    public static final String SCRIPT_BASE_STRUCTURE = "structure.base.base_structure";
    public static final String SCRIPT_TURNSTILE = "structure.base.turnstile";
    public static final string_id SID_CLONING_FACILITY = new string_id("building_name", "cloning_facility");
    public static final String WORLD_DELTA = "WORLD_DELTA";
    public static final String DATATABLE_STRUCTURE_VERSION = "datatables/structure/structure_version.iff";
    public static final String DATATABLE_COL_STRUCTURE = "STRUCTURE";
    public static final String DATATABLE_COL_SCRIPT = "SCRIPT";
    public static final String DATATABLE_COL_VERSION_TERMINAL = "VERSION_TERMINAL";
    public static final String DATATABLE_COL_VERSION_SPAWN = "VERSION_SPAWN";
    public static final String DATATABLE_COL_TUBES = "TUBES";
    public static final String DATATABLE_COL_X = "X";
    public static final String DATATABLE_COL_Y = "Y";
    public static final String DATATABLE_COL_Z = "Z";
    public static final String DATATABLE_COL_CELL = "CELL";
    public static final String DATATABLE_COL_HEADING = "HEADING";
    public static final String DATATABLE_COL_TERMINAL = "TERMINAL";
    public static final String VAR_FACTION = "faction";
    public static final String VAR_STRUCTURE_BASE = "structure";
    public static final String VAR_STRUCTURE_TERMINALS = "structure.terminals";
    public static final String VAR_STRUCTURE_VERSION_SPAWN = "structure.version.spawn";
    public static final String VAR_STRUCTURE_VERSION_TERMINAL = "structure.version.terminal";
    public static final String VAR_STRUCTURE_LEVEL_CURRENT = "structure.level.current";
    public static final String VAR_STRUCTURE_LEVEL_DESIRE = "structure.level.desired";
    public static final int DEFAULT_THRESHOLD = 10;
    public static final String VAR_SPAWN_BASE = "structure.spawn";
    public static final int SWITCHLIST_STATIC = 0;
    public static final int SWITCHLIST_AMBIENT = 16;
    public static final String VAR_SPAWN_SWITCHES_BASE = "structure.spawn.switches";
    public static final String VAR_SPAWN_SWITCHES_CURRENT = "structure.spawn.switches.current";
    public static final String VAR_SPAWN_SWITCHES_DESIRED = "structure.spawn.switches.desired";
    public static final String VAR_SPAWN_QUEUE_BASE = "structure.spawn.queue";
    public static final String VAR_SPAWN_QUEUE_LOAD_BASE = "structure.spawn.queue.load";
    public static final String VAR_SPAWN_QUEUE_LOAD_HI = "structure.spawn.queue.load.hi";
    public static final String VAR_SPAWN_QUEUE_LOAD_LOW = "structure.spawn.queue.load.low";
    public static final String VAR_SPAWN_QUEUE_BOXED = "structure.spawn.queue.boxed";
    public static final String VAR_SPAWN_QUEUE_UNLOAD = "structure.spawn.queue.unload";
    public static final String VAR_SPAWN_CURRENT_BASE = "structure.spawn.current";
    public static final String VAR_SPAWN_CURRENT_STATIC = "structure.spawn.current.static";
    public static final String VAR_SPAWN_CURRENT_AMBIENT = "structure.spawn.current.ambient";
    public static final String VAR_SPAWN_POPULATION_BASE = "structure.spawn.population";
    public static final String VAR_SPAWN_POPULATION_PLAYER = "structure.spawn.population.player";
    public static final String VAR_SPAWN_POPULATION_THRESHOLD = "structure.spawn.population.threshold";
    public static final String DATATABLE_COL_SPAWN = "SPAWN";
    public static final String DATATABLE_COL_SKILL_TEMPLATES = "SKILL_TEMPLATES";
    public static final String DATATABLE_COL_SCRIPTS = "SCRIPTS";
    public static final String DATATABLE_CLONING_FACILITY_SPAWN = "datatables/structure/municipal/cloning_facility_spawn.iff";
    public static final String DATATABLE_CLONING_FACILITY_RESPAWN = "datatables/structure/municipal/cloning_facility_respawn.iff";
    public static final String VAR_TERMINAL_HEADING = "intendedHeading";
    public static final String HANDLER_REGISTER_CLONING_FACILITY = "handleRegisterCloningFacility";
    public static final String DATATABLE_FILLER_BASE = "datatables/spawning/filler/";
    public static final String DATATABLE_FILLER_SPAWN_ENDING = "_filler_spawn.iff";
    public static final String DATATABLE_FILLER_EGG_ENDING = "_filler_egg.iff";
    public static final String DATATABLE_COL_TEMPLATE = "TEMPLATE";
    public static final String DATATABLE_COL_IDX = "IDX";
    public static final String DICT_TEMPLATE = "template";
    public static final String DICT_IDX = "idx";
    public static final String DICT_LOC = "loc";
    public static final String VAR_FILLER_SPAWN_BASE = "fillerSpawn";
    public static final String VAR_FILLER_SPAWN_CURRENT_BASE = "fillerSpawn.current";
    public static final String VAR_FILLER_SPAWN_CURRENT_EGG = "fillerSpawn.current.egg";
    public static final String VAR_FILLER_SPAWN_CURRENT_LOC = "fillerSpawn.current.loc";
    public static final String HANDLER_CLEANUP_SELF = "handleCleanupSelf";
    public static obj_id getContainingBuilding(obj_id target) throws InterruptedException
    {
        if (target == null)
        {
            return null;
        }
        obj_id topMost = getTopMostContainer(target);
        if ((topMost == target) || (topMost == null))
        {
            return null;
        }
        return topMost;
    }
    public static boolean isInside(obj_id target) throws InterruptedException
    {
        if (target == null)
        {
            return false;
        }
        obj_id topMost = getTopMostContainer(target);
        return !((topMost == target) || (topMost == null));
    }
    public static String getRandomCell(obj_id structure) throws InterruptedException
    {
        if ((structure == null) || (structure == obj_id.NULL_ID))
        {
            return "";
        }
        String[] cellNames = getCellNames(structure);
        if ((cellNames == null) || (cellNames.length == 0))
        {
            return "";
        }
        else 
        {
            int idx = rand(0, cellNames.length - 1);
            String cellName = cellNames[idx];
            if (!cellName.equals(""))
            {
                return cellName;
            }
        }
        return "";
    }
    public static obj_id getRandomCellId(obj_id structure) throws InterruptedException
    {
        if ((structure == null) || (structure == obj_id.NULL_ID))
        {
            return null;
        }
        String[] cellNames = getCellNames(structure);
        if ((cellNames == null) || (cellNames.length == 0))
        {
            return null;
        }
        else 
        {
            int idx = rand(0, cellNames.length - 1);
            String cellName = cellNames[idx];
            if (!cellName.equals(""))
            {
                obj_id cellId = getCellId(structure, cellName);
                if ((cellId != null) && (cellId != obj_id.NULL_ID)) {
                    return cellId;
                }
            }
        }
        return null;
    }
    public static location getRandomLocationInStructure(obj_id structure) throws InterruptedException
    {
        if ((structure == null) || (structure == obj_id.NULL_ID))
        {
            return null;
        }
        String cellName = getRandomCell(structure);
        if (!cellName.equals(""))
        {
            return getGoodLocation(structure, cellName);
        }
        return null;
    }
    public static int getFacilityVersion(obj_id facility, String scriptName, String columnName) throws InterruptedException
    {
        if ((facility == null) || (scriptName.equals("")) || (columnName.equals("")))
        {
            return -1;
        }
        String facilityTemplate = getTemplateName(facility);
        String[] tpfs = dataTableGetStringColumn(DATATABLE_STRUCTURE_VERSION, DATATABLE_COL_STRUCTURE);
        if ((tpfs != null) && (tpfs.length > 0))
        {
            String dataScript;
            for (int i = 0; i < tpfs.length; i++)
            {
                if (tpfs[i].equals(facilityTemplate))
                {
                    dataScript = dataTableGetString(DATATABLE_STRUCTURE_VERSION, i, DATATABLE_COL_SCRIPT);
                    if (dataScript.equals(scriptName))
                    {
                        return dataTableGetInt(DATATABLE_STRUCTURE_VERSION, i, columnName);
                    }
                }
            }
        }
        LOG("structure", "WARNING: getFacilityVersion error: unable to locate entry for passed params");
        LOG("structure", "facility = " + facility + " template = " + facilityTemplate);
        LOG("structure", "script = " + scriptName + " col = " + columnName);
        return -1;
    }
    public static int getFacilityTerminalVersion(obj_id facility, String scriptName) throws InterruptedException
    {
        if ((facility == null) || (scriptName.equals("")))
        {
            return -1;
        }
        return getFacilityVersion(facility, scriptName, DATATABLE_COL_VERSION_TERMINAL);
    }
    public static int getFacilitySpawnVersion(obj_id facility, String scriptName) throws InterruptedException
    {
        if ((facility == null) || (scriptName.equals("")))
        {
            return -1;
        }
        return getFacilityVersion(facility, scriptName, DATATABLE_COL_VERSION_SPAWN);
    }
    public static boolean isCurrentTerminalVersion(obj_id facility, String scriptName) throws InterruptedException
    {
        if ((facility == null) || (scriptName.equals("")))
        {
            return false;
        }
        int current = getIntObjVar(facility, VAR_STRUCTURE_VERSION_TERMINAL);
        int latest = getFacilityTerminalVersion(facility, scriptName);
        return current >= latest;
    }
    public static boolean isCurrentSpawnVersion(obj_id facility, String scriptName) throws InterruptedException
    {
        if ((facility == null) || (scriptName.equals("")))
        {
            return false;
        }
        int current = getIntObjVar(facility, VAR_STRUCTURE_VERSION_SPAWN);
        int latest = getFacilitySpawnVersion(facility, scriptName);
        return current >= latest;
    }
    public static obj_id[] getPlayersInStructure(obj_id facility) throws InterruptedException
    {
        if (facility == null)
        {
            return null;
        }
        Vector cells = new Vector();
        cells.setSize(0);
        switch (getContainerType(facility))
        {
            case 0:
                break;
            case 1:
                obj_id[] contents = getContents(facility);
                if ((contents == null) || (contents.length == 0))
                {
                    return null;
                }
                for (obj_id content : contents) {
                    if ((getTemplateName(content)).equals(TEMPLATE_CELL)) {
                        cells = utils.addElement(cells, content);
                    }
                }
                break;
        }
        if ((cells == null) || (cells.size() == 0))
        {
            return null;
        }
        obj_id[] cellsArray = new obj_id[0];
        cellsArray = new obj_id[cells.size()];
        cells.toArray(cellsArray);
        Vector players = new Vector();
        players.setSize(0);
        obj_id[] cellContents;
        for (obj_id aCellsArray : cellsArray) {
            cellContents = getContents(aCellsArray);
            if ((cellContents != null) && (cellContents.length != 0)) {
                for (obj_id cellContent : cellContents) {
                    if (isPlayer(cellContent)) {
                        players = utils.addElement(players, cellContent);
                    }
                }
            }
        }
        if ((players == null) || (players.size() == 0))
        {
            return null;
        }
        else 
        {
            obj_id[] _players = new obj_id[players.size()];
            players.toArray(_players);
            return _players;
        }
    }
    public static boolean addStructureCoOwner(obj_id structure, obj_id owner, obj_id player) throws InterruptedException
    {
        if ((structure == null) || (owner == null) || (player == null))
        {
            return false;
        }
        boolean isHouse = false;
        if (hasObjVar(structure, utils.VAR_COOWNERS))
        {
            Vector coowners = getResizeableObjIdArrayObjVar(structure, utils.VAR_COOWNERS);
            if ((coowners == null) || (coowners.size() == 0))
            {
                removeObjVar(structure, utils.VAR_COOWNERS);
                return addStructureCoOwner(structure, owner, player);
            }
            coowners = utils.addElement(coowners, player);
            if ((coowners != null) && (coowners.size() > 0))
            {
                setObjVar(structure, utils.VAR_COOWNERS, coowners);
                return true;
            }
        }
        else 
        {
            obj_id[] newCoOwners = new obj_id[1];
            newCoOwners[0] = player;
            if ((newCoOwners.length > 0))
            {
                setObjVar(structure, utils.VAR_COOWNERS, newCoOwners);
                return true;
            }
        }
        return false;
    }
    public static boolean removeStructureCoOwner(obj_id structure, obj_id owner, obj_id player) throws InterruptedException
    {
        if ((structure == null) || (owner == null) || (player == null))
        {
            return false;
        }
        boolean isHouse = false;
        if (hasObjVar(structure, utils.VAR_COOWNERS))
        {
            Vector coowners = getResizeableObjIdArrayObjVar(structure, utils.VAR_COOWNERS);
            if ((coowners == null) || (coowners.size() == 0))
            {
                removeObjVar(structure, utils.VAR_COOWNERS);
                return false;
            }
            if (!utils.isElementInArray(coowners, player))
            {
                return false;
            }
            coowners = utils.removeElement(coowners, player);
            if ((coowners == null) || (coowners.size() == 0))
            {
                removeObjVar(structure, utils.VAR_COOWNERS);
            }
            else 
            {
                setObjVar(structure, utils.VAR_COOWNERS, coowners);
            }
            return true;
        }
        return false;
    }
    public static obj_id packStructureToDeed(obj_id owner, obj_id building) throws InterruptedException
    {
        if ((owner == null) || (building == null))
        {
            return null;
        }
        obj_id deed = createObject(TEMPLATE_DEED, owner, SLOT_GENERAL_INVENTORY);
        if (putIn(building, deed))
        {
            return deed;
        }
        destroyObject(deed);
        return null;
    }
    public static obj_id unpackStructureFromDeed(obj_id owner, obj_id deed) throws InterruptedException
    {
        if ((owner == null) || (deed == null))
        {
            return null;
        }
        obj_id[] contents = getContents(deed);
        if ((contents == null) || (contents.length != 1))
        {
            return null;
        }
        obj_id building = contents[0];
        if (setLocation(building, getLocation(owner)))
        {
            destroyObject(deed);
            return building;
        }
        return null;
    }
    public static boolean createStructureTerminals(obj_id facility, String scriptName, String datatable) throws InterruptedException
    {
        if (!isIdValid(facility) || datatable == null || datatable.equals(""))
        {
            return false;
        }
        obj_id[] terminals = createStructureTerminals(facility, datatable);
        return !((terminals == null) || (terminals.length == 0));
    }
    public static obj_id[] createStructureTerminals(obj_id facility, String datatable) throws InterruptedException
    {
        if (!isIdValid(facility) || datatable == null || datatable.equals(""))
        {
            return null;
        }
        LOG("structureTerminal", "******************* FACILITY = " + facility + " *****************");
        String facilityTemplate = getTemplateName(facility);
        float fYaw = getYaw(facility);
        LOG("structureTerminal", "fYaw = " + fYaw);
        String sceneName = getCurrentSceneName();
        int numRows = dataTableGetNumRows(datatable);
        Vector terminals = new Vector();
        terminals.setSize(0);
        for (int i = 0; i < numRows; i++)
        {
            String buildingTemplate = dataTableGetString(datatable, i, DATATABLE_COL_STRUCTURE);
            if (buildingTemplate.equals(facilityTemplate))
            {
                dictionary item = dataTableGetRow(datatable, i);
                String TEMPLATE = item.getString(DATATABLE_COL_TERMINAL);
                if (TEMPLATE == null || TEMPLATE.equals(""))
                {
                    continue;
                }
                float X = item.getFloat(DATATABLE_COL_X);
                float Y = item.getFloat(DATATABLE_COL_Y);
                float Z = item.getFloat(DATATABLE_COL_Z);
                float relativeHeading = item.getFloat(DATATABLE_COL_HEADING);
                String CELL_NAME = item.getString(DATATABLE_COL_CELL);
                obj_id CELL_ID = obj_id.NULL_ID;
                LOG("structureTerminal", "retrieved terminal data...");
                LOG("structureTerminal", "loc = " + X + ", " + Y + ". " + Z + " H = " + relativeHeading + " cell = " + CELL_NAME);
                if (!CELL_NAME.equals(WORLD_DELTA)) {
                    CELL_ID = getCellId(facility, CELL_NAME);
                }
                if (CELL_ID == null)
                {
                    LOG("structure", "datatable " + datatable + " has a bad cell name");
                }
                else 
                {
                    obj_id terminal_id;
                    location spot = new location(X, Y, Z, sceneName, CELL_ID);
                    float HEADING = relativeHeading;
                    if (CELL_ID == obj_id.NULL_ID)
                    {
                        LOG("structureTerminal", "creating terminal in world space");
                        location fLoc = getLocation(facility);
                        LOG("structureTerminal", "coordinates of facility are: " + fLoc.x + " " + fLoc.y + " " + fLoc.z + ".");
                        spot = new location(fLoc.x + X, fLoc.y + Y, fLoc.z + Z, sceneName, CELL_ID);
                        LOG("structureTerminal", "coordinates of spot are: " + spot.x + " " + spot.y + " " + spot.z + ".");
                        if (fYaw != 0)
                        {
                            LOG("structureTerminal", "translating coordinates to rotated location");
                            spot = utils.rotatePointXZ(fLoc, spot, fYaw);
                            LOG("structureTerminal", "coordinates of new spot are: " + spot.x + " " + spot.y + " " + spot.z + ".");
                            LOG("structureTerminal", "correcting relative heading...");
                            HEADING -= (90 - fYaw);
                        }
                        LOG("structureTerminal", "Creating new terminal at: " + spot.x + " " + spot.y + " " + spot.z + ".");
                        terminal_id = createObject(TEMPLATE, spot);
                    }
                    else 
                    {
                        LOG("structureTerminal", "creating terminal in CELL (" + CELL_ID + ") space");
                        terminal_id = createObjectInCell(TEMPLATE, facility, CELL_NAME, spot);
                    }
                    if ((terminal_id != null) && (terminal_id != obj_id.NULL_ID))
                    {
                        LOG("structureTerminal", "rotating terminal to heading = " + HEADING);
                        setYaw(terminal_id, HEADING);
                        terminals = utils.addElement(terminals, terminal_id);
                    }
                }
            }
        }
        if ((terminals == null) || (terminals.size() == 0))
        {
            return null;
        }
        return utils.toStaticObjIdArray(terminals);
    }
    public static boolean destroyStructureTerminals(obj_id facility) throws InterruptedException
    {
        if (facility == null)
        {
            return false;
        }
        obj_id[] terminals = getObjIdArrayObjVar(facility, VAR_STRUCTURE_TERMINALS);
        if ((terminals == null) || (terminals.length == 0))
        {
            return false;
        }
        for (obj_id terminal : terminals) {
            destroyObject(terminal);
        }
        removeObjVar(facility, VAR_STRUCTURE_TERMINALS);
        return true;
    }
    public static boolean initializeStructureInteriorSpawn(obj_id facility) throws InterruptedException
    {
        if (facility == null)
        {
            return false;
        }
        if (hasObjVar(facility, VAR_SPAWN_CURRENT_BASE))
        {
            return false;
        }
        setObjVar(facility, VAR_SPAWN_SWITCHES_CURRENT, 0);
        setObjVar(facility, VAR_SPAWN_SWITCHES_DESIRED, 0);
        obj_id[] players = getPlayersInStructure(facility);
        if ((players == null) || (players.length == 0))
        {
            setObjVar(facility, VAR_SPAWN_POPULATION_PLAYER, 0);
        }
        else 
        {
            setObjVar(facility, VAR_SPAWN_POPULATION_PLAYER, players.length);
        }
        if (!hasObjVar(facility, VAR_SPAWN_POPULATION_THRESHOLD))
        {
            setObjVar(facility, VAR_SPAWN_POPULATION_THRESHOLD, DEFAULT_THRESHOLD);
        }
        return true;
    }
    public static int getSpawnSwitchState(obj_id facility, int idx) throws InterruptedException
    {
        if (facility != null)
        {
            if (hasObjVar(facility, VAR_SPAWN_SWITCHES_CURRENT))
            {
                if ((idx >= 0) && (idx < utils.BIT_LIST_SIZE))
                {
                    int switches = getIntObjVar(facility, VAR_SPAWN_SWITCHES_CURRENT);
                    if (utils.checkBit(switches, idx))
                    {
                        return 1;
                    }
                    return 0;
                }
            }
        }
        return -1;
    }
    public static boolean setSpawnSwitchState(obj_id facility, int idx, boolean setActive) throws InterruptedException
    {
        if (facility != null)
        {
            if (hasObjVar(facility, VAR_SPAWN_SWITCHES_CURRENT))
            {
                if ((idx >= 0) && (idx < utils.BIT_LIST_SIZE))
                {
                    int switches = getIntObjVar(facility, VAR_SPAWN_SWITCHES_CURRENT);
                    if (setActive)
                    {
                        switches = utils.setBit(switches, idx);
                    }
                    else 
                    {
                        switches = utils.clearBit(switches, idx);
                    }
                    return setObjVar(facility, VAR_SPAWN_SWITCHES_CURRENT, switches);
                }
            }
        }
        return false;
    }
    public static boolean resetFillerSpawns(obj_id building) throws InterruptedException
    {
        LOG("FILLER_BUILDING", "resetFillerSpawns: entered!");
        if (building == null)
        {
            return false;
        }
        if (hasObjVar(building, VAR_FILLER_SPAWN_CURRENT_EGG))
        {
            if (!cleanupFillerSpawns(building))
            {
                LOG("FILLER_BUILDING", "resetFillerSpawns: unable to cleanup existing spawns");
                return false;
            }
        }
        boolean doContinue = true;
        int count = 0;
        do
        {
            LOG("FILLER_BUILDING", "resetFillerSpawns: attempting to spawn next template");
            if (!spawnNextFillerEggTemplate(building))
            {
                LOG("FILLER_BUILDING", "resetFillerSpawns: SPAWN FAILED!");
                doContinue = false;
            }
            else 
            {
                count++;
            }
        } while (doContinue);
        return count > 0;
    }
    public static boolean cleanupFillerSpawns(obj_id building) throws InterruptedException
    {
        if (building == null)
        {
            return false;
        }
        obj_id[] eggs = getObjIdArrayObjVar(building, VAR_FILLER_SPAWN_CURRENT_EGG);
        if ((eggs != null) && (eggs.length != 0)) {
            for (obj_id egg : eggs) {
                destroyObject(egg);
            }
        }
        removeObjVar(building, VAR_FILLER_SPAWN_CURRENT_BASE);
        return true;
    }
    public static dictionary getFillerEggTemplate(obj_id building, int spawnIdx, int eggIdx) throws InterruptedException
    {
        if ((building == null) || (spawnIdx < -1) || (eggIdx < -1))
        {
            return null;
        }
        String buildingTemplate = getTemplateName(building);
        String table = DATATABLE_FILLER_BASE + getCurrentSceneName() + DATATABLE_FILLER_SPAWN_ENDING;
        if (dataTableOpen(table))
        {
            String[] buildingTemplates = dataTableGetStringColumn(table, DATATABLE_COL_TEMPLATE);
            dictionary d;
            location bLoc;
            location loc;
            String lookup;
            String[] eggs;
            for (int i = 0; i < buildingTemplates.length; i++)
            {
                if (buildingTemplates[i].equals(buildingTemplate))
                {
                    d = new dictionary();
                    int idx = dataTableGetInt(table, i, DATATABLE_COL_IDX);
                    if ((spawnIdx == -1) || (spawnIdx == idx))
                    {
                        d.put(DICT_IDX, idx);
                        float dx = dataTableGetFloat(table, i, DATATABLE_COL_X);
                        float dy = dataTableGetFloat(table, i, DATATABLE_COL_Y);
                        float dz = dataTableGetFloat(table, i, DATATABLE_COL_Z);
                        bLoc = getLocation(building);
                        loc = new location(bLoc.x + dx, bLoc.y + dy, bLoc.z + dz, bLoc.area, bLoc.cell);
                        float bYaw = getYaw(building);
                        if (bYaw != 0.0f)
                        {
                            float dist = utils.getDistance2D(bLoc, loc);
                            loc = utils.rotatePointXZ(bLoc, dist, bYaw);
                        }
                        d.put(DICT_LOC, loc);
                        lookup = DATATABLE_FILLER_BASE + getCurrentSceneName() + DATATABLE_FILLER_EGG_ENDING;
                        if (dataTableOpen(lookup))
                        {
                            eggs = dataTableGetStringColumn(lookup, DATATABLE_COL_TEMPLATE);
                            switch (eggIdx)
                            {
                                case -1:
                                    eggIdx = rand(0, eggs.length - 1);
                                    default:
                                if (eggIdx < eggs.length)
                                {
                                    d.put(DICT_TEMPLATE, eggs[eggIdx]);
                                    return d;
                                }
                                else 
                                {
                                    return null;
                                }
                            }
                        }
                    }
                }
            }
        }
        return null;
    }
    public static dictionary getRandomFillerEggTemplate(obj_id building) throws InterruptedException
    {
        if (building == null)
        {
            return null;
        }
        return getFillerEggTemplate(building, -1, -1);
    }
    public static dictionary getNextFillerEggTemplate(obj_id building) throws InterruptedException
    {
        if (building == null)
        {
            return null;
        }
        int spawnIdx = -1;
        int eggIdx = -1;
        obj_id[] eggs = getObjIdArrayObjVar(building, VAR_FILLER_SPAWN_CURRENT_EGG);
        if ((eggs == null) || (eggs.length == 0))
        {
            spawnIdx = 0;
        }
        else 
        {
            spawnIdx = eggs.length;
        }
        return getFillerEggTemplate(building, spawnIdx, eggIdx);
    }
    public static boolean spawnNextFillerEggTemplate(obj_id building) throws InterruptedException
    {
        LOG("FILLER_BUILDING", "spawnNextFillerEggTemplate: entered!");
        if (building == null)
        {
            return false;
        }
        dictionary d = getNextFillerEggTemplate(building);
        if (d == null)
        {
            LOG("FILLER_BUILDING", "spawnNextFillerEggTemplate: unable to retrieve next egg template!");
            return false;
        }
        int idx = d.getInt(DICT_IDX);
        obj_id[] eggs = getObjIdArrayObjVar(building, VAR_FILLER_SPAWN_CURRENT_EGG);
        if ((eggs == null) || (eggs.length == 0))
        {
            if (idx == 0)
            {
                return spawnFillerEgg(building, d);
            }
        }
        else 
        {
            if (idx == eggs.length)
            {
                return spawnFillerEgg(building, d);
            }
        }
        return false;
    }
    public static boolean spawnFillerEgg(obj_id building, dictionary params) throws InterruptedException
    {
        LOG("FILLER_BUILDING", "spawnFillerEgg: entered!");
        if ((building == null) || (params == null))
        {
            return false;
        }
        String template = params.getString(DICT_TEMPLATE);
        location loc = params.getLocation(DICT_LOC);
        LOG("FILLER_BUILDING", "spawnFillerEgg: template = " + template);
        LOG("FILLER_BUILDING", "spawnFillerEgg: loc = " + loc);
        obj_id egg = createObject(template, loc);
        if (egg == null)
        {
            LOG("FILLER_BUILDING", "spawnFillerEgg: unable to create spawn egg!");
            return false;
        }
        setYaw(egg, rand(0, 360));
        Vector eggs = getResizeableObjIdArrayObjVar(building, VAR_FILLER_SPAWN_CURRENT_EGG);
        Vector locs = getResizeableLocationArrayObjVar(building, VAR_FILLER_SPAWN_CURRENT_LOC);
        eggs = utils.addElement(eggs, egg);
        locs = utils.addElement(locs, loc);
        if (eggs.size() > 0 && locs.size() > 0)
        {
            setObjVar(building, VAR_FILLER_SPAWN_CURRENT_EGG, eggs);
            setObjVar(building, VAR_FILLER_SPAWN_CURRENT_LOC, locs);
            return true;
        }
        else 
        {
            return false;
        }
    }
}
