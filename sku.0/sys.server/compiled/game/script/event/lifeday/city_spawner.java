package script.event.lifeday;

import script.library.create;
import script.location;
import script.obj_id;

public class city_spawner extends script.base_script
{
    public city_spawner()
    {
    }
    private static final int[][] startLocations =
    {
        {131,52,-5384},
        {-5545,23,-6177},
        {-5557,-150,0}
    };
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        if (!hasObjVar(self, "event.lifeday.spawned"))
        {
            String myPlanet = getCurrentSceneName();
            int locStart;
            switch(myPlanet){
                case "tatooine":
                    locStart = 0;
                    break;
                case "corellia":
                    locStart = 1;
                    break;
                case "naboo":
                    locStart = 2;
                    break;
                default:
                    return SCRIPT_CONTINUE;
            }
            location male1Spawn = new location(startLocations[locStart][0], startLocations[locStart][1], startLocations[locStart][2], myPlanet);
            obj_id male1 = create.object("object/tangible/spawning/static_npc/wookiee_lifeday_male1.iff", male1Spawn);
            setObjVar(self, "event.lifeday.spawned", 1);
            setObjVar(self, "event.lifeday.male1", male1);
        }
        return SCRIPT_CONTINUE;
    }
}
