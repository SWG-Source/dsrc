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
import script.library.space_utils;
import script.library.utils;

public class corellia_coronet_demmi_wot extends script.base_script
{
    public corellia_coronet_demmi_wot()
    {
    }
    public static String c_stringFile = "conversation/corellia_coronet_demmi_wot";
    public boolean corellia_coronet_demmi_wot_condition__defaultCondition(obj_id player, obj_id npc) throws InterruptedException
    {
        return true;
    }
    public boolean corellia_coronet_demmi_wot_condition_starportVandalsBegin(obj_id player, obj_id npc) throws InterruptedException
    {
        return groundquests.isTaskActive(player, "corellia_coronet_capitol_problems_starport_vandals_2", "starport_vandals_5");
    }
    public boolean corellia_coronet_demmi_wot_condition_alreadyHasSpaceMission(obj_id player, obj_id npc) throws InterruptedException
    {
        return space_quest.hasQuest(player);
    }
    public boolean corellia_coronet_demmi_wot_condition_onFirstRagtagSquad(obj_id player, obj_id npc) throws InterruptedException
    {
        return space_quest.hasQuest(player, "assassinate", "corellia_coronet_starport_vandals_a");
    }
    public boolean corellia_coronet_demmi_wot_condition_playerHasShip(obj_id player, obj_id npc) throws InterruptedException
    {
        return space_utils.hasShip(player);
    }
    public boolean corellia_coronet_demmi_wot_condition_wonSecondRagtagSquad(obj_id player, obj_id npc) throws InterruptedException
    {
        return groundquests.isTaskActive(player, "corellia_coronet_capitol_problems_starport_vandals_2", "starport_vandals_6");
    }
    public boolean corellia_coronet_demmi_wot_condition_onSecondRagtagSquad(obj_id player, obj_id npc) throws InterruptedException
    {
        return space_quest.hasQuest(player, "assassinate", "corellia_coronet_starport_vandals_b");
    }
    public boolean corellia_coronet_demmi_wot_condition_starportVandalsCompleted(obj_id player, obj_id npc) throws InterruptedException
    {
        return groundquests.hasCompletedQuest(player, "corellia_coronet_capitol_problems_starport_vandals_2") || groundquests.hasCompletedTask(player, "corellia_coronet_capitol_problems_starport_vandals_2", "starport_vandals_6");
    }
    public void corellia_coronet_demmi_wot_action_giveFirstRagtagSquad(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.sendSignal(player, "starport_vandals_5");
    }
    public void corellia_coronet_demmi_wot_action_giveShip(obj_id player, obj_id npc) throws InterruptedException
    {
        space_quest.grantNewbieShip(player, "neutral");
    }
    public void corellia_coronet_demmi_wot_action_starportVandalsEnded(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.sendSignal(player, "starport_vandals_6");
    }
    public int corellia_coronet_demmi_wot_handleBranch5(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_30"))
        {
            if (corellia_coronet_demmi_wot_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_32");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (corellia_coronet_demmi_wot_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (corellia_coronet_demmi_wot_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_34");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_40");
                    }
                    utils.setScriptVar(player, "conversation.corellia_coronet_demmi_wot.branchId", 6);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.corellia_coronet_demmi_wot.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int corellia_coronet_demmi_wot_handleBranch6(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_34"))
        {
            if (corellia_coronet_demmi_wot_condition_alreadyHasSpaceMission(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_36");
                utils.removeScriptVar(player, "conversation.corellia_coronet_demmi_wot.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
            if (!corellia_coronet_demmi_wot_condition_playerHasShip(player, npc))
            {
                corellia_coronet_demmi_wot_action_giveShip(player, npc);
                string_id message = new string_id(c_stringFile, "s_16");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (corellia_coronet_demmi_wot_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_17");
                    }
                    utils.setScriptVar(player, "conversation.corellia_coronet_demmi_wot.branchId", 8);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.corellia_coronet_demmi_wot.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
            if (corellia_coronet_demmi_wot_condition__defaultCondition(player, npc))
            {
                corellia_coronet_demmi_wot_action_giveFirstRagtagSquad(player, npc);
                string_id message = new string_id(c_stringFile, "s_38");
                utils.removeScriptVar(player, "conversation.corellia_coronet_demmi_wot.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_40"))
        {
            if (corellia_coronet_demmi_wot_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_42");
                utils.removeScriptVar(player, "conversation.corellia_coronet_demmi_wot.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int corellia_coronet_demmi_wot_handleBranch8(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_17"))
        {
            if (corellia_coronet_demmi_wot_condition_alreadyHasSpaceMission(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_36");
                utils.removeScriptVar(player, "conversation.corellia_coronet_demmi_wot.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
            if (!corellia_coronet_demmi_wot_condition_playerHasShip(player, npc))
            {
                corellia_coronet_demmi_wot_action_giveShip(player, npc);
                string_id message = new string_id(c_stringFile, "s_16");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (corellia_coronet_demmi_wot_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_17");
                    }
                    utils.setScriptVar(player, "conversation.corellia_coronet_demmi_wot.branchId", 8);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.corellia_coronet_demmi_wot.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
            if (corellia_coronet_demmi_wot_condition__defaultCondition(player, npc))
            {
                corellia_coronet_demmi_wot_action_giveFirstRagtagSquad(player, npc);
                string_id message = new string_id(c_stringFile, "s_38");
                utils.removeScriptVar(player, "conversation.corellia_coronet_demmi_wot.branchId");
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
            detachScript(self, "conversation.corellia_coronet_demmi_wot");
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
        detachScript(self, "conversation.corellia_coronet_demmi_wot");
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
        if (corellia_coronet_demmi_wot_condition_starportVandalsCompleted(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_26");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        if (corellia_coronet_demmi_wot_condition_wonSecondRagtagSquad(player, npc))
        {
            corellia_coronet_demmi_wot_action_starportVandalsEnded(player, npc);
            string_id message = new string_id(c_stringFile, "s_23");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        if (corellia_coronet_demmi_wot_condition_onSecondRagtagSquad(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_25");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        if (corellia_coronet_demmi_wot_condition_onFirstRagtagSquad(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_21");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        if (corellia_coronet_demmi_wot_condition_starportVandalsBegin(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_28");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (corellia_coronet_demmi_wot_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_30");
                }
                utils.setScriptVar(player, "conversation.corellia_coronet_demmi_wot.branchId", 5);
                npcStartConversation(player, npc, "corellia_coronet_demmi_wot", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (corellia_coronet_demmi_wot_condition__defaultCondition(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_44");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        chat.chat(npc, "Error:  All conditions for OnStartNpcConversation were false.");
        return SCRIPT_CONTINUE;
    }
    public int OnNpcConversationResponse(obj_id self, String conversationId, obj_id player, string_id response) throws InterruptedException
    {
        if (!conversationId.equals("corellia_coronet_demmi_wot"))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id npc = self;
        int branchId = utils.getIntScriptVar(player, "conversation.corellia_coronet_demmi_wot.branchId");
        if (branchId == 5 && corellia_coronet_demmi_wot_handleBranch5(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 6 && corellia_coronet_demmi_wot_handleBranch6(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 8 && corellia_coronet_demmi_wot_handleBranch8(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        chat.chat(npc, "Error:  Fell through all branches and responses for OnNpcConversationResponse.");
        utils.removeScriptVar(player, "conversation.corellia_coronet_demmi_wot.branchId");
        return SCRIPT_CONTINUE;
    }
}
