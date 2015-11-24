package script.test;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.fs_quests;

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
