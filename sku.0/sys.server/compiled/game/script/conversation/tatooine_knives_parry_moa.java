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
import script.library.conversation;
import script.library.features;
import script.library.groundquests;
import script.library.utils;

public class tatooine_knives_parry_moa extends script.base_script
{
    public tatooine_knives_parry_moa()
    {
    }
    public static String c_stringFile = "conversation/tatooine_knives_parry_moa";
    public boolean tatooine_knives_parry_moa_condition__defaultCondition(obj_id player, obj_id npc) throws InterruptedException
    {
        return true;
    }
    public boolean tatooine_knives_parry_moa_condition_facePlayer2(obj_id player, obj_id npc) throws InterruptedException
    {
        faceTo(npc, player);
        return true;
    }
    public boolean tatooine_knives_parry_moa_condition_onPistolTask(obj_id player, obj_id npc) throws InterruptedException
    {
        faceTo(npc, player);
        int questId1 = questGetQuestId("quest/tatooine_knives_parry");
        int finishPistols = groundquests.getTaskId(questId1, "tatooine_knives_parry_e3");
        boolean onTask = questIsTaskActive(questId1, finishPistols, player);
        return onTask;
    }
    public boolean tatooine_knives_parry_moa_condition_questComplete(obj_id player, obj_id npc) throws InterruptedException
    {
        faceTo(npc, player);
        int questId1 = questGetQuestId("quest/tatooine_knives_parry_pt2");
        boolean OnTask = (questIsQuestComplete(questId1, player));
        return OnTask;
    }
    public boolean tatooine_knives_parry_moa_condition_firstContact(obj_id player, obj_id npc) throws InterruptedException
    {
        faceTo(npc, player);
        int questId1 = questGetQuestId("quest/tatooine_knives_parry");
        int finishPistols = groundquests.getTaskId(questId1, "tatooine_knives_parry_e2");
        boolean onTask = questIsTaskActive(questId1, finishPistols, player);
        return onTask;
    }
    public boolean tatooine_knives_parry_moa_condition_onPistolComplete(obj_id player, obj_id npc) throws InterruptedException
    {
        faceTo(npc, player);
        int questId1 = questGetQuestId("quest/tatooine_knives_parry");
        int pistolComplete = groundquests.getTaskId(questId1, "tatooine_knives_parry_e3");
        boolean onTask = (questIsTaskComplete(questId1, pistolComplete, player));
        return onTask;
    }
    public void tatooine_knives_parry_moa_action_signalWeapon(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.sendSignal(player, "tatoonie_knives_parry_launch_e3");
    }
    public void tatooine_knives_parry_moa_action_signalWeaponComplete(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.sendSignal(player, "tatooine_knives_parry_launch_e97");
    }
    public void tatooine_knives_parry_moa_action_facePlayer(obj_id player, obj_id npc) throws InterruptedException
    {
        faceTo(npc, player);
    }
    public int tatooine_knives_parry_moa_handleBranch4(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_47"))
        {
            if (tatooine_knives_parry_moa_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_48");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (tatooine_knives_parry_moa_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_50");
                    }
                    utils.setScriptVar(player, "conversation.tatooine_knives_parry_moa.branchId", 5);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.tatooine_knives_parry_moa.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int tatooine_knives_parry_moa_handleBranch5(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_50"))
        {
            if (tatooine_knives_parry_moa_condition__defaultCondition(player, npc))
            {
                tatooine_knives_parry_moa_action_signalWeapon(player, npc);
                string_id message = new string_id(c_stringFile, "s_51");
                utils.removeScriptVar(player, "conversation.tatooine_knives_parry_moa.branchId");
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
            detachScript(self, "conversation.tatooine_knives_parry_moa");
        }
        setCondition(self, CONDITION_CONVERSABLE);
        setCondition(self, CONDITION_INTERESTING);
        setInvulnerable(self, true);
        setName(self, "Moa");
        ai_lib.setDefaultCalmBehavior(self, ai_lib.BEHAVIOR_SENTINEL);
        return SCRIPT_CONTINUE;
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        setCondition(self, CONDITION_CONVERSABLE);
        setCondition(self, CONDITION_INTERESTING);
        setInvulnerable(self, true);
        setName(self, "Moa");
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
        detachScript(self, "conversation.tatooine_knives_parry_moa");
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
        if (tatooine_knives_parry_moa_condition_questComplete(player, npc))
        {
            doAnimationAction(npc, "slump_head");
            tatooine_knives_parry_moa_action_facePlayer(player, npc);
            string_id message = new string_id(c_stringFile, "s_29");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        if (tatooine_knives_parry_moa_condition_onPistolComplete(player, npc))
        {
            doAnimationAction(npc, "applause_excited");
            tatooine_knives_parry_moa_action_signalWeaponComplete(player, npc);
            string_id message = new string_id(c_stringFile, "s_28");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        if (tatooine_knives_parry_moa_condition_onPistolTask(player, npc))
        {
            tatooine_knives_parry_moa_action_facePlayer(player, npc);
            string_id message = new string_id(c_stringFile, "s_6");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        if (tatooine_knives_parry_moa_condition_firstContact(player, npc))
        {
            tatooine_knives_parry_moa_action_facePlayer(player, npc);
            string_id message = new string_id(c_stringFile, "s_8");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (tatooine_knives_parry_moa_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_47");
                }
                utils.setScriptVar(player, "conversation.tatooine_knives_parry_moa.branchId", 4);
                npcStartConversation(player, npc, "tatooine_knives_parry_moa", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (tatooine_knives_parry_moa_condition_facePlayer2(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_49");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        chat.chat(npc, "Error:  All conditions for OnStartNpcConversation were false.");
        return SCRIPT_CONTINUE;
    }
    public int OnNpcConversationResponse(obj_id self, String conversationId, obj_id player, string_id response) throws InterruptedException
    {
        if (!conversationId.equals("tatooine_knives_parry_moa"))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id npc = self;
        int branchId = utils.getIntScriptVar(player, "conversation.tatooine_knives_parry_moa.branchId");
        if (branchId == 4 && tatooine_knives_parry_moa_handleBranch4(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 5 && tatooine_knives_parry_moa_handleBranch5(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        chat.chat(npc, "Error:  Fell through all branches and responses for OnNpcConversationResponse.");
        utils.removeScriptVar(player, "conversation.tatooine_knives_parry_moa.branchId");
        return SCRIPT_CONTINUE;
    }
}
