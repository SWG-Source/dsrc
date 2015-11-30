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

public class tyrena_lyman_zavala extends script.base_script
{
    public tyrena_lyman_zavala()
    {
    }
    public static String c_stringFile = "conversation/tyrena_lyman_zavala";
    public boolean tyrena_lyman_zavala_condition__defaultCondition(obj_id player, obj_id npc) throws InterruptedException
    {
        return true;
    }
    public boolean tyrena_lyman_zavala_condition_finishedDoolQuests(obj_id player, obj_id npc) throws InterruptedException
    {
        return groundquests.hasCompletedQuest(player, "quest/coronet_captain_dool");
    }
    public boolean tyrena_lyman_zavala_condition_doingLymanQuests(obj_id player, obj_id npc) throws InterruptedException
    {
        if (groundquests.isQuestActive(player, "quest/tyrena_lyman_evidence"))
        {
            return true;
        }
        return false;
    }
    public boolean tyrena_lyman_zavala_condition_waitingForLymanFinish(obj_id player, obj_id npc) throws InterruptedException
    {
        if (groundquests.hasCompletedTask(player, "quest/tyrena_lyman_olag_greck", "returningToLyman"))
        {
            return true;
        }
        return false;
    }
    public boolean tyrena_lyman_zavala_condition_needTakouhliWaypoint(obj_id player, obj_id npc) throws InterruptedException
    {
        if (groundquests.hasCompletedQuest(player, "quest/tyrena_lyman_olag_greck") && !groundquests.isQuestActiveOrComplete(player, "quest/tyrena_meet_the_meatlumps"))
        {
            return true;
        }
        return false;
    }
    public boolean tyrena_lyman_zavala_condition_startedTakouhi(obj_id player, obj_id npc) throws InterruptedException
    {
        return groundquests.isQuestActiveOrComplete(player, "quest/tyrena_meet_the_meatlumps");
    }
    public boolean tyrena_lyman_zavala_condition_doingOlagQuest(obj_id player, obj_id npc) throws InterruptedException
    {
        if (groundquests.isQuestActive(player, "quest/tyrena_lyman_olag_greck"))
        {
            return true;
        }
        return false;
    }
    public boolean tyrena_lyman_zavala_condition_finishingOlagQuest(obj_id player, obj_id npc) throws InterruptedException
    {
        if (groundquests.isTaskActive(player, "quest/tyrena_lyman_olag_greck", "returningToLyman"))
        {
            return true;
        }
        return false;
    }
    public void tyrena_lyman_zavala_action_grantLymanQuest(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.grantQuest(player, "quest/tyrena_lyman_evidence");
    }
    public void tyrena_lyman_zavala_action_grantGoToTakouhi(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.grantQuest(player, "quest/tyrena_goto_takouhi");
    }
    public void tyrena_lyman_zavala_action_completeLymanQuests(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.sendSignal(player, "finishLymanQuest");
    }
    public void tyrena_lyman_zavala_action_sendDoolGotoSignal(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.sendSignal(player, "cameFromDool");
    }
    public void tyrena_lyman_zavala_action_sendOlagDeadSignal(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.sendSignal(player, "olagIsDead");
    }
    public void tyrena_lyman_zavala_action_grantKillOlagQuest(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.grantQuest(player, "quest/tyrena_lyman_olag_greck");
    }
    public int tyrena_lyman_zavala_handleBranch2(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_11"))
        {
            if (tyrena_lyman_zavala_condition__defaultCondition(player, npc))
            {
                tyrena_lyman_zavala_action_grantGoToTakouhi(player, npc);
                string_id message = new string_id(c_stringFile, "s_12");
                utils.removeScriptVar(player, "conversation.tyrena_lyman_zavala.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int tyrena_lyman_zavala_handleBranch4(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_29"))
        {
            if (tyrena_lyman_zavala_condition__defaultCondition(player, npc))
            {
                tyrena_lyman_zavala_action_grantGoToTakouhi(player, npc);
                string_id message = new string_id(c_stringFile, "s_30");
                utils.removeScriptVar(player, "conversation.tyrena_lyman_zavala.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int tyrena_lyman_zavala_handleBranch7(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_14"))
        {
            if (tyrena_lyman_zavala_condition__defaultCondition(player, npc))
            {
                tyrena_lyman_zavala_action_grantKillOlagQuest(player, npc);
                string_id message = new string_id(c_stringFile, "s_16");
                utils.removeScriptVar(player, "conversation.tyrena_lyman_zavala.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int tyrena_lyman_zavala_handleBranch10(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_22"))
        {
            if (tyrena_lyman_zavala_condition__defaultCondition(player, npc))
            {
                tyrena_lyman_zavala_action_grantLymanQuest(player, npc);
                string_id message = new string_id(c_stringFile, "s_24");
                utils.removeScriptVar(player, "conversation.tyrena_lyman_zavala.branchId");
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
            detachScript(self, "conversation.tyrena_lyman_zavala");
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
        detachScript(self, "conversation.tyrena_lyman_zavala");
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
        if (tyrena_lyman_zavala_condition_startedTakouhi(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_4");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        if (tyrena_lyman_zavala_condition_needTakouhliWaypoint(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_6");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (tyrena_lyman_zavala_condition__defaultCondition(player, npc))
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
                utils.setScriptVar(player, "conversation.tyrena_lyman_zavala.branchId", 2);
                npcStartConversation(player, npc, "tyrena_lyman_zavala", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (tyrena_lyman_zavala_condition_finishingOlagQuest(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_27");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (tyrena_lyman_zavala_condition__defaultCondition(player, npc))
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
                utils.setScriptVar(player, "conversation.tyrena_lyman_zavala.branchId", 4);
                npcStartConversation(player, npc, "tyrena_lyman_zavala", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (tyrena_lyman_zavala_condition_doingOlagQuest(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_28");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        if (tyrena_lyman_zavala_condition_waitingForLymanFinish(player, npc))
        {
            tyrena_lyman_zavala_action_completeLymanQuests(player, npc);
            string_id message = new string_id(c_stringFile, "s_10");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (tyrena_lyman_zavala_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_14");
                }
                utils.setScriptVar(player, "conversation.tyrena_lyman_zavala.branchId", 7);
                npcStartConversation(player, npc, "tyrena_lyman_zavala", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (tyrena_lyman_zavala_condition_doingLymanQuests(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_18");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        if (tyrena_lyman_zavala_condition_finishedDoolQuests(player, npc))
        {
            tyrena_lyman_zavala_action_sendDoolGotoSignal(player, npc);
            string_id message = new string_id(c_stringFile, "s_20");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (tyrena_lyman_zavala_condition__defaultCondition(player, npc))
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
                utils.setScriptVar(player, "conversation.tyrena_lyman_zavala.branchId", 10);
                npcStartConversation(player, npc, "tyrena_lyman_zavala", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (tyrena_lyman_zavala_condition__defaultCondition(player, npc))
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
        if (!conversationId.equals("tyrena_lyman_zavala"))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id npc = self;
        int branchId = utils.getIntScriptVar(player, "conversation.tyrena_lyman_zavala.branchId");
        if (branchId == 2 && tyrena_lyman_zavala_handleBranch2(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 4 && tyrena_lyman_zavala_handleBranch4(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 7 && tyrena_lyman_zavala_handleBranch7(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 10 && tyrena_lyman_zavala_handleBranch10(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        chat.chat(npc, "Error:  Fell through all branches and responses for OnNpcConversationResponse.");
        utils.removeScriptVar(player, "conversation.tyrena_lyman_zavala.branchId");
        return SCRIPT_CONTINUE;
    }
}
