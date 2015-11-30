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

public class c_newbie_melee_combat_client_2 extends script.base_script
{
    public c_newbie_melee_combat_client_2()
    {
    }
    public static String c_stringFile = "conversation/c_newbie_melee_combat_client_2";
    public boolean c_newbie_melee_combat_client_2_condition__defaultCondition(obj_id player, obj_id npc) throws InterruptedException
    {
        return true;
    }
    public boolean c_newbie_melee_combat_client_2_condition_onTaskTalk(obj_id player, obj_id npc) throws InterruptedException
    {
        return (groundquests.isTaskActive(player, "c_newbie_melee_quest_2", "talkCourier1"));
    }
    public boolean c_newbie_melee_combat_client_2_condition_onTaskEscort(obj_id player, obj_id npc) throws InterruptedException
    {
        return (groundquests.isTaskActive(player, "c_newbie_melee_quest_2", "escort"));
    }
    public boolean c_newbie_melee_combat_client_2_condition_completedEscort(obj_id player, obj_id npc) throws InterruptedException
    {
        return (groundquests.isTaskActive(player, "c_newbie_melee_quest_2", "talkCourier2"));
    }
    public void c_newbie_melee_combat_client_2_action_signalTalked(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.sendSignal(player, "talkedCourier1");
    }
    public void c_newbie_melee_combat_client_2_action_signalTalked2(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.sendSignal(player, "talkedCourier2");
        groundquests.completeTask(player, "c_newbie_melee_quest_2", "escortTask");
    }
    public int c_newbie_melee_combat_client_2_handleBranch3(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_288"))
        {
            doAnimationAction(player, "nod");
            if (c_newbie_melee_combat_client_2_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "nod_head_once");
                c_newbie_melee_combat_client_2_action_signalTalked(player, npc);
                string_id message = new string_id(c_stringFile, "s_290");
                utils.removeScriptVar(player, "conversation.c_newbie_melee_combat_client_2.branchId");
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
            detachScript(self, "conversation.c_newbie_melee_combat_client_2");
        }
        setInvulnerable(self, false);
        setCondition(self, CONDITION_CONVERSABLE);
        setName(self, new string_id("npc_name", "walter_orvett"));
        return SCRIPT_CONTINUE;
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        setInvulnerable(self, false);
        setCondition(self, CONDITION_CONVERSABLE);
        setName(self, new string_id("npc_name", "walter_orvett"));
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
        detachScript(self, "conversation.c_newbie_melee_combat_client_2");
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
        if (c_newbie_melee_combat_client_2_condition_completedEscort(player, npc))
        {
            doAnimationAction(npc, "goodbye");
            doAnimationAction(player, "goodbye");
            c_newbie_melee_combat_client_2_action_signalTalked2(player, npc);
            string_id message = new string_id(c_stringFile, "s_282");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        if (c_newbie_melee_combat_client_2_condition_onTaskEscort(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_284");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        if (c_newbie_melee_combat_client_2_condition_onTaskTalk(player, npc))
        {
            doAnimationAction(npc, "greet");
            doAnimationAction(player, "greet");
            string_id message = new string_id(c_stringFile, "s_286");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (c_newbie_melee_combat_client_2_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_288");
                }
                utils.setScriptVar(player, "conversation.c_newbie_melee_combat_client_2.branchId", 3);
                npcStartConversation(player, npc, "c_newbie_melee_combat_client_2", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (c_newbie_melee_combat_client_2_condition__defaultCondition(player, npc))
        {
            doAnimationAction(npc, "dismiss");
            doAnimationAction(player, "greet");
            string_id message = new string_id(c_stringFile, "s_292");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        chat.chat(npc, "Error:  All conditions for OnStartNpcConversation were false.");
        return SCRIPT_CONTINUE;
    }
    public int OnNpcConversationResponse(obj_id self, String conversationId, obj_id player, string_id response) throws InterruptedException
    {
        if (!conversationId.equals("c_newbie_melee_combat_client_2"))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id npc = self;
        int branchId = utils.getIntScriptVar(player, "conversation.c_newbie_melee_combat_client_2.branchId");
        if (branchId == 3 && c_newbie_melee_combat_client_2_handleBranch3(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        chat.chat(npc, "Error:  Fell through all branches and responses for OnNpcConversationResponse.");
        utils.removeScriptVar(player, "conversation.c_newbie_melee_combat_client_2.branchId");
        return SCRIPT_CONTINUE;
    }
}
