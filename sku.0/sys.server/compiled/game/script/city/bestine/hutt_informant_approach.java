package script.city.bestine;

import script.library.chat;
import script.library.utils;
import script.obj_id;
import script.string_id;

public class hutt_informant_approach extends script.base_script
{
    public hutt_informant_approach()
    {
    }
    public static final String CALL_OUT_TO_VOLUME_NAME = "callOutToTriggerVolume";
    public int OnAttach(obj_id self) throws InterruptedException
    {
        createTriggerVolume(CALL_OUT_TO_VOLUME_NAME, 15.0f, true);
        return SCRIPT_CONTINUE;
    }
    public int OnTriggerVolumeEntered(obj_id self, String volumeName, obj_id breacher) throws InterruptedException
    {
        if (isPlayer(breacher))
        {
            if (volumeName.equals(CALL_OUT_TO_VOLUME_NAME))
            {
                if (canSee(self, breacher))
                {
                    if (utils.playerHasItemByTemplate(breacher, "object/tangible/loot/quest/sean_history_disk.iff"))
                    {
                        faceTo(self, breacher);
                        string_id come_here = new string_id("bestine", "come_here");
                        chat.chat(self, come_here);
                    }
                }
            }
        }
        return SCRIPT_CONTINUE;
    }
}
