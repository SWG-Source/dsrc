package script.theme_park.poi.tatooine.jawa;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.ai_lib;

public class poi_jawa_droid extends script.theme_park.poi.base
{
    public poi_jawa_droid()
    {
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        String objective = poiGetObjective(self);
        if (objective.equals("jawas"))
        {
            obj_id jawa1 = poiCreateNpc("jawa", 1, 1);
            obj_id jawa2 = poiCreateNpc("jawa", 1, 2);
            obj_id droid = poiCreateNpc("droid", 1, 3);
            attachScript(jawa1, "theme_park.poi.tatooine.behavior.tusken_stationary_poi");
            attachScript(jawa2, "theme_park.poi.tatooine.behavior.tusken_stationary_poi");
            attachScript(droid, "theme_park.poi.tatooine.behavior.tusken_stationary_poi");
            location here = getLocation(self);
            faceTo(jawa1, droid);
            faceTo(jawa2, droid);
            dictionary params = new dictionary();
            params.put("droid", droid);
            params.put("jawa1", jawa1);
            params.put("jawa2", jawa2);
            messageTo(self, "moveDroid", params, 10, true);
            return SCRIPT_CONTINUE;
        }
        return SCRIPT_CONTINUE;
    }
    public int moveDroid(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id droid = params.getObjId("droid");
        location here = getLocation(droid);
        int XorZ = rand(1, 2);
        if (XorZ == 1)
        {
            here.x = here.x + (rand(-10, 10));
        }
        else 
        {
            here.z = here.z + (rand(-10, 10));
        }
        pathTo(droid, here);
        messageTo(self, "moveJawas", params, 5, true);
        return SCRIPT_CONTINUE;
    }
    public int moveJawas(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id jawa1 = params.getObjId("jawa1");
        obj_id jawa2 = params.getObjId("jawa2");
        obj_id droid = params.getObjId("droid");
        location droidLoc = getLocation(droid);
        droidLoc.x = droidLoc.x + 1;
        pathTo(jawa1, droidLoc);
        droidLoc.x = droidLoc.x - 2;
        pathTo(jawa2, droidLoc);
        messageTo(self, "moveDroid", params, 13, true);
        return SCRIPT_CONTINUE;
    }
}
