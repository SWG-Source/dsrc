package script.event.ep3demo;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.create;
import script.library.utils;
import script.library.ai_lib;

public class battle_spawner extends script.base_script
{
    public battle_spawner()
    {
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        messageTo(self, "ep3demoCleanUpBattle", null, 36000, false);
        startBattle(self);
        return SCRIPT_CONTINUE;
    }
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        float timeStamp = getFloatObjVar(self, "ep3demo.timeStamp");
        float rightNow = getGameTime();
        if (rightNow - timeStamp > 36000)
        {
            destroyObject(self);
        }
        return SCRIPT_CONTINUE;
    }
    public int OnAddedToWorld(obj_id self) throws InterruptedException
    {
        float timeStamp = getFloatObjVar(self, "ep3demo.timeStamp");
        float rightNow = getGameTime();
        if (rightNow - timeStamp > 36000)
        {
            destroyObject(self);
        }
        return SCRIPT_CONTINUE;
    }
    public void startBattle(obj_id self) throws InterruptedException
    {
        String[] aggressorTemplate = getStringArrayObjVar(self, "ep3demo.aggressorTemplate");
        String[] victimTemplate = getStringArrayObjVar(self, "ep3demo.victimTemplate");
        int aggressorNumber = getIntObjVar(self, "ep3demo.aggressorNumber");
        int victimNumber = getIntObjVar(self, "ep3demo.victimNumber");
        location here = getLocation(self);
        for (int i = 0; i < aggressorNumber; i++)
        {
            int aggRoll = rand(0, aggressorTemplate.length - 1);
            location spawnLoc = utils.getRandomLocationInRing(here, 1, 16);
            obj_id aggressor = create.object(aggressorTemplate[aggRoll], spawnLoc);
            ai_lib.setDefaultCalmBehavior(aggressor, ai_lib.BEHAVIOR_SENTINEL);
            setObjVar(aggressor, "ep3demo.mom", self);
            utils.setScriptVar(aggressor, "ep3demo.aggressor", 1);
            setObjVar(aggressor, "ep3demo.spawnPoint", spawnLoc);
            attachScript(aggressor, "event.ep3demo.combatant");
        }
        for (int i = 0; i < victimNumber; i++)
        {
            int victRoll = rand(0, victimTemplate.length - 1);
            location theOtherSpawnLoc = utils.getRandomLocationInRing(here, 1, 16);
            obj_id victim = create.object(victimTemplate[victRoll], theOtherSpawnLoc);
            ai_lib.setDefaultCalmBehavior(victim, ai_lib.BEHAVIOR_SENTINEL);
            setObjVar(victim, "ep3demo.mom", self);
            utils.setScriptVar(victim, "ep3demo.victim", 1);
            setObjVar(victim, "ep3demo.spawnPoint", theOtherSpawnLoc);
            attachScript(victim, "event.ep3demo.combatant");
        }
        return;
    }
    public int OnHearSpeech(obj_id self, obj_id objSpeaker, String strText) throws InterruptedException
    {
        if (!isGod(objSpeaker))
        {
            return SCRIPT_CONTINUE;
        }
        if ((toLower(strText)).startsWith("stop battle"))
        {
            sendSystemMessage(objSpeaker, "Ending battle...", null);
            destroyObject(self);
        }
        if ((toLower(strText)).startsWith("restart battle"))
        {
            sendSystemMessage(objSpeaker, "Spawning fresh combatants...", null);
            startBattle(self);
        }
        return SCRIPT_CONTINUE;
    }
    public int ep3demoCleanUpBattle(obj_id self, dictionary params) throws InterruptedException
    {
        destroyObject(self);
        return SCRIPT_CONTINUE;
    }
    public int spawnAnotherVictim(obj_id self, dictionary params) throws InterruptedException
    {
        String[] victimTemplate = getStringArrayObjVar(self, "ep3demo.victimTemplate");
        int victRoll = rand(0, victimTemplate.length - 1);
        location here = getLocation(self);
        location theOtherSpawnLoc = utils.getRandomLocationInRing(here, 1, 16);
        obj_id victim = create.object(victimTemplate[victRoll], theOtherSpawnLoc);
        ai_lib.setDefaultCalmBehavior(victim, ai_lib.BEHAVIOR_SENTINEL);
        setObjVar(victim, "ep3demo.mom", self);
        utils.setScriptVar(victim, "ep3demo.victim", 1);
        setObjVar(victim, "ep3demo.spawnPoint", theOtherSpawnLoc);
        attachScript(victim, "event.ep3demo.combatant");
        return SCRIPT_CONTINUE;
    }
    public int spawnAnotherAggressor(obj_id self, dictionary params) throws InterruptedException
    {
        String[] aggressorTemplate = getStringArrayObjVar(self, "ep3demo.aggressorTemplate");
        int aggRoll = rand(0, aggressorTemplate.length - 1);
        location here = getLocation(self);
        location spawnLoc = utils.getRandomLocationInRing(here, 1, 16);
        obj_id aggressor = create.object(aggressorTemplate[aggRoll], spawnLoc);
        ai_lib.setDefaultCalmBehavior(aggressor, ai_lib.BEHAVIOR_SENTINEL);
        setObjVar(aggressor, "ep3demo.mom", self);
        utils.setScriptVar(aggressor, "ep3demo.aggressor", 1);
        setObjVar(aggressor, "ep3demo.spawnPoint", spawnLoc);
        attachScript(aggressor, "event.ep3demo.combatant");
        return SCRIPT_CONTINUE;
    }
}
