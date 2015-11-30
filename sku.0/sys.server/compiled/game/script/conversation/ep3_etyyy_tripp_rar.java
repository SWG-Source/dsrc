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
import script.library.space_quest;
import script.library.utils;

public class ep3_etyyy_tripp_rar extends script.base_script
{
    public ep3_etyyy_tripp_rar()
    {
    }
    public static String c_stringFile = "conversation/ep3_etyyy_tripp_rar";
    public boolean ep3_etyyy_tripp_rar_condition__defaultCondition(obj_id player, obj_id npc) throws InterruptedException
    {
        return true;
    }
    public boolean ep3_etyyy_tripp_rar_condition_completedTrippHunts(obj_id player, obj_id npc) throws InterruptedException
    {
        return groundquests.hasCompletedQuest(player, "ep3_hunt_tripp_collect_mouf_incisors");
    }
    public boolean ep3_etyyy_tripp_rar_condition_finishedCollectingMoufPelts(obj_id player, obj_id npc) throws InterruptedException
    {
        return groundquests.isTaskActive(player, "ep3_hunt_tripp_collect_mouf_pelts", "tripp_moufPelts") || groundquests.hasCompletedQuest(player, "ep3_hunt_tripp_collect_mouf_pelts");
    }
    public boolean ep3_etyyy_tripp_rar_condition_speakWithTripp(obj_id player, obj_id npc) throws InterruptedException
    {
        return groundquests.isTaskActive(player, "ep3_hunt_tripp_collect_mouf_pelts", "tripp_talkToTripp");
    }
    public boolean ep3_etyyy_tripp_rar_condition_isCollectingMoufIncisors(obj_id player, obj_id npc) throws InterruptedException
    {
        return groundquests.isTaskActive(player, "ep3_hunt_tripp_collect_mouf_incisors", "tripp_collectingMoufIncisors");
    }
    public boolean ep3_etyyy_tripp_rar_condition_isCollectingMoufPelts(obj_id player, obj_id npc) throws InterruptedException
    {
        return groundquests.isTaskActive(player, "ep3_hunt_tripp_collect_mouf_pelts", "tripp_collectingMoufPelts");
    }
    public boolean ep3_etyyy_tripp_rar_condition_finishedCollectingMoufIncisors(obj_id player, obj_id npc) throws InterruptedException
    {
        return groundquests.isTaskActive(player, "ep3_hunt_tripp_collect_mouf_incisors", "tripp_moufIncisors");
    }
    public boolean ep3_etyyy_tripp_rar_condition_killedBrightclaw(obj_id player, obj_id npc) throws InterruptedException
    {
        return groundquests.isQuestActive(player, "ep3_hunt_loot_brightclaw_killed");
    }
    public boolean ep3_etyyy_tripp_rar_condition_killedPaleclaw(obj_id player, obj_id npc) throws InterruptedException
    {
        return groundquests.isQuestActive(player, "ep3_hunt_loot_paleclaw_killed");
    }
    public boolean ep3_etyyy_tripp_rar_condition_killedBrightclawPlusAll(obj_id player, obj_id npc) throws InterruptedException
    {
        return groundquests.hasCompletedQuest(player, "ep3_hunt_loot_paleclaw_killed") && groundquests.hasCompletedQuest(player, "ep3_hunt_loot_silkthrower_killed") && groundquests.hasCompletedQuest(player, "ep3_hunt_loot_stoneleg_killed") && groundquests.hasCompletedQuest(player, "ep3_hunt_loot_spiketop_killed") && groundquests.hasCompletedQuest(player, "ep3_hunt_loot_greyclimber_killed");
    }
    public boolean ep3_etyyy_tripp_rar_condition_killedPaleclawPlusAll(obj_id player, obj_id npc) throws InterruptedException
    {
        return groundquests.hasCompletedQuest(player, "ep3_hunt_loot_brightclaw_killed") && groundquests.hasCompletedQuest(player, "ep3_hunt_loot_silkthrower_killed") && groundquests.hasCompletedQuest(player, "ep3_hunt_loot_stoneleg_killed") && groundquests.hasCompletedQuest(player, "ep3_hunt_loot_spiketop_killed") && groundquests.hasCompletedQuest(player, "ep3_hunt_loot_greyclimber_killed");
    }
    public boolean ep3_etyyy_tripp_rar_condition_spaceMissionOffer(obj_id player, obj_id npc) throws InterruptedException
    {
        return groundquests.hasCompletedQuest(player, "ep3_hunt_tripp_collect_mouf_incisors") && space_quest.hasWonQuest(player, "assassinate", "ep3_hunting_banol_destroy_tripps_goods");
    }
    public boolean ep3_etyyy_tripp_rar_condition_spaceMissionWon(obj_id player, obj_id npc) throws InterruptedException
    {
        return space_quest.hasWonQuest(player, "escort", "ep3_hunting_tripp_protect_shipment");
    }
    public boolean ep3_etyyy_tripp_rar_condition_alreadyHasSpaceMission(obj_id player, obj_id npc) throws InterruptedException
    {
        return space_quest.hasQuest(player);
    }
    public boolean ep3_etyyy_tripp_rar_condition_isProtectingSpaceShipment(obj_id player, obj_id npc) throws InterruptedException
    {
        return space_quest.hasQuest(player, "escort", "ep3_hunting_tripp_protect_shipment");
    }
    public boolean ep3_etyyy_tripp_rar_condition_spaceMissionFailed(obj_id player, obj_id npc) throws InterruptedException
    {
        return space_quest.hasFailedQuest(player, "escort", "ep3_hunting_tripp_protect_shipment") || space_quest.hasAbortedQuest(player, "escort", "ep3_hunting_tripp_protect_shipment");
    }
    public void ep3_etyyy_tripp_rar_action_collectMoufIncisors(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.grantQuest(player, "ep3_hunt_tripp_collect_mouf_incisors");
    }
    public void ep3_etyyy_tripp_rar_action_doneCollectingMoufPelts(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.sendSignal(player, "tripp_moufPelts");
    }
    public void ep3_etyyy_tripp_rar_action_speakWithSordaan(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.sendSignal(player, "tripp_moufIncisors");
        groundquests.grantQuest(player, "ep3_hunt_sordaan_seek_sordaan");
        groundquests.sendSignal(player, "sordaan_trippSendsYou");
    }
    public void ep3_etyyy_tripp_rar_action_collectMoufPelts(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.sendSignal(player, "tripp_talkToTripp");
    }
    public void ep3_etyyy_tripp_rar_action_paleclawReward(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.sendSignal(player, "lootQuest_defeatedPaleclaw");
    }
    public void ep3_etyyy_tripp_rar_action_brightclawReward(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.sendSignal(player, "lootQuest_defeatedBrightclaw");
    }
    public void ep3_etyyy_tripp_rar_action_brightclawRewardPlusAll(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.sendSignal(player, "lootQuest_defeatedBrightclaw");
        groundquests.grantQuest(player, "ep3_hunt_loot_completed_all");
    }
    public void ep3_etyyy_tripp_rar_action_paleclawRewardPlusAll(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.sendSignal(player, "lootQuest_defeatedPaleclaw");
        groundquests.grantQuest(player, "ep3_hunt_loot_completed_all");
    }
    public void ep3_etyyy_tripp_rar_action_grantSpaceMission(obj_id player, obj_id npc) throws InterruptedException
    {
        space_quest.clearQuestFlags(player, "escort", "ep3_hunting_tripp_protect_shipment");
        space_quest.grantQuest(player, "escort", "ep3_hunting_tripp_protect_shipment");
    }
    public int ep3_etyyy_tripp_rar_handleBranch1(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_372"))
        {
            if (ep3_etyyy_tripp_rar_condition_spaceMissionWon(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_374");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (ep3_etyyy_tripp_rar_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_376");
                    }
                    utils.setScriptVar(player, "conversation.ep3_etyyy_tripp_rar.branchId", 2);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.ep3_etyyy_tripp_rar.branchId");
                    chat.chat(npc, player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_380"))
        {
            if (ep3_etyyy_tripp_rar_condition_killedBrightclawPlusAll(player, npc))
            {
                ep3_etyyy_tripp_rar_action_brightclawRewardPlusAll(player, npc);
                string_id message = new string_id(c_stringFile, "s_470");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (ep3_etyyy_tripp_rar_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_472");
                    }
                    utils.setScriptVar(player, "conversation.ep3_etyyy_tripp_rar.branchId", 26);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.ep3_etyyy_tripp_rar.branchId");
                    chat.chat(npc, player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
            if (ep3_etyyy_tripp_rar_condition__defaultCondition(player, npc))
            {
                ep3_etyyy_tripp_rar_action_brightclawReward(player, npc);
                string_id message = new string_id(c_stringFile, "s_476");
                utils.removeScriptVar(player, "conversation.ep3_etyyy_tripp_rar.branchId");
                chat.chat(npc, player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_382"))
        {
            if (ep3_etyyy_tripp_rar_condition_killedPaleclawPlusAll(player, npc))
            {
                ep3_etyyy_tripp_rar_action_paleclawRewardPlusAll(player, npc);
                string_id message = new string_id(c_stringFile, "s_480");
                utils.removeScriptVar(player, "conversation.ep3_etyyy_tripp_rar.branchId");
                chat.chat(npc, player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            if (ep3_etyyy_tripp_rar_condition__defaultCondition(player, npc))
            {
                ep3_etyyy_tripp_rar_action_paleclawReward(player, npc);
                string_id message = new string_id(c_stringFile, "s_482");
                utils.removeScriptVar(player, "conversation.ep3_etyyy_tripp_rar.branchId");
                chat.chat(npc, player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int ep3_etyyy_tripp_rar_handleBranch2(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_376"))
        {
            if (ep3_etyyy_tripp_rar_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_378");
                utils.removeScriptVar(player, "conversation.ep3_etyyy_tripp_rar.branchId");
                chat.chat(npc, player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int ep3_etyyy_tripp_rar_handleBranch4(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_230"))
        {
            if (ep3_etyyy_tripp_rar_condition_alreadyHasSpaceMission(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_750");
                utils.removeScriptVar(player, "conversation.ep3_etyyy_tripp_rar.branchId");
                chat.chat(npc, player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            if (ep3_etyyy_tripp_rar_condition__defaultCondition(player, npc))
            {
                ep3_etyyy_tripp_rar_action_grantSpaceMission(player, npc);
                string_id message = new string_id(c_stringFile, "s_388");
                utils.removeScriptVar(player, "conversation.ep3_etyyy_tripp_rar.branchId");
                chat.chat(npc, player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_233"))
        {
            if (ep3_etyyy_tripp_rar_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_235");
                utils.removeScriptVar(player, "conversation.ep3_etyyy_tripp_rar.branchId");
                chat.chat(npc, player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_241"))
        {
            if (ep3_etyyy_tripp_rar_condition_killedBrightclawPlusAll(player, npc))
            {
                ep3_etyyy_tripp_rar_action_brightclawRewardPlusAll(player, npc);
                string_id message = new string_id(c_stringFile, "s_470");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (ep3_etyyy_tripp_rar_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_472");
                    }
                    utils.setScriptVar(player, "conversation.ep3_etyyy_tripp_rar.branchId", 26);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.ep3_etyyy_tripp_rar.branchId");
                    chat.chat(npc, player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
            if (ep3_etyyy_tripp_rar_condition__defaultCondition(player, npc))
            {
                ep3_etyyy_tripp_rar_action_brightclawReward(player, npc);
                string_id message = new string_id(c_stringFile, "s_476");
                utils.removeScriptVar(player, "conversation.ep3_etyyy_tripp_rar.branchId");
                chat.chat(npc, player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_243"))
        {
            if (ep3_etyyy_tripp_rar_condition_killedPaleclawPlusAll(player, npc))
            {
                ep3_etyyy_tripp_rar_action_paleclawRewardPlusAll(player, npc);
                string_id message = new string_id(c_stringFile, "s_480");
                utils.removeScriptVar(player, "conversation.ep3_etyyy_tripp_rar.branchId");
                chat.chat(npc, player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            if (ep3_etyyy_tripp_rar_condition__defaultCondition(player, npc))
            {
                ep3_etyyy_tripp_rar_action_paleclawReward(player, npc);
                string_id message = new string_id(c_stringFile, "s_482");
                utils.removeScriptVar(player, "conversation.ep3_etyyy_tripp_rar.branchId");
                chat.chat(npc, player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int ep3_etyyy_tripp_rar_handleBranch6(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_237"))
        {
            if (ep3_etyyy_tripp_rar_condition_killedBrightclawPlusAll(player, npc))
            {
                ep3_etyyy_tripp_rar_action_brightclawRewardPlusAll(player, npc);
                string_id message = new string_id(c_stringFile, "s_470");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (ep3_etyyy_tripp_rar_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_472");
                    }
                    utils.setScriptVar(player, "conversation.ep3_etyyy_tripp_rar.branchId", 26);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.ep3_etyyy_tripp_rar.branchId");
                    chat.chat(npc, player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
            if (ep3_etyyy_tripp_rar_condition__defaultCondition(player, npc))
            {
                ep3_etyyy_tripp_rar_action_brightclawReward(player, npc);
                string_id message = new string_id(c_stringFile, "s_476");
                utils.removeScriptVar(player, "conversation.ep3_etyyy_tripp_rar.branchId");
                chat.chat(npc, player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_239"))
        {
            if (ep3_etyyy_tripp_rar_condition_killedPaleclawPlusAll(player, npc))
            {
                ep3_etyyy_tripp_rar_action_paleclawRewardPlusAll(player, npc);
                string_id message = new string_id(c_stringFile, "s_480");
                utils.removeScriptVar(player, "conversation.ep3_etyyy_tripp_rar.branchId");
                chat.chat(npc, player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            if (ep3_etyyy_tripp_rar_condition__defaultCondition(player, npc))
            {
                ep3_etyyy_tripp_rar_action_paleclawReward(player, npc);
                string_id message = new string_id(c_stringFile, "s_482");
                utils.removeScriptVar(player, "conversation.ep3_etyyy_tripp_rar.branchId");
                chat.chat(npc, player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int ep3_etyyy_tripp_rar_handleBranch7(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_386"))
        {
            if (ep3_etyyy_tripp_rar_condition_alreadyHasSpaceMission(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_750");
                utils.removeScriptVar(player, "conversation.ep3_etyyy_tripp_rar.branchId");
                chat.chat(npc, player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            if (ep3_etyyy_tripp_rar_condition__defaultCondition(player, npc))
            {
                ep3_etyyy_tripp_rar_action_grantSpaceMission(player, npc);
                string_id message = new string_id(c_stringFile, "s_388");
                utils.removeScriptVar(player, "conversation.ep3_etyyy_tripp_rar.branchId");
                chat.chat(npc, player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_390"))
        {
            if (ep3_etyyy_tripp_rar_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_392");
                utils.removeScriptVar(player, "conversation.ep3_etyyy_tripp_rar.branchId");
                chat.chat(npc, player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_394"))
        {
            if (ep3_etyyy_tripp_rar_condition_killedBrightclawPlusAll(player, npc))
            {
                ep3_etyyy_tripp_rar_action_brightclawRewardPlusAll(player, npc);
                string_id message = new string_id(c_stringFile, "s_470");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (ep3_etyyy_tripp_rar_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_472");
                    }
                    utils.setScriptVar(player, "conversation.ep3_etyyy_tripp_rar.branchId", 26);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.ep3_etyyy_tripp_rar.branchId");
                    chat.chat(npc, player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
            if (ep3_etyyy_tripp_rar_condition__defaultCondition(player, npc))
            {
                ep3_etyyy_tripp_rar_action_brightclawReward(player, npc);
                string_id message = new string_id(c_stringFile, "s_476");
                utils.removeScriptVar(player, "conversation.ep3_etyyy_tripp_rar.branchId");
                chat.chat(npc, player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_396"))
        {
            if (ep3_etyyy_tripp_rar_condition_killedPaleclawPlusAll(player, npc))
            {
                ep3_etyyy_tripp_rar_action_paleclawRewardPlusAll(player, npc);
                string_id message = new string_id(c_stringFile, "s_480");
                utils.removeScriptVar(player, "conversation.ep3_etyyy_tripp_rar.branchId");
                chat.chat(npc, player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            if (ep3_etyyy_tripp_rar_condition__defaultCondition(player, npc))
            {
                ep3_etyyy_tripp_rar_action_paleclawReward(player, npc);
                string_id message = new string_id(c_stringFile, "s_482");
                utils.removeScriptVar(player, "conversation.ep3_etyyy_tripp_rar.branchId");
                chat.chat(npc, player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int ep3_etyyy_tripp_rar_handleBranch11(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_400"))
        {
            if (ep3_etyyy_tripp_rar_condition_killedBrightclawPlusAll(player, npc))
            {
                ep3_etyyy_tripp_rar_action_brightclawRewardPlusAll(player, npc);
                string_id message = new string_id(c_stringFile, "s_470");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (ep3_etyyy_tripp_rar_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_472");
                    }
                    utils.setScriptVar(player, "conversation.ep3_etyyy_tripp_rar.branchId", 26);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.ep3_etyyy_tripp_rar.branchId");
                    chat.chat(npc, player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
            if (ep3_etyyy_tripp_rar_condition__defaultCondition(player, npc))
            {
                ep3_etyyy_tripp_rar_action_brightclawReward(player, npc);
                string_id message = new string_id(c_stringFile, "s_476");
                utils.removeScriptVar(player, "conversation.ep3_etyyy_tripp_rar.branchId");
                chat.chat(npc, player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_402"))
        {
            if (ep3_etyyy_tripp_rar_condition_killedPaleclawPlusAll(player, npc))
            {
                ep3_etyyy_tripp_rar_action_paleclawRewardPlusAll(player, npc);
                string_id message = new string_id(c_stringFile, "s_480");
                utils.removeScriptVar(player, "conversation.ep3_etyyy_tripp_rar.branchId");
                chat.chat(npc, player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            if (ep3_etyyy_tripp_rar_condition__defaultCondition(player, npc))
            {
                ep3_etyyy_tripp_rar_action_paleclawReward(player, npc);
                string_id message = new string_id(c_stringFile, "s_482");
                utils.removeScriptVar(player, "conversation.ep3_etyyy_tripp_rar.branchId");
                chat.chat(npc, player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int ep3_etyyy_tripp_rar_handleBranch12(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_406"))
        {
            if (ep3_etyyy_tripp_rar_condition__defaultCondition(player, npc))
            {
                ep3_etyyy_tripp_rar_action_speakWithSordaan(player, npc);
                string_id message = new string_id(c_stringFile, "s_408");
                utils.removeScriptVar(player, "conversation.ep3_etyyy_tripp_rar.branchId");
                chat.chat(npc, player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_410"))
        {
            if (ep3_etyyy_tripp_rar_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_412");
                utils.removeScriptVar(player, "conversation.ep3_etyyy_tripp_rar.branchId");
                chat.chat(npc, player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_414"))
        {
            if (ep3_etyyy_tripp_rar_condition_killedBrightclawPlusAll(player, npc))
            {
                ep3_etyyy_tripp_rar_action_brightclawRewardPlusAll(player, npc);
                string_id message = new string_id(c_stringFile, "s_470");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (ep3_etyyy_tripp_rar_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_472");
                    }
                    utils.setScriptVar(player, "conversation.ep3_etyyy_tripp_rar.branchId", 26);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.ep3_etyyy_tripp_rar.branchId");
                    chat.chat(npc, player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
            if (ep3_etyyy_tripp_rar_condition__defaultCondition(player, npc))
            {
                ep3_etyyy_tripp_rar_action_brightclawReward(player, npc);
                string_id message = new string_id(c_stringFile, "s_476");
                utils.removeScriptVar(player, "conversation.ep3_etyyy_tripp_rar.branchId");
                chat.chat(npc, player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_416"))
        {
            if (ep3_etyyy_tripp_rar_condition_killedPaleclawPlusAll(player, npc))
            {
                ep3_etyyy_tripp_rar_action_paleclawRewardPlusAll(player, npc);
                string_id message = new string_id(c_stringFile, "s_480");
                utils.removeScriptVar(player, "conversation.ep3_etyyy_tripp_rar.branchId");
                chat.chat(npc, player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            if (ep3_etyyy_tripp_rar_condition__defaultCondition(player, npc))
            {
                ep3_etyyy_tripp_rar_action_paleclawReward(player, npc);
                string_id message = new string_id(c_stringFile, "s_482");
                utils.removeScriptVar(player, "conversation.ep3_etyyy_tripp_rar.branchId");
                chat.chat(npc, player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int ep3_etyyy_tripp_rar_handleBranch15(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_420"))
        {
            if (ep3_etyyy_tripp_rar_condition_killedBrightclawPlusAll(player, npc))
            {
                ep3_etyyy_tripp_rar_action_brightclawRewardPlusAll(player, npc);
                string_id message = new string_id(c_stringFile, "s_470");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (ep3_etyyy_tripp_rar_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_472");
                    }
                    utils.setScriptVar(player, "conversation.ep3_etyyy_tripp_rar.branchId", 26);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.ep3_etyyy_tripp_rar.branchId");
                    chat.chat(npc, player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
            if (ep3_etyyy_tripp_rar_condition__defaultCondition(player, npc))
            {
                ep3_etyyy_tripp_rar_action_brightclawReward(player, npc);
                string_id message = new string_id(c_stringFile, "s_476");
                utils.removeScriptVar(player, "conversation.ep3_etyyy_tripp_rar.branchId");
                chat.chat(npc, player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_422"))
        {
            if (ep3_etyyy_tripp_rar_condition_killedPaleclawPlusAll(player, npc))
            {
                ep3_etyyy_tripp_rar_action_paleclawRewardPlusAll(player, npc);
                string_id message = new string_id(c_stringFile, "s_480");
                utils.removeScriptVar(player, "conversation.ep3_etyyy_tripp_rar.branchId");
                chat.chat(npc, player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            if (ep3_etyyy_tripp_rar_condition__defaultCondition(player, npc))
            {
                ep3_etyyy_tripp_rar_action_paleclawReward(player, npc);
                string_id message = new string_id(c_stringFile, "s_482");
                utils.removeScriptVar(player, "conversation.ep3_etyyy_tripp_rar.branchId");
                chat.chat(npc, player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int ep3_etyyy_tripp_rar_handleBranch16(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_426"))
        {
            if (ep3_etyyy_tripp_rar_condition__defaultCondition(player, npc))
            {
                ep3_etyyy_tripp_rar_action_collectMoufIncisors(player, npc);
                string_id message = new string_id(c_stringFile, "s_428");
                utils.removeScriptVar(player, "conversation.ep3_etyyy_tripp_rar.branchId");
                chat.chat(npc, player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_430"))
        {
            if (ep3_etyyy_tripp_rar_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_432");
                utils.removeScriptVar(player, "conversation.ep3_etyyy_tripp_rar.branchId");
                chat.chat(npc, player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_434"))
        {
            if (ep3_etyyy_tripp_rar_condition_killedBrightclawPlusAll(player, npc))
            {
                ep3_etyyy_tripp_rar_action_brightclawRewardPlusAll(player, npc);
                string_id message = new string_id(c_stringFile, "s_470");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (ep3_etyyy_tripp_rar_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_472");
                    }
                    utils.setScriptVar(player, "conversation.ep3_etyyy_tripp_rar.branchId", 26);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.ep3_etyyy_tripp_rar.branchId");
                    chat.chat(npc, player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
            if (ep3_etyyy_tripp_rar_condition__defaultCondition(player, npc))
            {
                ep3_etyyy_tripp_rar_action_brightclawReward(player, npc);
                string_id message = new string_id(c_stringFile, "s_476");
                utils.removeScriptVar(player, "conversation.ep3_etyyy_tripp_rar.branchId");
                chat.chat(npc, player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_436"))
        {
            if (ep3_etyyy_tripp_rar_condition_killedPaleclawPlusAll(player, npc))
            {
                ep3_etyyy_tripp_rar_action_paleclawRewardPlusAll(player, npc);
                string_id message = new string_id(c_stringFile, "s_480");
                utils.removeScriptVar(player, "conversation.ep3_etyyy_tripp_rar.branchId");
                chat.chat(npc, player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            if (ep3_etyyy_tripp_rar_condition__defaultCondition(player, npc))
            {
                ep3_etyyy_tripp_rar_action_paleclawReward(player, npc);
                string_id message = new string_id(c_stringFile, "s_482");
                utils.removeScriptVar(player, "conversation.ep3_etyyy_tripp_rar.branchId");
                chat.chat(npc, player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int ep3_etyyy_tripp_rar_handleBranch19(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_440"))
        {
            if (ep3_etyyy_tripp_rar_condition_killedBrightclawPlusAll(player, npc))
            {
                ep3_etyyy_tripp_rar_action_brightclawRewardPlusAll(player, npc);
                string_id message = new string_id(c_stringFile, "s_470");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (ep3_etyyy_tripp_rar_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_472");
                    }
                    utils.setScriptVar(player, "conversation.ep3_etyyy_tripp_rar.branchId", 26);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.ep3_etyyy_tripp_rar.branchId");
                    chat.chat(npc, player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
            if (ep3_etyyy_tripp_rar_condition__defaultCondition(player, npc))
            {
                ep3_etyyy_tripp_rar_action_brightclawReward(player, npc);
                string_id message = new string_id(c_stringFile, "s_476");
                utils.removeScriptVar(player, "conversation.ep3_etyyy_tripp_rar.branchId");
                chat.chat(npc, player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_442"))
        {
            if (ep3_etyyy_tripp_rar_condition_killedPaleclawPlusAll(player, npc))
            {
                ep3_etyyy_tripp_rar_action_paleclawRewardPlusAll(player, npc);
                string_id message = new string_id(c_stringFile, "s_480");
                utils.removeScriptVar(player, "conversation.ep3_etyyy_tripp_rar.branchId");
                chat.chat(npc, player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            if (ep3_etyyy_tripp_rar_condition__defaultCondition(player, npc))
            {
                ep3_etyyy_tripp_rar_action_paleclawReward(player, npc);
                string_id message = new string_id(c_stringFile, "s_482");
                utils.removeScriptVar(player, "conversation.ep3_etyyy_tripp_rar.branchId");
                chat.chat(npc, player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int ep3_etyyy_tripp_rar_handleBranch20(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_446"))
        {
            if (ep3_etyyy_tripp_rar_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_448");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (ep3_etyyy_tripp_rar_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (ep3_etyyy_tripp_rar_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_450");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_454");
                    }
                    utils.setScriptVar(player, "conversation.ep3_etyyy_tripp_rar.branchId", 21);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.ep3_etyyy_tripp_rar.branchId");
                    chat.chat(npc, player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_458"))
        {
            if (ep3_etyyy_tripp_rar_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_460");
                utils.removeScriptVar(player, "conversation.ep3_etyyy_tripp_rar.branchId");
                chat.chat(npc, player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_462"))
        {
            if (ep3_etyyy_tripp_rar_condition_killedBrightclawPlusAll(player, npc))
            {
                ep3_etyyy_tripp_rar_action_brightclawRewardPlusAll(player, npc);
                string_id message = new string_id(c_stringFile, "s_470");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (ep3_etyyy_tripp_rar_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_472");
                    }
                    utils.setScriptVar(player, "conversation.ep3_etyyy_tripp_rar.branchId", 26);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.ep3_etyyy_tripp_rar.branchId");
                    chat.chat(npc, player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
            if (ep3_etyyy_tripp_rar_condition__defaultCondition(player, npc))
            {
                ep3_etyyy_tripp_rar_action_brightclawReward(player, npc);
                string_id message = new string_id(c_stringFile, "s_476");
                utils.removeScriptVar(player, "conversation.ep3_etyyy_tripp_rar.branchId");
                chat.chat(npc, player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_464"))
        {
            if (ep3_etyyy_tripp_rar_condition_killedPaleclawPlusAll(player, npc))
            {
                ep3_etyyy_tripp_rar_action_paleclawRewardPlusAll(player, npc);
                string_id message = new string_id(c_stringFile, "s_480");
                utils.removeScriptVar(player, "conversation.ep3_etyyy_tripp_rar.branchId");
                chat.chat(npc, player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            if (ep3_etyyy_tripp_rar_condition__defaultCondition(player, npc))
            {
                ep3_etyyy_tripp_rar_action_paleclawReward(player, npc);
                string_id message = new string_id(c_stringFile, "s_482");
                utils.removeScriptVar(player, "conversation.ep3_etyyy_tripp_rar.branchId");
                chat.chat(npc, player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int ep3_etyyy_tripp_rar_handleBranch21(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_450"))
        {
            if (ep3_etyyy_tripp_rar_condition__defaultCondition(player, npc))
            {
                ep3_etyyy_tripp_rar_action_collectMoufPelts(player, npc);
                string_id message = new string_id(c_stringFile, "s_452");
                utils.removeScriptVar(player, "conversation.ep3_etyyy_tripp_rar.branchId");
                chat.chat(npc, player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_454"))
        {
            if (ep3_etyyy_tripp_rar_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_456");
                utils.removeScriptVar(player, "conversation.ep3_etyyy_tripp_rar.branchId");
                chat.chat(npc, player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int ep3_etyyy_tripp_rar_handleBranch25(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_468"))
        {
            if (ep3_etyyy_tripp_rar_condition_killedBrightclawPlusAll(player, npc))
            {
                ep3_etyyy_tripp_rar_action_brightclawRewardPlusAll(player, npc);
                string_id message = new string_id(c_stringFile, "s_470");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (ep3_etyyy_tripp_rar_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_472");
                    }
                    utils.setScriptVar(player, "conversation.ep3_etyyy_tripp_rar.branchId", 26);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.ep3_etyyy_tripp_rar.branchId");
                    chat.chat(npc, player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
            if (ep3_etyyy_tripp_rar_condition__defaultCondition(player, npc))
            {
                ep3_etyyy_tripp_rar_action_brightclawReward(player, npc);
                string_id message = new string_id(c_stringFile, "s_476");
                utils.removeScriptVar(player, "conversation.ep3_etyyy_tripp_rar.branchId");
                chat.chat(npc, player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_478"))
        {
            if (ep3_etyyy_tripp_rar_condition_killedPaleclawPlusAll(player, npc))
            {
                ep3_etyyy_tripp_rar_action_paleclawRewardPlusAll(player, npc);
                string_id message = new string_id(c_stringFile, "s_480");
                utils.removeScriptVar(player, "conversation.ep3_etyyy_tripp_rar.branchId");
                chat.chat(npc, player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            if (ep3_etyyy_tripp_rar_condition__defaultCondition(player, npc))
            {
                ep3_etyyy_tripp_rar_action_paleclawReward(player, npc);
                string_id message = new string_id(c_stringFile, "s_482");
                utils.removeScriptVar(player, "conversation.ep3_etyyy_tripp_rar.branchId");
                chat.chat(npc, player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int ep3_etyyy_tripp_rar_handleBranch26(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_472"))
        {
            if (ep3_etyyy_tripp_rar_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_474");
                utils.removeScriptVar(player, "conversation.ep3_etyyy_tripp_rar.branchId");
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
            detachScript(self, "conversation.ep3_etyyy_tripp_rar");
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
        detachScript(self, "conversation.ep3_etyyy_tripp_rar");
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
        if (ep3_etyyy_tripp_rar_condition_spaceMissionWon(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_370");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (ep3_etyyy_tripp_rar_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            boolean hasResponse1 = false;
            if (ep3_etyyy_tripp_rar_condition_killedBrightclaw(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse1 = true;
            }
            boolean hasResponse2 = false;
            if (ep3_etyyy_tripp_rar_condition_killedPaleclaw(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_372");
                }
                if (hasResponse1)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_380");
                }
                if (hasResponse2)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_382");
                }
                utils.setScriptVar(player, "conversation.ep3_etyyy_tripp_rar.branchId", 1);
                npcStartConversation(player, npc, "ep3_etyyy_tripp_rar", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (ep3_etyyy_tripp_rar_condition_spaceMissionFailed(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_156");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (ep3_etyyy_tripp_rar_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            boolean hasResponse1 = false;
            if (ep3_etyyy_tripp_rar_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse1 = true;
            }
            boolean hasResponse2 = false;
            if (ep3_etyyy_tripp_rar_condition_killedBrightclaw(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse2 = true;
            }
            boolean hasResponse3 = false;
            if (ep3_etyyy_tripp_rar_condition_killedPaleclaw(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse3 = true;
            }
            if (hasResponse)
            {
                int responseIndex = 0;
                string_id responses[] = new string_id[numberOfResponses];
                if (hasResponse0)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_230");
                }
                if (hasResponse1)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_233");
                }
                if (hasResponse2)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_241");
                }
                if (hasResponse3)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_243");
                }
                utils.setScriptVar(player, "conversation.ep3_etyyy_tripp_rar.branchId", 4);
                npcStartConversation(player, npc, "ep3_etyyy_tripp_rar", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (ep3_etyyy_tripp_rar_condition_isProtectingSpaceShipment(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_749");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (ep3_etyyy_tripp_rar_condition_killedBrightclaw(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            boolean hasResponse1 = false;
            if (ep3_etyyy_tripp_rar_condition_killedPaleclaw(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_237");
                }
                if (hasResponse1)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_239");
                }
                utils.setScriptVar(player, "conversation.ep3_etyyy_tripp_rar.branchId", 6);
                npcStartConversation(player, npc, "ep3_etyyy_tripp_rar", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (ep3_etyyy_tripp_rar_condition_spaceMissionOffer(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_384");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (ep3_etyyy_tripp_rar_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            boolean hasResponse1 = false;
            if (ep3_etyyy_tripp_rar_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse1 = true;
            }
            boolean hasResponse2 = false;
            if (ep3_etyyy_tripp_rar_condition_killedBrightclaw(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse2 = true;
            }
            boolean hasResponse3 = false;
            if (ep3_etyyy_tripp_rar_condition_killedPaleclaw(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse3 = true;
            }
            if (hasResponse)
            {
                int responseIndex = 0;
                string_id responses[] = new string_id[numberOfResponses];
                if (hasResponse0)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_386");
                }
                if (hasResponse1)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_390");
                }
                if (hasResponse2)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_394");
                }
                if (hasResponse3)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_396");
                }
                utils.setScriptVar(player, "conversation.ep3_etyyy_tripp_rar.branchId", 7);
                npcStartConversation(player, npc, "ep3_etyyy_tripp_rar", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (ep3_etyyy_tripp_rar_condition_completedTrippHunts(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_398");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (ep3_etyyy_tripp_rar_condition_killedBrightclaw(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            boolean hasResponse1 = false;
            if (ep3_etyyy_tripp_rar_condition_killedPaleclaw(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_400");
                }
                if (hasResponse1)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_402");
                }
                utils.setScriptVar(player, "conversation.ep3_etyyy_tripp_rar.branchId", 11);
                npcStartConversation(player, npc, "ep3_etyyy_tripp_rar", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (ep3_etyyy_tripp_rar_condition_finishedCollectingMoufIncisors(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_404");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (ep3_etyyy_tripp_rar_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            boolean hasResponse1 = false;
            if (ep3_etyyy_tripp_rar_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse1 = true;
            }
            boolean hasResponse2 = false;
            if (ep3_etyyy_tripp_rar_condition_killedBrightclaw(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse2 = true;
            }
            boolean hasResponse3 = false;
            if (ep3_etyyy_tripp_rar_condition_killedPaleclaw(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse3 = true;
            }
            if (hasResponse)
            {
                int responseIndex = 0;
                string_id responses[] = new string_id[numberOfResponses];
                if (hasResponse0)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_406");
                }
                if (hasResponse1)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_410");
                }
                if (hasResponse2)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_414");
                }
                if (hasResponse3)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_416");
                }
                utils.setScriptVar(player, "conversation.ep3_etyyy_tripp_rar.branchId", 12);
                npcStartConversation(player, npc, "ep3_etyyy_tripp_rar", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (ep3_etyyy_tripp_rar_condition_isCollectingMoufIncisors(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_418");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (ep3_etyyy_tripp_rar_condition_killedBrightclaw(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            boolean hasResponse1 = false;
            if (ep3_etyyy_tripp_rar_condition_killedPaleclaw(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_420");
                }
                if (hasResponse1)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_422");
                }
                utils.setScriptVar(player, "conversation.ep3_etyyy_tripp_rar.branchId", 15);
                npcStartConversation(player, npc, "ep3_etyyy_tripp_rar", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (ep3_etyyy_tripp_rar_condition_finishedCollectingMoufPelts(player, npc))
        {
            ep3_etyyy_tripp_rar_action_doneCollectingMoufPelts(player, npc);
            string_id message = new string_id(c_stringFile, "s_424");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (ep3_etyyy_tripp_rar_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            boolean hasResponse1 = false;
            if (ep3_etyyy_tripp_rar_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse1 = true;
            }
            boolean hasResponse2 = false;
            if (ep3_etyyy_tripp_rar_condition_killedBrightclaw(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse2 = true;
            }
            boolean hasResponse3 = false;
            if (ep3_etyyy_tripp_rar_condition_killedPaleclaw(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse3 = true;
            }
            if (hasResponse)
            {
                int responseIndex = 0;
                string_id responses[] = new string_id[numberOfResponses];
                if (hasResponse0)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_426");
                }
                if (hasResponse1)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_430");
                }
                if (hasResponse2)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_434");
                }
                if (hasResponse3)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_436");
                }
                utils.setScriptVar(player, "conversation.ep3_etyyy_tripp_rar.branchId", 16);
                npcStartConversation(player, npc, "ep3_etyyy_tripp_rar", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (ep3_etyyy_tripp_rar_condition_isCollectingMoufPelts(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_438");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (ep3_etyyy_tripp_rar_condition_killedBrightclaw(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            boolean hasResponse1 = false;
            if (ep3_etyyy_tripp_rar_condition_killedPaleclaw(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_440");
                }
                if (hasResponse1)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_442");
                }
                utils.setScriptVar(player, "conversation.ep3_etyyy_tripp_rar.branchId", 19);
                npcStartConversation(player, npc, "ep3_etyyy_tripp_rar", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (ep3_etyyy_tripp_rar_condition_speakWithTripp(player, npc))
        {
            doAnimationAction(npc, "greet");
            string_id message = new string_id(c_stringFile, "s_444");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (ep3_etyyy_tripp_rar_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            boolean hasResponse1 = false;
            if (ep3_etyyy_tripp_rar_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse1 = true;
            }
            boolean hasResponse2 = false;
            if (ep3_etyyy_tripp_rar_condition_killedBrightclaw(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse2 = true;
            }
            boolean hasResponse3 = false;
            if (ep3_etyyy_tripp_rar_condition_killedPaleclaw(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse3 = true;
            }
            if (hasResponse)
            {
                int responseIndex = 0;
                string_id responses[] = new string_id[numberOfResponses];
                if (hasResponse0)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_446");
                }
                if (hasResponse1)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_458");
                }
                if (hasResponse2)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_462");
                }
                if (hasResponse3)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_464");
                }
                utils.setScriptVar(player, "conversation.ep3_etyyy_tripp_rar.branchId", 20);
                npcStartConversation(player, npc, "ep3_etyyy_tripp_rar", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (ep3_etyyy_tripp_rar_condition__defaultCondition(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_466");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (ep3_etyyy_tripp_rar_condition_killedBrightclaw(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            boolean hasResponse1 = false;
            if (ep3_etyyy_tripp_rar_condition_killedPaleclaw(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_468");
                }
                if (hasResponse1)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_478");
                }
                utils.setScriptVar(player, "conversation.ep3_etyyy_tripp_rar.branchId", 25);
                npcStartConversation(player, npc, "ep3_etyyy_tripp_rar", message, responses);
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
        if (!conversationId.equals("ep3_etyyy_tripp_rar"))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id npc = self;
        int branchId = utils.getIntScriptVar(player, "conversation.ep3_etyyy_tripp_rar.branchId");
        if (branchId == 1 && ep3_etyyy_tripp_rar_handleBranch1(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 2 && ep3_etyyy_tripp_rar_handleBranch2(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 4 && ep3_etyyy_tripp_rar_handleBranch4(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 6 && ep3_etyyy_tripp_rar_handleBranch6(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 7 && ep3_etyyy_tripp_rar_handleBranch7(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 11 && ep3_etyyy_tripp_rar_handleBranch11(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 12 && ep3_etyyy_tripp_rar_handleBranch12(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 15 && ep3_etyyy_tripp_rar_handleBranch15(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 16 && ep3_etyyy_tripp_rar_handleBranch16(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 19 && ep3_etyyy_tripp_rar_handleBranch19(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 20 && ep3_etyyy_tripp_rar_handleBranch20(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 21 && ep3_etyyy_tripp_rar_handleBranch21(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 25 && ep3_etyyy_tripp_rar_handleBranch25(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 26 && ep3_etyyy_tripp_rar_handleBranch26(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        chat.chat(npc, "Error:  Fell through all branches and responses for OnNpcConversationResponse.");
        utils.removeScriptVar(player, "conversation.ep3_etyyy_tripp_rar.branchId");
        return SCRIPT_CONTINUE;
    }
}
