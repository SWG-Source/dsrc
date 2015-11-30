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
import script.library.prose;
import script.library.static_item;
import script.library.utils;

public class som_kenobi_epo_qetora extends script.base_script
{
    public som_kenobi_epo_qetora()
    {
    }
    public static String c_stringFile = "conversation/som_kenobi_epo_qetora";
    public boolean som_kenobi_epo_qetora_condition__defaultCondition(obj_id player, obj_id npc) throws InterruptedException
    {
        return true;
    }
    public boolean som_kenobi_epo_qetora_condition_onQuest(obj_id player, obj_id npc) throws InterruptedException
    {
        if (groundquests.isQuestActive(player, "som_kenobi_historian_1") || groundquests.isQuestActive(player, "som_kenobi_historian_smuggler"))
        {
            return true;
        }
        else 
        {
            return false;
        }
    }
    public boolean som_kenobi_epo_qetora_condition_isSmuggler(obj_id player, obj_id npc) throws InterruptedException
    {
        return hasSkill(player, "class_smuggler_phase1_novice");
    }
    public boolean som_kenobi_epo_qetora_condition_haveDisk(obj_id player, obj_id npc) throws InterruptedException
    {
        if (groundquests.isTaskActive(player, "som_kenobi_historian_1", "success") || groundquests.isTaskActive(player, "som_kenobi_historian_smuggler", "success") || groundquests.isTaskActive(player, "som_kenobi_historian_1", "done") || groundquests.isTaskActive(player, "som_kenobi_historian_smuggler", "done"))
        {
            return true;
        }
        else 
        {
            return false;
        }
    }
    public boolean som_kenobi_epo_qetora_condition_finishedQuestAll(obj_id player, obj_id npc) throws InterruptedException
    {
        if (groundquests.hasCompletedQuest(player, "som_kenobi_historian_1") || groundquests.hasCompletedQuest(player, "som_kenobi_historian_smuggler"))
        {
            return groundquests.hasCompletedQuest(player, "som_kenobi_historian_2");
        }
        else 
        {
            return false;
        }
    }
    public boolean som_kenobi_epo_qetora_condition_finishedQuest1(obj_id player, obj_id npc) throws InterruptedException
    {
        if (groundquests.hasCompletedQuest(player, "som_kenobi_historian_1") || groundquests.hasCompletedQuest(player, "som_kenobi_historian_smuggler"))
        {
            return true;
        }
        else 
        {
            return false;
        }
    }
    public boolean som_kenobi_epo_qetora_condition_onQuest2(obj_id player, obj_id npc) throws InterruptedException
    {
        return (groundquests.isQuestActive(player, "som_kenobi_historian_2"));
    }
    public boolean som_kenobi_epo_qetora_condition_rewardQuest2(obj_id player, obj_id npc) throws InterruptedException
    {
        return groundquests.isTaskActive(player, "som_kenobi_historian_2", "retrievedPieces");
    }
    public void som_kenobi_epo_qetora_action_giveQuestSmuggler(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.grantQuest(player, "som_kenobi_historian_smuggler");
    }
    public void som_kenobi_epo_qetora_action_giveQuest(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.grantQuest(player, "som_kenobi_historian_1");
    }
    public void som_kenobi_epo_qetora_action_reward(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.sendSignal(player, "successDone");
    }
    public void som_kenobi_epo_qetora_action_giveQuest2(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.grantQuest(player, "som_kenobi_historian_2");
    }
    public void som_kenobi_epo_qetora_action_reward2(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.sendSignal(player, "piecesRetrieved");
    }
    public void som_kenobi_epo_qetora_action_rewardBattle(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.sendSignal(player, "battlePicked");
    }
    public void som_kenobi_epo_qetora_action_rewardRecon(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.sendSignal(player, "reconPicked");
    }
    public void som_kenobi_epo_qetora_action_rewardAssault(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.sendSignal(player, "assaultPicked");
    }
    public void som_kenobi_epo_qetora_action_rewardRifle(obj_id player, obj_id npc) throws InterruptedException
    {
        obj_id item = static_item.createNewItemFunction("weapon_tow_rifle_03_02", player);
        string_id name = new string_id("static_item_n", "weapon_tow_rifle_03_02");
        prose_package pp = new prose_package();
        pp = prose.setStringId(pp, new string_id("quest/ground/system_message", "placed_in_inventory"));
        pp = prose.setTO(pp, name);
        sendQuestSystemMessage(player, pp);
    }
    public void som_kenobi_epo_qetora_action_rewardCarbine(obj_id player, obj_id npc) throws InterruptedException
    {
        obj_id item = static_item.createNewItemFunction("weapon_tow_carbine_03_01", player);
        string_id name = new string_id("static_item_n", "weapon_tow_carbine_03_01");
        prose_package pp = new prose_package();
        pp = prose.setStringId(pp, new string_id("quest/ground/system_message", "placed_in_inventory"));
        pp = prose.setTO(pp, name);
        sendQuestSystemMessage(player, pp);
    }
    public void som_kenobi_epo_qetora_action_rewardPistol(obj_id player, obj_id npc) throws InterruptedException
    {
        obj_id item = static_item.createNewItemFunction("weapon_tow_pistol_03_01", player);
        string_id name = new string_id("static_item_n", "weapon_tow_pistol_03_01");
        prose_package pp = new prose_package();
        pp = prose.setStringId(pp, new string_id("quest/ground/system_message", "placed_in_inventory"));
        pp = prose.setTO(pp, name);
        sendQuestSystemMessage(player, pp);
    }
    public void som_kenobi_epo_qetora_action_finishQuest1(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.sendSignal(player, "completed");
    }
    public int som_kenobi_epo_qetora_handleBranch2(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_106"))
        {
            if (som_kenobi_epo_qetora_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "nod");
                string_id message = new string_id(c_stringFile, "s_108");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (som_kenobi_epo_qetora_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_134");
                    }
                    utils.setScriptVar(player, "conversation.som_kenobi_epo_qetora.branchId", 3);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.som_kenobi_epo_qetora.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_107"))
        {
            doAnimationAction(player, "shrug_shoulders");
            if (som_kenobi_epo_qetora_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "shake_head_no");
                string_id message = new string_id(c_stringFile, "s_109");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (som_kenobi_epo_qetora_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_124");
                    }
                    utils.setScriptVar(player, "conversation.som_kenobi_epo_qetora.branchId", 11);
                    prose_package pp = new prose_package();
                    pp.stringId = message;
                    pp.actor.set(player);
                    pp.target.set(npc);
                    npcSpeak(player, pp);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.som_kenobi_epo_qetora.branchId");
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
    public int som_kenobi_epo_qetora_handleBranch3(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_134"))
        {
            doAnimationAction(player, "thumb_up");
            if (som_kenobi_epo_qetora_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "nod");
                string_id message = new string_id(c_stringFile, "s_138");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (som_kenobi_epo_qetora_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (som_kenobi_epo_qetora_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (som_kenobi_epo_qetora_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_262");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_270");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_294");
                    }
                    utils.setScriptVar(player, "conversation.som_kenobi_epo_qetora.branchId", 4);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.som_kenobi_epo_qetora.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int som_kenobi_epo_qetora_handleBranch4(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_262"))
        {
            if (som_kenobi_epo_qetora_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_264");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (som_kenobi_epo_qetora_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_266");
                    }
                    utils.setScriptVar(player, "conversation.som_kenobi_epo_qetora.branchId", 5);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.som_kenobi_epo_qetora.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_270"))
        {
            if (som_kenobi_epo_qetora_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_272");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (som_kenobi_epo_qetora_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_274");
                    }
                    utils.setScriptVar(player, "conversation.som_kenobi_epo_qetora.branchId", 7);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.som_kenobi_epo_qetora.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_294"))
        {
            if (som_kenobi_epo_qetora_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_296");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (som_kenobi_epo_qetora_condition__defaultCondition(player, npc))
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
                    utils.setScriptVar(player, "conversation.som_kenobi_epo_qetora.branchId", 9);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.som_kenobi_epo_qetora.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int som_kenobi_epo_qetora_handleBranch5(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_266"))
        {
            som_kenobi_epo_qetora_action_reward2(player, npc);
            if (som_kenobi_epo_qetora_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "bow5");
                doAnimationAction(player, "nod_head_once");
                som_kenobi_epo_qetora_action_rewardRifle(player, npc);
                string_id message = new string_id(c_stringFile, "s_268");
                utils.removeScriptVar(player, "conversation.som_kenobi_epo_qetora.branchId");
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
    public int som_kenobi_epo_qetora_handleBranch7(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_274"))
        {
            som_kenobi_epo_qetora_action_reward2(player, npc);
            if (som_kenobi_epo_qetora_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "bow5");
                doAnimationAction(player, "nod_head_once");
                som_kenobi_epo_qetora_action_rewardCarbine(player, npc);
                string_id message = new string_id(c_stringFile, "s_276");
                utils.removeScriptVar(player, "conversation.som_kenobi_epo_qetora.branchId");
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
    public int som_kenobi_epo_qetora_handleBranch9(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_298"))
        {
            som_kenobi_epo_qetora_action_reward2(player, npc);
            if (som_kenobi_epo_qetora_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "bow5");
                doAnimationAction(player, "nod_head_once");
                som_kenobi_epo_qetora_action_rewardPistol(player, npc);
                string_id message = new string_id(c_stringFile, "s_300");
                utils.removeScriptVar(player, "conversation.som_kenobi_epo_qetora.branchId");
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
    public int som_kenobi_epo_qetora_handleBranch11(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_124"))
        {
            doAnimationAction(player, "tap_foot");
            if (som_kenobi_epo_qetora_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "shake_head_no");
                string_id message = new string_id(c_stringFile, "s_126");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (som_kenobi_epo_qetora_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (som_kenobi_epo_qetora_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (som_kenobi_epo_qetora_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_278");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_286");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_243");
                    }
                    utils.setScriptVar(player, "conversation.som_kenobi_epo_qetora.branchId", 12);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.som_kenobi_epo_qetora.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int som_kenobi_epo_qetora_handleBranch12(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_278"))
        {
            if (som_kenobi_epo_qetora_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_280");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (som_kenobi_epo_qetora_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_282");
                    }
                    utils.setScriptVar(player, "conversation.som_kenobi_epo_qetora.branchId", 13);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.som_kenobi_epo_qetora.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_286"))
        {
            if (som_kenobi_epo_qetora_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_288");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (som_kenobi_epo_qetora_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_290");
                    }
                    utils.setScriptVar(player, "conversation.som_kenobi_epo_qetora.branchId", 15);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.som_kenobi_epo_qetora.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_243"))
        {
            if (som_kenobi_epo_qetora_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_256");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (som_kenobi_epo_qetora_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_258");
                    }
                    utils.setScriptVar(player, "conversation.som_kenobi_epo_qetora.branchId", 17);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.som_kenobi_epo_qetora.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int som_kenobi_epo_qetora_handleBranch13(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_282"))
        {
            som_kenobi_epo_qetora_action_reward2(player, npc);
            if (som_kenobi_epo_qetora_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "bow5");
                doAnimationAction(player, "nod_head_once");
                som_kenobi_epo_qetora_action_rewardRifle(player, npc);
                string_id message = new string_id(c_stringFile, "s_284");
                utils.removeScriptVar(player, "conversation.som_kenobi_epo_qetora.branchId");
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
    public int som_kenobi_epo_qetora_handleBranch15(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_290"))
        {
            som_kenobi_epo_qetora_action_reward2(player, npc);
            if (som_kenobi_epo_qetora_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "bow5");
                doAnimationAction(player, "nod_head_once");
                som_kenobi_epo_qetora_action_rewardCarbine(player, npc);
                string_id message = new string_id(c_stringFile, "s_292");
                utils.removeScriptVar(player, "conversation.som_kenobi_epo_qetora.branchId");
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
    public int som_kenobi_epo_qetora_handleBranch17(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_258"))
        {
            som_kenobi_epo_qetora_action_reward2(player, npc);
            if (som_kenobi_epo_qetora_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "bow5");
                doAnimationAction(player, "nod_head_once");
                som_kenobi_epo_qetora_action_rewardPistol(player, npc);
                string_id message = new string_id(c_stringFile, "s_260");
                utils.removeScriptVar(player, "conversation.som_kenobi_epo_qetora.branchId");
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
    public int som_kenobi_epo_qetora_handleBranch19(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_84"))
        {
            if (som_kenobi_epo_qetora_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "bow5");
                doAnimationAction(player, "goodbye");
                string_id message = new string_id(c_stringFile, "s_86");
                utils.removeScriptVar(player, "conversation.som_kenobi_epo_qetora.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int som_kenobi_epo_qetora_handleBranch21(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_66"))
        {
            if (som_kenobi_epo_qetora_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "explain");
                string_id message = new string_id(c_stringFile, "s_72");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (som_kenobi_epo_qetora_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_73");
                    }
                    utils.setScriptVar(player, "conversation.som_kenobi_epo_qetora.branchId", 22);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.som_kenobi_epo_qetora.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_67"))
        {
            if (som_kenobi_epo_qetora_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "explain");
                string_id message = new string_id(c_stringFile, "s_88");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (som_kenobi_epo_qetora_condition__defaultCondition(player, npc))
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
                    utils.setScriptVar(player, "conversation.som_kenobi_epo_qetora.branchId", 27);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.som_kenobi_epo_qetora.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_68"))
        {
            doAnimationAction(player, "shake_head_no");
            if (som_kenobi_epo_qetora_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "sigh_deeply");
                string_id message = new string_id(c_stringFile, "s_71");
                utils.removeScriptVar(player, "conversation.som_kenobi_epo_qetora.branchId");
                prose_package pp = new prose_package();
                pp.stringId = message;
                pp.actor.set(player);
                pp.target.set(npc);
                npcEndConversationWithMessage(player, pp);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_69"))
        {
            doAnimationAction(player, "dismiss");
            if (som_kenobi_epo_qetora_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "laugh");
                string_id message = new string_id(c_stringFile, "s_81");
                utils.removeScriptVar(player, "conversation.som_kenobi_epo_qetora.branchId");
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
    public int som_kenobi_epo_qetora_handleBranch22(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_73"))
        {
            if (som_kenobi_epo_qetora_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "nod");
                string_id message = new string_id(c_stringFile, "s_74");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (som_kenobi_epo_qetora_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (som_kenobi_epo_qetora_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_75");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_76");
                    }
                    utils.setScriptVar(player, "conversation.som_kenobi_epo_qetora.branchId", 23);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.som_kenobi_epo_qetora.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int som_kenobi_epo_qetora_handleBranch23(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_75"))
        {
            doAnimationAction(player, "nod");
            if (som_kenobi_epo_qetora_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_78");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (som_kenobi_epo_qetora_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_79");
                    }
                    utils.setScriptVar(player, "conversation.som_kenobi_epo_qetora.branchId", 24);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.som_kenobi_epo_qetora.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_76"))
        {
            doAnimationAction(player, "shake_head_no");
            if (som_kenobi_epo_qetora_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "sigh_deeply");
                string_id message = new string_id(c_stringFile, "s_77");
                utils.removeScriptVar(player, "conversation.som_kenobi_epo_qetora.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int som_kenobi_epo_qetora_handleBranch24(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_79"))
        {
            doAnimationAction(player, "nod");
            som_kenobi_epo_qetora_action_giveQuest2(player, npc);
            if (som_kenobi_epo_qetora_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "bow5");
                string_id message = new string_id(c_stringFile, "s_80");
                utils.removeScriptVar(player, "conversation.som_kenobi_epo_qetora.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int som_kenobi_epo_qetora_handleBranch27(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_90"))
        {
            if (som_kenobi_epo_qetora_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "nod");
                string_id message = new string_id(c_stringFile, "s_92");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (som_kenobi_epo_qetora_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (som_kenobi_epo_qetora_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_94");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_102");
                    }
                    utils.setScriptVar(player, "conversation.som_kenobi_epo_qetora.branchId", 28);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.som_kenobi_epo_qetora.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int som_kenobi_epo_qetora_handleBranch28(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_94"))
        {
            doAnimationAction(player, "point_to_self");
            if (som_kenobi_epo_qetora_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_96");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (som_kenobi_epo_qetora_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_98");
                    }
                    utils.setScriptVar(player, "conversation.som_kenobi_epo_qetora.branchId", 29);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.som_kenobi_epo_qetora.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_102"))
        {
            doAnimationAction(player, "shake_head_no");
            if (som_kenobi_epo_qetora_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "sigh_deeply");
                string_id message = new string_id(c_stringFile, "s_104");
                utils.removeScriptVar(player, "conversation.som_kenobi_epo_qetora.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int som_kenobi_epo_qetora_handleBranch29(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_98"))
        {
            doAnimationAction(player, "nod");
            som_kenobi_epo_qetora_action_giveQuest2(player, npc);
            if (som_kenobi_epo_qetora_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "bow5");
                string_id message = new string_id(c_stringFile, "s_100");
                utils.removeScriptVar(player, "conversation.som_kenobi_epo_qetora.branchId");
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
    public int som_kenobi_epo_qetora_handleBranch34(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_89"))
        {
            doAnimationAction(player, "shrug_shoulders");
            if (som_kenobi_epo_qetora_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "rub_chin_thoughtful");
                string_id message = new string_id(c_stringFile, "s_93");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (som_kenobi_epo_qetora_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_97");
                    }
                    utils.setScriptVar(player, "conversation.som_kenobi_epo_qetora.branchId", 35);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.som_kenobi_epo_qetora.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_122"))
        {
            doAnimationAction(player, "shrug_shoulders");
            if (som_kenobi_epo_qetora_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "rub_chin_thoughtful");
                string_id message = new string_id(c_stringFile, "s_125");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (som_kenobi_epo_qetora_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_128");
                    }
                    utils.setScriptVar(player, "conversation.som_kenobi_epo_qetora.branchId", 43);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.som_kenobi_epo_qetora.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int som_kenobi_epo_qetora_handleBranch35(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_97"))
        {
            doAnimationAction(player, "refuse_offer_affection");
            if (som_kenobi_epo_qetora_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "nod_head_multiple");
                string_id message = new string_id(c_stringFile, "s_101");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (som_kenobi_epo_qetora_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (som_kenobi_epo_qetora_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (som_kenobi_epo_qetora_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_210");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_182");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_183");
                    }
                    utils.setScriptVar(player, "conversation.som_kenobi_epo_qetora.branchId", 36);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.som_kenobi_epo_qetora.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int som_kenobi_epo_qetora_handleBranch36(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_210"))
        {
            som_kenobi_epo_qetora_action_reward(player, npc);
            if (som_kenobi_epo_qetora_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "nod");
                string_id message = new string_id(c_stringFile, "s_212");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (som_kenobi_epo_qetora_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_214");
                    }
                    utils.setScriptVar(player, "conversation.som_kenobi_epo_qetora.branchId", 37);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.som_kenobi_epo_qetora.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_182"))
        {
            som_kenobi_epo_qetora_action_reward(player, npc);
            if (som_kenobi_epo_qetora_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "nod");
                string_id message = new string_id(c_stringFile, "s_190");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (som_kenobi_epo_qetora_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_192");
                    }
                    utils.setScriptVar(player, "conversation.som_kenobi_epo_qetora.branchId", 39);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.som_kenobi_epo_qetora.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_183"))
        {
            som_kenobi_epo_qetora_action_reward(player, npc);
            if (som_kenobi_epo_qetora_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "nod");
                string_id message = new string_id(c_stringFile, "s_196");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (som_kenobi_epo_qetora_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_198");
                    }
                    utils.setScriptVar(player, "conversation.som_kenobi_epo_qetora.branchId", 41);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.som_kenobi_epo_qetora.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int som_kenobi_epo_qetora_handleBranch37(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_214"))
        {
            doAnimationAction(player, "thumb_up");
            som_kenobi_epo_qetora_action_rewardAssault(player, npc);
            if (som_kenobi_epo_qetora_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "bow5");
                som_kenobi_epo_qetora_action_finishQuest1(player, npc);
                string_id message = new string_id(c_stringFile, "s_216");
                utils.removeScriptVar(player, "conversation.som_kenobi_epo_qetora.branchId");
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
    public int som_kenobi_epo_qetora_handleBranch39(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_192"))
        {
            doAnimationAction(player, "thumb_up");
            som_kenobi_epo_qetora_action_rewardBattle(player, npc);
            if (som_kenobi_epo_qetora_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "bow5");
                som_kenobi_epo_qetora_action_finishQuest1(player, npc);
                string_id message = new string_id(c_stringFile, "s_194");
                utils.removeScriptVar(player, "conversation.som_kenobi_epo_qetora.branchId");
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
    public int som_kenobi_epo_qetora_handleBranch41(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_198"))
        {
            doAnimationAction(player, "thumb_up");
            som_kenobi_epo_qetora_action_rewardRecon(player, npc);
            if (som_kenobi_epo_qetora_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "bow5");
                som_kenobi_epo_qetora_action_finishQuest1(player, npc);
                string_id message = new string_id(c_stringFile, "s_200");
                utils.removeScriptVar(player, "conversation.som_kenobi_epo_qetora.branchId");
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
    public int som_kenobi_epo_qetora_handleBranch43(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_128"))
        {
            if (som_kenobi_epo_qetora_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "nod_head_multiple");
                som_kenobi_epo_qetora_action_reward(player, npc);
                string_id message = new string_id(c_stringFile, "s_130");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (som_kenobi_epo_qetora_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (som_kenobi_epo_qetora_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (som_kenobi_epo_qetora_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_218");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_226");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_234");
                    }
                    utils.setScriptVar(player, "conversation.som_kenobi_epo_qetora.branchId", 44);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.som_kenobi_epo_qetora.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int som_kenobi_epo_qetora_handleBranch44(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_218"))
        {
            som_kenobi_epo_qetora_action_reward(player, npc);
            if (som_kenobi_epo_qetora_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "nod");
                string_id message = new string_id(c_stringFile, "s_220");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (som_kenobi_epo_qetora_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_222");
                    }
                    utils.setScriptVar(player, "conversation.som_kenobi_epo_qetora.branchId", 45);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.som_kenobi_epo_qetora.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_226"))
        {
            som_kenobi_epo_qetora_action_reward(player, npc);
            if (som_kenobi_epo_qetora_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "nod");
                string_id message = new string_id(c_stringFile, "s_228");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (som_kenobi_epo_qetora_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_230");
                    }
                    utils.setScriptVar(player, "conversation.som_kenobi_epo_qetora.branchId", 47);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.som_kenobi_epo_qetora.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_234"))
        {
            som_kenobi_epo_qetora_action_reward(player, npc);
            if (som_kenobi_epo_qetora_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "nod");
                string_id message = new string_id(c_stringFile, "s_236");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (som_kenobi_epo_qetora_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_238");
                    }
                    utils.setScriptVar(player, "conversation.som_kenobi_epo_qetora.branchId", 49);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.som_kenobi_epo_qetora.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int som_kenobi_epo_qetora_handleBranch45(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_222"))
        {
            doAnimationAction(player, "nod");
            som_kenobi_epo_qetora_action_rewardAssault(player, npc);
            if (som_kenobi_epo_qetora_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "bow5");
                som_kenobi_epo_qetora_action_finishQuest1(player, npc);
                string_id message = new string_id(c_stringFile, "s_224");
                utils.removeScriptVar(player, "conversation.som_kenobi_epo_qetora.branchId");
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
    public int som_kenobi_epo_qetora_handleBranch47(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_230"))
        {
            doAnimationAction(player, "nod");
            som_kenobi_epo_qetora_action_rewardBattle(player, npc);
            if (som_kenobi_epo_qetora_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "bow5");
                som_kenobi_epo_qetora_action_finishQuest1(player, npc);
                string_id message = new string_id(c_stringFile, "s_232");
                utils.removeScriptVar(player, "conversation.som_kenobi_epo_qetora.branchId");
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
    public int som_kenobi_epo_qetora_handleBranch49(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_238"))
        {
            doAnimationAction(player, "nod");
            som_kenobi_epo_qetora_action_rewardRecon(player, npc);
            if (som_kenobi_epo_qetora_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "bow5");
                som_kenobi_epo_qetora_action_finishQuest1(player, npc);
                string_id message = new string_id(c_stringFile, "s_240");
                utils.removeScriptVar(player, "conversation.som_kenobi_epo_qetora.branchId");
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
    public int som_kenobi_epo_qetora_handleBranch51(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_148"))
        {
            if (som_kenobi_epo_qetora_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "bow5");
                doAnimationAction(player, "goodbye");
                string_id message = new string_id(c_stringFile, "s_150");
                utils.removeScriptVar(player, "conversation.som_kenobi_epo_qetora.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int som_kenobi_epo_qetora_handleBranch53(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_154"))
        {
            doAnimationAction(player, "shrug_shoulders");
            if (som_kenobi_epo_qetora_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "bow5");
                string_id message = new string_id(c_stringFile, "s_156");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (som_kenobi_epo_qetora_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_158");
                    }
                    utils.setScriptVar(player, "conversation.som_kenobi_epo_qetora.branchId", 54);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.som_kenobi_epo_qetora.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_180"))
        {
            doAnimationAction(player, "pose_proudly");
            if (som_kenobi_epo_qetora_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "thumbs_up");
                string_id message = new string_id(c_stringFile, "s_184");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (som_kenobi_epo_qetora_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_186");
                    }
                    utils.setScriptVar(player, "conversation.som_kenobi_epo_qetora.branchId", 61);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.som_kenobi_epo_qetora.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_219"))
        {
            doAnimationAction(player, "shake_head_no");
            if (som_kenobi_epo_qetora_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "apologize");
                string_id message = new string_id(c_stringFile, "s_223");
                utils.removeScriptVar(player, "conversation.som_kenobi_epo_qetora.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_227"))
        {
            doAnimationAction(player, "dismiss");
            if (som_kenobi_epo_qetora_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "taken_aback");
                string_id message = new string_id(c_stringFile, "s_231");
                utils.removeScriptVar(player, "conversation.som_kenobi_epo_qetora.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int som_kenobi_epo_qetora_handleBranch54(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_158"))
        {
            doAnimationAction(player, "yawn");
            if (som_kenobi_epo_qetora_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "nod_head_multiple");
                string_id message = new string_id(c_stringFile, "s_160");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (som_kenobi_epo_qetora_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (som_kenobi_epo_qetora_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_162");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_176");
                    }
                    utils.setScriptVar(player, "conversation.som_kenobi_epo_qetora.branchId", 55);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.som_kenobi_epo_qetora.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int som_kenobi_epo_qetora_handleBranch55(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_162"))
        {
            doAnimationAction(player, "rub_chin_thoughtful");
            if (som_kenobi_epo_qetora_condition_isSmuggler(player, npc))
            {
                doAnimationAction(npc, "clap_rousing");
                string_id message = new string_id(c_stringFile, "s_164");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (som_kenobi_epo_qetora_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_166");
                    }
                    utils.setScriptVar(player, "conversation.som_kenobi_epo_qetora.branchId", 56);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.som_kenobi_epo_qetora.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
            if (som_kenobi_epo_qetora_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "clap_rousing");
                string_id message = new string_id(c_stringFile, "s_170");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (som_kenobi_epo_qetora_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_172");
                    }
                    utils.setScriptVar(player, "conversation.som_kenobi_epo_qetora.branchId", 58);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.som_kenobi_epo_qetora.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_176"))
        {
            doAnimationAction(player, "check_wrist_device");
            if (som_kenobi_epo_qetora_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "goodbye");
                string_id message = new string_id(c_stringFile, "s_178");
                utils.removeScriptVar(player, "conversation.som_kenobi_epo_qetora.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int som_kenobi_epo_qetora_handleBranch56(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_166"))
        {
            doAnimationAction(player, "nod");
            som_kenobi_epo_qetora_action_giveQuestSmuggler(player, npc);
            if (som_kenobi_epo_qetora_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "goodbye");
                string_id message = new string_id(c_stringFile, "s_168");
                utils.removeScriptVar(player, "conversation.som_kenobi_epo_qetora.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int som_kenobi_epo_qetora_handleBranch58(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_172"))
        {
            doAnimationAction(player, "nod");
            som_kenobi_epo_qetora_action_giveQuest(player, npc);
            if (som_kenobi_epo_qetora_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "goodbye");
                string_id message = new string_id(c_stringFile, "s_174");
                utils.removeScriptVar(player, "conversation.som_kenobi_epo_qetora.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int som_kenobi_epo_qetora_handleBranch61(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_186"))
        {
            doAnimationAction(player, "yawn");
            if (som_kenobi_epo_qetora_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "apologize");
                string_id message = new string_id(c_stringFile, "s_188");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (som_kenobi_epo_qetora_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (som_kenobi_epo_qetora_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_191");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_211");
                    }
                    utils.setScriptVar(player, "conversation.som_kenobi_epo_qetora.branchId", 62);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.som_kenobi_epo_qetora.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int som_kenobi_epo_qetora_handleBranch62(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_191"))
        {
            doAnimationAction(player, "rub_chin_thoughtful");
            if (som_kenobi_epo_qetora_condition_isSmuggler(player, npc))
            {
                doAnimationAction(npc, "clap_rousing");
                string_id message = new string_id(c_stringFile, "s_195");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (som_kenobi_epo_qetora_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_199");
                    }
                    utils.setScriptVar(player, "conversation.som_kenobi_epo_qetora.branchId", 63);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.som_kenobi_epo_qetora.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
            if (som_kenobi_epo_qetora_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "clap_rousing");
                string_id message = new string_id(c_stringFile, "s_204");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (som_kenobi_epo_qetora_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_206");
                    }
                    utils.setScriptVar(player, "conversation.som_kenobi_epo_qetora.branchId", 65);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.som_kenobi_epo_qetora.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_211"))
        {
            doAnimationAction(player, "dismiss");
            if (som_kenobi_epo_qetora_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "goodbye");
                string_id message = new string_id(c_stringFile, "s_215");
                utils.removeScriptVar(player, "conversation.som_kenobi_epo_qetora.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int som_kenobi_epo_qetora_handleBranch63(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_199"))
        {
            doAnimationAction(player, "nod");
            som_kenobi_epo_qetora_action_giveQuestSmuggler(player, npc);
            if (som_kenobi_epo_qetora_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "goodbye");
                string_id message = new string_id(c_stringFile, "s_202");
                utils.removeScriptVar(player, "conversation.som_kenobi_epo_qetora.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int som_kenobi_epo_qetora_handleBranch65(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_206"))
        {
            doAnimationAction(player, "pose_proudly");
            som_kenobi_epo_qetora_action_giveQuest(player, npc);
            if (som_kenobi_epo_qetora_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "goodbye");
                string_id message = new string_id(c_stringFile, "s_208");
                utils.removeScriptVar(player, "conversation.som_kenobi_epo_qetora.branchId");
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
            detachScript(self, "conversation.som_kenobi_epo_qetora");
        }
        setCondition(self, CONDITION_INTERESTING);
        setCondition(self, CONDITION_CONVERSABLE);
        setInvulnerable(self, true);
        return SCRIPT_CONTINUE;
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        setCondition(self, CONDITION_INTERESTING);
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
        return SCRIPT_CONTINUE;
    }
    public int OnIncapacitated(obj_id self, obj_id killer) throws InterruptedException
    {
        clearCondition(self, CONDITION_CONVERSABLE);
        detachScript(self, "conversation.som_kenobi_epo_qetora");
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
        if (som_kenobi_epo_qetora_condition_finishedQuestAll(player, npc))
        {
            doAnimationAction(npc, "bow5");
            doAnimationAction(player, "greet");
            string_id message = new string_id(c_stringFile, "s_55");
            prose_package pp = new prose_package();
            pp.stringId = message;
            pp.actor.set(player);
            pp.target.set(npc);
            chat.chat(npc, player, null, null, pp);
            return SCRIPT_CONTINUE;
        }
        if (som_kenobi_epo_qetora_condition_rewardQuest2(player, npc))
        {
            doAnimationAction(npc, "bow5");
            doAnimationAction(player, "greet");
            string_id message = new string_id(c_stringFile, "s_105");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (som_kenobi_epo_qetora_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            boolean hasResponse1 = false;
            if (som_kenobi_epo_qetora_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_106");
                }
                if (hasResponse1)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_107");
                }
                utils.setScriptVar(player, "conversation.som_kenobi_epo_qetora.branchId", 2);
                prose_package pp = new prose_package();
                pp.stringId = message;
                pp.actor.set(player);
                pp.target.set(npc);
                npcStartConversation(player, npc, "som_kenobi_epo_qetora", null, pp, responses);
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
        if (som_kenobi_epo_qetora_condition_onQuest2(player, npc))
        {
            doAnimationAction(npc, "bow5");
            string_id message = new string_id(c_stringFile, "s_82");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (som_kenobi_epo_qetora_condition__defaultCondition(player, npc))
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
                utils.setScriptVar(player, "conversation.som_kenobi_epo_qetora.branchId", 19);
                npcStartConversation(player, npc, "som_kenobi_epo_qetora", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (som_kenobi_epo_qetora_condition_finishedQuest1(player, npc))
        {
            doAnimationAction(npc, "bow5");
            doAnimationAction(player, "greet");
            string_id message = new string_id(c_stringFile, "s_65");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (som_kenobi_epo_qetora_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            boolean hasResponse1 = false;
            if (som_kenobi_epo_qetora_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse1 = true;
            }
            boolean hasResponse2 = false;
            if (som_kenobi_epo_qetora_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse2 = true;
            }
            boolean hasResponse3 = false;
            if (som_kenobi_epo_qetora_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_66");
                }
                if (hasResponse1)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_67");
                }
                if (hasResponse2)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_68");
                }
                if (hasResponse3)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_69");
                }
                utils.setScriptVar(player, "conversation.som_kenobi_epo_qetora.branchId", 21);
                prose_package pp = new prose_package();
                pp.stringId = message;
                pp.actor.set(player);
                pp.target.set(npc);
                npcStartConversation(player, npc, "som_kenobi_epo_qetora", null, pp, responses);
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
        if (som_kenobi_epo_qetora_condition_haveDisk(player, npc))
        {
            doAnimationAction(npc, "offer_affection");
            doAnimationAction(player, "greet");
            string_id message = new string_id(c_stringFile, "s_85");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (som_kenobi_epo_qetora_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            boolean hasResponse1 = false;
            if (som_kenobi_epo_qetora_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_89");
                }
                if (hasResponse1)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_122");
                }
                utils.setScriptVar(player, "conversation.som_kenobi_epo_qetora.branchId", 34);
                prose_package pp = new prose_package();
                pp.stringId = message;
                pp.actor.set(player);
                pp.target.set(npc);
                npcStartConversation(player, npc, "som_kenobi_epo_qetora", null, pp, responses);
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
        if (som_kenobi_epo_qetora_condition_onQuest(player, npc))
        {
            doAnimationAction(npc, "bow5");
            string_id message = new string_id(c_stringFile, "s_146");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (som_kenobi_epo_qetora_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_148");
                }
                utils.setScriptVar(player, "conversation.som_kenobi_epo_qetora.branchId", 51);
                npcStartConversation(player, npc, "som_kenobi_epo_qetora", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (som_kenobi_epo_qetora_condition__defaultCondition(player, npc))
        {
            doAnimationAction(npc, "greet");
            doAnimationAction(player, "greet");
            string_id message = new string_id(c_stringFile, "s_152");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (som_kenobi_epo_qetora_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            boolean hasResponse1 = false;
            if (som_kenobi_epo_qetora_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse1 = true;
            }
            boolean hasResponse2 = false;
            if (som_kenobi_epo_qetora_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse2 = true;
            }
            boolean hasResponse3 = false;
            if (som_kenobi_epo_qetora_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_154");
                }
                if (hasResponse1)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_180");
                }
                if (hasResponse2)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_219");
                }
                if (hasResponse3)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_227");
                }
                utils.setScriptVar(player, "conversation.som_kenobi_epo_qetora.branchId", 53);
                npcStartConversation(player, npc, "som_kenobi_epo_qetora", message, responses);
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
        if (!conversationId.equals("som_kenobi_epo_qetora"))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id npc = self;
        int branchId = utils.getIntScriptVar(player, "conversation.som_kenobi_epo_qetora.branchId");
        if (branchId == 2 && som_kenobi_epo_qetora_handleBranch2(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 3 && som_kenobi_epo_qetora_handleBranch3(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 4 && som_kenobi_epo_qetora_handleBranch4(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 5 && som_kenobi_epo_qetora_handleBranch5(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 7 && som_kenobi_epo_qetora_handleBranch7(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 9 && som_kenobi_epo_qetora_handleBranch9(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 11 && som_kenobi_epo_qetora_handleBranch11(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 12 && som_kenobi_epo_qetora_handleBranch12(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 13 && som_kenobi_epo_qetora_handleBranch13(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 15 && som_kenobi_epo_qetora_handleBranch15(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 17 && som_kenobi_epo_qetora_handleBranch17(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 19 && som_kenobi_epo_qetora_handleBranch19(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 21 && som_kenobi_epo_qetora_handleBranch21(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 22 && som_kenobi_epo_qetora_handleBranch22(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 23 && som_kenobi_epo_qetora_handleBranch23(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 24 && som_kenobi_epo_qetora_handleBranch24(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 27 && som_kenobi_epo_qetora_handleBranch27(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 28 && som_kenobi_epo_qetora_handleBranch28(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 29 && som_kenobi_epo_qetora_handleBranch29(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 34 && som_kenobi_epo_qetora_handleBranch34(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 35 && som_kenobi_epo_qetora_handleBranch35(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 36 && som_kenobi_epo_qetora_handleBranch36(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 37 && som_kenobi_epo_qetora_handleBranch37(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 39 && som_kenobi_epo_qetora_handleBranch39(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 41 && som_kenobi_epo_qetora_handleBranch41(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 43 && som_kenobi_epo_qetora_handleBranch43(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 44 && som_kenobi_epo_qetora_handleBranch44(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 45 && som_kenobi_epo_qetora_handleBranch45(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 47 && som_kenobi_epo_qetora_handleBranch47(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 49 && som_kenobi_epo_qetora_handleBranch49(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 51 && som_kenobi_epo_qetora_handleBranch51(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 53 && som_kenobi_epo_qetora_handleBranch53(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 54 && som_kenobi_epo_qetora_handleBranch54(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 55 && som_kenobi_epo_qetora_handleBranch55(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 56 && som_kenobi_epo_qetora_handleBranch56(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 58 && som_kenobi_epo_qetora_handleBranch58(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 61 && som_kenobi_epo_qetora_handleBranch61(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 62 && som_kenobi_epo_qetora_handleBranch62(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 63 && som_kenobi_epo_qetora_handleBranch63(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 65 && som_kenobi_epo_qetora_handleBranch65(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        chat.chat(npc, "Error:  Fell through all branches and responses for OnNpcConversationResponse.");
        utils.removeScriptVar(player, "conversation.som_kenobi_epo_qetora.branchId");
        return SCRIPT_CONTINUE;
    }
}
