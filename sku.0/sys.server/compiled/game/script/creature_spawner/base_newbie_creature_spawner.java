package script.creature_spawner;

import script.dictionary;
import script.library.ai_lib;
import script.library.create;
import script.library.locations;
import script.library.utils;
import script.location;
import script.obj_id;

public class base_newbie_creature_spawner extends script.base_script
{
    public static final boolean SPAWNER_DISABLED = true;
    public int maxPop = 1;
    public boolean newbie = false;

    public base_newbie_creature_spawner()
    {
    }
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        if (!SPAWNER_DISABLED)
        {
            spawnCreatures(self);
            if(newbie) LOG("NewbieSpawn", "Spawning Init: " + self);
        }
        return SCRIPT_CONTINUE;
    }
    public void spawnCreatures(obj_id self) throws InterruptedException
    {
        int count = utils.getIntScriptVar(self, "count");
        if (count < 0)
        {
            count = 0;
            CustomerServiceLog("SPAWNER_OVERLOAD", "Count went below 0 on " + self + " in base_newbie_creature_spawner script.");
        }
        location goodLoc;
        obj_id spawned;
        for (; count < maxPop; ++count)
        {
            if(newbie) LOG("NewbieSpawn", "spawning #" + count);
            goodLoc = pickLocation();
            if (goodLoc == null)
            {
                goodLoc = getLocation(self);
            }
            count++;
            utils.setScriptVar(self, "count", count);
            spawned = create.object(pickCreature(), goodLoc);
            attachScript(spawned, "creature_spawner.death_msg");
            setObjVar(spawned, "creater", self);
            ai_lib.setDefaultCalmBehavior(spawned, ai_lib.BEHAVIOR_LOITER);
        }
    }
    public String pickCreature() throws InterruptedException
    {
        return "crazed_durni";
    }
    public location pickLocation() throws InterruptedException
    {
        location here = getLocation(getSelf());
        here.x = here.x + rand(-5, 5);
        here.z = here.z + rand(-5, 5);
        return locations.getGoodLocationAroundLocation(here, 10f, 10f, 10f, 10f);
    }
    public int creatureDied(obj_id self, dictionary params) throws InterruptedException
    {
        doDeathRespawn(self);
        return SCRIPT_CONTINUE;
    }
    public void doDeathRespawn(obj_id self) throws InterruptedException
    {
        int count = utils.getIntScriptVar(self, "count");
        count = count - 1;
        if (count < 0)
        {
            count = 0;
            CustomerServiceLog("SPAWNER_OVERLOAD", "Count went below 0 on " + self + " in base_newbie_creature_spawner script.");
        }
        utils.setScriptVar(self, "count", count);
        spawnCreatures(self);
    }
}
