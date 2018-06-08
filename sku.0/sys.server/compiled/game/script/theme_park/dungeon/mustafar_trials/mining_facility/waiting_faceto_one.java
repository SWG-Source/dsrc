package script.theme_park.dungeon.mustafar_trials.mining_facility;

import script.library.sequencer;
import script.obj_id;

public class waiting_faceto_one extends script.base_script
{
    public waiting_faceto_one()
    {
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        sequencer.registerSequenceObject(self, "wait_faceto1");
        return SCRIPT_CONTINUE;
    }
}
