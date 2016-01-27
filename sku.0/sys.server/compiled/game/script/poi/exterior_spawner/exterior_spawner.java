package script.poi.exterior_spawner;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.utils;
import script.library.ai_lib;
import script.library.create;
import script.library.locations;

public class exterior_spawner extends script.base_script
{
	public exterior_spawner()
	{
	}
	public static final String TABLE_BEGIN = "datatables/spawning/poi_spawner/";
	public static final String TYPE = "TYPE";
	public static final String POP = "POP";
	public static final String RADIUS = "RADIUS";
	public static final String DELAY = "DELAY";
	public static final String SCRIPTS = "SCRIPTS";
	public static final String SENTINEL = "SENTINEL";
	public static final String PLANET_LEVEL = "PLANET_LEVEL";
	public static final String SCRIPTVAR_CREATIONS = "myCreations";
	public static final String SCRIPTVAR_TIMERS = "respawnTimes";
	public static final String SCRIPTVAR_LAST_LEVEL = "lastLevel";
	public static final float CAP_SPAWN_TIME = 86400.0f;
	public void checkFactionalSpawners(obj_id self) throws InterruptedException
	{
		obj_id objContainer = getTopMostContainer(self);
		location locTest = getLocation(self);
		if (objContainer != self)
		{
			locTest = getLocation(objContainer);
		}
		obj_id[] objParents = getAllObjectsWithScript(locTest, 250, "systems.gcw.factional_dungeon_parent");
		if (objParents != null)
		{
			if (objParents.length > 0)
			{
				dictionary dctParams = new dictionary();
				dctParams.put("objSpawner", self);
				messageTo(objParents[0], "validateSpawner", dctParams, 1, false);
			}
		}
	}
	public int OnInitialize(obj_id self) throws InterruptedException
	{
		checkFactionalSpawners(self);
		messageTo(self, "spawnNpcs", null, 0, false);
		return SCRIPT_CONTINUE;
	}
	public int exteriorNpcDied(obj_id self, dictionary params) throws InterruptedException
	{
		obj_id deadNpc = params.getObjId("object");
		if (!utils.hasScriptVar(self, SCRIPTVAR_CREATIONS))
		{
			CustomerServiceLog("SPAWNER_OVERLOAD", "Spawner " + self + " tried to spawn a replacement for " + deadNpc + " but I don't have a list!");
			return SCRIPT_CONTINUE;
		}
		obj_id[] spawnedList = utils.getObjIdArrayScriptVar(self, SCRIPTVAR_CREATIONS);
		int[] npcRespawnTimes = utils.getIntArrayScriptVar(self, SCRIPTVAR_TIMERS);
		for (int i = 0; i < spawnedList.length; ++i)
		{
			if (spawnedList[i] == deadNpc)
			{
				int tableDelay = (int)getDelay(self);
				int curTime = getGameTime();
				int delayTime = curTime + tableDelay;
				npcRespawnTimes[i] = delayTime;
				spawnedList[i] = obj_id.NULL_ID;
			}
		}
		utils.setScriptVar(self, SCRIPTVAR_TIMERS, npcRespawnTimes);
		float delay = getDelay(self);
		messageTo(self, "spawnNpcs", null, delay, false);
		return SCRIPT_CONTINUE;
	}
	public int spawnNpcs(obj_id self, dictionary params) throws InterruptedException
	{
		location here = getLocation(self);
		String planet = here.area;
		String table = TABLE_BEGIN + planet + ".iff";
		String spawner = "" + self;
		dictionary row = dataTableGetRow(table, spawner);
		String npcType = getType(row, self);
		if (row == null)
		{
			LOG("POI_SPAWNER", "Spawner " + self + " had a null dictionary entry in datatable " + table);
			return SCRIPT_CONTINUE;
		}
		int max = getMaxPop(row, self);
		int lastLevel = 0;
		if (utils.hasScriptVar(self, SCRIPTVAR_LAST_LEVEL))
		{
			lastLevel = utils.getIntScriptVar(self, SCRIPTVAR_LAST_LEVEL);
		}
		obj_id[] npcList = new obj_id[max];
		int[] npcRespawnTimes = new int[max];
		if (!utils.hasScriptVar(self, SCRIPTVAR_CREATIONS))
		{
			for (int x = 0; x < max; ++x)
			{
				int level = getRandomPlanetCreatureLevel(self, npcType, lastLevel, here, row);
				lastLevel = level;
				obj_id npc = spawnMob(self, max, level, here, row);
				if (!isIdValid(npc))
				{
					LOG("POI_SPAWNER", "Spawner " + self + " failed to create mob");
					return SCRIPT_CONTINUE;
				}
				npcList[x] = npc;
				npcRespawnTimes[x] = getGameTime();
			}
			utils.setScriptVar(self, SCRIPTVAR_CREATIONS, npcList);
			utils.setScriptVar(self, SCRIPTVAR_TIMERS, npcRespawnTimes);
		}
		else
		{
			npcList = utils.getObjIdArrayScriptVar(self, SCRIPTVAR_CREATIONS);
			npcRespawnTimes = utils.getIntArrayScriptVar(self, SCRIPTVAR_TIMERS);
			if (npcList.length != npcRespawnTimes.length)
			{
				LOG("POI_SPAWNER", "Spawner " + self + " Spawn list and timer list dont match.");
				return SCRIPT_CONTINUE;
			}
			for (int i = 0; i < npcList.length; ++i)
			{
				if (!isIdValid(npcList[i]) || !exists(npcList[i]))
				{
					int validCreateTime = npcRespawnTimes[i];
					if (getGameTime() < validCreateTime)
					{
						int delayTime = (int)getDelay(self);
						delayTime = delayTime / 2;
						messageTo(self, "spawnNpcs", null, delayTime, false);
						return SCRIPT_CONTINUE;
					}
					int level = getRandomPlanetCreatureLevel(self, npcType, lastLevel, here, row);
					lastLevel = level;
					obj_id npc = spawnMob(self, max, level, here, row);
					npcList[i] = npc;
					npcRespawnTimes[i] = getGameTime();
				}
			}
		}
		utils.setScriptVar(self, SCRIPTVAR_LAST_LEVEL, lastLevel);
		return SCRIPT_CONTINUE;
	}
	public void npcScriptAttacher(obj_id creature, String scriptList) throws InterruptedException
	{
		if (scriptList == null || scriptList.equals(""))
		{
			return;
		}
		String[] scriptArray = split(scriptList, ',');
		for (int i = 0; i < scriptArray.length; i++)
		{
			attachScript(creature, scriptArray[i]);
		}
	}
	public String getType(dictionary row, obj_id self) throws InterruptedException
	{
		if (row == null)
		{
			return "thug";
		}
		String type = row.getString(TYPE);
		if (type == null || type.equals(""))
		{
			type = "thug";
		}
		return type;
	}
	public float getDelay(obj_id self) throws InterruptedException
	{
		location here = getLocation(self);
		String planet = here.area;
		String table = TABLE_BEGIN + planet + ".iff";
		String spawner = "" + self;
		dictionary row = dataTableGetRow(table, spawner);
		if (row == null)
		{
			LOG("POI_SPAWNER", "Spawner " + self + " had a null dictionary entry in datatable " + table);
			return 120.0f;
		}
		float delay = row.getFloat(DELAY);
		if (delay <= 0.0f || delay >= CAP_SPAWN_TIME)
		{
			delay = 120.0f;
		}
		return delay;
	}
	public int getCurrentPop(obj_id self) throws InterruptedException
	{
		int current = 0;
		if (utils.hasScriptVar(self, SCRIPTVAR_CREATIONS))
		{
			Vector npcList = utils.getResizeableObjIdArrayScriptVar(self, SCRIPTVAR_CREATIONS);
			if (npcList != null)
			{
				utils.removeScriptVar(self, SCRIPTVAR_CREATIONS);
				current = npcList.size();
			}
		}
		return current;
	}
	public boolean isNpcSentinel(dictionary row, obj_id self) throws InterruptedException
	{
		int isSentinel = 0;
		if (row.containsKey(SENTINEL))
		{
			isSentinel = row.getInt(SENTINEL);
		}
		if (isSentinel > 0)
		{
			return true;
		}
		return false;
	}
	public int getMaxPop(dictionary row, obj_id self) throws InterruptedException
	{
		int pop = row.getInt(POP);
		if (pop == 0)
		{
			pop = 5;
		}
		return pop;
	}
	public float getRadius(dictionary row, obj_id self, int max) throws InterruptedException
	{
		if (row == null)
		{
			return 1.5f;
		}
		float radius = row.getFloat(RADIUS);
		if (radius < 0)
		{
			radius = 1.5f;
		}
		if (max > 1 && radius == 0)
		{
			radius = 1.5f;
		}
		return radius;
	}
	public String getScripts(dictionary row, obj_id self) throws InterruptedException
	{
		if (row == null)
		{
			return "";
		}
		String scripts = row.getString(SCRIPTS);
		return scripts;
	}
	public boolean usePlanetLevel(dictionary row, obj_id self) throws InterruptedException
	{
		if (row == null)
		{
			return false;
		}
		int useLevel = row.getInt(PLANET_LEVEL);
		return (useLevel == 1);
	}
	public obj_id spawnMob(obj_id spawner, int max, int level, location here, dictionary row) throws InterruptedException
	{
		if (!isIdValid(spawner) || !exists(spawner))
		{
			return obj_id.NULL_ID;
		}
		if (max <= 0)
		{
			return obj_id.NULL_ID;
		}
		if (row == null || row.isEmpty())
		{
			return obj_id.NULL_ID;
		}
		String npcType = getType(row, spawner);
		float radius = getRadius(row, spawner, max);
		String scripts = getScripts(row, spawner);
		if (radius > 0)
		{
			float newX = rand((radius * (-1f)), radius);
			float newZ = rand((radius * (-1f)), radius);
			here.x = here.x + newX;
			here.z = here.z + newZ;
		}
		obj_id npc = create.object(npcType, here, level);
		create.addDestroyMessage(npc, "exteriorNpcDied", 3, spawner);
		float spawnerYaw = getYaw(spawner);
		setYaw(npc, spawnerYaw);
		if (isNpcSentinel(row, spawner))
		{
			setObjVar(npc, "SENTINEL", 1);
			ai_lib.setDefaultCalmBehavior(npc, ai_lib.BEHAVIOR_SENTINEL);
		}
		else
		{
			setObjVar(npc, "LOITER", 1);
			ai_lib.setDefaultCalmBehavior(npc, ai_lib.BEHAVIOR_LOITER);
		}
		if (scripts != null && !scripts.equals(""))
		{
			npcScriptAttacher(npc, scripts);
		}
		return npc;
	}
	public int getRandomPlanetCreatureLevel(obj_id spawner, String npcType, int lastLevel, location here, dictionary row) throws InterruptedException
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
			if (usePlanetLevel(row, spawner))
			{
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
						CustomerServiceLog("exterior_spawner_level", "Spawner " + spawner + " tried to spawn a creature whose base level from creatures.tab (" + baseLevel + ") was outside of the planet's range (" + minLevel + " to " + maxLevel + ").");
					}
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
