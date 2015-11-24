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
import script.library.utils;

public class talus_dearic_coa_braedic_ekirk extends script.base_script
{
    public talus_dearic_coa_braedic_ekirk()
    {
    }
    public static String c_stringFile = "conversation/talus_dearic_coa_braedic_ekirk";
    public boolean talus_dearic_coa_braedic_ekirk_condition__defaultCondition(obj_id player, obj_id npc) throws InterruptedException
    {
        return true;
    }
    public boolean talus_dearic_coa_braedic_ekirk_condition_main_quest_accepted(obj_id player, obj_id npc) throws InterruptedException
    {
        return groundquests.isQuestActive(player, "talus_dearic_crisis_of_allegiance");
    }
    public boolean talus_dearic_coa_braedic_ekirk_condition_imperial_quest_accepted(obj_id player, obj_id npc) throws InterruptedException
    {
        if (groundquests.isQuestActive(player, "talus_dearic_coa_imperial_view") || groundquests.isTaskActive(player, "talus_dearic_coa_imperial_view_end", "talus_dearic_coa_lag_tasks_reported_wfs"))
        {
            return true;
        }
        else 
        {
            return false;
        }
    }
    public boolean talus_dearic_coa_braedic_ekirk_condition_imperial_tasks_complete(obj_id player, obj_id npc) throws InterruptedException
    {
        return groundquests.isTaskActive(player, "talus_dearic_coa_imperial_view_end", "talus_dearic_coa_imperial_gratitude_wfs");
    }
    public boolean talus_dearic_coa_braedic_ekirk_condition_imperial_quest_complete(obj_id player, obj_id npc) throws InterruptedException
    {
        return groundquests.hasCompletedQuest(player, "talus_dearic_coa_imperial_view_end");
    }
    public void talus_dearic_coa_braedic_ekirk_action_accept_imperial_quest(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.grantQuest(player, "talus_dearic_coa_imperial_view");
        groundquests.sendSignal(player, "talus_dearic_coa_spoke_with_ekirk");
    }
    public void talus_dearic_coa_braedic_ekirk_action_complete_imperial_quest(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.sendSignal(player, "talus_dearic_coa_imperial_gratititude_received");
        groundquests.sendSignal(player, "talus_dearic_nsh_investigated");
    }
    public int talus_dearic_coa_braedic_ekirk_handleBranch1(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_37"))
        {
            doAnimationAction(player, "shake_head_no");
            if (talus_dearic_coa_braedic_ekirk_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "dismiss");
                string_id message = new string_id(c_stringFile, "s_39");
                utils.removeScriptVar(player, "conversation.talus_dearic_coa_braedic_ekirk.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int talus_dearic_coa_braedic_ekirk_handleBranch3(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_25"))
        {
            doAnimationAction(player, "thank");
            if (talus_dearic_coa_braedic_ekirk_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "explain");
                talus_dearic_coa_braedic_ekirk_action_complete_imperial_quest(player, npc);
                string_id message = new string_id(c_stringFile, "s_28");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (talus_dearic_coa_braedic_ekirk_condition__defaultCondition(player, npc))
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
                    utils.setScriptVar(player, "conversation.talus_dearic_coa_braedic_ekirk.branchId", 4);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.talus_dearic_coa_braedic_ekirk.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_26"))
        {
            doAnimationAction(player, "explain");
            if (talus_dearic_coa_braedic_ekirk_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "nod");
                string_id message = new string_id(c_stringFile, "s_27");
                utils.removeScriptVar(player, "conversation.talus_dearic_coa_braedic_ekirk.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int talus_dearic_coa_braedic_ekirk_handleBranch4(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_29"))
        {
            if (talus_dearic_coa_braedic_ekirk_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "thank");
                string_id message = new string_id(c_stringFile, "s_31");
                utils.removeScriptVar(player, "conversation.talus_dearic_coa_braedic_ekirk.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int talus_dearic_coa_braedic_ekirk_handleBranch7(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_38"))
        {
            doAnimationAction(player, "salute1");
            if (talus_dearic_coa_braedic_ekirk_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "dismiss");
                string_id message = new string_id(c_stringFile, "s_41");
                utils.removeScriptVar(player, "conversation.talus_dearic_coa_braedic_ekirk.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int talus_dearic_coa_braedic_ekirk_handleBranch9(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_42"))
        {
            doAnimationAction(player, "nod");
            if (talus_dearic_coa_braedic_ekirk_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "paper");
                string_id message = new string_id(c_stringFile, "s_44");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (talus_dearic_coa_braedic_ekirk_condition__defaultCondition(player, npc))
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
                    utils.setScriptVar(player, "conversation.talus_dearic_coa_braedic_ekirk.branchId", 10);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.talus_dearic_coa_braedic_ekirk.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_62"))
        {
            doAnimationAction(player, "shake_head_no");
            if (talus_dearic_coa_braedic_ekirk_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "dismiss");
                string_id message = new string_id(c_stringFile, "s_64");
                utils.removeScriptVar(player, "conversation.talus_dearic_coa_braedic_ekirk.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int talus_dearic_coa_braedic_ekirk_handleBranch10(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_46"))
        {
            doAnimationAction(player, "nod");
            if (talus_dearic_coa_braedic_ekirk_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "explain");
                doAnimationAction(player, "listen");
                string_id message = new string_id(c_stringFile, "s_48");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (talus_dearic_coa_braedic_ekirk_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (talus_dearic_coa_braedic_ekirk_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_50");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_58");
                    }
                    utils.setScriptVar(player, "conversation.talus_dearic_coa_braedic_ekirk.branchId", 11);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.talus_dearic_coa_braedic_ekirk.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int talus_dearic_coa_braedic_ekirk_handleBranch11(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_50"))
        {
            doAnimationAction(player, "nod_head_multiple");
            talus_dearic_coa_braedic_ekirk_action_accept_imperial_quest(player, npc);
            if (talus_dearic_coa_braedic_ekirk_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "explain");
                doAnimationAction(player, "listen");
                string_id message = new string_id(c_stringFile, "s_52");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (talus_dearic_coa_braedic_ekirk_condition__defaultCondition(player, npc))
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
                    utils.setScriptVar(player, "conversation.talus_dearic_coa_braedic_ekirk.branchId", 12);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.talus_dearic_coa_braedic_ekirk.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_58"))
        {
            doAnimationAction(player, "shake_head_no");
            if (talus_dearic_coa_braedic_ekirk_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "dismiss");
                string_id message = new string_id(c_stringFile, "s_60");
                utils.removeScriptVar(player, "conversation.talus_dearic_coa_braedic_ekirk.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int talus_dearic_coa_braedic_ekirk_handleBranch12(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_54"))
        {
            doAnimationAction(player, "nod");
            if (talus_dearic_coa_braedic_ekirk_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "dismiss");
                string_id message = new string_id(c_stringFile, "s_56");
                utils.removeScriptVar(player, "conversation.talus_dearic_coa_braedic_ekirk.branchId");
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
            detachScript(self, "conversation.talus_dearic_coa_braedic_ekirk");
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
        detachScript(self, "conversation.talus_dearic_coa_braedic_ekirk");
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
        if (talus_dearic_coa_braedic_ekirk_condition_imperial_quest_complete(player, npc))
        {
            doAnimationAction(npc, "paper");
            string_id message = new string_id(c_stringFile, "s_6");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (talus_dearic_coa_braedic_ekirk_condition__defaultCondition(player, npc))
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
                utils.setScriptVar(player, "conversation.talus_dearic_coa_braedic_ekirk.branchId", 1);
                npcStartConversation(player, npc, "talus_dearic_coa_braedic_ekirk", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (talus_dearic_coa_braedic_ekirk_condition_imperial_tasks_complete(player, npc))
        {
            doAnimationAction(npc, "thank");
            string_id message = new string_id(c_stringFile, "s_9");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (talus_dearic_coa_braedic_ekirk_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            boolean hasResponse1 = false;
            if (talus_dearic_coa_braedic_ekirk_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_26");
                }
                utils.setScriptVar(player, "conversation.talus_dearic_coa_braedic_ekirk.branchId", 3);
                npcStartConversation(player, npc, "talus_dearic_coa_braedic_ekirk", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (talus_dearic_coa_braedic_ekirk_condition_imperial_quest_accepted(player, npc))
        {
            doAnimationAction(npc, "shake_head_disgust");
            string_id message = new string_id(c_stringFile, "s_23");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (talus_dearic_coa_braedic_ekirk_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_38");
                }
                utils.setScriptVar(player, "conversation.talus_dearic_coa_braedic_ekirk.branchId", 7);
                npcStartConversation(player, npc, "talus_dearic_coa_braedic_ekirk", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (talus_dearic_coa_braedic_ekirk_condition_main_quest_accepted(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_40");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (talus_dearic_coa_braedic_ekirk_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            boolean hasResponse1 = false;
            if (talus_dearic_coa_braedic_ekirk_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_62");
                }
                utils.setScriptVar(player, "conversation.talus_dearic_coa_braedic_ekirk.branchId", 9);
                npcStartConversation(player, npc, "talus_dearic_coa_braedic_ekirk", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (talus_dearic_coa_braedic_ekirk_condition__defaultCondition(player, npc))
        {
            doAnimationAction(npc, "dismiss");
            string_id message = new string_id(c_stringFile, "s_66");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        chat.chat(npc, "Error:  All conditions for OnStartNpcConversation were false.");
        return SCRIPT_CONTINUE;
    }
    public int OnNpcConversationResponse(obj_id self, String conversationId, obj_id player, string_id response) throws InterruptedException
    {
        if (!conversationId.equals("talus_dearic_coa_braedic_ekirk"))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id npc = self;
        int branchId = utils.getIntScriptVar(player, "conversation.talus_dearic_coa_braedic_ekirk.branchId");
        if (branchId == 1 && talus_dearic_coa_braedic_ekirk_handleBranch1(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 3 && talus_dearic_coa_braedic_ekirk_handleBranch3(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 4 && talus_dearic_coa_braedic_ekirk_handleBranch4(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 7 && talus_dearic_coa_braedic_ekirk_handleBranch7(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 9 && talus_dearic_coa_braedic_ekirk_handleBranch9(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 10 && talus_dearic_coa_braedic_ekirk_handleBranch10(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 11 && talus_dearic_coa_braedic_ekirk_handleBranch11(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 12 && talus_dearic_coa_braedic_ekirk_handleBranch12(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        chat.chat(npc, "Error:  Fell through all branches and responses for OnNpcConversationResponse.");
        utils.removeScriptVar(player, "conversation.talus_dearic_coa_braedic_ekirk.branchId");
        return SCRIPT_CONTINUE;
    }
}
