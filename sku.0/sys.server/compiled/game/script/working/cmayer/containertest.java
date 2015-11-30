package script.working.cmayer;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

public class containertest extends script.base_script
{
    public containertest()
    {
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        return SCRIPT_CONTINUE;
    }
    public int OnSpeaking(obj_id self, String text) throws InterruptedException
    {
        if (text.equals("test"))
        {
            obj_id groupObject = getGroupObject(self);
            if (isIdValid(groupObject))
            {
                int groupSize = getGroupSize(groupObject);
                int pcSize = getPCGroupSize(groupObject);
                LOG("GroupTest", "Size: " + groupSize + " PCSIze: " + pcSize);
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int OnHearSpeech(obj_id self, obj_id speaker, String text) throws InterruptedException
    {
        if (text.equals("list"))
        {
            obj_id[] contents = getContents(self);
            for (int i = 0; i < contents.length; ++i)
            {
                debugConsoleMsg(speaker, "I have " + contents[i]);
            }
        }
        else if (text.equals("yaw"))
        {
            float f = rand(0.0f, 270.0f);
            debugConsoleMsg(speaker, "Rotating object " + f);
            setYaw(self, f);
        }
        else if (text.equals("createyaw"))
        {
            float f = rand(0.0f, 270.0f);
            debugConsoleMsg(speaker, "Rotating object " + f);
            obj_id obj = createObject("object/building/player/player_house_tatooine_small_style_01.iff", getLocation(speaker));
            persistObject(obj);
            setYaw(obj, f);
        }
        return SCRIPT_CONTINUE;
    }
}
