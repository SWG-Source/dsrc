package script.theme_park.wod;

import script.dictionary;
import script.library.create;
import script.library.pet_lib;
import script.library.utils;
import script.location;
import script.obj_id;

public class corrupted_rancor_cage_controller extends script.base_script {

	public corrupted_rancor_cage_controller()
	{
	}

	public int OnAttach(obj_id self) throws InterruptedException
	{
		createTriggerVolume("cageInterior", 7f, true);
		messageTo(self, "resetCage", null, 3f, false);
		utils.setScriptVar(self, "wod.cageEffectPoints",
				getAllObjectsWithTemplate(getLocation(self), 8f, "object/tangible/ground_spawning/patrol_waypoint.iff"));
		obj_id[] creatures = getAllObjectsWithObjVar(getLocation(self), 30f, "wod.kyrisa");
		if(creatures != null && creatures.length > 0)
		{
			utils.setScriptVar(self, "wod.kyrisa", creatures[0]);
			utils.setScriptVar(creatures[0], "wod.cage_controller", self);
		}
		return SCRIPT_CONTINUE;
	}

	public int resetCage(obj_id self, dictionary params) throws InterruptedException
	{
		final obj_id[] contents = getTriggerVolumeContents(self, "cageInterior");
		final location loc = getLocation(self);
		final obj_id cell = getContainedBy(self);
		final obj_id wall = createObject("object/static/item/item_stone_cage_wall.iff",
				new location(-90.9413f, -100.8f, -96.9441f, loc.area, cell));
		setYaw(wall, -7f);
		utils.setScriptVar(self, "wod.wall", wall);
		for(obj_id o : contents)
		{
			if(isPlayer(o) || pet_lib.isPet(o) || isDead(o))
			{
				setLocation(o, new location(-90f, -103.2f, -126.9f, loc.area, cell));
			}
		}
		if(params.getBoolean("wasDefeat"))
		{
			utils.setScriptVar(self, "wod.ready", false);
			messageTo(self, "doSpawnEvent", null, 900f, false);
		}
		else
		{
			messageTo(self, "doSpawnEvent", null, 0f, false);
		}
		return SCRIPT_CONTINUE;
	}

	public int destroyCage(obj_id self, dictionary params) throws InterruptedException
	{
		utils.setScriptVar(self, "wod.ready", false);
		final obj_id[] players = getPlayerCreaturesInRange(self, 40f);
		for(obj_id e : utils.getObjIdArrayScriptVar(self, "wod.cageEffectPoints"))
		{
			playClientEffectLoc(players, "clienteffect/lair_damage_heavy_shake.cef", getLocation(e), 0f);
		}
		for(obj_id e : getAllObjectsWithTemplate(getLocation(self), 40f, "object/tangible/ground_spawning/patrol_waypoint.iff"))
		{
			playClientEffectLoc(players, "appearance/pt_miasma_of_fog_greenish.prt", getLocation(e), 0f);
		}
		destroyObject(utils.getObjIdScriptVar(self, "wod.wall"));
		return SCRIPT_CONTINUE;
	}

	public int doSpawnEvent(obj_id self, dictionary params) throws InterruptedException
	{
		final obj_id creature = create.createCreature("wod_mutant_rancor_boss",
				new location(-90.8537f, -100.0102f, -91.8525f, getLocation(self).area, getContainedBy(self)), true);
		attachScript(creature, "theme_park.wod.corrupted_rancor_mutation_npc");
		utils.setScriptVar(self, "wod.ready", true);
		return SCRIPT_CONTINUE;
	}

	public int OnHearSpeech(obj_id self, obj_id objSpeaker, String strText) throws InterruptedException
	{
		if(isGod(objSpeaker))
		{
			if(strText.equalsIgnoreCase("resetCage"))
			{
				messageTo(self, "resetCage", null, 0f, false);
				sendSystemMessageTestingOnly(objSpeaker, "Resetting cage...");
			}
			if(strText.equalsIgnoreCase("destroyCage"))
			{
				messageTo(self, "destroyCage", null, 0f, false);
				sendSystemMessageTestingOnly(objSpeaker, "Destroying cage...");
			}
		}
		return SCRIPT_CONTINUE;
	}

}
