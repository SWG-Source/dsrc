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

public class naboo_deeja_peak_vance_groten extends script.base_script
{
    public naboo_deeja_peak_vance_groten()
    {
    }
    public static String c_stringFile = "conversation/naboo_deeja_peak_vance_groten";
    public boolean naboo_deeja_peak_vance_groten_condition__defaultCondition(obj_id player, obj_id npc) throws InterruptedException
    {
        return true;
    }
    public boolean naboo_deeja_peak_vance_groten_condition_startBeaconQuest(obj_id player, obj_id npc) throws InterruptedException
    {
        return groundquests.isQuestActive(player, "naboo_deeja_peak_goto_vance") || groundquests.hasCompletedQuest(player, "naboo_deeja_peak_goto_prof_hudmasse");
    }
    public boolean naboo_deeja_peak_vance_groten_condition_onSafetyQuest(obj_id player, obj_id npc) throws InterruptedException
    {
        return groundquests.isQuestActive(player, "naboo_deeja_peak_beacon_safety");
    }
    public boolean naboo_deeja_peak_vance_groten_condition_finishedSafetyQuest(obj_id player, obj_id npc) throws InterruptedException
    {
        return groundquests.isTaskActive(player, "naboo_deeja_peak_beacon_safety", "deeja_peak_beacon_safety_05") || groundquests.hasCompletedQuest(player, "naboo_deeja_peak_beacon_safety");
    }
    public boolean naboo_deeja_peak_vance_groten_condition_onDarknessQuest(obj_id player, obj_id npc) throws InterruptedException
    {
        return groundquests.isQuestActive(player, "naboo_deeja_peak_beacon_darkness");
    }
    public boolean naboo_deeja_peak_vance_groten_condition_finishedDarknessQuest(obj_id player, obj_id npc) throws InterruptedException
    {
        return groundquests.isTaskActive(player, "naboo_deeja_peak_beacon_darkness", "deeja_peak_beacon_darkness_10") || groundquests.hasCompletedQuest(player, "naboo_deeja_peak_beacon_darkness");
    }
    public boolean naboo_deeja_peak_vance_groten_condition_onFavorQuest(obj_id player, obj_id npc) throws InterruptedException
    {
        return groundquests.isQuestActive(player, "naboo_deeja_peak_beacon_favor");
    }
    public boolean naboo_deeja_peak_vance_groten_condition_finishedFavorQuest(obj_id player, obj_id npc) throws InterruptedException
    {
        return groundquests.isTaskActive(player, "naboo_deeja_peak_beacon_favor", "deeja_peak_beacon_favor_05") || groundquests.hasCompletedQuest(player, "naboo_deeja_peak_beacon_favor");
    }
    public boolean naboo_deeja_peak_vance_groten_condition_completedBeaconQuest(obj_id player, obj_id npc) throws InterruptedException
    {
        return groundquests.isTaskActive(player, "naboo_deeja_peak_beacon_mercs", "deeja_peak_beacon_mercs_06") || groundquests.hasCompletedQuest(player, "naboo_deeja_peak_beacon_mercs");
    }
    public boolean naboo_deeja_peak_vance_groten_condition_onMercsQuest(obj_id player, obj_id npc) throws InterruptedException
    {
        return groundquests.isQuestActive(player, "naboo_deeja_peak_beacon_mercs");
    }
    public boolean naboo_deeja_peak_vance_groten_condition_finishedMercQuest(obj_id player, obj_id npc) throws InterruptedException
    {
        return groundquests.isTaskActive(player, "naboo_deeja_peak_beacon_mercs", "deeja_peak_beacon_mercs_05");
    }
    public boolean naboo_deeja_peak_vance_groten_condition_noLuckWithKippy(obj_id player, obj_id npc) throws InterruptedException
    {
        return groundquests.isTaskActive(player, "naboo_deeja_peak_beacon_darkness", "deeja_peak_beacon_darkness_04");
    }
    public void naboo_deeja_peak_vance_groten_action_grantSafetyQuest(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.requestGrantQuest(player, "naboo_deeja_peak_beacon_safety");
    }
    public void naboo_deeja_peak_vance_groten_action_grantDarknessQuest(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.requestGrantQuest(player, "naboo_deeja_peak_beacon_darkness");
    }
    public void naboo_deeja_peak_vance_groten_action_grantFavorQuest(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.requestGrantQuest(player, "naboo_deeja_peak_beacon_favor");
    }
    public void naboo_deeja_peak_vance_groten_action_grantMercsQuest(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.requestGrantQuest(player, "naboo_deeja_peak_beacon_mercs");
    }
    public void naboo_deeja_peak_vance_groten_action_signel_mercs_05(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.sendSignal(player, "deeja_peak_beacon_mercs_05");
    }
    public void naboo_deeja_peak_vance_groten_action_signal_darkness_04(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.sendSignal(player, "deeja_peak_beacon_darkness_04");
    }
    public void naboo_deeja_peak_vance_groten_action_endFavorQuest(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.sendSignal(player, "deeja_peak_beacon_favor_05");
    }
    public void naboo_deeja_peak_vance_groten_action_endDarknessQuest(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.sendSignal(player, "deeja_peak_beacon_darkness_10");
    }
    public void naboo_deeja_peak_vance_groten_action_endSafetyQuest(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.sendSignal(player, "deeja_peak_beacon_safety_05");
    }
    public void naboo_deeja_peak_vance_groten_action_endGotoVance(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.sendSignal(player, "deeja_peak_goto_vance");
    }
    public int naboo_deeja_peak_vance_groten_handleBranch2(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_87"))
        {
            if (naboo_deeja_peak_vance_groten_condition__defaultCondition(player, npc))
            {
                naboo_deeja_peak_vance_groten_action_signel_mercs_05(player, npc);
                string_id message = new string_id(c_stringFile, "s_88");
                utils.removeScriptVar(player, "conversation.naboo_deeja_peak_vance_groten.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int naboo_deeja_peak_vance_groten_handleBranch5(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_58"))
        {
            if (naboo_deeja_peak_vance_groten_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_59");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (naboo_deeja_peak_vance_groten_condition__defaultCondition(player, npc))
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
                    utils.setScriptVar(player, "conversation.naboo_deeja_peak_vance_groten.branchId", 6);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.naboo_deeja_peak_vance_groten.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int naboo_deeja_peak_vance_groten_handleBranch6(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_83"))
        {
            if (naboo_deeja_peak_vance_groten_condition__defaultCondition(player, npc))
            {
                naboo_deeja_peak_vance_groten_action_grantMercsQuest(player, npc);
                string_id message = new string_id(c_stringFile, "s_84");
                utils.removeScriptVar(player, "conversation.naboo_deeja_peak_vance_groten.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int naboo_deeja_peak_vance_groten_handleBranch9(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_53"))
        {
            if (naboo_deeja_peak_vance_groten_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_54");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (naboo_deeja_peak_vance_groten_condition__defaultCondition(player, npc))
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
                    utils.setScriptVar(player, "conversation.naboo_deeja_peak_vance_groten.branchId", 10);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.naboo_deeja_peak_vance_groten.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int naboo_deeja_peak_vance_groten_handleBranch10(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_81"))
        {
            if (naboo_deeja_peak_vance_groten_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_82");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (naboo_deeja_peak_vance_groten_condition__defaultCondition(player, npc))
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
                    utils.setScriptVar(player, "conversation.naboo_deeja_peak_vance_groten.branchId", 11);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.naboo_deeja_peak_vance_groten.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int naboo_deeja_peak_vance_groten_handleBranch11(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_98"))
        {
            if (naboo_deeja_peak_vance_groten_condition__defaultCondition(player, npc))
            {
                naboo_deeja_peak_vance_groten_action_grantFavorQuest(player, npc);
                string_id message = new string_id(c_stringFile, "s_99");
                utils.removeScriptVar(player, "conversation.naboo_deeja_peak_vance_groten.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int naboo_deeja_peak_vance_groten_handleBranch13(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_94"))
        {
            if (naboo_deeja_peak_vance_groten_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_95");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (naboo_deeja_peak_vance_groten_condition__defaultCondition(player, npc))
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
                    utils.setScriptVar(player, "conversation.naboo_deeja_peak_vance_groten.branchId", 14);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.naboo_deeja_peak_vance_groten.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int naboo_deeja_peak_vance_groten_handleBranch14(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_96"))
        {
            if (naboo_deeja_peak_vance_groten_condition__defaultCondition(player, npc))
            {
                naboo_deeja_peak_vance_groten_action_signal_darkness_04(player, npc);
                string_id message = new string_id(c_stringFile, "s_97");
                utils.removeScriptVar(player, "conversation.naboo_deeja_peak_vance_groten.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int naboo_deeja_peak_vance_groten_handleBranch17(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_73"))
        {
            if (naboo_deeja_peak_vance_groten_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_74");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (naboo_deeja_peak_vance_groten_condition__defaultCondition(player, npc))
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
                    utils.setScriptVar(player, "conversation.naboo_deeja_peak_vance_groten.branchId", 18);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.naboo_deeja_peak_vance_groten.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int naboo_deeja_peak_vance_groten_handleBranch18(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_75"))
        {
            if (naboo_deeja_peak_vance_groten_condition__defaultCondition(player, npc))
            {
                naboo_deeja_peak_vance_groten_action_grantDarknessQuest(player, npc);
                string_id message = new string_id(c_stringFile, "s_76");
                utils.removeScriptVar(player, "conversation.naboo_deeja_peak_vance_groten.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int naboo_deeja_peak_vance_groten_handleBranch21(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_37"))
        {
            if (naboo_deeja_peak_vance_groten_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_39");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (naboo_deeja_peak_vance_groten_condition__defaultCondition(player, npc))
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
                    utils.setScriptVar(player, "conversation.naboo_deeja_peak_vance_groten.branchId", 22);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.naboo_deeja_peak_vance_groten.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int naboo_deeja_peak_vance_groten_handleBranch22(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_41"))
        {
            if (naboo_deeja_peak_vance_groten_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_60");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (naboo_deeja_peak_vance_groten_condition__defaultCondition(player, npc))
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
                    utils.setScriptVar(player, "conversation.naboo_deeja_peak_vance_groten.branchId", 23);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.naboo_deeja_peak_vance_groten.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int naboo_deeja_peak_vance_groten_handleBranch23(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_61"))
        {
            if (naboo_deeja_peak_vance_groten_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_62");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (naboo_deeja_peak_vance_groten_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (naboo_deeja_peak_vance_groten_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_90");
                    }
                    utils.setScriptVar(player, "conversation.naboo_deeja_peak_vance_groten.branchId", 24);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.naboo_deeja_peak_vance_groten.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int naboo_deeja_peak_vance_groten_handleBranch24(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_63"))
        {
            naboo_deeja_peak_vance_groten_action_grantSafetyQuest(player, npc);
            if (naboo_deeja_peak_vance_groten_condition__defaultCondition(player, npc))
            {
                naboo_deeja_peak_vance_groten_action_endGotoVance(player, npc);
                string_id message = new string_id(c_stringFile, "s_64");
                utils.removeScriptVar(player, "conversation.naboo_deeja_peak_vance_groten.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_90"))
        {
            if (naboo_deeja_peak_vance_groten_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_92");
                utils.removeScriptVar(player, "conversation.naboo_deeja_peak_vance_groten.branchId");
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
            detachScript(self, "conversation.naboo_deeja_peak_vance_groten");
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
        detachScript(self, "conversation.naboo_deeja_peak_vance_groten");
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
        if (naboo_deeja_peak_vance_groten_condition_completedBeaconQuest(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_57");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        if (naboo_deeja_peak_vance_groten_condition_finishedMercQuest(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_20");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (naboo_deeja_peak_vance_groten_condition__defaultCondition(player, npc))
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
                utils.setScriptVar(player, "conversation.naboo_deeja_peak_vance_groten.branchId", 2);
                npcStartConversation(player, npc, "naboo_deeja_peak_vance_groten", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (naboo_deeja_peak_vance_groten_condition_onMercsQuest(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_21");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        if (naboo_deeja_peak_vance_groten_condition_finishedFavorQuest(player, npc))
        {
            naboo_deeja_peak_vance_groten_action_endFavorQuest(player, npc);
            string_id message = new string_id(c_stringFile, "s_56");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (naboo_deeja_peak_vance_groten_condition__defaultCondition(player, npc))
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
                utils.setScriptVar(player, "conversation.naboo_deeja_peak_vance_groten.branchId", 5);
                npcStartConversation(player, npc, "naboo_deeja_peak_vance_groten", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (naboo_deeja_peak_vance_groten_condition_onFavorQuest(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_55");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        if (naboo_deeja_peak_vance_groten_condition_finishedDarknessQuest(player, npc))
        {
            naboo_deeja_peak_vance_groten_action_endDarknessQuest(player, npc);
            string_id message = new string_id(c_stringFile, "s_52");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (naboo_deeja_peak_vance_groten_condition__defaultCondition(player, npc))
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
                utils.setScriptVar(player, "conversation.naboo_deeja_peak_vance_groten.branchId", 9);
                npcStartConversation(player, npc, "naboo_deeja_peak_vance_groten", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (naboo_deeja_peak_vance_groten_condition_noLuckWithKippy(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_93");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (naboo_deeja_peak_vance_groten_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_94");
                }
                utils.setScriptVar(player, "conversation.naboo_deeja_peak_vance_groten.branchId", 13);
                npcStartConversation(player, npc, "naboo_deeja_peak_vance_groten", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (naboo_deeja_peak_vance_groten_condition_onDarknessQuest(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_51");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        if (naboo_deeja_peak_vance_groten_condition_finishedSafetyQuest(player, npc))
        {
            naboo_deeja_peak_vance_groten_action_endSafetyQuest(player, npc);
            string_id message = new string_id(c_stringFile, "s_48");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (naboo_deeja_peak_vance_groten_condition__defaultCondition(player, npc))
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
                utils.setScriptVar(player, "conversation.naboo_deeja_peak_vance_groten.branchId", 17);
                npcStartConversation(player, npc, "naboo_deeja_peak_vance_groten", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (naboo_deeja_peak_vance_groten_condition_onSafetyQuest(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_47");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        if (naboo_deeja_peak_vance_groten_condition__defaultCondition(player, npc))
        {
            doAnimationAction(npc, "pose_proudly");
            string_id message = new string_id(c_stringFile, "s_43");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (naboo_deeja_peak_vance_groten_condition_startBeaconQuest(player, npc))
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
                utils.setScriptVar(player, "conversation.naboo_deeja_peak_vance_groten.branchId", 21);
                npcStartConversation(player, npc, "naboo_deeja_peak_vance_groten", message, responses);
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
        if (!conversationId.equals("naboo_deeja_peak_vance_groten"))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id npc = self;
        int branchId = utils.getIntScriptVar(player, "conversation.naboo_deeja_peak_vance_groten.branchId");
        if (branchId == 2 && naboo_deeja_peak_vance_groten_handleBranch2(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 5 && naboo_deeja_peak_vance_groten_handleBranch5(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 6 && naboo_deeja_peak_vance_groten_handleBranch6(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 9 && naboo_deeja_peak_vance_groten_handleBranch9(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 10 && naboo_deeja_peak_vance_groten_handleBranch10(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 11 && naboo_deeja_peak_vance_groten_handleBranch11(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 13 && naboo_deeja_peak_vance_groten_handleBranch13(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 14 && naboo_deeja_peak_vance_groten_handleBranch14(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 17 && naboo_deeja_peak_vance_groten_handleBranch17(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 18 && naboo_deeja_peak_vance_groten_handleBranch18(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 21 && naboo_deeja_peak_vance_groten_handleBranch21(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 22 && naboo_deeja_peak_vance_groten_handleBranch22(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 23 && naboo_deeja_peak_vance_groten_handleBranch23(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 24 && naboo_deeja_peak_vance_groten_handleBranch24(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        chat.chat(npc, "Error:  Fell through all branches and responses for OnNpcConversationResponse.");
        utils.removeScriptVar(player, "conversation.naboo_deeja_peak_vance_groten.branchId");
        return SCRIPT_CONTINUE;
    }
}
