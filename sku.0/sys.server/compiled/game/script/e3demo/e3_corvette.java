package script.e3demo;

import script.dictionary;
import script.library.*;
import script.location;
import script.obj_id;
import script.transform;

import java.util.Vector;

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
        Vector<obj_id> objMembers = space_create.createSquadHyperspace(self, strSquad, getTransform_o2p(self), rand(200, 300), null);
        for (obj_id objMember : objMembers) {
            ship_ai.unitAddDamageTaken(objMember, self, 100.0f);
        }
        strSquad = "squad_tie_bomberwithguard_ace";
        objMembers = space_create.createSquadHyperspace(self, strSquad, getTransform_o2p(self), rand(200, 300), null);
        for (obj_id objMember : objMembers) {
            ship_ai.unitAddDamageTaken(objMember, self, 100.0f);
        }
        return SCRIPT_CONTINUE;
    }
    public int damageLoop(obj_id self, dictionary params) throws InterruptedException
    {
        int intCount = rand(1, 3);
        for (int intI = 0; intI < intCount; intI++)
        {
            dictionary dctParams = new dictionary();
            dctParams.put("intDamageType", rand(1,4));
            dctParams.put("intDamageIntensity", rand(1,2));
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

        obj_id objMob = create.object("rebel_crewman", locSpawnLocation);
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
