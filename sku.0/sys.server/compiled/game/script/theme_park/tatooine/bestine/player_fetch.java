package script.theme_park.tatooine.bestine;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.locations;
import script.library.chat;
import script.library.quests;
import script.library.create;
import script.ai.ai_combat;

public class player_fetch extends script.base_script
{
    public player_fetch()
    {
    }
    public static final String CONVO = "theme_park_bestine/curator";
    public int OnAttach(obj_id self) throws InterruptedException
    {
        location questLoc = getLocationObjVar(self, "bestine.curator.fetchLoc");
        quests.addQuestLocationTarget(self, null, "quest", questLoc, 15);
        addLocationTarget("spawnGuy", questLoc, 100);
        return SCRIPT_CONTINUE;
    }
    public int OnArrivedAtLocation(obj_id self, String name) throws InterruptedException
    {
        int questNum = getIntObjVar(self, "bestine.curator_quest");
        String datatable = "datatables/theme_park/bestine_curator.iff";
        String enemy = dataTableGetString(datatable, 0, questNum - 1);
        if (enemy == null)
        {
            enemy = "thug";
        }
        String enemyName = dataTableGetString(datatable, 1, questNum - 1);
        if (enemyName == null)
        {
            enemyName = "Bob";
        }
        if (name.equals("quest"))
        {
            obj_id fight = getObjIdObjVar(self, "bestine.curator.npc");
            string_id message = new string_id(CONVO, "enemy_" + questNum);
            chat.chat(fight, message);
            startCombat(fight, self);
            quests.clearQuestLocationTarget(self, null, name);
        }
        if (name.equals("spawnGuy"))
        {
            removeLocationTarget(name);
            location spawn = getLocationObjVar(self, "bestine.curator.fetchLoc");
            if (spawn == null)
            {
                spawn = getLocation(self);
                spawn.x = spawn.x + 10;
            }
            obj_id npc = create.object(enemy, spawn);
            setName(npc, enemyName);
            attachScript(npc, "theme_park.tatooine.bestine.curator_fetch_enemy");
            setObjVar(npc, "player", self);
            setObjVar(self, "bestine.curator.npc", npc);
        }
        if (name.equals("questGiver"))
        {
            quests.clearQuestLocationTarget(self, null, name);
        }
        return SCRIPT_CONTINUE;
    }
    public int finishQuest(obj_id self, dictionary params) throws InterruptedException
    {
        location questGiver = getLocationObjVar(self, "bestine.curator.guardLoc");
        quests.addQuestLocationTarget(self, null, "questGiver", questGiver, 10);
        return SCRIPT_CONTINUE;
    }
}
