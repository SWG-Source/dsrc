package script.event.gcwlaunch;

import script.library.create;
import script.library.locations;
import script.location;
import script.obj_id;

public class gcwlaunch_spawner extends script.base_script
{
    public gcwlaunch_spawner()
    {
    }
    public static final int[] LOCS = 
    {
        110,
        52,
        -5342,
        -150,
        28,
        -4720,
        4812,
        4,
        -4705,
        -1280,
        0,
        -3590,
        6902,
        330,
        -5550,
        -4897,
        6,
        4106
    };
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        if (!hasObjVar(self, "event.gcwlaunch.spawner"))
        {
            int locStart;
            switch (locations.getGuardSpawnerRegionName(getLocation(self))) {
                case "@tatooine_region_names:anchorhead":
                    locStart = 0;
                    break;
                case "@corellia_region_names:coronet":
                    locStart = 3;
                    break;
                case "@naboo_region_names:moenia":
                    locStart = 6;
                    break;
                case "@tatooine_region_names:bestine":
                    locStart = 9;
                    break;
                case "@corellia_region_names:bela_vistal":
                    locStart = 12;
                    break;
                case "@naboo_region_names:theed":
                    locStart = 15;
                    break;
                default:
                    return SCRIPT_CONTINUE;
            }
            obj_id spawner = create.object("object/tangible/poi/base/poi_base.iff", new location(LOCS[locStart], LOCS[locStart + 1], LOCS[locStart + 2], getCurrentSceneName()));
            setObjVar(self, "event.gcwlaunch.spawner", spawner);
            attachScript(spawner, "event.gcwlaunch.gcwlaunch");
        }
        return SCRIPT_CONTINUE;
    }
}
