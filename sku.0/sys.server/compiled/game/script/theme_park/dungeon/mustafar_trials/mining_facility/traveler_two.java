package script.theme_park.dungeon.mustafar_trials.mining_facility;

import script.library.chat;
import script.library.sequencer;
import script.obj_id;

public class traveler_two extends script.base_script
{
    public traveler_two()
    {
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        setInvulnerable(self, true);
        chat.setChatMood(self, "angry");
        sequencer.registerSequenceObject(self, "female_traveler");
        return SCRIPT_CONTINUE;
    }
}
