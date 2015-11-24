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
import script.library.restuss_event;
import script.library.utils;

public class restuss_imperial_ambush_1 extends script.base_script
{
    public restuss_imperial_ambush_1()
    {
    }
    public static String c_stringFile = "conversation/restuss_imperial_ambush_1";
    public boolean restuss_imperial_ambush_1_condition__defaultCondition(obj_id player, obj_id npc) throws InterruptedException
    {
        return true;
    }
    public boolean restuss_imperial_ambush_1_condition_stage3ready(obj_id player, obj_id npc) throws InterruptedException
    {
        if (factions.isImperial(player) && restuss_event.isRestussInStageThree(npc))
        {
            return true;
        }
        else 
        {
            return false;
        }
    }
    public boolean restuss_imperial_ambush_1_condition_mission1Active(obj_id player, obj_id npc) throws InterruptedException
    {
        if (groundquests.isQuestActive(player, "restuss_imperial_st3_comm_array") || groundquests.isQuestActive(player, "restuss_imperial_st3_comm_array_spy"))
        {
            return true;
        }
        else 
        {
            return false;
        }
    }
    public boolean restuss_imperial_ambush_1_condition_mission1Success(obj_id player, obj_id npc) throws InterruptedException
    {
        if (groundquests.isTaskActive(player, "restuss_imperial_st3_comm_array", "returnExov1") || groundquests.isTaskActive(player, "restuss_imperial_st3_comm_array_spy", "returnExov1"))
        {
            return true;
        }
        else 
        {
            return false;
        }
    }
    public boolean restuss_imperial_ambush_1_condition_completedMission1(obj_id player, obj_id npc) throws InterruptedException
    {
        if (groundquests.hasCompletedQuest(player, "restuss_imperial_st3_comm_array") || groundquests.hasCompletedQuest(player, "restuss_imperial_st3_comm_array_spy"))
        {
            return true;
        }
        else 
        {
            return false;
        }
    }
    public boolean restuss_imperial_ambush_1_condition_mission2Active(obj_id player, obj_id npc) throws InterruptedException
    {
        return groundquests.isQuestActive(player, "restuss_imperial_st3_ambush_1");
    }
    public boolean restuss_imperial_ambush_1_condition_mission2Success(obj_id player, obj_id npc) throws InterruptedException
    {
        return groundquests.isTaskActive(player, "restuss_imperial_st3_ambush_1", "returnExov2");
    }
    public boolean restuss_imperial_ambush_1_condition_notSpy(obj_id player, obj_id npc) throws InterruptedException
    {
        if (utils.isProfession(player, utils.SPY) || utils.isProfession(player, utils.SMUGGLER))
        {
            return false;
        }
        else 
        {
            return true;
        }
    }
    public boolean restuss_imperial_ambush_1_condition_spy(obj_id player, obj_id npc) throws InterruptedException
    {
        if (utils.isProfession(player, utils.SPY) || utils.isProfession(player, utils.SMUGGLER))
        {
            return true;
        }
        else 
        {
            return false;
        }
    }
    public boolean restuss_imperial_ambush_1_condition_completedMission2(obj_id player, obj_id npc) throws InterruptedException
    {
        return groundquests.hasCompletedQuest(player, "restuss_imperial_st3_ambush_1");
    }
    public boolean restuss_imperial_ambush_1_condition_playerImperial(obj_id player, obj_id npc) throws InterruptedException
    {
        if (factions.isImperial(player))
        {
            return true;
        }
        else 
        {
            return false;
        }
    }
    public boolean restuss_imperial_ambush_1_condition_isRebelPlayer(obj_id player, obj_id npc) throws InterruptedException
    {
        return factions.isRebel(player);
    }
    public void restuss_imperial_ambush_1_action_giveCapture(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.grantQuest(player, "restuss_imperial_st3_comm_array");
    }
    public void restuss_imperial_ambush_1_action_reward1(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.sendSignal(player, "returnedExov1");
    }
    public void restuss_imperial_ambush_1_action_giveAmbush(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.grantQuest(player, "restuss_imperial_st3_ambush_1");
    }
    public void restuss_imperial_ambush_1_action_reward2(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.sendSignal(player, "returnedExov2");
    }
    public void restuss_imperial_ambush_1_action_giveCaptureSpy(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.grantQuest(player, "restuss_imperial_st3_comm_array_spy");
    }
    public void restuss_imperial_ambush_1_action_eject(obj_id player, obj_id npc) throws InterruptedException
    {
        expelFromBuilding(player);
    }
    public int restuss_imperial_ambush_1_handleBranch6(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_74"))
        {
            doAnimationAction(player, "salute2");
            if (restuss_imperial_ambush_1_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "salute2");
                restuss_imperial_ambush_1_action_giveAmbush(player, npc);
                string_id message = new string_id(c_stringFile, "s_76");
                utils.removeScriptVar(player, "conversation.restuss_imperial_ambush_1.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_64"))
        {
            doAnimationAction(player, "salute2");
            if (restuss_imperial_ambush_1_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "salute2");
                restuss_imperial_ambush_1_action_giveCapture(player, npc);
                string_id message = new string_id(c_stringFile, "s_68");
                utils.removeScriptVar(player, "conversation.restuss_imperial_ambush_1.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_71"))
        {
            doAnimationAction(player, "salute2");
            if (restuss_imperial_ambush_1_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "salute2");
                restuss_imperial_ambush_1_action_giveCaptureSpy(player, npc);
                string_id message = new string_id(c_stringFile, "s_75");
                utils.removeScriptVar(player, "conversation.restuss_imperial_ambush_1.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_94"))
        {
            doAnimationAction(player, "salute2");
            if (restuss_imperial_ambush_1_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "salute2");
                string_id message = new string_id(c_stringFile, "s_96");
                utils.removeScriptVar(player, "conversation.restuss_imperial_ambush_1.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int restuss_imperial_ambush_1_handleBranch11(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_26"))
        {
            doAnimationAction(player, "salute2");
            if (restuss_imperial_ambush_1_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "salute2");
                restuss_imperial_ambush_1_action_giveAmbush(player, npc);
                string_id message = new string_id(c_stringFile, "s_28");
                utils.removeScriptVar(player, "conversation.restuss_imperial_ambush_1.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_30"))
        {
            doAnimationAction(player, "shake_head_no");
            if (restuss_imperial_ambush_1_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_32");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (restuss_imperial_ambush_1_condition_notSpy(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (restuss_imperial_ambush_1_condition_spy(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (restuss_imperial_ambush_1_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_65");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_36");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_42");
                    }
                    utils.setScriptVar(player, "conversation.restuss_imperial_ambush_1.branchId", 13);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.restuss_imperial_ambush_1.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_46"))
        {
            doAnimationAction(player, "salute2");
            if (restuss_imperial_ambush_1_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "salute2");
                string_id message = new string_id(c_stringFile, "s_48");
                utils.removeScriptVar(player, "conversation.restuss_imperial_ambush_1.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int restuss_imperial_ambush_1_handleBranch13(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_65"))
        {
            doAnimationAction(player, "salute2");
            if (restuss_imperial_ambush_1_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "salute2");
                restuss_imperial_ambush_1_action_giveCapture(player, npc);
                string_id message = new string_id(c_stringFile, "s_67");
                utils.removeScriptVar(player, "conversation.restuss_imperial_ambush_1.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_36"))
        {
            doAnimationAction(player, "salute2");
            if (restuss_imperial_ambush_1_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "salute2");
                restuss_imperial_ambush_1_action_giveCaptureSpy(player, npc);
                string_id message = new string_id(c_stringFile, "s_69");
                utils.removeScriptVar(player, "conversation.restuss_imperial_ambush_1.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_42"))
        {
            doAnimationAction(player, "salute2");
            if (restuss_imperial_ambush_1_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "salute2");
                string_id message = new string_id(c_stringFile, "s_44");
                utils.removeScriptVar(player, "conversation.restuss_imperial_ambush_1.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int restuss_imperial_ambush_1_handleBranch18(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_52"))
        {
            doAnimationAction(player, "salute2");
            if (restuss_imperial_ambush_1_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "salute2");
                restuss_imperial_ambush_1_action_giveCapture(player, npc);
                string_id message = new string_id(c_stringFile, "s_54");
                utils.removeScriptVar(player, "conversation.restuss_imperial_ambush_1.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_61"))
        {
            doAnimationAction(player, "salute2");
            if (restuss_imperial_ambush_1_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "salute2");
                restuss_imperial_ambush_1_action_giveCaptureSpy(player, npc);
                string_id message = new string_id(c_stringFile, "s_63");
                utils.removeScriptVar(player, "conversation.restuss_imperial_ambush_1.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_56"))
        {
            doAnimationAction(player, "salute2");
            if (restuss_imperial_ambush_1_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "salute2");
                string_id message = new string_id(c_stringFile, "s_58");
                utils.removeScriptVar(player, "conversation.restuss_imperial_ambush_1.branchId");
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
            detachScript(self, "conversation.restuss_imperial_ambush_1");
        }
        setCondition(self, CONDITION_CONVERSABLE);
        setInvulnerable(self, true);
        return SCRIPT_CONTINUE;
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        setCondition(self, CONDITION_CONVERSABLE);
        setInvulnerable(self, true);
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
        detachScript(self, "conversation.restuss_imperial_ambush_1");
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
        if (restuss_imperial_ambush_1_condition_isRebelPlayer(player, npc))
        {
            restuss_imperial_ambush_1_action_eject(player, npc);
            string_id message = new string_id(c_stringFile, "s_57");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        if (restuss_imperial_ambush_1_condition_mission2Success(player, npc))
        {
            doAnimationAction(npc, "salute2");
            doAnimationAction(player, "salute2");
            restuss_imperial_ambush_1_action_reward2(player, npc);
            string_id message = new string_id(c_stringFile, "s_39");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        if (restuss_imperial_ambush_1_condition_mission1Success(player, npc))
        {
            doAnimationAction(npc, "salute2");
            doAnimationAction(player, "salute2");
            restuss_imperial_ambush_1_action_reward1(player, npc);
            string_id message = new string_id(c_stringFile, "s_10");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        if (restuss_imperial_ambush_1_condition_mission1Active(player, npc))
        {
            doAnimationAction(npc, "salute2");
            doAnimationAction(player, "salute2");
            string_id message = new string_id(c_stringFile, "s_9");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        if (restuss_imperial_ambush_1_condition_mission2Active(player, npc))
        {
            doAnimationAction(npc, "salute2");
            doAnimationAction(player, "salute2");
            string_id message = new string_id(c_stringFile, "s_25");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        if (restuss_imperial_ambush_1_condition_completedMission2(player, npc))
        {
            doAnimationAction(npc, "salute2");
            doAnimationAction(player, "salute2");
            string_id message = new string_id(c_stringFile, "s_72");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (restuss_imperial_ambush_1_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            boolean hasResponse1 = false;
            if (restuss_imperial_ambush_1_condition_notSpy(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse1 = true;
            }
            boolean hasResponse2 = false;
            if (restuss_imperial_ambush_1_condition_spy(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse2 = true;
            }
            boolean hasResponse3 = false;
            if (restuss_imperial_ambush_1_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse3 = true;
            }
            if (hasResponse)
            {
                int responseIndex = 0;
                string_id responses[] = new string_id[numberOfResponses];
                if (hasResponse0)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_74");
                }
                if (hasResponse1)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_64");
                }
                if (hasResponse2)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_71");
                }
                if (hasResponse3)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_94");
                }
                utils.setScriptVar(player, "conversation.restuss_imperial_ambush_1.branchId", 6);
                npcStartConversation(player, npc, "restuss_imperial_ambush_1", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (restuss_imperial_ambush_1_condition_completedMission1(player, npc))
        {
            doAnimationAction(npc, "salute2");
            doAnimationAction(player, "salute2");
            string_id message = new string_id(c_stringFile, "s_23");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (restuss_imperial_ambush_1_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            boolean hasResponse1 = false;
            if (restuss_imperial_ambush_1_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse1 = true;
            }
            boolean hasResponse2 = false;
            if (restuss_imperial_ambush_1_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_26");
                }
                if (hasResponse1)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_30");
                }
                if (hasResponse2)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_46");
                }
                utils.setScriptVar(player, "conversation.restuss_imperial_ambush_1.branchId", 11);
                npcStartConversation(player, npc, "restuss_imperial_ambush_1", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (restuss_imperial_ambush_1_condition_stage3ready(player, npc))
        {
            doAnimationAction(npc, "salute2");
            doAnimationAction(player, "salute2");
            string_id message = new string_id(c_stringFile, "s_50");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (restuss_imperial_ambush_1_condition_notSpy(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            boolean hasResponse1 = false;
            if (restuss_imperial_ambush_1_condition_spy(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse1 = true;
            }
            boolean hasResponse2 = false;
            if (restuss_imperial_ambush_1_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_52");
                }
                if (hasResponse1)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_61");
                }
                if (hasResponse2)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_56");
                }
                utils.setScriptVar(player, "conversation.restuss_imperial_ambush_1.branchId", 18);
                npcStartConversation(player, npc, "restuss_imperial_ambush_1", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (restuss_imperial_ambush_1_condition_playerImperial(player, npc))
        {
            doAnimationAction(npc, "salute2");
            doAnimationAction(player, "salute2");
            string_id message = new string_id(c_stringFile, "s_53");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        if (restuss_imperial_ambush_1_condition__defaultCondition(player, npc))
        {
            doAnimationAction(npc, "dismiss");
            string_id message = new string_id(c_stringFile, "s_60");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        chat.chat(npc, "Error:  All conditions for OnStartNpcConversation were false.");
        return SCRIPT_CONTINUE;
    }
    public int OnNpcConversationResponse(obj_id self, String conversationId, obj_id player, string_id response) throws InterruptedException
    {
        if (!conversationId.equals("restuss_imperial_ambush_1"))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id npc = self;
        int branchId = utils.getIntScriptVar(player, "conversation.restuss_imperial_ambush_1.branchId");
        if (branchId == 6 && restuss_imperial_ambush_1_handleBranch6(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 11 && restuss_imperial_ambush_1_handleBranch11(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 13 && restuss_imperial_ambush_1_handleBranch13(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 18 && restuss_imperial_ambush_1_handleBranch18(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        chat.chat(npc, "Error:  Fell through all branches and responses for OnNpcConversationResponse.");
        utils.removeScriptVar(player, "conversation.restuss_imperial_ambush_1.branchId");
        return SCRIPT_CONTINUE;
    }
}
