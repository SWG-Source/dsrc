package script.npe;

import script.library.groundquests;
import script.obj_id;

public class npe_station_player extends script.base_script
{
    public npe_station_player()
    {
    }
    public int OnNewbieTutorialResponse(obj_id self, String strAction) throws InterruptedException
    {
        if (strAction.equals("clientReady"))
        {
            newbieTutorialEnableHudElement(self, "chatbox", true, 0);
            newbieTutorialEnableHudElement(self, "radar", false, 0);
            groundquests.grantQuest(self, "quest/npe_solo_profession_2");
            detachScript(self, "npe.npe_station_player");
        }
        return SCRIPT_CONTINUE;
    }
}
