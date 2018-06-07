package script.theme_park.heroic.exar_kun;

import script.library.buff;
import script.library.trial;
import script.obj_id;

public class exar_vengence extends script.base_script
{
    public exar_vengence()
    {
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        trial.setHp(self, 545020);
        return SCRIPT_CONTINUE;
    }
    public int OnEnteredCombat(obj_id self) throws InterruptedException
    {
        buff.applyBuff(self, "mind_trick_immune");
        return SCRIPT_CONTINUE;
    }
}
