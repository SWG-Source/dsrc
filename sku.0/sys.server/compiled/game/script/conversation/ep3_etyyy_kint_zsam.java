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
import script.library.group;
import script.library.space_dungeon;
import script.library.utils;

public class ep3_etyyy_kint_zsam extends script.base_script
{
    public ep3_etyyy_kint_zsam()
    {
    }
    public static String c_stringFile = "conversation/ep3_etyyy_kint_zsam";
    public boolean ep3_etyyy_kint_zsam_condition__defaultCondition(obj_id player, obj_id npc) throws InterruptedException
    {
        return true;
    }
    public boolean ep3_etyyy_kint_zsam_condition_hasHraccaTicket(obj_id player, obj_id npc) throws InterruptedException
    {
        obj_id[] tickets = space_dungeon.findValidDungeonTickets(player, npc);
        if (tickets != null && tickets.length > 0)
        {
            return true;
        }
        return false;
    }
    public boolean ep3_etyyy_kint_zsam_condition_knowsKint(obj_id player, obj_id npc) throws InterruptedException
    {
        return (groundquests.hasCompletedQuest(player, "ep3_hunt_johnson_seek_kint"));
    }
    public boolean ep3_etyyy_kint_zsam_condition_canEnterHracca(obj_id player, obj_id npc) throws InterruptedException
    {
        if (groundquests.isTaskActive(player, "ep3_hunt_hracca_kkorrwrot_hunt", "hracca_huntKkorrwrot"))
        {
            if (ep3_etyyy_kint_zsam_condition_hasHraccaTicket(player, npc))
            {
                return true;
            }
        }
        return false;
    }
    public boolean ep3_etyyy_kint_zsam_condition_sentBySmith(obj_id player, obj_id npc) throws InterruptedException
    {
        return (groundquests.isQuestActive(player, "ep3_hunt_johnson_seek_kint"));
    }
    public boolean ep3_etyyy_kint_zsam_condition_hasKilledKkorrwrot(obj_id player, obj_id npc) throws InterruptedException
    {
        return groundquests.isTaskActive(player, "ep3_hunt_hracca_kkorrwrot_hunt", "hracca_kkorrwrotKilled");
    }
    public boolean ep3_etyyy_kint_zsam_condition_needsATicket(obj_id player, obj_id npc) throws InterruptedException
    {
        if (groundquests.isTaskActive(player, "ep3_hunt_hracca_kkorrwrot_hunt", "hracca_huntKkorrwrot"))
        {
            if (!ep3_etyyy_kint_zsam_condition_hasHraccaTicket(player, npc))
            {
                return true;
            }
        }
        return false;
    }
    public boolean ep3_etyyy_kint_zsam_condition_canRepeatHunt(obj_id player, obj_id npc) throws InterruptedException
    {
        return (groundquests.hasCompletedQuest(player, "ep3_hunt_hracca_kkorrwrot_hunt"));
    }
    public boolean ep3_etyyy_kint_zsam_condition_tooManyInGroup(obj_id player, obj_id npc) throws InterruptedException
    {
        if (group.isGrouped(player))
        {
            obj_id groupObj = getGroupObject(player);
            if (isIdValid(groupObj))
            {
                int numGroupMembers = getGroupSize(groupObj);
                if (numGroupMembers > 8)
                {
                    return true;
                }
            }
        }
        return false;
    }
    public boolean ep3_etyyy_kint_zsam_condition_isInGodMode(obj_id player, obj_id npc) throws InterruptedException
    {
        return isGod(player);
    }
    public boolean ep3_etyyy_kint_zsam_condition_killedKkorrwrotNoBadge(obj_id player, obj_id npc) throws InterruptedException
    {
        return groundquests.hasCompletedQuest(player, "ep3_hunt_hracca_kkorrwrot_hunt") && !badge.hasBadge(player, "bdg_kash_kkorrwrot");
    }
    public void ep3_etyyy_kint_zsam_action_giveHraccaGatePass(obj_id player, obj_id npc) throws InterruptedException
    {
        if (!ep3_etyyy_kint_zsam_condition_hasHraccaTicket(player, npc))
        {
            String ticketTemplate = "object/tangible/travel/travel_ticket/hracca_glade_ticket.iff";
            obj_id ticket = space_dungeon.createTicket(player, "kashyyyk_hunting", "kashyyyk_hunting", space_dungeon.KASH_MONSTER_ISLAND, ticketTemplate);
            if (isIdValid(ticket))
            {
                setObjVar(ticket, "space_dungeon.ticket.quest_type", "ep3_hunt_hracca_kkorrwrot_hunt");
                setObjVar(ticket, "kashyyyk.ticket_owner", player);
            }
        }
        return;
    }
    public void ep3_etyyy_kint_zsam_action_endSuccessfulHunt(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.sendSignal(player, "hracca_kkorrwrotKilled");
        return;
    }
    public void ep3_etyyy_kint_zsam_action_sendToHracca(obj_id player, obj_id npc) throws InterruptedException
    {
        space_dungeon.selectDungeonTicket(npc, player);
        return;
    }
    public void ep3_etyyy_kint_zsam_action_giveKkorrwrotQuest(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.sendSignal(player, "johnson_talkToKint");
        groundquests.grantQuest(player, "ep3_hunt_hracca_kkorrwrot_hunt");
        ep3_etyyy_kint_zsam_action_giveHraccaGatePass(player, npc);
        return;
    }
    public void ep3_etyyy_kint_zsam_action_godModeEntry(obj_id player, obj_id npc) throws InterruptedException
    {
        if (isGod(player))
        {
            String ticketTemplate = "object/tangible/travel/travel_ticket/hracca_glade_ticket.iff";
            obj_id ticket = space_dungeon.createTicket(player, "kashyyyk_hunting", "kashyyyk_hunting", space_dungeon.KASH_MONSTER_ISLAND, ticketTemplate);
            if (isIdValid(ticket))
            {
                setObjVar(ticket, "space_dungeon.ticket.quest_type", "none");
                setObjVar(ticket, "kashyyyk.ticket_owner", player);
                space_dungeon.selectDungeonTicket(npc, player);
            }
        }
        return;
    }
    public void ep3_etyyy_kint_zsam_action_giveKkorrwrotBadge(obj_id player, obj_id npc) throws InterruptedException
    {
        if (!badge.hasBadge(player, "bdg_kash_kkorrwrot"))
        {
            badge.grantBadge(player, "bdg_kash_kkorrwrot");
        }
        return;
    }
    public void ep3_etyyy_kint_zsam_action_revokeKkorrwrotBadge(obj_id player, obj_id npc) throws InterruptedException
    {
        if (badge.hasBadge(player, "bdg_kash_kkorrwrot"))
        {
            badge.revokeBadge(player, "bdg_kash_kkorrwrot", true);
        }
    }
    public String ep3_etyyy_kint_zsam_tokenTO_getNameOfPlayer(obj_id player, obj_id npc) throws InterruptedException
    {
        return getFirstName(player);
    }
    public int ep3_etyyy_kint_zsam_handleBranch1(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_185"))
        {
            if (ep3_etyyy_kint_zsam_condition_tooManyInGroup(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_187");
                utils.removeScriptVar(player, "conversation.ep3_etyyy_kint_zsam.branchId");
                chat.chat(npc, player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            if (ep3_etyyy_kint_zsam_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_189");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (ep3_etyyy_kint_zsam_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_257");
                    }
                    utils.setScriptVar(player, "conversation.ep3_etyyy_kint_zsam.branchId", 3);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.ep3_etyyy_kint_zsam.branchId");
                    chat.chat(npc, player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_379"))
        {
            if (ep3_etyyy_kint_zsam_condition__defaultCondition(player, npc))
            {
                ep3_etyyy_kint_zsam_action_giveKkorrwrotBadge(player, npc);
                string_id message = new string_id(c_stringFile, "s_380");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (ep3_etyyy_kint_zsam_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (ep3_etyyy_kint_zsam_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_381");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_383");
                    }
                    utils.setScriptVar(player, "conversation.ep3_etyyy_kint_zsam.branchId", 5);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.ep3_etyyy_kint_zsam.branchId");
                    chat.chat(npc, player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_191"))
        {
            if (ep3_etyyy_kint_zsam_condition__defaultCondition(player, npc))
            {
                ep3_etyyy_kint_zsam_action_endSuccessfulHunt(player, npc);
                string_id message = new string_id(c_stringFile, "s_193");
                utils.removeScriptVar(player, "conversation.ep3_etyyy_kint_zsam.branchId");
                chat.chat(npc, player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_195"))
        {
            if (ep3_etyyy_kint_zsam_condition__defaultCondition(player, npc))
            {
                ep3_etyyy_kint_zsam_action_giveHraccaGatePass(player, npc);
                string_id message = new string_id(c_stringFile, "s_197");
                utils.removeScriptVar(player, "conversation.ep3_etyyy_kint_zsam.branchId");
                chat.chat(npc, player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_199"))
        {
            if (ep3_etyyy_kint_zsam_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_201");
                utils.removeScriptVar(player, "conversation.ep3_etyyy_kint_zsam.branchId");
                chat.chat(npc, player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int ep3_etyyy_kint_zsam_handleBranch3(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_257"))
        {
            if (ep3_etyyy_kint_zsam_condition__defaultCondition(player, npc))
            {
                ep3_etyyy_kint_zsam_action_sendToHracca(player, npc);
                string_id message = new string_id(c_stringFile, "s_259");
                utils.removeScriptVar(player, "conversation.ep3_etyyy_kint_zsam.branchId");
                chat.chat(npc, player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int ep3_etyyy_kint_zsam_handleBranch5(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_381"))
        {
            if (ep3_etyyy_kint_zsam_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_382");
                utils.removeScriptVar(player, "conversation.ep3_etyyy_kint_zsam.branchId");
                chat.chat(npc, player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_383"))
        {
            if (ep3_etyyy_kint_zsam_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_384");
                utils.removeScriptVar(player, "conversation.ep3_etyyy_kint_zsam.branchId");
                chat.chat(npc, player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int ep3_etyyy_kint_zsam_handleBranch11(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_205"))
        {
            if (ep3_etyyy_kint_zsam_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_208");
                utils.removeScriptVar(player, "conversation.ep3_etyyy_kint_zsam.branchId");
                chat.chat(npc, player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_210"))
        {
            if (ep3_etyyy_kint_zsam_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_212");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (ep3_etyyy_kint_zsam_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_215");
                    }
                    utils.setScriptVar(player, "conversation.ep3_etyyy_kint_zsam.branchId", 13);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.ep3_etyyy_kint_zsam.branchId");
                    chat.chat(npc, player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_282"))
        {
            if (ep3_etyyy_kint_zsam_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_284");
                utils.removeScriptVar(player, "conversation.ep3_etyyy_kint_zsam.branchId");
                chat.chat(npc, player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_287"))
        {
            if (ep3_etyyy_kint_zsam_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_290");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (ep3_etyyy_kint_zsam_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_293");
                    }
                    utils.setScriptVar(player, "conversation.ep3_etyyy_kint_zsam.branchId", 29);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.ep3_etyyy_kint_zsam.branchId");
                    chat.chat(npc, player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_301"))
        {
            if (ep3_etyyy_kint_zsam_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_303");
                utils.removeScriptVar(player, "conversation.ep3_etyyy_kint_zsam.branchId");
                chat.chat(npc, player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int ep3_etyyy_kint_zsam_handleBranch13(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_215"))
        {
            if (ep3_etyyy_kint_zsam_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_217");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (ep3_etyyy_kint_zsam_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (ep3_etyyy_kint_zsam_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (ep3_etyyy_kint_zsam_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse2 = true;
                }
                boolean hasResponse3 = false;
                if (ep3_etyyy_kint_zsam_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse3 = true;
                }
                boolean hasResponse4 = false;
                if (ep3_etyyy_kint_zsam_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_219");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_223");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_268");
                    }
                    if (hasResponse3)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_273");
                    }
                    if (hasResponse4)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_277");
                    }
                    utils.setScriptVar(player, "conversation.ep3_etyyy_kint_zsam.branchId", 14);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.ep3_etyyy_kint_zsam.branchId");
                    chat.chat(npc, player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int ep3_etyyy_kint_zsam_handleBranch14(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_219"))
        {
            if (ep3_etyyy_kint_zsam_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_221");
                utils.removeScriptVar(player, "conversation.ep3_etyyy_kint_zsam.branchId");
                chat.chat(npc, player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_223"))
        {
            if (ep3_etyyy_kint_zsam_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_225");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (ep3_etyyy_kint_zsam_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (ep3_etyyy_kint_zsam_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (ep3_etyyy_kint_zsam_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_227");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_231");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_235");
                    }
                    utils.setScriptVar(player, "conversation.ep3_etyyy_kint_zsam.branchId", 16);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.ep3_etyyy_kint_zsam.branchId");
                    chat.chat(npc, player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_268"))
        {
            if (ep3_etyyy_kint_zsam_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "belly_laugh");
                string_id message = new string_id(c_stringFile, "s_270");
                utils.removeScriptVar(player, "conversation.ep3_etyyy_kint_zsam.branchId");
                chat.chat(npc, player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_273"))
        {
            if (ep3_etyyy_kint_zsam_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_275");
                utils.removeScriptVar(player, "conversation.ep3_etyyy_kint_zsam.branchId");
                chat.chat(npc, player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_277"))
        {
            if (ep3_etyyy_kint_zsam_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_279");
                utils.removeScriptVar(player, "conversation.ep3_etyyy_kint_zsam.branchId");
                chat.chat(npc, player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int ep3_etyyy_kint_zsam_handleBranch16(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_227"))
        {
            if (ep3_etyyy_kint_zsam_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_229");
                utils.removeScriptVar(player, "conversation.ep3_etyyy_kint_zsam.branchId");
                chat.chat(npc, player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_231"))
        {
            if (ep3_etyyy_kint_zsam_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_233");
                utils.removeScriptVar(player, "conversation.ep3_etyyy_kint_zsam.branchId");
                chat.chat(npc, player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_235"))
        {
            if (ep3_etyyy_kint_zsam_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_237");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (ep3_etyyy_kint_zsam_condition__defaultCondition(player, npc))
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
                    utils.setScriptVar(player, "conversation.ep3_etyyy_kint_zsam.branchId", 19);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.ep3_etyyy_kint_zsam.branchId");
                    chat.chat(npc, player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int ep3_etyyy_kint_zsam_handleBranch19(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_239"))
        {
            if (ep3_etyyy_kint_zsam_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_241");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (ep3_etyyy_kint_zsam_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_243");
                    }
                    utils.setScriptVar(player, "conversation.ep3_etyyy_kint_zsam.branchId", 20);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.ep3_etyyy_kint_zsam.branchId");
                    chat.chat(npc, player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int ep3_etyyy_kint_zsam_handleBranch20(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_243"))
        {
            if (ep3_etyyy_kint_zsam_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_246");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (ep3_etyyy_kint_zsam_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (ep3_etyyy_kint_zsam_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_250");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_262");
                    }
                    utils.setScriptVar(player, "conversation.ep3_etyyy_kint_zsam.branchId", 21);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.ep3_etyyy_kint_zsam.branchId");
                    chat.chat(npc, player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int ep3_etyyy_kint_zsam_handleBranch21(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_250"))
        {
            if (ep3_etyyy_kint_zsam_condition_hasHraccaTicket(player, npc))
            {
                ep3_etyyy_kint_zsam_action_giveKkorrwrotQuest(player, npc);
                string_id message = new string_id(c_stringFile, "s_254");
                utils.removeScriptVar(player, "conversation.ep3_etyyy_kint_zsam.branchId");
                chat.chat(npc, player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            if (ep3_etyyy_kint_zsam_condition__defaultCondition(player, npc))
            {
                ep3_etyyy_kint_zsam_action_giveKkorrwrotQuest(player, npc);
                string_id message = new string_id(c_stringFile, "s_258");
                utils.removeScriptVar(player, "conversation.ep3_etyyy_kint_zsam.branchId");
                chat.chat(npc, player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_262"))
        {
            if (ep3_etyyy_kint_zsam_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_266");
                utils.removeScriptVar(player, "conversation.ep3_etyyy_kint_zsam.branchId");
                chat.chat(npc, player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int ep3_etyyy_kint_zsam_handleBranch29(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_293"))
        {
            if (ep3_etyyy_kint_zsam_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_295");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (ep3_etyyy_kint_zsam_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_297");
                    }
                    utils.setScriptVar(player, "conversation.ep3_etyyy_kint_zsam.branchId", 30);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.ep3_etyyy_kint_zsam.branchId");
                    chat.chat(npc, player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int ep3_etyyy_kint_zsam_handleBranch30(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_297"))
        {
            if (ep3_etyyy_kint_zsam_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_299");
                utils.removeScriptVar(player, "conversation.ep3_etyyy_kint_zsam.branchId");
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
            detachScript(self, "conversation.ep3_etyyy_kint_zsam");
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
        detachScript(self, "conversation.ep3_etyyy_kint_zsam");
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
        if (ep3_etyyy_kint_zsam_condition_knowsKint(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_183");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (ep3_etyyy_kint_zsam_condition_canEnterHracca(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            boolean hasResponse1 = false;
            if (ep3_etyyy_kint_zsam_condition_killedKkorrwrotNoBadge(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse1 = true;
            }
            boolean hasResponse2 = false;
            if (ep3_etyyy_kint_zsam_condition_hasKilledKkorrwrot(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse2 = true;
            }
            boolean hasResponse3 = false;
            if (ep3_etyyy_kint_zsam_condition_needsATicket(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse3 = true;
            }
            boolean hasResponse4 = false;
            if (ep3_etyyy_kint_zsam_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_185");
                }
                if (hasResponse1)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_379");
                }
                if (hasResponse2)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_191");
                }
                if (hasResponse3)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_195");
                }
                if (hasResponse4)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_199");
                }
                utils.setScriptVar(player, "conversation.ep3_etyyy_kint_zsam.branchId", 1);
                prose_package pp = new prose_package();
                pp.stringId = message;
                pp.actor.set(player);
                pp.target.set(npc);
                pp.other.set(ep3_etyyy_kint_zsam_tokenTO_getNameOfPlayer(player, npc));
                npcStartConversation(player, npc, "ep3_etyyy_kint_zsam", null, pp, responses);
            }
            else 
            {
                prose_package pp = new prose_package();
                pp.stringId = message;
                pp.actor.set(player);
                pp.target.set(npc);
                pp.other.set(ep3_etyyy_kint_zsam_tokenTO_getNameOfPlayer(player, npc));
                chat.chat(npc, player, null, null, pp);
            }
            return SCRIPT_CONTINUE;
        }
        if (ep3_etyyy_kint_zsam_condition__defaultCondition(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_203");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (ep3_etyyy_kint_zsam_condition_sentBySmith(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            boolean hasResponse1 = false;
            if (ep3_etyyy_kint_zsam_condition_sentBySmith(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse1 = true;
            }
            boolean hasResponse2 = false;
            if (ep3_etyyy_kint_zsam_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse2 = true;
            }
            boolean hasResponse3 = false;
            if (ep3_etyyy_kint_zsam_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse3 = true;
            }
            boolean hasResponse4 = false;
            if (ep3_etyyy_kint_zsam_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_205");
                }
                if (hasResponse1)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_210");
                }
                if (hasResponse2)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_282");
                }
                if (hasResponse3)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_287");
                }
                if (hasResponse4)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_301");
                }
                utils.setScriptVar(player, "conversation.ep3_etyyy_kint_zsam.branchId", 11);
                npcStartConversation(player, npc, "ep3_etyyy_kint_zsam", message, responses);
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
        if (!conversationId.equals("ep3_etyyy_kint_zsam"))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id npc = self;
        int branchId = utils.getIntScriptVar(player, "conversation.ep3_etyyy_kint_zsam.branchId");
        if (branchId == 1 && ep3_etyyy_kint_zsam_handleBranch1(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 3 && ep3_etyyy_kint_zsam_handleBranch3(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 5 && ep3_etyyy_kint_zsam_handleBranch5(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 11 && ep3_etyyy_kint_zsam_handleBranch11(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 13 && ep3_etyyy_kint_zsam_handleBranch13(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 14 && ep3_etyyy_kint_zsam_handleBranch14(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 16 && ep3_etyyy_kint_zsam_handleBranch16(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 19 && ep3_etyyy_kint_zsam_handleBranch19(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 20 && ep3_etyyy_kint_zsam_handleBranch20(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 21 && ep3_etyyy_kint_zsam_handleBranch21(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 29 && ep3_etyyy_kint_zsam_handleBranch29(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 30 && ep3_etyyy_kint_zsam_handleBranch30(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        chat.chat(npc, "Error:  Fell through all branches and responses for OnNpcConversationResponse.");
        utils.removeScriptVar(player, "conversation.ep3_etyyy_kint_zsam.branchId");
        return SCRIPT_CONTINUE;
    }
}
