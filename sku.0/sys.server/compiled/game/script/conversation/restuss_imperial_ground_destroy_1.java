package script.conversation;

import script.library.*;
import script.*;

public class restuss_imperial_ground_destroy_1 extends script.base_script
{
    public restuss_imperial_ground_destroy_1()
    {
    }
    public static String c_stringFile = "conversation/restuss_imperial_ground_destroy_1";
    public boolean restuss_imperial_ground_destroy_1_condition__defaultCondition(obj_id player, obj_id npc) throws InterruptedException
    {
        return true;
    }
    public boolean restuss_imperial_ground_destroy_1_condition_completedKill1(obj_id player, obj_id npc) throws InterruptedException
    {
        return groundquests.isTaskActive(player, "restuss_imperial_soldier_1", "returnGregor");
    }
    public boolean restuss_imperial_ground_destroy_1_condition_killActive1(obj_id player, obj_id npc) throws InterruptedException
    {
        return groundquests.isQuestActive(player, "restuss_imperial_soldier_1");
    }
    public boolean restuss_imperial_ground_destroy_1_condition_completedKill2(obj_id player, obj_id npc) throws InterruptedException
    {
        return groundquests.isTaskActive(player, "restuss_imperial_soldier_2", "returnGregor2");
    }
    public boolean restuss_imperial_ground_destroy_1_condition_killActive2(obj_id player, obj_id npc) throws InterruptedException
    {
        return groundquests.isQuestActive(player, "restuss_imperial_soldier_2");
    }
    public boolean restuss_imperial_ground_destroy_1_condition_inPhase2(obj_id player, obj_id npc) throws InterruptedException
    {
        obj_id object = trial.getParent(npc);
        if (factions.isImperial(player) && (restuss_event.getPhase(object) == 2))
        {
            return true;
        }
        else 
        {
            return false;
        }
    }
    public boolean restuss_imperial_ground_destroy_1_condition_inPhase1(obj_id player, obj_id npc) throws InterruptedException
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
    public boolean restuss_imperial_ground_destroy_1_condition_enoughKills(obj_id player, obj_id npc) throws InterruptedException
    {
        obj_id object = trial.getParent(npc);
        if (factions.isImperial(player) && (restuss_event.getPhase(object) == 3))
        {
            return true;
        }
        else 
        {
            return false;
        }
    }
    public boolean restuss_imperial_ground_destroy_1_condition_isRebelPlayer(obj_id player, obj_id npc) throws InterruptedException
    {
        return factions.isRebel(player);
    }
    public void restuss_imperial_ground_destroy_1_action_givekill1(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.grantQuest(player, "restuss_imperial_soldier_1");
    }
    public void restuss_imperial_ground_destroy_1_action_signalDone(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.sendSignal(player, "returnedGregor");
        restuss_event.incrimentCompletedQuestCount(npc, "restuss_imperial_soldier_1");
        int phase = restuss_event.getPhase(npc);
        if (phase == 0)
        {
            float ratio = restuss_event.getCompletedQuestRatio(npc, "restuss_imperial_soldier_1");
            if (ratio > 0.5)
            {
                restuss_event.incrimentPhase(npc);
                return;
            }
        }
        if (phase == 1)
        {
            if (restuss_event.isRequiredCountMet(npc, "restuss_imperial_soldier_1"))
            {
                restuss_event.incrimentPhase(npc);
                return;
            }
        }
    }
    public void restuss_imperial_ground_destroy_1_action_givekill2(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.grantQuest(player, "restuss_imperial_soldier_2");
    }
    public void restuss_imperial_ground_destroy_1_action_signalDone2(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.sendSignal(player, "returnedGregor2");
        restuss_event.incrimentCompletedQuestCount(npc, "restuss_imperial_soldier_2");
        int phase = restuss_event.getPhase(npc);
        if (phase == 2)
        {
            if (restuss_event.isRequiredCountMet(npc, "restuss_imperial_soldier_2"))
            {
                restuss_event.incrimentPhase(npc);
                return;
            }
        }
    }
    public void restuss_imperial_ground_destroy_1_action_eject(obj_id player, obj_id npc) throws InterruptedException
    {
        expelFromBuilding(player);
    }
    public int restuss_imperial_ground_destroy_1_handleBranch6(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_38"))
        {
            doAnimationAction(player, "salute2");
            restuss_imperial_ground_destroy_1_action_givekill2(player, npc);
            if (restuss_imperial_ground_destroy_1_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "salute2");
                string_id message = new string_id(c_stringFile, "s_40");
                utils.removeScriptVar(player, "conversation.restuss_imperial_ground_destroy_1.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_42"))
        {
            doAnimationAction(player, "shake_head_no");
            if (restuss_imperial_ground_destroy_1_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "salute2");
                doAnimationAction(player, "salute2");
                string_id message = new string_id(c_stringFile, "s_44");
                utils.removeScriptVar(player, "conversation.restuss_imperial_ground_destroy_1.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int restuss_imperial_ground_destroy_1_handleBranch9(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_30"))
        {
            doAnimationAction(player, "salute2");
            restuss_imperial_ground_destroy_1_action_givekill2(player, npc);
            if (restuss_imperial_ground_destroy_1_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "salute2");
                string_id message = new string_id(c_stringFile, "s_32");
                utils.removeScriptVar(player, "conversation.restuss_imperial_ground_destroy_1.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_34"))
        {
            doAnimationAction(player, "shake_head_no");
            if (restuss_imperial_ground_destroy_1_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "salute2");
                doAnimationAction(player, "salute2");
                string_id message = new string_id(c_stringFile, "s_36");
                utils.removeScriptVar(player, "conversation.restuss_imperial_ground_destroy_1.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int restuss_imperial_ground_destroy_1_handleBranch12(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_24"))
        {
            doAnimationAction(player, "salute2");
            restuss_imperial_ground_destroy_1_action_givekill1(player, npc);
            if (restuss_imperial_ground_destroy_1_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "salute2");
                string_id message = new string_id(c_stringFile, "s_26");
                utils.removeScriptVar(player, "conversation.restuss_imperial_ground_destroy_1.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_29"))
        {
            doAnimationAction(player, "shake_head_no");
            if (restuss_imperial_ground_destroy_1_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "salute2");
                doAnimationAction(player, "salute2");
                string_id message = new string_id(c_stringFile, "s_35");
                utils.removeScriptVar(player, "conversation.restuss_imperial_ground_destroy_1.branchId");
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
            detachScript(self, "conversation.restuss_imperial_ground_destroy_1");
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
        detachScript(self, "conversation.restuss_imperial_ground_destroy_1");
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
        if (restuss_imperial_ground_destroy_1_condition_isRebelPlayer(player, npc))
        {
            restuss_imperial_ground_destroy_1_action_eject(player, npc);
            string_id message = new string_id(c_stringFile, "s_41");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        if (restuss_imperial_ground_destroy_1_condition_completedKill2(player, npc))
        {
            doAnimationAction(npc, "salute2");
            doAnimationAction(player, "salute2");
            restuss_imperial_ground_destroy_1_action_signalDone2(player, npc);
            string_id message = new string_id(c_stringFile, "s_13");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        if (restuss_imperial_ground_destroy_1_condition_completedKill1(player, npc))
        {
            doAnimationAction(npc, "salute2");
            doAnimationAction(player, "salute2");
            restuss_imperial_ground_destroy_1_action_signalDone(player, npc);
            string_id message = new string_id(c_stringFile, "s_6");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        if (restuss_imperial_ground_destroy_1_condition_killActive2(player, npc))
        {
            doAnimationAction(npc, "salute2");
            doAnimationAction(player, "salute2");
            string_id message = new string_id(c_stringFile, "s_11");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        if (restuss_imperial_ground_destroy_1_condition_killActive1(player, npc))
        {
            doAnimationAction(npc, "salute2");
            doAnimationAction(player, "salute2");
            string_id message = new string_id(c_stringFile, "s_9");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        if (restuss_imperial_ground_destroy_1_condition_enoughKills(player, npc))
        {
            doAnimationAction(npc, "salute2");
            doAnimationAction(player, "salute2");
            string_id message = new string_id(c_stringFile, "s_33");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (restuss_imperial_ground_destroy_1_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            boolean hasResponse1 = false;
            if (restuss_imperial_ground_destroy_1_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_38");
                }
                if (hasResponse1)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_42");
                }
                utils.setScriptVar(player, "conversation.restuss_imperial_ground_destroy_1.branchId", 6);
                npcStartConversation(player, npc, "restuss_imperial_ground_destroy_1", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (restuss_imperial_ground_destroy_1_condition_inPhase2(player, npc))
        {
            doAnimationAction(npc, "salute2");
            doAnimationAction(player, "salute2");
            string_id message = new string_id(c_stringFile, "s_28");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (restuss_imperial_ground_destroy_1_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            boolean hasResponse1 = false;
            if (restuss_imperial_ground_destroy_1_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_30");
                }
                if (hasResponse1)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_34");
                }
                utils.setScriptVar(player, "conversation.restuss_imperial_ground_destroy_1.branchId", 9);
                npcStartConversation(player, npc, "restuss_imperial_ground_destroy_1", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (restuss_imperial_ground_destroy_1_condition_inPhase1(player, npc))
        {
            doAnimationAction(npc, "salute2");
            doAnimationAction(player, "salute2");
            string_id message = new string_id(c_stringFile, "s_22");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (restuss_imperial_ground_destroy_1_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            boolean hasResponse1 = false;
            if (restuss_imperial_ground_destroy_1_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_24");
                }
                if (hasResponse1)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_29");
                }
                utils.setScriptVar(player, "conversation.restuss_imperial_ground_destroy_1.branchId", 12);
                npcStartConversation(player, npc, "restuss_imperial_ground_destroy_1", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (restuss_imperial_ground_destroy_1_condition__defaultCondition(player, npc))
        {
            doAnimationAction(npc, "dismiss");
            string_id message = new string_id(c_stringFile, "s_39");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        chat.chat(npc, "Error:  All conditions for OnStartNpcConversation were false.");
        return SCRIPT_CONTINUE;
    }
    public int OnNpcConversationResponse(obj_id self, String conversationId, obj_id player, string_id response) throws InterruptedException
    {
        if (!conversationId.equals("restuss_imperial_ground_destroy_1"))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id npc = self;
        int branchId = utils.getIntScriptVar(player, "conversation.restuss_imperial_ground_destroy_1.branchId");
        if (branchId == 6 && restuss_imperial_ground_destroy_1_handleBranch6(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 9 && restuss_imperial_ground_destroy_1_handleBranch9(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 12 && restuss_imperial_ground_destroy_1_handleBranch12(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        chat.chat(npc, "Error:  Fell through all branches and responses for OnNpcConversationResponse.");
        utils.removeScriptVar(player, "conversation.restuss_imperial_ground_destroy_1.branchId");
        return SCRIPT_CONTINUE;
    }
}
