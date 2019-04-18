package script.working.cmayer;

import script.obj_id;

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
        switch (text) {
            case "list":
                obj_id[] contents = getContents(self);
                for (obj_id content : contents) {
                    debugConsoleMsg(speaker, "I have " + content);
                }
                break;
            case "yaw": {
                float f = rand(0.0f, 270.0f);
                debugConsoleMsg(speaker, "Rotating object " + f);
                setYaw(self, f);
                break;
            }
            case "createyaw": {
                float f = rand(0.0f, 270.0f);
                debugConsoleMsg(speaker, "Rotating object " + f);
                obj_id obj = createObject("object/building/player/player_house_tatooine_small_style_01.iff", getLocation(speaker));
                persistObject(obj);
                setYaw(obj, f);
                break;
            }
        }
        return SCRIPT_CONTINUE;
    }
}
