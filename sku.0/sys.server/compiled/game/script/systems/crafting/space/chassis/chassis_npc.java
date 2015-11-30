package script.systems.crafting.space.chassis;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.create;
import script.library.utils;
import script.library.space_crafting;
import script.library.space_transition;

public class chassis_npc extends script.base_script
{
    public chassis_npc()
    {
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        return SCRIPT_CONTINUE;
    }
    public int OnGiveItem(obj_id self, obj_id deed, obj_id player) throws InterruptedException
    {
        obj_id playerInv = utils.getInventoryContainer(player);
        if (hasObjVar(deed, "isShipDeed"))
        {
            obj_id[] oldPcds = space_transition.findShipControlDevicesForPlayer(player);
            if (oldPcds != null && oldPcds.length > 0)
            {
                debugSpeakMsg(self, "You already have a ship.");
                return SCRIPT_CONTINUE;
            }
            obj_id datapad = utils.getDatapad(player);
            if (isIdValid(datapad))
            {
                obj_id pcd = createObjectOverloaded("object/intangible/ship/xwing_pcd.iff", datapad);
                if (!isIdValid(pcd))
                {
                    debugSpeakMsg(self, "Failed to create pcd");
                }
                else 
                {
                    obj_id xwing = createObjectOverloaded("object/ship/player/player_xwing.iff", pcd);
                    space_crafting.uninstallAll(xwing);
                    utils.copyObjVarList(deed, xwing, "ship_comp");
                    destroyObject(deed);
                    debugSpeakMsg(self, "Congratulations!  You will find a new ship control device in your datapad.");
                    if (isIdValid(xwing))
                    {
                        setOwner(xwing, player);
                    }
                    else 
                    {
                        debugSpeakMsg(self, "Failed to create xwing");
                    }
                }
            }
        }
        return SCRIPT_CONTINUE;
    }
}
