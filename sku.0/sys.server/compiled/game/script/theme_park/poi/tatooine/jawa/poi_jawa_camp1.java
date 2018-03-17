package script.theme_park.poi.tatooine.jawa;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

public class poi_jawa_camp1 extends script.theme_park.poi.base
{
    public poi_jawa_camp1()
    {
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        String objective = poiGetObjective(self);
        if (objective.equals("jawas"))
        {
            obj_id jawa1 = poiCreateNpc("jawa", -1, -3);
            obj_id jawa2 = poiCreateNpc("jawa", -1, 3);
            obj_id jawa3 = poiCreateNpc("jawa", 2, 2);
            attachScript(jawa1, "theme_park.poi.tatooine.behavior.tusken_stationary_poi");
            attachScript(jawa2, "theme_park.poi.tatooine.behavior.tusken_stationary_poi");
            attachScript(jawa3, "theme_park.poi.tatooine.behavior.tusken_stationary_poi");
            location here = getLocation(self);
            obj_id fire = poiCreateObject(self, "campfire", "object/static/particle/particle_campfire_style_1.iff", 1, 2);
            obj_id center = poiCreateObject(self, "center", "object/tangible/camp/campfire_logs_burnt.iff", 1, 2);
            return SCRIPT_CONTINUE;
        }
        return SCRIPT_CONTINUE;
    }
}
