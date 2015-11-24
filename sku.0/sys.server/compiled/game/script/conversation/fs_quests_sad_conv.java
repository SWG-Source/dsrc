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
import script.library.fs_quests;
import script.library.fs_quests_sad;
import script.library.geiger;
import script.library.quests;
import script.library.utils;

public class fs_quests_sad_conv extends script.base_script
{
    public fs_quests_sad_conv()
    {
    }
    public static String c_stringFile = "conversation/fs_quests_sad_conv";
    public boolean fs_quests_sad_conv_condition__defaultCondition(obj_id player, obj_id npc) throws InterruptedException
    {
        return true;
    }
    public boolean fs_quests_sad_conv_condition_hasCompletedAllTasksCond(obj_id player, obj_id npc) throws InterruptedException
    {
        return fs_quests_sad.hasCompletedAllTasks(player);
    }
    public boolean fs_quests_sad_conv_condition_hasExceededTaskLimitCond(obj_id player, obj_id npc) throws InterruptedException
    {
        return fs_quests_sad.hasExceededTaskLimit(player);
    }
    public boolean fs_quests_sad_conv_condition_hasTaskCond(obj_id player, obj_id npc) throws InterruptedException
    {
        return fs_quests_sad.hasTask(player);
    }
    public boolean fs_quests_sad_conv_condition_isVillageEligibleCond(obj_id player, obj_id npc) throws InterruptedException
    {
        return fs_quests.isVillageEligible(player);
    }
    public boolean fs_quests_sad_conv_condition_readyForTaskCond(obj_id player, obj_id npc) throws InterruptedException
    {
        return !fs_quests_sad.hasTask(player) && !fs_quests_sad.hasExceededTaskLimit(player) && !fs_quests_sad.hasCompletedAllTasks(player) && fs_quests.isVillageEligible(player);
    }
    public boolean fs_quests_sad_conv_condition_hasTheaterAssignedCond(obj_id player, obj_id npc) throws InterruptedException
    {
        return hasTheaterAssigned(player);
    }
    public boolean fs_quests_sad_conv_condition_hasNotCompletedAnyTasksCond(obj_id player, obj_id npc) throws InterruptedException
    {
        return !quests.isComplete("fs_quests_sad_task1", player);
    }
    public boolean fs_quests_sad_conv_condition_tooLowLevelCond(obj_id player, obj_id npc) throws InterruptedException
    {
        return true;
    }
    public boolean fs_quests_sad_conv_condition_hasFullInventoryCond(obj_id player, obj_id npc) throws InterruptedException
    {
        if (fs_quests_sad_conv_condition_readyForRewardCond(player, npc))
        {
            obj_id objInventory = utils.getInventoryContainer(player);
            if (getVolumeFree(objInventory) <= 0)
            {
                return true;
            }
        }
        return false;
    }
    public boolean fs_quests_sad_conv_condition_readyForRewardCond(obj_id player, obj_id npc) throws InterruptedException
    {
        return quests.isActive("fs_quests_sad_return8", player) && !fs_quests_sad_conv_condition_hasCompletedAllTasksCond(player, npc);
    }
    public boolean fs_quests_sad_conv_condition_completed1TaskCond(obj_id player, obj_id npc) throws InterruptedException
    {
        return fs_quests_sad_conv_condition_readyForTaskCond(player, npc) && fs_quests_sad.getNumberTasksCompleted(player) == 0;
    }
    public boolean fs_quests_sad_conv_condition_completed2TaskCond(obj_id player, obj_id npc) throws InterruptedException
    {
        return fs_quests_sad_conv_condition_readyForTaskCond(player, npc) && fs_quests_sad.getNumberTasksCompleted(player) == 1;
    }
    public boolean fs_quests_sad_conv_condition_completed3TaskCond(obj_id player, obj_id npc) throws InterruptedException
    {
        return fs_quests_sad_conv_condition_readyForTaskCond(player, npc) && fs_quests_sad.getNumberTasksCompleted(player) == 2;
    }
    public boolean fs_quests_sad_conv_condition_completed4TaskCond(obj_id player, obj_id npc) throws InterruptedException
    {
        return fs_quests_sad_conv_condition_readyForTaskCond(player, npc) && fs_quests_sad.getNumberTasksCompleted(player) == 3;
    }
    public boolean fs_quests_sad_conv_condition_completed5TaskCond(obj_id player, obj_id npc) throws InterruptedException
    {
        return fs_quests_sad_conv_condition_readyForTaskCond(player, npc) && fs_quests_sad.getNumberTasksCompleted(player) == 4;
    }
    public boolean fs_quests_sad_conv_condition_completed6TaskCond(obj_id player, obj_id npc) throws InterruptedException
    {
        return fs_quests_sad_conv_condition_readyForTaskCond(player, npc) && fs_quests_sad.getNumberTasksCompleted(player) == 5;
    }
    public boolean fs_quests_sad_conv_condition_completed7TaskCond(obj_id player, obj_id npc) throws InterruptedException
    {
        return fs_quests_sad_conv_condition_readyForTaskCond(player, npc) && fs_quests_sad.getNumberTasksCompleted(player) == 6;
    }
    public boolean fs_quests_sad_conv_condition_needsAnotherGeigerCond(obj_id player, obj_id npc) throws InterruptedException
    {
        return !hasObjVar(player, geiger.OBJVAR_GEIGER_OBJECT) && fs_quests_sad_conv_condition_hasTaskCond(player, npc);
    }
    public boolean fs_quests_sad_conv_condition_hasOtherPhaseQuestCond(obj_id player, obj_id npc) throws InterruptedException
    {
        return !quests.isActive("fs_quests_sad_task1", player) && !quests.isComplete("fs_quests_sad_task1", player) && fs_quests.hasQuestAccepted(player);
    }
    public boolean fs_quests_sad_conv_condition_completedOtherPhaseQuestCond(obj_id player, obj_id npc) throws InterruptedException
    {
        return fs_quests.hasQuestCompleted(player);
    }
    public void fs_quests_sad_conv_action_grantTask(obj_id player, obj_id npc) throws InterruptedException
    {
        fs_quests.setQuestAccepted(player);
        if (!quests.isComplete("fs_quests_sad_tasks", player) && !quests.isActive("fs_quests_sad_tasks", player))
        {
            LOG("newquests", "fs_quests_sad_conv - assigning beginning quest");
            quests.activate("fs_quests_sad_tasks", player, npc);
            attachScript(player, "systems.fs_quest.fs_quests_sad.cleanup");
        }
        else 
        {
            for (int i = 1; i <= 8; i++)
            {
                if (quests.isActive("fs_quests_sad_return" + i, player))
                {
                    LOG("newquests", "fs_quests_sad_conv - fs_quests_sad_return" + i + " being sent signal");
                    dictionary params = new dictionary();
                    params.put("questName", "fs_quests_sad_return" + i);
                    messageTo(player, "OnFinishQuestSignal", params, 0, false);
                    if (i == 8)
                    {
                        fs_quests_sad.performEndOfQuest(player);
                    }
                }
            }
        }
        return;
    }
    public void fs_quests_sad_conv_action_giveGeiger(obj_id player, obj_id npc) throws InterruptedException
    {
        geiger.resetGeiger(player);
    }
    public void fs_quests_sad_conv_action_clearWaypoint(obj_id player, obj_id npc) throws InterruptedException
    {
        String questName = "fs_quests_sad_return1";
        for (int i = 1; i <= 8; i++)
        {
            if (quests.isActive("fs_quests_sad_return" + i, player))
            {
                questName = "fs_quests_sad_return" + i;
            }
        }
        String waypointObjVar = "quest." + questName + ".waypoint";
        if (hasObjVar(player, waypointObjVar))
        {
            obj_id point = getObjIdObjVar(player, waypointObjVar);
            destroyWaypointInDatapad(point, player);
            removeObjVar(player, waypointObjVar);
        }
        return;
    }
    public String fs_quests_sad_conv_tokenTO_greetString(obj_id player, obj_id npc) throws InterruptedException
    {
        return getFirstName(player);
    }
    public float fs_quests_sad_conv_tokenDF_tokenDF0001(obj_id player, obj_id npc) throws InterruptedException
    {
        return 0.f;
    }
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        if ((!isMob(self)) || (isPlayer(self)))
        {
            detachScript(self, "npc.conversation.fs_quests_sad_conv");
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
        detachScript(self, "npc.conversation.fs_quests_sad_conv");
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
        if (ai_lib.isInCombat(self) || ai_lib.isInCombat(player))
        {
            return SCRIPT_OVERRIDE;
        }
        if (!fs_quests_sad_conv_condition_isVillageEligibleCond(player, self))
        {
            string_id message = new string_id(c_stringFile, "s_a4ab4cd7");
            chat.chat(self, message);
            return SCRIPT_CONTINUE;
        }
        if (fs_quests_sad_conv_condition_completedOtherPhaseQuestCond(player, self))
        {
            string_id message = new string_id(c_stringFile, "s_ad5c1b56");
            chat.chat(self, message);
            return SCRIPT_CONTINUE;
        }
        if (fs_quests_sad_conv_condition_hasOtherPhaseQuestCond(player, self))
        {
            string_id message = new string_id(c_stringFile, "s_be2a8162");
            chat.chat(self, message);
            return SCRIPT_CONTINUE;
        }
        if (fs_quests_sad_conv_condition_hasCompletedAllTasksCond(player, self))
        {
            doAnimationAction(self, "bow");
            doAnimationAction(player, "accept_affection");
            fs_quests_sad_conv_action_clearWaypoint(player, self);
            string_id message = new string_id(c_stringFile, "s_ce50d249");
            chat.chat(self, message);
            return SCRIPT_CONTINUE;
        }
        if (fs_quests_sad_conv_condition_hasFullInventoryCond(player, self))
        {
            string_id message = new string_id(c_stringFile, "s_e874c8c5");
            chat.chat(self, message);
            return SCRIPT_CONTINUE;
        }
        if (fs_quests_sad_conv_condition_readyForRewardCond(player, self))
        {
            doAnimationAction(self, "bow3");
            fs_quests_sad_conv_action_grantTask(player, self);
            string_id message = new string_id(c_stringFile, "s_21cb22af");
            chat.chat(self, message);
            return SCRIPT_CONTINUE;
        }
        if (fs_quests_sad_conv_condition_hasExceededTaskLimitCond(player, self))
        {
            doAnimationAction(self, "shake_head_no");
            doAnimationAction(player, "rub_chin_thoughtful");
            string_id message = new string_id(c_stringFile, "s_45e3060a");
            chat.chat(self, message);
            return SCRIPT_CONTINUE;
        }
        if (fs_quests_sad_conv_condition_needsAnotherGeigerCond(player, self))
        {
            fs_quests_sad_conv_action_giveGeiger(player, self);
            string_id message = new string_id(c_stringFile, "s_86aa30ad");
            chat.chat(self, message);
            return SCRIPT_CONTINUE;
        }
        if (fs_quests_sad_conv_condition_hasTaskCond(player, self))
        {
            doAnimationAction(self, "dismiss");
            doAnimationAction(player, "salute1");
            string_id message = new string_id(c_stringFile, "s_8903f2e3");
            chat.chat(self, message);
            return SCRIPT_CONTINUE;
        }
        if (fs_quests_sad_conv_condition_hasTheaterAssignedCond(player, self))
        {
            doAnimationAction(self, "shake_head_no");
            string_id message = new string_id(c_stringFile, "s_23720024");
            chat.chat(self, message);
            return SCRIPT_CONTINUE;
        }
        if (fs_quests_sad_conv_condition_hasNotCompletedAnyTasksCond(player, self))
        {
            doAnimationAction(self, "greet");
            string_id message = new string_id(c_stringFile, "s_b3f0b662");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (fs_quests_sad_conv_condition__defaultCondition(player, self))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            boolean hasResponse1 = false;
            if (fs_quests_sad_conv_condition__defaultCondition(player, self))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_b84b366c");
                }
                if (hasResponse1)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_e1175f92");
                }
                setObjVar(player, "conversation.fs_quests_sad_conv.branchId", 11);
                npcStartConversation(player, self, "fs_quests_sad_conv", message, responses);
            }
            else 
            {
                chat.chat(self, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (fs_quests_sad_conv_condition_completed1TaskCond(player, self))
        {
            fs_quests_sad_conv_action_clearWaypoint(player, self);
            string_id message = new string_id(c_stringFile, "s_511b214d");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (fs_quests_sad_conv_condition__defaultCondition(player, self))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            boolean hasResponse1 = false;
            if (fs_quests_sad_conv_condition__defaultCondition(player, self))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_1adbadc4");
                }
                if (hasResponse1)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_45d6d31e");
                }
                setObjVar(player, "conversation.fs_quests_sad_conv.branchId", 17);
                npcStartConversation(player, self, "fs_quests_sad_conv", message, responses);
            }
            else 
            {
                chat.chat(self, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (fs_quests_sad_conv_condition_completed2TaskCond(player, self))
        {
            fs_quests_sad_conv_action_clearWaypoint(player, self);
            string_id message = new string_id(c_stringFile, "s_ceb13feb");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (fs_quests_sad_conv_condition__defaultCondition(player, self))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            boolean hasResponse1 = false;
            if (fs_quests_sad_conv_condition__defaultCondition(player, self))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_1adbadc4");
                }
                if (hasResponse1)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_45d6d31e");
                }
                setObjVar(player, "conversation.fs_quests_sad_conv.branchId", 17);
                npcStartConversation(player, self, "fs_quests_sad_conv", message, responses);
            }
            else 
            {
                chat.chat(self, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (fs_quests_sad_conv_condition_completed3TaskCond(player, self))
        {
            fs_quests_sad_conv_action_clearWaypoint(player, self);
            string_id message = new string_id(c_stringFile, "s_e549bc85");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (fs_quests_sad_conv_condition__defaultCondition(player, self))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            boolean hasResponse1 = false;
            if (fs_quests_sad_conv_condition__defaultCondition(player, self))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_1adbadc4");
                }
                if (hasResponse1)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_45d6d31e");
                }
                setObjVar(player, "conversation.fs_quests_sad_conv.branchId", 17);
                npcStartConversation(player, self, "fs_quests_sad_conv", message, responses);
            }
            else 
            {
                chat.chat(self, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (fs_quests_sad_conv_condition_completed4TaskCond(player, self))
        {
            fs_quests_sad_conv_action_clearWaypoint(player, self);
            string_id message = new string_id(c_stringFile, "s_ae82a454");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (fs_quests_sad_conv_condition__defaultCondition(player, self))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            boolean hasResponse1 = false;
            if (fs_quests_sad_conv_condition__defaultCondition(player, self))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_1adbadc4");
                }
                if (hasResponse1)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_45d6d31e");
                }
                setObjVar(player, "conversation.fs_quests_sad_conv.branchId", 17);
                npcStartConversation(player, self, "fs_quests_sad_conv", message, responses);
            }
            else 
            {
                chat.chat(self, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (fs_quests_sad_conv_condition_completed5TaskCond(player, self))
        {
            fs_quests_sad_conv_action_clearWaypoint(player, self);
            string_id message = new string_id(c_stringFile, "s_b1cf9ec4");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (fs_quests_sad_conv_condition__defaultCondition(player, self))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            boolean hasResponse1 = false;
            if (fs_quests_sad_conv_condition__defaultCondition(player, self))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_1adbadc4");
                }
                if (hasResponse1)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_45d6d31e");
                }
                setObjVar(player, "conversation.fs_quests_sad_conv.branchId", 17);
                npcStartConversation(player, self, "fs_quests_sad_conv", message, responses);
            }
            else 
            {
                chat.chat(self, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (fs_quests_sad_conv_condition_completed6TaskCond(player, self))
        {
            fs_quests_sad_conv_action_clearWaypoint(player, self);
            string_id message = new string_id(c_stringFile, "s_ace5dd74");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (fs_quests_sad_conv_condition__defaultCondition(player, self))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            boolean hasResponse1 = false;
            if (fs_quests_sad_conv_condition__defaultCondition(player, self))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_1adbadc4");
                }
                if (hasResponse1)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_45d6d31e");
                }
                setObjVar(player, "conversation.fs_quests_sad_conv.branchId", 17);
                npcStartConversation(player, self, "fs_quests_sad_conv", message, responses);
            }
            else 
            {
                chat.chat(self, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (fs_quests_sad_conv_condition_completed7TaskCond(player, self))
        {
            fs_quests_sad_conv_action_clearWaypoint(player, self);
            string_id message = new string_id(c_stringFile, "s_744e165e");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (fs_quests_sad_conv_condition__defaultCondition(player, self))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            boolean hasResponse1 = false;
            if (fs_quests_sad_conv_condition__defaultCondition(player, self))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_1adbadc4");
                }
                if (hasResponse1)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_45d6d31e");
                }
                setObjVar(player, "conversation.fs_quests_sad_conv.branchId", 17);
                npcStartConversation(player, self, "fs_quests_sad_conv", message, responses);
            }
            else 
            {
                chat.chat(self, message);
            }
            return SCRIPT_CONTINUE;
        }
        chat.chat(self, "Error:  All conditions for OnStartNpcConversation were false.");
        return SCRIPT_CONTINUE;
    }
    public int OnNpcConversationResponse(obj_id self, String conversationId, obj_id player, string_id response) throws InterruptedException
    {
        if (!conversationId.equals("fs_quests_sad_conv"))
        {
            return SCRIPT_CONTINUE;
        }
        int branchId = getIntObjVar(player, "conversation.fs_quests_sad_conv.branchId");
        if (branchId == 11 && response.equals("s_b84b366c"))
        {
            if (fs_quests_sad_conv_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_bc39cbe0");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (fs_quests_sad_conv_condition__defaultCondition(player, self))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_52917b0d");
                    }
                    setObjVar(player, "conversation.fs_quests_sad_conv.branchId", 12);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    removeObjVar(player, "conversation.fs_quests_sad_conv.branchId");
                    npcSpeak(player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'Greetings.  I am eager for your assistance.  In return, I offer to train you to use the Force to quicken you in melee.' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 11 && response.equals("s_e1175f92"))
        {
            if (fs_quests_sad_conv_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_253d75eb");
                removeObjVar(player, "conversation.fs_quests_sad_conv.branchId");
                npcSpeak(player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'Greetings.  I am eager for your assistance.  In return, I offer to train you to use the Force to quicken you in melee.' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 12 && response.equals("s_52917b0d"))
        {
            if (fs_quests_sad_conv_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_14c652fd");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (fs_quests_sad_conv_condition__defaultCondition(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (fs_quests_sad_conv_condition__defaultCondition(player, self))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_faedbf19");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_8909d6f9");
                    }
                    setObjVar(player, "conversation.fs_quests_sad_conv.branchId", 13);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    removeObjVar(player, "conversation.fs_quests_sad_conv.branchId");
                    npcSpeak(player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'A variety of Shadow bases have been spotted in the surrounding countryside.  We suspect they are marshalling their forces for some sort of large-scale raid unlike anything we've seen.  If they were to strike now, we would be horribly unprepared.  We must convince them that we are much stronger than we, in fact, are.  Village agents are scouring the countryside as we speak, looking for likely targets for dynamic raids.  We can use your help.' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 13 && response.equals("s_faedbf19"))
        {
            if (fs_quests_sad_conv_condition__defaultCondition(player, self))
            {
                fs_quests_sad_conv_action_grantTask(player, self);
                string_id message = new string_id(c_stringFile, "s_f163f1df");
                removeObjVar(player, "conversation.fs_quests_sad_conv.branchId");
                npcSpeak(player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'Now, I want to make something clear.  This mission is going to carry you out into the field as an operative for our militia.  It's going to be impossible to do anything else for our village for quite a while - possibly as much as 3 weeks.  You can back out now, but once I give you our intelligence data, you're committed.' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 13 && response.equals("s_8909d6f9"))
        {
            if (fs_quests_sad_conv_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_253d75eb");
                removeObjVar(player, "conversation.fs_quests_sad_conv.branchId");
                npcSpeak(player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'Now, I want to make something clear.  This mission is going to carry you out into the field as an operative for our militia.  It's going to be impossible to do anything else for our village for quite a while - possibly as much as 3 weeks.  You can back out now, but once I give you our intelligence data, you're committed.' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 17 && response.equals("s_1adbadc4"))
        {
            doAnimationAction(player, "nod_head_once");
            if (fs_quests_sad_conv_condition__defaultCondition(player, self))
            {
                doAnimationAction(self, "bow2");
                doAnimationAction(player, "bow2");
                fs_quests_sad_conv_action_grantTask(player, self);
                string_id message = new string_id(c_stringFile, "s_71c49afd");
                removeObjVar(player, "conversation.fs_quests_sad_conv.branchId");
                npcSpeak(player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'You did us a great service with your last raid on the Shadow, but there are more out there.  Can you help us again?' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 17 && response.equals("s_45d6d31e"))
        {
            if (fs_quests_sad_conv_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_7b05a163");
                removeObjVar(player, "conversation.fs_quests_sad_conv.branchId");
                npcSpeak(player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'You did us a great service with your last raid on the Shadow, but there are more out there.  Can you help us again?' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 20 && response.equals("s_1adbadc4"))
        {
            doAnimationAction(player, "nod_head_once");
            if (fs_quests_sad_conv_condition__defaultCondition(player, self))
            {
                doAnimationAction(self, "bow2");
                doAnimationAction(player, "bow2");
                fs_quests_sad_conv_action_grantTask(player, self);
                string_id message = new string_id(c_stringFile, "s_71c49afd");
                removeObjVar(player, "conversation.fs_quests_sad_conv.branchId");
                npcSpeak(player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'I think your attacks are beginning to get their attention.  I'd watch your back.  Here's your next target, if you're up for it...' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 20 && response.equals("s_45d6d31e"))
        {
            if (fs_quests_sad_conv_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_7b05a163");
                removeObjVar(player, "conversation.fs_quests_sad_conv.branchId");
                npcSpeak(player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'I think your attacks are beginning to get their attention.  I'd watch your back.  Here's your next target, if you're up for it...' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 21 && response.equals("s_1adbadc4"))
        {
            doAnimationAction(player, "nod_head_once");
            if (fs_quests_sad_conv_condition__defaultCondition(player, self))
            {
                doAnimationAction(self, "bow2");
                doAnimationAction(player, "bow2");
                fs_quests_sad_conv_action_grantTask(player, self);
                string_id message = new string_id(c_stringFile, "s_71c49afd");
                removeObjVar(player, "conversation.fs_quests_sad_conv.branchId");
                npcSpeak(player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'They've started moving their bases around in response to our attacks.  We've tracked them into a new area that we'd like to investigate.' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 21 && response.equals("s_45d6d31e"))
        {
            if (fs_quests_sad_conv_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_7b05a163");
                removeObjVar(player, "conversation.fs_quests_sad_conv.branchId");
                npcSpeak(player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'They've started moving their bases around in response to our attacks.  We've tracked them into a new area that we'd like to investigate.' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 22 && response.equals("s_1adbadc4"))
        {
            doAnimationAction(player, "nod_head_once");
            if (fs_quests_sad_conv_condition__defaultCondition(player, self))
            {
                doAnimationAction(self, "bow2");
                doAnimationAction(player, "bow2");
                fs_quests_sad_conv_action_grantTask(player, self);
                string_id message = new string_id(c_stringFile, "s_71c49afd");
                removeObjVar(player, "conversation.fs_quests_sad_conv.branchId");
                npcSpeak(player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'Unfortunately, one of our field soldiers was caught and killed.  But he managed to get us a message with the coordinates of an enemy base.  Can you avenge him for us?' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 22 && response.equals("s_45d6d31e"))
        {
            if (fs_quests_sad_conv_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_7b05a163");
                removeObjVar(player, "conversation.fs_quests_sad_conv.branchId");
                npcSpeak(player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'Unfortunately, one of our field soldiers was caught and killed.  But he managed to get us a message with the coordinates of an enemy base.  Can you avenge him for us?' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 23 && response.equals("s_1adbadc4"))
        {
            doAnimationAction(player, "nod_head_once");
            if (fs_quests_sad_conv_condition__defaultCondition(player, self))
            {
                doAnimationAction(self, "bow2");
                doAnimationAction(player, "bow2");
                fs_quests_sad_conv_action_grantTask(player, self);
                string_id message = new string_id(c_stringFile, "s_71c49afd");
                removeObjVar(player, "conversation.fs_quests_sad_conv.branchId");
                npcSpeak(player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'We may have turned the corner on this thing.  It feels great to finally take the battle to our enemy instead of waiting for them to attack.  Ready for another one?' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 23 && response.equals("s_45d6d31e"))
        {
            if (fs_quests_sad_conv_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_7b05a163");
                removeObjVar(player, "conversation.fs_quests_sad_conv.branchId");
                npcSpeak(player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'We may have turned the corner on this thing.  It feels great to finally take the battle to our enemy instead of waiting for them to attack.  Ready for another one?' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 24 && response.equals("s_1adbadc4"))
        {
            doAnimationAction(player, "nod_head_once");
            if (fs_quests_sad_conv_condition__defaultCondition(player, self))
            {
                doAnimationAction(self, "bow2");
                doAnimationAction(player, "bow2");
                fs_quests_sad_conv_action_grantTask(player, self);
                string_id message = new string_id(c_stringFile, "s_71c49afd");
                removeObjVar(player, "conversation.fs_quests_sad_conv.branchId");
                npcSpeak(player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'All of the reports coming in are promising -- we definitely have them on the run now.  I think we're getting to their more elite troops -- please be careful.' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 24 && response.equals("s_45d6d31e"))
        {
            if (fs_quests_sad_conv_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_7b05a163");
                removeObjVar(player, "conversation.fs_quests_sad_conv.branchId");
                npcSpeak(player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'All of the reports coming in are promising -- we definitely have them on the run now.  I think we're getting to their more elite troops -- please be careful.' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 25 && response.equals("s_1adbadc4"))
        {
            doAnimationAction(player, "nod_head_once");
            if (fs_quests_sad_conv_condition__defaultCondition(player, self))
            {
                doAnimationAction(self, "bow2");
                doAnimationAction(player, "bow2");
                fs_quests_sad_conv_action_grantTask(player, self);
                string_id message = new string_id(c_stringFile, "s_71c49afd");
                removeObjVar(player, "conversation.fs_quests_sad_conv.branchId");
                npcSpeak(player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'Exciting news!  We've finally tracked down one of their high-ranking officers.  Our efforts have flushed him out, and our agent caught a glimpse of him just a while back.  I think...well, better not say anything.  Be careful -- a cornered rat fights hard.' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 25 && response.equals("s_45d6d31e"))
        {
            if (fs_quests_sad_conv_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_7b05a163");
                removeObjVar(player, "conversation.fs_quests_sad_conv.branchId");
                npcSpeak(player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'Exciting news!  We've finally tracked down one of their high-ranking officers.  Our efforts have flushed him out, and our agent caught a glimpse of him just a while back.  I think...well, better not say anything.  Be careful -- a cornered rat fights hard.' were false.");
            return SCRIPT_CONTINUE;
        }
        chat.chat(self, "Error:  Fell through all branches and responses for OnNpcConversationResponse.");
        removeObjVar(player, "conversation.fs_quests_sad_conv.branchId");
        return SCRIPT_CONTINUE;
    }
}
