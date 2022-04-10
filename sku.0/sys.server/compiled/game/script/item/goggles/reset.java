package script.item.goggles;

import script.library.features;
import script.library.utils;
import script.obj_id;
import script.string_id;

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
    
        String config = getConfigSetting("GameServer", "seGogglesReward");
        if (config == null || !config.equals("true"))
        {
            return SCRIPT_CONTINUE;
        }
    
        boolean createFailed = false;
        if (features.isCollectorEdition(self) || features.isJPCollectorEdition(self))
        {
            if (features.isCollectorEdition(self))
            {
                obj_id[] allItems = utils.getAllItemsInBankAndInventory(self);
                obj_id inventory = getObjectInSlot(self, "inventory");
                for (String collectorEditionItem : COLLECTOR_EDITION_ITEMS) {
                    if (checkItems(allItems, collectorEditionItem)) {
                        continue;
                    }
                    obj_id newItem = createObject(collectorEditionItem, inventory, "");
                    if (!isIdValid(newItem)) {
                        createFailed = true;
                    }
                }
            }
            if (features.isJPCollectorEdition(self))
            {
                obj_id[] allItems = utils.getAllItemsInBankAndInventory(self);
                obj_id inventory = getObjectInSlot(self, "inventory");
                for (String jpCollectorEditionItem : JP_COLLECTOR_EDITION_ITEMS) {
                    if (checkItems(allItems, jpCollectorEditionItem)) {
                        continue;
                    }
                    obj_id newItem = createObject(jpCollectorEditionItem, inventory, "");
                    if (!isIdValid(newItem)) {
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
        for (obj_id obj_id : itemList) {
            String itemTemplate = getTemplateName(obj_id);
            if (itemTemplate.equals(template)) {
                return true;
            }
        }
        return false;
    }
}
