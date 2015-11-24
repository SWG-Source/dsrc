package script.creature_spawner;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.ai_lib;
import script.library.create;
import script.library.locations;
import script.library.spawning;
import script.library.utils;

public class rori_npc_hard extends script.base_script
{
    public rori_npc_hard()
    {
    }
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        spawnCreatures(self);
        return SCRIPT_CONTINUE;
    }
    public void spawnCreatures(obj_id self) throws InterruptedException
    {
        String whatToSpawn = utils.getStringScriptVar(self, "creatureToSpawn");
        if (whatToSpawn == null)
        {
            whatToSpawn = pickCreature();
            utils.setScriptVar(self, "creatureToSpawn", whatToSpawn);
        }
        int maxPop = 3;
        if (hasObjVar(self, "pop"))
        {
            maxPop = getIntObjVar(self, "pop");
        }
        int count = utils.getIntScriptVar(self, "count");
        while (count < maxPop)
        {
            location goodLoc = pickLocation();
            if (goodLoc == null)
            {
                goodLoc = getLocation(self);
            }
            obj_id spawned = create.object(whatToSpawn, goodLoc);
            attachScript(spawned, "creature_spawner.death_msg");
            setObjVar(spawned, "creater", self);
            if (!utils.hasScriptVar(self, "myCreations"))
            {
                Vector myCreations = new Vector();
                myCreations.setSize(0);
                utils.addElement(myCreations, spawned);
                utils.setScriptVar(self, "myCreations", myCreations);
            }
            else 
            {
                Vector theList = utils.getResizeableObjIdArrayScriptVar(self, "myCreations");
                if (theList.size() >= maxPop)
                {
                    CustomerServiceLog("SPAWNER_OVERLOAD", "Tried to spawn something even though the list was full.");
                    return;
                }
                else 
                {
                    theList.add(spawned);
                    utils.setScriptVar(self, "myCreations", theList);
                }
            }
            if (hasObjVar(self, "useCityWanderScript"))
            {
                attachScript(spawned, "city.city_wander");
            }
            else 
            {
                ai_lib.setDefaultCalmBehavior(spawned, ai_lib.BEHAVIOR_LOITER);
            }
            count++;
            utils.setScriptVar(self, "count", count);
        }
        return;
    }
    public String pickCreature() throws InterruptedException
    {
        int choice = rand(1, 5);
        String creature = "gundark_raider";
        switch (choice)
        {
            case 1:
            creature = "gundark_raider";
            break;
            case 2:
            creature = "gundark_ruffian";
            break;
            case 3:
            creature = "garyn_mugger";
            break;
            case 4:
            creature = "cobral_thug";
            break;
            case 5:
            creature = "garyn_pirate";
            break;
            default:
            creature = "garyn_pirate";
            break;
        }
        return creature;
    }
    public location pickLocation() throws InterruptedException
    {
        location here = getLocation(getSelf());
        here.x = here.x + rand(-5, 5);
        here.z = here.z + rand(-5, 5);
        location goodLoc = locations.getGoodLocationAroundLocation(here, 1f, 1f, 1.5f, 1.5f);
        return goodLoc;
    }
    public int creatureDied(obj_id self, dictionary params) throws InterruptedException
    {
        if (params == null || params.isEmpty())
        {
            LOG("sissynoid", "Spawner " + self + " on Rori. Rori_npc_medium script had Invalid Params from the deadGuy.");
            CustomerServiceLog("SPAWNER_OVERLOAD", "Spawner " + self + " on Rori. Rori_npc_medium script had Invalid Params from the deadGuy.");
            return SCRIPT_CONTINUE;
        }
        obj_id deadNpc = params.getObjId("deadGuy");
        spawning.planetSpawnersCreatureDied(self, deadNpc);
        spawnCreatures(self);
        return SCRIPT_CONTINUE;
    }
}
