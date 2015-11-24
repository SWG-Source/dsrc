package script.item;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.utils;
import script.library.prose;
import script.library.trial;

public class heroic_token extends script.base_script
{
    public heroic_token()
    {
    }
    public int OnObjectMenuRequest(obj_id self, obj_id player, menu_info mi) throws InterruptedException
    {
        menu_info_data mid = mi.getMenuItemByType(menu_info_types.ITEM_USE);
        int menuStoreToken = mi.addRootMenu(menu_info_types.ITEM_USE, new string_id("spam", "heroic_token_store"));
        if (getCount(self) > 1)
        {
            mi.addSubMenu(menuStoreToken, menu_info_types.SERVER_MENU1, new string_id("spam", "heroic_token_store_all"));
        }
        return SCRIPT_CONTINUE;
    }
    public int OnObjectMenuSelect(obj_id self, obj_id player, int item) throws InterruptedException
    {
        if (isDead(player) || isIncapacitated(player))
        {
            return SCRIPT_CONTINUE;
        }
        if (getContainedBy(self) != utils.getInventoryContainer(player))
        {
            sendSystemMessage(player, new string_id("spam", "use_top_level_only"));
            return SCRIPT_CONTINUE;
        }
        if (item == menu_info_types.ITEM_USE)
        {
            findAndUpdateTokenBox(self, player, 1);
        }
        else if (item == menu_info_types.SERVER_MENU1)
        {
            findAndUpdateTokenBox(self, player, getCount(self));
        }
        return SCRIPT_CONTINUE;
    }
    public void findAndUpdateTokenBox(obj_id self, obj_id player, int amount) throws InterruptedException
    {
        obj_id inventory = utils.getInventoryContainer(player);
        obj_id[] inventoryContents = getContents(inventory);
        int myId = findMyId(self);
        if (myId < 0)
        {
            sendSystemMessage(player, new string_id("set_bonus", "heroic_token_token_error"));
            return;
        }
        for (int i = 0; i < inventoryContents.length; i++)
        {
            obj_id inventoryItem = inventoryContents[i];
            if (!isIdValid(inventoryItem))
            {
                continue;
            }
            String itemName = getStaticItemName(inventoryItem);
            if (itemName != null && !itemName.equals("") && itemName.equals("item_heroic_token_box_01_01"))
            {
                if (hasObjVar(inventoryItem, "item.set.tokens_held"))
                {
                    updateTokenBox(myId, inventoryItem, self, player, amount);
                    break;
                }
                else 
                {
                    trial.initializeBox(self);
                    if (hasObjVar(inventoryItem, "item.set.tokens_held"))
                    {
                        updateTokenBox(myId, inventoryItem, self, player, amount);
                    }
                    else 
                    {
                        sendSystemMessage(player, new string_id("spam", "heroic_token_box_error"));
                    }
                }
            }
        }
        return;
    }
    public int findMyId(obj_id self) throws InterruptedException
    {
        int failId = -1;
        String myName = getStaticItemName(self);
        for (int i = 0; i < trial.HEROIC_TOKENS.length; i++)
        {
            if (myName.equals(trial.HEROIC_TOKENS[i]))
            {
                return i;
            }
        }
        return failId;
    }
    public void updateTokenBox(int myId, obj_id tokenBox, obj_id self, obj_id player, int amount) throws InterruptedException
    {
        int[] currentHeroicTokens = getIntArrayObjVar(tokenBox, "item.set.tokens_held");
        if (currentHeroicTokens != null && currentHeroicTokens.length == trial.NUM_HEROIC_TOKEN_TYPES)
        {
            updateTokenCount(currentHeroicTokens, myId, self, tokenBox, player, amount);
        }
        else 
        {
            trial.verifyBox(self);
            currentHeroicTokens = getIntArrayObjVar(tokenBox, "item.set.tokens_held");
            if (currentHeroicTokens != null && currentHeroicTokens.length == trial.NUM_HEROIC_TOKEN_TYPES)
            {
                updateTokenCount(currentHeroicTokens, myId, self, tokenBox, player, amount);
            }
            else 
            {
                sendSystemMessage(player, new string_id("spam", "heroic_token_box_error"));
            }
        }
        return;
    }
    public void updateTokenCount(int[] currentHeroicTokens, int myId, obj_id self, obj_id tokenBox, obj_id player, int amount) throws InterruptedException
    {
        currentHeroicTokens[myId] += amount;
        setObjVar(tokenBox, "item.set.tokens_held", currentHeroicTokens);
        decrementTokenCount(self, player, amount);
        return;
    }
    public void decrementTokenCount(obj_id self, obj_id player, int amount) throws InterruptedException
    {
        prose_package pp = new prose_package();
        pp = prose.setStringId(pp, new string_id("spam", "heroic_token_box_success"));
        pp = prose.setTT(pp, self);
        sendSystemMessageProse(player, pp);
        if (getCount(self) > amount)
        {
            setCount(self, getCount(self) - amount);
        }
        else 
        {
            destroyObject(self);
        }
    }
}
