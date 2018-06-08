package script.theme_park.dungeon.mustafar_trials.mining_facility;

import script.library.sequencer;
import script.obj_id;

public class waiting_goto_three extends script.base_script
{
    public waiting_goto_three()
    {
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        sequencer.registerSequenceObject(self, "wait_goto3");
        return SCRIPT_CONTINUE;
    }
}
