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
	private location[] patrolPoints;

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
	// These are the different types of NPC's that can be spawned
	// into a patrol.  The second value is a lower bound for the
	// random number, the third value is an upper bound for the
	// random number.
	private String[][] patrolNpcTypes = {
			{"stormtrooper", "0", "50"},
			{"stormtrooper_squad_leader", "51", "75"},
			{"stormtrooper_captain", "76", "85"},
			{"stormtrooper_major", "86", "90"},
			{"stormtrooper_medic", "91", "100"}
	};
	private String[][] officerPatrolTypes = {
			{"stormtrooper", "0", "25"},
			{"imperial_private", "26", "50"},
			{"imperial_sergeant", "51", "60"},
			{"imperial_staff_sergeant", "61", "68"},
			{"imperial_staff_corporal", "69", "75"},
			{"imperial_first_lieutenant", "76", "81"},
			{"imperial_second_lieutenant", "82", "86"},
			{"imperial_major", "87", "90"},
			{"imperial_colonel", "91", "93"},
			{"imperial_brigadier_general", "94", "96"},
			{"imperial_major_general", "97", "98"},
			{"imperial_lieutenant_general", "99", "100"},
			{"imperial_general", "101", "101"}
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
		spawnGuardPatrol(self, "officers", new location(-4272.7f, 3.0f, -2397.1f), 3, getPatrolPoints(officerPatrolPoints), "officer", false);
		spawnGuardPatrol(self, "squadOne", new location(-4272.7f, 3.0f, -2397.1f), 3, getPatrolPoints(smallSquadOnePoints), "trooper", true);
		spawnGuardPatrol(self, "squadTwo", new location(-4272.7f, 3.0f, -2397.1f), 3, getPatrolPoints(smallSquadTwoPoints), "trooper", true);
		spawnGuardPatrol(self, "squadThree", new location(-4277.5f, 3.0f, -2407.9f), 3, getPatrolPoints(smallSquadOnePoints), "trooper", true);
		spawnGuardPatrol(self, "squadFour", new location(-4176.7f, 3.0f, -2405.1f), 3, getPatrolPoints(smallSquadTwoPoints), "trooper", true);
		spawnGuardPatrol(self, "largeSquadOne", new location(-4272.7f, 3.0f, -2395.1f), 5, getPatrolPoints(largeSquadOnePoints), "trooper", false);
		spawnGuardPatrol(self, "largeSquadTwo", new location(-4176.7f, 3.0f, -2395.1f), 5, getPatrolPoints(largeSquadTwoPoints), "trooper", false);
		spawnSG567();
		spawnLX466();
		//spawnSocialGroups(self);

		patrolPoints = getPatrolPoints(droidPatrolCoords);

		messageTo(self, "spawnDroidPatrol", null, 10, false);
	}
	private void spawnSG567() throws InterruptedException{
		obj_id sgSpawn = spawn("sg_567", -4216.4f, 3.0f, -2435.8f, 160.0f, null, "npc_imperial");
		setObjVar(sgSpawn, "quest_table", "sg_567");
		setName(sgSpawn, "SG-567");
		attachScript(sgSpawn, "npc.static_quest.quest_convo");
	}
	private void spawnLX466() throws InterruptedException{
		obj_id lxSpawn = spawn("lx_466", -4182.0f, 3.0f, -2386.0f, -45.0f, null, "npc_imperial");
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
			spawn("imperial_recruiter", coord[0], coord[1], coord[2], coord[3], null, "npc_imperial");
		}
	}
	private void spawnCommoners() throws InterruptedException
	{
		// These are the coordinates and yaw values at which the commoners
		// will spawn.  A script method was used to spawn them instead
		// of datatable as I couldn't figure out how to make them unattackable
		// in the datatable.  Lame, I know.
		float[][] coords = {{-4260.1f, 3.0f, -2425.0f, rand(1f,180f)},
							{-4225.7f, 3.0f, -2424.2f, rand(1f,180f)},
							{-4181.3f, 3.0f, -2422.7f, rand(1f,180f)},
							{-4271.8f, 3.0f, -2390.8f, rand(1f,180f)},
							{-4236.2f, 3.0f, -2379.6f, rand(1f,180f)},
							{-4245.2f, 3.0f, -2371.8f, rand(1f,180f)},
							{-4222.4f, 3.0f, -2365.2f, rand(1f,180f)}
		};

		for (float[] coord : coords) {
			spawn("commoner", coord[0], coord[1], coord[2], coord[3], null, "npc_imperial");
		}
	}
	public void spawnSocialGroups(obj_id self) throws InterruptedException{
		// spawn("object/tangible/poi/tatooine/poi_city_convo.iff", 0f, 0.5f, -8.2f, 0, getCellId(self, "lobby"), "npc_imperial");
	}
	public int spawnDroidPatrol(obj_id self, dictionary params) throws InterruptedException{
		obj_id droidSpawn;
		String[] droid;
		// Sets the number of droids that will spawn on the outpost grounds.
		int maxDroids = 4;

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
			ai_lib.setPatrolPath(droidSpawn, patrolPoints);
			setObjVar(self, "current.droids", currentDroids + 1);
		}
		messageTo(self, "spawnDroidPatrol", params, 10, false);
		return SCRIPT_CONTINUE;
	}
	private void spawnGuardPatrol(obj_id self, String patrolName, location spawnLocation, int totalMembers, location [] patrolPoints, String npcType, boolean flip) throws InterruptedException{
		String pName = "patrols." + patrolName;
		setObjVar(self, pName + ".startLocation", spawnLocation);
		setObjVar(self, pName + ".totalMembers", totalMembers);
		setObjVar(self, pName + ".points", patrolPoints);
		setObjVar(self, pName + ".flipPaths", flip);
		setObjVar(self, pName + ".types", npcType);

		spawnGuardPatrolMember(self, pName);

		dictionary params = new dictionary();
		params.put("name", pName);
		params.put("size", totalMembers);
		params.put("type", npcType);
		messageTo(self, "spawnMoreGuards", params, 10, false);
	}
	private void spawnGuardPatrolMember(obj_id self, String patrolName) throws InterruptedException{
		location start = getLocationObjVar(self, patrolName + ".startLocation");
		obj_id guardSpawn = spawn(npcToSpawn(getStringObjVar(self, patrolName + ".types")), start.x, start.y, start.z, 0f, null, null);
		if(getBooleanObjVar(self, patrolName + ".flipPaths"))
			ai_lib.setPatrolFlipPath(guardSpawn, getLocationArrayObjVar(self, patrolName + ".points"));
		else
			ai_lib.setPatrolPath(guardSpawn, getLocationArrayObjVar(self, patrolName + ".points"));
		int currentPop = getIntObjVar(self, patrolName + ".current.guards") + 1;
		if (currentPop == 1)
		{
			setObjVar(self, patrolName + ".leader", guardSpawn);
		}
		else
		{
			obj_id leader = getObjIdObjVar(self, patrolName + ".leader");
			ai_lib.followInFormation(guardSpawn, leader, ai_lib.FORMATION_COLUMN, currentPop - 1);
			setMovementPercent(guardSpawn, 1.2f);
		}
		setCreatureStatic(guardSpawn, false);
		setObjVar(self, patrolName + ".current.guards", currentPop);
		dictionary params = new dictionary();
		params.put("name", patrolName);
		params.put("size", getObjVar(self, patrolName + ".totalMembers"));
		params.put("type", getObjVar(self, patrolName + ".types"));
	}
	public int spawnMoreGuards(obj_id self, dictionary params) throws InterruptedException{
		String patrolName = params.getString("name");
		int maxGuardsToSpawn = params.getInt("size");
		String currentPopulation = patrolName + ".current.guards";
		if (!hasObjVar(self, currentPopulation))
		{
			setObjVar(self, currentPopulation, 0);
			spawnGuardPatrolMember(self, patrolName);
		}
		else
		{
			if (getIntObjVar(self, currentPopulation) < maxGuardsToSpawn)
			{
				spawnGuardPatrolMember(self, patrolName);
			}
		}
		messageTo(self, "spawnMoreGuards", params, 10, false);
		return SCRIPT_CONTINUE;
	}
	private location[] getPatrolPoints(float[][] patrolCoords){
		location[] patrolPoints = new location[patrolCoords.length];
		// take the patrol coordinates and turn them into location objects such that we can
		// pass the location objects to the AI and have it traverse those locations.
		for (int i = 0; i < patrolCoords.length; i++){
			patrolPoints[i] = new location(patrolCoords[i][0], patrolCoords[i][1], patrolCoords[i][2], "dantooine", null);
		}

		return patrolPoints;
	}
	public String npcToSpawn(String npcType){
		// get NPC type:
		int roll = rand(0,100);
		String [][] npcTypes = patrolNpcTypes;
		if(npcType != null && npcType.equals("officer")){
			npcTypes = officerPatrolTypes;
		}
		for(String[] npcConfig : npcTypes){
			if(roll >= Integer.parseInt(npcConfig[1]) && roll <= Integer.parseInt(npcConfig[2]))
				return npcConfig[0];
		}
		return patrolNpcTypes[0][0];
	}
	public obj_id spawn(String obj, float x, float y, float z, float yaw, obj_id cell, String mood) throws InterruptedException{
		obj_id spawnedObject;
		if(obj.contains(".iff")){
			spawnedObject = createObject(obj, new location(x, y, z, "dantooine", cell));
		}
		else{
			spawnedObject = create.object(obj, new location(x, y, z, "dantooine", cell));
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
