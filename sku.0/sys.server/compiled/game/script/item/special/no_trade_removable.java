package script.item.special;

import script.obj_id;
import script.string_id;
import script.menu_info;
import script.menu_info_types;

/**
 * Allows items that previously had the "noTrade" flag to be made tradeable by
 * their original owner.
 * 
 * Automatically attached to objects in no_trade_removable.tab
 */
public class no_trade_removable extends script.base_script {
    public no_trade_removable() {
    }

    // Using one of the unused reserved entries.
    public static final int RADIAL_MENU_ENTRY = menu_info_types.SERVER_MENU47;
    public static final string_id REMOVE_NO_TRADE = new string_id("ui_radial", "no_trade_removable");

    public int OnObjectMenuRequest(obj_id self, obj_id player, menu_info mi) throws InterruptedException {
        if (hasObjVar(self, "noTrade") && (getOwner(self) == player || isGod(player))) {
            mi.addRootMenu(RADIAL_MENU_ENTRY, REMOVE_NO_TRADE);
        }
        return SCRIPT_CONTINUE;
    }

    public int OnObjectMenuSelect(obj_id self, obj_id player, int selectedMenuItem) throws InterruptedException {
        if (selectedMenuItem == RADIAL_MENU_ENTRY) {
            if (!hasObjVar(self, "noTrade")) {
                sendSystemMessage(player, "This item is already tradeable.", null);
                return SCRIPT_CONTINUE;
            }

            if (getOwner(self) != player && !isGod(player)) {
                sendSystemMessage(player, "This item does not belong to you.", null);
                return SCRIPT_CONTINUE;
            }

            removeObjVar(self, "noTrade");
            sendSystemMessage(player, "This item is now tradable.", null);
        }
        return SCRIPT_CONTINUE;
    }
}
