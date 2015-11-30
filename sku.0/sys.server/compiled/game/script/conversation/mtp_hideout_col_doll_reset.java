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

public class mtp_hideout_col_doll_reset extends script.base_script
{
    public mtp_hideout_col_doll_reset()
    {
    }
    public static String c_stringFile = "conversation/mtp_hideout_col_doll_reset";
    public boolean mtp_hideout_col_doll_reset_condition__defaultCondition(obj_id player, obj_id npc) throws InterruptedException
    {
        return true;
    }
    public boolean mtp_hideout_col_doll_reset_condition_hasAllHenchPieces(obj_id player, obj_id npc) throws InterruptedException
    {
        String[] completedSlots = getCompletedCollectionSlotsInCollection(player, "col_meatlump_hench_doll_01");
        if (completedSlots != null && completedSlots.length == 6 && !hasCompletedCollectionSlot(player, "received_hench_doll_tracker"))
        {
            return true;
        }
        return false;
    }
    public boolean mtp_hideout_col_doll_reset_condition_hasAllKingPieces(obj_id player, obj_id npc) throws InterruptedException
    {
        String[] completedSlots = getCompletedCollectionSlotsInCollection(player, "col_meatlump_king_doll_01");
        if (completedSlots != null && completedSlots.length == 6 && !hasCompletedCollectionSlot(player, "received_king_doll_tracker"))
        {
            return true;
        }
        return false;
    }
    public boolean mtp_hideout_col_doll_reset_condition_hasAllOfficerPieces(obj_id player, obj_id npc) throws InterruptedException
    {
        String[] completedSlots = getCompletedCollectionSlotsInCollection(player, "col_meatlump_officer_doll_01");
        if (completedSlots != null && completedSlots.length == 6 && !hasCompletedCollectionSlot(player, "received_officer_doll_tracker"))
        {
            return true;
        }
        return false;
    }
    public boolean mtp_hideout_col_doll_reset_condition_henchColComplete(obj_id player, obj_id npc) throws InterruptedException
    {
        if (hasCompletedCollection(player, "col_meatlump_hench_doll_01"))
        {
            return true;
        }
        return false;
    }
    public boolean mtp_hideout_col_doll_reset_condition_officerColComplete(obj_id player, obj_id npc) throws InterruptedException
    {
        if (hasCompletedCollection(player, "col_meatlump_officer_doll_01"))
        {
            return true;
        }
        return false;
    }
    public boolean mtp_hideout_col_doll_reset_condition_kingColComplete(obj_id player, obj_id npc) throws InterruptedException
    {
        if (hasCompletedCollection(player, "col_meatlump_king_doll_01"))
        {
            return true;
        }
        return false;
    }
    public boolean mtp_hideout_col_doll_reset_condition_noDollHench(obj_id player, obj_id npc) throws InterruptedException
    {
        String[] completedSlots = getCompletedCollectionSlotsInCollection(player, "col_meatlump_hench_doll_01");
        if (completedSlots != null && completedSlots.length == 6)
        {
            if (hasCompletedCollectionSlot(player, "received_hench_doll_tracker"))
            {
                return true;
            }
        }
        return false;
    }
    public boolean mtp_hideout_col_doll_reset_condition_noDollOfficer(obj_id player, obj_id npc) throws InterruptedException
    {
        String[] completedSlots = getCompletedCollectionSlotsInCollection(player, "col_meatlump_officer_doll_01");
        if (completedSlots != null && completedSlots.length == 6)
        {
            if (hasCompletedCollectionSlot(player, "received_officer_doll_tracker"))
            {
                return true;
            }
        }
        return false;
    }
    public boolean mtp_hideout_col_doll_reset_condition_noDollKing(obj_id player, obj_id npc) throws InterruptedException
    {
        String[] completedSlots = getCompletedCollectionSlotsInCollection(player, "col_meatlump_king_doll_01");
        if (completedSlots != null && completedSlots.length == 6)
        {
            if (hasCompletedCollectionSlot(player, "received_king_doll_tracker"))
            {
                return true;
            }
        }
        return false;
    }
    public void mtp_hideout_col_doll_reset_action_grantHenchReward(obj_id player, obj_id npc) throws InterruptedException
    {
        modifyCollectionSlotValue(player, "received_hench_doll", 1);
        obj_id pInv = utils.getInventoryContainer(player);
        if (hasCompletedCollectionSlot(player, "received_hench_doll_tracker"))
        {
            obj_id lumps = static_item.createNewItemFunction("item_meatlump_lump_01_01", pInv);
            setCount(lumps, 10);
        }
        else 
        {
            obj_id doll = static_item.createNewItemFunction("col_reward_meatlump_doll_hench_02_01", pInv);
            if (!hasCompletedCollectionSlot(player, "received_hench_doll_tracker"))
            {
                modifyCollectionSlotValue(player, "received_hench_doll_tracker", 1);
            }
        }
    }
    public void mtp_hideout_col_doll_reset_action_grantOfficerReward(obj_id player, obj_id npc) throws InterruptedException
    {
        modifyCollectionSlotValue(player, "received_officer_doll", 1);
        obj_id pInv = utils.getInventoryContainer(player);
        if (hasCompletedCollectionSlot(player, "received_officer_doll_tracker"))
        {
            obj_id lumps = static_item.createNewItemFunction("item_meatlump_lump_01_01", pInv);
            setCount(lumps, 10);
        }
        else 
        {
            obj_id doll = static_item.createNewItemFunction("col_reward_meatlump_doll_officer_02_01", pInv);
            if (!hasCompletedCollectionSlot(player, "received_officer_doll_tracker"))
            {
                modifyCollectionSlotValue(player, "received_officer_doll_tracker", 1);
            }
        }
    }
    public void mtp_hideout_col_doll_reset_action_grantKingReward(obj_id player, obj_id npc) throws InterruptedException
    {
        modifyCollectionSlotValue(player, "received_king_doll", 1);
        obj_id pInv = utils.getInventoryContainer(player);
        if (hasCompletedCollectionSlot(player, "received_king_doll_tracker"))
        {
            obj_id lumps = static_item.createNewItemFunction("item_meatlump_lump_01_01", pInv);
            setCount(lumps, 10);
        }
        else 
        {
            obj_id doll = static_item.createNewItemFunction("col_reward_meatlump_doll_king_02_01", pInv);
            if (!hasCompletedCollectionSlot(player, "received_king_doll_tracker"))
            {
                modifyCollectionSlotValue(player, "received_king_doll_tracker", 1);
            }
        }
    }
    public void mtp_hideout_col_doll_reset_action_resetHenchCollection(obj_id player, obj_id npc) throws InterruptedException
    {
        if (hasCompletedCollection(player, "col_meatlump_hench_doll_01"))
        {
            collection.removeCollectionForRealsies(player, "col_meatlump_hench_doll_01");
        }
    }
    public void mtp_hideout_col_doll_reset_action_resetOfficerCollection(obj_id player, obj_id npc) throws InterruptedException
    {
        if (hasCompletedCollection(player, "col_meatlump_officer_doll_01"))
        {
            collection.removeCollectionForRealsies(player, "col_meatlump_officer_doll_01");
        }
    }
    public void mtp_hideout_col_doll_reset_action_resetKingCollection(obj_id player, obj_id npc) throws InterruptedException
    {
        if (hasCompletedCollection(player, "col_meatlump_king_doll_01"))
        {
            collection.removeCollectionForRealsies(player, "col_meatlump_king_doll_01");
        }
    }
    public int mtp_hideout_col_doll_reset_handleBranch1(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_6"))
        {
            if (mtp_hideout_col_doll_reset_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_8");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (mtp_hideout_col_doll_reset_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (mtp_hideout_col_doll_reset_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_44");
                    }
                    utils.setScriptVar(player, "conversation.mtp_hideout_col_doll_reset.branchId", 2);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.mtp_hideout_col_doll_reset.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_15"))
        {
            if (mtp_hideout_col_doll_reset_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_17");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (mtp_hideout_col_doll_reset_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (mtp_hideout_col_doll_reset_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_45");
                    }
                    utils.setScriptVar(player, "conversation.mtp_hideout_col_doll_reset.branchId", 5);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.mtp_hideout_col_doll_reset.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_25"))
        {
            if (mtp_hideout_col_doll_reset_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_27");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (mtp_hideout_col_doll_reset_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (mtp_hideout_col_doll_reset_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_29");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_48");
                    }
                    utils.setScriptVar(player, "conversation.mtp_hideout_col_doll_reset.branchId", 8);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.mtp_hideout_col_doll_reset.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_38"))
        {
            if (mtp_hideout_col_doll_reset_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_40");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (mtp_hideout_col_doll_reset_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (mtp_hideout_col_doll_reset_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_42");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_66");
                    }
                    utils.setScriptVar(player, "conversation.mtp_hideout_col_doll_reset.branchId", 13);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.mtp_hideout_col_doll_reset.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_70"))
        {
            if (mtp_hideout_col_doll_reset_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_72");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (mtp_hideout_col_doll_reset_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_74");
                    }
                    utils.setScriptVar(player, "conversation.mtp_hideout_col_doll_reset.branchId", 18);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.mtp_hideout_col_doll_reset.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_86"))
        {
            if (mtp_hideout_col_doll_reset_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_88");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (mtp_hideout_col_doll_reset_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_90");
                    }
                    utils.setScriptVar(player, "conversation.mtp_hideout_col_doll_reset.branchId", 22);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.mtp_hideout_col_doll_reset.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_106"))
        {
            if (mtp_hideout_col_doll_reset_condition__defaultCondition(player, npc))
            {
                mtp_hideout_col_doll_reset_action_grantHenchReward(player, npc);
                string_id message = new string_id(c_stringFile, "s_108");
                utils.removeScriptVar(player, "conversation.mtp_hideout_col_doll_reset.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_110"))
        {
            if (mtp_hideout_col_doll_reset_condition__defaultCondition(player, npc))
            {
                mtp_hideout_col_doll_reset_action_grantOfficerReward(player, npc);
                string_id message = new string_id(c_stringFile, "s_112");
                utils.removeScriptVar(player, "conversation.mtp_hideout_col_doll_reset.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_114"))
        {
            if (mtp_hideout_col_doll_reset_condition__defaultCondition(player, npc))
            {
                mtp_hideout_col_doll_reset_action_grantKingReward(player, npc);
                string_id message = new string_id(c_stringFile, "s_116");
                utils.removeScriptVar(player, "conversation.mtp_hideout_col_doll_reset.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_118"))
        {
            if (mtp_hideout_col_doll_reset_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_120");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (mtp_hideout_col_doll_reset_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_122");
                    }
                    utils.setScriptVar(player, "conversation.mtp_hideout_col_doll_reset.branchId", 30);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.mtp_hideout_col_doll_reset.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int mtp_hideout_col_doll_reset_handleBranch2(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_10"))
        {
            if (mtp_hideout_col_doll_reset_condition__defaultCondition(player, npc))
            {
                mtp_hideout_col_doll_reset_action_grantHenchReward(player, npc);
                string_id message = new string_id(c_stringFile, "s_23");
                utils.removeScriptVar(player, "conversation.mtp_hideout_col_doll_reset.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_44"))
        {
            if (mtp_hideout_col_doll_reset_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_47");
                utils.removeScriptVar(player, "conversation.mtp_hideout_col_doll_reset.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int mtp_hideout_col_doll_reset_handleBranch5(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_19"))
        {
            if (mtp_hideout_col_doll_reset_condition__defaultCondition(player, npc))
            {
                mtp_hideout_col_doll_reset_action_grantOfficerReward(player, npc);
                string_id message = new string_id(c_stringFile, "s_43");
                utils.removeScriptVar(player, "conversation.mtp_hideout_col_doll_reset.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_45"))
        {
            if (mtp_hideout_col_doll_reset_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_46");
                utils.removeScriptVar(player, "conversation.mtp_hideout_col_doll_reset.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int mtp_hideout_col_doll_reset_handleBranch8(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_29"))
        {
            if (mtp_hideout_col_doll_reset_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_50");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (mtp_hideout_col_doll_reset_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (mtp_hideout_col_doll_reset_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_51");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_52");
                    }
                    utils.setScriptVar(player, "conversation.mtp_hideout_col_doll_reset.branchId", 9);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.mtp_hideout_col_doll_reset.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_48"))
        {
            if (mtp_hideout_col_doll_reset_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_49");
                utils.removeScriptVar(player, "conversation.mtp_hideout_col_doll_reset.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int mtp_hideout_col_doll_reset_handleBranch9(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_51"))
        {
            if (mtp_hideout_col_doll_reset_condition__defaultCondition(player, npc))
            {
                mtp_hideout_col_doll_reset_action_grantKingReward(player, npc);
                string_id message = new string_id(c_stringFile, "s_54");
                utils.removeScriptVar(player, "conversation.mtp_hideout_col_doll_reset.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_52"))
        {
            if (mtp_hideout_col_doll_reset_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_53");
                utils.removeScriptVar(player, "conversation.mtp_hideout_col_doll_reset.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int mtp_hideout_col_doll_reset_handleBranch13(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_42"))
        {
            if (mtp_hideout_col_doll_reset_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_56");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (mtp_hideout_col_doll_reset_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (mtp_hideout_col_doll_reset_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_58");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_62");
                    }
                    utils.setScriptVar(player, "conversation.mtp_hideout_col_doll_reset.branchId", 14);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.mtp_hideout_col_doll_reset.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_66"))
        {
            if (mtp_hideout_col_doll_reset_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_68");
                utils.removeScriptVar(player, "conversation.mtp_hideout_col_doll_reset.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int mtp_hideout_col_doll_reset_handleBranch14(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_58"))
        {
            if (mtp_hideout_col_doll_reset_condition__defaultCondition(player, npc))
            {
                mtp_hideout_col_doll_reset_action_resetHenchCollection(player, npc);
                string_id message = new string_id(c_stringFile, "s_60");
                utils.removeScriptVar(player, "conversation.mtp_hideout_col_doll_reset.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_62"))
        {
            if (mtp_hideout_col_doll_reset_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_64");
                utils.removeScriptVar(player, "conversation.mtp_hideout_col_doll_reset.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int mtp_hideout_col_doll_reset_handleBranch18(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_74"))
        {
            if (mtp_hideout_col_doll_reset_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_76");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (mtp_hideout_col_doll_reset_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (mtp_hideout_col_doll_reset_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_78");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_82");
                    }
                    utils.setScriptVar(player, "conversation.mtp_hideout_col_doll_reset.branchId", 19);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.mtp_hideout_col_doll_reset.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int mtp_hideout_col_doll_reset_handleBranch19(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_78"))
        {
            if (mtp_hideout_col_doll_reset_condition__defaultCondition(player, npc))
            {
                mtp_hideout_col_doll_reset_action_resetOfficerCollection(player, npc);
                string_id message = new string_id(c_stringFile, "s_80");
                utils.removeScriptVar(player, "conversation.mtp_hideout_col_doll_reset.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_82"))
        {
            if (mtp_hideout_col_doll_reset_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_84");
                utils.removeScriptVar(player, "conversation.mtp_hideout_col_doll_reset.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int mtp_hideout_col_doll_reset_handleBranch22(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_90"))
        {
            if (mtp_hideout_col_doll_reset_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_92");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (mtp_hideout_col_doll_reset_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_94");
                    }
                    utils.setScriptVar(player, "conversation.mtp_hideout_col_doll_reset.branchId", 23);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.mtp_hideout_col_doll_reset.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int mtp_hideout_col_doll_reset_handleBranch23(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_94"))
        {
            if (mtp_hideout_col_doll_reset_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_96");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (mtp_hideout_col_doll_reset_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (mtp_hideout_col_doll_reset_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_98");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_102");
                    }
                    utils.setScriptVar(player, "conversation.mtp_hideout_col_doll_reset.branchId", 24);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.mtp_hideout_col_doll_reset.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int mtp_hideout_col_doll_reset_handleBranch24(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_98"))
        {
            if (mtp_hideout_col_doll_reset_condition__defaultCondition(player, npc))
            {
                mtp_hideout_col_doll_reset_action_resetKingCollection(player, npc);
                string_id message = new string_id(c_stringFile, "s_100");
                utils.removeScriptVar(player, "conversation.mtp_hideout_col_doll_reset.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_102"))
        {
            if (mtp_hideout_col_doll_reset_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_104");
                utils.removeScriptVar(player, "conversation.mtp_hideout_col_doll_reset.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int mtp_hideout_col_doll_reset_handleBranch30(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_122"))
        {
            if (mtp_hideout_col_doll_reset_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_124");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (mtp_hideout_col_doll_reset_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_126");
                    }
                    utils.setScriptVar(player, "conversation.mtp_hideout_col_doll_reset.branchId", 31);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.mtp_hideout_col_doll_reset.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int mtp_hideout_col_doll_reset_handleBranch31(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_126"))
        {
            if (mtp_hideout_col_doll_reset_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_128");
                utils.removeScriptVar(player, "conversation.mtp_hideout_col_doll_reset.branchId");
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
            detachScript(self, "conversation.mtp_hideout_col_doll_reset");
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
        detachScript(self, "conversation.mtp_hideout_col_doll_reset");
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
        if (mtp_hideout_col_doll_reset_condition__defaultCondition(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_4");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (mtp_hideout_col_doll_reset_condition_hasAllHenchPieces(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            boolean hasResponse1 = false;
            if (mtp_hideout_col_doll_reset_condition_hasAllOfficerPieces(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse1 = true;
            }
            boolean hasResponse2 = false;
            if (mtp_hideout_col_doll_reset_condition_hasAllKingPieces(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse2 = true;
            }
            boolean hasResponse3 = false;
            if (mtp_hideout_col_doll_reset_condition_henchColComplete(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse3 = true;
            }
            boolean hasResponse4 = false;
            if (mtp_hideout_col_doll_reset_condition_officerColComplete(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse4 = true;
            }
            boolean hasResponse5 = false;
            if (mtp_hideout_col_doll_reset_condition_kingColComplete(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse5 = true;
            }
            boolean hasResponse6 = false;
            if (mtp_hideout_col_doll_reset_condition_noDollHench(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse6 = true;
            }
            boolean hasResponse7 = false;
            if (mtp_hideout_col_doll_reset_condition_noDollOfficer(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse7 = true;
            }
            boolean hasResponse8 = false;
            if (mtp_hideout_col_doll_reset_condition_noDollKing(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse8 = true;
            }
            boolean hasResponse9 = false;
            if (mtp_hideout_col_doll_reset_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse9 = true;
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_15");
                }
                if (hasResponse2)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_25");
                }
                if (hasResponse3)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_38");
                }
                if (hasResponse4)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_70");
                }
                if (hasResponse5)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_86");
                }
                if (hasResponse6)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_106");
                }
                if (hasResponse7)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_110");
                }
                if (hasResponse8)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_114");
                }
                if (hasResponse9)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_118");
                }
                utils.setScriptVar(player, "conversation.mtp_hideout_col_doll_reset.branchId", 1);
                npcStartConversation(player, npc, "mtp_hideout_col_doll_reset", message, responses);
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
        if (!conversationId.equals("mtp_hideout_col_doll_reset"))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id npc = self;
        int branchId = utils.getIntScriptVar(player, "conversation.mtp_hideout_col_doll_reset.branchId");
        if (branchId == 1 && mtp_hideout_col_doll_reset_handleBranch1(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 2 && mtp_hideout_col_doll_reset_handleBranch2(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 5 && mtp_hideout_col_doll_reset_handleBranch5(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 8 && mtp_hideout_col_doll_reset_handleBranch8(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 9 && mtp_hideout_col_doll_reset_handleBranch9(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 13 && mtp_hideout_col_doll_reset_handleBranch13(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 14 && mtp_hideout_col_doll_reset_handleBranch14(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 18 && mtp_hideout_col_doll_reset_handleBranch18(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 19 && mtp_hideout_col_doll_reset_handleBranch19(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 22 && mtp_hideout_col_doll_reset_handleBranch22(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 23 && mtp_hideout_col_doll_reset_handleBranch23(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 24 && mtp_hideout_col_doll_reset_handleBranch24(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 30 && mtp_hideout_col_doll_reset_handleBranch30(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 31 && mtp_hideout_col_doll_reset_handleBranch31(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        chat.chat(npc, "Error:  Fell through all branches and responses for OnNpcConversationResponse.");
        utils.removeScriptVar(player, "conversation.mtp_hideout_col_doll_reset.branchId");
        return SCRIPT_CONTINUE;
    }
}
