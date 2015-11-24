package script.item.publish_gift;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.create;
import script.library.utils;
import script.library.player_structure;
import script.library.sui;
import script.library.static_item;

public class homing_beacon extends script.base_script
{
    public homing_beacon()
    {
    }
    public static final string_id NOT_OWNER = new string_id("spam", "homing_not_owner");
    public static final string_id BEACON_ACTIVE = new string_id("spam", "homing_activated");
    public static final string_id BEACON_DESTROY = new string_id("spam", "homing_destroy");
    public static final string_id BEACON_DEACTIVE = new string_id("spam", "homing_deactivated");
    public int OnAttach(obj_id self) throws InterruptedException
    {
        return SCRIPT_CONTINUE;
    }
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        return SCRIPT_CONTINUE;
    }
    public int OnAboutToBeTransferred(obj_id self, obj_id destContainer, obj_id transferer) throws InterruptedException
    {
        if (!utils.isNestedWithinAPlayer(destContainer))
        {
            location loc = getLocation(transferer);
            obj_id cell = loc.cell;
            obj_id house = getTopMostContainer(destContainer);
            if (isIdValid(cell))
            {
                if (destContainer == cell)
                {
                    if (isIdValid(house) && (player_structure.isBuilding(house)))
                    {
                        if (getOwner(house) == transferer)
                        {
                            String planet = getCurrentSceneName();
                            location houseLoc = getLocation(house);
                            setObjVar(transferer, "homingBeacon.planet", planet);
                            setObjVar(transferer, "homingBeacon.houseLoc", houseLoc);
                            setObjVar(transferer, "homingBeacon.houseId", house);
                            sendSystemMessage(transferer, BEACON_ACTIVE);
                        }
                        else 
                        {
                            sendSystemMessage(transferer, NOT_OWNER);
                            return SCRIPT_OVERRIDE;
                        }
                    }
                }
            }
            else if (hasObjVar(transferer, "homingBeacon"))
            {
                removeObjVar(transferer, "homingBeacon");
                sendSystemMessage(transferer, BEACON_DEACTIVE);
            }
        }
        else if (utils.isNestedWithinAPlayer(destContainer))
        {
            if (hasObjVar(transferer, "homingBeacon"))
            {
                removeObjVar(transferer, "homingBeacon");
                sendSystemMessage(transferer, BEACON_DEACTIVE);
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int OnDestroy(obj_id self) throws InterruptedException
    {
        String myTemplate = static_item.getStaticItemName(self);
        obj_id player = utils.getContainingPlayer(self);
        if (myTemplate.equals("item_vet_homing_beacon_03_01"))
        {
            if (isGod(player))
            {
                return SCRIPT_CONTINUE;
            }
            sendSystemMessage(player, BEACON_DESTROY);
            return SCRIPT_OVERRIDE;
        }
        return SCRIPT_CONTINUE;
    }
}
