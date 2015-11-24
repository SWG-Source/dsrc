package script.systems.gcw.static_base;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

public class spawned_object extends script.base_script
{
    public spawned_object()
    {
    }
    public int OnIncapacitated(obj_id self, obj_id killer) throws InterruptedException
    {
        location loc = getLocation(self);
        String datatable = "datatables/gcw/static_base/base_spawn_" + loc.area + ".iff";
        obj_id master = getObjIdObjVar(self, "master");
        if (!isIdValid(master))
        {
            return SCRIPT_OVERRIDE;
        }
        int spawnNum = getIntObjVar(self, "spawn_number");
        int status = getIntObjVar(self, "status");
        int respawnTime = dataTableGetInt(datatable, spawnNum, "respawn_time");
        if (respawnTime == 0)
        {
            respawnTime = 30;
        }
        dictionary info = new dictionary();
        info.put("spawnNumber", spawnNum);
        info.put("status", status);
        info.put("spawnObj", self);
        messageTo(master, "handleNpcDeath", info, respawnTime, false);
        return SCRIPT_CONTINUE;
    }
    public int OnObjectDisabled(obj_id self, obj_id killer) throws InterruptedException
    {
        location loc = getLocation(self);
        String datatable = "datatables/gcw/static_base/base_spawn_" + loc.area + ".iff";
        obj_id master = getObjIdObjVar(self, "master");
        if (!isIdValid(master))
        {
            return SCRIPT_OVERRIDE;
        }
        int spawnNum = getIntObjVar(self, "spawn_number");
        int status = getIntObjVar(self, "status");
        int respawnTime = dataTableGetInt(datatable, spawnNum, "respawn_time");
        if (respawnTime == 0)
        {
            respawnTime = 30;
        }
        dictionary info = new dictionary();
        info.put("spawnNumber", spawnNum);
        info.put("status", status);
        info.put("spawnObj", self);
        messageTo(master, "handleNpcDeath", info, respawnTime, false);
        return SCRIPT_CONTINUE;
    }
    public int handleDestroyRequest(obj_id self, dictionary params) throws InterruptedException
    {
        destroyObject(self);
        return SCRIPT_CONTINUE;
    }
}
