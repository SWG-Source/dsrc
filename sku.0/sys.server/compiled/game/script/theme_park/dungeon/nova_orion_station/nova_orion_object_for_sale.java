package script.theme_park.dungeon.nova_orion_station;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.badge;
import script.library.money;
import script.library.prose;
import script.library.static_item;
import script.library.township;
import script.library.trial;
import script.library.utils;

public class nova_orion_object_for_sale extends script.base_script
{
    public nova_orion_object_for_sale()
    {
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        setObjVar(self, township.OBJECT_FOR_SALE_ON_VENDOR, true);
        return SCRIPT_CONTINUE;
    }
    public int OnObjectMenuSelect(obj_id self, obj_id player, int item) throws InterruptedException
    {
        if (item == menu_info_types.ITEM_PUBLIC_CONTAINER_USE1)
        {
            if (!hasObjVar(self, township.OBJVAR_NOVA_ORION_FACTION))
            {
                if (isGod(player))
                {
                    sendSystemMessage(player, "[unlocalized] God Mode Warning: Vendor object invalid. Lacks required objvar(s).", "");
                }
                return SCRIPT_CONTINUE;
            }
            String requiredNovaOrionFaction = getStringObjVar(self, township.OBJVAR_NOVA_ORION_FACTION);
            if (requiredNovaOrionFaction == null || (!requiredNovaOrionFaction.equals("nova") && !requiredNovaOrionFaction.equals("orion")))
            {
                if (isGod(player))
                {
                    sendSystemMessage(player, "[unlocalized] God Mode Warning: Vendor object invalid. Nova Orion faction missing.", "");
                }
                return SCRIPT_CONTINUE;
            }
            if (!badge.hasBadge(player, "nova_orion_peace_keeper"))
            {
                if (!hasCompletedCollectionSlot(player, requiredNovaOrionFaction + "_rank_01_05"))
                {
                    sendSystemMessage(player, new string_id("spam", "nova_orion_unauthorized_purchased"));
                    return SCRIPT_CONTINUE;
                }
            }
            if (confirmFunds(self, player))
            {
                processItemPurchase(self, player);
            }
            else 
            {
                sendSystemMessage(player, new string_id("spam", "buildabuff_nsf_buffee"));
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int OnGetAttributes(obj_id self, obj_id player, String[] names, String[] attribs) throws InterruptedException
    {
        if (names == null || attribs == null || names.length != attribs.length)
        {
            return SCRIPT_CONTINUE;
        }
        final int firstFreeIndex = getFirstFreeIndex(names);
        if (firstFreeIndex >= 0 && firstFreeIndex < names.length)
        {
            names[firstFreeIndex] = utils.packStringId(new string_id("set_bonus", "vendor_cost"));
            attribs[firstFreeIndex] = createPureConcatenatedJoy(self);
        }
        return SCRIPT_CONTINUE;
    }
    public String createPureConcatenatedJoy(obj_id self) throws InterruptedException
    {
        String pureConcatenatedJoy = getString(new string_id("set_bonus", "vendor_sale_object_justify_line"));
        int creditCost = 0;
        int resourceCost = 1000;
        if (hasObjVar(self, "item.object_for_sale.cash_cost"))
        {
            creditCost = getIntObjVar(self, "item.object_for_sale.cash_cost");
        }
        if (hasObjVar(self, "item.object_for_sale.resource_cost"))
        {
            resourceCost = getIntObjVar(self, "item.object_for_sale.resource_cost");
        }
        pureConcatenatedJoy += "[" + resourceCost + "] " + getString(new string_id("static_item_n", township.MIDLITHE_CRYSTAL)) + "\n";
        if (creditCost > 0)
        {
            pureConcatenatedJoy += creditCost + getString(new string_id("set_bonus", "vendor_credits"));
        }
        return pureConcatenatedJoy;
    }
    public boolean confirmFunds(obj_id self, obj_id player) throws InterruptedException
    {
        boolean canPay = false;
        boolean hasTheCredits = false;
        boolean hasTheResources = false;
        obj_id[] inventoryContents = getInventoryAndEquipment(player);
        int creditCost = getIntObjVar(self, "item.object_for_sale.cash_cost");
        int resourceCost = getIntObjVar(self, "item.object_for_sale.resource_cost");
        if (creditCost < 1)
        {
            hasTheCredits = true;
        }
        if (creditCost > 0)
        {
            if (money.hasFunds(player, money.MT_TOTAL, creditCost))
            {
                hasTheCredits = true;
            }
        }
        int resourcesInInventory = 0;
        if (resourceCost > 0)
        {
            for (int i = 0; i < inventoryContents.length; i++)
            {
                obj_id inventoryObject = inventoryContents[i];
                String itemName = getStaticItemName(inventoryObject);
                if (itemName != null && !itemName.equals(""))
                {
                    if (itemName.equals(township.MIDLITHE_CRYSTAL))
                    {
                        int amountInResourceStack = getCount(inventoryObject);
                        if (amountInResourceStack > 0)
                        {
                            resourcesInInventory = resourcesInInventory + amountInResourceStack;
                        }
                    }
                }
            }
        }
        if (resourcesInInventory >= resourceCost)
        {
            hasTheResources = true;
        }
        return hasTheCredits && hasTheResources;
    }
    public void processItemPurchase(obj_id self, obj_id player) throws InterruptedException
    {
        obj_id inventory = utils.getInventoryContainer(player);
        obj_id[] inventoryContents = getInventoryAndEquipment(player);
        int creditCost = getIntObjVar(self, "item.object_for_sale.cash_cost");
        int resourceCost = getIntObjVar(self, "item.object_for_sale.resource_cost");
        String myName = static_item.getStaticItemName(self);
        obj_id purchasedItem = static_item.createNewItemFunction(myName, inventory);
        CustomerServiceLog("Heroic-Token: ", "player " + getFirstName(player) + "(" + player + ") purchased item " + myName + "(" + purchasedItem + ")");
        if (!exists(purchasedItem))
        {
            sendSystemMessage(player, new string_id("set_bonus", "vendor_cant_purchase"));
            return;
        }
        String readableName = getString(parseNameToStringId(getName(self)));
        prose_package pp = new prose_package();
        pp = prose.setStringId(pp, new string_id("set_bonus", "vendor_item_purchased"));
        pp = prose.setTT(pp, readableName);
        sendSystemMessageProse(player, pp);
        if (creditCost > 0)
        {
            obj_id containedBy = getContainedBy(getContainedBy(getContainedBy(self)));
            money.requestPayment(player, containedBy, creditCost, "no_handler", null, false);
        }
        if (resourceCost > 0)
        {
            int remainingResourceCost = resourceCost;
            for (int i = 0; i < inventoryContents.length; i++)
            {
                if (remainingResourceCost > 0)
                {
                    obj_id inventoryObject = inventoryContents[i];
                    String itemName = getStaticItemName(inventoryObject);
                    if (itemName != null && !itemName.equals(""))
                    {
                        if (itemName.equals(township.MIDLITHE_CRYSTAL))
                        {
                            int numInStack = getCount(inventoryObject);
                            if (numInStack > 0)
                            {
                                if (numInStack > remainingResourceCost)
                                {
                                    setCount(inventoryObject, numInStack - remainingResourceCost);
                                    remainingResourceCost = 0;
                                }
                                else 
                                {
                                    remainingResourceCost = remainingResourceCost - numInStack;
                                    destroyObject(inventoryObject);
                                }
                            }
                        }
                    }
                }
            }
        }
        return;
    }
    public string_id parseNameToStringId(String itemName) throws InterruptedException
    {
        String[] parsedString = split(itemName, ':');
        string_id itemNameSID;
        if (parsedString.length > 1)
        {
            String stfFile = parsedString[0];
            String reference = parsedString[1];
            itemNameSID = new string_id(stfFile, reference);
        }
        else 
        {
            String stfFile = parsedString[0];
            itemNameSID = new string_id(stfFile, " ");
        }
        return itemNameSID;
    }
}
