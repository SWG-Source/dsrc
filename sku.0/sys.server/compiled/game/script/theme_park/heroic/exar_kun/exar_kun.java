package script.theme_park.heroic.exar_kun;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.ai_lib;
import script.library.trial;

public class exar_kun extends script.base_script
{
    public exar_kun()
    {
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        setCreatureCoverVisibility(self, false);
        setInvulnerable(self, true);
        return SCRIPT_CONTINUE;
    }
    public int kill_command(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id[] players = trial.getValidTargetsInCell(trial.getTop(self), "r7");
        obj_id closest = trial.getClosest(self, players);
        trial.setHp(self, 1105245);
        startCombat(self, closest);
        obj_id swordsman = getFirstObjectWithScript(getLocation(self), 100.0f, "theme_park.heroic.exar_kun.exar_swordsman");
        obj_id observer = getFirstObjectWithScript(getLocation(self), 100.0f, "theme_park.heroic.exar_kun.exar_observer");
        obj_id[] team = 
        {
            self,
            swordsman,
            observer
        };
        ai_lib.establishAgroLink(self, team);
        return SCRIPT_CONTINUE;
    }
}
