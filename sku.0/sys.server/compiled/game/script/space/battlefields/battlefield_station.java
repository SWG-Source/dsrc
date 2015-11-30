package script.space.battlefields;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.group;
import script.library.ship_ai;
import script.library.objvar_mangle;
import script.library.space_utils;
import script.library.space_create;
import script.library.load_test;
import script.library.prose;
import script.library.ship_ai;
import script.library.space_combat;
import script.library.space_transition;
import script.library.space_battlefield;
import script.library.utils;
import java.lang.Math;

public class battlefield_station extends script.base_script
{
    public battlefield_station()
    {
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        setShipFaction(self, "generic");
        setObjVar(self, "intActivationPhase", 2);
        setObjVar(self, "strMyName", "rebel_station");
        setObjVar(self, "intDockable", 1);
        setObjVar(self, "strTargetName", "star_destroyer");
        String[] strPaths = 
        {
            "rebelTest1",
            "rebelTest2",
            "rebelTest3",
            "rebelTest4",
            "rebelTest5"
        };
        int[] intPathCounts = 
        {
            7,
            7,
            7,
            7,
            7
        };
        setObjVar(self, "strCapitalShipType", "capital_ship_spacestation");
        setObjVar(self, "strPaths", strPaths);
        String strShipType = "spacestation_rebel";
        String strPilot = "capital_ship_tier5";
        int intXP = 10000;
        setObjVar(self, "ship.shipName", strShipType);
        setObjVar(self, "ship.pilotType", strPilot);
        setObjVar(self, "xp.intXP", intXP);
        String strFaction = "rebel";
        setObjVar(self, "ship.space_faction", strFaction);
        setShipFaction(self, strFaction);
        int intMinCredits = 10000;
        int intMaxCredits = 20000;
        setObjVar(self, "loot.intCredits", rand(intMinCredits, intMaxCredits));
        int intRolls = 10;
        float fltItemChance = .25f;
        int intNumItems = rand(1, 5);
        setObjVar(self, "loot.intNumItems", intNumItems);
        setObjVar(self, "loot.strLootTable", "rebel_tier1");
        setObjVar(self, "intPathCounts", intPathCounts);
        String strObjVars = "fltMaxSpawnDistance|2|1850.000000|fltMaxSpawnTime|2|600.000000|fltMinSpawnDistance|2|1000.000000|fltMinSpawnTime|2|180.000000|intSpawnCount|0|20|strDefaultBehavior|4|specialty|strPatrolPoints_mangled.count|0|7|strPatrolPoints_mangled.segment.0|5|rebel_invasion_1_1:rebel_invasion_1_2:rebel_invasion_1_3:rebel_invasion_1_4:rebel_invasion_1_5:rebel_invasion_1_6:rebel_invasion_1_7:|strSpawnerName|4|rebel_invasion_1|strSpawnerType|4|generic|strSpawns_mangled.count|0|5|strSpawns_mangled.segment.0|5|squad_rebel_station_1:squad_rebel_station_2:squad_rebel_station_3:squad_rebel_station_4:squad_rebel_station_5:|$|";
        setPackedObjvars(self, strObjVars);
        string_id strSpam = new string_id("space/space_mobile_type", "spacestation_rebel");
        setName(self, strSpam);
        LOG("space", "setting name to " + strSpam);
        space_combat.setupCapitalShipFromTurretDefinition(self, "rebel_spacestation");
        messageTo(self, "setupFaction", null, 1, false);
        return SCRIPT_CONTINUE;
    }
    public int setupFaction(obj_id self, dictionary params) throws InterruptedException
    {
        int intFaction = (370444368);
        int[] intAlliedFactions = 
        {
            (370444368)
        };
        int[] intEnemyFactions = 
        {
            (-615855020)
        };
        LOG("space", "setting faction ");
        shipSetSpaceFaction(self, intFaction);
        shipSetSpaceFactionAllies(self, intAlliedFactions);
        shipSetSpaceFactionEnemies(self, intEnemyFactions);
        return SCRIPT_CONTINUE;
    }
    public int OnDestroy(obj_id self) throws InterruptedException
    {
        LOG("space", "KABOOM SpACE STACEION");
        obj_id objManager = space_battlefield.getManagerObject();
        if (!hasObjVar(self, "intCleaningUp"))
        {
            if (!utils.hasScriptVar(objManager, "intResetting"))
            {
                utils.setScriptVar(objManager, "intResetting", 1);
                space_battlefield.battlefieldCompleted(objManager, space_battlefield.STATE_IMPERIAL);
                messageTo(objManager, "resetBattlefield", null, space_battlefield.RESET_TIME, false);
                LOG("space", "I exploded");
                CustomerServiceLog("battlefield", "Space Station destroyed in Battlefield Zone, flipping kessel to Imperial");
            }
            else 
            {
                CustomerServiceLog("battlefield", "Space Station destroyed in Battlefield Zone, but no flip because the Empire already won");
            }
        }
        detachScript(self, "space.battlefields.battlefield_spawner");
        detachScript(self, "space.content_tools.spawner");
        obj_id objEgg = createObject("object/tangible/space/content_infrastructure/generic_egg_small.iff", getTransform_o2p(self), null);
        String strObjVars = getPackedObjvars(self);
        String strScripts = utils.getPackedScripts(self);
        String strTemplate = getTemplateName(self);
        utils.setLocalVar(objEgg, "strObjVars", strObjVars);
        utils.setLocalVar(objEgg, "strScripts", strScripts);
        utils.setLocalVar(objEgg, "strTemplate", strTemplate);
        setObjVar(objEgg, "intStationRespawner", 1);
        setObjVar(objEgg, "intNoDump", 1);
        LOG("space", "objEgg is " + objEgg);
        return SCRIPT_CONTINUE;
    }
    public int objectDocked(obj_id self, dictionary params) throws InterruptedException
    {
        LOG("space", "DOCKING");
        obj_id objShip = params.getObjId("objShip");
        obj_id objContentManager = space_battlefield.getManagerObject();
        String strTemplate = getTemplateName(self);
        int intIndex = strTemplate.indexOf("rebel");
        String strObjVarName = "";
        if (intIndex > -1)
        {
            strObjVarName = "intRebelEscortsCompleted";
        }
        else 
        {
            strObjVarName = "intImperialEscortsCompleted";
        }
        LOG("space", "NAME IS " + strObjVarName);
        LOG("space", "SHIP IS " + objShip);
        int intCount = getIntObjVar(objContentManager, strObjVarName);
        intCount = intCount + 1;
        setObjVar(objContentManager, strObjVarName, intCount);
        messageTo(self, "undockAndLeave", params, 5, false);
        return SCRIPT_CONTINUE;
    }
    public int undockAndLeave(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id objShip = params.getObjId("objShip");
        ship_ai.unitUnDock(objShip);
        transform trTest = space_utils.getRandomPositionInSphere(getTransform_o2p(self), 600, 900, false);
        location locTest = space_utils.getLocationFromTransform(trTest);
        addLocationTarget3d(objShip, "spawnerArrival", locTest, 16);
        removeObjVar(objShip, "objDockingStation");
        ship_ai.spaceMoveTo(objShip, trTest);
        return SCRIPT_CONTINUE;
    }
}
