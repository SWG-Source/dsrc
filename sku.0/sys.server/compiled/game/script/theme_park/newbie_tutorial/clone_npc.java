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

public class clone_npc extends script.theme_park.newbie_tutorial.tutorial_base
{
    public clone_npc()
    {
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        createTriggerVolume("playerEntered", 10.0f, true);
        setYaw(self, 91.0f);
        return SCRIPT_CONTINUE;
    }
    public int handleInitiateConvo(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id player = getPlayer(self);
        faceToBehavior(self, player);
        return SCRIPT_CONTINUE;
    }
    public int OnTriggerVolumeEntered(obj_id self, String volumeName, obj_id breacher) throws InterruptedException
    {
        if (hasObjVar(self, "newbie.greeted"))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id player = getPlayer(self);
        if (player != breacher)
        {
            return SCRIPT_CONTINUE;
        }
        if (volumeName.equals("playerEntered"))
        {
            ai_lib.aiStopFollowing(self);
            stop(self);
            faceToBehavior(self, player);
            doAnimationAction(self, "wave_hail");
            chat.chat(self, new string_id(NEWBIE_CONVO, "clone_greeting"));
            setObjVar(self, "newbie.greeted", true);
        }
        return SCRIPT_CONTINUE;
    }
    public int OnObjectMenuRequest(obj_id self, obj_id player, menu_info mi) throws InterruptedException
    {
        int mnu = mi.addRootMenu(menu_info_types.CONVERSE_START, null);
        menu_info_data mdata = mi.getMenuItemById(mnu);
        mdata.setServerNotify(false);
        return SCRIPT_CONTINUE;
    }
    public int OnStartNpcConversation(obj_id self, obj_id speaker) throws InterruptedException
    {
        if (hasObjVar(self, "newbie.noTalking"))
        {
            return SCRIPT_CONTINUE;
        }
        faceToBehavior(self, speaker);
        string_id greeting = new string_id(NEWBIE_CONVO, "convo_2_start");
        string_id response[] = new string_id[2];
        response[0] = new string_id(NEWBIE_CONVO, "convo_2_reply_1");
        response[1] = new string_id(NEWBIE_CONVO, "convo_2_reply_2");
        npcStartConversation(speaker, self, CONVO, greeting, response);
        return SCRIPT_CONTINUE;
    }
    public int OnNpcConversationResponse(obj_id self, String convo, obj_id player, string_id response) throws InterruptedException
    {
        npcRemoveConversationResponse(player, response);
        if ((response.getAsciiId()).equals("convo_2_reply_1"))
        {
            string_id message = new string_id(NEWBIE_CONVO, "convo_2_cloning");
            npcSpeak(player, message);
            npcAddConversationResponse(player, new string_id(NEWBIE_CONVO, "convo_2_reply_3"));
        }
        else if ((response.getAsciiId()).equals("convo_2_reply_2"))
        {
            string_id message = new string_id(NEWBIE_CONVO, "convo_2_insurance");
            npcSpeak(player, message);
            npcAddConversationResponse(player, new string_id(NEWBIE_CONVO, "convo_2_reply_4"));
        }
        else if ((response.getAsciiId()).equals("convo_2_reply_3"))
        {
            string_id message = new string_id(NEWBIE_CONVO, "convo_2_cloning_2");
            npcSpeak(player, message);
            npcAddConversationResponse(player, new string_id(NEWBIE_CONVO, "convo_2_reply_5"));
        }
        else if ((response.getAsciiId()).equals("convo_2_reply_4"))
        {
            string_id message = new string_id(NEWBIE_CONVO, "convo_2_insurance_2");
            npcSpeak(player, message);
            npcAddConversationResponse(player, new string_id(NEWBIE_CONVO, "convo_2_reply_8"));
        }
        else if ((response.getAsciiId()).equals("convo_2_reply_5"))
        {
            string_id message = new string_id(NEWBIE_CONVO, "convo_2_cloning_3");
            npcSpeak(player, message);
        }
        else if ((response.getAsciiId()).equals("convo_2_reply_6"))
        {
            npcRemoveConversationResponse(player, new string_id(NEWBIE_CONVO, "convo_2_reply_7"));
            string_id message = new string_id(NEWBIE_CONVO, "convo_2_insurance_3");
            npcSpeak(player, message);
        }
        else if ((response.getAsciiId()).equals("convo_2_reply_7"))
        {
            npcRemoveConversationResponse(player, new string_id(NEWBIE_CONVO, "convo_2_reply_6"));
            string_id message = new string_id(NEWBIE_CONVO, "convo_2_insurance_4");
            npcSpeak(player, message);
        }
        else if ((response.getAsciiId()).equals("convo_2_reply_8"))
        {
            string_id message = new string_id(NEWBIE_CONVO, "convo_2_insurance_5");
            npcSpeak(player, message);
        }
        return SCRIPT_CONTINUE;
    }
    public int handleInitiateClonePrompt(obj_id self, dictionary params) throws InterruptedException
    {
        location destLoc = new location(getLocation(self));
        destLoc.x = CLONE_PROMPT_LOCATION_X;
        destLoc.z = CLONE_PROMPT_LOCATION_Z;
        destLoc.cell = getCellId(getTopMostContainer(self), CLONE_PROMPT_LOCATION_CELL);
        setMovementWalk(self);
        ai_lib.aiStopFollowing(self);
        pathTo(self, destLoc);
        setHomeLocation(self, destLoc);
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
        obj_id player = getPlayer(self);
        if (!isInRoom(player, "r5"))
        {
            return SCRIPT_CONTINUE;
        }
        setObjVar(self, "newbie.noTalking", true);
        chat.chat(self, new string_id(NEWBIE_CONVO, "convo_3_explain_terminal_1"));
        messageTo(self, "handleExplainTerminalTwo", null, 5, false);
        return SCRIPT_CONTINUE;
    }
    public int handleExplainTerminalTwo(obj_id self, dictionary params) throws InterruptedException
    {
        messageTo(self, "handleDoneTalking", null, 5, false);
        if (!isInRoom(getPlayer(self), "r5"))
        {
            return SCRIPT_CONTINUE;
        }
        chat.chat(self, new string_id(NEWBIE_CONVO, "convo_3_explain_terminal_2"));
        return SCRIPT_CONTINUE;
    }
    public int handleDoneTalking(obj_id self, dictionary params) throws InterruptedException
    {
        removeObjVar(self, "newbie.noTalking");
        return SCRIPT_CONTINUE;
    }
}
