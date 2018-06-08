package script.theme_park.dungeon.mustafar_trials.mining_facility;

import script.library.sequencer;
import script.obj_id;

public class waiting_goto_six extends script.base_script
{
    public waiting_goto_six()
    {
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        sequencer.registerSequenceObject(self, "wait_goto6");
        return SCRIPT_CONTINUE;
    }
}
