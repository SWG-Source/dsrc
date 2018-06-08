package script.theme_park.dungeon.mustafar_trials.mining_facility;

import script.library.sequencer;
import script.obj_id;

public class working_droid3 extends script.base_script
{
    public working_droid3()
    {
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        setInvulnerable(self, true);
        sequencer.registerSequenceObject(self, "droid3");
        return SCRIPT_CONTINUE;
    }
}
