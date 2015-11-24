package script.systems.npc_lair;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.create;
import script.library.ai_lib;
import script.library.player_structure;

public class uber_lair_interior extends script.base_script
{
    public uber_lair_interior()
    {
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        messageTo(self, "spawnStuff", null, 20, false);
        return SCRIPT_CONTINUE;
    }
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        return SCRIPT_CONTINUE;
    }
    public int spawnStuff(obj_id self, dictionary params) throws InterruptedException
    {
        final int MAX_SPAWNS_PER_FRAME = 3;
        location locTest = getLocation(self);
        String strArea = locTest.area;
        String strLocations = getStringObjVar(self, "uberlair.locations");
        String strSpawns = getStringObjVar(self, "uberlair.spawnType");
        String strLocationsFile = "datatables/uberlair/locations/" + strLocations + ".iff";
        String strSpawnFile = "datatables/uberlair/interior_spawns/" + strSpawns + ".iff";
        LOG("interior", "strSPawnFine is " + strSpawnFile);
        LOG("interior", "strLocations is " + strLocationsFile);
        float[] fltX = dataTableGetFloatColumn(strLocationsFile, "fltX");
        float[] fltY = dataTableGetFloatColumn(strLocationsFile, "fltX");
        float[] fltZ = dataTableGetFloatColumn(strLocationsFile, "fltX");
        float[] fltYaw = dataTableGetFloatColumn(strLocationsFile, "fltX");
        String[] strCell = dataTableGetStringColumn(strLocationsFile, "strCell");
        String[] strMobs = dataTableGetStringColumnNoDefaults(strSpawnFile, "strMobs");
        String[] strMobScripts = dataTableGetStringColumnNoDefaults(strSpawnFile, "strMobScripts");
        String[] strBoss = dataTableGetStringColumnNoDefaults(strSpawnFile, "strMobs");
        String[] strBossScripts = dataTableGetStringColumnNoDefaults(strSpawnFile, "strMobScripts");
        String[] strObjective = dataTableGetStringColumnNoDefaults(strSpawnFile, "strMobs");
        String[] strobjectiveScript = dataTableGetStringColumnNoDefaults(strSpawnFile, "strMobScripts");
        int intSpawnIndex = getIntObjVar(self, "intSpawnIndex");
        int intCounter = 0;
        for (int intI = 0; intI < fltX.length - 2; intI++)
        {
            if (intCounter > MAX_SPAWNS_PER_FRAME)
            {
                setObjVar(self, "intSpawnIndex", intI);
                messageTo(self, "spawnMobiles", null, 3, false);
                return SCRIPT_CONTINUE;
            }
            location locSpawnLocation = new location();
            locSpawnLocation.area = strArea;
            locSpawnLocation.cell = getCellId(self, strCell[intI]);
            locSpawnLocation.x = fltX[intI];
            locSpawnLocation.y = fltY[intI];
            locSpawnLocation.z = fltZ[intI];
            int intRoll = rand(0, strMobs.length - 1);
            String strMob = strMobs[intRoll];
            obj_id objMob = create.object(strMob, locSpawnLocation);
            if (objMob != null)
            {
                ai_lib.setDefaultCalmBehavior(objMob, ai_lib.BEHAVIOR_SENTINEL);
            }
            else 
            {
            }
        }
        location locSpawnLocation = new location();
        locSpawnLocation.area = strArea;
        locSpawnLocation.cell = getCellId(self, strCell[strCell.length - 2]);
        locSpawnLocation.x = fltX[strCell.length - 2];
        locSpawnLocation.y = fltY[strCell.length - 2];
        locSpawnLocation.z = fltZ[strCell.length - 2];
        create.object(strBoss[rand(0, strBoss.length - 1)], locSpawnLocation);
        locSpawnLocation = new location();
        locSpawnLocation.area = strArea;
        locSpawnLocation.cell = getCellId(self, strCell[strCell.length - 1]);
        locSpawnLocation.x = fltX[strCell.length - 1];
        locSpawnLocation.y = fltY[strCell.length - 1];
        locSpawnLocation.z = fltZ[strCell.length - 1];
        obj_id objObjective = create.object(strObjective[rand(0, strObjective.length - 1)], locSpawnLocation);
        attachScript(objObjective, "npc_lair.uber_lair_objective");
        return SCRIPT_CONTINUE;
    }
    public int OnDestroy(obj_id self) throws InterruptedException
    {
        boolean boolDestroy = true;
        obj_id[] objStuff = player_structure.getObjectsInBuilding(self);
        for (int intI = 0; intI < objStuff.length; intI++)
        {
            if (isPlayer(objStuff[intI]))
            {
                boolDestroy = false;
                expelFromBuilding(objStuff[intI]);
            }
            else if ((getGameObjectType(objStuff[intI]) == GOT_corpse))
            {
                boolDestroy = false;
                expelFromBuilding(objStuff[intI]);
            }
        }
        if (boolDestroy)
        {
            return SCRIPT_CONTINUE;
        }
        else 
        {
            messageTo(self, "reDestroySelf", null, 1, false);
            return SCRIPT_OVERRIDE;
        }
    }
    public int reDestroySelf(obj_id self, dictionary params) throws InterruptedException
    {
        destroyObject(self);
        return SCRIPT_CONTINUE;
    }
}
