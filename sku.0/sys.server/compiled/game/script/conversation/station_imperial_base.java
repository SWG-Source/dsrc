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
import script.library.space_battlefield;
import script.library.space_quest;
import script.library.space_transition;
import script.library.utils;

public class station_imperial_base extends script.base_script
{
    public station_imperial_base()
    {
    }
    public static String c_stringFile = "conversation/station_imperial_base";
    public boolean station_imperial_base_condition__defaultCondition(obj_id player, obj_id npc) throws InterruptedException
    {
        return true;
    }
    public boolean station_imperial_base_condition_isPlayerRebel(obj_id player, obj_id npc) throws InterruptedException
    {
        return (space_battlefield.isInRebelShip(player) || pvpGetAlignedFaction(player) == (370444368));
    }
    public boolean station_imperial_base_condition_hasReceivedReward_recovery_red_tie(obj_id player, obj_id npc) throws InterruptedException
    {
        return space_quest.hasReceivedReward(player, "recovery", "ep3_red_tie_recovery");
    }
    public boolean station_imperial_base_condition_isImperialFactionWithMission(obj_id player, obj_id npc) throws InterruptedException
    {
        if (space_battlefield.isInNeutralShip(player))
        {
            return ((pvpGetAlignedFaction(player) == (-615855020)) && space_quest.hasQuest(player));
        }
        else 
        {
            return false;
        }
    }
    public boolean station_imperial_base_condition_isInYacht(obj_id player, obj_id npc) throws InterruptedException
    {
        obj_id ship = space_transition.getContainingShip(player);
        if (isIdValid(ship))
        {
            String template = getTemplateName(ship);
            if (template != null && template.endsWith("_yacht.iff"))
            {
                return true;
            }
        }
        return false;
    }
    public boolean station_imperial_base_condition_isPlayerNeutral(obj_id player, obj_id npc) throws InterruptedException
    {
        if (space_battlefield.isInNeutralShip(player))
        {
            if (pvpGetAlignedFaction(player) == (-615855020))
            {
                return false;
            }
            else 
            {
                return true;
            }
        }
        return false;
    }
    public boolean station_imperial_base_condition_isTooFar(obj_id player, obj_id npc) throws InterruptedException
    {
        obj_id containingShip = space_transition.getContainingShip(player);
        return (getDistance(npc, containingShip) > space_transition.STATION_COMM_MAX_DISTANCE);
    }
    public boolean station_imperial_base_condition_hasFailed_recovery_red_tie(obj_id player, obj_id npc) throws InterruptedException
    {
        return (space_quest.hasFailedQuest(player, "recovery", "ep3_red_tie_recovery") || space_quest.hasAbortedQuest(player, "recovery", "ep3_red_tie_recovery"));
    }
    public boolean station_imperial_base_condition_hasFailed_escort_alpha(obj_id player, obj_id npc) throws InterruptedException
    {
        return space_quest.hasFailedQuest(player, "escort", "ep3_kash_station_imperial_escort_alpha");
    }
    public boolean station_imperial_base_condition_isOnQuest_escort(obj_id player, obj_id npc) throws InterruptedException
    {
        return space_quest.hasQuest(player, "escort", "ep3_kash_station_imperial_escort_alpha");
    }
    public boolean station_imperial_base_condition_hasReceivedReward_escort_apha(obj_id player, obj_id npc) throws InterruptedException
    {
        return space_quest.hasReceivedReward(player, "escort", "ep3_kash_station_imperial_escort_alpha");
    }
    public boolean station_imperial_base_condition_isReadyfor_escort_alpha(obj_id player, obj_id npc) throws InterruptedException
    {
        return (space_quest.hasWonQuest(player, "assassinate", "ep3_kash_station_assassinate_imperial") && !space_quest.hasWonQuest(player, "escort", "ep3_kash_station_imperial_escort_alpha"));
    }
    public boolean station_imperial_base_condition_isOnQuest_recovery(obj_id player, obj_id npc) throws InterruptedException
    {
        return space_quest.hasQuest(player, "recovery", "ep3_red_tie_recovery");
    }
    public boolean station_imperial_base_condition_hasWon_recovery_red_tie(obj_id player, obj_id npc) throws InterruptedException
    {
        return (space_quest.hasWonQuest(player, "recovery", "ep3_red_tie_recovery") && !space_quest.hasReceivedReward(player, "recovery", "ep3_red_tie_recovery"));
    }
    public boolean station_imperial_base_condition_hasWon_escort_alpha(obj_id player, obj_id npc) throws InterruptedException
    {
        return (space_quest.hasWonQuest(player, "escort", "ep3_kash_station_imperial_escort_alpha"));
    }
    public boolean station_imperial_base_condition_isIn_Red_TIE_Interceptor(obj_id player, obj_id npc) throws InterruptedException
    {
        obj_id objShip = getPilotedShip(player);
        String chassis = getShipChassisType(objShip);
        return (chassis.equals("player_tieinterceptor_imperial_guard"));
    }
    public void station_imperial_base_action_grantDuty_destroy(obj_id player, obj_id npc) throws InterruptedException
    {
        space_quest.grantQuest(player, "destroy_duty", "ep3_kash_station_destroy_duty_imperial");
    }
    public void station_imperial_base_action_grantDuty_escort(obj_id player, obj_id npc) throws InterruptedException
    {
        space_quest.grantQuest(player, "escort_duty", "ep3_kash_station_escort_duty_imperial");
    }
    public void station_imperial_base_action_grantMission_escort_alpha(obj_id player, obj_id npc) throws InterruptedException
    {
        space_quest.grantQuest(player, "escort", "ep3_kash_station_imperial_escort_alpha");
    }
    public void station_imperial_base_action_setPlayerToOvertStatus(obj_id player, obj_id npc) throws InterruptedException
    {
        space_transition.setPlayerOvert(player, (-615855020));
    }
    public void station_imperial_base_action_giveReward_escort_alpha(obj_id player, obj_id npc) throws InterruptedException
    {
        space_quest.giveReward(player, "escort", "ep3_kash_station_imperial_escort_alpha", 5000, "object/tangible/furniture/ep3_rewards/hologram_insignia_imperial_01.iff");
    }
    public void station_imperial_base_action_grantMission_recovery_red_tie(obj_id player, obj_id npc) throws InterruptedException
    {
        space_quest.grantQuest(player, "recovery", "ep3_red_tie_recovery");
    }
    public void station_imperial_base_action_giveReward_red_tie(obj_id player, obj_id npc) throws InterruptedException
    {
        badge.grantBadge(player, "bdg_kash_imperial_red_tie");
        space_quest.giveReward(player, "recovery", "ep3_red_tie_recovery", 15000, "object/tangible/ship/crafted/chassis/tieinterceptor_imperial_guard_reward_deed.iff");
    }
    public int station_imperial_base_handleBranch6(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_262"))
        {
            if (station_imperial_base_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_314");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (station_imperial_base_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (station_imperial_base_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_316");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_326");
                    }
                    utils.setScriptVar(player, "conversation.station_imperial_base.branchId", 21);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.station_imperial_base.branchId");
                    chat.chat(npc, player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_264"))
        {
            if (station_imperial_base_condition__defaultCondition(player, npc))
            {
                station_imperial_base_action_setPlayerToOvertStatus(player, npc);
                string_id message = new string_id(c_stringFile, "s_7b492de4");
                utils.removeScriptVar(player, "conversation.station_imperial_base.branchId");
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
    public int station_imperial_base_handleBranch7(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_268"))
        {
            if (station_imperial_base_condition__defaultCondition(player, npc))
            {
                station_imperial_base_action_giveReward_red_tie(player, npc);
                string_id message = new string_id(c_stringFile, "s_270");
                utils.removeScriptVar(player, "conversation.station_imperial_base.branchId");
                chat.chat(npc, player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_272"))
        {
            if (station_imperial_base_condition__defaultCondition(player, npc))
            {
                station_imperial_base_action_setPlayerToOvertStatus(player, npc);
                string_id message = new string_id(c_stringFile, "s_7b492de4");
                utils.removeScriptVar(player, "conversation.station_imperial_base.branchId");
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
    public int station_imperial_base_handleBranch11(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_280"))
        {
            if (station_imperial_base_condition__defaultCondition(player, npc))
            {
                station_imperial_base_action_grantMission_recovery_red_tie(player, npc);
                string_id message = new string_id(c_stringFile, "s_282");
                utils.removeScriptVar(player, "conversation.station_imperial_base.branchId");
                chat.chat(npc, player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_284"))
        {
            if (station_imperial_base_condition__defaultCondition(player, npc))
            {
                station_imperial_base_action_setPlayerToOvertStatus(player, npc);
                string_id message = new string_id(c_stringFile, "s_7b492de4");
                utils.removeScriptVar(player, "conversation.station_imperial_base.branchId");
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
    public int station_imperial_base_handleBranch13(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_288"))
        {
            if (station_imperial_base_condition__defaultCondition(player, npc))
            {
                station_imperial_base_action_grantMission_escort_alpha(player, npc);
                string_id message = new string_id(c_stringFile, "s_290");
                utils.removeScriptVar(player, "conversation.station_imperial_base.branchId");
                chat.chat(npc, player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int station_imperial_base_handleBranch15(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_294"))
        {
            if (station_imperial_base_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_296");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (station_imperial_base_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_298");
                    }
                    utils.setScriptVar(player, "conversation.station_imperial_base.branchId", 16);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.station_imperial_base.branchId");
                    chat.chat(npc, player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int station_imperial_base_handleBranch16(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_298"))
        {
            if (station_imperial_base_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_300");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (station_imperial_base_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_302");
                    }
                    utils.setScriptVar(player, "conversation.station_imperial_base.branchId", 17);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.station_imperial_base.branchId");
                    chat.chat(npc, player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int station_imperial_base_handleBranch17(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_302"))
        {
            if (station_imperial_base_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_304");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (station_imperial_base_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_306");
                    }
                    utils.setScriptVar(player, "conversation.station_imperial_base.branchId", 18);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.station_imperial_base.branchId");
                    chat.chat(npc, player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int station_imperial_base_handleBranch18(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_306"))
        {
            if (station_imperial_base_condition__defaultCondition(player, npc))
            {
                station_imperial_base_action_grantMission_escort_alpha(player, npc);
                string_id message = new string_id(c_stringFile, "s_308");
                utils.removeScriptVar(player, "conversation.station_imperial_base.branchId");
                chat.chat(npc, player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int station_imperial_base_handleBranch20(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_312"))
        {
            if (station_imperial_base_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_314");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (station_imperial_base_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (station_imperial_base_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_316");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_326");
                    }
                    utils.setScriptVar(player, "conversation.station_imperial_base.branchId", 21);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.station_imperial_base.branchId");
                    chat.chat(npc, player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_336"))
        {
            if (station_imperial_base_condition__defaultCondition(player, npc))
            {
                station_imperial_base_action_setPlayerToOvertStatus(player, npc);
                string_id message = new string_id(c_stringFile, "s_7b492de4");
                utils.removeScriptVar(player, "conversation.station_imperial_base.branchId");
                prose_package pp = new prose_package();
                pp.stringId = message;
                pp.actor.set(player);
                pp.target.set(npc);
                chat.chat(npc, player, pp);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_338"))
        {
            if (station_imperial_base_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_340");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (station_imperial_base_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_342");
                    }
                    utils.setScriptVar(player, "conversation.station_imperial_base.branchId", 26);
                    prose_package pp = new prose_package();
                    pp.stringId = message;
                    pp.actor.set(player);
                    pp.target.set(npc);
                    npcSpeak(player, pp);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.station_imperial_base.branchId");
                    prose_package pp = new prose_package();
                    pp.stringId = message;
                    pp.actor.set(player);
                    pp.target.set(npc);
                    chat.chat(npc, player, pp);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int station_imperial_base_handleBranch21(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_316"))
        {
            if (station_imperial_base_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_318");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (station_imperial_base_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (station_imperial_base_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_320");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_324");
                    }
                    utils.setScriptVar(player, "conversation.station_imperial_base.branchId", 22);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.station_imperial_base.branchId");
                    chat.chat(npc, player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_326"))
        {
            if (station_imperial_base_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_328");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (station_imperial_base_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (station_imperial_base_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_330");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_334");
                    }
                    utils.setScriptVar(player, "conversation.station_imperial_base.branchId", 24);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.station_imperial_base.branchId");
                    chat.chat(npc, player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int station_imperial_base_handleBranch22(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_320"))
        {
            if (station_imperial_base_condition__defaultCondition(player, npc))
            {
                station_imperial_base_action_grantDuty_destroy(player, npc);
                string_id message = new string_id(c_stringFile, "s_322");
                utils.removeScriptVar(player, "conversation.station_imperial_base.branchId");
                chat.chat(npc, player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_324"))
        {
            if (station_imperial_base_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_314");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (station_imperial_base_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (station_imperial_base_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_316");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_326");
                    }
                    utils.setScriptVar(player, "conversation.station_imperial_base.branchId", 21);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.station_imperial_base.branchId");
                    chat.chat(npc, player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int station_imperial_base_handleBranch24(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_330"))
        {
            if (station_imperial_base_condition__defaultCondition(player, npc))
            {
                station_imperial_base_action_grantDuty_escort(player, npc);
                string_id message = new string_id(c_stringFile, "s_332");
                utils.removeScriptVar(player, "conversation.station_imperial_base.branchId");
                chat.chat(npc, player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_334"))
        {
            if (station_imperial_base_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_314");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (station_imperial_base_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (station_imperial_base_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_316");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_326");
                    }
                    utils.setScriptVar(player, "conversation.station_imperial_base.branchId", 21);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.station_imperial_base.branchId");
                    chat.chat(npc, player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int station_imperial_base_handleBranch26(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_342"))
        {
            if (station_imperial_base_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_344");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (station_imperial_base_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_346");
                    }
                    utils.setScriptVar(player, "conversation.station_imperial_base.branchId", 27);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.station_imperial_base.branchId");
                    chat.chat(npc, player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int station_imperial_base_handleBranch27(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_346"))
        {
            if (station_imperial_base_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_348");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (station_imperial_base_condition__defaultCondition(player, npc))
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
                    utils.setScriptVar(player, "conversation.station_imperial_base.branchId", 28);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.station_imperial_base.branchId");
                    chat.chat(npc, player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int station_imperial_base_handleBranch28(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_350"))
        {
            if (station_imperial_base_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_352");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (station_imperial_base_condition__defaultCondition(player, npc))
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
                    utils.setScriptVar(player, "conversation.station_imperial_base.branchId", 29);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.station_imperial_base.branchId");
                    chat.chat(npc, player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int station_imperial_base_handleBranch29(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_354"))
        {
            if (station_imperial_base_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_356");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (station_imperial_base_condition__defaultCondition(player, npc))
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
                    utils.setScriptVar(player, "conversation.station_imperial_base.branchId", 30);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.station_imperial_base.branchId");
                    chat.chat(npc, player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int station_imperial_base_handleBranch30(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_358"))
        {
            if (station_imperial_base_condition__defaultCondition(player, npc))
            {
                station_imperial_base_action_grantMission_recovery_red_tie(player, npc);
                string_id message = new string_id(c_stringFile, "s_360");
                utils.removeScriptVar(player, "conversation.station_imperial_base.branchId");
                chat.chat(npc, player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int station_imperial_base_handleBranch32(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_364"))
        {
            station_imperial_base_action_giveReward_escort_alpha(player, npc);
            if (station_imperial_base_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_393");
                utils.removeScriptVar(player, "conversation.station_imperial_base.branchId");
                chat.chat(npc, player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int station_imperial_base_handleBranch34(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_370822d1"))
        {
            if (station_imperial_base_condition__defaultCondition(player, npc))
            {
                station_imperial_base_action_setPlayerToOvertStatus(player, npc);
                string_id message = new string_id(c_stringFile, "s_7b492de4");
                utils.removeScriptVar(player, "conversation.station_imperial_base.branchId");
                prose_package pp = new prose_package();
                pp.stringId = message;
                pp.actor.set(player);
                pp.target.set(npc);
                chat.chat(npc, player, pp);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_457a7010"))
        {
            if (station_imperial_base_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_1e8d6243");
                utils.removeScriptVar(player, "conversation.station_imperial_base.branchId");
                chat.chat(npc, player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_dd67013"))
        {
            if (station_imperial_base_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_25329880");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (station_imperial_base_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_626fe576");
                    }
                    utils.setScriptVar(player, "conversation.station_imperial_base.branchId", 37);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.station_imperial_base.branchId");
                    chat.chat(npc, player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int station_imperial_base_handleBranch37(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_626fe576"))
        {
            if (station_imperial_base_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_41ebf230");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (station_imperial_base_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_3a7768e6");
                    }
                    utils.setScriptVar(player, "conversation.station_imperial_base.branchId", 38);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.station_imperial_base.branchId");
                    chat.chat(npc, player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int station_imperial_base_handleBranch38(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_3a7768e6"))
        {
            if (station_imperial_base_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_ef2a0e9c");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (station_imperial_base_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (station_imperial_base_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (station_imperial_base_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_370822d1");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_457a7010");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_dd67013");
                    }
                    utils.setScriptVar(player, "conversation.station_imperial_base.branchId", 34);
                    prose_package pp = new prose_package();
                    pp.stringId = message;
                    pp.actor.set(player);
                    pp.target.set(npc);
                    npcSpeak(player, pp);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.station_imperial_base.branchId");
                    prose_package pp = new prose_package();
                    pp.stringId = message;
                    pp.actor.set(player);
                    pp.target.set(npc);
                    chat.chat(npc, player, pp);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int station_imperial_base_handleBranch39(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_370822d1"))
        {
            if (station_imperial_base_condition__defaultCondition(player, npc))
            {
                station_imperial_base_action_setPlayerToOvertStatus(player, npc);
                string_id message = new string_id(c_stringFile, "s_7b492de4");
                utils.removeScriptVar(player, "conversation.station_imperial_base.branchId");
                prose_package pp = new prose_package();
                pp.stringId = message;
                pp.actor.set(player);
                pp.target.set(npc);
                chat.chat(npc, player, pp);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_457a7010"))
        {
            if (station_imperial_base_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_1e8d6243");
                utils.removeScriptVar(player, "conversation.station_imperial_base.branchId");
                chat.chat(npc, player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_dd67013"))
        {
            if (station_imperial_base_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_25329880");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (station_imperial_base_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_626fe576");
                    }
                    utils.setScriptVar(player, "conversation.station_imperial_base.branchId", 37);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.station_imperial_base.branchId");
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
        setCondition(self, CONDITION_CONVERSABLE);
        setObjVar(self, "convo.appearance", "object/mobile/space_comm_kash_imp_base.iff");
        return SCRIPT_CONTINUE;
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        setCondition(self, CONDITION_CONVERSABLE);
        setObjVar(self, "convo.appearance", "object/mobile/space_comm_kash_imp_base.iff");
        detachScript(self, "space.content_tools.spacestation");
        return SCRIPT_CONTINUE;
    }
    public int OnObjectMenuRequest(obj_id self, obj_id player, menu_info menuInfo) throws InterruptedException
    {
        int menu = menuInfo.addRootMenu(menu_info_types.CONVERSE_START, null);
        menu_info_data menuInfoData = menuInfo.getMenuItemById(menu);
        menuInfoData.setServerNotify(false);
        setCondition(self, CONDITION_CONVERSABLE);
        if (space_quest.hasWonQuest(player, "recovery", "ep3_red_tie_recovery") && space_quest.hasReceivedReward(player, "recovery", "ep3_red_tie_recovery"))
        {
            if (!badge.hasBadge(player, "bdg_kash_imperial_red_tie"))
            {
                badge.grantBadge(player, "bdg_kash_imperial_red_tie");
            }
        }
        if (!space_quest.hasWonQuest(player, "recovery", "ep3_heavy_xwing_recovery"))
        {
            if (badge.hasBadge(player, "bdg_kash_rebel_heavy_xwing"))
            {
                badge.revokeBadge(player, "bdg_kash_rebel_heavy_xwing", true);
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int OnIncapacitated(obj_id self, obj_id killer) throws InterruptedException
    {
        clearCondition(self, CONDITION_CONVERSABLE);
        detachScript(self, "conversation.station_imperial_base");
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
        if (station_imperial_base_condition_isInYacht(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_358253b2");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        if (station_imperial_base_condition_isPlayerRebel(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_f48e8567");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        if (station_imperial_base_condition_isImperialFactionWithMission(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_e8cb2563");
            prose_package pp = new prose_package();
            pp.stringId = message;
            pp.actor.set(player);
            pp.target.set(npc);
            chat.chat(npc, player, null, null, pp);
            return SCRIPT_CONTINUE;
        }
        if (station_imperial_base_condition_isPlayerNeutral(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_2e491159");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        if (station_imperial_base_condition_isTooFar(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_48f82131");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        if (station_imperial_base_condition_isIn_Red_TIE_Interceptor(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_260");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (station_imperial_base_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            boolean hasResponse1 = false;
            if (station_imperial_base_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_262");
                }
                if (hasResponse1)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_264");
                }
                utils.setScriptVar(player, "conversation.station_imperial_base.branchId", 6);
                prose_package pp = new prose_package();
                pp.stringId = message;
                pp.actor.set(player);
                pp.target.set(npc);
                npcStartConversation(player, npc, "station_imperial_base", null, pp, responses);
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
        if (station_imperial_base_condition_hasWon_recovery_red_tie(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_266");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (station_imperial_base_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            boolean hasResponse1 = false;
            if (station_imperial_base_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_268");
                }
                if (hasResponse1)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_272");
                }
                utils.setScriptVar(player, "conversation.station_imperial_base.branchId", 7);
                npcStartConversation(player, npc, "station_imperial_base", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (station_imperial_base_condition_isOnQuest_recovery(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_274");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        if (station_imperial_base_condition_isOnQuest_escort(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_276");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        if (station_imperial_base_condition_hasFailed_recovery_red_tie(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_278");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (station_imperial_base_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            boolean hasResponse1 = false;
            if (station_imperial_base_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_280");
                }
                if (hasResponse1)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_284");
                }
                utils.setScriptVar(player, "conversation.station_imperial_base.branchId", 11);
                npcStartConversation(player, npc, "station_imperial_base", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (station_imperial_base_condition_hasFailed_escort_alpha(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_286");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (station_imperial_base_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_288");
                }
                utils.setScriptVar(player, "conversation.station_imperial_base.branchId", 13);
                npcStartConversation(player, npc, "station_imperial_base", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (station_imperial_base_condition_isReadyfor_escort_alpha(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_292");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (station_imperial_base_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_294");
                }
                utils.setScriptVar(player, "conversation.station_imperial_base.branchId", 15);
                npcStartConversation(player, npc, "station_imperial_base", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (station_imperial_base_condition_hasReceivedReward_escort_apha(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_310");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (station_imperial_base_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            boolean hasResponse1 = false;
            if (station_imperial_base_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse1 = true;
            }
            boolean hasResponse2 = false;
            if (!station_imperial_base_condition_hasReceivedReward_recovery_red_tie(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_312");
                }
                if (hasResponse1)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_336");
                }
                if (hasResponse2)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_338");
                }
                utils.setScriptVar(player, "conversation.station_imperial_base.branchId", 20);
                npcStartConversation(player, npc, "station_imperial_base", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (station_imperial_base_condition_hasWon_escort_alpha(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_362");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (station_imperial_base_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_364");
                }
                utils.setScriptVar(player, "conversation.station_imperial_base.branchId", 32);
                npcStartConversation(player, npc, "station_imperial_base", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (station_imperial_base_condition__defaultCondition(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_afe52489");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (station_imperial_base_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            boolean hasResponse1 = false;
            if (station_imperial_base_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse1 = true;
            }
            boolean hasResponse2 = false;
            if (station_imperial_base_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_370822d1");
                }
                if (hasResponse1)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_457a7010");
                }
                if (hasResponse2)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_dd67013");
                }
                utils.setScriptVar(player, "conversation.station_imperial_base.branchId", 34);
                prose_package pp = new prose_package();
                pp.stringId = message;
                pp.actor.set(player);
                pp.target.set(npc);
                npcStartConversation(player, npc, "station_imperial_base", null, pp, responses);
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
        chat.chat(npc, "Error:  All conditions for OnStartNpcConversation were false.");
        return SCRIPT_CONTINUE;
    }
    public int OnNpcConversationResponse(obj_id self, String conversationId, obj_id player, string_id response) throws InterruptedException
    {
        if (!conversationId.equals("station_imperial_base"))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id npc = self;
        int branchId = utils.getIntScriptVar(player, "conversation.station_imperial_base.branchId");
        if (branchId == 6 && station_imperial_base_handleBranch6(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 7 && station_imperial_base_handleBranch7(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 11 && station_imperial_base_handleBranch11(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 13 && station_imperial_base_handleBranch13(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 15 && station_imperial_base_handleBranch15(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 16 && station_imperial_base_handleBranch16(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 17 && station_imperial_base_handleBranch17(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 18 && station_imperial_base_handleBranch18(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 20 && station_imperial_base_handleBranch20(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 21 && station_imperial_base_handleBranch21(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 22 && station_imperial_base_handleBranch22(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 24 && station_imperial_base_handleBranch24(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 26 && station_imperial_base_handleBranch26(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 27 && station_imperial_base_handleBranch27(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 28 && station_imperial_base_handleBranch28(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 29 && station_imperial_base_handleBranch29(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 30 && station_imperial_base_handleBranch30(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 32 && station_imperial_base_handleBranch32(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 34 && station_imperial_base_handleBranch34(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 37 && station_imperial_base_handleBranch37(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 38 && station_imperial_base_handleBranch38(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 39 && station_imperial_base_handleBranch39(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        chat.chat(npc, "Error:  Fell through all branches and responses for OnNpcConversationResponse.");
        utils.removeScriptVar(player, "conversation.station_imperial_base.branchId");
        return SCRIPT_CONTINUE;
    }
}
