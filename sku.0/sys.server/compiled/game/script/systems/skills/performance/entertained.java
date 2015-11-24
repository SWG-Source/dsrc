package script.systems.skills.performance;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.performance;

public class entertained extends script.base_script
{
    public entertained()
    {
    }
    public static final int ENTERTAINED_CHECK_TIME = 10;
    public int OnAttach(obj_id self) throws InterruptedException
    {
        messageTo(self, "OnEntertainedCheck", null, ENTERTAINED_CHECK_TIME, false);
        return SCRIPT_CONTINUE;
    }
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        if (performance.checkPlayerEntertained(self, "both"))
        {
            messageTo(self, "OnEntertainedCheck", null, ENTERTAINED_CHECK_TIME, false);
        }
        return SCRIPT_CONTINUE;
    }
    public int OnEntertainedCheck(obj_id self, dictionary params) throws InterruptedException
    {
        LOG("performance_entertained", "OnEntertainedCheck");
        if (performance.checkPlayerEntertained(self, "both"))
        {
            LOG("performance_entertained", "Still being entertained.");
            messageTo(self, "OnEntertainedCheck", null, ENTERTAINED_CHECK_TIME, false);
        }
        else 
        {
            LOG("performance_entertained", "No longer entertained.");
        }
        return SCRIPT_CONTINUE;
    }
    public int OnTriggerVolumeExited(obj_id self, String volumeName, obj_id target) throws InterruptedException
    {
        return SCRIPT_CONTINUE;
    }
    public int handlePerformerStopPerforming(obj_id self, dictionary params) throws InterruptedException
    {
        LOG("performance_entertained", "Received stop performing message");
        obj_id target = params.getObjId("performer");
        String check = params.getString("check");
        stopListeningToMessage(target, "handlePerformerStopPerforming");
        listenToMessage(target, "handlePerformerStartPerforming");
        performance.checkPlayerEntertained(self, check);
        return SCRIPT_CONTINUE;
    }
    public int handlePerformerStartPerforming(obj_id self, dictionary params) throws InterruptedException
    {
        LOG("performance_entertained", "Received start performing message");
        obj_id target = params.getObjId("performer");
        stopListeningToMessage(target, "handlePerformerStartPerforming");
        listenToMessage(target, "handlePerformerStopPerforming");
        performance.startEntertainingPlayer(self);
        return SCRIPT_CONTINUE;
    }
}
