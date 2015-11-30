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
import script.library.space_quest;
import script.library.utils;

public class itp_emperor_main extends script.base_script
{
    public itp_emperor_main()
    {
    }
    public static String c_stringFile = "conversation/itp_emperor_main";
    public boolean itp_emperor_main_condition__defaultCondition(obj_id player, obj_id npc) throws InterruptedException
    {
        return true;
    }
    public boolean itp_emperor_main_condition_itp_emp_01_active(obj_id player, obj_id npc) throws InterruptedException
    {
        return groundquests.isQuestActive(player, "itp_emperor_01");
    }
    public boolean itp_emperor_main_condition_itp_emp_01_complete(obj_id player, obj_id npc) throws InterruptedException
    {
        return groundquests.isTaskActive(player, "itp_emperor_01", "itp_emperor_01_05") || groundquests.hasCompletedQuest(player, "itp_emperor_01");
    }
    public boolean itp_emperor_main_condition_itp_emp_02_active(obj_id player, obj_id npc) throws InterruptedException
    {
        return groundquests.isQuestActive(player, "itp_emperor_02");
    }
    public boolean itp_emperor_main_condition_itp_emp_03_complete(obj_id player, obj_id npc) throws InterruptedException
    {
        return groundquests.isTaskActive(player, "itp_emperor_03", "itp_emperor_03_04") || groundquests.hasCompletedQuest(player, "itp_emperor_03");
    }
    public boolean itp_emperor_main_condition_itp_emp_03_active(obj_id player, obj_id npc) throws InterruptedException
    {
        return groundquests.isQuestActive(player, "itp_emperor_03");
    }
    public boolean itp_emperor_main_condition_itp_emp_02_complete(obj_id player, obj_id npc) throws InterruptedException
    {
        return groundquests.isTaskActive(player, "itp_emperor_02", "itp_emperor_02_03") || groundquests.hasCompletedQuest(player, "itp_emperor_02");
    }
    public boolean itp_emperor_main_condition_completedVeers(obj_id player, obj_id npc) throws InterruptedException
    {
        return groundquests.hasCompletedQuest(player, "itp_veers_02");
    }
    public boolean itp_emperor_main_condition_notImperial(obj_id player, obj_id npc) throws InterruptedException
    {
        String playerFaction = factions.getFaction(player);
        if (playerFaction == null || !playerFaction.equals("Imperial"))
        {
            return true;
        }
        return false;
    }
    public boolean itp_emperor_main_condition_imperial_isOnLeave(obj_id player, obj_id npc) throws InterruptedException
    {
        return factions.isOnLeave(player);
    }
    public void itp_emperor_main_action_itp_emp_01_granted(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.grantQuest(player, "itp_emperor_01");
    }
    public void itp_emperor_main_action_itp_emp_01_signal(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.sendSignal(player, "itp_emperor_01_05");
    }
    public void itp_emperor_main_action_itp_emp_03_signal(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.sendSignal(player, "itp_emperor_03_04");
    }
    public void itp_emperor_main_action_itp_emp_02_signal(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.sendSignal(player, "itp_emperor_02_03");
    }
    public void itp_emperor_main_action_itp_emp_02_granted(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.grantQuest(player, "itp_emperor_02");
    }
    public void itp_emperor_main_action_itp_emp_03_granted(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.grantQuest(player, "itp_emperor_03");
    }
    public int itp_emperor_main_handleBranch6(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_31"))
        {
            if (itp_emperor_main_condition__defaultCondition(player, npc))
            {
                itp_emperor_main_action_itp_emp_03_granted(player, npc);
                string_id message = new string_id(c_stringFile, "s_33");
                utils.removeScriptVar(player, "conversation.itp_emperor_main.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_35"))
        {
            if (itp_emperor_main_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_37");
                utils.removeScriptVar(player, "conversation.itp_emperor_main.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int itp_emperor_main_handleBranch10(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_22"))
        {
            if (itp_emperor_main_condition__defaultCondition(player, npc))
            {
                itp_emperor_main_action_itp_emp_02_granted(player, npc);
                string_id message = new string_id(c_stringFile, "s_25");
                utils.removeScriptVar(player, "conversation.itp_emperor_main.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_26"))
        {
            if (itp_emperor_main_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_30");
                utils.removeScriptVar(player, "conversation.itp_emperor_main.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int itp_emperor_main_handleBranch14(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_36"))
        {
            if (itp_emperor_main_condition__defaultCondition(player, npc))
            {
                itp_emperor_main_action_itp_emp_01_granted(player, npc);
                string_id message = new string_id(c_stringFile, "s_39");
                utils.removeScriptVar(player, "conversation.itp_emperor_main.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_41"))
        {
            if (itp_emperor_main_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_45");
                utils.removeScriptVar(player, "conversation.itp_emperor_main.branchId");
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
            detachScript(self, "conversation.itp_emperor_main");
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
        detachScript(self, "conversation.itp_emperor_main");
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
        if (itp_emperor_main_condition_notImperial(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_44");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        if (!itp_emperor_main_condition_completedVeers(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_46");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        if (itp_emperor_main_condition_imperial_isOnLeave(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_43");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        if (itp_emperor_main_condition_itp_emp_03_complete(player, npc))
        {
            itp_emperor_main_action_itp_emp_03_signal(player, npc);
            string_id message = new string_id(c_stringFile, "s_9");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        if (itp_emperor_main_condition_itp_emp_03_active(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_29");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        if (itp_emperor_main_condition_itp_emp_02_complete(player, npc))
        {
            itp_emperor_main_action_itp_emp_02_signal(player, npc);
            string_id message = new string_id(c_stringFile, "s_28");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (itp_emperor_main_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            boolean hasResponse1 = false;
            if (itp_emperor_main_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_31");
                }
                if (hasResponse1)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_35");
                }
                utils.setScriptVar(player, "conversation.itp_emperor_main.branchId", 6);
                npcStartConversation(player, npc, "itp_emperor_main", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (itp_emperor_main_condition_itp_emp_02_active(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_16");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        if (itp_emperor_main_condition_itp_emp_01_complete(player, npc))
        {
            itp_emperor_main_action_itp_emp_01_signal(player, npc);
            string_id message = new string_id(c_stringFile, "s_20");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (itp_emperor_main_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            boolean hasResponse1 = false;
            if (itp_emperor_main_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_22");
                }
                if (hasResponse1)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_26");
                }
                utils.setScriptVar(player, "conversation.itp_emperor_main.branchId", 10);
                npcStartConversation(player, npc, "itp_emperor_main", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (itp_emperor_main_condition_itp_emp_01_active(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_32");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        if (itp_emperor_main_condition__defaultCondition(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_34");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (itp_emperor_main_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            boolean hasResponse1 = false;
            if (itp_emperor_main_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_41");
                }
                utils.setScriptVar(player, "conversation.itp_emperor_main.branchId", 14);
                npcStartConversation(player, npc, "itp_emperor_main", message, responses);
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
        if (!conversationId.equals("itp_emperor_main"))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id npc = self;
        int branchId = utils.getIntScriptVar(player, "conversation.itp_emperor_main.branchId");
        if (branchId == 6 && itp_emperor_main_handleBranch6(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 10 && itp_emperor_main_handleBranch10(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 14 && itp_emperor_main_handleBranch14(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        chat.chat(npc, "Error:  Fell through all branches and responses for OnNpcConversationResponse.");
        utils.removeScriptVar(player, "conversation.itp_emperor_main.branchId");
        return SCRIPT_CONTINUE;
    }
}
