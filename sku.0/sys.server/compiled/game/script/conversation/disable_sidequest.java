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
import script.library.skill;
import script.library.space_quest;
import script.library.xp;

public class disable_sidequest extends script.base_script
{
    public disable_sidequest()
    {
    }
    public static String c_stringFile = "conversation/disable_sidequest";
    public boolean disable_sidequest_condition__defaultCondition(obj_id player, obj_id npc) throws InterruptedException
    {
        return true;
    }
    public boolean disable_sidequest_condition_hasReward(obj_id player, obj_id npc) throws InterruptedException
    {
        if (space_quest.hasReceivedReward(player, "inspect", "robot_sidequest"))
        {
            return true;
        }
        else 
        {
            return false;
        }
    }
    public boolean disable_sidequest_condition_hasWonMission1(obj_id player, obj_id npc) throws InterruptedException
    {
        if (space_quest.hasWonQuest(player, "inspect", "robot_sidequest"))
        {
            return true;
        }
        else 
        {
            return false;
        }
    }
    public boolean disable_sidequest_condition_readyForMission2(obj_id player, obj_id npc) throws InterruptedException
    {
        if (space_quest.hasReceivedReward(player, "destroy_surpriseattack", "tatooine_imperial_1_surprise"))
        {
            return true;
        }
        else 
        {
            return false;
        }
    }
    public void disable_sidequest_action_grantMission1(obj_id player, obj_id npc) throws InterruptedException
    {
        faceTo(npc, player);
        space_quest.grantQuest(player, "inspect", "robot_sidequest");
    }
    public void disable_sidequest_action_animExplain(obj_id player, obj_id npc) throws InterruptedException
    {
        faceTo(npc, player);
        doAnimationAction(npc, "explain");
    }
    public void disable_sidequest_action_giveRewardMission1(obj_id player, obj_id npc) throws InterruptedException
    {
        faceTo(npc, player);
        if (!space_quest.hasReceivedReward(player, "inspect", "robot_sidequest"))
        {
            space_quest.giveReward(player, "inspect", "robot_sidequest", 3000);
        }
    }
    public void disable_sidequest_action_animNodHead(obj_id player, obj_id npc) throws InterruptedException
    {
        setName(npc, "CSSD-7");
        setInvulnerable(npc, true);
        faceTo(npc, player);
        doAnimationAction(npc, "nod_head_once");
    }
    public void disable_sidequest_action_animConverse(obj_id player, obj_id npc) throws InterruptedException
    {
        faceTo(npc, player);
        doAnimationAction(npc, "conversation_1");
    }
    public void disable_sidequest_action_animStop(obj_id player, obj_id npc) throws InterruptedException
    {
        faceTo(npc, player);
        doAnimationAction(npc, "stop");
    }
    public void disable_sidequest_action_animSalute(obj_id player, obj_id npc) throws InterruptedException
    {
        faceTo(npc, player);
        doAnimationAction(npc, "salute1");
    }
    public void disable_sidequest_action_animBye(obj_id player, obj_id npc) throws InterruptedException
    {
        faceTo(npc, player);
        doAnimationAction(npc, "goodbye");
    }
    public void disable_sidequest_action_animBothSalute(obj_id player, obj_id npc) throws InterruptedException
    {
        faceTo(npc, player);
        doAnimationAction(npc, "salute1");
        doAnimationAction(player, "salute1");
    }
    public void disable_sidequest_action_animDisgust(obj_id player, obj_id npc) throws InterruptedException
    {
        faceTo(npc, player);
        doAnimationAction(npc, "shake_head_disgust");
    }
    public void disable_sidequest_action_animGestureWild(obj_id player, obj_id npc) throws InterruptedException
    {
        faceTo(npc, player);
        doAnimationAction(npc, "gesticulate_wildly");
    }
    public void disable_sidequest_action_animPointAway(obj_id player, obj_id npc) throws InterruptedException
    {
        doAnimationAction(npc, "point_away");
    }
    public void disable_sidequest_action_animPoundFist(obj_id player, obj_id npc) throws InterruptedException
    {
        faceTo(npc, player);
        doAnimationAction(npc, "pound_fist_palm");
    }
    public void disable_sidequest_action_animRubChin(obj_id player, obj_id npc) throws InterruptedException
    {
        faceTo(npc, player);
        doAnimationAction(npc, "rub_chin_thoughtful");
    }
    public void disable_sidequest_action_animPoliteApplause(obj_id player, obj_id npc) throws InterruptedException
    {
        faceTo(npc, player);
        doAnimationAction(player, "salute1");
        doAnimationAction(npc, "applause_polite");
    }
    public void disable_sidequest_action_animWaveFinger(obj_id player, obj_id npc) throws InterruptedException
    {
        faceTo(npc, player);
        doAnimationAction(npc, "wave_finger_warning");
    }
    public void disable_sidequest_action_animRaiseFist(obj_id player, obj_id npc) throws InterruptedException
    {
        faceTo(npc, player);
        doAnimationAction(npc, "standing_raise_fist");
    }
    public int disable_sidequest_handleBranch2(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_c9e7c48f"))
        {
            if (disable_sidequest_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_5ee9bf09");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (disable_sidequest_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_26966a66");
                    }
                    setObjVar(player, "conversation.disable_sidequest.branchId", 3);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    removeObjVar(player, "conversation.disable_sidequest.branchId");
                    npcSpeak(player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int disable_sidequest_handleBranch3(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_26966a66"))
        {
            if (disable_sidequest_condition__defaultCondition(player, npc))
            {
                disable_sidequest_action_animStop(player, npc);
                string_id message = new string_id(c_stringFile, "s_9b809db7");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (disable_sidequest_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_1224b289");
                    }
                    setObjVar(player, "conversation.disable_sidequest.branchId", 4);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    removeObjVar(player, "conversation.disable_sidequest.branchId");
                    npcSpeak(player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int disable_sidequest_handleBranch4(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_1224b289"))
        {
            if (disable_sidequest_condition__defaultCondition(player, npc))
            {
                disable_sidequest_action_animExplain(player, npc);
                string_id message = new string_id(c_stringFile, "s_3e25bf08");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (disable_sidequest_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_6a5944bd");
                    }
                    setObjVar(player, "conversation.disable_sidequest.branchId", 5);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    removeObjVar(player, "conversation.disable_sidequest.branchId");
                    npcSpeak(player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int disable_sidequest_handleBranch5(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_6a5944bd"))
        {
            if (disable_sidequest_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_c85fb291");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (disable_sidequest_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_a54ac5ac");
                    }
                    setObjVar(player, "conversation.disable_sidequest.branchId", 6);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    removeObjVar(player, "conversation.disable_sidequest.branchId");
                    npcSpeak(player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int disable_sidequest_handleBranch6(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_a54ac5ac"))
        {
            if (disable_sidequest_condition__defaultCondition(player, npc))
            {
                disable_sidequest_action_giveRewardMission1(player, npc);
                string_id message = new string_id(c_stringFile, "s_3019b01e");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (disable_sidequest_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_d28c8e75");
                    }
                    setObjVar(player, "conversation.disable_sidequest.branchId", 7);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    removeObjVar(player, "conversation.disable_sidequest.branchId");
                    npcSpeak(player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int disable_sidequest_handleBranch7(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_d28c8e75"))
        {
            if (disable_sidequest_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_5eec7e62");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (disable_sidequest_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_72b585f7");
                    }
                    setObjVar(player, "conversation.disable_sidequest.branchId", 8);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    removeObjVar(player, "conversation.disable_sidequest.branchId");
                    npcSpeak(player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int disable_sidequest_handleBranch8(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_72b585f7"))
        {
            if (disable_sidequest_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_de5882b1");
                removeObjVar(player, "conversation.disable_sidequest.branchId");
                npcSpeak(player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int disable_sidequest_handleBranch10(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_6b12b256"))
        {
            if (disable_sidequest_condition__defaultCondition(player, npc))
            {
                disable_sidequest_action_animConverse(player, npc);
                string_id message = new string_id(c_stringFile, "s_3e0deaf9");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (disable_sidequest_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_6ea28bdf");
                    }
                    setObjVar(player, "conversation.disable_sidequest.branchId", 11);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    removeObjVar(player, "conversation.disable_sidequest.branchId");
                    npcSpeak(player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int disable_sidequest_handleBranch11(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_6ea28bdf"))
        {
            if (disable_sidequest_condition__defaultCondition(player, npc))
            {
                disable_sidequest_action_animNodHead(player, npc);
                string_id message = new string_id(c_stringFile, "s_a893010");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (disable_sidequest_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_a4f3fede");
                    }
                    setObjVar(player, "conversation.disable_sidequest.branchId", 12);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    removeObjVar(player, "conversation.disable_sidequest.branchId");
                    npcSpeak(player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int disable_sidequest_handleBranch12(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_a4f3fede"))
        {
            if (disable_sidequest_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_897e7261");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (disable_sidequest_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_a017debb");
                    }
                    setObjVar(player, "conversation.disable_sidequest.branchId", 13);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    removeObjVar(player, "conversation.disable_sidequest.branchId");
                    npcSpeak(player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int disable_sidequest_handleBranch13(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_a017debb"))
        {
            if (disable_sidequest_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_53d10098");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (disable_sidequest_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_46712ca3");
                    }
                    setObjVar(player, "conversation.disable_sidequest.branchId", 14);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    removeObjVar(player, "conversation.disable_sidequest.branchId");
                    npcSpeak(player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int disable_sidequest_handleBranch14(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_46712ca3"))
        {
            if (disable_sidequest_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_2bc613ea");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (disable_sidequest_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_9eee12c7");
                    }
                    setObjVar(player, "conversation.disable_sidequest.branchId", 15);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    removeObjVar(player, "conversation.disable_sidequest.branchId");
                    npcSpeak(player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int disable_sidequest_handleBranch15(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_9eee12c7"))
        {
            if (disable_sidequest_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_b1ed67bc");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (disable_sidequest_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_c5495de6");
                    }
                    setObjVar(player, "conversation.disable_sidequest.branchId", 16);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    removeObjVar(player, "conversation.disable_sidequest.branchId");
                    npcSpeak(player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int disable_sidequest_handleBranch16(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_c5495de6"))
        {
            if (disable_sidequest_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_3d54f7bd");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (disable_sidequest_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_907d29f3");
                    }
                    setObjVar(player, "conversation.disable_sidequest.branchId", 17);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    removeObjVar(player, "conversation.disable_sidequest.branchId");
                    npcSpeak(player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int disable_sidequest_handleBranch17(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_907d29f3"))
        {
            if (disable_sidequest_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_96d51a4e");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (disable_sidequest_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_d92cbbea");
                    }
                    setObjVar(player, "conversation.disable_sidequest.branchId", 19);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    removeObjVar(player, "conversation.disable_sidequest.branchId");
                    npcSpeak(player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int disable_sidequest_handleBranch18(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_5d0b3789"))
        {
            if (disable_sidequest_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_96d51a4e");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (disable_sidequest_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_d92cbbea");
                    }
                    setObjVar(player, "conversation.disable_sidequest.branchId", 19);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    removeObjVar(player, "conversation.disable_sidequest.branchId");
                    npcSpeak(player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int disable_sidequest_handleBranch19(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_d92cbbea"))
        {
            if (disable_sidequest_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_76eb7e3b");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (disable_sidequest_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_192752ed");
                    }
                    setObjVar(player, "conversation.disable_sidequest.branchId", 20);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    removeObjVar(player, "conversation.disable_sidequest.branchId");
                    npcSpeak(player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int disable_sidequest_handleBranch20(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_192752ed"))
        {
            if (disable_sidequest_condition__defaultCondition(player, npc))
            {
                disable_sidequest_action_grantMission1(player, npc);
                string_id message = new string_id(c_stringFile, "s_1e6f479b");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (disable_sidequest_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_2b9397ba");
                    }
                    setObjVar(player, "conversation.disable_sidequest.branchId", 21);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    removeObjVar(player, "conversation.disable_sidequest.branchId");
                    npcSpeak(player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int disable_sidequest_handleBranch21(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_2b9397ba"))
        {
            if (disable_sidequest_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_648ee28b");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (disable_sidequest_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_16148f5c");
                    }
                    setObjVar(player, "conversation.disable_sidequest.branchId", 22);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    removeObjVar(player, "conversation.disable_sidequest.branchId");
                    npcSpeak(player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int disable_sidequest_handleBranch22(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_16148f5c"))
        {
            if (disable_sidequest_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_eeeb19c9");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (disable_sidequest_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_376a2b9c");
                    }
                    setObjVar(player, "conversation.disable_sidequest.branchId", 23);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    removeObjVar(player, "conversation.disable_sidequest.branchId");
                    npcSpeak(player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int disable_sidequest_handleBranch23(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_376a2b9c"))
        {
            if (disable_sidequest_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_90c59f1a");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (disable_sidequest_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_3b78667c");
                    }
                    setObjVar(player, "conversation.disable_sidequest.branchId", 24);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    removeObjVar(player, "conversation.disable_sidequest.branchId");
                    npcSpeak(player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int disable_sidequest_handleBranch24(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_3b78667c"))
        {
            if (disable_sidequest_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_d1b59965");
                removeObjVar(player, "conversation.disable_sidequest.branchId");
                npcSpeak(player, message);
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
            detachScript(self, "conversation.disable_sidequest");
        }
        setCondition(self, CONDITION_CONVERSABLE);
        setInvulnerable(self, true);
        setCondition(self, CONDITION_SPACE_INTERESTING);
        return SCRIPT_CONTINUE;
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        setCondition(self, CONDITION_CONVERSABLE);
        setInvulnerable(self, true);
        setCondition(self, CONDITION_SPACE_INTERESTING);
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
        detachScript(self, "conversation.disable_sidequest");
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
        if (disable_sidequest_condition_hasReward(player, npc))
        {
            disable_sidequest_action_animBothSalute(player, npc);
            string_id message = new string_id(c_stringFile, "s_b451a4a3");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        if (disable_sidequest_condition_hasWonMission1(player, npc))
        {
            disable_sidequest_action_animBothSalute(player, npc);
            string_id message = new string_id(c_stringFile, "s_c55d7e2");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (disable_sidequest_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_c9e7c48f");
                }
                setObjVar(player, "conversation.disable_sidequest.branchId", 2);
                npcStartConversation(player, npc, "disable_sidequest", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (disable_sidequest_condition__defaultCondition(player, npc))
        {
            disable_sidequest_action_animNodHead(player, npc);
            string_id message = new string_id(c_stringFile, "s_3c6ee0a0");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (disable_sidequest_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_6b12b256");
                }
                setObjVar(player, "conversation.disable_sidequest.branchId", 10);
                npcStartConversation(player, npc, "disable_sidequest", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (disable_sidequest_condition_readyForMission2(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_5d0b3789");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (disable_sidequest_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_5d0b3789");
                }
                setObjVar(player, "conversation.disable_sidequest.branchId", 18);
                npcStartConversation(player, npc, "disable_sidequest", message, responses);
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
        if (!conversationId.equals("disable_sidequest"))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id npc = self;
        int branchId = getIntObjVar(player, "conversation.disable_sidequest.branchId");
        if (branchId == 2 && disable_sidequest_handleBranch2(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 3 && disable_sidequest_handleBranch3(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 4 && disable_sidequest_handleBranch4(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 5 && disable_sidequest_handleBranch5(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 6 && disable_sidequest_handleBranch6(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 7 && disable_sidequest_handleBranch7(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 8 && disable_sidequest_handleBranch8(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 10 && disable_sidequest_handleBranch10(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 11 && disable_sidequest_handleBranch11(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 12 && disable_sidequest_handleBranch12(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 13 && disable_sidequest_handleBranch13(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 14 && disable_sidequest_handleBranch14(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 15 && disable_sidequest_handleBranch15(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 16 && disable_sidequest_handleBranch16(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 17 && disable_sidequest_handleBranch17(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 18 && disable_sidequest_handleBranch18(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 19 && disable_sidequest_handleBranch19(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 20 && disable_sidequest_handleBranch20(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 21 && disable_sidequest_handleBranch21(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 22 && disable_sidequest_handleBranch22(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 23 && disable_sidequest_handleBranch23(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 24 && disable_sidequest_handleBranch24(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        chat.chat(npc, "Error:  Fell through all branches and responses for OnNpcConversationResponse.");
        removeObjVar(player, "conversation.disable_sidequest.branchId");
        return SCRIPT_CONTINUE;
    }
}
