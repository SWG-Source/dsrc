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

public class bestine_sean_questn_university_spawner extends script.base_script
{
    public bestine_sean_questn_university_spawner()
    {
    }
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        messageTo(self, "spawnMissingNpc", null, 2, false);
        messageTo(self, "spawnInformantNpc", null, 2, false);
        return SCRIPT_CONTINUE;
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        messageTo(self, "spawnMissingNpc", null, 2, false);
        messageTo(self, "spawnInformantNpc", null, 2, false);
        return SCRIPT_CONTINUE;
    }
    public int spawnMissingNpc(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id spawnCell = getCellId(self, "mainroom");
        location locTeacherLocation = new location(3.6f, 1.1f, -4.6f, "tatooine", spawnCell);
        obj_id template = create.object("object/tangible/ground_spawning/area_spawner.iff", locTeacherLocation);
        setObjVar(template, "spawns", "sean_questn_university");
        setObjVar(template, "npc_name", "Ocket Abaot");
        setObjVar(template, "quest_script", "conversation.sean_questn_university");
        String spawnerObjName = "Ocket Abaot Spawner";
        setName(template, spawnerObjName);
        attachScript(template, "city.bestine.politician_event_spawner");
        return SCRIPT_CONTINUE;
    }
    public int spawnInformantNpc(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id spawnCell = getCellId(self, "meetinge");
        location locImpLocation = new location(-6.5f, 1.1f, -11.6f, "tatooine", spawnCell);
        obj_id template = create.object("coa3_information_imperial", locImpLocation);
        int inform_yaw = -90;
        setYaw(template, inform_yaw);
        return SCRIPT_CONTINUE;
    }
}
