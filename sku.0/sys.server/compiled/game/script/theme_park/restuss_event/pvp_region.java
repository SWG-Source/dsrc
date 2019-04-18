package script.theme_park.restuss_event;

import script.library.factions;
import script.library.regions;
import script.library.restuss_event;
import script.library.trial;
import script.obj_id;
import script.region;

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
        if(getRegion("rori", restuss_event.PVP_REGION_NAME) == null) {
            createCircleRegion(
                    getLocation(self),
                    400,
                    restuss_event.PVP_REGION_NAME,
                    regions.PVP_REGION_TYPE_NORMAL,
                    regions.BUILD_FALSE,
                    regions.MUNI_TRUE,
                    regions.GEO_CITY,
                    0,
                    0,
                    regions.SPAWN_FALSE,
                    regions.MISSION_NONE,
                    false,
                    true
            );
        }
        obj_id[] players = getPlayerCreaturesInRange(getLocation(self), 420.0f);
        if (players == null || players.length == 0)
        {
            return;
        }
        for (obj_id player : players) {
            if (factions.isImperial(player) || factions.isRebel(player)) {
                continue;
            }
            warpPlayer(player, "rori", 5305, 80, 6188, null, 0, 0, 0);
        }
    }
    public int OnDestroy(obj_id self) throws InterruptedException
    {
        region pvpRegion = getRegion("rori", restuss_event.PVP_REGION_NAME);
        deleteRegion(pvpRegion);
        return SCRIPT_CONTINUE;
    }
}
