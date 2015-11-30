package script.theme_park;

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

public class quest_player extends script.base_script
{
    public quest_player()
    {
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        String datatable = getStringObjVar(self, "quest_table");
        String gatingString = dataTableGetString(datatable, 2, 1);
        String questID = dataTableGetString(datatable, 3, 1);
        int questNum = getIntObjVar(self, questID + ".questNum");
        String target = dataTableGetString(datatable, 8, questNum);
        int totalQuestNum = getIntObjVar(self, gatingString);
        String file = "theme_park_jabba/quest_details";
        String entry = questID + "_" + totalQuestNum;
        location questLoc = getLocationObjVar(self, questID + ".questLoc");
        quests.addThemeParkWaypoint(self, null, "quest", questLoc, 15, target, file, entry);
        addLocationTarget("spawnGuy", questLoc, 100);
        return SCRIPT_CONTINUE;
    }
    public int OnArrivedAtLocation(obj_id self, String name) throws InterruptedException
    {
        String datatable = getStringObjVar(self, "quest_table");
        String gatingString = dataTableGetString(datatable, 2, 1);
        int gating = getIntObjVar(self, gatingString);
        String questID = dataTableGetString(datatable, 3, 1);
        int questNum = getIntObjVar(self, questID + ".questNum");
        if (name.equals("quest"))
        {
            quests.clearQuestLocationTarget(self, null, name);
            obj_id vip = getObjIdObjVar(self, questID + ".vip");
            messageTo(vip, "saySomething", null, 0, true);
        }
        if (name.equals("spawnGuy"))
        {
            removeLocationTarget(name);
            location spawn = getLocationObjVar(self, questID + ".questLoc");
            String npcToSpawn = dataTableGetString(datatable, 7, questNum);
            if (npcToSpawn == null)
            {
                debugSpeakMsg(self, "Can't find anyone to spawn");
                return SCRIPT_CONTINUE;
            }
            String npcName = dataTableGetString(datatable, 8, questNum);
            String npcScript = dataTableGetString(datatable, 9, questNum);
            obj_id npc = create.object(npcToSpawn, spawn);
            attachScript(npc, npcScript);
            setObjVar(npc, "player", self);
            setObjVar(self, questID + ".vip", npc);
            setObjVar(npc, "quest_table", datatable);
            setObjVar(npc, "questNum", questNum);
            attachScript(npc, npcScript);
            if (npcName != null)
            {
                setName(npc, npcName);
            }
            spawnExtras(self, spawn);
        }
        if (name.equals("home"))
        {
            quests.clearQuestLocationTarget(self, null, name);
        }
        return SCRIPT_CONTINUE;
    }
    public int finishQuest(obj_id self, dictionary params) throws InterruptedException
    {
        String datatable = getStringObjVar(self, "quest_table");
        String gatingString = dataTableGetString(datatable, 2, 1);
        int gating = getIntObjVar(self, gatingString);
        String questID = dataTableGetString(datatable, 3, 1);
        int questNum = getIntObjVar(self, questID + ".questNum");
        location home = getLocationObjVar(self, questID + ".home");
        String file = "theme_park_jabba/quest_details";
        String entry = questID + "_return";
        setObjVar(self, questID + ".done", 1);
        quests.addThemeParkWaypoint(self, null, "home", home, 190, questID, file, entry);
        return SCRIPT_CONTINUE;
    }
    public void spawnExtras(obj_id self, location spawn) throws InterruptedException
    {
        String datatable = getStringObjVar(self, "quest_table");
        String gatingString = dataTableGetString(datatable, 2, 1);
        String questID = dataTableGetString(datatable, 3, 1);
        int questNum = getIntObjVar(self, questID + ".questNum");
        String[] max = dataTableGetStringColumnNoDefaults(datatable, questNum);
        int maxTotal = max.length;
        int x = 10;
        while (x <= maxTotal)
        {
            String extra = dataTableGetString(datatable, x, questNum);
            spawn.x = spawn.x + rand(1f, 3f);
            spawn.z = spawn.z + rand(1f, 3f);
            obj_id npc = create.object(extra, spawn);
            x = x + 1;
        }
    }
    public int OnIncapacitated(obj_id self, obj_id killer) throws InterruptedException
    {
        return SCRIPT_CONTINUE;
    }
}
