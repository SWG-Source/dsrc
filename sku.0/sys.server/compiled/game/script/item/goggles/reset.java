package script.item.goggles;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.utils;
import script.library.features;

public class reset extends script.base_script
{
    public reset()
    {
    }
    public static final String[] COLLECTOR_EDITION_ITEMS = 
    {
        "object/tangible/wearables/goggles/goggles_s01.iff",
        "object/tangible/wearables/goggles/goggles_s02.iff",
        "object/tangible/wearables/goggles/goggles_s03.iff"
    };
    public static final String[] JP_COLLECTOR_EDITION_ITEMS = 
    {
        "object/tangible/wearables/goggles/goggles_s04.iff",
        "object/tangible/wearables/goggles/goggles_s05.iff",
        "object/tangible/wearables/goggles/goggles_s06.iff"
    };
    public int createNewGoggles(obj_id self, obj_id target, String params, float defaultTime) throws InterruptedException
    {
        boolean createFailed = false;
        if (features.isCollectorEdition(self) || features.isJPCollectorEdition(self))
        {
            if (features.isCollectorEdition(self))
            {
                obj_id[] allItems = utils.getAllItemsInBankAndInventory(self);
                obj_id inventory = getObjectInSlot(self, "inventory");
                for (int i = 0; i < COLLECTOR_EDITION_ITEMS.length; i++)
                {
                    if (checkItems(allItems, COLLECTOR_EDITION_ITEMS[i]))
                    {
                        continue;
                    }
                    obj_id newItem = createObject(COLLECTOR_EDITION_ITEMS[i], inventory, "");
                    if (!isIdValid(newItem))
                    {
                        createFailed = true;
                    }
                }
            }
            if (features.isJPCollectorEdition(self))
            {
                obj_id[] allItems = utils.getAllItemsInBankAndInventory(self);
                obj_id inventory = getObjectInSlot(self, "inventory");
                for (int i = 0; i < JP_COLLECTOR_EDITION_ITEMS.length; i++)
                {
                    if (checkItems(allItems, JP_COLLECTOR_EDITION_ITEMS[i]))
                    {
                        continue;
                    }
                    obj_id newItem = createObject(JP_COLLECTOR_EDITION_ITEMS[i], inventory, "");
                    if (!isIdValid(newItem))
                    {
                        createFailed = true;
                    }
                }
            }
        }
        else 
        {
            sendSystemMessage(self, new string_id("error_message", "create_goggle_non_se"));
        }
        if (createFailed)
        {
            sendSystemMessage(self, new string_id("error_message", "create_goggle_fail"));
        }
        return SCRIPT_CONTINUE;
    }
    public boolean checkItems(obj_id[] itemList, String template) throws InterruptedException
    {
        for (int i = 0; i < itemList.length; i++)
        {
            String itemTemplate = getTemplateName(itemList[i]);
            if (itemTemplate.equals(template))
            {
                return true;
            }
        }
        return false;
    }
}
