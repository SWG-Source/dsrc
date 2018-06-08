package script.theme_park.newbie_tutorial;

import script.dictionary;
import script.library.chat;
import script.obj_id;
import script.string_id;

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
