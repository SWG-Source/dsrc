package script.theme_park.jabba;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.ai.ai_combat;
import script.library.locations;
import script.library.quests;
import script.library.create;
import script.library.factions;

public class quest_rantok extends script.base_script
{
    public quest_rantok()
    {
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        String datatable = getStringObjVar(self, "quest_table");
        String gatingString = dataTableGetString(datatable, 1, "overall_objvar");
        String questID = dataTableGetString(datatable, 1, "temp_objvar");
        int questNum = getIntObjVar(self, questID + ".questNum");
        String target = dataTableGetString(datatable, questNum, "npc_name");
        int totalQuestNum = getIntObjVar(self, gatingString);
        String file = "theme_park_jabba/quest_details";
        String entry = questID + "_" + totalQuestNum;
        location questLoc = getLocationObjVar(self, questID + ".questLoc");
        obj_id waypoint = quests.addThemeParkWaypoint(self, questID, "quest", questLoc, 15, target, file, entry);
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
        String gatingString = dataTableGetString(datatable, 1, "overall_objvar");
        int gating = getIntObjVar(self, gatingString);
        String questID = dataTableGetString(datatable, 1, "temp_objvar");
        int questNum = getIntObjVar(self, questID + ".questNum");
        if (name.equals("quest"))
        {
            quests.clearQuestLocationTarget(self, questID, name);
        }
        if (name.equals("spawnGuy"))
        {
            removeLocationTarget(name);
            location spawn = getLocationObjVar(self, questID + ".questLoc");
            String toSpawn = dataTableGetString(datatable, questNum, "extra_spawn");
            String bldgScript = dataTableGetString(datatable, questNum, "spawn_script");
            obj_id bldg = createObject(toSpawn, spawn);
            setObjVar(self, questID + ".vip", bldg);
            attachScript(bldg, bldgScript);
            if (questID != null && !questID.equals(""))
            {
                setObjVar(bldg, "player", self);
                setObjVar(self, questID + ".bldg", bldg);
                setObjVar(bldg, "quest_table", datatable);
                setObjVar(bldg, "questNum", questNum);
            }
            createGuards(bldg);
        }
        if (name.equals("home"))
        {
            quests.clearQuestLocationTarget(self, questID, name);
        }
        return SCRIPT_CONTINUE;
    }
    public int finishRantokQuest(obj_id self, dictionary params) throws InterruptedException
    {
        String datatable = getStringObjVar(self, "quest_table");
        String gatingString = dataTableGetString(datatable, 1, "overall_objvar");
        int gating = getIntObjVar(self, gatingString);
        String questID = dataTableGetString(datatable, 1, "temp_objvar");
        int questNum = getIntObjVar(self, questID + ".questNum");
        location home = getLocationObjVar(self, questID + ".home");
        String file = "theme_park_jabba/quest_details";
        String entry = questID + "_return";
        obj_id waypoint = getObjIdObjVar(self, questID + ".waypoint");
        if (waypoint != null)
        {
            destroyWaypointInDatapad(waypoint, self);
        }
        if (questID != null && !questID.equals(""))
        {
            setObjVar(self, questID + ".done", 1);
            obj_id waypointhome = quests.addThemeParkWaypoint(self, questID, "home", home, 190, "Finish Quest", file, entry);
            sendSystemMessage(self, "Return to Jabba's Palace for your reward, the mission is complete.", null);
            setObjVar(self, questID + ".waypointhome", waypointhome);
        }
        return SCRIPT_CONTINUE;
    }
    public void createGuards(obj_id bldg) throws InterruptedException
    {
        location g1 = getLocation(bldg);
        g1.x = g1.x + 10;
        location g2 = getLocation(bldg);
        g2.x = g2.x - 10;
        location g3 = getLocation(bldg);
        g3.z = g3.z - 10;
        location g4 = getLocation(bldg);
        g4.z = g4.z + 10;
        obj_id guard1 = create.object("valarian_thug", g1);
        obj_id guard2 = create.object("valarian_thug", g2);
        obj_id guard3 = create.object("valarian_thug", g3);
        obj_id guard4 = create.object("valarian_thug", g4);
        setUpNpc(guard1);
        setUpNpc(guard2);
        setUpNpc(guard3);
        setUpNpc(guard4);
        factions.setFaction(guard1, "valarian");
        factions.setFaction(guard2, "valarian");
        factions.setFaction(guard3, "valarian");
        factions.setFaction(guard4, "valarian");
        setObjVar(bldg, "guard1", guard1);
        setObjVar(bldg, "guard2", guard2);
        setObjVar(bldg, "guard3", guard3);
        setObjVar(bldg, "guard4", guard4);
        dictionary enemies = new dictionary();
        enemies.put("guard1", guard1);
        enemies.put("guard2", guard2);
        enemies.put("guard3", guard3);
        enemies.put("guard4", guard4);
        enemies.put("player", getSelf());
        messageTo(getSelf(), "makeEnemyAttack", enemies, 3, true);
        return;
    }
    public void setUpNpc(obj_id newNpc) throws InterruptedException
    {
        return;
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
            String currentName = names[idx];
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
            return SCRIPT_OVERRIDE;
        }
        else 
        {
            int stringCheck = questID.indexOf("jabbas_palace");
            if (stringCheck > -1)
            {
                dictionary questBook = new dictionary();
                questBook.put("questID", questID);
                messageTo(self, "removeJabbaQuestInfo", questBook, 2, true);
                return SCRIPT_CONTINUE;
            }
            else 
            {
                return SCRIPT_OVERRIDE;
            }
        }
    }
    public int makeEnemyAttack(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id guard1 = params.getObjId("guard1");
        obj_id guard2 = params.getObjId("guard2");
        obj_id guard3 = params.getObjId("guard3");
        obj_id guard4 = params.getObjId("guard4");
        obj_id player = params.getObjId("player");
        startCombat(guard1, player);
        startCombat(guard2, player);
        startCombat(guard3, player);
        startCombat(guard4, player);
        return SCRIPT_CONTINUE;
    }
    public int removeJabbaQuestInfo(obj_id self, dictionary params) throws InterruptedException
    {
        String questID = params.getString("questID");
        if (questID == null)
        {
            questID = "none";
        }
        int questNum = getIntObjVar(self, questID + ".questNum");
        String datatable = getStringObjVar(self, "quest_table");
        String gatingString = dataTableGetString(datatable, 1, "overall_objvar");
        String selfScript = dataTableGetString(datatable, questNum, "player_script");
        String type = dataTableGetString(datatable, questNum, "quest_type");
        obj_id waypoint = getObjIdObjVar(self, questID + ".waypoint");
        if (waypoint != null)
        {
            destroyObject(waypoint);
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
                messageTo(vip, "stopFollowing", null, 1, true);
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
