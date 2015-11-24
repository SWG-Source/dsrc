package script.creature_spawner;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.locations;
import script.library.create;
import script.library.ai_lib;
import script.library.utils;

public class base_newbie_creature_spawner extends script.base_script
{
    public base_newbie_creature_spawner()
    {
    }
    public void spawnCreatures(obj_id self) throws InterruptedException
    {
        deltadictionary spawn = self.getScriptVars();
        int maxPop = getMaxPop();
        int count = utils.getIntScriptVar(self, "count");
        String whatToSpawn;
        if (count < 0)
        {
            count = 0;
            CustomerServiceLog("SPAWNER_OVERLOAD", "Count went below 0 on " + self + " in base_newbie_creature_spawner script.");
        }
        for (; count < maxPop; ++count)
        {
            location goodLoc = pickLocation();
            if (goodLoc == null)
            {
                goodLoc = getLocation(self);
            }
            whatToSpawn = pickCreature();
            count++;
            utils.setScriptVar(self, "count", count);
            obj_id spawned = create.object(whatToSpawn, goodLoc);
            attachScript(spawned, "creature_spawner.death_msg");
            setObjVar(spawned, "creater", self);
            ai_lib.setDefaultCalmBehavior(spawned, ai_lib.BEHAVIOR_LOITER);
        }
        return;
    }
    public String pickCreature() throws InterruptedException
    {
        return "crazed_durni";
    }
    public int getMaxPop() throws InterruptedException
    {
        return 1;
    }
    public location pickLocation() throws InterruptedException
    {
        location here = getLocation(getSelf());
        here.x = here.x + rand(-5, 5);
        here.z = here.z + rand(-5, 5);
        location goodLoc = locations.getGoodLocationAroundLocation(here, 10f, 10f, 10f, 10f);
        return goodLoc;
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
        return;
    }
}
