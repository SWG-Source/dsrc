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

public class som_storm_lord_jural extends script.base_script
{
    public som_storm_lord_jural()
    {
    }
    public static String c_stringFile = "conversation/som_storm_lord_jural";
    public boolean som_storm_lord_jural_condition__defaultCondition(obj_id player, obj_id npc) throws InterruptedException
    {
        return true;
    }
    public boolean som_storm_lord_jural_condition_isOnTaskOne(obj_id player, obj_id npc) throws InterruptedException
    {
        return groundquests.isTaskActive(player, "som_storm_lord", "storm_lord_one");
    }
    public boolean som_storm_lord_jural_condition_finishedTaskOne(obj_id player, obj_id npc) throws InterruptedException
    {
        return groundquests.isTaskActive(player, "som_storm_lord", "storm_lord_two");
    }
    public boolean som_storm_lord_jural_condition_isOnTaskTwo(obj_id player, obj_id npc) throws InterruptedException
    {
        return groundquests.isTaskActive(player, "som_storm_lord", "storm_lord_three");
    }
    public boolean som_storm_lord_jural_condition_finishedTaskTwo(obj_id player, obj_id npc) throws InterruptedException
    {
        return groundquests.isTaskActive(player, "som_storm_lord", "storm_lord_four");
    }
    public boolean som_storm_lord_jural_condition_isOnTaskThree(obj_id player, obj_id npc) throws InterruptedException
    {
        return groundquests.isTaskActive(player, "som_storm_lord", "storm_lord_five");
    }
    public boolean som_storm_lord_jural_condition_finishedTaskThree(obj_id player, obj_id npc) throws InterruptedException
    {
        return groundquests.isTaskActive(player, "som_storm_lord", "storm_lord_six");
    }
    public boolean som_storm_lord_jural_condition_isOnTaskFour(obj_id player, obj_id npc) throws InterruptedException
    {
        return groundquests.isTaskActive(player, "som_storm_lord", "storm_lord_seven");
    }
    public boolean som_storm_lord_jural_condition_finishedTaskFour(obj_id player, obj_id npc) throws InterruptedException
    {
        return groundquests.isTaskActive(player, "som_storm_lord", "storm_lord_eight");
    }
    public boolean som_storm_lord_jural_condition_hasWonMission(obj_id player, obj_id npc) throws InterruptedException
    {
        return groundquests.hasCompletedQuest(player, "som_storm_lord");
    }
    public void som_storm_lord_jural_action_startMission(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.grantQuest(player, "som_storm_lord");
    }
    public void som_storm_lord_jural_action_sendFirstSignal(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.sendSignal(player, "storm_lord_minions_defeated");
    }
    public void som_storm_lord_jural_action_sendSecondSignal(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.sendSignal(player, "storm_lord_zealots_defeated");
    }
    public void som_storm_lord_jural_action_sendThirdSignal(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.sendSignal(player, "storm_lord_prophet_defeated");
    }
    public void som_storm_lord_jural_action_sendFourthSignal(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.sendSignal(player, "storm_lord_defeated");
    }
    public int som_storm_lord_jural_handleBranch2(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_63"))
        {
            if (som_storm_lord_jural_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "manipulate_medium");
                som_storm_lord_jural_action_sendFourthSignal(player, npc);
                string_id message = new string_id(c_stringFile, "s_64");
                utils.removeScriptVar(player, "conversation.som_storm_lord_jural.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int som_storm_lord_jural_handleBranch5(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_56"))
        {
            if (som_storm_lord_jural_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "shrug_hands");
                string_id message = new string_id(c_stringFile, "s_57");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (som_storm_lord_jural_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (som_storm_lord_jural_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_58");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_59");
                    }
                    utils.setScriptVar(player, "conversation.som_storm_lord_jural.branchId", 6);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.som_storm_lord_jural.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int som_storm_lord_jural_handleBranch6(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_58"))
        {
            doAnimationAction(player, "nod");
            if (som_storm_lord_jural_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "pound_fist_palm");
                som_storm_lord_jural_action_sendThirdSignal(player, npc);
                string_id message = new string_id(c_stringFile, "s_60");
                utils.removeScriptVar(player, "conversation.som_storm_lord_jural.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_59"))
        {
            if (som_storm_lord_jural_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_61");
                utils.removeScriptVar(player, "conversation.som_storm_lord_jural.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int som_storm_lord_jural_handleBranch10(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_45"))
        {
            doAnimationAction(player, "pose_proudly");
            if (som_storm_lord_jural_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "pound_fist_palm");
                string_id message = new string_id(c_stringFile, "s_46");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (som_storm_lord_jural_condition__defaultCondition(player, npc))
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
                    utils.setScriptVar(player, "conversation.som_storm_lord_jural.branchId", 11);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.som_storm_lord_jural.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int som_storm_lord_jural_handleBranch11(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_47"))
        {
            if (som_storm_lord_jural_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "shake_head_no");
                string_id message = new string_id(c_stringFile, "s_48");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (som_storm_lord_jural_condition__defaultCondition(player, npc))
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
                    utils.setScriptVar(player, "conversation.som_storm_lord_jural.branchId", 12);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.som_storm_lord_jural.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int som_storm_lord_jural_handleBranch12(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_49"))
        {
            doAnimationAction(player, "rub_chin_thoughtful");
            if (som_storm_lord_jural_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "explain");
                string_id message = new string_id(c_stringFile, "s_50");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (som_storm_lord_jural_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (som_storm_lord_jural_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_51");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_52");
                    }
                    utils.setScriptVar(player, "conversation.som_storm_lord_jural.branchId", 13);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.som_storm_lord_jural.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int som_storm_lord_jural_handleBranch13(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_51"))
        {
            if (som_storm_lord_jural_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "slow_down");
                som_storm_lord_jural_action_sendSecondSignal(player, npc);
                string_id message = new string_id(c_stringFile, "s_54");
                utils.removeScriptVar(player, "conversation.som_storm_lord_jural.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_52"))
        {
            if (som_storm_lord_jural_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "sigh_deeply");
                string_id message = new string_id(c_stringFile, "s_53");
                utils.removeScriptVar(player, "conversation.som_storm_lord_jural.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int som_storm_lord_jural_handleBranch17(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_37"))
        {
            if (som_storm_lord_jural_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "sigh_deeply");
                string_id message = new string_id(c_stringFile, "s_38");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (som_storm_lord_jural_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_40");
                    }
                    utils.setScriptVar(player, "conversation.som_storm_lord_jural.branchId", 18);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.som_storm_lord_jural.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int som_storm_lord_jural_handleBranch18(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_40"))
        {
            if (som_storm_lord_jural_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "explain");
                string_id message = new string_id(c_stringFile, "s_42");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (som_storm_lord_jural_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (som_storm_lord_jural_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_44");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_68");
                    }
                    utils.setScriptVar(player, "conversation.som_storm_lord_jural.branchId", 19);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.som_storm_lord_jural.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int som_storm_lord_jural_handleBranch19(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_44"))
        {
            if (som_storm_lord_jural_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "implore");
                som_storm_lord_jural_action_sendFirstSignal(player, npc);
                string_id message = new string_id(c_stringFile, "s_66");
                utils.removeScriptVar(player, "conversation.som_storm_lord_jural.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_68"))
        {
            if (som_storm_lord_jural_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_70");
                utils.removeScriptVar(player, "conversation.som_storm_lord_jural.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int som_storm_lord_jural_handleBranch23(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_76"))
        {
            if (som_storm_lord_jural_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "sweat");
                string_id message = new string_id(c_stringFile, "s_78");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (som_storm_lord_jural_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_80");
                    }
                    utils.setScriptVar(player, "conversation.som_storm_lord_jural.branchId", 24);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.som_storm_lord_jural.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int som_storm_lord_jural_handleBranch24(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_80"))
        {
            if (som_storm_lord_jural_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "point_to_self");
                string_id message = new string_id(c_stringFile, "s_82");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (som_storm_lord_jural_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_84");
                    }
                    utils.setScriptVar(player, "conversation.som_storm_lord_jural.branchId", 25);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.som_storm_lord_jural.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int som_storm_lord_jural_handleBranch25(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_84"))
        {
            if (som_storm_lord_jural_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "nod");
                string_id message = new string_id(c_stringFile, "s_86");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (som_storm_lord_jural_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_88");
                    }
                    utils.setScriptVar(player, "conversation.som_storm_lord_jural.branchId", 26);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.som_storm_lord_jural.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int som_storm_lord_jural_handleBranch26(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_88"))
        {
            if (som_storm_lord_jural_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "whisper");
                string_id message = new string_id(c_stringFile, "s_90");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (som_storm_lord_jural_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_92");
                    }
                    utils.setScriptVar(player, "conversation.som_storm_lord_jural.branchId", 27);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.som_storm_lord_jural.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int som_storm_lord_jural_handleBranch27(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_92"))
        {
            if (som_storm_lord_jural_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_94");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (som_storm_lord_jural_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_96");
                    }
                    utils.setScriptVar(player, "conversation.som_storm_lord_jural.branchId", 28);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.som_storm_lord_jural.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int som_storm_lord_jural_handleBranch28(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_96"))
        {
            if (som_storm_lord_jural_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "explain");
                string_id message = new string_id(c_stringFile, "s_98");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (som_storm_lord_jural_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_100");
                    }
                    utils.setScriptVar(player, "conversation.som_storm_lord_jural.branchId", 29);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.som_storm_lord_jural.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int som_storm_lord_jural_handleBranch29(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_100"))
        {
            if (som_storm_lord_jural_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "shush");
                string_id message = new string_id(c_stringFile, "s_102");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (som_storm_lord_jural_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_104");
                    }
                    utils.setScriptVar(player, "conversation.som_storm_lord_jural.branchId", 30);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.som_storm_lord_jural.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int som_storm_lord_jural_handleBranch30(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_104"))
        {
            if (som_storm_lord_jural_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "sigh_deeply");
                string_id message = new string_id(c_stringFile, "s_106");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (som_storm_lord_jural_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_108");
                    }
                    utils.setScriptVar(player, "conversation.som_storm_lord_jural.branchId", 31);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.som_storm_lord_jural.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int som_storm_lord_jural_handleBranch31(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_108"))
        {
            doAnimationAction(player, "huh");
            if (som_storm_lord_jural_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "shrug_hands");
                string_id message = new string_id(c_stringFile, "s_110");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (som_storm_lord_jural_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_112");
                    }
                    utils.setScriptVar(player, "conversation.som_storm_lord_jural.branchId", 32);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.som_storm_lord_jural.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int som_storm_lord_jural_handleBranch32(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_112"))
        {
            if (som_storm_lord_jural_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "rub_chin_thoughtful");
                string_id message = new string_id(c_stringFile, "s_114");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (som_storm_lord_jural_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_116");
                    }
                    utils.setScriptVar(player, "conversation.som_storm_lord_jural.branchId", 33);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.som_storm_lord_jural.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int som_storm_lord_jural_handleBranch33(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_116"))
        {
            if (som_storm_lord_jural_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "explain");
                string_id message = new string_id(c_stringFile, "s_118");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (som_storm_lord_jural_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (som_storm_lord_jural_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_120");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_124");
                    }
                    utils.setScriptVar(player, "conversation.som_storm_lord_jural.branchId", 34);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.som_storm_lord_jural.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int som_storm_lord_jural_handleBranch34(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_120"))
        {
            if (som_storm_lord_jural_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "nod_head_multiple");
                som_storm_lord_jural_action_startMission(player, npc);
                string_id message = new string_id(c_stringFile, "s_122");
                utils.removeScriptVar(player, "conversation.som_storm_lord_jural.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_124"))
        {
            doAnimationAction(player, "stop");
            if (som_storm_lord_jural_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "sigh_deeply");
                string_id message = new string_id(c_stringFile, "s_126");
                utils.removeScriptVar(player, "conversation.som_storm_lord_jural.branchId");
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
            detachScript(self, "conversation.som_storm_lord_jural");
        }
        setCondition(self, CONDITION_CONVERSABLE);
        setInvulnerable(self, true);
        setCondition(self, CONDITION_INTERESTING);
        setName(self, "Jural");
        return SCRIPT_CONTINUE;
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        setCondition(self, CONDITION_CONVERSABLE);
        setInvulnerable(self, true);
        setCondition(self, CONDITION_INTERESTING);
        setName(self, "Jural");
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
        detachScript(self, "conversation.som_storm_lord_jural");
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
        if (som_storm_lord_jural_condition_hasWonMission(player, npc))
        {
            doAnimationAction(npc, "greet");
            string_id message = new string_id(c_stringFile, "s_62");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        if (som_storm_lord_jural_condition_finishedTaskFour(player, npc))
        {
            doAnimationAction(npc, "offer_affection");
            string_id message = new string_id(c_stringFile, "s_55");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (som_storm_lord_jural_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_63");
                }
                utils.setScriptVar(player, "conversation.som_storm_lord_jural.branchId", 2);
                npcStartConversation(player, npc, "som_storm_lord_jural", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (som_storm_lord_jural_condition_isOnTaskFour(player, npc))
        {
            doAnimationAction(npc, "whisper");
            string_id message = new string_id(c_stringFile, "s_8");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        if (som_storm_lord_jural_condition_finishedTaskThree(player, npc))
        {
            doAnimationAction(npc, "celebrate");
            string_id message = new string_id(c_stringFile, "s_10");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (som_storm_lord_jural_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_56");
                }
                utils.setScriptVar(player, "conversation.som_storm_lord_jural.branchId", 5);
                npcStartConversation(player, npc, "som_storm_lord_jural", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (som_storm_lord_jural_condition_isOnTaskThree(player, npc))
        {
            doAnimationAction(npc, "hug_self");
            string_id message = new string_id(c_stringFile, "s_18");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        if (som_storm_lord_jural_condition_finishedTaskTwo(player, npc))
        {
            doAnimationAction(npc, "kisscheek");
            string_id message = new string_id(c_stringFile, "s_20");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (som_storm_lord_jural_condition__defaultCondition(player, npc))
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
                utils.setScriptVar(player, "conversation.som_storm_lord_jural.branchId", 10);
                npcStartConversation(player, npc, "som_storm_lord_jural", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (som_storm_lord_jural_condition_isOnTaskTwo(player, npc))
        {
            doAnimationAction(npc, "weeping");
            string_id message = new string_id(c_stringFile, "s_32");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        if (som_storm_lord_jural_condition_finishedTaskOne(player, npc))
        {
            doAnimationAction(npc, "beckon");
            string_id message = new string_id(c_stringFile, "s_34");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (som_storm_lord_jural_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_37");
                }
                utils.setScriptVar(player, "conversation.som_storm_lord_jural.branchId", 17);
                npcStartConversation(player, npc, "som_storm_lord_jural", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (som_storm_lord_jural_condition_isOnTaskOne(player, npc))
        {
            doAnimationAction(npc, "weeping");
            string_id message = new string_id(c_stringFile, "s_72");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        if (som_storm_lord_jural_condition__defaultCondition(player, npc))
        {
            doAnimationAction(npc, "shake_head_disgust");
            string_id message = new string_id(c_stringFile, "s_74");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (som_storm_lord_jural_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_76");
                }
                utils.setScriptVar(player, "conversation.som_storm_lord_jural.branchId", 23);
                npcStartConversation(player, npc, "som_storm_lord_jural", message, responses);
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
        if (!conversationId.equals("som_storm_lord_jural"))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id npc = self;
        int branchId = utils.getIntScriptVar(player, "conversation.som_storm_lord_jural.branchId");
        if (branchId == 2 && som_storm_lord_jural_handleBranch2(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 5 && som_storm_lord_jural_handleBranch5(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 6 && som_storm_lord_jural_handleBranch6(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 10 && som_storm_lord_jural_handleBranch10(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 11 && som_storm_lord_jural_handleBranch11(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 12 && som_storm_lord_jural_handleBranch12(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 13 && som_storm_lord_jural_handleBranch13(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 17 && som_storm_lord_jural_handleBranch17(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 18 && som_storm_lord_jural_handleBranch18(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 19 && som_storm_lord_jural_handleBranch19(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 23 && som_storm_lord_jural_handleBranch23(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 24 && som_storm_lord_jural_handleBranch24(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 25 && som_storm_lord_jural_handleBranch25(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 26 && som_storm_lord_jural_handleBranch26(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 27 && som_storm_lord_jural_handleBranch27(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 28 && som_storm_lord_jural_handleBranch28(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 29 && som_storm_lord_jural_handleBranch29(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 30 && som_storm_lord_jural_handleBranch30(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 31 && som_storm_lord_jural_handleBranch31(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 32 && som_storm_lord_jural_handleBranch32(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 33 && som_storm_lord_jural_handleBranch33(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 34 && som_storm_lord_jural_handleBranch34(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        chat.chat(npc, "Error:  Fell through all branches and responses for OnNpcConversationResponse.");
        utils.removeScriptVar(player, "conversation.som_storm_lord_jural.branchId");
        return SCRIPT_CONTINUE;
    }
}
