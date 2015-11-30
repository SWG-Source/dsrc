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

public class ep3_forest_athnalu extends script.base_script
{
    public ep3_forest_athnalu()
    {
    }
    public static String c_stringFile = "conversation/ep3_forest_athnalu";
    public boolean ep3_forest_athnalu_condition__defaultCondition(obj_id player, obj_id npc) throws InterruptedException
    {
        return true;
    }
    public boolean ep3_forest_athnalu_condition_isGoodGuy(obj_id player, obj_id npc) throws InterruptedException
    {
        return (groundquests.isTaskActive(player, "ep3_forest_wirartu_epic_3", 0) || groundquests.hasCompletedQuest(player, "ep3_forest_wirartu_epic_3") || groundquests.hasCompletedQuest(player, "ep3_forest_kerritamba_epic_7"));
    }
    public boolean ep3_forest_athnalu_condition_isBadGuy(obj_id player, obj_id npc) throws InterruptedException
    {
        return (groundquests.isTaskActive(player, "ep3_forest_wirartu_epic_2", 0) || groundquests.hasCompletedQuest(player, "ep3_forest_wirartu_epic_2"));
    }
    public boolean ep3_forest_athnalu_condition_isTaskOneActive(obj_id player, obj_id npc) throws InterruptedException
    {
        return groundquests.isTaskActive(player, "ep3_forest_athnalu_quest_1", "dolls");
    }
    public boolean ep3_forest_athnalu_condition_hasCompletedTaskOne(obj_id player, obj_id npc) throws InterruptedException
    {
        return groundquests.isTaskActive(player, "ep3_forest_athnalu_quest_1", "return");
    }
    public boolean ep3_forest_athnalu_condition_hasCompletedQuestOne(obj_id player, obj_id npc) throws InterruptedException
    {
        return groundquests.hasCompletedQuest(player, "ep3_forest_athnalu_quest_1");
    }
    public boolean ep3_forest_athnalu_condition_isTaskActiveTwo(obj_id player, obj_id npc) throws InterruptedException
    {
        return groundquests.isTaskActive(player, "ep3_forest_athnalu_quest_2", "collecting");
    }
    public boolean ep3_forest_athnalu_condition_hasCompletedTaskTwo(obj_id player, obj_id npc) throws InterruptedException
    {
        return groundquests.isTaskActive(player, "ep3_forest_athnalu_quest_2", "return");
    }
    public boolean ep3_forest_athnalu_condition_hasCompletedQuestTwo(obj_id player, obj_id npc) throws InterruptedException
    {
        return groundquests.hasCompletedQuest(player, "ep3_forest_athnalu_quest_2");
    }
    public boolean ep3_forest_athnalu_condition_hasLanguage(obj_id player, obj_id npc) throws InterruptedException
    {
        return utils.canSpeakWookiee(player, npc);
    }
    public void ep3_forest_athnalu_action_giveSignalOne(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.sendSignal(player, "dolls");
    }
    public void ep3_forest_athnalu_action_grantQuestOne(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.grantQuest(player, "ep3_forest_athnalu_quest_1");
    }
    public void ep3_forest_athnalu_action_grantQuestTwo(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.grantQuest(player, "ep3_forest_athnalu_quest_2");
    }
    public void ep3_forest_athnalu_action_giveSignalTwo(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.sendSignal(player, "snakes");
    }
    public void ep3_forest_athnalu_action_Language(obj_id player, obj_id npc) throws InterruptedException
    {
        utils.emoteWookieeConfusion(player, npc);
    }
    public int ep3_forest_athnalu_handleBranch3(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_4180"))
        {
            if (ep3_forest_athnalu_condition__defaultCondition(player, npc))
            {
                ep3_forest_athnalu_action_giveSignalTwo(player, npc);
                string_id message = new string_id(c_stringFile, "s_4182");
                utils.removeScriptVar(player, "conversation.ep3_forest_athnalu.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int ep3_forest_athnalu_handleBranch6(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_4188"))
        {
            if (ep3_forest_athnalu_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_4190");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (ep3_forest_athnalu_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (ep3_forest_athnalu_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_4192");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_4196");
                    }
                    utils.setScriptVar(player, "conversation.ep3_forest_athnalu.branchId", 7);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.ep3_forest_athnalu.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int ep3_forest_athnalu_handleBranch7(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_4192"))
        {
            if (ep3_forest_athnalu_condition__defaultCondition(player, npc))
            {
                ep3_forest_athnalu_action_grantQuestTwo(player, npc);
                string_id message = new string_id(c_stringFile, "s_4194");
                utils.removeScriptVar(player, "conversation.ep3_forest_athnalu.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_4196"))
        {
            if (ep3_forest_athnalu_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_4198");
                utils.removeScriptVar(player, "conversation.ep3_forest_athnalu.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int ep3_forest_athnalu_handleBranch10(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_4202"))
        {
            if (ep3_forest_athnalu_condition__defaultCondition(player, npc))
            {
                ep3_forest_athnalu_action_giveSignalOne(player, npc);
                string_id message = new string_id(c_stringFile, "s_4204");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (ep3_forest_athnalu_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_4206");
                    }
                    utils.setScriptVar(player, "conversation.ep3_forest_athnalu.branchId", 11);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.ep3_forest_athnalu.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int ep3_forest_athnalu_handleBranch11(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_4206"))
        {
            if (ep3_forest_athnalu_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_4208");
                utils.removeScriptVar(player, "conversation.ep3_forest_athnalu.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int ep3_forest_athnalu_handleBranch13(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_4212"))
        {
            if (ep3_forest_athnalu_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_4214");
                utils.removeScriptVar(player, "conversation.ep3_forest_athnalu.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int ep3_forest_athnalu_handleBranch15(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_4218"))
        {
            if (ep3_forest_athnalu_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_4220");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (ep3_forest_athnalu_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_4222");
                    }
                    utils.setScriptVar(player, "conversation.ep3_forest_athnalu.branchId", 16);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.ep3_forest_athnalu.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int ep3_forest_athnalu_handleBranch16(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_4222"))
        {
            if (ep3_forest_athnalu_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_4224");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (ep3_forest_athnalu_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (ep3_forest_athnalu_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_4226");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_4234");
                    }
                    utils.setScriptVar(player, "conversation.ep3_forest_athnalu.branchId", 17);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.ep3_forest_athnalu.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int ep3_forest_athnalu_handleBranch17(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_4226"))
        {
            if (ep3_forest_athnalu_condition__defaultCondition(player, npc))
            {
                ep3_forest_athnalu_action_grantQuestOne(player, npc);
                string_id message = new string_id(c_stringFile, "s_4228");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (ep3_forest_athnalu_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_4230");
                    }
                    utils.setScriptVar(player, "conversation.ep3_forest_athnalu.branchId", 18);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.ep3_forest_athnalu.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_4234"))
        {
            if (ep3_forest_athnalu_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_4236");
                utils.removeScriptVar(player, "conversation.ep3_forest_athnalu.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int ep3_forest_athnalu_handleBranch18(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_4230"))
        {
            if (ep3_forest_athnalu_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_4232");
                utils.removeScriptVar(player, "conversation.ep3_forest_athnalu.branchId");
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
            detachScript(self, "conversation.ep3_forest_athnalu");
        }
        setCondition(self, CONDITION_CONVERSABLE);
        setName(self, new string_id("ep3/npc_names", "athnalu"));
        setInvulnerable(self, true);
        return SCRIPT_CONTINUE;
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        setCondition(self, CONDITION_CONVERSABLE);
        setName(self, new string_id("ep3/npc_names", "athnalu"));
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
        detachScript(self, "conversation.ep3_forest_athnalu");
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
        if (ep3_forest_athnalu_condition_hasLanguage(player, npc))
        {
            ep3_forest_athnalu_action_Language(player, npc);
            string_id message = new string_id(c_stringFile, "s_1136");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        if (ep3_forest_athnalu_condition_hasCompletedQuestTwo(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_4176");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        if (ep3_forest_athnalu_condition_hasCompletedTaskTwo(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_4178");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (ep3_forest_athnalu_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_4180");
                }
                utils.setScriptVar(player, "conversation.ep3_forest_athnalu.branchId", 3);
                npcStartConversation(player, npc, "ep3_forest_athnalu", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (ep3_forest_athnalu_condition_isTaskActiveTwo(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_4184");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        if (ep3_forest_athnalu_condition_hasCompletedQuestOne(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_4186");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (ep3_forest_athnalu_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_4188");
                }
                utils.setScriptVar(player, "conversation.ep3_forest_athnalu.branchId", 6);
                npcStartConversation(player, npc, "ep3_forest_athnalu", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (ep3_forest_athnalu_condition_hasCompletedTaskOne(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_4200");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (ep3_forest_athnalu_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_4202");
                }
                utils.setScriptVar(player, "conversation.ep3_forest_athnalu.branchId", 10);
                npcStartConversation(player, npc, "ep3_forest_athnalu", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (ep3_forest_athnalu_condition_isTaskOneActive(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_4210");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (ep3_forest_athnalu_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_4212");
                }
                utils.setScriptVar(player, "conversation.ep3_forest_athnalu.branchId", 13);
                npcStartConversation(player, npc, "ep3_forest_athnalu", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (ep3_forest_athnalu_condition_isGoodGuy(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_4216");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (ep3_forest_athnalu_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_4218");
                }
                utils.setScriptVar(player, "conversation.ep3_forest_athnalu.branchId", 15);
                npcStartConversation(player, npc, "ep3_forest_athnalu", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (ep3_forest_athnalu_condition_isBadGuy(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_4238");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        if (ep3_forest_athnalu_condition__defaultCondition(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_4240");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        chat.chat(npc, "Error:  All conditions for OnStartNpcConversation were false.");
        return SCRIPT_CONTINUE;
    }
    public int OnNpcConversationResponse(obj_id self, String conversationId, obj_id player, string_id response) throws InterruptedException
    {
        if (!conversationId.equals("ep3_forest_athnalu"))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id npc = self;
        int branchId = utils.getIntScriptVar(player, "conversation.ep3_forest_athnalu.branchId");
        if (branchId == 3 && ep3_forest_athnalu_handleBranch3(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 6 && ep3_forest_athnalu_handleBranch6(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 7 && ep3_forest_athnalu_handleBranch7(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 10 && ep3_forest_athnalu_handleBranch10(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 11 && ep3_forest_athnalu_handleBranch11(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 13 && ep3_forest_athnalu_handleBranch13(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 15 && ep3_forest_athnalu_handleBranch15(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 16 && ep3_forest_athnalu_handleBranch16(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 17 && ep3_forest_athnalu_handleBranch17(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 18 && ep3_forest_athnalu_handleBranch18(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        chat.chat(npc, "Error:  Fell through all branches and responses for OnNpcConversationResponse.");
        utils.removeScriptVar(player, "conversation.ep3_forest_athnalu.branchId");
        return SCRIPT_CONTINUE;
    }
}
