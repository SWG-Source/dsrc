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
import script.library.utils;

public class tatooine_eisley_purvis extends script.base_script
{
    public tatooine_eisley_purvis()
    {
    }
    public static String c_stringFile = "conversation/tatooine_eisley_purvis";
    public boolean tatooine_eisley_purvis_condition__defaultCondition(obj_id player, obj_id npc) throws InterruptedException
    {
        return true;
    }
    public boolean tatooine_eisley_purvis_condition_completedRecon(obj_id player, obj_id npc) throws InterruptedException
    {
        return (groundquests.isTaskActive(player, "quest/purvis_recon_four", "return") || groundquests.hasCompletedQuest(player, "quest/purvis_recon_four"));
    }
    public boolean tatooine_eisley_purvis_condition_completedCombatQuests(obj_id player, obj_id npc) throws InterruptedException
    {
        if (groundquests.hasCompletedQuest(player, "quest/purvis_kill_warriors"))
        {
            return true;
        }
        return false;
    }
    public boolean tatooine_eisley_purvis_condition_isReadyForPurvis(obj_id player, obj_id npc) throws InterruptedException
    {
        if (groundquests.hasCompletedQuest(player, "quest/c_ranged_combat_trainer") || (groundquests.isQuestActive(player, "quest/c_ranged_combat_trainer")))
        {
            return true;
        }
        return false;
    }
    public boolean tatooine_eisley_purvis_condition_isOnCombatQuests(obj_id player, obj_id npc) throws InterruptedException
    {
        if (groundquests.isQuestActive(player, "quest/purvis_kill_zealots") || groundquests.isQuestActive(player, "quest/purvis_kill_soldiers") || groundquests.isQuestActive(player, "quest/purvis_kill_warriors"))
        {
            return true;
        }
        return false;
    }
    public boolean tatooine_eisley_purvis_condition_goSeeMayor(obj_id player, obj_id npc) throws InterruptedException
    {
        if (groundquests.isTaskActive(player, "quest/purvis_kill_warriors", "newbie_ranged_e7"))
        {
            return true;
        }
        return false;
    }
    public boolean tatooine_eisley_purvis_condition_needsACombatQuest(obj_id player, obj_id npc) throws InterruptedException
    {
        if (groundquests.hasCompletedQuest(player, "quest/purvis_kill_soldiers") && (!groundquests.hasCompletedQuest(player, "quest/purvis_kill_warriors")))
        {
            if (!groundquests.isQuestActive(player, "quest/purvis_kill_warriors"))
            {
                groundquests.requestGrantQuest(player, "quest/purvis_kill_warriors");
                return true;
            }
        }
        if (groundquests.hasCompletedQuest(player, "quest/purvis_kill_zealots") && (!groundquests.hasCompletedQuest(player, "quest/purvis_kill_soldiers")))
        {
            if (!groundquests.isQuestActive(player, "quest/purvis_kill_soldiers"))
            {
                groundquests.requestGrantQuest(player, "quest/purvis_kill_soldiers");
                return true;
            }
        }
        return false;
    }
    public boolean tatooine_eisley_purvis_condition_pickingPurvisPocket(obj_id player, obj_id npc) throws InterruptedException
    {
        if (groundquests.isTaskActive(player, "quest/steal_speeder", "pickPurvisPocket"))
        {
            return true;
        }
        return false;
    }
    public boolean tatooine_eisley_purvis_condition_completedOneRecon(obj_id player, obj_id npc) throws InterruptedException
    {
        if (groundquests.hasCompletedQuest(player, "quest/purvis_recon_one") || groundquests.isQuestActive(player, "quest/purvis_recon_one"))
        {
            return true;
        }
        return false;
    }
    public boolean tatooine_eisley_purvis_condition_senByMayor(obj_id player, obj_id npc) throws InterruptedException
    {
        return groundquests.isQuestActiveOrComplete(player, "c_ranged_combat_trainer");
    }
    public void tatooine_eisley_purvis_action_grantReconQuest1(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.requestGrantQuest(player, "quest/purvis_recon_one");
    }
    public void tatooine_eisley_purvis_action_grantCombatQuest(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.requestGrantQuest(player, "quest/purvis_kill_zealots");
    }
    public void tatooine_eisley_purvis_action_startTaskThree(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.sendSignal(player, "start_task_three");
    }
    public void tatooine_eisley_purvis_action_sendPocketPickSignal(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.sendSignal(player, "purvisPocketPicked");
    }
    public void tatooine_eisley_purvis_action_checkReconQuestStatus(obj_id player, obj_id npc) throws InterruptedException
    {
        if (groundquests.hasCompletedQuest(player, "quest/purvis_recon_one") && (!groundquests.hasCompletedQuest(player, "quest/purvis_recon_two") && (!groundquests.isQuestActive(player, "quest/purvis_recon_two"))))
        {
            groundquests.requestGrantQuest(player, "quest/purvis_recon_two");
            return;
        }
        if (groundquests.hasCompletedQuest(player, "quest/purvis_recon_two") && (!groundquests.hasCompletedQuest(player, "quest/purvis_recon_three") && (!groundquests.isQuestActive(player, "quest/purvis_recon_three"))))
        {
            groundquests.requestGrantQuest(player, "quest/purvis_recon_three");
            return;
        }
        if (groundquests.hasCompletedQuest(player, "quest/purvis_recon_three") && (!groundquests.hasCompletedQuest(player, "quest/purvis_recon_four") && (!groundquests.isQuestActive(player, "quest/purvis_recon_four"))))
        {
            groundquests.requestGrantQuest(player, "quest/purvis_recon_four");
            return;
        }
        return;
    }
    public void tatooine_eisley_purvis_action_endFromMayor(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.sendSignal(player, "newbie_speak_purvis");
    }
    public void tatooine_eisley_purvis_action_sendCompleteReconSignal(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.sendSignal(player, "purvis_return");
    }
    public int tatooine_eisley_purvis_handleBranch5(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_200"))
        {
            if (tatooine_eisley_purvis_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_205");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (tatooine_eisley_purvis_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (tatooine_eisley_purvis_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_208");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_67");
                    }
                    utils.setScriptVar(player, "conversation.tatooine_eisley_purvis.branchId", 6);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.tatooine_eisley_purvis.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_203"))
        {
            if (tatooine_eisley_purvis_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_205");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (tatooine_eisley_purvis_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (tatooine_eisley_purvis_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_208");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_67");
                    }
                    utils.setScriptVar(player, "conversation.tatooine_eisley_purvis.branchId", 6);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.tatooine_eisley_purvis.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int tatooine_eisley_purvis_handleBranch6(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_208"))
        {
            if (tatooine_eisley_purvis_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_211");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (tatooine_eisley_purvis_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (tatooine_eisley_purvis_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_213");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_69");
                    }
                    utils.setScriptVar(player, "conversation.tatooine_eisley_purvis.branchId", 7);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.tatooine_eisley_purvis.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_67"))
        {
            if (tatooine_eisley_purvis_condition__defaultCondition(player, npc))
            {
                tatooine_eisley_purvis_action_grantCombatQuest(player, npc);
                string_id message = new string_id(c_stringFile, "s_68");
                utils.removeScriptVar(player, "conversation.tatooine_eisley_purvis.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int tatooine_eisley_purvis_handleBranch7(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_213"))
        {
            if (tatooine_eisley_purvis_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_216");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (tatooine_eisley_purvis_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_219");
                    }
                    utils.setScriptVar(player, "conversation.tatooine_eisley_purvis.branchId", 8);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.tatooine_eisley_purvis.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_69"))
        {
            if (tatooine_eisley_purvis_condition__defaultCondition(player, npc))
            {
                tatooine_eisley_purvis_action_grantCombatQuest(player, npc);
                string_id message = new string_id(c_stringFile, "s_68");
                utils.removeScriptVar(player, "conversation.tatooine_eisley_purvis.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int tatooine_eisley_purvis_handleBranch8(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_219"))
        {
            if (tatooine_eisley_purvis_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_221");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (tatooine_eisley_purvis_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (tatooine_eisley_purvis_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_224");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_250");
                    }
                    utils.setScriptVar(player, "conversation.tatooine_eisley_purvis.branchId", 9);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.tatooine_eisley_purvis.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int tatooine_eisley_purvis_handleBranch9(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_224"))
        {
            if (tatooine_eisley_purvis_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_227");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (tatooine_eisley_purvis_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (tatooine_eisley_purvis_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_229");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_237");
                    }
                    utils.setScriptVar(player, "conversation.tatooine_eisley_purvis.branchId", 10);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.tatooine_eisley_purvis.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_250"))
        {
            if (tatooine_eisley_purvis_condition__defaultCondition(player, npc))
            {
                tatooine_eisley_purvis_action_grantCombatQuest(player, npc);
                string_id message = new string_id(c_stringFile, "s_68");
                utils.removeScriptVar(player, "conversation.tatooine_eisley_purvis.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int tatooine_eisley_purvis_handleBranch10(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_229"))
        {
            if (tatooine_eisley_purvis_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_232");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (tatooine_eisley_purvis_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_235");
                    }
                    utils.setScriptVar(player, "conversation.tatooine_eisley_purvis.branchId", 11);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.tatooine_eisley_purvis.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_237"))
        {
            if (tatooine_eisley_purvis_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_240");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (tatooine_eisley_purvis_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (tatooine_eisley_purvis_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_243");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_248");
                    }
                    utils.setScriptVar(player, "conversation.tatooine_eisley_purvis.branchId", 12);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.tatooine_eisley_purvis.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int tatooine_eisley_purvis_handleBranch11(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_235"))
        {
            if (tatooine_eisley_purvis_condition__defaultCondition(player, npc))
            {
                tatooine_eisley_purvis_action_grantCombatQuest(player, npc);
                string_id message = new string_id(c_stringFile, "s_68");
                utils.removeScriptVar(player, "conversation.tatooine_eisley_purvis.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int tatooine_eisley_purvis_handleBranch12(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_243"))
        {
            if (tatooine_eisley_purvis_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_216");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (tatooine_eisley_purvis_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_219");
                    }
                    utils.setScriptVar(player, "conversation.tatooine_eisley_purvis.branchId", 8);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.tatooine_eisley_purvis.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_248"))
        {
            if (tatooine_eisley_purvis_condition__defaultCondition(player, npc))
            {
                tatooine_eisley_purvis_action_grantCombatQuest(player, npc);
                string_id message = new string_id(c_stringFile, "s_68");
                utils.removeScriptVar(player, "conversation.tatooine_eisley_purvis.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int tatooine_eisley_purvis_handleBranch15(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_254"))
        {
            if (tatooine_eisley_purvis_condition__defaultCondition(player, npc))
            {
                tatooine_eisley_purvis_action_endFromMayor(player, npc);
                string_id message = new string_id(c_stringFile, "s_258");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (tatooine_eisley_purvis_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (tatooine_eisley_purvis_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_260");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_272");
                    }
                    utils.setScriptVar(player, "conversation.tatooine_eisley_purvis.branchId", 16);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.tatooine_eisley_purvis.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_256"))
        {
            if (tatooine_eisley_purvis_condition__defaultCondition(player, npc))
            {
                tatooine_eisley_purvis_action_endFromMayor(player, npc);
                string_id message = new string_id(c_stringFile, "s_258");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (tatooine_eisley_purvis_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (tatooine_eisley_purvis_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_260");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_272");
                    }
                    utils.setScriptVar(player, "conversation.tatooine_eisley_purvis.branchId", 16);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.tatooine_eisley_purvis.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_44"))
        {
            if (tatooine_eisley_purvis_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_46");
                utils.removeScriptVar(player, "conversation.tatooine_eisley_purvis.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int tatooine_eisley_purvis_handleBranch16(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_260"))
        {
            if (tatooine_eisley_purvis_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_262");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (tatooine_eisley_purvis_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (tatooine_eisley_purvis_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_264");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_268");
                    }
                    utils.setScriptVar(player, "conversation.tatooine_eisley_purvis.branchId", 17);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.tatooine_eisley_purvis.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_272"))
        {
            if (tatooine_eisley_purvis_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_274");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (tatooine_eisley_purvis_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_276");
                    }
                    utils.setScriptVar(player, "conversation.tatooine_eisley_purvis.branchId", 20);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.tatooine_eisley_purvis.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int tatooine_eisley_purvis_handleBranch17(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_264"))
        {
            if (tatooine_eisley_purvis_condition__defaultCondition(player, npc))
            {
                tatooine_eisley_purvis_action_grantReconQuest1(player, npc);
                string_id message = new string_id(c_stringFile, "s_266");
                utils.removeScriptVar(player, "conversation.tatooine_eisley_purvis.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_268"))
        {
            if (tatooine_eisley_purvis_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_270");
                utils.removeScriptVar(player, "conversation.tatooine_eisley_purvis.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int tatooine_eisley_purvis_handleBranch20(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_276"))
        {
            if (tatooine_eisley_purvis_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_262");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (tatooine_eisley_purvis_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (tatooine_eisley_purvis_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_264");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_268");
                    }
                    utils.setScriptVar(player, "conversation.tatooine_eisley_purvis.branchId", 17);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.tatooine_eisley_purvis.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        if ((!isMob(self)) || (isPlayer(self)))
        {
            detachScript(self, "conversation.tatooine_eisley_purvis");
        }
        setCondition(self, CONDITION_CONVERSABLE);
        setInvulnerable(self, true);
        setCondition(self, CONDITION_INTERESTING);
        ai_lib.setDefaultCalmBehavior(self, ai_lib.BEHAVIOR_SENTINEL);
        return SCRIPT_CONTINUE;
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        setCondition(self, CONDITION_CONVERSABLE);
        setInvulnerable(self, true);
        setCondition(self, CONDITION_INTERESTING);
        ai_lib.setDefaultCalmBehavior(self, ai_lib.BEHAVIOR_SENTINEL);
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
        detachScript(self, "conversation.tatooine_eisley_purvis");
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
        if (tatooine_eisley_purvis_condition_completedCombatQuests(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_58");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        if (tatooine_eisley_purvis_condition_goSeeMayor(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_132");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        if (tatooine_eisley_purvis_condition_needsACombatQuest(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_136");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        if (tatooine_eisley_purvis_condition_isOnCombatQuests(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_70");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        if (tatooine_eisley_purvis_condition_completedRecon(player, npc))
        {
            tatooine_eisley_purvis_action_sendCompleteReconSignal(player, npc);
            string_id message = new string_id(c_stringFile, "s_197");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (tatooine_eisley_purvis_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            boolean hasResponse1 = false;
            if (tatooine_eisley_purvis_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_200");
                }
                if (hasResponse1)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_203");
                }
                utils.setScriptVar(player, "conversation.tatooine_eisley_purvis.branchId", 5);
                npcStartConversation(player, npc, "tatooine_eisley_purvis", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (tatooine_eisley_purvis_condition_completedOneRecon(player, npc))
        {
            tatooine_eisley_purvis_action_checkReconQuestStatus(player, npc);
            string_id message = new string_id(c_stringFile, "s_66");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        if (tatooine_eisley_purvis_condition__defaultCondition(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_252");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (tatooine_eisley_purvis_condition_senByMayor(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            boolean hasResponse1 = false;
            if (tatooine_eisley_purvis_condition_senByMayor(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse1 = true;
            }
            boolean hasResponse2 = false;
            if (!tatooine_eisley_purvis_condition_senByMayor(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_254");
                }
                if (hasResponse1)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_256");
                }
                if (hasResponse2)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_44");
                }
                utils.setScriptVar(player, "conversation.tatooine_eisley_purvis.branchId", 15);
                npcStartConversation(player, npc, "tatooine_eisley_purvis", message, responses);
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
        if (!conversationId.equals("tatooine_eisley_purvis"))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id npc = self;
        int branchId = utils.getIntScriptVar(player, "conversation.tatooine_eisley_purvis.branchId");
        if (branchId == 5 && tatooine_eisley_purvis_handleBranch5(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 6 && tatooine_eisley_purvis_handleBranch6(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 7 && tatooine_eisley_purvis_handleBranch7(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 8 && tatooine_eisley_purvis_handleBranch8(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 9 && tatooine_eisley_purvis_handleBranch9(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 10 && tatooine_eisley_purvis_handleBranch10(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 11 && tatooine_eisley_purvis_handleBranch11(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 12 && tatooine_eisley_purvis_handleBranch12(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 15 && tatooine_eisley_purvis_handleBranch15(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 16 && tatooine_eisley_purvis_handleBranch16(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 17 && tatooine_eisley_purvis_handleBranch17(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 20 && tatooine_eisley_purvis_handleBranch20(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        chat.chat(npc, "Error:  Fell through all branches and responses for OnNpcConversationResponse.");
        utils.removeScriptVar(player, "conversation.tatooine_eisley_purvis.branchId");
        return SCRIPT_CONTINUE;
    }
}
