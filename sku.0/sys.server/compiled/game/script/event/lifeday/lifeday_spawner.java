package script.event.lifeday;

import script.library.create;
import script.location;
import script.obj_id;

public class lifeday_spawner extends script.base_script
{
    public lifeday_spawner()
    {
    }
    private static final int[][] LOCS =
    {
            {-2580,77,-5519},
            {-2576,77,-5519},
            {-2576,77,-5508},
            {-2580,77,-5508},
            {-1088,6,-998},
            {-1088,6,-994},
            {-1100,6,-994},
            {-1100,6,-998},
            {-12,163,-3918},
            {-12,163,-3922},
            {-3,163,-3922},
            {-3,163,-3918}
    };
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        if (!hasObjVar(self, "event.lifeday.spawned"))
        {
            String myPlanet = getCurrentSceneName();
            int locStart;
            switch(myPlanet){
                case "dathomir":
                    locStart = 0;
                    break;
                case "endor":
                    locStart = 4;
                    break;
                case "yavin4":
                    locStart = 8;
                    break;
                default:
                    return SCRIPT_CONTINUE;
            }
            location elderSpawn = new location(LOCS[locStart][0], LOCS[locStart][1], LOCS[locStart][2], myPlanet);
            location female1Spawn = new location(LOCS[locStart][0], LOCS[locStart][1], LOCS[locStart][2], myPlanet);
            location female2Spawn = new location(LOCS[locStart][0], LOCS[locStart][1], LOCS[locStart][2], myPlanet);
            location male2Spawn = new location(LOCS[locStart][0], LOCS[locStart][1], LOCS[locStart][2], myPlanet);
            obj_id elder = create.object("object/tangible/spawning/static_npc/wookiee_lifeday_elder.iff", elderSpawn);
            obj_id female1 = create.object("object/tangible/spawning/static_npc/wookiee_lifeday_female1.iff", female1Spawn);
            obj_id female2 = create.object("object/tangible/spawning/static_npc/wookiee_lifeday_female2.iff", female2Spawn);
            obj_id male2 = create.object("object/tangible/spawning/static_npc/wookiee_lifeday_male2.iff", male2Spawn);
            setObjVar(self, "event.lifeday.elder", elder);
            setObjVar(self, "event.lifeday.female1", female1);
            setObjVar(self, "event.lifeday.female2", female2);
            setObjVar(self, "event.lifeday.male2", male2);
            setObjVar(self, "event.lifeday.spawned", 1);
        }
        return SCRIPT_CONTINUE;
    }
}
