package script.quest.task;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

public class theater_controller extends script.base_script
{
    public theater_controller()
    {
    }
    public int OnTheaterCreated(obj_id self, obj_id[] objects, obj_id player, obj_id creator) throws InterruptedException
    {
        if (objects != null)
        {
            for (int i = 0; i < objects.length; i++)
            {
                setObjVar(objects[i], "quest.owner", player);
            }
        }
        return SCRIPT_CONTINUE;
    }
}
