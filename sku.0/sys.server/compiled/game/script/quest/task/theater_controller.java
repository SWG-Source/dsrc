package script.quest.task;

import script.obj_id;

public class theater_controller extends script.base_script
{
    public theater_controller()
    {
    }
    public int OnTheaterCreated(obj_id self, obj_id[] objects, obj_id player, obj_id creator) throws InterruptedException
    {
        if (objects != null)
        {
            for (obj_id object : objects) {
                setObjVar(object, "quest.owner", player);
            }
        }
        return SCRIPT_CONTINUE;
    }
}
