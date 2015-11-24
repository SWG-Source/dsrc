package script.theme_park.poi.tatooine.stormtrooper;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

public class poi_stormtrooper_camp extends script.theme_park.poi.base
{
    public poi_stormtrooper_camp()
    {
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        String objective = poiGetObjective(self);
        if (objective.equals("troopers"))
        {
            obj_id tr1 = poiCreateNpc("st1", "stormtrooper", 1, 0);
            poiSetDestroyMessage(tr1, "trooperKilled");
            obj_id tr2 = poiCreateNpc("st2", "stormtrooper", -1, 0);
            poiSetDestroyMessage(tr2, "trooperKilled");
            obj_id tr3 = poiCreateNpc("st3", "stormtrooper", 0, 1);
            poiSetDestroyMessage(tr3, "trooperKilled");
            attachScript(tr1, "theme_park.poi.tatooine.behavior.stormtrooper_stationary_poi");
            attachScript(tr2, "theme_park.poi.tatooine.behavior.stormtrooper_stationary_poi");
            attachScript(tr3, "theme_park.poi.tatooine.behavior.stormtrooper_stationary_poi");
        }
        return SCRIPT_CONTINUE;
    }
    public int trooperKilled(obj_id self, dictionary params) throws InterruptedException
    {
        int numKilled = getIntObjVar(self, "numTroopersKilled");
        numKilled++;
        if (numKilled > 1)
        {
            poiComplete(POI_SUCCESS);
        }
        setObjVar(self, "numTroopersKilled", numKilled);
        return SCRIPT_CONTINUE;
    }
}
