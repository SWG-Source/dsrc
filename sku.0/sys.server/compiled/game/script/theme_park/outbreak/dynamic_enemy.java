package script.theme_park.outbreak;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.ai_lib;
import script.library.utils;

public class dynamic_enemy extends script.base_script
{
    public dynamic_enemy()
    {
    }
    public static final int MAXDISTANCE = 60;
    public int OnAttach(obj_id self) throws InterruptedException
    {
        CustomerServiceLog("outbreak_themepark", "dynamic_enemy.OnAttach() initialized.");
        messageTo(self, "startVerificationLoop", null, 0, false);
        return SCRIPT_CONTINUE;
    }
    public int OnDeath(obj_id self, obj_id killer, obj_id corpseId) throws InterruptedException
    {
        CustomerServiceLog("outbreak_themepark", "dynamic_enemy.OnDeath() Undead NPC " + self + " has died at the hands of " + killer);
        return SCRIPT_CONTINUE;
    }
    public int OnIncapacitateTarget(obj_id self, obj_id victim) throws InterruptedException
    {
        if (ai_lib.isDead(self))
        {
            return SCRIPT_CONTINUE;
        }
        CustomerServiceLog("outbreak_themepark", "dynamic_enemy.OnIncapacitateTarget() Mob " + self + " Incapacitated a target: " + victim);
        messageTo(self, "destroySelf", null, 1, false);
        return SCRIPT_CONTINUE;
    }
    public int OnExitedCombat(obj_id self) throws InterruptedException
    {
        if (ai_lib.isDead(self))
        {
            return SCRIPT_CONTINUE;
        }
        messageTo(self, "destroySelf", null, 1, false);
        return SCRIPT_CONTINUE;
    }
    public int startVerificationLoop(obj_id self, dictionary params) throws InterruptedException
    {
        CustomerServiceLog("outbreak_themepark", "dynamic_enemy.startVerificationLoop() Handler initialized");
        location currentLoc = getLocation(self);
        if (currentLoc == null)
        {
            CustomerServiceLog("outbreak_themepark", "dynamic_enemy.startVerificationLoop() current boss mob (" + self + ") location NULL.");
            return SCRIPT_CONTINUE;
        }
        location creationLoc = aiGetHomeLocation(self);
        if (creationLoc == null)
        {
            CustomerServiceLog("outbreak_themepark", "dynamic_enemy.startVerificationLoop() creation location for boss mob (" + self + ") was NULL.");
            return SCRIPT_CONTINUE;
        }
        float distanceCheck = utils.getDistance2D(currentLoc, creationLoc);
        if (distanceCheck > MAXDISTANCE)
        {
            CustomerServiceLog("outbreak_themepark", "dynamic_enemy.startVerificationLoop() boss mob (" + self + ") has moved too far from creation location. Moving back to creation location.");
            messageTo(self, "destroySelf", null, 1, false);
        }
        else 
        {
            messageTo(self, "startVerificationLoop", null, 3, false);
        }
        return SCRIPT_CONTINUE;
    }
    public int destroySelf(obj_id self, dictionary params) throws InterruptedException
    {
        if (ai_lib.isInCombat(self))
        {
            return SCRIPT_CONTINUE;
        }
        destroyObject(self);
        return SCRIPT_CONTINUE;
    }
}
