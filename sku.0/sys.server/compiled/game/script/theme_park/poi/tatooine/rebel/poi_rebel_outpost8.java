package script.theme_park.poi.tatooine.rebel;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.ai_lib;
import script.library.factions;

public class poi_rebel_outpost8 extends script.theme_park.poi.base
{
    public poi_rebel_outpost8()
    {
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        String objective = poiGetObjective(self);
        if (objective.equals("rebels"))
        {
            obj_id outpost = poiCreateObject(self, "center", "object/installation/turret/turret_tower_sm.iff", 0, 0);
            factions.setFaction(outpost, factions.FACTION_REBEL);
            poiSetDestroyMessage(outpost, "outpostKilled");
            obj_id tr1 = poiCreateNpc("st1", "rebel", 6, 6);
            obj_id tr2 = poiCreateNpc("st2", "rebel", -10, 13);
            obj_id tr3 = poiCreateNpc("st3", "rebel", -10, 11);
            obj_id tr4 = poiCreateNpc("st4", "rebel", 5, 5);
            obj_id tr5 = poiCreateNpc("st5", "rebel", 0, -5);
            obj_id tr6 = poiCreateNpc("st6", "rebel", 10, 2);
            factions.setFaction(outpost, "Rebel");
            attachScript(tr1, "theme_park.poi.tatooine.behavior.stormtrooper_stationary_poi");
            attachScript(tr2, "theme_park.poi.tatooine.behavior.stormtrooper_stationary_poi");
            attachScript(tr3, "theme_park.poi.tatooine.behavior.poi_waiting");
            attachScript(tr4, "theme_park.poi.tatooine.behavior.poi_waiting");
            String[] patrolPoints = new String[4];
            patrolPoints[0] = "-20, 20";
            patrolPoints[1] = "-20, -20.3";
            patrolPoints[2] = "20, 20";
            patrolPoints[3] = "20, -20";
            ai_lib.setPatrolPath(tr5, patrolPoints);
            String[] patrolPoints2 = new String[5];
            patrolPoints2[0] = "15, 15";
            patrolPoints2[1] = "15, -15.3";
            patrolPoints2[2] = "-15, -15";
            patrolPoints2[3] = "-15, 15";
            patrolPoints2[4] = "-20, 20";
            ai_lib.setPatrolPath(tr6, patrolPoints2);
        }
        return SCRIPT_CONTINUE;
    }
    public int outpostKilled(obj_id self, dictionary params) throws InterruptedException
    {
        poiComplete(POI_SUCCESS);
        return SCRIPT_CONTINUE;
    }
}
