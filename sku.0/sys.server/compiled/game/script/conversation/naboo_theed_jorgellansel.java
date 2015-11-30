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

public class naboo_theed_jorgellansel extends script.base_script
{
    public naboo_theed_jorgellansel()
    {
    }
    public static String c_stringFile = "conversation/naboo_theed_jorgellansel";
    public boolean naboo_theed_jorgellansel_condition__defaultCondition(obj_id player, obj_id npc) throws InterruptedException
    {
        return true;
    }
    public boolean naboo_theed_jorgellansel_condition_startDarkwalkerQuest(obj_id player, obj_id npc) throws InterruptedException
    {
        return groundquests.isQuestActive(player, "naboo_theed_goto_jorgellansel") || groundquests.hasCompletedQuest(player, "naboo_theed_hugo_borvo");
    }
    public boolean naboo_theed_jorgellansel_condition_onDarkwalkerQuest(obj_id player, obj_id npc) throws InterruptedException
    {
        return groundquests.isQuestActive(player, "naboo_theed_hugo_darkwalker");
    }
    public boolean naboo_theed_jorgellansel_condition_completedDarkwalkerQuest(obj_id player, obj_id npc) throws InterruptedException
    {
        return groundquests.hasCompletedQuest(player, "naboo_theed_hugo_darkwalker");
    }
    public boolean naboo_theed_jorgellansel_condition_lastStepDarkwalkerQuest(obj_id player, obj_id npc) throws InterruptedException
    {
        return groundquests.isTaskActive(player, "naboo_theed_hugo_darkwalker", "theed_hugo_darkwalker_05");
    }
    public boolean naboo_theed_jorgellansel_condition_reGiveHugoPointer(obj_id player, obj_id npc) throws InterruptedException
    {
        return !groundquests.isQuestActive(player, "naboo_theed_hugo_return") && (!groundquests.isQuestActiveOrComplete(player, "naboo_theed_hugo_rescue") && !groundquests.isQuestActiveOrComplete(player, "naboo_theed_hugo_rescue_skaak") && !groundquests.isQuestActiveOrComplete(player, "naboo_theed_hugo_rescue_darkwalker"));
    }
    public void naboo_theed_jorgellansel_action_grantDarkwalker(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.requestGrantQuest(player, "naboo_theed_hugo_darkwalker");
    }
    public void naboo_theed_jorgellansel_action_signal_darkwalker_05(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.sendSignal(player, "theed_hugo_darkwalker_05");
    }
    public void naboo_theed_jorgellansel_action_endGotoJorgellansel(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.sendSignal(player, "theed_goto_jorgellansel");
    }
    public void naboo_theed_jorgellansel_action_sendBackToHugo(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.requestGrantQuest(player, "naboo_theed_hugo_return");
        return;
    }
    public int naboo_theed_jorgellansel_handleBranch1(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_14"))
        {
            if (naboo_theed_jorgellansel_condition__defaultCondition(player, npc))
            {
                naboo_theed_jorgellansel_action_sendBackToHugo(player, npc);
                string_id message = new string_id(c_stringFile, "s_15");
                utils.removeScriptVar(player, "conversation.naboo_theed_jorgellansel.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int naboo_theed_jorgellansel_handleBranch3(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_74"))
        {
            if (naboo_theed_jorgellansel_condition__defaultCondition(player, npc))
            {
                naboo_theed_jorgellansel_action_sendBackToHugo(player, npc);
                string_id message = new string_id(c_stringFile, "s_75");
                utils.removeScriptVar(player, "conversation.naboo_theed_jorgellansel.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int naboo_theed_jorgellansel_handleBranch6(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_39"))
        {
            if (naboo_theed_jorgellansel_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_40");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (naboo_theed_jorgellansel_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_24");
                    }
                    utils.setScriptVar(player, "conversation.naboo_theed_jorgellansel.branchId", 7);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.naboo_theed_jorgellansel.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int naboo_theed_jorgellansel_handleBranch7(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_24"))
        {
            if (naboo_theed_jorgellansel_condition__defaultCondition(player, npc))
            {
                naboo_theed_jorgellansel_action_grantDarkwalker(player, npc);
                string_id message = new string_id(c_stringFile, "s_25");
                utils.removeScriptVar(player, "conversation.naboo_theed_jorgellansel.branchId");
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
            detachScript(self, "conversation.naboo_theed_jorgellansel");
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
        detachScript(self, "conversation.naboo_theed_jorgellansel");
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
        if (naboo_theed_jorgellansel_condition_completedDarkwalkerQuest(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_37");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (naboo_theed_jorgellansel_condition_reGiveHugoPointer(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_14");
                }
                utils.setScriptVar(player, "conversation.naboo_theed_jorgellansel.branchId", 1);
                npcStartConversation(player, npc, "naboo_theed_jorgellansel", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (naboo_theed_jorgellansel_condition_lastStepDarkwalkerQuest(player, npc))
        {
            naboo_theed_jorgellansel_action_signal_darkwalker_05(player, npc);
            string_id message = new string_id(c_stringFile, "s_73");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (naboo_theed_jorgellansel_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_74");
                }
                utils.setScriptVar(player, "conversation.naboo_theed_jorgellansel.branchId", 3);
                npcStartConversation(player, npc, "naboo_theed_jorgellansel", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (naboo_theed_jorgellansel_condition_onDarkwalkerQuest(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_36");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        if (naboo_theed_jorgellansel_condition_startDarkwalkerQuest(player, npc))
        {
            naboo_theed_jorgellansel_action_endGotoJorgellansel(player, npc);
            string_id message = new string_id(c_stringFile, "s_38");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (naboo_theed_jorgellansel_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_39");
                }
                utils.setScriptVar(player, "conversation.naboo_theed_jorgellansel.branchId", 6);
                npcStartConversation(player, npc, "naboo_theed_jorgellansel", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (naboo_theed_jorgellansel_condition__defaultCondition(player, npc))
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
        if (!conversationId.equals("naboo_theed_jorgellansel"))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id npc = self;
        int branchId = utils.getIntScriptVar(player, "conversation.naboo_theed_jorgellansel.branchId");
        if (branchId == 1 && naboo_theed_jorgellansel_handleBranch1(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 3 && naboo_theed_jorgellansel_handleBranch3(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 6 && naboo_theed_jorgellansel_handleBranch6(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 7 && naboo_theed_jorgellansel_handleBranch7(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        chat.chat(npc, "Error:  Fell through all branches and responses for OnNpcConversationResponse.");
        utils.removeScriptVar(player, "conversation.naboo_theed_jorgellansel.branchId");
        return SCRIPT_CONTINUE;
    }
}
