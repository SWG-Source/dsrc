package script.npe;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.utils;
import script.library.space_dungeon;
import script.library.npe;

public class npe_instance_travel_player extends script.base_script
{
    public npe_instance_travel_player()
    {
    }
    public int OnClusterWideDataResponse(obj_id self, String manage_name, String name, int request_id, String[] element_name_list, dictionary[] data, int lock_key) throws InterruptedException
    {
        LOG("npe", "npe_instance_travel_player.OnClusterWideDataResponse");
        boolean error = false;
        if (!manage_name.equals(npe.DUNGEON_PUBLIC_MANAGER_NAME))
        {
            LOG("npe", "npe_instance_travel_player.OnClusterWideDataResponse -- ignorning manage_name " + manage_name + " because it is not public_instances.");
            error = true;
        }
        if (request_id < 1)
        {
            LOG("npe", "npe_instance_travel_player.OnClusterWideDataResponse -- invalid request_id value of " + request_id);
            error = true;
        }
        if (data == null || data.length == 0)
        {
            LOG("npe", "npe_instance_travel_player.OnClusterWideDataResponse -- data is null.");
            error = true;
        }
        if (element_name_list == null)
        {
            LOG("npe", "npe_instance_travel_player.OnClusterWideDataResponse -- element_name_list is null.");
            error = true;
        }
        if (error)
        {
            releaseClusterWideDataLock(manage_name, lock_key);
            return SCRIPT_CONTINUE;
        }
        if (!npe.performTransitionFromClusterWideData(self, data, name))
        {
            LOG("npe", "Failed to perform transition for player " + self + " instance name " + name);
        }
        releaseClusterWideDataLock(manage_name, lock_key);
        return SCRIPT_CONTINUE;
    }
    public int msgNpeInstanceTravelComplete(obj_id self, dictionary params) throws InterruptedException
    {
        if (hasObjVar(self, space_dungeon.VAR_RESET_DUNGEON))
        {
            obj_id dungeon_id = getObjIdObjVar(self, space_dungeon.VAR_RESET_DUNGEON);
            dictionary d = new dictionary();
            messageTo(dungeon_id, "msgManualDungeonReset", d, 0.0f, false);
            removeObjVar(self, space_dungeon.VAR_RESET_DUNGEON);
        }
        return SCRIPT_CONTINUE;
    }
}
