package script.theme_park.newbie_tutorial;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.chat;
import script.library.ai_lib;

public class panic_guy extends script.theme_park.newbie_tutorial.tutorial_base
{
    public panic_guy()
    {
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        queueCommand(self, (1000440469), self, "unhappy", COMMAND_PRIORITY_DEFAULT);
        return SCRIPT_CONTINUE;
    }
    public int initiatePanic(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id trooper = getObjIdObjVar(getTopMostContainer(self), TROOPER1);
        if (!isIdValid(trooper))
        {
            trooper = getObjIdObjVar(getTopMostContainer(self), TROOPER2);
            if (!isIdValid(trooper))
            {
                return SCRIPT_CONTINUE;
            }
        }
        setMovementRun(self);
        location destLoc = new location(getLocation(self));
        destLoc.x = MOUSE_DROID_DEST_X;
        destLoc.y = MOUSE_DROID_DEST_Y;
        destLoc.z = MOUSE_DROID_DEST_Z;
        destLoc.cell = getCellId(getTopMostContainer(self), "r4");
        setHomeLocation(self, destLoc);
        pathTo(self, destLoc);
        chat.setChatMood(self, chat.MOOD_SCARED);
        chat.chat(self, chat.CHAT_SHOUT, new string_id(NEWBIE_CONVO, "shout_panic1"));
        setObjVar(self, "newbie.shoutNum", 1);
        messageTo(self, "handleNextYell", null, 10, false);
        return SCRIPT_CONTINUE;
    }
    public int handleNextYell(obj_id self, dictionary params) throws InterruptedException
    {
        setMovementRun(self);
        int shoutNum = getIntObjVar(self, "newbie.shoutNum");
        shoutNum++;
        chat.chat(self, chat.CHAT_SHOUT, new string_id(NEWBIE_CONVO, "shout_panic" + shoutNum));
        setObjVar(self, "newbie.shoutNum", shoutNum);
        if (shoutNum < 3)
        {
            messageTo(self, "handleNextYell", null, 10, false);
        }
        return SCRIPT_CONTINUE;
    }
    public int OnMovePathComplete(obj_id self) throws InterruptedException
    {
        ai_lib.setMood(self, "npc_sad");
        setMovementWalk(self);
        messageTo(self, "handleWarnTroopers", null, 0, false);
        return SCRIPT_CONTINUE;
    }
    public int OnBehaviorChange(obj_id self, int newBehavior, int oldBehavior, int[] changeFlags) throws InterruptedException
    {
        return SCRIPT_OVERRIDE;
    }
    public int handleWarnTroopers(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id player = getPlayer(self);
        if (player == null)
        {
            return SCRIPT_CONTINUE;
        }
        obj_id currentRoom = getContainedBy(player);
        obj_id bldg = getTopMostContainer(player);
        obj_id roomId = getCellId(bldg, "r4");
        if (currentRoom == roomId)
        {
            faceToBehavior(self, player);
            doAnimationAction(self, "gesticulate_wildly");
            chat.chat(self, chat.CHAT_SHOUT, new string_id(NEWBIE_CONVO, "shout_panic4"));
            messageTo(self, "handleWarnTroopers", null, 20, false);
        }
        return SCRIPT_CONTINUE;
    }
}
