package script.theme_park.heroic.star_destroyer;

import script.dictionary;
import script.library.ai_lib;
import script.library.trial;
import script.obj_id;

public class south_squad_leader extends script.base_script
{
    public south_squad_leader()
    {
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        messageTo(self, "handleClanup", null, 30.0f, false);
        return SCRIPT_CONTINUE;
    }
    public int handleClanup(obj_id self, dictionary params) throws InterruptedException
    {
        if (ai_lib.isInCombat(self))
        {
            messageTo(self, "handleClanup", null, 10.0f, false);
            return SCRIPT_CONTINUE;
        }
        trial.cleanupObject(self);
        return SCRIPT_CONTINUE;
    }
}
