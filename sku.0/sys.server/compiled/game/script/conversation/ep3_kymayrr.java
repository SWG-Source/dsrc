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
import script.library.groundquests;
import script.library.space_quest;
import script.library.utils;

public class ep3_kymayrr extends script.base_script
{
    public ep3_kymayrr()
    {
    }
    public static String c_stringFile = "conversation/ep3_kymayrr";
    public boolean ep3_kymayrr_condition__defaultCondition(obj_id player, obj_id npc) throws InterruptedException
    {
        return true;
    }
    public boolean ep3_kymayrr_condition_hasWonTrandoRadio(obj_id player, obj_id npc) throws InterruptedException
    {
        return groundquests.hasCompletedQuest(player, "ep3_zhailaut_recover_trando_radio");
    }
    public boolean ep3_kymayrr_condition_hasWonAdjutantRecovery(obj_id player, obj_id npc) throws InterruptedException
    {
        return space_quest.hasWonQuest(player, "recovery", "ep3_slaver_rhosk_recovery");
    }
    public boolean ep3_kymayrr_condition_hasRescuedRroot(obj_id player, obj_id npc) throws InterruptedException
    {
        return groundquests.isTaskActive(player, "ep3_kymayrr_rescue_rroot", "taskReturnToKymayrr");
    }
    public boolean ep3_kymayrr_condition_hasCompletedRescue(obj_id player, obj_id npc) throws InterruptedException
    {
        return groundquests.hasCompletedQuest(player, "ep3_kymayrr_rescue_rroot");
    }
    public boolean ep3_kymayrr_condition_hasSlainHsskas(obj_id player, obj_id npc) throws InterruptedException
    {
        return groundquests.hasCompletedQuest(player, "ep3_gursan_slay_hsskas");
    }
    public boolean ep3_kymayrr_condition_hasCysscSignalQuest(obj_id player, obj_id npc) throws InterruptedException
    {
        return groundquests.isQuestActive(player, "ep3_kymayrr_send_cyssc_signal");
    }
    public boolean ep3_kymayrr_condition_canCompleteSignalQuest(obj_id player, obj_id npc) throws InterruptedException
    {
        return (groundquests.isTaskActive(player, "ep3_kymayrr_send_cyssc_signal", "taskReturnToGursan") || groundquests.hasCompletedQuest(player, "ep3_kymayrr_send_cyssc_signal"));
    }
    public boolean ep3_kymayrr_condition_hasDefeatedCysscSpace(obj_id player, obj_id npc) throws InterruptedException
    {
        return space_quest.hasWonQuest(player, "assassinate", "ep3_slaver_lord_cyssc_final");
    }
    public boolean ep3_kymayrr_condition_isWookieeHero(obj_id player, obj_id npc) throws InterruptedException
    {
        return badge.hasBadge(player, "bdg_kash_wookiee_rage");
    }
    public boolean ep3_kymayrr_condition_canDoWookieeJabba(obj_id player, obj_id npc) throws InterruptedException
    {
        return utils.canSpeakWookiee(player, npc);
    }
    public void ep3_kymayrr_action_grantAdjutantRhoskMission(obj_id player, obj_id npc) throws InterruptedException
    {
        space_quest.grantQuest(player, "space_battle", "ep3_slaver_rhosk_space_battle");
    }
    public void ep3_kymayrr_action_grantRescueRrootMission(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.grantQuest(player, "ep3_kymayrr_rescue_rroot");
    }
    public void ep3_kymayrr_action_rewardRrootRescue(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.sendSignal(player, "signalReturnToKymayrr");
    }
    public void ep3_kymayrr_action_grantSendCysscSignalQuest(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.grantQuest(player, "ep3_kymayrr_send_cyssc_signal");
    }
    public void ep3_kymayrr_action_giveWookieeRageMedal(obj_id player, obj_id npc) throws InterruptedException
    {
        if (!badge.hasBadge(player, "bdg_kash_wookiee_rage"))
        {
            badge.grantBadge(player, "bdg_kash_wookiee_rage");
        }
        if (getGender(player) == GENDER_MALE)
        {
            if (getSpecies(player) == SPECIES_ITHORIAN)
            {
                createObjectInInventoryAllowOverload("object/tangible/wearables/necklace/ith_necklace_wookiee_freedom_m.iff", player);
            }
            else if (getSpecies(player) == SPECIES_WOOKIEE)
            {
                createObjectInInventoryAllowOverload("object/tangible/wearables/necklace/necklace_wookiee_freedom_wke_.iff", player);
            }
            else 
            {
                createObjectInInventoryAllowOverload("object/tangible/wearables/necklace/necklace_wookiee_freedom_m.iff", player);
            }
        }
        else 
        {
            if (getSpecies(player) == SPECIES_ITHORIAN)
            {
                createObjectInInventoryAllowOverload("object/tangible/wearables/necklace/ith_necklace_wookiee_freedom_f.iff", player);
            }
            else if (getSpecies(player) == SPECIES_WOOKIEE)
            {
                createObjectInInventoryAllowOverload("object/tangible/wearables/necklace/necklace_wookiee_freedom_wke_f.iff", player);
            }
            else 
            {
                createObjectInInventoryAllowOverload("object/tangible/wearables/necklace/necklace_wookiee_freedom_f.iff", player);
            }
        }
    }
    public void ep3_kymayrr_action_doDunnoEmote(obj_id player, obj_id npc) throws InterruptedException
    {
        utils.emoteWookieeConfusion(player, npc);
    }
    public int ep3_kymayrr_handleBranch2(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_279"))
        {
            if (ep3_kymayrr_condition__defaultCondition(player, npc))
            {
                ep3_kymayrr_action_giveWookieeRageMedal(player, npc);
                string_id message = new string_id(c_stringFile, "s_281");
                utils.removeScriptVar(player, "conversation.ep3_kymayrr.branchId");
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
    public int ep3_kymayrr_handleBranch5(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_287"))
        {
            if (ep3_kymayrr_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_289");
                utils.removeScriptVar(player, "conversation.ep3_kymayrr.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int ep3_kymayrr_handleBranch8(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_295"))
        {
            if (ep3_kymayrr_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_297");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (ep3_kymayrr_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_299");
                    }
                    utils.setScriptVar(player, "conversation.ep3_kymayrr.branchId", 9);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.ep3_kymayrr.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int ep3_kymayrr_handleBranch9(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_299"))
        {
            if (ep3_kymayrr_condition__defaultCondition(player, npc))
            {
                ep3_kymayrr_action_grantSendCysscSignalQuest(player, npc);
                string_id message = new string_id(c_stringFile, "s_301");
                utils.removeScriptVar(player, "conversation.ep3_kymayrr.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int ep3_kymayrr_handleBranch11(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_305"))
        {
            if (ep3_kymayrr_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_307");
                utils.removeScriptVar(player, "conversation.ep3_kymayrr.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int ep3_kymayrr_handleBranch13(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_311"))
        {
            if (ep3_kymayrr_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_313");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (ep3_kymayrr_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_315");
                    }
                    utils.setScriptVar(player, "conversation.ep3_kymayrr.branchId", 14);
                    prose_package pp = new prose_package();
                    pp.stringId = message;
                    pp.actor.set(player);
                    pp.target.set(npc);
                    npcSpeak(player, pp);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.ep3_kymayrr.branchId");
                    prose_package pp = new prose_package();
                    pp.stringId = message;
                    pp.actor.set(player);
                    pp.target.set(npc);
                    npcEndConversationWithMessage(player, pp);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int ep3_kymayrr_handleBranch14(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_315"))
        {
            if (ep3_kymayrr_condition__defaultCondition(player, npc))
            {
                ep3_kymayrr_action_rewardRrootRescue(player, npc);
                string_id message = new string_id(c_stringFile, "s_317");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (ep3_kymayrr_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_319");
                    }
                    utils.setScriptVar(player, "conversation.ep3_kymayrr.branchId", 15);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.ep3_kymayrr.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int ep3_kymayrr_handleBranch15(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_319"))
        {
            if (ep3_kymayrr_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_321");
                utils.removeScriptVar(player, "conversation.ep3_kymayrr.branchId");
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
    public int ep3_kymayrr_handleBranch17(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_326"))
        {
            if (ep3_kymayrr_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_330");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (ep3_kymayrr_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (ep3_kymayrr_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_332");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_340");
                    }
                    utils.setScriptVar(player, "conversation.ep3_kymayrr.branchId", 18);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.ep3_kymayrr.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int ep3_kymayrr_handleBranch18(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_332"))
        {
            if (ep3_kymayrr_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_334");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (ep3_kymayrr_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_336");
                    }
                    utils.setScriptVar(player, "conversation.ep3_kymayrr.branchId", 19);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.ep3_kymayrr.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_340"))
        {
            if (ep3_kymayrr_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_342");
                utils.removeScriptVar(player, "conversation.ep3_kymayrr.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int ep3_kymayrr_handleBranch19(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_336"))
        {
            if (ep3_kymayrr_condition__defaultCondition(player, npc))
            {
                ep3_kymayrr_action_grantRescueRrootMission(player, npc);
                string_id message = new string_id(c_stringFile, "s_338");
                utils.removeScriptVar(player, "conversation.ep3_kymayrr.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int ep3_kymayrr_handleBranch22(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_346"))
        {
            if (ep3_kymayrr_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_348");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (ep3_kymayrr_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_350");
                    }
                    utils.setScriptVar(player, "conversation.ep3_kymayrr.branchId", 23);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.ep3_kymayrr.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_378"))
        {
            if (ep3_kymayrr_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_380");
                utils.removeScriptVar(player, "conversation.ep3_kymayrr.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int ep3_kymayrr_handleBranch23(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_350"))
        {
            if (ep3_kymayrr_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_352");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (ep3_kymayrr_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_354");
                    }
                    utils.setScriptVar(player, "conversation.ep3_kymayrr.branchId", 24);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.ep3_kymayrr.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int ep3_kymayrr_handleBranch24(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_354"))
        {
            if (ep3_kymayrr_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_356");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (ep3_kymayrr_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_358");
                    }
                    utils.setScriptVar(player, "conversation.ep3_kymayrr.branchId", 25);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.ep3_kymayrr.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int ep3_kymayrr_handleBranch25(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_358"))
        {
            if (ep3_kymayrr_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_360");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (ep3_kymayrr_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_362");
                    }
                    utils.setScriptVar(player, "conversation.ep3_kymayrr.branchId", 26);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.ep3_kymayrr.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int ep3_kymayrr_handleBranch26(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_362"))
        {
            if (ep3_kymayrr_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_364");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (ep3_kymayrr_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_366");
                    }
                    utils.setScriptVar(player, "conversation.ep3_kymayrr.branchId", 27);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.ep3_kymayrr.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int ep3_kymayrr_handleBranch27(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_366"))
        {
            if (ep3_kymayrr_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_368");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (ep3_kymayrr_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_370");
                    }
                    utils.setScriptVar(player, "conversation.ep3_kymayrr.branchId", 28);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.ep3_kymayrr.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int ep3_kymayrr_handleBranch28(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_370"))
        {
            if (ep3_kymayrr_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_372");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (ep3_kymayrr_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_374");
                    }
                    utils.setScriptVar(player, "conversation.ep3_kymayrr.branchId", 29);
                    prose_package pp = new prose_package();
                    pp.stringId = message;
                    pp.actor.set(player);
                    pp.target.set(npc);
                    npcSpeak(player, pp);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.ep3_kymayrr.branchId");
                    prose_package pp = new prose_package();
                    pp.stringId = message;
                    pp.actor.set(player);
                    pp.target.set(npc);
                    npcEndConversationWithMessage(player, pp);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int ep3_kymayrr_handleBranch29(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_374"))
        {
            if (ep3_kymayrr_condition__defaultCondition(player, npc))
            {
                ep3_kymayrr_action_grantAdjutantRhoskMission(player, npc);
                string_id message = new string_id(c_stringFile, "s_376");
                utils.removeScriptVar(player, "conversation.ep3_kymayrr.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        if ((!isMob(self)) || (isPlayer(self)))
        {
            detachScript(self, "conversation.ep3_kymayrr");
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
        detachScript(self, "conversation.ep3_kymayrr");
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
        if (ep3_kymayrr_condition_isWookieeHero(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_275");
            prose_package pp = new prose_package();
            pp.stringId = message;
            pp.actor.set(player);
            pp.target.set(npc);
            chat.chat(npc, player, null, null, pp);
            return SCRIPT_CONTINUE;
        }
        if (ep3_kymayrr_condition_hasDefeatedCysscSpace(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_277");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (ep3_kymayrr_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_279");
                }
                utils.setScriptVar(player, "conversation.ep3_kymayrr.branchId", 2);
                prose_package pp = new prose_package();
                pp.stringId = message;
                pp.actor.set(player);
                pp.target.set(npc);
                npcStartConversation(player, npc, "ep3_kymayrr", null, pp, responses);
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
        if (ep3_kymayrr_condition_canDoWookieeJabba(player, npc))
        {
            ep3_kymayrr_action_doDunnoEmote(player, npc);
            string_id message = new string_id(c_stringFile, "s_283");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        if (ep3_kymayrr_condition_canCompleteSignalQuest(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_285");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (ep3_kymayrr_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_287");
                }
                utils.setScriptVar(player, "conversation.ep3_kymayrr.branchId", 5);
                npcStartConversation(player, npc, "ep3_kymayrr", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (ep3_kymayrr_condition_hasCysscSignalQuest(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_291");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        if (ep3_kymayrr_condition_hasSlainHsskas(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_293");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (ep3_kymayrr_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_295");
                }
                utils.setScriptVar(player, "conversation.ep3_kymayrr.branchId", 8);
                prose_package pp = new prose_package();
                pp.stringId = message;
                pp.actor.set(player);
                pp.target.set(npc);
                npcStartConversation(player, npc, "ep3_kymayrr", null, pp, responses);
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
        if (ep3_kymayrr_condition_hasCompletedRescue(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_303");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (ep3_kymayrr_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_305");
                }
                utils.setScriptVar(player, "conversation.ep3_kymayrr.branchId", 11);
                npcStartConversation(player, npc, "ep3_kymayrr", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (ep3_kymayrr_condition_hasRescuedRroot(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_309");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (ep3_kymayrr_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_311");
                }
                utils.setScriptVar(player, "conversation.ep3_kymayrr.branchId", 13);
                npcStartConversation(player, npc, "ep3_kymayrr", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (ep3_kymayrr_condition_hasWonAdjutantRecovery(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_324");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (ep3_kymayrr_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_326");
                }
                utils.setScriptVar(player, "conversation.ep3_kymayrr.branchId", 17);
                prose_package pp = new prose_package();
                pp.stringId = message;
                pp.actor.set(player);
                pp.target.set(npc);
                npcStartConversation(player, npc, "ep3_kymayrr", null, pp, responses);
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
        if (ep3_kymayrr_condition_hasWonTrandoRadio(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_344");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (ep3_kymayrr_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            boolean hasResponse1 = false;
            if (ep3_kymayrr_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_346");
                }
                if (hasResponse1)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_378");
                }
                utils.setScriptVar(player, "conversation.ep3_kymayrr.branchId", 22);
                prose_package pp = new prose_package();
                pp.stringId = message;
                pp.actor.set(player);
                pp.target.set(npc);
                npcStartConversation(player, npc, "ep3_kymayrr", null, pp, responses);
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
        if (ep3_kymayrr_condition__defaultCondition(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_382");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        chat.chat(npc, "Error:  All conditions for OnStartNpcConversation were false.");
        return SCRIPT_CONTINUE;
    }
    public int OnNpcConversationResponse(obj_id self, String conversationId, obj_id player, string_id response) throws InterruptedException
    {
        if (!conversationId.equals("ep3_kymayrr"))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id npc = self;
        int branchId = utils.getIntScriptVar(player, "conversation.ep3_kymayrr.branchId");
        if (branchId == 2 && ep3_kymayrr_handleBranch2(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 5 && ep3_kymayrr_handleBranch5(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 8 && ep3_kymayrr_handleBranch8(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 9 && ep3_kymayrr_handleBranch9(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 11 && ep3_kymayrr_handleBranch11(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 13 && ep3_kymayrr_handleBranch13(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 14 && ep3_kymayrr_handleBranch14(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 15 && ep3_kymayrr_handleBranch15(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 17 && ep3_kymayrr_handleBranch17(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 18 && ep3_kymayrr_handleBranch18(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 19 && ep3_kymayrr_handleBranch19(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 22 && ep3_kymayrr_handleBranch22(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 23 && ep3_kymayrr_handleBranch23(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 24 && ep3_kymayrr_handleBranch24(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 25 && ep3_kymayrr_handleBranch25(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 26 && ep3_kymayrr_handleBranch26(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 27 && ep3_kymayrr_handleBranch27(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 28 && ep3_kymayrr_handleBranch28(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 29 && ep3_kymayrr_handleBranch29(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        chat.chat(npc, "Error:  Fell through all branches and responses for OnNpcConversationResponse.");
        utils.removeScriptVar(player, "conversation.ep3_kymayrr.branchId");
        return SCRIPT_CONTINUE;
    }
}
