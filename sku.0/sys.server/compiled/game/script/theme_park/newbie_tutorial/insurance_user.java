package script.theme_park.newbie_tutorial;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.ai.ai_combat;
import script.library.chat;

public class insurance_user extends script.theme_park.newbie_tutorial.tutorial_base
{
    public insurance_user()
    {
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        messageTo(self, "handleUseBank", null, rand(15, 30), false);
        return SCRIPT_CONTINUE;
    }
    public int OnObjectMenuRequest(obj_id self, obj_id player, menu_info mi) throws InterruptedException
    {
        setObjVar(self, "newbie.playingAnim", true);
        chat.chat(self, new string_id(NEWBIE_CONVO, "ins_user_bark_" + rand(1, 3)));
        stop(self);
        faceToBehavior(self, player);
        switch (rand(1, 3))
        {
            case 1:
            doAnimationAction(self, "alert");
            break;
            case 2:
            doAnimationAction(self, "greet");
            break;
            case 3:
            doAnimationAction(self, "hug_self");
            break;
        }
        return SCRIPT_CONTINUE;
    }
    public int handleUseTerminal(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id bldg = getTopMostContainer(self);
        obj_id terminal = getObjIdObjVar(bldg, INSURANCE);
        faceToBehavior(self, terminal);
        doAnimationAction(self, "manipulate_medium");
        return SCRIPT_CONTINUE;
    }
    public int handleUseBank(obj_id self, dictionary params) throws InterruptedException
    {
        messageTo(self, "handleUseTerminal", null, 5, false);
        messageTo(self, "handleUseBank", null, rand(15, 30), false);
        return SCRIPT_CONTINUE;
    }
}
