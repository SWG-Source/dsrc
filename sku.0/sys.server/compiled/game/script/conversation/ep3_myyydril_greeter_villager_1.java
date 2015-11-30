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

public class ep3_myyydril_greeter_villager_1 extends script.base_script
{
    public ep3_myyydril_greeter_villager_1()
    {
    }
    public static String c_stringFile = "conversation/ep3_myyydril_greeter_villager_1";
    public boolean ep3_myyydril_greeter_villager_1_condition__defaultCondition(obj_id player, obj_id npc) throws InterruptedException
    {
        return true;
    }
    public boolean ep3_myyydril_greeter_villager_1_condition_Kallaarac(obj_id player, obj_id npc) throws InterruptedException
    {
        return groundquests.isTaskActive(player, "ep3_myyydril_kivvaaa_talkto_1", 0);
    }
    public boolean ep3_myyydril_greeter_villager_1_condition_Helping(obj_id player, obj_id npc) throws InterruptedException
    {
        return groundquests.hasCompletedQuest(player, "ep3_myyydril_kivvaaa_talkto_1");
    }
    public boolean ep3_myyydril_greeter_villager_1_condition_Caverns(obj_id player, obj_id npc) throws InterruptedException
    {
        return groundquests.hasCompletedQuest(player, "ep3_myyydril_nawika_talkto_5");
    }
    public boolean ep3_myyydril_greeter_villager_1_condition_Hero(obj_id player, obj_id npc) throws InterruptedException
    {
        return groundquests.hasCompletedQuest(player, "ep3_myyydril_kallaarac_destroy_3");
    }
    public boolean ep3_myyydril_greeter_villager_1_condition_Doctor(obj_id player, obj_id npc) throws InterruptedException
    {
        return groundquests.isTaskActive(player, "ep3_myyydril_yraka_talkto_6", 0);
    }
    public boolean ep3_myyydril_greeter_villager_1_condition_Lorn(obj_id player, obj_id npc) throws InterruptedException
    {
        return groundquests.isTaskActive(player, "ep3_myyydril_kinesworthy_epic_3", 0);
    }
    public boolean ep3_myyydril_greeter_villager_1_condition_End(obj_id player, obj_id npc) throws InterruptedException
    {
        return groundquests.hasCompletedQuest(player, "ep3_myyydril_kinesworthy_epic_3");
    }
    public int ep3_myyydril_greeter_villager_1_handleBranch1(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_1332"))
        {
            if (ep3_myyydril_greeter_villager_1_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_1333");
                utils.removeScriptVar(player, "conversation.ep3_myyydril_greeter_villager_1.branchId");
                chat.chat(npc, player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int ep3_myyydril_greeter_villager_1_handleBranch3(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_1169"))
        {
            if (ep3_myyydril_greeter_villager_1_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_1170");
                utils.removeScriptVar(player, "conversation.ep3_myyydril_greeter_villager_1.branchId");
                chat.chat(npc, player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int ep3_myyydril_greeter_villager_1_handleBranch5(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_1119"))
        {
            if (ep3_myyydril_greeter_villager_1_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_1121");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (ep3_myyydril_greeter_villager_1_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_1123");
                    }
                    utils.setScriptVar(player, "conversation.ep3_myyydril_greeter_villager_1.branchId", 6);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.ep3_myyydril_greeter_villager_1.branchId");
                    chat.chat(npc, player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int ep3_myyydril_greeter_villager_1_handleBranch6(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_1123"))
        {
            if (ep3_myyydril_greeter_villager_1_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_1125");
                utils.removeScriptVar(player, "conversation.ep3_myyydril_greeter_villager_1.branchId");
                chat.chat(npc, player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int ep3_myyydril_greeter_villager_1_handleBranch8(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_1129"))
        {
            if (ep3_myyydril_greeter_villager_1_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_1131");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (ep3_myyydril_greeter_villager_1_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_1133");
                    }
                    utils.setScriptVar(player, "conversation.ep3_myyydril_greeter_villager_1.branchId", 9);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.ep3_myyydril_greeter_villager_1.branchId");
                    chat.chat(npc, player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int ep3_myyydril_greeter_villager_1_handleBranch9(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_1133"))
        {
            if (ep3_myyydril_greeter_villager_1_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_1135");
                utils.removeScriptVar(player, "conversation.ep3_myyydril_greeter_villager_1.branchId");
                chat.chat(npc, player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int ep3_myyydril_greeter_villager_1_handleBranch11(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_1139"))
        {
            if (ep3_myyydril_greeter_villager_1_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_1141");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (ep3_myyydril_greeter_villager_1_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_1143");
                    }
                    utils.setScriptVar(player, "conversation.ep3_myyydril_greeter_villager_1.branchId", 12);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.ep3_myyydril_greeter_villager_1.branchId");
                    chat.chat(npc, player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int ep3_myyydril_greeter_villager_1_handleBranch12(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_1143"))
        {
            if (ep3_myyydril_greeter_villager_1_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_1145");
                utils.removeScriptVar(player, "conversation.ep3_myyydril_greeter_villager_1.branchId");
                chat.chat(npc, player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int ep3_myyydril_greeter_villager_1_handleBranch14(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_1149"))
        {
            if (ep3_myyydril_greeter_villager_1_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_1151");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (ep3_myyydril_greeter_villager_1_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_1153");
                    }
                    utils.setScriptVar(player, "conversation.ep3_myyydril_greeter_villager_1.branchId", 15);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.ep3_myyydril_greeter_villager_1.branchId");
                    chat.chat(npc, player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int ep3_myyydril_greeter_villager_1_handleBranch15(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_1153"))
        {
            if (ep3_myyydril_greeter_villager_1_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_1155");
                utils.removeScriptVar(player, "conversation.ep3_myyydril_greeter_villager_1.branchId");
                chat.chat(npc, player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int ep3_myyydril_greeter_villager_1_handleBranch17(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_1159"))
        {
            if (ep3_myyydril_greeter_villager_1_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_1161");
                utils.removeScriptVar(player, "conversation.ep3_myyydril_greeter_villager_1.branchId");
                chat.chat(npc, player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int ep3_myyydril_greeter_villager_1_handleBranch19(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_1165"))
        {
            if (ep3_myyydril_greeter_villager_1_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_1167");
                utils.removeScriptVar(player, "conversation.ep3_myyydril_greeter_villager_1.branchId");
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
            detachScript(self, "conversation.ep3_myyydril_greeter_villager_1");
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
        faceTo(self, player);
        return SCRIPT_CONTINUE;
    }
    public int OnIncapacitated(obj_id self, obj_id killer) throws InterruptedException
    {
        clearCondition(self, CONDITION_CONVERSABLE);
        detachScript(self, "conversation.ep3_myyydril_greeter_villager_1");
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
        if (ep3_myyydril_greeter_villager_1_condition_End(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_1331");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (ep3_myyydril_greeter_villager_1_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_1332");
                }
                utils.setScriptVar(player, "conversation.ep3_myyydril_greeter_villager_1.branchId", 1);
                npcStartConversation(player, npc, "ep3_myyydril_greeter_villager_1", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (ep3_myyydril_greeter_villager_1_condition_Lorn(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_1168");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (ep3_myyydril_greeter_villager_1_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_1169");
                }
                utils.setScriptVar(player, "conversation.ep3_myyydril_greeter_villager_1.branchId", 3);
                npcStartConversation(player, npc, "ep3_myyydril_greeter_villager_1", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (ep3_myyydril_greeter_villager_1_condition_Doctor(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_1117");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (ep3_myyydril_greeter_villager_1_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_1119");
                }
                utils.setScriptVar(player, "conversation.ep3_myyydril_greeter_villager_1.branchId", 5);
                npcStartConversation(player, npc, "ep3_myyydril_greeter_villager_1", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (ep3_myyydril_greeter_villager_1_condition_Hero(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_1127");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (ep3_myyydril_greeter_villager_1_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_1129");
                }
                utils.setScriptVar(player, "conversation.ep3_myyydril_greeter_villager_1.branchId", 8);
                npcStartConversation(player, npc, "ep3_myyydril_greeter_villager_1", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (ep3_myyydril_greeter_villager_1_condition_Caverns(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_1137");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (ep3_myyydril_greeter_villager_1_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_1139");
                }
                utils.setScriptVar(player, "conversation.ep3_myyydril_greeter_villager_1.branchId", 11);
                npcStartConversation(player, npc, "ep3_myyydril_greeter_villager_1", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (ep3_myyydril_greeter_villager_1_condition_Helping(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_1147");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (ep3_myyydril_greeter_villager_1_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_1149");
                }
                utils.setScriptVar(player, "conversation.ep3_myyydril_greeter_villager_1.branchId", 14);
                npcStartConversation(player, npc, "ep3_myyydril_greeter_villager_1", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (ep3_myyydril_greeter_villager_1_condition_Kallaarac(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_1157");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (ep3_myyydril_greeter_villager_1_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_1159");
                }
                utils.setScriptVar(player, "conversation.ep3_myyydril_greeter_villager_1.branchId", 17);
                npcStartConversation(player, npc, "ep3_myyydril_greeter_villager_1", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (ep3_myyydril_greeter_villager_1_condition__defaultCondition(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_1163");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (ep3_myyydril_greeter_villager_1_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_1165");
                }
                utils.setScriptVar(player, "conversation.ep3_myyydril_greeter_villager_1.branchId", 19);
                npcStartConversation(player, npc, "ep3_myyydril_greeter_villager_1", message, responses);
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
        if (!conversationId.equals("ep3_myyydril_greeter_villager_1"))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id npc = self;
        int branchId = utils.getIntScriptVar(player, "conversation.ep3_myyydril_greeter_villager_1.branchId");
        if (branchId == 1 && ep3_myyydril_greeter_villager_1_handleBranch1(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 3 && ep3_myyydril_greeter_villager_1_handleBranch3(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 5 && ep3_myyydril_greeter_villager_1_handleBranch5(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 6 && ep3_myyydril_greeter_villager_1_handleBranch6(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 8 && ep3_myyydril_greeter_villager_1_handleBranch8(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 9 && ep3_myyydril_greeter_villager_1_handleBranch9(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 11 && ep3_myyydril_greeter_villager_1_handleBranch11(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 12 && ep3_myyydril_greeter_villager_1_handleBranch12(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 14 && ep3_myyydril_greeter_villager_1_handleBranch14(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 15 && ep3_myyydril_greeter_villager_1_handleBranch15(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 17 && ep3_myyydril_greeter_villager_1_handleBranch17(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 19 && ep3_myyydril_greeter_villager_1_handleBranch19(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        chat.chat(npc, "Error:  Fell through all branches and responses for OnNpcConversationResponse.");
        utils.removeScriptVar(player, "conversation.ep3_myyydril_greeter_villager_1.branchId");
        return SCRIPT_CONTINUE;
    }
}
