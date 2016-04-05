package script.event.auto_invasion;

import script.library.create;
import script.library.locations;
import script.location;
import script.obj_id;

public class spawner_spawner extends script.base_script
{
    public spawner_spawner()
    {
    }
    public static final int[] LOCS = 
    {
        -4915,6,4093,
        -1376,12,-3576,
        -51,28,-4734,
        -977,73,1554,
        3668,96,-6446,
        -2197,20,2302
    };
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        if (!hasObjVar(self, "auto_invasion.spawned"))
        {
            int locStart = 999;
            String myRegion = locations.getGuardSpawnerRegionName(getLocation(self));
            switch (myRegion) {
                case "@naboo_region_names:theed":
                    locStart = 0;
                    break;
                case "@tatooine_region_names:bestine":
                    locStart = 3;
                    break;
                case "@corellia_region_names:coronet":
                    locStart = 6;
                    break;
                case "@endor_region_names:endor_smuggler_outpost":
                    locStart = 9;
                    break;
                case "@rori_region_names:sdungeon_rebel_outpost":
                    locStart = 12;
                    break;
                case "@talus_region_names:talus_imperial_outpost":
                    locStart = 15;
                    break;
            }
            if (locStart > 15)
            {
                return SCRIPT_CONTINUE;
            }
            obj_id spawner = create.object("object/tangible/spawning/auto_invasion_spawner.iff", new location(LOCS[locStart], LOCS[locStart + 1], LOCS[locStart + 2], getCurrentSceneName()));
            setObjVar(self, "auto_invasion.spawned", 1);
            setObjVar(self, "auto_invasion.spawner", spawner);
        }
        return SCRIPT_CONTINUE;
    }
}
