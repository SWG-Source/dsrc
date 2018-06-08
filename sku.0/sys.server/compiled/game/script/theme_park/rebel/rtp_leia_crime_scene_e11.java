package script.theme_park.rebel;

import script.dictionary;
import script.location;
import script.obj_id;

public class rtp_leia_crime_scene_e11 extends script.base_script
{
    public rtp_leia_crime_scene_e11()
    {
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        if (hasObjVar(self, "e11Spawned"))
        {
            removeObjVar(self, "e11Spawned");
        }
        return SCRIPT_CONTINUE;
    }
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        if (hasObjVar(self, "e11Spawned"))
        {
            removeObjVar(self, "e11Spawned");
        }
        return SCRIPT_CONTINUE;
    }
    public int handleQuestFlavorObject(obj_id self, dictionary params) throws InterruptedException
    {
        if (!hasObjVar(self, "e11Spawned"))
        {
            String e11Carbine = "object/tangible/quest/rebel/rtp_leia_e11_rifle.iff";
            location here = getLocation(self);
            here.x = here.x + 0.5f;
            here.z = here.z + 0.8f;
            obj_id objTemplate = createObject(e11Carbine, here);
            setObjVar(self, "e11Spawned", true);
            setObjVar(objTemplate, "objParent", self);
        }
        return SCRIPT_CONTINUE;
    }
    public int spawnDestroyed(obj_id self, dictionary params) throws InterruptedException
    {
        if (hasObjVar(self, "e11Spawned"))
        {
            removeObjVar(self, "e11Spawned");
        }
        return SCRIPT_CONTINUE;
    }
}
