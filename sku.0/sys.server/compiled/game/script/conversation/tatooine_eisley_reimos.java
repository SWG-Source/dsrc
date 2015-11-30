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

public class tatooine_eisley_reimos extends script.base_script
{
    public tatooine_eisley_reimos()
    {
    }
    public static String c_stringFile = "conversation/tatooine_eisley_reimos";
    public boolean tatooine_eisley_reimos_condition__defaultCondition(obj_id player, obj_id npc) throws InterruptedException
    {
        return true;
    }
    public boolean tatooine_eisley_reimos_condition_sdQuestComplete(obj_id player, obj_id npc) throws InterruptedException
    {
        int questId1 = questGetQuestId("quest/tatooine_eisley_special_delivery");
        boolean OnTask = (questIsQuestComplete(questId1, player));
        return OnTask;
    }
    public boolean tatooine_eisley_reimos_condition_nikoQuestComplete(obj_id player, obj_id npc) throws InterruptedException
    {
        int questId1 = questGetQuestId("quest/tatooine_eisley_nikos_coins");
        boolean OnTask = (questIsQuestComplete(questId1, player));
        return OnTask;
    }
    public boolean tatooine_eisley_reimos_condition_diQuestComplete(obj_id player, obj_id npc) throws InterruptedException
    {
        int questId1 = questGetQuestId("quest/tatooine_eisley_digital_infection");
        boolean OnTask = (questIsQuestComplete(questId1, player));
        return OnTask;
    }
    public boolean tatooine_eisley_reimos_condition_dpOnTask(obj_id player, obj_id npc) throws InterruptedException
    {
        int questId1 = questGetQuestId("quest/tatooine_eisley_data_purge");
        int tat_eisley_infection_e1 = groundquests.getTaskId(questId1, "tat_eisley_datapurge_e1");
        boolean onTask = questIsTaskActive(questId1, tat_eisley_infection_e1, player);
        return onTask;
    }
    public boolean tatooine_eisley_reimos_condition_diOnTask(obj_id player, obj_id npc) throws InterruptedException
    {
        int questId1 = questGetQuestId("quest/tatooine_eisley_digital_infection");
        int tat_eisley_infection_e1 = groundquests.getTaskId(questId1, "tat_eisley_infection_e1");
        boolean onTask = questIsTaskActive(questId1, tat_eisley_infection_e1, player);
        return onTask;
    }
    public boolean tatooine_eisley_reimos_condition_diTaskComplete(obj_id player, obj_id npc) throws InterruptedException
    {
        int questId1 = questGetQuestId("quest/tatooine_eisley_digital_infection");
        int tat_eisley_infection_e2 = groundquests.getTaskId(questId1, "tat_eisley_infection_e2");
        boolean onTask = questIsTaskActive(questId1, tat_eisley_infection_e2, player);
        return onTask;
    }
    public boolean tatooine_eisley_reimos_condition_dpQuestComplete(obj_id player, obj_id npc) throws InterruptedException
    {
        int questId1 = questGetQuestId("quest/tatooine_eisley_data_purge");
        boolean OnTask = (questIsQuestComplete(questId1, player));
        return OnTask;
    }
    public boolean tatooine_eisley_reimos_condition_hiddenQuestComplete(obj_id player, obj_id npc) throws InterruptedException
    {
        int questId1 = questGetQuestId("quest/tatooine_eisley_hidden_agenda");
        boolean OnTask = (questIsQuestComplete(questId1, player));
        return OnTask;
    }
    public boolean tatooine_eisley_reimos_condition_hiddenOnTask(obj_id player, obj_id npc) throws InterruptedException
    {
        int questId1 = questGetQuestId("quest/tatooine_eisley_hidden_agenda");
        int tat_eisley_hidden_e1 = groundquests.getTaskId(questId1, "tat_eisley_hidden_e1");
        boolean onTask = questIsTaskActive(questId1, tat_eisley_hidden_e1, player);
        return onTask;
    }
    public boolean tatooine_eisley_reimos_condition_hiddenTaskComplete(obj_id player, obj_id npc) throws InterruptedException
    {
        int questId1 = questGetQuestId("quest/tatooine_eisley_hidden_agenda");
        int tat_eisley_hidden_e2 = groundquests.getTaskId(questId1, "tat_eisley_hidden_e2");
        boolean onTask = questIsTaskActive(questId1, tat_eisley_hidden_e2, player);
        return onTask;
    }
    public boolean tatooine_eisley_reimos_condition_epiQuestComplete(obj_id player, obj_id npc) throws InterruptedException
    {
        int questId1 = questGetQuestId("quest/tatooine_eisley_epidemic");
        boolean OnTask = (questIsQuestComplete(questId1, player));
        return OnTask;
    }
    public boolean tatooine_eisley_reimos_condition_heirQuestComlete(obj_id player, obj_id npc) throws InterruptedException
    {
        int questId1 = questGetQuestId("quest/tatooine_eisley_heirloom");
        boolean OnTask = (questIsQuestComplete(questId1, player));
        return OnTask;
    }
    public boolean tatooine_eisley_reimos_condition_foeQuestComplete(obj_id player, obj_id npc) throws InterruptedException
    {
        int questId1 = questGetQuestId("quest/tatooine_eisley_faceevil");
        boolean OnTask = (questIsQuestComplete(questId1, player));
        return OnTask;
    }
    public boolean tatooine_eisley_reimos_condition_dbOnTask(obj_id player, obj_id npc) throws InterruptedException
    {
        int questId1 = questGetQuestId("quest/tatooine_eisley_drensbounty");
        int tat_eisley_drensbounty_e1 = groundquests.getTaskId(questId1, "tat_eisley_drensbounty_e1");
        boolean onTask = questIsTaskActive(questId1, tat_eisley_drensbounty_e1, player);
        return onTask;
    }
    public boolean tatooine_eisley_reimos_condition_dpTaskComplete(obj_id player, obj_id npc) throws InterruptedException
    {
        int questId1 = questGetQuestId("quest/tatooine_eisley_data_purge");
        int tat_eisley_datapurge_e2 = groundquests.getTaskId(questId1, "tat_eisley_datapurge_e2");
        boolean onTask = questIsTaskActive(questId1, tat_eisley_datapurge_e2, player);
        return onTask;
    }
    public boolean tatooine_eisley_reimos_condition_blocker(obj_id player, obj_id npc) throws InterruptedException
    {
        return groundquests.isQuestDisabled("tatooine_eisley_digital_infection");
    }
    public boolean tatooine_eisley_reimos_condition_boneQuestComplete(obj_id player, obj_id npc) throws InterruptedException
    {
        int questId1 = questGetQuestId("quest/tatooine_eisley_bone_gnasher");
        boolean OnTask = (questIsQuestComplete(questId1, player));
        return OnTask;
    }
    public boolean tatooine_eisley_reimos_condition_gotoReimosDIComplete(obj_id player, obj_id npc) throws InterruptedException
    {
        int questId1 = questGetQuestId("quest/tatooine_eisley_gotoreimos");
        boolean OnTask = (questIsQuestComplete(questId1, player));
        return OnTask;
    }
    public boolean tatooine_eisley_reimos_condition_returnReimosComplete(obj_id player, obj_id npc) throws InterruptedException
    {
        int questId1 = questGetQuestId("quest/tatooine_eisley_returnreimos");
        boolean OnTask = (questIsQuestComplete(questId1, player));
        return OnTask;
    }
    public boolean tatooine_eisley_reimos_condition_answersComplete(obj_id player, obj_id npc) throws InterruptedException
    {
        int questId1 = questGetQuestId("quest/tatooine_eisley_answers");
        boolean OnTask = (questIsQuestComplete(questId1, player));
        return OnTask;
    }
    public boolean tatooine_eisley_reimos_condition_dbQuestComplete(obj_id player, obj_id npc) throws InterruptedException
    {
        int questId1 = questGetQuestId("quest/tatooine_eisley_drensbounty");
        boolean OnTask = (questIsQuestComplete(questId1, player));
        return OnTask;
    }
    public boolean tatooine_eisley_reimos_condition_answersActive(obj_id player, obj_id npc) throws InterruptedException
    {
        return groundquests.isQuestActive(player, "quest/tatooine_eisley_answers");
    }
    public void tatooine_eisley_reimos_action_facePlayer(obj_id player, obj_id npc) throws InterruptedException
    {
        faceTo(npc, player);
    }
    public void tatooine_eisley_reimos_action_grantDBQuest(obj_id player, obj_id npc) throws InterruptedException
    {
        int questId = questGetQuestId("quest/tatooine_eisley_drensbounty");
        groundquests.grantQuest(questId, player, npc, true);
    }
    public void tatooine_eisley_reimos_action_grantDIQuest(obj_id player, obj_id npc) throws InterruptedException
    {
        int questId = questGetQuestId("quest/tatooine_eisley_digital_infection");
        groundquests.grantQuest(questId, player, npc, true);
    }
    public void tatooine_eisley_reimos_action_grantHAQuest(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.sendSignal(player, "tat_eisley_infection_e2");
        int questId = questGetQuestId("quest/tatooine_eisley_hidden_agenda");
        groundquests.grantQuest(questId, player, npc, true);
    }
    public void tatooine_eisley_reimos_action_grantDELQuest(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.sendSignal(player, "tat_eisley_hidden_e2");
        int questId = questGetQuestId("quest/tatooine_eisley_data_purge");
        groundquests.grantQuest(questId, player, npc, true);
    }
    public void tatooine_eisley_reimos_action_signalLegacyUpdateAigorn(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.sendSignal(player, "tat_eisley_legacy_e2");
    }
    public void tatooine_eisley_reimos_action_signalLegacyUpdateKashi(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.sendSignal(player, "tat_eisley_legacy_e4");
    }
    public void tatooine_eisley_reimos_action_signalDIReward(obj_id player, obj_id npc) throws InterruptedException
    {
        faceTo(npc, player);
        groundquests.sendSignal(player, "tat_eisley_infection_e2");
    }
    public void tatooine_eisley_reimos_action_signalHAReward(obj_id player, obj_id npc) throws InterruptedException
    {
        faceTo(npc, player);
        groundquests.sendSignal(player, "tat_eisley_hidden_e2");
    }
    public void tatooine_eisley_reimos_action_signalDPReward(obj_id player, obj_id npc) throws InterruptedException
    {
        faceTo(npc, player);
        groundquests.sendSignal(player, "tat_eisley_datapurge_e2");
    }
    public void tatooine_eisley_reimos_action_grantGotoTori(obj_id player, obj_id npc) throws InterruptedException
    {
        int questId = questGetQuestId("quest/tatooine_eisley_gototori");
        groundquests.grantQuest(questId, player, npc, true);
    }
    public void tatooine_eisley_reimos_action_signalGotoReimos(obj_id player, obj_id npc) throws InterruptedException
    {
        faceTo(npc, player);
        groundquests.sendSignal(player, "tat_dunir_reimos_e1");
    }
    public void tatooine_eisley_reimos_action_signalReturnReimos(obj_id player, obj_id npc) throws InterruptedException
    {
        faceTo(npc, player);
        groundquests.sendSignal(player, "tat_tori_reimos_e1");
    }
    public void tatooine_eisley_reimos_action_grantAnswers(obj_id player, obj_id npc) throws InterruptedException
    {
        if (!groundquests.isQuestActive(player, "quest/tatooine_eisley_answers"))
        {
            
        }
        groundquests.grantQuest(player, "quest/tatooine_eisley_answers");
    }
    public void tatooine_eisley_reimos_action_grantFaceofEvil(obj_id player, obj_id npc) throws InterruptedException
    {
        int questId = questGetQuestId("quest/tatooine_eisley_faceevil");
        groundquests.grantQuest(questId, player, npc, true);
    }
    public void tatooine_eisley_reimos_action_action0001(obj_id player, obj_id npc) throws InterruptedException
    {
    }
    public int tatooine_eisley_reimos_handleBranch1(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_165"))
        {
            if (tatooine_eisley_reimos_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_166");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (tatooine_eisley_reimos_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_167");
                    }
                    utils.setScriptVar(player, "conversation.tatooine_eisley_reimos.branchId", 2);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.tatooine_eisley_reimos.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int tatooine_eisley_reimos_handleBranch2(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_167"))
        {
            if (tatooine_eisley_reimos_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "laugh");
                string_id message = new string_id(c_stringFile, "s_168");
                utils.removeScriptVar(player, "conversation.tatooine_eisley_reimos.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int tatooine_eisley_reimos_handleBranch4(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_300"))
        {
            if (tatooine_eisley_reimos_condition_answersComplete(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_301");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (tatooine_eisley_reimos_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_303");
                    }
                    utils.setScriptVar(player, "conversation.tatooine_eisley_reimos.branchId", 5);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.tatooine_eisley_reimos.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
            if (tatooine_eisley_reimos_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_302");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (tatooine_eisley_reimos_condition__defaultCondition(player, npc))
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
                    utils.setScriptVar(player, "conversation.tatooine_eisley_reimos.branchId", 7);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.tatooine_eisley_reimos.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int tatooine_eisley_reimos_handleBranch5(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_303"))
        {
            if (tatooine_eisley_reimos_condition__defaultCondition(player, npc))
            {
                tatooine_eisley_reimos_action_grantFaceofEvil(player, npc);
                string_id message = new string_id(c_stringFile, "s_304");
                utils.removeScriptVar(player, "conversation.tatooine_eisley_reimos.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int tatooine_eisley_reimos_handleBranch7(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_305"))
        {
            if (tatooine_eisley_reimos_condition__defaultCondition(player, npc))
            {
                tatooine_eisley_reimos_action_grantAnswers(player, npc);
                string_id message = new string_id(c_stringFile, "s_306");
                utils.removeScriptVar(player, "conversation.tatooine_eisley_reimos.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int tatooine_eisley_reimos_handleBranch9(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_192"))
        {
            if (tatooine_eisley_reimos_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_193");
                utils.removeScriptVar(player, "conversation.tatooine_eisley_reimos.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int tatooine_eisley_reimos_handleBranch11(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_290"))
        {
            if (tatooine_eisley_reimos_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_291");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (tatooine_eisley_reimos_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_292");
                    }
                    utils.setScriptVar(player, "conversation.tatooine_eisley_reimos.branchId", 12);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.tatooine_eisley_reimos.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int tatooine_eisley_reimos_handleBranch12(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_292"))
        {
            if (tatooine_eisley_reimos_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_293");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (tatooine_eisley_reimos_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_262");
                    }
                    utils.setScriptVar(player, "conversation.tatooine_eisley_reimos.branchId", 16);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.tatooine_eisley_reimos.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int tatooine_eisley_reimos_handleBranch13(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_262"))
        {
            if (tatooine_eisley_reimos_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_263");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (tatooine_eisley_reimos_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_264");
                    }
                    utils.setScriptVar(player, "conversation.tatooine_eisley_reimos.branchId", 17);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.tatooine_eisley_reimos.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int tatooine_eisley_reimos_handleBranch14(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_183"))
        {
            if (tatooine_eisley_reimos_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_184");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (tatooine_eisley_reimos_condition__defaultCondition(player, npc))
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
                    utils.setScriptVar(player, "conversation.tatooine_eisley_reimos.branchId", 15);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.tatooine_eisley_reimos.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int tatooine_eisley_reimos_handleBranch15(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_186"))
        {
            if (tatooine_eisley_reimos_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_187");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (tatooine_eisley_reimos_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_262");
                    }
                    utils.setScriptVar(player, "conversation.tatooine_eisley_reimos.branchId", 16);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.tatooine_eisley_reimos.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int tatooine_eisley_reimos_handleBranch16(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_262"))
        {
            if (tatooine_eisley_reimos_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_263");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (tatooine_eisley_reimos_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_264");
                    }
                    utils.setScriptVar(player, "conversation.tatooine_eisley_reimos.branchId", 17);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.tatooine_eisley_reimos.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int tatooine_eisley_reimos_handleBranch17(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_264"))
        {
            tatooine_eisley_reimos_action_grantDBQuest(player, npc);
            if (tatooine_eisley_reimos_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_265");
                utils.removeScriptVar(player, "conversation.tatooine_eisley_reimos.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int tatooine_eisley_reimos_handleBranch19(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_145"))
        {
            if (tatooine_eisley_reimos_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_146");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (tatooine_eisley_reimos_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_147");
                    }
                    utils.setScriptVar(player, "conversation.tatooine_eisley_reimos.branchId", 20);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.tatooine_eisley_reimos.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int tatooine_eisley_reimos_handleBranch20(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_147"))
        {
            if (tatooine_eisley_reimos_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_148");
                utils.removeScriptVar(player, "conversation.tatooine_eisley_reimos.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int tatooine_eisley_reimos_handleBranch22(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_285"))
        {
            if (tatooine_eisley_reimos_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_286");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (tatooine_eisley_reimos_condition__defaultCondition(player, npc))
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
                    utils.setScriptVar(player, "conversation.tatooine_eisley_reimos.branchId", 23);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.tatooine_eisley_reimos.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int tatooine_eisley_reimos_handleBranch23(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_287"))
        {
            if (tatooine_eisley_reimos_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_288");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (tatooine_eisley_reimos_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_442");
                    }
                    utils.setScriptVar(player, "conversation.tatooine_eisley_reimos.branchId", 27);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.tatooine_eisley_reimos.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int tatooine_eisley_reimos_handleBranch24(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_442"))
        {
            if (tatooine_eisley_reimos_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_444");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (tatooine_eisley_reimos_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_458");
                    }
                    utils.setScriptVar(player, "conversation.tatooine_eisley_reimos.branchId", 28);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.tatooine_eisley_reimos.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int tatooine_eisley_reimos_handleBranch25(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_384"))
        {
            if (tatooine_eisley_reimos_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_386");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (tatooine_eisley_reimos_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_388");
                    }
                    utils.setScriptVar(player, "conversation.tatooine_eisley_reimos.branchId", 26);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.tatooine_eisley_reimos.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_412"))
        {
            if (tatooine_eisley_reimos_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_414");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (tatooine_eisley_reimos_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_416");
                    }
                    utils.setScriptVar(player, "conversation.tatooine_eisley_reimos.branchId", 31);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.tatooine_eisley_reimos.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int tatooine_eisley_reimos_handleBranch26(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_388"))
        {
            if (tatooine_eisley_reimos_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_440");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (tatooine_eisley_reimos_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_442");
                    }
                    utils.setScriptVar(player, "conversation.tatooine_eisley_reimos.branchId", 27);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.tatooine_eisley_reimos.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int tatooine_eisley_reimos_handleBranch27(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_442"))
        {
            if (tatooine_eisley_reimos_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_444");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (tatooine_eisley_reimos_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_458");
                    }
                    utils.setScriptVar(player, "conversation.tatooine_eisley_reimos.branchId", 28);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.tatooine_eisley_reimos.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int tatooine_eisley_reimos_handleBranch28(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_458"))
        {
            if (tatooine_eisley_reimos_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_460");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (tatooine_eisley_reimos_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_462");
                    }
                    utils.setScriptVar(player, "conversation.tatooine_eisley_reimos.branchId", 29);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.tatooine_eisley_reimos.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int tatooine_eisley_reimos_handleBranch29(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_462"))
        {
            tatooine_eisley_reimos_action_grantDELQuest(player, npc);
            if (tatooine_eisley_reimos_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_464");
                utils.removeScriptVar(player, "conversation.tatooine_eisley_reimos.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int tatooine_eisley_reimos_handleBranch31(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_416"))
        {
            if (tatooine_eisley_reimos_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_418");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (tatooine_eisley_reimos_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_420");
                    }
                    utils.setScriptVar(player, "conversation.tatooine_eisley_reimos.branchId", 32);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.tatooine_eisley_reimos.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int tatooine_eisley_reimos_handleBranch32(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_420"))
        {
            if (tatooine_eisley_reimos_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_422");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (tatooine_eisley_reimos_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_424");
                    }
                    utils.setScriptVar(player, "conversation.tatooine_eisley_reimos.branchId", 33);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.tatooine_eisley_reimos.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int tatooine_eisley_reimos_handleBranch33(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_424"))
        {
            if (tatooine_eisley_reimos_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_426");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (tatooine_eisley_reimos_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_428");
                    }
                    utils.setScriptVar(player, "conversation.tatooine_eisley_reimos.branchId", 34);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.tatooine_eisley_reimos.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int tatooine_eisley_reimos_handleBranch34(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_428"))
        {
            if (tatooine_eisley_reimos_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_466");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (tatooine_eisley_reimos_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_468");
                    }
                    utils.setScriptVar(player, "conversation.tatooine_eisley_reimos.branchId", 35);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.tatooine_eisley_reimos.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int tatooine_eisley_reimos_handleBranch35(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_468"))
        {
            tatooine_eisley_reimos_action_grantDELQuest(player, npc);
            if (tatooine_eisley_reimos_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_470");
                utils.removeScriptVar(player, "conversation.tatooine_eisley_reimos.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int tatooine_eisley_reimos_handleBranch37(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_138"))
        {
            if (tatooine_eisley_reimos_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_139");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (tatooine_eisley_reimos_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_140");
                    }
                    utils.setScriptVar(player, "conversation.tatooine_eisley_reimos.branchId", 38);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.tatooine_eisley_reimos.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int tatooine_eisley_reimos_handleBranch38(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_140"))
        {
            if (tatooine_eisley_reimos_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_141");
                utils.removeScriptVar(player, "conversation.tatooine_eisley_reimos.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int tatooine_eisley_reimos_handleBranch40(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_282"))
        {
            if (tatooine_eisley_reimos_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_283");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (tatooine_eisley_reimos_condition__defaultCondition(player, npc))
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
                    utils.setScriptVar(player, "conversation.tatooine_eisley_reimos.branchId", 43);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.tatooine_eisley_reimos.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int tatooine_eisley_reimos_handleBranch41(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_358"))
        {
            if (tatooine_eisley_reimos_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_360");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (tatooine_eisley_reimos_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (tatooine_eisley_reimos_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_362");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_374");
                    }
                    utils.setScriptVar(player, "conversation.tatooine_eisley_reimos.branchId", 44);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.tatooine_eisley_reimos.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int tatooine_eisley_reimos_handleBranch42(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_91"))
        {
            if (tatooine_eisley_reimos_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_94");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (tatooine_eisley_reimos_condition__defaultCondition(player, npc))
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
                    utils.setScriptVar(player, "conversation.tatooine_eisley_reimos.branchId", 43);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.tatooine_eisley_reimos.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int tatooine_eisley_reimos_handleBranch43(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_358"))
        {
            if (tatooine_eisley_reimos_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_360");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (tatooine_eisley_reimos_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (tatooine_eisley_reimos_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_362");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_374");
                    }
                    utils.setScriptVar(player, "conversation.tatooine_eisley_reimos.branchId", 44);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.tatooine_eisley_reimos.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int tatooine_eisley_reimos_handleBranch44(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_362"))
        {
            if (tatooine_eisley_reimos_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_364");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (tatooine_eisley_reimos_condition__defaultCondition(player, npc))
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
                    utils.setScriptVar(player, "conversation.tatooine_eisley_reimos.branchId", 45);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.tatooine_eisley_reimos.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_374"))
        {
            if (tatooine_eisley_reimos_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_376");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (tatooine_eisley_reimos_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_378");
                    }
                    utils.setScriptVar(player, "conversation.tatooine_eisley_reimos.branchId", 48);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.tatooine_eisley_reimos.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int tatooine_eisley_reimos_handleBranch45(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_366"))
        {
            if (tatooine_eisley_reimos_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_368");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (tatooine_eisley_reimos_condition__defaultCondition(player, npc))
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
                    utils.setScriptVar(player, "conversation.tatooine_eisley_reimos.branchId", 46);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.tatooine_eisley_reimos.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int tatooine_eisley_reimos_handleBranch46(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_370"))
        {
            if (tatooine_eisley_reimos_condition__defaultCondition(player, npc))
            {
                tatooine_eisley_reimos_action_grantHAQuest(player, npc);
                string_id message = new string_id(c_stringFile, "s_372");
                utils.removeScriptVar(player, "conversation.tatooine_eisley_reimos.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int tatooine_eisley_reimos_handleBranch48(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_378"))
        {
            if (tatooine_eisley_reimos_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_380");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (tatooine_eisley_reimos_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_382");
                    }
                    utils.setScriptVar(player, "conversation.tatooine_eisley_reimos.branchId", 49);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.tatooine_eisley_reimos.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int tatooine_eisley_reimos_handleBranch49(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_382"))
        {
            if (tatooine_eisley_reimos_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_364");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (tatooine_eisley_reimos_condition__defaultCondition(player, npc))
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
                    utils.setScriptVar(player, "conversation.tatooine_eisley_reimos.branchId", 45);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.tatooine_eisley_reimos.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int tatooine_eisley_reimos_handleBranch50(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_219"))
        {
            if (tatooine_eisley_reimos_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_221");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (tatooine_eisley_reimos_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (tatooine_eisley_reimos_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_223");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_225");
                    }
                    utils.setScriptVar(player, "conversation.tatooine_eisley_reimos.branchId", 51);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.tatooine_eisley_reimos.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int tatooine_eisley_reimos_handleBranch51(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_223"))
        {
            if (tatooine_eisley_reimos_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "wave2");
                tatooine_eisley_reimos_action_grantGotoTori(player, npc);
                string_id message = new string_id(c_stringFile, "s_229");
                utils.removeScriptVar(player, "conversation.tatooine_eisley_reimos.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_225"))
        {
            if (tatooine_eisley_reimos_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "wave1");
                string_id message = new string_id(c_stringFile, "s_227");
                utils.removeScriptVar(player, "conversation.tatooine_eisley_reimos.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int tatooine_eisley_reimos_handleBranch54(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_267"))
        {
            if (tatooine_eisley_reimos_condition__defaultCondition(player, npc))
            {
                tatooine_eisley_reimos_action_grantGotoTori(player, npc);
                string_id message = new string_id(c_stringFile, "s_268");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (tatooine_eisley_reimos_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_269");
                    }
                    utils.setScriptVar(player, "conversation.tatooine_eisley_reimos.branchId", 55);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.tatooine_eisley_reimos.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int tatooine_eisley_reimos_handleBranch55(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_269"))
        {
            if (tatooine_eisley_reimos_condition__defaultCondition(player, npc))
            {
                tatooine_eisley_reimos_action_signalDIReward(player, npc);
                string_id message = new string_id(c_stringFile, "s_270");
                utils.removeScriptVar(player, "conversation.tatooine_eisley_reimos.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int tatooine_eisley_reimos_handleBranch57(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_160"))
        {
            if (tatooine_eisley_reimos_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_162");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (tatooine_eisley_reimos_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_164");
                    }
                    utils.setScriptVar(player, "conversation.tatooine_eisley_reimos.branchId", 58);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.tatooine_eisley_reimos.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int tatooine_eisley_reimos_handleBranch58(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_164"))
        {
            if (tatooine_eisley_reimos_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_180");
                utils.removeScriptVar(player, "conversation.tatooine_eisley_reimos.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int tatooine_eisley_reimos_handleBranch60(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_276"))
        {
            if (tatooine_eisley_reimos_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_277");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (tatooine_eisley_reimos_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_280");
                    }
                    utils.setScriptVar(player, "conversation.tatooine_eisley_reimos.branchId", 64);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.tatooine_eisley_reimos.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int tatooine_eisley_reimos_handleBranch61(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_280"))
        {
            if (tatooine_eisley_reimos_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_297");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (tatooine_eisley_reimos_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (tatooine_eisley_reimos_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_344");
                    }
                    utils.setScriptVar(player, "conversation.tatooine_eisley_reimos.branchId", 65);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.tatooine_eisley_reimos.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int tatooine_eisley_reimos_handleBranch62(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_198"))
        {
            if (tatooine_eisley_reimos_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_200");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (tatooine_eisley_reimos_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (tatooine_eisley_reimos_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_202");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_308");
                    }
                    utils.setScriptVar(player, "conversation.tatooine_eisley_reimos.branchId", 63);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.tatooine_eisley_reimos.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int tatooine_eisley_reimos_handleBranch63(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_202"))
        {
            if (tatooine_eisley_reimos_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_204");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (tatooine_eisley_reimos_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_280");
                    }
                    utils.setScriptVar(player, "conversation.tatooine_eisley_reimos.branchId", 64);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.tatooine_eisley_reimos.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_308"))
        {
            if (tatooine_eisley_reimos_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_310");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (tatooine_eisley_reimos_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_312");
                    }
                    utils.setScriptVar(player, "conversation.tatooine_eisley_reimos.branchId", 68);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.tatooine_eisley_reimos.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int tatooine_eisley_reimos_handleBranch64(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_280"))
        {
            if (tatooine_eisley_reimos_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_297");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (tatooine_eisley_reimos_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (tatooine_eisley_reimos_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_344");
                    }
                    utils.setScriptVar(player, "conversation.tatooine_eisley_reimos.branchId", 65);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.tatooine_eisley_reimos.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int tatooine_eisley_reimos_handleBranch65(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_332"))
        {
            if (tatooine_eisley_reimos_condition__defaultCondition(player, npc))
            {
                tatooine_eisley_reimos_action_grantDIQuest(player, npc);
                string_id message = new string_id(c_stringFile, "s_334");
                utils.removeScriptVar(player, "conversation.tatooine_eisley_reimos.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_344"))
        {
            if (tatooine_eisley_reimos_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_346");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (tatooine_eisley_reimos_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (tatooine_eisley_reimos_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_348");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_350");
                    }
                    utils.setScriptVar(player, "conversation.tatooine_eisley_reimos.branchId", 67);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.tatooine_eisley_reimos.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int tatooine_eisley_reimos_handleBranch67(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_348"))
        {
            if (tatooine_eisley_reimos_condition__defaultCondition(player, npc))
            {
                tatooine_eisley_reimos_action_grantDIQuest(player, npc);
                string_id message = new string_id(c_stringFile, "s_334");
                utils.removeScriptVar(player, "conversation.tatooine_eisley_reimos.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_350"))
        {
            if (tatooine_eisley_reimos_condition__defaultCondition(player, npc))
            {
                tatooine_eisley_reimos_action_grantDIQuest(player, npc);
                string_id message = new string_id(c_stringFile, "s_334");
                utils.removeScriptVar(player, "conversation.tatooine_eisley_reimos.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int tatooine_eisley_reimos_handleBranch68(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_312"))
        {
            if (tatooine_eisley_reimos_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_204");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (tatooine_eisley_reimos_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_280");
                    }
                    utils.setScriptVar(player, "conversation.tatooine_eisley_reimos.branchId", 64);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.tatooine_eisley_reimos.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int tatooine_eisley_reimos_handleBranch69(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_316"))
        {
            if (tatooine_eisley_reimos_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_318");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (tatooine_eisley_reimos_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_320");
                    }
                    utils.setScriptVar(player, "conversation.tatooine_eisley_reimos.branchId", 70);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.tatooine_eisley_reimos.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int tatooine_eisley_reimos_handleBranch70(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_320"))
        {
            if (tatooine_eisley_reimos_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_322");
                utils.removeScriptVar(player, "conversation.tatooine_eisley_reimos.branchId");
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
            detachScript(self, "conversation.tatooine_eisley_reimos");
        }
        setCondition(self, CONDITION_CONVERSABLE);
        setCondition(self, CONDITION_INTERESTING);
        setInvulnerable(self, true);
        setName(self, "Reimos Taligg");
        ai_lib.setDefaultCalmBehavior(self, ai_lib.BEHAVIOR_SENTINEL);
        return SCRIPT_CONTINUE;
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        setCondition(self, CONDITION_CONVERSABLE);
        setCondition(self, CONDITION_INTERESTING);
        setInvulnerable(self, true);
        setName(self, "Reimos Taligg");
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
        detachScript(self, "conversation.tatooine_eisley_reimos");
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
        if (tatooine_eisley_reimos_condition_foeQuestComplete(player, npc))
        {
            tatooine_eisley_reimos_action_facePlayer(player, npc);
            string_id message = new string_id(c_stringFile, "s_142");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (tatooine_eisley_reimos_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_165");
                }
                utils.setScriptVar(player, "conversation.tatooine_eisley_reimos.branchId", 1);
                npcStartConversation(player, npc, "tatooine_eisley_reimos", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (tatooine_eisley_reimos_condition_dbQuestComplete(player, npc))
        {
            tatooine_eisley_reimos_action_facePlayer(player, npc);
            string_id message = new string_id(c_stringFile, "s_299");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (tatooine_eisley_reimos_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_300");
                }
                utils.setScriptVar(player, "conversation.tatooine_eisley_reimos.branchId", 4);
                npcStartConversation(player, npc, "tatooine_eisley_reimos", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (tatooine_eisley_reimos_condition_dbOnTask(player, npc))
        {
            tatooine_eisley_reimos_action_facePlayer(player, npc);
            string_id message = new string_id(c_stringFile, "s_170");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (tatooine_eisley_reimos_condition__defaultCondition(player, npc))
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
                utils.setScriptVar(player, "conversation.tatooine_eisley_reimos.branchId", 9);
                npcStartConversation(player, npc, "tatooine_eisley_reimos", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (tatooine_eisley_reimos_condition_dpQuestComplete(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_289");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (tatooine_eisley_reimos_condition__defaultCondition(player, npc))
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
                utils.setScriptVar(player, "conversation.tatooine_eisley_reimos.branchId", 11);
                npcStartConversation(player, npc, "tatooine_eisley_reimos", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (tatooine_eisley_reimos_condition_dpTaskComplete(player, npc))
        {
            tatooine_eisley_reimos_action_signalDPReward(player, npc);
            string_id message = new string_id(c_stringFile, "s_169");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (tatooine_eisley_reimos_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_183");
                }
                utils.setScriptVar(player, "conversation.tatooine_eisley_reimos.branchId", 14);
                npcStartConversation(player, npc, "tatooine_eisley_reimos", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (tatooine_eisley_reimos_condition_dpOnTask(player, npc))
        {
            tatooine_eisley_reimos_action_facePlayer(player, npc);
            string_id message = new string_id(c_stringFile, "s_143");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (tatooine_eisley_reimos_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_145");
                }
                utils.setScriptVar(player, "conversation.tatooine_eisley_reimos.branchId", 19);
                npcStartConversation(player, npc, "tatooine_eisley_reimos", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (tatooine_eisley_reimos_condition_hiddenQuestComplete(player, npc))
        {
            tatooine_eisley_reimos_action_facePlayer(player, npc);
            string_id message = new string_id(c_stringFile, "s_284");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (tatooine_eisley_reimos_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_285");
                }
                utils.setScriptVar(player, "conversation.tatooine_eisley_reimos.branchId", 22);
                npcStartConversation(player, npc, "tatooine_eisley_reimos", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (tatooine_eisley_reimos_condition_hiddenTaskComplete(player, npc))
        {
            tatooine_eisley_reimos_action_signalHAReward(player, npc);
            string_id message = new string_id(c_stringFile, "s_93");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (tatooine_eisley_reimos_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            boolean hasResponse1 = false;
            if (tatooine_eisley_reimos_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_384");
                }
                if (hasResponse1)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_412");
                }
                utils.setScriptVar(player, "conversation.tatooine_eisley_reimos.branchId", 25);
                npcStartConversation(player, npc, "tatooine_eisley_reimos", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (tatooine_eisley_reimos_condition_hiddenOnTask(player, npc))
        {
            tatooine_eisley_reimos_action_facePlayer(player, npc);
            string_id message = new string_id(c_stringFile, "s_137");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (tatooine_eisley_reimos_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_138");
                }
                utils.setScriptVar(player, "conversation.tatooine_eisley_reimos.branchId", 37);
                npcStartConversation(player, npc, "tatooine_eisley_reimos", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (tatooine_eisley_reimos_condition_returnReimosComplete(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_281");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (tatooine_eisley_reimos_condition__defaultCondition(player, npc))
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
                utils.setScriptVar(player, "conversation.tatooine_eisley_reimos.branchId", 40);
                npcStartConversation(player, npc, "tatooine_eisley_reimos", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (tatooine_eisley_reimos_condition_boneQuestComplete(player, npc))
        {
            doAnimationAction(npc, "rub_chin_thoughtful");
            tatooine_eisley_reimos_action_signalReturnReimos(player, npc);
            string_id message = new string_id(c_stringFile, "s_89");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (tatooine_eisley_reimos_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_91");
                }
                utils.setScriptVar(player, "conversation.tatooine_eisley_reimos.branchId", 42);
                npcStartConversation(player, npc, "tatooine_eisley_reimos", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (tatooine_eisley_reimos_condition_diQuestComplete(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_215");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (tatooine_eisley_reimos_condition__defaultCondition(player, npc))
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
                utils.setScriptVar(player, "conversation.tatooine_eisley_reimos.branchId", 50);
                npcStartConversation(player, npc, "tatooine_eisley_reimos", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (tatooine_eisley_reimos_condition_diTaskComplete(player, npc))
        {
            tatooine_eisley_reimos_action_signalDIReward(player, npc);
            string_id message = new string_id(c_stringFile, "s_266");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (tatooine_eisley_reimos_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_267");
                }
                utils.setScriptVar(player, "conversation.tatooine_eisley_reimos.branchId", 54);
                npcStartConversation(player, npc, "tatooine_eisley_reimos", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (tatooine_eisley_reimos_condition_diOnTask(player, npc))
        {
            tatooine_eisley_reimos_action_facePlayer(player, npc);
            string_id message = new string_id(c_stringFile, "s_158");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (tatooine_eisley_reimos_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_160");
                }
                utils.setScriptVar(player, "conversation.tatooine_eisley_reimos.branchId", 57);
                npcStartConversation(player, npc, "tatooine_eisley_reimos", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (tatooine_eisley_reimos_condition_gotoReimosDIComplete(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_275");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (tatooine_eisley_reimos_condition__defaultCondition(player, npc))
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
                utils.setScriptVar(player, "conversation.tatooine_eisley_reimos.branchId", 60);
                npcStartConversation(player, npc, "tatooine_eisley_reimos", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (tatooine_eisley_reimos_condition_sdQuestComplete(player, npc))
        {
            tatooine_eisley_reimos_action_signalGotoReimos(player, npc);
            string_id message = new string_id(c_stringFile, "s_189");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (tatooine_eisley_reimos_condition__defaultCondition(player, npc))
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
                utils.setScriptVar(player, "conversation.tatooine_eisley_reimos.branchId", 62);
                npcStartConversation(player, npc, "tatooine_eisley_reimos", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (tatooine_eisley_reimos_condition__defaultCondition(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_314");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (tatooine_eisley_reimos_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_316");
                }
                utils.setScriptVar(player, "conversation.tatooine_eisley_reimos.branchId", 69);
                npcStartConversation(player, npc, "tatooine_eisley_reimos", message, responses);
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
        if (!conversationId.equals("tatooine_eisley_reimos"))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id npc = self;
        int branchId = utils.getIntScriptVar(player, "conversation.tatooine_eisley_reimos.branchId");
        if (branchId == 1 && tatooine_eisley_reimos_handleBranch1(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 2 && tatooine_eisley_reimos_handleBranch2(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 4 && tatooine_eisley_reimos_handleBranch4(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 5 && tatooine_eisley_reimos_handleBranch5(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 7 && tatooine_eisley_reimos_handleBranch7(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 9 && tatooine_eisley_reimos_handleBranch9(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 11 && tatooine_eisley_reimos_handleBranch11(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 12 && tatooine_eisley_reimos_handleBranch12(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 13 && tatooine_eisley_reimos_handleBranch13(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 14 && tatooine_eisley_reimos_handleBranch14(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 15 && tatooine_eisley_reimos_handleBranch15(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 16 && tatooine_eisley_reimos_handleBranch16(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 17 && tatooine_eisley_reimos_handleBranch17(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 19 && tatooine_eisley_reimos_handleBranch19(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 20 && tatooine_eisley_reimos_handleBranch20(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 22 && tatooine_eisley_reimos_handleBranch22(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 23 && tatooine_eisley_reimos_handleBranch23(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 24 && tatooine_eisley_reimos_handleBranch24(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 25 && tatooine_eisley_reimos_handleBranch25(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 26 && tatooine_eisley_reimos_handleBranch26(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 27 && tatooine_eisley_reimos_handleBranch27(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 28 && tatooine_eisley_reimos_handleBranch28(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 29 && tatooine_eisley_reimos_handleBranch29(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 31 && tatooine_eisley_reimos_handleBranch31(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 32 && tatooine_eisley_reimos_handleBranch32(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 33 && tatooine_eisley_reimos_handleBranch33(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 34 && tatooine_eisley_reimos_handleBranch34(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 35 && tatooine_eisley_reimos_handleBranch35(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 37 && tatooine_eisley_reimos_handleBranch37(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 38 && tatooine_eisley_reimos_handleBranch38(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 40 && tatooine_eisley_reimos_handleBranch40(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 41 && tatooine_eisley_reimos_handleBranch41(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 42 && tatooine_eisley_reimos_handleBranch42(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 43 && tatooine_eisley_reimos_handleBranch43(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 44 && tatooine_eisley_reimos_handleBranch44(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 45 && tatooine_eisley_reimos_handleBranch45(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 46 && tatooine_eisley_reimos_handleBranch46(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 48 && tatooine_eisley_reimos_handleBranch48(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 49 && tatooine_eisley_reimos_handleBranch49(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 50 && tatooine_eisley_reimos_handleBranch50(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 51 && tatooine_eisley_reimos_handleBranch51(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 54 && tatooine_eisley_reimos_handleBranch54(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 55 && tatooine_eisley_reimos_handleBranch55(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 57 && tatooine_eisley_reimos_handleBranch57(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 58 && tatooine_eisley_reimos_handleBranch58(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 60 && tatooine_eisley_reimos_handleBranch60(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 61 && tatooine_eisley_reimos_handleBranch61(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 62 && tatooine_eisley_reimos_handleBranch62(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 63 && tatooine_eisley_reimos_handleBranch63(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 64 && tatooine_eisley_reimos_handleBranch64(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 65 && tatooine_eisley_reimos_handleBranch65(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 67 && tatooine_eisley_reimos_handleBranch67(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 68 && tatooine_eisley_reimos_handleBranch68(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 69 && tatooine_eisley_reimos_handleBranch69(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 70 && tatooine_eisley_reimos_handleBranch70(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        chat.chat(npc, "Error:  Fell through all branches and responses for OnNpcConversationResponse.");
        utils.removeScriptVar(player, "conversation.tatooine_eisley_reimos.branchId");
        return SCRIPT_CONTINUE;
    }
}
