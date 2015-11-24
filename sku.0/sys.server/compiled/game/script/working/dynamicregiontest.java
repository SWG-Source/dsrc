package script.working;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.regions;

public class dynamicregiontest extends script.base_script
{
    public dynamicregiontest()
    {
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        createCircleRegion(getLocation(self), 2000, self.toString(), regions.PVP_REGION_TYPE_NORMAL, regions.BUILD_TRUE, regions.MUNI_FALSE, 600, 0, 50, regions.SPAWN_FALSE, regions.MISSION_NONE, false, false);
        debugSpeakMsg(self, "made region called " + self.toString());
        return SCRIPT_CONTINUE;
    }
    public int OnDestroy(obj_id self) throws InterruptedException
    {
        location locTest = getLocation(self);
        String strPlanet = locTest.area;
        region rgnRegion = getRegion(strPlanet, self.toString());
        if (rgnRegion == null)
        {
            debugSpeakMsg(self, "Null region.. no good");
        }
        else 
        {
            deleteRegion(rgnRegion);
        }
        return SCRIPT_CONTINUE;
    }
}
