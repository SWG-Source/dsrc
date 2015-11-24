package script.npc;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.utils;
import script.library.sui;
import script.library.trial;
import script.library.static_item;

public class token_vendor extends script.base_script
{
    public token_vendor()
    {
    }
    public static final String VENDOR_TABLE = "datatables/item/token_vendor.iff";
    public static final int COMMANDO = 1;
    public static final int SMUGGLER = 2;
    public static final int MEDIC = 3;
    public static final int OFFICER = 4;
    public static final int SPY = 5;
    public static final int BOUNTY_HUNTER = 6;
    public static final int FORCE_SENSITIVE = 7;
    public static final int TRADER = 8;
    public static final int ENTERTAINER = 9;
    public int showInventorySUI(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id player = params.getObjId("player");
        cleanScriptVars(player);
        String prompt = "@set_bonus:vendor_sui_1_prompt";
        String title = "@set_bonus:vendor_sui_1_title";
        String[] purchaseList = getPurchaseList(self, player);
        if (purchaseList.length > 1)
        {
            int pid = sui.listbox(self, player, prompt, sui.OK_CANCEL, title, purchaseList, "handleItemSelect", true, false);
            setWindowPid(player, pid);
        }
        else 
        {
            sendSystemMessage(player, new string_id("set_bonus", "vendor_not_qualified"));
        }
        return SCRIPT_CONTINUE;
    }
    public String[] getPurchaseList(obj_id self, obj_id player) throws InterruptedException
    {
        int numRowsInTable = dataTableGetNumRows(VENDOR_TABLE);
        int itemCount = 0;
        Vector purchaseList = new Vector();
        purchaseList.setSize(0);
        Vector itemIndexes = new Vector();
        itemIndexes.setSize(0);
        for (int i = 0; i < numRowsInTable; i++)
        {
            int reqClass = dataTableGetInt(VENDOR_TABLE, i, "class");
            int category = dataTableGetInt(VENDOR_TABLE, i, "category");
            String item = dataTableGetString(VENDOR_TABLE, i, "item");
            if (reqClass < 1 || utils.isProfession(player, reqClass))
            {
                String itemName = getString(new string_id("static_item_n", item));
                utils.addElement(purchaseList, itemName);
                utils.addElement(itemIndexes, itemCount);
            }
            itemCount++;
        }
        utils.setScriptVar(player, "item.set.token_vendor_list", itemIndexes);
        String[] _purchaseList = new String[0];
        if (purchaseList != null)
        {
            _purchaseList = new String[purchaseList.size()];
            purchaseList.toArray(_purchaseList);
        }
        return _purchaseList;
    }
    public int handleItemSelect(obj_id self, dictionary params) throws InterruptedException
    {
        if ((params == null) || (params.isEmpty()))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id player = sui.getPlayerId(params);
        int btn = sui.getIntButtonPressed(params);
        int idx = sui.getListboxSelectedRow(params);
        int[] purchaseList = utils.getIntArrayScriptVar(player, "item.set.token_vendor_list");
        int tokenCosts[] = new int[trial.HEROIC_TOKENS.length + 1];
        if (btn == sui.BP_CANCEL)
        {
            cleanScriptVars(player);
            return SCRIPT_CONTINUE;
        }
        if (idx == -1 || idx > purchaseList.length)
        {
            cleanScriptVars(player);
            return SCRIPT_CONTINUE;
        }
        int[] intendedPayment = new int[trial.HEROIC_TOKENS.length];
        for (int i = 0; i < intendedPayment.length; i++)
        {
            intendedPayment[i] = 0;
        }
        utils.setScriptVar(player, "item.set.intended_payment", intendedPayment);
        int selectionRow = purchaseList[idx];
        dictionary d = dataTableGetRow(VENDOR_TABLE, selectionRow);
        utils.setScriptVar(player, "item.set.intended_purchase", selectionRow);
        String item = d.getString("item");
        if (!utils.hasScriptVar(player, "item.set.owed_tokens"))
        {
            tokenCosts[trial.HEROIC_TOKENS.length] = d.getInt("any_token");
            int totalCost = tokenCosts[trial.HEROIC_TOKENS.length];
            for (int i = 0; i < trial.HEROIC_TOKENS.length; i++)
            {
                String reference = "token" + i;
                int cost = d.getInt(reference);
                totalCost += cost;
                tokenCosts[i] = d.getInt(reference);
            }
            if (totalCost == 0)
            {
                tokenCosts[trial.HEROIC_TOKENS.length] = 1;
            }
            utils.setScriptVar(player, "item.set.owed_tokens", tokenCosts);
            utils.setScriptVar(player, "item.set.final_cost", tokenCosts);
        }
        int[] tokensOwed = utils.getIntArrayScriptVar(player, "item.set.owed_tokens");
        String title = "@set_bonus:vendor_sui_2_title";
        String prompt = getTokensOwedPrompt(player);
        String[] inventoryTokens = getInventoryTokens(player);
        refreshMenu(player, prompt, title, inventoryTokens, "handlePaymentUpdate", false);
        return SCRIPT_CONTINUE;
    }
    public int handlePaymentUpdate(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id player = sui.getPlayerId(params);
        int btn = sui.getIntButtonPressed(params);
        int idx = sui.getListboxSelectedRow(params);
        int[] tokensOwed = utils.getIntArrayScriptVar(player, "item.set.owed_tokens");
        int[] tokensRemaining = utils.getIntArrayScriptVar(player, "item.set.tokens_remaining");
        int[] intendedPayment = utils.getIntArrayScriptVar(player, "item.set.intended_payment");
        String[] lastListStringData = utils.getStringArrayScriptVar(player, "item.set.inventory_token_strings");
        String title = "@set_bonus:vendor_sui_2_title";
        String prompt = getTokensOwedPrompt(player);
        if (btn == sui.BP_CANCEL)
        {
            cleanScriptVars(player);
            return SCRIPT_CONTINUE;
        }
        if (idx == -1 || idx > tokensOwed.length || idx > intendedPayment.length)
        {
            cleanScriptVars(player);
            return SCRIPT_CONTINUE;
        }
        if (tokensRemaining[idx] == 0)
        {
            sendSystemMessage(player, new string_id("set_bonus", "vendor_no_token"));
            refreshMenu(player, prompt, title, getInventoryTokens(player), "handlePaymentUpdate", false);
            return SCRIPT_CONTINUE;
        }
        if (tokensOwed[idx] < 1 && tokensOwed[trial.HEROIC_TOKENS.length] < 1)
        {
            sendSystemMessage(player, new string_id("set_bonus", "vendor_dont_need_token"));
            refreshMenu(player, prompt, title, getInventoryTokens(player), "handlePaymentUpdate", false);
            return SCRIPT_CONTINUE;
        }
        else if (tokensOwed[idx] > 0)
        {
            tokensOwed[idx]--;
            intendedPayment[idx]++;
        }
        else if (tokensOwed[trial.HEROIC_TOKENS.length] > 0)
        {
            tokensOwed[trial.HEROIC_TOKENS.length]--;
            intendedPayment[idx]++;
        }
        utils.setScriptVar(player, "item.set.owed_tokens", tokensOwed);
        utils.setScriptVar(player, "item.set.intended_payment", intendedPayment);
        int tokensPaidInFull = 0;
        for (int i = 0; i < tokensOwed.length; i++)
        {
            if (tokensOwed[i] == 0)
            {
                tokensPaidInFull++;
            }
            if (tokensPaidInFull == trial.HEROIC_TOKENS.length + 1)
            {
                processItemPurchase(player);
                closeOldWindow(player);
                return SCRIPT_CONTINUE;
            }
        }
        prompt = getTokensOwedPrompt(player);
        refreshMenu(player, prompt, title, getInventoryTokens(player), "handlePaymentUpdate", false);
        return SCRIPT_CONTINUE;
    }
    public String[] getInventoryTokens(obj_id player) throws InterruptedException
    {
        int[] totalTokens = new int[trial.HEROIC_TOKENS.length + 1];
        String[] inventoryTokens = new String[trial.HEROIC_TOKENS.length];
        for (int h = 0; h < totalTokens.length; h++)
        {
            totalTokens[h] = 0;
        }
        obj_id inventory = utils.getInventoryContainer(player);
        obj_id[] inventoryContents = getContents(inventory);
        boolean foundTokenHolderBox = false;
        for (int i = 0; i < inventoryContents.length; i++)
        {
            String itemName = getStaticItemName(inventoryContents[i]);
            if (itemName != null && !itemName.equals(""))
            {
                for (int j = 0; j < trial.HEROIC_TOKENS.length; j++)
                {
                    if (itemName.equals(trial.HEROIC_TOKENS[j]))
                    {
                        if (getCount(inventoryContents[i]) > 1)
                        {
                            totalTokens[j] += getCount(inventoryContents[i]);
                        }
                        else 
                        {
                            totalTokens[j]++;
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
                            int thisTokenCount = virtualTokens[k];
                            totalTokens[k] += thisTokenCount;
                        }
                    }
                }
            }
        }
        if (utils.hasScriptVar(player, "item.set.intended_payment"))
        {
            int[] intendedPayment = utils.getIntArrayScriptVar(player, "item.set.intended_payment");
            for (int i = 0; i < intendedPayment.length; i++)
            {
                totalTokens[i] = totalTokens[i] - intendedPayment[i];
            }
        }
        for (int i = 0; i < trial.HEROIC_TOKENS.length; i++)
        {
            inventoryTokens[i] = "[" + totalTokens[i] + "] " + getString(new string_id("static_item_n", trial.HEROIC_TOKENS[i]));
        }
        utils.setScriptVar(player, "item.set.tokens_remaining", totalTokens);
        utils.setScriptVar(player, "item.set.inventory_token_strings", inventoryTokens);
        return inventoryTokens;
    }
    public void processItemPurchase(obj_id player) throws InterruptedException
    {
        int intendedPurchaseRow = utils.getIntScriptVar(player, "item.set.intended_purchase");
        int[] tokenCost = utils.getIntArrayScriptVar(player, "item.set.final_cost");
        int[] intendedPayment = utils.getIntArrayScriptVar(player, "item.set.intended_payment");
        dictionary d = dataTableGetRow(VENDOR_TABLE, intendedPurchaseRow);
        String item = d.getString("item");
        if (!verifyTokens(player, intendedPayment))
        {
            sendSystemMessage(player, new string_id("set_bonus", "vendor_cant_purchase"));
            closeOldWindow(player);
            cleanScriptVars(player);
            return;
        }
        obj_id inventory = utils.getInventoryContainer(player);
        obj_id purchasedItem = static_item.createNewItemFunction(item, inventory);
        if (!exists(purchasedItem))
        {
            sendSystemMessage(player, new string_id("set_bonus", "vendor_cant_purchase"));
            closeOldWindow(player);
            cleanScriptVars(player);
            return;
        }
        obj_id[] inventoryContents = getContents(inventory);
        boolean foundTokenHolderBox = false;
        for (int i = 0; i < inventoryContents.length; i++)
        {
            String itemName = getStaticItemName(inventoryContents[i]);
            if (itemName != null && !itemName.equals(""))
            {
                for (int j = 0; j < trial.HEROIC_TOKENS.length; j++)
                {
                    if (itemName.equals(trial.HEROIC_TOKENS[j]) && intendedPayment[j] > 0)
                    {
                        if (getCount(inventoryContents[i]) > 1)
                        {
                            setCount(inventoryContents[i], getCount(inventoryContents[i]) - 1);
                        }
                        else 
                        {
                            destroyObject(inventoryContents[i]);
                        }
                        intendedPayment[j]--;
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
                            if (intendedPayment[k] > 0 && virtualTokens[k] > 0)
                            {
                                int paymentCounter = intendedPayment[k];
                                for (int l = 0; l < paymentCounter; l++)
                                {
                                    if (virtualTokens[k] >= 0)
                                    {
                                        virtualTokens[k]--;
                                        intendedPayment[k]--;
                                    }
                                }
                            }
                        }
                        setObjVar(inventoryContents[i], "item.set.tokens_held", virtualTokens);
                    }
                }
            }
        }
        cleanScriptVars(player);
        closeOldWindow(player);
        return;
    }
    public boolean verifyTokens(obj_id player, int[] intendedPayment) throws InterruptedException
    {
        obj_id inventory = utils.getInventoryContainer(player);
        obj_id[] inventoryContents = getContents(inventory);
        boolean foundTokenHolderBox = false;
        int tokensNeeded = 0;
        int tokensIGot = 0;
        int[] payment = new int[trial.HEROIC_TOKENS.length];
        for (int z = 0; z < payment.length; z++)
        {
            payment[z] = intendedPayment[z];
        }
        for (int i = 0; i < payment.length; i++)
        {
            tokensNeeded += payment[i];
        }
        for (int i = 0; i < inventoryContents.length; i++)
        {
            String itemName = getStaticItemName(inventoryContents[i]);
            if (itemName != null && !itemName.equals(""))
            {
                for (int j = 0; j < trial.HEROIC_TOKENS.length; j++)
                {
                    if (itemName.equals(trial.HEROIC_TOKENS[j]) && payment[j] > 0)
                    {
                        if (getCount(inventoryContents[i]) > 1)
                        {
                            for (int m = 0; m < getCount(inventoryContents[i]); m++)
                            {
                                if (payment[j] > 0)
                                {
                                    payment[j]--;
                                    tokensIGot++;
                                }
                            }
                        }
                        else 
                        {
                            payment[j]--;
                            tokensIGot++;
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
                            if (payment[k] > 0 && virtualTokens[k] > 0)
                            {
                                int paymentIterations = payment[k];
                                for (int l = 0; l < paymentIterations; l++)
                                {
                                    if (virtualTokens[k] > 0)
                                    {
                                        virtualTokens[k]--;
                                        payment[k]--;
                                        tokensIGot++;
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        if (tokensNeeded != tokensIGot)
        {
            return false;
        }
        return true;
    }
    public String getTokensOwedPrompt(obj_id player) throws InterruptedException
    {
        int[] tokensOwed = utils.getIntArrayScriptVar(player, "item.set.owed_tokens");
        String prompt = getString(new string_id("set_bonus", "vendor_sui_2_prompt"));
        prompt += "\n";
        for (int i = 0; i < trial.HEROIC_TOKENS.length; i++)
        {
            if (tokensOwed[i] > 0)
            {
                prompt += "[" + tokensOwed[i] + "] " + getString(new string_id("static_item_n", trial.HEROIC_TOKENS[i])) + "\n";
            }
        }
        if (tokensOwed[trial.HEROIC_TOKENS.length] > 0)
        {
            prompt += "[" + tokensOwed[trial.HEROIC_TOKENS.length] + "] " + getString(new string_id("set_bonus", "vendor_sui_any_token"));
        }
        return prompt;
    }
    public void cleanScriptVars(obj_id player) throws InterruptedException
    {
        obj_id self = getSelf();
        utils.removeScriptVarTree(player, "item.set");
        utils.removeScriptVarTree(self, "item.set");
    }
    public void refreshMenu(obj_id player, String prompt, String title, String[] options, String myHandler, boolean draw) throws InterruptedException
    {
        obj_id self = getSelf();
        closeOldWindow(player);
        if (outOfRange(self, player, true))
        {
            cleanScriptVars(player);
            return;
        }
        if (draw == false)
        {
            int pid = sui.listbox(self, player, prompt, sui.OK_CANCEL, title, options, myHandler, false, false);
            sui.showSUIPage(pid);
            setWindowPid(player, pid);
        }
        else 
        {
            int pid = sui.listbox(self, player, prompt, sui.OK_CANCEL, title, options, myHandler, true, false);
            sui.showSUIPage(pid);
            setWindowPid(player, pid);
        }
    }
    public void closeOldWindow(obj_id player) throws InterruptedException
    {
        String playerPath = "item.set.";
        if (utils.hasScriptVar(player, "item.set.pid"))
        {
            int oldpid = utils.getIntScriptVar(player, "item.set.pid");
            forceCloseSUIPage(oldpid);
            utils.removeScriptVar(player, "item.set.pid");
        }
    }
    public void setWindowPid(obj_id player, int pid) throws InterruptedException
    {
        if (pid > -1)
        {
            utils.setScriptVar(player, "item.set.pid", pid);
        }
    }
    public boolean outOfRange(obj_id self, obj_id player, boolean message) throws InterruptedException
    {
        if (isGod(player))
        {
            return false;
        }
        location a = getLocation(self);
        location b = getLocation(player);
        if (a.cell == b.cell && a.distance(b) < 8.0f)
        {
            return false;
        }
        if (message)
        {
            sendSystemMessageTestingOnly(player, "Out of Range of Character Builder Terminal.");
        }
        return true;
    }
}
