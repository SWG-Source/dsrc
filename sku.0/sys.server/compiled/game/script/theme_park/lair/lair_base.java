package script.theme_park.lair;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.ai.ai_combat;
import script.library.ai_lib;

public class lair_base extends script.theme_park.poi.base
{
    public lair_base()
    {
    }
    public static final int LAIR_RANGE = 200;
    public static final int LAIR_LIMIT = 2;
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        createTriggerVolume("lairBreached", 30.0f, true);
        return SCRIPT_CONTINUE;
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        spawnAllCreatures(self);
        createTriggerVolume("lairBreached", 30.0f, true);
        setObjVar(self, "intPersistent", true);
        messageTo(self, "expireLair", null, 604800, true);
        return SCRIPT_CONTINUE;
    }
    public int OnObjectDisabled(obj_id self, obj_id killer) throws InterruptedException
    {
        location death = getLocation(self);
        playClientEffectLoc(killer, "clienteffect/combat_explosion_lair_large.cef", death, 0);
        spawnBossMob(self, killer);
        destroyObject(self);
        return SCRIPT_CONTINUE;
    }
    public void spawnAllCreatures(obj_id lair) throws InterruptedException
    {
        setObjVar(lair, POI_BASE_OBJECT, lair);
        int numToSpawn = getIntObjVar(lair, "numToSpawn");
        int intI = 0;
        while (intI <= numToSpawn)
        {
            messageTo(lair, "respawnCreature", null, 1 + intI, true);
            intI = intI + 1;
        }
        return;
    }
    public int respawnCreature(obj_id self, dictionary params) throws InterruptedException
    {
        if (hasObjVar(self, "lair.deadLair"))
        {
            return SCRIPT_CONTINUE;
        }
        int numToSpawn = getIntObjVar(self, "numToSpawn");
        int maxToSpawn = numToSpawn * 10;
        int numSpawned = getIntObjVar(self, "lair.numSpawned");
        if (numSpawned >= maxToSpawn)
        {
            setObjVar(self, "lair.deadLair", true);
            return SCRIPT_CONTINUE;
        }
        String respawningCreature = getCreatureName(self);
        String type = getStringObjVar(self, "lairType");
        if (type.equals("npc"))
        {
            location here = getLocation(self);
            obj_id spawnNpc = poiCreateNpc(respawningCreature, rand(-5, +6), rand(-5, +6));
            int delay = getIntObjVar(self, "timeToRespawn");
            poiSetDestroyMessage(spawnNpc, "respawnCreature", delay);
            setObjVar(spawnNpc, "intPersistent", true);
            setObjVar(spawnNpc, "lairSpawn", true);
            return SCRIPT_CONTINUE;
        }
        obj_id creature = poiCreateObject(respawningCreature, rand(-5, +6), rand(-5, +6));
        int delay = getIntObjVar(self, "timeToRespawn");
        poiSetDestroyMessage(creature, "respawnCreature", delay);
        setObjVar(creature, "intPersistent", true);
        setObjVar(creature, "lairSpawn", true);
        if (rand(1, 20) == 1 && ai_lib.isMonster(creature))
        {
            attachScript(creature, "ai.pet_advance");
        }
        return SCRIPT_CONTINUE;
    }
    public int OnObjectDamaged(obj_id self, obj_id attacker, obj_id weapon, int damage) throws InterruptedException
    {
        int curHP = getHitpoints(self);
        int smolder = 2000;
        int fire = 1000;
        if (!hasObjVar(self, "playingEffect"))
        {
            if (curHP < smolder)
            {
                if (curHP < fire)
                {
                    location death = getLocation(self);
                    playClientEffectLoc(attacker, "clienteffect/lair_hvy_damage_fire.cef", death, 0);
                    setObjVar(self, "playingEffect", 1);
                    messageTo(self, "effectManager", null, 15, true);
                    spawnWave(self, 1);
                }
                else 
                {
                    location death = getLocation(self);
                    playClientEffectLoc(attacker, "clienteffect/lair_med_damage_smoke.cef", death, 0);
                    setObjVar(self, "playingEffect", 1);
                    messageTo(self, "effectManager", null, 15, true);
                    spawnWave(self, 2);
                }
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int effectManager(obj_id self, dictionary params) throws InterruptedException
    {
        removeObjVar(self, "playingEffect");
        return SCRIPT_CONTINUE;
    }
    public int OnRemovingFromWorld(obj_id self) throws InterruptedException
    {
        if (hasObjVar(self, "lair.deadLair"))
        {
            destroyObject(self);
        }
        return SCRIPT_CONTINUE;
    }
    public int expireLair(obj_id self, dictionary params) throws InterruptedException
    {
        destroyObject(self);
        return SCRIPT_CONTINUE;
    }
    public void spawnWave(obj_id lair, int wave) throws InterruptedException
    {
        int lastSpawn = getIntObjVar(lair, "lair.waveSpawned");
        if (lastSpawn >= wave)
        {
            return;
        }
        setObjVar(lair, "lair.waveSpawned", wave);
        spawnAllCreatures(lair);
    }
    public void spawnBossMob(obj_id lair, obj_id killer) throws InterruptedException
    {
        if (!hasObjVar(lair, "lair.bossTemplate"))
        {
            return;
        }
        playMusic(killer, "sound/music_event_danger.snd");
        String bossTemplate = getStringObjVar(lair, "lair.bossTemplate") + ".iff";
        obj_id spawnNpc = poiCreateObject(bossTemplate, 0, 0);
        setObjVar(spawnNpc, "intPersistent", true);
        setObjVar(spawnNpc, "lairSpawn", true);
        startCombat(spawnNpc, killer);
    }
}
