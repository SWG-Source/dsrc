package script.test;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

public class remotedebug_test extends script.base_script
{
    public remotedebug_test()
    {
    }
    public void end(obj_id self) throws InterruptedException
    {
        debugSpeakMsg(self, "Testing of RemoteDebug messages has finished");
    }
    public void start(obj_id self) throws InterruptedException
    {
        debugSpeakMsg(self, "Testing of RemoteDebug messages has begun");
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        start(self);
        return SCRIPT_CONTINUE;
    }
    public int OnDetach(obj_id self) throws InterruptedException
    {
        end(self);
        return SCRIPT_CONTINUE;
    }
    public int OnHearSpeech(obj_id self, obj_id speaker, String text) throws InterruptedException
    {
        String[] words = split(text, ' ');
        if (words[0].equals("sendhi"))
        {
            printChannelMessage("GameServer", "hi");
        }
        if (words[0].equals("sendyo"))
        {
            printChannelMessage("GameServer\\Script", "yo");
        }
        if (words[0].equals("sendsup"))
        {
            printChannelMessage("GameServer\\Script\\Sup", "sup");
        }
        return SCRIPT_CONTINUE;
    }
}
