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
import script.library.static_item;
import script.library.utils;

public class nym_anniversary_dispenser extends script.base_script
{
    public nym_anniversary_dispenser()
    {
    }
    public static String c_stringFile = "conversation/nym_anniversary_dispenser";
    public boolean nym_anniversary_dispenser_condition__defaultCondition(obj_id player, obj_id npc) throws InterruptedException
    {
        return true;
    }
    public boolean nym_anniversary_dispenser_condition_notEligable(obj_id player, obj_id npc) throws InterruptedException
    {
        return true;
    }
    public boolean nym_anniversary_dispenser_condition_newRewardEligable(obj_id player, obj_id npc) throws InterruptedException
    {
        if (hasObjVar(player, "event.last_timed_reward"))
        {
            float lastRewardTime = getFloatObjVar(player, "event.last_timed_reward");
            float rightNow = getGameTime();
            if (rightNow - lastRewardTime > 3600)
            {
                return true;
            }
        }
        return false;
    }
    public boolean nym_anniversary_dispenser_condition_firstRewardEligable(obj_id player, obj_id npc) throws InterruptedException
    {
        if (!hasObjVar(player, "event.last_timed_reward"))
        {
            return true;
        }
        return false;
    }
    public void nym_anniversary_dispenser_action_randomReward(obj_id player, obj_id npc) throws InterruptedException
    {
        String[] itemList = 
        {
            "item_event_dance_party_device_03_01",
            "item_event_firework_show_04_01",
            "item_event_air_cake_01_02",
            "item_event_energy_drink_01_02"
        };
        String itemName = itemList[rand(0, itemList.length)];
        obj_id reward = null;
        obj_id playerInventory = utils.getInventoryContainer(player);
        if (rand(1, 15) != 15)
        {
            reward = static_item.createNewItemFunction(itemName, playerInventory);
        }
        else 
        {
            reward = createObject("object/tangible/furniture/decorative/30th_anniversary_painting_01.iff", playerInventory, "");
        }
        if (exists(reward) && isIdValid(reward))
        {
            float rightNow = getGameTime();
            setObjVar(player, "event.last_timed_reward", rightNow);
        }
    }
    public int nym_anniversary_dispenser_handleBranch1(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_6"))
        {
            if (nym_anniversary_dispenser_condition__defaultCondition(player, npc))
            {
                nym_anniversary_dispenser_action_randomReward(player, npc);
                string_id message = new string_id(c_stringFile, "s_9");
                utils.removeScriptVar(player, "conversation.nym_anniversary_dispenser.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int nym_anniversary_dispenser_handleBranch3(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_12"))
        {
            if (nym_anniversary_dispenser_condition__defaultCondition(player, npc))
            {
                nym_anniversary_dispenser_action_randomReward(player, npc);
                string_id message = new string_id(c_stringFile, "s_14");
                utils.removeScriptVar(player, "conversation.nym_anniversary_dispenser.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int nym_anniversary_dispenser_handleBranch5(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_18"))
        {
            if (nym_anniversary_dispenser_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_20");
                utils.removeScriptVar(player, "conversation.nym_anniversary_dispenser.branchId");
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
            detachScript(self, "conversation.nym_anniversary_dispenser");
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
        detachScript(self, "conversation.nym_anniversary_dispenser");
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
        if (nym_anniversary_dispenser_condition_firstRewardEligable(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_4");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (nym_anniversary_dispenser_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_6");
                }
                utils.setScriptVar(player, "conversation.nym_anniversary_dispenser.branchId", 1);
                npcStartConversation(player, npc, "nym_anniversary_dispenser", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (nym_anniversary_dispenser_condition_newRewardEligable(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_10");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (nym_anniversary_dispenser_condition__defaultCondition(player, npc))
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
                utils.setScriptVar(player, "conversation.nym_anniversary_dispenser.branchId", 3);
                npcStartConversation(player, npc, "nym_anniversary_dispenser", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (nym_anniversary_dispenser_condition__defaultCondition(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_16");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (nym_anniversary_dispenser_condition__defaultCondition(player, npc))
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
                utils.setScriptVar(player, "conversation.nym_anniversary_dispenser.branchId", 5);
                npcStartConversation(player, npc, "nym_anniversary_dispenser", message, responses);
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
        if (!conversationId.equals("nym_anniversary_dispenser"))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id npc = self;
        int branchId = utils.getIntScriptVar(player, "conversation.nym_anniversary_dispenser.branchId");
        if (branchId == 1 && nym_anniversary_dispenser_handleBranch1(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 3 && nym_anniversary_dispenser_handleBranch3(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 5 && nym_anniversary_dispenser_handleBranch5(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        chat.chat(npc, "Error:  Fell through all branches and responses for OnNpcConversationResponse.");
        utils.removeScriptVar(player, "conversation.nym_anniversary_dispenser.branchId");
        return SCRIPT_CONTINUE;
    }
}
