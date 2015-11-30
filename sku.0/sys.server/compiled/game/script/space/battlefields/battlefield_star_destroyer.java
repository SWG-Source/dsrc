package script.space.battlefields;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.space_utils;
import script.library.objvar_mangle;
import script.library.space_quest;
import script.library.space_battlefield;
import script.library.space_create;
import script.library.ship_ai;
import script.library.utils;
import java.lang.Math;
import script.library.hue;

public class battlefield_star_destroyer extends script.base_script
{
    public battlefield_star_destroyer()
    {
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        final String[] strPaths = 
        {
            "imperialTest1",
            "imperialTest2",
            "imperialTest3",
            "imperialTest4",
            "imperialTest5"
        };
        final int[] intPathCounts = 
        {
            7,
            7,
            7,
            7,
            7
        };
        String strObjVars = "fltMaxSpawnDistance|2|120.000000|fltMaxSpawnTime|2|600.000000|fltMinSpawnDistance|2|90.000000|fltMinSpawnTime|2|180.000000|intActivationPhase|0|2|intSpawnCount|0|20|strDefaultBehavior|4|specialty|strPatrolPoints_mangled.count|0|7|strPatrolPoints_mangled.segment.0|5|imperial_invasion_1_1:imperial_invasion_1_2:imperial_invasion_1_3:imperial_invasion_1_4:imperial_invasion_1_5:imperial_invasion_1_6:imperial_invasion_1_7:|strSpawnerName|4|imperial_invasion_1|strSpawnerType|4|generic|strSpawns_mangled.count|0|5|strSpawns_mangled.segment.0|5|squad_stardestroyer_1:squad_stardestroyer_2:squad_stardestroyer_3:squad_stardestroyer_4:squad_stardestroyer_5:|$|";
        setPackedObjvars(self, strObjVars);
        String strPilot = "capital_ship_tier5";
        
        setObjVar(self, "ship.pilotType", strPilot);
        setObjVar(self, "strMyName", "star_destroyer");
        setObjVar(self, "strTargetName", "rebel_station");
        setObjVar(self, "strPaths", strPaths);
        setObjVar(self, "intPathCounts", intPathCounts);
        int intMinCredits = 10000;
        int intMaxCredits = 20000;
        setObjVar(self, "loot.intCredits", rand(intMinCredits, intMaxCredits));
        int intXP = 10000;
        setObjVar(self, "xp.intXP", intXP);
        setObjVar(self, "intActivationPhase", 2);
        setObjVar(self, "intNoCleanup", 1);
        String strFaction = "imperial";
        setObjVar(self, "ship.space_faction", strFaction);
        setShipFaction(self, strFaction);
        obj_id objManager = space_battlefield.getManagerObject();
        int intSpawnsAllowed = getIntObjVar(objManager, "intImperialShipCount");
        if (intSpawnsAllowed < 10)
        {
            intSpawnsAllowed = 10;
        }
        attachScript(self, "space.battlefields.battlefield_spawner");
        attachScript(self, "space.content_tools.spawner");
        messageTo(self, "startSpawning", null, 3, false);
        return SCRIPT_CONTINUE;
    }
    public int spawnCountReached(obj_id self, dictionary params) throws InterruptedException
    {
        return SCRIPT_CONTINUE;
    }
    public int OnDestroy(obj_id self) throws InterruptedException
    {
        obj_id objManager = space_battlefield.getManagerObject();
        LOG("space", "KABOOM STAR DESTROYER");
        if (hasObjVar(self, "intCleaningUp"))
        {
            return SCRIPT_CONTINUE;
        }
        else 
        {
            space_battlefield.battlefieldCompleted(objManager, space_battlefield.STATE_REBEL);
            if (!utils.hasScriptVar(objManager, "intResetting"))
            {
                utils.setScriptVar(objManager, "intResetting", 1);
                messageTo(objManager, "resetBattlefield", null, space_battlefield.RESET_TIME, false);
                CustomerServiceLog("battlefield", "STAR DESTROYER  destroyed in Battlefield Zone, flipping kessel to rebel");
            }
            else 
            {
                CustomerServiceLog("battlefield", "STAR DESTROYER  destroyed in Battlefield Zone, Not flipping because the imperials won first.");
            }
        }
        return SCRIPT_CONTINUE;
    }
}
