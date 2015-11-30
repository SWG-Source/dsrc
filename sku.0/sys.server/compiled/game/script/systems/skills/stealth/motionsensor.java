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
import script.library.ai_lib;

public class motionsensor extends script.base_script
{
    public motionsensor()
    {
    }
    public static final java.text.NumberFormat floatFormat = new java.text.DecimalFormat("#####");
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        if (hasObjVar(self, "armed"))
        {
            destroyObject(self);
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
        int armedTime = 0;
        if (hasObjVar(self, "armed"))
        {
            armedTime = getIntObjVar(self, stealth.SENSOR_ARMED_TIME);
            power -= (getGameTime() - armedTime);
        }
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
        free = utils.addClassRequirementAttributes(player, self, names, attribs, free, "trap.");
        free = utils.addClassRequirementAttributes(player, self, names, attribs, free, "trap.trigger.");
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
            return SCRIPT_OVERRIDE;
        }
        return SCRIPT_CONTINUE;
    }
    public int OnObjectMenuSelect(obj_id self, obj_id player, int item) throws InterruptedException
    {
        if (!utils.testItemClassRequirements(player, self, false, "") || !utils.testItemLevelRequirements(player, self, false, ""))
        {
            return SCRIPT_OVERRIDE;
        }
        if (item == menu_info_types.ITEM_USE && !hasObjVar(self, "armed"))
        {
            LOG("stealth", "Deploying Motion Sensor");
            if (!stealth.canPlaceMotionSensor(player, self))
            {
                LOG("stealth", "Can't place motion sensor. - Failed can place check");
                return SCRIPT_OVERRIDE;
            }
            stealth.placeMotionSensor(player, self);
        }
        return SCRIPT_CONTINUE;
    }
    public int OnTriggerVolumeEntered(obj_id self, String volumeName, obj_id breacher) throws InterruptedException
    {
        LOG("stealth", "Entered sensor volume (" + breacher + ")...");
        if (!volumeName.equals(stealth.SENSOR_PROXIMITY_VOLUME))
        {
            LOG("stealth", " Wrong volume name");
            return SCRIPT_CONTINUE;
        }
        if (!ai_lib.isHumanoid(breacher) || getOwner(self) == breacher)
        {
            LOG("stealth", "Failed humanoid / owner check.");
            return SCRIPT_CONTINUE;
        }
        obj_id filter = stealth.getBioProbeTarget(self);
        if (filter != null && filter != breacher)
        {
            LOG("stealth", "Valid breach, but didn't match bio filter.  Not detonating.");
            return SCRIPT_CONTINUE;
        }
        LOG("stealth", "Calling tripMotionSensor");
        stealth.tripMotionSensor(self, breacher, filter != null);
        return SCRIPT_CONTINUE;
    }
    public int OnAboutToBeTransferred(obj_id self, obj_id destContainer, obj_id transferer) throws InterruptedException
    {
        if (hasObjVar(self, "armed"))
        {
            sendSystemMessage(transferer, new string_id("spam", "cant_pick_up_armed"));
            return SCRIPT_OVERRIDE;
        }
        return SCRIPT_CONTINUE;
    }
    public int msgBatteryDead(obj_id self, dictionary params) throws InterruptedException
    {
        stealth.playSelfDestruct(self);
        destroyObject(self);
        return SCRIPT_CONTINUE;
    }
    public int msgDeploy(obj_id self, dictionary params) throws InterruptedException
    {
        createTriggerVolume(stealth.SENSOR_PROXIMITY_VOLUME, stealth.SENSOR_RANGE, true);
        return SCRIPT_CONTINUE;
    }
}
