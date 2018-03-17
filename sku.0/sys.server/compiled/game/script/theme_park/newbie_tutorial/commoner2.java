package script.theme_park.newbie_tutorial;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.ai_lib;
import script.library.chat;

public class commoner2 extends script.theme_park.newbie_tutorial.tutorial_base
{
    public commoner2()
    {
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        ai_lib.setDefaultCalmMood(self, "npc_angry");
        return SCRIPT_CONTINUE;
    }
    public int OnObjectMenuRequest(obj_id self, obj_id player, menu_info mi) throws InterruptedException
    {
        chat.chat(self, new string_id(NEWBIE_CONVO, "leave_me_alone"));
        doAnimationAction(self, "dismiss");
        return SCRIPT_CONTINUE;
    }
}
