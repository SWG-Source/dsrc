package script.theme_park.dungeon.trando_slave_camp;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.utils;
import script.library.create;

public class bunker_controller extends script.base_script
{
    public bunker_controller()
    {
    }
    public static final String DATATABLE = "datatables/dungeon/trando_slave_camp/camp_command_bunker.iff";
    public static final String[] CELL_NAMES = 
    {
        "entry",
        "hall1",
        "hall2",
        "anteroom",
        "powerroom",
        "computerroom",
        "office",
        "barracks"
    };
    public static final boolean doLogging = false;
    public int beginSpawn(obj_id self, dictionary params) throws InterruptedException
    {
        resetBunker(self);
        return SCRIPT_CONTINUE;
    }
    public int OnHearSpeech(obj_id self, obj_id speaker, String text) throws InterruptedException
    {
        if (isGod(speaker))
        {
            String[] parse = split(text, ' ');
            if (parse[0].equals("bunker"))
            {
                if (parse[1].equals("lockdown"))
                {
                    setEventLocks(self);
                }
                if (parse[1].equals("reset"))
                {
                    resetBunker(self);
                }
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        validateScripts(self);
        return SCRIPT_CONTINUE;
    }
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        validateScripts(self);
        return SCRIPT_CONTINUE;
    }
    public void resetBunker(obj_id bunker) throws InterruptedException
    {
        obj_id[] cellIds = getCellObjIds(bunker);
        if (hasObjVar(bunker, "toskKilled"))
        {
            removeObjVar(bunker, "toskKilled");
        }
        clearDungeon(bunker, cellIds);
        spawnMobs(bunker);
        setEventLocks(bunker);
    }
    public void setEventLocks(obj_id bunker) throws InterruptedException
    {
        doLogging("setEventLocks", "In lock loop");
        obj_id powerRoom = getCellId(bunker, "barracks");
        obj_id sequencer = getCellId(bunker, "powerroom");
        setObjVar(bunker, "powerRoom", powerRoom);
        setObjVar(bunker, "sequencer", sequencer);
        permissionsMakePrivate(powerRoom);
        permissionsMakePrivate(sequencer);
    }
    public void validateScripts(obj_id bunker) throws InterruptedException
    {
        if (!hasScript(getCellId(bunker, "entry"), "theme_park.dungeon.trando_slave_camp.door_signal"))
        {
            attachScript(getCellId(bunker, "entry"), "theme_park.dungeon.trando_slave_camp.door_signal");
        }
    }
    public void doLogging(String section, String message) throws InterruptedException
    {
        if (doLogging)
        {
            LOG("debug_bunker_controller_" + section, message);
        }
    }
    public obj_id[] getCellObjIds(obj_id bunker) throws InterruptedException
    {
        Vector resizeableCellId = new Vector();
        resizeableCellId.setSize(0);
        for (int i = 0; i < CELL_NAMES.length; i++)
        {
            utils.addElement(resizeableCellId, getCellId(bunker, CELL_NAMES[i]));
        }
        obj_id[] _resizeableCellId = new obj_id[0];
        if (resizeableCellId != null)
        {
            _resizeableCellId = new obj_id[resizeableCellId.size()];
            resizeableCellId.toArray(_resizeableCellId);
        }
        return _resizeableCellId;
    }
    public void clearDungeon(obj_id bunker, obj_id[] cellIds) throws InterruptedException
    {
        for (int i = 0; i < cellIds.length; i++)
        {
            obj_id[] cellContents = getContents(cellIds[i]);
            if (cellContents.length > 0)
            {
                for (int k = 0; k < cellContents.length; k++)
                {
                    if (isPlayer(cellContents[k]))
                    {
                        expelFromBuilding(cellContents[k]);
                    }
                    else if (isMob(cellContents[k]))
                    {
                        destroyObject(cellContents[k]);
                    }
                }
            }
        }
        if (hasObjVar(bunker, "terminalObjIdList"))
        {
            obj_id[] terminalList = getObjIdArrayObjVar(bunker, "terminalObjIdList");
            if (terminalList.length > 0)
            {
                for (int j = 0; j < terminalList.length; j++)
                {
                    destroyObject(terminalList[j]);
                }
            }
        }
    }
    public void spawnMobs(obj_id bunker) throws InterruptedException
    {
        int spawnCount = dataTableGetNumRows(DATATABLE);
        Vector resizeableTerminalId = new Vector();
        resizeableTerminalId.setSize(0);
        for (int i = 0; i < spawnCount; i++)
        {
            String object = dataTableGetString(DATATABLE, i, "object");
            String cellName = dataTableGetString(DATATABLE, i, "cell");
            obj_id cellObjId = getCellId(bunker, cellName);
            float locX = dataTableGetFloat(DATATABLE, i, "locx");
            float locY = dataTableGetFloat(DATATABLE, i, "locy");
            float locZ = dataTableGetFloat(DATATABLE, i, "locz");
            float yaw = dataTableGetFloat(DATATABLE, i, "yaw");
            location bunkerLoc = getLocation(bunker);
            String planet = bunkerLoc.area;
            String script1 = dataTableGetString(DATATABLE, i, "script1");
            String script2 = dataTableGetString(DATATABLE, i, "script2");
            location spawnLoc = new location(locX, locY, locZ, planet, cellObjId);
            obj_id spawnObject;
            String preCheck = object.substring(0, 7);
            if (preCheck.equals("object/"))
            {
                spawnObject = createObjectInCell(object, bunker, cellName, spawnLoc);
                if (isIdValid(spawnObject))
                {
                    utils.addElement(resizeableTerminalId, spawnObject);
                }
            }
            else 
            {
                spawnObject = create.object(object, spawnLoc);
            }
            if (isIdValid(spawnObject))
            {
                setYaw(spawnObject, yaw);
                setObjVar(spawnObject, "parent", bunker);
                if (!script1.equals(""))
                {
                    attachScript(spawnObject, script1);
                }
                if (!script2.equals(""))
                {
                    attachScript(spawnObject, script2);
                }
            }
        }
        if (resizeableTerminalId.size() > 0)
        {
            setObjVar(bunker, "terminalObjIdList", resizeableTerminalId);
        }
    }
}
