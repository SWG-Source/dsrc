package script.npc.random_quest;

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
        String gatingString = dataTableGetString(datatable, 1, "overall_objvar");
        String questID = dataTableGetString(datatable, 1, "temp_objvar");
        int questNum = getIntObjVar(self, questID + ".questNum");
        String type = dataTableGetString(datatable, questNum, "quest_type");
        String convo = dataTableGetString(datatable, questNum, "convo");
        String target = dataTableGetString(datatable, questNum, "npc_type");
        String display = type + " for a " + questID;
        String entry = "npc_1_" + questNum;
        location questLoc = getLocationObjVar(self, questID + ".questLoc");
        obj_id waypoint = quests.addThemeParkWaypoint(self, questID, "quest", questLoc, 15, display, convo, entry);
        if (questID != null && !questID.equals(""))
        {
            setObjVar(self, questID + ".waypoint", waypoint);
            addLocationTarget("spawnGuy", questLoc, 100);
        }
        return SCRIPT_CONTINUE;
    }
    public int OnArrivedAtLocation(obj_id self, String name) throws InterruptedException
    {
        String datatable = getStringObjVar(self, "quest_table");
        String gatingString = dataTableGetString(datatable, 1, "overall_objar");
        int gating = getIntObjVar(self, gatingString);
        String questID = dataTableGetString(datatable, 1, "temp_objvar");
        int questNum = getIntObjVar(self, questID + ".questNum");
        if (name.equals("quest"))
        {
            obj_id waypoint = getObjIdObjVar(self, questID + ".waypoint");
            if (waypoint != null)
            {
                destroyWaypointInDatapad(waypoint, self);
            }
            if (questID != null && !questID.equals(""))
            {
                setObjVar(self, questID + ".arrived", 1);
            }
            quests.clearQuestLocationTarget(self, questID, name);
            obj_id vip = getObjIdObjVar(self, questID + ".vip");
            messageTo(vip, "saySomething", null, 2, true);
        }
        if (name.equals("spawnGuy"))
        {
            removeLocationTarget(name);
            location spawn = getLocationObjVar(self, questID + ".questLoc");
            String npcToSpawn = dataTableGetString(datatable, questNum, "npc_type");
            if (npcToSpawn == null)
            {
                debugSpeakMsg(self, "Can't find anyone to spawn");
                return SCRIPT_CONTINUE;
            }
            String npcName = dataTableGetString(datatable, questNum, "npc_name");
            String npcScript = dataTableGetString(datatable, questNum, "npc_script");
            obj_id npc = create.object(npcToSpawn, spawn);
            attachScript(npc, npcScript);
            if (questID != null && !questID.equals(""))
            {
                setObjVar(npc, "player", self);
                setObjVar(self, questID + ".vip", npc);
                setObjVar(npc, "quest_table", datatable);
                setObjVar(npc, "questNum", questNum);
                attachScript(npc, npcScript);
            }
            if (npcName != null && !npcName.equals(""))
            {
                setName(npc, npcName);
            }
        }
        if (name.equals("home"))
        {
            obj_id waypoint = getObjIdObjVar(self, questID + ".waypoint");
            if (waypoint != null)
            {
                destroyWaypointInDatapad(waypoint, self);
            }
            quests.clearQuestLocationTarget(self, questID, name);
        }
        return SCRIPT_CONTINUE;
    }
    public int finishRandomQuest(obj_id self, dictionary params) throws InterruptedException
    {
        String datatable = getStringObjVar(self, "quest_table");
        String gatingString = dataTableGetString(datatable, 1, "overall_objar");
        int gating = getIntObjVar(self, gatingString);
        String questID = dataTableGetString(datatable, 1, "temp_objvar");
        int questNum = getIntObjVar(self, questID + ".questNum");
        String type = dataTableGetString(datatable, questNum, "quest_type");
        String convo = dataTableGetString(datatable, questNum, "convo");
        location home = getLocationObjVar(self, questID + ".home");
        if (questID != null && !questID.equals(""))
        {
            setObjVar(self, questID + ".done", 1);
            String display = "Return to " + type;
            String entry = "return";
            obj_id waypoint = quests.addThemeParkWaypoint(self, questID, "home", home, 20, display, convo, entry);
            setObjVar(self, questID + ".waypointhome", waypoint);
        }
        return SCRIPT_CONTINUE;
    }
    public int removeQuestInfo(obj_id self, dictionary params) throws InterruptedException
    {
        int questNum = getIntObjVar(self, "questNum");
        String datatable = getStringObjVar(self, "quest_table");
        String questID = dataTableGetString(datatable, questNum, "temp_objvar");
        String gatingString = dataTableGetString(datatable, questNum, "overall_objar");
        String selfScript = dataTableGetString(datatable, questNum, "player_script");
        String type = dataTableGetString(datatable, questNum, "quest_type");
        if (hasObjVar(self, questID + ".arrived"))
        {
            return SCRIPT_CONTINUE;
        }
        else 
        {
            if (type.equals("rescue"))
            {
                obj_id vip = getObjIdObjVar(self, questID + ".vip");
                messageTo(vip, "stopFollowing", null, 2, true);
            }
            removeObjVar(self, questID);
            removeObjVar(self, "quest_table");
            removeObjVar(self, "questNum");
            removeObjVar(self, "boss");
            if (hasScript(self, selfScript))
            {
                detachScript(self, selfScript);
            }
            return SCRIPT_CONTINUE;
        }
    }
}
