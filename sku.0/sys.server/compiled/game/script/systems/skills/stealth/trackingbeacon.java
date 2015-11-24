package script.systems.skills.stealth;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.utils;
import script.library.stealth;
import script.library.prose;

public class trackingbeacon extends script.base_script
{
    public trackingbeacon()
    {
    }
    public static final java.text.NumberFormat floatFormat = new java.text.DecimalFormat("#####");
    public int msgDecaySample(obj_id self, dictionary params) throws InterruptedException
    {
        stealth.cleanComponentFromContainer(self, getContainedBy(self));
        setObjVar(self, stealth.BIO_PROBE_DECAYED, 1);
        removeObjVar(self, stealth.BIO_PROBE_TRAP_TARGET);
        obj_id player = utils.getContainingPlayer(self);
        if (isIdValid(player))
        {
            String targetName = getStringObjVar(self, stealth.BIO_PROBE_TARGET_NAME);
            prose_package pp = prose.getPackage(new string_id("spam", "probe_decayed_target"));
            pp = prose.setTT(pp, targetName);
            sendSystemMessageProse(player, pp);
        }
        if (hasObjVar(self, stealth.BEACON_MSG_DISPATCHED))
        {
            stealth.stopTrackingBeacon(self, player);
        }
        return SCRIPT_CONTINUE;
    }
    public int OnGetAttributes(obj_id self, obj_id player, String[] names, String[] attribs) throws InterruptedException
    {
        int free = getFirstFreeIndex(names);
        if (free == -1)
        {
            return SCRIPT_CONTINUE;
        }
        float power = getFloatObjVar(self, stealth.BEACON_BATTERY);
        names[free] = "battery_power";
        if (power < 1)
        {
            attribs[free++] = "@spam:power_drained";
        }
        else 
        {
            if (power < 60)
            {
                attribs[free++] = floatFormat.format(power) + " secs left";
            }
            else 
            {
                attribs[free++] = floatFormat.format(power / 60) + " mins left";
            }
        }
        if (hasObjVar(self, stealth.BIO_PROBE_TRAP_TARGET))
        {
            names[free] = "beacon_target";
            attribs[free++] = "" + getStringObjVar(self, stealth.BIO_PROBE_TARGET_NAME);
        }
        if (hasObjVar(self, stealth.BEACON_LAST_KNOWN_TARGET_NAME))
        {
            names[free] = "last_known_target";
            attribs[free++] = getStringObjVar(self, stealth.BEACON_LAST_KNOWN_TARGET_NAME);
        }
        if (hasObjVar(self, stealth.BEACON_LAST_KNOWN_LOC))
        {
            location loc = getLocationObjVar(self, stealth.BEACON_LAST_KNOWN_LOC);
            names[free] = "last_known_planet";
            String planet = utils.packStringId(new string_id("planet_n", loc.area));
            attribs[free++] = planet;
            names[free] = "last_known_loc";
            attribs[free++] = planet.startsWith("Kashyyyk") || planet.startsWith("Mustafar") ? "Unknown" : floatFormat.format(loc.x) + ", " + floatFormat.format(loc.z);
        }
        float thisProbeStorage = getFloatObjVar(self, stealth.BIO_PROBE_STORAGE_TIME);
        names[free] = "sample_preserve";
        if (hasObjVar(self, stealth.BIO_PROBE_SAMPLE_TIME))
        {
            int when = getIntObjVar(self, stealth.BIO_PROBE_SAMPLE_TIME);
            int now = getGameTime();
            float timespan = thisProbeStorage - (now - when);
            if (timespan < 1)
            {
                attribs[free++] = "@spam:already_expired";
            }
            else 
            {
                if (timespan < 60)
                {
                    attribs[free++] = floatFormat.format(timespan) + " secs left";
                }
                else 
                {
                    attribs[free++] = floatFormat.format(timespan / 60) + " mins left";
                }
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int OnObjectMenuRequest(obj_id self, obj_id player, menu_info mi) throws InterruptedException
    {
        if (!utils.isNestedWithin(self, player))
        {
            return SCRIPT_OVERRIDE;
        }
        if (!utils.testItemClassRequirements(player, self, true, "") || !utils.testItemLevelRequirements(player, self, true, ""))
        {
            return SCRIPT_CONTINUE;
        }
        menu_info_data mid2 = mi.getMenuItemByType(menu_info_types.ITEM_USE);
        if (mid2 != null)
        {
            mid2.setServerNotify(true);
        }
        if (hasObjVar(self, stealth.BEACON_MSG_DISPATCHED))
        {
            int mnuUse = mi.addRootMenu(menu_info_types.SERVER_MENU2, new string_id("spam", "stop_tracking"));
        }
        else if (stealth.canActivateTrackingBeacon(player, self, true))
        {
            int mnuUse = mi.addRootMenu(menu_info_types.SERVER_MENU1, new string_id("spam", "start_tracker"));
        }
        return SCRIPT_CONTINUE;
    }
    public int OnObjectMenuSelect(obj_id self, obj_id player, int item) throws InterruptedException
    {
        if (!utils.isNestedWithin(self, player))
        {
            return SCRIPT_OVERRIDE;
        }
        if (!utils.testItemClassRequirements(player, self, false, "") || !utils.testItemLevelRequirements(player, self, false, ""))
        {
            return SCRIPT_CONTINUE;
        }
        if (item == menu_info_types.SERVER_MENU1)
        {
            LOG("stealth", "Selected Start Tracking From Menu.");
            if (!stealth.canActivateTrackingBeacon(player, self, false))
            {
                return SCRIPT_CONTINUE;
            }
            stealth.startTrackingBeacon(player, self);
        }
        else if (item == menu_info_types.SERVER_MENU2 && hasObjVar(self, stealth.BEACON_MSG_DISPATCHED))
        {
            stealth.stopTrackingBeacon(self, player);
        }
        else if (item == menu_info_types.ITEM_USE)
        {
            if (hasObjVar(self, stealth.BEACON_MSG_DISPATCHED))
            {
                sendSystemMessage(player, new string_id("spam", "tracking_must_turn_off"));
                return SCRIPT_CONTINUE;
            }
            else 
            {
                obj_id target = getLookAtTarget(player);
                if (stealth.canBioProbe(player, self, target))
                {
                    stealth.bioProbe(player, self, target);
                }
            }
        }
        sendDirtyObjectMenuNotification(self);
        return SCRIPT_CONTINUE;
    }
    public int msgTrackingBeaconLocationReply(obj_id self, dictionary params) throws InterruptedException
    {
        LOG("stealth", "Object " + self + " " + getName(self) + " got msgTrackingBeaconLocationReply");
        obj_id sendingTarget = params.getObjId("sendingTarget");
        location loc = params.getLocation("location");
        String targetName = params.getString("name");
        stealth.updateTrackingBeaconData(self, sendingTarget, loc, targetName);
        return SCRIPT_CONTINUE;
    }
    public int msgTrackingBeaconUpdate(obj_id self, dictionary params) throws InterruptedException
    {
        LOG("stealth", "Object " + self + " " + getName(self) + " got msgTrackingBeaconUpdate");
        stealth.updateTrackingBeaconDisplay(self);
        return SCRIPT_CONTINUE;
    }
}
