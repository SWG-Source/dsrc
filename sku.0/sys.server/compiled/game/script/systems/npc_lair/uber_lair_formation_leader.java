package script.systems.npc_lair;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.ai_lib;
import script.library.create;

public class uber_lair_formation_leader extends script.theme_park.poi.base
{
    public uber_lair_formation_leader()
    {
    }
    public int createFormation(obj_id self, dictionary params) throws InterruptedException
    {
        LOG("uber", "GOT SPAWN COMMAND");
        obj_id objParent = getObjIdObjVar(self, "objParent");
        location[] locWaypoints = params.getLocationArray("locWaypoints");
        String[] strFormationMembers = params.getStringArray("strFormationMembers");
        int intOffset = params.getInt("intOffset");
        location locSpawnLocation = locWaypoints[intOffset];
        if (strFormationMembers == null)
        {
            LOG("uber", "NULL FORMATIONS IN LEADER");
        }
        for (int intI = 0; intI < strFormationMembers.length; intI++)
        {
            obj_id objMob = poiCreateObject(objParent, strFormationMembers[intI], locSpawnLocation);
            ai_lib.followInFormation(objMob, self, ai_lib.FORMATION_WEDGE, intI + 1);
        }
        ai_lib.setPatrolPath(self, locWaypoints);
        return SCRIPT_CONTINUE;
    }
    public location[] organizeLocations(location[] locWaypoints, int intOffset) throws InterruptedException
    {
        location[] locNewWaypoints = new location[locWaypoints.length];
        int intIndex = 0;
        int intOldIndex = intOffset;
        while (intIndex < locWaypoints.length)
        {
            locNewWaypoints[intIndex] = locWaypoints[intOffset];
            intOldIndex = intOldIndex + 1;
            intIndex = intIndex + 1;
            if (intOldIndex > locWaypoints.length - 1)
            {
                intOldIndex = 0;
            }
        }
        return locNewWaypoints;
    }
}
