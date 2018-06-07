package script.theme_park.newbie_tutorial;

import script.library.ai_lib;
import script.library.chat;
import script.menu_info;
import script.obj_id;
import script.string_id;

public class commoner1 extends script.theme_park.newbie_tutorial.tutorial_base
{
    public commoner1()
    {
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        ai_lib.setDefaultCalmMood(self, "npc_consoling");
        return SCRIPT_CONTINUE;
    }
    public int OnObjectMenuRequest(obj_id self, obj_id player, menu_info mi) throws InterruptedException
    {
        chat.chat(self, new string_id(NEWBIE_CONVO, "darn_empire"));
        doAnimationAction(self, "gesticulate_wildly");
        return SCRIPT_CONTINUE;
    }
}
