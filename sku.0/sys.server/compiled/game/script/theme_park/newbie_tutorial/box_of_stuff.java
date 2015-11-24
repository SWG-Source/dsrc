package script.theme_park.newbie_tutorial;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.colors;

public class box_of_stuff extends script.theme_park.newbie_tutorial.tutorial_base
{
    public box_of_stuff()
    {
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        for (int i = 0; i < BOX_CONTENTS.length; i++)
        {
            obj_id item = createObject(BOX_CONTENTS[i], self, "");
            attachScript(item, BOX_ITEM_SCRIPT);
            setObjVar(item, "newbie.item", true);
        }
        return SCRIPT_CONTINUE;
    }
    public int OnObjectMenuRequest(obj_id self, obj_id player, menu_info mi) throws InterruptedException
    {
        if (hasObjVar(self, "newbie.boxEmptied"))
        {
            return SCRIPT_CONTINUE;
        }
        setObjVar(player, "newbie.getBoxOfStuff", true);
        return SCRIPT_CONTINUE;
    }
    public int handlePromptToOpen(obj_id self, dictionary params) throws InterruptedException
    {
        if (hasObjVar(self, "newbie.boxOpened"))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id player = getPlayer(self);
        messageTo(self, "handleRepeatBoxOpenPrompt", null, VERY_LONG_DELAY, false);
        return SCRIPT_CONTINUE;
    }
    public int handleRepeatBoxOpenPrompt(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id player = getPlayer(self);
        if (!isInRoom(player, "r2"))
        {
            return SCRIPT_CONTINUE;
        }
        if (hasObjVar(self, "newbie.boxOpened"))
        {
            return SCRIPT_CONTINUE;
        }
        if (hasObjVar(self, "newbie.boxEmptied"))
        {
            return SCRIPT_CONTINUE;
        }
        flyText(self, "open_me", 1.5f, colors.YELLOW);
        messageTo(self, "handleOpenMeFlyText", null, 5, false);
        return SCRIPT_CONTINUE;
    }
    public int handleOpenMeFlyText(obj_id self, dictionary params) throws InterruptedException
    {
        if (hasObjVar(self, "newbie.stopFlyText"))
        {
            return SCRIPT_CONTINUE;
        }
        flyText(self, "open_me", 1.5f, colors.YELLOW);
        messageTo(self, "handleOpenMeFlyText", null, 5, false);
        return SCRIPT_CONTINUE;
    }
    public int OnOpenedContainer(obj_id self, obj_id player) throws InterruptedException
    {
        removeStaticWaypoint(self);
        if (hasObjVar(self, "newbie.boxEmptied"))
        {
            return SCRIPT_CONTINUE;
        }
        setObjVar(self, "newbie.boxOpened", true);
        setObjVar(self, "newbie.stopFlyText", true);
        messageTo(self, "handleExplainTakeItem", null, 4, false);
        return SCRIPT_CONTINUE;
    }
    public int handleExplainTakeItem(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id player = getPlayer(self);
        obj_id[] contents = getContents(self);
        if (contents != null || contents.length > 0)
        {
        }
        messageTo(player, "moveOnToNextRoom", null, 5, false);
        return SCRIPT_CONTINUE;
    }
    public int OnClosedContainer(obj_id self, obj_id player) throws InterruptedException
    {
        if (hasObjVar(self, "newbie.boxEmptied"))
        {
            return SCRIPT_CONTINUE;
        }
        setObjVar(self, "newbie.boxEmptied", true);
        messageTo(player, "handleExplainFreemouse", null, 1, false);
        return SCRIPT_CONTINUE;
    }
    public int OnAboutToBeTransferred(obj_id self, obj_id destContainer, obj_id transferer) throws InterruptedException
    {
        debugSpeakMsg(self, "There's no possible explanation for this.");
        if (isPlayer(transferer))
        {
            sendSystemMessageTestingOnly(transferer, "You cannot pick that up.");
            return SCRIPT_OVERRIDE;
        }
        return SCRIPT_CONTINUE;
    }
}
