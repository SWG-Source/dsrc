package script.working.jfreeman;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

public class broadtest extends script.base_script
{
    public broadtest()
    {
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        debugSpeakMsg(self, "attached");
        return SCRIPT_CONTINUE;
    }
    public int OnSpeaking(obj_id self, String text) throws InterruptedException
    {
        if (text.equals("go"))
        {
            obj_id lookAt = getLookAtTarget(self);
            if (lookAt == null)
            {
                return SCRIPT_CONTINUE;
            }
            listenToMessage(lookAt, "handleBroadcast");
            debugSpeakMsg(self, "subscribed");
        }
        return SCRIPT_CONTINUE;
    }
    public int OnHearSpeech(obj_id self, obj_id speaker, String text) throws InterruptedException
    {
        dictionary stuff = new dictionary();
        stuff.put("objid", self);
        if (text.equals("send"))
        {
            broadcastMessage("handleBroadcast", stuff);
            debugSpeakMsg(self, "broadcast");
        }
        return SCRIPT_CONTINUE;
    }
    public int handleBroadcast(obj_id self, dictionary params) throws InterruptedException
    {
        debugSpeakMsg(self, "received from " + params.getObjId("objid"));
        return SCRIPT_CONTINUE;
    }
}
