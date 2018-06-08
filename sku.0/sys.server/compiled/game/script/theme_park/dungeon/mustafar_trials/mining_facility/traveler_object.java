package script.theme_park.dungeon.mustafar_trials.mining_facility;

import script.library.sequencer;
import script.obj_id;

public class traveler_object extends script.base_script
{
    public traveler_object()
    {
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        sequencer.registerSequenceObject(self, "traveler_object");
        return SCRIPT_CONTINUE;
    }
}
