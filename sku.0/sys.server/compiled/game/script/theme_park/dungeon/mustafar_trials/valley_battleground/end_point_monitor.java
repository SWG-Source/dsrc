package script.theme_park.dungeon.mustafar_trials.valley_battleground;

import script.dictionary;
import script.library.ai_lib;
import script.library.instance;
import script.library.trial;
import script.library.utils;
import script.obj_id;

public class end_point_monitor extends script.base_script
{
    public end_point_monitor()
    {
    }
    public static final String VOLUME_NAME = "finish_monitor";
    public static final float VOLUME_RANGE = 18.0f;
    public static final int RESCAN = 10;
    public static final String CAN_SCAN = "canScan";
    public static final String STAGE = "stage";
    public int OnAttach(obj_id self) throws InterruptedException
    {
        trial.setInterest(self);
        messageTo(self, "scan", null, 1.0f, false);
        return SCRIPT_CONTINUE;
    }
    public int scan(obj_id self, dictionary params) throws InterruptedException
    {
        checkForDroidArmy(self);
        return SCRIPT_CONTINUE;
    }
    public int getStage(obj_id self) throws InterruptedException
    {
        int stage = 0;
        if (utils.hasScriptVar(self, STAGE))
        {
            stage = utils.getIntScriptVar(self, STAGE);
        }
        return stage;
    }
    public void incrementStage(obj_id self) throws InterruptedException
    {
        int stage = getStage(self);
        stage += 1;
        utils.setScriptVar(self, STAGE, stage);
    }
    public void decrementStage(obj_id self) throws InterruptedException
    {
        int stage = getStage(self);
        stage -= 1;
        if (stage < 0)
        {
            stage = 0;
        }
        utils.setScriptVar(self, STAGE, stage);
    }
    public void checkForDroidArmy(obj_id self) throws InterruptedException
    {
        messageTo(self, "scan", null, RESCAN, false);
        obj_id[] droids = trial.getObjectsInRangeWithScriptVar(self, trial.BATTLEFIELD_DROID_ARMY, VOLUME_RANGE);
        if (droids == null || droids.length == 0)
        {
            decrementStage(self);
        }
        else 
        {
            boolean living = false;
            for (obj_id droid : droids) {
                if (!isDead(droid) && !ai_lib.isInCombat(droid)) {
                    living = true;
                }
            }
            if (living)
            {
                incrementStage(self);
            }
            else 
            {
                decrementStage(self);
                return;
            }
        }
        int stage = getStage(self);
        switch (stage)
        {
            case 1:
            instance.sendInstanceSystemMessage(trial.getParent(self), trial.BATTLEFIELD_LOSE_1);
            return;
            case 2:
            instance.sendInstanceSystemMessage(trial.getParent(self), trial.BATTLEFIELD_LOSE_2);
            return;
            case 3:
            instance.sendInstanceSystemMessage(trial.getParent(self), trial.BATTLEFIELD_LOSE_3);
            return;
            case 4:
            messageTo(trial.getParent(self), "loseTrial", null, 0, false);
        }
    }
}
