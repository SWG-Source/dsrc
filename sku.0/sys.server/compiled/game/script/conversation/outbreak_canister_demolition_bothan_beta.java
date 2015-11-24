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
import script.library.features;
import script.library.groundquests;
import script.library.space_quest;
import script.library.utils;

public class outbreak_canister_demolition_bothan_beta extends script.base_script
{
    public outbreak_canister_demolition_bothan_beta()
    {
    }
    public static String c_stringFile = "conversation/outbreak_canister_demolition_bothan_beta";
    public boolean outbreak_canister_demolition_bothan_beta_condition__defaultCondition(obj_id player, obj_id npc) throws InterruptedException
    {
        return true;
    }
    public boolean outbreak_canister_demolition_bothan_beta_condition_hasBetaQuestIncomplete(obj_id player, obj_id npc) throws InterruptedException
    {
        return groundquests.isQuestActive(player, "camp_beta_canister_demo") && !groundquests.isTaskActive(player, "camp_beta_canister_demo", "collectRewardBeta");
    }
    public boolean outbreak_canister_demolition_bothan_beta_condition_hasFinishedQuota(obj_id player, obj_id npc) throws InterruptedException
    {
        return groundquests.isTaskActive(player, "camp_beta_canister_demo", "collectRewardBeta");
    }
    public boolean outbreak_canister_demolition_bothan_beta_condition_hasNeverSpoken(obj_id player, obj_id npc) throws InterruptedException
    {
        return hasCompletedCollectionSlot(player, "exp_icon_deathtroopers_camp_alpha") && !groundquests.isQuestActiveOrComplete(player, "camp_alpha_canister_demo");
    }
    public boolean outbreak_canister_demolition_bothan_beta_condition_hasCompletedBetaQuest(obj_id player, obj_id npc) throws InterruptedException
    {
        return groundquests.hasCompletedQuest(player, "camp_beta_canister_demo");
    }
    public boolean outbreak_canister_demolition_bothan_beta_condition_hasCompletedRadioQuests(obj_id player, obj_id npc) throws InterruptedException
    {
        return groundquests.hasCompletedQuest(player, "camp_beta_canister_demo") && groundquests.hasCompletedQuest(player, "outbreak_radio_delivery_03");
    }
    public boolean outbreak_canister_demolition_bothan_beta_condition_hasFoundOtherCamps(obj_id player, obj_id npc) throws InterruptedException
    {
        return groundquests.hasCompletedQuest(player, "camp_beta_canister_demo") && (hasCompletedCollectionSlot(player, "exp_icon_deathtroopers_camp_delta") || hasCompletedCollectionSlot(player, "exp_icon_deathtroopers_camp_gamma"));
    }
    public boolean outbreak_canister_demolition_bothan_beta_condition_noAntiVirus(obj_id player, obj_id npc) throws InterruptedException
    {
        return (!groundquests.hasCompletedQuest(player, "outbreak_quest_01_imperial") && !groundquests.hasCompletedQuest(player, "outbreak_quest_01_rebel") && !groundquests.hasCompletedQuest(player, "outbreak_quest_01_neutral"));
    }
    public boolean outbreak_canister_demolition_bothan_beta_condition_hasCompletedAllCamps(obj_id player, obj_id npc) throws InterruptedException
    {
        return (groundquests.hasCompletedQuest(player, "camp_alpha_canister_demo") && groundquests.hasCompletedQuest(player, "camp_beta_canister_demo") && groundquests.hasCompletedQuest(player, "camp_gamma_canister_demo") && groundquests.hasCompletedQuest(player, "camp_delta_canister_demo"));
    }
    public boolean outbreak_canister_demolition_bothan_beta_condition_hasFinishedQuotaAlpha(obj_id player, obj_id npc) throws InterruptedException
    {
        return groundquests.isTaskActive(player, "camp_alpha_canister_demo", "collectRewardAlpha");
    }
    public boolean outbreak_canister_demolition_bothan_beta_condition_hasAnotherCampIncomplete(obj_id player, obj_id npc) throws InterruptedException
    {
        return (groundquests.isQuestActive(player, "camp_alpha_canister_demo") || groundquests.isQuestActive(player, "camp_gamma_canister_demo") || groundquests.isQuestActive(player, "camp_delta_canister_demo"));
    }
    public boolean outbreak_canister_demolition_bothan_beta_condition_hasAnotherCampCompleted(obj_id player, obj_id npc) throws InterruptedException
    {
        return (groundquests.hasCompletedQuest(player, "camp_alpha_canister_demo") || groundquests.hasCompletedQuest(player, "camp_gamma_canister_demo") || groundquests.hasCompletedQuest(player, "camp_delta_canister_demo"));
    }
    public boolean outbreak_canister_demolition_bothan_beta_condition_hasFinishedQuotaDelta(obj_id player, obj_id npc) throws InterruptedException
    {
        return groundquests.isTaskActive(player, "camp_delta_canister_demo", "collectRewardDelta");
    }
    public boolean outbreak_canister_demolition_bothan_beta_condition_hasFinishedQuotaGamma(obj_id player, obj_id npc) throws InterruptedException
    {
        return groundquests.isTaskActive(player, "camp_gamma_canister_demo", "collectRewardGamma");
    }
    public void outbreak_canister_demolition_bothan_beta_action_grantNextQuestAndCollection(obj_id player, obj_id npc) throws InterruptedException
    {
        if (!hasCompletedCollectionSlotPrereq(player, "outbreak_camp_alpha_canister_counter"))
        {
            modifyCollectionSlotValue(player, "outbreak_camp_canister_activation", 1);
        }
        groundquests.grantQuest(player, "camp_beta_canister_demo");
    }
    public void outbreak_canister_demolition_bothan_beta_action_completeQuest(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.sendSignal(player, "hasCompletedBeta");
    }
    public int outbreak_canister_demolition_bothan_beta_handleBranch3(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_95"))
        {
            if (outbreak_canister_demolition_bothan_beta_condition__defaultCondition(player, npc))
            {
                outbreak_canister_demolition_bothan_beta_action_completeQuest(player, npc);
                string_id message = new string_id(c_stringFile, "s_96");
                utils.removeScriptVar(player, "conversation.outbreak_canister_demolition_bothan_beta.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int outbreak_canister_demolition_bothan_beta_handleBranch7(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_27"))
        {
            if (outbreak_canister_demolition_bothan_beta_condition__defaultCondition(player, npc))
            {
                outbreak_canister_demolition_bothan_beta_action_grantNextQuestAndCollection(player, npc);
                string_id message = new string_id(c_stringFile, "s_28");
                utils.removeScriptVar(player, "conversation.outbreak_canister_demolition_bothan_beta.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int outbreak_canister_demolition_bothan_beta_handleBranch9(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_29"))
        {
            if (outbreak_canister_demolition_bothan_beta_condition__defaultCondition(player, npc))
            {
                outbreak_canister_demolition_bothan_beta_action_grantNextQuestAndCollection(player, npc);
                string_id message = new string_id(c_stringFile, "s_30");
                utils.removeScriptVar(player, "conversation.outbreak_canister_demolition_bothan_beta.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int outbreak_canister_demolition_bothan_beta_handleBranch11(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_36"))
        {
            if (outbreak_canister_demolition_bothan_beta_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_37");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (outbreak_canister_demolition_bothan_beta_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_38");
                    }
                    utils.setScriptVar(player, "conversation.outbreak_canister_demolition_bothan_beta.branchId", 12);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.outbreak_canister_demolition_bothan_beta.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int outbreak_canister_demolition_bothan_beta_handleBranch12(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_38"))
        {
            if (outbreak_canister_demolition_bothan_beta_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_40");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (outbreak_canister_demolition_bothan_beta_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_43");
                    }
                    utils.setScriptVar(player, "conversation.outbreak_canister_demolition_bothan_beta.branchId", 13);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.outbreak_canister_demolition_bothan_beta.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int outbreak_canister_demolition_bothan_beta_handleBranch13(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_43"))
        {
            if (outbreak_canister_demolition_bothan_beta_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_45");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (outbreak_canister_demolition_bothan_beta_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_47");
                    }
                    utils.setScriptVar(player, "conversation.outbreak_canister_demolition_bothan_beta.branchId", 14);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.outbreak_canister_demolition_bothan_beta.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int outbreak_canister_demolition_bothan_beta_handleBranch14(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_47"))
        {
            if (outbreak_canister_demolition_bothan_beta_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_49");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (outbreak_canister_demolition_bothan_beta_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_51");
                    }
                    utils.setScriptVar(player, "conversation.outbreak_canister_demolition_bothan_beta.branchId", 15);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.outbreak_canister_demolition_bothan_beta.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int outbreak_canister_demolition_bothan_beta_handleBranch15(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_51"))
        {
            if (outbreak_canister_demolition_bothan_beta_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_76");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (outbreak_canister_demolition_bothan_beta_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_78");
                    }
                    utils.setScriptVar(player, "conversation.outbreak_canister_demolition_bothan_beta.branchId", 16);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.outbreak_canister_demolition_bothan_beta.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int outbreak_canister_demolition_bothan_beta_handleBranch16(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_78"))
        {
            if (outbreak_canister_demolition_bothan_beta_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "handshake_tandem");
                doAnimationAction(player, "handshake_tandem");
                outbreak_canister_demolition_bothan_beta_action_grantNextQuestAndCollection(player, npc);
                string_id message = new string_id(c_stringFile, "s_91");
                utils.removeScriptVar(player, "conversation.outbreak_canister_demolition_bothan_beta.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        setCondition(self, CONDITION_CONVERSABLE);
        setCondition(self, CONDITION_INTERESTING);
        return SCRIPT_CONTINUE;
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        setCondition(self, CONDITION_CONVERSABLE);
        setCondition(self, CONDITION_INTERESTING);
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
        if (outbreak_canister_demolition_bothan_beta_condition_hasCompletedAllCamps(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_42");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        if (outbreak_canister_demolition_bothan_beta_condition_hasCompletedBetaQuest(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_97");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        if (outbreak_canister_demolition_bothan_beta_condition_hasFinishedQuota(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_94");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (outbreak_canister_demolition_bothan_beta_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_95");
                }
                utils.setScriptVar(player, "conversation.outbreak_canister_demolition_bothan_beta.branchId", 3);
                npcStartConversation(player, npc, "outbreak_canister_demolition_bothan_beta", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (outbreak_canister_demolition_bothan_beta_condition_hasBetaQuestIncomplete(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_93");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        if (outbreak_canister_demolition_bothan_beta_condition_noAntiVirus(player, npc))
        {
            doAnimationAction(npc, "wave_on_dismissing");
            string_id message = new string_id(c_stringFile, "s_69");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        if (outbreak_canister_demolition_bothan_beta_condition_hasAnotherCampIncomplete(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_25");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (outbreak_canister_demolition_bothan_beta_condition__defaultCondition(player, npc))
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
                utils.setScriptVar(player, "conversation.outbreak_canister_demolition_bothan_beta.branchId", 7);
                npcStartConversation(player, npc, "outbreak_canister_demolition_bothan_beta", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (outbreak_canister_demolition_bothan_beta_condition_hasAnotherCampCompleted(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_26");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (outbreak_canister_demolition_bothan_beta_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_29");
                }
                utils.setScriptVar(player, "conversation.outbreak_canister_demolition_bothan_beta.branchId", 9);
                npcStartConversation(player, npc, "outbreak_canister_demolition_bothan_beta", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (outbreak_canister_demolition_bothan_beta_condition_hasNeverSpoken(player, npc))
        {
            doAnimationAction(npc, "beckon");
            string_id message = new string_id(c_stringFile, "s_35");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (outbreak_canister_demolition_bothan_beta_condition__defaultCondition(player, npc))
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
                utils.setScriptVar(player, "conversation.outbreak_canister_demolition_bothan_beta.branchId", 11);
                npcStartConversation(player, npc, "outbreak_canister_demolition_bothan_beta", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (outbreak_canister_demolition_bothan_beta_condition__defaultCondition(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_90");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        chat.chat(npc, "Error:  All conditions for OnStartNpcConversation were false.");
        return SCRIPT_CONTINUE;
    }
    public int OnNpcConversationResponse(obj_id self, String conversationId, obj_id player, string_id response) throws InterruptedException
    {
        if (!conversationId.equals("outbreak_canister_demolition_bothan_beta"))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id npc = self;
        int branchId = utils.getIntScriptVar(player, "conversation.outbreak_canister_demolition_bothan_beta.branchId");
        if (branchId == 3 && outbreak_canister_demolition_bothan_beta_handleBranch3(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 7 && outbreak_canister_demolition_bothan_beta_handleBranch7(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 9 && outbreak_canister_demolition_bothan_beta_handleBranch9(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 11 && outbreak_canister_demolition_bothan_beta_handleBranch11(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 12 && outbreak_canister_demolition_bothan_beta_handleBranch12(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 13 && outbreak_canister_demolition_bothan_beta_handleBranch13(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 14 && outbreak_canister_demolition_bothan_beta_handleBranch14(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 15 && outbreak_canister_demolition_bothan_beta_handleBranch15(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 16 && outbreak_canister_demolition_bothan_beta_handleBranch16(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        chat.chat(npc, "Error:  Fell through all branches and responses for OnNpcConversationResponse.");
        utils.removeScriptVar(player, "conversation.outbreak_canister_demolition_bothan_beta.branchId");
        return SCRIPT_CONTINUE;
    }
}
