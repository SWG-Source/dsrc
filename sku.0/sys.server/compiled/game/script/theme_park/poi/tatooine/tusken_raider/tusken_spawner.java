package script.theme_park.poi.tatooine.tusken_raider;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

public class tusken_spawner extends script.theme_park.poi.base
{
    public tusken_spawner()
    {
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        messageTo(self, "doSpawning", null, 10, true);
        return SCRIPT_CONTINUE;
    }
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        messageTo(self, "doSpawning", null, 10, true);
        return SCRIPT_CONTINUE;
    }
    public int doSpawning(obj_id self, dictionary params) throws InterruptedException
    {
        int tuskensSpawned = getIntObjVar(self, "tuskensSpawned");
        if (tuskensSpawned <= 10)
        {
            debugSpeakMsg(self, "I'm spawning tuskens # " + tuskensSpawned);
            String tuskenTable = "datatables/spawning/spawn_lists/tatooine/tatooine_fort_tusken.iff";
            int rows = dataTableGetNumRows(tuskenTable);
            int randomRow = rand(1, rows);
            randomRow = randomRow - 1;
            String tuskenToSpawn = dataTableGetString(tuskenTable, randomRow, "tuskens");
            String tusken = "object/tangible/poi/tatooine/poi_tuskenraider_" + tuskenToSpawn + ".iff";
            poiCreateObject(self, tusken, 8, 8);
            tuskensSpawned = tuskensSpawned + 1;
            setObjVar(self, "tuskensSpawned", tuskensSpawned);
            messageTo(self, "doSpawning", null, 30, true);
        }
        return SCRIPT_CONTINUE;
    }
}
