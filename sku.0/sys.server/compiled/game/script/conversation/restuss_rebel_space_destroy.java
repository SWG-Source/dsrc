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
import script.library.resource;
import script.library.restuss_event;
import script.library.space_flags;
import script.library.space_quest;
import script.library.trial;
import script.library.utils;

public class restuss_rebel_space_destroy extends script.base_script
{
    public restuss_rebel_space_destroy()
    {
    }
    public static String c_stringFile = "conversation/restuss_rebel_space_destroy";
    public boolean restuss_rebel_space_destroy_condition__defaultCondition(obj_id player, obj_id npc) throws InterruptedException
    {
        return true;
    }
    public boolean restuss_rebel_space_destroy_condition_completedKill1(obj_id player, obj_id npc) throws InterruptedException
    {
        return groundquests.isTaskActive(player, "restuss_rebel_space_destroy_1", "returnGrollo");
    }
    public boolean restuss_rebel_space_destroy_condition_killActive1(obj_id player, obj_id npc) throws InterruptedException
    {
        return groundquests.isQuestActive(player, "restuss_rebel_space_destroy_1");
    }
    public boolean restuss_rebel_space_destroy_condition_completedKill2(obj_id player, obj_id npc) throws InterruptedException
    {
        return groundquests.isTaskActive(player, "restuss_rebel_space_destroy_2", "returnGrollo2");
    }
    public boolean restuss_rebel_space_destroy_condition_killActive2(obj_id player, obj_id npc) throws InterruptedException
    {
        return groundquests.isQuestActive(player, "restuss_rebel_space_destroy_2");
    }
    public boolean restuss_rebel_space_destroy_condition_inPhase2(obj_id player, obj_id npc) throws InterruptedException
    {
        obj_id object = trial.getParent(npc);
        if (factions.isRebel(player) && (restuss_event.getPhase(object) == 2))
        {
            return true;
        }
        else 
        {
            return false;
        }
    }
    public boolean restuss_rebel_space_destroy_condition_inPhase1(obj_id player, obj_id npc) throws InterruptedException
    {
        if (factions.isRebel(player))
        {
            return true;
        }
        else 
        {
            return false;
        }
    }
    public boolean restuss_rebel_space_destroy_condition_enoughKills(obj_id player, obj_id npc) throws InterruptedException
    {
        obj_id object = trial.getParent(npc);
        if (factions.isRebel(player) && (restuss_event.getPhase(object) == 3))
        {
            return true;
        }
        else 
        {
            return false;
        }
    }
    public boolean restuss_rebel_space_destroy_condition_isRSFPilot(obj_id player, obj_id npc) throws InterruptedException
    {
        return space_flags.isSpaceTrack(player, space_flags.PRIVATEER_NABOO);
    }
    public boolean restuss_rebel_space_destroy_condition_isFailedTwo(obj_id player, obj_id npc) throws InterruptedException
    {
        if (space_quest.hasFailedQuestRecursive(player, "destroy", "restuss_rebel_destroy_2") || space_quest.hasAbortedQuestRecursive(player, "destroy", "restuss_rebel_destroy_2"))
        {
            return true;
        }
        return false;
    }
    public boolean restuss_rebel_space_destroy_condition_isFailedOne(obj_id player, obj_id npc) throws InterruptedException
    {
        if (space_quest.hasFailedQuestRecursive(player, "destroy", "restuss_rebel_destroy_1") || space_quest.hasAbortedQuestRecursive(player, "destroy", "restuss_rebel_destroy_1"))
        {
            return true;
        }
        return false;
    }
    public boolean restuss_rebel_space_destroy_condition_isImperialPlayer(obj_id player, obj_id npc) throws InterruptedException
    {
        return factions.isImperial(player);
    }
    public void restuss_rebel_space_destroy_action_givekill1(obj_id player, obj_id npc) throws InterruptedException
    {
        obj_id mobj = space_quest._getQuest(player, "destroy", "restuss_rebel_destroy_1");
        if (mobj != null) space_quest.setSilentQuestAborted(player, mobj);
        groundquests.clearQuest(player, "restuss_rebel_space_destroy_1");
        groundquests.grantQuest(player, "restuss_rebel_space_destroy_1");
    }
    public void restuss_rebel_space_destroy_action_signalDone(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.sendSignal(player, "returnedGrollo");
        restuss_event.incrimentCompletedQuestCount(npc, "restuss_rebel_space_destroy_1");
        int phase = restuss_event.getPhase(npc);
        if (phase == 0)
        {
            float ratio = restuss_event.getCompletedQuestRatio(npc, "restuss_rebel_space_destroy_1");
            if (ratio > .5)
            {
                restuss_event.incrimentPhase(npc);
                return;
            }
        }
        if (phase == 1)
        {
            if (restuss_event.isRequiredCountMet(npc, "restuss_rebel_space_destroy_1"))
            {
                restuss_event.incrimentPhase(npc);
                return;
            }
        }
    }
    public void restuss_rebel_space_destroy_action_givekill2(obj_id player, obj_id npc) throws InterruptedException
    {
        obj_id mobj = space_quest._getQuest(player, "destroy", "restuss_rebel_destroy_2");
        if (mobj != null) space_quest.setSilentQuestAborted(player, mobj);
        groundquests.clearQuest(player, "restuss_rebel_space_destroy_2");
        groundquests.grantQuest(player, "restuss_rebel_space_destroy_2");
    }
    public void restuss_rebel_space_destroy_action_signalDone2(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.sendSignal(player, "returnedGrollo2");
        restuss_event.incrimentCompletedQuestCount(npc, "restuss_rebel_space_destroy_2");
        int phase = restuss_event.getPhase(npc);
        if (phase == 2)
        {
            if (restuss_event.isRequiredCountMet(npc, "restuss_rebel_space_destroy_2"))
            {
                restuss_event.incrimentPhase(npc);
                return;
            }
        }
    }
    public void restuss_rebel_space_destroy_action_eject(obj_id player, obj_id npc) throws InterruptedException
    {
        expelFromBuilding(player);
    }
    public int restuss_rebel_space_destroy_handleBranch5(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_30"))
        {
            doAnimationAction(player, "salute1");
            restuss_rebel_space_destroy_action_givekill2(player, npc);
            if (restuss_rebel_space_destroy_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "salute1");
                string_id message = new string_id(c_stringFile, "s_32");
                utils.removeScriptVar(player, "conversation.restuss_rebel_space_destroy.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_34"))
        {
            doAnimationAction(player, "shake_head_no");
            if (restuss_rebel_space_destroy_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "salute1");
                doAnimationAction(player, "salute1");
                string_id message = new string_id(c_stringFile, "s_36");
                utils.removeScriptVar(player, "conversation.restuss_rebel_space_destroy.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int restuss_rebel_space_destroy_handleBranch6(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_27"))
        {
            doAnimationAction(player, "salute1");
            restuss_rebel_space_destroy_action_givekill1(player, npc);
            if (restuss_rebel_space_destroy_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "salute1");
                string_id message = new string_id(c_stringFile, "s_31");
                utils.removeScriptVar(player, "conversation.restuss_rebel_space_destroy.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_37"))
        {
            doAnimationAction(player, "shake_head_no");
            if (restuss_rebel_space_destroy_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "salute1");
                doAnimationAction(player, "salute1");
                string_id message = new string_id(c_stringFile, "s_43");
                utils.removeScriptVar(player, "conversation.restuss_rebel_space_destroy.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int restuss_rebel_space_destroy_handleBranch9(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_38"))
        {
            doAnimationAction(player, "salute1");
            restuss_rebel_space_destroy_action_givekill2(player, npc);
            if (restuss_rebel_space_destroy_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "salute1");
                string_id message = new string_id(c_stringFile, "s_40");
                utils.removeScriptVar(player, "conversation.restuss_rebel_space_destroy.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_42"))
        {
            doAnimationAction(player, "shake_head_no");
            if (restuss_rebel_space_destroy_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "salute1");
                doAnimationAction(player, "salute1");
                string_id message = new string_id(c_stringFile, "s_44");
                utils.removeScriptVar(player, "conversation.restuss_rebel_space_destroy.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int restuss_rebel_space_destroy_handleBranch12(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_30"))
        {
            doAnimationAction(player, "salute1");
            restuss_rebel_space_destroy_action_givekill2(player, npc);
            if (restuss_rebel_space_destroy_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "salute1");
                string_id message = new string_id(c_stringFile, "s_32");
                utils.removeScriptVar(player, "conversation.restuss_rebel_space_destroy.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_34"))
        {
            doAnimationAction(player, "shake_head_no");
            if (restuss_rebel_space_destroy_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "salute1");
                doAnimationAction(player, "salute1");
                string_id message = new string_id(c_stringFile, "s_36");
                utils.removeScriptVar(player, "conversation.restuss_rebel_space_destroy.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int restuss_rebel_space_destroy_handleBranch15(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_27"))
        {
            doAnimationAction(player, "salute1");
            restuss_rebel_space_destroy_action_givekill1(player, npc);
            if (restuss_rebel_space_destroy_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "salute1");
                string_id message = new string_id(c_stringFile, "s_31");
                utils.removeScriptVar(player, "conversation.restuss_rebel_space_destroy.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_37"))
        {
            doAnimationAction(player, "shake_head_no");
            if (restuss_rebel_space_destroy_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "salute1");
                doAnimationAction(player, "salute1");
                string_id message = new string_id(c_stringFile, "s_43");
                utils.removeScriptVar(player, "conversation.restuss_rebel_space_destroy.branchId");
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
            detachScript(self, "conversation.restuss_rebel_space_destroy");
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
        detachScript(self, "conversation.restuss_rebel_space_destroy");
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
        if (restuss_rebel_space_destroy_condition_isImperialPlayer(player, npc))
        {
            restuss_rebel_space_destroy_action_eject(player, npc);
            string_id message = new string_id(c_stringFile, "s_49");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        if (restuss_rebel_space_destroy_condition_isRSFPilot(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_41");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        if (restuss_rebel_space_destroy_condition_completedKill2(player, npc))
        {
            doAnimationAction(npc, "salute1");
            doAnimationAction(player, "salute1");
            restuss_rebel_space_destroy_action_signalDone2(player, npc);
            string_id message = new string_id(c_stringFile, "s_13");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        if (restuss_rebel_space_destroy_condition_completedKill1(player, npc))
        {
            doAnimationAction(npc, "salute1");
            doAnimationAction(player, "salute1");
            restuss_rebel_space_destroy_action_signalDone(player, npc);
            string_id message = new string_id(c_stringFile, "s_6");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        if (restuss_rebel_space_destroy_condition_isFailedTwo(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_45");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (restuss_rebel_space_destroy_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            boolean hasResponse1 = false;
            if (restuss_rebel_space_destroy_condition__defaultCondition(player, npc))
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
                utils.setScriptVar(player, "conversation.restuss_rebel_space_destroy.branchId", 12);
                npcStartConversation(player, npc, "restuss_rebel_space_destroy", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (restuss_rebel_space_destroy_condition_isFailedOne(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_46");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (restuss_rebel_space_destroy_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            boolean hasResponse1 = false;
            if (restuss_rebel_space_destroy_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_27");
                }
                if (hasResponse1)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_37");
                }
                utils.setScriptVar(player, "conversation.restuss_rebel_space_destroy.branchId", 15);
                npcStartConversation(player, npc, "restuss_rebel_space_destroy", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (restuss_rebel_space_destroy_condition_killActive2(player, npc))
        {
            doAnimationAction(npc, "salute1");
            doAnimationAction(player, "salute1");
            string_id message = new string_id(c_stringFile, "s_11");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        if (restuss_rebel_space_destroy_condition_killActive1(player, npc))
        {
            doAnimationAction(npc, "salute1");
            doAnimationAction(player, "salute1");
            string_id message = new string_id(c_stringFile, "s_12");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        if (restuss_rebel_space_destroy_condition_enoughKills(player, npc))
        {
            doAnimationAction(npc, "salute1");
            doAnimationAction(player, "salute1");
            string_id message = new string_id(c_stringFile, "s_33");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (restuss_rebel_space_destroy_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            boolean hasResponse1 = false;
            if (restuss_rebel_space_destroy_condition__defaultCondition(player, npc))
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
                utils.setScriptVar(player, "conversation.restuss_rebel_space_destroy.branchId", 9);
                npcStartConversation(player, npc, "restuss_rebel_space_destroy", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (restuss_rebel_space_destroy_condition_inPhase2(player, npc))
        {
            doAnimationAction(npc, "salute1");
            doAnimationAction(player, "salute1");
            string_id message = new string_id(c_stringFile, "s_28");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (restuss_rebel_space_destroy_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            boolean hasResponse1 = false;
            if (restuss_rebel_space_destroy_condition__defaultCondition(player, npc))
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
                utils.setScriptVar(player, "conversation.restuss_rebel_space_destroy.branchId", 12);
                npcStartConversation(player, npc, "restuss_rebel_space_destroy", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (restuss_rebel_space_destroy_condition_inPhase1(player, npc))
        {
            doAnimationAction(npc, "salute1");
            doAnimationAction(player, "salute1");
            string_id message = new string_id(c_stringFile, "s_25");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (restuss_rebel_space_destroy_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            boolean hasResponse1 = false;
            if (restuss_rebel_space_destroy_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_27");
                }
                if (hasResponse1)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_37");
                }
                utils.setScriptVar(player, "conversation.restuss_rebel_space_destroy.branchId", 15);
                npcStartConversation(player, npc, "restuss_rebel_space_destroy", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (restuss_rebel_space_destroy_condition__defaultCondition(player, npc))
        {
            doAnimationAction(npc, "dismiss");
            string_id message = new string_id(c_stringFile, "s_48");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        chat.chat(npc, "Error:  All conditions for OnStartNpcConversation were false.");
        return SCRIPT_CONTINUE;
    }
    public int OnNpcConversationResponse(obj_id self, String conversationId, obj_id player, string_id response) throws InterruptedException
    {
        if (!conversationId.equals("restuss_rebel_space_destroy"))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id npc = self;
        int branchId = utils.getIntScriptVar(player, "conversation.restuss_rebel_space_destroy.branchId");
        if (branchId == 5 && restuss_rebel_space_destroy_handleBranch5(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 6 && restuss_rebel_space_destroy_handleBranch6(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 9 && restuss_rebel_space_destroy_handleBranch9(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 12 && restuss_rebel_space_destroy_handleBranch12(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 15 && restuss_rebel_space_destroy_handleBranch15(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        chat.chat(npc, "Error:  Fell through all branches and responses for OnNpcConversationResponse.");
        utils.removeScriptVar(player, "conversation.restuss_rebel_space_destroy.branchId");
        return SCRIPT_CONTINUE;
    }
}
