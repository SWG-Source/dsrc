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
import script.library.utils;

public class event_ls_informant_2 extends script.base_script
{
    public event_ls_informant_2()
    {
    }
    public static String c_stringFile = "conversation/event_ls_informant_2";
    public boolean event_ls_informant_2_condition__defaultCondition(obj_id player, obj_id npc) throws InterruptedException
    {
        return true;
    }
    public int event_ls_informant_2_handleBranch1(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_5"))
        {
            if (event_ls_informant_2_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_6");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (event_ls_informant_2_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (event_ls_informant_2_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (event_ls_informant_2_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse2 = true;
                }
                boolean hasResponse3 = false;
                if (event_ls_informant_2_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse3 = true;
                }
                boolean hasResponse4 = false;
                if (event_ls_informant_2_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse4 = true;
                }
                boolean hasResponse5 = false;
                if (event_ls_informant_2_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse5 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_5");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_7");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_8");
                    }
                    if (hasResponse3)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_9");
                    }
                    if (hasResponse4)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_10");
                    }
                    if (hasResponse5)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_11");
                    }
                    utils.setScriptVar(player, "conversation.event_ls_informant_2.branchId", 1);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.event_ls_informant_2.branchId");
                    chat.chat(npc, player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_7"))
        {
            if (event_ls_informant_2_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "explain");
                string_id message = new string_id(c_stringFile, "s_12");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (event_ls_informant_2_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_14");
                    }
                    utils.setScriptVar(player, "conversation.event_ls_informant_2.branchId", 3);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.event_ls_informant_2.branchId");
                    chat.chat(npc, player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_8"))
        {
            if (event_ls_informant_2_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_13");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (event_ls_informant_2_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (event_ls_informant_2_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (event_ls_informant_2_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse2 = true;
                }
                boolean hasResponse3 = false;
                if (event_ls_informant_2_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse3 = true;
                }
                boolean hasResponse4 = false;
                if (event_ls_informant_2_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse4 = true;
                }
                boolean hasResponse5 = false;
                if (event_ls_informant_2_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse5 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_5");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_7");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_8");
                    }
                    if (hasResponse3)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_9");
                    }
                    if (hasResponse4)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_10");
                    }
                    if (hasResponse5)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_11");
                    }
                    utils.setScriptVar(player, "conversation.event_ls_informant_2.branchId", 1);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.event_ls_informant_2.branchId");
                    chat.chat(npc, player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_9"))
        {
            if (event_ls_informant_2_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_24");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (event_ls_informant_2_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (event_ls_informant_2_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (event_ls_informant_2_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse2 = true;
                }
                boolean hasResponse3 = false;
                if (event_ls_informant_2_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse3 = true;
                }
                boolean hasResponse4 = false;
                if (event_ls_informant_2_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse4 = true;
                }
                boolean hasResponse5 = false;
                if (event_ls_informant_2_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse5 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_5");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_7");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_8");
                    }
                    if (hasResponse3)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_9");
                    }
                    if (hasResponse4)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_10");
                    }
                    if (hasResponse5)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_11");
                    }
                    utils.setScriptVar(player, "conversation.event_ls_informant_2.branchId", 1);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.event_ls_informant_2.branchId");
                    chat.chat(npc, player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_10"))
        {
            if (event_ls_informant_2_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_25");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (event_ls_informant_2_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (event_ls_informant_2_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (event_ls_informant_2_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse2 = true;
                }
                boolean hasResponse3 = false;
                if (event_ls_informant_2_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse3 = true;
                }
                boolean hasResponse4 = false;
                if (event_ls_informant_2_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse4 = true;
                }
                boolean hasResponse5 = false;
                if (event_ls_informant_2_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse5 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_5");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_7");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_8");
                    }
                    if (hasResponse3)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_9");
                    }
                    if (hasResponse4)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_10");
                    }
                    if (hasResponse5)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_11");
                    }
                    utils.setScriptVar(player, "conversation.event_ls_informant_2.branchId", 1);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.event_ls_informant_2.branchId");
                    chat.chat(npc, player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_11"))
        {
            if (event_ls_informant_2_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "goodbye");
                string_id message = new string_id(c_stringFile, "s_26");
                utils.removeScriptVar(player, "conversation.event_ls_informant_2.branchId");
                chat.chat(npc, player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int event_ls_informant_2_handleBranch2(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_5"))
        {
            if (event_ls_informant_2_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_6");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (event_ls_informant_2_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (event_ls_informant_2_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (event_ls_informant_2_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse2 = true;
                }
                boolean hasResponse3 = false;
                if (event_ls_informant_2_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse3 = true;
                }
                boolean hasResponse4 = false;
                if (event_ls_informant_2_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse4 = true;
                }
                boolean hasResponse5 = false;
                if (event_ls_informant_2_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse5 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_5");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_7");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_8");
                    }
                    if (hasResponse3)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_9");
                    }
                    if (hasResponse4)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_10");
                    }
                    if (hasResponse5)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_11");
                    }
                    utils.setScriptVar(player, "conversation.event_ls_informant_2.branchId", 1);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.event_ls_informant_2.branchId");
                    chat.chat(npc, player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_7"))
        {
            if (event_ls_informant_2_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "explain");
                string_id message = new string_id(c_stringFile, "s_12");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (event_ls_informant_2_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_14");
                    }
                    utils.setScriptVar(player, "conversation.event_ls_informant_2.branchId", 3);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.event_ls_informant_2.branchId");
                    chat.chat(npc, player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_8"))
        {
            if (event_ls_informant_2_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_13");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (event_ls_informant_2_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (event_ls_informant_2_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (event_ls_informant_2_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse2 = true;
                }
                boolean hasResponse3 = false;
                if (event_ls_informant_2_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse3 = true;
                }
                boolean hasResponse4 = false;
                if (event_ls_informant_2_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse4 = true;
                }
                boolean hasResponse5 = false;
                if (event_ls_informant_2_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse5 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_5");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_7");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_8");
                    }
                    if (hasResponse3)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_9");
                    }
                    if (hasResponse4)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_10");
                    }
                    if (hasResponse5)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_11");
                    }
                    utils.setScriptVar(player, "conversation.event_ls_informant_2.branchId", 1);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.event_ls_informant_2.branchId");
                    chat.chat(npc, player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_9"))
        {
            if (event_ls_informant_2_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_24");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (event_ls_informant_2_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (event_ls_informant_2_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (event_ls_informant_2_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse2 = true;
                }
                boolean hasResponse3 = false;
                if (event_ls_informant_2_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse3 = true;
                }
                boolean hasResponse4 = false;
                if (event_ls_informant_2_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse4 = true;
                }
                boolean hasResponse5 = false;
                if (event_ls_informant_2_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse5 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_5");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_7");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_8");
                    }
                    if (hasResponse3)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_9");
                    }
                    if (hasResponse4)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_10");
                    }
                    if (hasResponse5)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_11");
                    }
                    utils.setScriptVar(player, "conversation.event_ls_informant_2.branchId", 1);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.event_ls_informant_2.branchId");
                    chat.chat(npc, player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_10"))
        {
            if (event_ls_informant_2_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_25");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (event_ls_informant_2_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (event_ls_informant_2_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (event_ls_informant_2_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse2 = true;
                }
                boolean hasResponse3 = false;
                if (event_ls_informant_2_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse3 = true;
                }
                boolean hasResponse4 = false;
                if (event_ls_informant_2_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse4 = true;
                }
                boolean hasResponse5 = false;
                if (event_ls_informant_2_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse5 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_5");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_7");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_8");
                    }
                    if (hasResponse3)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_9");
                    }
                    if (hasResponse4)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_10");
                    }
                    if (hasResponse5)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_11");
                    }
                    utils.setScriptVar(player, "conversation.event_ls_informant_2.branchId", 1);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.event_ls_informant_2.branchId");
                    chat.chat(npc, player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_11"))
        {
            if (event_ls_informant_2_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "goodbye");
                string_id message = new string_id(c_stringFile, "s_26");
                utils.removeScriptVar(player, "conversation.event_ls_informant_2.branchId");
                chat.chat(npc, player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int event_ls_informant_2_handleBranch3(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_14"))
        {
            if (event_ls_informant_2_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_15");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (event_ls_informant_2_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_16");
                    }
                    utils.setScriptVar(player, "conversation.event_ls_informant_2.branchId", 4);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.event_ls_informant_2.branchId");
                    chat.chat(npc, player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int event_ls_informant_2_handleBranch4(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_16"))
        {
            if (event_ls_informant_2_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_17");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (event_ls_informant_2_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_18");
                    }
                    utils.setScriptVar(player, "conversation.event_ls_informant_2.branchId", 5);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.event_ls_informant_2.branchId");
                    chat.chat(npc, player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int event_ls_informant_2_handleBranch5(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_18"))
        {
            if (event_ls_informant_2_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_19");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (event_ls_informant_2_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_20");
                    }
                    utils.setScriptVar(player, "conversation.event_ls_informant_2.branchId", 6);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.event_ls_informant_2.branchId");
                    chat.chat(npc, player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int event_ls_informant_2_handleBranch6(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_20"))
        {
            if (event_ls_informant_2_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_21");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (event_ls_informant_2_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_22");
                    }
                    utils.setScriptVar(player, "conversation.event_ls_informant_2.branchId", 7);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.event_ls_informant_2.branchId");
                    chat.chat(npc, player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int event_ls_informant_2_handleBranch7(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_22"))
        {
            if (event_ls_informant_2_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "shrug_hands");
                string_id message = new string_id(c_stringFile, "s_23");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (event_ls_informant_2_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (event_ls_informant_2_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (event_ls_informant_2_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse2 = true;
                }
                boolean hasResponse3 = false;
                if (event_ls_informant_2_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse3 = true;
                }
                boolean hasResponse4 = false;
                if (event_ls_informant_2_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse4 = true;
                }
                boolean hasResponse5 = false;
                if (event_ls_informant_2_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse5 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_5");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_7");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_8");
                    }
                    if (hasResponse3)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_9");
                    }
                    if (hasResponse4)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_10");
                    }
                    if (hasResponse5)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_11");
                    }
                    utils.setScriptVar(player, "conversation.event_ls_informant_2.branchId", 1);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.event_ls_informant_2.branchId");
                    chat.chat(npc, player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int event_ls_informant_2_handleBranch8(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_5"))
        {
            if (event_ls_informant_2_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_6");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (event_ls_informant_2_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (event_ls_informant_2_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (event_ls_informant_2_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse2 = true;
                }
                boolean hasResponse3 = false;
                if (event_ls_informant_2_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse3 = true;
                }
                boolean hasResponse4 = false;
                if (event_ls_informant_2_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse4 = true;
                }
                boolean hasResponse5 = false;
                if (event_ls_informant_2_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse5 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_5");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_7");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_8");
                    }
                    if (hasResponse3)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_9");
                    }
                    if (hasResponse4)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_10");
                    }
                    if (hasResponse5)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_11");
                    }
                    utils.setScriptVar(player, "conversation.event_ls_informant_2.branchId", 1);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.event_ls_informant_2.branchId");
                    chat.chat(npc, player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_7"))
        {
            if (event_ls_informant_2_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "explain");
                string_id message = new string_id(c_stringFile, "s_12");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (event_ls_informant_2_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_14");
                    }
                    utils.setScriptVar(player, "conversation.event_ls_informant_2.branchId", 3);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.event_ls_informant_2.branchId");
                    chat.chat(npc, player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_8"))
        {
            if (event_ls_informant_2_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_13");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (event_ls_informant_2_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (event_ls_informant_2_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (event_ls_informant_2_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse2 = true;
                }
                boolean hasResponse3 = false;
                if (event_ls_informant_2_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse3 = true;
                }
                boolean hasResponse4 = false;
                if (event_ls_informant_2_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse4 = true;
                }
                boolean hasResponse5 = false;
                if (event_ls_informant_2_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse5 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_5");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_7");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_8");
                    }
                    if (hasResponse3)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_9");
                    }
                    if (hasResponse4)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_10");
                    }
                    if (hasResponse5)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_11");
                    }
                    utils.setScriptVar(player, "conversation.event_ls_informant_2.branchId", 1);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.event_ls_informant_2.branchId");
                    chat.chat(npc, player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_9"))
        {
            if (event_ls_informant_2_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_24");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (event_ls_informant_2_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (event_ls_informant_2_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (event_ls_informant_2_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse2 = true;
                }
                boolean hasResponse3 = false;
                if (event_ls_informant_2_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse3 = true;
                }
                boolean hasResponse4 = false;
                if (event_ls_informant_2_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse4 = true;
                }
                boolean hasResponse5 = false;
                if (event_ls_informant_2_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse5 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_5");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_7");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_8");
                    }
                    if (hasResponse3)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_9");
                    }
                    if (hasResponse4)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_10");
                    }
                    if (hasResponse5)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_11");
                    }
                    utils.setScriptVar(player, "conversation.event_ls_informant_2.branchId", 1);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.event_ls_informant_2.branchId");
                    chat.chat(npc, player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_10"))
        {
            if (event_ls_informant_2_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_25");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (event_ls_informant_2_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (event_ls_informant_2_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (event_ls_informant_2_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse2 = true;
                }
                boolean hasResponse3 = false;
                if (event_ls_informant_2_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse3 = true;
                }
                boolean hasResponse4 = false;
                if (event_ls_informant_2_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse4 = true;
                }
                boolean hasResponse5 = false;
                if (event_ls_informant_2_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse5 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_5");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_7");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_8");
                    }
                    if (hasResponse3)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_9");
                    }
                    if (hasResponse4)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_10");
                    }
                    if (hasResponse5)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_11");
                    }
                    utils.setScriptVar(player, "conversation.event_ls_informant_2.branchId", 1);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.event_ls_informant_2.branchId");
                    chat.chat(npc, player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_11"))
        {
            if (event_ls_informant_2_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "goodbye");
                string_id message = new string_id(c_stringFile, "s_26");
                utils.removeScriptVar(player, "conversation.event_ls_informant_2.branchId");
                chat.chat(npc, player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int event_ls_informant_2_handleBranch9(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_5"))
        {
            if (event_ls_informant_2_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_6");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (event_ls_informant_2_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (event_ls_informant_2_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (event_ls_informant_2_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse2 = true;
                }
                boolean hasResponse3 = false;
                if (event_ls_informant_2_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse3 = true;
                }
                boolean hasResponse4 = false;
                if (event_ls_informant_2_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse4 = true;
                }
                boolean hasResponse5 = false;
                if (event_ls_informant_2_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse5 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_5");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_7");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_8");
                    }
                    if (hasResponse3)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_9");
                    }
                    if (hasResponse4)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_10");
                    }
                    if (hasResponse5)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_11");
                    }
                    utils.setScriptVar(player, "conversation.event_ls_informant_2.branchId", 1);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.event_ls_informant_2.branchId");
                    chat.chat(npc, player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_7"))
        {
            if (event_ls_informant_2_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "explain");
                string_id message = new string_id(c_stringFile, "s_12");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (event_ls_informant_2_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_14");
                    }
                    utils.setScriptVar(player, "conversation.event_ls_informant_2.branchId", 3);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.event_ls_informant_2.branchId");
                    chat.chat(npc, player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_8"))
        {
            if (event_ls_informant_2_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_13");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (event_ls_informant_2_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (event_ls_informant_2_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (event_ls_informant_2_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse2 = true;
                }
                boolean hasResponse3 = false;
                if (event_ls_informant_2_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse3 = true;
                }
                boolean hasResponse4 = false;
                if (event_ls_informant_2_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse4 = true;
                }
                boolean hasResponse5 = false;
                if (event_ls_informant_2_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse5 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_5");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_7");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_8");
                    }
                    if (hasResponse3)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_9");
                    }
                    if (hasResponse4)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_10");
                    }
                    if (hasResponse5)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_11");
                    }
                    utils.setScriptVar(player, "conversation.event_ls_informant_2.branchId", 1);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.event_ls_informant_2.branchId");
                    chat.chat(npc, player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_9"))
        {
            if (event_ls_informant_2_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_24");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (event_ls_informant_2_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (event_ls_informant_2_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (event_ls_informant_2_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse2 = true;
                }
                boolean hasResponse3 = false;
                if (event_ls_informant_2_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse3 = true;
                }
                boolean hasResponse4 = false;
                if (event_ls_informant_2_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse4 = true;
                }
                boolean hasResponse5 = false;
                if (event_ls_informant_2_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse5 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_5");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_7");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_8");
                    }
                    if (hasResponse3)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_9");
                    }
                    if (hasResponse4)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_10");
                    }
                    if (hasResponse5)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_11");
                    }
                    utils.setScriptVar(player, "conversation.event_ls_informant_2.branchId", 1);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.event_ls_informant_2.branchId");
                    chat.chat(npc, player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_10"))
        {
            if (event_ls_informant_2_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_25");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (event_ls_informant_2_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (event_ls_informant_2_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (event_ls_informant_2_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse2 = true;
                }
                boolean hasResponse3 = false;
                if (event_ls_informant_2_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse3 = true;
                }
                boolean hasResponse4 = false;
                if (event_ls_informant_2_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse4 = true;
                }
                boolean hasResponse5 = false;
                if (event_ls_informant_2_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse5 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_5");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_7");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_8");
                    }
                    if (hasResponse3)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_9");
                    }
                    if (hasResponse4)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_10");
                    }
                    if (hasResponse5)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_11");
                    }
                    utils.setScriptVar(player, "conversation.event_ls_informant_2.branchId", 1);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.event_ls_informant_2.branchId");
                    chat.chat(npc, player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_11"))
        {
            if (event_ls_informant_2_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "goodbye");
                string_id message = new string_id(c_stringFile, "s_26");
                utils.removeScriptVar(player, "conversation.event_ls_informant_2.branchId");
                chat.chat(npc, player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int event_ls_informant_2_handleBranch10(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_5"))
        {
            if (event_ls_informant_2_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_6");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (event_ls_informant_2_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (event_ls_informant_2_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (event_ls_informant_2_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse2 = true;
                }
                boolean hasResponse3 = false;
                if (event_ls_informant_2_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse3 = true;
                }
                boolean hasResponse4 = false;
                if (event_ls_informant_2_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse4 = true;
                }
                boolean hasResponse5 = false;
                if (event_ls_informant_2_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse5 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_5");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_7");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_8");
                    }
                    if (hasResponse3)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_9");
                    }
                    if (hasResponse4)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_10");
                    }
                    if (hasResponse5)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_11");
                    }
                    utils.setScriptVar(player, "conversation.event_ls_informant_2.branchId", 1);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.event_ls_informant_2.branchId");
                    chat.chat(npc, player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_7"))
        {
            if (event_ls_informant_2_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "explain");
                string_id message = new string_id(c_stringFile, "s_12");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (event_ls_informant_2_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_14");
                    }
                    utils.setScriptVar(player, "conversation.event_ls_informant_2.branchId", 3);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.event_ls_informant_2.branchId");
                    chat.chat(npc, player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_8"))
        {
            if (event_ls_informant_2_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_13");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (event_ls_informant_2_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (event_ls_informant_2_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (event_ls_informant_2_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse2 = true;
                }
                boolean hasResponse3 = false;
                if (event_ls_informant_2_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse3 = true;
                }
                boolean hasResponse4 = false;
                if (event_ls_informant_2_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse4 = true;
                }
                boolean hasResponse5 = false;
                if (event_ls_informant_2_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse5 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_5");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_7");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_8");
                    }
                    if (hasResponse3)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_9");
                    }
                    if (hasResponse4)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_10");
                    }
                    if (hasResponse5)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_11");
                    }
                    utils.setScriptVar(player, "conversation.event_ls_informant_2.branchId", 1);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.event_ls_informant_2.branchId");
                    chat.chat(npc, player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_9"))
        {
            if (event_ls_informant_2_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_24");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (event_ls_informant_2_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (event_ls_informant_2_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (event_ls_informant_2_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse2 = true;
                }
                boolean hasResponse3 = false;
                if (event_ls_informant_2_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse3 = true;
                }
                boolean hasResponse4 = false;
                if (event_ls_informant_2_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse4 = true;
                }
                boolean hasResponse5 = false;
                if (event_ls_informant_2_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse5 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_5");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_7");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_8");
                    }
                    if (hasResponse3)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_9");
                    }
                    if (hasResponse4)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_10");
                    }
                    if (hasResponse5)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_11");
                    }
                    utils.setScriptVar(player, "conversation.event_ls_informant_2.branchId", 1);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.event_ls_informant_2.branchId");
                    chat.chat(npc, player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_10"))
        {
            if (event_ls_informant_2_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_25");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (event_ls_informant_2_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (event_ls_informant_2_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (event_ls_informant_2_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse2 = true;
                }
                boolean hasResponse3 = false;
                if (event_ls_informant_2_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse3 = true;
                }
                boolean hasResponse4 = false;
                if (event_ls_informant_2_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse4 = true;
                }
                boolean hasResponse5 = false;
                if (event_ls_informant_2_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse5 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_5");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_7");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_8");
                    }
                    if (hasResponse3)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_9");
                    }
                    if (hasResponse4)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_10");
                    }
                    if (hasResponse5)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_11");
                    }
                    utils.setScriptVar(player, "conversation.event_ls_informant_2.branchId", 1);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.event_ls_informant_2.branchId");
                    chat.chat(npc, player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_11"))
        {
            if (event_ls_informant_2_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "goodbye");
                string_id message = new string_id(c_stringFile, "s_26");
                utils.removeScriptVar(player, "conversation.event_ls_informant_2.branchId");
                chat.chat(npc, player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int event_ls_informant_2_handleBranch11(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_5"))
        {
            if (event_ls_informant_2_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_6");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (event_ls_informant_2_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (event_ls_informant_2_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (event_ls_informant_2_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse2 = true;
                }
                boolean hasResponse3 = false;
                if (event_ls_informant_2_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse3 = true;
                }
                boolean hasResponse4 = false;
                if (event_ls_informant_2_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse4 = true;
                }
                boolean hasResponse5 = false;
                if (event_ls_informant_2_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse5 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_5");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_7");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_8");
                    }
                    if (hasResponse3)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_9");
                    }
                    if (hasResponse4)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_10");
                    }
                    if (hasResponse5)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_11");
                    }
                    utils.setScriptVar(player, "conversation.event_ls_informant_2.branchId", 1);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.event_ls_informant_2.branchId");
                    chat.chat(npc, player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_7"))
        {
            if (event_ls_informant_2_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "explain");
                string_id message = new string_id(c_stringFile, "s_12");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (event_ls_informant_2_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_14");
                    }
                    utils.setScriptVar(player, "conversation.event_ls_informant_2.branchId", 3);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.event_ls_informant_2.branchId");
                    chat.chat(npc, player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_8"))
        {
            if (event_ls_informant_2_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_13");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (event_ls_informant_2_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (event_ls_informant_2_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (event_ls_informant_2_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse2 = true;
                }
                boolean hasResponse3 = false;
                if (event_ls_informant_2_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse3 = true;
                }
                boolean hasResponse4 = false;
                if (event_ls_informant_2_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse4 = true;
                }
                boolean hasResponse5 = false;
                if (event_ls_informant_2_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse5 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_5");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_7");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_8");
                    }
                    if (hasResponse3)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_9");
                    }
                    if (hasResponse4)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_10");
                    }
                    if (hasResponse5)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_11");
                    }
                    utils.setScriptVar(player, "conversation.event_ls_informant_2.branchId", 1);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.event_ls_informant_2.branchId");
                    chat.chat(npc, player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_9"))
        {
            if (event_ls_informant_2_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_24");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (event_ls_informant_2_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (event_ls_informant_2_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (event_ls_informant_2_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse2 = true;
                }
                boolean hasResponse3 = false;
                if (event_ls_informant_2_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse3 = true;
                }
                boolean hasResponse4 = false;
                if (event_ls_informant_2_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse4 = true;
                }
                boolean hasResponse5 = false;
                if (event_ls_informant_2_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse5 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_5");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_7");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_8");
                    }
                    if (hasResponse3)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_9");
                    }
                    if (hasResponse4)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_10");
                    }
                    if (hasResponse5)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_11");
                    }
                    utils.setScriptVar(player, "conversation.event_ls_informant_2.branchId", 1);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.event_ls_informant_2.branchId");
                    chat.chat(npc, player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_10"))
        {
            if (event_ls_informant_2_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_25");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (event_ls_informant_2_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (event_ls_informant_2_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (event_ls_informant_2_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse2 = true;
                }
                boolean hasResponse3 = false;
                if (event_ls_informant_2_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse3 = true;
                }
                boolean hasResponse4 = false;
                if (event_ls_informant_2_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse4 = true;
                }
                boolean hasResponse5 = false;
                if (event_ls_informant_2_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse5 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_5");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_7");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_8");
                    }
                    if (hasResponse3)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_9");
                    }
                    if (hasResponse4)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_10");
                    }
                    if (hasResponse5)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_11");
                    }
                    utils.setScriptVar(player, "conversation.event_ls_informant_2.branchId", 1);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.event_ls_informant_2.branchId");
                    chat.chat(npc, player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_11"))
        {
            if (event_ls_informant_2_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "goodbye");
                string_id message = new string_id(c_stringFile, "s_26");
                utils.removeScriptVar(player, "conversation.event_ls_informant_2.branchId");
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
            detachScript(self, "conversation.event_ls_informant_2");
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
        detachScript(self, "conversation.event_ls_informant_2");
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
        if (event_ls_informant_2_condition__defaultCondition(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_3");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (event_ls_informant_2_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            boolean hasResponse1 = false;
            if (event_ls_informant_2_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse1 = true;
            }
            boolean hasResponse2 = false;
            if (event_ls_informant_2_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse2 = true;
            }
            boolean hasResponse3 = false;
            if (event_ls_informant_2_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse3 = true;
            }
            boolean hasResponse4 = false;
            if (event_ls_informant_2_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse4 = true;
            }
            boolean hasResponse5 = false;
            if (event_ls_informant_2_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse5 = true;
            }
            if (hasResponse)
            {
                int responseIndex = 0;
                string_id responses[] = new string_id[numberOfResponses];
                if (hasResponse0)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_5");
                }
                if (hasResponse1)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_7");
                }
                if (hasResponse2)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_8");
                }
                if (hasResponse3)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_9");
                }
                if (hasResponse4)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_10");
                }
                if (hasResponse5)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_11");
                }
                utils.setScriptVar(player, "conversation.event_ls_informant_2.branchId", 1);
                npcStartConversation(player, npc, "event_ls_informant_2", message, responses);
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
        if (!conversationId.equals("event_ls_informant_2"))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id npc = self;
        int branchId = utils.getIntScriptVar(player, "conversation.event_ls_informant_2.branchId");
        if (branchId == 1 && event_ls_informant_2_handleBranch1(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 2 && event_ls_informant_2_handleBranch2(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 3 && event_ls_informant_2_handleBranch3(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 4 && event_ls_informant_2_handleBranch4(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 5 && event_ls_informant_2_handleBranch5(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 6 && event_ls_informant_2_handleBranch6(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 7 && event_ls_informant_2_handleBranch7(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 8 && event_ls_informant_2_handleBranch8(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 9 && event_ls_informant_2_handleBranch9(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 10 && event_ls_informant_2_handleBranch10(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 11 && event_ls_informant_2_handleBranch11(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        chat.chat(npc, "Error:  Fell through all branches and responses for OnNpcConversationResponse.");
        utils.removeScriptVar(player, "conversation.event_ls_informant_2.branchId");
        return SCRIPT_CONTINUE;
    }
}
