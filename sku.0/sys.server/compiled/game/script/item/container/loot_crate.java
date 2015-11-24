package script.item.container;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.collection;
import script.library.loot;
import script.library.static_item;
import script.library.sui;
import script.library.utils;

public class loot_crate extends script.base_script
{
    public loot_crate()
    {
    }
    public static final String COLLECTION_COLUMN = "collections";
    public static final String PLAYER_ACCESS_ID = "player_access_id";
    public static final int lootMin = 0;
    public static final int lootMax = 2;
    public static String MAGSEAL_TEMPLATE = "object/tangible/container/loot/placable_loot_crate.iff";
    public static String MAGSEAL_LOOT_TABLE = "datatables/loot/loot_items/collectible/magseal_loot.iff";
    public static string_id MAGSEAL_RADIAL_OPEN = new string_id("collection", "magseal_radial");
    public static string_id DOES_NOT_HAVE_ACCESS = new string_id("collection", "no_magseal_access");
    public static string_id COUNTDOWN_HAS_BEGUN = new string_id("collection", "magseal_timer_warning");
    public static string_id NO_ACCESS_MAGSEAL = new string_id("collection", "magseal_no_access");
    public static string_id MAGSEAL_OCCUPIED = new string_id("collection", "magseal_occupied");
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        if (!utils.isContainer(self))
        {
            return SCRIPT_CONTINUE;
        }
        String containerTemplate = getTemplateName(self);
        if (containerTemplate.equals(MAGSEAL_TEMPLATE))
        {
            obj_id[] contents = getContents(self);
            if (contents != null)
            {
                messageTo(self, "initializeLootContainer", null, 20.0f, false);
            }
            return SCRIPT_CONTINUE;
        }
        else 
        {
            destroyObject(self);
            return SCRIPT_CONTINUE;
        }
    }
    public int OnAboutToLoseItem(obj_id self, obj_id pInv, obj_id player, obj_id item) throws InterruptedException
    {
        if (!utils.hasScriptVar(self, PLAYER_ACCESS_ID))
        {
            LOG("sissynoid", "Magseal - NO ScriptVar 'player_access_id' - Bail");
            CustomerServiceLog("CollectionLootChannel: ", "Magseal: no ScriptVar on MagSeal: Player: " + player + " attempting to loot" + item + "from" + self + ".");
            return SCRIPT_OVERRIDE;
        }
        obj_id playerId = utils.getObjIdScriptVar(self, PLAYER_ACCESS_ID);
        if (!isIdValid(playerId))
        {
            LOG("sissynoid", "Magseal - Invalid Player ID - Fail");
            CustomerServiceLog("CollectionLootChannel: ", "Magseal: invalid playerId on Magseal: Player: " + player + " attempting to loot" + item + "from" + self + ".");
            return SCRIPT_OVERRIDE;
        }
        if (player != playerId)
        {
            LOG("sissynoid", "Magseal - Player ID does not match the Access ID - Fail");
            sendSystemMessage(player, NO_ACCESS_MAGSEAL);
            return SCRIPT_OVERRIDE;
        }
        setOwner(item, player);
        return SCRIPT_CONTINUE;
    }
    public int OnLostItem(obj_id self, obj_id pInv, obj_id player, obj_id item) throws InterruptedException
    {
        obj_id[] containerContents = getContents(self);
        if (containerContents == null)
        {
            LOG("sissynoid", "Magseal - OnLostItem, Contents are 'null' - Bail");
            return SCRIPT_CONTINUE;
        }
        if (containerContents.length == 0)
        {
            LOG("sissynoid", "Magseal - OnLostItem, Container has no Items - Removing Player Access ID");
            utils.removeScriptVar(self, PLAYER_ACCESS_ID);
        }
        return SCRIPT_CONTINUE;
    }
    public int OnObjectMenuRequest(obj_id self, obj_id player, menu_info mi) throws InterruptedException
    {
        LOG("sissynoid", "Magseal - Requesting Magseal Menu");
        mi.addRootMenu(menu_info_types.ITEM_OPEN, MAGSEAL_RADIAL_OPEN);
        return SCRIPT_CONTINUE;
    }
    public int OnObjectMenuSelect(obj_id self, obj_id player, int item) throws InterruptedException
    {
        LOG("sissynoid", "ENTER Magseal Menu Selection");
        obj_id container = self;
        if (item == menu_info_types.ITEM_OPEN)
        {
            LOG("sissynoid", "Magseal - Player selected 'OPEN'");
            String planetName = getCurrentSceneName();
            obj_id keyCard = utils.getStaticItemInInventory(player, "col_magseal_keycard_" + planetName + "_02_01");
            if (!isIdValid(keyCard))
            {
                return SCRIPT_CONTINUE;
            }
            if (!utils.hasScriptVar(container, PLAYER_ACCESS_ID))
            {
                if (consumeKey(player, keyCard, planetName))
                {
                    utils.setScriptVar(container, PLAYER_ACCESS_ID, player);
                    int numLootItems = rand(lootMin, lootMax);
                    String[] lootArray = dataTableGetStringColumnNoDefaults(MAGSEAL_LOOT_TABLE, planetName);
                    if (lootArray == null || lootArray.length <= 0)
                    {
                        CustomerServiceLog("CollectionLootChannel: ", "BrokenLoot: (DataTable Error in loot_crate.script) for MagSeal Container Collection");
                        LOG("sissynoid", "Magseal - Loot Array Null or < 0 - FAIL");
                        return SCRIPT_CONTINUE;
                    }
                    for (int i = 0; i <= numLootItems; i++)
                    {
                        int random = rand(0, lootArray.length - 1);
                        LOG("sissynoid", "Magseal - Player to receive (" + numLootItems + ") items.");
                        obj_id itemId = static_item.createNewItemFunction(lootArray[random], container);
                        if (!isValidId(itemId) || !exists(itemId))
                        {
                            LOG("sissynoid", "Magseal - INVALID ITEM CREATION (" + itemId + ")");
                            CustomerServiceLog("CollectionLootChannel: ", "BrokenLoot: " + itemId + "(inValid ID)was not created for player" + player + "for magseal loot from container: " + container);
                        }
                    }
                    collection.getRandomCollectionItem(player, container, MAGSEAL_LOOT_TABLE, COLLECTION_COLUMN);
                    LOG("sissynoid", "Magseal - Generating Collection Item");
                }
                return SCRIPT_CONTINUE;
            }
            else 
            {
                obj_id currentUser = utils.getObjIdScriptVar(container, PLAYER_ACCESS_ID);
                LOG("sissynoid", "Magseal - Magseal is Currently Being Used by (" + currentUser + ")");
                if (player != currentUser)
                {
                    LOG("sissynoid", "Magseal - Player(" + player + ") is not the CurrentUser(" + currentUser + ") - FAIL");
                    sendSystemMessage(player, MAGSEAL_OCCUPIED);
                    return SCRIPT_OVERRIDE;
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_CONTINUE;
    }
    public boolean consumeKey(obj_id player, obj_id keyCard, String planetName) throws InterruptedException
    {
        obj_id container = getSelf();
        if (!isIdValid(player) || !isIdValid(keyCard))
        {
            LOG("sissynoid", "Magseal - Invalid Player or KeyCard - return False");
            return false;
        }
        decrementCount(keyCard);
        setOwner(container, player);
        sendSystemMessage(player, COUNTDOWN_HAS_BEGUN);
        dictionary dict = new dictionary();
        dict.put("player", player);
        messageTo(container, "resetContainer", dict, 120, false);
        modifyCollectionSlotValue(player, "magseal_" + planetName, 1);
        return true;
    }
    public int resetContainer(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id container = getSelf();
        if (params == null || params.isEmpty())
        {
            return SCRIPT_CONTINUE;
        }
        obj_id player = params.getObjId("player");
        obj_id[] remainingItems = getContents(container);
        if (remainingItems == null)
        {
            return SCRIPT_CONTINUE;
        }
        if (remainingItems.length == 0)
        {
            utils.removeScriptVar(container, PLAYER_ACCESS_ID);
            return SCRIPT_CONTINUE;
        }
        if (remainingItems.length > 0)
        {
            for (int k = 0; k < remainingItems.length; k++)
            {
                CustomerServiceLog("CollectionLootChannel: ", "Magseal: " + "Magseal Timer Expired for " + player + " item: " + remainingItems[k] + " was deleted - as designed.");
                destroyObject(remainingItems[k]);
            }
            utils.removeScriptVar(container, PLAYER_ACCESS_ID);
        }
        setOwner(container, obj_id.NULL_ID);
        return SCRIPT_CONTINUE;
    }
    public int initializeLootContainer(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id[] contents = getContents(self);
        if (contents != null)
        {
            for (int i = 0; i < contents.length; i++)
            {
                String template = getTemplateName(contents[i]);
                LOG("sissynoid", "Magseal - CONTAINER HAD CONTENTS UPON LOADING! (" + contents.length + ") - (" + template + ") Items");
                destroyObject(contents[i]);
            }
        }
        if (hasObjVar(self, "cash_max"))
        {
            removeObjVar(self, "cash_max");
        }
        if (hasObjVar(self, "cash_min"))
        {
            removeObjVar(self, "cash_min");
        }
        if (hasObjVar(self, "items_max"))
        {
            removeObjVar(self, "items_max");
        }
        if (hasObjVar(self, "items_min"))
        {
            removeObjVar(self, "items_min");
        }
        if (hasObjVar(self, "loot_table"))
        {
            removeObjVar(self, "loot_table");
        }
        if (hasObjVar(self, "reset_time"))
        {
            removeObjVar(self, "reset_time");
        }
        if (hasObjVar(self, "unlocked"))
        {
            removeObjVar(self, "unlocked");
        }
        return SCRIPT_CONTINUE;
    }
}
