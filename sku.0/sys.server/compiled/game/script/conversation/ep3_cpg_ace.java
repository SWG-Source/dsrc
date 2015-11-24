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
import script.library.ship_ai;
import script.library.space_combat;
import script.library.space_content;
import script.library.space_crafting;
import script.library.space_flags;
import script.library.space_quest;
import script.library.space_transition;
import script.library.utils;

public class ep3_cpg_ace extends script.base_script
{
    public ep3_cpg_ace()
    {
    }
    public static String c_stringFile = "conversation/ep3_cpg_ace";
    public boolean ep3_cpg_ace_condition__defaultCondition(obj_id player, obj_id npc) throws InterruptedException
    {
        return true;
    }
    public boolean ep3_cpg_ace_condition_canAfford50(obj_id player, obj_id npc) throws InterruptedException
    {
        return space_crafting.canAffordShipRepairs(player, npc, .50f);
    }
    public boolean ep3_cpg_ace_condition_canAfford25(obj_id player, obj_id npc) throws InterruptedException
    {
        return (space_crafting.canAffordShipRepairs(player, npc, .25f) && space_crafting.isDamaged(player));
    }
    public boolean ep3_cpg_ace_condition_canAfford75(obj_id player, obj_id npc) throws InterruptedException
    {
        return space_crafting.canAffordShipRepairs(player, npc, .75f);
    }
    public boolean ep3_cpg_ace_condition_canAfford100(obj_id player, obj_id npc) throws InterruptedException
    {
        return space_crafting.canAffordShipRepairs(player, npc, 1.0f);
    }
    public boolean ep3_cpg_ace_condition_hasWonQuest_01(obj_id player, obj_id npc) throws InterruptedException
    {
        return (space_quest.hasWonQuest(player, "escort", "ep3_kash_station_escort_alpha"));
    }
    public boolean ep3_cpg_ace_condition_hasWonQuest_02(obj_id player, obj_id npc) throws InterruptedException
    {
        return (space_quest.hasWonQuest(player, "escort", "ep3_kash_station_escort_bravo"));
    }
    public boolean ep3_cpg_ace_condition_hasWonQuest_03(obj_id player, obj_id npc) throws InterruptedException
    {
        return (space_quest.hasWonQuest(player, "rescue", "ep3_kash_station_rescue_alpha"));
    }
    public boolean ep3_cpg_ace_condition_hasWonQuest_04(obj_id player, obj_id npc) throws InterruptedException
    {
        return (space_quest.hasWonQuest(player, "rescue", "ep3_kash_station_rescue_bravo"));
    }
    public boolean ep3_cpg_ace_condition_isOnQuest_escort_alpha(obj_id player, obj_id npc) throws InterruptedException
    {
        return (space_quest.hasQuest(player, "escort", "ep3_kash_station_escort_alpha"));
    }
    public boolean ep3_cpg_ace_condition_hasFailedQuest_01(obj_id player, obj_id npc) throws InterruptedException
    {
        return (space_quest.hasFailedQuest(player, "escort", "ep3_kash_station_escort_alpha") || space_quest.hasAbortedQuest(player, "escort", "ep3_kash_station_escort_alpha"));
    }
    public boolean ep3_cpg_ace_condition_hasBeenRewarded_01(obj_id player, obj_id npc) throws InterruptedException
    {
        return space_quest.hasReceivedReward(player, "escort", "ep3_kash_station_escort_alpha");
    }
    public boolean ep3_cpg_ace_condition_hasFailedQuest_02(obj_id player, obj_id npc) throws InterruptedException
    {
        return (space_quest.hasFailedQuest(player, "escort", "ep3_kash_station_escort_bravo") || space_quest.hasAbortedQuest(player, "escort", "ep3_kash_station_escort_bravo"));
    }
    public boolean ep3_cpg_ace_condition_hasBeenRewarded_02(obj_id player, obj_id npc) throws InterruptedException
    {
        return space_quest.hasReceivedReward(player, "escort", "ep3_kash_station_escort_bravo");
    }
    public boolean ep3_cpg_ace_condition_hasWon01_not02yet(obj_id player, obj_id npc) throws InterruptedException
    {
        return (space_quest.hasWonQuest(player, "escort", "ep3_kash_station_escort_alpha") && (!space_quest.hasWonQuest(player, "escort", "ep3_kash_station_escort_bravo")));
    }
    public boolean ep3_cpg_ace_condition_hasWon02_not03yet(obj_id player, obj_id npc) throws InterruptedException
    {
        return (space_quest.hasWonQuest(player, "escort", "ep3_kash_station_escort_bravo") && (!space_quest.hasWonQuest(player, "rescue", "ep3_kash_station_rescue_alpha")));
    }
    public boolean ep3_cpg_ace_condition_hasWon03_not04yet(obj_id player, obj_id npc) throws InterruptedException
    {
        return (space_quest.hasWonQuest(player, "rescue", "ep3_kash_station_rescue_alpha") && (!space_quest.hasWonQuest(player, "rescue", "ep3_kash_station_rescue_bravo")));
    }
    public boolean ep3_cpg_ace_condition_hasDoneGround_01(obj_id player, obj_id npc) throws InterruptedException
    {
        return (groundquests.hasCompletedQuest(player, "ep3_kash_station_contact_eyma_01"));
    }
    public boolean ep3_cpg_ace_condition_hasWon_assassinations(obj_id player, obj_id npc) throws InterruptedException
    {
        return (space_quest.hasReceivedReward(player, "assassinate", "ep3_kash_station_assassinate_neutral") || space_quest.hasReceivedReward(player, "assassinate", "ep3_kash_station_assassinate_rebel") || space_quest.hasReceivedReward(player, "assassinate", "ep3_kash_station_assassinate_imperial"));
    }
    public boolean ep3_cpg_ace_condition_isOnQuest_ground_01(obj_id player, obj_id npc) throws InterruptedException
    {
        return (groundquests.isQuestActive(player, "ep3_kash_station_contact_eyma_01"));
    }
    public boolean ep3_cpg_ace_condition_isOnQuest_AMBUSH(obj_id player, obj_id npc) throws InterruptedException
    {
        return (space_quest.hasQuest(player, "destroy_surpriseattack", "ep3_kash_station_ghrag_ambush"));
    }
    public boolean ep3_cpg_ace_condition_hasFailedQuest_03(obj_id player, obj_id npc) throws InterruptedException
    {
        return (space_quest.hasFailedQuest(player, "rescue", "ep3_kash_station_rescue_alpha") || space_quest.hasAbortedQuest(player, "rescue", "ep3_kash_station_rescue_alpha"));
    }
    public boolean ep3_cpg_ace_condition_isOnQuest_ground_00(obj_id player, obj_id npc) throws InterruptedException
    {
        return (groundquests.isQuestActive(player, "ep3_kash_station_contact_eyma_00"));
    }
    public boolean ep3_cpg_ace_condition_isOnQuest_rescue_alpha(obj_id player, obj_id npc) throws InterruptedException
    {
        return (space_quest.hasQuest(player, "rescue", "ep3_kash_station_rescue_alpha"));
    }
    public boolean ep3_cpg_ace_condition_isOnQuest_escort_bravo(obj_id player, obj_id npc) throws InterruptedException
    {
        return (space_quest.hasQuest(player, "escort", "ep3_kash_station_escort_bravo"));
    }
    public boolean ep3_cpg_ace_condition_isTooFar(obj_id player, obj_id npc) throws InterruptedException
    {
        space_combat.playCombatTauntSound(player);
        obj_id containingShip = space_transition.getContainingShip(player);
        return (getDistance(npc, containingShip) > space_transition.STATION_COMM_MAX_DISTANCE);
    }
    public boolean ep3_cpg_ace_condition_hasBadge_visited_Kachirho(obj_id player, obj_id npc) throws InterruptedException
    {
        return badge.hasBadge(player, "exp_kash_kachirho_found");
    }
    public boolean ep3_cpg_ace_condition_isAttacking(obj_id player, obj_id npc) throws InterruptedException
    {
        return _spaceUnitIsAttacking(npc);
    }
    public void ep3_cpg_ace_action_land_kashyyyk(obj_id player, obj_id npc) throws InterruptedException
    {
        space_content.landPlayer(player, npc, "Kachirho Starport");
    }
    public void ep3_cpg_ace_action_fix25(obj_id player, obj_id npc) throws InterruptedException
    {
        space_crafting.doStationToShipRepairs(player, npc, .25f);
    }
    public void ep3_cpg_ace_action_fix50(obj_id player, obj_id npc) throws InterruptedException
    {
        space_crafting.doStationToShipRepairs(player, npc, .50f);
    }
    public void ep3_cpg_ace_action_fix75(obj_id player, obj_id npc) throws InterruptedException
    {
        space_crafting.doStationToShipRepairs(player, npc, .75f);
    }
    public void ep3_cpg_ace_action_fix100(obj_id player, obj_id npc) throws InterruptedException
    {
        space_crafting.doStationToShipRepairs(player, npc, 1.0f);
    }
    public void ep3_cpg_ace_action_grantQuest_01(obj_id player, obj_id npc) throws InterruptedException
    {
        space_quest.grantQuest(player, "escort", "ep3_kash_station_escort_alpha");
    }
    public void ep3_cpg_ace_action_grantQuest_02(obj_id player, obj_id npc) throws InterruptedException
    {
        space_quest.grantQuest(player, "escort", "ep3_kash_station_escort_bravo");
    }
    public void ep3_cpg_ace_action_grantQuest_03(obj_id player, obj_id npc) throws InterruptedException
    {
        space_quest.grantQuest(player, "rescue", "ep3_kash_station_rescue_alpha");
    }
    public void ep3_cpg_ace_action_grantQuest_04(obj_id player, obj_id npc) throws InterruptedException
    {
        space_quest.grantQuest(player, "rescue", "ep3_kash_station_rescue_bravo");
    }
    public void ep3_cpg_ace_action_rewardQuest_01(obj_id player, obj_id npc) throws InterruptedException
    {
        if (!space_quest.hasReceivedReward(player, "escort", "ep3_kash_station_escort_alpha"))
        {
            space_quest.giveReward(player, "escort", "ep3_kash_station_escort_alpha", 400);
        }
    }
    public void ep3_cpg_ace_action_rewardQuest_02(obj_id player, obj_id npc) throws InterruptedException
    {
        if (!space_quest.hasReceivedReward(player, "escort", "ep3_kash_station_escort_bravo"))
        {
            space_quest.giveReward(player, "escort", "ep3_kash_station_escort_bravo", 500);
        }
    }
    public void ep3_cpg_ace_action_grantDuty_escort(obj_id player, obj_id npc) throws InterruptedException
    {
        space_quest.grantQuest(player, "escort_duty", "ep3_kash_station_escort_duty");
    }
    public void ep3_cpg_ace_action_giveGroundQuest_01(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.grantQuest(player, "ep3_kash_station_contact_eyma_01");
    }
    public void ep3_cpg_ace_action_grantDuty_destroy(obj_id player, obj_id npc) throws InterruptedException
    {
        space_quest.grantQuest(player, "destroy_duty", "ep3_kash_station_destroy_duty_neutral");
    }
    public void ep3_cpg_ace_action_giveGroundQuest_00(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.grantQuest(player, "ep3_kash_station_contact_eyma_00");
    }
    public void ep3_cpg_ace_action_Land_and_GrantBadge_visited_Kachirho(obj_id player, obj_id npc) throws InterruptedException
    {
        badge.grantBadge(player, "exp_kash_kachirho_found");
        space_content.landPlayer(player, npc, "Kachirho Starport");
    }
    public int ep3_cpg_ace_tokenDI_getStationRepairCost25(obj_id player, obj_id npc) throws InterruptedException
    {
        return space_crafting.getStationRepairCost(player, npc, .25f);
    }
    public int ep3_cpg_ace_tokenDI_getStationRepairCost50(obj_id player, obj_id npc) throws InterruptedException
    {
        return space_crafting.getStationRepairCost(player, npc, .50f);
    }
    public int ep3_cpg_ace_tokenDI_getStationRepairCost75(obj_id player, obj_id npc) throws InterruptedException
    {
        return space_crafting.getStationRepairCost(player, npc, .75f);
    }
    public int ep3_cpg_ace_tokenDI_getStationRepairCost100(obj_id player, obj_id npc) throws InterruptedException
    {
        return space_crafting.getStationRepairCost(player, npc, 1.0f);
    }
    public int ep3_cpg_ace_handleBranch6(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_239"))
        {
            if (ep3_cpg_ace_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_241");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (!ep3_cpg_ace_condition_isAttacking(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_27");
                    }
                    utils.setScriptVar(player, "conversation.ep3_cpg_ace.branchId", 7);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.ep3_cpg_ace.branchId");
                    chat.chat(npc, player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int ep3_cpg_ace_handleBranch7(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_27"))
        {
            if (ep3_cpg_ace_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_28");
                utils.removeScriptVar(player, "conversation.ep3_cpg_ace.branchId");
                prose_package pp = new prose_package();
                pp.stringId = message;
                pp.actor.set(player);
                pp.target.set(npc);
                chat.chat(npc, player, pp);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int ep3_cpg_ace_handleBranch11(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_168"))
        {
            if (ep3_cpg_ace_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_169");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (!ep3_cpg_ace_condition_isAttacking(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_34");
                    }
                    utils.setScriptVar(player, "conversation.ep3_cpg_ace.branchId", 12);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.ep3_cpg_ace.branchId");
                    chat.chat(npc, player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int ep3_cpg_ace_handleBranch12(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_34"))
        {
            if (ep3_cpg_ace_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_36");
                utils.removeScriptVar(player, "conversation.ep3_cpg_ace.branchId");
                chat.chat(npc, player, message);
                npcEndConversation(player);
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
    public int OnIncapacitated(obj_id self, obj_id killer) throws InterruptedException
    {
        clearCondition(self, CONDITION_CONVERSABLE);
        detachScript(self, "conversation.ep3_cpg_ace");
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
        if (ep3_cpg_ace_condition_isTooFar(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_185");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        if (ep3_cpg_ace_condition_isAttacking(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_156");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        if (!ep3_cpg_ace_condition_hasBadge_visited_Kachirho(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_187");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        if (ep3_cpg_ace_condition_isOnQuest_escort_alpha(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_233");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        if (ep3_cpg_ace_condition_isOnQuest_escort_bravo(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_235");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        if (ep3_cpg_ace_condition_isOnQuest_rescue_alpha(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_237");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (!ep3_cpg_ace_condition_isAttacking(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_239");
                }
                utils.setScriptVar(player, "conversation.ep3_cpg_ace.branchId", 6);
                npcStartConversation(player, npc, "ep3_cpg_ace", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (ep3_cpg_ace_condition_isOnQuest_AMBUSH(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_243");
            prose_package pp = new prose_package();
            pp.stringId = message;
            pp.actor.set(player);
            pp.target.set(npc);
            chat.chat(npc, player, null, null, pp);
            return SCRIPT_CONTINUE;
        }
        if (ep3_cpg_ace_condition_hasWon_assassinations(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_165");
            prose_package pp = new prose_package();
            pp.stringId = message;
            pp.actor.set(player);
            pp.target.set(npc);
            chat.chat(npc, player, null, null, pp);
            return SCRIPT_CONTINUE;
        }
        if (ep3_cpg_ace_condition__defaultCondition(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_351");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (!ep3_cpg_ace_condition_isAttacking(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_168");
                }
                utils.setScriptVar(player, "conversation.ep3_cpg_ace.branchId", 11);
                npcStartConversation(player, npc, "ep3_cpg_ace", message, responses);
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
        if (!conversationId.equals("ep3_cpg_ace"))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id npc = self;
        int branchId = utils.getIntScriptVar(player, "conversation.ep3_cpg_ace.branchId");
        if (branchId == 6 && ep3_cpg_ace_handleBranch6(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 7 && ep3_cpg_ace_handleBranch7(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 11 && ep3_cpg_ace_handleBranch11(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 12 && ep3_cpg_ace_handleBranch12(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        chat.chat(npc, "Error:  Fell through all branches and responses for OnNpcConversationResponse.");
        utils.removeScriptVar(player, "conversation.ep3_cpg_ace.branchId");
        return SCRIPT_CONTINUE;
    }
}
