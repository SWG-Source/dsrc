package script.theme_park.restuss_event;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.trial;
import script.library.restuss_event;
import script.library.regions;
import script.library.factions;

public class pvp_region extends script.base_script
{
    public pvp_region()
    {
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        initialize(self);
        return SCRIPT_CONTINUE;
    }
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        initialize(self);
        return SCRIPT_CONTINUE;
    }
    public void initialize(obj_id self) throws InterruptedException
    {
        if (hasObjVar(self, trial.PARENT))
        {
            removeObjVar(self, trial.PARENT);
        }
        persistObject(self);
        createCircleRegion(getLocation(self), 400, restuss_event.PVP_REGION_NAME, regions.PVP_REGION_TYPE_NORMAL, regions.BUILD_FALSE, regions.MUNI_TRUE, regions.GEO_CITY, 0, 0, regions.SPAWN_FALSE, regions.MISSION_NONE, false, true);
        obj_id[] players = getPlayerCreaturesInRange(getLocation(self), 420.0f);
        if (players == null || players.length == 0)
        {
            return;
        }
        for (int i = 0; i < players.length; i++)
        {
            if (factions.isImperial(players[i]) || factions.isRebel(players[i]))
            {
                continue;
            }
            warpPlayer(players[i], "rori", 5305, 80, 6188, null, 0, 0, 0);
        }
    }
    public int OnDestroy(obj_id self) throws InterruptedException
    {
        region pvpRegion = getRegion("rori", restuss_event.PVP_REGION_NAME);
        deleteRegion(pvpRegion);
        return SCRIPT_CONTINUE;
    }
}
