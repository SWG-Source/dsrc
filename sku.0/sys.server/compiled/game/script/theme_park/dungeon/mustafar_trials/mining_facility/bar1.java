package script.theme_park.dungeon.mustafar_trials.mining_facility;

import script.library.sequencer;
import script.obj_id;

public class bar1 extends script.base_script
{
    public bar1()
    {
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        sequencer.registerSequenceObject(self, "bar1");
        return SCRIPT_CONTINUE;
    }
}
