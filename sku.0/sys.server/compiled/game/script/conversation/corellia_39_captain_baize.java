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

public class corellia_39_captain_baize extends script.base_script
{
    public corellia_39_captain_baize()
    {
    }
    public static String c_stringFile = "conversation/corellia_39_captain_baize";
    public boolean corellia_39_captain_baize_condition__defaultCondition(obj_id player, obj_id npc) throws InterruptedException
    {
        return true;
    }
    public boolean corellia_39_captain_baize_condition_assumedIdentityContinuedA(obj_id player, obj_id npc) throws InterruptedException
    {
        return groundquests.isQuestActive(player, "corellia_39_chirq_council_02") && groundquests.hasCompletedTask(player, "corellia_39_chirq_council_02", "chirq_council_02_03");
    }
    public boolean corellia_39_captain_baize_condition_assumedIdentityComplete(obj_id player, obj_id npc) throws InterruptedException
    {
        return groundquests.isTaskActive(player, "corellia_39_chirq_council_02b", "chirq_council_02_07") || groundquests.hasCompletedQuest(player, "corellia_39_chirq_council_02b");
    }
    public boolean corellia_39_captain_baize_condition_assumedIdentityFailed(obj_id player, obj_id npc) throws InterruptedException
    {
        return groundquests.isQuestActive(player, "corellia_39_chirq_council_02_failed");
    }
    public boolean corellia_39_captain_baize_condition_assumedIdentityContinuedB(obj_id player, obj_id npc) throws InterruptedException
    {
        return groundquests.hasCompletedQuest(player, "corellia_39_chirq_council_02");
    }
    public boolean corellia_39_captain_baize_condition_movingUpSpiceActive(obj_id player, obj_id npc) throws InterruptedException
    {
        return groundquests.isQuestActive(player, "corellia_39_chirq_council_03") && !groundquests.isTaskActive(player, "corellia_39_chirq_council_03", "chirq_council_03_03");
    }
    public boolean corellia_39_captain_baize_condition_movingUpSpiceComplete(obj_id player, obj_id npc) throws InterruptedException
    {
        return groundquests.isTaskActive(player, "corellia_39_chirq_council_03", "chirq_council_03_03");
    }
    public boolean corellia_39_captain_baize_condition_readyFor39(obj_id player, obj_id npc) throws InterruptedException
    {
        return groundquests.isTaskActive(player, "corellia_38_corsec_files_03", "corsec_files_03_01") || groundquests.hasCompletedQuest(player, "corellia_38_corsec_files_05");
    }
    public boolean corellia_39_captain_baize_condition_movingUpCoverUpA(obj_id player, obj_id npc) throws InterruptedException
    {
        return groundquests.isQuestActive(player, "corellia_39_chirq_council_03") && groundquests.hasCompletedTask(player, "corellia_39_chirq_council_03", "chirq_council_03_03");
    }
    public boolean corellia_39_captain_baize_condition_movingUpCoverUpB(obj_id player, obj_id npc) throws InterruptedException
    {
        return groundquests.hasCompletedQuest(player, "corellia_39_chirq_council_03");
    }
    public boolean corellia_39_captain_baize_condition_movingUpCorsecTerminal(obj_id player, obj_id npc) throws InterruptedException
    {
        return groundquests.isTaskActive(player, "corellia_39_chirq_council_03", "chirq_council_03_08");
    }
    public boolean corellia_39_captain_baize_condition_AllCompleted(obj_id player, obj_id npc) throws InterruptedException
    {
        return groundquests.hasCompletedQuest(player, "corellia_39_chirq_council_04");
    }
    public boolean corellia_39_captain_baize_condition_seekCouncilActive(obj_id player, obj_id npc) throws InterruptedException
    {
        return groundquests.isQuestActive(player, "corellia_39_chirq_council_01");
    }
    public boolean corellia_39_captain_baize_condition_seekCouncilComplete(obj_id player, obj_id npc) throws InterruptedException
    {
        return groundquests.isTaskActive(player, "corellia_39_chirq_council_01", "chirq_council_01_08") || groundquests.hasCompletedQuest(player, "corellia_39_chirq_council_01");
    }
    public boolean corellia_39_captain_baize_condition_assumedIdentityTrinsActive(obj_id player, obj_id npc) throws InterruptedException
    {
        return groundquests.isQuestActive(player, "corellia_39_chirq_council_02") && !groundquests.isTaskActive(player, "corellia_39_chirq_council_02", "chirq_council_02_03");
    }
    public boolean corellia_39_captain_baize_condition_assumedIdentityTrinsComplete(obj_id player, obj_id npc) throws InterruptedException
    {
        return groundquests.isTaskActive(player, "corellia_39_chirq_council_02", "chirq_council_02_03");
    }
    public boolean corellia_39_captain_baize_condition_movingUpComplete(obj_id player, obj_id npc) throws InterruptedException
    {
        return groundquests.isTaskActive(player, "corellia_39_chirq_council_03b", "chirq_council_03_11") || groundquests.hasCompletedQuest(player, "corellia_39_chirq_council_03b");
    }
    public boolean corellia_39_captain_baize_condition_eliteCouncilActive(obj_id player, obj_id npc) throws InterruptedException
    {
        return groundquests.isQuestActive(player, "corellia_39_chirq_council_04");
    }
    public boolean corellia_39_captain_baize_condition_eliteCouncilComplete(obj_id player, obj_id npc) throws InterruptedException
    {
        return groundquests.isTaskActive(player, "corellia_39_chirq_council_04", "chirq_council_04_03");
    }
    public boolean corellia_39_captain_baize_condition_needs40Pointer(obj_id player, obj_id npc) throws InterruptedException
    {
        return !groundquests.isQuestActiveOrComplete(player, "talus_nashal_goto_agent") && !groundquests.isQuestActiveOrComplete(player, "talus_nashal_goto_kiki");
    }
    public void corellia_39_captain_baize_action_regrant_assumedIdentityB(obj_id player, obj_id npc) throws InterruptedException
    {
        if (!groundquests.isQuestActive(player, "corellia_39_chirq_council_02b"))
        {
            groundquests.grantQuest(player, "corellia_39_chirq_council_02b");
        }
        return;
    }
    public void corellia_39_captain_baize_action_signal_assumedIdentityFailed(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.sendSignal(player, "chirq_council_02_failed");
        groundquests.grantQuest(player, "corellia_39_chirq_council_02b");
    }
    public void corellia_39_captain_baize_action_signal_assumedIdentityComplete(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.sendSignal(player, "chirq_council_02_07");
    }
    public void corellia_39_captain_baize_action_grant_movingUp(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.grantQuest(player, "corellia_39_chirq_council_03");
    }
    public void corellia_39_captain_baize_action_signal_movingUpSpice(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.sendSignal(player, "chirq_council_03_03");
    }
    public void corellia_39_captain_baize_action_regrant_movingUpB(obj_id player, obj_id npc) throws InterruptedException
    {
        if (!groundquests.isQuestActive(player, "corellia_39_chirq_council_03b"))
        {
            groundquests.grantQuest(player, "corellia_39_chirq_council_03b");
        }
        return;
    }
    public void corellia_39_captain_baize_action_signal_movingUpComplete(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.sendSignal(player, "chirq_council_03_11");
    }
    public void corellia_39_captain_baize_action_signal_39Pointer(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.sendSignal(player, "corellia_39_pointer_01");
    }
    public void corellia_39_captain_baize_action_grant_seekCouncil(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.grantQuest(player, "corellia_39_chirq_council_01");
    }
    public void corellia_39_captain_baize_action_signal_seekCouncilComplete(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.sendSignal(player, "chirq_council_01_08");
    }
    public void corellia_39_captain_baize_action_grant_assumedIdentity(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.grantQuest(player, "corellia_39_chirq_council_02");
    }
    public void corellia_39_captain_baize_action_signal_assumedIdentityTrins(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.sendSignal(player, "chirq_council_02_03");
    }
    public void corellia_39_captain_baize_action_gant_eliteCouncil(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.grantQuest(player, "corellia_39_chirq_council_04");
    }
    public void corellia_39_captain_baize_action_signal_eliteCouncilComplete(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.sendSignal(player, "chirq_council_04_03");
    }
    public void corellia_39_captain_baize_action_grant_40Pointer(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.grantQuest(player, "talus_nashal_goto_agent");
    }
    public String corellia_39_captain_baize_tokenTO_playersName(obj_id player, obj_id npc) throws InterruptedException
    {
        String name = getName(player);
        String firstName = "agent";
        if (name != null)
        {
            java.util.StringTokenizer tok = new java.util.StringTokenizer(name);
            if (tok.hasMoreTokens())
            {
                firstName = tok.nextToken();
            }
        }
        return firstName;
    }
    public int corellia_39_captain_baize_handleBranch1(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_93"))
        {
            if (corellia_39_captain_baize_condition__defaultCondition(player, npc))
            {
                corellia_39_captain_baize_action_grant_40Pointer(player, npc);
                string_id message = new string_id(c_stringFile, "s_94");
                utils.removeScriptVar(player, "conversation.corellia_39_captain_baize.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int corellia_39_captain_baize_handleBranch3(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_76"))
        {
            if (corellia_39_captain_baize_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "salute2");
                corellia_39_captain_baize_action_grant_40Pointer(player, npc);
                string_id message = new string_id(c_stringFile, "s_78");
                utils.removeScriptVar(player, "conversation.corellia_39_captain_baize.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int corellia_39_captain_baize_handleBranch6(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_47"))
        {
            if (corellia_39_captain_baize_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_48");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (corellia_39_captain_baize_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_74");
                    }
                    utils.setScriptVar(player, "conversation.corellia_39_captain_baize.branchId", 7);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.corellia_39_captain_baize.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int corellia_39_captain_baize_handleBranch7(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_74"))
        {
            if (corellia_39_captain_baize_condition__defaultCondition(player, npc))
            {
                corellia_39_captain_baize_action_gant_eliteCouncil(player, npc);
                string_id message = new string_id(c_stringFile, "s_75");
                utils.removeScriptVar(player, "conversation.corellia_39_captain_baize.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int corellia_39_captain_baize_handleBranch14(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_89"))
        {
            if (corellia_39_captain_baize_condition__defaultCondition(player, npc))
            {
                corellia_39_captain_baize_action_signal_assumedIdentityFailed(player, npc);
                string_id message = new string_id(c_stringFile, "s_90");
                utils.removeScriptVar(player, "conversation.corellia_39_captain_baize.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int corellia_39_captain_baize_handleBranch16(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_67"))
        {
            if (corellia_39_captain_baize_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_68");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (corellia_39_captain_baize_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_69");
                    }
                    utils.setScriptVar(player, "conversation.corellia_39_captain_baize.branchId", 17);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.corellia_39_captain_baize.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_36"))
        {
            if (corellia_39_captain_baize_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_37");
                utils.removeScriptVar(player, "conversation.corellia_39_captain_baize.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int corellia_39_captain_baize_handleBranch17(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_69"))
        {
            if (corellia_39_captain_baize_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "laugh");
                corellia_39_captain_baize_action_grant_movingUp(player, npc);
                string_id message = new string_id(c_stringFile, "s_70");
                utils.removeScriptVar(player, "conversation.corellia_39_captain_baize.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int corellia_39_captain_baize_handleBranch22(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_72"))
        {
            if (corellia_39_captain_baize_condition__defaultCondition(player, npc))
            {
                corellia_39_captain_baize_action_signal_assumedIdentityTrins(player, npc);
                string_id message = new string_id(c_stringFile, "s_73");
                utils.removeScriptVar(player, "conversation.corellia_39_captain_baize.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int corellia_39_captain_baize_handleBranch25(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_57"))
        {
            if (corellia_39_captain_baize_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_58");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (corellia_39_captain_baize_condition__defaultCondition(player, npc))
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
                    utils.setScriptVar(player, "conversation.corellia_39_captain_baize.branchId", 26);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.corellia_39_captain_baize.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int corellia_39_captain_baize_handleBranch26(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_63"))
        {
            if (corellia_39_captain_baize_condition__defaultCondition(player, npc))
            {
                corellia_39_captain_baize_action_grant_assumedIdentity(player, npc);
                string_id message = new string_id(c_stringFile, "s_64");
                utils.removeScriptVar(player, "conversation.corellia_39_captain_baize.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int corellia_39_captain_baize_handleBranch29(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_80"))
        {
            if (corellia_39_captain_baize_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_82");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (corellia_39_captain_baize_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (corellia_39_captain_baize_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_84");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_88");
                    }
                    utils.setScriptVar(player, "conversation.corellia_39_captain_baize.branchId", 30);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.corellia_39_captain_baize.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int corellia_39_captain_baize_handleBranch30(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_84"))
        {
            if (corellia_39_captain_baize_condition__defaultCondition(player, npc))
            {
                corellia_39_captain_baize_action_grant_seekCouncil(player, npc);
                string_id message = new string_id(c_stringFile, "s_86");
                utils.removeScriptVar(player, "conversation.corellia_39_captain_baize.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_88"))
        {
            if (corellia_39_captain_baize_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_92");
                utils.removeScriptVar(player, "conversation.corellia_39_captain_baize.branchId");
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
            detachScript(self, "conversation.corellia_39_captain_baize");
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
        detachScript(self, "conversation.corellia_39_captain_baize");
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
        if (corellia_39_captain_baize_condition_AllCompleted(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_77");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (corellia_39_captain_baize_condition_needs40Pointer(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_93");
                }
                utils.setScriptVar(player, "conversation.corellia_39_captain_baize.branchId", 1);
                npcStartConversation(player, npc, "corellia_39_captain_baize", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (corellia_39_captain_baize_condition_eliteCouncilComplete(player, npc))
        {
            corellia_39_captain_baize_action_signal_eliteCouncilComplete(player, npc);
            string_id message = new string_id(c_stringFile, "s_49");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (corellia_39_captain_baize_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_76");
                }
                utils.setScriptVar(player, "conversation.corellia_39_captain_baize.branchId", 3);
                npcStartConversation(player, npc, "corellia_39_captain_baize", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (corellia_39_captain_baize_condition_eliteCouncilActive(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_51");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        if (corellia_39_captain_baize_condition_movingUpComplete(player, npc))
        {
            corellia_39_captain_baize_action_signal_movingUpComplete(player, npc);
            string_id message = new string_id(c_stringFile, "s_42");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (corellia_39_captain_baize_condition__defaultCondition(player, npc))
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
                utils.setScriptVar(player, "conversation.corellia_39_captain_baize.branchId", 6);
                npcStartConversation(player, npc, "corellia_39_captain_baize", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (corellia_39_captain_baize_condition_movingUpCoverUpB(player, npc))
        {
            corellia_39_captain_baize_action_regrant_movingUpB(player, npc);
            string_id message = new string_id(c_stringFile, "s_39");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        if (corellia_39_captain_baize_condition_movingUpCorsecTerminal(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_41");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        if (corellia_39_captain_baize_condition_movingUpCoverUpA(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_38");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        if (corellia_39_captain_baize_condition_movingUpSpiceComplete(player, npc))
        {
            corellia_39_captain_baize_action_signal_movingUpSpice(player, npc);
            string_id message = new string_id(c_stringFile, "s_40");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        if (corellia_39_captain_baize_condition_movingUpSpiceActive(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_71");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        if (corellia_39_captain_baize_condition_assumedIdentityFailed(player, npc))
        {
            doAnimationAction(npc, "blame");
            string_id message = new string_id(c_stringFile, "s_34");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (corellia_39_captain_baize_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_89");
                }
                utils.setScriptVar(player, "conversation.corellia_39_captain_baize.branchId", 14);
                npcStartConversation(player, npc, "corellia_39_captain_baize", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (corellia_39_captain_baize_condition_assumedIdentityComplete(player, npc))
        {
            corellia_39_captain_baize_action_signal_assumedIdentityComplete(player, npc);
            string_id message = new string_id(c_stringFile, "s_66");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (corellia_39_captain_baize_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            boolean hasResponse1 = false;
            if (corellia_39_captain_baize_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_36");
                }
                utils.setScriptVar(player, "conversation.corellia_39_captain_baize.branchId", 16);
                npcStartConversation(player, npc, "corellia_39_captain_baize", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (corellia_39_captain_baize_condition_assumedIdentityContinuedB(player, npc))
        {
            corellia_39_captain_baize_action_regrant_assumedIdentityB(player, npc);
            string_id message = new string_id(c_stringFile, "s_35");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        if (corellia_39_captain_baize_condition_assumedIdentityContinuedA(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_65");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        if (corellia_39_captain_baize_condition_assumedIdentityTrinsComplete(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_62");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (corellia_39_captain_baize_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_72");
                }
                utils.setScriptVar(player, "conversation.corellia_39_captain_baize.branchId", 22);
                npcStartConversation(player, npc, "corellia_39_captain_baize", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (corellia_39_captain_baize_condition_assumedIdentityTrinsActive(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_61");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        if (corellia_39_captain_baize_condition_seekCouncilComplete(player, npc))
        {
            corellia_39_captain_baize_action_signal_seekCouncilComplete(player, npc);
            string_id message = new string_id(c_stringFile, "s_56");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (corellia_39_captain_baize_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_57");
                }
                utils.setScriptVar(player, "conversation.corellia_39_captain_baize.branchId", 25);
                npcStartConversation(player, npc, "corellia_39_captain_baize", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (corellia_39_captain_baize_condition_seekCouncilActive(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_55");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        if (corellia_39_captain_baize_condition_readyFor39(player, npc))
        {
            corellia_39_captain_baize_action_signal_39Pointer(player, npc);
            string_id message = new string_id(c_stringFile, "s_60");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (corellia_39_captain_baize_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_80");
                }
                utils.setScriptVar(player, "conversation.corellia_39_captain_baize.branchId", 29);
                prose_package pp = new prose_package();
                pp.stringId = message;
                pp.actor.set(player);
                pp.target.set(npc);
                pp.other.set(corellia_39_captain_baize_tokenTO_playersName(player, npc));
                npcStartConversation(player, npc, "corellia_39_captain_baize", null, pp, responses);
            }
            else 
            {
                prose_package pp = new prose_package();
                pp.stringId = message;
                pp.actor.set(player);
                pp.target.set(npc);
                pp.other.set(corellia_39_captain_baize_tokenTO_playersName(player, npc));
                chat.chat(npc, player, null, null, pp);
            }
            return SCRIPT_CONTINUE;
        }
        if (corellia_39_captain_baize_condition__defaultCondition(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_96");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        chat.chat(npc, "Error:  All conditions for OnStartNpcConversation were false.");
        return SCRIPT_CONTINUE;
    }
    public int OnNpcConversationResponse(obj_id self, String conversationId, obj_id player, string_id response) throws InterruptedException
    {
        if (!conversationId.equals("corellia_39_captain_baize"))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id npc = self;
        int branchId = utils.getIntScriptVar(player, "conversation.corellia_39_captain_baize.branchId");
        if (branchId == 1 && corellia_39_captain_baize_handleBranch1(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 3 && corellia_39_captain_baize_handleBranch3(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 6 && corellia_39_captain_baize_handleBranch6(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 7 && corellia_39_captain_baize_handleBranch7(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 14 && corellia_39_captain_baize_handleBranch14(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 16 && corellia_39_captain_baize_handleBranch16(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 17 && corellia_39_captain_baize_handleBranch17(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 22 && corellia_39_captain_baize_handleBranch22(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 25 && corellia_39_captain_baize_handleBranch25(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 26 && corellia_39_captain_baize_handleBranch26(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 29 && corellia_39_captain_baize_handleBranch29(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 30 && corellia_39_captain_baize_handleBranch30(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        chat.chat(npc, "Error:  Fell through all branches and responses for OnNpcConversationResponse.");
        utils.removeScriptVar(player, "conversation.corellia_39_captain_baize.branchId");
        return SCRIPT_CONTINUE;
    }
}
