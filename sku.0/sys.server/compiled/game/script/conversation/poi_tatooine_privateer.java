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

public class poi_tatooine_privateer extends script.base_script
{
    public poi_tatooine_privateer()
    {
    }
    public static String c_stringFile = "conversation/poi_tatooine_privateer";
    public boolean poi_tatooine_privateer_condition__defaultCondition(obj_id player, obj_id npc) throws InterruptedException
    {
        return true;
    }
    public boolean poi_tatooine_privateer_condition_failedQuestOne(obj_id player, obj_id npc) throws InterruptedException
    {
        if (space_quest.hasFailedQuestRecursive(player, "delivery_no_pickup", "poi_tatooine_privateer_1") || space_quest.hasAbortedQuestRecursive(player, "delivery_no_pickup", "poi_tatooine_privateer_1"))
        {
            return true;
        }
        return false;
    }
    public boolean poi_tatooine_privateer_condition_failedQuestTwo(obj_id player, obj_id npc) throws InterruptedException
    {
        if (space_quest.hasFailedQuestRecursive(player, "patrol", "poi_tatooine_privateer_2") || space_quest.hasAbortedQuestRecursive(player, "patrol", "poi_tatooine_privateer_2"))
        {
            return true;
        }
        return false;
    }
    public boolean poi_tatooine_privateer_condition_failedQuestThree(obj_id player, obj_id npc) throws InterruptedException
    {
        if (space_quest.hasFailedQuestRecursive(player, "delivery_no_pickup", "poi_tatooine_privateer_3") || space_quest.hasAbortedQuestRecursive(player, "delivery_no_pickup", "poi_tatooine_privateer_3"))
        {
            return true;
        }
        return false;
    }
    public boolean poi_tatooine_privateer_condition_hasCompletedQuestOne(obj_id player, obj_id npc) throws InterruptedException
    {
        if (space_quest.hasCompletedQuestRecursive(player, "delivery_no_pickup", "poi_tatooine_privateer_1"))
        {
            return true;
        }
        return false;
    }
    public boolean poi_tatooine_privateer_condition_hasCompletedQuestTwo(obj_id player, obj_id npc) throws InterruptedException
    {
        if (space_quest.hasCompletedQuestRecursive(player, "patrol", "poi_tatooine_privateer_2"))
        {
            return true;
        }
        return false;
    }
    public boolean poi_tatooine_privateer_condition_hasCompletedQuestThree(obj_id player, obj_id npc) throws InterruptedException
    {
        if (space_quest.hasCompletedQuestRecursive(player, "delivery_no_pickup", "poi_tatooine_privateer_3"))
        {
            return true;
        }
        return false;
    }
    public boolean poi_tatooine_privateer_condition_collectingQuestOneReward(obj_id player, obj_id npc) throws InterruptedException
    {
        if (space_quest.hasCompletedQuestRecursive(player, "delivery_no_pickup", "poi_tatooine_privateer_1") && !space_quest.hasReceivedReward(player, "delivery_no_pickup", "poi_tatooine_privateer_1"))
        {
            return true;
        }
        else 
        {
            return false;
        }
    }
    public boolean poi_tatooine_privateer_condition_collectingQuestTwoReward(obj_id player, obj_id npc) throws InterruptedException
    {
        if (space_quest.hasCompletedQuestRecursive(player, "patrol", "poi_tatooine_privateer_2") && !space_quest.hasReceivedReward(player, "patrol", "poi_tatooine_privateer_2"))
        {
            return true;
        }
        else 
        {
            return false;
        }
    }
    public boolean poi_tatooine_privateer_condition_collectingQuestThreeReward(obj_id player, obj_id npc) throws InterruptedException
    {
        if (space_quest.hasCompletedQuestRecursive(player, "delivery_no_pickup", "poi_tatooine_privateer_3") && !space_quest.hasReceivedReward(player, "delivery_no_pickup", "poi_tatooine_privateer_3"))
        {
            return true;
        }
        else 
        {
            return false;
        }
    }
    public boolean poi_tatooine_privateer_condition_isCorrectFaction(obj_id player, obj_id npc) throws InterruptedException
    {
        return (hasSkill(player, "pilot_neutral_novice"));
    }
    public boolean poi_tatooine_privateer_condition_isNegativeFaction(obj_id player, obj_id npc) throws InterruptedException
    {
        return (factions.getFactionStanding(player, "jabba") < 0.0f);
    }
    public boolean poi_tatooine_privateer_condition_hasAnyQuest(obj_id player, obj_id npc) throws InterruptedException
    {
        return space_quest.hasQuest(player);
    }
    public boolean poi_tatooine_privateer_condition_isTooFar(obj_id player, obj_id npc) throws InterruptedException
    {
        space_combat.playCombatTauntSound(player);
        obj_id containingShip = space_transition.getContainingShip(player);
        return (getDistance(npc, containingShip) > space_transition.STATION_COMM_MAX_DISTANCE);
    }
    public void poi_tatooine_privateer_action_grantQuestOne(obj_id player, obj_id npc) throws InterruptedException
    {
        space_quest.grantQuest(player, "delivery_no_pickup", "poi_tatooine_privateer_1");
    }
    public void poi_tatooine_privateer_action_grantQuestTwo(obj_id player, obj_id npc) throws InterruptedException
    {
        space_quest.grantQuest(player, "patrol", "poi_tatooine_privateer_2");
    }
    public void poi_tatooine_privateer_action_grantQuestThree(obj_id player, obj_id npc) throws InterruptedException
    {
        space_quest.grantQuest(player, "delivery_no_pickup", "poi_tatooine_privateer_3");
    }
    public void poi_tatooine_privateer_action_rewardForQuestOne(obj_id player, obj_id npc) throws InterruptedException
    {
        if (!space_quest.hasReceivedReward(player, "delivery_no_pickup", "poi_tatooine_privateer_1"))
        {
            space_quest.giveReward(player, "delivery_no_pickup", "poi_tatooine_privateer_1", 5000);
        }
    }
    public void poi_tatooine_privateer_action_rewardForQuestTwo(obj_id player, obj_id npc) throws InterruptedException
    {
        if (!space_quest.hasReceivedReward(player, "patrol", "poi_tatooine_privateer_2"))
        {
            space_quest.giveReward(player, "patrol", "poi_tatooine_privateer_2", 10000);
        }
    }
    public void poi_tatooine_privateer_action_rewardForQuestThree(obj_id player, obj_id npc) throws InterruptedException
    {
        if (!space_quest.hasReceivedReward(player, "delivery_no_pickup", "poi_tatooine_privateer_3"))
        {
            space_quest.giveReward(player, "delivery_no_pickup", "poi_tatooine_privateer_3", 20000);
        }
    }
    public int poi_tatooine_privateer_handleBranch5(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_fcf8c341"))
        {
            if (poi_tatooine_privateer_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_8842ba84");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (poi_tatooine_privateer_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_67e6df55");
                    }
                    utils.setScriptVar(player, "conversation.poi_tatooine_privateer.branchId", 6);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.poi_tatooine_privateer.branchId");
                    chat.chat(npc, player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int poi_tatooine_privateer_handleBranch6(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_67e6df55"))
        {
            if (poi_tatooine_privateer_condition__defaultCondition(player, npc))
            {
                poi_tatooine_privateer_action_grantQuestTwo(player, npc);
                string_id message = new string_id(c_stringFile, "s_753c600d");
                utils.removeScriptVar(player, "conversation.poi_tatooine_privateer.branchId");
                chat.chat(npc, player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int poi_tatooine_privateer_handleBranch8(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_5c3ad40f"))
        {
            if (poi_tatooine_privateer_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_e48b5a8");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (poi_tatooine_privateer_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_67e6df55");
                    }
                    utils.setScriptVar(player, "conversation.poi_tatooine_privateer.branchId", 9);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.poi_tatooine_privateer.branchId");
                    chat.chat(npc, player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int poi_tatooine_privateer_handleBranch9(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_67e6df55"))
        {
            if (poi_tatooine_privateer_condition__defaultCondition(player, npc))
            {
                poi_tatooine_privateer_action_grantQuestThree(player, npc);
                string_id message = new string_id(c_stringFile, "s_753c600d");
                utils.removeScriptVar(player, "conversation.poi_tatooine_privateer.branchId");
                chat.chat(npc, player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int poi_tatooine_privateer_handleBranch11(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_2d8a838b"))
        {
            if (poi_tatooine_privateer_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_6b51a7a7");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (poi_tatooine_privateer_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_67e6df55");
                    }
                    utils.setScriptVar(player, "conversation.poi_tatooine_privateer.branchId", 12);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.poi_tatooine_privateer.branchId");
                    chat.chat(npc, player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int poi_tatooine_privateer_handleBranch12(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_67e6df55"))
        {
            if (poi_tatooine_privateer_condition__defaultCondition(player, npc))
            {
                poi_tatooine_privateer_action_grantQuestOne(player, npc);
                string_id message = new string_id(c_stringFile, "s_931fb8d6");
                utils.removeScriptVar(player, "conversation.poi_tatooine_privateer.branchId");
                chat.chat(npc, player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int poi_tatooine_privateer_handleBranch14(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_6613e8a2"))
        {
            if (poi_tatooine_privateer_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_c5afcca4");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (poi_tatooine_privateer_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_2aa3e51d");
                    }
                    utils.setScriptVar(player, "conversation.poi_tatooine_privateer.branchId", 15);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.poi_tatooine_privateer.branchId");
                    chat.chat(npc, player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int poi_tatooine_privateer_handleBranch15(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_2aa3e51d"))
        {
            if (poi_tatooine_privateer_condition__defaultCondition(player, npc))
            {
                poi_tatooine_privateer_action_rewardForQuestOne(player, npc);
                string_id message = new string_id(c_stringFile, "s_753c600d");
                utils.removeScriptVar(player, "conversation.poi_tatooine_privateer.branchId");
                chat.chat(npc, player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int poi_tatooine_privateer_handleBranch17(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_6fc5551a"))
        {
            if (poi_tatooine_privateer_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_4e0a91b7");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (poi_tatooine_privateer_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_27eedc3f");
                    }
                    utils.setScriptVar(player, "conversation.poi_tatooine_privateer.branchId", 18);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.poi_tatooine_privateer.branchId");
                    chat.chat(npc, player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int poi_tatooine_privateer_handleBranch18(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_27eedc3f"))
        {
            if (poi_tatooine_privateer_condition__defaultCondition(player, npc))
            {
                poi_tatooine_privateer_action_rewardForQuestThree(player, npc);
                string_id message = new string_id(c_stringFile, "s_931fb8d6");
                utils.removeScriptVar(player, "conversation.poi_tatooine_privateer.branchId");
                chat.chat(npc, player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int poi_tatooine_privateer_handleBranch20(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_e7cb1e01"))
        {
            if (poi_tatooine_privateer_condition__defaultCondition(player, npc))
            {
                poi_tatooine_privateer_action_rewardForQuestTwo(player, npc);
                string_id message = new string_id(c_stringFile, "s_753c600d");
                utils.removeScriptVar(player, "conversation.poi_tatooine_privateer.branchId");
                chat.chat(npc, player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int poi_tatooine_privateer_handleBranch22(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_e4bd704d"))
        {
            if (poi_tatooine_privateer_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_5f5d3519");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (poi_tatooine_privateer_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_4b48508f");
                    }
                    utils.setScriptVar(player, "conversation.poi_tatooine_privateer.branchId", 23);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.poi_tatooine_privateer.branchId");
                    chat.chat(npc, player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int poi_tatooine_privateer_handleBranch23(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_4b48508f"))
        {
            if (poi_tatooine_privateer_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_3856c45c");
                utils.removeScriptVar(player, "conversation.poi_tatooine_privateer.branchId");
                chat.chat(npc, player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int poi_tatooine_privateer_handleBranch25(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_4dc8c70"))
        {
            if (poi_tatooine_privateer_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_f4142e79");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (poi_tatooine_privateer_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (poi_tatooine_privateer_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_f8ccf9dd");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_8d73b3f1");
                    }
                    utils.setScriptVar(player, "conversation.poi_tatooine_privateer.branchId", 26);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.poi_tatooine_privateer.branchId");
                    chat.chat(npc, player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int poi_tatooine_privateer_handleBranch26(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_f8ccf9dd"))
        {
            if (poi_tatooine_privateer_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_7552334");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (poi_tatooine_privateer_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (poi_tatooine_privateer_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_1bce15e6");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_aef4404d");
                    }
                    utils.setScriptVar(player, "conversation.poi_tatooine_privateer.branchId", 27);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.poi_tatooine_privateer.branchId");
                    chat.chat(npc, player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_8d73b3f1"))
        {
            if (poi_tatooine_privateer_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_95d8887f");
                utils.removeScriptVar(player, "conversation.poi_tatooine_privateer.branchId");
                chat.chat(npc, player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int poi_tatooine_privateer_handleBranch27(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_1bce15e6"))
        {
            if (poi_tatooine_privateer_condition__defaultCondition(player, npc))
            {
                poi_tatooine_privateer_action_grantQuestThree(player, npc);
                string_id message = new string_id(c_stringFile, "s_99dc1f68");
                utils.removeScriptVar(player, "conversation.poi_tatooine_privateer.branchId");
                chat.chat(npc, player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_aef4404d"))
        {
            if (poi_tatooine_privateer_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_61657d0f");
                utils.removeScriptVar(player, "conversation.poi_tatooine_privateer.branchId");
                chat.chat(npc, player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int poi_tatooine_privateer_handleBranch31(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_6d43911a"))
        {
            if (poi_tatooine_privateer_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_936786d3");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (poi_tatooine_privateer_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (poi_tatooine_privateer_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_cccb49af");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_618da618");
                    }
                    utils.setScriptVar(player, "conversation.poi_tatooine_privateer.branchId", 32);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.poi_tatooine_privateer.branchId");
                    chat.chat(npc, player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int poi_tatooine_privateer_handleBranch32(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_cccb49af"))
        {
            if (poi_tatooine_privateer_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_706c0c0c");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (poi_tatooine_privateer_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (poi_tatooine_privateer_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_8e9d0848");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_1c914ce3");
                    }
                    utils.setScriptVar(player, "conversation.poi_tatooine_privateer.branchId", 33);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.poi_tatooine_privateer.branchId");
                    chat.chat(npc, player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_618da618"))
        {
            if (poi_tatooine_privateer_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_8ff8682d");
                utils.removeScriptVar(player, "conversation.poi_tatooine_privateer.branchId");
                chat.chat(npc, player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int poi_tatooine_privateer_handleBranch33(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_8e9d0848"))
        {
            if (poi_tatooine_privateer_condition__defaultCondition(player, npc))
            {
                poi_tatooine_privateer_action_grantQuestTwo(player, npc);
                string_id message = new string_id(c_stringFile, "s_c45f4bb1");
                utils.removeScriptVar(player, "conversation.poi_tatooine_privateer.branchId");
                chat.chat(npc, player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_1c914ce3"))
        {
            if (poi_tatooine_privateer_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_61657d0f");
                utils.removeScriptVar(player, "conversation.poi_tatooine_privateer.branchId");
                chat.chat(npc, player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int poi_tatooine_privateer_handleBranch37(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_dfce1c9a"))
        {
            if (poi_tatooine_privateer_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_3bf29799");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (poi_tatooine_privateer_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (poi_tatooine_privateer_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_4470da");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_c5a66e82");
                    }
                    utils.setScriptVar(player, "conversation.poi_tatooine_privateer.branchId", 38);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.poi_tatooine_privateer.branchId");
                    chat.chat(npc, player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_3a510008"))
        {
            if (poi_tatooine_privateer_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_61657d0f");
                utils.removeScriptVar(player, "conversation.poi_tatooine_privateer.branchId");
                chat.chat(npc, player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int poi_tatooine_privateer_handleBranch38(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_4470da"))
        {
            if (poi_tatooine_privateer_condition__defaultCondition(player, npc))
            {
                poi_tatooine_privateer_action_grantQuestOne(player, npc);
                string_id message = new string_id(c_stringFile, "s_752f38d3");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (poi_tatooine_privateer_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_fc27931b");
                    }
                    utils.setScriptVar(player, "conversation.poi_tatooine_privateer.branchId", 39);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.poi_tatooine_privateer.branchId");
                    chat.chat(npc, player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_c5a66e82"))
        {
            if (poi_tatooine_privateer_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_1639204e");
                utils.removeScriptVar(player, "conversation.poi_tatooine_privateer.branchId");
                chat.chat(npc, player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int poi_tatooine_privateer_handleBranch39(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_fc27931b"))
        {
            if (poi_tatooine_privateer_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_a388d49");
                utils.removeScriptVar(player, "conversation.poi_tatooine_privateer.branchId");
                chat.chat(npc, player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int poi_tatooine_privateer_handleBranch43(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_d3874dd0"))
        {
            if (poi_tatooine_privateer_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_931fb8d6");
                utils.removeScriptVar(player, "conversation.poi_tatooine_privateer.branchId");
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
            detachScript(self, "conversation.poi_tatooine_privateer");
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
        detachScript(self, "conversation.poi_tatooine_privateer");
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
        if (poi_tatooine_privateer_condition_isTooFar(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_fc740f6a");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        if (poi_tatooine_privateer_condition_hasAnyQuest(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_736584d8");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        if (!poi_tatooine_privateer_condition_isCorrectFaction(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_49904b3");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        if (poi_tatooine_privateer_condition_isNegativeFaction(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_f59dd698");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        if (poi_tatooine_privateer_condition_failedQuestTwo(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_36d6852");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (poi_tatooine_privateer_condition__defaultCondition(player, npc))
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
                utils.setScriptVar(player, "conversation.poi_tatooine_privateer.branchId", 5);
                npcStartConversation(player, npc, "poi_tatooine_privateer", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (poi_tatooine_privateer_condition_failedQuestThree(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_42e25376");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (poi_tatooine_privateer_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_5c3ad40f");
                }
                utils.setScriptVar(player, "conversation.poi_tatooine_privateer.branchId", 8);
                npcStartConversation(player, npc, "poi_tatooine_privateer", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (poi_tatooine_privateer_condition_failedQuestOne(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_76924085");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (poi_tatooine_privateer_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_2d8a838b");
                }
                utils.setScriptVar(player, "conversation.poi_tatooine_privateer.branchId", 11);
                npcStartConversation(player, npc, "poi_tatooine_privateer", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (poi_tatooine_privateer_condition_collectingQuestOneReward(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_e20422e9");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (poi_tatooine_privateer_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_6613e8a2");
                }
                utils.setScriptVar(player, "conversation.poi_tatooine_privateer.branchId", 14);
                npcStartConversation(player, npc, "poi_tatooine_privateer", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (poi_tatooine_privateer_condition_collectingQuestThreeReward(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_8d1e636e");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (poi_tatooine_privateer_condition__defaultCondition(player, npc))
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
                utils.setScriptVar(player, "conversation.poi_tatooine_privateer.branchId", 17);
                npcStartConversation(player, npc, "poi_tatooine_privateer", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (poi_tatooine_privateer_condition_collectingQuestTwoReward(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_50cdd468");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (poi_tatooine_privateer_condition__defaultCondition(player, npc))
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
                utils.setScriptVar(player, "conversation.poi_tatooine_privateer.branchId", 20);
                npcStartConversation(player, npc, "poi_tatooine_privateer", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (poi_tatooine_privateer_condition_hasCompletedQuestThree(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_458d24ab");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (poi_tatooine_privateer_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_e4bd704d");
                }
                utils.setScriptVar(player, "conversation.poi_tatooine_privateer.branchId", 22);
                npcStartConversation(player, npc, "poi_tatooine_privateer", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (poi_tatooine_privateer_condition_hasCompletedQuestTwo(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_68496504");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (poi_tatooine_privateer_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_4dc8c70");
                }
                utils.setScriptVar(player, "conversation.poi_tatooine_privateer.branchId", 25);
                npcStartConversation(player, npc, "poi_tatooine_privateer", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (poi_tatooine_privateer_condition_hasCompletedQuestOne(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_b71871d5");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (poi_tatooine_privateer_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_6d43911a");
                }
                utils.setScriptVar(player, "conversation.poi_tatooine_privateer.branchId", 31);
                npcStartConversation(player, npc, "poi_tatooine_privateer", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (poi_tatooine_privateer_condition_isCorrectFaction(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_4004b748");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (poi_tatooine_privateer_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            boolean hasResponse1 = false;
            if (poi_tatooine_privateer_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_dfce1c9a");
                }
                if (hasResponse1)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_3a510008");
                }
                utils.setScriptVar(player, "conversation.poi_tatooine_privateer.branchId", 37);
                npcStartConversation(player, npc, "poi_tatooine_privateer", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (poi_tatooine_privateer_condition__defaultCondition(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_485816ee");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (poi_tatooine_privateer_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_d3874dd0");
                }
                utils.setScriptVar(player, "conversation.poi_tatooine_privateer.branchId", 43);
                npcStartConversation(player, npc, "poi_tatooine_privateer", message, responses);
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
        if (!conversationId.equals("poi_tatooine_privateer"))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id npc = self;
        int branchId = utils.getIntScriptVar(player, "conversation.poi_tatooine_privateer.branchId");
        if (branchId == 5 && poi_tatooine_privateer_handleBranch5(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 6 && poi_tatooine_privateer_handleBranch6(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 8 && poi_tatooine_privateer_handleBranch8(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 9 && poi_tatooine_privateer_handleBranch9(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 11 && poi_tatooine_privateer_handleBranch11(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 12 && poi_tatooine_privateer_handleBranch12(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 14 && poi_tatooine_privateer_handleBranch14(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 15 && poi_tatooine_privateer_handleBranch15(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 17 && poi_tatooine_privateer_handleBranch17(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 18 && poi_tatooine_privateer_handleBranch18(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 20 && poi_tatooine_privateer_handleBranch20(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 22 && poi_tatooine_privateer_handleBranch22(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 23 && poi_tatooine_privateer_handleBranch23(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 25 && poi_tatooine_privateer_handleBranch25(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 26 && poi_tatooine_privateer_handleBranch26(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 27 && poi_tatooine_privateer_handleBranch27(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 31 && poi_tatooine_privateer_handleBranch31(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 32 && poi_tatooine_privateer_handleBranch32(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 33 && poi_tatooine_privateer_handleBranch33(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 37 && poi_tatooine_privateer_handleBranch37(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 38 && poi_tatooine_privateer_handleBranch38(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 39 && poi_tatooine_privateer_handleBranch39(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 43 && poi_tatooine_privateer_handleBranch43(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        chat.chat(npc, "Error:  Fell through all branches and responses for OnNpcConversationResponse.");
        utils.removeScriptVar(player, "conversation.poi_tatooine_privateer.branchId");
        return SCRIPT_CONTINUE;
    }
}
