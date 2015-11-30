package script.systems.cw_data;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

public class cluster_wide_response_manager extends script.base_script
{
    public cluster_wide_response_manager()
    {
    }
    public int OnClusterWideDataResponse(obj_id self, String manage_name, String name, int request_id, String[] element_name_list, dictionary[] dungeon_data, int lock_key) throws InterruptedException
    {
        LOG("doLogging", "Recieved CWData. Name = " + name);
        if (name.startsWith("restuss_event"))
        {
            obj_id restuss_event_controller = dungeon_data[0].getObjId("dungeon_id");
            messageTo(restuss_event_controller, "beginSpawning", null, 1, false);
            releaseClusterWideDataLock(manage_name, lock_key);
            return SCRIPT_CONTINUE;
        }
        detachScript(self, "systems.cw_data.cluster_wide_response_manager");
        return SCRIPT_CONTINUE;
    }
}
