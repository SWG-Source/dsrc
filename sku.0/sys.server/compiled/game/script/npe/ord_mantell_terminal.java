package script.npe;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.create;
import script.library.utils;
import script.library.npe;
import script.library.groundquests;
import script.library.space_quest;
import script.library.space_utils;
import script.library.space_crafting;

public class ord_mantell_terminal extends script.base_script
{
    public ord_mantell_terminal()
    {
    }
    public static final string_id LAUNCH = new string_id("npe", "launch");
    public int OnObjectMenuRequest(obj_id self, obj_id player, menu_info mi) throws InterruptedException
    {
        mi.addRootMenu(menu_info_types.ITEM_USE, LAUNCH);
        return SCRIPT_CONTINUE;
    }
    public int OnObjectMenuSelect(obj_id self, obj_id player, int item) throws InterruptedException
    {
        if (item == menu_info_types.ITEM_USE)
        {
            if (!space_utils.hasShip(player))
            {
                string_id noCanFly = new string_id("npe", "cantfly");
                sendSystemMessage(player, noCanFly);
                return SCRIPT_CONTINUE;
            }
            obj_id vcdShipId = space_utils.hasUsableShip(player);
            obj_id[] shipId = getContents(vcdShipId);
            if (shipId != null && shipId.length != 0)
            {
                space_crafting.repairDamage(player, shipId[0], 1.0f);
            }
            if (checkGod(player))
                return SCRIPT_CONTINUE;

            location ordLoc = new location(200, 150, 600);
            if (npe.movePlayerFromSharedStationToOrdMantellSpace(player, ordLoc)) {
                // Only update quests if the player has successfully moved into OM space
                groundquests.sendSignal(player, "accessedSpace");
            } else {
                LOG("npe", "Could not move to OM space!");
            }
        }
        return SCRIPT_CONTINUE;
    }
    public boolean checkGod(obj_id self) throws InterruptedException
    {
        if (isGod(self))
        {
            sendSystemMessageTestingOnly(self, "Please turn off god mode when moving between npe locations. God mode and instances do not get along");
            return true;
        }
        return false;
    }
}
