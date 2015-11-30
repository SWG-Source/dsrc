package script.theme_park.heroic.exar_kun;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.trial;

public class exar_swordsman extends script.base_script
{
    public exar_swordsman()
    {
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        setCreatureCoverVisibility(self, false);
        setInvulnerable(self, true);
        aiEquipPrimaryWeapon(self);
        return SCRIPT_CONTINUE;
    }
    public int kill_command(obj_id self, dictionary params) throws InterruptedException
    {
        trial.setHp(self, 415245);
        obj_id[] players = trial.getValidTargetsInCell(trial.getTop(self), "r7");
        obj_id closest = trial.getClosest(self, players);
        startCombat(self, closest);
        return SCRIPT_CONTINUE;
    }
}
