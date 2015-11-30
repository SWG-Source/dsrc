package script.theme_park.dathomir.aurilia;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.buff;
import script.library.prose;
import script.library.static_item;
import script.library.sui;
import script.library.trial;
import script.library.utils;

public class aurilia_buff_vendor extends script.base_script
{
    public aurilia_buff_vendor()
    {
    }
    public int OnObjectMenuSelect(obj_id self, obj_id player, int selection) throws InterruptedException
    {
        if (selection == menu_info_types.ITEM_PUBLIC_CONTAINER_USE1)
        {
            String staticName = getStaticItemName(self);
            if (static_item.isUniqueStaticItem(staticName))
            {
                if (!static_item.canCreateUniqueStaticItem(player, staticName))
                {
                    sendSystemMessage(player, new string_id("set_bonus", "vendor_already_has_unique"));
                    return SCRIPT_CONTINUE;
                }
            }
            dictionary itemData = new dictionary();
            itemData = dataTableGetRow(static_item.ITEM_STAT_BALANCE_TABLE, staticName);
            if (itemData == null)
            {
                LOG("create", staticName + ": Active buff check could not happen because row in item_stats datatable is bad");
                return SCRIPT_CONTINUE;
            }
            String buffName = itemData.getString("buff_name");
            if (buff.hasBuff(player, buffName))
            {
                sendSystemMessage(player, new string_id("set_bonus", "vendor_already_has_buff"));
                return SCRIPT_CONTINUE;
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
        int tokenArrayLength = 0;
        creditCost = getIntObjVar(self, "item.object_for_sale.cash_cost");
        int[] tokenCost = getIntArrayObjVar(self, "item.object_for_sale.token_cost");
        if (tokenCost.length > trial.HEROIC_TOKENS.length)
        {
            tokenArrayLength = trial.HEROIC_TOKENS.length;
        }
        else if (tokenCost.length > 0)
        {
            tokenArrayLength = tokenCost.length;
        }
        for (int i = 0; i < tokenArrayLength; i++)
        {
            if (tokenCost[i] > 0)
            {
                pureConcatenatedJoy += "[" + tokenCost[i] + "] " + getString(new string_id("static_item_n", trial.HEROIC_TOKENS[i])) + "\n";
            }
        }
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
        boolean hasTheTokens = false;
        boolean foundTokenHolderBox = false;
        obj_id inventory = utils.getInventoryContainer(player);
        obj_id[] inventoryContents = getContents(inventory);
        int creditCost = getIntObjVar(self, "item.object_for_sale.cash_cost");
        int[] tokenCostInThisFunction = getIntArrayObjVar(self, "item.object_for_sale.token_cost");
        if (creditCost < 1)
        {
            hasTheCredits = true;
        }
        int owedTokenCount = 0;
        boolean owesTokens = false;
        for (int o = 0; o < tokenCostInThisFunction.length; o++)
        {
            owedTokenCount += tokenCostInThisFunction[o];
        }
        if (owedTokenCount > 0)
        {
            owesTokens = true;
        }
        if (owesTokens)
        {
            for (int i = 0; i < inventoryContents.length; i++)
            {
                String itemName = getStaticItemName(inventoryContents[i]);
                if (itemName != null && !itemName.equals(""))
                {
                    for (int j = 0; j < trial.HEROIC_TOKENS.length; j++)
                    {
                        if (itemName.equals(trial.HEROIC_TOKENS[j]) && tokenCostInThisFunction[j] > 0)
                        {
                            if (getCount(inventoryContents[i]) > 1)
                            {
                                for (int m = 0; m < getCount(inventoryContents[i]); m++)
                                {
                                    if (tokenCostInThisFunction[j] > 0)
                                    {
                                        tokenCostInThisFunction[j]--;
                                    }
                                }
                            }
                            else 
                            {
                                tokenCostInThisFunction[j]--;
                            }
                        }
                    }
                    if (!foundTokenHolderBox && itemName.equals("item_heroic_token_box_01_01"))
                    {
                        foundTokenHolderBox = true;
                        if (hasObjVar(inventoryContents[i], "item.set.tokens_held"))
                        {
                            int[] virtualTokens = getIntArrayObjVar(inventoryContents[i], "item.set.tokens_held");
                            for (int k = 0; k < trial.HEROIC_TOKENS.length; k++)
                            {
                                if (tokenCostInThisFunction[k] > 0 && virtualTokens[k] > 0)
                                {
                                    int paymentIterations = tokenCostInThisFunction[k];
                                    for (int l = 0; l < paymentIterations; l++)
                                    {
                                        if (virtualTokens[k] > 0)
                                        {
                                            virtualTokens[k]--;
                                            tokenCostInThisFunction[k]--;
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        int outstandingTokensOwed = 0;
        for (int n = 0; n < tokenCostInThisFunction.length; n++)
        {
            outstandingTokensOwed += tokenCostInThisFunction[n];
        }
        if (outstandingTokensOwed == 0)
        {
            hasTheTokens = true;
        }
        return hasTheCredits && hasTheTokens;
    }
    public void processItemPurchase(obj_id self, obj_id player) throws InterruptedException
    {
        obj_id inventory = utils.getInventoryContainer(player);
        obj_id[] inventoryContents = getContents(inventory);
        int creditCost = getIntObjVar(self, "item.object_for_sale.cash_cost");
        int[] tokenCostForReals = getIntArrayObjVar(self, "item.object_for_sale.token_cost");
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
        boolean foundTokenHolderBox = false;
        for (int i = 0; i < inventoryContents.length; i++)
        {
            String itemName = getStaticItemName(inventoryContents[i]);
            if (itemName != null && !itemName.equals(""))
            {
                for (int j = 0; j < trial.HEROIC_TOKENS.length; j++)
                {
                    if (itemName.equals(trial.HEROIC_TOKENS[j]) && tokenCostForReals[j] > 0)
                    {
                        if (getCount(inventoryContents[i]) > 1)
                        {
                            int numInStack = getCount(inventoryContents[i]);
                            for (int m = 0; m < numInStack - 1; m++)
                            {
                                if (tokenCostForReals[j] > 0)
                                {
                                    tokenCostForReals[j]--;
                                    setCount(inventoryContents[i], getCount(inventoryContents[i]) - 1);
                                }
                            }
                        }
                        if (getCount(inventoryContents[i]) <= 1 && tokenCostForReals[j] > 0)
                        {
                            destroyObject(inventoryContents[i]);
                            tokenCostForReals[j]--;
                        }
                    }
                }
                if (!foundTokenHolderBox && itemName.equals("item_heroic_token_box_01_01"))
                {
                    foundTokenHolderBox = true;
                    if (hasObjVar(inventoryContents[i], "item.set.tokens_held"))
                    {
                        int[] virtualTokens = getIntArrayObjVar(inventoryContents[i], "item.set.tokens_held");
                        for (int k = 0; k < trial.HEROIC_TOKENS.length; k++)
                        {
                            if (tokenCostForReals[k] > 0 && virtualTokens[k] > 0)
                            {
                                int paymentCounter = tokenCostForReals[k];
                                for (int l = 0; l < paymentCounter; l++)
                                {
                                    if (virtualTokens[k] > 0)
                                    {
                                        virtualTokens[k]--;
                                        tokenCostForReals[k]--;
                                    }
                                }
                            }
                        }
                        setObjVar(inventoryContents[i], "item.set.tokens_held", virtualTokens);
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
