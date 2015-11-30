package script.item.loot_kits;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.sui;
import script.library.utils;

public class simple_kit extends script.base_script
{
    public simple_kit()
    {
    }
    public static final String STF = "loot_kit";
    public static final string_id INCORRECT_ITEM = new string_id(STF, "incorrect_item");
    public static final string_id ALREADY_CONTAINS = new string_id(STF, "already_contains");
    public static final string_id NEW_ITEM_CREATED = new string_id(STF, "new_item_created");
    public static final string_id ITEM_USED = new string_id(STF, "item_used");
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        return SCRIPT_CONTINUE;
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        return SCRIPT_CONTINUE;
    }
    public int OnGetAttributes(obj_id self, obj_id player, String[] names, String[] attribs) throws InterruptedException
    {
        int idx = utils.getValidAttributeIndex(names);
        if (idx == -1)
        {
            return SCRIPT_CONTINUE;
        }
        String datatable = getStringObjVar(self, "loot_kit");
        if (hasObjVar(self, "collectible"))
        {
            int one = getIntObjVar(self, "collectible.item_contained_01");
            int two = getIntObjVar(self, "collectible.item_contained_02");
            int three = getIntObjVar(self, "collectible.item_contained_03");
            int four = getIntObjVar(self, "collectible.item_contained_04");
            int five = getIntObjVar(self, "collectible.item_contained_05");
            int six = getIntObjVar(self, "collectible.item_contained_06");
            int seven = getIntObjVar(self, "collectible.item_contained_07");
            int eight = getIntObjVar(self, "collectible.item_contained_08");
            int nine = getIntObjVar(self, "collectible.item_contained_09");
            int ten = getIntObjVar(self, "collectible.item_contained_10");
            String itemNameOne = dataTableGetString(datatable, 0, "examine_item_name");
            names[idx] = itemNameOne;
            if (one == 0)
            {
                String hasItem = "No";
                attribs[idx] = hasItem;
            }
            else if (one == 1)
            {
                String hasItem = "Yes";
                attribs[idx] = hasItem;
            }
            idx++;
            if (idx >= names.length)
            {
                return SCRIPT_CONTINUE;
            }
            String itemNameTwo = dataTableGetString(datatable, 1, "examine_item_name");
            names[idx] = itemNameTwo;
            if (two == 0)
            {
                String hasItem = "No";
                attribs[idx] = hasItem;
            }
            else if (two == 1)
            {
                String hasItem = "Yes";
                attribs[idx] = hasItem;
            }
            idx++;
            if (idx >= names.length)
            {
                return SCRIPT_CONTINUE;
            }
            String itemNameThree = dataTableGetString(datatable, 2, "examine_item_name");
            names[idx] = itemNameThree;
            if (three == 0)
            {
                String hasItem = "No";
                attribs[idx] = hasItem;
            }
            else if (three == 1)
            {
                String hasItem = "Yes";
                attribs[idx] = hasItem;
            }
            idx++;
            if (idx >= names.length)
            {
                return SCRIPT_CONTINUE;
            }
            String itemNameFour = dataTableGetString(datatable, 3, "examine_item_name");
            names[idx] = itemNameFour;
            if (four == 0)
            {
                String hasItem = "No";
                attribs[idx] = hasItem;
            }
            else if (four == 1)
            {
                String hasItem = "Yes";
                attribs[idx] = hasItem;
            }
            idx++;
            if (idx >= names.length)
            {
                return SCRIPT_CONTINUE;
            }
            String itemNameFive = dataTableGetString(datatable, 4, "examine_item_name");
            names[idx] = itemNameFive;
            if (five == 0)
            {
                String hasItem = "No";
                attribs[idx] = hasItem;
            }
            else if (five == 1)
            {
                String hasItem = "Yes";
                attribs[idx] = hasItem;
            }
            idx++;
            if (idx >= names.length)
            {
                return SCRIPT_CONTINUE;
            }
            String itemNameSix = dataTableGetString(datatable, 5, "examine_item_name");
            names[idx] = itemNameSix;
            if (six == 0)
            {
                String hasItem = "No";
                attribs[idx] = hasItem;
            }
            else if (six == 1)
            {
                String hasItem = "Yes";
                attribs[idx] = hasItem;
            }
            idx++;
            if (idx >= names.length)
            {
                return SCRIPT_CONTINUE;
            }
            String itemNameSeven = dataTableGetString(datatable, 6, "examine_item_name");
            names[idx] = itemNameSeven;
            if (seven == 0)
            {
                String hasItem = "No";
                attribs[idx] = hasItem;
            }
            else if (seven == 1)
            {
                String hasItem = "Yes";
                attribs[idx] = hasItem;
            }
            idx++;
            if (idx >= names.length)
            {
                return SCRIPT_CONTINUE;
            }
            String itemNameEight = dataTableGetString(datatable, 7, "examine_item_name");
            names[idx] = itemNameEight;
            if (eight == 0)
            {
                String hasItem = "No";
                attribs[idx] = hasItem;
            }
            else if (eight == 1)
            {
                String hasItem = "Yes";
                attribs[idx] = hasItem;
            }
            idx++;
            if (idx >= names.length)
            {
                return SCRIPT_CONTINUE;
            }
            String itemNameNine = dataTableGetString(datatable, 8, "examine_item_name");
            names[idx] = itemNameNine;
            if (nine == 0)
            {
                String hasItem = "No";
                attribs[idx] = hasItem;
            }
            else if (nine == 1)
            {
                String hasItem = "Yes";
                attribs[idx] = hasItem;
            }
            idx++;
            if (idx >= names.length)
            {
                return SCRIPT_CONTINUE;
            }
            String itemNameTen = dataTableGetString(datatable, 9, "examine_item_name");
            names[idx] = itemNameTen;
            if (ten == 0)
            {
                String hasItem = "No";
                attribs[idx] = hasItem;
            }
            else if (ten == 1)
            {
                String hasItem = "Yes";
                attribs[idx] = hasItem;
            }
            idx++;
            if (idx >= names.length)
            {
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int OnAboutToReceiveItem(obj_id self, obj_id srcContainer, obj_id player, obj_id item) throws InterruptedException
    {
        if (!isIdValid(player))
        {
            return SCRIPT_CONTINUE;
        }
        if (!utils.isNestedWithin(self, player))
        {
            return SCRIPT_OVERRIDE;
        }
        String itemTemplate = getTemplateName(item);
        if (itemTemplate.equals("object/tangible/loot/misc/firework_dud_s2.iff"))
        {
            itemTemplate = "object/tangible/loot/misc/firework_dud_s1.iff";
        }
        if (itemTemplate == null)
        {
            return SCRIPT_CONTINUE;
        }
        Vector itemsNeeded = getResizeableStringArrayObjVar(self, "needs");
        if (itemsNeeded == null)
        {
            return SCRIPT_CONTINUE;
        }
        int numNeeded = itemsNeeded.size();
        boolean useful = false;
        for (int x = 0; x < numNeeded; x++)
        {
            String thisItem = ((String)itemsNeeded.get(x));
            if (thisItem.equals(itemTemplate))
            {
                useful = true;
                return SCRIPT_CONTINUE;
            }
        }
        if (useful == false)
        {
            return SCRIPT_OVERRIDE;
        }
        return SCRIPT_CONTINUE;
    }
    public int OnReceivedItem(obj_id self, obj_id srcContainer, obj_id transferer, obj_id item) throws InterruptedException
    {
        obj_id player = utils.getContainingPlayer(self);
        String itemTemplate = getTemplateName(item);
        Vector itemsNeeded = getResizeableStringArrayObjVar(self, "needs");
        if (itemsNeeded == null)
        {
            return SCRIPT_CONTINUE;
        }
        int numNeeded = itemsNeeded.size();
        for (int x = 0; x < numNeeded; x++)
        {
            String thisItem = ((String)itemsNeeded.get(x));
            if (thisItem.equals(itemTemplate))
            {
                checkForCompletion(self, player, x, item);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_CONTINUE;
    }
    public void checkForCompletion(obj_id self, obj_id player, int x, obj_id item) throws InterruptedException
    {
        if (!isIdValid(player))
        {
            return;
        }
        Vector needs = getResizeableStringArrayObjVar(self, "needs");
        if (needs == null)
        {
            return;
        }
        int numItems = needs.size();
        needs = utils.removeElementAt(needs, x);
        if (needs.size() > 0)
        {
            setObjVar(self, "needs", needs);
        }
        else 
        {
            removeObjVar(self, "needs");
        }
        if (numItems == 1)
        {
            obj_id playerInv = utils.getInventoryContainer(player);
            if (isIdValid(playerInv))
            {
                String rewardItem = getStringObjVar(self, "reward");
                obj_id reward = createObjectOverloaded(rewardItem, playerInv);
                if (isIdValid(reward))
                {
                    sendSystemMessage(player, NEW_ITEM_CREATED);
                    destroyObject(self);
                }
            }
            return;
        }
        else 
        {
        }
        return;
    }
    public int destroyPart(obj_id self, dictionary params) throws InterruptedException
    {
        return SCRIPT_CONTINUE;
    }
    public int OnAboutToLoseItem(obj_id self, obj_id srcContainer, obj_id player, obj_id item) throws InterruptedException
    {
        if (!utils.isNestedWithin(self, player))
        {
            return SCRIPT_OVERRIDE;
        }
        String itemTemplate = getTemplateName(item);
        if (itemTemplate == null)
        {
            return SCRIPT_CONTINUE;
        }
        if (itemTemplate.equals("object/tangible/loot/misc/firework_dud_s2.iff"))
        {
            itemTemplate = "object/tangible/loot/misc/firework_dud_s1.iff";
        }
        Vector overview = getResizeableStringArrayObjVar(self, "overview");
        if (overview == null)
        {
            LOG("DESIGNER_FATAL", "Simple Kit script failed to find an overview objvar on the kit it was making.");
            return SCRIPT_CONTINUE;
        }
        int totalItems = overview.size();
        if (totalItems == 0)
        {
            LOG("DESIGNER_FATAL", "Simple Kit script found an overview objvar but it was empty!");
            return SCRIPT_CONTINUE;
        }
        Vector itemsNeeded = getResizeableStringArrayObjVar(self, "needs");
        if (itemsNeeded == null)
        {
            LOG("DESIGNER_FATAL", "Simple Kit script failed to find a Needed objvar (that's actually an objvar named 'needs') on a kit it was making.");
            return SCRIPT_CONTINUE;
        }
        for (int x = 0; x < totalItems; x++)
        {
            String thisItem = ((String)overview.get(x));
            if (itemTemplate.equals(thisItem) && itemsNeeded.indexOf(thisItem) < 0)
            {
                itemsNeeded = utils.addElement(itemsNeeded, thisItem);
            }
        }
        setObjVar(self, "needs", itemsNeeded);
        return SCRIPT_CONTINUE;
    }
}
