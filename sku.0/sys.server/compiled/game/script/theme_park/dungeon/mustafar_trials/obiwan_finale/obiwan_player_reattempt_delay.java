package script.theme_park.dungeon.mustafar_trials.obiwan_finale;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

public class obiwan_player_reattempt_delay extends script.base_script
{
    public obiwan_player_reattempt_delay()
    {
    }
    public static final int REATTEMPT_DELAY_MINUTES = 120;
    public int OnAttach(obj_id self) throws InterruptedException
    {
        initializeTimeDelay(self);
        return SCRIPT_CONTINUE;
    }
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        mustafarReattemptDelayCheck(self);
        return SCRIPT_CONTINUE;
    }
    public int OnLogin(obj_id self) throws InterruptedException
    {
        mustafarReattemptDelayCheck(self);
        return SCRIPT_CONTINUE;
    }
    public void initializeTimeDelay(obj_id player) throws InterruptedException
    {
        int currentTime = getGameTime();
        int delayTime = currentTime + (REATTEMPT_DELAY_MINUTES * 60);
        setObjVar(player, "mustafar.reattemptDelay", delayTime);
        return;
    }
    public void mustafarReattemptDelayCheck(obj_id player) throws InterruptedException
    {
        if (!hasObjVar(player, "mustafar.reattemptDelay"))
        {
            detachScript(player, "player_reattempt_delay");
            return;
        }
        int timeOutEnd = getIntObjVar(player, "mustafar.reattemptDelay");
        int currentTime = getGameTime();
        if (currentTime < timeOutEnd)
        {
            int delayTimeRemaining = timeOutEnd - currentTime;
            if (delayTimeRemaining < 60)
            {
                return;
            }
            messageTo(player, "mustafarReattemptTimeoutExpire", null, delayTimeRemaining, false);
            return;
        }
        removeObjVar(player, "mustafar.reattemptDelay");
        detachScript(player, "them_park.dungeon.mustafar_trials.obiwan_finale.player_reattempt_delay");
        return;
    }
    public int mustafarReattemptTimeoutExpire(obj_id self, dictionary params) throws InterruptedException
    {
        mustafarReattemptDelayCheck(self);
        return SCRIPT_CONTINUE;
    }
}
