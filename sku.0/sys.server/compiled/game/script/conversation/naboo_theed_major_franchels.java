package script.conversation;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.ai_lib;
import script.library.badge;
import script.library.chat;
import script.library.groundquests;
import script.library.space_quest;
import script.library.utils;

public class naboo_theed_major_franchels extends script.base_script
{
    public naboo_theed_major_franchels()
    {
    }
    public static String c_stringFile = "conversation/naboo_theed_major_franchels";
    public boolean naboo_theed_major_franchels_condition__defaultCondition(obj_id player, obj_id npc) throws InterruptedException
    {
        return true;
    }
    public boolean naboo_theed_major_franchels_condition_completedAftermathQuest(obj_id player, obj_id npc) throws InterruptedException
    {
        return groundquests.hasCompletedQuest(player, "naboo_theed_terrorist_aftermath");
    }
    public boolean naboo_theed_major_franchels_condition_onAftermathQuest(obj_id player, obj_id npc) throws InterruptedException
    {
        return groundquests.isQuestActive(player, "naboo_theed_terrorist_aftermath");
    }
    public boolean naboo_theed_major_franchels_condition_lastStepAftermathQuest(obj_id player, obj_id npc) throws InterruptedException
    {
        return groundquests.isTaskActive(player, "naboo_theed_terrorist_aftermath", "theed_terrorist_aftermath_07");
    }
    public boolean naboo_theed_major_franchels_condition_needsResponsibleQuest(obj_id player, obj_id npc) throws InterruptedException
    {
        return !groundquests.isQuestActive(player, "naboo_theed_goto_bragg") && !groundquests.isQuestActive(player, "naboo_theed_terrorist_responsible") && !groundquests.hasCompletedQuest(player, "naboo_theed_terrorist_responsible");
    }
    public boolean naboo_theed_major_franchels_condition_readyForAftermathQuest(obj_id player, obj_id npc) throws InterruptedException
    {
        return badge.hasBadge(player, "bdg_content_rsf_clearance_4");
    }
    public void naboo_theed_major_franchels_action_grantAftermath(obj_id player, obj_id npc) throws InterruptedException
    {
        if (groundquests.isQuestActive(player, "naboo_theed_goto_pooja"))
        {
            groundquests.sendSignal(player, "theed_goto_pooja");
        }
        groundquests.requestGrantQuest(player, "naboo_theed_terrorist_aftermath");
        return;
    }
    public void naboo_theed_major_franchels_action_sendToSgtBragg(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.requestGrantQuest(player, "naboo_theed_goto_bragg");
    }
    public void naboo_theed_major_franchels_action_endGotoFranchels(obj_id player, obj_id npc) throws InterruptedException
    {
        if (groundquests.isQuestActive(player, "naboo_theed_goto_franchels"))
        {
            groundquests.sendSignal(player, "theed_goto_franchels");
        }
        return;
    }
    public void naboo_theed_major_franchels_action_endAftermath(obj_id player, obj_id npc) throws InterruptedException
    {
        if (groundquests.isTaskActive(player, "naboo_theed_terrorist_aftermath", "theed_terrorist_aftermath_07"))
        {
            groundquests.sendSignal(player, "theed_terrorist_aftermath_07");
        }
        return;
    }
    public int naboo_theed_major_franchels_handleBranch1(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_12"))
        {
            if (naboo_theed_major_franchels_condition__defaultCondition(player, npc))
            {
                naboo_theed_major_franchels_action_sendToSgtBragg(player, npc);
                string_id message = new string_id(c_stringFile, "s_11");
                utils.removeScriptVar(player, "conversation.naboo_theed_major_franchels.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int naboo_theed_major_franchels_handleBranch2(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_10"))
        {
            if (naboo_theed_major_franchels_condition__defaultCondition(player, npc))
            {
                naboo_theed_major_franchels_action_sendToSgtBragg(player, npc);
                string_id message = new string_id(c_stringFile, "s_11");
                utils.removeScriptVar(player, "conversation.naboo_theed_major_franchels.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_19"))
        {
            if (naboo_theed_major_franchels_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_20");
                utils.removeScriptVar(player, "conversation.naboo_theed_major_franchels.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int naboo_theed_major_franchels_handleBranch6(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_30"))
        {
            if (naboo_theed_major_franchels_condition__defaultCondition(player, npc))
            {
                naboo_theed_major_franchels_action_grantAftermath(player, npc);
                string_id message = new string_id(c_stringFile, "s_33");
                utils.removeScriptVar(player, "conversation.naboo_theed_major_franchels.branchId");
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
            detachScript(self, "conversation.naboo_theed_major_franchels");
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
        detachScript(self, "conversation.naboo_theed_major_franchels");
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
        if (naboo_theed_major_franchels_condition_completedAftermathQuest(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_35");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (naboo_theed_major_franchels_condition_needsResponsibleQuest(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_12");
                }
                utils.setScriptVar(player, "conversation.naboo_theed_major_franchels.branchId", 1);
                npcStartConversation(player, npc, "naboo_theed_major_franchels", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (naboo_theed_major_franchels_condition_lastStepAftermathQuest(player, npc))
        {
            naboo_theed_major_franchels_action_endAftermath(player, npc);
            string_id message = new string_id(c_stringFile, "s_9");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (naboo_theed_major_franchels_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            boolean hasResponse1 = false;
            if (naboo_theed_major_franchels_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse1 = true;
            }
            if (hasResponse)
            {
                int responseIndex = 0;
                string_id responses[] = new string_id[numberOfResponses];
                if (hasResponse0)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_10");
                }
                if (hasResponse1)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_19");
                }
                utils.setScriptVar(player, "conversation.naboo_theed_major_franchels.branchId", 2);
                npcStartConversation(player, npc, "naboo_theed_major_franchels", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (naboo_theed_major_franchels_condition_onAftermathQuest(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_28");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        if (naboo_theed_major_franchels_condition_readyForAftermathQuest(player, npc))
        {
            naboo_theed_major_franchels_action_endGotoFranchels(player, npc);
            string_id message = new string_id(c_stringFile, "s_29");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (naboo_theed_major_franchels_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_30");
                }
                utils.setScriptVar(player, "conversation.naboo_theed_major_franchels.branchId", 6);
                npcStartConversation(player, npc, "naboo_theed_major_franchels", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (naboo_theed_major_franchels_condition__defaultCondition(player, npc))
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
        if (!conversationId.equals("naboo_theed_major_franchels"))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id npc = self;
        int branchId = utils.getIntScriptVar(player, "conversation.naboo_theed_major_franchels.branchId");
        if (branchId == 1 && naboo_theed_major_franchels_handleBranch1(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 2 && naboo_theed_major_franchels_handleBranch2(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 6 && naboo_theed_major_franchels_handleBranch6(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        chat.chat(npc, "Error:  Fell through all branches and responses for OnNpcConversationResponse.");
        utils.removeScriptVar(player, "conversation.naboo_theed_major_franchels.branchId");
        return SCRIPT_CONTINUE;
    }
}
