package script.npc.epic_quest;

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
        String target = dataTableGetString(datatable, 11, questNum);
        location questLoc = getLocationObjVar(self, questID + ".questLoc");
        quests.addQuestLocationTarget(self, questID, "quest", questLoc, 15);
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
            quests.clearQuestLocationTarget(self, questID, name);
            obj_id vip = getObjIdObjVar(self, questID + ".vip");
            messageTo(vip, "saySomething", null, 0, true);
        }
        if (name.equals("spawnGuy"))
        {
            removeLocationTarget(name);
            location spawn = getLocationObjVar(self, questID + ".questLoc");
            String npcToSpawn = dataTableGetString(datatable, 9, questNum);
            if (npcToSpawn == null)
            {
                debugSpeakMsg(self, "Can't find anyone to spawn");
                return SCRIPT_CONTINUE;
            }
            String npcName = dataTableGetString(datatable, 11, questNum);
            String npcScript = dataTableGetString(datatable, 10, questNum);
            obj_id npc = create.object(npcToSpawn, spawn);
            attachScript(npc, npcScript);
            setObjVar(npc, "player", self);
            setObjVar(self, questID + ".vip", npc);
            setObjVar(npc, "quest_table", datatable);
            setObjVar(npc, "questNum", questNum);
            attachScript(npc, npcScript);
            if (npcName != null && !npcName.equals("empty"))
            {
                setName(npc, npcName);
            }
        }
        if (name.equals("home"))
        {
            quests.clearQuestLocationTarget(self, null, name);
        }
        return SCRIPT_CONTINUE;
    }
    public int finishStaticQuest(obj_id self, dictionary params) throws InterruptedException
    {
        String datatable = getStringObjVar(self, "quest_table");
        String gatingString = dataTableGetString(datatable, 2, 1);
        int gating = getIntObjVar(self, gatingString);
        String questID = dataTableGetString(datatable, 3, 1);
        int questNum = getIntObjVar(self, questID + ".questNum");
        location home = getLocationObjVar(self, questID + ".home");
        setObjVar(self, questID + ".done", 1);
        quests.addQuestLocationTarget(self, null, "home", home, 20);
        return SCRIPT_CONTINUE;
    }
}
