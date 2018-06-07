package script.theme_park.dungeon.mustafar_trials.mining_facility;

import script.library.sequencer;
import script.obj_id;

public class drunk1 extends script.base_script
{
    public drunk1()
    {
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        sequencer.registerSequenceObject(self, "drunk_go1");
        return SCRIPT_CONTINUE;
    }
}
