package script.space.ship;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.create;

public class ship_yt1300_chair extends script.base_script
{
    public ship_yt1300_chair()
    {
    }
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        debugServerConsoleMsg(self, "Initialized YT-1300 pilot chair creation script");
        spawnChair(self);
        return SCRIPT_CONTINUE;
    }
    public void spawnChair(obj_id self) throws InterruptedException
    {
        obj_id cockpit = getCellId(self, "cockpit");
        if (isIdValid(cockpit))
        {
            location chair_location = new location(12.32f, 0.14f, 12.88f, getCurrentSceneName(), cockpit);
            obj_id pilot_chair = createObject("object/tangible/shipcontrol/shipcontrol_falcon.iff", chair_location);
            if (!isIdValid(pilot_chair))
            {
                debugSpeakMsg(self, "Could not create pilot_chair.");
            }
        }
        else 
        {
            debugSpeakMsg(self, "Could not find cell 'cockpit'.");
        }
    }
}
