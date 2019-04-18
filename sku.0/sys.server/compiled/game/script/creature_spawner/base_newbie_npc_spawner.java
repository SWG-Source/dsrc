package script.creature_spawner;

import script.dictionary;
import script.library.*;
import script.location;
import script.obj_id;

import java.util.Vector;

public class base_newbie_npc_spawner extends script.base_script
{
	public int maxPop = 3;
	public boolean newbie = false;

	public base_newbie_npc_spawner()
	{
	}
	public int OnInitialize(obj_id self) throws InterruptedException
	{
		spawnCreatures(self);
		if(newbie) LOG("NewbieSpawn", "Spawning Init: " + self);
		return SCRIPT_CONTINUE;
	}
	public void spawnCreatures(obj_id self) throws InterruptedException
	{
		if(newbie) LOG("NewbieSpawn", "Spawning Anew: " + self);
		String whatToSpawn = utils.getStringScriptVar(self, "creatureToSpawn");
		if (whatToSpawn == null)
		{
			whatToSpawn = pickCreature();
			utils.setScriptVar(self, "creatureToSpawn", whatToSpawn);
		}
		if (hasObjVar(self, "pop"))
		{
			maxPop = getIntObjVar(self, "pop");
			if (newbie && maxPop > 20)
			{
				CustomerServiceLog("SPAWNER_OVERLOAD", "MaxPop of Spawner " + self + " was greater than 20.  It was " + maxPop + " in fact.");
			}
		}
		int count = utils.getIntScriptVar(self, "count");
		location goodLoc;
		obj_id spawned;
		Vector myCreations;
		Vector theList;
		while (count < maxPop)
		{
			if(newbie) LOG("NewbieSpawn", "spawning #" + count);
			goodLoc = pickLocation();
			if (goodLoc == null)
			{
				goodLoc = getLocation(self);
			}
			spawned = create.object(whatToSpawn, goodLoc);
			attachScript(spawned, "creature_spawner.death_msg");
			setObjVar(spawned, "creater", self);
			if (!utils.hasScriptVar(self, "myCreations"))
			{
				myCreations = new Vector();
				myCreations.setSize(0);
				utils.addElement(myCreations, spawned);
				utils.setScriptVar(self, "myCreations", myCreations);
			}
			else
			{
				theList = utils.getResizeableObjIdArrayScriptVar(self, "myCreations");
				if (theList.size() >= maxPop)
				{
					CustomerServiceLog("SPAWNER_OVERLOAD", "Tried to spawn something even though the list was full.");
					return;
				}
				else
				{
					theList.add(spawned);
					utils.setScriptVar(self, "myCreations", theList);
				}
			}
			if (hasObjVar(self, "useCityWanderScript"))
			{
				attachScript(spawned, "city.city_wander");
			}
			else
			{
				ai_lib.setDefaultCalmBehavior(spawned, ai_lib.BEHAVIOR_LOITER);
			}
			count++;
			utils.setScriptVar(self, "count", count);
		}
	}
	public String pickCreature() throws InterruptedException
	{
		// note: this method should be overridden in all child classes.  probably should
		// make an interface to enforce this.
		return "flail_cutthroat";
	}
	public location pickLocation() throws InterruptedException
	{
		location here = getLocation(getSelf());
		here.x = here.x + rand(-5, 5);
		here.z = here.z + rand(-5, 5);
		return locations.getGoodLocationAroundLocation(here, 1.0f, 1.0f, 1.5f, 1.5f);
	}
	public int creatureDied(obj_id self, dictionary params) throws InterruptedException
	{
		spawning.planetSpawnersCreatureDied(self, params.getObjId("deadGuy"));
		spawnCreatures(self);
		return SCRIPT_CONTINUE;
	}
}
