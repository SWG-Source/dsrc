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
import script.library.collection;
import script.library.conversation;
import script.library.static_item;
import script.library.utils;

public class community_painting_handout_2009 extends script.base_script
{
    public community_painting_handout_2009()
    {
    }
    public static String c_stringFile = "conversation/community_painting_handout_2009";
    public boolean community_painting_handout_2009_condition__defaultCondition(obj_id player, obj_id npc) throws InterruptedException
    {
        return true;
    }
    public boolean community_painting_handout_2009_condition_hasReceivedRebelPainting(obj_id player, obj_id npc) throws InterruptedException
    {
        if (hasCompletedCollectionSlot(player, "received_rebel_contest_painting_2009"))
        {
            return true;
        }
        return false;
    }
    public boolean community_painting_handout_2009_condition_hasReceivedImpPainting(obj_id player, obj_id npc) throws InterruptedException
    {
        if (hasCompletedCollectionSlot(player, "received_imperial_contest_painting_2009"))
        {
            return true;
        }
        return false;
    }
    public boolean community_painting_handout_2009_condition_canReceivePaintings(obj_id player, obj_id npc) throws InterruptedException
    {
        int myAge = getCurrentBirthDate() - getPlayerBirthDate(player);
        if (hasCompletedCollection(player, "player_received_contest_painting_2009_tracker") || myAge < 10)
        {
            return false;
        }
        return true;
    }
    public boolean community_painting_handout_2009_condition_hasNone(obj_id player, obj_id npc) throws InterruptedException
    {
        if (hasCompletedCollectionSlot(player, "received_rebel_contest_painting_2009") || hasCompletedCollectionSlot(player, "received_imperial_contest_painting_2009"))
        {
            return false;
        }
        return true;
    }
    public boolean community_painting_handout_2009_condition_not_old_enough(obj_id player, obj_id npc) throws InterruptedException
    {
        int myAge = getCurrentBirthDate() - getPlayerBirthDate(player);
        if (myAge < 10)
        {
            return true;
        }
        return false;
    }
    public boolean community_painting_handout_2009_condition_hasReceivedBothCommunity(obj_id player, obj_id npc) throws InterruptedException
    {
        if (hasCompletedCollection(player, "player_received_contest_painting_2009_tracker"))
        {
            return true;
        }
        return false;
    }
    public void community_painting_handout_2009_action_grantRebelCommunityPainting(obj_id player, obj_id npc) throws InterruptedException
    {
        if (!isIdValid(player) || !isIdValid(npc))
        {
            return;
        }
        if (!hasCompletedCollection(player, "player_received_contest_painting_2009_tracker") && !hasCompletedCollectionSlot(player, "received_rebel_contest_painting"))
        {
            obj_id pInv = utils.getInventoryContainer(player);
            obj_id painting_rebel = static_item.createNewItemFunction("community_painting_2009_reb", pInv);
            if (isValidId(painting_rebel) || exists(painting_rebel))
            {
                if (!hasCompletedCollectionSlot(player, "received_rebel_contest_painting_2009"))
                {
                    modifyCollectionSlotValue(player, "received_rebel_contest_painting_2009", 1);
                }
                CustomerServiceLog("Collection: ", "Player " + getFirstName(player) + "(" + player + ") received " + "rebel_community_painting_2008_02_01" + "(" + painting_rebel + ").");
            }
        }
    }
    public void community_painting_handout_2009_action_grantImpCommunitylPainting(obj_id player, obj_id npc) throws InterruptedException
    {
        if (!isValidId(player) || !isValidId(npc))
        {
            return;
        }
        if (!hasCompletedCollection(player, "player_received_contest_painting_2009_tracker") && !hasCompletedCollectionSlot(player, "received_imperial_contest_painting_2009"))
        {
            obj_id pInv = utils.getInventoryContainer(player);
            obj_id painting_imperial = static_item.createNewItemFunction("community_painting_2009_imp", pInv);
            if (isValidId(painting_imperial) || exists(painting_imperial))
            {
                if (!hasCompletedCollectionSlot(player, "received_imperial_contest_painting_2009"))
                {
                    modifyCollectionSlotValue(player, "received_imperial_contest_painting_2009", 1);
                }
                CustomerServiceLog("Collection: ", "Player " + getFirstName(player) + "(" + player + ") received " + "community_painting_2009_imp" + "(" + painting_imperial + ").");
            }
        }
    }
    public void community_painting_handout_2009_action_grantBothCommunityPaintings(obj_id player, obj_id npc) throws InterruptedException
    {
        if (!isIdValid(player) || !isIdValid(npc))
        {
            return;
        }
        if (!hasCompletedCollection(player, "player_received_contest_painting_2009_tracker"))
        {
            obj_id pInv = utils.getInventoryContainer(player);
            obj_id painting_rebel = static_item.createNewItemFunction("community_painting_2009_reb", pInv);
            obj_id painting_imperial = static_item.createNewItemFunction("community_painting_2009_imp", pInv);
            if (isValidId(painting_rebel) || exists(painting_rebel))
            {
                if (!hasCompletedCollectionSlot(player, "received_rebel_contest_painting_2009"))
                {
                    modifyCollectionSlotValue(player, "received_rebel_contest_painting_2009", 1);
                }
                CustomerServiceLog("Collection: ", "Player " + getFirstName(player) + "(" + player + ") received " + "community_painting_2009_reb" + "(" + painting_rebel + ").");
            }
            if (isValidId(painting_imperial) || exists(painting_imperial))
            {
                if (!hasCompletedCollectionSlot(player, "received_imperial_contest_painting_2009"))
                {
                    modifyCollectionSlotValue(player, "received_imperial_contest_painting_2009", 1);
                }
                CustomerServiceLog("Collection: ", "Player " + getFirstName(player) + "(" + player + ") received " + "community_painting_2009_imp" + "(" + painting_imperial + ").");
            }
        }
    }
    public int community_painting_handout_2009_handleBranch2(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_6"))
        {
            if (community_painting_handout_2009_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_8");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (community_painting_handout_2009_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (community_painting_handout_2009_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_10");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_26");
                    }
                    utils.setScriptVar(player, "conversation.community_painting_handout_2009.branchId", 3);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.community_painting_handout_2009.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_30"))
        {
            if (community_painting_handout_2009_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_32");
                utils.removeScriptVar(player, "conversation.community_painting_handout_2009.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int community_painting_handout_2009_handleBranch3(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_10"))
        {
            if (community_painting_handout_2009_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_12");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (!community_painting_handout_2009_condition_hasReceivedImpPainting(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (!community_painting_handout_2009_condition_hasReceivedRebelPainting(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (community_painting_handout_2009_condition_hasNone(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse2 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_14");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_18");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_22");
                    }
                    utils.setScriptVar(player, "conversation.community_painting_handout_2009.branchId", 4);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.community_painting_handout_2009.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_26"))
        {
            if (community_painting_handout_2009_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_28");
                utils.removeScriptVar(player, "conversation.community_painting_handout_2009.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int community_painting_handout_2009_handleBranch4(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_14"))
        {
            community_painting_handout_2009_action_grantImpCommunitylPainting(player, npc);
            if (community_painting_handout_2009_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_16");
                utils.removeScriptVar(player, "conversation.community_painting_handout_2009.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_18"))
        {
            community_painting_handout_2009_action_grantRebelCommunityPainting(player, npc);
            if (community_painting_handout_2009_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_20");
                utils.removeScriptVar(player, "conversation.community_painting_handout_2009.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_22"))
        {
            community_painting_handout_2009_action_grantBothCommunityPaintings(player, npc);
            if (community_painting_handout_2009_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_24");
                utils.removeScriptVar(player, "conversation.community_painting_handout_2009.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public static string_id SID_NOT_OLD_ENOUGH = new string_id("collection", "not_old_enough");
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        if ((!isTangible(self)) || (isPlayer(self)))
        {
            detachScript(self, "conversation.community_painting_handout_2009");
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
        detachScript(self, "conversation.community_painting_handout_2009");
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
        if (community_painting_handout_2009_condition_hasReceivedBothCommunity(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_34");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        if (community_painting_handout_2009_condition_canReceivePaintings(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_4");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (community_painting_handout_2009_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            boolean hasResponse1 = false;
            if (community_painting_handout_2009_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_6");
                }
                if (hasResponse1)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_30");
                }
                utils.setScriptVar(player, "conversation.community_painting_handout_2009.branchId", 2);
                npcStartConversation(player, npc, "community_painting_handout_2009", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (community_painting_handout_2009_condition_not_old_enough(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_36");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        chat.chat(npc, "Error:  All conditions for OnStartNpcConversation were false.");
        return SCRIPT_CONTINUE;
    }
    public int OnNpcConversationResponse(obj_id self, String conversationId, obj_id player, string_id response) throws InterruptedException
    {
        if (!conversationId.equals("community_painting_handout_2009"))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id npc = self;
        int branchId = utils.getIntScriptVar(player, "conversation.community_painting_handout_2009.branchId");
        if (branchId == 2 && community_painting_handout_2009_handleBranch2(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 3 && community_painting_handout_2009_handleBranch3(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 4 && community_painting_handout_2009_handleBranch4(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        chat.chat(npc, "Error:  Fell through all branches and responses for OnNpcConversationResponse.");
        utils.removeScriptVar(player, "conversation.community_painting_handout_2009.branchId");
        return SCRIPT_CONTINUE;
    }
}
