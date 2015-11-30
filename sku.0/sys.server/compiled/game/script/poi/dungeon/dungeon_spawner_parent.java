package script.poi.dungeon;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.factions;
import script.library.create;
import script.library.hq;
import script.library.utils;

public class dungeon_spawner_parent extends script.base_script
{
    public dungeon_spawner_parent()
    {
    }
    public static final float SPAWN_DELAY = 1.0f;
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        messageTo(self, "startWaves", null, 10, false);
        return SCRIPT_CONTINUE;
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        messageTo(self, "startWaves", null, 1, false);
        return SCRIPT_CONTINUE;
    }
    public int startWaves(obj_id self, dictionary params) throws InterruptedException
    {
        deltadictionary dctScriptVars = self.getScriptVars();
        obj_id[] objSpawners = getDungeonSpawners(self);
        for (int intI = 0; intI < objSpawners.length; intI++)
        {
            detachScript(objSpawners[intI], "poi.interior_spawner.interior_spawner");
        }
        dctScriptVars.put("objSpawners", objSpawners);
        String strDataTable = getStringObjVar(self, "strDataTable");
        strDataTable = "datatables/dungeon/" + strDataTable + ".iff";
        LOG("dungeon", "strDataTable is " + strDataTable);
        dctScriptVars.put("intWave", 1);
        dictionary dctInfo = dataTableGetRow(strDataTable, 0);
        String strCompletionStringId = dctInfo.getString("strCompletionStringId");
        setObjVar(self, "strCompletionStringId", strCompletionStringId);
        int intMinWaveDelay = dctInfo.getInt("intMinWaveDelay");
        dctScriptVars.put("intMinWaveDelay", intMinWaveDelay);
        int intMaxWaveDelay = dctInfo.getInt("intMaxWaveDelay");
        dctScriptVars.put("intMaxWaveDelay", intMaxWaveDelay);
        int intMinRecycleTime = dctInfo.getInt("intMinRecycleTime");
        dctScriptVars.put("intMinRecycleTime", intMinRecycleTime);
        int intMaxRecycleTime = dctInfo.getInt("intMaxRecycleTime");
        dctScriptVars.put("intMaxRecycleTime", intMaxRecycleTime);
        int intNumWaves = dctInfo.getInt("intNumWaves");
        dctScriptVars.put("intNumWaves", intNumWaves);
        dctScriptVars.put("strDataTable", strDataTable);
        dctScriptVars.put("intIndex", 0);
        messageTo(self, "spawnWave", null, 1, false);
        return SCRIPT_CONTINUE;
    }
    public int spawnWave(obj_id self, dictionary params) throws InterruptedException
    {
        deltadictionary dctScriptVars = self.getScriptVars();
        int intWave = dctScriptVars.getInt("intWave");
        int intIndex = dctScriptVars.getInt("intIndex");
        String strDataTable = dctScriptVars.getString("strDataTable");
        String[] strSpawns = dataTableGetStringColumnNoDefaults(strDataTable, "strWave" + intWave);
        dctScriptVars.put("strSpawns", strSpawns);
        for (int intI = 0; intI < strSpawns.length; intI++)
        {
            LOG("dungeon", "strSpawns " + intI + " is " + strSpawns[intI]);
        }
        LOG("dungeon", "Wave is " + intWave + " and column name is strWave" + intWave);
        dctScriptVars.put("intWaveSize", strSpawns.length);
        LOG("dungeon", "Start intIndex is " + intIndex);
        location locSpawnLocation = getDungeonSpawnLocation(self);
        obj_id objMob = create.object(strSpawns[intIndex], locSpawnLocation);
        LOG("dungeon", "Spawning a " + strSpawns[intIndex]);
        attachScript(objMob, "poi.dungeon.dungeon_mob");
        setObjVar(objMob, "objParent", self);
        intIndex = intIndex + 1;
        LOG("dungeon", "spawns length is " + strSpawns.length);
        dctScriptVars.put("intIndex", intIndex);
        if (intIndex < strSpawns.length)
        {
            dctScriptVars.put("intIndex", intIndex);
            messageTo(self, "continueSpawns", null, SPAWN_DELAY, false);
        }
        return SCRIPT_CONTINUE;
    }
    public int continueSpawns(obj_id self, dictionary params) throws InterruptedException
    {
        deltadictionary dctScriptVars = self.getScriptVars();
        int intIndex = dctScriptVars.getInt("intIndex");
        LOG("dungeon", "intIndex in continue spawns is  " + intIndex);
        String[] strSpawns = dctScriptVars.getStringArray("strSpawns");
        if (strSpawns == null || strSpawns.length == 0)
        {
            return SCRIPT_CONTINUE;
        }
        location locSpawnLocation = getDungeonSpawnLocation(self);
        if (locSpawnLocation == null)
        {
            obj_id objMob = create.object(strSpawns[intIndex], locSpawnLocation);
            if (isIdValid(objMob))
            {
                attachScript(objMob, "poi.dungeon.dungeon_mob");
                setObjVar(objMob, "objParent", self);
                LOG("dungeon", "continue spawn is spawnign a " + strSpawns[intIndex]);
                intIndex = intIndex + 1;
                dctScriptVars.put("intIndex", intIndex);
                LOG("dungeon", "intIndex is now " + intIndex + " and length is " + strSpawns.length);
                if (intIndex < strSpawns.length)
                {
                    messageTo(self, "continueSpawns", null, SPAWN_DELAY, false);
                }
            }
        }
        return SCRIPT_CONTINUE;
    }
    public location getDungeonSpawnLocation(obj_id objParent) throws InterruptedException
    {
        if (!isIdValid(objParent))
        {
            return null;
        }
        deltadictionary dctScriptVars = objParent.getScriptVars();
        obj_id[] objSpawners = dctScriptVars.getObjIdArray("objSpawners");
        if (objSpawners == null || objSpawners.length == 0)
        {
            return null;
        }
        location locSpawnLocation = getLocation(objSpawners[rand(0, objSpawners.length - 1)]);
        if (locSpawnLocation == null)
        {
            return null;
        }
        locSpawnLocation.x = locSpawnLocation.x + rand(-1, 1);
        locSpawnLocation.z = locSpawnLocation.z + rand(-1, 1);
        return locSpawnLocation;
    }
    public int elementDestroyed(obj_id self, dictionary params) throws InterruptedException
    {
        deltadictionary dctScriptVars = self.getScriptVars();
        int intWaveSize = dctScriptVars.getInt("intWaveSize");
        int intNumKilled = dctScriptVars.getInt("intNumKilled");
        LOG("dungeon", "ELEMENT DESTROUED");
        LOG("dungeon", "intWaveSize is " + intWaveSize);
        intNumKilled = intNumKilled + 1;
        LOG("dungeon", "intKilled is " + intNumKilled);
        if (intNumKilled >= intWaveSize)
        {
            resetWave(self);
        }
        else 
        {
            dctScriptVars.put("intNumKilled", intNumKilled);
        }
        return SCRIPT_CONTINUE;
    }
    public void resetWave(obj_id objParent) throws InterruptedException
    {
        deltadictionary dctScriptVars = objParent.getScriptVars();
        dctScriptVars.put("intIndex", 0);
        dctScriptVars.put("intNumKilled", 0);
        int intNumWaves = dctScriptVars.getInt("intNumWaves");
        int intWave = dctScriptVars.getInt("intWave");
        intWave = intWave + 1;
        LOG("dungeon", "Intwave is " + intWave + " and intNumWaves is " + intNumWaves);
        if (intWave > intNumWaves)
        {
            int intMinRecycleTime = dctScriptVars.getInt("intMinRecycleTime");
            int intMaxRecycleTime = dctScriptVars.getInt("intMaxRecycleTime");
            dctScriptVars.put("intWave", 1);
            messageTo(objParent, "spawnWave", null, rand(intMinRecycleTime, intMaxRecycleTime), false);
            String strCompletionStringId = getStringObjVar(objParent, "strCompletionStringId");
            if ((strCompletionStringId != null) && (!strCompletionStringId.equals("")))
            {
                string_id strSpam = utils.unpackString(strCompletionStringId);
                obj_id[] objPlayers = getAllPlayers(getLocation(objParent), 150);
                if ((objPlayers != null) && (objPlayers.length != 0))
                {
                    for (int intI = 0; intI < objPlayers.length; intI++)
                    {
                        sendSystemMessage(objPlayers[intI], strSpam);
                    }
                }
            }
        }
        else 
        {
            int intMinWaveDelay = dctScriptVars.getInt("intMinWaveDelay");
            int intMaxWaveDelay = dctScriptVars.getInt("intMaxWaveDelay");
            dctScriptVars.put("intWave", intWave);
            messageTo(objParent, "spawnWave", null, rand(intMinWaveDelay, intMaxWaveDelay), false);
        }
    }
    public obj_id[] getDungeonSpawners(obj_id objParent) throws InterruptedException
    {
        Vector objSpawners = new Vector();
        objSpawners.setSize(0);
        obj_id[] objObjects = getAllObjectsWithObjVar(getLocation(objParent), 250, "type");
        for (int intI = 0; intI < objObjects.length; intI++)
        {
            obj_id[] objContents = getContents(objObjects[intI]);
            if (objContents != null)
            {
                for (int intM = 0; intM < objContents.length; intM++)
                {
                    if (hasObjVar(objContents[intM], "type"))
                    {
                        objSpawners = utils.addElement(objSpawners, objContents[intM]);
                    }
                }
            }
            else 
            {
                objSpawners = utils.addElement(objSpawners, objObjects[intI]);
            }
        }
        obj_id[] _objSpawners = new obj_id[0];
        if (objSpawners != null)
        {
            _objSpawners = new obj_id[objSpawners.size()];
            objSpawners.toArray(_objSpawners);
        }
        return _objSpawners;
    }
}
