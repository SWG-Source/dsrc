package script.conversation;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.ai_lib;
import script.library.chat;
import script.library.groundquests;
import script.library.space_quest;
import script.library.utils;

public class rtp_ackbar_rebel_sympathizer extends script.base_script
{
    public rtp_ackbar_rebel_sympathizer()
    {
    }
    public static String c_stringFile = "conversation/rtp_ackbar_rebel_sympathizer";
    public boolean rtp_ackbar_rebel_sympathizer_condition__defaultCondition(obj_id player, obj_id npc) throws InterruptedException
    {
        return true;
    }
    public boolean rtp_ackbar_rebel_sympathizer_condition_ackbarTaskActive(obj_id player, obj_id npc) throws InterruptedException
    {
        return groundquests.isTaskActive(player, "rtp_ackbar_01", "rtp_ackbar_01_01");
    }
    public void rtp_ackbar_rebel_sympathizer_action_attack(obj_id player, obj_id npc) throws InterruptedException
    {
        setInvulnerable(npc, false);
        startCombat(npc, player);
        clearCondition(npc, CONDITION_CONVERSABLE);
        detachScript(npc, "conversation.rtp_ackbar_rebel_sympathizer");
    }
    public int rtp_ackbar_rebel_sympathizer_handleBranch1(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_5"))
        {
            if (rtp_ackbar_rebel_sympathizer_condition__defaultCondition(player, npc))
            {
                rtp_ackbar_rebel_sympathizer_action_attack(player, npc);
                string_id message = new string_id(c_stringFile, "s_6");
                utils.removeScriptVar(player, "conversation.rtp_ackbar_rebel_sympathizer.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        if ((!isMob(self)) || (isPlayer(self)))
        {
            detachScript(self, "conversation.rtp_ackbar_rebel_sympathizer");
        }
        setCondition(self, CONDITION_CONVERSABLE);
        return SCRIPT_CONTINUE;
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        setCondition(self, CONDITION_CONVERSABLE);
        return SCRIPT_CONTINUE;
    }
    public int OnObjectMenuRequest(obj_id self, obj_id player, menu_info menuInfo) throws InterruptedException
    {
        int menu = menuInfo.addRootMenu(menu_info_types.CONVERSE_START, null);
        menu_info_data menuInfoData = menuInfo.getMenuItemById(menu);
        menuInfoData.setServerNotify(false);
        faceTo(self, player);
        return SCRIPT_CONTINUE;
    }
    public int OnIncapacitated(obj_id self, obj_id killer) throws InterruptedException
    {
        clearCondition(self, CONDITION_CONVERSABLE);
        detachScript(self, "conversation.rtp_ackbar_rebel_sympathizer");
        return SCRIPT_CONTINUE;
    }
    public boolean npcStartConversation(obj_id player, obj_id npc, String convoName, string_id greetingId, prose_package greetingProse, string_id[] responses) throws InterruptedException
    {
        Object[] objects = new Object[responses.length];
        System.arraycopy(responses, 0, objects, 0, responses.length);
        return npcStartConversation(player, npc, convoName, greetingId, greetingProse, objects);
    }
    public int OnStartNpcConversation(obj_id self, obj_id player) throws InterruptedException
    {
        obj_id npc = self;
        if (ai_lib.isInCombat(npc) || ai_lib.isInCombat(player))
        {
            return SCRIPT_OVERRIDE;
        }
        if (rtp_ackbar_rebel_sympathizer_condition_ackbarTaskActive(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_32");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (rtp_ackbar_rebel_sympathizer_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            if (hasResponse)
            {
                int responseIndex = 0;
                string_id responses[] = new string_id[numberOfResponses];
                if (hasResponse0)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_5");
                }
                utils.setScriptVar(player, "conversation.rtp_ackbar_rebel_sympathizer.branchId", 1);
                npcStartConversation(player, npc, "rtp_ackbar_rebel_sympathizer", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (rtp_ackbar_rebel_sympathizer_condition__defaultCondition(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_34");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        chat.chat(npc, "Error:  All conditions for OnStartNpcConversation were false.");
        return SCRIPT_CONTINUE;
    }
    public int OnNpcConversationResponse(obj_id self, String conversationId, obj_id player, string_id response) throws InterruptedException
    {
        if (!conversationId.equals("rtp_ackbar_rebel_sympathizer"))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id npc = self;
        int branchId = utils.getIntScriptVar(player, "conversation.rtp_ackbar_rebel_sympathizer.branchId");
        if (branchId == 1 && rtp_ackbar_rebel_sympathizer_handleBranch1(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        chat.chat(npc, "Error:  Fell through all branches and responses for OnNpcConversationResponse.");
        utils.removeScriptVar(player, "conversation.rtp_ackbar_rebel_sympathizer.branchId");
        return SCRIPT_CONTINUE;
    }
}
