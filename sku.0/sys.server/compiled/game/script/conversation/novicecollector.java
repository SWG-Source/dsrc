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
import script.library.groundquests;
import script.library.sui;
import script.library.utils;

public class novicecollector extends script.base_script
{
    public novicecollector()
    {
    }
    public static String c_stringFile = "conversation/novicecollector";
    public boolean novicecollector_condition__defaultCondition(obj_id player, obj_id npc) throws InterruptedException
    {
        return true;
    }
    public boolean novicecollector_condition_hasPublishGiftToken(obj_id player, obj_id npc) throws InterruptedException
    {
        return (hasCompletedCollectionSlotPrereq(player, "publish_chapter7_click_activation_03"));
    }
    public boolean novicecollector_condition_hasFoundClickCollection(obj_id player, obj_id npc) throws InterruptedException
    {
        return groundquests.isTaskActive(player, "chapter7_beginning_click_collection", "publishClickCollectionReturnToCollectionNPC");
    }
    public boolean novicecollector_condition_hasCompletedClickCollection(obj_id player, obj_id npc) throws InterruptedException
    {
        return groundquests.hasCompletedQuest(player, "chapter7_beginning_click_collection");
    }
    public boolean novicecollector_condition_hasFoundInventoryCollection(obj_id player, obj_id npc) throws InterruptedException
    {
        if (groundquests.isTaskActive(player, "chapter7_beginning_inventory_collection_01", "returnToCollectorInventoryCollectionComplete"))
        {
            return true;
        }
        else if (groundquests.isTaskActive(player, "chapter7_beginning_inventory_collection_02", "returnToCollectorInventoryCollectionComplete"))
        {
            return true;
        }
        else if (groundquests.isTaskActive(player, "chapter7_beginning_inventory_collection_03", "returnToCollectorInventoryCollectionComplete"))
        {
            return true;
        }
        else if (groundquests.isTaskActive(player, "chapter7_beginning_inventory_collection_04", "returnToCollectorInventoryCollectionComplete"))
        {
            return true;
        }
        return false;
    }
    public boolean novicecollector_condition_hasCompletedClickInventoryCollection(obj_id player, obj_id npc) throws InterruptedException
    {
        if (groundquests.hasCompletedQuest(player, "chapter7_beginning_click_collection") && groundquests.hasCompletedQuest(player, "chapter7_beginning_inventory_collection_01"))
        {
            return true;
        }
        else if (groundquests.hasCompletedQuest(player, "chapter7_beginning_click_collection") && groundquests.hasCompletedQuest(player, "chapter7_beginning_inventory_collection_02"))
        {
            return true;
        }
        else if (groundquests.hasCompletedQuest(player, "chapter7_beginning_click_collection") && groundquests.hasCompletedQuest(player, "chapter7_beginning_inventory_collection_03"))
        {
            return true;
        }
        else if (groundquests.hasCompletedQuest(player, "chapter7_beginning_click_collection") && groundquests.hasCompletedQuest(player, "chapter7_beginning_inventory_collection_04"))
        {
            return true;
        }
        return false;
    }
    public boolean novicecollector_condition_hasFulfilledCounterQuota(obj_id player, obj_id npc) throws InterruptedException
    {
        return groundquests.isTaskActive(player, "chapter7_beginning_counter_collection", "publishCounterReturn");
    }
    public boolean novicecollector_condition_allCollectionsComplete(obj_id player, obj_id npc) throws InterruptedException
    {
        return groundquests.hasCompletedQuest(player, "chapter7_beginning_counter_collection");
    }
    public boolean novicecollector_condition_clickCollectionActive(obj_id player, obj_id npc) throws InterruptedException
    {
        return groundquests.isQuestActive(player, "chapter7_beginning_click_collection");
    }
    public boolean novicecollector_condition_inventoryCollectionActive(obj_id player, obj_id npc) throws InterruptedException
    {
        if (groundquests.isQuestActive(player, "chapter7_beginning_inventory_collection_01") || groundquests.isQuestActive(player, "chapter7_beginning_inventory_collection_02") || groundquests.isQuestActive(player, "chapter7_beginning_inventory_collection_03") || groundquests.isQuestActive(player, "chapter7_beginning_inventory_collection_04"))
        {
            return true;
        }
        return false;
    }
    public boolean novicecollector_condition_counterCollectionActive(obj_id player, obj_id npc) throws InterruptedException
    {
        return (groundquests.isQuestActive(player, "chapter7_beginning_counter_collection"));
    }
    public boolean novicecollector_condition_noMoreCollections(obj_id player, obj_id npc) throws InterruptedException
    {
        if (hasObjVar(npc, "collection.columnName"))
        {
            String columnName = getStringObjVar(npc, "collection.columnName");
            return collection.npcHasMoreCollections(player, npc, columnName);
        }
        return false;
    }
    public boolean novicecollector_condition_cantGetMore(obj_id player, obj_id npc) throws InterruptedException
    {
        if (hasObjVar(npc, "collection.columnName"))
        {
            String columnName = getStringObjVar(npc, "collection.columnName");
            return collection.checkMaxActive(player, npc, columnName);
        }
        return false;
    }
    public boolean novicecollector_condition_isSomehowBroken(obj_id player, obj_id npc) throws InterruptedException
    {
        return (groundquests.isQuestActive(player, "chapter7_beginning_click_collection") && (getCollectionSlotValue(player, "publish_chapter7_click_activation_03") == 0));
    }
    public boolean novicecollector_condition_foundUnusedComlink(obj_id player, obj_id npc) throws InterruptedException
    {
        return utils.playerHasItemByTemplateInBankOrInventory(player, "object/tangible/quest/quest_start/chapter7_publish_gift_collection_token.iff");
    }
    public boolean novicecollector_condition_hasActivatedOne(obj_id player, obj_id npc) throws InterruptedException
    {
        return (getCollectionSlotValue(player, "publish_chapter7_click_activation_01") == 1);
    }
    public boolean novicecollector_condition_hasCollectionsToRemove(obj_id player, obj_id npc) throws InterruptedException
    {
        if (hasObjVar(npc, "collection.columnName"))
        {
            String columnName = getStringObjVar(npc, "collection.columnName");
            return collection.npcHasCollectionsToRemove(player, npc, columnName);
        }
        return false;
    }
    public boolean novicecollector_condition_collectorHintEndor(obj_id player, obj_id npc) throws InterruptedException
    {
        String planetName = getCurrentSceneName();
        sendSystemMessageTestingOnly(player, planetName);
        if (planetName.equals("endor"))
        {
            return false;
        }
        return true;
    }
    public boolean novicecollector_condition_hasFinishedInventoryOutOfOrder(obj_id player, obj_id npc) throws InterruptedException
    {
        if (groundquests.hasCompletedQuest(player, "chapter7_beginning_click_collection"))
        {
            int questNumber = 0;
            boolean hasQuest = false;
            for (int i = 1; i <= 4; i++)
            {
                if (groundquests.isQuestActive(player, "chapter7_beginning_inventory_collection_0" + i))
                {
                    questNumber = i;
                    hasQuest = true;
                }
            }
            if (hasQuest && questNumber > 0)
            {
                if (getCollectionSlotValue(player, "publish_datapad_component_01") == 1)
                {
                    if (groundquests.isTaskActive(player, "chapter7_beginning_inventory_collection_0" + questNumber, "publishInventoryCollectionProcessor"))
                    {
                        return true;
                    }
                }
                if (getCollectionSlotValue(player, "publish_datapad_component_02") == 1)
                {
                    if (groundquests.isTaskActive(player, "chapter7_beginning_inventory_collection_0" + questNumber, "publishInventoryCollectionHousing"))
                    {
                        return true;
                    }
                }
                if (getCollectionSlotValue(player, "publish_datapad_component_03") == 1)
                {
                    if (groundquests.isTaskActive(player, "chapter7_beginning_inventory_collection_0" + questNumber, "publishInventoryCollectionStorageDrive"))
                    {
                        return true;
                    }
                }
            }
        }
        return false;
    }
    public boolean novicecollector_condition_collectorHintTatooine(obj_id player, obj_id npc) throws InterruptedException
    {
        String planetName = getCurrentSceneName();
        sendSystemMessageTestingOnly(player, planetName);
        if (planetName.equals("tatooine"))
        {
            return false;
        }
        return true;
    }
    public boolean novicecollector_condition_collectorHintYavin4(obj_id player, obj_id npc) throws InterruptedException
    {
        String planetName = getCurrentSceneName();
        sendSystemMessageTestingOnly(player, planetName);
        if (planetName.equals("yavin4"))
        {
            return false;
        }
        return true;
    }
    public boolean novicecollector_condition_collectorHintAurilia(obj_id player, obj_id npc) throws InterruptedException
    {
        String planetName = getCurrentSceneName();
        int playerLevel = getLevel(player);
        sendSystemMessageTestingOnly(player, planetName);
        if (planetName.equals("dathomir") || playerLevel < 70)
        {
            return false;
        }
        return true;
    }
    public void novicecollector_action_grantCollectionQuest(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.sendSignal(player, "publishClickCollectionActivation");
        modifyCollectionSlotValue(player, "publish_chapter7_click_activation_03", 1);
        groundquests.grantQuest(player, "chapter7_beginning_click_collection");
    }
    public void novicecollector_action_completeClickCollection(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.sendSignal(player, "publishClickCollectionReturnToCollectionNPC");
        if (hasObjVar(player, "publish_chapter7_click_activated"))
        {
            groundquests.grantQuest(player, "chapter7_give_comlink_reward");
        }
    }
    public void novicecollector_action_grantInventoryQuest(obj_id player, obj_id npc) throws InterruptedException
    {
        modifyCollectionSlotValue(player, "publish_chapter7_activation_02", 1);
        boolean giveQuest = true;
        for (int i = 1; i <= 4; i++)
        {
            if (groundquests.isQuestActiveOrComplete(player, "chapter7_beginning_inventory_collection_0" + i))
            {
                giveQuest = false;
            }
        }
        if (giveQuest)
        {
            groundquests.grantQuest(player, "chapter7_beginning_inventory_collection_0" + rand(1, 4));
        }
        if (getCollectionSlotValue(player, "publish_datapad_component_01") == 1)
        {
            groundquests.sendSignal(player, "publishInventoryCollectionProcessor");
        }
        if (getCollectionSlotValue(player, "publish_datapad_component_02") == 1)
        {
            groundquests.sendSignal(player, "publishInventoryCollectionHousing");
        }
        if (getCollectionSlotValue(player, "publish_datapad_component_03") == 1)
        {
            groundquests.sendSignal(player, "publishInventoryCollectionStorageDrive");
        }
    }
    public void novicecollector_action_completeInventoryCollection(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.sendSignal(player, "returnToCollectorInventoryCollectionComplete");
        if (hasObjVar(player, "publish_chapter7_click_activated"))
        {
            groundquests.grantQuest(player, "chapter7_give_datapad_reward");
        }
    }
    public void novicecollector_action_grantCounterQuest(obj_id player, obj_id npc) throws InterruptedException
    {
        modifyCollectionSlotValue(player, "kill_tusken_activate", 1);
        groundquests.grantQuest(player, "chapter7_beginning_counter_collection");
    }
    public void novicecollector_action_completeCounterCollection(obj_id player, obj_id npc) throws InterruptedException
    {
        modifyCollectionSlotValue(player, "kill_tusken_complete", 1);
        groundquests.sendSignal(player, "publishCounterReturn");
    }
    public void novicecollector_action_sendActivationSignal(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.sendSignal(player, "publishClickCollectionActivation");
    }
    public void novicecollector_action_showCollectionSui(obj_id player, obj_id npc) throws InterruptedException
    {
        if (hasObjVar(npc, "collection.columnName"))
        {
            String columnName = getStringObjVar(npc, "collection.columnName");
            collection.showNpcCollections(player, npc, columnName);
        }
    }
    public void novicecollector_action_fixPlayer(obj_id player, obj_id npc) throws InterruptedException
    {
        modifyCollectionSlotValue(player, "publish_chapter7_click_activation_01", 1);
        modifyCollectionSlotValue(player, "publish_chapter7_click_activation_02", 1);
        modifyCollectionSlotValue(player, "publish_chapter7_click_activation_03", 1);
    }
    public void novicecollector_action_tutorialNoGifts(obj_id player, obj_id npc) throws InterruptedException
    {
        modifyCollectionSlotValue(player, "publish_chapter7_click_activation_01", 1);
        modifyCollectionSlotValue(player, "publish_chapter7_click_activation_02", 1);
        modifyCollectionSlotValue(player, "publish_chapter7_click_activation_03", 1);
        groundquests.grantQuest(player, "chapter7_beginning_click_collection");
    }
    public void novicecollector_action_showRemovalSui(obj_id player, obj_id npc) throws InterruptedException
    {
        if (hasObjVar(npc, "collection.columnName"))
        {
            String columnName = getStringObjVar(npc, "collection.columnName");
            collection.showNpcCollectionsRemoval(player, npc, columnName);
        }
    }
    public void novicecollector_action_collectionQuestSignal(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.sendSignal(player, "grantCollectionRewardSignal");
    }
    public int novicecollector_handleBranch2(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_57"))
        {
            if (!novicecollector_condition_noMoreCollections(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_58");
                utils.removeScriptVar(player, "conversation.novicecollector.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
            if (novicecollector_condition_cantGetMore(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_59");
                utils.removeScriptVar(player, "conversation.novicecollector.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
            if (novicecollector_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_60");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (novicecollector_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (novicecollector_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_61");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_63");
                    }
                    utils.setScriptVar(player, "conversation.novicecollector.branchId", 5);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.novicecollector.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_95"))
        {
            if (novicecollector_condition_hasCollectionsToRemove(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_96");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (novicecollector_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (novicecollector_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_97");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_98");
                    }
                    utils.setScriptVar(player, "conversation.novicecollector.branchId", 8);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.novicecollector.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
            if (novicecollector_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_101");
                utils.removeScriptVar(player, "conversation.novicecollector.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_111"))
        {
            if (novicecollector_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_112");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (novicecollector_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_113");
                    }
                    utils.setScriptVar(player, "conversation.novicecollector.branchId", 12);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.novicecollector.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_65"))
        {
            if (novicecollector_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_66");
                utils.removeScriptVar(player, "conversation.novicecollector.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int novicecollector_handleBranch5(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_61"))
        {
            if (novicecollector_condition__defaultCondition(player, npc))
            {
                novicecollector_action_showCollectionSui(player, npc);
                string_id message = new string_id(c_stringFile, "s_62");
                utils.removeScriptVar(player, "conversation.novicecollector.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_63"))
        {
            if (novicecollector_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_64");
                utils.removeScriptVar(player, "conversation.novicecollector.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int novicecollector_handleBranch8(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_97"))
        {
            if (novicecollector_condition__defaultCondition(player, npc))
            {
                novicecollector_action_showRemovalSui(player, npc);
                string_id message = new string_id(c_stringFile, "s_99");
                utils.removeScriptVar(player, "conversation.novicecollector.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_98"))
        {
            if (novicecollector_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_100");
                utils.removeScriptVar(player, "conversation.novicecollector.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int novicecollector_handleBranch12(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_113"))
        {
            if (novicecollector_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_114");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (novicecollector_condition_collectorHintEndor(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (novicecollector_condition_collectorHintYavin4(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (novicecollector_condition_collectorHintAurilia(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse2 = true;
                }
                boolean hasResponse3 = false;
                if (novicecollector_condition_collectorHintTatooine(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_115");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_116");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_117");
                    }
                    if (hasResponse3)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_118");
                    }
                    utils.setScriptVar(player, "conversation.novicecollector.branchId", 13);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.novicecollector.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int novicecollector_handleBranch13(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_115"))
        {
            if (novicecollector_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_119");
                utils.removeScriptVar(player, "conversation.novicecollector.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_116"))
        {
            if (novicecollector_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_120");
                utils.removeScriptVar(player, "conversation.novicecollector.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_117"))
        {
            if (novicecollector_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_121");
                utils.removeScriptVar(player, "conversation.novicecollector.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_118"))
        {
            if (novicecollector_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_122");
                utils.removeScriptVar(player, "conversation.novicecollector.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int novicecollector_handleBranch19(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_53"))
        {
            if (novicecollector_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_54");
                utils.removeScriptVar(player, "conversation.novicecollector.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int novicecollector_handleBranch22(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_43"))
        {
            if (novicecollector_condition__defaultCondition(player, npc))
            {
                novicecollector_action_grantCounterQuest(player, npc);
                string_id message = new string_id(c_stringFile, "s_45");
                utils.removeScriptVar(player, "conversation.novicecollector.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_47"))
        {
            if (novicecollector_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_49");
                utils.removeScriptVar(player, "conversation.novicecollector.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int novicecollector_handleBranch28(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_71"))
        {
            if (novicecollector_condition__defaultCondition(player, npc))
            {
                novicecollector_action_grantInventoryQuest(player, npc);
                string_id message = new string_id(c_stringFile, "s_73");
                utils.removeScriptVar(player, "conversation.novicecollector.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_75"))
        {
            if (novicecollector_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_78");
                utils.removeScriptVar(player, "conversation.novicecollector.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int novicecollector_handleBranch33(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_86"))
        {
            if (novicecollector_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_88");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (novicecollector_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (novicecollector_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_90");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_94");
                    }
                    utils.setScriptVar(player, "conversation.novicecollector.branchId", 34);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.novicecollector.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int novicecollector_handleBranch34(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_90"))
        {
            if (novicecollector_condition__defaultCondition(player, npc))
            {
                novicecollector_action_grantCollectionQuest(player, npc);
                string_id message = new string_id(c_stringFile, "s_92");
                utils.removeScriptVar(player, "conversation.novicecollector.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_94"))
        {
            if (novicecollector_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_103");
                utils.removeScriptVar(player, "conversation.novicecollector.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int novicecollector_handleBranch39(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_124"))
        {
            if (novicecollector_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_126");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (novicecollector_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (novicecollector_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_128");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_132");
                    }
                    utils.setScriptVar(player, "conversation.novicecollector.branchId", 40);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.novicecollector.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int novicecollector_handleBranch40(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_128"))
        {
            if (novicecollector_condition__defaultCondition(player, npc))
            {
                novicecollector_action_tutorialNoGifts(player, npc);
                string_id message = new string_id(c_stringFile, "s_130");
                utils.removeScriptVar(player, "conversation.novicecollector.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_132"))
        {
            if (novicecollector_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_134");
                utils.removeScriptVar(player, "conversation.novicecollector.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public static final String PID_NAME = "collection_npc";
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        if ((!isTangible(self)) || (isPlayer(self)))
        {
            detachScript(self, "conversation.novicecollector");
        }
        setCondition(self, CONDITION_CONVERSABLE);
        return SCRIPT_CONTINUE;
    }
    public int handleCollectionNpc(obj_id self, dictionary params) throws InterruptedException
    {
        int btn = sui.getIntButtonPressed(params);
        int idx = sui.getListboxSelectedRow(params);
        obj_id player = sui.getPlayerId(params);
        if (btn == sui.BP_CANCEL || idx < 0)
        {
            sui.removePid(player, PID_NAME);
            return SCRIPT_CONTINUE;
        }
        if (!sui.hasPid(player, PID_NAME))
        {
            return SCRIPT_CONTINUE;
        }
        if (btn == sui.BP_OK)
        {
            if (hasObjVar(self, "collection.columnName"))
            {
                String[] availableCollections = utils.getStringArrayScriptVar(player, "collection.allCollections");
                String selectedCollection = availableCollections[idx];
                collection.findAndGrantSlot(player, self, selectedCollection);
                sui.removePid(player, PID_NAME);
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int handleCollectionRemoval(obj_id self, dictionary params) throws InterruptedException
    {
        int btn = sui.getIntButtonPressed(params);
        int idx = sui.getListboxSelectedRow(params);
        obj_id player = sui.getPlayerId(params);
        if (btn == sui.BP_CANCEL || idx < 0)
        {
            sui.removePid(player, PID_NAME);
            return SCRIPT_CONTINUE;
        }
        if (!sui.hasPid(player, PID_NAME))
        {
            return SCRIPT_CONTINUE;
        }
        if (btn == sui.BP_OK)
        {
            if (hasObjVar(self, "collection.columnName"))
            {
                String[] availableCollections = utils.getStringArrayScriptVar(player, "collection.allCollections");
                String selectedCollection = availableCollections[idx];
                utils.setScriptVar(player, "collection.selectedToDelete", selectedCollection);
                int pid = sui.msgbox(self, player, "@collection:confirm_delete_prompt", sui.YES_NO, "@collection:confirm_delete_title", "handlePlayerConfirmedCollectionDelete");
                sui.setPid(player, pid, PID_NAME);
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int handlePlayerConfirmedCollectionDelete(obj_id self, dictionary params) throws InterruptedException
    {
        int btn = sui.getIntButtonPressed(params);
        obj_id player = sui.getPlayerId(params);
        if (btn == sui.BP_CANCEL)
        {
            sui.removePid(player, PID_NAME);
            return SCRIPT_CONTINUE;
        }
        if (!sui.hasPid(player, PID_NAME))
        {
            return SCRIPT_CONTINUE;
        }
        if (btn == sui.BP_OK)
        {
            if (utils.hasScriptVar(player, "collection.selectedToDelete"))
            {
                String selectedCollection = utils.getStringScriptVar(player, "collection.selectedToDelete");
                collection.removeCollection(player, selectedCollection);
                sui.removePid(player, PID_NAME);
            }
        }
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
        detachScript(self, "conversation.novicecollector");
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
        if (novicecollector_condition_isSomehowBroken(player, npc))
        {
            novicecollector_action_fixPlayer(player, npc);
            string_id message = new string_id(c_stringFile, "s_77");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        if (novicecollector_condition_allCollectionsComplete(player, npc))
        {
            doAnimationAction(npc, "goodbye");
            novicecollector_action_collectionQuestSignal(player, npc);
            string_id message = new string_id(c_stringFile, "s_37");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (novicecollector_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            boolean hasResponse1 = false;
            if (novicecollector_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse1 = true;
            }
            boolean hasResponse2 = false;
            if (novicecollector_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse2 = true;
            }
            boolean hasResponse3 = false;
            if (novicecollector_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_57");
                }
                if (hasResponse1)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_95");
                }
                if (hasResponse2)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_111");
                }
                if (hasResponse3)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_65");
                }
                utils.setScriptVar(player, "conversation.novicecollector.branchId", 2);
                npcStartConversation(player, npc, "novicecollector", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (novicecollector_condition_hasFulfilledCounterQuota(player, npc))
        {
            doAnimationAction(npc, "applause_polite");
            doAnimationAction(player, "bow");
            novicecollector_action_completeCounterCollection(player, npc);
            string_id message = new string_id(c_stringFile, "s_36");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (novicecollector_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_53");
                }
                utils.setScriptVar(player, "conversation.novicecollector.branchId", 19);
                npcStartConversation(player, npc, "novicecollector", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (novicecollector_condition_counterCollectionActive(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_51");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        if (novicecollector_condition_hasCompletedClickInventoryCollection(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_41");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (novicecollector_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            boolean hasResponse1 = false;
            if (novicecollector_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_43");
                }
                if (hasResponse1)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_47");
                }
                utils.setScriptVar(player, "conversation.novicecollector.branchId", 22);
                npcStartConversation(player, npc, "novicecollector", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (novicecollector_condition_hasFoundInventoryCollection(player, npc))
        {
            doAnimationAction(npc, "thumbs_up");
            novicecollector_action_completeInventoryCollection(player, npc);
            string_id message = new string_id(c_stringFile, "s_52");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        if (novicecollector_condition_hasFinishedInventoryOutOfOrder(player, npc))
        {
            novicecollector_action_grantInventoryQuest(player, npc);
            string_id message = new string_id(c_stringFile, "s_110");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        if (novicecollector_condition_inventoryCollectionActive(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_67");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        if (novicecollector_condition_hasCompletedClickCollection(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_69");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (novicecollector_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            boolean hasResponse1 = false;
            if (novicecollector_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_71");
                }
                if (hasResponse1)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_75");
                }
                utils.setScriptVar(player, "conversation.novicecollector.branchId", 28);
                npcStartConversation(player, npc, "novicecollector", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (novicecollector_condition_hasFoundClickCollection(player, npc))
        {
            doAnimationAction(npc, "thumb_up");
            novicecollector_action_completeClickCollection(player, npc);
            string_id message = new string_id(c_stringFile, "s_80");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        if (novicecollector_condition_clickCollectionActive(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_82");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        if (novicecollector_condition_hasPublishGiftToken(player, npc))
        {
            doAnimationAction(npc, "curtsey");
            string_id message = new string_id(c_stringFile, "s_84");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (novicecollector_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_86");
                }
                utils.setScriptVar(player, "conversation.novicecollector.branchId", 33);
                npcStartConversation(player, npc, "novicecollector", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (novicecollector_condition_hasActivatedOne(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_105");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        if (novicecollector_condition_foundUnusedComlink(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_107");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        if (novicecollector_condition__defaultCondition(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_109");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (novicecollector_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_124");
                }
                utils.setScriptVar(player, "conversation.novicecollector.branchId", 39);
                npcStartConversation(player, npc, "novicecollector", message, responses);
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
        if (!conversationId.equals("novicecollector"))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id npc = self;
        int branchId = utils.getIntScriptVar(player, "conversation.novicecollector.branchId");
        if (branchId == 2 && novicecollector_handleBranch2(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 5 && novicecollector_handleBranch5(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 8 && novicecollector_handleBranch8(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 12 && novicecollector_handleBranch12(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 13 && novicecollector_handleBranch13(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 19 && novicecollector_handleBranch19(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 22 && novicecollector_handleBranch22(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 28 && novicecollector_handleBranch28(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 33 && novicecollector_handleBranch33(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 34 && novicecollector_handleBranch34(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 39 && novicecollector_handleBranch39(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 40 && novicecollector_handleBranch40(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        chat.chat(npc, "Error:  Fell through all branches and responses for OnNpcConversationResponse.");
        utils.removeScriptVar(player, "conversation.novicecollector.branchId");
        return SCRIPT_CONTINUE;
    }
}
