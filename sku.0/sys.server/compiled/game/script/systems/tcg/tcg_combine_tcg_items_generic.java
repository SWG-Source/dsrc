package script.systems.tcg;

import script.*;
import script.library.static_item;
import script.library.utils;

import java.util.Vector;

public class tcg_combine_tcg_items_generic extends script.base_script
{
    public tcg_combine_tcg_items_generic()
    {
    }
    public static final boolean LOGGING_ON = true;
    public static final String LOGGING_CATEGORY = "combine";
    public static final String OBJVAR_SET_NAME = "tcg.setName";
    public static final String DATATABLE_COMBINE_ITEM = "datatables/tcg/combine_item.iff";
    public static final String COMBINE = "combine";
    public static final String OBJVAR_COMBINE_ITEM = "tcg.combineItemTemplatePattern";
    public static final int NUM_COMBINE_ITEMS = 4;
    public int OnObjectMenuRequest(obj_id self, obj_id player, menu_info mi) throws InterruptedException
    {
        blog("combine: OnObjectMenuRequest - Init");
        if (!utils.isNestedWithinAPlayer(self))
        {
            return SCRIPT_CONTINUE;
        }
        blog("combine: OnObjectMenuRequest - Is Nested");
        obj_id owner = utils.getContainingPlayer(self);
        if (!isValidId(owner) || !exists(owner))
        {
            return SCRIPT_CONTINUE;
        }
        if (owner != player)
        {
            return SCRIPT_CONTINUE;
        }
        blog("combine: OnObjectMenuRequest - Owner: " + owner);
        if (hasObjVar(self, COMBINE) && (getStringObjVar(self, COMBINE)).startsWith("true"))
        {
            mi.addRootMenu(menu_info_types.SERVER_MENU10, new string_id("tcg", "combine_tcg_items"));
        }
        return SCRIPT_CONTINUE;
    }
    public int OnObjectMenuSelect(obj_id self, obj_id player, int item) throws InterruptedException
    {
        if (item != menu_info_types.SERVER_MENU10)
        {
            return SCRIPT_CONTINUE;
        }
        if (!utils.isNestedWithinAPlayer(self))
        {
            return SCRIPT_CONTINUE;
        }
        blog("combine: OnObjectMenuSelect - Is Nested");
        obj_id owner = utils.getContainingPlayer(self);
        if (!isValidId(owner) || !exists(owner))
        {
            return SCRIPT_CONTINUE;
        }
        if (owner != player)
        {
            return SCRIPT_CONTINUE;
        }
        blog("combine: OnObjectMenuSelect - Owner: " + owner);
        String templatePattern = getStringObjVar(self, OBJVAR_COMBINE_ITEM);
        blog("combine: OnObjectMenuSelect - templatePattern: " + templatePattern);
        obj_id[] allPossibleMatches = utils.getAllItemsPlayerHasByTemplateStartsWith(owner, templatePattern);
        if(allPossibleMatches == null || allPossibleMatches.length == 0){
            sendSystemMessage(player, new string_id("tcg", "combination_not_enough_items"));
            return SCRIPT_CONTINUE;
        }
        blog("combine: OnObjectMenuSelect - allPossibleMatches length: " + allPossibleMatches.length);
        if (allPossibleMatches.length < NUM_COMBINE_ITEMS)
        {
            sendSystemMessage(player, new string_id("tcg", "combination_not_enough_items"));
            return SCRIPT_CONTINUE;
        }
        String setName = getStringObjVar(self, OBJVAR_SET_NAME);
        if (setName == null || setName.length() <= 0)
        {
            return SCRIPT_CONTINUE;
        }
        obj_id[] combinableObjects = getListOfCombinableObjects(allPossibleMatches);
        if (combinableObjects == null || combinableObjects.length <= 0)
        {
            CustomerServiceLog("tcg", setName + " Combination Attempt Failed for: " + owner + " " + getPlayerName(owner) + ", reason: not enough combinable objects in inventory.");
            sendSystemMessage(player, new string_id("tcg", "combination_not_enough_items"));
            return SCRIPT_CONTINUE;
        }
        blog("combine: OnObjectMenuSelect - combinableObjects.length: " + combinableObjects.length);
        if (!getCorrectCombination(combinableObjects, owner, setName))
        {
            sendSystemMessage(player, new string_id("tcg", "combination_failed"));
            return SCRIPT_CONTINUE;
        }
        sendSystemMessage(player, new string_id("tcg", "combination_success"));
        blog("combine: OnObjectMenuSelect - SUCCESS");
        return SCRIPT_CONTINUE;
    }
    public obj_id[] getListOfCombinableObjects(obj_id[] allPossibleMatches) throws InterruptedException
    {
        blog("combine: getListOfCombinableObjects - init");
        if (allPossibleMatches == null || allPossibleMatches.length <= 0)
        {
            return null;
        }
        Vector combinableObjects = new Vector();
        combinableObjects.setSize(0);
        for (obj_id possibleMatch : allPossibleMatches) {
            if (hasObjVar(possibleMatch, COMBINE) && (getStringObjVar(possibleMatch, COMBINE)).startsWith("true")) {
                utils.addElement(combinableObjects, possibleMatch);
            }
        }
        blog("combine: getListOfCombinableObjects - List size is " + combinableObjects.size());
        if (combinableObjects.size() < NUM_COMBINE_ITEMS)
        {
            blog("combine: getListOfCombinableObjects - List shorter than 4 so quitting.");
            return null;
        }
        blog("combine: getListOfCombinableObjects - List longer or equal to 4 so continuing");
        return utils.toStaticObjIdArray(combinableObjects);
    }
    public boolean getCorrectCombination(obj_id[] combinableObjects, obj_id owner, String setName) throws InterruptedException
    {
        blog("combine: getCorrectCombination - init");
        if (combinableObjects == null || combinableObjects.length <= 0)
        {
            return false;
        }
        if (!isValidId(owner) || !exists(owner))
        {
            return false;
        }
        if (setName == null || setName.length() <= 0)
        {
            return false;
        }
        int row = dataTableSearchColumnForString(setName, "setNames", DATATABLE_COMBINE_ITEM);
        if (row < 0)
        {
            return false;
        }
        dictionary tableData = dataTableGetRow(DATATABLE_COMBINE_ITEM, row);
        Vector toBeCombinedList = new Vector();
        toBeCombinedList.setSize(0);
        boolean[] items = {false, false, false, false};
        obj_id potentialItem;
        String[] templates = new String[4];
        // get our template names for this collection.
        for(int i = 0; i < 4; i++){
            templates[i] = tableData.getString("template_" + (i + 1));
        }
        // iterate through the entire list of the items found that may be legit
        for(int i = 0; i < combinableObjects.length; i++){
            if(combinableObjects[i] == null) continue;
            // make sure we are still looking for our items - if not, break and continue with combining
            if (items[0] && items[1] && items[2] && items[3]) break;
            potentialItem = combinableObjects[i];
            CustomerServiceLog("tcg", setName + " Checking item: " + getTemplateName(potentialItem));
            if (owner != utils.getContainingPlayer(potentialItem)) {
                combinableObjects[i] = null;
                CustomerServiceLog("tcg", setName + " Item " + getTemplateName(potentialItem) + " is not contained in the player's inventory so skipping.");
                continue;
            }
            // iterate through the items needed to see if we have a match or not
            for(int k = 0; k < 4; k++) {
                // don't evaluate against items already found.
                if (!items[k]){
                    CustomerServiceLog("tcg", setName + " Checking if item (" + getTemplateName(potentialItem) + ") is equivalent to set item #" + (k+1) + " (" + templates[k] + ")");
                    if (getTemplateName(potentialItem).equals(templates[k])) {
                        // user has the item
                        items[k] = true;
                        utils.addElement(toBeCombinedList, potentialItem);
                        CustomerServiceLog("tcg", setName + " Combination - Player: " + owner + " " + getPlayerName(owner) + " has object #" + (i + 1) + " (" + potentialItem + ")in inventory");
                        break;
                    }
                }
            }
        }
        if (toBeCombinedList.size() < NUM_COMBINE_ITEMS)
        {
            CustomerServiceLog("tcg", setName + " Combination Failed - Player: " + owner + " " + getPlayerName(owner) + " has only: " + toBeCombinedList.size() + " items that can be combined.");
            return false;
        }
        if (items[0] && items[1] && items[2] && items[3])
        {
            String reward = tableData.getString("reward_item");
            CustomerServiceLog("tcg", setName + " Combination - Player: " + owner + " " + getPlayerName(owner) + " has all 4 items needed to get " + reward + ". Green light for combination process.");
            obj_id[] combinableList = utils.toStaticObjIdArray(toBeCombinedList);
            return combineTheCombinableObjects(combinableList, owner, setName, reward);
        }
        return false;
    }
    public boolean combineTheCombinableObjects(obj_id[] combinableList, obj_id owner, String setName, String tableReward) throws InterruptedException
    {
        blog("combine: combineTheCombinableObjects - init");
        if (!isValidId(owner) || !exists(owner))
        {
            CustomerServiceLog("tcg", setName + " Combination Failed - Last process needed for combine function received a NULL for owner.");
            return false;
        }
        if (combinableList == null || combinableList.length <= 0)
        {
            CustomerServiceLog("tcg", setName + " Combination Failed - Last process needed for combine function received a NULL combinableList for Player: " + owner + " " + getPlayerName(owner) + ".");
            return false;
        }
        if (combinableList.length != NUM_COMBINE_ITEMS)
        {
            CustomerServiceLog("tcg", setName + " Combination Failed - Last process needed for combine function received a combinableList of less than 4 items for Player: " + owner + " " + getPlayerName(owner) + ".");
            return false;
        }
        if (setName == null || setName.length() <= 0)
        {
            CustomerServiceLog("tcg", setName + " Combination Failed - Last process needed for combine function received a null setName for Player: " + owner + " " + getPlayerName(owner) + ".");
            return false;
        }
        if (tableReward == null || tableReward.length() <= 0)
        {
            CustomerServiceLog("tcg", setName + " Combination Failed - Last process needed for combine function received a null tableReward for Player: " + owner + " " + getPlayerName(owner) + ".");
            return false;
        }
        int checkSum = 0;
        for (obj_id combinableItem : combinableList) {
            if (!isValidId(combinableItem) || !exists(combinableItem)) {
                CustomerServiceLog("tcg", setName + " Combination ERROR - Object (" + combinableItem + ") No longer exists for combine process. Possible attempt to exploit involving Player: " + owner + " " + getPlayerName(owner) + ".");
                continue;
            }
            if (owner != utils.getContainingPlayer(combinableItem)) {
                CustomerServiceLog("tcg", setName + " Combination ERROR - Object (" + combinableItem + ") No longer exists in player inventory for the combine process. Possible attempt to exploit involving Player: " + owner + " " + getPlayerName(owner) + ".");
                continue;
            }
            if (!hasObjVar(combinableItem, COMBINE) && (getStringObjVar(combinableItem, COMBINE)).startsWith("true")) {
                CustomerServiceLog("tcg", setName + " Combination ERROR - Object (" + combinableItem + ") No longer has objvar allowing combination process. Possible attempt to exploit involving Player: " + owner + " " + getPlayerName(owner) + ".");
                continue;
            }
            checkSum++;
        }
        if (checkSum != NUM_COMBINE_ITEMS)
        {
            CustomerServiceLog("tcg", setName + " Combination Failed - One or more objects failed to be combined. This may be due to a catestrophic error or Player: " + owner + " " + getPlayerName(owner) + " attempting to subvert/exploit systems.");
            return false;
        }
        obj_id playerInv = utils.getInventoryContainer(owner);
        if (!isValidId(playerInv) || !exists(playerInv))
        {
            CustomerServiceLog("tcg", setName + " Combination Failed - Reward Creation failed for Player: " + owner + " " + getPlayerName(owner) + ". Reason: Player Inventory invalid.");
            return false;
        }
        obj_id reward = static_item.createNewItemFunction(tableReward, playerInv, null);
        if (!isValidId(reward) || !exists(reward))
        {
            CustomerServiceLog("tcg", setName + " Combination Failed - Reward creation failed for Player: " + owner + " " + getPlayerName(owner) + ". Reason: reward could not be created in Player Inventory.");
            return false;
        }
        CustomerServiceLog("tcg", setName + " Combination Success - Reward creation success. reward Object: (" + reward + ") in player inventory: " + playerInv + " for Player: " + owner + " " + getPlayerName(owner) + ".");
        for (obj_id item : combinableList) {
            if (!isValidId(item) || !exists(item)) {
                CustomerServiceLog("tcg", setName + " Combination ERROR - Combine object: " + item + " could not be found for update. This object was last owned by Player: " + owner + " " + getPlayerName(owner) + " and needed to be updated to avoid reuse in the combination process.");
                continue;
            }
            setObjVar(item, COMBINE, "false");
            CustomerServiceLog("tcg", setName + " Combination - Combine object: " + item + " was successfully updated to remove objvar. This object should no longer allow for combination functionality. Object owned by Player: " + owner + " " + getPlayerName(owner) + ".");
        }
        CustomerServiceLog("tcg", setName + " Combination SUCCESS - All objects combined for Player: " + owner + " " + getPlayerName(owner) + ". reward object: " + reward + " created in player inventory, objects used in process should be updated.");
        return true;
    }
    public boolean blog(String msg) throws InterruptedException
    {
        if (LOGGING_ON && msg != null && !msg.equals(""))
        {
            LOG(LOGGING_CATEGORY, msg);
        }
        return true;
    }
}
