package script.e3demo;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.create;
import script.library.utils;
import script.library.space_crafting;
import script.library.space_combat;
import script.library.anims;
import script.library.space_utils;
import script.library.space_create;
import script.library.ship_ai;

public class e3_corvette extends script.base_script
{
    public e3_corvette()
    {
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        setObjVar(self, "intAlwaysDump", 1);
        messageTo(self, "delayedSpawn", null, 1, false);
        messageTo(self, "damageLoop", null, 1, false);
        attachScript(self, "conversation.e3_corvette");
        return SCRIPT_CONTINUE;
    }
    public int spawnAttackers(obj_id self, dictionary params) throws InterruptedException
    {
        String strSquad = "squad_tie_bomberwithguard_ace";
        Vector objMembers = space_create.createSquadHyperspace(self, strSquad, getTransform_o2p(self), rand(200, 300), null);
        for (int intI = 0; intI < objMembers.size(); intI++)
        {
            ship_ai.unitAddDamageTaken(((obj_id)objMembers.get(intI)), self, 100f);
        }
        strSquad = "squad_tie_bomberwithguard_ace";
        objMembers = space_create.createSquadHyperspace(self, strSquad, getTransform_o2p(self), rand(200, 300), null);
        for (int intI = 0; intI < objMembers.size(); intI++)
        {
            ship_ai.unitAddDamageTaken(((obj_id)objMembers.get(intI)), self, 100f);
        }
        return SCRIPT_CONTINUE;
    }
    public int damageLoop(obj_id self, dictionary params) throws InterruptedException
    {
        int intCount = rand(1, 3);
        for (int intI = 0; intI < intCount; intI++)
        {
            int intDamageType = rand(1, 4);
            int intDamageIntensity = rand(1, 2);
            dictionary dctParams = new dictionary();
            dctParams.put("intDamageType", intDamageType);
            dctParams.put("intDamageIntensity", intDamageIntensity);
            space_utils.notifyObject(self, "interiorDamageNotification", dctParams);
        }
        messageTo(self, "damageLoop", null, rand(5, 10), false);
        return SCRIPT_CONTINUE;
    }
    public int delayedSpawn(obj_id self, dictionary params) throws InterruptedException
    {
        transform[] trSpawnLocations = utils.getTransformArrayScriptVar(self, "locSpawners");
        obj_id[] objCells = utils.getObjIdArrayScriptVar(self, "locSpawnersCells");
        int intIndex = utils.getIntLocalVar(self, "intIndex");
        location locSpawnLocation = space_utils.getLocationFromTransform(trSpawnLocations[intIndex]);
        locSpawnLocation.cell = objCells[intIndex];
        String[] strMobs = 
        {
            "rebel_crewman"
        };
        int intRoll = rand(0, strMobs.length - 1);
        obj_id objMob = create.object(strMobs[intRoll], locSpawnLocation);
        LOG("create", "Made mob " + objMob + " at location " + locSpawnLocation);
        setState(objMob, STATE_SHIP_INTERIOR, false);
        setObjVar(objMob, "intDeleteOnReset", 1);
        intIndex = intIndex + 1;
        if (intIndex < trSpawnLocations.length)
        {
            utils.setLocalVar(self, "intIndex", intIndex);
            messageTo(self, "delayedSpawn", null, 1, false);
        }
        else 
        {
            utils.removeLocalVar(self, "intIndex");
        }
        return SCRIPT_CONTINUE;
    }
}
