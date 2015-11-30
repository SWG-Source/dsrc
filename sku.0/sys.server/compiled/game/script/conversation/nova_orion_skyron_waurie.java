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

public class nova_orion_skyron_waurie extends script.base_script
{
    public nova_orion_skyron_waurie()
    {
    }
    public static String c_stringFile = "conversation/nova_orion_skyron_waurie";
    public boolean nova_orion_skyron_waurie_condition__defaultCondition(obj_id player, obj_id npc) throws InterruptedException
    {
        return true;
    }
    public boolean nova_orion_skyron_waurie_condition_qualifiesForSkyronQuest(obj_id player, obj_id npc) throws InterruptedException
    {
        return groundquests.hasCompletedQuest(player, "no_rank2_03");
    }
    public boolean nova_orion_skyron_waurie_condition_returnRank2_04(obj_id player, obj_id npc) throws InterruptedException
    {
        return groundquests.isTaskActive(player, "no_rank2_04", "no_rank2_04_03a") && groundquests.isTaskActive(player, "no_rank2_04", "no_rank2_04_03b");
    }
    public boolean nova_orion_skyron_waurie_condition_doneWithSkyron(obj_id player, obj_id npc) throws InterruptedException
    {
        return groundquests.hasCompletedTask(player, "no_rank2_04", "no_rank2_04_03a") || groundquests.hasCompletedTask(player, "no_rank2_04", "no_rank2_04_03b");
    }
    public boolean nova_orion_skyron_waurie_condition_onRank2_04(obj_id player, obj_id npc) throws InterruptedException
    {
        return groundquests.isQuestActive(player, "no_rank2_04");
    }
    public void nova_orion_skyron_waurie_action_grantRank2_04(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.clearQuest(player, "no_rank2_04");
        space_quest.clearQuestFlags(player, "assassinate", "no_rank2_04_01");
        space_quest.clearQuestFlags(player, "assassinate", "no_rank2_04_02");
        space_quest.clearQuestFlags(player, "assassinate", "no_rank2_04_03");
        space_quest.clearQuestFlags(player, "assassinate", "no_rank2_04_04");
        groundquests.grantQuest(player, "no_rank2_04");
    }
    public int nova_orion_skyron_waurie_handleBranch4(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_12"))
        {
            if (nova_orion_skyron_waurie_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_14");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (nova_orion_skyron_waurie_condition__defaultCondition(player, npc))
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
                    utils.setScriptVar(player, "conversation.nova_orion_skyron_waurie.branchId", 5);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.nova_orion_skyron_waurie.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int nova_orion_skyron_waurie_handleBranch5(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_19"))
        {
            if (nova_orion_skyron_waurie_condition__defaultCondition(player, npc))
            {
                nova_orion_skyron_waurie_action_grantRank2_04(player, npc);
                string_id message = new string_id(c_stringFile, "s_20");
                utils.removeScriptVar(player, "conversation.nova_orion_skyron_waurie.branchId");
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
            detachScript(self, "conversation.nova_orion_skyron_waurie");
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
        detachScript(self, "conversation.nova_orion_skyron_waurie");
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
        if (nova_orion_skyron_waurie_condition_doneWithSkyron(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_8");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        if (nova_orion_skyron_waurie_condition_returnRank2_04(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_5");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        if (nova_orion_skyron_waurie_condition_onRank2_04(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_11");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        if (nova_orion_skyron_waurie_condition_qualifiesForSkyronQuest(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_9");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (nova_orion_skyron_waurie_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_12");
                }
                utils.setScriptVar(player, "conversation.nova_orion_skyron_waurie.branchId", 4);
                npcStartConversation(player, npc, "nova_orion_skyron_waurie", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (nova_orion_skyron_waurie_condition__defaultCondition(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_18");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        chat.chat(npc, "Error:  All conditions for OnStartNpcConversation were false.");
        return SCRIPT_CONTINUE;
    }
    public int OnNpcConversationResponse(obj_id self, String conversationId, obj_id player, string_id response) throws InterruptedException
    {
        if (!conversationId.equals("nova_orion_skyron_waurie"))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id npc = self;
        int branchId = utils.getIntScriptVar(player, "conversation.nova_orion_skyron_waurie.branchId");
        if (branchId == 4 && nova_orion_skyron_waurie_handleBranch4(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 5 && nova_orion_skyron_waurie_handleBranch5(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        chat.chat(npc, "Error:  Fell through all branches and responses for OnNpcConversationResponse.");
        utils.removeScriptVar(player, "conversation.nova_orion_skyron_waurie.branchId");
        return SCRIPT_CONTINUE;
    }
}
