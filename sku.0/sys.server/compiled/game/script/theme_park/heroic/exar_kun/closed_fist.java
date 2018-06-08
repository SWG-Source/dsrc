package script.theme_park.heroic.exar_kun;

import script.library.buff;
import script.library.trial;
import script.obj_id;

public class closed_fist extends script.base_script
{
    public closed_fist()
    {
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        trial.setHp(self, trial.HP_EXAR_CLOSED);
        return SCRIPT_CONTINUE;
    }
    public int OnEnteredCombat(obj_id self) throws InterruptedException
    {
        buff.applyBuff(self, "closed_balance_buff");
        return SCRIPT_CONTINUE;
    }
}
