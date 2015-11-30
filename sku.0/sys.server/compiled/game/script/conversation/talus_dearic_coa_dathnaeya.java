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
import script.library.conversation;
import script.library.groundquests;
import script.library.utils;

public class talus_dearic_coa_dathnaeya extends script.base_script
{
    public talus_dearic_coa_dathnaeya()
    {
    }
    public static String c_stringFile = "conversation/talus_dearic_coa_dathnaeya";
    public boolean talus_dearic_coa_dathnaeya_condition__defaultCondition(obj_id player, obj_id npc) throws InterruptedException
    {
        return true;
    }
    public boolean talus_dearic_coa_dathnaeya_condition_both_complete(obj_id player, obj_id npc) throws InterruptedException
    {
        if (groundquests.hasCompletedQuest(player, "talus_dearic_coa_imperial_view_end") && groundquests.hasCompletedQuest(player, "talus_dearic_coa_rebel_view_end"))
        {
            return true;
        }
        else 
        {
            return false;
        }
    }
    public boolean talus_dearic_coa_dathnaeya_condition_rebel_complete(obj_id player, obj_id npc) throws InterruptedException
    {
        if (!groundquests.hasCompletedQuest(player, "talus_dearic_coa_imperial_view_end") && groundquests.hasCompletedQuest(player, "talus_dearic_coa_rebel_view_end"))
        {
            return true;
        }
        else 
        {
            return false;
        }
    }
    public boolean talus_dearic_coa_dathnaeya_condition_imperial_complete(obj_id player, obj_id npc) throws InterruptedException
    {
        if (groundquests.hasCompletedQuest(player, "talus_dearic_coa_imperial_view_end") && !groundquests.hasCompletedQuest(player, "talus_dearic_coa_rebel_view_end"))
        {
            return true;
        }
        else 
        {
            return false;
        }
    }
    public boolean talus_dearic_coa_dathnaeya_condition_taarna_contact(obj_id player, obj_id npc) throws InterruptedException
    {
        return groundquests.isQuestActiveOrComplete(player, "talus_dearic_goto_dathnaeya");
    }
    public boolean talus_dearic_coa_dathnaeya_condition_main_quest_active(obj_id player, obj_id npc) throws InterruptedException
    {
        return groundquests.isQuestActive(player, "talus_dearic_crisis_of_allegiance");
    }
    public boolean talus_dearic_coa_dathnaeya_condition_task1or2complete(obj_id player, obj_id npc) throws InterruptedException
    {
        if ((groundquests.hasCompletedQuest(player, "talus_dearic_coa_imperial_view_end") || groundquests.hasCompletedQuest(player, "talus_dearic_coa_rebel_view_end")) && (groundquests.hasCompletedTask(player, "talus_dearic_crisis_of_allegiance", "talus_dearic_coa_report_rebels_wfs") && groundquests.hasCompletedTask(player, "talus_dearic_crisis_of_allegiance", "talus_dearic_coa_report_imperials_wfs")))
        {
            return true;
        }
        else 
        {
            return false;
        }
    }
    public boolean talus_dearic_coa_dathnaeya_condition_rebel_choice(obj_id player, obj_id npc) throws InterruptedException
    {
        boolean choseRebel = false;
        if (groundquests.hasCompletedQuest(player, "talus_dearic_crisis_of_allegiance"))
        {
            if (hasObjVar(player, "coa_dathnaeyas_choice"))
            {
                String choice = getStringObjVar(player, "coa_dathnaeyas_choice");
                if (choice != null && choice.equals("rebel"))
                {
                    choseRebel = true;
                }
            }
        }
        return choseRebel;
    }
    public boolean talus_dearic_coa_dathnaeya_condition_imperial_choice(obj_id player, obj_id npc) throws InterruptedException
    {
        boolean choseEmpire = false;
        if (groundquests.hasCompletedQuest(player, "talus_dearic_crisis_of_allegiance"))
        {
            if (hasObjVar(player, "coa_dathnaeyas_choice"))
            {
                String choice = getStringObjVar(player, "coa_dathnaeyas_choice");
                if (choice != null && choice.equals("empire"))
                {
                    choseEmpire = true;
                }
            }
        }
        return choseEmpire;
    }
    public boolean talus_dearic_coa_dathnaeya_condition_quellium_active(obj_id player, obj_id npc) throws InterruptedException
    {
        return groundquests.isTaskActive(player, "talus_dearic_crisis_of_allegiance", "talus_dearic_coa_report_rebels_wfs");
    }
    public boolean talus_dearic_coa_dathnaeya_condition_spk_to_not_cmplt(obj_id player, obj_id npc) throws InterruptedException
    {
        if (groundquests.isQuestActive(player, "talus_dearic_crisis_of_allegiance"))
        {
            if ((groundquests.hasCompletedQuest(player, "talus_dearic_coa_imperial_view_end") || groundquests.hasCompletedQuest(player, "talus_dearic_coa_rebel_view_end")) && (!groundquests.hasCompletedTask(player, "talus_dearic_crisis_of_allegiance", "talus_dearic_coa_report_rebels_wfs") || !groundquests.hasCompletedTask(player, "talus_dearic_crisis_of_allegiance", "talus_dearic_coa_report_imperials_wfs")))
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
            return false;
        }
    }
    public boolean talus_dearic_coa_dathnaeya_condition_spk_reb_not_cmplt(obj_id player, obj_id npc) throws InterruptedException
    {
        if (groundquests.hasCompletedQuest(player, "talus_dearic_coa_imperial_view_end") && !groundquests.hasCompletedTask(player, "talus_dearic_crisis_of_allegiance", "talus_dearic_coa_report_rebels_wfs"))
        {
            return true;
        }
        else 
        {
            return false;
        }
    }
    public boolean talus_dearic_coa_dathnaeya_condition_spk_imp_not_cmplt(obj_id player, obj_id npc) throws InterruptedException
    {
        if (groundquests.hasCompletedQuest(player, "talus_dearic_coa_rebel_view_end") && !groundquests.hasCompletedTask(player, "talus_dearic_crisis_of_allegiance", "talus_dearic_coa_report_imperials_wfs"))
        {
            return true;
        }
        else 
        {
            return false;
        }
    }
    public boolean talus_dearic_coa_dathnaeya_condition_need_COA_regrant(obj_id player, obj_id npc) throws InterruptedException
    {
        if (!groundquests.isQuestActive(player, "talus_dearic_crisis_of_allegiance") && (groundquests.hasCompletedQuest(player, "talus_dearic_coa_rebel_view_end") || groundquests.hasCompletedQuest(player, "talus_dearic_coa_imperial_view_end")))
        {
            return true;
        }
        else 
        {
            return false;
        }
    }
    public void talus_dearic_coa_dathnaeya_action_grant_main_quest(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.grantQuest(player, "talus_dearic_crisis_of_allegiance");
    }
    public void talus_dearic_coa_dathnaeya_action_missed_42_qst(obj_id player, obj_id npc) throws InterruptedException
    {
        if (!groundquests.isQuestActiveOrComplete(player, "talus_nashal_rescue_operation"))
        {
            groundquests.grantQuest(player, "talus_nashal_rescue_operation");
        }
        else 
        {
            return;
        }
    }
    public void talus_dearic_coa_dathnaeya_action_cmplt_reb_inv(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.sendSignal(player, "talus_dearic_aclo_investigated");
    }
    public void talus_dearic_coa_dathnaeya_action_dath_chose_emp(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.sendSignal(player, "talus_dearic_coa_dathnaeya_chose_empire");
        setObjVar(player, "coa_dathnaeyas_choice", "empire");
    }
    public void talus_dearic_coa_dathnaeya_action_dath_chose_reb(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.sendSignal(player, "talus_dearic_coa_dathnaeya_chose_alliance");
        setObjVar(player, "coa_dathnaeyas_choice", "rebel");
    }
    public void talus_dearic_coa_dathnaeya_action_grant_42_quest(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.grantQuest(player, "talus_nashal_rescue_operation");
    }
    public void talus_dearic_coa_dathnaeya_action_cmplt_tarna_goto(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.sendSignal(player, "goto_dathnaeya_signal_01_01");
    }
    public void talus_dearic_coa_dathnaeya_action_cmplt_imp_inv(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.sendSignal(player, "talus_dearic_nsh_investigated");
    }
    public void talus_dearic_coa_dathnaeya_action_regrant_COA(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.grantQuestNoAcceptUI(player, "talus_dearic_crisis_of_allegiance", false);
        if (groundquests.isQuestActiveOrComplete(player, "talus_dearic_coa_imperial_view"))
        {
            groundquests.sendSignal(player, "talus_dearic_coa_spoke_with_ekirk");
        }
        if (groundquests.isQuestActiveOrComplete(player, "talus_dearic_coa_rebel_view"))
        {
            groundquests.sendSignal(player, "talus_dearic_coa_spoke_with_vayde");
        }
        if (groundquests.hasCompletedQuest(player, "talus_dearic_coa_imperial_view_end"))
        {
            groundquests.sendSignal(player, "talus_dearic_nsh_investigated");
        }
        if (groundquests.hasCompletedQuest(player, "talus_dearic_coa_rebel_view_end"))
        {
            groundquests.sendSignal(player, "talus_dearic_aclo_investigated");
        }
    }
    public int talus_dearic_coa_dathnaeya_handleBranch1(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_169"))
        {
            doAnimationAction(player, "bow");
            if (talus_dearic_coa_dathnaeya_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "nod_head_once");
                talus_dearic_coa_dathnaeya_action_missed_42_qst(player, npc);
                string_id message = new string_id(c_stringFile, "s_170");
                utils.removeScriptVar(player, "conversation.talus_dearic_coa_dathnaeya.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int talus_dearic_coa_dathnaeya_handleBranch3(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_167"))
        {
            doAnimationAction(player, "explain");
            if (talus_dearic_coa_dathnaeya_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "pat");
                talus_dearic_coa_dathnaeya_action_missed_42_qst(player, npc);
                string_id message = new string_id(c_stringFile, "s_168");
                utils.removeScriptVar(player, "conversation.talus_dearic_coa_dathnaeya.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int talus_dearic_coa_dathnaeya_handleBranch5(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_42"))
        {
            doAnimationAction(player, "explain");
            if (talus_dearic_coa_dathnaeya_condition_main_quest_active(player, npc))
            {
                doAnimationAction(player, "beckon");
                string_id message = new string_id(c_stringFile, "s_45");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (talus_dearic_coa_dathnaeya_condition_both_complete(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (talus_dearic_coa_dathnaeya_condition_imperial_complete(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (talus_dearic_coa_dathnaeya_condition_rebel_complete(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_46");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_47");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_50");
                    }
                    utils.setScriptVar(player, "conversation.talus_dearic_coa_dathnaeya.branchId", 6);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.talus_dearic_coa_dathnaeya.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
            if (talus_dearic_coa_dathnaeya_condition__defaultCondition(player, npc))
            {
                talus_dearic_coa_dathnaeya_action_regrant_COA(player, npc);
                string_id message = new string_id(c_stringFile, "s_206");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (talus_dearic_coa_dathnaeya_condition_both_complete(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (talus_dearic_coa_dathnaeya_condition_imperial_complete(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (talus_dearic_coa_dathnaeya_condition_rebel_complete(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_46");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_47");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_50");
                    }
                    utils.setScriptVar(player, "conversation.talus_dearic_coa_dathnaeya.branchId", 6);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.talus_dearic_coa_dathnaeya.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_105"))
        {
            doAnimationAction(player, "shake_head_no");
            if (talus_dearic_coa_dathnaeya_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "nod_head_multiple");
                string_id message = new string_id(c_stringFile, "s_107");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (talus_dearic_coa_dathnaeya_condition__defaultCondition(player, npc))
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
                    utils.setScriptVar(player, "conversation.talus_dearic_coa_dathnaeya.branchId", 30);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.talus_dearic_coa_dathnaeya.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int talus_dearic_coa_dathnaeya_handleBranch6(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_46"))
        {
            doAnimationAction(player, "explain");
            if (talus_dearic_coa_dathnaeya_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "paper");
                string_id message = new string_id(c_stringFile, "s_75");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (talus_dearic_coa_dathnaeya_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (talus_dearic_coa_dathnaeya_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_76");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_77");
                    }
                    utils.setScriptVar(player, "conversation.talus_dearic_coa_dathnaeya.branchId", 7);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.talus_dearic_coa_dathnaeya.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_47"))
        {
            doAnimationAction(player, "explain");
            if (talus_dearic_coa_dathnaeya_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "paper");
                doAnimationAction(player, "nod_head_once");
                string_id message = new string_id(c_stringFile, "s_62");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (talus_dearic_coa_dathnaeya_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (talus_dearic_coa_dathnaeya_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_63");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_64");
                    }
                    utils.setScriptVar(player, "conversation.talus_dearic_coa_dathnaeya.branchId", 15);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.talus_dearic_coa_dathnaeya.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_50"))
        {
            doAnimationAction(player, "explain");
            if (talus_dearic_coa_dathnaeya_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "paper");
                string_id message = new string_id(c_stringFile, "s_52");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (talus_dearic_coa_dathnaeya_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (talus_dearic_coa_dathnaeya_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_54");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_92");
                    }
                    utils.setScriptVar(player, "conversation.talus_dearic_coa_dathnaeya.branchId", 22);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.talus_dearic_coa_dathnaeya.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int talus_dearic_coa_dathnaeya_handleBranch7(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_76"))
        {
            doAnimationAction(player, "explain");
            if (talus_dearic_coa_dathnaeya_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "paper");
                doAnimationAction(player, "explain");
                string_id message = new string_id(c_stringFile, "s_78");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (talus_dearic_coa_dathnaeya_condition__defaultCondition(player, npc))
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
                    utils.setScriptVar(player, "conversation.talus_dearic_coa_dathnaeya.branchId", 8);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.talus_dearic_coa_dathnaeya.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_77"))
        {
            doAnimationAction(player, "explain");
            if (talus_dearic_coa_dathnaeya_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "paper");
                string_id message = new string_id(c_stringFile, "s_83");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (talus_dearic_coa_dathnaeya_condition__defaultCondition(player, npc))
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
                    utils.setScriptVar(player, "conversation.talus_dearic_coa_dathnaeya.branchId", 11);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.talus_dearic_coa_dathnaeya.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int talus_dearic_coa_dathnaeya_handleBranch8(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_79"))
        {
            doAnimationAction(player, "nod_head_multiple");
            if (talus_dearic_coa_dathnaeya_condition__defaultCondition(player, npc))
            {
                doAnimationAction(player, "rub_chin_thoughtful");
                talus_dearic_coa_dathnaeya_action_dath_chose_reb(player, npc);
                string_id message = new string_id(c_stringFile, "s_80");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (talus_dearic_coa_dathnaeya_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_81");
                    }
                    utils.setScriptVar(player, "conversation.talus_dearic_coa_dathnaeya.branchId", 9);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.talus_dearic_coa_dathnaeya.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int talus_dearic_coa_dathnaeya_handleBranch9(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_81"))
        {
            doAnimationAction(player, "standing_placate");
            if (talus_dearic_coa_dathnaeya_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "handshake_tandem");
                doAnimationAction(player, "handshake_tandem");
                talus_dearic_coa_dathnaeya_action_grant_42_quest(player, npc);
                string_id message = new string_id(c_stringFile, "s_82");
                utils.removeScriptVar(player, "conversation.talus_dearic_coa_dathnaeya.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int talus_dearic_coa_dathnaeya_handleBranch11(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_84"))
        {
            doAnimationAction(player, "explain");
            if (talus_dearic_coa_dathnaeya_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "nod_head_multiple");
                talus_dearic_coa_dathnaeya_action_dath_chose_emp(player, npc);
                string_id message = new string_id(c_stringFile, "s_85");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (talus_dearic_coa_dathnaeya_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_86");
                    }
                    utils.setScriptVar(player, "conversation.talus_dearic_coa_dathnaeya.branchId", 12);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.talus_dearic_coa_dathnaeya.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int talus_dearic_coa_dathnaeya_handleBranch12(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_86"))
        {
            doAnimationAction(player, "offer_affection");
            if (talus_dearic_coa_dathnaeya_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "refuse_offer_affection");
                doAnimationAction(player, "taken_aback");
                string_id message = new string_id(c_stringFile, "s_87");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (talus_dearic_coa_dathnaeya_condition__defaultCondition(player, npc))
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
                    utils.setScriptVar(player, "conversation.talus_dearic_coa_dathnaeya.branchId", 13);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.talus_dearic_coa_dathnaeya.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int talus_dearic_coa_dathnaeya_handleBranch13(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_183"))
        {
            doAnimationAction(player, "salute1");
            if (talus_dearic_coa_dathnaeya_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "salute2");
                talus_dearic_coa_dathnaeya_action_grant_42_quest(player, npc);
                string_id message = new string_id(c_stringFile, "s_184");
                utils.removeScriptVar(player, "conversation.talus_dearic_coa_dathnaeya.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int talus_dearic_coa_dathnaeya_handleBranch15(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_63"))
        {
            doAnimationAction(player, "explain");
            if (talus_dearic_coa_dathnaeya_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "explain");
                string_id message = new string_id(c_stringFile, "s_65");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (talus_dearic_coa_dathnaeya_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_66");
                    }
                    utils.setScriptVar(player, "conversation.talus_dearic_coa_dathnaeya.branchId", 16);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.talus_dearic_coa_dathnaeya.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_64"))
        {
            doAnimationAction(player, "wave_finger_warning");
            if (talus_dearic_coa_dathnaeya_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "taken_aback");
                doAnimationAction(player, "nod_head_multiple");
                talus_dearic_coa_dathnaeya_action_cmplt_reb_inv(player, npc);
                string_id message = new string_id(c_stringFile, "s_70");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (talus_dearic_coa_dathnaeya_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_71");
                    }
                    utils.setScriptVar(player, "conversation.talus_dearic_coa_dathnaeya.branchId", 19);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.talus_dearic_coa_dathnaeya.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int talus_dearic_coa_dathnaeya_handleBranch16(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_66"))
        {
            doAnimationAction(player, "pound_fist_palm");
            if (talus_dearic_coa_dathnaeya_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "nod_head_multiple");
                talus_dearic_coa_dathnaeya_action_dath_chose_emp(player, npc);
                string_id message = new string_id(c_stringFile, "s_67");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (talus_dearic_coa_dathnaeya_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_68");
                    }
                    utils.setScriptVar(player, "conversation.talus_dearic_coa_dathnaeya.branchId", 17);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.talus_dearic_coa_dathnaeya.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int talus_dearic_coa_dathnaeya_handleBranch17(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_68"))
        {
            doAnimationAction(player, "nod_head_multiple");
            if (talus_dearic_coa_dathnaeya_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "salute2");
                talus_dearic_coa_dathnaeya_action_grant_42_quest(player, npc);
                string_id message = new string_id(c_stringFile, "s_69");
                utils.removeScriptVar(player, "conversation.talus_dearic_coa_dathnaeya.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int talus_dearic_coa_dathnaeya_handleBranch19(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_71"))
        {
            doAnimationAction(player, "implore");
            if (talus_dearic_coa_dathnaeya_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "pound_fist_palm");
                doAnimationAction(player, "taken_aback");
                talus_dearic_coa_dathnaeya_action_dath_chose_reb(player, npc);
                string_id message = new string_id(c_stringFile, "s_72");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (talus_dearic_coa_dathnaeya_condition__defaultCondition(player, npc))
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
                    utils.setScriptVar(player, "conversation.talus_dearic_coa_dathnaeya.branchId", 20);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.talus_dearic_coa_dathnaeya.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int talus_dearic_coa_dathnaeya_handleBranch20(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_73"))
        {
            doAnimationAction(player, "belly_laugh");
            if (talus_dearic_coa_dathnaeya_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "point_accusingly");
                doAnimationAction(player, "belly_laugh");
                talus_dearic_coa_dathnaeya_action_grant_42_quest(player, npc);
                string_id message = new string_id(c_stringFile, "s_74");
                utils.removeScriptVar(player, "conversation.talus_dearic_coa_dathnaeya.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int talus_dearic_coa_dathnaeya_handleBranch22(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_54"))
        {
            doAnimationAction(player, "explain");
            if (talus_dearic_coa_dathnaeya_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "rub_chin_thoughtful");
                string_id message = new string_id(c_stringFile, "s_56");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (talus_dearic_coa_dathnaeya_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_58");
                    }
                    utils.setScriptVar(player, "conversation.talus_dearic_coa_dathnaeya.branchId", 23);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.talus_dearic_coa_dathnaeya.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_92"))
        {
            doAnimationAction(player, "pound_fist_palm");
            if (talus_dearic_coa_dathnaeya_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "taken_aback");
                talus_dearic_coa_dathnaeya_action_cmplt_imp_inv(player, npc);
                string_id message = new string_id(c_stringFile, "s_94");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (talus_dearic_coa_dathnaeya_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_96");
                    }
                    utils.setScriptVar(player, "conversation.talus_dearic_coa_dathnaeya.branchId", 26);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.talus_dearic_coa_dathnaeya.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int talus_dearic_coa_dathnaeya_handleBranch23(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_58"))
        {
            doAnimationAction(player, "shake_head_no");
            if (talus_dearic_coa_dathnaeya_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "paper");
                talus_dearic_coa_dathnaeya_action_dath_chose_reb(player, npc);
                string_id message = new string_id(c_stringFile, "s_60");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (talus_dearic_coa_dathnaeya_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_88");
                    }
                    utils.setScriptVar(player, "conversation.talus_dearic_coa_dathnaeya.branchId", 24);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.talus_dearic_coa_dathnaeya.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int talus_dearic_coa_dathnaeya_handleBranch24(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_88"))
        {
            doAnimationAction(player, "bow");
            if (talus_dearic_coa_dathnaeya_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "curtsey1");
                talus_dearic_coa_dathnaeya_action_grant_42_quest(player, npc);
                string_id message = new string_id(c_stringFile, "s_90");
                utils.removeScriptVar(player, "conversation.talus_dearic_coa_dathnaeya.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int talus_dearic_coa_dathnaeya_handleBranch26(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_96"))
        {
            doAnimationAction(player, "point_accusingly");
            if (talus_dearic_coa_dathnaeya_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "nod_head_multiple");
                doAnimationAction(player, "tap_foot");
                talus_dearic_coa_dathnaeya_action_dath_chose_emp(player, npc);
                string_id message = new string_id(c_stringFile, "s_98");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (talus_dearic_coa_dathnaeya_condition__defaultCondition(player, npc))
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
                    utils.setScriptVar(player, "conversation.talus_dearic_coa_dathnaeya.branchId", 27);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.talus_dearic_coa_dathnaeya.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int talus_dearic_coa_dathnaeya_handleBranch27(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_100"))
        {
            doAnimationAction(player, "salute2");
            if (talus_dearic_coa_dathnaeya_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "salute2");
                talus_dearic_coa_dathnaeya_action_grant_42_quest(player, npc);
                string_id message = new string_id(c_stringFile, "s_102");
                utils.removeScriptVar(player, "conversation.talus_dearic_coa_dathnaeya.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int talus_dearic_coa_dathnaeya_handleBranch29(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_46"))
        {
            doAnimationAction(player, "explain");
            if (talus_dearic_coa_dathnaeya_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "paper");
                string_id message = new string_id(c_stringFile, "s_75");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (talus_dearic_coa_dathnaeya_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (talus_dearic_coa_dathnaeya_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_76");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_77");
                    }
                    utils.setScriptVar(player, "conversation.talus_dearic_coa_dathnaeya.branchId", 7);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.talus_dearic_coa_dathnaeya.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_47"))
        {
            doAnimationAction(player, "explain");
            if (talus_dearic_coa_dathnaeya_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "paper");
                doAnimationAction(player, "nod_head_once");
                string_id message = new string_id(c_stringFile, "s_62");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (talus_dearic_coa_dathnaeya_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (talus_dearic_coa_dathnaeya_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_63");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_64");
                    }
                    utils.setScriptVar(player, "conversation.talus_dearic_coa_dathnaeya.branchId", 15);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.talus_dearic_coa_dathnaeya.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_50"))
        {
            doAnimationAction(player, "explain");
            if (talus_dearic_coa_dathnaeya_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "paper");
                string_id message = new string_id(c_stringFile, "s_52");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (talus_dearic_coa_dathnaeya_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (talus_dearic_coa_dathnaeya_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_54");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_92");
                    }
                    utils.setScriptVar(player, "conversation.talus_dearic_coa_dathnaeya.branchId", 22);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.talus_dearic_coa_dathnaeya.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int talus_dearic_coa_dathnaeya_handleBranch30(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_165"))
        {
            doAnimationAction(player, "nod_head_once");
            if (talus_dearic_coa_dathnaeya_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "dream");
                string_id message = new string_id(c_stringFile, "s_166");
                utils.removeScriptVar(player, "conversation.talus_dearic_coa_dathnaeya.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int talus_dearic_coa_dathnaeya_handleBranch32(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_219"))
        {
            doAnimationAction(player, "manipulate_medium");
            if (talus_dearic_coa_dathnaeya_condition_spk_reb_not_cmplt(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_220");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (talus_dearic_coa_dathnaeya_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_223");
                    }
                    utils.setScriptVar(player, "conversation.talus_dearic_coa_dathnaeya.branchId", 33);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.talus_dearic_coa_dathnaeya.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
            if (talus_dearic_coa_dathnaeya_condition_spk_imp_not_cmplt(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_221");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (talus_dearic_coa_dathnaeya_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_227");
                    }
                    utils.setScriptVar(player, "conversation.talus_dearic_coa_dathnaeya.branchId", 36);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.talus_dearic_coa_dathnaeya.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
            if (talus_dearic_coa_dathnaeya_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_222");
                utils.removeScriptVar(player, "conversation.talus_dearic_coa_dathnaeya.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_231"))
        {
            if (talus_dearic_coa_dathnaeya_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "slow_down");
                string_id message = new string_id(c_stringFile, "s_178");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (talus_dearic_coa_dathnaeya_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (talus_dearic_coa_dathnaeya_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_180");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_182");
                    }
                    utils.setScriptVar(player, "conversation.talus_dearic_coa_dathnaeya.branchId", 53);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.talus_dearic_coa_dathnaeya.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int talus_dearic_coa_dathnaeya_handleBranch33(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_223"))
        {
            if (talus_dearic_coa_dathnaeya_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_224");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (talus_dearic_coa_dathnaeya_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_225");
                    }
                    utils.setScriptVar(player, "conversation.talus_dearic_coa_dathnaeya.branchId", 34);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.talus_dearic_coa_dathnaeya.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int talus_dearic_coa_dathnaeya_handleBranch34(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_225"))
        {
            if (talus_dearic_coa_dathnaeya_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_226");
                utils.removeScriptVar(player, "conversation.talus_dearic_coa_dathnaeya.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int talus_dearic_coa_dathnaeya_handleBranch36(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_227"))
        {
            if (talus_dearic_coa_dathnaeya_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_228");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (talus_dearic_coa_dathnaeya_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_229");
                    }
                    utils.setScriptVar(player, "conversation.talus_dearic_coa_dathnaeya.branchId", 37);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.talus_dearic_coa_dathnaeya.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int talus_dearic_coa_dathnaeya_handleBranch37(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_229"))
        {
            if (talus_dearic_coa_dathnaeya_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_230");
                utils.removeScriptVar(player, "conversation.talus_dearic_coa_dathnaeya.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int talus_dearic_coa_dathnaeya_handleBranch40(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_151"))
        {
            doAnimationAction(player, "explain");
            if (talus_dearic_coa_dathnaeya_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "thank");
                string_id message = new string_id(c_stringFile, "s_153");
                utils.removeScriptVar(player, "conversation.talus_dearic_coa_dathnaeya.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_152"))
        {
            doAnimationAction(player, "apologize");
            if (talus_dearic_coa_dathnaeya_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "survey");
                string_id message = new string_id(c_stringFile, "s_154");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (talus_dearic_coa_dathnaeya_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_163");
                    }
                    utils.setScriptVar(player, "conversation.talus_dearic_coa_dathnaeya.branchId", 42);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.talus_dearic_coa_dathnaeya.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int talus_dearic_coa_dathnaeya_handleBranch42(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_163"))
        {
            doAnimationAction(player, "thank");
            if (talus_dearic_coa_dathnaeya_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "goodbye");
                string_id message = new string_id(c_stringFile, "s_164");
                utils.removeScriptVar(player, "conversation.talus_dearic_coa_dathnaeya.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int talus_dearic_coa_dathnaeya_handleBranch44(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_210"))
        {
            if (talus_dearic_coa_dathnaeya_condition__defaultCondition(player, npc))
            {
                talus_dearic_coa_dathnaeya_action_regrant_COA(player, npc);
                string_id message = new string_id(c_stringFile, "s_211");
                utils.removeScriptVar(player, "conversation.talus_dearic_coa_dathnaeya.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int talus_dearic_coa_dathnaeya_handleBranch46(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_138"))
        {
            doAnimationAction(player, "thank");
            talus_dearic_coa_dathnaeya_action_cmplt_tarna_goto(player, npc);
            if (talus_dearic_coa_dathnaeya_condition__defaultCondition(player, npc))
            {
                doAnimationAction(player, "sigh_deeply");
                string_id message = new string_id(c_stringFile, "s_140");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (talus_dearic_coa_dathnaeya_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (talus_dearic_coa_dathnaeya_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (talus_dearic_coa_dathnaeya_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_142");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_174");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_176");
                    }
                    utils.setScriptVar(player, "conversation.talus_dearic_coa_dathnaeya.branchId", 47);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.talus_dearic_coa_dathnaeya.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_188"))
        {
            doAnimationAction(player, "shake_head_no");
            if (talus_dearic_coa_dathnaeya_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "sigh_deeply");
                string_id message = new string_id(c_stringFile, "s_190");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (talus_dearic_coa_dathnaeya_condition__defaultCondition(player, npc))
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
                    utils.setScriptVar(player, "conversation.talus_dearic_coa_dathnaeya.branchId", 55);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.talus_dearic_coa_dathnaeya.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int talus_dearic_coa_dathnaeya_handleBranch47(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_142"))
        {
            doAnimationAction(player, "shrug_hands");
            if (talus_dearic_coa_dathnaeya_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "explain");
                string_id message = new string_id(c_stringFile, "s_144");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (talus_dearic_coa_dathnaeya_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_146");
                    }
                    utils.setScriptVar(player, "conversation.talus_dearic_coa_dathnaeya.branchId", 48);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.talus_dearic_coa_dathnaeya.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_174"))
        {
            doAnimationAction(player, "snap_finger1");
            if (talus_dearic_coa_dathnaeya_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "slow_down");
                string_id message = new string_id(c_stringFile, "s_178");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (talus_dearic_coa_dathnaeya_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (talus_dearic_coa_dathnaeya_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_180");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_182");
                    }
                    utils.setScriptVar(player, "conversation.talus_dearic_coa_dathnaeya.branchId", 53);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.talus_dearic_coa_dathnaeya.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_176"))
        {
            doAnimationAction(player, "snap_finger2");
            if (talus_dearic_coa_dathnaeya_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "slow_down");
                string_id message = new string_id(c_stringFile, "s_178");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (talus_dearic_coa_dathnaeya_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (talus_dearic_coa_dathnaeya_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_180");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_182");
                    }
                    utils.setScriptVar(player, "conversation.talus_dearic_coa_dathnaeya.branchId", 53);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.talus_dearic_coa_dathnaeya.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int talus_dearic_coa_dathnaeya_handleBranch48(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_146"))
        {
            doAnimationAction(player, "taken_aback");
            if (talus_dearic_coa_dathnaeya_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "standing_placate");
                doAnimationAction(player, "taken_aback");
                string_id message = new string_id(c_stringFile, "s_148");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (talus_dearic_coa_dathnaeya_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (talus_dearic_coa_dathnaeya_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_150");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_160");
                    }
                    utils.setScriptVar(player, "conversation.talus_dearic_coa_dathnaeya.branchId", 49);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.talus_dearic_coa_dathnaeya.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int talus_dearic_coa_dathnaeya_handleBranch49(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_150"))
        {
            doAnimationAction(player, "shrug_shoulders");
            if (talus_dearic_coa_dathnaeya_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "thank");
                doAnimationAction(player, "thumbs_up");
                talus_dearic_coa_dathnaeya_action_grant_main_quest(player, npc);
                string_id message = new string_id(c_stringFile, "s_156");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (talus_dearic_coa_dathnaeya_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_161");
                    }
                    utils.setScriptVar(player, "conversation.talus_dearic_coa_dathnaeya.branchId", 50);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.talus_dearic_coa_dathnaeya.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_160"))
        {
            doAnimationAction(player, "shake_head_no");
            if (talus_dearic_coa_dathnaeya_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "slump_head");
                string_id message = new string_id(c_stringFile, "s_172");
                utils.removeScriptVar(player, "conversation.talus_dearic_coa_dathnaeya.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int talus_dearic_coa_dathnaeya_handleBranch50(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_161"))
        {
            doAnimationAction(player, "bow");
            if (talus_dearic_coa_dathnaeya_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "curtsey1");
                string_id message = new string_id(c_stringFile, "s_162");
                utils.removeScriptVar(player, "conversation.talus_dearic_coa_dathnaeya.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int talus_dearic_coa_dathnaeya_handleBranch53(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_180"))
        {
            doAnimationAction(player, "sigh_deeply");
            if (talus_dearic_coa_dathnaeya_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "explain");
                string_id message = new string_id(c_stringFile, "s_144");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (talus_dearic_coa_dathnaeya_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_146");
                    }
                    utils.setScriptVar(player, "conversation.talus_dearic_coa_dathnaeya.branchId", 48);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.talus_dearic_coa_dathnaeya.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_182"))
        {
            doAnimationAction(player, "shake_head_no");
            if (talus_dearic_coa_dathnaeya_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "sigh_deeply");
                doAnimationAction(player, "bow");
                string_id message = new string_id(c_stringFile, "s_186");
                utils.removeScriptVar(player, "conversation.talus_dearic_coa_dathnaeya.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int talus_dearic_coa_dathnaeya_handleBranch55(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_192"))
        {
            doAnimationAction(player, "explain");
            if (talus_dearic_coa_dathnaeya_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "slump_head");
                string_id message = new string_id(c_stringFile, "s_195");
                utils.removeScriptVar(player, "conversation.talus_dearic_coa_dathnaeya.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int talus_dearic_coa_dathnaeya_handleBranch57(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_199"))
        {
            doAnimationAction(player, "apologize");
            if (talus_dearic_coa_dathnaeya_condition__defaultCondition(player, npc))
            {
                doAnimationAction(player, "goodbye");
                string_id message = new string_id(c_stringFile, "s_201");
                utils.removeScriptVar(player, "conversation.talus_dearic_coa_dathnaeya.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_203"))
        {
            doAnimationAction(player, "explain");
            if (talus_dearic_coa_dathnaeya_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "point_right");
                string_id message = new string_id(c_stringFile, "s_205");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (talus_dearic_coa_dathnaeya_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_208");
                    }
                    utils.setScriptVar(player, "conversation.talus_dearic_coa_dathnaeya.branchId", 59);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.talus_dearic_coa_dathnaeya.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int talus_dearic_coa_dathnaeya_handleBranch59(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_208"))
        {
            doAnimationAction(player, "thank");
            if (talus_dearic_coa_dathnaeya_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "goodbye");
                string_id message = new string_id(c_stringFile, "s_213");
                utils.removeScriptVar(player, "conversation.talus_dearic_coa_dathnaeya.branchId");
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
            detachScript(self, "conversation.talus_dearic_coa_dathnaeya");
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
        detachScript(self, "conversation.talus_dearic_coa_dathnaeya");
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
        if (talus_dearic_coa_dathnaeya_condition_imperial_choice(player, npc))
        {
            doAnimationAction(npc, "salute1");
            doAnimationAction(player, "salute2");
            string_id message = new string_id(c_stringFile, "s_12");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (talus_dearic_coa_dathnaeya_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_169");
                }
                utils.setScriptVar(player, "conversation.talus_dearic_coa_dathnaeya.branchId", 1);
                npcStartConversation(player, npc, "talus_dearic_coa_dathnaeya", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (talus_dearic_coa_dathnaeya_condition_rebel_choice(player, npc))
        {
            doAnimationAction(npc, "offer_affection");
            doAnimationAction(player, "accept_affection");
            string_id message = new string_id(c_stringFile, "s_7");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (talus_dearic_coa_dathnaeya_condition__defaultCondition(player, npc))
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
                utils.setScriptVar(player, "conversation.talus_dearic_coa_dathnaeya.branchId", 3);
                npcStartConversation(player, npc, "talus_dearic_coa_dathnaeya", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (talus_dearic_coa_dathnaeya_condition_task1or2complete(player, npc))
        {
            doAnimationAction(npc, "dream");
            string_id message = new string_id(c_stringFile, "s_11");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (talus_dearic_coa_dathnaeya_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            boolean hasResponse1 = false;
            if (talus_dearic_coa_dathnaeya_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_42");
                }
                if (hasResponse1)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_105");
                }
                utils.setScriptVar(player, "conversation.talus_dearic_coa_dathnaeya.branchId", 5);
                npcStartConversation(player, npc, "talus_dearic_coa_dathnaeya", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (talus_dearic_coa_dathnaeya_condition_spk_to_not_cmplt(player, npc))
        {
            doAnimationAction(npc, "greet");
            doAnimationAction(player, "greet");
            string_id message = new string_id(c_stringFile, "s_194");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (talus_dearic_coa_dathnaeya_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            boolean hasResponse1 = false;
            if (talus_dearic_coa_dathnaeya_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_219");
                }
                if (hasResponse1)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_231");
                }
                utils.setScriptVar(player, "conversation.talus_dearic_coa_dathnaeya.branchId", 32);
                npcStartConversation(player, npc, "talus_dearic_coa_dathnaeya", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (talus_dearic_coa_dathnaeya_condition_main_quest_active(player, npc))
        {
            doAnimationAction(npc, "explain");
            string_id message = new string_id(c_stringFile, "s_125");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (talus_dearic_coa_dathnaeya_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            boolean hasResponse1 = false;
            if (talus_dearic_coa_dathnaeya_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_151");
                }
                if (hasResponse1)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_152");
                }
                utils.setScriptVar(player, "conversation.talus_dearic_coa_dathnaeya.branchId", 40);
                npcStartConversation(player, npc, "talus_dearic_coa_dathnaeya", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (talus_dearic_coa_dathnaeya_condition_need_COA_regrant(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_209");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (talus_dearic_coa_dathnaeya_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_210");
                }
                utils.setScriptVar(player, "conversation.talus_dearic_coa_dathnaeya.branchId", 44);
                npcStartConversation(player, npc, "talus_dearic_coa_dathnaeya", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (talus_dearic_coa_dathnaeya_condition_taarna_contact(player, npc))
        {
            doAnimationAction(npc, "dream");
            string_id message = new string_id(c_stringFile, "s_136");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (talus_dearic_coa_dathnaeya_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            boolean hasResponse1 = false;
            if (talus_dearic_coa_dathnaeya_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_138");
                }
                if (hasResponse1)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_188");
                }
                utils.setScriptVar(player, "conversation.talus_dearic_coa_dathnaeya.branchId", 46);
                npcStartConversation(player, npc, "talus_dearic_coa_dathnaeya", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (talus_dearic_coa_dathnaeya_condition__defaultCondition(player, npc))
        {
            doAnimationAction(npc, "greet");
            string_id message = new string_id(c_stringFile, "s_197");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (talus_dearic_coa_dathnaeya_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            boolean hasResponse1 = false;
            if (talus_dearic_coa_dathnaeya_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_199");
                }
                if (hasResponse1)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_203");
                }
                utils.setScriptVar(player, "conversation.talus_dearic_coa_dathnaeya.branchId", 57);
                npcStartConversation(player, npc, "talus_dearic_coa_dathnaeya", message, responses);
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
        if (!conversationId.equals("talus_dearic_coa_dathnaeya"))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id npc = self;
        int branchId = utils.getIntScriptVar(player, "conversation.talus_dearic_coa_dathnaeya.branchId");
        if (branchId == 1 && talus_dearic_coa_dathnaeya_handleBranch1(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 3 && talus_dearic_coa_dathnaeya_handleBranch3(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 5 && talus_dearic_coa_dathnaeya_handleBranch5(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 6 && talus_dearic_coa_dathnaeya_handleBranch6(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 7 && talus_dearic_coa_dathnaeya_handleBranch7(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 8 && talus_dearic_coa_dathnaeya_handleBranch8(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 9 && talus_dearic_coa_dathnaeya_handleBranch9(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 11 && talus_dearic_coa_dathnaeya_handleBranch11(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 12 && talus_dearic_coa_dathnaeya_handleBranch12(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 13 && talus_dearic_coa_dathnaeya_handleBranch13(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 15 && talus_dearic_coa_dathnaeya_handleBranch15(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 16 && talus_dearic_coa_dathnaeya_handleBranch16(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 17 && talus_dearic_coa_dathnaeya_handleBranch17(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 19 && talus_dearic_coa_dathnaeya_handleBranch19(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 20 && talus_dearic_coa_dathnaeya_handleBranch20(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 22 && talus_dearic_coa_dathnaeya_handleBranch22(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 23 && talus_dearic_coa_dathnaeya_handleBranch23(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 24 && talus_dearic_coa_dathnaeya_handleBranch24(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 26 && talus_dearic_coa_dathnaeya_handleBranch26(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 27 && talus_dearic_coa_dathnaeya_handleBranch27(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 29 && talus_dearic_coa_dathnaeya_handleBranch29(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 30 && talus_dearic_coa_dathnaeya_handleBranch30(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 32 && talus_dearic_coa_dathnaeya_handleBranch32(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 33 && talus_dearic_coa_dathnaeya_handleBranch33(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 34 && talus_dearic_coa_dathnaeya_handleBranch34(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 36 && talus_dearic_coa_dathnaeya_handleBranch36(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 37 && talus_dearic_coa_dathnaeya_handleBranch37(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 40 && talus_dearic_coa_dathnaeya_handleBranch40(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 42 && talus_dearic_coa_dathnaeya_handleBranch42(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 44 && talus_dearic_coa_dathnaeya_handleBranch44(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 46 && talus_dearic_coa_dathnaeya_handleBranch46(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 47 && talus_dearic_coa_dathnaeya_handleBranch47(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 48 && talus_dearic_coa_dathnaeya_handleBranch48(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 49 && talus_dearic_coa_dathnaeya_handleBranch49(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 50 && talus_dearic_coa_dathnaeya_handleBranch50(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 53 && talus_dearic_coa_dathnaeya_handleBranch53(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 55 && talus_dearic_coa_dathnaeya_handleBranch55(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 57 && talus_dearic_coa_dathnaeya_handleBranch57(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 59 && talus_dearic_coa_dathnaeya_handleBranch59(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        chat.chat(npc, "Error:  Fell through all branches and responses for OnNpcConversationResponse.");
        utils.removeScriptVar(player, "conversation.talus_dearic_coa_dathnaeya.branchId");
        return SCRIPT_CONTINUE;
    }
}
