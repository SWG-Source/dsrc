package script.theme_park.heroic.axkva_min;

import script.library.trial;
import script.obj_id;

public class gorvo extends script.base_script
{
    public gorvo()
    {
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        trial.setHp(self, trial.HP_AXKVA_GORVO);
        return SCRIPT_CONTINUE;
    }
}
