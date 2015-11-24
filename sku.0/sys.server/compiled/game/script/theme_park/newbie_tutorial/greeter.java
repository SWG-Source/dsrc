package script.theme_park.newbie_tutorial;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.chat;
import script.library.colors;

public class greeter extends script.theme_park.newbie_tutorial.tutorial_base
{
    public greeter()
    {
    }
    public int initiateConvo(obj_id self, dictionary params) throws InterruptedException
    {
        makeStaticWaypoint(self);
        obj_id player = getPlayer(self);
        doAnimationAction(self, "wave_hail");
        chat.chat(self, new string_id(NEWBIE_CONVO, "greeting"));
        messageTo(self, "handleLookAtExplanation", null, SHORT_DELAY, false);
        return SCRIPT_CONTINUE;
    }
    public int handleLookAtExplanation(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id player = getPlayer(self);
        if (!isInRoom(player, "r2"))
        {
            return SCRIPT_CONTINUE;
        }
        if (hasObjVar(self, "newbie.handlingConvo"))
        {
            return SCRIPT_CONTINUE;
        }
        doAnimationAction(self, "point_to_self");
        chat.chat(self, new string_id(NEWBIE_CONVO, "explain_lookat"));
        messageTo(self, "handleConvoExplain", null, SHORT_DELAY, false);
        return SCRIPT_CONTINUE;
    }
    public int handleConvoExplain(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id player = getPlayer(self);
        if (!isInRoom(player, "r2"))
        {
            return SCRIPT_CONTINUE;
        }
        if (hasObjVar(self, "newbie.handlingConvo"))
        {
            return SCRIPT_CONTINUE;
        }
        doAnimationAction(self, "yes");
        return SCRIPT_CONTINUE;
    }
    public int OnObjectMenuRequest(obj_id self, obj_id player, menu_info mi) throws InterruptedException
    {
        removeStaticWaypoint(self);
        setObjVar(self, "newbie.handlingConvo", true);
        int mnu = mi.addRootMenu(menu_info_types.CONVERSE_START, null);
        menu_info_data mdata = mi.getMenuItemById(mnu);
        mdata.setServerNotify(false);
        return SCRIPT_CONTINUE;
    }
    public int OnStartNpcConversation(obj_id self, obj_id speaker) throws InterruptedException
    {
        faceToBehavior(self, speaker);
        string_id greeting = new string_id(NEWBIE_CONVO, "convo_1_start");
        string_id response[] = new string_id[2];
        response[0] = new string_id(NEWBIE_CONVO, "convo_1_reply_1");
        response[1] = new string_id(NEWBIE_CONVO, "convo_1_reply_2");
        setObjVar(self, "newbie.inConvo", true);
        npcStartConversation(speaker, self, CONVO, greeting, response);
        return SCRIPT_CONTINUE;
    }
    public int OnNpcConversationResponse(obj_id self, String convo, obj_id player, string_id response) throws InterruptedException
    {
        npcRemoveConversationResponse(player, response);
        if ((response.getAsciiId()).equals("convo_1_reply_1"))
        {
            string_id message = new string_id(NEWBIE_CONVO, "convo_1_more");
            npcSpeak(player, message);
            npcAddConversationResponse(player, new string_id(NEWBIE_CONVO, "convo_1_reply_3"));
            doAnimationAction(self, "tap_head");
        }
        else if ((response.getAsciiId()).equals("convo_1_reply_2"))
        {
            doAnimationAction(self, "point_left");
            setObjVar(player, "newbie.getBoxOfStuff", true);
            string_id message = new string_id(NEWBIE_CONVO, "convo_1_stuff");
            npcSpeak(player, message);
        }
        else if ((response.getAsciiId()).equals("convo_1_reply_3"))
        {
            doAnimationAction(self, "pound_fist_palm");
            string_id message = new string_id(NEWBIE_CONVO, "convo_1_explain");
            npcSpeak(player, message);
        }
        return SCRIPT_CONTINUE;
    }
    public int OnEndNpcConversation(obj_id self, obj_id player) throws InterruptedException
    {
        removeObjVar(self, "newbie.inConvo");
        messageTo(self, "handleOfferSupplies", null, 2, false);
        return SCRIPT_CONTINUE;
    }
    public int handleOfferSupplies(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id player = getPlayer(self);
        if (!isInRoom(player, "r2"))
        {
            return SCRIPT_CONTINUE;
        }
        if (hasObjVar(self, "newbie.offerOnce"))
        {
            return SCRIPT_CONTINUE;
        }
        setObjVar(self, "newbie.offerOnce", true);
        if (!hasObjVar(player, "newbie.getBoxOfStuff"))
        {
            string_id message = new string_id(NEWBIE_CONVO, "convo_1_stuff");
            chat.chat(self, message);
            doAnimationAction(self, "point_left");
            setObjVar(player, "newbie.getBoxOfStuff", true);
        }
        obj_id box = getObjIdObjVar(getTopMostContainer(self), CRATE);
        messageTo(box, "handlePromptToOpen", null, SHORT_DELAY, false);
        makeStaticWaypoint(box);
        flyText(box, "open_me", 1.5f, colors.YELLOW);
        return SCRIPT_CONTINUE;
    }
}
