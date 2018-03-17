package script.npe;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.space_utils;
import script.library.space_transition;

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
