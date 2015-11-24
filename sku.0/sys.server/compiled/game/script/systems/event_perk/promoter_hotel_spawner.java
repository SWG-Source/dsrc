package script.systems.event_perk;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.create;
import script.library.locations;

public class promoter_hotel_spawner extends script.base_script
{
    public promoter_hotel_spawner()
    {
    }
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        if (!hasObjVar(self, "event_perk.promoter"))
        {
            location here = getLocation(self);
            String myPlanet = getCurrentSceneName();
            String myCity = locations.getCityName(here);
            obj_id spawnCell = getCellId(self, "r10");
            location spawnPoint = getLocation(self);
            spawnPoint.x = -23.0f;
            spawnPoint.y = 1.6f;
            spawnPoint.z = -15.0f;
            spawnPoint.cell = spawnCell;
            if (!isIdValid(spawnCell))
            {
                return SCRIPT_CONTINUE;
            }
            obj_id template = create.object("object/tangible/ground_spawning/area_spawner.iff", spawnPoint);
            setObjVar(self, "event_perk.promoter", template);
            setObjVar(template, "spawns", "commoner");
            setObjVar(template, "npc_name", "An Event Promoter");
            setObjVar(template, "quest_script", "systems.event_perk.promoter");
            setObjVar(template, "promoter_type", "neutral");
            String spawnerObjName = "spawning: hotel_promoter_spawner_neutral";
            setName(template, spawnerObjName);
            attachScript(template, "systems.event_perk.promoter_spawner");
            return SCRIPT_CONTINUE;
        }
        return SCRIPT_CONTINUE;
    }
}
