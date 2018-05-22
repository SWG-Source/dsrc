package script.working.jfreeman;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

public class loitertest extends script.base_script
{
    public loitertest()
    {
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        debugSpeakMsg(self, "attached.  Loitering: ");
        final location anchorLocation = getLocation(self);
        final float minDistance = 10.0f;
        final float maxDistance = 30.0f;
        final float minDelay = 3.0f;
        final float maxDelay = 8.0f;
        loiterLocation(self, anchorLocation, minDistance, maxDistance, minDelay, maxDelay);
        return SCRIPT_CONTINUE;
    }
    public int OnLoiterMoving(obj_id self) throws InterruptedException
    {
        debugSpeakMsg(self, "loiter moving");
        return SCRIPT_CONTINUE;
    }
    public int OnLoiterWaypoint(obj_id self) throws InterruptedException
    {
        debugSpeakMsg(self, "loiter waypoint");
        return SCRIPT_CONTINUE;
    }
    public int OnLoiterWaiting(obj_id self, modifiable_float time) throws InterruptedException
    {
        debugSpeakMsg(self, "loiter waiting");
        return SCRIPT_CONTINUE;
    }
}
