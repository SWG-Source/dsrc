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
import script.library.factions;
import script.library.groundquests;
import script.library.utils;

public class talus_dearic_coa_syirlabradawk extends script.base_script
{
    public talus_dearic_coa_syirlabradawk()
    {
    }
    public static String c_stringFile = "conversation/talus_dearic_coa_syirlabradawk";
    public boolean talus_dearic_coa_syirlabradawk_condition__defaultCondition(obj_id player, obj_id npc) throws InterruptedException
    {
        return true;
    }
    public boolean talus_dearic_coa_syirlabradawk_condition_rebel_faction(obj_id player, obj_id npc) throws InterruptedException
    {
        return (factions.isRebel(player) && groundquests.hasCompletedQuest(player, "talus_dearic_coa_medic_prisoner"));
    }
    public boolean talus_dearic_coa_syirlabradawk_condition_accepted_medic_quest(obj_id player, obj_id npc) throws InterruptedException
    {
        return groundquests.isTaskActive(player, "talus_dearic_coa_medic_prisoner", "talus_dearic_coa_help_syirlabradawk_wft");
    }
    public boolean talus_dearic_coa_syirlabradawk_condition_used_mainframe(obj_id player, obj_id npc) throws InterruptedException
    {
        return groundquests.isTaskActive(player, "talus_dearic_coa_medic_prisoner", "talus_dearic_coa_find_syir_wfs");
    }
    public boolean talus_dearic_coa_syirlabradawk_condition_completed_medic_quest(obj_id player, obj_id npc) throws InterruptedException
    {
        if (factions.isRebel(player))
        {
            if (groundquests.hasCompletedQuest(player, "talus_dearic_coa_medic_prisoner") && (groundquests.hasCompletedQuest(player, "talus_dearic_coa_opt_rebel_quest") || groundquests.hasCompletedQuest(player, "talus_dearic_coa_rebel_view_end")))
            {
                return true;
            }
            else 
            {
                return false;
            }
        }
        else 
        {
            return groundquests.hasCompletedQuest(player, "talus_dearic_coa_medic_prisoner");
        }
    }
    public boolean talus_dearic_coa_syirlabradawk_condition_reb_opt_quest_complete(obj_id player, obj_id npc) throws InterruptedException
    {
        if (factions.isRebel(player) && groundquests.isTaskActive(player, "talus_dearic_coa_opt_rebel_quest", "talus_dearic_coa_imperials_eliminated_wfs"))
        {
            return true;
        }
        else 
        {
            return false;
        }
    }
    public boolean talus_dearic_coa_syirlabradawk_condition_medic_tasks_complete(obj_id player, obj_id npc) throws InterruptedException
    {
        return groundquests.isTaskActive(player, "talus_dearic_coa_medic_prisoner", "talus_dearic_coa_return_to_syir_wfs");
    }
    public boolean talus_dearic_coa_syirlabradawk_condition_reb_opt_qst_active(obj_id player, obj_id npc) throws InterruptedException
    {
        return groundquests.isTaskActive(player, "talus_dearic_coa_opt_rebel_quest", "talus_dearic_coa_escort_elimination_dsm");
    }
    public boolean talus_dearic_coa_syirlabradawk_condition_reb_opt_qst_unaccepted(obj_id player, obj_id npc) throws InterruptedException
    {
        if (factions.isRebel(player) && !groundquests.isQuestActiveOrComplete(player, "talus_dearic_coa_opt_rebel_quest") && groundquests.hasCompletedQuest(player, "talus_dearic_coa_medic_prisoner"))
        {
            return true;
        }
        else 
        {
            return false;
        }
    }
    public void talus_dearic_coa_syirlabradawk_action_accept_medic_quest(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.sendSignal(player, "talus_dearic_coa_syir_found");
    }
    public void talus_dearic_coa_syirlabradawk_action_accept_opt_reb_quest(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.grantQuest(player, "talus_dearic_coa_opt_rebel_quest");
    }
    public void talus_dearic_coa_syirlabradawk_action_complete_opt_reb_quest(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.sendSignal(player, "talus_dearic_coa_report_imperials_eliminated");
    }
    public void talus_dearic_coa_syirlabradawk_action_complete_medic_quest(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.sendSignal(player, "talus_dearic_coa_rescue_syir");
    }
    public void talus_dearic_coa_syirlabradawk_action_grant_return_quest(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.sendSignal(player, "coa_speak_with_syir");
        groundquests.grantQuest(player, "talus_dearic_coa_rebel_view_end");
    }
    public void talus_dearic_coa_syirlabradawk_action_grant_return_quest2(obj_id player, obj_id npc) throws InterruptedException
    {
        if (!groundquests.isQuestActiveOrComplete(player, "talus_dearic_coa_rebel_view_end"))
        {
            groundquests.grantQuest(player, "talus_dearic_coa_rebel_view_end");
        }
        else 
        {
            return;
        }
    }
    public int talus_dearic_coa_syirlabradawk_handleBranch2(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_65"))
        {
            doAnimationAction(player, "beckon");
            talus_dearic_coa_syirlabradawk_action_complete_opt_reb_quest(player, npc);
            if (talus_dearic_coa_syirlabradawk_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "point_away");
                string_id message = new string_id(c_stringFile, "s_66");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (talus_dearic_coa_syirlabradawk_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_130");
                    }
                    utils.setScriptVar(player, "conversation.talus_dearic_coa_syirlabradawk.branchId", 3);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.talus_dearic_coa_syirlabradawk.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int talus_dearic_coa_syirlabradawk_handleBranch3(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_130"))
        {
            if (talus_dearic_coa_syirlabradawk_condition__defaultCondition(player, npc))
            {
                talus_dearic_coa_syirlabradawk_action_grant_return_quest2(player, npc);
                string_id message = new string_id(c_stringFile, "s_131");
                utils.removeScriptVar(player, "conversation.talus_dearic_coa_syirlabradawk.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int talus_dearic_coa_syirlabradawk_handleBranch6(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_78"))
        {
            doAnimationAction(player, "explain");
            talus_dearic_coa_syirlabradawk_action_accept_opt_reb_quest(player, npc);
            if (talus_dearic_coa_syirlabradawk_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "nod_head_once");
                string_id message = new string_id(c_stringFile, "s_79");
                utils.removeScriptVar(player, "conversation.talus_dearic_coa_syirlabradawk.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_83"))
        {
            doAnimationAction(player, "beckon");
            if (talus_dearic_coa_syirlabradawk_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "point_away");
                string_id message = new string_id(c_stringFile, "s_84");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (talus_dearic_coa_syirlabradawk_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (talus_dearic_coa_syirlabradawk_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_93");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_95");
                    }
                    utils.setScriptVar(player, "conversation.talus_dearic_coa_syirlabradawk.branchId", 8);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.talus_dearic_coa_syirlabradawk.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int talus_dearic_coa_syirlabradawk_handleBranch8(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_93"))
        {
            if (talus_dearic_coa_syirlabradawk_condition__defaultCondition(player, npc))
            {
                talus_dearic_coa_syirlabradawk_action_grant_return_quest2(player, npc);
                string_id message = new string_id(c_stringFile, "s_99");
                utils.removeScriptVar(player, "conversation.talus_dearic_coa_syirlabradawk.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_95"))
        {
            if (talus_dearic_coa_syirlabradawk_condition__defaultCondition(player, npc))
            {
                talus_dearic_coa_syirlabradawk_action_grant_return_quest2(player, npc);
                string_id message = new string_id(c_stringFile, "s_97");
                utils.removeScriptVar(player, "conversation.talus_dearic_coa_syirlabradawk.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int talus_dearic_coa_syirlabradawk_handleBranch11(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_27"))
        {
            doAnimationAction(player, "beckon");
            talus_dearic_coa_syirlabradawk_action_complete_medic_quest(player, npc);
            if (talus_dearic_coa_syirlabradawk_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_89");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (talus_dearic_coa_syirlabradawk_condition__defaultCondition(player, npc))
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
                    utils.setScriptVar(player, "conversation.talus_dearic_coa_syirlabradawk.branchId", 12);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.talus_dearic_coa_syirlabradawk.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int talus_dearic_coa_syirlabradawk_handleBranch12(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_90"))
        {
            if (talus_dearic_coa_syirlabradawk_condition_rebel_faction(player, npc))
            {
                doAnimationAction(npc, "explain");
                doAnimationAction(player, "listen");
                string_id message = new string_id(c_stringFile, "s_98");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (talus_dearic_coa_syirlabradawk_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (talus_dearic_coa_syirlabradawk_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (talus_dearic_coa_syirlabradawk_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_103");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_111");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_120");
                    }
                    utils.setScriptVar(player, "conversation.talus_dearic_coa_syirlabradawk.branchId", 13);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.talus_dearic_coa_syirlabradawk.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
            if (talus_dearic_coa_syirlabradawk_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "sigh_deeply");
                string_id message = new string_id(c_stringFile, "s_124");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (talus_dearic_coa_syirlabradawk_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_126");
                    }
                    utils.setScriptVar(player, "conversation.talus_dearic_coa_syirlabradawk.branchId", 18);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.talus_dearic_coa_syirlabradawk.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int talus_dearic_coa_syirlabradawk_handleBranch13(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_103"))
        {
            doAnimationAction(player, "stop");
            talus_dearic_coa_syirlabradawk_action_accept_opt_reb_quest(player, npc);
            if (talus_dearic_coa_syirlabradawk_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "goodbye");
                talus_dearic_coa_syirlabradawk_action_accept_opt_reb_quest(player, npc);
                string_id message = new string_id(c_stringFile, "s_107");
                utils.removeScriptVar(player, "conversation.talus_dearic_coa_syirlabradawk.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_111"))
        {
            doAnimationAction(player, "slump_head");
            if (talus_dearic_coa_syirlabradawk_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "wave_on_dismissing");
                string_id message = new string_id(c_stringFile, "s_114");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (talus_dearic_coa_syirlabradawk_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_116");
                    }
                    utils.setScriptVar(player, "conversation.talus_dearic_coa_syirlabradawk.branchId", 15);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.talus_dearic_coa_syirlabradawk.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_120"))
        {
            doAnimationAction(player, "apologize");
            if (talus_dearic_coa_syirlabradawk_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "nod_head_once");
                talus_dearic_coa_syirlabradawk_action_grant_return_quest(player, npc);
                string_id message = new string_id(c_stringFile, "s_122");
                utils.removeScriptVar(player, "conversation.talus_dearic_coa_syirlabradawk.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int talus_dearic_coa_syirlabradawk_handleBranch15(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_116"))
        {
            doAnimationAction(player, "shake_head_no");
            if (talus_dearic_coa_syirlabradawk_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "explain");
                doAnimationAction(player, "listen");
                talus_dearic_coa_syirlabradawk_action_grant_return_quest2(player, npc);
                string_id message = new string_id(c_stringFile, "s_118");
                utils.removeScriptVar(player, "conversation.talus_dearic_coa_syirlabradawk.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int talus_dearic_coa_syirlabradawk_handleBranch18(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_126"))
        {
            doAnimationAction(player, "huh");
            talus_dearic_coa_syirlabradawk_action_complete_medic_quest(player, npc);
            if (talus_dearic_coa_syirlabradawk_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "explain");
                talus_dearic_coa_syirlabradawk_action_grant_return_quest(player, npc);
                string_id message = new string_id(c_stringFile, "s_128");
                utils.removeScriptVar(player, "conversation.talus_dearic_coa_syirlabradawk.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int talus_dearic_coa_syirlabradawk_handleBranch20(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_91"))
        {
            doAnimationAction(player, "nod_head_once");
            if (talus_dearic_coa_syirlabradawk_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "thank");
                string_id message = new string_id(c_stringFile, "s_92");
                utils.removeScriptVar(player, "conversation.talus_dearic_coa_syirlabradawk.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int talus_dearic_coa_syirlabradawk_handleBranch22(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_48"))
        {
            doAnimationAction(player, "explain");
            if (talus_dearic_coa_syirlabradawk_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "sigh_deeply");
                string_id message = new string_id(c_stringFile, "s_50");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (talus_dearic_coa_syirlabradawk_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_52");
                    }
                    utils.setScriptVar(player, "conversation.talus_dearic_coa_syirlabradawk.branchId", 23);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.talus_dearic_coa_syirlabradawk.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_104"))
        {
            doAnimationAction(player, "shrug_shoulders");
            if (talus_dearic_coa_syirlabradawk_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "nod_head_multiple");
                string_id message = new string_id(c_stringFile, "s_106");
                utils.removeScriptVar(player, "conversation.talus_dearic_coa_syirlabradawk.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int talus_dearic_coa_syirlabradawk_handleBranch23(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_52"))
        {
            doAnimationAction(player, "explain");
            if (talus_dearic_coa_syirlabradawk_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "implore");
                string_id message = new string_id(c_stringFile, "s_54");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (talus_dearic_coa_syirlabradawk_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_56");
                    }
                    utils.setScriptVar(player, "conversation.talus_dearic_coa_syirlabradawk.branchId", 24);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.talus_dearic_coa_syirlabradawk.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int talus_dearic_coa_syirlabradawk_handleBranch24(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_56"))
        {
            doAnimationAction(player, "nod_head_multiple");
            if (talus_dearic_coa_syirlabradawk_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "explain");
                doAnimationAction(player, "listen");
                string_id message = new string_id(c_stringFile, "s_58");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (talus_dearic_coa_syirlabradawk_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (talus_dearic_coa_syirlabradawk_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_60");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_86");
                    }
                    utils.setScriptVar(player, "conversation.talus_dearic_coa_syirlabradawk.branchId", 25);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.talus_dearic_coa_syirlabradawk.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int talus_dearic_coa_syirlabradawk_handleBranch25(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_60"))
        {
            doAnimationAction(player, "sigh_deeply");
            if (talus_dearic_coa_syirlabradawk_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "explain");
                string_id message = new string_id(c_stringFile, "s_68");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (talus_dearic_coa_syirlabradawk_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_70");
                    }
                    utils.setScriptVar(player, "conversation.talus_dearic_coa_syirlabradawk.branchId", 26);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.talus_dearic_coa_syirlabradawk.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_86"))
        {
            doAnimationAction(player, "wave_finger_warning");
            if (talus_dearic_coa_syirlabradawk_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "sigh_deeply");
                string_id message = new string_id(c_stringFile, "s_96");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (talus_dearic_coa_syirlabradawk_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_100");
                    }
                    utils.setScriptVar(player, "conversation.talus_dearic_coa_syirlabradawk.branchId", 28);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.talus_dearic_coa_syirlabradawk.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int talus_dearic_coa_syirlabradawk_handleBranch26(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_70"))
        {
            doAnimationAction(player, "nod");
            talus_dearic_coa_syirlabradawk_action_accept_medic_quest(player, npc);
            if (talus_dearic_coa_syirlabradawk_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "implore");
                string_id message = new string_id(c_stringFile, "s_81");
                utils.removeScriptVar(player, "conversation.talus_dearic_coa_syirlabradawk.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int talus_dearic_coa_syirlabradawk_handleBranch28(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_100"))
        {
            doAnimationAction(player, "thank");
            if (talus_dearic_coa_syirlabradawk_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "goodbye");
                string_id message = new string_id(c_stringFile, "s_102");
                utils.removeScriptVar(player, "conversation.talus_dearic_coa_syirlabradawk.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int talus_dearic_coa_syirlabradawk_handleBranch31(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_110"))
        {
            doAnimationAction(player, "dismiss");
            if (talus_dearic_coa_syirlabradawk_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "goodbye");
                string_id message = new string_id(c_stringFile, "s_112");
                utils.removeScriptVar(player, "conversation.talus_dearic_coa_syirlabradawk.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        if ((!isTangible(self)) || (isPlayer(self)))
        {
            detachScript(self, "conversation.talus_dearic_coa_syirlabradawk");
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
        return SCRIPT_CONTINUE;
    }
    public int OnIncapacitated(obj_id self, obj_id killer) throws InterruptedException
    {
        clearCondition(self, CONDITION_CONVERSABLE);
        detachScript(self, "conversation.talus_dearic_coa_syirlabradawk");
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
        if (talus_dearic_coa_syirlabradawk_condition_completed_medic_quest(player, npc))
        {
            doAnimationAction(npc, "explain");
            talus_dearic_coa_syirlabradawk_action_grant_return_quest2(player, npc);
            string_id message = new string_id(c_stringFile, "s_4");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        if (talus_dearic_coa_syirlabradawk_condition_reb_opt_quest_complete(player, npc))
        {
            doAnimationAction(npc, "manipulate_medium");
            string_id message = new string_id(c_stringFile, "s_63");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (talus_dearic_coa_syirlabradawk_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_65");
                }
                utils.setScriptVar(player, "conversation.talus_dearic_coa_syirlabradawk.branchId", 2);
                npcStartConversation(player, npc, "talus_dearic_coa_syirlabradawk", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (talus_dearic_coa_syirlabradawk_condition_reb_opt_qst_active(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_129");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        if (talus_dearic_coa_syirlabradawk_condition_reb_opt_qst_unaccepted(player, npc))
        {
            doAnimationAction(npc, "explain");
            string_id message = new string_id(c_stringFile, "s_64");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (talus_dearic_coa_syirlabradawk_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            boolean hasResponse1 = false;
            if (talus_dearic_coa_syirlabradawk_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_78");
                }
                if (hasResponse1)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_83");
                }
                utils.setScriptVar(player, "conversation.talus_dearic_coa_syirlabradawk.branchId", 6);
                npcStartConversation(player, npc, "talus_dearic_coa_syirlabradawk", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (talus_dearic_coa_syirlabradawk_condition_medic_tasks_complete(player, npc))
        {
            doAnimationAction(npc, "thank");
            doAnimationAction(player, "tap_foot");
            string_id message = new string_id(c_stringFile, "s_23");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (talus_dearic_coa_syirlabradawk_condition__defaultCondition(player, npc))
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
                utils.setScriptVar(player, "conversation.talus_dearic_coa_syirlabradawk.branchId", 11);
                npcStartConversation(player, npc, "talus_dearic_coa_syirlabradawk", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (talus_dearic_coa_syirlabradawk_condition_accepted_medic_quest(player, npc))
        {
            doAnimationAction(npc, "explain");
            doAnimationAction(player, "listen");
            string_id message = new string_id(c_stringFile, "s_42");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (talus_dearic_coa_syirlabradawk_condition__defaultCondition(player, npc))
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
                utils.setScriptVar(player, "conversation.talus_dearic_coa_syirlabradawk.branchId", 20);
                npcStartConversation(player, npc, "talus_dearic_coa_syirlabradawk", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (talus_dearic_coa_syirlabradawk_condition_used_mainframe(player, npc))
        {
            doAnimationAction(npc, "whisper");
            string_id message = new string_id(c_stringFile, "s_46");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (talus_dearic_coa_syirlabradawk_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            boolean hasResponse1 = false;
            if (talus_dearic_coa_syirlabradawk_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_48");
                }
                if (hasResponse1)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_104");
                }
                utils.setScriptVar(player, "conversation.talus_dearic_coa_syirlabradawk.branchId", 22);
                npcStartConversation(player, npc, "talus_dearic_coa_syirlabradawk", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (talus_dearic_coa_syirlabradawk_condition__defaultCondition(player, npc))
        {
            doAnimationAction(npc, "whisper");
            string_id message = new string_id(c_stringFile, "s_108");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (talus_dearic_coa_syirlabradawk_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_110");
                }
                utils.setScriptVar(player, "conversation.talus_dearic_coa_syirlabradawk.branchId", 31);
                npcStartConversation(player, npc, "talus_dearic_coa_syirlabradawk", message, responses);
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
        if (!conversationId.equals("talus_dearic_coa_syirlabradawk"))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id npc = self;
        int branchId = utils.getIntScriptVar(player, "conversation.talus_dearic_coa_syirlabradawk.branchId");
        if (branchId == 2 && talus_dearic_coa_syirlabradawk_handleBranch2(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 3 && talus_dearic_coa_syirlabradawk_handleBranch3(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 6 && talus_dearic_coa_syirlabradawk_handleBranch6(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 8 && talus_dearic_coa_syirlabradawk_handleBranch8(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 11 && talus_dearic_coa_syirlabradawk_handleBranch11(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 12 && talus_dearic_coa_syirlabradawk_handleBranch12(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 13 && talus_dearic_coa_syirlabradawk_handleBranch13(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 15 && talus_dearic_coa_syirlabradawk_handleBranch15(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 18 && talus_dearic_coa_syirlabradawk_handleBranch18(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 20 && talus_dearic_coa_syirlabradawk_handleBranch20(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 22 && talus_dearic_coa_syirlabradawk_handleBranch22(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 23 && talus_dearic_coa_syirlabradawk_handleBranch23(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 24 && talus_dearic_coa_syirlabradawk_handleBranch24(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 25 && talus_dearic_coa_syirlabradawk_handleBranch25(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 26 && talus_dearic_coa_syirlabradawk_handleBranch26(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 28 && talus_dearic_coa_syirlabradawk_handleBranch28(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 31 && talus_dearic_coa_syirlabradawk_handleBranch31(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        chat.chat(npc, "Error:  Fell through all branches and responses for OnNpcConversationResponse.");
        utils.removeScriptVar(player, "conversation.talus_dearic_coa_syirlabradawk.branchId");
        return SCRIPT_CONTINUE;
    }
}
