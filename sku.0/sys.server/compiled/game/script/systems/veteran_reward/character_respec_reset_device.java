package script.systems.veteran_reward;

import script.menu_info;
import script.menu_info_types;
import script.obj_id;

public class character_respec_reset_device extends script.base_script {

    public character_respec_reset_device() {}

    public int OnObjectMenuRequest(obj_id self, obj_id player, menu_info item) throws InterruptedException {
        item.addRootMenu(menu_info_types.ITEM_USE, null);
        return SCRIPT_CONTINUE;
    }

    public int OnObjectMenuSelect(obj_id self, obj_id player, int item) throws InterruptedException
    {
        if (item == menu_info_types.ITEM_USE)
        {
            // There is no way to know who the owner of this item is... expect that the container it is in is owned by the player.
            if(!hasObjVar(player, "respecDeviceUsed")) {
                if(getOwner(getTopMostContainer(self)) == player) {
                    // ok to reset respec.
                    if(destroyObject(self)) {
                        setObjVar(player, "respecDeviceUsed", 1);
                        setObjVar(player, "respecsBought", 0);
                        sendSystemMessage(player, "Your Respect Counter has been reset.", null);
                    };
                } else {
                    sendSystemMessage(player, "You are not the owner of this device.", null);
                }
            } else {
                sendSystemMessage(player, "Your Respec Counter has already been reset on this character.", null);
            }
        }
        return SCRIPT_CONTINUE;
    }
}
