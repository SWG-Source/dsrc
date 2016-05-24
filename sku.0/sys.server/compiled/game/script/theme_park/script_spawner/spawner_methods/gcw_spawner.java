package script.theme_park.script_spawner.spawner_methods;

import script.dictionary;
import script.library.*;
import script.location;
import script.obj_id;

public class gcw_spawner extends script.base_script
{
    public gcw_spawner()
    {
    }
    public static final int MAX_SPAWN_TRIES = 5;
    public int OnAttach(obj_id self) throws InterruptedException
    {
        messageTo(self, "beginSetup", null, 1, false);
        if (!hasScript(self, "systems.gcw.gcw_data_updater"))
        {
            attachScript(self, "systems.gcw.gcw_data_updater");
        }
        return SCRIPT_CONTINUE;
    }
    public int OnHearSpeech(obj_id self, obj_id speaker, String strText) throws InterruptedException
    {
        String[] strCommands = split(strText, ' ');
        if (strCommands[0].equalsIgnoreCase("gcwflip"))
        {
            LOG("gcw_region", "OnHearSpeech gcw_spawner");
            messageTo(self, "updateGCWInfo", null, 1.0f, false);
            messageTo(self, "beginSetup", null, 2.f, false);
        }
        return SCRIPT_CONTINUE;
    }
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        location here = getLocation(self);
        String planet = here.area;
        String city = locations.getCityName(here);
        if (city == null)
        {
            city = locations.getGuardSpawnerRegionName(here);
        }
        String table = "datatables/spawning/script_spawner/" + planet + "/" + planet + "_spawners.iff";
        int row = dataTableSearchColumnForString(city, 0, table);
        if (row == -1)
        {
            return SCRIPT_CONTINUE;
        }
        setObjVar(self, "planet_spawn_datatable", table);
        setObjVar(self, "row_number", row);
        messageTo(self, "beginSetup", null, 1, false);
        if (!hasScript(self, "systems.gcw.gcw_data_updater"))
        {
            attachScript(self, "systems.gcw.gcw_data_updater");
        }
        return SCRIPT_CONTINUE;
    }
    public int beginSetup(obj_id self, dictionary params) throws InterruptedException
    {
        String badScript = "poi.interior_spawner.interior_spawner";
        if (hasScript(self, badScript))
        {
            detachScript(self, badScript);
        }
        badScript = "theme_park.script_spawner.spawner_methods.script_spawner_default";
        if (hasScript(self, badScript))
        {
            detachScript(self, badScript);
        }
        String dtbMasterList = getStringObjVar(self, "planet_spawn_datatable");
        int intDtbRowNum = getIntObjVar(self, "row_number");
        if (dtbMasterList == null)
        {
            return SCRIPT_CONTINUE;
        }
        if (!setTable(self, intDtbRowNum, dtbMasterList))
        {
            return SCRIPT_CONTINUE;
        }
        messageTo(self, "beginSpawn", null, 1, false);
        return SCRIPT_CONTINUE;
    }
    public boolean setTable(obj_id self, int intDtbRowNum, String dtbMasterList) throws InterruptedException
    {
        String tableToSet = dataTableGetString(dtbMasterList, intDtbRowNum, "spawnTable");
        if (tableToSet == null || tableToSet.equals(""))
        {
            return false;
        }
        setObjVar(self, "spawn_table_imperial", tableToSet + "_imperial.iff");
        setObjVar(self, "spawn_table_rebel", tableToSet + "_rebel.iff");
        return true;
    }
    public int beginSpawn(obj_id self, dictionary params) throws InterruptedException
    {
        dictionary webster = getSpawnFactionData(self);
        boolean hard = webster.getBoolean("hard");
        String faction = webster.getString("faction");
        if (faction == null || faction.length() <= 0)
        {
            return SCRIPT_CONTINUE;
        }
        String datatable = getStringObjVar(self, "spawn_table_" + faction);
        if (datatable == null)
        {
            return SCRIPT_CONTINUE;
        }
        int numberOfCreaturesToSpawn = dataTableGetNumRows(datatable);
        if (numberOfCreaturesToSpawn <= 0)
        {
            return SCRIPT_CONTINUE;
        }
        int spawnedCount = utils.getIntScriptVar(self, "spawnCount");
        obj_id[] spawned = utils.getObjIdArrayScriptVar(self, "spawnList");
        if (spawned == null || spawned.length == 0)
        {
            spawned = new obj_id[numberOfCreaturesToSpawn];
        }
        boolean failed = false;
        for (int i = 0; i < spawned.length; ++i)
        {
            if (!isIdValid(spawned[i]) || !exists(spawned[i]))
            {
                spawned[i] = spawnActor(datatable, i, hard);
                if (!isIdValid(spawned[i]))
                {
                    failed = true;
                }
            }
        }
        if (failed)
        {
            spawnedCount++;
            if (spawnedCount <= MAX_SPAWN_TRIES)
            {
                messageTo(self, "beginSpawn", null, 1, false);
            }
        }
        utils.setScriptVar(self, "spawnList", spawned);
        utils.setScriptVar(self, "spawnCount", spawnedCount);
        return SCRIPT_CONTINUE;
    }
    public obj_id spawnActor(String datatable, int x, boolean hard) throws InterruptedException
    {
        obj_id self = getSelf();
        String spawn = dataTableGetString(datatable, x, "spawns");
        float xCoord = dataTableGetFloat(datatable, x, "loc_x");
        float yCoord = dataTableGetFloat(datatable, x, "loc_y");
        float zCoord = dataTableGetFloat(datatable, x, "loc_z");
        location myself = getLocation(self);
        String planet = myself.area;
        String spawnRoom = dataTableGetString(datatable, x, "room");
        obj_id building = self;
        if (dataTableGetInt(datatable, x, "building_id") > 0)
        {
            building = obj_id.getObjId(dataTableGetInt(datatable, x, "building_id"));
        }
        LOG("guard_spawn", "Spawning with building " + building + " " + getTemplateName(building));
        if (!isIdValid(building) || spawnRoom == null || spawnRoom.length() < 1)
        {
            return obj_id.NULL_ID;
        }
        obj_id room = null;
        try {
            room = getCellId(building, spawnRoom);
        }
        catch (Exception e){
            System.out.println("Unable to get cell id of room (" + spawnRoom + ") in building " + building.toString() + " while looking in datatable \"" + datatable + "\".");
            System.out.println("Script is currently attached to OBJID " + self.toString());
            e.printStackTrace();
        }
        if (!isIdValid(room) && !spawnRoom.equals("world"))
        {
            CustomerServiceLog("bad_spawner_data", "spawner " + self + ", datatable " + datatable + ", row " + x + ", mob " + spawn + ", room " + spawnRoom + " doesn't exist");
            return obj_id.NULL_ID;
        }
        LOG("guard_spawn", "Spawning crackdown guard " + spawn);
        location spawnPoint = new location(xCoord, yCoord, zCoord, planet, room);
        obj_id spawnedCreature = null;
        if (hard)
        {
            spawnedCreature = create.object(spawn + "_hard", spawnPoint);
            if (!isIdValid(spawnedCreature))
            {
                spawnedCreature = create.object(spawn, spawnPoint);
            }
        }
        else 
        {
            spawnedCreature = create.object(spawn, spawnPoint);
        }
        if (!isIdValid(spawnedCreature))
        {
            return obj_id.NULL_ID;
        }
        String scriptOne = dataTableGetString(datatable, x, "scriptOne");
        if (scriptOne != null && !scriptOne.equals(""))
        {
            attachScript(spawnedCreature, scriptOne);
        }
        String scriptTwo = dataTableGetString(datatable, x, "scriptTwo");
        if (scriptTwo != null && !scriptTwo.equals(""))
        {
            attachScript(spawnedCreature, scriptTwo);
        }
        String scriptThree = dataTableGetString(datatable, x, "scriptThree");
        if (scriptThree != null && !scriptThree.equals(""))
        {
            attachScript(spawnedCreature, scriptThree);
        }
        String creatureName = dataTableGetString(datatable, x, "name");
        if (creatureName != null && !creatureName.equals(""))
        {
            setName(spawnedCreature, creatureName);
        }
        float yaw = dataTableGetFloat(datatable, x, "yaw");
        setYaw(spawnedCreature, yaw);
        String mood = dataTableGetString(datatable, x, "mood");
        ai_lib.setDefaultCalmMood(spawnedCreature, mood);
        setObjVar(spawnedCreature, "spawn_number", x);
        setObjVar(spawnedCreature, "mom", self);
        int respawnTime = dataTableGetInt(datatable, x, "respawn_time");
        if (respawnTime < 1)
        {
            respawnTime = 300;
        }
        setObjVar(spawnedCreature, "respawn_time", respawnTime);
        String spawnObjVar = dataTableGetString(datatable, x, "spawn_objvar");
        String objvarValue = dataTableGetString(datatable, x, "spawn_objvar_value");
        if ((spawnObjVar != null && !spawnObjVar.equals("")) && (objvarValue != null && !objvarValue.equals("")))
        {
            setObjVar(spawnedCreature, spawnObjVar, objvarValue);
        }
        String spawnObjVar2 = dataTableGetString(datatable, x, "spawn_objvar2");
        String objvarValue2 = dataTableGetString(datatable, x, "spawn_objvar_value2");
        if ((spawnObjVar2 != null && !spawnObjVar2.equals("")) && (objvarValue2 != null && !objvarValue2.equals("")))
        {
            setObjVar(spawnedCreature, spawnObjVar2, objvarValue2);
        }
        return spawnedCreature;
    }
    public int tellingMomIDied(obj_id self, dictionary params) throws InterruptedException
    {
        dictionary webster = getSpawnFactionData(self);
        boolean hard = webster.getBoolean("hard");
        String faction = webster.getString("faction");
        if (faction == null || faction.length() <= 0)
        {
            return SCRIPT_CONTINUE;
        }
        int actorNum = params.getInt("spawnNumber");
        obj_id corpseObjId = params.getObjId("spawnMob");
        String datatable = getStringObjVar(self, "spawn_table_" + faction);
        obj_id[] spawned = utils.getObjIdArrayScriptVar(self, "spawnList");
        if (datatable == null)
        {
            return SCRIPT_CONTINUE;
        }
        obj_id actor = spawned[actorNum];
        if (isIdValid(actor) && actor == corpseObjId)
        {
            obj_id newActor = spawnActor(datatable, actorNum, hard);
            if (isIdValid(newActor))
            {
                spawned[actorNum] = newActor;
                utils.setScriptVar(self, "spawnList", spawned);
            }
            else 
            {
                messageTo(self, "tellingMomIDied", params, 3, false);
            }
        }
        return SCRIPT_CONTINUE;
    }
    public dictionary getSpawnFactionData(obj_id self) throws InterruptedException
    {
        dictionary webster = new dictionary();
        float imp_r = gcw.getImperialRatio(self);
        float reb_r = gcw.getRebelRatio(self);
        boolean hard = false;
        String faction = null;
        if (imp_r >= reb_r)
        {
            faction = "imperial";
            if (imp_r > 2.0)
            {
                hard = true;
            }
        }
        else 
        {
            faction = "rebel";
            if (reb_r > 2.0)
            {
                hard = true;
            }
        }
        String empiredayRunning = getConfigSetting("GameServer", "empireday_ceremony");
        if (empiredayRunning != null)
        {
            if (empiredayRunning.equals("true") || empiredayRunning.equals("1"))
            {
                location here = getLocation(self);
                String city = locations.getCityName(here);
                if (city == null)
                {
                    city = locations.getGuardSpawnerRegionName(here);
                }
                if (city != null && city.length() > 0)
                {
                    if (city.equals("coronet"))
                    {
                        faction = "rebel";
                        hard = false;
                    }
                    else if (city.equals("theed"))
                    {
                        faction = "imperial";
                        hard = false;
                    }
                }
            }
        }
        webster.put("faction", faction);
        webster.put("hard", hard);
        return webster;
    }
    public int destroyChildren(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id[] spawned = utils.getObjIdArrayScriptVar(self, "spawnList");
        if (spawned == null || spawned.length <= 0)
        {
            return SCRIPT_CONTINUE;
        }
        for (int i = 0, j = spawned.length; i < j; i++)
        {
            if (isIdValid(spawned[i]) && exists(spawned[i]))
            {
                destroyObject(spawned[i]);
            }
        }
        messageTo(self, "beginSpawn", null, 1, false);
        return SCRIPT_CONTINUE;
    }
}
