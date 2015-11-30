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

public class c_smuggler_radeld extends script.base_script
{
    public c_smuggler_radeld()
    {
    }
    public static String c_stringFile = "conversation/c_smuggler_radeld";
    public boolean c_smuggler_radeld_condition__defaultCondition(obj_id player, obj_id npc) throws InterruptedException
    {
        return true;
    }
    public boolean c_smuggler_radeld_condition_isTaskActiveLink(obj_id player, obj_id npc) throws InterruptedException
    {
        return groundquests.isQuestActiveOrComplete(player, "c_smuggler_han_link");
    }
    public boolean c_smuggler_radeld_condition_isTaskActiveSteal1(obj_id player, obj_id npc) throws InterruptedException
    {
        return (groundquests.isTaskActive(player, "c_smuggler_steal_1", "dostuff"));
    }
    public boolean c_smuggler_radeld_condition_hasCompletedTaskSteal1(obj_id player, obj_id npc) throws InterruptedException
    {
        return groundquests.isTaskActive(player, "c_smuggler_steal_1", "imdone");
    }
    public boolean c_smuggler_radeld_condition_hasCompletedQuestsSteal(obj_id player, obj_id npc) throws InterruptedException
    {
        return (groundquests.hasCompletedQuest(player, "c_smuggler_steal_1") || groundquests.hasCompletedQuest(player, "c_smuggler_steal_2"));
    }
    public void c_smuggler_radeld_action_sendLinkSignal(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.sendSignal(player, "newguy");
    }
    public void c_smuggler_radeld_action_grantSteal1(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.grantQuest(player, "c_smuggler_steal_1");
    }
    public void c_smuggler_radeld_action_grantSteal2(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.sendSignal(player, "imdone");
    }
    public void c_smuggler_radeld_action_sendDoneSignal(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.sendSignal(player, "radeld");
    }
    public int c_smuggler_radeld_handleBranch2(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_21"))
        {
            if (c_smuggler_radeld_condition__defaultCondition(player, npc))
            {
                c_smuggler_radeld_action_grantSteal2(player, npc);
                string_id message = new string_id(c_stringFile, "s_22");
                utils.removeScriptVar(player, "conversation.c_smuggler_radeld.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int c_smuggler_radeld_handleBranch4(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_16"))
        {
            if (c_smuggler_radeld_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_17");
                utils.removeScriptVar(player, "conversation.c_smuggler_radeld.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int c_smuggler_radeld_handleBranch6(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_25"))
        {
            if (c_smuggler_radeld_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_27");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (c_smuggler_radeld_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_29");
                    }
                    utils.setScriptVar(player, "conversation.c_smuggler_radeld.branchId", 7);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.c_smuggler_radeld.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_53"))
        {
            if (c_smuggler_radeld_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_54");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (c_smuggler_radeld_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (c_smuggler_radeld_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_56");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_60");
                    }
                    utils.setScriptVar(player, "conversation.c_smuggler_radeld.branchId", 13);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.c_smuggler_radeld.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int c_smuggler_radeld_handleBranch7(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_29"))
        {
            if (c_smuggler_radeld_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_31");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (c_smuggler_radeld_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_33");
                    }
                    utils.setScriptVar(player, "conversation.c_smuggler_radeld.branchId", 8);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.c_smuggler_radeld.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int c_smuggler_radeld_handleBranch8(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_33"))
        {
            if (c_smuggler_radeld_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_35");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (c_smuggler_radeld_condition__defaultCondition(player, npc))
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
                    utils.setScriptVar(player, "conversation.c_smuggler_radeld.branchId", 9);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.c_smuggler_radeld.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int c_smuggler_radeld_handleBranch9(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_37"))
        {
            if (c_smuggler_radeld_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "shrug_shoulders");
                c_smuggler_radeld_action_sendLinkSignal(player, npc);
                string_id message = new string_id(c_stringFile, "s_39");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (c_smuggler_radeld_condition__defaultCondition(player, npc))
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
                    utils.setScriptVar(player, "conversation.c_smuggler_radeld.branchId", 10);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.c_smuggler_radeld.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int c_smuggler_radeld_handleBranch10(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_41"))
        {
            if (c_smuggler_radeld_condition__defaultCondition(player, npc))
            {
                c_smuggler_radeld_action_grantSteal1(player, npc);
                string_id message = new string_id(c_stringFile, "s_43");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (c_smuggler_radeld_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_45");
                    }
                    utils.setScriptVar(player, "conversation.c_smuggler_radeld.branchId", 11);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.c_smuggler_radeld.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int c_smuggler_radeld_handleBranch11(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_45"))
        {
            if (c_smuggler_radeld_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_50");
                utils.removeScriptVar(player, "conversation.c_smuggler_radeld.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int c_smuggler_radeld_handleBranch13(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_56"))
        {
            if (c_smuggler_radeld_condition__defaultCondition(player, npc))
            {
                c_smuggler_radeld_action_grantSteal1(player, npc);
                string_id message = new string_id(c_stringFile, "s_58");
                utils.removeScriptVar(player, "conversation.c_smuggler_radeld.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_60"))
        {
            if (c_smuggler_radeld_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_31");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (c_smuggler_radeld_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_33");
                    }
                    utils.setScriptVar(player, "conversation.c_smuggler_radeld.branchId", 8);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.c_smuggler_radeld.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        if ((!isMob(self)) || (isPlayer(self)))
        {
            detachScript(self, "conversation.c_smuggler_radeld");
        }
        setCondition(self, CONDITION_CONVERSABLE);
        setName(self, "Radeld Siwar");
        return SCRIPT_CONTINUE;
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        setCondition(self, CONDITION_CONVERSABLE);
        setName(self, "Radeld Siwar");
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
        detachScript(self, "conversation.c_smuggler_radeld");
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
        if (c_smuggler_radeld_condition_hasCompletedQuestsSteal(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_49");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        if (c_smuggler_radeld_condition_hasCompletedTaskSteal1(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_20");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (c_smuggler_radeld_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_21");
                }
                utils.setScriptVar(player, "conversation.c_smuggler_radeld.branchId", 2);
                npcStartConversation(player, npc, "c_smuggler_radeld", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (c_smuggler_radeld_condition_isTaskActiveSteal1(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_15");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (c_smuggler_radeld_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_16");
                }
                utils.setScriptVar(player, "conversation.c_smuggler_radeld.branchId", 4);
                npcStartConversation(player, npc, "c_smuggler_radeld", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (c_smuggler_radeld_condition_isTaskActiveLink(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_18");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (c_smuggler_radeld_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            boolean hasResponse1 = false;
            if (c_smuggler_radeld_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_25");
                }
                if (hasResponse1)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_53");
                }
                utils.setScriptVar(player, "conversation.c_smuggler_radeld.branchId", 6);
                npcStartConversation(player, npc, "c_smuggler_radeld", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (c_smuggler_radeld_condition__defaultCondition(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_62");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        chat.chat(npc, "Error:  All conditions for OnStartNpcConversation were false.");
        return SCRIPT_CONTINUE;
    }
    public int OnNpcConversationResponse(obj_id self, String conversationId, obj_id player, string_id response) throws InterruptedException
    {
        if (!conversationId.equals("c_smuggler_radeld"))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id npc = self;
        int branchId = utils.getIntScriptVar(player, "conversation.c_smuggler_radeld.branchId");
        if (branchId == 2 && c_smuggler_radeld_handleBranch2(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 4 && c_smuggler_radeld_handleBranch4(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 6 && c_smuggler_radeld_handleBranch6(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 7 && c_smuggler_radeld_handleBranch7(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 8 && c_smuggler_radeld_handleBranch8(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 9 && c_smuggler_radeld_handleBranch9(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 10 && c_smuggler_radeld_handleBranch10(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 11 && c_smuggler_radeld_handleBranch11(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 13 && c_smuggler_radeld_handleBranch13(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        chat.chat(npc, "Error:  Fell through all branches and responses for OnNpcConversationResponse.");
        utils.removeScriptVar(player, "conversation.c_smuggler_radeld.branchId");
        return SCRIPT_CONTINUE;
    }
}
