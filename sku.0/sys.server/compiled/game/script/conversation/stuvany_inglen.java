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
import script.library.conversation;
import script.library.groundquests;
import script.library.utils;

public class stuvany_inglen extends script.base_script
{
    public stuvany_inglen()
    {
    }
    public static String c_stringFile = "conversation/stuvany_inglen";
    public boolean stuvany_inglen_condition__defaultCondition(obj_id player, obj_id npc) throws InterruptedException
    {
        return true;
    }
    public boolean stuvany_inglen_condition_canDoPrisoner(obj_id player, obj_id npc) throws InterruptedException
    {
        faceTo(npc, player);
        return !groundquests.isQuestActiveOrComplete(player, "u16_nym_themepark_pirate_prisoner_rescue_collection") && !groundquests.isQuestActiveOrComplete(player, "u16_nym_themepark_pirate_prisoner_rescue_retry_01") && !groundquests.isQuestActiveOrComplete(player, "u16_nym_themepark_pirate_prisoner_rescue_retry_02") && groundquests.isQuestActiveOrComplete(player, "u16_nym_themepark_pirate_hideout");
    }
    public boolean stuvany_inglen_condition_hasReturnedPrisoner(obj_id player, obj_id npc) throws InterruptedException
    {
        faceTo(npc, player);
        return groundquests.isTaskActive(player, "u16_nym_themepark_pirate_prisoner_rescue_collection", "returnPrisonerRescueComplete") || groundquests.isTaskActive(player, "u16_nym_themepark_pirate_prisoner_rescue_retry_01", "returnPrisonerRescueComplete") || groundquests.isTaskActive(player, "u16_nym_themepark_pirate_prisoner_rescue_retry_02", "returnPrisonerRescueComplete");
    }
    public boolean stuvany_inglen_condition_hasPrisonerQuestNotComplete(obj_id player, obj_id npc) throws InterruptedException
    {
        faceTo(npc, player);
        return groundquests.isQuestActive(player, "u16_nym_themepark_pirate_prisoner_rescue_collection") || groundquests.isQuestActive(player, "u16_nym_themepark_pirate_prisoner_rescue_retry_01") || groundquests.isQuestActive(player, "u16_nym_themepark_pirate_prisoner_rescue_retry_02");
    }
    public boolean stuvany_inglen_condition_canDoStealOre(obj_id player, obj_id npc) throws InterruptedException
    {
        faceTo(npc, player);
        return !groundquests.isQuestActiveOrComplete(player, "u16_nym_themepark_pirate_steal_mined_ore_collection") && (groundquests.hasCompletedQuest(player, "u16_nym_themepark_pirate_prisoner_rescue_collection") || groundquests.hasCompletedQuest(player, "u16_nym_themepark_pirate_prisoner_rescue_retry_01") || groundquests.hasCompletedQuest(player, "u16_nym_themepark_pirate_prisoner_rescue_retry_02")) && groundquests.isQuestActiveOrComplete(player, "u16_nym_themepark_mine");
    }
    public boolean stuvany_inglen_condition_hasStealOreQuestNotComplete(obj_id player, obj_id npc) throws InterruptedException
    {
        faceTo(npc, player);
        return groundquests.isQuestActive(player, "u16_nym_themepark_pirate_steal_mined_ore_collection");
    }
    public boolean stuvany_inglen_condition_hasReturnedStealOre(obj_id player, obj_id npc) throws InterruptedException
    {
        faceTo(npc, player);
        return groundquests.isTaskActive(player, "u16_nym_themepark_pirate_steal_mined_ore_collection", "returnStealMinedOreComplete");
    }
    public boolean stuvany_inglen_condition_canDoStealData(obj_id player, obj_id npc) throws InterruptedException
    {
        faceTo(npc, player);
        return !groundquests.isQuestActiveOrComplete(player, "u16_nym_themepark_pirate_steal_lab_data_collection") && groundquests.hasCompletedQuest(player, "u16_nym_themepark_pirate_steal_mined_ore_collection") && groundquests.isQuestActiveOrComplete(player, "u16_nym_themepark_research_facility");
    }
    public boolean stuvany_inglen_condition_hasStealDataQuestNotComplete(obj_id player, obj_id npc) throws InterruptedException
    {
        faceTo(npc, player);
        return groundquests.isQuestActive(player, "u16_nym_themepark_pirate_steal_lab_data_collection");
    }
    public boolean stuvany_inglen_condition_hasReturnedStealData(obj_id player, obj_id npc) throws InterruptedException
    {
        faceTo(npc, player);
        return groundquests.isTaskActive(player, "u16_nym_themepark_pirate_steal_lab_data_collection", "returnStealLabDataComplete");
    }
    public boolean stuvany_inglen_condition_hasntDonePrisoner(obj_id player, obj_id npc) throws InterruptedException
    {
        faceTo(npc, player);
        return !groundquests.isQuestActiveOrComplete(player, "u16_nym_themepark_pirate_hideout");
    }
    public boolean stuvany_inglen_condition_hasntDoneOre(obj_id player, obj_id npc) throws InterruptedException
    {
        faceTo(npc, player);
        return !groundquests.isQuestActiveOrComplete(player, "u16_nym_themepark_mine") && groundquests.hasCompletedQuest(player, "u16_nym_themepark_pirate_hideout") && groundquests.hasCompletedQuest(player, "u16_nym_themepark_pirate_prisoner_rescue_collection");
    }
    public boolean stuvany_inglen_condition_hasntDoneData(obj_id player, obj_id npc) throws InterruptedException
    {
        faceTo(npc, player);
        return !groundquests.isQuestActiveOrComplete(player, "u16_nym_themepark_research_facility") && groundquests.hasCompletedQuest(player, "u16_nym_themepark_mine") && groundquests.hasCompletedQuest(player, "u16_nym_themepark_pirate_steal_mined_ore_collection");
    }
    public boolean stuvany_inglen_condition_hasCompletedAll(obj_id player, obj_id npc) throws InterruptedException
    {
        faceTo(npc, player);
        return groundquests.hasCompletedQuest(player, "u16_nym_themepark_pirate_steal_lab_data_collection");
    }
    public boolean stuvany_inglen_condition_hasCompletedPrisonerRescueCollection(obj_id player, obj_id npc) throws InterruptedException
    {
        return hasCompletedCollection(player, "nyms_rescue_prisoner") && (groundquests.isQuestActive(player, "u16_nym_themepark_pirate_prisoner_rescue_collection") || groundquests.isQuestActive(player, "u16_nym_themepark_pirate_prisoner_rescue_retry_01") || groundquests.isQuestActive(player, "u16_nym_themepark_pirate_prisoner_rescue_retry_02"));
    }
    public boolean stuvany_inglen_condition_hasCompletedMinedOreCollection(obj_id player, obj_id npc) throws InterruptedException
    {
        return hasCompletedCollection(player, "nyms_steal_mined_ore") && groundquests.isQuestActive(player, "u16_nym_themepark_pirate_steal_mined_ore_collection");
    }
    public boolean stuvany_inglen_condition_hasCompletedLabDataCollection(obj_id player, obj_id npc) throws InterruptedException
    {
        return hasCompletedCollection(player, "nyms_steal_lab_data") && groundquests.isQuestActive(player, "u16_nym_themepark_pirate_steal_lab_data_collection");
    }
    public void stuvany_inglen_action_grantPrisonerQuest(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.grantQuest(player, "u16_nym_themepark_pirate_prisoner_rescue_collection");
        if (!hasCompletedCollectionSlot(player, "nym_prisoner_rescue_activation"))
        {
            modifyCollectionSlotValue(player, "nym_prisoner_rescue_activation", 1);
        }
    }
    public void stuvany_inglen_action_completePrisonerQuest(obj_id player, obj_id npc) throws InterruptedException
    {
        if (groundquests.isTaskActive(player, "u16_nym_themepark_pirate_prisoner_rescue_collection", "returnPrisonerRescueComplete") || groundquests.isTaskActive(player, "u16_nym_themepark_pirate_prisoner_rescue_retry_01", "returnPrisonerRescueComplete") || groundquests.isTaskActive(player, "u16_nym_themepark_pirate_prisoner_rescue_retry_02", "returnPrisonerRescueComplete"))
        {
            groundquests.sendSignal(player, "hasCompletedPrisonerRescue");
        }
        if (!hasCompletedCollectionSlot(player, "icon_nyms_master_collection_2_rescue_prisoner"))
        {
            modifyCollectionSlotValue(player, "icon_nyms_master_collection_2_rescue_prisoner", 1);
        }
    }
    public void stuvany_inglen_action_completeStealOreQuest(obj_id player, obj_id npc) throws InterruptedException
    {
        if (groundquests.isTaskActive(player, "u16_nym_themepark_pirate_steal_mined_ore_collection", "returnStealMinedOreComplete"))
        {
            groundquests.sendSignal(player, "hasCompletedStealMinedOre");
        }
        if (!hasCompletedCollectionSlot(player, "icon_nyms_master_collection_2_steal_ore"))
        {
            modifyCollectionSlotValue(player, "icon_nyms_master_collection_2_steal_ore", 1);
        }
    }
    public void stuvany_inglen_action_grantStealOreQuest(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.grantQuest(player, "u16_nym_themepark_pirate_steal_mined_ore_collection");
        if (!hasCompletedCollectionSlot(player, "nym_mined_ore_activation"))
        {
            modifyCollectionSlotValue(player, "nym_mined_ore_activation", 1);
        }
    }
    public void stuvany_inglen_action_grantStealDataQuest(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.grantQuest(player, "u16_nym_themepark_pirate_steal_lab_data_collection");
        if (!hasCompletedCollectionSlot(player, "nym_lab_data_activation"))
        {
            modifyCollectionSlotValue(player, "nym_lab_data_activation", 1);
        }
    }
    public void stuvany_inglen_action_completeStealDataQuest(obj_id player, obj_id npc) throws InterruptedException
    {
        if (groundquests.isTaskActive(player, "u16_nym_themepark_pirate_steal_lab_data_collection", "returnStealLabDataComplete"))
        {
            groundquests.sendSignal(player, "hasCompletedStealLabData");
        }
        if (!hasCompletedCollectionSlot(player, "icon_nyms_master_collection_2_lab_data"))
        {
            modifyCollectionSlotValue(player, "icon_nyms_master_collection_2_lab_data", 1);
        }
    }
    public void stuvany_inglen_action_BruteForceCompletePrisoner(obj_id player, obj_id npc) throws InterruptedException
    {
        if (stuvany_inglen_condition_hasCompletedPrisonerRescueCollection(player, npc))
        {
            if (groundquests.isQuestActive(player, "u16_nym_themepark_pirate_prisoner_rescue_collection"))
            {
                CustomerServiceLog("nyms_themepark", "NPC Conversation - Brute Force completing quest: u16_nym_themepark_pirate_prisoner_rescue_collection for player: " + player + " so they do not remailn bugged.");
                int questid = questGetQuestId("quest/u16_nym_themepark_pirate_prisoner_rescue_collection");
                CustomerServiceLog("nyms_themepark", "Quest Id: " + questid);
                if ((questid != 0) && questIsQuestActive(questid, player))
                {
                    CustomerServiceLog("nyms_themepark", "Quest Id: " + questid);
                    questCompleteQuest(questid, player);
                    stuvany_inglen_action_completePrisonerQuest(player, npc);
                }
            }
            if (groundquests.isQuestActive(player, "u16_nym_themepark_pirate_prisoner_rescue_retry_01"))
            {
                CustomerServiceLog("nyms_themepark", "NPC Conversation - Brute Force completing quest: u16_nym_themepark_pirate_prisoner_rescue_retry_01 for player: " + player + " so they do not remailn bugged.");
                int questid = questGetQuestId("quest/u16_nym_themepark_pirate_prisoner_rescue_retry_01");
                if ((questid != 0) && questIsQuestActive(questid, player))
                {
                    questCompleteQuest(questid, player);
                    stuvany_inglen_action_completePrisonerQuest(player, npc);
                }
            }
            if (groundquests.isQuestActive(player, "u16_nym_themepark_pirate_prisoner_rescue_retry_02"))
            {
                CustomerServiceLog("nyms_themepark", "NPC Conversation - Brute Force completing quest: u16_nym_themepark_pirate_prisoner_rescue_retry_02 for player: " + player + " so they do not remailn bugged.");
                int questid = questGetQuestId("quest/u16_nym_themepark_pirate_prisoner_rescue_retry_02");
                if ((questid != 0) && questIsQuestActive(questid, player))
                {
                    questCompleteQuest(questid, player);
                    stuvany_inglen_action_completePrisonerQuest(player, npc);
                }
            }
        }
    }
    public void stuvany_inglen_action_BruteForceCompleteLabData(obj_id player, obj_id npc) throws InterruptedException
    {
        if (stuvany_inglen_condition_hasCompletedLabDataCollection(player, npc))
        {
            if (groundquests.isQuestActive(player, "u16_nym_themepark_pirate_steal_lab_data_collection"))
            {
                CustomerServiceLog("nyms_themepark", "NPC Conversation - Brute Force completing quest: u16_nym_themepark_pirate_steal_lab_data_collection for player: " + player + " so they do not remailn bugged.");
                int questid = questGetQuestId("quest/u16_nym_themepark_pirate_steal_lab_data_collection");
                if ((questid != 0) && questIsQuestActive(questid, player))
                {
                    questCompleteQuest(questid, player);
                    stuvany_inglen_action_completeStealDataQuest(player, npc);
                }
            }
        }
    }
    public void stuvany_inglen_action_BruteForceCompleteMinedOre(obj_id player, obj_id npc) throws InterruptedException
    {
        if (stuvany_inglen_condition_hasCompletedMinedOreCollection(player, npc))
        {
            if (groundquests.isQuestActive(player, "u16_nym_themepark_pirate_steal_mined_ore_collection"))
            {
                CustomerServiceLog("nyms_themepark", "NPC Conversation - Brute Force completing quest: u16_nym_themepark_pirate_steal_mined_ore_collection for player: " + player + " so they do not remailn bugged.");
                int questid = questGetQuestId("quest/u16_nym_themepark_pirate_steal_mined_ore_collection");
                if ((questid != 0) && questIsQuestActive(questid, player))
                {
                    questCompleteQuest(questid, player);
                    stuvany_inglen_action_completeStealOreQuest(player, npc);
                }
            }
        }
    }
    public void stuvany_inglen_action_correctAllCollectionErrors(obj_id player, obj_id npc) throws InterruptedException
    {
        stuvany_inglen_action_completePrisonerQuest(player, npc);
        stuvany_inglen_action_completeStealOreQuest(player, npc);
        stuvany_inglen_action_completeStealDataQuest(player, npc);
    }
    public int stuvany_inglen_handleBranch2(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_28"))
        {
            if (stuvany_inglen_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_29");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (stuvany_inglen_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_90");
                    }
                    utils.setScriptVar(player, "conversation.stuvany_inglen.branchId", 3);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.stuvany_inglen.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int stuvany_inglen_handleBranch3(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_90"))
        {
            if (stuvany_inglen_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_91");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (stuvany_inglen_condition__defaultCondition(player, npc))
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
                    utils.setScriptVar(player, "conversation.stuvany_inglen.branchId", 4);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.stuvany_inglen.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int stuvany_inglen_handleBranch4(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_92"))
        {
            if (stuvany_inglen_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_93");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (stuvany_inglen_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_94");
                    }
                    utils.setScriptVar(player, "conversation.stuvany_inglen.branchId", 5);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.stuvany_inglen.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int stuvany_inglen_handleBranch5(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_94"))
        {
            if (stuvany_inglen_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_95");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (stuvany_inglen_condition__defaultCondition(player, npc))
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
                    utils.setScriptVar(player, "conversation.stuvany_inglen.branchId", 6);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.stuvany_inglen.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int stuvany_inglen_handleBranch6(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_96"))
        {
            if (stuvany_inglen_condition__defaultCondition(player, npc))
            {
                stuvany_inglen_action_completeStealDataQuest(player, npc);
                string_id message = new string_id(c_stringFile, "s_97");
                utils.removeScriptVar(player, "conversation.stuvany_inglen.branchId");
                prose_package pp = new prose_package();
                pp.stringId = message;
                pp.actor.set(player);
                pp.target.set(npc);
                npcEndConversationWithMessage(player, pp);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int stuvany_inglen_handleBranch9(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_30"))
        {
            if (stuvany_inglen_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_31");
                utils.removeScriptVar(player, "conversation.stuvany_inglen.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int stuvany_inglen_handleBranch11(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_32"))
        {
            if (stuvany_inglen_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_33");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (stuvany_inglen_condition__defaultCondition(player, npc))
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
                    utils.setScriptVar(player, "conversation.stuvany_inglen.branchId", 12);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.stuvany_inglen.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int stuvany_inglen_handleBranch12(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_84"))
        {
            if (stuvany_inglen_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_85");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (stuvany_inglen_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_86");
                    }
                    utils.setScriptVar(player, "conversation.stuvany_inglen.branchId", 13);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.stuvany_inglen.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int stuvany_inglen_handleBranch13(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_86"))
        {
            if (stuvany_inglen_condition__defaultCondition(player, npc))
            {
                stuvany_inglen_action_grantStealDataQuest(player, npc);
                string_id message = new string_id(c_stringFile, "s_87");
                utils.removeScriptVar(player, "conversation.stuvany_inglen.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int stuvany_inglen_handleBranch16(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_39"))
        {
            if (stuvany_inglen_condition__defaultCondition(player, npc))
            {
                stuvany_inglen_action_completeStealOreQuest(player, npc);
                string_id message = new string_id(c_stringFile, "s_41");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (stuvany_inglen_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_82");
                    }
                    utils.setScriptVar(player, "conversation.stuvany_inglen.branchId", 17);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.stuvany_inglen.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int stuvany_inglen_handleBranch17(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_82"))
        {
            if (stuvany_inglen_condition__defaultCondition(player, npc))
            {
                stuvany_inglen_action_completeStealOreQuest(player, npc);
                string_id message = new string_id(c_stringFile, "s_83");
                utils.removeScriptVar(player, "conversation.stuvany_inglen.branchId");
                prose_package pp = new prose_package();
                pp.stringId = message;
                pp.actor.set(player);
                pp.target.set(npc);
                npcEndConversationWithMessage(player, pp);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int stuvany_inglen_handleBranch20(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_48"))
        {
            if (stuvany_inglen_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_50");
                utils.removeScriptVar(player, "conversation.stuvany_inglen.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int stuvany_inglen_handleBranch22(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_72"))
        {
            if (stuvany_inglen_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_73");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (stuvany_inglen_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_74");
                    }
                    utils.setScriptVar(player, "conversation.stuvany_inglen.branchId", 23);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.stuvany_inglen.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int stuvany_inglen_handleBranch23(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_74"))
        {
            if (stuvany_inglen_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_75");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (stuvany_inglen_condition__defaultCondition(player, npc))
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
                    utils.setScriptVar(player, "conversation.stuvany_inglen.branchId", 24);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.stuvany_inglen.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int stuvany_inglen_handleBranch24(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_76"))
        {
            if (stuvany_inglen_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_77");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (stuvany_inglen_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_78");
                    }
                    utils.setScriptVar(player, "conversation.stuvany_inglen.branchId", 25);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.stuvany_inglen.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int stuvany_inglen_handleBranch25(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_78"))
        {
            if (stuvany_inglen_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_79");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (stuvany_inglen_condition__defaultCondition(player, npc))
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
                    utils.setScriptVar(player, "conversation.stuvany_inglen.branchId", 26);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.stuvany_inglen.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int stuvany_inglen_handleBranch26(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_80"))
        {
            if (stuvany_inglen_condition__defaultCondition(player, npc))
            {
                stuvany_inglen_action_grantStealOreQuest(player, npc);
                string_id message = new string_id(c_stringFile, "s_81");
                utils.removeScriptVar(player, "conversation.stuvany_inglen.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int stuvany_inglen_handleBranch29(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_67"))
        {
            if (stuvany_inglen_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_69");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (stuvany_inglen_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_71");
                    }
                    utils.setScriptVar(player, "conversation.stuvany_inglen.branchId", 30);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.stuvany_inglen.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int stuvany_inglen_handleBranch30(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_71"))
        {
            if (stuvany_inglen_condition__defaultCondition(player, npc))
            {
                stuvany_inglen_action_completePrisonerQuest(player, npc);
                string_id message = new string_id(c_stringFile, "s_89");
                utils.removeScriptVar(player, "conversation.stuvany_inglen.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int stuvany_inglen_handleBranch33(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_102"))
        {
            if (stuvany_inglen_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_104");
                utils.removeScriptVar(player, "conversation.stuvany_inglen.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int stuvany_inglen_handleBranch35(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_108"))
        {
            if (stuvany_inglen_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_110");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (stuvany_inglen_condition__defaultCondition(player, npc))
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
                    utils.setScriptVar(player, "conversation.stuvany_inglen.branchId", 36);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.stuvany_inglen.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int stuvany_inglen_handleBranch36(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_112"))
        {
            if (stuvany_inglen_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_114");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (stuvany_inglen_condition__defaultCondition(player, npc))
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
                    utils.setScriptVar(player, "conversation.stuvany_inglen.branchId", 37);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.stuvany_inglen.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int stuvany_inglen_handleBranch37(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_116"))
        {
            if (stuvany_inglen_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_118");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (stuvany_inglen_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_120");
                    }
                    utils.setScriptVar(player, "conversation.stuvany_inglen.branchId", 38);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.stuvany_inglen.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int stuvany_inglen_handleBranch38(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_120"))
        {
            if (stuvany_inglen_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_122");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (stuvany_inglen_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_126");
                    }
                    utils.setScriptVar(player, "conversation.stuvany_inglen.branchId", 39);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.stuvany_inglen.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int stuvany_inglen_handleBranch39(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_126"))
        {
            if (stuvany_inglen_condition__defaultCondition(player, npc))
            {
                stuvany_inglen_action_grantPrisonerQuest(player, npc);
                string_id message = new string_id(c_stringFile, "s_128");
                utils.removeScriptVar(player, "conversation.stuvany_inglen.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        if ((!isTangible(self)) || (isPlayer(self)))
        {
            detachScript(self, "conversation.stuvany_inglen");
        }
        setCondition(self, CONDITION_CONVERSABLE);
        setCondition(self, CONDITION_INTERESTING);
        return SCRIPT_CONTINUE;
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        setCondition(self, CONDITION_CONVERSABLE);
        setCondition(self, CONDITION_INTERESTING);
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
        detachScript(self, "conversation.stuvany_inglen");
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
        if (stuvany_inglen_condition_hasCompletedAll(player, npc))
        {
            stuvany_inglen_action_correctAllCollectionErrors(player, npc);
            string_id message = new string_id(c_stringFile, "s_135");
            prose_package pp = new prose_package();
            pp.stringId = message;
            pp.actor.set(player);
            pp.target.set(npc);
            chat.chat(npc, player, null, null, pp);
            return SCRIPT_CONTINUE;
        }
        if (stuvany_inglen_condition_hasReturnedStealData(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_25");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (stuvany_inglen_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_28");
                }
                utils.setScriptVar(player, "conversation.stuvany_inglen.branchId", 2);
                npcStartConversation(player, npc, "stuvany_inglen", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (stuvany_inglen_condition_hasCompletedLabDataCollection(player, npc))
        {
            stuvany_inglen_action_BruteForceCompleteLabData(player, npc);
            string_id message = new string_id(c_stringFile, "s_131");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        if (stuvany_inglen_condition_hasStealDataQuestNotComplete(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_26");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (stuvany_inglen_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_30");
                }
                utils.setScriptVar(player, "conversation.stuvany_inglen.branchId", 9);
                npcStartConversation(player, npc, "stuvany_inglen", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (stuvany_inglen_condition_canDoStealData(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_27");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (stuvany_inglen_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_32");
                }
                utils.setScriptVar(player, "conversation.stuvany_inglen.branchId", 11);
                npcStartConversation(player, npc, "stuvany_inglen", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (stuvany_inglen_condition_hasntDoneData(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_125");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        if (stuvany_inglen_condition_hasReturnedStealOre(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_37");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (stuvany_inglen_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_39");
                }
                utils.setScriptVar(player, "conversation.stuvany_inglen.branchId", 16);
                prose_package pp = new prose_package();
                pp.stringId = message;
                pp.actor.set(player);
                pp.target.set(npc);
                npcStartConversation(player, npc, "stuvany_inglen", null, pp, responses);
            }
            else 
            {
                prose_package pp = new prose_package();
                pp.stringId = message;
                pp.actor.set(player);
                pp.target.set(npc);
                chat.chat(npc, player, null, null, pp);
            }
            return SCRIPT_CONTINUE;
        }
        if (stuvany_inglen_condition_hasCompletedMinedOreCollection(player, npc))
        {
            stuvany_inglen_action_BruteForceCompleteMinedOre(player, npc);
            string_id message = new string_id(c_stringFile, "s_130");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        if (stuvany_inglen_condition_hasStealOreQuestNotComplete(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_46");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (stuvany_inglen_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_48");
                }
                utils.setScriptVar(player, "conversation.stuvany_inglen.branchId", 20);
                npcStartConversation(player, npc, "stuvany_inglen", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (stuvany_inglen_condition_canDoStealOre(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_52");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (stuvany_inglen_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_72");
                }
                utils.setScriptVar(player, "conversation.stuvany_inglen.branchId", 22);
                npcStartConversation(player, npc, "stuvany_inglen", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (stuvany_inglen_condition_hasntDoneOre(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_124");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        if (stuvany_inglen_condition_hasReturnedPrisoner(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_65");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (stuvany_inglen_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_67");
                }
                utils.setScriptVar(player, "conversation.stuvany_inglen.branchId", 29);
                prose_package pp = new prose_package();
                pp.stringId = message;
                pp.actor.set(player);
                pp.target.set(npc);
                npcStartConversation(player, npc, "stuvany_inglen", null, pp, responses);
            }
            else 
            {
                prose_package pp = new prose_package();
                pp.stringId = message;
                pp.actor.set(player);
                pp.target.set(npc);
                chat.chat(npc, player, null, null, pp);
            }
            return SCRIPT_CONTINUE;
        }
        if (stuvany_inglen_condition_hasCompletedPrisonerRescueCollection(player, npc))
        {
            stuvany_inglen_action_BruteForceCompletePrisoner(player, npc);
            string_id message = new string_id(c_stringFile, "s_129");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        if (stuvany_inglen_condition_hasPrisonerQuestNotComplete(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_100");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (stuvany_inglen_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_102");
                }
                utils.setScriptVar(player, "conversation.stuvany_inglen.branchId", 33);
                prose_package pp = new prose_package();
                pp.stringId = message;
                pp.actor.set(player);
                pp.target.set(npc);
                npcStartConversation(player, npc, "stuvany_inglen", null, pp, responses);
            }
            else 
            {
                prose_package pp = new prose_package();
                pp.stringId = message;
                pp.actor.set(player);
                pp.target.set(npc);
                chat.chat(npc, player, null, null, pp);
            }
            return SCRIPT_CONTINUE;
        }
        if (stuvany_inglen_condition_canDoPrisoner(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_106");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (stuvany_inglen_condition__defaultCondition(player, npc))
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
                utils.setScriptVar(player, "conversation.stuvany_inglen.branchId", 35);
                prose_package pp = new prose_package();
                pp.stringId = message;
                pp.actor.set(player);
                pp.target.set(npc);
                npcStartConversation(player, npc, "stuvany_inglen", null, pp, responses);
            }
            else 
            {
                prose_package pp = new prose_package();
                pp.stringId = message;
                pp.actor.set(player);
                pp.target.set(npc);
                chat.chat(npc, player, null, null, pp);
            }
            return SCRIPT_CONTINUE;
        }
        if (stuvany_inglen_condition_hasntDonePrisoner(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_133");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        if (stuvany_inglen_condition__defaultCondition(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_136");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        chat.chat(npc, "Error:  All conditions for OnStartNpcConversation were false.");
        return SCRIPT_CONTINUE;
    }
    public int OnNpcConversationResponse(obj_id self, String conversationId, obj_id player, string_id response) throws InterruptedException
    {
        if (!conversationId.equals("stuvany_inglen"))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id npc = self;
        int branchId = utils.getIntScriptVar(player, "conversation.stuvany_inglen.branchId");
        if (branchId == 2 && stuvany_inglen_handleBranch2(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 3 && stuvany_inglen_handleBranch3(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 4 && stuvany_inglen_handleBranch4(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 5 && stuvany_inglen_handleBranch5(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 6 && stuvany_inglen_handleBranch6(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 9 && stuvany_inglen_handleBranch9(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 11 && stuvany_inglen_handleBranch11(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 12 && stuvany_inglen_handleBranch12(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 13 && stuvany_inglen_handleBranch13(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 16 && stuvany_inglen_handleBranch16(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 17 && stuvany_inglen_handleBranch17(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 20 && stuvany_inglen_handleBranch20(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 22 && stuvany_inglen_handleBranch22(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 23 && stuvany_inglen_handleBranch23(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 24 && stuvany_inglen_handleBranch24(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 25 && stuvany_inglen_handleBranch25(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 26 && stuvany_inglen_handleBranch26(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 29 && stuvany_inglen_handleBranch29(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 30 && stuvany_inglen_handleBranch30(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 33 && stuvany_inglen_handleBranch33(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 35 && stuvany_inglen_handleBranch35(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 36 && stuvany_inglen_handleBranch36(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 37 && stuvany_inglen_handleBranch37(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 38 && stuvany_inglen_handleBranch38(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 39 && stuvany_inglen_handleBranch39(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        chat.chat(npc, "Error:  Fell through all branches and responses for OnNpcConversationResponse.");
        utils.removeScriptVar(player, "conversation.stuvany_inglen.branchId");
        return SCRIPT_CONTINUE;
    }
}
