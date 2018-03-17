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

public class community_tcg_photo_contest_painting_handout extends script.base_script
{
    public community_tcg_photo_contest_painting_handout()
    {
    }
    public static String c_stringFile = "conversation/community_tcg_photo_contest_painting_handout";
    public boolean community_tcg_photo_contest_painting_handout_condition__defaultCondition(obj_id player, obj_id npc) throws InterruptedException
    {
        return true;
    }
    public boolean community_tcg_photo_contest_painting_handout_condition_hasReceivedPainting02(obj_id player, obj_id npc) throws InterruptedException
    {
        if (hasCompletedCollectionSlot(player, "received_tcg_gcw_photo_painting_2010_02"))
        {
            return true;
        }
        return false;
    }
    public boolean community_tcg_photo_contest_painting_handout_condition_hasReceivedPainting01(obj_id player, obj_id npc) throws InterruptedException
    {
        if (hasCompletedCollectionSlot(player, "received_tcg_gcw_photo_painting_2010_01"))
        {
            return true;
        }
        return false;
    }
    public boolean community_tcg_photo_contest_painting_handout_condition_canReceivePaintings(obj_id player, obj_id npc) throws InterruptedException
    {
        if (isFreeTrialAccount(player))
        {
            return false;
        }
        int myAge = getCurrentBirthDate() - getPlayerBirthDate(player);
        if (hasCompletedCollection(player, "player_received_tcg_gcw_photo_painting_2010_tracker") || myAge < 10)
        {
            return false;
        }
        return true;
    }
    public boolean community_tcg_photo_contest_painting_handout_condition_hasNone(obj_id player, obj_id npc) throws InterruptedException
    {
        if (hasCompletedCollectionSlot(player, "received_tcg_gcw_photo_painting_2010_01") || hasCompletedCollectionSlot(player, "received_tcg_gcw_photo_painting_2010_02") || hasCompletedCollectionSlot(player, "received_tcg_gcw_photo_painting_2010_03"))
        {
            return false;
        }
        return true;
    }
    public boolean community_tcg_photo_contest_painting_handout_condition_not_old_enough(obj_id player, obj_id npc) throws InterruptedException
    {
        int myAge = getCurrentBirthDate() - getPlayerBirthDate(player);
        if (myAge < 10)
        {
            return true;
        }
        return false;
    }
    public boolean community_tcg_photo_contest_painting_handout_condition_hasReceivedAllPaintings(obj_id player, obj_id npc) throws InterruptedException
    {
        if (hasCompletedCollection(player, "player_received_tcg_gcw_photo_painting_2010_tracker"))
        {
            return true;
        }
        return false;
    }
    public boolean community_tcg_photo_contest_painting_handout_condition_hasReceivedPainting03(obj_id player, obj_id npc) throws InterruptedException
    {
        if (hasCompletedCollectionSlot(player, "received_tcg_gcw_photo_painting_2010_03"))
        {
            return true;
        }
        return false;
    }
    public boolean community_tcg_photo_contest_painting_handout_condition_isTrialAccountPlayer(obj_id player, obj_id npc) throws InterruptedException
    {
        if (isFreeTrialAccount(player))
        {
            return true;
        }
        return false;
    }
    public void community_tcg_photo_contest_painting_handout_action_grantPainting03(obj_id player, obj_id npc) throws InterruptedException
    {
        if (!isValidId(player) || !isValidId(npc))
        {
            return;
        }
        if (!hasCompletedCollection(player, "player_received_tcg_gcw_photo_painting_2010_tracker") && !hasCompletedCollectionSlot(player, "received_tcg_gcw_photo_painting_2010_03"))
        {
            obj_id pInv = utils.getInventoryContainer(player);
            obj_id painting03 = static_item.createNewItemFunction("community_tcg_gcw_photo_contest_painting_2010_03", pInv);
            if (isValidId(painting03) || exists(painting03))
            {
                if (!hasCompletedCollectionSlot(player, "received_tcg_gcw_photo_painting_2010_03"))
                {
                    modifyCollectionSlotValue(player, "received_tcg_gcw_photo_painting_2010_03", 1);
                }
                CustomerServiceLog("Collection: ", "Player " + getFirstName(player) + "(" + player + ") received " + "community_tcg_gcw_photo_contest_painting_2010_03" + "(" + painting03 + ").");
            }
        }
    }
    public void community_tcg_photo_contest_painting_handout_action_grantPainting02(obj_id player, obj_id npc) throws InterruptedException
    {
        if (!isValidId(player) || !isValidId(npc))
        {
            return;
        }
        if (!hasCompletedCollection(player, "player_received_tcg_gcw_photo_painting_2010_tracker") && !hasCompletedCollectionSlot(player, "received_tcg_gcw_photo_painting_2010_02"))
        {
            obj_id pInv = utils.getInventoryContainer(player);
            obj_id painting02 = static_item.createNewItemFunction("community_tcg_gcw_photo_contest_painting_2010_02", pInv);
            if (isValidId(painting02) || exists(painting02))
            {
                if (!hasCompletedCollectionSlot(player, "received_tcg_gcw_photo_painting_2010_02"))
                {
                    modifyCollectionSlotValue(player, "received_tcg_gcw_photo_painting_2010_02", 1);
                }
                CustomerServiceLog("Collection: ", "Player " + getFirstName(player) + "(" + player + ") received " + "community_tcg_gcw_photo_contest_painting_2010_02" + "(" + painting02 + ").");
            }
        }
    }
    public void community_tcg_photo_contest_painting_handout_action_grantAllPaintings(obj_id player, obj_id npc) throws InterruptedException
    {
        if (!isIdValid(player) || !isIdValid(npc))
        {
            return;
        }
        if (!hasCompletedCollection(player, "player_received_tcg_gcw_photo_painting_2010_tracker"))
        {
            obj_id pInv = utils.getInventoryContainer(player);
            if (!hasCompletedCollectionSlot(player, "received_tcg_gcw_photo_painting_2010_01"))
            {
                obj_id painting01 = static_item.createNewItemFunction("community_tcg_gcw_photo_contest_painting_2010_01", pInv);
                if (isValidId(painting01) || exists(painting01))
                {
                    modifyCollectionSlotValue(player, "received_tcg_gcw_photo_painting_2010_01", 1);
                }
                CustomerServiceLog("Collection: ", "Player " + getFirstName(player) + "(" + player + ") received " + "community_tcg_gcw_photo_contest_painting_2010_01" + "(" + painting01 + ").");
            }
            if (!hasCompletedCollectionSlot(player, "received_tcg_gcw_photo_painting_2010_02"))
            {
                obj_id painting02 = static_item.createNewItemFunction("community_tcg_gcw_photo_contest_painting_2010_02", pInv);
                if (isValidId(painting02) || exists(painting02))
                {
                    modifyCollectionSlotValue(player, "received_tcg_gcw_photo_painting_2010_02", 1);
                }
                CustomerServiceLog("Collection: ", "Player " + getFirstName(player) + "(" + player + ") received " + "community_tcg_gcw_photo_contest_painting_2010_02" + "(" + painting02 + ").");
            }
            if (!hasCompletedCollectionSlot(player, "received_tcg_gcw_photo_painting_2010_03"))
            {
                obj_id painting03 = static_item.createNewItemFunction("community_tcg_gcw_photo_contest_painting_2010_03", pInv);
                if (isValidId(painting03) || exists(painting03))
                {
                    modifyCollectionSlotValue(player, "received_tcg_gcw_photo_painting_2010_03", 1);
                }
                CustomerServiceLog("Collection: ", "Player " + getFirstName(player) + "(" + player + ") received " + "community_tcg_gcw_photo_contest_painting_2010_03" + "(" + painting03 + ").");
            }
        }
    }
    public void community_tcg_photo_contest_painting_handout_action_grantPainting01(obj_id player, obj_id npc) throws InterruptedException
    {
        if (!isValidId(player) || !isValidId(npc))
        {
            return;
        }
        if (!hasCompletedCollection(player, "player_received_tcg_gcw_photo_painting_2010_tracker") && !hasCompletedCollectionSlot(player, "received_tcg_gcw_photo_painting_2010_01"))
        {
            obj_id pInv = utils.getInventoryContainer(player);
            obj_id painting01 = static_item.createNewItemFunction("community_tcg_gcw_photo_contest_painting_2010_01", pInv);
            if (isValidId(painting01) || exists(painting01))
            {
                if (!hasCompletedCollectionSlot(player, "received_tcg_gcw_photo_painting_2010_01"))
                {
                    modifyCollectionSlotValue(player, "received_tcg_gcw_photo_painting_2010_01", 1);
                }
                CustomerServiceLog("Collection: ", "Player " + getFirstName(player) + "(" + player + ") received " + "community_tcg_gcw_photo_contest_painting_2010_01" + "(" + painting01 + ").");
            }
        }
    }
    public int community_tcg_photo_contest_painting_handout_handleBranch2(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_7"))
        {
            if (community_tcg_photo_contest_painting_handout_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_9");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (community_tcg_photo_contest_painting_handout_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (community_tcg_photo_contest_painting_handout_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_11");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_29");
                    }
                    utils.setScriptVar(player, "conversation.community_tcg_photo_contest_painting_handout.branchId", 3);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.community_tcg_photo_contest_painting_handout.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_33"))
        {
            if (community_tcg_photo_contest_painting_handout_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_36");
                utils.removeScriptVar(player, "conversation.community_tcg_photo_contest_painting_handout.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int community_tcg_photo_contest_painting_handout_handleBranch3(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_11"))
        {
            if (community_tcg_photo_contest_painting_handout_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "point_right");
                string_id message = new string_id(c_stringFile, "s_13");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (!community_tcg_photo_contest_painting_handout_condition_hasReceivedPainting01(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (!community_tcg_photo_contest_painting_handout_condition_hasReceivedPainting02(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (!community_tcg_photo_contest_painting_handout_condition_hasReceivedPainting03(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse2 = true;
                }
                boolean hasResponse3 = false;
                if (community_tcg_photo_contest_painting_handout_condition_hasNone(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse3 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_15");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_19");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_37");
                    }
                    if (hasResponse3)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_25");
                    }
                    utils.setScriptVar(player, "conversation.community_tcg_photo_contest_painting_handout.branchId", 4);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.community_tcg_photo_contest_painting_handout.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_29"))
        {
            if (community_tcg_photo_contest_painting_handout_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_31");
                utils.removeScriptVar(player, "conversation.community_tcg_photo_contest_painting_handout.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int community_tcg_photo_contest_painting_handout_handleBranch4(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_15"))
        {
            community_tcg_photo_contest_painting_handout_action_grantPainting01(player, npc);
            if (community_tcg_photo_contest_painting_handout_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_17");
                utils.removeScriptVar(player, "conversation.community_tcg_photo_contest_painting_handout.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_19"))
        {
            community_tcg_photo_contest_painting_handout_action_grantPainting02(player, npc);
            if (community_tcg_photo_contest_painting_handout_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_21");
                utils.removeScriptVar(player, "conversation.community_tcg_photo_contest_painting_handout.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_37"))
        {
            community_tcg_photo_contest_painting_handout_action_grantPainting03(player, npc);
            if (community_tcg_photo_contest_painting_handout_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_38");
                utils.removeScriptVar(player, "conversation.community_tcg_photo_contest_painting_handout.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_25"))
        {
            community_tcg_photo_contest_painting_handout_action_grantAllPaintings(player, npc);
            if (community_tcg_photo_contest_painting_handout_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_27");
                utils.removeScriptVar(player, "conversation.community_tcg_photo_contest_painting_handout.branchId");
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
            detachScript(self, "conversation.community_tcg_photo_contest_painting_handout");
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
        detachScript(self, "conversation.community_tcg_photo_contest_painting_handout");
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
        if (community_tcg_photo_contest_painting_handout_condition_hasReceivedAllPaintings(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_34");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        if (community_tcg_photo_contest_painting_handout_condition_canReceivePaintings(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_5");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (community_tcg_photo_contest_painting_handout_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            boolean hasResponse1 = false;
            if (community_tcg_photo_contest_painting_handout_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_7");
                }
                if (hasResponse1)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_33");
                }
                utils.setScriptVar(player, "conversation.community_tcg_photo_contest_painting_handout.branchId", 2);
                npcStartConversation(player, npc, "community_tcg_photo_contest_painting_handout", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (community_tcg_photo_contest_painting_handout_condition_isTrialAccountPlayer(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_41");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        if (community_tcg_photo_contest_painting_handout_condition_not_old_enough(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_42");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        if (community_tcg_photo_contest_painting_handout_condition__defaultCondition(player, npc))
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
        if (!conversationId.equals("community_tcg_photo_contest_painting_handout"))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id npc = self;
        int branchId = utils.getIntScriptVar(player, "conversation.community_tcg_photo_contest_painting_handout.branchId");
        if (branchId == 2 && community_tcg_photo_contest_painting_handout_handleBranch2(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 3 && community_tcg_photo_contest_painting_handout_handleBranch3(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 4 && community_tcg_photo_contest_painting_handout_handleBranch4(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        chat.chat(npc, "Error:  Fell through all branches and responses for OnNpcConversationResponse.");
        utils.removeScriptVar(player, "conversation.community_tcg_photo_contest_painting_handout.branchId");
        return SCRIPT_CONTINUE;
    }
}
