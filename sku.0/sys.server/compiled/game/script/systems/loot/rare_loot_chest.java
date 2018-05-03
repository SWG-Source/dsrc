package script.systems.loot;

import script.library.badge;
import script.library.collection;
import script.library.loot;
import script.library.utils;
import script.menu_info;
import script.menu_info_types;
import script.obj_id;
import script.string_id;

public class rare_loot_chest extends script.base_script {
    public int OnObjectMenuRequest(obj_id self, obj_id player, menu_info mi) throws InterruptedException {
        if (utils.getContainingPlayer(self) == player) {
            int mnu2 = mi.addRootMenu(menu_info_types.ITEM_USE, new string_id("npe", "crate_use"));
        }
        return SCRIPT_CONTINUE;
    }

    public int OnObjectMenuSelect(obj_id self, obj_id player, int item) throws InterruptedException {
        sendDirtyObjectMenuNotification(self);
        if (item == menu_info_types.ITEM_USE) {
            String template = getTemplateName(self);
            int rarity = 0;
            for (int i = 2; i < 4; i++) {
                if (template.equals("object/tangible/item/rare_loot_chest_" + i + ".iff")) {
                    rarity = i - 1;
                }
            }
            int numberOfItems = rand(1, 2);
            for (int i = 0; i < numberOfItems; i++) {
                loot.makeLootInContainer(utils.getInventoryContainer(player), "rls/" + loot.CHEST_TYPES[rand(0,rarity)] + "_loot", 1, 1);
            }
            destroyObject(self);
            handleRareLootCollection(player, rarity + 1);
            LOG("rare_loot", "Player (" + getName(player) + ":" + player + ") opened RLS chest with rarity of " + loot.CHEST_TYPES[rarity]);
        }
        return SCRIPT_CONTINUE;
    }

    private void handleRareLootCollection(obj_id player, int lootType) throws InterruptedException {
        if (getCollectionSlotValue(player, "rare_loot_opened_one_" + lootType) == 0) {
            modifyCollectionSlotValue(player, "rare_loot_opened_one_" + lootType, 1);
        }

        if (!badge.hasBadge(player, "bdg_rare_loot")) {
            for (int i = 1; i < 5; i++) {
                if (!hasCompletedCollection(player, "rare_loot_opened_one_" + i)) {
                    break;
                } else if (i == 4) {
                    badge.grantBadge(player, "bdg_rare_loot");
                }
            }
        }

        long numberOfChests = getCollectionSlotValue(player, "rare_loot_opened_five_" + lootType);
        modifyCollectionSlotValue(player, "rare_loot_opened_five_" + lootType, ++numberOfChests);

        if (hasCompletedCollection(player, "rare_loot_opened_five_" + lootType)) {
            loot.createRareLootChest(player, lootType);
            collection.removeCompletedCollection(player, "rare_loot_opened_five_" + lootType);
        }
    }
}