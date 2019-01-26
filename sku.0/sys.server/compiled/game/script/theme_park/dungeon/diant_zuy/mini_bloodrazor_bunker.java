package script.theme_park.dungeon.diant_zuy;

import script.dictionary;
import script.library.create;
import script.location;
import script.obj_id;

public class mini_bloodrazor_bunker extends script.base_script
{
    public mini_bloodrazor_bunker()
    {
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        int sekritCode = rand(1000, 9999);
        setObjVar(self, "diant.sekritCode", sekritCode);
        setObjVar(self, "diant.generator", 1);
        setObjVar(self, "diant.mainframe", 0);
        setObjVar(self, "diant.security", 0);
        setObjVar(self, "diant.remote", 0);
        setObjVar(self, "diant.doorOpened", 0);
        setObjVar(self, "diant.isExploding", 0);
        setObjVar(self, "diant.explosionIntensity", 1.0f);
        setObjVar(self, "diant.intensifying", 0);
        spawnEveryone(self);
        obj_id lockedRoom = getCellId(self, "pantry");
        permissionsMakePrivate(lockedRoom);
        return SCRIPT_CONTINUE;
    }
    public void spawnEveryone(obj_id self) throws InterruptedException
    {
        debugSpeakMsg(self, "Spawning Everyone");
        if (!hasScript(self, "theme_park.dungeon.generic_spawner"))
        {
            
        }
        
        {
            attachScript(self, "theme_park.dungeon.generic_spawner");
        }
        if (!hasObjVar(self, "spawn_table"))
        {
            setObjVar(self, "spawn_table", "datatables/spawning/dungeon/mini_bloodrazor_bunker.iff");
        }
        return;
    }
    public int mainframeFailed(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id spawnCell = getCellId(self, "computer");
        location locOne = getLocation(self);
        location locTwo = getLocation(self);
        locOne.x = 7.00f;
        locOne.y = -16.00f;
        locOne.z = 135.00f;
        locOne.cell = spawnCell;
        locTwo.x = 0.00f;
        locTwo.y = -16.00f;
        locTwo.z = 135.00f;
        locTwo.cell = spawnCell;
        obj_id droidOne = create.object("blood_razor_pirate_elite", locOne);
        obj_id droidTwo = create.object("blood_razor_pirate_elite", locTwo);
        return SCRIPT_CONTINUE;
    }
    public int openLocks(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id lockedRoom = getCellId(self, "pantry");
        permissionsMakePublic(lockedRoom);
        return SCRIPT_CONTINUE;
    }
    public int selfDestruct(obj_id self, dictionary params) throws InterruptedException
    {
        messageTo(self, "intensifyExplosions", null, 1.0f, false);
        obj_id[] objPlayers = getPlayerCreaturesInRange(self, 64.0f);
        if (objPlayers != null && objPlayers.length > 0)
        {
            for (obj_id objPlayer : objPlayers) {
                location myLoc = getLocation(self);
                playClientEffectLoc(objPlayer, "clienteffect/combat_explosion_lair_large.cef", myLoc, 10.0f);
                myLoc.x += 5.0f;
                playClientEffectLoc(objPlayer, "clienteffect/combat_explosion_lair_large.cef", myLoc, 10.0f);
                myLoc.z += 5.0f;
                playClientEffectLoc(objPlayer, "clienteffect/combat_explosion_lair_large.cef", myLoc, 10.0f);
                myLoc.x -= 10.0f;
                playClientEffectLoc(objPlayer, "clienteffect/combat_explosion_lair_large.cef", myLoc, 10.0f);
                myLoc.z -= 10.0f;
                playClientEffectLoc(objPlayer, "clienteffect/combat_explosion_lair_large.cef", myLoc, 10.0f);
            }
        }
        destroyObject(self);
        return SCRIPT_CONTINUE;
    }
    public int jackUpIntensity(obj_id self, dictionary params) throws InterruptedException
    {
        float intensity = getFloatObjVar(self, "diant.explosionIntensity");
        intensity += 0.1f;
        if (intensity > 2)
        {
            intensity = 2.6f;
        }
        setObjVar(self, "diant.explosionIntensity", intensity);
        return SCRIPT_CONTINUE;
    }
    public int intensifyExplosions(obj_id self, dictionary params) throws InterruptedException
    {
        int intensifying = getIntObjVar(self, "diant.intensifying");
        if (intensifying == 0)
        {
            intensifying = 1;
            setObjVar(self, "diant.intensifying", intensifying);
            messageTo(self, "jackUpIntensity", null, 20.0f, false);
            messageTo(self, "jackUpIntensity", null, 40.0f, false);
            messageTo(self, "jackUpIntensity", null, 60.0f, false);
            messageTo(self, "jackUpIntensity", null, 80.0f, false);
            messageTo(self, "jackUpIntensity", null, 100.0f, false);
            messageTo(self, "jackUpIntensity", null, 120.0f, false);
            messageTo(self, "jackUpIntensity", null, 140.0f, false);
            messageTo(self, "jackUpIntensity", null, 160.0f, false);
            messageTo(self, "jackUpIntensity", null, 180.0f, false);
            messageTo(self, "jackUpIntensity", null, 200.0f, false);
            messageTo(self, "jackUpIntensity", null, 220.0f, false);
        }
        return SCRIPT_CONTINUE;
    }
}
