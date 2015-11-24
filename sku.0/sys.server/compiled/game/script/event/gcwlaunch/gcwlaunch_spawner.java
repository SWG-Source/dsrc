package script.event.gcwlaunch;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.create;
import script.library.locations;

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
            location here = getLocation(self);
            String myPlanet = getCurrentSceneName();
            int locStart = 999;
            String myRegion = locations.getGuardSpawnerRegionName(here);
            if (myRegion.equals("@tatooine_region_names:anchorhead"))
            {
                locStart = 0;
            }
            if (myRegion.equals("@corellia_region_names:coronet"))
            {
                locStart = 3;
            }
            if (myRegion.equals("@naboo_region_names:moenia"))
            {
                locStart = 6;
            }
            if (myRegion.equals("@tatooine_region_names:bestine"))
            {
                locStart = 9;
            }
            if (myRegion.equals("@corellia_region_names:bela_vistal"))
            {
                locStart = 12;
            }
            if (myRegion.equals("@naboo_region_names:theed"))
            {
                locStart = 15;
            }
            if (locStart > 15)
            {
                return SCRIPT_CONTINUE;
            }
            location spawnerLocation = new location(LOCS[locStart], LOCS[locStart + 1], LOCS[locStart + 2], myPlanet);
            obj_id spawner = create.object("object/tangible/poi/base/poi_base.iff", spawnerLocation);
            setObjVar(self, "event.gcwlaunch.spawner", spawner);
            attachScript(spawner, "event.gcwlaunch.gcwlaunch");
        }
        return SCRIPT_CONTINUE;
    }
}
