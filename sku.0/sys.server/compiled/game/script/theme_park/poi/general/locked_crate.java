package script.theme_park.poi.general;

import script.obj_id;

public class locked_crate extends script.theme_park.poi.base
{
    public locked_crate()
    {
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        makeCrate(self);
        return SCRIPT_CONTINUE;
    }
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        makeCrate(self);
        return SCRIPT_CONTINUE;
    }
    public void makeCrate(obj_id self) throws InterruptedException
    {
        String objective = poiGetObjective(self);
        if (objective.equals("crate"))
        {
            obj_id crate = poiCreateObject(self, "crate", "object/tangible/container/drum/tatt_drum_1.iff", 0, 1);
            return;
        }
        return;
    }
}
