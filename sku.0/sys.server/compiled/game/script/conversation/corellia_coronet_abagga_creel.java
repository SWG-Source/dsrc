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

public class corellia_coronet_abagga_creel extends script.base_script
{
    public corellia_coronet_abagga_creel()
    {
    }
    public static String c_stringFile = "conversation/corellia_coronet_abagga_creel";
    public boolean corellia_coronet_abagga_creel_condition__defaultCondition(obj_id player, obj_id npc) throws InterruptedException
    {
        return true;
    }
    public boolean corellia_coronet_abagga_creel_condition_needsInvoice(obj_id player, obj_id npc) throws InterruptedException
    {
        return groundquests.isTaskActive(player, "corellia_coronet_find_missing_shipment", "ralMundi_getInvoice");
    }
    public boolean corellia_coronet_abagga_creel_condition_isCraftingTool(obj_id player, obj_id npc) throws InterruptedException
    {
        return groundquests.isTaskActive(player, "corellia_coronet_find_missing_shipment", "ralMundi_craftTool");
    }
    public boolean corellia_coronet_abagga_creel_condition_craftedTool(obj_id player, obj_id npc) throws InterruptedException
    {
        return groundquests.isTaskActive(player, "corellia_coronet_find_missing_shipment", "ralMundi_craftedTool");
    }
    public boolean corellia_coronet_abagga_creel_condition_finishedWithAbagga(obj_id player, obj_id npc) throws InterruptedException
    {
        return groundquests.hasCompletedTask(player, "corellia_coronet_find_missing_shipment", "ralMundi_craftedTool") || groundquests.hasCompletedTask(player, "corellia_coronet_capitol_problems_missing_shipment_intro", "ralMundi_gaveTool");
    }
    public boolean corellia_coronet_abagga_creel_condition_finishedMissingShipment(obj_id player, obj_id npc) throws InterruptedException
    {
        return groundquests.hasCompletedQuest(player, "corellia_coronet_find_missing_shipment") || groundquests.hasCompletedQuest(player, "corellia_coronet_capitol_problems_missing_shipment");
    }
    public boolean corellia_coronet_abagga_creel_condition_craftCraftingTool(obj_id player, obj_id npc) throws InterruptedException
    {
        return groundquests.isTaskActive(player, "corellia_coronet_find_missing_shipment", "ralMundi_getInvoice");
    }
    public boolean corellia_coronet_abagga_creel_condition_newIntroBranch(obj_id player, obj_id npc) throws InterruptedException
    {
        return groundquests.isTaskActive(player, "corellia_coronet_capitol_problems_missing_shipment_intro", "ralMundi_gaveTool");
    }
    public void corellia_coronet_abagga_creel_action_sendForTool(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.sendSignal(player, "ralMundi_getInvoice");
    }
    public void corellia_coronet_abagga_creel_action_giveInvoiceCrafted(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.sendSignal(player, "ralMundi_craftedTool");
    }
    public void corellia_coronet_abagga_creel_action_giveInvoiceGiven(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.sendSignal(player, "ralMundi_gaveTool");
    }
    public int corellia_coronet_abagga_creel_handleBranch3(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_34"))
        {
            if (corellia_coronet_abagga_creel_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "check_wrist_device");
                corellia_coronet_abagga_creel_action_giveInvoiceCrafted(player, npc);
                string_id message = new string_id(c_stringFile, "s_35");
                utils.removeScriptVar(player, "conversation.corellia_coronet_abagga_creel.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int corellia_coronet_abagga_creel_handleBranch5(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_38"))
        {
            if (corellia_coronet_abagga_creel_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "check_wrist_device");
                corellia_coronet_abagga_creel_action_giveInvoiceGiven(player, npc);
                string_id message = new string_id(c_stringFile, "s_39");
                utils.removeScriptVar(player, "conversation.corellia_coronet_abagga_creel.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int corellia_coronet_abagga_creel_handleBranch8(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_19"))
        {
            if (corellia_coronet_abagga_creel_condition__defaultCondition(player, npc))
            {
                corellia_coronet_abagga_creel_action_sendForTool(player, npc);
                string_id message = new string_id(c_stringFile, "s_21");
                utils.removeScriptVar(player, "conversation.corellia_coronet_abagga_creel.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_25"))
        {
            if (corellia_coronet_abagga_creel_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_27");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (corellia_coronet_abagga_creel_condition_craftCraftingTool(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (corellia_coronet_abagga_creel_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_42");
                    }
                    utils.setScriptVar(player, "conversation.corellia_coronet_abagga_creel.branchId", 10);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.corellia_coronet_abagga_creel.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int corellia_coronet_abagga_creel_handleBranch10(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_30"))
        {
            if (corellia_coronet_abagga_creel_condition__defaultCondition(player, npc))
            {
                corellia_coronet_abagga_creel_action_sendForTool(player, npc);
                string_id message = new string_id(c_stringFile, "s_21");
                utils.removeScriptVar(player, "conversation.corellia_coronet_abagga_creel.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_42"))
        {
            if (corellia_coronet_abagga_creel_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_44");
                utils.removeScriptVar(player, "conversation.corellia_coronet_abagga_creel.branchId");
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
            detachScript(self, "conversation.corellia_coronet_abagga_creel");
        }
        setCondition(self, CONDITION_CONVERSABLE);
        setInvulnerable(self, true);
        if (!hasScript(self, "quest.task.ground.give_item_to_npc"))
        {
            attachScript(self, "quest.task.ground.give_item_to_npc");
            setObjVar(self, "quest_name", "corellia_coronet_capitol_problems_missing_shipment");
            setObjVar(self, "task_name", "ralMundi_giveTool");
        }
        return SCRIPT_CONTINUE;
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        setCondition(self, CONDITION_CONVERSABLE);
        setInvulnerable(self, true);
        if (!hasScript(self, "quest.task.ground.give_item_to_npc"))
        {
            attachScript(self, "quest.task.ground.give_item_to_npc");
            setObjVar(self, "quest_name", "corellia_coronet_capitol_problems_missing_shipment");
            setObjVar(self, "task_name", "ralMundi_giveTool");
        }
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
        detachScript(self, "conversation.corellia_coronet_abagga_creel");
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
        if (corellia_coronet_abagga_creel_condition_finishedMissingShipment(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_29");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        if (corellia_coronet_abagga_creel_condition_finishedWithAbagga(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_15");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        if (corellia_coronet_abagga_creel_condition_craftedTool(player, npc))
        {
            doAnimationAction(npc, "manipulate_medium");
            string_id message = new string_id(c_stringFile, "s_6");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (corellia_coronet_abagga_creel_condition__defaultCondition(player, npc))
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
                utils.setScriptVar(player, "conversation.corellia_coronet_abagga_creel.branchId", 3);
                npcStartConversation(player, npc, "corellia_coronet_abagga_creel", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (corellia_coronet_abagga_creel_condition_newIntroBranch(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_36");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (corellia_coronet_abagga_creel_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_38");
                }
                utils.setScriptVar(player, "conversation.corellia_coronet_abagga_creel.branchId", 5);
                npcStartConversation(player, npc, "corellia_coronet_abagga_creel", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (corellia_coronet_abagga_creel_condition_isCraftingTool(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_13");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        if (corellia_coronet_abagga_creel_condition_needsInvoice(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_17");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (corellia_coronet_abagga_creel_condition_craftCraftingTool(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            boolean hasResponse1 = false;
            if (corellia_coronet_abagga_creel_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_19");
                }
                if (hasResponse1)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_25");
                }
                utils.setScriptVar(player, "conversation.corellia_coronet_abagga_creel.branchId", 8);
                npcStartConversation(player, npc, "corellia_coronet_abagga_creel", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (corellia_coronet_abagga_creel_condition__defaultCondition(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_46");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        chat.chat(npc, "Error:  All conditions for OnStartNpcConversation were false.");
        return SCRIPT_CONTINUE;
    }
    public int OnNpcConversationResponse(obj_id self, String conversationId, obj_id player, string_id response) throws InterruptedException
    {
        if (!conversationId.equals("corellia_coronet_abagga_creel"))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id npc = self;
        int branchId = utils.getIntScriptVar(player, "conversation.corellia_coronet_abagga_creel.branchId");
        if (branchId == 3 && corellia_coronet_abagga_creel_handleBranch3(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 5 && corellia_coronet_abagga_creel_handleBranch5(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 8 && corellia_coronet_abagga_creel_handleBranch8(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 10 && corellia_coronet_abagga_creel_handleBranch10(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        chat.chat(npc, "Error:  Fell through all branches and responses for OnNpcConversationResponse.");
        utils.removeScriptVar(player, "conversation.corellia_coronet_abagga_creel.branchId");
        return SCRIPT_CONTINUE;
    }
}
