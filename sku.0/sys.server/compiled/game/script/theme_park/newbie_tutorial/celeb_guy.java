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

public class celeb_guy extends script.theme_park.newbie_tutorial.tutorial_base
{
    public celeb_guy()
    {
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        messageTo(self, "handleSpamming", null, rand(20, 30), false);
        return SCRIPT_CONTINUE;
    }
    public int OnObjectMenuRequest(obj_id self, obj_id player, menu_info mi) throws InterruptedException
    {
        chat.chat(self, new string_id(NEWBIE_CONVO, "celeb_guy" + rand(1, 5)));
        return SCRIPT_CONTINUE;
    }
    public int handleSpamming(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id player = getPlayer(self);
        if ((rand(1, 10) > 5) && (isInRoom(player, "r9")))
        {
            chat.chat(self, new string_id(NEWBIE_CONVO, "celeb_guy" + rand(1, 5)));
        }
        messageTo(self, "handleSpamming", null, rand(20, 30), false);
        return SCRIPT_CONTINUE;
    }
    public int handlePlayerEnteredRoom(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id player = getPlayer(self);
        faceToBehavior(self, player);
        String anim = anims.PLAYER_CELEBRATE;
        switch (rand(1, 4))
        {
            case 1:
            anim = anims.PLAYER_CELEBRATE;
            break;
            case 2:
            anim = anims.PLAYER_CLAP_ROUSING;
            break;
            case 3:
            anim = anims.PLAYER_CELEBRATE1;
            break;
            case 4:
            anim = anims.PLAYER_WAVE2;
            break;
        }
        doAnimationAction(self, anim);
        messageTo(self, "handlePlayerEnteredRoom", null, rand(5, 10), false);
        return SCRIPT_CONTINUE;
    }
}
