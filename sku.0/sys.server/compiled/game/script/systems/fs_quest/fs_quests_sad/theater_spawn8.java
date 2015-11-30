package script.systems.fs_quest.fs_quests_sad;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.quests;

public class theater_spawn8 extends script.base_script
{
    public theater_spawn8()
    {
    }
    public int OnTheaterCreated(obj_id self, dictionary params) throws InterruptedException
    {
        LOG("newquests", "theater_spawn1");
        obj_id player = params.getObjId("player");
        LOG("newquests", "theater_spawn1 player = " + player);
        location mylocation = getLocation(self);
        quests.createSpawner("fs_quest_sad8_1", mylocation, "datatables/quest/force_sensitive/base_spawn.iff", player);
        quests.createSpawner("fs_quest_sad8_2", mylocation, "datatables/quest/force_sensitive/base_spawn.iff", player);
        return SCRIPT_CONTINUE;
    }
}
