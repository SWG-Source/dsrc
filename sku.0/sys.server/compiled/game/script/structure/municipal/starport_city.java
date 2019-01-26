package script.structure.municipal;

import script.dictionary;
import script.library.city;
import script.library.travel;
import script.location;
import script.obj_id;

public class starport_city extends script.structure.municipal.starport
{
    public starport_city()
    {
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        messageTo(self, "setupStartport", null, 1.0f, false);
        return super.OnAttach(self);
    }
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        String planet = getCurrentSceneName();
        String travel_point = travel.getTravelPointName(self);
        location arrival_loc = travel.getArrivalLocation(self);
        int travel_cost = travel.getTravelCost(self);
        if (travel_point == null || travel_cost == -1)
        {
            return super.OnInitialize(self);
        }
        travel.initializeStarport(self, travel_point, travel_cost, true);
        return super.OnInitialize(self);
    }
    public int setupStartport(obj_id self, dictionary params) throws InterruptedException
    {
        int city_id = getCityAtLocation(getLocation(self), 0);
        obj_id mayor = cityGetLeader(city_id);
        int cost = cityGetTravelCost(city_id);
        if (cost > 0)
        {
            destroyObject(self);
            return SCRIPT_CONTINUE;
        }
        String cityName = cityGetName(city_id);
        String travel_point = cityName;
        int travel_cost = 100;
        travel.initializeStarport(self, travel_point, travel_cost, true);
        return SCRIPT_CONTINUE;
    }
    public int OnDestroy(obj_id self) throws InterruptedException
    {
        boolean initd = false;
        obj_id[] objects = getObjIdArrayObjVar(self, "travel.base_object");
        for (obj_id object : objects) {
            destroyObject(object);
            initd = true;
        }
        if (initd)
        {
            city.removeStarport(self);
        }
        return super.OnInitialize(self);
    }
}
