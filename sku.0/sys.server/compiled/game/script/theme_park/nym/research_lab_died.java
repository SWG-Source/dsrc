package script.theme_park.nym;

import script.dictionary;
import script.obj_id;

public class research_lab_died extends script.base_script
{
    public research_lab_died()
    {
    }
    public int OnDestroy(obj_id self) throws InterruptedException
    {
        obj_id mom = getObjIdObjVar(self, "mom");
        if (mom == null)
        {
            return SCRIPT_CONTINUE;
        }
        int spawnNum = getIntObjVar(self, "spawn_number");
        dictionary info = new dictionary();
        info.put("spawnNumber", spawnNum);
        info.put("spawnMob", self);
        messageTo(mom, "tellingMomIDied", info, rand(90, 200), false);
        return SCRIPT_CONTINUE;
    }
}
