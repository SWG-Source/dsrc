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

public class player_destroy extends script.base_script
{
    public player_destroy()
    {
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        location questLoc = getLocationObjVar(self, "mos_taike.old_guard.targetLoc");
        quests.addQuestLocationTarget(self, null, "quest", questLoc, 15);
        addLocationTarget("spawnGuy", questLoc, 100);
        return SCRIPT_CONTINUE;
    }
    public int OnArrivedAtLocation(obj_id self, String name) throws InterruptedException
    {
        String datatable = "datatables/theme_park/mos_taike_old_guard.iff";
        int questNum = getIntObjVar(self, "mos_taike.old_guard_quest");
        String creature = dataTableGetString(datatable, 0, questNum - 1);
        String creatureName = dataTableGetString(datatable, 1, questNum - 1);
        if (name.equals("quest"))
        {
            quests.clearQuestLocationTarget(self, null, name);
        }
        if (name.equals("spawnGuy"))
        {
            removeLocationTarget(name);
            location spawn = getLocationObjVar(self, "mos_taike.old_guard.targetLoc");
            obj_id npc = create.object(creature, spawn);
            setName(npc, creatureName);
            String rewardScript = "theme_park.tatooine.mos_taike.old_guard_destroy_enemy";
            attachScript(npc, rewardScript);
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
        location questGiver = getLocationObjVar(self, "mos_taike.old_guard.loc");
        quests.addQuestLocationTarget(self, null, "questGiver", questGiver, 10);
        setObjVar(self, "mos_taike.old_guard.questDone", 1);
        return SCRIPT_CONTINUE;
    }
}
