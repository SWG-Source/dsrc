package script.systems.fs_quest.fs_quests_sad;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.fs_dyn_village;
import script.library.fs_quests_sad;
import script.library.quests;

public class cleanup2 extends script.base_script
{
    public cleanup2()
    {
    }
    public void cleanup(obj_id self) throws InterruptedException
    {
        if (!fs_quests_sad.hasCompletedAllTasks2(self))
        {
            clearWaypoint(self);
            string_id sid = new string_id("quest/quest_journal/fs_quests_sad", "wrong_phase");
            sendSystemMessage(self, sid);
            int questId = quests.getQuestId("fs_quests_sad2_tasks");
            if (questId < 0)
            {
                return;
            }
            clearCompletedQuest(self, questId);
            if (isQuestActive(self, questId))
            {
                quests.deactivate("fs_quests_sad2_tasks", self);
            }
            for (int i = 1; i <= 8; i++)
            {
                questId = quests.getQuestId("fs_quests_sad2_task" + i);
                clearCompletedQuest(self, questId);
                if (isQuestActive(self, questId))
                {
                    quests.deactivate("fs_quests_sad2_task" + i, self);
                }
                questId = quests.getQuestId("fs_quests_sad2_return" + i);
                clearCompletedQuest(self, questId);
                if (isQuestActive(self, questId))
                {
                    quests.deactivate("fs_quests_sad2_return" + i, self);
                }
            }
            removeObjVar(self, fs_quests_sad.SAD_OBJVAR_TASK_ACTIVE);
            removeObjVar(self, fs_quests_sad.SAD_OBJVAR_TIMESTAMP);
            removeObjVar(self, fs_quests_sad.SAD_OBJVAR_TAKEN_SINCE_TIMESTAMP);
        }
        detachScript(self, "systems.fs_quest.fs_quests_sad.cleanup2");
    }
    public int msgQuestAbortPhaseChange(obj_id self, dictionary params) throws InterruptedException
    {
        cleanup(self);
        return SCRIPT_CONTINUE;
    }
    public void clearWaypoint(obj_id player) throws InterruptedException
    {
        String questName = "fs_quests_sad2_return1";
        for (int i = 1; i <= 8; i++)
        {
            if (quests.isActive("fs_quests_sad2_return" + i, player))
            {
                questName = "fs_quests_sad2_return" + i;
            }
        }
        String waypointObjVar = "quest." + questName + ".waypoint";
        questName = "fs_quests_sad2_task1";
        for (int x = 1; x <= 8; x++)
        {
            if (quests.isActive("fs_quests_sad2_task" + x, player))
            {
                questName = "fs_quests_sad2_task" + x;
            }
        }
        String waypointObjVar2 = "quest." + questName + ".waypoint";
        if (hasObjVar(player, waypointObjVar))
        {
            obj_id point = getObjIdObjVar(player, waypointObjVar);
            destroyWaypointInDatapad(point, player);
            removeObjVar(player, waypointObjVar);
        }
        if (hasObjVar(player, waypointObjVar2))
        {
            obj_id point2 = getObjIdObjVar(player, waypointObjVar);
            destroyWaypointInDatapad(point2, player);
            removeObjVar(player, waypointObjVar2);
        }
        return;
    }
}
