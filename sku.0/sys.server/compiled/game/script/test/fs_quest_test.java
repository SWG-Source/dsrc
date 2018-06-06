package script.test;

import script.obj_id;

public class fs_quest_test extends script.base_script
{
    public fs_quest_test()
    {
    }
    public int OnSpeaking(obj_id self, String text) throws InterruptedException
    {
        return SCRIPT_CONTINUE;
    }
}
