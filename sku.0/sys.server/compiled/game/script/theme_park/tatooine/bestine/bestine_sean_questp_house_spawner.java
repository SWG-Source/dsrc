package script.theme_park.tatooine.bestine;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.create;
import script.library.locations;

public class bestine_sean_questp_house_spawner extends script.base_script
{
    public bestine_sean_questp_house_spawner()
    {
    }
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        messageTo(self, "spawnMissingNpc", null, 2, false);
        return SCRIPT_CONTINUE;
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        messageTo(self, "spawnMissingNpc", null, 2, false);
        return SCRIPT_CONTINUE;
    }
    public int spawnMissingNpc(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id spawnCell = getCellId(self, "livingroom1");
        location locMaidLocation = new location(3.0f, 0.4f, -0.2f, "tatooine", spawnCell);
        obj_id template = create.object("object/tangible/ground_spawning/area_spawner.iff", locMaidLocation);
        setObjVar(template, "spawns", "sean_questp_house");
        setObjVar(template, "npc_name", "Edaekomeu Ossilei");
        setObjVar(template, "quest_script", "conversation.sean_questp_house");
        String spawnerObjName = "Edaekomeu Ossilei Spawner";
        setName(template, spawnerObjName);
        attachScript(template, "city.bestine.politician_event_spawner");
        return SCRIPT_CONTINUE;
    }
}
