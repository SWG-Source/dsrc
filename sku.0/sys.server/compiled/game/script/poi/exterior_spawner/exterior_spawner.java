package script.poi.exterior_spawner;


import script.obj_id;

public class exterior_spawner extends script.poi.interior_spawner.interior_spawner
{
	public exterior_spawner()
	{
	}
	public int OnInitialize(obj_id self) throws InterruptedException
	{
		System.out.println("---Spawner ("+self.toString()+") is initializing...");
		checkFactionalSpawners(self);
		messageTo(self, "spawnNpcs", null, 0, false);
		System.out.println("---Spawner ("+self.toString()+") is initialized.");
		return SCRIPT_CONTINUE;
	}
}
