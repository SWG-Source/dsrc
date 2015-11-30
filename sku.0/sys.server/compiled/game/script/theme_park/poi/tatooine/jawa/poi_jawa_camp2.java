package script.theme_park.poi.tatooine.jawa;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.ai_lib;

public class poi_jawa_camp2 extends script.theme_park.poi.base
{
    public poi_jawa_camp2()
    {
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        String objective = poiGetObjective(self);
        if (objective.equals("jawas"))
        {
            obj_id jawa1 = poiCreateNpc("jawa", -1, -2);
            obj_id jawa2 = poiCreateNpc("jawa", 1, 1);
            obj_id jawa3 = poiCreateNpc("jawa", 1, 3);
            attachScript(jawa2, "theme_park.poi.tatooine.behavior.tusken_stationary_poi");
            attachScript(jawa3, "theme_park.poi.tatooine.behavior.tusken_stationary_poi");
            String[] patrolPoints = new String[4];
            patrolPoints[0] = "10, 10";
            patrolPoints[1] = "10, -10.3";
            patrolPoints[2] = "-10, -10";
            patrolPoints[3] = "-10, 10";
            ai_lib.setPatrolPath(jawa1, patrolPoints);
            location here = getLocation(self);
            obj_id fire = poiCreateObject(self, "campfire", "object/static/particle/particle_campfire_style_1.iff", 1, 2);
            obj_id center = poiCreateObject(self, "center", "object/tangible/camp/campfire_logs_burnt.iff", 1, 2);
            return SCRIPT_CONTINUE;
        }
        return SCRIPT_CONTINUE;
    }
}
