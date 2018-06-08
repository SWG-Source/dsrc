package script.theme_park.dungeon.mustafar_trials.mining_facility;

import script.library.sequencer;
import script.obj_id;

public class tech_goto_two extends script.base_script
{
    public tech_goto_two()
    {
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        sequencer.registerSequenceObject(self, "tech_goto2");
        return SCRIPT_CONTINUE;
    }
}
