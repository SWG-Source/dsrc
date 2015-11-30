package script.theme_park.poi.test;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.ai_lib;
import script.library.factions;

public class pirate_leader extends script.theme_park.poi.base
{
    public pirate_leader()
    {
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        String objective = poiGetObjective(self);
        if (objective.equals("pirate"))
        {
            obj_id pirateLeader = poiCreateNpc("pirate", 10, 2);
            poiSetDestroyMessage(self, "imDead");
            attachScript(pirateLeader, "theme_park.poi.test.pirate_death");
            return SCRIPT_CONTINUE;
        }
        return SCRIPT_CONTINUE;
    }
    public int imDead(obj_id self, dictionary params) throws InterruptedException
    {
        poiComplete(POI_SUCCESS);
        return SCRIPT_CONTINUE;
    }
}
