package script.theme_park.tatooine.darklighter_spawner;

import script.dictionary;
import script.library.ai_lib;
import script.library.create;
import script.library.locations;
import script.location;
import script.obj_id;

public class masterspawner extends script.base_script
{
	// The world coordinates of where the droids will travel.
	// Note:  The route is flipped when completed such that
	// the droids don't try to go from start to finish directly
	// and get hung up on Darklighter's estate wall.  They dont
	// block there when they do get stuck, but instead warp
	// to the first point - which looks weird.
	// NOTE:  More patrol coordinates may be added to this array.
	public float[][] patrolCoords = {
			{-646.0f, 13.0f, -6850.0f},
			{-678.0f, 9.9f, -6830.0f},
			{-631.4f, 6.7f, -6770.1f},
			{-674.0f, 8.0f, -6734.0f},
			{-692.0f, 8.0f, -6716.0f},
			{-703.0f, 5.8f, -6652.0f},
			{-726.0f, 6.8f, -6660.5f}
	};
	location[] patrolPoints;

	// These are the different types of droids that can spawn.
	// The secondary value is the speed at which the droid travels
	// in relation to "normal" speed which is 1.0.
	public String[][] droidTypes = {
			{"cll8_binary_load_lifter","0.7"},
			{"eg6_power_droid","0.6"},
			{"r2","1.0"},
			{"r3","1.0"},
			{"r4","1.1"},
			{"r5","1.2"}
	};
	public static final String guardTable = "datatables/npc/guard_spawner/guard.iff";

	public masterspawner()
	{
	}
	public int OnInitialize(obj_id self) throws InterruptedException
	{
		debugServerConsoleMsg(self, "Initialized Darklighter spawner script");
		messageTo(self, "spawnThings", null, 2, true);
		return SCRIPT_CONTINUE;
	}
	public int spawnThings(obj_id self, dictionary params) throws InterruptedException
	{
		spawnEveryone(self);
		return SCRIPT_CONTINUE;
	}
	public void spawnEveryone(obj_id self) throws InterruptedException
	{
		spawnCourtyardPeeps();
		spawnGuard();
		spawnHuff(self);
		spawnConversations(self);

		patrolPoints = getPatrolPoints();

		messageTo(self, "spawnDroidPatrol", null, 10, false);
		messageTo(self, "spawnMoreGuards", null, 10, false);
	}
	public void spawnCourtyardPeeps() throws InterruptedException
	{
		// These are the coordinates and yaw values at which the commoners
		// will spawn.  A script method was used to spawn them instead
		// of datatable as I couldn't figure out how to make them unattackable
		// in the datatable.  Lame, I know.
		float[][] coords = {{-688.7f, 8.0f, -6724.0f, -163},
							{-672.0f, 8.0f, -6728.8f, -144},
							{-706.9f, 8.0f, -6724.2f, 154},
							{-722.9f, 8.0f, -6728.2f, 138}
		};

		for (float[] coord : coords) {
			spawn("commoner", coord[0], coord[1], coord[2], coord[3], null, "npc_imperial");
		}
	}
	public void spawnGuard() throws InterruptedException
	{
		spawn("huff_guard", -693.7f, 8.0f, -6733.9f, 0, null, "npc_imperial");
	}
	public void spawnHuff(obj_id self) throws InterruptedException
	{
		spawn("huff_darklighter", 0.0f, 1.0f, 4.2f, 0, getCellId(self, "lobby"), "npc_imperial");
	}
	public void spawnConversations(obj_id self) throws InterruptedException{
		spawn("object/tangible/poi/tatooine/poi_city_convo.iff", 0.0f, 0.5f, -8.2f, 0, getCellId(self, "lobby"), "npc_imperial");
		spawn("object/tangible/poi/tatooine/poi_city_convo.iff", 6.4f, 0.5f, -7.4f, 0, getCellId(self, "lobby"), "npc_imperial");
		spawn("object/tangible/poi/tatooine/poi_city_convo.iff", -16.5f, 0.2f, 7.2f, 0, getCellId(self, "livingroom1"), "npc_imperial");
		spawn("object/tangible/poi/tatooine/poi_city_convo.iff", -22.9f, 1.0f, -8.9f, 0, getCellId(self, "kitchen"), "npc_imperial");
	}
	public int spawnDroidPatrol(obj_id self, dictionary params) throws InterruptedException{
		obj_id droidSpawn;
		String[] droid;
		// Sets the number of droids that will spawn on estate grounds.
		int maxDroids = 3;

		int currentDroids = getIntObjVar(self, "current.droids");
		if(currentDroids < maxDroids){
			int spawnPoint = 1;
			// get a random droid from our list of droids.
			droid = droidTypes[rand(0, droidTypes.length - 1)];

			// spawn the droid and assign the obj_id to the droidSpawn var.
			droidSpawn = spawn(droid[0], patrolPoints[spawnPoint].x, patrolPoints[spawnPoint].y, patrolPoints[spawnPoint].z, 0, null, "");

			// handle AI features - make the droid movable, set their speed and set their path.
			setCreatureStatic(droidSpawn, false);
			setMovementPercent(droidSpawn, Float.parseFloat(droid[1]));
			ai_lib.setPatrolFlipPath(droidSpawn, patrolPoints);
			setObjVar(self, "current.droids", currentDroids + 1);
		}
		messageTo(self, "spawnDroidPatrol", null, 10, false);
		return SCRIPT_CONTINUE;
	}
	public void spawnGuardPatrol(obj_id self) throws InterruptedException{
		obj_id guardSpawn = spawn(npcToSpawn(getGuardArea(self)),-646.0f, 13.0f, -6850.0f, 0.0f, null, null);
		ai_lib.setPatrolFlipPath(guardSpawn, patrolPoints);
		int currentPop = getIntObjVar(self, "current.guards");
		currentPop++;
		if (currentPop == 1)
		{
			setObjVar(self, "leader", guardSpawn);
		}
		else
		{
			obj_id leader = getObjIdObjVar(self, "leader");
			ai_lib.followInFormation(guardSpawn, leader, ai_lib.FORMATION_COLUMN, currentPop - 1);
			setMovementPercent(guardSpawn, 1.2f);
		}
		setCreatureStatic(guardSpawn, false);
		setObjVar(self, "current.guards", currentPop);
		messageTo(self, "spawnMoreGuards", null, 10, false);
	}
	public int spawnMoreGuards(obj_id self, dictionary params) throws InterruptedException{
		int maxGuardsToSpawn = 5;
		if (!hasObjVar(self, "current.guards"))
		{
			setObjVar(self, "current.guards", 0);
			spawnGuardPatrol(self);
		}
		else
		{
			if (getIntObjVar(self, "current.guards") < maxGuardsToSpawn)
			{
				spawnGuardPatrol(self);
			}
		}
		return SCRIPT_CONTINUE;
	}
	private location[] getPatrolPoints(){
		location[] patrolPoints = new location[patrolCoords.length];
		// take the patrol coordinates and turn them into location objects such that we can
		// pass the location objects to the AI and have it traverse those locations.
		for (int i = 0; i < patrolCoords.length; i++){
			patrolPoints[i] = new location(patrolCoords[i][0], patrolCoords[i][1], patrolCoords[i][2], "tatooine", null);
		}

		return patrolPoints;
	}
	public String npcToSpawn(String type) throws InterruptedException
	{
		String[] guardList = dataTableGetStringColumnNoDefaults(guardTable, type);
		return guardList[rand(0, guardList.length - 1)];
	}
	public String getGuardArea(obj_id self) throws InterruptedException
	{
		return locations.getGuardSpawnerRegionName(getLocation(self));
	}
	public obj_id spawn(String obj, float x, float y, float z, float yaw, obj_id cell, String mood) throws InterruptedException{
		obj_id spawnedObject;
		if(obj.contains(".iff")){
			spawnedObject = createObject(obj, new location(x, y, z, "tatooine", cell));
		}
		else{
			spawnedObject = create.object(obj, new location(x, y, z, "tatooine", cell));
		}
		setCreatureStatic(spawnedObject, true);
		setInvulnerable(spawnedObject, true);
		if(yaw > 0){
			setYaw(spawnedObject, yaw);
		}
		ai_lib.setDefaultCalmMood(spawnedObject, mood);
		return spawnedObject;
	}
}
