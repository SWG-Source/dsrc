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

public class corellia_coronet_daclif_gallamby extends script.base_script
{
    public corellia_coronet_daclif_gallamby()
    {
    }
    public static String c_stringFile = "conversation/corellia_coronet_daclif_gallamby";
    public boolean corellia_coronet_daclif_gallamby_condition__defaultCondition(obj_id player, obj_id npc) throws InterruptedException
    {
        return true;
    }
    public boolean corellia_coronet_daclif_gallamby_condition_presentSearchWrits(obj_id player, obj_id npc) throws InterruptedException
    {
        return groundquests.isTaskActive(player, "corellia_coronet_capitol_problems_diktat_search", "diktat_search_1");
    }
    public boolean corellia_coronet_daclif_gallamby_condition_diktatSearchActive(obj_id player, obj_id npc) throws InterruptedException
    {
        return groundquests.isTaskActive(player, "corellia_coronet_capitol_problems_diktat_search", "diktat_search_2") || groundquests.isTaskActive(player, "corellia_coronet_capitol_problems_diktat_search", "diktat_search_3") || groundquests.isTaskActive(player, "corellia_coronet_capitol_problems_diktat_search", "diktat_search_4");
    }
    public boolean corellia_coronet_daclif_gallamby_condition_diktatSearchComplete(obj_id player, obj_id npc) throws InterruptedException
    {
        return groundquests.isTaskActive(player, "corellia_coronet_capitol_problems_diktat_search", "diktat_search_5");
    }
    public boolean corellia_coronet_daclif_gallamby_condition_thankfulDiktatActive(obj_id player, obj_id npc) throws InterruptedException
    {
        return groundquests.isTaskActive(player, "corellia_coronet_capitol_problems_thankful_diktat", "thankful_diktat_1");
    }
    public boolean corellia_coronet_daclif_gallamby_condition_thankfulDiktatComplete(obj_id player, obj_id npc) throws InterruptedException
    {
        return groundquests.isTaskActive(player, "corellia_coronet_capitol_problems_thankful_diktat", "thankful_diktat_2") || groundquests.hasCompletedQuest(player, "corellia_coronet_capitol_problems_thankful_diktat");
    }
    public void corellia_coronet_daclif_gallamby_action_diktatSearchBegin(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.sendSignal(player, "diktat_search_1");
    }
    public void corellia_coronet_daclif_gallamby_action_thankfulDiktatReward(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.sendSignal(player, "thankful_diktat_1");
    }
    public void corellia_coronet_daclif_gallamby_action_diktatSearchEnd(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.sendSignal(player, "diktat_search_5");
    }
    public int corellia_coronet_daclif_gallamby_handleBranch2(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_23"))
        {
            if (corellia_coronet_daclif_gallamby_condition__defaultCondition(player, npc))
            {
                corellia_coronet_daclif_gallamby_action_thankfulDiktatReward(player, npc);
                string_id message = new string_id(c_stringFile, "s_24");
                utils.removeScriptVar(player, "conversation.corellia_coronet_daclif_gallamby.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int corellia_coronet_daclif_gallamby_handleBranch4(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_19"))
        {
            if (corellia_coronet_daclif_gallamby_condition__defaultCondition(player, npc))
            {
                corellia_coronet_daclif_gallamby_action_diktatSearchEnd(player, npc);
                string_id message = new string_id(c_stringFile, "s_20");
                utils.removeScriptVar(player, "conversation.corellia_coronet_daclif_gallamby.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int corellia_coronet_daclif_gallamby_handleBranch7(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_18"))
        {
            if (corellia_coronet_daclif_gallamby_condition__defaultCondition(player, npc))
            {
                corellia_coronet_daclif_gallamby_action_diktatSearchBegin(player, npc);
                string_id message = new string_id(c_stringFile, "s_22");
                utils.removeScriptVar(player, "conversation.corellia_coronet_daclif_gallamby.branchId");
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
            detachScript(self, "conversation.corellia_coronet_daclif_gallamby");
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
        detachScript(self, "conversation.corellia_coronet_daclif_gallamby");
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
        if (corellia_coronet_daclif_gallamby_condition_thankfulDiktatComplete(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_4");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        if (corellia_coronet_daclif_gallamby_condition_thankfulDiktatActive(player, npc))
        {
            doAnimationAction(npc, "thank");
            string_id message = new string_id(c_stringFile, "s_10");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (corellia_coronet_daclif_gallamby_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_23");
                }
                utils.setScriptVar(player, "conversation.corellia_coronet_daclif_gallamby.branchId", 2);
                npcStartConversation(player, npc, "corellia_coronet_daclif_gallamby", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (corellia_coronet_daclif_gallamby_condition_diktatSearchComplete(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_9");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (corellia_coronet_daclif_gallamby_condition__defaultCondition(player, npc))
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
                utils.setScriptVar(player, "conversation.corellia_coronet_daclif_gallamby.branchId", 4);
                npcStartConversation(player, npc, "corellia_coronet_daclif_gallamby", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (corellia_coronet_daclif_gallamby_condition_diktatSearchActive(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_14");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        if (corellia_coronet_daclif_gallamby_condition_presentSearchWrits(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_16");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (corellia_coronet_daclif_gallamby_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_18");
                }
                utils.setScriptVar(player, "conversation.corellia_coronet_daclif_gallamby.branchId", 7);
                npcStartConversation(player, npc, "corellia_coronet_daclif_gallamby", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (corellia_coronet_daclif_gallamby_condition__defaultCondition(player, npc))
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
        if (!conversationId.equals("corellia_coronet_daclif_gallamby"))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id npc = self;
        int branchId = utils.getIntScriptVar(player, "conversation.corellia_coronet_daclif_gallamby.branchId");
        if (branchId == 2 && corellia_coronet_daclif_gallamby_handleBranch2(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 4 && corellia_coronet_daclif_gallamby_handleBranch4(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 7 && corellia_coronet_daclif_gallamby_handleBranch7(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        chat.chat(npc, "Error:  Fell through all branches and responses for OnNpcConversationResponse.");
        utils.removeScriptVar(player, "conversation.corellia_coronet_daclif_gallamby.branchId");
        return SCRIPT_CONTINUE;
    }
}
