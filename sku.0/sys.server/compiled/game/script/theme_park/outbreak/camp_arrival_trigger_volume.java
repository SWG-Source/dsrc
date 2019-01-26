package script.theme_park.outbreak;

import script.library.attrib;
import script.obj_id;

public class camp_arrival_trigger_volume extends script.base_script
{
    public camp_arrival_trigger_volume()
    {
    }
    public static final boolean LOGGING_ON = true;
    public static final String SCRIPT_LOG = "outbreak_pathing";
    public static final String TRIGGER_NAME_PREFIX = "camp_arrival_trigger_";
    public static final String UPDATE_SIGNAL = "update_signal";
    public static final float TRIGGER_RADIUS = 40.0f;
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        setAttributeInterested(self, attrib.OUTBREAK_SURVIVOR);
        createTriggerVolume(TRIGGER_NAME_PREFIX + self, TRIGGER_RADIUS, true);
        return SCRIPT_CONTINUE;
    }
    public int OnTriggerVolumeEntered(obj_id self, String volumeName, obj_id whoTriggeredMe) throws InterruptedException
    {
        blog("OnTriggerVolumeEntered INIT: " + whoTriggeredMe);
        if (isPlayer(whoTriggeredMe))
        {
            return SCRIPT_CONTINUE;
        }
        messageTo(whoTriggeredMe, "cleanUpCampNpcAndParent", null, 1, false);
        return SCRIPT_CONTINUE;
    }
    public int OnTriggerVolumeExited(obj_id self, String volumeName, obj_id whoTriggeredMe) throws InterruptedException
    {
        blog("Exited trigger volume: " + whoTriggeredMe);
        return SCRIPT_CONTINUE;
    }
    public boolean blog(String msg) throws InterruptedException
    {
        if (LOGGING_ON)
        {
            LOG(SCRIPT_LOG, msg);
        }
        return true;
    }
}
