package script.ai;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.ai_lib;
import script.library.stealth;
import script.library.utils;

public class random_player_target extends script.base_script
{
    public random_player_target()
    {
    }
    public void addHateToTarget(obj_id self) throws InterruptedException
    {
        if (ai_lib.isDead(self))
        {
            return;
        }
        obj_id target = null;
        obj_id[] targets = getPlayerCreaturesInRange(getLocation(self), 64f);
        if (targets == null || targets.length <= 0)
        {
            return;
        }
        target = targets[rand(0, targets.length - 1)];
        if (!isIdValid(target) || isIncapacitated(target) || isDead(target) || stealth.hasInvisibleBuff(target))
        {
            return;
        }
        if (!canSee(self, target))
        {
            stopCombat(self);
        }
        utils.setScriptVar(self, "lastTargetSwitchTime", getGameTime());
        addHate(self, target, 5000);
    }
    public int OnAiCombatFrame(obj_id self) throws InterruptedException
    {
        if (!isIdValid(self))
        {
            return SCRIPT_CONTINUE;
        }
        int lastTime = utils.getIntScriptVar(self, "lastTargetSwitchTime");
        if (getGameTime() - lastTime > 10)
        {
            addHateToTarget(self);
        }
        return SCRIPT_CONTINUE;
    }
}
