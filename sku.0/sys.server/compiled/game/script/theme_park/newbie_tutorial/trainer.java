package script.theme_park.newbie_tutorial;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.utils;

public class trainer extends script.theme_park.newbie_tutorial.tutorial_base
{
    public trainer()
    {
    }
    public int OnObjectMenuRequest(obj_id self, obj_id player, menu_info mi) throws InterruptedException
    {
        removeStaticWaypoint(self);
        return SCRIPT_CONTINUE;
    }
    public int OnEndNpcConversation(obj_id self, obj_id player) throws InterruptedException
    {
        if (hasObjVar(self, "newbie.doneThis"))
        {
            return SCRIPT_CONTINUE;
        }
        if (hasObjVar(player, "newbie.trained"))
        {
            setObjVar(self, "newbie.doneThis", true);
            if (!isInRoom(player, "r9"))
            {
                return SCRIPT_CONTINUE;
            }
            utils.setScriptVar(player, "newbie.trainerIsTalking", true);
            messageTo(player, "handleTrainerDoneTalking", null, 35, false);
            newbieTutorialEnableHudElement(player, "buttonbar", true, 5.0f);
            messageTo(player, "handleShowCharSheet", null, (LONG_DELAY + 5), false);
        }
        return SCRIPT_CONTINUE;
    }
}
