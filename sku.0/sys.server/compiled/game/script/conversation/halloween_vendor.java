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
import script.library.buff;
import script.library.chat;
import script.library.collection;
import script.library.conversation;
import script.library.factions;
import script.library.static_item;
import script.library.utils;

public class halloween_vendor extends script.base_script
{
    public halloween_vendor()
    {
    }
    public static String c_stringFile = "conversation/halloween_vendor";
    public boolean halloween_vendor_condition__defaultCondition(obj_id player, obj_id npc) throws InterruptedException
    {
        return true;
    }
    public boolean halloween_vendor_condition_costumeLockout(obj_id player, obj_id npc) throws InterruptedException
    {
        if (buff.hasBuff(player, "event_halloween_costume_lockout") || !buff.canApplyBuff(player, "event_halloween_costume_jawa"))
        {
            return true;
        }
        return false;
    }
    public boolean halloween_vendor_condition_costumeAlready(obj_id player, obj_id npc) throws InterruptedException
    {
        int[] currentBuffs = buff.getAllBuffs(player);
        if (currentBuffs != null || currentBuffs.length > 0)
        {
            for (int i = 0; i < currentBuffs.length; i++)
            {
                String buffName = buff.getBuffNameFromCrc(currentBuffs[i]);
                if (buffName.startsWith("event_halloween_costume_"))
                {
                    return true;
                }
            }
        }
        return false;
    }
    public boolean halloween_vendor_condition_godMode(obj_id player, obj_id npc) throws InterruptedException
    {
        return (isGod(player));
    }
    public boolean halloween_vendor_condition_doesntHaveThree(obj_id player, obj_id npc) throws InterruptedException
    {
        long collectionValue = getCollectionSlotValue(player, "received_halloween_reward");
        if (collectionValue < 1)
        {
            return true;
        }
        else 
        {
            return false;
        }
    }
    public boolean halloween_vendor_condition_canBuyThree(obj_id player, obj_id npc) throws InterruptedException
    {
        obj_id trickDeviceTwo = utils.getStaticItemInInventory(player, "item_event_halloween_trick_device_02_01");
        obj_id[] coins = utils.getAllStaticItemsInPlayerInventory(player, "item_event_halloween_coin");
        long collectionValue = getCollectionSlotValue(player, "received_halloween_reward");
        if (coins != null && coins.length > 0)
        {
            int numberOfCoins = utils.countOfStackedItemsInArray(coins);
            if (numberOfCoins != 0)
            {
                if (isIdValid(trickDeviceTwo) && exists(trickDeviceTwo) && numberOfCoins >= 700 && collectionValue < 1)
                {
                    return true;
                }
                else 
                {
                    return false;
                }
            }
            else 
            {
                return false;
            }
        }
        else 
        {
            return false;
        }
    }
    public boolean halloween_vendor_condition_hasDevice(obj_id player, obj_id npc) throws InterruptedException
    {
        if (hasCompletedCollectionSlot(player, "received_halloween_reward"))
        {
            return true;
        }
        obj_id inventory = utils.getInventoryContainer(player);
        obj_id[] inventoryContents = getContents(inventory);
        for (int i = 0; i < inventoryContents.length; i++)
        {
            String itemName = getStaticItemName(inventoryContents[i]);
            if (itemName != null && !itemName.equals("") && itemName.startsWith("item_event_halloween_trick_device_"))
            {
                return true;
            }
        }
        return false;
    }
    public boolean halloween_vendor_condition_noBadge(obj_id player, obj_id npc) throws InterruptedException
    {
        if (!hasCompletedCollectionSlot(player, "halloween_badge_09"))
        {
            return true;
        }
        else 
        {
            return false;
        }
    }
    public boolean halloween_vendor_condition_canBuyBadge(obj_id player, obj_id npc) throws InterruptedException
    {
        obj_id[] coins = utils.getAllStaticItemsInPlayerInventory(player, "item_event_halloween_coin");
        if (coins != null && coins.length > 0)
        {
            int numberOfCoins = utils.countOfStackedItemsInArray(coins);
            if (numberOfCoins != 0)
            {
                if (numberOfCoins >= 900 && !hasCompletedCollectionSlot(player, "halloween_badge_09"))
                {
                    return true;
                }
                else 
                {
                    return false;
                }
            }
            else 
            {
                return false;
            }
        }
        else 
        {
            return false;
        }
    }
    public boolean halloween_vendor_condition_playerStealthed(obj_id player, obj_id npc) throws InterruptedException
    {
        int stealth = buff.getBuffOnTargetFromGroup(player, "invisibility");
        if (stealth != 0)
        {
            return true;
        }
        return false;
    }
    public boolean halloween_vendor_condition_outOfRange(obj_id player, obj_id npc) throws InterruptedException
    {
        location selfLoc = getLocation(npc);
        location targetLoc = getLocation(player);
        float fltDistance = getDistance(selfLoc, targetLoc);
        if (fltDistance > 10.0f)
        {
            return true;
        }
        return false;
    }
    public void halloween_vendor_action_showTokenVendorUI(obj_id player, obj_id npc) throws InterruptedException
    {
        dictionary d = new dictionary();
        d.put("player", player);
        messageTo(npc, "showInventorySUI", d, 0, false);
        return;
    }
    public void halloween_vendor_action_applyCostume(obj_id player, obj_id npc) throws InterruptedException
    {
        obj_id inventory = utils.getInventoryContainer(player);
        if (!halloween_vendor_condition_hasDevice(player, npc))
        {
            static_item.createNewItemFunction("item_event_halloween_trick_device_01_01", inventory);
            sendSystemMessage(player, new string_id("event/halloween", "halloween_projector"));
        }
        String[] costumes = 
        {
            "jawa",
            "toydarian",
            "hutt_female",
            "droid",
            "kowakian"
        };
        if (halloween_vendor_condition_costumeAlready(player, npc))
        {
            int[] currentBuffs = buff.getAllBuffs(player);
            for (int i = 0; i < currentBuffs.length; i++)
            {
                String buffName = buff.getBuffNameFromCrc(currentBuffs[i]);
                if (buffName.startsWith("event_halloween_costume_"))
                {
                    int costumeRandom = rand(0, 4);
                    String newCostume = "event_halloween_costume_" + costumes[costumeRandom];
                    if (newCostume != null && !newCostume.equals(""))
                    {
                        obj_id weapon = getObjectInSlot(player, "hold_r");
                        obj_id playerInv = utils.getInventoryContainer(player);
                        if (isIdValid(weapon) && isIdValid(playerInv))
                        {
                            putInOverloaded(weapon, playerInv);
                        }
                        if (buffName.equals(newCostume))
                        {
                            halloween_vendor_action_applyCostume(player, npc);
                        }
                        else 
                        {
                            int halloweenBuff = buff.getBuffOnTargetFromGroup(player, "shapechange");
                            if (halloweenBuff != 0)
                            {
                                buff.removeBuff(player, halloweenBuff);
                            }
                            buff.applyBuff(player, newCostume);
                            buff.applyBuff(player, "event_halloween_costume_lockout");
                        }
                    }
                }
            }
        }
        else 
        {
            obj_id weapon = getObjectInSlot(player, "hold_r");
            obj_id playerInv = utils.getInventoryContainer(player);
            if (isIdValid(weapon) && isIdValid(playerInv))
            {
                putInOverloaded(weapon, playerInv);
            }
            int costumeRandom = rand(0, 4);
            String newCostume = "event_halloween_costume_" + costumes[costumeRandom];
            buff.applyBuff(player, newCostume);
            buff.applyBuff(player, "event_halloween_costume_lockout");
        }
    }
    public void halloween_vendor_action_removeLockout(obj_id player, obj_id npc) throws InterruptedException
    {
        if (buff.hasBuff(player, "event_halloween_costume_lockout"))
        {
            buff.removeBuff(player, "event_halloween_costume_lockout");
        }
        return;
    }
    public void halloween_vendor_action_grantProjectorThree(obj_id player, obj_id npc) throws InterruptedException
    {
        if (halloween_vendor_condition_canBuyThree(player, npc))
        {
            obj_id trickDeviceTwo = utils.getStaticItemInInventory(player, "item_event_halloween_trick_device_02_01");
            obj_id inventory = utils.getInventoryContainer(player);
            obj_id[] inventoryContents = getInventoryAndEquipment(player);
            int tokenCostForReals = 700;
            for (int i = 0; i < inventoryContents.length; i++)
            {
                String itemName = getStaticItemName(inventoryContents[i]);
                if (itemName != null && !itemName.equals(""))
                {
                    String halloweenCoins = "item_event_halloween_coin";
                    if (itemName.equals(halloweenCoins) && tokenCostForReals > 0)
                    {
                        if (getCount(inventoryContents[i]) > 1)
                        {
                            int numInStack = getCount(inventoryContents[i]);
                            for (int m = 0; m < numInStack - 1; m++)
                            {
                                if (tokenCostForReals > 0)
                                {
                                    tokenCostForReals--;
                                    setCount(inventoryContents[i], getCount(inventoryContents[i]) - 1);
                                    if (tokenCostForReals <= 0)
                                    {
                                        static_item.createNewItemFunction("item_event_halloween_trick_device_03_01", inventory);
                                        obj_id trickDeviceThree = utils.getStaticItemInInventory(player, "item_event_halloween_trick_device_03_01");
                                        if (isIdValid(trickDeviceThree) && exists(trickDeviceThree))
                                        {
                                            setObjVar(trickDeviceThree, "deviceOwner", player);
                                            destroyObject(trickDeviceTwo);
                                            modifyCollectionSlotValue(player, "received_halloween_reward", 1);
                                        }
                                    }
                                }
                            }
                        }
                        if (getCount(inventoryContents[i]) <= 1 && tokenCostForReals > 0)
                        {
                            destroyObject(inventoryContents[i]);
                            tokenCostForReals--;
                            if (tokenCostForReals <= 0)
                            {
                                static_item.createNewItemFunction("item_event_halloween_trick_device_03_01", inventory);
                                obj_id trickDeviceThree = utils.getStaticItemInInventory(player, "item_event_halloween_trick_device_03_01");
                                if (isIdValid(trickDeviceThree) && exists(trickDeviceThree))
                                {
                                    destroyObject(trickDeviceTwo);
                                    modifyCollectionSlotValue(player, "received_halloween_reward", 1);
                                }
                            }
                        }
                    }
                }
            }
        }
    }
    public void halloween_vendor_action_grantCoins(obj_id player, obj_id npc) throws InterruptedException
    {
        obj_id pInv = utils.getInventoryContainer(player);
        obj_id halloweenCoins = utils.getStaticItemInInventory(player, "item_event_halloween_coin");
        if (isIdValid(halloweenCoins))
        {
            int currentCoins = getCount(halloweenCoins);
            setCount(halloweenCoins, currentCoins + 5000);
        }
        else 
        {
            obj_id coins = static_item.createNewItemFunction("item_event_halloween_coin", pInv, 5000);
        }
    }
    public void halloween_vendor_action_removeProjectorLockout(obj_id player, obj_id npc) throws InterruptedException
    {
        if (hasCompletedCollectionSlot(player, "received_halloween_reward"))
        {
            modifyCollectionSlotValue(player, "received_halloween_reward", -1);
        }
    }
    public void halloween_vendor_action_badgePurchased(obj_id player, obj_id npc) throws InterruptedException
    {
        obj_id inventory = utils.getInventoryContainer(player);
        obj_id[] inventoryContents = getInventoryAndEquipment(player);
        int tokenCostForReals = 900;
        for (int i = 0; i < inventoryContents.length; i++)
        {
            String itemName = getStaticItemName(inventoryContents[i]);
            if (itemName != null && !itemName.equals(""))
            {
                String halloweenCoins = "item_event_halloween_coin";
                if (itemName.equals(halloweenCoins) && tokenCostForReals > 0)
                {
                    if (getCount(inventoryContents[i]) > 1)
                    {
                        int numInStack = getCount(inventoryContents[i]);
                        for (int m = 0; m < numInStack - 1; m++)
                        {
                            if (tokenCostForReals > 0)
                            {
                                tokenCostForReals--;
                                setCount(inventoryContents[i], getCount(inventoryContents[i]) - 1);
                                if (tokenCostForReals <= 0)
                                {
                                    badge.grantBadge(player, "halloween_badge_09");
                                }
                            }
                        }
                    }
                    if (getCount(inventoryContents[i]) <= 1 && tokenCostForReals > 0)
                    {
                        destroyObject(inventoryContents[i]);
                        tokenCostForReals--;
                        if (tokenCostForReals <= 0)
                        {
                            badge.grantBadge(player, "halloween_badge_09");
                        }
                    }
                }
            }
        }
    }
    public void halloween_vendor_action_removeDailyLimit(obj_id player, obj_id npc) throws InterruptedException
    {
        if (hasObjVar(player, "galacticCoinCounter.numberOfCoins"))
        {
            removeObjVar(player, "galacticCoinCounter.numberOfCoins");
        }
        if (hasObjVar(player, "galacticCoinCounter.timeStamp"))
        {
            removeObjVar(player, "galacticCoinCounter.timeStamp");
        }
        if (hasObjVar(player, "galacticCoinCounter.startTime"))
        {
            removeObjVar(player, "galacticCoinCounter.startTime");
        }
        if (buff.hasBuff(player, "event_halloween_coin_limit"))
        {
            buff.removeBuff(player, "event_halloween_coin_limit");
        }
        utils.removeScriptVarTree(player, "galacticMoonNpcList");
    }
    public int halloween_vendor_handleBranch1(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_7"))
        {
            if (halloween_vendor_condition_outOfRange(player, npc))
            {
                doAnimationAction(npc, "shake_head_no");
                string_id message = new string_id(c_stringFile, "s_69");
                utils.removeScriptVar(player, "conversation.halloween_vendor.branchId");
                prose_package pp = new prose_package();
                pp.stringId = message;
                pp.actor.set(player);
                pp.target.set(npc);
                npcEndConversationWithMessage(player, pp);
                return SCRIPT_CONTINUE;
            }
            if (halloween_vendor_condition_playerStealthed(player, npc))
            {
                doAnimationAction(npc, "shake_head_no");
                string_id message = new string_id(c_stringFile, "s_67");
                utils.removeScriptVar(player, "conversation.halloween_vendor.branchId");
                prose_package pp = new prose_package();
                pp.stringId = message;
                pp.actor.set(player);
                pp.target.set(npc);
                npcEndConversationWithMessage(player, pp);
                return SCRIPT_CONTINUE;
            }
            if (halloween_vendor_condition_costumeLockout(player, npc))
            {
                doAnimationAction(npc, "shake_head_no");
                string_id message = new string_id(c_stringFile, "s_12");
                utils.removeScriptVar(player, "conversation.halloween_vendor.branchId");
                prose_package pp = new prose_package();
                pp.stringId = message;
                pp.actor.set(player);
                pp.target.set(npc);
                npcEndConversationWithMessage(player, pp);
                return SCRIPT_CONTINUE;
            }
            if (halloween_vendor_condition__defaultCondition(player, npc))
            {
                halloween_vendor_action_applyCostume(player, npc);
                string_id message = new string_id(c_stringFile, "s_13");
                utils.removeScriptVar(player, "conversation.halloween_vendor.branchId");
                prose_package pp = new prose_package();
                pp.stringId = message;
                pp.actor.set(player);
                pp.target.set(npc);
                npcEndConversationWithMessage(player, pp);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_14"))
        {
            if (halloween_vendor_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "huge");
                halloween_vendor_action_showTokenVendorUI(player, npc);
                string_id message = new string_id(c_stringFile, "s_16");
                utils.removeScriptVar(player, "conversation.halloween_vendor.branchId");
                prose_package pp = new prose_package();
                pp.stringId = message;
                pp.actor.set(player);
                pp.target.set(npc);
                npcEndConversationWithMessage(player, pp);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_63"))
        {
            if (halloween_vendor_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "explain");
                string_id message = new string_id(c_stringFile, "s_64");
                utils.removeScriptVar(player, "conversation.halloween_vendor.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_52"))
        {
            if (halloween_vendor_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "nod_head_once");
                string_id message = new string_id(c_stringFile, "s_53");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (halloween_vendor_condition_canBuyBadge(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (halloween_vendor_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_54");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_55");
                    }
                    utils.setScriptVar(player, "conversation.halloween_vendor.branchId", 8);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.halloween_vendor.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_43"))
        {
            if (halloween_vendor_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "nod_head_once");
                string_id message = new string_id(c_stringFile, "s_45");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (halloween_vendor_condition_canBuyThree(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_78");
                    }
                    utils.setScriptVar(player, "conversation.halloween_vendor.branchId", 11);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.halloween_vendor.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_47"))
        {
            doAnimationAction(player, "celebrate1");
            if (halloween_vendor_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "laugh_cackle");
                string_id message = new string_id(c_stringFile, "s_49");
                utils.removeScriptVar(player, "conversation.halloween_vendor.branchId");
                prose_package pp = new prose_package();
                pp.stringId = message;
                pp.actor.set(player);
                pp.target.set(npc);
                npcEndConversationWithMessage(player, pp);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_51"))
        {
            if (halloween_vendor_condition__defaultCondition(player, npc))
            {
                halloween_vendor_action_removeLockout(player, npc);
                string_id message = new string_id(c_stringFile, "s_59");
                utils.removeScriptVar(player, "conversation.halloween_vendor.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_61"))
        {
            if (halloween_vendor_condition__defaultCondition(player, npc))
            {
                halloween_vendor_action_grantCoins(player, npc);
                string_id message = new string_id(c_stringFile, "s_65");
                utils.removeScriptVar(player, "conversation.halloween_vendor.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_68"))
        {
            if (halloween_vendor_condition__defaultCondition(player, npc))
            {
                halloween_vendor_action_removeProjectorLockout(player, npc);
                string_id message = new string_id(c_stringFile, "s_72");
                utils.removeScriptVar(player, "conversation.halloween_vendor.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_74"))
        {
            if (halloween_vendor_condition__defaultCondition(player, npc))
            {
                halloween_vendor_action_removeDailyLimit(player, npc);
                string_id message = new string_id(c_stringFile, "s_76");
                utils.removeScriptVar(player, "conversation.halloween_vendor.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int halloween_vendor_handleBranch8(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_54"))
        {
            doAnimationAction(player, "thumbs_up");
            if (halloween_vendor_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "thumb_up");
                halloween_vendor_action_badgePurchased(player, npc);
                string_id message = new string_id(c_stringFile, "s_56");
                utils.removeScriptVar(player, "conversation.halloween_vendor.branchId");
                prose_package pp = new prose_package();
                pp.stringId = message;
                pp.actor.set(player);
                pp.target.set(npc);
                npcEndConversationWithMessage(player, pp);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_55"))
        {
            if (halloween_vendor_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_57");
                utils.removeScriptVar(player, "conversation.halloween_vendor.branchId");
                prose_package pp = new prose_package();
                pp.stringId = message;
                pp.actor.set(player);
                pp.target.set(npc);
                npcEndConversationWithMessage(player, pp);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int halloween_vendor_handleBranch11(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_78"))
        {
            if (halloween_vendor_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_80");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (halloween_vendor_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_82");
                    }
                    utils.setScriptVar(player, "conversation.halloween_vendor.branchId", 12);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.halloween_vendor.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int halloween_vendor_handleBranch12(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_82"))
        {
            if (halloween_vendor_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_84");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (halloween_vendor_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (halloween_vendor_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_86");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_92");
                    }
                    utils.setScriptVar(player, "conversation.halloween_vendor.branchId", 13);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.halloween_vendor.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int halloween_vendor_handleBranch13(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_86"))
        {
            if (halloween_vendor_condition_canBuyThree(player, npc))
            {
                doAnimationAction(npc, "manipulate_medium");
                halloween_vendor_action_grantProjectorThree(player, npc);
                string_id message = new string_id(c_stringFile, "s_88");
                utils.removeScriptVar(player, "conversation.halloween_vendor.branchId");
                prose_package pp = new prose_package();
                pp.stringId = message;
                pp.actor.set(player);
                pp.target.set(npc);
                npcEndConversationWithMessage(player, pp);
                return SCRIPT_CONTINUE;
            }
            if (halloween_vendor_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_90");
                utils.removeScriptVar(player, "conversation.halloween_vendor.branchId");
                prose_package pp = new prose_package();
                pp.stringId = message;
                pp.actor.set(player);
                pp.target.set(npc);
                npcEndConversationWithMessage(player, pp);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_92"))
        {
            if (halloween_vendor_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "nod_head_once");
                string_id message = new string_id(c_stringFile, "s_94");
                utils.removeScriptVar(player, "conversation.halloween_vendor.branchId");
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
            detachScript(self, "conversation.halloween_vendor");
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
        detachScript(self, "conversation.halloween_vendor");
        return SCRIPT_CONTINUE;
    }
    public int OnDetach(obj_id self) throws InterruptedException
    {
        clearCondition(self, CONDITION_CONVERSABLE);
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
        if (halloween_vendor_condition__defaultCondition(player, npc))
        {
            doAnimationAction(npc, "nod_head_once");
            string_id message = new string_id(c_stringFile, "s_6");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (halloween_vendor_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            boolean hasResponse1 = false;
            if (halloween_vendor_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse1 = true;
            }
            boolean hasResponse2 = false;
            if (halloween_vendor_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse2 = true;
            }
            boolean hasResponse3 = false;
            if (halloween_vendor_condition_noBadge(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse3 = true;
            }
            boolean hasResponse4 = false;
            if (halloween_vendor_condition_doesntHaveThree(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse4 = true;
            }
            boolean hasResponse5 = false;
            if (halloween_vendor_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse5 = true;
            }
            boolean hasResponse6 = false;
            if (halloween_vendor_condition_godMode(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse6 = true;
            }
            boolean hasResponse7 = false;
            if (halloween_vendor_condition_godMode(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse7 = true;
            }
            boolean hasResponse8 = false;
            if (halloween_vendor_condition_godMode(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse8 = true;
            }
            boolean hasResponse9 = false;
            if (halloween_vendor_condition_godMode(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_7");
                }
                if (hasResponse1)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_14");
                }
                if (hasResponse2)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_63");
                }
                if (hasResponse3)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_52");
                }
                if (hasResponse4)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_43");
                }
                if (hasResponse5)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_47");
                }
                if (hasResponse6)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_51");
                }
                if (hasResponse7)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_61");
                }
                if (hasResponse8)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_68");
                }
                if (hasResponse9)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_74");
                }
                utils.setScriptVar(player, "conversation.halloween_vendor.branchId", 1);
                npcStartConversation(player, npc, "halloween_vendor", message, responses);
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
        if (!conversationId.equals("halloween_vendor"))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id npc = self;
        int branchId = utils.getIntScriptVar(player, "conversation.halloween_vendor.branchId");
        if (branchId == 1 && halloween_vendor_handleBranch1(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 8 && halloween_vendor_handleBranch8(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 11 && halloween_vendor_handleBranch11(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 12 && halloween_vendor_handleBranch12(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 13 && halloween_vendor_handleBranch13(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        chat.chat(npc, "Error:  Fell through all branches and responses for OnNpcConversationResponse.");
        utils.removeScriptVar(player, "conversation.halloween_vendor.branchId");
        return SCRIPT_CONTINUE;
    }
}
