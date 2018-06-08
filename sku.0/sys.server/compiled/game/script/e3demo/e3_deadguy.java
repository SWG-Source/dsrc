package script.e3demo;

import script.library.factions;
import script.obj_id;

public class e3_deadguy extends script.base_script
{
    public e3_deadguy()
    {
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        kill(self);
        factions.setFaction(self, "Rebel");
        return SCRIPT_CONTINUE;
    }
}
