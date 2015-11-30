package script.theme_park.heroic.exar_kun;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.ai_lib;
import script.library.buff;
import script.library.factions;
import script.library.trial;

public class ct_vapor_walker extends script.base_script
{
    public ct_vapor_walker()
    {
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        setCreatureCoverVisibility(self, false);
        setInvulnerable(self, true);
        messageTo(self, "handlePath", null, 1.0f, false);
        messageTo(self, "vapor_pulse", null, 2.0f, false);
        factions.setIgnorePlayer(self);
        return SCRIPT_CONTINUE;
    }
    public int handlePath(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id[] players = trial.getValidTargetsInCell(trial.getTop(self), "r4");
        obj_id target = players[rand(0, players.length - 1)];
        setMovementPercent(self, 0.75f);
        follow(self, target, 0.0f, 0.1f);
        messageTo(self, "handlePath", null, 10.0f, false);
        return SCRIPT_CONTINUE;
    }
    public int vapor_pulse(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id boss = getFirstObjectWithScript(getLocation(self), 200.0f, "theme_park.heroic.exar_kun.caretaker");
        if (!isIdValid(boss) || !exists(boss) || isDead(boss))
        {
            trial.cleanupObject(self);
            return SCRIPT_CONTINUE;
        }
        location selfLoc = getLocation(self);
        String tarLoc = "" + selfLoc.x + " " + selfLoc.y + " " + selfLoc.z + " " + selfLoc.cell + " " + selfLoc.x + " " + selfLoc.y + " " + selfLoc.z;
        queueCommand(boss, (-345389055), ai_lib.getFollowTarget(self), tarLoc, COMMAND_PRIORITY_DEFAULT);
        messageTo(self, "vapor_pulse", null, 2.0f, false);
        return SCRIPT_CONTINUE;
    }
}
