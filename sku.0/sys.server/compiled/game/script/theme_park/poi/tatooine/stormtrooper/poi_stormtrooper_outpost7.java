package script.theme_park.poi.tatooine.stormtrooper;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.ai_lib;
import script.library.factions;

public class poi_stormtrooper_outpost7 extends script.theme_park.poi.base
{
    public poi_stormtrooper_outpost7()
    {
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        String objective = poiGetObjective(self);
        if (objective.equals("troopers"))
        {
            obj_id outpost = poiCreateObject(self, "center", "object/installation/turret/turret_tower_med.iff", 0, 0);
            factions.setFaction(outpost, factions.FACTION_IMPERIAL);
            poiSetDestroyMessage(outpost, "outpostKilled");
            obj_id tr1 = poiCreateNpc("st1", "stormtrooper", 6, 6);
            obj_id tr2 = poiCreateNpc("st2", "stormtrooper", -10, 13);
            obj_id tr3 = poiCreateNpc("st3", "stormtrooper", -10, 11);
            obj_id tr4 = poiCreateNpc("st4", "stormtrooper", 5, 5);
            obj_id tr5 = poiCreateNpc("st5", "stormtrooper", 0, -5);
            obj_id tr6 = poiCreateNpc("st6", "stormtrooper", 10, 2);
            attachScript(tr1, "theme_park.poi.tatooine.behavior.stormtrooper_stationary_poi");
            attachScript(tr2, "theme_park.poi.tatooine.behavior.stormtrooper_stationary_poi");
            attachScript(tr3, "theme_park.poi.tatooine.behavior.poi_waiting");
            attachScript(tr4, "theme_park.poi.tatooine.behavior.poi_waiting");
            factions.setFaction(outpost, "Imperial");
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
