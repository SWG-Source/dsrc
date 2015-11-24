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

public class bestine_farious_gletch_spawner extends script.base_script
{
    public bestine_farious_gletch_spawner()
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
        location locAsLocation = new location(2.0f, -0.4f, -5.3f, "tatooine", spawnCell);
        obj_id template = create.object("farious_gletch", locAsLocation);
        setObjVar(template, "quest_table", "farious_gletch");
        attachScript(template, "npc.static_quest.quest_convo");
        int as_yaw = -60;
        setYaw(template, as_yaw);
        return SCRIPT_CONTINUE;
    }
}
