package script.npe;

import script.obj_id;

public class npe_falcon_player extends script.base_script
{
    public npe_falcon_player()
    {
    }
    public int OnNewbieTutorialResponse(obj_id self, String strAction) throws InterruptedException
    {
        if (strAction.equals("clientReady"))
        {
            obj_id objShip = getTopMostContainer(self);
            messageTo(objShip, "doEvents", null, 1, false);
        }
        return SCRIPT_CONTINUE;
    }
}
