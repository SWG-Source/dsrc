package script.event.lifeday;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.create;

public class lifeday_spawner extends script.base_script
{
    public lifeday_spawner()
    {
    }
    public static final int[] LOCS = 
    {
        -2580,
        77,
        -5519,
        -2576,
        77,
        -5519,
        -2576,
        77,
        -5508,
        -2580,
        77,
        -5508,
        -1088,
        6,
        -998,
        -1088,
        6,
        -994,
        -1100,
        6,
        -994,
        -1100,
        6,
        -998,
        -12,
        163,
        -3918,
        -12,
        163,
        -3922,
        -3,
        163,
        -3922,
        -3,
        163,
        -3918
    };
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        if (!hasObjVar(self, "event.lifeday.spawned"))
        {
            String myPlanet = getCurrentSceneName();
            if (!myPlanet.equals("dathomir") && !myPlanet.equals("endor") && !myPlanet.equals("yavin4"))
            {
                return SCRIPT_CONTINUE;
            }
            int locStart = 0;
            if (myPlanet.equals("endor"))
            {
                locStart = 12;
            }
            if (myPlanet.equals("yavin4"))
            {
                locStart = 24;
            }
            location elderSpawn = new location(LOCS[locStart], LOCS[locStart + 1], LOCS[locStart + 2], myPlanet);
            location female1Spawn = new location(LOCS[locStart + 3], LOCS[locStart + 4], LOCS[locStart + 5], myPlanet);
            location female2Spawn = new location(LOCS[locStart + 6], LOCS[locStart + 7], LOCS[locStart + 8], myPlanet);
            location male2Spawn = new location(LOCS[locStart + 9], LOCS[locStart + 10], LOCS[locStart + 11], myPlanet);
            obj_id elder = create.object("object/tangible/spawning/static_npc/wookiee_lifeday_elder.iff", elderSpawn);
            obj_id female1 = create.object("object/tangible/spawning/static_npc/wookiee_lifeday_female1.iff", female1Spawn);
            obj_id female2 = create.object("object/tangible/spawning/static_npc/wookiee_lifeday_female2.iff", female2Spawn);
            obj_id male2 = create.object("object/tangible/spawning/static_npc/wookiee_lifeday_male2.iff", male2Spawn);
            setObjVar(self, "event.lifeday.spawned", 1);
            setObjVar(self, "event.lifeday.elder", elder);
            setObjVar(self, "event.lifeday.female1", female1);
            setObjVar(self, "event.lifeday.female2", female2);
            setObjVar(self, "event.lifeday.male2", male2);
        }
        return SCRIPT_CONTINUE;
    }
}
