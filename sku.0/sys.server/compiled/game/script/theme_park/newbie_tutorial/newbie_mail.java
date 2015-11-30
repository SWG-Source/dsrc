package script.theme_park.newbie_tutorial;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.utils;

public class newbie_mail extends script.theme_park.newbie_tutorial.tutorial_base
{
    public newbie_mail()
    {
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        newbieTutorialRequest(self, "clientReady");
        return SCRIPT_CONTINUE;
    }
    public int OnNewbieTutorialResponse(obj_id self, String action) throws InterruptedException
    {
        if (action.equals("clientReady"))
        {
            sendStartingMessageToPlayer(self);
            getStartLocationWaypoint(self);
            removeObjVar(self, "newbie");
            removeObjVar(self, "skipTutorial");
            removeObjVar(self, "banking_bankid");
            attachScript(self, "holocron.newbie_handoff");
            detachScript(self, NEWBIE_SCRIPT_EMAIL);
            return SCRIPT_CONTINUE;
        }
        return SCRIPT_CONTINUE;
    }
    public void getStartLocationWaypoint(obj_id player) throws InterruptedException
    {
        obj_id wayp = createWaypointInDatapad(player, getLocation(player));
        if (isIdValid(wayp))
        {
            setWaypointName(wayp, getString(STARTLOC_NAME));
            setWaypointActive(wayp, false);
            setWaypointVisible(wayp, true);
        }
    }
}
