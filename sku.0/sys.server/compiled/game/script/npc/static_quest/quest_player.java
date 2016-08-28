package script.npc.static_quest;

import script.dictionary;
import script.library.create;
import script.library.quests;
import script.location;
import script.obj_id;
import script.string_id;

public class quest_player extends script.base_script
{
    public quest_player()
    {
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        String datatable = getStringObjVar(self, "quest_table");
        String questID = dataTableGetString(datatable, 1, "temp_objvar");
        int questNum = getIntObjVar(self, questID + ".questNum");
        String CONVO = dataTableGetString(datatable, questNum, "convo");
        string_id waypointName = new string_id(CONVO, "waypoint_name_" + questNum);
        String target = getString(waypointName);
        if (target == null || target.equals(""))
        {
            target = dataTableGetString(datatable, questNum, "npc_name");
            if (target == null || target.equals(""))
            {
                target = dataTableGetString(datatable, questNum, "npc_type");
                if (target == null || target.equals(""))
                {
                    target = "Quest Waypoint";
                }
            }
        }
        string_id waypointDesc = new string_id(CONVO, "waypoint_description_" + questNum);
        String entry = getString(waypointDesc);
        if (entry == null || entry.equals(""))
        {
            entry = "npc_1_" + questNum;
        }
        else 
        {
            entry = "waypoint_description_" + questNum;
        }
        location questLoc = getLocationObjVar(self, questID + ".questLoc");
        obj_id waypoint = quests.addThemeParkWaypoint(self, questID, "quest", questLoc, 20, target, CONVO, entry);
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
        String questID = dataTableGetString(datatable, 1, "temp_objvar");
        int questNum = getIntObjVar(self, questID + ".questNum");
        String type = dataTableGetString(datatable, questNum, "quest_type");
        if (type == null)
        {
            type = "missing";
        }
        if (name.equals("quest"))
        {
            obj_id vip = getObjIdObjVar(self, questID + ".vip");
            if (!hasObjVar(self, questID + ".waypointhome"))
            {
                messageTo(vip, "saySomething", null, 1, true);
            }
        }
        if (name.equals("spawnGuy"))
        {
            if (type.equals("go"))
            {
                messageTo(self, "finishStaticGoQuest", null, 2, true);
                return SCRIPT_OVERRIDE;
            }
            location spawn = getLocationObjVar(self, questID + ".questLoc");
            String npcToSpawn = dataTableGetString(datatable, questNum, "npc_type");
            if (npcToSpawn == null)
            {
                string_id badNpc = new string_id("theme_park/messages", "bad_npc_message");
                String nospawn = getString(badNpc);
                sendSystemMessage(self, nospawn, null);
                if (questID != null && !questID.equals(""))
                {
                    setObjVar(self, questID + ".noloc", 1);
                }
                return SCRIPT_OVERRIDE;
            }
            String npcName = dataTableGetString(datatable, questNum, "npc_name");
            String npcScript = dataTableGetString(datatable, questNum, "npc_script");
            obj_id npc = create.object(npcToSpawn, spawn);
            if (questID != null && !questID.equals(""))
            {
                setObjVar(npc, "player", self);
                setObjVar(self, questID + ".vip", npc);
                setObjVar(npc, "quest_table", datatable);
                setObjVar(npc, "questNum", questNum);
                setObjVar(self, questID + ".arrived", 1);
                attachScript(npc, npcScript);
            }
            if (npcName != null && !npcName.equals(""))
            {
                setName(npc, npcName);
            }
            for(int i = 1; i <= 4; i++) {
                spawnExtraNpc(self, questNum, spawn, datatable, i);
            }
        }
        if (name.equals("home"))
        {
            obj_id waypoint = getObjIdObjVar(self, questID + ".waypointhome");
            if (waypoint != null)
            {
                destroyWaypointInDatapad(waypoint, self);
            }
            quests.clearQuestLocationTarget(self, null, name);
        }
        return SCRIPT_CONTINUE;
    }
    public int finishStaticQuest(obj_id self, dictionary params) throws InterruptedException
    {
        String datatable = getStringObjVar(self, "quest_table");
        String questID = dataTableGetString(datatable, 1, "temp_objvar");
        int questNum = getIntObjVar(self, questID + ".questNum");
        String file = dataTableGetString(datatable, 1, "convo");
        string_id waypointName = new string_id(file, "return_waypoint_name_" + questNum);
        String target = getString(waypointName);
        if (target == null || target.equals(""))
        {
            target = "Finish Quest";
        }
        string_id waypointDesc = new string_id(file, "return_waypoint_description_" + questNum);
        String entry = getString(waypointDesc);
        if (entry == null || entry.equals(""))
        {
            entry = "npc_reward_" + questNum;
        }
        else 
        {
            entry = "return_waypoint_description_" + questNum;
        }
        setObjVar(self, "GotFinishMessage", 1);
        obj_id waypoint = getObjIdObjVar(self, questID + ".waypoint");
        if (waypoint != null)
        {
            destroyWaypointInDatapad(waypoint, self);
        }
        location home = getLocationObjVar(self, questID + ".home");
        String type = dataTableGetString(datatable, questNum, "quest_type");
        if (type == null)
        {
            type = "missing";
        }
        if (!hasObjVar(self, questID + ".failed"))
        {
            if (type.equals("go"))
            {
                string_id goMsg = new string_id("theme_park/messages", "go_message");
                String going = getString(goMsg);
                sendSystemMessage(self, going, null);
                messageTo(self, "removeQuestInfo", null, 2, true);
            }
            else 
            {
                string_id done = new string_id("theme_park/messages", "static_completion_message");
                String finished = getString(done);
                sendSystemMessage(self, finished, null);
            }
        }
        if (questID != null && !questID.equals("") && !type.equals("go"))
        {
            setObjVar(self, questID + ".done", 1);
            obj_id waypointhome = quests.addThemeParkWaypoint(self, questID, "quest", home, 15, target, file, entry);
            setObjVar(self, questID + ".waypointhome", waypointhome);
        }
        return SCRIPT_CONTINUE;
    }
    public int finishStaticGoQuest(obj_id self, dictionary params) throws InterruptedException
    {
        String datatable = getStringObjVar(self, "quest_table");
        String questID = dataTableGetString(datatable, 1, "temp_objvar");
        obj_id waypoint = getObjIdObjVar(self, questID + ".waypoint");
        if (waypoint != null)
        {
            destroyWaypointInDatapad(waypoint, self);
        }
        String going = getString(new string_id("theme_park/messages", "go_message"));
        sendSystemMessage(self, going, null);
        messageTo(self, "removeQuestInfo", null, 2, true);
        return SCRIPT_CONTINUE;
    }
    private void spawnExtraNpc(obj_id self, int questNum, location spawnLoc, String datatable, int index) throws InterruptedException{
        String spawn = dataTableGetString(datatable, questNum, "extra_npc" + index);
        location npcSpawn = (location) spawnLoc.clone();
        int ranges[] = {3, -3, 10, -10};
        if (spawn != null && !spawn.equals(""))
        {
            npcSpawn.x = npcSpawn.x + rand(ranges[rand(0,1)], ranges[rand(2,3)]);
            npcSpawn.z = npcSpawn.z + rand(ranges[rand(0,1)], ranges[rand(2,3)]);
            obj_id extra = create.object(spawn, npcSpawn);
            if (extra != null)
            {
                String disposition = dataTableGetString(datatable, questNum, "extra_npc" + index + "_disposition");
                if (disposition.equals("aggro") || disposition.equals("aggressive"))
                {
                    startCombat(extra, self);
                }
            }
        }
    }
    public int removeQuestInfo(obj_id self, dictionary params) throws InterruptedException
    {
        String questID = params.getString("questID");
        if (questID == null)
        {
            questID = "none";
        }
        int questNum = getIntObjVar(self, questID + ".questNum");
        String datatable = getStringObjVar(self, "quest_table");
        if (datatable == null)
        {
            return SCRIPT_OVERRIDE;
        }
        String selfScript = dataTableGetString(datatable, questNum, "player_script");
        String type = dataTableGetString(datatable, questNum, "quest_type");
        obj_id waypoint = getObjIdObjVar(self, questID + ".waypoint");
        if (type == null)
        {
            type = "";
        }
        if (waypoint != null)
        {
            destroyWaypointInDatapad(waypoint, self);
        }
        if (hasObjVar(self, questID + ".vip"))
        {
            return SCRIPT_CONTINUE;
        }
        else 
        {
            if (type.equals("rescue") || type.equals("arrest") || type.equals("escort"))
            {
                obj_id vip = getObjIdObjVar(self, questID + ".vip");
                messageTo(vip, "stopFollowing", null, 0, true);
            }
            removeObjVar(self, questID);
            removeObjVar(self, "questID");
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
    public int OnWaypointGetAttributes(obj_id self, obj_id waypoint, obj_id player, String[] names, String[] attribs) throws InterruptedException
    {
        String questId = getStringObjVar(player, "questID");
        String file = getStringObjVar(player, questId + ".file");
        String entry = getStringObjVar(player, questId + ".entry");
        obj_id wp = getObjIdObjVar(player, questId + ".waypoint");
        obj_id wph = getObjIdObjVar(player, questId + ".waypointhome");
        if (waypoint != wp && waypoint != wph)
        {
            return SCRIPT_OVERRIDE;
        }
        string_id detail = new string_id(file, entry);
        String questDetails = "@" + detail.toString();
        int idx = 0;
        while (idx >= 0)
        {
            if (names[idx] == null)
            {
                names[idx] = "quest_details";
                attribs[idx] = questDetails;
                idx = -1;
            }
            else 
            {
                idx = idx + 1;
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int OnWaypointDestroyed(obj_id self, obj_id waypoint) throws InterruptedException
    {
        String questID = getStringObjVar(self, "questID");
        if (questID == null)
        {
            questID = "none";
        }
        obj_id questWaypoint = getObjIdObjVar(self, questID + ".waypoint");
        if (questWaypoint != null)
        {
            if (questWaypoint == waypoint)
            {
                dictionary questBook = new dictionary();
                questBook.put("questID", questID);
                messageTo(self, "removeQuestInfo", questBook, 2, true);
            }
        }
        return SCRIPT_CONTINUE;
    }
}
