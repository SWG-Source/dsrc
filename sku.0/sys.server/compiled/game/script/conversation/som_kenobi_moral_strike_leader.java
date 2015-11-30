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

public class som_kenobi_moral_strike_leader extends script.base_script
{
    public som_kenobi_moral_strike_leader()
    {
    }
    public static String c_stringFile = "conversation/som_kenobi_moral_strike_leader";
    public boolean som_kenobi_moral_strike_leader_condition__defaultCondition(obj_id player, obj_id npc) throws InterruptedException
    {
        return true;
    }
    public boolean som_kenobi_moral_strike_leader_condition_onTaskDestroy(obj_id player, obj_id npc) throws InterruptedException
    {
        return groundquests.isTaskActive(player, "som_kenobi_moral_choice_1", "needDestroy");
    }
    public boolean som_kenobi_moral_strike_leader_condition_haveCore(obj_id player, obj_id npc) throws InterruptedException
    {
        return groundquests.isTaskActive(player, "som_kenobi_moral_choice_1", "gotCore");
    }
    public boolean som_kenobi_moral_strike_leader_condition_done(obj_id player, obj_id npc) throws InterruptedException
    {
        return groundquests.hasCompletedQuest(player, "som_kenobi_moral_choice_1");
    }
    public boolean som_kenobi_moral_strike_leader_condition_onUploadTask(obj_id player, obj_id npc) throws InterruptedException
    {
        return groundquests.isTaskActive(player, "som_kenobi_moral_choice_1", "switchedSides");
    }
    public boolean som_kenobi_moral_strike_leader_condition_onQuest(obj_id player, obj_id npc) throws InterruptedException
    {
        return groundquests.isQuestActive(player, "som_kenobi_moral_choice_1");
    }
    public boolean som_kenobi_moral_strike_leader_condition_missionDone(obj_id player, obj_id npc) throws InterruptedException
    {
        return groundquests.isTaskActive(player, "som_kenobi_moral_choice_1", "talkLeader2");
    }
    public void som_kenobi_moral_strike_leader_action_switched(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.sendSignal(player, "talkedLeader");
    }
    public void som_kenobi_moral_strike_leader_action_removeDirty(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.failTask(player, "som_kenobi_moral_choice_1", "needDestroy");
    }
    public void som_kenobi_moral_strike_leader_action_reward(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.sendSignal(player, "talkedLeader2");
    }
    public int som_kenobi_moral_strike_leader_handleBranch2(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_209"))
        {
            if (som_kenobi_moral_strike_leader_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "nod");
                som_kenobi_moral_strike_leader_action_reward(player, npc);
                string_id message = new string_id(c_stringFile, "s_210");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (som_kenobi_moral_strike_leader_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_211");
                    }
                    utils.setScriptVar(player, "conversation.som_kenobi_moral_strike_leader.branchId", 3);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.som_kenobi_moral_strike_leader.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int som_kenobi_moral_strike_leader_handleBranch3(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_211"))
        {
            doAnimationAction(player, "thank");
            if (som_kenobi_moral_strike_leader_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "thank");
                string_id message = new string_id(c_stringFile, "s_212");
                utils.removeScriptVar(player, "conversation.som_kenobi_moral_strike_leader.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int som_kenobi_moral_strike_leader_handleBranch6(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_173"))
        {
            doAnimationAction(player, "shrug_hands");
            if (som_kenobi_moral_strike_leader_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "shake_head_no");
                string_id message = new string_id(c_stringFile, "s_177");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (som_kenobi_moral_strike_leader_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (som_kenobi_moral_strike_leader_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_179");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_181");
                    }
                    utils.setScriptVar(player, "conversation.som_kenobi_moral_strike_leader.branchId", 7);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.som_kenobi_moral_strike_leader.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_175"))
        {
            if (som_kenobi_moral_strike_leader_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_214");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (som_kenobi_moral_strike_leader_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_216");
                    }
                    utils.setScriptVar(player, "conversation.som_kenobi_moral_strike_leader.branchId", 19);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.som_kenobi_moral_strike_leader.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int som_kenobi_moral_strike_leader_handleBranch7(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_179"))
        {
            if (som_kenobi_moral_strike_leader_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_185");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (som_kenobi_moral_strike_leader_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (som_kenobi_moral_strike_leader_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_187");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_188");
                    }
                    utils.setScriptVar(player, "conversation.som_kenobi_moral_strike_leader.branchId", 8);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.som_kenobi_moral_strike_leader.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_181"))
        {
            doAnimationAction(player, "nod");
            if (som_kenobi_moral_strike_leader_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "goodbye");
                string_id message = new string_id(c_stringFile, "s_183");
                utils.removeScriptVar(player, "conversation.som_kenobi_moral_strike_leader.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int som_kenobi_moral_strike_leader_handleBranch8(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_187"))
        {
            if (som_kenobi_moral_strike_leader_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "rub_chin_thoughtful");
                string_id message = new string_id(c_stringFile, "s_191");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (som_kenobi_moral_strike_leader_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (som_kenobi_moral_strike_leader_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_192");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_193");
                    }
                    utils.setScriptVar(player, "conversation.som_kenobi_moral_strike_leader.branchId", 9);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.som_kenobi_moral_strike_leader.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_188"))
        {
            doAnimationAction(player, "rub_chin_thoughtful");
            if (som_kenobi_moral_strike_leader_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "goodbye");
                string_id message = new string_id(c_stringFile, "s_190");
                utils.removeScriptVar(player, "conversation.som_kenobi_moral_strike_leader.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int som_kenobi_moral_strike_leader_handleBranch9(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_192"))
        {
            if (som_kenobi_moral_strike_leader_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "stamp_feet");
                string_id message = new string_id(c_stringFile, "s_198");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (som_kenobi_moral_strike_leader_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_199");
                    }
                    utils.setScriptVar(player, "conversation.som_kenobi_moral_strike_leader.branchId", 10);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.som_kenobi_moral_strike_leader.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_193"))
        {
            if (som_kenobi_moral_strike_leader_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_194");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (som_kenobi_moral_strike_leader_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (som_kenobi_moral_strike_leader_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_195");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_196");
                    }
                    utils.setScriptVar(player, "conversation.som_kenobi_moral_strike_leader.branchId", 15);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.som_kenobi_moral_strike_leader.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int som_kenobi_moral_strike_leader_handleBranch10(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_199"))
        {
            if (som_kenobi_moral_strike_leader_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_200");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (som_kenobi_moral_strike_leader_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_201");
                    }
                    utils.setScriptVar(player, "conversation.som_kenobi_moral_strike_leader.branchId", 11);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.som_kenobi_moral_strike_leader.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int som_kenobi_moral_strike_leader_handleBranch11(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_201"))
        {
            doAnimationAction(player, "nod");
            if (som_kenobi_moral_strike_leader_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_202");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (som_kenobi_moral_strike_leader_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_203");
                    }
                    utils.setScriptVar(player, "conversation.som_kenobi_moral_strike_leader.branchId", 12);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.som_kenobi_moral_strike_leader.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int som_kenobi_moral_strike_leader_handleBranch12(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_203"))
        {
            if (som_kenobi_moral_strike_leader_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "nod");
                string_id message = new string_id(c_stringFile, "s_204");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (som_kenobi_moral_strike_leader_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_205");
                    }
                    utils.setScriptVar(player, "conversation.som_kenobi_moral_strike_leader.branchId", 13);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.som_kenobi_moral_strike_leader.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int som_kenobi_moral_strike_leader_handleBranch13(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_205"))
        {
            som_kenobi_moral_strike_leader_action_switched(player, npc);
            if (som_kenobi_moral_strike_leader_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "goodbye");
                doAnimationAction(player, "goodbye");
                som_kenobi_moral_strike_leader_action_removeDirty(player, npc);
                string_id message = new string_id(c_stringFile, "s_206");
                utils.removeScriptVar(player, "conversation.som_kenobi_moral_strike_leader.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int som_kenobi_moral_strike_leader_handleBranch15(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_195"))
        {
            if (som_kenobi_moral_strike_leader_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "stamp_feet");
                string_id message = new string_id(c_stringFile, "s_198");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (som_kenobi_moral_strike_leader_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_199");
                    }
                    utils.setScriptVar(player, "conversation.som_kenobi_moral_strike_leader.branchId", 10);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.som_kenobi_moral_strike_leader.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_196"))
        {
            doAnimationAction(player, "nod");
            if (som_kenobi_moral_strike_leader_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "goodbye");
                doAnimationAction(player, "goodbye");
                string_id message = new string_id(c_stringFile, "s_197");
                utils.removeScriptVar(player, "conversation.som_kenobi_moral_strike_leader.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int som_kenobi_moral_strike_leader_handleBranch19(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_216"))
        {
            if (som_kenobi_moral_strike_leader_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_261");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (som_kenobi_moral_strike_leader_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_262");
                    }
                    utils.setScriptVar(player, "conversation.som_kenobi_moral_strike_leader.branchId", 20);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.som_kenobi_moral_strike_leader.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int som_kenobi_moral_strike_leader_handleBranch20(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_262"))
        {
            if (som_kenobi_moral_strike_leader_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_264");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (som_kenobi_moral_strike_leader_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (som_kenobi_moral_strike_leader_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_266");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_300");
                    }
                    utils.setScriptVar(player, "conversation.som_kenobi_moral_strike_leader.branchId", 21);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.som_kenobi_moral_strike_leader.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int som_kenobi_moral_strike_leader_handleBranch21(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_266"))
        {
            if (som_kenobi_moral_strike_leader_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "rub_chin_thoughtful");
                string_id message = new string_id(c_stringFile, "s_268");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (som_kenobi_moral_strike_leader_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (som_kenobi_moral_strike_leader_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_270");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_290");
                    }
                    utils.setScriptVar(player, "conversation.som_kenobi_moral_strike_leader.branchId", 22);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.som_kenobi_moral_strike_leader.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_300"))
        {
            doAnimationAction(player, "rub_chin_thoughtful");
            if (som_kenobi_moral_strike_leader_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "goodbye");
                string_id message = new string_id(c_stringFile, "s_302");
                utils.removeScriptVar(player, "conversation.som_kenobi_moral_strike_leader.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int som_kenobi_moral_strike_leader_handleBranch22(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_270"))
        {
            if (som_kenobi_moral_strike_leader_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "stamp_feet");
                string_id message = new string_id(c_stringFile, "s_272");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (som_kenobi_moral_strike_leader_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_274");
                    }
                    utils.setScriptVar(player, "conversation.som_kenobi_moral_strike_leader.branchId", 23);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.som_kenobi_moral_strike_leader.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_290"))
        {
            if (som_kenobi_moral_strike_leader_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_292");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (som_kenobi_moral_strike_leader_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (som_kenobi_moral_strike_leader_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_294");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_296");
                    }
                    utils.setScriptVar(player, "conversation.som_kenobi_moral_strike_leader.branchId", 28);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.som_kenobi_moral_strike_leader.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int som_kenobi_moral_strike_leader_handleBranch23(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_274"))
        {
            if (som_kenobi_moral_strike_leader_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_276");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (som_kenobi_moral_strike_leader_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_278");
                    }
                    utils.setScriptVar(player, "conversation.som_kenobi_moral_strike_leader.branchId", 24);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.som_kenobi_moral_strike_leader.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int som_kenobi_moral_strike_leader_handleBranch24(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_278"))
        {
            doAnimationAction(player, "nod");
            if (som_kenobi_moral_strike_leader_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_280");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (som_kenobi_moral_strike_leader_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_282");
                    }
                    utils.setScriptVar(player, "conversation.som_kenobi_moral_strike_leader.branchId", 25);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.som_kenobi_moral_strike_leader.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int som_kenobi_moral_strike_leader_handleBranch25(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_282"))
        {
            if (som_kenobi_moral_strike_leader_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "nod");
                string_id message = new string_id(c_stringFile, "s_284");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (som_kenobi_moral_strike_leader_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_286");
                    }
                    utils.setScriptVar(player, "conversation.som_kenobi_moral_strike_leader.branchId", 26);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.som_kenobi_moral_strike_leader.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int som_kenobi_moral_strike_leader_handleBranch26(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_286"))
        {
            som_kenobi_moral_strike_leader_action_switched(player, npc);
            if (som_kenobi_moral_strike_leader_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "goodbye");
                doAnimationAction(player, "goodbye");
                string_id message = new string_id(c_stringFile, "s_288");
                utils.removeScriptVar(player, "conversation.som_kenobi_moral_strike_leader.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int som_kenobi_moral_strike_leader_handleBranch28(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_294"))
        {
            if (som_kenobi_moral_strike_leader_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "stamp_feet");
                string_id message = new string_id(c_stringFile, "s_272");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (som_kenobi_moral_strike_leader_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_274");
                    }
                    utils.setScriptVar(player, "conversation.som_kenobi_moral_strike_leader.branchId", 23);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.som_kenobi_moral_strike_leader.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_296"))
        {
            doAnimationAction(player, "nod");
            if (som_kenobi_moral_strike_leader_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "goodbye");
                doAnimationAction(player, "goodbye");
                string_id message = new string_id(c_stringFile, "s_298");
                utils.removeScriptVar(player, "conversation.som_kenobi_moral_strike_leader.branchId");
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
            detachScript(self, "conversation.som_kenobi_moral_strike_leader");
        }
        setCondition(self, CONDITION_CONVERSABLE);
        setInvulnerable(self, true);
        return SCRIPT_CONTINUE;
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        setCondition(self, CONDITION_CONVERSABLE);
        setInvulnerable(self, true);
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
        detachScript(self, "conversation.som_kenobi_moral_strike_leader");
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
        if (som_kenobi_moral_strike_leader_condition_done(player, npc))
        {
            doAnimationAction(npc, "dismiss");
            string_id message = new string_id(c_stringFile, "s_171");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        if (som_kenobi_moral_strike_leader_condition_missionDone(player, npc))
        {
            doAnimationAction(npc, "greet");
            doAnimationAction(player, "greet");
            string_id message = new string_id(c_stringFile, "s_208");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (som_kenobi_moral_strike_leader_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_209");
                }
                utils.setScriptVar(player, "conversation.som_kenobi_moral_strike_leader.branchId", 2);
                npcStartConversation(player, npc, "som_kenobi_moral_strike_leader", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (som_kenobi_moral_strike_leader_condition_onUploadTask(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_207");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        if (som_kenobi_moral_strike_leader_condition_onTaskDestroy(player, npc))
        {
            doAnimationAction(npc, "nod_head_once");
            doAnimationAction(player, "greet");
            string_id message = new string_id(c_stringFile, "s_140");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (som_kenobi_moral_strike_leader_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            boolean hasResponse1 = false;
            if (som_kenobi_moral_strike_leader_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_173");
                }
                if (hasResponse1)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_175");
                }
                utils.setScriptVar(player, "conversation.som_kenobi_moral_strike_leader.branchId", 6);
                npcStartConversation(player, npc, "som_kenobi_moral_strike_leader", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (som_kenobi_moral_strike_leader_condition_onQuest(player, npc))
        {
            doAnimationAction(npc, "point_accusingly");
            string_id message = new string_id(c_stringFile, "s_139");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        if (som_kenobi_moral_strike_leader_condition__defaultCondition(player, npc))
        {
            doAnimationAction(npc, "point_forward");
            doAnimationAction(player, "greet");
            string_id message = new string_id(c_stringFile, "s_110");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        chat.chat(npc, "Error:  All conditions for OnStartNpcConversation were false.");
        return SCRIPT_CONTINUE;
    }
    public int OnNpcConversationResponse(obj_id self, String conversationId, obj_id player, string_id response) throws InterruptedException
    {
        if (!conversationId.equals("som_kenobi_moral_strike_leader"))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id npc = self;
        int branchId = utils.getIntScriptVar(player, "conversation.som_kenobi_moral_strike_leader.branchId");
        if (branchId == 2 && som_kenobi_moral_strike_leader_handleBranch2(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 3 && som_kenobi_moral_strike_leader_handleBranch3(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 6 && som_kenobi_moral_strike_leader_handleBranch6(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 7 && som_kenobi_moral_strike_leader_handleBranch7(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 8 && som_kenobi_moral_strike_leader_handleBranch8(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 9 && som_kenobi_moral_strike_leader_handleBranch9(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 10 && som_kenobi_moral_strike_leader_handleBranch10(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 11 && som_kenobi_moral_strike_leader_handleBranch11(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 12 && som_kenobi_moral_strike_leader_handleBranch12(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 13 && som_kenobi_moral_strike_leader_handleBranch13(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 15 && som_kenobi_moral_strike_leader_handleBranch15(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 19 && som_kenobi_moral_strike_leader_handleBranch19(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 20 && som_kenobi_moral_strike_leader_handleBranch20(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 21 && som_kenobi_moral_strike_leader_handleBranch21(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 22 && som_kenobi_moral_strike_leader_handleBranch22(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 23 && som_kenobi_moral_strike_leader_handleBranch23(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 24 && som_kenobi_moral_strike_leader_handleBranch24(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 25 && som_kenobi_moral_strike_leader_handleBranch25(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 26 && som_kenobi_moral_strike_leader_handleBranch26(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 28 && som_kenobi_moral_strike_leader_handleBranch28(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        chat.chat(npc, "Error:  Fell through all branches and responses for OnNpcConversationResponse.");
        utils.removeScriptVar(player, "conversation.som_kenobi_moral_strike_leader.branchId");
        return SCRIPT_CONTINUE;
    }
}
