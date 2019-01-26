package script.theme_park.dantooine.imperial_outpost;

import script.dictionary;
import script.library.ai_lib;
import script.library.create;
import script.location;
import script.obj_id;

public class masterspawner extends script.base_script
{
	// The world coordinates of where the droids will travel.
	// NOTE:  More patrol coordinates may be added to this array.
	private float[][] droidPatrolCoords = {
			{-4244.3f, 3.0f, -2351.2f},
			{-4244.3f, 3.0f, -2357.9f},
			{-4226.0f, 3.0f, -2370.0f},
			{-4244.3f, 3.0f, -2384.3f},
			{-4226.0f, 3.0f, -2370.0f},
			{-4204.5f, 3.0f, -2387.1f},
			{-4226.0f, 3.0f, -2370.0f},
			{-4204.5f, 3.0f, -2358.4f},
			{-4226.0f, 3.0f, -2370.0f}
	};
	private float[][] officerPatrolPoints = {
			{-4224.2f, 3.0f, -2370.5f},
			{-4224.2f, 3.0f, -2395.9f},
			{-4248.4f, 3.0f, -2395.9f},
			{-4225.5f, 3.0f, -2424.9f},
			{-4204.6f, 3.0f, -2395.9f},
			{-4224.2f, 3.0f, -2395.9f},
			{-4224.2f, 3.0f, -2370.5f}
	};
	private float[][] smallSquadOnePoints = {
			{-4204.6f, 3.0f, -2349.8f},
			{-4204.6f, 3.0f, -2387.4f},
			{-4232.2f, 3.0f, -2395.9f},
			{-4272.7f, 3.0f, -2395.9f},
			{-4277.5f, 3.0f, -2407.9f},
			{-4287.9f, 3.0f, -2407.9f}
	};
	private float[][] smallSquadTwoPoints = {
			{-4253.7f, 3.0f, -2349.8f},
			{-4253.7f, 3.0f, -2379.9f},
			{-4216.5f, 3.0f, -2396.0f},
			{-4176.7f, 3.0f, -2396.0f},
			{-4173.8f, 3.0f, -2407.9f},
			{-4160.9f, 3.0f, -2407.9f}
	};
	private float[][] smallSquadThreePoints = {
			{-4287.9f, 3.0f, -2407.9f},
			{-4277.5f, 3.0f, -2407.9f},
			{-4272.7f, 3.0f, -2395.9f},
			{-4232.2f, 3.0f, -2395.9f},
			{-4204.6f, 3.0f, -2387.4f},
			{-4204.6f, 3.0f, -2349.8f}
	};
	private float[][] smallSquadFourPoints = {
			{-4160.9f, 3.0f, -2407.9f},
			{-4173.8f, 3.0f, -2407.9f},
			{-4176.7f, 3.0f, -2396.0f},
			{-4216.5f, 3.0f, -2396.0f},
			{-4253.7f, 3.0f, -2379.9f},
			{-4253.7f, 3.0f, -2349.8f}
	};
	private float[][] largeSquadOnePoints = {
			{-4272.7f, 3.0f, -2395.1f},
			{-4272.7f, 3.0f, -2407.1f},
			{-4288.0f, 3.0f, -2407.1f},
			{-4259.2f, 3.0f, -2330.7f},
			{-4194.3f, 3.0f, -2330.7f},
			{-4160.6f, 3.0f, -2408.2f},
			{-4176.7f, 3.0f, -2456.1f},
			{-4270.7f, 3.0f, -2451.8f},
			{-4288.0f, 3.0f, -2407.1f},
			{-4272.7f, 3.0f, -2407.1f},
			{-4272.7f, 3.0f, -2419.1f},
			{-4176.7f, 3.0f, -2419.1f},
			{-4176.7f, 3.0f, -2395.1f}
	};
	private float[][] largeSquadTwoPoints = {
			{-4176.7f, 3.0f, -2395.1f},
			{-4176.7f, 3.0f, -2419.1f},
			{-4272.7f, 3.0f, -2419.1f},
			{-4272.7f, 3.0f, -2407.1f},
			{-4288.0f, 3.0f, -2407.1f},
			{-4270.7f, 3.0f, -2451.8f},
			{-4176.7f, 3.0f, -2456.1f},
			{-4160.6f, 3.0f, -2408.2f},
			{-4194.3f, 3.0f, -2330.7f},
			{-4259.2f, 3.0f, -2330.7f},
			{-4288.0f, 3.0f, -2407.1f},
			{-4272.7f, 3.0f, -2407.1f},
			{-4272.7f, 3.0f, -2395.1f}
	};

	// These are the different types of droids that can spawn.
	// The secondary value is the speed at which the droid travels
	// in relation to "normal" speed which is 1.0.
	private String[][] droidTypes = {
			{"cll8_binary_load_lifter","0.7"},
			{"eg6_power_droid","0.6"},
			{"r2","1.0"},
			{"r3","1.0"},
			{"r4","1.1"},
			{"r5","1.2"}
	};

	public masterspawner()
	{
	}
	public int OnInitialize(obj_id self) throws InterruptedException
	{
		debugServerConsoleMsg(self, "Initialized Imperial Outpost spawner script");
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
		spawnCommoners();
		spawnRecruiters();
		spawnGuardPatrol(self, "officers", new location(-4272.7f, 3.0f, -2397.1f), 3, getPatrolPoints(officerPatrolPoints), "plain", false);
		spawnGuardPatrol(self, "squadOne", new location(-4272.7f, 3.0f, -2397.1f), 3, getPatrolPoints(smallSquadOnePoints), "armored", true);
		spawnGuardPatrol(self, "squadTwo", new location(-4272.7f, 3.0f, -2397.1f), 3, getPatrolPoints(smallSquadTwoPoints), "armored", true);
		spawnGuardPatrol(self, "squadThree", new location(-4277.5f, 3.0f, -2407.9f), 3, getPatrolPoints(smallSquadThreePoints), "armored", true);
		spawnGuardPatrol(self, "squadFour", new location(-4176.7f, 3.0f, -2405.1f), 3, getPatrolPoints(smallSquadFourPoints), "armored", true);
		spawnGuardPatrol(self, "largeSquadOne", new location(-4272.7f, 3.0f, -2395.1f), 5, getPatrolPoints(largeSquadOnePoints), "armored", false);
		spawnGuardPatrol(self, "largeSquadTwo", new location(-4176.7f, 3.0f, -2395.1f), 5, getPatrolPoints(largeSquadTwoPoints), "armored", false);
		spawnSG567();
		spawnLX466();
		//spawnSocialGroups(self);

		messageTo(self, "spawnDroidPatrol", null, 10, false);
	}
	private void spawnSG567() throws InterruptedException{
		obj_id sgSpawn = spawn("sg_567", -4216.4f, 3.0f, -2435.8f, 160.0f, null, "npc_imperial", true);
		setObjVar(sgSpawn, "quest_table", "sg_567");
		setName(sgSpawn, "SG-567");
		attachScript(sgSpawn, "npc.static_quest.quest_convo");
	}
	private void spawnLX466() throws InterruptedException{
		obj_id lxSpawn = spawn("lx_466", -4182.0f, 3.0f, -2386.0f, -45.0f, null, "npc_imperial", true);
		setObjVar(lxSpawn, "quest_table", "lx_466");
		setName(lxSpawn, "LX-466");
		attachScript(lxSpawn, "npc.static_quest.quest_convo");
	}
	private void spawnRecruiters() throws InterruptedException{
		// These are the coordinates and yaw values at which the recruiters
		// will spawn.  A script method was used to spawn them instead
		// of datatable as I couldn't figure out how to make them unattackable
		// in the datatable.  Lame, I know.
		float[][] coords = {
				{-4197.8f, 3.0f, -2413.3f, -90}
		};

		for (float[] coord : coords) {
			spawn("imperial_recruiter", coord[0], coord[1], coord[2], coord[3], null, "npc_imperial", true);
		}
	}
	private void spawnCommoners() throws InterruptedException
	{
		// These are the coordinates and yaw values at which the commoners
		// will spawn.  A script method was used to spawn them instead
		// of datatable as I couldn't figure out how to make them unattackable
		// in the datatable.  Lame, I know.
		float[][] coords = {{-4260.1f, 3.0f, -2425.0f, rand(1.0f, 180.0f)},
				{-4225.7f, 3.0f, -2424.2f, rand(1.0f, 180.0f)},
				{-4181.3f, 3.0f, -2422.7f, rand(1.0f, 180.0f)},
				{-4271.8f, 3.0f, -2390.8f, rand(1.0f, 180.0f)},
				{-4236.2f, 3.0f, -2379.6f, rand(1.0f, 180.0f)},
				{-4245.2f, 3.0f, -2371.8f, rand(1.0f, 180.0f)},
				{-4222.4f, 3.0f, -2365.2f, rand(1.0f, 180.0f)}
		};

		for (float[] coord : coords) {
			spawn("commoner", coord[0], coord[1], coord[2], coord[3], null, "npc_imperial", true);
		}
	}
	public void spawnSocialGroups(obj_id self) throws InterruptedException{
		// spawn("object/tangible/poi/tatooine/poi_city_convo.iff", 0f, 0.5f, -8.2f, 0, getCellId(self, "lobby"), "npc_imperial");
	}
	public int spawnDroidPatrol(obj_id self, dictionary params) throws InterruptedException{
		// Sets the number of droids that will spawn on the outpost grounds.
		int maxDroids = 4;
		int currentDroids = getIntObjVar(self, "current.droids");
		location[] points = getPatrolPoints(droidPatrolCoords);

		if(currentDroids < maxDroids){
			int spawnPoint = 1;
			// get a random droid from our list of droids.
			String [] droid = droidTypes[rand(0, droidTypes.length - 1)];

			// spawn the droid and assign the obj_id to the droidSpawn var.
			obj_id droidSpawn = spawn(droid[0], points[spawnPoint].x, points[spawnPoint].y, points[spawnPoint].z, 0, null, "", true);

			// handle AI features - make the droid movable, set their speed and set their path.
			setCreatureStatic(droidSpawn, false);
			setMovementPercent(droidSpawn, Float.parseFloat(droid[1]));
			ai_lib.setPatrolPath(droidSpawn, points);
			setObjVar(self, "current.droids", currentDroids + 1);
		}

		messageTo(self, "spawnDroidPatrol", params, 10, false);
		return SCRIPT_CONTINUE;
	}
	private void spawnGuardPatrol(obj_id self, String patrolName, location spawnLocation, int totalMembers, location [] patrolPoints, String npcType, boolean flip) throws InterruptedException{
		String pName = "patrols." + patrolName;
		setObjVar(self, pName + ".startLocation", spawnLocation);

		//spawnGuardPatrolMember(self, pName);
		obj_id patrolSpawnEgg = createObject("object/tangible/poi/spawnegg/patrol_spawnegg.iff", spawnLocation);

		setObjVar(patrolSpawnEgg, "name", patrolName);
		setObjVar(patrolSpawnEgg, "size", totalMembers);
		setObjVar(patrolSpawnEgg, "patrolPoints", patrolPoints);
		setObjVar(patrolSpawnEgg, "flipPaths", flip);
		setObjVar(patrolSpawnEgg, "faction", "imperial");
		setObjVar(patrolSpawnEgg, "invulnerable", false);
		setObjVar(patrolSpawnEgg, "respawnTime", 5.0f);
		setObjVar(patrolSpawnEgg, "patrolLevel", 66);
		setObjVar(patrolSpawnEgg, "squadClass", npcType);

		messageTo(patrolSpawnEgg, "beginSpawn", null, 10, false);
	}
	public location[] getPatrolPoints(float[][] patrolCoords){
		location[] patrolPoints = new location[patrolCoords.length];
		// take the patrol coordinates and turn them into location objects such that we can
		// pass the location objects to the AI and have it traverse those locations.
		for (int i = 0; i < patrolCoords.length; i++){
			patrolPoints[i] = new location(patrolCoords[i][0], patrolCoords[i][1], patrolCoords[i][2], "dantooine", null);
		}

		return patrolPoints;
	}
	public obj_id spawn(String obj, float x, float y, float z, float yaw, obj_id cell, String mood, boolean invuln) throws InterruptedException{
		obj_id spawnedObject;

		if(obj.contains(".iff")){
			spawnedObject = createObject(obj, new location(x, y, z, "dantooine", cell));
		}
		else{
			spawnedObject = create.object(obj, new location(x, y, z, "dantooine", cell));
		}

		setObjVar(spawnedObject, "spawner", getSelf());
		attachScript(spawnedObject, "theme_park.patrol_member");
		setCreatureStatic(spawnedObject, true);
		setInvulnerable(spawnedObject, invuln);
		setLevel(spawnedObject, rand(65,69));

		if(yaw > 0){
			setYaw(spawnedObject, yaw);
		}

		ai_lib.setDefaultCalmMood(spawnedObject, mood);

		return spawnedObject;
	}
}
