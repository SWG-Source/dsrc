package script.city;

import script.dictionary;
import script.obj_id;

public class city_wander extends script.city.base.base_wander
{
    public String[] patrolPoints =
        {
                "city1",
                "city2",
                "city3",
                "city4",
                "city5",
                "city6"
        };

    public city_wander()
    {
        super.patrolPoints = patrolPoints;
    }
    public int killIt(obj_id self, dictionary params) throws InterruptedException
    {
        LOG("NPC_Cleanup", "NPC: " + self + " at " + getLocation(self) + " has cleaned itself up because it couldn't find anyplace to go, according to the City_Wander script.");
        return super.killIt(self, params);
    }

}
