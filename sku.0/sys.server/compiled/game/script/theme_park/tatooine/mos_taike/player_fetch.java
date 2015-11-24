package script.theme_park.tatooine.mos_taike;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.locations;
import script.library.quests;
import script.library.create;

public class player_fetch extends script.base_script
{
    public player_fetch()
    {
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        location questLoc = getLocationObjVar(self, "mos_taike.young_guard.fetchLoc");
        quests.addQuestLocationTarget(self, null, "quest", questLoc, 15);
        addLocationTarget("spawnGuy", questLoc, 100);
        return SCRIPT_CONTINUE;
    }
    public int OnArrivedAtLocation(obj_id self, String name) throws InterruptedException
    {
        int questNum = getIntObjVar(self, "mos_taike.young_guard_quest");
        String datatable = "datatables/theme_park/mos_taike_young_guard.iff";
        String enemy = dataTableGetString(datatable, 0, questNum - 1);
        String enemyName = dataTableGetString(datatable, 1, questNum - 1);
        if (name.equals("quest"))
        {
            quests.clearQuestLocationTarget(self, null, name);
        }
        if (name.equals("spawnGuy"))
        {
            removeLocationTarget(name);
            location spawn = getLocationObjVar(self, "mos_taike.young_guard.fetchLoc");
            obj_id npc = create.object(enemy, spawn);
            setName(npc, enemyName);
            attachScript(npc, "theme_park.tatooine.mos_taike.young_guard_fetch_enemy");
            setObjVar(npc, "player", self);
        }
        if (name.equals("questGiver"))
        {
            quests.clearQuestLocationTarget(self, null, name);
        }
        return SCRIPT_CONTINUE;
    }
    public int finishQuest(obj_id self, dictionary params) throws InterruptedException
    {
        location questGiver = getLocationObjVar(self, "mos_taike.young_guard.guardLoc");
        quests.addQuestLocationTarget(self, null, "questGiver", questGiver, 10);
        return SCRIPT_CONTINUE;
    }
}
