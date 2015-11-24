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

public class u13_lieutenant_tarc extends script.base_script
{
    public u13_lieutenant_tarc()
    {
    }
    public static String c_stringFile = "conversation/u13_lieutenant_tarc";
    public boolean u13_lieutenant_tarc_condition__defaultCondition(obj_id player, obj_id npc) throws InterruptedException
    {
        return true;
    }
    public boolean u13_lieutenant_tarc_condition_active_ponda_pointer(obj_id player, obj_id npc) throws InterruptedException
    {
        return groundquests.isQuestActiveOrComplete(player, "u13_ponda_pointer");
    }
    public boolean u13_lieutenant_tarc_condition_active_ponda_01(obj_id player, obj_id npc) throws InterruptedException
    {
        return groundquests.isQuestActive(player, "u13_ponda_01");
    }
    public boolean u13_lieutenant_tarc_condition_returning_ponda_01(obj_id player, obj_id npc) throws InterruptedException
    {
        return groundquests.isTaskActive(player, "u13_ponda_01", "u13_ponda_01_03") || groundquests.hasCompletedQuest(player, "u13_ponda_01");
    }
    public boolean u13_lieutenant_tarc_condition_active_ponda_02(obj_id player, obj_id npc) throws InterruptedException
    {
        return groundquests.isQuestActive(player, "u13_ponda_02");
    }
    public boolean u13_lieutenant_tarc_condition_returning_ponda_02(obj_id player, obj_id npc) throws InterruptedException
    {
        return groundquests.isTaskActive(player, "u13_ponda_02", "u13_ponda_02_06") || groundquests.hasCompletedQuest(player, "u13_ponda_02");
    }
    public boolean u13_lieutenant_tarc_condition_active_ponda_03(obj_id player, obj_id npc) throws InterruptedException
    {
        return groundquests.isQuestActive(player, "u13_ponda_03");
    }
    public boolean u13_lieutenant_tarc_condition_returning_ponda_03(obj_id player, obj_id npc) throws InterruptedException
    {
        return groundquests.isTaskActive(player, "u13_ponda_03", "u13_ponda_03_04") || groundquests.hasCompletedQuest(player, "u13_ponda_03");
    }
    public boolean u13_lieutenant_tarc_condition_alreadyDoneMeatlumpQuests(obj_id player, obj_id npc) throws InterruptedException
    {
        return groundquests.isQuestActiveOrComplete(player, "mtp_hideout_access_high_01") || groundquests.isQuestActiveOrComplete(player, "mtp_hideout_access_01");
    }
    public boolean u13_lieutenant_tarc_condition_needsMeatlumpPointer(obj_id player, obj_id npc) throws InterruptedException
    {
        return !groundquests.isQuestActiveOrComplete(player, "mtp_hideout_access_high_01") && !groundquests.isQuestActiveOrComplete(player, "mtp_hideout_access_01") && !groundquests.isQuestActiveOrComplete(player, "corellia_coronet_meatlump_act1_pointer") && !groundquests.isQuestActiveOrComplete(player, "corellia_coronet_meatlump_act2_pointer");
    }
    public void u13_lieutenant_tarc_action_signal_ponda_pointer(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.sendSignal(player, "u13_ponda_pointer");
    }
    public void u13_lieutenant_tarc_action_grant_ponda_01(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.requestGrantQuest(player, "u13_ponda_01");
    }
    public void u13_lieutenant_tarc_action_signal_ponda_01_03(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.sendSignal(player, "u13_ponda_01_03");
    }
    public void u13_lieutenant_tarc_action_grant_ponda_02(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.requestGrantQuest(player, "u13_ponda_02");
    }
    public void u13_lieutenant_tarc_action_signal_ponda_02_06(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.sendSignal(player, "u13_ponda_02_06");
    }
    public void u13_lieutenant_tarc_action_grant_ponda_03(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.requestGrantQuest(player, "u13_ponda_03");
    }
    public void u13_lieutenant_tarc_action_signal_ponda_03_04(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.sendSignal(player, "u13_ponda_03_04");
    }
    public void u13_lieutenant_tarc_action_meatlumps_pointer(obj_id player, obj_id npc) throws InterruptedException
    {
        if (!groundquests.isQuestActiveOrComplete(player, "corellia_coronet_meatlump_act1_begin"))
        {
            groundquests.requestGrantQuest(player, "corellia_coronet_meatlump_act1_pointer");
        }
        else 
        {
            groundquests.requestGrantQuest(player, "corellia_coronet_meatlump_act2_pointer");
        }
    }
    public int u13_lieutenant_tarc_handleBranch1(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_17"))
        {
            if (u13_lieutenant_tarc_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_18");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (u13_lieutenant_tarc_condition_needsMeatlumpPointer(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_19");
                    }
                    utils.setScriptVar(player, "conversation.u13_lieutenant_tarc.branchId", 2);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.u13_lieutenant_tarc.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int u13_lieutenant_tarc_handleBranch2(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_19"))
        {
            if (u13_lieutenant_tarc_condition_alreadyDoneMeatlumpQuests(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_22");
                utils.removeScriptVar(player, "conversation.u13_lieutenant_tarc.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
            if (u13_lieutenant_tarc_condition__defaultCondition(player, npc))
            {
                u13_lieutenant_tarc_action_meatlumps_pointer(player, npc);
                string_id message = new string_id(c_stringFile, "s_21");
                utils.removeScriptVar(player, "conversation.u13_lieutenant_tarc.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int u13_lieutenant_tarc_handleBranch6(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_15"))
        {
            if (u13_lieutenant_tarc_condition__defaultCondition(player, npc))
            {
                u13_lieutenant_tarc_action_grant_ponda_03(player, npc);
                string_id message = new string_id(c_stringFile, "s_20");
                utils.removeScriptVar(player, "conversation.u13_lieutenant_tarc.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int u13_lieutenant_tarc_handleBranch9(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_28"))
        {
            if (u13_lieutenant_tarc_condition__defaultCondition(player, npc))
            {
                u13_lieutenant_tarc_action_grant_ponda_02(player, npc);
                string_id message = new string_id(c_stringFile, "s_30");
                utils.removeScriptVar(player, "conversation.u13_lieutenant_tarc.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int u13_lieutenant_tarc_handleBranch12(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_36"))
        {
            if (u13_lieutenant_tarc_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_38");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (u13_lieutenant_tarc_condition__defaultCondition(player, npc))
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
                    utils.setScriptVar(player, "conversation.u13_lieutenant_tarc.branchId", 13);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.u13_lieutenant_tarc.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int u13_lieutenant_tarc_handleBranch13(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_41"))
        {
            if (u13_lieutenant_tarc_condition__defaultCondition(player, npc))
            {
                u13_lieutenant_tarc_action_grant_ponda_01(player, npc);
                string_id message = new string_id(c_stringFile, "s_42");
                utils.removeScriptVar(player, "conversation.u13_lieutenant_tarc.branchId");
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
            detachScript(self, "conversation.u13_lieutenant_tarc");
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
        detachScript(self, "conversation.u13_lieutenant_tarc");
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
        if (u13_lieutenant_tarc_condition_returning_ponda_03(player, npc))
        {
            u13_lieutenant_tarc_action_signal_ponda_03_04(player, npc);
            string_id message = new string_id(c_stringFile, "s_4");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (u13_lieutenant_tarc_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_17");
                }
                utils.setScriptVar(player, "conversation.u13_lieutenant_tarc.branchId", 1);
                npcStartConversation(player, npc, "u13_lieutenant_tarc", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (u13_lieutenant_tarc_condition_active_ponda_03(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_11");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        if (u13_lieutenant_tarc_condition_returning_ponda_02(player, npc))
        {
            u13_lieutenant_tarc_action_signal_ponda_02_06(player, npc);
            string_id message = new string_id(c_stringFile, "s_13");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (u13_lieutenant_tarc_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_15");
                }
                utils.setScriptVar(player, "conversation.u13_lieutenant_tarc.branchId", 6);
                npcStartConversation(player, npc, "u13_lieutenant_tarc", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (u13_lieutenant_tarc_condition_active_ponda_02(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_24");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        if (u13_lieutenant_tarc_condition_returning_ponda_01(player, npc))
        {
            u13_lieutenant_tarc_action_signal_ponda_01_03(player, npc);
            string_id message = new string_id(c_stringFile, "s_26");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (u13_lieutenant_tarc_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_28");
                }
                utils.setScriptVar(player, "conversation.u13_lieutenant_tarc.branchId", 9);
                npcStartConversation(player, npc, "u13_lieutenant_tarc", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (u13_lieutenant_tarc_condition_active_ponda_01(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_32");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        if (u13_lieutenant_tarc_condition_active_ponda_pointer(player, npc))
        {
            u13_lieutenant_tarc_action_signal_ponda_pointer(player, npc);
            string_id message = new string_id(c_stringFile, "s_34");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (u13_lieutenant_tarc_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_36");
                }
                utils.setScriptVar(player, "conversation.u13_lieutenant_tarc.branchId", 12);
                npcStartConversation(player, npc, "u13_lieutenant_tarc", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (u13_lieutenant_tarc_condition__defaultCondition(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_44");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        chat.chat(npc, "Error:  All conditions for OnStartNpcConversation were false.");
        return SCRIPT_CONTINUE;
    }
    public int OnNpcConversationResponse(obj_id self, String conversationId, obj_id player, string_id response) throws InterruptedException
    {
        if (!conversationId.equals("u13_lieutenant_tarc"))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id npc = self;
        int branchId = utils.getIntScriptVar(player, "conversation.u13_lieutenant_tarc.branchId");
        if (branchId == 1 && u13_lieutenant_tarc_handleBranch1(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 2 && u13_lieutenant_tarc_handleBranch2(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 6 && u13_lieutenant_tarc_handleBranch6(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 9 && u13_lieutenant_tarc_handleBranch9(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 12 && u13_lieutenant_tarc_handleBranch12(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 13 && u13_lieutenant_tarc_handleBranch13(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        chat.chat(npc, "Error:  Fell through all branches and responses for OnNpcConversationResponse.");
        utils.removeScriptVar(player, "conversation.u13_lieutenant_tarc.branchId");
        return SCRIPT_CONTINUE;
    }
}
