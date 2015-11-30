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
import script.library.factions;
import script.library.space_combat;
import script.library.space_flags;
import script.library.space_quest;
import script.library.space_skill;
import script.library.space_transition;
import script.library.utils;

public class poi_tatooine_general_1 extends script.base_script
{
    public poi_tatooine_general_1()
    {
    }
    public static String c_stringFile = "conversation/poi_tatooine_general_1";
    public boolean poi_tatooine_general_1_condition__defaultCondition(obj_id player, obj_id npc) throws InterruptedException
    {
        return true;
    }
    public boolean poi_tatooine_general_1_condition_failedQuestOne(obj_id player, obj_id npc) throws InterruptedException
    {
        if (space_quest.hasFailedQuestRecursive(player, "patrol", "poi_tatooine_general_1") || space_quest.hasAbortedQuestRecursive(player, "patrol", "poi_tatooine_general_1"))
        {
            return true;
        }
        return false;
    }
    public boolean poi_tatooine_general_1_condition_failedQuestTwo(obj_id player, obj_id npc) throws InterruptedException
    {
        if (space_quest.hasFailedQuestRecursive(player, "inspect", "poi_tatooine_general_2") || space_quest.hasAbortedQuestRecursive(player, "inspect", "poi_tatooine_general_2"))
        {
            return true;
        }
        return false;
    }
    public boolean poi_tatooine_general_1_condition_failedQuestThree(obj_id player, obj_id npc) throws InterruptedException
    {
        if (space_quest.hasFailedQuestRecursive(player, "space_battle", "poi_tatooine_general_3") || space_quest.hasAbortedQuestRecursive(player, "space_battle", "poi_tatooine_general_3"))
        {
            return true;
        }
        return false;
    }
    public boolean poi_tatooine_general_1_condition_hasCompletedQuestOne(obj_id player, obj_id npc) throws InterruptedException
    {
        if (space_quest.hasCompletedQuestRecursive(player, "patrol", "poi_tatooine_general_1"))
        {
            return true;
        }
        return false;
    }
    public boolean poi_tatooine_general_1_condition_hasCompletedQuestTwo(obj_id player, obj_id npc) throws InterruptedException
    {
        if (space_quest.hasCompletedQuestRecursive(player, "inspect", "poi_tatooine_general_2"))
        {
            return true;
        }
        return false;
    }
    public boolean poi_tatooine_general_1_condition_hasCompletedQuestThree(obj_id player, obj_id npc) throws InterruptedException
    {
        if (space_quest.hasCompletedQuestRecursive(player, "space_battle", "poi_tatooine_general_3"))
        {
            return true;
        }
        return false;
    }
    public boolean poi_tatooine_general_1_condition_collectingQuestOneReward(obj_id player, obj_id npc) throws InterruptedException
    {
        if (space_quest.hasCompletedQuestRecursive(player, "patrol", "poi_tatooine_general_1") && !space_quest.hasReceivedReward(player, "patrol", "poi_tatooine_general_1"))
        {
            return true;
        }
        else 
        {
            return false;
        }
    }
    public boolean poi_tatooine_general_1_condition_collectingQuestTwoReward(obj_id player, obj_id npc) throws InterruptedException
    {
        if (space_quest.hasCompletedQuestRecursive(player, "inspect", "poi_tatooine_general_2") && !space_quest.hasReceivedReward(player, "inspect", "poi_tatooine_general_2"))
        {
            return true;
        }
        else 
        {
            return false;
        }
    }
    public boolean poi_tatooine_general_1_condition_collectingQuestThreeReward(obj_id player, obj_id npc) throws InterruptedException
    {
        if (space_quest.hasCompletedQuestRecursive(player, "space_battle", "poi_tatooine_general_3") && !space_quest.hasReceivedReward(player, "space_battle", "poi_tatooine_general_3"))
        {
            return true;
        }
        else 
        {
            return false;
        }
    }
    public boolean poi_tatooine_general_1_condition_isCorrectFaction(obj_id player, obj_id npc) throws InterruptedException
    {
        return (hasSkill(player, "pilot_neutral_novice"));
    }
    public boolean poi_tatooine_general_1_condition_isNegativeFaction(obj_id player, obj_id npc) throws InterruptedException
    {
        return (factions.getFactionStanding(player, "jabba") < 0.0f);
    }
    public boolean poi_tatooine_general_1_condition_hasAnyQuest(obj_id player, obj_id npc) throws InterruptedException
    {
        return space_quest.hasQuest(player);
    }
    public boolean poi_tatooine_general_1_condition_isTooFar(obj_id player, obj_id npc) throws InterruptedException
    {
        space_combat.playCombatTauntSound(player);
        obj_id containingShip = space_transition.getContainingShip(player);
        return (getDistance(npc, containingShip) > space_transition.STATION_COMM_MAX_DISTANCE);
    }
    public void poi_tatooine_general_1_action_grantQuestOne(obj_id player, obj_id npc) throws InterruptedException
    {
        space_quest.grantQuest(player, "patrol", "poi_tatooine_general_1");
    }
    public void poi_tatooine_general_1_action_grantQuestTwo(obj_id player, obj_id npc) throws InterruptedException
    {
        space_quest.grantQuest(player, "inspect", "poi_tatooine_general_2");
    }
    public void poi_tatooine_general_1_action_grantQuestThree(obj_id player, obj_id npc) throws InterruptedException
    {
        space_quest.grantQuest(player, "space_battle", "poi_tatooine_general_3");
    }
    public void poi_tatooine_general_1_action_rewardForQuestOne(obj_id player, obj_id npc) throws InterruptedException
    {
        if (!space_quest.hasReceivedReward(player, "patrol", "poi_tatooine_general_1"))
        {
            space_quest.giveReward(player, "patrol", "poi_tatooine_general_1", 7000);
        }
    }
    public void poi_tatooine_general_1_action_rewardForQuestTwo(obj_id player, obj_id npc) throws InterruptedException
    {
        if (!space_quest.hasReceivedReward(player, "inspect", "poi_tatooine_general_2"))
        {
            space_quest.giveReward(player, "inspect", "poi_tatooine_general_2", 15000);
        }
    }
    public void poi_tatooine_general_1_action_rewardForQuestThree(obj_id player, obj_id npc) throws InterruptedException
    {
        if (!space_quest.hasReceivedReward(player, "space_battle", "poi_tatooine_general_3"))
        {
            space_quest.giveReward(player, "space_battle", "poi_tatooine_general_3", 25000);
        }
    }
    public int poi_tatooine_general_1_handleBranch3(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_fcf8c341"))
        {
            if (poi_tatooine_general_1_condition__defaultCondition(player, npc))
            {
                poi_tatooine_general_1_action_grantQuestTwo(player, npc);
                string_id message = new string_id(c_stringFile, "s_c738862c");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (poi_tatooine_general_1_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_603f6374");
                    }
                    utils.setScriptVar(player, "conversation.poi_tatooine_general_1.branchId", 4);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.poi_tatooine_general_1.branchId");
                    chat.chat(npc, player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int poi_tatooine_general_1_handleBranch4(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_603f6374"))
        {
            if (poi_tatooine_general_1_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_30a4f2c3");
                utils.removeScriptVar(player, "conversation.poi_tatooine_general_1.branchId");
                chat.chat(npc, player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int poi_tatooine_general_1_handleBranch6(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_406612f9"))
        {
            if (poi_tatooine_general_1_condition__defaultCondition(player, npc))
            {
                poi_tatooine_general_1_action_grantQuestThree(player, npc);
                string_id message = new string_id(c_stringFile, "s_8a564db7");
                utils.removeScriptVar(player, "conversation.poi_tatooine_general_1.branchId");
                chat.chat(npc, player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int poi_tatooine_general_1_handleBranch8(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_14b13184"))
        {
            if (poi_tatooine_general_1_condition__defaultCondition(player, npc))
            {
                poi_tatooine_general_1_action_grantQuestOne(player, npc);
                string_id message = new string_id(c_stringFile, "s_fe1b33c5");
                utils.removeScriptVar(player, "conversation.poi_tatooine_general_1.branchId");
                chat.chat(npc, player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int poi_tatooine_general_1_handleBranch10(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_81b811c0"))
        {
            if (poi_tatooine_general_1_condition__defaultCondition(player, npc))
            {
                poi_tatooine_general_1_action_rewardForQuestOne(player, npc);
                string_id message = new string_id(c_stringFile, "s_c5afcca4");
                utils.removeScriptVar(player, "conversation.poi_tatooine_general_1.branchId");
                chat.chat(npc, player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int poi_tatooine_general_1_handleBranch12(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_6fc5551a"))
        {
            if (poi_tatooine_general_1_condition__defaultCondition(player, npc))
            {
                poi_tatooine_general_1_action_rewardForQuestThree(player, npc);
                string_id message = new string_id(c_stringFile, "s_2a29152c");
                utils.removeScriptVar(player, "conversation.poi_tatooine_general_1.branchId");
                chat.chat(npc, player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int poi_tatooine_general_1_handleBranch14(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_e7cb1e01"))
        {
            if (poi_tatooine_general_1_condition__defaultCondition(player, npc))
            {
                poi_tatooine_general_1_action_rewardForQuestTwo(player, npc);
                string_id message = new string_id(c_stringFile, "s_e7f5f946");
                utils.removeScriptVar(player, "conversation.poi_tatooine_general_1.branchId");
                chat.chat(npc, player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int poi_tatooine_general_1_handleBranch16(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_21c417c8"))
        {
            if (poi_tatooine_general_1_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_e7af48c");
                utils.removeScriptVar(player, "conversation.poi_tatooine_general_1.branchId");
                chat.chat(npc, player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int poi_tatooine_general_1_handleBranch18(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_b4b3fdb5"))
        {
            if (poi_tatooine_general_1_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_ea18852");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (poi_tatooine_general_1_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (poi_tatooine_general_1_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_676ef8a2");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_8d73b3f1");
                    }
                    utils.setScriptVar(player, "conversation.poi_tatooine_general_1.branchId", 19);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.poi_tatooine_general_1.branchId");
                    chat.chat(npc, player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int poi_tatooine_general_1_handleBranch19(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_676ef8a2"))
        {
            if (poi_tatooine_general_1_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_bf8bb897");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (poi_tatooine_general_1_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (poi_tatooine_general_1_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_6324b0d2");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_c8053613");
                    }
                    utils.setScriptVar(player, "conversation.poi_tatooine_general_1.branchId", 20);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.poi_tatooine_general_1.branchId");
                    chat.chat(npc, player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_8d73b3f1"))
        {
            if (poi_tatooine_general_1_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_95d8887f");
                utils.removeScriptVar(player, "conversation.poi_tatooine_general_1.branchId");
                chat.chat(npc, player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int poi_tatooine_general_1_handleBranch20(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_6324b0d2"))
        {
            if (poi_tatooine_general_1_condition__defaultCondition(player, npc))
            {
                poi_tatooine_general_1_action_grantQuestThree(player, npc);
                string_id message = new string_id(c_stringFile, "s_a020b1a9");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (poi_tatooine_general_1_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_b9b27823");
                    }
                    utils.setScriptVar(player, "conversation.poi_tatooine_general_1.branchId", 21);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.poi_tatooine_general_1.branchId");
                    chat.chat(npc, player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_c8053613"))
        {
            if (poi_tatooine_general_1_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_61657d0f");
                utils.removeScriptVar(player, "conversation.poi_tatooine_general_1.branchId");
                chat.chat(npc, player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int poi_tatooine_general_1_handleBranch21(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_b9b27823"))
        {
            if (poi_tatooine_general_1_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_e7f5f946");
                utils.removeScriptVar(player, "conversation.poi_tatooine_general_1.branchId");
                chat.chat(npc, player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int poi_tatooine_general_1_handleBranch25(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_a118c43e"))
        {
            if (poi_tatooine_general_1_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_239edf9a");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (poi_tatooine_general_1_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (poi_tatooine_general_1_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_969c4dc2");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_a18139bb");
                    }
                    utils.setScriptVar(player, "conversation.poi_tatooine_general_1.branchId", 26);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.poi_tatooine_general_1.branchId");
                    chat.chat(npc, player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int poi_tatooine_general_1_handleBranch26(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_969c4dc2"))
        {
            if (poi_tatooine_general_1_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_724ce284");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (poi_tatooine_general_1_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (poi_tatooine_general_1_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_ec4258cd");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_1c914ce3");
                    }
                    utils.setScriptVar(player, "conversation.poi_tatooine_general_1.branchId", 27);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.poi_tatooine_general_1.branchId");
                    chat.chat(npc, player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_a18139bb"))
        {
            if (poi_tatooine_general_1_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_de1d7ae9");
                utils.removeScriptVar(player, "conversation.poi_tatooine_general_1.branchId");
                chat.chat(npc, player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int poi_tatooine_general_1_handleBranch27(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_ec4258cd"))
        {
            if (poi_tatooine_general_1_condition__defaultCondition(player, npc))
            {
                poi_tatooine_general_1_action_grantQuestTwo(player, npc);
                string_id message = new string_id(c_stringFile, "s_18a00d6a");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (poi_tatooine_general_1_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_668c0704");
                    }
                    utils.setScriptVar(player, "conversation.poi_tatooine_general_1.branchId", 28);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.poi_tatooine_general_1.branchId");
                    chat.chat(npc, player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_1c914ce3"))
        {
            if (poi_tatooine_general_1_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_6e5b35c0");
                utils.removeScriptVar(player, "conversation.poi_tatooine_general_1.branchId");
                chat.chat(npc, player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int poi_tatooine_general_1_handleBranch28(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_668c0704"))
        {
            if (poi_tatooine_general_1_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_dfba44fb");
                utils.removeScriptVar(player, "conversation.poi_tatooine_general_1.branchId");
                chat.chat(npc, player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int poi_tatooine_general_1_handleBranch32(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_f0445c1d"))
        {
            if (poi_tatooine_general_1_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_11ee3ff6");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (poi_tatooine_general_1_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_c9f21b00");
                    }
                    utils.setScriptVar(player, "conversation.poi_tatooine_general_1.branchId", 33);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.poi_tatooine_general_1.branchId");
                    chat.chat(npc, player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_ae1a82fc"))
        {
            if (poi_tatooine_general_1_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_9208410d");
                utils.removeScriptVar(player, "conversation.poi_tatooine_general_1.branchId");
                chat.chat(npc, player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int poi_tatooine_general_1_handleBranch33(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_c9f21b00"))
        {
            if (poi_tatooine_general_1_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_7d7ae55");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (poi_tatooine_general_1_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_3313e38");
                    }
                    utils.setScriptVar(player, "conversation.poi_tatooine_general_1.branchId", 34);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.poi_tatooine_general_1.branchId");
                    chat.chat(npc, player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int poi_tatooine_general_1_handleBranch34(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_3313e38"))
        {
            if (poi_tatooine_general_1_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_c0dec268");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (poi_tatooine_general_1_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (poi_tatooine_general_1_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_a99397a");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_76cb0e7f");
                    }
                    utils.setScriptVar(player, "conversation.poi_tatooine_general_1.branchId", 35);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.poi_tatooine_general_1.branchId");
                    chat.chat(npc, player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int poi_tatooine_general_1_handleBranch35(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_a99397a"))
        {
            if (poi_tatooine_general_1_condition__defaultCondition(player, npc))
            {
                poi_tatooine_general_1_action_grantQuestOne(player, npc);
                string_id message = new string_id(c_stringFile, "s_e635adbf");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (poi_tatooine_general_1_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_fdba7430");
                    }
                    utils.setScriptVar(player, "conversation.poi_tatooine_general_1.branchId", 36);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.poi_tatooine_general_1.branchId");
                    chat.chat(npc, player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_76cb0e7f"))
        {
            if (poi_tatooine_general_1_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_890511af");
                utils.removeScriptVar(player, "conversation.poi_tatooine_general_1.branchId");
                chat.chat(npc, player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int poi_tatooine_general_1_handleBranch36(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_fdba7430"))
        {
            if (poi_tatooine_general_1_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_8167d29a");
                utils.removeScriptVar(player, "conversation.poi_tatooine_general_1.branchId");
                chat.chat(npc, player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        if ((!isMob(self)) || (isPlayer(self)))
        {
            detachScript(self, "conversation.poi_tatooine_general_1");
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
        detachScript(self, "conversation.poi_tatooine_general_1");
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
        if (poi_tatooine_general_1_condition_isTooFar(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_cd91abc2");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        if (poi_tatooine_general_1_condition_hasAnyQuest(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_c14d0f0c");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        if (poi_tatooine_general_1_condition_failedQuestTwo(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_255d3625");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (poi_tatooine_general_1_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_fcf8c341");
                }
                utils.setScriptVar(player, "conversation.poi_tatooine_general_1.branchId", 3);
                npcStartConversation(player, npc, "poi_tatooine_general_1", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (poi_tatooine_general_1_condition_failedQuestThree(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_c34478b1");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (poi_tatooine_general_1_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_406612f9");
                }
                utils.setScriptVar(player, "conversation.poi_tatooine_general_1.branchId", 6);
                npcStartConversation(player, npc, "poi_tatooine_general_1", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (poi_tatooine_general_1_condition_failedQuestOne(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_dc79df4f");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (poi_tatooine_general_1_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_14b13184");
                }
                utils.setScriptVar(player, "conversation.poi_tatooine_general_1.branchId", 8);
                npcStartConversation(player, npc, "poi_tatooine_general_1", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (poi_tatooine_general_1_condition_collectingQuestOneReward(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_28c5432f");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (poi_tatooine_general_1_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_81b811c0");
                }
                utils.setScriptVar(player, "conversation.poi_tatooine_general_1.branchId", 10);
                npcStartConversation(player, npc, "poi_tatooine_general_1", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (poi_tatooine_general_1_condition_collectingQuestThreeReward(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_1d52b30");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (poi_tatooine_general_1_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_6fc5551a");
                }
                utils.setScriptVar(player, "conversation.poi_tatooine_general_1.branchId", 12);
                npcStartConversation(player, npc, "poi_tatooine_general_1", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (poi_tatooine_general_1_condition_collectingQuestTwoReward(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_3c3a1569");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (poi_tatooine_general_1_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_e7cb1e01");
                }
                utils.setScriptVar(player, "conversation.poi_tatooine_general_1.branchId", 14);
                npcStartConversation(player, npc, "poi_tatooine_general_1", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (poi_tatooine_general_1_condition_hasCompletedQuestThree(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_dfe42352");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (poi_tatooine_general_1_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_21c417c8");
                }
                utils.setScriptVar(player, "conversation.poi_tatooine_general_1.branchId", 16);
                npcStartConversation(player, npc, "poi_tatooine_general_1", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (poi_tatooine_general_1_condition_hasCompletedQuestTwo(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_9eaf76c6");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (poi_tatooine_general_1_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_b4b3fdb5");
                }
                utils.setScriptVar(player, "conversation.poi_tatooine_general_1.branchId", 18);
                npcStartConversation(player, npc, "poi_tatooine_general_1", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (poi_tatooine_general_1_condition_hasCompletedQuestOne(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_e8b2ad1e");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (poi_tatooine_general_1_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_a118c43e");
                }
                utils.setScriptVar(player, "conversation.poi_tatooine_general_1.branchId", 25);
                npcStartConversation(player, npc, "poi_tatooine_general_1", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (poi_tatooine_general_1_condition__defaultCondition(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_a23289f6");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (poi_tatooine_general_1_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            boolean hasResponse1 = false;
            if (poi_tatooine_general_1_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_f0445c1d");
                }
                if (hasResponse1)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_ae1a82fc");
                }
                utils.setScriptVar(player, "conversation.poi_tatooine_general_1.branchId", 32);
                npcStartConversation(player, npc, "poi_tatooine_general_1", message, responses);
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
        if (!conversationId.equals("poi_tatooine_general_1"))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id npc = self;
        int branchId = utils.getIntScriptVar(player, "conversation.poi_tatooine_general_1.branchId");
        if (branchId == 3 && poi_tatooine_general_1_handleBranch3(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 4 && poi_tatooine_general_1_handleBranch4(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 6 && poi_tatooine_general_1_handleBranch6(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 8 && poi_tatooine_general_1_handleBranch8(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 10 && poi_tatooine_general_1_handleBranch10(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 12 && poi_tatooine_general_1_handleBranch12(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 14 && poi_tatooine_general_1_handleBranch14(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 16 && poi_tatooine_general_1_handleBranch16(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 18 && poi_tatooine_general_1_handleBranch18(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 19 && poi_tatooine_general_1_handleBranch19(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 20 && poi_tatooine_general_1_handleBranch20(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 21 && poi_tatooine_general_1_handleBranch21(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 25 && poi_tatooine_general_1_handleBranch25(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 26 && poi_tatooine_general_1_handleBranch26(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 27 && poi_tatooine_general_1_handleBranch27(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 28 && poi_tatooine_general_1_handleBranch28(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 32 && poi_tatooine_general_1_handleBranch32(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 33 && poi_tatooine_general_1_handleBranch33(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 34 && poi_tatooine_general_1_handleBranch34(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 35 && poi_tatooine_general_1_handleBranch35(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 36 && poi_tatooine_general_1_handleBranch36(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        chat.chat(npc, "Error:  Fell through all branches and responses for OnNpcConversationResponse.");
        utils.removeScriptVar(player, "conversation.poi_tatooine_general_1.branchId");
        return SCRIPT_CONTINUE;
    }
}
