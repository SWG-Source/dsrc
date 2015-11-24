package script.working.toad;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.utils;

public class msg_test_sender extends script.base_script
{
    public msg_test_sender()
    {
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        location here = getLocation(self);
        here.x = here.x + 4;
        obj_id receiver = createObject("object/tangible/container/drum/tatt_drum_1.iff", here);
        setObjVar(self, "receiver", receiver);
        attachScript(receiver, "toad.msg_test_receiver");
        return SCRIPT_CONTINUE;
    }
    public int OnHearSpeech(obj_id self, obj_id speaker, String text) throws InterruptedException
    {
        obj_id receiver = getObjIdObjVar(self, "receiver");
        if (receiver == null)
        {
            debugSpeakMsg(self, "Receiver is invalid, please make a new test case");
            return SCRIPT_CONTINUE;
        }
        if (!hasObjVar(speaker, "gm"))
        {
            return SCRIPT_CONTINUE;
        }
        if (text.equals("self message"))
        {
            String messageToMe = "Got my own Message.";
            dictionary boom = new dictionary();
            boom.put("message", messageToMe);
            messageTo(self, "messageForMe", boom, 5, true);
            debugSpeakMsg(self, "I sent Myself a message");
            return SCRIPT_CONTINUE;
        }
        if (text.equals("instant message"))
        {
            String message = "Got Instant Message";
            dictionary stuff = new dictionary();
            stuff.put("message", message);
            stuff.put("sender", self);
            messageTo(receiver, "immediate_message", stuff, 0, true);
            debugSpeakMsg(self, "Sent Immediate Message");
            return SCRIPT_CONTINUE;
        }
        int stringCheck = text.indexOf("delayMessage");
        if (stringCheck > -1)
        {
            String time = text.substring(text.indexOf(" ") + 1, text.length());
            int delay = utils.stringToInt(time);
            String delayedMessage = "Got a delayed Message";
            dictionary things = new dictionary();
            things.put("message", delayedMessage);
            things.put("sender", self);
            messageTo(receiver, "delayed_message", things, delay, true);
            debugSpeakMsg(self, "I sent a delayed Message");
            return SCRIPT_CONTINUE;
        }
        return SCRIPT_CONTINUE;
    }
    public int messageForMe(obj_id self, dictionary params) throws InterruptedException
    {
        String message = params.getString("message");
        debugSpeakMsg(self, message);
        return SCRIPT_CONTINUE;
    }
    public int gotImmediate(obj_id self, dictionary params) throws InterruptedException
    {
        debugSpeakMsg(self, "Instant Message Went Through");
        return SCRIPT_CONTINUE;
    }
    public int gotDelayed(obj_id self, dictionary params) throws InterruptedException
    {
        debugSpeakMsg(self, "Delayed Message Went through");
        return SCRIPT_CONTINUE;
    }
}
