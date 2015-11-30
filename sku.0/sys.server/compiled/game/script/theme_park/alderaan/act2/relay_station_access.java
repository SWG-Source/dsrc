package script.theme_park.alderaan.act2;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

public class relay_station_access extends script.base_script
{
    public relay_station_access()
    {
    }
    public int OnAboutToReceiveItem(obj_id self, obj_id destinationCell, obj_id transferrer, obj_id item) throws InterruptedException
    {
        if (!isPlayer(item))
        {
            return SCRIPT_CONTINUE;
        }
        if (hasObjVar(self, "coa2.rebel.unlocked"))
        {
            return SCRIPT_CONTINUE;
        }
        boolean locked = (getIntObjVar(item, "coa2.rebel.station_key") == 0);
        if (locked)
        {
            string_id message = new string_id("theme_park/alderaan/act2/shared_rebel_missions", "imperial_station_locked");
            sendSystemMessage(item, message);
            return SCRIPT_OVERRIDE;
        }
        else 
        {
            string_id message = new string_id("theme_park/alderaan/act2/shared_rebel_missions", "imperial_station_unlocked");
            sendSystemMessage(item, message);
            removeObjVar(item, "coa2.rebel.station_key");
            setObjVar(self, "coa2.rebel.unlocked", 1);
            permissionsMakePublic(self);
            detachScript(self, "theme_park.alderaan.act2.relay_station_access");
            return SCRIPT_CONTINUE;
        }
    }
}
