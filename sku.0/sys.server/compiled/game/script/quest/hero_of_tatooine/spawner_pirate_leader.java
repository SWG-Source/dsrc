package script.quest.hero_of_tatooine;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.utils;
import script.library.ai_lib;
import script.library.create;

public class spawner_pirate_leader extends script.base_script
{
    public spawner_pirate_leader()
    {
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        return SCRIPT_CONTINUE;
    }
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        if (canSpawnByConfigSetting())
        {
            setName(self, "Pirate Leader Spawner");
            resetPirateCounter(self);
            messageTo(self, "doSpawnEvent", null, 20, false);
        }
        else 
        {
            setName(self, "Pirate Leader Spawner - Off by Config");
        }
        return SCRIPT_CONTINUE;
    }
    public int doSpawnEvent(obj_id self, dictionary params) throws InterruptedException
    {
        String whatToSpawn = pickAPirate(self);
        float spawnYaw = getYaw(self);
        location spawnLoc = getLocation(self);
        float minSpawnTime = 190.0f;
        float maxSpawnTime = 240.0f;
        float respawnTime = rand(minSpawnTime, maxSpawnTime);
        obj_id npc = create.object(whatToSpawn, spawnLoc);
        if (isIdValid(npc))
        {
            ai_lib.setDefaultCalmBehavior(npc, ai_lib.BEHAVIOR_SENTINEL);
            setObjVar(npc, "objParent", self);
            setObjVar(npc, "fltRespawnTime", respawnTime);
            attachScript(npc, "quest.hero_of_tatooine.spawned_pirate_tracker");
            setYaw(npc, spawnYaw);
        }
        return SCRIPT_CONTINUE;
    }
    public int spawnDestroyed(obj_id self, dictionary params) throws InterruptedException
    {
        messageTo(self, "doSpawnEvent", null, 2, false);
        return SCRIPT_CONTINUE;
    }
    public String pickAPirate(obj_id spawner) throws InterruptedException
    {
        String pirate = "quest_hero_of_tatooine_pirate_deckhand";
        int piratesSpawned = utils.getIntScriptVar(spawner, "piratesSpawned");
        int numPiratesNeeded = utils.getIntScriptVar(spawner, "numPiratesNeeded");
        if (piratesSpawned >= numPiratesNeeded)
        {
            pirate = "quest_hero_of_tatooine_pirate_leader";
            resetPirateCounter(spawner);
        }
        else 
        {
            int chance = rand(0, 24);
            if (chance == 19)
            {
                pirate = "quest_hero_of_tatooine_pirate_leader";
                resetPirateCounter(spawner);
            }
            else 
            {
                utils.setScriptVar(spawner, "piratesSpawned", piratesSpawned + 1);
                int whichOtherPirate = rand(0, 2);
                switch (whichOtherPirate)
                {
                    case 0:
                    pirate = "quest_hero_of_tatooine_pirate_deckhand";
                    break;
                    case 1:
                    pirate = "quest_hero_of_tatooine_pirate_plunderer";
                    break;
                    case 2:
                    pirate = "quest_hero_of_tatooine_pirate_first_mate";
                    break;
                }
            }
        }
        return pirate;
    }
    public void resetPirateCounter(obj_id spawner) throws InterruptedException
    {
        utils.setScriptVar(spawner, "piratesSpawned", 0);
        int num = rand(5, 9);
        utils.setScriptVar(spawner, "numPiratesNeeded", num);
    }
    public boolean canSpawnByConfigSetting() throws InterruptedException
    {
        String disableSpawners = getConfigSetting("GameServer", "disableAreaSpawners");
        if (disableSpawners == null)
        {
            return true;
        }
        if (disableSpawners.equals("true") || disableSpawners.equals("1"))
        {
            return false;
        }
        return true;
    }
}
