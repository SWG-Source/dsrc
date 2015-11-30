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
import script.library.jedi_trials;
import script.library.utils;

public class padawan_sob_story_01 extends script.base_script
{
    public padawan_sob_story_01()
    {
    }
    public static String c_stringFile = "conversation/padawan_sob_story_01";
    public boolean padawan_sob_story_01_condition__defaultCondition(obj_id player, obj_id npc) throws InterruptedException
    {
        return true;
    }
    public boolean padawan_sob_story_01_condition_acceptedQuest(obj_id player, obj_id npc) throws InterruptedException
    {
        obj_id trialPlayer = getObjIdObjVar(npc, jedi_trials.PADAWAN_TRIAL_PLAYER_OBJVAR);
        if (player == trialPlayer)
        {
            return hasObjVar(player, "jedi_trials.padawan_trials.temp.acceptedTask");
        }
        return false;
    }
    public boolean padawan_sob_story_01_condition_isTrialPlayer(obj_id player, obj_id npc) throws InterruptedException
    {
        obj_id trialPlayer = getObjIdObjVar(npc, jedi_trials.PADAWAN_TRIAL_PLAYER_OBJVAR);
        if (player == trialPlayer)
        {
            String trialName = jedi_trials.getJediTrialName(player);
            if (trialName != null && trialName.equals("sob_story"))
            {
                return !hasObjVar(npc, "padawan_trials.playerFailed");
            }
        }
        return false;
    }
    public boolean padawan_sob_story_01_condition_spokeToNpc(obj_id player, obj_id npc) throws InterruptedException
    {
        obj_id trialPlayer = getObjIdObjVar(npc, jedi_trials.PADAWAN_TRIAL_PLAYER_OBJVAR);
        if (player == trialPlayer)
        {
            return hasObjVar(player, "jedi_trials.padawan_trials.temp.spokeToTarget_01");
        }
        return false;
    }
    public boolean padawan_sob_story_01_condition_completedQuest(obj_id player, obj_id npc) throws InterruptedException
    {
        obj_id trialPlayer = getObjIdObjVar(npc, jedi_trials.PADAWAN_TRIAL_PLAYER_OBJVAR);
        if (player == trialPlayer)
        {
            String trialName = jedi_trials.getJediTrialName(player);
            if (trialName != null && !trialName.equals("sob_story"))
            {
                return hasObjVar(npc, "padawan_trials.playerSucceeded");
            }
        }
        return false;
    }
    public void padawan_sob_story_01_action_questFailure(obj_id player, obj_id npc) throws InterruptedException
    {
        setObjVar(npc, "padawan_trials.playerFailed", true);
        messageTo(player, "handleQuestFailure", null, 1, false);
        return;
    }
    public void padawan_sob_story_01_action_questSuccess(obj_id player, obj_id npc) throws InterruptedException
    {
        setObjVar(npc, "padawan_trials.playerSucceeded", true);
        messageTo(player, "handleTrialComplete", null, 1, false);
        return;
    }
    public void padawan_sob_story_01_action_sendToNextLoc(obj_id player, obj_id npc) throws InterruptedException
    {
        setObjVar(npc, "padawan_trials.playerAccepted", true);
        setObjVar(player, "jedi_trials.padawan_trials.temp.acceptedTask", true);
        messageTo(player, "handleSetSecondLoc", null, 1, false);
        return;
    }
    public int padawan_sob_story_01_handleBranch2(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_da7087d0"))
        {
            if (padawan_sob_story_01_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "weeping");
                padawan_sob_story_01_action_questSuccess(player, npc);
                string_id message = new string_id(c_stringFile, "s_91b4a77f");
                utils.removeScriptVar(player, "conversation.padawan_sob_story_01.branchId");
                chat.chat(npc, player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int padawan_sob_story_01_handleBranch4(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_e6ae286a"))
        {
            if (padawan_sob_story_01_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_e2b8c011");
                utils.removeScriptVar(player, "conversation.padawan_sob_story_01.branchId");
                chat.chat(npc, player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_2438dea0"))
        {
            if (padawan_sob_story_01_condition__defaultCondition(player, npc))
            {
                padawan_sob_story_01_action_questFailure(player, npc);
                string_id message = new string_id(c_stringFile, "s_5bd31ce");
                utils.removeScriptVar(player, "conversation.padawan_sob_story_01.branchId");
                chat.chat(npc, player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int padawan_sob_story_01_handleBranch7(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_36a4e374"))
        {
            if (padawan_sob_story_01_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_dbff867f");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (padawan_sob_story_01_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (padawan_sob_story_01_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_390df542");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_f89b1ce4");
                    }
                    utils.setScriptVar(player, "conversation.padawan_sob_story_01.branchId", 8);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.padawan_sob_story_01.branchId");
                    chat.chat(npc, player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_b7139d72"))
        {
            if (padawan_sob_story_01_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_ab866ec6");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (padawan_sob_story_01_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_2e220ef9");
                    }
                    utils.setScriptVar(player, "conversation.padawan_sob_story_01.branchId", 22);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.padawan_sob_story_01.branchId");
                    chat.chat(npc, player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int padawan_sob_story_01_handleBranch8(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_390df542"))
        {
            if (padawan_sob_story_01_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_83d29997");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (padawan_sob_story_01_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (padawan_sob_story_01_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_291d0c7a");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_8de1965a");
                    }
                    utils.setScriptVar(player, "conversation.padawan_sob_story_01.branchId", 9);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.padawan_sob_story_01.branchId");
                    chat.chat(npc, player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_f89b1ce4"))
        {
            if (padawan_sob_story_01_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_72071926");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (padawan_sob_story_01_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (padawan_sob_story_01_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_6845d2f7");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_94c910a4");
                    }
                    utils.setScriptVar(player, "conversation.padawan_sob_story_01.branchId", 19);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.padawan_sob_story_01.branchId");
                    chat.chat(npc, player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int padawan_sob_story_01_handleBranch9(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_291d0c7a"))
        {
            if (padawan_sob_story_01_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_ae1fd723");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (padawan_sob_story_01_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (padawan_sob_story_01_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_1cf278e9");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_9a6c3b2a");
                    }
                    utils.setScriptVar(player, "conversation.padawan_sob_story_01.branchId", 10);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.padawan_sob_story_01.branchId");
                    chat.chat(npc, player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_8de1965a"))
        {
            if (padawan_sob_story_01_condition__defaultCondition(player, npc))
            {
                padawan_sob_story_01_action_questFailure(player, npc);
                string_id message = new string_id(c_stringFile, "s_164cf4c2");
                utils.removeScriptVar(player, "conversation.padawan_sob_story_01.branchId");
                chat.chat(npc, player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int padawan_sob_story_01_handleBranch10(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_1cf278e9"))
        {
            if (padawan_sob_story_01_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_182378be");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (padawan_sob_story_01_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_5bdbb8de");
                    }
                    utils.setScriptVar(player, "conversation.padawan_sob_story_01.branchId", 11);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.padawan_sob_story_01.branchId");
                    chat.chat(npc, player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_9a6c3b2a"))
        {
            if (padawan_sob_story_01_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_4f7b1e26");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (padawan_sob_story_01_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (padawan_sob_story_01_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_25");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_9525d947");
                    }
                    utils.setScriptVar(player, "conversation.padawan_sob_story_01.branchId", 13);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.padawan_sob_story_01.branchId");
                    chat.chat(npc, player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int padawan_sob_story_01_handleBranch11(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_5bdbb8de"))
        {
            if (padawan_sob_story_01_condition__defaultCondition(player, npc))
            {
                padawan_sob_story_01_action_sendToNextLoc(player, npc);
                string_id message = new string_id(c_stringFile, "s_b7015989");
                utils.removeScriptVar(player, "conversation.padawan_sob_story_01.branchId");
                chat.chat(npc, player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int padawan_sob_story_01_handleBranch13(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_25"))
        {
            if (padawan_sob_story_01_condition__defaultCondition(player, npc))
            {
                padawan_sob_story_01_action_sendToNextLoc(player, npc);
                string_id message = new string_id(c_stringFile, "s_be83966b");
                utils.removeScriptVar(player, "conversation.padawan_sob_story_01.branchId");
                chat.chat(npc, player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_9525d947"))
        {
            if (padawan_sob_story_01_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_b6e31aa4");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (padawan_sob_story_01_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (padawan_sob_story_01_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_a128b86a");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_290ad18b");
                    }
                    utils.setScriptVar(player, "conversation.padawan_sob_story_01.branchId", 15);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.padawan_sob_story_01.branchId");
                    chat.chat(npc, player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int padawan_sob_story_01_handleBranch15(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_a128b86a"))
        {
            if (padawan_sob_story_01_condition__defaultCondition(player, npc))
            {
                padawan_sob_story_01_action_sendToNextLoc(player, npc);
                string_id message = new string_id(c_stringFile, "s_31");
                utils.removeScriptVar(player, "conversation.padawan_sob_story_01.branchId");
                chat.chat(npc, player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_290ad18b"))
        {
            if (padawan_sob_story_01_condition__defaultCondition(player, npc))
            {
                padawan_sob_story_01_action_questFailure(player, npc);
                string_id message = new string_id(c_stringFile, "s_10163a72");
                utils.removeScriptVar(player, "conversation.padawan_sob_story_01.branchId");
                chat.chat(npc, player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int padawan_sob_story_01_handleBranch19(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_6845d2f7"))
        {
            if (padawan_sob_story_01_condition__defaultCondition(player, npc))
            {
                padawan_sob_story_01_action_sendToNextLoc(player, npc);
                string_id message = new string_id(c_stringFile, "s_40");
                utils.removeScriptVar(player, "conversation.padawan_sob_story_01.branchId");
                chat.chat(npc, player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_94c910a4"))
        {
            if (padawan_sob_story_01_condition__defaultCondition(player, npc))
            {
                padawan_sob_story_01_action_questFailure(player, npc);
                string_id message = new string_id(c_stringFile, "s_360d601b");
                utils.removeScriptVar(player, "conversation.padawan_sob_story_01.branchId");
                chat.chat(npc, player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int padawan_sob_story_01_handleBranch22(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_2e220ef9"))
        {
            if (padawan_sob_story_01_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_6f0025f1");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (padawan_sob_story_01_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (padawan_sob_story_01_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_297f30e9");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_d3249f0b");
                    }
                    utils.setScriptVar(player, "conversation.padawan_sob_story_01.branchId", 23);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.padawan_sob_story_01.branchId");
                    chat.chat(npc, player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int padawan_sob_story_01_handleBranch23(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_297f30e9"))
        {
            if (padawan_sob_story_01_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_986473");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (padawan_sob_story_01_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (padawan_sob_story_01_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_d4cfb5b8");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_69");
                    }
                    utils.setScriptVar(player, "conversation.padawan_sob_story_01.branchId", 24);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.padawan_sob_story_01.branchId");
                    chat.chat(npc, player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_d3249f0b"))
        {
            if (padawan_sob_story_01_condition__defaultCondition(player, npc))
            {
                padawan_sob_story_01_action_questFailure(player, npc);
                string_id message = new string_id(c_stringFile, "s_ed0b42f4");
                utils.removeScriptVar(player, "conversation.padawan_sob_story_01.branchId");
                chat.chat(npc, player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int padawan_sob_story_01_handleBranch24(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_d4cfb5b8"))
        {
            if (padawan_sob_story_01_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_51");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (padawan_sob_story_01_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (padawan_sob_story_01_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_53");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_57");
                    }
                    utils.setScriptVar(player, "conversation.padawan_sob_story_01.branchId", 25);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.padawan_sob_story_01.branchId");
                    chat.chat(npc, player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_69"))
        {
            if (padawan_sob_story_01_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_71");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (padawan_sob_story_01_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_73");
                    }
                    utils.setScriptVar(player, "conversation.padawan_sob_story_01.branchId", 30);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.padawan_sob_story_01.branchId");
                    chat.chat(npc, player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int padawan_sob_story_01_handleBranch25(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_53"))
        {
            if (padawan_sob_story_01_condition__defaultCondition(player, npc))
            {
                padawan_sob_story_01_action_sendToNextLoc(player, npc);
                string_id message = new string_id(c_stringFile, "s_55");
                utils.removeScriptVar(player, "conversation.padawan_sob_story_01.branchId");
                chat.chat(npc, player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_57"))
        {
            if (padawan_sob_story_01_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_59");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (padawan_sob_story_01_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (padawan_sob_story_01_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_61");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_65");
                    }
                    utils.setScriptVar(player, "conversation.padawan_sob_story_01.branchId", 27);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.padawan_sob_story_01.branchId");
                    chat.chat(npc, player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int padawan_sob_story_01_handleBranch27(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_61"))
        {
            if (padawan_sob_story_01_condition__defaultCondition(player, npc))
            {
                padawan_sob_story_01_action_sendToNextLoc(player, npc);
                string_id message = new string_id(c_stringFile, "s_63");
                utils.removeScriptVar(player, "conversation.padawan_sob_story_01.branchId");
                chat.chat(npc, player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_65"))
        {
            if (padawan_sob_story_01_condition__defaultCondition(player, npc))
            {
                padawan_sob_story_01_action_questFailure(player, npc);
                string_id message = new string_id(c_stringFile, "s_67");
                utils.removeScriptVar(player, "conversation.padawan_sob_story_01.branchId");
                chat.chat(npc, player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int padawan_sob_story_01_handleBranch30(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_73"))
        {
            if (padawan_sob_story_01_condition__defaultCondition(player, npc))
            {
                padawan_sob_story_01_action_sendToNextLoc(player, npc);
                string_id message = new string_id(c_stringFile, "s_f8143d38");
                utils.removeScriptVar(player, "conversation.padawan_sob_story_01.branchId");
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
            detachScript(self, "conversation.padawan_sob_story_01");
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
        detachScript(self, "conversation.padawan_sob_story_01");
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
        if (padawan_sob_story_01_condition_completedQuest(player, npc))
        {
            doAnimationAction(npc, "embarrassed");
            string_id message = new string_id(c_stringFile, "s_1f8a26f8");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        if (padawan_sob_story_01_condition_spokeToNpc(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_3d146002");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (padawan_sob_story_01_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_da7087d0");
                }
                utils.setScriptVar(player, "conversation.padawan_sob_story_01.branchId", 2);
                npcStartConversation(player, npc, "padawan_sob_story_01", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (padawan_sob_story_01_condition_acceptedQuest(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_45240d04");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (padawan_sob_story_01_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            boolean hasResponse1 = false;
            if (padawan_sob_story_01_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_e6ae286a");
                }
                if (hasResponse1)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_2438dea0");
                }
                utils.setScriptVar(player, "conversation.padawan_sob_story_01.branchId", 4);
                npcStartConversation(player, npc, "padawan_sob_story_01", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (padawan_sob_story_01_condition_isTrialPlayer(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_4c1008a4");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (padawan_sob_story_01_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            boolean hasResponse1 = false;
            if (padawan_sob_story_01_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_36a4e374");
                }
                if (hasResponse1)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_b7139d72");
                }
                utils.setScriptVar(player, "conversation.padawan_sob_story_01.branchId", 7);
                npcStartConversation(player, npc, "padawan_sob_story_01", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (padawan_sob_story_01_condition__defaultCondition(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_202dc610");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        chat.chat(npc, "Error:  All conditions for OnStartNpcConversation were false.");
        return SCRIPT_CONTINUE;
    }
    public int OnNpcConversationResponse(obj_id self, String conversationId, obj_id player, string_id response) throws InterruptedException
    {
        if (!conversationId.equals("padawan_sob_story_01"))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id npc = self;
        int branchId = utils.getIntScriptVar(player, "conversation.padawan_sob_story_01.branchId");
        if (branchId == 2 && padawan_sob_story_01_handleBranch2(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 4 && padawan_sob_story_01_handleBranch4(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 7 && padawan_sob_story_01_handleBranch7(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 8 && padawan_sob_story_01_handleBranch8(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 9 && padawan_sob_story_01_handleBranch9(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 10 && padawan_sob_story_01_handleBranch10(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 11 && padawan_sob_story_01_handleBranch11(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 13 && padawan_sob_story_01_handleBranch13(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 15 && padawan_sob_story_01_handleBranch15(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 19 && padawan_sob_story_01_handleBranch19(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 22 && padawan_sob_story_01_handleBranch22(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 23 && padawan_sob_story_01_handleBranch23(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 24 && padawan_sob_story_01_handleBranch24(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 25 && padawan_sob_story_01_handleBranch25(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 27 && padawan_sob_story_01_handleBranch27(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 30 && padawan_sob_story_01_handleBranch30(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        chat.chat(npc, "Error:  Fell through all branches and responses for OnNpcConversationResponse.");
        utils.removeScriptVar(player, "conversation.padawan_sob_story_01.branchId");
        return SCRIPT_CONTINUE;
    }
}
