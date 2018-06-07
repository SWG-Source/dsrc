package script.theme_park.dungeon.mustafar_trials.mining_facility;

import script.library.sequencer;
import script.obj_id;

public class tech_faceto_two extends script.base_script
{
    public tech_faceto_two()
    {
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        sequencer.registerSequenceObject(self, "tech_face2");
        return SCRIPT_CONTINUE;
    }
}
