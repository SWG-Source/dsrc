package script.theme_park.restuss_event;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.create;
import script.library.trial;

public class convoy_spawner extends script.base_script
{
    public convoy_spawner()
    {
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        messageTo(self, "createConvoy", null, 1, false);
        return SCRIPT_CONTINUE;
    }
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        messageTo(self, "createConvoy", null, 1, false);
        return SCRIPT_CONTINUE;
    }
    public int createConvoy(obj_id self, dictionary params) throws InterruptedException
    {
        String faction = getStringObjVar(self, "alignment");
        location selfLoc = getLocation(self);
        obj_id newSpawn = null;
        if (faction.equals("imperial"))
        {
            for (int i = 0; i < 8; i++)
            {
                if (i < 4)
                {
                    location spawnLoc = new location(selfLoc.x + (i * 2), selfLoc.y, selfLoc.z);
                    newSpawn = create.object("restuss_imperial_load_lifter_ambush", spawnLoc);
                }
                else 
                {
                    location spawnLoc = new location(selfLoc.x + (i * 2), selfLoc.y, selfLoc.z);
                    newSpawn = create.object("restuss_imperial_stormtrooper_st3", spawnLoc);
                }
                setHibernationDelay(newSpawn, 600);
                trial.setParent(trial.getParent(self), newSpawn, true);
                copyObjVar(self, newSpawn, "alignment");
                attachScript(newSpawn, "theme_park.restuss_event.convoy_master");
            }
        }
        if (faction.equals("rebel"))
        {
            for (int i = 0; i < 8; i++)
            {
                if (i < 4)
                {
                    location spawnLoc = new location(selfLoc.x - (i * 2), selfLoc.y, selfLoc.z);
                    newSpawn = create.object("restuss_rebel_load_lifter_ambush", spawnLoc);
                }
                else 
                {
                    location spawnLoc = new location(selfLoc.x - (i * 2), selfLoc.y, selfLoc.z);
                    newSpawn = create.object("restuss_rebel_trooper_st3", spawnLoc);
                }
                setHibernationDelay(newSpawn, 600);
                trial.setParent(trial.getParent(self), newSpawn, true);
                copyObjVar(self, newSpawn, "alignment");
                attachScript(newSpawn, "theme_park.restuss_event.convoy_master");
            }
        }
        messageTo(self, "createConvoy", null, 630, false);
        return SCRIPT_CONTINUE;
    }
}
