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
import script.library.space_quest;
import script.library.utils;

public class naboo_deeja_peak_tanoa_vills extends script.base_script
{
    public naboo_deeja_peak_tanoa_vills()
    {
    }
    public static String c_stringFile = "conversation/naboo_deeja_peak_tanoa_vills";
    public boolean naboo_deeja_peak_tanoa_vills_condition__defaultCondition(obj_id player, obj_id npc) throws InterruptedException
    {
        return true;
    }
    public boolean naboo_deeja_peak_tanoa_vills_condition_startSequencerQuest(obj_id player, obj_id npc) throws InterruptedException
    {
        return groundquests.isQuestActive(player, "naboo_deeja_peak_goto_tanoa") || groundquests.hasCompletedQuest(player, "naboo_deeja_peak_goto_prof_hudmasse");
    }
    public boolean naboo_deeja_peak_tanoa_vills_condition_onPartsQuest(obj_id player, obj_id npc) throws InterruptedException
    {
        return groundquests.isQuestActive(player, "naboo_deeja_peak_sequencer_parts");
    }
    public boolean naboo_deeja_peak_tanoa_vills_condition_finishedPartsQuest(obj_id player, obj_id npc) throws InterruptedException
    {
        return groundquests.isTaskActive(player, "naboo_deeja_peak_sequencer_parts", "deeja_peak_sequencer_parts_04") || groundquests.hasCompletedQuest(player, "naboo_deeja_peak_sequencer_parts");
    }
    public boolean naboo_deeja_peak_tanoa_vills_condition_onCrashQuest(obj_id player, obj_id npc) throws InterruptedException
    {
        return groundquests.isQuestActive(player, "naboo_deeja_peak_sequencer_crash");
    }
    public boolean naboo_deeja_peak_tanoa_vills_condition_finishedCrashQuest(obj_id player, obj_id npc) throws InterruptedException
    {
        return groundquests.isTaskActive(player, "naboo_deeja_peak_sequencer_crash", "deeja_peak_sequencer_crash_06") || groundquests.hasCompletedQuest(player, "naboo_deeja_peak_sequencer_crash");
    }
    public boolean naboo_deeja_peak_tanoa_vills_condition_onMissingQuest(obj_id player, obj_id npc) throws InterruptedException
    {
        return groundquests.isQuestActive(player, "naboo_deeja_peak_sequencer_missing");
    }
    public boolean naboo_deeja_peak_tanoa_vills_condition_finishedMissingQuest(obj_id player, obj_id npc) throws InterruptedException
    {
        return groundquests.isTaskActive(player, "naboo_deeja_peak_sequencer_missing", "deeja_peak_sequencer_missing_06") || groundquests.hasCompletedQuest(player, "naboo_deeja_peak_sequencer_missing");
    }
    public boolean naboo_deeja_peak_tanoa_vills_condition_completedSequencerQuest(obj_id player, obj_id npc) throws InterruptedException
    {
        return groundquests.isTaskActive(player, "naboo_deeja_peak_sequencer_scanner", "deeja_peak_sequencer_scanner_07") || groundquests.hasCompletedQuest(player, "naboo_deeja_peak_sequencer_scanner");
    }
    public boolean naboo_deeja_peak_tanoa_vills_condition_onScannerQuest(obj_id player, obj_id npc) throws InterruptedException
    {
        return groundquests.isQuestActive(player, "naboo_deeja_peak_sequencer_scanner");
    }
    public boolean naboo_deeja_peak_tanoa_vills_condition_finishedScannerQuest(obj_id player, obj_id npc) throws InterruptedException
    {
        return groundquests.isTaskActive(player, "naboo_deeja_peak_sequencer_scanner", "deeja_peak_sequencer_scanner_06");
    }
    public void naboo_deeja_peak_tanoa_vills_action_grantPartsQuest(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.requestGrantQuest(player, "naboo_deeja_peak_sequencer_parts");
    }
    public void naboo_deeja_peak_tanoa_vills_action_grantCrashQuest(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.requestGrantQuest(player, "naboo_deeja_peak_sequencer_crash");
    }
    public void naboo_deeja_peak_tanoa_vills_action_grantMissingQuest(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.requestGrantQuest(player, "naboo_deeja_peak_sequencer_missing");
    }
    public void naboo_deeja_peak_tanoa_vills_action_grantScannerQuest(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.requestGrantQuest(player, "naboo_deeja_peak_sequencer_scanner");
    }
    public void naboo_deeja_peak_tanoa_vills_action_signel_scanner_06(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.sendSignal(player, "deeja_peak_sequencer_scanner_06");
    }
    public void naboo_deeja_peak_tanoa_vills_action_endMissingQuest(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.sendSignal(player, "deeja_peak_sequencer_missing_06");
    }
    public void naboo_deeja_peak_tanoa_vills_action_endCrashQuest(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.sendSignal(player, "deeja_peak_sequencer_crash_06");
    }
    public void naboo_deeja_peak_tanoa_vills_action_endPartsQuest(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.sendSignal(player, "deeja_peak_sequencer_parts_04");
    }
    public void naboo_deeja_peak_tanoa_vills_action_endGotoTanoa(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.sendSignal(player, "deeja_peak_goto_tanoa");
    }
    public int naboo_deeja_peak_tanoa_vills_handleBranch2(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_87"))
        {
            if (naboo_deeja_peak_tanoa_vills_condition__defaultCondition(player, npc))
            {
                naboo_deeja_peak_tanoa_vills_action_signel_scanner_06(player, npc);
                string_id message = new string_id(c_stringFile, "s_88");
                utils.removeScriptVar(player, "conversation.naboo_deeja_peak_tanoa_vills.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int naboo_deeja_peak_tanoa_vills_handleBranch5(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_58"))
        {
            if (naboo_deeja_peak_tanoa_vills_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_59");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (naboo_deeja_peak_tanoa_vills_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_83");
                    }
                    utils.setScriptVar(player, "conversation.naboo_deeja_peak_tanoa_vills.branchId", 6);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.naboo_deeja_peak_tanoa_vills.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int naboo_deeja_peak_tanoa_vills_handleBranch6(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_83"))
        {
            if (naboo_deeja_peak_tanoa_vills_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_84");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (naboo_deeja_peak_tanoa_vills_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_85");
                    }
                    utils.setScriptVar(player, "conversation.naboo_deeja_peak_tanoa_vills.branchId", 7);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.naboo_deeja_peak_tanoa_vills.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int naboo_deeja_peak_tanoa_vills_handleBranch7(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_85"))
        {
            if (naboo_deeja_peak_tanoa_vills_condition__defaultCondition(player, npc))
            {
                naboo_deeja_peak_tanoa_vills_action_grantScannerQuest(player, npc);
                string_id message = new string_id(c_stringFile, "s_86");
                utils.removeScriptVar(player, "conversation.naboo_deeja_peak_tanoa_vills.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int naboo_deeja_peak_tanoa_vills_handleBranch10(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_53"))
        {
            if (naboo_deeja_peak_tanoa_vills_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_54");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (naboo_deeja_peak_tanoa_vills_condition__defaultCondition(player, npc))
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
                    utils.setScriptVar(player, "conversation.naboo_deeja_peak_tanoa_vills.branchId", 11);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.naboo_deeja_peak_tanoa_vills.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int naboo_deeja_peak_tanoa_vills_handleBranch11(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_81"))
        {
            if (naboo_deeja_peak_tanoa_vills_condition__defaultCondition(player, npc))
            {
                naboo_deeja_peak_tanoa_vills_action_grantMissingQuest(player, npc);
                string_id message = new string_id(c_stringFile, "s_82");
                utils.removeScriptVar(player, "conversation.naboo_deeja_peak_tanoa_vills.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int naboo_deeja_peak_tanoa_vills_handleBranch14(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_73"))
        {
            if (naboo_deeja_peak_tanoa_vills_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_74");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (naboo_deeja_peak_tanoa_vills_condition__defaultCondition(player, npc))
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
                    utils.setScriptVar(player, "conversation.naboo_deeja_peak_tanoa_vills.branchId", 15);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.naboo_deeja_peak_tanoa_vills.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int naboo_deeja_peak_tanoa_vills_handleBranch15(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_75"))
        {
            if (naboo_deeja_peak_tanoa_vills_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_76");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (naboo_deeja_peak_tanoa_vills_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_78");
                    }
                    utils.setScriptVar(player, "conversation.naboo_deeja_peak_tanoa_vills.branchId", 16);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.naboo_deeja_peak_tanoa_vills.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int naboo_deeja_peak_tanoa_vills_handleBranch16(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_78"))
        {
            if (naboo_deeja_peak_tanoa_vills_condition__defaultCondition(player, npc))
            {
                naboo_deeja_peak_tanoa_vills_action_grantCrashQuest(player, npc);
                string_id message = new string_id(c_stringFile, "s_80");
                utils.removeScriptVar(player, "conversation.naboo_deeja_peak_tanoa_vills.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int naboo_deeja_peak_tanoa_vills_handleBranch19(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_37"))
        {
            if (naboo_deeja_peak_tanoa_vills_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_39");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (naboo_deeja_peak_tanoa_vills_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_41");
                    }
                    utils.setScriptVar(player, "conversation.naboo_deeja_peak_tanoa_vills.branchId", 20);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.naboo_deeja_peak_tanoa_vills.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int naboo_deeja_peak_tanoa_vills_handleBranch20(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_41"))
        {
            if (naboo_deeja_peak_tanoa_vills_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_60");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (naboo_deeja_peak_tanoa_vills_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_61");
                    }
                    utils.setScriptVar(player, "conversation.naboo_deeja_peak_tanoa_vills.branchId", 21);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.naboo_deeja_peak_tanoa_vills.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int naboo_deeja_peak_tanoa_vills_handleBranch21(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_61"))
        {
            if (naboo_deeja_peak_tanoa_vills_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_62");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (naboo_deeja_peak_tanoa_vills_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_63");
                    }
                    utils.setScriptVar(player, "conversation.naboo_deeja_peak_tanoa_vills.branchId", 22);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.naboo_deeja_peak_tanoa_vills.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int naboo_deeja_peak_tanoa_vills_handleBranch22(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_63"))
        {
            if (naboo_deeja_peak_tanoa_vills_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_64");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (naboo_deeja_peak_tanoa_vills_condition__defaultCondition(player, npc))
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
                    utils.setScriptVar(player, "conversation.naboo_deeja_peak_tanoa_vills.branchId", 23);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.naboo_deeja_peak_tanoa_vills.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int naboo_deeja_peak_tanoa_vills_handleBranch23(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_65"))
        {
            if (naboo_deeja_peak_tanoa_vills_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_66");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (naboo_deeja_peak_tanoa_vills_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (naboo_deeja_peak_tanoa_vills_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_67");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_79");
                    }
                    utils.setScriptVar(player, "conversation.naboo_deeja_peak_tanoa_vills.branchId", 24);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.naboo_deeja_peak_tanoa_vills.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int naboo_deeja_peak_tanoa_vills_handleBranch24(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_67"))
        {
            if (naboo_deeja_peak_tanoa_vills_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_68");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (naboo_deeja_peak_tanoa_vills_condition__defaultCondition(player, npc))
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
                    utils.setScriptVar(player, "conversation.naboo_deeja_peak_tanoa_vills.branchId", 25);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.naboo_deeja_peak_tanoa_vills.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_79"))
        {
            if (naboo_deeja_peak_tanoa_vills_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_90");
                utils.removeScriptVar(player, "conversation.naboo_deeja_peak_tanoa_vills.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int naboo_deeja_peak_tanoa_vills_handleBranch25(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_71"))
        {
            naboo_deeja_peak_tanoa_vills_action_endGotoTanoa(player, npc);
            if (naboo_deeja_peak_tanoa_vills_condition__defaultCondition(player, npc))
            {
                naboo_deeja_peak_tanoa_vills_action_grantPartsQuest(player, npc);
                string_id message = new string_id(c_stringFile, "s_72");
                utils.removeScriptVar(player, "conversation.naboo_deeja_peak_tanoa_vills.branchId");
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
            detachScript(self, "conversation.naboo_deeja_peak_tanoa_vills");
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
        faceTo(self, player);
        return SCRIPT_CONTINUE;
    }
    public int OnIncapacitated(obj_id self, obj_id killer) throws InterruptedException
    {
        clearCondition(self, CONDITION_CONVERSABLE);
        detachScript(self, "conversation.naboo_deeja_peak_tanoa_vills");
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
        if (naboo_deeja_peak_tanoa_vills_condition_completedSequencerQuest(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_57");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        if (naboo_deeja_peak_tanoa_vills_condition_finishedScannerQuest(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_20");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (naboo_deeja_peak_tanoa_vills_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_87");
                }
                utils.setScriptVar(player, "conversation.naboo_deeja_peak_tanoa_vills.branchId", 2);
                npcStartConversation(player, npc, "naboo_deeja_peak_tanoa_vills", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (naboo_deeja_peak_tanoa_vills_condition_onScannerQuest(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_21");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        if (naboo_deeja_peak_tanoa_vills_condition_finishedMissingQuest(player, npc))
        {
            naboo_deeja_peak_tanoa_vills_action_endMissingQuest(player, npc);
            string_id message = new string_id(c_stringFile, "s_56");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (naboo_deeja_peak_tanoa_vills_condition__defaultCondition(player, npc))
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
                utils.setScriptVar(player, "conversation.naboo_deeja_peak_tanoa_vills.branchId", 5);
                npcStartConversation(player, npc, "naboo_deeja_peak_tanoa_vills", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (naboo_deeja_peak_tanoa_vills_condition_onMissingQuest(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_55");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        if (naboo_deeja_peak_tanoa_vills_condition_finishedCrashQuest(player, npc))
        {
            naboo_deeja_peak_tanoa_vills_action_endCrashQuest(player, npc);
            string_id message = new string_id(c_stringFile, "s_52");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (naboo_deeja_peak_tanoa_vills_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_53");
                }
                utils.setScriptVar(player, "conversation.naboo_deeja_peak_tanoa_vills.branchId", 10);
                npcStartConversation(player, npc, "naboo_deeja_peak_tanoa_vills", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (naboo_deeja_peak_tanoa_vills_condition_onCrashQuest(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_51");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        if (naboo_deeja_peak_tanoa_vills_condition_finishedPartsQuest(player, npc))
        {
            naboo_deeja_peak_tanoa_vills_action_endPartsQuest(player, npc);
            string_id message = new string_id(c_stringFile, "s_48");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (naboo_deeja_peak_tanoa_vills_condition__defaultCondition(player, npc))
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
                utils.setScriptVar(player, "conversation.naboo_deeja_peak_tanoa_vills.branchId", 14);
                npcStartConversation(player, npc, "naboo_deeja_peak_tanoa_vills", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (naboo_deeja_peak_tanoa_vills_condition_onPartsQuest(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_47");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        if (naboo_deeja_peak_tanoa_vills_condition__defaultCondition(player, npc))
        {
            doAnimationAction(npc, "check_wrist_device");
            string_id message = new string_id(c_stringFile, "s_43");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (naboo_deeja_peak_tanoa_vills_condition_startSequencerQuest(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_37");
                }
                utils.setScriptVar(player, "conversation.naboo_deeja_peak_tanoa_vills.branchId", 19);
                npcStartConversation(player, npc, "naboo_deeja_peak_tanoa_vills", message, responses);
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
        if (!conversationId.equals("naboo_deeja_peak_tanoa_vills"))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id npc = self;
        int branchId = utils.getIntScriptVar(player, "conversation.naboo_deeja_peak_tanoa_vills.branchId");
        if (branchId == 2 && naboo_deeja_peak_tanoa_vills_handleBranch2(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 5 && naboo_deeja_peak_tanoa_vills_handleBranch5(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 6 && naboo_deeja_peak_tanoa_vills_handleBranch6(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 7 && naboo_deeja_peak_tanoa_vills_handleBranch7(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 10 && naboo_deeja_peak_tanoa_vills_handleBranch10(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 11 && naboo_deeja_peak_tanoa_vills_handleBranch11(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 14 && naboo_deeja_peak_tanoa_vills_handleBranch14(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 15 && naboo_deeja_peak_tanoa_vills_handleBranch15(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 16 && naboo_deeja_peak_tanoa_vills_handleBranch16(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 19 && naboo_deeja_peak_tanoa_vills_handleBranch19(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 20 && naboo_deeja_peak_tanoa_vills_handleBranch20(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 21 && naboo_deeja_peak_tanoa_vills_handleBranch21(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 22 && naboo_deeja_peak_tanoa_vills_handleBranch22(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 23 && naboo_deeja_peak_tanoa_vills_handleBranch23(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 24 && naboo_deeja_peak_tanoa_vills_handleBranch24(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 25 && naboo_deeja_peak_tanoa_vills_handleBranch25(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        chat.chat(npc, "Error:  Fell through all branches and responses for OnNpcConversationResponse.");
        utils.removeScriptVar(player, "conversation.naboo_deeja_peak_tanoa_vills.branchId");
        return SCRIPT_CONTINUE;
    }
}
