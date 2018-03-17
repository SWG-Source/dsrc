package script.theme_park.restuss_event;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

public class comm_array_spawn_died extends script.base_script
{
    public comm_array_spawn_died()
    {
    }
    public int OnIncapacitated(obj_id self, obj_id killer) throws InterruptedException
    {
        if (hasObjVar(self, "respawn_called"))
        {
            return SCRIPT_CONTINUE;
        }
        else 
        {
            setObjVar(self, "respawn_called", 1);
        }
        obj_id mom = getObjIdObjVar(self, "mom");
        if (!isIdValid(mom))
        {
            return SCRIPT_CONTINUE;
        }
        String myFac = getStringObjVar(self, "faction");
        String momFac = getStringObjVar(mom, "faction");
        if (myFac.equals("Rebel") && momFac.equals("Rebel"))
        {
            
        }
        
        {
            String datatable = getStringObjVar(mom, "spawn_table_rebel");
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
        }
        if (myFac.equals("Imperial") && momFac.equals("Imperial"))
        {
            
        }
        
        {
            String datatable = getStringObjVar(mom, "spawn_table_imperial");
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
        }
        return SCRIPT_CONTINUE;
    }
}
