package script.city.bestine;

import script.deltadictionary;
import script.dictionary;
import script.library.create;
import script.library.utils;
import script.location;
import script.obj_id;

public class city_raid_spawner extends script.base_script
{
    public city_raid_spawner()
    {
    }
    public static final float SPAWN_DELAY = 1.0f;
    public static final int ONE_DAY_IN_SECS = 86400;
    public static final int EIGHT_HOURS_IN_SECS = 28800;
    public int OnAttach(obj_id self) throws InterruptedException
    {
        if (!hasScript(self, "city.bestine.politician_event_npc"))
        {
            attachScript(self, "city.bestine.politician_event_npc");
        }
        if (!hasObjVar(self, "nextWave"))
        {
            int startTime = EIGHT_HOURS_IN_SECS + (rand(1, 2) * ONE_DAY_IN_SECS);
            messageTo(self, "startRaid", null, startTime, true);
        }
        return SCRIPT_CONTINUE;
    }
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        if (!hasScript(self, "city.bestine.politician_event_npc"))
        {
            attachScript(self, "city.bestine.politician_event_npc");
        }
        if (hasObjVar(self, "raidAlreadyStarted"))
        {
            removeObjVar(self, "raidAlreadyStarted");
        }
        return SCRIPT_CONTINUE;
    }
    public int startRaid(obj_id self, dictionary params) throws InterruptedException
    {
        if (hasObjVar(self, "raidAlreadyStarted"))
        {
            return SCRIPT_CONTINUE;
        }
        setObjVar(self, "raidAlreadyStarted", 1);
        deltadictionary dctScriptVars = self.getScriptVars();
        if (hasObjVar(self, "strDataTable"))
        {
            String strDataTable = getStringObjVar(self, "strDataTable");
            dctScriptVars.put("intWave", 1);
            dictionary dctInfo = dataTableGetRow(strDataTable, 0);
            int intMinWaveDelay = dctInfo.getInt("intMinWaveDelay");
            dctScriptVars.put("intMinWaveDelay", intMinWaveDelay);
            int intMaxWaveDelay = dctInfo.getInt("intMaxWaveDelay");
            dctScriptVars.put("intMaxWaveDelay", intMaxWaveDelay);
            int intMinRecycleTime = dctInfo.getInt("intMinCommerceRecycleTime");
            dctScriptVars.put("intMinRecycleTime", intMinRecycleTime);
            int intMaxRecycleTime = dctInfo.getInt("intMaxCommerceRecycleTime");
            dctScriptVars.put("intMaxRecycleTime", intMaxRecycleTime);
            if (hasObjVar(self, "bestine.electionWinner"))
            {
                String electionWinner = getStringObjVar(self, "bestine.electionWinner");
                if (electionWinner.equalsIgnoreCase("Victor"))
                {
                    intMinRecycleTime = dctInfo.getInt("intMinDefenseRecycleTime");
                    dctScriptVars.put("intMinRecycleTime", intMinRecycleTime);
                    intMaxRecycleTime = dctInfo.getInt("intMaxDefenseRecycleTime");
                    dctScriptVars.put("intMaxRecycleTime", intMaxRecycleTime);
                }
            }
            int intNumWaves = dctInfo.getInt("intNumWaves");
            dctScriptVars.put("intNumWaves", intNumWaves);
            dctScriptVars.put("strDataTable", strDataTable);
            dctScriptVars.put("intIndex", 0);
            dctScriptVars.put("intNumKilled", 0);
            messageTo(self, "spawnWave", null, 1, false);
        }
        return SCRIPT_CONTINUE;
    }
    public int spawnWave(obj_id self, dictionary params) throws InterruptedException
    {
        deltadictionary dctScriptVars = self.getScriptVars();
        int intWave = dctScriptVars.getInt("intWave");
        int intIndex = dctScriptVars.getInt("intIndex");
        String strDataTable = dctScriptVars.getString("strDataTable");
        String[] strSpawns = dataTableGetStringColumnNoDefaults(strDataTable, "strWave" + intWave);
        utils.setBatchScriptVar(self, "strSpawns", strSpawns);
        float[] fltSpawnLoc_X = dataTableGetFloatColumn(strDataTable, "fltSpawnLoc_X");
        float[] fltSpawnLoc_Y = dataTableGetFloatColumn(strDataTable, "fltSpawnLoc_Y");
        float[] fltSpawnLoc_Z = dataTableGetFloatColumn(strDataTable, "fltSpawnLoc_Z");
        dctScriptVars.put("fltSpawnLoc_X", fltSpawnLoc_X);
        dctScriptVars.put("fltSpawnLoc_Y", fltSpawnLoc_Y);
        dctScriptVars.put("fltSpawnLoc_Z", fltSpawnLoc_Z);
        int intWaveSize = fltSpawnLoc_X.length;
        dctScriptVars.put("intWaveSize", intWaveSize);
        float coordX = fltSpawnLoc_X[intIndex];
        float coordY = fltSpawnLoc_Y[intIndex];
        float coordZ = fltSpawnLoc_Z[intIndex];
        location locSelf = getLocation(self);
        String strPlanet = locSelf.area;
        location locSpawnLocation = new location(coordX, coordY, coordZ, strPlanet, null);
        int intSpawnChoice = rand(0, (strSpawns.length - 1));
        obj_id objMob = create.object(strSpawns[intSpawnChoice], locSpawnLocation);
        if (isIdValid(objMob))
		{
			attachScript(objMob, "city.bestine.city_raid_mob");
			setObjVar(objMob, "objRaidSpawner", self);
		}
        intIndex = intIndex + 1;
        dctScriptVars.put("intIndex", intIndex);
        if (intIndex < intWaveSize)
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
        String[] strSpawns = utils.getStringBatchScriptVar(self, "strSpawns");
        if (strSpawns == null || strSpawns.length == 0)
        {
            return SCRIPT_CONTINUE;
        }
        float[] fltSpawnLoc_X = dctScriptVars.getFloatArray("fltSpawnLoc_X");
        float[] fltSpawnLoc_Y = dctScriptVars.getFloatArray("fltSpawnLoc_Y");
        float[] fltSpawnLoc_Z = dctScriptVars.getFloatArray("fltSpawnLoc_Z");
        float coordX = fltSpawnLoc_X[intIndex];
        float coordY = fltSpawnLoc_Y[intIndex];
        float coordZ = fltSpawnLoc_Z[intIndex];
        location locSelf = getLocation(self);
        String strPlanet = locSelf.area;
        location locSpawnLocation = new location(coordX, coordY, coordZ, strPlanet, null);
        int intSpawnChoice = rand(0, (strSpawns.length - 1));
        obj_id objMob = create.object(strSpawns[intSpawnChoice], locSpawnLocation);
        if (isIdValid(objMob))
		{
			attachScript(objMob, "city.bestine.city_raid_mob");
			setObjVar(objMob, "objRaidSpawner", self);
		}
        intIndex = intIndex + 1;
        dctScriptVars.put("intIndex", intIndex);
        int intWaveSize = dctScriptVars.getInt("intWaveSize");
        if (intIndex < intWaveSize)
        {
            messageTo(self, "continueSpawns", null, SPAWN_DELAY, false);
        }
        return SCRIPT_CONTINUE;
    }
    public int elementDestroyed(obj_id self, dictionary params) throws InterruptedException
    {
        deltadictionary dctScriptVars = self.getScriptVars();
        int intWaveSize = dctScriptVars.getInt("intWaveSize");
        int intNumKilled = dctScriptVars.getInt("intNumKilled");
        intNumKilled = intNumKilled + 1;
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
    public void resetWave(obj_id objRaidSpawner) throws InterruptedException
    {
        deltadictionary dctScriptVars = objRaidSpawner.getScriptVars();
        dctScriptVars.put("intIndex", 0);
        dctScriptVars.put("intNumKilled", 0);
        int intNumWaves = dctScriptVars.getInt("intNumWaves");
        int intWave = dctScriptVars.getInt("intWave");
        intWave = intWave + 1;
        if (intWave > intNumWaves)
        {
            if (hasObjVar(objRaidSpawner, "raidAlreadyStarted"))
            {
                removeObjVar(objRaidSpawner, "raidAlreadyStarted");
            }
            int intMinRecycleTime = dctScriptVars.getInt("intMinRecycleTime");
            int intMaxRecycleTime = dctScriptVars.getInt("intMaxRecycleTime");
            dctScriptVars.put("intWave", 1);
            messageTo(objRaidSpawner, "startRaid", null, rand(intMinRecycleTime, intMaxRecycleTime), true);
        }
        else 
        {
            int intMinWaveDelay = dctScriptVars.getInt("intMinWaveDelay");
            int intMaxWaveDelay = dctScriptVars.getInt("intMaxWaveDelay");
            dctScriptVars.put("intWave", intWave);
            messageTo(objRaidSpawner, "spawnWave", null, rand(intMinWaveDelay, intMaxWaveDelay), false);
        }
    }
    public int OnHearSpeech(obj_id self, obj_id speaker, String text) throws InterruptedException
    {
        if (!isGod(speaker))
        {
            return SCRIPT_CONTINUE;
        }
        if (hasObjVar(speaker, "bestine.raidTesting"))
        {
            int stringCheck = text.indexOf("initiate_raid");
            if (stringCheck > -1)
            {
                messageTo(self, "startRaid", null, 2, false);
                return SCRIPT_OVERRIDE;
            }
        }
        return SCRIPT_CONTINUE;
    }
}
