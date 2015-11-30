package script.systems.collections;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.collection;
import script.library.sui;
import script.library.utils;

public class listening_device_location extends script.base_script
{
    public listening_device_location()
    {
    }
    public static final float UPDATE_RADIUS = 5f;
    public static final string_id SID_ENTERED_GOOD_LOCATION = new string_id("collection", "entered_good_location");
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        createTriggerVolume("listening_device_object", UPDATE_RADIUS, true);
        return SCRIPT_CONTINUE;
    }
    public int OnTriggerVolumeEntered(obj_id self, String volName, obj_id breecher) throws InterruptedException
    {
        obj_id triggerVolumeObject = self;
        if (!isIdValid(breecher) || !exists(breecher))
        {
            return SCRIPT_CONTINUE;
        }
        if (!isPlayer(breecher))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id playerDevice = utils.getStaticItemInInventory(breecher, "col_listening_device_02_01");
        if (!isIdValid(playerDevice) || !exists(playerDevice))
        {
            return SCRIPT_CONTINUE;
        }
        if (!hasCompletedCollection(breecher, "col_meatlump_eavesdrop"))
        {
            sendSystemMessage(breecher, SID_ENTERED_GOOD_LOCATION);
            utils.setScriptVar(playerDevice, "device_active", 1);
            int triggerLocation = getIntObjVar(triggerVolumeObject, "device_location");
            utils.setScriptVar(playerDevice, "device_location", triggerLocation);
        }
        return SCRIPT_CONTINUE;
    }
    public int OnTriggerVolumeExited(obj_id self, String volName, obj_id exitingPlayer) throws InterruptedException
    {
        if (!isIdValid(exitingPlayer) || !exists(exitingPlayer))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id playerDevice = utils.getStaticItemInInventory(exitingPlayer, "col_listening_device_02_01");
        if (!isIdValid(playerDevice) || !exists(playerDevice))
        {
            return SCRIPT_CONTINUE;
        }
        if (utils.hasScriptVar(playerDevice, "device_active"))
        {
            utils.removeScriptVar(playerDevice, "device_active");
        }
        if (utils.hasScriptVar(playerDevice, "device_location"))
        {
            utils.removeScriptVar(playerDevice, "device_location");
        }
        return SCRIPT_CONTINUE;
    }
}
