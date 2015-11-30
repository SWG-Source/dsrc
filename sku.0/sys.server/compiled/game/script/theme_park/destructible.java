package script.theme_park;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.ai_lib;
import script.library.beast_lib;
import script.library.factions;
import script.library.trial;
import script.library.utils;

public class destructible extends script.base_script
{
    public destructible()
    {
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        int hp = hasObjVar(self, "hp") ? getIntObjVar(self, "hp") : 5000;
        setMaxHitpoints(self, hp);
        setHitpoints(self, hp);
        String attract = hasObjVar(self, "attraction") ? getStringObjVar(self, "attraction") : "none";
        int attractRange = hasObjVar(self, "attraction_range") ? getIntObjVar(self, "attraction_range") : 20;
        if (!attract.equals("none"))
        {
            trial.setInterest(self);
            setTriggerVolume(self, attract, attractRange);
        }
        int ignore_player = hasObjVar(self, "ignore_player") ? getIntObjVar(self, "ignore_player") : 0;
        if (ignore_player == 1)
        {
            factions.setIgnorePlayer(self);
        }
        return SCRIPT_CONTINUE;
    }
    public int OnObjectDisabled(obj_id self, obj_id killer) throws InterruptedException
    {
        location death = getLocation(self);
        String effect = "clienteffect/combat_explosion_lair_large.cef";
        if (hasObjVar(self, "deathEffect"))
        {
            effect = getStringObjVar(self, "deathEffect");
        }
        playClientEffectObj(killer, effect, self, "");
        playClientEffectLoc(killer, effect, death, 0);
        setInvulnerable(self, true);
        messageTo(self, "destroyDisabledLair", null, .5f, false);
        obj_id[] enemies = getWhoIsTargetingMe(self);
        if (enemies != null && enemies.length > 1)
        {
            for (int i = 0; i < enemies.length; i++)
            {
                if (isPlayer(enemies[i]))
                {
                }
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int OnTriggerVolumeEntered(obj_id self, String volumeName, obj_id breacher) throws InterruptedException
    {
        if (!ai_lib.isMob(breacher) || beast_lib.isBeast(breacher))
        {
            return SCRIPT_CONTINUE;
        }
        String attraction_name = getStringObjVar(self, "attraction");
        String social = ai_lib.getSocialGroup(breacher);
        if (volumeName.equals(attraction_name))
        {
            if (social != null && !social.equals("") && social.equals(attraction_name))
            {
                startCombat(breacher, self);
            }
        }
        return SCRIPT_CONTINUE;
    }
    public void setTriggerVolume(obj_id self, String vol_name, int vol_range) throws InterruptedException
    {
        createTriggerVolume(vol_name, vol_range, true);
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
    public void reportDeath(obj_id self, obj_id killer) throws InterruptedException
    {
        int row = getIntObjVar(self, "row");
        String spawn_id = "none";
        if (hasObjVar(self, "spawn_id"))
        {
            spawn_id = getStringObjVar(self, "spawn_id");
        }
        int respawn = getIntObjVar(self, "respawn");
        dictionary dict = trial.getSessionDict(trial.getParent(self));
        dict.put("object", self);
        dict.put("row", row);
        dict.put("spawn_id", spawn_id);
        dict.put("respawn", respawn);
        dict.put("killer", killer);
        messageTo(trial.getParent(self), "terminationCallback", dict, 0.0f, false);
    }
}
