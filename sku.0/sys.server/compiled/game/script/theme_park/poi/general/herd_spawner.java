package script.theme_park.poi.general;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.ai_lib;

public class herd_spawner extends script.theme_park.poi.base
{
    public herd_spawner()
    {
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        doSpawning(self);
        return SCRIPT_CONTINUE;
    }
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        doSpawning(self);
        return SCRIPT_CONTINUE;
    }
    public void doSpawning(obj_id self) throws InterruptedException
    {
        String planet = getCurrentSceneName();
        debugSpeakMsg(self, "Planet is " + planet);
        String planetHerdTable = "datatables/herd/" + planet + "_herds.iff";
        debugSpeakMsg(self, "datatable is " + planetHerdTable);
        int herdRows = dataTableGetNumRows(planetHerdTable);
        debugSpeakMsg(self, "rows in datatable = " + herdRows);
        int randomHerd = rand(1, herdRows);
        debugSpeakMsg(self, "picked number " + randomHerd);
        randomHerd = randomHerd - 1;
        debugSpeakMsg(self, "changed it to " + randomHerd);
        String[] herdChoice = dataTableGetStringColumn(planetHerdTable, "herd");
        String herdToSpawn = dataTableGetString(planetHerdTable, randomHerd, "herd");
        String thing;
        if (hasObjVar(self, "creatureTemplate"))
        {
            thing = getStringObjVar(self, "creatureTemplate");
            thing = thing + "_large.iff";
        }
        else 
        {
            thing = "object/creature/monster/" + herdToSpawn;
            setObjVar(self, "creatureTemplate", thing);
            thing = thing + "_large.iff";
        }
        debugSpeakMsg(self, "This is " + thing);
        poiCreateObject(thing, 8, 8);
        setObjVar(self, "spawned", 1);
        dictionary herdString = new dictionary();
        messageTo(self, "spawnCreatures", herdString, 4, true);
        return;
    }
    public String getCreature(obj_id self) throws InterruptedException
    {
        String creatureSpawn = getStringObjVar(self, "creatureTemplate");
        int creatureType = rand(1, 2);
        switch (creatureType)
        {
            case 1:
            creatureSpawn = creatureSpawn + "_medium.iff";
            break;
            case 2:
            creatureSpawn = creatureSpawn + "_small.iff";
            break;
        }
        return creatureSpawn;
    }
    public int spawnCreatures(obj_id self, dictionary params) throws InterruptedException
    {
        dictionary herdString = new dictionary();
        int i = getIntObjVar(self, "spawned");
        int max = getIntObjVar(self, "numToSpawn");
        if (i <= max)
        {
            location here = new location(getLocation(self));
            here.x = rand(-10, 10);
            here.y = rand(-10, 10);
            obj_id critter = poiCreateObject(getCreature(self), here.x, here.y);
            if (rand(1, 20) == 1 && ai_lib.isMonster(critter))
            {
                attachScript(critter, "ai.pet_advance");
            }
            i = i + 1;
            setObjVar(self, "spawned", i);
            messageTo(self, "spawnCreatures", herdString, 3, true);
        }
        return SCRIPT_CONTINUE;
    }
}
