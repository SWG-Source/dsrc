package script.theme_park.dungeon.mustafar_trials.volcano_battlefield;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.ai_lib;
import script.library.utils;
import script.library.create;
import script.library.weapons;
import script.library.trial;
import script.library.buff;

public class event_one_boss extends script.base_script
{
    public event_one_boss()
    {
    }
    public static final String[] BUFF_LIST = 
    {
        "volc_boss_one_1",
        "volc_boss_one_2",
        "volc_boss_one_3",
        "volc_boss_one_4",
        "volc_boss_one_5",
        "volc_boss_one_6",
        "volc_boss_one_7",
        "volc_boss_one_8"
    };
    public static final boolean doLogging = false;
    public int OnAttach(obj_id self) throws InterruptedException
    {
        trial.setHp(self, trial.HP_VOLCANO_ONE_BOSS);
        setInvulnerable(self, true);
        return SCRIPT_CONTINUE;
    }
    public int OnIncapacitated(obj_id self, obj_id killer) throws InterruptedException
    {
        obj_id parent = trial.getParent(self);
        dictionary dict = new dictionary();
        dict.put("type", "boss");
        if (isIdValid(parent))
        {
            messageTo(parent, "eventMobDied", dict, 0, false);
        }
        return SCRIPT_CONTINUE;
    }
    public int OnEnteredCombat(obj_id self) throws InterruptedException
    {
        messageTo(trial.getParent(self), "beginGuardCycle", null, 0, false);
        return SCRIPT_CONTINUE;
    }
    public int OnExitedCombat(obj_id self) throws InterruptedException
    {
        if (!isIncapacitated(self))
        {
            resetEncounter(self);
        }
        return SCRIPT_CONTINUE;
    }
    public void resetEncounter(obj_id self) throws InterruptedException
    {
        clearAllAdds(self);
        respawnAdds(self);
        resetSelf(self);
        clearDamageBuff(self);
    }
    public void clearDamageBuff(obj_id self) throws InterruptedException
    {
        for (int i = 0; i < BUFF_LIST.length; i++)
        {
            if (buff.hasBuff(self, BUFF_LIST[i]))
            {
                buff.removeBuff(self, BUFF_LIST[i]);
            }
        }
    }
    public void clearAllAdds(obj_id self) throws InterruptedException
    {
        obj_id[] objects = getAllObjectsWithObjVar(getLocation(self), 400.0f, "boss");
        if (objects == null || objects.length == 0)
        {
            doLogging("clearAllAdds", "There are no objects in range");
            return;
        }
        for (int i = 0; i < objects.length; i++)
        {
            obj_id parent = getObjIdObjVar(objects[i], "boss");
            if (parent == self)
            {
                trial.cleanupNpc(objects[i]);
            }
        }
    }
    public void respawnAdds(obj_id self) throws InterruptedException
    {
        obj_id parent = trial.getParent(self);
        messageTo(parent, "spawnGuards", null, 2, false);
    }
    public void resetSelf(obj_id self) throws InterruptedException
    {
        int max = getMaxHealth(self);
        int current = getHealth(self);
        int toHeal = max - current;
        addToHealth(self, toHeal);
        setInvulnerable(self, true);
        ai_lib.clearCombatData();
    }
    public int guardDied(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id[] players = getHateList(self);
        utils.sendSystemMessage(players, trial.VOLCANO_TASKMASTER_STRENGTHEN);
        int current = 0;
        for (int i = 0; i < BUFF_LIST.length; i++)
        {
            if (buff.hasBuff(self, BUFF_LIST[i]))
            {
                if (i < BUFF_LIST.length - 1)
                {
                    buff.removeBuff(self, BUFF_LIST[i]);
                    current = i + 1;
                    buff.applyBuff(self, BUFF_LIST[current]);
                    break;
                }
            }
        }
        if (current == 0)
        {
            buff.applyBuff(self, BUFF_LIST[current]);
        }
        return SCRIPT_CONTINUE;
    }
    public int performGuardHeal(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id healingGuard = params.getObjId("guard");
        if (!isIdValid(healingGuard) || !exists(healingGuard))
        {
            doLogging("performGuardHeal", "The guard that tried to heal me was invalid");
            return SCRIPT_CONTINUE;
        }
        playClientEffectObj(healingGuard, trial.PRT_DROID_HEAL, healingGuard, "");
        playClientEffectObj(self, trial.PRT_DROID_HEAL, self, "");
        addToHealth(self, 1000);
        return SCRIPT_CONTINUE;
    }
    public void doLogging(String section, String message) throws InterruptedException
    {
        if (doLogging)
        {
            LOG("travis/event_one/" + section, message);
        }
    }
}
