package script.ai;

import script.dictionary;
import script.library.create;
import script.location;
import script.obj_id;

public class parasite extends script.base_script
{
    public parasite()
    {
    }
    public static final String SCRIPT_NAME = "ai.parasite";
    public int OnAttach(obj_id self) throws InterruptedException
    {
        if (!hasObjVar(self, "host"))
        {
            detachScript(self, SCRIPT_NAME);
        }
        return SCRIPT_CONTINUE;
    }
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        if (!hasObjVar(self, "host"))
        {
            detachScript(self, SCRIPT_NAME);
        }
        return SCRIPT_CONTINUE;
    }
    public int OnIncapacitated(obj_id self, obj_id killer) throws InterruptedException
    {
        if (!hasObjVar(self, "host"))
        {
            return SCRIPT_CONTINUE;
        }
        dictionary d = new dictionary();
        d.put("killer", killer);
        messageTo(self, "handleParasiteExplosion", d, 1.0f, false);
        return SCRIPT_CONTINUE;
    }
    public int handleParasiteExplosion(obj_id self, dictionary params) throws InterruptedException
    {
        if (!hasObjVar(self, "host"))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id killer = params.getObjId("killer");
        if (isIdValid(killer))
        {
            playClientEffectLoc(killer, "clienteffect/cr_parasite_explosion.cef", getLocation(self), 0.5f);
        }
        String parasite = getStringObjVar(self, "host");
        int numToSpawn = rand(1, 3);
        for (int i = 0; i < numToSpawn; i++)
        {
            location here = getLocation(self);
            int randX = rand(-10, 10);
            int randZ = rand(-10, 10);
            float rX = randX / 10.0f;
            float rZ = randZ / 10.0f;
            here.x += rX;
            here.z += rZ;
            obj_id critter = create.object(parasite, here);
            if (isIdValid(critter) && isIdValid(killer))
            {
                addHate(critter, killer, 100.0f);
                addHate(killer, critter, 0.0f);
            }
        }
        return SCRIPT_CONTINUE;
    }
}
