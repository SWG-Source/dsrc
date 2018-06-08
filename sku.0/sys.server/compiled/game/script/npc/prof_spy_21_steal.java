package script.npc;

import script.library.stealth;
import script.obj_id;

public class prof_spy_21_steal extends script.base_script
{
    public prof_spy_21_steal()
    {
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        stealth.addStealableTemplate(self, "object/tangible/quest/prof_spy_21_incom_plans.iff");
        return SCRIPT_CONTINUE;
    }
}
