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
import script.library.features;
import script.library.groundquests;
import script.library.utils;

public class tatooine_knives_fencing_keela extends script.base_script
{
    public tatooine_knives_fencing_keela()
    {
    }
    public static String c_stringFile = "conversation/tatooine_knives_fencing_keela";
    public boolean tatooine_knives_fencing_keela_condition__defaultCondition(obj_id player, obj_id npc) throws InterruptedException
    {
        return true;
    }
    public boolean tatooine_knives_fencing_keela_condition_onTaskSnitch(obj_id player, obj_id npc) throws InterruptedException
    {
        int questId1 = questGetQuestId("quest/tatooine_knives_fencing");
        int snitch = groundquests.getTaskId(questId1, "tatooine_knives_fencing_e6");
        boolean onTask = questIsTaskActive(questId1, snitch, player);
        return onTask;
    }
    public boolean tatooine_knives_fencing_keela_condition_questComplete(obj_id player, obj_id npc) throws InterruptedException
    {
        int questId1 = questGetQuestId("quest/tatooine_knives_fencing");
        boolean OnTask = (questIsQuestComplete(questId1, player));
        return OnTask;
    }
    public void tatooine_knives_fencing_keela_action_facePlayer(obj_id player, obj_id npc) throws InterruptedException
    {
        faceTo(npc, player);
    }
    public void tatooine_knives_fencing_keela_action_signalKeelaOver(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.sendSignal(player, "tatooine_knives_fencing_launch_e7");
    }
    public int tatooine_knives_fencing_keela_handleBranch2(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_42"))
        {
            if (tatooine_knives_fencing_keela_condition__defaultCondition(player, npc))
            {
                tatooine_knives_fencing_keela_action_signalKeelaOver(player, npc);
                string_id message = new string_id(c_stringFile, "s_43");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (tatooine_knives_fencing_keela_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_8");
                    }
                    utils.setScriptVar(player, "conversation.tatooine_knives_fencing_keela.branchId", 3);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.tatooine_knives_fencing_keela.branchId");
                    chat.chat(npc, player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int tatooine_knives_fencing_keela_handleBranch3(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_8"))
        {
            if (tatooine_knives_fencing_keela_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_9");
                utils.removeScriptVar(player, "conversation.tatooine_knives_fencing_keela.branchId");
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
            detachScript(self, "conversation.tatooine_knives_fencing_keela");
        }
        setCondition(self, CONDITION_CONVERSABLE);
        setCondition(self, CONDITION_INTERESTING);
        setInvulnerable(self, true);
        setName(self, "Keela");
        ai_lib.setDefaultCalmBehavior(self, ai_lib.BEHAVIOR_SENTINEL);
        return SCRIPT_CONTINUE;
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        setCondition(self, CONDITION_CONVERSABLE);
        setCondition(self, CONDITION_INTERESTING);
        setInvulnerable(self, true);
        setName(self, "Keela");
        ai_lib.setDefaultCalmBehavior(self, ai_lib.BEHAVIOR_SENTINEL);
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
        detachScript(self, "conversation.tatooine_knives_fencing_keela");
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
        if (tatooine_knives_fencing_keela_condition_questComplete(player, npc))
        {
            doAnimationAction(npc, "thank");
            tatooine_knives_fencing_keela_action_facePlayer(player, npc);
            string_id message = new string_id(c_stringFile, "s_48");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        if (tatooine_knives_fencing_keela_condition_onTaskSnitch(player, npc))
        {
            doAnimationAction(npc, "thank");
            tatooine_knives_fencing_keela_action_facePlayer(player, npc);
            string_id message = new string_id(c_stringFile, "s_41");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (tatooine_knives_fencing_keela_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_42");
                }
                utils.setScriptVar(player, "conversation.tatooine_knives_fencing_keela.branchId", 2);
                npcStartConversation(player, npc, "tatooine_knives_fencing_keela", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (tatooine_knives_fencing_keela_condition__defaultCondition(player, npc))
        {
            tatooine_knives_fencing_keela_action_facePlayer(player, npc);
            string_id message = new string_id(c_stringFile, "s_12");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        chat.chat(npc, "Error:  All conditions for OnStartNpcConversation were false.");
        return SCRIPT_CONTINUE;
    }
    public int OnNpcConversationResponse(obj_id self, String conversationId, obj_id player, string_id response) throws InterruptedException
    {
        if (!conversationId.equals("tatooine_knives_fencing_keela"))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id npc = self;
        int branchId = utils.getIntScriptVar(player, "conversation.tatooine_knives_fencing_keela.branchId");
        if (branchId == 2 && tatooine_knives_fencing_keela_handleBranch2(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 3 && tatooine_knives_fencing_keela_handleBranch3(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        chat.chat(npc, "Error:  Fell through all branches and responses for OnNpcConversationResponse.");
        utils.removeScriptVar(player, "conversation.tatooine_knives_fencing_keela.branchId");
        return SCRIPT_CONTINUE;
    }
}
