package script.gm;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

public class fs_intro_starter extends script.base_script
{
    public fs_intro_starter()
    {
    }
    public int OnSpeaking(obj_id self, String text) throws InterruptedException
    {
        if (text.equals("startintro"))
        {
            if (!hasScript(self, "quest.force_sensitive.fs_kickoff"))
            {
                attachScript(self, "quest.force_sensitive.fs_kickoff");
            }
            setObjVar(self, "fs_kickoff_stage", 2);
            messageTo(self, "meetOldMan", null, 10.0f, false);
            sendSystemMessageTestingOnly(self, "FS Intro Initiated.");
        }
        return SCRIPT_CONTINUE;
    }
}
