package script.systems.loot;

import script.library.collection;
import script.library.loot;
import script.library.utils;
import script.menu_info;
import script.menu_info_types;
import script.obj_id;
import script.string_id;

import java.util.ArrayList;
import java.util.List;

public class rare_loot_chest extends script.base_script {
    public int OnObjectMenuRequest(obj_id self, obj_id player, menu_info mi) throws InterruptedException {
        if (utils.getContainingPlayer(self) == player) {
            int mnu2 = mi.addRootMenu(menu_info_types.ITEM_USE, new string_id("npe", "crate_use"));
        }
        return SCRIPT_CONTINUE;
    }

    public int OnObjectMenuSelect(obj_id self, obj_id player, int item) throws InterruptedException {
        sendDirtyObjectMenuNotification(self);
        if (item == menu_info_types.ITEM_USE)
        {
            String template = getTemplateName(self);
            int rarity = Integer.parseInt(template.replace("object/tangible/item/rare_loot_chest_", "").replace(".iff", "")) - 1;

            List<obj_id> items = new ArrayList<>();
            int numberOfItems = rand(1, 2);
            while(items.size() < numberOfItems) {
                obj_id rareItem = loot.makeRareLootItem(utils.getInventoryContainer(player), "rls/" + loot.CHEST_TYPES[rand(0,rarity)] + "_loot");
                if(rareItem != null) {
                    items.add(rareItem);
                }
            }

            obj_id[] lootedItems = new obj_id[items.size()];
            items.toArray(lootedItems);
            showLootBox(player, lootedItems);

            destroyObject(self);
            handleRareLootCollection(player, rarity + 1);
            LOG("rare_loot", "Player (" + getName(player) + ":" + player + ") opened RLS chest with rarity of " + loot.CHEST_TYPES[rarity]);
        }
        return SCRIPT_CONTINUE; 
    }

    private void handleRareLootCollection(obj_id player, int lootType) throws InterruptedException {
        String typeOpenedOne = "rare_loot_opened_one_" + lootType;
        String typeOpenedFive = "rare_loot_opened_five_" + lootType;

        modifyCollectionSlotValue(player, typeOpenedOne, 1);
        modifyCollectionSlotValue(player, typeOpenedFive, 1);
        if (getCollectionSlotValue(player, typeOpenedFive) == 5) {
            switch(lootType) {
            case 1: collection.removeCompletedCollection(player, "col_rare_loot_five"); break;
            case 2: collection.removeCompletedCollection(player, "col_exceptional_loot_five"); break;
            case 3: collection.removeCompletedCollection(player, "col_legendary_loot_five"); break;
            }
        }
    }
}