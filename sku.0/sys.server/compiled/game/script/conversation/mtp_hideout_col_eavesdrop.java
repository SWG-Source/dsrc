package script.conversation;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.ai_lib;
import script.library.buff;
import script.library.chat;
import script.library.conversation;
import script.library.static_item;
import script.library.utils;

public class mtp_hideout_col_eavesdrop extends script.base_script
{
    public mtp_hideout_col_eavesdrop()
    {
    }
    public static String c_stringFile = "conversation/mtp_hideout_col_eavesdrop";
    public boolean mtp_hideout_col_eavesdrop_condition__defaultCondition(obj_id player, obj_id npc) throws InterruptedException
    {
        return true;
    }
    public boolean mtp_hideout_col_eavesdrop_condition_activeOrCompleteCollection(obj_id player, obj_id npc) throws InterruptedException
    {
        if (hasCompletedCollection(player, "col_meatlump_eavesdrop") || hasCompletedCollectionSlotPrereq(player, "eavesdrop_location_1"))
        {
            return false;
        }
        return true;
    }
    public boolean mtp_hideout_col_eavesdrop_condition_collectionActive(obj_id player, obj_id npc) throws InterruptedException
    {
        if (!hasCompletedCollection(player, "col_meatlump_eavesdrop") && hasCompletedCollectionSlotPrereq(player, "eavesdrop_location_1"))
        {
            return true;
        }
        return false;
    }
    public boolean mtp_hideout_col_eavesdrop_condition_readyForTurnIn(obj_id player, obj_id npc) throws InterruptedException
    {
        for (int k = 0; k < BUG_LOCATIONS.length; k++)
        {
            if (!hasCompletedCollectionSlot(player, BUG_LOCATIONS[k]))
            {
                return false;
            }
        }
        obj_id bug_jar = utils.getStaticItemInInventory(player, "col_listening_device_02_01");
        if (!isIdValid(bug_jar))
        {
            return false;
        }
        return true;
    }
    public boolean mtp_hideout_col_eavesdrop_condition_collectionComplete(obj_id player, obj_id npc) throws InterruptedException
    {
        if (hasCompletedCollection(player, "col_meatlump_eavesdrop"))
        {
            return true;
        }
        return false;
    }
    public boolean mtp_hideout_col_eavesdrop_condition_hasLockoutBuff(obj_id player, obj_id npc) throws InterruptedException
    {
        if (buff.hasBuff(player, "mtp_eavesdrop_lockout"))
        {
            return false;
        }
        return true;
    }
    public void mtp_hideout_col_eavesdrop_action_grantBugJar(obj_id player, obj_id npc) throws InterruptedException
    {
        obj_id pInv = utils.getInventoryContainer(player);
        obj_id bug_jar = static_item.createNewItemFunction("col_listening_device_02_01", pInv);
        modifyCollectionSlotValue(player, "meatlump_eavesdrop_start", 1);
        if (!isIdValid(bug_jar) || !exists(bug_jar))
        {
        }
    }
    public void mtp_hideout_col_eavesdrop_action_returnBugJar(obj_id player, obj_id npc) throws InterruptedException
    {
        obj_id bug_jar = utils.getStaticItemInInventory(player, "col_listening_device_02_01");
        if (!isIdValid(bug_jar))
        {
            return;
        }
        if (!hasCompletedCollectionSlotPrereq(player, "eavesdrop_location_1"))
        {
            return;
        }
        for (int j = 0; j < BUG_LOCATIONS.length; j++)
        {
            if (!hasCompletedCollectionSlot(player, BUG_LOCATIONS[j]))
            {
                return;
            }
        }
        modifyCollectionSlotValue(player, "meatlump_eavesdrop_finish", 1);
        if (hasCompletedCollection(player, "col_meatlump_eavesdrop"))
        {
            detachScript(bug_jar, "item.special.nodestroy");
            destroyObject(bug_jar);
            CustomerServiceLog("Collection: ", "Player " + getFirstName(player) + "(" + player + ") completed the 'meatlump photo' collection. " + "item 'col_listening_device_02_01'" + "(" + bug_jar + ") " + "was deleted.  *****This deletion is required to finish the collection!*****");
            buff.applyBuff(player, "mtp_eavesdrop_lockout");
        }
    }
    public void mtp_hideout_col_eavesdrop_action_clearCollectionGrantNew(obj_id player, obj_id npc) throws InterruptedException
    {
        String[] completedSlots = getCompletedCollectionSlotsInCollection(player, "col_meatlump_eavesdrop");
        for (int q = 0; q < completedSlots.length; q++)
        {
            long value = getCollectionSlotValue(player, completedSlots[q]);
            modifyCollectionSlotValue(player, completedSlots[q], value * -1);
        }
        obj_id pInv = utils.getInventoryContainer(player);
        obj_id bug_jar = static_item.createNewItemFunction("col_listening_device_02_01", pInv);
        modifyCollectionSlotValue(player, "meatlump_eavesdrop_start", 1);
        if (!isIdValid(bug_jar) || !exists(bug_jar))
        {
        }
    }
    public int mtp_hideout_col_eavesdrop_handleBranch1(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_5"))
        {
            if (mtp_hideout_col_eavesdrop_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_27");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (mtp_hideout_col_eavesdrop_condition__defaultCondition(player, npc))
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
                    utils.setScriptVar(player, "conversation.mtp_hideout_col_eavesdrop.branchId", 2);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.mtp_hideout_col_eavesdrop.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_6"))
        {
            if (mtp_hideout_col_eavesdrop_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_41");
                utils.removeScriptVar(player, "conversation.mtp_hideout_col_eavesdrop.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_42"))
        {
            if (mtp_hideout_col_eavesdrop_condition__defaultCondition(player, npc))
            {
                mtp_hideout_col_eavesdrop_action_returnBugJar(player, npc);
                string_id message = new string_id(c_stringFile, "s_43");
                utils.removeScriptVar(player, "conversation.mtp_hideout_col_eavesdrop.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_44"))
        {
            if (mtp_hideout_col_eavesdrop_condition_hasLockoutBuff(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_45");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (mtp_hideout_col_eavesdrop_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_46");
                    }
                    utils.setScriptVar(player, "conversation.mtp_hideout_col_eavesdrop.branchId", 10);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.mtp_hideout_col_eavesdrop.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
            if (!mtp_hideout_col_eavesdrop_condition_hasLockoutBuff(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_51");
                utils.removeScriptVar(player, "conversation.mtp_hideout_col_eavesdrop.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int mtp_hideout_col_eavesdrop_handleBranch2(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_28"))
        {
            if (mtp_hideout_col_eavesdrop_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_29");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (mtp_hideout_col_eavesdrop_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_31");
                    }
                    utils.setScriptVar(player, "conversation.mtp_hideout_col_eavesdrop.branchId", 3);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.mtp_hideout_col_eavesdrop.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int mtp_hideout_col_eavesdrop_handleBranch3(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_31"))
        {
            if (mtp_hideout_col_eavesdrop_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_33");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (mtp_hideout_col_eavesdrop_condition__defaultCondition(player, npc))
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
                    utils.setScriptVar(player, "conversation.mtp_hideout_col_eavesdrop.branchId", 4);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.mtp_hideout_col_eavesdrop.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int mtp_hideout_col_eavesdrop_handleBranch4(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_34"))
        {
            if (mtp_hideout_col_eavesdrop_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_35");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (mtp_hideout_col_eavesdrop_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_36");
                    }
                    utils.setScriptVar(player, "conversation.mtp_hideout_col_eavesdrop.branchId", 5);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.mtp_hideout_col_eavesdrop.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int mtp_hideout_col_eavesdrop_handleBranch5(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_36"))
        {
            if (mtp_hideout_col_eavesdrop_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_37");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (mtp_hideout_col_eavesdrop_condition__defaultCondition(player, npc))
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
                    utils.setScriptVar(player, "conversation.mtp_hideout_col_eavesdrop.branchId", 6);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.mtp_hideout_col_eavesdrop.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int mtp_hideout_col_eavesdrop_handleBranch6(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_38"))
        {
            if (mtp_hideout_col_eavesdrop_condition__defaultCondition(player, npc))
            {
                mtp_hideout_col_eavesdrop_action_grantBugJar(player, npc);
                string_id message = new string_id(c_stringFile, "s_49");
                utils.removeScriptVar(player, "conversation.mtp_hideout_col_eavesdrop.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int mtp_hideout_col_eavesdrop_handleBranch10(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_46"))
        {
            if (mtp_hideout_col_eavesdrop_condition__defaultCondition(player, npc))
            {
                mtp_hideout_col_eavesdrop_action_clearCollectionGrantNew(player, npc);
                string_id message = new string_id(c_stringFile, "s_50");
                utils.removeScriptVar(player, "conversation.mtp_hideout_col_eavesdrop.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public static final String[] BUG_LOCATIONS = 
    {
        "eavesdrop_location_1",
        "eavesdrop_location_2",
        "eavesdrop_location_3",
        "eavesdrop_location_4",
        "eavesdrop_location_5"
    };
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        if ((!isTangible(self)) || (isPlayer(self)))
        {
            detachScript(self, "conversation.mtp_hideout_col_eavesdrop");
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
        detachScript(self, "conversation.mtp_hideout_col_eavesdrop");
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
        if (mtp_hideout_col_eavesdrop_condition__defaultCondition(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_4");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (mtp_hideout_col_eavesdrop_condition_activeOrCompleteCollection(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            boolean hasResponse1 = false;
            if (mtp_hideout_col_eavesdrop_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse1 = true;
            }
            boolean hasResponse2 = false;
            if (mtp_hideout_col_eavesdrop_condition_readyForTurnIn(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse2 = true;
            }
            boolean hasResponse3 = false;
            if (mtp_hideout_col_eavesdrop_condition_collectionComplete(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_5");
                }
                if (hasResponse1)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_6");
                }
                if (hasResponse2)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_42");
                }
                if (hasResponse3)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_44");
                }
                utils.setScriptVar(player, "conversation.mtp_hideout_col_eavesdrop.branchId", 1);
                npcStartConversation(player, npc, "mtp_hideout_col_eavesdrop", message, responses);
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
        if (!conversationId.equals("mtp_hideout_col_eavesdrop"))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id npc = self;
        int branchId = utils.getIntScriptVar(player, "conversation.mtp_hideout_col_eavesdrop.branchId");
        if (branchId == 1 && mtp_hideout_col_eavesdrop_handleBranch1(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 2 && mtp_hideout_col_eavesdrop_handleBranch2(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 3 && mtp_hideout_col_eavesdrop_handleBranch3(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 4 && mtp_hideout_col_eavesdrop_handleBranch4(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 5 && mtp_hideout_col_eavesdrop_handleBranch5(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 6 && mtp_hideout_col_eavesdrop_handleBranch6(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 10 && mtp_hideout_col_eavesdrop_handleBranch10(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        chat.chat(npc, "Error:  Fell through all branches and responses for OnNpcConversationResponse.");
        utils.removeScriptVar(player, "conversation.mtp_hideout_col_eavesdrop.branchId");
        return SCRIPT_CONTINUE;
    }
}
