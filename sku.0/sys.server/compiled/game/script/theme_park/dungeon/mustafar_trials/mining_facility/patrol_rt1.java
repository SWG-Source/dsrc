package script.theme_park.dungeon.mustafar_trials.mining_facility;

import script.library.sequencer;
import script.obj_id;

public class patrol_rt1 extends script.base_script
{
    public patrol_rt1()
    {
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        sequencer.registerSequenceObject(self, "patrol_rt1");
        return SCRIPT_CONTINUE;
    }
}
