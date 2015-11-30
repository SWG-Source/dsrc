package script.conversation;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.ai_lib;
import script.library.badge;
import script.library.chat;
import script.library.conversation;
import script.library.groundquests;
import script.library.space_dungeon;
import script.library.space_quest;
import script.library.utils;

public class ep3_gursan_bryes extends script.base_script
{
    public ep3_gursan_bryes()
    {
    }
    public static String c_stringFile = "conversation/ep3_gursan_bryes";
    public boolean ep3_gursan_bryes_condition__defaultCondition(obj_id player, obj_id npc) throws InterruptedException
    {
        return true;
    }
    public boolean ep3_gursan_bryes_condition_hasWonHsskasSpaceQuest(obj_id player, obj_id npc) throws InterruptedException
    {
        return space_quest.hasWonQuest(player, "space_battle", "ep3_slaver_hsskas_space_battle");
    }
    public boolean ep3_gursan_bryes_condition_isReadyForHsskasReward(obj_id player, obj_id npc) throws InterruptedException
    {
        return groundquests.isTaskActive(player, "ep3_gursan_slay_hsskas", "taskReturnHsskasVictorious");
    }
    public boolean ep3_gursan_bryes_condition_hasCompletedHsskasQuest(obj_id player, obj_id npc) throws InterruptedException
    {
        return groundquests.hasCompletedQuest(player, "ep3_gursan_slay_hsskas");
    }
    public boolean ep3_gursan_bryes_condition_isSignalQuestActive(obj_id player, obj_id npc) throws InterruptedException
    {
        return groundquests.isQuestActive(player, "ep3_kymayrr_send_cyssc_signal");
    }
    public boolean ep3_gursan_bryes_condition_readyForSlaveCamp(obj_id player, obj_id npc) throws InterruptedException
    {
        return (groundquests.isQuestActive(player, "ep3_talk_to_gursan"));
    }
    public boolean ep3_gursan_bryes_condition_hasRescuedRroot(obj_id player, obj_id npc) throws InterruptedException
    {
        return groundquests.hasCompletedQuest(player, "ep3_kymayrr_rescue_rroot");
    }
    public boolean ep3_gursan_bryes_condition_canCompleteSignalQuest(obj_id player, obj_id npc) throws InterruptedException
    {
        return (groundquests.isTaskActive(player, "ep3_kymayrr_send_cyssc_signal", "taskReturnToGursan") || groundquests.hasCompletedQuest(player, "ep3_kymayrr_send_cyssc_signal"));
    }
    public boolean ep3_gursan_bryes_condition_hasCysscSpaceSeries(obj_id player, obj_id npc) throws InterruptedException
    {
        return (space_quest.hasQuest(player, "space_battle", "ep3_slaver_khrask_space_battle") || space_quest.hasQuest(player, "assassinate", "ep3_slaver_lord_cyssc_final"));
    }
    public boolean ep3_gursan_bryes_condition_hasDefeatedCysscSpace(obj_id player, obj_id npc) throws InterruptedException
    {
        return space_quest.hasWonQuest(player, "assassinate", "ep3_slaver_lord_cyssc_final");
    }
    public boolean ep3_gursan_bryes_condition_canRepeatCyssc(obj_id player, obj_id npc) throws InterruptedException
    {
        int currentTime = getCalendarTime();
        int cysscTimer = getIntObjVar(player, "ep3.cysscTimer");
        if (cysscTimer > currentTime)
        {
            return false;
        }
        if (badge.hasBadge(player, "bdg_kash_wookiee_rage") && !space_quest.hasQuest(player, "space_battle", "ep3_slaver_khrask_space_battle_alt") && !space_quest.hasQuest(player, "assassinate", "ep3_slaver_lord_cyssc_alt"))
        {
            return true;
        }
        return false;
    }
    public boolean ep3_gursan_bryes_condition_haveRepatCysscQuest(obj_id player, obj_id npc) throws InterruptedException
    {
        if (space_quest.hasQuest(player, "space_battle", "ep3_slaver_khrask_space_battle_alt") && !space_quest.hasQuest(player, "assassinate", "ep3_slaver_lord_cyssc_alt"))
        {
            return true;
        }
        return false;
    }
    public boolean ep3_gursan_bryes_condition_haveWonRepeatCyssc(obj_id player, obj_id npc) throws InterruptedException
    {
        return space_quest.hasWonQuest(player, "assassinate", "ep3_slaver_lord_cyssc_alt");
    }
    public boolean ep3_gursan_bryes_condition_cysscTimerActive(obj_id player, obj_id npc) throws InterruptedException
    {
        int currentTime = getCalendarTime();
        int cysscTimer = getIntObjVar(player, "ep3.cysscTimer");
        if (cysscTimer > currentTime)
        {
            return true;
        }
        return false;
    }
    public void ep3_gursan_bryes_action_grantSequencerQuestAndTicket(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.grantQuest(player, "ep3_gursan_capture_sequencer_codes");
    }
    public void ep3_gursan_bryes_action_grantTrandoFighterQuest(obj_id player, obj_id npc) throws InterruptedException
    {
        space_quest.grantQuest(player, "assassinate", "ep3_slaver_trando_reinforcement_intercept");
    }
    public void ep3_gursan_bryes_action_grantSlayHsskasQuest(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.grantQuest(player, "ep3_gursan_slay_hsskas");
    }
    public void ep3_gursan_bryes_action_grantSlayHsskasReward(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.sendSignal(player, "signalReturnHsskasVictorious");
    }
    public void ep3_gursan_bryes_action_grantKymayrrBrief(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.grantQuest(player, "ep3_gursan_kyamyrr_brief");
    }
    public void ep3_gursan_bryes_action_grantEntryQuest(obj_id player, obj_id npc) throws InterruptedException
    {
        if (!groundquests.hasCompletedQuest(player, "ep3_slaver_gursan_entry_quest"))
        {
            groundquests.grantQuest(player, "ep3_slaver_gursan_entry_quest");
        }
    }
    public void ep3_gursan_bryes_action_finishSignalQeust(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.sendSignal(player, "signalReturnToGursan");
    }
    public void ep3_gursan_bryes_action_grantCysscSpaceSeries(obj_id player, obj_id npc) throws InterruptedException
    {
        space_quest.grantQuest(player, "space_battle", "ep3_slaver_khrask_space_battle");
    }
    public void ep3_gursan_bryes_action_grantRepeatCyssc(obj_id player, obj_id npc) throws InterruptedException
    {
        space_quest.grantQuest(player, "space_battle", "ep3_slaver_khrask_space_battle_alt");
        int currentTime = getCalendarTime();
        int timeUntilReset = secondsUntilNextDailyTime(8, 0, 0);
        int nextReset = timeUntilReset + currentTime;
        setObjVar(player, "ep3.cysscTimer", nextReset);
    }
    public void ep3_gursan_bryes_action_giveRepeatCysscReward(obj_id player, obj_id npc) throws InterruptedException
    {
        if (space_quest.hasWonQuest(player, "space_battle", "ep3_slaver_khrask_space_battle_alt"))
        {
            space_quest.clearQuestFlags(player, "space_battle", "ep3_slaver_khrask_space_battle_alt");
        }
        if (space_quest.hasWonQuest(player, "assassinate", "ep3_slaver_lord_cyssc_alt"))
        {
            space_quest.clearQuestFlags(player, "assassinate", "ep3_slaver_lord_cyssc_alt");
        }
        obj_id pInv = utils.getInventoryContainer(player);
        int reward = rand(1, 4);
        String rewardString = "object/tangible/ship/components/weapon/wpn_ionic_pulse_weapon.iff";
        if (reward == 1)
        {
            createObject(rewardString, pInv, "");
            sendSystemMessage(player, "You have received an Ionic Pulse Weapon", null);
        }
        if (reward == 2)
        {
            rewardString = "object/tangible/ship/components/weapon/wpn_vengeance_ion_cannon.iff";
            createObject(rewardString, pInv, "");
            sendSystemMessage(player, "You have received a Vengeance Ion Cannon", null);
        }
        if (reward == 3)
        {
            rewardString = "object/tangible/ship/components/weapon/wpn_vulcan_cannon.iff";
            createObject(rewardString, pInv, "");
            sendSystemMessage(player, "You have received an Incom 'Trandoshan Special' Repeater Cannon", null);
        }
        if (reward == 4)
        {
            rewardString = "object/tangible/ship/components/weapon/wpn_heavy_ionic_pulse_weapon.iff";
            createObject(rewardString, pInv, "");
            sendSystemMessage(player, "You have received a Heavy Ionic Pulse Weapon", null);
        }
    }
    public String ep3_gursan_bryes_tokenTO_timeLeft(obj_id player, obj_id npc) throws InterruptedException
    {
        int cysscTimer = getIntObjVar(player, "ep3.cysscTimer");
        int currentTime = getCalendarTime();
        String ep3_gursan_bryes_tokenTO_timeLeft = "return in " + utils.formatTimeVerbose(cysscTimer - currentTime);
        return ep3_gursan_bryes_tokenTO_timeLeft;
    }
    public int ep3_gursan_bryes_handleBranch1(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_44"))
        {
            if (ep3_gursan_bryes_condition__defaultCondition(player, npc))
            {
                ep3_gursan_bryes_action_giveRepeatCysscReward(player, npc);
                string_id message = new string_id(c_stringFile, "s_45");
                utils.removeScriptVar(player, "conversation.ep3_gursan_bryes.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int ep3_gursan_bryes_handleBranch4(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_39"))
        {
            if (ep3_gursan_bryes_condition__defaultCondition(player, npc))
            {
                ep3_gursan_bryes_action_grantRepeatCyssc(player, npc);
                string_id message = new string_id(c_stringFile, "s_40");
                utils.removeScriptVar(player, "conversation.ep3_gursan_bryes.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_41"))
        {
            if (ep3_gursan_bryes_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_42");
                utils.removeScriptVar(player, "conversation.ep3_gursan_bryes.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int ep3_gursan_bryes_handleBranch7(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_804"))
        {
            if (ep3_gursan_bryes_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_806");
                utils.removeScriptVar(player, "conversation.ep3_gursan_bryes.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int ep3_gursan_bryes_handleBranch10(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_812"))
        {
            if (ep3_gursan_bryes_condition__defaultCondition(player, npc))
            {
                ep3_gursan_bryes_action_grantCysscSpaceSeries(player, npc);
                string_id message = new string_id(c_stringFile, "s_814");
                utils.removeScriptVar(player, "conversation.ep3_gursan_bryes.branchId");
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
    public int ep3_gursan_bryes_handleBranch12(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_818"))
        {
            if (ep3_gursan_bryes_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_820");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (ep3_gursan_bryes_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_822");
                    }
                    utils.setScriptVar(player, "conversation.ep3_gursan_bryes.branchId", 13);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.ep3_gursan_bryes.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int ep3_gursan_bryes_handleBranch13(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_822"))
        {
            if (ep3_gursan_bryes_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_824");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (ep3_gursan_bryes_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_826");
                    }
                    utils.setScriptVar(player, "conversation.ep3_gursan_bryes.branchId", 14);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.ep3_gursan_bryes.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int ep3_gursan_bryes_handleBranch14(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_826"))
        {
            if (ep3_gursan_bryes_condition__defaultCondition(player, npc))
            {
                ep3_gursan_bryes_action_grantEntryQuest(player, npc);
                string_id message = new string_id(c_stringFile, "s_828");
                utils.removeScriptVar(player, "conversation.ep3_gursan_bryes.branchId");
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
    public int ep3_gursan_bryes_handleBranch16(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_832"))
        {
            if (ep3_gursan_bryes_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_834");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (ep3_gursan_bryes_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_836");
                    }
                    utils.setScriptVar(player, "conversation.ep3_gursan_bryes.branchId", 17);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.ep3_gursan_bryes.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int ep3_gursan_bryes_handleBranch17(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_836"))
        {
            if (ep3_gursan_bryes_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_838");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (ep3_gursan_bryes_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_840");
                    }
                    utils.setScriptVar(player, "conversation.ep3_gursan_bryes.branchId", 18);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.ep3_gursan_bryes.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int ep3_gursan_bryes_handleBranch18(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_840"))
        {
            if (ep3_gursan_bryes_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_842");
                utils.removeScriptVar(player, "conversation.ep3_gursan_bryes.branchId");
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
    public int ep3_gursan_bryes_handleBranch20(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_846"))
        {
            if (ep3_gursan_bryes_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_848");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (ep3_gursan_bryes_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_850");
                    }
                    utils.setScriptVar(player, "conversation.ep3_gursan_bryes.branchId", 21);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.ep3_gursan_bryes.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int ep3_gursan_bryes_handleBranch21(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_850"))
        {
            if (ep3_gursan_bryes_condition_haveWonRepeatCyssc(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_43");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (ep3_gursan_bryes_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_44");
                    }
                    utils.setScriptVar(player, "conversation.ep3_gursan_bryes.branchId", 1);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.ep3_gursan_bryes.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
            if (ep3_gursan_bryes_condition_cysscTimerActive(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_54");
                utils.removeScriptVar(player, "conversation.ep3_gursan_bryes.branchId");
                prose_package pp = new prose_package();
                pp.stringId = message;
                pp.actor.set(player);
                pp.target.set(npc);
                pp.other.set(ep3_gursan_bryes_tokenTO_timeLeft(player, npc));
                npcEndConversationWithMessage(player, pp);
                return SCRIPT_CONTINUE;
            }
            if (ep3_gursan_bryes_condition_canRepeatCyssc(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_38");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (ep3_gursan_bryes_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (ep3_gursan_bryes_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_39");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_41");
                    }
                    utils.setScriptVar(player, "conversation.ep3_gursan_bryes.branchId", 4);
                    prose_package pp = new prose_package();
                    pp.stringId = message;
                    pp.actor.set(player);
                    pp.target.set(npc);
                    npcSpeak(player, pp);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.ep3_gursan_bryes.branchId");
                    prose_package pp = new prose_package();
                    pp.stringId = message;
                    pp.actor.set(player);
                    pp.target.set(npc);
                    npcEndConversationWithMessage(player, pp);
                }
                return SCRIPT_CONTINUE;
            }
            if (ep3_gursan_bryes_condition_hasDefeatedCysscSpace(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_802");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (ep3_gursan_bryes_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_804");
                    }
                    utils.setScriptVar(player, "conversation.ep3_gursan_bryes.branchId", 7);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.ep3_gursan_bryes.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
            if (ep3_gursan_bryes_condition_hasCysscSpaceSeries(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_808");
                utils.removeScriptVar(player, "conversation.ep3_gursan_bryes.branchId");
                prose_package pp = new prose_package();
                pp.stringId = message;
                pp.actor.set(player);
                pp.target.set(npc);
                npcEndConversationWithMessage(player, pp);
                return SCRIPT_CONTINUE;
            }
            if (ep3_gursan_bryes_condition_canCompleteSignalQuest(player, npc))
            {
                ep3_gursan_bryes_action_finishSignalQeust(player, npc);
                string_id message = new string_id(c_stringFile, "s_810");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (ep3_gursan_bryes_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_812");
                    }
                    utils.setScriptVar(player, "conversation.ep3_gursan_bryes.branchId", 10);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.ep3_gursan_bryes.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
            if (ep3_gursan_bryes_condition_isSignalQuestActive(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_816");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (ep3_gursan_bryes_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_818");
                    }
                    utils.setScriptVar(player, "conversation.ep3_gursan_bryes.branchId", 12);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.ep3_gursan_bryes.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
            if (ep3_gursan_bryes_condition_hasCompletedHsskasQuest(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_830");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (ep3_gursan_bryes_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_832");
                    }
                    utils.setScriptVar(player, "conversation.ep3_gursan_bryes.branchId", 16);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.ep3_gursan_bryes.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
            if (ep3_gursan_bryes_condition_isReadyForHsskasReward(player, npc))
            {
                ep3_gursan_bryes_action_grantSlayHsskasReward(player, npc);
                string_id message = new string_id(c_stringFile, "s_844");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (ep3_gursan_bryes_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_846");
                    }
                    utils.setScriptVar(player, "conversation.ep3_gursan_bryes.branchId", 20);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.ep3_gursan_bryes.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
            if (ep3_gursan_bryes_condition_hasWonHsskasSpaceQuest(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_852");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (ep3_gursan_bryes_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_854");
                    }
                    utils.setScriptVar(player, "conversation.ep3_gursan_bryes.branchId", 22);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.ep3_gursan_bryes.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
            if (ep3_gursan_bryes_condition_hasRescuedRroot(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_858");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (ep3_gursan_bryes_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_860");
                    }
                    utils.setScriptVar(player, "conversation.ep3_gursan_bryes.branchId", 24);
                    prose_package pp = new prose_package();
                    pp.stringId = message;
                    pp.actor.set(player);
                    pp.target.set(npc);
                    npcSpeak(player, pp);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.ep3_gursan_bryes.branchId");
                    prose_package pp = new prose_package();
                    pp.stringId = message;
                    pp.actor.set(player);
                    pp.target.set(npc);
                    npcEndConversationWithMessage(player, pp);
                }
                return SCRIPT_CONTINUE;
            }
            if (ep3_gursan_bryes_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_872");
                utils.removeScriptVar(player, "conversation.ep3_gursan_bryes.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int ep3_gursan_bryes_handleBranch22(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_854"))
        {
            if (ep3_gursan_bryes_condition__defaultCondition(player, npc))
            {
                ep3_gursan_bryes_action_grantSlayHsskasQuest(player, npc);
                string_id message = new string_id(c_stringFile, "s_856");
                utils.removeScriptVar(player, "conversation.ep3_gursan_bryes.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int ep3_gursan_bryes_handleBranch24(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_860"))
        {
            if (ep3_gursan_bryes_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_862");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (ep3_gursan_bryes_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_864");
                    }
                    utils.setScriptVar(player, "conversation.ep3_gursan_bryes.branchId", 25);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.ep3_gursan_bryes.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int ep3_gursan_bryes_handleBranch25(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_864"))
        {
            if (ep3_gursan_bryes_condition__defaultCondition(player, npc))
            {
                ep3_gursan_bryes_action_grantTrandoFighterQuest(player, npc);
                string_id message = new string_id(c_stringFile, "s_866");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (ep3_gursan_bryes_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_868");
                    }
                    utils.setScriptVar(player, "conversation.ep3_gursan_bryes.branchId", 26);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.ep3_gursan_bryes.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int ep3_gursan_bryes_handleBranch26(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_868"))
        {
            if (ep3_gursan_bryes_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_870");
                utils.removeScriptVar(player, "conversation.ep3_gursan_bryes.branchId");
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
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        if ((!isMob(self)) || (isPlayer(self)))
        {
            detachScript(self, "conversation.ep3_gursan_bryes");
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
        detachScript(self, "conversation.ep3_gursan_bryes");
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
        if (ep3_gursan_bryes_condition_haveWonRepeatCyssc(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_43");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (ep3_gursan_bryes_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_44");
                }
                utils.setScriptVar(player, "conversation.ep3_gursan_bryes.branchId", 1);
                npcStartConversation(player, npc, "ep3_gursan_bryes", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (ep3_gursan_bryes_condition_cysscTimerActive(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_54");
            prose_package pp = new prose_package();
            pp.stringId = message;
            pp.actor.set(player);
            pp.target.set(npc);
            pp.other.set(ep3_gursan_bryes_tokenTO_timeLeft(player, npc));
            chat.chat(npc, player, null, null, pp);
            return SCRIPT_CONTINUE;
        }
        if (ep3_gursan_bryes_condition_canRepeatCyssc(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_38");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (ep3_gursan_bryes_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            boolean hasResponse1 = false;
            if (ep3_gursan_bryes_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_39");
                }
                if (hasResponse1)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_41");
                }
                utils.setScriptVar(player, "conversation.ep3_gursan_bryes.branchId", 4);
                prose_package pp = new prose_package();
                pp.stringId = message;
                pp.actor.set(player);
                pp.target.set(npc);
                npcStartConversation(player, npc, "ep3_gursan_bryes", null, pp, responses);
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
        if (ep3_gursan_bryes_condition_hasDefeatedCysscSpace(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_802");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (ep3_gursan_bryes_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_804");
                }
                utils.setScriptVar(player, "conversation.ep3_gursan_bryes.branchId", 7);
                npcStartConversation(player, npc, "ep3_gursan_bryes", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (ep3_gursan_bryes_condition_hasCysscSpaceSeries(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_808");
            prose_package pp = new prose_package();
            pp.stringId = message;
            pp.actor.set(player);
            pp.target.set(npc);
            chat.chat(npc, player, null, null, pp);
            return SCRIPT_CONTINUE;
        }
        if (ep3_gursan_bryes_condition_canCompleteSignalQuest(player, npc))
        {
            ep3_gursan_bryes_action_finishSignalQeust(player, npc);
            string_id message = new string_id(c_stringFile, "s_810");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (ep3_gursan_bryes_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_812");
                }
                utils.setScriptVar(player, "conversation.ep3_gursan_bryes.branchId", 10);
                npcStartConversation(player, npc, "ep3_gursan_bryes", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (ep3_gursan_bryes_condition_isSignalQuestActive(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_816");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (ep3_gursan_bryes_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_818");
                }
                utils.setScriptVar(player, "conversation.ep3_gursan_bryes.branchId", 12);
                npcStartConversation(player, npc, "ep3_gursan_bryes", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (ep3_gursan_bryes_condition_hasCompletedHsskasQuest(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_830");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (ep3_gursan_bryes_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_832");
                }
                utils.setScriptVar(player, "conversation.ep3_gursan_bryes.branchId", 16);
                npcStartConversation(player, npc, "ep3_gursan_bryes", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (ep3_gursan_bryes_condition_isReadyForHsskasReward(player, npc))
        {
            ep3_gursan_bryes_action_grantSlayHsskasReward(player, npc);
            string_id message = new string_id(c_stringFile, "s_844");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (ep3_gursan_bryes_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_846");
                }
                utils.setScriptVar(player, "conversation.ep3_gursan_bryes.branchId", 20);
                npcStartConversation(player, npc, "ep3_gursan_bryes", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (ep3_gursan_bryes_condition_hasWonHsskasSpaceQuest(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_852");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (ep3_gursan_bryes_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_854");
                }
                utils.setScriptVar(player, "conversation.ep3_gursan_bryes.branchId", 22);
                npcStartConversation(player, npc, "ep3_gursan_bryes", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (ep3_gursan_bryes_condition_hasRescuedRroot(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_858");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (ep3_gursan_bryes_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_860");
                }
                utils.setScriptVar(player, "conversation.ep3_gursan_bryes.branchId", 24);
                prose_package pp = new prose_package();
                pp.stringId = message;
                pp.actor.set(player);
                pp.target.set(npc);
                npcStartConversation(player, npc, "ep3_gursan_bryes", null, pp, responses);
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
        if (ep3_gursan_bryes_condition__defaultCondition(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_872");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        chat.chat(npc, "Error:  All conditions for OnStartNpcConversation were false.");
        return SCRIPT_CONTINUE;
    }
    public int OnNpcConversationResponse(obj_id self, String conversationId, obj_id player, string_id response) throws InterruptedException
    {
        if (!conversationId.equals("ep3_gursan_bryes"))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id npc = self;
        int branchId = utils.getIntScriptVar(player, "conversation.ep3_gursan_bryes.branchId");
        if (branchId == 1 && ep3_gursan_bryes_handleBranch1(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 4 && ep3_gursan_bryes_handleBranch4(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 7 && ep3_gursan_bryes_handleBranch7(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 10 && ep3_gursan_bryes_handleBranch10(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 12 && ep3_gursan_bryes_handleBranch12(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 13 && ep3_gursan_bryes_handleBranch13(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 14 && ep3_gursan_bryes_handleBranch14(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 16 && ep3_gursan_bryes_handleBranch16(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 17 && ep3_gursan_bryes_handleBranch17(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 18 && ep3_gursan_bryes_handleBranch18(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 20 && ep3_gursan_bryes_handleBranch20(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 21 && ep3_gursan_bryes_handleBranch21(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 22 && ep3_gursan_bryes_handleBranch22(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 24 && ep3_gursan_bryes_handleBranch24(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 25 && ep3_gursan_bryes_handleBranch25(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 26 && ep3_gursan_bryes_handleBranch26(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        chat.chat(npc, "Error:  Fell through all branches and responses for OnNpcConversationResponse.");
        utils.removeScriptVar(player, "conversation.ep3_gursan_bryes.branchId");
        return SCRIPT_CONTINUE;
    }
}
