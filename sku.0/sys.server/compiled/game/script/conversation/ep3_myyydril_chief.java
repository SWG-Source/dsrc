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

public class ep3_myyydril_chief extends script.base_script
{
    public ep3_myyydril_chief()
    {
    }
    public static String c_stringFile = "conversation/ep3_myyydril_chief";
    public boolean ep3_myyydril_chief_condition__defaultCondition(obj_id player, obj_id npc) throws InterruptedException
    {
        return true;
    }
    public boolean ep3_myyydril_chief_condition_isActiveQuestInitial(obj_id player, obj_id npc) throws InterruptedException
    {
        return groundquests.isQuestActive(player, "ep3_myyydril_kallaarac_talkto_2");
    }
    public boolean ep3_myyydril_chief_condition_hasCompletedQuestInitial(obj_id player, obj_id npc) throws InterruptedException
    {
        if (groundquests.isTaskActive(player, "ep3_myyydril_kivvaaa_talkto_1", 0) || groundquests.hasCompletedTask(player, "ep3_myyydril_kivvaaa_talkto_1", 0))
        {
            return true;
        }
        else 
        {
            return false;
        }
    }
    public boolean ep3_myyydril_chief_condition_hasNotFinishedQuestSeries(obj_id player, obj_id npc) throws InterruptedException
    {
        return (groundquests.hasCompletedQuest(player, "ep3_myyydril_kivvaaa_talkto_1") && !groundquests.hasCompletedQuest(player, "ep3_myyydril_nawika_escort_1"));
    }
    public boolean ep3_myyydril_chief_condition_hasCompletedQuestSeries(obj_id player, obj_id npc) throws InterruptedException
    {
        return groundquests.hasCompletedQuest(player, "ep3_myyydril_nawika_talkto_5");
    }
    public boolean ep3_myyydril_chief_condition_hasCompletedQuestOne(obj_id player, obj_id npc) throws InterruptedException
    {
        return groundquests.hasCompletedQuest(player, "ep3_myyydril_kallaarac_destroy_2");
    }
    public boolean ep3_myyydril_chief_condition_hasCompletedQuestTwo(obj_id player, obj_id npc) throws InterruptedException
    {
        return groundquests.hasCompletedQuest(player, "ep3_myyydril_kallaarac_retrieve_1");
    }
    public boolean ep3_myyydril_chief_condition_hasLanguage(obj_id player, obj_id npc) throws InterruptedException
    {
        return utils.canSpeakWookiee(player, npc);
    }
    public boolean ep3_myyydril_chief_condition_isactiveNawikaTalk(obj_id player, obj_id npc) throws InterruptedException
    {
        if (groundquests.isTaskActive(player, "ep3_myyydril_nawika_talkto_5", 0) || groundquests.hasCompletedTask(player, "ep3_myyydril_nawika_talkto_5", 0))
        {
            return true;
        }
        else 
        {
            return false;
        }
    }
    public boolean ep3_myyydril_chief_condition_needsQuestCorrection(obj_id player, obj_id npc) throws InterruptedException
    {
        if (groundquests.hasCompletedQuest(player, "ep3_myyydril_kivvaaa_talkto_1"))
        {
            if (!groundquests.hasCompletedQuest(player, "ep3_myyydril_kallaarac_talkto_2") && !groundquests.isQuestActive(player, "ep3_myyydril_kallaarac_talkto_2"))
            {
                return true;
            }
        }
        return false;
    }
    public boolean ep3_myyydril_chief_condition_isActiveTaskOne(obj_id player, obj_id npc) throws InterruptedException
    {
        if (groundquests.isTaskActive(player, "ep3_myyydril_kallaarac_destroy_2", 0) || groundquests.hasCompletedTask(player, "ep3_myyydril_kallaarac_destroy_2", 0))
        {
            return true;
        }
        else 
        {
            return false;
        }
    }
    public boolean ep3_myyydril_chief_condition_hasCompletedTaskOne(obj_id player, obj_id npc) throws InterruptedException
    {
        return (groundquests.hasCompletedTask(player, "ep3_myyydril_kallaarac_destroy_2", 0) && !groundquests.hasCompletedQuest(player, "ep3_myyydril_kallaarac_destroy_2"));
    }
    public boolean ep3_myyydril_chief_condition_isActiveTaskTwo(obj_id player, obj_id npc) throws InterruptedException
    {
        if (groundquests.isTaskActive(player, "ep3_myyydril_kallaarac_retrieve_1", 0) || groundquests.hasCompletedTask(player, "ep3_myyydril_kallaarac_retrieve_1", 0))
        {
            return true;
        }
        else 
        {
            return false;
        }
    }
    public boolean ep3_myyydril_chief_condition_hasCompletedTaskTwo(obj_id player, obj_id npc) throws InterruptedException
    {
        return (groundquests.hasCompletedTask(player, "ep3_myyydril_kallaarac_retrieve_1", 0) && !groundquests.hasCompletedQuest(player, "ep3_myyydril_kallaarac_retrieve_1"));
    }
    public boolean ep3_myyydril_chief_condition_isActiveTaskThree(obj_id player, obj_id npc) throws InterruptedException
    {
        if (groundquests.isTaskActive(player, "ep3_myyydril_kallaarac_destroy_3", 0) || groundquests.hasCompletedTask(player, "ep3_myyydril_kallaarac_destroy_3", 0))
        {
            return true;
        }
        else 
        {
            return false;
        }
    }
    public boolean ep3_myyydril_chief_condition_hasCompletedTaskThree(obj_id player, obj_id npc) throws InterruptedException
    {
        return (groundquests.hasCompletedTask(player, "ep3_myyydril_kallaarac_destroy_3", 0) && !groundquests.hasCompletedQuest(player, "ep3_myyydril_kallaarac_destroy_3"));
    }
    public boolean ep3_myyydril_chief_condition_hasCompletedQuestThree(obj_id player, obj_id npc) throws InterruptedException
    {
        return groundquests.hasCompletedQuest(player, "ep3_myyydril_kallaarac_destroy_3");
    }
    public void ep3_myyydril_chief_action_giveQuestInitial(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.grantQuest(player, "ep3_myyydril_kallaarac_talkto_2");
        groundquests.sendSignal(player, "talktokallaarac");
    }
    public void ep3_myyydril_chief_action_giveQuestOne(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.grantQuest(player, "ep3_myyydril_kallaarac_destroy_2");
    }
    public void ep3_myyydril_chief_action_sendSignal1(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.sendSignal(player, "giveStuff");
    }
    public void ep3_myyydril_chief_action_doSignal2(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.sendSignal(player, "talktokallaarac");
    }
    public void ep3_myyydril_chief_action_giveQuestTwo(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.grantQuest(player, "ep3_myyydril_kallaarac_retrieve_1");
    }
    public void ep3_myyydril_chief_action_giveQuestThree(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.grantQuest(player, "ep3_myyydril_kallaarac_destroy_3");
    }
    public void ep3_myyydril_chief_action_doSingal3(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.sendSignal(player, "talktochief");
    }
    public void ep3_myyydril_chief_action_Language(obj_id player, obj_id npc) throws InterruptedException
    {
        utils.emoteWookieeConfusion(player, npc);
    }
    public int ep3_myyydril_chief_handleBranch2(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_3744"))
        {
            if (ep3_myyydril_chief_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_3746");
                utils.removeScriptVar(player, "conversation.ep3_myyydril_chief.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int ep3_myyydril_chief_handleBranch4(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_3750"))
        {
            if (ep3_myyydril_chief_condition__defaultCondition(player, npc))
            {
                ep3_myyydril_chief_action_doSingal3(player, npc);
                string_id message = new string_id(c_stringFile, "s_3752");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (ep3_myyydril_chief_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_3754");
                    }
                    utils.setScriptVar(player, "conversation.ep3_myyydril_chief.branchId", 5);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.ep3_myyydril_chief.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int ep3_myyydril_chief_handleBranch5(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_3754"))
        {
            if (ep3_myyydril_chief_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_3756");
                utils.removeScriptVar(player, "conversation.ep3_myyydril_chief.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int ep3_myyydril_chief_handleBranch8(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_3762"))
        {
            if (ep3_myyydril_chief_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_3764");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (ep3_myyydril_chief_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (ep3_myyydril_chief_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_3766");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_3770");
                    }
                    utils.setScriptVar(player, "conversation.ep3_myyydril_chief.branchId", 9);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.ep3_myyydril_chief.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int ep3_myyydril_chief_handleBranch9(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_3766"))
        {
            if (ep3_myyydril_chief_condition__defaultCondition(player, npc))
            {
                ep3_myyydril_chief_action_giveQuestThree(player, npc);
                string_id message = new string_id(c_stringFile, "s_3768");
                utils.removeScriptVar(player, "conversation.ep3_myyydril_chief.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_3770"))
        {
            if (ep3_myyydril_chief_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_3772");
                utils.removeScriptVar(player, "conversation.ep3_myyydril_chief.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int ep3_myyydril_chief_handleBranch12(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_3776"))
        {
            if (ep3_myyydril_chief_condition__defaultCondition(player, npc))
            {
                ep3_myyydril_chief_action_doSignal2(player, npc);
                string_id message = new string_id(c_stringFile, "s_3778");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (ep3_myyydril_chief_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_3780");
                    }
                    utils.setScriptVar(player, "conversation.ep3_myyydril_chief.branchId", 13);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.ep3_myyydril_chief.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int ep3_myyydril_chief_handleBranch13(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_3780"))
        {
            if (ep3_myyydril_chief_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_3782");
                utils.removeScriptVar(player, "conversation.ep3_myyydril_chief.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int ep3_myyydril_chief_handleBranch16(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_3788"))
        {
            if (ep3_myyydril_chief_condition__defaultCondition(player, npc))
            {
                ep3_myyydril_chief_action_giveQuestTwo(player, npc);
                string_id message = new string_id(c_stringFile, "s_3790");
                utils.removeScriptVar(player, "conversation.ep3_myyydril_chief.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_3792"))
        {
            if (ep3_myyydril_chief_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_3794");
                utils.removeScriptVar(player, "conversation.ep3_myyydril_chief.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int ep3_myyydril_chief_handleBranch19(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_3798"))
        {
            if (ep3_myyydril_chief_condition__defaultCondition(player, npc))
            {
                ep3_myyydril_chief_action_sendSignal1(player, npc);
                string_id message = new string_id(c_stringFile, "s_3800");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (ep3_myyydril_chief_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_3802");
                    }
                    utils.setScriptVar(player, "conversation.ep3_myyydril_chief.branchId", 20);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.ep3_myyydril_chief.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int ep3_myyydril_chief_handleBranch20(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_3802"))
        {
            if (ep3_myyydril_chief_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_3804");
                utils.removeScriptVar(player, "conversation.ep3_myyydril_chief.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int ep3_myyydril_chief_handleBranch22(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_3808"))
        {
            if (ep3_myyydril_chief_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_3810");
                utils.removeScriptVar(player, "conversation.ep3_myyydril_chief.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int ep3_myyydril_chief_handleBranch24(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_1115"))
        {
            if (ep3_myyydril_chief_condition__defaultCondition(player, npc))
            {
                ep3_myyydril_chief_action_giveQuestOne(player, npc);
                string_id message = new string_id(c_stringFile, "s_1117");
                utils.removeScriptVar(player, "conversation.ep3_myyydril_chief.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_1119"))
        {
            if (ep3_myyydril_chief_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_1121");
                utils.removeScriptVar(player, "conversation.ep3_myyydril_chief.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int ep3_myyydril_chief_handleBranch27(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_3814"))
        {
            ep3_myyydril_chief_action_doSingal3(player, npc);
            if (ep3_myyydril_chief_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_3816");
                utils.removeScriptVar(player, "conversation.ep3_myyydril_chief.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int ep3_myyydril_chief_handleBranch29(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_3828"))
        {
            if (ep3_myyydril_chief_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_3830");
                utils.removeScriptVar(player, "conversation.ep3_myyydril_chief.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_363"))
        {
            if (ep3_myyydril_chief_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_3836");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (ep3_myyydril_chief_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_3838");
                    }
                    utils.setScriptVar(player, "conversation.ep3_myyydril_chief.branchId", 32);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.ep3_myyydril_chief.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int ep3_myyydril_chief_handleBranch31(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_3834"))
        {
            if (ep3_myyydril_chief_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_3836");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (ep3_myyydril_chief_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_3838");
                    }
                    utils.setScriptVar(player, "conversation.ep3_myyydril_chief.branchId", 32);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.ep3_myyydril_chief.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_3842"))
        {
            if (ep3_myyydril_chief_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_3844");
                utils.removeScriptVar(player, "conversation.ep3_myyydril_chief.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int ep3_myyydril_chief_handleBranch32(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_3838"))
        {
            if (ep3_myyydril_chief_condition__defaultCondition(player, npc))
            {
                ep3_myyydril_chief_action_giveQuestInitial(player, npc);
                string_id message = new string_id(c_stringFile, "s_3840");
                utils.removeScriptVar(player, "conversation.ep3_myyydril_chief.branchId");
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
            detachScript(self, "conversation.ep3_myyydril_chief");
        }
        setCondition(self, CONDITION_CONVERSABLE);
        setName(self, new string_id("ep3/npc_names", "kallaarac"));
        setInvulnerable(self, true);
        return SCRIPT_CONTINUE;
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        setCondition(self, CONDITION_CONVERSABLE);
        setName(self, new string_id("ep3/npc_names", "kallaarac"));
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
        detachScript(self, "conversation.ep3_myyydril_chief");
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
        if (ep3_myyydril_chief_condition_hasLanguage(player, npc))
        {
            ep3_myyydril_chief_action_Language(player, npc);
            string_id message = new string_id(c_stringFile, "s_1076");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        if (ep3_myyydril_chief_condition_hasCompletedQuestThree(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_3742");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (ep3_myyydril_chief_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_3744");
                }
                utils.setScriptVar(player, "conversation.ep3_myyydril_chief.branchId", 2);
                npcStartConversation(player, npc, "ep3_myyydril_chief", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (ep3_myyydril_chief_condition_hasCompletedTaskThree(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_3748");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (ep3_myyydril_chief_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_3750");
                }
                utils.setScriptVar(player, "conversation.ep3_myyydril_chief.branchId", 4);
                npcStartConversation(player, npc, "ep3_myyydril_chief", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (ep3_myyydril_chief_condition_isActiveTaskThree(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_3758");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        if (ep3_myyydril_chief_condition_hasCompletedQuestTwo(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_3760");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (ep3_myyydril_chief_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_3762");
                }
                utils.setScriptVar(player, "conversation.ep3_myyydril_chief.branchId", 8);
                npcStartConversation(player, npc, "ep3_myyydril_chief", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (ep3_myyydril_chief_condition_hasCompletedTaskTwo(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_3774");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (ep3_myyydril_chief_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_3776");
                }
                utils.setScriptVar(player, "conversation.ep3_myyydril_chief.branchId", 12);
                npcStartConversation(player, npc, "ep3_myyydril_chief", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (ep3_myyydril_chief_condition_isActiveTaskTwo(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_3784");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        if (ep3_myyydril_chief_condition_hasCompletedQuestOne(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_3786");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (ep3_myyydril_chief_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            boolean hasResponse1 = false;
            if (ep3_myyydril_chief_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_3788");
                }
                if (hasResponse1)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_3792");
                }
                utils.setScriptVar(player, "conversation.ep3_myyydril_chief.branchId", 16);
                npcStartConversation(player, npc, "ep3_myyydril_chief", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (ep3_myyydril_chief_condition_hasCompletedTaskOne(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_3796");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (ep3_myyydril_chief_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_3798");
                }
                utils.setScriptVar(player, "conversation.ep3_myyydril_chief.branchId", 19);
                npcStartConversation(player, npc, "ep3_myyydril_chief", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (ep3_myyydril_chief_condition_isActiveTaskOne(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_3806");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (ep3_myyydril_chief_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_3808");
                }
                utils.setScriptVar(player, "conversation.ep3_myyydril_chief.branchId", 22);
                npcStartConversation(player, npc, "ep3_myyydril_chief", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (ep3_myyydril_chief_condition_hasCompletedQuestSeries(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_1113");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (ep3_myyydril_chief_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            boolean hasResponse1 = false;
            if (ep3_myyydril_chief_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_1115");
                }
                if (hasResponse1)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_1119");
                }
                utils.setScriptVar(player, "conversation.ep3_myyydril_chief.branchId", 24);
                npcStartConversation(player, npc, "ep3_myyydril_chief", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (ep3_myyydril_chief_condition_isactiveNawikaTalk(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_3812");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (ep3_myyydril_chief_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_3814");
                }
                utils.setScriptVar(player, "conversation.ep3_myyydril_chief.branchId", 27);
                npcStartConversation(player, npc, "ep3_myyydril_chief", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (ep3_myyydril_chief_condition_hasNotFinishedQuestSeries(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_3826");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (ep3_myyydril_chief_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            boolean hasResponse1 = false;
            if (ep3_myyydril_chief_condition_needsQuestCorrection(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_3828");
                }
                if (hasResponse1)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_363");
                }
                utils.setScriptVar(player, "conversation.ep3_myyydril_chief.branchId", 29);
                npcStartConversation(player, npc, "ep3_myyydril_chief", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (ep3_myyydril_chief_condition_hasCompletedQuestInitial(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_3832");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (ep3_myyydril_chief_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            boolean hasResponse1 = false;
            if (ep3_myyydril_chief_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_3834");
                }
                if (hasResponse1)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_3842");
                }
                utils.setScriptVar(player, "conversation.ep3_myyydril_chief.branchId", 31);
                npcStartConversation(player, npc, "ep3_myyydril_chief", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (ep3_myyydril_chief_condition__defaultCondition(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_3846");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        chat.chat(npc, "Error:  All conditions for OnStartNpcConversation were false.");
        return SCRIPT_CONTINUE;
    }
    public int OnNpcConversationResponse(obj_id self, String conversationId, obj_id player, string_id response) throws InterruptedException
    {
        if (!conversationId.equals("ep3_myyydril_chief"))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id npc = self;
        int branchId = utils.getIntScriptVar(player, "conversation.ep3_myyydril_chief.branchId");
        if (branchId == 2 && ep3_myyydril_chief_handleBranch2(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 4 && ep3_myyydril_chief_handleBranch4(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 5 && ep3_myyydril_chief_handleBranch5(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 8 && ep3_myyydril_chief_handleBranch8(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 9 && ep3_myyydril_chief_handleBranch9(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 12 && ep3_myyydril_chief_handleBranch12(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 13 && ep3_myyydril_chief_handleBranch13(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 16 && ep3_myyydril_chief_handleBranch16(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 19 && ep3_myyydril_chief_handleBranch19(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 20 && ep3_myyydril_chief_handleBranch20(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 22 && ep3_myyydril_chief_handleBranch22(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 24 && ep3_myyydril_chief_handleBranch24(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 27 && ep3_myyydril_chief_handleBranch27(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 29 && ep3_myyydril_chief_handleBranch29(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 31 && ep3_myyydril_chief_handleBranch31(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 32 && ep3_myyydril_chief_handleBranch32(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        chat.chat(npc, "Error:  Fell through all branches and responses for OnNpcConversationResponse.");
        utils.removeScriptVar(player, "conversation.ep3_myyydril_chief.branchId");
        return SCRIPT_CONTINUE;
    }
}
