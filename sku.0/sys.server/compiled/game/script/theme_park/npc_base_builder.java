package script.theme_park;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.utils;
import script.library.trial;
import script.library.create;
import script.library.ai_lib;

public class npc_base_builder extends script.base_script
{
    public npc_base_builder()
    {
    }
    public static final String SPAWN_DATATABLE = "base_builder.spawn_datatable";
    public static final String SPAWN_LIST = "base_builder.spawn_list";
    public static final String SPAWN_POSITION = "base_builder.spawn_position";
    public static final String CONTROLLER_ID = "base_builder.unique_tag";
    public static final String MASTER_OBJECT = "base_builder.master_object";
    public static final String PHASE_ARRAY = "base_builder.phase_array";
    public static final String CURRENT_PHASE = "base_builder.current_phase";
    public static final String OVERRIDE_PHASE = "base_builder.override_phase";
    public static final boolean LOGGING = false;
    public int OnAttach(obj_id self) throws InterruptedException
    {
        dictionary dict = trial.getSessionDict(self);
        messageTo(self, "initializePhase", dict, 5, false);
        if (hasObjVar(self, "element"))
        {
            setName(self, getStringObjVar(self, "element"));
        }
        return SCRIPT_CONTINUE;
    }
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        dictionary dict = trial.getSessionDict(self);
        messageTo(self, "initializePhase", dict, 5, false);
        if (hasObjVar(self, "element"))
        {
            setName(self, getStringObjVar(self, "element"));
        }
        return SCRIPT_CONTINUE;
    }
    public int OnDetach(obj_id self) throws InterruptedException
    {
        cleanupChildren(self);
        return SCRIPT_CONTINUE;
    }
    public int OnDestroy(obj_id self) throws InterruptedException
    {
        cleanupChildren(self);
        return SCRIPT_CONTINUE;
    }
    public int cleanupEvent(obj_id self, dictionary params) throws InterruptedException
    {
        String dataTable = getStringObjVar(self, SPAWN_DATATABLE);
        if (hasObjVar(self, SPAWN_LIST))
        {
            cleanupChildren(self);
        }
        int spawnPhase = getCurrentPhase(self);
        if (hasObjVar(self, OVERRIDE_PHASE))
        {
            spawnPhase = getIntObjVar(self, OVERRIDE_PHASE);
        }
        doSpawning(self, dataTable, spawnPhase);
        return SCRIPT_CONTINUE;
    }
    public int initializePhase(obj_id self, dictionary params) throws InterruptedException
    {
        if (!trial.verifySession(self, params))
        {
            return SCRIPT_CONTINUE;
        }
        if (!hasObjVar(self, SPAWN_DATATABLE))
        {
            doLogging("initializePhase", "Did not have the " + SPAWN_DATATABLE + " objvar");
            return SCRIPT_CONTINUE;
        }
        String dataTable = getStringObjVar(self, SPAWN_DATATABLE);
        if (!dataTableOpen(dataTable))
        {
            doLogging("initializePhase", dataTable + " was not a valid table");
            return SCRIPT_CONTINUE;
        }
        if (hasObjVar(self, SPAWN_LIST))
        {
            cleanupChildren(self);
        }
        int spawnPhase = getCurrentPhase(self);
        if (hasObjVar(self, OVERRIDE_PHASE))
        {
            spawnPhase = getIntObjVar(self, OVERRIDE_PHASE);
        }
        doSpawning(self, dataTable, spawnPhase);
        return SCRIPT_CONTINUE;
    }
    public void cleanupChildren(obj_id self) throws InterruptedException
    {
        obj_id[] objects = getObjectsInRange(getSelf(), 400.0f);
        if (objects == null || objects.length == 0)
        {
            removeObjVar(self, SPAWN_LIST);
            return;
        }
        boolean isMaster = ((getStringObjVar(self, "element")).equals("ph1_restuss_master"));
        for (int i = 0; i < objects.length; i++)
        {
            if (trial.isChild(self, objects[i]))
            {
                trial.cleanupObject(objects[i]);
                continue;
            }
            if (isMaster)
            {
                if (hasObjVar(objects[i], trial.PARENT))
                {
                    if (!isIdValid(trial.getParent(objects[i])) || !hasScript(trial.getParent(objects[i]), "theme_park.npc_base_builder"))
                    {
                        trial.cleanupObject(objects[i]);
                    }
                }
            }
        }
        removeObjVar(self, SPAWN_LIST);
        return;
    }
    public void doSpawning(obj_id self, String dataTable, int phase) throws InterruptedException
    {
        doSpawning(self, dataTable, phase, -1);
    }
    public void doSpawning(obj_id self, String dataTable, int phase, int lineNumber) throws InterruptedException
    {
        final int SPAWN_OFFSET = 0;
        final int SPAWN_LITERAL = 1;
        int length = dataTableGetNumRows(dataTable);
        Vector children = new Vector();
        children.setSize(0);
        String LAST_POB = "lastPob";
        if (phase > -1)
        {
            setObjVar(self, CURRENT_PHASE, phase);
        }
        for (int i = 0; i < length; i++)
        {
            dictionary data = dataTableGetRow(dataTable, i);
            String toSpawn = data.getString("object");
            String spawnPhase = data.getString("phase");
            int locationType = data.getInt("location_type");
            float locX = data.getFloat("x_offset");
            float locY = data.getFloat("y_offset");
            float locZ = data.getFloat("z_offset");
            float yaw = data.getFloat("yaw");
            String cell = data.getString("cell");
            String scriptString = data.getString("script");
            String objvarString = data.getString("objvar");
            String respawn = data.getString("respawn");
            if (!validatePhase(phase, spawnPhase) && i != lineNumber)
            {
                continue;
            }
            obj_id newObject = null;
            if (!cell.equals("world"))
            {
                if (!utils.hasScriptVar(self, LAST_POB))
                {
                    obj_id[] cells = getCellIds(self);
                    if (cells == null)
                    {
                        doLogging("doSpawning", "I am the parent object(" + getName(self) + "/" + self + ") but I have no cells. skipping creation of " + toSpawn);
                        continue;
                    }
                    else 
                    {
                        utils.setScriptVar(self, LAST_POB, self);
                    }
                }
                obj_id lastPob = utils.getObjIdScriptVar(self, LAST_POB);
                if (!hasCell(lastPob, cell))
                {
                    doLogging("doSpawning", "The pob in question(" + getName(lastPob) + "/" + lastPob + ") does not have the required cell(" + cell + ") skipping creation of " + toSpawn);
                    continue;
                }
                obj_id cellId = getCellId(lastPob, cell);
                if (!isIdValid(cellId))
                {
                    doLogging("doSpawning", "Specified cell was invalid. skipping creation of " + toSpawn);
                    continue;
                }
                location spawnLoc = new location(locX, locY, locZ, getCurrentSceneName(), cellId);
                if (toSpawn.endsWith(".iff"))
                {
                    newObject = createObjectInCell(toSpawn, lastPob, cell, spawnLoc);
                }
                else 
                {
                    newObject = create.object(toSpawn, spawnLoc);
                    ai_lib.setDefaultCalmBehavior(newObject, ai_lib.BEHAVIOR_SENTINEL);
                }
            }
            else 
            {
                if (locationType == SPAWN_OFFSET)
                {
                    location baseLoc = getLocation(self);
                    locX += baseLoc.x;
                    locY += baseLoc.y;
                    locZ += baseLoc.z;
                }
                location spawnLoc = new location(locX, locY, locZ);
                if (toSpawn.endsWith(".iff"))
                {
                    newObject = createObject(toSpawn, spawnLoc);
                    obj_id[] cells = getCellIds(newObject);
                    if (cells != null && cells.length > 0)
                    {
                        utils.setScriptVar(self, LAST_POB, newObject);
                    }
                }
                else 
                {
                    newObject = create.object(toSpawn, spawnLoc);
                    ai_lib.setDefaultCalmBehavior(newObject, ai_lib.BEHAVIOR_SENTINEL);
                }
            }
            if (!isIdValid(newObject))
            {
                doLogging("doSpawning", "Failed to create object(" + toSpawn + ")");
                continue;
            }
            trial.setParent(self, newObject, true);
            setObjVar(newObject, SPAWN_POSITION, i);
            setSpawnObjVar(newObject, objvarString);
            attachSpawnScript(newObject, scriptString, respawn);
            setYaw(newObject, yaw);
            setObjVar(self, SPAWN_LIST, "base_builder.childrenInWorld");
        }
    }
    public int handleEntityRespawn(obj_id self, dictionary params) throws InterruptedException
    {
        if (!trial.verifySession(self, params))
        {
            return SCRIPT_CONTINUE;
        }
        int lineNumber = params.getInt("spawnNumber");
        String dataTable = getStringObjVar(self, SPAWN_DATATABLE);
        if (!dataTableOpen(dataTable))
        {
            doLogging("hnadleEntityRespawn", dataTable + " was not a valid table");
            return SCRIPT_CONTINUE;
        }
        doSpawning(self, dataTable, -1, lineNumber);
        return SCRIPT_CONTINUE;
    }
    public boolean validatePhase(int phase, String spawnPhase) throws InterruptedException
    {
        String[] parse = split(spawnPhase, ',');
        for (int i = 0; i < parse.length; i++)
        {
            int intParse = utils.stringToInt(parse[i]);
            if (intParse == -1 || intParse == phase)
            {
                return true;
            }
        }
        return false;
    }
    public void attachSpawnScript(obj_id newObject, String scriptString, String respawn) throws InterruptedException
    {
        if (!respawn.equals("-1"))
        {
            setObjVar(newObject, "respawn", respawn);
            attachScript(newObject, "theme_park.npc_base_builder_respawn");
        }
        if (scriptString.equals("none"))
        {
            return;
        }
        String[] parse = split(scriptString, ';');
        if (parse == null || parse.length == 0)
        {
            return;
        }
        for (int i = 0; i < parse.length; i++)
        {
            attachScript(newObject, parse[i]);
        }
    }
    public void setSpawnObjVar(obj_id newObject, String objvarString) throws InterruptedException
    {
        if (objvarString.equals("none"))
        {
            return;
        }
        String[] parse = split(objvarString, ';');
        if (parse == null || parse.length == 0)
        {
            return;
        }
        for (int i = 0; i < parse.length; i++)
        {
            String[] typeDataSplit = split(parse[i], ':');
            String type = typeDataSplit[0];
            String data = typeDataSplit[1];
            String[] nameValueSplit = split(data, '=');
            String name = nameValueSplit[0];
            String value = nameValueSplit[1];
            if (type.equals("int"))
            {
                setObjVar(newObject, name, utils.stringToInt(value));
            }
            if (type.equals("float"))
            {
                setObjVar(newObject, name, utils.stringToFloat(value));
            }
            if (type.equals("string"))
            {
                setObjVar(newObject, name, value);
            }
            if (type.equals("boolean") && (value.equals("true") || value.equals("1")))
            {
                setObjVar(newObject, name, true);
            }
            if (type.equals("boolean") && (value.equals("false") || value.equals("0")))
            {
                setObjVar(newObject, name, false);
            }
        }
    }
    public int incrimentPhase(obj_id self, dictionary params) throws InterruptedException
    {
        incrimentPhase(self);
        return SCRIPT_CONTINUE;
    }
    public void incrimentPhase(obj_id self) throws InterruptedException
    {
        if (!canIncrimentPhase(self))
        {
            doLogging("incrimentPhase", "Cannot incriment phase");
            return;
        }
        if (hasObjVar(self, CURRENT_PHASE))
        {
            setObjVar(self, CURRENT_PHASE, getIntObjVar(self, CURRENT_PHASE) + 1);
        }
        else 
        {
            setObjVar(self, CURRENT_PHASE, 1);
        }
        String dataTable = getStringObjVar(self, SPAWN_DATATABLE);
        if (hasObjVar(self, SPAWN_LIST))
        {
            cleanupChildren(self);
        }
        int spawnPhase = getCurrentPhase(self);
        messageTo(self, "initializePhase", trial.getSessionDict(self), 1.0f, false);
    }
    public boolean canIncrimentPhase(obj_id self) throws InterruptedException
    {
        int phase = 0;
        if (hasObjVar(self, CURRENT_PHASE))
        {
            phase = getIntObjVar(self, CURRENT_PHASE);
        }
        String table = getStringObjVar(self, SPAWN_DATATABLE);
        int length = dataTableGetNumRows(table);
        for (int i = 0; i < length; i++)
        {
            dictionary data = dataTableGetRow(table, i);
            String spawnPhase = data.getString("phase");
            if (validatePhase(phase + 1, spawnPhase))
            {
                return true;
            }
        }
        return false;
    }
    public int decrimentPhase(obj_id self, dictionary params) throws InterruptedException
    {
        decrimentPhase(self);
        return SCRIPT_CONTINUE;
    }
    public void decrimentPhase(obj_id self) throws InterruptedException
    {
        if (!hasObjVar(self, CURRENT_PHASE) || getIntObjVar(self, CURRENT_PHASE) == 0)
        {
            doLogging("decrimentPhase", "Tried to decriment to a negative phase");
            return;
        }
        setObjVar(self, CURRENT_PHASE, getIntObjVar(self, CURRENT_PHASE) - 1);
        String dataTable = getStringObjVar(self, SPAWN_DATATABLE);
        if (hasObjVar(self, SPAWN_LIST))
        {
            cleanupChildren(self);
        }
        int spawnPhase = getCurrentPhase(self);
        messageTo(self, "initializePhase", trial.getSessionDict(self), 1.0f, false);
    }
    public int getCurrentPhase(obj_id self) throws InterruptedException
    {
        if (!hasObjVar(self, CURRENT_PHASE))
        {
            setObjVar(self, CURRENT_PHASE, 0);
        }
        return getIntObjVar(self, CURRENT_PHASE);
    }
    public void doLogging(String section, String message) throws InterruptedException
    {
        if (LOGGING)
        {
            LOG("doLogging/npc_base_builder/" + section, message);
        }
    }
}
