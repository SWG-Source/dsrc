package script.player;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

public class player_logout extends script.base_script
{
    public player_logout()
    {
    }
    public static final string_id SID_LOGOUT_TIME_LEFT = new string_id("logout", "time_left");
    public static final string_id SID_LOGOUT_SAFE_TO_LOG_OUT = new string_id("logout", "safe_to_log_out");
    public static final string_id SID_LOGOUT_ABORTED = new string_id("logout", "aborted");
    public static final string_id SID_LOGOUT_MUST_BE_SITTING = new string_id("logout", "must_be_sitting");
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        removeObjVar(self, "safeLogout");
        detachScript(self, "player.player_logout");
        return SCRIPT_CONTINUE;
    }
    public int OnLogin(obj_id self) throws InterruptedException
    {
        removeObjVar(self, "safeLogout");
        detachScript(self, "player.player_logout");
        return SCRIPT_CONTINUE;
    }
    public int OnEnteredCombat(obj_id self) throws InterruptedException
    {
        abortLogout(self);
        return SCRIPT_CONTINUE;
    }
    public void abortLogout(obj_id self) throws InterruptedException
    {
        sendSystemMessage(self, SID_LOGOUT_ABORTED);
        detachScript(self, "player.player_logout");
    }
    public int OnLogoutPulse(obj_id self, dictionary params) throws InterruptedException
    {
        if (getPosture(self) != POSTURE_SITTING)
        {
            sendSystemMessage(self, SID_LOGOUT_MUST_BE_SITTING);
            abortLogout(self);
        }
        else 
        {
            int timeLeft = params.getInt("timeLeft");
            int countInterval = params.getInt("countInterval");
            if (timeLeft <= 0)
            {
                setObjVar(self, "safeLogout", 1);
                disconnectPlayer(self);
            }
            else 
            {
                prose_package pp = new prose_package();
                pp.stringId = SID_LOGOUT_TIME_LEFT;
                pp.digitInteger = timeLeft;
                sendSystemMessageProse(self, pp);
                params.put("timeLeft", timeLeft - countInterval);
                messageTo(self, "OnLogoutPulse", params, countInterval, false);
            }
        }
        return SCRIPT_CONTINUE;
    }
}
