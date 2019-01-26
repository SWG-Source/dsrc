package script.theme_park.rebel;

import script.library.groundquests;
import script.obj_id;

public class rtp_wedge_imperial_meeting extends script.base_script
{
    public rtp_wedge_imperial_meeting()
    {
    }
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        createTriggerVolume("rtp_wedge_imperial_meeting", 10.0f, true);
        return SCRIPT_CONTINUE;
    }
    public int OnTriggerVolumeEntered(obj_id self, String volName, obj_id breecher) throws InterruptedException
    {
        if (!isPlayer(breecher))
        {
            return SCRIPT_CONTINUE;
        }
        else 
        {
            if (groundquests.isQuestActive(breecher, "rtp_wedge_01"))
            {
                if (groundquests.isTaskActive(breecher, "rtp_wedge_01", "rtp_wedge_01_01"))
                {
                    groundquests.sendSignal(breecher, "rtp_wedge_01_01");
                    return SCRIPT_CONTINUE;
                }
            }
        }
        return SCRIPT_CONTINUE;
    }
}
