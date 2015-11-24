package script.quest.force_sensitive;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.locations;
import script.library.quests;
import script.library.utils;
import script.library.create;
import script.library.fs_dyn_village;

public class fs_patrol_spawns1 extends script.base_script
{
    public fs_patrol_spawns1()
    {
    }
    public static final boolean DEBUGGING = false;
    public static final String[] ENEMY_LIST = 
    {
        "shadow_mercenary",
        "shadow_thug",
        "shadow_pirate",
        "shadow_outlaw"
    };
    public int msgQuestAbortPhaseChange(obj_id self, dictionary params) throws InterruptedException
    {
        detachScript(self, "quest.force_sensitive.fs_patrol_spawns1");
        return SCRIPT_CONTINUE;
    }
    public int handleDontKillPatrolFSNpc(obj_id self, dictionary params) throws InterruptedException
    {
        setObjVar(self, "quest.fs_patrol.current_patrol", false);
        string_id messageId = new string_id("quest/force_sensitive/fs_patrol", "failed_killed_dont_kill_npc");
        sendSystemMessage(self, messageId);
        return SCRIPT_CONTINUE;
    }
    public int handleKillPatrolFSNpc(obj_id self, dictionary params) throws InterruptedException
    {
        if (hasObjVar(self, "quest.fs_patrol.number_of_enemies"))
        {
            
        }
        
        {
            int numberOfEnemies = getIntObjVar(self, "quest.fs_patrol.number_of_enemies");
            numberOfEnemies--;
            if (numberOfEnemies <= 0)
            {
                setObjVar(self, "quest.fs_patrol.current_patrol_point", true);
            }
            setObjVar(self, "quest.fs_patrol.number_of_enemies", numberOfEnemies);
        }
        return SCRIPT_CONTINUE;
    }
    public void spawnStuff(int numberOfEnemies, location startLocation, String questName, obj_id player) throws InterruptedException
    {
        setObjVar(player, "quest.fs_patrol.number_of_enemies", numberOfEnemies);
        for (int i = 0; i < numberOfEnemies; i++)
        {
            int offsetx = rand(-6, 6);
            int offsetz = rand(-6, 6);
            int npcType = 2;
            if (numberOfEnemies > 3)
            {
                npcType = rand(0, 2);
            }
            if (numberOfEnemies < 3)
            {
                npcType = rand(2, 3);
            }
            location spawnSpot = startLocation;
            spawnSpot.x += offsetx;
            spawnSpot.z += offsetz;
            obj_id spawn = create.object(ENEMY_LIST[npcType], spawnSpot);
            setObjVar(spawn, "quest.fs_patrol.spawned_for", player);
            if (numberOfEnemies <= 3)
            {
                attachScript(spawn, "systems.fs_quest.patrol.fs_killable_npc");
            }
            else 
            {
                attachScript(spawn, "systems.fs_quest.patrol.fs_dont_kill_npc");
            }
        }
    }
    public void cleanUpStuff(obj_id player) throws InterruptedException
    {
        String questName = "fs_patrol_quest_";
        if (!quests.isComplete("fs_patrol_quest_10", player))
        {
            sendSystemMessage(player, new string_id("fs_quest_village", "combat_quest_failed_timeout"));
        }
        for (int j = 1; j < 21; j++)
        {
            if (!quests.isComplete("fs_patrol_quest_10", player))
            {
                clearCompletedQuest(player, quests.getQuestId(questName + j));
                quests.deactivate(questName + j, player);
            }
            else if (quests.isComplete("fs_patrol_quest_10", player) && !quests.isComplete("fs_patrol_quest_20", player))
            {
                if (j > 10)
                {
                    clearCompletedQuest(player, quests.getQuestId(questName + j));
                    quests.deactivate(questName + j, player);
                }
            }
            for (int i = 0; i < 8; i++)
            {
                if (hasObjVar(player, "quest." + questName + j + ".waypoint" + i))
                {
                    obj_id waypoint = getObjIdObjVar(player, "quest." + questName + j + ".waypoint" + i);
                    if (isIdValid(waypoint))
                    {
                        destroyWaypointInDatapad(waypoint, player);
                    }
                    removeObjVar(player, "quest." + questName + j + ".waypoint" + i);
                    removeObjVar(player, "quest." + questName + j + ".waypointcount");
                }
            }
        }
        if (!quests.isComplete("fs_patrol_quest_10", player))
        {
            clearCompletedQuest(player, quests.getQuestId(questName + "start"));
            quests.deactivate(questName + "start", player);
        }
        else 
        {
            quests.complete(questName + "finish", player, true);
        }
        if (hasScript(player, "quest.task.patrol"))
        {
            detachScript(player, "quest.task.patrol");
        }
        removeObjVar(player, "quest.fs_patrol");
        if (DEBUGGING)
        {
            sendSystemMessageTestingOnly(player, "Cleanup Done");
        }
    }
    public int OnDeath(obj_id self, obj_id killer, obj_id corpseId) throws InterruptedException
    {
        setObjVar(self, "quest.fs_patrol.current_patrol", false);
        string_id messageId = new string_id("quest/force_sensitive/fs_patrol", "failed_death");
        sendSystemMessage(self, messageId);
        return SCRIPT_CONTINUE;
    }
    public int OnArrivedAtLocation(obj_id self, String locationName) throws InterruptedException
    {
        if (DEBUGGING)
        {
            sendSystemMessageTestingOnly(self, "Trigger 2");
        }
        String[] tokens = split(locationName, '.');
        String questName = "";
        int waypointNumber = -1;
        if (tokens.length > 0)
        {
            String s = tokens[0];
            if (!Character.isDigit(s.charAt(0)))
            {
                questName = tokens[0];
                if (tokens.length > 2)
                {
                    String wpNum = tokens[2];
                    if (Character.isDigit(wpNum.charAt(0)))
                    {
                        waypointNumber = utils.stringToInt(tokens[2]);
                        if (DEBUGGING)
                        {
                            sendSystemMessageTestingOnly(self, "wptoken done");
                        }
                    }
                }
            }
        }
        if (quests.isActive(questName, self) && waypointNumber != -1)
        {
            if (hasObjVar(self, "quest." + questName + ".waypoint" + waypointNumber))
            {
                if (hasObjVar(self, "quest.fs_patrol.current_patrol_point"))
                {
                    if (!getBooleanObjVar(self, "quest.fs_patrol.current_patrol_point"))
                    {
                        setObjVar(self, "quest.fs_patrol.current_patrol", false);
                        string_id messageId = new string_id("quest/force_sensitive/fs_patrol", "failed_previous_point");
                        sendSystemMessage(self, messageId);
                    }
                }
                obj_id wp = getObjIdObjVar(self, "quest." + questName + ".waypoint" + waypointNumber);
                location startLocation = getWaypointLocation(wp);
                int waypointCount = 0;
                if (hasObjVar(self, "quest." + questName + ".waypointcount"))
                {
                    waypointCount = getIntObjVar(self, "quest." + questName + ".waypointcount");
                }
                int chance = rand(1, 100);
                obj_id[] objects = getObjectsInRange(startLocation, 64.0f);
                int iter;
                for (iter = 0; iter < objects.length; ++iter)
                {
                    if (hasObjVar(objects[iter], "quest.fs_patrol.spawned_for"))
                    {
                        chance = 1;
                        break;
                    }
                }
                if (chance >= 80)
                {
                    setObjVar(self, "quest.fs_patrol.current_patrol_point", false);
                    int numberOfEnemies = rand(1, 3);
                    string_id messageId = new string_id("quest/force_sensitive/fs_patrol", "small_group" + numberOfEnemies);
                    sendSystemMessage(self, messageId);
                    spawnStuff(numberOfEnemies, startLocation, questName, self);
                }
                else if (chance >= 75 && chance < 80)
                {
                    setObjVar(self, "quest.fs_patrol.current_patrol_point", true);
                    int numberOfEnemies = rand(5, 8);
                    string_id messageId = new string_id("quest/force_sensitive/fs_patrol", "large_group" + numberOfEnemies);
                    sendSystemMessage(self, messageId);
                    spawnStuff(numberOfEnemies, startLocation, questName, self);
                }
                else 
                {
                    string_id messageId = new string_id("quest/force_sensitive/fs_patrol", "no_objective");
                    setObjVar(self, "quest.fs_patrol.current_patrol_point", true);
                    sendSystemMessage(self, messageId);
                    if (hasObjVar(self, "quest." + questName + ".waypoint" + waypointNumber))
                    {
                        dictionary params = new dictionary();
                        params.put("waypointNumber", waypointNumber);
                        params.put("questName", questName);
                        messageTo(self, "handleDestroyWaypoint", params, 3.0f, false);
                    }
                }
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int OnDetach(obj_id self) throws InterruptedException
    {
        if (DEBUGGING)
        {
            sendSystemMessageTestingOnly(self, "Detach Trigger Fire");
        }
        cleanUpStuff(self);
        return SCRIPT_CONTINUE;
    }
}
