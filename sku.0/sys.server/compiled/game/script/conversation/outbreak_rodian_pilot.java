package script.conversation;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.ai_lib;
import script.library.buff;
import script.library.chat;
import script.library.conversation;
import script.library.features;
import script.library.groundquests;
import script.library.money;
import script.library.space_quest;
import script.library.utils;

public class outbreak_rodian_pilot extends script.base_script
{
    public outbreak_rodian_pilot()
    {
    }
    public static String c_stringFile = "conversation/outbreak_rodian_pilot";
    public boolean outbreak_rodian_pilot_condition__defaultCondition(obj_id player, obj_id npc) throws InterruptedException
    {
        return true;
    }
    public boolean outbreak_rodian_pilot_condition_hasCollectionSlotAlpha(obj_id player, obj_id npc) throws InterruptedException
    {
        return hasCompletedCollectionSlot(player, "exp_icon_deathtroopers_camp_alpha");
    }
    public boolean outbreak_rodian_pilot_condition_has100Credits(obj_id player, obj_id npc) throws InterruptedException
    {
        return money.hasFunds(player, money.MT_TOTAL, 100);
    }
    public boolean outbreak_rodian_pilot_condition_hasCollectionSlotEpsilon(obj_id player, obj_id npc) throws InterruptedException
    {
        return hasCompletedCollectionSlot(player, "exp_icon_deathtroopers_camp_epsilon");
    }
    public boolean outbreak_rodian_pilot_condition_hasCollectionSlotDelta(obj_id player, obj_id npc) throws InterruptedException
    {
        return hasCompletedCollectionSlot(player, "exp_icon_deathtroopers_camp_delta");
    }
    public boolean outbreak_rodian_pilot_condition_hasCollectionSlotBeta(obj_id player, obj_id npc) throws InterruptedException
    {
        return hasCompletedCollectionSlot(player, "exp_icon_deathtroopers_camp_beta");
    }
    public boolean outbreak_rodian_pilot_condition_hasCollectionSlotGamma(obj_id player, obj_id npc) throws InterruptedException
    {
        return hasCompletedCollectionSlot(player, "exp_icon_deathtroopers_camp_gamma");
    }
    public boolean outbreak_rodian_pilot_condition_has500Credits(obj_id player, obj_id npc) throws InterruptedException
    {
        return money.hasFunds(player, money.MT_TOTAL, 500);
    }
    public boolean outbreak_rodian_pilot_condition_has1000Credits(obj_id player, obj_id npc) throws InterruptedException
    {
        return money.hasFunds(player, money.MT_TOTAL, 1000);
    }
    public boolean outbreak_rodian_pilot_condition_has1500Credits(obj_id player, obj_id npc) throws InterruptedException
    {
        return money.hasFunds(player, money.MT_TOTAL, 1500);
    }
    public boolean outbreak_rodian_pilot_condition_has2000Credits(obj_id player, obj_id npc) throws InterruptedException
    {
        return money.hasFunds(player, money.MT_TOTAL, 2000);
    }
    public boolean outbreak_rodian_pilot_condition_hasFrequentFlyerBadge(obj_id player, obj_id npc) throws InterruptedException
    {
        return hasCompletedCollection(player, "deathtrooper_rodian_frequent_flyer");
    }
    public boolean outbreak_rodian_pilot_condition_isEligibleForTrans(obj_id player, obj_id npc) throws InterruptedException
    {
        return outbreak_rodian_pilot_condition_hasCollectionSlotAlpha(player, npc) && (groundquests.hasCompletedQuest(player, "outbreak_quest_01_imperial") || groundquests.hasCompletedQuest(player, "outbreak_quest_01_rebel") || groundquests.hasCompletedQuest(player, "outbreak_quest_01_neutral")) || isGod(player);
    }
    public void outbreak_rodian_pilot_action_dropOffAlphaForCredits(obj_id player, obj_id npc) throws InterruptedException
    {
        if (money.requestPayment(player, npc, 100, "pass_fail", null, true))
        {
            if (!hasCompletedCollection(player, "deathtrooper_rodian_frequent_flyer"))
            {
                modifyCollectionSlotValue(player, "outbreak_frequent_flyer_counter", 1);
            }
            outbreak_rodian_pilot_action_applyBuffs(player, npc);
            warpPlayer(player, "dathomir", -5925, 559, -6667, null, -5925, 559, -6667, null, true);
        }
    }
    public void outbreak_rodian_pilot_action_dropOffBetaForCredits(obj_id player, obj_id npc) throws InterruptedException
    {
        if (money.requestPayment(player, npc, 500, "pass_fail", null, true))
        {
            if (!hasCompletedCollection(player, "deathtrooper_rodian_frequent_flyer"))
            {
                modifyCollectionSlotValue(player, "outbreak_frequent_flyer_counter", 1);
            }
            outbreak_rodian_pilot_action_applyBuffs(player, npc);
            warpPlayer(player, "dathomir", -6283, 561, -7521, null, -6283, 561, -7521, null, true);
        }
    }
    public void outbreak_rodian_pilot_action_dropOffGammaForCredits(obj_id player, obj_id npc) throws InterruptedException
    {
        if (money.requestPayment(player, npc, 1000, "pass_fail", null, true))
        {
            if (!hasCompletedCollection(player, "deathtrooper_rodian_frequent_flyer"))
            {
                modifyCollectionSlotValue(player, "outbreak_frequent_flyer_counter", 1);
            }
            outbreak_rodian_pilot_action_applyBuffs(player, npc);
            warpPlayer(player, "dathomir", -6824, 553, -6456, null, -6824, 553, -6456, null, true);
        }
    }
    public void outbreak_rodian_pilot_action_dropOffDeltaForCredits(obj_id player, obj_id npc) throws InterruptedException
    {
        if (money.requestPayment(player, npc, 1500, "pass_fail", null, true))
        {
            if (!hasCompletedCollection(player, "deathtrooper_rodian_frequent_flyer"))
            {
                modifyCollectionSlotValue(player, "outbreak_frequent_flyer_counter", 1);
            }
            outbreak_rodian_pilot_action_applyBuffs(player, npc);
            warpPlayer(player, "dathomir", -7145, 562, -6922, null, -7145, 562, -6922, null, true);
        }
    }
    public void outbreak_rodian_pilot_action_dropOffEpsilonForCredits(obj_id player, obj_id npc) throws InterruptedException
    {
        if (money.requestPayment(player, npc, 2000, "pass_fail", null, true))
        {
            if (!hasCompletedCollection(player, "deathtrooper_rodian_frequent_flyer"))
            {
                modifyCollectionSlotValue(player, "outbreak_frequent_flyer_counter", 1);
            }
            outbreak_rodian_pilot_action_applyBuffs(player, npc);
            warpPlayer(player, "dathomir", -7448, 570, -7342, null, -7448, 570, -7342, null, true);
        }
    }
    public void outbreak_rodian_pilot_action_AlphaFree(obj_id player, obj_id npc) throws InterruptedException
    {
        outbreak_rodian_pilot_action_applyBuffs(player, npc);
        warpPlayer(player, "dathomir", -5925, 559, -6667, null, -5925, 559, -6667, null, true);
    }
    public void outbreak_rodian_pilot_action_EpsilonFree(obj_id player, obj_id npc) throws InterruptedException
    {
        outbreak_rodian_pilot_action_applyBuffs(player, npc);
        warpPlayer(player, "dathomir", -7448, 570, -7342, null, -7448, 570, -7342, null, true);
    }
    public void outbreak_rodian_pilot_action_DeltaFree(obj_id player, obj_id npc) throws InterruptedException
    {
        outbreak_rodian_pilot_action_applyBuffs(player, npc);
        warpPlayer(player, "dathomir", -7145, 562, -6922, null, -7145, 562, -6922, null, true);
    }
    public void outbreak_rodian_pilot_action_GammaFree(obj_id player, obj_id npc) throws InterruptedException
    {
        outbreak_rodian_pilot_action_applyBuffs(player, npc);
        warpPlayer(player, "dathomir", -6824, 553, -6456, null, -6824, 553, -6456, null, true);
    }
    public void outbreak_rodian_pilot_action_BetaFree(obj_id player, obj_id npc) throws InterruptedException
    {
        outbreak_rodian_pilot_action_applyBuffs(player, npc);
        warpPlayer(player, "dathomir", -6283, 561, -7521, null, -6283, 561, -7521, null, true);
    }
    public void outbreak_rodian_pilot_action_applyBuffs(obj_id player, obj_id npc) throws InterruptedException
    {
        messageTo(player, "death_troopers_apply_virus", null, 10.0f, false);
        setObjVar(player, "outbreak.usedGate", 1);
        buff.applyBuff(player, "death_troopers_no_vehicle");
    }
    public int outbreak_rodian_pilot_handleBranch1(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_49"))
        {
            if (outbreak_rodian_pilot_condition__defaultCondition(player, npc))
            {
                outbreak_rodian_pilot_action_AlphaFree(player, npc);
                string_id message = new string_id(c_stringFile, "s_50");
                utils.removeScriptVar(player, "conversation.outbreak_rodian_pilot.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_51"))
        {
            if (outbreak_rodian_pilot_condition__defaultCondition(player, npc))
            {
                outbreak_rodian_pilot_action_BetaFree(player, npc);
                string_id message = new string_id(c_stringFile, "s_56");
                utils.removeScriptVar(player, "conversation.outbreak_rodian_pilot.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_52"))
        {
            if (outbreak_rodian_pilot_condition__defaultCondition(player, npc))
            {
                outbreak_rodian_pilot_action_GammaFree(player, npc);
                string_id message = new string_id(c_stringFile, "s_57");
                utils.removeScriptVar(player, "conversation.outbreak_rodian_pilot.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_54"))
        {
            if (outbreak_rodian_pilot_condition__defaultCondition(player, npc))
            {
                outbreak_rodian_pilot_action_DeltaFree(player, npc);
                string_id message = new string_id(c_stringFile, "s_58");
                utils.removeScriptVar(player, "conversation.outbreak_rodian_pilot.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_55"))
        {
            if (outbreak_rodian_pilot_condition__defaultCondition(player, npc))
            {
                outbreak_rodian_pilot_action_EpsilonFree(player, npc);
                string_id message = new string_id(c_stringFile, "s_59");
                utils.removeScriptVar(player, "conversation.outbreak_rodian_pilot.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int outbreak_rodian_pilot_handleBranch7(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_32"))
        {
            if (outbreak_rodian_pilot_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_33");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (outbreak_rodian_pilot_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_75");
                    }
                    utils.setScriptVar(player, "conversation.outbreak_rodian_pilot.branchId", 8);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.outbreak_rodian_pilot.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int outbreak_rodian_pilot_handleBranch8(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_75"))
        {
            if (outbreak_rodian_pilot_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_76");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (outbreak_rodian_pilot_condition_hasCollectionSlotAlpha(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (outbreak_rodian_pilot_condition_hasCollectionSlotBeta(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (outbreak_rodian_pilot_condition_hasCollectionSlotGamma(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse2 = true;
                }
                boolean hasResponse3 = false;
                if (outbreak_rodian_pilot_condition_hasCollectionSlotDelta(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse3 = true;
                }
                boolean hasResponse4 = false;
                if (outbreak_rodian_pilot_condition_hasCollectionSlotEpsilon(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse4 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_20");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_28");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_47");
                    }
                    if (hasResponse3)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_70");
                    }
                    if (hasResponse4)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_84");
                    }
                    utils.setScriptVar(player, "conversation.outbreak_rodian_pilot.branchId", 9);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.outbreak_rodian_pilot.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int outbreak_rodian_pilot_handleBranch9(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_20"))
        {
            if (outbreak_rodian_pilot_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_22");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (outbreak_rodian_pilot_condition_has100Credits(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (outbreak_rodian_pilot_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_35");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_38");
                    }
                    utils.setScriptVar(player, "conversation.outbreak_rodian_pilot.branchId", 10);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.outbreak_rodian_pilot.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_28"))
        {
            if (outbreak_rodian_pilot_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_30");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (outbreak_rodian_pilot_condition_has500Credits(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (outbreak_rodian_pilot_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_37");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_42");
                    }
                    utils.setScriptVar(player, "conversation.outbreak_rodian_pilot.branchId", 13);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.outbreak_rodian_pilot.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_47"))
        {
            if (outbreak_rodian_pilot_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_60");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (outbreak_rodian_pilot_condition_has1000Credits(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (outbreak_rodian_pilot_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_62");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_66");
                    }
                    utils.setScriptVar(player, "conversation.outbreak_rodian_pilot.branchId", 16);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.outbreak_rodian_pilot.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_70"))
        {
            if (outbreak_rodian_pilot_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_72");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (outbreak_rodian_pilot_condition_has1500Credits(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (outbreak_rodian_pilot_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_74");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_80");
                    }
                    utils.setScriptVar(player, "conversation.outbreak_rodian_pilot.branchId", 19);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.outbreak_rodian_pilot.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_84"))
        {
            if (outbreak_rodian_pilot_condition_has2000Credits(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_86");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (outbreak_rodian_pilot_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (outbreak_rodian_pilot_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_88");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_92");
                    }
                    utils.setScriptVar(player, "conversation.outbreak_rodian_pilot.branchId", 22);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.outbreak_rodian_pilot.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int outbreak_rodian_pilot_handleBranch10(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_35"))
        {
            if (outbreak_rodian_pilot_condition__defaultCondition(player, npc))
            {
                outbreak_rodian_pilot_action_dropOffAlphaForCredits(player, npc);
                string_id message = new string_id(c_stringFile, "s_36");
                utils.removeScriptVar(player, "conversation.outbreak_rodian_pilot.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_38"))
        {
            if (outbreak_rodian_pilot_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_43");
                utils.removeScriptVar(player, "conversation.outbreak_rodian_pilot.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int outbreak_rodian_pilot_handleBranch13(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_37"))
        {
            if (outbreak_rodian_pilot_condition__defaultCondition(player, npc))
            {
                outbreak_rodian_pilot_action_dropOffBetaForCredits(player, npc);
                string_id message = new string_id(c_stringFile, "s_40");
                utils.removeScriptVar(player, "conversation.outbreak_rodian_pilot.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_42"))
        {
            if (outbreak_rodian_pilot_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_45");
                utils.removeScriptVar(player, "conversation.outbreak_rodian_pilot.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int outbreak_rodian_pilot_handleBranch16(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_62"))
        {
            if (outbreak_rodian_pilot_condition__defaultCondition(player, npc))
            {
                outbreak_rodian_pilot_action_dropOffGammaForCredits(player, npc);
                string_id message = new string_id(c_stringFile, "s_64");
                utils.removeScriptVar(player, "conversation.outbreak_rodian_pilot.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_66"))
        {
            if (outbreak_rodian_pilot_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_68");
                utils.removeScriptVar(player, "conversation.outbreak_rodian_pilot.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int outbreak_rodian_pilot_handleBranch19(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_74"))
        {
            if (outbreak_rodian_pilot_condition__defaultCondition(player, npc))
            {
                outbreak_rodian_pilot_action_dropOffDeltaForCredits(player, npc);
                string_id message = new string_id(c_stringFile, "s_78");
                utils.removeScriptVar(player, "conversation.outbreak_rodian_pilot.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_80"))
        {
            if (outbreak_rodian_pilot_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_82");
                utils.removeScriptVar(player, "conversation.outbreak_rodian_pilot.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int outbreak_rodian_pilot_handleBranch22(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_88"))
        {
            if (outbreak_rodian_pilot_condition__defaultCondition(player, npc))
            {
                outbreak_rodian_pilot_action_dropOffEpsilonForCredits(player, npc);
                string_id message = new string_id(c_stringFile, "s_90");
                utils.removeScriptVar(player, "conversation.outbreak_rodian_pilot.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_92"))
        {
            if (outbreak_rodian_pilot_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_94");
                utils.removeScriptVar(player, "conversation.outbreak_rodian_pilot.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int OnInitialize(obj_id self) throws InterruptedException
    {
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
        if (outbreak_rodian_pilot_condition_hasFrequentFlyerBadge(player, npc))
        {
            doAnimationAction(npc, "bow2");
            doAnimationAction(player, "wave2");
            string_id message = new string_id(c_stringFile, "s_48");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (outbreak_rodian_pilot_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            boolean hasResponse1 = false;
            if (outbreak_rodian_pilot_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse1 = true;
            }
            boolean hasResponse2 = false;
            if (outbreak_rodian_pilot_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse2 = true;
            }
            boolean hasResponse3 = false;
            if (outbreak_rodian_pilot_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse3 = true;
            }
            boolean hasResponse4 = false;
            if (outbreak_rodian_pilot_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse4 = true;
            }
            if (hasResponse)
            {
                int responseIndex = 0;
                string_id responses[] = new string_id[numberOfResponses];
                if (hasResponse0)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_49");
                }
                if (hasResponse1)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_51");
                }
                if (hasResponse2)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_52");
                }
                if (hasResponse3)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_54");
                }
                if (hasResponse4)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_55");
                }
                utils.setScriptVar(player, "conversation.outbreak_rodian_pilot.branchId", 1);
                prose_package pp = new prose_package();
                pp.stringId = message;
                pp.actor.set(player);
                pp.target.set(npc);
                npcStartConversation(player, npc, "outbreak_rodian_pilot", null, pp, responses);
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
        if (outbreak_rodian_pilot_condition_isEligibleForTrans(player, npc))
        {
            doAnimationAction(npc, "nervous");
            string_id message = new string_id(c_stringFile, "s_31");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (outbreak_rodian_pilot_condition__defaultCondition(player, npc))
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
                utils.setScriptVar(player, "conversation.outbreak_rodian_pilot.branchId", 7);
                npcStartConversation(player, npc, "outbreak_rodian_pilot", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (outbreak_rodian_pilot_condition__defaultCondition(player, npc))
        {
            doAnimationAction(npc, "wave_on_dismissing");
            string_id message = new string_id(c_stringFile, "s_96");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        chat.chat(npc, "Error:  All conditions for OnStartNpcConversation were false.");
        return SCRIPT_CONTINUE;
    }
    public int OnNpcConversationResponse(obj_id self, String conversationId, obj_id player, string_id response) throws InterruptedException
    {
        if (!conversationId.equals("outbreak_rodian_pilot"))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id npc = self;
        int branchId = utils.getIntScriptVar(player, "conversation.outbreak_rodian_pilot.branchId");
        if (branchId == 1 && outbreak_rodian_pilot_handleBranch1(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 7 && outbreak_rodian_pilot_handleBranch7(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 8 && outbreak_rodian_pilot_handleBranch8(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 9 && outbreak_rodian_pilot_handleBranch9(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 10 && outbreak_rodian_pilot_handleBranch10(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 13 && outbreak_rodian_pilot_handleBranch13(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 16 && outbreak_rodian_pilot_handleBranch16(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 19 && outbreak_rodian_pilot_handleBranch19(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 22 && outbreak_rodian_pilot_handleBranch22(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        chat.chat(npc, "Error:  Fell through all branches and responses for OnNpcConversationResponse.");
        utils.removeScriptVar(player, "conversation.outbreak_rodian_pilot.branchId");
        return SCRIPT_CONTINUE;
    }
}
