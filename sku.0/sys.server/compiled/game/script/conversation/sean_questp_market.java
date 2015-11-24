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
import script.library.utils;

public class sean_questp_market extends script.base_script
{
    public sean_questp_market()
    {
    }
    public static String c_stringFile = "conversation/sean_questp_market";
    public boolean sean_questp_market_condition__defaultCondition(obj_id player, obj_id npc) throws InterruptedException
    {
        return true;
    }
    public boolean sean_questp_market_condition_Campaign(obj_id player, obj_id npc) throws InterruptedException
    {
        if (hasObjVar(npc, "bestine.electionStarted"))
        {
            int electionNum = getIntObjVar(npc, "bestine.electionStarted");
            if (hasObjVar(player, "bestine.campaign"))
            {
                int electionPlayerIsIn = getIntObjVar(player, "bestine.campaign");
                if (electionPlayerIsIn >= electionNum)
                {
                    if (!hasObjVar(player, "bestine.sean_market_noroom"))
                    {
                        return true;
                    }
                }
            }
        }
        return false;
    }
    public boolean sean_questp_market_condition_alreadyHasEvidence(obj_id player, obj_id npc) throws InterruptedException
    {
        return utils.playerHasItemByTemplate(player, "object/tangible/loot/quest/sean_questp_mdisk.iff");
    }
    public boolean sean_questp_market_condition_noroomObjVar(obj_id player, obj_id npc) throws InterruptedException
    {
        if (hasObjVar(npc, "bestine.electionStarted"))
        {
            int electionNum = getIntObjVar(npc, "bestine.electionStarted");
            if (hasObjVar(player, "bestine.campaign"))
            {
                int electionPlayerIsIn = getIntObjVar(player, "bestine.campaign");
                if (electionPlayerIsIn >= electionNum)
                {
                    if (hasObjVar(player, "bestine.sean_market_noroom"))
                    {
                        return true;
                    }
                }
            }
        }
        return false;
    }
    public boolean sean_questp_market_condition_nonoffice(obj_id player, obj_id npc) throws InterruptedException
    {
        return hasObjVar(npc, "bestine.electionEnded");
    }
    public boolean sean_questp_market_condition_noInventorySpace(obj_id player, obj_id npc) throws InterruptedException
    {
        boolean hasNoInvRoom = false;
        obj_id playerInv = utils.getInventoryContainer(player);
        if (isIdValid(playerInv))
        {
            int free_space = getVolumeFree(playerInv);
            if (free_space < 1)
            {
                hasNoInvRoom = true;
            }
        }
        return hasNoInvRoom;
    }
    public void sean_questp_market_action_noroom(obj_id player, obj_id npc) throws InterruptedException
    {
        int electionNum = getIntObjVar(npc, "bestine.electionStarted");
        setObjVar(player, "bestine.sean_market_noroom", electionNum);
    }
    public void sean_questp_market_action_giveDisk(obj_id player, obj_id npc) throws InterruptedException
    {
        if (hasObjVar(player, "bestine.sean_market_noroom"))
        {
            removeObjVar(player, "bestine.sean_market_noroom");
        }
        String MDISK = "object/tangible/loot/quest/sean_questp_mdisk.iff";
        if (isIdValid(player))
        {
            obj_id playerInv = getObjectInSlot(player, "inventory");
            if (isIdValid(playerInv))
            {
                obj_id item = createObject(MDISK, playerInv, "");
                if (isIdValid(item))
                {
                    return;
                }
            }
        }
        return;
    }
    public int sean_questp_market_handleBranch3(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_c82e9a2f"))
        {
            if (!sean_questp_market_condition_noInventorySpace(player, npc))
            {
                sean_questp_market_action_giveDisk(player, npc);
                string_id message = new string_id(c_stringFile, "s_14d1042b");
                utils.removeScriptVar(player, "conversation.sean_questp_market.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
            if (sean_questp_market_condition_noInventorySpace(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_1b885620");
                utils.removeScriptVar(player, "conversation.sean_questp_market.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_6cd9792d"))
        {
            if (sean_questp_market_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_c1035c83");
                utils.removeScriptVar(player, "conversation.sean_questp_market.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int sean_questp_market_handleBranch7(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_cb056ffb"))
        {
            if (sean_questp_market_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_e61c91fe");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (sean_questp_market_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_67e6df55");
                    }
                    utils.setScriptVar(player, "conversation.sean_questp_market.branchId", 8);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.sean_questp_market.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_de2e386f"))
        {
            if (sean_questp_market_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_ce0e0612");
                utils.removeScriptVar(player, "conversation.sean_questp_market.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int sean_questp_market_handleBranch8(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_67e6df55"))
        {
            if (sean_questp_market_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_9e71b2ab");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (sean_questp_market_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_a4149441");
                    }
                    utils.setScriptVar(player, "conversation.sean_questp_market.branchId", 9);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.sean_questp_market.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int sean_questp_market_handleBranch9(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_a4149441"))
        {
            if (sean_questp_market_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_b4164d7b");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (sean_questp_market_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_fc27931b");
                    }
                    utils.setScriptVar(player, "conversation.sean_questp_market.branchId", 10);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.sean_questp_market.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int sean_questp_market_handleBranch10(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_fc27931b"))
        {
            if (!sean_questp_market_condition_noInventorySpace(player, npc))
            {
                sean_questp_market_action_giveDisk(player, npc);
                string_id message = new string_id(c_stringFile, "s_60613aaf");
                utils.removeScriptVar(player, "conversation.sean_questp_market.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
            if (sean_questp_market_condition_noInventorySpace(player, npc))
            {
                sean_questp_market_action_noroom(player, npc);
                string_id message = new string_id(c_stringFile, "s_a1a4c8c9");
                utils.removeScriptVar(player, "conversation.sean_questp_market.branchId");
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
            detachScript(self, "conversation.sean_questp_market");
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
        detachScript(self, "conversation.sean_questp_market");
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
        if (sean_questp_market_condition_nonoffice(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_f319b246");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        if (sean_questp_market_condition_alreadyHasEvidence(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_c8e9e99a");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        if (sean_questp_market_condition_noroomObjVar(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_40977666");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (sean_questp_market_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            boolean hasResponse1 = false;
            if (sean_questp_market_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_c82e9a2f");
                }
                if (hasResponse1)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_6cd9792d");
                }
                utils.setScriptVar(player, "conversation.sean_questp_market.branchId", 3);
                npcStartConversation(player, npc, "sean_questp_market", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (sean_questp_market_condition_Campaign(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_faf0f6d6");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (sean_questp_market_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            boolean hasResponse1 = false;
            if (sean_questp_market_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_cb056ffb");
                }
                if (hasResponse1)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_de2e386f");
                }
                utils.setScriptVar(player, "conversation.sean_questp_market.branchId", 7);
                npcStartConversation(player, npc, "sean_questp_market", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (sean_questp_market_condition__defaultCondition(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_7ffa475");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        chat.chat(npc, "Error:  All conditions for OnStartNpcConversation were false.");
        return SCRIPT_CONTINUE;
    }
    public int OnNpcConversationResponse(obj_id self, String conversationId, obj_id player, string_id response) throws InterruptedException
    {
        if (!conversationId.equals("sean_questp_market"))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id npc = self;
        int branchId = utils.getIntScriptVar(player, "conversation.sean_questp_market.branchId");
        if (branchId == 3 && sean_questp_market_handleBranch3(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 7 && sean_questp_market_handleBranch7(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 8 && sean_questp_market_handleBranch8(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 9 && sean_questp_market_handleBranch9(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 10 && sean_questp_market_handleBranch10(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        chat.chat(npc, "Error:  Fell through all branches and responses for OnNpcConversationResponse.");
        utils.removeScriptVar(player, "conversation.sean_questp_market.branchId");
        return SCRIPT_CONTINUE;
    }
}
