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
import script.library.utils;

public class groundquest_01 extends script.base_script
{
    public groundquest_01()
    {
    }
    public static String c_stringFile = "conversation/groundquest_01";
    public boolean groundquest_01_condition__defaultCondition(obj_id player, obj_id npc) throws InterruptedException
    {
        return true;
    }
    public boolean groundquest_01_condition_hasQuest_kill_3_valarian_assassins(obj_id player, obj_id npc) throws InterruptedException
    {
        int questId = questGetQuestId("quest/kill_3_valarian_assassins");
        return questIsQuestActive(questId, player);
    }
    public boolean groundquest_01_condition_wonQuest_kill_3_valarian_assassins(obj_id player, obj_id npc) throws InterruptedException
    {
        int questId = questGetQuestId("quest/kill_3_valarian_assassins");
        return questIsQuestComplete(questId, player);
    }
    public void groundquest_01_action_grantQuest_kill_2_womprats(obj_id player, obj_id npc) throws InterruptedException
    {
        int questId = questGetQuestId("quest/kill_2_womprats");
        questActivateQuest(questId, player, npc);
    }
    public void groundquest_01_action_grantQuest_kill_3_womprats(obj_id player, obj_id npc) throws InterruptedException
    {
        int questId = questGetQuestId("quest/kill_3_womprats");
        questActivateQuest(questId, player, npc);
    }
    public void groundquest_01_action_grantQuest_kill_3_valarian_assassins(obj_id player, obj_id npc) throws InterruptedException
    {
        int questId = questGetQuestId("quest/kill_3_valarian_assassins");
        questActivateQuest(questId, player, npc);
    }
    public void groundquest_01_action_clearQuest_kill_3_valarian_assassins(obj_id player, obj_id npc) throws InterruptedException
    {
        int questId = questGetQuestId("quest/kill_3_valarian_assassins");
        questClearQuest(questId, player);
    }
    public int groundquest_01_handleBranch1(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_26e69dd2"))
        {
            if (groundquest_01_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_87d7e908");
                utils.removeScriptVar(player, "conversation.groundquest_01.branchId");
                chat.chat(npc, player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int groundquest_01_handleBranch3(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_ca992d2c"))
        {
            if (groundquest_01_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_7e24578a");
                utils.removeScriptVar(player, "conversation.groundquest_01.branchId");
                chat.chat(npc, player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_4e85991"))
        {
            if (groundquest_01_condition__defaultCondition(player, npc))
            {
                groundquest_01_action_clearQuest_kill_3_valarian_assassins(player, npc);
                string_id message = new string_id(c_stringFile, "s_3021e76e");
                utils.removeScriptVar(player, "conversation.groundquest_01.branchId");
                chat.chat(npc, player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int groundquest_01_handleBranch6(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_b0797972"))
        {
            if (groundquest_01_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "salute2");
                groundquest_01_action_grantQuest_kill_3_valarian_assassins(player, npc);
                string_id message = new string_id(c_stringFile, "s_266d5e58");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (groundquest_01_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_6c251948");
                    }
                    utils.setScriptVar(player, "conversation.groundquest_01.branchId", 7);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.groundquest_01.branchId");
                    chat.chat(npc, player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_5716a3cb"))
        {
            if (groundquest_01_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "bow");
                groundquest_01_action_grantQuest_kill_3_womprats(player, npc);
                string_id message = new string_id(c_stringFile, "s_64e9f319");
                utils.removeScriptVar(player, "conversation.groundquest_01.branchId");
                chat.chat(npc, player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_d6695e83"))
        {
            if (groundquest_01_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "goodbye");
                string_id message = new string_id(c_stringFile, "s_de3892ae");
                utils.removeScriptVar(player, "conversation.groundquest_01.branchId");
                chat.chat(npc, player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int groundquest_01_handleBranch7(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_6c251948"))
        {
            if (groundquest_01_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_a80d9308");
                utils.removeScriptVar(player, "conversation.groundquest_01.branchId");
                chat.chat(npc, player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        if ((!isMob(self)) || (isPlayer(self)))
        {
            detachScript(self, "conversation.groundquest_01");
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
        detachScript(self, "conversation.groundquest_01");
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
        if (groundquest_01_condition_wonQuest_kill_3_valarian_assassins(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_3c3aa26e");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (groundquest_01_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_26e69dd2");
                }
                utils.setScriptVar(player, "conversation.groundquest_01.branchId", 1);
                npcStartConversation(player, npc, "groundquest_01", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (groundquest_01_condition_hasQuest_kill_3_valarian_assassins(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_b0216abf");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (groundquest_01_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            boolean hasResponse1 = false;
            if (groundquest_01_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_ca992d2c");
                }
                if (hasResponse1)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_4e85991");
                }
                utils.setScriptVar(player, "conversation.groundquest_01.branchId", 3);
                npcStartConversation(player, npc, "groundquest_01", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (groundquest_01_condition__defaultCondition(player, npc))
        {
            doAnimationAction(npc, "greet");
            string_id message = new string_id(c_stringFile, "s_95fea403");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (groundquest_01_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            boolean hasResponse1 = false;
            if (groundquest_01_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse1 = true;
            }
            boolean hasResponse2 = false;
            if (groundquest_01_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_b0797972");
                }
                if (hasResponse1)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_5716a3cb");
                }
                if (hasResponse2)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_d6695e83");
                }
                utils.setScriptVar(player, "conversation.groundquest_01.branchId", 6);
                npcStartConversation(player, npc, "groundquest_01", message, responses);
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
        if (!conversationId.equals("groundquest_01"))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id npc = self;
        int branchId = utils.getIntScriptVar(player, "conversation.groundquest_01.branchId");
        if (branchId == 1 && groundquest_01_handleBranch1(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 3 && groundquest_01_handleBranch3(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 6 && groundquest_01_handleBranch6(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 7 && groundquest_01_handleBranch7(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        chat.chat(npc, "Error:  Fell through all branches and responses for OnNpcConversationResponse.");
        utils.removeScriptVar(player, "conversation.groundquest_01.branchId");
        return SCRIPT_CONTINUE;
    }
}
