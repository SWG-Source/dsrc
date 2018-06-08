package script.theme_park.dungeon.mustafar_trials.mining_facility;

import script.library.sequencer;
import script.obj_id;

public class tech_goto_one extends script.base_script
{
    public tech_goto_one()
    {
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        sequencer.registerSequenceObject(self, "tech_goto1");
        return SCRIPT_CONTINUE;
    }
}
