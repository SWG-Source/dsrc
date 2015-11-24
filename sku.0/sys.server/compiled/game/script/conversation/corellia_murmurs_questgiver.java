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
import script.library.utils;

public class corellia_murmurs_questgiver extends script.base_script
{
    public corellia_murmurs_questgiver()
    {
    }
    public static String c_stringFile = "conversation/corellia_murmurs_questgiver";
    public boolean corellia_murmurs_questgiver_condition__defaultCondition(obj_id player, obj_id npc) throws InterruptedException
    {
        return true;
    }
    public boolean corellia_murmurs_questgiver_condition_playerCompletedInitialStep(obj_id player, obj_id npc) throws InterruptedException
    {
        return groundquests.isQuestActiveOrComplete(player, "coronet_murmurs_1_smuggler");
    }
    public boolean corellia_murmurs_questgiver_condition_hasPointer(obj_id player, obj_id npc) throws InterruptedException
    {
        return groundquests.isQuestActive(player, "corellia_cb_1_pointer") || groundquests.isQuestActiveOrComplete(player, "corellia_cb_1");
    }
    public boolean corellia_murmurs_questgiver_condition_playerDeletedQ2(obj_id player, obj_id npc) throws InterruptedException
    {
        return (groundquests.hasCompletedQuest(player, "coronet_murmurs_1_smuggler") && !groundquests.isQuestActiveOrComplete(player, "coronet_murmurs_2_gatherinfo"));
    }
    public boolean corellia_murmurs_questgiver_condition_playerDeletedQ3(obj_id player, obj_id npc) throws InterruptedException
    {
        return (groundquests.hasCompletedQuest(player, "coronet_murmurs_2_gatherinfo") && !groundquests.isQuestActiveOrComplete(player, "coronet_murmurs_3_miniboss"));
    }
    public boolean corellia_murmurs_questgiver_condition_finishedAll(obj_id player, obj_id npc) throws InterruptedException
    {
        return groundquests.hasCompletedQuest(player, "coronet_murmurs_7_reward");
    }
    public boolean corellia_murmurs_questgiver_condition_playerDeletedQ4(obj_id player, obj_id npc) throws InterruptedException
    {
        return (groundquests.hasCompletedQuest(player, "coronet_murmurs_3_miniboss") && !groundquests.isQuestActiveOrComplete(player, "coronet_murmurs_4_lab"));
    }
    public boolean corellia_murmurs_questgiver_condition_playerDeletedQ5(obj_id player, obj_id npc) throws InterruptedException
    {
        return (groundquests.hasCompletedQuest(player, "coronet_murmurs_4_lab") && !groundquests.isQuestActiveOrComplete(player, "coronet_murmurs_5_gas"));
    }
    public boolean corellia_murmurs_questgiver_condition_playerDeletedQ6(obj_id player, obj_id npc) throws InterruptedException
    {
        return (groundquests.hasCompletedQuest(player, "coronet_murmurs_5_gas") && !groundquests.isQuestActiveOrComplete(player, "coronet_murmurs_6_zoo"));
    }
    public boolean corellia_murmurs_questgiver_condition_playerOnFinalStep(obj_id player, obj_id npc) throws InterruptedException
    {
        return groundquests.isTaskActive(player, "coronet_murmurs_7_reward", "returntojoth");
    }
    public boolean corellia_murmurs_questgiver_condition_playerDeletedQ7(obj_id player, obj_id npc) throws InterruptedException
    {
        return (groundquests.hasCompletedQuest(player, "coronet_murmurs_6_zoo") && !groundquests.isQuestActiveOrComplete(player, "coronet_murmurs_7_reward"));
    }
    public void corellia_murmurs_questgiver_action_giveFirstQuest(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.grantQuest(player, "coronet_murmurs_1_smuggler");
    }
    public void corellia_murmurs_questgiver_action_giveSecondQuest(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.grantQuest(player, "coronet_murmurs_2_gatherinfo");
    }
    public void corellia_murmurs_questgiver_action_giveThirdQuest(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.grantQuest(player, "coronet_murmurs_3_miniboss");
    }
    public void corellia_murmurs_questgiver_action_giveFourthQuest(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.grantQuest(player, "coronet_murmurs_4_lab");
    }
    public void corellia_murmurs_questgiver_action_giveNextSeriesPointer(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.grantQuest(player, "corellia_cb_1_pointer");
    }
    public void corellia_murmurs_questgiver_action_giveFifthQuest(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.grantQuest(player, "coronet_murmurs_5_gas");
    }
    public void corellia_murmurs_questgiver_action_giveSixthQuest(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.grantQuest(player, "coronet_murmurs_6_zoo");
    }
    public void corellia_murmurs_questgiver_action_signalRewardQuest(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.sendSignal(player, "coronet_murmurs_7_reward_signal");
    }
    public void corellia_murmurs_questgiver_action_giveSeventhQuest(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.grantQuest(player, "coronet_murmurs_7_reward");
    }
    public void corellia_murmurs_questgiver_action_signalGotoQuest(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.sendSignal(player, "goto_talktojoth");
    }
    public int corellia_murmurs_questgiver_handleBranch1(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_28"))
        {
            if (corellia_murmurs_questgiver_condition__defaultCondition(player, npc))
            {
                corellia_murmurs_questgiver_action_giveNextSeriesPointer(player, npc);
                string_id message = new string_id(c_stringFile, "s_29");
                utils.removeScriptVar(player, "conversation.corellia_murmurs_questgiver.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int corellia_murmurs_questgiver_handleBranch3(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_41"))
        {
            if (corellia_murmurs_questgiver_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "salute1");
                corellia_murmurs_questgiver_action_signalRewardQuest(player, npc);
                string_id message = new string_id(c_stringFile, "s_42");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (!corellia_murmurs_questgiver_condition_hasPointer(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_28");
                    }
                    utils.setScriptVar(player, "conversation.corellia_murmurs_questgiver.branchId", 1);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.corellia_murmurs_questgiver.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int corellia_murmurs_questgiver_handleBranch4(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_28"))
        {
            if (corellia_murmurs_questgiver_condition__defaultCondition(player, npc))
            {
                corellia_murmurs_questgiver_action_giveNextSeriesPointer(player, npc);
                string_id message = new string_id(c_stringFile, "s_29");
                utils.removeScriptVar(player, "conversation.corellia_murmurs_questgiver.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int corellia_murmurs_questgiver_handleBranch5(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_23"))
        {
            if (corellia_murmurs_questgiver_condition_playerDeletedQ2(player, npc))
            {
                corellia_murmurs_questgiver_action_giveSecondQuest(player, npc);
                string_id message = new string_id(c_stringFile, "s_25");
                utils.removeScriptVar(player, "conversation.corellia_murmurs_questgiver.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
            if (corellia_murmurs_questgiver_condition_playerDeletedQ3(player, npc))
            {
                corellia_murmurs_questgiver_action_giveThirdQuest(player, npc);
                string_id message = new string_id(c_stringFile, "s_27");
                utils.removeScriptVar(player, "conversation.corellia_murmurs_questgiver.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
            if (corellia_murmurs_questgiver_condition_playerDeletedQ4(player, npc))
            {
                corellia_murmurs_questgiver_action_giveFourthQuest(player, npc);
                string_id message = new string_id(c_stringFile, "s_39");
                utils.removeScriptVar(player, "conversation.corellia_murmurs_questgiver.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
            if (corellia_murmurs_questgiver_condition_playerDeletedQ5(player, npc))
            {
                corellia_murmurs_questgiver_action_giveFifthQuest(player, npc);
                string_id message = new string_id(c_stringFile, "s_37");
                utils.removeScriptVar(player, "conversation.corellia_murmurs_questgiver.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
            if (corellia_murmurs_questgiver_condition_playerDeletedQ6(player, npc))
            {
                corellia_murmurs_questgiver_action_giveSixthQuest(player, npc);
                string_id message = new string_id(c_stringFile, "s_38");
                utils.removeScriptVar(player, "conversation.corellia_murmurs_questgiver.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
            if (corellia_murmurs_questgiver_condition_playerDeletedQ7(player, npc))
            {
                corellia_murmurs_questgiver_action_giveSeventhQuest(player, npc);
                string_id message = new string_id(c_stringFile, "s_32");
                utils.removeScriptVar(player, "conversation.corellia_murmurs_questgiver.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
            if (corellia_murmurs_questgiver_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_26");
                utils.removeScriptVar(player, "conversation.corellia_murmurs_questgiver.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int corellia_murmurs_questgiver_handleBranch13(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_36"))
        {
            if (corellia_murmurs_questgiver_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_44");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (corellia_murmurs_questgiver_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (corellia_murmurs_questgiver_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_46");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_33");
                    }
                    utils.setScriptVar(player, "conversation.corellia_murmurs_questgiver.branchId", 14);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.corellia_murmurs_questgiver.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int corellia_murmurs_questgiver_handleBranch14(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_46"))
        {
            if (corellia_murmurs_questgiver_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "thank");
                corellia_murmurs_questgiver_action_giveFirstQuest(player, npc);
                string_id message = new string_id(c_stringFile, "s_48");
                utils.removeScriptVar(player, "conversation.corellia_murmurs_questgiver.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_33"))
        {
            if (corellia_murmurs_questgiver_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "shake_head_no");
                string_id message = new string_id(c_stringFile, "s_43");
                utils.removeScriptVar(player, "conversation.corellia_murmurs_questgiver.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        if ((!isTangible(self)) || (isPlayer(self)))
        {
            detachScript(self, "conversation.corellia_murmurs_questgiver");
        }
        setCondition(self, CONDITION_CONVERSABLE);
        setCondition(self, CONDITION_INTERESTING);
        setInvulnerable(self, true);
        ai_lib.setDefaultCalmBehavior(self, ai_lib.BEHAVIOR_SENTINEL);
        return SCRIPT_CONTINUE;
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        setCondition(self, CONDITION_CONVERSABLE);
        setCondition(self, CONDITION_INTERESTING);
        setInvulnerable(self, true);
        ai_lib.setDefaultCalmBehavior(self, ai_lib.BEHAVIOR_SENTINEL);
        return SCRIPT_CONTINUE;
    }
    public int OnObjectMenuRequest(obj_id self, obj_id player, menu_info menuInfo) throws InterruptedException
    {
        int menu = menuInfo.addRootMenu(menu_info_types.CONVERSE_START, null);
        menu_info_data menuInfoData = menuInfo.getMenuItemById(menu);
        menuInfoData.setServerNotify(false);
        setCondition(self, CONDITION_CONVERSABLE);
        return SCRIPT_CONTINUE;
    }
    public int OnIncapacitated(obj_id self, obj_id killer) throws InterruptedException
    {
        clearCondition(self, CONDITION_CONVERSABLE);
        detachScript(self, "conversation.corellia_murmurs_questgiver");
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
        if (corellia_murmurs_questgiver_condition_finishedAll(player, npc))
        {
            doAnimationAction(npc, "salute1");
            corellia_murmurs_questgiver_action_signalGotoQuest(player, npc);
            string_id message = new string_id(c_stringFile, "s_20");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (!corellia_murmurs_questgiver_condition_hasPointer(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_28");
                }
                utils.setScriptVar(player, "conversation.corellia_murmurs_questgiver.branchId", 1);
                npcStartConversation(player, npc, "corellia_murmurs_questgiver", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (corellia_murmurs_questgiver_condition_playerOnFinalStep(player, npc))
        {
            corellia_murmurs_questgiver_action_signalGotoQuest(player, npc);
            string_id message = new string_id(c_stringFile, "s_40");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (corellia_murmurs_questgiver_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_41");
                }
                utils.setScriptVar(player, "conversation.corellia_murmurs_questgiver.branchId", 3);
                npcStartConversation(player, npc, "corellia_murmurs_questgiver", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (corellia_murmurs_questgiver_condition_playerCompletedInitialStep(player, npc))
        {
            doAnimationAction(npc, "salute1");
            corellia_murmurs_questgiver_action_signalGotoQuest(player, npc);
            string_id message = new string_id(c_stringFile, "s_14");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (corellia_murmurs_questgiver_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_23");
                }
                utils.setScriptVar(player, "conversation.corellia_murmurs_questgiver.branchId", 5);
                npcStartConversation(player, npc, "corellia_murmurs_questgiver", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (corellia_murmurs_questgiver_condition__defaultCondition(player, npc))
        {
            corellia_murmurs_questgiver_action_signalGotoQuest(player, npc);
            string_id message = new string_id(c_stringFile, "s_34");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (corellia_murmurs_questgiver_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_36");
                }
                utils.setScriptVar(player, "conversation.corellia_murmurs_questgiver.branchId", 13);
                npcStartConversation(player, npc, "corellia_murmurs_questgiver", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        chat.chat(npc, "Error:  All conditions for OnStartNpcConversation were false.");
        return SCRIPT_CONTINUE;
    }
    public int OnNpcConversationResponse(obj_id self, String conversationId, obj_id player, string_id response) throws InterruptedException
    {
        if (!conversationId.equals("corellia_murmurs_questgiver"))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id npc = self;
        int branchId = utils.getIntScriptVar(player, "conversation.corellia_murmurs_questgiver.branchId");
        if (branchId == 1 && corellia_murmurs_questgiver_handleBranch1(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 3 && corellia_murmurs_questgiver_handleBranch3(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 4 && corellia_murmurs_questgiver_handleBranch4(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 5 && corellia_murmurs_questgiver_handleBranch5(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 13 && corellia_murmurs_questgiver_handleBranch13(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 14 && corellia_murmurs_questgiver_handleBranch14(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        chat.chat(npc, "Error:  Fell through all branches and responses for OnNpcConversationResponse.");
        utils.removeScriptVar(player, "conversation.corellia_murmurs_questgiver.branchId");
        return SCRIPT_CONTINUE;
    }
}
