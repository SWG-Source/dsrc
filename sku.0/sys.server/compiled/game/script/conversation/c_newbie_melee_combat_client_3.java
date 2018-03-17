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

public class c_newbie_melee_combat_client_3 extends script.base_script
{
    public c_newbie_melee_combat_client_3()
    {
    }
    public static String c_stringFile = "conversation/c_newbie_melee_combat_client_3";
    public boolean c_newbie_melee_combat_client_3_condition__defaultCondition(obj_id player, obj_id npc) throws InterruptedException
    {
        return true;
    }
    public boolean c_newbie_melee_combat_client_3_condition_onTaskTalk(obj_id player, obj_id npc) throws InterruptedException
    {
        return (groundquests.isTaskActive(player, "c_newbie_melee_quest_3", "talkClient1"));
    }
    public boolean c_newbie_melee_combat_client_3_condition_onTaskEscort(obj_id player, obj_id npc) throws InterruptedException
    {
        return (groundquests.isTaskActive(player, "c_newbie_melee_quest_3", "onEscort1"));
    }
    public boolean c_newbie_melee_combat_client_3_condition_completedEscort(obj_id player, obj_id npc) throws InterruptedException
    {
        return (groundquests.hasCompletedTask(player, "c_newbie_melee_quest_3", "onEscort1"));
    }
    public boolean c_newbie_melee_combat_client_3_condition_defeatedFirstEncounter(obj_id player, obj_id npc) throws InterruptedException
    {
        return (groundquests.hasCompletedTask(player, "c_newbie_melee_quest_3", "timer1"));
    }
    public boolean c_newbie_melee_combat_client_3_condition_defeatedSecondEncounter(obj_id player, obj_id npc) throws InterruptedException
    {
        return (groundquests.hasCompletedTask(player, "c_newbie_melee_quest_3", "encounter2"));
    }
    public boolean c_newbie_melee_combat_client_3_condition_onTaskEscortTwo(obj_id player, obj_id npc) throws InterruptedException
    {
        return (groundquests.isTaskActive(player, "c_newbie_melee_quest_3", "onEscort2"));
    }
    public boolean c_newbie_melee_combat_client_3_condition_completedTaskEscortTwo(obj_id player, obj_id npc) throws InterruptedException
    {
        return (groundquests.hasCompletedTask(player, "c_newbie_melee_quest_3", "onEscort2"));
    }
    public void c_newbie_melee_combat_client_3_action_signalTalked(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.sendSignal(player, "talkedClient1");
    }
    public void c_newbie_melee_combat_client_3_action_signalTalkedTwo(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.sendSignal(player, "talkedClient2");
    }
    public void c_newbie_melee_combat_client_3_action_signalTalkedThree(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.sendSignal(player, "talkedClient3");
    }
    public void c_newbie_melee_combat_client_3_action_signalEndEscort(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.sendSignal(player, "escortComplete");
        groundquests.completeTask(player, "c_newbie_melee_quest_3", "escortTask");
    }
    public int c_newbie_melee_combat_client_3_handleBranch5(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_262"))
        {
            if (c_newbie_melee_combat_client_3_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "smack_self");
                c_newbie_melee_combat_client_3_action_signalTalkedTwo(player, npc);
                string_id message = new string_id(c_stringFile, "s_264");
                utils.removeScriptVar(player, "conversation.c_newbie_melee_combat_client_3.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int c_newbie_melee_combat_client_3_handleBranch7(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_268"))
        {
            if (c_newbie_melee_combat_client_3_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "dismiss");
                string_id message = new string_id(c_stringFile, "s_270");
                utils.removeScriptVar(player, "conversation.c_newbie_melee_combat_client_3.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int c_newbie_melee_combat_client_3_handleBranch9(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_274"))
        {
            doAnimationAction(player, "nod");
            if (c_newbie_melee_combat_client_3_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "nod_head_once");
                c_newbie_melee_combat_client_3_action_signalTalked(player, npc);
                string_id message = new string_id(c_stringFile, "s_276");
                utils.removeScriptVar(player, "conversation.c_newbie_melee_combat_client_3.branchId");
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
            detachScript(self, "conversation.c_newbie_melee_combat_client_3");
        }
        setInvulnerable(self, false);
        setCondition(self, CONDITION_CONVERSABLE);
        setName(self, new string_id("npc_name", "tobo"));
        return SCRIPT_CONTINUE;
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        setInvulnerable(self, false);
        setCondition(self, CONDITION_CONVERSABLE);
        setName(self, new string_id("npc_name", "tobo"));
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
        detachScript(self, "conversation.c_newbie_melee_combat_client_3");
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
        if (c_newbie_melee_combat_client_3_condition_completedTaskEscortTwo(player, npc))
        {
            doAnimationAction(npc, "goodbye");
            doAnimationAction(player, "goodbye");
            c_newbie_melee_combat_client_3_action_signalEndEscort(player, npc);
            string_id message = new string_id(c_stringFile, "s_252");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        if (c_newbie_melee_combat_client_3_condition_onTaskEscortTwo(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_254");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        if (c_newbie_melee_combat_client_3_condition_defeatedSecondEncounter(player, npc))
        {
            c_newbie_melee_combat_client_3_action_signalTalkedThree(player, npc);
            string_id message = new string_id(c_stringFile, "s_256");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        if (c_newbie_melee_combat_client_3_condition_defeatedFirstEncounter(player, npc))
        {
            doAnimationAction(npc, "stamp_feet");
            string_id message = new string_id(c_stringFile, "s_258");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        if (c_newbie_melee_combat_client_3_condition_completedEscort(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_260");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (c_newbie_melee_combat_client_3_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_262");
                }
                utils.setScriptVar(player, "conversation.c_newbie_melee_combat_client_3.branchId", 5);
                npcStartConversation(player, npc, "c_newbie_melee_combat_client_3", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (c_newbie_melee_combat_client_3_condition_onTaskEscort(player, npc))
        {
            doAnimationAction(npc, "shake_head_disgust");
            string_id message = new string_id(c_stringFile, "s_266");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (c_newbie_melee_combat_client_3_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_268");
                }
                utils.setScriptVar(player, "conversation.c_newbie_melee_combat_client_3.branchId", 7);
                npcStartConversation(player, npc, "c_newbie_melee_combat_client_3", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (c_newbie_melee_combat_client_3_condition_onTaskTalk(player, npc))
        {
            doAnimationAction(npc, "greet");
            doAnimationAction(player, "greet");
            string_id message = new string_id(c_stringFile, "s_272");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (c_newbie_melee_combat_client_3_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_274");
                }
                utils.setScriptVar(player, "conversation.c_newbie_melee_combat_client_3.branchId", 9);
                npcStartConversation(player, npc, "c_newbie_melee_combat_client_3", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (c_newbie_melee_combat_client_3_condition__defaultCondition(player, npc))
        {
            doAnimationAction(npc, "dismiss");
            doAnimationAction(player, "greet");
            string_id message = new string_id(c_stringFile, "s_278");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        chat.chat(npc, "Error:  All conditions for OnStartNpcConversation were false.");
        return SCRIPT_CONTINUE;
    }
    public int OnNpcConversationResponse(obj_id self, String conversationId, obj_id player, string_id response) throws InterruptedException
    {
        if (!conversationId.equals("c_newbie_melee_combat_client_3"))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id npc = self;
        int branchId = utils.getIntScriptVar(player, "conversation.c_newbie_melee_combat_client_3.branchId");
        if (branchId == 5 && c_newbie_melee_combat_client_3_handleBranch5(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 7 && c_newbie_melee_combat_client_3_handleBranch7(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 9 && c_newbie_melee_combat_client_3_handleBranch9(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        chat.chat(npc, "Error:  Fell through all branches and responses for OnNpcConversationResponse.");
        utils.removeScriptVar(player, "conversation.c_newbie_melee_combat_client_3.branchId");
        return SCRIPT_CONTINUE;
    }
}
