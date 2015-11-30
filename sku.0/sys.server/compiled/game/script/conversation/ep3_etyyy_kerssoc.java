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
import script.library.money;
import script.library.space_quest;
import script.library.utils;

public class ep3_etyyy_kerssoc extends script.base_script
{
    public ep3_etyyy_kerssoc()
    {
    }
    public static String c_stringFile = "conversation/ep3_etyyy_kerssoc";
    public boolean ep3_etyyy_kerssoc_condition__defaultCondition(obj_id player, obj_id npc) throws InterruptedException
    {
        return true;
    }
    public boolean ep3_etyyy_kerssoc_condition_hasCompletedAllQuests(obj_id player, obj_id npc) throws InterruptedException
    {
        return (groundquests.isQuestActive(player, "ep3_hunt_kerssoc_enter_etyyy") || groundquests.hasCompletedQuest(player, "ep3_hunt_kerssoc_enter_etyyy"));
    }
    public boolean ep3_etyyy_kerssoc_condition_hasCompletedDeliverQuest(obj_id player, obj_id npc) throws InterruptedException
    {
        return (space_quest.hasWonQuest(player, "deliver_no_pickup", "ep3_hunting_kerssoc_smuggle_goods"));
    }
    public boolean ep3_etyyy_kerssoc_condition_hasCompletedEscortQuest(obj_id player, obj_id npc) throws InterruptedException
    {
        return (space_quest.hasWonQuest(player, "escort", "ep3_hunting_kerssoc_supplies"));
    }
    public boolean ep3_etyyy_kerssoc_condition_isHuntingBantha(obj_id player, obj_id npc) throws InterruptedException
    {
        return (groundquests.isTaskActive(player, "ep3_hunt_kerssoc_bantha_pelts", "kerssoc_huntingBantha"));
    }
    public boolean ep3_etyyy_kerssoc_condition_isOnDeliverQuest(obj_id player, obj_id npc) throws InterruptedException
    {
        return (space_quest.hasQuest(player, "deliver_no_pickup", "ep3_hunting_kerssoc_smuggle_goods"));
    }
    public boolean ep3_etyyy_kerssoc_condition_fromChrilooc(obj_id player, obj_id npc) throws InterruptedException
    {
        return (groundquests.isTaskActive(player, "ep3_hunt_chrilooc_seek_rodians", "chrilooc_talkToKerssoc"));
    }
    public boolean ep3_etyyy_kerssoc_condition_finishedHuntingBantha(obj_id player, obj_id npc) throws InterruptedException
    {
        return (groundquests.isTaskActive(player, "ep3_hunt_kerssoc_bantha_pelts", "kerssoc_kashyyykBanthaPelts") || groundquests.hasCompletedQuest(player, "ep3_hunt_kerssoc_bantha_pelts"));
    }
    public boolean ep3_etyyy_kerssoc_condition_hasCompletedDestroyWeaponsQuest(obj_id player, obj_id npc) throws InterruptedException
    {
        return (space_quest.hasWonQuest(player, "assassinate", "ep3_hunting_kerssoc_destroy_chiss_weapons"));
    }
    public boolean ep3_etyyy_kerssoc_condition_attackingChissCamp(obj_id player, obj_id npc) throws InterruptedException
    {
        return (groundquests.isTaskActive(player, "ep3_hunt_kerssoc_kill_chiss_poachers", "kerssoc_killingChissPoachers"));
    }
    public boolean ep3_etyyy_kerssoc_condition_finishedAttackingChissCamp(obj_id player, obj_id npc) throws InterruptedException
    {
        return (groundquests.isTaskActive(player, "ep3_hunt_kerssoc_kill_chiss_poachers", "kerssoc_attackChissPoachers") || groundquests.hasCompletedQuest(player, "ep3_hunt_kerssoc_kill_chiss_poachers"));
    }
    public boolean ep3_etyyy_kerssoc_condition_hasManfredsDelivery(obj_id player, obj_id npc) throws InterruptedException
    {
        return groundquests.isTaskActive(player, "ep3_hunt_manfred_steal_chiss_goods", "manfred_deliverToKerssoc");
    }
    public boolean ep3_etyyy_kerssoc_condition_alreadyHasSpaceMission(obj_id player, obj_id npc) throws InterruptedException
    {
        return space_quest.hasQuest(player);
    }
    public void ep3_etyyy_kerssoc_action_spokeToKerssoc(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.sendSignal(player, "chrilooc_talkToKerssoc");
    }
    public void ep3_etyyy_kerssoc_action_finishedHuntBantha(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.sendSignal(player, "kerssoc_kashyyykBanthaPelts");
    }
    public void ep3_etyyy_kerssoc_action_huntKashBantha(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.grantQuest(player, "ep3_hunt_kerssoc_bantha_pelts");
    }
    public void ep3_etyyy_kerssoc_action_deliverBanthaHides(obj_id player, obj_id npc) throws InterruptedException
    {
        space_quest.grantQuest(player, "delivery_no_pickup", "ep3_hunting_kerssoc_smuggle_goods");
    }
    public void ep3_etyyy_kerssoc_action_destroyChissWeapons(obj_id player, obj_id npc) throws InterruptedException
    {
        space_quest.grantQuest(player, "assassinate", "ep3_hunting_kerssoc_destroy_chiss_weapons");
    }
    public void ep3_etyyy_kerssoc_action_escortSupplies(obj_id player, obj_id npc) throws InterruptedException
    {
        space_quest.grantQuest(player, "escort", "ep3_hunting_kerssoc_supplies");
    }
    public void ep3_etyyy_kerssoc_action_attackChissCamp(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.grantQuest(player, "ep3_hunt_kerssoc_kill_chiss_poachers");
    }
    public void ep3_etyyy_kerssoc_action_finishedAttackChissCamp(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.sendSignal(player, "kerssoc_attackChissPoachers");
    }
    public void ep3_etyyy_kerssoc_action_enterEtyyy(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.sendSignal(player, "chrilooc_gainEtyyyEntry");
        groundquests.grantQuest(player, "ep3_hunt_kerssoc_enter_etyyy");
    }
    public void ep3_etyyy_kerssoc_action_manfredsDeliverMade(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.sendSignal(player, "manfred_deliverToKerssoc");
    }
    public int ep3_etyyy_kerssoc_handleBranch3(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_1062"))
        {
            if (ep3_etyyy_kerssoc_condition__defaultCondition(player, npc))
            {
                ep3_etyyy_kerssoc_action_enterEtyyy(player, npc);
                string_id message = new string_id(c_stringFile, "s_1064");
                utils.removeScriptVar(player, "conversation.ep3_etyyy_kerssoc.branchId");
                chat.chat(npc, player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_1066"))
        {
            if (ep3_etyyy_kerssoc_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_1068");
                utils.removeScriptVar(player, "conversation.ep3_etyyy_kerssoc.branchId");
                chat.chat(npc, player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int ep3_etyyy_kerssoc_handleBranch7(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_1074"))
        {
            if (ep3_etyyy_kerssoc_condition__defaultCondition(player, npc))
            {
                ep3_etyyy_kerssoc_action_attackChissCamp(player, npc);
                string_id message = new string_id(c_stringFile, "s_1076");
                utils.removeScriptVar(player, "conversation.ep3_etyyy_kerssoc.branchId");
                chat.chat(npc, player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_1078"))
        {
            if (ep3_etyyy_kerssoc_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_1080");
                utils.removeScriptVar(player, "conversation.ep3_etyyy_kerssoc.branchId");
                chat.chat(npc, player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int ep3_etyyy_kerssoc_handleBranch10(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_1084"))
        {
            if (ep3_etyyy_kerssoc_condition_alreadyHasSpaceMission(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_1303");
                utils.removeScriptVar(player, "conversation.ep3_etyyy_kerssoc.branchId");
                chat.chat(npc, player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            if (ep3_etyyy_kerssoc_condition__defaultCondition(player, npc))
            {
                ep3_etyyy_kerssoc_action_destroyChissWeapons(player, npc);
                string_id message = new string_id(c_stringFile, "s_1086");
                utils.removeScriptVar(player, "conversation.ep3_etyyy_kerssoc.branchId");
                chat.chat(npc, player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_1088"))
        {
            if (ep3_etyyy_kerssoc_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_1090");
                utils.removeScriptVar(player, "conversation.ep3_etyyy_kerssoc.branchId");
                chat.chat(npc, player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int ep3_etyyy_kerssoc_handleBranch14(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_1094"))
        {
            if (ep3_etyyy_kerssoc_condition_alreadyHasSpaceMission(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_1302");
                utils.removeScriptVar(player, "conversation.ep3_etyyy_kerssoc.branchId");
                chat.chat(npc, player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            if (ep3_etyyy_kerssoc_condition__defaultCondition(player, npc))
            {
                ep3_etyyy_kerssoc_action_escortSupplies(player, npc);
                string_id message = new string_id(c_stringFile, "s_1096");
                utils.removeScriptVar(player, "conversation.ep3_etyyy_kerssoc.branchId");
                chat.chat(npc, player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_1098"))
        {
            if (ep3_etyyy_kerssoc_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_1100");
                utils.removeScriptVar(player, "conversation.ep3_etyyy_kerssoc.branchId");
                chat.chat(npc, player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int ep3_etyyy_kerssoc_handleBranch19(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_1106"))
        {
            if (ep3_etyyy_kerssoc_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_1108");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (ep3_etyyy_kerssoc_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (ep3_etyyy_kerssoc_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_1110");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_1114");
                    }
                    utils.setScriptVar(player, "conversation.ep3_etyyy_kerssoc.branchId", 20);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.ep3_etyyy_kerssoc.branchId");
                    chat.chat(npc, player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int ep3_etyyy_kerssoc_handleBranch20(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_1110"))
        {
            if (ep3_etyyy_kerssoc_condition_alreadyHasSpaceMission(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_1301");
                utils.removeScriptVar(player, "conversation.ep3_etyyy_kerssoc.branchId");
                chat.chat(npc, player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            if (ep3_etyyy_kerssoc_condition__defaultCondition(player, npc))
            {
                ep3_etyyy_kerssoc_action_deliverBanthaHides(player, npc);
                string_id message = new string_id(c_stringFile, "s_1112");
                utils.removeScriptVar(player, "conversation.ep3_etyyy_kerssoc.branchId");
                chat.chat(npc, player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_1114"))
        {
            if (ep3_etyyy_kerssoc_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_1116");
                utils.removeScriptVar(player, "conversation.ep3_etyyy_kerssoc.branchId");
                chat.chat(npc, player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int ep3_etyyy_kerssoc_handleBranch24(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_1120"))
        {
            if (ep3_etyyy_kerssoc_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_1122");
                utils.removeScriptVar(player, "conversation.ep3_etyyy_kerssoc.branchId");
                chat.chat(npc, player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int ep3_etyyy_kerssoc_handleBranch26(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_1126"))
        {
            if (ep3_etyyy_kerssoc_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_1128");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (ep3_etyyy_kerssoc_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_1130");
                    }
                    utils.setScriptVar(player, "conversation.ep3_etyyy_kerssoc.branchId", 27);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.ep3_etyyy_kerssoc.branchId");
                    chat.chat(npc, player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_1142"))
        {
            if (ep3_etyyy_kerssoc_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_1144");
                utils.removeScriptVar(player, "conversation.ep3_etyyy_kerssoc.branchId");
                chat.chat(npc, player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int ep3_etyyy_kerssoc_handleBranch27(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_1130"))
        {
            if (ep3_etyyy_kerssoc_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_1132");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (ep3_etyyy_kerssoc_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (ep3_etyyy_kerssoc_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_1134");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_1138");
                    }
                    utils.setScriptVar(player, "conversation.ep3_etyyy_kerssoc.branchId", 28);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.ep3_etyyy_kerssoc.branchId");
                    chat.chat(npc, player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int ep3_etyyy_kerssoc_handleBranch28(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_1134"))
        {
            if (ep3_etyyy_kerssoc_condition__defaultCondition(player, npc))
            {
                ep3_etyyy_kerssoc_action_huntKashBantha(player, npc);
                string_id message = new string_id(c_stringFile, "s_1136");
                utils.removeScriptVar(player, "conversation.ep3_etyyy_kerssoc.branchId");
                chat.chat(npc, player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_1138"))
        {
            if (ep3_etyyy_kerssoc_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_1140");
                utils.removeScriptVar(player, "conversation.ep3_etyyy_kerssoc.branchId");
                chat.chat(npc, player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int ep3_etyyy_kerssoc_handleBranch32(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_1148"))
        {
            if (ep3_etyyy_kerssoc_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_1150");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (ep3_etyyy_kerssoc_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_1152");
                    }
                    utils.setScriptVar(player, "conversation.ep3_etyyy_kerssoc.branchId", 33);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.ep3_etyyy_kerssoc.branchId");
                    chat.chat(npc, player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_1164"))
        {
            if (ep3_etyyy_kerssoc_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_1166");
                utils.removeScriptVar(player, "conversation.ep3_etyyy_kerssoc.branchId");
                chat.chat(npc, player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int ep3_etyyy_kerssoc_handleBranch33(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_1152"))
        {
            if (ep3_etyyy_kerssoc_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_1154");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (ep3_etyyy_kerssoc_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (ep3_etyyy_kerssoc_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_1156");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_1160");
                    }
                    utils.setScriptVar(player, "conversation.ep3_etyyy_kerssoc.branchId", 34);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.ep3_etyyy_kerssoc.branchId");
                    chat.chat(npc, player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int ep3_etyyy_kerssoc_handleBranch34(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_1156"))
        {
            if (ep3_etyyy_kerssoc_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_1158");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (ep3_etyyy_kerssoc_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_1130");
                    }
                    utils.setScriptVar(player, "conversation.ep3_etyyy_kerssoc.branchId", 27);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.ep3_etyyy_kerssoc.branchId");
                    chat.chat(npc, player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_1160"))
        {
            if (ep3_etyyy_kerssoc_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_1162");
                utils.removeScriptVar(player, "conversation.ep3_etyyy_kerssoc.branchId");
                chat.chat(npc, player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int ep3_etyyy_kerssoc_handleBranch35(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_1130"))
        {
            if (ep3_etyyy_kerssoc_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_1132");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (ep3_etyyy_kerssoc_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (ep3_etyyy_kerssoc_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_1134");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_1138");
                    }
                    utils.setScriptVar(player, "conversation.ep3_etyyy_kerssoc.branchId", 28);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.ep3_etyyy_kerssoc.branchId");
                    chat.chat(npc, player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        if ((!isMob(self)) || (isPlayer(self)))
        {
            detachScript(self, "conversation.ep3_etyyy_kerssoc");
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
        faceTo(self, player);
        return SCRIPT_CONTINUE;
    }
    public int OnIncapacitated(obj_id self, obj_id killer) throws InterruptedException
    {
        clearCondition(self, CONDITION_CONVERSABLE);
        detachScript(self, "conversation.ep3_etyyy_kerssoc");
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
        if (ep3_etyyy_kerssoc_condition_hasManfredsDelivery(player, npc))
        {
            ep3_etyyy_kerssoc_action_manfredsDeliverMade(player, npc);
            string_id message = new string_id(c_stringFile, "s_1056");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        if (ep3_etyyy_kerssoc_condition_hasCompletedAllQuests(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_1058");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        if (ep3_etyyy_kerssoc_condition_finishedAttackingChissCamp(player, npc))
        {
            ep3_etyyy_kerssoc_action_finishedAttackChissCamp(player, npc);
            string_id message = new string_id(c_stringFile, "s_1060");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (ep3_etyyy_kerssoc_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            boolean hasResponse1 = false;
            if (ep3_etyyy_kerssoc_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_1062");
                }
                if (hasResponse1)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_1066");
                }
                utils.setScriptVar(player, "conversation.ep3_etyyy_kerssoc.branchId", 3);
                npcStartConversation(player, npc, "ep3_etyyy_kerssoc", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (ep3_etyyy_kerssoc_condition_attackingChissCamp(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_1070");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        if (ep3_etyyy_kerssoc_condition_hasCompletedDestroyWeaponsQuest(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_1072");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (ep3_etyyy_kerssoc_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            boolean hasResponse1 = false;
            if (ep3_etyyy_kerssoc_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_1074");
                }
                if (hasResponse1)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_1078");
                }
                utils.setScriptVar(player, "conversation.ep3_etyyy_kerssoc.branchId", 7);
                npcStartConversation(player, npc, "ep3_etyyy_kerssoc", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (ep3_etyyy_kerssoc_condition_hasCompletedEscortQuest(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_1082");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (ep3_etyyy_kerssoc_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            boolean hasResponse1 = false;
            if (ep3_etyyy_kerssoc_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_1084");
                }
                if (hasResponse1)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_1088");
                }
                utils.setScriptVar(player, "conversation.ep3_etyyy_kerssoc.branchId", 10);
                npcStartConversation(player, npc, "ep3_etyyy_kerssoc", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (ep3_etyyy_kerssoc_condition_hasCompletedDeliverQuest(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_1092");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (ep3_etyyy_kerssoc_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            boolean hasResponse1 = false;
            if (ep3_etyyy_kerssoc_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_1094");
                }
                if (hasResponse1)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_1098");
                }
                utils.setScriptVar(player, "conversation.ep3_etyyy_kerssoc.branchId", 14);
                npcStartConversation(player, npc, "ep3_etyyy_kerssoc", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (ep3_etyyy_kerssoc_condition_isOnDeliverQuest(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_1102");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        if (ep3_etyyy_kerssoc_condition_finishedHuntingBantha(player, npc))
        {
            ep3_etyyy_kerssoc_action_finishedHuntBantha(player, npc);
            string_id message = new string_id(c_stringFile, "s_1104");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (ep3_etyyy_kerssoc_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_1106");
                }
                utils.setScriptVar(player, "conversation.ep3_etyyy_kerssoc.branchId", 19);
                npcStartConversation(player, npc, "ep3_etyyy_kerssoc", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (ep3_etyyy_kerssoc_condition_isHuntingBantha(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_1118");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (ep3_etyyy_kerssoc_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_1120");
                }
                utils.setScriptVar(player, "conversation.ep3_etyyy_kerssoc.branchId", 24);
                npcStartConversation(player, npc, "ep3_etyyy_kerssoc", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (ep3_etyyy_kerssoc_condition_fromChrilooc(player, npc))
        {
            doAnimationAction(npc, "greet");
            ep3_etyyy_kerssoc_action_spokeToKerssoc(player, npc);
            string_id message = new string_id(c_stringFile, "s_1124");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (ep3_etyyy_kerssoc_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            boolean hasResponse1 = false;
            if (ep3_etyyy_kerssoc_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_1126");
                }
                if (hasResponse1)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_1142");
                }
                utils.setScriptVar(player, "conversation.ep3_etyyy_kerssoc.branchId", 26);
                npcStartConversation(player, npc, "ep3_etyyy_kerssoc", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (ep3_etyyy_kerssoc_condition__defaultCondition(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_1146");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (ep3_etyyy_kerssoc_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            boolean hasResponse1 = false;
            if (ep3_etyyy_kerssoc_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_1148");
                }
                if (hasResponse1)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_1164");
                }
                utils.setScriptVar(player, "conversation.ep3_etyyy_kerssoc.branchId", 32);
                npcStartConversation(player, npc, "ep3_etyyy_kerssoc", message, responses);
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
        if (!conversationId.equals("ep3_etyyy_kerssoc"))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id npc = self;
        int branchId = utils.getIntScriptVar(player, "conversation.ep3_etyyy_kerssoc.branchId");
        if (branchId == 3 && ep3_etyyy_kerssoc_handleBranch3(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 7 && ep3_etyyy_kerssoc_handleBranch7(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 10 && ep3_etyyy_kerssoc_handleBranch10(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 14 && ep3_etyyy_kerssoc_handleBranch14(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 19 && ep3_etyyy_kerssoc_handleBranch19(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 20 && ep3_etyyy_kerssoc_handleBranch20(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 24 && ep3_etyyy_kerssoc_handleBranch24(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 26 && ep3_etyyy_kerssoc_handleBranch26(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 27 && ep3_etyyy_kerssoc_handleBranch27(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 28 && ep3_etyyy_kerssoc_handleBranch28(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 32 && ep3_etyyy_kerssoc_handleBranch32(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 33 && ep3_etyyy_kerssoc_handleBranch33(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 34 && ep3_etyyy_kerssoc_handleBranch34(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 35 && ep3_etyyy_kerssoc_handleBranch35(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        chat.chat(npc, "Error:  Fell through all branches and responses for OnNpcConversationResponse.");
        utils.removeScriptVar(player, "conversation.ep3_etyyy_kerssoc.branchId");
        return SCRIPT_CONTINUE;
    }
}
