package script.theme_park.heroic.exar_kun;

import script.dictionary;
import script.library.ai_lib;
import script.obj_id;

public class exar_professor extends script.base_script
{
    public exar_professor()
    {
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        setCreatureCoverVisibility(self, false);
        setInvulnerable(self, true);
        return SCRIPT_CONTINUE;
    }
    public int assume_death(obj_id self, dictionary params) throws InterruptedException
    {
        ai_lib.setMood(self, "npc_dead_01");
        return SCRIPT_CONTINUE;
    }
}
