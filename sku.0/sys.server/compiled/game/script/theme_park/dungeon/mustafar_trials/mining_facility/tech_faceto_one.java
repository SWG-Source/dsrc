package script.theme_park.dungeon.mustafar_trials.mining_facility;

import script.library.sequencer;
import script.obj_id;

public class tech_faceto_one extends script.base_script
{
    public tech_faceto_one()
    {
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        sequencer.registerSequenceObject(self, "tech_face1");
        return SCRIPT_CONTINUE;
    }
}
