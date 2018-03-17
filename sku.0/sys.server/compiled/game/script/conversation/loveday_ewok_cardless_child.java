package script.conversation;

import script.library.ai_lib;
import script.library.chat;
import script.library.groundquests;
import script.library.utils;
import script.*;

public class loveday_ewok_cardless_child extends script.base_script
{
    public loveday_ewok_cardless_child()
    {
    }
    public static String c_stringFile = "conversation/loveday_ewok_cardless_child";
    public boolean loveday_ewok_cardless_child_condition__defaultCondition(obj_id player, obj_id npc) throws InterruptedException
    {
        return true;
    }
    public boolean loveday_ewok_cardless_child_condition_returningQuestIncomplete(obj_id player, obj_id npc) throws InterruptedException
    {
        if (groundquests.isQuestActive(player, "loveday_ewok_bundle_of_cards"))
        {
            obj_id lovedayCardBundle = utils.getStaticItemInInventory(player, "item_event_loveday_card_stack");
            if (!isIdValid(lovedayCardBundle))
            {
                return true;
            }
        }
        return false;
    }
    public boolean loveday_ewok_cardless_child_condition_returningQuestComplete(obj_id player, obj_id npc) throws InterruptedException
    {
        if (groundquests.isQuestActive(player, "loveday_ewok_bundle_of_cards"))
        {
            obj_id lovedayCardBundle = utils.getStaticItemInInventory(player, "item_event_loveday_card_stack");
            if (isIdValid(lovedayCardBundle))
            {
                return true;
            }
        }
        return false;
    }
    public boolean loveday_ewok_cardless_child_condition_notYetReadyForAnother(obj_id player, obj_id npc) throws InterruptedException
    {
        if (groundquests.hasCompletedQuest(player, "loveday_ewok_bundle_of_cards") && hasObjVar(player, "loveday.eligibleBundleOfCards"))
        {
            int eligibleForNextQuestAt = getIntObjVar(player, "loveday.eligibleBundleOfCards");
            if (getCalendarTime() < eligibleForNextQuestAt)
            {
                return true;
            }
        }
        return false;
    }
    public void loveday_ewok_cardless_child_action_grantLovedayQuest(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.clearQuest(player, "loveday_ewok_bundle_of_cards");
        groundquests.grantQuest(player, "loveday_ewok_bundle_of_cards");
        if (hasObjVar(player, "loveday.eligibleBundleOfCards"))
        {
            removeObjVar(player, "loveday.eligibleBundleOfCards");
        }
        return;
    }
    public boolean loveday_ewok_cardless_child_action_sendCompletionSignal(obj_id player, obj_id npc) throws InterruptedException
    {
        obj_id lovedayCardBundle = utils.getStaticItemInInventory(player, "item_event_loveday_card_stack");
        if (isIdValid(lovedayCardBundle))
        {
            if(!destroyObject(lovedayCardBundle)){
                return false;
            }
        }
        else{
            return false;
        }
        groundquests.sendSignal(player, "loveday_ewok_bundle_of_cards_complete");
        if (!hasCompletedCollectionSlot(player, "loveday_2010_card_gathering"))
        {
            modifyCollectionSlotValue(player, "loveday_2010_card_gathering", 1);
        }
        int now = getCalendarTime();
        int secondsUntil = secondsUntilNextDailyTime(4, 0, 0);
        int then = now + secondsUntil;
        setObjVar(player, "loveday.eligibleBundleOfCards", then);
        return true;
    }
    public int loveday_ewok_cardless_child_handleBranch1(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_21"))
        {
            if (loveday_ewok_cardless_child_condition__defaultCondition(player, npc))
            {
                if(!loveday_ewok_cardless_child_action_sendCompletionSignal(player, npc)){
                    // fixes exploit where card deck can be "hidden" and user still gets credit.
                    chat.chat(npc, player, "Please put the cards in your inventory so I can get them.", 0);
                    npcEndConversation(player);
                    return SCRIPT_CONTINUE;
                }
                string_id message = new string_id(c_stringFile, "s_22");
                utils.removeScriptVar(player, "conversation.loveday_ewok_cardless_child.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int loveday_ewok_cardless_child_handleBranch5(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_14"))
        {
            if (loveday_ewok_cardless_child_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "clap_rousing");
                loveday_ewok_cardless_child_action_grantLovedayQuest(player, npc);
                string_id message = new string_id(c_stringFile, "s_16");
                utils.removeScriptVar(player, "conversation.loveday_ewok_cardless_child.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_18"))
        {
            if (loveday_ewok_cardless_child_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "weeping");
                string_id message = new string_id(c_stringFile, "s_20");
                utils.removeScriptVar(player, "conversation.loveday_ewok_cardless_child.branchId");
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
            detachScript(self, "conversation.loveday_ewok_cardless_child");
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
        detachScript(self, "conversation.loveday_ewok_cardless_child");
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
        if (loveday_ewok_cardless_child_condition_returningQuestComplete(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_4");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (loveday_ewok_cardless_child_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_21");
                }
                utils.setScriptVar(player, "conversation.loveday_ewok_cardless_child.branchId", 1);
                npcStartConversation(player, npc, "loveday_ewok_cardless_child", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (loveday_ewok_cardless_child_condition_returningQuestIncomplete(player, npc))
        {
            doAnimationAction(npc, "helpme");
            string_id message = new string_id(c_stringFile, "s_8");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        if (loveday_ewok_cardless_child_condition_notYetReadyForAnother(player, npc))
        {
            doAnimationAction(npc, "applause_excited");
            string_id message = new string_id(c_stringFile, "s_10");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        if (loveday_ewok_cardless_child_condition__defaultCondition(player, npc))
        {
            doAnimationAction(npc, "implore");
            string_id message = new string_id(c_stringFile, "s_12");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (loveday_ewok_cardless_child_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            boolean hasResponse1 = false;
            if (loveday_ewok_cardless_child_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_14");
                }
                if (hasResponse1)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_18");
                }
                utils.setScriptVar(player, "conversation.loveday_ewok_cardless_child.branchId", 5);
                npcStartConversation(player, npc, "loveday_ewok_cardless_child", message, responses);
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
        if (!conversationId.equals("loveday_ewok_cardless_child"))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id npc = self;
        int branchId = utils.getIntScriptVar(player, "conversation.loveday_ewok_cardless_child.branchId");
        if (branchId == 1 && loveday_ewok_cardless_child_handleBranch1(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 5 && loveday_ewok_cardless_child_handleBranch5(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        chat.chat(npc, "Error:  Fell through all branches and responses for OnNpcConversationResponse.");
        utils.removeScriptVar(player, "conversation.loveday_ewok_cardless_child.branchId");
        return SCRIPT_CONTINUE;
    }
}
