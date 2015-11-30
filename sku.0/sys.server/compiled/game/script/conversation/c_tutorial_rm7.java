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

public class c_tutorial_rm7 extends script.base_script
{
    public c_tutorial_rm7()
    {
    }
    public static String c_stringFile = "conversation/c_tutorial_rm7";
    public boolean c_tutorial_rm7_condition__defaultCondition(obj_id player, obj_id npc) throws InterruptedException
    {
        return true;
    }
    public boolean c_tutorial_rm7_condition_playerOnQuest(obj_id player, obj_id npc) throws InterruptedException
    {
        int questId = questGetQuestId("quest/c_newbie_hall_03");
        int killPirate = groundquests.getTaskId(questId, "killPirate");
        boolean onTask = questIsQuestActive(questId, player) && !questIsTaskComplete(questId, killPirate, player);
        return onTask;
    }
    public boolean c_tutorial_rm7_condition_taskComplete(obj_id player, obj_id npc) throws InterruptedException
    {
        int questId = questGetQuestId("quest/c_newbie_hall_03");
        int killPirate = groundquests.getTaskId(questId, "killPirate");
        boolean onTask = questIsTaskComplete(questId, killPirate, player);
        return onTask;
    }
    public boolean c_tutorial_rm7_condition_playerOnQuestStep0602(obj_id player, obj_id npc) throws InterruptedException
    {
        int questId = questGetQuestId("quest/c_newbie_hall_02");
        int explainWaypoint = groundquests.getTaskId(questId, "explainWaypoint");
        boolean onTask = questIsQuestActive(questId, player) && !questIsTaskComplete(questId, explainWaypoint, player);
        return onTask;
    }
    public boolean c_tutorial_rm7_condition_playerOnQuestStep0603(obj_id player, obj_id npc) throws InterruptedException
    {
        int questId = questGetQuestId("quest/c_newbie_hall_01");
        int explainRadar = groundquests.getTaskId(questId, "explainRadar");
        boolean onTask = questIsQuestActive(questId, player) && !questIsTaskComplete(questId, explainRadar, player);
        return onTask;
    }
    public boolean c_tutorial_rm7_condition_playerOnQuestStep06(obj_id player, obj_id npc) throws InterruptedException
    {
        int questId = questGetQuestId("quest/c_newbie_hall_range01");
        int explainWeapons = groundquests.getTaskId(questId, "explainWeapons");
        boolean onTask = questIsQuestActive(questId, player) && !questIsTaskComplete(questId, explainWeapons, player);
        return onTask;
    }
    public void c_tutorial_rm7_action_facePlayer(obj_id player, obj_id npc) throws InterruptedException
    {
        faceTo(npc, player);
    }
    public void c_tutorial_rm7_action_actionSendSignal(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.sendSignal(player, "explainFight");
    }
    public int c_tutorial_rm7_handleBranch1(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_18"))
        {
            if (c_tutorial_rm7_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "shake_head_disgust");
                string_id message = new string_id(c_stringFile, "s_19");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (c_tutorial_rm7_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_27");
                    }
                    utils.setScriptVar(player, "conversation.c_tutorial_rm7.branchId", 2);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.c_tutorial_rm7.branchId");
                    chat.chat(npc, player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_20"))
        {
            if (c_tutorial_rm7_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "pose_proudly");
                string_id message = new string_id(c_stringFile, "s_21");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (c_tutorial_rm7_condition__defaultCondition(player, npc))
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
                    utils.setScriptVar(player, "conversation.c_tutorial_rm7.branchId", 4);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.c_tutorial_rm7.branchId");
                    chat.chat(npc, player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int c_tutorial_rm7_handleBranch2(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_27"))
        {
            if (c_tutorial_rm7_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "pound_fist_palm");
                c_tutorial_rm7_action_actionSendSignal(player, npc);
                string_id message = new string_id(c_stringFile, "s_28");
                utils.removeScriptVar(player, "conversation.c_tutorial_rm7.branchId");
                chat.chat(npc, player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int c_tutorial_rm7_handleBranch4(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_29"))
        {
            if (c_tutorial_rm7_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "pound_fist_palm");
                c_tutorial_rm7_action_actionSendSignal(player, npc);
                string_id message = new string_id(c_stringFile, "s_30");
                utils.removeScriptVar(player, "conversation.c_tutorial_rm7.branchId");
                chat.chat(npc, player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int c_tutorial_rm7_handleBranch6(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_22"))
        {
            if (c_tutorial_rm7_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "shake_head_disgust");
                string_id message = new string_id(c_stringFile, "s_23");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (c_tutorial_rm7_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_31");
                    }
                    utils.setScriptVar(player, "conversation.c_tutorial_rm7.branchId", 7);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.c_tutorial_rm7.branchId");
                    chat.chat(npc, player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_13"))
        {
            if (c_tutorial_rm7_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "pose_proudly");
                string_id message = new string_id(c_stringFile, "s_15");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (c_tutorial_rm7_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_34");
                    }
                    utils.setScriptVar(player, "conversation.c_tutorial_rm7.branchId", 9);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.c_tutorial_rm7.branchId");
                    chat.chat(npc, player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int c_tutorial_rm7_handleBranch7(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_31"))
        {
            if (c_tutorial_rm7_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "wave_on_dismissing");
                string_id message = new string_id(c_stringFile, "s_32");
                utils.removeScriptVar(player, "conversation.c_tutorial_rm7.branchId");
                chat.chat(npc, player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int c_tutorial_rm7_handleBranch9(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_34"))
        {
            if (c_tutorial_rm7_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "tap_foot");
                string_id message = new string_id(c_stringFile, "s_35");
                utils.removeScriptVar(player, "conversation.c_tutorial_rm7.branchId");
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
            detachScript(self, "conversation.c_tutorial_rm7");
        }
        setCondition(self, CONDITION_CONVERSABLE);
        setCondition(self, CONDITION_INTERESTING);
        setInvulnerable(self, true);
        setName(self, "Colonel Sheray");
        return SCRIPT_CONTINUE;
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        setCondition(self, CONDITION_CONVERSABLE);
        setCondition(self, CONDITION_INTERESTING);
        setInvulnerable(self, true);
        setName(self, "Colonel Sheray");
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
        detachScript(self, "conversation.c_tutorial_rm7");
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
        if (c_tutorial_rm7_condition_playerOnQuest(player, npc))
        {
            doAnimationAction(npc, "poke");
            c_tutorial_rm7_action_facePlayer(player, npc);
            string_id message = new string_id(c_stringFile, "s_7");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (c_tutorial_rm7_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            boolean hasResponse1 = false;
            if (c_tutorial_rm7_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_18");
                }
                if (hasResponse1)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_20");
                }
                utils.setScriptVar(player, "conversation.c_tutorial_rm7.branchId", 1);
                npcStartConversation(player, npc, "c_tutorial_rm7", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (c_tutorial_rm7_condition_taskComplete(player, npc))
        {
            doAnimationAction(npc, "wave_on_dismissing");
            c_tutorial_rm7_action_facePlayer(player, npc);
            string_id message = new string_id(c_stringFile, "s_53");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (c_tutorial_rm7_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            boolean hasResponse1 = false;
            if (c_tutorial_rm7_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_22");
                }
                if (hasResponse1)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_13");
                }
                utils.setScriptVar(player, "conversation.c_tutorial_rm7.branchId", 6);
                npcStartConversation(player, npc, "c_tutorial_rm7", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (c_tutorial_rm7_condition_playerOnQuestStep06(player, npc))
        {
            doAnimationAction(npc, "point_forward");
            c_tutorial_rm7_action_facePlayer(player, npc);
            string_id message = new string_id(c_stringFile, "s_33");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        if (c_tutorial_rm7_condition_playerOnQuestStep0602(player, npc))
        {
            doAnimationAction(npc, "point_forward");
            c_tutorial_rm7_action_facePlayer(player, npc);
            string_id message = new string_id(c_stringFile, "s_24");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        if (c_tutorial_rm7_condition_playerOnQuestStep0603(player, npc))
        {
            doAnimationAction(npc, "point_forward");
            c_tutorial_rm7_action_facePlayer(player, npc);
            string_id message = new string_id(c_stringFile, "s_26");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        chat.chat(npc, "Error:  All conditions for OnStartNpcConversation were false.");
        return SCRIPT_CONTINUE;
    }
    public int OnNpcConversationResponse(obj_id self, String conversationId, obj_id player, string_id response) throws InterruptedException
    {
        if (!conversationId.equals("c_tutorial_rm7"))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id npc = self;
        int branchId = utils.getIntScriptVar(player, "conversation.c_tutorial_rm7.branchId");
        if (branchId == 1 && c_tutorial_rm7_handleBranch1(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 2 && c_tutorial_rm7_handleBranch2(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 4 && c_tutorial_rm7_handleBranch4(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 6 && c_tutorial_rm7_handleBranch6(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 7 && c_tutorial_rm7_handleBranch7(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 9 && c_tutorial_rm7_handleBranch9(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        chat.chat(npc, "Error:  Fell through all branches and responses for OnNpcConversationResponse.");
        utils.removeScriptVar(player, "conversation.c_tutorial_rm7.branchId");
        return SCRIPT_CONTINUE;
    }
}
