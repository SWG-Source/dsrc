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

public class c_newbie_melee_combat_client_1 extends script.base_script
{
    public c_newbie_melee_combat_client_1()
    {
    }
    public static String c_stringFile = "conversation/c_newbie_melee_combat_client_1";
    public boolean c_newbie_melee_combat_client_1_condition__defaultCondition(obj_id player, obj_id npc) throws InterruptedException
    {
        return true;
    }
    public boolean c_newbie_melee_combat_client_1_condition_onTaskTalk(obj_id player, obj_id npc) throws InterruptedException
    {
        return (groundquests.isTaskActive(player, "c_newbie_melee_quest_1", "talkOtwi1"));
    }
    public boolean c_newbie_melee_combat_client_1_condition_completedTalk(obj_id player, obj_id npc) throws InterruptedException
    {
        return (groundquests.hasCompletedTask(player, "c_newbie_melee_quest_1", "talkOtwi1"));
    }
    public boolean c_newbie_melee_combat_client_1_condition_completedEncounter(obj_id player, obj_id npc) throws InterruptedException
    {
        return (groundquests.hasCompletedTask(player, "c_newbie_melee_quest_1", "thugAttack"));
    }
    public void c_newbie_melee_combat_client_1_action_signalTalkedOne(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.sendSignal(player, "talkedOtwi1");
    }
    public void c_newbie_melee_combat_client_1_action_signalTalkedTwo(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.sendSignal(player, "talkedOtwi2");
    }
    public int c_newbie_melee_combat_client_1_handleBranch3(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_302"))
        {
            doAnimationAction(player, "nod");
            if (c_newbie_melee_combat_client_1_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "nod_head_once");
                c_newbie_melee_combat_client_1_action_signalTalkedOne(player, npc);
                string_id message = new string_id(c_stringFile, "s_304");
                utils.removeScriptVar(player, "conversation.c_newbie_melee_combat_client_1.branchId");
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
            detachScript(self, "conversation.c_newbie_melee_combat_client_1");
        }
        setInvulnerable(self, true);
        setCondition(self, CONDITION_CONVERSABLE);
        setName(self, new string_id("npc_name", "otwi_opawl"));
        return SCRIPT_CONTINUE;
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        setInvulnerable(self, true);
        setCondition(self, CONDITION_CONVERSABLE);
        setName(self, new string_id("npc_name", "otwi_opawl"));
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
        detachScript(self, "conversation.c_newbie_melee_combat_client_1");
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
        if (c_newbie_melee_combat_client_1_condition_completedEncounter(player, npc))
        {
            doAnimationAction(npc, "stamp_feet");
            c_newbie_melee_combat_client_1_action_signalTalkedTwo(player, npc);
            string_id message = new string_id(c_stringFile, "s_296");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        if (c_newbie_melee_combat_client_1_condition_completedTalk(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_298");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        if (c_newbie_melee_combat_client_1_condition_onTaskTalk(player, npc))
        {
            doAnimationAction(npc, "greet");
            doAnimationAction(player, "greet");
            string_id message = new string_id(c_stringFile, "s_300");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (c_newbie_melee_combat_client_1_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_302");
                }
                utils.setScriptVar(player, "conversation.c_newbie_melee_combat_client_1.branchId", 3);
                npcStartConversation(player, npc, "c_newbie_melee_combat_client_1", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (c_newbie_melee_combat_client_1_condition__defaultCondition(player, npc))
        {
            doAnimationAction(npc, "dismiss");
            doAnimationAction(player, "greet");
            string_id message = new string_id(c_stringFile, "s_306");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        chat.chat(npc, "Error:  All conditions for OnStartNpcConversation were false.");
        return SCRIPT_CONTINUE;
    }
    public int OnNpcConversationResponse(obj_id self, String conversationId, obj_id player, string_id response) throws InterruptedException
    {
        if (!conversationId.equals("c_newbie_melee_combat_client_1"))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id npc = self;
        int branchId = utils.getIntScriptVar(player, "conversation.c_newbie_melee_combat_client_1.branchId");
        if (branchId == 3 && c_newbie_melee_combat_client_1_handleBranch3(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        chat.chat(npc, "Error:  Fell through all branches and responses for OnNpcConversationResponse.");
        utils.removeScriptVar(player, "conversation.c_newbie_melee_combat_client_1.branchId");
        return SCRIPT_CONTINUE;
    }
}
