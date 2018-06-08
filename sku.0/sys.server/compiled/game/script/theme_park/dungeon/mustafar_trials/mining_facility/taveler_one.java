package script.theme_park.dungeon.mustafar_trials.mining_facility;

import script.library.chat;
import script.library.sequencer;
import script.obj_id;

public class taveler_one extends script.base_script
{
    public taveler_one()
    {
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        attachScript(self, "content_tools.sequencer_master_object");
        sequencer.registerSequenceObject(self, "male_traveler");
        setObjVar(self, "strSequenceTable", "must_mining_traveler");
        chat.setChatMood(self, "sorry");
        setInvulnerable(self, true);
        messageTo(self, "doEvents", null, 10, false);
        return SCRIPT_CONTINUE;
    }
}
