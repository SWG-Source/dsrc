package script.event.gcwraids;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.utils;
import script.library.create;
import script.library.locations;
import script.library.skill;
import script.library.chat;
import script.library.badge;
import java.util.StringTokenizer;
import script.library.gcw;

public class gcwraid extends script.base_script
{
    public gcwraid()
    {
    }
    public static final String DATATABLE = "datatables/event/gcwraid/city_data.iff";
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        if (hasObjVar(self, "event.gcwraids.shuttle"))
        {
            obj_id shuttle = getObjIdObjVar(self, "event.gcwraids.shuttle");
            destroyObject(shuttle);
        }
        if (!hasScript(self, "event.gcwraids.cheerleader"))
        {
            attachScript(self, "event.gcwraids.cheerleader");
        }
        if (!hasScript(self, "systems.gcw.gcw_data_updater"))
        {
            attachScript(self, "systems.gcw.gcw_data_updater");
        }
        String setting = getConfigSetting("EventTeam", "gcwraid");
        if (setting == null || setting.equals("") || !setting.equals("true"))
        {
            return SCRIPT_CONTINUE;
        }
        location here = getLocation(self);
        String myCity = locations.getGuardSpawnerRegionName(here);
        int numEntries = dataTableGetNumRows(DATATABLE);
        if (myCity == null || myCity.equals(""))
        {
            return SCRIPT_CONTINUE;
        }
        if (myCity.equals("@corellia_region_names:bela_vistal") || myCity.equals("@naboo_region_names:moenia"))
        {
            return SCRIPT_CONTINUE;
        }
        if (!hasObjVar(self, "auto_invasion.reference_number"))
        {
            for (int i = 0; i < numEntries; i++)
            {
                String thisCity = dataTableGetString(DATATABLE, i, "CITY");
                if (thisCity.equals(myCity))
                {
                    setObjVar(self, "auto_invasion.reference_number", i);
                }
            }
        }
        if (!hasObjVar(self, "auto_invasion.reference_number"))
        {
            return SCRIPT_CONTINUE;
        }
        int referenceNumber = getIntObjVar(self, "auto_invasion.reference_number");
        float minInvasionTime = dataTableGetFloat(DATATABLE, referenceNumber, "MINTIME");
        float timeChunkSize = dataTableGetFloat(DATATABLE, referenceNumber, "TIMECHUNKSIZE");
        int numTimeChunk = dataTableGetInt(DATATABLE, referenceNumber, "NUMTIMECHUNK");
        float rightNow = getGameTime();
        if (!hasObjVar(self, "auto_invasion.next_invasion_time"))
        {
            float nextInvasionTime = (rand(1, numTimeChunk) * timeChunkSize) + minInvasionTime + rightNow;
            setObjVar(self, "auto_invasion.next_invasion_time", nextInvasionTime);
        }
        setObjVar(self, "auto_invasion.invasion_active", 0);
        setObjVar(self, "auto_invasion.testing_multiplier", 1);
        messageTo(self, "invasionTimerPing", null, 30, false);
        messageTo(self, "endInvasion", null, 1, false);
        return SCRIPT_CONTINUE;
    }
    public int invasionTimerPing(obj_id self, dictionary params) throws InterruptedException
    {
        if (!hasObjVar(self, "auto_invasion.next_invasion_time"))
        {
            return SCRIPT_CONTINUE;
        }
        float rightNow = getGameTime();
        float nextInvasionTime = getFloatObjVar(self, "auto_invasion.next_invasion_time");
        int invasionActive = getIntObjVar(self, "auto_invasion.invasion_active");
        if (invasionActive == 1)
        {
            return SCRIPT_CONTINUE;
        }
        if (rightNow > nextInvasionTime && invasionActive == 0)
        {
            int roll = rand(0, 100);
            if (roll < 66)
            {
                messageTo(self, "startCheerleaderEvent", null, 1, false);
            }
            else 
            {
                messageTo(self, "startInvasion", null, 1, false);
            }
            setObjVar(self, "auto_invasion.invasion_active", 1);
            return SCRIPT_CONTINUE;
        }
        else 
        {
            messageTo(self, "invasionTimerPing", null, 3600, false);
        }
        return SCRIPT_CONTINUE;
    }
    public int startInvasion(obj_id self, dictionary params) throws InterruptedException
    {
        if (!hasObjVar(self, "auto_invasion.reference_number"))
        {
            return SCRIPT_CONTINUE;
        }
        removeObjVar(self, "auto_invasion.next_invasion_time");
        int referenceNumber = getIntObjVar(self, "auto_invasion.reference_number");
        float imp_r = gcw.getImperialRatio(self);
        float reb_r = gcw.getRebelRatio(self);
        String invasionTable = dataTableGetString(DATATABLE, referenceNumber, "INV1");
        if (reb_r >= imp_r)
        {
            invasionTable = dataTableGetString(DATATABLE, referenceNumber, "INV1");
        }
        else 
        {
            invasionTable = dataTableGetString(DATATABLE, referenceNumber, "INV2");
        }
        location here = getLocation(self);
        String myCity = locations.getGuardSpawnerRegionName(here);
        if (myCity.equals("@tatooine_region_names:bestine"))
        {
            invasionTable = dataTableGetString(DATATABLE, referenceNumber, "INV2");
        }
        if (myCity.equals("@tatooine_region_names:anchorhead"))
        {
            invasionTable = dataTableGetString(DATATABLE, referenceNumber, "INV1");
        }
        if (invasionTable == null || invasionTable.equals(""))
        {
            return SCRIPT_CONTINUE;
        }
        int difficulty = rand(1, 100);
        int mobMin = 0;
        int mobMax = 18;
        if (difficulty <= 5)
        {
            mobMin = 0;
            mobMax = 8;
        }
        if (difficulty >= 6 && difficulty <= 20)
        {
            mobMin = 8;
            mobMax = 18;
        }
        if (difficulty >= 21 && difficulty <= 45)
        {
            mobMin = 18;
            mobMax = 33;
        }
        if (difficulty >= 46 && difficulty <= 70)
        {
            mobMin = 33;
            mobMax = 45;
        }
        if (difficulty >= 71 && difficulty <= 95)
        {
            mobMin = 36;
            mobMax = 46;
        }
        if (difficulty >= 96 && difficulty <= 100)
        {
            mobMin = 0;
            mobMax = 46;
        }
        String locTable = dataTableGetString(DATATABLE, referenceNumber, "LOCS");
        String destTable = dataTableGetString(DATATABLE, referenceNumber, "DEST");
        int locTableLength = dataTableGetNumRows(locTable);
        int destTableLength = dataTableGetNumRows(destTable);
        setObjVar(self, "auto_invasion.loc_table_length", locTableLength);
        setObjVar(self, "auto_invasion.dest_table_length", destTableLength);
        for (int i = 0; i < locTableLength; i++)
        {
            location loc = getLocation(self);
            loc.x = dataTableGetFloat(locTable, i, "SPAWN_X");
            loc.y = dataTableGetFloat(locTable, i, "SPAWN_Y");
            loc.z = dataTableGetFloat(locTable, i, "SPAWN_Z");
            setObjVar(self, "auto_invasion.spawn_loc_" + i, loc);
        }
        for (int i = 0; i < destTableLength; i++)
        {
            location dest = getLocation(self);
            dest.x = dataTableGetFloat(destTable, i, "DEST_X");
            dest.y = dataTableGetFloat(destTable, i, "DEST_Y");
            dest.z = dataTableGetFloat(destTable, i, "DEST_Z");
            setObjVar(self, "auto_invasion.dest_off_" + i, dataTableGetFloat(destTable, i, "DEST_OFF"));
            setObjVar(self, "auto_invasion.spawn_dest_" + i, dest);
        }
        int maxWaves = dataTableGetInt(invasionTable, 0, "NUMWAVES");
        int spawnDelay = dataTableGetInt(invasionTable, 0, "SPAWNDELAY");
        int badgeAwarded = dataTableGetInt(invasionTable, 0, "BADGE");
        setObjVar(self, "auto_invasion.badge_awarded", badgeAwarded);
        setObjVar(self, "auto_invasion.spawn_delay", spawnDelay);
        setObjVar(self, "auto_invasion.invasion_table", invasionTable);
        setObjVar(self, "auto_invasion.max_waves", maxWaves);
        setObjVar(self, "auto_invasion.current_wave", 0);
        setObjVar(self, "auto_invasion.spawn_number", 1);
        setObjVar(self, "auto_invasion.dest_table", destTable);
        setObjVar(self, "auto_invasion.mob_min_index", mobMin);
        setObjVar(self, "auto_invasion.mob_max_index", mobMax);
        messageTo(self, "launchWave", null, 1, false);
        return SCRIPT_CONTINUE;
    }
    public int launchWave(obj_id self, dictionary params) throws InterruptedException
    {
        String invasionTable = getStringObjVar(self, "auto_invasion.invasion_table");
        int currentWave = getIntObjVar(self, "auto_invasion.current_wave");
        int maxWaves = getIntObjVar(self, "auto_invasion.max_waves");
        int numberDead = 0;
        String startMessage = dataTableGetString(invasionTable, 0, "SYSMESSTART");
        String endMessage = dataTableGetString(invasionTable, 0, "SYSMESEND");
        if (currentWave == maxWaves)
        {
            announceStatusToPlayers(self, endMessage);
            messageTo(self, "endInvasion", null, 1, false);
            return SCRIPT_CONTINUE;
        }
        if (currentWave == 0)
        {
            announceStatusToPlayers(self, startMessage);
        }
        currentWave++;
        setObjVar(self, "auto_invasion.current_wave", currentWave);
        setObjVar(self, "auto_invasion.spawn_number", 1);
        setObjVar(self, "auto_invasion.done_spawning", 0);
        setObjVar(self, "auto_invasion.number_dead", numberDead);
        messageTo(self, "startSpawning", null, 1, false);
        return SCRIPT_CONTINUE;
    }
    public int startSpawning(obj_id self, dictionary params) throws InterruptedException
    {
        String invasionTable = getStringObjVar(self, "auto_invasion.invasion_table");
        if (invasionTable.equals("null") || invasionTable.equals(""))
        {
            messageTo(self, "endInvasion", null, 1, false);
            return SCRIPT_CONTINUE;
        }
        int currentWave = getIntObjVar(self, "auto_invasion.current_wave");
        int spawnNum = getIntObjVar(self, "auto_invasion.spawn_number");
        int spawnDelay = dataTableGetInt(invasionTable, 0, "SPAWNDELAY");
        int maxWaveTimeMinutes = dataTableGetInt(invasionTable, 0, "MAXWAVETIME");
        int maxWaveTime = maxWaveTimeMinutes * 60;
        int mobMinIndex = getIntObjVar(self, "auto_invasion.mob_min_index");
        int mobMaxIndex = getIntObjVar(self, "auto_invasion.mob_max_index");
        int doneSpawning = getIntObjVar(self, "auto_invasion.done_spawning");
        String script = dataTableGetString(invasionTable, 0, "SCRIPT");
        if (doneSpawning == 1)
        {
            return SCRIPT_CONTINUE;
        }
        int locTableLength = getIntObjVar(self, "auto_invasion.loc_table_length") - 1;
        int locIndex = rand(0, locTableLength);
        location spawnLoc = getLocationObjVar(self, "auto_invasion.spawn_loc_" + locIndex);
        int destTableLength = getIntObjVar(self, "auto_invasion.dest_table_length") - 1;
        int destIndex = rand(0, destTableLength);
        location dest = getLocationObjVar(self, "auto_invasion.spawn_dest_" + destIndex);
        float destOff = getFloatObjVar(self, "auto_invasion.dest_off_" + destIndex);
        if (spawnLoc == null)
        {
            spawnLoc = getLocationObjVar(self, "auto_invasion.spawn_loc_0");
        }
        if (dest == null)
        {
            dest = getLocationObjVar(self, "auto_invasion.spawn_dest_0");
        }
        if (destOff == -1)
        {
            destOff = getFloatObjVar(self, "auto_invasion.dest_off_" + destIndex);
        }
        int mobIndex = rand(mobMinIndex, mobMaxIndex);
        String spawnType = dataTableGetString(invasionTable, mobIndex, "SPAWN");
        obj_id spawn = create.object(spawnType, spawnLoc);
        if (isIdValid(spawn))
        {
            setObjVar(spawn, "auto_invasion.dest_offset", destOff);
            setObjVar(spawn, "auto_invasion.target", dest);
            setObjVar(spawn, "auto_invasion.my_number", spawnNum);
            setObjVar(spawn, "auto_invasion.mom", self);
            attachScript(spawn, script);
            setObjVar(self, "auto_invasion.spawn" + spawnNum, spawn);
            spawnNum++;
        }
        setObjVar(self, "auto_invasion.spawn_number", spawnNum);
        if (spawnNum > 19)
        {
            params.put("currentWave", currentWave);
            messageTo(self, "waveTimeLimitCheck", params, maxWaveTime, false);
            setObjVar(self, "auto_invasion.done_spawning", 1);
            return SCRIPT_CONTINUE;
        }
        else 
        {
            messageTo(self, "startSpawning", null, spawnDelay, false);
        }
        return SCRIPT_CONTINUE;
    }
    public int invaderDied(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id deadGuy = params.getObjId("deadGuy");
        int myNumber = params.getInt("myNumber");
        removeObjVar(self, "auto_invasion.spawn" + myNumber);
        int spawnNum = getIntObjVar(self, "auto_invasion.spawn_number");
        int numberDead = 0;
        int doneSpawning = getIntObjVar(self, "auto_invasion.done_spawning");
        String invasionTable = getStringObjVar(self, "auto_invasion.invasion_table");
        int timeBetweenWaves = dataTableGetInt(invasionTable, 0, "TIMEBETWEENWAVES");
        if (doneSpawning == 1)
        {
            for (int i = 1; i <= spawnNum; i++)
            {
                obj_id possibleInvader = getObjIdObjVar(self, "auto_invasion.spawn" + i);
                if (!isIdValid(possibleInvader))
                {
                    numberDead++;
                    setObjVar(self, "auto_invasion.number_dead", numberDead);
                }
            }
        }
        if (doneSpawning == 1 && numberDead >= spawnNum)
        {
            messageTo(self, "launchWave", null, timeBetweenWaves * 60, false);
        }
        return SCRIPT_CONTINUE;
    }
    public int waveTimeLimitCheck(obj_id self, dictionary params) throws InterruptedException
    {
        int lastWave = params.getInt("currentWave");
        int currentWave = getIntObjVar(self, "auto_invasion.current_wave");
        int spawnNum = getIntObjVar(self, "auto_invasion.spawn_number");
        int invasionActive = getIntObjVar(self, "auto_invasion.invasion_active");
        if (lastWave == currentWave)
        {
            for (int i = 1; i <= spawnNum; i++)
            {
                obj_id spawn = getObjIdObjVar(self, "auto_invasion.spawn" + i);
                if (isIdValid(spawn))
                {
                    messageTo(spawn, "goDie", null, 1, false);
                }
                removeObjVar(self, "auto_invasion.spawn" + i);
            }
            if (invasionActive == 1)
            {
                messageTo(self, "launchWave", null, 1, false);
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int endInvasion(obj_id self, dictionary params) throws InterruptedException
    {
        setObjVar(self, "auto_invasion.invasion_active", 0);
        int spawnNum = getIntObjVar(self, "auto_invasion.spawn_num");
        for (int i = 1; i <= 20; i++)
        {
            obj_id spawn = getObjIdObjVar(self, "auto_invasion.spawn" + i);
            if (isIdValid(spawn))
            {
                messageTo(spawn, "goDie", null, 1, false);
            }
            removeObjVar(self, "auto_invasion.spawn" + i);
        }
        int badgeAwarded = getIntObjVar(self, "auto_invasion.badge_awarded");
        String badgeName = getCollectionSlotName(badgeAwarded);
        if ((badgeAwarded > 0) && (badgeName != null) && (badgeName.length() > 0))
        {
            obj_id[] objPlayers = getPlayerCreaturesInRange(self, 255);
            if (objPlayers != null && objPlayers.length > 0)
            {
                for (int i = 0; i < objPlayers.length; i++)
                {
                    badge.grantBadge(objPlayers[i], badgeName);
                }
            }
        }
        removeObjVar(self, "auto_invasion.spawn_delay");
        removeObjVar(self, "auto_invasion.max_waves");
        removeObjVar(self, "auto_invasion.current_wave");
        removeObjVar(self, "auto_invasion.spawn_number");
        removeObjVar(self, "auto_invasion.mob_min_index");
        removeObjVar(self, "auto_invasion.mob_max_index");
        removeObjVar(self, "auto_invasion.done_spawning");
        removeObjVar(self, "auto_invasion.number_dead");
        int referenceNumber = getIntObjVar(self, "auto_invasion.reference_number");
        float minInvasionTime = dataTableGetFloat(DATATABLE, referenceNumber, "MINTIME");
        float timeChunkSize = dataTableGetFloat(DATATABLE, referenceNumber, "TIMECHUNKSIZE");
        int numTimeChunk = dataTableGetInt(DATATABLE, referenceNumber, "NUMTIMECHUNK");
        float rightNow = getGameTime();
        if (!hasObjVar(self, "auto_invasion.next_invasion_time"))
        {
            float nextInvasionTime = (rand(1, numTimeChunk) * timeChunkSize) + minInvasionTime + rightNow;
            setObjVar(self, "auto_invasion.next_invasion_time", nextInvasionTime);
        }
        setObjVar(self, "auto_invasion.testing_multiplier", 1);
        messageTo(self, "invasionTimerPing", null, 2700, false);
        return SCRIPT_CONTINUE;
    }
    public void announceStatusToPlayers(obj_id self, String messageId) throws InterruptedException
    {
        obj_id[] objPlayers = getPlayerCreaturesInRange(self, 256.0f);
        if (objPlayers != null && objPlayers.length > 0)
        {
            for (int i = 0; i < objPlayers.length; i++)
            {
                sendSystemMessage(objPlayers[i], new string_id("auto_invasion", messageId));
            }
        }
    }
    public int OnHearSpeech(obj_id self, obj_id objSpeaker, String strText) throws InterruptedException
    {
        if (isGod(objSpeaker))
        {
            if ((toLower(strText)).startsWith("forcegcwraid"))
            {
                int invasionActive = getIntObjVar(self, "auto_invasion.invasion_active");
                if (invasionActive == 0)
                {
                    messageTo(self, "startInvasion", null, 1, false);
                    setObjVar(self, "auto_invasion.invasion_active", 1);
                }
            }
            if ((toLower(strText)).startsWith("endcheerleaderevent"))
            {
                messageTo(self, "everyoneWalkBack", null, 1, false);
            }
            if ((toLower(strText)).startsWith("forcecheerleaderevent"))
            {
                messageTo(self, "startCheerleaderEvent", null, 1, false);
                setObjVar(self, "auto_invasion.invasion_active", 1);
            }
        }
        return SCRIPT_CONTINUE;
    }
}
