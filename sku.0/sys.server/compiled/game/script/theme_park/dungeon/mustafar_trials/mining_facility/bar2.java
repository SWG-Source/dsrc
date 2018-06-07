package script.theme_park.dungeon.mustafar_trials.mining_facility;

import script.library.sequencer;
import script.obj_id;

public class bar2 extends script.base_script
{
    public bar2()
    {
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        sequencer.registerSequenceObject(self, "bar2");
        return SCRIPT_CONTINUE;
    }
}
