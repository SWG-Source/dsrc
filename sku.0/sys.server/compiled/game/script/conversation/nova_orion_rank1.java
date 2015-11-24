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
import script.library.conversation;
import script.library.groundquests;
import script.library.space_quest;
import script.library.utils;

public class nova_orion_rank1 extends script.base_script
{
    public nova_orion_rank1()
    {
    }
    public static String c_stringFile = "conversation/nova_orion_rank1";
    public boolean nova_orion_rank1_condition__defaultCondition(obj_id player, obj_id npc) throws InterruptedException
    {
        return true;
    }
    public boolean nova_orion_rank1_condition_onQuest_01(obj_id player, obj_id npc) throws InterruptedException
    {
        return (groundquests.isQuestActive(player, "nova_orion_rank1_01") && !groundquests.isTaskActive(player, "nova_orion_rank1_01", "wait_01"));
    }
    public boolean nova_orion_rank1_condition_returnWin_01(obj_id player, obj_id npc) throws InterruptedException
    {
        return (groundquests.isTaskActive(player, "nova_orion_rank1_01", "wait_01") || groundquests.hasCompletedQuest(player, "nova_orion_rank1_01"));
    }
    public boolean nova_orion_rank1_condition_onQuest_02(obj_id player, obj_id npc) throws InterruptedException
    {
        return (groundquests.isQuestActive(player, "nova_orion_rank1_02") && !groundquests.isTaskActive(player, "nova_orion_rank1_02", "wait"));
    }
    public boolean nova_orion_rank1_condition_returnWin_02(obj_id player, obj_id npc) throws InterruptedException
    {
        return (groundquests.isTaskActive(player, "nova_orion_rank1_02", "wait") || groundquests.hasCompletedQuest(player, "nova_orion_rank1_02"));
    }
    public boolean nova_orion_rank1_condition_onQuest_03(obj_id player, obj_id npc) throws InterruptedException
    {
        return (groundquests.isQuestActive(player, "nova_orion_rank1_03") && !groundquests.isTaskActive(player, "nova_orion_rank1_02", "wait"));
    }
    public boolean nova_orion_rank1_condition_returnWin_03(obj_id player, obj_id npc) throws InterruptedException
    {
        return groundquests.isTaskActive(player, "nova_orion_rank1_03", "wait");
    }
    public boolean nova_orion_rank1_condition_finishedAll(obj_id player, obj_id npc) throws InterruptedException
    {
        return groundquests.hasCompletedQuest(player, "nova_orion_rank1_03");
    }
    public boolean nova_orion_rank1_condition_isRank1(obj_id player, obj_id npc) throws InterruptedException
    {
        return hasCompletedCollectionSlot(player, "nova_orion_rank_01");
    }
    public void nova_orion_rank1_action_grantQuest_01(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.grantQuest(player, "nova_orion_rank1_01");
    }
    public void nova_orion_rank1_action_clearAndRegrant_01(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.clearQuest(player, "nova_orion_rank1_01");
        space_quest.clearQuestFlags(player, "delivery", "nova_orion_rank1_01");
        groundquests.grantQuest(player, "nova_orion_rank1_01");
    }
    public void nova_orion_rank1_action_sendFinishSignal_01(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.sendSignal(player, "nova_orion_rank1_01");
    }
    public void nova_orion_rank1_action_grantQuest_02(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.grantQuest(player, "nova_orion_rank1_02");
    }
    public void nova_orion_rank1_action_clearAndRegrant_02(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.clearQuest(player, "nova_orion_rank1_02");
        space_quest.clearQuestFlags(player, "patrol", "nova_orion_rank1_01");
        space_quest.clearQuestFlags(player, "destroy", "nova_orion_rank1_01");
        space_quest.clearQuestFlags(player, "patrol", "nova_orion_rank1_02");
        space_quest.clearQuestFlags(player, "destroy_surpriseattack", "nova_orion_rank1_01");
        groundquests.grantQuest(player, "nova_orion_rank1_02");
    }
    public void nova_orion_rank1_action_grantQuest_03(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.grantQuest(player, "nova_orion_rank1_03");
    }
    public void nova_orion_rank1_action_clearAndRegrant_03(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.clearQuest(player, "nova_orion_rank1_03");
        space_quest.clearQuestFlags(player, "patrol", "nova_orion_rank1_03");
        space_quest.clearQuestFlags(player, "assassinate", "nova_orion_rank1_01");
        groundquests.grantQuest(player, "nova_orion_rank1_03");
    }
    public void nova_orion_rank1_action_sendFinishSignal_02(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.sendSignal(player, "nova_orion_rank1_02");
    }
    public void nova_orion_rank1_action_sendFinishSignal_03(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.sendSignal(player, "nova_orion_rank1_03");
    }
    public int nova_orion_rank1_handleBranch2(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_33"))
        {
            nova_orion_rank1_action_sendFinishSignal_03(player, npc);
            if (nova_orion_rank1_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_34");
                utils.removeScriptVar(player, "conversation.nova_orion_rank1.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int nova_orion_rank1_handleBranch4(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_29"))
        {
            if (nova_orion_rank1_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_31");
                utils.removeScriptVar(player, "conversation.nova_orion_rank1.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_30"))
        {
            if (nova_orion_rank1_condition__defaultCondition(player, npc))
            {
                nova_orion_rank1_action_clearAndRegrant_03(player, npc);
                string_id message = new string_id(c_stringFile, "s_32");
                utils.removeScriptVar(player, "conversation.nova_orion_rank1.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int nova_orion_rank1_handleBranch7(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_27"))
        {
            nova_orion_rank1_action_sendFinishSignal_02(player, npc);
            if (nova_orion_rank1_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_35");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (nova_orion_rank1_condition__defaultCondition(player, npc))
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
                    utils.setScriptVar(player, "conversation.nova_orion_rank1.branchId", 8);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.nova_orion_rank1.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int nova_orion_rank1_handleBranch8(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_36"))
        {
            if (nova_orion_rank1_condition__defaultCondition(player, npc))
            {
                nova_orion_rank1_action_grantQuest_03(player, npc);
                string_id message = new string_id(c_stringFile, "s_38");
                utils.removeScriptVar(player, "conversation.nova_orion_rank1.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int nova_orion_rank1_handleBranch10(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_20"))
        {
            if (nova_orion_rank1_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_22");
                utils.removeScriptVar(player, "conversation.nova_orion_rank1.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_37"))
        {
            if (nova_orion_rank1_condition__defaultCondition(player, npc))
            {
                nova_orion_rank1_action_clearAndRegrant_02(player, npc);
                string_id message = new string_id(c_stringFile, "s_40");
                utils.removeScriptVar(player, "conversation.nova_orion_rank1.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int nova_orion_rank1_handleBranch13(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_44"))
        {
            nova_orion_rank1_action_sendFinishSignal_01(player, npc);
            if (nova_orion_rank1_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_46");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (nova_orion_rank1_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_48");
                    }
                    utils.setScriptVar(player, "conversation.nova_orion_rank1.branchId", 14);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.nova_orion_rank1.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int nova_orion_rank1_handleBranch14(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_48"))
        {
            if (nova_orion_rank1_condition__defaultCondition(player, npc))
            {
                nova_orion_rank1_action_grantQuest_02(player, npc);
                string_id message = new string_id(c_stringFile, "s_50");
                utils.removeScriptVar(player, "conversation.nova_orion_rank1.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int nova_orion_rank1_handleBranch16(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_54"))
        {
            if (nova_orion_rank1_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_56");
                utils.removeScriptVar(player, "conversation.nova_orion_rank1.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_58"))
        {
            if (nova_orion_rank1_condition__defaultCondition(player, npc))
            {
                nova_orion_rank1_action_clearAndRegrant_01(player, npc);
                string_id message = new string_id(c_stringFile, "s_60");
                utils.removeScriptVar(player, "conversation.nova_orion_rank1.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int nova_orion_rank1_handleBranch19(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_64"))
        {
            if (nova_orion_rank1_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_66");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (nova_orion_rank1_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_68");
                    }
                    utils.setScriptVar(player, "conversation.nova_orion_rank1.branchId", 20);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.nova_orion_rank1.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int nova_orion_rank1_handleBranch20(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_68"))
        {
            if (nova_orion_rank1_condition__defaultCondition(player, npc))
            {
                nova_orion_rank1_action_grantQuest_01(player, npc);
                string_id message = new string_id(c_stringFile, "s_70");
                utils.removeScriptVar(player, "conversation.nova_orion_rank1.branchId");
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
            detachScript(self, "conversation.nova_orion_rank1");
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
        setCondition(self, CONDITION_CONVERSABLE);
        return SCRIPT_CONTINUE;
    }
    public int OnIncapacitated(obj_id self, obj_id killer) throws InterruptedException
    {
        clearCondition(self, CONDITION_CONVERSABLE);
        detachScript(self, "conversation.nova_orion_rank1");
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
        if (nova_orion_rank1_condition_finishedAll(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_24");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        if (nova_orion_rank1_condition_returnWin_03(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_25");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (nova_orion_rank1_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_33");
                }
                utils.setScriptVar(player, "conversation.nova_orion_rank1.branchId", 2);
                npcStartConversation(player, npc, "nova_orion_rank1", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (nova_orion_rank1_condition_onQuest_03(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_26");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (nova_orion_rank1_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            boolean hasResponse1 = false;
            if (nova_orion_rank1_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_29");
                }
                if (hasResponse1)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_30");
                }
                utils.setScriptVar(player, "conversation.nova_orion_rank1.branchId", 4);
                npcStartConversation(player, npc, "nova_orion_rank1", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (nova_orion_rank1_condition_returnWin_02(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_23");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (nova_orion_rank1_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_27");
                }
                utils.setScriptVar(player, "conversation.nova_orion_rank1.branchId", 7);
                npcStartConversation(player, npc, "nova_orion_rank1", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (nova_orion_rank1_condition_onQuest_02(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_18");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (nova_orion_rank1_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            boolean hasResponse1 = false;
            if (nova_orion_rank1_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_20");
                }
                if (hasResponse1)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_37");
                }
                utils.setScriptVar(player, "conversation.nova_orion_rank1.branchId", 10);
                npcStartConversation(player, npc, "nova_orion_rank1", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (nova_orion_rank1_condition_returnWin_01(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_42");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (nova_orion_rank1_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_44");
                }
                utils.setScriptVar(player, "conversation.nova_orion_rank1.branchId", 13);
                npcStartConversation(player, npc, "nova_orion_rank1", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (nova_orion_rank1_condition_onQuest_01(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_52");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (nova_orion_rank1_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            boolean hasResponse1 = false;
            if (nova_orion_rank1_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_54");
                }
                if (hasResponse1)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_58");
                }
                utils.setScriptVar(player, "conversation.nova_orion_rank1.branchId", 16);
                npcStartConversation(player, npc, "nova_orion_rank1", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (nova_orion_rank1_condition_isRank1(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_62");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (nova_orion_rank1_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_64");
                }
                utils.setScriptVar(player, "conversation.nova_orion_rank1.branchId", 19);
                npcStartConversation(player, npc, "nova_orion_rank1", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (nova_orion_rank1_condition__defaultCondition(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_72");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        chat.chat(npc, "Error:  All conditions for OnStartNpcConversation were false.");
        return SCRIPT_CONTINUE;
    }
    public int OnNpcConversationResponse(obj_id self, String conversationId, obj_id player, string_id response) throws InterruptedException
    {
        if (!conversationId.equals("nova_orion_rank1"))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id npc = self;
        int branchId = utils.getIntScriptVar(player, "conversation.nova_orion_rank1.branchId");
        if (branchId == 2 && nova_orion_rank1_handleBranch2(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 4 && nova_orion_rank1_handleBranch4(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 7 && nova_orion_rank1_handleBranch7(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 8 && nova_orion_rank1_handleBranch8(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 10 && nova_orion_rank1_handleBranch10(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 13 && nova_orion_rank1_handleBranch13(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 14 && nova_orion_rank1_handleBranch14(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 16 && nova_orion_rank1_handleBranch16(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 19 && nova_orion_rank1_handleBranch19(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 20 && nova_orion_rank1_handleBranch20(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        chat.chat(npc, "Error:  Fell through all branches and responses for OnNpcConversationResponse.");
        utils.removeScriptVar(player, "conversation.nova_orion_rank1.branchId");
        return SCRIPT_CONTINUE;
    }
}
