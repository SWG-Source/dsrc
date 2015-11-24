package script.systems.regions;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.locations;

public class region_tracker extends script.base_script
{
    public region_tracker()
    {
    }
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        debugServerConsoleMsg(self, "setting city name on " + self);
        if (!hasObjVar(self, "strCity"))
        {
            String strCity = locations.getCityName(getLocation(self));
            if (strCity != null)
            {
                debugServerConsoleMsg(self, "City name is " + strCity);
                setObjVar(self, "strCity", strCity);
                int intRegionSize = getIntObjVar(self, "intSize");
                intRegionSize = intRegionSize / 2;
                setObjVar(self, "intSize", intRegionSize);
            }
            else 
            {
                debugServerConsoleMsg(self, "OBJ_ID " + self + " IS A DELIVER REGION SPAWNER PLACED OUTSIDE OF A CITY. IT SHOULD BE DELETED");
            }
        }
        else 
        {
            String strCity = getStringObjVar(self, "strCity");
            LOG("regions", "strCity is " + strCity);
            if (strCity == null)
            {
                strCity = locations.getCityName(getLocation(self));
                debugServerConsoleMsg(self, "City name is " + strCity);
                setObjVar(self, "strCity", strCity);
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        if (!hasObjVar(self, "strCity"))
        {
            String strCity = locations.getCityName(getLocation(self));
            if (strCity != null)
            {
                setObjVar(self, "strCity", strCity);
            }
            else 
            {
                debugServerConsoleMsg(self, "OBJ_ID " + self + " IS A DELIVER REGION SPAWNER PLACED OUTSIDE OF A CITY. IT SHOULD BE DELETED");
            }
        }
        return SCRIPT_CONTINUE;
    }
}
