package script.quest.task.ground;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.ai.ai_combat;
import script.library.ai_lib;
import script.library.create;
import script.library.groundquests;
import script.library.locations;
import script.library.utils;

public class encounter extends script.quest.task.ground.base_task
{
    public encounter()
    {
    }
    public static final String dataTableColumnCreatureType = "CREATURE_TYPE";
    public static final String dataTableColumnCount = "COUNT";
    public static final String dataTableColumnMinDistance = "MIN_DISTANCE";
    public static final String dataTableColumnMaxDistance = "MAX_DISTANCE";
    public static final String dataTableColumnRelativeOffsetX = "RELATIVE_OFFSET_X";
    public static final String dataTableColumnRelativeOffsetZ = "RELATIVE_OFFSET_Z";
    public static final String objvarSpawnList = "spawnList";
    public static final String scriptNameEncounterOnCreature = "quest.task.ground.encounter_on_creature";
    public static final String objvarOnCreatureOwner = "quest.owner";
    public static final String objvarOnCreatureQuestCrc = "quest.questCrc";
    public static final String objvarOnCreatureTaskId = "quest.taskId";
    public static final String objvarAttackOnSpawn = "quest.attackOnSpawn";
    public static final String taskType = "encounter";
    public static final String dot = ".";
    public static final int cleanupTimeMin = 300;
    public static final int cleanupTimeMax = 600;
    public int OnTaskActivated(obj_id self, int questCrc, int taskId) throws InterruptedException
    {
        groundquests.questOutputDebugInfo(self, questCrc, taskId, taskType, "OnTaskActivated", taskType + " task activated.");
        String baseObjVar = groundquests.setBaseObjVar(self, taskType, questGetQuestName(questCrc), taskId);
        String creatureType = groundquests.getTaskStringDataEntry(questCrc, taskId, dataTableColumnCreatureType);
        int minCount = groundquests.getTaskIntDataEntry(questCrc, taskId, dataTableColumnCount);
        int maxCount = minCount;
        float minRadius = groundquests.getTaskIntDataEntry(questCrc, taskId, dataTableColumnMinDistance);
        float maxRadius = groundquests.getTaskIntDataEntry(questCrc, taskId, dataTableColumnMaxDistance);
        String relativeOffsetXString = groundquests.getTaskStringDataEntry(questCrc, taskId, dataTableColumnRelativeOffsetX);
        String relativeOffsetZString = groundquests.getTaskStringDataEntry(questCrc, taskId, dataTableColumnRelativeOffsetZ);
        float relativeOffsetX = 0;
        float relativeOffsetZ = 0;
        if (relativeOffsetXString != null)
        {
            relativeOffsetX = utils.stringToFloat(relativeOffsetXString);
        }
        if (relativeOffsetZString != null)
        {
            relativeOffsetZ = utils.stringToFloat(relativeOffsetZString);
        }
        Vector spawnList = new Vector();
        spawnList.setSize(0);
        spawnList.clear();
        int count = rand(minCount, maxCount);
        for (int i = 0; i < count; ++i)
        {
            location l = null;
            location locTest = getLocation(self);
            if (!isIdValid(locTest.cell))
            {
                l = groundquests.getRandom2DLocationAroundLocation(self, relativeOffsetX, relativeOffsetZ, minRadius, maxRadius);
            }
            else 
            {
                String strCell = getCellName(locTest.cell);
                obj_id objBuilding = getTopMostContainer(self);
                l = getGoodLocation(objBuilding, strCell);
            }
            obj_id creature = create.createCreature(creatureType, l, true);
            if(isValidId(creature)) {
                setObjVar(creature, objvarOnCreatureOwner, self);
                setObjVar(creature, objvarOnCreatureQuestCrc, questCrc);
                setObjVar(creature, objvarOnCreatureTaskId, taskId);
                attachScript(creature, scriptNameEncounterOnCreature);
                if (!hasCondition(creature, CONDITION_CONVERSABLE)) {
                    groundquests.questOutputDebugInfo(self, questCrc, taskId, taskType, "OnTaskActivated", "Spawned " + creature + " at [" + l.x + ", " + l.y + ", " + l.z + "] to attack " + self);
                    startCombat(creature, self);
                }
                utils.addElement(spawnList, creature);
            }
        }
        setObjVar(self, baseObjVar + dot + objvarSpawnList, spawnList);
        return super.OnTaskActivated(self, questCrc, taskId);
    }
    public int messageEncounterTaskCreatureDied(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id creature = params.getObjId("source");
        int questCrc = params.getInt("questCrc");
        int taskId = params.getInt("taskId");
        String baseObjVar = groundquests.getBaseObjVar(self, taskType, questGetQuestName(questCrc), taskId);
        String spawnListObjVarName = baseObjVar + dot + objvarSpawnList;
        if (hasObjVar(self, spawnListObjVarName))
        {
            obj_id[] spawnList = getObjIdArrayObjVar(self, spawnListObjVarName);
            if (spawnList != null)
            {
                for (int k = 0; k < spawnList.length; ++k)
                {
                    obj_id spawn = spawnList[k];
                    if (spawn == creature)
                    {
                        groundquests.questOutputDebugInfo(self, questCrc, taskId, taskType, "messageEncounterTaskCreatureDied", "[" + creature + "] being tracked for an encounter task was killed.");
                        if (spawnList.length == 1)
                        {
                            removeObjVar(self, spawnListObjVarName);
                            questCompleteTask(questCrc, taskId, self);
                        }
                        else 
                        {
                            Vector newSpawnListResizable = new Vector();
                            newSpawnListResizable.setSize(0);
                            for (int l = 0; l < spawnList.length; ++l)
                            {
                                if (spawnList[l] != spawnList[k])
                                {
                                    utils.addElement(newSpawnListResizable, spawnList[l]);
                                }
                            }
                            obj_id[] newSpawnList = new obj_id[0];
                            if (newSpawnListResizable != null)
                            {
                                newSpawnList = new obj_id[newSpawnListResizable.size()];
                                newSpawnListResizable.toArray(newSpawnList);
                            }
                            setObjVar(self, spawnListObjVarName, newSpawnList);
                        }
                    }
                }
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int OnTaskCompleted(obj_id self, int questCrc, int taskId) throws InterruptedException
    {
        cleanup(self, questCrc, taskId);
        groundquests.questOutputDebugInfo(self, questCrc, taskId, taskType, "OnTaskCompleted", taskType + " task completed.");
        return super.OnTaskCompleted(self, questCrc, taskId);
    }
    public int OnTaskFailed(obj_id self, int questCrc, int taskId) throws InterruptedException
    {
        cleanup(self, questCrc, taskId);
        groundquests.questOutputDebugInfo(self, questCrc, taskId, taskType, "OnTaskFailed", taskType + " task failed.");
        return super.OnTaskFailed(self, questCrc, taskId);
    }
    public int OnTaskCleared(obj_id self, int questCrc, int taskId) throws InterruptedException
    {
        cleanup(self, questCrc, taskId);
        groundquests.questOutputDebugInfo(self, questCrc, taskId, taskType, "OnTaskCleared", taskType + " task cleared.");
        return super.OnTaskCleared(self, questCrc, taskId);
    }
    public void cleanup(obj_id player, int questCrc, int taskId) throws InterruptedException
    {
        String taskIdObjVarName = groundquests.getBaseObjVar(player, taskType, questGetQuestName(questCrc), taskId);
        if (taskIdObjVarName != null)
        {
            String spawnListObjVarName = taskIdObjVarName + dot + objvarSpawnList;
            if (hasObjVar(player, spawnListObjVarName))
            {
                obj_id[] spawnList = getObjIdArrayObjVar(player, spawnListObjVarName);
                if (spawnList != null)
                {
                    for (int k = 0; k < spawnList.length; ++k)
                    {
                        obj_id spawn = spawnList[k];
                        if (!ai_lib.isAiDead(spawn))
                        {
                            dictionary params = new dictionary();
                            int cleanupTime = rand(cleanupTimeMin, cleanupTimeMax);
                            messageTo(spawn, "messageEncounterCleanup", params, cleanupTime, false);
                        }
                    }
                }
            }
        }
        groundquests.clearBaseObjVar(player, taskType, questGetQuestName(questCrc), taskId);
    }
    public int OnDeath(obj_id self, obj_id killer, obj_id corpseId) throws InterruptedException
    {
        groundquests.failAllActiveTasksOfType(self, taskType);
        return SCRIPT_CONTINUE;
    }
    public int OnLogout(obj_id self) throws InterruptedException
    {
        groundquests.failAllActiveTasksOfType(self, taskType);
        return SCRIPT_CONTINUE;
    }
    public int OnDetach(obj_id self) throws InterruptedException
    {
        removeObjVar(self, groundquests.getTaskTypeObjVar(self, taskType));
        return SCRIPT_CONTINUE;
    }
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        groundquests.failAllActiveTasksOfType(self, taskType);
        return SCRIPT_CONTINUE;
    }
}
