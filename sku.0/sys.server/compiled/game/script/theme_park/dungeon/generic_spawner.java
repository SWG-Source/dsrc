package script.theme_park.dungeon;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.ai.ai;
import script.library.ai_lib;
import script.library.utils;
import script.library.create;
import script.library.trial;
import script.library.space_dungeon;
import script.library.instance;
import script.library.locations;
import script.library.spawning;

public class generic_spawner extends script.base_script
{
    public generic_spawner()
    {
    }
    public static final String SPAWNED_LIST = "spawned";
    public static final String SPAWNED = SPAWNED_LIST + "." + "spawnNumber_";
    public static final String GENERIC_SPAWNER_ACTIVE = "genericSpawner.isActive";
    public int OnAttach(obj_id self) throws InterruptedException
    {
        if (getTemplateName(self).equals("object/building/general/bunker_allum_mine.iff"))
        {
            String setting = getConfigSetting("Dungeon", "Death_Watch");
            if (setting == null || setting.equals("false") || setting.equals("0"))
            {
                return SCRIPT_CONTINUE;
            }
        }
        if (!canSpawnByConfigSetting())
        {
            return SCRIPT_CONTINUE;
        }
        if (!hasObjVar(self, "space_dungeon.name") && !hasScript(self, "systems.instance.instance_manager"))
        {
            messageTo(self, "beginSpawn", null, 1, false);
        }
        return SCRIPT_CONTINUE;
    }
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        if (getTemplateName(self).equals("object/building/general/bunker_allum_mine.iff"))
        {
            String setting = getConfigSetting("Dungeon", "Death_Watch");
            if (setting == null || setting.equals("false") || setting.equals("0"))
            {
                return SCRIPT_CONTINUE;
            }
        }
        if (!canSpawnByConfigSetting())
        {
            return SCRIPT_CONTINUE;
        }
        if (!hasObjVar(self, "space_dungeon.name") && !hasScript(self, "systems.instance.instance_manager"))
        {
            messageTo(self, "beginSpawn", null, 1, false);
        }
        return SCRIPT_CONTINUE;
    }
    public int beginSpawn(obj_id self, dictionary params) throws InterruptedException
    {
        String datatable = getStringObjVar(self, "spawn_table");
        if (datatable == null || datatable.equals(""))
        {
            return SCRIPT_OVERRIDE;
        }
        int x = utils.getIntScriptVar(self, "spawnCounter");
        setDungeonActive(self, true);
        while (x < dataTableGetNumRows(datatable))
        {
            if (hasObjVar(self, SPAWNED + x))
            {
                if (!getObjIdObjVar(self, SPAWNED + x).isLoaded())
                {
                    spawnCreatures(x, datatable, self);
                }
            }
            else 
            {
                spawnCreatures(x, datatable, self);
            }
            x = x + 1;
        }
        if (dataTableHasColumn(datatable, "special_room"))
        {
            attachRoomScripts(self, datatable);
        }
        if (dataTableHasColumn(datatable, "room_objvar"))
        {
            setRoomObjVars(self, datatable);
        }
        return SCRIPT_CONTINUE;
    }
    public int cleanupSpawn(obj_id self, dictionary params) throws InterruptedException
    {
        messageTo(self, "dungeonCleanup", null, 0.0f, false);
        return SCRIPT_CONTINUE;
    }
    public int dungeonCleanup(obj_id self, dictionary params) throws InterruptedException
    {
        setDungeonActive(self, false);
        obj_var_list ovl = getObjVarList(self, SPAWNED_LIST);
        if ((ovl != null) && (ovl.getNumItems() != 0))
        {
            int numItems = ovl.getNumItems();
            for (int i = 0; i < numItems; i++)
            {
                removeObjVar(self, SPAWNED + i);
            }
        }
        obj_id[] objects = trial.getAllObjectsInDungeon(self);
        if (objects == null || objects.length == 0)
        {
            return SCRIPT_CONTINUE;
        }
        for (obj_id object : objects) {
            if (isPlayer(object)) {
                if (instance.isInInstanceArea(object)) {
                    instance.requestExitPlayer(self, object, 1);
                    continue;
                } else {
                    space_dungeon.verifyPlayerSession(object);
                    continue;
                }
            }
            if (isMob(object) || trial.isTempObject(object)) {
                trial.cleanupNpc(object);
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int tellingMomIDied(obj_id self, dictionary params) throws InterruptedException
    {
        if (!isDungeonActive(self))
        {
            return SCRIPT_CONTINUE;
        }
        int spawn_num = params.getInt("spawnNumber");
        if (hasObjVar(self, SPAWNED + spawn_num) && (params.getObjId("spawnMob") == getObjIdObjVar(self, SPAWNED + spawn_num)))
        {
            spawnCreatures(spawn_num, getStringObjVar(self, "spawn_table"), self);
        }
        return SCRIPT_CONTINUE;
    }
    public boolean isDungeonActive(obj_id dungeon) throws InterruptedException {
        return !utils.hasScriptVar(dungeon, GENERIC_SPAWNER_ACTIVE) || utils.getBooleanScriptVar(dungeon, GENERIC_SPAWNER_ACTIVE);
    }
    public void setDungeonActive(obj_id dungeon, boolean state) throws InterruptedException
    {
        utils.setScriptVar(dungeon, GENERIC_SPAWNER_ACTIVE, state);
    }
    public void attachRoomScripts(obj_id self, String datatable) throws InterruptedException
    {
        int passThrough = 0;
        while (passThrough < dataTableGetStringColumnNoDefaults(datatable, "special_room").length)
        {
            String roomName = dataTableGetString(datatable, passThrough, "special_room");
            String roomScript = dataTableGetString(datatable, passThrough, "special_room_script");
            if (roomName == null || roomName.equals(""))
            {
                setObjVar(self, "problem", "No Special Room Name found.");
                return;
            }
            if (roomScript == null || roomScript.equals(""))
            {
                setObjVar(self, "problem", "No Special Room Script found.");
                return;
            }
            obj_id roomObj = roomName.equals("self") ? self : getCellId(self, roomName);
            attachScript(roomObj, roomScript);
            setObjVar(self, "set_room", passThrough);
            passThrough++;
        }
    }
    public void setRoomObjVars(obj_id self, String datatable) throws InterruptedException
    {
        int numRooms = dataTableGetStringColumnNoDefaults(datatable, "room_objvar").length;
        if (numRooms == 0)
        {
            return;
        }
        int passThrough = 0;
        while (passThrough < numRooms)
        {
            String roomName = dataTableGetString(datatable, passThrough, "room_objvar");
            if (roomName == null || roomName.equals(""))
            {
                setObjVar(self, "problem", "No room name");
                return;
            }
            String roomObjVar = dataTableGetString(datatable, passThrough, "room_objvar_name");
            if (roomObjVar == null || roomObjVar.equals(""))
            {
                setObjVar(self, "problem", "No ObjVar Name");
                return;
            }
            setObjVar(getCellId(self, roomName), roomObjVar, dataTableGetString(datatable, passThrough, "room_objvar_value"));
            passThrough++;
        }
    }
    public void spawnCreatures(int x, String datatable, obj_id self) throws InterruptedException
    {
        String spawn = dataTableGetString(datatable, x, "spawns");
        float xCoord = dataTableGetFloat(datatable, x, "loc_x");
        float yCoord = dataTableGetFloat(datatable, x, "loc_y");
        float zCoord = dataTableGetFloat(datatable, x, "loc_z");
        location myself = getLocation(self);
        String planet = myself.area;
        String spawnRoom = dataTableGetString(datatable, x, "room");
        obj_id room = getCellId(self, spawnRoom);
        if (!isIdValid(room) && (!spawnRoom.equals("world")))
        {
            CustomerServiceLog("bad_spawner_data", "spawner " + self + ", datatable " + datatable + ", row " + x + ", mob " + spawn + ", room " + spawnRoom + " doesn't exist");
            removeObjVar(self, SPAWNED + x);
            return;
        }
        String creatureName = dataTableGetString(datatable, x, "name");
        if (dataTableHasColumn(datatable, "boss") && dataTableHasColumn(datatable, "boss_chance") && dataTableHasColumn(datatable, "boss_name"))
        {
            String boss = dataTableGetString(datatable, x, "boss");
            int percentage = dataTableGetInt(datatable, x, "boss_chance");
            String bossName = dataTableGetString(datatable, x, "boss_name");
            if (boss != null && !boss.equals("none") && !boss.equals(""))
            {
                int bossRoll = rand(1, 10000);
                if (percentage != 0)
                {
                    if (bossRoll < percentage)
                    {
                        spawn = boss;
                        if (bossName != null && !bossName.equals(""))
                        {
                            creatureName = bossName;
                        }
                        else 
                        {
                            creatureName = "";
                        }
                    }
                }
            }
        }
        location locTest = new location(xCoord, yCoord, zCoord, planet, room);
        if (dataTableHasColumn(datatable, "radius"))
        {
            float fltRadius = dataTableGetFloat(datatable, x, "radius");
            if (fltRadius != 0)
            {
                locTest = spawning.getRandomLocationInCircle(locTest, fltRadius);
            }
        }
        int level = -1;
        if (dataTableHasColumn(datatable, "planet_level"))
        {
            if (dataTableGetInt(datatable, x, "planet_level") != 0)
            {
                level = getRandomPlanetCreatureLevel(self, spawn, locTest);
            }
        }
        obj_id spawnedCreature = create.object(spawn, locTest, level);
        if (!isIdValid(spawnedCreature))
        {
            CustomerServiceLog("bad_spawner_data", "spawner " + self + ", datatable " + datatable + ", row " + x + ", mob " + spawn + " doesn't exist");
            removeObjVar(self, SPAWNED + x);
            return;
        }
        if (dataTableHasColumn(datatable, "convo_script"))
        {
            String convoScript = dataTableGetString(datatable, x, "convo_script");
            if (convoScript != null && !convoScript.equals(""))
            {
                attachScript(spawnedCreature, convoScript);
            }
        }
        if (creatureName != null && !creatureName.equals(""))
        {
            setName(spawnedCreature, creatureName);
        }
        setObjVar(spawnedCreature, "spawn_number", x);
        setObjVar(spawnedCreature, "mom", self);
        setObjVar(self, SPAWNED + x, spawnedCreature);
        trial.markAsTempObject(spawnedCreature, false);
        if (dataTableHasColumn(datatable, "script"))
        {
            String scriptList = dataTableGetString(datatable, x, "script");
            if (scriptList != null && !scriptList.equals(""))
            {
                String[] scriptArray = split(scriptList, ',');
                for (int i = 0; i < scriptArray.length; i++)
                {
                    attachScript(spawnedCreature, scriptArray[i]);
                }
            }
        }
        if (dataTableHasColumn(datatable, "yaw"))
        {
            float spawnYaw = dataTableGetFloat(datatable, x, "yaw");
            if (spawnYaw != 0)
            {
                setYaw(spawnedCreature, spawnYaw);
            }
        }
        if (dataTableHasColumn(datatable, "spawn_objvar2"))
        {
            String spawnObjVar = dataTableGetString(datatable, x, "spawn_objvar");
            String objvarValue1 = dataTableGetString(datatable, x, "spawn_objvar_value");
            String spawnObjVar2 = dataTableGetString(datatable, x, "spawn_objvar2");
            String spawnValue2 = dataTableGetString(datatable, x, "spawn_objvar_value2");
            if (spawnObjVar != null && !spawnObjVar.equals(""))
            {
                if (objvarValue1 != null && !objvarValue1.equals(""))
                {
                    setObjVar(spawnedCreature, spawnObjVar, objvarValue1);
                }
            }
            if (spawnObjVar2 != null && !spawnObjVar2.equals(""))
            {
                if (spawnValue2 != null && !spawnValue2.equals(""))
                {
                    setObjVar(spawnedCreature, spawnObjVar2, spawnValue2);
                }
            }
        }
        else 
        {
            if (dataTableHasColumn(datatable, "spawn_objvar"))
            {
                String objVarList = dataTableGetString(datatable, x, "spawn_objvar");
                if (objVarList != null && !objVarList.equals(""))
                {
                    setCreatureObjVars(spawnedCreature, objVarList);
                }
            }
        }
        if (dataTableHasColumn(datatable, "animation_mood"))
        {
            dictionary outparams = new dictionary();
            outparams.put("x", x);
            outparams.put("datatable", datatable);
            outparams.put("spawnedCreature", spawnedCreature);
            messageTo(self, "animationMood", outparams, 3, false);
        }
        if (dataTableHasColumn(datatable, "mood"))
        {
            String creatureMood = dataTableGetString(datatable, x, "mood");
            if (creatureMood != null && !creatureMood.equals(""))
            {
                ai_lib.setDefaultCalmMood(spawnedCreature, creatureMood);
            }
        }
    }
    public int animationMood(obj_id self, dictionary params) throws InterruptedException
    {
        String creatureAnimationMood = dataTableGetString(params.getString("datatable"), params.getInt("x"), "animation_mood");
        if (creatureAnimationMood != null && !creatureAnimationMood.equals(""))
        {
            ai_lib.setAnimationMood(params.getObjId("spawnedCreature"), creatureAnimationMood);
        }
        return SCRIPT_CONTINUE;
    }
    public void setCreatureObjVars(obj_id creature, String objVarList) throws InterruptedException
    {
        if (objVarList == null || objVarList.equals(""))
        {
            return;
        }
        String[] pairs = split(objVarList, ',');
        for (String pair : pairs) {
            String[] objVarToSet = split(pair, '=');
            String objVarValue = objVarToSet[1];
            String[] objVarNameAndType = split(objVarToSet[0], ':');
            String objVarType = objVarNameAndType[0];
            String objVarName = objVarNameAndType[1];
            switch (objVarType) {
                case "string":
                    setObjVar(creature, objVarName, objVarValue);
                    break;
                case "int":
                    setObjVar(creature, objVarName, utils.stringToInt(objVarValue));
                    break;
                case "float":
                    setObjVar(creature, objVarName, utils.stringToFloat(objVarValue));
                    break;
                case "boolean":
                case "bool":
                    setObjVar(creature, objVarName, utils.stringToInt(objVarValue));
                    break;
                default:
                    setObjVar(creature, objVarName, objVarValue);
                    break;
            }
        }
    }
    public boolean canSpawnByConfigSetting() throws InterruptedException {
        String disableSpawners = getConfigSetting("GameServer", "disableGenericSpawner");
        return disableSpawners == null || !(disableSpawners.equals("true") || disableSpawners.equals("1"));
    }
    public int getRandomPlanetCreatureLevel(obj_id spawner, String npcType, location here) throws InterruptedException
    {
        int minLevel = locations.getMinDifficultyForPlanet(here.area);
        int maxLevel = locations.getMaxDifficultyForPlanet(here.area);
        utils.setScriptVar(spawner, "testing_planetMinLevel", minLevel);
        utils.setScriptVar(spawner, "testing_planetMaxLevel", maxLevel);
        utils.setScriptVar(spawner, "testing_here.area", here.area);
        int level = minLevel;
        dictionary creatureDict = utils.dataTableGetRow(create.CREATURE_TABLE, npcType);
        if (creatureDict != null)
        {
            int baseLevel = creatureDict.getInt("BaseLevel");
            level = baseLevel;
            if (minLevel > 0 && maxLevel > 0)
            {
                if (level < minLevel || level > maxLevel)
                {
                    if (level < minLevel)
                    {
                        level = minLevel;
                    }
                    else if (level > maxLevel)
                    {
                        level = maxLevel;
                    }
                    CustomerServiceLog("interior_spawner_level", "Spawner " + spawner + " tried to spawn a creature whose base level from creatures.tab (" + baseLevel + ") was outside of the planet's range (" + minLevel + " to " + maxLevel + ").");
                }
            }
        }
        if (level < 1)
        {
            level = 1;
        }
        return level;
    }
}
