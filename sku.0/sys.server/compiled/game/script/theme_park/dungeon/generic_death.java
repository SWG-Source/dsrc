package script.theme_park.dungeon;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

public class generic_death extends script.base_script
{
    public generic_death()
    {
    }
    public int OnIncapacitated(obj_id self, obj_id killer) throws InterruptedException
    {
        obj_id top = getTopMostContainer(self);
        String datatable = getStringObjVar(top, "spawn_table");
        obj_id mom = getObjIdObjVar(self, "mom");
        if (mom == null)
        {
            return SCRIPT_OVERRIDE;
        }
        int spawnNum = getIntObjVar(self, "spawn_number");
        int respawnTime = dataTableGetInt(datatable, spawnNum, "respawn_time");
        if (respawnTime == 0)
        {
            respawnTime = 30;
        }
        dictionary info = new dictionary();
        info.put("spawnNumber", spawnNum);
        info.put("spawnMob", self);
        messageTo(mom, "tellingMomIDied", info, respawnTime, false);
        return SCRIPT_CONTINUE;
    }
    public int OnObjectDisabled(obj_id self, obj_id killer) throws InterruptedException
    {
        obj_id top = getTopMostContainer(self);
        String datatable = getStringObjVar(top, "spawn_table");
        obj_id mom = getObjIdObjVar(self, "mom");
        if (mom == null)
        {
            return SCRIPT_OVERRIDE;
        }
        int spawnNum = getIntObjVar(self, "spawn_number");
        if (spawnNum == 0)
        {
            return SCRIPT_OVERRIDE;
        }
        int respawnTime = dataTableGetInt(datatable, spawnNum, "respawn_time");
        if (respawnTime == 0)
        {
            respawnTime = 30;
        }
        dictionary info = new dictionary();
        info.put("spawnNumber", spawnNum);
        info.put("spawnMob", self);
        messageTo(mom, "tellingMomIDied", info, respawnTime, false);
        return SCRIPT_CONTINUE;
    }
}
