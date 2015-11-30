package script.poi;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

public class remote_theater_spawner extends script.base_script
{
    public remote_theater_spawner()
    {
    }
    public int handleCreateRemoteTheater(obj_id self, dictionary params) throws InterruptedException
    {
        String datatable = params.getString("datatable");
        if (datatable == null)
        {
            CustomerServiceLog("theater", "WARNING: handleCreateRemoteTheater could not get " + "datatable from params on remote theater spawner " + self);
            return SCRIPT_CONTINUE;
        }
        obj_id caller = params.getObjId("caller");
        String name = params.getString("name");
        int locationType = params.getInt("locationType");
        obj_id theater = null;
        if (params.containsKey("x"))
        {
            float x = params.getFloat("x");
            float z = params.getFloat("z");
            String script = params.getString("script");
            debugServerConsoleMsg(self, "handleCreateRemoteTheater creating theater at " + x + ", " + z + " with script " + script);
            theater = createTheater(datatable, new location(x, 0, z), script, caller, name, locationType);
        }
        else 
        {
            theater = createTheater(datatable, caller, name, locationType);
        }
        if (!isIdValid(theater))
        {
            CustomerServiceLog("theater", "WARNING: handleCreateRemoteTheater could not create " + "theater from datatable " + datatable + " on remote theater spawner " + self);
        }
        destroyObject(self);
        return SCRIPT_CONTINUE;
    }
}
