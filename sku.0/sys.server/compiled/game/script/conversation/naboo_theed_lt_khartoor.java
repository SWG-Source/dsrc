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

public class naboo_theed_lt_khartoor extends script.base_script
{
    public naboo_theed_lt_khartoor()
    {
    }
    public static String c_stringFile = "conversation/naboo_theed_lt_khartoor";
    public boolean naboo_theed_lt_khartoor_condition__defaultCondition(obj_id player, obj_id npc) throws InterruptedException
    {
        return true;
    }
    public boolean naboo_theed_lt_khartoor_condition_startMeanwhileQuest(obj_id player, obj_id npc) throws InterruptedException
    {
        return groundquests.isQuestActive(player, "naboo_theed_goto_khartoor") || (groundquests.hasCompletedQuest(player, "naboo_theed_terrorist_terrorize") && (groundquests.hasCompletedQuest(player, "naboo_theed_hugo_rescue") || groundquests.hasCompletedQuest(player, "naboo_theed_hugo_rescue_skaak") || groundquests.hasCompletedQuest(player, "naboo_theed_hugo_rescue_darkwalker")));
    }
    public boolean naboo_theed_lt_khartoor_condition_onAssociatesQuest(obj_id player, obj_id npc) throws InterruptedException
    {
        return groundquests.isQuestActive(player, "naboo_theed_meanwhile_associates");
    }
    public boolean naboo_theed_lt_khartoor_condition_finishedAssociatesQuest(obj_id player, obj_id npc) throws InterruptedException
    {
        return groundquests.isTaskActive(player, "naboo_theed_meanwhile_associates", "theed_meanwhile_associates_07") || groundquests.hasCompletedQuest(player, "naboo_theed_meanwhile_associates");
    }
    public boolean naboo_theed_lt_khartoor_condition_onChaseQuest(obj_id player, obj_id npc) throws InterruptedException
    {
        return groundquests.isQuestActive(player, "naboo_theed_meanwhile_chase");
    }
    public boolean naboo_theed_lt_khartoor_condition_finishedChaseQuest(obj_id player, obj_id npc) throws InterruptedException
    {
        return groundquests.isTaskActive(player, "naboo_theed_meanwhile_chase", "theed_meanwhile_chase_09") || groundquests.hasCompletedQuest(player, "naboo_theed_meanwhile_chase");
    }
    public boolean naboo_theed_lt_khartoor_condition_onRanchQuest(obj_id player, obj_id npc) throws InterruptedException
    {
        return groundquests.isQuestActive(player, "naboo_theed_meanwhile_ranch");
    }
    public boolean naboo_theed_lt_khartoor_condition_lastStepRanchQuest(obj_id player, obj_id npc) throws InterruptedException
    {
        return groundquests.isTaskActive(player, "naboo_theed_meanwhile_ranch", "theed_meanwhile_ranch_05");
    }
    public boolean naboo_theed_lt_khartoor_condition_completedMeanwhileQuest(obj_id player, obj_id npc) throws InterruptedException
    {
        return groundquests.isTaskActive(player, "naboo_theed_meanwhile_ranch", "theed_meanwhile_ranch_05b") || groundquests.isTaskActive(player, "naboo_theed_meanwhile_ranch", "theed_meanwhile_ranch_06") || groundquests.hasCompletedQuest(player, "naboo_theed_meanwhile_ranch");
    }
    public void naboo_theed_lt_khartoor_action_grantAssociatesQuest(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.requestGrantQuest(player, "naboo_theed_meanwhile_associates");
    }
    public void naboo_theed_lt_khartoor_action_grantChaseQuest(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.requestGrantQuest(player, "naboo_theed_meanwhile_chase");
    }
    public void naboo_theed_lt_khartoor_action_grantRanchQuest(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.requestGrantQuest(player, "naboo_theed_meanwhile_ranch");
    }
    public void naboo_theed_lt_khartoor_action_signal_ranch_05(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.sendSignal(player, "theed_meanwhile_ranch_05");
    }
    public void naboo_theed_lt_khartoor_action_endGotoKhartoor(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.sendSignal(player, "theed_goto_khartoor");
    }
    public void naboo_theed_lt_khartoor_action_endAssociatesQuest(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.sendSignal(player, "theed_meanwhile_associates_07");
    }
    public void naboo_theed_lt_khartoor_action_endChaseQuest(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.sendSignal(player, "theed_meanwhile_chase_09");
    }
    public int naboo_theed_lt_khartoor_handleBranch2(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_58"))
        {
            if (naboo_theed_lt_khartoor_condition__defaultCondition(player, npc))
            {
                naboo_theed_lt_khartoor_action_signal_ranch_05(player, npc);
                string_id message = new string_id(c_stringFile, "s_59");
                utils.removeScriptVar(player, "conversation.naboo_theed_lt_khartoor.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int naboo_theed_lt_khartoor_handleBranch5(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_53"))
        {
            if (naboo_theed_lt_khartoor_condition__defaultCondition(player, npc))
            {
                naboo_theed_lt_khartoor_action_grantRanchQuest(player, npc);
                string_id message = new string_id(c_stringFile, "s_54");
                utils.removeScriptVar(player, "conversation.naboo_theed_lt_khartoor.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int naboo_theed_lt_khartoor_handleBranch8(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_49"))
        {
            if (naboo_theed_lt_khartoor_condition__defaultCondition(player, npc))
            {
                naboo_theed_lt_khartoor_action_grantChaseQuest(player, npc);
                string_id message = new string_id(c_stringFile, "s_50");
                utils.removeScriptVar(player, "conversation.naboo_theed_lt_khartoor.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int naboo_theed_lt_khartoor_handleBranch11(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_45"))
        {
            if (naboo_theed_lt_khartoor_condition__defaultCondition(player, npc))
            {
                naboo_theed_lt_khartoor_action_grantAssociatesQuest(player, npc);
                string_id message = new string_id(c_stringFile, "s_46");
                utils.removeScriptVar(player, "conversation.naboo_theed_lt_khartoor.branchId");
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
            detachScript(self, "conversation.naboo_theed_lt_khartoor");
        }
        setCondition(self, CONDITION_CONVERSABLE);
        setCondition(self, CONDITION_INTERESTING);
        setInvulnerable(self, true);
        return SCRIPT_CONTINUE;
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        setCondition(self, CONDITION_CONVERSABLE);
        setCondition(self, CONDITION_INTERESTING);
        setInvulnerable(self, true);
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
        detachScript(self, "conversation.naboo_theed_lt_khartoor");
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
        if (naboo_theed_lt_khartoor_condition_completedMeanwhileQuest(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_57");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        if (naboo_theed_lt_khartoor_condition_lastStepRanchQuest(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_56");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (naboo_theed_lt_khartoor_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_58");
                }
                utils.setScriptVar(player, "conversation.naboo_theed_lt_khartoor.branchId", 2);
                npcStartConversation(player, npc, "naboo_theed_lt_khartoor", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (naboo_theed_lt_khartoor_condition_onRanchQuest(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_55");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        if (naboo_theed_lt_khartoor_condition_finishedChaseQuest(player, npc))
        {
            naboo_theed_lt_khartoor_action_endChaseQuest(player, npc);
            string_id message = new string_id(c_stringFile, "s_52");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (naboo_theed_lt_khartoor_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_53");
                }
                utils.setScriptVar(player, "conversation.naboo_theed_lt_khartoor.branchId", 5);
                npcStartConversation(player, npc, "naboo_theed_lt_khartoor", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (naboo_theed_lt_khartoor_condition_onChaseQuest(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_51");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        if (naboo_theed_lt_khartoor_condition_finishedAssociatesQuest(player, npc))
        {
            naboo_theed_lt_khartoor_action_endAssociatesQuest(player, npc);
            string_id message = new string_id(c_stringFile, "s_48");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (naboo_theed_lt_khartoor_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_49");
                }
                utils.setScriptVar(player, "conversation.naboo_theed_lt_khartoor.branchId", 8);
                npcStartConversation(player, npc, "naboo_theed_lt_khartoor", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (naboo_theed_lt_khartoor_condition_onAssociatesQuest(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_47");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        if (naboo_theed_lt_khartoor_condition_startMeanwhileQuest(player, npc))
        {
            naboo_theed_lt_khartoor_action_endGotoKhartoor(player, npc);
            string_id message = new string_id(c_stringFile, "s_44");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (naboo_theed_lt_khartoor_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_45");
                }
                utils.setScriptVar(player, "conversation.naboo_theed_lt_khartoor.branchId", 11);
                npcStartConversation(player, npc, "naboo_theed_lt_khartoor", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (naboo_theed_lt_khartoor_condition__defaultCondition(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_43");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        chat.chat(npc, "Error:  All conditions for OnStartNpcConversation were false.");
        return SCRIPT_CONTINUE;
    }
    public int OnNpcConversationResponse(obj_id self, String conversationId, obj_id player, string_id response) throws InterruptedException
    {
        if (!conversationId.equals("naboo_theed_lt_khartoor"))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id npc = self;
        int branchId = utils.getIntScriptVar(player, "conversation.naboo_theed_lt_khartoor.branchId");
        if (branchId == 2 && naboo_theed_lt_khartoor_handleBranch2(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 5 && naboo_theed_lt_khartoor_handleBranch5(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 8 && naboo_theed_lt_khartoor_handleBranch8(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 11 && naboo_theed_lt_khartoor_handleBranch11(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        chat.chat(npc, "Error:  Fell through all branches and responses for OnNpcConversationResponse.");
        utils.removeScriptVar(player, "conversation.naboo_theed_lt_khartoor.branchId");
        return SCRIPT_CONTINUE;
    }
}
