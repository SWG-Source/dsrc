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

public class naboo_deeja_peak_prof_hudmasse extends script.base_script
{
    public naboo_deeja_peak_prof_hudmasse()
    {
    }
    public static String c_stringFile = "conversation/naboo_deeja_peak_prof_hudmasse";
    public boolean naboo_deeja_peak_prof_hudmasse_condition__defaultCondition(obj_id player, obj_id npc) throws InterruptedException
    {
        return true;
    }
    public boolean naboo_deeja_peak_prof_hudmasse_condition_sentToProfHudmasse(obj_id player, obj_id npc) throws InterruptedException
    {
        return groundquests.isQuestActive(player, "legacy_naboo_droid_module_3") || groundquests.hasCompletedQuest(player, "legacy_naboo_droid_module_3");
    }
    public boolean naboo_deeja_peak_prof_hudmasse_condition_needsSequencerQuest(obj_id player, obj_id npc) throws InterruptedException
    {
        return !groundquests.isQuestActive(player, "naboo_deeja_peak_goto_tanoa") && !groundquests.isQuestActive(player, "naboo_deeja_peak_sequencer_parts") && !groundquests.hasCompletedQuest(player, "naboo_deeja_peak_sequencer_parts");
    }
    public boolean naboo_deeja_peak_prof_hudmasse_condition_completedBeaconAndSequencer(obj_id player, obj_id npc) throws InterruptedException
    {
        return groundquests.hasCompletedQuest(player, "naboo_deeja_peak_beacon_mercs") && groundquests.hasCompletedQuest(player, "naboo_deeja_peak_sequencer_scanner");
    }
    public boolean naboo_deeja_peak_prof_hudmasse_condition_hasDeejaPeakQuest(obj_id player, obj_id npc) throws InterruptedException
    {
        return groundquests.isQuestActive(player, "naboo_deeja_peak_goto_vance") || groundquests.isQuestActive(player, "naboo_deeja_peak_beacon_safety") || groundquests.hasCompletedQuest(player, "naboo_deeja_peak_beacon_safety") || groundquests.isQuestActive(player, "naboo_deeja_peak_goto_tanoa") || groundquests.isQuestActive(player, "naboo_deeja_peak_sequencer_parts") || groundquests.hasCompletedQuest(player, "naboo_deeja_peak_sequencer_parts");
    }
    public boolean naboo_deeja_peak_prof_hudmasse_condition_needsBeaconQuest(obj_id player, obj_id npc) throws InterruptedException
    {
        return !groundquests.isQuestActive(player, "naboo_deeja_peak_goto_vance") && !groundquests.isQuestActive(player, "naboo_deeja_peak_beacon_safety") && !groundquests.hasCompletedQuest(player, "naboo_deeja_peak_beacon_safety");
    }
    public boolean naboo_deeja_peak_prof_hudmasse_condition_hasSequencer(obj_id player, obj_id npc) throws InterruptedException
    {
        return groundquests.hasCompletedQuest(player, "naboo_deeja_peak_sequencer_scanner");
    }
    public boolean naboo_deeja_peak_prof_hudmasse_condition_hasBeacon(obj_id player, obj_id npc) throws InterruptedException
    {
        return groundquests.hasCompletedQuest(player, "naboo_deeja_peak_beacon_mercs");
    }
    public boolean naboo_deeja_peak_prof_hudmasse_condition_returningWithBeacon(obj_id player, obj_id npc) throws InterruptedException
    {
        return groundquests.isTaskActive(player, "naboo_deeja_peak_beacon_mercs", "deeja_peak_beacon_mercs_06");
    }
    public boolean naboo_deeja_peak_prof_hudmasse_condition_returningWithSequencer(obj_id player, obj_id npc) throws InterruptedException
    {
        return groundquests.isTaskActive(player, "naboo_deeja_peak_sequencer_scanner", "deeja_peak_sequencer_scanner_07");
    }
    public boolean naboo_deeja_peak_prof_hudmasse_condition_needsModulePt2(obj_id player, obj_id npc) throws InterruptedException
    {
        return !groundquests.isQuestActive(player, "legacy_naboo_droid_module_3_pt2") && !groundquests.hasCompletedQuest(player, "legacy_naboo_droid_module_3_pt2");
    }
    public void naboo_deeja_peak_prof_hudmasse_action_sendToTanoa(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.requestGrantQuest(player, "naboo_deeja_peak_goto_tanoa");
    }
    public void naboo_deeja_peak_prof_hudmasse_action_sendToVance(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.requestGrantQuest(player, "naboo_deeja_peak_goto_vance");
    }
    public void naboo_deeja_peak_prof_hudmasse_action_endGotoProfHudmasse(obj_id player, obj_id npc) throws InterruptedException
    {
        if (groundquests.isQuestActive(player, "legacy_naboo_droid_module_3"))
        {
            groundquests.sendSignal(player, "findDroidPart3_01");
        }
        return;
    }
    public void naboo_deeja_peak_prof_hudmasse_action_endBeacon(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.sendSignal(player, "deeja_peak_beacon_mercs_06");
    }
    public void naboo_deeja_peak_prof_hudmasse_action_endSequencer(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.sendSignal(player, "deeja_peak_sequencer_scanner_07");
    }
    public void naboo_deeja_peak_prof_hudmasse_action_sendToStonewall(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.requestGrantQuest(player, "legacy_naboo_droid_module_3_pt2");
    }
    public int naboo_deeja_peak_prof_hudmasse_handleBranch1(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_60"))
        {
            if (naboo_deeja_peak_prof_hudmasse_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "manipulate_medium");
                naboo_deeja_peak_prof_hudmasse_action_sendToStonewall(player, npc);
                string_id message = new string_id(c_stringFile, "s_62");
                utils.removeScriptVar(player, "conversation.naboo_deeja_peak_prof_hudmasse.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int naboo_deeja_peak_prof_hudmasse_handleBranch3(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_29"))
        {
            if (naboo_deeja_peak_prof_hudmasse_condition__defaultCondition(player, npc))
            {
                naboo_deeja_peak_prof_hudmasse_action_endBeacon(player, npc);
                string_id message = new string_id(c_stringFile, "s_33");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (naboo_deeja_peak_prof_hudmasse_condition_hasSequencer(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (!naboo_deeja_peak_prof_hudmasse_condition_hasSequencer(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_36");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_40");
                    }
                    utils.setScriptVar(player, "conversation.naboo_deeja_peak_prof_hudmasse.branchId", 4);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.naboo_deeja_peak_prof_hudmasse.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_30"))
        {
            if (naboo_deeja_peak_prof_hudmasse_condition__defaultCondition(player, npc))
            {
                naboo_deeja_peak_prof_hudmasse_action_endSequencer(player, npc);
                string_id message = new string_id(c_stringFile, "s_35");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (naboo_deeja_peak_prof_hudmasse_condition_hasBeacon(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (!naboo_deeja_peak_prof_hudmasse_condition_hasBeacon(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_37");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_49");
                    }
                    utils.setScriptVar(player, "conversation.naboo_deeja_peak_prof_hudmasse.branchId", 8);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.naboo_deeja_peak_prof_hudmasse.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_65"))
        {
            if (naboo_deeja_peak_prof_hudmasse_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_105");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (naboo_deeja_peak_prof_hudmasse_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_107");
                    }
                    utils.setScriptVar(player, "conversation.naboo_deeja_peak_prof_hudmasse.branchId", 17);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.naboo_deeja_peak_prof_hudmasse.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_66"))
        {
            if (naboo_deeja_peak_prof_hudmasse_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_113");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (naboo_deeja_peak_prof_hudmasse_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_115");
                    }
                    utils.setScriptVar(player, "conversation.naboo_deeja_peak_prof_hudmasse.branchId", 19);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.naboo_deeja_peak_prof_hudmasse.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int naboo_deeja_peak_prof_hudmasse_handleBranch4(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_36"))
        {
            if (naboo_deeja_peak_prof_hudmasse_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_39");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (naboo_deeja_peak_prof_hudmasse_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_42");
                    }
                    utils.setScriptVar(player, "conversation.naboo_deeja_peak_prof_hudmasse.branchId", 5);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.naboo_deeja_peak_prof_hudmasse.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_40"))
        {
            if (naboo_deeja_peak_prof_hudmasse_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_41");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (naboo_deeja_peak_prof_hudmasse_condition_needsSequencerQuest(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_54");
                    }
                    utils.setScriptVar(player, "conversation.naboo_deeja_peak_prof_hudmasse.branchId", 7);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.naboo_deeja_peak_prof_hudmasse.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int naboo_deeja_peak_prof_hudmasse_handleBranch5(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_42"))
        {
            if (naboo_deeja_peak_prof_hudmasse_condition__defaultCondition(player, npc))
            {
                naboo_deeja_peak_prof_hudmasse_action_sendToStonewall(player, npc);
                string_id message = new string_id(c_stringFile, "s_43");
                utils.removeScriptVar(player, "conversation.naboo_deeja_peak_prof_hudmasse.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int naboo_deeja_peak_prof_hudmasse_handleBranch7(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_54"))
        {
            if (naboo_deeja_peak_prof_hudmasse_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_113");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (naboo_deeja_peak_prof_hudmasse_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_115");
                    }
                    utils.setScriptVar(player, "conversation.naboo_deeja_peak_prof_hudmasse.branchId", 19);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.naboo_deeja_peak_prof_hudmasse.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int naboo_deeja_peak_prof_hudmasse_handleBranch8(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_37"))
        {
            if (naboo_deeja_peak_prof_hudmasse_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_38");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (naboo_deeja_peak_prof_hudmasse_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_55");
                    }
                    utils.setScriptVar(player, "conversation.naboo_deeja_peak_prof_hudmasse.branchId", 9);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.naboo_deeja_peak_prof_hudmasse.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_49"))
        {
            if (naboo_deeja_peak_prof_hudmasse_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_50");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (naboo_deeja_peak_prof_hudmasse_condition_needsBeaconQuest(player, npc))
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
                    utils.setScriptVar(player, "conversation.naboo_deeja_peak_prof_hudmasse.branchId", 11);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.naboo_deeja_peak_prof_hudmasse.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int naboo_deeja_peak_prof_hudmasse_handleBranch9(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_55"))
        {
            if (naboo_deeja_peak_prof_hudmasse_condition__defaultCondition(player, npc))
            {
                naboo_deeja_peak_prof_hudmasse_action_sendToStonewall(player, npc);
                string_id message = new string_id(c_stringFile, "s_56");
                utils.removeScriptVar(player, "conversation.naboo_deeja_peak_prof_hudmasse.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int naboo_deeja_peak_prof_hudmasse_handleBranch11(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_52"))
        {
            if (naboo_deeja_peak_prof_hudmasse_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_105");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (naboo_deeja_peak_prof_hudmasse_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_107");
                    }
                    utils.setScriptVar(player, "conversation.naboo_deeja_peak_prof_hudmasse.branchId", 17);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.naboo_deeja_peak_prof_hudmasse.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int naboo_deeja_peak_prof_hudmasse_handleBranch12(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_47"))
        {
            if (naboo_deeja_peak_prof_hudmasse_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_48");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (naboo_deeja_peak_prof_hudmasse_condition__defaultCondition(player, npc))
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
                    utils.setScriptVar(player, "conversation.naboo_deeja_peak_prof_hudmasse.branchId", 13);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.naboo_deeja_peak_prof_hudmasse.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int naboo_deeja_peak_prof_hudmasse_handleBranch13(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_34"))
        {
            if (naboo_deeja_peak_prof_hudmasse_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_53");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (naboo_deeja_peak_prof_hudmasse_condition__defaultCondition(player, npc))
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
                    utils.setScriptVar(player, "conversation.naboo_deeja_peak_prof_hudmasse.branchId", 14);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.naboo_deeja_peak_prof_hudmasse.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int naboo_deeja_peak_prof_hudmasse_handleBranch14(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_78"))
        {
            if (naboo_deeja_peak_prof_hudmasse_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_79");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (naboo_deeja_peak_prof_hudmasse_condition__defaultCondition(player, npc))
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
                    utils.setScriptVar(player, "conversation.naboo_deeja_peak_prof_hudmasse.branchId", 15);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.naboo_deeja_peak_prof_hudmasse.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int naboo_deeja_peak_prof_hudmasse_handleBranch15(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_100"))
        {
            if (naboo_deeja_peak_prof_hudmasse_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_101");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (naboo_deeja_peak_prof_hudmasse_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (naboo_deeja_peak_prof_hudmasse_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (naboo_deeja_peak_prof_hudmasse_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_119");
                    }
                    utils.setScriptVar(player, "conversation.naboo_deeja_peak_prof_hudmasse.branchId", 16);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.naboo_deeja_peak_prof_hudmasse.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int naboo_deeja_peak_prof_hudmasse_handleBranch16(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_103"))
        {
            if (naboo_deeja_peak_prof_hudmasse_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_105");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (naboo_deeja_peak_prof_hudmasse_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_107");
                    }
                    utils.setScriptVar(player, "conversation.naboo_deeja_peak_prof_hudmasse.branchId", 17);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.naboo_deeja_peak_prof_hudmasse.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_111"))
        {
            if (naboo_deeja_peak_prof_hudmasse_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_113");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (naboo_deeja_peak_prof_hudmasse_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_115");
                    }
                    utils.setScriptVar(player, "conversation.naboo_deeja_peak_prof_hudmasse.branchId", 19);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.naboo_deeja_peak_prof_hudmasse.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_119"))
        {
            if (naboo_deeja_peak_prof_hudmasse_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_121");
                utils.removeScriptVar(player, "conversation.naboo_deeja_peak_prof_hudmasse.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int naboo_deeja_peak_prof_hudmasse_handleBranch17(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_107"))
        {
            if (naboo_deeja_peak_prof_hudmasse_condition__defaultCondition(player, npc))
            {
                naboo_deeja_peak_prof_hudmasse_action_sendToVance(player, npc);
                string_id message = new string_id(c_stringFile, "s_109");
                utils.removeScriptVar(player, "conversation.naboo_deeja_peak_prof_hudmasse.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int naboo_deeja_peak_prof_hudmasse_handleBranch19(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_115"))
        {
            if (naboo_deeja_peak_prof_hudmasse_condition__defaultCondition(player, npc))
            {
                naboo_deeja_peak_prof_hudmasse_action_sendToTanoa(player, npc);
                string_id message = new string_id(c_stringFile, "s_117");
                utils.removeScriptVar(player, "conversation.naboo_deeja_peak_prof_hudmasse.branchId");
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
            detachScript(self, "conversation.naboo_deeja_peak_prof_hudmasse");
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
        detachScript(self, "conversation.naboo_deeja_peak_prof_hudmasse");
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
        if (naboo_deeja_peak_prof_hudmasse_condition_completedBeaconAndSequencer(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_44");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (naboo_deeja_peak_prof_hudmasse_condition_needsModulePt2(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_60");
                }
                utils.setScriptVar(player, "conversation.naboo_deeja_peak_prof_hudmasse.branchId", 1);
                npcStartConversation(player, npc, "naboo_deeja_peak_prof_hudmasse", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (naboo_deeja_peak_prof_hudmasse_condition_hasDeejaPeakQuest(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_45");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (naboo_deeja_peak_prof_hudmasse_condition_returningWithBeacon(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            boolean hasResponse1 = false;
            if (naboo_deeja_peak_prof_hudmasse_condition_returningWithSequencer(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse1 = true;
            }
            boolean hasResponse2 = false;
            if (naboo_deeja_peak_prof_hudmasse_condition_needsBeaconQuest(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse2 = true;
            }
            boolean hasResponse3 = false;
            if (naboo_deeja_peak_prof_hudmasse_condition_needsSequencerQuest(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_29");
                }
                if (hasResponse1)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_30");
                }
                if (hasResponse2)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_65");
                }
                if (hasResponse3)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_66");
                }
                utils.setScriptVar(player, "conversation.naboo_deeja_peak_prof_hudmasse.branchId", 3);
                npcStartConversation(player, npc, "naboo_deeja_peak_prof_hudmasse", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (naboo_deeja_peak_prof_hudmasse_condition_sentToProfHudmasse(player, npc))
        {
            naboo_deeja_peak_prof_hudmasse_action_endGotoProfHudmasse(player, npc);
            string_id message = new string_id(c_stringFile, "s_46");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (naboo_deeja_peak_prof_hudmasse_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_47");
                }
                utils.setScriptVar(player, "conversation.naboo_deeja_peak_prof_hudmasse.branchId", 12);
                npcStartConversation(player, npc, "naboo_deeja_peak_prof_hudmasse", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (naboo_deeja_peak_prof_hudmasse_condition__defaultCondition(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_76");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        chat.chat(npc, "Error:  All conditions for OnStartNpcConversation were false.");
        return SCRIPT_CONTINUE;
    }
    public int OnNpcConversationResponse(obj_id self, String conversationId, obj_id player, string_id response) throws InterruptedException
    {
        if (!conversationId.equals("naboo_deeja_peak_prof_hudmasse"))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id npc = self;
        int branchId = utils.getIntScriptVar(player, "conversation.naboo_deeja_peak_prof_hudmasse.branchId");
        if (branchId == 1 && naboo_deeja_peak_prof_hudmasse_handleBranch1(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 3 && naboo_deeja_peak_prof_hudmasse_handleBranch3(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 4 && naboo_deeja_peak_prof_hudmasse_handleBranch4(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 5 && naboo_deeja_peak_prof_hudmasse_handleBranch5(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 7 && naboo_deeja_peak_prof_hudmasse_handleBranch7(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 8 && naboo_deeja_peak_prof_hudmasse_handleBranch8(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 9 && naboo_deeja_peak_prof_hudmasse_handleBranch9(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 11 && naboo_deeja_peak_prof_hudmasse_handleBranch11(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 12 && naboo_deeja_peak_prof_hudmasse_handleBranch12(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 13 && naboo_deeja_peak_prof_hudmasse_handleBranch13(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 14 && naboo_deeja_peak_prof_hudmasse_handleBranch14(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 15 && naboo_deeja_peak_prof_hudmasse_handleBranch15(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 16 && naboo_deeja_peak_prof_hudmasse_handleBranch16(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 17 && naboo_deeja_peak_prof_hudmasse_handleBranch17(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 19 && naboo_deeja_peak_prof_hudmasse_handleBranch19(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        chat.chat(npc, "Error:  Fell through all branches and responses for OnNpcConversationResponse.");
        utils.removeScriptVar(player, "conversation.naboo_deeja_peak_prof_hudmasse.branchId");
        return SCRIPT_CONTINUE;
    }
}
