package script.city.bestine;

import script.deltadictionary;
import script.dictionary;
import script.library.create;
import script.library.utils;
import script.location;
import script.obj_id;

public class tusken_master_spawner extends script.base_script
{
    public tusken_master_spawner()
    {
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        deltadictionary tuskenSpawn = self.getScriptVars();
        tuskenSpawn.put("count", 0);
        tuskenSpawn.put("wave",0);
        messageTo(self, "checkSpawnStatus", null, 60, false);
        return SCRIPT_CONTINUE;
    }
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        deltadictionary tuskenSpawn = self.getScriptVars();
        tuskenSpawn.put("count", 0);
        messageTo(self, "checkSpawnStatus", null, 60, false);
        return SCRIPT_CONTINUE;
    }
    public int checkSpawnStatus(obj_id self, dictionary params) throws InterruptedException
    {
        int count = utils.getIntScriptVar(self, "count");
        if (count == 0)
        {
            int delay = 3600 + rand(300, 900);
            if(utils.getIntScriptVar(self, "wave") == 0) delay = 1;
            utils.setScriptVar(self, "delay", delay);
            utils.setScriptVar(self, "nextSpawn", delay + getGameTime());
            messageTo(self, "spawnTuskenNpcs", null, delay, false);
        }
        return SCRIPT_CONTINUE;
    }
    public int spawnTuskenNpcs(obj_id self, dictionary params) throws InterruptedException
    {
        deltadictionary tuskenSpawn = self.getScriptVars();
        int count = tuskenSpawn.getInt("count");
        tuskenSpawn.put("wave", tuskenSpawn.getInt("wave") + 1);
        obj_id building = getTopMostContainer(self);

        try {
            createSpawn(self, new location(6.54f, 8.01f, -34.17f, "tatooine", getCellId(building, "r5")), "tusken_executioner");
            count++;

            createSpawn(self, new location(1.20f, 8.15f, -20.20f, "tatooine", getCellId(building, "r5")), "tusken_observer");
            count++;

            createSpawn(self, new location(-1.54f, 1.58f, -0.90f, "tatooine", getCellId(building, "r2")), "tusken_observer");
            count++;

            createSpawn(self, new location(3.07f, 8.31f, -39.26f, "tatooine", getCellId(building, "r5")), "tusken_observer");
            count++;

            createSpawn(self, new location(2.17f, 8.36f, -32.02f, "tatooine", getCellId(building, "r5")), "tusken_witch_doctor");
            count++;
        }
        catch(Exception e){
            // do nothing.
        }
        tuskenSpawn.put("lastSpawned", getGameTime());
        tuskenSpawn.put("count", count);

        return SCRIPT_CONTINUE;
    }
    public int doDeathRespawn(obj_id self, dictionary params) throws InterruptedException
    {
        deltadictionary tuskenSpawn = self.getScriptVars();
        int count = tuskenSpawn.getInt("count");
        tuskenSpawn.put("count", count - 1);
        messageTo(self, "checkSpawnStatus", null, 1, false);
        return SCRIPT_CONTINUE;
    }
    private obj_id createSpawn(obj_id self, location spawnLoc, String template) throws InterruptedException{
        obj_id tusken = create.object(template, spawnLoc);
        attachScript(tusken, "city.bestine.tusken_spawner");
        setObjVar(tusken, "spawner", self);
        return tusken;
    }
}
