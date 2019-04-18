package script.systems.storyteller;

import script.library.static_item;
import script.library.storyteller;
import script.library.trial;
import script.library.utils;
import script.menu_info;
import script.menu_info_types;
import script.obj_id;
import script.string_id;

import java.util.Vector;

public class theater_controller extends script.base_script
{
    public theater_controller()
    {
    }
    public int OnObjectMenuRequest(obj_id self, obj_id player, menu_info mi) throws InterruptedException
    {
        if (isInBuildoutMode(self))
        {
            mi.addRootMenu(menu_info_types.ITEM_USE, new string_id("storyteller", "recover_theater"));
        }
        else 
        {
            mi.addRootMenu(menu_info_types.ITEM_USE, new string_id("storyteller", "destroy_theater"));
        }
        return SCRIPT_CONTINUE;
    }
    public int OnObjectMenuSelect(obj_id self, obj_id player, int item) throws InterruptedException
    {
        if (item == menu_info_types.ITEM_USE)
        {
            if (isInBuildoutMode(self))
            {
                recoverTheater(self, player);
            }
            else 
            {
                clearTheaterArea(self);
            }
        }
        return SCRIPT_CONTINUE;
    }
    public boolean isInBuildoutMode(obj_id theater) throws InterruptedException
    {
        return getBooleanObjVar(theater, storyteller.THEATER_MODE);
    }
    public String[] recoverTheater(obj_id theater, obj_id player) throws InterruptedException
    {
        obj_id[] cellIds = getCellIds(theater);
        Vector allObjects = new Vector();
        allObjects.setSize(0);
        if (cellIds != null && cellIds.length > 0)
        {
            obj_id[] interiorObjects = trial.getAllObjectsInDungeon(theater);
            if (interiorObjects != null && interiorObjects.length > 0)
            {
                for (obj_id interiorObject : interiorObjects) {
                    utils.addElement(allObjects, interiorObject);
                }
            }
        }
        float range = getTheaterBuildoutRange(getStaticItemName(theater));
        obj_id[] objectsInRange = getObjectsInRange(getLocation(theater), range);
        if (objectsInRange != null && objectsInRange.length > 0)
        {
            for (obj_id obj_id : objectsInRange) {
                utils.addElement(allObjects, obj_id);
            }
        }
        obj_id[] filteredList = getStorytellerObjectsFromArray(allObjects);
        if (filteredList == null || filteredList.length == 0)
        {
            return null;
        }
        String[] storytellerItems = getStorytellerItemListFromArray(filteredList);
        if (storytellerItems == null || storytellerItems.length == 0)
        {
            return null;
        }
        trial.cleanupObject(filteredList);
        obj_id inventory = utils.getInventoryContainer(player);
        for (String storytellerItem : storytellerItems) {
            static_item.createNewItemFunction(storytellerItem, inventory);
        }
        return storytellerItems;
    }
    public float getTheaterBuildoutRange(String item) throws InterruptedException
    {
        return dataTableGetFloat(storyteller.STORYTELLER_DATATABLE, item, "buildout_radius");
    }
    public obj_id[] getStorytellerObjectsFromArray(Vector allObjects) throws InterruptedException
    {
        Vector filteredList = new Vector();
        filteredList.setSize(0);
        for (Object allObject : allObjects) {
            if (!isIdValid(((obj_id) allObject)) || !exists(((obj_id) allObject))) {
                continue;
            }
            String itemName = getStaticItemName(((obj_id) allObject));
            int row = dataTableSearchColumnForString(itemName, "name", storyteller.STORYTELLER_DATATABLE);
            if (row == -1) {
                continue;
            }
            utils.addElement(filteredList, ((obj_id) allObject));
        }
        obj_id[] _filteredList = new obj_id[0];
        if (filteredList != null)
        {
            _filteredList = new obj_id[filteredList.size()];
            filteredList.toArray(_filteredList);
        }
        return _filteredList;
    }
    public String[] getStorytellerItemListFromArray(obj_id[] objectList) throws InterruptedException
    {
        String[] storytellerObjects = new String[objectList.length];
        for (int i = 0; i < objectList.length; i++)
        {
            storytellerObjects[i] = getStaticItemName(objectList[i]);
        }
        return storytellerObjects;
    }
    public void clearTheaterArea(obj_id theater) throws InterruptedException
    {
        float range = getTheaterBuildoutRange(getStaticItemName(theater));
        obj_id[] objects = getObjectsInRange(getLocation(theater), range);
        for (obj_id object : objects) {
            if (storyteller.isAnyStorytellerItem(object) && object != theater) {
                trial.cleanupObject(object);
            }
        }
        trial.cleanupObject(theater);
    }
}
