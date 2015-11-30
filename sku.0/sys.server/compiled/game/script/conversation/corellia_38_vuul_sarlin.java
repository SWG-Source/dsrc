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

public class corellia_38_vuul_sarlin extends script.base_script
{
    public corellia_38_vuul_sarlin()
    {
    }
    public static String c_stringFile = "conversation/corellia_38_vuul_sarlin";
    public boolean corellia_38_vuul_sarlin_condition__defaultCondition(obj_id player, obj_id npc) throws InterruptedException
    {
        return true;
    }
    public boolean corellia_38_vuul_sarlin_condition_taskOneActive(obj_id player, obj_id npc) throws InterruptedException
    {
        return groundquests.isTaskActive(player, "corellia_38_corsec_files_04", "corsec_files_04_05");
    }
    public boolean corellia_38_vuul_sarlin_condition_sidequestActive(obj_id player, obj_id npc) throws InterruptedException
    {
        return groundquests.isQuestActive(player, "corellia_38_sidequest_06");
    }
    public boolean corellia_38_vuul_sarlin_condition_sidequestComplete(obj_id player, obj_id npc) throws InterruptedException
    {
        return groundquests.isTaskActive(player, "corellia_38_sidequest_06", "corellia_38_sidequest_06_02");
    }
    public boolean corellia_38_vuul_sarlin_condition_AllDone(obj_id player, obj_id npc) throws InterruptedException
    {
        return groundquests.hasCompletedQuest(player, "corellia_38_sidequest_06");
    }
    public boolean corellia_38_vuul_sarlin_condition_ryllComplete(obj_id player, obj_id npc) throws InterruptedException
    {
        return groundquests.hasCompletedTask(player, "corellia_38_corsec_files_04", "corsec_files_04_05") && !groundquests.isQuestActive(player, "corellia_38_sidequest_06");
    }
    public void corellia_38_vuul_sarlin_action_sidequestGrant(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.grantQuest(player, "corellia_38_sidequest_06");
    }
    public void corellia_38_vuul_sarlin_action_ryllCompleteSignal(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.sendSignal(player, "corsec_files_04_05");
    }
    public void corellia_38_vuul_sarlin_action_sidequestSignal(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.sendSignal(player, "corellia_38_sidequest_06_02");
    }
    public int corellia_38_vuul_sarlin_handleBranch4(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_48"))
        {
            if (corellia_38_vuul_sarlin_condition__defaultCondition(player, npc))
            {
                corellia_38_vuul_sarlin_action_sidequestGrant(player, npc);
                string_id message = new string_id(c_stringFile, "s_49");
                utils.removeScriptVar(player, "conversation.corellia_38_vuul_sarlin.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_50"))
        {
            if (corellia_38_vuul_sarlin_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_51");
                utils.removeScriptVar(player, "conversation.corellia_38_vuul_sarlin.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int corellia_38_vuul_sarlin_handleBranch7(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_46"))
        {
            if (corellia_38_vuul_sarlin_condition__defaultCondition(player, npc))
            {
                corellia_38_vuul_sarlin_action_ryllCompleteSignal(player, npc);
                string_id message = new string_id(c_stringFile, "s_47");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (corellia_38_vuul_sarlin_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (corellia_38_vuul_sarlin_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_55");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_56");
                    }
                    utils.setScriptVar(player, "conversation.corellia_38_vuul_sarlin.branchId", 8);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.corellia_38_vuul_sarlin.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int corellia_38_vuul_sarlin_handleBranch8(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_55"))
        {
            if (corellia_38_vuul_sarlin_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_58");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (corellia_38_vuul_sarlin_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (corellia_38_vuul_sarlin_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_50");
                    }
                    utils.setScriptVar(player, "conversation.corellia_38_vuul_sarlin.branchId", 4);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.corellia_38_vuul_sarlin.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_56"))
        {
            if (corellia_38_vuul_sarlin_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_57");
                utils.removeScriptVar(player, "conversation.corellia_38_vuul_sarlin.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int corellia_38_vuul_sarlin_handleBranch9(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_48"))
        {
            if (corellia_38_vuul_sarlin_condition__defaultCondition(player, npc))
            {
                corellia_38_vuul_sarlin_action_sidequestGrant(player, npc);
                string_id message = new string_id(c_stringFile, "s_49");
                utils.removeScriptVar(player, "conversation.corellia_38_vuul_sarlin.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_50"))
        {
            if (corellia_38_vuul_sarlin_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_51");
                utils.removeScriptVar(player, "conversation.corellia_38_vuul_sarlin.branchId");
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
            detachScript(self, "conversation.corellia_38_vuul_sarlin");
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
        detachScript(self, "conversation.corellia_38_vuul_sarlin");
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
        if (corellia_38_vuul_sarlin_condition_AllDone(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_54");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        if (corellia_38_vuul_sarlin_condition_sidequestComplete(player, npc))
        {
            corellia_38_vuul_sarlin_action_sidequestSignal(player, npc);
            string_id message = new string_id(c_stringFile, "s_53");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        if (corellia_38_vuul_sarlin_condition_sidequestActive(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_52");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        if (corellia_38_vuul_sarlin_condition_ryllComplete(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_43");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (corellia_38_vuul_sarlin_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            boolean hasResponse1 = false;
            if (corellia_38_vuul_sarlin_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_50");
                }
                utils.setScriptVar(player, "conversation.corellia_38_vuul_sarlin.branchId", 4);
                npcStartConversation(player, npc, "corellia_38_vuul_sarlin", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (corellia_38_vuul_sarlin_condition_taskOneActive(player, npc))
        {
            corellia_38_vuul_sarlin_action_ryllCompleteSignal(player, npc);
            string_id message = new string_id(c_stringFile, "s_44");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (corellia_38_vuul_sarlin_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_46");
                }
                utils.setScriptVar(player, "conversation.corellia_38_vuul_sarlin.branchId", 7);
                npcStartConversation(player, npc, "corellia_38_vuul_sarlin", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (corellia_38_vuul_sarlin_condition__defaultCondition(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_45");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        chat.chat(npc, "Error:  All conditions for OnStartNpcConversation were false.");
        return SCRIPT_CONTINUE;
    }
    public int OnNpcConversationResponse(obj_id self, String conversationId, obj_id player, string_id response) throws InterruptedException
    {
        if (!conversationId.equals("corellia_38_vuul_sarlin"))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id npc = self;
        int branchId = utils.getIntScriptVar(player, "conversation.corellia_38_vuul_sarlin.branchId");
        if (branchId == 4 && corellia_38_vuul_sarlin_handleBranch4(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 7 && corellia_38_vuul_sarlin_handleBranch7(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 8 && corellia_38_vuul_sarlin_handleBranch8(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 9 && corellia_38_vuul_sarlin_handleBranch9(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        chat.chat(npc, "Error:  Fell through all branches and responses for OnNpcConversationResponse.");
        utils.removeScriptVar(player, "conversation.corellia_38_vuul_sarlin.branchId");
        return SCRIPT_CONTINUE;
    }
}
