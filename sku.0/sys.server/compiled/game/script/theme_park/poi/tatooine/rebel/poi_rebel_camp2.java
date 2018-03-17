package script.theme_park.poi.tatooine.rebel;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

public class poi_rebel_camp2 extends script.theme_park.poi.base
{
    public poi_rebel_camp2()
    {
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        String objective = poiGetObjective(self);
        if (objective.equals("rebel"))
        {
            obj_id tr1 = poiCreateNpc("st1", "rebel", 1, 0);
            obj_id tr2 = poiCreateNpc("st2", "rebel", -1, 0);
            obj_id tr3 = poiCreateNpc("st3", "rebel", 0, 1);
            obj_id tr4 = poiCreateNpc("st4", "rebel", 0, -1);
            attachScript(tr1, "theme_park.poi.tatooine.behavior.stormtrooper_stationary_poi");
            attachScript(tr2, "theme_park.poi.tatooine.behavior.stormtrooper_stationary_poi");
            attachScript(tr3, "theme_park.poi.tatooine.behavior.stormtrooper_stationary_poi");
            attachScript(tr4, "theme_park.poi.tatooine.behavior.stormtrooper_stationary_poi");
            return SCRIPT_CONTINUE;
        }
        return SCRIPT_CONTINUE;
    }
}
