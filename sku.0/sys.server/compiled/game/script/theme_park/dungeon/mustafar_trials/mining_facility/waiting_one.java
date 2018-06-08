package script.theme_park.dungeon.mustafar_trials.mining_facility;

import script.library.sequencer;
import script.obj_id;

public class waiting_one extends script.base_script
{
    public waiting_one()
    {
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        attachScript(self, "content_tools.sequencer_master_object");
        sequencer.registerSequenceObject(self, "waiting1");
        setObjVar(self, "strSequenceTable", "must_waiting_one");
        setInvulnerable(self, true);
        messageTo(self, "doEvents", null, 10, false);
        return SCRIPT_CONTINUE;
    }
}
