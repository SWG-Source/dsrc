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

public class coronet_captain_dool extends script.base_script
{
    public coronet_captain_dool()
    {
    }
    public static String c_stringFile = "conversation/coronet_captain_dool";
    public boolean coronet_captain_dool_condition__defaultCondition(obj_id player, obj_id npc) throws InterruptedException
    {
        return true;
    }
    public boolean coronet_captain_dool_condition_justCameFromSheelya(obj_id player, obj_id npc) throws InterruptedException
    {
        if (groundquests.isTaskActive(player, "quest/coronet_sheelya_commanding_officer", "goSeeDool") || groundquests.hasCompletedTask(player, "quest/coronet_sheelya_commanding_officer", "goSeeDool"))
        {
            return true;
        }
        return false;
    }
    public boolean coronet_captain_dool_condition_doingDoolsQuest(obj_id player, obj_id npc) throws InterruptedException
    {
        return groundquests.isQuestActive(player, "quest/coronet_captain_dool");
    }
    public boolean coronet_captain_dool_condition_readyToFinishDoolsQueset(obj_id player, obj_id npc) throws InterruptedException
    {
        if (groundquests.isTaskActive(player, "quest/coronet_captain_dool", "returnToCaptain") || groundquests.hasCompletedTask(player, "quest/coronet_captain_dool", "returnToCaptain"))
        {
            return true;
        }
        return false;
    }
    public boolean coronet_captain_dool_condition_giveGoToLymanZavalaAgain(obj_id player, obj_id npc) throws InterruptedException
    {
        if (groundquests.hasCompletedQuest(player, "quest/coronet_captain_dool") && !groundquests.isQuestActive(player, "quest/tyrena_goto_lyman_zavala"))
        {
            return true;
        }
        return false;
    }
    public boolean coronet_captain_dool_condition_workingForLyman(obj_id player, obj_id npc) throws InterruptedException
    {
        return groundquests.isQuestActiveOrComplete(player, "quest/tyrena_goto_lyman_zavala");
    }
    public void coronet_captain_dool_action_grantDoolQuest(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.grantQuest(player, "quest/coronet_captain_dool");
    }
    public void coronet_captain_dool_action_completeGoToDoolQuest(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.sendSignal(player, "spokeWithCO");
    }
    public void coronet_captain_dool_action_completeDoolsQuest(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.sendSignal(player, "doneWithDool");
    }
    public void coronet_captain_dool_action_giveLymanGoTo(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.grantQuest(player, "quest/tyrena_goto_lyman_zavala");
    }
    public void coronet_captain_dool_action_sendGreckMissionCompleteSignal(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.sendSignal(player, "finishedDoolsGreckMission");
    }
    public int coronet_captain_dool_handleBranch2(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_13"))
        {
            if (coronet_captain_dool_condition__defaultCondition(player, npc))
            {
                coronet_captain_dool_action_giveLymanGoTo(player, npc);
                string_id message = new string_id(c_stringFile, "s_14");
                utils.removeScriptVar(player, "conversation.coronet_captain_dool.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int coronet_captain_dool_handleBranch4(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_11"))
        {
            if (coronet_captain_dool_condition__defaultCondition(player, npc))
            {
                coronet_captain_dool_action_giveLymanGoTo(player, npc);
                string_id message = new string_id(c_stringFile, "s_16");
                utils.removeScriptVar(player, "conversation.coronet_captain_dool.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int coronet_captain_dool_handleBranch7(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_22"))
        {
            if (coronet_captain_dool_condition__defaultCondition(player, npc))
            {
                coronet_captain_dool_action_grantDoolQuest(player, npc);
                string_id message = new string_id(c_stringFile, "s_24");
                utils.removeScriptVar(player, "conversation.coronet_captain_dool.branchId");
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
            detachScript(self, "conversation.coronet_captain_dool");
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
        detachScript(self, "conversation.coronet_captain_dool");
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
        if (coronet_captain_dool_condition_workingForLyman(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_4");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        if (coronet_captain_dool_condition_giveGoToLymanZavalaAgain(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_12");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (coronet_captain_dool_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_13");
                }
                utils.setScriptVar(player, "conversation.coronet_captain_dool.branchId", 2);
                npcStartConversation(player, npc, "coronet_captain_dool", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (coronet_captain_dool_condition_readyToFinishDoolsQueset(player, npc))
        {
            coronet_captain_dool_action_sendGreckMissionCompleteSignal(player, npc);
            string_id message = new string_id(c_stringFile, "s_9");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (coronet_captain_dool_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_11");
                }
                utils.setScriptVar(player, "conversation.coronet_captain_dool.branchId", 4);
                npcStartConversation(player, npc, "coronet_captain_dool", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (coronet_captain_dool_condition_doingDoolsQuest(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_18");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        if (coronet_captain_dool_condition_justCameFromSheelya(player, npc))
        {
            coronet_captain_dool_action_completeGoToDoolQuest(player, npc);
            string_id message = new string_id(c_stringFile, "s_20");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (coronet_captain_dool_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_22");
                }
                utils.setScriptVar(player, "conversation.coronet_captain_dool.branchId", 7);
                npcStartConversation(player, npc, "coronet_captain_dool", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (coronet_captain_dool_condition__defaultCondition(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_26");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        chat.chat(npc, "Error:  All conditions for OnStartNpcConversation were false.");
        return SCRIPT_CONTINUE;
    }
    public int OnNpcConversationResponse(obj_id self, String conversationId, obj_id player, string_id response) throws InterruptedException
    {
        if (!conversationId.equals("coronet_captain_dool"))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id npc = self;
        int branchId = utils.getIntScriptVar(player, "conversation.coronet_captain_dool.branchId");
        if (branchId == 2 && coronet_captain_dool_handleBranch2(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 4 && coronet_captain_dool_handleBranch4(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 7 && coronet_captain_dool_handleBranch7(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        chat.chat(npc, "Error:  Fell through all branches and responses for OnNpcConversationResponse.");
        utils.removeScriptVar(player, "conversation.coronet_captain_dool.branchId");
        return SCRIPT_CONTINUE;
    }
}
