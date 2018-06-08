package script.theme_park.heroic.exar_kun;

import script.dictionary;
import script.library.trial;
import script.obj_id;

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
