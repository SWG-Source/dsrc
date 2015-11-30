package script.theme_park.newbie_tutorial;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.ai_lib;
import script.library.anims;
import script.library.chat;

public class trapped_guy extends script.theme_park.newbie_tutorial.tutorial_base
{
    public trapped_guy()
    {
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        messageTo(self, "handleSpamming", null, rand(20, 30), false);
        return SCRIPT_CONTINUE;
    }
    public int handleSpamming(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id player = getPlayer(self);
        if ((rand(1, 10) < 5) && (isInRoom(player, "r10")))
        {
            chat.chat(self, new string_id(NEWBIE_CONVO, "trapped_guy" + rand(1, 5)));
        }
        messageTo(self, "handleSpamming", null, rand(20, 30), false);
        return SCRIPT_CONTINUE;
    }
}
