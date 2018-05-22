package script.working.toad;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

public class get_location extends script.base_script
{
    public get_location()
    {
    }
    public int OnSpeaking(obj_id self, String text) throws InterruptedException
    {
        int stringCheck = text.indexOf("here");
        if (stringCheck > -1)
        {
            location here = getLocation(self);
            float yaw = getYaw(self);
            String spawnLoc = text + " @ " + here + " yaw " + yaw;
            sendSystemMessageTestingOnly(self, spawnLoc);
            return SCRIPT_OVERRIDE;
        }
        return SCRIPT_CONTINUE;
    }
}
