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
import script.library.utils;
import script.library.colors;

public class greeter_one extends script.theme_park.newbie_tutorial.tutorial_base
{
    public greeter_one()
    {
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        setHomeLocation(self, getLocation(self));
        obj_id player = getPlayer(self);
        faceTo(self, player);
        return SCRIPT_CONTINUE;
    }
    public int handleNewArrival(obj_id self, dictionary params) throws InterruptedException
    {
        if (utils.hasScriptVar(self, "newbie.greeting"))
        {
            return SCRIPT_CONTINUE;
        }
        utils.setScriptVar(self, "newbie.greeting", true);
        obj_id player = getPlayer(self);
        if (player == null)
        {
            if (hasObjVar(self, "newbie.triedOnce"))
            {
                return SCRIPT_CONTINUE;
            }
            setObjVar(self, "newbie.triedOnce", true);
            messageTo(self, "handleNewArrival", null, 10, false);
            return SCRIPT_CONTINUE;
        }
        faceToBehavior(self, player);
        ai_lib.aiFollow(self, player);
        messageTo(self, "handleOne", null, 5, false);
        return SCRIPT_CONTINUE;
    }
    public int handleOne(obj_id self, dictionary params) throws InterruptedException
    {
        faceToBehavior(self, getPlayer(self));
        chat.chat(self, new string_id(NEWBIE_CONVO, "greeter1_bark1"));
        messageTo(self, "handleTwo", null, 5, false);
        return SCRIPT_CONTINUE;
    }
    public int handleTwo(obj_id self, dictionary params) throws InterruptedException
    {
        faceToBehavior(self, getPlayer(self));
        chat.chat(self, new string_id(NEWBIE_CONVO, "greeter1_bark2"));
        messageTo(self, "handleThree", null, 5, false);
        return SCRIPT_CONTINUE;
    }
    public int handleThree(obj_id self, dictionary params) throws InterruptedException
    {
        faceToBehavior(self, getPlayer(self));
        chat.chat(self, new string_id(NEWBIE_CONVO, "greeter1_bark3"));
        messageTo(self, "handlePathToNextRoom", null, 5, false);
        return SCRIPT_CONTINUE;
    }
    public int handlePathToNextRoom(obj_id self, dictionary params) throws InterruptedException
    {
        location destLoc = new location(getLocation(self));
        destLoc.x = ROOM1_GREETER_DEST_X;
        destLoc.y = ROOM1_GREETER_DEST_Y;
        destLoc.z = ROOM1_GREETER_DEST_Z;
        obj_id bldg = getTopMostContainer(self);
        destLoc.cell = getCellId(bldg, ROOM1_GREETER_DEST_CELL);
        stop(self);
        removeObjVar(self, "ai.persistantFollowing");
        pathTo(self, destLoc);
        setObjVar(self, "newbie.movingToRoom2", true);
        return SCRIPT_CONTINUE;
    }
    public int OnMovePathComplete(obj_id self) throws InterruptedException
    {
        if (hasObjVar(self, "newbie.movingToRoom2"))
        {
            removeObjVar(self, "newbie.movingToRoom2");
            doAnimationAction(self, "point_right");
            messageTo(self, "handlePathHome", null, 5, false);
            return SCRIPT_CONTINUE;
        }
        return SCRIPT_CONTINUE;
    }
    public int handlePathHome(obj_id self, dictionary params) throws InterruptedException
    {
        location destLoc = getLocation(self);
        destLoc.x = ROOM1_GREETER_LOC_X;
        destLoc.y = ROOM1_GREETER_LOC_Y;
        destLoc.z = ROOM1_GREETER_LOC_Z;
        obj_id bldg = getTopMostContainer(self);
        destLoc.cell = getCellId(bldg, ROOM1_GREETER_LOC_CELL);
        setObjVar(self, "newbie.movingToMidPoint", true);
        pathTo(self, destLoc);
        return SCRIPT_CONTINUE;
    }
}
