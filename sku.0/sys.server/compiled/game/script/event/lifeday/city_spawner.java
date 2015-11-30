package script.event.lifeday;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.create;

public class city_spawner extends script.base_script
{
    public city_spawner()
    {
    }
    public static final int[] LOCS = 
    {
        131,
        52,
        -5384,
        -5545,
        23,
        -6177,
        -5557,
        -150,
        0
    };
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        if (!hasObjVar(self, "event.lifeday.spawned"))
        {
            String myPlanet = getCurrentSceneName();
            if (!myPlanet.equals("tatooine") && !myPlanet.equals("corellia") && !myPlanet.equals("naboo"))
            {
                return SCRIPT_CONTINUE;
            }
            int locStart = 0;
            if (myPlanet.equals("corellia"))
            {
                locStart = 3;
            }
            if (myPlanet.equals("naboo"))
            {
                locStart = 6;
            }
            location male1Spawn = new location(LOCS[locStart], LOCS[locStart + 1], LOCS[locStart + 2], myPlanet);
            obj_id male1 = create.object("object/tangible/spawning/static_npc/wookiee_lifeday_male1.iff", male1Spawn);
            setObjVar(self, "event.lifeday.spawned", 1);
            setObjVar(self, "event.lifeday.male1", male1);
        }
        return SCRIPT_CONTINUE;
    }
}
