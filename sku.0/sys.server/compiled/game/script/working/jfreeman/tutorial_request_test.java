package script.working.jfreeman;

import script.obj_id;

public class tutorial_request_test extends script.base_script
{
    public tutorial_request_test()
    {
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        newbieTutorialRequest(self, "openInventory");
        newbieTutorialRequest(self, "closeInventory");
        return SCRIPT_CONTINUE;
    }
    public int OnNewbieTutorialResponse(obj_id self, String action) throws InterruptedException
    {
        if (action.equals("openInventory"))
        {
            debugSpeakMsg(self, "opened inventory!");
            newbieTutorialRequest(self, "openInventory");
        }
        else if (action.equals("closeInventory"))
        {
            debugSpeakMsg(self, "closed inventory!");
            newbieTutorialRequest(self, "closeInventory");
        }
        debugSpeakMsg(self, "woah : " + action);
        return SCRIPT_CONTINUE;
    }
}
