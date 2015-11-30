package script.event.auto_invasion;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.create;
import script.library.locations;

public class spawner_spawner extends script.base_script
{
    public spawner_spawner()
    {
    }
    public static final int[] LOCS = 
    {
        -4915,
        6,
        4093,
        -1376,
        12,
        -3576,
        -51,
        28,
        -4734,
        -977,
        73,
        1554,
        3668,
        96,
        -6446,
        -2197,
        20,
        2302
    };
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        if (!hasObjVar(self, "auto_invasion.spawned"))
        {
            location here = getLocation(self);
            String myPlanet = getCurrentSceneName();
            int locStart = 999;
            String myRegion = locations.getGuardSpawnerRegionName(here);
            if (myRegion.equals("@naboo_region_names:theed"))
            {
                locStart = 0;
            }
            if (myRegion.equals("@tatooine_region_names:bestine"))
            {
                locStart = 3;
            }
            if (myRegion.equals("@corellia_region_names:coronet"))
            {
                locStart = 6;
            }
            if (myRegion.equals("@endor_region_names:endor_smuggler_outpost"))
            {
                locStart = 9;
            }
            if (myRegion.equals("@rori_region_names:sdungeon_rebel_outpost"))
            {
                locStart = 12;
            }
            if (myRegion.equals("@talus_region_names:talus_imperial_outpost"))
            {
                locStart = 15;
            }
            if (locStart > 15)
            {
                return SCRIPT_CONTINUE;
            }
            location spawnerLocation = new location(LOCS[locStart], LOCS[locStart + 1], LOCS[locStart + 2], myPlanet);
            obj_id spawner = create.object("object/tangible/spawning/auto_invasion_spawner.iff", spawnerLocation);
            setObjVar(self, "auto_invasion.spawned", 1);
            setObjVar(self, "auto_invasion.spawner", spawner);
        }
        return SCRIPT_CONTINUE;
    }
}
