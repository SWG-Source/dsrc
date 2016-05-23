package script.systems.npc_lair;

import script.dictionary;
import script.library.factions;
import script.library.poi;
import script.library.utils;
import script.location;
import script.obj_id;

public class lair_destruct extends script.base_script
{
    public lair_destruct()
    {
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        attachScript(self, "systems.npc_lair.lair_interactivity");
        return SCRIPT_CONTINUE;
    }
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        if (hasObjVar(self, "lairDestroyed"))
        {
            messageTo(self, "destroyDisabledLair", null, .5f, false);
        }
        attachScript(self, "systems.npc_lair.lair_interactivity");
        return SCRIPT_CONTINUE;
    }
    public int OnObjectDisabled(obj_id self, obj_id killer) throws InterruptedException
    {
        location death = getLocation(self);
        playClientEffectObj(killer, "clienteffect/combat_explosion_lair_large.cef", self, "");
        playClientEffectLoc(killer, "clienteffect/combat_explosion_lair_large.cef", death, 0);
        if (hasObjVar(self, "npc_lair.isCreatureLair"))
        {
            spawnBossMonster(self);
        }
        setInvulnerable(self, true);
        setObjVar(self, "lairDestroyed", true);
        messageTo(self, "destroyDisabledLair", null, .5f, false);
        obj_id[] enemies = getWhoIsTargetingMe(self);
        if (enemies != null && enemies.length > 1)
        {
            for (obj_id enemy : enemies) {
                if (isPlayer(enemy)) {
                    setTarget(enemy, null);
                    setCombatTarget(enemy, null);
                }
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int destroyDisabledLair(obj_id self, dictionary params) throws InterruptedException
    {
        destroyObject(self);
        return SCRIPT_CONTINUE;
    }
    public int OnObjectDamaged(obj_id self, obj_id attacker, obj_id weapon, int damage) throws InterruptedException
    {
        int curHP = getHitpoints(self);
        int maxHP = getMaxHitpoints(self);
        int smolder = (maxHP - (maxHP / 3));
        int fire = (maxHP / 3);
        if (poi.isComplete(self))
        {
            if (curHP > damage)
            {
                setHitpoints(self, (curHP - damage));
            }
        }
        obj_id[] attackers = getWhoIsTargetingMe(self);
        if (attackers != null && attackers.length != 0) {
            obj_id poiBaseObject = getObjIdObjVar(self, "poi.baseObject");
            if (isIdValid(poiBaseObject))
            {
                setObjVar(poiBaseObject, "npc_lair.numAttackers", attackers.length);
            }
        }
        if (!hasObjVar(self, "playingEffect"))
        {
            if (curHP < smolder)
            {
                if (curHP < fire)
                {
                    setObjVar(self, "playingEffect", 1);
                    messageTo(self, "effectManager", null, 15, true);
                }
                else 
                {
                    setObjVar(self, "playingEffect", 1);
                    messageTo(self, "effectManager", null, 15, true);
                }
            }
        }
        if (hasObjVar(self, "npc_lair.isCreatureLair"))
        {
            spawnWaveOfDefenders(self, curHP);
            spawnBossMonster(self);
            callForHealing(self, curHP, maxHP);
        }
        sendAlarm(self, attacker);
        return SCRIPT_CONTINUE;
    }
    public void spawnWaveOfDefenders(obj_id lair, int curHP) throws InterruptedException
    {
        int lastWave = getIntObjVar(lair, "npc_lair.waveSpawned");
        if (lastWave > 1)
        {
            return;
        }
        int maxHP = getMaxHitpoints(lair);
        int nextWave = 0;
        if (curHP < (maxHP - (maxHP / 3)))
        {
            nextWave = 2;
        }
        else if (curHP < maxHP)
        {
            nextWave = 1;
        }
        if (nextWave <= lastWave || nextWave == 0)
        {
            return;
        }
        obj_id poiBaseObject = getObjIdObjVar(lair, "poi.baseObject");
        if (isIdValid(poiBaseObject))
        {
            dictionary parms = new dictionary();
            parms.put("spawningFor", lair);
            messageTo(poiBaseObject, "handleSpawnWaveOfDefenders", parms, 0, false);
        }
        setObjVar(lair, "npc_lair.waveSpawned", nextWave);
    }
    public void spawnBossMonster(obj_id lair) throws InterruptedException
    {
        if (!isIdValid(lair))
        {
            return;
        }
        if (hasObjVar(lair, "npc_lair.bossSpawned"))
        {
            return;
        }
        int maxHP = getMaxHitpoints(lair);
        int curHP = getHitpoints(lair);
        obj_id poiBaseObject = getObjIdObjVar(lair, "poi.baseObject");
        if (!isIdValid(poiBaseObject))
        {
            return;
        }
        if (!isDisabled(lair))
        {
            String diff = getStringObjVar(poiBaseObject, "spawning.lairDifficulty");
            int multiplier = 2;
            if (diff.equals("veryEasy"))
            {
                multiplier = 1;
            }
            else if (diff.equals("easy"))
            {
                multiplier = 2;
            }
            else if (diff.equals("medium"))
            {
                multiplier = 3;
            }
            else if (diff.equals("hard"))
            {
                multiplier = 4;
            }
            else if (diff.equals("veryHard"))
            {
                multiplier = 5;
            }
            if (curHP > ((maxHP / 5) * multiplier))
            {
                return;
            }
        }
        setObjVar(lair, "npc_lair.bossSpawned", true);
        dictionary parms = new dictionary();
        parms.put("npc_lair", lair);
        messageTo(poiBaseObject, "handleSpawnBossMonster", parms, 0, false);
    }
    public int effectManager(obj_id self, dictionary params) throws InterruptedException
    {
        if(isIdValid(self) && hasObjVar(self, "playingEffect")) {
            removeObjVar(self, "playingEffect");
        }
        return SCRIPT_CONTINUE;
    }
    public void callForHealing(obj_id lair, int curHP, int maxHP) throws InterruptedException
    {
        if (curHP == maxHP)
        {
            return;
        }
        int lastCall = utils.getIntScriptVar(lair, "npc_lair.lastHealingCall");
        if (lastCall > getGameTime())
        {
            return;
        }
        obj_id poiBaseObject = getObjIdObjVar(lair, "poi.baseObject");
        if (isIdValid(poiBaseObject))
        {
            dictionary params = new dictionary();
            params.put("lair", lair);
            messageTo(poiBaseObject, "handleCallForHealing", params, 1, false);
        }
        lastCall = getGameTime() + 15;
        utils.setScriptVar(lair, "npc_lair.lastHealingCall", lastCall);
    }
    public void sendAlarm(obj_id lair, obj_id attacker) throws InterruptedException
    {
        int lastCall = utils.getIntScriptVar(lair, "npc_lair.lastAlarmCall");
        if (lastCall > getGameTime())
        {
            return;
        }
        obj_id poiBaseObject = getObjIdObjVar(lair, "poi.baseObject");
        if (isIdValid(poiBaseObject))
        {
            dictionary params = new dictionary();
            params.put("lair", lair);
            params.put("target", attacker);
            messageTo(poiBaseObject, "handleScoutAlarm", params, 1, false);
        }
        lastCall = getGameTime() + 60;
        utils.setScriptVar(lair, "npc_lair.lastAlarmCall", lastCall);
    }
    public void setLairFaction(obj_id lair) throws InterruptedException
    {
        obj_id poiBaseObject = getObjIdObjVar(lair, "poi.baseObject");
        if (isIdValid(poiBaseObject))
        {
            String lairType = getStringObjVar(poiBaseObject, "spawning.lairType");
            if (lairType != null)
            {
                factions.setFaction(lair, utils.getFactionSubString(lairType));
            }
        }
    }
}
