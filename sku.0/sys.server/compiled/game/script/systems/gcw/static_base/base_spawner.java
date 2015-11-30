package script.systems.gcw.static_base;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.ai_lib;
import script.library.create;
import script.library.utils;

public class base_spawner extends script.base_script
{
    public base_spawner()
    {
    }
    public static final String SPAWNED_LIST = "spawned";
    public static final String SPAWNED = SPAWNED_LIST + ".spawnNumber_";
    public static final String VAR_BASE_MASTER = "gcw.static_base.master";
    public static final String VAR_BASE_STATUS = "gcw.static_base.base_status";
    public static final int NO_CONTROL = 0;
    public static final int IMPERIAL_CONTROL = 1;
    public static final int REBEL_CONTROL = 2;
    public int OnAttach(obj_id self) throws InterruptedException
    {
        messageTo(self, "handleBeginSpawnRequest", null, 20f, false);
        return SCRIPT_CONTINUE;
    }
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        messageTo(self, "handleBeginSpawnRequest", null, 20f, false);
        return SCRIPT_CONTINUE;
    }
    public int handleBeginSpawnRequest(obj_id self, dictionary params) throws InterruptedException
    {
        location loc = getLocation(self);
        String datatable = "datatables/gcw/static_base/base_spawn_" + loc.area + ".iff";
        int numObjects = dataTableGetNumRows(datatable);
        int x = utils.getIntScriptVar(self, "spawnCounter");
        while (x < numObjects)
        {
            if (hasObjVar(self, SPAWNED + x))
            {
                obj_id check = getObjIdObjVar(self, SPAWNED + x);
                if (!check.isLoaded())
                {
                    spawn(x, datatable, self);
                }
            }
            else 
            {
                spawn(x, datatable, self);
            }
            x++;
        }
        return SCRIPT_CONTINUE;
    }
    public int handleBaseCleanupRequest(obj_id self, dictionary params) throws InterruptedException
    {
        obj_var_list ovl = getObjVarList(self, SPAWNED_LIST);
        obj_id[] objects = null;
        if ((ovl != null) && (ovl.getNumItems() > 0))
        {
            int numItems = ovl.getNumItems();
            objects = new obj_id[numItems];
            for (int i = 0; i < numItems; i++)
            {
                objects[i] = getObjIdObjVar(self, SPAWNED + i);
                removeObjVar(self, SPAWNED + i);
            }
        }
        if (objects == null || objects.length == 0)
        {
            return SCRIPT_CONTINUE;
        }
        for (int i = 0; i < objects.length; i++)
        {
            if (!isIdValid(objects[i]))
            {
                continue;
            }
            if (objects[i].isLoaded())
            {
                destroyObject(objects[i]);
            }
            else 
            {
                messageTo(objects[i], "handleDestroyRequest", null, 0.0f, false);
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int handleNpcDeath(obj_id self, dictionary params) throws InterruptedException
    {
        int spawn_num = params.getInt("spawnNumber");
        int obj_status = params.getInt("status");
        obj_id spawn_obj = params.getObjId("spawnObj");
        if (!hasObjVar(self, VAR_BASE_STATUS))
        {
            return SCRIPT_CONTINUE;
        }
        int status = getIntObjVar(self, VAR_BASE_STATUS);
        if (status == 0 || status != obj_status)
        {
            return SCRIPT_CONTINUE;
        }
        if (hasObjVar(self, SPAWNED + spawn_num) && (spawn_obj == getObjIdObjVar(self, SPAWNED + spawn_num)))
        {
            location loc = getLocation(self);
            String datatable = "datatables/gcw/static_base/base_spawn_" + loc.area + ".iff";
            spawn(spawn_num, datatable, self);
        }
        return SCRIPT_CONTINUE;
    }
    public void spawn(int x, String datatable, obj_id self) throws InterruptedException
    {
        if (!hasObjVar(self, VAR_BASE_STATUS))
        {
            return;
        }
        int status = getIntObjVar(self, VAR_BASE_STATUS);
        if (status == NO_CONTROL)
        {
            return;
        }
        String spawnCol = "rebel";
        if (status == IMPERIAL_CONTROL)
        {
            spawnCol = "imperial";
        }
        String spawn = dataTableGetString(datatable, x, spawnCol);
        float xCoord = dataTableGetFloat(datatable, x, "loc_x");
        float yCoord = dataTableGetFloat(datatable, x, "loc_y");
        float zCoord = dataTableGetFloat(datatable, x, "loc_z");
        location myself = getLocation(self);
        String planet = myself.area;
        String spawnRoom = dataTableGetString(datatable, x, "room");
        obj_id room = null;
        if (!spawnRoom.equals("world"))
        {
            room = getCellId(self, spawnRoom);
        }
        if (!isIdValid(room) && !spawnRoom.equals("world"))
        {
            removeObjVar(self, SPAWNED + x);
            return;
        }
        location spawnPoint = new location(xCoord, yCoord, zCoord, planet, room);
        obj_id spawnedObject = create.object(spawn, spawnPoint);
        if (!isIdValid(spawnedObject))
        {
            removeObjVar(self, SPAWNED + x);
            return;
        }
        setObjVar(self, SPAWNED + x, spawnedObject);
        setObjVar(spawnedObject, "spawn_number", x);
        setObjVar(spawnedObject, "master", self);
        setObjVar(spawnedObject, "status", status);
        attachScript(spawnedObject, "systems.gcw.static_base.spawned_object");
        if (dataTableHasColumn(datatable, "script"))
        {
            String scriptList = dataTableGetString(datatable, x, "script");
            if (scriptList != null && !scriptList.equals(""))
            {
                String[] scriptArray = split(scriptList, ',');
                for (int i = 0; i < scriptArray.length; i++)
                {
                    attachScript(spawnedObject, scriptArray[i]);
                }
            }
        }
        if (dataTableHasColumn(datatable, "yaw"))
        {
            float spawnYaw = dataTableGetFloat(datatable, x, "yaw");
            if (spawnYaw != 0)
            {
                setYaw(spawnedObject, spawnYaw);
            }
        }
        if (dataTableHasColumn(datatable, "objvar"))
        {
            String objVarList = dataTableGetString(datatable, x, "objvar");
            if (objVarList != null && !objVarList.equals(""))
            {
                setObjectObjVars(spawnedObject, objVarList);
            }
        }
        if (isMob(spawnedObject))
        {
            int behavior = ai_lib.BEHAVIOR_SENTINEL;
            if (dataTableHasColumn(datatable, "behavior"))
            {
                behavior = dataTableGetInt(datatable, x, "behavior");
            }
            ai_lib.setDefaultCalmBehavior(spawnedObject, behavior);
        }
    }
    public void setObjectObjVars(obj_id obj, String objVarList) throws InterruptedException
    {
        if (objVarList == null || objVarList.equals(""))
        {
            return;
        }
        String[] pairs = split(objVarList, ',');
        for (int i = 0; i < pairs.length; i++)
        {
            String[] objVarToSet = split(pairs[i], '=');
            String objVarValue = objVarToSet[1];
            String[] objVarNameAndType = split(objVarToSet[0], ':');
            String objVarType = objVarNameAndType[0];
            String objVarName = objVarNameAndType[1];
            if (objVarType.equals("string"))
            {
                setObjVar(obj, objVarName, objVarValue);
            }
            else if (objVarType.equals("int"))
            {
                setObjVar(obj, objVarName, utils.stringToInt(objVarValue));
            }
            else if (objVarType.equals("float"))
            {
                setObjVar(obj, objVarName, utils.stringToFloat(objVarValue));
            }
            else if (objVarType.equals("boolean") || objVarType.equals("bool"))
            {
                setObjVar(obj, objVarName, utils.stringToInt(objVarValue));
            }
            else 
            {
                setObjVar(obj, objVarName, objVarValue);
            }
        }
    }
    public boolean canSpawnByConfigSetting() throws InterruptedException
    {
        String disableSpawners = getConfigSetting("GameServer", "disableGenericSpawner");
        if (disableSpawners == null)
        {
            return true;
        }
        if (disableSpawners.equals("true") || disableSpawners.equals("1"))
        {
            return false;
        }
        return true;
    }
}
