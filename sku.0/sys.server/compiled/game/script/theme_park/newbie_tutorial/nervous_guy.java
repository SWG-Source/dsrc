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

public class nervous_guy extends script.theme_park.newbie_tutorial.tutorial_base
{
    public nervous_guy()
    {
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        messageTo(self, "handleSpamming", null, rand(30, 60), false);
        return SCRIPT_CONTINUE;
    }
    public int OnObjectMenuRequest(obj_id self, obj_id player, menu_info mi) throws InterruptedException
    {
        chat.chat(self, new string_id(NEWBIE_CONVO, "nervous_guy" + rand(1, 5)));
        return SCRIPT_CONTINUE;
    }
    public int handleSpamming(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id player = getPlayer(self);
        if (hasObjVar(player, "newbie.killedPirate"))
        {
            return SCRIPT_CONTINUE;
        }
        if ((rand(1, 10) == 1) && (isInRoom(player, "r7")))
        {
            chat.chat(self, new string_id(NEWBIE_CONVO, "nervous_guy" + rand(1, 5)));
        }
        messageTo(self, "handleSpamming", null, rand(30, 60), false);
        return SCRIPT_CONTINUE;
    }
}
