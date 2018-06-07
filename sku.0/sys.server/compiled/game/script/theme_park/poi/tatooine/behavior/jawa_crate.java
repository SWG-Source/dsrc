package script.theme_park.poi.tatooine.behavior;

import script.obj_id;

public class jawa_crate extends script.theme_park.poi.base
{
    public jawa_crate()
    {
    }
    public int OnAboutToLoseItem(obj_id self, obj_id dest, obj_id grabber, obj_id item) throws InterruptedException
    {
        obj_id jawa1 = getObjIdObjVar(self, "jawa1");
        obj_id jawa2 = getObjIdObjVar(self, "jawa2");
        obj_id jawa3 = getObjIdObjVar(self, "jawa3");
        startCombat(jawa1, grabber);
        startCombat(jawa2, grabber);
        startCombat(jawa3, grabber);
        return SCRIPT_CONTINUE;
    }
}
