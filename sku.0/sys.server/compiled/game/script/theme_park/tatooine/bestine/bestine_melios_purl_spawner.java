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

public class bestine_melios_purl_spawner extends script.base_script
{
    public bestine_melios_purl_spawner()
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
        obj_id spawnCell = getCellId(self, "mainroom");
        location locDocLocation = new location(-1.4f, 0.2f, 0.6f, "tatooine", spawnCell);
        obj_id template = create.object("melios_purl", locDocLocation);
        setObjVar(template, "quest_table", "melios_purl");
        attachScript(template, "npc.static_quest.quest_convo");
        int doc_yaw = 60;
        setYaw(template, doc_yaw);
        return SCRIPT_CONTINUE;
    }
}
