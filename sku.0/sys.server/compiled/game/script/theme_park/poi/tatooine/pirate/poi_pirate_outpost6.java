package script.theme_park.poi.tatooine.pirate;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.ai_lib;
import script.library.factions;

public class poi_pirate_outpost6 extends script.theme_park.poi.base
{
    public poi_pirate_outpost6()
    {
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        String objective = poiGetObjective(self);
        if (objective.equals("pirates"))
        {
            obj_id outpost = poiCreateObject(self, "center", "object/installation/turret/turret_tower_large.iff", 0, 0);
            setPirateFaction(outpost);
            poiSetDestroyMessage(outpost, "outpostKilled");
            obj_id tr1 = poiCreateNpc("st1", "pirate", 6, 6);
            setPirateFaction(tr1);
            obj_id tr2 = poiCreateNpc("st2", "pirate", -10, 13);
            setPirateFaction(tr2);
            obj_id tr3 = poiCreateNpc("st3", "pirate", -10, 11);
            setPirateFaction(tr3);
            obj_id tr4 = poiCreateNpc("st4", "pirate", 5, 5);
            setPirateFaction(tr4);
            obj_id tr5 = poiCreateNpc("st5", "pirate", 0, -5);
            setPirateFaction(tr5);
            obj_id tr6 = poiCreateNpc("st6", "pirate", 10, 2);
            setPirateFaction(tr6);
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
    public void setPirateFaction(obj_id target) throws InterruptedException
    {
        factions.setFaction(target, "pirate");
    }
    public int outpostKilled(obj_id self, dictionary params) throws InterruptedException
    {
        poiComplete(POI_SUCCESS);
        return SCRIPT_CONTINUE;
    }
}
