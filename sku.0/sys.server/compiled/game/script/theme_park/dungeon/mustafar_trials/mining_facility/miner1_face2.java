package script.theme_park.dungeon.mustafar_trials.mining_facility;

import script.library.sequencer;
import script.obj_id;

public class miner1_face2 extends script.base_script
{
    public miner1_face2()
    {
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        sequencer.registerSequenceObject(self, "miner1_face2");
        return SCRIPT_CONTINUE;
    }
}
