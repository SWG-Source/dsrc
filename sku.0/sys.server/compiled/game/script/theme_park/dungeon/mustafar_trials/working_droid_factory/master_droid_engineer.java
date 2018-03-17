package script.theme_park.dungeon.mustafar_trials.working_droid_factory;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.ai_lib;
import script.library.trial;
import script.library.utils;
import script.library.buff;
import script.library.prose;

public class master_droid_engineer extends script.base_script
{
    public master_droid_engineer()
    {
    }
    public static final String VOLUME_NAME = "mdeStartRange";
    public static final float VOLUME_RANGE = 25.0f;
    public static final float RAT_STAGE_1 = 0.90f;
    public static final float RAT_STAGE_2 = 0.75f;
    public static final float RAT_STAGE_3 = 0.60f;
    public static final float RAT_STAGE_4 = 0.45f;
    public static final float RAT_STAGE_5 = 0.30f;
    public static final float RAT_STAGE_6 = 0.15f;
    public static final boolean LOGGING = false;
    public int OnDestroy(obj_id self) throws InterruptedException
    {
        return SCRIPT_CONTINUE;
    }
    public int OnIncapacitated(obj_id self, obj_id killer) throws InterruptedException
    {
        obj_id top = trial.getTop(self);
        dictionary dict = new dictionary();
        dict.put("mde", self);
        messageTo(top, "mdeKilled", dict, 0, false);
        trial.healAssemblyUnit(self);
        detachAllScripts(self);
        removeAllObjVars(self);
        return SCRIPT_CONTINUE;
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        trial.setHp(self, trial.HP_WORKING_MDE);
        setInvulnerable(self, true);
        obj_id homeCell = getContainedBy(self);
        utils.setScriptVar(self, "home", homeCell);
        setTriggerVolume(self);
        return SCRIPT_CONTINUE;
    }
    public int OnAboutToBeTransferred(obj_id self, obj_id destContainer, obj_id transferer) throws InterruptedException
    {
        obj_id homeCell = utils.getObjIdScriptVar(self, "home");
        if (homeCell != destContainer)
        {
            resetSelf(self);
            return SCRIPT_CONTINUE;
        }
        return SCRIPT_CONTINUE;
    }
    public int removeInvulnerable(obj_id self, dictionary params) throws InterruptedException
    {
        setInvulnerable(self, false);
        return SCRIPT_CONTINUE;
    }
    public void resetSelf(obj_id self) throws InterruptedException
    {
        int max = getMaxHealth(self);
        int current = getHealth(self);
        int toHeal = max - current;
        addToHealth(self, toHeal);
        setInvulnerable(self, true);
        ai_lib.clearCombatData();
        trial.healAssemblyUnit(self);
        trial.clearMdeArea(self);
        trial.setMdeEngaged(self, false);
        trial.bumpSession(trial.getTop(self), "mde_control");
        trial.resetAssemblyStage(self);
        String[] healthObjVar = 
        {
            "stage1",
            "stage2",
            "stage3",
            "stage4",
            "stage5",
            "stage6"
        };
        utils.removeObjVarList(self, healthObjVar);
    }
    public int OnExitedCombat(obj_id self) throws InterruptedException
    {
        if (!isDead(self))
        {
            resetSelf(self);
        }
        return SCRIPT_CONTINUE;
    }
    public void setTriggerVolume(obj_id self) throws InterruptedException
    {
        createTriggerVolume(VOLUME_NAME, VOLUME_RANGE, true);
    }
    public int OnTriggerVolumeEntered(obj_id self, String volumeName, obj_id breacher) throws InterruptedException
    {
        if (volumeName.equals(VOLUME_NAME))
        {
            if (isPlayer(breacher))
            {
                triggerEvent(self);
            }
        }
        return SCRIPT_CONTINUE;
    }
    public void triggerEvent(obj_id self) throws InterruptedException
    {
        if (trial.isMdeEngaged(self))
        {
            return;
        }
        utils.messagePlayer(self, trial.getPlayersInDungeon(self), trial.WORKING_MDE_TAUNT);
        setInvulnerable(self, false);
        trial.setMdeEngaged(self, true);
        obj_id[] rau = trial.getObjectsInDungeonWithScript(self, "theme_park.dungeon.mustafar_trials.working_droid_factory.rapid_assembly_unit");
        if (rau == null || rau.length == 0)
        {
            return;
        }
        utils.messageTo(rau, "triggerEvent", trial.getSessionDict(trial.getTop(self), "mde_control"), 0, false);
    }
    public int OnCreatureDamaged(obj_id self, obj_id attacker, obj_id weapon, int[] damage) throws InterruptedException
    {
        if (!trial.isMdeEngaged(self))
        {
            return SCRIPT_CONTINUE;
        }
        verifyHealth(self);
        return SCRIPT_CONTINUE;
    }
    public void verifyHealth(obj_id self) throws InterruptedException
    {
        float max = (float)getMaxHealth(self);
        float current = (float)getHealth(self);
        float ratio = current / max;
        if (ratio <= RAT_STAGE_1)
        {
            if (ratio <= RAT_STAGE_2)
            {
                if (ratio <= RAT_STAGE_3)
                {
                    if (ratio <= RAT_STAGE_4)
                    {
                        if (ratio <= RAT_STAGE_5)
                        {
                            if (ratio <= RAT_STAGE_6)
                            {
                                activateRau(self, 6);
                            }
                            activateRau(self, 5);
                            return;
                        }
                        activateRau(self, 4);
                        return;
                    }
                    activateRau(self, 3);
                    return;
                }
                activateRau(self, 2);
                return;
            }
            activateRau(self, 1);
            return;
        }
    }
    public void activateRau(obj_id self, int value) throws InterruptedException
    {
        String stage = "stage" + Integer.toString(value);
        if (!hasObjVar(self, stage))
        {
            obj_id[] rau = trial.getObjectsInDungeonWithScript(self, "theme_park.dungeon.mustafar_trials.working_droid_factory.rapid_assembly_unit");
            if (rau == null || rau.length == 0)
            {
                return;
            }
            for (int i = 0; i < rau.length; i++)
            {
                utils.setScriptVar(rau[i], trial.WORKING_ASSEMBLY_STAGE, value);
                messageTo(rau[i], "triggerEvent", trial.getSessionDict(trial.getTop(self), "mde_control"), 0, false);
            }
            setObjVar(self, stage, true);
        }
    }
    public void doLogging(String section, String message) throws InterruptedException
    {
        if (LOGGING || trial.WORKING_LOGGING)
        {
            LOG("logging/master_droid_engineer/" + section, message);
        }
    }
}
