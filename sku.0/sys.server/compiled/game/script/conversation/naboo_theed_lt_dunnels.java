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
import script.library.space_quest;
import script.library.utils;

public class naboo_theed_lt_dunnels extends script.base_script
{
    public naboo_theed_lt_dunnels()
    {
    }
    public static String c_stringFile = "conversation/naboo_theed_lt_dunnels";
    public boolean naboo_theed_lt_dunnels_condition__defaultCondition(obj_id player, obj_id npc) throws InterruptedException
    {
        return true;
    }
    public boolean naboo_theed_lt_dunnels_condition_startBoosterQuest(obj_id player, obj_id npc) throws InterruptedException
    {
        return groundquests.isQuestActive(player, "naboo_theed_goto_bragg") || groundquests.hasCompletedQuest(player, "naboo_theed_terrorist_responsible");
    }
    public boolean naboo_theed_lt_dunnels_condition_onBoosterQuest(obj_id player, obj_id npc) throws InterruptedException
    {
        return groundquests.isQuestActive(player, "naboo_theed_terrorist_booster");
    }
    public boolean naboo_theed_lt_dunnels_condition_completedBoosterQuest(obj_id player, obj_id npc) throws InterruptedException
    {
        return groundquests.hasCompletedQuest(player, "naboo_theed_terrorist_booster");
    }
    public boolean naboo_theed_lt_dunnels_condition_lastStepBoosterQuest(obj_id player, obj_id npc) throws InterruptedException
    {
        return groundquests.isTaskActive(player, "naboo_theed_terrorist_booster", "theed_terrorist_booster_08");
    }
    public boolean naboo_theed_lt_dunnels_condition_needsTerrorizeQuest(obj_id player, obj_id npc) throws InterruptedException
    {
        return !groundquests.isQuestActive(player, "naboo_theed_goto_brunser") && !groundquests.isQuestActive(player, "naboo_theed_terrorist_terrorize") && !groundquests.hasCompletedQuest(player, "naboo_theed_terrorist_terrorize");
    }
    public void naboo_theed_lt_dunnels_action_grantBooster(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.requestGrantQuest(player, "naboo_theed_terrorist_booster");
    }
    public void naboo_theed_lt_dunnels_action_sendToSgtBrunser(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.requestGrantQuest(player, "naboo_theed_goto_brunser");
    }
    public void naboo_theed_lt_dunnels_action_endBoosterQuest(obj_id player, obj_id npc) throws InterruptedException
    {
        if (groundquests.isTaskActive(player, "naboo_theed_terrorist_booster", "theed_terrorist_booster_08"))
        {
            groundquests.sendSignal(player, "theed_terrorist_booster_08");
        }
        return;
    }
    public void naboo_theed_lt_dunnels_action_endGotoDunnels(obj_id player, obj_id npc) throws InterruptedException
    {
        if (groundquests.isQuestActive(player, "naboo_theed_goto_dunnels"))
        {
            groundquests.sendSignal(player, "theed_goto_dunnels");
        }
        return;
    }
    public int naboo_theed_lt_dunnels_handleBranch1(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_78"))
        {
            if (naboo_theed_lt_dunnels_condition__defaultCondition(player, npc))
            {
                naboo_theed_lt_dunnels_action_sendToSgtBrunser(player, npc);
                string_id message = new string_id(c_stringFile, "s_79");
                utils.removeScriptVar(player, "conversation.naboo_theed_lt_dunnels.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int naboo_theed_lt_dunnels_handleBranch3(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_74"))
        {
            if (naboo_theed_lt_dunnels_condition__defaultCondition(player, npc))
            {
                naboo_theed_lt_dunnels_action_sendToSgtBrunser(player, npc);
                string_id message = new string_id(c_stringFile, "s_75");
                utils.removeScriptVar(player, "conversation.naboo_theed_lt_dunnels.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int naboo_theed_lt_dunnels_handleBranch6(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_39"))
        {
            if (naboo_theed_lt_dunnels_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_40");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (naboo_theed_lt_dunnels_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_16");
                    }
                    utils.setScriptVar(player, "conversation.naboo_theed_lt_dunnels.branchId", 7);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.naboo_theed_lt_dunnels.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int naboo_theed_lt_dunnels_handleBranch7(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_16"))
        {
            if (naboo_theed_lt_dunnels_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_17");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (naboo_theed_lt_dunnels_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_19");
                    }
                    utils.setScriptVar(player, "conversation.naboo_theed_lt_dunnels.branchId", 8);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.naboo_theed_lt_dunnels.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int naboo_theed_lt_dunnels_handleBranch8(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_19"))
        {
            if (naboo_theed_lt_dunnels_condition__defaultCondition(player, npc))
            {
                naboo_theed_lt_dunnels_action_grantBooster(player, npc);
                string_id message = new string_id(c_stringFile, "s_21");
                utils.removeScriptVar(player, "conversation.naboo_theed_lt_dunnels.branchId");
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
            detachScript(self, "conversation.naboo_theed_lt_dunnels");
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
        faceTo(self, player);
        return SCRIPT_CONTINUE;
    }
    public int OnIncapacitated(obj_id self, obj_id killer) throws InterruptedException
    {
        clearCondition(self, CONDITION_CONVERSABLE);
        detachScript(self, "conversation.naboo_theed_lt_dunnels");
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
        if (naboo_theed_lt_dunnels_condition_completedBoosterQuest(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_37");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (naboo_theed_lt_dunnels_condition_needsTerrorizeQuest(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_78");
                }
                utils.setScriptVar(player, "conversation.naboo_theed_lt_dunnels.branchId", 1);
                npcStartConversation(player, npc, "naboo_theed_lt_dunnels", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (naboo_theed_lt_dunnels_condition_lastStepBoosterQuest(player, npc))
        {
            naboo_theed_lt_dunnels_action_endBoosterQuest(player, npc);
            string_id message = new string_id(c_stringFile, "s_73");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (naboo_theed_lt_dunnels_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_74");
                }
                utils.setScriptVar(player, "conversation.naboo_theed_lt_dunnels.branchId", 3);
                npcStartConversation(player, npc, "naboo_theed_lt_dunnels", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (naboo_theed_lt_dunnels_condition_onBoosterQuest(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_36");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        if (naboo_theed_lt_dunnels_condition_startBoosterQuest(player, npc))
        {
            naboo_theed_lt_dunnels_action_endGotoDunnels(player, npc);
            string_id message = new string_id(c_stringFile, "s_38");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (naboo_theed_lt_dunnels_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_39");
                }
                utils.setScriptVar(player, "conversation.naboo_theed_lt_dunnels.branchId", 6);
                npcStartConversation(player, npc, "naboo_theed_lt_dunnels", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (naboo_theed_lt_dunnels_condition__defaultCondition(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_34");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        chat.chat(npc, "Error:  All conditions for OnStartNpcConversation were false.");
        return SCRIPT_CONTINUE;
    }
    public int OnNpcConversationResponse(obj_id self, String conversationId, obj_id player, string_id response) throws InterruptedException
    {
        if (!conversationId.equals("naboo_theed_lt_dunnels"))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id npc = self;
        int branchId = utils.getIntScriptVar(player, "conversation.naboo_theed_lt_dunnels.branchId");
        if (branchId == 1 && naboo_theed_lt_dunnels_handleBranch1(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 3 && naboo_theed_lt_dunnels_handleBranch3(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 6 && naboo_theed_lt_dunnels_handleBranch6(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 7 && naboo_theed_lt_dunnels_handleBranch7(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 8 && naboo_theed_lt_dunnels_handleBranch8(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        chat.chat(npc, "Error:  Fell through all branches and responses for OnNpcConversationResponse.");
        utils.removeScriptVar(player, "conversation.naboo_theed_lt_dunnels.branchId");
        return SCRIPT_CONTINUE;
    }
}
