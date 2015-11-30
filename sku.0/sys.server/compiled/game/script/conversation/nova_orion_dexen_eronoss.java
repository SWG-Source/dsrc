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
import script.library.conversation;
import script.library.groundquests;
import script.library.space_quest;
import script.library.utils;

public class nova_orion_dexen_eronoss extends script.base_script
{
    public nova_orion_dexen_eronoss()
    {
    }
    public static String c_stringFile = "conversation/nova_orion_dexen_eronoss";
    public boolean nova_orion_dexen_eronoss_condition__defaultCondition(obj_id player, obj_id npc) throws InterruptedException
    {
        return true;
    }
    public boolean nova_orion_dexen_eronoss_condition_qualifiesForRank2Quests(obj_id player, obj_id npc) throws InterruptedException
    {
        return groundquests.hasCompletedQuest(player, "nova_orion_rank1_03") && hasCompletedCollectionSlot(player, "nova_orion_rank_02");
    }
    public boolean nova_orion_dexen_eronoss_condition_returnRank2_01(obj_id player, obj_id npc) throws InterruptedException
    {
        return groundquests.isTaskActive(player, "no_rank2_01", "no_rank2_01_02") || groundquests.hasCompletedQuest(player, "no_rank2_01");
    }
    public boolean nova_orion_dexen_eronoss_condition_returnRank2_02(obj_id player, obj_id npc) throws InterruptedException
    {
        return groundquests.isTaskActive(player, "no_rank2_02", "no_rank2_02_02") || groundquests.hasCompletedQuest(player, "no_rank2_02");
    }
    public boolean nova_orion_dexen_eronoss_condition_returnRank2_03(obj_id player, obj_id npc) throws InterruptedException
    {
        return groundquests.isTaskActive(player, "no_rank2_03", "no_rank2_03_02") || groundquests.hasCompletedQuest(player, "no_rank2_03");
    }
    public boolean nova_orion_dexen_eronoss_condition_doneWithDexen(obj_id player, obj_id npc) throws InterruptedException
    {
        return groundquests.isQuestActiveOrComplete(player, "no_rank2_04");
    }
    public boolean nova_orion_dexen_eronoss_condition_onRank2_01(obj_id player, obj_id npc) throws InterruptedException
    {
        return groundquests.isQuestActive(player, "no_rank2_01");
    }
    public boolean nova_orion_dexen_eronoss_condition_onRank2_02(obj_id player, obj_id npc) throws InterruptedException
    {
        return groundquests.isQuestActive(player, "no_rank2_02");
    }
    public boolean nova_orion_dexen_eronoss_condition_onRank2_03(obj_id player, obj_id npc) throws InterruptedException
    {
        return groundquests.isQuestActive(player, "no_rank2_03");
    }
    public void nova_orion_dexen_eronoss_action_grantRank2_01(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.clearQuest(player, "no_rank2_01");
        space_quest.clearQuestFlags(player, "escort", "no_rank2_01_01");
        space_quest.clearQuestFlags(player, "escort", "no_rank2_01_02");
        space_quest.clearQuestFlags(player, "escort", "no_rank2_01_03");
        space_quest.clearQuestFlags(player, "escort", "no_rank2_01_04");
        groundquests.grantQuest(player, "no_rank2_01");
    }
    public void nova_orion_dexen_eronoss_action_grantRank2_02(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.clearQuest(player, "no_rank2_02");
        space_quest.clearQuestFlags(player, "delivery", "no_rank2_02_01");
        space_quest.clearQuestFlags(player, "escort", "no_rank2_02_02");
        groundquests.grantQuest(player, "no_rank2_02");
    }
    public void nova_orion_dexen_eronoss_action_grantRank2_03(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.clearQuest(player, "no_rank2_03");
        space_quest.clearQuestFlags(player, "patrol", "no_rank2_03_01");
        space_quest.clearQuestFlags(player, "recovery", "no_rank2_03_02");
        space_quest.clearQuestFlags(player, "destroy_surpriseattack", "no_rank2_03_03");
        groundquests.grantQuest(player, "no_rank2_03");
    }
    public void nova_orion_dexen_eronoss_action_sendRank2_03Signal(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.sendSignal(player, "no_rank2_03_02");
    }
    public void nova_orion_dexen_eronoss_action_sendRank2_02Signal(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.sendSignal(player, "no_rank2_02_02");
    }
    public void nova_orion_dexen_eronoss_action_sendRank2_01Signal(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.sendSignal(player, "no_rank2_01_02");
    }
    public int nova_orion_dexen_eronoss_handleBranch4(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_17"))
        {
            if (nova_orion_dexen_eronoss_condition__defaultCondition(player, npc))
            {
                nova_orion_dexen_eronoss_action_grantRank2_03(player, npc);
                string_id message = new string_id(c_stringFile, "s_18");
                utils.removeScriptVar(player, "conversation.nova_orion_dexen_eronoss.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int nova_orion_dexen_eronoss_handleBranch7(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_20"))
        {
            if (nova_orion_dexen_eronoss_condition__defaultCondition(player, npc))
            {
                nova_orion_dexen_eronoss_action_grantRank2_02(player, npc);
                string_id message = new string_id(c_stringFile, "s_22");
                utils.removeScriptVar(player, "conversation.nova_orion_dexen_eronoss.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int nova_orion_dexen_eronoss_handleBranch10(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_28"))
        {
            if (nova_orion_dexen_eronoss_condition__defaultCondition(player, npc))
            {
                nova_orion_dexen_eronoss_action_grantRank2_01(player, npc);
                string_id message = new string_id(c_stringFile, "s_30");
                utils.removeScriptVar(player, "conversation.nova_orion_dexen_eronoss.branchId");
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
            detachScript(self, "conversation.nova_orion_dexen_eronoss");
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
        detachScript(self, "conversation.nova_orion_dexen_eronoss");
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
        if (nova_orion_dexen_eronoss_condition_doneWithDexen(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_8");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        if (nova_orion_dexen_eronoss_condition_returnRank2_03(player, npc))
        {
            nova_orion_dexen_eronoss_action_sendRank2_03Signal(player, npc);
            string_id message = new string_id(c_stringFile, "s_5");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        if (nova_orion_dexen_eronoss_condition_onRank2_03(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_13");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        if (nova_orion_dexen_eronoss_condition_returnRank2_02(player, npc))
        {
            nova_orion_dexen_eronoss_action_sendRank2_02Signal(player, npc);
            string_id message = new string_id(c_stringFile, "s_14");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (nova_orion_dexen_eronoss_condition__defaultCondition(player, npc))
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
                utils.setScriptVar(player, "conversation.nova_orion_dexen_eronoss.branchId", 4);
                npcStartConversation(player, npc, "nova_orion_dexen_eronoss", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (nova_orion_dexen_eronoss_condition_onRank2_02(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_12");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        if (nova_orion_dexen_eronoss_condition_returnRank2_01(player, npc))
        {
            nova_orion_dexen_eronoss_action_sendRank2_01Signal(player, npc);
            string_id message = new string_id(c_stringFile, "s_16");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (nova_orion_dexen_eronoss_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_20");
                }
                utils.setScriptVar(player, "conversation.nova_orion_dexen_eronoss.branchId", 7);
                npcStartConversation(player, npc, "nova_orion_dexen_eronoss", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (nova_orion_dexen_eronoss_condition_onRank2_01(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_24");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        if (nova_orion_dexen_eronoss_condition_qualifiesForRank2Quests(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_26");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (nova_orion_dexen_eronoss_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_28");
                }
                utils.setScriptVar(player, "conversation.nova_orion_dexen_eronoss.branchId", 10);
                npcStartConversation(player, npc, "nova_orion_dexen_eronoss", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (nova_orion_dexen_eronoss_condition__defaultCondition(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_32");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        chat.chat(npc, "Error:  All conditions for OnStartNpcConversation were false.");
        return SCRIPT_CONTINUE;
    }
    public int OnNpcConversationResponse(obj_id self, String conversationId, obj_id player, string_id response) throws InterruptedException
    {
        if (!conversationId.equals("nova_orion_dexen_eronoss"))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id npc = self;
        int branchId = utils.getIntScriptVar(player, "conversation.nova_orion_dexen_eronoss.branchId");
        if (branchId == 4 && nova_orion_dexen_eronoss_handleBranch4(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 7 && nova_orion_dexen_eronoss_handleBranch7(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 10 && nova_orion_dexen_eronoss_handleBranch10(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        chat.chat(npc, "Error:  Fell through all branches and responses for OnNpcConversationResponse.");
        utils.removeScriptVar(player, "conversation.nova_orion_dexen_eronoss.branchId");
        return SCRIPT_CONTINUE;
    }
}
