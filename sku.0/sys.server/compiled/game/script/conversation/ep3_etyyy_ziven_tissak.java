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

public class ep3_etyyy_ziven_tissak extends script.base_script
{
    public ep3_etyyy_ziven_tissak()
    {
    }
    public static String c_stringFile = "conversation/ep3_etyyy_ziven_tissak";
    public boolean ep3_etyyy_ziven_tissak_condition__defaultCondition(obj_id player, obj_id npc) throws InterruptedException
    {
        return true;
    }
    public boolean ep3_etyyy_ziven_tissak_condition_hasCompletedZivenHunts(obj_id player, obj_id npc) throws InterruptedException
    {
        return groundquests.hasCompletedQuest(player, "ep3_hunt_ziven_collect_webweaver_eyes");
    }
    public boolean ep3_etyyy_ziven_tissak_condition_finishedWeaverEyes(obj_id player, obj_id npc) throws InterruptedException
    {
        return groundquests.isTaskActive(player, "ep3_hunt_ziven_collect_webweaver_eyes", "ziven_webweaverEyes");
    }
    public boolean ep3_etyyy_ziven_tissak_condition_finishedWeaverFangs(obj_id player, obj_id npc) throws InterruptedException
    {
        return groundquests.isTaskActive(player, "ep3_hunt_ziven_collect_webweaver_fangs", "ziven_webweaverFangs") || groundquests.hasCompletedQuest(player, "ep3_hunt_ziven_collect_webweaver_fangs");
    }
    public boolean ep3_etyyy_ziven_tissak_condition_isCollectingWeaverEyes(obj_id player, obj_id npc) throws InterruptedException
    {
        return groundquests.isTaskActive(player, "ep3_hunt_ziven_collect_webweaver_eyes", "ziven_collectWebweaverEyes");
    }
    public boolean ep3_etyyy_ziven_tissak_condition_isCollectingWeaverFangs(obj_id player, obj_id npc) throws InterruptedException
    {
        return groundquests.isTaskActive(player, "ep3_hunt_ziven_collect_webweaver_fangs", "ziven_collectWebweaverFangs");
    }
    public boolean ep3_etyyy_ziven_tissak_condition_speakWithZiven(obj_id player, obj_id npc) throws InterruptedException
    {
        return groundquests.isTaskActive(player, "ep3_hunt_ziven_collect_webweaver_fangs", "ziven_talkToZiven");
    }
    public boolean ep3_etyyy_ziven_tissak_condition_newToEtyyy(obj_id player, obj_id npc) throws InterruptedException
    {
        return groundquests.isTaskActive(player, "ep3_hunt_kerssoc_enter_etyyy", "etyyy_talkToZiven");
    }
    public boolean ep3_etyyy_ziven_tissak_condition_killedSilkthrowerPlusAll(obj_id player, obj_id npc) throws InterruptedException
    {
        return groundquests.hasCompletedQuest(player, "ep3_hunt_loot_brightclaw_killed") && groundquests.hasCompletedQuest(player, "ep3_hunt_loot_paleclaw_killed") && groundquests.hasCompletedQuest(player, "ep3_hunt_loot_stoneleg_killed") && groundquests.hasCompletedQuest(player, "ep3_hunt_loot_spiketop_killed") && groundquests.hasCompletedQuest(player, "ep3_hunt_loot_greyclimber_killed");
    }
    public boolean ep3_etyyy_ziven_tissak_condition_killedSilkthrower(obj_id player, obj_id npc) throws InterruptedException
    {
        return groundquests.isQuestActive(player, "ep3_hunt_loot_silkthrower_killed");
    }
    public boolean ep3_etyyy_ziven_tissak_condition_startSpaceMissions(obj_id player, obj_id npc) throws InterruptedException
    {
        return groundquests.hasCompletedQuest(player, "ep3_hunt_ziven_collect_webweaver_eyes") && space_quest.hasWonQuest(player, "recovery", "ep3_hunting_banol_capture_fordan");
    }
    public boolean ep3_etyyy_ziven_tissak_condition_fordanShipRescueWon(obj_id player, obj_id npc) throws InterruptedException
    {
        return space_quest.hasWonQuest(player, "rescue", "ep3_hunting_ziven_fordans_ship");
    }
    public boolean ep3_etyyy_ziven_tissak_condition_fordanShipRescueFailed(obj_id player, obj_id npc) throws InterruptedException
    {
        return space_quest.hasFailedQuest(player, "rescue", "ep3_hunting_ziven_fordans_ship") || space_quest.hasAbortedQuest(player, "rescue", "ep3_hunting_ziven_fordans_ship");
    }
    public boolean ep3_etyyy_ziven_tissak_condition_alreadyHasSpaceMission(obj_id player, obj_id npc) throws InterruptedException
    {
        return space_quest.hasQuest(player);
    }
    public boolean ep3_etyyy_ziven_tissak_condition_isRescuingFordansShip(obj_id player, obj_id npc) throws InterruptedException
    {
        return space_quest.hasQuest(player, "rescue", "ep3_hunting_ziven_fordans_ship");
    }
    public boolean ep3_etyyy_ziven_tissak_condition_revengeOneUnderway(obj_id player, obj_id npc) throws InterruptedException
    {
        return space_quest.hasQuest(player, "assassinate", "ep3_hunting_ziven_vs_sordaans_freighter_01");
    }
    public boolean ep3_etyyy_ziven_tissak_condition_revengeOneComplete(obj_id player, obj_id npc) throws InterruptedException
    {
        return space_quest.hasWonQuest(player, "assassinate", "ep3_hunting_ziven_vs_sordaans_freighter_01");
    }
    public boolean ep3_etyyy_ziven_tissak_condition_revengeTwoUnderway(obj_id player, obj_id npc) throws InterruptedException
    {
        return space_quest.hasQuest(player, "assassinate", "ep3_hunting_ziven_vs_sordaans_freighter_02");
    }
    public boolean ep3_etyyy_ziven_tissak_condition_revengeOneFailed(obj_id player, obj_id npc) throws InterruptedException
    {
        return space_quest.hasFailedQuest(player, "assassinate", "ep3_hunting_ziven_vs_sordaans_freighter_01") || space_quest.hasAbortedQuest(player, "assassinate", "ep3_hunting_ziven_vs_sordaans_freighter_01");
    }
    public boolean ep3_etyyy_ziven_tissak_condition_revengeTwoFailed(obj_id player, obj_id npc) throws InterruptedException
    {
        return space_quest.hasFailedQuest(player, "assassinate", "ep3_hunting_ziven_vs_sordaans_freighter_02") || space_quest.hasAbortedQuest(player, "assassinate", "ep3_hunting_ziven_vs_sordaans_freighter_02");
    }
    public boolean ep3_etyyy_ziven_tissak_condition_revengeTwoCompleted(obj_id player, obj_id npc) throws InterruptedException
    {
        return space_quest.hasWonQuest(player, "assassinate", "ep3_hunting_ziven_vs_sordaans_freighter_02");
    }
    public void ep3_etyyy_ziven_tissak_action_finishedCollectingWeaverFangs(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.sendSignal(player, "ziven_webweaverFangs");
    }
    public void ep3_etyyy_ziven_tissak_action_speakWithSordaan(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.sendSignal(player, "ziven_webweaverEyes");
        groundquests.grantQuest(player, "ep3_hunt_sordaan_seek_sordaan");
        groundquests.sendSignal(player, "sordaan_zivenSendsYou");
    }
    public void ep3_etyyy_ziven_tissak_action_collectWeaverFangs(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.sendSignal(player, "ziven_talkToZiven");
    }
    public void ep3_etyyy_ziven_tissak_action_collectWeaverEyes(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.grantQuest(player, "ep3_hunt_ziven_collect_webweaver_eyes");
    }
    public void ep3_etyyy_ziven_tissak_action_sendToTuwezz(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.sendSignal(player, "etyyy_talkToZiven");
        if (!groundquests.isQuestActive(player, "ep3_hunt_tuwezz_kill_diseased_ullers") && !groundquests.hasCompletedQuest(player, "ep3_hunt_tuwezz_kill_diseased_ullers"))
        {
            groundquests.grantQuest(player, "ep3_hunt_tuwezz_kill_diseased_ullers");
        }
    }
    public void ep3_etyyy_ziven_tissak_action_silkthrowerRewardPlusAll(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.sendSignal(player, "lootQuest_defeatedSilkthrower");
        groundquests.grantQuest(player, "ep3_hunt_loot_completed_all");
    }
    public void ep3_etyyy_ziven_tissak_action_silkthrowerReward(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.sendSignal(player, "lootQuest_defeatedSilkthrower");
    }
    public void ep3_etyyy_ziven_tissak_action_giveFordanShipRescue(obj_id player, obj_id npc) throws InterruptedException
    {
        space_quest.clearQuestFlags(player, "rescue", "ep3_hunting_ziven_fordans_ship");
        space_quest.grantQuest(player, "rescue", "ep3_hunting_ziven_fordans_ship");
    }
    public void ep3_etyyy_ziven_tissak_action_giveRevengeTwo(obj_id player, obj_id npc) throws InterruptedException
    {
        space_quest.clearQuestFlags(player, "assassinate", "ep3_hunting_ziven_vs_sordaans_freighter_02");
        space_quest.grantQuest(player, "assassinate", "ep3_hunting_ziven_vs_sordaans_freighter_02");
    }
    public void ep3_etyyy_ziven_tissak_action_giveRevengeOne(obj_id player, obj_id npc) throws InterruptedException
    {
        space_quest.clearQuestFlags(player, "assassinate", "ep3_hunting_ziven_vs_sordaans_freighter_01");
        space_quest.grantQuest(player, "assassinate", "ep3_hunting_ziven_vs_sordaans_freighter_01");
    }
    public int ep3_etyyy_ziven_tissak_handleBranch1(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_263"))
        {
            if (ep3_etyyy_ziven_tissak_condition_killedSilkthrowerPlusAll(player, npc))
            {
                ep3_etyyy_ziven_tissak_action_silkthrowerRewardPlusAll(player, npc);
                string_id message = new string_id(c_stringFile, "s_1763");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (ep3_etyyy_ziven_tissak_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_1765");
                    }
                    utils.setScriptVar(player, "conversation.ep3_etyyy_ziven_tissak.branchId", 41);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.ep3_etyyy_ziven_tissak.branchId");
                    chat.chat(npc, player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
            if (ep3_etyyy_ziven_tissak_condition__defaultCondition(player, npc))
            {
                ep3_etyyy_ziven_tissak_action_silkthrowerReward(player, npc);
                string_id message = new string_id(c_stringFile, "s_1769");
                utils.removeScriptVar(player, "conversation.ep3_etyyy_ziven_tissak.branchId");
                chat.chat(npc, player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int ep3_etyyy_ziven_tissak_handleBranch2(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_884"))
        {
            if (ep3_etyyy_ziven_tissak_condition_alreadyHasSpaceMission(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_768");
                utils.removeScriptVar(player, "conversation.ep3_etyyy_ziven_tissak.branchId");
                chat.chat(npc, player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            if (ep3_etyyy_ziven_tissak_condition__defaultCondition(player, npc))
            {
                ep3_etyyy_ziven_tissak_action_giveRevengeTwo(player, npc);
                string_id message = new string_id(c_stringFile, "s_769");
                utils.removeScriptVar(player, "conversation.ep3_etyyy_ziven_tissak.branchId");
                chat.chat(npc, player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_886"))
        {
            if (ep3_etyyy_ziven_tissak_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_888");
                utils.removeScriptVar(player, "conversation.ep3_etyyy_ziven_tissak.branchId");
                chat.chat(npc, player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_261"))
        {
            if (ep3_etyyy_ziven_tissak_condition_killedSilkthrowerPlusAll(player, npc))
            {
                ep3_etyyy_ziven_tissak_action_silkthrowerRewardPlusAll(player, npc);
                string_id message = new string_id(c_stringFile, "s_1763");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (ep3_etyyy_ziven_tissak_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_1765");
                    }
                    utils.setScriptVar(player, "conversation.ep3_etyyy_ziven_tissak.branchId", 41);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.ep3_etyyy_ziven_tissak.branchId");
                    chat.chat(npc, player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
            if (ep3_etyyy_ziven_tissak_condition__defaultCondition(player, npc))
            {
                ep3_etyyy_ziven_tissak_action_silkthrowerReward(player, npc);
                string_id message = new string_id(c_stringFile, "s_1769");
                utils.removeScriptVar(player, "conversation.ep3_etyyy_ziven_tissak.branchId");
                chat.chat(npc, player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int ep3_etyyy_ziven_tissak_handleBranch4(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_259"))
        {
            if (ep3_etyyy_ziven_tissak_condition_killedSilkthrowerPlusAll(player, npc))
            {
                ep3_etyyy_ziven_tissak_action_silkthrowerRewardPlusAll(player, npc);
                string_id message = new string_id(c_stringFile, "s_1763");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (ep3_etyyy_ziven_tissak_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_1765");
                    }
                    utils.setScriptVar(player, "conversation.ep3_etyyy_ziven_tissak.branchId", 41);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.ep3_etyyy_ziven_tissak.branchId");
                    chat.chat(npc, player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
            if (ep3_etyyy_ziven_tissak_condition__defaultCondition(player, npc))
            {
                ep3_etyyy_ziven_tissak_action_silkthrowerReward(player, npc);
                string_id message = new string_id(c_stringFile, "s_1769");
                utils.removeScriptVar(player, "conversation.ep3_etyyy_ziven_tissak.branchId");
                chat.chat(npc, player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int ep3_etyyy_ziven_tissak_handleBranch5(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_766"))
        {
            if (ep3_etyyy_ziven_tissak_condition_alreadyHasSpaceMission(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_768");
                utils.removeScriptVar(player, "conversation.ep3_etyyy_ziven_tissak.branchId");
                chat.chat(npc, player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            if (ep3_etyyy_ziven_tissak_condition__defaultCondition(player, npc))
            {
                ep3_etyyy_ziven_tissak_action_giveRevengeTwo(player, npc);
                string_id message = new string_id(c_stringFile, "s_769");
                utils.removeScriptVar(player, "conversation.ep3_etyyy_ziven_tissak.branchId");
                chat.chat(npc, player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_772"))
        {
            if (ep3_etyyy_ziven_tissak_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_774");
                utils.removeScriptVar(player, "conversation.ep3_etyyy_ziven_tissak.branchId");
                chat.chat(npc, player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_257"))
        {
            if (ep3_etyyy_ziven_tissak_condition_killedSilkthrowerPlusAll(player, npc))
            {
                ep3_etyyy_ziven_tissak_action_silkthrowerRewardPlusAll(player, npc);
                string_id message = new string_id(c_stringFile, "s_1763");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (ep3_etyyy_ziven_tissak_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_1765");
                    }
                    utils.setScriptVar(player, "conversation.ep3_etyyy_ziven_tissak.branchId", 41);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.ep3_etyyy_ziven_tissak.branchId");
                    chat.chat(npc, player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
            if (ep3_etyyy_ziven_tissak_condition__defaultCondition(player, npc))
            {
                ep3_etyyy_ziven_tissak_action_silkthrowerReward(player, npc);
                string_id message = new string_id(c_stringFile, "s_1769");
                utils.removeScriptVar(player, "conversation.ep3_etyyy_ziven_tissak.branchId");
                chat.chat(npc, player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int ep3_etyyy_ziven_tissak_handleBranch9(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_876"))
        {
            if (ep3_etyyy_ziven_tissak_condition_alreadyHasSpaceMission(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_760");
                utils.removeScriptVar(player, "conversation.ep3_etyyy_ziven_tissak.branchId");
                chat.chat(npc, player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            if (ep3_etyyy_ziven_tissak_condition__defaultCondition(player, npc))
            {
                ep3_etyyy_ziven_tissak_action_giveRevengeOne(player, npc);
                string_id message = new string_id(c_stringFile, "s_761");
                utils.removeScriptVar(player, "conversation.ep3_etyyy_ziven_tissak.branchId");
                chat.chat(npc, player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_879"))
        {
            if (ep3_etyyy_ziven_tissak_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_881");
                utils.removeScriptVar(player, "conversation.ep3_etyyy_ziven_tissak.branchId");
                chat.chat(npc, player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_255"))
        {
            if (ep3_etyyy_ziven_tissak_condition_killedSilkthrowerPlusAll(player, npc))
            {
                ep3_etyyy_ziven_tissak_action_silkthrowerRewardPlusAll(player, npc);
                string_id message = new string_id(c_stringFile, "s_1763");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (ep3_etyyy_ziven_tissak_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_1765");
                    }
                    utils.setScriptVar(player, "conversation.ep3_etyyy_ziven_tissak.branchId", 41);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.ep3_etyyy_ziven_tissak.branchId");
                    chat.chat(npc, player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
            if (ep3_etyyy_ziven_tissak_condition__defaultCondition(player, npc))
            {
                ep3_etyyy_ziven_tissak_action_silkthrowerReward(player, npc);
                string_id message = new string_id(c_stringFile, "s_1769");
                utils.removeScriptVar(player, "conversation.ep3_etyyy_ziven_tissak.branchId");
                chat.chat(npc, player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int ep3_etyyy_ziven_tissak_handleBranch11(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_253"))
        {
            if (ep3_etyyy_ziven_tissak_condition_killedSilkthrowerPlusAll(player, npc))
            {
                ep3_etyyy_ziven_tissak_action_silkthrowerRewardPlusAll(player, npc);
                string_id message = new string_id(c_stringFile, "s_1763");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (ep3_etyyy_ziven_tissak_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_1765");
                    }
                    utils.setScriptVar(player, "conversation.ep3_etyyy_ziven_tissak.branchId", 41);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.ep3_etyyy_ziven_tissak.branchId");
                    chat.chat(npc, player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
            if (ep3_etyyy_ziven_tissak_condition__defaultCondition(player, npc))
            {
                ep3_etyyy_ziven_tissak_action_silkthrowerReward(player, npc);
                string_id message = new string_id(c_stringFile, "s_1769");
                utils.removeScriptVar(player, "conversation.ep3_etyyy_ziven_tissak.branchId");
                chat.chat(npc, player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int ep3_etyyy_ziven_tissak_handleBranch12(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_758"))
        {
            if (ep3_etyyy_ziven_tissak_condition_alreadyHasSpaceMission(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_760");
                utils.removeScriptVar(player, "conversation.ep3_etyyy_ziven_tissak.branchId");
                chat.chat(npc, player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            if (ep3_etyyy_ziven_tissak_condition__defaultCondition(player, npc))
            {
                ep3_etyyy_ziven_tissak_action_giveRevengeOne(player, npc);
                string_id message = new string_id(c_stringFile, "s_761");
                utils.removeScriptVar(player, "conversation.ep3_etyyy_ziven_tissak.branchId");
                chat.chat(npc, player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_759"))
        {
            if (ep3_etyyy_ziven_tissak_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_762");
                utils.removeScriptVar(player, "conversation.ep3_etyyy_ziven_tissak.branchId");
                chat.chat(npc, player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_251"))
        {
            if (ep3_etyyy_ziven_tissak_condition_killedSilkthrowerPlusAll(player, npc))
            {
                ep3_etyyy_ziven_tissak_action_silkthrowerRewardPlusAll(player, npc);
                string_id message = new string_id(c_stringFile, "s_1763");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (ep3_etyyy_ziven_tissak_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_1765");
                    }
                    utils.setScriptVar(player, "conversation.ep3_etyyy_ziven_tissak.branchId", 41);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.ep3_etyyy_ziven_tissak.branchId");
                    chat.chat(npc, player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
            if (ep3_etyyy_ziven_tissak_condition__defaultCondition(player, npc))
            {
                ep3_etyyy_ziven_tissak_action_silkthrowerReward(player, npc);
                string_id message = new string_id(c_stringFile, "s_1769");
                utils.removeScriptVar(player, "conversation.ep3_etyyy_ziven_tissak.branchId");
                chat.chat(npc, player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int ep3_etyyy_ziven_tissak_handleBranch16(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_578"))
        {
            if (ep3_etyyy_ziven_tissak_condition_alreadyHasSpaceMission(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_747");
                utils.removeScriptVar(player, "conversation.ep3_etyyy_ziven_tissak.branchId");
                chat.chat(npc, player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            if (ep3_etyyy_ziven_tissak_condition__defaultCondition(player, npc))
            {
                ep3_etyyy_ziven_tissak_action_giveFordanShipRescue(player, npc);
                string_id message = new string_id(c_stringFile, "s_580");
                utils.removeScriptVar(player, "conversation.ep3_etyyy_ziven_tissak.branchId");
                chat.chat(npc, player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_582"))
        {
            if (ep3_etyyy_ziven_tissak_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_584");
                utils.removeScriptVar(player, "conversation.ep3_etyyy_ziven_tissak.branchId");
                chat.chat(npc, player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_249"))
        {
            if (ep3_etyyy_ziven_tissak_condition_killedSilkthrowerPlusAll(player, npc))
            {
                ep3_etyyy_ziven_tissak_action_silkthrowerRewardPlusAll(player, npc);
                string_id message = new string_id(c_stringFile, "s_1763");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (ep3_etyyy_ziven_tissak_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_1765");
                    }
                    utils.setScriptVar(player, "conversation.ep3_etyyy_ziven_tissak.branchId", 41);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.ep3_etyyy_ziven_tissak.branchId");
                    chat.chat(npc, player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
            if (ep3_etyyy_ziven_tissak_condition__defaultCondition(player, npc))
            {
                ep3_etyyy_ziven_tissak_action_silkthrowerReward(player, npc);
                string_id message = new string_id(c_stringFile, "s_1769");
                utils.removeScriptVar(player, "conversation.ep3_etyyy_ziven_tissak.branchId");
                chat.chat(npc, player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int ep3_etyyy_ziven_tissak_handleBranch20(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_247"))
        {
            if (ep3_etyyy_ziven_tissak_condition_killedSilkthrowerPlusAll(player, npc))
            {
                ep3_etyyy_ziven_tissak_action_silkthrowerRewardPlusAll(player, npc);
                string_id message = new string_id(c_stringFile, "s_1763");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (ep3_etyyy_ziven_tissak_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_1765");
                    }
                    utils.setScriptVar(player, "conversation.ep3_etyyy_ziven_tissak.branchId", 41);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.ep3_etyyy_ziven_tissak.branchId");
                    chat.chat(npc, player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
            if (ep3_etyyy_ziven_tissak_condition__defaultCondition(player, npc))
            {
                ep3_etyyy_ziven_tissak_action_silkthrowerReward(player, npc);
                string_id message = new string_id(c_stringFile, "s_1769");
                utils.removeScriptVar(player, "conversation.ep3_etyyy_ziven_tissak.branchId");
                chat.chat(npc, player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int ep3_etyyy_ziven_tissak_handleBranch21(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_571"))
        {
            if (ep3_etyyy_ziven_tissak_condition_alreadyHasSpaceMission(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_745");
                utils.removeScriptVar(player, "conversation.ep3_etyyy_ziven_tissak.branchId");
                chat.chat(npc, player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            if (ep3_etyyy_ziven_tissak_condition__defaultCondition(player, npc))
            {
                ep3_etyyy_ziven_tissak_action_giveFordanShipRescue(player, npc);
                string_id message = new string_id(c_stringFile, "s_573");
                utils.removeScriptVar(player, "conversation.ep3_etyyy_ziven_tissak.branchId");
                chat.chat(npc, player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_572"))
        {
            if (ep3_etyyy_ziven_tissak_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_574");
                utils.removeScriptVar(player, "conversation.ep3_etyyy_ziven_tissak.branchId");
                chat.chat(npc, player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_245"))
        {
            if (ep3_etyyy_ziven_tissak_condition_killedSilkthrowerPlusAll(player, npc))
            {
                ep3_etyyy_ziven_tissak_action_silkthrowerRewardPlusAll(player, npc);
                string_id message = new string_id(c_stringFile, "s_1763");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (ep3_etyyy_ziven_tissak_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_1765");
                    }
                    utils.setScriptVar(player, "conversation.ep3_etyyy_ziven_tissak.branchId", 41);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.ep3_etyyy_ziven_tissak.branchId");
                    chat.chat(npc, player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
            if (ep3_etyyy_ziven_tissak_condition__defaultCondition(player, npc))
            {
                ep3_etyyy_ziven_tissak_action_silkthrowerReward(player, npc);
                string_id message = new string_id(c_stringFile, "s_1769");
                utils.removeScriptVar(player, "conversation.ep3_etyyy_ziven_tissak.branchId");
                chat.chat(npc, player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int ep3_etyyy_ziven_tissak_handleBranch25(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_1701"))
        {
            if (ep3_etyyy_ziven_tissak_condition_killedSilkthrowerPlusAll(player, npc))
            {
                ep3_etyyy_ziven_tissak_action_silkthrowerRewardPlusAll(player, npc);
                string_id message = new string_id(c_stringFile, "s_1763");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (ep3_etyyy_ziven_tissak_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_1765");
                    }
                    utils.setScriptVar(player, "conversation.ep3_etyyy_ziven_tissak.branchId", 41);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.ep3_etyyy_ziven_tissak.branchId");
                    chat.chat(npc, player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
            if (ep3_etyyy_ziven_tissak_condition__defaultCondition(player, npc))
            {
                ep3_etyyy_ziven_tissak_action_silkthrowerReward(player, npc);
                string_id message = new string_id(c_stringFile, "s_1769");
                utils.removeScriptVar(player, "conversation.ep3_etyyy_ziven_tissak.branchId");
                chat.chat(npc, player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int ep3_etyyy_ziven_tissak_handleBranch26(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_1705"))
        {
            if (ep3_etyyy_ziven_tissak_condition__defaultCondition(player, npc))
            {
                ep3_etyyy_ziven_tissak_action_speakWithSordaan(player, npc);
                string_id message = new string_id(c_stringFile, "s_1707");
                utils.removeScriptVar(player, "conversation.ep3_etyyy_ziven_tissak.branchId");
                chat.chat(npc, player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_1709"))
        {
            if (ep3_etyyy_ziven_tissak_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_1711");
                utils.removeScriptVar(player, "conversation.ep3_etyyy_ziven_tissak.branchId");
                chat.chat(npc, player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_1713"))
        {
            if (ep3_etyyy_ziven_tissak_condition_killedSilkthrowerPlusAll(player, npc))
            {
                ep3_etyyy_ziven_tissak_action_silkthrowerRewardPlusAll(player, npc);
                string_id message = new string_id(c_stringFile, "s_1763");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (ep3_etyyy_ziven_tissak_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_1765");
                    }
                    utils.setScriptVar(player, "conversation.ep3_etyyy_ziven_tissak.branchId", 41);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.ep3_etyyy_ziven_tissak.branchId");
                    chat.chat(npc, player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
            if (ep3_etyyy_ziven_tissak_condition__defaultCondition(player, npc))
            {
                ep3_etyyy_ziven_tissak_action_silkthrowerReward(player, npc);
                string_id message = new string_id(c_stringFile, "s_1769");
                utils.removeScriptVar(player, "conversation.ep3_etyyy_ziven_tissak.branchId");
                chat.chat(npc, player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int ep3_etyyy_ziven_tissak_handleBranch29(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_1717"))
        {
            if (ep3_etyyy_ziven_tissak_condition_killedSilkthrowerPlusAll(player, npc))
            {
                ep3_etyyy_ziven_tissak_action_silkthrowerRewardPlusAll(player, npc);
                string_id message = new string_id(c_stringFile, "s_1763");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (ep3_etyyy_ziven_tissak_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_1765");
                    }
                    utils.setScriptVar(player, "conversation.ep3_etyyy_ziven_tissak.branchId", 41);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.ep3_etyyy_ziven_tissak.branchId");
                    chat.chat(npc, player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
            if (ep3_etyyy_ziven_tissak_condition__defaultCondition(player, npc))
            {
                ep3_etyyy_ziven_tissak_action_silkthrowerReward(player, npc);
                string_id message = new string_id(c_stringFile, "s_1769");
                utils.removeScriptVar(player, "conversation.ep3_etyyy_ziven_tissak.branchId");
                chat.chat(npc, player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int ep3_etyyy_ziven_tissak_handleBranch30(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_1721"))
        {
            if (ep3_etyyy_ziven_tissak_condition__defaultCondition(player, npc))
            {
                ep3_etyyy_ziven_tissak_action_collectWeaverEyes(player, npc);
                string_id message = new string_id(c_stringFile, "s_1723");
                utils.removeScriptVar(player, "conversation.ep3_etyyy_ziven_tissak.branchId");
                chat.chat(npc, player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_1725"))
        {
            if (ep3_etyyy_ziven_tissak_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_1727");
                utils.removeScriptVar(player, "conversation.ep3_etyyy_ziven_tissak.branchId");
                chat.chat(npc, player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_1729"))
        {
            if (ep3_etyyy_ziven_tissak_condition_killedSilkthrowerPlusAll(player, npc))
            {
                ep3_etyyy_ziven_tissak_action_silkthrowerRewardPlusAll(player, npc);
                string_id message = new string_id(c_stringFile, "s_1763");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (ep3_etyyy_ziven_tissak_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_1765");
                    }
                    utils.setScriptVar(player, "conversation.ep3_etyyy_ziven_tissak.branchId", 41);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.ep3_etyyy_ziven_tissak.branchId");
                    chat.chat(npc, player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
            if (ep3_etyyy_ziven_tissak_condition__defaultCondition(player, npc))
            {
                ep3_etyyy_ziven_tissak_action_silkthrowerReward(player, npc);
                string_id message = new string_id(c_stringFile, "s_1769");
                utils.removeScriptVar(player, "conversation.ep3_etyyy_ziven_tissak.branchId");
                chat.chat(npc, player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int ep3_etyyy_ziven_tissak_handleBranch33(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_1733"))
        {
            if (ep3_etyyy_ziven_tissak_condition_killedSilkthrowerPlusAll(player, npc))
            {
                ep3_etyyy_ziven_tissak_action_silkthrowerRewardPlusAll(player, npc);
                string_id message = new string_id(c_stringFile, "s_1763");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (ep3_etyyy_ziven_tissak_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_1765");
                    }
                    utils.setScriptVar(player, "conversation.ep3_etyyy_ziven_tissak.branchId", 41);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.ep3_etyyy_ziven_tissak.branchId");
                    chat.chat(npc, player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
            if (ep3_etyyy_ziven_tissak_condition__defaultCondition(player, npc))
            {
                ep3_etyyy_ziven_tissak_action_silkthrowerReward(player, npc);
                string_id message = new string_id(c_stringFile, "s_1769");
                utils.removeScriptVar(player, "conversation.ep3_etyyy_ziven_tissak.branchId");
                chat.chat(npc, player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int ep3_etyyy_ziven_tissak_handleBranch34(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_1737"))
        {
            if (ep3_etyyy_ziven_tissak_condition__defaultCondition(player, npc))
            {
                ep3_etyyy_ziven_tissak_action_collectWeaverFangs(player, npc);
                string_id message = new string_id(c_stringFile, "s_1739");
                utils.removeScriptVar(player, "conversation.ep3_etyyy_ziven_tissak.branchId");
                chat.chat(npc, player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_1741"))
        {
            if (ep3_etyyy_ziven_tissak_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_1743");
                utils.removeScriptVar(player, "conversation.ep3_etyyy_ziven_tissak.branchId");
                chat.chat(npc, player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_1745"))
        {
            if (ep3_etyyy_ziven_tissak_condition_killedSilkthrowerPlusAll(player, npc))
            {
                ep3_etyyy_ziven_tissak_action_silkthrowerRewardPlusAll(player, npc);
                string_id message = new string_id(c_stringFile, "s_1763");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (ep3_etyyy_ziven_tissak_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_1765");
                    }
                    utils.setScriptVar(player, "conversation.ep3_etyyy_ziven_tissak.branchId", 41);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.ep3_etyyy_ziven_tissak.branchId");
                    chat.chat(npc, player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
            if (ep3_etyyy_ziven_tissak_condition__defaultCondition(player, npc))
            {
                ep3_etyyy_ziven_tissak_action_silkthrowerReward(player, npc);
                string_id message = new string_id(c_stringFile, "s_1769");
                utils.removeScriptVar(player, "conversation.ep3_etyyy_ziven_tissak.branchId");
                chat.chat(npc, player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int ep3_etyyy_ziven_tissak_handleBranch37(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_1749"))
        {
            if (ep3_etyyy_ziven_tissak_condition__defaultCondition(player, npc))
            {
                ep3_etyyy_ziven_tissak_action_sendToTuwezz(player, npc);
                string_id message = new string_id(c_stringFile, "s_1751");
                utils.removeScriptVar(player, "conversation.ep3_etyyy_ziven_tissak.branchId");
                chat.chat(npc, player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_1753"))
        {
            if (ep3_etyyy_ziven_tissak_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_1755");
                utils.removeScriptVar(player, "conversation.ep3_etyyy_ziven_tissak.branchId");
                chat.chat(npc, player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_1757"))
        {
            if (ep3_etyyy_ziven_tissak_condition_killedSilkthrowerPlusAll(player, npc))
            {
                ep3_etyyy_ziven_tissak_action_silkthrowerRewardPlusAll(player, npc);
                string_id message = new string_id(c_stringFile, "s_1763");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (ep3_etyyy_ziven_tissak_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_1765");
                    }
                    utils.setScriptVar(player, "conversation.ep3_etyyy_ziven_tissak.branchId", 41);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.ep3_etyyy_ziven_tissak.branchId");
                    chat.chat(npc, player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
            if (ep3_etyyy_ziven_tissak_condition__defaultCondition(player, npc))
            {
                ep3_etyyy_ziven_tissak_action_silkthrowerReward(player, npc);
                string_id message = new string_id(c_stringFile, "s_1769");
                utils.removeScriptVar(player, "conversation.ep3_etyyy_ziven_tissak.branchId");
                chat.chat(npc, player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int ep3_etyyy_ziven_tissak_handleBranch40(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_1761"))
        {
            if (ep3_etyyy_ziven_tissak_condition_killedSilkthrowerPlusAll(player, npc))
            {
                ep3_etyyy_ziven_tissak_action_silkthrowerRewardPlusAll(player, npc);
                string_id message = new string_id(c_stringFile, "s_1763");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (ep3_etyyy_ziven_tissak_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_1765");
                    }
                    utils.setScriptVar(player, "conversation.ep3_etyyy_ziven_tissak.branchId", 41);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.ep3_etyyy_ziven_tissak.branchId");
                    chat.chat(npc, player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
            if (ep3_etyyy_ziven_tissak_condition__defaultCondition(player, npc))
            {
                ep3_etyyy_ziven_tissak_action_silkthrowerReward(player, npc);
                string_id message = new string_id(c_stringFile, "s_1769");
                utils.removeScriptVar(player, "conversation.ep3_etyyy_ziven_tissak.branchId");
                chat.chat(npc, player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int ep3_etyyy_ziven_tissak_handleBranch41(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_1765"))
        {
            if (ep3_etyyy_ziven_tissak_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_1767");
                utils.removeScriptVar(player, "conversation.ep3_etyyy_ziven_tissak.branchId");
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
            detachScript(self, "conversation.ep3_etyyy_ziven_tissak");
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
        detachScript(self, "conversation.ep3_etyyy_ziven_tissak");
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
        if (ep3_etyyy_ziven_tissak_condition_revengeTwoCompleted(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_882");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (ep3_etyyy_ziven_tissak_condition_killedSilkthrower(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_263");
                }
                utils.setScriptVar(player, "conversation.ep3_etyyy_ziven_tissak.branchId", 1);
                npcStartConversation(player, npc, "ep3_etyyy_ziven_tissak", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (ep3_etyyy_ziven_tissak_condition_revengeTwoFailed(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_883");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (ep3_etyyy_ziven_tissak_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            boolean hasResponse1 = false;
            if (ep3_etyyy_ziven_tissak_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse1 = true;
            }
            boolean hasResponse2 = false;
            if (ep3_etyyy_ziven_tissak_condition_killedSilkthrower(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_884");
                }
                if (hasResponse1)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_886");
                }
                if (hasResponse2)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_261");
                }
                utils.setScriptVar(player, "conversation.ep3_etyyy_ziven_tissak.branchId", 2);
                npcStartConversation(player, npc, "ep3_etyyy_ziven_tissak", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (ep3_etyyy_ziven_tissak_condition_revengeTwoUnderway(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_763");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (ep3_etyyy_ziven_tissak_condition_killedSilkthrower(player, npc))
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
                utils.setScriptVar(player, "conversation.ep3_etyyy_ziven_tissak.branchId", 4);
                npcStartConversation(player, npc, "ep3_etyyy_ziven_tissak", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (ep3_etyyy_ziven_tissak_condition_revengeOneComplete(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_764");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (ep3_etyyy_ziven_tissak_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            boolean hasResponse1 = false;
            if (ep3_etyyy_ziven_tissak_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse1 = true;
            }
            boolean hasResponse2 = false;
            if (ep3_etyyy_ziven_tissak_condition_killedSilkthrower(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_766");
                }
                if (hasResponse1)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_772");
                }
                if (hasResponse2)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_257");
                }
                utils.setScriptVar(player, "conversation.ep3_etyyy_ziven_tissak.branchId", 5);
                npcStartConversation(player, npc, "ep3_etyyy_ziven_tissak", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (ep3_etyyy_ziven_tissak_condition_revengeOneFailed(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_875");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (ep3_etyyy_ziven_tissak_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            boolean hasResponse1 = false;
            if (ep3_etyyy_ziven_tissak_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse1 = true;
            }
            boolean hasResponse2 = false;
            if (ep3_etyyy_ziven_tissak_condition_killedSilkthrower(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_876");
                }
                if (hasResponse1)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_879");
                }
                if (hasResponse2)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_255");
                }
                utils.setScriptVar(player, "conversation.ep3_etyyy_ziven_tissak.branchId", 9);
                npcStartConversation(player, npc, "ep3_etyyy_ziven_tissak", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (ep3_etyyy_ziven_tissak_condition_revengeOneUnderway(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_765");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (ep3_etyyy_ziven_tissak_condition_killedSilkthrower(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_253");
                }
                utils.setScriptVar(player, "conversation.ep3_etyyy_ziven_tissak.branchId", 11);
                npcStartConversation(player, npc, "ep3_etyyy_ziven_tissak", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (ep3_etyyy_ziven_tissak_condition_fordanShipRescueWon(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_576");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (ep3_etyyy_ziven_tissak_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            boolean hasResponse1 = false;
            if (ep3_etyyy_ziven_tissak_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse1 = true;
            }
            boolean hasResponse2 = false;
            if (ep3_etyyy_ziven_tissak_condition_killedSilkthrower(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_758");
                }
                if (hasResponse1)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_759");
                }
                if (hasResponse2)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_251");
                }
                utils.setScriptVar(player, "conversation.ep3_etyyy_ziven_tissak.branchId", 12);
                npcStartConversation(player, npc, "ep3_etyyy_ziven_tissak", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (ep3_etyyy_ziven_tissak_condition_fordanShipRescueFailed(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_575");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (ep3_etyyy_ziven_tissak_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            boolean hasResponse1 = false;
            if (ep3_etyyy_ziven_tissak_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse1 = true;
            }
            boolean hasResponse2 = false;
            if (ep3_etyyy_ziven_tissak_condition_killedSilkthrower(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_578");
                }
                if (hasResponse1)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_582");
                }
                if (hasResponse2)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_249");
                }
                utils.setScriptVar(player, "conversation.ep3_etyyy_ziven_tissak.branchId", 16);
                npcStartConversation(player, npc, "ep3_etyyy_ziven_tissak", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (ep3_etyyy_ziven_tissak_condition_isRescuingFordansShip(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_748");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (ep3_etyyy_ziven_tissak_condition_killedSilkthrower(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_247");
                }
                utils.setScriptVar(player, "conversation.ep3_etyyy_ziven_tissak.branchId", 20);
                npcStartConversation(player, npc, "ep3_etyyy_ziven_tissak", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (ep3_etyyy_ziven_tissak_condition_startSpaceMissions(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_366");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (ep3_etyyy_ziven_tissak_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            boolean hasResponse1 = false;
            if (ep3_etyyy_ziven_tissak_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse1 = true;
            }
            boolean hasResponse2 = false;
            if (ep3_etyyy_ziven_tissak_condition_killedSilkthrower(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_571");
                }
                if (hasResponse1)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_572");
                }
                if (hasResponse2)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_245");
                }
                utils.setScriptVar(player, "conversation.ep3_etyyy_ziven_tissak.branchId", 21);
                npcStartConversation(player, npc, "ep3_etyyy_ziven_tissak", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (ep3_etyyy_ziven_tissak_condition_hasCompletedZivenHunts(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_1699");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (ep3_etyyy_ziven_tissak_condition_killedSilkthrower(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_1701");
                }
                utils.setScriptVar(player, "conversation.ep3_etyyy_ziven_tissak.branchId", 25);
                npcStartConversation(player, npc, "ep3_etyyy_ziven_tissak", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (ep3_etyyy_ziven_tissak_condition_finishedWeaverEyes(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_1703");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (ep3_etyyy_ziven_tissak_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            boolean hasResponse1 = false;
            if (ep3_etyyy_ziven_tissak_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse1 = true;
            }
            boolean hasResponse2 = false;
            if (ep3_etyyy_ziven_tissak_condition_killedSilkthrower(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_1705");
                }
                if (hasResponse1)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_1709");
                }
                if (hasResponse2)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_1713");
                }
                utils.setScriptVar(player, "conversation.ep3_etyyy_ziven_tissak.branchId", 26);
                npcStartConversation(player, npc, "ep3_etyyy_ziven_tissak", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (ep3_etyyy_ziven_tissak_condition_isCollectingWeaverEyes(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_1715");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (ep3_etyyy_ziven_tissak_condition_killedSilkthrower(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_1717");
                }
                utils.setScriptVar(player, "conversation.ep3_etyyy_ziven_tissak.branchId", 29);
                npcStartConversation(player, npc, "ep3_etyyy_ziven_tissak", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (ep3_etyyy_ziven_tissak_condition_finishedWeaverFangs(player, npc))
        {
            ep3_etyyy_ziven_tissak_action_finishedCollectingWeaverFangs(player, npc);
            string_id message = new string_id(c_stringFile, "s_1719");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (ep3_etyyy_ziven_tissak_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            boolean hasResponse1 = false;
            if (ep3_etyyy_ziven_tissak_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse1 = true;
            }
            boolean hasResponse2 = false;
            if (ep3_etyyy_ziven_tissak_condition_killedSilkthrower(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_1721");
                }
                if (hasResponse1)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_1725");
                }
                if (hasResponse2)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_1729");
                }
                utils.setScriptVar(player, "conversation.ep3_etyyy_ziven_tissak.branchId", 30);
                npcStartConversation(player, npc, "ep3_etyyy_ziven_tissak", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (ep3_etyyy_ziven_tissak_condition_isCollectingWeaverFangs(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_1731");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (ep3_etyyy_ziven_tissak_condition_killedSilkthrower(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_1733");
                }
                utils.setScriptVar(player, "conversation.ep3_etyyy_ziven_tissak.branchId", 33);
                npcStartConversation(player, npc, "ep3_etyyy_ziven_tissak", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (ep3_etyyy_ziven_tissak_condition_speakWithZiven(player, npc))
        {
            doAnimationAction(npc, "greet");
            string_id message = new string_id(c_stringFile, "s_1735");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (ep3_etyyy_ziven_tissak_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            boolean hasResponse1 = false;
            if (ep3_etyyy_ziven_tissak_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse1 = true;
            }
            boolean hasResponse2 = false;
            if (ep3_etyyy_ziven_tissak_condition_killedSilkthrower(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_1737");
                }
                if (hasResponse1)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_1741");
                }
                if (hasResponse2)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_1745");
                }
                utils.setScriptVar(player, "conversation.ep3_etyyy_ziven_tissak.branchId", 34);
                npcStartConversation(player, npc, "ep3_etyyy_ziven_tissak", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (ep3_etyyy_ziven_tissak_condition_newToEtyyy(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_1747");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (ep3_etyyy_ziven_tissak_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            boolean hasResponse1 = false;
            if (ep3_etyyy_ziven_tissak_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse1 = true;
            }
            boolean hasResponse2 = false;
            if (ep3_etyyy_ziven_tissak_condition_killedSilkthrower(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_1749");
                }
                if (hasResponse1)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_1753");
                }
                if (hasResponse2)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_1757");
                }
                utils.setScriptVar(player, "conversation.ep3_etyyy_ziven_tissak.branchId", 37);
                npcStartConversation(player, npc, "ep3_etyyy_ziven_tissak", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (ep3_etyyy_ziven_tissak_condition__defaultCondition(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_1759");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (ep3_etyyy_ziven_tissak_condition_killedSilkthrower(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_1761");
                }
                utils.setScriptVar(player, "conversation.ep3_etyyy_ziven_tissak.branchId", 40);
                npcStartConversation(player, npc, "ep3_etyyy_ziven_tissak", message, responses);
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
        if (!conversationId.equals("ep3_etyyy_ziven_tissak"))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id npc = self;
        int branchId = utils.getIntScriptVar(player, "conversation.ep3_etyyy_ziven_tissak.branchId");
        if (branchId == 1 && ep3_etyyy_ziven_tissak_handleBranch1(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 2 && ep3_etyyy_ziven_tissak_handleBranch2(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 4 && ep3_etyyy_ziven_tissak_handleBranch4(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 5 && ep3_etyyy_ziven_tissak_handleBranch5(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 9 && ep3_etyyy_ziven_tissak_handleBranch9(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 11 && ep3_etyyy_ziven_tissak_handleBranch11(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 12 && ep3_etyyy_ziven_tissak_handleBranch12(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 16 && ep3_etyyy_ziven_tissak_handleBranch16(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 20 && ep3_etyyy_ziven_tissak_handleBranch20(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 21 && ep3_etyyy_ziven_tissak_handleBranch21(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 25 && ep3_etyyy_ziven_tissak_handleBranch25(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 26 && ep3_etyyy_ziven_tissak_handleBranch26(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 29 && ep3_etyyy_ziven_tissak_handleBranch29(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 30 && ep3_etyyy_ziven_tissak_handleBranch30(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 33 && ep3_etyyy_ziven_tissak_handleBranch33(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 34 && ep3_etyyy_ziven_tissak_handleBranch34(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 37 && ep3_etyyy_ziven_tissak_handleBranch37(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 40 && ep3_etyyy_ziven_tissak_handleBranch40(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 41 && ep3_etyyy_ziven_tissak_handleBranch41(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        chat.chat(npc, "Error:  Fell through all branches and responses for OnNpcConversationResponse.");
        utils.removeScriptVar(player, "conversation.ep3_etyyy_ziven_tissak.branchId");
        return SCRIPT_CONTINUE;
    }
}
