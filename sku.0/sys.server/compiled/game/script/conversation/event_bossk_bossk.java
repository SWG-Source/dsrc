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

public class event_bossk_bossk extends script.base_script
{
    public event_bossk_bossk()
    {
    }
    public static String c_stringFile = "conversation/event_bossk_bossk";
    public boolean event_bossk_bossk_condition__defaultCondition(obj_id player, obj_id npc) throws InterruptedException
    {
        return true;
    }
    public boolean event_bossk_bossk_condition_checkQ1(obj_id player, obj_id npc) throws InterruptedException
    {
        int quest1 = questGetQuestId("quest/event_cantina_bossk_1");
        int task = groundquests.getTaskId(quest1, "q1Failed");
        return (questIsQuestActive(quest1, player) && questIsTaskActive(quest1, task, player));
    }
    public boolean event_bossk_bossk_condition_checkQ2(obj_id player, obj_id npc) throws InterruptedException
    {
        int quest1 = questGetQuestId("quest/event_cantina_bossk_2");
        int task = groundquests.getTaskId(quest1, "q2Failed");
        return (questIsQuestActive(quest1, player) && questIsTaskActive(quest1, task, player));
    }
    public boolean event_bossk_bossk_condition_checkQ3(obj_id player, obj_id npc) throws InterruptedException
    {
        return true;
    }
    public boolean event_bossk_bossk_condition_checkQ4(obj_id player, obj_id npc) throws InterruptedException
    {
        int quest1 = questGetQuestId("quest/event_cantina_bossk_4");
        int task = groundquests.getTaskId(quest1, "q4Failed");
        return (questIsQuestActive(quest1, player) && questIsTaskActive(quest1, task, player));
    }
    public boolean event_bossk_bossk_condition_hasAnyQuest(obj_id player, obj_id npc) throws InterruptedException
    {
        int quest1 = questGetQuestId("quest/event_cantina_bossk_1");
        int quest2 = questGetQuestId("quest/event_cantina_bossk_2");
        int quest3 = questGetQuestId("quest/event_cantina_bossk_3");
        int quest4 = questGetQuestId("quest/event_cantina_bossk_4");
        return (questIsQuestActive(quest1, player) || questIsQuestActive(quest2, player) || questIsQuestActive(quest3, player) || questIsQuestActive(quest4, player));
    }
    public boolean event_bossk_bossk_condition_finishedQ1(obj_id player, obj_id npc) throws InterruptedException
    {
        int quest1 = questGetQuestId("quest/event_cantina_bossk_1");
        int quest2 = questGetQuestId("quest/event_cantina_bossk_2");
        int quest3 = questGetQuestId("quest/event_cantina_bossk_3");
        int quest4 = questGetQuestId("quest/event_cantina_bossk_4");
        return (questIsQuestComplete(quest1, player) && !questIsQuestActive(quest2, player) && !questIsQuestComplete(quest2, player) && !questIsQuestActive(quest3, player) && !questIsQuestComplete(quest3, player) && !questIsQuestActive(quest4, player) && !questIsQuestComplete(quest4, player));
    }
    public boolean event_bossk_bossk_condition_finishedQ2(obj_id player, obj_id npc) throws InterruptedException
    {
        int quest1 = questGetQuestId("quest/event_cantina_bossk_1");
        int quest2 = questGetQuestId("quest/event_cantina_bossk_2");
        int quest3 = questGetQuestId("quest/event_cantina_bossk_3");
        int quest4 = questGetQuestId("quest/event_cantina_bossk_4");
        return (questIsQuestComplete(quest1, player) && questIsQuestComplete(quest2, player) && !questIsQuestActive(quest3, player) && !questIsQuestComplete(quest3, player) && !questIsQuestActive(quest4, player) && !questIsQuestComplete(quest4, player));
    }
    public boolean event_bossk_bossk_condition_finishedQ3(obj_id player, obj_id npc) throws InterruptedException
    {
        int quest1 = questGetQuestId("quest/event_cantina_bossk_1");
        int quest2 = questGetQuestId("quest/event_cantina_bossk_2");
        int quest3 = questGetQuestId("quest/event_cantina_bossk_3");
        int quest4 = questGetQuestId("quest/event_cantina_bossk_4");
        return (questIsQuestComplete(quest1, player) && questIsQuestComplete(quest2, player) && questIsQuestComplete(quest3, player) && !questIsQuestActive(quest4, player) && !questIsQuestComplete(quest4, player));
    }
    public boolean event_bossk_bossk_condition_finishedAll(obj_id player, obj_id npc) throws InterruptedException
    {
        int quest1 = questGetQuestId("quest/event_cantina_bossk_1");
        int quest2 = questGetQuestId("quest/event_cantina_bossk_2");
        int quest3 = questGetQuestId("quest/event_cantina_bossk_3");
        int quest4 = questGetQuestId("quest/event_cantina_bossk_4");
        return (questIsQuestComplete(quest1, player) && questIsQuestComplete(quest2, player) && questIsQuestComplete(quest3, player) && questIsQuestComplete(quest4, player));
    }
    public void event_bossk_bossk_action_endQ1(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.sendSignal(player, "q1Failed");
    }
    public void event_bossk_bossk_action_endQ2(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.sendSignal(player, "q2Failed");
    }
    public void event_bossk_bossk_action_endQ4(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.sendSignal(player, "q4Failed");
    }
    public void event_bossk_bossk_action_grantQ1(obj_id player, obj_id npc) throws InterruptedException
    {
        int questId = questGetQuestId("quest/event_cantina_bossk_1");
        groundquests.grantQuest(questId, player, npc, true);
    }
    public void event_bossk_bossk_action_grantQ2(obj_id player, obj_id npc) throws InterruptedException
    {
        int questId = questGetQuestId("quest/event_cantina_bossk_2");
        groundquests.grantQuest(questId, player, npc, true);
    }
    public void event_bossk_bossk_action_grantQ3(obj_id player, obj_id npc) throws InterruptedException
    {
        int questId = questGetQuestId("quest/event_cantina_bossk_3");
        groundquests.grantQuest(questId, player, npc, true);
    }
    public void event_bossk_bossk_action_grantQ4(obj_id player, obj_id npc) throws InterruptedException
    {
        int questId = questGetQuestId("quest/event_cantina_bossk_4");
        groundquests.grantQuest(questId, player, npc, true);
    }
    public int event_bossk_bossk_handleBranch1(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_165"))
        {
            if (event_bossk_bossk_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "loser");
                event_bossk_bossk_action_endQ1(player, npc);
                string_id message = new string_id(c_stringFile, "s_167");
                utils.removeScriptVar(player, "conversation.event_bossk_bossk.branchId");
                chat.chat(npc, player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_169"))
        {
            if (event_bossk_bossk_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "loser");
                event_bossk_bossk_action_endQ2(player, npc);
                string_id message = new string_id(c_stringFile, "s_171");
                utils.removeScriptVar(player, "conversation.event_bossk_bossk.branchId");
                chat.chat(npc, player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_173"))
        {
            if (event_bossk_bossk_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "loser");
                event_bossk_bossk_action_endQ4(player, npc);
                string_id message = new string_id(c_stringFile, "s_175");
                utils.removeScriptVar(player, "conversation.event_bossk_bossk.branchId");
                chat.chat(npc, player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int event_bossk_bossk_handleBranch6(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_181"))
        {
            if (event_bossk_bossk_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "explain");
                string_id message = new string_id(c_stringFile, "s_183");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (event_bossk_bossk_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_185");
                    }
                    utils.setScriptVar(player, "conversation.event_bossk_bossk.branchId", 7);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.event_bossk_bossk.branchId");
                    chat.chat(npc, player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_201"))
        {
            if (event_bossk_bossk_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "rude");
                string_id message = new string_id(c_stringFile, "s_203");
                utils.removeScriptVar(player, "conversation.event_bossk_bossk.branchId");
                chat.chat(npc, player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int event_bossk_bossk_handleBranch7(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_185"))
        {
            if (event_bossk_bossk_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "explain");
                string_id message = new string_id(c_stringFile, "s_187");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (event_bossk_bossk_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_189");
                    }
                    utils.setScriptVar(player, "conversation.event_bossk_bossk.branchId", 8);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.event_bossk_bossk.branchId");
                    chat.chat(npc, player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int event_bossk_bossk_handleBranch8(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_189"))
        {
            if (event_bossk_bossk_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "explain");
                string_id message = new string_id(c_stringFile, "s_191");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (event_bossk_bossk_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (event_bossk_bossk_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_193");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_197");
                    }
                    utils.setScriptVar(player, "conversation.event_bossk_bossk.branchId", 9);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.event_bossk_bossk.branchId");
                    chat.chat(npc, player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int event_bossk_bossk_handleBranch9(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_193"))
        {
            if (event_bossk_bossk_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "nod");
                event_bossk_bossk_action_grantQ4(player, npc);
                string_id message = new string_id(c_stringFile, "s_195");
                utils.removeScriptVar(player, "conversation.event_bossk_bossk.branchId");
                chat.chat(npc, player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_197"))
        {
            if (event_bossk_bossk_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "shrug_shoulders");
                string_id message = new string_id(c_stringFile, "s_199");
                utils.removeScriptVar(player, "conversation.event_bossk_bossk.branchId");
                chat.chat(npc, player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int event_bossk_bossk_handleBranch13(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_207"))
        {
            if (event_bossk_bossk_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "explain");
                string_id message = new string_id(c_stringFile, "s_209");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (event_bossk_bossk_condition__defaultCondition(player, npc))
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
                    utils.setScriptVar(player, "conversation.event_bossk_bossk.branchId", 14);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.event_bossk_bossk.branchId");
                    chat.chat(npc, player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_223"))
        {
            if (event_bossk_bossk_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "loser");
                string_id message = new string_id(c_stringFile, "s_225");
                utils.removeScriptVar(player, "conversation.event_bossk_bossk.branchId");
                chat.chat(npc, player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int event_bossk_bossk_handleBranch14(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_211"))
        {
            if (event_bossk_bossk_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "explain");
                string_id message = new string_id(c_stringFile, "s_213");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (event_bossk_bossk_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (event_bossk_bossk_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_215");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_219");
                    }
                    utils.setScriptVar(player, "conversation.event_bossk_bossk.branchId", 15);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.event_bossk_bossk.branchId");
                    chat.chat(npc, player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int event_bossk_bossk_handleBranch15(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_215"))
        {
            if (event_bossk_bossk_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "nod");
                event_bossk_bossk_action_grantQ3(player, npc);
                string_id message = new string_id(c_stringFile, "s_217");
                utils.removeScriptVar(player, "conversation.event_bossk_bossk.branchId");
                chat.chat(npc, player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_219"))
        {
            if (event_bossk_bossk_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "loser");
                string_id message = new string_id(c_stringFile, "s_221");
                utils.removeScriptVar(player, "conversation.event_bossk_bossk.branchId");
                chat.chat(npc, player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int event_bossk_bossk_handleBranch19(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_229"))
        {
            if (event_bossk_bossk_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_231");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (event_bossk_bossk_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_233");
                    }
                    utils.setScriptVar(player, "conversation.event_bossk_bossk.branchId", 20);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.event_bossk_bossk.branchId");
                    chat.chat(npc, player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_249"))
        {
            if (event_bossk_bossk_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "rude");
                string_id message = new string_id(c_stringFile, "s_251");
                utils.removeScriptVar(player, "conversation.event_bossk_bossk.branchId");
                chat.chat(npc, player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int event_bossk_bossk_handleBranch20(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_233"))
        {
            if (event_bossk_bossk_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "explain");
                string_id message = new string_id(c_stringFile, "s_235");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (event_bossk_bossk_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_237");
                    }
                    utils.setScriptVar(player, "conversation.event_bossk_bossk.branchId", 21);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.event_bossk_bossk.branchId");
                    chat.chat(npc, player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int event_bossk_bossk_handleBranch21(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_237"))
        {
            if (event_bossk_bossk_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "point_forward");
                string_id message = new string_id(c_stringFile, "s_239");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (event_bossk_bossk_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (event_bossk_bossk_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_241");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_245");
                    }
                    utils.setScriptVar(player, "conversation.event_bossk_bossk.branchId", 22);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.event_bossk_bossk.branchId");
                    chat.chat(npc, player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int event_bossk_bossk_handleBranch22(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_241"))
        {
            if (event_bossk_bossk_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "nod");
                event_bossk_bossk_action_grantQ2(player, npc);
                string_id message = new string_id(c_stringFile, "s_243");
                utils.removeScriptVar(player, "conversation.event_bossk_bossk.branchId");
                chat.chat(npc, player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_245"))
        {
            if (event_bossk_bossk_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "dismiss");
                string_id message = new string_id(c_stringFile, "s_247");
                utils.removeScriptVar(player, "conversation.event_bossk_bossk.branchId");
                chat.chat(npc, player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int event_bossk_bossk_handleBranch26(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_255"))
        {
            if (event_bossk_bossk_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "explain");
                string_id message = new string_id(c_stringFile, "s_257");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (event_bossk_bossk_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_259");
                    }
                    utils.setScriptVar(player, "conversation.event_bossk_bossk.branchId", 27);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.event_bossk_bossk.branchId");
                    chat.chat(npc, player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_271"))
        {
            if (event_bossk_bossk_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "dismiss");
                string_id message = new string_id(c_stringFile, "s_273");
                utils.removeScriptVar(player, "conversation.event_bossk_bossk.branchId");
                chat.chat(npc, player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int event_bossk_bossk_handleBranch27(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_259"))
        {
            if (event_bossk_bossk_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_261");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (event_bossk_bossk_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (event_bossk_bossk_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_263");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_267");
                    }
                    utils.setScriptVar(player, "conversation.event_bossk_bossk.branchId", 28);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.event_bossk_bossk.branchId");
                    chat.chat(npc, player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int event_bossk_bossk_handleBranch28(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_263"))
        {
            if (event_bossk_bossk_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "nod_head_once");
                event_bossk_bossk_action_grantQ1(player, npc);
                string_id message = new string_id(c_stringFile, "s_265");
                utils.removeScriptVar(player, "conversation.event_bossk_bossk.branchId");
                chat.chat(npc, player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_267"))
        {
            if (event_bossk_bossk_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "dismiss");
                string_id message = new string_id(c_stringFile, "s_269");
                utils.removeScriptVar(player, "conversation.event_bossk_bossk.branchId");
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
            detachScript(self, "conversation.event_bossk_bossk");
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
        detachScript(self, "conversation.event_bossk_bossk");
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
        if (event_bossk_bossk_condition_hasAnyQuest(player, npc))
        {
            doAnimationAction(npc, "gesticulate_wildly");
            string_id message = new string_id(c_stringFile, "s_163");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (event_bossk_bossk_condition_checkQ1(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            boolean hasResponse1 = false;
            if (event_bossk_bossk_condition_checkQ2(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse1 = true;
            }
            boolean hasResponse2 = false;
            if (event_bossk_bossk_condition_checkQ4(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse2 = true;
            }
            if (hasResponse)
            {
                int responseIndex = 0;
                string_id responses[] = new string_id[numberOfResponses];
                if (hasResponse0)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_165");
                }
                if (hasResponse1)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_169");
                }
                if (hasResponse2)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_173");
                }
                utils.setScriptVar(player, "conversation.event_bossk_bossk.branchId", 1);
                npcStartConversation(player, npc, "event_bossk_bossk", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (event_bossk_bossk_condition_finishedAll(player, npc))
        {
            doAnimationAction(npc, "dismiss");
            string_id message = new string_id(c_stringFile, "s_177");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        if (event_bossk_bossk_condition_finishedQ3(player, npc))
        {
            doAnimationAction(npc, "hands_behind_head");
            string_id message = new string_id(c_stringFile, "s_179");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (event_bossk_bossk_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            boolean hasResponse1 = false;
            if (event_bossk_bossk_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_181");
                }
                if (hasResponse1)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_201");
                }
                utils.setScriptVar(player, "conversation.event_bossk_bossk.branchId", 6);
                npcStartConversation(player, npc, "event_bossk_bossk", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (event_bossk_bossk_condition_finishedQ2(player, npc))
        {
            doAnimationAction(npc, "hands_behind_head");
            string_id message = new string_id(c_stringFile, "s_205");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (event_bossk_bossk_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            boolean hasResponse1 = false;
            if (event_bossk_bossk_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_207");
                }
                if (hasResponse1)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_223");
                }
                utils.setScriptVar(player, "conversation.event_bossk_bossk.branchId", 13);
                npcStartConversation(player, npc, "event_bossk_bossk", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (event_bossk_bossk_condition_finishedQ1(player, npc))
        {
            doAnimationAction(npc, "hands_behind_head");
            string_id message = new string_id(c_stringFile, "s_227");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (event_bossk_bossk_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            boolean hasResponse1 = false;
            if (event_bossk_bossk_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_229");
                }
                if (hasResponse1)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_249");
                }
                utils.setScriptVar(player, "conversation.event_bossk_bossk.branchId", 19);
                npcStartConversation(player, npc, "event_bossk_bossk", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (event_bossk_bossk_condition__defaultCondition(player, npc))
        {
            doAnimationAction(npc, "poke");
            string_id message = new string_id(c_stringFile, "s_253");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (event_bossk_bossk_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            boolean hasResponse1 = false;
            if (event_bossk_bossk_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_255");
                }
                if (hasResponse1)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_271");
                }
                utils.setScriptVar(player, "conversation.event_bossk_bossk.branchId", 26);
                npcStartConversation(player, npc, "event_bossk_bossk", message, responses);
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
        if (!conversationId.equals("event_bossk_bossk"))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id npc = self;
        int branchId = utils.getIntScriptVar(player, "conversation.event_bossk_bossk.branchId");
        if (branchId == 1 && event_bossk_bossk_handleBranch1(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 6 && event_bossk_bossk_handleBranch6(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 7 && event_bossk_bossk_handleBranch7(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 8 && event_bossk_bossk_handleBranch8(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 9 && event_bossk_bossk_handleBranch9(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 13 && event_bossk_bossk_handleBranch13(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 14 && event_bossk_bossk_handleBranch14(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 15 && event_bossk_bossk_handleBranch15(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 19 && event_bossk_bossk_handleBranch19(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 20 && event_bossk_bossk_handleBranch20(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 21 && event_bossk_bossk_handleBranch21(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 22 && event_bossk_bossk_handleBranch22(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 26 && event_bossk_bossk_handleBranch26(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 27 && event_bossk_bossk_handleBranch27(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 28 && event_bossk_bossk_handleBranch28(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        chat.chat(npc, "Error:  Fell through all branches and responses for OnNpcConversationResponse.");
        utils.removeScriptVar(player, "conversation.event_bossk_bossk.branchId");
        return SCRIPT_CONTINUE;
    }
}
