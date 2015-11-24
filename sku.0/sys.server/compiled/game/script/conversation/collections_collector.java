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
import script.library.static_item;
import script.library.sui;
import script.library.utils;

public class collections_collector extends script.base_script
{
    public collections_collector()
    {
    }
    public static String c_stringFile = "conversation/collections_collector";
    public boolean collections_collector_condition__defaultCondition(obj_id player, obj_id npc) throws InterruptedException
    {
        return true;
    }
    public boolean collections_collector_condition_cantGetMore(obj_id player, obj_id npc) throws InterruptedException
    {
        if (hasObjVar(npc, "collection.columnName"))
        {
            String columnName = getStringObjVar(npc, "collection.columnName");
            return collection.checkMaxActive(player, npc, columnName);
        }
        return false;
    }
    public boolean collections_collector_condition_noMoreCollections(obj_id player, obj_id npc) throws InterruptedException
    {
        if (hasObjVar(npc, "collection.columnName"))
        {
            String columnName = getStringObjVar(npc, "collection.columnName");
            return collection.npcHasMoreCollections(player, npc, columnName);
        }
        return false;
    }
    public boolean collections_collector_condition_hasCollectionsToRemove(obj_id player, obj_id npc) throws InterruptedException
    {
        if (hasObjVar(npc, "collection.columnName"))
        {
            String columnName = getStringObjVar(npc, "collection.columnName");
            return collection.npcHasCollectionsToRemove(player, npc, columnName);
        }
        return false;
    }
    public boolean collections_collector_condition_collectorHintEndor(obj_id player, obj_id npc) throws InterruptedException
    {
        String planetName = getCurrentSceneName();
        if (planetName.equals("endor"))
        {
            return false;
        }
        return true;
    }
    public boolean collections_collector_condition_collectorHintTatooine(obj_id player, obj_id npc) throws InterruptedException
    {
        String planetName = getCurrentSceneName();
        if (planetName.equals("tatooine"))
        {
            return false;
        }
        return true;
    }
    public boolean collections_collector_condition_collectorHintYavin4(obj_id player, obj_id npc) throws InterruptedException
    {
        String planetName = getCurrentSceneName();
        if (planetName.equals("yavin4"))
        {
            return false;
        }
        return true;
    }
    public boolean collections_collector_condition_collectorHintAurilia(obj_id player, obj_id npc) throws InterruptedException
    {
        String planetName = getCurrentSceneName();
        int playerLevel = getLevel(player);
        if (planetName.equals("dathomir") || playerLevel < 70)
        {
            return false;
        }
        return true;
    }
    public boolean collections_collector_condition_durniPhotoConvoStart(obj_id player, obj_id npc) throws InterruptedException
    {
        return !hasCompletedCollectionSlot(player, "photo_durni_activation");
    }
    public boolean collections_collector_condition_durniPhotoActive(obj_id player, obj_id npc) throws InterruptedException
    {
        if (hasCompletedCollectionSlot(player, "photo_durni_activation") && !hasCompletedCollection(player, "col_photo_durni_01"))
        {
            return true;
        }
        return false;
    }
    public boolean collections_collector_condition_completedSlots_hasCamera(obj_id player, obj_id npc) throws InterruptedException
    {
        obj_id camera = utils.getStaticItemInInventory(player, "col_photo_camera_02_01");
        if (hasCompletedCollectionSlot(player, "photo_durni_vehement_warrior_01") && hasCompletedCollectionSlot(player, "photo_durni_01") && hasCompletedCollectionSlot(player, "photo_durni_fiendish_01") && hasCompletedCollectionSlot(player, "photo_crazed_durni_01") && isIdValid(camera))
        {
            return true;
        }
        return false;
    }
    public boolean collections_collector_condition_completedSlots_noCamera(obj_id player, obj_id npc) throws InterruptedException
    {
        obj_id camera = utils.getStaticItemInInventory(player, "col_photo_camera_02_01");
        if (hasCompletedCollectionSlot(player, "photo_durni_vehement_warrior_01") && hasCompletedCollectionSlot(player, "photo_durni_01") && hasCompletedCollectionSlot(player, "photo_durni_fiendish_01") && hasCompletedCollectionSlot(player, "photo_crazed_durni_01") && !isIdValid(camera))
        {
            return true;
        }
        return false;
    }
    public void collections_collector_action_showRemovalSui(obj_id player, obj_id npc) throws InterruptedException
    {
        if (hasObjVar(npc, "collection.columnName"))
        {
            String columnName = getStringObjVar(npc, "collection.columnName");
            collection.showNpcCollectionsRemoval(player, npc, columnName);
        }
    }
    public void collections_collector_action_showCollectionSui(obj_id player, obj_id npc) throws InterruptedException
    {
        if (hasObjVar(npc, "collection.columnName"))
        {
            String columnName = getStringObjVar(npc, "collection.columnName");
            collection.showNpcCollections(player, npc, columnName);
        }
    }
    public void collections_collector_action_grantDurniCamera(obj_id player, obj_id npc) throws InterruptedException
    {
        if (!hasCompletedCollection(player, "col_photo_durni_01") && !hasCompletedCollectionSlot(player, "photo_durni_activation"))
        {
            obj_id itemId = static_item.createNewItemFunction("col_photo_camera_02_01", player);
            if (isValidId(itemId) || exists(itemId))
            {
                CustomerServiceLog("Collection: ", "Player " + getFirstName(player) + "(" + player + ") received " + "col_photo_camera_02_01" + "(" + itemId + ") " + "for the 'col_photo_durni_01' collection.");
                modifyCollectionSlotValue(player, "photo_durni_activation", 1);
            }
        }
    }
    public void collections_collector_action_completeDurniCollection(obj_id player, obj_id npc) throws InterruptedException
    {
        obj_id camera = utils.getStaticItemInInventory(player, "col_photo_camera_02_01");
        if (hasCompletedCollectionSlot(player, "photo_durni_vehement_warrior_01") && hasCompletedCollectionSlot(player, "photo_durni_01") && hasCompletedCollectionSlot(player, "photo_durni_fiendish_01") && hasCompletedCollectionSlot(player, "photo_crazed_durni_01") && isIdValid(camera))
        {
            modifyCollectionSlotValue(player, "photo_durni_completion", 1);
            if (hasCompletedCollection(player, "col_photo_durni_01"))
            {
                detachScript(camera, "item.special.nodestroy");
                destroyObject(camera);
                CustomerServiceLog("Collection: ", "Player " + getFirstName(player) + "(" + player + ") completed the 'col_photo_durni_01' collection. " + "item 'col_photo_camera_02_01'" + "(" + camera + ") " + "was deleted.  *****This deletion is required to finish the collection!*****");
            }
        }
    }
    public int collections_collector_handleBranch1(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_20"))
        {
            if (!collections_collector_condition_noMoreCollections(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_22");
                utils.removeScriptVar(player, "conversation.collections_collector.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
            if (collections_collector_condition_cantGetMore(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_29");
                utils.removeScriptVar(player, "conversation.collections_collector.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
            if (collections_collector_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_18");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (collections_collector_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (collections_collector_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_28");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_33");
                    }
                    utils.setScriptVar(player, "conversation.collections_collector.branchId", 4);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.collections_collector.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_16"))
        {
            if (collections_collector_condition_hasCollectionsToRemove(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_19");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (collections_collector_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (collections_collector_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_25");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_32");
                    }
                    utils.setScriptVar(player, "conversation.collections_collector.branchId", 7);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.collections_collector.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
            if (collections_collector_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_38");
                utils.removeScriptVar(player, "conversation.collections_collector.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_39"))
        {
            if (collections_collector_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_40");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (collections_collector_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_41");
                    }
                    utils.setScriptVar(player, "conversation.collections_collector.branchId", 11);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.collections_collector.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_69"))
        {
            if (collections_collector_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_70");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (collections_collector_condition_completedSlots_hasCamera(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (collections_collector_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (collections_collector_condition_completedSlots_noCamera(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_71");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_72");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_73");
                    }
                    utils.setScriptVar(player, "conversation.collections_collector.branchId", 17);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.collections_collector.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_68"))
        {
            if (collections_collector_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_78");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (collections_collector_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_80");
                    }
                    utils.setScriptVar(player, "conversation.collections_collector.branchId", 21);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.collections_collector.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_88"))
        {
            if (collections_collector_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_90");
                utils.removeScriptVar(player, "conversation.collections_collector.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int collections_collector_handleBranch4(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_28"))
        {
            if (collections_collector_condition__defaultCondition(player, npc))
            {
                collections_collector_action_showCollectionSui(player, npc);
                string_id message = new string_id(c_stringFile, "s_31");
                utils.removeScriptVar(player, "conversation.collections_collector.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_33"))
        {
            if (collections_collector_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_35");
                utils.removeScriptVar(player, "conversation.collections_collector.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int collections_collector_handleBranch7(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_25"))
        {
            if (collections_collector_condition__defaultCondition(player, npc))
            {
                collections_collector_action_showRemovalSui(player, npc);
                string_id message = new string_id(c_stringFile, "s_27");
                utils.removeScriptVar(player, "conversation.collections_collector.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_32"))
        {
            if (collections_collector_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_36");
                utils.removeScriptVar(player, "conversation.collections_collector.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int collections_collector_handleBranch11(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_41"))
        {
            if (collections_collector_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_42");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (collections_collector_condition_collectorHintEndor(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (collections_collector_condition_collectorHintYavin4(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (collections_collector_condition_collectorHintAurilia(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse2 = true;
                }
                boolean hasResponse3 = false;
                if (collections_collector_condition_collectorHintTatooine(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_44");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_47");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_52");
                    }
                    if (hasResponse3)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_56");
                    }
                    utils.setScriptVar(player, "conversation.collections_collector.branchId", 12);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.collections_collector.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int collections_collector_handleBranch12(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_44"))
        {
            if (collections_collector_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_48");
                utils.removeScriptVar(player, "conversation.collections_collector.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_47"))
        {
            if (collections_collector_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_50");
                utils.removeScriptVar(player, "conversation.collections_collector.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_52"))
        {
            if (collections_collector_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_54");
                utils.removeScriptVar(player, "conversation.collections_collector.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_56"))
        {
            if (collections_collector_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_58");
                utils.removeScriptVar(player, "conversation.collections_collector.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int collections_collector_handleBranch17(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_71"))
        {
            if (collections_collector_condition__defaultCondition(player, npc))
            {
                collections_collector_action_completeDurniCollection(player, npc);
                string_id message = new string_id(c_stringFile, "s_74");
                utils.removeScriptVar(player, "conversation.collections_collector.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_72"))
        {
            if (collections_collector_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_75");
                utils.removeScriptVar(player, "conversation.collections_collector.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_73"))
        {
            if (collections_collector_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_76");
                utils.removeScriptVar(player, "conversation.collections_collector.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int collections_collector_handleBranch21(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_80"))
        {
            if (collections_collector_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_82");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (collections_collector_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_84");
                    }
                    utils.setScriptVar(player, "conversation.collections_collector.branchId", 22);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.collections_collector.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int collections_collector_handleBranch22(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_84"))
        {
            if (collections_collector_condition__defaultCondition(player, npc))
            {
                collections_collector_action_grantDurniCamera(player, npc);
                string_id message = new string_id(c_stringFile, "s_86");
                utils.removeScriptVar(player, "conversation.collections_collector.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public static final String PID_NAME = "collection_npc";
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
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        if ((!isTangible(self)) || (isPlayer(self)))
        {
            detachScript(self, "conversation.collections_collector");
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
        detachScript(self, "conversation.collections_collector");
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
        if (collections_collector_condition__defaultCondition(player, npc))
        {
            doAnimationAction(player, "pose_proudly");
            string_id message = new string_id(c_stringFile, "s_10");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (collections_collector_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            boolean hasResponse1 = false;
            if (collections_collector_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse1 = true;
            }
            boolean hasResponse2 = false;
            if (collections_collector_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse2 = true;
            }
            boolean hasResponse3 = false;
            if (collections_collector_condition_durniPhotoActive(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse3 = true;
            }
            boolean hasResponse4 = false;
            if (collections_collector_condition_durniPhotoConvoStart(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse4 = true;
            }
            boolean hasResponse5 = false;
            if (collections_collector_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse5 = true;
            }
            if (hasResponse)
            {
                int responseIndex = 0;
                string_id responses[] = new string_id[numberOfResponses];
                if (hasResponse0)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_20");
                }
                if (hasResponse1)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_16");
                }
                if (hasResponse2)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_39");
                }
                if (hasResponse3)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_69");
                }
                if (hasResponse4)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_68");
                }
                if (hasResponse5)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_88");
                }
                utils.setScriptVar(player, "conversation.collections_collector.branchId", 1);
                npcStartConversation(player, npc, "collections_collector", message, responses);
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
        if (!conversationId.equals("collections_collector"))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id npc = self;
        int branchId = utils.getIntScriptVar(player, "conversation.collections_collector.branchId");
        if (branchId == 1 && collections_collector_handleBranch1(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 4 && collections_collector_handleBranch4(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 7 && collections_collector_handleBranch7(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 11 && collections_collector_handleBranch11(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 12 && collections_collector_handleBranch12(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 17 && collections_collector_handleBranch17(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 21 && collections_collector_handleBranch21(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 22 && collections_collector_handleBranch22(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        chat.chat(npc, "Error:  Fell through all branches and responses for OnNpcConversationResponse.");
        utils.removeScriptVar(player, "conversation.collections_collector.branchId");
        return SCRIPT_CONTINUE;
    }
}
