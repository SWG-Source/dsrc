package script.structure.municipal;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.travel;
import script.library.player_structure;

public class starport extends script.base_script
{
    public starport()
    {
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        LOG("LOG_CHANNEL", "starport::OnAttach -- " + self);
        if (player_structure.isCivic(self))
        {
            return SCRIPT_CONTINUE;
        }
        doInitializeStarport(self);
        return SCRIPT_CONTINUE;
    }
    public int OnDetach(obj_id self) throws InterruptedException
    {
        travel.destroyBaseObjects(self);
        travel.removeTravelPoint(self);
        return SCRIPT_CONTINUE;
    }
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        LOG("LOG_CHANNEL", "starport::OnIntialize -- " + self);
        if (player_structure.isCivic(self))
        {
            return SCRIPT_CONTINUE;
        }
        doInitializeStarport(self);
        int numberOfRetry = 0;
        dictionary params = new dictionary();
        params.put("number_of_retry", numberOfRetry);
        messageTo(self, "retryInitializeStarport", params, 60.0f, false);
        return SCRIPT_CONTINUE;
    }
    public int OnUnloadedFromMemory(obj_id self) throws InterruptedException
    {
        LOG("LOG_CHANNEL", "starport::OnUnloadedFromMemory -- " + self);
        return SCRIPT_CONTINUE;
    }
    public int OnDestroy(obj_id self) throws InterruptedException
    {
        LOG("LOG_CHANNEL", "starport::OnDestroy -- " + self);
        boolean success = travel.removeTravelPoint(self);
        if (!success)
        {
            LOG("LOG_CHANNEL", "Unable to remove travel point for " + self);
        }
        travel.destroyBaseObjects(self);
        return SCRIPT_CONTINUE;
    }
    public int msgArrivalLocation(obj_id self, dictionary params) throws InterruptedException
    {
        LOG("LOG_CHANNEL", "starport::msgArrivalLocation -- " + self + "  " + params);
        location loc = travel.getArrivalLocation(self);
        obj_id player = params.getObjId("player");
        LOG("LOG_CHANNEL", "starport::msgArrivalLocation --  loc ->" + loc);
        params.put("location", loc);
        messageTo(player, "msgTravelToStarport", params, 0.0f, true);
        return SCRIPT_CONTINUE;
    }
    public int msgRestartShuttle(obj_id self, dictionary params) throws InterruptedException
    {
        LOG("LOG_CHANNEL", "starport::msgRestartShuttle -- " + self);
        obj_id shuttle = params.getObjId("shuttle");
        if (shuttle == null || shuttle == obj_id.NULL_ID)
        {
            LOG("LOG_CHANNEL", "starport::msgRestartShuttle -- shuttle is null for " + self);
            return SCRIPT_CONTINUE;
        }
        setPosture(shuttle, POSTURE_UPRIGHT);
        queueCommand(shuttle, (-1465754503), self, "", COMMAND_PRIORITY_FRONT);
        setObjVar(self, travel.VAR_SHUTTLE_AVAILABLE, 1);
        setObjVar(self, travel.VAR_SHUTTLE_TIMESTAMP, getGameTime());
        params.put("starport", self);
        int ground_time = travel.getGroundTime(self);
        messageTo(shuttle, "msgRestartShuttle", params, 1.0f, false);
        LOG("LOG_CHANNEL", "starport::msgRestartShuttle -- sending message to " + shuttle);
        return SCRIPT_CONTINUE;
    }
    public int retryInitializeStarport(obj_id self, dictionary params) throws InterruptedException
    {
        String planet = getCurrentSceneName();
        String travel_point = travel.getTravelPointName(self);
        if ((planet != null) && (travel_point != null))
        {
            location loc = getPlanetTravelPointLocation(planet, travel_point);
            if (loc != null)
            {
                return SCRIPT_CONTINUE;
            }
        }
        int numberOfRetry = params.getInt("number_of_retry");
        if (numberOfRetry >= 5)
        {
            return SCRIPT_CONTINUE;
        }
        doInitializeStarport(self);
        ++numberOfRetry;
        if (numberOfRetry < 5)
        {
            params.put("number_of_retry", numberOfRetry);
            messageTo(self, "retryInitializeStarport", params, 60.0f, false);
        }
        return SCRIPT_CONTINUE;
    }
    public void doInitializeStarport(obj_id self) throws InterruptedException
    {
        if (player_structure.isCivic(self))
        {
            return;
        }
        String travel_point = travel.getTravelPointName(self);
        int travel_cost = travel.getTravelCost(self);
        if (travel_point == null || travel_cost == -1)
        {
            LOG("LOG_CHANNEL", "starport::doInitializeStarport -- Unable to obtain valid travel_point information for " + self);
            return;
        }
        travel.initializeStarport(self, travel_point, travel_cost, false);
    }
}
