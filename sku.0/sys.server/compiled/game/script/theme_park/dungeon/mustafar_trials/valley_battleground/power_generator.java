package script.theme_park.dungeon.mustafar_trials.valley_battleground;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.ai_lib;
import script.library.factions;
import script.library.utils;
import script.library.trial;

public class power_generator extends script.base_script
{
    public power_generator()
    {
    }
    public static final String VOLUME_NAME = "addGeneratorHate";
    public static final float VOLUME_RANGE = 40.0f;
    public static final boolean LOGGING = false;
    public int OnAttach(obj_id self) throws InterruptedException
    {
        trial.setInterest(self);
        setTriggerVolume(self);
        trial.setHp(self, trial.HP_BATTLEFIELD_GENERATOR);
        factions.setIgnorePlayer(self);
        return SCRIPT_CONTINUE;
    }
    public int OnObjectDisabled(obj_id self, obj_id killer) throws InterruptedException
    {
        location death = getLocation(self);
        playClientEffectObj(killer, trial.PRT_WORKING_HK_BOOM_1, self, "");
        playClientEffectLoc(killer, "clienteffect/combat_explosion_lair_large.cef", death, 0);
        obj_id top = trial.getParent(self);
        if (!isIdValid(top))
        {
            doLogging("OnDestroy", "Could not find parent obj_id");
            return SCRIPT_CONTINUE;
        }
        messageTo(top, "generatorDestroyed", null, 0, false);
        setInvulnerable(self, true);
        messageTo(self, "destroyDisabledLair", null, .5f, false);
        obj_id[] enemies = getWhoIsTargetingMe(self);
        if (enemies != null && enemies.length > 1)
        {
            for (int i = 0; i < enemies.length; i++)
            {
                if (isPlayer(enemies[i]))
                {
                    setTarget(enemies[i], null);
                    setCombatTarget(enemies[i], null);
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
                addHate(breacher, self, 1);
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
                    playClientEffectObj(attacker, "clienteffect/lair_med_damage_smoke.cef", self, "");
                    playClientEffectLoc(attacker, "clienteffect/combat_explosion_lair_large.cef", death, 0);
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
    public void doLogging(String section, String message) throws InterruptedException
    {
        if (LOGGING || trial.VALLEY_LOGGING)
        {
            LOG("logging/power_generator/" + section, message);
        }
    }
}
