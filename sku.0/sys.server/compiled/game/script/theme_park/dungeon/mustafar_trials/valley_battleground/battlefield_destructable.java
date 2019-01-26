package script.theme_park.dungeon.mustafar_trials.valley_battleground;

import script.dictionary;
import script.library.factions;
import script.library.trial;
import script.library.utils;
import script.location;
import script.obj_id;

public class battlefield_destructable extends script.base_script
{
    public battlefield_destructable()
    {
    }
    public static final String VOLUME_NAME = "addGeneratorHate";
    public static final float VOLUME_RANGE = 40.0f;
    public int OnAttach(obj_id self) throws InterruptedException
    {
        trial.setInterest(self);
        setTriggerVolume(self);
        setMaxHitpoints(self, 20000);
        setHitpoints(self, 20000);
        setInvulnerable(self, false);
        factions.setIgnorePlayer(self);
        return SCRIPT_CONTINUE;
    }
    public int OnObjectDisabled(obj_id self, obj_id killer) throws InterruptedException
    {
        location death = getLocation(self);
        playClientEffectObj(killer, "clienteffect/combat_explosion_lair_large.cef", self, "");
        playClientEffectLoc(killer, "clienteffect/combat_explosion_lair_large.cef", death, 0);
        setInvulnerable(self, true);
        messageTo(self, "destroyDisabledLair", null, 0.5f, false);
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
    public int OnTriggerVolumeEntered(obj_id self, String volumeName, obj_id breacher) throws InterruptedException
    {
        if (volumeName.equals(VOLUME_NAME))
        {
            if (utils.hasScriptVar(breacher, trial.BATTLEFIELD_DROID_ARMY))
            {
                addHate(breacher, self, 1000);
            }
        }
        return SCRIPT_CONTINUE;
    }
    public void setTriggerVolume(obj_id self) throws InterruptedException
    {
        createTriggerVolume(VOLUME_NAME, VOLUME_RANGE, true);
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
        if (!hasObjVar(self, "playingEffect"))
        {
            if (curHP < smolder)
            {
                if (curHP < fire)
                {
                    location death = getLocation(self);
                    setObjVar(self, "playingEffect", 1);
                    messageTo(self, "effectManager", null, 15, true);
                }
                else 
                {
                    location death = getLocation(self);
                    setObjVar(self, "playingEffect", 1);
                    messageTo(self, "effectManager", null, 15, true);
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
}
