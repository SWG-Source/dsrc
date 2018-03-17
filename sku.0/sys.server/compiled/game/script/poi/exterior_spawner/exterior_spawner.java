package script.poi.exterior_spawner;


import script.obj_id;

public class exterior_spawner extends script.poi.interior_spawner.interior_spawner
{
	public exterior_spawner()
	{
	}
	public int OnInitialize(obj_id self) throws InterruptedException
	{
		checkFactionalSpawners(self);
		messageTo(self, "spawnNpcs", null, 0, false);
		return SCRIPT_CONTINUE;
	}
}
