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
import script.library.utils;

public class banker extends script.theme_park.newbie_tutorial.tutorial_base
{
    public banker()
    {
    }
    public int handleInitiateConvo(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id player = getPlayer(self);
        doAnimationAction(self, "check_wrist_device");
        chat.setChatMood(self, chat.MOOD_GUILTY);
        chat.chat(self, new string_id(NEWBIE_CONVO, "banker_greeting"));
        faceToBehavior(self, player);
        messageTo(self, "explainConvoAgain", null, 5, false);
        return SCRIPT_CONTINUE;
    }
    public int explainConvoAgain(obj_id self, dictionary params) throws InterruptedException
    {
        if (hasObjVar(self, "newbie.talking"))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id player = getPlayer(self);
        if (utils.hasScriptVar(player, "newbie.usedBank"))
        {
            return SCRIPT_CONTINUE;
        }
        return SCRIPT_CONTINUE;
    }
    public int OnObjectMenuRequest(obj_id self, obj_id player, menu_info mi) throws InterruptedException
    {
        int mnu = mi.addRootMenu(menu_info_types.CONVERSE_START, null);
        menu_info_data mdata = mi.getMenuItemById(mnu);
        mdata.setServerNotify(false);
        if (hasObjVar(self, "newbie.talking"))
        {
            return SCRIPT_CONTINUE;
        }
        if (utils.hasScriptVar(player, "newbie.usedBank"))
        {
            return SCRIPT_CONTINUE;
        }
        setObjVar(self, "newbie.talking", true);
        return SCRIPT_CONTINUE;
    }
    public int OnStartNpcConversation(obj_id self, obj_id speaker) throws InterruptedException
    {
        setObjVar(self, "newbie.talking", true);
        removeStaticWaypoint(self);
        obj_id bldg = getBuilding(self);
        if (!utils.hasScriptVar(speaker, "newbie.usedBank"))
        {
            obj_id bank = getObjIdObjVar(bldg, BANK);
            makeStaticWaypoint(bank);
        }
        string_id greeting = new string_id(NEWBIE_CONVO, "banker_1_start");
        string_id response[] = new string_id[2];
        response[0] = new string_id(NEWBIE_CONVO, "banker_1_reply_1");
        response[1] = new string_id(NEWBIE_CONVO, "banker_1_reply_2");
        doAnimationAction(self, "embarrassed");
        npcStartConversation(speaker, self, CONVO, greeting, response);
        return SCRIPT_CONTINUE;
    }
    public int OnNpcConversationResponse(obj_id self, String convo, obj_id player, string_id response) throws InterruptedException
    {
        npcRemoveConversationResponse(player, response);
        if ((response.getAsciiId()).equals("banker_1_reply_1"))
        {
            string_id message = new string_id(NEWBIE_CONVO, "banker_2_start");
            npcSpeak(player, message);
            doAnimationAction(self, "explain");
            npcAddConversationResponse(player, new string_id(NEWBIE_CONVO, "banker_2_reply_1"));
        }
        else if ((response.getAsciiId()).equals("banker_2_reply_1"))
        {
            string_id message = new string_id(NEWBIE_CONVO, "banker_2_more");
            npcSpeak(player, message);
            doAnimationAction(self, "shake_head_no");
            npcAddConversationResponse(player, new string_id(NEWBIE_CONVO, "banker_2_reply_2"));
        }
        else if ((response.getAsciiId()).equals("banker_2_reply_2"))
        {
            string_id message = new string_id(NEWBIE_CONVO, "banker_2_explain");
            npcSpeak(player, message);
            doAnimationAction(self, "point_left");
            npcAddConversationResponse(player, new string_id(NEWBIE_CONVO, "banker_2_reply_3"));
        }
        else if ((response.getAsciiId()).equals("banker_2_reply_3"))
        {
            string_id message = new string_id(NEWBIE_CONVO, "banker_2_explain_2");
            doAnimationAction(self, "face_wink");
            npcSpeak(player, message);
        }
        else if ((response.getAsciiId()).equals("banker_1_reply_2"))
        {
            string_id message = new string_id(NEWBIE_CONVO, "banker_2_explain_terminals");
            npcSpeak(player, message);
            doAnimationAction(self, "look_left");
            npcAddConversationResponse(player, new string_id(NEWBIE_CONVO, "banker_1_reply_3"));
        }
        else if ((response.getAsciiId()).equals("banker_1_reply_3"))
        {
            string_id message = new string_id(NEWBIE_CONVO, "banker_2_explain_bank");
            npcSpeak(player, message);
            doAnimationAction(self, "face_innocent");
            npcAddConversationResponse(player, new string_id(NEWBIE_CONVO, "banker_bank_reply_1"));
        }
        else if ((response.getAsciiId()).equals("banker_bank_reply_1"))
        {
            string_id message = new string_id(NEWBIE_CONVO, "banker_2_more_bank");
            npcSpeak(player, message);
            doAnimationAction(self, "whisper");
            npcAddConversationResponse(player, new string_id(NEWBIE_CONVO, "banker_2_bank_question"));
        }
        else if ((response.getAsciiId()).equals("banker_2_bank_question"))
        {
            string_id message = new string_id(NEWBIE_CONVO, "banker_2_bank_answer");
            npcSpeak(player, message);
            doAnimationAction(self, "face_eye_roll");
            npcAddConversationResponse(player, new string_id(NEWBIE_CONVO, "banker_bank_question_2"));
        }
        else if ((response.getAsciiId()).equals("banker_bank_question_2"))
        {
            string_id message = new string_id(NEWBIE_CONVO, "banker_bank_answer_2");
            npcSpeak(player, message);
            doAnimationAction(self, "wave_finger_warning");
        }
        return SCRIPT_CONTINUE;
    }
    public int handleInitiateClonePrompt(obj_id self, dictionary params) throws InterruptedException
    {
        location destLoc = new location(getLocation(self));
        destLoc.x = CLONE_PROMPT_LOCATION_X;
        destLoc.z = CLONE_PROMPT_LOCATION_Z;
        destLoc.cell = getCellId(getTopMostContainer(self), CLONE_PROMPT_LOCATION_CELL);
        ai_lib.aiStopFollowing(self);
        pathTo(self, destLoc);
        messageTo(self, "handleExplainTerminalOne", null, 8, false);
        return SCRIPT_CONTINUE;
    }
    public int OnMovePathComplete(obj_id self) throws InterruptedException
    {
        if (!isInRoom(getPlayer(self), "r5"))
        {
            return SCRIPT_CONTINUE;
        }
        faceToBehavior(self, getPlayer(self));
        messageTo(self, "handleCorrectFacing", null, 2, false);
        return SCRIPT_CONTINUE;
    }
    public int handleCorrectFacing(obj_id self, dictionary params) throws InterruptedException
    {
        if (!isInRoom(getPlayer(self), "r5"))
        {
            return SCRIPT_CONTINUE;
        }
        faceToBehavior(self, getPlayer(self));
        return SCRIPT_CONTINUE;
    }
    public int handleExplainTerminalOne(obj_id self, dictionary params) throws InterruptedException
    {
        if (!isInRoom(getPlayer(self), "r5"))
        {
            return SCRIPT_CONTINUE;
        }
        chat.chat(self, new string_id(NEWBIE_CONVO, "convo_3_explain_terminal_1"));
        messageTo(self, "handleExplainTerminalTwo", null, 5, false);
        return SCRIPT_CONTINUE;
    }
    public int handleExplainTerminalTwo(obj_id self, dictionary params) throws InterruptedException
    {
        if (!isInRoom(getPlayer(self), "r5"))
        {
            return SCRIPT_CONTINUE;
        }
        chat.chat(self, new string_id(NEWBIE_CONVO, "convo_3_explain_terminal_2"));
        return SCRIPT_CONTINUE;
    }
}
