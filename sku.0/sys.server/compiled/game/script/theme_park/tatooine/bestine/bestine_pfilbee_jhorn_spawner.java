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

public class bestine_pfilbee_jhorn_spawner extends script.base_script
{
    public bestine_pfilbee_jhorn_spawner()
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
        obj_id spawnCell = getCellId(self, "room2");
        location locImpLocation = new location(5.3f, 0.1f, -3.7f, "tatooine", spawnCell);
        obj_id template = create.object("pfilbee_jhorn", locImpLocation);
        setObjVar(template, "quest_table", "pfilbee_jhorn");
        attachScript(template, "npc.static_quest.quest_convo");
        int imp_yaw = -87;
        setYaw(template, imp_yaw);
        return SCRIPT_CONTINUE;
    }
}
