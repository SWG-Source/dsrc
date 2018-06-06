package script.npe;

import script.library.groundquests;
import script.library.utils;
import script.obj_id;
import script.string_id;

public class npe_journal_skeleton extends script.base_script
{
    public npe_journal_skeleton()
    {
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        createTriggerVolume("questdude", 10.0f, true);
        return SCRIPT_CONTINUE;
    }
    public int OnTriggerVolumeEntered(obj_id self, String volumeName, obj_id breacher) throws InterruptedException
    {
        if (!isPlayer(breacher))
        {
            return SCRIPT_CONTINUE;
        }
        if (!volumeName.equals("questdude"))
        {
            return SCRIPT_CONTINUE;
        }
        else 
        {
            if (groundquests.isTaskActive(breacher, "npe_journal_quest", "skeleton") && !utils.hasScriptVar(breacher, "heardit"))
            {
                string_id blah = new string_id("npe/han_solo_experience", "npe_journal");
                sendSystemMessage(breacher, blah);
                utils.setScriptVar(breacher, "heardit", 1);
            }
        }
        return SCRIPT_CONTINUE;
    }
}
