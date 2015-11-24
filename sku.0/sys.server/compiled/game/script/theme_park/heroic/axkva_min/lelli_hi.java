package script.theme_park.heroic.axkva_min;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.combat;
import script.library.trial;
import script.library.utils;

public class lelli_hi extends script.base_script
{
    public lelli_hi()
    {
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        trial.setHp(self, trial.HP_AXKVA_LILLI_HI);
        return SCRIPT_CONTINUE;
    }
    public int performAmbush(obj_id self, dictionary params) throws InterruptedException
    {
        if (!trial.verifySession(self, params, "ambush"))
        {
            return SCRIPT_CONTINUE;
        }
        utils.removeScriptVar(self, combat.DEFAULT_ATTACK_OVERRIDE);
        obj_id target = combat.getRandomHateTarget(self);
        setInvulnerable(self, false);
        setCreatureCoverVisibility(self, true);
        if (isIdValid(target))
        {
            setLocation(self, getLocation(target));
            queueCommand(self, (1420167995), target, "", COMMAND_PRIORITY_DEFAULT);
        }
        return SCRIPT_CONTINUE;
    }
    public int dropMines(obj_id self, dictionary params) throws InterruptedException
    {
        if (!trial.verifySession(self, params, "ambush"))
        {
            return SCRIPT_CONTINUE;
        }
        queueCommand(self, (1876436203), getTarget(self), "", COMMAND_PRIORITY_DEFAULT);
        return SCRIPT_CONTINUE;
    }
    public int OnExitedCombat(obj_id self) throws InterruptedException
    {
        trial.bumpSession(self, "ambush");
        utils.removeScriptVar(self, combat.DEFAULT_ATTACK_OVERRIDE);
        setInvulnerable(self, false);
        setCreatureCoverVisibility(self, true);
        return SCRIPT_CONTINUE;
    }
    public int OnEnteredCombat(obj_id self) throws InterruptedException
    {
        obj_id[] players = trial.getPlayersInDungeon(trial.getTop(self));
        if (players == null || players.length == 0)
        {
            return SCRIPT_CONTINUE;
        }
        for (int i = 0; i < players.length; i++)
        {
            addHate(self, players[i], 1);
        }
        return SCRIPT_CONTINUE;
    }
}
