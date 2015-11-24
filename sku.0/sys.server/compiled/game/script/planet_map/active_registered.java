package script.planet_map;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.utils;
import script.library.planetary_map;

public class active_registered extends script.base_script
{
    public active_registered()
    {
    }
    public static final string_id SID_LOCATION_REGISTRATION_CANCELLED = new string_id("travel", "location_registration_cancelled");
    public int OnLostItem(obj_id self, obj_id destContainer, obj_id transferer, obj_id item) throws InterruptedException
    {
        if (utils.isNestedWithin(destContainer, self))
        {
            return SCRIPT_CONTINUE;
        }
        removeRegistrant(self, item);
        return SCRIPT_CONTINUE;
    }
    public int OnTriggerVolumeExited(obj_id self, String volName, obj_id who) throws InterruptedException
    {
        if (who == self)
        {
            return SCRIPT_CONTINUE;
        }
        if (volName.equals("camp_" + self))
        {
            removeRegistrant(self, who);
        }
        return SCRIPT_CONTINUE;
    }
    public int removeRegistrant(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id objItem = params.getObjId("objItem");
        removeRegistrant(self, objItem);
        return SCRIPT_CONTINUE;
    }
    public void removeRegistrant(obj_id self, obj_id item) throws InterruptedException
    {
        if (!isIdValid(self) || !isIdValid(item) || !isPlayer(item))
        {
            return;
        }
        Vector registrants = utils.getResizeableObjIdBatchScriptVar(self, "registrants");
        if (registrants != null && registrants.size() > 0)
        {
            int idx = utils.getElementPositionInArray(registrants, item);
            if (idx > -1)
            {
                registrants = utils.removeElementAt(registrants, idx);
                if (registrants == null || registrants.size() == 0)
                {
                    utils.removeBatchScriptVar(self, "registrants");
                    if (planetary_map.updateFacilityActive(self, false))
                    {
                        detachScript(self, "planet_map.active_registered");
                    }
                }
                else 
                {
                    utils.setBatchScriptVar(self, "registrants", registrants);
                }
                if (item.isLoaded())
                {
                    sendSystemMessage(item, SID_LOCATION_REGISTRATION_CANCELLED);
                    utils.removeScriptVar(item, "registerWithLocation");
                }
                return;
            }
        }
    }
}
