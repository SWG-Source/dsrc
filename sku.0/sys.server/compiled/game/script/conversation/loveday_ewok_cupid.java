package script.conversation;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.ai_lib;
import script.library.badge;
import script.library.chat;
import script.library.conversation;
import script.library.groundquests;
import script.library.utils;

public class loveday_ewok_cupid extends script.base_script
{
    public loveday_ewok_cupid()
    {
    }
    public static String c_stringFile = "conversation/loveday_ewok_cupid";
    public boolean loveday_ewok_cupid_condition__defaultCondition(obj_id player, obj_id npc) throws InterruptedException
    {
        return true;
    }
    public boolean loveday_ewok_cupid_condition_alreadyHasBadge(obj_id player, obj_id npc) throws InterruptedException
    {
        return badge.hasBadge(player, "loveday_cupid_badge_10");
    }
    public boolean loveday_ewok_cupid_condition_hasCardBundle(obj_id player, obj_id npc) throws InterruptedException
    {
        obj_id lovedayCardBundle = utils.getStaticItemInInventory(player, "item_event_loveday_card_stack");
        if (isIdValid(lovedayCardBundle))
        {
            return true;
        }
        return false;
    }
    public boolean loveday_ewok_cupid_condition_hasChakHearts(obj_id player, obj_id npc) throws InterruptedException
    {
        obj_id lovedayCardBundle = utils.getStaticItemInInventory(player, "item_event_loveday_chak_heart");
        if (isIdValid(lovedayCardBundle) && getCount(lovedayCardBundle) >= 2)
        {
            return true;
        }
        return false;
    }
    public boolean loveday_ewok_cupid_condition_preparingToLeave(obj_id player, obj_id npc) throws InterruptedException
    {
        return hasObjVar(npc, "preparingForDespawn");
    }
    public boolean loveday_ewok_cupid_condition_preparingToLeave_hasBadge(obj_id player, obj_id npc) throws InterruptedException
    {
        return hasObjVar(npc, "preparingForDespawn") && badge.hasBadge(player, "loveday_cupid_badge_10");
    }
    public void loveday_ewok_cupid_action_hearts_grantCupidBadge(obj_id player, obj_id npc) throws InterruptedException
    {
        obj_id lovedayChakHearts = utils.getStaticItemInInventory(player, "item_event_loveday_chak_heart");
        if (isIdValid(lovedayChakHearts) && getCount(lovedayChakHearts) >= 2)
        {
            boolean success = incrementCount(lovedayChakHearts, -2);
            if (success && getCount(lovedayChakHearts) <= 0)
            {
                destroyObject(lovedayChakHearts);
            }
            badge.grantBadge(player, "loveday_cupid_badge_10");
            if (!hasCompletedCollectionSlot(player, "loveday_2010_found_kyoopid"))
            {
                modifyCollectionSlotValue(player, "loveday_2010_found_kyoopid", 1);
            }
        }
        return;
    }
    public void loveday_ewok_cupid_action_cards_grantCupidBadge(obj_id player, obj_id npc) throws InterruptedException
    {
        obj_id lovedayCardBundle = utils.getStaticItemInInventory(player, "item_event_loveday_card_stack");
        if (isIdValid(lovedayCardBundle))
        {
            destroyObject(lovedayCardBundle);
            badge.grantBadge(player, "loveday_cupid_badge_10");
            if (!hasCompletedCollectionSlot(player, "loveday_2010_found_kyoopid"))
            {
                modifyCollectionSlotValue(player, "loveday_2010_found_kyoopid", 1);
            }
        }
        return;
    }
    public void loveday_ewok_cupid_action_grant_collectionSlot(obj_id player, obj_id npc) throws InterruptedException
    {
        if (!hasCompletedCollectionSlot(player, "loveday_2010_found_kyoopid"))
        {
            modifyCollectionSlotValue(player, "loveday_2010_found_kyoopid", 1);
        }
        return;
    }
    public int loveday_ewok_cupid_handleBranch4(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_12"))
        {
            if (loveday_ewok_cupid_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "nod_head_once");
                string_id message = new string_id(c_stringFile, "s_14");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (loveday_ewok_cupid_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (loveday_ewok_cupid_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (loveday_ewok_cupid_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_19");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_20");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_27");
                    }
                    utils.setScriptVar(player, "conversation.loveday_ewok_cupid.branchId", 5);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.loveday_ewok_cupid.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_30"))
        {
            if (loveday_ewok_cupid_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_34");
                utils.removeScriptVar(player, "conversation.loveday_ewok_cupid.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int loveday_ewok_cupid_handleBranch5(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_19"))
        {
            if (loveday_ewok_cupid_condition_hasChakHearts(player, npc))
            {
                doAnimationAction(npc, "celebrate");
                loveday_ewok_cupid_action_hearts_grantCupidBadge(player, npc);
                string_id message = new string_id(c_stringFile, "s_21");
                utils.removeScriptVar(player, "conversation.loveday_ewok_cupid.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
            if (!loveday_ewok_cupid_condition_hasChakHearts(player, npc))
            {
                doAnimationAction(npc, "explain");
                string_id message = new string_id(c_stringFile, "s_23");
                utils.removeScriptVar(player, "conversation.loveday_ewok_cupid.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_20"))
        {
            if (loveday_ewok_cupid_condition_hasCardBundle(player, npc))
            {
                doAnimationAction(npc, "celebrate1");
                loveday_ewok_cupid_action_cards_grantCupidBadge(player, npc);
                string_id message = new string_id(c_stringFile, "s_22");
                utils.removeScriptVar(player, "conversation.loveday_ewok_cupid.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
            if (!loveday_ewok_cupid_condition_hasCardBundle(player, npc))
            {
                doAnimationAction(npc, "explain");
                string_id message = new string_id(c_stringFile, "s_24");
                utils.removeScriptVar(player, "conversation.loveday_ewok_cupid.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_27"))
        {
            if (loveday_ewok_cupid_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_28");
                utils.removeScriptVar(player, "conversation.loveday_ewok_cupid.branchId");
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
            detachScript(self, "conversation.loveday_ewok_cupid");
        }
        setCondition(self, CONDITION_CONVERSABLE);
        setCondition(self, CONDITION_HOLIDAY_INTERESTING);
        return SCRIPT_CONTINUE;
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        setCondition(self, CONDITION_CONVERSABLE);
        setCondition(self, CONDITION_HOLIDAY_INTERESTING);
        return SCRIPT_CONTINUE;
    }
    public int prepareForDespawn(obj_id self, dictionary params) throws InterruptedException
    {
        setObjVar(self, "preparingForDespawn", true);
        messageTo(self, "handleMyDespawn", null, 180, false);
        return SCRIPT_CONTINUE;
    }
    public int handleMyDespawn(obj_id self, dictionary params) throws InterruptedException
    {
        location here = getLocation(self);
        playClientEffectLoc(getPlayerCreaturesInRange(here, 100.0f), "appearance/pt_efol_hearts_large_noloop.prt", here, 1.0f);
        messageTo(self, "handleDestroySelf", null, 0.25f, false);
        return SCRIPT_CONTINUE;
    }
    public int handleDestroySelf(obj_id self, dictionary params) throws InterruptedException
    {
        LOG("idiot", "Loveday Cupid Conversation: handleDestroySelf called... ");
        obj_id[] players = getPlayerCreaturesInRange(getLocation(self), 20f);
        if (players != null && players.length > 0)
        {
            for (int i = 0; i < players.length; i++)
            {
                npcEndConversation(players[i]);
            }
        }
        destroyObject(self);
        return SCRIPT_CONTINUE;
    }
    public int OnObjectMenuRequest(obj_id self, obj_id player, menu_info menuInfo) throws InterruptedException
    {
        int menu = menuInfo.addRootMenu(menu_info_types.CONVERSE_START, null);
        menu_info_data menuInfoData = menuInfo.getMenuItemById(menu);
        menuInfoData.setServerNotify(false);
        setCondition(self, CONDITION_CONVERSABLE);
        faceTo(self, player);
        return SCRIPT_CONTINUE;
    }
    public int OnIncapacitated(obj_id self, obj_id killer) throws InterruptedException
    {
        clearCondition(self, CONDITION_CONVERSABLE);
        clearCondition(self, CONDITION_SPACE_INTERESTING);
        detachScript(self, "conversation.loveday_ewok_cupid");
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
        if (loveday_ewok_cupid_condition_preparingToLeave_hasBadge(player, npc))
        {
            loveday_ewok_cupid_action_grant_collectionSlot(player, npc);
            string_id message = new string_id(c_stringFile, "s_33");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        if (loveday_ewok_cupid_condition_preparingToLeave(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_31");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        if (loveday_ewok_cupid_condition_alreadyHasBadge(player, npc))
        {
            loveday_ewok_cupid_action_grant_collectionSlot(player, npc);
            string_id message = new string_id(c_stringFile, "s_6");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        if (loveday_ewok_cupid_condition__defaultCondition(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_10");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (loveday_ewok_cupid_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            boolean hasResponse1 = false;
            if (loveday_ewok_cupid_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_12");
                }
                if (hasResponse1)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_30");
                }
                utils.setScriptVar(player, "conversation.loveday_ewok_cupid.branchId", 4);
                npcStartConversation(player, npc, "loveday_ewok_cupid", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        chat.chat(npc, "Error:  All conditions for OnStartNpcConversation were false.");
        return SCRIPT_CONTINUE;
    }
    public int OnNpcConversationResponse(obj_id self, String conversationId, obj_id player, string_id response) throws InterruptedException
    {
        if (!conversationId.equals("loveday_ewok_cupid"))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id npc = self;
        int branchId = utils.getIntScriptVar(player, "conversation.loveday_ewok_cupid.branchId");
        if (branchId == 4 && loveday_ewok_cupid_handleBranch4(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 5 && loveday_ewok_cupid_handleBranch5(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        chat.chat(npc, "Error:  Fell through all branches and responses for OnNpcConversationResponse.");
        utils.removeScriptVar(player, "conversation.loveday_ewok_cupid.branchId");
        return SCRIPT_CONTINUE;
    }
}
