package script.working.steve;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

public class theater_test extends script.base_script
{
    public theater_test()
    {
    }
    public int OnTheaterCreated(obj_id self, obj_id[] objects, obj_id player, obj_id creator) throws InterruptedException
    {
        debugServerConsoleMsg(self, "OnTheaterCreated enter");
        if (isIdValid(creator))
        {
            dictionary params = new dictionary();
            params.put("theater", self);
            messageTo(creator, "handleTheaterCreated", params, 0.1f, false);
        }
        else if (isIdValid(player))
        {
            persistObject(self);
            setObjVar(player, "player_theater", self);
            debugSpeakMsg(player, "Theater " + self + " active");
        }
        else 
        {
            debugServerConsoleMsg(self, "Theater missing creator");
        }
        if (objects != null)
        {
            if (objects.length == 0)
            {
                debugServerConsoleMsg(self, "OnTheaterCreated no objects");
            }
            for (int i = 0; i < objects.length; ++i)
            {
                if (isIdValid(objects[i]))
                {
                    messageTo(objects[i], "startHandler", null, 1, false);
                }
            }
        }
        else 
        {
            debugServerConsoleMsg(self, "OnTheaterCreated null objects");
        }
        return SCRIPT_CONTINUE;
    }
}
